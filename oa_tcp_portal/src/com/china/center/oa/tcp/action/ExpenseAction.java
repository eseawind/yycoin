/**
 * File Name: TravelApplyAction.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2011-7-20<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.tcp.action;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Iterator;
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
import com.center.china.osgi.publics.file.read.ReadeFileFactory;
import com.center.china.osgi.publics.file.read.ReaderFile;
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
import com.china.center.oa.budget.bean.BudgetBean;
import com.china.center.oa.budget.bean.FeeItemBean;
import com.china.center.oa.budget.constant.BudgetConstant;
import com.china.center.oa.budget.dao.BudgetDAO;
import com.china.center.oa.budget.dao.BudgetItemDAO;
import com.china.center.oa.budget.dao.FeeItemDAO;
import com.china.center.oa.budget.vo.FeeItemVO;
import com.china.center.oa.finance.bean.InBillBean;
import com.china.center.oa.finance.bean.OutBillBean;
import com.china.center.oa.finance.dao.InBillDAO;
import com.china.center.oa.finance.dao.OutBillDAO;
import com.china.center.oa.publics.Helper;
import com.china.center.oa.publics.bean.AttachmentBean;
import com.china.center.oa.publics.bean.FlowLogBean;
import com.china.center.oa.publics.bean.PrincipalshipBean;
import com.china.center.oa.publics.bean.StafferBean;
import com.china.center.oa.publics.constant.StafferConstant;
import com.china.center.oa.publics.dao.AttachmentDAO;
import com.china.center.oa.publics.dao.FlowLogDAO;
import com.china.center.oa.publics.dao.PrincipalshipDAO;
import com.china.center.oa.publics.dao.StafferDAO;
import com.china.center.oa.publics.vo.FlowLogVO;
import com.china.center.oa.tax.bean.FinanceBean;
import com.china.center.oa.tax.dao.FinanceDAO;
import com.china.center.oa.tcp.bean.ExpenseApplyBean;
import com.china.center.oa.tcp.bean.TcpApproveBean;
import com.china.center.oa.tcp.bean.TcpFlowBean;
import com.china.center.oa.tcp.bean.TcpShareBean;
import com.china.center.oa.tcp.bean.TravelApplyItemBean;
import com.china.center.oa.tcp.bean.TravelApplyPayBean;
import com.china.center.oa.tcp.constanst.TcpConstanst;
import com.china.center.oa.tcp.constanst.TcpFlowConstant;
import com.china.center.oa.tcp.dao.ExpenseApplyDAO;
import com.china.center.oa.tcp.dao.TcpApplyDAO;
import com.china.center.oa.tcp.dao.TcpApproveDAO;
import com.china.center.oa.tcp.dao.TcpFlowDAO;
import com.china.center.oa.tcp.dao.TcpHandleHisDAO;
import com.china.center.oa.tcp.dao.TcpPrepaymentDAO;
import com.china.center.oa.tcp.dao.TcpShareDAO;
import com.china.center.oa.tcp.dao.TravelApplyDAO;
import com.china.center.oa.tcp.dao.TravelApplyItemDAO;
import com.china.center.oa.tcp.dao.TravelApplyPayDAO;
import com.china.center.oa.tcp.helper.TCPHelper;
import com.china.center.oa.tcp.manager.ExpenseManager;
import com.china.center.oa.tcp.manager.TcpFlowManager;
import com.china.center.oa.tcp.vo.ExpenseApplyVO;
import com.china.center.oa.tcp.vo.TcpApproveVO;
import com.china.center.oa.tcp.vo.TcpHandleHisVO;
import com.china.center.oa.tcp.vo.TcpShareVO;
import com.china.center.oa.tcp.vo.TravelApplyItemVO;
import com.china.center.oa.tcp.vo.TravelApplyVO;
import com.china.center.oa.tcp.wrap.AddFinWrap;
import com.china.center.oa.tcp.wrap.TcpParamWrap;
import com.china.center.osgi.jsp.ElTools;
import com.china.center.tools.BeanUtil;
import com.china.center.tools.CommonTools;
import com.china.center.tools.FileTools;
import com.china.center.tools.ListTools;
import com.china.center.tools.MathTools;
import com.china.center.tools.RegularExpress;
import com.china.center.tools.RequestDataStream;
import com.china.center.tools.SequenceTools;
import com.china.center.tools.StringTools;
import com.china.center.tools.TimeTools;
import com.china.center.tools.UtilStream;
import com.china.center.tools.WriteFileBuffer;


/**
 * TravelApplyAction
 * 
 * @author ZHUZHU
 * @version 2011-7-20
 * @see ExpenseAction
 * @since 3.0
 */
public class ExpenseAction extends DispatchAction
{
    private final Log _logger = LogFactory.getLog(getClass());

    private ExpenseManager expenseManager = null;

    private TcpApplyDAO tcpApplyDAO = null;

    private TcpFlowDAO tcpFlowDAO = null;

    private TcpApproveDAO tcpApproveDAO = null;

    private TcpPrepaymentDAO tcpPrepaymentDAO = null;

    private TcpShareDAO tcpShareDAO = null;

    private TcpFlowManager tcpFlowManager = null;

    private TravelApplyDAO travelApplyDAO = null;

    private TravelApplyItemDAO travelApplyItemDAO = null;

    private TravelApplyPayDAO travelApplyPayDAO = null;

    private BudgetItemDAO budgetItemDAO = null;

    private BudgetDAO budgetDAO = null;

    private FeeItemDAO feeItemDAO = null;

    private ExpenseApplyDAO expenseApplyDAO = null;

    private FlowLogDAO flowLogDAO = null;

    private TcpHandleHisDAO tcpHandleHisDAO = null;

    private OutBillDAO outBillDAO = null;

    private StafferDAO stafferDAO = null;

    private InBillDAO inBillDAO = null;

    private FinanceDAO financeDAO = null;
    
    private PrincipalshipDAO principalshipDAO = null;

    private AttachmentDAO attachmentDAO = null;

    private static String QUERYSELFEXPENSE = "tcp.querySelfExpense";

    private static String QUERYALLEXPENSE = "tcp.queryAllExpense";

    private static String QUERYSELFAPPROVE = "tcp.querySelfApprove";

    private static String QUERYPOOLAPPROVE = "tcp.queryPoolApprove";

    private static String QUERYTCPHIS = "tcp.queryTcpHis";

    /**
     * default constructor
     */
    public ExpenseAction()
    {
    }
    
