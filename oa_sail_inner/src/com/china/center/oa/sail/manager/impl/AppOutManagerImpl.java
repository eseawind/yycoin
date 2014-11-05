package com.china.center.oa.sail.manager.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

import com.china.center.common.MYException;
import com.china.center.common.taglib.DefinedCommon;
import com.china.center.jdbc.util.ConditionParse;
import com.china.center.oa.client.bean.AppUserBean;
import com.china.center.oa.client.bean.AppUserVSCustomerBean;
import com.china.center.oa.client.dao.AppUserDAO;
import com.china.center.oa.client.dao.AppUserVSCustomerDAO;
import com.china.center.oa.client.dao.StafferVSCustomerDAO;
import com.china.center.oa.client.vo.StafferVSCustomerVO;
import com.china.center.oa.client.vs.StafferVSCustomerBean;
import com.china.center.oa.customer.constant.CustomerConstant;
import com.china.center.oa.product.bean.PriceConfigBean;
import com.china.center.oa.product.bean.ProductBean;
import com.china.center.oa.product.constant.ProductConstant;
import com.china.center.oa.product.dao.PriceConfigDAO;
import com.china.center.oa.product.dao.ProductDAO;
import com.china.center.oa.product.manager.PriceConfigManager;
import com.china.center.oa.publics.bean.FlowLogBean;
import com.china.center.oa.publics.bean.StafferBean;
import com.china.center.oa.publics.constant.PublicConstant;
import com.china.center.oa.publics.constant.SysConfigConstant;
import com.china.center.oa.publics.dao.CommonDAO;
import com.china.center.oa.publics.dao.FlowLogDAO;
import com.china.center.oa.publics.dao.ParameterDAO;
import com.china.center.oa.publics.dao.StafferDAO;
import com.china.center.oa.publics.manager.CommonMailManager;
import com.china.center.oa.sail.bean.AppBaseBean;
import com.china.center.oa.sail.bean.AppDistributionBean;
import com.china.center.oa.sail.bean.AppInvoiceBean;
import com.china.center.oa.sail.bean.AppOutBean;
import com.china.center.oa.sail.bean.AppOutVSOutBean;
import com.china.center.oa.sail.bean.BaseBean;
import com.china.center.oa.sail.bean.DistributionBean;
import com.china.center.oa.sail.bean.OutBean;
import com.china.center.oa.sail.bean.PromotionBean;
import com.china.center.oa.sail.bean.PromotionItemBean;
import com.china.center.oa.sail.bean.SailConfBean;
import com.china.center.oa.sail.constanst.OutConstant;
import com.china.center.oa.sail.constanst.OutImportConstant;
import com.china.center.oa.sail.dao.AppBaseDAO;
import com.china.center.oa.sail.dao.AppDistributionDAO;
import com.china.center.oa.sail.dao.AppInvoiceDAO;
import com.china.center.oa.sail.dao.AppOutDAO;
import com.china.center.oa.sail.dao.AppOutVSOutDAO;
import com.china.center.oa.sail.dao.BaseDAO;
import com.china.center.oa.sail.dao.DistributionDAO;
import com.china.center.oa.sail.dao.OutDAO;
import com.china.center.oa.sail.dao.PromotionDAO;
import com.china.center.oa.sail.dao.PromotionItemDAO;
import com.china.center.oa.sail.manager.AppOutManager;
import com.china.center.oa.sail.manager.SailConfigManager;
import com.china.center.oa.sail.vo.AppOutVO;
import com.china.center.oa.sail.wrap.OrderListResult;
import com.china.center.oa.sail.wrap.QueryActivityOutput;
import com.china.center.oa.sail.wrap.QueryPriceOutput;
import com.china.center.oa.sail.wrap.SingleOrderResult;
import com.china.center.oa.sail.wrap.Wrap;
import com.china.center.tools.JudgeTools;
import com.china.center.tools.ListTools;
import com.china.center.tools.MathTools;
import com.china.center.tools.StringTools;
import com.china.center.tools.TimeTools;

public class AppOutManagerImpl implements AppOutManager
{
	private final Log _logger = LogFactory.getLog(this.getClass());
	
	private final Log triggerLog = LogFactory.getLog("trigger");
	
	private ProductDAO productDAO = null;
	
	private PriceConfigDAO priceConfigDAO = null;
	
