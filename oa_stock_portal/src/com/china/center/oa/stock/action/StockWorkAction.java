package com.china.center.oa.stock.action;

import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
import com.center.china.osgi.publics.file.writer.WriteFile;
import com.center.china.osgi.publics.file.writer.WriteFileFactory;
import com.china.center.actionhelper.common.ActionTools;
import com.china.center.actionhelper.common.JSONPageSeparateTools;
import com.china.center.actionhelper.common.JSONTools;
import com.china.center.actionhelper.common.KeyConstant;
import com.china.center.common.MYException;
import com.china.center.jdbc.util.ConditionParse;
import com.china.center.jdbc.util.PageSeparate;
import com.china.center.oa.publics.Helper;
import com.china.center.oa.stock.bean.StockItemBean;
import com.china.center.oa.stock.bean.StockWorkBean;
import com.china.center.oa.stock.bean.StockWorkCountBean;
import com.china.center.oa.stock.constant.StockConstant;
import com.china.center.oa.stock.dao.StockItemDAO;
import com.china.center.oa.stock.dao.StockWorkCountDAO;
import com.china.center.oa.stock.dao.StockWorkDAO;
import com.china.center.oa.stock.manager.StockWorkManager;
import com.china.center.osgi.jsp.ElTools;
import com.china.center.tools.BeanUtil;
import com.china.center.tools.CommonTools;
import com.china.center.tools.StringTools;
import com.china.center.tools.TimeTools;
import com.china.center.tools.WriteFileBuffer;

/**
 * 
 * 采购跟单
 *
 * @author fangliwen 2014-6-4
 */
public class StockWorkAction extends DispatchAction
{
	private final Log _logger = LogFactory.getLog(getClass());
	
	private StockWorkDAO stockWorkDAO = null;
	
	private StockWorkCountDAO stockWorkCountDAO = null;
	
	private StockWorkManager stockWorkManager = null;
	
	private StockItemDAO stockItemDAO = null;
	
	private final static String QUERYSELFSTOCKWORK = "querySelfStockWork";
	
	private final static String QUERYSTOCKWORK = "queryStockWork";
	
	public StockWorkAction()
	{
	}
	
