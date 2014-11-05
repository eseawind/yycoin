package com.china.center.oa.sail.action;

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
import com.china.center.actionhelper.common.ActionTools;
import com.china.center.actionhelper.common.JSONTools;
import com.china.center.actionhelper.common.KeyConstant;
import com.china.center.actionhelper.common.PageSeparateTools;
import com.china.center.actionhelper.json.AjaxResult;
import com.china.center.actionhelper.query.HandleResult;
import com.china.center.common.MYException;
import com.china.center.jdbc.util.ConditionParse;
import com.china.center.jdbc.util.PageSeparate;
import com.china.center.oa.publics.Helper;
import com.china.center.oa.publics.bean.PrincipalshipBean;
import com.china.center.oa.publics.bean.StafferBean;
import com.china.center.oa.publics.constant.PublicConstant;
import com.china.center.oa.publics.dao.PrincipalshipDAO;
import com.china.center.oa.publics.dao.StafferDAO;
import com.china.center.oa.publics.manager.OrgManager;
import com.china.center.oa.sail.bean.PromotionBean;
import com.china.center.oa.sail.bean.PromotionItemBean;
import com.china.center.oa.sail.dao.PromotionDAO;
import com.china.center.oa.sail.dao.PromotionItemDAO;
import com.china.center.oa.sail.manager.PromotionManager;
import com.china.center.oa.sail.vo.PromotionItemVO;
import com.china.center.oa.sail.vo.PromotionVO;
import com.china.center.tools.BeanUtil;
import com.china.center.tools.CommonTools;
import com.china.center.tools.MathTools;
import com.china.center.tools.StringTools;
import com.china.center.tools.TimeTools;

public class PromotionAction extends DispatchAction 
{
    private final Log _logger = LogFactory.getLog(getClass());

    private PromotionDAO promotionDAO = null;
    
    private PromotionItemDAO promotionItemDAO = null;

    private PromotionManager promotionManager = null;

    private OrgManager orgManager = null;
    
    private StafferDAO stafferDAO = null;

    private PrincipalshipDAO principalshipDAO = null;
    
    private static final String QUERYPROMOTION = "queryPromotion";
    
    private static final String RPTQUERYPROMOTION = "rptQueryPromotion";

    /**
     * default constructor
     */
    public PromotionAction()
    {
    }

