package com.china.center.oa.openservice.action;

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

import com.china.center.actionhelper.common.JSONTools;
import com.china.center.actionhelper.jsonimpl.JSONException;
import com.china.center.actionhelper.jsonimpl.JSONObject;
import com.china.center.common.MYException;
import com.china.center.common.taglib.DefinedCommon;
import com.china.center.jdbc.util.ConditionParse;
import com.china.center.oa.client.bean.AppUserBean;
import com.china.center.oa.client.dao.AppUserDAO;
import com.china.center.oa.client.manager.AppUserManager;
import com.china.center.oa.client.vo.AppUserVO;
import com.china.center.oa.openservice.constant.OpenServiceConstant;
import com.china.center.oa.openservice.service.result.AppResult;
import com.china.center.oa.openservice.util.JsonMapper;
import com.china.center.oa.sail.bean.AppOutBean;
import com.china.center.oa.sail.dao.AppBaseDAO;
import com.china.center.oa.sail.dao.AppDistributionDAO;
import com.china.center.oa.sail.dao.AppInvoiceDAO;
import com.china.center.oa.sail.dao.AppOutDAO;
import com.china.center.oa.sail.manager.AppOutManager;
import com.china.center.oa.sail.wrap.OrderListResult;
import com.china.center.oa.sail.wrap.QueryActivityOutput;
import com.china.center.oa.sail.wrap.QueryPriceInput;
import com.china.center.oa.sail.wrap.QueryPriceOutput;
import com.china.center.oa.sail.wrap.SingleOrderResult;
import com.china.center.oa.sail.wrap.Wrap;
import com.china.center.tools.StringTools;
import com.china.center.tools.TimeTools;

/**
 * 
 * 手机终端 http POST方式接口
 * 1.只支持手机终端APP的接入 ?
 * 2.访问安全性问题?
 *
 * @author fangliwen 2013-8-5
 */
public class AppsServiceAction extends DispatchAction
{
	private final Log _logger = LogFactory.getLog(getClass());
	
	private AppUserManager appUserManager = null;
	
	private AppOutManager appOutManager = null;
	
	private AppUserDAO appUserDAO = null;
	
	private AppOutDAO appOutDAO = null;
	
	private AppBaseDAO appBaseDAO = null;
	
	private AppInvoiceDAO appInvoiceDAO = null;
	
	private AppDistributionDAO appDistributionDAO = null;
	
	/**
	 * 创建、修改手机用户
	 * @param mapping
	 * @param form
	 * @param request
	 * @param reponse
	 * @return
	 * @throws ServletException
	 */
    public ActionForward createOrModifyUser(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse reponse)
    throws ServletException
    {
    	_logger.info("appsServiceAction.createOrModifyUser entrance...");
    	
    	String jsonParam = request.getParameter("params");
    	
    	_logger.info("appsServiceAction.createOrModifyUser jsonParams:" + jsonParam);
    	
    	JsonMapper mapper = new JsonMapper();
    	
    	// 解析 JSON (简单的JSONObject)
    	AppUserBean appUser = mapper.fromJson(jsonParam, AppUserBean.class);
    	_logger.info("createOrModifyUser.AppUserBean:"+appUser);
    	AppResult result = new AppResult();
    	
    	AppUserVO retAppUser = null;
    	
    	try
    	{
    		retAppUser = appUserManager.createOrModifyUser(appUser);

    		result.setSuccessAndObj("操作成功", retAppUser);
    	}
    	catch(MYException e)
    	{
    		_logger.warn(e, e);
    		
    		result.setError("创建失败" + e.getErrorContent());
    	}
    	
    	String jsonstr = mapper.toJson(result);
    	
    	_logger.info("appsServiceAction.createOrModifyUser exit...");
    	
    	return JSONTools.writeResponse(reponse, jsonstr);
    }