    /**
     * 获取TAB页任务列表
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward queryTabExpense(ActionMapping mapping, ActionForm form,
            final HttpServletRequest request, HttpServletResponse response)
			throws ServletException
			{
				CommonTools.saveParamers(request);
				
//				ConditionParse condtion = new ConditionParse();
				
//				condtion.addCondition("AgreementBean.taskStatus", "=", ProjectConstant.TASK_STAGE_TWO);
				
//				condtion.addWhereStr();
//				List<AgreementBean> list = this.agreementDAO.queryEntityBeansByCondition(condtion.toString());
				List<ExpenseApplyVO> list = this.expenseApplyDAO.listEntityVOs();
				request.setAttribute("beanList", list);
				
				return mapping.findForward("queryTabExpense");
			}

    /**
     * querySelfExpense
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward querySelfExpense(ActionMapping mapping, ActionForm form,
                                          HttpServletRequest request, HttpServletResponse response)
        throws ServletException
    {
        User user = Helper.getUser(request);

        ConditionParse condtion = new ConditionParse();

        condtion.addWhereStr();

        ActionTools.processJSONQueryCondition(QUERYSELFEXPENSE, request, condtion);

        condtion.addCondition("ExpenseApplyBean.stafferId", "=", user.getStafferId());

        String type = request.getParameter("type");

        condtion.addIntCondition("ExpenseApplyBean.type", "=", type);

        condtion.addCondition("order by ExpenseApplyBean.logTime desc");

        String jsonstr = ActionTools.queryVOByJSONAndToString(QUERYSELFEXPENSE, request, condtion,
            this.expenseApplyDAO, new HandleResult<ExpenseApplyVO>()
            {
                public void handle(ExpenseApplyVO vo)
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
     * queryAllExpense
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward queryAllExpense(ActionMapping mapping, ActionForm form,
                                         HttpServletRequest request, HttpServletResponse response)
        throws ServletException
    {
        ConditionParse condtion = new ConditionParse();

        condtion.addWhereStr();

        ActionTools.processJSONQueryCondition(QUERYALLEXPENSE, request, condtion);

        condtion.addCondition("order by ExpenseApplyBean.logTime desc");

        String jsonstr = ActionTools.queryVOByJSONAndToString(QUERYALLEXPENSE, request, condtion,
            this.expenseApplyDAO, new HandleResult<ExpenseApplyVO>()
            {
                public void handle(ExpenseApplyVO vo)
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
     * queryPoolApprove
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward queryPoolApprove(ActionMapping mapping, ActionForm form,
                                          HttpServletRequest request, HttpServletResponse response)
        throws ServletException
    {
        User user = Helper.getUser(request);

        ConditionParse condtion = new ConditionParse();

        condtion.addWhereStr();

        String cacheKey = QUERYPOOLAPPROVE;

        ActionTools.processJSONQueryCondition(cacheKey, request, condtion);

        condtion.addCondition("TcpApproveBean.approverId", "=", user.getStafferId());

        condtion.addIntCondition("TcpApproveBean.pool", "=", TcpConstanst.TCP_POOL_POOL);

        condtion.addCondition("order by TcpApproveBean.logTime desc");

        String jsonstr = ActionTools.queryVOByJSONAndToString(cacheKey, request, condtion,
            this.tcpApproveDAO, new HandleResult<TcpApproveVO>()
            {
                public void handle(TcpApproveVO vo)
                {
                    TCPHelper.getTcpApproveVO(vo);

                    vo.setUrl(TcpConstanst.TCP_TRAVELAPPLY_DETAIL_URL + vo.getApplyId());
                }
            });

        return JSONTools.writeResponse(response, jsonstr);
    }

    /**
     * queryTcpHis
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward queryTcpHis(ActionMapping mapping, ActionForm form,
                                     HttpServletRequest request, HttpServletResponse response)
        throws ServletException
    {
        User user = Helper.getUser(request);

        ConditionParse condtion = new ConditionParse();

        condtion.addWhereStr();

        String cacheKey = QUERYTCPHIS;

        ActionTools.processJSONQueryCondition(cacheKey, request, condtion);

        condtion.addCondition("TcpHandleHisBean.stafferId", "=", user.getStafferId());

        condtion.addCondition("order by TcpHandleHisBean.logTime desc");

        String jsonstr = ActionTools.queryVOByJSONAndToString(cacheKey, request, condtion,
            this.tcpHandleHisDAO, new HandleResult<TcpHandleHisVO>()
            {
                public void handle(TcpHandleHisVO vo)
                {
                    vo.setUrl(TcpConstanst.TCP_TRAVELAPPLY_DETAIL_URL + vo.getRefId());
                }
            });

        return JSONTools.writeResponse(response, jsonstr);
    }

    /**
     * 查询待我处理的
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward querySelfApprove(ActionMapping mapping, ActionForm form,
                                          HttpServletRequest request, HttpServletResponse response)
        throws ServletException
    {
        User user = Helper.getUser(request);

        ConditionParse condtion = new ConditionParse();

        condtion.addWhereStr();

        String cacheKey = QUERYSELFAPPROVE;

        ActionTools.processJSONQueryCondition(cacheKey, request, condtion);

        condtion.addCondition("TcpApproveBean.approverId", "=", user.getStafferId());

        condtion.addIntCondition("TcpApproveBean.pool", "=", TcpConstanst.TCP_POOL_COMMON);

        condtion.addCondition("order by TcpApproveBean.logTime desc");

        String jsonstr = ActionTools.queryVOByJSONAndToString(cacheKey, request, condtion,
            this.tcpApproveDAO, new HandleResult<TcpApproveVO>()
            {
                public void handle(TcpApproveVO vo)
                {
                    TCPHelper.getTcpApproveVO(vo);
                }
            });

        return JSONTools.writeResponse(response, jsonstr);
    }

    /**
     * preForAddExpense
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward preForAddExpense(ActionMapping mapping, ActionForm form,
                                          HttpServletRequest request, HttpServletResponse response)
        throws ServletException
    {
        prepareInner(request);

        String type = request.getParameter("type");

        return mapping.findForward("addExpense" + type);
    }

    /**
     * prepareInner
     * 
     * @param request
     */
    private void prepareInner(HttpServletRequest request)
    {
        String type = request.getParameter("type");

        List<FeeItemVO> feeItemList = feeItemDAO.listEntityVOs();

        // 只有出差的特殊处理
        if ("0".equals(type) || "11".equals(type))
        {
            for (Iterator iterator = feeItemList.iterator(); iterator.hasNext();)
            {
                FeeItemVO feeItemVO = (FeeItemVO)iterator.next();

                if (feeItemVO.getId().equals(BudgetConstant.FEE_ITEM_TRAVELLING))
                {
                    iterator.remove();
                    break;
                }
            }
        }

        request.setAttribute("feeItemList", feeItemList);

        // 群组
        request.setAttribute("pluginType", "group");

        request.setAttribute("pluginValue", TcpFlowConstant.GROUP_DM);
    }

