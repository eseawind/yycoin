/**
 * File Name: StafferAction.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * Creater: zhuAchen<br>
 * CreateTime: 2008-11-2<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.budget.action;


import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
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
import com.china.center.actionhelper.common.HandleQueryCondition;
import com.china.center.actionhelper.common.JSONTools;
import com.china.center.actionhelper.common.KeyConstant;
import com.china.center.actionhelper.common.PageSeparateTools;
import com.china.center.actionhelper.json.AjaxResult;
import com.china.center.actionhelper.query.HandleHint;
import com.china.center.actionhelper.query.HandleResult;
import com.china.center.common.MYException;
import com.china.center.jdbc.util.ConditionParse;
import com.china.center.jdbc.util.PageSeparate;
import com.china.center.oa.budget.bean.BudgetApplyBean;
import com.china.center.oa.budget.bean.BudgetBean;
import com.china.center.oa.budget.bean.BudgetItemBean;
import com.china.center.oa.budget.bean.FeeItemBean;
import com.china.center.oa.budget.constant.BudgetConstant;
import com.china.center.oa.budget.dao.BudgetApplyDAO;
import com.china.center.oa.budget.dao.BudgetDAO;
import com.china.center.oa.budget.dao.BudgetItemDAO;
import com.china.center.oa.budget.dao.BudgetLogDAO;
import com.china.center.oa.budget.dao.FeeItemDAO;
import com.china.center.oa.budget.facade.BudgetFacade;
import com.china.center.oa.budget.helper.BudgetHelper;
import com.china.center.oa.budget.manager.BudgetApplyManager;
import com.china.center.oa.budget.manager.BudgetManager;
import com.china.center.oa.budget.vo.BudgetItemVO;
import com.china.center.oa.budget.vo.BudgetLogVO;
import com.china.center.oa.budget.vo.BudgetVO;
import com.china.center.oa.publics.Helper;
import com.china.center.oa.publics.LocationHelper;
import com.china.center.oa.publics.bean.PrincipalshipBean;
import com.china.center.oa.publics.constant.AuthConstant;
import com.china.center.oa.publics.constant.PublicConstant;
import com.china.center.oa.publics.dao.LogDAO;
import com.china.center.oa.publics.manager.OrgManager;
import com.china.center.oa.publics.manager.UserManager;
import com.china.center.oa.publics.vo.LogVO;
import com.china.center.tools.BeanUtil;
import com.china.center.tools.CommonTools;
import com.china.center.tools.MathTools;
import com.china.center.tools.RequestDataStream;
import com.china.center.tools.StringTools;
import com.china.center.tools.TimeTools;


/**
 * BudgetAction
 * 
 * @author ZHUZHU
 * @version 2011-5-14
 * @see BudgetAction
 * @since 3.0
 */
public class BudgetAction extends DispatchAction
{
    private final Log _logger = LogFactory.getLog(getClass());

    private UserManager userManager = null;

    private BudgetManager budgetManager = null;

    private BudgetFacade budgetFacade = null;

    private BudgetApplyDAO budgetApplyDAO = null;

    private BudgetApplyManager budgetApplyManager = null;

    private BudgetDAO budgetDAO = null;

    private OrgManager orgManager = null;

    private BudgetLogDAO budgetLogDAO = null;

    private FeeItemDAO feeItemDAO = null;

    private BudgetItemDAO budgetItemDAO = null;

    private LogDAO logDAO = null;

    private static String QUERYBUDGET = "queryBudget";

    private static String QUERYBUDGETFORAPPROVE = "queryBudgetForApprove";

    private static String QUERYBUDGETLOG = "queryBudgetLog";

    private static String QUERYRUNBUDGET = "queryRunBudget";

    private static String QUERYSELFBUDGETAPPLY = "querySelfBudgetApply";

    private static String QUERYFEEITEM = "queryFeeItem";

    private static String QUERYALLBUDGETLOG = "queryAllBudgetLog";

    private static String RPTQUERYRUNDEPARTMENTBUDGET = "rptQueryRunDepartmentBudget";

    private static String QUERYBUDGETAPPLYFORAPPROVE = "queryBudgetApplyForApprove";

    private static String RPTQUERYDEPARTMENTYEARBUDGET = "rptQueryDepartmentYearBudget";
    /**
     * default constructor
     */
    public BudgetAction()
    {
    }

    /**
     * queryBudget(查询预算)
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward queryBudget(ActionMapping mapping, ActionForm form,
                                     HttpServletRequest request, HttpServletResponse response)
        throws ServletException
    {
        ConditionParse condtion = new ConditionParse();

        condtion.addWhereStr();

        // User user = Helper.getUser(request);

        ActionTools.processJSONQueryCondition(QUERYBUDGET, request, condtion);

        // 查询自己的或者通过的
        // condtion.addCondition("and (BudgetBean.stafferId = '" + user.getStafferId()
        // + "' or BudgetBean.status = " + BudgetConstant.BUDGET_STATUS_PASS
        // + ")");

        condtion.addCondition("order by BudgetBean.logTime desc");

        String jsonstr = ActionTools.queryVOByJSONAndToString(QUERYBUDGET, request, condtion,
            this.budgetDAO, new HandleResult<BudgetVO>()
            {
                public void handle(BudgetVO obj)
                {
                    warpBudgetVO(obj);
                }
            });

        return JSONTools.writeResponse(response, jsonstr);
    }

    /**
     * querySelfBudget
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward querySelfBudget(ActionMapping mapping, ActionForm form,
                                         HttpServletRequest request, HttpServletResponse response)
        throws ServletException
    {
        ConditionParse condtion = new ConditionParse();

        condtion.addWhereStr();

        User user = Helper.getUser(request);

        ActionTools.processJSONQueryCondition(QUERYBUDGET, request, condtion);

        condtion.addCondition("BudgetBean.signer", "=", user.getStafferId());

        condtion.addCondition("order by BudgetBean.logTime desc");

        String jsonstr = ActionTools.queryVOByJSONAndToString(QUERYBUDGET, request, condtion,
            this.budgetDAO, new HandleResult<BudgetVO>()
            {
                public void handle(BudgetVO obj)
                {
                    warpBudgetVO(obj);
                }
            });

        return JSONTools.writeResponse(response, jsonstr);
    }

    /**
     * rptQueryRunDepartmentBudget
     * 
     * @param mapping
     * @param form
     * @param request
     * @param reponse
     * @return
     * @throws ServletException
     */
    public ActionForward rptQueryRunDepartmentBudget(ActionMapping mapping, ActionForm form,
                                                     HttpServletRequest request,
                                                     HttpServletResponse reponse)
        throws ServletException
    {
        CommonTools.saveParamers(request);

        List<BudgetVO> list = null;

        String cacheKey = RPTQUERYRUNDEPARTMENTBUDGET;

        if (PageSeparateTools.isFirstLoad(request))
        {
            ConditionParse condtion = new ConditionParse();

            condtion.addWhereStr();

            setQueryRunDepartmentBudgetCondition(request, condtion);

            int total = budgetDAO.countVOByCondition(condtion.toString());

            PageSeparate page = new PageSeparate(total, PublicConstant.PAGE_COMMON_SIZE);

            PageSeparateTools.initPageSeparate(condtion, page, request, cacheKey);

            list = budgetDAO.queryEntityVOsByCondition(condtion, page);
        }
        else
        {
            PageSeparateTools.processSeparate(request, cacheKey);

            list = budgetDAO.queryEntityVOsByCondition(PageSeparateTools.getCondition(request,
                cacheKey), PageSeparateTools.getPageSeparate(request, cacheKey));
        }

        for (BudgetVO budgetVO : list)
        {
            warpBudgetVO(budgetVO);
        }

        request.setAttribute("beanList", list);

        return mapping.findForward(cacheKey);
    }

