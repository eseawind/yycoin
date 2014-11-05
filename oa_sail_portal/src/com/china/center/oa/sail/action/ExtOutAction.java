package com.china.center.oa.sail.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

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
import com.china.center.actionhelper.common.OldPageSeparateTools;
import com.china.center.actionhelper.common.PageSeparateTools;
import com.china.center.actionhelper.json.AjaxResult;
import com.china.center.common.MYException;
import com.china.center.jdbc.util.ConditionParse;
import com.china.center.jdbc.util.PageSeparate;
import com.china.center.oa.extsail.bean.ZJRCBaseBean;
import com.china.center.oa.extsail.bean.ZJRCDistributionBean;
import com.china.center.oa.extsail.bean.ZJRCOutBean;
import com.china.center.oa.extsail.bean.ZJRCProductBean;
import com.china.center.oa.extsail.dao.ZJRCBaseDAO;
import com.china.center.oa.extsail.dao.ZJRCDistributionDAO;
import com.china.center.oa.extsail.dao.ZJRCOutDAO;
import com.china.center.oa.extsail.dao.ZJRCProductDAO;
import com.china.center.oa.extsail.manager.ZJRCManager;
import com.china.center.oa.extsail.vo.ZJRCOutVO;
import com.china.center.oa.extsail.vo.ZJRCProductVO;
import com.china.center.oa.product.dao.ProductDAO;
import com.china.center.oa.publics.Helper;
import com.china.center.oa.publics.constant.PublicConstant;
import com.china.center.oa.sail.constanst.OutConstant;
import com.china.center.oa.sail.constanst.OutImportConstant;
import com.china.center.tools.BeanUtil;
import com.china.center.tools.CommonTools;
import com.china.center.tools.ListTools;
import com.china.center.tools.MathTools;
import com.china.center.tools.ParamterMap;
import com.china.center.tools.RequestTools;
import com.china.center.tools.StringTools;
import com.china.center.tools.TimeTools;

public class ExtOutAction extends DispatchAction
{
	private final Log _logger = LogFactory.getLog(getClass());
	
	private final Log importLog = LogFactory.getLog("sec");
	
	private ZJRCManager zjrcManager = null;
	
	private ZJRCProductDAO zjrcProductDAO = null;
	
	private ZJRCOutDAO zjrcOutDAO = null;
	
	private ZJRCBaseDAO zjrcBaseDAO = null;
	
	private ProductDAO productDAO = null;
	
	private ZJRCDistributionDAO zjrcDistributionDAO = null;
	
	private final static String QUERYZJRCPRODUCT = "queryZJRCProduct";
	
	private final static String RPTQUERYZJRCPRODUCT = "rptQueryZJRCProduct";
	
	private final static String QUERYSELFZJRCOUT = "querySelfZJRCOut";
	
	private final static String QUERYZJRCOUT = "queryZJRCOut";
	
	/**
	 * default construct
	 */
	public ExtOutAction()
	{
	}
	
	/**
	 * queryZJRCProduct
	 * @param mapping
	 * @param form
	 * @param request
	 * @param reponse
	 * @return
	 * @throws ServletException
	 */
	public ActionForward queryZJRCProduct(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse reponse)
	throws ServletException
	{
        ConditionParse condtion = new ConditionParse();

        condtion.addWhereStr();

        ActionTools.processJSONQueryCondition(QUERYZJRCPRODUCT, request, condtion);

        String jsonstr = ActionTools.queryVOByJSONAndToString(QUERYZJRCPRODUCT, request, condtion,
            this.zjrcProductDAO);

        return JSONTools.writeResponse(reponse, jsonstr);
    }
	
	/**
	 * addZJRCProduct
	 * @param mapping
	 * @param form
	 * @param request
	 * @param reponse
	 * @return
	 * @throws ServletException
	 */
	public ActionForward addZJRCProduct(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse reponse)
	throws ServletException
	{
        ZJRCProductBean bean = new ZJRCProductBean();

        try
        {
            BeanUtil.getBean(bean, request);

            User user = Helper.getUser(request);

            zjrcManager.addZJRCProduct(user, bean);

            request.setAttribute(KeyConstant.MESSAGE, "成功操作");
        }
        catch (MYException e)
        {
            _logger.warn(e, e);

            request.setAttribute(KeyConstant.ERROR_MESSAGE, "增加失败:" + e.getMessage());
        }

        CommonTools.removeParamers(request);

        return mapping.findForward("queryZJRCProduct");
    }
	
