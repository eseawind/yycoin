/**
 * File Name: StorageRelationManagerImpl.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2010-8-25<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.product.manager.impl;


import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

import com.center.china.osgi.config.ConfigLoader;
import com.center.china.osgi.publics.AbstractListenerManager;
import com.center.china.osgi.publics.User;
import com.center.china.osgi.publics.file.writer.WriteFile;
import com.center.china.osgi.publics.file.writer.WriteFileFactory;
import com.china.center.common.MYException;
import com.china.center.common.taglib.DefinedCommon;
import com.china.center.jdbc.util.ConditionParse;
import com.china.center.oa.product.bean.DepotBean;
import com.china.center.oa.product.bean.DepotpartBean;
import com.china.center.oa.product.bean.PriceHistoryBean;
import com.china.center.oa.product.bean.ProductBean;
import com.china.center.oa.product.bean.ProductChangeRecordBean;
import com.china.center.oa.product.bean.StorageBean;
import com.china.center.oa.product.bean.StorageLogBean;
import com.china.center.oa.product.bean.StorageSnapshotBean;
import com.china.center.oa.product.constant.ProductConstant;
import com.china.center.oa.product.constant.StorageConstant;
import com.china.center.oa.product.dao.DepotDAO;
import com.china.center.oa.product.dao.DepotpartDAO;
import com.china.center.oa.product.dao.PriceHistoryDAO;
import com.china.center.oa.product.dao.ProductChangeRecordDAO;
import com.china.center.oa.product.dao.ProductDAO;
import com.china.center.oa.product.dao.StorageDAO;
import com.china.center.oa.product.dao.StorageLogDAO;
import com.china.center.oa.product.dao.StorageRelationDAO;
import com.china.center.oa.product.dao.StorageSnapshotDAO;
import com.china.center.oa.product.helper.StorageRelationHelper;
import com.china.center.oa.product.listener.StorageRelationListener;
import com.china.center.oa.product.manager.StorageRelationManager;
import com.china.center.oa.product.vo.DepotVO;
import com.china.center.oa.product.vo.StorageLogVO;
import com.china.center.oa.product.vo.StorageRelationVO;
import com.china.center.oa.product.vs.StorageRelationBean;
import com.china.center.oa.product.wrap.ProductChangeWrap;
import com.china.center.oa.publics.constant.PublicLock;
import com.china.center.oa.publics.dao.CommonDAO;
import com.china.center.oa.publics.manager.FatalNotify;
import com.china.center.tools.BeanUtil;
import com.china.center.tools.CommonTools;
import com.china.center.tools.JudgeTools;
import com.china.center.tools.ListTools;
import com.china.center.tools.MathTools;
import com.china.center.tools.StringTools;
import com.china.center.tools.TimeTools;


/**
 * StorageRelationManagerImpl(CORE)核心的库存操作类
 * 
 * @author ZHUZHU
 * @version 2010-8-25
 * @see StorageRelationManagerImpl
 * @since 1.0
 */
public class StorageRelationManagerImpl extends AbstractListenerManager<StorageRelationListener> implements StorageRelationManager
{
    private final Log fatalLog = LogFactory.getLog("fatal");

    private final Log operationLog = LogFactory.getLog("opr");

    private final Log triggerLog = LogFactory.getLog("trigger");

    private final Log _logger = LogFactory.getLog(getClass());

    private final Log monitorLog = LogFactory.getLog("bill");

    private final Log badLog = LogFactory.getLog("bad");

    private PlatformTransactionManager transactionManager = null;

    private StorageLogDAO storageLogDAO = null;
    
    private ProductChangeRecordDAO productChangeRecordDAO = null;

    private DepotDAO depotDAO = null;

    private PriceHistoryDAO priceHistoryDAO = null;

    private DepotpartDAO depotpartDAO = null;

    private StorageDAO storageDAO = null;

    private ProductDAO productDAO = null;

    private CommonDAO commonDAO = null;
    
    private StorageSnapshotDAO storageSnapshotDAO = null;

    private FatalNotify fatalNotify = null;

    private StorageRelationDAO storageRelationDAO = null;

    private static boolean storageRelationLock = false;

    /**
     * default constructor
     */
    public StorageRelationManagerImpl()
    {
    }

    public boolean checkStorageRelation(ProductChangeWrap bean, boolean includeSelf)
        throws MYException
    {
        if (StorageRelationManagerImpl.storageRelationLock)
        {
            throw new MYException("库存被锁定,请确认解锁库存操作");
        }

        ProductBean productBean = productDAO.find(bean.getProductId());

        if (productBean == null)
        {
            throw new MYException("数据错误,请确认操作");
        }

        // 对库存增加是不校验的
        if (bean.getChange() >= 0)
        {
            return true;
        }
        
        // 对于通用商品不检查库存
        if (bean.getProductId().equals(ProductConstant.OUT_COMMON_PRODUCTID) 
        		|| bean.getProductId().equals(ProductConstant.OUT_COMMON_MPRODUCTID))
		{
        	return true;
		}

        String priceKey = StorageRelationHelper.getPriceKey(bean.getPrice());
        StorageRelationBean relation = storageRelationDAO
            .findByDepotpartIdAndProductIdAndPriceKeyAndStafferId(bean.getDepotpartId(), bean
                .getProductId(), priceKey, bean.getStafferId());
        
        if (relation == null)
        {
            throw new MYException("产品[%s]可发库存不足", productBean.getName());
        }

        int zaitu = sumPreassignByStorageRelation(relation);

        relation.setAmount(relation.getAmount() - zaitu);

        if (relation.getAmount() < 0)
        {
            badLog.error("产品[" + productBean.getName() + "]可发库存为负数:" + relation.getAmount());

            relation.setAmount(0);
        }

        if ( !includeSelf)
        {
            // 自身不再在途中
            if (relation.getAmount() + bean.getChange() < 0)
            {
                throw new MYException("产品[%s]可发库存不足,当前可发:[%d]", productBean.getName(), relation
                    .getAmount());
            }
        }
        else
        {
            if (relation.getAmount() < 0)
            {
                throw new MYException("产品[%s]可发库存不足,当前可发:[%d]", productBean.getName(), relation
                    .getAmount());
            }
        }

        return true;
    }

    /**
     * 根据产品，仓区，所有者 （没有成本）检查库存数 
     * 
     * @param bean
     * @param includeSelf
     * @return
     * @throws MYException
     */
    public List<StorageRelationBean> checkStorageRelation2(ProductChangeWrap bean, boolean includeSelf)
    throws MYException
	{
	    if (StorageRelationManagerImpl.storageRelationLock)
	    {
	        throw new MYException("库存被锁定,请确认解锁库存操作");
	    }
	
	    ProductBean productBean = productDAO.find(bean.getProductId());
	
	    if (productBean == null)
	    {
	        throw new MYException("数据错误,请确认操作");
	    }
	
	    List<StorageRelationBean> relationList = storageRelationDAO
	        .queryByDepotpartIdAndProductIdAndStafferId(bean.getDepotpartId(), bean
	            .getProductId(), bean.getStafferId());
	    
	    if (ListTools.isEmptyOrNull(relationList))
	    {
	        throw new MYException("产品[%s]可发库存不足", productBean.getName());
	    }
	
	    int zaitu = 0;
	    
	    int relationAmount = 0;
	    
	    for (StorageRelationBean relation : relationList)
	    {
	    	int zaitur = sumPreassignByStorageRelation(relation);
	    	
	    	zaitu += zaitur; 
	    	
	    	relationAmount += relation.getAmount();
	    	
	    	relation.setAmount(relation.getAmount() - zaitur);
	    }
	    
	    relationAmount -= zaitu ;
	
	    if ( !includeSelf)
	    {
	        // 自身不再在途中
	        if (relationAmount + bean.getChange() < 0)
	        {
	            throw new MYException("产品[%s]可发库存不足,当前可发:[%d]", productBean.getName(), relationAmount);
	        }
	    }
	    else
	    {
	        if (relationAmount < 0)
	        {
	            throw new MYException("产品[%s]可发库存不足,当前可发:[%d]", productBean.getName(), relationAmount);
	        }
	    }
	
	    return relationList;
	}
    