    /**
     * 
     * @param mapping
     * @param form
     * @param request
     * @param reponse
     * @return
     * @throws ServletException
     */
    public ActionForward rptQueryDepartmentYearBudget(ActionMapping mapping, ActionForm form,
            HttpServletRequest request,
            HttpServletResponse reponse)
    throws ServletException
{
        CommonTools.saveParamers(request);

        List<BudgetVO> list = null;

        String cacheKey = RPTQUERYDEPARTMENTYEARBUDGET;

        if (PageSeparateTools.isFirstLoad(request))
        {
            ConditionParse condtion = new ConditionParse();

            condtion.addWhereStr();

            setQueryDepartmentYearBudgetCondition(request, condtion);

            int total = budgetDAO.countVOByCondition(condtion.toString());

            PageSeparate page = new PageSeparate(total, PublicConstant.PAGE_COMMON_SIZE);

            PageSeparateTools.initPageSeparate(condtion, page, request, cacheKey);

            list = budgetDAO.queryEntityVOsByCondition(condtion, page);
        }
        else
        {
            PageSeparateTools.processSeparate(request, cacheKey);

            list = budgetDAO.queryEntityVOsByCondition(PageSeparateTools.getCondition(request,
                cacheKey), PageSeparateTools.getPageSeparate(request, cacheKey));
        }

        for (BudgetVO budgetVO : list)
        {
            warpBudgetVO(budgetVO);
        }

        request.setAttribute("beanList", list);

        return mapping.findForward(cacheKey);
    }
    
    /**
     * 
     * @param request
     * @param condition
     */
    private void setQueryDepartmentYearBudgetCondition(HttpServletRequest request,
            ConditionParse condition)
    {
        String name = request.getParameter("name");

        String departmentName = request.getParameter("departmentName");

        // 权签人
        String stafferName = request.getParameter("stafferName");

        if ( !StringTools.isNullOrNone(name))
        {
            condition.addCondition("BudgetBean.name", "like", name);
        }

        if ( !StringTools.isNullOrNone(departmentName))
        {
            condition.addCondition("PrincipalshipBean.name", "like", departmentName);
        }

        if ( !StringTools.isNullOrNone(stafferName))
        {
            condition.addCondition("SIGN.name", "like", stafferName);
        }

        condition.addIntCondition("BudgetBean.carryStatus", "=", BudgetConstant.BUDGET_CARRY_DOING);

        condition.addIntCondition("BudgetBean.type", "=", BudgetConstant.BUDGET_TYPE_DEPARTMENT);

        condition.addIntCondition("BudgetBean.level", "=", BudgetConstant.BUDGET_LEVEL_YEAR);

        condition.addIntCondition("BudgetBean.status", "=", BudgetConstant.BUDGET_STATUS_PASS);

    } 
    /**
     * setQueryRunDepartmentBudgetCondition
     * 
     * @param request
     * @param condition
     */
    private void setQueryRunDepartmentBudgetCondition(HttpServletRequest request,
                                                      ConditionParse condition)
    {
        String name = request.getParameter("name");

        String departmentName = request.getParameter("departmentName");

        // 权签人
        String stafferName = request.getParameter("stafferName");

        if ( !StringTools.isNullOrNone(name))
        {
            condition.addCondition("BudgetBean.name", "like", name);
        }

        if ( !StringTools.isNullOrNone(departmentName))
        {
            condition.addCondition("PrincipalshipBean.name", "like", departmentName);
        }

        if ( !StringTools.isNullOrNone(stafferName))
        {
            condition.addCondition("SIGN.name", "like", stafferName);
        }

        condition.addIntCondition("BudgetBean.carryStatus", "=", BudgetConstant.BUDGET_CARRY_DOING);

        condition.addIntCondition("BudgetBean.type", "=", BudgetConstant.BUDGET_TYPE_DEPARTMENT);

        condition.addIntCondition("BudgetBean.level", "=", BudgetConstant.BUDGET_LEVEL_MONTH);

        condition.addIntCondition("BudgetBean.status", "=", BudgetConstant.BUDGET_STATUS_PASS);

        // 时间在当前时间内的有效预算
        condition.addCondition("BudgetBean.endDate", ">=", TimeTools.now_short());

        condition.addCondition("BudgetBean.beginDate", "<=", TimeTools.now_short());
    }

