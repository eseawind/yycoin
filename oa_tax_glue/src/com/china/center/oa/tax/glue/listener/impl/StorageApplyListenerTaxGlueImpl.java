package com.china.center.oa.tax.glue.listener.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.center.china.osgi.publics.User;
import com.china.center.common.MYException;
import com.china.center.oa.product.bean.GSOutBean;
import com.china.center.oa.product.bean.GSOutItemBean;
import com.china.center.oa.product.bean.ProductBean;
import com.china.center.oa.product.constant.StorageConstant;
import com.china.center.oa.product.dao.ProductDAO;
import com.china.center.oa.product.listener.StorageApplyListener;
import com.china.center.oa.publics.constant.PublicConstant;
import com.china.center.oa.publics.dao.CommonDAO;
import com.china.center.oa.publics.dao.DepartmentDAO;
import com.china.center.oa.publics.dao.DutyDAO;
import com.china.center.oa.publics.dao.StafferDAO;
import com.china.center.oa.tax.bean.FinanceBean;
import com.china.center.oa.tax.bean.FinanceItemBean;
import com.china.center.oa.tax.bean.TaxBean;
import com.china.center.oa.tax.constanst.TaxConstanst;
import com.china.center.oa.tax.constanst.TaxItemConstanst;
import com.china.center.oa.tax.dao.TaxDAO;
import com.china.center.oa.tax.helper.FinanceHelper;
import com.china.center.oa.tax.manager.FinanceManager;
import com.china.center.tools.TimeTools;

public class StorageApplyListenerTaxGlueImpl implements StorageApplyListener
{
	private final Log _logger = LogFactory.getLog(getClass());

    private DutyDAO dutyDAO = null;

    private DepartmentDAO departmentDAO = null;

    private TaxDAO taxDAO = null;

    private CommonDAO commonDAO = null;

    private FinanceManager financeManager = null;

    private StafferDAO stafferDAO = null;

    private ProductDAO productDAO = null;

	@Override
	public String getListenerType()
	{
		return "StorageApplyListener.TaxGlueImpl";
	}

	@Override
	public void onConfirmGSOut(User user, GSOutBean bean) throws MYException
	{
        // 出库
        if (bean.getType() == StorageConstant.STORAGEAPPLY_GS_OUT)
        {
            FinanceBean financeBean = new FinanceBean();

            String name = "金银料出库:" + bean.getId() + '.';

            financeBean.setName(name);

            financeBean.setCreateType(TaxConstanst.FINANCE_CREATETYPE_GS_OUT);

            financeBean.setRefId(bean.getId());

            financeBean.setType(TaxConstanst.FINANCE_TYPE_DUTY);
            financeBean.setDutyId(PublicConstant.DEFAULR_DUTY_ID);
            
            financeBean.setDescription(financeBean.getName());

            financeBean.setFinanceDate(TimeTools.now_short());

            financeBean.setLogTime(TimeTools.now());

            List<FinanceItemBean> itemList = new ArrayList<FinanceItemBean>();

            createItemForOut(user, bean, financeBean, itemList);

            financeBean.setItemList(itemList);

            financeManager.addFinanceBeanWithoutTransactional(user, financeBean);
        }
        else
        {
            // 入库
            FinanceBean financeBean = new FinanceBean();

            String name = "金银料入库:" + bean.getId() + '.';

            financeBean.setName(name);

            financeBean.setType(TaxConstanst.FINANCE_TYPE_DUTY);
            financeBean.setDutyId(PublicConstant.DEFAULR_DUTY_ID);

            financeBean.setCreateType(TaxConstanst.FINANCE_CREATETYPE_GS_OUT);

            financeBean.setRefId(bean.getId());

            financeBean.setDescription(financeBean.getName());

            financeBean.setFinanceDate(TimeTools.now_short());

            financeBean.setLogTime(TimeTools.now());

            List<FinanceItemBean> itemList = new ArrayList<FinanceItemBean>();

            createItemForIn(user, bean, financeBean, itemList);

            financeBean.setItemList(itemList);

            financeManager.addFinanceBeanWithoutTransactional(user, financeBean);
        }
    }
	
