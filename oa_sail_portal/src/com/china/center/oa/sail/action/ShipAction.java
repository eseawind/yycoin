package com.china.center.oa.sail.action;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
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
import com.china.center.actionhelper.common.ActionTools;
import com.china.center.actionhelper.common.JSONTools;
import com.china.center.actionhelper.common.KeyConstant;
import com.china.center.actionhelper.common.OldPageSeparateTools;
import com.china.center.actionhelper.json.AjaxResult;
import com.china.center.actionhelper.query.HandleResult;
import com.china.center.common.MYException;
import com.china.center.common.taglib.DefinedCommon;
import com.china.center.jdbc.annosql.constant.AnoConstant;
import com.china.center.jdbc.util.ConditionParse;
import com.china.center.jdbc.util.PageSeparate;
import com.china.center.oa.product.bean.ComposeProductBean;
import com.china.center.oa.product.bean.DepotBean;
import com.china.center.oa.product.bean.ProductBean;
import com.china.center.oa.product.constant.ProductConstant;
import com.china.center.oa.product.dao.ComposeItemDAO;
import com.china.center.oa.product.dao.ComposeProductDAO;
import com.china.center.oa.product.dao.DepotDAO;
import com.china.center.oa.product.dao.ProductDAO;
import com.china.center.oa.product.vo.ComposeItemVO;
import com.china.center.oa.publics.Helper;
import com.china.center.oa.publics.bean.AuthBean;
import com.china.center.oa.publics.constant.AuthConstant;
import com.china.center.oa.publics.manager.UserManager;
import com.china.center.oa.sail.bean.ExpressBean;
import com.china.center.oa.sail.bean.OutBean;
import com.china.center.oa.sail.bean.OutImportBean;
import com.china.center.oa.sail.bean.PackageItemBean;
import com.china.center.oa.sail.bean.PackageVSCustomerBean;
import com.china.center.oa.sail.constanst.OutConstant;
import com.china.center.oa.sail.constanst.ShipConstant;
import com.china.center.oa.sail.dao.BaseDAO;
import com.china.center.oa.sail.dao.DistributionDAO;
import com.china.center.oa.sail.dao.ExpressDAO;
import com.china.center.oa.sail.dao.OutDAO;
import com.china.center.oa.sail.dao.OutImportDAO;
import com.china.center.oa.sail.dao.PackageDAO;
import com.china.center.oa.sail.dao.PackageItemDAO;
import com.china.center.oa.sail.dao.PackageVSCustomerDAO;
import com.china.center.oa.sail.manager.ShipManager;
import com.china.center.oa.sail.vo.PackageVO;
import com.china.center.oa.sail.wrap.PackageWrap;
import com.china.center.oa.sail.wrap.PickupWrap;
import com.china.center.tools.CommonTools;
import com.china.center.tools.ListTools;
import com.china.center.tools.MathTools;
import com.china.center.tools.RequestTools;
import com.china.center.tools.StringTools;
import com.china.center.tools.TimeTools;

public class ShipAction extends DispatchAction
{
	private final Log _logger = LogFactory.getLog(getClass());
	
	private PackageDAO packageDAO = null;
	
	private PackageItemDAO packageItemDAO = null;
	
	private OutDAO outDAO = null;
	
	private BaseDAO baseDAO = null;
	
	private DistributionDAO distributionDAO = null;
	
	private ExpressDAO expressDAO = null;
	
	private ShipManager shipManager = null;
	
	private ProductDAO productDAO = null;
	
	private ComposeProductDAO composeProductDAO = null;
	
	private ComposeItemDAO composeItemDAO = null;
	
	private OutImportDAO outImportDAO = null;
	
	private PackageVSCustomerDAO packageVSCustomerDAO = null;
	
	private UserManager userManager = null;
	
	private DepotDAO depotDAO = null;
	
	private final static String QUERYPACKAGE = "queryPackage";
	
	private final static String QUERYPICKUP = "queryPickup";

	/**
	 * default construct
	 */
	public ShipAction()
	{
	}
	
