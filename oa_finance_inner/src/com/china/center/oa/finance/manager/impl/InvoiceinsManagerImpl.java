/**
 * File Name: InvoiceinsManagerImpl.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2011-1-1<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.finance.manager.impl;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.china.center.spring.ex.annotation.Exceptional;
import org.springframework.dao.DataAccessException;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionException;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

import com.center.china.osgi.publics.AbstractListenerManager;
import com.center.china.osgi.publics.User;
import com.china.center.common.MYException;
import com.china.center.jdbc.annosql.constant.AnoConstant;
import com.china.center.jdbc.util.ConditionParse;
import com.china.center.oa.finance.bean.InsImportLogBean;
import com.china.center.oa.finance.bean.InsVSInvoiceNumBean;
import com.china.center.oa.finance.bean.InvoiceBindOutBean;
import com.china.center.oa.finance.bean.InvoiceStorageBean;
import com.china.center.oa.finance.bean.InvoiceinsBean;
import com.china.center.oa.finance.bean.InvoiceinsImportBean;
import com.china.center.oa.finance.bean.InvoiceinsItemBean;
import com.china.center.oa.finance.bean.InvoiceinsTagBean;
import com.china.center.oa.finance.bean.StockPayApplyBean;
import com.china.center.oa.finance.constant.FinanceConstant;
import com.china.center.oa.finance.constant.InvoiceinsConstants;
import com.china.center.oa.finance.constant.StockPayApplyConstant;
import com.china.center.oa.finance.dao.InsImportLogDAO;
import com.china.center.oa.finance.dao.InsVSInvoiceNumDAO;
import com.china.center.oa.finance.dao.InsVSOutDAO;
import com.china.center.oa.finance.dao.InvoiceBindOutDAO;
import com.china.center.oa.finance.dao.InvoiceStorageDAO;
import com.china.center.oa.finance.dao.InvoiceinsDAO;
import com.china.center.oa.finance.dao.InvoiceinsDetailDAO;
import com.china.center.oa.finance.dao.InvoiceinsImportDAO;
import com.china.center.oa.finance.dao.InvoiceinsItemDAO;
import com.china.center.oa.finance.dao.InvoiceinsTagDAO;
import com.china.center.oa.finance.dao.StockPayApplyDAO;
import com.china.center.oa.finance.listener.InvoiceinsListener;
import com.china.center.oa.finance.manager.InvoiceinsManager;
import com.china.center.oa.finance.manager.PackageManager;
import com.china.center.oa.finance.vo.InvoiceinsVO;
import com.china.center.oa.finance.vs.InsVSOutBean;
import com.china.center.oa.product.bean.ProductBean;
import com.china.center.oa.product.constant.ProductConstant;
import com.china.center.oa.product.dao.ProductDAO;
import com.china.center.oa.publics.bean.AttachmentBean;
import com.china.center.oa.publics.bean.DutyBean;
import com.china.center.oa.publics.bean.FlowLogBean;
import com.china.center.oa.publics.constant.InvoiceConstant;
import com.china.center.oa.publics.constant.PublicConstant;
import com.china.center.oa.publics.constant.StafferConstant;
import com.china.center.oa.publics.dao.AttachmentDAO;
import com.china.center.oa.publics.dao.CommonDAO;
import com.china.center.oa.publics.dao.DutyDAO;
import com.china.center.oa.publics.dao.FlowLogDAO;
import com.china.center.oa.sail.bean.BaseBalanceBean;
import com.china.center.oa.sail.bean.BaseBean;
import com.china.center.oa.sail.bean.DistributionBean;
import com.china.center.oa.sail.bean.OutBalanceBean;
import com.china.center.oa.sail.bean.OutBean;
import com.china.center.oa.sail.bean.PreConsignBean;
import com.china.center.oa.sail.constanst.OutConstant;
import com.china.center.oa.sail.constanst.OutImportConstant;
import com.china.center.oa.sail.dao.BaseBalanceDAO;
import com.china.center.oa.sail.dao.BaseDAO;
import com.china.center.oa.sail.dao.DistributionDAO;
import com.china.center.oa.sail.dao.OutBalanceDAO;
import com.china.center.oa.sail.dao.OutDAO;
import com.china.center.oa.sail.dao.PreConsignDAO;
import com.china.center.tools.BeanUtil;
import com.china.center.tools.JudgeTools;
import com.china.center.tools.ListTools;
import com.china.center.tools.MathTools;
import com.china.center.tools.StringTools;
import com.china.center.tools.TimeTools;


/**
 * InvoiceinsManagerImpl
 * 
 * @author ZHUZHU
 * @version 2011-1-1
 * @see InvoiceinsManagerImpl
 * @since 3.0
 */
@Exceptional
public class InvoiceinsManagerImpl extends AbstractListenerManager<InvoiceinsListener> implements InvoiceinsManager
{
    private final Log operationLog = LogFactory.getLog("opr");

    private CommonDAO commonDAO = null;

    private InvoiceinsDAO invoiceinsDAO = null;

    private InvoiceinsItemDAO invoiceinsItemDAO = null;

    private OutBalanceDAO outBalanceDAO = null;

    private InsVSOutDAO insVSOutDAO = null;

    private OutDAO outDAO = null;

    private FlowLogDAO flowLogDAO = null;

    private BaseDAO baseDAO = null;

    private DutyDAO dutyDAO = null;

    private BaseBalanceDAO baseBalanceDAO = null;
    
    private InvoiceStorageDAO invoiceStorageDAO = null;
    
    private InvoiceBindOutDAO invoiceBindOutDAO = null;
    
    private StockPayApplyDAO stockPayApplyDAO = null;
    
    private InvoiceinsDetailDAO invoiceinsDetailDAO = null;
    
    private InsVSInvoiceNumDAO insVSInvoiceNumDAO = null;
    
    private InvoiceinsTagDAO invoiceinsTagDAO = null;
    
    private InvoiceinsImportDAO invoiceinsImportDAO = null;
    
    private InsImportLogDAO insImportLogDAO = null;
    
    private AttachmentDAO attachmentDAO = null;
    
    private ProductDAO productDAO = null;
    
    private DistributionDAO distributionDAO = null;
    
    private PreConsignDAO preConsignDAO = null;
    
    private PackageManager packageManager = null;
    
    private PlatformTransactionManager transactionManager = null;

    /*
     * (non-Javadoc)
     * 
     * @see com.china.center.oa.finance.manager.InvoiceinsManager#addInvoiceinsBean(com.center.china.osgi.publics.User,
     *      com.china.center.oa.finance.bean.InvoiceinsBean)
     */
    @Transactional(rollbackFor = MYException.class)
    public boolean addInvoiceinsBean(User user, InvoiceinsBean bean)
        throws MYException
    {
        JudgeTools.judgeParameterIsNull(user, bean);

        boolean update = true;
        
        String id = bean.getId();
        
        if (StringTools.isNullOrNone(id))
        {
        	update = false;
        	
        	id = commonDAO.getSquenceString20();
        }
        
        bean.setId(id);

        bean.setLogTime(TimeTools.now());
        bean.setStafferId(user.getStafferId());

        DutyBean duty = dutyDAO.find(bean.getDutyId());

        if (duty == null)
        {
            throw new MYException("数据错误,请确认操作");
        }

        // 再检查一次商品的管理属性与开票类型是否匹配
        checkProductAndInvoiceAttr(bean);
        
        // check 未发货状态销售单是否一次性开完发票
        for (InsVSOutBean insVSOutBean : bean.getVsList())
        {
            if (insVSOutBean.getType() == FinanceConstant.INSVSOUT_TYPE_OUT)
            {
            	// 针对未发货状态的开票申请，要一次性开完，不能部分开票
            	OutBean outBean = outDAO.find(insVSOutBean.getOutId());
            	
            	if (null != outBean) {
            		if (outBean.getStatus() != OutConstant.STATUS_PASS
            				&& outBean.getStatus() != OutConstant.STATUS_SEC_PASS) {
            			double ret = outDAO.sumOutBackValue(outBean.getFullId());
            			
            			if (!MathTools.equal(outBean.getTotal() - ret, outBean.getInvoiceMoney() + insVSOutBean.getMoneys())){
            				throw new MYException("销售单[%s]是未发货状态，须一次性开完发票.");
            			}
            		}
            	}
            }
        }
        
        // 对分公司的直接OK
        if (bean.getType() == FinanceConstant.INVOICEINS_TYPE_DUTY)
        {
            bean.setStatus(FinanceConstant.INVOICEINS_STATUS_END);

            if ( !bean.getInvoiceId().equals(InvoiceConstant.INVOICE_INSTACE_DK_17))
            {
                throw new MYException("发票只能是:增值专用发票(一般纳税人)[可抵扣](17.00%)");
            }
        }
        else
        {
            // 设置状态
            fillStatus(bean);
        }

        bean.setMtype(duty.getMtype());
        
        setDistFromDist(bean);

        if (update)
        {
        	invoiceinsDAO.updateEntityBean(bean);
        }else
        {
        	invoiceinsDAO.saveEntityBean(bean);
        }

        invoiceinsItemDAO.deleteEntityBeansByFK(id);
        insVSOutDAO.deleteEntityBeansByFK(id, AnoConstant.FK_FIRST);
        
        List<InvoiceinsItemBean> itemList = bean.getItemList();

        for (InvoiceinsItemBean invoiceinsItemBean : itemList)
        {
            invoiceinsItemBean.setId(commonDAO.getSquenceString20());

            invoiceinsItemBean.setParentId(bean.getId());
        }

        invoiceinsItemDAO.saveAllEntityBeans(itemList);

        List<InsVSOutBean> vsList = bean.getVsList();

        if ( !ListTools.isEmptyOrNull(vsList))
        {
            for (InsVSOutBean insVSOutBean : vsList)
            {
                insVSOutBean.setId(commonDAO.getSquenceString20());
                
                insVSOutBean.setInsId(bean.getId());
                
                if (insVSOutBean.getType() == 1)
                {
                	OutBalanceBean balanceBean = outBalanceDAO.find(insVSOutBean.getOutId());
                	
                	insVSOutBean.setOutId(balanceBean.getOutId());
                	insVSOutBean.setOutBalanceId(balanceBean.getId());
                }
            }

            insVSOutDAO.saveAllEntityBeans(vsList);
            
            processInvoicePay(duty, vsList, 0);
        }
        
        List<AttachmentBean> attachmentList = bean.getAttachmentList();

        for (AttachmentBean attachmentBean : attachmentList) {
            attachmentBean.setId(commonDAO.getSquenceString20());
            attachmentBean.setRefId(bean.getId());
        }

        attachmentDAO.saveAllEntityBeans(attachmentList);
        
        // 这里仅仅是提交,审核通过后才能修改单据的状态
        return true;
    }

    /**
     * fillType 1 地址同原销售单地址
     * @param bean
     */
	private void setDistFromDist(InvoiceinsBean bean) {
		DistributionBean dist = null;
		
		if (bean.getFillType() == 1) {
        	InvoiceinsItemBean item = bean.getItemList().get(0);
        	
        	String outId = item.getOutId();
        	
        	if (item.getType() == FinanceConstant.INSVSOUT_TYPE_BALANCE) {
        		OutBalanceBean balance = outBalanceDAO.find(item.getOutId());
        		
        		outId = balance.getOutId();
        	}
        	
        	List<DistributionBean> dList = distributionDAO.queryEntityBeansByFK(outId);
        	
        	dist = dList.get(0);
        	
        	bean.setShipping(dist.getShipping());
        	bean.setTransport1(dist.getTransport1());
        	bean.setTransport2(dist.getTransport2());
        	bean.setProvinceId(dist.getProvinceId());
        	bean.setCityId(dist.getCityId());
        	bean.setAreaId(dist.getAreaId());
        	bean.setAddress(dist.getAddress());
        	bean.setReceiver(dist.getReceiver());
        	bean.setMobile(dist.getMobile());
        	bean.setTelephone(dist.getTelephone());
        	bean.setExpressPay(dist.getExpressPay());
        	bean.setTransportPay(dist.getTransportPay());
        } else {
        	dist = new DistributionBean();
        	
        	BeanUtil.copyProperties(dist, bean);
        }
		
		dist.setId(commonDAO.getSquenceString20("PS"));
		dist.setOutId(bean.getId());
		dist.setDescription("发票配送");
		
		distributionDAO.deleteEntityBeansByFK(bean.getId());
		
		distributionDAO.saveEntityBean(dist);
	}

