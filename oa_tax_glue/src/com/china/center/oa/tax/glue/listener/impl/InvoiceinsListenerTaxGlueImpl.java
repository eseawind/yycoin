package com.china.center.oa.tax.glue.listener.impl;

import java.util.ArrayList;
import java.util.List;

import com.center.china.osgi.publics.User;
import com.china.center.common.MYException;
import com.china.center.oa.finance.bean.InvoiceinsBean;
import com.china.center.oa.finance.bean.InvoiceinsItemBean;
import com.china.center.oa.finance.constant.FinanceConstant;
import com.china.center.oa.finance.dao.InvoiceinsDAO;
import com.china.center.oa.finance.dao.InvoiceinsItemDAO;
import com.china.center.oa.finance.listener.InvoiceinsListener;
import com.china.center.oa.publics.bean.DutyBean;
import com.china.center.oa.publics.bean.InvoiceBean;
import com.china.center.oa.publics.bean.StafferBean;
import com.china.center.oa.publics.constant.PublicConstant;
import com.china.center.oa.publics.dao.CommonDAO;
import com.china.center.oa.publics.dao.DepartmentDAO;
import com.china.center.oa.publics.dao.DutyDAO;
import com.china.center.oa.publics.dao.InvoiceDAO;
import com.china.center.oa.publics.dao.StafferDAO;
import com.china.center.oa.tax.bean.FinanceBean;
import com.china.center.oa.tax.bean.FinanceItemBean;
import com.china.center.oa.tax.bean.TaxBean;
import com.china.center.oa.tax.constanst.TaxConstanst;
import com.china.center.oa.tax.constanst.TaxItemConstanst;
import com.china.center.oa.tax.dao.FinanceDAO;
import com.china.center.oa.tax.dao.TaxDAO;
import com.china.center.oa.tax.helper.FinanceHelper;
import com.china.center.oa.tax.manager.FinanceManager;
import com.china.center.tools.TimeTools;

public class InvoiceinsListenerTaxGlueImpl implements InvoiceinsListener
{
	private DutyDAO dutyDAO = null;
	
	private InvoiceDAO invoiceDAO = null;
	
    private DepartmentDAO departmentDAO = null;

    private TaxDAO taxDAO = null;

    private CommonDAO commonDAO = null;

    private StafferDAO stafferDAO = null;

    private FinanceManager financeManager = null;

    private FinanceDAO financeDAO = null;

    private InvoiceinsDAO invoiceinsDAO = null;
    
    private InvoiceinsItemDAO invoiceinsItemDAO = null;
	
	public String getListenerType()
	{
		return "InvoiceinsListener.TaxGlueImpl";
	}

	public void onConfirmPay(User user, InvoiceinsBean bean) throws MYException
	{
        FinanceBean financeBean = new FinanceBean();

        String desc = (bean.getOtype() == FinanceConstant.INVOICEINS_TYPE_OUT ? "开票-确认付款:" : "退票");
        
        String name = user.getStafferName() + desc + bean.getId() + '.';

        financeBean.setName(name);

        financeBean.setType(TaxConstanst.FINANCE_TYPE_MANAGER);

        if (bean.getOtype() == FinanceConstant.INVOICEINS_TYPE_OUT)
        	financeBean.setCreateType(TaxConstanst.FINANCE_CREATETYPE_INVOICEINS_CONFIRMPAY);
        else
        	financeBean.setCreateType(TaxConstanst.FINANCE_CREATETYPE_INVOICEINS_RET);

        financeBean.setRefId(bean.getId());

        financeBean.setDutyId(bean.getDutyId());

        financeBean.setCreaterId(user.getStafferId());

        financeBean.setDescription(financeBean.getName());

        financeBean.setFinanceDate(TimeTools.now_short());

        financeBean.setLogTime(TimeTools.now());

        List<FinanceItemBean> itemList = new ArrayList<FinanceItemBean>();

        // 借:银行科目 贷:银行对应的暂记户科目
        createAddItem1(user, bean, financeBean, itemList);

        financeBean.setItemList(itemList);

        financeManager.addFinanceBeanWithoutTransactional(user, financeBean);
    
	}

