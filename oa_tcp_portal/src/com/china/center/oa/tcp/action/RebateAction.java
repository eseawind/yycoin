package com.china.center.oa.tcp.action;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
import com.china.center.actionhelper.common.ActionTools;
import com.china.center.actionhelper.common.JSONTools;
import com.china.center.actionhelper.common.KeyConstant;
import com.china.center.actionhelper.json.AjaxResult;
import com.china.center.actionhelper.query.HandleResult;
import com.china.center.common.MYException;
import com.china.center.jdbc.util.ConditionParse;
import com.china.center.oa.finance.bean.OutBillBean;
import com.china.center.oa.finance.dao.OutBillDAO;
import com.china.center.oa.product.bean.ProductBean;
import com.china.center.oa.product.dao.ProductDAO;
import com.china.center.oa.publics.Helper;
import com.china.center.oa.publics.bean.FlowLogBean;
import com.china.center.oa.publics.bean.PrincipalshipBean;
import com.china.center.oa.publics.bean.StafferBean;
import com.china.center.oa.publics.dao.FlowLogDAO;
import com.china.center.oa.publics.dao.PrincipalshipDAO;
import com.china.center.oa.publics.dao.StafferDAO;
import com.china.center.oa.publics.vo.FlowLogVO;
import com.china.center.oa.tax.bean.FinanceBean;
import com.china.center.oa.tax.dao.FinanceDAO;
import com.china.center.oa.tcp.bean.OutBatchPriceBean;
import com.china.center.oa.tcp.bean.RebateApplyBean;
import com.china.center.oa.tcp.bean.TcpApproveBean;
import com.china.center.oa.tcp.bean.TcpFlowBean;
import com.china.center.oa.tcp.bean.TravelApplyPayBean;
import com.china.center.oa.tcp.constanst.TcpConstanst;
import com.china.center.oa.tcp.constanst.TcpFlowConstant;
import com.china.center.oa.tcp.dao.RebateApplyDAO;
import com.china.center.oa.tcp.dao.TcpApproveDAO;
import com.china.center.oa.tcp.dao.TcpFlowDAO;
import com.china.center.oa.tcp.helper.TCPHelper;
import com.china.center.oa.tcp.manager.RebateManager;
import com.china.center.oa.tcp.vo.RebateApplyVO;
import com.china.center.oa.tcp.vo.TcpApproveVO;
import com.china.center.oa.tcp.wrap.TcpParamWrap;
import com.china.center.tools.BeanUtil;
import com.china.center.tools.CommonTools;
import com.china.center.tools.MathTools;
import com.china.center.tools.RequestDataStream;
import com.china.center.tools.StringTools;
import com.china.center.tools.TimeTools;

/**
 * 
 * 返利
 *
 * @author fangliwen 2013-8-31
 */
public class RebateAction extends DispatchAction
{
	private final Log _logger = LogFactory.getLog(getClass());
	
	private StafferDAO stafferDAO = null;
	
	private RebateApplyDAO rebateApplyDAO = null;
	
	private RebateManager rebateManager = null;
	
	private OutBillDAO outBillDAO = null;
	
	private FinanceDAO financeDAO = null;
	
	private FlowLogDAO flowLogDAO = null;
	
	private TcpApproveDAO tcpApproveDAO = null;
	
	private TcpFlowDAO tcpFlowDAO = null;
	
	private ProductDAO productDAO = null;
	
	private PrincipalshipDAO principalshipDAO = null;
	
	private final static String QUERYSELFREBATE = "querySelfRebate";
	
	/**
	 * 
	 */
	public RebateAction()
	{
		
	}