	/**
     * queryPackage
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward queryPackage(ActionMapping mapping, ActionForm form,
                                                HttpServletRequest request,
                                                HttpServletResponse response)
        throws ServletException
    {
    	User user = Helper.getUser(request);
    	
        ConditionParse condtion = new ConditionParse();

        condtion.addWhereStr();
        
        // 要根据仓库的权限,计算出地点,根据地点查询
        setDepotCondotionInOut(user, condtion);
        
        // TEMPLATE 在action里面默认查询条件
		Map<String, String> initMap = initLogTime(request, condtion, true);

		ActionTools.processJSONDataQueryCondition(QUERYPACKAGE, request, condtion, initMap);
		
        condtion.addCondition("order by CustomerBean.name");
        
        String jsonstr = ActionTools.queryVOByJSONAndToString(QUERYPACKAGE, request, condtion, this.packageDAO,
        		new HandleResult<PackageVO>()
                {
					public void handle(PackageVO vo)
					{
						vo.setPay(DefinedCommon.getValue("deliverPay", vo.getExpressPay()) + "/" + DefinedCommon.getValue("deliverPay", vo.getTransportPay()));
					}
        	
                });

        return JSONTools.writeResponse(response, jsonstr);
    }
    
    /**
	 * 设置仓库的过滤条件
	 * 
	 * @param user
	 * @param condtion
	 */
	private void setDepotCondotionInOut(User user, ConditionParse condtion)
	{
		// 只能看到自己的仓库
		List<AuthBean> depotAuthList = userManager.queryExpandAuthById(
				user.getId(), AuthConstant.EXPAND_AUTH_DEPOT);

		if (ListTools.isEmptyOrNull(depotAuthList))
		{
			// 永远也没有结果
			condtion.addFlaseCondition();
		}
		else
		{
			StringBuffer sb = new StringBuffer();

			sb.append("and (");
			for (Iterator<AuthBean> iterator = depotAuthList.iterator(); iterator
					.hasNext();)
			{
				AuthBean authBean = (AuthBean) iterator.next();
				
				// 接受仓库是自己管辖的
				if (iterator.hasNext())
				{
					// 根据仓库找出地点
					DepotBean dept = depotDAO.find(authBean.getId());
					
					if (null != dept)
					{
						sb.append("PackageBean.locationId = '" + dept.getIndustryId2()
								+ "' or ");
					}
				}
				else
				{
					// 根据仓库找出地点
					DepotBean dept = depotDAO.find(authBean.getId());
					
					if (null != dept)
					{
						sb.append("PackageBean.locationId = '" + dept.getIndustryId2() + "'");
					}
					else
					{
						sb.append("1=1");
					}
				}
			}

			sb.append(") ");

			condtion.addCondition(sb.toString());
		}
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
			changeMap.put("alogTime", TimeTools.now_short(-7));

			changeMap.put("blogTime", TimeTools.now_short());

			if (initStatus) {
				changeMap.put("status",	String.valueOf(ShipConstant.SHIP_STATUS_INIT));

				condtion.addIntCondition("PackageBean.status", "=", ShipConstant.SHIP_STATUS_INIT);
			}

			condtion.addCondition("PackageBean.logTime", ">=",
					TimeTools.now_short(-7) + " 00:00:00");

			condtion.addCondition("PackageBean.logTime", "<=",
					TimeTools.now_short() + " 23:59:59");
		}

