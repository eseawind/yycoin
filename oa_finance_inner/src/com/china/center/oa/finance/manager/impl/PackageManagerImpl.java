package com.china.center.oa.finance.manager.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

import com.china.center.common.MYException;
import com.china.center.jdbc.annosql.constant.AnoConstant;
import com.china.center.jdbc.util.ConditionParse;
import com.china.center.oa.finance.bean.InsVSInvoiceNumBean;
import com.china.center.oa.finance.bean.InvoiceinsBean;
import com.china.center.oa.finance.constant.FinanceConstant;
import com.china.center.oa.finance.dao.InsVSInvoiceNumDAO;
import com.china.center.oa.finance.dao.InvoiceinsDAO;
import com.china.center.oa.finance.dao.InvoiceinsItemDAO;
import com.china.center.oa.finance.manager.PackageManager;
import com.china.center.oa.finance.vo.InvoiceinsItemVO;
import com.china.center.oa.finance.vo.InvoiceinsVO;
import com.china.center.oa.product.bean.DepotBean;
import com.china.center.oa.product.constant.DepotConstant;
import com.china.center.oa.product.dao.DepotDAO;
import com.china.center.oa.publics.dao.CommonDAO;
import com.china.center.oa.publics.dao.StafferDAO;
import com.china.center.oa.publics.vo.StafferVO;
import com.china.center.oa.sail.bean.BaseBean;
import com.china.center.oa.sail.bean.OutImportBean;
import com.china.center.oa.sail.bean.PackageBean;
import com.china.center.oa.sail.bean.PackageItemBean;
import com.china.center.oa.sail.bean.PackageVSCustomerBean;
import com.china.center.oa.sail.bean.PreConsignBean;
import com.china.center.oa.sail.constanst.OutConstant;
import com.china.center.oa.sail.dao.BaseDAO;
import com.china.center.oa.sail.dao.DistributionDAO;
import com.china.center.oa.sail.dao.OutDAO;
import com.china.center.oa.sail.dao.OutImportDAO;
import com.china.center.oa.sail.dao.PackageDAO;
import com.china.center.oa.sail.dao.PackageItemDAO;
import com.china.center.oa.sail.dao.PackageVSCustomerDAO;
import com.china.center.oa.sail.dao.PreConsignDAO;
import com.china.center.oa.sail.vo.DistributionVO;
import com.china.center.oa.sail.vo.OutVO;
import com.china.center.oa.sail.vo.PackageVO;
import com.china.center.tools.ListTools;
import com.china.center.tools.StringTools;
import com.china.center.tools.TimeTools;

