/**
 * File Name: ParentQueryFinaAction.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2011-8-7<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.tax.action;


import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

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
import com.china.center.actionhelper.common.KeyConstant;
import com.china.center.actionhelper.common.OldPageSeparateTools;
import com.china.center.actionhelper.common.PageSeparateTools;
import com.china.center.common.MYException;
import com.china.center.jdbc.util.ConditionParse;
import com.china.center.jdbc.util.PageSeparate;
import com.china.center.oa.product.bean.DepotBean;
import com.china.center.oa.product.bean.ProductBean;
import com.china.center.oa.product.dao.DepotDAO;
import com.china.center.oa.product.dao.ProductDAO;
import com.china.center.oa.publics.Helper;
import com.china.center.oa.publics.bean.DutyBean;
import com.china.center.oa.publics.bean.PrincipalshipBean;
import com.china.center.oa.publics.bean.StafferBean;
import com.china.center.oa.publics.constant.SysConfigConstant;
import com.china.center.oa.publics.dao.DutyDAO;
import com.china.center.oa.publics.dao.ParameterDAO;
import com.china.center.oa.publics.dao.PrincipalshipDAO;
import com.china.center.oa.publics.dao.StafferDAO;
import com.china.center.oa.publics.manager.OrgManager;
import com.china.center.oa.sail.bean.UnitViewBean;
import com.china.center.oa.sail.dao.UnitViewDAO;
import com.china.center.oa.sail.manager.OutManager;
import com.china.center.oa.tax.bean.FinanceBean;
import com.china.center.oa.tax.bean.FinanceItemBean;
import com.china.center.oa.tax.bean.FinanceMonthBean;
import com.china.center.oa.tax.bean.FinanceMonthBefBean;
import com.china.center.oa.tax.bean.FinanceRefer;
import com.china.center.oa.tax.bean.TaxBean;
import com.china.center.oa.tax.bean.UnitBean;
import com.china.center.oa.tax.constanst.FinaConstant;
import com.china.center.oa.tax.constanst.TaxConstanst;
import com.china.center.oa.tax.dao.CheckViewDAO;
import com.china.center.oa.tax.dao.FinanceDAO;
import com.china.center.oa.tax.dao.FinanceItemDAO;
import com.china.center.oa.tax.dao.FinanceItemTempDAO;
import com.china.center.oa.tax.dao.FinanceMonthBefDAO;
import com.china.center.oa.tax.dao.FinanceMonthDAO;
import com.china.center.oa.tax.dao.FinanceReferDAO;
import com.china.center.oa.tax.dao.FinanceRepDAO;
import com.china.center.oa.tax.dao.FinanceShowDAO;
import com.china.center.oa.tax.dao.FinanceTagDAO;
import com.china.center.oa.tax.dao.FinanceTempDAO;
import com.china.center.oa.tax.dao.FinanceTurnDAO;
import com.china.center.oa.tax.dao.TaxDAO;
import com.china.center.oa.tax.dao.UnitDAO;
import com.china.center.oa.tax.facade.TaxFacade;
import com.china.center.oa.tax.helper.FinanceHelper;
import com.china.center.oa.tax.helper.TaxHelper;
import com.china.center.oa.tax.manager.FinanceManager;
import com.china.center.oa.tax.vo.FinanceItemVO;
import com.china.center.oa.tax.vo.FinanceRepVO;
import com.china.center.oa.tax.vo.FinanceShowVO;
import com.china.center.oa.tax.vo.StafferUnitVO;
import com.china.center.oa.tax.vo.TaxVO;
import com.china.center.osgi.jsp.ElTools;
import com.china.center.tools.BeanUtil;
import com.china.center.tools.CommonTools;
import com.china.center.tools.MathTools;
import com.china.center.tools.RequestDataStream;
import com.china.center.tools.SequenceTools;
import com.china.center.tools.StringTools;
import com.china.center.tools.TimeTools;
import com.china.center.tools.WriteFileBuffer;


/**
 * ParentQueryFinaAction
 * 
 * @author ZHUZHU
 * @version 2011-8-7
 * @see ParentQueryFinaAction
 * @since 3.0
 */
public class ParentQueryFinaAction extends DispatchAction
{
    protected final Log _logger = LogFactory.getLog(getClass());

    protected TaxFacade taxFacade = null;

    protected TaxDAO taxDAO = null;

    protected DutyDAO dutyDAO = null;

    protected UnitDAO unitDAO = null;

    protected OutManager outManager = null;

    protected FinanceManager financeManager = null;

    protected PrincipalshipDAO principalshipDAO = null;

    protected StafferDAO stafferDAO = null;

    protected FinanceDAO financeDAO = null;

    protected UnitViewDAO unitViewDAO = null;

    protected CheckViewDAO checkViewDAO = null;

    protected FinanceItemDAO financeItemDAO = null;

    protected DepotDAO depotDAO = null;

    protected ProductDAO productDAO = null;

    protected OrgManager orgManager = null;

    protected FinanceTurnDAO financeTurnDAO = null;

    protected FinanceMonthDAO financeMonthDAO = null;

    protected ParameterDAO parameterDAO = null;

    protected FinanceRepDAO financeRepDAO = null;

    protected FinanceTempDAO financeTempDAO = null;

    protected FinanceItemTempDAO financeItemTempDAO = null;

    protected FinanceReferDAO financeReferDAO = null;
    
    protected FinanceMonthBefDAO financeMonthBefDAO = null;    
    
    protected FinanceTagDAO financeTagDAO = null;
    
    protected FinanceShowDAO financeShowDAO = null;

    protected static final String QUERYFINANCE = "queryFinance";

    protected static final String QUERYTEMPFINANCE = "queryTempFinance";

    protected static final String QUERYFINANCEMONTH = "queryFinanceMonth";

    protected static final String QUERYFINANCETURN = "queryFinanceTurn";

    protected static final String QUERYFINANCEITEM = "queryFinanceItem";

    protected static final String QUERYCHECKVIEW = "queryCheckView";

    protected static final String QUERYTAXFINANCE1 = "queryTaxFinance1";

    protected static final String QUERYTAXFINANCE2 = "queryTaxFinance2";

    protected static final String QUERYTAXFINANCE3 = "queryTaxFinance3";

    protected static final String QUERYTAXREPORT = "queryTaxReport";

    protected static String RPTQUERYUNIT = "rptQueryUnit";
    
    protected static final String QUERYFINANCETAG = "queryFinanceTag";

    /**
     * 分类账查询
     * 
     * @param mapping
     * @param form
     * @param request
     * @param reponse
     * @return
     * @throws ServletException
     */
    public ActionForward queryTaxFinance1(ActionMapping mapping, ActionForm form,
                                          HttpServletRequest request, HttpServletResponse reponse)
        throws ServletException
    {
        User user = Helper.getUser(request);

        request.getSession().setAttribute("EXPORT_FINANCEITE_KEY", QUERYTAXFINANCE1);

        List<FinanceItemVO> list = null;

        CommonTools.saveParamers(request);

        FinanceItemVO head = null;
        FinanceItemVO currentTotal = null;
        FinanceItemVO allTotal = null;

        try
        {
            if (PageSeparateTools.isFirstLoad(request))
            {
                ConditionParse condtion = getQueryCondition(request, user, 0);

                int tatol = financeItemDAO.countVOByCondition(condtion.toString());

                PageSeparate page = new PageSeparate(tatol, 50);

                OldPageSeparateTools.initPageSeparate(condtion, page, request, QUERYTAXFINANCE1);

                list = financeItemDAO.queryEntityVOsByCondition(condtion, page);

                // 结转
                head = sumHead(request, user);

                // 当期合计
                currentTotal = sumCurrentTotal(request, user, condtion);

                // 当前累计
                allTotal = sumAllTotal(request, user);
            }
            else
            {
                PageSeparateTools.processSeparate(request, QUERYTAXFINANCE1);

                list = financeItemDAO.queryEntityVOsByCondition(OldPageSeparateTools.getCondition(
                    request, QUERYTAXFINANCE1), OldPageSeparateTools.getPageSeparate(request,
                    QUERYTAXFINANCE1));

                head = (FinanceItemVO)request.getSession().getAttribute("queryTaxFinance1_head");

                currentTotal = (FinanceItemVO)request.getSession().getAttribute(
                    "queryTaxFinance1_currentTotal");

                allTotal = (FinanceItemVO)request.getSession().getAttribute(
                    "queryTaxFinance1_allTotal");
            }
        }
        catch (Exception e)
        {
            request.setAttribute(KeyConstant.ERROR_MESSAGE, "查询失败");

            _logger.error(e, e);

            return mapping.findForward("error");
        }

        String queryType = request.getParameter("queryType");

        // 明细
        if ("0".equals(queryType))
        {
            // 这里的分类账有个特点就是余额需要递增
            PageSeparate pageSeparate = OldPageSeparateTools.getPageSeparate(request,
                QUERYTAXFINANCE1);

            TaxBean tax = (TaxBean)request.getSession().getAttribute("queryTaxFinance1_tax");

            long ptotal = 0L;

            // 当进入翻页的时候,开始计算上页的期末余额
            if (pageSeparate.getNowPage() > 1)
            {
                PageSeparate newPage = new PageSeparate();
                newPage.setRowCount( (pageSeparate.getNowPage() - 1) * pageSeparate.getPageSize());
                newPage.setPageSize(newPage.getRowCount());

                long[] sumVOn = financeItemDAO.sumVOMoneryByCondition(OldPageSeparateTools
                    .getCondition(request, QUERYTAXFINANCE1), newPage);

                // 余额
                ptotal = TaxHelper.getLastMoney(tax, sumVOn);
            }

            // 合计出本次展现的开始金额
            long lastmoney = head.getLastmoney() + ptotal;

            long oldLastmoney = lastmoney;

            for (FinanceItemVO financeItemVO : list)
            {
                fillItemVO(financeItemVO);

                lastmoney = financeItemVO.getLastmoney() + lastmoney;

                financeItemVO.setLastmoney(lastmoney);

                financeItemVO.setShowLastmoney(FinanceHelper.longToString(lastmoney));
            }

            if (pageSeparate.getNowPage() <= 1)
            {
                // 放入合计统计
                list.add(0, head);
            }
            else
            {
                FinanceItemVO newHead = new FinanceItemVO();

                BeanUtil.copyProperties(newHead, head);

                newHead.setLastmoney(oldLastmoney);

                newHead.setDescription("转上页累计余额");

                newHead.setShowLastmoney(FinanceHelper.longToString(oldLastmoney));

                // 放入合计统计
                list.add(0, newHead);
            }
        }
        else
        {
            // 总帐
            list.clear();

            // 放入合计统计
            list.add(0, head);
        }

        list.add(currentTotal);

        list.add(allTotal);

        request.setAttribute("resultList", list);

        return mapping.findForward(QUERYTAXFINANCE1);
    }

