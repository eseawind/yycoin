package com.china.center.oa.tcp.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileUploadBase;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.center.china.osgi.config.ConfigLoader;
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
import com.china.center.oa.finance.bean.BackPrePayApplyBean;
import com.china.center.oa.finance.bean.OutBillBean;
import com.china.center.oa.finance.dao.BackPrePayApplyDAO;
import com.china.center.oa.finance.dao.InBillDAO;
import com.china.center.oa.finance.vo.BackPrePayApplyVO;
import com.china.center.oa.publics.Helper;
import com.china.center.oa.publics.bean.AttachmentBean;
import com.china.center.oa.publics.bean.FlowLogBean;
import com.china.center.oa.publics.bean.StafferBean;
import com.china.center.oa.publics.dao.AttachmentDAO;
import com.china.center.oa.publics.dao.FlowLogDAO;
import com.china.center.oa.publics.dao.StafferDAO;
import com.china.center.oa.publics.vo.FlowLogVO;
import com.china.center.oa.tcp.bean.TcpApproveBean;
import com.china.center.oa.tcp.bean.TcpFlowBean;
import com.china.center.oa.tcp.constanst.TcpConstanst;
import com.china.center.oa.tcp.constanst.TcpFlowConstant;
import com.china.center.oa.tcp.dao.TcpApproveDAO;
import com.china.center.oa.tcp.dao.TcpFlowDAO;
import com.china.center.oa.tcp.helper.TCPHelper;
import com.china.center.oa.tcp.manager.BackPrePayManager;
import com.china.center.oa.tcp.vo.TcpApproveVO;
import com.china.center.oa.tcp.wrap.TcpParamWrap;
import com.china.center.osgi.jsp.ElTools;
import com.china.center.tools.BeanUtil;
import com.china.center.tools.FileTools;
import com.china.center.tools.MathTools;
import com.china.center.tools.RequestDataStream;
import com.china.center.tools.SequenceTools;
import com.china.center.tools.StringTools;
import com.china.center.tools.TimeTools;
import com.china.center.tools.UtilStream;
import com.china.center.tools.WriteFileBuffer;

/**
 * 
 * 预收退款
 *
 * @author fangliwen 2013-8-31
 */
public class BackPrePayAction extends DispatchAction
{
	private final Log _logger = LogFactory.getLog(getClass());
	
	private StafferDAO stafferDAO = null;
	
	private BackPrePayApplyDAO backPrePayApplyDAO = null;
	
	private BackPrePayManager backPrePayManager = null;
	
	private InBillDAO inBillDAO = null;
	
	private FlowLogDAO flowLogDAO = null;
	
	private TcpApproveDAO tcpApproveDAO = null;
	
	private TcpFlowDAO tcpFlowDAO = null;
	
	private AttachmentDAO attachmentDAO = null;
	
	private final static String QUERYSELFBACKPREPAY = "tcp.querySelfBackPrePay";
	
	private final static String QUERYALLBACKPREPAY = "tcp.queryAllBackPrePay";
	
	/**
	 * 
	 */
	public BackPrePayAction()
	{
		
	}