    /**
     *  // 普通+旧货=增值税普通发票（旧货）90000000000000000007
     *  // 普通+非旧货=增值税专用发票17 90000000000000000003
     *  // 普通+零税率=增值普通发票(0.00%) 90000000000000000004
     * @param itemList
     * @throws MYException
     */
    private void checkProductAndInvoiceAttr(InvoiceinsBean bean) throws MYException {
    	String invoiceId = bean.getInvoiceId();
    	
    	List<InvoiceinsItemBean> itemList = bean.getItemList();
    	
    	for (InvoiceinsItemBean each : itemList) {
			checkProductAttrInner(invoiceId, each.getProductId());
    	}
    }

	private void checkProductAttrInner(String invoiceId, String productId)
			throws MYException {
		ProductBean product = productDAO.find(productId);
		
		if (null == product) {
			throw new MYException("数据错误");
		} else {
			String mtype = product.getReserve4();
			int oldgoods = product.getConsumeInDay();
			
			if ("1".equals(mtype) && oldgoods == ProductConstant.PRODUCT_OLDGOOD) {
				if (!invoiceId.equals("90000000000000000007")) {
					throw new MYException("普通且是旧货的商品只能开具增值税普通发票（旧货）类型发票");
				}
			}
			
			if ("1".equals(mtype) && oldgoods == ProductConstant.PRODUCT_OLDGOOD_YES) {
				if (!invoiceId.equals("90000000000000000003")) {
					throw new MYException("普通且是非旧货的商品 只能开具 增值税专用发票17 类型发票");
				}
			}
			
			if ("1".equals(mtype) && oldgoods == ProductConstant.PRODUCT_OLDGOOD_ZERO) {
				if (!invoiceId.equals("90000000000000000004")) {
					throw new MYException("普通且是零税率的商品只能开具增值普通发票(0.00%) 类型发票");
				}
			}
		}
	}
    
    /**
     * 
     * @param duty
     * @param vsList
     * @param type 0:打标记，1：取消标记
     */
	private void processInvoicePay(DutyBean duty, List<InsVSOutBean> vsList, int type)
	{
		Set<String> oset = new HashSet<String>();
		
		for (InsVSOutBean insVSOutBean : vsList)
		{
		//  原销售单打上开票标记
		    if (insVSOutBean.getType() == FinanceConstant.INSVSOUT_TYPE_OUT)
		    {
		    	if (!oset.contains(insVSOutBean.getOutId()))
		    	{
		    		OutBean out = outDAO.find(insVSOutBean.getOutId());
		        	
		        	if (null != out)
		        	{
		        		if (type == 0) // 申请
		        		{
		        			if (StringTools.isNullOrNone(out.getPiDutyId()))
			        		{
			        			outDAO.updatePayInvoiceData(out.getFullId(), OutConstant.OUT_PAYINS_TYPE_INVOICE, duty.getMtype(), duty.getId(), 0);
			        		}
		        		}
		        		else if (type == 1) // 驳回/退票
		        		{
		        			if (out.getPiType() == OutConstant.OUT_PAYINS_TYPE_INVOICE && out.getPiStatus() == OutConstant.OUT_PAYINS_STATUS_APPROVE)
		        				outDAO.initPayInvoiceData(out.getFullId());
		        		}else // 审批通过
		        		{
		        			if (out.getPiType() == OutConstant.OUT_PAYINS_TYPE_INVOICE && out.getPiStatus() == OutConstant.OUT_PAYINS_STATUS_APPROVE)
		        				outDAO.updatePayInvoiceStatus(out.getFullId(), OutConstant.OUT_PAYINS_STATUS_FINISH);
		        		}
		        	}
		        	
		        	oset.add(insVSOutBean.getOutId());
		    	}
		    	
		    }else{
		    	if (!oset.contains(insVSOutBean.getOutBalanceId()))
		    	{
		    		OutBalanceBean outb = outBalanceDAO.find(insVSOutBean.getOutBalanceId());
		        	
		        	if (null != outb)
		        	{
		        		if (type == 0)
		        		{
		        			if (StringTools.isNullOrNone(outb.getPiDutyId()))
			        		{
			        			outBalanceDAO.updatePayInvoiceData(outb.getId(), OutConstant.OUT_PAYINS_TYPE_INVOICE, duty.getMtype(), duty.getId(), 0);
			        		}
		        		}
		        		else if (type == 1)
		        		{
		        			if (outb.getPiType() == OutConstant.OUT_PAYINS_TYPE_INVOICE && outb.getPiStatus() == OutConstant.OUT_PAYINS_STATUS_APPROVE)
		        				outBalanceDAO.initPayInvoiceData(outb.getId());
		        		}
		        		else
		        		{
		        			if (outb.getPiType() == OutConstant.OUT_PAYINS_TYPE_INVOICE && outb.getPiStatus() == OutConstant.OUT_PAYINS_STATUS_APPROVE)
		        				outBalanceDAO.updatePayInvoiceStatus(outb.getId(), OutConstant.OUT_PAYINS_STATUS_FINISH);
		        		}
		        	}
		        	
		        	oset.add(insVSOutBean.getOutBalanceId());
		    	}
		    }
		}
	}

    /**
     * fillStatus
     * 
     * @param bean
     */
    private void fillStatus(InvoiceinsBean bean)
    {
        // 全部到稽核
        bean.setStatus(FinanceConstant.INVOICEINS_STATUS_CHECK);

        List<InsVSOutBean> vsList = bean.getVsList();

        if (ListTools.isEmptyOrNull(vsList))
        {
            return;
        }

        // 是否相同纳税属性
        boolean isEqualsMtype = true;

        // 是否都是A1
        boolean isAllCommon = (bean.getMtype() == PublicConstant.MANAGER_TYPE_COMMON);

        // 这里需要判断是否是关注单据
        for (InsVSOutBean insVSOutBean : vsList)
        {
            if (insVSOutBean.getType() == FinanceConstant.INSVSOUT_TYPE_OUT)
            {
                OutBean out = outDAO.find(insVSOutBean.getOutId());

                if ( !out.getDutyId().equals(bean.getDutyId()))
                {
                    bean.setVtype(PublicConstant.VTYPE_SPECIAL);

                    bean.setStatus(FinanceConstant.INVOICEINS_STATUS_CHECK);

                    break;
                }
            }

            if (insVSOutBean.getType() == FinanceConstant.INSVSOUT_TYPE_BALANCE)
            {
                OutBalanceBean ob = outBalanceDAO.find(insVSOutBean.getOutId());

                OutBean out = outDAO.find(ob.getOutId());

                if ( !out.getDutyId().equals(bean.getDutyId()))
                {
                    bean.setVtype(PublicConstant.VTYPE_SPECIAL);

                    bean.setStatus(FinanceConstant.INVOICEINS_STATUS_CHECK);

                    break;
                }
            }
        }

        Map<String, InvoiceinsItemBean> tmpMap = new HashMap<String, InvoiceinsItemBean>();

        for (InsVSOutBean insVSOutBean : vsList)
        {
            OutBean out = null;

            if (insVSOutBean.getType() == FinanceConstant.INSVSOUT_TYPE_OUT)
            {
                out = outDAO.find(insVSOutBean.getOutId());

            }
            else
            {
                OutBalanceBean ob = outBalanceDAO.find(insVSOutBean.getOutId());

                out = outDAO.find(ob.getOutId());
            }

            List<BaseBean> baseList = baseDAO.queryEntityBeansByFK(insVSOutBean.getOutId());

            for (BaseBean baseBean : baseList)
            {
                if ( !tmpMap.containsKey(baseBean.getShowId()))
                {
                    InvoiceinsItemBean item = new InvoiceinsItemBean();
                    item.setShowId(baseBean.getShowId());

                    tmpMap.put(baseBean.getShowId(), item);
                }

                InvoiceinsItemBean subItem = tmpMap.get(baseBean.getShowId());

                subItem.setAmount(subItem.getAmount() + baseBean.getAmount());
                subItem.setMoneys(subItem.getMoneys() + baseBean.getValue());
            }

            // 是否同一个纳税实体
            if (isEqualsMtype && out.getMtype() != bean.getMtype())
            {
                isEqualsMtype = false;
            }

            // 是否都是A1
            if (isAllCommon && (out.getMtype() != PublicConstant.MANAGER_TYPE_COMMON))
            {
                isAllCommon = false;
            }

            // 是否同一个纳税实体
            if (isEqualsMtype && out.getMtype() != bean.getMtype())
            {
                isEqualsMtype = false;
            }
        }

        // 设置特殊类型
        if (isAllCommon)
        {
            // 数量相同,但是价格不同
            List<InvoiceinsItemBean> itemList = bean.getItemList();

            for (InvoiceinsItemBean invoiceinsItemBean : itemList)
            {
                InvoiceinsItemBean compareItem = tmpMap.get(invoiceinsItemBean.getShowId());

                if (compareItem == null)
                {
                    bean.setStype(FinanceConstant.INVOICEINS_STYPE_A1A1_APD);

                    return;
                }

                if (compareItem.getAmount() != invoiceinsItemBean.getAmount()
                    && !MathTools.equal2(compareItem.getMoneys(), invoiceinsItemBean.getMoneys()))
                {
                    bean.setStype(FinanceConstant.INVOICEINS_STYPE_A1A1_APD);

                    return;
                }

                if (compareItem.getAmount() == invoiceinsItemBean.getAmount()
                    && !MathTools.equal2(compareItem.getMoneys(), invoiceinsItemBean.getMoneys()))
                {
                    bean.setStype(FinanceConstant.INVOICEINS_STYPE_A1A1_PD);

                    return;
                }
            }
        }
        else
        {
            if (bean.getMtype() != PublicConstant.MANAGER_TYPE_COMMON)
            {
                bean.setStype(FinanceConstant.INVOICEINS_STYPE_A1A2);
            }
            else
            {
                bean.setStype(FinanceConstant.INVOICEINS_STYPE_A2A1);
            }
        }
    }

    /**
     * 处理单据和发票实例
     * 
     * @param insVSOutBean
     * @throws MYException
     */
    private void handlerEachInAdd(InsVSOutBean insVSOutBean)
        throws MYException
    {
        if (insVSOutBean.getType() == FinanceConstant.INSVSOUT_TYPE_OUT)
        {
            // 销售单
            OutBean out = outDAO.find(insVSOutBean.getOutId());

            if (out == null)
            {
                throw new MYException("数据错误,请确认操作");
            }
            
            if (MathTools.compare(insVSOutBean.getMoneys() + out.getInvoiceMoney(), out.getTotal()) > 0)
            {
                // TEMPLATE 数字格式化显示
                throw new MYException("单据[%s]开票溢出,开票金额[%.2f],销售金额[%.2f]", out.getFullId(),
                    (insVSOutBean.getMoneys() + out.getInvoiceMoney()), out.getTotal());
            }

            if (MathTools.compare(insVSOutBean.getMoneys() + out.getInvoiceMoney(), out.getTotal()) == 0)            	
            {
                // 更新开票状态-结束
                outDAO.updateInvoiceStatus(out.getFullId(), out.getTotal(),
                    OutConstant.INVOICESTATUS_END);
            }

            if (MathTools.compare(insVSOutBean.getMoneys() + out.getInvoiceMoney(), out.getTotal()) < 0)
            {
                // 更新开票状态-过程
                outDAO.updateInvoiceStatus(out.getFullId(), (insVSOutBean.getMoneys() + out
                    .getInvoiceMoney()), OutConstant.INVOICESTATUS_INIT);
            }
        }
        else
        {
            // 结算清单
            OutBalanceBean balance = outBalanceDAO.find(insVSOutBean.getOutBalanceId());

            if (balance == null)
            {
                throw new MYException("数据错误,请确认操作");
            }

            if (MathTools.compare(insVSOutBean.getMoneys() + balance.getInvoiceMoney(), balance.getTotal()) > 0)
            {
                // TEMPLATE 数字格式化显示
                throw new MYException("委托结算单[%s]开票溢出,开票金额[%.2f],销售金额[%.2f]", balance.getId(),
                    (insVSOutBean.getMoneys() + balance.getInvoiceMoney()), balance.getTotal());
            }

            if (MathTools.compare(insVSOutBean.getMoneys() + balance.getInvoiceMoney(), balance.getTotal()) == 0)
            {
                // 更新开票状态-结束
                outBalanceDAO.updateInvoiceStatus(balance.getId(), balance.getTotal(),
                    OutConstant.INVOICESTATUS_END);
            }

            if (MathTools.compare(insVSOutBean.getMoneys() + balance.getInvoiceMoney(), balance.getTotal()) < 0)
            {
                // 更新开票状态-过程
                outBalanceDAO.updateInvoiceStatus(balance.getId(),
                    (insVSOutBean.getMoneys() + balance.getInvoiceMoney()),
                    OutConstant.INVOICESTATUS_INIT);
            }
        }
    }