	/**
	 * updateZJRCProduct
	 * @param mapping
	 * @param form
	 * @param request
	 * @param reponse
	 * @return
	 * @throws ServletException
	 */
	public ActionForward updateZJRCProduct(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse reponse)
	throws ServletException
	{
		ZJRCProductBean bean = new ZJRCProductBean();

        try
        {
            BeanUtil.getBean(bean, request);

            User user = Helper.getUser(request);

            zjrcManager.updateZJRCProduct(user, bean);

            request.setAttribute(KeyConstant.MESSAGE, "成功操作");
        }
        catch (MYException e)
        {
            _logger.warn(e, e);

            request.setAttribute(KeyConstant.ERROR_MESSAGE, "增加失败:" + e.getMessage());
        }

        return mapping.findForward("queryZJRCProduct");
    }
	
	/**
	 * deleteZJRCProduct
	 * @param mapping
	 * @param form
	 * @param request
	 * @param reponse
	 * @return
	 * @throws ServletException
	 */
	public ActionForward deleteZJRCProduct(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse reponse)
	throws ServletException
	{
        AjaxResult ajax = new AjaxResult();

        try
        {
            String id = request.getParameter("id");

            User user = Helper.getUser(request);

            zjrcManager.deleteZJRCProduct(user, id);

            ajax.setSuccess("成功删除");
        }
        catch (MYException e)
        {
            _logger.warn(e, e);

            ajax.setError("删除失败:" + e.getMessage());
        }

        return JSONTools.writeResponse(reponse, ajax);
    }
	
	/**
	 * findZJRCProduct
	 * @param mapping
	 * @param form
	 * @param request
	 * @param reponse
	 * @return
	 * @throws ServletException
	 */
	public ActionForward findZJRCProduct(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse reponse)
	throws ServletException
	{
        String id = request.getParameter("id");

        String update = request.getParameter("update");

        CommonTools.saveParamers(request);

        String goback = request.getParameter("goback");

        if (StringTools.isNullOrNone(goback))
        {
            goback = "1";
        }

        request.setAttribute("goback", goback);
        
        ZJRCProductVO vo = zjrcProductDAO.findVO(id);

        if (vo == null)
        {
            request.setAttribute(KeyConstant.ERROR_MESSAGE, "不存在");

            return mapping.findForward("queryZJRCProduct");
        }

        request.setAttribute("bean", vo);

        if ("1".equals(update))
        {
            return mapping.findForward("updateZJRCProduct");
        }

        return mapping.findForward("detailZJRCProduct");
    }
	
	/**
	 * rptQueryZJRCProduct
	 * @param mapping
	 * @param form
	 * @param request
	 * @param reponse
	 * @return
	 * @throws ServletException
	 */
	public ActionForward rptQueryZJRCProduct(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse reponse)
	throws ServletException
	{
        CommonTools.saveParamers(request);

        List<ZJRCProductVO> list = null;
        
        if (PageSeparateTools.isFirstLoad(request))
        {
            ConditionParse condtion = new ConditionParse();

            condtion.addWhereStr();

            String productId = request.getParameter("id");

            if ( !StringTools.isNullOrNone(productId))
            {
                condtion.addCondition("ZJRCProductBean.id", "like", productId);
            }
            
            String name = request.getParameter("name");

            if ( !StringTools.isNullOrNone(name))
            {
                condtion.addCondition("ZJRCProductBean.zjrProductName", "like", name);
            }

            int total = zjrcProductDAO.countByCondition(condtion.toString());

            PageSeparate page = new PageSeparate(total, PublicConstant.PAGE_COMMON_SIZE);

            PageSeparateTools.initPageSeparate(condtion, page, request, RPTQUERYZJRCPRODUCT);

            list = zjrcProductDAO.queryEntityVOsByCondition(condtion, page);
        }
        else
        {
            PageSeparateTools.processSeparate(request, RPTQUERYZJRCPRODUCT);

            list = zjrcProductDAO.queryEntityVOsByCondition(PageSeparateTools.getCondition(request,
            		RPTQUERYZJRCPRODUCT), PageSeparateTools.getPageSeparate(request, RPTQUERYZJRCPRODUCT));
        }

        request.setAttribute("beanList", list);

        request.setAttribute("random", new Random().nextInt());

		return mapping.findForward("rptQueryZJRCProduct");
	}
	
