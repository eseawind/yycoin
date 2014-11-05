package com.china.center.oa.extsail.manager.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

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
import com.china.center.jdbc.expression.Expression;
import com.china.center.oa.client.bean.CustomerBean;
import com.china.center.oa.client.dao.CustomerMainDAO;
import com.china.center.oa.client.dao.StafferVSCustomerDAO;
import com.china.center.oa.client.vo.StafferVSCustomerVO;
import com.china.center.oa.extsail.bean.ZJRCBaseBean;
import com.china.center.oa.extsail.bean.ZJRCDistributionBean;
import com.china.center.oa.extsail.bean.ZJRCOutBean;
import com.china.center.oa.extsail.bean.ZJRCProductBean;
import com.china.center.oa.extsail.dao.ZJRCBaseDAO;
import com.china.center.oa.extsail.dao.ZJRCDistributionDAO;
import com.china.center.oa.extsail.dao.ZJRCOutDAO;
import com.china.center.oa.extsail.dao.ZJRCProductDAO;
import com.china.center.oa.extsail.manager.ZJRCManager;
import com.china.center.oa.product.bean.PriceConfigBean;
import com.china.center.oa.product.bean.ProductBean;
import com.china.center.oa.product.dao.PriceConfigDAO;
import com.china.center.oa.product.dao.ProductDAO;
import com.china.center.oa.product.manager.PriceConfigManager;
import com.china.center.oa.publics.bean.FlowLogBean;
import com.china.center.oa.publics.bean.StafferBean;
import com.china.center.oa.publics.constant.PublicConstant;
import com.china.center.oa.publics.dao.CommonDAO;
import com.china.center.oa.publics.dao.FlowLogDAO;
import com.china.center.oa.publics.dao.StafferDAO;
import com.china.center.oa.publics.dao.UserDAO;
import com.china.center.oa.publics.vo.StafferVO;
import com.china.center.oa.sail.bean.BaseBean;
import com.china.center.oa.sail.bean.DistributionBean;
import com.china.center.oa.sail.bean.OutBean;
import com.china.center.oa.sail.bean.OutImportBean;
import com.china.center.oa.sail.bean.SailConfBean;
import com.china.center.oa.sail.constanst.OutConstant;
import com.china.center.oa.sail.constanst.OutImportConstant;
import com.china.center.oa.sail.dao.BaseDAO;
import com.china.center.oa.sail.dao.DistributionDAO;
import com.china.center.oa.sail.dao.OutDAO;
import com.china.center.oa.sail.dao.OutImportDAO;
import com.china.center.oa.sail.helper.OutHelper;
import com.china.center.oa.sail.manager.SailConfigManager;
import com.china.center.tools.JudgeTools;
import com.china.center.tools.ListTools;
import com.china.center.tools.MathTools;
import com.china.center.tools.ParamterMap;
import com.china.center.tools.StringTools;
import com.china.center.tools.TimeTools;

public class ZJRCManagerImpl implements ZJRCManager
{
	private final Log _logger = LogFactory.getLog(getClass());
	
	private final Log triggerLog = LogFactory.getLog("trigger");

    private CommonDAO commonDAO = null;

    private ProductDAO productDAO = null;
    
    private ZJRCProductDAO zjrcProductDAO = null;
    
    private ZJRCOutDAO zjrcOutDAO = null;
    
    private ZJRCBaseDAO zjrcBaseDAO = null;
    
    private UserDAO userDAO = null;

    private StafferDAO stafferDAO = null;
    
    private StafferVSCustomerDAO stafferVSCustomerDAO = null;
    
    private PriceConfigDAO priceConfigDAO = null;
	
	private PriceConfigManager priceConfigManager = null;
	
	private OutDAO outDAO = null;

    private BaseDAO baseDAO = null;
    
    private DistributionDAO distributionDAO = null;
    
    private SailConfigManager sailConfigManager = null;
    
    private FlowLogDAO flowLogDAO = null;
    
    private ZJRCDistributionDAO zjrcDistributionDAO = null;
    