	/**
	 * querySelfStockWork
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 */
	public ActionForward querySelfStockWork(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws ServletException {
		
		ConditionParse condtion = new ConditionParse();

		condtion.addWhereStr();

		User user = Helper.getUser(request);

		// TEMPLATE 在action里面默认查询条件
		Map<String, String> initMap = initLogTime(request, condtion, true);

		ActionTools.processJSONDataQueryCondition(QUERYSELFSTOCKWORK, request,
				condtion, initMap);

		condtion.addCondition("StockWorkBean.stafferId", "=", user.getStafferId());

		condtion.addCondition("order by StockWorkBean.logTime desc");

		String jsonstr = ActionTools.queryVOByJSONAndToString(
				QUERYSELFSTOCKWORK, request, condtion, this.stockWorkDAO);

		return JSONTools.writeResponse(response, jsonstr);
	}
	
	/**
	 * initLogTime
	 * 
	 * @param request
	 * @param condtion
	 * @return
	 */
	private Map<String, String> initLogTime(HttpServletRequest request,
			ConditionParse condtion, boolean initStatus) {
		
		Map<String, String> changeMap = new HashMap<String, String>();

		String alogTime = request.getParameter("alogTime");

		String blogTime = request.getParameter("blogTime");
		
		if (StringTools.isNullOrNone(alogTime)
				&& StringTools.isNullOrNone(blogTime)) {

			changeMap.put("alogTime", TimeTools.now_short(-30));

			changeMap.put("blogTime", TimeTools.now_short());
			
			if (initStatus) {
				changeMap.put("status", "0");

				condtion.addIntCondition("StockWorkBean.status", "=",
						StockConstant.STOCKWORK_STATUS_WAIT);
			}
			
			condtion.addCondition("StockWorkBean.workDate", ">=",
					TimeTools.now_short(-30));

			condtion.addCondition("StockWorkBean.workDate", "<=",
					TimeTools.now_short());
		}

		return changeMap;
	}
	
	/**
	 * queryStockWork
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 */
	public ActionForward queryStockWork(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws ServletException {
		
		ConditionParse condtion = new ConditionParse();

		condtion.addWhereStr();

		// TEMPLATE 在action里面默认查询条件
		Map<String, String> initMap = initLogTime(request, condtion, false);

		ActionTools.processJSONDataQueryCondition(QUERYSTOCKWORK, request,
				condtion, initMap);
		
		condtion.addCondition("order by StockWorkBean.logTime desc");

		String jsonstr = ActionTools.queryVOByJSONAndToString(
				QUERYSTOCKWORK, request, condtion, this.stockWorkDAO);

		return JSONTools.writeResponse(response, jsonstr);
	}
	
	/**
	 * exportStockWork
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 */
	public ActionForward exportStockWork(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws ServletException
	{
		OutputStream out = null;

		String filenName = "stockWork_" + TimeTools.now("MMddHHmmss") + ".csv";

		response.setContentType("application/x-dbf");

		response.setHeader("Content-Disposition", "attachment; filename="
				+ filenName);

		WriteFile write = null;

		ConditionParse condtion = JSONPageSeparateTools.getCondition(request,
				QUERYSTOCKWORK);
		
		String newContion = condtion.toString().trim();
		
		if (newContion.toUpperCase().indexOf("ORDER BY") != -1) {
			 newContion = newContion.substring(0, newContion.toUpperCase().indexOf("ORDER BY") - 1);

			 newContion += " ORDER BY StockWorkBean.stockid, StockWorkBean.stockItemId, StockWorkBean.logtime desc";
		 }

		int count = stockWorkDAO.countVOByCondition(newContion);

		if (count > 150000)
		{
			return ActionTools.toError("导出数量大于150000,请重新选择时间段导出", mapping, request);
		}

		// 只取同stockid, stockitemid下 logtime 最大的一条
		Set<String> uset = new HashSet<String>();
		
		try
		{
			out = response.getOutputStream();

			write = WriteFileFactory.getMyTXTWriter();

			write.openFile(out);

			write.writeLine("采购单号,采购商品,数量,供应商,是否按计划继续,供应商发货日期,原供应商发货日期,备注");
			
			PageSeparate page = new PageSeparate();

			page.reset2(count, 2000);

			WriteFileBuffer line = new WriteFileBuffer(write);

			while (page.nextPage())
			{
				List<StockWorkBean> voFList = stockWorkDAO.queryEntityVOsByCondition(
						newContion, page);

				for (StockWorkBean each : voFList)
				{
					if (uset.contains(each.getStockId() + "-" + each.getStockItemId()))
						continue;
					
					uset.add(each.getStockId() + "-" + each.getStockItemId());
					
					line.reset();

					line.writeColumn(each.getStockId());
					
					line.writeColumn(each.getProductName());
					
					StockItemBean item = stockItemDAO.find(each.getStockItemId());
					
					if (null != item)
						line.writeColumn(item.getAmount());
					else
						line.writeColumn(0);
					
					line.writeColumn(each.getProvideName());
					line.writeColumn(ElTools.get("hasRef", each.getFollowPlan()));
					
//					line.writeColumn(each.getProvideConfirmDate());
					line.writeColumn(each.getNewSendDate());
					
					// 取第一条
//					List<StockWorkBean> innerBeanList = stockWorkDAO.queryEntityBeansByCondition("where stockid=? and stockItemid=? order by logtime", each.getStockId(), each.getStockItemId());
					
//					line.writeColumn(innerBeanList.get(0).getProvideConfirmDate());
					line.writeColumn(each.getConfirmSendDate());
					line.writeColumn(each.getDescription());
					
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
	 * findStockWork
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 */
	public ActionForward findStockWork(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) 
	throws ServletException 
	{
		String id = request.getParameter("id");

		String update = request.getParameter("update");

		StockWorkBean bean = stockWorkDAO.findVO(id);
		
		if (bean == null) {
			request.setAttribute(KeyConstant.ERROR_MESSAGE, "数据异常,请重新操作");

			return mapping.findForward("error");
		}

		request.setAttribute("bean", bean);
		
		// 获取跟单次数
		StockWorkCountBean countBean = stockWorkCountDAO.findByUnique(bean.getStockId(), bean.getStockItemId());
		
		int count = 0;
		if (null != countBean)
		{
			count = countBean.getCount();
		}

		request.setAttribute("count", count);
		
		if ("1".equals(update)) {
			return mapping.findForward("handleStockWork");
		}

		return mapping.findForward("detailStockWork");
	}
	
	/**
	 * 跟单任务处理
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 */
	public ActionForward handleStockWork(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) 
	throws ServletException 
	{
        StockWorkBean bean = new StockWorkBean();

        try
        {
            BeanUtil.getBean(bean, request);

            User user = Helper.getUser(request);

            stockWorkManager.handleStockWork(user.getId(), bean);

            request.setAttribute(KeyConstant.MESSAGE, "成功操作");
        }
        catch (MYException e)
        {
            _logger.warn(e, e);

            request.setAttribute(KeyConstant.ERROR_MESSAGE, "操作失败:" + e.getMessage());
        }

        CommonTools.removeParamers(request);

        return mapping.findForward("querySelfStockWork");
    }

	/**
	 * @return the stockWorkDAO
	 */
	public StockWorkDAO getStockWorkDAO()
	{
		return stockWorkDAO;
	}

	/**
	 * @param stockWorkDAO the stockWorkDAO to set
	 */
	public void setStockWorkDAO(StockWorkDAO stockWorkDAO)
	{
		this.stockWorkDAO = stockWorkDAO;
	}

	/**
	 * @return the stockWorkCountDAO
	 */
	public StockWorkCountDAO getStockWorkCountDAO()
	{
		return stockWorkCountDAO;
	}

	/**
	 * @param stockWorkCountDAO the stockWorkCountDAO to set
	 */
	public void setStockWorkCountDAO(StockWorkCountDAO stockWorkCountDAO)
	{
		this.stockWorkCountDAO = stockWorkCountDAO;
	}

	/**
	 * @return the stockWorkManager
	 */
	public StockWorkManager getStockWorkManager()
	{
		return stockWorkManager;
	}

	/**
	 * @param stockWorkManager the stockWorkManager to set
	 */
	public void setStockWorkManager(StockWorkManager stockWorkManager)
	{
		this.stockWorkManager = stockWorkManager;
	}

	/**
	 * @return the stockItemDAO
	 */
	public StockItemDAO getStockItemDAO() {
		return stockItemDAO;
	}

	/**
	 * @param stockItemDAO the stockItemDAO to set
	 */
	public void setStockItemDAO(StockItemDAO stockItemDAO) {
		this.stockItemDAO = stockItemDAO;
	}
}