	/**
	 * queryZJRCOut
	 * @param mapping
	 * @param form
	 * @param request
	 * @param reponse
	 * @return
	 * @throws ServletException
	 */
	public ActionForward queryZJRCOut(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse reponse)
	throws ServletException
	{
		User user = (User) request.getSession().getAttribute("user");

		request.getSession().setAttribute("exportKey", QUERYZJRCOUT);

		List<ZJRCOutVO> list = null;

		CommonTools.saveParamers(request);

		try
		{
			if (OldPageSeparateTools.isFirstLoad(request))
			{
				Object attribute = request.getAttribute("holdCondition");

				ConditionParse condtion = null;

				if (attribute == null)
				{
					condtion = getQueryCondition(request, user);
				}
				else
				{
					condtion = OldPageSeparateTools.getCondition(request,
							QUERYZJRCOUT);
				}

				int tatol = zjrcOutDAO.countVOByCondition(condtion.toString());

				PageSeparate page = new PageSeparate(tatol,
						PublicConstant.PAGE_SIZE - 5);

				OldPageSeparateTools.initPageSeparate(condtion, page, request,
						QUERYZJRCOUT);

				list = zjrcOutDAO.queryEntityVOsByCondition(condtion, page);
			}
			else
			{
				OldPageSeparateTools.processSeparate(request, QUERYZJRCOUT);

				list = zjrcOutDAO
						.queryEntityVOsByCondition(OldPageSeparateTools
								.getCondition(request, QUERYZJRCOUT),
								OldPageSeparateTools.getPageSeparate(request,
										QUERYZJRCOUT));

			}
		}
		catch (Exception e)
		{
			request.setAttribute(KeyConstant.ERROR_MESSAGE, "查询单据失败");

			_logger.error(e, e);

			return mapping.findForward("error");
		}

		request.setAttribute("listOut1", list);

		request.getSession().setAttribute("listOut1", list);

		request.setAttribute("now", TimeTools.now("yyyy-MM-dd"));

		getDivs(request, list);

		return mapping.findForward("queryZJRCOut");
	}
	
	private ConditionParse getQueryCondition(HttpServletRequest request,
			User user) throws MYException
	{
		Map<String, String> queryOutCondtionMap = CommonTools
				.saveParamersToMap(request);

		ConditionParse condtion = new ConditionParse();

		condtion.addWhereStr();

		String outTime = request.getParameter("outTime");

		String outTime1 = request.getParameter("outTime1");

		if (!StringTools.isNullOrNone(outTime))
		{
			condtion.addCondition("ZJRCOutBean.outTime", ">=", outTime);
		}
		else
		{
			condtion.addCondition("ZJRCOutBean.outTime", ">=",
					TimeTools.now_short(-7));

			request.setAttribute("outTime", TimeTools.now_short(-7));
		}

		if (!StringTools.isNullOrNone(outTime1))
		{
			condtion.addCondition("ZJRCOutBean.outTime", "<=", outTime1);
		}
		else
		{
			condtion.addCondition("ZJRCOutBean.outTime", "<=",
					TimeTools.now_short());

			request.setAttribute("outTime1", TimeTools.now_short());
		}

		String id = request.getParameter("id");

		if (!StringTools.isNullOrNone(id))
		{
			condtion.addCondition("ZJRCOutBean.fullid", "like", id.trim());
		}

		String stafferName = request.getParameter("stafferName");

		if (!StringTools.isNullOrNone(stafferName))
		{
			condtion.addCondition("ZJRCOutBean.stafferName", "like", stafferName);
		}
		
		String receiver = request.getParameter("receiver");

		if (!StringTools.isNullOrNone(receiver))
		{
			condtion.addCondition("ZJRCOutBean.receiver", "like", receiver.trim());
		}

		String handphone = request.getParameter("handphone");

		if (!StringTools.isNullOrNone(handphone))
		{
			condtion.addCondition("ZJRCOutBean.handphone", "like", handphone);
		}
		
		String status = request.getParameter("status");

		if (!StringTools.isNullOrNone(status))
		{
			condtion.addIntCondition("ZJRCOutBean.status", "=", status);
		}

		if (!condtion.containOrder())
		{
			condtion.addCondition("order by ZJRCOutBean.id desc");
		}

		request.getSession().setAttribute("ppmap", queryOutCondtionMap);

		return condtion;
	}
	