	private PriceConfigManager priceConfigManager = null;
	
	private PromotionDAO promotionDAO = null;
	
	private PromotionItemDAO promotionItemDAO = null;
	
	private CommonDAO commonDAO = null;
	
	private AppOutDAO  appOutDAO = null;
	
	private AppBaseDAO appBaseDAO = null;
	
	private AppInvoiceDAO appInvoiceDAO = null;
	
	private AppDistributionDAO appDistributionDAO = null;
	
	private OutDAO outDAO = null;
	
	private BaseDAO baseDAO = null;
	
	private DistributionDAO distributionDAO = null;
	
	private AppUserVSCustomerDAO appUserVSCustomerDAO = null;
	
	private AppUserDAO appUserDAO = null;
	
	private AppOutVSOutDAO appOutVSOutDAO = null;
	
	private StafferVSCustomerDAO stafferVSCustomerDAO = null;
	
	private StafferDAO stafferDAO = null;
	
	private SailConfigManager sailConfigManager = null;
	
	private FlowLogDAO flowLogDAO = null;
	
	private CommonMailManager commonMailManager = null;
	
	private ParameterDAO parameterDAO = null;
	
	private PlatformTransactionManager transactionManager = null;
	
	public AppOutManagerImpl()
	{
		
	}
	
	/**
	 * 价格查询
	 * {@inheritDoc}
	 */
	public List<QueryPriceOutput> queryPrice(String userId, List<Wrap> products)
			throws MYException
	{
		JudgeTools.judgeParameterIsNull(userId, products);
		
		List<QueryPriceOutput> outputList = new ArrayList<QueryPriceOutput>();

		AppUserBean userBean = appUserDAO.find(userId);
		
		if (null == userBean)
		{
			throw new MYException("用户不存在");
		}
		
		if (userBean.getStatus() != CustomerConstant.USER_STATUS_AVAILABLE)
		{
			throw new MYException("非有效用户");
		}
		
		// 找到用户对应的客户， 客户对应的业务员， 业务员对应事业部
		AppUserVSCustomerBean appVSCust = appUserVSCustomerDAO.findByUnique(userId);
		
		if (null == appVSCust)
		{
			throw new MYException("用户信息不完整");
		}
		
		StafferVSCustomerBean  staffVSCust = stafferVSCustomerDAO.findByUnique(appVSCust.getCustomerId());
		
		if (null == staffVSCust)
		{
			throw new MYException("用户信息不完整");
		}
		
		StafferBean staffer = stafferDAO.find(staffVSCust.getStafferId());
		
		if (null == staffer)
		{
			throw new MYException("用户信息不完整");
		}
		
		String industryId = staffer.getIndustryId();
		
		if (!ListTools.isEmptyOrNull(products))
		{
			for (Wrap each : products)
			{
				QueryPriceOutput output = new QueryPriceOutput();

				output.setProductCode(each.getProductCode());
				output.setProductName(each.getProductName());

				ProductBean product = productDAO.findByUnique(each
						.getProductCode());

				if (null == product)
				{
					continue;
				}

				// 根据配置获取销售价
				output.setPrice("0.00");
				
				List<PriceConfigBean> pcblist = priceConfigDAO.queryMinPricebyProductIdAndIndustryId(product.getId(), industryId);

				if (!ListTools.isEmptyOrNull(pcblist))
				{
					output.setPrice(MathTools.formatNum(pcblist.get(0).getMinPrice()));
				}

				outputList.add(output);
			}
		}

		return outputList;
	}

	/**
	 * 查询活动商品
	 * {@inheritDoc}
	 */
	public QueryActivityOutput queryActivity(String activityId)
			throws MYException
	{
		QueryActivityOutput output = new QueryActivityOutput();

		PromotionBean pb = promotionDAO.find(activityId);

		if (null == pb)
		{
			throw new MYException("活动不存在");
		}

		output.setActivityId(activityId);
		output.setActivityName(pb.getName());
		output.setDescription(pb.getDescription());

		List<Wrap> products = new ArrayList<Wrap>();

		output.setProducts(products);
		
		List<PromotionItemBean> itemList = promotionItemDAO
				.queryEntityBeansByFK(activityId);

		for (PromotionItemBean each : itemList)
		{
			String productId = each.getProductId();

			if (StringTools.isNullOrNone(productId)) continue;

			ProductBean pbean = productDAO.find(productId);

			if (null == pbean) continue;

			Wrap wrap = new Wrap();

			wrap.setProductCode(pbean.getCode());
			wrap.setProductName(pbean.getName());

			products.add(wrap);
		}

		return output;
	}

