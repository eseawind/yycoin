/**
 * File Name: StorageApplyManagerImpl.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2010-10-28<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.product.manager.impl;


import java.util.Collection;
import java.util.List;

import org.china.center.spring.ex.annotation.Exceptional;
import org.springframework.transaction.annotation.Transactional;

import com.center.china.osgi.publics.AbstractListenerManager;
import com.center.china.osgi.publics.User;
import com.china.center.common.MYException;
import com.china.center.jdbc.util.ConditionParse;
import com.china.center.oa.product.bean.GSOutBean;
import com.china.center.oa.product.bean.GSOutItemBean;
import com.china.center.oa.product.bean.StorageApplyBean;
import com.china.center.oa.product.constant.ProductApplyConstant;
import com.china.center.oa.product.constant.StorageConstant;
import com.china.center.oa.product.dao.GSOutDAO;
import com.china.center.oa.product.dao.GSOutItemDAO;
import com.china.center.oa.product.dao.StorageApplyDAO;
import com.china.center.oa.product.dao.StorageRelationDAO;
import com.china.center.oa.product.helper.StorageRelationHelper;
import com.china.center.oa.product.listener.StorageApplyListener;
import com.china.center.oa.product.manager.StorageApplyManager;
import com.china.center.oa.product.manager.StorageRelationManager;
import com.china.center.oa.product.vo.StorageApplyVO;
import com.china.center.oa.product.vs.StorageRelationBean;
import com.china.center.oa.product.wrap.ProductChangeWrap;
import com.china.center.oa.publics.bean.FlowLogBean;
import com.china.center.oa.publics.constant.AuthConstant;
import com.china.center.oa.publics.dao.CommonDAO;
import com.china.center.oa.publics.dao.FlowLogDAO;
import com.china.center.oa.publics.manager.UserManager;
import com.china.center.tools.JudgeTools;
import com.china.center.tools.ListTools;
import com.china.center.tools.TimeTools;


/**
 * StorageApplyManagerImpl
 * 
 * @author ZHUZHU
 * @version 2010-10-28
 * @see StorageApplyManagerImpl
 * @since 1.0
 */
@Exceptional
public class StorageApplyManagerImpl extends AbstractListenerManager<StorageApplyListener> implements StorageApplyManager
{
    private StorageApplyDAO storageApplyDAO = null;

    private StorageRelationDAO storageRelationDAO = null;

    private CommonDAO commonDAO = null;

    private UserManager userManager = null;

    private StorageRelationManager storageRelationManager = null;
    
    private FlowLogDAO flowLogDAO = null;
    
    private GSOutDAO gsOutDAO = null;
    
    private GSOutItemDAO gsOutItemDAO = null;
    
    private static final Object lock = new Object();

    /**
     * default constructor
     */
    public StorageApplyManagerImpl()
    {
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.china.center.oa.product.manager.StorageApplyManager#addBean(com.center.china.osgi.publics.User,
     *      com.china.center.oa.product.bean.StorageApplyBean)
     */
    @Transactional(rollbackFor = MYException.class)
    public boolean addBean(User user, StorageApplyBean bean)
        throws MYException
    {
        JudgeTools.judgeParameterIsNull(user, bean);

        checkAdd(user, bean);

        bean.setId(commonDAO.getSquenceString20());

        return storageApplyDAO.saveEntityBean(bean);
    }

