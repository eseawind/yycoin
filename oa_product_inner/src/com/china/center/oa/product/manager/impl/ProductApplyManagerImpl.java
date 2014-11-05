package com.china.center.oa.product.manager.impl;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.transaction.annotation.Transactional;

import com.center.china.osgi.publics.AbstractListenerManager;
import com.center.china.osgi.publics.User;
import com.china.center.common.MYException;
import com.china.center.jdbc.expression.Expression;
import com.china.center.oa.product.bean.ProductApplyBean;
import com.china.center.oa.product.bean.ProductBOMBean;
import com.china.center.oa.product.bean.ProductBean;
import com.china.center.oa.product.bean.ProductSubApplyBean;
import com.china.center.oa.product.bean.ProductVSStafferBean;
import com.china.center.oa.product.constant.ProductApplyConstant;
import com.china.center.oa.product.constant.ProductConstant;
import com.china.center.oa.product.dao.ProductApplyDAO;
import com.china.center.oa.product.dao.ProductBOMDAO;
import com.china.center.oa.product.dao.ProductCombinationDAO;
import com.china.center.oa.product.dao.ProductDAO;
import com.china.center.oa.product.dao.ProductSubApplyDAO;
import com.china.center.oa.product.dao.ProductVSStafferDAO;
import com.china.center.oa.product.dao.SequenceDAO;
import com.china.center.oa.product.listener.ProductApplyListener;
import com.china.center.oa.product.manager.ProductApplyManager;
import com.china.center.oa.product.vs.ProductCombinationBean;
import com.china.center.oa.publics.bean.FlowLogBean;
import com.china.center.oa.publics.dao.CommonDAO;
import com.china.center.oa.publics.dao.FlowLogDAO;
import com.china.center.tools.JPTools;
import com.china.center.tools.JudgeTools;
import com.china.center.tools.ListTools;
import com.china.center.tools.StringTools;
import com.china.center.tools.TimeTools;