	/**
	 * 创建订单
	 * {@inheritDoc}
	 */
	@Transactional(rollbackFor = MYException.class)
	public SingleOrderResult createOrder(AppOutBean bean) throws MYException
	{
		JudgeTools.judgeParameterIsNull(bean);
		
		SingleOrderResult orderResult = new SingleOrderResult();
		
		List<AppBaseBean> items = bean.getItems();
		
		AppInvoiceBean invoice = bean.getInvoice();
		
		AppDistributionBean dist = bean.getDistribution();
		
		checkOrder(bean);
		
		String id = commonDAO.getSquenceString();
		
		// AP means APP creates Orders
		String orderNo = commonDAO.getSquenceString20("AP");
		
		bean.setId(id);
		
		bean.setOrderNo(orderNo);
		
		bean.setOutDate(TimeTools.now_short());
		bean.setOutTime(TimeTools.now());
		bean.setStatus(1);
		
		double total = 0.0d;
		
		for (AppBaseBean each : items)
		{
			ProductBean pb = productDAO.findByUnique(each.getProductCode());
			
			if (null == pb)
			{
				throw new MYException("商品 %s 不存在", each.getProductName());
			}
			
			each.setId(commonDAO.getSquenceString());
			
			each.setProductId(pb.getId());
			
			each.setProductName(pb.getName());
			
			each.setOrderNo(orderNo);
			
			each.setMoney(each.getAmount() * each.getPrice()); 
			
			total += each.getMoney();
		}
		
		bean.setTotal(total);
		
		appOutDAO.saveEntityBean(bean);
		
		appBaseDAO.saveAllEntityBeans(items);
		
		if (invoice != null)
		{
			invoice.setId(commonDAO.getSquenceString20());
			invoice.setOrderNo(orderNo);
			
			appInvoiceDAO.saveEntityBean(invoice);
		}
		
		dist.setId(commonDAO.getSquenceString20());
		dist.setOrderNo(orderNo);
		
		appDistributionDAO.saveEntityBean(dist);
		
		_logger.info("App 订单:" + orderNo);
		
		orderResult.setOrderNo(orderNo);
		orderResult.setStatus(DefinedCommon.getValue("appOutStatus", bean.getStatus()));
		orderResult.setSale("0.0");
		orderResult.setPay(MathTools.formatNum2(total));
		orderResult.setPayAccount("");
		orderResult.setPayStatus("未付款");
		orderResult.setOutTime(bean.getOutTime());
		
		return orderResult;
	}

	private void checkOrder(AppOutBean bean) throws MYException
	{
		if (StringTools.isNullOrNone(bean.getUserId()))
		{
			throw new MYException("信息不完整，用户信息不能为空.");
		}
		
		List<AppBaseBean> items = bean.getItems();
		
		if (items.size() == 0)
		{
			throw new MYException("信息不完整,没有产品明细");
		}
		
		for (AppBaseBean each : items)
		{
			if (StringTools.isNullOrNone(each.getProductCode()))
			{
				throw new MYException("信息不完整,没有产品编码.");
			}
			
			if (each.getAmount() <= 0)
			{
				throw new MYException("信息不完整,没有输入数量");
			}
			
			if (each.getPrice() <= 0)
			{
				throw new MYException("信息不完整,没有产品价格");
			}
		}
		
		AppDistributionBean dist = bean.getDistribution();
		
		if (null == dist)
		{
			throw new MYException("信息不完整,没有配送信息.");
		}
		
		if (StringTools.isNullOrNone(dist.getProvince()))
		{
			throw new MYException("信息不完整,没有省份信息.");
		}
		
		if (StringTools.isNullOrNone(dist.getCity()))
		{
			throw new MYException("信息不完整,没有市信息.");
		}
		
		if (StringTools.isNullOrNone(dist.getFullAddress()))
		{
			throw new MYException("信息不完整,没有详细信息.");
		}
		
		if (StringTools.isNullOrNone(dist.getReceiver()))
		{
			throw new MYException("信息不完整,没有收货人.");
		}
		
		if (StringTools.isNullOrNone(dist.getReceiverMobile()))
		{
			throw new MYException("信息不完整,没有收货人电话.");
		}
		
		if (StringTools.isNullOrNone(dist.getCarryType()))
		{
			throw new MYException("信息不完整,没有配送方式.");
		}
		
		String userId = bean.getUserId();
		
		// 确认用户为有效用户
		AppUserBean userBean = appUserDAO.find(userId);
		
		if (null  == userBean)
		{
			throw new MYException("用户不存在");
		}
		
		if (userBean.getStatus() != CustomerConstant.USER_STATUS_AVAILABLE){
			throw new MYException("用户不是有效的");
		}
		
		// 确认客户存在，且已挂靠业务员
		AppUserVSCustomerBean vscust = appUserVSCustomerDAO.findByUnique(userId);
		
		if (null == vscust)
		{
			throw new MYException("用户信息审核异常");
		}
		
		String customerId = vscust.getCustomerId();
		
		StafferVSCustomerBean svscust = stafferVSCustomerDAO.findByUnique(customerId);
		
		if (null == svscust)
		{
			throw new MYException("系统没有对接的业务员.");
		}
	}
	