    private CustomerMainDAO customerMainDAO = null;
    
    private OutImportDAO outImportDAO = null;
    
    private PlatformTransactionManager transactionManager = null;
    
    /**
     * default constructor
     */
    public ZJRCManagerImpl()
    {
    }
    
    @Transactional(rollbackFor = MYException.class)
	public boolean addZJRCProduct(User user, ZJRCProductBean bean)
			throws MYException
	{
        JudgeTools.judgeParameterIsNull(user, bean);
        
        bean.setZjrProductName(bean.getZjrProductName().trim());
        
        bean.setStafferId(user.getStafferId());
        
        bean.setStafferName(user.getStafferName());
        
        bean.setLogTime(TimeTools.now());

        Expression exp = new Expression(bean, this);

        exp.check("#zjrProductName &unique @zjrcProductDAO",
            "开单品名已经存在");
        
        bean.setId(commonDAO.getSquenceString20());

        return zjrcProductDAO.saveEntityBean(bean);
    }

    @Transactional(rollbackFor = MYException.class)
	public boolean updateZJRCProduct(User user, ZJRCProductBean bean)
			throws MYException
	{
        JudgeTools.judgeParameterIsNull(user, bean);

        ZJRCProductBean old = zjrcProductDAO.find(bean.getId());

        if (old == null)
        {
            throw new MYException("数据错误,请确认操作");
        }

        if (!old.getZjrProductName().trim().equals(bean.getZjrProductName().trim()))
        {
        	Expression exp = new Expression(bean, this);

            exp.check("#zjrProductName &unique @zjrcProductDAO",
            "开单品名已经存在");
        }
        
        bean.setZjrProductName(bean.getZjrProductName().trim());
        
        bean.setStafferId(user.getStafferId());
        
        bean.setStafferName(user.getStafferName());
        
        bean.setLogTime(TimeTools.now());
        
        zjrcProductDAO.updateEntityBean(bean);

        return true;
    }