    /**
     * 根据产品，仓区，所有者 （没有成本）检查库存数 ，只是检查，不做比较
     * 
     * @param bean
     * @param includeSelf
     * @return
     * @throws MYException
     */
    public List<StorageRelationBean> checkStorageRelation3(ProductChangeWrap bean, boolean includeSelf)
    throws MYException
	{
	    ProductBean productBean = productDAO.find(bean.getProductId());
	
	    if (productBean == null)
	    {
	        throw new MYException("数据错误,请确认操作");
	    }
	
	    List<StorageRelationBean> relationList = storageRelationDAO
	        .queryByDepotpartIdAndProductIdAndStafferId(bean.getDepotpartId(), bean
	            .getProductId(), bean.getStafferId());
	    
	    if (ListTools.isEmptyOrNull(relationList))
	    {
	        throw new MYException("产品[%s]可发库存不足", productBean.getName());
	    }
	
	    for (StorageRelationBean relation : relationList)
	    {
	    	int zaitur = sumPreassignByStorageRelation(relation);
	    	
	    	relation.setAmount(relation.getAmount() - zaitur);
	    }
	    
	    return relationList;
	}
    
    /**
     * CORE 处理库存变动的核心
     */
    public synchronized StorageRelationBean changeStorageRelationWithoutTransaction(
                                                                                    User user,
                                                                                    ProductChangeWrap bean,
                                                                                    boolean deleteZeroRelation)
        throws MYException
    {
        if (StorageRelationManagerImpl.storageRelationLock)
        {
            throw new MYException("库存被锁定,请确认解锁库存操作");
        }

        JudgeTools.judgeParameterIsNull(user, bean, bean.getStafferId());

        StorageRelationBean relation = null;

        String priceKey = "";

        // 直接找到储位(优先级最高)
        if ( !StringTools.isNullOrNone(bean.getRelationId()))
        {
            relation = storageRelationDAO.find(bean.getRelationId());

            if (relation == null)
            {
                throw new MYException("数据错误,请确认操作");
            }

            bean.setStorageId(relation.getStorageId());

            priceKey = StorageRelationHelper.getPriceKey(relation.getPrice());

            bean.setPrice(relation.getPrice());

            bean.setProductId(relation.getProductId());

            bean.setDepotpartId(relation.getDepotpartId());

            bean.setStafferId(relation.getStafferId());
        }
        else
        {
            priceKey = StorageRelationHelper.getPriceKey(bean.getPrice());

            JudgeTools.judgeParameterIsNull(bean.getDepotpartId(), bean.getProductId());
        }

        // 防止直接插入的(先给默认储位)
        if (StringTools.isNullOrNone(bean.getStorageId()))
        {
            StorageBean sb = storageDAO.findFristStorage(bean.getDepotpartId());

            if (sb == null)
            {
                throw new MYException("仓区下没有储位,请确认操作");
            }

            bean.setStorageId(sb.getId());
        }

        StorageBean storageBean = storageDAO.find(bean.getStorageId());

        if (storageBean == null)
        {
            throw new MYException("数据错误,请确认操作");
        }

        DepotpartBean depotpartBean = depotpartDAO.find(storageBean.getDepotpartId());

        if (depotpartBean == null)
        {
            throw new MYException("数据错误,请确认操作");
        }

        DepotBean depotBean = depotDAO.find(depotpartBean.getLocationId());

        if (depotBean == null)
        {
            throw new MYException("数据错误,请确认操作");
        }

        ProductBean productBean = productDAO.find(bean.getProductId());

        if (productBean == null)
        {
            throw new MYException("数据错误,请确认操作");
        }

        if (productBean.getAbstractType() == ProductConstant.ABSTRACT_TYPE_YES)
        {
            throw new MYException("虚拟产品没有库存,请确认操作");
        }

        // 处理通用产品库存
        if ((bean.getProductId().equals(ProductConstant.OUT_COMMON_PRODUCTID) || bean.getProductId().equals(ProductConstant.OUT_COMMON_MPRODUCTID))
        		&& bean.getType() == StorageConstant.OPR_STORAGE_OUTBILL)
        {
        	// costPrice = 000
            relation = storageRelationDAO.findByDepotpartIdAndProductIdAndPriceKeyAndStafferId(bean
                    .getDepotpartId(), bean.getProductId(), priceKey, bean.getStafferId());
        	
        	if (null == relation)
        	{
                // 增加一个空的库存
                StorageRelationBean newStorageRelation = new StorageRelationBean();

                newStorageRelation.setId(commonDAO.getSquenceString20());

                // 使用定义储位
                newStorageRelation.setStorageId(bean.getStorageId());
                newStorageRelation.setLocationId(depotBean.getId());
                newStorageRelation.setDepotpartId(depotpartBean.getId());
                newStorageRelation.setPrice(bean.getPrice());
                newStorageRelation.setPriceKey(priceKey);
                newStorageRelation.setAmount(bean.getChange());
                newStorageRelation.setLastPrice(bean.getPrice());
                newStorageRelation.setProductId(bean.getProductId());
                newStorageRelation.setStafferId(bean.getStafferId());
                newStorageRelation.setInputRate(bean.getInputRate());

                storageRelationDAO.saveEntityBean(newStorageRelation);

                relation = newStorageRelation;
        	}
        	else
        	{
                // CORE 更新库存数量变动
                storageRelationDAO.updateStorageRelationAmount(relation.getId(), bean.getChange());
        	}
        	
        	return relation;
        }
        
        if (relation == null)
        {
            relation = storageRelationDAO.findByDepotpartIdAndProductIdAndPriceKeyAndStafferId(bean
                .getDepotpartId(), bean.getProductId(), priceKey, bean.getStafferId());
        }

        if (relation == null && bean.getChange() < 0)
        {
            throw new MYException("仓库[%s]下仓区[%s]下储位[%s]的产品[%s]库存不够,当前库存为[%d],需要使用[%d]", depotBean
                .getName(), depotpartBean.getName(), storageBean.getName(), productBean.getName(),
                0, -bean.getChange());
        }

        if (relation == null && bean.getChange() >= 0)
        {
            // 增加一个空的库存
            StorageRelationBean newStorageRelation = new StorageRelationBean();

            newStorageRelation.setId(commonDAO.getSquenceString20());

            // 使用定义储位
            newStorageRelation.setStorageId(bean.getStorageId());
            newStorageRelation.setLocationId(depotBean.getId());
            newStorageRelation.setDepotpartId(depotpartBean.getId());
            newStorageRelation.setPrice(bean.getPrice());
            newStorageRelation.setPriceKey(priceKey);
            newStorageRelation.setAmount(0);
            newStorageRelation.setLastPrice(bean.getPrice());
            newStorageRelation.setProductId(bean.getProductId());
            newStorageRelation.setStafferId(bean.getStafferId());
            newStorageRelation.setInputRate(bean.getInputRate());

            storageRelationDAO.saveEntityBean(newStorageRelation);

            relation = newStorageRelation;
        }

        // 查看库存大小
        if (relation.getAmount() + bean.getChange() < 0)
        {
            throw new MYException("仓库[%s]下仓区[%s]下储位[%s]的产品[%s]库存不够,当前库存为[%d],需要使用[%d]", depotBean
                .getName(), depotpartBean.getName(), storageBean.getName(), productBean.getName(),
                relation.getAmount(), -bean.getChange());
        }

        // 之前储位内产品的数量
        int preAmount = storageRelationDAO.sumProductInStorage(bean.getProductId(), bean
            .getStorageId());

        int preAmount1 = storageRelationDAO.sumProductInDepotpartId(bean.getProductId(),
            depotpartBean.getId());

        int preAmount11 = storageRelationDAO.sumProductInDepotpartIdAndPriceKey(
            bean.getProductId(), depotpartBean.getId(), priceKey);

        int preAmount2 = storageRelationDAO.sumProductInLocationId(bean.getProductId(), depotBean
            .getId());

        int preAmount22 = storageRelationDAO.sumProductInLocationIdAndPriceKey(bean.getProductId(),
            depotBean.getId(), priceKey);

        // int newAmount = relation.getAmount() + bean.getChange();

        // CORE 更新库存数量变动
        storageRelationDAO.updateStorageRelationAmount(relation.getId(), bean.getChange());
        
        // 采购入库 - 更新进项税率
        if (bean.getType() == StorageConstant.OPR_STORAGE_OUTBILLIN 
        		|| bean.getType() == StorageConstant.OPR_STORAGE_COMPOSE)
        {
        	storageRelationDAO.updateStorageRelationInputRate(relation.getId(), bean.getInputRate());
        }

        // 从数据库读出
        StorageRelationBean updateNew = storageRelationDAO.find(relation.getId());

        // 库存数量符合
        relation.setAmount(updateNew.getAmount());

        if (updateNew.getAmount() == 0 && deleteZeroRelation)
        {
            // 变动后产品数量为0，清除在储位的关系
            storageRelationDAO.deleteEntityBean(relation.getId());
        }

        // 如果脏读有小于0的数据异常抛出
        if (updateNew.getAmount() < 0)
        {
            throw new MYException("仓库[%s]下仓区[%s]下储位[%s]的产品[%s]库存不够,当前库存为[%d],需要使用[%d]", depotBean
                .getName(), depotpartBean.getName(), storageBean.getName(), productBean.getName(),
                relation.getAmount(), -bean.getChange());
        }

        // 之后储位内产品的数量
        int afterAmount = storageRelationDAO.sumProductInStorage(bean.getProductId(), bean
            .getStorageId());

        int afterAmount1 = storageRelationDAO.sumProductInDepotpartId(bean.getProductId(),
            depotpartBean.getId());

        int afterAmount11 = storageRelationDAO.sumProductInDepotpartIdAndPriceKey(bean
            .getProductId(), depotpartBean.getId(), priceKey);

        int afterAmount2 = storageRelationDAO.sumProductInLocationId(bean.getProductId(), depotBean
            .getId());

        int afterAmount22 = storageRelationDAO.sumProductInLocationIdAndPriceKey(bean
            .getProductId(), depotBean.getId(), priceKey);

        // save log
        // 记录仓区的产品异动数量
        StorageLogBean log = new StorageLogBean();

        log.setProductId(bean.getProductId());
        log.setLocationId(depotBean.getId());
        log.setDepotpartId(depotpartBean.getId());
        log.setStorageId(bean.getStorageId());
        log.setPrice(bean.getPrice());
        log.setPriceKey(priceKey);

        log.setType(bean.getType());

        log.setPreAmount(preAmount);

        log.setAfterAmount(afterAmount);

        log.setSerializeId(bean.getSerializeId());

        log.setPreAmount1(preAmount1);
        log.setPreAmount11(preAmount11);

        log.setAfterAmount1(afterAmount1);
        log.setAfterAmount11(afterAmount11);

        log.setPreAmount2(preAmount2);
        log.setPreAmount22(preAmount22);

        log.setAfterAmount2(afterAmount2);
        log.setAfterAmount22(afterAmount22);

        log.setChangeAmount(bean.getChange());

        log.setLogTime(TimeTools.now());

        log.setUser(user.getStafferName());

        log.setDescription(bean.getDescription());

        log.setRefId(bean.getRefId());

        log.setOwner(relation.getStafferId());

        storageLogDAO.saveEntityBean(log);

        monitorLog.info(log);

        // 记录产品价格历史异动
        PriceHistoryBean lastHis = priceHistoryDAO.findLastByProductId(bean.getProductId());

        // 只有产品增加的时候才有价格异动历史
        if ( (lastHis == null && bean.getChange() > 0)
            || (lastHis != null && lastHis.getPrice() != relation.getPrice() && bean.getChange() > 0))
        {
            PriceHistoryBean his = new PriceHistoryBean();
            his.setId(commonDAO.getSquenceString20());
            his.setLogTime(TimeTools.now());
            his.setPrice(relation.getPrice());
            his.setProductId(bean.getProductId());
            his.setType(bean.getType());

            priceHistoryDAO.saveEntityBean(his);
        }

        return relation;
    }

