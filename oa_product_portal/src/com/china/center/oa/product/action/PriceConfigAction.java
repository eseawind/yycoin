package com.china.center.oa.product.action;

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
import com.china.center.actionhelper.query.HandleResult;
import com.china.center.common.MYException;
import com.china.center.jdbc.util.ConditionParse;
import com.china.center.oa.product.bean.GoldSilverPriceBean;
import com.china.center.oa.product.bean.PriceConfigBean;
import com.china.center.oa.product.dao.GoldSilverPriceDAO;
import com.china.center.oa.product.dao.PriceConfigDAO;
import com.china.center.oa.product.manager.PriceConfigManager;
import com.china.center.oa.product.vo.PriceConfigVO;
import com.china.center.oa.publics.Helper;
import com.china.center.oa.publics.bean.PrincipalshipBean;
import com.china.center.oa.publics.dao.PrincipalshipDAO;
import com.china.center.tools.BeanUtil;
import com.china.center.tools.CommonTools;
import com.china.center.tools.ListTools;
import com.china.center.tools.MathTools;
import com.china.center.tools.StringTools;
import com.china.center.tools.TimeTools;

public class PriceConfigAction extends DispatchAction
{
	private final Log _logger = LogFactory.getLog(getClass());	
	
	private PriceConfigManager priceConfigManager = null;
	
	private PriceConfigDAO priceConfigDAO = null;
	
	private PrincipalshipDAO principalshipDAO = null;
	
	private GoldSilverPriceDAO goldSilverPriceDAO = null;
	
	private static final String QUERYPRICECONFIG = "queryPriceConfig";