    private void createAddItem1(User user, InvoiceinsBean bean, 
    		FinanceBean financeBean, List<FinanceItemBean> itemList)
    throws MYException
    {
    	StafferBean inStaffer = stafferDAO.find(bean.getStafferId());
    	
    	if (null == inStaffer)
    		throw new MYException("数据错误，业务员不存");
    	
		String name = user.getStafferName() + "开票:" + bean.getId() + '.';
		
		// 借:库存商品 贷:应付账款-供应商
		FinanceItemBean itemIn = new FinanceItemBean();
		
		String pareId = commonDAO.getSquenceString();
		
		itemIn.setPareId(pareId);
		
		itemIn.setName("业务员开票税金:" + name);
		
		itemIn.setForward(TaxConstanst.TAX_FORWARD_IN);
		
		FinanceHelper.copyFinanceItem(financeBean, itemIn);
		
		TaxBean inTax = taxDAO.find(TaxItemConstanst.SAIL_INVOICEINS_FEE);
		
		if (inTax == null)
		{
			throw new MYException("缺少对应科目,请确认操作");
		}
		
		// 科目拷贝
		FinanceHelper.copyTax(inTax, itemIn);
		
		double inMoney = calculateTaxes(bean);
		
		itemIn.setInmoney(FinanceHelper.doubleToLong(inMoney));
		
		itemIn.setOutmoney(0);
		
		itemIn.setDescription(itemIn.getName());
		
		// 辅助核算 NA
		itemIn.setDepartmentId(inStaffer.getPrincipalshipId());
		itemIn.setStafferId(inStaffer.getId());
		
		itemList.add(itemIn);
		
		// 贷方
		FinanceItemBean itemOut = new FinanceItemBean();
		
		itemOut.setPareId(pareId);
		
		itemOut.setName("主营业务税金及附加:" + name);
		
		itemOut.setForward(TaxConstanst.TAX_FORWARD_OUT);
		
		FinanceHelper.copyFinanceItem(financeBean, itemOut);
		
		TaxBean outTax = taxDAO.find(TaxItemConstanst.MAIN_INVOICEINS);
		
		if (outTax == null)
		{
			throw new MYException("缺少对应科目,请确认操作");
		}
		
		// 科目拷贝
		FinanceHelper.copyTax(outTax, itemOut);
		
		itemOut.setInmoney(0);
		
		itemOut.setOutmoney(itemIn.getInmoney());
		
		itemOut.setDescription(itemOut.getName());
		
		// 辅助核算 NA
		itemOut.setDepartmentId(inStaffer.getPrincipalshipId());
		
		itemList.add(itemOut);
    }
	
    /**
     * 计算税金
     * @param bean
     * @return
     */
    private double calculateTaxes(InvoiceinsBean bean) throws MYException
    {
    	String dutyId = bean.getDutyId();
    	
    	String invoiceId = bean.getInvoiceId();
    	
    	DutyBean dutyBean = dutyDAO.find(dutyId);
    	
    	if (null == dutyBean)
    	{
    		throw new MYException("数据错误1");
    	}
    	
    	InvoiceBean invoice = invoiceDAO.find(invoiceId);
    	
    	if (null == invoice)
    		throw new MYException("数据错误2");
    	
    	// 普通
    	if (dutyBean.getMtype() == PublicConstant.MANAGER_TYPE_COMMON)
    	{
    		// 非旧货  （开票额-销售单对应原始成本 ）÷117%×17%
    		if (dutyBean.getType() == 0)
    		{
    			double costTotal = 0.0d;
    			
    			for (InvoiceinsItemBean each : bean.getItemList())
    			{
    				costTotal += each.getAmount() * each.getCostPrice();
    			}
    			
    			return (bean.getMoneys() - costTotal)/(1 + invoice.getVal()/100) * (invoice.getVal()/100);
    		}
    		else if (dutyBean.getType() == 4) //开票额÷102%×2%
    		{
    			return bean.getMoneys()/(1 + invoice.getVal()/100) * (invoice.getVal()/100);
    		}
    		else
    		{
    			return 0.0d;
    		}
    	}
    	else
    	{
    		return bean.getMoneys()/(1 + invoice.getVal()/100) * (invoice.getVal()/100);
    	}
    }
    
	public DepartmentDAO getDepartmentDAO()
	{
		return departmentDAO;
	}

	public void setDepartmentDAO(DepartmentDAO departmentDAO)
	{
		this.departmentDAO = departmentDAO;
	}

	public TaxDAO getTaxDAO()
	{
		return taxDAO;
	}

	public void setTaxDAO(TaxDAO taxDAO)
	{
		this.taxDAO = taxDAO;
	}

	public CommonDAO getCommonDAO()
	{
		return commonDAO;
	}

	public void setCommonDAO(CommonDAO commonDAO)
	{
		this.commonDAO = commonDAO;
	}

	public StafferDAO getStafferDAO()
	{
		return stafferDAO;
	}

	public void setStafferDAO(StafferDAO stafferDAO)
	{
		this.stafferDAO = stafferDAO;
	}

	public FinanceManager getFinanceManager()
	{
		return financeManager;
	}

	public void setFinanceManager(FinanceManager financeManager)
	{
		this.financeManager = financeManager;
	}

	public FinanceDAO getFinanceDAO()
	{
		return financeDAO;
	}

	public void setFinanceDAO(FinanceDAO financeDAO)
	{
		this.financeDAO = financeDAO;
	}

	public InvoiceinsDAO getInvoiceinsDAO()
	{
		return invoiceinsDAO;
	}

	public void setInvoiceinsDAO(InvoiceinsDAO invoiceinsDAO)
	{
		this.invoiceinsDAO = invoiceinsDAO;
	}

	public InvoiceinsItemDAO getInvoiceinsItemDAO()
	{
		return invoiceinsItemDAO;
	}

	public void setInvoiceinsItemDAO(InvoiceinsItemDAO invoiceinsItemDAO)
	{
		this.invoiceinsItemDAO = invoiceinsItemDAO;
	}

	public DutyDAO getDutyDAO()
	{
		return dutyDAO;
	}

	public void setDutyDAO(DutyDAO dutyDAO)
	{
		this.dutyDAO = dutyDAO;
	}

	public InvoiceDAO getInvoiceDAO()
	{
		return invoiceDAO;
	}

	public void setInvoiceDAO(InvoiceDAO invoiceDAO)
	{
		this.invoiceDAO = invoiceDAO;
	}

}