    /**
     * 用户登陆
     * @param mapping
     * @param form
     * @param request
     * @param reponse
     * @return
     * @throws ServletException
     */
    public ActionForward userLogin(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse reponse)
    throws ServletException
    {
    	_logger.info("appsServiceAction.userLogin entrance...");
    	
    	String jsonParam = request.getParameter("params");
    	
    	_logger.info("appsServiceAction.userLogin jsonParams:" + jsonParam);
    	
    	JsonMapper mapper = new JsonMapper();
    	
    	AppResult result = new AppResult();
    	
    	String loginName = "";
    	String password = "";
    	
    	// JSONObject 解析出用户名与密码
    	try
		{
			JSONObject jsonObj = new JSONObject(jsonParam);
			
			loginName = jsonObj.getString("loginName");
			password = jsonObj.getString("password");
		}
		catch (JSONException e)
		{
			_logger.warn(e, e);
			
			result.setError("参数解析出错");
			
			return JSONTools.writeResponse(reponse, mapper.toJson(result));
		}
    	
		AppUserVO appUser = appUserDAO.findVOByUnique(loginName);
		
		if (null == appUser)
		{
			result.setRetCode(OpenServiceConstant.USERNAMENOTEXISTS);
			result.setRetMsg("用户名不存在");

			return JSONTools.writeResponse(reponse, mapper.toJson(result));
		}
		
		if (!appUser.getPassword().equals(password))
		{
			result.setRetCode(OpenServiceConstant.USERPASSWORDWRONG);
			result.setRetMsg("密码错误");
			
			return JSONTools.writeResponse(reponse, mapper.toJson(result));
		}
		
		appUser.setStatusVal(DefinedCommon.getValue("userStatus", appUser.getStatus()));

		// TODO 信用额度 预收款 应收款
		result.setSuccessAndObj("登陆成功", appUser);
		
		_logger.info("appsServiceAction.userLogin exit...");
		
		return JSONTools.writeResponse(reponse, mapper.toJson(result));
    }
    
    /**
     * 密码修改
     * @param mapping
     * @param form
     * @param request
     * @param reponse
     * @return
     * @throws ServletException
     */
    public ActionForward modifyPassword(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse reponse)
    throws ServletException
    {
    	_logger.info("appsServiceAction.modifyPassword entrance...");
    	
    	String jsonParam = request.getParameter("params");
    	
    	_logger.info("appsServiceAction.modifyPassword jsonParams:" + jsonParam);
    	
    	JsonMapper mapper = new JsonMapper();
    	
    	AppResult result = new AppResult();
    	
    	String userId = "";
    	String orignalPassword = "";
    	String newPassword = "";
    	
    	// JSONObject 解析出用户名与密码
    	try
		{
			JSONObject jsonObj = new JSONObject(jsonParam);
			
			userId = jsonObj.getString("userId");
			orignalPassword = jsonObj.getString("orignalPassword");
			newPassword = jsonObj.getString("newPassword");
		}
		catch (JSONException e)
		{
			_logger.warn(e, e);
			
			result.setError("参数解析出错");
			
			return JSONTools.writeResponse(reponse, mapper.toJson(result));
		}
		
		try
		{
			appUserManager.modifyPassword(userId, orignalPassword, newPassword);
			
			result.setSuccess("操作成功");
		}
		catch(MYException e)
		{
			_logger.warn(e, e);
			
			result.setError("操作失败，原因：" + e.getErrorContent());
		}
		
		_logger.info("appsServiceAction.modifyPassword exit...");
		
    	return JSONTools.writeResponse(reponse, mapper.toJson(result));
    }
    
    /**
     * 价格查询
     * @param mapping
     * @param form
     * @param request
     * @param reponse
     * @return
     * @throws ServletException
     */
    public ActionForward queryPrice(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse reponse)
    throws ServletException
    {
    	_logger.info("appsServiceAction.queryPrice entrance...");
    	
    	String jsonParam = request.getParameter("params");
    	
    	_logger.info("appsServiceAction.queryPrice jsonParams:" + jsonParam);
    	
    	JsonMapper mapper = new JsonMapper();
    	
    	AppResult result = new AppResult();
    	
    	QueryPriceInput input = mapper.fromJson(jsonParam, QueryPriceInput.class);

    	List<Wrap> products = input.getProducts();
    	
    	String userId = input.getUserId();

    	List<QueryPriceOutput> output = null;
    	
		try
		{
			output = appOutManager.queryPrice(userId, products);
			
			result.setSuccessAndObj("查询成功", output);
		}
		catch (MYException e)
		{
			_logger.warn(e, e);
			
			result.setError("操作失败，原因：" + e.getErrorContent());
		}
    	
		_logger.info("appsServiceAction.queryPrice exit...");
		
    	return JSONTools.writeResponse(reponse, mapper.toJson(result));
    }
    