    private void handlerEachInAdd2(InsVSOutBean insVSOutBean)
        throws MYException
    {
        if (insVSOutBean.getType() == FinanceConstant.INSVSOUT_TYPE_OUT)
        {
            // 销售单
            OutBean out = outDAO.find(insVSOutBean.getOutId());

            if (out == null)
            {
                throw new MYException("数据错误,请确认操作");
            }

            BaseBean base = baseDAO.find(insVSOutBean.getBaseId());

            // 溢出的
            if (MathTools.compare(insVSOutBean.getMoneys() + base.getInvoiceMoney(), base
                .getValue()) > 0)
            {
                throw new MYException("单据[%s]开票溢出,开票金额[%.2f],销售项金额[%.2f]", out.getFullId(),
                    (insVSOutBean.getMoneys() + base.getInvoiceMoney()), base.getValue());
            }

            if (MathTools.compare(insVSOutBean.getMoneys() + base.getInvoiceMoney(), base
                .getValue()) <= 0)
            {
                baseDAO.updateInvoice(base.getId(), (insVSOutBean.getMoneys() + base
                    .getInvoiceMoney()));
            }

            // 更新主单据
            updateOut(out);
        }
        else
        {
            // 结算清单
            OutBalanceBean balance = outBalanceDAO.find(insVSOutBean.getOutId());

            if (balance == null)
            {
                throw new MYException("数据错误,请确认操作");
            }

            BaseBalanceBean bbb = baseBalanceDAO.find(insVSOutBean.getBaseId());

            double baseTotal = bbb.getAmount() * bbb.getSailPrice();

            if (MathTools.compare(insVSOutBean.getMoneys() + bbb.getInvoiceMoney(), baseTotal) > 0)
            {
                throw new MYException("委托结算单项[%s]开票溢出,开票金额[%.2f],销售金额[%.2f]", balance.getId(),
                    (insVSOutBean.getMoneys() + bbb.getInvoiceMoney()), baseTotal);
            }

            if (MathTools.compare(insVSOutBean.getMoneys() + bbb.getInvoiceMoney(), baseTotal) <= 0)
            {
                baseBalanceDAO.updateInvoice(bbb.getId(), (insVSOutBean.getMoneys() + bbb
                    .getInvoiceMoney()));
            }

            updateOutBalance(balance);
        }
    }

    private void handlerEachInAdd3(InvoiceinsItemBean item)
    throws MYException
	{
	    if (item.getType() == FinanceConstant.INSVSOUT_TYPE_OUT)
	    {
	        // 销售单
	        OutBean out = outDAO.find(item.getOutId());
	
	        if (out == null)
	        {
	            throw new MYException("数据错误,请确认操作");
	        }
	
	        BaseBean base = baseDAO.find(item.getBaseId());
	
	        // 溢出的
	        if (MathTools.compare(item.getMoneys() + base.getInvoiceMoney(), base
	            .getValue()) > 0)
	        {
	            throw new MYException("单据[%s]开票溢出,开票金额[%.2f],销售项金额[%.2f]", out.getFullId(),
	                (item.getMoneys() + base.getInvoiceMoney()), base.getValue());
	        }
	
	        if (MathTools.compare(item.getMoneys() + base.getInvoiceMoney(), base
	            .getValue()) <= 0)
	        {
	            baseDAO.updateInvoice(base.getId(), (item.getMoneys() + base
	                .getInvoiceMoney()));
	        }
	
	        // 更新主单据
	        updateOut(out);
	    }
	    else
	    {
	        // 结算清单
	        OutBalanceBean balance = outBalanceDAO.find(item.getOutId());
	
	        if (balance == null)
	        {
	            throw new MYException("数据错误,请确认操作");
	        }
	
	        BaseBalanceBean bbb = baseBalanceDAO.find(item.getBaseId());
	
	        double baseTotal = bbb.getAmount() * bbb.getSailPrice();
	
	        if (MathTools.compare(item.getMoneys() + bbb.getInvoiceMoney(), baseTotal) > 0)
	        {
	            throw new MYException("委托结算单项[%s]开票溢出,开票金额[%.2f],销售金额[%.2f]", balance.getId(),
	                (item.getMoneys() + bbb.getInvoiceMoney()), baseTotal);
	        }
	
	        if (MathTools.compare(item.getMoneys() + bbb.getInvoiceMoney(), baseTotal) <= 0)
	        {
	            baseBalanceDAO.updateInvoice(bbb.getId(), (item.getMoneys() + bbb
	                .getInvoiceMoney()));
	        }
	
	        updateOutBalance(balance);
	    }
	}
    
    /**
     * 更新销售单的开票状态
     * 
     * @param out
     */
    private void updateOut(OutBean out)
    {
        List<BaseBean> baseList = baseDAO.queryEntityBeansByFK(out.getFullId());

        double total = 0.0d;

        for (BaseBean baseBean : baseList)
        {
            total += baseBean.getInvoiceMoney();
        }

        // 全部开票
        if (MathTools.compare(total, out.getTotal()) >= 0)
        {
            // 更新开票状态-结束
            outDAO.updateInvoiceStatus(out.getFullId(), out.getTotal(),
                OutConstant.INVOICESTATUS_END);
        }
        else
        {
            // 更新开票状态-过程
            outDAO.updateInvoiceStatus(out.getFullId(), total, OutConstant.INVOICESTATUS_INIT);
        }
    }

    /**
     * updateOutBalance
     * 
     * @param balance
     */
    private void updateOutBalance(OutBalanceBean balance)
    {
        List<OutBalanceBean> baseList = outBalanceDAO.queryEntityBeansByFK(balance.getId());

        double total = 0.0d;

        for (OutBalanceBean baseBean : baseList)
        {
            total += baseBean.getInvoiceMoney();
        }

        // 全部开票
        if (MathTools.compare(total, balance.getTotal()) >= 0)
        {
            // 更新开票状态-结束
            outBalanceDAO.updateInvoiceStatus(balance.getId(), balance.getTotal(),
                OutConstant.INVOICESTATUS_END);
        }
        else
        {
            // 更新开票状态-过程
            outBalanceDAO.updateInvoiceStatus(balance.getId(), total,
                OutConstant.INVOICESTATUS_INIT);
        }
    }