public class ProductApplyManagerImpl extends AbstractListenerManager<ProductApplyListener>
        implements ProductApplyManager {

    private final Log             operationLog          = LogFactory.getLog("opr");

    private ProductApplyDAO       productApplyDAO       = null;

    private ProductSubApplyDAO    productSubApplyDAO    = null;

    private ProductVSStafferDAO   productVSStafferDAO   = null;

    private FlowLogDAO            flowLogDAO            = null;

    private CommonDAO             commonDAO             = null;

    private ProductDAO            productDAO            = null;
    
    private SequenceDAO sequenceDAO = null;

    private ProductCombinationDAO productCombinationDAO = null;
    
    private ProductBOMDAO productBOMDAO = null;
    
    private static Object lock = new Object();

    public ProductApplyManagerImpl() {

    }

    @Override
    @Transactional(rollbackFor = MYException.class)
    public boolean addProductApply(User user, ProductApplyBean bean) throws MYException {

        JudgeTools.judgeParameterIsNull(user, bean);

        // 获取ID
        String id = commonDAO.getSquenceString();

        bean.setId(id);

        int oldStatus = bean.getStatus();

        if (oldStatus == ProductApplyConstant.STATUS_SUBMIT) {
            bean.setStatus(ProductApplyConstant.STATUS_DEPARTMENTAPPLY);
        }

        checkUnique(bean);

        // exp.check("#code &unique @productApplyDAO", "编码已经存在");

        productApplyDAO.saveEntityBean(bean);

        List<ProductSubApplyBean> subList = bean.getProductSubApplyList();

        if (!ListTools.isEmptyOrNull(subList)) {
            for (ProductSubApplyBean each : subList) {
                each.setRefId(id);

                productSubApplyDAO.saveEntityBean(each);
            }
        }

        List<ProductVSStafferBean> vsList = bean.getVsList();

        if (!ListTools.isEmptyOrNull(vsList)) {
            for (ProductVSStafferBean each : vsList) {
                each.setRefId(id);

                productVSStafferDAO.saveEntityBean(each);
            }
        }

        // 增加数据库日志
        if (oldStatus == ProductApplyConstant.STATUS_SAVE)
            addLog(id, user, bean, "保存", ProductApplyConstant.OPRMODE_SAVE,
                    ProductApplyConstant.STATUS_SUBMIT);
        else {
            bean.setStatus(oldStatus);

            addLog(id, user, bean, "提交", ProductApplyConstant.OPRMODE_SUBMIT,
                    ProductApplyConstant.STATUS_DEPARTMENTAPPLY);
        }

        return true;
    }

    /**
     * checkUnique
     * 
     * @param bean
     * @throws MYException
     */
    private void checkUnique(ProductApplyBean bean) throws MYException 
    {
        ProductBean productBean = new ProductBean();
        
        productBean.setName(bean.getName());
        
        Expression exp1 = new Expression(productBean, this);

        exp1.check("#name &unique @productDAO", "名称已经存在");
        
        if (bean.getNature() == ProductApplyConstant.NATURE_SINGLE)
	    {
	    	// 根据成品，获取配件的编码
	    	String refProductId = bean.getRefProductId();
	    	
	    	if (StringTools.isNullOrNone(refProductId))
	    	{
	    		throw new MYException("配件产品时要有成品产品关联");
	    	}
	    	
	    	ProductBean product = productDAO.find(refProductId);
	    	
	    	if (null == product)
	    	{
	    		throw new MYException("关联的成品不存在");
	    	}
	    	
	    	String masterMidName = product.getMidName();
	    	
	    	if (StringTools.isNullOrNone(masterMidName))
	    	{
	    		throw new MYException("新模式产生的成品才能建配件产品");
	    	}
	    }
        
/*        Expression exp = new Expression(bean, this);

        exp.check("#name &unique @productApplyDAO", "名称已经存在");*/
    }


    @Transactional(rollbackFor = MYException.class)
    public boolean updateProductApply(User user, ProductApplyBean bean) throws MYException {

        JudgeTools.judgeParameterIsNull(user, bean);

        String id = bean.getId();

        ProductApplyBean old = productApplyDAO.find(id);

        if (null == old) {
            throw new MYException("数据错误");
        }
                
        checkUnique(bean);
        
        operationLog.info("updateProductBean productApplyBean old:" + old);
        
        if (old.getStatus() != ProductApplyConstant.STATUS_SAVE && old.getStatus() != ProductApplyConstant.STATUS_REJECT)
        {
        	throw new MYException("只能修改保存、驳回状态申请");
        }
        
        if (bean.getStatus() == ProductApplyConstant.STATUS_SUBMIT) {
            bean.setStatus(ProductApplyConstant.STATUS_DEPARTMENTAPPLY);
        }
        
        productApplyDAO.updateEntityBean(bean);

        List<ProductSubApplyBean> subList = bean.getProductSubApplyList();

        productSubApplyDAO.deleteEntityBeansByFK(id);

        if (!ListTools.isEmptyOrNull(subList)) {
            for (ProductSubApplyBean subBean : subList) {
                subBean.setRefId(id);

                productSubApplyDAO.saveEntityBean(subBean);
            }
        }

        List<ProductVSStafferBean> vsList = bean.getVsList();

        productVSStafferDAO.deleteEntityBeansByFK(id);

        if (!ListTools.isEmptyOrNull(vsList)) {
            for (ProductVSStafferBean each : vsList) {
                each.setRefId(id);

                productVSStafferDAO.saveEntityBean(each);
            }
        }

        // 增加数据库日志
        if (bean.getStatus() == ProductApplyConstant.STATUS_SAVE)
            addLog(id, user, bean, "保存", ProductApplyConstant.OPRMODE_SAVE,
                    ProductApplyConstant.STATUS_SUBMIT);
        else {
            bean.setStatus(bean.getStatus());

            addLog(id, user, bean, "提交", ProductApplyConstant.OPRMODE_SUBMIT,
                    ProductApplyConstant.STATUS_DEPARTMENTAPPLY);
        }

        operationLog.info("updateProductBean productApplyBean new :" + bean);

        return true;
    }

    @Override
    @Transactional(rollbackFor = MYException.class)
    public boolean deleteProductApply(User user, String id) throws MYException {

        JudgeTools.judgeParameterIsNull(user, id);

        ProductApplyBean pab = productApplyDAO.find(id);

        if (null == pab) {
            throw new MYException("数据错误，ID=" + id + "产品没有找到，请联系管理员");
        }

        int status = pab.getStatus();

        if (status != ProductApplyConstant.STATUS_SAVE
                && status != ProductApplyConstant.STATUS_REJECT) {
            throw new MYException("非保存或驳回状态，不允许删除");
        }

        productApplyDAO.deleteEntityBean(id);

        productSubApplyDAO.deleteEntityBeansByFK(id);

        productVSStafferDAO.deleteEntityBeansByFK(id);

        operationLog.info("Delete ProductApplyBean, id:" + id);

        return true;
    }

    @Override
    @Transactional(rollbackFor = MYException.class)
    public boolean passProductApply(User user, ProductApplyBean bean) throws MYException {

        JudgeTools.judgeParameterIsNull(user, bean);

        String id = bean.getId();

        ProductApplyBean pab = productApplyDAO.find(id);

        if (null == pab) {
            throw new MYException("数据错误，ID=" + id + "产品没有找到，请联系管理员");
        }

        int status = bean.getStatus();

        if (status != ProductApplyConstant.STATUS_DEPARTMENTAPPLY) {
            throw new MYException("当前状态不是待部门审批状态,请联系管理员");
        }

        productApplyDAO.modifyProductApplyStatus(id, ProductApplyConstant.STATUS_PRODUCTAPPLY);

        // add log
        addLog(id, user, bean, "通过", ProductApplyConstant.OPRMODE_PASS,
                ProductApplyConstant.STATUS_PRODUCTAPPLY);

        return true;
    }

    @Override
    @Transactional(rollbackFor = MYException.class)
    public boolean pass1ProductApply(User user, ProductApplyBean bean) throws MYException {

        JudgeTools.judgeParameterIsNull(user, bean);

        synchronized (lock)
		{
        	String id = bean.getId();

            ProductApplyBean applyBean = productApplyDAO.find(id);

            if (null == applyBean) {
                throw new MYException("数据错误，ID=" + id + "产品没有找到，请联系管理员");
            }

            int status = bean.getStatus();

            if (status != ProductApplyConstant.STATUS_PRODUCTAPPLY) {
                throw new MYException("当前状态不是待产品管理中心审批状态,请联系管理员");
            }

            createFullName(applyBean);
            
            createCode(applyBean);

            createSpell(applyBean);

            applyBean.setStatus(ProductApplyConstant.STATUS_FINISHED);

            productApplyDAO.updateEntityBean(applyBean);

            // 申请审批结束后产品转至正式商品表
            transferToProductBean(id, applyBean);
            
            addLog(id, user, bean, "通过", ProductApplyConstant.OPRMODE_PASS,
                    ProductApplyConstant.STATUS_FINISHED);
		}
        
        return true;
    }

	private void createFullName(ProductApplyBean bean) throws MYException
	{
		// 组合产品全名
	    String firstName = "";
	    
	    String sailType = "";
	    
	    switch (bean.getChannelType())
	    {
	        case ProductConstant.SAILTYPE_SELF:
	        	sailType = "Z";
	            break;
	        case ProductConstant.SAILTYPE_REPLACE:
	        	sailType = "G";
	            break;
	        case ProductConstant.SAILTYPE_CUSTOMER:
	        	sailType = "D";
	            break;
	        case ProductConstant.SAILTYPE_OTHER:
	        	sailType = "S";
	            break;
	        default:
	        	sailType = "";
	            break;
	    }
	    
	    if (StringTools.isNullOrNone(sailType))
	    {
	    	throw new MYException("渠道数据错误");
	    }
	    
	    firstName = "Y" + sailType;
	    
	    bean.setFirstName(firstName);
	    
	    String midName = "";
	    
	    // midName，需确定是成品还是配件，如果是成品，生成5位的流水， 如果是配件，获取成品midName，并取出后2位，并增1. 
	    // 同步synchronized,且在审批最后产生
	    if (bean.getNature() == ProductApplyConstant.NATURE_COMPOSE)
	    {
	    	midName = sequenceDAO.getSquenceString5(sailType) + "00";
	    }else
	    {
	    	// 根据成品，获取配件的编码
	    	String refProductId = bean.getRefProductId();
	    	
	    	if (StringTools.isNullOrNone(refProductId))
	    	{
	    		throw new MYException("配件产品时要有成品产品关联");
	    	}
	    	
	    	ProductBean product = productDAO.find(refProductId);
	    	
	    	if (null == product)
	    	{
	    		throw new MYException("关联的成品不存在");
	    	}
	    	
	    	String masterMidName = product.getMidName();
	    	
	    	if (StringTools.isNullOrNone(masterMidName))
	    	{
	    		throw new MYException("新模式产生的成品才能建配件产品");
	    	}
	    	
	    	// 取配件产品midName 最大值 
	    	int maxMidName = productDAO.getmaxMidName(refProductId);
	    	
	    	if (maxMidName == 0)
	    	{
	    		midName = masterMidName.substring(0, 5) + "01";
	    	}else{
	    		midName = StringTools.formatString(String.valueOf(maxMidName + 1), 7);
	    	}
	    }
	    
	    String fullName = firstName + midName + " " + bean.getName();
	    
	    bean.setMidName(midName);
	    
	    bean.setFullName(fullName);
	}

    /**
     * transferToProductBean
     * 
     * @param id
     * @param applyBean
     */
    private void transferToProductBean(String id, ProductApplyBean applyBean) throws MYException
    {
        // 申请审批完成后，产品转至正式产品表
        ProductBean productBean = new ProductBean();

        productBean.setId(applyBean.getId());
        productBean.setName(applyBean.getFullName());
        productBean.setFullspell(applyBean.getFullspell());
        productBean.setShortspell(applyBean.getShortspell());
        productBean.setCode(applyBean.getCode());
        productBean.setCtype(applyBean.getNature());
        productBean.setType(applyBean.getType());
        productBean.setStockType(applyBean.getSalePeriod());
        productBean.setCheckDays(applyBean.getMateriaType());
        productBean.setMaxStoreDays(applyBean.getCultrueType());
        productBean.setSafeStoreDays(applyBean.getDiscountRate());
        productBean.setMakeDays(applyBean.getPriceRange());
        productBean.setFlowDays(applyBean.getSaleTarget());
        productBean.setMinAmount(applyBean.getCurrencyType());
        productBean.setConsumeInDay(applyBean.getSecondhandGoods());
        productBean.setOrderAmount(applyBean.getStyle());
        productBean.setAssistantProvider2(applyBean.getCommissionBeginDate());
        productBean.setAssistantProvider3(applyBean.getCommissionEndDate());
        productBean.setAssistantProvider4(applyBean.getProductManagerId());
        productBean.setSailType(applyBean.getChannelType());
        productBean.setAdjustPrice(applyBean.getCountry());
        productBean.setLogTime(TimeTools.now());
        productBean.setCreaterId(applyBean.getOprId());
        productBean.setDescription(applyBean.getDescription());
        productBean.setReserve4(String.valueOf(applyBean.getManagerType()));
        productBean.setReserve6(applyBean.getIndustryId());
        productBean.setStatus(ProductConstant.STATUS_COMMON);
        productBean.setCost(applyBean.getGold());
        productBean.setPlanCost(applyBean.getSilver());
        productBean.setMidName(applyBean.getMidName());
        productBean.setRefProductId(applyBean.getRefProductId()); // 配件产品时关联的成品产品
        productBean.setDutyType(applyBean.getDutyType());
        productBean.setInputInvoice(applyBean.getInputInvoice());
        productBean.setSailInvoice(applyBean.getSailInvoice());

        Expression exp = new Expression(productBean, this);

        exp.check("#name &unique @productDAO", "名称已经存在");

        exp.check("#code &unique @productDAO", "编码已经存在");
        
        productDAO.saveEntityBean(productBean);
        
        List<ProductSubApplyBean> productSubApplyList = productSubApplyDAO.queryEntityBeansByFK(id);

        if (!ListTools.isEmptyOrNull(productSubApplyList) 
                && applyBean.getNature() == ProductApplyConstant.NATURE_COMPOSE)
        {
            for (ProductSubApplyBean each : productSubApplyList)
            {
                ProductCombinationBean cBean = new ProductCombinationBean();
                
                cBean.setVproductId(id);
                cBean.setSproductId(each.getSubProductId());
                cBean.setAmount(each.getSubProductAmount());
                
                productCombinationDAO.saveEntityBean(cBean);
            }
        }
        
        // 如果有关联产品，则自动增加到BOM表中
        if (!StringTools.isNullOrNone(productBean.getRefProductId())) {
        	ProductBOMBean bom = new ProductBOMBean();
        	
        	bom.setProductId(productBean.getRefProductId());
        	bom.setSubProductId(productBean.getId());
        	
        	productBOMDAO.saveEntityBean(bom);
        }
    }

    @Override
    @Transactional(rollbackFor = MYException.class)
    public boolean rejectProductApply(User user, ProductApplyBean bean) throws MYException {

        JudgeTools.judgeParameterIsNull(user, bean);

        String id = bean.getId();

        ProductApplyBean pab = productApplyDAO.find(id);

        if (null == pab) {
            throw new MYException("数据错误，ID=" + id + "产品没有找到，请联系管理员");
        }

        int status = bean.getStatus();

        if (status == ProductApplyConstant.STATUS_FINISHED) {
            throw new MYException("结束状态不允许驳回");
        }

        productApplyDAO.modifyProductApplyStatus(id, ProductApplyConstant.STATUS_REJECT);

        // add log
        addLog(id, user, bean, "驳回", ProductApplyConstant.OPRMODE_REJECT,
                ProductApplyConstant.STATUS_REJECT);

        return true;
    }

    /**
     * 
     * @param bean
     */
    private void createCode(ProductApplyBean bean) {
    	
        String code = commonDAO.getSquenceString();

        bean.setCode(code);
    }

    /**
     * createSpell(更新简拼)
     * 
     * @param bean
     */
    private void createSpell(ProductApplyBean bean) {
        bean.setFullspell(JPTools.createFullSpell(bean.getFullName()));

        bean.setShortspell(JPTools.createShortSpell(bean.getFullName()));
    }

    private void addLog(final String fullId, final User user, final ProductApplyBean bean,
            String des, int mode, int astatus) {
        FlowLogBean log = new FlowLogBean();

        log.setActor(user.getStafferName());

        log.setDescription(des);
        log.setFullId(fullId);
        log.setOprMode(mode);
        log.setLogTime(TimeTools.now());

        log.setPreStatus(bean.getStatus());

        log.setAfterStatus(astatus);

        flowLogDAO.saveEntityBean(log);
    }

    public ProductApplyDAO getProductApplyDAO() {
        return productApplyDAO;
    }

    public void setProductApplyDAO(ProductApplyDAO productApplyDAO) {
        this.productApplyDAO = productApplyDAO;
    }

    public ProductSubApplyDAO getProductSubApplyDAO() {
        return productSubApplyDAO;
    }

    public void setProductSubApplyDAO(ProductSubApplyDAO productSubApplyDAO) {
        this.productSubApplyDAO = productSubApplyDAO;
    }

    public ProductVSStafferDAO getProductVSStafferDAO() {
        return productVSStafferDAO;
    }

    public void setProductVSStafferDAO(ProductVSStafferDAO productVSStafferDAO) {
        this.productVSStafferDAO = productVSStafferDAO;
    }

    public FlowLogDAO getFlowLogDAO() {
        return flowLogDAO;
    }

    public void setFlowLogDAO(FlowLogDAO flowLogDAO) {
        this.flowLogDAO = flowLogDAO;
    }

    public CommonDAO getCommonDAO() {
        return commonDAO;
    }

    public void setCommonDAO(CommonDAO commonDAO) {
        this.commonDAO = commonDAO;
    }

    public ProductDAO getProductDAO() {
        return productDAO;
    }

    public void setProductDAO(ProductDAO productDAO) {
        this.productDAO = productDAO;
    }

    public ProductCombinationDAO getProductCombinationDAO() {
        return productCombinationDAO;
    }

    public void setProductCombinationDAO(ProductCombinationDAO productCombinationDAO) {
        this.productCombinationDAO = productCombinationDAO;
    }

	/**
	 * @return the sequenceDAO
	 */
	public SequenceDAO getSequenceDAO()
	{
		return sequenceDAO;
	}

	/**
	 * @param sequenceDAO the sequenceDAO to set
	 */
	public void setSequenceDAO(SequenceDAO sequenceDAO)
	{
		this.sequenceDAO = sequenceDAO;
	}

	/**
	 * @return the productBOMDAO
	 */
	public ProductBOMDAO getProductBOMDAO() {
		return productBOMDAO;
	}

	/**
	 * @param productBOMDAO the productBOMDAO to set
	 */
	public void setProductBOMDAO(ProductBOMDAO productBOMDAO) {
		this.productBOMDAO = productBOMDAO;
	}
}