	/**
     * querySelfRebate
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward querySelfRebate(ActionMapping mapping, ActionForm form,
                                          HttpServletRequest request, HttpServletResponse response)
        throws ServletException
    {
        User user = Helper.getUser(request);

        ConditionParse condtion = new ConditionParse();

        condtion.addWhereStr();

        ActionTools.processJSONQueryCondition(QUERYSELFREBATE, request, condtion);

        condtion.addCondition("RebateApplyBean.stafferId", "=", user.getStafferId());

        String type = request.getParameter("type");

        condtion.addIntCondition("RebateApplyBean.type", "=", type);

        condtion.addCondition("order by RebateApplyBean.logTime desc");

        String jsonstr = ActionTools.queryVOByJSONAndToString(QUERYSELFREBATE, request, condtion,
            this.rebateApplyDAO, new HandleResult<RebateApplyVO>()
            {
                public void handle(RebateApplyVO vo)
                {
                    TCPHelper.chageVO(vo);

                    // 当前处理人
                    List<TcpApproveVO> approveList = tcpApproveDAO.queryEntityVOsByFK(vo.getId());

                    for (TcpApproveVO tcpApproveVO : approveList)
                    {
                        if (tcpApproveVO.getPool() == TcpConstanst.TCP_POOL_COMMON)
                        {
                            vo.setProcesser(vo.getProcesser() + tcpApproveVO.getApproverName()
                                            + ';');
                        }
                    }
                }
            });

        return JSONTools.writeResponse(response, jsonstr);
    }
	
    /**
     * preForAddRebate
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward preForAddRebate(ActionMapping mapping, ActionForm form,
                                          HttpServletRequest request, HttpServletResponse response)
        throws ServletException
    {
        prepareInner(request);

        return mapping.findForward("addRebate");
    }
    
    /**
     * prepareInner
     * 
     * @param request
     */
    private void prepareInner(HttpServletRequest request)
    {
        // 群组
        request.setAttribute("pluginType", "group");

        request.setAttribute("pluginValue", TcpFlowConstant.GROUP_DM);
    }
    
