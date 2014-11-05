/**
 * File Name: StockManagerImpl.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2010-9-20<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.stock.manager.impl;


import java.util.Collection;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.china.center.spring.ex.annotation.Exceptional;
import org.springframework.dao.DataAccessException;
import org.springframework.transaction.annotation.Transactional;

import com.center.china.osgi.publics.AbstractListenerManager;
import com.center.china.osgi.publics.User;
import com.china.center.common.MYException;
import com.china.center.oa.note.bean.ShortMessageTaskBean;
import com.china.center.oa.note.constant.ShortMessageConstant;
import com.china.center.oa.note.dao.ShortMessageTaskDAO;
import com.china.center.oa.product.bean.ProductBean;
import com.china.center.oa.product.dao.ProductDAO;
import com.china.center.oa.publics.bean.FlowLogBean;
import com.china.center.oa.publics.bean.LocationBean;
import com.china.center.oa.publics.bean.StafferBean;
import com.china.center.oa.publics.constant.AuthConstant;
import com.china.center.oa.publics.constant.PublicConstant;
import com.china.center.oa.publics.constant.SysConfigConstant;
import com.china.center.oa.publics.dao.CommonDAO;
import com.china.center.oa.publics.dao.FlowLogDAO;
import com.china.center.oa.publics.dao.LocationDAO;
import com.china.center.oa.publics.dao.ParameterDAO;
import com.china.center.oa.publics.dao.StafferDAO;
import com.china.center.oa.publics.helper.AuthHelper;
import com.china.center.oa.publics.helper.OATools;
import com.china.center.oa.stock.bean.PriceAskBean;
import com.china.center.oa.stock.bean.PriceAskProviderBean;
import com.china.center.oa.stock.bean.StockBean;
import com.china.center.oa.stock.bean.StockItemBean;
import com.china.center.oa.stock.bean.StockWorkBean;
import com.china.center.oa.stock.constant.PriceConstant;
import com.china.center.oa.stock.constant.StockConstant;
import com.china.center.oa.stock.dao.PriceAskDAO;
import com.china.center.oa.stock.dao.PriceAskProviderDAO;
import com.china.center.oa.stock.dao.StockDAO;
import com.china.center.oa.stock.dao.StockItemDAO;
import com.china.center.oa.stock.dao.StockWorkDAO;
import com.china.center.oa.stock.listener.StockListener;
import com.china.center.oa.stock.manager.PriceAskManager;
import com.china.center.oa.stock.manager.StockManager;
import com.china.center.oa.stock.vo.PriceAskProviderBeanVO;
import com.china.center.oa.stock.vo.StockItemVO;
import com.china.center.oa.stock.vo.StockVO;
import com.china.center.tools.JudgeTools;
import com.china.center.tools.MathTools;
import com.china.center.tools.RandomTools;
import com.china.center.tools.SequenceTools;
import com.china.center.tools.StringTools;
import com.china.center.tools.TimeTools;


/**
 * StockManagerImpl
 * 
 * @author ZHUZHU
 * @version 2010-9-20
 * @see StockManagerImpl
 * @since 1.0
 */
@Exceptional
public class StockManagerImpl extends AbstractListenerManager<StockListener> implements StockManager
{
    private StockDAO stockDAO = null;

    private CommonDAO commonDAO = null;
    
    private final Log _logger = LogFactory.getLog(getClass());

    /**
     * 流程日志
     */
    private FlowLogDAO flowLogDAO = null;

    private StockItemDAO stockItemDAO = null;

    private LocationDAO locationDAO = null;

    private ParameterDAO parameterDAO = null;

    private StafferDAO stafferDAO = null;

    private ProductDAO productDAO = null;

    private PriceAskManager priceAskManager = null;

    private PriceAskDAO priceAskDAO = null;

    private ShortMessageTaskDAO shortMessageTaskDAO = null;

    private String stockLocation = "";

    private PriceAskProviderDAO priceAskProviderDAO = null;
    
    private StockWorkDAO stockWorkDAO = null;