	public List<OrderListResult> queryOrderList(ConditionParse con)
	throws MYException
	{
		List<AppOutBean> outList = appOutDAO.queryAppOut(con);
		
		List<OrderListResult> resultList = new ArrayList<OrderListResult>();
		
		for (AppOutBean each : outList)
		{
			OrderListResult result = new OrderListResult();
			
			result.setOrderNo(each.getOrderNo());
			result.setOutTime(each.getOutTime());
			result.setPay(MathTools.formatNum2(each.getTotal()));
			result.setPayAccount(each.getPayAccount());
			result.setPayStatus(DefinedCommon.getValue("outPay", each.getPayStatus()));
			result.setSale(MathTools.formatNum2(each.getSale()));
			result.setStatus(DefinedCommon.getValue("appOutStatus", each.getStatus()));
			
			List<AppBaseBean> items = appBaseDAO.queryEntityBeansByFK(each.getOrderNo());
			
			AppBaseBean base = items.get(0);
			
			result.setPicPath(base.getPicPath());
			result.setProductCode(base.getProductCode());
			result.setProductName(base.getProductName());
			
			//动态查询 状态
			setAppListStatus(each, result);
			
			resultList.add(result);
		}
		
		return resultList;
	}

	/**
	 * 
	 * @param each
	 * @param result
	 */
	private void setAppListStatus(AppOutBean each, OrderListResult result)
	{
		List<AppOutVSOutBean> vsList = appOutVSOutDAO.queryEntityBeansByFK(each.getOrderNo());
		
		int payCount = 0, statusCount = 0;
		
		if (!ListTools.isEmptyOrNull(vsList))
		{
			for(AppOutVSOutBean eachvs : vsList)
			{
				OutBean out = outDAO.find(eachvs.getOutId());
				
				if (null != out)
				{
					payCount += out.getPay();
					
					if (out.getStatus() == 3 || out.getStatus() == 4)
					{
						statusCount += 1;
					}
					else
					{
						statusCount += 0;
					}
				}else{ // 有可能单子删除了! maybe...
					payCount += 1;
					statusCount += 1;
				}
			}
			
			// 
			if (payCount != vsList.size())
			{
				result.setPayStatus("未付款");
			}else{
				result.setPayStatus("已付款");
			}
			
			if (statusCount == vsList.size())
			{
				result.setStatus("已发货");
			}else{
				result.setStatus("未发货");
			}
			
		}else{
			result.setPayStatus("未付款");
			result.setStatus("审核中");
		}
	}
	
	/**
	 * 定时执行，将产生的APP 订单生成OA 格式订单 internal
	 * {@inheritDoc}
	 */
	public void createOAOrder()
	{
        triggerLog.info("createOAOrder 开始...");
        
        long statsStar = System.currentTimeMillis();
        
        TransactionTemplate tran = new TransactionTemplate(transactionManager);
        
        try
        {
            tran.execute(new TransactionCallback()
            {
                public Object doInTransaction(TransactionStatus arg0)
                {
                	// 1,生成OA 订单 2,给商务人员、业务员发邮件
                	processOAOrder();
                	
                    return Boolean.TRUE;
                }
            });
        }
        catch (Exception e)
        {
            triggerLog.error(e, e);
        }
      
        triggerLog.info("createOAOrder... ,共耗时："+ (System.currentTimeMillis() - statsStar));
        
        return;
	}
	
