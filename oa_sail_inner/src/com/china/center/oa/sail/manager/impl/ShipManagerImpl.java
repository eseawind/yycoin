package com.china.center.oa.sail.manager.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.transaction.annotation.Transactional;

import com.center.china.osgi.publics.User;
import com.china.center.common.MYException;
import com.china.center.jdbc.util.ConditionParse;
import com.china.center.oa.product.bean.DepotBean;
import com.china.center.oa.product.dao.DepotDAO;
import com.china.center.oa.publics.dao.CommonDAO;
import com.china.center.oa.sail.bean.BaseBean;
import com.china.center.oa.sail.bean.OutBean;
import com.china.center.oa.sail.bean.PackageBean;
import com.china.center.oa.sail.bean.PackageItemBean;
import com.china.center.oa.sail.bean.PackageVSCustomerBean;
import com.china.center.oa.sail.bean.PreConsignBean;
import com.china.center.oa.sail.constanst.OutConstant;
import com.china.center.oa.sail.constanst.ShipConstant;
import com.china.center.oa.sail.dao.BaseDAO;
import com.china.center.oa.sail.dao.DistributionDAO;
import com.china.center.oa.sail.dao.OutDAO;
import com.china.center.oa.sail.dao.PackageDAO;
import com.china.center.oa.sail.dao.PackageItemDAO;
import com.china.center.oa.sail.dao.PackageVSCustomerDAO;
import com.china.center.oa.sail.dao.PreConsignDAO;
import com.china.center.oa.sail.manager.ShipManager;
import com.china.center.oa.sail.vo.DistributionVO;
import com.china.center.oa.sail.vo.OutVO;
import com.china.center.oa.sail.vo.PackageVO;
import com.china.center.tools.JudgeTools;
import com.china.center.tools.ListTools;
import com.china.center.tools.TimeTools;

public class ShipManagerImpl implements ShipManager
{
	private final Log operationLog = LogFactory.getLog("opr");
	
	private PreConsignDAO preConsignDAO = null;
	
	private PackageDAO packageDAO = null;
	
	private PackageItemDAO packageItemDAO = null;
	
	private OutDAO outDAO = null;
	
	private BaseDAO baseDAO = null;
	
	private DistributionDAO distributionDAO = null;
	
	private CommonDAO commonDAO = null;
	
	private DepotDAO depotDAO = null;
	
	private PackageVSCustomerDAO packageVSCustomerDAO = null;
	
	public ShipManagerImpl()
	{
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
		
		Map<String, List<BaseBean>> pmap = new HashMap<String, List<BaseBean>>();
		
		boolean isEmergency = false;
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
			preConsignDAO.deleteEntityBean(pre.getId());
			
			return;
		}
		
		DistributionVO distVO = distList.get(0);
		
		// 如果是空发,则不处理
		if (distVO.getShipping() == OutConstant.OUT_SHIPPING_NOTSHIPPING)
		{
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
	 * 拣配包
	 */
	@Transactional(rollbackFor = MYException.class)
	public boolean addPickup(User user, String packageIds) throws MYException
	{
		JudgeTools.judgeParameterIsNull(user, packageIds);

		String [] packages = packageIds.split("~");

		if (null != packages)
		{
			String pickupId = commonDAO.getSquenceString20("PC");
			
			int i = 1;
			
			for (String id : packages)
			{
				// 只能拣配初始态的
				PackageBean bean = packageDAO.find(id);
				
				if (null == bean)
				{
					throw new MYException("出库单[%s]不存在", id);
				}
				
				if (bean.getStatus() != ShipConstant.SHIP_STATUS_INIT)
				{
					throw new MYException("[%s]已被拣配", id);
				}
				
				bean.setIndex_pos(i++);

				bean.setPickupId(pickupId);
				
				bean.setStatus(ShipConstant.SHIP_STATUS_PICKUP);
				
				packageDAO.updateEntityBean(bean);
			}
			
		}
		
		return true;
	}

	/**
	 * 撤销生成的出库单
	 */
	@Transactional(rollbackFor = MYException.class)
	public boolean deletePackage(User user, String packageIds)
			throws MYException
	{
		JudgeTools.judgeParameterIsNull(user, packageIds);
		
		String [] packages = packageIds.split("~");
		
		if (null != packages)
		{
			Set<String> set = new HashSet<String>();
			
			for (String id : packages)
			{
				// 只能拣配初始态的
				PackageBean bean = packageDAO.find(id);
				
				if (null == bean)
				{
					throw new MYException("出库单[%s]不存在", id);
				}
				
				packageDAO.deleteEntityBean(bean.getId());
				
				List<PackageItemBean> itemList = packageItemDAO.queryEntityBeansByFK(bean.getId());
				
				for (PackageItemBean each : itemList)
				{
					if (!set.contains(each.getOutId()))
					{
						set.add(each.getOutId());
					}
					
					packageItemDAO.deleteEntityBean(each.getId());
				}
				
				packageVSCustomerDAO.deleteEntityBeansByFK(id);
			}
			
			// 重新生成发货单
			for (String outId : set)
			{
				PreConsignBean pre = new PreConsignBean();
				
				pre.setOutId(outId);
				
				preConsignDAO.saveEntityBean(pre);
			}
			
			operationLog.info("重新生成发货单准备数据PreConsign:" + set);
		}
		
		return true;
	}

	/**
	 * 
	 */
	@Transactional(rollbackFor = MYException.class)
	public boolean updatePrintStatus(String pickupId, int index_pos) throws MYException
	{
		JudgeTools.judgeParameterIsNull(pickupId);
		
		ConditionParse condtion = new ConditionParse();
    	
    	condtion.addWhereStr();
    	
    	condtion.addCondition("PackageBean.pickupId", "=", pickupId);
    	condtion.addIntCondition("PackageBean.index_pos", "=", index_pos);

    	List<PackageBean> packageList = packageDAO.queryEntityBeansByCondition(condtion);
    	
    	if (!ListTools.isEmptyOrNull(packageList))
    	{
    		PackageBean packageBean = packageList.get(0);
    		
    		if (packageBean.getStatus() != ShipConstant.SHIP_STATUS_CONSIGN)
    		{
    			packageBean.setStatus(ShipConstant.SHIP_STATUS_PRINT);
        		
        		packageDAO.updateEntityBean(packageBean);
    		}
    	}
		
		return true;
	}
	
	@Transactional(rollbackFor = MYException.class)
	public boolean updateStatus(User user, String pickupId) throws MYException
	{
		JudgeTools.judgeParameterIsNull(user, pickupId);
		
		List<PackageBean> packageList = packageDAO.queryEntityBeansByFK(pickupId);
		
		Set<String> set = new HashSet<String>();
		
		for (PackageBean each : packageList)
		{
			each.setStatus(ShipConstant.SHIP_STATUS_CONSIGN);
			
			each.setShipTime(TimeTools.now());
			
			List<PackageItemBean> itemList = packageItemDAO.queryEntityBeansByFK(each.getId());
			
			for (PackageItemBean eachItem : itemList)
			{
				if (!set.contains(eachItem.getOutId()))
				{
					OutBean out = outDAO.find(eachItem.getOutId());
					
					if (null != out && out.getStatus() == OutConstant.STATUS_PASS)
					{
						outDAO.modifyOutStatus(out.getFullId(), OutConstant.STATUS_SEC_PASS);
						
						distributionDAO.updateOutboundDate(out.getFullId(), TimeTools.now_short());
					}
				}
			}
			
			packageDAO.updateEntityBean(each);
		}
		
		return true;
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
}