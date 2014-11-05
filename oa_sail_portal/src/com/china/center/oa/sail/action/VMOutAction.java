package com.china.center.oa.sail.action;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.center.china.osgi.publics.User;
import com.center.china.osgi.publics.file.read.ReadeFileFactory;
import com.center.china.osgi.publics.file.read.ReaderFile;
import com.china.center.actionhelper.common.KeyConstant;
import com.china.center.common.MYException;
import com.china.center.oa.product.bean.DepotBean;
import com.china.center.oa.product.bean.ProductBean;
import com.china.center.oa.product.dao.DepotDAO;
import com.china.center.oa.product.dao.ProductDAO;
import com.china.center.oa.publics.Helper;
import com.china.center.oa.publics.bean.DutyBean;
import com.china.center.oa.publics.bean.StafferBean;
import com.china.center.oa.publics.dao.DutyDAO;
import com.china.center.oa.publics.dao.StafferDAO;
import com.china.center.oa.sail.bean.BaseBean;
import com.china.center.oa.sail.bean.OutBean;
import com.china.center.oa.sail.bean.UnitViewBean;
import com.china.center.oa.sail.constanst.OutConstant;
import com.china.center.oa.sail.dao.UnitViewDAO;
import com.china.center.oa.sail.manager.VMOutManager;
import com.china.center.tools.ListTools;
import com.china.center.tools.MathTools;
import com.china.center.tools.RequestDataStream;
import com.china.center.tools.StringTools;
import com.china.center.tools.TimeTools;

public class VMOutAction extends DispatchAction
{
	private final Log _logger = LogFactory.getLog(getClass());
	
	private UnitViewDAO unitViewDAO = null;
	
	private ProductDAO productDAO = null;
	
	private StafferDAO stafferDAO = null;
	
	private DepotDAO depotDAO = null;
	
	private DutyDAO dutyDAO = null;
	
	private VMOutManager vmOutManager = null;
	