	private void processOAOrder()
	{
		List<AppOutBean> list = appOutDAO.queryNotCreateOAOrder();
		
		List<OutBean> outList = new ArrayList<OutBean>();
		
		for (AppOutBean each : list)
		{
			AppUserVSCustomerBean vscust = appUserVSCustomerDAO.findByUnique(each.getUserId());
			
			StafferVSCustomerVO svscust = stafferVSCustomerDAO.findVOByUnique(vscust.getCustomerId());
			
			OutBean out = null;
			
			// create out
			try{
				out = createOut(each, svscust);
			}catch(MYException e)
			{
				triggerLog.warn(e.getErrorContent());
				
				continue;
			}
			
			outList.add(out);
			
			each.setoStatus(1);
			
			each.setOutId(out.getFullId());
			
			appOutDAO.updateEntityBean(each);
		}
		
		// send mail
		for (OutBean out : outList)
		{
			sendOutMail(out, "APP生成库单:" + out.getFullId());
		}
	}
	
	/**
	 * 创建销售库单
	 * 
	 * @param list
	 */
	private OutBean createOut(AppOutBean appOutBean, StafferVSCustomerVO vscust) throws MYException
	{
		String orderNo = appOutBean.getOrderNo();
		
		List<AppBaseBean> items = appBaseDAO.queryEntityBeansByFK(orderNo);

		AppDistributionBean dist = appDistributionDAO.findByUnique(orderNo);

		String newOutId;
		
		OutBean newOutBean = new OutBean();
    	
    	String id = appOutBean.getId();

        newOutId = "TM" + appOutBean.getOrderNo().substring(2);

        _logger.info("===================" + newOutId);
        
        newOutBean.setId(id);
    	
    	newOutBean.setFullId(newOutId);
    	
    	newOutBean.setOutTime(TimeTools.now_short());
    	
    	newOutBean.setCustomerId(vscust.getCustomerId());
    	
    	newOutBean.setCustomerName(vscust.getCustomerName());
    	
    	newOutBean.setLocationId("999");
    	
    	newOutBean.setLocation(ProductConstant.OUT_COMMON_DEPOT);
    	
    	newOutBean.setType(0);
    	
    	newOutBean.setOutType(0);
    	
    	newOutBean.setReserve3(1);
    	
    	newOutBean.setFlowId(appOutBean.getOrderNo());
    	
    	newOutBean.setReday(15);
    	
		long add = newOutBean.getReday()  * 24 * 3600 * 1000L;
		
		String redate = TimeTools.getStringByFormat(new Date(new Date().getTime() + add), "yyyy-MM-dd");
		
    	newOutBean.setRedate(redate);
    	
    	newOutBean.setManagerTime("");
    	
    	newOutBean.setDutyId(OutImportConstant.CITIC_DUTY);
    	
    	newOutBean.setInvoiceId(OutImportConstant.CITIC_INVOICEID);
    	
    	newOutBean.setStafferId(vscust.getStafferId());
    	
    	newOutBean.setStafferName(vscust.getStafferName());
    	
    	setInvoiceId(newOutBean);
    	
    	newOutBean.setStatus(OutConstant.STATUS_SAVE);
    	
    	double total = 0.0d;
    	
    	for (AppBaseBean each : items)
    	{
    		total += each.getPrice() * each.getAmount();
    	}
    	
    	newOutBean.setTotal(total);
    	
		newOutBean.setPay(0);
		
    	newOutBean.setArriveDate(TimeTools.now_short(7));
    	
    	newOutBean.setChangeTime("");
    	
    	newOutBean.setOperator("APP");
    	
    	newOutBean.setOperatorName("APP");
    	
    	newOutBean.setDescription("APP应用生成订单，App单号:" + appOutBean.getOrderNo());
    	
    	final StafferBean stafferBean = stafferDAO.find(newOutBean.getStafferId());
    	
    	List<BaseBean> baseList = new ArrayList<BaseBean>();
    	
		for (AppBaseBean each : items)
		{
			BaseBean base = new BaseBean();
			
			base.setId(each.getId());
			
			base.setOutId(newOutId);
			
			base.setProductId(each.getProductId());
			
			base.setProductName(each.getProductName());
			
			base.setUnit("套");
			
			base.setAmount(each.getAmount());
			
			base.setPrice(each.getPrice());
			
			base.setValue(each.getMoney());
			
			base.setLocationId(newOutBean.getLocation());
			
			base.setDepotpartId("");
			
			base.setDepotpartName("");
			
			base.setOwner("0");
			
			base.setOwnerName("公共");
			
			// 业务员结算价，总部结算价
			ProductBean product = productDAO.find(base.getProductId());
			
			if (null == product)
			{
				throw new MYException("APP 单号：" + appOutBean.getOrderNo() +",产品不存在");
			}

			double sailPrice = product.getSailPrice();
			
        	// 根据配置获取结算价
        	List<PriceConfigBean> pcblist = priceConfigDAO.querySailPricebyProductId(product.getId());
        	
        	if (!ListTools.isEmptyOrNull(pcblist))
        	{
        		PriceConfigBean cb = priceConfigManager.calcSailPrice(pcblist.get(0));
        		
        		if (cb == null)
        			sailPrice = 0;
        		else
        			sailPrice = cb.getSailPrice();
        	}

        	// 获取销售配置
            SailConfBean sailConf = sailConfigManager.findProductConf(stafferBean,
                product);
            
            // 总部结算价(产品结算价 * (1 + 总部结算率))
            base.setPprice(sailPrice
                           * (1 + sailConf.getPratio() / 1000.0d));

            // 事业部结算价(产品结算价 * (1 + 总部结算率 + 事业部结算率))
            base.setIprice(sailPrice
                           * (1 + sailConf.getIratio() / 1000.0d + sailConf
                               .getPratio() / 1000.0d));

            // 业务员结算价就是事业部结算价
            base.setInputPrice(base.getIprice());

/*            if (base.getInputPrice() == 0)
            {
                throw new RuntimeException("业务员结算价不能为0");
            }*/
            
			// 配送 方式及毛利率
            base.setDeliverType(0);
            
        	// 毛利，毛利率（针对业务员的）
        	double profit = 0.0d;
        	
        	double profitRatio = 0.0d;
        	
        	if (base.getValue() != 0)
        	{
            	profit = base.getAmount() * (base.getPrice() - base.getInputPrice());
            	
            	profitRatio = profit / base.getValue();
        	}

        	base.setProfit(profit);
        	base.setProfitRatio(profitRatio);
			
        	baseList.add(base);
		}

		newOutBean.setBaseList(baseList);
		
		outDAO.saveEntityBean(newOutBean);
    	
    	baseDAO.saveAllEntityBeans(baseList);

    	// 配送地址
    	DistributionBean distBean = new DistributionBean();
        
        distBean.setId(dist.getId());
        
        distBean.setOutId(newOutId);
        
        distBean.setAddress(dist.getFullAddress());
        
//        distBean.setProvinceId(bean.getProvinceId());
//        
//        distBean.setCityId(bean.getCityId());
        
        distBean.setMobile(dist.getReceiverMobile());
        
        distBean.setReceiver(dist.getReceiver());
        
        distBean.setExpressPay(1);
        
        distBean.setTransportPay(0);
        
        distBean.setTransport1(2);
        
        distBean.setTransport2(0);
        
        distBean.setShipping(2);
        
        distributionDAO.saveEntityBean(distBean);

        // 记录退货审批日志 操作人系统，自动审批 
    	FlowLogBean log = new FlowLogBean();

        log.setActor("系统");

        log.setDescription("App应用生成订单");
        log.setFullId(newOutBean.getFullId());
        log.setOprMode(PublicConstant.OPRMODE_PASS);
        log.setLogTime(TimeTools.now());

        log.setPreStatus(OutConstant.STATUS_SAVE);

        log.setAfterStatus(newOutBean.getStatus());

        flowLogDAO.saveEntityBean(log);
        
    	return newOutBean;
	}
	