	/**
	 * 
	 * @param user
	 * @param compose
	 * @param financeBean
	 * @param itemList
	 * @throws MYException
	 */
	private void createItemForOut(User user, GSOutBean gsoutBean,
            FinanceBean financeBean, List<FinanceItemBean> itemList)
	throws MYException
	{
        String name = "金银料出库:" + gsoutBean.getId() + '.';

        String pare1 = commonDAO.getSquenceString();

        // 借: 委托加工物资 贷:库存商品
        long total1 = 0L;
        long total = 0L;

        // 产品成本
        List<GSOutItemBean> gsitemList = gsoutBean.getItemList();

        for (GSOutItemBean itemBean : gsitemList)
        {
        	int [] weights = new int[]{itemBean.getGoldWeight(), itemBean.getSilverWeight()};
        	
        	for (int i = 0; i < weights.length; i++)
        	{
        		int weight = weights[i];
        		
        		if (weight <= 0)
        			continue;
        		
        		FinanceItemBean itemIn = new FinanceItemBean();

                itemIn.setPareId(pare1);

                if (i == 0)
                {
                	itemIn.setName("委托加工物资(金料 ):" + name);
                }else{
                	itemIn.setName("委托加工物资(银料):" + name);
                }

                itemIn.setForward(TaxConstanst.TAX_FORWARD_IN);

                FinanceHelper.copyFinanceItem(financeBean, itemIn);

           	 	// 委托加工物资
               String itemTaxIdIn = TaxItemConstanst.CONSIGN_PROCESS;

               TaxBean inTax = taxDAO.findByUnique(itemTaxIdIn);

               if (inTax == null)
               {
                   throw new MYException("数据错误,请确认操作");
               }

               // 科目拷贝
               FinanceHelper.copyTax(inTax, itemIn);
           
                double InMoney = weight * (i == 0 ? itemBean.getGoldPrice() : itemBean.getSilverPrice());

                itemIn.setInmoney(FinanceHelper.doubleToLong(InMoney));

                itemIn.setOutmoney(0);

                total1 += FinanceHelper.doubleToLong(InMoney);

                itemIn.setDescription(itemIn.getName());
                
                // 辅助核算 产品/仓库
                if (i == 0)
                {
                	// 金料 ID "7631391"
                	itemIn.setProductId(StorageConstant.GOLD_PRODUCT);
                }else{
                	// 银料 ID
                	itemIn.setProductId(StorageConstant.SILVER_PRODUCT);
                }
                
                itemIn.setProductAmountOut(weight);
                itemIn.setDepotId(itemBean.getDeportId());

                itemList.add(itemIn);
        	}
        	
        	FinanceItemBean itemOut1 = new FinanceItemBean();

            itemOut1.setPareId(pare1);
            
            ProductBean product = productDAO.find(itemBean.getProductId());

            if (product == null)
            {
                throw new MYException("数据错误,请确认操作");
            }

            itemOut1.setName("库存商品(" + product.getName() + "):" + name);

            itemOut1.setForward(TaxConstanst.TAX_FORWARD_OUT);

            FinanceHelper.copyFinanceItem(financeBean, itemOut1);

       	 	// 库存商品
           String itemTaxIdOut1 = TaxItemConstanst.DEPOR_PRODUCT;

           TaxBean outTax = taxDAO.findByUnique(itemTaxIdOut1);

           if (outTax == null)
           {
               throw new MYException("数据错误,请确认操作");
           }

           	// 科目拷贝
           	FinanceHelper.copyTax(outTax, itemOut1);
       
            double outMoney = itemBean.getAmount() * itemBean.getPrice();

            itemOut1.setInmoney(0);

            itemOut1.setOutmoney(FinanceHelper.doubleToLong(outMoney));

            total += FinanceHelper.doubleToLong(outMoney);

            itemOut1.setDescription(itemOut1.getName());
            
            // 辅助核算 产品/仓库
            itemOut1.setProductId(itemBean.getProductId());
            itemOut1.setProductAmountOut( -itemBean.getAmount());
            itemOut1.setDepotId(itemBean.getDeportId());

            itemList.add(itemOut1);
            
            if (itemOut1.getOutmoney() != total1)
            {
                _logger.info("产品拆分差价:" + itemOut1.getOutmoney() + ".实际合计:" + total1);

                itemOut1.setOutmoney(total1);
            }
        }
	}
	