	/**
	 * 
	 */
	public VMOutAction()
	{
	}
	
	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 */
    public ActionForward importVMOut(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
	throws ServletException
	{
    	User user = Helper.getUser(request);
    	
        RequestDataStream rds = new RequestDataStream(request);
        
        boolean importError = false;
        
        List<OutBean> importItemList = new ArrayList<OutBean>(); 
        
        StringBuilder builder = new StringBuilder();
        
        try
        {
            rds.parser();
        }
        catch (Exception e1)
        {
            _logger.error(e1, e1);

            request.setAttribute(KeyConstant.ERROR_MESSAGE, "解析失败");

            return mapping.findForward("importVMOut");
        }

        if ( !rds.haveStream())
        {
            request.setAttribute(KeyConstant.ERROR_MESSAGE, "解析失败");

            return mapping.findForward("importVMOut");
        }
        
        ReaderFile reader = ReadeFileFactory.getXLSReader();
        
        try
        {
            reader.readFile(rds.getUniqueInputStream());
            
            while (reader.hasNext())
            {
                String[] obj = fillObj((String[])reader.next());

                // 第一行忽略
                if (reader.getCurrentLineNumber() == 1)
                {
                    continue;
                }

                if (StringTools.isNullOrNone(obj[0]))
                {
                    continue;
                }
                
                int currentNumber = reader.getCurrentLineNumber();

                if (obj.length >= 2 )
                {
                	OutBean bean = new OutBean();
                    
                	boolean error = innerAdd(bean, obj, builder, currentNumber);
                	
                	if (!importError)
                	{
                		importError = error;
                	}
                    
                    importItemList.add(bean);
                    
                }
                else
                {
                    builder
                        .append("第[" + currentNumber + "]错误:")
                        .append("数据长度不足20格错误")
                        .append("<br>");
                    
                    importError = true;
                }
            }
            
            
        }catch (Exception e)
        {
            _logger.error(e, e);
            
            request.setAttribute(KeyConstant.ERROR_MESSAGE, e.toString());

            return mapping.findForward("importVMOut");
        }
        finally
        {
            try
            {
                reader.close();
            }
            catch (IOException e)
            {
                _logger.error(e, e);
            }
        }
        
        rds.close();

        if (importError){
            
            request.setAttribute(KeyConstant.ERROR_MESSAGE, "导入出错:"+ builder.toString());

            return mapping.findForward("importVMOut");
        }
        
        try
        {
        	vmOutManager.addBeans(user, importItemList);
        }
        catch(MYException e)
        {
            request.setAttribute(KeyConstant.ERROR_MESSAGE, "导入出错:"+ e.getErrorContent());

            return mapping.findForward("importVMOut");
        }
        
        return mapping.findForward("importVMOut");
	}
    
    private String[] fillObj(String[] obj)
    {
        String[] result = new String[40];

        for (int i = 0; i < result.length; i++ )
        {
            if (i < obj.length)
            {
                result[i] = obj[i];
            }
            else
            {
                result[i] = "";
            }
        }

        return result;
    }
    
    /**
     * 
     * @param obj
     * @throws MYException
     */
	private boolean innerAdd(OutBean bean, String[] obj, StringBuilder builder, int currentNumber)
	{
		List<BaseBean> baseList = new ArrayList<BaseBean>();
		
		bean.setBaseList(baseList);
		
		BaseBean base = new BaseBean();
		
		baseList.add(base);
		
		boolean importError = false;
		
		// 类型  【销售 -0；入库 -1】
		if ( !StringTools.isNullOrNone(obj[0]))
		{
			String name = obj[0].trim();
			
			bean.setType(-1);
			
			if ("销售".equals(name))
			{
				bean.setType(OutConstant.OUT_TYPE_OUTBILL);
			}else if ("入库".equals(name)){
				bean.setType(OutConstant.OUT_TYPE_INBILL);
			}else
			{
				builder
	            .append("第[" + currentNumber + "]错误:")
	            .append("订单类型只能为[销售或入库]")
	            .append("<br>");
				
				importError = true;
			}
			
		}else{
			builder
            .append("第[" + currentNumber + "]错误:")
            .append("订单类型不能为空")
            .append("<br>");
			
			importError = true;
		}

		// 销售类型 销售时只能是销售出库， 入库时只能为[销售退库、采购入库、采购退库]
		if ( !StringTools.isNullOrNone(obj[1]))
		{
			String name = obj[1].trim();
			
			if (bean.getType() == OutConstant.OUT_TYPE_OUTBILL)
			{
				if ("销售出库".equals(name))
				{
					bean.setOutType(OutConstant.OUTTYPE_OUT_COMMON);
				}else{
					builder
		            .append("第[" + currentNumber + "]错误:")
		            .append("销售时类型只能为销售出库")
		            .append("<br>");
					
					importError = true;
				}
			}else if (bean.getType() == OutConstant.OUT_TYPE_INBILL){
				if ("销售退库".equals(name))
				{
					bean.setOutType(OutConstant.OUTTYPE_IN_OUTBACK);
				}else if ("采购入库".equals(name))
				{
					bean.setOutType(OutConstant.OUTTYPE_IN_COMMON);
				}
				else if ("采购退库".equals(name))
				{
					bean.setOutType(OutConstant.OUTTYPE_IN_STOCK);
				}else{
					builder
		            .append("第[" + currentNumber + "]错误:")
		            .append("入库时类型只能为[销售退货、采购入库、采购退库]")
		            .append("<br>");
					
					importError = true;
				}
			}else{
				builder
	            .append("第[" + currentNumber + "]错误:")
	            .append("类型只能为[销售、入库]")
	            .append("<br>");
				
				importError = true;
			}
		}else{
			builder
            .append("第[" + currentNumber + "]错误:")
            .append("销售类型不能为空")
            .append("<br>");
			
			importError = true;
		}

		// 销售日期 格式 yyyy-MM-dd
		if ( !StringTools.isNullOrNone(obj[2]))
		{
			String name = obj[2].trim();
			
			String eL = "[0-9]{4}-[0-9]{2}-[0-9]{2}";
			Pattern p = Pattern.compile(eL);
			Matcher m = p.matcher(name);
			boolean dateFlag = m.matches();
			if (!dateFlag) {
				builder
                .append("第[" + currentNumber + "]错误:")
                .append("日期格式错误，如 2000-01-01")
                .append("<br>");
            
    			importError = true;
			} else {
				bean.setOutTime(name);
			}
		}else{
			builder
            .append("第[" + currentNumber + "]错误:")
            .append("销售日期不能为空")
            .append("<br>");
			
			importError = true;
		}
		
		// 客户/供应商代码 （客户名由代码获取）
		if ( !StringTools.isNullOrNone(obj[3]))
		{
			String name = obj[3].trim();
			
			List<UnitViewBean> unitList = unitViewDAO.queryEntityBeansByCondition("where code = ?", name);
			
			if (ListTools.isEmptyOrNull(unitList))
			{
				builder
	            .append("第[" + currentNumber + "]错误:")
	            .append("客户/供应商不存在")
	            .append("<br>");
				
				importError = true;
			}else{
				bean.setCustomerId(unitList.get(0).getId());
				bean.setCustomerName(unitList.get(0).getName());
			}
		}else{
			builder
            .append("第[" + currentNumber + "]错误:")
            .append("客户/供应商不能为空")
            .append("<br>");
			
			importError = true;
		}
		
		// 纳税实体
		if ( !StringTools.isNullOrNone(obj[5]))
		{
			String name = obj[5].trim();
			
			DutyBean duty = dutyDAO.findByUnique(name);
			
			if (null == duty)
			{
				builder
	            .append("第[" + currentNumber + "]错误:")
	            .append("纳税实体不存在")
	            .append("<br>");
				
				importError = true;
			}else{
				bean.setDutyId(duty.getId());
			}
			
		}else
		{
			builder
            .append("第[" + currentNumber + "]错误:")
            .append("纳税实体不能为空")
            .append("<br>");
			
			importError = true;
		}
		
		// 描述
		if ( !StringTools.isNullOrNone(obj[6]))
		{
			bean.setDescription(obj[6].trim());
		}
		else
		{
			builder
            .append("第[" + currentNumber + "]错误:")
            .append("备注不能为空")
            .append("<br>");
			
			importError = true;
		}

		// 回款天数
		if ( !StringTools.isNullOrNone(obj[7]))
		{
			bean.setReday(MathTools.parseInt(obj[7].trim()));
			
			if (!StringTools.isNullOrNone(bean.getOutTime()) && !StringTools.isNullOrNone(bean.getReday())) {
				bean.setRedate(TimeTools.getSpecialDateStringByDays(bean.getOutTime(), bean.getReday(), "yyyy-MM-dd"));
			}
		}
		else
		{
			builder
            .append("第[" + currentNumber + "]错误:")
            .append("回款天数不可为空")
            .append("<br>");
			
			importError = true;
		}

		// 业务员
		if ( !StringTools.isNullOrNone(obj[8]))
		{
			String name = obj[8].trim();
			
			StafferBean staffer = stafferDAO.findByUnique(name);
			
			if (null == staffer)
			{
				builder
	            .append("第[" + currentNumber + "]错误:")
	            .append("职员["+name+"]不存在")
	            .append("<br>");
				
				importError = true;
			}else{
				bean.setStafferId(staffer.getId());
				bean.setStafferName(staffer.getName());
				
				bean.setIndustryId(staffer.getIndustryId());
				bean.setIndustryId2(staffer.getIndustryId2());
				bean.setIndustryId3(staffer.getIndustryId3());
			}
		}else{
			builder
            .append("第[" + currentNumber + "]错误:")
            .append("职员不能为空")
            .append("<br>");
			
			importError = true;
		}
		
		// 仓库
		if ( !StringTools.isNullOrNone(obj[9]))
		{
			String depotName = obj[9];
			
			DepotBean depot = depotDAO.findByUnique(depotName);
			
			if (null == depot)
			{
				builder
	            .append("第[" + currentNumber + "]错误:")
	            .append("仓库" + depotName + "不存在")
	            .append("<br>");
				
				importError = true;
			}else{
				bean.setLocation(depot.getId());
			}
		}
		
		// 产品编码 (名称由编码获取）
		if ( !StringTools.isNullOrNone(obj[10]))
		{
			String name = obj[10].trim();
			
			ProductBean product = productDAO.findByUnique(name);
			
			if (null == product)
			{
				builder
	            .append("第[" + currentNumber + "]错误:")
	            .append("产品不存在")
	            .append("<br>");
				
				importError = true;
			}else{
				base.setProductId(product.getId());
				base.setProductName(product.getName());
			}
		}else{
			builder
            .append("第[" + currentNumber + "]错误:")
            .append("产品不能为空")
            .append("<br>");
			
			importError = true;
		}
		
		// 管理属性
		if ( !StringTools.isNullOrNone(obj[12]))
		{
			String name = obj[12].trim();
			
			if ("管理".equals(name))
			{
				bean.setMtype(0);
				base.setMtype(0);
			}else if ("普通".equals(name))
			{
				bean.setMtype(1);
				base.setMtype(1);
			}else{
				builder
	            .append("第[" + currentNumber + "]错误:")
	            .append("管理属性只能是[管理、普通]")
	            .append("<br>");
				
				importError = true;
			}
			
		}else{
			builder
            .append("第[" + currentNumber + "]错误:")
            .append("管理属性不能为空")
            .append("<br>");
			
			importError = true;
		}
		
		// 单位
		if ( !StringTools.isNullOrNone(obj[13]))
		{
			base.setUnit(obj[13].trim());
		}else{
			base.setUnit("套 ");
		}
		
		// 数量
		if ( !StringTools.isNullOrNone(obj[14]))
		{
			int amount = MathTools.parseInt(obj[14].trim());
			
			if (amount == 0){
				builder
	            .append("第[" + currentNumber + "]错误:")
	            .append("数量不能等于0")
	            .append("<br>");
				
				importError = true;
			}else{
				base.setAmount(amount);
				
				// 采购退货
				if (bean.getOutType() == 6)
				{
					if (amount > 0)
					{
						base.setAmount(-amount);
					}
				}else{
					if (amount < 0)
					{
						base.setAmount(-amount);
					}
				}
			}
		}
		else
		{
			builder
            .append("第[" + currentNumber + "]错误:")
            .append("数量不可为空")
            .append("<br>");
			
			importError = true;
		}

		// 单价
		if ( !StringTools.isNullOrNone(obj[15]))
		{
			double price = MathTools.parseDouble(obj[15].trim());
			
			base.setPrice(price);
			
			if (price <= 0)
			{
				builder
	            .append("第[" + currentNumber + "]错误:")
	            .append("单价须大于0")
	            .append("<br>");
				
				importError = true;
			}
		}
		else
		{
			builder
            .append("第[" + currentNumber + "]错误:")
            .append("单价不可为空")
            .append("<br>");
			
			importError = true;
		}

		// 金额
		if ( !StringTools.isNullOrNone(obj[16]))
		{
			double value = MathTools.parseDouble(obj[16].trim());
			
			base.setValue(value);
			
			if (value != (base.getAmount() * base.getPrice()))
			{
				base.setValue(base.getAmount() * base.getPrice());
			}
		}
		else
		{
			base.setValue(base.getAmount() * base.getPrice());
		}

		// 成本
		if ( !StringTools.isNullOrNone(obj[17]))
		{
			base.setCostPrice(MathTools.parseDouble(obj[17].trim()));
		}else
		{
			builder
            .append("第[" + currentNumber + "]错误:")
            .append("成本不能为空")
            .append("<br>");
			
			importError = true;
		}
		
		// 业务员结算价
		if ( !StringTools.isNullOrNone(obj[18]))
		{
			base.setInputPrice(MathTools.parseDouble(obj[18].trim()));
		}else
		{
			builder
            .append("第[" + currentNumber + "]错误:")
            .append("业务员结算价不能为空")
            .append("<br>");
			
			importError = true;
		}
		
		// 事业部结算价
		if ( !StringTools.isNullOrNone(obj[19]))
		{
			base.setIprice(MathTools.parseDouble(obj[19].trim()));
		}else
		{
			if (bean.getOutType() == OutConstant.OUTTYPE_OUT_SWATCH)
			{
				builder
	            .append("第[" + currentNumber + "]错误:")
	            .append("事业部结算价不能为空")
	            .append("<br>");
				
				importError = true;	
			}
		}
		
		// 总部结算价
		if ( !StringTools.isNullOrNone(obj[20]))
		{
			base.setPprice(MathTools.parseDouble(obj[20].trim()));
		}else{
			if (bean.getOutType() == OutConstant.OUTTYPE_OUT_SWATCH)
			{
				builder
	            .append("第[" + currentNumber + "]错误:")
	            .append("总部结算价不能为空")
	            .append("<br>");
				
				importError = true;	
			}
		}
		
		if (bean.getType() == 0)
		{
			bean.setPay(1);
			bean.setHadPay(base.getValue());
			bean.setReserve3(2);
		}
		
		base.setLocationId(bean.getLocation());
		base.setOwner("0");

		bean.setLocationId("99");
		bean.setDestinationId(bean.getLocation());
		bean.setTotal(base.getValue());
		bean.setStatus(OutConstant.STATUS_PASS);
		bean.setChangeTime(bean.getOutTime() + " 14:03:20");
		
		return importError;
	}

	/**
	 * @return the unitViewDAO
	 */
	public UnitViewDAO getUnitViewDAO()
	{
		return unitViewDAO;
	}

	/**
	 * @param unitViewDAO the unitViewDAO to set
	 */
	public void setUnitViewDAO(UnitViewDAO unitViewDAO)
	{
		this.unitViewDAO = unitViewDAO;
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
	 * @return the depotDAO
	 */
	public DepotDAO getDepotDAO()
	{
		return depotDAO;
	}

	/**
	 * @param depotDAO the depotDAO to set
	 */
	public void setDepotDAO(DepotDAO depotDAO)
	{
		this.depotDAO = depotDAO;
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
	 * @return the vmOutManager
	 */
	public VMOutManager getVmOutManager()
	{
		return vmOutManager;
	}

	/**
	 * @param vmOutManager the vmOutManager to set
	 */
	public void setVmOutManager(VMOutManager vmOutManager)
	{
		this.vmOutManager = vmOutManager;
	}
}