	/**
     * QUERYSELFBACKPREPAY
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward querySelfBackPrePay(ActionMapping mapping, ActionForm form,
                                          HttpServletRequest request, HttpServletResponse response)
        throws ServletException
    {
        User user = Helper.getUser(request);

        ConditionParse condtion = new ConditionParse();

        condtion.addWhereStr();

        ActionTools.processJSONQueryCondition(QUERYSELFBACKPREPAY, request, condtion);

        condtion.addCondition("BackPrePayApplyBean.stafferId", "=", user.getStafferId());

        String type = request.getParameter("type");

        condtion.addIntCondition("BackPrePayApplyBean.type", "=", type);

        condtion.addCondition("order by BackPrePayApplyBean.logTime desc");

        String jsonstr = ActionTools.queryVOByJSONAndToString(QUERYSELFBACKPREPAY, request, condtion,
            this.backPrePayApplyDAO, new HandleResult<BackPrePayApplyVO>()
            {
                public void handle(BackPrePayApplyVO vo)
                {
                	vo.setShowBackMoney(TCPHelper.formatNum2(vo.getBackMoney() / 100.0d));
                	
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
     * QUERYALLBACKPREPAY
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward queryAllBackPrePay(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
	throws ServletException
	{
        ConditionParse condtion = new ConditionParse();

        condtion.addWhereStr();

        ActionTools.processJSONQueryCondition(QUERYALLBACKPREPAY, request, condtion);

        String type = request.getParameter("type");

        condtion.addIntCondition("BackPrePayApplyBean.type", "=", type);

        condtion.addCondition("order by BackPrePayApplyBean.logTime desc");

        String jsonstr = ActionTools.queryVOByJSONAndToString(QUERYALLBACKPREPAY, request, condtion,
            this.backPrePayApplyDAO, new HandleResult<BackPrePayApplyVO>()
            {
                public void handle(BackPrePayApplyVO vo)
                {
                	vo.setShowBackMoney(TCPHelper.formatNum2(vo.getBackMoney() / 100.0d));
                	
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
    public ActionForward exportBackPrePayApply(ActionMapping mapping, ActionForm form,
                                           HttpServletRequest request, HttpServletResponse response)
        throws ServletException
    {
        OutputStream out = null;

        String filenName = "BackPrePay_APPLY_" + TimeTools.now("MMddHHmmss") + ".csv";

        response.setContentType("application/x-dbf");

        response.setHeader("Content-Disposition", "attachment; filename=" + filenName);

        WriteFile write = null;

        ConditionParse condtion = JSONPageSeparateTools.getCondition(request, QUERYALLBACKPREPAY);

        int count = this.backPrePayApplyDAO.countVOByCondition(condtion.toString());

        try
        {
            out = response.getOutputStream();

            write = WriteFileFactory.getMyTXTWriter();

            write.openFile(out);

            write.writeLine("日期,标识,目的,申请人,客户,状态,收款单,回款单,退款金额,收款人,收款银行,收款账号");

            PageSeparate page = new PageSeparate();

            page.reset2(count, 2000);

            WriteFileBuffer line = new WriteFileBuffer(write);

            while (page.nextPage())
            {
                List<BackPrePayApplyVO> voFList = backPrePayApplyDAO.queryEntityVOsByCondition(condtion,
                    page);

                for (BackPrePayApplyVO vo : voFList)
                {
            		line.reset();

                    line.writeColumn("[" + vo.getLogTime() + "]");
                    line.writeColumn(vo.getId());
                    line.writeColumn(StringTools.getExportString(vo.getName()));
                    line.writeColumn(vo.getStafferName());
                    line.writeColumn(vo.getCustomerName());

                    line.writeColumn(ElTools.get("tcpStatus", vo.getStatus()));
                    line.writeColumn(vo.getBillId());
                    line.writeColumn(vo.getPaymentId());
                    line.writeColumn(changeString(TCPHelper.formatNum2(vo.getTotal() / 100.0d)));
                    line.writeColumn(vo.getReceiver());
                    line.writeColumn(vo.getReceiveBank());
                    line.writeColumn(vo.getReceiveAccount());
                    
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

    private String changeString(String str)
    {
        return str.replaceAll(",", "");
    }
    
    /**
     * preForAddBackPrePay
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward preForAddBackPrePay(ActionMapping mapping, ActionForm form,
                                          HttpServletRequest request, HttpServletResponse response)
        throws ServletException
    {
        prepareInner(request);

        return mapping.findForward("addBackPrePay");
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
	public ActionForward addOrUpdateBackPrePay(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
	throws ServletException
	{
		BackPrePayApplyBean bean = new BackPrePayApplyBean();

		// 模板最多10M
        RequestDataStream rds = new RequestDataStream(request, 1024 * 1024 * 10L);

        try
        {
            rds.parser();
        }
        catch (FileUploadBase.SizeLimitExceededException e)
        {
            _logger.error(e, e);

            request.setAttribute(KeyConstant.ERROR_MESSAGE, "增加失败:附件超过10M");

            return mapping.findForward("error");
        }
        catch (Exception e)
        {
            _logger.error(e, e);

            request.setAttribute(KeyConstant.ERROR_MESSAGE, "增加失败");

            return mapping.findForward("error");
        }

        BeanUtil.getBean(bean, rds.getParmterMap());

        String addOrUpdate = rds.getParameter("addOrUpdate");

        String oprType = rds.getParameter("oprType");

        String processId = rds.getParameter("processId");
        
        ActionForward afor = parserAttachment(mapping, request, rds, bean);

        if (afor != null)
        {
            return afor;
        }

        rds.close();

        StafferBean stafferBean = stafferDAO.find(bean.getStafferId());

        bean.setStype(stafferBean.getOtype());
        
        String totals = rds.getParameter("total");
        
        bean.setTotal(MathTools.doubleToLong2(totals));
        
        String backs = rds.getParameter("backMoney");
        
        bean.setBackMoney(MathTools.doubleToLong2(backs));
        
        try
        {
            User user = Helper.getUser(request);

            bean.setLogTime(TimeTools.now());

            if ("0".equals(addOrUpdate))
            {
                backPrePayManager.addBackPrePayBean(user, bean);
            }
            else
            {
            	backPrePayManager.updateBackPrePayBean(user, bean);
            }

            request.setAttribute(KeyConstant.MESSAGE, "成功保存预收退款申请");

            // 提交
            if ("1".equals(oprType))
            {
            	backPrePayManager.submitBackPrePayBean(user, bean.getId(), processId);
            }

            request.setAttribute(KeyConstant.MESSAGE, "成功提交预收退款申请");
        }
        catch (MYException e)
        {
            _logger.warn(e, e);

            request.setAttribute(KeyConstant.ERROR_MESSAGE, "操作预收退款申请失败:" + e.getMessage());
        }

        return mapping.findForward("querySelfBackPrePay");
    }
	
	/**
     * parserAttachment
     * 
     * @param mapping
     * @param request
     * @param rds
     * @param bean
     * @return
     */
    private ActionForward parserAttachment(ActionMapping mapping, HttpServletRequest request,
                                           RequestDataStream rds, BackPrePayApplyBean apply)
    {
        List<AttachmentBean> attachmentList = new ArrayList<AttachmentBean>();

        apply.setAttachmentList(attachmentList);

        String addOrUpdate = rds.getParameter("addOrUpdate");

        // 更新新加入之前
        if ("1".equals(addOrUpdate))
        {
            String attacmentIds = rds.getParameter("attacmentIds");

            String[] split = attacmentIds.split(";");

            for (String each : split)
            {
                if (StringTools.isNullOrNone(each))
                {
                    continue;
                }

                AttachmentBean att = attachmentDAO.find(each);

                if (att != null)
                {
                    attachmentList.add(att);
                }
            }
        }

        // parser attachment
        if ( !rds.haveStream())
        {
            return null;
        }

        Map<String, InputStream> streamMap = rds.getStreamMap();

        for (Map.Entry<String, InputStream> entry : streamMap.entrySet())
        {
            AttachmentBean bean = new AttachmentBean();

            FileOutputStream out = null;

            UtilStream ustream = null;

            try
            {
                String savePath = mkdir(this.getAttachmentPath());

                String fileAlais = SequenceTools.getSequence();

                String fileName = FileTools.getFileName(rds.getFileName(entry.getKey()));

                String rabsPath = '/' + savePath + '/' + fileAlais + "."
                                  + FileTools.getFilePostfix(fileName).toLowerCase();

                String filePath = this.getAttachmentPath() + '/' + rabsPath;

                bean.setName(fileName);

                bean.setPath(rabsPath);

                bean.setLogTime(TimeTools.now());

                out = new FileOutputStream(filePath);

                ustream = new UtilStream(entry.getValue(), out);

                ustream.copyStream();

                attachmentList.add(bean);
            }
            catch (IOException e)
            {
                _logger.error(e, e);

                request.setAttribute(KeyConstant.ERROR_MESSAGE, "保存失败");

                return mapping.findForward("querySelfBackPrePay");
            }
            finally
            {
                if (ustream != null)
                {
                    try
                    {
                        ustream.close();
                    }
                    catch (IOException e)
                    {
                        _logger.error(e, e);
                    }
                }
            }
        }

        return null;
    }
    