    /**
     * 三大费用
     * 
     * @param mapping
     * @param form
     * @param request
     * @param reponse
     * @return
     * @throws ServletException
     */
    public ActionForward queryTaxFinance3(ActionMapping mapping, ActionForm form,
                                          HttpServletRequest request, HttpServletResponse reponse)
        throws ServletException
    {
        User user = Helper.getUser(request);

        request.getSession().setAttribute("EXPORT_FINANCEITE_KEY", QUERYTAXFINANCE3);

        List<FinanceItemVO> list = null;

        CommonTools.saveParamers(request);

        FinanceItemVO currentTotal = null;

        String parentTax = request.getParameter("parentTax");

        try
        {
            if (PageSeparateTools.isFirstLoad(request))
            {
                ConditionParse condtion = getQueryCondition3(request, user, 0, parentTax);

                int tatol = financeItemDAO.countVOByCondition(condtion.toString());

                PageSeparate page = new PageSeparate(tatol, 50);

                OldPageSeparateTools.initPageSeparate(condtion, page, request, QUERYTAXFINANCE3);

                list = financeItemDAO.queryEntityVOsByCondition(condtion, page);

                // 当期合计
                currentTotal = sumCurrentTotal(request, user, condtion);
            }
            else
            {
                PageSeparateTools.processSeparate(request, QUERYTAXFINANCE3);

                list = financeItemDAO.queryEntityVOsByCondition(OldPageSeparateTools.getCondition(
                    request, QUERYTAXFINANCE3), OldPageSeparateTools.getPageSeparate(request,
                    QUERYTAXFINANCE3));

                currentTotal = (FinanceItemVO)request.getSession().getAttribute(
                    "queryTaxFinance1_currentTotal");
            }
        }
        catch (Exception e)
        {
            request.setAttribute(KeyConstant.ERROR_MESSAGE, "查询失败");

            _logger.error(e, e);

            return mapping.findForward("error");
        }

        for (FinanceItemVO financeItemVO : list)
        {
            fillItemVO(financeItemVO);
        }

        list.add(currentTotal);

        request.setAttribute("resultList", list);

        return mapping.findForward(QUERYTAXFINANCE3);
    }

    /**
     * 报表
     * 
     * @param mapping
     * @param form
     * @param request
     * @param reponse
     * @return
     * @throws ServletException
     */
    public ActionForward queryTaxReport(ActionMapping mapping, ActionForm form,
                                        HttpServletRequest request, HttpServletResponse reponse)
        throws ServletException
    {
        request.getSession().setAttribute("EXPORT_FINANCEITE_KEY", QUERYTAXREPORT);

        CommonTools.saveParamers(request);

        String type = request.getParameter("type");

        String year = request.getParameter("year");

        String month = request.getParameter("month");

        String beginTimeKey = (MathTools.parseInt(year) - 1) + "02";

        String endTimeKey = year + month;

        try
        {
            List<FinanceRepVO> repList = financeRepDAO.queryEntityVOsByFK(type);

            Collections.sort(repList, new Comparator<FinanceRepVO>()
            {
                public int compare(FinanceRepVO o1, FinanceRepVO o2)
                {
                    return o1.getItemIndex() - o2.getItemIndex();
                }
            });

            ConditionParse condition = new ConditionParse();
            
            // 资产负债表
            if ("0".equals(type))
            {
                for (FinanceRepVO financeRepVO : repList)
                {
                    long beginTotal = 0L;

                    long endTotal = 0L;

                    // 允许为空
                    if (StringTools.isNullOrNone(financeRepVO.getExpr()))
                    {
                        financeRepVO.setBeginMoney(beginTotal);

                        financeRepVO.setBeginMoneyStr(FinanceHelper.longToString(beginTotal));

                        financeRepVO.setEndMoney(endTotal);

                        financeRepVO.setEndMoneyStr(FinanceHelper.longToString(endTotal));

                        continue;
                    }

                    // 表达式
                    String expr = financeRepVO.getExpr();

                    List<String>[] parserExpr = FinanceHelper.parserExpr(expr);

                    List<String> taxList = parserExpr[0];

                    List<String> oprList = parserExpr[1];

                    for (int i = 0; i < taxList.size(); i++ )
                    {
                        FinanceMonthBean beginTurn = financeMonthDAO.findByUnique(taxList.get(i),
                            beginTimeKey);

                        FinanceMonthBean endTurn = financeMonthDAO.findByUnique(taxList.get(i),
                            endTimeKey);
                        
                        // 第一个为初始化
                        if (i == 0)
                        {
                            if (beginTurn != null)
                            {
                                beginTotal = beginTurn.getLastAllTotal();
                            }

                            if (endTurn != null)
                            {
                                endTotal = endTurn.getLastAllTotal();
                            }

                            continue;
                        }

                        //防止list空指针
                        if (taxList.size() > 1){
                            // 需要操作符号
                            if (i < taxList.size())
                            {
                                String opr = oprList.get(i - 1);
    
                                if ("+".equals(opr))
                                {
                                    if (beginTurn != null)
                                    {
                                        beginTotal += beginTurn.getLastAllTotal();
                                    }
    
                                    if (endTurn != null)
                                    {
                                        endTotal += endTurn.getLastAllTotal();
                                    }
                                }
    
                                if ("-".equals(opr))
                                {
                                    if (beginTurn != null)
                                    {
                                        beginTotal -= beginTurn.getLastAllTotal();
                                    }
    
                                    if (endTurn != null)
                                    {
                                        endTotal -= endTurn.getLastAllTotal();
                                    }
                                }
                            }
                        }
                    }

                    financeRepVO.setBeginMoney(beginTotal);

                    financeRepVO.setBeginMoneyStr(FinanceHelper.longToString(beginTotal));

                    financeRepVO.setEndMoney(endTotal);

                    financeRepVO.setEndMoneyStr(FinanceHelper.longToString(endTotal));
                }
                
                // 将资产负债表补录的数据统计出来。条件是数据时间小于endTimeKey
                condition.clear();
                
                condition.addWhereStr();
                
                condition.addIntCondition("FinanceMonthBefBean.type", "=", 0);
                
                condition.addCondition("FinanceMonthBefBean.monthkey", "<=", endTimeKey);
                
                List<FinanceMonthBefBean> fmbbList = financeMonthBefDAO.queryEntityBeansByCondition(condition);
                
                for (FinanceRepVO financeRepVO : repList)
                {
                    
                    for (FinanceMonthBefBean fmbb : fmbbList)
                    {
                        
                        if (financeRepVO.getType() == fmbb.getType() && financeRepVO.getItemIndex() == fmbb.getItemIndex() )
                        {
                            
                            Long lastFinaMonth = fmbb.getMoney();
                            
                            financeRepVO.setEndMoney(financeRepVO.getEndMoney() + lastFinaMonth);

                            financeRepVO.setEndMoneyStr(FinanceHelper.longToString(financeRepVO.getEndMoney()));
                        }
                        
                    }
                    
                }
            }
            else
            {
                
                String beginTimeKey1 = year + "03";
                // 损益报表
                for (FinanceRepVO financeRepVO : repList)
                {
                    long beginTotal = 0L;

                    long endTotal = 0L;

                    // 允许为空
                    if (StringTools.isNullOrNone(financeRepVO.getExpr()))
                    {
                        financeRepVO.setBeginMoney(beginTotal);

                        financeRepVO.setBeginMoneyStr(FinanceHelper.longToString(beginTotal));

                        financeRepVO.setEndMoney(endTotal);

                        financeRepVO.setEndMoneyStr(FinanceHelper.longToString(endTotal));

                        continue;
                    }

                    // 表达式
                    String expr = financeRepVO.getExpr();

                    List<String>[] parserExpr = FinanceHelper.parserExpr(expr);

                    List<String> taxList = parserExpr[0];

                    List<String> oprList = parserExpr[1];

                    if (MathTools.parseInt(month)>=1 && MathTools.parseInt(month)<=2)
                        beginTimeKey1 = (MathTools.parseInt(year) - 1) + "03";
                    
                    for (int i = 0; i < taxList.size(); i++ )
                    {
                        TaxBean taxBean = taxDAO.find(taxList.get(i));
                        
                        String level = "";
                        
                        if (taxBean.getBottomFlag() == TaxConstanst.TAX_BOTTOMFLAG_ITEM){
                            
                            level = "";
                        }else
                        {
                            level = ""+taxBean.getLevel();
                        }
                        
//                        FinanceMonthBean curTurn = financeMonthDAO.findByUnique(taxList.get(i),
//                            endTimeKey);
                        
                        long sumCurMonthTurnTotal = financeMonthDAO.sumCurMonthTurnTotal(taxList.get(i), level, endTimeKey);

                        // 当年的累计数(1月到N月)
//                        long sumMonthTurnTotal = financeMonthDAO.sumMonthTurnTotal(taxList.get(i),
//                                beginTimeKey1, endTimeKey);
                        
                        long sumAllMonthTurnTotal = financeMonthDAO.sumAllMonthTurnTotal(taxList.get(i), level, beginTimeKey1, endTimeKey);

                        // 第一个为初始化
                        if (i == 0)
                        {
//                            if (curTurn != null)
//                            {
//                                // 当月发生额
////                                beginTotal = curTurn.getInmoneyTotal();
//                                beginTotal = curTurn.getMonthTurnTotal();
//                            }

                            // 累计数
                            beginTotal = sumCurMonthTurnTotal;
                            endTotal = sumAllMonthTurnTotal;

                            continue;
                        }

                        //防止list空指针
                        if (taxList.size() > 1){
                            // 需要操作符号
                            if (i < taxList.size())
                            {
                                String opr = oprList.get(i - 1);
    
                                if ("+".equals(opr))
                                {
//                                    if (curTurn != null)
//                                    {
//    //                                    beginTotal += curTurn.getInmoneyTotal();
//                                        beginTotal += curTurn.getMonthTurnTotal();
//                                    }
                                    beginTotal += sumCurMonthTurnTotal;
                                    endTotal += sumAllMonthTurnTotal;
                                }
    
                                if ("-".equals(opr))
                                {
//                                    if (curTurn != null)
//                                    {
//    //                                    beginTotal -= curTurn.getInmoneyTotal();
//                                        beginTotal -= curTurn.getMonthTurnTotal();
//                                    }
                                    
                                    beginTotal -= sumCurMonthTurnTotal;
                                    endTotal -= sumAllMonthTurnTotal;
                                }
                            }
                        }
                    }

                    financeRepVO.setBeginMoney(beginTotal);

                    financeRepVO.setBeginMoneyStr(FinanceHelper.longToString(beginTotal));

                    financeRepVO.setEndMoney(endTotal);

                    financeRepVO.setEndMoneyStr(FinanceHelper.longToString(endTotal));
                }

                // 当查询财务上线的损溢报表时，将补录的财务月结数据取出,做为本年累计。是否要在OA中查询上线前的数据?
                condition.clear();
                
                condition.addWhereStr();
                
                condition.addIntCondition("FinanceMonthBefBean.type", "=", 1);
                
                condition.addCondition("FinanceMonthBefBean.monthkey", ">=", beginTimeKey1);

                condition.addCondition("FinanceMonthBefBean.monthkey", "<=", endTimeKey);
                
                List<FinanceMonthBefBean> fmbbList = financeMonthBefDAO.queryEntityBeansByCondition(condition);
                
                for (FinanceRepVO financeRepVO : repList)
                {
                    
                    for (FinanceMonthBefBean fmbb : fmbbList)
                    {
                        
                        if (financeRepVO.getType() == fmbb.getType() && financeRepVO.getItemIndex() == fmbb.getItemIndex() )
                        {
                            
                            Long lastFinaMonth = fmbb.getMoney();
                            
                            financeRepVO.setEndMoney(financeRepVO.getEndMoney() + lastFinaMonth);

                            financeRepVO.setEndMoneyStr(FinanceHelper.longToString(financeRepVO.getEndMoney()));
                        }
                        
                    }
                    
                }
                
                // 因为2012.3月份数据是财务OA上线后补录的数据，所以当查询2012-03月的数据时，当月的数据要从实录的数据中取得
                if (endTimeKey.equals("201203")){
                    condition.clear();
                    
                    condition.addWhereStr();
                    
                    condition.addIntCondition("FinanceMonthBefBean.type", "=", 1);
                    
                    condition.addCondition("FinanceMonthBefBean.monthkey", "=", beginTimeKey1);
                    
                    List<FinanceMonthBefBean> fmbb1List = financeMonthBefDAO.queryEntityBeansByCondition(condition);
                    
                    for (FinanceRepVO financeRepVO : repList)
                    {
                        
                        for (FinanceMonthBefBean fmbb : fmbb1List)
                        {
                            
                            if (financeRepVO.getType() == fmbb.getType() && financeRepVO.getItemIndex() == fmbb.getItemIndex() )
                            {
                                
                                financeRepVO.setBeginMoney(fmbb.getMoney());

                                financeRepVO.setBeginMoneyStr(FinanceHelper.longToString(fmbb.getMoney()));
                                
                                financeRepVO.setEndMoney(fmbb.getMoney());

                                financeRepVO.setEndMoneyStr(FinanceHelper.longToString(fmbb.getMoney()));
                            }
                            
                        }
                        
                    }
                }
                
                
            }

            for (FinanceRepVO financeRepVO : repList)
            {
                if (financeRepVO.getRmethod() == FinaConstant.FINANCEREP_RMETHOD_ALL)
                {
                    financeRepVO.setEndMoneyChineseStr(MathTools.hangeToBig(FinanceHelper
                        .longToDouble(financeRepVO.getEndMoney())));
                }
            }

            request.getSession().setAttribute("g_tax_rep_resultList" + type, repList);

            request.getSession().setAttribute("g_tax_rep_type", type);
        }
        catch (Exception e)
        {
            request.setAttribute(KeyConstant.ERROR_MESSAGE, "查询失败");

            _logger.error(e, e);

            return mapping.findForward("error");
        }

        return mapping.findForward(QUERYTAXREPORT + (CommonTools.parseInt(type) + 1));
    }