    /**
     * 活动查询
     * @param mapping
     * @param form
     * @param request
     * @param reponse
     * @return
     * @throws ServletException
     */
    @Deprecated
    public ActionForward queryActivity(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse reponse)
    throws ServletException
    {
    	_logger.info("appsServiceAction.queryActivity entrance...");
    	
    	String jsonParam = request.getParameter("params");
    	
    	_logger.info("appsServiceAction.queryActivity jsonParams:" + jsonParam);
    	
    	AppResult result = new AppResult();
    	
    	JsonMapper mapper = new JsonMapper();
    	
    	String activityId = "";
    	
    	try
		{
			JSONObject jsonObj = new JSONObject(jsonParam);
			
			activityId = jsonObj.getString("activityId");
		}
		catch (JSONException e)
		{
			_logger.warn(e, e);
			
			result.setError("参数解析出错");
			
			return JSONTools.writeResponse(reponse, mapper.toJson(result));
		}
    	
		QueryActivityOutput output = null;
		
		try
		{
			output = appOutManager.queryActivity(activityId);
			
			if (output.getProducts().size() == 0)
			{
				result.setError("没有找到对应活动的商品明细.");
			}
			else
			{
				result.setSuccessAndObj("查询成功", output);
			}
		}
		catch (MYException e)
		{
			_logger.warn(e, e);
			
			result.setError("操作失败，原因：" + e.getErrorContent());
		}
		
		_logger.info("appsServiceAction.queryActivity exit...");
		
    	return JSONTools.writeResponse(reponse, mapper.toJson(result));
    }
    
    /**
     * 订单创建
     * @param mapping
     * @param form
     * @param request
     * @param reponse
     * @return
     * @throws ServletException
     */
    public ActionForward createOrder(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse reponse)
    throws ServletException
    {
    	_logger.info("appsServiceAction.createOrder entrance...");
    	
    	String jsonParam = request.getParameter("params");
    	
    	_logger.info("appsServiceAction.createOrder jsonParams:" + jsonParam);
    	
    	JsonMapper mapper = new JsonMapper();
    	
    	// 解析 JSON (简单的JSONObject)
    	AppOutBean appOut = mapper.fromJson(jsonParam, AppOutBean.class);
    	
    	AppResult result = new AppResult();
    	
    	SingleOrderResult orderResult = null;
    	
    	try
    	{
    		orderResult = appOutManager.createOrder(appOut);
    		
    		result.setSuccessAndObj("操作成功", orderResult);
    	}
    	catch(MYException e)
    	{
    		_logger.warn(e, e);
    		
    		result.setError("创建失败" + e.getErrorContent());
    	}
    	
    	String jsonstr = mapper.toJson(result);
    	
    	_logger.info("appsService.createOrder exit...");
    	
    	return JSONTools.writeResponse(reponse, jsonstr);
    }
    
    /**
     * 订单列表查询
     * @param mapping
     * @param form
     * @param request
     * @param reponse
     * @return
     * @throws ServletException
     */
    public ActionForward queryOrderList(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse reponse)
    throws ServletException
    {
    	_logger.info("appsServiceAction.queryOrderList entrance...");
    	
    	String jsonParam = request.getParameter("params");
    	
    	_logger.info("appsServiceAction.queryOrderList jsonParams:" + jsonParam);
    	
    	AppResult result = new AppResult();
    	
    	JsonMapper mapper = new JsonMapper();
    	
    	String userId = "", orderNo = "", productName = "", begin = "", end = "";
    	
    	try
		{
			JSONObject jsonObj = new JSONObject(jsonParam);
			
			userId = jsonObj.getString("userId");
			orderNo = jsonObj.getString("orderNo");
			productName = jsonObj.getString("productName");
			begin = jsonObj.getString("begin");
			end = jsonObj.getString("end");
		}
		catch (JSONException e)
		{
			_logger.warn(e, e);
			
			result.setError("参数解析出错");
			
			return JSONTools.writeResponse(reponse, mapper.toJson(result));
		}
    	
		ConditionParse con = new ConditionParse();
		
		setCondition(userId, orderNo, productName, begin, end, con);
		
		try
		{
			List<OrderListResult> outputList = appOutManager.queryOrderList(con);
			
			result.setSuccessAndObj("查询成功", outputList);
		}
		catch (MYException e)
		{
			_logger.warn(e,e);
			
			result.setError("查询失败");
		}
    	
		_logger.info("appsServiceAction.queryOrderList exit...");
		
    	return JSONTools.writeResponse(reponse, mapper.toJson(result));
    }