/**
 * 〈一句话功能简述〉<br> 
 * 〈功能详细描述〉
 *
 * @author smart
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public class PackageManagerImpl implements PackageManager {
	private final Log triggerLog = LogFactory.getLog("trigger");
	
	private PreConsignDAO preConsignDAO = null;
	
	private PackageDAO packageDAO = null;
	
	private PackageItemDAO packageItemDAO = null;
	
	private OutDAO outDAO = null;
	
	private BaseDAO baseDAO = null;
	
	private DistributionDAO distributionDAO = null;
	
	private CommonDAO commonDAO = null;
	
	private DepotDAO depotDAO = null;
	
	private PackageVSCustomerDAO packageVSCustomerDAO = null;
	
	private InvoiceinsDAO invoiceinsDAO = null;
	
	private InsVSInvoiceNumDAO insVSInvoiceNumDAO = null;
	
	private StafferDAO stafferDAO = null;
	
	private InvoiceinsItemDAO invoiceinsItemDAO = null;
	
	private OutImportDAO outImportDAO = null;
	
	private PlatformTransactionManager transactionManager = null;

	public PackageManagerImpl()
	{
	}
	
	/**
	 * 生成发货单 (根据收货打包)
	 */
	public void createPackage()
	{
        triggerLog.info("createPackage 开始统计...");
        
        long statsStar = System.currentTimeMillis();
        
        TransactionTemplate tran = new TransactionTemplate(transactionManager);
        
        try
        {
            tran.execute(new TransactionCallback()
            {
                public Object doInTransaction(TransactionStatus arg0)
                {
                    try{
                    	processOut();
                    }catch(MYException e)
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
      
        triggerLog.info("createPackage 统计结束... ,共耗时："+ (System.currentTimeMillis() - statsStar));
        
        return;
    }
	
	/**
	 * 生成发货单核心
	 * @throws MYException
	 */
	private void processOut() throws MYException
	{
		List<PreConsignBean> list = preConsignDAO.listEntityBeans();
		
		for (PreConsignBean each : list) {
			OutVO outBean = outDAO.findVO(each.getOutId());
			
			if (null != outBean) {
				triggerLog.info("======is out======" + each.getOutId());
				createPackage(each, outBean);
			} else {
				InvoiceinsBean insBean = invoiceinsDAO.find(each.getOutId());
				
				if (null != insBean) {
					triggerLog.info("======is invoiceins======" + each.getOutId());
					createInsPackage(each, insBean.getId());
				} else {
					triggerLog.info("======is other, direct delete, handle nothing======");
					preConsignDAO.deleteEntityBean(each.getId());
					
					continue;
				}
			}
		}
	}

	private void createNewPackage(OutVO outBean,
			List<BaseBean> baseList, DistributionVO distVO, String fullAddress, String location)
	{
		String id = commonDAO.getSquenceString20("CK");
		
		int allAmount = 0;
		
		PackageBean packBean = new PackageBean();
		
		packBean.setId(id);
		packBean.setCustomerId(outBean.getCustomerId());
		packBean.setShipping(distVO.getShipping());
		packBean.setTransport1(distVO.getTransport1());
		packBean.setExpressPay(distVO.getExpressPay());
		packBean.setTransport2(distVO.getTransport2());
		packBean.setTransportPay(distVO.getTransportPay());
		packBean.setAddress(fullAddress);
		packBean.setReceiver(distVO.getReceiver());
		packBean.setMobile(distVO.getMobile());
		packBean.setLocationId(location);
		packBean.setCityId(distVO.getCityId());
		
		packBean.setStafferName(outBean.getStafferName());
		packBean.setIndustryName(outBean.getIndustryName());
		packBean.setDepartName(outBean.getIndustryName3());
		
		packBean.setTotal(outBean.getTotal());
		packBean.setStatus(0);
		packBean.setLogTime(TimeTools.now());
		
		List<PackageItemBean> itemList = new ArrayList<PackageItemBean>();
		
		boolean isEmergency = false;
		Map<String, List<BaseBean>> pmap = new HashMap<String, List<BaseBean>>();
		
		for (BaseBean base : baseList)
		{
			PackageItemBean item = new PackageItemBean();
			
			item.setPackageId(id);
			item.setOutId(outBean.getFullId());
			item.setBaseId(base.getId());
			item.setProductId(base.getProductId());
			item.setProductName(base.getProductName());
			item.setAmount(base.getAmount());
			item.setPrice(base.getPrice());
			item.setValue(base.getValue());
			item.setOutTime(outBean.getOutTime());
			item.setDescription(outBean.getDescription());
			item.setCustomerId(outBean.getCustomerId());
			item.setEmergency(outBean.getEmergency());
			
			if (item.getEmergency() == 1) {
				isEmergency = true;
			}
			
			itemList.add(item);
			
			allAmount += item.getAmount();
			
			if (!pmap.containsKey(base.getProductId()))
			{
				List<BaseBean> blist = new ArrayList<BaseBean>();
				
				blist.add(base);
				
				pmap.put(base.getProductId(), blist);
			}else
			{
				List<BaseBean> blist = pmap.get(base.getProductId());
				
				blist.add(base);
			}
		}
		
		packBean.setAmount(allAmount);
		
		if (isEmergency) {
			packBean.setEmergency(OutConstant.OUT_EMERGENCY_YES);
		}
		
		packBean.setProductCount(pmap.values().size());
		
		PackageVSCustomerBean vsBean = new PackageVSCustomerBean();
		
		vsBean.setPackageId(id);
		vsBean.setCustomerId(outBean.getCustomerId());
		vsBean.setCustomerName(outBean.getCustomerName());
		vsBean.setIndexPos(1);
		
		packageDAO.saveEntityBean(packBean);
		
		packageItemDAO.saveAllEntityBeans(itemList);
		
		packageVSCustomerDAO.saveEntityBean(vsBean);
	}
	
	/**
	 * for invoiceins
	 * @param outBean
	 * @param baseList
	 * @param distVO
	 * @param fullAddress
	 * @param location
	 */
	private void createNewInsPackage(InvoiceinsVO ins,
			List<InsVSInvoiceNumBean> numList, DistributionVO distVO, String fullAddress, String location)
	{
		String id = commonDAO.getSquenceString20("CK");
		
		int allAmount = 0;
		
		PackageBean packBean = new PackageBean();
		
		packBean.setId(id);
		packBean.setCustomerId(ins.getCustomerId());
		packBean.setShipping(distVO.getShipping());
		packBean.setTransport1(distVO.getTransport1());
		packBean.setExpressPay(distVO.getExpressPay());
		packBean.setTransport2(distVO.getTransport2());
		packBean.setTransportPay(distVO.getTransportPay());
		packBean.setAddress(fullAddress);
		packBean.setReceiver(distVO.getReceiver());
		packBean.setMobile(distVO.getMobile());
		packBean.setLocationId(location);
		packBean.setCityId(distVO.getCityId());
		
		packBean.setStafferName(ins.getStafferName());
		
		StafferVO staff = stafferDAO.findVO(ins.getStafferId());
		
		if (null != staff) {
			packBean.setIndustryName(staff.getIndustryName());
			packBean.setDepartName(staff.getIndustryName3());
		}
		
		packBean.setTotal(ins.getMoneys());
		packBean.setStatus(0);
		packBean.setLogTime(TimeTools.now());
		
		StringBuilder sb = getPrintTextForIns(ins);
		
		List<PackageItemBean> itemList = new ArrayList<PackageItemBean>();
		
		boolean first = false;
		
		for (InsVSInvoiceNumBean base : numList)
		{
			PackageItemBean item = new PackageItemBean();
			
			item.setPackageId(id);
			item.setOutId(ins.getId());
			item.setBaseId(base.getId());
			item.setProductId(base.getInvoiceNum());
			item.setProductName("发票号：" + base.getInvoiceNum());
			item.setAmount(1);
			item.setPrice(base.getMoneys());
			item.setValue(base.getMoneys());
			item.setOutTime(TimeTools.changeFormat(ins.getLogTime(), TimeTools.LONG_FORMAT, TimeTools.SHORT_FORMAT));
			item.setDescription(ins.getDescription());
			item.setCustomerId(ins.getCustomerId());
			if (!first) {
				item.setPrintText(sb.toString());	
			}
			
			first = true;
			
			itemList.add(item);
			
			allAmount += item.getAmount();
		}
		
		packBean.setAmount(allAmount);
		
		packBean.setProductCount(numList.size());
		
		PackageVSCustomerBean vsBean = new PackageVSCustomerBean();
		
		vsBean.setPackageId(id);
		vsBean.setCustomerId(ins.getCustomerId());
		vsBean.setCustomerName(ins.getCustomerName());
		vsBean.setIndexPos(1);
		
		packageDAO.saveEntityBean(packBean);
		
		packageItemDAO.saveAllEntityBeans(itemList);
		
		packageVSCustomerDAO.saveEntityBean(vsBean);
	}

	/**
	 * 功能描述: <br>
	 * 〈功能详细描述〉
	 *
	 * @param ins
	 * @return
	 * @see [相关类/方法](可选)
	 * @since [产品/模块版本](可选)
	 */
	private StringBuilder getPrintTextForIns(InvoiceinsVO ins) {
		StringBuilder sb = new StringBuilder();
		
		List<InvoiceinsItemVO> insVOList = invoiceinsItemDAO.queryEntityVOsByFK(ins.getId());
		
		Set<String> uniqueSO = new HashSet<String>();
		
//		sb.append(ins.getCustomerName()).append(";商品明细：");
		
		for (InvoiceinsItemVO insVO : insVOList) {
//			sb.append(insVO.getProductName()).append(";");
			
			if (!uniqueSO.contains(insVO.getOutId()) && insVO.getType() == FinanceConstant.INSVSOUT_TYPE_OUT) {
				uniqueSO.add(insVO.getOutId());
			}
		}
		
		sb.append("银行订单号：");
		for (String outid : uniqueSO) {
			List<OutImportBean> outiList = outImportDAO.queryEntityBeansByFK(outid, AnoConstant.FK_FIRST);
			
			if (!ListTools.isEmptyOrNull(outiList))
			{
				if (!StringTools.isNullOrNone(outiList.get(0).getCiticNo())) {
					sb.append(outiList.get(0).getCiticNo()).append(";");
				}
			}
		}
		return sb;
	}

	private void setInnerCondition(DistributionVO distVO, String location, ConditionParse con)
	{
		//con.addCondition("PackageBean.customerId", "=", outBean.getCustomerId());
		con.addCondition("PackageBean.cityId", "=", distVO.getCityId());  // 借用outId 用于存储城市。生成出库单增加 城市 维度
		
		con.addIntCondition("PackageBean.shipping", "=", distVO.getShipping());
		
		con.addIntCondition("PackageBean.transport1", "=", distVO.getTransport1());
		
		con.addIntCondition("PackageBean.expressPay", "=", distVO.getExpressPay());
		
		con.addIntCondition("PackageBean.transport2", "=", distVO.getTransport2());
		
		con.addIntCondition("PackageBean.transportPay", "=", distVO.getTransportPay());
		
		con.addCondition("PackageBean.locationId", "=", location);
		
		con.addCondition("PackageBean.receiver", "=", distVO.getReceiver());
		
		con.addCondition("PackageBean.mobile", "=", distVO.getMobile());
		
		con.addIntCondition("PackageBean.status", "=", 0);
	}

	/**
	 * 
	 */
	public void createPackage(PreConsignBean pre, OutVO out) throws MYException
	{
		String location = "";
		
		// 通过仓库获取 仓库地点
		DepotBean depot = depotDAO.find(out.getLocation());
		
		if (depot != null)
			location = depot.getIndustryId2();
		
		List<BaseBean> baseList = baseDAO.queryEntityBeansByFK(out.getFullId());
		
		List<DistributionVO> distList = distributionDAO.queryEntityVOsByFK(out.getFullId());
		
		if (ListTools.isEmptyOrNull(distList))
		{
			triggerLog.info("======createPackage== (distList is null or empty)====" + out.getFullId());
			preConsignDAO.deleteEntityBean(pre.getId());
			
			return;
		}
		
		DistributionVO distVO = distList.get(0);
		
		// 如果是空发,则不处理
		if (distVO.getShipping() == OutConstant.OUT_SHIPPING_NOTSHIPPING)
		{
			triggerLog.info("======createPackage== (shipping is OUT_SHIPPING_NOTSHIPPING)====" + out.getFullId());
			preConsignDAO.deleteEntityBean(pre.getId());
			
			return;
		}
		
		// 地址不全,不发
		if (distVO.getAddress().trim().equals("0") && distVO.getReceiver().trim().equals("0") && distVO.getMobile().trim().equals("0"))
		{
			return;
		}
		
		String fullAddress = distVO.getProvinceName()+distVO.getCityName()+distVO.getAddress();
		
		// 此客户是否存在同一个发货包裹,且未拣配
		ConditionParse con = new ConditionParse();
		
		con.addWhereStr();
		
		setInnerCondition(distVO, location, con);
		
		List<PackageVO> packageList = packageDAO.queryVOsByCondition(con);
		
//		if (packageList.size() > 1){
//			throw new MYException("数据异常,生成发货单出错.");
//		}
		
		if (ListTools.isEmptyOrNull(packageList))
		{
			createNewPackage(out, baseList, distVO, fullAddress, location);
			
		}else{
			String id = packageList.get(0).getId();
			
			PackageBean packBean = packageDAO.find(id);
			
			// 不存在或已不是初始状态(可能已被拣配)
			if (null == packBean || packBean.getStatus() != 0)
			{
				createNewPackage(out, baseList, distVO, fullAddress, location);
			}else
			{
				List<PackageItemBean> itemList = new ArrayList<PackageItemBean>();
				
				int allAmount = 0;
				double total = 0;
				
				Map<String, List<BaseBean>> pmap = new HashMap<String, List<BaseBean>>();
				boolean isEmergency = false;
				for (BaseBean base : baseList)
				{
					PackageItemBean item = new PackageItemBean();
					
					item.setPackageId(id);
					item.setOutId(out.getFullId());
					item.setBaseId(base.getId());
					item.setProductId(base.getProductId());
					item.setProductName(base.getProductName());
					item.setAmount(base.getAmount());
					item.setPrice(base.getPrice());
					item.setValue(base.getValue());
					item.setOutTime(out.getOutTime());
					item.setDescription(out.getDescription());
					item.setCustomerId(out.getCustomerId());
					item.setEmergency(out.getEmergency());
					
					if (item.getEmergency() == 1) {
						isEmergency = true;
					}
					
					itemList.add(item);
					
					allAmount += item.getAmount();
					total += base.getValue();
					
					if (!pmap.containsKey(base.getProductId()))
					{
						List<BaseBean> blist = new ArrayList<BaseBean>();
						
						blist.add(base);
						
						pmap.put(base.getProductId(), blist);
					}else
					{
						List<BaseBean> blist = pmap.get(base.getProductId());
						
						blist.add(base);
					}
				}
				
				packBean.setAmount(packBean.getAmount() + allAmount);
				packBean.setTotal(packBean.getTotal() + total);
				packBean.setProductCount(packBean.getProductCount() + pmap.values().size());
				
				if (isEmergency) {
					packBean.setEmergency(OutConstant.OUT_EMERGENCY_YES);
				}
				
				packageDAO.updateEntityBean(packBean);
				
				packageItemDAO.saveAllEntityBeans(itemList);
				
				// 包与客户关系
				PackageVSCustomerBean vsBean = packageVSCustomerDAO.findByUnique(id, out.getCustomerId());
				
				if (null == vsBean)
				{
					int count = packageVSCustomerDAO.countByCondition("where packageId = ?", id);
					
					PackageVSCustomerBean newvsBean = new PackageVSCustomerBean();
					
					newvsBean.setPackageId(id);
					newvsBean.setCustomerId(out.getCustomerId());
					newvsBean.setCustomerName(out.getCustomerName());
					newvsBean.setIndexPos(count + 1);
					
					packageVSCustomerDAO.saveEntityBean(newvsBean);
				}
			}
		}
		
		preConsignDAO.deleteEntityBean(pre.getId());
	}
	
	/**
	 * 
	 */
	public void createInsPackage(PreConsignBean pre, String insId) throws MYException
	{
		String location = "";
		
		// 通过仓库获取 仓库地点
		DepotBean depot = depotDAO.find(DepotConstant.CENTER_DEPOT_ID);
		
		if (depot != null)
			location = depot.getIndustryId2();
		
		InvoiceinsVO ins = invoiceinsDAO.findVO(insId);
		
		if (null == ins) {
			preConsignDAO.deleteEntityBean(pre.getId());
			
			return;
		}
		
		List<InsVSInvoiceNumBean> numList = insVSInvoiceNumDAO.queryEntityBeansByFK(insId);
		
		if (ListTools.isEmptyOrNull(numList)) {
			triggerLog.info("======createInsPackage== (numList is null or empty)====" + insId);
			return;
		}
		
		List<DistributionVO> distList = distributionDAO.queryEntityVOsByFK(ins.getId());
		
		if (ListTools.isEmptyOrNull(distList))
		{
			triggerLog.info("======createInsPackage==(distList is null or empty)====" + insId);
			
			preConsignDAO.deleteEntityBean(pre.getId());
			
			return;
		}
		
		DistributionVO distVO = distList.get(0);
		
		// 如果是空发,则不处理
		if (distVO.getShipping() == OutConstant.OUT_SHIPPING_NOTSHIPPING) {
			triggerLog.info("======createInsPackage==(shipping is OUT_SHIPPING_NOTSHIPPING)====" + insId);
			preConsignDAO.deleteEntityBean(pre.getId());
			
			return;
		}
		
		// 历史数据无配送信息，pass
		if (StringTools.isNullOrNone(distVO.getAddress())
				&& StringTools.isNullOrNone(distVO.getReceiver())
				&& StringTools.isNullOrNone(distVO.getMobile())) {
			triggerLog.info("======createInsPackage==(distList detail is null or empty)====" + insId);
			preConsignDAO.deleteEntityBean(pre.getId());
			
			return;
		}
		
		// 地址不全,不发
		if (distVO.getAddress().trim().equals("0") && distVO.getReceiver().trim().equals("0") && distVO.getMobile().trim().equals("0"))
		{
			return;
		}
		
		String fullAddress = distVO.getProvinceName()+distVO.getCityName()+distVO.getAddress();
		
		// 此客户是否存在同一个发货包裹,且未拣配
		ConditionParse con = new ConditionParse();
		
		con.addWhereStr();
		
		setInnerCondition(distVO, location, con);
		
		List<PackageVO> packageList = packageDAO.queryVOsByCondition(con);
		
//		if (packageList.size() > 1){
//			throw new MYException("数据异常,生成发货单出错.");
//		}
		
		if (ListTools.isEmptyOrNull(packageList))
		{
			createNewInsPackage(ins, numList, distVO, fullAddress, location);
			
		}else{
			String id = packageList.get(0).getId();
			
			PackageBean packBean = packageDAO.find(id);
			
			// 不存在或已不是初始状态(可能已被拣配)
			if (null == packBean || packBean.getStatus() != 0)
			{
				createNewInsPackage(ins, numList, distVO, fullAddress, location);
			}else
			{
				List<PackageItemBean> itemList = new ArrayList<PackageItemBean>();
				
				int allAmount = 0;
				double total = 0;
				
				StringBuilder sb = getPrintTextForIns(ins);
				
				boolean first = false;
				
				for (InsVSInvoiceNumBean base : numList)
				{
					PackageItemBean item = new PackageItemBean();
					
					item.setPackageId(id);
					item.setOutId(ins.getId());
					item.setBaseId(base.getId());
					item.setProductId(base.getInvoiceNum());
					item.setProductName("发票号：" + base.getInvoiceNum());
					item.setAmount(1);
					item.setPrice(base.getMoneys());
					item.setValue(base.getMoneys());
					item.setOutTime(TimeTools.changeFormat(ins.getLogTime(), TimeTools.LONG_FORMAT, TimeTools.SHORT_FORMAT));
					item.setDescription(ins.getDescription());
					item.setCustomerId(ins.getCustomerId());
					if (!first) {
						item.setPrintText(sb.toString());	
					}
					
					first = true;
					
					itemList.add(item);
					
					allAmount += item.getAmount();
					total += base.getMoneys();
				}
				
				packBean.setAmount(packBean.getAmount() + allAmount);
				packBean.setTotal(packBean.getTotal() + total);
				packBean.setProductCount(packBean.getProductCount() + numList.size());
				
				packageDAO.updateEntityBean(packBean);
				
				packageItemDAO.saveAllEntityBeans(itemList);
				
				// 包与客户关系
				PackageVSCustomerBean vsBean = packageVSCustomerDAO.findByUnique(id, ins.getCustomerId());
				
				if (null == vsBean)
				{
					int count = packageVSCustomerDAO.countByCondition("where packageId = ?", id);
					
					PackageVSCustomerBean newvsBean = new PackageVSCustomerBean();
					
					newvsBean.setPackageId(id);
					newvsBean.setCustomerId(ins.getCustomerId());
					newvsBean.setCustomerName(ins.getCustomerName());
					newvsBean.setIndexPos(count + 1);
					
					packageVSCustomerDAO.saveEntityBean(newvsBean);
				}
			}
		}
		
		preConsignDAO.deleteEntityBean(pre.getId());
	}
	
	/**
	 * @return the preConsignDAO
	 */
	public PreConsignDAO getPreConsignDAO()
	{
		return preConsignDAO;
	}

	/**
	 * @param preConsignDAO the preConsignDAO to set
	 */
	public void setPreConsignDAO(PreConsignDAO preConsignDAO)
	{
		this.preConsignDAO = preConsignDAO;
	}

	/**
	 * @return the packageDAO
	 */
	public PackageDAO getPackageDAO()
	{
		return packageDAO;
	}

	/**
	 * @param packageDAO the packageDAO to set
	 */
	public void setPackageDAO(PackageDAO packageDAO)
	{
		this.packageDAO = packageDAO;
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

	public InvoiceinsDAO getInvoiceinsDAO() {
		return invoiceinsDAO;
	}

	public void setInvoiceinsDAO(InvoiceinsDAO invoiceinsDAO) {
		this.invoiceinsDAO = invoiceinsDAO;
	}

	public InsVSInvoiceNumDAO getInsVSInvoiceNumDAO() {
		return insVSInvoiceNumDAO;
	}

	public void setInsVSInvoiceNumDAO(InsVSInvoiceNumDAO insVSInvoiceNumDAO) {
		this.insVSInvoiceNumDAO = insVSInvoiceNumDAO;
	}

	public StafferDAO getStafferDAO() {
		return stafferDAO;
	}

	public void setStafferDAO(StafferDAO stafferDAO) {
		this.stafferDAO = stafferDAO;
	}

	/**
	 * @return the invoiceinsItemDAO
	 */
	public InvoiceinsItemDAO getInvoiceinsItemDAO() {
		return invoiceinsItemDAO;
	}

	/**
	 * @param invoiceinsItemDAO the invoiceinsItemDAO to set
	 */
	public void setInvoiceinsItemDAO(InvoiceinsItemDAO invoiceinsItemDAO) {
		this.invoiceinsItemDAO = invoiceinsItemDAO;
	}

	/**
	 * @return the outImportDAO
	 */
	public OutImportDAO getOutImportDAO() {
		return outImportDAO;
	}

	/**
	 * @param outImportDAO the outImportDAO to set
	 */
	public void setOutImportDAO(OutImportDAO outImportDAO) {
		this.outImportDAO = outImportDAO;
	}
}