	/**
	 * querySelfZJRCOut
	 * @param mapping
	 * @param form
	 * @param request
	 * @param reponse
	 * @return
	 * @throws ServletException
	 */
	public ActionForward querySelfZJRCOut(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse reponse)
	throws ServletException
	{
		User user = (User) request.getSession().getAttribute("user");

		request.getSession().setAttribute("exportKey", QUERYSELFZJRCOUT);

		List<ZJRCOutVO> list = null;

		CommonTools.saveParamers(request);

		try
		{
			if (OldPageSeparateTools.isFirstLoad(request))
			{
				ConditionParse condtion = getQuerySelfCondition(request, user);

				int tatol = zjrcOutDAO.countVOByCondition(condtion.toString());

				PageSeparate page = new PageSeparate(tatol,
						PublicConstant.PAGE_SIZE - 5);

				OldPageSeparateTools.initPageSeparate(condtion, page, request, QUERYSELFZJRCOUT);

				list = zjrcOutDAO.queryEntityVOsByCondition(condtion, page);
			}
			else
			{
				OldPageSeparateTools.processSeparate(request, QUERYSELFZJRCOUT);

				list = zjrcOutDAO.queryEntityVOsByCondition(OldPageSeparateTools
						.getCondition(request, QUERYSELFZJRCOUT),
						OldPageSeparateTools.getPageSeparate(request, QUERYSELFZJRCOUT));
			}
		}
		catch (Exception e)
		{
			request.setAttribute(KeyConstant.ERROR_MESSAGE, "查询单据失败");

			_logger.error(e, e);

			return mapping.findForward("error");
		}

		request.setAttribute("listOut1", list);

		request.getSession().setAttribute("listOut1", list);

		request.setAttribute("now", TimeTools.now("yyyy-MM-dd"));

		getDivs(request, list);

		return mapping.findForward("querySelfZJRCOut");
	}
	
	private ConditionParse getQuerySelfCondition(HttpServletRequest request,
			User user)
	{
		ConditionParse condtion = new ConditionParse();

		condtion.addWhereStr();

		// 只能查询自己的
		condtion.addCondition("ZJRCOutBean.STAFFERID", "=", user.getStafferId());
		
		String outTime = request.getParameter("outTime");

		String outTime1 = request.getParameter("outTime1");

		if (!StringTools.isNullOrNone(outTime))
		{
			condtion.addCondition("ZJRCOutBean.outTime", ">=", outTime);
		}
		else
		{
			condtion.addCondition("ZJRCOutBean.outTime", ">=",
					TimeTools.now_short(-7));

			request.setAttribute("outTime", TimeTools.now_short(-7));
		}

		if (!StringTools.isNullOrNone(outTime1))
		{
			condtion.addCondition("ZJRCOutBean.outTime", "<=", outTime1);
		}
		else
		{
			condtion.addCondition("ZJRCOutBean.outTime", "<=",
					TimeTools.now_short());

			request.setAttribute("outTime1", TimeTools.now_short());
		}

		String id = request.getParameter("id");

		if (!StringTools.isNullOrNone(id))
		{
			condtion.addCondition("ZJRCOutBean.fullid", "like", id);
		}
		
		String receiver = request.getParameter("receiver");

		if (!StringTools.isNullOrNone(receiver))
		{
			condtion.addCondition("ZJRCOutBean.receiver", "like", receiver.trim());
		}

		String handphone = request.getParameter("handphone");

		if (!StringTools.isNullOrNone(handphone))
		{
			condtion.addCondition("ZJRCOutBean.handphone", "like", handphone);
		}
		
		String status = request.getParameter("status");

		if (!StringTools.isNullOrNone(status))
		{
			condtion.addIntCondition("ZJRCOutBean.status", "=", status);
		}

		condtion.addCondition("order by ZJRCOutBean.id desc");

		return condtion;
	}
	