    @Transactional(rollbackFor = MYException.class)
	public boolean deleteZJRCProduct(User user, String id) throws MYException
	{
        JudgeTools.judgeParameterIsNull(user, id);

        ZJRCProductBean conf = zjrcProductDAO.find(id);

        if (conf == null)
        {
            throw new MYException("数据错误,请确认操作");
        }

        return zjrcProductDAO.deleteEntityBean(id);
    }

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public String addZJRCOut(final ZJRCOutBean outBean, final Map dataMap, final User user)
			throws MYException
	{
        ParamterMap request = new ParamterMap(dataMap);

        String fullId = request.getParameter("fullId");

        if (StringTools.isNullOrNone(fullId))
        {
            // 先保存
            String id = getAll(commonDAO.getSquence());

            String flag = "ZJ";
            
            String time = TimeTools.getStringByFormat(new Date(), "yyMMddHHmm");

            fullId = flag + time + id;

            outBean.setId(getOutId(id));

            outBean.setFullId(fullId);

            dataMap.put("modify", false);
        }
        else
        {
            dataMap.put("modify", true);
        }

        final String totalss = request.getParameter("totalss");

        outBean.setTotal(MathTools.parseDouble(totalss));
        
        outBean.setStatus(OutImportConstant.STATUS_SAVE);

        // 获得baseList
        final String[] nameList = request.getParameter("nameList").split("~");
        final String[] idsList = request.getParameter("idsList").split("~");
        final String[] amontList = request.getParameter("amontList").split("~");

        // 含税价
        final String[] priceList = request.getParameter("priceList").split("~");
        final String[] costpriceList = request.getParameter("costpriceList").split("~");
        final String[] midrevenueList = request.getParameter("midrevenueList").split("~");

        _logger.info(fullId + "/nameList/" + request.getParameter("nameList"));

        _logger.info(fullId + "/idsList/" + request.getParameter("idsList"));

        _logger.info(fullId + "/totalList/" + request.getParameter("totalList"));

        _logger.info(fullId + "/price/" + request.getParameter("priceList"));
        _logger.info(fullId + "/costprice/" + request.getParameter("costpriceList"));
        _logger.info(fullId + "/midrevenue/" + request.getParameter("midrevenueList"));

        // 组织BaseBean
        double ttatol = 0.0d;
        
        for (int i = 0; i < nameList.length; i++ )
        {
            ttatol += (Double.parseDouble(priceList[i]) * Integer.parseInt(amontList[i]));
        }

        outBean.setTotal(ttatol);

        final StafferVO stafferBean = stafferDAO.findVO(user.getStafferId());

        // 防止商务登陆多人在不同的页面，导致脏数据
        if (!user.getStafferName().equals(outBean.getStafferName()))
        {
        	throw new MYException("当前登陆者为[%s],但系统中记录的是[%s],请重新登陆！", user.getStafferName(), outBean.getStafferName());
        }
        
        outBean.setCustomerName(stafferBean.getIndustryName3());
        
        CustomerBean customer = customerMainDAO.findByUnique(stafferBean.getIndustryName3());
        
        if (null == customer) {
        	throw new MYException("客户[%s]不存在", stafferBean.getIndustryName3());
        }
        
        // ZJRC 客户定为紫金农商的人员对应的部门
        outBean.setCustomerId(customer.getId());
        
        outBean.setLocation(OutImportConstant.ZJRC_DEPOT);
        
        // 增加管理员操作在数据库事务中完成
        TransactionTemplate tran = new TransactionTemplate(transactionManager);
        try
        {
            tran.execute(new TransactionCallback()
            {
                public Object doInTransaction(TransactionStatus tran)
                {
                    if ((Boolean)dataMap.get("modify"))
                    {
                        // 在删除前检查单据状态是否为保存或驳回
                        ZJRCOutBean coutBean = zjrcOutDAO.find(outBean.getFullId());
                        
                        if (coutBean.getStatus() != OutConstant.STATUS_SAVE && coutBean.getStatus() != OutConstant.STATUS_REJECT)
                        {
                            throw new RuntimeException("销售单"+outBean.getFullId()+"已不是保存或驳回状态，当前状态是："+OutHelper.getStatus(coutBean.getStatus())+",不可修改");
                        }
                        
                        zjrcOutDAO.deleteEntityBean(outBean.getFullId());

                        zjrcBaseDAO.deleteEntityBeansByFK(outBean.getFullId());
                    }

                    // 组织BaseBean
                    boolean addSub = false;

                    double total = 0.0d;
                    
                    List<ZJRCBaseBean> baseList = new ArrayList<ZJRCBaseBean>();

                    // 处理每个base
                    for (int i = 0; i < nameList.length; i++ )
                    {
                        ZJRCBaseBean base = new ZJRCBaseBean();

                        base.setId(commonDAO.getSquenceString());

                        // 允许存在
                        base.setAmount(MathTools.parseInt(amontList[i]));

                        if (base.getAmount() == 0)
                        {
                            continue;
                        }

                        base.setOutId(outBean.getFullId());

                        base.setZjrcProductId(idsList[i]);
                        
                        if (StringTools.isNullOrNone(base.getZjrcProductId()))
                        {
                            throw new RuntimeException("产品ID为空,数据不完备");
                        }
                        
                        // ZJRC 配置的产品
                        ZJRCProductBean zjrcProduct = zjrcProductDAO.find(base.getZjrcProductId());
                        
                        if (null == zjrcProduct)
                        {
                        	throw new RuntimeException("产品为空,数据不完备");
                        }
                        
                        base.setZjrcProductName(zjrcProduct.getZjrProductName());
                        
                        ProductBean product = productDAO.find(zjrcProduct.getProductId());

                        if (product == null)
                        {
                            throw new RuntimeException("OA产品为空,数据不完备");
                        }
                        
                        base.setProductId(product.getId());
                        // 产品名称来源于数据库
                        base.setProductName(product.getName());
                        
                        base.setPrice(MathTools.parseDouble(priceList[i]));
                        base.setCostPrice(MathTools.parseDouble(costpriceList[i]));
                        base.setMidRevenue(MathTools.parseDouble(midrevenueList[i]));
                        
                        // 销售价格动态获取的
                        base.setValue(base.getAmount() * base.getPrice());

                        total += base.getValue();
                        
                        base.setLocationId(OutImportConstant.ZJRC_DEPOT);
                        base.setDepotpartId(OutImportConstant.ZJRC_DEPOTPART);

                        baseList.add(base);

                        // 增加单个产品到base表
                        zjrcBaseDAO.saveEntityBean(base);

                        addSub = true;
                    }

                    // 重新计算价格
                    outBean.setTotal(total);
                    
                    outBean.setBaseList(baseList);

                    // 保存入库单
                    zjrcOutDAO.saveEntityBean(outBean);

                    if ( !addSub)
                    {
                        throw new RuntimeException("没有产品数量");
                    }

                    return Boolean.TRUE;
                }
            });
        }
        catch (TransactionException e)
        {
            _logger.error("增加库单错误：", e);
            throw new MYException("数据库内部错误");
        }
        catch (DataAccessException e)
        {
            _logger.error("增加库单错误：", e);
            throw new MYException(e.getCause().toString());
        }
        catch (Exception e)
        {
            _logger.error("增加库单错误：", e);
            throw new MYException("系统错误，请联系管理员:" + e);
        }

        _logger.info(user.getStafferName() + "/" + user.getName() + "/ADD:" + outBean);

        return fullId;
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
	public int submit(String fullId, User user) throws MYException
	{
		JudgeTools.judgeParameterIsNull(fullId);
		
		ZJRCOutBean out = zjrcOutDAO.find(fullId);
		
		if (null == out)
		{
			throw new MYException("数据错误");
		}
		
		out.setStatus(OutImportConstant.STATUS_SUBMIT);
		
		zjrcOutDAO.updateEntityBean(out);
		
		return 0;
	}

    /**
     * 
     */
	@Transactional(rollbackFor = MYException.class)
	public boolean delZJRCOut(User user, String fullId) throws MYException
	{
		JudgeTools.judgeParameterIsNull(fullId);
		
		checkCanDelete(fullId);
		
		zjrcOutDAO.deleteEntityBean(fullId);
		
		zjrcBaseDAO.deleteEntityBeansByFK(fullId);
		
		return true;
	}
	
	/**
	 * 
	 * 〈功能详细描述〉
	 *
	 * @param fullId
	 * @throws MYException
	 * @see [相关类/方法](可选)
	 * @since [产品/模块版本](可选)
	 */
	private void checkCanDelete(String fullId) throws MYException {
		ZJRCOutBean zout = zjrcOutDAO.find(fullId);
		
		if (null != zout) {
			if (zout.getStatus() == OutImportConstant.STATUS_SAVE
					|| zout.getStatus() == OutImportConstant.STATUS_SUBMIT) {
				return;
			} else {
				// 检查生成的OA订单是不是全为驳回态
				List<OutBean> outList = outDAO.queryEntityBeansByFK(fullId);
				
				for (OutBean each : outList) {
					if (each.getStatus() != OutConstant.STATUS_REJECT) {
						throw new MYException("紫金订单对应OA订单不全为驳回态，不能删除.");
					}
				}
			}
		}
	}
	
	/**
	 * 紫金订单 生成 OA 订单
	 * {@inheritDoc}
	 */
	public void createZJRC2OAOut()
	{
        triggerLog.info("createZJRC2OAOut 开始...");
        
        long statsStar = System.currentTimeMillis();
        
        TransactionTemplate tran = new TransactionTemplate(transactionManager);
        
        try
        {
            tran.execute(new TransactionCallback()
            {
                public Object doInTransaction(TransactionStatus arg0)
                {            
                    try
					{
						processZJRC2OA();
					}
					catch (MYException e)
					{
						throw new RuntimeException(e);
					}
                    
                    return Boolean.TRUE;
                }
            });
        }
        catch (Exception e)
        {
            triggerLog.error(e, e);
        }
      
        triggerLog.info("createZJRC2OAOut 结束... ,共耗时："+ (System.currentTimeMillis() - statsStar));
        
        return;
    }

	@Transactional(rollbackFor = MYException.class)
	public boolean createOneZJRC(String fullId) throws MYException
	{
		JudgeTools.judgeParameterIsNull(fullId);
		
		final ZJRCOutBean zjrc = zjrcOutDAO.find(fullId);
		
		final List<ZJRCBaseBean> blist = zjrcBaseDAO.queryEntityBeansByFK(zjrc.getFullId());
		
		 // 增加管理员操作在数据库事务中完成
        TransactionTemplate tran = new TransactionTemplate(transactionManager);
        try
        {
            tran.execute(new TransactionCallback()
            {
                public Object doInTransaction(TransactionStatus arg0)
                {
                	try {
	                	createOAOut(zjrc, blist);
	            		
	            		zjrcOutDAO.updateStatus(zjrc.getFullId(), OutImportConstant.STATUS_PREPARE);
	            		
	            		// 增加一条配件日志: 配送中
	            		saveZJRCDist(zjrc.getFullId(), OutImportConstant.STATUS_PREPARE);
	            		
	            		return Boolean.TRUE;
                	} catch (MYException e) {
                		throw new RuntimeException(e.getErrorContent());
                	}
                }
            });
        }
        catch (TransactionException e)
        {
            _logger.error("增加库单错误：", e);
            throw new MYException("数据库内部错误");
        }
        catch (DataAccessException e)
        {
            _logger.error("增加库单错误：", e);
            throw new MYException(e.getCause().toString());
        }
        catch (Exception e)
        {
            _logger.error("增加库单错误：", e);
            throw new MYException("系统错误，请联系管理员:" + e);
        }

		return true;
	}
	
	private void processZJRC2OA() throws MYException
	{
		List<ZJRCOutBean> list = zjrcOutDAO.queryNotCreateOA();
		
		for (ZJRCOutBean each : list) {
			List<ZJRCBaseBean> blist = zjrcBaseDAO.queryEntityBeansByFK(each.getFullId());
			
			createOAOut(each, blist);
			
			zjrcOutDAO.updateStatus(each.getFullId(), OutImportConstant.STATUS_PREPARE);
			
			// 增加一条配件日志: 配送中
			saveZJRCDist(each.getFullId(), OutImportConstant.STATUS_PREPARE);
		}
	}

	private void saveZJRCDist(String zjrcOutId, int stauts)
	{
		ZJRCDistributionBean dist = new ZJRCDistributionBean();
		
		dist.setZjrcOutId(zjrcOutId);
		dist.setStatus(stauts);
		dist.setLogTime(TimeTools.now());
		
		zjrcDistributionDAO.saveEntityBean(dist);
	}
	
	private void createOAOut(ZJRCOutBean zjrcOut, List<ZJRCBaseBean> blist) throws MYException
	{
		for (ZJRCBaseBean each : blist) {
			
			String newOutId;
			
			OutBean newOutBean = new OutBean();
	    	
	    	String id = getAll(commonDAO.getSquence());

	        String time = TimeTools.getStringByFormat(new Date(), "yyMMddHHmm");

	        String flag = "SO";
	        
	        newOutId = flag + time + id;

	        newOutBean.setId(getOutId(id));
	    	
	    	newOutBean.setFullId(newOutId);
	    	
	    	newOutBean.setRefOutFullId(zjrcOut.getFullId());
	    	
	    	newOutBean.setOutTime(TimeTools.changeFormat(zjrcOut.getLogTime(), TimeTools.LONG_FORMAT, TimeTools.SHORT_FORMAT));
	    	
	    	newOutBean.setCustomerId(zjrcOut.getCustomerId());
	    	
	    	newOutBean.setCustomerName(zjrcOut.getCustomerName());
	    	
	    	newOutBean.setLocationId("999");
	    	
	    	newOutBean.setLocation(each.getLocationId());
	    	
	    	newOutBean.setType(OutConstant.OUT_TYPE_OUTBILL);
	    	
	    	newOutBean.setOutType(OutConstant.OUTTYPE_OUT_COMMON);
	    	
	    	newOutBean.setReserve3(OutImportConstant.CITIC_PAYTYPE);
	    	
	    	// 紫金农商
	    	newOutBean.setFlowId("ZJRC");
	    	
	    	newOutBean.setReday(30);
	    	
			long add = newOutBean.getReday()  * 24 * 3600 * 1000L;
			
			String redate = TimeTools.getStringByFormat(new Date(new Date().getTime() + add), "yyyy-MM-dd");
			
	    	newOutBean.setRedate(redate);
	    	
	    	newOutBean.setArriveDate(TimeTools.now_short(2));
	    	
	    	// 结算中心审批时间
	    	newOutBean.setManagerTime("");
	    	
	    	//newOutBean.setDutyId(OutImportConstant.CITIC_DUTY);
	    	
	    	newOutBean.setInvoiceId(OutImportConstant.CITIC_INVOICEID);
	    	
	    	//
	    	StafferVSCustomerVO vsCustVO = stafferVSCustomerDAO.findVOByUnique(zjrcOut.getCustomerId());
	    	
	    	if (vsCustVO == null) {
	    		throw new MYException("客户 " + zjrcOut.getCustomerName() + " 没有挂靠关系");
	    	}
        	
    		newOutBean.setStafferId(vsCustVO.getStafferId());
        	
        	newOutBean.setStafferName(vsCustVO.getStafferName());
	    	
	    	setInvoiceId(newOutBean);
	    	
	    	newOutBean.setStatus(OutConstant.STATUS_SUBMIT);
	    	
	    	newOutBean.setTotal(each.getValue());
	    	
			newOutBean.setPay(0);
			
	    	newOutBean.setArriveDate("");
	    	
	    	newOutBean.setChangeTime("");
	    	
	    	newOutBean.setOperator(zjrcOut.getStafferId());
	    	
	    	newOutBean.setOperatorName(zjrcOut.getStafferName());
	    	
	    	newOutBean.setDescription(new StringBuilder().append("紫金农商订单转OA订单 ").append(zjrcOut.getFullId()).append(".发票抬头：")
	    	    	.append(zjrcOut.getInvoiceHead()).append("，发票明细:").append(zjrcOut.getInvoiceDetail())
	    	    	.append(",发票备注:").append(zjrcOut.getInvoiceDescription()).toString());
	    	
	    	final StafferBean stafferBean = stafferDAO.find(newOutBean.getStafferId());
	    	
	    	List<BaseBean> baseList = new ArrayList<BaseBean>();
	    	
			String dutyId = "";
			int mtype = 0;

			BaseBean base = new BaseBean();
			
			base.setId(commonDAO.getSquenceString());
			
			base.setOutId(newOutId);
			
			base.setProductId(each.getProductId());
			
			base.setProductName(each.getProductName());
			
			base.setUnit("套");
			
			base.setAmount(each.getAmount());
			
			base.setPrice(each.getPrice());
			
			base.setValue(each.getValue());
			
			base.setLocationId(each.getLocationId());
			
			base.setDepotpartId(each.getDepotpartId());
			
			base.setDepotpartName(OutImportConstant.ZJRC_DEPOTPARTNAME);
			
			base.setOwner("0");
			
			base.setOwnerName("公共");
			
			base.setRefId(each.getId());
			
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
			}
			else
			{
				dutyId = PublicConstant.MANAGER2_DUTY_ID;
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
                throw new MYException(base.getProductName() + " 业务员结算价不能为0");
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
        	
			newOutBean.setDutyId(dutyId);
			
			newOutBean.setMtype(mtype);
			
			outDAO.saveEntityBean(newOutBean);
	    	
	    	baseDAO.saveAllEntityBeans(baseList);
	    	
	    	zjrcBaseDAO.updateOAno(each.getId(), newOutId);
			
	    	newOutBean.setBaseList(baseList);
	    	
	    	// 配送地址
	    	DistributionBean distBean = new DistributionBean();
	        
	        distBean.setId(commonDAO.getSquenceString20("PS"));
	        
	        distBean.setOutId(newOutId);
	        
	        distBean.setProvinceId(zjrcOut.getProvinceId());
	        distBean.setCityId(zjrcOut.getCityId());
	        distBean.setAddress(zjrcOut.getAddress());
	        
	        distBean.setMobile(zjrcOut.getHandPhone());
	        
	        distBean.setReceiver(zjrcOut.getReceiver());
	        
	        distBean.setExpressPay(-1);
	        
	        distBean.setTransportPay(-1);
	        
	        distBean.setTransport1(-1);
	        
	        distBean.setTransport2(-1);
	        
	        distBean.setShipping(2);
	        
	        distributionDAO.saveEntityBean(distBean);
	        
	    	// 记录退货审批日志 操作人系统，自动审批 
	    	FlowLogBean log = new FlowLogBean();

	        log.setActor("系统");

	        log.setDescription("紫金农商订单系统自动审批");
	        log.setFullId(newOutBean.getFullId());
	        log.setOprMode(PublicConstant.OPRMODE_PASS);
	        log.setLogTime(TimeTools.now());

	        log.setPreStatus(OutConstant.STATUS_SAVE);

	        log.setAfterStatus(newOutBean.getStatus());

	        flowLogDAO.saveEntityBean(log);
	        
	        // 每个行项目数据写入t_center_out_import。 目的用于中收数据统计
	        OutImportBean outimport = new OutImportBean();
	        
	        outimport.setBatchId(newOutBean.getRefOutFullId());
	        outimport.setCustomerId(newOutBean.getCustomerId());
	        outimport.setComunicatonBranchName(newOutBean.getCustomerName());
	        outimport.setProductId(base.getProductId());
	        outimport.setProductName(base.getProductName());
	        outimport.setAmount(base.getAmount());
	        outimport.setPrice(base.getPrice());
	        outimport.setValue(base.getValue());
	        outimport.setMidValue(each.getMidRevenue());
	        outimport.setOANo(newOutBean.getFullId());
	        outimport.setLogTime(zjrcOut.getLogTime());
	        outimport.setDepotId(each.getLocationId());
	        outimport.setDepotpartId(each.getDepotpartId());
	        outimport.setStafferId(newOutBean.getStafferId());
	        
	        outImportDAO.saveEntityBean(outimport);
		}
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
	
	@Override
	public void modifyStatusWithoutTrans(String fullId, String refBaseId)
	{
    	zjrcBaseDAO.updatePstatus(refBaseId, 1);
    	
    	// 存的是zjrc 单号
    	List<ZJRCBaseBean> zbList = zjrcBaseDAO.queryEntityBeansByFK(fullId);
    	
    	int count = 0;
    	
    	for (ZJRCBaseBean each : zbList) {
    		count += each.getPstatus();
    	}
    	
    	if (count == zbList.size()) {
    		zjrcOutDAO.updateStatus(fullId, OutImportConstant.STATUS_SHIPPING);
    		
    		saveZJRCDist(fullId, OutImportConstant.STATUS_SHIPPING);
    	}
    }
	
	@Transactional(rollbackFor = MYException.class)
	public boolean modifyStatus(String fullId, int status) throws MYException
	{
		JudgeTools.judgeParameterIsNull(fullId);
		
		zjrcOutDAO.updateStatus(fullId, status);
		
		saveZJRCDist(fullId, status);
		
		return true;
	}
	
	/**
	 * @return the commonDAO
	 */
	public CommonDAO getCommonDAO()
	{
		return commonDAO;
	}

	/**
	 * @param commonDAO the commonDAO to set
	 */
	public void setCommonDAO(CommonDAO commonDAO)
	{
		this.commonDAO = commonDAO;
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
	 * @return the userDAO
	 */
	public UserDAO getUserDAO()
	{
		return userDAO;
	}

	/**
	 * @param userDAO the userDAO to set
	 */
	public void setUserDAO(UserDAO userDAO)
	{
		this.userDAO = userDAO;
	}

	/**
	 * @return the stafferDAO
	 */
	public StafferDAO getStafferDAO()
	{
		return stafferDAO;
	}

	/**
	 * @param stafferDAO the stafferDAO to set
	 */
	public void setStafferDAO(StafferDAO stafferDAO)
	{
		this.stafferDAO = stafferDAO;
	}

	/**
	 * @return the transactionManager
	 */
	public PlatformTransactionManager getTransactionManager()
	{
		return transactionManager;
	}

	/**
	 * @param transactionManager the transactionManager to set
	 */
	public void setTransactionManager(PlatformTransactionManager transactionManager)
	{
		this.transactionManager = transactionManager;
	}

	/**
	 * @return the stafferVSCustomerDAO
	 */
	public StafferVSCustomerDAO getStafferVSCustomerDAO()
	{
		return stafferVSCustomerDAO;
	}

	/**
	 * @param stafferVSCustomerDAO the stafferVSCustomerDAO to set
	 */
	public void setStafferVSCustomerDAO(StafferVSCustomerDAO stafferVSCustomerDAO)
	{
		this.stafferVSCustomerDAO = stafferVSCustomerDAO;
	}

	/**
	 * @return the priceConfigDAO
	 */
	public PriceConfigDAO getPriceConfigDAO()
	{
		return priceConfigDAO;
	}

	/**
	 * @param priceConfigDAO the priceConfigDAO to set
	 */
	public void setPriceConfigDAO(PriceConfigDAO priceConfigDAO)
	{
		this.priceConfigDAO = priceConfigDAO;
	}

	/**
	 * @return the priceConfigManager
	 */
	public PriceConfigManager getPriceConfigManager()
	{
		return priceConfigManager;
	}

	/**
	 * @param priceConfigManager the priceConfigManager to set
	 */
	public void setPriceConfigManager(PriceConfigManager priceConfigManager)
	{
		this.priceConfigManager = priceConfigManager;
	}

	/**
	 * @return the outDAO
	 */
	public OutDAO getOutDAO()
	{
		return outDAO;
	}

	/**
	 * @param outDAO the outDAO to set
	 */
	public void setOutDAO(OutDAO outDAO)
	{
		this.outDAO = outDAO;
	}

	/**
	 * @return the baseDAO
	 */
	public BaseDAO getBaseDAO()
	{
		return baseDAO;
	}

	/**
	 * @param baseDAO the baseDAO to set
	 */
	public void setBaseDAO(BaseDAO baseDAO)
	{
		this.baseDAO = baseDAO;
	}

	/**
	 * @return the distributionDAO
	 */
	public DistributionDAO getDistributionDAO()
	{
		return distributionDAO;
	}

	/**
	 * @param distributionDAO the distributionDAO to set
	 */
	public void setDistributionDAO(DistributionDAO distributionDAO)
	{
		this.distributionDAO = distributionDAO;
	}

	/**
	 * @return the sailConfigManager
	 */
	public SailConfigManager getSailConfigManager()
	{
		return sailConfigManager;
	}

	/**
	 * @param sailConfigManager the sailConfigManager to set
	 */
	public void setSailConfigManager(SailConfigManager sailConfigManager)
	{
		this.sailConfigManager = sailConfigManager;
	}

	/**
	 * @return the flowLogDAO
	 */
	public FlowLogDAO getFlowLogDAO()
	{
		return flowLogDAO;
	}

	/**
	 * @param flowLogDAO the flowLogDAO to set
	 */
	public void setFlowLogDAO(FlowLogDAO flowLogDAO)
	{
		this.flowLogDAO = flowLogDAO;
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

	/**
	 * @return the customerMainDAO
	 */
	public CustomerMainDAO getCustomerMainDAO()
	{
		return customerMainDAO;
	}

	/**
	 * @param customerMainDAO the customerMainDAO to set
	 */
	public void setCustomerMainDAO(CustomerMainDAO customerMainDAO)
	{
		this.customerMainDAO = customerMainDAO;
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
}
