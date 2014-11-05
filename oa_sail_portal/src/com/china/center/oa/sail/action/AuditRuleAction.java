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
import com.china.center.actionhelper.json.AjaxResult;
import com.china.center.common.MYException;
import com.china.center.jdbc.util.ConditionParse;
import com.china.center.oa.publics.Helper;
import com.china.center.oa.sail.bean.AuditRuleBean;
import com.china.center.oa.sail.bean.AuditRuleItemBean;
import com.china.center.oa.sail.dao.AuditRuleDAO;
import com.china.center.oa.sail.dao.AuditRuleItemDAO;
import com.china.center.oa.sail.manager.AuditRuleManager;
import com.china.center.oa.sail.vo.AuditRuleItemVO;
import com.china.center.oa.sail.vo.AuditRuleVO;
import com.china.center.tools.BeanUtil;
import com.china.center.tools.CommonTools;
import com.china.center.tools.MathTools;
import com.china.center.tools.StringTools;

public class AuditRuleAction extends DispatchAction
{

	private final Log _logger = LogFactory.getLog(getClass());
	
    private AuditRuleDAO auditRuleDAO = null;
    
    private AuditRuleItemDAO auditRuleItemDAO = null;

    private AuditRuleManager auditRuleManager = null;
    
    private static final String QUERYAUDITRULE = "queryAuditRule";
    