    private void setInvoiceId(final OutBean outBean)
    {
    	// 行业
        StafferBean sb = stafferDAO.find(outBean.getStafferId());

        outBean.setIndustryId(sb.getIndustryId());

        if (StringTools.isNullOrNone(sb.getIndustryId2()))
        {
        	outBean.setIndustryId2(sb.getIndustryId());
        }
        else
        {
        	outBean.setIndustryId2(sb.getIndustryId2());
        }
        
        if (StringTools.isNullOrNone(sb.getIndustryId3()))
        {
        	outBean.setIndustryId3(outBean.getIndustryId2());
        }
        else
        {
        	outBean.setIndustryId3(sb.getIndustryId3());
        }
        
    }
	
    private void sendOutMail(OutBean out,String subject)
    {
        String operatorName = out.getOperatorName();
        
        StafferBean approverBean = stafferDAO.find(out.getStafferId());
        
        String customerName = out.getCustomerName();
        
        List<BaseBean> list = out.getBaseList();
        
        StringBuffer sb1 = new StringBuffer();
        
        sb1.append("<br>")
        .append("<br>")
        .append("====销售明细====");
        
        for (BaseBean each : list)
        {
        	sb1.append("<br>").append("商品：").append(each.getProductName())
        					  .append(", 数量：").append(each.getAmount())
        					  .append(", 单价：").append(each.getPrice());
        }
        
        sb1.append("。<br>")
        	.append("<br>");
        
        if (null != approverBean)
        {
            StringBuffer sb = new StringBuffer();
            
            sb.append("系统发送>>>")
            .append("<br>").append("单号:"+ out.getFullId()).append(",")
            .append("<br>").append("客户:"+ customerName).append(",")
            .append("<br>").append("总金额:"+  out.getTotal()).append(",")
            .append("<br>").append("申请人:"+ approverBean.getName()).append(",")
            .append("<br>").append("经办人:"+ operatorName);
            
            String message = sb.toString() + sb1.toString();
            
            String to = approverBean.getNation();
            
            _logger.info(message);
            
            commonMailManager.sendMail(to, subject, message);
            
            // 商务负责人。暂写死  (zhangyumei@yycoin.com)
            commonMailManager.sendMail("zhangyumei@yycoin.com", subject, message);
        }
    }
    