    /**
     * checkAdd
     * 
     * @param user
     * @param bean
     * @throws MYException
     */
    private void checkAdd(User user, StorageApplyBean bean)
        throws MYException
    {
        StorageRelationBean relation = storageRelationDAO.find(bean.getStorageRelationId());

        if (relation == null)
        {
            throw new MYException("数据错误,请确认操作");
        }

        // 公卖转私买
        if ("0".equals(relation.getStafferId()))
        {
            boolean hasPublicTranc = userManager.containAuth(user, AuthConstant.STORAGE_TRANS);

            if ( !hasPublicTranc)
            {
                throw new MYException("没有操作权限");
            }

            if ( !"0".equals(relation.getStafferId()))
            {
                throw new MYException("只能转移公共库存");
            }
        }
        else
        {
            if ( !user.getStafferId().equals(relation.getStafferId()))
            {
                throw new MYException("只能转移自己名下的库存");
            }

            if (user.getStafferId().equals(bean.getReveiver()))
            {
                throw new MYException("不能自己转移给自己");
            }
        }

        // 删除以前的申请
        ConditionParse condition = new ConditionParse();

        condition.addWhereStr();

        condition.addCondition("storageRelationId", "=", bean.getStorageRelationId());

        condition.addIntCondition("status", "=", StorageConstant.STORAGEAPPLY_STATUS_SUBMIT);

        storageApplyDAO.deleteEntityBeansByCondition(condition);

        // Expression exp = new Expression(bean, this);
        // exp.check("#storageRelationId && #status &unique @storageApplyDAO", "库存申请已经存在,请重新操作");
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.china.center.oa.product.manager.StorageApplyManager#passBean(com.center.china.osgi.publics.User,
     *      java.lang.String)
     */
    @Transactional(rollbackFor = MYException.class)
    public boolean passBean(User user, String id)
        throws MYException
    {
        JudgeTools.judgeParameterIsNull(user, id);

        StorageApplyVO bean = storageApplyDAO.findVO(id);

        if (bean == null)
        {
            throw new MYException("数据错误,请确认操作");
        }

        if ( !bean.getReveiver().equals(user.getStafferId()))
        {
            throw new MYException("只有接收者可以通过申请");
        }

        if (bean.getStatus() != StorageConstant.STORAGEAPPLY_STATUS_SUBMIT)
        {
            throw new MYException("数据错误,请确认操作");
        }

        StorageRelationBean relation = storageRelationDAO.find(bean.getStorageRelationId());

        if (relation == null)
        {
            throw new MYException("库存不存在,请废弃此申请");
        }

        if (relation.getAmount() < bean.getAmount())
        {
            throw new MYException("库存不够,请废弃此申请");
        }

        // 库存的变动
        moveRelation(user, bean, relation);

        // 修改申请的状态
        bean.setStatus(StorageConstant.STORAGEAPPLY_STATUS_PASS);

        return storageApplyDAO.updateEntityBean(bean);
    }

    /**
     * moveRelation
     * 
     * @param user
     * @param bean
     * @param relation
     * @throws MYException
     */
    private void moveRelation(User user, StorageApplyVO bean, StorageRelationBean relation)
        throws MYException
    {
        String sequence = commonDAO.getSquenceString();

        ProductChangeWrap deleteWrap = new ProductChangeWrap();

        deleteWrap.setRelationId(bean.getStorageRelationId());

        deleteWrap.setType(StorageConstant.OPR_DDEPOTPART_APPLY_MOVE);

        deleteWrap.setSerializeId(sequence);

        deleteWrap.setChange( -bean.getAmount());

        deleteWrap.setRefId(sequence);

        deleteWrap.setDescription("职员[" + bean.getApplyName() + "]转移名下产品到[" + bean.getReveiveName()
                                  + "]");

        storageRelationManager.changeStorageRelationWithoutTransaction(user, deleteWrap, true);

        ProductChangeWrap addWrap = StorageRelationHelper.createProductChangeWrap(relation);

        addWrap.setType(StorageConstant.OPR_DDEPOTPART_APPLY_MOVE);

        addWrap.setSerializeId(sequence);

        addWrap.setStafferId(bean.getReveiver());

        addWrap.setChange(bean.getAmount());

        addWrap.setRefId(sequence);

        addWrap.setDescription("职员[" + bean.getReveiveName() + "]接受名下产品[" + bean.getApplyName()
                               + "]");

        storageRelationManager.changeStorageRelationWithoutTransaction(user, addWrap, false);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.china.center.oa.product.manager.StorageApplyManager#rejectBean(com.center.china.osgi.publics.User,
     *      java.lang.String)
     */
    @Transactional(rollbackFor = MYException.class)
    public boolean rejectBean(User user, String id)
        throws MYException
    {
        JudgeTools.judgeParameterIsNull(user, id);

        return storageApplyDAO.deleteEntityBean(id);
    }

