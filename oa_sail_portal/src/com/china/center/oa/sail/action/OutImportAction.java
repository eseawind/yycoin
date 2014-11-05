package com.china.center.oa.sail.action;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
import com.center.china.osgi.publics.file.writer.WriteFile;
import com.center.china.osgi.publics.file.writer.WriteFileFactory;
import com.china.center.actionhelper.common.ActionTools;
import com.china.center.actionhelper.common.JSONPageSeparateTools;
import com.china.center.actionhelper.common.JSONTools;
import com.china.center.actionhelper.common.KeyConstant;
import com.china.center.actionhelper.json.AjaxResult;
import com.china.center.common.MYException;
import com.china.center.jdbc.util.ConditionParse;
import com.china.center.jdbc.util.PageSeparate;
import com.china.center.oa.client.bean.CiticBranchBean;
import com.china.center.oa.client.bean.CiticVSStafferBean;
import com.china.center.oa.client.bean.CustomerBean;
import com.china.center.oa.client.dao.CiticBranchDAO;
import com.china.center.oa.client.dao.CiticVSStafferDAO;
import com.china.center.oa.client.dao.CustomerMainDAO;
import com.china.center.oa.client.dao.StafferVSCustomerDAO;
import com.china.center.oa.client.vs.StafferVSCustomerBean;
import com.china.center.oa.product.bean.CiticVSOAProductBean;
import com.china.center.oa.product.bean.DepotBean;
import com.china.center.oa.product.bean.DepotpartBean;
import com.china.center.oa.product.bean.ProductBean;
import com.china.center.oa.product.dao.CiticVSOAProductDAO;
import com.china.center.oa.product.dao.DepotDAO;
import com.china.center.oa.product.dao.DepotpartDAO;
import com.china.center.oa.product.dao.ProductDAO;
import com.china.center.oa.product.manager.StorageRelationManager;
import com.china.center.oa.publics.Helper;
import com.china.center.oa.publics.bean.CityBean;
import com.china.center.oa.publics.bean.ProvinceBean;
import com.china.center.oa.publics.bean.StafferBean;
import com.china.center.oa.publics.dao.AreaDAO;
import com.china.center.oa.publics.dao.CityDAO;
import com.china.center.oa.publics.dao.ProvinceDAO;
import com.china.center.oa.publics.dao.StafferDAO;
import com.china.center.oa.sail.bean.BankSailBean;
import com.china.center.oa.sail.bean.BaseBean;
import com.china.center.oa.sail.bean.BatchApproveBean;
import com.china.center.oa.sail.bean.BatchSwatchBean;
import com.china.center.oa.sail.bean.ConsignBean;
import com.china.center.oa.sail.bean.DistributionBean;
import com.china.center.oa.sail.bean.EstimateProfitBean;
import com.china.center.oa.sail.bean.ExpressBean;
import com.china.center.oa.sail.bean.OutBean;
import com.china.center.oa.sail.bean.OutImportBean;
import com.china.center.oa.sail.bean.OutImportLogBean;
import com.china.center.oa.sail.bean.ReplenishmentBean;
import com.china.center.oa.sail.bean.TransportBean;
import com.china.center.oa.sail.constanst.OutConstant;
import com.china.center.oa.sail.constanst.OutImportConstant;
import com.china.center.oa.sail.dao.BankSailDAO;
import com.china.center.oa.sail.dao.BaseDAO;
import com.china.center.oa.sail.dao.BatchApproveDAO;
import com.china.center.oa.sail.dao.BatchSwatchDAO;
import com.china.center.oa.sail.dao.ConsignDAO;
import com.china.center.oa.sail.dao.EstimateProfitDAO;
import com.china.center.oa.sail.dao.ExpressDAO;
import com.china.center.oa.sail.dao.OutDAO;
import com.china.center.oa.sail.dao.OutImportDAO;
import com.china.center.oa.sail.dao.OutImportLogDAO;
import com.china.center.oa.sail.dao.OutImportResultDAO;
import com.china.center.oa.sail.dao.ReplenishmentDAO;
import com.china.center.oa.sail.helper.OutImportHelper;
import com.china.center.oa.sail.manager.OutImportManager;
import com.china.center.oa.sail.vo.OutImportVO;
import com.china.center.tools.CommonTools;
import com.china.center.tools.ListTools;
import com.china.center.tools.MathTools;
import com.china.center.tools.RequestDataStream;
import com.china.center.tools.RequestTools;
import com.china.center.tools.StringTools;
import com.china.center.tools.TimeTools;
import com.china.center.tools.WriteFileBuffer;

/**
 * 
 * 中信订单处理
 *
 * @author fangliwen 2013-10-15
 */
public class OutImportAction extends DispatchAction
{
	private final Log _logger = LogFactory.getLog(getClass());
	
	private CustomerMainDAO customerMainDAO = null;
	
	private OutImportDAO outImportDAO = null;
	
	private OutImportManager outImportManager = null;
	
    private OutImportResultDAO outImportResultDAO  = null;
    
    private ReplenishmentDAO replenishmentDAO  = null;
    
	private StorageRelationManager storageRelationManager = null;
	
	private CiticVSOAProductDAO citicVSOAProductDAO = null;
	
	private ProductDAO productDAO = null;
	
	private OutImportLogDAO outImportLogDAO = null;
	
	private StafferDAO stafferDAO = null;
	
	private StafferVSCustomerDAO stafferVSCustomerDAO = null;
	
	private CiticVSStafferDAO citicVSStafferDAO = null;
	
	private CiticBranchDAO citicBranchDAO = null;
	
	private ExpressDAO expressDAO = null;
	
	private BatchApproveDAO batchApproveDAO = null;
	
	private BatchSwatchDAO batchSwatchDAO = null;
	
	private ConsignDAO consignDAO = null;
	
	private DepotDAO depotDAO = null;
	
	private DepotpartDAO depotpartDAO = null;
	
	private OutDAO outDAO = null;
	
	private BaseDAO baseDAO = null;
	
	private ProvinceDAO provinceDAO = null;
	
	private CityDAO cityDAO = null;
	
	private AreaDAO areaDAO = null;
	
	private BankSailDAO bankSailDAO = null;
	
	private EstimateProfitDAO estimateProfitDAO = null;
	
	private static String QUERYOUTIMPORT = "queryOutImport";
	
	private static String QUERYOUTIMPORTLOG = "queryOutImportLog";
	
	private static String QUERYBANKSAIL = "queryBankSail";
	