	private void getDivs(HttpServletRequest request, List list)
	{
		Map divMap = new HashMap();

		if (list != null)
		{
			for (Object each : list)
			{
				ZJRCOutBean bean = (ZJRCOutBean) each;

				try
				{
					List<ZJRCBaseBean> baseList = zjrcBaseDAO.queryEntityBeansByFK(bean
							.getFullId());

					divMap.put(bean.getFullId(), createTable(baseList, bean.getTotal()));
				}
				catch (Exception e)
				{
					_logger.error("addZJRCOut", e);
				}
			}
		}

		request.setAttribute("divMap", divMap);
	}
	
	private String createTable(List<ZJRCBaseBean> list, double tatol)
    {
        StringBuffer buffer = new StringBuffer();

        buffer.append("<table width='100%' border='0' cellspacing='1'>");
        buffer.append("<tr align='center' class='content0'>");
        buffer.append("<td width='20%' align='center'>品名</td>");
        buffer.append("<td width='10%' align='center'>数量</td>");
        buffer.append("<td width='15%' align='center'>单价</td>");
        buffer.append("<td width='20%' align='left'>金额(总计:<span id='total'>"
                      + MathTools.formatNum(tatol) + "</span>)</td>");
        buffer.append("<td width='25%' align='center'>成本</td></tr>");

        int index = 0;
        String cls = null;
        for (ZJRCBaseBean bean : list)
        {
            if (index % 2 == 0)
            {
                cls = "content1";
            }
            else
            {
                cls = "content2";
            }

            buffer.append("<tr class='" + cls + "'>");

            buffer.append("<td width='20%' align='center'>"
                          + StringTools.getLineString(bean.getProductName()) + "</td>");
            buffer.append("<td width='10%' align='center'>" + bean.getAmount() + "</td>");
            buffer.append(" <td width='15%' align='center'>" + MathTools.formatNum(bean.getPrice())
                          + "</td>");
            buffer.append("<td width='15%' align='center'>" + MathTools.formatNum(bean.getValue())
                          + "</td>");
            buffer.append("<td width='25%' align='center'>"
                          + MathTools.formatNum(bean.getCostPrice()) + "</td>");
            index++ ;
        }

        buffer.append("</table>");

        return StringTools.getLineString(buffer.toString());
    }
	
	/**
	 * queryForAdd
	 * @param mapping
	 * @param form
	 * @param request
	 * @param reponse
	 * @return
	 * @throws ServletException
	 */
	public ActionForward queryForAdd(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse reponse)
	throws ServletException
	{
		request.setAttribute("current", TimeTools.now("yyyy-MM-dd"));

		// 销售单
		return mapping.findForward("addZJRCOut");
	}
	
	/**
	 * addZJRCOut
	 * @param mapping
	 * @param form
	 * @param request
	 * @param reponse
	 * @return
	 * @throws ServletException
	 */
	public ActionForward addZJRCOut(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse reponse)
	throws ServletException
	{
		CommonTools.saveParamers(request);

		User user = (User) request.getSession().getAttribute("user");

		String saves = request.getParameter("saves");

		String fullId = request.getParameter("fullId");

		if ("save".equals(saves))
		{
			saves = "保存";
		}
		else
		{
			saves = "提交";
		}

		ParamterMap map = new ParamterMap(request);

		ActionForward action = null;

		ZJRCOutBean outBean = null;

		outBean = new ZJRCOutBean();

		// 增加职员的ID
		outBean.setStafferId(user.getStafferId());
		outBean.setStafferName(user.getStafferName());

		BeanUtil.getBean(outBean, request);

		outBean.setLogTime(TimeTools.now());

		action = processCommonOut(mapping, request, user, saves, fullId, outBean, map);

		if (action != null)
		{
			return action;
		}

		CommonTools.removeParamers(request);

		RequestTools.actionInitQuery(request);

		return querySelfZJRCOut(mapping, form, request, reponse);
	}
	
