package com.china.center.oa.tcp.action;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jxl.write.Label;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.center.china.osgi.publics.User;
import com.center.china.osgi.publics.file.writer.WriteFile;
import com.center.china.osgi.publics.file.writer.WriteFileFactory;
import com.china.center.actionhelper.common.ActionTools;
import com.china.center.actionhelper.common.JSONPageSeparateTools;
import com.china.center.actionhelper.common.JSONTools;
import com.china.center.actionhelper.common.KeyConstant;
import com.china.center.actionhelper.json.AjaxResult;
import com.china.center.actionhelper.query.HandleResult;
import com.china.center.common.MYException;
import com.china.center.jdbc.util.ConditionParse;
import com.china.center.jdbc.util.PageSeparate;
import com.china.center.oa.finance.bean.PreInvoiceApplyBean;
import com.china.center.oa.finance.bean.PreInvoiceVSOutBean;
import com.china.center.oa.finance.dao.PreInvoiceApplyDAO;
import com.china.center.oa.finance.dao.PreInvoiceVSOutDAO;
import com.china.center.oa.finance.vo.PreInvoiceApplyVO;
import com.china.center.oa.publics.Helper;
import com.china.center.oa.publics.bean.DutyBean;
import com.china.center.oa.publics.bean.FlowLogBean;
import com.china.center.oa.publics.bean.StafferBean;
import com.china.center.oa.publics.dao.DutyDAO;
import com.china.center.oa.publics.dao.FlowLogDAO;
import com.china.center.oa.publics.dao.StafferDAO;
import com.china.center.oa.publics.vo.FlowLogVO;
import com.china.center.oa.sail.bean.BaseBean;
import com.china.center.oa.sail.bean.OutBean;
import com.china.center.oa.sail.dao.BaseDAO;
import com.china.center.oa.sail.dao.OutDAO;
import com.china.center.oa.tcp.bean.TcpApproveBean;
import com.china.center.oa.tcp.bean.TcpFlowBean;
import com.china.center.oa.tcp.constanst.TcpConstanst;
import com.china.center.oa.tcp.constanst.TcpFlowConstant;
import com.china.center.oa.tcp.dao.TcpApproveDAO;
import com.china.center.oa.tcp.dao.TcpFlowDAO;
import com.china.center.oa.tcp.helper.TCPHelper;
import com.china.center.oa.tcp.manager.PreInvoiceManager;
import com.china.center.oa.tcp.vo.TcpApproveVO;
import com.china.center.oa.tcp.wrap.TcpParamWrap;
import com.china.center.osgi.jsp.ElTools;
import com.china.center.tools.BeanUtil;
import com.china.center.tools.CommonTools;
import com.china.center.tools.ListTools;
import com.china.center.tools.MathTools;
import com.china.center.tools.StringTools;
import com.china.center.tools.TimeTools;
import com.china.center.tools.WriteFileBuffer;

/**
 * 
 * 预开票
 *
 * @author fangliwen 2013-8-31
 */
public class PreinvoiceAction extends DispatchAction
{
	private final Log _logger = LogFactory.getLog(getClass());
	
	private StafferDAO stafferDAO = null;
	
	private PreInvoiceApplyDAO preInvoiceApplyDAO = null;
	
	private PreInvoiceVSOutDAO preInvoiceVSOutDAO = null;
	
	private PreInvoiceManager preInvoiceManager = null;
	
	private FlowLogDAO flowLogDAO = null;
	
	private TcpApproveDAO tcpApproveDAO = null;
	
	private TcpFlowDAO tcpFlowDAO = null;
	
	private DutyDAO dutyDAO = null;
	
	private OutDAO outDAO = null;
	
	private BaseDAO baseDAO = null;
	
	private final static String QUERYSELFPREINVOICE = "querySelfPreInvoice";
	
	private final static String QUERYALLPREINVOICE = "queryAllPreInvoice";
	
	/**
	 * 
	 */
	public PreinvoiceAction()
	{
		
	}

