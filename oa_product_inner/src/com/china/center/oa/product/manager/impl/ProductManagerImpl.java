/**
 * File Name: ProductManagerImpl.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2010-8-15<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.product.manager.impl;


import java.util.Collection;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.china.center.spring.ex.annotation.Exceptional;
import org.springframework.transaction.annotation.Transactional;

import com.center.china.osgi.config.ConfigLoader;
import com.center.china.osgi.publics.AbstractListenerManager;
import com.center.china.osgi.publics.User;
import com.china.center.common.MYException;
import com.china.center.jdbc.expression.Expression;
import com.china.center.oa.product.bean.CiticVSOAProductBean;
import com.china.center.oa.product.bean.GoldSilverPriceBean;
import com.china.center.oa.product.bean.ProductBean;
import com.china.center.oa.product.constant.ProductConstant;
import com.china.center.oa.product.dao.CiticVSOAProductDAO;
import com.china.center.oa.product.dao.GoldSilverPriceDAO;
import com.china.center.oa.product.dao.ProductCombinationDAO;
import com.china.center.oa.product.dao.ProductDAO;
import com.china.center.oa.product.dao.ProductVSLocationDAO;
import com.china.center.oa.product.listener.ProductListener;
import com.china.center.oa.product.manager.ProductManager;
import com.china.center.oa.product.vs.ProductCombinationBean;
import com.china.center.oa.product.vs.ProductVSLocationBean;
import com.china.center.oa.publics.dao.CommonDAO;
import com.china.center.oa.publics.manager.NotifyManager;
import com.china.center.tools.FileTools;
import com.china.center.tools.JPTools;
import com.china.center.tools.JudgeTools;
import com.china.center.tools.ListTools;
import com.china.center.tools.StringTools;


/**
 * ProductManagerImpl
 * 
 * @author ZHUZHU
 * @version 2010-8-15
 * @see ProductManagerImpl
 * @since 1.0
 */
@Exceptional
public class ProductManagerImpl extends AbstractListenerManager<ProductListener> implements ProductManager
{
    private final Log operationLog = LogFactory.getLog("opr");

    private ProductCombinationDAO productCombinationDAO = null;

    private ProductDAO productDAO = null;

    private CommonDAO commonDAO = null;

    private NotifyManager notifyManager = null;

    private ProductVSLocationDAO productVSLocationDAO = null;

    private GoldSilverPriceDAO goldSilverPriceDAO = null;
    
    private CiticVSOAProductDAO citicVSOAProductDAO = null;
    
    /**
     * default constructor
     */
    public ProductManagerImpl()
    {
    }

    /**
     * addProductBean
     * 
     * @param user
     * @param bean
     * @throws MYException
     */
    @Transactional(rollbackFor = MYException.class)
    public boolean addProductBean(User user, ProductBean bean)
        throws MYException
    {
        JudgeTools.judgeParameterIsNull(user, bean);

        createCode(bean);

        createSpell(bean);

        Expression exp = new Expression(bean, this);

        exp.check("#name &unique @productDAO", "名称已经存在");

        exp.check("#code &unique @productDAO", "编码已经存在");

        productDAO.saveEntityBean(bean);

        // 这里插入产品对于关系
        List<ProductCombinationBean> vsList = bean.getVsList();

        if ( !ListTools.isEmptyOrNull(vsList))
        {
            for (ProductCombinationBean productCombinationBean : vsList)
            {
                productCombinationBean.setVproductId(bean.getId());
            }

            productCombinationDAO.saveAllEntityBeans(vsList);
        }

        // 如果是申请虚拟产品,自动绑定销售区域
        if (bean.getAbstractType() == ProductConstant.ABSTRACT_TYPE_YES
            && bean.getStatus() == ProductConstant.STATUS_APPLY)
        {
            ProductVSLocationBean vs = new ProductVSLocationBean();
            vs.setLocationId(user.getLocationId());
            vs.setProductId(bean.getId());

            productVSLocationDAO.saveEntityBean(vs);
        }

        executeAddListener(user, bean);

        return true;
    }

    /**
     * changeProductStatus
     * 
     * @param user
     * @param productId
     * @param oldStatus
     * @param newStatus
     * @throws MYException
     */
    @Transactional(rollbackFor = MYException.class)
    public boolean changeProductStatus(User user, String productId, int oldStatus, int newStatus)
        throws MYException
    {
        JudgeTools.judgeParameterIsNull(user, productId);

        this.productDAO.updateStatus(productId, newStatus);

        executeProductStatusChange(user, productId, oldStatus, newStatus);

        return true;
    }