	private ActionForward processCommonOut(ActionMapping mapping, HttpServletRequest request,
            User user, String saves, String fullId,
            ZJRCOutBean outBean, ParamterMap map)
	{
        // 增加库单
        if ( !StringTools.isNullOrNone(fullId))
        {
            // 修改
            ZJRCOutBean out = zjrcOutDAO.find(fullId);

            if (out == null)
            {
                request.setAttribute(KeyConstant.ERROR_MESSAGE, "库单不存在");

                return mapping.findForward("error");
            }
        }

        try
        {
            String id = zjrcManager.addZJRCOut(outBean, map.getParameterMap(), user);

            // 提交
            if (OutConstant.FLOW_DECISION_SUBMIT.equals(saves))
            {
                zjrcManager.submit(id, user);
            }
        }
        catch (MYException e)
        {
            _logger.warn(e, e);

            request.setAttribute(KeyConstant.ERROR_MESSAGE, e.getErrorContent());

            return mapping.findForward("error");
        }

        return null;
    }
	
	/**
	 * deleteZJRCOut
	 * @param mapping
	 * @param form
	 * @param request
	 * @param reponse
	 * @return
	 * @throws ServletException
	 */
	public ActionForward deleteZJRCOut(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse reponse)
	throws ServletException
	{
		String fullId = request.getParameter("outId");

		User user = (User) request.getSession().getAttribute("user");

		if (StringTools.isNullOrNone(fullId))
		{
			request.setAttribute(KeyConstant.ERROR_MESSAGE, "库单不存在，请重新操作");

			return mapping.findForward("error");
		}

		ZJRCOutBean bean = zjrcOutDAO.find(fullId);

		if (bean == null)
		{
			request.setAttribute(KeyConstant.ERROR_MESSAGE, "库单不存在，请重新操作");

			return mapping.findForward("error");
		}

		try
		{
			zjrcManager.delZJRCOut(user, fullId);

			importLog.info(user.getStafferName() + "/" + user.getName() + "删除了库单:"
					+ fullId);

			request.setAttribute(KeyConstant.MESSAGE, "库单删除成功:" + fullId);
		}
		catch (MYException e)
		{
			_logger.warn(e, e);

			request.setAttribute(KeyConstant.ERROR_MESSAGE, "流程异常，请重新操作:"
					+ e.toString());

			return mapping.findForward("error");
		}
		
		CommonTools.removeParamers(request);

		RequestTools.actionInitQuery(request);

		return querySelfZJRCOut(mapping, form, request, reponse);
	}
	