    @Transactional(rollbackFor = MYException.class)
    @Override
	public boolean addGSOutBean(User user, GSOutBean bean) throws MYException
	{
        JudgeTools.judgeParameterIsNull(user, bean);

        bean.setId(commonDAO.getSquenceString20());

        gsOutDAO.saveEntityBean(bean);

        List<GSOutItemBean> itemList = bean.getItemList();

        if ( !ListTools.isEmptyOrNull(itemList))
        {
            for (GSOutItemBean item : itemList)
            {
            	item.setParentId(bean.getId());
            }
        }

        gsOutItemDAO.saveAllEntityBeans(itemList);
        
        FlowLogBean log = new FlowLogBean();

        log.setActor(user.getStafferName());

        log.setDescription("提交");
        log.setFullId(bean.getId());
        log.setOprMode(ProductApplyConstant.OPRMODE_SUBMIT);
        log.setLogTime(TimeTools.now());

        log.setPreStatus(StorageConstant.GSOUT_STATUS_SAVE);

        log.setAfterStatus(bean.getStatus());

        flowLogDAO.saveEntityBean(log);
        
        return true;
    }

	@Override
	@Transactional(rollbackFor = MYException.class)
	public boolean updateGSOutBean(User user, GSOutBean bean)
			throws MYException
	{
        JudgeTools.judgeParameterIsNull(user, bean);

        String id = bean.getId();
        
        GSOutBean oldBean = gsOutDAO.find(id);
        
        if (null == oldBean)
        {
        	throw new MYException("数据错误");
        }

        if (oldBean.getStatus() != StorageConstant.GSOUT_STATUS_REJECT && 
        		oldBean.getStatus() != StorageConstant.GSOUT_STATUS_SAVE)
		{
			throw new MYException("只能修改状态为保存或驳回态");
		}
        
        gsOutDAO.updateEntityBean(bean);

        List<GSOutItemBean> itemList = bean.getItemList();

        if ( !ListTools.isEmptyOrNull(itemList))
        {
            for (GSOutItemBean item : itemList)
            {
            	item.setParentId(bean.getId());
            }
        }

        gsOutItemDAO.deleteEntityBeansByFK(id);
        
        gsOutItemDAO.saveAllEntityBeans(itemList);
        
        FlowLogBean log = new FlowLogBean();

        log.setActor(user.getStafferName());

        log.setDescription("提交");
        log.setFullId(bean.getId());
        log.setOprMode(ProductApplyConstant.OPRMODE_SUBMIT);
        log.setLogTime(TimeTools.now());

        log.setPreStatus(bean.getStatus());

        log.setAfterStatus(StorageConstant.GSOUT_STATUS_SAVE);

        flowLogDAO.saveEntityBean(log);
        
        return true;
    }

	@Override
	@Transactional(rollbackFor = MYException.class)
	public boolean deleteGSOutBean(User user, String id) throws MYException
	{
		JudgeTools.judgeParameterIsNull(id);
		
		GSOutBean bean = gsOutDAO.find(id);
		
		if (null == bean)
		{
			throw new MYException("数据错误");
		}
		
		if (bean.getStatus() != StorageConstant.GSOUT_STATUS_REJECT && 
				bean.getStatus() != StorageConstant.GSOUT_STATUS_SAVE)
		{
			throw new MYException("只能删除状态为保存或驳回态");
		}
		
		gsOutDAO.deleteEntityBean(id);
		
		gsOutItemDAO.deleteEntityBeansByFK(id);
		
		return true;
	}

