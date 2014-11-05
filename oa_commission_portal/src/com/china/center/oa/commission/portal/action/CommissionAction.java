package com.china.center.oa.commission.portal.action;


import java.io.IOException;
import java.io.OutputStream;
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
import com.center.china.osgi.publics.file.writer.WriteFile;
import com.center.china.osgi.publics.file.writer.WriteFileFactory;
import com.china.center.actionhelper.common.ActionTools;
import com.china.center.actionhelper.common.JSONTools;
import com.china.center.actionhelper.common.KeyConstant;
import com.china.center.actionhelper.common.OldPageSeparateTools;
import com.china.center.actionhelper.json.AjaxResult;
import com.china.center.common.MYException;
import com.china.center.jdbc.util.ConditionParse;
import com.china.center.jdbc.util.PageSeparate;
import com.china.center.oa.commission.bean.CommissionBean;
import com.china.center.oa.commission.bean.CommissionFreezeBean;
import com.china.center.oa.commission.bean.ExternalAssessBean;
import com.china.center.oa.commission.bean.ExternalStafferYearBean;
import com.china.center.oa.commission.dao.BadDropAssessDetailDAO;
import com.china.center.oa.commission.dao.CommissionDAO;
import com.china.center.oa.commission.dao.CommissionFreezeDAO;
import com.china.center.oa.commission.dao.CommissionMonthDAO;
import com.china.center.oa.commission.dao.CurFeeDetailDAO;
import com.china.center.oa.commission.dao.CurMoneyDetailDAO;
import com.china.center.oa.commission.dao.CurProfitDAO;
import com.china.center.oa.commission.dao.ExternalAssessDAO;
import com.china.center.oa.commission.dao.ExternalStafferYearDAO;
import com.china.center.oa.commission.dao.FinanceFeeStatsDetailDAO;
import com.china.center.oa.commission.facade.CommissionFacade;
import com.china.center.oa.commission.manager.CommissionManager;
import com.china.center.oa.commission.vo.BadDropAssessDetailVO;
import com.china.center.oa.commission.vo.CurFeeDetailVO;
import com.china.center.oa.commission.vo.CurMoneyDetailVO;
import com.china.center.oa.commission.vo.ExternalAssessVO;
import com.china.center.oa.commission.vo.FinanceFeeStatsDetailVO;
import com.china.center.oa.commission.wrap.ProfitVSStafferWrap;
import com.china.center.oa.publics.Helper;
import com.china.center.oa.publics.bean.StafferBean;
import com.china.center.oa.publics.constant.PublicConstant;
import com.china.center.oa.publics.dao.StafferDAO;
import com.china.center.tools.MathTools;
import com.china.center.tools.RequestDataStream;
import com.china.center.tools.StringTools;
import com.china.center.tools.TimeTools;
import com.china.center.tools.WriteFileBuffer;


/**
 * 
 * 提成
 *
 * @author fangliwen 2012-11-12
 */
public class CommissionAction extends DispatchAction
{
    private final Log _logger = LogFactory.getLog(getClass());
    
    private CommissionFacade commissionFacade = null;
    
    private CommissionMonthDAO commissionMonthDAO = null;
    
    private CommissionDAO commissionDAO = null;
    
    private CurMoneyDetailDAO curMoneyDetailDAO = null;
    
    private CurFeeDetailDAO curFeeDetailDAO = null;
    
    private BadDropAssessDetailDAO badDropAssessDetailDAO = null;
    
    private FinanceFeeStatsDetailDAO financeFeeStatsDetailDAO = null;
    
    private ExternalAssessDAO externalAssessDAO = null;
    
    private StafferDAO stafferDAO = null;
    
    private ExternalStafferYearDAO externalStafferYearDAO = null;
    
    private CommissionManager commissionManager = null;
    
    private CurProfitDAO curProfitDAO = null;
    
    private CommissionFreezeDAO commissionFreezeDAO = null;
    