	/**
	 * findZJRCOut
	 * @param mapping
	 * @param form
	 * @param request
	 * @param reponse
	 * @return
	 * @throws ServletException
	 */
	public ActionForward findZJRCOut(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse reponse)
	throws ServletException
	{
        String outId = RequestTools.getValueFromRequest(request, "outId");

        String fow = RequestTools.getValueFromRequest(request, "fow");
        
        String radioIndex = RequestTools.getValueFromRequest(request, "radioIndex");

        CommonTools.saveParamers(request);

        String goback = request.getParameter("goback");

        if (StringTools.isNullOrNone(goback))
        {
            goback = "1";
        }

        request.setAttribute("goback", goback);

        ZJRCOutVO bean = null;
        try
        {
            bean = zjrcOutDAO.findVO(outId);
            
            if (bean == null)
            {
                request.setAttribute(KeyConstant.ERROR_MESSAGE, "库单不存在,请重新操作");

                return mapping.findForward("error");
            }

            List<ZJRCBaseBean> list = zjrcBaseDAO.queryEntityBeansByFK(outId);

            if (ListTools.isEmptyOrNull(list))
            {
                request.setAttribute(KeyConstant.ERROR_MESSAGE, "库单不存在子项,请重新操作");

                return mapping.findForward("error");
            }

            bean.setBaseList(list);

            request.setAttribute("bean", bean);
            
            request.setAttribute("fristBase", list.get(0));

            if (list.size() > 1)
            {
                request.setAttribute("lastBaseList", list.subList(1, list.size()));
            }
        }
        catch (Exception e)
        {
            request.setAttribute(KeyConstant.ERROR_MESSAGE, "查询库单失败");

            _logger.error(e, e);

            return mapping.findForward("error");
        }

        request.setAttribute("flag", "0");
        
        if ( !StringTools.isNullOrNone(radioIndex))
        {
            Map map = (Map)request.getSession().getAttribute("ppmap");

            if (map != null)
            {
                map.put("radioIndex", radioIndex);
            }
        }
        
        // 修改单据
        if ("1".equals(fow))
        {
            if (bean.getStatus() != OutImportConstant.STATUS_SAVE)
            {
                request.setAttribute(KeyConstant.ERROR_MESSAGE, "此状态不能修改单据");

                return mapping.findForward("error");
            }

            return mapping.findForward("updateZJRCOut");
        }

        // 将配送日志显示出来
        List<ZJRCDistributionBean> zjrcdistList = zjrcDistributionDAO.queryEntityBeansByFK(outId);
        
        request.setAttribute("zjrcdistList", zjrcdistList);
        
        // 详细
        return mapping.findForward("detailZJRCOut");
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
	public ActionForward modifyOutStatus(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse reponse)
	throws ServletException
	{
		String fullId = request.getParameter("outId");
		
		User user = (User)request.getSession().getAttribute("user");

        int statuss = Integer.parseInt(request.getParameter("statuss"));

        String oldStatus = request.getParameter("oldStatus");

        if (StringTools.isNullOrNone(oldStatus))
        {
            request.setAttribute(KeyConstant.ERROR_MESSAGE, "没有历史状态,请重新操作!");

            return mapping.findForward("error");
        }

        int ioldStatus = Integer.parseInt(oldStatus);

        CommonTools.saveParamers(request);

       try{
    	   zjrcManager.modifyStatus(fullId, statuss);
    	   
    	   request.setAttribute(KeyConstant.MESSAGE, "操作成功");
       } catch (MYException e) {
           _logger.warn(e, e);

           request.setAttribute(KeyConstant.ERROR_MESSAGE, e.getErrorContent());

           return mapping.findForward("error");
       }
       
       return querySelfZJRCOut(mapping, form, request, reponse);
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
	public ActionForward createOA(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse reponse)
	throws ServletException
	{
		String fullId = request.getParameter("outId");
		
		try{
	    	   zjrcManager.createOneZJRC(fullId);
	    	   
	    	   request.setAttribute(KeyConstant.MESSAGE, "操作成功");
	       } catch (MYException e) {
	           _logger.warn(e, e);

	           request.setAttribute(KeyConstant.ERROR_MESSAGE, e.getErrorContent());

	           return mapping.findForward("error");
	       }
	       
	     return queryZJRCOut(mapping, form, request, reponse);
	}

	/**
	 * @return the zjrcManager
	 */
	public ZJRCManager getZjrcManager()
	{
		return zjrcManager;
	}

	/**
	 * @param zjrcManager the zjrcManager to set
	 */
	public void setZjrcManager(ZJRCManager zjrcManager)
	{
		this.zjrcManager = zjrcManager;
	}

	/**
	 * @return the zjrcProductDAO
	 */
	public ZJRCProductDAO getZjrcProductDAO()
	{
		return zjrcProductDAO;
	}

	/**
	 * @param zjrcProductDAO the zjrcProductDAO to set
	 */
	public void setZjrcProductDAO(ZJRCProductDAO zjrcProductDAO)
	{
		this.zjrcProductDAO = zjrcProductDAO;
	}

	/**
	 * @return the zjrcOutDAO
	 */
	public ZJRCOutDAO getZjrcOutDAO()
	{
		return zjrcOutDAO;
	}

	/**
	 * @param zjrcOutDAO the zjrcOutDAO to set
	 */
	public void setZjrcOutDAO(ZJRCOutDAO zjrcOutDAO)
	{
		this.zjrcOutDAO = zjrcOutDAO;
	}

	/**
	 * @return the zjrcBaseDAO
	 */
	public ZJRCBaseDAO getZjrcBaseDAO()
	{
		return zjrcBaseDAO;
	}

	/**
	 * @param zjrcBaseDAO the zjrcBaseDAO to set
	 */
	public void setZjrcBaseDAO(ZJRCBaseDAO zjrcBaseDAO)
	{
		this.zjrcBaseDAO = zjrcBaseDAO;
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
	 * @return the zjrcDistributionDAO
	 */
	public ZJRCDistributionDAO getZjrcDistributionDAO()
	{
		return zjrcDistributionDAO;
	}

	/**
	 * @param zjrcDistributionDAO the zjrcDistributionDAO to set
	 */
	public void setZjrcDistributionDAO(ZJRCDistributionDAO zjrcDistributionDAO)
	{
		this.zjrcDistributionDAO = zjrcDistributionDAO;
	}
}