    /**
     * 合计出结转结余(辅助)
     * 
     * @param request
     * @param user
     * @return
     * @throws MYException
     */
    private FinanceItemVO sumHead(HttpServletRequest request, User user)
        throws MYException
    {
        // 计算出开始日期前的结余(开始日期前扫描全表,这里可以优化,如果只有科目可以查询月结表)
        FinanceMonthBean monthTurn = hasMonthTurn(request, user);

        ConditionParse preQueryCondition = null;

        if (monthTurn == null)
        {
            preQueryCondition = getQueryCondition(request, user, 1);
        }
        else
        {
            // 构建条件
            preQueryCondition = new ConditionParse();

            preQueryCondition.addWhereStr();

            // 开始日期前的结余
            String beginDate = request.getParameter("beginDate");

            TaxVO tax = (TaxVO)request.getAttribute("tax");

            // 动态级别的查询
            preQueryCondition.addCondition("FinanceItemBean.taxId" + tax.getLevel(), "=", tax
                .getId());

            preQueryCondition.addCondition("FinanceItemBean.financeDate", ">=", monthTurn
                .getDescription());

            preQueryCondition.addCondition("FinanceItemBean.financeDate", "<", beginDate);
        }

        TaxVO tax = (TaxVO)request.getAttribute("tax");

        FinanceItemVO head = sumHeadInner(monthTurn, preQueryCondition, tax);

        request.getSession().setAttribute("queryTaxFinance1_head", head);

        String beginDate = request.getParameter("beginDate");

        head.setDescription("结余(" + beginDate + "之前)");

        return head;
    }

    /**
     * sumHeadInner
     * 
     * @param preQueryCondition
     * @param tax
     * @return
     */
    private FinanceItemVO sumHeadInner(FinanceMonthBean monthTurn,
                                       ConditionParse preQueryCondition, TaxBean tax)
    {
        // 期初余额
        long last = 0L;

        FinanceItemVO head = new FinanceItemVO();

        // 借方
        long[] sumMoneryByCondition = financeItemDAO.sumMoneryByCondition(preQueryCondition);

        if (monthTurn != null)
        {
            // 通过月结查询和增量查询
            sumMoneryByCondition[0] += monthTurn.getInmoneyAllTotal();

            sumMoneryByCondition[1] += monthTurn.getOutmoneyAllTotal();
        }

        head.setInmoney(sumMoneryByCondition[0]);

        head.setOutmoney(sumMoneryByCondition[1]);

        if (tax.getForward() == TaxConstanst.TAX_FORWARD_IN)
        {
            last = sumMoneryByCondition[0] - sumMoneryByCondition[1];
        }
        else
        {
            last = sumMoneryByCondition[1] - sumMoneryByCondition[0];
        }

        head.setTaxId(tax.getId());

        fillItemVO(head);

        // 开始日期前累计余额
        head.setLastmoney(last);

        head.setShowLastmoney(FinanceHelper.longToString(last));

        return head;
    }

    /**
     * 当期合计(查询条件重复使用)
     * 
     * @param request
     * @param user
     * @param condtion
     * @return
     * @throws MYException
     */
    private FinanceItemVO sumCurrentTotal(HttpServletRequest request, User user,
                                          ConditionParse condtion)
        throws MYException
    {
        TaxVO tax = (TaxVO)request.getAttribute("tax");

        // 借方
        long[] sumMoneryByCondition = financeItemDAO.sumMoneryByCondition(condtion);

        FinanceItemVO currentTotal = new FinanceItemVO();

        currentTotal.setTaxId(tax.getId());

        if (sumMoneryByCondition[0] == sumMoneryByCondition[1])
        {
            currentTotal.setDescription("当期合计:"
                                        + FinanceHelper
                                            .longToChineseString(sumMoneryByCondition[0]));
        }
        else
        {
            currentTotal.setDescription("当期合计");
        }

        currentTotal.setInmoney(sumMoneryByCondition[0]);

        currentTotal.setOutmoney(sumMoneryByCondition[1]);

        fillItemVO(currentTotal);

        request.getSession().setAttribute("queryTaxFinance1_currentTotal", currentTotal);

        return currentTotal;
    }

    /**
     * 当前累计（借方：从当年1月到选择的结束日期的借方和，贷一样，但是余额是从帐册开始到结束时间的余额，支持辅助核算的过滤）
     * 
     * @param request
     * @param user
     * @param condtion
     * @return
     * @throws MYException
     */
    private FinanceItemVO sumAllTotal(HttpServletRequest request, User user)
        throws MYException
    {
        // 从当年1月到选择的结束日期
        ConditionParse condtion = getQueryCondition(request, user, 2);

        TaxVO tax = (TaxVO)request.getAttribute("tax");

        // 借方
        long[] sumMoneryByCondition = financeItemDAO.sumMoneryByCondition(condtion);

        FinanceItemVO allTotal = new FinanceItemVO();

        allTotal.setTaxId(tax.getId());

        allTotal.setDescription("当年累计(余额是截至查询期末余额)");

        allTotal.setInmoney(sumMoneryByCondition[0]);

        allTotal.setOutmoney(sumMoneryByCondition[1]);

        fillItemVO(allTotal);

        FinanceItemVO current = (FinanceItemVO)request.getSession().getAttribute(
            "queryTaxFinance1_currentTotal");

        // 累计的需要叠加
        FinanceItemVO head = (FinanceItemVO)request.getSession().getAttribute(
            "queryTaxFinance1_head");

        // 重新计算(结余即期末余额+当期余额)
        allTotal.setLastmoney(head.getLastmoney() + current.getLastmoney());

        allTotal.setShowLastmoney(FinanceHelper.longToString(allTotal.getLastmoney()));

        request.getSession().setAttribute("queryTaxFinance1_allTotal", allTotal);

        return allTotal;
    }

    /**
     * getQueryCondition
     * 
     * @param request
     * @param user
     * @param type
     * @return
     * @throws MYException
     */
    protected ConditionParse getQueryCondition(HttpServletRequest request, User user, int type)
        throws MYException
    {
        ConditionParse condtion = new ConditionParse();

        condtion.addWhereStr();

        if (type == 0)
        {
            String beginDate = request.getParameter("beginDate");
            condtion.addCondition("FinanceItemBean.financeDate", ">=", beginDate);

            String endDate = request.getParameter("endDate");
            condtion.addCondition("FinanceItemBean.financeDate", "<=", endDate);

            if ( !beginDate.substring(0, 4).equals(endDate.substring(0, 4)))
            {
                throw new MYException("查询不能跨年");
            }
        }

        // 结转 开始日期前的结余(整个表查询哦)
        if (type == 1)
        {
            String begin = parameterDAO.getString(SysConfigConstant.TAX_EXPORT_POINT);

            // 开始日期前的结余
            String beginDate = request.getParameter("beginDate");

            // 这里的时间是默认的
            condtion.addCondition("FinanceItemBean.financeDate", ">=", begin);

            condtion.addCondition("FinanceItemBean.financeDate", "<", beginDate);
        }

        // 当前累计(从当年1月到选择的结束日期)
        if (type == 2)
        {
            String endDate = request.getParameter("endDate");

            // 从当年1月
            condtion.addCondition("FinanceItemBean.financeDate", ">=", getYearBegin(endDate
                .substring(0, 4)
                                                                                    + "-01-01"));

            condtion.addCondition("FinanceItemBean.financeDate", "<=", endDate);
        }

        String taxId = request.getParameter("taxId");

        TaxVO tax = taxDAO.findVO(taxId);

        if (tax == null)
        {
            throw new MYException("数据错误,请确认操作");
        }

        request.setAttribute("tax", tax);

        request.getSession().setAttribute("queryTaxFinance1_tax", tax);

        // 动态级别的查询
        condtion.addCondition("FinanceItemBean.taxId" + tax.getLevel(), "=", taxId);

        // 支持不同纳税下查询
        String dutyId = request.getParameter("dutyId");

        if ( !StringTools.isNullOrNone(dutyId))
        {
            condtion.addCondition("FinanceItemBean.dutyId", "=", dutyId);
        }

        String stafferId = request.getParameter("stafferId");

        if ( !StringTools.isNullOrNone(stafferId))
        {
            condtion.addCondition("FinanceItemBean.stafferId", "=", stafferId);
        }

        String departmentId = request.getParameter("departmentId");

        if ( !StringTools.isNullOrNone(departmentId))
        {
            condtion.addCondition("FinanceItemBean.departmentId", "=", departmentId);
        }

        String productId = request.getParameter("productId");

        if ( !StringTools.isNullOrNone(productId))
        {
            condtion.addCondition("FinanceItemBean.productId", "=", productId);
        }

        String depotId = request.getParameter("depotId");

        if ( !StringTools.isNullOrNone(depotId))
        {
            condtion.addCondition("FinanceItemBean.depotId", "=", depotId);
        }

        String unitId = request.getParameter("unitId");

        if ( !StringTools.isNullOrNone(unitId))
        {
            condtion.addCondition("FinanceItemBean.unitId", "=", unitId);
        }

        return condtion;
    }

    /**
     * getYearBegin
     * 
     * @param begin
     * @return
     */
    private String getYearBegin(String begin)
    {
        String beginConfig = parameterDAO.getString(SysConfigConstant.TAX_EXPORT_POINT);

        if (StringTools.isNullOrNone(beginConfig))
        {
            return begin;
        }

        if (beginConfig.compareTo(begin) > 0)
        {
            return beginConfig;
        }

        return begin;
    }

    /**
     * 是否可以使用月结加快统计(有就就是FinanceMonthBean)
     * 
     * @param request
     * @param user
     * @return
     * @throws MYException
     */
    private FinanceMonthBean hasMonthTurn(HttpServletRequest request, User user)
        throws MYException
    {
        boolean quickQuery = parameterDAO.getBoolean(SysConfigConstant.TAX_QUICK_QUERY);

        // 不启用
        if ( !quickQuery)
        {
            return null;
        }

        String stafferId = request.getParameter("stafferId");

        String departmentId = request.getParameter("departmentId");

        String beginDate = request.getParameter("beginDate");

        String taxId = request.getParameter("taxId");

        String productId = request.getParameter("productId");

        String depotId = request.getParameter("depotId");

        String unitId = request.getParameter("unitId");

        if (StringTools.isNullOrNone(stafferId) && StringTools.isNullOrNone(departmentId)
            && StringTools.isNullOrNone(productId) && StringTools.isNullOrNone(depotId)
            && StringTools.isNullOrNone(unitId))
        {
            TaxVO tax = taxDAO.findVO(taxId);

            if (tax == null)
            {
                throw new MYException("数据错误,请确认操作");
            }

            request.setAttribute("tax", tax);

            request.getSession().setAttribute("queryTaxFinance1_tax", tax);

            Calendar cal = Calendar.getInstance();

            // 本月时间
            cal.setTime(TimeTools.getDateByFormat(beginDate, TimeTools.SHORT_FORMAT));

            // 下个月的1号
            cal.set(Calendar.MONTH, cal.get(Calendar.MONTH) - 1);

            String turnMonth = TimeTools.getStringByFormat(new Date(cal.getTime().getTime()),
                "yyyyMM");

            FinanceMonthBean month = financeMonthDAO.findByUnique(taxId, turnMonth);

            if (month != null)
            {
                // 获取月初
                month.setDescription(beginDate.substring(0, 7) + "-01");
            }

            return month;
        }

        return null;
    }