    /*
     * (non-Javadoc)
     * 
     * @see com.china.center.oa.stock.manager.StockManager#addBean(com.center.china.osgi.publics.User,
     *      com.china.center.oa.stock.bean.StockBean)
     */
    @Transactional(rollbackFor = MYException.class)
    public boolean addStockBean(User user, StockBean bean)
        throws MYException
    {
        JudgeTools.judgeParameterIsNull(bean);

        LocationBean lb = locationDAO.find(bean.getLocationId());

        if (lb == null)
        {
            throw new MYException("区域不存在");
        }

        // 修改为定长
        bean.setId(lb.getCode() + "_CG" + TimeTools.now("yyyyMMddHHmm")
                   + commonDAO.getSquenceString());

        bean.setStatus(StockConstant.STOCK_STATUS_INIT);

        if (StringTools.isNullOrNone(bean.getStafferId()))
        {
            bean.setStafferId(user.getStafferId());
        }

        // 保存采购主单据
        stockDAO.saveEntityBean(bean);
        
        if (bean.getMtype() == StockConstant.MANAGER_TYPE_COMMON2
        		|| bean.getMtype() == StockConstant.MANAGER_TYPE_COMMON3)
        {
        	bean.setMtype(StockConstant.MANAGER_TYPE_COMMON);
        }

        // 保存采购的产品
        List<StockItemBean> items = bean.getItem();

        for (StockItemBean stockItemBean : items)
        {
            stockItemBean.setStockId(bean.getId());

            if (OATools.getManagerFlag())
            {
                ProductBean product = productDAO.find(stockItemBean.getProductId());

                // 判断是否同类
                /*if ( !String.valueOf(bean.getMtype()).equals(product.getReserve4()))
                {
                    throw new MYException("产品的管理类型和采购的管理类型不一致");
                }*/
                stockItemBean.setMtype(MathTools.parseInt(product.getReserve4()));
                
            }

            // stockItemBean.setMtype(bean.getMtype());

            stockItemDAO.saveEntityBean(stockItemBean);
        }

        return true;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.china.center.oa.stock.manager.StockManager#delStockBean(com.center.china.osgi.publics.User,
     *      java.lang.String)
     */
    @Transactional(rollbackFor = MYException.class)
    public boolean delStockBean(User user, String id)
        throws MYException
    {
        JudgeTools.judgeParameterIsNull(user, id);

        StockBean sb = stockDAO.find(id);

        if (sb == null)
        {
            throw new MYException("采购单不存在");
        }

        if ( !user.getId().equals(sb.getUserId()))
        {
            throw new MYException("没有权限");
        }

        if (sb.getStatus() != StockConstant.STOCK_STATUS_REJECT
            && sb.getStatus() != StockConstant.STOCK_STATUS_INIT)
        {
            throw new MYException("采购单不存在初始或者驳回状态不能删除");
        }

        // 更新采购主单据
        stockDAO.deleteEntityBean(id);

        // 先删除
        stockItemDAO.deleteEntityBeansByFK(id);

        flowLogDAO.deleteEntityBeansByFK(id);

        return true;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.china.center.oa.stock.manager.StockManager#endStock(com.center.china.osgi.publics.User,
     *      java.lang.String)
     */
    @Transactional(rollbackFor = MYException.class)
    public String endStock(User user, String id, String reason)
        throws MYException
    {
        JudgeTools.judgeParameterIsNull(user, id);

        String fullId = "";

        StockBean bean = stockDAO.find(id);

        if (bean == null)
        {
            throw new MYException("采购单不存在");
        }

        // 结束和和经理通过都是可以结束采购单的
        if (bean.getStatus() != StockConstant.STOCK_STATUS_END
            && bean.getStatus() != StockConstant.STOCK_STATUS_STOCKMANAGERPASS)
        {
            throw new MYException("采购单不存在采购中,不能结束采购");
        }

        // 更新状态并且记录日志
        updateStockStatus(user, id, StockConstant.STOCK_STATUS_LASTEND,
            PublicConstant.OPRMODE_PASS, "采购结束");

        // 更新发货备注
        stockDAO.updateConsign(id, reason);

        return fullId;
    }

    /**
     * 更新采购单的状态
     * 
     * @param bean
     * @return
     * @throws MYException
     */
    private boolean updateStockStatus(final User user, final String id, int nextStatus,
                                      int oprMode, String reason)
        throws MYException
    {
        JudgeTools.judgeParameterIsNull(user, id);

        StockBean old = stockDAO.find(id);

        if (old == null)
        {
            throw new MYException("采购单不存在");
        }

        // 更新采购主单据
        stockDAO.updateStatus(id, nextStatus);

        // 只有驳回
        if (oprMode == PublicConstant.OPRMODE_REJECT)
        {
            // 回退信息
            old.setWillDate(0);

            old.setDutyId("");

            old.setInvoice(0);

            old.setInvoiceType("");

            old.setExceptStatus(StockConstant.EXCEPTSTATUS_COMMON);

            old.setStatus(nextStatus);

            // 更新采购主单据
            stockDAO.updateEntityBean(old);
        }

        addLog(user, id, nextStatus, old, oprMode, reason);

        return true;
    }