    /**
     * 查询明细
     * {@inheritDoc}
     */
	public AppOutBean queryOrderDetail(String orderNo) throws MYException
	{
		AppOutVO bean = appOutDAO.findVO(orderNo);
		
		if (null != bean)
		{
			List<AppBaseBean> items = appBaseDAO.queryEntityBeansByFK(orderNo);

			AppInvoiceBean invoice = appInvoiceDAO.findByUnique(orderNo);
			
			AppDistributionBean dist = appDistributionDAO.findByUnique(orderNo);
			
			bean.setItems(items);
			
			bean.setInvoice(invoice);
			
			bean.setDistribution(dist);
			
			// 根据APP 单号与 OUT 单号关联关系，查询订单状态，付款状态，支付账号
			List<AppOutVSOutBean> vsList = appOutVSOutDAO.queryEntityBeansByFK(orderNo);
			
			int count = 0, payCount = 0, statusCount = 0;
			
			double total = 0.0d, hadPay = 0.0d, sale = 0.0d;
			
			if (!ListTools.isEmptyOrNull(vsList))
			{
				for(AppOutVSOutBean each : vsList)
				{
					OutBean out = outDAO.find(each.getOutId());
					
					if (null != out)
					{
						count += out.getMtype();
						
						payCount += out.getPay();
						
						if (out.getStatus() == 3 || out.getStatus() == 4)
						{
							statusCount += 1;
						}
						else
						{
							statusCount += 0;
						}
						
						total += out.getTotal();
						hadPay += out.getHadPay();
						sale += out.getPromValue();
					}
				}
				
				// 全是管理
				if (count == 0)
				{
					bean.setPayAccount(parameterDAO.getString(SysConfigConstant.YULIN_PRESENT_CONSTRUCTION_ACCOUNT));
				}
				
				// 全是普通
				if (count == vsList.size())
				{
					bean.setPayAccount(parameterDAO.getString(SysConfigConstant.YYCOIN_COLLECTION_CONSTRUCTION_ACCOUNT));
				}
				
				// 
				if (payCount != vsList.size())
				{
					bean.setPayStatusName("未付款");
				}else{
					bean.setPayStatusName("已付款");
				}
				
				if (statusCount == vsList.size())
				{
					bean.setStatusName("已发货");
				}else{
					bean.setStatusName("未发货");
				}
				
				bean.setTotal(MathTools.round2(total));
				bean.setPay(MathTools.round2(hadPay));
				bean.setSale(MathTools.round2(sale));
				
			}else{
				bean.setPayStatusName("未付款");
				bean.setStatusName("审核中");
			}
			
		}
		
		return bean;
	}
    