	/**
     * querySelfPreInvoice
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward querySelfPreInvoice(ActionMapping mapping, ActionForm form,
                                          HttpServletRequest request, HttpServletResponse response)
        throws ServletException
    {
        User user = Helper.getUser(request);

        ConditionParse condtion = new ConditionParse();

        condtion.addWhereStr();

        ActionTools.processJSONQueryCondition(QUERYSELFPREINVOICE, request, condtion);

        condtion.addCondition("PreInvoiceApplyBean.stafferId", "=", user.getStafferId());

        String type = request.getParameter("type");

        condtion.addIntCondition("PreInvoiceApplyBean.type", "=", type);

        condtion.addCondition("order by PreInvoiceApplyBean.logTime desc");

        String jsonstr = ActionTools.queryVOByJSONAndToString(QUERYSELFPREINVOICE, request, condtion,
            this.preInvoiceApplyDAO, new HandleResult<PreInvoiceApplyVO>()
            {
                public void handle(PreInvoiceApplyVO vo)
                {
                	vo.setShowTotal(TCPHelper.formatNum2(vo.getTotal() / 100.0d));
                	
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
     * queryAllPreInvoice
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward queryAllPreInvoice(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
	throws ServletException
	{
        ConditionParse condtion = new ConditionParse();

        condtion.addWhereStr();

        ActionTools.processJSONQueryCondition(QUERYALLPREINVOICE, request, condtion);

        String type = request.getParameter("type");

        condtion.addIntCondition("PreInvoiceApplyBean.type", "=", type);

        condtion.addCondition("order by PreInvoiceApplyBean.logTime desc");

        String jsonstr = ActionTools.queryVOByJSONAndToString(QUERYALLPREINVOICE, request, condtion,
            this.preInvoiceApplyDAO, new HandleResult<PreInvoiceApplyVO>()
            {
                public void handle(PreInvoiceApplyVO vo)
                {
                	vo.setShowTotal(TCPHelper.formatNum2(vo.getTotal() / 100.0d));
                	
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
     * exportTravelApply
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward exportPreInvoiceApply(ActionMapping mapping, ActionForm form,
                                           HttpServletRequest request, HttpServletResponse response)
        throws ServletException
    {
        OutputStream out = null;

        String filenName = "PREINVOICE_APPLY_" + TimeTools.now("MMddHHmmss") + ".csv";

        response.setContentType("application/x-dbf");

        response.setHeader("Content-Disposition", "attachment; filename=" + filenName);

        WriteFile write = null;

        ConditionParse condtion = JSONPageSeparateTools.getCondition(request, QUERYALLPREINVOICE);

        int count = this.preInvoiceApplyDAO.countVOByCondition(condtion.toString());

        try
        {
            out = response.getOutputStream();

            write = WriteFileFactory.getMyTXTWriter();

            write.openFile(out);

            write.writeLine("日期,标识,目的,申请人,客户,状态,预开票总金额,关联销售单,关联开票金额,旧货属性,管理属性");

            PageSeparate page = new PageSeparate();

            page.reset2(count, 2000);

            WriteFileBuffer line = new WriteFileBuffer(write);

            while (page.nextPage())
            {
                List<PreInvoiceApplyVO> voFList = preInvoiceApplyDAO.queryEntityVOsByCondition(condtion,
                    page);

                for (PreInvoiceApplyVO vo : voFList)
                {
                	List<PreInvoiceVSOutBean> vsList = preInvoiceVSOutDAO.queryEntityBeansByFK(vo.getId());
                	
                	if (!ListTools.isEmptyOrNull(vsList))
                	{
                		for (PreInvoiceVSOutBean vs : vsList)
                    	{
                			OutBean outBean = outDAO.find(vs.getOutId());
                			
                			BaseBean base = baseDAO.queryEntityBeansByFK(vs.getOutId()).get(0);
                			
                    		line.reset();

                            line.writeColumn("[" + vo.getLogTime() + "]");
                            line.writeColumn(vo.getId());
                            line.writeColumn(StringTools.getExportString(vo.getName()));
                            line.writeColumn(vo.getStafferName());
                            line.writeColumn(vo.getCustomerName());

                            line.writeColumn(ElTools.get("tcpStatus", vo.getStatus()));

                            line.writeColumn(changeString(TCPHelper.formatNum2(vo.getTotal() / 100.0d)));
                            line.writeColumn(vs.getOutId());
                            line.writeColumn(changeString(TCPHelper.formatNum2(vs.getInvoiceMoney() / 100.0d)));
                            
                            line.writeColumn(base.getOldGoods()== 9522152 ? "旧货" : "非旧货");
                            line.writeColumn(outBean.getMtype() == 1 ? "普通" : "管理");

                            line.writeLine();
                    	}
                	}
                	else
                	{
                		line.reset();

                        line.writeColumn("[" + vo.getLogTime() + "]");
                        line.writeColumn(vo.getId());
                        line.writeColumn(StringTools.getExportString(vo.getName()));
                        line.writeColumn(vo.getStafferName());
                        line.writeColumn(vo.getCustomerName());

                        line.writeColumn(ElTools.get("tcpStatus", vo.getStatus()));

                        line.writeColumn(changeString(TCPHelper.formatNum2(vo.getTotal() / 100.0d)));
                        line.writeColumn("");
                        line.writeColumn("");

                        line.writeLine();
                	}
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

    private String changeString(String str)
    {
        return str.replaceAll(",", "");
    }
    
    /**
     * preForAddPreInvoice
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward preForAddPreInvoice(ActionMapping mapping, ActionForm form,
                                          HttpServletRequest request, HttpServletResponse response)
        throws ServletException
    {
        prepareInner(request);

        return mapping.findForward("addPreInvoice");
    }
    
    /**
     * prepareInner
     * 
     * @param request
     */
    private void prepareInner(HttpServletRequest request)
    {
    	List<DutyBean> dutyList = dutyDAO.listEntityBeans();
        
        List<DutyBean> dutyList1 = new ArrayList<DutyBean>();
	       for(int i = 0 ; i < dutyList.size();i++)
	       {
	    	   DutyBean db = dutyList.get(i);
	    	   if(!db.getName().contains("停用"))
	    	   {
	    		   dutyList1.add(db);
	    	   }
	       }
       
       request.setAttribute("dutyList", dutyList1);
    	
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
	public ActionForward addOrUpdatePreInvoice(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
	throws ServletException
	{
		CommonTools.saveParamers(request);

		PreInvoiceApplyBean bean = new PreInvoiceApplyBean();

        BeanUtil.getBean(bean, request);

        String addOrUpdate = request.getParameter("addOrUpdate");

        String oprType = request.getParameter("oprType");

        String processId = request.getParameter("processId");

        StafferBean stafferBean = stafferDAO.find(bean.getStafferId());

        bean.setStype(stafferBean.getOtype());
        
        String totals = request.getParameter("total");
        
        bean.setTotal(MathTools.doubleToLong2(totals));
        
        try
        {
            User user = Helper.getUser(request);

            bean.setLogTime(TimeTools.now());

            if ("0".equals(addOrUpdate))
            {
                preInvoiceManager.addPreInvoiceBean(user, bean);
            }
            else
            {
            	preInvoiceManager.updatePreInvoiceBean(user, bean);
            }

            request.setAttribute(KeyConstant.MESSAGE, "成功保存预开票申请");

            // 提交
            if ("1".equals(oprType))
            {
            	preInvoiceManager.submitPreInvoiceBean(user, bean.getId(), processId);
            }

            request.setAttribute(KeyConstant.MESSAGE, "成功提交预开票申请");
        }
        catch (MYException e)
        {
            _logger.warn(e, e);

            request.setAttribute(KeyConstant.ERROR_MESSAGE, "操作预开票申请失败:" + e.getMessage());
        }

        return mapping.findForward("querySelfPreInvoice");
    }
	
	/**
	 * 
	 * @param request
	 * @param bean
	 */
    private void fillWrap(HttpServletRequest request, TcpParamWrap param)
    {
        List<PreInvoiceVSOutBean> vsList = new ArrayList<PreInvoiceVSOutBean>();

        String [] outIds = request.getParameterValues("p_outId");
        String [] ids = request.getParameterValues("p_id");
        String [] moneys = request.getParameterValues("p_money");
        String [] mayInvoiceMoneys = request.getParameterValues("p_mayInvoiceMoney");
        String [] invoiceMoneys = request.getParameterValues("p_invoiceMoney");

        if (outIds != null && outIds.length > 0)
        {

            for (int i = 0; i < outIds.length; i++ )
            {
                String each = outIds[i];

                if (StringTools.isNullOrNone(each))
                {
                    continue;
                }
                
                if (!StringTools.isNullOrNone(ids[i]))
                {
                	continue;
                }

                PreInvoiceVSOutBean vs = new PreInvoiceVSOutBean();

                vs.setOutId(outIds[i]);
                vs.setMoney(MathTools.parseDouble(moneys[i]));
                vs.setMayInvoiceMoney(MathTools.parseDouble(mayInvoiceMoneys[i]));
                vs.setInvoiceMoney(MathTools.parseDouble(invoiceMoneys[i]));

                vsList.add(vs);
            }
            
            param.setOther(vsList);
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
    public ActionForward processPreInvoiceBean(ActionMapping mapping, ActionForm form,
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
                preInvoiceManager.passPreInvoiceBean(user, param);
            }
            else
            {
            	preInvoiceManager.rejectPreInvoiceBean(user, param);
            }

            request.setAttribute(KeyConstant.MESSAGE, "成功处理预开票申请");
        }
        catch (MYException e)
        {
            _logger.warn(e, e);

            request.setAttribute(KeyConstant.ERROR_MESSAGE, "处理预开票申请失败:" + e.getMessage());
        }

        return mapping.findForward("querySelfApprove");
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
    public ActionForward findPreInvoice(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
	throws ServletException
	{
        User user = Helper.getUser(request);

        String id = request.getParameter("id");

        String update = request.getParameter("update");

        PreInvoiceApplyBean bean = preInvoiceManager.findVO(id);

        if (bean == null)
        {
            return ActionTools.toError("数据异常,请重新操作", mapping, request);
        }

        prepareInner(request);

        request.setAttribute("bean", bean);

        request.setAttribute("update", update);

        String outIds = "";
        
        if (!ListTools.isEmptyOrNull(bean.getVsList())){
        	for (PreInvoiceVSOutBean each : bean.getVsList())
        	{
        		if (!StringTools.isNullOrNone(each.getOutBalanceId()))
        		{
        			outIds += each.getOutBalanceId() + ",";
        		}else{
        			outIds += each.getOutId() + ",";	
        		}
        	}
        }
        
        request.setAttribute("outIds", outIds);

        // 2是稽核修改
        if ("1".equals(update) || "3".equals(update))
        {
            if ( !canTravelApplyDelete(bean))
            {
                return ActionTools.toError("申请当前状态下不能被修改", mapping, request);
            }

            return mapping.findForward("updatePreInvoice");
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
            
            return mapping.findForward("processPreInvoice");
        }

        return mapping.findForward("detailPreInvoice");
    }
    
    /**
     * deletePreInvoice
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward deletePreInvoice(ActionMapping mapping, ActionForm form,
                                       HttpServletRequest request, HttpServletResponse response)
        throws ServletException
    {
        AjaxResult ajax = new AjaxResult();

        try
        {
            String id = request.getParameter("id");

            User user = Helper.getUser(request);

            preInvoiceManager.deletePreInvoiceBean(user, id);

            ajax.setSuccess("成功操作");
        }
        catch (MYException e)
        {
            _logger.warn(e, e);

            ajax.setError("操作失败:" + e.getMessage());
        }

        return JSONTools.writeResponse(response, ajax);
    }
    
    private boolean canTravelApplyDelete(PreInvoiceApplyBean bean)
    {
        if (bean.getStatus() == TcpConstanst.TCP_STATUS_INIT
            || bean.getStatus() == TcpConstanst.TCP_STATUS_REJECT)
        {
            return true;
        }

        return false;
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

	public PreInvoiceApplyDAO getPreInvoiceApplyDAO()
	{
		return preInvoiceApplyDAO;
	}

	public void setPreInvoiceApplyDAO(PreInvoiceApplyDAO preInvoiceApplyDAO)
	{
		this.preInvoiceApplyDAO = preInvoiceApplyDAO;
	}

	public PreInvoiceVSOutDAO getPreInvoiceVSOutDAO()
	{
		return preInvoiceVSOutDAO;
	}

	public void setPreInvoiceVSOutDAO(PreInvoiceVSOutDAO preInvoiceVSOutDAO)
	{
		this.preInvoiceVSOutDAO = preInvoiceVSOutDAO;
	}

	public FlowLogDAO getFlowLogDAO()
	{
		return flowLogDAO;
	}

	public void setFlowLogDAO(FlowLogDAO flowLogDAO)
	{
		this.flowLogDAO = flowLogDAO;
	}

	public TcpApproveDAO getTcpApproveDAO()
	{
		return tcpApproveDAO;
	}

	public void setTcpApproveDAO(TcpApproveDAO tcpApproveDAO)
	{
		this.tcpApproveDAO = tcpApproveDAO;
	}

	public TcpFlowDAO getTcpFlowDAO()
	{
		return tcpFlowDAO;
	}

	public void setTcpFlowDAO(TcpFlowDAO tcpFlowDAO)
	{
		this.tcpFlowDAO = tcpFlowDAO;
	}

	public PreInvoiceManager getPreInvoiceManager()
	{
		return preInvoiceManager;
	}

	public void setPreInvoiceManager(PreInvoiceManager preInvoiceManager)
	{
		this.preInvoiceManager = preInvoiceManager;
	}

	public DutyDAO getDutyDAO()
	{
		return dutyDAO;
	}

	public void setDutyDAO(DutyDAO dutyDAO)
	{
		this.dutyDAO = dutyDAO;
	}

	/**
	 * @return the outDAO
	 */
	public OutDAO getOutDAO()
	{
		return outDAO;
	}

	/**
	 * @param outDAO the outDAO to set
	 */
	public void setOutDAO(OutDAO outDAO)
	{
		this.outDAO = outDAO;
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
}
