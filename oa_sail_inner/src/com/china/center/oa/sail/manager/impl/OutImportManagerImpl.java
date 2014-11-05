package com.china.center.oa.sail.manager.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionException;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

import com.center.china.osgi.publics.User;
import com.china.center.common.MYException;
import com.china.center.jdbc.util.ConditionParse;
import com.china.center.oa.client.bean.AddressBean;
import com.china.center.oa.client.bean.CustomerBean;
import com.china.center.oa.client.dao.AddressDAO;
import com.china.center.oa.client.dao.CustomerMainDAO;
import com.china.center.oa.client.dao.StafferVSCustomerDAO;
import com.china.center.oa.client.vo.AddressVO;
import com.china.center.oa.client.vo.StafferVSCustomerVO;
import com.china.center.oa.product.bean.DepotpartBean;
import com.china.center.oa.product.bean.PriceConfigBean;
import com.china.center.oa.product.bean.ProductBean;
import com.china.center.oa.product.constant.ProductConstant;
import com.china.center.oa.product.dao.DepotpartDAO;
import com.china.center.oa.product.dao.PriceConfigDAO;
import com.china.center.oa.product.dao.ProductDAO;
import com.china.center.oa.product.dao.ProductVSGiftDAO;
import com.china.center.oa.product.dao.StorageRelationDAO;
import com.china.center.oa.product.manager.PriceConfigManager;
import com.china.center.oa.product.manager.StorageRelationManager;
import com.china.center.oa.product.vo.ProductVSGiftVO;
import com.china.center.oa.product.vs.StorageRelationBean;
import com.china.center.oa.product.wrap.ProductChangeWrap;
import com.china.center.oa.publics.bean.AreaBean;
import com.china.center.oa.publics.bean.CityBean;
import com.china.center.oa.publics.bean.FlowLogBean;
import com.china.center.oa.publics.bean.ProvinceBean;
import com.china.center.oa.publics.bean.StafferBean;
import com.china.center.oa.publics.constant.IDPrefixConstant;
import com.china.center.oa.publics.constant.PublicConstant;
import com.china.center.oa.publics.dao.AreaDAO;
import com.china.center.oa.publics.dao.CityDAO;
import com.china.center.oa.publics.dao.CommonDAO;
import com.china.center.oa.publics.dao.FlowLogDAO;
import com.china.center.oa.publics.dao.LocationDAO;
import com.china.center.oa.publics.dao.ProvinceDAO;
import com.china.center.oa.publics.dao.StafferDAO;
import com.china.center.oa.sail.bean.BankSailBean;
import com.china.center.oa.sail.bean.BaseBean;
import com.china.center.oa.sail.bean.BatchApproveBean;
import com.china.center.oa.sail.bean.BatchReturnLog;
import com.china.center.oa.sail.bean.BatchSwatchBean;
import com.china.center.oa.sail.bean.ConsignBean;
import com.china.center.oa.sail.bean.DistributionBaseBean;
import com.china.center.oa.sail.bean.DistributionBean;
import com.china.center.oa.sail.bean.EstimateProfitBean;
import com.china.center.oa.sail.bean.OutBean;
import com.china.center.oa.sail.bean.OutImportBean;
import com.china.center.oa.sail.bean.OutImportLogBean;
import com.china.center.oa.sail.bean.OutImportResultBean;
import com.china.center.oa.sail.bean.PackageItemBean;
import com.china.center.oa.sail.bean.ReplenishmentBean;
import com.china.center.oa.sail.bean.SailConfBean;
import com.china.center.oa.sail.constanst.OutConstant;
import com.china.center.oa.sail.constanst.OutImportConstant;
import com.china.center.oa.sail.constanst.SailConstant;
import com.china.center.oa.sail.dao.BankSailDAO;
import com.china.center.oa.sail.dao.BaseDAO;
import com.china.center.oa.sail.dao.BatchApproveDAO;
import com.china.center.oa.sail.dao.BatchReturnLogDAO;
import com.china.center.oa.sail.dao.BatchSwatchDAO;
import com.china.center.oa.sail.dao.ConsignDAO;
import com.china.center.oa.sail.dao.DistributionBaseDAO;
import com.china.center.oa.sail.dao.DistributionDAO;
import com.china.center.oa.sail.dao.EstimateProfitDAO;
import com.china.center.oa.sail.dao.OutDAO;
import com.china.center.oa.sail.dao.OutImportDAO;
import com.china.center.oa.sail.dao.OutImportLogDAO;
import com.china.center.oa.sail.dao.OutImportResultDAO;
import com.china.center.oa.sail.dao.PackageItemDAO;
import com.china.center.oa.sail.dao.ReplenishmentDAO;
import com.china.center.oa.sail.helper.OutHelper;
import com.china.center.oa.sail.manager.OutImportManager;
import com.china.center.oa.sail.manager.OutManager;
import com.china.center.oa.sail.manager.SailConfigManager;
import com.china.center.tools.BeanUtil;
import com.china.center.tools.JudgeTools;
import com.china.center.tools.ListTools;
import com.china.center.tools.MathTools;
import com.china.center.tools.StringTools;
import com.china.center.tools.TimeTools;

public class OutImportManagerImpl implements OutImportManager
{
	private final Log _logger = LogFactory.getLog(getClass());
	
	private final Log operationLog = LogFactory.getLog("opr");
	
	private CommonDAO commonDAO = null;
	
	private OutImportDAO outImportDAO = null;
	
	private StorageRelationDAO storageRelationDAO = null;
	
	private ProductDAO productDAO = null;
	
	private FlowLogDAO flowLogDAO = null;
	
	private LocationDAO locationDAO = null;
	
	private StafferDAO stafferDAO = null;
	
    private OutDAO outDAO = null;

    private BaseDAO baseDAO = null;
    
    private DistributionDAO distributionDAO = null; 
	
    private OutImportResultDAO outImportResultDAO  = null;
    
    private ReplenishmentDAO replenishmentDAO  = null;
    
    private ConsignDAO consignDAO = null;
    
    private OutImportLogDAO outImportLogDAO = null;
    
    private SailConfigManager sailConfigManager = null;
    
	private StorageRelationManager storageRelationManager = null;
	
	private PlatformTransactionManager transactionManager = null;
	
	private CustomerMainDAO customerMainDAO = null;
	
	private StafferVSCustomerDAO stafferVSCustomerDAO = null;
	
	private ProvinceDAO provinceDAO = null;
	
	private CityDAO cityDAO = null;
	
	private AreaDAO areaDAO = null;
	
	private PriceConfigDAO priceConfigDAO = null;
	
	private PriceConfigManager priceConfigManager = null;
	
	private DistributionBaseDAO distributionBaseDAO = null;
	
	private BatchApproveDAO batchApproveDAO = null;
	
	private BatchSwatchDAO batchSwatchDAO = null;
	
	private OutManager outManager = null;
	
	private AddressDAO addressDAO = null;
	
	private ProductVSGiftDAO productVSGiftDAO = null;
	
	private DepotpartDAO depotpartDAO = null;
	
	private PackageItemDAO packageItemDAO = null;
	
	private BatchReturnLogDAO batchReturnLogDAO = null;
	
	private BankSailDAO bankSailDAO = null;
	
	private EstimateProfitDAO estimateProfitDAO = null;
	
	private final static String SPLIT = "_";
	
	public OutImportManagerImpl()
	{
		
	}
	
	@Transactional(rollbackFor = MYException.class)
	public String addBean(List<OutImportBean> list)
			throws MYException
	{
		JudgeTools.judgeParameterIsNull(list);
		
        String id = commonDAO.getSquenceString20();
        
        int i = 0;
        
        for (OutImportBean each : list)
        {
        	//each.setItype(0);
        	
        	each.setBatchId(id);
        	
        	each.setId(++i);
        	
        	each.setStatus(OutImportConstant.STATUS_INIT);
        	
        	if (each.getOutType() == OutConstant.OUTTYPE_OUT_PRESENT
        			&& each.getPrice() != 0)
        	{
        		throw new MYException("赠送类型订单单价须为0");
        	}
        }
        
        outImportDAO.saveAllEntityBeans(list);
        
        // 清除 10天前的数据至备份表
        //delHisData();
        
		return id;
	}
	
	@Transactional(rollbackFor = MYException.class)
	public String addPufaBean(List<OutImportBean> list)
			throws MYException
	{
		JudgeTools.judgeParameterIsNull(list);
		
        String id = commonDAO.getSquenceString20();
        
        int i = 0;
        
        for (OutImportBean each : list)
        {
        	//each.setItype(1); // 浦发
        	
        	each.setBatchId(id);
        	
        	each.setId(++i);
        	
        	each.setStatus(OutImportConstant.STATUS_INIT);
        	
        	if (each.getOutType() == OutConstant.OUTTYPE_OUT_PRESENT
        			&& each.getPrice() != 0)
        	{
        		throw new MYException("赠送类型订单单价须为0");
        	}
        }
        
        outImportDAO.saveAllEntityBeans(list);
        
		return id;
	}
	
	/**
	 * 
	 * {@inheritDoc}
	 */
	public boolean process(final List<OutImportBean> list)
			throws MYException
	{
		JudgeTools.judgeParameterIsNull(list);
		
		OutImportBean bean = list.get(0);
		
		check(bean);
		
		saveLogInner(bean, OutImportConstant.LOGSTATUS_ING, "处理中...");
		
		try
		{
			TransactionTemplate tran = new TransactionTemplate(transactionManager);
			
			tran.execute(new TransactionCallback()
			{
				public Object doInTransaction(TransactionStatus status)
				{
					processInner(list);
					
					return Boolean.TRUE;
				}
			}
			);
		}
		catch (TransactionException e)
        {
			saveLogInner(bean, OutImportConstant.LOGSTATUS_FAIL, "处理失败,数据库内部错误");
			
			operationLog.error("订单接口数据处理错误：", e);
            throw new MYException("数据库内部错误");
        }
        catch (DataAccessException e)
        {
        	saveLogInner(bean, OutImportConstant.LOGSTATUS_FAIL, "处理失败,数据访问异常");
        	
        	operationLog.error("订单接口数据处理错误：", e);
            throw new MYException(e.getCause().toString());
        }
        catch (Exception e)
        {
        	saveLogInner(bean, OutImportConstant.LOGSTATUS_FAIL, "处理失败,系统错误，请联系管理员");
        	
        	operationLog.error("订单接口数据处理错误：", e);
            throw new MYException("系统错误，请联系管理员:" + e);
        }
        
		return true;
	}
	