    /**
     * 科目余额的导出
     * 
     * @param mapping
     * @param form
     * @param request
     * @param reponse
     * @return
     * @throws ServletException
     */
    public ActionForward exportTaxQuery(ActionMapping mapping, ActionForm form,
                                        HttpServletRequest request, HttpServletResponse reponse)
        throws ServletException
    {
        OutputStream out = null;

        String filenName = "Tax_" + TimeTools.now("MMddHHmmss") + ".csv";

        reponse.setContentType("application/x-dbf");

        reponse.setHeader("Content-Disposition", "attachment; filename=" + filenName);

        WriteFile write = null;

        try
        {
            out = reponse.getOutputStream();

            write = WriteFileFactory.getMyTXTWriter();

            write.openFile(out);

            Object attribute = request.getSession().getAttribute("EXPORT_FINANCEITE_KEY");

            if (attribute == null)
            {
                return ActionTools.toError(mapping, request);
            }

            String type = attribute.toString();

            if (type.equals(QUERYTAXFINANCE2))
            {

                String queryType = request
                    .getSession()
                    .getAttribute("EXPORT_FINANCEITE_QUERYTYPE")
                    .toString();

                List<FinanceShowVO> showList = (List<FinanceShowVO>)request
                    .getSession()
                    .getAttribute("resultList_2");

                if ("0".equals(queryType))
                {
                    write.writeLine("科目,级别,名称,期初余额,本期借方,本期贷方,借方累计,贷方累计,方向,期末余额");

                    for (FinanceShowVO each : showList)
                    {
                        WriteFileBuffer line = new WriteFileBuffer(write);

                        line.writeColumn("[" + each.getTaxId() + "]");

                        TaxBean tax = taxDAO.find(each.getTaxId());

                        if (tax != null)
                        {
                            line.writeColumn(ElTools.get("taxBottomFlag", tax.getBottomFlag()));
                        }
                        else
                        {
                            line.writeColumn("");
                        }

                        line.writeColumn(each.getTaxName());
                        line.writeColumn(changeString(each.getShowBeginAllmoney()));
                        line.writeColumn(changeString(each.getShowCurrInmoney()));
                        line.writeColumn(changeString(each.getShowCurrOutmoney()));
                        line.writeColumn(changeString(each.getShowAllInmoney()));
                        line.writeColumn(changeString(each.getShowAllOutmoney()));
                        line.writeColumn(changeString(each.getForwardName()));
                        line.writeColumn(changeString(each.getShowLastmoney()));

                        line.writeLine();
                    }
                }

                if ("1".equals(queryType))
                {
                    write.writeLine("科目,级别,名称,职员,期初余额,本期借方,本期贷方,借方累计,贷方累计,方向,期末余额");

                    for (FinanceShowVO each : showList)
                    {
                        WriteFileBuffer line = new WriteFileBuffer(write);

                        line.writeColumn("[" + each.getTaxId() + "]");

                        TaxBean tax = taxDAO.find(each.getTaxId());

                        if (tax != null)
                        {
                            line.writeColumn(ElTools.get("taxBottomFlag", tax.getBottomFlag()));
                        }
                        else
                        {
                            line.writeColumn("");
                        }

                        line.writeColumn(each.getTaxName());
                        line.writeColumn(each.getStafferName());
                        line.writeColumn(changeString(each.getShowBeginAllmoney()));
                        line.writeColumn(changeString(each.getShowCurrInmoney()));
                        line.writeColumn(changeString(each.getShowCurrOutmoney()));
                        line.writeColumn(changeString(each.getShowAllInmoney()));
                        line.writeColumn(changeString(each.getShowAllOutmoney()));
                        line.writeColumn(changeString(each.getForwardName()));
                        line.writeColumn(changeString(each.getShowLastmoney()));

                        line.writeLine();
                    }
                }

                if ("2".equals(queryType))
                {
                    write.writeLine("科目,级别,名称,单位,期初余额,本期借方,本期贷方,借方累计,贷方累计,方向,期末余额");

                    for (FinanceShowVO each : showList)
                    {
                        WriteFileBuffer line = new WriteFileBuffer(write);

                        line.writeColumn("[" + each.getTaxId() + "]");

                        TaxBean tax = taxDAO.find(each.getTaxId());

                        if (tax != null)
                        {
                            line.writeColumn(ElTools.get("taxBottomFlag", tax.getBottomFlag()));
                        }
                        else
                        {
                            line.writeColumn("");
                        }

                        line.writeColumn(each.getTaxName());
                        line.writeColumn(each.getUnitName());
                        line.writeColumn(changeString(each.getShowBeginAllmoney()));
                        line.writeColumn(changeString(each.getShowCurrInmoney()));
                        line.writeColumn(changeString(each.getShowCurrOutmoney()));
                        line.writeColumn(changeString(each.getShowAllInmoney()));
                        line.writeColumn(changeString(each.getShowAllOutmoney()));
                        line.writeColumn(changeString(each.getForwardName()));
                        line.writeColumn(changeString(each.getShowLastmoney()));

                        line.writeLine();
                    }
                }
                
                // 职员 单位导出
                if ("3".equals(queryType))
                {
                    write.writeLine("科目,级别,名称,职员,单位,期初余额,本期借方,本期贷方,借方累计,贷方累计,方向,期末余额");

                    for (FinanceShowVO each : showList)
                    {
                        WriteFileBuffer line = new WriteFileBuffer(write);

                        line.writeColumn("[" + each.getTaxId() + "]");

                        TaxBean tax = taxDAO.find(each.getTaxId());

                        if (tax != null)
                        {
                            line.writeColumn(ElTools.get("taxBottomFlag", tax.getBottomFlag()));
                        }
                        else
                        {
                            line.writeColumn("");
                        }

                        line.writeColumn(each.getTaxName());
                        line.writeColumn(each.getStafferName());
                        line.writeColumn(each.getUnitName());
                        line.writeColumn(changeString(each.getShowBeginAllmoney()));
                        line.writeColumn(changeString(each.getShowCurrInmoney()));
                        line.writeColumn(changeString(each.getShowCurrOutmoney()));
                        line.writeColumn(changeString(each.getShowAllInmoney()));
                        line.writeColumn(changeString(each.getShowAllOutmoney()));
                        line.writeColumn(changeString(each.getForwardName()));
                        line.writeColumn(changeString(each.getShowLastmoney()));

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

    /**
     * 导出报表
     * 
     * @param mapping
     * @param form
     * @param request
     * @param reponse
     * @return
     * @throws ServletException
     */
    public ActionForward exportTaxReport(ActionMapping mapping, ActionForm form,
                                         HttpServletRequest request, HttpServletResponse reponse)
        throws ServletException
    {
        OutputStream out = null;

        String filenName = "Tax_Report_" + TimeTools.now("MMddHHmmss") + ".csv";

        reponse.setContentType("application/x-dbf");

        reponse.setHeader("Content-Disposition", "attachment; filename=" + filenName);

        WriteFile write = null;

        try
        {
            out = reponse.getOutputStream();

            write = WriteFileFactory.getMyTXTWriter();

            write.openFile(out);

            // g_tax_rep_type
            Object attribute = request.getSession().getAttribute("g_tax_rep_type");

            if (attribute == null)
            {
                return ActionTools.toError(mapping, request);
            }

            String type = attribute.toString();

            List<FinanceRepVO> list = (List<FinanceRepVO>)request.getSession().getAttribute(
                "g_tax_rep_resultList" + type);

            if ("0".equals(type))
            {
                write.writeLine("行次,分类,名称,年初数,期末数,算法");

                for (FinanceRepVO each : list)
                {
                    WriteFileBuffer line = new WriteFileBuffer(write);

                    line.writeColumn(each.getItemIndex());
                    line.writeColumn(each.getItemPName());
                    line.writeColumn(each.getItemName());
                    line.writeColumn(changeString(each.getBeginMoneyStr()));
                    line.writeColumn(changeString(each.getEndMoneyStr()));
                    line.writeColumn(changeString('[' + each.getExpr() + ']'));

                    line.writeLine();
                }
            }

            if ("1".equals(type))
            {
                write.writeLine("行次,分类,名称,本初数,本年累计数,算法");

                for (FinanceRepVO each : list)
                {
                    WriteFileBuffer line = new WriteFileBuffer(write);

                    line.writeColumn(each.getItemIndex());
                    line.writeColumn(each.getItemPName());
                    line.writeColumn(each.getItemName());
                    line.writeColumn(changeString(each.getBeginMoneyStr()));
                    line.writeColumn(changeString(each.getEndMoneyStr()));
                    line.writeColumn(changeString('[' + each.getExpr() + ']'));

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
     * getQueryCondition3
     * 
     * @param request
     * @param user
     * @param type
     * @return
     * @throws MYException
     */
    protected ConditionParse getQueryCondition3(HttpServletRequest request, User user, int type,
                                                String parentTax)
        throws MYException
    {
        ConditionParse condtion = new ConditionParse();

        condtion.addWhereStr();

        if (type == 0)
        {
            String year = request.getParameter("year");
            String month = request.getParameter("month");

            condtion.addCondition("FinanceItemBean.financeDate", ">=", year + "-" + month + "-01");

            condtion.addCondition("FinanceItemBean.financeDate", "<=", year + "-" + month + "-31");
        }

        String taxId = request.getParameter("taxId");

        if (StringTools.isNullOrNone(taxId))
        {
            taxId = parentTax;
        }

        TaxVO tax = taxDAO.findVO(taxId);

        if (tax == null)
        {
            throw new MYException("数据错误,请确认操作");
        }

        request.setAttribute("tax", tax);

        // 动态级别的查询
        condtion.addCondition("FinanceItemBean.taxId" + tax.getLevel(), "=", taxId);

        String stafferId = request.getParameter("stafferId");

        if ( !StringTools.isNullOrNone(stafferId))
        {
            condtion.addCondition("FinanceItemBean.stafferId", "=", stafferId);
        }

        String departmentId = request.getParameter("departmentId");

        if ( !StringTools.isNullOrNone(departmentId))
        {
            condtion.addCondition("FinanceItemBean.departmentId", "=", departmentId);
        }

        return condtion;
    }

    /**
     * 科目的查询条件(科目余额)
     * 
     * @param request
     * @param user
     * @param type
     * @return
     * @throws MYException
     */
    protected ConditionParse getQueryCondition2(HttpServletRequest request, User user, int type)
        throws MYException
    {
        ConditionParse condtion = new ConditionParse();

        condtion.addWhereStr();

        if (type == 0)
        {
            String beginDate = request.getParameter("beginDate");

            condtion.addCondition("FinanceItemBean.financeDate", ">=", beginDate);

            String endDate = request.getParameter("endDate");

            condtion.addCondition("FinanceItemBean.financeDate", "<=", endDate);

            if ( !beginDate.substring(0, 4).equals(endDate.substring(0, 4)))
            {
                throw new MYException("查询不能跨年");
            }
        }

        // 结转 开始日期前的结余(整个表查询哦)
        if (type == 1)
        {
            String begin = parameterDAO.getString(SysConfigConstant.TAX_EXPORT_POINT);

            // 开始日期前的结余
            String beginDate = request.getParameter("beginDate");

            // 这里的时间是默认的
            condtion.addCondition("FinanceItemBean.financeDate", ">=", begin);

            condtion.addCondition("FinanceItemBean.financeDate", "<", beginDate);
        }

        // 当前累计(从当年1月到选择的结束日期)
        if (type == 2)
        {
            String endDate = request.getParameter("endDate");

            // 从当年1月
            condtion.addCondition("FinanceItemBean.financeDate", ">=", getYearBegin(endDate
                .substring(0, 4)
                                                                                    + "-01-01"));

            condtion.addCondition("FinanceItemBean.financeDate", "<=", endDate);
        }

        // 支持不同纳税下查询
        String dutyId = request.getParameter("dutyId");

        if ( !StringTools.isNullOrNone(dutyId))
        {
            condtion.addCondition("FinanceItemBean.dutyId", "=", dutyId);
        }

        return condtion;
    }

    /**
     * 科目余额查询(先查询上月的结余,然后是时间内的统计)
     * 
     * @param mapping
     * @param form
     * @param request
     * @param reponse
     * @return
     * @throws ServletException
     */
    public ActionForward queryTaxFinance2(ActionMapping mapping, ActionForm form,
                                          HttpServletRequest request, HttpServletResponse reponse)
        throws ServletException
    {
        User user = Helper.getUser(request);

        String queryType = request.getParameter("queryType");
        
        // 只针对科目类型
        String queryMode = request.getParameter("queryMode");

        request.getSession().setAttribute("EXPORT_FINANCEITE_KEY", QUERYTAXFINANCE2);

        request.getSession().setAttribute("EXPORT_FINANCEITE_QUERYTYPE", queryType);

        CommonTools.saveParamers(request);

        try
        {
            String taxId = request.getParameter("taxId");

            TaxVO tax = null;

            if ( !StringTools.isNullOrNone(taxId))
            {
                tax = taxDAO.findVO(taxId);
            }

            // 科目查询
            if ("0".equals(queryType))
            {
            	if ("0".equals(queryMode))
            		processTaxLastQuery(request, user, tax);
            	else
            	{
            		List<FinanceShowVO> showList = financeShowDAO.listEntityVOs();

                    request.getSession().setAttribute("resultList_2", showList);
            	}
            }

            // 职员查询
            if ("1".equals(queryType))
            {
                if (tax == null)
                {
                    throw new MYException("缺少科目");
                }

                processStafferLastQuery(request, user, tax, true);
            }

            // 单位查询
            if ("2".equals(queryType))
            {
                if (tax == null)
                {
                    throw new MYException("缺少科目");
                }

                processUnitLastQuery(request, user, tax, true);
            }
            
            // 职员单位查询 - 限制只当科目为1132 2131时使用
            if ("3".equals(queryType))
            {
                if (tax == null)
                {
                    throw new MYException("缺少科目");
                }
                
                processStafferAndUnitLastQuery(request, user, tax, true);
            }
        }
        catch (MYException e)
        {
            request.setAttribute(KeyConstant.ERROR_MESSAGE, e.getErrorContent());

            _logger.error(e, e);

            return mapping.findForward("error");
        }

        return mapping.findForward(QUERYTAXFINANCE2);
    }

    /**
     * 查询导出科目余额(职员和单位)
     * 
     * @param mapping
     * @param form
     * @param request
     * @param reponse
     * @return
     * @throws ServletException
     */
    public ActionForward queryExportTaxFinance2(ActionMapping mapping, ActionForm form,
                                                HttpServletRequest request,
                                                HttpServletResponse reponse)
        throws ServletException
    {
        User user = Helper.getUser(request);

        String queryType = request.getParameter("queryType");

        CommonTools.saveParamers(request);

        List<FinanceShowVO> showList = new LinkedList<FinanceShowVO>();

        try
        {
            String taxId = request.getParameter("taxId");

            TaxVO tax = null;

            if ( !StringTools.isNullOrNone(taxId))
            {
                tax = taxDAO.findVO(taxId);
            }

            // 科目查询
            if ("0".equals(queryType))
            {
                showList.addAll(processTaxLastQuery(request, user, tax));
            }

            // 职员查询
            if ("1".equals(queryType))
            {
                if (tax != null)
                {
                    showList.addAll(processStafferLastQuery(request, user, tax, false));
                }
                else
                {
                    // 职员相关的末级科目
                    List<TaxVO> listLastStafferTax = taxDAO.listLastStafferTax();

                    for (TaxVO taxVO : listLastStafferTax)
                    {
                        showList.addAll(processStafferLastQuery(request, user, taxVO, false));
                    }
                }
            }

            // 单位查询
            if ("2".equals(queryType))
            {
                if (tax != null)
                {
                    showList.addAll(processUnitLastQuery(request, user, tax, false));
                }
                else
                {
                    // 职员相关的末级科目
                    List<TaxVO> listLastStafferTax = taxDAO.listLastUnitTax();

                    for (TaxVO taxVO : listLastStafferTax)
                    {
                        showList.addAll(processUnitLastQuery(request, user, taxVO, false));
                    }
                }
            }
            
            // 职员单位查询
            if ("3".equals(queryType))
            {
                if (tax != null)
                {
                    showList.addAll(processStafferAndUnitLastQuery(request, user, tax, false));
                }
            }
        }
        catch (MYException e)
        {
            request.setAttribute(KeyConstant.ERROR_MESSAGE, e.getErrorContent());

            _logger.error(e, e);

            return mapping.findForward("error");
        }

        OutputStream out = null;

        String filenName = "Tax_" + TimeTools.now("MMddHHmmss") + ".csv";

        reponse.setContentType("application/x-dbf");

        reponse.setHeader("Content-Disposition", "attachment; filename=" + filenName);

        WriteFile write = null;

        try
        {
            out = reponse.getOutputStream();

            write = WriteFileFactory.getMyTXTWriter();

            write.openFile(out);

            if ("0".equals(queryType))
            {
                write.writeLine("科目,级别,名称,期初余额,本期借方,本期贷方,借方累计,贷方累计,方向,期末余额");

                for (FinanceShowVO each : showList)
                {
                    WriteFileBuffer line = new WriteFileBuffer(write);

                    line.writeColumn("[" + each.getTaxId() + "]");

                    TaxBean tax = taxDAO.find(each.getTaxId());

                    if (tax != null)
                    {
                        line.writeColumn(ElTools.get("taxBottomFlag", tax.getBottomFlag()));
                    }
                    else
                    {
                        line.writeColumn("");
                    }

                    line.writeColumn(each.getTaxName());
                    line.writeColumn(changeString(each.getShowBeginAllmoney()));
                    line.writeColumn(changeString(each.getShowCurrInmoney()));
                    line.writeColumn(changeString(each.getShowCurrOutmoney()));
                    line.writeColumn(changeString(each.getShowAllInmoney()));
                    line.writeColumn(changeString(each.getShowAllOutmoney()));
                    line.writeColumn(changeString(each.getForwardName()));
                    line.writeColumn(changeString(each.getShowLastmoney()));

                    line.writeLine();
                }
            }

            if ("1".equals(queryType))
            {
                write.writeLine("科目,级别,名称,职员,期初余额,本期借方,本期贷方,借方累计,贷方累计,方向,期末余额");

                for (FinanceShowVO each : showList)
                {
                    WriteFileBuffer line = new WriteFileBuffer(write);

                    line.writeColumn("[" + each.getTaxId() + "]");

                    TaxBean tax = taxDAO.find(each.getTaxId());

                    if (tax != null)
                    {
                        line.writeColumn(ElTools.get("taxBottomFlag", tax.getBottomFlag()));
                    }
                    else
                    {
                        line.writeColumn("");
                    }

                    line.writeColumn(each.getTaxName());
                    line.writeColumn(each.getStafferName());
                    line.writeColumn(changeString(each.getShowBeginAllmoney()));
                    line.writeColumn(changeString(each.getShowCurrInmoney()));
                    line.writeColumn(changeString(each.getShowCurrOutmoney()));
                    line.writeColumn(changeString(each.getShowAllInmoney()));
                    line.writeColumn(changeString(each.getShowAllOutmoney()));
                    line.writeColumn(changeString(each.getForwardName()));
                    line.writeColumn(changeString(each.getShowLastmoney()));

                    line.writeLine();
                }
            }

            if ("2".equals(queryType))
            {
                write.writeLine("科目,级别,名称,单位,期初余额,本期借方,本期贷方,借方累计,贷方累计,方向,期末余额");

                for (FinanceShowVO each : showList)
                {
                    WriteFileBuffer line = new WriteFileBuffer(write);

                    line.writeColumn("[" + each.getTaxId() + "]");

                    TaxBean tax = taxDAO.find(each.getTaxId());

                    if (tax != null)
                    {
                        line.writeColumn(ElTools.get("taxBottomFlag", tax.getBottomFlag()));
                    }
                    else
                    {
                        line.writeColumn("");
                    }

                    line.writeColumn(each.getTaxName());
                    line.writeColumn(each.getUnitName());
                    line.writeColumn(changeString(each.getShowBeginAllmoney()));
                    line.writeColumn(changeString(each.getShowCurrInmoney()));
                    line.writeColumn(changeString(each.getShowCurrOutmoney()));
                    line.writeColumn(changeString(each.getShowAllInmoney()));
                    line.writeColumn(changeString(each.getShowAllOutmoney()));
                    line.writeColumn(changeString(each.getForwardName()));
                    line.writeColumn(changeString(each.getShowLastmoney()));

                    line.writeLine();
                }
            }

         // 职员 单位导出
            if ("3".equals(queryType))
            {
                write.writeLine("科目,级别,名称,职员,单位,期初余额,本期借方,本期贷方,借方累计,贷方累计,方向,期末余额");

                for (FinanceShowVO each : showList)
                {
                    WriteFileBuffer line = new WriteFileBuffer(write);

                    line.writeColumn("[" + each.getTaxId() + "]");

                    TaxBean tax = taxDAO.find(each.getTaxId());

                    if (tax != null)
                    {
                        line.writeColumn(ElTools.get("taxBottomFlag", tax.getBottomFlag()));
                    }
                    else
                    {
                        line.writeColumn("");
                    }

                    line.writeColumn(each.getTaxName());
                    line.writeColumn(each.getStafferName());
                    line.writeColumn(each.getUnitName());
                    line.writeColumn(changeString(each.getShowBeginAllmoney()));
                    line.writeColumn(changeString(each.getShowCurrInmoney()));
                    line.writeColumn(changeString(each.getShowCurrOutmoney()));
                    line.writeColumn(changeString(each.getShowAllInmoney()));
                    line.writeColumn(changeString(each.getShowAllOutmoney()));
                    line.writeColumn(changeString(each.getForwardName()));
                    line.writeColumn(changeString(each.getShowLastmoney()));

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
     * 科目余额-科目：只有科目
     * 
     * @param request
     * @param user
     * @param tax
     * @throws MYException
     */
    private List<FinanceShowVO> processTaxLastQuery(HttpServletRequest request, User user, TaxVO tax)
        throws MYException
    {
        String stafferId = request.getParameter("stafferId");

        String unitId = request.getParameter("unitId");

        String taxLevel = request.getParameter("taxLevel");

        if ( !StringTools.isNullOrNone(stafferId) && !StringTools.isNullOrNone(unitId))
        {
            throw new MYException("科目余额查询不能同时有职员和单位");
        }

        List<TaxBean> taxList = null;

        if (tax == null)
        {
            if ( !StringTools.isNullOrNone(stafferId))
            {
                // 辅助核算
                ConditionParse con = new ConditionParse();
                con.addWhereStr();

                con.addIntCondition("staffer", "=", TaxConstanst.TAX_CHECK_YES);

                taxList = taxDAO.queryEntityBeansByCondition(con.toString());
            }
            else if ( !StringTools.isNullOrNone(unitId))
            {
                // 辅助核算
                ConditionParse con = new ConditionParse();
                con.addWhereStr();

                con.addIntCondition("unit", "=", TaxConstanst.TAX_CHECK_YES);

                taxList = taxDAO.queryEntityBeansByCondition(con.toString());
            }
            else
            {
                taxList = taxDAO.listEntityBeansByOrder("order by id");
            }
        }
        else
        {
            if (tax.getBottomFlag() == TaxConstanst.TAX_BOTTOMFLAG_ROOT)
            {
                // 查询所有的
                // 动态级别的查询
                ConditionParse taxCondition = new ConditionParse();
                taxCondition.addWhereStr();
                taxCondition.addCondition("parentId" + tax.getLevel(), "=", tax.getId());

                taxList = taxDAO.queryEntityBeansByCondition(taxCondition.toString());

                taxList.add(0, tax);
            }
            else
            {
                taxList = new ArrayList();

                taxList.add(tax);
            }
        }

        if ( !StringTools.isNullOrNone(taxLevel))
        {
            for (Iterator iterator = taxList.iterator(); iterator.hasNext();)
            {
                TaxBean each = (TaxBean)iterator.next();

                if ( !String.valueOf(each.getLevel()).equals(taxLevel))
                {
                    iterator.remove();
                }
            }
        }

        List<FinanceShowVO> showList = new LinkedList<FinanceShowVO>();

        request.getSession().setAttribute("resultList_2", showList);

        // 查询
        for (TaxBean taxBean : taxList)
        {
            FinanceShowVO show = new FinanceShowVO(0);

            show.setTaxId(taxBean.getId());

            show.setTaxName(taxBean.getName());

            show.setForwardName(FinanceHelper.getForwardName(taxBean));

            // 本期借方/贷方
            ConditionParse condtion = getQueryCondition2(request, user, 0);

            createTaxQuery(request, taxBean, condtion);

            long[] sumCurrMoneryByCondition = financeItemDAO.sumMoneryByCondition(condtion);

            show.setShowCurrInmoney(FinanceHelper.longToString(sumCurrMoneryByCondition[0]));
            show.setShowCurrOutmoney(FinanceHelper.longToString(sumCurrMoneryByCondition[1]));

            // 期初余额
            condtion = getQueryCondition2(request, user, 1);

            createTaxQuery(request, taxBean, condtion);

            FinanceItemVO head = sumHeadInner(null, condtion, taxBean);

            show.setShowBeginAllmoney(FinanceHelper.longToString(head.getLastmoney()));

            // 当前累计(从当年1月到选择的结束日期)
            condtion = getQueryCondition2(request, user, 2);

            createTaxQuery(request, taxBean, condtion);

            long[] sumAllMoneryByCondition = financeItemDAO.sumMoneryByCondition(condtion);

            show.setShowAllInmoney(FinanceHelper.longToString(sumAllMoneryByCondition[0]));
            show.setShowAllOutmoney(FinanceHelper.longToString(sumAllMoneryByCondition[1]));

            // 期末余额
            long currentLast = 0L;

            if (taxBean.getForward() == TaxConstanst.TAX_FORWARD_IN)
            {
                currentLast = sumCurrMoneryByCondition[0] - sumCurrMoneryByCondition[1];
            }
            else
            {
                currentLast = sumCurrMoneryByCondition[1] - sumCurrMoneryByCondition[0];
            }

            // 期初+当期发生=期末
            show.setShowLastmoney(FinanceHelper.longToString(head.getLastmoney() + currentLast));

            showList.add(show);
        }

        return showList;
    }

    /**
     * createTaxQuery
     * 
     * @param request
     * @param taxBean
     * @param condtion
     */
    private void createTaxQuery(HttpServletRequest request, TaxBean taxBean, ConditionParse condtion)
    {
        condtion.addCondition("FinanceItemBean.taxId" + taxBean.getLevel(), "=", taxBean.getId());

        String stafferId = request.getParameter("stafferId");

        if ( !StringTools.isNullOrNone(stafferId))
        {
            condtion.addCondition("FinanceItemBean.stafferId", "=", stafferId);
        }

        String unitId = request.getParameter("unitId");

        if ( !StringTools.isNullOrNone(unitId))
        {
            condtion.addCondition("FinanceItemBean.unitId", "=", unitId);
        }
    }

    /**
     * 单位么科目余额查询
     * 
     * @param request
     * @param user
     * @param isTotal
     *            isTotal
     * @param tax
     * @throws MYException
     */
    private List<FinanceShowVO> processUnitLastQuery(HttpServletRequest request, User user,
                                                     TaxVO taxBean, boolean isTotal)
        throws MYException
    {
        List<FinanceShowVO> showList = new LinkedList<FinanceShowVO>();

        request.getSession().setAttribute("resultList_2", showList);

        // 这里的查询分为职员下所有单位的查询,或者查询指定的一个单位

        String stafferId = request.getParameter("stafferId");

        String unitId = request.getParameter("unitId");

        List<String> unitList = new LinkedList<String>();

        String beginDate = request.getParameter("beginDate");

        String endDate = request.getParameter("endDate");

        boolean weiyi = false;

        // 查询名下所有的单位(过滤掉查询范围内没有出现的单位)
        if ( !StringTools.isNullOrNone(stafferId) && StringTools.isNullOrNone(unitId))
        {
            // 先查询出本期发生的单位
            unitList.addAll(financeItemDAO.queryDistinctUnitByStafferId(stafferId, beginDate,
                endDate));
        }
        else if (StringTools.isNullOrNone(stafferId) && StringTools.isNullOrNone(unitId))
        {
            ConditionParse con = new ConditionParse();

            con.addWhereStr();

            List<FinanceRefer> referList = financeReferDAO
                .queryEntityBeansByFK(FinaConstant.FINANCEREFER_UNIT);

            for (FinanceRefer financeRefer : referList)
            {
                unitList.add(financeRefer.getRefId());
            }

            List<String> queryDistinctUnit = financeItemDAO.queryDistinctUnit(beginDate, endDate,
                "taxId" + taxBean.getLevel(), taxBean.getId());

            for (String each : queryDistinctUnit)
            {
                if ( !unitList.contains(each))
                {
                    unitList.add(each);
                }
            }
        }
        else
        {
            weiyi = true;

            // 只有一个单位
            unitList.add(unitId);
        }

        // 查询每个单位
        for (String eachUnitId : unitList)
        {
            if (StringTools.isNullOrNone(eachUnitId))
            {
                continue;
            }

            // 查询
            FinanceShowVO show = new FinanceShowVO(2);

            show.setTaxId(taxBean.getId());

            show.setTaxName(taxBean.getName());

            show.setForwardName(FinanceHelper.getForwardName(taxBean));

            UnitBean unitBean = unitDAO.find(eachUnitId);

            if (unitBean != null)
            {
                show.setUnitName(unitBean.getName());
            }

            // 本期借方/贷方
            ConditionParse condtion = getQueryCondition2(request, user, 0);

            createUnitCondition(taxBean, eachUnitId, condtion);

            long[] sumCurrMoneryByCondition = financeItemDAO.sumMoneryByCondition(condtion);

            show.setCurrInmoney(sumCurrMoneryByCondition[0]);
            show.setShowCurrInmoney(FinanceHelper.longToString(sumCurrMoneryByCondition[0]));
            show.setCurrOutmoney(sumCurrMoneryByCondition[1]);
            show.setShowCurrOutmoney(FinanceHelper.longToString(sumCurrMoneryByCondition[1]));

            // 期初余额
            condtion = getQueryCondition2(request, user, 1);
            createUnitCondition(taxBean, eachUnitId, condtion);

            FinanceItemVO head = sumHeadInner(null, condtion, taxBean);

            show.setBeginAllmoney(head.getLastmoney());
            show.setShowBeginAllmoney(FinanceHelper.longToString(head.getLastmoney()));

            // 当前累计(从当年1月到选择的结束日期)
            condtion = getQueryCondition2(request, user, 2);
            createUnitCondition(taxBean, eachUnitId, condtion);

            long[] sumAllMoneryByCondition = financeItemDAO.sumMoneryByCondition(condtion);

            show.setAllInmoney(sumAllMoneryByCondition[0]);
            show.setShowAllInmoney(FinanceHelper.longToString(sumAllMoneryByCondition[0]));
            show.setAllOutmoney(sumAllMoneryByCondition[1]);
            show.setShowAllOutmoney(FinanceHelper.longToString(sumAllMoneryByCondition[1]));

            // 期末余额
            long currentLast = 0L;

            if (taxBean.getForward() == TaxConstanst.TAX_FORWARD_IN)
            {
                currentLast = sumCurrMoneryByCondition[0] - sumCurrMoneryByCondition[1];
            }
            else
            {
                currentLast = sumCurrMoneryByCondition[1] - sumCurrMoneryByCondition[0];
            }

            show.setLastmoney(head.getLastmoney() + currentLast);
            show.setShowLastmoney(FinanceHelper.longToString(head.getLastmoney() + currentLast));

            // 三个为0的数据没有意义
            if (weiyi
                || ! (show.getBeginAllmoney() == 0 && show.getCurrInmoney() == 0 && show
                    .getCurrOutmoney() == 0))
            {
                showList.add(show);
            }
        }

        if (isTotal)
        {
            // 合计
            FinanceShowVO total = new FinanceShowVO();

            total.setTaxId("合计");

            total.setForwardName(FinanceHelper.getForwardName(taxBean));

            for (FinanceShowVO financeShowVO : showList)
            {
                total.setBeginAllmoney(financeShowVO.getBeginAllmoney() + total.getBeginAllmoney());

                total.setCurrInmoney(financeShowVO.getCurrInmoney() + total.getCurrInmoney());
                total.setCurrOutmoney(financeShowVO.getCurrOutmoney() + total.getCurrOutmoney());

                total.setAllInmoney(financeShowVO.getAllInmoney() + total.getAllInmoney());
                total.setAllOutmoney(financeShowVO.getAllOutmoney() + total.getAllOutmoney());

                total.setLastmoney(financeShowVO.getLastmoney() + total.getLastmoney());
            }

            total.setShowBeginAllmoney(FinanceHelper.longToString(total.getBeginAllmoney()));
            total.setShowCurrInmoney(FinanceHelper.longToString(total.getCurrInmoney()));
            total.setShowCurrOutmoney(FinanceHelper.longToString(total.getCurrOutmoney()));

            total.setShowAllInmoney(FinanceHelper.longToString(total.getAllInmoney()));
            total.setShowAllOutmoney(FinanceHelper.longToString(total.getAllOutmoney()));

            total.setShowLastmoney(FinanceHelper.longToString(total.getLastmoney()));

            showList.add(total);
        }

        return showList;
    }

    /**
     * processStafferLastQuery(职员查询)
     * 
     * @param request
     * @param user
     * @param taxBean
     * @param isTotal
     *            isTotal
     * @throws MYException
     */
    private List<FinanceShowVO> processStafferLastQuery(HttpServletRequest request, User user,
                                                        TaxVO taxBean, boolean isTotal)
        throws MYException
    {
        List<FinanceShowVO> showList = new LinkedList<FinanceShowVO>();

        request.getSession().setAttribute("resultList_2", showList);

        // 这里的查询分为职员下所有单位的查询,或者查询指定的一个单位

        String stafferId = request.getParameter("stafferId");

        List<String> stafferList = new ArrayList();

        boolean weiyi = false;

        // 查询人员
        if (StringTools.isNullOrNone(stafferId))
        {
            String beginDate = request.getParameter("beginDate");

            String endDate = request.getParameter("endDate");

            List<FinanceRefer> referList = financeReferDAO
                .queryEntityBeansByFK(FinaConstant.FINANCEREFER_STAFFER);

            for (FinanceRefer financeRefer : referList)
            {
                stafferList.add(financeRefer.getRefId());
            }

            List<String> queryDistinctStafferId = financeItemDAO.queryDistinctStafferId(beginDate,
                endDate, "taxId" + taxBean.getLevel(), taxBean.getId());

            for (String each : queryDistinctStafferId)
            {
                if ( !stafferList.contains(each))
                {
                    stafferList.add(each);
                }
            }

        }
        else
        {
            weiyi = true;

            // 只有一个职员
            stafferList.add(stafferId);
        }

        // 查询每个职员
        for (String eachStafferId : stafferList)
        {
            if (StringTools.isNullOrNone(eachStafferId))
            {
                continue;
            }

            // 查询
            FinanceShowVO show = new FinanceShowVO(1);

            show.setTaxId(taxBean.getId());

            show.setTaxName(taxBean.getName());

            show.setForwardName(FinanceHelper.getForwardName(taxBean));

            StafferBean staffer = stafferDAO.find(eachStafferId);

            if (staffer != null)
            {
                show.setStafferName(staffer.getName());
            }

            // 本期借方/贷方
            ConditionParse condtion = getQueryCondition2(request, user, 0);
            createStafferCondition(taxBean, eachStafferId, condtion);

            long[] sumCurrMoneryByCondition = financeItemDAO.sumMoneryByCondition(condtion);

            show.setCurrInmoney(sumCurrMoneryByCondition[0]);
            show.setShowCurrInmoney(FinanceHelper.longToString(sumCurrMoneryByCondition[0]));

            show.setCurrOutmoney(sumCurrMoneryByCondition[1]);
            show.setShowCurrOutmoney(FinanceHelper.longToString(sumCurrMoneryByCondition[1]));

            // 期初余额
            condtion = getQueryCondition2(request, user, 1);

            createStafferCondition(taxBean, eachStafferId, condtion);

            FinanceItemVO head = sumHeadInner(null, condtion, taxBean);

            show.setBeginAllmoney(head.getLastmoney());
            show.setShowBeginAllmoney(FinanceHelper.longToString(head.getLastmoney()));

            // 当前累计(从当年1月到选择的结束日期)
            condtion = getQueryCondition2(request, user, 2);
            createStafferCondition(taxBean, eachStafferId, condtion);

            long[] sumAllMoneryByCondition = financeItemDAO.sumMoneryByCondition(condtion);

            show.setAllInmoney(sumAllMoneryByCondition[0]);
            show.setShowAllInmoney(FinanceHelper.longToString(sumAllMoneryByCondition[0]));
            show.setAllOutmoney(sumAllMoneryByCondition[1]);
            show.setShowAllOutmoney(FinanceHelper.longToString(sumAllMoneryByCondition[1]));

            // 期末余额
            long currentLast = 0L;

            if (taxBean.getForward() == TaxConstanst.TAX_FORWARD_IN)
            {
                currentLast = sumCurrMoneryByCondition[0] - sumCurrMoneryByCondition[1];
            }
            else
            {
                currentLast = sumCurrMoneryByCondition[1] - sumCurrMoneryByCondition[0];
            }

            show.setLastmoney(head.getLastmoney() + currentLast);
            show.setShowLastmoney(FinanceHelper.longToString(head.getLastmoney() + currentLast));

            // 三个为0的数据没有意义
            if (weiyi
                || ! (show.getBeginAllmoney() == 0 && show.getCurrInmoney() == 0 && show
                    .getCurrOutmoney() == 0))
            {
                showList.add(show);
            }
        }

        if (isTotal)
        {
            // 合计
            FinanceShowVO total = new FinanceShowVO();

            total.setTaxId("合计");

            total.setForwardName(FinanceHelper.getForwardName(taxBean));

            for (FinanceShowVO financeShowVO : showList)
            {
                total.setBeginAllmoney(financeShowVO.getBeginAllmoney() + total.getBeginAllmoney());

                total.setCurrInmoney(financeShowVO.getCurrInmoney() + total.getCurrInmoney());
                total.setCurrOutmoney(financeShowVO.getCurrOutmoney() + total.getCurrOutmoney());

                total.setAllInmoney(financeShowVO.getAllInmoney() + total.getAllInmoney());
                total.setAllOutmoney(financeShowVO.getAllOutmoney() + total.getAllOutmoney());

                total.setLastmoney(financeShowVO.getLastmoney() + total.getLastmoney());
            }

            total.setShowBeginAllmoney(FinanceHelper.longToString(total.getBeginAllmoney()));
            total.setShowCurrInmoney(FinanceHelper.longToString(total.getCurrInmoney()));
            total.setShowCurrOutmoney(FinanceHelper.longToString(total.getCurrOutmoney()));

            total.setShowAllInmoney(FinanceHelper.longToString(total.getAllInmoney()));
            total.setShowAllOutmoney(FinanceHelper.longToString(total.getAllOutmoney()));

            total.setShowLastmoney(FinanceHelper.longToString(total.getLastmoney()));

            showList.add(total);
        }

        return showList;
    }

    /**
     * processStafferAndUnitLastQuery 职员,单位查询
     * @param request
     * @param user
     * @param taxBean
     * @param isTotal
     * @return
     * @throws MYException
     */
    private List<FinanceShowVO> processStafferAndUnitLastQuery(HttpServletRequest request, User user,
            TaxVO taxBean, boolean isTotal)
    throws MYException
    {
        List<FinanceShowVO> showList = new LinkedList<FinanceShowVO>();

        request.getSession().setAttribute("resultList_2", showList);

        // 这里的查询分为职员下所有单位的查询,或者查询指定的一个单位

        String beginDate = request.getParameter("beginDate");

        String endDate = request.getParameter("endDate");
        
        // 查询人员
        List<StafferUnitVO> voList = financeItemDAO.queryDistinctStafferIdAndUnitId(beginDate, endDate, 
                "taxId" + taxBean.getLevel(), taxBean.getId());      
                
        Map<String, String> map = new HashMap<String, String>();
        
        // 查询每个职员
        for (StafferUnitVO eachVO : voList)
        {
            String stafferId = eachVO.getStafferId();
            
            if (StringTools.isNullOrNone(stafferId))
            {
                continue;
            }

            for (StafferUnitVO eachVO1 : voList)
            {
                String unitId = eachVO1.getUnitId();
                
                if (StringTools.isNullOrNone(unitId))
                {
                    continue;
                }

                String stafferId1 = eachVO1.getStafferId();
                
                if (!stafferId.equals(stafferId1))
                {
                    continue;
                }
                
                String key = stafferId + unitId;
                
                if (map.containsKey(key))
                {
                    continue;
                }
                else
                {
                    map.put(key, key);
                }
                
                // 查询
                FinanceShowVO show = new FinanceShowVO(1);

                show.setTaxId(taxBean.getId());

                show.setTaxName(taxBean.getName());

                show.setForwardName(FinanceHelper.getForwardName(taxBean));

                StafferBean staffer = stafferDAO.find(stafferId);

                if (staffer != null)
                {
                    show.setStafferName(staffer.getName());
                }

                UnitViewBean unitBean = unitViewDAO.find(unitId);
                
                if (null != unitBean)
                {
                    show.setUnitName(unitBean.getName());
                }
                
                // 本期借方/贷方
                ConditionParse condtion = getQueryCondition2(request, user, 0);
                createStafferAndUnitCondition(taxBean, stafferId, unitId, condtion);

                long[] sumCurrMoneryByCondition = financeItemDAO.sumMoneryByCondition(condtion);

                show.setCurrInmoney(sumCurrMoneryByCondition[0]);
                show.setShowCurrInmoney(FinanceHelper.longToString(sumCurrMoneryByCondition[0]));

                show.setCurrOutmoney(sumCurrMoneryByCondition[1]);
                show.setShowCurrOutmoney(FinanceHelper.longToString(sumCurrMoneryByCondition[1]));

                // 期初余额
                condtion = getQueryCondition2(request, user, 1);
                
                createStafferAndUnitCondition(taxBean, stafferId, unitId, condtion);

                FinanceItemVO head = sumHeadInner(null, condtion, taxBean);

                show.setBeginAllmoney(head.getLastmoney());
                show.setShowBeginAllmoney(FinanceHelper.longToString(head.getLastmoney()));

                // 当前累计(从当年1月到选择的结束日期)
                condtion = getQueryCondition2(request, user, 2);                
                createStafferAndUnitCondition(taxBean, stafferId, unitId, condtion);

                long[] sumAllMoneryByCondition = financeItemDAO.sumMoneryByCondition(condtion);

                show.setAllInmoney(sumAllMoneryByCondition[0]);
                show.setShowAllInmoney(FinanceHelper.longToString(sumAllMoneryByCondition[0]));
                show.setAllOutmoney(sumAllMoneryByCondition[1]);
                show.setShowAllOutmoney(FinanceHelper.longToString(sumAllMoneryByCondition[1]));

                // 期末余额
                long currentLast = 0L;

                if (taxBean.getForward() == TaxConstanst.TAX_FORWARD_IN)
                {
                    currentLast = sumCurrMoneryByCondition[0] - sumCurrMoneryByCondition[1];
                }
                else
                {
                    currentLast = sumCurrMoneryByCondition[1] - sumCurrMoneryByCondition[0];
                }

                show.setLastmoney(head.getLastmoney() + currentLast);
                show.setShowLastmoney(FinanceHelper.longToString(head.getLastmoney() + currentLast));

                // 三个为0的数据没有意义
                if (!(show.getBeginAllmoney() == 0 && show.getCurrInmoney() == 0 && show.getCurrOutmoney() == 0))
                {
                    showList.add(show);
                }
            }
            
        }

        if (isTotal)
        {
            // 合计
            FinanceShowVO total = new FinanceShowVO();

            total.setTaxId("合计");

            total.setForwardName(FinanceHelper.getForwardName(taxBean));

            for (FinanceShowVO financeShowVO : showList)
            {
                total.setBeginAllmoney(financeShowVO.getBeginAllmoney() + total.getBeginAllmoney());

                total.setCurrInmoney(financeShowVO.getCurrInmoney() + total.getCurrInmoney());
                total.setCurrOutmoney(financeShowVO.getCurrOutmoney() + total.getCurrOutmoney());

                total.setAllInmoney(financeShowVO.getAllInmoney() + total.getAllInmoney());
                total.setAllOutmoney(financeShowVO.getAllOutmoney() + total.getAllOutmoney());

                total.setLastmoney(financeShowVO.getLastmoney() + total.getLastmoney());
            }

            total.setShowBeginAllmoney(FinanceHelper.longToString(total.getBeginAllmoney()));
            total.setShowCurrInmoney(FinanceHelper.longToString(total.getCurrInmoney()));
            total.setShowCurrOutmoney(FinanceHelper.longToString(total.getCurrOutmoney()));

            total.setShowAllInmoney(FinanceHelper.longToString(total.getAllInmoney()));
            total.setShowAllOutmoney(FinanceHelper.longToString(total.getAllOutmoney()));

            total.setShowLastmoney(FinanceHelper.longToString(total.getLastmoney()));

            showList.add(total);
        }

        return showList;
    }
    
    /**
     * createUnitCondition
     * 
     * @param taxBean
     * @param eachUnitId
     * @param condtion
     */
    private void createUnitCondition(TaxVO taxBean, String eachUnitId, ConditionParse condtion)
    {
        condtion.addCondition("FinanceItemBean.taxId" + taxBean.getLevel(), "=", taxBean.getId());

        condtion.addCondition("FinanceItemBean.unitId", "=", eachUnitId);
    }

    /**
     * createStafferCondition
     * 
     * @param taxBean
     * @param stafferId
     * @param condtion
     */
    private void createStafferCondition(TaxVO taxBean, String stafferId, ConditionParse condtion)
    {
        condtion.addCondition("FinanceItemBean.taxId" + taxBean.getLevel(), "=", taxBean.getId());

        condtion.addCondition("FinanceItemBean.stafferId", "=", stafferId);
    }

    private void createStafferAndUnitCondition(TaxVO taxBean, String stafferId, String unitId, ConditionParse condtion)
    {
        condtion.addCondition("FinanceItemBean.taxId" + taxBean.getLevel(), "=", taxBean.getId());

        condtion.addCondition("FinanceItemBean.stafferId", "=", stafferId);
        
        condtion.addCondition("FinanceItemBean.unitId", "=", unitId);
    }
    
    /**
     * fillItemVO
     * 
     * @param item
     */
    protected void fillItemVO(FinanceItemVO item)
    {
        TaxBean tax = taxDAO.find(item.getTaxId());

        item.setForward(tax.getForward());

        if (tax.getDepartment() == TaxConstanst.TAX_CHECK_YES
            || !StringTools.isNullOrNone(item.getDepartmentId()))
        {
            PrincipalshipBean depart = principalshipDAO.find(item.getDepartmentId());

            if (depart != null)
            {
                item.setDepartmentName(depart.getName());
            }
        }

        if (tax.getStaffer() == TaxConstanst.TAX_CHECK_YES
            || !StringTools.isNullOrNone(item.getStafferId()))
        {
            StafferBean sb = stafferDAO.find(item.getStafferId());

            if (sb != null)
            {
                item.setStafferName(sb.getName());
            }
        }

        if (tax.getUnit() == TaxConstanst.TAX_CHECK_YES
            || !StringTools.isNullOrNone(item.getUnitId()))
        {
            UnitBean unit = unitDAO.find(item.getUnitId());

            if (unit != null)
            {
                item.setUnitName(unit.getName());
            }
        }

        if (tax.getProduct() == TaxConstanst.TAX_CHECK_YES
            || !StringTools.isNullOrNone(item.getProductId()))
        {
            ProductBean product = productDAO.find(item.getProductId());

            if (product != null)
            {
                item.setProductName(product.getName());
                item.setProductCode(product.getCode());
            }
        }

        if (tax.getDepot() == TaxConstanst.TAX_CHECK_YES
            || !StringTools.isNullOrNone(item.getDepotId()))
        {
            DepotBean depot = depotDAO.find(item.getDepotId());

            if (depot != null)
            {
                item.setDepotName(depot.getName());
            }
        }

        if (tax.getDuty() == TaxConstanst.TAX_CHECK_YES
            || !StringTools.isNullOrNone(item.getDuty2Id()))
        {
            DutyBean duty2 = dutyDAO.find(item.getDuty2Id());

            if (duty2 != null)
            {
                item.setDuty2Name(duty2.getName());
            }
        }

        item.getShowInmoney();
        item.getShowOutmoney();

        long last = 0;

        if (tax.getForward() == TaxConstanst.TAX_FORWARD_IN)
        {
            last = item.getInmoney() - item.getOutmoney();
            item.setForwardName("借");
        }
        else
        {
            last = item.getOutmoney() - item.getInmoney();
            item.setForwardName("贷");
        }

        item.setLastmoney(last);

        item.setShowLastmoney(FinanceHelper.longToString(last));

        item.getShowChineseInmoney();
        item.getShowChineseOutmoney();
        item.getShowChineseLastmoney();
    }

    /**
     * 导入凭证
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward importFinance(ActionMapping mapping, ActionForm form,
                                       HttpServletRequest request, HttpServletResponse response)
        throws ServletException
    {
        User user = Helper.getUser(request);

        RequestDataStream rds = new RequestDataStream(request);

        try
        {
            rds.parser();
        }
        catch (Exception e1)
        {
            _logger.error(e1, e1);

            request.setAttribute(KeyConstant.ERROR_MESSAGE, "解析失败");

            return mapping.findForward("importFinance");
        }

        if ( !rds.haveStream())
        {
            request.setAttribute(KeyConstant.ERROR_MESSAGE, "解析失败");

            return mapping.findForward("importFinance");
        }

        StringBuilder builder = new StringBuilder();

        FinanceBean finance = new FinanceBean();

        finance.setFinanceDate(rds.getParameter("financeDate"));
        finance.setDutyId(rds.getParameter("dutyId"));
        finance.setCreateType(MathTools.parseInt(rds.getParameter("createType")));
        finance.setRefId(rds.getParameter("refId"));

        request.setAttribute("financeDate", rds.getParameter("financeDate"));
        request.setAttribute("dutyId", rds.getParameter("dutyId"));
        request.setAttribute("createType", rds.getParameter("createType"));

        String pareId = SequenceTools.getSequence();

        ReaderFile reader = ReadeFileFactory.getXLSReader();

        try
        {
            reader.readFile(rds.getUniqueInputStream());

            List<FinanceItemBean> itemList = new ArrayList();

            finance.setItemList(itemList);

            String stafferId = "";

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

                if (StringTools.isNullOrNone(stafferId))
                {
                    stafferId = getStafferId(obj);

                    finance.setCreaterId(stafferId);
                }

                int currentNumber = reader.getCurrentLineNumber();

                if (obj.length >= 6 && !StringTools.isNullOrNone(stafferId))
                {
                    innerAdd(user, builder, obj, stafferId, currentNumber, itemList, finance,
                        pareId);
                }
                else
                {
                    builder
                        .append("第[" + currentNumber + "]错误:")
                        .append("数据长度不足6格,或者制单人错误")
                        .append("<br>");
                }
            }

            if (builder.length() > 0)
            {
                request.setAttribute(KeyConstant.ERROR_MESSAGE, builder.toString());

                return mapping.findForward("importFinance");
            }

            financeManager.addFinanceBean(user, finance);
        }
        catch (Exception e)
        {
            _logger.error(e, e);

            request.setAttribute(KeyConstant.ERROR_MESSAGE, e.toString());

            return mapping.findForward("importFinance");
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

        StringBuilder result = new StringBuilder();

        result.append("导入成功:" + finance.getId());

        result.append(builder.toString());

        request.setAttribute(KeyConstant.MESSAGE, result.toString());

        return mapping.findForward("importFinance");
    }

    /**
     * handleStafferId
     * 
     * @param obj
     * @param stafferId
     * @return
     */
    private String getStafferId(String[] obj)
    {
        String stafferId = "";

        if ( !StringTools.isNullOrNone(obj[1]))
        {
            StafferBean sb = stafferDAO.findyStafferByName(obj[1]);

            if (sb != null)
            {
                stafferId = sb.getId();
            }
        }

        return stafferId;
    }

    /**
     * @param user
     * @param builder
     * @param obj
     * @param stafferId
     * @param currentNumber
     * @return
     */
    private boolean innerAdd(User user, StringBuilder builder, String[] obj, String stafferId,
                             int currentNumber, List<FinanceItemBean> itemList,
                             FinanceBean finance, String pareId)
    {
        boolean addSucess = false;

        try
        {
            FinanceItemBean item = new FinanceItemBean();

            item.setFinanceDate(finance.getFinanceDate());
            item.setCreateType(TaxConstanst.FINANCE_CREATETYPE_HAND);
            item.setDutyId(finance.getDutyId());
            item.setPareId(pareId);

            // 凭证字号
            finance.setRefChecks(obj[2]);

            // description
            item.setDescription(obj[3]);

            String taxId = parserTax(obj[4]);

            TaxBean tax = taxDAO.find(taxId);

            if (tax == null)
            {
                builder
                    .append("<font color=red>第[" + currentNumber + "]行错误:")
                    .append("科目不存在")
                    .append("</font><br>");

                return false;
            }

            item.setTaxId(tax.getId());

            if ( !StringTools.isNullOrNone(obj[5]))
            {
                item.setInmoney(FinanceHelper.doubleToLong(obj[5].replace(",", "")));
            }

            if ( !StringTools.isNullOrNone(obj[6]))
            {
                item.setOutmoney(FinanceHelper.doubleToLong(obj[6].replace(",", "")));
            }

            int index = 7;
            // 产品借
            if ( !StringTools.isNullOrNone(obj[index]))
            {
                item.setProductAmountIn(MathTools.parseInt(obj[index]));
            }

            // 产品贷
            index++ ;
            if ( !StringTools.isNullOrNone(obj[index]))
            {
                item.setProductAmountOut(MathTools.parseInt(obj[index]));
            }

            index++ ;
            if ( !StringTools.isNullOrNone(obj[index]))
            {
                String postName = obj[index];

                PrincipalshipBean pri = principalshipDAO.findUniqueByName(postName);

                if (pri != null)
                {
                    item.setDepartmentId(pri.getId());
                }
            }

            index++ ;
            if ( !StringTools.isNullOrNone(obj[index]))
            {
                String name = obj[index];

                StafferBean bean = stafferDAO.findByUnique(name);

                if (bean == null)
                {
                    builder
                        .append("<font color=red>第[" + currentNumber + "]行错误:")
                        .append("职员不存在")
                        .append("</font><br>");

                    return false;
                }

                item.setStafferId(bean.getId());

                if (StringTools.isNullOrNone(item.getDepartmentId()))
                {
                    item.setDepartmentId(bean.getPrincipalshipId());
                }
            }

            index++ ;
            if ( !StringTools.isNullOrNone(obj[index]))
            {
                String name = obj[index];

                UnitBean bean = unitDAO.findByUnique(name);

                if (bean == null)
                {
                    builder
                        .append("<font color=red>第[" + currentNumber + "]行错误:")
                        .append("单位不存在")
                        .append("</font><br>");

                    return false;
                }

                item.setUnitId(bean.getId());
            }

            index++ ;
            if ( !StringTools.isNullOrNone(obj[index]))
            {
                String name = obj[index];

                ProductBean bean = productDAO.findByName(name);

                if (bean == null)
                {
                    builder
                        .append("<font color=red>第[" + currentNumber + "]行错误:")
                        .append("产品不存在")
                        .append("</font><br>");

                    return false;
                }

                item.setProductId(bean.getId());
            }

            index++ ;
            if ( !StringTools.isNullOrNone(obj[index]))
            {
                String name = obj[index];

                DepotBean bean = depotDAO.findByUnique(name);

                if (bean == null)
                {
                    builder
                        .append("<font color=red>第[" + currentNumber + "]行错误:")
                        .append("仓区不存在")
                        .append("</font><br>");

                    return false;
                }

                item.setDepotId(bean.getId());
            }

            index++ ;
            if ( !StringTools.isNullOrNone(obj[index]))
            {
                String name = obj[index];

                DutyBean bean = dutyDAO.findByUnique(name);

                if (bean == null)
                {
                    builder
                        .append("<font color=red>第[" + currentNumber + "]行错误:")
                        .append("纳税实体不存在")
                        .append("</font><br>");

                    return false;
                }

                item.setDuty2Id(bean.getId());
            }

            itemList.add(item);

            addSucess = true;
        }
        catch (Exception e)
        {
            _logger.error(e, e);

            addSucess = false;

            builder
                .append("<font color=red>第[" + currentNumber + "]行错误:")
                .append(e.getMessage())
                .append("</font><br>");
        }

        return addSucess;
    }

    private String parserTax(String str)
    {
        int length = 0;

        for (int i = 0; i < str.length(); i++ )
        {
            if (str.charAt(i) > 128)
            {
                length = i;
                break;
            }
        }

        return str.substring(0, length).trim();
    }

    private String[] fillObj(String[] obj)
    {
        String[] result = new String[15];

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
     * @return the financeReferDAO
     */
    public FinanceReferDAO getFinanceReferDAO()
    {
        return financeReferDAO;
    }

    /**
     * @param financeReferDAO
     *            the financeReferDAO to set
     */
    public void setFinanceReferDAO(FinanceReferDAO financeReferDAO)
    {
        this.financeReferDAO = financeReferDAO;
    }
}