    /**
     * queryPromotion
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward queryPromotion(ActionMapping mapping, ActionForm form,
                                         HttpServletRequest request, HttpServletResponse response)
        throws ServletException
    {
        ConditionParse condtion = new ConditionParse();

        condtion.addWhereStr();

        ActionTools.processJSONQueryCondition(QUERYPROMOTION, request, condtion);

        String jsonstr = ActionTools.queryVOByJSONAndToString(QUERYPROMOTION, request, condtion,
            this.promotionDAO, new HandleResult<PromotionVO>()
            {
                public void handle(PromotionVO obj)
                {
                    String industryNames = "";
                    
                    String [] industryIds = obj.getIndustryId().split(",");
                    
                    for (String industryId : industryIds) 
                    {
                       PrincipalshipBean prin = principalshipDAO.find(industryId);
                       
                       industryNames += prin.getName()+",";
                    }
                    
                    obj.setIndustryName(industryNames);
                }
            });

        return JSONTools.writeResponse(response, jsonstr);
    }

    /**
     * preForAddPromotion
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward preForAddPromotion(ActionMapping mapping, ActionForm form,
                                             HttpServletRequest request,
                                             HttpServletResponse response)
        throws ServletException
    {

        request.setAttribute("now", TimeTools.now("yyyy-MM-dd"));
        
        return mapping.findForward("addPromotion");
    }

    /**
     * addPromotion
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward addPromotion(ActionMapping mapping, ActionForm form,
                                       HttpServletRequest request, HttpServletResponse response)
        throws ServletException
    {
        CommonTools.saveParamers(request);
        
        PromotionBean bean = new PromotionBean();

        String [] sailTypes = request.getParameterValues("p_sailType");
        String [] productTypes = request.getParameterValues("p_productType");
        String [] productIds = request.getParameterValues("productId");
        
        List<PromotionItemBean> itemList = new ArrayList<PromotionItemBean>();
        
        bean.setItemList(itemList);                
        
        try
        {
            BeanUtil.getBean(bean, request);

            for (int i = 0 ; i < sailTypes.length; i++)
            {
                
                PromotionItemBean itemBean = new PromotionItemBean();
                
                if (StringTools.isNullOrNone(sailTypes[i]))
                {
                    itemBean.setSailType( -1);
                }else
                {
                    itemBean.setSailType(MathTools.parseInt(sailTypes[i]));
                }

                if (StringTools.isNullOrNone(productTypes[i]))
                {
                    itemBean.setProductType( -1);                    
                }else
                {
                    itemBean.setProductType(MathTools.parseInt(productTypes[i]));
                }
                
                if (StringTools.isNullOrNone(productIds[i]))
                {
                    itemBean.setProductId("0");
                }else
                {
                    itemBean.setProductId(productIds[i]);
                }
                
                itemList.add(itemBean);
            }

            User user = Helper.getUser(request);

            bean.setUpdater(user.getStafferId());
            
            bean.setUpdateTime(TimeTools.now());
            
            promotionManager.addBean(user, bean);

            request.setAttribute(KeyConstant.MESSAGE, "成功操作");
        }
        catch (MYException e)
        {
            _logger.warn(e, e);

            request.setAttribute(KeyConstant.ERROR_MESSAGE, "增加失败:" + e.getMessage());
        }

        CommonTools.removeParamers(request);

        return mapping.findForward("queryPromotion");
    }

    /**
     * addPromotion
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward updatePromotion(ActionMapping mapping, ActionForm form,
                                          HttpServletRequest request, HttpServletResponse response)
        throws ServletException
    {
        CommonTools.saveParamers(request);
        
        PromotionBean bean = new PromotionBean();

        String [] sailTypes = request.getParameterValues("p_sailType");
        String [] productTypes = request.getParameterValues("p_productType");
        String [] productIds = request.getParameterValues("productId");
        
        List<PromotionItemBean> itemList = new ArrayList<PromotionItemBean>();
        
        bean.setItemList(itemList);
        
        try
        {
            BeanUtil.getBean(bean, request);

            for (int i = 0 ; i < sailTypes.length; i++)
            {
                
                PromotionItemBean itemBean = new PromotionItemBean();
                
                if (StringTools.isNullOrNone(sailTypes[i]))
                {
                    itemBean.setSailType( -1);
                }else
                {
                    itemBean.setSailType(MathTools.parseInt(sailTypes[i]));
                }

                if (StringTools.isNullOrNone(productTypes[i]))
                {
                    itemBean.setProductType( -1);                    
                }else
                {
                    itemBean.setProductType(MathTools.parseInt(productTypes[i]));
                }
                
                if (StringTools.isNullOrNone(productIds[i]))
                {
                    itemBean.setProductId("0");
                }else
                {
                    itemBean.setProductId(productIds[i]);
                }
                
                itemList.add(itemBean);
            }
            
            User user = Helper.getUser(request);

            bean.setUpdater(user.getStafferId());
            
            bean.setUpdateTime(TimeTools.now());
            
            promotionManager.updateBean(user, bean);

            request.setAttribute(KeyConstant.MESSAGE, "成功操作");
        }
        catch (MYException e)
        {
            _logger.warn(e, e);

            request.setAttribute(KeyConstant.ERROR_MESSAGE, "增加失败:" + e.getMessage());
        }

        CommonTools.removeParamers(request);

        return mapping.findForward("queryPromotion");
    }

    /**
     * findPromotion
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward findPromotion(ActionMapping mapping, ActionForm form,
                                        HttpServletRequest request, HttpServletResponse response)
        throws ServletException
    {
        String id = request.getParameter("id");

        String update = request.getParameter("update");

        PromotionVO vo = promotionDAO.findVO(id);

        if (vo == null)
        {
            request.setAttribute(KeyConstant.ERROR_MESSAGE, "不存在");

            return mapping.findForward("queryPromotion");
        }
        
        industryInner(vo);

        request.setAttribute("bean", vo);
        
        //明细
        List<PromotionItemVO> itemList = promotionItemDAO.queryEntityVOsByFK(vo.getId());
        
        request.setAttribute("itemList", itemList);

        request.setAttribute("now", TimeTools.now("yyyy-MM-dd"));
        
        if ("1".equals(update))
        {
            return mapping.findForward("updatePromotion");
        }

        return mapping.findForward("detailPromotion");
    }

    /**
     * 处理事业部，逗号分隔
     * @param vo
     */
    private void industryInner(PromotionVO vo) {
        String industryNames = "";
        
        String [] industryIds = vo.getIndustryId().split(",");
        
        for (String industryId : industryIds) 
        {
           PrincipalshipBean prin = principalshipDAO.find(industryId);
           
           industryNames += prin.getName()+",";
        }
        
        vo.setIndustryName(industryNames);
    }