	/**
	 * 检查是否已生成了OA单
	 * 
	 * @param bean
	 * @throws MYException
	 */
	private void check(OutImportBean bean) throws MYException
	{
		String batchId = bean.getBatchId();
		
		OutImportLogBean logBean = outImportLogDAO.findByBatchIdAndStatus(batchId, OutImportConstant.LOGSTATUS_ING);
		
		if (null != logBean)
		{
			throw new MYException("批次号：" + batchId + " 正在处理中，不可重复处理");
		}
		
		logBean = outImportLogDAO.findByBatchIdAndStatus(batchId, OutImportConstant.LOGSTATUS_SUCCESSFULL);
		
		if (null != logBean)
		{
			throw new MYException("批次号：" + batchId + " 已处理完成了，不可重复产生OA单");
		}
	}
	
	private boolean saveLogInner(final OutImportBean bean, final int status, final String message)
	{
		try
		{
			TransactionTemplate tran = new TransactionTemplate(transactionManager);
			
			tran.execute(new TransactionCallback()
			{
				public Object doInTransaction(TransactionStatus tstatus)
				{
					outImportLogDAO.deleteEntityBeansByFK(bean.getBatchId());
					
					OutImportLogBean logBean = new OutImportLogBean();
					
					logBean.setId(commonDAO.getSquenceString20());
					logBean.setBatchId(bean.getBatchId());
					logBean.setLogTime(TimeTools.now());
					logBean.setMessage(message);
					logBean.setStatus(status);
					
					outImportLogDAO.saveEntityBean(logBean);
					
					return Boolean.TRUE;
				}
			}
			);
		}
        catch (Exception e)
        {
            throw new RuntimeException("系统错误，请联系管理员saveLogInner:" + e);
        }
		
		return true;
	}
	
	/**
	 * 
	 * @param bean
	 * @param status
	 * @param message
	 * @return
	 */
	private boolean saveLogInnerWithoutTransaction(OutImportBean bean, int status, String message)
	{
		outImportLogDAO.deleteEntityBeansByFK(bean.getBatchId());
		
		OutImportLogBean logBean = new OutImportLogBean();
		
		logBean.setId(commonDAO.getSquenceString20());
		logBean.setBatchId(bean.getBatchId());
		logBean.setLogTime(TimeTools.now());
		logBean.setMessage(message);
		logBean.setStatus(status);
		
		outImportLogDAO.saveEntityBean(logBean);
		
		return true;
	}
	
	/**
	 * 导入订单数据合并成OA订单 - CORE
	 * 按批号为处理单元（暂定），一个批号要不成功，要不失败。
	 * 
	 * 方法体事务，unChecked 异常，有异常抛出RuntimeException
	 * 
	 */
	private void processInner(List<OutImportBean> list)
	{
		String batchId = list.get(0).getBatchId();
		
		List<OutImportBean> list1 = outImportDAO.queryEntityBeansByFK(batchId);
		
		if (!ListTools.isEmptyOrNull(list1))
		{
			// 去掉处理中与成功处理的数据
			if (list1.get(0).getStatus() == OutImportConstant.STATUS_SUCCESSFULL)
			{
				throw new RuntimeException("已处理过的接口数据，不可再生成OA库单。");
			}
			
			Map<String, List<OutImportBean>> map = new HashMap<String, List<OutImportBean>>();
			
			// 处理需要处理的 - 1.按客户与送货时间分组
			for (OutImportBean each : list1)
			{
				// 根据网点获取客户ID
				CustomerBean cbean = customerMainDAO.findByUnique(each.getComunicatonBranchName());
				
				if (null == cbean)
				{
					throw new RuntimeException("客户（网点）不存在。");
				}
				else
					each.setCustomerId(cbean.getId());
				
				String key = each.getCustomerId() + SPLIT + each.getArriveDate();
				
				if (map.containsKey(key))
				{
					List<OutImportBean> mapList = map.get(key);
					
					mapList.add(each);
				}
				else
				{
					List<OutImportBean> importList = new ArrayList<OutImportBean>();
					
					importList.add(each);
					
					map.put(key, importList);
				}
			}
			
			List<OutImportBean> uList = new ArrayList<OutImportBean>();
			
			// 2.对分组根据库存情况再次分组 - 根据产品 +仓区+所有者生成销售单
			for (List<OutImportBean> eachList : map.values())
			{
				// 合并同一产品为一行，数量，金额合并  (暂不合并数量)
				//List<OutImportBean> mergeList = mergeList(eachList);
				
				// CORE -- 要求一个商品生成一单子
				List<OutImportBean> useList = new ArrayList<OutImportBean>();
				
				for (OutImportBean eachOut : eachList)
				{
					useList.clear();
					
					// size == 1, support size than 1
					useList.add(eachOut);
					
					// create Out
					OutBean outBean = createOut(useList);
					
					// 中信类型 产生赠品订单
					if (eachOut.getItype() == 0){
						createGiftOut(outBean);
					}
					
					eachOut.setOANo(outBean.getFullId());
					
					eachOut.setStatus(OutImportConstant.STATUS_SUCCESSFULL);
					
					uList.add(eachOut);
				}
			}
			
			// 防止数据被重复处理，再次检查下状态
			List<OutImportBean> reList = outImportDAO.queryEntityBeansByFK(batchId);
			
			for (OutImportBean each : reList)
			{
				if (each.getStatus() == OutImportConstant.STATUS_SUCCESSFULL)
				{
					throw new RuntimeException("数据被重复处理，请确认");
				}
			}
			
			outImportDAO.updateAllEntityBeans(uList);
			
			saveLogInnerWithoutTransaction(list.get(0), OutImportConstant.LOGSTATUS_SUCCESSFULL, "成功");
		}
		
	}
	
	/**
	 * 合并商品一样的数量
	 * 
	 * @param eachList
	 * @return
	 */
	@SuppressWarnings("unused")
	private List<OutImportBean> mergeList(List<OutImportBean> eachList)
	{
		Map<String, OutImportBean> map = new HashMap<String, OutImportBean>();
		
		for (OutImportBean each : eachList)
		{
			if (map.containsKey(each.getProductId()))
			{
				OutImportBean bean = map.get(each.getProductId());
				
				bean.setAmount(bean.getAmount() + each.getAmount());
				
				bean.setValue(bean.getAmount() * bean.getPrice());
			}
			else
			{
				map.put(each.getProductId(), each);
			}
		}
		
        Collection<OutImportBean> values = map.values();

		List<OutImportBean> mergeList = new ArrayList<OutImportBean>();
		
        for (OutImportBean eachBean : values)
        {
        	mergeList.add(eachBean);
        }
		
		return mergeList;
	}
	