	private List<StorageRelationVO> getCommonProductStorageRelation(final String productId)
	{
		ConditionParse condtion = new ConditionParse();
        
        condtion.clear();
        
        condtion.addWhereStr();
        
        condtion.addCondition("StorageRelationBean.locationId", "=", ProductConstant.OUT_COMMON_DEPOT);
        
        condtion.addCondition("StorageRelationBean.depotpartId", "=", ProductConstant.OUT_COMMON_DEPOTPART);
        
        condtion.addCondition("StorageRelationBean.priceKey", "<>", "0");
        
        condtion.addIntCondition("StorageRelationBean.amount", ">", 0);
        
        condtion.addIntCondition("DepotpartBean.type", "=", 0);
        
        condtion.addCondition("StorageRelationBean.productId", "=", productId);
        
        //condtion.addCondition("ProductBean.reserve4", "=", "1");
        
        List<StorageRelationVO> queryList = storageRelationDAO.queryEntityVOsByCondition(condtion);
        
		return queryList;
	}
    
    /**
     * CORE 处理库存变动的核心
     */
    public synchronized StorageRelationBean changeStorageRelationWithoutTransaction1(
                                                                                    User user,
                                                                                    ProductChangeWrap bean,
                                                                                    boolean deleteZeroRelation)
        throws MYException
    {
        if (StorageRelationManagerImpl.storageRelationLock)
        {
            throw new MYException("库存被锁定,请确认解锁库存操作");
        }

        JudgeTools.judgeParameterIsNull(user, bean, bean.getStafferId());

        StorageRelationBean relation = null;

        String priceKey = "";

        // 直接找到储位(优先级最高)
        if ( !StringTools.isNullOrNone(bean.getRelationId()))
        {
            relation = storageRelationDAO.find(bean.getRelationId());

            if (relation == null)
            {
                throw new MYException("数据错误,请确认操作");
            }

            bean.setStorageId(relation.getStorageId());

            priceKey = StorageRelationHelper.getPriceKey(relation.getPrice());

            bean.setPrice(relation.getPrice());

            bean.setProductId(relation.getProductId());

            bean.setDepotpartId(relation.getDepotpartId());

            bean.setStafferId(relation.getStafferId());
        }
        else
        {
            priceKey = StorageRelationHelper.getPriceKey(bean.getPrice());
            JudgeTools.judgeParameterIsNull(bean.getProductId());
        }
        // 防止直接插入的(先给默认储位)
        if (StringTools.isNullOrNone(bean.getStorageId()))
        {
            StorageBean sb = storageDAO.findFristStorage(bean.getDepotpartId());

            if (sb == null)
            {
                throw new MYException("仓区下没有储位,请确认操作");
            }

            bean.setStorageId(sb.getId());
        }

        StorageBean storageBean = storageDAO.find(bean.getStorageId());

        if (storageBean == null)
        {
            throw new MYException("数据错误,请确认操作");
        }

        DepotpartBean depotpartBean = depotpartDAO.find(storageBean.getDepotpartId());

        if (depotpartBean == null)
        {
            throw new MYException("数据错误,请确认操作");
        }

        DepotBean depotBean = depotDAO.find(depotpartBean.getLocationId());

        if (depotBean == null)
        {
            throw new MYException("数据错误,请确认操作");
        }

        ProductBean productBean = productDAO.find(bean.getProductId());

        if (productBean == null)
        {
            throw new MYException("数据错误,请确认操作");
        }

        if (productBean.getAbstractType() == ProductConstant.ABSTRACT_TYPE_YES)
        {
            throw new MYException("虚拟产品没有库存,请确认操作");
        }
        if (relation == null)
        {
            relation = storageRelationDAO.findByDepotpartIdAndProductIdAndPriceKeyAndStafferId(bean
                .getDepotpartId(), bean.getProductId(), priceKey, bean.getStafferId());
        }

        if (relation == null && bean.getChange() < 0)
        {
            throw new MYException("仓库[%s]下仓区[%s]下储位[%s]的产品[%s]库存不够,当前库存为[%d],需要使用[%d]", depotBean
                .getName(), depotpartBean.getName(), storageBean.getName(), productBean.getName(),
                0, -bean.getChange());
        }
        if (relation == null && bean.getChange() >= 0)
        {
            // 增加一个空的库存
            StorageRelationBean newStorageRelation = new StorageRelationBean();

            newStorageRelation.setId(commonDAO.getSquenceString20());

            // 使用定义储位
            newStorageRelation.setStorageId(bean.getStorageId());
            newStorageRelation.setLocationId(depotBean.getId());
            newStorageRelation.setDepotpartId(depotpartBean.getId());
            newStorageRelation.setPrice(bean.getPrice());
            newStorageRelation.setPriceKey(priceKey);
            newStorageRelation.setAmount(0);
            newStorageRelation.setLastPrice(bean.getPrice());
            newStorageRelation.setProductId(bean.getProductId());
            newStorageRelation.setStafferId(bean.getStafferId());
            newStorageRelation.setInputRate(bean.getInputRate());

            storageRelationDAO.saveEntityBean(newStorageRelation);

            relation = newStorageRelation;
        }
        // 查看库存大小
        if (relation.getAmount() + bean.getChange() < 0)
        {
            throw new MYException("仓库[%s]下仓区[%s]下储位[%s]的产品[%s]库存不够,当前库存为[%d],需要使用[%d]", depotBean
                .getName(), depotpartBean.getName(), storageBean.getName(), productBean.getName(),
                relation.getAmount(), -bean.getChange());
        }

        // 之前储位内产品的数量
        int preAmount = storageRelationDAO.sumProductInStorage(bean.getProductId(), bean
            .getStorageId());

        int preAmount1 = storageRelationDAO.sumProductInDepotpartId(bean.getProductId(),
            depotpartBean.getId());

        int preAmount11 = storageRelationDAO.sumProductInDepotpartIdAndPriceKey(
            bean.getProductId(), depotpartBean.getId(), priceKey);

        int preAmount2 = storageRelationDAO.sumProductInLocationId(bean.getProductId(), depotBean
            .getId());

        int preAmount22 = storageRelationDAO.sumProductInLocationIdAndPriceKey(bean.getProductId(),
            depotBean.getId(), priceKey);
        // int newAmount = relation.getAmount() + bean.getChange();

        // CORE 更新库存数量变动
        storageRelationDAO.updateStorageRelationAmount(relation.getId(), bean.getChange());

        // 从数据库读出
        StorageRelationBean updateNew = storageRelationDAO.find(relation.getId());
        // 库存数量符合
        relation.setAmount(updateNew.getAmount());

        if (updateNew.getAmount() == 0 && deleteZeroRelation)
        {
            // 变动后产品数量为0，清除在储位的关系
            storageRelationDAO.deleteEntityBean(relation.getId());
        }
        // 如果脏读有小于0的数据异常抛出
        if (updateNew.getAmount() < 0)
        {
            throw new MYException("仓库[%s]下仓区[%s]下储位[%s]的产品[%s]库存不够,当前库存为[%d],需要使用[%d]", depotBean
                .getName(), depotpartBean.getName(), storageBean.getName(), productBean.getName(),
                relation.getAmount(), -bean.getChange());
        }

        // 之后储位内产品的数量
        int afterAmount = storageRelationDAO.sumProductInStorage(bean.getProductId(), bean
            .getStorageId());

        int afterAmount1 = storageRelationDAO.sumProductInDepotpartId(bean.getProductId(),
            depotpartBean.getId());
        int afterAmount11 = storageRelationDAO.sumProductInDepotpartIdAndPriceKey(bean
            .getProductId(), depotpartBean.getId(), priceKey);

        int afterAmount2 = storageRelationDAO.sumProductInLocationId(bean.getProductId(), depotBean
            .getId());

        int afterAmount22 = storageRelationDAO.sumProductInLocationIdAndPriceKey(bean
            .getProductId(), depotBean.getId(), priceKey);
        // save log
        // 记录仓区的产品异动数量
        StorageLogBean log = new StorageLogBean();

        log.setProductId(bean.getProductId());
        log.setLocationId(depotBean.getId());
        log.setDepotpartId(depotpartBean.getId());
        log.setStorageId(bean.getStorageId());
        log.setPrice(bean.getPrice());
        log.setPriceKey(priceKey);
        log.setType(bean.getType());

        log.setPreAmount(preAmount);

        log.setAfterAmount(afterAmount);

        log.setSerializeId(bean.getSerializeId());

        log.setPreAmount1(preAmount1);
        log.setPreAmount11(preAmount11);

        log.setAfterAmount1(afterAmount1);
        log.setAfterAmount11(afterAmount11);

        log.setPreAmount2(preAmount2);
        log.setPreAmount22(preAmount22);

        log.setAfterAmount2(afterAmount2);
        log.setAfterAmount22(afterAmount22);

        log.setChangeAmount(bean.getChange());

        log.setLogTime(TimeTools.now());

        log.setUser(user.getStafferName());

        log.setDescription(bean.getDescription());

        log.setRefId(bean.getRefId());

        log.setOwner(relation.getStafferId());

        storageLogDAO.saveEntityBean(log);
        monitorLog.info(log);

        // 记录产品价格历史异动
        PriceHistoryBean lastHis = priceHistoryDAO.findLastByProductId(bean.getProductId());

        // 只有产品增加的时候才有价格异动历史
        if ( (lastHis == null && bean.getChange() > 0)
            || (lastHis != null && lastHis.getPrice() != relation.getPrice() && bean.getChange() > 0))
        {
            PriceHistoryBean his = new PriceHistoryBean();
            his.setId(commonDAO.getSquenceString20());
            his.setLogTime(TimeTools.now());
            his.setPrice(relation.getPrice());
            his.setProductId(bean.getProductId());
            his.setType(bean.getType());

            priceHistoryDAO.saveEntityBean(his);
        }
        return relation;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.china.center.oa.product.manager.StorageRelationManager#changeStorageRelationWithoutTransaction(com.center.china.osgi.publics.User,
     *      com.china.center.oa.product.wrap.ProductChangeWrap)
     */
    public StorageRelationBean changeStorageRelationWithTransaction(final User user,
                                                                    final ProductChangeWrap bean,
                                                                    final boolean deleteZeroRelation)
        throws MYException
    {
        // LOCK 产品库存变动,单独提供事务
        synchronized (PublicLock.PRODUCT_CORE)
        {

            StorageRelationBean result = null;

            try
            {
                // 增加管理员操作在数据库事务中完成
                TransactionTemplate tran = new TransactionTemplate(transactionManager);

                result = (StorageRelationBean)tran.execute(new TransactionCallback()
                {
                    public Object doInTransaction(TransactionStatus arg0)
                    {
                        try
                        {
                            return changeStorageRelationWithoutTransaction(user, bean,
                                deleteZeroRelation);
                        }
                        catch (MYException e)
                        {
                            throw new RuntimeException(e.getErrorContent());
                        }
                    }
                });
            }
            catch (Exception e)
            {
                _logger.error(e, e);

                throw new MYException(e.getMessage());
            }

            return result;
        }
    }