	public OutImportAction()
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
    public ActionForward importOut(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
	throws ServletException
	{
    	User user = Helper.getUser(request);
    	
        RequestDataStream rds = new RequestDataStream(request);
        
        boolean importError = false;
        
        List<OutImportBean> importItemList = new ArrayList<OutImportBean>(); 
        
        StringBuilder builder = new StringBuilder();
        
        try
        {
            rds.parser();
        }
        catch (Exception e1)
        {
            _logger.error(e1, e1);

            request.setAttribute(KeyConstant.ERROR_MESSAGE, "解析失败");

            return mapping.findForward("importOut");
        }

        if ( !rds.haveStream())
        {
            request.setAttribute(KeyConstant.ERROR_MESSAGE, "解析失败");

            return mapping.findForward("importOut");
        }
        
        String itype = rds.getParameter("type"); 
        
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
                	OutImportBean bean = new OutImportBean();
                    
                	boolean error = innerAdd(bean, obj, builder, currentNumber);
                	
                	if (!importError)
                	{
                		importError = error;
                	}
                    
                    bean.setLogTime(TimeTools.now());
                    
                    bean.setItype(MathTools.parseInt(itype));
                    
                    // 操作人
                    bean.setReason(user.getStafferId());
                    
                    importItemList.add(bean);
                    
                }
                else
                {
                    builder
                        .append("第[" + currentNumber + "]错误:")
                        .append("数据长度不足32格错误")
                        .append("<br>");
                    
                    importError = true;
                }
            }
            
            
        }catch (Exception e)
        {
            _logger.error(e, e);
            
            request.setAttribute(KeyConstant.ERROR_MESSAGE, e.toString());

            return mapping.findForward("importOut");
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

            return mapping.findForward("importOut");
        }
        
        String batchId = "";
        
        try
        {
        	batchId = outImportManager.addBean(importItemList);
        }
        catch(MYException e)
        {
            request.setAttribute(KeyConstant.ERROR_MESSAGE, "导入出错:"+ e.getErrorContent());

            return mapping.findForward("importOut");
        }
        
        // 异步处理 - 只针对初始或失败的行项目
        List<OutImportBean> list = outImportDAO.queryEntityBeansByFK(batchId);
        
        if (!ListTools.isEmptyOrNull(list))
        {
        	outImportManager.processAsyn(list);
        }
        
        return mapping.findForward("queryOutImport");
	}

    /**
     * 
     * @param obj
     * @throws MYException
     */
	private boolean innerAdd(OutImportBean bean, String[] obj, StringBuilder builder, int currentNumber)
	{
		boolean importError = false;
		
		// 分行名称 （相当于客户）
		if ( !StringTools.isNullOrNone(obj[0]))
		{
			bean.setBranchName(obj[0]);
		}

		// 二级分行名称
		if ( !StringTools.isNullOrNone(obj[1]))
		{
			bean.setSecondBranch(obj[1]);
		}

		// 联行网点号
		if ( !StringTools.isNullOrNone(obj[2]))
		{
			bean.setComunicationBranch(obj[2]);
		}
		
		// 订单类型
		if ( !StringTools.isNullOrNone(obj[29]))
		{
			String outType = obj[29];
			
			String [] outTypes = OutImportConstant.outTypesArr;
			
			for(int i = 0; i< outTypes.length; i++)
			{
				if (outType.equals(outTypes[i]))
				{
					bean.setOutType(OutImportConstant.outTypeiArr[i]);
					
					break;
				}
			}
			
			if (bean.getOutType() == -1)
			{
				builder
                .append("第[" + currentNumber + "]错误:")
                .append("订单类型不存在")
                .append("<br>");
				
				importError = true;
			}
		}else
		{
			builder
            .append("第[" + currentNumber + "]错误:")
            .append("订单类型不能为空")
            .append("<br>");
			
			importError = true;
		}
		
		// 网点名称 - 永银的客户,不做强制要求事先须存在，没有实时创建一条 -- 20131125 修改为客户须事先存在
		// comunicatonBranchName
		if ( !StringTools.isNullOrNone(obj[3]))
		{
			String custName = obj[3].trim();

			CustomerBean cBean = customerMainDAO.findByUnique(custName);

			if (null == cBean)
			{
				builder
	            .append("第[" + currentNumber + "]错误:")
	            .append("网点名称（客户）" + custName + "不存在")
	            .append("<br>");
				
				importError = true;
			}else{
				if (bean.getOutType() != OutConstant.OUTTYPE_OUT_SWATCH)
				{
					StafferVSCustomerBean vsBean = stafferVSCustomerDAO.findByUnique(cBean.getId());
					
					if (null == vsBean)
					{
						builder
			            .append("第[" + currentNumber + "]错误:")
			            .append("网点名称（客户）" + custName + "没有与业务员挂靠关系")
			            .append("<br>");
						
						importError = true;
					}else{
						bean.setComunicatonBranchName(custName);
					}
				}else{
					bean.setComunicatonBranchName("公共客户");
				}
			}
		}
		else
		{
			builder
            .append("第[" + currentNumber + "]错误:")
            .append("网点名称不能为空")
            .append("<br>");
			
			importError = true;
		}

		// 商品编码
		if ( !StringTools.isNullOrNone(obj[4]))
		{
			String code = obj[4].trim();
			
			bean.setProductCode(code);
			
			// 姓氏
			if ( !StringTools.isNullOrNone(obj[26]))
			{
				bean.setFirstName(obj[26]);
			}
			else
				bean.setFirstName("N/A");
			
			// 根据产品映射关系获取OA产品编码
			CiticVSOAProductBean vsbean = citicVSOAProductDAO.findByUnique(code, bean.getFirstName());
			
			if (null == vsbean)
			{
				builder
	            .append("第[" + currentNumber + "]错误:")
	            .append("没有配置产品编码为：" + code + "-" + bean.getFirstName() + " 对应的OA产品编码映射关系" )
	            .append("<br>");
				
				importError = true;
			}else{
				ProductBean pbean = productDAO.findByUnique(vsbean.getProductCode());
				
				if (null == pbean)
				{
					builder
		            .append("第[" + currentNumber + "]错误:")
		            .append("产品编码" + vsbean.getProductCode() + "的产品不存在,请创建")
		            .append("<br>");
					
					importError = true;
				}else{
					bean.setProductId(pbean.getId());
					
					bean.setProductName(pbean.getName());
				}
			}
		}
		else
		{
			builder
            .append("第[" + currentNumber + "]错误:")
            .append("产品不可为空")
            .append("<br>");
			
			importError = true;
		}

		// 数量
		if ( !StringTools.isNullOrNone(obj[6]))
		{
			int amount = MathTools.parseInt(obj[6]);
			
			if (amount <= 0){
				builder
	            .append("第[" + currentNumber + "]错误:")
	            .append("数量须大于0")
	            .append("<br>");
				
				importError = true;
			}else{
				bean.setAmount(amount);
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
		if ( !StringTools.isNullOrNone(obj[7]))
		{
			double price = MathTools.parseDouble(obj[7]);
			
			bean.setPrice(price);
			
			if (bean.getOutType() != OutConstant.OUTTYPE_OUT_PRESENT){
				
				if (price <= 0)
				{
					builder
		            .append("第[" + currentNumber + "]错误:")
		            .append("非赠送类型时，单价须大于0")
		            .append("<br>");
					
					importError = true;
				}
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

		// 规格
		if ( !StringTools.isNullOrNone(obj[8]))
		{
			bean.setStyle(obj[8]);
		}
		
		// 金额
		if ( !StringTools.isNullOrNone(obj[9]))
		{
			double value = MathTools.parseDouble(obj[9].trim());
			
			bean.setValue(value);
			
			if (value != (bean.getAmount() * bean.getPrice()))
			{
				bean.setValue(bean.getAmount() * bean.getPrice());
			}
		}
		else
		{
			bean.setValue(bean.getAmount() * bean.getPrice());
		}

		// 中收金额
		if ( !StringTools.isNullOrNone(obj[10]))
		{
			String value = obj[10].trim();
			
			if (value.equals("无")) {
				bean.setMidValue(0);
			} else {
				double midValue = MathTools.parseDouble(value);
				
				if (midValue <= 0) {
					builder
		            .append("第[" + currentNumber + "]错误:")
		            .append("中收为0请用'无'代替")
		            .append("<br>");
					
					importError = true;
				} else {
					bean.setMidValue(midValue);
				}
			}
		} else {
			builder
            .append("第[" + currentNumber + "]错误:")
            .append("中收不能为空，若没有，请用'无'代替")
            .append("<br>");
			
			importError = true;
		}

		// 计划交付日期
		if ( !StringTools.isNullOrNone(obj[11]))
		{
			bean.setArriveDate(obj[11]);
		}
		else
		{
			builder
            .append("第[" + currentNumber + "]错误:")
            .append("计划交付日期不能为空")
            .append("<br>");
			
			importError = true;
		}
		
		// 库存类型
		if ( !StringTools.isNullOrNone(obj[12]))
		{
			int storageType = OutImportHelper.getStorageType(obj[12]);
			
			bean.setStorageType(storageType);
		}

		// 中信订单号
		if ( !StringTools.isNullOrNone(obj[13]))
		{
			bean.setCiticNo(obj[13]);
		}
		else
		{
			builder
            .append("第[" + currentNumber + "]错误:")
            .append("中信银行订单号不能为空")
            .append("<br>");
			
			importError = true;
		}

		// 开票性质
		if ( !StringTools.isNullOrNone(obj[14]))
		{
			int invoiceNature = OutImportHelper.getInvoiceNature(obj[14]);
			
			bean.setInvoiceNature(invoiceNature);
		}
		
		// 开票抬头	
		if ( !StringTools.isNullOrNone(obj[15]))
		{
			bean.setInvoiceHead(obj[15]);
		}

		// 开票要求	
		if ( !StringTools.isNullOrNone(obj[16]))
		{
			bean.setInvoiceCondition(obj[16]);
		}

		// 绑定单号	
		if ( !StringTools.isNullOrNone(obj[17]))
		{
			bean.setBindNo(obj[17]);
		}
		
		// 开票类型	
		if ( !StringTools.isNullOrNone(obj[18]))
		{
			int invoiceType = OutImportHelper.getInvoiceType(obj[18]);
			
			bean.setInvoiceType(invoiceType);
		}

		// 开票品名	
		if ( !StringTools.isNullOrNone(obj[19]))
		{
			bean.setInvoiceName(obj[19]);
		}

		// 开票金额	
		if ( !StringTools.isNullOrNone(obj[20]))
		{
			double invoiceMoney = MathTools.parseDouble(obj[20]);
			
			bean.setInvoiceMoney(invoiceMoney);
		}
		
		// 中信订单日期
		if ( !StringTools.isNullOrNone(obj[27]))
		{
			String name = obj[27].trim();
			
			String eL = "[0-9]{4}-[0-9]{2}-[0-9]{2}";
			Pattern p = Pattern.compile(eL);
			Matcher m = p.matcher(name);
			boolean dateFlag = m.matches();
			if (!dateFlag) {
				builder
                .append("第[" + currentNumber + "]错误:")
                .append("中信订单日期格式错误，如 2000-01-01")
                .append("<br>");
            
    			importError = true;
			}else{
				bean.setCiticOrderDate(name);
			}
		}
		
		// 仓库
		if ( !StringTools.isNullOrNone(obj[30]))
		{
			String depotName = obj[30];
			
			DepotBean depot = depotDAO.findByUnique(depotName);
			
			if (null == depot)
			{
				builder
	            .append("第[" + currentNumber + "]错误:")
	            .append("仓库" + depotName + "不存在")
	            .append("<br>");
				
				importError = true;
			}else{
				bean.setDepotId(depot.getId());
			}
		}else
		{
			builder
            .append("第[" + currentNumber + "]错误:")
            .append("仓库不能为空")
            .append("<br>");
			
			importError = true;
		}
		
		// 仓区
		if ( !StringTools.isNullOrNone(obj[31]))
		{
			String depotpartName = obj[31].trim();
			
			DepotpartBean depotpart = depotpartDAO.findByUnique(depotpartName);
			
			if (null == depotpart)
			{
				builder
	            .append("第[" + currentNumber + "]错误:")
	            .append("仓区[" + depotpartName + "]不存在")
	            .append("<br>");
				
				importError = true;
			}else{
				if (!depotpart.getLocationId().equals(bean.getDepotId()))
				{
					builder
		            .append("第[" + currentNumber + "]错误:")
		            .append("仓区[" +depotpartName + "]对应的仓库不是导入中的仓库")
		            .append("<br>");
					
					importError = true;
				}else
				{
					bean.setDepotpartId(depotpart.getId());
					bean.setComunicationBranch(depotpart.getName());
				}
			}
		}else
		{
			builder
            .append("第[" + currentNumber + "]错误:")
            .append("仓区不能为空")
            .append("<br>");
			
			importError = true;
		}
		
		// 职员 - 领样类型时 必须
		if ( !StringTools.isNullOrNone(obj[32]))
		{
			String name = obj[32].trim();
			
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
			}
		}else
		{
			if (bean.getOutType() == OutConstant.OUTTYPE_OUT_SWATCH)
			{
				builder
	            .append("第[" + currentNumber + "]错误:")
	            .append("领样类型库单时职员不能为空")
	            .append("<br>");
				
				importError = true;	
			}
		}
		
		// 备注
		if ( !StringTools.isNullOrNone(obj[33]))
		{
			bean.setDescription(obj[33]);
		}
		
		// 发货方式
		if ( !StringTools.isNullOrNone(obj[34]))
		{
			boolean has = false;
			
			String shipping = obj[34].trim();
			
			for (int i = 0 ; i < OutImportConstant.shipping.length; i++)
			{
				if (OutImportConstant.shipping[i].equals(shipping))
				{
					has = true;
					
					bean.setShipping(OutImportConstant.ishipping[i]);
					
					break;
				}
			}
			
			if (!has)
			{
				builder
	            .append("第[" + currentNumber + "]错误:")
	            .append("发货方式不对,须为[自提,公司第三方快递,第三方货运,第三方快递+货运,空发]中之一")
	            .append("<br>");
				
				importError = true;
			}
		}else
		{
			builder
            .append("第[" + currentNumber + "]错误:")
            .append("发货方式不能为空,须为[自提,公司第三方快递,第三方货运,第三方快递+货运,空发]中之一")
            .append("<br>");
			
			importError = true;
		}
		
		if (bean.getShipping() == 2 || bean.getShipping() == 4)
		{
			// 如果发货方式是快递或快递+货运 ,则快递须为必填
			if ( !StringTools.isNullOrNone(obj[35]))
			{
				String transport1 = obj[35].trim();
				
				ExpressBean express = expressDAO.findByUnique(transport1);
				
				if (null == express)
				{
					builder
		            .append("第[" + currentNumber + "]错误:")
		            .append("快递方式"+ transport1 +"不存在")
		            .append("<br>");
					
					importError = true;
				}else{
					bean.setTransport1(MathTools.parseInt(express.getId()));
				}
			}else
			{
				builder
	            .append("第[" + currentNumber + "]错误:")
	            .append("快递方式不能为空")
	            .append("<br>");
				
				importError = true;
			}
			
			// 快递支付方式也不能为空
			if ( !StringTools.isNullOrNone(obj[36]))
			{
				String expressPay = obj[36].trim();
				
				boolean isexists = false;
				
				for (int i = 0; i < OutImportConstant.expressPay.length; i++)
				{
					if (expressPay.equals(OutImportConstant.expressPay[i]))
					{
						isexists = true;
						
						bean.setExpressPay(OutImportConstant.iexpressPay[i]);
						
						break;
					}
				}
				
				if (!isexists)
				{
					builder
		            .append("第[" + currentNumber + "]错误:")
		            .append("快递支付方式不存在,只能为[业务员支付,公司支付,客户支付]之一")
		            .append("<br>");
					
					importError = true;
				}
			}else
			{
				builder
	            .append("第[" + currentNumber + "]错误:")
	            .append("快递支付方式不能为空,只能为[业务员支付,公司支付,客户支付]之一")
	            .append("<br>");
				
				importError = true;
			}
		}
		
		if (bean.getShipping() == 3 || bean.getShipping() == 4)
		{
			// 如果发货方式是快递或快递+货运 ,则快递须为必填
			if ( !StringTools.isNullOrNone(obj[37]))
			{
				String transport1 = obj[37].trim();
				
				ExpressBean express = expressDAO.findByUnique(transport1);
				
				if (null == express)
				{
					builder
		            .append("第[" + currentNumber + "]错误:")
		            .append("货运方式"+ transport1 +"不存在")
		            .append("<br>");
					
					importError = true;
				}else{
					bean.setTransport2(MathTools.parseInt(express.getId()));
				}
			}else
			{
				builder
	            .append("第[" + currentNumber + "]错误:")
	            .append("货运方式不能为空")
	            .append("<br>");
				
				importError = true;
			}
			
			// 快递支付方式也不能为空
			if ( !StringTools.isNullOrNone(obj[38]))
			{
				String expressPay = obj[38].trim();
				
				boolean isexists = false;
				
				for (int i = 0; i < OutImportConstant.expressPay.length; i++)
				{
					if (expressPay.equals(OutImportConstant.expressPay[i]))
					{
						isexists = true;
						
						bean.setTransportPay(OutImportConstant.iexpressPay[i]);
						
						break;
					}
				}
				
				if (!isexists)
				{
					builder
		            .append("第[" + currentNumber + "]错误:")
		            .append("货运支付方式不存在,只能为[业务员支付,公司支付,客户支付]之一")
		            .append("<br>");
					
					importError = true;
				}
			}else
			{
				builder
	            .append("第[" + currentNumber + "]错误:")
	            .append("货运支付方式不能为空,只能为[业务员支付,公司支付,客户支付]之一")
	            .append("<br>");
				
				importError = true;
			}
		}
		
		// 省	
		if ( !StringTools.isNullOrNone(obj[21]))
		{
			String name = obj[21].trim();
			
			ProvinceBean province = provinceDAO.findByUnique(name);
			
			if (null != province)
				bean.setProvinceId(province.getId());
			else {
				if (bean.getShipping() == 2 || bean.getShipping() == 3 || bean.getShipping() == 4) {
					builder
		            .append("第[" + currentNumber + "]错误:")
		            .append("快递、货运时省不存在")
		            .append("<br>");
					
					importError = true;
				}
			}
		} else {
			if (bean.getShipping() == 2 || bean.getShipping() == 3 || bean.getShipping() == 4) {
				builder
	            .append("第[" + currentNumber + "]错误:")
	            .append("快递、货运时省不能为空")
	            .append("<br>");
				
				importError = true;
			}
		}

		// 市	
		if ( !StringTools.isNullOrNone(obj[22]))
		{
			String name = obj[22].trim();
			
			CityBean city = cityDAO.findByUnique(name);
			
			if (null != city)
				bean.setCityId(city.getId());
			else {
				if (bean.getShipping() == 2 || bean.getShipping() == 3 || bean.getShipping() == 4) {
					builder
		            .append("第[" + currentNumber + "]错误:")
		            .append("快递、货运时市不存在")
		            .append("<br>");
					
					importError = true;
				}
			}
		} else {
			if (bean.getShipping() == 2 || bean.getShipping() == 3 || bean.getShipping() == 4) {
				builder
	            .append("第[" + currentNumber + "]错误:")
	            .append("快递、货运时市不能为空")
	            .append("<br>");
				
				importError = true;
			}
		}

		// 详细地址	
		if ( !StringTools.isNullOrNone(obj[23]))
		{
			bean.setAddress(obj[23].trim());
			
			if (bean.getShipping() == 2 || bean.getShipping() == 3 || bean.getShipping() == 4) {
				if (bean.getAddress().length() < 5) {
					builder
		            .append("第[" + currentNumber + "]错误:")
		            .append("快递、货运时详细地址不能少于5个字")
		            .append("<br>");
					
					importError = true;
				}
			}
			
		} else {
			if (bean.getShipping() == 2 || bean.getShipping() == 3 || bean.getShipping() == 4) {
				builder
	            .append("第[" + currentNumber + "]错误:")
	            .append("快递、货运时详细地址不能为空")
	            .append("<br>");
				
				importError = true;
			}
		}
		
		// 收货人	
		if ( !StringTools.isNullOrNone(obj[24]))
		{
			bean.setReceiver(obj[24].trim());
			
			if (bean.getShipping() == 2 || bean.getShipping() == 3 || bean.getShipping() == 4) {
				if (bean.getReceiver().length() < 2) {
					builder
		            .append("第[" + currentNumber + "]错误:")
		            .append("快递、货运时收货人不能少于2个字")
		            .append("<br>");
					
					importError = true;
				}
			}
		} else {
			if (bean.getShipping() == 2 || bean.getShipping() == 3 || bean.getShipping() == 4) {
				builder
	            .append("第[" + currentNumber + "]错误:")
	            .append("快递、货运时收货人不能为空")
	            .append("<br>");
				
				importError = true;
			}
		}

		// 收货人手机
		if ( !StringTools.isNullOrNone(obj[25]))
		{
			bean.setHandPhone(obj[25].trim());
			
			if (bean.getShipping() == 2 || bean.getShipping() == 3 || bean.getShipping() == 4) {
				if (bean.getHandPhone().length() < 11) {
					builder
		            .append("第[" + currentNumber + "]错误:")
		            .append("快递、货运时收货人手机不能少于11位")
		            .append("<br>");
					
					importError = true;
				}
			}
		} else {
			if (bean.getShipping() == 2 || bean.getShipping() == 3 || bean.getShipping() == 4) {
				builder
	            .append("第[" + currentNumber + "]错误:")
	            .append("快递、货运时收货人手机不能为空")
	            .append("<br>");
				
				importError = true;
			}
		}
		
		// 回款天数
		if ( !StringTools.isNullOrNone(obj[39]))
		{
			bean.setReday(MathTools.parseInt(obj[39].trim()));
		}else{
			bean.setReday(OutImportConstant.CITIC_REDAY);
		}
		
		if (bean.getOutType() == OutConstant.OUTTYPE_OUT_PRESENT) {
			if ( !StringTools.isNullOrNone(obj[40]))
			{
				String presentFlag = obj[40].trim();
				
				String [] presentFlags = OutImportConstant.outPresentTypesArr;
				
				for(int i = 0; i< presentFlags.length; i++)
				{
					if (presentFlag.equals(presentFlags[i]))
					{
						bean.setPresentFlag(OutImportConstant.outPresentTypeiArr[i]);
						
						break;
					}
				}
				
				if (bean.getPresentFlag() == 0)
				{
					builder
	                .append("第[" + currentNumber + "]错误:")
	                .append("赠送类型不存在")
	                .append("<br>");
					
					importError = true;
				}
			}else{
				builder
	            .append("第[" + currentNumber + "]错误:")
	            .append("赠送单据时赠送类型不能为空")
	            .append("<br>");
				
				importError = true;
			}
		}
		
		return importError;
	}
    
	/**
	 * importOutForPufa 
	 * 浦发销售导入
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 */
	public ActionForward importOutForPufa(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
	throws ServletException
	{
		User user = Helper.getUser(request);
		
        RequestDataStream rds = new RequestDataStream(request);
        
        boolean importError = false;
        
        List<OutImportBean> importItemList = new ArrayList<OutImportBean>(); 
        
        StringBuilder builder = new StringBuilder();
        
        try
        {
            rds.parser();
        }
        catch (Exception e1)
        {
            _logger.error(e1, e1);

            request.setAttribute(KeyConstant.ERROR_MESSAGE, "解析失败");

            return mapping.findForward("importOut");
        }

        if ( !rds.haveStream())
        {
            request.setAttribute(KeyConstant.ERROR_MESSAGE, "解析失败");

            return mapping.findForward("importOut");
        }
        
        String itype = rds.getParameter("type"); 
        
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
                	OutImportBean bean = new OutImportBean();
                    
                	boolean error = innerAddForPufa(bean, obj, builder, currentNumber);
                    
                	if (!importError)
                	{
                		importError = error;
                	}
                	
                    bean.setLogTime(TimeTools.now());
                    
                    bean.setItype(MathTools.parseInt(itype));
                    
                    bean.setReason(user.getStafferId());
                    
                    importItemList.add(bean);
                    
                }
                else
                {
                    builder
                        .append("第[" + currentNumber + "]错误:")
                        .append("数据长度不足26格错误")
                        .append("<br>");
                    
                    importError = true;
                }
            }
            
            
        }catch (Exception e)
        {
            _logger.error(e, e);
            
            request.setAttribute(KeyConstant.ERROR_MESSAGE, e.toString());

            return mapping.findForward("importOut");
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

            return mapping.findForward("importOut");
        }
        
        String batchId = "";
        
        try
        {
        	batchId = outImportManager.addPufaBean(importItemList);
        }
        catch(MYException e)
        {
            request.setAttribute(KeyConstant.ERROR_MESSAGE, "导入出错:"+ e.getErrorContent());

            return mapping.findForward("importOut");
        }
        
        // 异步处理 - 只针对初始或失败的行项目
        List<OutImportBean> list = outImportDAO.queryEntityBeansByFK(batchId);
        
        if (!ListTools.isEmptyOrNull(list))
        {
        	outImportManager.processAsyn(list);
        }
        
        return mapping.findForward("queryOutImport");
	}
	
	private boolean innerAddForPufa(OutImportBean bean, String[] obj, StringBuilder builder, int currentNumber)
	{
		boolean importError = false;
		
		// 分行名称 （相当于客户）
		if ( !StringTools.isNullOrNone(obj[0]))
		{
			bean.setBranchName(obj[0]);
		}

		// 二级分行名称
		if ( !StringTools.isNullOrNone(obj[1]))
		{
			bean.setSecondBranch(obj[1]);
		}

		// 联行网点号
		if ( !StringTools.isNullOrNone(obj[2]))
		{
			bean.setComunicationBranch(obj[2]);
		}
		
		// 订单类型
		if ( !StringTools.isNullOrNone(obj[29]))
		{
			String outType = obj[29];
			
			String [] outTypes = OutImportConstant.outTypesArr;
			
			for(int i = 0; i< outTypes.length; i++)
			{
				if (outType.equals(outTypes[i]))
				{
					bean.setOutType(OutImportConstant.outTypeiArr[i]);
					
					break;
				}
			}
			
			if (bean.getOutType() == -1)
			{
				builder
                .append("第[" + currentNumber + "]错误:")
                .append("订单类型不存在")
                .append("<br>");
				
				importError = true;
			}
		}else
		{
			builder
            .append("第[" + currentNumber + "]错误:")
            .append("订单类型不能为空")
            .append("<br>");
			
			importError = true;
		}
		
		// 网点名称 - 永银的客户,不做强制要求事先须存在，没有不能进行
		if ( !StringTools.isNullOrNone(obj[3]))
		{
			String custName = obj[3].trim();
			
			CustomerBean cBean = customerMainDAO.findByUnique(custName);
			
			if (null == cBean)
			{
				builder
	            .append("第[" + currentNumber + "]错误:")
	            .append("网点名称（客户）" + custName + "不存在")
	            .append("<br>");
				
				importError = true;
			}else{
				if (bean.getOutType() != OutConstant.OUTTYPE_OUT_SWATCH)
				{
					StafferVSCustomerBean vsBean = stafferVSCustomerDAO.findByUnique(cBean.getId());
					
					if (null == vsBean)
					{
						builder
			            .append("第[" + currentNumber + "]错误:")
			            .append("网点名称（客户）" + custName + "没有与业务员挂靠关系")
			            .append("<br>");
						
						importError = true;
					}else{
						bean.setComunicatonBranchName(custName);
					}
				}else{
					bean.setComunicatonBranchName("公共客户");
				}
			}
		}
		else
		{
			builder
            .append("第[" + currentNumber + "]错误:")
            .append("网点名称不能为空")
            .append("<br>");
			
			importError = true;
		}

		// 商品名称
		if ( !StringTools.isNullOrNone(obj[5]))
		{
			String name = obj[5].trim();
			
			bean.setProductCode("");
			
			// 姓氏
			bean.setFirstName("N/A");
			
			ProductBean pbean = productDAO.findByName(name);
			
			if (null == pbean)
			{
				builder
	            .append("第[" + currentNumber + "]错误:")
	            .append("产品["+name+"]的产品不存在,请创建")
	            .append("<br>");
				
				importError = true;
			}else{
				bean.setProductId(pbean.getId());
				
				bean.setProductName(pbean.getName());
			}
		}
		else
		{
			builder
            .append("第[" + currentNumber + "]错误:")
            .append("产品不可为空")
            .append("<br>");
			
			importError = true;
		}

		// 数量
		if ( !StringTools.isNullOrNone(obj[6]))
		{
			int amount = MathTools.parseInt(obj[6]);
			
			if (amount <= 0){
				builder
	            .append("第[" + currentNumber + "]错误:")
	            .append("数量须大于0")
	            .append("<br>");
				
				importError = true;
			}else{
				bean.setAmount(amount);
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
		if ( !StringTools.isNullOrNone(obj[7]))
		{
			double price = MathTools.parseDouble(obj[7].trim());
			
			bean.setPrice(price);
			
			if (bean.getOutType() != OutConstant.OUTTYPE_OUT_PRESENT){
				
				if (price <= 0)
				{
					builder
		            .append("第[" + currentNumber + "]错误:")
		            .append("非赠送类型时，单价须大于0")
		            .append("<br>");
					
					importError = true;
				}
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

		// 规格
		if ( !StringTools.isNullOrNone(obj[8]))
		{
			bean.setStyle(obj[8]);
		}
		
		// 金额
		if ( !StringTools.isNullOrNone(obj[9]))
		{
			double value = MathTools.parseDouble(obj[9].trim());
			
			bean.setValue(value);
			
			if (value != (bean.getAmount() * bean.getPrice()))
			{
				bean.setValue(bean.getAmount() * bean.getPrice());
			}
		}
		else
		{
			bean.setValue(bean.getAmount() * bean.getPrice());
		}

		// 中收金额
		if ( !StringTools.isNullOrNone(obj[10]))
		{
			String value = obj[10].trim();
			
			if (value.equals("无")) {
				bean.setMidValue(0);
			} else {
				double midValue = MathTools.parseDouble(value);
				
				if (midValue <= 0) {
					builder
		            .append("第[" + currentNumber + "]错误:")
		            .append("中收为0请用'无'代替")
		            .append("<br>");
					
					importError = true;
				} else {
					bean.setMidValue(midValue);
				}
			}
		} else {
			builder
            .append("第[" + currentNumber + "]错误:")
            .append("中收不能为空，若没有，请用'无'代替")
            .append("<br>");
			
			importError = true;
		}

		// 计划交付日期
		if ( !StringTools.isNullOrNone(obj[11]))
		{
			bean.setArriveDate(obj[11]);
		}
		else
		{
			builder
            .append("第[" + currentNumber + "]错误:")
            .append("计划交付日期不能为空")
            .append("<br>");
			
			importError = true;
		}
		
		// 库存类型
		if ( !StringTools.isNullOrNone(obj[12]))
		{
			int storageType = OutImportHelper.getStorageType(obj[12]);
			
			bean.setStorageType(storageType);
		}

		// 中信订单号
		if ( !StringTools.isNullOrNone(obj[13]))
		{
			bean.setCiticNo(obj[13]);
		}
		else
		{
			builder
            .append("第[" + currentNumber + "]错误:")
            .append("浦发银行订单号不能为空")
            .append("<br>");
			
			importError = true;
		}

		// 开票性质
		if ( !StringTools.isNullOrNone(obj[14]))
		{
			int invoiceNature = OutImportHelper.getInvoiceNature(obj[14]);
			
			bean.setInvoiceNature(invoiceNature);
		}
		
		// 开票抬头	
		if ( !StringTools.isNullOrNone(obj[15]))
		{
			bean.setInvoiceHead(obj[15]);
		}

		// 开票要求	
		if ( !StringTools.isNullOrNone(obj[16]))
		{
			bean.setInvoiceCondition(obj[16]);
		}

		// 绑定单号	
		if ( !StringTools.isNullOrNone(obj[17]))
		{
			bean.setBindNo(obj[17]);
		}
		
		// 开票类型	
		if ( !StringTools.isNullOrNone(obj[18]))
		{
			int invoiceType = OutImportHelper.getInvoiceType(obj[18]);
			
			bean.setInvoiceType(invoiceType);
		}

		// 开票品名	
		if ( !StringTools.isNullOrNone(obj[19]))
		{
			bean.setInvoiceName(obj[19]);
		}

		// 开票金额	
		if ( !StringTools.isNullOrNone(obj[20]))
		{
			double invoiceMoney = MathTools.parseDouble(obj[20]);
			
			bean.setInvoiceMoney(invoiceMoney);
		}
		
		// 中信订单日期
		if ( !StringTools.isNullOrNone(obj[27]))
		{
			String name = obj[27].trim();
			
			String eL = "[0-9]{4}-[0-9]{2}-[0-9]{2}";
			Pattern p = Pattern.compile(eL);
			Matcher m = p.matcher(name);
			boolean dateFlag = m.matches();
			if (!dateFlag) {
				builder
                .append("第[" + currentNumber + "]错误:")
                .append("中信订单日期格式错误，如 2000-01-01")
                .append("<br>");
            
    			importError = true;
			}else{
				bean.setCiticOrderDate(name);
			}
		}
		
		// 仓库
		if ( !StringTools.isNullOrNone(obj[30]))
		{
			String depotName = obj[30];
			
			DepotBean depot = depotDAO.findByUnique(depotName);
			
			if (null == depot)
			{
				builder
	            .append("第[" + currentNumber + "]错误:")
	            .append("仓库" + depotName + "不存在")
	            .append("<br>");
				
				importError = true;
			}else
			{
				bean.setDepotId(depot.getId());
			}
		}else
		{
			builder
            .append("第[" + currentNumber + "]错误:")
            .append("仓库不能为空")
            .append("<br>");
			
			importError = true;
		}
		
		// 仓区
		if ( !StringTools.isNullOrNone(obj[31]))
		{
			String depotpartName = obj[31];
			
			DepotpartBean depotpart = depotpartDAO.findByUnique(depotpartName);
			
			if (null == depotpart)
			{
				builder
	            .append("第[" + currentNumber + "]错误:")
	            .append("仓区[" + depotpartName + "]不存在")
	            .append("<br>");
				
				importError = true;
			}else{
				if (!depotpart.getLocationId().equals(bean.getDepotId()))
				{
					builder
		            .append("第[" + currentNumber + "]错误:")
		            .append("仓区[" +depotpartName + "]对应的仓库不是导入中的仓库")
		            .append("<br>");
					
					importError = true;
				}else{
					bean.setDepotpartId(depotpart.getId());
					bean.setComunicationBranch(depotpart.getName());
				}
			}
		}else
		{
			builder
            .append("第[" + currentNumber + "]错误:")
            .append("仓区不能为空")
            .append("<br>");
			
			importError = true;
		}
		
		// 职员 - 领样类型时 必须
		if ( !StringTools.isNullOrNone(obj[32]))
		{
			String name = obj[32].trim();
			
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
			}
		}else
		{
			if (bean.getOutType() == OutConstant.OUTTYPE_OUT_SWATCH)
			{
				builder
	            .append("第[" + currentNumber + "]错误:")
	            .append("领样类型库单时职员不能为空")
	            .append("<br>");
				
				importError = true;	
			}
		}
		
		// 备注
		if ( !StringTools.isNullOrNone(obj[33]))
		{
			bean.setDescription(obj[33]);
		}
		
		// 发货方式
		if ( !StringTools.isNullOrNone(obj[34]))
		{
			boolean has = false;
			
			String shipping = obj[34].trim();
			
			for (int i = 0 ; i < OutImportConstant.shipping.length; i++)
			{
				if (OutImportConstant.shipping[i].equals(shipping))
				{
					has = true;
					
					bean.setShipping(OutImportConstant.ishipping[i]);
					
					break;
				}
			}
			
			if (!has)
			{
				builder
	            .append("第[" + currentNumber + "]错误:")
	            .append("发货方式不对,须为[自提,公司第三方快递,第三方货运,第三方快递+货运,空发]中之一")
	            .append("<br>");
				
				importError = true;
			}
		}else
		{
			builder
            .append("第[" + currentNumber + "]错误:")
            .append("发货方式不能为空,须为[自提,公司第三方快递,第三方货运,第三方快递+货运,空发]中之一")
            .append("<br>");
			
			importError = true;
		}
		
		if (bean.getShipping() == 2 || bean.getShipping() == 4)
		{
			// 如果发货方式是快递或快递+货运 ,则快递须为必填
			if ( !StringTools.isNullOrNone(obj[35]))
			{
				String transport1 = obj[35].trim();
				
				ExpressBean express = expressDAO.findByUnique(transport1);
				
				if (null == express)
				{
					builder
		            .append("第[" + currentNumber + "]错误:")
		            .append("快递方式"+ transport1 +"不存在")
		            .append("<br>");
					
					importError = true;
				}else{
					bean.setTransport1(MathTools.parseInt(express.getId()));
				}
			}else
			{
				builder
	            .append("第[" + currentNumber + "]错误:")
	            .append("快递方式不能为空")
	            .append("<br>");
				
				importError = true;
			}
			
			// 快递支付方式也不能为空
			if ( !StringTools.isNullOrNone(obj[36]))
			{
				String expressPay = obj[36].trim();
				
				boolean isexists = false;
				
				for (int i = 0; i < OutImportConstant.expressPay.length; i++)
				{
					if (expressPay.equals(OutImportConstant.expressPay[i]))
					{
						isexists = true;
						
						bean.setExpressPay(OutImportConstant.iexpressPay[i]);
						
						break;
					}
				}
				
				if (!isexists)
				{
					builder
		            .append("第[" + currentNumber + "]错误:")
		            .append("快递支付方式不存在,只能为[业务员支付,公司支付,客户支付]之一")
		            .append("<br>");
					
					importError = true;
				}
			}else
			{
				builder
	            .append("第[" + currentNumber + "]错误:")
	            .append("快递支付方式不能为空,只能为[业务员支付,公司支付,客户支付]之一")
	            .append("<br>");
				
				importError = true;
			}
		}
		
		if (bean.getShipping() == 3 || bean.getShipping() == 4)
		{
			// 如果发货方式是快递或快递+货运 ,则快递须为必填
			if ( !StringTools.isNullOrNone(obj[37]))
			{
				String transport1 = obj[37].trim();
				
				ExpressBean express = expressDAO.findByUnique(transport1);
				
				if (null == express)
				{
					builder
		            .append("第[" + currentNumber + "]错误:")
		            .append("货运方式"+ transport1 +"不存在")
		            .append("<br>");
					
					importError = true;
				}else{
					bean.setTransport2(MathTools.parseInt(express.getId()));
				}
			}else
			{
				builder
	            .append("第[" + currentNumber + "]错误:")
	            .append("货运方式不能为空")
	            .append("<br>");
				
				importError = true;
			}
			
			// 快递支付方式也不能为空
			if ( !StringTools.isNullOrNone(obj[38]))
			{
				String expressPay = obj[38].trim();
				
				boolean isexists = false;
				
				for (int i = 0; i < OutImportConstant.expressPay.length; i++)
				{
					if (expressPay.equals(OutImportConstant.expressPay[i]))
					{
						isexists = true;
						
						bean.setTransportPay(OutImportConstant.iexpressPay[i]);
						
						break;
					}
				}
				
				if (!isexists)
				{
					builder
		            .append("第[" + currentNumber + "]错误:")
		            .append("货运支付方式不存在,只能为[业务员支付,公司支付,客户支付]之一")
		            .append("<br>");
					
					importError = true;
				}
			}else
			{
				builder
	            .append("第[" + currentNumber + "]错误:")
	            .append("货运支付方式不能为空,只能为[业务员支付,公司支付,客户支付]之一")
	            .append("<br>");
				
				importError = true;
			}
		}
		
		// 省	
		if ( !StringTools.isNullOrNone(obj[21]))
		{
			String name = obj[21].trim();
			
			ProvinceBean province = provinceDAO.findByUnique(name);
			
			if (null != province)
				bean.setProvinceId(province.getId());
			else {
				if (bean.getShipping() == 2 || bean.getShipping() == 3 || bean.getShipping() == 4) {
					builder
		            .append("第[" + currentNumber + "]错误:")
		            .append("快递、货运时省名系统不存在")
		            .append("<br>");
					
					importError = true;
				}
			}
		} else {
			if (bean.getShipping() == 2 || bean.getShipping() == 3 || bean.getShipping() == 4) {
				builder
	            .append("第[" + currentNumber + "]错误:")
	            .append("快递、货运时省不能为空")
	            .append("<br>");
				
				importError = true;
			}
		}

		// 市	
		if ( !StringTools.isNullOrNone(obj[22]))
		{
			String name = obj[22].trim();
			
			CityBean city = cityDAO.findByUnique(name);
			
			if (null != city)
				bean.setCityId(city.getId());
			else {
				if (bean.getShipping() == 2 || bean.getShipping() == 3 || bean.getShipping() == 4) {
					builder
		            .append("第[" + currentNumber + "]错误:")
		            .append("快递、货运时市不存在")
		            .append("<br>");
					
					importError = true;
				}
			}
		} else {
			if (bean.getShipping() == 2 || bean.getShipping() == 3 || bean.getShipping() == 4) {
				builder
	            .append("第[" + currentNumber + "]错误:")
	            .append("快递、货运时市不能为空")
	            .append("<br>");
				
				importError = true;
			}
		}

		// 详细地址	
		if ( !StringTools.isNullOrNone(obj[23]))
		{
			bean.setAddress(obj[23].trim());
			
			if (bean.getShipping() == 2 || bean.getShipping() == 3 || bean.getShipping() == 4) {
				if (bean.getAddress().length() < 5) {
					builder
		            .append("第[" + currentNumber + "]错误:")
		            .append("快递、货运时详细地址不能少于5个字")
		            .append("<br>");
					
					importError = true;
				}
			}
			
		} else {
			if (bean.getShipping() == 2 || bean.getShipping() == 3 || bean.getShipping() == 4) {
				builder
	            .append("第[" + currentNumber + "]错误:")
	            .append("快递、货运时详细地址不能为空")
	            .append("<br>");
				
				importError = true;
			}
		}
		
		// 收货人	
		if ( !StringTools.isNullOrNone(obj[24]))
		{
			bean.setReceiver(obj[24].trim());
			
			if (bean.getShipping() == 2 || bean.getShipping() == 3 || bean.getShipping() == 4) {
				if (bean.getReceiver().length() < 2) {
					builder
		            .append("第[" + currentNumber + "]错误:")
		            .append("快递、货运时收货人不能少于2个字")
		            .append("<br>");
					
					importError = true;
				}
			}
		} else {
			if (bean.getShipping() == 2 || bean.getShipping() == 3 || bean.getShipping() == 4) {
				builder
	            .append("第[" + currentNumber + "]错误:")
	            .append("快递、货运时收货人不能为空")
	            .append("<br>");
				
				importError = true;
			}
		}

		// 收货人手机
		if ( !StringTools.isNullOrNone(obj[25]))
		{
			bean.setHandPhone(obj[25].trim());
			
			if (bean.getShipping() == 2 || bean.getShipping() == 3 || bean.getShipping() == 4) {
				if (bean.getHandPhone().length() < 11) {
					builder
		            .append("第[" + currentNumber + "]错误:")
		            .append("快递、货运时收货人手机不能少于11位")
		            .append("<br>");
					
					importError = true;
				}
			}
		} else {
			if (bean.getShipping() == 2 || bean.getShipping() == 3 || bean.getShipping() == 4) {
				builder
	            .append("第[" + currentNumber + "]错误:")
	            .append("快递、货运时收货人手机不能为空")
	            .append("<br>");
				
				importError = true;
			}
		}
		
		// 回款天数
		if ( !StringTools.isNullOrNone(obj[39]))
		{
			bean.setReday(MathTools.parseInt(obj[39].trim()));
		}else{
			bean.setReday(OutImportConstant.CITIC_REDAY);
		}
		
		if (bean.getOutType() == OutConstant.OUTTYPE_OUT_PRESENT) {
			if ( !StringTools.isNullOrNone(obj[40]))
			{
				String presentFlag = obj[40].trim();
				
				String [] presentFlags = OutImportConstant.outPresentTypesArr;
				
				for(int i = 0; i< presentFlags.length; i++)
				{
					if (presentFlag.equals(presentFlags[i]))
					{
						bean.setPresentFlag(OutImportConstant.outPresentTypeiArr[i]);
						
						break;
					}
				}
				
				if (bean.getPresentFlag() == 0)
				{
					builder
	                .append("第[" + currentNumber + "]错误:")
	                .append("赠送类型不存在")
	                .append("<br>");
					
					importError = true;
				}
			}else{
				builder
	            .append("第[" + currentNumber + "]错误:")
	            .append("赠送单据时赠送类型不能为空")
	            .append("<br>");
				
				importError = true;
			}
		}
		
		return importError;
	}
	
    /**
     * 
     * @param obj
     * @return
     */
    private String[] fillObj(String[] obj)
    {
        String[] result = new String[50];

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
     * 批量更新配送地址
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward batchUpdateDistAddr(ActionMapping mapping, ActionForm form,
    		HttpServletRequest request, HttpServletResponse response)
    throws ServletException
    {
        System.out.println("1111111111111111111111111111");
        RequestDataStream rds = new RequestDataStream(request);
        
        boolean importError = false;
        
        List<DistributionBean> importItemList = new ArrayList<DistributionBean>(); 
        
        StringBuilder builder = new StringBuilder();
        
        try
        {
            rds.parser();
        }
        catch (Exception e1)
        {
            _logger.error(e1, e1);

            request.setAttribute(KeyConstant.ERROR_MESSAGE, "解析失败");

            return mapping.findForward("uploadDistAddress");
        }

        if ( !rds.haveStream())
        {
            request.setAttribute(KeyConstant.ERROR_MESSAGE, "解析失败");

            return mapping.findForward("uploadDistAddress");
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
                	DistributionBean bean = new DistributionBean();
                    
                	// 单号
            		if ( !StringTools.isNullOrNone(obj[0]))
            		{
            			String outId = obj[0].trim();
            			
            			OutBean out = outDAO.find(outId);
            			
            			if (null == out){
                			/*builder
                            .append("第[" + currentNumber + "]错误:")
                            .append("销售单不存在")
                            .append("<br>");
                        
                			importError = true;*/
            			}else{
            				if (out.getType() != 0){
                    			builder
                                .append("第[" + currentNumber + "]错误:")
                                .append("单号不是销售单")
                                .append("<br>");
                            
                    			importError = true;
            				}else{
            					if (out.getStatus() == 3 || out.getStatus() == 4){
                        			builder
                                    .append("第[" + currentNumber + "]错误:")
                                    .append("销售单已是发货态不能批量更新")
                                    .append("<br>");
                                
                        			importError = true;
            					}else{
            						bean.setOutId(outId);
            					}
            				}
            			}
            			
            			
            		}else{
            			builder
                        .append("第[" + currentNumber + "]错误:")
                        .append("销售单号不能为空")
                        .append("<br>");
                    
            			importError = true;
            		}

            		// 省
            		if ( !StringTools.isNullOrNone(obj[1]))
            		{
            			String name = obj[1].trim();
            			
            			ProvinceBean province = provinceDAO.findByUnique(name);
            			
            			if (null == province){
                			builder
                            .append("第[" + currentNumber + "]错误:")
                            .append("省不存在")
                            .append("<br>");
                        
                			importError = true;
            			}else{
            				bean.setProvinceId(province.getId());
            			}
            		}else{
            			builder
                        .append("第[" + currentNumber + "]错误:")
                        .append("省不能为空")
                        .append("<br>");
                    
            			importError = true;
            		}

            		// 市
            		if ( !StringTools.isNullOrNone(obj[2]))
            		{
            			String name = obj[2].trim();
            			
            			List<CityBean> cityList = cityDAO.queryEntityBeansByCondition("where name=? and parentid=?", name, bean.getProvinceId());
            			
            			if (ListTools.isEmptyOrNull(cityList)){
                			builder
                            .append("第[" + currentNumber + "]错误:")
                            .append("市不存在")
                            .append("<br>");
                        
                			importError = true;
            			}else{
            				bean.setCityId(cityList.get(0).getId());
            			}
            			
            		}else{
            			builder
                        .append("第[" + currentNumber + "]错误:")
                        .append("市不能为空")
                        .append("<br>");
                    
            			importError = true;
            		}
            		
            		// 区
            		/*if ( !StringTools.isNullOrNone(obj[3]))
            		{
            			String name = obj[3].trim();
            			
            			List<AreaBean> areaList = areaDAO.queryEntityBeansByCondition("where name=? and parentid=?", name, bean.getCityId());
            			
            			if (ListTools.isEmptyOrNull(areaList)){
                			builder
                            .append("第[" + currentNumber + "]错误:")
                            .append("区不存在")
                            .append("<br>");
                        
                			importError = true;
            			}else{
            				bean.setAreaId(areaList.get(0).getId());
            			}
            			
            		}*/
            		
            		// 详细地址
            		if ( !StringTools.isNullOrNone(obj[3]))
            		{
            			bean.setAddress(obj[3].trim());
            		}else{
            			builder
                        .append("第[" + currentNumber + "]错误:")
                        .append("详细地址不能为空")
                        .append("<br>");
                    
            			importError = true;
            		}
            		
            		// 收货人
            		if ( !StringTools.isNullOrNone(obj[4]))
            		{
            			bean.setReceiver(obj[4].trim());
            		}else{
            			builder
                        .append("第[" + currentNumber + "]错误:")
                        .append("收货人不能为空")
                        .append("<br>");
                    
            			importError = true;
            		}
            		
            		// 收货人电话
            		if ( !StringTools.isNullOrNone(obj[5]))
            		{
            			bean.setMobile(obj[5].trim());
            		}else{
            			builder
                        .append("第[" + currentNumber + "]错误:")
                        .append("收货人电话不能为空")
                        .append("<br>");
                    
            			importError = true;
            		}
            		
            		// 发货方式
            		if ( !StringTools.isNullOrNone(obj[6]))
            		{
            			boolean has = false;
            			
            			String shipping = obj[6].trim();
            			
            			for (int i = 0 ; i < OutImportConstant.shipping.length; i++)
            			{
            				if (OutImportConstant.shipping[i].equals(shipping))
            				{
            					has = true;
            					
            					bean.setShipping(OutImportConstant.ishipping[i]);
            					
            					break;
            				}
            			}
            			
            			if (!has)
            			{
            				builder
            	            .append("第[" + currentNumber + "]错误:")
            	            .append("发货方式不对,须为[自提,公司第三方快递,第三方货运,第三方快递+货运,空发]中之一")
            	            .append("<br>");
            				
            				importError = true;
            			}
            		}else
            		{
            			builder
                        .append("第[" + currentNumber + "]错误:")
                        .append("发货方式不能为空,须为[自提,公司第三方快递,第三方货运,第三方快递+货运,空发]中之一")
                        .append("<br>");
            			
            			importError = true;
            		}

                    // 销售单备注
                    if ( !StringTools.isNullOrNone(obj[7]))
                    {
                        bean.setDescription(obj[7].trim());
                    }
            		
                    importItemList.add(bean);
                    
                }
                else
                {
                    builder
                        .append("第[" + currentNumber + "]错误:")
                        .append("数据长度不足32格错误")
                        .append("<br>");
                    
                    importError = true;
                }
            }
        }catch (Exception e)
        {
            _logger.error(e, e);
            
            request.setAttribute(KeyConstant.ERROR_MESSAGE, e.toString());

            return mapping.findForward("uploadDistAddress");
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

            return mapping.findForward("uploadDistAddress");
        }
        
        try
        {
            System.out.println("222222222222222222222222");
        	outImportManager.batchUpdateDistAddr(importItemList);
        	
        	request.setAttribute(KeyConstant.MESSAGE, "批量更新成功");
        }
        catch(MYException e)
        {
            request.setAttribute(KeyConstant.ERROR_MESSAGE, "批量更新出错:"+ e.getErrorContent());
        }
        
        return mapping.findForward("uploadDistAddress");
	}
    
    /**
     * 批量更新账期
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward batchUpdateRedate(ActionMapping mapping, ActionForm form,
    		HttpServletRequest request, HttpServletResponse response)
    throws ServletException
    {
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

            return mapping.findForward("importRedate");
        }

        if ( !rds.haveStream())
        {
            request.setAttribute(KeyConstant.ERROR_MESSAGE, "解析失败");

            return mapping.findForward("importRedate");
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
                    
                	// 单号
            		if ( !StringTools.isNullOrNone(obj[0]))
            		{
            			String outId = obj[0].trim();
            			
            			OutBean out = outDAO.find(outId);
            			
            			if (null == out){
                			builder
                            .append("第[" + currentNumber + "]错误:")
                            .append("销售单不存在")
                            .append("<br>");
                        
                			importError = true;
            			}else{
            				if (out.getType() != 0){
                    			builder
                                .append("第[" + currentNumber + "]错误:")
                                .append("单号不是销售单")
                                .append("<br>");
                            
                    			importError = true;
            				}else{
//            					if (out.getStatus() == 3 || out.getStatus() == 4){
//                        			builder
//                                    .append("第[" + currentNumber + "]错误:")
//                                    .append("销售单已是发货态不能批量更新")
//                                    .append("<br>");
//                                
//                        			importError = true;
//            					}else{
            						bean.setFullId(outId);
//            					}
            				}
            			}
            			
            			
            		}else{
            			builder
                        .append("第[" + currentNumber + "]错误:")
                        .append("销售单号不能为空")
                        .append("<br>");
                    
            			importError = true;
            		}

            		// 延期天数
            		if ( !StringTools.isNullOrNone(obj[1]))
            		{
            			int reday = MathTools.parseInt(obj[1].trim());
            			
            			if (reday <= 0)
            			{
            				builder
                            .append("第[" + currentNumber + "]错误:")
                            .append("延期天数须为数字")
                            .append("<br>");
                        
                			importError = true;
            			}
            			
            			bean.setReday(reday);
            		}else{
            			builder
                        .append("第[" + currentNumber + "]错误:")
                        .append("延期天数不能为空")
                        .append("<br>");
                    
            			importError = true;
            		}
                	
                    importItemList.add(bean);
                }
                else
                {
                    builder
                        .append("第[" + currentNumber + "]错误:")
                        .append("数据长度不足32格错误")
                        .append("<br>");
                    
                    importError = true;
                }
            }
        }catch (Exception e)
        {
            _logger.error(e, e);
            
            request.setAttribute(KeyConstant.ERROR_MESSAGE, e.toString());

            return mapping.findForward("importRedate");
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

            return mapping.findForward("importRedate");
        }
        
        try
        {
        	outImportManager.batchUpdateRedate(importItemList);
        	
        	request.setAttribute(KeyConstant.MESSAGE, "批量更新成功");
        }
        catch(MYException e)
        {
            request.setAttribute(KeyConstant.ERROR_MESSAGE, "批量更新出错:"+ e.getErrorContent());
        }
        
        return mapping.findForward("importRedate");
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
    public ActionForward processOutImport(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
	throws ServletException
	{
    	AjaxResult ajaxResult = new AjaxResult();
    	
    	String batchId = request.getParameter("batchId");
    	
    	List<OutImportBean> list = outImportDAO.queryEntityBeansByFK(batchId);
    	
    	ajaxResult.setSuccess();
    	
    	if (!ListTools.isEmptyOrNull(list))
    	{
    		try
			{
				outImportManager.process(list);
				
				ajaxResult.setSuccess("处理成功");
			}
			catch (MYException e)
			{
				_logger.warn(e, e);
				
				ajaxResult.setError("处理失败:" + e.getErrorContent());
			}
    	}
    	
    	return JSONTools.writeResponse(response, ajaxResult);
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
    public ActionForward queryOutImportResult(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
	throws ServletException
	{
    	return null;
	}
    
    /**
     * 日志查询
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward queryOutImportLog(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
	throws ServletException
	{
        ConditionParse condtion = new ConditionParse();

        condtion.addWhereStr();

        ActionTools.processJSONQueryCondition(QUERYOUTIMPORTLOG, request, condtion);

        String batchId = request.getParameter("batchId");
        
        if (!StringTools.isNullOrNone(batchId))
        {
        	condtion.addCondition("OutImportLogBean.batchId", "=", batchId);
        }
        
        condtion.addCondition(" order by OutImportLogBean.logTime desc");
        
        String jsonstr = ActionTools.queryVOByJSONAndToString(QUERYOUTIMPORTLOG, request, condtion,
            this.outImportLogDAO);

        return JSONTools.writeResponse(response, jsonstr);
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
    public ActionForward queryOutImport(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
	throws ServletException
	{
    	User user = Helper.getUser(request);
    	
        ConditionParse condtion = new ConditionParse();

        condtion.addWhereStr();

        String sbatchId = request.getParameter("sbatchId");
        
        if (!StringTools.isNullOrNone(sbatchId))
        {
        	condtion.addCondition("OutImportBean.batchId", "=", sbatchId);
        }
        
        setCondition(condtion, user);
        
        Map<String, String> initMap = initLogTime1(request, condtion);
        
        ActionTools.processJSONDataQueryCondition(QUERYOUTIMPORT, request,
				condtion, initMap);
        
        condtion.addCondition(" order by OutImportBean.logTime desc");
        
        String jsonstr = ActionTools.queryVOByJSONAndToString(QUERYOUTIMPORT, request, condtion,
            this.outImportDAO);

        return JSONTools.writeResponse(response, jsonstr);
    }
    
    private  void setCondition(ConditionParse condtion, User user)
    {
    	CiticBranchBean bean = citicBranchDAO.findByUnique(user.getStafferId());
		
    	if (null != bean)
    	{
    		List<CiticVSStafferBean> vsList = citicVSStafferDAO.queryEntityBeansByFK(bean.getId());
    		
			StringBuffer sb = new StringBuffer();
			
	        final String TAB = ",";
	        final String TAB2 = "'";
	        
	        sb.append("(");
	        
			// 指定查询数据范围
			for(int i = 0 ; i < vsList.size(); i++ )
			{
    			sb.append(TAB2);
    			sb.append(vsList.get(i).getCustomerId());
    			sb.append(TAB2);
    			
    			if(i+1 < vsList.size())
    			{
    				sb.append(TAB);
    			}
    		}
			
			sb.append(")");
			
			condtion.addCondition(" and OutImportBean.customerId in " + sb.toString());
    	}
    }
    
    /**
     * 负库存查询
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward queryReplenishment(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
	throws ServletException
	{
    	List<ReplenishmentBean> list = null;
    	
    	try
    	{
    		list = outImportManager.queryReplenishmentBean();
    	}
    	catch(MYException e)
    	{
    		_logger.error(e, e);
    		
    		request.setAttribute(KeyConstant.ERROR_MESSAGE, "查询出现异常.");
    		
    		mapping.findForward("error");
    	}
    	
    	request.setAttribute("list", list);
    	
    	return mapping.findForward("queryReplenishment");
	}

    /**
     * 导出
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward exportOutImport(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
	throws ServletException
	{
		OutputStream out = null;

		String filenName = "ExportOutImport_" + TimeTools.now("MMddHHmmss") + ".csv";

		response.setContentType("application/x-dbf");

		response.setHeader("Content-Disposition", "attachment; filename="
				+ filenName);

		WriteFile write = null;

		ConditionParse condtion = JSONPageSeparateTools.getCondition(request,
				QUERYOUTIMPORT);

		int count = outImportDAO.countVOByCondition(condtion.toString());

		if (count > 150000)
		{
			return ActionTools.toError("导出数量大于150000,请重新选择时间段导出", mapping, request);
		}

		try
		{
			out = response.getOutputStream();

			write = WriteFileFactory.getMyTXTWriter();

			write.openFile(out);

			write.writeLine("序号,中信订单号,OA单号,申请人,网点名称,品名,数量,金额,中收金额");
			
			PageSeparate page = new PageSeparate();

			page.reset2(count, 2000);

			WriteFileBuffer line = new WriteFileBuffer(write);

			while (page.nextPage())
			{
				List<OutImportVO> voFList = outImportDAO.queryEntityVOsByCondition(
						condtion, page);

				for (OutImportVO each : voFList)
				{
					line.reset();

					line.writeColumn(each.getId());
					
					line.writeColumn("["+each.getCiticNo()+"]");
					
					line.writeColumn(each.getOANo());
					
					// 申请人取客户挂靠人
					if (!StringTools.isNullOrNone(each.getOANo())){
						OutBean outBean = outDAO.find(each.getOANo());
						
						if (null == outBean){
							line.writeColumn("");
						}else{
							line.writeColumn(outBean.getStafferName());
						}
					}else{
						line.writeColumn("");
					}
					
					line.writeColumn(each.getComunicatonBranchName());
					
					line.writeColumn(each.getProductName());
					line.writeColumn(each.getAmount());
					line.writeColumn(each.getValue());
					line.writeColumn(each.getMidValue());
					
					line.writeLine();
				}
			}

			write.close();
		}
		catch (Throwable e)
		{
			_logger.error(e, e);

			return null;
		}
		finally
		{
			if (out != null)
			{
				try
				{
					out.close();
				}
				catch (IOException e1)
				{
				}
			}

			if (write != null)
			{
				try
				{
					write.close();
				}
				catch (IOException e1)
				{
				}
			}
		}

		return null;
	
	}
    
    /**
     * 批量审核
     * 导入要审核的单子
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward batchApproveImport(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
	throws ServletException
	{
        RequestDataStream rds = new RequestDataStream(request);
        
        boolean importError = false;
        
        List<BatchApproveBean> importItemList = new ArrayList<BatchApproveBean>(); 
        
        StringBuilder builder = new StringBuilder();
        
        try
        {
            rds.parser();
        }
        catch (Exception e1)
        {
            _logger.error(e1, e1);

            request.setAttribute(KeyConstant.ERROR_MESSAGE, "解析失败");

            return mapping.findForward("importBatchApprove");
        }

        if ( !rds.haveStream())
        {
            request.setAttribute(KeyConstant.ERROR_MESSAGE, "解析失败");

            return mapping.findForward("importBatchApprove");
        }
        
        String type = rds.getParameter("mode");
        
        if (StringTools.isNullOrNone(type))
        {
        	request.setAttribute(KeyConstant.ERROR_MESSAGE, "参数错误");

            return mapping.findForward("importBatchApprove");
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
                	BatchApproveBean bean = new BatchApproveBean();
                    
                	bean.setType(MathTools.parseInt(type));
                	
                	// 中信订单号
            		if ( !StringTools.isNullOrNone(obj[0]))
            		{
            			bean.setOutId(obj[0]);
            		}
            		else
            		{
            			throw new MYException("中信银行订单号不能为空");
            		}
            		
            		// 申请人
            		if ( !StringTools.isNullOrNone(obj[1]))
            		{
            			String applyId =  obj[1];
            			
            			StafferBean sb = stafferDAO.findByUnique(applyId);
            			
            			if (sb == null)
            			{
            				throw new MYException("申请人不存在");
            			}else{
            				bean.setApplyId(applyId);	
            			}
            		}
            		else
            		{
            			throw new MYException("申请人不能为空");
            		}
            		
            		// 审批动作
            		if ( !StringTools.isNullOrNone(obj[2]))
            		{
            			String action = obj[2];
            			if (!action.equals("通过") && !action.equals("驳回"))
            			{
            				throw new MYException("审批动作只能是通过或驳回");
            			}
            			
            			bean.setAction(action);
            		}
            		else
            		{
            			throw new MYException("审批动作不存在");
            		}
            		
            		// 审批意见
            		if ( !StringTools.isNullOrNone(obj[3]))
            		{
            			bean.setReason(obj[3]);
            		}
            		
                    importItemList.add(bean);
                    
                }
                else
                {
                    builder
                        .append("第[" + currentNumber + "]错误:")
                        .append("数据长度不足26格错误")
                        .append("<br>");
                    
                    importError = true;
                }
            }
            
            
        }catch (Exception e)
        {
            _logger.error(e, e);
            
            request.setAttribute(KeyConstant.ERROR_MESSAGE, e.toString());

            return mapping.findForward("importBatchApprove");
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

            return mapping.findForward("importBatchApprove");
        }
        
        String batchId = "";
        
        try
        {
        	batchId = outImportManager.batchApproveImport(importItemList, MathTools.parseInt(type));
        }
        catch(MYException e)
        {
            request.setAttribute(KeyConstant.ERROR_MESSAGE, "导入出错:"+ e.getErrorContent());

            return mapping.findForward("importBatchApprove");
        }
        
        request.setAttribute("batchId", batchId);
        
        request.setAttribute("flag", 0);

        return queryBatchApprove(mapping, form, request, response);
	}
    
    public ActionForward queryBatchApprove(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
	throws ServletException
	{
    	String batchId = RequestTools.getValueFromRequest(request, "batchId");
    	
    	CommonTools.saveParamers(request);
    	
        // 查询
        List<BatchApproveBean> baList = batchApproveDAO.queryEntityBeansByFK(batchId);
        
        request.setAttribute("baList", baList);
        
    	return mapping.findForward("queryBatchApprove");
	}
    
    /**
     * queryBatchSwatch
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward queryBatchSwatch(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
	throws ServletException
	{
    	String batchId = RequestTools.getValueFromRequest(request, "batchId");
    	
    	CommonTools.saveParamers(request);
    	
        // 查询
        List<BatchSwatchBean> baList = batchSwatchDAO.queryEntityBeansByFK(batchId);
        
        request.setAttribute("baList", baList);
        
    	return mapping.findForward("queryBatchSwatch");
	}
    
    /**
     * 批量审核 处理 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward batchApprove(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
	throws ServletException
	{
    	String batchId = request.getParameter("batchId");
    	
    	User user = Helper.getUser(request);
    	
    	try{
    		outImportManager.batchApprove(user, batchId);
    		
    		request.setAttribute(KeyConstant.MESSAGE, "批量审批成功");
    	}catch(MYException e)
    	{
    		_logger.warn(e, e);
    		
    		request.setAttribute(KeyConstant.ERROR_MESSAGE, "批量审批失败");
    	}
    	
    	request.setAttribute("batchId", batchId);
    	
    	request.setAttribute("flag", 1);
    	
    	return queryBatchApprove(mapping, form, request, response);
	}
    
    /**
     * batchSwacth
     * 
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward batchSwatch(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
	throws ServletException
	{
    	String batchId = request.getParameter("batchId");
    	
    	User user = Helper.getUser(request);
    	
    	try{
    		outImportManager.batchSwatch(user, batchId);
    		
    		request.setAttribute(KeyConstant.MESSAGE, "批量处理成功");
    	}catch(MYException e)
    	{
    		_logger.warn(e, e);
    		
    		request.setAttribute(KeyConstant.ERROR_MESSAGE, "批量处理失败");
    	}
    	
    	request.setAttribute("batchId", batchId);
    	
    	request.setAttribute("flag", 1);
    	
    	return queryBatchSwatch(mapping, form, request, response);
	}
    
    /**
     * 领样/巡展 导入式生成退货或销售
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward batchSwatchImport(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
	throws ServletException
	{
        RequestDataStream rds = new RequestDataStream(request);
        
        boolean importError = false;
        
        List<BatchSwatchBean> importItemList = new ArrayList<BatchSwatchBean>(); 
        
        StringBuilder builder = new StringBuilder();
        
        try
        {
            rds.parser();
        }
        catch (Exception e1)
        {
            _logger.error(e1, e1);

            request.setAttribute(KeyConstant.ERROR_MESSAGE, "解析失败");

            return mapping.findForward("importBatchSwatch");
        }

        if ( !rds.haveStream())
        {
            request.setAttribute(KeyConstant.ERROR_MESSAGE, "解析失败");

            return mapping.findForward("importBatchSwatch");
        }
        
        Map<String,String> map = new HashMap<String, String>();
        
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
                	BatchSwatchBean bean = new BatchSwatchBean();
                    
            		// 审批动作
            		if ( !StringTools.isNullOrNone(obj[0]))
            		{
            			String action = obj[0];
            			if (!action.equals("退货"))
            			{
            				throw new MYException("类型只能是领样转退货");
            			}
            			
            			bean.setAction(action);
            		}
            		else
            		{
            			throw new MYException("类型不能为空");
            		}
            		
                	// 中信订单号
            		if ( !StringTools.isNullOrNone(obj[1]))
            		{
            			String outId = obj[1].trim(); 
            			
            			// 须为销售领样单
            			OutBean out = outDAO.find(outId);
            			
            			if (null == out)
            			{
            				throw new MYException("销售单[%s]不存在", outId);
            			}
            			
            			if (out.getType() == OutConstant.OUT_TYPE_OUTBILL && 
            					(out.getOutType() == OutConstant.OUTTYPE_OUT_SWATCH 
            					|| out.getOutType() == OutConstant.OUTTYPE_OUT_SHOW
            					|| out.getOutType() == OutConstant.OUTTYPE_OUT_SHOWSWATCH) )
            			{
            				if (out.getStatus() == OutConstant.STATUS_SEC_PASS || out.getStatus() == OutConstant.STATUS_PASS)
            				{
            					bean.setOutId(outId);
            				}else{
            					throw new MYException("销售状态不是已出库");
            				}
            			}
            			else
            			{
            				throw new MYException("销售类型错误");
            			}
            		}
            		else
            		{
            			throw new MYException("中信银行订单号不能为空");
            		}
            		
            		// 商品
            		if ( !StringTools.isNullOrNone(obj[2]))
            		{
            			bean.setProductName(obj[2]);
            		}else
            		{
            			throw new MYException("商品不能为空");
            		}
            		
            		// 数量
            		if ( !StringTools.isNullOrNone(obj[3]))
            		{
            			int amount = MathTools.parseInt(obj[3].trim());
            			
            			if (amount <= 0)
            			{
            				throw new MYException("数量须大于0");
            			}
            			
            			bean.setAmount(amount);
            		}else
            		{
            			throw new MYException("数量不能为空");
            		}
            		
            		// 客户
            		if ( !StringTools.isNullOrNone(obj[4]))
            		{
            			String cust = obj[4];
            			
            			CustomerBean custBean = customerMainDAO.findByUnique(cust);
            			
            			if (null == custBean)
            			{
            				throw new MYException(cust+ " 客户不存在");
            			}else{
            				bean.setCustomerId(custBean.getId());
            			}
            		}else
            		{
            			throw new MYException("客户不能为空");
            		}
            		
            		// 目的仓库
            		if ( !StringTools.isNullOrNone(obj[5]))
            		{
            			String dirDeport = obj[5];
            			
            			DepotBean deportBean = depotDAO.findByUnique(dirDeport);
            			
            			if (null == deportBean)
            			{
            				throw new MYException(dirDeport+ " 仓库不存在");
            			}else{
            				bean.setDirDeport(deportBean.getId());
            			}
            		}else
            		{
            			throw new MYException("目的仓库不能为空");
            		}
            		
            		if (map.containsKey(bean.getOutId() + "~" + bean.getProductName()))
            		{
            			throw new MYException("一次导入时同一单同一产品不能重复出现");
            		}else
            		{
            			map.put(bean.getOutId() + "~" + bean.getProductName(), bean.getOutId() + "~" + bean.getProductName());
            		}
            		
            		bean.setDescription(obj[5].trim());
            		
                    importItemList.add(bean);
                    
                }
                else
                {
                    builder
                        .append("第[" + currentNumber + "]错误:")
                        .append("数据长度不足26格错误")
                        .append("<br>");
                    
                    importError = true;
                }
            }
            
            
        }catch (Exception e)
        {
            _logger.error(e, e);
            
            request.setAttribute(KeyConstant.ERROR_MESSAGE, e.toString());

            return mapping.findForward("importBatchSwatch");
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

            return mapping.findForward("importBatchSwatch");
        }
        
        String batchId = "";
        
        try
        {
        	batchId = outImportManager.batchSwatchImport(importItemList);
        }
        catch(MYException e)
        {
            request.setAttribute(KeyConstant.ERROR_MESSAGE, "导入出错:"+ e.getErrorContent());

            return mapping.findForward("importBatchSwatch");
        }
        
        request.setAttribute("batchId", batchId);
        
        request.setAttribute("flag", 0);

        return queryBatchSwatch(mapping, form, request, response);
	}
    
    /**
     * 批量导入发货信息,并根据配送单号更新
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward importConsign(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
	throws ServletException
	{
        RequestDataStream rds = new RequestDataStream(request);
        
        boolean importError = false;
        
        List<ConsignBean> importItemList = new ArrayList<ConsignBean>(); 
        
        StringBuilder builder = new StringBuilder();
        
        try
        {
            rds.parser();
        }
        catch (Exception e1)
        {
            _logger.error(e1, e1);

            request.setAttribute(KeyConstant.ERROR_MESSAGE, "解析失败");

            return mapping.findForward("importConsign");
        }

        if ( !rds.haveStream())
        {
            request.setAttribute(KeyConstant.ERROR_MESSAGE, "解析失败");

            return mapping.findForward("importConsign");
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
                	ConsignBean bean = new ConsignBean();
                    
            		// 配送单号
            		if ( !StringTools.isNullOrNone(obj[0]))
            		{
            			bean.setDistId(obj[0]);
            		}
            		else
            		{
            			builder
                        .append("第[" + currentNumber + "]错误:")
                        .append("出库单不能为空")
                        .append("<br>");
                    
            			importError = true;
            		}
            		
            		if ( !StringTools.isNullOrNone(obj[1]))
            		{
            			bean.setReveiver(obj[1]);
            		}
            		
            		if ( !StringTools.isNullOrNone(obj[2]))
            		{
            			bean.setApplys(obj[2]);
            		}
            		
            		// 
            		if ( !StringTools.isNullOrNone(obj[4]))
            		{
            			// transport 须是在transport1 下面的
            			String transport = obj[4].trim();
            			
            			TransportBean tb = consignDAO.findTransportByName(transport);
            			
            			if (null == tb)
            			{
                			builder
                            .append("第[" + currentNumber + "]错误:")
                            .append("运输单位不存在")
                            .append("<br>");
                        
                			importError = true;
            			}
            			
            			bean.setTransport(tb.getId());
            		}
            		else
            		{
            			builder
                        .append("第[" + currentNumber + "]错误:")
                        .append("运输单位不能为空")
                        .append("<br>");
                    
            			importError = true;
            		}
            		
            		if ( !StringTools.isNullOrNone(obj[5]))
            		{
            			bean.setTransportNo(obj[5]);
            		}else
            		{
            			builder
                        .append("第[" + currentNumber + "]错误:")
                        .append("发货单号不能为空")
                        .append("<br>");
                    
            			importError = true;
            		}
            		
            		if ( !StringTools.isNullOrNone(obj[6]))
            		{
            			bean.setSendPlace(obj[6]);
            		}
            		
            		if ( !StringTools.isNullOrNone(obj[7]))
            		{
            			bean.setPreparer(obj[7]);
            		}
            		
            		if ( !StringTools.isNullOrNone(obj[8]))
            		{
            			bean.setChecker(obj[8]);
            		}
            		
            		if ( !StringTools.isNullOrNone(obj[9]))
            		{
            			bean.setPackager(obj[9]);
            		}
            		
            		if ( !StringTools.isNullOrNone(obj[10]))
            		{
            			bean.setPackageTime(obj[10]);
            		}
            		
            		if ( !StringTools.isNullOrNone(obj[11]))
            		{
            			bean.setPackageTime(bean.getPackageTime() + " " + obj[11]);
            		}
            		
            		if ( !StringTools.isNullOrNone(obj[12]))
            		{
            			bean.setMathine(obj[12]);
            		}
            		
            		if ( !StringTools.isNullOrNone(obj[13]))
            		{
            			bean.setPackageAmount(obj[13]);
            		}
            		
            		if ( !StringTools.isNullOrNone(obj[14]))
            		{
            			bean.setPackageWeight(obj[14]);
            		}
            		
            		if ( !StringTools.isNullOrNone(obj[15]))
            		{
            			bean.setTransportFee(obj[15]);
            		}
            		
                    importItemList.add(bean);
                    
                }
                else
                {
                    builder
                        .append("第[" + currentNumber + "]错误:")
                        .append("数据长度不足26格错误")
                        .append("<br>");
                    
                    importError = true;
                }
            }
        }catch (Exception e)
        {
            _logger.error(e, e);
            
            request.setAttribute(KeyConstant.ERROR_MESSAGE, e.toString());

            return mapping.findForward("importConsign");
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

            return mapping.findForward("importConsign");
        }
        
        try
        {
        	outImportManager.batchUpdateConsign(importItemList);
        	
        	request.setAttribute(KeyConstant.MESSAGE, "批量更新成功");
        }
        catch(MYException e)
        {
            request.setAttribute(KeyConstant.ERROR_MESSAGE, "导入出错:"+ e.getErrorContent());

            return mapping.findForward("importConsign");
        }
        
        return mapping.findForward("importConsign");
	}
    
    /**
     * 批量导入销售单号， 目的 根据fullId, 更新emergency
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward importOutId(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
	throws ServletException
	{
        RequestDataStream rds = new RequestDataStream(request);
        
        boolean importError = false;
        
        List<ConsignBean> importItemList = new ArrayList<ConsignBean>(); 
        
        StringBuilder builder = new StringBuilder();
        
        try
        {
            rds.parser();
        }
        catch (Exception e1)
        {
            _logger.error(e1, e1);

            request.setAttribute(KeyConstant.ERROR_MESSAGE, "解析失败");

            return mapping.findForward("importOutId");
        }

        if ( !rds.haveStream())
        {
            request.setAttribute(KeyConstant.ERROR_MESSAGE, "解析失败");

            return mapping.findForward("importOutId");
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
                	ConsignBean bean = new ConsignBean();
                    
                	// 销售单号
            		if ( !StringTools.isNullOrNone(obj[0]))
            		{
            			String outId = obj[0].trim(); 
            			
            			// 须为销售领样单
            			OutBean out = outDAO.find(outId);
            			
            			if (null == out)
            			{
            				builder
                            .append("第[" + currentNumber + "]错误:")
                            .append("销售单"+ outId +"不存在")
                            .append("<br>");
                        
                			importError = true;
            			} else {
            				if (out.getType() == OutConstant.OUT_TYPE_OUTBILL) {
            					if (out.getStatus() == OutConstant.STATUS_SEC_PASS || out.getStatus() == OutConstant.STATUS_PASS)
                				{
            						builder
                                    .append("第[" + currentNumber + "]错误:")
                                    .append("销售单号已是发货态，不能操作.")
                                    .append("<br>");
                                
                        			importError = true;
                				}else{
                					bean.setFullId(outId);
                				}
            				} else {
            					builder
                                .append("第[" + currentNumber + "]错误:")
                                .append("销售单号须是销售单，不能为入库单.")
                                .append("<br>");
                            
                    			importError = true;
            				}
            			}
            		}
            		else
            		{
            			builder
                        .append("第[" + currentNumber + "]错误:")
                        .append("销售单号不能为空")
                        .append("<br>");
                    
            			importError = true;
            		}
            		
                    importItemList.add(bean);
                    
                }
                else
                {
                    builder
                        .append("第[" + currentNumber + "]错误:")
                        .append("数据长度不足26格错误")
                        .append("<br>");
                    
                    importError = true;
                }
            }
        }catch (Exception e)
        {
            _logger.error(e, e);
            
            request.setAttribute(KeyConstant.ERROR_MESSAGE, e.toString());

            return mapping.findForward("importConsign");
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

            return mapping.findForward("importOutId");
        }
        
        try
        {
        	outImportManager.batchUpdateEmergency(importItemList);
        	
        	request.setAttribute(KeyConstant.MESSAGE, "批量更新成功");
        }
        catch(MYException e)
        {
            request.setAttribute(KeyConstant.ERROR_MESSAGE, "导入出错:"+ e.getErrorContent());

            return mapping.findForward("importOutId");
        }
        
        return mapping.findForward("importOutId");
	}
    
    /**
     * importOutDepot 
     * 		批量导入销售单仓库及库区
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward importOutDepot(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
	throws ServletException
	{
        RequestDataStream rds = new RequestDataStream(request);
        
        boolean importError = false;
        
        List<BaseBean> importItemList = new ArrayList<BaseBean>(); 
        
        StringBuilder builder = new StringBuilder();
        
        try
        {
            rds.parser();
        }
        catch (Exception e1)
        {
            _logger.error(e1, e1);

            request.setAttribute(KeyConstant.ERROR_MESSAGE, "解析失败");

            return mapping.findForward("importOutId");
        }

        if ( !rds.haveStream())
        {
            request.setAttribute(KeyConstant.ERROR_MESSAGE, "解析失败");

            return mapping.findForward("importOutId");
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
                	BaseBean bean = new BaseBean();
                    
                	// 销售单号
            		if ( !StringTools.isNullOrNone(obj[0]))
            		{
            			String outId = obj[0].trim(); 
            			
            			// 须为销售领样单
            			OutBean out = outDAO.find(outId);
            			
            			if (null == out)
            			{
            				builder
                            .append("第[" + currentNumber + "]错误:")
                            .append("销售单"+ outId +"不存在")
                            .append("<br>");
                        
                			importError = true;
            			} else {
            				if (out.getType() == OutConstant.OUT_TYPE_OUTBILL) {
            					if (out.getStatus() != OutConstant.STATUS_SUBMIT)
                				{
            						builder
                                    .append("第[" + currentNumber + "]错误:")
                                    .append("销售单号不是待商务审批态，不能操作.")
                                    .append("<br>");
                                
                        			importError = true;
                				}else{
                					bean.setOutId(outId);
                				}
            				} else {
            					builder
                                .append("第[" + currentNumber + "]错误:")
                                .append("销售单号须是销售单，不能为入库单.")
                                .append("<br>");
                            
                    			importError = true;
            				}
            			}
            		}
            		else
            		{
            			builder
                        .append("第[" + currentNumber + "]错误:")
                        .append("销售单号不能为空")
                        .append("<br>");
                    
            			importError = true;
            		}
            		
            		// 商品
            		if (!StringTools.isNullOrNone(obj[1]))
            		{
            			String name = obj[1].trim();
            			
            			if (!StringTools.isNullOrNone(bean.getOutId())) {
            				List<BaseBean> baseList = baseDAO.queryEntityBeansByCondition(" where BaseBean.outid = ? and BaseBean.productname=?", bean.getOutId(), name);
            				
            				if (ListTools.isEmptyOrNull(baseList)) {
            					builder
                                .append("第[" + currentNumber + "]错误:")
                                .append("销售单产品"+ name +"不存在")
                                .append("<br>");
                            
                    			importError = true;
            				} else {
            					bean.setProductId(baseList.get(0).getProductId());
            				}
            			}
            		}
            		else
            		{
            			builder
                        .append("第[" + currentNumber + "]错误:")
                        .append("销售单产品不能为空")
                        .append("<br>");
                    
            			importError = true;
            		}
            		
            		// 仓库
            		if ( !StringTools.isNullOrNone(obj[2]))
            		{
            			String depotName = obj[2];
            			
            			DepotBean depot = depotDAO.findByUnique(depotName);
            			
            			if (null == depot)
            			{
            				builder
            	            .append("第[" + currentNumber + "]错误:")
            	            .append("仓库" + depotName + "不存在")
            	            .append("<br>");
            				
            				importError = true;
            			}else{
            				bean.setLocationId(depot.getId());
            			}
            		}else
            		{
            			builder
                        .append("第[" + currentNumber + "]错误:")
                        .append("仓库不能为空")
                        .append("<br>");
            			
            			importError = true;
            		}
            		
            		// 仓区
            		if ( !StringTools.isNullOrNone(obj[3]))
            		{
            			String depotpartName = obj[3].trim();
            			
            			DepotpartBean depotpart = depotpartDAO.findByUnique(depotpartName);
            			
            			if (null == depotpart)
            			{
            				builder
            	            .append("第[" + currentNumber + "]错误:")
            	            .append("仓区[" + depotpartName + "]不存在")
            	            .append("<br>");
            				
            				importError = true;
            			}else{
            				if (!depotpart.getLocationId().equals(bean.getLocationId()))
            				{
            					builder
            		            .append("第[" + currentNumber + "]错误:")
            		            .append("仓区[" +depotpartName + "]对应的仓库不是导入中的仓库")
            		            .append("<br>");
            					
            					importError = true;
            				}else
            				{
            					bean.setDepotpartId(depotpart.getId());
            					bean.setDepotpartName(depotpart.getName());
            				}
            			}
            		}else
            		{
            			builder
                        .append("第[" + currentNumber + "]错误:")
                        .append("仓区不能为空")
                        .append("<br>");
            			
            			importError = true;
            		}
            		
                    importItemList.add(bean);
                    
                }
                else
                {
                    builder
                        .append("第[" + currentNumber + "]错误:")
                        .append("数据长度不足26格错误")
                        .append("<br>");
                    
                    importError = true;
                }
            }
        }catch (Exception e)
        {
            _logger.error(e, e);
            
            request.setAttribute(KeyConstant.ERROR_MESSAGE, e.toString());

            return mapping.findForward("importConsign");
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

            return mapping.findForward("importOutId");
        }
        
        try
        {
        	outImportManager.batchUpdateDepot(importItemList);
        	
        	request.setAttribute(KeyConstant.MESSAGE, "批量更新成功");
        }
        catch(MYException e)
        {
            request.setAttribute(KeyConstant.ERROR_MESSAGE, "导入出错:"+ e.getErrorContent());

            return mapping.findForward("importOutId");
        }
        
        return mapping.findForward("importOutId");
	}
    
    /**
     * preForSplitOut
     * 
     * 按商品合计数量，统一检查是可能库与需要的库存
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward preForSplitOut(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
	throws ServletException
	{
    	String id = request.getParameter("id");
    	
    	OutImportLogBean logBean = outImportLogDAO.find(id);
    	
    	if (null == logBean){
    		return ActionTools.toError("数据异常,请重新操作", "queryOutImportLog", mapping, request);
    	}
    	
    	if (logBean.getStatus() != OutImportConstant.LOGSTATUS_SUCCESSFULL)
    	{
    		return ActionTools.toError("只能预占导入成功的批次", "queryOutImportLog", mapping, request);
    	}
    	
    	String batchId = logBean.getBatchId();
    	
    	List<OutImportBean> resultList = outImportManager.preUseAmountCheck(batchId);
    	
    	request.setAttribute("batchId", batchId);
    	
    	request.setAttribute("resultList", resultList);
    	
    	return mapping.findForward("querySplitOut");
	}
    
    /**
     * processSplitOut
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward processSplitOut(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
	throws ServletException
	{
    	String batchId = request.getParameter("batchId");

    	outImportManager.processSplitOut(batchId);
    	
    	request.setAttribute("sbatchId", batchId);
    	
    	request.setAttribute(KeyConstant.MESSAGE, "预占操作完成，结果可以在导入数据中查看");
    	
    	return mapping.findForward("queryOutImport");
	}
    
    /**
     * importBankSail
     * 银行销售导入
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward importBankSail(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
	throws ServletException
	{
    	User user = Helper.getUser(request);
    	
        RequestDataStream rds = new RequestDataStream(request);
        
        boolean importError = false;
        
        List<BankSailBean> importItemList = new ArrayList<BankSailBean>(); 
        
        StringBuilder builder = new StringBuilder();
        
        try
        {
            rds.parser();
        }
        catch (Exception e1)
        {
            _logger.error(e1, e1);

            request.setAttribute(KeyConstant.ERROR_MESSAGE, "解析失败");

            return mapping.findForward("importBankSail");
        }

        if ( !rds.haveStream())
        {
            request.setAttribute(KeyConstant.ERROR_MESSAGE, "解析失败");

            return mapping.findForward("importBankSail");
        }
        
        String itype = rds.getParameter("type"); 
        
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
                	BankSailBean bean = new BankSailBean();
                    
                	// 销售日期
            		if ( !StringTools.isNullOrNone(obj[0]))
            		{
            			String eL = "[0-9]{4}-[0-9]{2}-[0-9]{2}";
            			Pattern p = Pattern.compile(eL);
            			Matcher m = p.matcher(obj[0].trim());
            			boolean dateFlag = m.matches();
            			if (!dateFlag) {
            				builder
                            .append("第[" + currentNumber + "]错误:")
                            .append("销售日期格式错误，如 2000-01-01")
                            .append("<br>");
                        
                			importError = true;
            			}
            			
            			bean.setOutTime(obj[0].trim());
            		} else {
            			builder
                        .append("第[" + currentNumber + "]错误:")
                        .append("销售日期不能为空")
                        .append("<br>");
            			
            			importError = true;
            		}

            		// 网点名称（客户）
            		if ( !StringTools.isNullOrNone(obj[1]))
            		{
            			String name = obj[1].trim();
            			
            			CustomerBean customer = customerMainDAO.findByUnique(name + "（银行）");
            			
            			if (null == customer) {
            				builder
                            .append("第[" + currentNumber + "]错误:")
                            .append("网点名称（客户）不存在")
                            .append("<br>");
                			
                			importError = true;
            			} else {
            				bean.setCustomerId(customer.getId());
            				bean.setCustomerName(customer.getName());
            			}
            		} else {
            			builder
                        .append("第[" + currentNumber + "]错误:")
                        .append("网点名称（客户）不能为空")
                        .append("<br>");
            			
            			importError = true;
            		}

            		// 金额
            		if ( !StringTools.isNullOrNone(obj[2]))
            		{
            			double value = MathTools.parseDouble(obj[2].trim());
            			
            			if (value <= 0) {
            				builder
                            .append("第[" + currentNumber + "]错误:")
                            .append("金额只能是大于0的数字")
                            .append("<br>");
                			
                			importError = true;
            			} else {
            				bean.setValue(value);
            			}
            			
            		} else {
            			builder
                        .append("第[" + currentNumber + "]错误:")
                        .append("金额不能为空")
                        .append("<br>");
            			
            			importError = true;
            		}
                	
            		// 商品
            		if ( !StringTools.isNullOrNone(obj[3]))
            		{
            			bean.setProductName(obj[3].trim());
            		} else {
            			builder
                        .append("第[" + currentNumber + "]错误:")
                        .append("商品不能为空")
                        .append("<br>");
            			
            			importError = true;
            		}
            		
            		// 数量
            		if ( !StringTools.isNullOrNone(obj[4]))
            		{
            			int amount = MathTools.parseInt(obj[4]);
            			
            			if (amount <= 0) {
            				builder
                            .append("第[" + currentNumber + "]错误:")
                            .append("商品不能为空")
                            .append("<br>");
                			
                			importError = true;
            			} else {
            				bean.setAmount(amount);
            			}
            		} else {
            			builder
                        .append("第[" + currentNumber + "]错误:")
                        .append("商品不能为空")
                        .append("<br>");
            			
            			importError = true;
            		}
            		
            		//  中收
            		if ( !StringTools.isNullOrNone(obj[5]))
            		{
            			double value = MathTools.parseDouble(obj[5].trim());
            			
            			if (value < 0) {
            				builder
                            .append("第[" + currentNumber + "]错误:")
                            .append("中收只能是大于或等于0的数字")
                            .append("<br>");
                			
                			importError = true;
            			} else {
            				bean.setMidincome(value);
            			}
            			
            		} else {
            			builder
                        .append("第[" + currentNumber + "]错误:")
                        .append("中收不能为空")
                        .append("<br>");
            			
            			importError = true;
            		}
                    
                    bean.setLogTime(TimeTools.now());
                    bean.setStafferName(user.getStafferName());
                    
                    bean.setType(MathTools.parseInt(itype));
                    
                    importItemList.add(bean);
                    
                }
                else
                {
                    builder
                        .append("第[" + currentNumber + "]错误:")
                        .append("数据长度不足32格错误")
                        .append("<br>");
                    
                    importError = true;
                }
            }
            
            
        }catch (Exception e)
        {
            _logger.error(e, e);
            
            request.setAttribute(KeyConstant.ERROR_MESSAGE, e.toString());

            return mapping.findForward("importBankSail");
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

            return mapping.findForward("importBankSail");
        }
        
        try
        {
        	outImportManager.addBankSail(user, importItemList);
        }
        catch(MYException e)
        {
            request.setAttribute(KeyConstant.ERROR_MESSAGE, "导入出错:"+ e.getErrorContent());

            return mapping.findForward("importBankSail");
        }
        
        return mapping.findForward("importBankSail");
	}
    
    /**
     * queryBankSail
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward queryBankSail(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
	throws ServletException
	{
        ConditionParse condtion = new ConditionParse();

        condtion.addWhereStr();

        // 默认 查询条件
        Map<String, String> initMap = initLogTime(request, condtion);
        
        ActionTools.processJSONDataQueryCondition(QUERYBANKSAIL, request,
				condtion, initMap);

        condtion.addCondition(" order by BankSailBean.logTime desc");
        
        String jsonstr = ActionTools.queryVOByJSONAndToString(QUERYBANKSAIL, request, condtion,
            this.bankSailDAO);

        return JSONTools.writeResponse(response, jsonstr);
	}
    
    private Map<String, String> initLogTime(HttpServletRequest request,
			ConditionParse condtion) {
		Map<String, String> changeMap = new HashMap<String, String>();

		String alogTime = request.getParameter("beginDate");

		String blogTime = request.getParameter("beginDate1");

		if (StringTools.isNullOrNone(alogTime)
				&& StringTools.isNullOrNone(blogTime)) {
			changeMap.put("beginDate", TimeTools.now_short(-30));

			changeMap.put("beginDate1", TimeTools.now_short());

			condtion.addCondition("BankSailBean.logTime", ">=",
					TimeTools.now_short(-30) + " 00:00:00");

			condtion.addCondition("BankSailBean.logTime", "<=",
					TimeTools.now_short() + " 23:59:59");
		}

		return changeMap;
	}
    
    private Map<String, String> initLogTime1(HttpServletRequest request,
			ConditionParse condtion) {
		Map<String, String> changeMap = new HashMap<String, String>();

		String alogTime = request.getParameter("beginDate");

		String blogTime = request.getParameter("beginDate1");

		if (StringTools.isNullOrNone(alogTime)
				&& StringTools.isNullOrNone(blogTime)) {
			changeMap.put("beginDate", TimeTools.now_short(-30));

			changeMap.put("beginDate1", TimeTools.now_short());

			condtion.addCondition("OutImportBean.logTime", ">=",
					TimeTools.now_short(-30) + " 00:00:00");

			condtion.addCondition("OutImportBean.logTime", "<=",
					TimeTools.now_short() + " 23:59:59");
		}

		return changeMap;
	}
    
    /**
     * deleteBankSail
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward deleteBankSail(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
	throws ServletException
	{
    	String batchId = request.getParameter("id");
    	
    	AjaxResult ajax = new AjaxResult();
    	
    	try {
    		User user = Helper.getUser(request);
    		
    		outImportManager.deleteBankSail(user, batchId);
    		
    		ajax.setSuccess("删除成功");
    	} catch (MYException e) {
    		_logger.warn(e,e);
    		
    		ajax.setError("删除失败");
    	}
    	
    	return JSONTools.writeResponse(response, ajax);
	}
    
    /**
     * importEstimateProfit
     */
    public ActionForward importEstimateProfit(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
	throws ServletException
	{
    	User user = Helper.getUser(request);
    	
        RequestDataStream rds = new RequestDataStream(request);
        
        boolean importError = false;
        
        List<EstimateProfitBean> importItemList = new ArrayList<EstimateProfitBean>(); 
        
        StringBuilder builder = new StringBuilder();
        
        try
        {
            rds.parser();
        }
        catch (Exception e1)
        {
            _logger.error(e1, e1);

            request.setAttribute(KeyConstant.ERROR_MESSAGE, "解析失败");

            return mapping.findForward("importEstimateProfit");
        }

        if ( !rds.haveStream())
        {
            request.setAttribute(KeyConstant.ERROR_MESSAGE, "解析失败");

            return mapping.findForward("importEstimateProfit");
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
                	EstimateProfitBean bean = new EstimateProfitBean();
                    
                	// 产品
            		if ( !StringTools.isNullOrNone(obj[0]))
            		{
            			String name = obj[0].trim();
            			
            			bean.setProductName(name);
            			
            		} else {
            			builder
                        .append("第[" + currentNumber + "]错误:")
                        .append("产品不能为空")
                        .append("<br>");
            			
            			importError = true;
            		}

            		// 预估毛利
            		if ( !StringTools.isNullOrNone(obj[1]))
            		{
            			double value = MathTools.parseDouble(obj[1].trim());
            			
            			if (value <= 0) {
            				builder
                            .append("第[" + currentNumber + "]错误:")
                            .append("预估毛利 只能为大于0的数字")
                            .append("<br>");
                			
                			importError = true;
            			} else {
            				bean.setProfit(value);
            			}
            			
            		} else {
            			builder
                        .append("第[" + currentNumber + "]错误:")
                        .append("预估毛利不能为空")
                        .append("<br>");
            			
            			importError = true;
            		}

                    bean.setLogTime(TimeTools.now());
                    bean.setStafferName(user.getStafferName());
                    
                    importItemList.add(bean);
                    
                }
                else
                {
                    builder
                        .append("第[" + currentNumber + "]错误:")
                        .append("数据长度不足32格错误")
                        .append("<br>");
                    
                    importError = true;
                }
            }
            
            
        }catch (Exception e)
        {
            _logger.error(e, e);
            
            request.setAttribute(KeyConstant.ERROR_MESSAGE, e.toString());

            return mapping.findForward("importEstimateProfit");
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

            return mapping.findForward("importEstimateProfit");
        }
        
        try
        {
        	outImportManager.addEstimateProfit(user, importItemList);
        }
        catch(MYException e)
        {
            request.setAttribute(KeyConstant.ERROR_MESSAGE, "导入出错:"+ e.getErrorContent());

            return mapping.findForward("importEstimateProfit");
        }
        
        return mapping.findForward("importEstimateProfit");
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
    public ActionForward deleteEstimateProfit(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
	throws ServletException
	{
    	String id = request.getParameter("id");
    	
    	AjaxResult ajax = new AjaxResult();
    	
    	try {
    		User user = Helper.getUser(request);
    		
    		outImportManager.deleteEstimateProfit(user, id);
    		
    		ajax.setSuccess("删除成功");
    	} catch (MYException e) {
    		_logger.warn(e,e);
    		
    		ajax.setError("删除失败");
    	}
    	
    	return JSONTools.writeResponse(response, ajax);
	}
    
	public CustomerMainDAO getCustomerMainDAO() {
		return customerMainDAO;
	}

	public void setCustomerMainDAO(CustomerMainDAO customerMainDAO) {
		this.customerMainDAO = customerMainDAO;
	}

	public OutImportDAO getOutImportDAO()
	{
		return outImportDAO;
	}

	public void setOutImportDAO(OutImportDAO outImportDAO)
	{
		this.outImportDAO = outImportDAO;
	}

	public OutImportManager getOutImportManager()
	{
		return outImportManager;
	}

	public void setOutImportManager(OutImportManager outImportManager)
	{
		this.outImportManager = outImportManager;
	}

	public OutImportResultDAO getOutImportResultDAO()
	{
		return outImportResultDAO;
	}

	public void setOutImportResultDAO(OutImportResultDAO outImportResultDAO)
	{
		this.outImportResultDAO = outImportResultDAO;
	}

	public ReplenishmentDAO getReplenishmentDAO()
	{
		return replenishmentDAO;
	}

	public void setReplenishmentDAO(ReplenishmentDAO replenishmentDAO)
	{
		this.replenishmentDAO = replenishmentDAO;
	}

	public StorageRelationManager getStorageRelationManager()
	{
		return storageRelationManager;
	}

	public void setStorageRelationManager(
			StorageRelationManager storageRelationManager)
	{
		this.storageRelationManager = storageRelationManager;
	}

	public CiticVSOAProductDAO getCiticVSOAProductDAO()
	{
		return citicVSOAProductDAO;
	}

	public void setCiticVSOAProductDAO(CiticVSOAProductDAO citicVSOAProductDAO)
	{
		this.citicVSOAProductDAO = citicVSOAProductDAO;
	}

	public ProductDAO getProductDAO()
	{
		return productDAO;
	}

	public void setProductDAO(ProductDAO productDAO)
	{
		this.productDAO = productDAO;
	}

	public OutImportLogDAO getOutImportLogDAO()
	{
		return outImportLogDAO;
	}

	public void setOutImportLogDAO(OutImportLogDAO outImportLogDAO)
	{
		this.outImportLogDAO = outImportLogDAO;
	}

	public StafferDAO getStafferDAO()
	{
		return stafferDAO;
	}

	public void setStafferDAO(StafferDAO stafferDAO)
	{
		this.stafferDAO = stafferDAO;
	}

	public StafferVSCustomerDAO getStafferVSCustomerDAO()
	{
		return stafferVSCustomerDAO;
	}

	public void setStafferVSCustomerDAO(StafferVSCustomerDAO stafferVSCustomerDAO)
	{
		this.stafferVSCustomerDAO = stafferVSCustomerDAO;
	}

	public CiticBranchDAO getCiticBranchDAO()
	{
		return citicBranchDAO;
	}

	public void setCiticBranchDAO(CiticBranchDAO citicBranchDAO)
	{
		this.citicBranchDAO = citicBranchDAO;
	}

	public CiticVSStafferDAO getCiticVSStafferDAO()
	{
		return citicVSStafferDAO;
	}

	public void setCiticVSStafferDAO(CiticVSStafferDAO citicVSStafferDAO)
	{
		this.citicVSStafferDAO = citicVSStafferDAO;
	}

	/**
	 * @return the expressDAO
	 */
	public ExpressDAO getExpressDAO()
	{
		return expressDAO;
	}

	/**
	 * @param expressDAO the expressDAO to set
	 */
	public void setExpressDAO(ExpressDAO expressDAO)
	{
		this.expressDAO = expressDAO;
	}

	/**
	 * @return the batchApproveDAO
	 */
	public BatchApproveDAO getBatchApproveDAO()
	{
		return batchApproveDAO;
	}

	/**
	 * @param batchApproveDAO the batchApproveDAO to set
	 */
	public void setBatchApproveDAO(BatchApproveDAO batchApproveDAO)
	{
		this.batchApproveDAO = batchApproveDAO;
	}

	/**
	 * @return the batchSwatchDAO
	 */
	public BatchSwatchDAO getBatchSwatchDAO()
	{
		return batchSwatchDAO;
	}

	/**
	 * @param batchSwatchDAO the batchSwatchDAO to set
	 */
	public void setBatchSwatchDAO(BatchSwatchDAO batchSwatchDAO)
	{
		this.batchSwatchDAO = batchSwatchDAO;
	}

	/**
	 * @return the consignDAO
	 */
	public ConsignDAO getConsignDAO()
	{
		return consignDAO;
	}

	/**
	 * @param consignDAO the consignDAO to set
	 */
	public void setConsignDAO(ConsignDAO consignDAO)
	{
		this.consignDAO = consignDAO;
	}

	public DepotDAO getDepotDAO()
	{
		return depotDAO;
	}

	public void setDepotDAO(DepotDAO depotDAO)
	{
		this.depotDAO = depotDAO;
	}

	public DepotpartDAO getDepotpartDAO()
	{
		return depotpartDAO;
	}

	public void setDepotpartDAO(DepotpartDAO depotpartDAO)
	{
		this.depotpartDAO = depotpartDAO;
	}

	public OutDAO getOutDAO()
	{
		return outDAO;
	}

	public void setOutDAO(OutDAO outDAO)
	{
		this.outDAO = outDAO;
	}

	public ProvinceDAO getProvinceDAO()
	{
		return provinceDAO;
	}

	public void setProvinceDAO(ProvinceDAO provinceDAO)
	{
		this.provinceDAO = provinceDAO;
	}

	public CityDAO getCityDAO()
	{
		return cityDAO;
	}

	public void setCityDAO(CityDAO cityDAO)
	{
		this.cityDAO = cityDAO;
	}

	public AreaDAO getAreaDAO()
	{
		return areaDAO;
	}

	public void setAreaDAO(AreaDAO areaDAO)
	{
		this.areaDAO = areaDAO;
	}

	/**
	 * @return the baseDAO
	 */
	public BaseDAO getBaseDAO()
	{
		return baseDAO;
	}

	/**
	 * @param baseDAO the baseDAO to set
	 */
	public void setBaseDAO(BaseDAO baseDAO)
	{
		this.baseDAO = baseDAO;
	}

	/**
	 * @return the bankSailDAO
	 */
	public BankSailDAO getBankSailDAO()
	{
		return bankSailDAO;
	}

	/**
	 * @param bankSailDAO the bankSailDAO to set
	 */
	public void setBankSailDAO(BankSailDAO bankSailDAO)
	{
		this.bankSailDAO = bankSailDAO;
	}

	/**
	 * @return the estimateProfitDAO
	 */
	public EstimateProfitDAO getEstimateProfitDAO()
	{
		return estimateProfitDAO;
	}

	/**
	 * @param estimateProfitDAO the estimateProfitDAO to set
	 */
	public void setEstimateProfitDAO(EstimateProfitDAO estimateProfitDAO)
	{
		this.estimateProfitDAO = estimateProfitDAO;
	}
}