	@Override
	@Transactional(rollbackFor = MYException.class)
	public boolean passGSOutBean(User user, String id, int nextStatus)
			throws MYException
	{
		JudgeTools.judgeParameterIsNull(user, id, nextStatus);
		
		synchronized (lock)
		{
			GSOutBean bean = gsOutDAO.find(id);
			
			if (null == bean)
			{
				throw new MYException("数据错误");
			}
			
			if (bean.getStatus() == nextStatus)
			{
				throw new MYException("状态不对，已被其他人通过");
			}
			
			if (bean.getStatus() == StorageConstant.GSOUT_STATUS_SEC_PASS)
			{
				throw new MYException("审批已结束，不可操作");
			}
			
			bean.setItemList(gsOutItemDAO.queryEntityBeansByFK(id));
			
			gsOutDAO.modifyStatus(id, nextStatus);
			
			// 库管通过 库存异动，生成凭证
			if (nextStatus == StorageConstant.GSOUT_STATUS_SEC_PASS)
			{
				// 出库
				if (bean.getType() == StorageConstant.STORAGEAPPLY_GS_OUT)
				{
					processGSOutStorage(user, bean, -1);
				}else{
					processGSOutStorage(user, bean, 1);
				}
				
				// listener 生成凭证
				Collection<StorageApplyListener> listeners = this.listenerMapValues();
				
				for (StorageApplyListener each : listeners)
				{
					each.onConfirmGSOut(user, bean);
				}
			}
			
			FlowLogBean log = new FlowLogBean();

	        log.setActor(user.getStafferName());

	        log.setDescription("通过");
	        log.setFullId(bean.getId());
	        log.setOprMode(ProductApplyConstant.OPRMODE_PASS);
	        log.setLogTime(TimeTools.now());

	        log.setPreStatus(bean.getStatus());

	        log.setAfterStatus(nextStatus);

	        flowLogDAO.saveEntityBean(log);
		}
		
		return true;
	}
	
	/**
	 * 
	 * @param user
	 * @param bean
	 * @param forward
	 * @throws MYException
	 */
	private void processGSOutStorage(User user, GSOutBean bean, int forward)
    throws MYException
	{
	    String sid = commonDAO.getSquenceString();
	    
	    List<GSOutItemBean> itemList = bean.getItemList();
	
	    for (GSOutItemBean item : itemList)
	    {
	        ProductChangeWrap eachWrap = new ProductChangeWrap();
	
	        eachWrap.setStorageId(item.getStorageId());
	        eachWrap.setStafferId(StorageConstant.PUBLIC_STAFFER);
	        eachWrap.setChange( forward * item.getAmount());
	        eachWrap.setDepotpartId(item.getDepotpartId());
	        eachWrap.setDescription("金银料成品出入库:" + bean.getId());
	        eachWrap.setPrice(item.getPrice());
	        eachWrap.setProductId(item.getProductId());
	        eachWrap.setType(StorageConstant.OPR_STORAGE_GSOUT);
	        eachWrap.setSerializeId(sid);
	        eachWrap.setRefId(sid);
	
	        storageRelationManager.changeStorageRelationWithoutTransaction(user, eachWrap, true);
	        
	        int [] weights = new int[]{item.getGoldWeight(), item.getSilverWeight()};
        	
        	for (int i = 0; i < weights.length; i++)
        	{
        		int weight = weights[i];
        		
        		if (weight <= 0)
        			continue;
        		
		        // 金银料出入库
		        ProductChangeWrap eachgsWrap = new ProductChangeWrap();
		    	
		        eachgsWrap.setStorageId(item.getStorageId());
		        eachgsWrap.setStafferId(StorageConstant.PUBLIC_STAFFER);
		        if (bean.getType() == StorageConstant.STORAGEAPPLY_GS_OUT)
		        {
		        	eachgsWrap.setChange( -forward * weight);
		        }else{
		        	eachgsWrap.setChange( -forward * weight * item.getAmount());
		        }
		        
		        eachgsWrap.setDepotpartId(item.getDepotpartId());
		        eachgsWrap.setDescription("金银料原料出入库:" + bean.getId());
		        if (i == 0)
	            {
	            	// 金料 ID
	            	eachgsWrap.setPrice(item.getGoldPrice());
	    	        eachgsWrap.setProductId(StorageConstant.GOLD_PRODUCT);
	            }else{
	            	// 银料 ID
	            	eachgsWrap.setPrice(item.getSilverPrice());
	    	        eachgsWrap.setProductId(StorageConstant.SILVER_PRODUCT);
	            }
		        
		        eachgsWrap.setType(StorageConstant.OPR_STORAGE_GSOUT);
		        eachgsWrap.setSerializeId(sid);
		        eachgsWrap.setRefId(sid);
		
		        storageRelationManager.changeStorageRelationWithoutTransaction(user, eachgsWrap, true);
        	}
	    }
	}