    private static final String QUERYCOMMISSION = "queryCommission";
    
    private static final String QUERYSELFCOMMISSION = "querySelfCommission";
    
    private static final String QUERYSELFCOMMISSION2 = "querySelfCommission2";
    
    private static final String QUERYSELFCOMMISSION3 = "querySelfCommission3";
    
    private static final String QUERYSELFCOMMISSION4 = "querySelfCommission4";
    
    private static final String QUERYSELFCOMMISSION5 = "querySelfCommission5";
    
    private static final String QUERYSELFCOMMISSION6 = "querySelfCommission6";
    
    private static final String DETAILSELFCOMMISSIONKEY = "detailSelfCommission";
    
    private static final String QUERYEXTERNALSTAFFER = "queryExternalStaffer";
    
    private static final String QUERYEXTERNALASSESS = "queryExternalAssess";
    
    public CommissionAction()
    {
        
    }
    
    /**
     * 增加新的提成计算操作
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward addCommission(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) 
    throws ServletException
    {
        
        User user = Helper.getUser(request);
        
        try
        {
            commissionFacade.addCommission(user.getId());
            
            request.setAttribute(KeyConstant.MESSAGE, "成功操作");
        }
        catch(MYException e)
        {
            _logger.warn(e, e);

            request.setAttribute(KeyConstant.ERROR_MESSAGE, "操作失败:" + e.getMessage());
        }
        
        return mapping.findForward("queryCommission");
    }

    /**
     * 撤销提成计算
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
   public ActionForward deleteCommission(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
   throws ServletException
   {
       AjaxResult ajax = new AjaxResult();
       
       String id = request.getParameter("id");
       
       User user = Helper.getUser(request);
       
       try
       {
           commissionFacade.deleteCommission(user.getId(), id);
           
           ajax.setSuccess("成功撤销");
       }
       catch(MYException e)
       {
           _logger.warn(e, e);

           ajax.setError("撤销失败:" + e.getMessage());
       }
       
       return JSONTools.writeResponse(response, ajax);
   }
    
    /**
     * 提成计算概要查询
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward queryCommission(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
    throws ServletException
    {
        ConditionParse condtion = new ConditionParse();

        condtion.addWhereStr();
        
        ActionTools.processJSONQueryCondition(QUERYCOMMISSION, request, condtion);
        
        String jsonstr = ActionTools.queryVOByJSONAndToString(QUERYCOMMISSION, request, condtion,
            this.commissionMonthDAO);

        return JSONTools.writeResponse(response, jsonstr);
    }
    
    /**
     * 提成结果查询
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward querySelfCommission(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
    throws ServletException
    {
        
        User user = Helper.getUser(request);
        
        String stafferId = user.getStafferId();

        ConditionParse condtion = new ConditionParse();

        condtion.addWhereStr();
        
        condtion.addCondition("CommissionBean.stafferId", "=", stafferId);
        
        ActionTools.processJSONQueryCondition(QUERYSELFCOMMISSION, request, condtion);
        
        String jsonstr = ActionTools.queryVOByJSONAndToString(QUERYSELFCOMMISSION, request, condtion,
            this.commissionDAO);

        return JSONTools.writeResponse(response, jsonstr);
    }
    
    /**
     * 提成冻结信息
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward findCommission0(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
    throws ServletException
    {
        
        String id = request.getParameter("id");

        CommissionBean bean = commissionDAO.find(id);
        
        if (null == bean)
        {
            request.setAttribute(KeyConstant.ERROR_MESSAGE, "数据错误");
            
            return mapping.findForward("querySelfCommission");
        }        
        
        request.setAttribute("bean", bean);
        
        CommissionFreezeBean freezeBean = commissionFreezeDAO.findByMonthAndStaffer(bean.getMonth(), bean.getStafferId());
        
        if (null == freezeBean)
            freezeBean = new CommissionFreezeBean();
        
        request.setAttribute("freezeBean", freezeBean);
        
        return mapping.findForward(DETAILSELFCOMMISSIONKEY+"0");        
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
    public ActionForward findCommission(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
    throws ServletException
    {
        
        String id = request.getParameter("id");

        CommissionBean bean = commissionDAO.find(id);
        
        if (null == bean)
        {
            request.setAttribute(KeyConstant.ERROR_MESSAGE, "数据错误");
            
            return mapping.findForward("querySelfCommission");
        }        
        
        request.setAttribute("bean", bean);
        
        return mapping.findForward(DETAILSELFCOMMISSIONKEY+"1");        
    }
    
    /**
     * 毛利明细
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward findCommission2(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
    throws ServletException
    {
        String id = request.getParameter("id");
        
        String type = request.getParameter("type");
        
        if (StringTools.isNullOrNone(type))
        {
            type = "2";
        }
        
        CommissionBean bean = commissionDAO.find(id);
        
        if (null == bean)
        {
            request.setAttribute(KeyConstant.ERROR_MESSAGE, "数据错误");
            
            return mapping.findForward("querySelfCommission");
        }
        
        String monthKey = bean.getMonth();
        String stafferId = bean.getStafferId();
        
        ConditionParse con = new ConditionParse();
        
        con.addWhereStr();
        
        con.addCondition("month", "=", monthKey);
        
        con.addCondition("stafferId", "=", stafferId);
              
        con.addCondition(" order by outid");
        
        List<CurMoneyDetailVO> beanList = null;
        
        try
        {
            if (OldPageSeparateTools.isFirstLoad(request))
            {
                Object attribute = request.getAttribute("holdCondition");

                ConditionParse condtion = null;

                if (attribute == null)
                {
                    condtion = con;
                }
                else
                {
                    condtion = OldPageSeparateTools.getCondition(request, QUERYSELFCOMMISSION2);
                }
                
                int tatol = curMoneyDetailDAO.countVOByCondition(condtion.toString());

                PageSeparate page = new PageSeparate(tatol, PublicConstant.PAGE_SIZE - 5);
                
                OldPageSeparateTools.initPageSeparate(condtion, page, request, QUERYSELFCOMMISSION2);
                
                beanList = curMoneyDetailDAO.queryEntityVOsByCondition(condtion, page);
                                
            }
            else
            {
                OldPageSeparateTools.processSeparate(request, QUERYSELFCOMMISSION2);

                beanList = curMoneyDetailDAO.queryEntityVOsByCondition(OldPageSeparateTools.getCondition(request,
                        QUERYSELFCOMMISSION2), OldPageSeparateTools.getPageSeparate(request, QUERYSELFCOMMISSION2));
            }
        }
        catch (Exception e)
        {
            request.setAttribute(KeyConstant.ERROR_MESSAGE, "查询单据失败");

            _logger.error(e, e);

            return mapping.findForward("error");
        }
                
        request.setAttribute("beanList", beanList);
        
        request.setAttribute("id", id);
        
        request.setAttribute("type", type);
        
        return mapping.findForward(DETAILSELFCOMMISSIONKEY+type);
      
    }
    
    /**
     * 费用明细
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward findCommission3(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
    throws ServletException
    {
        String id = request.getParameter("id");
        
        String type = request.getParameter("type");
        
        if (StringTools.isNullOrNone(type))
        {
            type = "3";
        }
        
        CommissionBean bean = commissionDAO.find(id);
        
        if (null == bean)
        {
            request.setAttribute(KeyConstant.ERROR_MESSAGE, "数据错误");
            
            return mapping.findForward("querySelfCommission");
        }
        
        String monthKey = bean.getMonth();
        String stafferId = bean.getStafferId();
        
        ConditionParse con = new ConditionParse();
        
        con.addWhereStr();
        
        con.addCondition("month", "=", monthKey);
        
        con.addCondition("stafferId", "=", stafferId);
              
        List<CurFeeDetailVO> beanList = null;
        
        try
        {
            if(OldPageSeparateTools.isFirstLoad(request))
            {
                Object attribute = request.getAttribute("holdCondition");

                ConditionParse condtion = null;

                if (attribute == null)
                {
                    condtion = con;
                }
                else
                {
                    condtion = OldPageSeparateTools.getCondition(request, QUERYSELFCOMMISSION3);
                }

                int tatol = curFeeDetailDAO.countVOByCondition(condtion.toString());

                PageSeparate page = new PageSeparate(tatol, PublicConstant.PAGE_SIZE - 5);

                OldPageSeparateTools.initPageSeparate(condtion, page, request, QUERYSELFCOMMISSION3);

                beanList = curFeeDetailDAO.queryEntityVOsByCondition(condtion, page);
                                
            }
            else
            {
                OldPageSeparateTools.processSeparate(request, QUERYSELFCOMMISSION3);

                beanList = curFeeDetailDAO.queryEntityVOsByCondition(OldPageSeparateTools.getCondition(request,
                        QUERYSELFCOMMISSION3), OldPageSeparateTools.getPageSeparate(request, QUERYSELFCOMMISSION3));
            }
        }
        catch (Exception e)
        {
            request.setAttribute(KeyConstant.ERROR_MESSAGE, "查询单据失败");

            _logger.error(e, e);

            return mapping.findForward("error");
        }
                
        request.setAttribute("beanList", beanList);
                
        request.setAttribute("id", id);
        
        request.setAttribute("type", type);
        
        return mapping.findForward(DETAILSELFCOMMISSIONKEY+type);
      
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
    public ActionForward findCommission4(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
    throws ServletException
    {
        String id = request.getParameter("id");
        
        String type = request.getParameter("type");
        
        if (StringTools.isNullOrNone(type))
        {
            type = "4";
        }
        
        CommissionBean bean = commissionDAO.find(id);
        
        if (null == bean)
        {
            request.setAttribute(KeyConstant.ERROR_MESSAGE, "数据错误");
            
            return mapping.findForward("querySelfCommission");
        }
        
        String monthKey = bean.getMonth();
        String stafferId = bean.getStafferId();
        
        ConditionParse con = new ConditionParse();
        
        con.addWhereStr();
        
        con.addCondition("month", "=", monthKey);
        
        con.addCondition("stafferId", "=", stafferId);
              
        List<ExternalAssessVO> beanList = null;
        
        try
        {
            if(OldPageSeparateTools.isFirstLoad(request))
            {
                Object attribute = request.getAttribute("holdCondition");

                ConditionParse condtion = null;

                if (attribute == null)
                {
                    condtion = con;
                }
                else
                {
                    condtion = OldPageSeparateTools.getCondition(request, QUERYSELFCOMMISSION4);
                }

                int tatol = curFeeDetailDAO.countVOByCondition(condtion.toString());

                PageSeparate page = new PageSeparate(tatol, PublicConstant.PAGE_SIZE - 5);

                OldPageSeparateTools.initPageSeparate(condtion, page, request, QUERYSELFCOMMISSION4);

                beanList = externalAssessDAO.queryEntityVOsByCondition(condtion, page);
                                
            }
            else
            {
                OldPageSeparateTools.processSeparate(request, QUERYSELFCOMMISSION4);

                beanList = externalAssessDAO.queryEntityVOsByCondition(OldPageSeparateTools.getCondition(request,
                        QUERYSELFCOMMISSION4), OldPageSeparateTools.getPageSeparate(request, QUERYSELFCOMMISSION4));
            }
        }
        catch (Exception e)
        {
            request.setAttribute(KeyConstant.ERROR_MESSAGE, "查询单据失败");

            _logger.error(e, e);

            return mapping.findForward("error");
        }
                
        request.setAttribute("beanList", beanList);
                
        request.setAttribute("id", id);
        
        request.setAttribute("type", type);
        
        return mapping.findForward(DETAILSELFCOMMISSIONKEY+type);
      
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
    public ActionForward findCommission5(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
    throws ServletException
    {
        String id = request.getParameter("id");
        
        String type = request.getParameter("type");
        
        if (StringTools.isNullOrNone(type))
        {
            type = "5";
        }
        
        CommissionBean bean = commissionDAO.find(id);
        
        if (null == bean)
        {
            request.setAttribute(KeyConstant.ERROR_MESSAGE, "数据错误");
            
            return mapping.findForward("querySelfCommission");
        }
        
        String monthKey = bean.getMonth();
        String stafferId = bean.getStafferId();
        
        ConditionParse con = new ConditionParse();
        
        con.addWhereStr();
        
        con.addCondition("month", "=", monthKey);
        
        con.addCondition("stafferId", "=", stafferId);
              
        List<BadDropAssessDetailVO> beanList = null;
        
        try
        {
            if (OldPageSeparateTools.isFirstLoad(request))
            {
                Object attribute = request.getAttribute("holdCondition");

                ConditionParse condtion = null;

                if (attribute == null)
                {
                    condtion = con;
                }
                else
                {
                    condtion = OldPageSeparateTools.getCondition(request, QUERYSELFCOMMISSION5);
                }

                int tatol = badDropAssessDetailDAO.countVOByCondition(condtion.toString());

                PageSeparate page = new PageSeparate(tatol, PublicConstant.PAGE_SIZE - 5);

                OldPageSeparateTools.initPageSeparate(condtion, page, request, QUERYSELFCOMMISSION5);

                beanList = badDropAssessDetailDAO.queryEntityVOsByCondition(condtion, page);
                                
            }
            else
            {
                OldPageSeparateTools.processSeparate(request, QUERYSELFCOMMISSION5);

                beanList = badDropAssessDetailDAO.queryEntityVOsByCondition(OldPageSeparateTools.getCondition(request,
                        QUERYSELFCOMMISSION5), OldPageSeparateTools.getPageSeparate(request, QUERYSELFCOMMISSION5));
            }
        }
        catch (Exception e)
        {
            request.setAttribute(KeyConstant.ERROR_MESSAGE, "查询单据失败");

            _logger.error(e, e);

            return mapping.findForward("error");
        }
                
        request.setAttribute("beanList", beanList);
        
        request.setAttribute("id", id);
        
        request.setAttribute("type", type);
        
        return mapping.findForward(DETAILSELFCOMMISSIONKEY+type);
      
    }
    
    /**
     * 资金占用费
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward findCommission6(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
    throws ServletException
    {
        String id = request.getParameter("id");
        
        String type = request.getParameter("type");
        
        if (StringTools.isNullOrNone(type))
        {
            type = "6";
        }
        
        CommissionBean bean = commissionDAO.find(id);
        
        if (null == bean)
        {
            request.setAttribute(KeyConstant.ERROR_MESSAGE, "数据错误");
            
            return mapping.findForward("querySelfCommission");
        }
        
        String monthKey = bean.getMonth();
        String stafferId = bean.getStafferId();
        
        ConditionParse con = new ConditionParse();
        
        con.addWhereStr();
        
        con.addCondition("month", "=", monthKey);
        
        con.addCondition("stafferId", "=", stafferId);
        
        con.addIntCondition("used", "=", 99);

        con.addCondition(" order by outId, type");
        
        List<FinanceFeeStatsDetailVO> beanList = null;
        
        try
        {
            if (OldPageSeparateTools.isFirstLoad(request))
            {
                Object attribute = request.getAttribute("holdCondition");

                ConditionParse condtion = null;

                if (attribute == null)
                {
                    condtion = con;
                }
                else
                {
                    condtion = OldPageSeparateTools.getCondition(request, QUERYSELFCOMMISSION6);
                }

                int tatol = financeFeeStatsDetailDAO.countVOByCondition(condtion.toString());

                PageSeparate page = new PageSeparate(tatol, PublicConstant.PAGE_SIZE - 5);

                OldPageSeparateTools.initPageSeparate(condtion, page, request, QUERYSELFCOMMISSION6);

                beanList = financeFeeStatsDetailDAO.queryEntityVOsByCondition(condtion, page);
                                
            }
            else
            {
                OldPageSeparateTools.processSeparate(request, QUERYSELFCOMMISSION6);

                beanList = financeFeeStatsDetailDAO.queryEntityVOsByCondition(OldPageSeparateTools.getCondition(request,
                        QUERYSELFCOMMISSION6), OldPageSeparateTools.getPageSeparate(request, QUERYSELFCOMMISSION6));
            }
        }
        catch (Exception e)
        {
            request.setAttribute(KeyConstant.ERROR_MESSAGE, "查询单据失败");

            _logger.error(e, e);

            return mapping.findForward("error");
        }

        request.setAttribute("beanList", beanList);
                
        request.setAttribute("id", id);
        
        request.setAttribute("type", type);
        
        return mapping.findForward(DETAILSELFCOMMISSIONKEY+type);
      
    }
    
    /**
     * 导入
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward importExternel(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
    throws ServletException
    {
        RequestDataStream rds = new RequestDataStream(request);
        
        boolean importError = false;
        
        List<ExternalStafferYearBean> importItemList1 = new ArrayList<ExternalStafferYearBean>();
        
        List<ExternalAssessBean> importItemList2 = new ArrayList<ExternalAssessBean>(); 
        
        StringBuilder builder = new StringBuilder();
        
        try
        {
            rds.parser();
        }
        catch (Exception e1)
        {
            _logger.error(e1, e1);

            request.setAttribute(KeyConstant.ERROR_MESSAGE, "解析失败");

            return mapping.findForward("importExport");
        }

        if ( !rds.haveStream())
        {
            request.setAttribute(KeyConstant.ERROR_MESSAGE, "解析失败");

            return mapping.findForward("importExport");
        }

        String type = rds.getParameter("type");
        
        ReaderFile reader = ReadeFileFactory.getXLSReader();
        
        try
        {
            reader.readFile(rds.getUniqueInputStream());
            
            // 职员年限
            if (type.equals("0"))
            {
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
                        ExternalStafferYearBean item = new ExternalStafferYearBean();
                        
                        // 年限
                        if ( !StringTools.isNullOrNone(obj[0]))
                        {
                            item.setYears(obj[0]);

                        }
                        
                        // 职员
                        if ( !StringTools.isNullOrNone(obj[1]))
                        {
                            String name = obj[1];

                            StafferBean stafferBean = stafferDAO.findByUnique(name);
                            
                            if (null == stafferBean)
                            {
                                builder
                                .append("<font color=red>第[" + currentNumber + "]行错误:")
                                .append("职员不存在")
                                .append("</font><br>");

                                importError = true;
                            }
                            else
                            {
                                item.setStafferId(stafferBean.getId());
                                
                                item.setStafferName(name);
                            }
                            
                        }
                        
                        importItemList1.add(item);
                        
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
            }
            else
            {
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

                    if (obj.length >= 7 )
                    {
                        ExternalAssessBean item = new ExternalAssessBean();
                        
                        // 月份
                        if ( !StringTools.isNullOrNone(obj[0]))
                        {
                            item.setMonth(obj[0]);

                        }
                        
                        // 职员
                        if ( !StringTools.isNullOrNone(obj[1]))
                        {
                            String name = obj[1];

                            StafferBean stafferBean = stafferDAO.findByUnique(name);
                            
                            if (null == stafferBean)
                            {
                                builder
                                .append("<font color=red>第[" + currentNumber + "]行错误:")
                                .append("职员不存在")
                                .append("</font><br>");

                                importError = true;
                            }
                            else
                            {
                                item.setStafferId(stafferBean.getId());
                            }
                            
                        }
                        
                        // kpi
                        if ( !StringTools.isNullOrNone(obj[2]))
                        {
                            double kpi = MathTools.parseDouble(obj[2]);

                            item.setKpi(kpi);                            
                        }
                        
                        // 冻结比例
                        if ( !StringTools.isNullOrNone(obj[3]))
                        {
                            double ratio = MathTools.parseDouble(obj[3]);

                            item.setRatio(ratio);                            
                        }
                        
                        // 应收成本
                        if ( !StringTools.isNullOrNone(obj[4]))
                        {
                            double cost = MathTools.parseDouble(obj[4]);

                            item.setCost(cost);                            
                        }
                        
                        // 年度KPI返款
                        if ( !StringTools.isNullOrNone(obj[5]))
                        {
                            double yearKpi = MathTools.parseDouble(obj[5]);

                            item.setYearKpi(yearKpi);                            
                        }
                        
                        // 应发提成
                        if ( !StringTools.isNullOrNone(obj[6]))
                        {
                            double shouldCommission = MathTools.parseDouble(obj[6]);

                            item.setShouldCommission(shouldCommission);                            
                        }
                        
                        importItemList2.add(item);
                        
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
            }
            
        }catch (Exception e)
        {
            _logger.error(e, e);
            
            request.setAttribute(KeyConstant.ERROR_MESSAGE, e.toString());

            return mapping.findForward("importExport");
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

            return mapping.findForward("importExport");
        }
        
        try
        {
            User user = Helper.getUser(request);
            
            CommissionBean bean = new CommissionBean();
            
            bean.setExternalStafferList(importItemList1);
            bean.setExternalAssessList(importItemList2);

            commissionManager.importExternel(user, bean, type);
                        
        }
        catch(MYException e)
        {
            _logger.warn(e, e);
            
            request.setAttribute(KeyConstant.ERROR_MESSAGE, "导入出错:数据库错");

            return mapping.findForward("importExport");
        }

        if (type.equals("0"))
            return mapping.findForward("externalStaffer");
        else
            return mapping.findForward("externalAssess");
        
    }
    
    private String[] fillObj(String[] obj)
    {
        String[] result = new String[7];

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
     * 导出
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward exportProfit(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
    throws ServletException
    {
        OutputStream out = null;
        
        String fileName = null;
        
        fileName = "Export_" + TimeTools.now("MMddHHmmss") + ".csv";
        
        response.setContentType("application/x-dbf");
        
        response.setHeader("Content-Disposition", "attachment; filename=" + fileName);
        
        String month = request.getParameter("month");
        
        List<ProfitVSStafferWrap> wrapList = curProfitDAO.queryProfitVSStaffer(month);
        
        WriteFile write = null;
        
        try
        {
            out = response.getOutputStream();
            
            write = WriteFileFactory.getMyTXTWriter();
            
            write.openFile(out);
            
            write.writeLine("年限,职员,当月到款净利,到款净利累计");
            
            WriteFileBuffer line = new WriteFileBuffer(write);
            
            for (ProfitVSStafferWrap each : wrapList)
            {
                line.reset();
                
                line.writeColumn(each.getYears());
                line.writeColumn(each.getStafferName());
                line.writeColumn(each.getCurMoney());
                line.writeColumn(each.getAllMoneys());                
                
                line.writeLine();
            }
        }
        catch (Exception e)
        {
            _logger.error(e, e);
            return null;
        }
        finally
        {
            if (write != null)
            {
                try
                {
                    write.close();
                }
                catch (Exception e1)
                {
                }
            }
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
        }
        
        return null;
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
    public ActionForward queryExternalStaffer(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
    throws ServletException
    {
        ConditionParse condtion = new ConditionParse();

        condtion.addWhereStr();
        
        ActionTools.processJSONQueryCondition(QUERYEXTERNALSTAFFER, request, condtion);
        
        String jsonstr = ActionTools.queryVOByJSONAndToString(QUERYEXTERNALSTAFFER, request, condtion,
            this.externalStafferYearDAO);

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
    public ActionForward queryExternalAssess(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
    throws ServletException
    {
        ConditionParse condtion = new ConditionParse();

        condtion.addWhereStr();
        
        ActionTools.processJSONQueryCondition(QUERYEXTERNALASSESS, request, condtion);
        
        String jsonstr = ActionTools.queryVOByJSONAndToString(QUERYEXTERNALASSESS, request, condtion,
            this.externalAssessDAO);

        return JSONTools.writeResponse(response, jsonstr);
    }
    
    public CommissionFacade getCommissionFacade() {
        return commissionFacade;
    }

    public void setCommissionFacade(CommissionFacade commissionFacade) {
        this.commissionFacade = commissionFacade;
    }

    public CommissionMonthDAO getCommissionMonthDAO() {
        return commissionMonthDAO;
    }

    public void setCommissionMonthDAO(CommissionMonthDAO commissionMonthDAO) {
        this.commissionMonthDAO = commissionMonthDAO;
    }

    public CommissionDAO getCommissionDAO() {
        return commissionDAO;
    }

    public void setCommissionDAO(CommissionDAO commissionDAO) {
        this.commissionDAO = commissionDAO;
    }

    public CurMoneyDetailDAO getCurMoneyDetailDAO() {
        return curMoneyDetailDAO;
    }

    public void setCurMoneyDetailDAO(CurMoneyDetailDAO curMoneyDetailDAO) {
        this.curMoneyDetailDAO = curMoneyDetailDAO;
    }

    public CurFeeDetailDAO getCurFeeDetailDAO() {
        return curFeeDetailDAO;
    }

    public void setCurFeeDetailDAO(CurFeeDetailDAO curFeeDetailDAO) {
        this.curFeeDetailDAO = curFeeDetailDAO;
    }

    public BadDropAssessDetailDAO getBadDropAssessDetailDAO() {
        return badDropAssessDetailDAO;
    }

    public void setBadDropAssessDetailDAO(BadDropAssessDetailDAO badDropAssessDetailDAO) {
        this.badDropAssessDetailDAO = badDropAssessDetailDAO;
    }

    public FinanceFeeStatsDetailDAO getFinanceFeeStatsDetailDAO() {
        return financeFeeStatsDetailDAO;
    }

    public void setFinanceFeeStatsDetailDAO(FinanceFeeStatsDetailDAO financeFeeStatsDetailDAO) {
        this.financeFeeStatsDetailDAO = financeFeeStatsDetailDAO;
    }

    public ExternalAssessDAO getExternalAssessDAO() {
        return externalAssessDAO;
    }

    public void setExternalAssessDAO(ExternalAssessDAO externalAssessDAO) {
        this.externalAssessDAO = externalAssessDAO;
    }

    public StafferDAO getStafferDAO() {
        return stafferDAO;
    }

    public void setStafferDAO(StafferDAO stafferDAO) {
        this.stafferDAO = stafferDAO;
    }

    public ExternalStafferYearDAO getExternalStafferYearDAO() {
        return externalStafferYearDAO;
    }

    public void setExternalStafferYearDAO(ExternalStafferYearDAO externalStafferYearDAO) {
        this.externalStafferYearDAO = externalStafferYearDAO;
    }

    public CommissionManager getCommissionManager() {
        return commissionManager;
    }

    public void setCommissionManager(CommissionManager commissionManager) {
        this.commissionManager = commissionManager;
    }

    public CurProfitDAO getCurProfitDAO() {
        return curProfitDAO;
    }

    public void setCurProfitDAO(CurProfitDAO curProfitDAO) {
        this.curProfitDAO = curProfitDAO;
    }

    public CommissionFreezeDAO getCommissionFreezeDAO() {
        return commissionFreezeDAO;
    }

    public void setCommissionFreezeDAO(CommissionFreezeDAO commissionFreezeDAO) {
        this.commissionFreezeDAO = commissionFreezeDAO;
    }
    
}