    /**
     * deletePromotion
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward deletePromotion(ActionMapping mapping, ActionForm form,
                                          HttpServletRequest request, HttpServletResponse response)
        throws ServletException
    {
        AjaxResult ajax = new AjaxResult();

        try
        {
            String id = request.getParameter("id");

            User user = Helper.getUser(request);

            promotionManager.deleteBean(user, id);

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
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward logicDeletePromotion(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
    throws ServletException
    {
        AjaxResult ajax = new AjaxResult();

        try
        {
            String id = request.getParameter("id");

            User user = Helper.getUser(request);
            
            promotionManager.logicDeleteBean(user, id);
            
            ajax.setSuccess("逻辑成功删除");
        }
        catch (MYException e)
        {
            _logger.warn(e, e);

            ajax.setError("删除失败:" + e.getMessage());
        }

        return JSONTools.writeResponse(response, ajax);
    }
    
    /**
     * 查询有效的活动规则
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     * @throws MYException 
     */
    public ActionForward rptQueryPromotion(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
    throws ServletException, MYException
    {
     
        CommonTools.saveParamers(request);
        
        List<PromotionVO> list = null;
        
        if (PageSeparateTools.isFirstLoad(request))
        {
            String now = TimeTools.now("yyyy-MM-dd");
            
            User user = Helper.getUser(request);
            
            StafferBean stafferBean = stafferDAO.find(user.getStafferId());
            
            if (null == stafferBean)
            {
                request.setAttribute(KeyConstant.ERROR_MESSAGE, "数据错误，"+ user.getStafferId() + " 不存在");
                
                return mapping.findForward("error");    
            }
            
            String industryId = stafferBean.getIndustryId();
            
            String payType = request.getParameter("payType");
            
            ConditionParse condition = new ConditionParse();
            
            condition.addWhereStr();
            
            condition.addCondition("PromotionBean.beginDate", "<=", now);
            
            condition.addCondition("PromotionBean.endDate", ">=", now);
            
            condition.addCondition("PromotionBean.industryId", "like", industryId);
           
            condition.addCondition("and PromotionBean.payType in ( 0,"+MathTools.parseInt(payType)+")");
            
            condition.addIntCondition("PromotionBean.inValid", "=", 0);            
            
            int total = promotionDAO.countByCondition(condition.toString());

            PageSeparate page = new PageSeparate(total, PublicConstant.PAGE_COMMON_SIZE);

            PageSeparateTools.initPageSeparate(condition, page, request, RPTQUERYPROMOTION);

            list = promotionDAO.queryEntityVOsByCondition(condition, page);
        }
        else
        {
            PageSeparateTools.processSeparate(request, RPTQUERYPROMOTION);

            list = promotionDAO.queryEntityVOsByCondition(PageSeparateTools.getCondition(request,
                RPTQUERYPROMOTION), PageSeparateTools.getPageSeparate(request, RPTQUERYPROMOTION));
        }
                
        request.setAttribute("beanList", list);
        
        return mapping.findForward("rptQueryPromotion");
        
    }
    
    /**
     * @return the PromotionDAO
     */
    public PromotionDAO getPromotionDAO()
    {
        return promotionDAO;
    }

    /**
     * @param PromotionDAO
     *            the PromotionDAO to set
     */
    public void setPromotionDAO(PromotionDAO promotionDAO)
    {
        this.promotionDAO = promotionDAO;
    }

    /**
     * @return the PromotionManager
     */
    public PromotionManager getPromotionManager()
    {
        return promotionManager;
    }

    /**
     * @param PromotionManager
     *            the PromotionManager to set
     */
    public void setPromotionManager(PromotionManager promotionManager)
    {
        this.promotionManager = promotionManager;
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

    public PrincipalshipDAO getPrincipalshipDAO() {
        return principalshipDAO;
    }

    public void setPrincipalshipDAO(PrincipalshipDAO principalshipDAO) {
        this.principalshipDAO = principalshipDAO;
    }

    public PromotionItemDAO getPromotionItemDAO() {
        return promotionItemDAO;
    }

    public void setPromotionItemDAO(PromotionItemDAO promotionItemDAO) {
        this.promotionItemDAO = promotionItemDAO;
    }

    public StafferDAO getStafferDAO() {
        return stafferDAO;
    }

    public void setStafferDAO(StafferDAO stafferDAO) {
        this.stafferDAO = stafferDAO;
    }
    
}