    public int[] initPriceKey()
    {
        // LOCK 产品库存priceKey初始化,单独提供事务
        synchronized (PublicLock.PRODUCT_CORE)
        {
            int[] result = new int[2];

            int success = 0;
            int fail = 0;

            final List<StorageRelationBean> allList = storageRelationDAO.listEntityBeans();

            for (final StorageRelationBean each : allList)
            {
                String priceKey = StorageRelationHelper.getPriceKey(each.getPrice());

                if ( !priceKey.equals(each.getPriceKey()))
                {
                    _logger.info(each + "||old PriceKey:" + each.getPriceKey() + ";new PriceKey:"
                                 + priceKey);

                    each.setPriceKey(priceKey);

                    try
                    {
                        // 增加管理员操作在数据库事务中完成
                        TransactionTemplate tran = new TransactionTemplate(transactionManager);

                        tran.execute(new TransactionCallback()
                        {
                            public Object doInTransaction(TransactionStatus arg0)
                            {
                                storageRelationDAO.updateEntityBean(each);

                                return Boolean.TRUE;
                            }
                        });

                        success++ ;
                    }
                    catch (Exception e)
                    {
                        fail++ ;
                        _logger.error(e, e);
                    }
                }
            }

            result[0] = success;
            result[1] = fail;

            return result;
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.china.center.oa.product.manager.StorageRelationManager#transferStorageRelation(com.center.china.osgi.publics.User,
     *      java.lang.String, java.lang.String, java.lang.String[])
     */
    public boolean transferStorageRelation(final User user, final String sourceStorageId,
                                           final String dirStorageId, final String[] relations)
        throws MYException
    {
        JudgeTools.judgeParameterIsNull(user, sourceStorageId, dirStorageId, relations);

        final StorageBean source = storageDAO.find(sourceStorageId);

        if (source == null)
        {
            throw new MYException("源储位不存在,请确认操作");
        }

        final StorageBean dir = storageDAO.find(dirStorageId);

        if (dir == null)
        {
            throw new MYException("目的储位不存在,请确认操作");
        }

        if ( !source.getDepotpartId().equals(dir.getDepotpartId()))
        {
            throw new MYException("不能跨仓区移动储位,请确认操作");
        }

        // LOCK 储位产品转移,只能是公共的库存
        synchronized (PublicLock.PRODUCT_CORE)
        {
            try
            {
                // 增加管理员操作在数据库事务中完成
                TransactionTemplate tran = new TransactionTemplate(transactionManager);

                tran.execute(new TransactionCallback()
                {
                    public Object doInTransaction(TransactionStatus arg0)
                    {
                        String sid = commonDAO.getSquenceString();

                        // 循环IDS
                        for (String relation : relations)
                        {
                            // ProductChangeWrap
                            StorageRelationBean srb = storageRelationDAO.find(relation);

                            if (srb == null)
                            {
                                throw new RuntimeException("储位内没有产品数据,请重新操作");
                            }

                            if ( !srb.getStorageId().equals(sourceStorageId))
                            {
                                throw new RuntimeException("储位不对,请重新操作");
                            }

                            ProductChangeWrap addWrap = new ProductChangeWrap();

                            addWrap.setType(StorageConstant.OPR_STORAGE_MOVE);
                            addWrap.setChange(srb.getAmount());
                            addWrap.setDescription("从" + source.getName() + "转移到" + dir.getName()
                                                   + "(" + sid + ")");
                            addWrap.setPrice(srb.getPrice());
                            addWrap.setProductId(srb.getProductId());
                            addWrap.setStorageId(dirStorageId);
                            addWrap.setStafferId(srb.getStafferId());
                            addWrap.setSerializeId(sid);
                            addWrap.setDepotpartId(srb.getDepotpartId());
                            addWrap.setRefId(commonDAO.getSquenceString());

                            ProductChangeWrap deleteWrap = new ProductChangeWrap();

                            deleteWrap.setType(StorageConstant.OPR_STORAGE_MOVE);
                            deleteWrap.setChange( -srb.getAmount());
                            deleteWrap.setDescription(addWrap.getDescription());
                            deleteWrap.setPrice(srb.getPrice());
                            deleteWrap.setProductId(srb.getProductId());
                            deleteWrap.setStorageId(sourceStorageId);
                            deleteWrap.setStafferId(srb.getStafferId());
                            deleteWrap.setSerializeId(sid);
                            deleteWrap.setDepotpartId(srb.getDepotpartId());
                            deleteWrap.setRefId(commonDAO.getSquenceString());

                            try
                            {
                                // 因为仓区、产品、价格是唯一主键(先删除再增加)
                                changeStorageRelationWithoutTransaction(user, deleteWrap, true);

                                changeStorageRelationWithoutTransaction(user, addWrap, false);
                            }
                            catch (MYException e)
                            {
                                throw new RuntimeException(e.getErrorContent());
                            }
                        }

                        return Boolean.TRUE;
                    }
                });
            }
            catch (Exception e)
            {
                _logger.error(e, e);

                throw new MYException(e.getMessage());
            }
        }

        return true;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.china.center.oa.product.manager.StorageRelationManager#transferStorageRelationInDepotpart(com.center.china.osgi.publics.User,
     *      java.lang.String, java.lang.String, int)
     */
    public String transferStorageRelationInDepotpart(final User user,
                                                     final String sourceRelationId,
                                                     final String dirDepotpartId, final String amount,
                                                     final String apply)
        throws MYException
    {
        JudgeTools.judgeParameterIsNull(user, sourceRelationId, dirDepotpartId);
        
        String rids[] = sourceRelationId.split(",");
        String amounts[] = amount.split(",");
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");//设置日期格式
        final String flowid = "MP33"+commonDAO.getSquenceString();
        for(int i = 0 ; i < rids.length ; i++)
        {
        	final ProductChangeRecordBean pcBean = new ProductChangeRecordBean();
        	pcBean.setMoveStaffer(user.getStafferId());
        	pcBean.setId("20"+commonDAO.getSquenceString());
        	pcBean.setFlowid(flowid);
        	pcBean.setMoveTime(df.format(new Date()));
	        final StorageRelationBean srb = storageRelationDAO.find(rids[i]);
	        pcBean.setProductId(srb.getProductId());
	        pcBean.setAmount(Integer.parseInt(amounts[i]));
	        final int am = CommonTools.parseInt(amounts[i]);
	
	        if (srb == null)
	        {
	            throw new MYException("数据错误,请确认操作");
	        }
	
	        if (srb.getDepotpartId().equals(dirDepotpartId))
	        {
	            throw new MYException("源仓区不能和目的仓区相同,请确认操作");
	        }
	
	        if (srb.getAmount() <  am)
	        {
	            throw new MYException("源仓区下产品数量不足[%d],请确认操作", srb.getAmount());
	        }
	        pcBean.setOldStore(srb.getDepotpartId());
	        final DepotpartBean oldDepotpart = depotpartDAO.find(srb.getDepotpartId());
	
	        if (oldDepotpart == null)
	        {
	            throw new MYException("数据错误,请确认操作");
	        }
	
	        final DepotpartBean newDepotpart = depotpartDAO.find(dirDepotpartId);
	        pcBean.setNewStore(dirDepotpartId);
	
	        if (newDepotpart == null)
	        {
	            throw new MYException("数据错误,请确认操作");
	        }
	
	        // 自动找寻仓区下产品的位置
	        final StorageRelationBean newRelationBean = storageRelationDAO
	            .findByDepotpartIdAndProductIdAndPriceKeyAndStafferId(dirDepotpartId, srb
	                .getProductId(), srb.getPriceKey(), srb.getStafferId());
	
	        final List<StorageBean> sbs = new ArrayList();
	
	        if (newRelationBean == null)
	        {
	            StorageBean sb = storageDAO.findFristStorage(dirDepotpartId);
	
	            if (sb == null)
	            {
	                throw new MYException("仓区下没有储位,请确认操作");
	            }
	            else
	            {
	                sbs.add(sb);
	            }
	        }
	
	        final String sid = commonDAO.getSquenceString();
	        
	        // 检查转移的产品是否存在销售未通过的(防止库存是负数的情况)
	        int count = this.sumPreassignByStorageRelation(srb);
	
	        if (srb.getAmount() - count - am < 0)
	        {
	            throw new MYException("转移的产品存在[%d]个未发货(在销售/入库中),不能仓区间转移,请确认操作", count);
	        }
	        
	
	        // LOCK 仓区产品转移
	        synchronized (PublicLock.PRODUCT_CORE)
	        {
	            try
	            {
	                // 增加管理员操作在数据库事务中完成
	                TransactionTemplate tran = new TransactionTemplate(transactionManager);
	
	                tran.execute(new TransactionCallback()
	                {
	                    public Object doInTransaction(TransactionStatus arg0)
	                    {
	            	        productChangeRecordDAO.saveEntityBean(pcBean);
	                        // 首先是源仓区减去产品数量
	                        String des = "从仓区[" + oldDepotpart.getName() + "]转移到["
	                                     + newDepotpart.getName() + "],申请人:" + apply;
	
	                        ProductChangeWrap deleteWrap = new ProductChangeWrap();
	
	                        deleteWrap.setType(StorageConstant.OPR_DDEPOTPART_MOVE);
	                        deleteWrap.setChange( -am);
	                        deleteWrap.setDescription(des);
	                        deleteWrap.setPrice(srb.getPrice());
	                        deleteWrap.setProductId(srb.getProductId());
	                        deleteWrap.setStorageId(srb.getStorageId());
	                        deleteWrap.setSerializeId(sid);
	                        deleteWrap.setDepotpartId(srb.getDepotpartId());
	                        deleteWrap.setRefId(sid);
	                        deleteWrap.setStafferId(srb.getStafferId());
	
	                        ProductChangeWrap addWrap = new ProductChangeWrap();
	
	                        addWrap.setType(StorageConstant.OPR_DDEPOTPART_MOVE);
	                        addWrap.setChange(am);
	                        addWrap.setDescription(des);
	                        addWrap.setPrice(srb.getPrice());
	                        addWrap.setProductId(srb.getProductId());
	
	                        if (newRelationBean != null)
	                        {
	                            addWrap.setStorageId(newRelationBean.getStorageId());
	                        }
	                        else
	                        {
	                            // 就是仓区下没有此价格的储位关系,此时默认转移到在仓区下第一个储位
	                            addWrap.setStorageId(sbs.get(0).getId());
	                        }
	                        addWrap.setSerializeId(sid);
	                        addWrap.setDepotpartId(dirDepotpartId);
	                        addWrap.setRefId(sid);
	                        addWrap.setStafferId(srb.getStafferId());
	
	                        try
	                        {
	                            // 因为仓区、产品、价格是唯一主键(先删除再增加)
	                            changeStorageRelationWithoutTransaction(user, deleteWrap, false);
	
	                            changeStorageRelationWithoutTransaction(user, addWrap, false);
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
	                _logger.error(e, e);
	
	                throw new MYException(e.getMessage());
	            }
	        }
        }
        return flowid;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.china.center.oa.product.manager.StorageRelationManager#deleteStorageRelation(com.center.china.osgi.publics.User,
     *      java.lang.String)
     */
    public boolean deleteStorageRelation(User user, final String id)
        throws MYException
    {
        // LOCK 删除产品数量为0的数据
        synchronized (PublicLock.PRODUCT_CORE)
        {
            JudgeTools.judgeParameterIsNull(user, id);

            final StorageRelationBean old = storageRelationDAO.find(id);

            if (old == null)
            {
                throw new MYException("数据错误,请确认操作");
            }

            if (old.getAmount() != 0)
            {
                throw new MYException("储位内产品数量大于0,不能删除,请确认操作");
            }

            try
            {
                // 增加管理员操作在数据库事务中完成
                TransactionTemplate tran = new TransactionTemplate(transactionManager);

                tran.execute(new TransactionCallback()
                {
                    public Object doInTransaction(TransactionStatus arg0)
                    {
                        storageRelationDAO.deleteEntityBean(id);

                        return Boolean.TRUE;
                    }
                });
            }
            catch (Exception e)
            {
                _logger.error(e, e);

                throw new MYException(e.getCause().toString());
            }

            return true;
        }
    }

    public int sumPreassignByStorageRelation(StorageRelationBean bean)
    {
        Collection<StorageRelationListener> listenerMapValues = this.listenerMapValues();

        int sum = 0;

        for (StorageRelationListener storageRelationListener : listenerMapValues)
        {
            sum += storageRelationListener.onFindPreassignByStorageRelation(bean);
        }

        return sum;
    }
    
    public int sumPreassignByStorageRelation2(String depotpartId, String productId, String stafferId)
    {
        Collection<StorageRelationListener> listenerMapValues = this.listenerMapValues();

        int sum = 0;

        for (StorageRelationListener storageRelationListener : listenerMapValues)
        {
            sum += storageRelationListener.onFindPreassignByStorageRelation2(depotpartId, productId, stafferId);
        }

        return sum;
    }

    public int sumInwayByStorageRelation(StorageRelationBean bean)
    {
        Collection<StorageRelationListener> listenerMapValues = this.listenerMapValues();

        int sum = 0;

        for (StorageRelationListener storageRelationListener : listenerMapValues)
        {
            sum += storageRelationListener.onFindInwayByStorageRelation(bean);
        }

        return sum;
    }

    public boolean isStorageRelationLock()
    {
        return StorageRelationManagerImpl.storageRelationLock;
    }

    public synchronized void lockStorageRelation()
    {
        StorageRelationManagerImpl.storageRelationLock = true;
    }

    public synchronized void unlockStorageRelation()
    {
        StorageRelationManagerImpl.storageRelationLock = false;
    }

    public List<String> checkStorageLog()
    {
        triggerLog.info("begin checkStorageLog...");

        final List<String> result = new LinkedList<String>();

        // String logTime = "2011-04-01 00:00:00";
        String logTime = TimeTools.now( -30);

        // 获得仓区下移动的产品
        List<DepotBean> listEntityBeans = depotDAO.listEntityBeans();

        int total = 0;
        int success = 0;
        int fail = 0;
        // 迭代仓库
        for (DepotBean depotBean : listEntityBeans)
        {
            List<String> productList = storageLogDAO.queryDistinctProductByDepotIdAndLogTime(
                depotBean.getId(), logTime);

            // 产品
            for (String productIdEach : productList)
            {
                total++ ;
                ConditionParse condition = new ConditionParse();

                condition.addWhereStr();

                condition.addCondition("StorageLogBean.locationId", "=", depotBean.getId());
                condition.addCondition("StorageLogBean.productId", "=", productIdEach);
                condition.addCondition("StorageLogBean.logTime", ">=", logTime);

                condition.addCondition("order by StorageLogBean.id asc");

                List<StorageLogVO> logList = storageLogDAO.queryEntityVOsByCondition(condition);

                int end = -99;

                boolean allTrue = true;

                for (StorageLogVO storageLogBeanEach : logList)
                {
                    if (end == -99)
                    {
                        end = storageLogBeanEach.getAfterAmount2();
                    }
                    else
                    {
                        if (storageLogBeanEach.getPreAmount2() != end)
                        {
                            String msg = "产品[" + storageLogBeanEach.getProductName() + "]在仓库["
                                         + storageLogBeanEach.getLocationName() + "]下异动断节";

                            fatalLog.error(msg + ".StorageLog:" + storageLogBeanEach);

                            result.add(msg);

                            allTrue = false;

                            break;
                        }
                        else
                        {
                            end = storageLogBeanEach.getAfterAmount2();
                        }
                    }
                }

                if (allTrue)
                {
                    success++ ;
                }
                else
                {
                    fail++ ;
                }
            }
        }

        String totalMsg = "共计体检库存异动:" + total + ".其中成功:" + success + ".失败:" + fail;

        result.add(totalMsg);

        if (fail > 0)
        {
            fatalNotify.notify(totalMsg);
        }

        triggerLog.info("checkStorageLog:" + totalMsg);

        operationLog.info("checkStorageLog:" + totalMsg);

        triggerLog.info("end checkStorageLog...");

        return result;
    }

    public void exportAllStorageRelation()
    {
        triggerLog.info("begin exportAllStorageRelation...");

        final List<StorageSnapshotBean> snapshotList = new ArrayList<StorageSnapshotBean>();
        
        String date = TimeTools.now_short(1);
        
        WriteFile write = null;

        OutputStream out = null;

        try
        {
            out = new FileOutputStream(getProductStorePath() + "/ProductAmount_"
                                       + TimeTools.now("yyyyMMddHHmmss") + ".csv");

            ConditionParse condtion = new ConditionParse();

            List<DepotVO> lList = depotDAO.listEntityVOs();

            write = WriteFileFactory.getMyTXTWriter();

            write.openFile(out);

            write.writeLine("日期,事业部,仓库,仓区,仓区属性,储位,产品名称,产品编码,产品数量,产品价格");

            String now = TimeTools.now("yyyy-MM-dd");
            
            for (DepotVO locationBean : lList)
            {
                condtion.clear();

                condtion.addCondition("StorageRelationBean.locationId", "=", locationBean.getId());

                condtion.addIntCondition("StorageRelationBean.amount", ">", 0);

                List<StorageRelationVO> list = storageRelationDAO
                    .queryEntityVOsByCondition(condtion);

                for (StorageRelationVO each : list)
                {
                    if (each.getAmount() > 0)
                    {
                        String typeName = DefinedCommon.getValue("depotpartType", each
                            .getDepotpartType());

                        write.writeLine(now + ','
                                        + StringTools.getLineString(locationBean.getIndustryName())
                                        + ',' + locationBean.getName() + ','
                                        + each.getDepotpartName() + ',' + typeName + ','
                                        + each.getStorageName() + ','
                                        + StringTools.getExportString2(each.getProductName()) + ','
                                        + each.getProductCode() + ','
                                        + String.valueOf(each.getAmount()) + ','
                                        + MathTools.formatNum(each.getPrice()));
                        
                        StorageSnapshotBean snapBean = new StorageSnapshotBean();
                        
                        snapBean.setSdate(date);
                        snapBean.setIndustryName(locationBean.getIndustryName());
                        snapBean.setDepotName(locationBean.getName());
                        snapBean.setDepotpartName(each.getDepotpartName());
                        snapBean.setDepotpartProp(typeName);
                        snapBean.setStorageName(each.getStorageName());
                        snapBean.setProductName(each.getProductName());
                        snapBean.setProductCode(each.getProductCode());
                        snapBean.setAmount(each.getAmount());
                        snapBean.setPrice(each.getPrice());
                        
                        snapshotList.add(snapBean);
                    }
                }

            }

            // 导出在途的产品
            Collection<StorageRelationListener> listenerMapValues = this.listenerMapValues();

            List<StorageRelationVO> extList = new LinkedList<StorageRelationVO>();

            for (StorageRelationListener storageRelationListener : listenerMapValues)
            {
                List<StorageRelationVO> onExportOtherStorageRelation = storageRelationListener
                    .onExportOtherStorageRelation();

                if ( !ListTools.isEmptyOrNull(onExportOtherStorageRelation))
                {
                    extList.addAll(onExportOtherStorageRelation);
                }
            }

            for (StorageRelationVO each : extList)
            {
                DepotVO depot = depotDAO.findVO(each.getLocationId());

                String locationName = "";

                if (depot != null)
                {
                    locationName = depot.getName();
                }

                ProductBean product = productDAO.find(each.getProductId());

                String productCode = "";

                if (product != null)
                {
                    productCode = product.getCode();
                }

                String typeName = "在途库存";

                write.writeLine(now + ',' + StringTools.getLineString(depot.getIndustryName())
                                + ',' + locationName + ',' + each.getDepotpartName() + ','
                                + typeName + ',' + each.getStorageName() + ','
                                + StringTools.getLineString(each.getProductName()) + ','
                                + productCode + ',' + String.valueOf(each.getAmount()) + ','
                                + MathTools.formatNum(each.getPrice()));
                
                StorageSnapshotBean snapBean = new StorageSnapshotBean();
                
                snapBean.setSdate(date);
                snapBean.setIndustryName(depot.getIndustryName());
                snapBean.setDepotName(locationName);
                snapBean.setDepotpartName(each.getDepotpartName());
                snapBean.setDepotpartProp(typeName);
                snapBean.setStorageName(each.getStorageName());
                snapBean.setProductName(each.getProductName());
                snapBean.setProductCode(productCode);
                snapBean.setAmount(each.getAmount());
                snapBean.setPrice(each.getPrice());
                
                snapshotList.add(snapBean);
            }

            write.close();

        }
        catch (Throwable e)
        {
            triggerLog.error(e, e);
        }
        finally
        {
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
        }

        try
        {
            // 增加管理员操作在数据库事务中完成
            TransactionTemplate tran = new TransactionTemplate(transactionManager);

            tran.execute(new TransactionCallback()
            {
                public Object doInTransaction(TransactionStatus arg0)
                {
                	storageSnapshotDAO.saveAllEntityBeans(snapshotList);
            	
                	return Boolean.TRUE;
            	}
            });
        }
        catch (Exception e)
        {
            _logger.error(e, e);

            throw new RuntimeException(e.getMessage());
        }
        
        
        triggerLog.info("end exportAllStorageRelation...");
    }

    /**
     * 冲平通用商品销售产生的负库存，计划执行时间为晚上23:00点
     * {@inheritDoc}
     */
	public void processStorageRelationByCommonProduct()
	{
		String [] productIds = new String[]{ProductConstant.OUT_COMMON_PRODUCTID, ProductConstant.OUT_COMMON_MPRODUCTID};
		
    	final List<StorageRelationBean> rList = new ArrayList<StorageRelationBean>();
		
		for (int i = 0; i< productIds.length; i++)
		{
			StorageRelationBean relation = storageRelationDAO.findByDepotpartIdAndProductIdAndPriceKeyAndStafferId(ProductConstant.OUT_COMMON_DEPOTPART, 
					productIds[i], "0", "0");
	    	
	    	if (null == relation)
	    	{
	    		_logger.info("通用商品库存不存在(成本为0的数据)");
	    		
	    		return;
	    	}
			
	    	rList.add(relation);
	    	
			List<StorageRelationVO> list = getCommonProductStorageRelation(productIds[i]);
			
			if (!ListTools.isEmptyOrNull(list))
			{
				for (StorageRelationVO each : list)
				{
					if (each.getAmount() > 0)
					{
						relation.setAmount(each.getAmount() + relation.getAmount());
						
						each.setAmount(0);
						
						StorageRelationBean bean = new StorageRelationBean();
						
						BeanUtil.copyProperties(bean, each);
						
						rList.add(bean);
					}
				}
			}
		}
		
		try
        {
            // 增加管理员操作在数据库事务中完成
            TransactionTemplate tran = new TransactionTemplate(transactionManager);

            tran.execute(new TransactionCallback()
            {
                public Object doInTransaction(TransactionStatus arg0)
                {                    	
                	storageRelationDAO.updateAllEntityBeans(rList);
            	
                	return Boolean.TRUE;
            	}
            });
        }
        catch (Exception e)
        {
            _logger.error(e, e);

            throw new RuntimeException(e.getMessage());
        }
	}
    
    /**
     * @return the mailAttchmentPath
     */
    public String getProductStorePath()
    {
        return ConfigLoader.getProperty("productStore");
    }

    /**
     * @return the transactionManager
     */
    public PlatformTransactionManager getTransactionManager()
    {
        return transactionManager;
    }

    /**
     * @param transactionManager
     *            the transactionManager to set
     */
    public void setTransactionManager(PlatformTransactionManager transactionManager)
    {
        this.transactionManager = transactionManager;
    }

    /**
     * @return the storageLogDAO
     */
    public StorageLogDAO getStorageLogDAO()
    {
        return storageLogDAO;
    }

    /**
     * @param storageLogDAO
     *            the storageLogDAO to set
     */
    public void setStorageLogDAO(StorageLogDAO storageLogDAO)
    {
        this.storageLogDAO = storageLogDAO;
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
     * @return the depotDAO
     */
    public DepotDAO getDepotDAO()
    {
        return depotDAO;
    }

    /**
     * @param depotDAO
     *            the depotDAO to set
     */
    public void setDepotDAO(DepotDAO depotDAO)
    {
        this.depotDAO = depotDAO;
    }

    /**
     * @return the depotpartDAO
     */
    public DepotpartDAO getDepotpartDAO()
    {
        return depotpartDAO;
    }

    /**
     * @param depotpartDAO
     *            the depotpartDAO to set
     */
    public void setDepotpartDAO(DepotpartDAO depotpartDAO)
    {
        this.depotpartDAO = depotpartDAO;
    }

    /**
     * @return the storageDAO
     */
    public StorageDAO getStorageDAO()
    {
        return storageDAO;
    }

    /**
     * @param storageDAO
     *            the storageDAO to set
     */
    public void setStorageDAO(StorageDAO storageDAO)
    {
        this.storageDAO = storageDAO;
    }

    /**
     * @return the productDAO
     */
    public ProductDAO getProductDAO()
    {
        return productDAO;
    }

    /**
     * @param productDAO
     *            the productDAO to set
     */
    public void setProductDAO(ProductDAO productDAO)
    {
        this.productDAO = productDAO;
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
     * @return the priceHistoryDAO
     */
    public PriceHistoryDAO getPriceHistoryDAO()
    {
        return priceHistoryDAO;
    }

    /**
     * @param priceHistoryDAO
     *            the priceHistoryDAO to set
     */
    public void setPriceHistoryDAO(PriceHistoryDAO priceHistoryDAO)
    {
        this.priceHistoryDAO = priceHistoryDAO;
    }

    /**
     * @return the fatalNotify
     */
    public FatalNotify getFatalNotify()
    {
        return fatalNotify;
    }

    /**
     * @param fatalNotify
     *            the fatalNotify to set
     */
    public void setFatalNotify(FatalNotify fatalNotify)
    {
        this.fatalNotify = fatalNotify;
    }

	public ProductChangeRecordDAO getProductChangeRecordDAO() {
		return productChangeRecordDAO;
	}

	public void setProductChangeRecordDAO(
			ProductChangeRecordDAO productChangeRecordDAO) {
		this.productChangeRecordDAO = productChangeRecordDAO;
	}

	/**
	 * @return the storageSnapshotDAO
	 */
	public StorageSnapshotDAO getStorageSnapshotDAO()
	{
		return storageSnapshotDAO;
	}

	/**
	 * @param storageSnapshotDAO the storageSnapshotDAO to set
	 */
	public void setStorageSnapshotDAO(StorageSnapshotDAO storageSnapshotDAO)
	{
		this.storageSnapshotDAO = storageSnapshotDAO;
	}
}