	/**
	 * 创建销售库单
	 * 
	 * @param list
	 * 			
	 */
	private OutBean createOut(List<OutImportBean> list)
	{
		String newOutId;
		int itype = 0;
		
		OutImportBean bean = list.get(0);
		
		itype = bean.getItype();
		
		String mess = "";
		
		if (itype == 0){
			mess = "中信";
		}else
		{
			//mess = "浦发";
		}
		
		OutBean newOutBean = new OutBean();
    	
    	String id = getAll(commonDAO.getSquence());

        String time = TimeTools.getStringByFormat(new Date(), "yyMMddHHmm");

        String flag = OutHelper.getSailHead(OutConstant.OUT_TYPE_OUTBILL, bean.getOutType());
        
        newOutId = flag + time + id;

        newOutBean.setId(getOutId(id));
    	
    	newOutBean.setFullId(newOutId);
    	
    	newOutBean.setOutTime(TimeTools.now_short());
    	
    	newOutBean.setCustomerId(bean.getCustomerId());
    	
    	newOutBean.setCustomerName(bean.getComunicatonBranchName());
    	
    	newOutBean.setLocationId("999");
    	
    	newOutBean.setLocation(bean.getDepotId());
    	
    	newOutBean.setType(0);
    	
    	newOutBean.setOutType(bean.getOutType());
    	
    	newOutBean.setReserve3(OutImportConstant.CITIC_PAYTYPE);
    	
    	// 中信银行导入订单
    	newOutBean.setFlowId(OutImportConstant.CITIC);
    	
    	newOutBean.setReday(bean.getReday());
    	
		long add = newOutBean.getReday()  * 24 * 3600 * 1000L;
		
		String redate = TimeTools.getStringByFormat(new Date(new Date().getTime() + add), "yyyy-MM-dd");
		
    	newOutBean.setRedate(redate);
    	
    	newOutBean.setArriveDate(TimeTools.now_short(2));
    	
    	// 结算中心审批时间
    	newOutBean.setManagerTime("");
    	
    	//newOutBean.setDutyId(OutImportConstant.CITIC_DUTY);
    	
    	newOutBean.setInvoiceId(OutImportConstant.CITIC_INVOICEID);
    	
    	//
    	if (bean.getOutType() == OutConstant.OUTTYPE_OUT_SWATCH)
    	{
    		newOutBean.setStafferId(bean.getStafferId());
    		
    		StafferBean staffer = stafferDAO.find(bean.getStafferId());
    		
    		newOutBean.setStafferName(staffer.getName());
    	}else
    	{
        	StafferVSCustomerVO vsCustVO = stafferVSCustomerDAO.findVOByUnique(bean.getCustomerId());
        	
    		newOutBean.setStafferId(vsCustVO.getStafferId());
        	
        	newOutBean.setStafferName(vsCustVO.getStafferName());
    	}
    	
    	setInvoiceId(newOutBean);
    	
    	newOutBean.setStatus(OutConstant.STATUS_SUBMIT);
    	
    	double total = 0.0d;
    	
    	int amounts = 0;
    	
    	for (OutImportBean each : list)
    	{
    		total += each.getPrice() * each.getAmount();
    		
    		amounts += each.getAmount();
    	}
    	
    	newOutBean.setTotal(total);
    	
		newOutBean.setPay(0);
		
    	newOutBean.setArriveDate(bean.getArriveDate());
    	
    	newOutBean.setChangeTime("");
    	
    	newOutBean.setOperator(bean.getReason());
    	
    	StafferBean staff = stafferDAO.find(bean.getReason());
    	
    	if (null != staff) {
    		newOutBean.setOperatorName(staff.getName());    		
    	}
    	
    	// 赠送类型
    	newOutBean.setPresentFlag(bean.getPresentFlag());
    	
    	newOutBean.setDescription("数据接口批量导入，银行单号" + bean.getCiticNo() + "." + bean.getDescription());
    	
    	final StafferBean stafferBean = stafferDAO.find(newOutBean.getStafferId());
    	
    	List<ReplenishmentBean> replenishmentList = new ArrayList<ReplenishmentBean>();
    	
    	List<BaseBean> baseList = new ArrayList<BaseBean>();
    	
		String dutyId = "";
		int mtype = 0;
    	
		for (OutImportBean each : list)
		{
			BaseBean base = new BaseBean();
			
			ReplenishmentBean replenishment = new ReplenishmentBean();
			
			base.setId(commonDAO.getSquenceString());
			
			base.setOutId(newOutId);
			
			base.setProductId(each.getProductId());
			replenishment.setProductId(each.getProductId());
			
			base.setProductName(each.getProductName());
			replenishment.setProductName(each.getProductName());
			
			base.setUnit("套");
			
			base.setAmount(each.getAmount());
			
			base.setPrice(each.getPrice());
			
			base.setValue(each.getValue());
			
			base.setLocationId(newOutBean.getLocation());
			
			base.setDepotpartId(bean.getDepotpartId());
			replenishment.setDepotpartId(bean.getDepotpartId());
			
			base.setDepotpartName(bean.getComunicationBranch());
			replenishment.setDepotpartName(bean.getComunicationBranch());
			
			
			base.setOwner("0");
			
			base.setOwnerName("公共");
			
			// 业务员结算价，总部结算价
			ProductBean product = productDAO.find(base.getProductId());
			
			if (null == product)
			{
				throw new RuntimeException("产品不存在");
			}
			
			mtype = MathTools.parseInt(product.getReserve4());
			
			if (mtype == PublicConstant.MANAGER_TYPE_COMMON)
			{
				dutyId = PublicConstant.DEFAULR_DUTY_ID;
				
				if (product.getConsumeInDay() == ProductConstant.PRODUCT_OLDGOOD) {
					base.setTaxrate(0.02);
				} else if (product.getConsumeInDay() == ProductConstant.PRODUCT_OLDGOOD_YES) {
					base.setTaxrate(0.17);
				} else {
					base.setTaxrate(0);
				}
			}
			else
			{
				dutyId = PublicConstant.MANAGER2_DUTY_ID;
				
				base.setTaxrate(0);
			}
			
			double sailPrice = product.getSailPrice();
			
        	// 根据配置获取结算价
        	List<PriceConfigBean> pcblist = priceConfigDAO.querySailPricebyProductId(product.getId());
        	
        	if (!ListTools.isEmptyOrNull(pcblist))
        	{
        		PriceConfigBean cb = priceConfigManager.calcSailPrice(pcblist.get(0));
        		
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

            if (base.getInputPrice() == 0)
            {
                throw new RuntimeException(base.getProductName() + " 业务员结算价不能为0");
            }
            
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
        	
        	replenishmentList.add(replenishment);
		}

		newOutBean.setDutyId(dutyId);
		
		newOutBean.setMtype(mtype);
		
		outDAO.saveEntityBean(newOutBean);
    	
    	baseDAO.saveAllEntityBeans(baseList);
    	
    	newOutBean.setBaseList(baseList);
    	
    	// 配送地址
    	DistributionBean distBean = new DistributionBean();
        
        //distBean.setId(commonDAO.getSquenceString());
        
        distBean.setOutId(newOutId);
        
        distBean.setProvinceId(bean.getProvinceId());
        distBean.setCityId(bean.getCityId());
        distBean.setAddress(bean.getAddress());
        
        //saveAddress(bean, newOutBean, distBean);
        
        distBean.setMobile(bean.getHandPhone());
        
        distBean.setReceiver(bean.getReceiver());
        
        distBean.setExpressPay(bean.getExpressPay());
        
        distBean.setTransportPay(bean.getTransportPay());
        
        distBean.setTransport1(bean.getTransport1());
        
        distBean.setTransport2(bean.getTransport2());
        
        distBean.setShipping(bean.getShipping());
        
        //distributionDAO.saveEntityBean(distBean);
        List<DistributionBean> distList = saveDistributionInner(distBean, baseList);
        
        // 产生发货数据
/*        for(DistributionBean each : distList)
        {
        	ConsignBean cbean = new ConsignBean();

            cbean.setCurrentStatus(SailConstant.CONSIGN_PASS);

            cbean.setGid(commonDAO.getSquenceString20());

            cbean.setDistId(each.getId());
            
            cbean.setFullId(newOutBean.getFullId());

            cbean.setArriveDate(newOutBean.getArriveDate());
            
            cbean.setReveiver(each.getReceiver());

            consignDAO.addConsign(cbean);
        }*/
    	
		// 接口处理结果数据
		OutImportResultBean resultBean = new OutImportResultBean();
		
		resultBean.setCiticNo(bean.getCiticNo());
		
		resultBean.setOANo(newOutId);
		
		resultBean.setOAamount(amounts);
		
		resultBean.setOAmoney(total);
		
		outImportResultDAO.saveEntityBean(resultBean);

		// 记录导入的产品信息，便于纺计库存情况
		for (Iterator<ReplenishmentBean> iterator = replenishmentList.iterator(); iterator.hasNext();)
		{
			ReplenishmentBean each = iterator.next();
			
			// 只增加没有的
			ReplenishmentBean replen = replenishmentDAO.
										findByProductIdAndDepotpartIdAndOwner(each.getProductId(), each.getDepotpartId(), each.getOwner());
			
			if (null != replen)
			{
				iterator.remove();
			}
		}
		
		if (!ListTools.isEmptyOrNull(replenishmentList))
		{
			replenishmentDAO.saveAllEntityBeans(replenishmentList);
		}
        
    	// 记录退货审批日志 操作人系统，自动审批 
    	FlowLogBean log = new FlowLogBean();

        log.setActor("系统");

        log.setDescription(mess + "银行数据导入系统自动审批");
        log.setFullId(newOutBean.getFullId());
        log.setOprMode(PublicConstant.OPRMODE_PASS);
        log.setLogTime(TimeTools.now());

        log.setPreStatus(OutConstant.STATUS_SAVE);

        log.setAfterStatus(newOutBean.getStatus());

        flowLogDAO.saveEntityBean(log);
        
    	return newOutBean;
	}

	private void saveAddress(OutImportBean bean, OutBean newOutBean,
			DistributionBean distBean)
	{
		// 
        ConditionParse con = new ConditionParse();
        
        con.addWhereStr();
        con.addCondition("AddressBean.customerId", "=", newOutBean.getCustomerId());
        
        con.addCondition(" order by AddressBean.id desc");
        
        List<AddressVO> addrList = addressDAO.queryEntityVOsByCondition(con);
        
        if (ListTools.isEmptyOrNull(addrList)){
        	// 
        	if (!StringTools.isNullOrNone(bean.getAddress().trim())){
        		AddressBean addrBean = new AddressBean();
        		
        		addrBean.setId(commonDAO.getSquenceString());
        		addrBean.setCustomerId(newOutBean.getCustomerId());
        		addrBean.setCustomerName(newOutBean.getCustomerName());
        		addrBean.setStafferId(newOutBean.getStafferId());
        		addrBean.setAddress(bean.getAddress());
        		addrBean.setReceiver(bean.getReceiver());
        		addrBean.setMobile(bean.getHandPhone());
        		addrBean.setLogTime(TimeTools.now());
        		
        		addressDAO.saveEntityBean(addrBean);
        	}
        }else{
        	AddressVO addrBean = addrList.get(0);
        	
        	if (StringTools.isNullOrNone(bean.getAddress().trim()))
        	{
        		distBean.setAddress(addrBean.getAddress());
                
            	distBean.setProvinceId(addrBean.getProvinceId());
              
            	distBean.setCityId(addrBean.getCityId());
            	
            	distBean.setAreaId(addrBean.getAreaId());
        	}else{
        		if (!bean.getAddress().equals(addrBean.getAddress())
    					|| !bean.getReceiver().equals(addrBean.getReceiver())
    					|| !bean.getHandPhone().equals(addrBean.getMobile()))
    			{
        			AddressBean addBean = new AddressBean();
            		
        			addBean.setId(commonDAO.getSquenceString());
        			addBean.setCustomerId(newOutBean.getCustomerId());
        			addBean.setCustomerName(newOutBean.getCustomerName());
        			addBean.setStafferId(newOutBean.getStafferId());
        			addBean.setAddress(bean.getAddress());
        			addBean.setReceiver(bean.getReceiver());
        			addBean.setMobile(bean.getHandPhone());
        			addBean.setLogTime(TimeTools.now());
            		
            		addressDAO.saveEntityBean(addBean);
    			}
        	}
        }
	}
	
	/**
	 * 产生多个配送单
	 */
	private List<DistributionBean> saveDistributionInner(DistributionBean distBean, List<BaseBean> baseList)
	{
		// 预处理distBean，将详细地址中有与省、市、区一样的信息清除
		String address = distBean.getAddress().trim();
		
		String provinceId = distBean.getProvinceId();
		
		String provinceName = "";
		
		if (!StringTools.isNullOrNone(provinceId))
		{
			ProvinceBean province = provinceDAO.find(provinceId);
			
			if (null != province)
			{
				provinceName = province.getName();
				
				int p = address.indexOf(provinceName);
				
				if (p == 0)
				{
					address = address.substring(province.getName().length());
				}
			}
		}
		
		String cityId = distBean.getCityId();
		
		if (!StringTools.isNullOrNone(cityId))
		{
			CityBean city = cityDAO.find(cityId);
			
			if (null != city)
			{
				if (provinceName.equals(city.getName()))
				{
					distBean.setProvinceId("");
				}
				
				int p = address.indexOf(city.getName());
				
				if (p == 0)
				{
					address = address.substring(city.getName().length());
				}
			}
		}
		
		String areaId = distBean.getAreaId();
		
		if (!StringTools.isNullOrNone(areaId))
		{
			AreaBean area = areaDAO.find(areaId);
			
			if (null != area)
			{
				int p = address.indexOf(area.getName());
				
				if (p == 0)
				{
					address = address.substring(area.getName().length());
				}
			}
		}
		
		distBean.setAddress(address);
		
		List<DistributionBean> distList = new ArrayList<DistributionBean>();
		
		Map<String,List<BaseBean>> map = new HashMap<String,List<BaseBean>>();
        
        for (BaseBean each : baseList)
        {
        	String key = each.getDeliverType()+"";
        	
        	if (!map.containsKey(key))
        	{
        		List<BaseBean> ebaseList = new ArrayList<BaseBean>();
        		
        		ebaseList.add(each);
        		
        		map.put(key, ebaseList);
        	}else{
        		List<BaseBean> ebaseList = map.get(key);
        		
        		ebaseList.add(each);
        	}
        }
        
        // 
        for (Map.Entry<String, List<BaseBean>> entry : map.entrySet())
        {
        	List<BaseBean> blist = entry.getValue();
        	
        	DistributionBean newDist = new DistributionBean();
        	
        	BeanUtil.copyProperties(newDist, distBean);
        	
        	String id = commonDAO.getSquenceString20(IDPrefixConstant.ID_DISTRIBUTION_PRIFIX);
        	
        	newDist.setId(id);
        	
        	distList.add(newDist);
        	
        	distributionDAO.saveEntityBean(newDist);
        	
        	for(BaseBean eachB : blist)
        	{
        		DistributionBaseBean dbb = new DistributionBaseBean();
        		
        		dbb.setRefId(id);
        		
        		dbb.setOutId(eachB.getOutId());
        		
        		dbb.setBaseId(eachB.getId());
        		
        		distributionBaseDAO.saveEntityBean(dbb);
        	}
        }
        
        return distList;
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
	
    // 创建赠品订单
    private void createGiftOut(OutBean out)
    {
    	// 判断产品是否有对应赠品关系 - 中信订单一个订单一个产品
    	BaseBean base = out.getBaseList().get(0);
    	
    	String productId = base.getProductId();
    	
    	List<ProductVSGiftVO> giftList = productVSGiftDAO.queryEntityVOsByFK(productId);
    	
    	if (ListTools.isEmptyOrNull(giftList))
    		return;
    	
    	OutBean newOutBean = new OutBean();

    	BeanUtil.copyProperties(newOutBean, out);
    	
    	String id = getAll(commonDAO.getSquence());

        String time = TimeTools.getStringByFormat(new Date(), "yyMMddHHmm");

        String flag = OutHelper.getSailHead(OutConstant.OUT_TYPE_OUTBILL, OutConstant.OUTTYPE_OUT_PRESENT);
        
        String newOutId = flag + time + id;

        newOutBean.setId(getOutId(id));
    	
    	newOutBean.setFullId(newOutId);
    	
    	newOutBean.setTotal(0);
    	
    	newOutBean.setOutType(OutConstant.OUTTYPE_OUT_PRESENT);
    	
    	newOutBean.setDescription("自动生成赠品订单，关联销售单：" + out.getFullId());
    	
    	outDAO.saveEntityBean(newOutBean);
    	
    	for (ProductVSGiftVO each : giftList)
    	{
    		BaseBean newBaseBean = new BaseBean();
    		
    		BeanUtil.copyProperties(newBaseBean, base);
    		
    		newBaseBean.setId(commonDAO.getSquenceString());
    		newBaseBean.setAmount(base.getAmount() * each.getAmount());
    		newBaseBean.setProductId(each.getGiftProductId());
    		newBaseBean.setProductName(each.getGiftProductName());
    		newBaseBean.setPrice(0);
    		newBaseBean.setValue(0);
    		newBaseBean.setProfit(0);
    		newBaseBean.setProfitRatio(0);
    		newBaseBean.setOutId(newOutId);
    		
    		baseDAO.saveEntityBean(newBaseBean);
    	}
    	
    	// 配送
    	List<DistributionBean> distList = distributionDAO.queryEntityBeansByFK(out.getFullId());
    	
    	for(DistributionBean each : distList)
    	{
    		String distId = each.getId();
    		
    		DistributionBean newDist = new DistributionBean();
    		
    		String newDistId = commonDAO.getSquenceString20(IDPrefixConstant.ID_DISTRIBUTION_PRIFIX);
    		
    		BeanUtil.copyProperties(newDist, each);
    		
    		newDist.setId(newDistId);
    		newDist.setOutId(newOutId);
    		
    		distributionDAO.saveEntityBean(newDist);
    		
    		// 发货
/*        	List<ConsignBean> consignList = consignDAO.queryConsignByDistId(distId);
        	
        	for(ConsignBean consign : consignList)
        	{
        		ConsignBean newConsign = new ConsignBean();
        		
        		BeanUtil.copyProperties(newConsign, consign);
        		
        		newConsign.setFullId(newOutId);
        		newConsign.setDistId(newDistId);
        		
        		newConsign.setGid(commonDAO.getSquenceString20());
        		
        		consignDAO.addConsign(newConsign);
        	}*/
    	}
    	
    	// 记录退货审批日志 操作人系统，自动审批 
    	FlowLogBean log = new FlowLogBean();

        log.setActor("系统");

        log.setDescription("银行数据导入系统[赠品]自动审批");
        log.setFullId(newOutBean.getFullId());
        log.setOprMode(PublicConstant.OPRMODE_PASS);
        log.setLogTime(TimeTools.now());

        log.setPreStatus(OutConstant.STATUS_SAVE);

        log.setAfterStatus(newOutBean.getStatus());

        flowLogDAO.saveEntityBean(log);
    }
    
	/**
	 * 异步处理
	 * {@inheritDoc}
	 */
	public boolean processAsyn(List<OutImportBean> list)
	{
		ProcessBeanThread thread = new ProcessBeanThread(list);
		
		try
		{
			thread.start();
		}
		catch(Exception e)
		{
			_logger.error(e, e);
		}
		
		return true;
	}
	
	class ProcessBeanThread extends Thread
	{
		private List<OutImportBean> list;
		
		public ProcessBeanThread(List<OutImportBean> list)
		{
			this.list = list;
		}

		public List<OutImportBean> getList()
		{
			return list;
		}

		public void run()
        {
            try
            {
            	process(list);
            }
            catch (MYException e)
            {
            	_logger.warn(e, e);
            }
        }
	}

	public List<ReplenishmentBean> queryReplenishmentBean() throws MYException
	{
		List<ReplenishmentBean> list = replenishmentDAO.listEntityBeans();
		
		final List<ReplenishmentBean> delList = new ArrayList<ReplenishmentBean>();
		
		for (Iterator<ReplenishmentBean> iterator = list.iterator(); iterator.hasNext();)
		{
			ReplenishmentBean bean = iterator.next();
			
			// 库存量
			int amount = storageRelationDAO.sumByDepotpartIdAndProductIdAndStafferId(bean.getDepotpartId(), bean.getProductId(), bean.getOwner());
			
			// 在途
			int inwayAmount = storageRelationManager.sumPreassignByStorageRelation2(bean.getDepotpartId(), bean.getProductId(), bean.getOwner());
			
			if (amount >= inwayAmount)
			{
				delList.add(bean);
				
				iterator.remove();
			}
			
			bean.setAmount(inwayAmount - amount);
		}
		
		if (!ListTools.isEmptyOrNull(delList))
		{
			deleteReplenishmentBeans(delList);
		}
		
		return list;
	}
	
	@Transactional(rollbackFor = MYException.class)
	private boolean deleteReplenishmentBeans(final List<ReplenishmentBean> delList) throws MYException
	{
		for (ReplenishmentBean each : delList)
		{
			replenishmentDAO.deleteEntityBean(each.getId());
		}
		
		return true;
	}
	
    private String getAll(int i)
    {
        String s = "00000000" + i;

        return s.substring(s.length() - 9);
    }
    
    private String getOutId(String idStr)
    {
        while (idStr.length() > 0 && idStr.charAt(0) == '0')
        {
            idStr = idStr.substring(1);
        }

        return idStr;
    }
    
    @Transactional(rollbackFor = MYException.class)
	public String batchApproveImport(List<BatchApproveBean> list, int type)
			throws MYException
	{
    	JudgeTools.judgeParameterIsNull(list);
    	
    	String batchId = commonDAO.getSquenceString20();
    	
    	for(BatchApproveBean each :list)
    	{
    		each.setBatchId(batchId);
    		
    		OutBean out = outDAO.find(each.getOutId());
    		
    		if (out == null)
    		{
    			each.setRet(1);
    			each.setResult(each.getOutId() + " 订单不存在") ;
    			
    			continue;
    		}
    		
    		each.setStatus(out.getStatus());
    		
    		// 须是销售单
    		if (out.getType() != OutConstant.OUT_TYPE_OUTBILL)
    		{
    			each.setRet(1);
    			each.setResult(each.getOutId() + " 订单不是销售单") ;
    			
    			continue;
    		}
    		
    		// 须是中信订单
    		/*if (!out.getFlowId().equals("CITIC"))
    		{
    			each.setRet(1);
    			each.setResult(each.getOutId() + " 订单不是中信订单") ;
    			
    			continue;
    		}*/
    		
    		// 须是结算中心或库管状态
    		if (type == 0 && out.getStatus() != OutConstant.STATUS_SUBMIT)
    		{
    			each.setRet(1);
    			each.setResult(each.getOutId() + " 订单状态只能是待商务审批") ;
    			
    			continue;
    		}
    		
    		if (type == 1 && out.getStatus() != OutConstant.STATUS_FLOW_PASS)
    		{
    			each.setRet(1);
    			each.setResult(each.getOutId() + " 订单状态只能是待库管审批") ;
    			
    			continue;
    		}
    	}
    	
    	batchApproveDAO.saveAllEntityBeans(list);
    	
		return batchId;
	}

    /**
     * 一单一单处理，并记录处理结果
     * {@inheritDoc}
     */
	public boolean batchApprove(User user, String batchId) throws MYException
	{
		List<BatchApproveBean> list = batchApproveDAO.queryEntityBeansByFK(batchId);
    	
    	// 只处理检查是OK的数据
    	for(Iterator<BatchApproveBean> iterator = list.iterator(); iterator.hasNext();)
    	{
    		BatchApproveBean  bean = iterator.next();
    		
    		if (bean.getRet() == 1)
    			iterator.remove();
    	}
		
    	//
    	for (BatchApproveBean each : list)
        {
            try
            {
            	processBatchApprove(user,each);
            	
            	// 成功的
            	updateBatchApprove(each, "批量处理成功");
            }
            catch (MYException e)
            {
                operationLog.error(e, e);
                
                // 异常的
                updateBatchApprove(each, e.getErrorContent());
            }
        }
    	
    	return true;
	}
	
	private void updateBatchApprove(final BatchApproveBean bean, final String result)
	{
		try
		{
			TransactionTemplate tran = new TransactionTemplate(transactionManager);
			
			tran.execute(new TransactionCallback()
			{
				public Object doInTransaction(TransactionStatus tstatus)
				{
					bean.setResult(result);
					
					batchApproveDAO.updateEntityBean(bean);
					
					return Boolean.TRUE;
				}
			}
			);
		}
        catch (Exception e)
        {
            throw new RuntimeException("系统错误，请联系管理员updateBatchApprove:" + e);
        }
		
        return;
	}
	
	/**
	 * CORE
	 * 
	 * @param user
	 * @param bean
	 * @throws MYException
	 */
	private void processBatchApprove(User user, BatchApproveBean bean) throws MYException
	{
		OutBean out = outDAO.find(bean.getOutId());
		
		if (null == out)
		{
			throw new MYException("销售单不存在");
		}
		
		// 商务审批,但状态已改变
		if (bean.getType()== 0)
		{
			if (out.getStatus() != OutConstant.STATUS_SUBMIT)
				throw new MYException("销售单不是待商务审批状态");
			else
			{
				if (bean.getAction().equals("通过"))
				{
					outManager.pass(bean.getOutId(), user, OutConstant.STATUS_FLOW_PASS, bean.getReason(), "");
				}else if (bean.getAction().equals("驳回")){
					
					outManager.reject(bean.getOutId(), user, bean.getReason());
					
				}else
				{
					throw new MYException("审批结果不是通过与驳回");
				}
			}
		}
		
		// 库管审批,但状态已改变
		if (bean.getType()== 1)
		{
			if (out.getStatus() != OutConstant.STATUS_FLOW_PASS)
				throw new MYException("销售单不是待库管审批状态");
			else
				if (bean.getAction().equals("通过"))
				{
					outManager.pass(bean.getOutId(), user, OutConstant.STATUS_PASS, bean.getReason(), "");
				}else if (bean.getAction().equals("驳回")){
					
					outManager.reject(bean.getOutId(), user, bean.getReason());
					
				}else
				{
					throw new MYException("审批结果不是通过与驳回");
				}
		}
	}
	
	@Transactional(rollbackFor = MYException.class)
	public String batchSwatchImport(List<BatchSwatchBean> list)
			throws MYException
	{
		JudgeTools.judgeParameterIsNull(list);
		
		String batchId = commonDAO.getSquenceString20();
		
		// 一个单号中不同的产品
		Map<String, List<BatchSwatchBean>> map = new HashMap<String, List<BatchSwatchBean>>();
		
		// 同一个单子同一个产品不能出现两次
		for (BatchSwatchBean each : list)
		{
			each.setBatchId(batchId);
			
			OutBean out = outDAO.find(each.getOutId());
			
			if (null == out)
			{
				each.setRet(1);
				each.setResult(each.getOutId() + " 销售单不存在");
			}
			
			if (out.getType() != OutConstant.OUT_TYPE_OUTBILL)
			{
				each.setRet(1);
				each.setResult(each.getOutId() + " 不是销售单");
			}
			
			if (out.getOutType() != OutConstant.OUTTYPE_OUT_SWATCH 
					&& out.getOutType() != OutConstant.OUTTYPE_OUT_SHOW
					&& out.getOutType() != OutConstant.OUTTYPE_OUT_SHOWSWATCH)
			{
				each.setRet(1);
				each.setResult(each.getOutId() + " 不是领样、铺货领样、巡展领样销售单");
			}
			
			String key = each.getOutId();
			
			if (map.containsKey(key))
			{
				List<BatchSwatchBean> blist = map.get(key);
				
				blist.add(each);
			}else{
				List<BatchSwatchBean> blist = new ArrayList<BatchSwatchBean>();
				
				blist.add(each);
				
				map.put(key, blist);
			}
		}
		
		// 确定 退货明细的有效性 - 一单一单的检查
		for(Entry<String, List<BatchSwatchBean>> entry : map.entrySet())
		{
			String outId = entry.getKey();
			
			List<BatchSwatchBean> bList = entry.getValue();
			
			//evalueSwatch
			List<BaseBean> baseList = evalueSwatch(outId);
            
            // 再与导入的数据进行匹配
            for (BatchSwatchBean each : bList)
            {
            	for(BaseBean eachBase : baseList)
            	{
            		int canUseAmount = eachBase.getAmount() - eachBase.getInway();
            		
            		if (each.getProductName().equals(eachBase.getProductName()) && each.getAmount() <= canUseAmount)
            		{
            			each.setRet(0);
            			each.setResult("OK");
            			
            			each.setBaseId(eachBase.getId());
            			
            			break;
            		}
            	}
            }
            
            batchSwatchDAO.saveAllEntityBeans(bList);
		}
		
		return batchId;
	}

	/**
	 * 计算某一领样或巡展单可以退或转的数量
	 * @param outId
	 * @return
	 */
	private List<BaseBean> evalueSwatch(String outId)
	{
		List<BaseBean> baseList = baseDAO.queryEntityBeansByFK(outId);
		
		// 检查商品是否存在
		
		// 要求导入的产品数量不能超过原单数量-已退-已转
		// 查询已转的销售
		ConditionParse con1 = new ConditionParse();
		
		con1.clear();

		con1.addWhereStr();

		con1.addCondition("OutBean.refOutFullId", "=", outId);

		con1.addCondition("OutBean.type", "=", OutConstant.OUT_TYPE_OUTBILL);

		List<OutBean> refList = outDAO.queryEntityBeansByCondition(con1);
		
		// 查询已退的退货
		List<OutBean> refBuyList = queryRefOut4(outId);
		
		// 计算出已经退货的数量
		for (Iterator<BaseBean> iterator = baseList.iterator(); iterator.hasNext();)
		{
		    BaseBean baseBean = iterator.next();

		    int hasBack = 0;

		    // 退库
		    for (OutBean ref : refBuyList)
		    {
		        List<BaseBean> refBaseList = ref.getBaseList();

		        for (BaseBean refBase : refBaseList)
		        {
		            if (refBase.equals(baseBean))
		            {
		                hasBack += refBase.getAmount();

		                break;
		            }
		        }
		    }

		    // 转销售的
		    for (OutBean ref : refList)
		    {
		        List<BaseBean> refBaseList = baseDAO.queryEntityBeansByFK(ref.getFullId());

		        for (BaseBean refBase : refBaseList)
		        {
		            if (refBase.equals(baseBean))
		            {
		                hasBack += refBase.getAmount();

		                break;
		            }
		        }
		    }

		    baseBean.setInway(hasBack);

		    int last = baseBean.getAmount() - baseBean.getInway();

			if (last <= 0)
				iterator.remove();
		}
		
		return baseList;
	}
	
	private List<OutBean> queryRefOut4(String outId)
	{
		// 查询当前已经有多少个人领样
		ConditionParse con = new ConditionParse();

		con.addWhereStr();

		con.addCondition("OutBean.refOutFullId", "=", outId);

		con.addIntCondition("OutBean.type", "=", OutConstant.OUT_TYPE_INBILL);

		// 排除其他入库(对冲单据)
		con.addCondition("OutBean.reserve8", "<>", "1");

		List<OutBean> refBuyList = outDAO.queryEntityBeansByCondition(con);

		for (OutBean outBean : refBuyList)
		{
			List<BaseBean> list = baseDAO.queryEntityBeansByFK(outBean
					.getFullId());

			outBean.setBaseList(list);
		}

		return refBuyList;
	}

	/**
	 * 处理领样批量转销售或退货
	 * 
	 * {@inheritDoc}
	 */
	public boolean batchSwatch(User user, String batchId) throws MYException
	{
		List<BatchSwatchBean> list = batchSwatchDAO.queryEntityBeansByFK(batchId);
    	
    	// 只处理检查是OK的数据
    	for(Iterator<BatchSwatchBean> iterator = list.iterator(); iterator.hasNext();)
    	{
    		BatchSwatchBean  bean = iterator.next();
    		
    		if (bean.getRet() == 1)
    			iterator.remove();
    	}
    	
    	if (ListTools.isEmptyOrNull(list))
    		return true;
    	
    	Map<String, List<BatchSwatchBean>> map = new HashMap<String, List<BatchSwatchBean>>();
		
		// 同一个单子同一个产品不能出现两次  - 
		for (BatchSwatchBean each : list)
		{
			String key = each.getOutId();
			
			if (map.containsKey(key))
			{
				List<BatchSwatchBean> blist = map.get(key);
				
				blist.add(each);
			}else{
				List<BatchSwatchBean> blist = new ArrayList<BatchSwatchBean>();
				
				blist.add(each);
				
				map.put(key, blist);
			}
		}
    	
		// 在事务中完成
		for(Entry<String, List<BatchSwatchBean>> entry : map.entrySet())
		{
			List<BatchSwatchBean> bList = entry.getValue();
			
			try
            {
            	processBatchSwatch(user,bList, batchId);
            	
            	// 成功的
            	updateBatchSwatch(bList, "批量处理成功");
            }
            catch (MYException e)
            {
                operationLog.error(e, e);
                
                // 异常的
                updateBatchSwatch(bList, e.getErrorContent());
            }
		}
		
    	return true;
	}
	
	/**
	 * CORE
	 * @param user
	 * @param bean
	 * @throws MYException
	 */
	private void processBatchSwatch(final User user, List<BatchSwatchBean> bList, final String batchId) throws MYException
	{
		final BatchSwatchBean bean = bList.get(0);
		
		OutBean out = outDAO.find(bean.getOutId());
		
		if (null == out)
		{
			throw new MYException("销售单不存在");
		}
		
		final List<BaseBean> backSwatchList = new ArrayList<BaseBean>();
		
		final List<BaseBean> outSwatchList = new ArrayList<BaseBean>();
		
		// 再次检查是否有足够的数量可以转或退
		List<BaseBean> baseList = evalueSwatch(bean.getOutId());
        
        // 再与导入的数据进行匹配
        for (BatchSwatchBean each : bList)
        {
        	for(BaseBean eachBase : baseList)
        	{
        		int canUseAmount = eachBase.getAmount() - eachBase.getInway();
        		
        		if (each.getBaseId().equals(eachBase.getId()))
        		{
        			if (each.getAmount() > canUseAmount)
            		{
            			throw new MYException("产品:"+ each.getProductName() + " 处理数量溢出.");
            		}
            		
            		if (each.getAction().equals("退货"))
            		{
            			BaseBean base = new BaseBean();
            			
            			BeanUtil.copyProperties(base, eachBase);
            			
            			base.setAmount(each.getAmount());
            			base.setValue(base.getAmount() * base.getPrice());
            			base.setInway(0);
            			base.setDescription(String.valueOf(eachBase
    							.getCostPrice()));

            			backSwatchList.add(base);
            		}
            		
            		if (each.getAction().equals("销售"))
            		{
            			BaseBean base = new BaseBean();
            			
            			BeanUtil.copyProperties(base, eachBase);
            			
            			base.setAmount(each.getAmount());
            			base.setValue(base.getAmount() * base.getPrice());
            			base.setInway(0);
            			
            			outSwatchList.add(base);
            		}
        		}
        	}
        }
        
        TransactionTemplate tran = new TransactionTemplate(transactionManager);
        
        try{
        	tran.execute(new TransactionCallback()
    		{
    			public Object doInTransaction(TransactionStatus tstatus)
    			{
    				try{
    					// 分别处理
    			        if (outSwatchList.size() > 0)
    			        {
    			        	OutBean outBean = setOutBean(user, bean, outSwatchList);
    			        	
    			        	outManager.addSwatchToSailWithoutTrans(user, outBean);
    			        	
    			        	int newNextStatus = OutConstant.STATUS_FLOW_PASS;
    			        	
    			        	// 修改状态
    			            outDAO.modifyOutStatus(outBean.getFullId(), newNextStatus);
    			        	
    			        	// 直接审批结束
    			        	// outManager.directPassWithoutTrans(user, outBean, OutConstant.OUT_TYPE_OUTBILL);
    			        	
    			        	addOutLog(outBean.getFullId(), user, outBean, "OK", SailConstant.OPR_OUT_PASS,
    			                    newNextStatus);
    			        	
    			        	saveLogInner(batchId, outBean);
    			        }
    			        
    			        if (backSwatchList.size() > 0)
    			        {
    			        	OutBean outBean = setOutBackBean(user, bean, backSwatchList);
    			        	
    			        	outManager.addSwatchToSailWithoutTrans(user, outBean);
    			        	
    			        	int newNextStatus = OutConstant.BUY_STATUS_SUBMIT;
    			        	
    			        	// 修改状态
    			            outDAO.modifyOutStatus(outBean.getFullId(), newNextStatus);
    			        	
    			        	// 直接审批结束
    			        	// outManager.directPassWithoutTrans(user, outBean, OutConstant.OUT_TYPE_INBILL);
    			        	
    			        	addOutLog(outBean.getFullId(), user, outBean, "OK", SailConstant.OPR_OUT_PASS,
    			                    newNextStatus);
    			        	
    			        	saveLogInner(batchId, outBean);
    			        }
    				}catch(MYException e)
    				{
    					throw new RuntimeException(e.getErrorContent(), e);
    				}
    		        
    				return Boolean.TRUE;
    			}
    		}
    		);
        } catch (TransactionException e)
        {
            _logger.error(e, e);
            throw new MYException("数据库内部错误");
        }
        catch (DataAccessException e)
        {
            _logger.error(e, e);
            throw new MYException("数据库内部错误");
        }
        catch (Exception e)
        {
            _logger.error(e, e);
            throw new MYException("处理异常:" + e.getMessage());
        }
	}
	
	private void saveLogInner(String batchId, OutBean outBean)
	{
		BatchReturnLog log = new BatchReturnLog();
		
		log.setBatchId(batchId);
		log.setOutId(outBean.getFullId());
		log.setOperator(outBean.getOperator());
		log.setOperatorName(outBean.getOperatorName());
		log.setLogTime(TimeTools.now_short());
		
		batchReturnLogDAO.saveEntityBean(log);
	}
	
	/**
     * 增加日志
     * 
     * @param fullId
     * @param user
     * @param outBean
     */
    private void addOutLog(final String fullId, final User user, final OutBean outBean, String des,
                           int mode, int astatus)
    {
        FlowLogBean log = new FlowLogBean();

        log.setActor(user.getStafferName());

        log.setDescription(des);
        log.setFullId(fullId);
        log.setOprMode(mode);
        log.setLogTime(TimeTools.now());

        log.setPreStatus(outBean.getStatus());

        log.setAfterStatus(astatus);

        flowLogDAO.saveEntityBean(log);
    }
	
	private OutBean setOutBean(User user, BatchSwatchBean sbean, List<BaseBean> bList)
    throws MYException
    {
    	OutBean bean = outDAO.find(sbean.getOutId());
    	
    	if (null == bean)
    	{
    		throw new MYException("数据错误");
    	}
    	
    	// 生成销售单,然后保存
        OutBean newOut = new OutBean();

        newOut.setOutTime(TimeTools.now_short());
        newOut.setType(OutConstant.OUT_TYPE_OUTBILL);
        newOut.setOutType(OutConstant.OUTTYPE_OUT_COMMON);
        newOut.setRefOutFullId(sbean.getOutId());
        newOut.setDutyId(bean.getDutyId());
        newOut.setMtype(bean.getMtype());
        newOut.setDescription("领样转销售,领样单据（批量导入）:" + sbean.getOutId() + "," + sbean.getDescription() );
        newOut.setDepartment(bean.getDepartment());
        newOut.setLocation(bean.getLocation());
        newOut.setLocationId(bean.getLocationId());
        newOut.setDepotpartId(bean.getDepotpartId());
        newOut.setStafferId(bean.getStafferId());
        newOut.setStafferName(bean.getStafferName());
        
        if (bean.getOutType() == OutConstant.OUTTYPE_OUT_SHOW)
        {
        	newOut.setCustomerId(bean.getCustomerId());
        	newOut.setCustomerName(bean.getCustomerName());
        	newOut.setConnector(bean.getConnector());
        }else{
        	newOut.setCustomerId(sbean.getCustomerId());
        	newOut.setCustomerName(sbean.getCustomerName());
        }
        
        newOut.setReday(1);
        newOut.setReserve3(1);

    	newOut.setOperator(user.getStafferId());
    	newOut.setOperatorName(user.getStafferName());
        
        newOut.setBaseList(bList);
        
        return newOut;
    }
	
	private OutBean setOutBackBean(User user, BatchSwatchBean sbean, List<BaseBean> bList)
    throws MYException
    {
    	OutBean oldOut = outDAO.find(sbean.getOutId());
    	
    	if (null == oldOut)
    	{
    		throw new MYException("数据错误");
    	}
    	
    	// 生成销售单,然后保存
    	OutBean out = new OutBean();

		out.setStatus(OutConstant.STATUS_SAVE);

		out.setStafferName(oldOut.getStafferName());

		out.setStafferId(oldOut.getStafferId());

		out.setType(OutConstant.OUT_TYPE_INBILL);

		out.setOutTime(TimeTools.now_short());

		out.setDepartment("采购部");

		if (oldOut.getOutType() == OutConstant.OUTTYPE_OUT_SHOW)
		{
			out.setCustomerId(oldOut.getCustomerId());

			out.setCustomerName(oldOut.getCustomerName());
		}
		else
		{
			out.setCustomerId("99");

			out.setCustomerName("系统内置供应商");
		}

		// 所在区域
		out.setLocationId(user.getLocationId());

		// 目的仓库
		out.setLocation(sbean.getDirDeport());

		out.setDestinationId(oldOut.getLocation());

		out.setInway(OutConstant.IN_WAY_NO);

		out.setOutType(OutConstant.OUTTYPE_IN_SWATCH);

		out.setRefOutFullId(sbean.getOutId());

		out.setDutyId(oldOut.getDutyId());

		out.setInvoiceId(oldOut.getInvoiceId());

		out.setDescription("个人领样退库(批量导入),领样单号:" + sbean.getOutId() + "," + sbean.getDescription());

		out.setOperator(user.getStafferId());
		out.setOperatorName(user.getStafferName());
        
		DepotpartBean okDepotpart = depotpartDAO.findDefaultOKDepotpart(out.getLocation());

		if (okDepotpart == null)
		{
			throw new MYException("仓库下没有良品仓");
		}
		
		for (BaseBean each : bList)
		{
			if (oldOut.getLocation().equals(out.getLocation()))
			{
				each.setDepotpartId(each.getDepotpartId());
				each.setDepotpartName(each.getDepotpartName());
			}
			else
			{
				each.setDepotpartId(okDepotpart.getId());
				each.setDepotpartName(okDepotpart.getName());
			}
		}
		
        out.setBaseList(bList);
        
        return out;
    }
	
	private void updateBatchSwatch(final List<BatchSwatchBean> bList, final String result)
	{
		try
		{
			TransactionTemplate tran = new TransactionTemplate(transactionManager);
			
			tran.execute(new TransactionCallback()
			{
				public Object doInTransaction(TransactionStatus tstatus)
				{
					for (BatchSwatchBean each : bList)
					{
						each.setResult(result);
						
						batchSwatchDAO.updateEntityBean(each);
					}
					
					return Boolean.TRUE;
				}
			}
			);
		}
        catch (Exception e)
        {
            throw new RuntimeException("系统错误，请联系管理员updateBatchApprove:" + e);
        }
		
        return;
	}
	
	@Transactional(rollbackFor = MYException.class)
	public boolean batchUpdateConsign(List<ConsignBean> list) throws MYException
	{
		JudgeTools.judgeParameterIsNull(list);
		
		for (ConsignBean each : list)
		{
			Set<String> set = new HashSet<String>();
			
			// each.getDistId() 改为导入的是 出库单，根据出库单找到销售单
			List<PackageItemBean> itemList = packageItemDAO.queryEntityBeansByFK(each.getDistId());
			
			for (PackageItemBean eachItem : itemList)
			{
				if (!set.contains(eachItem.getOutId()))
				{
					set.add(eachItem.getOutId());
				}
			}
			
			for (String s : set)
			{
				ConsignBean oldBean = consignDAO.findDefaultConsignByFullId(s);
				
				if (null != oldBean)
				{
					oldBean.setTransport(each.getTransport());
					oldBean.setTransportNo(each.getTransportNo());
					oldBean.setSendPlace(each.getSendPlace());
					oldBean.setPreparer(each.getPreparer());
					oldBean.setChecker(each.getChecker());
					oldBean.setPackager(each.getPackager());
					oldBean.setPackageTime(each.getPackageTime());
					oldBean.setMathine(each.getMathine());
					oldBean.setPackageAmount(each.getPackageAmount());
					oldBean.setPackageWeight(each.getPackageWeight());
					oldBean.setTransportFee(each.getTransportFee());
					oldBean.setReveiver(each.getReveiver());
					oldBean.setApplys(each.getApplys());
					
					consignDAO.updateConsign(oldBean);
				} else {
					List<DistributionBean> distList = distributionDAO.queryEntityBeansByFK(s);
					
					if (!ListTools.isEmptyOrNull(distList)) {
						oldBean = new ConsignBean();

						oldBean.setCurrentStatus(SailConstant.CONSIGN_PASS);

						oldBean.setGid(commonDAO.getSquenceString20());

						oldBean.setDistId(distList.get(0).getId());
			            
						oldBean.setFullId(s);

						oldBean.setTransport(each.getTransport());
						oldBean.setTransportNo(each.getTransportNo());
						oldBean.setSendPlace(each.getSendPlace());
						oldBean.setPreparer(each.getPreparer());
						oldBean.setChecker(each.getChecker());
						oldBean.setPackager(each.getPackager());
						oldBean.setPackageTime(each.getPackageTime());
						oldBean.setMathine(each.getMathine());
						oldBean.setPackageAmount(each.getPackageAmount());
						oldBean.setPackageWeight(each.getPackageWeight());
						oldBean.setTransportFee(each.getTransportFee());
						oldBean.setReveiver(each.getReveiver());
						oldBean.setApplys(each.getApplys());
						
			            consignDAO.addConsign(oldBean);
					}
				}
			}
		}
		
		return true;
	}
	
	/***
	 * 批量更新配送地址
	 */
	@Transactional(rollbackFor = MYException.class)
	public boolean batchUpdateDistAddr(List<DistributionBean> list)
	throws MYException
	{
        System.out.println("aaaaaaaaaaaaaaaaaaaaaaaaa");
		if (ListTools.isEmptyOrNull(list))
		{
			throw new MYException("导入异常");
		}
        System.out.println("aaaaaaaaaaaaaaaaaaaaaaaaa11");
		for(DistributionBean each : list)
		{
            System.out.println("aaaaaaaaaaaaaaaaaaaaaaaaa22"+each.getOutId());
			List<DistributionBean> distList = null;
            try{
            distList = distributionDAO.queryEntityBeansByFK(each.getOutId());
            }catch(Exception e){
                e.printStackTrace();
            }
            System.out.println("aaaaaaaaaaaaaaaaaaaaaaaaa333");
			if (ListTools.isEmptyOrNull(distList))
			{
				throw new MYException("导入异常，请退出重新登陆");
			}
			
			for (DistributionBean reach : distList)
			{
				/*reach.setProvinceId(each.getProvinceId());
				reach.setCityId(each.getCityId());
				reach.setAreaId(each.getAreaId());
				reach.setAddress(each.getAddress());
				reach.setReceiver(each.getReceiver());
				reach.setMobile(each.getMobile());
				reach.setShipping(each.getShipping());
				
				distributionDAO.updateEntityBean(reach);*/
				
				operationLog.info("地址更新，销售单：" + reach.getOutId());
				operationLog.info("新省：" + each.getProvinceId());
				operationLog.info("新市：" + each.getCityId());
				operationLog.info("新区：" + each.getAreaId());
				operationLog.info("新地址：" + each.getAddress());
				operationLog.info("新接收人：" + each.getReceiver());
				operationLog.info("新手机：" + each.getMobile());
				operationLog.info("新发货方式：" + each.getShipping());
                System.out.println("************销售单备注：" + each.getDescription());
				
				Boolean ret = distributionDAO.updateBean(reach.getId(), each);
				
				if (!ret)
				{
					throw new MYException("更新失败,请检查源文件");
				}

                System.out.println("aaaaaaaaaaaaaaaaaaaaaaaaa444"+reach.getOutId()+":"+each.getDescription());
                Boolean ret2 = this.outDAO.updateDescription(reach.getOutId(), each.getDescription());

                if (!ret2)
                {
                    throw new MYException("更新销售单备注失败,请检查源文件");
                }
			}
		}
		
		return true;
	}
	
	/***
	 * 批量更新销售单的 紧急 状态 （0 -> 1)
	 */
	@Transactional(rollbackFor = MYException.class)
	public boolean batchUpdateEmergency(List<ConsignBean> list)
	throws MYException
	{
		if (ListTools.isEmptyOrNull(list))
		{
			throw new MYException("导入异常");
		}
		
		for(ConsignBean each : list)
		{
			OutBean out = outDAO.find(each.getFullId());
			
			if (out == null)
			{
				throw new MYException("导入异常，请退出重新登陆");
			}
			
			operationLog.info("紧急状态更新，销售单：" + each.getFullId());
			
			outDAO.updateEmergency(each.getFullId(), 1);
		}
		
		return true;
	}
	
	/***
	 * 批量更新销售单的 仓库、仓区
	 */
	@Transactional(rollbackFor = MYException.class)
	public boolean batchUpdateDepot(List<BaseBean> list)
	throws MYException
	{
		if (ListTools.isEmptyOrNull(list))
		{
			throw new MYException("导入异常");
		}
		
		// 本批次导入，检查订单对应的产品数与单据中行项目数是否一致，须一致
		Map<String, Integer> cmap = new HashMap<String, Integer>();
		
		for (BaseBean each : list) {
			if (cmap.containsKey(each.getOutId())) {
				int i = cmap.get(each.getOutId());
				
				cmap.put(each.getOutId(), i + 1);
			} else {
				cmap.put(each.getOutId(), 1);
			}
		}
		
		for (Entry<String, Integer> eachMap : cmap.entrySet()) {
			int count = baseDAO.countByFK(eachMap.getKey());
			
			if (count != eachMap.getValue()) {
				throw new MYException("批量修改仓库，要求导入的文件中含有全部的商品行项目.");
			}
		}
		
		for(BaseBean each : list)
		{
			OutBean out = outDAO.find(each.getOutId());
			
			if (out == null)
			{
				throw new MYException("导入异常，请退出重新登陆");
			}
			
			if (out.getStatus() != OutConstant.STATUS_SUBMIT) {
				throw new MYException("[%s]状态不是待商务审批。", each.getOutId());
			}
			
			outDAO.updateLocation(each.getOutId(), each.getLocationId());
			
			baseDAO.updateLocationIdAndDepotpartByOutIdAndProductId(each.getLocationId(), each.getDepotpartId(), 
					each.getDepotpartName(), each.getOutId(), each.getProductId());
			
			operationLog.info("仓库更新，销售单：" + each.getOutId());
		}
		
		return true;
	}
	
	/***
	 * 批量延期账期
	 */
	@Transactional(rollbackFor = MYException.class)
	public boolean batchUpdateRedate(List<OutBean> list)
	throws MYException
	{
		for(OutBean each : list)
		{
			OutBean out = outDAO.find(each.getFullId());
			
			if (null != out)
			{
				out.setReday(out.getReday() + each.getReday());
				
				out.setRedate(TimeTools.getSpecialDateStringByDays(out.getRedate() + " 00:00:01", each.getReday(), "yyyy-MM-dd"));
				
				outDAO.updateEntityBean(out);
			}
		}
		
		return true;
	}
	
	/**
	 * preUseAmountCheck
	 */
	public List<OutImportBean> preUseAmountCheck(String batchId)
	{
		List<OutImportBean> list = outImportDAO.queryEntityBeansByFK(batchId);
		
		Map<String, OutImportBean> map = new HashMap<String, OutImportBean>();
		
		// 1.根据产品进行分组
		for (OutImportBean each : list)
		{
			if (each.getItype() != 2)
			{
				break;
			}
			
			if (each.getPreUse() == OutImportConstant.PREUSE_YES)
			{
				continue;
			}
			
			OutBean out = outDAO.find(each.getOANo());

			if (null == out)
			{
				continue;
			}
			
			if (out.getStatus() != OutConstant.STATUS_SUBMIT)
			{
				continue;
			}
			
			if (!map.containsKey(each.getProductId()))
			{
				map.put(each.getProductId(), each);
			}else
			{
				OutImportBean bean = map.get(each.getProductId());
				
				bean.setAmount(bean.getAmount() + each.getAmount());
			}
		}
		
		List<OutImportBean> resultList = new ArrayList<OutImportBean>();

		// 2.根据汇总的数据，查找商品的库总数
		for (Entry<String, OutImportBean> entry : map.entrySet())
		{
			OutImportBean each = entry.getValue();
			
			resultList.add(each);
			
			ProductChangeWrap wrap = new ProductChangeWrap();

            wrap.setDepotpartId(each.getDepotpartId());
            wrap.setPrice(0);
            wrap.setProductId(each.getProductId());
            wrap.setStafferId("0");

            wrap.setChange( -each.getAmount());

            List<StorageRelationBean> relationList = null;
			try
			{
				relationList = storageRelationManager.checkStorageRelation3(wrap, false);
			}
			catch (MYException e)
			{
				each.setMayAmount(0);
				
				continue;
			}
            
            // 足够库存，开始拆分
            if (!ListTools.isEmptyOrNull(relationList))
            {
            	int amount = 0;
            	for (StorageRelationBean eachs : relationList)
            	{
            		if (eachs.getAmount() <= 0)
            			continue;
            		
            		amount += eachs.getAmount();
            	}
            	
            	each.setMayAmount(amount);
            }
            else
            {
            	each.setMayAmount(0);
            }
		}
		
		return resultList;
	}
	
	/**
	 * processSplitOut
	 */
	public void processSplitOut(final String batchId)
	{
		List<OutImportBean> list = outImportDAO.queryEntityBeansByFK(batchId);
		
		for (OutImportBean each : list)
		{
			if (each.getItype() != 2)
			{
				break;
			}
			
			if (each.getPreUse() == OutImportConstant.PREUSE_YES)
			{
				continue;
			}

			final OutBean out = outDAO.find(each.getOANo());

			if (null == out)
			{
				continue;
			}
			
			if (out.getStatus() != OutConstant.STATUS_SUBMIT)
			{
				continue;
			}
			
			final List<BaseBean> baseList = baseDAO.queryEntityBeansByFK(out.getFullId());
			
			if (baseList.get(0).getCostPrice() != 0)
			{
				continue;
			}
			
			final String id = each.getFullId();
			
        	TransactionTemplate tran = new TransactionTemplate(transactionManager);
        	
            try
            {
                tran.execute(new TransactionCallback()
                {
                    public Object doInTransaction(TransactionStatus arg0)
                    {
                		List<BaseBean> newBaseList = null;
						try
						{
							newBaseList = outManager.splitBase(baseList);
						}
						catch (MYException e)
						{
							throw new RuntimeException(e.getErrorContent(), e);
						}

						baseDAO.deleteEntityBeansByFK(out.getFullId());
                    	
                    	baseDAO.saveAllEntityBeans(newBaseList);
                    	
                    	// 更新状态
                    	outImportDAO.updatePreUse(id, OutImportConstant.PREUSE_YES);
                    	
                    	/*if (!link.contains(batchId))
                    	{
                    		outImportLogDAO.updateStatus(batchId, OutImportConstant.LOGSTATUS_SUCCESSPREUSE);
                    		
                    		link.add(batchId);
                    	}*/
                    	
                        return Boolean.TRUE;
                    }
                });
            }
            catch (Exception e)
            {
            	operationLog.error(e, e);
            }
		}
		
	}
	
	@Transactional(rollbackFor = MYException.class)
	public String addBankSail(User user, List<BankSailBean> list) throws MYException
	{
		JudgeTools.judgeParameterIsNull(user, list);
		
		String batchId = commonDAO.getSquenceString20();
		
		for (BankSailBean each : list) {
			each.setBatchId(batchId);
		}
		
		bankSailDAO.saveAllEntityBeans(list);
		
		return batchId;
	}
	
	@Transactional(rollbackFor = MYException.class)
	public boolean deleteBankSail(User user,String batchId)	throws MYException {
		JudgeTools.judgeParameterIsNull(user, batchId);
		
		bankSailDAO.deleteEntityBeansByFK(batchId);

		operationLog.info(user.getStafferName() + " /delete 银行导入销售数据, 批次号:" + batchId);
		
		return true;
	}
	
	@Transactional(rollbackFor = MYException.class)
	public boolean addEstimateProfit(User user, List<EstimateProfitBean> list)
			throws MYException
	{
		JudgeTools.judgeParameterIsNull(user, list);
		
		//String batchId = commonDAO.getSquenceString20();
		
		for (EstimateProfitBean each : list) {
			EstimateProfitBean old = estimateProfitDAO.findByUnique(each.getProductName());
			
			if (old != null)
			{
				estimateProfitDAO.deleteEntityBean(old.getId());
			}
		}
		
		estimateProfitDAO.saveAllEntityBeans(list);
		
		return true;
	}

	@Transactional(rollbackFor = MYException.class)
	public boolean deleteEstimateProfit(User user, String id)
			throws MYException
	{
		JudgeTools.judgeParameterIsNull(user, id);
		
		EstimateProfitBean bean = estimateProfitDAO.find(id);
		
		if (null != bean) {
			operationLog.info(user.getStafferName() + " /delete 银行导入产品预估毛利, 产品:" + bean.getProductName());
		}
		
		estimateProfitDAO.deleteEntityBean(id);

		return true;
	}
	
	public PlatformTransactionManager getTransactionManager()
	{
		return transactionManager;
	}

	public void setTransactionManager(PlatformTransactionManager transactionManager)
	{
		this.transactionManager = transactionManager;
	}

	public CommonDAO getCommonDAO()
	{
		return commonDAO;
	}

	public void setCommonDAO(CommonDAO commonDAO)
	{
		this.commonDAO = commonDAO;
	}

	public OutImportDAO getOutImportDAO()
	{
		return outImportDAO;
	}

	public void setOutImportDAO(OutImportDAO outImportDAO)
	{
		this.outImportDAO = outImportDAO;
	}

	public StorageRelationDAO getStorageRelationDAO()
	{
		return storageRelationDAO;
	}

	public void setStorageRelationDAO(StorageRelationDAO storageRelationDAO)
	{
		this.storageRelationDAO = storageRelationDAO;
	}

	public StorageRelationManager getStorageRelationManager()
	{
		return storageRelationManager;
	}

	public void setStorageRelationManager(
			StorageRelationManager storageRelationManager)
	{
		this.storageRelationManager = storageRelationManager;
	}

	public ProductDAO getProductDAO()
	{
		return productDAO;
	}

	public void setProductDAO(ProductDAO productDAO)
	{
		this.productDAO = productDAO;
	}

	public FlowLogDAO getFlowLogDAO()
	{
		return flowLogDAO;
	}

	public void setFlowLogDAO(FlowLogDAO flowLogDAO)
	{
		this.flowLogDAO = flowLogDAO;
	}

	public LocationDAO getLocationDAO()
	{
		return locationDAO;
	}

	public void setLocationDAO(LocationDAO locationDAO)
	{
		this.locationDAO = locationDAO;
	}

	public StafferDAO getStafferDAO()
	{
		return stafferDAO;
	}

	public void setStafferDAO(StafferDAO stafferDAO)
	{
		this.stafferDAO = stafferDAO;
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

	public SailConfigManager getSailConfigManager()
	{
		return sailConfigManager;
	}

	public void setSailConfigManager(SailConfigManager sailConfigManager)
	{
		this.sailConfigManager = sailConfigManager;
	}

	public DistributionDAO getDistributionDAO()
	{
		return distributionDAO;
	}

	public void setDistributionDAO(DistributionDAO distributionDAO)
	{
		this.distributionDAO = distributionDAO;
	}

	public OutImportResultDAO getOutImportResultDAO()
	{
		return outImportResultDAO;
	}

	public void setOutImportResultDAO(OutImportResultDAO outImportResultDAO)
	{
		this.outImportResultDAO = outImportResultDAO;
	}

	public ReplenishmentDAO getReplenishmentDAO()
	{
		return replenishmentDAO;
	}

	public void setReplenishmentDAO(ReplenishmentDAO replenishmentDAO)
	{
		this.replenishmentDAO = replenishmentDAO;
	}

	public ConsignDAO getConsignDAO()
	{
		return consignDAO;
	}

	public void setConsignDAO(ConsignDAO consignDAO)
	{
		this.consignDAO = consignDAO;
	}

	public OutImportLogDAO getOutImportLogDAO()
	{
		return outImportLogDAO;
	}

	public void setOutImportLogDAO(OutImportLogDAO outImportLogDAO)
	{
		this.outImportLogDAO = outImportLogDAO;
	}

	public CustomerMainDAO getCustomerMainDAO() {
		return customerMainDAO;
	}

	public void setCustomerMainDAO(CustomerMainDAO customerMainDAO) {
		this.customerMainDAO = customerMainDAO;
	}

	public StafferVSCustomerDAO getStafferVSCustomerDAO()
	{
		return stafferVSCustomerDAO;
	}

	public void setStafferVSCustomerDAO(StafferVSCustomerDAO stafferVSCustomerDAO)
	{
		this.stafferVSCustomerDAO = stafferVSCustomerDAO;
	}

	public ProvinceDAO getProvinceDAO()
	{
		return provinceDAO;
	}

	public void setProvinceDAO(ProvinceDAO provinceDAO)
	{
		this.provinceDAO = provinceDAO;
	}

	public CityDAO getCityDAO()
	{
		return cityDAO;
	}

	public void setCityDAO(CityDAO cityDAO)
	{
		this.cityDAO = cityDAO;
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

	/**
	 * @return the distributionBaseDAO
	 */
	public DistributionBaseDAO getDistributionBaseDAO()
	{
		return distributionBaseDAO;
	}

	/**
	 * @param distributionBaseDAO the distributionBaseDAO to set
	 */
	public void setDistributionBaseDAO(DistributionBaseDAO distributionBaseDAO)
	{
		this.distributionBaseDAO = distributionBaseDAO;
	}

	/**
	 * @return the batchApproveDAO
	 */
	public BatchApproveDAO getBatchApproveDAO()
	{
		return batchApproveDAO;
	}

	/**
	 * @param batchApproveDAO the batchApproveDAO to set
	 */
	public void setBatchApproveDAO(BatchApproveDAO batchApproveDAO)
	{
		this.batchApproveDAO = batchApproveDAO;
	}

	/**
	 * @return the outManager
	 */
	public OutManager getOutManager()
	{
		return outManager;
	}

	/**
	 * @param outManager the outManager to set
	 */
	public void setOutManager(OutManager outManager)
	{
		this.outManager = outManager;
	}

	/**
	 * @return the batchSwatchDAO
	 */
	public BatchSwatchDAO getBatchSwatchDAO()
	{
		return batchSwatchDAO;
	}

	/**
	 * @param batchSwatchDAO the batchSwatchDAO to set
	 */
	public void setBatchSwatchDAO(BatchSwatchDAO batchSwatchDAO)
	{
		this.batchSwatchDAO = batchSwatchDAO;
	}

	/**
	 * @return the addressDAO
	 */
	public AddressDAO getAddressDAO()
	{
		return addressDAO;
	}

	/**
	 * @param addressDAO the addressDAO to set
	 */
	public void setAddressDAO(AddressDAO addressDAO)
	{
		this.addressDAO = addressDAO;
	}

	public ProductVSGiftDAO getProductVSGiftDAO()
	{
		return productVSGiftDAO;
	}

	public void setProductVSGiftDAO(ProductVSGiftDAO productVSGiftDAO)
	{
		this.productVSGiftDAO = productVSGiftDAO;
	}

	public DepotpartDAO getDepotpartDAO()
	{
		return depotpartDAO;
	}

	public void setDepotpartDAO(DepotpartDAO depotpartDAO)
	{
		this.depotpartDAO = depotpartDAO;
	}

	/**
	 * @return the areaDAO
	 */
	public AreaDAO getAreaDAO()
	{
		return areaDAO;
	}

	/**
	 * @param areaDAO the areaDAO to set
	 */
	public void setAreaDAO(AreaDAO areaDAO)
	{
		this.areaDAO = areaDAO;
	}

	/**
	 * @return the packageItemDAO
	 */
	public PackageItemDAO getPackageItemDAO()
	{
		return packageItemDAO;
	}

	/**
	 * @param packageItemDAO the packageItemDAO to set
	 */
	public void setPackageItemDAO(PackageItemDAO packageItemDAO)
	{
		this.packageItemDAO = packageItemDAO;
	}

	/**
	 * @return the batchReturnLogDAO
	 */
	public BatchReturnLogDAO getBatchReturnLogDAO()
	{
		return batchReturnLogDAO;
	}

	/**
	 * @param batchReturnLogDAO the batchReturnLogDAO to set
	 */
	public void setBatchReturnLogDAO(BatchReturnLogDAO batchReturnLogDAO)
	{
		this.batchReturnLogDAO = batchReturnLogDAO;
	}

	/**
	 * @return the bankSailDAO
	 */
	public BankSailDAO getBankSailDAO()
	{
		return bankSailDAO;
	}

	/**
	 * @param bankSailDAO the bankSailDAO to set
	 */
	public void setBankSailDAO(BankSailDAO bankSailDAO)
	{
		this.bankSailDAO = bankSailDAO;
	}

	/**
	 * @return the estimateProfitDAO
	 */
	public EstimateProfitDAO getEstimateProfitDAO()
	{
		return estimateProfitDAO;
	}

	/**
	 * @param estimateProfitDAO the estimateProfitDAO to set
	 */
	public void setEstimateProfitDAO(EstimateProfitDAO estimateProfitDAO)
	{
		this.estimateProfitDAO = estimateProfitDAO;
	}
}