    @Transactional(rollbackFor = MYException.class)
    public boolean configProductVSLocation(User user, String productId,
                                           List<ProductVSLocationBean> vsList)
        throws MYException
    {
        JudgeTools.judgeParameterIsNull(user, vsList);

        productVSLocationDAO.deleteEntityBeansByFK(productId);

        for (ProductVSLocationBean productVSLocationBean : vsList)
        {
            productVSLocationBean.setProductId(productId);
        }

        productVSLocationDAO.saveAllEntityBeans(vsList);

        return true;
    }

    @Transactional(rollbackFor = MYException.class)
    public boolean deleteProductBean(User user, String id)
        throws MYException
    {
        JudgeTools.judgeParameterIsNull(user, id);

        ProductBean old = productDAO.find(id);

        if (old == null)
        {
            throw new MYException("数据错误,请确认操作");
        }

        executeOnDeleteProduct(user, old);

        productDAO.deleteEntityBean(id);

        productCombinationDAO.deleteEntityBeansByFK(id);

        productVSLocationDAO.deleteEntityBeansByFK(id);

        FileTools.deleteFile(getPicPath() + old.getPicPath());

        notifyManager.notifyMessage(old.getCreaterId(), user.getStafferName() + "驳回或者删除了产品申请:"
                                                        + old.getName());

        operationLog.info("delete ProductBean:" + old);

        return true;
    }

    @Transactional(rollbackFor = MYException.class)
    public boolean updateProductBean(User user, ProductBean bean)
        throws MYException
    {
        JudgeTools.judgeParameterIsNull(user, bean);

        ProductBean old = productDAO.find(bean.getId());

        if (old == null)
        {
            throw new MYException("数据错误,请确认操作");
        }

        operationLog.info("updateProductBean old ProductBean:" + old);

        bean.setName(old.getName());

        bean.setCode(old.getCode());

        createSpell(bean);

        executeOnUpdateProduct(user, bean);

        productDAO.updateEntityBean(bean);

        operationLog.info("updateProductBean new ProductBean:" + bean);

        // 这里插入产品对于关系
        List<ProductCombinationBean> vsList = bean.getVsList();

        if ( !ListTools.isEmptyOrNull(vsList))
        {
            productCombinationDAO.deleteEntityBeansByFK(bean.getId());

            for (ProductCombinationBean productCombinationBean : vsList)
            {
                productCombinationBean.setVproductId(bean.getId());
            }

            productCombinationDAO.saveAllEntityBeans(vsList);
        }

        return true;
    }

    /**
     * executeAddListener
     * 
     * @param user
     * @param bean
     * @throws MYException
     */
    private void executeAddListener(User user, ProductBean bean)
        throws MYException
    {
        // 实现监听
        Collection<ProductListener> values = this.listenerMapValues();

        for (ProductListener productListener : values)
        {
            productListener.onAddProduct(user, bean);
        }
    }

    /**
     * executeProductStatusChange
     * 
     * @param user
     * @param bean
     * @throws MYException
     */
    private void executeProductStatusChange(User user, String productId, int oldStatus,
                                            int newStatus)
        throws MYException
    {
        // 实现监听
        Collection<ProductListener> values = this.listenerMapValues();

        for (ProductListener productListener : values)
        {
            productListener.onProductStatusChange(user, productId, oldStatus, newStatus);
        }
    }

    /**
     * executeDeleteProduct
     * 
     * @param user
     * @param bean
     * @throws MYException
     */
    private void executeOnDeleteProduct(User user, ProductBean bean)
        throws MYException
    {
        // 实现监听
        Collection<ProductListener> values = this.listenerMapValues();

        for (ProductListener productListener : values)
        {
            productListener.onDeleteProduct(user, bean);
        }
    }

    /**
     * executeOnUpdateProduct
     * 
     * @param user
     * @param bean
     * @throws MYException
     */
    private void executeOnUpdateProduct(User user, ProductBean bean)
        throws MYException
    {
        // 实现监听
        Collection<ProductListener> values = this.listenerMapValues();

        for (ProductListener productListener : values)
        {
            productListener.onUpdateProduct(user, bean);
        }
    }

    /**
     * createSpell(更新简拼)
     * 
     * @param bean
     */
    private void createSpell(ProductBean bean)
    {
        bean.setFullspell(JPTools.createFullSpell(bean.getName()));

        bean.setShortspell(JPTools.createShortSpell(bean.getName()));
    }