    /**
     * default constructor
     */
    public PriceConfigAction()
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
    public ActionForward queryPriceConfig(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
	throws ServletException
	{
        ConditionParse condtion = new ConditionParse();

        condtion.addWhereStr();

        ActionTools.processJSONQueryCondition(QUERYPRICECONFIG, request, condtion);

        String jsonstr = ActionTools.queryVOByJSONAndToString(QUERYPRICECONFIG, request, condtion,
            this.priceConfigDAO, new HandleResult<PriceConfigVO>()
            {

				public void handle(PriceConfigVO obj)
				{
					handlePrincipalshipResult(obj);
				}
        	
            }
        );

        return JSONTools.writeResponse(response, jsonstr);
    }
    
    private void handlePrincipalshipResult(PriceConfigVO obj) 
    {
        String industryNames = "";
        
        String industryId = obj.getIndustryId();
        
        if (!StringTools.isNullOrNone(industryId))
        {
            String [] industryIds = industryId.split(";");
            
            if (null != industryIds)
            {
                for (String each : industryIds) 
                {
                   PrincipalshipBean prin = principalshipDAO.find(each);
                   
                   industryNames += prin.getName()+";";
                }
            }
        }
        
        obj.setIndustryName(industryNames);
        
        // handler gold silver sailPrice for sailPrice
        if (obj.getType() == 1)
        {
            PriceConfigBean bean = priceConfigManager.calcSailPrice(obj);
            
            if (null != bean)
            {
            	obj.setGold(bean.getGold());
            	obj.setSilver(bean.getSilver());
            	obj.setSailPrice(bean.getSailPrice());
            }
        }
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
    public ActionForward preForAddPriceConfig(ActionMapping mapping, ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response)
	throws ServletException
	{
		request.setAttribute("now", TimeTools.now("yyyy-MM-dd"));
		
		String type = request.getParameter("type");
		
		request.setAttribute("type", type);
		
		if (MathTools.parseInt(type) == 1)
			setGoldSiverAttr(request);
		
		return mapping.findForward("addPriceConfig");
	}

	private void setGoldSiverAttr(HttpServletRequest request)
	{
		List<GoldSilverPriceBean> gspList = goldSilverPriceDAO.listEntityBeans();
		
		if (!ListTools.isEmptyOrNull(gspList))
		{
			request.setAttribute("goldSilver", "【金价：" + MathTools.formatNum2(gspList.get(0).getGold()) + 
					" , 银价：" + MathTools.formatNum2(gspList.get(0).getSilver()) + "】");
		}
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
    public ActionForward addPriceConfig(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
	throws ServletException
	{
        CommonTools.saveParamers(request);
        
        PriceConfigBean bean = new PriceConfigBean();          
        
        try
        {
            BeanUtil.getBean(bean, request);           

            User user = Helper.getUser(request);

            priceConfigManager.addBean(bean, user);

            request.setAttribute(KeyConstant.MESSAGE, "成功操作");
        }
        catch (MYException e)
        {
            _logger.warn(e, e);

            request.setAttribute(KeyConstant.ERROR_MESSAGE, "增加失败:" + e.getMessage());
        }

        CommonTools.removeParamers(request);

        return mapping.findForward("queryPriceConfig");
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
    public ActionForward updatePriceConfig(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
	throws ServletException
	{
        CommonTools.saveParamers(request);
        
        PriceConfigBean bean = new PriceConfigBean();     
        
        try
        {
            BeanUtil.getBean(bean, request);
            
            User user = Helper.getUser(request);
            
            priceConfigManager.updateBean(bean, user);

            request.setAttribute(KeyConstant.MESSAGE, "成功操作");
        }
        catch (MYException e)
        {
            _logger.warn(e, e);

            request.setAttribute(KeyConstant.ERROR_MESSAGE, "增加失败:" + e.getMessage());
        }

        CommonTools.removeParamers(request);

        return mapping.findForward("queryPriceConfig");
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
    public ActionForward findPriceConfig(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
	throws ServletException
	{
        String id = request.getParameter("id");

        String update = request.getParameter("update");

        PriceConfigVO vo = priceConfigDAO.findVO(id);

        if (vo == null)
        {
            request.setAttribute(KeyConstant.ERROR_MESSAGE, "不存在");

            return mapping.findForward("queryPriceConfig");
        }

        request.setAttribute("bean", vo);

        handlePrincipalshipResult(vo);
        
        request.setAttribute("now", TimeTools.now("yyyy-MM-dd"));
        
        if ("1".equals(update))
        {
        	if (vo.getType() == 1)
        		setGoldSiverAttr(request);
        	
            return mapping.findForward("updatePriceConfig");
        }

        return mapping.findForward("detailPriceConfig");
    }
    
    public ActionForward deletePriceConfig(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
	throws ServletException
	{
		AjaxResult ajax = new AjaxResult();
		
		try
		{
			String id = request.getParameter("id");
			
			User user = Helper.getUser(request);
			
			priceConfigManager.deleteBean(id, user);
			
			ajax.setSuccess("成功删除");
		}
		catch (MYException e)
		{
			_logger.warn(e, e);
			
			ajax.setError("删除失败:" + e.getMessage());
		}
		
		return JSONTools.writeResponse(response, ajax);
	}

	public PriceConfigManager getPriceConfigManager()
	{
		return priceConfigManager;
	}

	public void setPriceConfigManager(PriceConfigManager priceConfigManager)
	{
		this.priceConfigManager = priceConfigManager;
	}

	public PriceConfigDAO getPriceConfigDAO()
	{
		return priceConfigDAO;
	}

	public void setPriceConfigDAO(PriceConfigDAO priceConfigDAO)
	{
		this.priceConfigDAO = priceConfigDAO;
	}

	public PrincipalshipDAO getPrincipalshipDAO()
	{
		return principalshipDAO;
	}

	public void setPrincipalshipDAO(PrincipalshipDAO principalshipDAO)
	{
		this.principalshipDAO = principalshipDAO;
	}

	public GoldSilverPriceDAO getGoldSilverPriceDAO()
	{
		return goldSilverPriceDAO;
	}

	public void setGoldSilverPriceDAO(GoldSilverPriceDAO goldSilverPriceDAO)
	{
		this.goldSilverPriceDAO = goldSilverPriceDAO;
	}
}