	/**
	 * 增加/更新
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 */
	public ActionForward addOrUpdateRebate(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
	throws ServletException
	{
		CommonTools.saveParamers(request);

		RebateApplyBean bean = new RebateApplyBean();

        BeanUtil.getBean(bean, request);

        String addOrUpdate = request.getParameter("addOrUpdate");

        String oprType = request.getParameter("oprType");

        String processId = request.getParameter("processId");

        StafferBean stafferBean = stafferDAO.find(bean.getStafferId());

        bean.setStype(stafferBean.getOtype());
        
        // 子项的组装
        fillExpense(request, bean);

        try
        {
            User user = Helper.getUser(request);

            bean.setLogTime(TimeTools.now());

            if ("0".equals(addOrUpdate))
            {
                rebateManager.addRebateBean(user, bean);
            }
            else
            {
            	
            	rebateManager.updateRebateBean(user, bean);
            }

            request.setAttribute(KeyConstant.MESSAGE, "成功保存返利申请");

            // 提交
            if ("1".equals(oprType))
            {
            	rebateManager.submitRebateBean(user, bean.getId(), processId);
            }

            request.setAttribute(KeyConstant.MESSAGE, "成功提交返利申请");
        }
        catch (MYException e)
        {
            _logger.warn(e, e);

            request.setAttribute(KeyConstant.ERROR_MESSAGE, "操作返利申请失败:" + e.getMessage());
        }

        return mapping.findForward("querySelfRebate");
    }
	
	/**
	 * 
	 * @param request
	 * @param bean
	 */
    private void fillExpense(HttpServletRequest request, RebateApplyBean bean)
    {
        String total = request.getParameter("total");

        bean.setTotal(MathTools.doubleToLong2(total));   
    	
        List<TravelApplyPayBean> payList = new ArrayList<TravelApplyPayBean>();

        bean.setPayList(payList);
    	
        String [] receiveTypeList = request.getParameterValues("p_receiveType");
        String [] bankList = request.getParameterValues("p_bank");
        String [] userNameList = request.getParameterValues("p_userName");
        String [] bankNoList = request.getParameterValues("p_bankNo");
        String [] pmoneysList = request.getParameterValues("p_moneys");
        String [] pdescriptionList = request.getParameterValues("p_description");

        if (receiveTypeList != null && receiveTypeList.length > 0)
        {

            for (int i = 0; i < receiveTypeList.length; i++ )
            {
                String each = receiveTypeList[i];

                if (StringTools.isNullOrNone(each))
                {
                    continue;
                }

                TravelApplyPayBean pay = new TravelApplyPayBean();

                pay.setReceiveType(MathTools.parseInt(receiveTypeList[i]));
                pay.setBankName(bankList[i]);
                pay.setUserName(userNameList[i]);
                pay.setBankNo(bankNoList[i]);
                pay.setMoneys(TCPHelper.doubleToLong2(pmoneysList[i]));
                pay.setDescription(pdescriptionList[i]);

                payList.add(pay);
            }
        }
    }

    /**
     * 审批
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward processRebateBean(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
	throws ServletException
	{
        String id = request.getParameter("id");
        String oprType = request.getParameter("oprType");
        String reason = request.getParameter("reason");
        String processId = request.getParameter("processId");
        
        try
        {
            User user = Helper.getUser(request);

            TcpParamWrap param = new TcpParamWrap();

            param.setId(id);
            param.setType(oprType);
            param.setReason(reason);
            param.setProcessId(processId);

            // 组装参数
            fillWrap(request, param);

            // 提交
            if ("0".equals(oprType))
            {
                rebateManager.passRebateBean(user, param);
            }
            else
            {
            	rebateManager.rejectRebateBean(user, param);
            }

            request.setAttribute(KeyConstant.MESSAGE, "成功处理返利申请");
        }
        catch (MYException e)
        {
            _logger.warn(e, e);

            request.setAttribute(KeyConstant.ERROR_MESSAGE, "处理返利申请失败:" + e.getMessage());
        }

        return mapping.findForward("querySelfApprove");
    }
    
    /**
     * fillWrap
     * 
     * @param request
     * @param param
     * @throws MYException
     */
    private void fillWrap(HttpServletRequest request, TcpParamWrap param)
        throws MYException
    {
        String[] bankIds = request.getParameterValues("bankId");

        // 财务付款/收款
        if (bankIds != null && bankIds.length > 0)
        {
            String[] payTypes = request.getParameterValues("payType");
            String[] moneys = request.getParameterValues("money");

            // 付款

            List<OutBillBean> outBillList = new ArrayList<OutBillBean>();

            for (int i = 0; i < bankIds.length; i++ )
            {
                if (StringTools.isNullOrNone(bankIds[i]))
                {
                    continue;
                }

                OutBillBean outBill = new OutBillBean();

                outBill.setBankId(bankIds[i]);

                outBill.setPayType(MathTools.parseInt(payTypes[i]));

                outBill.setMoneys(MathTools.parseDouble(moneys[i]));

                outBillList.add(outBill);
            }

            param.setOther(outBillList);
        
        }
    }
    
    /**
     * 查找
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward findRebate(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
	throws ServletException
	{
        User user = Helper.getUser(request);

        String id = request.getParameter("id");

        String update = request.getParameter("update");

        RebateApplyVO bean = rebateManager.findVO(id);

        if (bean == null)
        {
            return ActionTools.toError("数据异常,请重新操作", mapping, request);
        }

        //prepareInner(request);

        request.setAttribute("bean", bean);

        request.setAttribute("update", update);

        // 查询关联的付款单和凭证
        List<OutBillBean> billList = outBillDAO.queryEntityBeansByFK(id);

        request.setAttribute("billList", billList);

        List<FinanceBean> financeList = financeDAO.queryEntityBeansByFK(id);

        request.setAttribute("financeList", financeList);

        // 2是稽核修改
        if ("1".equals(update) || "3".equals(update))
        {
            if ( !canTravelApplyDelete(bean))
            {
                return ActionTools.toError("申请当前状态下不能被修改", mapping, request);
            }

            return mapping.findForward("updateRebate");
        }

        // 获取审批日志
        List<FlowLogBean> logs = flowLogDAO.queryEntityBeansByFK(id);

        List<FlowLogVO> logsVO = new ArrayList<FlowLogVO>();

        for (FlowLogBean flowLogBean : logs)
        {
            logsVO.add(TCPHelper.getTCPFlowLogVO(flowLogBean));
        }

        request.setAttribute("logList", logsVO);

        // 处理
        if ("2".equals(update))
        {
            // 先鉴权
            List<TcpApproveBean> approveList = tcpApproveDAO.queryEntityBeansByFK(id);

            boolean hasAuth = false;
            
            for (TcpApproveBean tcpApproveBean : approveList)
            {
                if (tcpApproveBean.getApproverId().equals(user.getStafferId()))
                {
                    hasAuth = true;

                    break;
                }
            }

            if ( !hasAuth)
            {
                return ActionTools.toError("没有处理的权限", mapping, request);
            }

            // 获得当前的处理环节
            TcpFlowBean token = tcpFlowDAO.findByUnique(bean.getFlowKey(), bean.getStatus());
           
            request.setAttribute("token", token);

            if (token.getNextPlugin().startsWith("group"))
            {
                // 群组
                request.setAttribute("pluginType", "group");

                request.setAttribute("pluginValue", token.getNextPlugin().substring(6));
            }
            else
            {
                request.setAttribute("pluginType", "");
                request.setAttribute("pluginValue", "");
            }
            
            return mapping.findForward("processRebate");
        }

        return mapping.findForward("detailRebate");
    }
    
    /**
     * deleteRebate
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward deleteRebate(ActionMapping mapping, ActionForm form,
                                       HttpServletRequest request, HttpServletResponse response)
        throws ServletException
    {
        AjaxResult ajax = new AjaxResult();

        try
        {
            String id = request.getParameter("id");

            User user = Helper.getUser(request);

            rebateManager.deleteRebateBean(user, id);

            ajax.setSuccess("成功操作");
        }
        catch (MYException e)
        {
            _logger.warn(e, e);

            ajax.setError("操作失败:" + e.getMessage());
        }

        return JSONTools.writeResponse(response, ajax);
    }
    
    private boolean canTravelApplyDelete(RebateApplyBean bean)
    {
        if (bean.getStatus() == TcpConstanst.TCP_STATUS_INIT
            || bean.getStatus() == TcpConstanst.TCP_STATUS_REJECT)
        {
            return true;
        }

        return false;
    }
    
    /**
     * 导入出库日批价
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
	public ActionForward importBatchPrice(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
	throws ServletException
	{
		User user = Helper.getUser(request);
		
        RequestDataStream rds = new RequestDataStream(request);
        
        boolean importError = false;
        
        List<OutBatchPriceBean> importItemList = new ArrayList<OutBatchPriceBean>(); 
        
        StringBuilder builder = new StringBuilder();       
        
        try
        {
            rds.parser();
        }
        catch (Exception e1)
        {
            _logger.error(e1, e1);

            request.setAttribute(KeyConstant.ERROR_MESSAGE, "解析失败");

            return mapping.findForward("importBatchPrice");
        }

        if ( !rds.haveStream())
        {
            request.setAttribute(KeyConstant.ERROR_MESSAGE, "解析失败");

            return mapping.findForward("importBatchPrice");
        }
        
        // 获取上次最后一次导入的时间
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
                	OutBatchPriceBean bean = new OutBatchPriceBean();
                    
                	// 起始时间
            		if ( !StringTools.isNullOrNone(obj[0]))
            		{
            			bean.setBeginDate(obj[0]);
            		}else{
            			importError = true;
            			
                        builder
                        .append("第[" + currentNumber + "]错误:")
                        .append("起始时间为空")
                        .append("<br>");
            		}
            		
                	// 事业部
            		if ( !StringTools.isNullOrNone(obj[1]))
            		{
            			String name = obj[1];
            			
            			PrincipalshipBean pb = principalshipDAO.findByUnique(name);
            			
            			if (null == pb)
            			{
            				importError = true;
            				
                            builder
                            .append("第[" + currentNumber + "]错误:")
                            .append("事业部不存在")
                            .append("<br>");
            			}else{
            				bean.setIndustryId(pb.getId());
            			}
            		}else{
            			importError = true;
            			
                        builder
                        .append("第[" + currentNumber + "]错误:")
                        .append("事业部为空")
                        .append("<br>");
            		}
            		
                	// 产品
            		if ( !StringTools.isNullOrNone(obj[2]))
            		{
            			String name = obj[2];
            			
            			ProductBean pb = productDAO.findByName(name);
            			
            			if (null == pb)
            			{
            				importError = true;
            				
                            builder
                            .append("第[" + currentNumber + "]错误:")
                            .append("产品不存在")
                            .append("<br>");
            			}else{
            				bean.setProductId(pb.getId());
            			}
            		}else
            		{
            			importError = true;
            			
                        builder
                        .append("第[" + currentNumber + "]错误:")
                        .append("产品为空")
                        .append("<br>");
            		}

            		// 价格
            		if ( !StringTools.isNullOrNone(obj[3]))
            		{
            			bean.setPrice(MathTools.parseDouble(obj[3]));
            		}else
            		{
            			importError = true;
            			
                        builder
                        .append("第[" + currentNumber + "]错误:")
                        .append("价格为空")
                        .append("<br>");
            		}

            		bean.setEndDate("2099-12-31");
                    bean.setLogTime(TimeTools.now());
                    
                    importItemList.add(bean);
                }
                else
                {
                    builder
                        .append("第[" + currentNumber + "]错误:")
                        .append("数据长度不足4格错误")
                        .append("<br>");
                    
                    importError = true;
                }
            }
            
            
        }catch (Exception e)
        {
            _logger.error(e, e);
            
            request.setAttribute(KeyConstant.ERROR_MESSAGE, e.toString());

            return mapping.findForward("importBatchPrice");
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

            return mapping.findForward("importBatchPrice");
        }
        
        try
        {
        	rebateManager.importBatchPrice(user, importItemList);
        }
        catch(MYException e)
        {
            request.setAttribute(KeyConstant.ERROR_MESSAGE, "导入出错:"+ e.getErrorContent());

            return mapping.findForward("importBatchPrice");
        }
        
        return mapping.findForward("importBatchPrice");
	}
    
    private String[] fillObj(String[] obj)
    {
        String[] result = new String[4];

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
	 * @return the rebateApplyDAO
	 */
	public RebateApplyDAO getRebateApplyDAO()
	{
		return rebateApplyDAO;
	}

	/**
	 * @param rebateApplyDAO the rebateApplyDAO to set
	 */
	public void setRebateApplyDAO(RebateApplyDAO rebateApplyDAO)
	{
		this.rebateApplyDAO = rebateApplyDAO;
	}

	/**
	 * @return the rebateManager
	 */
	public RebateManager getRebateManager()
	{
		return rebateManager;
	}

	/**
	 * @param rebateManager the rebateManager to set
	 */
	public void setRebateManager(RebateManager rebateManager)
	{
		this.rebateManager = rebateManager;
	}

	/**
	 * @return the outBillDAO
	 */
	public OutBillDAO getOutBillDAO()
	{
		return outBillDAO;
	}

	/**
	 * @param outBillDAO the outBillDAO to set
	 */
	public void setOutBillDAO(OutBillDAO outBillDAO)
	{
		this.outBillDAO = outBillDAO;
	}

	/**
	 * @return the financeDAO
	 */
	public FinanceDAO getFinanceDAO()
	{
		return financeDAO;
	}

	/**
	 * @param financeDAO the financeDAO to set
	 */
	public void setFinanceDAO(FinanceDAO financeDAO)
	{
		this.financeDAO = financeDAO;
	}

	/**
	 * @return the flowLogDAO
	 */
	public FlowLogDAO getFlowLogDAO()
	{
		return flowLogDAO;
	}

	/**
	 * @param flowLogDAO the flowLogDAO to set
	 */
	public void setFlowLogDAO(FlowLogDAO flowLogDAO)
	{
		this.flowLogDAO = flowLogDAO;
	}

	/**
	 * @return the tcpApproveDAO
	 */
	public TcpApproveDAO getTcpApproveDAO()
	{
		return tcpApproveDAO;
	}

	/**
	 * @param tcpApproveDAO the tcpApproveDAO to set
	 */
	public void setTcpApproveDAO(TcpApproveDAO tcpApproveDAO)
	{
		this.tcpApproveDAO = tcpApproveDAO;
	}

	/**
	 * @return the tcpFlowDAO
	 */
	public TcpFlowDAO getTcpFlowDAO()
	{
		return tcpFlowDAO;
	}

	/**
	 * @param tcpFlowDAO the tcpFlowDAO to set
	 */
	public void setTcpFlowDAO(TcpFlowDAO tcpFlowDAO)
	{
		this.tcpFlowDAO = tcpFlowDAO;
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
	 * @return the principalshipDAO
	 */
	public PrincipalshipDAO getPrincipalshipDAO()
	{
		return principalshipDAO;
	}

	/**
	 * @param principalshipDAO the principalshipDAO to set
	 */
	public void setPrincipalshipDAO(PrincipalshipDAO principalshipDAO)
	{
		this.principalshipDAO = principalshipDAO;
	}
}