    @Transactional(rollbackFor = MYException.class)
    public void clearRejectInvoiceinsBean()
        throws MYException
    {
        ConditionParse conditionParse = new ConditionParse();

        conditionParse.addWhereStr();

        // 驳回
        conditionParse.addIntCondition("status", "=", FinanceConstant.INVOICEINS_STATUS_REJECT);

        conditionParse.addCondition("logTime", "<=", TimeTools.now( -60));

        List<InvoiceinsBean> beanList = invoiceinsDAO.queryEntityBeansByCondition(conditionParse
            .toString());

        for (InvoiceinsBean invoiceinsBean : beanList)
        {
            realDelete(invoiceinsBean.getId());

            operationLog.info("clearRejectInvoiceinsBean:" + invoiceinsBean);
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.china.center.oa.finance.manager.InvoiceinsManager#deleteInvoiceinsBean(com.center.china.osgi.publics.User,
     *      java.lang.String)
     */
    @Transactional(rollbackFor = MYException.class)
    public boolean deleteInvoiceinsBean(User user, String id)
        throws MYException
    {
        JudgeTools.judgeParameterIsNull(user, id);

        InvoiceinsBean bean = invoiceinsDAO.find(id);

        if (bean == null)
        {
            throw new MYException("数据错误,请确认操作");
        }

        if ( !bean.getStafferId().equals(user.getStafferId())
            && !bean.getProcesser().equals(user.getStafferId()))
        {
            throw new MYException("只能删除自己的发票或者是自己审批的,请确认操作");
        }

        List<InsVSOutBean> vsList = insVSOutDAO.queryEntityBeansByFK(id, AnoConstant.FK_FIRST);

        realDelete(id);

        if (bean.getStatus() != FinanceConstant.INVOICEINS_STATUS_END)
        {
            return true;
        }

        if (ListTools.isEmptyOrNull(vsList))
        {
            return true;
        }

        // 倒回开票状态
        for (InsVSOutBean insVSOutBean : vsList)
        {
            if (insVSOutBean.getType() == FinanceConstant.INSVSOUT_TYPE_OUT)
            {
                // 销售单
                OutBean out = outDAO.find(insVSOutBean.getOutId());

                if (out == null)
                {
                    continue;
                }

                double im = Math.max(0.0, out.getInvoiceMoney() - insVSOutBean.getMoneys());

                // 更新单据的开票金额
                outDAO.updateInvoiceStatus(out.getFullId(), im, OutConstant.INVOICESTATUS_INIT);
            }
            else
            {
                // 结算清单
                OutBalanceBean balance = outBalanceDAO.find(insVSOutBean.getOutId());

                if (balance == null)
                {
                    throw new MYException("数据错误,请确认操作");
                }

                double im = Math.max(0.0, balance.getInvoiceMoney() - insVSOutBean.getMoneys());

                // 更新开票状态-过程
                outBalanceDAO.updateInvoiceStatus(balance.getId(), im,
                    OutConstant.INVOICESTATUS_INIT);
            }
        }

        return true;
    }

    /**
     * 清除发票
     * 
     * @param id
     */
    private void realDelete(String id)
    {
        invoiceinsDAO.deleteEntityBean(id);

        invoiceinsItemDAO.deleteEntityBeansByFK(id);

        insVSOutDAO.deleteEntityBeansByFK(id, AnoConstant.FK_FIRST);
        
        invoiceinsDetailDAO.deleteEntityBeansByFK(id);
    }

    @Transactional(rollbackFor = MYException.class)
    public boolean passInvoiceinsBean(User user, InvoiceinsBean bean, String reason)
        throws MYException
    {
        JudgeTools.judgeParameterIsNull(user, bean, bean.getId());

        InvoiceinsBean obean = invoiceinsDAO.find(bean.getId());

        if (obean == null)
        {
            throw new MYException("数据错误,请确认操作");
        }

        if (obean.getStatus() != FinanceConstant.INVOICEINS_STATUS_SUBMIT)
        {
            throw new MYException("数据错误,请确认操作");
        }

        int status = obean.getStatus();

        // 开票成功
        //bean.setStatus(FinanceConstant.INVOICEINS_STATUS_END);
        if (obean.getOtype() == FinanceConstant.INVOICEINS_TYPE_OUT){
	        // 开票成功 - 待确认
        	obean.setStatus(FinanceConstant.INVOICEINS_STATUS_CONFIRM);
        	
        	saveForFinanceTag(bean.getId());
        }
        else
        	obean.setStatus(FinanceConstant.INVOICEINS_STATUS_END);

        invoiceinsDAO.updateEntityBean(obean);

        List<InsVSOutBean> vsList = insVSOutDAO.queryEntityBeansByFK(obean.getId(), AnoConstant.FK_FIRST);
        
        List<InvoiceinsItemBean> itemList = invoiceinsItemDAO.queryEntityBeansByFK(obean.getId());

        // 单据的开票状态需要更新
        if ( !ListTools.isEmptyOrNull(vsList))
        {
        	if (StringTools.isNullOrNone(vsList.get(0).getBaseId()))
        	{
        		for (InsVSOutBean insVSOutBean : vsList)
                {
        			handlerEachInAdd(insVSOutBean);
                }
        	}
        	// 0新模式标记 @@see InvoiceinsAction.createInsInNavigation1
        	else if (vsList.get(0).getBaseId().equals("0")){
        		
        		for (InvoiceinsItemBean item : itemList)
                {
        			 // 新的开单规则
                    handlerEachInAdd3(item);
                }
        	}else{
        		for (InsVSOutBean insVSOutBean : vsList)
                {
        			handlerEachInAdd2(insVSOutBean);
                }
        	}
        }
        
        if (bean.getOtype() == FinanceConstant.INVOICEINS_TYPE_IN)
        {
        	// 删除原单关联的销售单数据
    		List<InsVSOutBean> vsrList = insVSOutDAO.queryEntityVOsByFK(bean.getRefId(), AnoConstant.FK_FIRST);
    		
    		// 处理票款纳税实体一致  取消开票时打的标记
    		processInvoicePay(null, vsrList , 1);
    		
    		for (InsVSOutBean each : vsrList)
    		{
    			each.setOutId("");
    			each.setOutBalanceId("");
    			each.setBaseId("");
    			
    			insVSOutDAO.updateEntityBean(each);
    		}
    		
    		List<InvoiceinsItemBean> ritemList = invoiceinsItemDAO.queryEntityBeansByFK(bean.getRefId());
    		
    		for (InvoiceinsItemBean each : ritemList)
    		{
    			each.setOutId("");
    			each.setBaseId("");
    			
    			invoiceinsItemDAO.updateEntityBean(each);
    		}
    			
        	// 产生凭证
//        	for (InvoiceinsListener each : this.listenerMapValues())
//        	{
//        		each.onConfirmPay(user, bean);
//        	}
        }else{
        	// 处理票款纳税实体一致  结束开票时打的标记
        	processInvoicePay(null, vsList, 2);
        }

        List<InsVSInvoiceNumBean> numList = bean.getNumList();
        
        for (InsVSInvoiceNumBean each : numList)
        {
        	each.setInsId(obean.getId());
        }
        
        insVSInvoiceNumDAO.deleteEntityBeansByFK(obean.getId());
        
        insVSInvoiceNumDAO.saveAllEntityBeans(numList);
        
        // 准备发票打包数据
        createPackage(obean);
        
        FlowLogBean log = new FlowLogBean();

        log.setActor(user.getStafferName());
        log.setActorId(user.getStafferId());
        log.setFullId(obean.getId());
        log.setDescription(reason);
        log.setLogTime(TimeTools.now());
        log.setPreStatus(status);
        log.setAfterStatus(bean.getStatus());
        log.setOprMode(PublicConstant.OPRMODE_PASS);

        flowLogDAO.saveEntityBean(log);

        return true;
    }
    
    private void createPackage(final InvoiceinsBean bean) throws MYException
	{
    	if (bean.getOtype() == FinanceConstant.INVOICEINS_TYPE_IN) {
    		return;
    	}
    		
    	if (bean.getShipping() == OutConstant.OUT_SHIPPING_NOTSHIPPING) {
    		return;
    	}
    	
    	if (bean.getShipping() == OutConstant.OUT_SHIPPING_SELFSERVICE)
		{
			 PreConsignBean preConsign = new PreConsignBean();
             
             preConsign.setOutId(bean.getId());
             
             packageManager.createInsPackage(preConsign, bean.getId());
		}else
		{
            PreConsignBean preConsign = new PreConsignBean();
            
            preConsign.setOutId(bean.getId());
            
            preConsignDAO.saveEntityBean(preConsign);
		}
	}

    @Transactional(rollbackFor = MYException.class)
    public boolean checkInvoiceinsBean(User user, String id, String reason)
        throws MYException
    {
        JudgeTools.judgeParameterIsNull(user, id);

        InvoiceinsBean obean = invoiceinsDAO.find(id);

        if (obean == null)
        {
            throw new MYException("数据错误,请确认操作");
        }

        if (obean.getStatus() != FinanceConstant.INVOICEINS_STATUS_CHECK)
        {
            throw new MYException("数据错误,请确认操作");
        }

        int status = obean.getStatus();

        // 财务审核
        obean.setStatus(FinanceConstant.INVOICEINS_STATUS_SUBMIT);

        invoiceinsDAO.updateEntityBean(obean);
        
        FlowLogBean log = new FlowLogBean();

        log.setActor(user.getStafferName());
        log.setActorId(user.getStafferId());
        log.setFullId(obean.getId());
        log.setDescription(reason);
        log.setLogTime(TimeTools.now());
        log.setPreStatus(status);
        log.setAfterStatus(obean.getStatus());
        log.setOprMode(PublicConstant.OPRMODE_PASS);

        flowLogDAO.saveEntityBean(log);

        return true;
    }

    @Transactional(rollbackFor = MYException.class)
    public boolean rejectInvoiceinsBean(User user, String id, String reason)
        throws MYException
    {
        JudgeTools.judgeParameterIsNull(user, id);

        InvoiceinsBean bean = invoiceinsDAO.find(id);

        if (bean == null)
        {
            throw new MYException("数据错误,请确认操作");
        }

        if (bean.getStatus() == FinanceConstant.INVOICEINS_STATUS_END)
        {
            throw new MYException("数据错误,请确认操作");
        }

        int status = bean.getStatus();

        // 驳回
        bean.setStatus(FinanceConstant.INVOICEINS_STATUS_REJECT);

        invoiceinsDAO.updateEntityBean(bean);

        List<InsVSOutBean> vsList = insVSOutDAO.queryEntityBeansByFK(id, AnoConstant.FK_FIRST);
        
        // 删除对应关系
        //insVSOutDAO.deleteEntityBeansByFK(id, AnoConstant.FK_FIRST);
        
        // 开票打的标记清除
        processInvoicePay(null, vsList, 1);

        FlowLogBean log = new FlowLogBean();

        log.setActor(user.getStafferName());
        log.setActorId(user.getStafferId());
        log.setFullId(id);
        log.setDescription(reason);
        log.setLogTime(TimeTools.now());
        log.setPreStatus(status);
        log.setAfterStatus(FinanceConstant.INVOICEINS_STATUS_REJECT);
        log.setOprMode(PublicConstant.OPRMODE_REJECT);

        flowLogDAO.saveEntityBean(log);

        return true;
    }

    @Transactional(rollbackFor = MYException.class)
    public boolean checkInvoiceinsBean2(User user, String id, String checks, String refId)
        throws MYException
    {
        JudgeTools.judgeParameterIsNull(user, id);

        InvoiceinsBean bean = invoiceinsDAO.find(id);

        if (bean == null)
        {
            throw new MYException("数据错误,请确认操作");
        }

        bean.setCheckStatus(PublicConstant.CHECK_STATUS_END);
        bean.setChecks(checks + " [" + TimeTools.now() + ']');
        bean.setCheckrefId(refId);

        invoiceinsDAO.updateEntityBean(bean);

        return true;
    }

    public InvoiceinsVO findVO(String id)
    {
        InvoiceinsVO vo = invoiceinsDAO.findVO(id);

        if (vo == null)
        {
            return null;
        }

        vo.setItemList(invoiceinsItemDAO.queryEntityBeansByFK(id));

        vo.setVsList(insVSOutDAO.queryEntityBeansByFK(id, AnoConstant.FK_FIRST));
        
        vo.setDetailList(invoiceinsDetailDAO.queryEntityBeansByFK(id));
        
        vo.setNumList(insVSInvoiceNumDAO.queryEntityBeansByFK(id));
        
        List<AttachmentBean> attachmentList = attachmentDAO.queryEntityVOsByFK(id);

        vo.setAttachmentList(attachmentList);

        return vo;
    }
    
    /**
     * 确认开票
     * {@inheritDoc}
     */
    @Transactional(rollbackFor = MYException.class)
	public boolean confirmInvoice(User user, String id) throws MYException
	{
    	JudgeTools.judgeParameterIsNull(user, id);
    	
    	InvoiceinsBean bean = invoiceinsDAO.find(id);
    	
    	if (null == bean)
    	{
    		throw new MYException("数据错误");
    	}
    	
    	if (bean.getStatus() != FinanceConstant.INVOICEINS_STATUS_CONFIRM)
    	{
    		throw new MYException("数据错误，状态不是待确认状态");
    	}
    	
    	bean.setInvoiceConfirmStatus(FinanceConstant.INVOICEINS_CONFIRM_STATUS_YES);
    	
    	if (bean.getPayConfirmStatus() == FinanceConstant.INVOICEINS_PAY_CONFIRM_STATUS_YES)
    	{
    		bean.setStatus(FinanceConstant.INVOICEINS_STATUS_END);
    	}
		
		invoiceinsDAO.updateEntityBean(bean);
		
        FlowLogBean log = new FlowLogBean();

        log.setActor(user.getStafferName());
        log.setActorId(user.getStafferId());
        log.setFullId(id);
        log.setDescription("OK");
        log.setLogTime(TimeTools.now());
        log.setPreStatus(FinanceConstant.INVOICEINS_STATUS_CONFIRM);
        if (bean.getStatus() == FinanceConstant.INVOICEINS_STATUS_END)
        {
        	log.setAfterStatus(FinanceConstant.INVOICEINS_STATUS_END);
        }
        else
        {
        	log.setAfterStatus(FinanceConstant.INVOICEINS_STATUS_CONFIRM);
        }
        
        log.setOprMode(PublicConstant.OPRMODE_PASS);

        flowLogDAO.saveEntityBean(log);
		
		return true;
	}
    
    private void saveForFinanceTag(String id)
    {
    	InvoiceinsTagBean tagBean = new InvoiceinsTagBean();
    	
    	tagBean.setInsId(id);
    	
    	invoiceinsTagDAO.saveEntityBean(tagBean);
    }

	/**
	 * 确认付款
	 */
    @Transactional(rollbackFor = MYException.class)
	public boolean confirmPay(User user, String id) throws MYException
	{
    	JudgeTools.judgeParameterIsNull(user, id);
    	
    	InvoiceinsBean bean = invoiceinsDAO.find(id);
    	
    	if (null == bean)
    	{
    		throw new MYException("数据错误");
    	}
    	
    	if (bean.getStatus() != FinanceConstant.INVOICEINS_STATUS_CONFIRM)
    	{
    		throw new MYException("数据错误，状态不是待确认状态");
    	}
    	
    	bean.setPayConfirmStatus(FinanceConstant.INVOICEINS_PAY_CONFIRM_STATUS_YES);
    	
    	if (bean.getInvoiceConfirmStatus() == FinanceConstant.INVOICEINS_CONFIRM_STATUS_YES)
    	{
    		bean.setStatus(FinanceConstant.INVOICEINS_STATUS_END);
    	}
		
		invoiceinsDAO.updateEntityBean(bean);
		
		List<InvoiceinsItemBean> itemList = invoiceinsItemDAO.queryEntityBeansByFK(id);
		
		bean.setItemList(itemList);
		
		// 产生凭证 借:销售费用_业务员开票税金   贷:主营业务税金及附加
//		for (InvoiceinsListener each : this.listenerMapValues())
//		{
//			each.onConfirmPay(user, bean);
//		}
		
        FlowLogBean log = new FlowLogBean();

        log.setActor(user.getStafferName());
        log.setActorId(user.getStafferId());
        log.setFullId(id);
        log.setDescription("OK");
        log.setLogTime(TimeTools.now());
        log.setPreStatus(FinanceConstant.INVOICEINS_STATUS_CONFIRM);
        if (bean.getStatus() == FinanceConstant.INVOICEINS_STATUS_END)
        {
        	log.setAfterStatus(FinanceConstant.INVOICEINS_STATUS_END);
        }
        else
        {
        	log.setAfterStatus(FinanceConstant.INVOICEINS_STATUS_CONFIRM);
        }
        
        log.setOprMode(PublicConstant.OPRMODE_PASS);

        flowLogDAO.saveEntityBean(log);
		
		return true;
	}
    
    @Transactional(rollbackFor = MYException.class)
	public boolean backInvoiceins(User user, String id) throws MYException
	{
    	JudgeTools.judgeParameterIsNull(user, id);
    	
    	InvoiceinsBean bean = invoiceinsDAO.find(id);
    	
    	if (null == bean)
    	{
    		throw new MYException("数据错误");
    	}
    	
    	if (bean.getStatus() != FinanceConstant.INVOICEINS_STATUS_END)
    	{
    		throw new MYException("数据错误，状态不是待确认状态");
    	}
    	
    	if (bean.getOtype() != FinanceConstant.INVOICEINS_TYPE_OUT)
    	{
    		throw new MYException("数据错误，不是开票数据");
    	}
    	
    	List<InvoiceinsBean> beanList = invoiceinsDAO.queryEntityBeansByFK(id);
    	
    	if (beanList.size() > 0)
    	{
    		throw new MYException("数据错误，开票已发生退票");
    	}
    	
    	// 退票时检查开票日期必须在当前日期前三个月内
    	/*String logDate = TimeTools.changeTimeToDate(bean.getLogTime());
    	
    	if (TimeTools.cdate(TimeTools.now_short(-90), logDate) > 0) {
    		throw new MYException("开票日期必须在当前日期前三个月内才能退票");
    	}*/
    	
    	InvoiceinsBean newBean = new InvoiceinsBean();
    	
    	BeanUtil.copyProperties(newBean, bean);
    	
    	newBean.setOtype(FinanceConstant.INVOICEINS_TYPE_IN);
    	
    	newBean.setMoneys(-bean.getMoneys());
    	
    	newBean.setStatus(FinanceConstant.INVOICEINS_STATUS_SUBMIT);
    	
    	String newId = commonDAO.getSquenceString20();
    	
    	newBean.setId(newId);

    	newBean.setLogTime(TimeTools.now());
    	
    	newBean.setDescription("退票，原票：" + bean.getId());
    	
    	newBean.setRefId(bean.getId());
    	
    	newBean.setStafferId(user.getStafferId());
    	
    	invoiceinsDAO.saveEntityBean(newBean);
    	
    	List<InvoiceinsItemBean> itemList = invoiceinsItemDAO.queryEntityBeansByFK(id);
    	
    	for (InvoiceinsItemBean each : itemList)
    	{
    		InvoiceinsItemBean newItem = new InvoiceinsItemBean();
    		
    		BeanUtil.copyProperties(newItem, each);
    		
    		// 检查baseId
    		checkBaseId(each);
    		
    		newItem.setId(commonDAO.getSquenceString20());
    		
    		newItem.setParentId(newId);
    		
    		newItem.setAmount(-each.getAmount());
    		
    		newItem.setMoneys(newItem.getAmount() * newItem.getPrice());
    		
    		invoiceinsItemDAO.saveEntityBean(newItem);
    	}
    	
    	List<InsVSOutBean> vsList = insVSOutDAO.queryEntityBeansByFK(id, AnoConstant.FK_FIRST);
    	
    	for (InsVSOutBean each : vsList)
    	{
    		InsVSOutBean newItem = new InsVSOutBean();
    		
    		BeanUtil.copyProperties(newItem, each);
    		
    		newItem.setId(commonDAO.getSquenceString20());
    		
    		newItem.setInsId(newId);
    		
    		newItem.setMoneys(-each.getMoneys());
    		
    		insVSOutDAO.saveEntityBean(newItem);
    	}
    	
    	return true;
	}
    
    private void checkBaseId(InvoiceinsItemBean item) throws MYException
    {
    	if (item.getType() == FinanceConstant.INSVSOUT_TYPE_OUT) {
    		if (!StringTools.isNullOrNone(item.getBaseId())) {
        		BaseBean base = baseDAO.find(item.getBaseId());
        		
        		if (null == base) {
        			throw new MYException("销售单[%s]中的行项目在开票后发生修改过，不能退票.", item.getOutId());
        		}
        	}
    	}
    }

    @Override
    @Transactional(rollbackFor = MYException.class)
	public boolean importInvoice(User user, List<InvoiceStorageBean> list)
			throws MYException
	{
    	JudgeTools.judgeParameterIsNull(user,list);
    	
    	for (InvoiceStorageBean each : list)
    	{
    		each.setOprDate(TimeTools.now_short());
    		each.setOprName(user.getStafferName());
    		each.setLogTime(TimeTools.now());
    		
    		each.setId(commonDAO.getSquenceString20());
    	}
    	
    	invoiceStorageDAO.saveAllEntityBeans(list);
    	
		return true;
	}
    
    @Override
    @Transactional(rollbackFor = MYException.class)
	public boolean refConfirmInvoice(User user, List<InvoiceBindOutBean> vsList)
			throws MYException
	{
    	JudgeTools.judgeParameterIsNull(user, vsList);
    	
    	String invoiceId = vsList.get(0).getInvoiceStorageId();
    	
    	String providerId = vsList.get(0).getProviderId();
    	
    	ConditionParse con = new ConditionParse();
    	
    	con.addWhereStr();
    	
    	con.addCondition("InvoiceStorageBean.stafferId", "=", user.getStafferId());
    	
    	con.addCondition("InvoiceStorageBean.invoiceId", "=", invoiceId);
    	
    	con.addCondition("InvoiceStorageBean.providerId", "=", providerId);
    	
    	con.addCondition(" and InvoiceStorageBean.moneys > InvoiceStorageBean.hasConfirmMoneys");

    	List<InvoiceStorageBean> list = invoiceStorageDAO.queryEntityBeansByCondition(con);
    	
    	List<InvoiceBindOutBean> bindList = new ArrayList<InvoiceBindOutBean>();
    	
    	for (InvoiceStorageBean each : list)
    	{
    		double canUse = each.getMoneys() - each.getHasConfirmMoneys();
    		
    		for (InvoiceBindOutBean eachb : vsList)
        	{
    			if (eachb.getTemp() == 1)
    				continue;

    			double needConfirm = eachb.getConfirmMoney() - eachb.getTempMoney();
    			
    			if (canUse >= needConfirm)
    			{
    				canUse -= needConfirm;
    				
    				eachb.setTempMoney(needConfirm);
    				
    				eachb.setTemp(1); // 表示单据完且确认
    				
    				InvoiceBindOutBean bind = new InvoiceBindOutBean();
    				
    				bind.setInvoiceStorageId(each.getId());
    				bind.setConfirmMoney(needConfirm);
    				bind.setFullId(eachb.getFullId());
    				bind.setOuttype(eachb.getOuttype());
    				bind.setProviderId(eachb.getProviderId());
    				bind.setLogTime(eachb.getLogTime());
    				
    				bindList.add(bind);
    				
    				if (canUse == 0)
    					break;
    				
    			}else{
    				eachb.setTempMoney(eachb.getTempMoney() + canUse);
    				
    				eachb.setTemp(0);
    				
    				InvoiceBindOutBean bind = new InvoiceBindOutBean();
    				
    				bind.setInvoiceStorageId(each.getId());
    				bind.setConfirmMoney(canUse);
    				bind.setFullId(eachb.getFullId());
    				bind.setOuttype(eachb.getOuttype());
    				bind.setProviderId(eachb.getProviderId());
    				bind.setLogTime(eachb.getLogTime());
    				
    				bindList.add(bind);
    				
    				canUse = 0;
    				
    				break;
    			}
        	}
    		
    		each.setHasConfirmMoneys(each.getMoneys() - canUse);
    		
    		invoiceStorageDAO.updateEntityBean(each);
    	}
    	
    	Map<String,InvoiceBindOutBean> map = new HashMap<String,InvoiceBindOutBean>();
    	
    	//  按fullId + outtype 合并金额
    	for (InvoiceBindOutBean each : bindList)
    	{
    		String key = each.getFullId() + "-" + each.getOuttype();
    		
    		if (!map.containsKey(key))
    		{
    			map.put(key, each);
    		}else{
    			InvoiceBindOutBean bo = map.get(key);
    			
    			bo.setConfirmMoney(bo.getConfirmMoney() + each.getConfirmMoney());
    		}
    	}
    	
    	for (InvoiceBindOutBean each : map.values())
    	{
    		// 采购付款申请认票
    		if (each.getOuttype() == 999)
    		{
    			StockPayApplyBean spaBean = stockPayApplyDAO.find(each.getFullId());
    			
    			if (null == spaBean)
    			{
    				throw new MYException("数据错误");
    			}
    			
    			if (MathTools.compare(spaBean.getRealMoneys() - spaBean.getHasConfirmInsMoney(), each.getConfirmMoney()) < 0)
    			{
    				throw new MYException("数据溢出，请重新操作");
    			}
    			
    			stockPayApplyDAO.updateHasConfirmMoney(each.getFullId(), spaBean.getHasConfirmInsMoney() + each.getConfirmMoney());
    			
    			if (MathTools.equal(spaBean.getRealMoneys() - spaBean.getHasConfirmInsMoney(), each.getConfirmMoney()))
    			{
    				stockPayApplyDAO.updateHasConfirm(each.getFullId(), 1);
    			}
    			
    		}else if (each.getOuttype() == 98)  //委托退货认票
    		{
    			OutBalanceBean balanceBean = outBalanceDAO.find(each.getFullId());
    			
    			if (null == balanceBean)
    			{
    				throw new MYException("数据错误");
    			}
    			
    			if (MathTools.compare(balanceBean.getTotal() - balanceBean.getHasConfirmInsMoney(), each.getConfirmMoney()) < 0)
    			{
    				throw new MYException("数据溢出，请重新操作");
    			}
    			
    			outBalanceDAO.updateHasConfirmMoney(each.getFullId(), balanceBean.getHasConfirmInsMoney() + each.getConfirmMoney());
    			
    			if (MathTools.equal(balanceBean.getTotal() - balanceBean.getHasConfirmInsMoney(), each.getConfirmMoney()))
    			{
    				outBalanceDAO.updateHasConfirm(each.getFullId(), 1);
    			}
    			
    		}else{
    			OutBean out = outDAO.find(each.getFullId());
    			
    			if (null == out)
    			{
    				throw new MYException("数据错误");
    			}

    			if (MathTools.compare(out.getTotal() - out.getHasConfirmInsMoney(), each.getConfirmMoney()) < 0)
    			{
    				throw new MYException("数据溢出，请重新操作");
    			}
    			
    			outDAO.updateHasConfirmMoney(each.getFullId(), out.getHasConfirmInsMoney() + each.getConfirmMoney());
    			
    			if (MathTools.equal(out.getTotal() - out.getHasConfirmInsMoney(), each.getConfirmMoney()))
    			{
    				outDAO.updateHasConfirm(each.getFullId(), 1);
    			}
    		}
    	}
    	
    	invoiceBindOutDAO.saveAllEntityBeans(bindList);
    	
		return true;
	}
    
    @Transactional(rollbackFor = MYException.class)
	public boolean refInvoice(User user, List<InvoiceBindOutBean> vsList)
			throws MYException
	{
    	JudgeTools.judgeParameterIsNull(user, vsList);
    	
    	String stockPayApplyId = vsList.get(0).getFullId();
    	
    	StockPayApplyBean bean = stockPayApplyDAO.find(stockPayApplyId);
    	
    	if (null == bean)
    	{
    		throw new MYException("数据错误");
    	}
    	
    	if (bean.getStatus() != StockPayApplyConstant.APPLY_STATUS_END)
    	{
    		throw new MYException("采购付款申请单须是结束状态");
    	}
    	
    	double needConfirm = bean.getRealMoneys() - bean.getHasConfirmInsMoney();
    	
    	if (needConfirm <= 0)
    	{
    		throw new MYException("发票金额已全部确认");
    	}
    	
    	List<InvoiceBindOutBean> bindList = new ArrayList<InvoiceBindOutBean>();
    	
    	for (InvoiceBindOutBean each : vsList)
    	{
    		InvoiceStorageBean isbean = invoiceStorageDAO.find(each.getInvoiceStorageId());
    		
    		double canUseMoney = isbean.getMoneys() - isbean.getHasConfirmMoneys();
    		
    		if (canUseMoney >= needConfirm)
    		{
    			canUseMoney -= needConfirm;
    			
    			stockPayApplyDAO.updateHasConfirmMoney(stockPayApplyId, bean.getRealMoneys());
    			
    			stockPayApplyDAO.updateHasConfirm(stockPayApplyId, 1);
    			
    			isbean.setHasConfirmMoneys(isbean.getHasConfirmMoneys() + needConfirm);
    			
    			invoiceStorageDAO.updateEntityBean(isbean);
    			
    			bindList.add(each);
    			
    			break;
    		}else{
    			needConfirm -= canUseMoney;
    			
    			stockPayApplyDAO.updateHasConfirmMoney(stockPayApplyId, bean.getHasConfirmInsMoney() + canUseMoney);
    			
    			isbean.setHasConfirmMoneys(isbean.getHasConfirmMoneys() + canUseMoney);
    			
    			bindList.add(each);
    			
    			invoiceStorageDAO.updateEntityBean(isbean);
    		}
    	}
    	
    	invoiceBindOutDAO.saveAllEntityBeans(bindList);
    	
    	return true;
	}
    
    @Transactional(rollbackFor = MYException.class)
	public String importInvoiceins(User user, List<InvoiceinsImportBean> list)
			throws MYException
	{
    	JudgeTools.judgeParameterIsNull(user, list);
    	
    	String batchId = commonDAO.getSquenceString20();
    	
    	for (InvoiceinsImportBean each : list) {
    		each.setBatchId(batchId);
    	}
    	
    	invoiceinsImportDAO.saveAllEntityBeans(list);
    	
		return batchId;
	}
    
    @Transactional(rollbackFor = MYException.class)
	public boolean batchUpdateInsNum(User user, List<InvoiceinsImportBean> list)
			throws MYException
	{
    	JudgeTools.judgeParameterIsNull(user, list);
    	
    	Map<String, List<InvoiceinsImportBean>> map = new HashMap<String, List<InvoiceinsImportBean>>();
    	
    	Set<String> dset = new HashSet<String>();
    	
    	for (InvoiceinsImportBean each : list) {
    		if (dset.contains(each.getInvoiceNum())) {
    			throw new MYException("导入的发票号中有重复");
    		} else {
    			dset.add(each.getInvoiceNum());
    		}
    		
    		if (!map.containsKey(each.getId())) {
    			List<InvoiceinsImportBean> ilist = new ArrayList<InvoiceinsImportBean>();
    			
    			ilist.add(each);
    			
    			map.put(each.getId(), ilist);
    		} else {
    			List<InvoiceinsImportBean> ilist = map.get(each.getId());
    			
    			ilist.add(each);
    		}
    	}
    	
    	for (Map.Entry<String, List<InvoiceinsImportBean>> each : map.entrySet()) {
    		List<InvoiceinsImportBean> ilist = each.getValue();
    		
    		List<InsVSInvoiceNumBean> numList = insVSInvoiceNumDAO.queryEntityBeansByFK(each.getKey());
    		
    		if (ilist.size() != numList.size()) {
    			throw new MYException("开票标识[%s]发票号码条数是[%s],导入的发票号码条数[%s],两者条数须一致。", each.getKey(), numList.size(), ilist.size());
    		}
    		
    		// 不得有与现有的重复的发票号
    		for (int i = 0; i < ilist.size(); i++) {
    			InvoiceinsImportBean newnum = ilist.get(i);
    			
    			List<InsVSInvoiceNumBean> cList = insVSInvoiceNumDAO.queryEntityBeansByCondition("where invoiceNum = ?", newnum.getInvoiceNum());
    			
    			if (!ListTools.isEmptyOrNull(cList)) {
    				throw new MYException("开票标识[%s]发票号码[%s]已使用过,原发票标识[%s]", each.getKey(), newnum.getInvoiceNum(), cList.get(0).getInsId());
    			}
    			
    			InsVSInvoiceNumBean insNum = insVSInvoiceNumDAO.find(numList.get(i).getId());
    			
    			insNum.setInvoiceNum(newnum.getInvoiceNum());
    			
    			insVSInvoiceNumDAO.updateEntityBean(insNum);
    		}
    	}
    	
		return true;
	}

    public void checkImportIns(List<InvoiceinsImportBean> list, StringBuilder sb){
    	//1.没有发生过开票，可开票部分 = 原单 - 退货
    	//2.须符合票、款一致原则
    	for (InvoiceinsImportBean each : list) {
    		String fullId = "";
    		// 特殊类型,该类型不生成开票申请
    		if (each.getInvoiceId().equals("9999999999")) {
    			continue;
    		}
    		
    		OutBean out = outDAO.find(each.getOutId());
    		
    		if (out == null) {
    			OutBalanceBean balance = outBalanceDAO.find(each.getOutId());
    			
    			if (null == balance) {
    				sb.append("库单");
    				sb.append(each.getOutId());
    				sb.append("不存在");
    				sb.append("<br>");
    			} else {
    				if (balance.getInvoiceMoney() > 0) {
        				sb.append("结算单");
        				sb.append(each.getOutId());
        				sb.append("已部分开过发票，不能批量导入，请用[开票申请]功能");
        				sb.append("<br>");
        			} else {
        				List<BaseBalanceBean> balanceList = baseBalanceDAO.queryEntityBeansByFK(balance.getId());
        				
        				if (!ListTools.isEmptyOrNull(balanceList)) {
        					for (BaseBalanceBean eachbb : balanceList) {
        						BaseBean base = baseDAO.find(eachbb.getBaseId()); 
        						
        						if (null != base) {
        							try {
										checkProductAttrInner(each.getInvoiceId(), base.getProductId());
									} catch (MYException e) {
										sb.append("结算单");
				        				sb.append(each.getOutId());
				        				sb.append(e.getErrorContent());
				        				sb.append("<br>");
									}
        						}
        					}
        				}
        				
        				if (StringTools.isNullOrNone(balance.getPiDutyId()) || (balance.getPiMtype() == 1 && balance.getPiStatus() == 1)) {
        					// 
        					double refMoneys = outBalanceDAO.sumByOutBalanceId(each.getId());
        					
        					if (MathTools.compare(each.getInvoiceMoney(), balance.getTotal() - refMoneys - balance.getInvoiceMoney()) != 0) {
	    						sb.append("销售单");
	            				sb.append(each.getOutId());
	            				sb.append("导入的开票金额须等于可开票金额");
	            				sb.append("<br>");
	    					}
        				} else {
        					sb.append("结算单");
            				sb.append(each.getOutId());
            				sb.append("违反票、款一致，可能是非普通或勾款审批未结束");
            				sb.append("<br>");
        				}
        			}
    				
    				fullId = balance.getOutId();
    			}
    		} else {
    			if (out.getInvoiceStatus() == OutConstant.INVOICESTATUS_END
    					|| out.getInvoiceMoney() > 0) {
    				sb.append("销售单");
    				sb.append(each.getOutId());
    				sb.append("已部分开过发票，不能批量导入，请用[开票申请]功能");
    				sb.append("<br>");
    			} else {
    				List<BaseBean> baseList = baseDAO.queryEntityBeansByFK(out.getFullId());
    				
    				if (!ListTools.isEmptyOrNull(baseList)) {
    					for (BaseBean eachb : baseList) {
    						try {
								checkProductAttrInner(each.getInvoiceId(), eachb.getProductId());
							} catch (MYException e) {
								sb.append("销售单");
		        				sb.append(each.getOutId());
		        				sb.append(e.getErrorContent());
		        				sb.append("<br>");
							}
    					}
    				}
    				
    				if (StringTools.isNullOrNone(out.getPiDutyId()) || (out.getPiMtype() == 1 && out.getPiStatus() == 1)) {
    					// 检查 导入的开票金额是全部的可开票金额
    					double retTotal = outDAO.sumOutBackValueIgnoreStatus(out.getFullId());
    					
    					if (MathTools.compare(each.getInvoiceMoney(), out.getTotal() - retTotal - out.getInvoiceMoney()) != 0) {
    						sb.append("销售单");
            				sb.append(each.getOutId());
            				sb.append("导入的开票金额须等于可开票金额");
            				sb.append("<br>");
    					}
    					
    				} else {
    					sb.append("销售单");
        				sb.append(each.getOutId());
        				sb.append("违反票、款一致，可能是非普通或勾款审批未结束");
        				sb.append("<br>");
    				}
    			}
    			
    			fullId = out.getFullId();
    		}
    		
    		if (!StringTools.isNullOrNone(fullId) && each.getAddrType() == InvoiceinsConstants.INVOICEINS_DIST_SAME) {
    			List<DistributionBean> distList = distributionDAO.queryEntityBeansByFK(fullId);
    			
    			if (!ListTools.isEmptyOrNull(distList)) {
    				if (distList.get(0).getShipping() == OutConstant.OUT_SHIPPING_NOTSHIPPING) {
    					sb.append("销售单");
        				sb.append(fullId);
        				sb.append("配送地址选择 同原销售单，但原销售单配送方式是 空发 ");
        				sb.append("<br>");
    				}
    			}
    		}
    	}
    }
    
    public boolean process(final List<InvoiceinsImportBean> list)
	throws MYException
	{
    	JudgeTools.judgeParameterIsNull(list);
    	
    	StringBuilder sb = new StringBuilder();
    	
    	// check again
    	checkImportIns(list, sb);
    	
    	if (!StringTools.isNullOrNone(sb.toString())) {
    		throw new MYException(sb.toString());
    	}
    	
    	final String batchId = list.get(0).getBatchId();
		
		try
		{
			TransactionTemplate tran = new TransactionTemplate(transactionManager);
			
			tran.execute(new TransactionCallback()
			{
				public Object doInTransaction(TransactionStatus status)
				{
					try
					{
						processInner(list);
					}
					catch (MYException e)
					{
						throw new RuntimeException(e);
					}
					
					//saveLog
					saveLogInnerWithoutTransaction(batchId, OutImportConstant.LOGSTATUS_SUCCESSFULL, "成功");
					
					return Boolean.TRUE;
				}
			}
			);
		}
		catch (TransactionException e)
        {
			saveLogInner(batchId, OutImportConstant.LOGSTATUS_FAIL, "处理失败,数据库内部错误");
			
			operationLog.error("批量开票数据处理错误：", e);
            throw new MYException("数据库内部错误");
        }
        catch (DataAccessException e)
        {
        	saveLogInner(batchId, OutImportConstant.LOGSTATUS_FAIL, "处理失败,数据访问异常");
        	
        	operationLog.error("批量开票数据处理错误：", e);
            throw new MYException(e.getCause().toString());
        }
        catch (Exception e)
        {
        	saveLogInner(batchId, OutImportConstant.LOGSTATUS_FAIL, "处理失败,系统错误，请联系管理员");
        	
        	operationLog.error("批量开票数据处理错误：", e);
            throw new MYException("系统错误，请联系管理员:" + e);
        }
        
		return true;
	}
    
    private boolean saveLogInner(final String batchId, final int status, final String message)
	{
		try
		{
			TransactionTemplate tran = new TransactionTemplate(transactionManager);
			
			tran.execute(new TransactionCallback()
			{
				public Object doInTransaction(TransactionStatus tstatus)
				{
					insImportLogDAO.deleteEntityBeansByFK(batchId);
					
					InsImportLogBean logBean = new InsImportLogBean();
					
					logBean.setId(commonDAO.getSquenceString20());
					logBean.setBatchId(batchId);
					logBean.setLogTime(TimeTools.now());
					logBean.setMessage(message);
					logBean.setStatus(status);
					
					insImportLogDAO.saveEntityBean(logBean);
					
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
    
    private boolean saveLogInnerWithoutTransaction(String batchId, int status, String message)
	{
		insImportLogDAO.deleteEntityBeansByFK(batchId);
		
		InsImportLogBean logBean = new InsImportLogBean();
		
		logBean.setId(commonDAO.getSquenceString20());
		logBean.setBatchId(batchId);
		logBean.setLogTime(TimeTools.now());
		logBean.setMessage(message);
		logBean.setStatus(status);
		
		insImportLogDAO.saveEntityBean(logBean);
		
		return true;
	}
    
	private boolean processInner(List<InvoiceinsImportBean> list) throws MYException
	{
    	//  同一个发票号 + 客户 组合生成一张开票申请
		Map<String, List<InvoiceinsImportBean>> map = new HashMap<String, List<InvoiceinsImportBean>>();
		
    	for (InvoiceinsImportBean each : list) {
    		OutBean out = outDAO.find(each.getOutId());
    		
    		if (out == null) {
    			OutBalanceBean balance = outBalanceDAO.find(each.getOutId());
    			
    			if (null == balance) {
    				throw new MYException("库单[%s]不存在", each.getOutId());
    			} else {
    				if (each.getInvoiceId().equals("9999999999")) {
    					outBalanceDAO.updateInvoiceStatus(balance.getId(), each.getInvoiceMoney(), OutConstant.INVOICESTATUS_END);
    					
    					continue;
    				} else {
    					each.setCustomerId(balance.getCustomerId());
            			each.setType(FinanceConstant.INSVSOUT_TYPE_BALANCE);
            			each.setStafferId(balance.getStafferId());
    				}
    			}
    			
    		} else {
    			if (each.getInvoiceId().equals("9999999999")) {
    				outDAO.updateInvoiceStatus(out.getFullId(), each.getInvoiceMoney(), OutConstant.INVOICESTATUS_END);
    				
    				continue;
    			} else {
    				each.setCustomerId(out.getCustomerId());
        			each.setType(FinanceConstant.INSVSOUT_TYPE_OUT);
        			each.setStafferId(out.getStafferId());
    			}
    		}
    		
    		String key = each.getCustomerId() + "-" + each.getInvoiceNum();
    		
    		if (!map.containsKey(key)) {
    			List<InvoiceinsImportBean> mlist = new ArrayList<InvoiceinsImportBean>();
    			
    			mlist.add(each);
    			
    			map.put(key, mlist);
    		} else {
    			List<InvoiceinsImportBean> mlist = map.get(key);
    			
    			mlist.add(each);
    		}
    	}
    	
    	List<InvoiceinsBean> invoiceinsList = new ArrayList<InvoiceinsBean>();
    	
    	saveInner(map, invoiceinsList);
    	
    	// 调用审批通过
    	for (InvoiceinsBean bean : invoiceinsList) {
    		
    		InvoiceinsBean obean = invoiceinsDAO.find(bean.getId());

            if (obean == null)
            {
                throw new MYException("数据错误,请确认操作");
            }

            List<InsVSOutBean> vsList = insVSOutDAO.queryEntityBeansByFK(obean.getId(), AnoConstant.FK_FIRST);
            
            List<InvoiceinsItemBean> itemList = invoiceinsItemDAO.queryEntityBeansByFK(obean.getId());

            // 单据的开票状态需要更新
            if ( !ListTools.isEmptyOrNull(vsList))
            {
            	if (StringTools.isNullOrNone(vsList.get(0).getBaseId()))
            	{
            		for (InsVSOutBean insVSOutBean : vsList)
                    {
            			handlerEachInAdd(insVSOutBean);
                    }
            	}
            	// 0新模式标记 @@see InvoiceinsAction.createInsInNavigation1
            	else if (vsList.get(0).getBaseId().equals("0")){
            		
            		for (InvoiceinsItemBean item : itemList)
                    {
            			 // 新的开单规则
                        handlerEachInAdd3(item);
                    }
            	}else{
            		for (InsVSOutBean insVSOutBean : vsList)
                    {
            			handlerEachInAdd2(insVSOutBean);
                    }
            	}
            }
            
        	// 处理票款纳税实体一致  结束开票时打的标记
        	for  (InsVSOutBean vs : vsList) {
        		if (vs.getType() == FinanceConstant.INSVSOUT_TYPE_OUT) {
        			outDAO.updatePayInvoiceData(vs.getOutId(), OutConstant.OUT_PAYINS_TYPE_INVOICE, PublicConstant.MANAGER_TYPE_COMMON, PublicConstant.DEFAULR_DUTY_ID, 1);		
        		} else {
        			outBalanceDAO.updatePayInvoiceData(vs.getOutBalanceId(), OutConstant.OUT_PAYINS_TYPE_INVOICE, PublicConstant.MANAGER_TYPE_COMMON, PublicConstant.DEFAULR_DUTY_ID, 1);
        		}
        	}

            FlowLogBean log = new FlowLogBean();

            log.setActor("系统");
            log.setActorId(StafferConstant.SUPER_STAFFER);
            log.setFullId(obean.getId());
            log.setDescription("系统自动结束");
            log.setLogTime(TimeTools.now());
            log.setPreStatus(FinanceConstant.INVOICEINS_STATUS_SAVE);
            log.setAfterStatus(bean.getStatus());
            log.setOprMode(PublicConstant.OPRMODE_PASS);

            flowLogDAO.saveEntityBean(log);
    	}

        return true;
	}

	private void saveInner(Map<String, List<InvoiceinsImportBean>> map,
			List<InvoiceinsBean> invoiceinsList) throws MYException
	{
		DutyBean duty = dutyDAO.find(PublicConstant.DEFAULR_DUTY_ID);
    	
    	for (Map.Entry<String, List<InvoiceinsImportBean>> each : map.entrySet()) {
    		List<InvoiceinsImportBean> elist = each.getValue();
    		
    		InvoiceinsImportBean first = elist.get(0);
    		
    		// Assemble invoiceinsBean/invoiceinsItemBean/insVSOutBean/InsVSInvoiceNumBean
    		InvoiceinsBean bean = new InvoiceinsBean();
    		
    		bean.setId(commonDAO.getSquenceString20());
    		bean.setInvoiceDate(first.getInvoiceDate());
    		bean.setHeadType(0);
    		bean.setHeadContent(first.getInvoiceHead());
    		bean.setInvoiceId(first.getInvoiceId());
    		bean.setDutyId(PublicConstant.DEFAULR_DUTY_ID);
    		bean.setCustomerId(first.getCustomerId());
    		bean.setDescription("批量导入生成开票申请");
    		bean.setInsAmount(1);
    		bean.setLogTime(TimeTools.now());
    		bean.setLocationId("999");
    		//
    		bean.setMtype(PublicConstant.MANAGER_TYPE_COMMON);
    		bean.setOperator(StafferConstant.SUPER_STAFFER);
    		bean.setOperatorName("系统");
    		bean.setProcesser(duty.getInvoicer());
    		//
    		bean.setStatus(FinanceConstant.INVOICEINS_STATUS_END); // 直接结束
    		bean.setStafferId(first.getStafferId());
    		bean.setType(0);
    		bean.setOtype(0);
    		
    		List<InvoiceinsItemBean> itemList = new ArrayList<InvoiceinsItemBean>();
    		
    		List<InsVSOutBean> vsList = new ArrayList<InsVSOutBean>();
    		
    		List<InsVSInvoiceNumBean> numList = new ArrayList<InsVSInvoiceNumBean>();
    		
    		bean.setItemList(itemList);
    		bean.setVsList(vsList);
    		bean.setNumList(numList);
    		
    		double invoicemoney = 0.0d;
    		StringBuilder sb = new StringBuilder();
    		
    		for (InvoiceinsImportBean eachb : elist) {
    			
    			invoicemoney += eachb.getInvoiceMoney();
    			sb.append(eachb.getOutId());
    			sb.append(";");
    			
    			if (eachb.getType() == FinanceConstant.INSVSOUT_TYPE_OUT) {
    				List<BaseBean> baseList = baseDAO.queryEntityBeansByFK(eachb.getOutId());
    				
    				// eliminate return
                	ConditionParse con = new ConditionParse();

                    con.addWhereStr();

                    con.addCondition("OutBean.refOutFullId", "=", eachb.getOutId());

                    con.addIntCondition("OutBean.type", "=", OutConstant.OUT_TYPE_INBILL);

                    con.addIntCondition("OutBean.outType", "=", OutConstant.OUTTYPE_IN_OUTBACK);

                    List<OutBean> refOutList = outDAO.queryEntityBeansByCondition(con);
                    
                    for (OutBean eacho : refOutList)
                    {
                    	List<BaseBean> blist = baseDAO.queryEntityBeansByFK(eacho.getFullId());
                    	
                    	eacho.setBaseList(blist);
                    }
                    
                    // 计算出已经退货的数量                
                    for (BaseBean baseBean : baseList)
                    {
                        int hasBack = 0;

                        // 退库
                        for (OutBean ref : refOutList)
                        {
                            for (BaseBean refBase : ref.getBaseList())
                            {
                                if (refBase.equals2(baseBean))
                                {
                                    hasBack += refBase.getAmount();
                                }
                            }
                        }
                        
                        baseBean.setAmount(baseBean.getAmount() - hasBack);
                    }
                    
                    double vsMoney = 0.0d;
    				for (BaseBean eachitem : baseList) {
    					if (eachitem.getAmount() > 0) {
    						InvoiceinsItemBean item = new InvoiceinsItemBean();
        					
    						item.setId(commonDAO.getSquenceString20());
    						item.setParentId(bean.getId());
    						item.setShowId("10201103130001000189");
    						item.setShowName("纪念品");
    						item.setUnit("8874797");
    						item.setAmount(eachitem.getAmount());
    						item.setPrice(eachitem.getPrice());
    						item.setMoneys(item.getAmount() * item.getPrice());
    						item.setOutId(eachitem.getOutId());
    						item.setBaseId(eachitem.getId());
    						item.setProductId(eachitem.getProductId());
    						item.setType(eachb.getType());
    						item.setCostPrice(eachitem.getCostPrice());
    						
        					itemList.add(item);
        					
        					vsMoney += item.getMoneys();
    					}
    				}
    				
    				InsVSOutBean vsBean = new InsVSOutBean();
    				
    				vsBean.setId(commonDAO.getSquenceString20());
    				vsBean.setInsId(bean.getId());
    				vsBean.setOutId(eachb.getOutId());
    	        	vsBean.setType(eachb.getType());
    	        	vsBean.setMoneys(vsMoney);
    	        	vsBean.setBaseId("0"); // 标记
    				
    				vsList.add(vsBean);
    				
    			} else {
                	// 结算单
    				OutBalanceBean balance = outBalanceDAO.find(eachb.getOutId());
    				
                	List<BaseBalanceBean> baseBalanceList = baseBalanceDAO.queryEntityBeansByFK(balance.getId());
                	
                	List<BaseBean> baseList = new ArrayList<BaseBean>();
                	
                	for (BaseBalanceBean eachba : baseBalanceList)
                	{
                		String baseId = eachba.getBaseId();
                		
                		BaseBean baseBean = baseDAO.find(baseId);
                		
                		if (null != baseBean)
                		{
                			baseBean.setId(eachba.getId());
                			baseBean.setOutId(eachba.getParentId());
                			baseBean.setAmount(eachba.getAmount());
                			baseBean.setPrice(eachba.getSailPrice());
                			baseBean.setMtype(1);
                			baseBean.setDescription("");
                			baseBean.setLocationId(eachba.getBaseId());
                			
                			baseList.add(baseBean);
                		}
                	}
                	
                	// 结算单退货明细
                	List<BaseBalanceBean> refList = new ArrayList<BaseBalanceBean>();
                	
                	List<OutBalanceBean> balanceList = outBalanceDAO.queryEntityBeansByFK(eachb.getId(), AnoConstant.FK_FIRST);
                	
                	for (OutBalanceBean eachob : balanceList)
                	{
                		List<BaseBalanceBean> bbList = baseBalanceDAO.queryEntityBeansByFK(eachob.getId());
                		
                		refList.addAll(bbList);
                	}
                	
            		for (BaseBean baseBean : baseList)
                    {
                        int hasBack = 0;

                        // 申请开票除外
                        for (BaseBalanceBean eachbb : refList)
                        {
                            if (eachbb.getBaseId().equals(baseBean.getLocationId()))
                            {
                                hasBack += eachbb.getAmount();
                            }
                        }
                        
                        baseBean.setAmount(baseBean.getAmount() - hasBack);
                    }
            		
            		double vsMoney = 0.0d;
    				for (BaseBean eachitem : baseList) {
    					if (eachitem.getAmount() > 0) {
    						InvoiceinsItemBean item = new InvoiceinsItemBean();
        					
    						item.setId(commonDAO.getSquenceString20());
    						item.setParentId(bean.getId());
    						item.setShowId("10201103130001000189");
    						item.setShowName("纪念品");
    						item.setUnit("8874797");
    						item.setAmount(eachitem.getAmount());
    						item.setPrice(eachitem.getPrice());
    						item.setMoneys(item.getAmount() * item.getPrice());
    						item.setOutId(eachitem.getOutId());
    						item.setBaseId(eachitem.getId());
    						item.setProductId(eachitem.getProductId());
    						item.setType(eachb.getType());
    						item.setCostPrice(eachitem.getCostPrice());
    						
        					itemList.add(item);
        					
        					vsMoney += item.getMoneys();
    					}
    				}
    				
    				InsVSOutBean vsBean = new InsVSOutBean();
    				
    				vsBean.setId(commonDAO.getSquenceString20());
    				vsBean.setInsId(bean.getId());
    				vsBean.setOutId(balance.getOutId());
    				vsBean.setOutBalanceId(balance.getId());
    	        	vsBean.setType(eachb.getType());
    	        	vsBean.setMoneys(vsMoney);
    	        	vsBean.setBaseId("0"); // 标记
    				
    				vsList.add(vsBean);
    			}
    			
    			eachb.setRefInsId(bean.getId());
    			
    			invoiceinsImportDAO.updateEntityBean(eachb);
    		}
    		
    		bean.setMoneys(invoicemoney);
    		bean.setRefIds(sb.toString());
    		bean.setFillType(0);
    		
    		bean.setDescription(first.getDescription());
    		// fill distribution
    		if (first.getAddrType() == InvoiceinsConstants.INVOICEINS_DIST_NEW) {
    			bean.setShipping(first.getShipping());
            	bean.setTransport1(first.getTransport1());
            	bean.setTransport2(first.getTransport2());
            	bean.setProvinceId(first.getProvinceId());
            	bean.setCityId(first.getCityId());
            	bean.setAreaId(first.getAreaId());
            	bean.setAddress(first.getAddress());
            	bean.setReceiver(first.getReceiver());
            	bean.setMobile(first.getMobile());
            	bean.setTelephone(first.getTelephone());
            	bean.setExpressPay(first.getExpressPay());
            	bean.setTransportPay(first.getTransportPay());
    		} else {
    			bean.setFillType(InvoiceinsConstants.INVOICEINS_DIST_SAME);
    		}
        	
        	setDistFromDist(bean);
    		
    		// numList
    		InsVSInvoiceNumBean num = new InsVSInvoiceNumBean();
    		
    		num.setInsId(bean.getId());
    		num.setMoneys(bean.getMoneys());
    		num.setInvoiceNum(first.getInvoiceNum());
    		
    		numList.add(num);
    		
    		invoiceinsDAO.saveEntityBean(bean);
    		
    		invoiceinsItemDAO.saveAllEntityBeans(itemList);
    		
    		insVSOutDAO.saveAllEntityBeans(vsList);
    		
    		insVSInvoiceNumDAO.saveAllEntityBeans(numList);
    		
    		// package
    		createPackage(bean);
    		
    		invoiceinsList.add(bean);
    	}
	}

	public boolean processAsyn(final List<InvoiceinsImportBean> list)
	{
		Thread ithread = new Thread() {
			public void run() {
				try
				{
					process(list);
				}
				catch (MYException e)
				{
					operationLog.warn(e, e);
				}
			};
		};
		
		ithread.start();
		
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
     * @param commonDAO
     *            the commonDAO to set
     */
    public void setCommonDAO(CommonDAO commonDAO)
    {
        this.commonDAO = commonDAO;
    }

    /**
     * @return the invoiceinsDAO
     */
    public InvoiceinsDAO getInvoiceinsDAO()
    {
        return invoiceinsDAO;
    }

    /**
     * @param invoiceinsDAO
     *            the invoiceinsDAO to set
     */
    public void setInvoiceinsDAO(InvoiceinsDAO invoiceinsDAO)
    {
        this.invoiceinsDAO = invoiceinsDAO;
    }

    /**
     * @return the invoiceinsItemDAO
     */
    public InvoiceinsItemDAO getInvoiceinsItemDAO()
    {
        return invoiceinsItemDAO;
    }

    /**
     * @param invoiceinsItemDAO
     *            the invoiceinsItemDAO to set
     */
    public void setInvoiceinsItemDAO(InvoiceinsItemDAO invoiceinsItemDAO)
    {
        this.invoiceinsItemDAO = invoiceinsItemDAO;
    }

    /**
     * @return the insVSOutDAO
     */
    public InsVSOutDAO getInsVSOutDAO()
    {
        return insVSOutDAO;
    }

    /**
     * @param insVSOutDAO
     *            the insVSOutDAO to set
     */
    public void setInsVSOutDAO(InsVSOutDAO insVSOutDAO)
    {
        this.insVSOutDAO = insVSOutDAO;
    }

    /**
     * @return the outDAO
     */
    public OutDAO getOutDAO()
    {
        return outDAO;
    }

    /**
     * @param outDAO
     *            the outDAO to set
     */
    public void setOutDAO(OutDAO outDAO)
    {
        this.outDAO = outDAO;
    }

    /**
     * @return the outBalanceDAO
     */
    public OutBalanceDAO getOutBalanceDAO()
    {
        return outBalanceDAO;
    }

    /**
     * @param outBalanceDAO
     *            the outBalanceDAO to set
     */
    public void setOutBalanceDAO(OutBalanceDAO outBalanceDAO)
    {
        this.outBalanceDAO = outBalanceDAO;
    }

    /**
     * @return the baseDAO
     */
    public BaseDAO getBaseDAO()
    {
        return baseDAO;
    }

    /**
     * @param baseDAO
     *            the baseDAO to set
     */
    public void setBaseDAO(BaseDAO baseDAO)
    {
        this.baseDAO = baseDAO;
    }

    /**
     * @return the baseBalanceDAO
     */
    public BaseBalanceDAO getBaseBalanceDAO()
    {
        return baseBalanceDAO;
    }

    /**
     * @param baseBalanceDAO
     *            the baseBalanceDAO to set
     */
    public void setBaseBalanceDAO(BaseBalanceDAO baseBalanceDAO)
    {
        this.baseBalanceDAO = baseBalanceDAO;
    }

    /**
     * @return the dutyDAO
     */
    public DutyDAO getDutyDAO()
    {
        return dutyDAO;
    }

    /**
     * @param dutyDAO
     *            the dutyDAO to set
     */
    public void setDutyDAO(DutyDAO dutyDAO)
    {
        this.dutyDAO = dutyDAO;
    }

    /**
     * @return the flowLogDAO
     */
    public FlowLogDAO getFlowLogDAO()
    {
        return flowLogDAO;
    }

    /**
     * @param flowLogDAO
     *            the flowLogDAO to set
     */
    public void setFlowLogDAO(FlowLogDAO flowLogDAO)
    {
        this.flowLogDAO = flowLogDAO;
    }

	/**
	 * @return the invoiceStorageDAO
	 */
	public InvoiceStorageDAO getInvoiceStorageDAO()
	{
		return invoiceStorageDAO;
	}

	/**
	 * @param invoiceStorageDAO the invoiceStorageDAO to set
	 */
	public void setInvoiceStorageDAO(InvoiceStorageDAO invoiceStorageDAO)
	{
		this.invoiceStorageDAO = invoiceStorageDAO;
	}

	/**
	 * @return the invoiceBindOutDAO
	 */
	public InvoiceBindOutDAO getInvoiceBindOutDAO()
	{
		return invoiceBindOutDAO;
	}

	/**
	 * @param invoiceBindOutDAO the invoiceBindOutDAO to set
	 */
	public void setInvoiceBindOutDAO(InvoiceBindOutDAO invoiceBindOutDAO)
	{
		this.invoiceBindOutDAO = invoiceBindOutDAO;
	}

	/**
	 * @return the stockPayApplyDAO
	 */
	public StockPayApplyDAO getStockPayApplyDAO()
	{
		return stockPayApplyDAO;
	}

	/**
	 * @param stockPayApplyDAO the stockPayApplyDAO to set
	 */
	public void setStockPayApplyDAO(StockPayApplyDAO stockPayApplyDAO)
	{
		this.stockPayApplyDAO = stockPayApplyDAO;
	}

	/**
	 * @return the invoiceinsDetailDAO
	 */
	public InvoiceinsDetailDAO getInvoiceinsDetailDAO()
	{
		return invoiceinsDetailDAO;
	}

	/**
	 * @param invoiceinsDetailDAO the invoiceinsDetailDAO to set
	 */
	public void setInvoiceinsDetailDAO(InvoiceinsDetailDAO invoiceinsDetailDAO)
	{
		this.invoiceinsDetailDAO = invoiceinsDetailDAO;
	}

	/**
	 * @return the insVSInvoiceNumDAO
	 */
	public InsVSInvoiceNumDAO getInsVSInvoiceNumDAO()
	{
		return insVSInvoiceNumDAO;
	}

	/**
	 * @param insVSInvoiceNumDAO the insVSInvoiceNumDAO to set
	 */
	public void setInsVSInvoiceNumDAO(InsVSInvoiceNumDAO insVSInvoiceNumDAO)
	{
		this.insVSInvoiceNumDAO = insVSInvoiceNumDAO;
	}

	/**
	 * @return the invoiceinsTagDAO
	 */
	public InvoiceinsTagDAO getInvoiceinsTagDAO()
	{
		return invoiceinsTagDAO;
	}

	/**
	 * @param invoiceinsTagDAO the invoiceinsTagDAO to set
	 */
	public void setInvoiceinsTagDAO(InvoiceinsTagDAO invoiceinsTagDAO)
	{
		this.invoiceinsTagDAO = invoiceinsTagDAO;
	}

	/**
	 * @return the invoiceinsImportDAO
	 */
	public InvoiceinsImportDAO getInvoiceinsImportDAO()
	{
		return invoiceinsImportDAO;
	}

	/**
	 * @param invoiceinsImportDAO the invoiceinsImportDAO to set
	 */
	public void setInvoiceinsImportDAO(InvoiceinsImportDAO invoiceinsImportDAO)
	{
		this.invoiceinsImportDAO = invoiceinsImportDAO;
	}

	/**
	 * @return the insImportLogDAO
	 */
	public InsImportLogDAO getInsImportLogDAO()
	{
		return insImportLogDAO;
	}

	/**
	 * @param insImportLogDAO the insImportLogDAO to set
	 */
	public void setInsImportLogDAO(InsImportLogDAO insImportLogDAO)
	{
		this.insImportLogDAO = insImportLogDAO;
	}

	/**
	 * @return the attachmentDAO
	 */
	public AttachmentDAO getAttachmentDAO()
	{
		return attachmentDAO;
	}

	/**
	 * @param attachmentDAO the attachmentDAO to set
	 */
	public void setAttachmentDAO(AttachmentDAO attachmentDAO)
	{
		this.attachmentDAO = attachmentDAO;
	}

	/**
	 * @return the productDAO
	 */
	public ProductDAO getProductDAO() {
		return productDAO;
	}

	/**
	 * @param productDAO the productDAO to set
	 */
	public void setProductDAO(ProductDAO productDAO) {
		this.productDAO = productDAO;
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

	public DistributionDAO getDistributionDAO() {
		return distributionDAO;
	}

	public void setDistributionDAO(DistributionDAO distributionDAO) {
		this.distributionDAO = distributionDAO;
	}

	public PreConsignDAO getPreConsignDAO() {
		return preConsignDAO;
	}

	public void setPreConsignDAO(PreConsignDAO preConsignDAO) {
		this.preConsignDAO = preConsignDAO;
	}

	/**
	 * @return the packageManager
	 */
	public PackageManager getPackageManager() {
		return packageManager;
	}

	/**
	 * @param packageManager the packageManager to set
	 */
	public void setPackageManager(PackageManager packageManager) {
		this.packageManager = packageManager;
	}
}