    public AuditRuleAction()
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
    public ActionForward queryAuditRule(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
	throws ServletException
	{
        ConditionParse condtion = new ConditionParse();

        condtion.addWhereStr();

        ActionTools.processJSONQueryCondition(QUERYAUDITRULE, request, condtion);

        String jsonstr = ActionTools.queryVOByJSONAndToString(QUERYAUDITRULE, request, condtion,
            this.auditRuleDAO);

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
    public ActionForward addAuditRule(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
	throws ServletException
	{
        CommonTools.saveParamers(request);
        
        AuditRuleBean bean = new AuditRuleBean();

        String [] managerTypes = request.getParameterValues("p_managerType");
        String [] productTypes = request.getParameterValues("p_productType");
        String [] materialTypes = request.getParameterValues("p_materialType");
        String [] currencyTypes = request.getParameterValues("p_currencyType");
        String [] productIds = request.getParameterValues("productId");        
        String [] payConditions = request.getParameterValues("p_payCondition");
        String [] accountPeriods = request.getParameterValues("p_accountPeriod");
        String [] productPeriods = request.getParameterValues("p_productPeriod");
        String [] ratioDowns = request.getParameterValues("p_ratioDown");
        String [] ratioUps = request.getParameterValues("p_ratioUp");        
        String [] profitPeriods = request.getParameterValues("p_profitPeriod");
        String [] ltSailPrices = request.getParameterValues("p_ltSailPrice");
        String [] diffRatios = request.getParameterValues("p_diffRatio");
        String [] minRatios = request.getParameterValues("p_minRatio");
        
        List<AuditRuleItemBean> itemList = new ArrayList<AuditRuleItemBean>();
        
        bean.setItemList(itemList);                
        
        try
        {
            BeanUtil.getBean(bean, request);

            for (int i = 0 ; i < managerTypes.length; i++)
            {
                
                AuditRuleItemBean itemBean = new AuditRuleItemBean();
                
                if (StringTools.isNullOrNone(managerTypes[i]))
                {
                    itemBean.setManagerType( -1);
                }else
                {
                    itemBean.setManagerType(MathTools.parseInt(managerTypes[i]));
                }

                if (StringTools.isNullOrNone(productTypes[i]))
                {
                    itemBean.setProductType( -1);
                }else
                {
                    itemBean.setProductType(MathTools.parseInt(productTypes[i]));
                }
                
                if (StringTools.isNullOrNone(materialTypes[i]))
                {
                    itemBean.setMateriaType(-1);
                }else
                {
                    itemBean.setMateriaType(MathTools.parseInt(materialTypes[i]));
                }

                if (StringTools.isNullOrNone(currencyTypes[i]))
                {
                    itemBean.setCurrencyType(-1);
                }else
                {
                    itemBean.setCurrencyType(MathTools.parseInt(currencyTypes[i]));
                }
                
                if (StringTools.isNullOrNone(productIds[i]))
                {
                    itemBean.setProductId("");
                }else
                {
                    itemBean.setProductId(productIds[i]);
                }
                
                if (StringTools.isNullOrNone(payConditions[i]))
                {
                	itemBean.setPayCondition(-1);
                }
                else
                {
                	itemBean.setPayCondition(MathTools.parseInt(payConditions[i]));
                }

                itemBean.setAccountPeriod(MathTools.parseInt(accountPeriods[i]));
                
                itemBean.setProductPeriod(MathTools.parseInt(productPeriods[i]));
                
                int down = MathTools.parseInt(ratioDowns[i]);
                
                int up = MathTools.parseInt(ratioUps[i]);
                
                if (down > up)
                {
                	request.setAttribute(KeyConstant.ERROR_MESSAGE, "增加失败:毛利率下限不能大于上限");
                	
                	return mapping.findForward("queryAuditRule");
                }
                
                itemBean.setRatioDown(down);
                itemBean.setRatioUp(up);
                
                itemBean.setProfitPeriod(MathTools.parseInt(profitPeriods[i]));
                
                if (StringTools.isNullOrNone(ltSailPrices[i]))
                {
                	itemBean.setLtSailPrice(-1);
                }
                else
                {
                	itemBean.setLtSailPrice(MathTools.parseInt(ltSailPrices[i]));
                }
                
                itemBean.setDiffRatio(MathTools.parseDouble(diffRatios[i]));
                itemBean.setMinRatio(MathTools.parseDouble(minRatios[i]));
                
                itemList.add(itemBean);
            }

            User user = Helper.getUser(request);
            
            auditRuleManager.addBean(bean, user);

            request.setAttribute(KeyConstant.MESSAGE, "成功操作");
        }
        catch (MYException e)
        {
            _logger.warn(e, e);

            request.setAttribute(KeyConstant.ERROR_MESSAGE, "增加失败:" + e.getMessage());
        }

        CommonTools.removeParamers(request);

        return mapping.findForward("queryAuditRule");
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
    public ActionForward updateAuditRule(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
	throws ServletException
	{
        CommonTools.saveParamers(request);
        
        AuditRuleBean bean = new AuditRuleBean();

        String [] managerTypes = request.getParameterValues("p_managerType");
        String [] productTypes = request.getParameterValues("p_productType");
        String [] materialTypes = request.getParameterValues("p_materialType");
        String [] currencyTypes = request.getParameterValues("p_currencyType");
        String [] productIds = request.getParameterValues("productId");        
        String [] payConditions = request.getParameterValues("p_payCondition");
        String [] accountPeriods = request.getParameterValues("p_accountPeriod");
        String [] productPeriods = request.getParameterValues("p_productPeriod");
        String [] ratioDowns = request.getParameterValues("p_ratioDown");
        String [] ratioUps = request.getParameterValues("p_ratioUp");        
        String [] profitPeriods = request.getParameterValues("p_profitPeriod");
        String [] ltSailPrices = request.getParameterValues("p_ltSailPrice");
        String [] diffRatios = request.getParameterValues("p_diffRatio");
        String [] minRatios = request.getParameterValues("p_minRatio");
        
        List<AuditRuleItemBean> itemList = new ArrayList<AuditRuleItemBean>();
        
        bean.setItemList(itemList);
        
        try
        {
            BeanUtil.getBean(bean, request);

            for (int i = 0 ; i < managerTypes.length; i++)
            {
                
                AuditRuleItemBean itemBean = new AuditRuleItemBean();
                
                if (StringTools.isNullOrNone(managerTypes[i]))
                {
                    itemBean.setManagerType( -1);
                }else
                {
                    itemBean.setManagerType(MathTools.parseInt(managerTypes[i]));
                }

                if (StringTools.isNullOrNone(productTypes[i]))
                {
                    itemBean.setProductType( -1);
                }else
                {
                    itemBean.setProductType(MathTools.parseInt(productTypes[i]));
                }
                
                if (StringTools.isNullOrNone(materialTypes[i]))
                {
                    itemBean.setMateriaType(-1);
                }else
                {
                    itemBean.setMateriaType(MathTools.parseInt(materialTypes[i]));
                }

                if (StringTools.isNullOrNone(currencyTypes[i]))
                {
                    itemBean.setCurrencyType(-1);
                }else
                {
                    itemBean.setCurrencyType(MathTools.parseInt(currencyTypes[i]));
                }
                
                if (StringTools.isNullOrNone(productIds[i]))
                {
                    itemBean.setProductId("");
                }else
                {
                    itemBean.setProductId(productIds[i]);
                }
                
                if (StringTools.isNullOrNone(payConditions[i]))
                {
                	itemBean.setPayCondition(-1);
                }
                else
                {
                	itemBean.setPayCondition(MathTools.parseInt(payConditions[i]));
                }                

                itemBean.setAccountPeriod(MathTools.parseInt(accountPeriods[i]));
                
                itemBean.setProductPeriod(MathTools.parseInt(productPeriods[i]));
                
                int down = MathTools.parseInt(ratioDowns[i]);
                
                int up = MathTools.parseInt(ratioUps[i]);
                
                if (down > up)
                {
                	request.setAttribute(KeyConstant.ERROR_MESSAGE, "增加失败:毛利率下限不能大于上限");
                	
                	return mapping.findForward("queryAuditRule");
                }
                
                itemBean.setRatioDown(down);
                itemBean.setRatioUp(up);
                
                itemBean.setProfitPeriod(MathTools.parseInt(profitPeriods[i]));
                
                if (StringTools.isNullOrNone(ltSailPrices[i]))
                {
                	itemBean.setLtSailPrice(-1);
                }
                else
                {
                	itemBean.setLtSailPrice(MathTools.parseInt(ltSailPrices[i]));
                }
                
                itemBean.setDiffRatio(MathTools.parseDouble(diffRatios[i]));
                itemBean.setMinRatio(MathTools.parseDouble(minRatios[i]));
                
                itemList.add(itemBean);
            }
            
            User user = Helper.getUser(request);
            
            auditRuleManager.updateBean(bean, user);

            request.setAttribute(KeyConstant.MESSAGE, "成功操作");
        }
        catch (MYException e)
        {
            _logger.warn(e, e);

            request.setAttribute(KeyConstant.ERROR_MESSAGE, "增加失败:" + e.getMessage());
        }

        CommonTools.removeParamers(request);

        return mapping.findForward("queryAuditRule");
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
    public ActionForward findAuditRule(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
	throws ServletException
	{
        String id = request.getParameter("id");

        String update = request.getParameter("update");

        AuditRuleVO vo = auditRuleDAO.findVO(id);

        if (vo == null)
        {
            request.setAttribute(KeyConstant.ERROR_MESSAGE, "不存在");

            return mapping.findForward("queryAuditRule");
        }        

        request.setAttribute("bean", vo);
        
        //明细
        List<AuditRuleItemVO> itemList = auditRuleItemDAO.queryEntityVOsByFK(vo.getId());
        
        request.setAttribute("itemList", itemList);
        
        if ("1".equals(update))
        {
            return mapping.findForward("updateAuditRule");
        }

        return mapping.findForward("detailAuditRule");
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
    public ActionForward deleteAuditRule(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
	throws ServletException
	{
        AjaxResult ajax = new AjaxResult();

        try
        {
            String id = request.getParameter("id");

            User user = Helper.getUser(request);

            auditRuleManager.deleteBean(id, user);

            ajax.setSuccess("成功删除");
        }
        catch (MYException e)
        {
            _logger.warn(e, e);

            ajax.setError("删除失败:" + e.getMessage());
        }

        return JSONTools.writeResponse(response, ajax);
    }

	public AuditRuleDAO getAuditRuleDAO()
	{
		return auditRuleDAO;
	}

	public void setAuditRuleDAO(AuditRuleDAO auditRuleDAO)
	{
		this.auditRuleDAO = auditRuleDAO;
	}

	public AuditRuleItemDAO getAuditRuleItemDAO()
	{
		return auditRuleItemDAO;
	}

	public void setAuditRuleItemDAO(AuditRuleItemDAO auditRuleItemDAO)
	{
		this.auditRuleItemDAO = auditRuleItemDAO;
	}

	public AuditRuleManager getAuditRuleManager()
	{
		return auditRuleManager;
	}

	public void setAuditRuleManager(AuditRuleManager auditRuleManager)
	{
		this.auditRuleManager = auditRuleManager;
	}
    
    
}