	@Override
	@Transactional(rollbackFor = MYException.class)
	public boolean rejectGSOutBean(User user, String id, String reason) throws MYException
	{
		JudgeTools.judgeParameterIsNull(user, id);
		
		GSOutBean bean = gsOutDAO.find(id);
		
		if (null == bean)
		{
			throw new MYException("数据错误");
		}
		
		if (bean.getStatus() == StorageConstant.GSOUT_STATUS_SUBMIT ||
				bean.getStatus() == StorageConstant.GSOUT_STATUS_FLOW_PASS)
		{
			gsOutDAO.modifyStatus(id, StorageConstant.GSOUT_STATUS_REJECT);
		}else{
			throw new MYException("不可驳回");
		}
		
		FlowLogBean log = new FlowLogBean();

        log.setActor(user.getStafferName());

        log.setDescription(reason);
        log.setFullId(bean.getId());
        log.setOprMode(ProductApplyConstant.OPRMODE_REJECT);
        log.setLogTime(TimeTools.now());

        log.setPreStatus(bean.getStatus());

        log.setAfterStatus(StorageConstant.GSOUT_STATUS_REJECT);

        flowLogDAO.saveEntityBean(log);
		
		return true;
	}
    
    /**
     * @return the storageApplyDAO
     */
    public StorageApplyDAO getStorageApplyDAO()
    {
        return storageApplyDAO;
    }

    /**
     * @param storageApplyDAO
     *            the storageApplyDAO to set
     */
    public void setStorageApplyDAO(StorageApplyDAO storageApplyDAO)
    {
        this.storageApplyDAO = storageApplyDAO;
    }

    /**
     * @return the storageRelationDAO
     */
    public StorageRelationDAO getStorageRelationDAO()
    {
        return storageRelationDAO;
    }

    /**
     * @param storageRelationDAO
     *            the storageRelationDAO to set
     */
    public void setStorageRelationDAO(StorageRelationDAO storageRelationDAO)
    {
        this.storageRelationDAO = storageRelationDAO;
    }

    /**
     * @return the commonDAO
     */
    public CommonDAO getCommonDAO()
    {
        return commonDAO;
    }

    /**
     * @param commonDAO
     *            the commonDAO to set
     */
    public void setCommonDAO(CommonDAO commonDAO)
    {
        this.commonDAO = commonDAO;
    }

    /**
     * @return the storageRelationManager
     */
    public StorageRelationManager getStorageRelationManager()
    {
        return storageRelationManager;
    }

    /**
     * @param storageRelationManager
     *            the storageRelationManager to set
     */
    public void setStorageRelationManager(StorageRelationManager storageRelationManager)
    {
        this.storageRelationManager = storageRelationManager;
    }

    /**
     * @return the userManager
     */
    public UserManager getUserManager()
    {
        return userManager;
    }

    /**
     * @param userManager
     *            the userManager to set
     */
    public void setUserManager(UserManager userManager)
    {
        this.userManager = userManager;
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
	 * @return the gsOutDAO
	 */
	public GSOutDAO getGsOutDAO()
	{
		return gsOutDAO;
	}

	/**
	 * @param gsOutDAO the gsOutDAO to set
	 */
	public void setGsOutDAO(GSOutDAO gsOutDAO)
	{
		this.gsOutDAO = gsOutDAO;
	}

	/**
	 * @return the gsOutItemDAO
	 */
	public GSOutItemDAO getGsOutItemDAO()
	{
		return gsOutItemDAO;
	}

	/**
	 * @param gsOutItemDAO the gsOutItemDAO to set
	 */
	public void setGsOutItemDAO(GSOutItemDAO gsOutItemDAO)
	{
		this.gsOutItemDAO = gsOutItemDAO;
	}
}