    /**
     * deleteTravelApply
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward deleteExpense(ActionMapping mapping, ActionForm form,
                                       HttpServletRequest request, HttpServletResponse response)
        throws ServletException
    {
        AjaxResult ajax = new AjaxResult();

        try
        {
            String id = request.getParameter("id");

            User user = Helper.getUser(request);

            expenseManager.deleteExpenseBean(user, id);

            ajax.setSuccess("成功操作");
        }
        catch (MYException e)
        {
            _logger.warn(e, e);

            ajax.setError("操作失败:" + e.getMessage());
        }

        return JSONTools.writeResponse(response, ajax);
    }

    /**
     * findExpense
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward findExpense(ActionMapping mapping, ActionForm form,
                                     HttpServletRequest request, HttpServletResponse response)
        throws ServletException
    {
        User user = Helper.getUser(request);

        String id = request.getParameter("id");

        String update = request.getParameter("update");

        ExpenseApplyVO bean = expenseManager.findVO(id);

        if (bean == null)
        {
            return ActionTools.toError("数据异常,请重新操作", mapping, request);
        }

        prepareInner(request);

        request.setAttribute("bean", bean);

        request.setAttribute("update", update);

        // 查询关联的付款单和凭证
        List<OutBillBean> billList = outBillDAO.queryEntityBeansByFK(id);

        request.setAttribute("billList", billList);

        List<InBillBean> inList = inBillDAO.queryEntityBeansByFK(id);

        request.setAttribute("inList", inList);

        List<FinanceBean> financeList = financeDAO.queryEntityBeansByFK(id);

        request.setAttribute("financeList", financeList);

        // 2是稽核修改
        if ("1".equals(update) || "3".equals(update))
        {
            if ( !TCPHelper.canTravelApplyUpdate(bean))
            {
                return ActionTools.toError("申请当前状态下不能被修改", mapping, request);
            }

            List<AttachmentBean> attachmentList = bean.getAttachmentList();

            String attacmentIds = "";

            for (AttachmentBean attachmentBean : attachmentList)
            {
                attacmentIds = attacmentIds + attachmentBean.getId() + ";";
            }

            request.setAttribute("attacmentIds", attacmentIds);

            List<TravelApplyItemVO> itemVOList = bean.getItemVOList();

            // 出差特殊处理屏蔽差旅费
            if (bean.getType() == TcpConstanst.TCP_EXPENSETYPE_TRAVEL)
            {
                for (Iterator iterator = itemVOList.iterator(); iterator.hasNext();)
                {
                    TravelApplyItemVO travelApplyItemVO = (TravelApplyItemVO)iterator.next();

                    if (travelApplyItemVO.getFeeItemId().equals(BudgetConstant.FEE_ITEM_TRAVELLING))
                    {
                        iterator.remove();

                        break;
                    }
                }
            }

            if (TCPHelper.isTemplateExpense(bean))
            {
                bean.setItemVOList(null);
                bean.setShareVOList(null);
            }

            return mapping.findForward("updateExpense" + bean.getType());
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
            
            // 到财务支付(23)需要自动生成凭证的雏形
            if (bean.getStatus() == 23)
            {
                long taxAll = 0;

                if (bean.getPayType() == TcpConstanst.PAYTYPE_PAY_OK
                    && bean.getType() != TcpConstanst.TCP_EXPENSETYPE_COMMON)
                {
                    taxAll = bean.getRefMoney();
                }

                if (bean.getPayType() == TcpConstanst.PAYTYPE_PAY_OK
                    && bean.getType() == TcpConstanst.TCP_EXPENSETYPE_COMMON)
                {
                    taxAll = bean.getTotal();
                }

                if (bean.getPayType() == TcpConstanst.PAYTYPE_PAY_YES)
                {
                    taxAll = bean.getRefMoney() + bean.getBorrowTotal();
                }

                if (bean.getPayType() == TcpConstanst.PAYTYPE_PAY_NO)
                {
                    taxAll = bean.getRefMoney() - bean.getLastMoney();
                }

                List<AddFinWrap> wapList = new ArrayList<AddFinWrap>();
                List<TravelApplyItemVO> itemVOList = bean.getItemVOList();
                boolean templateExpense = TCPHelper.isTemplateExpense(bean);
                
                if (templateExpense)
                {
                    for (Iterator iterator = itemVOList.iterator(); iterator.hasNext();)
                    {
                        TravelApplyItemVO travelApplyItemVO = (TravelApplyItemVO)iterator.next();

                        FeeItemVO feeItem = feeItemDAO.findVO(travelApplyItemVO.getFeeItemId());

                        if (feeItem == null)
                        {
                            continue;
                        }

                        StafferBean approverBean = stafferDAO.find(travelApplyItemVO
                                .getFeeStafferId());
                        AddFinWrap wrap = new AddFinWrap();

                        if (approverBean.getOtype() == StafferConstant.OTYPE_SAIL)
                        {
                            wrap.setTaxId(feeItem.getTaxId());
                            wrap.setTaxName(feeItem.getTaxName());
                        }
                        else
                        {
                            wrap.setTaxId(feeItem.getTaxId2());
                            wrap.setTaxName(feeItem.getTaxName2());
                        }

                        if (iterator.hasNext())
                        {
                            wrap.setShowMoney(TCPHelper
                                .formatNum2(travelApplyItemVO.getMoneys() / 100.0d));

                            taxAll = taxAll - travelApplyItemVO.getMoneys();
                        }
                        else
                        {
                            wrap.setShowMoney(TCPHelper.formatNum2(taxAll / 100.0d));
                        }

                        if (templateExpense)
                        {
                            wrap.setStafferId(travelApplyItemVO.getFeeStafferId());
                        }
                        else
                        {
                            wrap.setStafferId(bean.getStafferId());
                        }

                        wrap.setStafferId(approverBean.getId());
                        wrap.setStafferName(approverBean.getName());

                        wapList.add(wrap);
                    }
                }
                else
                {
                    // 这里根据分担比例
                    List<TcpShareVO> shareVOList = bean.getShareVOList();
                    TCPHelper.ratioShare(shareVOList);

                    for (TcpShareVO tcpShareVO : shareVOList)
                    {
//                        String approverId = tcpShareVO.getApproverId();
                        //更改按承担人
                        String approverId = tcpShareVO.getBearId();
                        
                        StafferBean approverBean = stafferDAO.find(approverId);
                       
                        for (Iterator iterator = itemVOList.iterator(); iterator.hasNext();)
                        {
                            TravelApplyItemVO travelApplyItemVO = (TravelApplyItemVO)iterator
                                .next();

                            FeeItemVO feeItem = feeItemDAO.findVO(travelApplyItemVO.getFeeItemId());

                            if (feeItem == null)
                            {
                                continue;
                            }

                            AddFinWrap wrap = new AddFinWrap();

                            if (approverBean!=null && approverBean.getOtype() == StafferConstant.OTYPE_SAIL)
                            {
                                wrap.setTaxId(feeItem.getTaxId());
                                wrap.setTaxName(feeItem.getTaxName());
                            }
                            else
                            {
                                wrap.setTaxId(feeItem.getTaxId2());
                                wrap.setTaxName(feeItem.getTaxName2());
                            }

                            long val = TCPHelper.ratioValue(travelApplyItemVO.getMoneys(),
                                tcpShareVO.getRatio());

                            taxAll = taxAll - val;

                            if (taxAll >= 0)
                            {
                                wrap.setShowMoney(TCPHelper.formatNum2(val / 100.0d));
                            }
                            else
                            {
                                wrap.setShowMoney(TCPHelper.formatNum2(taxAll / 100.0d));

                                taxAll = 0;
                            }
                            if(null != approverBean)
                            {
	                            wrap.setStafferId(approverBean.getId());
	                            wrap.setStafferName(approverBean.getName());
                            }

                            wapList.add(wrap);

                            // 退出
                            if (taxAll <= 0)
                            {
                                break;
                            }
                        }
                    }

                    if (taxAll < 0)
                    {
                        request.setAttribute(KeyConstant.ERROR_MESSAGE, "存在尾差请联系管理员修复");

                        return mapping.findForward("error");
                    }

                    if (taxAll > 0)
                    {
                        String showMoney = wapList.get(0).getShowMoney();
                        long newVal = TCPHelper.doubleToLong2(showMoney) + taxAll;

                        wapList.get(0).setShowMoney(TCPHelper.formatNum2(newVal / 100.0d));
                    }
                }

                request.setAttribute("wapList", wapList);
            }

            return mapping.findForward("processExpense" + bean.getType());
        }

        return mapping.findForward("detailExpense" + bean.getType());
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
     * addOrUpdateExpense
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward addOrUpdateExpense(ActionMapping mapping, ActionForm form,
                                            HttpServletRequest request, HttpServletResponse response)
        throws ServletException
    {
        ExpenseApplyBean bean = new ExpenseApplyBean();

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

        // 出差申请的特殊处理
        if (bean.getType() == TcpConstanst.TCP_EXPENSETYPE_TRAVEL)
        {
            changeTravel(bean, rds);
        }

        ActionForward afor = parserAttachment(mapping, request, rds, bean);

        if (afor != null)
        {
            return afor;
        }

        rds.close();

        StafferBean stafferBean = stafferDAO.find(bean.getStafferId());
        
        bean.setStype(stafferBean.getOtype());
        
        // 子项的组装
        fillExpense(rds, bean);

        // 这里是通用报销的模板报销的特殊处理
        if (TCPHelper.isTemplateExpense(bean))
        {
            ActionForward parserExcel = parserExcel(mapping, request, bean);

            if (parserExcel != null)
            {
                return parserExcel;
            }
            
            if (bean.getPayType() == TcpConstanst.PAYTYPE_PAY_YES)
            {
            	if (ListTools.isEmptyOrNull(bean.getPayList()))
            	{
            		 request.setAttribute(KeyConstant.ERROR_MESSAGE, "公司付款给员工时，须有收款明细.");

                     return mapping.findForward("error");
            	}
            }
        }        

        try
        {
            User user = Helper.getUser(request);

            bean.setLogTime(TimeTools.now());

            if ("0".equals(addOrUpdate))
            {
                expenseManager.addExpenseBean(user, bean);
            }
            else
            {
                expenseManager.updateExpenseBean(user, bean);
            }

            request.setAttribute(KeyConstant.MESSAGE, "成功保存费用申请");

            // 提交
            if ("1".equals(oprType))
            {
                expenseManager.submitExpenseBean(user, bean.getId(), processId);
            }

            request.setAttribute(KeyConstant.MESSAGE, "成功提交费用申请");
        }
        catch (MYException e)
        {
            _logger.warn(e, e);

            request.setAttribute(KeyConstant.ERROR_MESSAGE, "操作费用申请失败:" + e.getMessage());
        }

        return mapping.findForward("querySelfExpense" + bean.getType());
    }

    /**
     * parserExcel
     * 
     * @param mapping
     * @param request
     * @param bean
     * @return
     */
    private ActionForward parserExcel(ActionMapping mapping, HttpServletRequest request,
                                      ExpenseApplyBean bean)
    {
        // 解析文件
        List<AttachmentBean> attachmentList = bean.getAttachmentList();

        if (ListTools.isEmptyOrNull(attachmentList) || attachmentList.size() != 1)
        {
            return ActionTools.toError("通用模板报销有且只有一个附件", mapping, request);
        }

        AttachmentBean attachmentBean = attachmentList.get(0);

        ReaderFile reader = ReadeFileFactory.getXLSReader();

        StringBuilder builder = new StringBuilder();

        List<TravelApplyItemBean> itemList = new ArrayList();

        List<TcpShareBean> shareList = new ArrayList<TcpShareBean>();

        try
        {

            reader.readFile(this.getAttachmentPath() + attachmentBean.getPath());

            while (reader.hasNext())
            {
                String[] obj = (String[])reader.next();

                // 第一行忽略
                if (reader.getCurrentLineNumber() == 1)
                {
                    continue;
                }

                TravelApplyItemBean item = new TravelApplyItemBean();

                int currentNumber = reader.getCurrentLineNumber();

                if (obj.length >= 5)
                {
                    StafferBean sb = stafferDAO.findByUnique(obj[0].trim());

                    if (sb == null)
                    {
                        builder
                            .append("第[" + currentNumber + "]行错误:")
                            .append("人员(费用承担人)没有找到")
                            .append("<br>");

                        continue;
                    }

                    if (sb.getStatus() == StafferConstant.STATUS_DROP)
                    {
                        builder
                            .append("第[" + currentNumber + "]行错误:")
                            .append("人员(费用承担人)状态是废弃")
                            .append("<br>");

                        continue;
                    }
                    
                    item.setFeeStafferId(sb.getId());

                    if ( !RegularExpress.isDouble(obj[2].trim()))
                    {
                        builder.append("第[" + currentNumber + "]错误:").append("费用不是数字").append(
                            "<br>");

                        continue;
                    }

                    item.setMoneys(MathTools.doubleToLong2(obj[2].trim()));

                    BudgetBean budgetBean = budgetDAO.findByUnique(obj[3].trim());

                    if (budgetBean == null)
                    {
                        builder.append("第[" + currentNumber + "]错误:").append("预算名称没有找到").append(
                            "<br>");

                        continue;
                    }

                    if (budgetBean.getStatus() != BudgetConstant.BUDGET_STATUS_PASS
                        || budgetBean.getCarryStatus() != BudgetConstant.BUDGET_CARRY_DOING)
                    {
                        builder.append("第[" + currentNumber + "]错误:").append("预算不在执行态中").append(
                            "<br>");

                        continue;
                    }

                    // 细化到预算
                    item.setBudgetId(budgetBean.getId());

                    // 分担的解析
                    TcpShareBean tcpShare = getTcpShare(shareList, budgetBean.getId());

                    if (tcpShare == null)
                    {
                        tcpShare = new TcpShareBean();

                        tcpShare.setApproverId(budgetBean.getSigner());
                        tcpShare.setBudgetId(budgetBean.getId());
                        tcpShare.setDepartmentId(budgetBean.getBudgetDepartment());
                        tcpShare.setRealMonery(item.getMoneys());
                        // add 2012.8.30
                        tcpShare.setBearId(item.getFeeStafferId());

                        shareList.add(tcpShare);
                    }
                    else
                    {
                        tcpShare.setRealMonery(tcpShare.getRealMonery() + item.getMoneys());
                    }

                    FeeItemBean feeItem = feeItemDAO.findByUnique(obj[4].trim());

                    if (feeItem == null)
                    {
                        builder.append("第[" + currentNumber + "]错误:").append("预算项没有找到").append(
                            "<br>");

                        continue;
                    }

                    item.setFeeItemId(feeItem.getId());

                    if (obj.length > 5)
                    {
                        item.setDescription(obj[5]);
                    }

                    item.setBeginDate(TimeTools.now_short());
                    item.setEndDate(TimeTools.now_short());

                    itemList.add(item);

                }
                else
                {
                    builder
                        .append("第[" + currentNumber + "]错误:")
                        .append("数据长度不足5格,描述可以为空,其他不可以为空")
                        .append("<br>");

                    continue;
                }
            }

            if (builder.length() != 0)
            {
                return ActionTools.toError(builder.toString(), mapping, request);
            }

            // 重新赋值
            bean.setItemList(itemList);
            bean.setShareList(shareList);

            long total = 0L;

            for (TravelApplyItemBean travelApplyItemBean : itemList)
            {
                total += travelApplyItemBean.getMoneys();
            }

            bean.setTotal(total);

            List<TravelApplyPayBean> payList = bean.getPayList();

            long paytotal = 0L;

            for (TravelApplyPayBean each : payList)
            {
                paytotal += each.getMoneys();
            }

            bean.setBorrowTotal(paytotal);

            // 不能溢出的
            if (paytotal > total)
            {
                return ActionTools.toError("总费用金额为:" + MathTools.longToDoubleStr2(total)
                                           + ",但是请求付款金额为:" + MathTools.longToDoubleStr2(paytotal)
                                           + ".需要付款金额过大", mapping, request);
            }

        }
        catch (Exception e)
        {
            _logger.error(e, e);

            return ActionTools.toError("附件解析失败", mapping, request);
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

        return null;
    }

    private TcpShareBean getTcpShare(List<TcpShareBean> shareList, String budegId)
    {
        for (TcpShareBean tcpShareBean : shareList)
        {
            if (tcpShareBean.getBudgetId().equals(budegId))
            {
                return tcpShareBean;
            }
        }

        return null;
    }

    /**
     * 处理
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward processExpenseBean(ActionMapping mapping, ActionForm form,
                                            HttpServletRequest request, HttpServletResponse response)
        throws ServletException
    {
        String id = request.getParameter("id");
        String oprType = request.getParameter("oprType");
        String reason = request.getParameter("reason");
        String processId = request.getParameter("processId");
        String compliance = request.getParameter("compliance");
        if (StringTools.isNullOrNone(compliance))
            compliance = "";
        
        try
        {
            User user = Helper.getUser(request);

            TcpParamWrap param = new TcpParamWrap();

            param.setId(id);
            param.setType(oprType);
            param.setReason(reason);
            param.setProcessId(processId);
            param.setCompliance(compliance);

            // 组装参数
            fillWrap(request, param);

            // 提交
            if ("0".equals(oprType))
            {
                expenseManager.passExpenseBean(user, param);
            }
            else
            {
                expenseManager.rejectExpenseBean(user, param);
            }

            request.setAttribute(KeyConstant.MESSAGE, "成功处理报销申请");
        }
        catch (MYException e)
        {
            _logger.warn(e, e);

            request.setAttribute(KeyConstant.ERROR_MESSAGE, "处理报销申请失败:" + e.getMessage());
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
        String[] ppid = request.getParameterValues("p_cid");

        // 稽核处理
        if (ppid != null && ppid.length > 0)
        {
            String[] pcmoneysList = request.getParameterValues("p_cmoneys");
            String[] pcdescriptionList = request.getParameterValues("p_cdescription");
            List<TravelApplyPayBean> payList = travelApplyPayDAO
                .queryEntityBeansByFK(param.getId());
            for (int i = 0; i < ppid.length; i++ )
            {
                for (TravelApplyPayBean travelApplyPayBean : payList)
                {
                    if (travelApplyPayBean.getId().equals(ppid[i]))
                    {
                        travelApplyPayBean.setCmoneys(MathTools.doubleToLong2(pcmoneysList[i]));
                        travelApplyPayBean.setCdescription(pcdescriptionList[i]);
                    }
                }
            }

            param.setOther(payList);
        }

        String[] bankIds = request.getParameterValues("bankId");

        // 财务付款/收款
        if (bankIds != null && bankIds.length > 0)
        {
            int payType = CommonTools.parseInt(request.getParameter("payType"));

            String[] payTypes = request.getParameterValues("payType");
            String[] moneys = request.getParameterValues("money");

            // 付款
            if (payType == TcpConstanst.PAYTYPE_PAY_YES)
            {
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

            // 收款
            if (payType == TcpConstanst.PAYTYPE_PAY_NO)
            {
                List<InBillBean> inBillList = new ArrayList<InBillBean>();

                for (int i = 0; i < bankIds.length; i++ )
                {
                    if (StringTools.isNullOrNone(bankIds[i]))
                    {
                        continue;
                    }

                    InBillBean outBill = new InBillBean();

                    outBill.setBankId(bankIds[i]);

                    outBill.setMoneys(MathTools.parseDouble(moneys[i]));

                    inBillList.add(outBill);
                }

                param.setOther2(inBillList);
            }
        }

        // 凭证
        String[] taxIds = request.getParameterValues("taxId");

        if (taxIds != null && taxIds.length > 0)
        {
            String[] moneys = request.getParameterValues("t_money");
            String[] stafferIds = request.getParameterValues("taxStafferId");

            // FinanceItemBean
            List<String> taxList = new ArrayList<String>();

            List<Long> longList = new ArrayList<Long>();

            List<String> stafferIdList = new ArrayList<String>();

            for (int i = 0; i < taxIds.length; i++ )
            {
                if (StringTools.isNullOrNone(taxIds[i]))
                {
                    continue;
                }
                taxList.add(taxIds[i]);

                // 万单位
                longList.add(MathTools.doubleToLong2(moneys[i]) * 100);

                if (stafferIds != null && stafferIds.length > i)
                {
                    stafferIdList.add(stafferIds[i]);
                }
                else
                {
                    stafferIdList.add("");
                }
            }

            param.setOther(taxList);

            param.setOther2(longList);

            param.setOther3(stafferIdList);
        }
    }

    /**
     * 变成long
     * 
     * @param bean
     * @param rds
     */
    private void changeTravel(ExpenseApplyBean bean, RequestDataStream rds)
    {
        String airplaneCharges = rds.getParameter("airplaneCharges");

        bean.setAirplaneCharges(TCPHelper.doubleToLong2(airplaneCharges));

        String trainCharges = rds.getParameter("trainCharges");

        bean.setTrainCharges(TCPHelper.doubleToLong2(trainCharges));

        String busCharges = rds.getParameter("busCharges");

        bean.setBusCharges(TCPHelper.doubleToLong2(busCharges));

        String hotelCharges = rds.getParameter("hotelCharges");

        bean.setHotelCharges(TCPHelper.doubleToLong2(hotelCharges));

        String entertainCharges = rds.getParameter("entertainCharges");

        bean.setEntertainCharges(TCPHelper.doubleToLong2(entertainCharges));

        String allowanceCharges = rds.getParameter("allowanceCharges");

        bean.setAllowanceCharges(TCPHelper.doubleToLong2(allowanceCharges));

        String other1Charges = rds.getParameter("other1Charges");

        bean.setOther1Charges(TCPHelper.doubleToLong2(other1Charges));

        String other2Charges = rds.getParameter("other2Charges");

        bean.setOther2Charges(TCPHelper.doubleToLong2(other2Charges));
    }

    private long getTravelItemTotal(ExpenseApplyBean bean)
    {
        long total = 0L;

        total += bean.getAirplaneCharges();
        total += bean.getTrainCharges();

        total += bean.getBusCharges();
        total += bean.getHotelCharges();

        total += bean.getEntertainCharges();
        total += bean.getAllowanceCharges();

        total += bean.getOther1Charges();
        total += bean.getOther2Charges();

        return total;
    }

    /**
     * fillExpense
     * 
     * @param rds
     * @param bean
     */
    private void fillExpense(RequestDataStream rds, ExpenseApplyBean bean)
    {
        String lastMoney = rds.getParameter("lastMoney");

        bean.setLastMoney(MathTools.doubleToLong2(lastMoney));       
        
        // 费用明细
        List<TravelApplyItemBean> itemList = new ArrayList<TravelApplyItemBean>();

        bean.setItemList(itemList);

        // i_beginDate
        List<String> beginDateList = rds.getParameters("i_beginDate");
        List<String> productNameList = rds.getParameters("i_productName");
        List<String> amountList = rds.getParameters("i_amount");
        List<String> pricesList = rds.getParameters("i_prices");
        List<String> endDateList = rds.getParameters("i_endDate");
        List<String> feeItemList = rds.getParameters("i_feeItem");
        List<String> moneysList = rds.getParameters("i_moneys");
        List<String> descriptionList = rds.getParameters("i_description");

        // 存在没有的可能
        if (feeItemList != null && feeItemList.size() > 0)
        {
            for (int i = 0; i < feeItemList.size(); i++ )
            {
                String each = feeItemList.get(i);

                if (StringTools.isNullOrNone(each))
                {
                    continue;
                }

                // 过滤差旅费
                if (bean.getType() == TcpConstanst.TCP_EXPENSETYPE_TRAVEL
                    && BudgetConstant.FEE_ITEM_TRAVELLING.equals(each))
                {
                    continue;
                }

                TravelApplyItemBean item = new TravelApplyItemBean();

                if (beginDateList != null && beginDateList.size() > 0)
                {
                    item.setBeginDate(beginDateList.get(i));
                }

                if (endDateList != null && endDateList.size() > 0)
                {
                    item.setEndDate(endDateList.get(i));
                }

                if (productNameList != null && productNameList.size() > 0)
                {
                    item.setProductName(productNameList.get(i));
                }

                if (amountList != null && amountList.size() > 0)
                {
                    item.setAmount(MathTools.parseInt(amountList.get(i)));
                }

                if (pricesList != null && pricesList.size() > 0)
                {
                    item.setPrices(MathTools.doubleToLong2(pricesList.get(i)));
                }

                item.setFeeItemId(feeItemList.get(i));
                item.setMoneys(TCPHelper.doubleToLong2(moneysList.get(i)));
                item.setDescription(descriptionList.get(i));

                itemList.add(item);
            }
        }

        // 特殊处理
        if (bean.getType() == TcpConstanst.TCP_EXPENSETYPE_TRAVEL)
        {
            // 自动组装差旅费
            TravelApplyItemBean travelItem = new TravelApplyItemBean();
            travelItem.setBeginDate(bean.getBeginDate());
            travelItem.setEndDate(bean.getEndDate());
            travelItem.setFeeItemId(BudgetConstant.FEE_ITEM_TRAVELLING);
            travelItem.setMoneys(getTravelItemTotal(bean));
            travelItem.setDescription("系统自动组装的差旅费");

            itemList.add(0, travelItem);
        }

        // 总费用
        long total = 0L;

        for (TravelApplyItemBean each : itemList)
        {
            total += each.getMoneys();
        }

        bean.setTotal(total);

        List<TravelApplyPayBean> payList = new ArrayList<TravelApplyPayBean>();

        bean.setPayList(payList);

        // 公司付款给员工
        if (bean.getPayType() == TcpConstanst.PAYTYPE_PAY_YES)
        {
            List<String> receiveTypeList = rds.getParameters("p_receiveType");
            List<String> bankList = rds.getParameters("p_bank");
            List<String> userNameList = rds.getParameters("p_userName");
            List<String> bankNoList = rds.getParameters("p_bankNo");
            List<String> pmoneysList = rds.getParameters("p_moneys");
            List<String> pdescriptionList = rds.getParameters("p_description");

            if (receiveTypeList != null && receiveTypeList.size() > 0)
            {

                for (int i = 0; i < receiveTypeList.size(); i++ )
                {
                    String each = receiveTypeList.get(i);

                    if (StringTools.isNullOrNone(each))
                    {
                        continue;
                    }

                    TravelApplyPayBean pay = new TravelApplyPayBean();

                    pay.setReceiveType(MathTools.parseInt(receiveTypeList.get(i)));
                    pay.setBankName(bankList.get(i));
                    pay.setUserName(userNameList.get(i));
                    pay.setBankNo(bankNoList.get(i));
                    pay.setMoneys(TCPHelper.doubleToLong2(pmoneysList.get(i)));
                    pay.setDescription(pdescriptionList.get(i));

                    payList.add(pay);
                }

                long paytotal = 0L;

                for (TravelApplyPayBean each : payList)
                {
                    paytotal += each.getMoneys();
                }

                bean.setBorrowTotal(paytotal);
            }
        }

        List<String> budgetIdeList = rds.getParameters("s_budgetId");

        if ( !ListTools.isEmptyOrNull(budgetIdeList))
        {
            // 费用分担(只有通用报销的时候需要,也不是的要是申请过时了,也要重新指定)
            List<TcpShareBean> shareList = new ArrayList<TcpShareBean>();

            bean.setShareList(shareList);

            List<String> departmentIdList = rds.getParameters("s_departmentId");
            List<String> approverIdList = rds.getParameters("s_approverId");
            List<String> bearIdList = rds.getParameters("s_bearId");
            List<String> ratioList = rds.getParameters("s_ratio");

            int rtotal = 0;

            for (String each : ratioList)
            {
                rtotal += MathTools.parseInt(each);
            }

            int shareType = 0;

            if (rtotal != 100)
            {
                shareType = 1;
            }

            for (int i = 0; i < budgetIdeList.size(); i++ )
            {
                String each = budgetIdeList.get(i);

                if (StringTools.isNullOrNone(each))
                {
                    continue;
                }

                TcpShareBean share = new TcpShareBean();

                share.setBudgetId(budgetIdeList.get(i));
                share.setDepartmentId(departmentIdList.get(i));
                share.setApproverId(approverIdList.get(i));

                if (bearIdList == null || bearIdList.size() < i
                    || StringTools.isNullOrNone(bearIdList.get(i)))
                {
                    share.setBearId(bean.getStafferId());
                }
                else
                {
                    share.setBearId(bearIdList.get(i));
                }

                // 自动识别是分担还是金额
                if (shareType == 0)
                {
                    share.setRatio(MathTools.parseInt(ratioList.get(i).trim()));
                }
                else
                {
                    share.setRealMonery(TCPHelper.doubleToLong2(ratioList.get(i).trim()));
                }

                shareList.add(share);
            }
        }
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
                                           RequestDataStream rds, ExpenseApplyBean travelApply)
    {
        List<AttachmentBean> attachmentList = new ArrayList<AttachmentBean>();

        travelApply.setAttachmentList(attachmentList);

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

                return mapping.findForward("querySelfTravelApply");
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

    /**
     * exportExpense
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward exportExpense(ActionMapping mapping, ActionForm form,
                                       HttpServletRequest request, HttpServletResponse response)
        throws ServletException
    {
        OutputStream out = null;

        String filenName = "TCP_APPLY_" + TimeTools.now("MMddHHmmss") + ".csv";

        response.setContentType("application/x-dbf");

        response.setHeader("Content-Disposition", "attachment; filename=" + filenName);

        WriteFile write = null;

        ConditionParse condtion = JSONPageSeparateTools.getCondition(request, QUERYALLEXPENSE);

        int count = this.expenseApplyDAO.countVOByCondition(condtion.toString());

        try
        {
            out = response.getOutputStream();

            write = WriteFileFactory.getMyTXTWriter();

            write.openFile(out);

            write.writeLine("日期,标识,关联申请,目的,申请人,系列,类型,状态,申请费用");

            PageSeparate page = new PageSeparate();

            page.reset2(count, 2000);

            WriteFileBuffer line = new WriteFileBuffer(write);

            while (page.nextPage())
            {
                List<ExpenseApplyVO> voFList = expenseApplyDAO.queryEntityVOsByCondition(condtion,
                    page);

                for (ExpenseApplyVO vo : voFList)
                {
                    line.reset();

                    TCPHelper.chageVO(vo);

                    line.writeColumn("[" + vo.getLogTime() + "]");
                    line.writeColumn(vo.getId());
                    line.writeColumn(vo.getRefId());
                    line.writeColumn(StringTools.getExportString(vo.getName()));
                    line.writeColumn(vo.getStafferName());

                    line.writeColumn(ElTools.get("tcpStype", vo.getStype()));
                    line.writeColumn(ElTools.get("tcpType", vo.getType()));
                    line.writeColumn(ElTools.get("tcpStatus", vo.getStatus()));

                    line.writeColumn(changeString(vo.getShowTotal()));

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
        return ConfigLoader.getProperty("tcpAttachmentPath");
    }

    /**
     * 导入费用分摊比例 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward importShare(ActionMapping mapping, ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response)
	throws ServletException
	{
        RequestDataStream rds = new RequestDataStream(request);
        
        boolean importError = false;
        
        List<TcpShareVO> importItemList = new ArrayList<TcpShareVO>(); 
        
        StringBuilder builder = new StringBuilder();       
        
        try
        {
            rds.parser();
        }
        catch (Exception e1)
        {
            _logger.error(e1, e1);

            request.setAttribute(KeyConstant.ERROR_MESSAGE, "解析失败");

            return mapping.findForward("importShare");
        }

        if ( !rds.haveStream())
        {
            request.setAttribute(KeyConstant.ERROR_MESSAGE, "解析失败");

            return mapping.findForward("importShare");
        }
        
        String type = rds.getParameter("type");

        request.setAttribute("type", type);
        
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
                	TcpShareVO item = new TcpShareVO();
                    
                    // 预算
                    if ( !StringTools.isNullOrNone(obj[0]))
                    {
                        String name = obj[0];

                        BudgetBean bean = budgetDAO.findByUnique(name);

                        if (bean == null)
                        {
                            builder
                                .append("<font color=red>第[" + currentNumber + "]行错误:")
                                .append("预算不存在")
                                .append("</font><br>");

                            importError = true;
                        }
                        else
                        {
                        	//有效性检查
                            if (bean.getCarryStatus() != 1)
                            {
                                builder
                                .append("<font color=red>第[" + currentNumber + "]行错误:")
                                .append("不是执行中的预算")
                                .append("</font><br>");

                                importError = true;
                            }
                            
                            if (bean.getType() != 2)
                            {
                                builder
                                    .append("<font color=red>第[" + currentNumber + "]行错误:")
                                    .append("不是部门预算")
                                    .append("</font><br>");

                                importError = true;
                            }
                            
                            if (bean.getLevel() != 2)
                            {
                                builder
                                .append("<font color=red>第[" + currentNumber + "]行错误:")
                                .append("不是月度预算")
                                .append("</font><br>");

                                importError = true;
                            }
                            
                            if (bean.getStatus() != 3)
                            {
                                builder
                                .append("<font color=red>第[" + currentNumber + "]行错误:")
                                .append("不存在通过状态预算")
                                .append("</font><br>");

                                importError = true;
                            }
                        }

                        if (!importError)
                        {
                            item.setBudgetId(bean.getId());
                            item.setBudgetName(bean.getName());
                            item.setDepartmentId(bean.getBudgetDepartment());

                            PrincipalshipBean prin = principalshipDAO.find(bean.getBudgetDepartment());

                            if (null != prin)
                            	item.setDepartmentName(prin.getName());

                            item.setApproverId(bean.getSigner());
                            
                            StafferBean staffer = stafferDAO.find(bean.getSigner());
                            
                            if (null != staffer)
                            	item.setApproverName(staffer.getName());
                        }

                    }

                    // 承担人
                    if ( !StringTools.isNullOrNone(obj[1]))
                    {
                        String name = obj[1];

                        StafferBean staff = stafferDAO.findByUnique(name);

                        if (null == staff)
                        {
                            builder
                            .append("<font color=red>第[" + currentNumber + "]行错误:")
                            .append("承担人不存在")
                            .append("</font><br>");

                            importError = true;
                        }
                        else
                        {
                        	item.setBearId(staff.getId());
                            item.setBearName(staff.getName());
                        }
                    }

                    // 分摊比例/金额
                    if ( !StringTools.isNullOrNone(obj[2]))
                    {
                        String showRealMonery = obj[2];

                        item.setShowRealMonery(showRealMonery);

                    }
                    
                    importItemList.add(item);
                    
                }
                else
                {
                    builder
                        .append("第[" + currentNumber + "]错误:")
                        .append("数据长度不足2格错误")
                        .append("<br>");
                    
                    importError = true;
                }
            }
            
            
        }catch (Exception e)
        {
            _logger.error(e, e);
            
            request.setAttribute(KeyConstant.ERROR_MESSAGE, e.toString());

            return mapping.findForward("importShare");
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

            return mapping.findForward("importShare");
        }
        
        request.setAttribute("imp", true);
        
        TravelApplyVO bean = new TravelApplyVO();
        
        bean.setShareVOList(importItemList);
        
        request.setAttribute("bean", bean);
        
        prepareInner(request);
        
        int itype = MathTools.parseInt(type);
        
        if (itype <= 10)
        {
        	return mapping.findForward("addTravelApply" + type);
        }
        
        return mapping.findForward("addExpense" + type);
	}
    
    /**
     * 
     * @param obj
     * @return
     */
    private String[] fillObj(String[] obj)
    {
        String[] result = new String[3];

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
     * @return the tcpApplyDAO
     */
    public TcpApplyDAO getTcpApplyDAO()
    {
        return tcpApplyDAO;
    }

    /**
     * @param tcpApplyDAO
     *            the tcpApplyDAO to set
     */
    public void setTcpApplyDAO(TcpApplyDAO tcpApplyDAO)
    {
        this.tcpApplyDAO = tcpApplyDAO;
    }

    /**
     * @return the tcpFlowDAO
     */
    public TcpFlowDAO getTcpFlowDAO()
    {
        return tcpFlowDAO;
    }

    /**
     * @param tcpFlowDAO
     *            the tcpFlowDAO to set
     */
    public void setTcpFlowDAO(TcpFlowDAO tcpFlowDAO)
    {
        this.tcpFlowDAO = tcpFlowDAO;
    }

    /**
     * @return the tcpPrepaymentDAO
     */
    public TcpPrepaymentDAO getTcpPrepaymentDAO()
    {
        return tcpPrepaymentDAO;
    }

    /**
     * @param tcpPrepaymentDAO
     *            the tcpPrepaymentDAO to set
     */
    public void setTcpPrepaymentDAO(TcpPrepaymentDAO tcpPrepaymentDAO)
    {
        this.tcpPrepaymentDAO = tcpPrepaymentDAO;
    }

    /**
     * @return the tcpShareDAO
     */
    public TcpShareDAO getTcpShareDAO()
    {
        return tcpShareDAO;
    }

    /**
     * @param tcpShareDAO
     *            the tcpShareDAO to set
     */
    public void setTcpShareDAO(TcpShareDAO tcpShareDAO)
    {
        this.tcpShareDAO = tcpShareDAO;
    }

    /**
     * @return the travelApplyDAO
     */
    public TravelApplyDAO getTravelApplyDAO()
    {
        return travelApplyDAO;
    }

    /**
     * @param travelApplyDAO
     *            the travelApplyDAO to set
     */
    public void setTravelApplyDAO(TravelApplyDAO travelApplyDAO)
    {
        this.travelApplyDAO = travelApplyDAO;
    }

    /**
     * @return the travelApplyItemDAO
     */
    public TravelApplyItemDAO getTravelApplyItemDAO()
    {
        return travelApplyItemDAO;
    }

    /**
     * @param travelApplyItemDAO
     *            the travelApplyItemDAO to set
     */
    public void setTravelApplyItemDAO(TravelApplyItemDAO travelApplyItemDAO)
    {
        this.travelApplyItemDAO = travelApplyItemDAO;
    }

    /**
     * @return the travelApplyPayDAO
     */
    public TravelApplyPayDAO getTravelApplyPayDAO()
    {
        return travelApplyPayDAO;
    }

    /**
     * @param travelApplyPayDAO
     *            the travelApplyPayDAO to set
     */
    public void setTravelApplyPayDAO(TravelApplyPayDAO travelApplyPayDAO)
    {
        this.travelApplyPayDAO = travelApplyPayDAO;
    }

    /**
     * @return the budgetItemDAO
     */
    public BudgetItemDAO getBudgetItemDAO()
    {
        return budgetItemDAO;
    }

    /**
     * @param budgetItemDAO
     *            the budgetItemDAO to set
     */
    public void setBudgetItemDAO(BudgetItemDAO budgetItemDAO)
    {
        this.budgetItemDAO = budgetItemDAO;
    }

    /**
     * @return the feeItemDAO
     */
    public FeeItemDAO getFeeItemDAO()
    {
        return feeItemDAO;
    }

    /**
     * @param feeItemDAO
     *            the feeItemDAO to set
     */
    public void setFeeItemDAO(FeeItemDAO feeItemDAO)
    {
        this.feeItemDAO = feeItemDAO;
    }

    /**
     * @return the attachmentDAO
     */
    public AttachmentDAO getAttachmentDAO()
    {
        return attachmentDAO;
    }

    /**
     * @param attachmentDAO
     *            the attachmentDAO to set
     */
    public void setAttachmentDAO(AttachmentDAO attachmentDAO)
    {
        this.attachmentDAO = attachmentDAO;
    }

    /**
     * @return the flowLogDAO
     */
    public FlowLogDAO getFlowLogDAO()
    {
        return flowLogDAO;
    }

    /**
     * @param flowLogDAO
     *            the flowLogDAO to set
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
     * @param tcpApproveDAO
     *            the tcpApproveDAO to set
     */
    public void setTcpApproveDAO(TcpApproveDAO tcpApproveDAO)
    {
        this.tcpApproveDAO = tcpApproveDAO;
    }

    /**
     * @return the tcpFlowManager
     */
    public TcpFlowManager getTcpFlowManager()
    {
        return tcpFlowManager;
    }

    /**
     * @param tcpFlowManager
     *            the tcpFlowManager to set
     */
    public void setTcpFlowManager(TcpFlowManager tcpFlowManager)
    {
        this.tcpFlowManager = tcpFlowManager;
    }

    /**
     * @return the outBillDAO
     */
    public OutBillDAO getOutBillDAO()
    {
        return outBillDAO;
    }

    /**
     * @param outBillDAO
     *            the outBillDAO to set
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
     * @param financeDAO
     *            the financeDAO to set
     */
    public void setFinanceDAO(FinanceDAO financeDAO)
    {
        this.financeDAO = financeDAO;
    }

    /**
     * @return the tcpHandleHisDAO
     */
    public TcpHandleHisDAO getTcpHandleHisDAO()
    {
        return tcpHandleHisDAO;
    }

    /**
     * @param tcpHandleHisDAO
     *            the tcpHandleHisDAO to set
     */
    public void setTcpHandleHisDAO(TcpHandleHisDAO tcpHandleHisDAO)
    {
        this.tcpHandleHisDAO = tcpHandleHisDAO;
    }

    /**
     * @return the expenseApplyDAO
     */
    public ExpenseApplyDAO getExpenseApplyDAO()
    {
        return expenseApplyDAO;
    }

    /**
     * @param expenseApplyDAO
     *            the expenseApplyDAO to set
     */
    public void setExpenseApplyDAO(ExpenseApplyDAO expenseApplyDAO)
    {
        this.expenseApplyDAO = expenseApplyDAO;
    }

    /**
     * @return the expenseManager
     */
    public ExpenseManager getExpenseManager()
    {
        return expenseManager;
    }

    /**
     * @param expenseManager
     *            the expenseManager to set
     */
    public void setExpenseManager(ExpenseManager expenseManager)
    {
        this.expenseManager = expenseManager;
    }

    /**
     * @return the inBillDAO
     */
    public InBillDAO getInBillDAO()
    {
        return inBillDAO;
    }

    /**
     * @param inBillDAO
     *            the inBillDAO to set
     */
    public void setInBillDAO(InBillDAO inBillDAO)
    {
        this.inBillDAO = inBillDAO;
    }

    /**
     * @return the stafferDAO
     */
    public StafferDAO getStafferDAO()
    {
        return stafferDAO;
    }

    /**
     * @param stafferDAO
     *            the stafferDAO to set
     */
    public void setStafferDAO(StafferDAO stafferDAO)
    {
        this.stafferDAO = stafferDAO;
    }

    /**
     * @return the budgetDAO
     */
    public BudgetDAO getBudgetDAO()
    {
        return budgetDAO;
    }

    /**
     * @param budgetDAO
     *            the budgetDAO to set
     */
    public void setBudgetDAO(BudgetDAO budgetDAO)
    {
        this.budgetDAO = budgetDAO;
    }

	public PrincipalshipDAO getPrincipalshipDAO()
	{
		return principalshipDAO;
	}

	public void setPrincipalshipDAO(PrincipalshipDAO principalshipDAO)
	{
		this.principalshipDAO = principalshipDAO;
	}

}