    /**
     * queryBudgetForApprove
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward queryBudgetForApprove(ActionMapping mapping, ActionForm form,
                                               HttpServletRequest request,
                                               HttpServletResponse response)
        throws ServletException
    {
        ConditionParse condtion = new ConditionParse();

        condtion.addWhereStr();

        User user = Helper.getUser(request);

        String fowardType = request.getParameter("fowardType");

        // 根预算审批审批
        if ("0".equals(fowardType))
        {
            if (userManager.containAuth(user, AuthConstant.BUDGET_ROOT_CHECK))
            {
                condtion.addIntCondition("BudgetBean.status", "=",
                    BudgetConstant.BUDGET_STATUS_SUBMIT);

                condtion
                    .addIntCondition("BudgetBean.type", "=", BudgetConstant.BUDGET_TYPE_COMPANY);
            }
        }
        // 事业部预算审批
        else if ("1".equals(fowardType))
        {
            if (userManager.containAuth(user, AuthConstant.BUDGET_LOCATION_CHECK))
            {
                condtion.addIntCondition("BudgetBean.status", "=",
                    BudgetConstant.BUDGET_STATUS_SUBMIT);

                condtion.addIntCondition("BudgetBean.type", "=",
                    BudgetConstant.BUDGET_TYPE_LOCATION);
            }
        }
        // 部门预算审批
        else if ("2".equals(fowardType))
        {
            if (userManager.containAuth(user, AuthConstant.BUDGET_DEPARTMENT_CHECK))
            {
                condtion.addIntCondition("BudgetBean.status", "=",
                    BudgetConstant.BUDGET_STATUS_SUBMIT);

                condtion.addIntCondition("BudgetBean.type", "=",
                    BudgetConstant.BUDGET_TYPE_DEPARTMENT);
            }
        }
        else
        {
            condtion.addFlaseCondition();
        }

        condtion.addCondition("BudgetBean.signer", "=", user.getStafferId());

        ActionTools.processJSONQueryCondition(QUERYBUDGETFORAPPROVE, request, condtion);

        condtion.addCondition("order by BudgetBean.logTime desc");

        String jsonstr = ActionTools.queryVOByJSONAndToString(QUERYBUDGETFORAPPROVE, request,
            condtion, this.budgetDAO, new HandleResult<BudgetVO>()
            {
                public void handle(BudgetVO obj)
                {
                    warpBudgetVO(obj);
                }
            });

        return JSONTools.writeResponse(response, jsonstr);
    }

    /**
     * queryBudgetApplyForApprove
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward queryBudgetApplyForApprove(ActionMapping mapping, ActionForm form,
                                                    HttpServletRequest request,
                                                    HttpServletResponse response)
        throws ServletException
    {
        ConditionParse condtion = new ConditionParse();

        condtion.addWhereStr();

        User user = Helper.getUser(request);

        String fowardType = request.getParameter("fowardType");

        // 公司预算变更审批
        if ("0".equals(fowardType))
        {
            if (userManager.containAuth(user, AuthConstant.BUDGET_ROOT_CHECK))
            {
                condtion
                    .addIntCondition("BudgetBean.type", "=", BudgetConstant.BUDGET_TYPE_COMPANY);
            }
        }
        // 事业部预算变更审批
        else if ("1".equals(fowardType))
        {
            if (userManager.containAuth(user, AuthConstant.BUDGET_LOCATION_CHECK))
            {
                condtion.addIntCondition("BudgetBean.type", "=",
                    BudgetConstant.BUDGET_TYPE_LOCATION);
            }
        }
        // 部门预算变更审批
        else if ("2".equals(fowardType))
        {
            if (userManager.containAuth(user, AuthConstant.BUDGET_DEPARTMENT_CHECK))
            {
                condtion.addIntCondition("BudgetBean.type", "=",
                    BudgetConstant.BUDGET_TYPE_DEPARTMENT);
            }
        }
        else
        {
            condtion.addFlaseCondition();
        }

        ActionTools.processJSONQueryCondition(QUERYBUDGETAPPLYFORAPPROVE, request, condtion);

        condtion.addIntCondition("BudgetApplyBean.status", "=",
            BudgetConstant.BUDGET_APPLY_STATUS_WAIT_APPROVE);

        condtion.addCondition("BudgetBean.signer", "=", user.getStafferId());

        condtion.addCondition("order by BudgetApplyBean.logTime desc");

        String jsonstr = ActionTools.queryVOByJSONAndToString(QUERYBUDGETAPPLYFORAPPROVE, request,
            condtion, this.budgetApplyDAO);

        return JSONTools.writeResponse(response, jsonstr);
    }

    /**
     * queryBudgetLog
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward queryBudgetLog(ActionMapping mapping, ActionForm form,
                                        final HttpServletRequest request,
                                        HttpServletResponse response)
        throws ServletException
    {
        ConditionParse condtion = new ConditionParse();

        condtion.addWhereStr();

        ActionTools.processJSONQueryCondition(QUERYBUDGETLOG, request, condtion);

        condtion.addCondition("order by BudgetLogBean.logTime desc");

        String jsonstr = ActionTools.queryVOByJSONAndToString(QUERYBUDGETLOG, request, condtion,
            this.budgetLogDAO, new HandleResult<BudgetLogVO>()
            {
                public void handle(BudgetLogVO obj)
                {
                    BudgetHelper.formatBudgetLog(obj);
                }
            }, new HandleHint<BudgetLogVO>()
            {
                public String getHint(List<BudgetLogVO> list)
                {
                    return "当前合计金额"
                           + MathTools.formatNum2(budgetLogDAO
                               .sumVOBudgetLogByCondition(PageSeparateTools.getCondition(request,
                                   QUERYBUDGETLOG)) / 100.0d);
                }
            });

        return JSONTools.writeResponse(response, jsonstr);
    }

    /**
     * queryAllLog
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward queryAllBudgetLog(ActionMapping mapping, ActionForm form,
                                           HttpServletRequest request, HttpServletResponse response)
        throws ServletException
    {
        final String itemId = request.getParameter("itemId");

        CommonTools.saveParamers(request);

        BudgetItemBean budgetItemBean = budgetItemDAO.find(itemId);

        if (budgetItemBean == null)
        {
            return ActionTools.toError(mapping, request);
        }

        BudgetBean budgetBean = budgetDAO.find(budgetItemBean.getBudgetId());

        if (budgetBean == null)
        {
            return ActionTools.toError(mapping, request);
        }

        String pfix = "";

        if (budgetBean.getType() == BudgetConstant.BUDGET_TYPE_COMPANY)
        {
            pfix = "0";
        }
        else if (budgetBean.getType() == BudgetConstant.BUDGET_TYPE_LOCATION)
        {
            pfix = "1";
        }
        else if (budgetBean.getType() == BudgetConstant.BUDGET_TYPE_DEPARTMENT)
        {
            if (budgetBean.getLevel() == BudgetConstant.BUDGET_LEVEL_YEAR)
            {
                pfix = "2";
            }
            else
            {
                pfix = "";
            }
        }

        final String fpix = pfix;

        ActionTools.commonQueryInPageSeparate(QUERYALLBUDGETLOG, request, this.budgetLogDAO, 200,
            new HandleQueryCondition()
            {

                public void setQueryCondition(HttpServletRequest request, ConditionParse condtion)
                {
                    condtion.addCondition("BudgetLogBean.budgetItemId" + fpix, "=", itemId);

                    condtion.addCondition("order by BudgetLogBean.id desc");
                }
            });

        return mapping.findForward(QUERYALLBUDGETLOG);
    }

    /**
     * query run budget
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward queryRunBudget(ActionMapping mapping, ActionForm form,
                                        HttpServletRequest request, HttpServletResponse response)
        throws ServletException
    {
        ConditionParse condtion = new ConditionParse();

        condtion.addWhereStr();

        // User user = Helper.getUser(request);

        // only can query pass and self budget
        // condtion.addCondition("BudgetBean.stafferId", "=", user.getStafferId());

        condtion.addIntCondition("BudgetBean.status", "=", BudgetConstant.BUDGET_STATUS_PASS);

        ActionTools.processJSONQueryCondition(QUERYRUNBUDGET, request, condtion);

        condtion.addCondition("order by BudgetBean.logTime desc");

        String jsonstr = ActionTools.queryVOByJSONAndToString(QUERYRUNBUDGET, request, condtion,
            this.budgetDAO, new HandleResult<BudgetVO>()
            {
                public void handle(BudgetVO obj)
                {
                    String formatNum = MathTools.formatNum(obj.getTotal());

                    obj.setStotal(formatNum);
                }
            });

        return JSONTools.writeResponse(response, jsonstr);
    }

    /**
     * 我的申请
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward querySelfBudgetApply(ActionMapping mapping, ActionForm form,
                                              HttpServletRequest request,
                                              HttpServletResponse response)
        throws ServletException
    {
        ConditionParse condtion = new ConditionParse();

        condtion.addWhereStr();

        User user = Helper.getUser(request);

        condtion.addCondition("BudgetApplyBean.stafferId", "=", user.getStafferId());

        ActionTools.processJSONQueryCondition(QUERYSELFBUDGETAPPLY, request, condtion);

        condtion.addCondition("order by BudgetApplyBean.logTime desc");

        String jsonstr = ActionTools.queryVOByJSONAndToString(QUERYSELFBUDGETAPPLY, request,
            condtion, this.budgetApplyDAO);

        return JSONTools.writeResponse(response, jsonstr);
    }

    /**
     * queryFeeItem
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward queryFeeItem(ActionMapping mapping, ActionForm form,
                                      HttpServletRequest request, HttpServletResponse response)
        throws ServletException
    {
        ConditionParse condtion = new ConditionParse();

        condtion.addWhereStr();

        ActionTools.processJSONQueryCondition(QUERYFEEITEM, request, condtion);

        String jsonstr = ActionTools.queryVOByJSONAndToString(QUERYFEEITEM, request, condtion,
            this.feeItemDAO);

        return JSONTools.writeResponse(response, jsonstr);
    }

    /**
     * preForAddBudget
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward preForAddBudget(ActionMapping mapping, ActionForm form,
                                         HttpServletRequest request, HttpServletResponse response)
        throws ServletException
    {
        CommonTools.saveParamers(request);

        String parentId = request.getParameter("parentId");

        String nextLevelStr = request.getParameter("nextLevel");

        String type = request.getParameter("type");

        BudgetBean parentBean = budgetDAO.find(parentId);

        if (parentBean == null)
        {
            return ActionTools.toError("父预算不存在", QUERYBUDGET, mapping, request);
        }

        // 月度预算
        if ("2".equals(nextLevelStr))
        {
            parentBean = budgetDAO.findVO(parentId);
        }

        parentBean.setItems(budgetItemDAO.queryEntityBeansByFK(parentId));

        request.setAttribute("pbean", parentBean);

        // add department budget
        if ("1".equals(type))
        {
            if (parentBean.getType() == BudgetConstant.BUDGET_TYPE_DEPARTMENT)
            {
                int nextLevel = BudgetHelper.getNextType(parentBean.getLevel());

                if (nextLevel > BudgetConstant.BUDGET_LEVEL_MONTH)
                {
                    return ActionTools.toError("月度预算已经最小,不能再细分", QUERYBUDGET, mapping, request);
                }

                request.setAttribute("nextLevel", nextLevel);
            }
            else
            {
                request.setAttribute("nextLevel", BudgetConstant.BUDGET_LEVEL_YEAR);
            }
        }

        List<FeeItemBean> feeItems = feeItemDAO.listEntityBeans();

        request.setAttribute("feeItems", feeItems);

        request.setAttribute("imp", false);
        
        return mapping.findForward("addBudget");
    }

    /**
     * queryReference(相关,包含使用情况)
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward queryReference(ActionMapping mapping, ActionForm form,
                                        HttpServletRequest request, HttpServletResponse response)
        throws ServletException
    {
        CommonTools.saveParamers(request);

        String parentId = request.getParameter("pid");

        BudgetBean parentBean = budgetDAO.find(parentId);

        if (parentBean == null)
        {
            return ActionTools.toError("父预算不存在", QUERYBUDGET, mapping, request);
        }

        List<BudgetItemVO> items = budgetItemDAO.queryEntityVOsByFK(parentId);

        request.setAttribute("pbean", parentBean);

        boolean isUnit = BudgetHelper.isUnitBudget(parentBean);

        request.setAttribute("unit", isUnit);

        request.setAttribute("items", items);

        // handle item
        for (BudgetItemVO budgetItemBean : items)
        {
            double hasUseed = budgetManager.sumHasUseInEachBudgetItem(budgetItemBean);

            budgetItemBean.setSuseMonery(MathTools.formatNum(hasUseed));

            // 查询父级预算的详细
            if ( !isUnit)
            {
                List<BudgetBean> subBudget = budgetDAO.querySubmitBudgetByParentId(parentId);

                String subDesc = "";

                double total = 0.0d;

                for (BudgetBean budgetBean : subBudget)
                {
                    BudgetItemBean subBudgetItemBean = budgetItemDAO.findByBudgetIdAndFeeItemId(
                        budgetBean.getId(), budgetItemBean.getFeeItemId());

                    if (subBudgetItemBean != null)
                    {

                        subDesc += BudgetHelper.getLinkName(budgetBean);

//                        total += BudgetHelper
//                            .getBudgetItemRealBudget(budgetBean, subBudgetItemBean);
                        total += budgetManager
                        .getBudgetItemRealBudget(budgetBean, subBudgetItemBean);
                    }
                }

                budgetItemBean.setSubDescription(subDesc);

                double last = budgetItemBean.getBudget() - total;

                // 为分配的预算
                budgetItemBean.setSnoAssignMonery(MathTools.formatNum(last));

                // 剩余预算
                budgetItemBean.setSremainMonery(MathTools.formatNum(budgetItemBean.getBudget()
                                                                    - hasUseed));
            }
            else
            {
                budgetItemBean.setSbudget(MathTools
                    .formatNum(budgetItemBean.getBudget() - hasUseed));
            }
        }

        return mapping.findForward("rptQueryReference");
    }

    /**
     * rptQueryCurrentBudget
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward rptQueryCurrentBudget(ActionMapping mapping, ActionForm form,
                                               HttpServletRequest request,
                                               HttpServletResponse response)
        throws ServletException
    {
        List<BudgetVO> currentRunBudgetList = budgetDAO.queryCurrentRunBudget();

        request.setAttribute("currentRunBudgetList", BudgetHelper
            .formatBudgetList(currentRunBudgetList));

        return mapping.findForward("rptQueryCurrentBudget");
    }

    /**
     * rptQueryCurrentBudgetItem
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward rptQueryBudgetItem(ActionMapping mapping, ActionForm form,
                                            HttpServletRequest request, HttpServletResponse response)
        throws ServletException
    {
        String id = request.getParameter("budgetId");

        request.setAttribute("beanList", BudgetHelper.formatBudgetItemList(budgetItemDAO
            .queryEntityVOsByFK(id)));

        return mapping.findForward("rptQueryBudgetItem");
    }

    /**
     * addUser
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward addBudget(ActionMapping mapping, ActionForm form,
                                   HttpServletRequest request, HttpServletResponse response)
        throws ServletException
    {
        User user = Helper.getUser(request);

        String opr = request.getParameter("opr");

        try
        {
            BudgetBean bean = new BudgetBean();

            BeanUtil.getBean(bean, request);

            bean.setItems(getBudgetItem(request));

            bean.setLogTime(TimeTools.now());

            bean.setStafferId(user.getStafferId());

            if (LocationHelper.isVirtualLocation(user.getLocationId()))
            {
                bean.setLocationId(PublicConstant.CENTER_LOCATION);
            }
            else
            {
                bean.setLocationId(user.getLocationId());
            }

            if ("1".equals(opr))
            {
                bean.setStatus(BudgetConstant.BUDGET_STATUS_SUBMIT);
            }

            // 处理月份
            if (BudgetHelper.isUnitBudget(bean))
            {
                String month = request.getParameter("month");

                String begin = bean.getYear() + '-' + month + "-01";

                String end = TimeTools.getMonthEnd(begin);

                bean.setBeginDate(begin);

                bean.setEndDate(end);
            }

            budgetFacade.addBudget(user.getId(), bean);

            request.setAttribute(KeyConstant.MESSAGE, "增加预算成成功");
        }
        catch (MYException e)
        {
            _logger.warn(e, e);

            request.setAttribute(KeyConstant.ERROR_MESSAGE, "增加预算失败:" + e.getMessage());
        }

        return mapping.findForward(QUERYBUDGET);
    }

    /**
     * addBudgetApply
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward addBudgetApply(ActionMapping mapping, ActionForm form,
                                        HttpServletRequest request, HttpServletResponse response)
        throws ServletException
    {
        User user = Helper.getUser(request);

        String applyReason = request.getParameter("applyReason");
        String oprType = request.getParameter("oprType");

        try
        {
            BudgetApplyBean bean = new BudgetApplyBean();

            BeanUtil.getBean(bean, request);

            bean.setDescription(applyReason);

            bean.setType(CommonTools.parseInt(oprType));

            // 子项
            bean.setItems(getBudgetItem(request));

            bean.setLogTime(TimeTools.now());

            bean.setStafferId(user.getStafferId());

            bean.setStatus(BudgetConstant.BUDGET_STATUS_SUBMIT);

            budgetFacade.addBudgetApply(user.getId(), bean);

            request.setAttribute(KeyConstant.MESSAGE, "申请更新预算成功");
        }
        catch (MYException e)
        {
            _logger.warn(e, e);

            request.setAttribute(KeyConstant.ERROR_MESSAGE, "申请更新预算失败:" + e.getMessage());
        }

        return mapping.findForward(QUERYRUNBUDGET);
    }

    /**
     * addFeeItem
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward addOrUpdateFeeItem(ActionMapping mapping, ActionForm form,
                                            HttpServletRequest request, HttpServletResponse response)
        throws ServletException
    {
        User user = Helper.getUser(request);

        try
        {
            FeeItemBean bean = new FeeItemBean();

            BeanUtil.getBean(bean, request);

            if ( !StringTools.isNullOrNone(bean.getId()))
            {
                budgetFacade.updateFeeItem(user.getId(), bean);
            }
            else
            {
                budgetFacade.addFeeItem(user.getId(), bean);
            }

            request.setAttribute(KeyConstant.MESSAGE, "操作预算项成功");
        }
        catch (MYException e)
        {
            _logger.warn(e, e);

            request.setAttribute(KeyConstant.ERROR_MESSAGE, "操作预算失败:" + e.getMessage());
        }

        return mapping.findForward(QUERYFEEITEM);
    }

    /**
     * 构建角色
     * 
     * @param request
     * @param bean
     */
    private List<BudgetItemBean> getBudgetItem(HttpServletRequest request)
    {
        List<BudgetItemBean> items = new ArrayList<BudgetItemBean>();
        User user = Helper.getUser(request);

        String[] names = request.getParameterValues("item_name");
        String[] budgets = request.getParameterValues("item_budget");
        String[] descriptions = request.getParameterValues("item_description");

        // check null
        if (names == null || names.length == 0)
        {
            return items;
        }

        for (int i = 0; i < names.length; i++ )
        {
            if (StringTools.isNullOrNone(names[i]))
            {
                continue;
            }

            BudgetItemBean item = new BudgetItemBean();

            item.setFeeItemId(names[i]);

            item.setBudget(Double.parseDouble(budgets[i]));

            // set useMoney equals budget
            item.setUseMonery(item.getBudget());

            item.setLocationId(user.getLocationId());

            if (descriptions.length > i)
            {
                item.setDescription(descriptions[i]);
            }

            items.add(item);
        }

        return items;
    }