	public ProductDAO getProductDAO()
	{
		return productDAO;
	}

	public void setProductDAO(ProductDAO productDAO)
	{
		this.productDAO = productDAO;
	}

	public PriceConfigDAO getPriceConfigDAO()
	{
		return priceConfigDAO;
	}

	public void setPriceConfigDAO(PriceConfigDAO priceConfigDAO)
	{
		this.priceConfigDAO = priceConfigDAO;
	}

	public PriceConfigManager getPriceConfigManager()
	{
		return priceConfigManager;
	}

	public void setPriceConfigManager(PriceConfigManager priceConfigManager)
	{
		this.priceConfigManager = priceConfigManager;
	}

	public PromotionDAO getPromotionDAO()
	{
		return promotionDAO;
	}

	public void setPromotionDAO(PromotionDAO promotionDAO)
	{
		this.promotionDAO = promotionDAO;
	}

	public PromotionItemDAO getPromotionItemDAO()
	{
		return promotionItemDAO;
	}

	public void setPromotionItemDAO(PromotionItemDAO promotionItemDAO)
	{
		this.promotionItemDAO = promotionItemDAO;
	}

	public CommonDAO getCommonDAO()
	{
		return commonDAO;
	}

	public void setCommonDAO(CommonDAO commonDAO)
	{
		this.commonDAO = commonDAO;
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

	public PlatformTransactionManager getTransactionManager()
	{
		return transactionManager;
	}

	public void setTransactionManager(PlatformTransactionManager transactionManager)
	{
		this.transactionManager = transactionManager;
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

	public AppUserVSCustomerDAO getAppUserVSCustomerDAO()
	{
		return appUserVSCustomerDAO;
	}

	public void setAppUserVSCustomerDAO(AppUserVSCustomerDAO appUserVSCustomerDAO)
	{
		this.appUserVSCustomerDAO = appUserVSCustomerDAO;
	}

	public AppUserDAO getAppUserDAO()
	{
		return appUserDAO;
	}

	public void setAppUserDAO(AppUserDAO appUserDAO)
	{
		this.appUserDAO = appUserDAO;
	}

	public StafferVSCustomerDAO getStafferVSCustomerDAO()
	{
		return stafferVSCustomerDAO;
	}

	public void setStafferVSCustomerDAO(StafferVSCustomerDAO stafferVSCustomerDAO)
	{
		this.stafferVSCustomerDAO = stafferVSCustomerDAO;
	}

	public StafferDAO getStafferDAO()
	{
		return stafferDAO;
	}

	public void setStafferDAO(StafferDAO stafferDAO)
	{
		this.stafferDAO = stafferDAO;
	}

	public SailConfigManager getSailConfigManager()
	{
		return sailConfigManager;
	}

	public void setSailConfigManager(SailConfigManager sailConfigManager)
	{
		this.sailConfigManager = sailConfigManager;
	}

	public FlowLogDAO getFlowLogDAO()
	{
		return flowLogDAO;
	}

	public void setFlowLogDAO(FlowLogDAO flowLogDAO)
	{
		this.flowLogDAO = flowLogDAO;
	}

	public CommonMailManager getCommonMailManager()
	{
		return commonMailManager;
	}

	public void setCommonMailManager(CommonMailManager commonMailManager)
	{
		this.commonMailManager = commonMailManager;
	}

	public AppOutVSOutDAO getAppOutVSOutDAO()
	{
		return appOutVSOutDAO;
	}

	public void setAppOutVSOutDAO(AppOutVSOutDAO appOutVSOutDAO)
	{
		this.appOutVSOutDAO = appOutVSOutDAO;
	}

	/**
	 * @return the parameterDAO
	 */
	public ParameterDAO getParameterDAO()
	{
		return parameterDAO;
	}

	/**
	 * @param parameterDAO the parameterDAO to set
	 */
	public void setParameterDAO(ParameterDAO parameterDAO)
	{
		this.parameterDAO = parameterDAO;
	}
}