    /**
     * 产品编码规则：<br>
     * 第一个字母分为下面几种，虚拟产品以v开头，物料s开头，合成后的产品是c开头，不需要模型计算的产品以w开头,普通产品是p开头。<br>
     * 第二个字母是产品类型纸币是z，金银币是j，古币是g，邮票是y，其他类型是q<br>
     * 第三个字母是销售类型，自卖的是s，代销的是i，<br>
     * 第四个预留成a<br>
     * 后面是8位的产品数字编码<br>
     */
    private void createCode(ProductBean bean)
    {
        String fl = "1";

        if (bean.getAbstractType() == ProductConstant.ABSTRACT_TYPE_YES)
        {
            fl = "2";
        }
        else if (bean.getPtype() == ProductConstant.PTYPE_WULIAO)
        {
            fl = "3";
        }
        else if (bean.getCtype() == ProductConstant.CTYPE_YES)
        {
            fl = "4";
        }
        else
        {
            fl = "1";
        }

        String sl = "1";

        switch (bean.getType())
        {
            case ProductConstant.PRODUCT_TYPE_OTHER:
                sl = "9";
                break;
            case ProductConstant.PRODUCT_TYPE_PAPER:
                sl = "1";
                break;
            case ProductConstant.PRODUCT_TYPE_METAL:
                sl = "2";
                break;
            case ProductConstant.PRODUCT_TYPE_NUMISMATICS:
                sl = "3";
                break;
            case ProductConstant.PRODUCT_TYPE_STAMP:
                sl = "4";
                break;
            default:
                sl = "9";
                break;
        }

        String tl = "1";

        if (bean.getSailType() == ProductConstant.SAILTYPE_SELF)
        {
            tl = "2";
        }
        else
        {
            tl = "1";
        }

        String fol = "1";

        // 10位
        String squenceString = StringTools.formatString(bean.getId(), 5);

        String code = fl + sl + tl + fol + squenceString;

        bean.setCode(code);
    }

    /**
     * 
     * {@inheritDoc}
     */
    @Transactional(rollbackFor = MYException.class)
	public boolean importCiticProduct(User user, List<CiticVSOAProductBean> list)
	throws MYException
	{
    	JudgeTools.judgeParameterIsNull(user, list);
    	
    	citicVSOAProductDAO.saveAllEntityBeans(list);
    	
		return true;
	}
    
    private String getPicPath()
    {
        return ConfigLoader.getProperty("picPath");
    }
    
    @Transactional(rollbackFor = MYException.class)
    public boolean deleteCiticProductBean(User user, String id)
    throws MYException {
    	JudgeTools.judgeParameterIsNull(user, id);
    	
    	citicVSOAProductDAO.deleteEntityBean(id);
    	
    	return true;
    }

    /**
     * 
     * {@inheritDoc}
     */
	@Transactional(rollbackFor = MYException.class)
	public boolean configGoldSilverPrice(User user, GoldSilverPriceBean bean)
			throws MYException
	{
		JudgeTools.judgeParameterIsNull(user, bean);
		
		// 始终只会只有一条数据
		goldSilverPriceDAO.deleteAllEntityBean();
		
		goldSilverPriceDAO.saveEntityBean(bean);
		
		return true;
	}
    
    /**
     * @return the productCombinationDAO
     */
    public ProductCombinationDAO getProductCombinationDAO()
    {
        return productCombinationDAO;
    }

    /**
     * @param productCombinationDAO
     *            the productCombinationDAO to set
     */
    public void setProductCombinationDAO(ProductCombinationDAO productCombinationDAO)
    {
        this.productCombinationDAO = productCombinationDAO;
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
     * @return the productVSLocationDAO
     */
    public ProductVSLocationDAO getProductVSLocationDAO()
    {
        return productVSLocationDAO;
    }

    /**
     * @param productVSLocationDAO
     *            the productVSLocationDAO to set
     */
    public void setProductVSLocationDAO(ProductVSLocationDAO productVSLocationDAO)
    {
        this.productVSLocationDAO = productVSLocationDAO;
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
     * @return the notifyManager
     */
    public NotifyManager getNotifyManager()
    {
        return notifyManager;
    }

    /**
     * @param notifyManager
     *            the notifyManager to set
     */
    public void setNotifyManager(NotifyManager notifyManager)
    {
        this.notifyManager = notifyManager;
    }

	public GoldSilverPriceDAO getGoldSilverPriceDAO()
	{
		return goldSilverPriceDAO;
	}

	public void setGoldSilverPriceDAO(GoldSilverPriceDAO goldSilverPriceDAO)
	{
		this.goldSilverPriceDAO = goldSilverPriceDAO;
	}

	/**
	 * @return the citicVSOAProductDAO
	 */
	public CiticVSOAProductDAO getCiticVSOAProductDAO()
	{
		return citicVSOAProductDAO;
	}

	/**
	 * @param citicVSOAProductDAO the citicVSOAProductDAO to set
	 */
	public void setCiticVSOAProductDAO(CiticVSOAProductDAO citicVSOAProductDAO)
	{
		this.citicVSOAProductDAO = citicVSOAProductDAO;
	}
}