    private String mkdir(String root)
    {
        String path = TimeTools.now("yyyy/MM/dd/HH") + "/"
                      + SequenceTools.getSequence(String.valueOf(new Random().nextInt(1000)));

        FileTools.mkdirs(root + '/' + path);

        return path;
    }

    /**
     * @return the flowAtt
     */
    public String getAttachmentPath()
    {
        return ConfigLoader.getProperty("backPrePayAttachmentPath");
    }
	
    /**
     * downAttachmentFile
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     * @throws IOException
     */
    public ActionForward downAttachmentFile(ActionMapping mapping, ActionForm form,
                                            HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException
    {
        String path = getAttachmentPath();

        String id = request.getParameter("id");

        AttachmentBean bean = attachmentDAO.find(id);

        if (bean == null)
        {
            return ActionTools.toError(mapping, request);
        }

        path += bean.getPath();

        File file = new File(path);

        OutputStream out = response.getOutputStream();

        response.setContentType("application/x-dbf");

        response.setHeader("Content-Disposition", "attachment; filename="
                                                  + StringTools.getStringBySet(bean.getName(),
                                                      "GBK", "ISO8859-1"));

        UtilStream us = new UtilStream(new FileInputStream(file), out);

        us.copyAndCloseStream();

        return null;
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
    public ActionForward processBackPrePayBean(ActionMapping mapping, ActionForm form,
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
                backPrePayManager.passBackPrePayBean(user, param);
            }
            else
            {
            	backPrePayManager.rejectBackPrePayBean(user, param);
            }

            request.setAttribute(KeyConstant.MESSAGE, "成功处理预收退款申请");
        }
        catch (MYException e)
        {
            _logger.warn(e, e);

            request.setAttribute(KeyConstant.ERROR_MESSAGE, "处理预收退款申请失败:" + e.getMessage());
        }

        return mapping.findForward("querySelfApprove");
    }
    