	private void setCondition(String userId, String orderNo,
			String productName, String begin, String end, ConditionParse con)
	{
		// MUST exist
		con.addCondition("appout.userId", "=", userId);
		
		if (!StringTools.isNullOrNone(orderNo))
		{
			con.addCondition("appout.orderNo", "like", orderNo);
		}
		
		if (!StringTools.isNullOrNone(productName))
		{
			con.addCondition("appbase.productName", "like", productName);
		}
		
		if (!StringTools.isNullOrNone(begin))
		{
			con.addCondition("appout.outDate", ">=", begin);
		}
		else
		{
			con.addCondition("appout.outDate", ">=", TimeTools.now_short(-7));
		}
		
		if (!StringTools.isNullOrNone(end))
		{
			con.addCondition("appout.outDate", "<=", end);
		}
		else
		{
			con.addCondition("appout.outDate", "<=", TimeTools.now_short());
		}
	}
    
    /**
     * 订单明细查询
     * @param mapping
     * @param form
     * @param request
     * @param reponse
     * @return
     * @throws ServletException
     */
    public ActionForward queryOrderDetail(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse reponse)
    throws ServletException
    {
    	_logger.info("appsServiceAction.queryOrderDetail entrance...");
    	
    	String jsonParam = request.getParameter("params");
    	
    	_logger.info("appsServiceAction.queryOrderDetail jsonParams:" + jsonParam);
    	
    	AppResult result = new AppResult();
    	
    	JsonMapper mapper = new JsonMapper();
    	
    	String userId = "", orderNo = "" ;
    	
    	try
		{
			JSONObject jsonObj = new JSONObject(jsonParam);
			
			userId = jsonObj.getString("userId");
			orderNo = jsonObj.getString("orderNo");
		}
		catch (JSONException e)
		{
			_logger.warn(e, e);
			
			result.setError("参数解析出错");
			
			return JSONTools.writeResponse(reponse, mapper.toJson(result));
		}
		
		AppOutBean bean = null;
		
		try
		{
			bean = appOutManager.queryOrderDetail(orderNo);
			
		}catch(MYException e)
		{
			_logger.warn(e, e);
			
			result.setError("查询失败");
		}
		
		if (null == bean)
		{
			result.setError("查询失败，没有找到销售单");
		}else
		{
			result.setSuccessAndObj("查询成功", bean);
		}
		
		_logger.info("用户：" + userId + ",appsServiceAction.queryOrderDetail exit...");
		
    	return JSONTools.writeResponse(reponse, mapper.toJson(result));
    }

	public AppUserDAO getAppUserDAO()
	{
		return appUserDAO;
	}

	public void setAppUserDAO(AppUserDAO appUserDAO)
	{
		this.appUserDAO = appUserDAO;
	}

	public AppUserManager getAppUserManager()
	{
		return appUserManager;
	}

	public void setAppUserManager(AppUserManager appUserManager)
	{
		this.appUserManager = appUserManager;
	}

	public AppOutManager getAppOutManager()
	{
		return appOutManager;
	}

	public void setAppOutManager(AppOutManager appOutManager)
	{
		this.appOutManager = appOutManager;
	}

	public AppOutDAO getAppOutDAO()
	{
		return appOutDAO;
	}

	public void setAppOutDAO(AppOutDAO appOutDAO)
	{
		this.appOutDAO = appOutDAO;
	}

	public AppBaseDAO getAppBaseDAO()
	{
		return appBaseDAO;
	}

	public void setAppBaseDAO(AppBaseDAO appBaseDAO)
	{
		this.appBaseDAO = appBaseDAO;
	}

	public AppInvoiceDAO getAppInvoiceDAO()
	{
		return appInvoiceDAO;
	}

	public void setAppInvoiceDAO(AppInvoiceDAO appInvoiceDAO)
	{
		this.appInvoiceDAO = appInvoiceDAO;
	}

	public AppDistributionDAO getAppDistributionDAO()
	{
		return appDistributionDAO;
	}

	public void setAppDistributionDAO(AppDistributionDAO appDistributionDAO)
	{
		this.appDistributionDAO = appDistributionDAO;
	}
}