	private void createItemForIn(User user, GSOutBean gsoutBean,
            FinanceBean financeBean, List<FinanceItemBean> itemList)
	throws MYException
	{
        String name = "金银料入库:" + gsoutBean.getId() + '.';

        String pare1 = commonDAO.getSquenceString();

        // 借:库存商品 贷: 委托加工物资
        // 产品成本
        List<GSOutItemBean> gsitemList = gsoutBean.getItemList();

        long total1 = 0L;
        
        for (GSOutItemBean itemBean : gsitemList)
        {
        	FinanceItemBean itemOut1 = new FinanceItemBean();

            itemOut1.setPareId(pare1);
            
            ProductBean product = productDAO.find(itemBean.getProductId());

            if (product == null)
            {
                throw new MYException("数据错误,请确认操作");
            }

            itemOut1.setName("库存商品(" + product.getName() + "):" + name);

            itemOut1.setForward(TaxConstanst.TAX_FORWARD_IN);

            FinanceHelper.copyFinanceItem(financeBean, itemOut1);

       	 	// 库存商品
           String itemTaxIdOut1 = TaxItemConstanst.DEPOR_PRODUCT;

           TaxBean outTax = taxDAO.findByUnique(itemTaxIdOut1);

           if (outTax == null)
           {
               throw new MYException("数据错误,请确认操作");
           }

            // 科目拷贝
            FinanceHelper.copyTax(outTax, itemOut1);
       
            double outMoney = itemBean.getAmount() * itemBean.getPrice();

            itemOut1.setInmoney(FinanceHelper.doubleToLong(outMoney));

            itemOut1.setOutmoney(0);

            itemOut1.setDescription(itemOut1.getName());
            
            // 辅助核算 产品/仓库
            itemOut1.setProductId(itemBean.getProductId());
            itemOut1.setProductAmountOut(itemBean.getAmount());
            itemOut1.setDepotId(itemBean.getDeportId());

            itemList.add(itemOut1);
        	
        	int [] weights = new int[]{itemBean.getGoldWeight(), itemBean.getSilverWeight()};
        	
        	for (int i = 0; i < weights.length; i++)
        	{
        		int weight = weights[i];
        		
        		if (weight <= 0)
        			continue;
        		
                FinanceItemBean itemIn = new FinanceItemBean();

                itemIn.setPareId(pare1);

                //
                if (i == 0)
                {
                	itemIn.setName("委托加工物资(金料 ):" + name);
                }else{
                	itemIn.setName("委托加工物资(银料):" + name);
                }

                itemIn.setForward(TaxConstanst.TAX_FORWARD_OUT);

                FinanceHelper.copyFinanceItem(financeBean, itemIn);

           	 	// 委托加工物资
               String itemTaxIdIn = TaxItemConstanst.CONSIGN_PROCESS;

               TaxBean inTax = taxDAO.findByUnique(itemTaxIdIn);

               if (inTax == null)
               {
                   throw new MYException("数据错误,请确认操作");
               }

               // 科目拷贝
               FinanceHelper.copyTax(inTax, itemIn);
           
               double InMoney = weight * itemBean.getAmount() * (i == 0 ? itemBean.getGoldPrice() : itemBean.getSilverPrice());

               itemIn.setInmoney(0);

               itemIn.setOutmoney(FinanceHelper.doubleToLong(InMoney));

               total1 += FinanceHelper.doubleToLong(InMoney);

                itemIn.setDescription(itemIn.getName());
                
                // 辅助核算 产品/仓库
                if (i == 0)
                {
                	// 金料 ID
                	itemIn.setProductId("7631391");
                }else{
                	// 银料 ID
                	itemIn.setProductId("7631392");
                }
                
                itemIn.setProductAmountOut( -weight * itemBean.getAmount());
                itemIn.setDepotId(itemBean.getDeportId());

                itemList.add(itemIn);
        	}
        	
        	if (itemOut1.getInmoney() != total1)
            {
                _logger.info("产品拆分差价:" + itemOut1.getInmoney() + ".实际合计:" + total1);

                itemOut1.setInmoney(total1);
            }
        }
	}
	
	/**
	 * @return the dutyDAO
	 */
	public DutyDAO getDutyDAO()
	{
		return dutyDAO;
	}

	/**
	 * @param dutyDAO the dutyDAO to set
	 */
	public void setDutyDAO(DutyDAO dutyDAO)
	{
		this.dutyDAO = dutyDAO;
	}

	/**
	 * @return the departmentDAO
	 */
	public DepartmentDAO getDepartmentDAO()
	{
		return departmentDAO;
	}

	/**
	 * @param departmentDAO the departmentDAO to set
	 */
	public void setDepartmentDAO(DepartmentDAO departmentDAO)
	{
		this.departmentDAO = departmentDAO;
	}

	/**
	 * @return the taxDAO
	 */
	public TaxDAO getTaxDAO()
	{
		return taxDAO;
	}

	/**
	 * @param taxDAO the taxDAO to set
	 */
	public void setTaxDAO(TaxDAO taxDAO)
	{
		this.taxDAO = taxDAO;
	}

	/**
	 * @return the commonDAO
	 */
	public CommonDAO getCommonDAO()
	{
		return commonDAO;
	}

	/**
	 * @param commonDAO the commonDAO to set
	 */
	public void setCommonDAO(CommonDAO commonDAO)
	{
		this.commonDAO = commonDAO;
	}

	/**
	 * @return the financeManager
	 */
	public FinanceManager getFinanceManager()
	{
		return financeManager;
	}

	/**
	 * @param financeManager the financeManager to set
	 */
	public void setFinanceManager(FinanceManager financeManager)
	{
		this.financeManager = financeManager;
	}

	/**
	 * @return the stafferDAO
	 */
	public StafferDAO getStafferDAO()
	{
		return stafferDAO;
	}

	/**
	 * @param stafferDAO the stafferDAO to set
	 */
	public void setStafferDAO(StafferDAO stafferDAO)
	{
		this.stafferDAO = stafferDAO;
	}

	/**
	 * @return the productDAO
	 */
	public ProductDAO getProductDAO()
	{
		return productDAO;
	}

	/**
	 * @param productDAO the productDAO to set
	 */
	public void setProductDAO(ProductDAO productDAO)
	{
		this.productDAO = productDAO;
	}
}