    /**
     * updateBudget
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward updateBudget(ActionMapping mapping, ActionForm form,
                                      HttpServletRequest request, HttpServletResponse response)
        throws ServletException
    {
        User user = Helper.getUser(request);

        String opr = request.getParameter("opr");

        try
        {
            BudgetBean bean = new BudgetBean();

            BeanUtil.getBean(bean, request);

            bean.setItems(getBudgetItem(request));

            bean.setLogTime(TimeTools.now());

            bean.setStafferId(user.getStafferId());

            if (LocationHelper.isVirtualLocation(user.getLocationId()))
            {
                bean.setLocationId(PublicConstant.CENTER_LOCATION);
            }
            else
            {
                bean.setLocationId(user.getLocationId());
            }

            if ("1".equals(opr))
            {
                bean.setStatus(BudgetConstant.BUDGET_STATUS_SUBMIT);
            }

            // 处理月份
            if (BudgetHelper.isUnitBudget(bean))
            {
                String month = request.getParameter("month");

                String begin = bean.getYear() + '-' + month + "-01";

                String end = TimeTools.getMonthEnd(begin);

                bean.setBeginDate(begin);

                bean.setEndDate(end);
            }

            budgetFacade.updateBudget(user.getId(), bean);

            request.setAttribute(KeyConstant.MESSAGE, "修改预算成功");
        }
        catch (MYException e)
        {
            _logger.warn(e, e);

            request.setAttribute(KeyConstant.ERROR_MESSAGE, "修改预算失败:" + e.getMessage());
        }

        return mapping.findForward(QUERYBUDGET);
    }

    /**
     * queryLog
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward queryLog(ActionMapping mapping, ActionForm form,
                                  HttpServletRequest request, HttpServletResponse response)
        throws ServletException
    {
        String id = request.getParameter("id");

        AjaxResult ajax = new AjaxResult();

        List<LogVO> logs = logDAO.queryEntityVOsByFK(id);

        ajax.setSuccess(logs);

        return JSONTools.writeResponse(response, ajax);
    }

    /**
     * delBudget
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward delBudget(ActionMapping mapping, ActionForm form,
                                   HttpServletRequest request, HttpServletResponse response)
        throws ServletException
    {
        AjaxResult ajax = new AjaxResult();

        try
        {
            String id = request.getParameter("id");

            User user = Helper.getUser(request);

            budgetFacade.delBudget(user.getId(), id);

            ajax.setSuccess("成功删除");
        }
        catch (MYException e)
        {
            _logger.warn(e, e);

            ajax.setError("删除失败:" + e.getMessage());
        }

        return JSONTools.writeResponse(response, ajax);
    }

    /**
     * carryBudget
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward carryBudget(ActionMapping mapping, ActionForm form,
                                     HttpServletRequest request, HttpServletResponse response)
        throws ServletException
    {
        AjaxResult ajax = new AjaxResult();

        try
        {
            budgetManager.initCarryStatus();

            ajax.setSuccess("成功执行");
        }
        catch (Exception e)
        {
            _logger.warn(e, e);

            ajax.setError("执行失败:" + e.getMessage());
        }

        return JSONTools.writeResponse(response, ajax);
    }

    /**
     * delFeeItem
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward delFeeItem(ActionMapping mapping, ActionForm form,
                                    HttpServletRequest request, HttpServletResponse response)
        throws ServletException
    {
        AjaxResult ajax = new AjaxResult();

        try
        {
            String id = request.getParameter("id");

            User user = Helper.getUser(request);

            budgetFacade.deleteFeeItem(user.getId(), id);

            ajax.setSuccess("成功删除预算项");
        }
        catch (MYException e)
        {
            _logger.warn(e, e);

            ajax.setError("删除预算项失败:" + e.getMessage());
        }

        return JSONTools.writeResponse(response, ajax);
    }

    /**
     * findUser
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward findBudget(ActionMapping mapping, ActionForm form,
                                    HttpServletRequest request, HttpServletResponse response)
        throws ServletException
    {
        CommonTools.saveParamers(request);

        String id = request.getParameter("id");

        User user = Helper.getUser(request);

        String update = request.getParameter("update");

        BudgetVO bean = null;
        try
        {
            bean = budgetManager.findBudgetVO(id);

            if (bean == null)
            {
                request.setAttribute(KeyConstant.ERROR_MESSAGE, "预算不存在");

                return mapping.findForward(QUERYBUDGET);
            }

            request.setAttribute("bean", bean);

            List<LogVO> logs = logDAO.queryEntityVOsByFK(id);

            request.setAttribute("logs", logs);

            boolean isUnit = BudgetHelper.isUnitBudget(bean);

            request.setAttribute("unit", isUnit);

            if (isUnit)
            {
                request.setAttribute("month", bean.getBeginDate().substring(5, 7));
            }
        }
        catch (MYException e)
        {
            _logger.warn(e, e);

            request.setAttribute(KeyConstant.ERROR_MESSAGE, "查询失败:" + e.getMessage());

            return mapping.findForward(QUERYBUDGET);
        }

        BudgetBean parentBean = budgetDAO.find(bean.getParentId());

        if (parentBean == null)
        {
            return ActionTools.toError("父预算不存在", QUERYBUDGET, mapping, request);
        }

        request.setAttribute("pbean", parentBean);

        List<FeeItemBean> feeItems = feeItemDAO.listEntityBeans();

        request.setAttribute("feeItems", feeItems);

        // whether apply
        if ("2".equals(update) || "3".equals(update))
        {
            try
            {
                budgetApplyManager.whetherApply(id);
            }
            catch (MYException e)
            {
                request.setAttribute(KeyConstant.ERROR_MESSAGE, e.getMessage());

                return mapping.findForward(QUERYRUNBUDGET);
            }
        }

        // 只有自己的且在初始和驳回的可以修改
        if ("1".equals(update)
            && user.getStafferId().equals(bean.getStafferId())
            && (bean.getStatus() == BudgetConstant.BUDGET_STATUS_INIT || bean.getStatus() == BudgetConstant.BUDGET_STATUS_REJECT))
        {
            return mapping.findForward("updateBudget");
        }
        // forward 变更
        else if ("2".equals(update) && bean.getStatus() == BudgetConstant.BUDGET_STATUS_PASS)
        {
            return mapping.findForward("updateBudget2");
        }
        // forward 追加
        else if ("3".equals(update) && bean.getStatus() == BudgetConstant.BUDGET_STATUS_PASS)
        {
            return mapping.findForward("updateBudget3");
        }
        else
        {
            return mapping.findForward("detailBudget");
        }
    }

    /**
     * findBudgetApply
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward findBudgetApply(ActionMapping mapping, ActionForm form,
                                         HttpServletRequest request, HttpServletResponse response)
        throws ServletException
    {
        String id = request.getParameter("id");

        BudgetApplyBean bean = null;

        BudgetVO vo = null;

        try
        {
            bean = budgetApplyDAO.find(id);

            if (bean == null)
            {
                request.setAttribute(KeyConstant.ERROR_MESSAGE, "预算申请不存在");

                return mapping.findForward(QUERYBUDGET);
            }

            vo = budgetManager.findBudgetVO(bean.getBudgetId());

            if (vo == null)
            {
                request.setAttribute(KeyConstant.ERROR_MESSAGE, "预算不存在");

                return mapping.findForward(QUERYBUDGET);
            }

            PrincipalshipBean org = orgManager.findPrincipalshipById(vo.getBudgetDepartment());

            if (org != null)
            {
                vo.setBudgetFullDepartmentName(org.getFullName());
            }

            request.setAttribute("bean", vo);

            request.setAttribute("applyBean", bean);
        }
        catch (Exception e)
        {
            _logger.warn(e, e);

            request.setAttribute(KeyConstant.ERROR_MESSAGE, "查询失败:" + e.getMessage());

            return mapping.findForward(QUERYBUDGET);
        }

        BudgetBean parentBean = budgetDAO.find(vo.getParentId());

        if (parentBean == null)
        {
            return ActionTools.toError("父预算不存在", QUERYBUDGET, mapping, request);
        }

        request.setAttribute("pbean", parentBean);

        List<FeeItemBean> feeItems = feeItemDAO.listEntityBeans();

        request.setAttribute("feeItems", feeItems);

        // 变更后的
        List<BudgetItemBean> items = budgetItemDAO.queryEntityBeansByFK(id);

        List<BudgetItemVO> items1 = new ArrayList();

        for (BudgetItemBean budgetItemBean : items)
        {
            BudgetItemVO vv = new BudgetItemVO();

            BeanUtil.copyProperties(vv, budgetItemBean);

            items1.add(vv);
        }

        List<BudgetItemVO> itemVOs = vo.getItemVOs();

        // format
        for (Iterator iterator = items1.iterator(); iterator.hasNext();)
        {
            BudgetItemVO budgetItemVO = (BudgetItemVO)iterator.next();

            BudgetHelper.formatBudgetItem(budgetItemVO);

            for (BudgetItemVO each : itemVOs)
            {
                BudgetHelper.formatBudgetItem(each);

                if (each.getFeeItemId().equals(budgetItemVO.getFeeItemId()))
                {
                    each.setSchangeMonery(budgetItemVO.getSbudget());

                    budgetItemVO.setFeeItemName(each.getFeeItemName());

                    break;
                }
            }
        }

        request.setAttribute("items", items1);

        return mapping.findForward("detailBudgetApply");
    }

    /**
     * findFeeItem
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward findFeeItem(ActionMapping mapping, ActionForm form,
                                     HttpServletRequest request, HttpServletResponse response)
        throws ServletException
    {
        String id = request.getParameter("id");

        FeeItemBean bean = null;
        try
        {
            bean = feeItemDAO.findVO(id);

            if (bean == null)
            {
                request.setAttribute(KeyConstant.ERROR_MESSAGE, "预算项不存在");

                return mapping.findForward(QUERYFEEITEM);
            }

            request.setAttribute("bean", bean);
        }
        catch (Exception e)
        {
            _logger.warn(e, e);

            request.setAttribute(KeyConstant.ERROR_MESSAGE, "查询失败:" + e.getMessage());

            return mapping.findForward(QUERYBUDGET);
        }

        return mapping.findForward("updateFeeItem");
    }

    /**
     * findBudgetLog
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward findBudgetLog(ActionMapping mapping, ActionForm form,
                                       HttpServletRequest request, HttpServletResponse response)
        throws ServletException
    {
        String id = request.getParameter("id");

        BudgetLogVO bean = budgetLogDAO.findVO(id);

        if (bean == null)
        {
            ActionTools.toError(mapping, request);
        }

        // 加工URL
        String billIds = bean.getBillIds();

        if ( !StringTools.isNullOrNone(bean.getBillId()))
        {
            if (billIds.indexOf(bean.getBillId()) == -1)
            {
                billIds += bean.getBillId();
            }
        }

        String[] billList = billIds.split(";");

        request.setAttribute("billList", billList);

        request.setAttribute("bean", bean);

        return mapping.findForward("detailBudgetLog");
    }

    /**
     * 审核预算
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward auditingBudget(ActionMapping mapping, ActionForm form,
                                        HttpServletRequest request, HttpServletResponse response)
        throws ServletException
    {
        String id = request.getParameter("id");

        User user = Helper.getUser(request);

        String opr = request.getParameter("opr");

        String reason = request.getParameter("reason");

        AjaxResult ajax = new AjaxResult();

        try
        {
            if ("0".equals(opr))
            {
                budgetFacade.passBudget(user.getId(), id);
            }

            if ("1".equals(opr))
            {
                budgetFacade.rejectBudget(user.getId(), id, reason);
            }

            ajax.setSuccess("审批预算成功");
        }
        catch (MYException e)
        {
            _logger.warn(e, e);

            ajax.setError("审批预算失败:" + e.getMessage());
        }

        return JSONTools.writeResponse(response, ajax);
    }

    /**
     * auditingBudgetApply(审批预算变更)
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward auditingBudgetApply(ActionMapping mapping, ActionForm form,
                                             HttpServletRequest request,
                                             HttpServletResponse response)
        throws ServletException
    {
        String id = request.getParameter("id");

        User user = Helper.getUser(request);

        String opr = request.getParameter("opr");

        String mode = request.getParameter("mode");

        String reason = request.getParameter("reason");

        AjaxResult ajax = new AjaxResult();

        try
        {
            if ("0".equals(opr))
            {
                budgetFacade.passBudgetApply(user.getId(), mode, id);
            }

            if ("1".equals(opr))
            {
                budgetFacade.rejectBudgetApply(user.getId(), mode, id, reason);
            }

            ajax.setSuccess("核准预算变更成功");
        }
        catch (MYException e)
        {
            _logger.warn(e, e);

            ajax.setError("核准预算变更失败:" + e.getMessage());
        }

        return JSONTools.writeResponse(response, ajax);
    }

    /**
     * 组织VO
     * 
     * @param obj
     */
    private void warpBudgetVO(BudgetVO obj)
    {
        BudgetHelper.formatBudgetVO(obj);

        PrincipalshipBean org = orgManager.findPrincipalshipById(obj.getBudgetDepartment());

        if (org != null)
        {
            obj.setBudgetFullDepartmentName(org.getFullName());
        }

        double hasUsed = budgetManager.sumHasUseInEachBudget(obj);

        obj.setSrealMonery(MathTools.formatNum(hasUsed));
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
    public ActionForward importBudget(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
    throws ServletException
    {
        RequestDataStream rds = new RequestDataStream(request);
        
        boolean importError = false;        
        
        List<BudgetItemBean> importItemList = new ArrayList<BudgetItemBean>(); 
        
        StringBuilder builder = new StringBuilder();       
        
        try
        {
            rds.parser();
        }
        catch (Exception e1)
        {
            _logger.error(e1, e1);

            request.setAttribute(KeyConstant.ERROR_MESSAGE, "解析失败");

            return mapping.findForward("importBudget");
        }

        if ( !rds.haveStream())
        {
            request.setAttribute(KeyConstant.ERROR_MESSAGE, "解析失败");

            return mapping.findForward("importBudget");
        }
        
        String parentId = rds.getParameter("parentId");

        String nextLevelStr = rds.getParameter("nextLevel");

        String type = rds.getParameter("type");
        
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
                    BudgetItemBean item = new BudgetItemBean();
                    
                    // 预算项
                    if ( !StringTools.isNullOrNone(obj[0]))
                    {
                        String name = obj[0];
                        
                        FeeItemBean bean = feeItemDAO.findByUnique(name);
                        
                        if (bean == null)
                        {
                            builder
                                .append("<font color=red>第[" + currentNumber + "]行错误:")
                                .append("预算项不存在")
                                .append("</font><br>");

                            importError = true;
                        }else
                        {
                        	item.setFeeItemId(bean.getId());
                        }
                    }
                    
                    // 预算金额 导入时只确保是float
                    if ( !StringTools.isNullOrNone(obj[1]))
                    {
                        String name = obj[1];

                        double  budget = MathTools.parseDouble(name);

                        item.setBudget(budget);

                    }
                    
                    // 描述
                    if ( !StringTools.isNullOrNone(obj[2]))
                    {
                        String name = obj[2];
                        
                        item.setDescription(name);

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

            return mapping.findForward("importBudget");
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

            return mapping.findForward("importBudget");
        }
        
        request.setAttribute("imp", true);
        
        request.setAttribute("importItemLists", importItemList);

        BudgetBean parentBean = budgetDAO.find(parentId);

        if (parentBean == null)
        {

            request.setAttribute(KeyConstant.ERROR_MESSAGE, "父预算不存在");
            
            return mapping.findForward("importBudget");
                        
        }

        // 月度预算
        if ("2".equals(nextLevelStr))
        {
            parentBean = budgetDAO.findVO(parentId);
        }

        parentBean.setItems(budgetItemDAO.queryEntityBeansByFK(parentId));

        request.setAttribute("pbean", parentBean);

        List<FeeItemBean> feeItems = feeItemDAO.listEntityBeans();

        request.setAttribute("feeItems", feeItems);
        
        request.setAttribute("parentId", parentId);
        
        request.setAttribute("nextLevel", nextLevelStr);
        
        request.setAttribute("type", type);
        
        return mapping.findForward("addBudget");
        
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
     * @return the userManager
     */
    public UserManager getUserManager()
    {
        return userManager;
    }

    /**
     * @param userManager
     *            the userManager to set
     */
    public void setUserManager(UserManager userManager)
    {
        this.userManager = userManager;
    }

    /**
     * @return the budgetManager
     */
    public BudgetManager getBudgetManager()
    {
        return budgetManager;
    }

    /**
     * @param budgetManager
     *            the budgetManager to set
     */
    public void setBudgetManager(BudgetManager budgetManager)
    {
        this.budgetManager = budgetManager;
    }

    /**
     * @return the budgetFacade
     */
    public BudgetFacade getBudgetFacade()
    {
        return budgetFacade;
    }

    /**
     * @param budgetFacade
     *            the budgetFacade to set
     */
    public void setBudgetFacade(BudgetFacade budgetFacade)
    {
        this.budgetFacade = budgetFacade;
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
     * @return the logDAO
     */
    public LogDAO getLogDAO()
    {
        return logDAO;
    }

    /**
     * @param logDAO
     *            the logDAO to set
     */
    public void setLogDAO(LogDAO logDAO)
    {
        this.logDAO = logDAO;
    }

    /**
     * @return the budgetApplyDAO
     */
    public BudgetApplyDAO getBudgetApplyDAO()
    {
        return budgetApplyDAO;
    }

    /**
     * @param budgetApplyDAO
     *            the budgetApplyDAO to set
     */
    public void setBudgetApplyDAO(BudgetApplyDAO budgetApplyDAO)
    {
        this.budgetApplyDAO = budgetApplyDAO;
    }

    /**
     * @return the budgetApplyManager
     */
    public BudgetApplyManager getBudgetApplyManager()
    {
        return budgetApplyManager;
    }

    /**
     * @param budgetApplyManager
     *            the budgetApplyManager to set
     */
    public void setBudgetApplyManager(BudgetApplyManager budgetApplyManager)
    {
        this.budgetApplyManager = budgetApplyManager;
    }

    /**
     * @return the budgetLogDAO
     */
    public BudgetLogDAO getBudgetLogDAO()
    {
        return budgetLogDAO;
    }

    /**
     * @param budgetLogDAO
     *            the budgetLogDAO to set
     */
    public void setBudgetLogDAO(BudgetLogDAO budgetLogDAO)
    {
        this.budgetLogDAO = budgetLogDAO;
    }

    /**
     * @return the orgManager
     */
    public OrgManager getOrgManager()
    {
        return orgManager;
    }

    /**
     * @param orgManager
     *            the orgManager to set
     */
    public void setOrgManager(OrgManager orgManager)
    {
        this.orgManager = orgManager;
    }

}