    /**
	 * 
	 * @param request
	 * @param bean
	 */
    private void fillWrap(HttpServletRequest request, TcpParamWrap param)
    {
    	List<OutBillBean> outBillList = new ArrayList<OutBillBean>();

        String[] bankIds = request.getParameterValues("bankId");
        String[] payTypes = request.getParameterValues("payType");
        String[] moneys = request.getParameterValues("money");

        if (null != bankIds && bankIds.length > 0) {
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
    public ActionForward findBackPrePay(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
	throws ServletException
	{
        User user = Helper.getUser(request);

        String id = request.getParameter("id");

        String update = request.getParameter("update");

        BackPrePayApplyBean bean = backPrePayManager.findVO(id);

        if (bean == null)
        {
            return ActionTools.toError("数据异常,请重新操作", mapping, request);
        }

        prepareInner(request);

        request.setAttribute("bean", bean);

        request.setAttribute("update", update);

        List<AttachmentBean> attachmentList = attachmentDAO.queryEntityVOsByFK(id);
        
        bean.setAttachmentList(attachmentList);
        
        // 2是稽核修改
        if ("1".equals(update) || "3".equals(update))
        {
            if ( !canTravelApplyDelete(bean))
            {
                return ActionTools.toError("申请当前状态下不能被修改", mapping, request);
            }
            
            String attacmentIds = "";

            for (AttachmentBean attachmentBean : attachmentList)
            {
                attacmentIds = attacmentIds + attachmentBean.getId() + ";";
            }

            request.setAttribute("attacmentIds", attacmentIds);

            return mapping.findForward("updateBackPrePay");
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
            
            return mapping.findForward("processBackPrePay");
        }

        return mapping.findForward("detailBackPrePay");
    }
    
    /**
     * deleteBackPrePay
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward deleteBackPrePay(ActionMapping mapping, ActionForm form,
                                       HttpServletRequest request, HttpServletResponse response)
        throws ServletException
    {
        AjaxResult ajax = new AjaxResult();

        try
        {
            String id = request.getParameter("id");

            User user = Helper.getUser(request);

            backPrePayManager.deleteBackPrePayBean(user, id);

            ajax.setSuccess("成功操作");
        }
        catch (MYException e)
        {
            _logger.warn(e, e);

            ajax.setError("操作失败:" + e.getMessage());
        }

        return JSONTools.writeResponse(response, ajax);
    }
    
    private boolean canTravelApplyDelete(BackPrePayApplyBean bean)
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
	public StafferDAO getStafferDAO() {
		return stafferDAO;
	}

	/**
	 * @param stafferDAO the stafferDAO to set
	 */
	public void setStafferDAO(StafferDAO stafferDAO) {
		this.stafferDAO = stafferDAO;
	}

	/**
	 * @return the backPrePayApplyDAO
	 */
	public BackPrePayApplyDAO getBackPrePayApplyDAO() {
		return backPrePayApplyDAO;
	}

	/**
	 * @param backPrePayApplyDAO the backPrePayApplyDAO to set
	 */
	public void setBackPrePayApplyDAO(BackPrePayApplyDAO backPrePayApplyDAO) {
		this.backPrePayApplyDAO = backPrePayApplyDAO;
	}

	/**
	 * @return the backPrePayManager
	 */
	public BackPrePayManager getBackPrePayManager() {
		return backPrePayManager;
	}

	/**
	 * @param backPrePayManager the backPrePayManager to set
	 */
	public void setBackPrePayManager(BackPrePayManager backPrePayManager) {
		this.backPrePayManager = backPrePayManager;
	}

	/**
	 * @return the inBillDAO
	 */
	public InBillDAO getInBillDAO() {
		return inBillDAO;
	}

	/**
	 * @param inBillDAO the inBillDAO to set
	 */
	public void setInBillDAO(InBillDAO inBillDAO) {
		this.inBillDAO = inBillDAO;
	}

	/**
	 * @return the flowLogDAO
	 */
	public FlowLogDAO getFlowLogDAO() {
		return flowLogDAO;
	}

	/**
	 * @param flowLogDAO the flowLogDAO to set
	 */
	public void setFlowLogDAO(FlowLogDAO flowLogDAO) {
		this.flowLogDAO = flowLogDAO;
	}

	/**
	 * @return the tcpApproveDAO
	 */
	public TcpApproveDAO getTcpApproveDAO() {
		return tcpApproveDAO;
	}

	/**
	 * @param tcpApproveDAO the tcpApproveDAO to set
	 */
	public void setTcpApproveDAO(TcpApproveDAO tcpApproveDAO) {
		this.tcpApproveDAO = tcpApproveDAO;
	}

	/**
	 * @return the tcpFlowDAO
	 */
	public TcpFlowDAO getTcpFlowDAO() {
		return tcpFlowDAO;
	}

	/**
	 * @param tcpFlowDAO the tcpFlowDAO to set
	 */
	public void setTcpFlowDAO(TcpFlowDAO tcpFlowDAO) {
		this.tcpFlowDAO = tcpFlowDAO;
	}

	/**
	 * @return the attachmentDAO
	 */
	public AttachmentDAO getAttachmentDAO() {
		return attachmentDAO;
	}

	/**
	 * @param attachmentDAO the attachmentDAO to set
	 */
	public void setAttachmentDAO(AttachmentDAO attachmentDAO) {
		this.attachmentDAO = attachmentDAO;
	}
}