    /**
     * 增加log
     * 
     * @param user
     * @param id
     * @param status
     * @param sb
     */
    private void addLog(final User user, final String id, int status, StockBean sb, int oprMode,
                        String reason)
    {
        FlowLogBean log = new FlowLogBean();

        log.setActor(user.getStafferName());

        log.setLogTime(TimeTools.now());

        log.setFullId(id);

        // 操作类型
        log.setOprMode(oprMode);

        log.setPreStatus(sb.getStatus());

        log.setAfterStatus(status);

        log.setDescription(reason);

        flowLogDAO.saveEntityBean(log);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.china.center.oa.stock.manager.StockManager#findStockVO(java.lang.String)
     */
    public StockVO findStockVO(String id)
    {
        StockVO vo = stockDAO.findVO(id);

        List<StockItemVO> itemVO = stockItemDAO.queryEntityVOsByFK(id);

        vo.setItemVO(itemVO);

        if (StringTools.isNullOrNone(vo.getOwerName()))
        {
            vo.setOwerName("公共");
        }

        return vo;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.china.center.oa.stock.manager.StockManager#passStock(com.center.china.osgi.publics.User,
     *      java.lang.String)
     */
    @Transactional(rollbackFor = {MYException.class, DataAccessException.class})
    public boolean passStock(User user, String id)
        throws MYException
    {
        JudgeTools.judgeParameterIsNull(user, id);

        StockVO sb = stockDAO.findVO(id);

        if (sb == null)
        {
            throw new MYException("采购单不存在");
        }

        int nextStatus = getNextStatus(user, sb);

        List<StockItemVO> itemList = stockItemDAO.queryEntityVOsByFK(id);

        double total = 0.0d;

        // 如果是结束需要验证是否是外网询价
        checkEndStock(sb, nextStatus, itemList);

        // 待采购拿货 需要全部的item都询价结束
        if (nextStatus == StockConstant.STOCK_STATUS_STOCKMANAGERPASS
            || nextStatus == StockConstant.STOCK_STATUS_STOCKPASS 
            || nextStatus == StockConstant.STOCK_STATUS_STOCKPASS_DUTYCEO
            || nextStatus == StockConstant.STOCK_STATUS_STOCKPASS_CEO)
        {
            for (StockItemBean stockItemBean : itemList)
            {
                if (stockItemBean.getStatus() != StockConstant.STOCK_ITEM_STATUS_ASK)
                {
                    throw new MYException("采购单下存在没有询价的产品,不能通过");
                }

                total += stockItemBean.getTotal();
            }

            stockDAO.updateTotal(id, total);
        }

        String reason = "";

        // 区域经理通过的时候,要是代销采购直接跳过询价
        if (nextStatus == StockConstant.STOCK_STATUS_MANAGERPASS)
        {
            // 代销采购
            if (sb.getStype() == StockConstant.STOCK_STYPE_ISAIL)
            {
                nextStatus = StockConstant.STOCK_STATUS_PRICEPASS;

                total = 0.0d;

                // 这里直接处理无需询价的逻辑
                for (StockItemBean stockItemBean : itemList)
                {
                    ProductBean product = productDAO.find(stockItemBean.getProductId());

                    if (StringTools.isNullOrNone(product.getMainProvider()))
                    {
                        throw new MYException("产品[%s]没有主供应商,不能通过", product.getName());
                    }

                    stockItemBean.setProviderId(product.getMainProvider());

                    stockItemBean.setStatus(StockConstant.STOCK_ITEM_STATUS_ASK);

                    // 自动拿货
                    stockItemBean.setFechProduct(StockConstant.STOCK_ITEM_FECH_YES);

                    stockItemBean.setPrice(stockItemBean.getPrePrice());

                    stockItemBean.setTotal(stockItemBean.getPrice() * stockItemBean.getAmount());

                    stockItemDAO.updateEntityBean(stockItemBean);

                    total += stockItemBean.getTotal();
                }

                stockDAO.updateTotal(id, total);

                reason = "代销采购无需询价";
            }
            else
            {
                // 直接生成询价单(每个采购项一个)
                for (StockItemBean stockItemBean : itemList)
                {
                    PriceAskBean autoAskBean = setAutoAskBean(sb, stockItemBean, user);

                    priceAskManager.addPriceAskBeanWithoutTransactional(user, autoAskBean);
                }
            }
        }

        // 采购主管通过后到 单比金额大于5万，或者是选择价格不是最低，需要采购经理审核
        // 0 - 3W 
        // 3 - 50W
        // 50 - 100W
        // 100W - 
        if (sb.getStatus() == StockConstant.STOCK_STATUS_PRICEPASS)
        {
            // 检查是否询价
            for (StockItemBean stockItemBean : itemList)
            {
                if (stockItemBean.getStatus() != StockConstant.STOCK_ITEM_STATUS_ASK)
                {
                    throw new MYException("采购单下存在没有询价的产品,不能通过");
                }
            }

//            if ( !checkMin(itemList))
//            {
//                stockDAO.updateExceptStatus(id, StockConstant.EXCEPTSTATUS_EXCEPTION_MIN);
//
//                nextStatus = StockConstant.STOCK_STATUS_STOCKPASS_CEO;
//            }

//            StringBuilder sbStr = new StringBuilder();
//
//            if ( !checkItemMoney(itemList, sbStr))
//            {
//                stockDAO.updateExceptStatus(id, StockConstant.EXCEPTSTATUS_EXCEPTION_MONEY);
//
//                // 获得董事长的人员
//                List<StafferBean> sbList = stafferDAO
//                    .queryStafferByAuthId(AuthConstant.STOCK_NOTICE_CHAIRMA);
//
//                for (StafferBean stafferBean : sbList)
//                {
//                    sendSMSInner(user, stafferBean, id, sb, sbStr.toString());
//                }
//
//                nextStatus = StockConstant.STOCK_STATUS_STOCKPASS_CEO;
//            }
        }

        // 如果是采购代销的采购主管通过直接到
        if (nextStatus == StockConstant.STOCK_STATUS_STOCKMANAGERPASS
        		|| nextStatus == StockConstant.STOCK_STATUS_STOCKPASS_DUTYCEO
        		|| nextStatus == StockConstant.STOCK_STATUS_STOCKPASS_CEO)
        {
            for (StockItemVO stockItemVO : itemList)
            {
                // 这里先校验是否已经配置
                if (StringTools.isNullOrNone(stockItemVO.getDutyId()))
                {
                    throw new MYException("请先配置税务属性");
                }
            }

            // 代销采购
            if (sb.getStype() == StockConstant.STOCK_STYPE_ISAIL)
            {
                // 直接到待结束采购
                nextStatus = StockConstant.STOCK_STATUS_END;
            }
        }

        // 当结束的时候 把各个item的状态标志成结束(给询价作为参考)
        if (nextStatus == StockConstant.STOCK_STATUS_END)
        {
            // 检验是否都已经入库
            boolean all = true;

            for (StockItemBean stockItemBean : itemList)
            {
                if (stockItemBean.getFechProduct() == StockConstant.STOCK_ITEM_FECH_NO)
                {
                    all = false;

                    break;
                }
            }

            if ( !all)
            {
                throw new MYException("还没有全部拿货不能通过");
            }

            for (StockItemBean stockItemBean2 : itemList)
            {
                // 更新状态
                stockItemBean2.setStatus(StockConstant.STOCK_ITEM_STATUS_END);

                // 更新记录时间
                stockItemBean2.setLogTime(TimeTools.now());

                stockItemDAO.updateEntityBean(stockItemBean2);
            }
        }

        // 生成采购付款申请
        if (nextStatus == StockConstant.STOCK_STATUS_STOCKMANAGERPASS)
        {
        	// 采购入库
            Collection<StockListener> listenerMapValues = this.listenerMapValues();
            
            for (StockListener stockListener : listenerMapValues)
            {
            	for (StockItemVO item : itemList)
            	{
            		stockListener.onWaitFetchStock(user, sb, item);            		
            	}
            }
        }
        
        // 处理外网询价的特殊流程
        if (nextStatus == StockConstant.STOCK_STATUS_MANAGERPASS
            && sb.getType() == PriceConstant.PRICE_ASK_TYPE_NET)
        {
            // 直接到(采购主管)
            nextStatus = StockConstant.STOCK_STATUS_PRICEPASS;

            reason = "";
        }

        updateStockStatus(user, id, nextStatus, PublicConstant.OPRMODE_PASS, reason);

        return true;
    }

    /**
     * 自动生成采购询价
     * 
     * @param itemId
     * @param item
     * @param user
     * @param bean
     */
    private PriceAskBean setAutoAskBean(StockBean stock, StockItemBean item, User user)
    {
        PriceAskBean bean = new PriceAskBean();

        bean.setId(SequenceTools.getSequence("ASK", 5));

        bean.setProductId(item.getProductId());

        bean.setAmount(item.getAmount());

        bean.setSrcamount(item.getAmount());

        // 默认是内外网询价的
        bean.setType(PriceConstant.PRICE_ASK_TYPE_BOTH);

        if (stock.getMode() == StockConstant.STOCK_MODE_SAIL)
        {
            bean.setSrc(PriceConstant.PRICE_ASK_SRC_STOCK);
        }
        else
        {
            bean.setSrc(PriceConstant.PRICE_ASK_SRC_MAKE);
        }

        bean.setRefStock(stock.getId());

        bean.setAreaId(stock.getAreaId());

        bean.setUserId(user.getId());

        bean.setLogTime(TimeTools.now());

        bean.setStatus(PriceConstant.PRICE_COMMON);

        bean.setLocationId(user.getLocationId());

        bean.setDescription(stock.getId() + "自动生成的询价单");

        ProductBean product = productDAO.find(bean.getProductId());

        if (product != null)
        {
            bean.setProductType(product.getType());
        }

        bean.setUserId(stock.getUserId());

        bean.setLocationId(stock.getLocationId());

        bean.setProcessTime(TimeTools.getDateString(1, TimeTools.LONG_FORMAT));

        bean.setAskDate(TimeTools.getDateString(1, "yyyyMMdd"));

        bean.setSrcamount(bean.getAmount());

        bean.setStockMode(stock.getMode());

        return bean;
    }

    /**
     * 检查采购的是否是最小的价格
     * 
     * @param item
     * @return
     */
    private boolean checkMin(List<StockItemVO> item)
    {
        List<PriceAskProviderBean> ppbs = null;

        for (StockItemBean stockItemBean : item)
        {
            ppbs = priceAskProviderDAO.queryEntityBeansByFK(stockItemBean.getId());

            double min = Integer.MAX_VALUE;
            for (PriceAskProviderBean priceAskProviderBean : ppbs)
            {
                min = Math.min(priceAskProviderBean.getPrice(), min);
            }

            if (stockItemBean.getPrice() > min)
            {
                return false;
            }
        }

        return true;
    }

    /**
     * 检查采购的单比价格有没有超过5万的
     * 
     * @param item
     * @return
     */
    private boolean checkItemMoney(List<StockItemVO> item, StringBuilder sbStr)
    {
        int max = parameterDAO.getInt(SysConfigConstant.STOCK_MAX_SINGLE_MONEY);

        for (StockItemVO stockItemBean : item)
        {
            if (stockItemBean.getTotal() >= max)
            {
                sbStr.append("[" + stockItemBean.getProductName() + "]的采购金额为:"
                             + MathTools.formatNum(stockItemBean.getTotal()) + ",请注意");

                return false;
            }
        }

        return true;
    }
    
    /**
     * 0 - 3
     * 3 - 50
     * 50 - 100
     * 100 - 
     * @param item
     * @param sbStr
     * @return
     */
    private int checkMoney(StockBean stock)
    {
        if (stock.getTotal() <= 30000) {
        	return StockConstant.STOCK_STATUS_STOCKMANAGERPASS;
        } else if (stock.getTotal() > 30000 && stock.getTotal() <= 500000) {
        	return StockConstant.STOCK_STATUS_STOCKPASS_DUTYCEO;
        } else if (stock.getTotal() > 500000 && stock.getTotal() <= 1000000) {
        	return StockConstant.STOCK_STATUS_STOCKPASS_CEO;
        } else {
        	return StockConstant.STOCK_STATUS_STOCKPASS;
        }
    }

    /**
     * checkSubmit
     * 
     * @param sb
     * @param nextStatus
     * @param itemList
     * @throws MYException
     */
    private void checkEndStock(StockBean sb, int nextStatus, List<StockItemVO> itemList)
        throws MYException
    {
        if (nextStatus == StockConstant.STOCK_STATUS_END
            && sb.getType() == PriceConstant.PRICE_ASK_TYPE_NET)
        {
            // 需要校验数量是
            for (StockItemBean iitem : itemList)
            {
                if (StringTools.isNullOrNone(iitem.getPriceAskProviderId()))
                {
                    throw new MYException("数据错误,请重新操作");
                }

                // 事务内已经被使用的
                int sum = stockItemDAO.sumNetProductByPid(iitem.getPriceAskProviderId());

                PriceAskProviderBeanVO ppb = priceAskProviderDAO.findVO(iitem
                    .getPriceAskProviderId());

                if (ppb == null)
                {
                    throw new MYException("数据错误,请重新操作");
                }

                if (ppb.getSupportAmount() < sum)
                {
                    throw new MYException(
                        "外网采购中供应商[%s]提供的产品[%s]数量只有%d已经使用了%d,你请求的采购数量[%d]已经超出(可能其他业务员已经采购一空)", ppb
                            .getProviderName(), ppb.getProductName(), ppb.getSupportAmount(),
                        (sum - iitem.getAmount()), iitem.getAmount());
                }
            }
        }
    }

    /**
     * 获得下一个状态
     * 
     * @param current
     * @param isPass
     * @return
     * @throws MYException
     */
    private int getNextStatus(User user, StockBean stock)
        throws MYException
    {
    	int current = stock.getStatus();
    	
        if (current == StockConstant.STOCK_STATUS_INIT)
        {
            return StockConstant.STOCK_STATUS_MANAGERPASS;
        }

        if (current == StockConstant.STOCK_STATUS_REJECT)
        {
            return StockConstant.STOCK_STATUS_MANAGERPASS;
        }

//        if (current == StockConstant.STOCK_STATUS_SUBMIT)
//        {
//            return StockConstant.STOCK_STATUS_MANAGERPASS;
//        }

        if (current == StockConstant.STOCK_STATUS_MANAGERPASS)
        {
            if (AuthHelper.containAuth(user, AuthConstant.STOCK_MANAGER_PASS))
            {
                return StockConstant.STOCK_STATUS_PRICEPASS;
            }
            else
            {
                throw new MYException("没有权限");
            }
        }

        // 这里是采购主管的操作(如果没有异常忽略采购经理)
        if (current == StockConstant.STOCK_STATUS_PRICEPASS)
        {
        	if (checkMoney(stock) == StockConstant.STOCK_STATUS_STOCKMANAGERPASS) {
        		return StockConstant.STOCK_STATUS_STOCKMANAGERPASS;	
        	} else {
        		return StockConstant.STOCK_STATUS_STOCKPASS_DUTYCEO;
        	}
        }

        if (current == StockConstant.STOCK_STATUS_STOCKPASS_DUTYCEO)
        {
        	if (checkMoney(stock) == StockConstant.STOCK_STATUS_STOCKPASS_DUTYCEO) {
        		return StockConstant.STOCK_STATUS_STOCKMANAGERPASS;
        	} else {
        		return StockConstant.STOCK_STATUS_STOCKPASS_CEO;
        	}
        }
        
        if (current == StockConstant.STOCK_STATUS_STOCKPASS_CEO)
        {
        	if (checkMoney(stock) == StockConstant.STOCK_STATUS_STOCKPASS_CEO) {
        		return StockConstant.STOCK_STATUS_STOCKMANAGERPASS;
        	} else {
        		return StockConstant.STOCK_STATUS_STOCKPASS;
        	}
        }
        
        if (current == StockConstant.STOCK_STATUS_STOCKPASS)
        {
            if (AuthHelper.containAuth(user, AuthConstant.STOCK_NOTICE_CHAIRMA))
            {
                return StockConstant.STOCK_STATUS_STOCKMANAGERPASS;
            }
            else
            {
                throw new MYException("没有权限");
            }
        }

        if (current == StockConstant.STOCK_STATUS_STOCKMANAGERPASS)
        {
            return StockConstant.STOCK_STATUS_END;
        }

        return StockConstant.STOCK_STATUS_INIT;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.china.center.oa.stock.manager.StockManager#rejectStock(com.center.china.osgi.publics.User,
     *      java.lang.String, java.lang.String)
     */
    @Transactional(rollbackFor = {MYException.class})
    public boolean rejectStock(User user, String id, String reason)
        throws MYException
    {
        JudgeTools.judgeParameterIsNull(user, id);

        StockBean sb = stockDAO.find(id);

        if (sb == null)
        {
            throw new MYException("采购单不存在");
        }

        if (sb.getStatus() == StockConstant.STOCK_STATUS_REJECT
            || sb.getStatus() == StockConstant.STOCK_STATUS_INIT
            || sb.getStatus() == StockConstant.STOCK_STATUS_END
            || sb.getStatus() == StockConstant.STOCK_STATUS_LASTEND)
        {
            throw new MYException("采购单状态错误");
        }

        int nextStatus = StockConstant.STOCK_STATUS_REJECT;

        recoverStockItemAsk(id);

        return updateStockStatus(user, id, nextStatus, PublicConstant.OPRMODE_REJECT, reason);
    }

    /**
     * 恢复采购询价的初始状态
     * 
     * @param id
     */
    private void recoverStockItemAsk(final String id)
    {
        List<StockItemBean> item = stockItemDAO.queryEntityBeansByFK(id);

        // 删除询价
        for (StockItemBean stockItemBean : item)
        {
            stockItemBean.setProviderId("");

            stockItemBean.setPriceAskProviderId("");

            stockItemBean.setStatus(StockConstant.STOCK_ITEM_STATUS_INIT);

            stockItemBean.setPrice(0.0d);

            stockItemBean.setTotal(0.0d);

            // 先修改item的一些值
            stockItemDAO.updateEntityBean(stockItemBean);

            // 删除先前的询价
            priceAskProviderDAO.deleteEntityBeansByFK(stockItemBean.getId());
        }

        // 删除自动生成的询价单
        List<PriceAskBean> refAskList = priceAskDAO.queryEntityBeansByFK(id);

        for (PriceAskBean priceAskBean : refAskList)
        {
            // 删除具体的报价
            priceAskProviderDAO.deleteEntityBeansByFK(priceAskBean.getId());
        }

        // 删除自动询价单
        priceAskDAO.deleteEntityBeansByFK(id);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.china.center.oa.stock.manager.StockManager#rejectStockToAsk(com.center.china.osgi.publics.User,
     *      java.lang.String, java.lang.String)
     */
    @Transactional(rollbackFor = {MYException.class})
    public boolean rejectStockToAsk(User user, String id, String reason)
        throws MYException
    {
        JudgeTools.judgeParameterIsNull(user, id);

        StockBean sb = stockDAO.find(id);

        if (sb == null)
        {
            throw new MYException("采购单不存在");
        }

        // 只有在询价员通过下才能驳回到询价员
        if (sb.getStatus() != StockConstant.STOCK_STATUS_PRICEPASS)
        {
            throw new MYException("采购单状态错误");
        }

        if ( !AuthHelper.containAuth(user, AuthConstant.STOCK_INNER_STOCK_PASS))
        {
            throw new MYException("不能操作");
        }

        recoverStockItemAsk(id);

        return updateStockStatus(user, id, StockConstant.STOCK_STATUS_MANAGERPASS,
            PublicConstant.OPRMODE_REJECT, reason);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.china.center.oa.stock.manager.StockManager#stockItemAsk(com.china.center.oa.stock.bean.StockItemBean)
     */
    @Transactional(rollbackFor = {MYException.class})
    public boolean stockItemAsk(StockItemBean bean)
        throws MYException
    {
        JudgeTools.judgeParameterIsNull(bean);

        bean.setStatus(StockConstant.STOCK_ITEM_STATUS_ASK);

        bean.setTotal(bean.getPrice() * bean.getAmount());

        // 先修改item的一些值
        stockItemDAO.updateEntityBean(bean);

        // 删除先前的询价
        priceAskProviderDAO.deleteEntityBeansByFK(bean.getId());

        for (PriceAskProviderBean priceAskProviderBean : bean.getAsks())
        {
            priceAskProviderDAO.saveEntityBean(priceAskProviderBean);
        }

        return true;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.china.center.oa.stock.manager.StockManager#stockItemAskChange(java.lang.String, java.lang.String)
     */
    @Transactional(rollbackFor = {MYException.class})
    public boolean stockItemAskChange(String itemId, String providerId)
        throws MYException
    {
        JudgeTools.judgeParameterIsNull(itemId, providerId);

        PriceAskProviderBean bean = priceAskProviderDAO.findBeanByAskIdAndProviderId(itemId,
            providerId, PriceConstant.PRICE_ASK_TYPE_INNER);

        if (bean == null)
        {
            throw new MYException("系统错误");
        }

        StockItemBean item = stockItemDAO.find(itemId);

        if (item == null)
        {
            throw new MYException("系统错误");
        }

        item.setProviderId(providerId);

        item.setPrice(bean.getPrice());

        item.setTotal(bean.getPrice() * item.getAmount());

        // 更新item
        stockItemDAO.updateEntityBean(item);

        List<StockItemBean> items = stockItemDAO.queryEntityBeansByFK(item.getStockId());

        double total = 0.0d;

        for (StockItemBean stockItemBean : items)
        {
            if (stockItemBean.getStatus() != StockConstant.STOCK_ITEM_STATUS_ASK)
            {
                throw new MYException("采购单下存在没有询价的产品,不能通过");
            }

            total += stockItemBean.getTotal();
        }

        // 更新采购单据的总金额
        stockDAO.updateTotal(item.getStockId(), total);

        return true;
    }

    /**
     * fechProduct
     * 
     * @param itemId
     * @param providerId
     * @return
     * @throws MYException
     */
    @Transactional(rollbackFor = {MYException.class})
    public boolean fechProduct(User user, String itemId, String depotpartId)
        throws MYException
    {
        JudgeTools.judgeParameterIsNull(itemId, user, depotpartId);

        StockItemBean item = stockItemDAO.find(itemId);
        
        if (item == null)
        {
            throw new MYException("系统错误");
        }
        
        if (item.getExtraStatus() == 0)
        {
        	throw new MYException("需进行采购入库预确认.");
        }

        StockBean stock = stockDAO.findVO(item.getStockId());

        if (stock == null)
        {
            throw new MYException("数据错误,请确认操作");
        }

        if (item.getFechProduct() == StockConstant.STOCK_ITEM_FECH_YES)
        {
            throw new MYException("已经拿货");
        }

        item.setFechProduct(StockConstant.STOCK_ITEM_FECH_YES);

        item.setDepotpartId(depotpartId);

        // 更新item
        stockItemDAO.updateEntityBean(item);

        // 采购入库
        Collection<StockListener> listenerMapValues = this.listenerMapValues();
        for (StockListener stockListener : listenerMapValues)
        {
            stockListener.onEndStockItem(user, stock, item);
        }
        List<StockItemBean> items = stockItemDAO.queryEntityBeansByFK(item.getStockId());
        boolean all = true;

        for (StockItemBean stockItemBean : items)
        {
            if (stockItemBean.getFechProduct() == StockConstant.STOCK_ITEM_FECH_NO)
            {
                all = false;

                break;
            }
        }

        addLog(user, item.getStockId(), stock.getStatus(), stock, PublicConstant.OPRMODE_PASS,
            "询价人拿货");

        if (all)
        {
            // 修改成待结束采购
            updateStockStatus(user, item.getStockId(), StockConstant.STOCK_STATUS_END,
                PublicConstant.OPRMODE_PASS, "拿货结束");
        }

        return true;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.china.center.oa.stock.manager.StockManager#stockItemAskForNet(com.china.center.oa.stock.bean.StockItemBean,
     *      java.util.List)
     */
    @Transactional(rollbackFor = {MYException.class})
    public boolean stockItemAskForNet(StockItemBean oldItem, List<StockItemBean> newItemList)
        throws MYException
    {
        JudgeTools.judgeParameterIsNull(oldItem, newItemList);

        // 删除旧的采购申请
        stockItemDAO.deleteEntityBean(oldItem.getId());
        
        StockBean bean = stockDAO.find(oldItem.getStockId());
        
        if (bean.getMtype() == PublicConstant.MANAGER_TYPE_MANAGER)
        {
        	bean.setWillDate(30);
        	bean.setInvoice(StockConstant.INVOICE_YES);
        }

        double total = 0.0d;
        for (StockItemBean stockItemBean : newItemList)
        {
            stockItemBean.setStatus(StockConstant.STOCK_ITEM_STATUS_ASK);
            stockItemBean.setTotal(stockItemBean.getAmount() * stockItemBean.getPrice());
            
            if (bean.getMtype() == PublicConstant.MANAGER_TYPE_MANAGER)
            {
            	// 直接完成税务配置  -- 不用再单独录入，直接通过
            	stockItemBean.setDutyId(PublicConstant.MANAGER_DUTY_ID);
            	stockItemBean.setInvoiceType("");
            }

            total += stockItemBean.getTotal();
        }

        stockItemDAO.saveAllEntityBeans(newItemList);

        stockDAO.updateTotal(oldItem.getStockId(), total);
        
        return true;
    }

    @Transactional(rollbackFor = {MYException.class})
    public boolean updateStockNearlyPayDate(User user, String id, String nearlyPayDate)
        throws MYException
    {
        JudgeTools.judgeParameterIsNull(user, id);

        StockBean sb = stockDAO.find(id);

        if (sb == null)
        {
            throw new MYException("采购单不存在");
        }

        // TEMPIMPL 采购防止用户ID为空
        if (StringTools.isNullOrNone(sb.getStafferId()))
        {
            sb.setStafferId(user.getStafferId());
        }

        sb.setNearlyPayDate(nearlyPayDate);

        // 更新采购主单据
        stockDAO.updateEntityBean(sb);

        return true;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.china.center.oa.stock.manager.StockManager#updateStockBean(com.center.china.osgi.publics.User,
     *      com.china.center.oa.stock.bean.StockBean)
     */
    @Transactional(rollbackFor = {MYException.class})
    public boolean updateStockBean(User user, StockBean bean)
        throws MYException
    {
        JudgeTools.judgeParameterIsNull(user, bean);

        StockBean sb = stockDAO.find(bean.getId());

        if (sb == null)
        {
            throw new MYException("采购单不存在");
        }

        if (sb.getStatus() != StockConstant.STOCK_STATUS_REJECT
            && sb.getStatus() != StockConstant.STOCK_STATUS_INIT)
        {
            throw new MYException("采购单不存在驳回状态不能修改");
        }

        if (StringTools.isNullOrNone(bean.getStafferId()))
        {
            bean.setStafferId(user.getStafferId());
        }

        // 更新采购主单据
        stockDAO.updateEntityBean(bean);

        // 先删除
        stockItemDAO.deleteEntityBeansByFK(bean.getId());

        List<StockItemBean> items = bean.getItem();

        for (StockItemBean stockItemBean : items)
        {
            stockItemBean.setStockId(bean.getId());

            if (OATools.getManagerFlag())
            {
                ProductBean product = productDAO.find(stockItemBean.getProductId());

                // 判断是否同类
                if ( !String.valueOf(bean.getMtype()).equals(product.getReserve4()))
                {
                    throw new MYException("产品的管理类型和采购的管理类型不一致");
                }
            }

            stockItemBean.setMtype(bean.getMtype());

            stockItemDAO.saveEntityBean(stockItemBean);
        }

        return true;
    }

    @Transactional(rollbackFor = {MYException.class})
    public boolean updateStockDutyConfig(User user, StockBean bean)
        throws MYException
    {
        JudgeTools.judgeParameterIsNull(user, bean);

        StockBean old = stockDAO.find(bean.getId());

        if (old == null)
        {
            throw new MYException("采购单不存在");
        }

        if (old.getStatus() != StockConstant.STOCK_STATUS_PRICEPASS)
        {
            throw new MYException("采购单不存在带采购主管审批状态不能配置");
        }

        old.setWillDate(bean.getWillDate());

        old.setInvoice(bean.getInvoice());

        // 更新采购主单据
        stockDAO.updateEntityBean(old);

        List<StockItemBean> items = bean.getItem();

        List<StockItemBean> oldItems = stockItemDAO.queryEntityBeansByFK(bean.getId());

        // 仅仅更新showID
        for (StockItemBean newItem : items)
        {
            for (StockItemBean oldItem : oldItems)
            {
                if (oldItem.getId().equals(newItem.getId()))
                {
                    oldItem.setShowId(newItem.getShowId());
                    oldItem.setDutyId(newItem.getDutyId());
                    oldItem.setInvoiceType(newItem.getInvoiceType());
                }
            }
        }

        stockItemDAO.updateAllEntityBeans(oldItems);
        
        return true;
    }

    public boolean payStockItemWithoutTransaction(User user, String stockItemId)
        throws MYException
    {
        JudgeTools.judgeParameterIsNull(user, stockItemId);

        StockItemBean item = stockItemDAO.find(stockItemId);

        if (item == null)
        {
            throw new MYException("数据错误,请重新操作");
        }

        if (item.getPay() == StockConstant.STOCK_PAY_YES)
        {
            throw new MYException("已经付款,请重新操作");
        }

        stockItemDAO.updatePay(stockItemId, StockConstant.STOCK_PAY_YES);

        List<StockItemBean> itemList = stockItemDAO.queryEntityBeansByFK(item.getStockId());

        boolean allPay = true;

        for (StockItemBean stockItemBean : itemList)
        {
            if (stockItemBean.getPay() != StockConstant.STOCK_PAY_YES)
            {
                allPay = false;
                break;
            }
        }

        if (allPay)
        {
            // 修改采购单付款
            stockDAO.updatePayStatus(item.getStockId(), StockConstant.STOCK_PAY_YES);
        }

        return true;
    }

	@Transactional(rollbackFor = MYException.class)
	public boolean confirmProduct(User user, String itemId) throws MYException
	{
		JudgeTools.judgeParameterIsNull(user, itemId);
		
		StockItemBean item = stockItemDAO.find(itemId);
		
		if (null == item)
		{
			throw new MYException("数据错误");
		}
		
		stockItemDAO.updateExtraStatus(itemId, 1);
		
		List<StockItemBean> itemList = stockItemDAO.queryEntityBeansByFK(item.getStockId());
		
		int count = 0;
		for (StockItemBean each : itemList)
		{
			count += each.getExtraStatus();
		}
		
		if (count == itemList.size())
		{
			stockDAO.updateExtraStatus(item.getStockId(), 1);
		}
		
		return true;
	}
    
	@Transactional(rollbackFor = MYException.class)
	public boolean endProduct(User user, String id, String reason) throws MYException
	{
		JudgeTools.judgeParameterIsNull(user, id, reason);
		
		StockBean stock = stockDAO.find(id);
		
		if (null == stock)
		{
			throw new MYException("数据错误");
		}
		
		if (stock.getStatus() == StockConstant.STOCK_STATUS_LASTEND)
		{
			throw new MYException("已是采购结束态,不用异常结束");
		}
		
		List<StockItemBean> itemList = stockItemDAO.queryEntityBeansByFK(id);
		
		// checkAndUpdate status to end
        Collection<StockListener> listenerMapValues = this.listenerMapValues();
        
        for (StockListener stockListener : listenerMapValues)
        {
        	for (StockItemBean each : itemList)
        	{
        		stockListener.onCheckStockPay(user, id, each.getId(), reason);            		
        	}
        }
        
        updateStockStatus(user, id, StockConstant.STOCK_STATUS_LASTEND, PublicConstant.OPRMODE_PASS, "异常结束原因:" + reason);
		
		List<StockWorkBean> stockWorkList = stockWorkDAO.queryEntityBeansByFK(id);
		
		for (StockWorkBean each : stockWorkList)
		{
			if (each.getStatus() != StockConstant.STOCKWORK_STATUS_END)
			{
				each.setStatus(StockConstant.STOCKWORK_STATUS_END);
				each.setDescription(each.getDescription() + ";异常结束原因:" + reason);
				
				stockWorkDAO.updateEntityBean(each);
			}
		}
		
		_logger.info(user.getStafferName() + " 异常结束采购单:" + id);
		
		return true;
	}
	
    /**
     * sendSMSInner
     * 
     * @param user
     * @param sb
     * @param id
     * @param sbvo
     * @param message
     */
    private void sendSMSInner(User user, StafferBean sb, String id, StockVO sbvo, String message)
    {
        // send short message
        ShortMessageTaskBean sms = new ShortMessageTaskBean();

        sms.setId(commonDAO.getSquenceString20());

        sms.setFk(id);

        sms.setType(104);

        sms.setHandId(RandomTools.getRandomMumber(4));

        sms.setStatus(ShortMessageConstant.STATUS_INIT);

        sms.setMtype(ShortMessageConstant.MTYPE_ONLY_SEND);

        sms.setFktoken("0");

        sms.setMessage(sbvo.getUserName() + "发起的采购单" + message);

        sms.setReceiver(sb.getHandphone());

        sms.setStafferId(sb.getId());

        sms.setLogTime(TimeTools.now());

        // 24 hour
        sms.setEndTime(TimeTools.now(1));

        // internal
        sms.setSendTime(TimeTools.now());

        // add sms
        shortMessageTaskDAO.saveEntityBean(sms);
    }

    /**
     * @return the stockDAO
     */
    public StockDAO getStockDAO()
    {
        return stockDAO;
    }

    /**
     * @param stockDAO
     *            the stockDAO to set
     */
    public void setStockDAO(StockDAO stockDAO)
    {
        this.stockDAO = stockDAO;
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
     * @return the stockItemDAO
     */
    public StockItemDAO getStockItemDAO()
    {
        return stockItemDAO;
    }

    /**
     * @param stockItemDAO
     *            the stockItemDAO to set
     */
    public void setStockItemDAO(StockItemDAO stockItemDAO)
    {
        this.stockItemDAO = stockItemDAO;
    }

    /**
     * @return the locationDAO
     */
    public LocationDAO getLocationDAO()
    {
        return locationDAO;
    }

    /**
     * @param locationDAO
     *            the locationDAO to set
     */
    public void setLocationDAO(LocationDAO locationDAO)
    {
        this.locationDAO = locationDAO;
    }

    /**
     * @return the parameterDAO
     */
    public ParameterDAO getParameterDAO()
    {
        return parameterDAO;
    }

    /**
     * @param parameterDAO
     *            the parameterDAO to set
     */
    public void setParameterDAO(ParameterDAO parameterDAO)
    {
        this.parameterDAO = parameterDAO;
    }

    /**
     * @return the stafferDAO
     */
    public StafferDAO getStafferDAO()
    {
        return stafferDAO;
    }

    /**
     * @param stafferDAO
     *            the stafferDAO to set
     */
    public void setStafferDAO(StafferDAO stafferDAO)
    {
        this.stafferDAO = stafferDAO;
    }

    /**
     * @return the shortMessageTaskDAO
     */
    public ShortMessageTaskDAO getShortMessageTaskDAO()
    {
        return shortMessageTaskDAO;
    }

    /**
     * @param shortMessageTaskDAO
     *            the shortMessageTaskDAO to set
     */
    public void setShortMessageTaskDAO(ShortMessageTaskDAO shortMessageTaskDAO)
    {
        this.shortMessageTaskDAO = shortMessageTaskDAO;
    }

    /**
     * @return the stockLocation
     */
    public String getStockLocation()
    {
        return stockLocation;
    }

    /**
     * @param stockLocation
     *            the stockLocation to set
     */
    public void setStockLocation(String stockLocation)
    {
        this.stockLocation = stockLocation;
    }

    /**
     * @return the priceAskProviderDAO
     */
    public PriceAskProviderDAO getPriceAskProviderDAO()
    {
        return priceAskProviderDAO;
    }

    /**
     * @param priceAskProviderDAO
     *            the priceAskProviderDAO to set
     */
    public void setPriceAskProviderDAO(PriceAskProviderDAO priceAskProviderDAO)
    {
        this.priceAskProviderDAO = priceAskProviderDAO;
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
     * @return the priceAskManager
     */
    public PriceAskManager getPriceAskManager()
    {
        return priceAskManager;
    }

    /**
     * @param priceAskManager
     *            the priceAskManager to set
     */
    public void setPriceAskManager(PriceAskManager priceAskManager)
    {
        this.priceAskManager = priceAskManager;
    }

    /**
     * @return the priceAskDAO
     */
    public PriceAskDAO getPriceAskDAO()
    {
        return priceAskDAO;
    }

    /**
     * @param priceAskDAO
     *            the priceAskDAO to set
     */
    public void setPriceAskDAO(PriceAskDAO priceAskDAO)
    {
        this.priceAskDAO = priceAskDAO;
    }

	/**
	 * @return the stockWorkDAO
	 */
	public StockWorkDAO getStockWorkDAO()
	{
		return stockWorkDAO;
	}

	/**
	 * @param stockWorkDAO the stockWorkDAO to set
	 */
	public void setStockWorkDAO(StockWorkDAO stockWorkDAO)
	{
		this.stockWorkDAO = stockWorkDAO;
	}
}