		return changeMap;
	}
    
    /**
     * preForQueryPickup
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward preForQueryPickup(ActionMapping mapping, ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response)
	throws ServletException
	{
    	prepare(request);
    	
    	return mapping.findForward("queryPickup");
    }
    
    private void prepare(HttpServletRequest request)
    {
    	List<ExpressBean> expressList = expressDAO.listEntityBeansByOrder("order by id");

    	request.setAttribute("expressList", expressList);
    }
    
    /**
     * queryPickup
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward queryPickup(ActionMapping mapping, ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response)
	throws ServletException
	{
		User user = (User) request.getSession().getAttribute("user");

		List<PackageVO> list = null;

		CommonTools.saveParamers(request);

		try
		{
			if (OldPageSeparateTools.isFirstLoad(request))
			{
				ConditionParse condtion = getQueryPickupCondition(request,user);

				int tatol = packageDAO.countByCon(condtion);

				PageSeparate page = new PageSeparate(tatol,	30);

				OldPageSeparateTools.initPageSeparate(condtion, page, request,QUERYPICKUP);

				list = packageDAO.queryVOsByCon(condtion, page);
			}
			else
			{
				OldPageSeparateTools.processSeparate(request,
						QUERYPICKUP);

				list = packageDAO.queryVOsByCon(OldPageSeparateTools.getCondition(request, QUERYPICKUP), OldPageSeparateTools
								.getPageSeparate(request, QUERYPICKUP));
			}
		}
		catch (Exception e)
		{
			request.setAttribute(KeyConstant.ERROR_MESSAGE, "查询单据失败");

			_logger.error(e, e);

			return mapping.findForward("error");
		}

		// 对结果集包装
		List<PickupWrap> wrapList = new ArrayList<PickupWrap>(); 
		
		Set<String> set = new HashSet<String>();
		
		for (PackageVO each : list)
		{
			if (!set.contains(each.getPickupId()))
			{
				set.add(each.getPickupId());
			}
		}
		
		for (String pickupId : set)
		{
			List<PackageVO> voList = packageDAO.queryEntityVOsByFK(pickupId);
			
			 // 从小到大
	        Collections.sort(voList, new Comparator<PackageVO>() {
	            public int compare(PackageVO o1, PackageVO o2) {
	                return o1.getIndex_pos() - o2.getIndex_pos();
	            }
	        });
	        
	        PickupWrap wrap = new PickupWrap();
			
			wrap.setPickupId(pickupId);
			wrap.setPackageList(voList);
			
			wrapList.add(wrap);
		}
		
		request.setAttribute("itemList", wrapList);

		List<ExpressBean> expressList = expressDAO.listEntityBeansByOrder("order by id");

		request.setAttribute("expressList", expressList);
		
		return mapping.findForward("queryPickup");
	}
    
    /**
	 * getQuerySelfBalanceCondition
	 * 
	 * @param request
	 * @param user
	 * @return
	 */
	private ConditionParse getQueryPickupCondition(HttpServletRequest request, User user)
	{
		Map<String, String> queryOutCondtionMap = CommonTools.saveParamersToMap(request);
		
		ConditionParse condtion = new ConditionParse();

		//condtion.addWhereStr();

		String batchId = request.getParameter("batchId");

		String shipment = request.getParameter("shipment");

		if (!StringTools.isNullOrNone(batchId))
		{
			condtion.addCondition("PackageBean.pickupId", "like", batchId);
			
			queryOutCondtionMap.put("batchId", batchId);
		}

		int shipping = -1;
		
		if (!StringTools.isNullOrNone(shipment))
		{
			shipping = MathTools.parseInt(shipment);
			
			condtion.addIntCondition("PackageBean.shipping", "=", shipping);
			
			queryOutCondtionMap.put("shipment", shipment);
		}

		String transport1 = request.getParameter("transport1");

		String transport2 = request.getParameter("transport2");

		if (!StringTools.isNullOrNone(transport1))
		{
			condtion.addIntCondition("PackageBean.transport1", "=", MathTools.parseInt(transport1));
			
			queryOutCondtionMap.put("transport1", transport1);
		}

		if (!StringTools.isNullOrNone(transport2))
		{
			condtion.addIntCondition("PackageBean.transport2", "=", MathTools.parseInt(transport2));
			
			queryOutCondtionMap.put("transport2", transport2);			
		}

		String packageId = request.getParameter("packageId");

		if (!StringTools.isNullOrNone(packageId))
		{
			condtion.addCondition("PackageBean.id", "like", packageId.trim());
			
			queryOutCondtionMap.put("packageId", packageId.trim());
		}
		
		String receiver = request.getParameter("receiver");

		if (!StringTools.isNullOrNone(receiver))
		{
			condtion.addCondition("PackageBean.receiver", "like", receiver.trim());
			
			queryOutCondtionMap.put("receiver", receiver.trim());
		}
		
		String mobile = request.getParameter("mobile");

		if (!StringTools.isNullOrNone(mobile))
		{
			condtion.addCondition("PackageBean.mobile", "like", mobile.trim());
			
			queryOutCondtionMap.put("mobile", mobile.trim());
		}
		
		String location = request.getParameter("location");

		if (!StringTools.isNullOrNone(location))
		{
			condtion.addCondition("PackageBean.locationId", "like", location.trim());
			
			queryOutCondtionMap.put("location", location.trim());
		}

		String status = request.getParameter("currentStatus");

		if (!StringTools.isNullOrNone(status))
		{
			if (status.equals("4"))
				condtion.addCondition(" and PackageBean.status in (1,3)");
			else
				condtion.addIntCondition("PackageBean.status", "=", MathTools.parseInt(status));
			
			queryOutCondtionMap.put("currentStatus",status);
		}else
		{
			condtion.addCondition(" and PackageBean.status in (1,3)");
			
			queryOutCondtionMap.put("currentStatus","4");
		}

		// 事业部
		String industryName = request.getParameter("industryName");
		if (!StringTools.isNullOrNone(industryName))
		{
			condtion.addCondition("PackageBean.industryName", "=", industryName);
			
			queryOutCondtionMap.put("industryName",industryName);
		}
		
		// 销售单
		String outId = request.getParameter("outId");
		if (!StringTools.isNullOrNone(outId))
		{
			condtion.addCondition("PackageItemBean.outid", "like", outId);
			
			queryOutCondtionMap.put("outId",outId);
		}
		
		// 销售单 紧急 
		String emergency = request.getParameter("emergency");
		if (!StringTools.isNullOrNone(emergency))
		{
			condtion.addIntCondition("PackageItemBean.emergency", "=", emergency);
			
			queryOutCondtionMap.put("emergency", emergency);
		}
		
		setDepotCondotionInOut(user, condtion);
		
		condtion.addCondition("order by PackageBean.logTime desc");

		request.getSession().setAttribute("ppmap", queryOutCondtionMap);
		
		return condtion;
	}
	
    /**
     * addPickup
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward addPickup(ActionMapping mapping, ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response)
	throws ServletException
	{
    	User user = Helper.getUser(request);
    	
    	AjaxResult ajax = new AjaxResult();
    	
    	// separate by ~
    	String packageIds = request.getParameter("packageIds");
    	
    	try{
    		shipManager.addPickup(user, packageIds);
    		
    		ajax.setSuccess("拣配成功");
    	}catch(MYException e)
    	{
    		_logger.warn(e, e);
    		
    		ajax.setError("拣配出错:"+ e.getErrorContent());
    	}
    	
    	return JSONTools.writeResponse(response, ajax);
    }
    
    /**
     * deletePackage
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward deletePackage(ActionMapping mapping, ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response)
	throws ServletException
	{
    	User user = Helper.getUser(request);
    	
    	AjaxResult ajax = new AjaxResult();
    	
    	// separate by ~
    	String packageIds = request.getParameter("packageIds");
    	
    	try{
    		shipManager.deletePackage(user, packageIds);
    		
    		ajax.setSuccess("撤销成功");
    	}catch(MYException e)
    	{
    		_logger.warn(e, e);
    		
    		ajax.setError("撤销出错:"+ e.getErrorContent());
    	}
    	
    	return JSONTools.writeResponse(response, ajax);
    }
    
    /**
     * findPackage
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward findPackage(ActionMapping mapping, ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response)
	throws ServletException
	{
    	String packageId = request.getParameter("packageId");
    	
    	PackageVO vo = packageDAO.findVO(packageId);
    	
    	if (null == vo)
    	{
    		 request.setAttribute(KeyConstant.ERROR_MESSAGE, "出库单不存在");
    		
    		return mapping.findForward("error");
    	}
    	
    	List<PackageItemBean> itemList = packageItemDAO.queryEntityBeansByFK(packageId);
    	
    	vo.setItemList(itemList);
    	
    	request.setAttribute("bean", vo);
    	
    	return mapping.findForward("detailPackage");
    }
    
    /**
     * findPickup
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward findPickup(ActionMapping mapping, ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response)
	throws ServletException
	{
    	CommonTools.saveParamers(request);
    	
    	String pickupId = request.getParameter("pickupId");
    	
    	String compose = request.getParameter("compose");
    	
    	if (StringTools.isNullOrNone(compose))
    		compose = "1";
    	
    	// 根据拣配单(批次单) 生成一张批次出库单
    	List<PackageVO> packageList = packageDAO.queryEntityVOsByFK(pickupId);
    	
    	StringBuilder sb = new StringBuilder();
    	
    	Map<String, PackageItemBean> map = new HashMap<String, PackageItemBean>();
    	
    	int pickupCount = 0;
    	
    	pickupCount = packageList.size();
    	
    	for (PackageVO each : packageList)
    	{
    		sb.append(each.getId()).append("<br>");
    		
    		List<PackageItemBean> itemList = packageItemDAO.queryEntityBeansByFK(each.getId());
    		
    		// 根据产品分组: 1.判断是否为合成,如是,则要找出子产品;2.数量合并
    		for (PackageItemBean eachItem : itemList)
    		{
    			if (map.containsKey(eachItem.getProductId()))
    			{
    				PackageItemBean itemBean = map.get(eachItem.getProductId());
    				
    				itemBean.setAmount(itemBean.getAmount() + eachItem.getAmount());
    			}else{
    				PackageItemBean itemBean = new PackageItemBean();
    				
    				itemBean.setProductId(eachItem.getProductId());
    				itemBean.setProductName(eachItem.getProductName());
    				itemBean.setAmount(eachItem.getAmount());
    				//itemBean.setShowSubProductName(showSubProductName);
    				
    				checkCompose(eachItem, itemBean, compose);
    				
    				map.put(eachItem.getProductId(), itemBean);
    			}
    		}
    	}
    	
    	PackageVO batchVO = new PackageVO();
    	
    	batchVO.setId(sb.toString());
    	batchVO.setPickupId(pickupId);
    	batchVO.setRepTime(TimeTools.now_short());
    	
    	List<PackageItemBean> lastList = new ArrayList<PackageItemBean>();
    	
    	for (Entry<String, PackageItemBean> entry : map.entrySet())
    	{
    		lastList.add(entry.getValue());
    	}

    	batchVO.setItemList(lastList);
    	
    	// key:以批次号做为key ?
    	request.setAttribute("bean", batchVO);
    	
    	request.setAttribute("year", TimeTools.now("yyyy"));
        request.setAttribute("month", TimeTools.now("MM"));
        request.setAttribute("day", TimeTools.now("dd"));
    	
    	request.setAttribute("index_pos", 0);
    	
    	request.setAttribute("pickupCount", pickupCount);
    	
    	request.setAttribute("compose", compose);

    	return mapping.findForward("printPickup");
    }

	private void checkCompose(PackageItemBean eachItem, PackageItemBean itemBean, String com)
	{
		if ("1".equals(com))
		{
			ProductBean product = productDAO.find(eachItem.getProductId());
			
			if (null != product){
				if (product.getCtype() == ProductConstant.CTYPE_YES)
				{
					ComposeProductBean compose = composeProductDAO.queryLatestByProduct(eachItem.getProductId());
					
					if (null != compose)
					{
						List<ComposeItemVO> citemList = composeItemDAO.queryEntityVOsByFK(compose.getId());
						
						StringBuilder sb2 = new StringBuilder();
						
						for (ComposeItemVO eachc : citemList)
						{
							sb2.append(eachc.getProductName()).append("<br>");
						}
						
						itemBean.setShowSubProductName(sb2.toString());
					}
				}
			}
		}
	}
	
	/**
     * findNextPackage
     * 
     * 根据批次号(拣配号)及index获取包package
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward findNextPackage(ActionMapping mapping, ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response)
	throws ServletException
	{
    	CommonTools.saveParamers(request);
    	
    	String pickupId = request.getParameter("pickupId");
    	
    	String sindex_pos = request.getParameter("index_pos");
    	
    	String printMode = request.getParameter("printMode");
    	
    	// 0 连打 1 单打
    	String printSmode = request.getParameter("printSmode");
    	
    	if (StringTools.isNullOrNone(printSmode))
    		printSmode = "";
    	
    	String compose = request.getParameter("compose");
    	
    	if (StringTools.isNullOrNone(compose))
    		compose = "1";
    	
    	int index_pos = 0;
    	
    	if (!StringTools.isNullOrNone(sindex_pos))
    	{
    		index_pos = MathTools.parseInt(sindex_pos);
    	}
    	
    	index_pos += 1;
    	
    	ConditionParse condtion = new ConditionParse();
    	
    	condtion.addWhereStr();
    	
    	condtion.addCondition("PackageBean.pickupId", "=", pickupId);
    	condtion.addIntCondition("PackageBean.index_pos", "=", index_pos);
    	
    	List<PackageVO> packageList = packageDAO.queryVOsByCondition(condtion);
    	
    	if (ListTools.isEmptyOrNull(packageList) || packageList.size() > 1)
    	{
    		// 只有连打时,才跳转到回执单打印
    		if (printMode.equals("0") && printSmode.equals("0"))
    		{
    			CommonTools.removeParamers(request);
    			
    			request.setAttribute("pickupId2", pickupId);
    			
    			request.setAttribute("compose1", compose);
    			
    			return findOutForReceipt(mapping, form, request, response);
    		}else
    		{
        		request.setAttribute(KeyConstant.ERROR_MESSAGE, "已打印完毕");
        		
        		return mapping.findForward("error");
    		}
    	}
    	
    	PackageVO vo = packageList.get(0);
    	
    	Map<String, PackageItemBean> map = new HashMap<String, PackageItemBean>();
    	
    	Map<String, PackageWrap> map1 = new HashMap<String, PackageWrap>();
    	
    	List<PackageItemBean> itemList = packageItemDAO.queryEntityBeansByFK(vo.getId());
		
		// 根据产品分组: 1.判断是否为合成,如是,则要找出子产品;2.数量合并
		for (PackageItemBean eachItem : itemList)
		{
			if (map.containsKey(eachItem.getProductId()))
			{
				PackageItemBean itemBean = map.get(eachItem.getProductId());
				
				itemBean.setAmount(itemBean.getAmount() + eachItem.getAmount());
			}else{
				PackageItemBean itemBean = new PackageItemBean();
				
				itemBean.setProductId(eachItem.getProductId());
				itemBean.setProductName(eachItem.getProductName());
				itemBean.setAmount(eachItem.getAmount());
				//itemBean.setShowSubProductName(showSubProductName);
				
				checkCompose(eachItem, itemBean, compose);
				
				map.put(eachItem.getProductId(), itemBean);
			}
			
			if (!map1.containsKey(eachItem.getOutId()))
			{
				PackageWrap wrap = new PackageWrap();
				
				wrap.setOutId(eachItem.getOutId());
				wrap.setDescription(eachItem.getDescription());
				
				map1.put(eachItem.getOutId(), wrap);
			}
		}
		
    	vo.setRepTime(TimeTools.now_short());
    	
    	List<PackageItemBean> lastList = new ArrayList<PackageItemBean>();
    	
    	for (Entry<String, PackageItemBean> entry : map.entrySet())
    	{
    		lastList.add(entry.getValue());
    	}

    	vo.setItemList(lastList);
    	
    	List<PackageWrap> wrapList = new ArrayList<PackageWrap>();
    	
    	for (Entry<String, PackageWrap> entry : map1.entrySet())
    	{
    		wrapList.add(entry.getValue());
    	}

    	vo.setItemList(lastList);
    	
    	vo.setWrapList(wrapList);
    	
    	request.setAttribute("bean", vo);
    	
    	request.setAttribute("year", TimeTools.now("yyyy"));
        request.setAttribute("month", TimeTools.now("MM"));
        request.setAttribute("day", TimeTools.now("dd"));
    	
    	request.setAttribute("index_pos", index_pos);
    	
    	request.setAttribute("compose", compose);
    	
    	return mapping.findForward("printPackage");
	}

    /**
     * 显示要打印的回执单
     * 邮政、浦发、中信
     * 一个包(package)一个打印单
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward findOutForReceipt(ActionMapping mapping, ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response)
	throws ServletException
	{
    	CommonTools.saveParamers(request);
    	
    	String compose =  RequestTools.getValueFromRequest(request, "compose");
    	
    	String compose1 =  RequestTools.getValueFromRequest(request, "compose1");

    	if (!StringTools.isNullOrNone(compose1))
    		compose = compose1;
    	
    	String pickupId =  RequestTools.getValueFromRequest(request, "pickupId");
    	
    	String pickupId2 =  RequestTools.getValueFromRequest(request, "pickupId2");

    	// 第一次跳转
    	if (!StringTools.isNullOrNone(pickupId2))
    	{
    		pickupId = pickupId2;
    	}
    	
    	String sindex_pos =  RequestTools.getValueFromRequest(request, "index_pos");
    	
    	int index_pos = 0;
    	
    	if (!StringTools.isNullOrNone(sindex_pos))
    	{
    		index_pos = MathTools.parseInt(sindex_pos);
    	}
    	
    	String packageId =  RequestTools.getValueFromRequest(request, "packageId");
    	
    	// 第一次打印时，找出第一个出库单，一个出库单对应多个客户 1:n
    	if (index_pos == 0)
    	{
    		ConditionParse condtion = new ConditionParse();
        	
        	condtion.addWhereStr();
        	
        	condtion.addCondition("PackageBean.pickupId", "=", pickupId);
        	condtion.addIntCondition("PackageBean.index_pos", "=", 1);
        	
        	List<PackageVO> packageList = packageDAO.queryVOsByCondition(condtion);
        	
        	if (ListTools.isEmptyOrNull(packageList) || packageList.size() > 1)
        	{
        		request.setAttribute(KeyConstant.ERROR_MESSAGE, "已打印完毕");
        		
        		return mapping.findForward("error");
        	}
        	
        	packageId = packageList.get(0).getId();
        	
        	index_pos = 1;
    	}
    	
    	String subindex_pos =  request.getParameter("subindex_pos");
    	
    	int subindexpos = 0;
    	
    	if (!StringTools.isNullOrNone(subindex_pos))
    	{
    		subindexpos = MathTools.parseInt(subindex_pos);
    	}
    	
    	subindexpos += 1;
    	
    	String customerId = "";
    	String customerName = "";
    	
    	ConditionParse con = new ConditionParse();
    	
    	con.addWhereStr();
    	
    	con.addCondition("PackageVSCustomerBean.packageId", "=", packageId);
    	con.addIntCondition("PackageVSCustomerBean.indexPos", "=", subindexpos);
    	//System.out.println("=======con========" + con.toString());
    	List<PackageVSCustomerBean> vsList = packageVSCustomerDAO.queryEntityBeansByCondition(con);
    	
    	if (!ListTools.isEmptyOrNull(vsList))
    	{
    		customerId = vsList.get(0).getCustomerId();
    		customerName = vsList.get(0).getCustomerName();
    		
    		request.setAttribute("subindex_pos", subindexpos);
    	}else
    	{
    		// 更新状态
    		try
			{
				shipManager.updatePrintStatus(pickupId, index_pos);
			}
			catch (MYException e)
			{
				request.setAttribute(KeyConstant.ERROR_MESSAGE, "已打印出错." + e.getErrorContent());
	    		
	    		return mapping.findForward("error");
			}
    		
    		index_pos += 1;
    	}
    	
    	ConditionParse condtion = new ConditionParse();
    	
    	condtion.addWhereStr();
    	
    	condtion.addCondition("PackageBean.pickupId", "=", pickupId);
    	condtion.addIntCondition("PackageBean.index_pos", "=", index_pos);
    	
    	List<PackageVO> packageList = packageDAO.queryVOsByCondition(condtion);
    	
    	if (ListTools.isEmptyOrNull(packageList) || packageList.size() > 1)
    	{
    		request.setAttribute(KeyConstant.ERROR_MESSAGE, "已打印完毕");
    		
    		return mapping.findForward("error");
    	}
    	
    	// 取出包
    	PackageVO vo = packageList.get(0);
    	
    	vo.setRepTime(TimeTools.now_short());
    	
    	vo.setCustomerId(customerId);
    	vo.setCustomerName(customerName);
    	
    	if (StringTools.isNullOrNone(customerId))
    	{
    		ConditionParse con1 = new ConditionParse();
        	
        	con1.addWhereStr();
        	
        	con1.addCondition("PackageVSCustomerBean.packageId", "=", vo.getId());
        	con1.addIntCondition("PackageVSCustomerBean.indexPos", "=", 1);
        	//System.out.println("=======con1========" + con1.toString());
        	List<PackageVSCustomerBean> vsList1 = packageVSCustomerDAO.queryEntityBeansByCondition(con1);
        	
        	if (!ListTools.isEmptyOrNull(vsList1))
        	{
        		customerId = vsList1.get(0).getCustomerId();
        		customerName = vsList1.get(0).getCustomerName();
        		
            	vo.setCustomerId(customerId);
            	vo.setCustomerName(customerName);
            	
            	request.setAttribute("subindex_pos", 1);
        	}
    	}
    	
    	List<PackageItemBean> itemList = packageItemDAO.
    			queryEntityBeansByCondition(" where PackageItemBean.packageId = ? order by PackageItemBean.productName", vo.getId()); //  .queryEntityBeansByFK(vo.getId());
    	
    	request.setAttribute("bean", vo);
    	
    	request.setAttribute("pickupId", pickupId);
    	
    	request.setAttribute("index_pos", index_pos);
    	
    	request.setAttribute("compose", compose);
    	
    	request.setAttribute("year", TimeTools.now("yyyy"));
        request.setAttribute("month", TimeTools.now("MM"));
        request.setAttribute("day", TimeTools.now("dd"));
    	
    	int totalAmount = 0;
    	double total = 0.0d;
    	
    	if (vo.getIndustryName().indexOf("邮政") != -1)
    	{
    		request.setAttribute("packageId", vo.getId());
    		
    		List<PackageItemBean> itemList1 = new ArrayList<PackageItemBean>();
    		
    		Map<String, PackageItemBean> map1 = new HashMap<String, PackageItemBean>();
    		
    		for (PackageItemBean each : itemList)
    		{
    			if (!each.getCustomerId().equals(vo.getCustomerId()))
    			{
    				continue;
    			}
    			
    			String key = each.getProductId()+ "~" + each.getPrice();
    			
    			if (!map1.containsKey(key))
    			{
    				checkCompose(each, each, compose);
    				
    				map1.put(each.getProductId(), each);
    			}else{
    				PackageItemBean itemBean = map1.get(key);
    				
    				itemBean.setAmount(itemBean.getAmount() + each.getAmount());
    				itemBean.setValue(itemBean.getValue() + each.getValue());
    				
    				itemBean.setOutId(itemBean.getOutId() + "<br>" + each.getOutId());
    			}
    			
    			total += each.getValue();
    		}
    		
    		for(Entry<String, PackageItemBean> each : map1.entrySet())
    		{
    			itemList1.add(each.getValue());
    		}
    		
    		vo.setItemList(itemList1);
    		
    		request.setAttribute("total", total);
    		
    		return mapping.findForward("printPostReceipt");
    	}else if (vo.getCustomerName().indexOf("中信银行") != -1 || vo.getCustomerName().indexOf("招商银行") != -1)
    	{
    		request.setAttribute("packageId", vo.getId());
    		
    		request.setAttribute("title", "永银文化创意产业发展有限责任公司产品发货清单");

    		prepareForBankPrint(request, vo, itemList, compose);
    		
    		return mapping.findForward("printBankReceipt");
    		
    	}else if (vo.getCustomerName().indexOf("浦发银行") != -1)
    	{
    		request.setAttribute("packageId", vo.getId());
    		
    		request.setAttribute("title", "北京黄金交易中心有限公司贵金属产品发货清单");
    		
    		//for (PackageItemBean each : itemList)
    		for (Iterator<PackageItemBean> iterator = itemList.iterator(); iterator.hasNext();)	
    		{
    			PackageItemBean each = iterator.next();
    			
    			if (!each.getCustomerId().equals(vo.getCustomerId()))
    			{
    				iterator.remove();
    				
    				continue;
    			}
    			
    			List<OutImportBean> outiList = outImportDAO.queryEntityBeansByFK(each.getOutId(), AnoConstant.FK_FIRST);
    			
    			if (!ListTools.isEmptyOrNull(outiList))
				{
					each.setRefId(outiList.get(0).getCiticNo());
					each.setOutTime(outiList.get(0).getCiticOrderDate());
				}
    			
    			if (each.getDescription().indexOf("赠品") != -1)
				{
					each.setDescription("赠品");
				}else
				{
					each.setDescription(each.getPrintText());
				}
    			
    			totalAmount += each.getAmount();
    		}
    		
    		vo.setItemList(itemList);
    		
    		request.setAttribute("total", totalAmount);
    		
    		return mapping.findForward("printPufaReceipt");
    		
    	}else{  // 打印发货单
    		//request.setAttribute("packageId", "None");
    		request.setAttribute("packageId", vo.getId());
    		
    		List<PackageItemBean> itemList1 = new ArrayList<PackageItemBean>();
    		
    		Map<String, PackageItemBean> map1 = new HashMap<String, PackageItemBean>();
    		
    		for (PackageItemBean each : itemList)
    		{
    			String key = each.getProductId();
    			
    			if (!map1.containsKey(key))
    			{
    				map1.put(each.getProductId(), each);
    			}else{
    				PackageItemBean itemBean = map1.get(key);
    				
    				itemBean.setAmount(itemBean.getAmount() + each.getAmount());
    			}
    		}
    		
    		for(Entry<String, PackageItemBean> each : map1.entrySet())
    		{
    			itemList1.add(each.getValue());
    		}
    		
    		vo.setItemList(itemList1);
    		
    		return mapping.findForward("printShipment");
    	}
	}

    /**
     * prepareForBankPrint
     * 
     * @param request
     * @param vo
     * @param itemList
     */
	private void prepareForBankPrint(HttpServletRequest request, PackageVO vo,
			List<PackageItemBean> itemList, String compose)
	{
		int totalAmount = 0 ;
		
		List<PackageItemBean> itemList1 = new ArrayList<PackageItemBean>();
		
		Map<String, PackageItemBean> map1 = new HashMap<String, PackageItemBean>();
		
		for (PackageItemBean each : itemList)
		{
			if (!each.getCustomerId().equals(vo.getCustomerId()))
			{
				continue;
			}
			
			// 针对赠品,且有备注的订单,单独显示
			String outId = each.getOutId();
			
			OutBean out = outDAO.find(outId);
			
			if (out != null && out.getOutType() == OutConstant.OUTTYPE_OUT_PRESENT)
			{
				List<OutImportBean> outiList = outImportDAO.queryEntityBeansByFK(each.getOutId(), AnoConstant.FK_FIRST);
				
				if (!ListTools.isEmptyOrNull(outiList))
				{
					each.setRefId(outiList.get(0).getCiticNo());
					
					if (!StringTools.isNullOrNone(outiList.get(0).getDescription()))
					{
						checkCompose(each, each, compose);

						each.setDescription(outiList.get(0).getDescription());
						
						itemList1.add(each);
						
						totalAmount += each.getAmount();
						
						continue;
					}
				}
			}
			
			String key = each.getProductId();
			
			if (!map1.containsKey(key))
			{
				checkCompose(each, each, compose);
				
				List<OutImportBean> outiList = outImportDAO.queryEntityBeansByFK(each.getOutId(), AnoConstant.FK_FIRST);
				
				if (!ListTools.isEmptyOrNull(outiList))
				{
					each.setRefId(outiList.get(0).getCiticNo());
				}
				
				each.setDescription("");
				
				map1.put(each.getProductId(), each);
			}else{
				PackageItemBean itemBean = map1.get(key);
				
				itemBean.setAmount(itemBean.getAmount() + each.getAmount());
				
				itemBean.setOutId(itemBean.getOutId() + "<br>" + each.getOutId());
				
				if (!StringTools.isNullOrNone(itemBean.getRefId()))
				{
					if (!StringTools.isNullOrNone(each.getRefId()))
					{
						itemBean.setRefId(itemBean.getRefId() + "<br>" + each.getRefId());
					}
				}else{
					if (!StringTools.isNullOrNone(each.getRefId()))
					{
						itemBean.setRefId(each.getRefId());
					}
				}
			}
			
			totalAmount += each.getAmount();
		}
		
		for(Entry<String, PackageItemBean> each : map1.entrySet())
		{
			itemList1.add(each.getValue());
		}
		
		vo.setItemList(itemList1);
		
		request.setAttribute("total", totalAmount);
	}
    
	/**
	 * updateStatus
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 */
	public ActionForward updateStatus(ActionMapping mapping, ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response)
	throws ServletException
	{
		String pickupId = request.getParameter("pickupId");
		
		User user = Helper.getUser(request);
		
		AjaxResult ajax = new AjaxResult();
		
		try{
			shipManager.updateStatus(user, pickupId);
			
			ajax.setSuccess("更新成功");
		}catch(MYException e)
		{
			_logger.warn(e, e);
			
			ajax.setError("更新失败");
		}
		
		return JSONTools.writeResponse(response, ajax);
	}
	
	/**
	 * mUpdateStatus
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 */
	public ActionForward mUpdateStatus(ActionMapping mapping, ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response)
	throws ServletException
	{
		String pickupId = request.getParameter("pickupId");
		
		User user = Helper.getUser(request);
		
		try{
			shipManager.updateStatus(user, pickupId);
			
			request.setAttribute(KeyConstant.MESSAGE, "发货确认成功");
		}catch(MYException e)
		{
			_logger.warn(e, e);
			
			request.setAttribute(KeyConstant.ERROR_MESSAGE, "确认发货失败");
		}
		
		return mapping.findForward("queryPickup");
	}
	
	public PackageDAO getPackageDAO()
	{
		return packageDAO;
	}

	public void setPackageDAO(PackageDAO packageDAO)
	{
		this.packageDAO = packageDAO;
	}

	public PackageItemDAO getPackageItemDAO()
	{
		return packageItemDAO;
	}

	public void setPackageItemDAO(PackageItemDAO packageItemDAO)
	{
		this.packageItemDAO = packageItemDAO;
	}

	public OutDAO getOutDAO()
	{
		return outDAO;
	}

	public void setOutDAO(OutDAO outDAO)
	{
		this.outDAO = outDAO;
	}

	public BaseDAO getBaseDAO()
	{
		return baseDAO;
	}

	public void setBaseDAO(BaseDAO baseDAO)
	{
		this.baseDAO = baseDAO;
	}

	public DistributionDAO getDistributionDAO()
	{
		return distributionDAO;
	}

	public void setDistributionDAO(DistributionDAO distributionDAO)
	{
		this.distributionDAO = distributionDAO;
	}

	public ExpressDAO getExpressDAO()
	{
		return expressDAO;
	}

	public void setExpressDAO(ExpressDAO expressDAO)
	{
		this.expressDAO = expressDAO;
	}

	public ShipManager getShipManager()
	{
		return shipManager;
	}

	public void setShipManager(ShipManager shipManager)
	{
		this.shipManager = shipManager;
	}

	public ProductDAO getProductDAO()
	{
		return productDAO;
	}

	public void setProductDAO(ProductDAO productDAO)
	{
		this.productDAO = productDAO;
	}

	public ComposeProductDAO getComposeProductDAO()
	{
		return composeProductDAO;
	}

	public void setComposeProductDAO(ComposeProductDAO composeProductDAO)
	{
		this.composeProductDAO = composeProductDAO;
	}

	public ComposeItemDAO getComposeItemDAO()
	{
		return composeItemDAO;
	}

	public void setComposeItemDAO(ComposeItemDAO composeItemDAO)
	{
		this.composeItemDAO = composeItemDAO;
	}

	/**
	 * @return the outImportDAO
	 */
	public OutImportDAO getOutImportDAO()
	{
		return outImportDAO;
	}

	/**
	 * @param outImportDAO the outImportDAO to set
	 */
	public void setOutImportDAO(OutImportDAO outImportDAO)
	{
		this.outImportDAO = outImportDAO;
	}

	/**
	 * @return the packageVSCustomerDAO
	 */
	public PackageVSCustomerDAO getPackageVSCustomerDAO()
	{
		return packageVSCustomerDAO;
	}

	/**
	 * @param packageVSCustomerDAO the packageVSCustomerDAO to set
	 */
	public void setPackageVSCustomerDAO(PackageVSCustomerDAO packageVSCustomerDAO)
	{
		this.packageVSCustomerDAO = packageVSCustomerDAO;
	}

	/**
	 * @return the userManager
	 */
	public UserManager getUserManager()
	{
		return userManager;
	}

	/**
	 * @param userManager the userManager to set
	 */
	public void setUserManager(UserManager userManager)
	{
		this.userManager = userManager;
	}

	/**
	 * @return the depotDAO
	 */
	public DepotDAO getDepotDAO()
	{
		return depotDAO;
	}

	/**
	 * @param depotDAO the depotDAO to set
	 */
	public void setDepotDAO(DepotDAO depotDAO)
	{
		this.depotDAO = depotDAO;
	}
}
