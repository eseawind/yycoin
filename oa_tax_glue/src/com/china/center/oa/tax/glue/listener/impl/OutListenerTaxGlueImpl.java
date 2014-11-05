/**
 * File Name: OutListenerTaxGlueImpl.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2011-6-8<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.tax.glue.listener.impl;


import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.center.china.osgi.publics.User;
import com.china.center.common.MYException;
import com.china.center.jdbc.util.ConditionParse;
import com.china.center.oa.client.dao.StafferVSCustomerDAO;
import com.china.center.oa.client.vs.StafferVSCustomerBean;
import com.china.center.oa.finance.bean.InBillBean;
import com.china.center.oa.finance.constant.FinanceConstant;
import com.china.center.oa.finance.dao.InBillDAO;
import com.china.center.oa.finance.manager.BillManager;
import com.china.center.oa.product.dao.ProviderDAO;
import com.china.center.oa.publics.bean.StafferBean;
import com.china.center.oa.publics.constant.IDPrefixConstant;
import com.china.center.oa.publics.constant.PublicConstant;
import com.china.center.oa.publics.dao.CommonDAO;
import com.china.center.oa.publics.dao.DepartmentDAO;
import com.china.center.oa.publics.dao.DutyDAO;
import com.china.center.oa.publics.dao.StafferDAO;
import com.china.center.oa.publics.wrap.ResultBean;
import com.china.center.oa.sail.bean.BaseBalanceBean;
import com.china.center.oa.sail.bean.BaseBean;
import com.china.center.oa.sail.bean.OutBalanceBean;
import com.china.center.oa.sail.bean.OutBean;
import com.china.center.oa.sail.constanst.OutConstant;
import com.china.center.oa.sail.dao.BaseBalanceDAO;
import com.china.center.oa.sail.dao.BaseDAO;
import com.china.center.oa.sail.dao.OutDAO;
import com.china.center.oa.sail.helper.OutHelper;
import com.china.center.oa.sail.listener.OutListener;
import com.china.center.oa.tax.bean.FinanceBean;
import com.china.center.oa.tax.bean.FinanceItemBean;
import com.china.center.oa.tax.bean.FinanceTagBean;
import com.china.center.oa.tax.bean.TaxBean;
import com.china.center.oa.tax.constanst.TaxConstanst;
import com.china.center.oa.tax.constanst.TaxItemConstanst;
import com.china.center.oa.tax.dao.FinanceDAO;
import com.china.center.oa.tax.dao.FinanceItemDAO;
import com.china.center.oa.tax.dao.TaxDAO;
import com.china.center.oa.tax.helper.FinanceHelper;
import com.china.center.oa.tax.manager.FinanceManager;
import com.china.center.oa.tax.manager.FinanceTagManager;
import com.china.center.tools.BeanUtil;
import com.china.center.tools.ListTools;
import com.china.center.tools.StringTools;
import com.china.center.tools.TimeTools;


/**
 * TODO_OSGI 入库单/销售单/结算单/坏账
 * 
 * @author ZHUZHU
 * @version 2011-6-8
 * @see OutListenerTaxGlueImpl
 * @since 3.0
 */
public class OutListenerTaxGlueImpl implements OutListener
{
    private final Log _logger = LogFactory.getLog("bad");

    private DutyDAO dutyDAO = null;

    private DepartmentDAO departmentDAO = null;

    private TaxDAO taxDAO = null;

    private CommonDAO commonDAO = null;

    private ProviderDAO providerDAO = null;

    private FinanceManager financeManager = null;

    private OutDAO outDAO = null;

    private BaseDAO baseDAO = null;

    private FinanceDAO financeDAO = null;

    private FinanceItemDAO financeItemDAO = null;

    private StafferDAO stafferDAO = null;

    private BaseBalanceDAO baseBalanceDAO = null;
    
    private InBillDAO inBillDAO = null;

    private BillManager billManager = null;
    
    private StafferVSCustomerDAO stafferVSCustomerDAO = null;
    
    private FinanceTagManager financeTagManager = null;
    
    /**
     * default constructor
     */
    public OutListenerTaxGlueImpl()
    {
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.china.center.oa.sail.listener.OutListener#onCancleBadDebts(com.center.china.osgi.publics.User,
     *      com.china.center.oa.sail.bean.OutBean)
     */
    public void onCancleBadDebts(User user, OutBean outBean)
        throws MYException
    {
        // 先找到此销售单所有的坏账凭证,然后全部回滚,在生成坏账
        ConditionParse condition = new ConditionParse();

        condition.addWhereStr();

        condition.addIntCondition("createType", "=", TaxConstanst.FINANCE_CREATETYPE_SAIL_BADMONEY);

        condition.addCondition("refId", "=", outBean.getFullId());

        List<FinanceBean> oldFinanceList = financeDAO.queryEntityBeansByCondition(condition);

        // 当前已经存在的坏账
        long bad = 0L;

        for (FinanceBean each : oldFinanceList)
        {
            bad += each.getInmoney();
        }

        if (bad == 0)
        {
            return;
        }

        FinanceBean financeBean = new FinanceBean();

        String name = user.getStafferName() + "往来核对或驳回中取消坏账:" + outBean.getFullId() + '.';

        financeBean.setName(name);

        financeBean.setType(TaxConstanst.FINANCE_TYPE_MANAGER);

        financeBean.setCreateType(TaxConstanst.FINANCE_CREATETYPE_SAIL_BADMONEY);

        financeBean.setRefId(outBean.getFullId());

        financeBean.setRefOut(outBean.getFullId());

        financeBean.setDutyId(outBean.getDutyId());

        financeBean.setCreaterId(user.getStafferId());

        financeBean.setDescription(financeBean.getName());

        financeBean.setFinanceDate(TimeTools.now_short());

        financeBean.setLogTime(TimeTools.now());

        List<FinanceItemBean> itemList = new ArrayList<FinanceItemBean>();

        doCancleBad(user, outBean, financeBean, itemList);

        financeBean.setItemList(itemList);

        financeManager.addFinanceBeanWithoutTransactional(user, financeBean);
    }

    /**
     * 其它应收-坏账（负数）/应收账款（负数）
     * 
     * @param user
     * @param outBean
     * @param financeBean
     * @param itemList
     * @throws MYException
     */
    private void doCancleBad(User user, OutBean outBean, FinanceBean financeBean,
                             List<FinanceItemBean> itemList)
        throws MYException
    {
        // 先找到此销售单所有的坏账凭证,然后全部回滚,在生成坏账
        ConditionParse condition = new ConditionParse();

        condition.addWhereStr();

        condition.addIntCondition("createType", "=", TaxConstanst.FINANCE_CREATETYPE_SAIL_BADMONEY);

        condition.addCondition("refId", "=", outBean.getFullId());

        List<FinanceBean> oldFinanceList = financeDAO.queryEntityBeansByCondition(condition);

        // 当前已经存在的坏账
        long bad = 0L;

        for (FinanceBean each : oldFinanceList)
        {
            bad += each.getInmoney();
        }

        if (bad != 0)
        {
            // 其它应收-坏账（负数）/应收账款（负数）
            createAddItem6(user, outBean, financeBean, itemList, bad);
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.china.center.oa.sail.listener.OutListener#onCheck(com.center.china.osgi.publics.User,
     *      com.china.center.oa.sail.bean.OutBean)
     */
    public void onCheck(User user, OutBean bean)
        throws MYException
    {
        // 
    }

    /**
     * CORE 销售/入库
     */
    public void onConfirmOutOrBuy(User user, OutBean outBean)
        throws MYException
    {
        // 销售处理
        if (outBean.getType() == OutConstant.OUT_TYPE_OUTBILL)
        {
            // 销售-销售出库
            if (outBean.getOutType() == OutConstant.OUTTYPE_OUT_COMMON
                && !isSwatchToSail(outBean.getFullId()))
            {
                processOutCommon(user, outBean);

                return;
            }

            // 销售-个人领转销售
            if (outBean.getOutType() == OutConstant.OUTTYPE_OUT_COMMON
                && isSwatchToSail(outBean.getFullId()))
            {
                processOutCommon2(user, outBean);

                return;
            }

            // 个人领样
            if (outBean.getType() == OutConstant.OUT_TYPE_OUTBILL
                && (outBean.getOutType() == OutConstant.OUTTYPE_OUT_SWATCH
                		|| outBean.getOutType() == OutConstant.OUTTYPE_OUT_SHOW
                		|| outBean.getOutType() == OutConstant.OUTTYPE_OUT_SHOWSWATCH))
            {
                processOutSwatch(user, outBean);

                processOutSwatchTag(user, outBean);
                
                return;
            }

            // 零售
            if (outBean.getType() == OutConstant.OUT_TYPE_OUTBILL
                && outBean.getOutType() == OutConstant.OUTTYPE_OUT_RETAIL)
            {
                processOutRetail(user, outBean);

                return;
            }

            // 销售-委托代销
            if (outBean.getType() == OutConstant.OUT_TYPE_OUTBILL
                && outBean.getOutType() == OutConstant.OUTTYPE_OUT_CONSIGN)
            {
                processOutConsign(user, outBean);
                
                processOutConsignTag(user, outBean);

                return;
            }

            // 销售-赠送
            if (outBean.getType() == OutConstant.OUT_TYPE_OUTBILL
                && outBean.getOutType() == OutConstant.OUTTYPE_OUT_PRESENT)
            {
                processOutPresent(user, outBean, 1);
                
                processOutPresentTag(user, outBean);

                return;
            }

            return;
        }

        // 入库处理
        if (outBean.getType() == OutConstant.OUT_TYPE_INBILL)
        {
            // 入库单-调拨（调入接受时）
            if (OutHelper.isMoveIn(outBean))
            {
                processMoveIn(user, outBean);

                return;
            }

            // 入库单-报废 (单独出来)&& 入库单-系统纠正
            if (outBean.getType() == OutConstant.OUT_TYPE_INBILL
                && outBean.getOutType() == OutConstant.OUTTYPE_IN_ERRORP )
            {
                // 营业外支出/库存商品
                processBuyDrop(user, outBean);

                return;
            }
            
            // 报废 (暂不开放）
            if (outBean.getType() == OutConstant.OUT_TYPE_INBILL
                    && outBean.getOutType() == OutConstant.OUTTYPE_IN_DROP)
            {
                // 兼容未审批结束的单子
                if (outBean.getForceBuyType() == -1)
                {
                    processBuyDrop(user, outBean);

                    return;
                }
                
                processBuyDrop1(user, outBean);
                
                return ;

            }

            // 采购退货
            if (outBean.getType() == OutConstant.OUT_TYPE_INBILL
                && outBean.getOutType() == OutConstant.OUTTYPE_IN_STOCK)
            {
                // 库存商品/应付账款-供应商（负数）
                processBuyStockBack(user, outBean);

                return;
            }

            if (outBean.getOutType() == OutConstant.OUTTYPE_IN_OTHER)
            {
                // 入库单-库存中转(其他)
                if (outBean.getType() == OutConstant.OUT_TYPE_INBILL
                    && outBean.getOutType() == OutConstant.OUTTYPE_IN_OTHER
                    && !"1".equals(outBean.getReserve8()))
                {
                    // 考虑兼容 上线时未审批结束的其它入库单
                    if (outBean.getForceBuyType() == OutConstant.FORCEBUYTYPE_MODIFYPRODUCTNAME 
                            || outBean.getForceBuyType() == OutConstant.FORCEBUYTYPE_SPITBADPRODUCT
                            || outBean.getForceBuyType() == OutConstant.FORCEBUYTYPE_PRITOPUB
                            || outBean.getForceBuyType() == OutConstant.FORCEBUYTYPE_MODIFYSTOREAGEATTR
                            || outBean.getForceBuyType() == -1)
                    {
                        // 库存商品/库存商品-中转
                        processBuyOther(user, outBean);
                        
                    }else if (outBean.getForceBuyType() == OutConstant.FORCEBUYTYPE_NOCOST 
                            || outBean.getForceBuyType() == OutConstant.FORCEBUYTYPE_DUTYERROR
                            || outBean.getForceBuyType() == OutConstant.FORCEBUYTYPE_ORDERNOTFOUND
                            || outBean.getForceBuyType() == OutConstant.FORCEBUYTYPE_TRANSCUSTOMERORSTAFFER)
                    {
                        
                        //11.4.1前销售单退货 、原单选错纳税实体 、找不到原单、 转业务员，转客户 产生新的凭证 
                        processBuyOther1(user, outBean);
                    }
                        
                    return;
                }

                // 入库单-对冲单据
                if (outBean.getType() == OutConstant.OUT_TYPE_INBILL
                    && outBean.getOutType() == OutConstant.OUTTYPE_IN_OTHER
                    && "1".equals(outBean.getReserve8()))
                {
                    // 和领样单相反
                    processDuiChong(user, outBean);
                    
                    processDuiChongTag(user, outBean);

                    return;
                }
            }

            // 生成B单入库的对冲单据(废弃)
            if (outBean.getOutType() == OutConstant.OUTTYPE_IN_OTHER && false)
            {
                // 贷库存商品 贷主营业务成本
                processDuiChong2(user, outBean);

                return;
            }

            // 入库单-领样退货
            if (outBean.getType() == OutConstant.OUT_TYPE_INBILL
                && outBean.getOutType() == OutConstant.OUTTYPE_IN_SWATCH)
            {
                processBuySwatch(user, outBean);
                
                processBuySwatchTag(user, outBean);

                return;
            }

            // 入库单-销售退库
            if (outBean.getType() == OutConstant.OUT_TYPE_INBILL
                && outBean.getOutType() == OutConstant.OUTTYPE_IN_OUTBACK)
            {
                processBuyCommonBack(user, outBean);
                
                if (!StringTools.isNullOrNone(FinanceHelper.getExecPromOutId(outBean.getRefBindOutId())))
                {
             
                    if ((outBean.getPromStatus() == OutConstant.OUT_PROMSTATUS_BACKBIND
                            || outBean.getPromStatus() == OutConstant.OUT_PROMSTATUS_PARTBACKBIND)
                            && !FinanceHelper.isPromBind(outBean.getRefOutFullId(), outBean.getRefBindOutId()))
                    {            
                        OutBean srcOutBean = outDAO.find(FinanceHelper.getExecPromOutId(outBean.getRefBindOutId()));
                        
                        processBuyCommonExecProm(user, outBean, srcOutBean);
                        
                    }
                }
                    
                return;
            }

            // 销售-赠送
            if (outBean.getOutType() == OutConstant.OUTTYPE_IN_PRESENT)
            {
                processOutPresent(user, outBean, -1);
                
//                processOutPresentTag(user, outBean);

                return;
            }
            
            return;
        }
    }

    /**
     * 应收抵消/主营业务收入抵消/主营业务成本抵消
     */
    public void onTranOutList(User user, List<OutBean> outList, StafferBean targerStaffer)
        throws MYException
    {
        for (OutBean outBean : outList)
        {
        	if (outBean.getStatus() == OutConstant.STATUS_PASS 
        			|| outBean.getStatus() == OutConstant.STATUS_SEC_PASS) {
        		processTranOutCommon(user, outBean, targerStaffer);
        	}
        }
    }

    /**
     * 是否领样转销售
     * 
     * @param fullId
     * @return
     */
    private boolean isSwatchToSail(String fullId)
    {
        OutBean out = outDAO.find(fullId);

        if (out == null)
        {
            return false;
        }

        if (StringTools.isNullOrNone(out.getRefOutFullId()))
        {
            return false;
        }

        OutBean src = outDAO.find(out.getRefOutFullId());

        if (src == null)
        {
            return false;
        }

        if (src.getType() == OutConstant.OUT_TYPE_OUTBILL
            && (src.getOutType() == OutConstant.OUTTYPE_OUT_SWATCH
            		|| src.getOutType() == OutConstant.OUTTYPE_OUT_SHOW
            		|| src.getOutType() == OutConstant.OUTTYPE_OUT_SHOWSWATCH))
        {
            return true;
        }

        return false;
    }

    private void processMoveIn(User user, OutBean outBean)
        throws MYException
    {
        // 库存商品
        // 库存商品
        FinanceBean financeBean = new FinanceBean();

        String name = "入库单-调拨（调入接受）:" + outBean.getFullId() + '.';

        financeBean.setName(name);

        financeBean.setType(TaxConstanst.FINANCE_TYPE_MANAGER);

        financeBean.setCreateType(TaxConstanst.FINANCE_CREATETYPE_BUY_OUT);

        financeBean.setRefId(outBean.getFullId());

        financeBean.setRefOut(outBean.getFullId());

        financeBean.setDutyId(outBean.getDutyId());

        financeBean.setDescription(financeBean.getName());

        financeBean.setFinanceDate(TimeTools.now_short());

        financeBean.setLogTime(TimeTools.now());

        List<FinanceItemBean> itemList = new ArrayList<FinanceItemBean>();

        // 库存商品/库存商品
        createOutCommonItemMoveIn(user, outBean, financeBean, itemList);

        financeBean.setItemList(itemList);

        financeManager.addFinanceBeanWithoutTransactional(user, financeBean);
    }

    /**
     * 入库单-报废/入库单-系统纠正( 营业外支出/库存商品)
     * 
     * @param user
     * @param outBean
     * @throws MYException
     */
    private void processBuyDrop(User user, OutBean outBean)
        throws MYException
    {
        // 营业外支出/库存商品
        FinanceBean financeBean = new FinanceBean();

        String name = "入库单-报废/系统纠正:" + outBean.getFullId() + '.';

        financeBean.setName(name);

        financeBean.setType(TaxConstanst.FINANCE_TYPE_MANAGER);

        if (outBean.getOutType() == OutConstant.OUTTYPE_IN_DROP)
        {
            financeBean.setCreateType(TaxConstanst.FINANCE_CREATETYPE_BUY_DROP);
        }
        else
        {
            financeBean.setCreateType(TaxConstanst.FINANCE_CREATETYPE_BUY_ERRORP);
        }

        financeBean.setRefId(outBean.getFullId());

        financeBean.setRefOut(outBean.getFullId());

        financeBean.setDutyId(outBean.getDutyId());

        financeBean.setDescription(financeBean.getName());

        financeBean.setFinanceDate(TimeTools.now_short());

        financeBean.setLogTime(TimeTools.now());

        List<FinanceItemBean> itemList = new ArrayList<FinanceItemBean>();

        // 营业外支出/库存商品
        createBuyDrop(user, outBean, financeBean, itemList);

        financeBean.setItemList(itemList);

        financeManager.addFinanceBeanWithoutTransactional(user, financeBean);
    }

    /**
     * 报废 - 借:产品报废损失/贷：库存商品
     * @param user
     * @param outBean
     * @throws MYException
     */
    private void processBuyDrop1(User user, OutBean outBean)
    throws MYException
    {
        // 借:产品报废损失/贷：库存商品
        FinanceBean financeBean = new FinanceBean();
    
        String name = "入库单-报废:" + outBean.getFullId() + '.';
    
        financeBean.setName(name);
    
        financeBean.setType(TaxConstanst.FINANCE_TYPE_MANAGER);
    
        financeBean.setCreateType(TaxConstanst.FINANCE_CREATETYPE_BUY_DROP);

        financeBean.setRefId(outBean.getFullId());
    
        financeBean.setRefOut(outBean.getFullId());
    
        financeBean.setDutyId(outBean.getDutyId());
    
        financeBean.setDescription(financeBean.getName());
    
        financeBean.setFinanceDate(TimeTools.now_short());
    
        financeBean.setLogTime(TimeTools.now());
    
        List<FinanceItemBean> itemList = new ArrayList<FinanceItemBean>();
    
        // 借:产品报废损失/贷：库存商品
        createBuyDrop1(user, outBean, financeBean, itemList);
    
        financeBean.setItemList(itemList);
    
        financeManager.addFinanceBeanWithoutTransactional(user, financeBean);
    }    
    
    /**
     * 库存商品/应付账款-供应商（负数）
     * 
     * @param user
     * @param outBean
     * @throws MYException
     */
    private void processBuyStockBack(User user, OutBean outBean)
        throws MYException
    {
        // 库存商品/应付账款-供应商（负数）
        FinanceBean financeBean = new FinanceBean();

        String name = "入库单-采购退货:" + outBean.getFullId() + '.';

        financeBean.setName(name);

        financeBean.setType(TaxConstanst.FINANCE_TYPE_MANAGER);

        financeBean.setCreateType(TaxConstanst.FINANCE_CREATETYPE_BUY_STOCKBACK);

        financeBean.setRefId(outBean.getFullId());

        financeBean.setRefOut(outBean.getFullId());

        financeBean.setDutyId(outBean.getDutyId());

        financeBean.setDescription(financeBean.getName());

        financeBean.setFinanceDate(TimeTools.now_short());

        financeBean.setLogTime(TimeTools.now());

        List<FinanceItemBean> itemList = new ArrayList<FinanceItemBean>();

        // 库存商品/应付账款-供应商（负数）
        createBuyStockBack(user, outBean, financeBean, itemList);

        financeBean.setItemList(itemList);

        financeManager.addFinanceBeanWithoutTransactional(user, financeBean);
    }

    /**
     * 入库单-库存中转
     * 
     * @param user
     * @param outBean
     * @throws MYException
     */
    private void processBuyOther(User user, OutBean outBean)
        throws MYException
    {
        // 库存商品/库存商品-中转
        FinanceBean financeBean = new FinanceBean();

        String name = "入库单-库存中转:" + outBean.getFullId() + '.';

        financeBean.setName(name);

        financeBean.setType(TaxConstanst.FINANCE_TYPE_MANAGER);

        financeBean.setCreateType(TaxConstanst.FINANCE_CREATETYPE_BUY_OTHER);

        financeBean.setRefId(outBean.getFullId());

        financeBean.setRefOut(outBean.getFullId());

        financeBean.setDutyId(PublicConstant.DEFAULR_DUTY_ID);

        financeBean.setDescription(financeBean.getName());

        financeBean.setFinanceDate(TimeTools.now_short());

        financeBean.setLogTime(TimeTools.now());

        List<FinanceItemBean> itemList = new ArrayList<FinanceItemBean>();

        // 库存商品/营业外收入
        createBuyOther(user, outBean, financeBean, itemList);

        financeBean.setItemList(itemList);

        financeManager.addFinanceBeanWithoutTransactional(user, financeBean);
    }

    /**
     * 
     * @param user
     * @param outBean
     * @throws MYException
     */
    private void processBuyOther1(User user, OutBean outBean)
    throws MYException
    {
        // 应收账款（销售金额，含税价）(1132) 主营业务收入：含税价
        // 主营业务成本(5401) 库存商品（成本价*数量）
        FinanceBean financeBean = new FinanceBean();

        String name = "其它出库:" + outBean.getFullId() + '.';

        financeBean.setName(name);

        financeBean.setType(TaxConstanst.FINANCE_TYPE_MANAGER);

        financeBean.setCreateType(TaxConstanst.FINANCE_CREATETYPE_BUY_OTHER);

        financeBean.setRefId(outBean.getFullId());

        financeBean.setRefOut(outBean.getFullId());

        financeBean.setDutyId(outBean.getDutyId());

        financeBean.setDescription(financeBean.getName());

        financeBean.setFinanceDate(TimeTools.now_short());

        financeBean.setLogTime(TimeTools.now());

        List<FinanceItemBean> itemList = new ArrayList<FinanceItemBean>();

        // 应收账款（销售金额，含税价）(1132)/主营业务收入：含税价
        createOutCommonItem14(user, outBean, financeBean, itemList);

        // 主营业务成本(5401)/库存商品（成本价*数量）
        createOutCommonItem24(user, outBean, financeBean, itemList);

        financeBean.setItemList(itemList);

        financeManager.addFinanceBeanWithoutTransactional(user, financeBean);
    }    
    
    /**
     * 销售-销售出库
     * 
     * @param user
     * @param outBean
     * @throws MYException
     */
    private void processOutCommon(User user, OutBean outBean)
        throws MYException
    {
        // 应收账款（销售金额，含税价）(1132) 主营业务收入：含税价
        // 主营业务成本(5401) 库存商品（成本价*数量）
        FinanceBean financeBean = new FinanceBean();

        String name = "销售出库:" + outBean.getFullId() + '.';

        financeBean.setName(name);

        financeBean.setType(TaxConstanst.FINANCE_TYPE_MANAGER);

        financeBean.setCreateType(TaxConstanst.FINANCE_CREATETYPE_SAIL_COMMON);

        financeBean.setRefId(outBean.getFullId());

        financeBean.setRefOut(outBean.getFullId());

        financeBean.setDutyId(outBean.getDutyId());

        financeBean.setDescription(financeBean.getName());

        financeBean.setFinanceDate(TimeTools.now_short());

        financeBean.setLogTime(TimeTools.now());

        List<FinanceItemBean> itemList = new ArrayList<FinanceItemBean>();

        // 应收账款（销售金额，含税价）(1132)/主营业务收入：含税价
        createOutCommonItem1(user, outBean, financeBean, itemList);

        // 主营业务成本(5401)/库存商品（成本价*数量）
        createOutCommonItem2(user, outBean, financeBean, itemList);

        financeBean.setItemList(itemList);

        financeManager.addFinanceBeanWithoutTransactional(user, financeBean);
    }

    /**
     * 销售-销售出库移交(应收抵消/主营业务收入抵消/主营业务成本抵消)
     * 
     * @param user
     * @param outBean
     * @throws MYException
     */
    private void processTranOutCommon(User user, OutBean outBean, StafferBean targerStaffer)
        throws MYException
    {
        // 应收抵消/主营业务收入抵消/主营业务成本抵消
        FinanceBean financeBean = new FinanceBean();

        String name = "销售移交:" + outBean.getFullId() + ".从:" + outBean.getStafferName() + "到:"
                      + targerStaffer.getName();

        financeBean.setName(name);

        financeBean.setCreateType(TaxConstanst.FINANCE_CREATETYPE_SAIL_TRAN);

        financeBean.setRefId(outBean.getFullId());

        financeBean.setRefOut(outBean.getFullId());

        financeBean.setDutyId(outBean.getDutyId());

        financeBean.setDescription(financeBean.getName());

        financeBean.setFinanceDate(TimeTools.now_short());

        financeBean.setLogTime(TimeTools.now());

        List<FinanceItemBean> itemList = new ArrayList<FinanceItemBean>();

        // 应收抵消
        createOutCommonItem11(user, outBean, financeBean, itemList, targerStaffer);

        // 主营业务收入抵消
        createOutCommonItem12(user, outBean, financeBean, itemList, targerStaffer);

        // 主营业务成本抵消
        createOutCommonItem13(user, outBean, financeBean, itemList, targerStaffer);

        financeBean.setItemList(itemList);

        financeManager.addFinanceBeanWithoutTransactional(user, financeBean);
    }
    
    /**
     * 应收账款（销售金额，含税价）/其他应收款
     * 
     * @param user
     * @param outBean
     * @throws MYException
     */
    private void processOutCommon2(User user, OutBean outBean)
        throws MYException
    {
        // // 应收账款（销售金额，含税价）/其他应收款
        // FinanceBean financeBean = new FinanceBean();
        //
        // String name = "销售-个人领转销售:" + outBean.getFullId() + '.';
        //
        // financeBean.setName(name);
        //
        // financeBean.setType(TaxConstanst.FINANCE_TYPE_MANAGER);
        //
        // financeBean.setCreateType(TaxConstanst.FINANCE_CREATETYPE_SAIL_SWATCHSAIL);
        //
        // financeBean.setRefId(outBean.getFullId());
        //
        // financeBean.setRefOut(outBean.getFullId());
        //
        // financeBean.setDutyId(outBean.getDutyId());
        //
        // financeBean.setDescription(financeBean.getName());
        //
        // financeBean.setFinanceDate(TimeTools.now_short());
        //
        // financeBean.setLogTime(TimeTools.now());
        //
        // List<FinanceItemBean> itemList = new ArrayList<FinanceItemBean>();
        //
        // // 应收账款（销售金额，含税价）/其他应收款
        // createOutCommonItem3(user, outBean, financeBean, itemList);
        //
        // financeBean.setItemList(itemList);
        //
        // financeManager.addFinanceBeanWithoutTransactional(user, financeBean);

        // 应收账款（销售金额，含税价）(1132) 主营业务收入：含税价
        // 主营业务成本(5401) 库存商品（成本价*数量）
        FinanceBean financeBean = new FinanceBean();

        String name = "销售-个人领转销售:" + outBean.getFullId() + '.';

        financeBean.setName(name);

        financeBean.setType(TaxConstanst.FINANCE_TYPE_MANAGER);

        financeBean.setCreateType(TaxConstanst.FINANCE_CREATETYPE_SAIL_SWATCHSAIL);

        financeBean.setRefId(outBean.getFullId());

        financeBean.setRefOut(outBean.getFullId());

        financeBean.setDutyId(outBean.getDutyId());

        financeBean.setDescription(financeBean.getName());

        financeBean.setFinanceDate(TimeTools.now_short());

        financeBean.setLogTime(TimeTools.now());

        List<FinanceItemBean> itemList = new ArrayList<FinanceItemBean>();

        // 应收账款（销售金额，含税价）(1132)/主营业务收入：含税价
        createOutCommonItem1(user, outBean, financeBean, itemList);

        // 主营业务成本(5401)/库存商品（成本价*数量）
        createOutCommonItem2(user, outBean, financeBean, itemList);

        financeBean.setItemList(itemList);

        financeManager.addFinanceBeanWithoutTransactional(user, financeBean);

    }

    /**
     * 个人领样
     * 
     * @param user
     * @param outBean
     * @throws MYException
     */
    private void processOutSwatch(User user, OutBean outBean)
        throws MYException
    {
        // 其他应收款（销售金额，含税价）/主营业务收入
        // 主营业务成本(5401) /库存商品（成本价*数量）
        FinanceBean financeBean = new FinanceBean();

        String name = "销售出库:" + outBean.getFullId() + '.';

        financeBean.setName(name);

        financeBean.setType(TaxConstanst.FINANCE_TYPE_MANAGER);

        financeBean.setCreateType(TaxConstanst.FINANCE_CREATETYPE_SAIL_SWATCH);

        financeBean.setRefId(outBean.getFullId());

        financeBean.setRefOut(outBean.getFullId());

        financeBean.setDutyId(outBean.getDutyId());

        financeBean.setDescription(financeBean.getName());

        financeBean.setFinanceDate(TimeTools.now_short());

        financeBean.setLogTime(TimeTools.now());

        List<FinanceItemBean> itemList = new ArrayList<FinanceItemBean>();

        // 其他应收款（销售金额，含税价）1133-20/主营业务收入：含税价
        createOutSwatchItem1(user, outBean, financeBean, itemList);

        // 主营业务成本(5401)/库存商品（成本价*数量）
        //createOutSwatchItem2(user, outBean, financeBean, itemList);

        financeBean.setItemList(itemList);

        financeManager.addFinanceBeanWithoutTransactional(user, financeBean);
    }

    /**
     * processDuiChong
     * 
     * @param user
     * @param outBean
     * @throws MYException
     */
    private void processDuiChong(User user, OutBean outBean)
        throws MYException
    {
        // 其他应收款（销售金额，含税价）/主营业务收入(负数)
        // 主营业务成本(5401) /库存商品（成本价*数量）(负数)
        FinanceBean financeBean = new FinanceBean();

        String name = "领样转销售对冲单据:" + outBean.getFullId() + '.';

        financeBean.setName(name);

        financeBean.setType(TaxConstanst.FINANCE_TYPE_MANAGER);

        financeBean.setCreateType(TaxConstanst.FINANCE_CREATETYPE_OUT_DUICHONG);

        financeBean.setRefId(outBean.getFullId());

        financeBean.setRefOut(outBean.getFullId());

        financeBean.setDutyId(outBean.getDutyId());

        financeBean.setDescription(financeBean.getName());

        financeBean.setFinanceDate(TimeTools.now_short());

        financeBean.setLogTime(TimeTools.now());

        List<FinanceItemBean> itemList = new ArrayList<FinanceItemBean>();

        OutBean out = outDAO.find(outBean.getRefOutFullId());
   	 
	 	// 之前的退货 按老的产生 
	 	if (TimeTools.cdate(out.getOutTime(), "2014-04-14") < 0)
	 	{
	 		// 其他应收款（销售金额，含税价）1133-20/主营业务收入：含税价
	        createDuiChongItem1(user, outBean, financeBean, itemList);

	        // 主营业务成本(5401)/库存商品（成本价*数量）
	        createDuiChongItem2(user, outBean, financeBean, itemList);
	 	}else{
	 		createDuiChongItem11(user, outBean, financeBean, itemList);
	 	}
        
        financeBean.setItemList(itemList);

        financeManager.addFinanceBeanWithoutTransactional(user, financeBean);
    }
    
    private void createDuiChongItem11(User user, OutBean outBean, FinanceBean financeBean,
            List<FinanceItemBean> itemList)
	throws MYException
	{
        String name = "领样转销售对冲单据:" + outBean.getFullId() + '.';

        // 其他应收款（销售金额，含税价）1133-20/主营业务收入
        String pare1 = commonDAO.getSquenceString();

        List<BaseBean> baseList = outBean.getBaseList();

        // 其他应收款-样品（成本价*数量）
        for (BaseBean baseBean : baseList)
        {
            FinanceItemBean itemInEach = new FinanceItemBean();

            itemInEach.setPareId(pare1);

            itemInEach.setName("其他应收款-样品(" + baseBean.getProductName() + "):" + name);

            itemInEach.setForward(TaxConstanst.TAX_FORWARD_IN);

            FinanceHelper.copyFinanceItem(financeBean, itemInEach);

            // 其他应收款-样品
            String itemTaxIdIn = TaxItemConstanst.OTHER_REVEIVE_PRODUCT;

            TaxBean outTax = taxDAO.findByUnique(itemTaxIdIn);

            if (outTax == null)
            {
                throw new MYException("数据错误,请确认操作");
            }

            // 科目拷贝
            FinanceHelper.copyTax(outTax, itemInEach);

            // 含税价
            double outMoney = -baseBean.getAmount() * baseBean.getPrice();

            itemInEach.setInmoney(FinanceHelper.doubleToLong(outMoney));

            itemInEach.setOutmoney(0);

            itemInEach.setDescription(itemInEach.getName());

            // 辅助核算 部门/职员/产品/仓库
            copyDepartment(outBean, itemInEach);
            itemInEach.setStafferId(outBean.getStafferId());
            itemInEach.setProductId(baseBean.getProductId());
            itemInEach.setProductAmountIn( -baseBean.getAmount());
            itemInEach.setDepotId(outBean.getLocation());

            itemList.add(itemInEach);
        }

        // 库存商品（成本价*数量）
        for (BaseBean baseBean : baseList)
        {
            FinanceItemBean itemOut1 = new FinanceItemBean();

            itemOut1.setPareId(pare1);

            itemOut1.setName("库存商品(" + baseBean.getProductName() + "):" + name);

            itemOut1.setForward(TaxConstanst.TAX_FORWARD_OUT);

            FinanceHelper.copyFinanceItem(financeBean, itemOut1);

            // 库存商品
            String itemTaxIdOut1 = TaxItemConstanst.DEPOR_PRODUCT;

            TaxBean outTax = taxDAO.findByUnique(itemTaxIdOut1);

            if (outTax == null)
            {
                throw new MYException("数据错误,请确认操作");
            }

            // 科目拷贝
            FinanceHelper.copyTax(outTax, itemOut1);

            // 成本价*数量
            double outMoney = -baseBean.getAmount() * baseBean.getCostPrice();

            itemOut1.setInmoney(0);

            itemOut1.setOutmoney(FinanceHelper.doubleToLong(outMoney));

            itemOut1.setDescription(itemOut1.getName());

            // 辅助核算 产品/仓库
            itemOut1.setProductId(baseBean.getProductId());
            itemOut1.setProductAmountOut( -baseBean.getAmount());
            itemOut1.setDepotId(outBean.getLocation());

            itemList.add(itemOut1);
        }
        
        // 样品毛利准备（(售价 - 成本价)*数量）
        for (BaseBean baseBean : baseList)
        {
            FinanceItemBean itemOut2 = new FinanceItemBean();

            itemOut2.setPareId(pare1);

            itemOut2.setName("库存商品(" + baseBean.getProductName() + "):" + name);

            itemOut2.setForward(TaxConstanst.TAX_FORWARD_OUT);

            FinanceHelper.copyFinanceItem(financeBean, itemOut2);

            // 样品毛利准备
            String itemTaxIdOut1 = TaxItemConstanst.SWATCH_GROSS;

            TaxBean outTax = taxDAO.findByUnique(itemTaxIdOut1);

            if (outTax == null)
            {
                throw new MYException("数据错误,请确认操作");
            }

            // 科目拷贝
            FinanceHelper.copyTax(outTax, itemOut2);

            // 成本价*数量
            double outMoney = -baseBean.getAmount() * (baseBean.getPrice() - baseBean.getCostPrice());

            itemOut2.setInmoney(0);

            itemOut2.setOutmoney(FinanceHelper.doubleToLong(outMoney));

            itemOut2.setDescription(itemOut2.getName());

            // 辅助核算 部门/职员/产品/仓库
            copyDepartment(outBean, itemOut2);
            itemOut2.setStafferId(outBean.getStafferId());
            itemOut2.setProductId(baseBean.getProductId());
            itemOut2.setProductAmountIn(baseBean.getAmount());
            itemOut2.setDepotId(outBean.getLocation());

            itemList.add(itemOut2);
        }
    }

    /**
     * processDuiChong2(主营业务成本(5401)/库存商品（成本价*数量）)
     * 
     * @param user
     * @param outBean
     * @throws MYException
     */
    private void processDuiChong2(User user, OutBean outBean)
        throws MYException
    {
        // 主营业务成本(5401) /库存商品（成本价*数量）(负数)
        FinanceBean financeBean = new FinanceBean();

        String name = "B单入库单据:" + outBean.getFullId() + '.';

        financeBean.setName(name);

        // 可以会略的
        financeBean.setType(TaxConstanst.FINANCE_TYPE_MANAGER);

        financeBean.setCreateType(TaxConstanst.FINANCE_CREATETYPE_OUT_BDUI);

        financeBean.setRefId(outBean.getFullId());

        financeBean.setRefOut(outBean.getFullId());

        financeBean.setDutyId(outBean.getDutyId());

        financeBean.setDescription(financeBean.getName());

        financeBean.setFinanceDate(TimeTools.now_short());

        financeBean.setLogTime(TimeTools.now());

        List<FinanceItemBean> itemList = new ArrayList<FinanceItemBean>();

        // 主营业务成本(5401)/库存商品（成本价*数量）
        createDuiChongItem3(user, outBean, financeBean, itemList);

        financeBean.setItemList(itemList);

        financeManager.addFinanceBeanWithoutTransactional(user, financeBean);
    }

    /**
     * 入库-个人领样退库<br>
     * 其他应收款（销售金额，含税价）（负数）/主营业务收入：含税价<br>
     * 主营业务成本（负数）/库存商品（成本价*数量）
     * 
     * @param user
     * @param outBean
     * @throws MYException
     */
    private void processBuySwatch(User user, OutBean outBean)
        throws MYException
    {
        // 其他应收款（销售金额，含税价）/主营业务收入
        // 主营业务成本(5401) /库存商品（成本价*数量）
        FinanceBean financeBean = new FinanceBean();

        String name = "入库-个人领样退库:" + outBean.getFullId() + '.';

        financeBean.setName(name);

        financeBean.setType(TaxConstanst.FINANCE_TYPE_MANAGER);

        financeBean.setCreateType(TaxConstanst.FINANCE_CREATETYPE_OUT_SWATCHBACK);

        financeBean.setRefId(outBean.getFullId());

        financeBean.setRefOut(outBean.getFullId());

        financeBean.setDutyId(outBean.getDutyId());

        financeBean.setDescription(financeBean.getName());

        financeBean.setFinanceDate(TimeTools.now_short());

        financeBean.setLogTime(TimeTools.now());

        List<FinanceItemBean> itemList = new ArrayList<FinanceItemBean>();

   	 	OutBean out = outDAO.find(outBean.getRefOutFullId());
	 
	 	// 之前的退货 按老的产生 
	 	if (TimeTools.cdate(out.getOutTime(), "2014-04-14") < 0)
	 	{
        	// -其他应收款（销售金额，含税价）1133-20/主营业务收入：含税价
            createBuySwatchItem1(user, outBean, financeBean, itemList);

            // -主营业务成本(5401)/库存商品（成本价*数量）
            createBuySwatchItem2(user, outBean, financeBean, itemList);
	 	}else{
	 		createBuySwatchItem11(user, outBean, financeBean, itemList);
	 	}

        financeBean.setItemList(itemList);

        financeManager.addFinanceBeanWithoutTransactional(user, financeBean);
    }

    /**
     * 入库-销售退库
     * 
     * @param user
     * @param outBean
     * @throws MYException
     */
    private void processBuyCommonBack(User user, OutBean outBean)
        throws MYException
    {
        // 其他应收款（销售金额，含税价）/主营业务收入
        // 主营业务成本(5401) /库存商品（成本价*数量）
        FinanceBean financeBean = new FinanceBean();

        String name = "入库-销售退库:" + outBean.getFullId() + '.';

        financeBean.setName(name);

        financeBean.setType(TaxConstanst.FINANCE_TYPE_MANAGER);

        financeBean.setCreateType(TaxConstanst.FINANCE_CREATETYPE_OUT_SAILBACK);

        financeBean.setRefId(outBean.getFullId());

        financeBean.setRefOut(outBean.getFullId());

        financeBean.setDutyId(outBean.getDutyId());

        financeBean.setDescription(financeBean.getName());

        financeBean.setFinanceDate(TimeTools.now_short());

        financeBean.setLogTime(TimeTools.now());

        List<FinanceItemBean> itemList = new ArrayList<FinanceItemBean>();

        // 应收账款（销售金额，含税价）（负数）（1132）/主营业务收入：含税价
        createBuyCommonBackItem1(user, outBean, financeBean, itemList);

        // -主营业务成本(5401)/库存商品（成本价*数量）
        createBuyCommonBackItem2(user, outBean, financeBean, itemList);

        financeBean.setItemList(itemList);

        financeManager.addFinanceBeanWithoutTransactional(user, financeBean);
    }

    /**
     * 促销绑定的销售单销售退库,折扣凭证要记录在原执行促销的订单
     * @param user
     * @param outBean
     * @throws MYException
     */
    private void processBuyCommonExecProm(User user, OutBean outBean, OutBean srcOutBean)
    throws MYException
    {
        // 其他应收款（销售金额，含税价）/主营业务收入_折扣
        FinanceBean financeBean = new FinanceBean();

        String name = "销售出库(折扣):" + srcOutBean.getFullId() + '.';

        financeBean.setName(name);

        financeBean.setType(TaxConstanst.FINANCE_TYPE_MANAGER);

        financeBean.setCreateType(TaxConstanst.FINANCE_CREATETYPE_SAIL_COMMON);

        financeBean.setRefId(srcOutBean.getFullId());

        financeBean.setRefOut(srcOutBean.getFullId());

        financeBean.setDutyId(srcOutBean.getDutyId());

        financeBean.setDescription(financeBean.getName());

        financeBean.setFinanceDate(TimeTools.now_short());

        financeBean.setLogTime(TimeTools.now());

        List<FinanceItemBean> itemList = new ArrayList<FinanceItemBean>();

        // 应收账款（销售金额，含税价）（负数）（1132）/主营业务收入_折扣：含税价
        createBuyCommonExecPromItem(user, outBean, srcOutBean, financeBean, itemList);

        financeBean.setItemList(itemList);

        financeManager.addFinanceBeanWithoutTransactional(user, financeBean);
    }
    
    /**
     * 
     * @param user
     * @param outBean
     * @param financeBean
     * @param itemList
     * @throws MYException
     */
    private void createBuyCommonExecPromItem(User user, OutBean outBean, OutBean srcOutBean, FinanceBean financeBean,
            List<FinanceItemBean> itemList)
    throws MYException
    {
        String name = "销售出库(折扣):" + srcOutBean.getFullId() + '.';

        // 借:库存商品 贷:应付账款-供应商
        FinanceItemBean itemIn1 = new FinanceItemBean();

        String pare1 = commonDAO.getSquenceString();

        itemIn1.setPareId(pare1);

        itemIn1.setName("应收账款:" + name);

        itemIn1.setForward(TaxConstanst.TAX_FORWARD_IN);

        FinanceHelper.copyFinanceItem(financeBean, itemIn1);

        // 应收账款(客户/职员/部门)
        String itemInTaxId = TaxItemConstanst.REVEIVE_PRODUCT;

        TaxBean itemInTax = taxDAO.findByUnique(itemInTaxId);

        if (itemInTax == null)
        {
            throw new MYException("数据错误,请确认操作");
        }

        // 科目拷贝
        FinanceHelper.copyTax(itemInTax, itemIn1);

        double money = outBean.getPromValue();

        itemIn1.setInmoney(FinanceHelper.doubleToLong(money));

        itemIn1.setOutmoney(0);

        itemIn1.setDescription(itemIn1.getName());

        // 辅助核算 客户，职员，部门
        copyDepartment(srcOutBean, itemIn1);
        itemIn1.setStafferId(srcOutBean.getStafferId());
        itemIn1.setUnitId(srcOutBean.getCustomerId());
        itemIn1.setUnitType(TaxConstanst.UNIT_TYPE_CUSTOMER);

        itemList.add(itemIn1);

        // 折扣
        FinanceItemBean itemOut1 = new FinanceItemBean();

        itemOut1.setPareId(pare1);

        itemOut1.setName("主营业务收入_折扣:" + name);

        itemOut1.setForward(TaxConstanst.TAX_FORWARD_OUT);

        FinanceHelper.copyFinanceItem(financeBean, itemOut1);

        // 应收账款(职员/部门)
        String itemOut1TaxId = TaxItemConstanst.MAIN_RECEIVE_REBATE;

        TaxBean itemOut1Tax = taxDAO.findByUnique(itemOut1TaxId);

        if (itemOut1Tax == null)
        {
            throw new MYException("数据错误,请确认操作");
        }

        // 科目拷贝
        FinanceHelper.copyTax(itemOut1Tax, itemOut1);

        double money1 = outBean.getPromValue();
        
        itemOut1.setInmoney(0);

        itemOut1.setOutmoney(FinanceHelper.doubleToLong(money1));

        itemOut1.setDescription(itemOut1.getName());

        // 辅助核算 客户，职员，部门
        copyDepartment(srcOutBean, itemOut1);
        itemOut1.setStafferId(srcOutBean.getStafferId());

        itemList.add(itemOut1);
           
    }
    
    /**
     * 销售-委托代销
     * 
     * @param user
     * @param outBean
     * @throws MYException
     */
    private void processOutConsign(User user, OutBean outBean)
        throws MYException
    {
        // 应收账款/主营业务收入
        // 主营业务成本/库存商品（成本价*数量）
        FinanceBean financeBean = new FinanceBean();

        String name = "销售-委托代销:" + outBean.getFullId() + '.';

        financeBean.setName(name);

        financeBean.setType(TaxConstanst.FINANCE_TYPE_MANAGER);

        financeBean.setCreateType(TaxConstanst.FINANCE_CREATETYPE_SAIL_CONSIGN);

        financeBean.setRefId(outBean.getFullId());

        financeBean.setRefOut(outBean.getFullId());

        financeBean.setDutyId(outBean.getDutyId());

        financeBean.setDescription(financeBean.getName());

        financeBean.setFinanceDate(TimeTools.now_short());

        financeBean.setLogTime(TimeTools.now());

        List<FinanceItemBean> itemList = new ArrayList<FinanceItemBean>();

        // 委托代销商品1261/库存商品（成本价*数量）
        createOutConsignItem(user, outBean, financeBean, itemList);

        financeBean.setItemList(itemList);

        financeManager.addFinanceBeanWithoutTransactional(user, financeBean);
    }

    /**
     * 销售-结算退货单
     * 
     * @param user
     * @param bean
     * @param outBean
     * @throws MYException
     */
    private void processOutBalanceBack(User user, OutBalanceBean bean, OutBean outBean)
        throws MYException
    {
        // 应收账款1132（负数）/主营业务收入5101（负数）
        // 主营业务成本5401/库存商品1243（成本价*数量）
        FinanceBean financeBean = new FinanceBean();

        String name = "委托销售-结算退货单:" + bean.getId() + '.';

        financeBean.setName(name);

        financeBean.setType(TaxConstanst.FINANCE_TYPE_MANAGER);

        financeBean.setCreateType(TaxConstanst.FINANCE_CREATETYPE_SAIL_CONSIGN_BACK);

        financeBean.setRefId(bean.getId());

        financeBean.setRefOut(outBean.getFullId());

        financeBean.setDutyId(outBean.getDutyId());

        financeBean.setDescription(financeBean.getName());

        financeBean.setFinanceDate(TimeTools.now_short());

        financeBean.setLogTime(TimeTools.now());

        List<FinanceItemBean> itemList = new ArrayList<FinanceItemBean>();

        // add 应收账款1132（负数）/主营业务收入5101（负数） 
        createOutBalanceBackItem0(user, outBean, bean, financeBean, itemList);
        
        // 委托代销商品1261（负数）/库存商品（成本价*数量）-- 修改为
        // 主营业务成本5401/库存商品1243（成本价*数量） 
        createOutBalanceBackItem(user, outBean, bean, financeBean, itemList);

        financeBean.setItemList(itemList);

        financeManager.addFinanceBeanWithoutTransactional(user, financeBean);
    }

    /**
     * 销售-委托单退货单
     * 
     * @param user
     * @param bean
     * @param outBean
     * @throws MYException
     */
    private void processOutBalanceBack2(User user, OutBalanceBean bean, OutBean outBean)
        throws MYException
    {
    	// 借  委托代销商品(1261)         负数  退货成本*数量
    	//   贷        库存商品(1243)          负数  退货成本*数量
        FinanceBean financeBean = new FinanceBean();

        String name = "委托销售-委托退货单:" + bean.getId() + '.';

        financeBean.setName(name);

        financeBean.setType(TaxConstanst.FINANCE_TYPE_MANAGER);

        financeBean.setCreateType(TaxConstanst.FINANCE_CREATETYPE_SAIL_CONSIGN_BACK);

        financeBean.setRefId(bean.getId());

        financeBean.setRefOut(outBean.getFullId());

        financeBean.setDutyId(outBean.getDutyId());

        financeBean.setDescription(financeBean.getName());

        financeBean.setFinanceDate(TimeTools.now_short());

        financeBean.setLogTime(TimeTools.now());

        List<FinanceItemBean> itemList = new ArrayList<FinanceItemBean>();

        createOutBalanceBackItem2(user, outBean, bean, financeBean, itemList);

        financeBean.setItemList(itemList);

        financeManager.addFinanceBeanWithoutTransactional(user, financeBean);
    }
    
    /**
     * 委托代销商品/库存商品
     * 
     * @param user
     * @param outBean
     * @param financeBean
     * @param itemList
     * @throws MYException
     */
    private void createOutBalanceBackItem2(User user, OutBean outBean, OutBalanceBean bean, FinanceBean financeBean,
                                      List<FinanceItemBean> itemList)
        throws MYException
    {
        String name = "销售-委托代销退货:" + outBean.getFullId() + '.';

        String pare1 = commonDAO.getSquenceString();

        List<BaseBalanceBean> baseBalanceList = bean.getBaseBalanceList();
        
        List<BaseBean> baseList = outBean.getBaseList();

        // 委托代销商品（成本价*数量）
        for (BaseBalanceBean baseBalanceBean : baseBalanceList)
        {
            BaseBean baseBean = getBaseBean(baseBalanceBean, baseList);
        
            FinanceItemBean itemInEach = new FinanceItemBean();

            itemInEach.setPareId(pare1);

            itemInEach.setName("委托代销商品(" + baseBean.getProductName() + "):" + name);

            itemInEach.setForward(TaxConstanst.TAX_FORWARD_IN);

            FinanceHelper.copyFinanceItem(financeBean, itemInEach);

            // 委托代销商品
            String itemTaxIdIn = TaxItemConstanst.CONSIGN_PRODUCT;

            TaxBean outTax = taxDAO.findByUnique(itemTaxIdIn);

            if (outTax == null)
            {
                throw new MYException("数据错误,请确认操作");
            }

            // 科目拷贝
            FinanceHelper.copyTax(outTax, itemInEach);

            // 委托代销商品（成本*数量）
            double outMoney = -baseBalanceBean.getAmount() * baseBean.getCostPrice();

            itemInEach.setInmoney(FinanceHelper.doubleToLong(outMoney));

            itemInEach.setOutmoney(0);

            itemInEach.setDescription(itemInEach.getName());

            // 辅助核算 产品/仓库
            itemInEach.setProductId(baseBean.getProductId());
            itemInEach.setProductAmountIn(-baseBalanceBean.getAmount());
            itemInEach.setDepotId(outBean.getLocation());

            itemList.add(itemInEach);
        }

        // 库存商品（成本价*数量）
        for (BaseBalanceBean baseBalanceBean : baseBalanceList)
        {
            BaseBean baseBean = getBaseBean(baseBalanceBean, baseList);
            
            FinanceItemBean itemOut1 = new FinanceItemBean();

            itemOut1.setPareId(pare1);

            itemOut1.setName("库存商品(" + baseBean.getProductName() + "):" + name);

            itemOut1.setForward(TaxConstanst.TAX_FORWARD_OUT);

            FinanceHelper.copyFinanceItem(financeBean, itemOut1);

            // 库存商品
            String itemTaxIdOut1 = TaxItemConstanst.DEPOR_PRODUCT;

            TaxBean outTax = taxDAO.findByUnique(itemTaxIdOut1);

            if (outTax == null)
            {
                throw new MYException("数据错误,请确认操作");
            }

            // 科目拷贝
            FinanceHelper.copyTax(outTax, itemOut1);

            // 成本价*数量
            double outMoney = -baseBalanceBean.getAmount() * baseBean.getCostPrice();

            itemOut1.setInmoney(0);
            itemOut1.setOutmoney(FinanceHelper.doubleToLong(outMoney));
            itemOut1.setDescription(itemOut1.getName());

            // 辅助核算 产品/仓库
            itemOut1.setProductId(baseBean.getProductId());
            itemOut1.setProductAmountOut(-baseBalanceBean.getAmount());
            itemOut1.setDepotId(outBean.getLocation());

            itemList.add(itemOut1);
        }
    }
    
    /**
     * 销售-零售
     * 
     * @param user
     * @param outBean
     * @throws MYException
     */
    private void processOutRetail(User user, OutBean outBean)
        throws MYException
    {
        // 应收账款/主营业务收入
        // 主营业务成本/库存商品（成本价*数量）
        FinanceBean financeBean = new FinanceBean();

        String name = "销售出库:" + outBean.getFullId() + '.';

        financeBean.setName(name);

        financeBean.setType(TaxConstanst.FINANCE_TYPE_MANAGER);

        financeBean.setCreateType(TaxConstanst.FINANCE_CREATETYPE_SAIL_RETAIL);

        financeBean.setRefId(outBean.getFullId());

        financeBean.setRefOut(outBean.getFullId());

        financeBean.setDutyId(outBean.getDutyId());

        financeBean.setDescription(financeBean.getName());

        financeBean.setFinanceDate(TimeTools.now_short());

        financeBean.setLogTime(TimeTools.now());

        List<FinanceItemBean> itemList = new ArrayList<FinanceItemBean>();

        // 其他应收款（销售金额，含税价）1133-20/主营业务收入：含税价
        createOutRetailItem(user, outBean, financeBean, itemList);

        // 主营业务成本(5401)/库存商品（成本价*数量）
        createOutRetailItem2(user, outBean, financeBean, itemList);

        financeBean.setItemList(itemList);

        financeManager.addFinanceBeanWithoutTransactional(user, financeBean);
    }

    /**
     * 销售-赠送
     * 
     * @param user
     * @param outBean
     * @param type
     * @throws MYException
     */
    private void processOutPresent(User user, OutBean outBean, int type)
        throws MYException
    {
        // 应收账款/主营业务收入
        // 主营业务成本/库存商品（成本价*数量）
        FinanceBean financeBean = new FinanceBean();

        String name = "销售-赠送:" + outBean.getFullId() + '.';

        financeBean.setName(name);

        financeBean.setType(TaxConstanst.FINANCE_TYPE_MANAGER);

        financeBean.setCreateType(TaxConstanst.FINANCE_CREATETYPE_SAIL_PRESENT);

        financeBean.setRefId(outBean.getFullId());

        financeBean.setRefOut(outBean.getFullId());

        financeBean.setDutyId(outBean.getDutyId());

        financeBean.setDescription(financeBean.getName());

        financeBean.setFinanceDate(TimeTools.now_short());

        financeBean.setLogTime(TimeTools.now());

        List<FinanceItemBean> itemList = new ArrayList<FinanceItemBean>();

        // 营业费用-业务招待费（5501-05）/库存商品（成本价*数量）
        createOutPresentItem(user, outBean, financeBean, itemList, type);

        financeBean.setItemList(itemList);

        financeManager.addFinanceBeanWithoutTransactional(user, financeBean);
    }

    /**
     * 应收账款（销售价*结算数量）/主营业务收入
     * 
     * @param user
     * @param outBean
     * @param financeBean
     * @param itemList
     * @throws MYException
     */
    private void createOutBalanceItem1(User user, OutBean outBean, OutBalanceBean bean,
                                       FinanceBean financeBean, List<FinanceItemBean> itemList)
        throws MYException
    {
        String name = "委托销售-结算单:" + outBean.getFullId() + '.';

        // 借:库存商品 贷:应付账款-供应商
        FinanceItemBean itemIn = new FinanceItemBean();

        String pare1 = commonDAO.getSquenceString();

        itemIn.setPareId(pare1);

        itemIn.setName("应收账款:" + name);

        itemIn.setForward(TaxConstanst.TAX_FORWARD_IN);

        FinanceHelper.copyFinanceItem(financeBean, itemIn);

        // 应收账款
        String itemInTaxId = TaxItemConstanst.REVEIVE_PRODUCT;

        TaxBean itemInTax = taxDAO.findByUnique(itemInTaxId);

        if (itemInTax == null)
        {
            throw new MYException("数据错误,请确认操作");
        }

        // 科目拷贝
        FinanceHelper.copyTax(itemInTax, itemIn);

        double money = bean.getTotal();

        itemIn.setInmoney(FinanceHelper.doubleToLong(money));

        itemIn.setOutmoney(0);

        itemIn.setDescription(itemIn.getName());

        // 辅助核算 客户，职员，部门
        copyDepartment(outBean, itemIn);
        itemIn.setStafferId(outBean.getStafferId());
        itemIn.setUnitId(outBean.getCustomerId());
        itemIn.setUnitType(TaxConstanst.UNIT_TYPE_CUSTOMER);

        itemList.add(itemIn);

        // 获取结算项
        List<BaseBalanceBean> baseList = baseBalanceDAO.queryEntityBeansByFK(bean.getId());

        long outTotal = 0L;

        for (BaseBalanceBean baseBalanceBean : baseList)
        {
            BaseBean base = baseDAO.find(baseBalanceBean.getBaseId());

            if (base == null)
            {
                throw new MYException("数据错误,请确认操作");
            }

            // 贷方
            FinanceItemBean itemOutEach = new FinanceItemBean();

            itemOutEach.setPareId(pare1);

            itemOutEach.setName("主营业务收入:" + name + ".产品:" + base.getProductName());

            itemOutEach.setForward(TaxConstanst.TAX_FORWARD_OUT);

            FinanceHelper.copyFinanceItem(financeBean, itemOutEach);

            // 库存商品
            String itemTaxIdOut1 = TaxItemConstanst.MAIN_RECEIVE;

            TaxBean outTax = taxDAO.findByUnique(itemTaxIdOut1);

            if (outTax == null)
            {
                throw new MYException("数据错误,请确认操作");
            }

            // 科目拷贝
            FinanceHelper.copyTax(outTax, itemOutEach);

            double outMoney = baseBalanceBean.getAmount() * baseBalanceBean.getSailPrice();

            itemOutEach.setInmoney(0);

            itemOutEach.setOutmoney(FinanceHelper.doubleToLong(outMoney));

            outTotal += itemOutEach.getOutmoney();

            itemOutEach.setDescription(itemOutEach.getName());

            // 辅助核算 部门/职员
            copyDepartment(outBean, itemOutEach);
            itemOutEach.setStafferId(outBean.getStafferId());
            itemOutEach.setProductId(base.getProductId());
            itemOutEach.setProductAmountOut(baseBalanceBean.getAmount());
            itemOutEach.setDepotId(base.getLocationId());

            itemList.add(itemOutEach);
        }

        if (Math.abs(outTotal - itemIn.getInmoney()) > 10)
        {
            _logger.error("Inmoney not equal outMonet:" + itemIn.getInmoney() + "/" + outTotal);
        }

        itemIn.setInmoney(outTotal);
    }

    /**
     * 主营业务成本/委托代销商品
     * 
     * @param user
     * @param outBean
     * @param financeBean
     * @param itemList
     * @throws MYException
     */
    private void createOutBalanceItem2(User user, OutBean outBean, OutBalanceBean bean,
                                       FinanceBean financeBean, List<FinanceItemBean> itemList)
        throws MYException
    {
        List<BaseBalanceBean> baseBalanceList = bean.getBaseBalanceList();

        List<BaseBean> baseList = outBean.getBaseList();

        String name = "委托销售-结算单:" + outBean.getFullId() + '.';

        String pare1 = commonDAO.getSquenceString();

        for (BaseBalanceBean baseBalanceBean : baseBalanceList)
        {
            BaseBean baseBean = getBaseBean(baseBalanceBean, baseList);

            // 主营业务成本/委托代销商品
            FinanceItemBean itemInEach = new FinanceItemBean();

            itemInEach.setPareId(pare1);

            itemInEach.setName("主营业务成本:" + name + ".产品:" + baseBean.getProductName());

            itemInEach.setForward(TaxConstanst.TAX_FORWARD_IN);

            FinanceHelper.copyFinanceItem(financeBean, itemInEach);

            // 主营业务成本(部门/职员)
            String itemInTaxId = TaxItemConstanst.MAIN_COST;

            TaxBean itemInTax = taxDAO.findByUnique(itemInTaxId);

            if (itemInTax == null)
            {
                throw new MYException("数据错误,请确认操作");
            }

            // 科目拷贝
            FinanceHelper.copyTax(itemInTax, itemInEach);

            double money = baseBalanceBean.getAmount() * baseBean.getCostPrice();

            itemInEach.setInmoney(FinanceHelper.doubleToLong(money));

            itemInEach.setOutmoney(0);

            itemInEach.setDescription(itemInEach.getName());

            // 辅助核算 客户，职员，部门
            copyDepartment(outBean, itemInEach);
            itemInEach.setStafferId(outBean.getStafferId());
            itemInEach.setProductId(baseBean.getProductId());
            itemInEach.setDepotId(baseBean.getLocationId());
            itemInEach.setProductAmountIn(baseBalanceBean.getAmount());

            itemList.add(itemInEach);
        }

        // 库存商品（成本价*数量）
        for (BaseBalanceBean baseBalanceBean : baseBalanceList)
        {
            BaseBean baseBean = getBaseBean(baseBalanceBean, baseList);

            FinanceItemBean itemOut = new FinanceItemBean();

            itemOut.setPareId(pare1);

            itemOut.setName("委托代销商品(" + baseBean.getProductName() + "):" + name);

            itemOut.setForward(TaxConstanst.TAX_FORWARD_OUT);

            FinanceHelper.copyFinanceItem(financeBean, itemOut);

            // 委托代销商品
            String itemTaxIdOut1 = TaxItemConstanst.CONSIGN_PRODUCT;

            TaxBean outTax = taxDAO.findByUnique(itemTaxIdOut1);

            if (outTax == null)
            {
                throw new MYException("数据错误,请确认操作");
            }

            // 科目拷贝
            FinanceHelper.copyTax(outTax, itemOut);

            // 成本*数量
            double outMoney = baseBalanceBean.getAmount() * baseBean.getCostPrice();

            itemOut.setInmoney(0);

            itemOut.setOutmoney(FinanceHelper.doubleToLong(outMoney));

            itemOut.setDescription(itemOut.getName());

            // 辅助核算 产品/仓库
            itemOut.setProductId(baseBean.getProductId());
            itemOut.setProductAmountOut(baseBalanceBean.getAmount());
            itemOut.setDepotId(outBean.getLocation());

            itemList.add(itemOut);
        }
    }

    private void createOutCommonItem1(User user, OutBean outBean, FinanceBean financeBean,
                                      List<FinanceItemBean> itemList)
        throws MYException
    {
        String name = "销售出库:" + outBean.getFullId() + '.';

        // 借:库存商品 贷:应付账款-供应商
        FinanceItemBean itemIn1 = new FinanceItemBean();

        String pare1 = commonDAO.getSquenceString();

        itemIn1.setPareId(pare1);

        itemIn1.setName("应收账款:" + name);

        itemIn1.setForward(TaxConstanst.TAX_FORWARD_IN);

        FinanceHelper.copyFinanceItem(financeBean, itemIn1);

        // 应收账款(客户/职员/部门)
        String itemInTaxId = TaxItemConstanst.REVEIVE_PRODUCT;

        TaxBean itemInTax = taxDAO.findByUnique(itemInTaxId);

        if (itemInTax == null)
        {
            throw new MYException("数据错误,请确认操作");
        }

        // 科目拷贝
        FinanceHelper.copyTax(itemInTax, itemIn1);

        double money = outBean.getTotal() - outBean.getPromValue();

        itemIn1.setInmoney(FinanceHelper.doubleToLong(money));

        itemIn1.setOutmoney(0);

        itemIn1.setDescription(itemIn1.getName());

        // 辅助核算 客户，职员，部门
        copyDepartment(outBean, itemIn1);
        itemIn1.setStafferId(outBean.getStafferId());
        itemIn1.setUnitId(outBean.getCustomerId());
        itemIn1.setUnitType(TaxConstanst.UNIT_TYPE_CUSTOMER);

        itemList.add(itemIn1);

        List<BaseBean> baseList = baseDAO.queryEntityBeansByFK(outBean.getFullId());

        long outTotal = 0L;

        // 主营业务收入
        for (BaseBean baseBean : baseList)
        {
            // 贷方
            FinanceItemBean itemOutEach = new FinanceItemBean();

            itemOutEach.setPareId(pare1);

            itemOutEach.setName("主营业务收入:" + name + ".产品:" + baseBean.getProductName());

            itemOutEach.setForward(TaxConstanst.TAX_FORWARD_OUT);

            FinanceHelper.copyFinanceItem(financeBean, itemOutEach);

            // 库存商品
            String itemTaxIdOut1 = TaxItemConstanst.MAIN_RECEIVE;

            TaxBean outTax = taxDAO.findByUnique(itemTaxIdOut1);

            if (outTax == null)
            {
                throw new MYException("数据错误,请确认操作");
            }

            // 科目拷贝
            FinanceHelper.copyTax(outTax, itemOutEach);

            double outMoney = baseBean.getAmount() * baseBean.getPrice();

            itemOutEach.setInmoney(0);

            itemOutEach.setOutmoney(FinanceHelper.doubleToLong(outMoney));

            outTotal += itemOutEach.getOutmoney();

            itemOutEach.setDescription(itemOutEach.getName());

            // 辅助核算 部门/职员
            copyDepartment(outBean, itemOutEach);
            itemOutEach.setStafferId(outBean.getStafferId());
            itemOutEach.setProductId(baseBean.getProductId());
            itemOutEach.setDepotId(baseBean.getLocationId());
            itemOutEach.setProductAmountOut(baseBean.getAmount());

            itemList.add(itemOutEach);
        }

        // 增加折扣部分凭证数据 - promStatus == 0 执行了促销
        long outTotal1 = 0L;
        
        if (outBean.getPromStatus() == OutConstant.OUT_PROMSTATUS_EXEC)
        {
            FinanceItemBean itemOut1 = new FinanceItemBean();

            itemOut1.setPareId(pare1);

            itemOut1.setName("主营业务收入_折扣:" + name);

            itemOut1.setForward(TaxConstanst.TAX_FORWARD_OUT);

            FinanceHelper.copyFinanceItem(financeBean, itemOut1);

            // 应收账款(职员/部门)
            String itemOut1TaxId = TaxItemConstanst.MAIN_RECEIVE_REBATE;

            TaxBean itemOut1Tax = taxDAO.findByUnique(itemOut1TaxId);

            if (itemOut1Tax == null)
            {
                throw new MYException("数据错误,请确认操作");
            }

            // 科目拷贝
            FinanceHelper.copyTax(itemOut1Tax, itemOut1);

            double money1 = -outBean.getPromValue();
            
            itemOut1.setInmoney(0);

            itemOut1.setOutmoney(FinanceHelper.doubleToLong(money1));

            outTotal1 = -itemOut1.getOutmoney();
            
            itemOut1.setDescription(itemOut1.getName());

            // 辅助核算 客户，职员，部门
            copyDepartment(outBean, itemOut1);
            itemOut1.setStafferId(outBean.getStafferId());

            itemList.add(itemOut1);
        }
                       
        if (Math.abs(outTotal - outTotal1 - itemIn1.getInmoney()) > 10)
        {
            _logger.error("Inmoney not equal outMonet:" + itemIn1.getInmoney() + "/" + outTotal);
        }

        itemIn1.setInmoney(outTotal - outTotal1);
    }

    /**
     * 应收抵消
     * 
     * @param user
     * @param outBean
     * @param financeBean
     * @param itemList
     * @throws MYException
     */
    private void createOutCommonItem11(User user, OutBean outBean, FinanceBean financeBean,
                                       List<FinanceItemBean> itemList, StafferBean target)
        throws MYException
    {
        String name = "应收抵消:" + outBean.getFullId();

        // 借:库存商品 贷:应付账款-供应商
        FinanceItemBean itemIn1 = new FinanceItemBean();

        String pareId = commonDAO.getSquenceString();

        itemIn1.setPareId(pareId);

        itemIn1.setName("应收账款:" + name);

        itemIn1.setForward(TaxConstanst.TAX_FORWARD_IN);

        FinanceHelper.copyFinanceItem(financeBean, itemIn1);

        // 应收账款(客户/职员/部门)
        String itemInTaxId = TaxItemConstanst.REVEIVE_PRODUCT;

        TaxBean itemInTax = taxDAO.findByUnique(itemInTaxId);

        if (itemInTax == null)
        {
            throw new MYException("数据错误,请确认操作");
        }

        // 科目拷贝
        FinanceHelper.copyTax(itemInTax, itemIn1);

        // 抵消之前的凭证
        double money = outBean.getTotal();

        itemIn1.setInmoney(0);

        itemIn1.setOutmoney(FinanceHelper.doubleToLong(money));

        itemIn1.setDescription(itemIn1.getName());

        // 辅助核算 客户，职员，部门
        copyDepartment(outBean, itemIn1);
        itemIn1.setStafferId(outBean.getStafferId());
        itemIn1.setUnitId(outBean.getCustomerId());
        itemIn1.setUnitType(TaxConstanst.UNIT_TYPE_CUSTOMER);

        itemList.add(itemIn1);

        // 贷方
        FinanceItemBean itemOut = new FinanceItemBean();

        itemOut.setPareId(pareId);

        itemOut.setName(name);

        itemOut.setForward(TaxConstanst.TAX_FORWARD_OUT);

        FinanceHelper.copyFinanceItem(financeBean, itemOut);

        // 应收账款(客户/职员/部门)
        TaxBean outTax = taxDAO.findByUnique(TaxItemConstanst.REVEIVE_PRODUCT);

        if (outTax == null)
        {
            throw new MYException("数据错误,请确认操作");
        }

        // 科目拷贝
        FinanceHelper.copyTax(outTax, itemOut);

        double outMoney = outBean.getTotal();

        itemOut.setInmoney(FinanceHelper.doubleToLong(outMoney));

        itemOut.setOutmoney(0);

        itemOut.setDescription(itemOut.getName());

        // 辅助核算 客户/职员/部门
//        copyDepartment(outBean, itemOut);
        itemOut.setDepartmentId(target.getPrincipalshipId());
        itemOut.setStafferId(target.getId());
        itemOut.setUnitId(outBean.getCustomerId());
        itemOut.setUnitType(TaxConstanst.UNIT_TYPE_CUSTOMER);

        itemList.add(itemOut);
    }

    /**
     * 主营业务收入抵消
     * 
     * @param user
     * @param outBean
     * @param financeBean
     * @param itemList
     * @throws MYException
     */
    private void createOutCommonItem12(User user, OutBean outBean, FinanceBean financeBean,
                                       List<FinanceItemBean> itemList, StafferBean target)
        throws MYException
    {
        String name = "主营业务收入抵消:" + outBean.getFullId();

        String pareId = commonDAO.getSquenceString();

        List<BaseBean> baseList = baseDAO.queryEntityBeansByFK(outBean.getFullId());

        // 借原职员主营业务收入
        for (BaseBean baseBean : baseList)
        {
            // 贷方
            FinanceItemBean itemInEach = new FinanceItemBean();

            itemInEach.setPareId(pareId);

            itemInEach.setName("主营业务收入抵消:" + name + ".产品:" + baseBean.getProductName());

            itemInEach.setForward(TaxConstanst.TAX_FORWARD_OUT);

            FinanceHelper.copyFinanceItem(financeBean, itemInEach);

            // 库存商品
            String itemTaxIdOut1 = TaxItemConstanst.MAIN_RECEIVE;

            TaxBean outTax = taxDAO.findByUnique(itemTaxIdOut1);

            if (outTax == null)
            {
                throw new MYException("数据错误,请确认操作");
            }

            // 科目拷贝
            FinanceHelper.copyTax(outTax, itemInEach);

            double outMoney = baseBean.getAmount() * baseBean.getPrice();

            itemInEach.setInmoney(FinanceHelper.doubleToLong(outMoney));

            itemInEach.setOutmoney(0);

            itemInEach.setDescription(itemInEach.getName());

            // 辅助核算 部门/职员
            copyDepartment(outBean, itemInEach);
            itemInEach.setStafferId(outBean.getStafferId());
            itemInEach.setProductId(baseBean.getProductId());
            itemInEach.setDepotId(baseBean.getLocationId());
            // 数量抵消
            itemInEach.setProductAmountIn(baseBean.getAmount());

            itemList.add(itemInEach);
        }

        // 主营业务收入(新职员)
        for (BaseBean baseBean : baseList)
        {
            // 贷方
            FinanceItemBean itemOutEach = new FinanceItemBean();

            itemOutEach.setPareId(pareId);

            itemOutEach.setName("抵消后,新主营业务收入:" + name + ".产品:" + baseBean.getProductName());

            itemOutEach.setForward(TaxConstanst.TAX_FORWARD_OUT);

            FinanceHelper.copyFinanceItem(financeBean, itemOutEach);

            // 库存商品
            String itemTaxIdOut1 = TaxItemConstanst.MAIN_RECEIVE;

            TaxBean outTax = taxDAO.findByUnique(itemTaxIdOut1);

            if (outTax == null)
            {
                throw new MYException("数据错误,请确认操作");
            }

            // 科目拷贝
            FinanceHelper.copyTax(outTax, itemOutEach);

            double outMoney = baseBean.getAmount() * baseBean.getPrice();

            itemOutEach.setInmoney(0);

            itemOutEach.setOutmoney(FinanceHelper.doubleToLong(outMoney));

            itemOutEach.setDescription(itemOutEach.getName());

            // 辅助核算 部门/职员
            itemOutEach.setDepartmentId(target.getPrincipalshipId());
            itemOutEach.setStafferId(target.getId());
            itemOutEach.setProductId(baseBean.getProductId());
            itemOutEach.setDepotId(baseBean.getLocationId());
            itemOutEach.setProductAmountOut(baseBean.getAmount());

            itemList.add(itemOutEach);
        }
    }

    /**
     * 应收账款（销售金额，含税价）/其他应收款-样品(原个人领样转销售)
     * 
     * @param user
     * @param outBean
     * @param financeBean
     * @param itemList
     * @throws MYException
     */
    protected void createOutCommonItem3(User user, OutBean outBean, FinanceBean financeBean,
                                        List<FinanceItemBean> itemList)
        throws MYException
    {
        String name = "销售-个人领转销售:" + outBean.getFullId() + '.';

        // 借:库存商品 贷:应付账款-供应商
        FinanceItemBean itemIn1 = new FinanceItemBean();

        String pare1 = commonDAO.getSquenceString();

        itemIn1.setPareId(pare1);

        itemIn1.setName("应收账款:" + name);

        itemIn1.setForward(TaxConstanst.TAX_FORWARD_IN);

        FinanceHelper.copyFinanceItem(financeBean, itemIn1);

        // 库存商品
        String itemInTaxId = TaxItemConstanst.REVEIVE_PRODUCT;

        TaxBean itemInTax = taxDAO.findByUnique(itemInTaxId);

        if (itemInTax == null)
        {
            throw new MYException("数据错误,请确认操作");
        }

        // 科目拷贝
        FinanceHelper.copyTax(itemInTax, itemIn1);

        double money = outBean.getTotal();

        itemIn1.setInmoney(FinanceHelper.doubleToLong(money));

        itemIn1.setOutmoney(0);

        itemIn1.setDescription(itemIn1.getName());

        // 辅助核算 客户，职员，部门
        copyDepartment(outBean, itemIn1);
        itemIn1.setStafferId(outBean.getStafferId());
        itemIn1.setUnitId(outBean.getCustomerId());
        itemIn1.setUnitType(TaxConstanst.UNIT_TYPE_CUSTOMER);

        itemList.add(itemIn1);

        List<BaseBean> baseList = baseDAO.queryEntityBeansByFK(outBean.getFullId());

        // 其他应收款-样品（销售价*数量）
        for (BaseBean baseBean : baseList)
        {
            FinanceItemBean itemOutEach = new FinanceItemBean();

            itemOutEach.setPareId(pare1);

            itemOutEach.setName("其他应收款-样品(" + baseBean.getProductName() + "):" + name);

            itemOutEach.setForward(TaxConstanst.TAX_FORWARD_OUT);

            FinanceHelper.copyFinanceItem(financeBean, itemOutEach);

            // 其他应收款-样品
            String itemTaxIdIn = TaxItemConstanst.OTHER_REVEIVE_PRODUCT;

            TaxBean outTax = taxDAO.findByUnique(itemTaxIdIn);

            if (outTax == null)
            {
                throw new MYException("数据错误,请确认操作");
            }

            // 科目拷贝
            FinanceHelper.copyTax(outTax, itemOutEach);

            // 含税价
            double outMoney = baseBean.getAmount() * baseBean.getPrice();

            itemOutEach.setInmoney(0);

            itemOutEach.setOutmoney(FinanceHelper.doubleToLong(outMoney));

            itemOutEach.setDescription(itemOutEach.getName());

            // 辅助核算 部门/职员/产品/仓库
            copyDepartment(outBean, itemOutEach);
            itemOutEach.setStafferId(outBean.getStafferId());
            itemOutEach.setProductId(baseBean.getProductId());
            itemOutEach.setProductAmountOut(baseBean.getAmount());
            itemOutEach.setDepotId(outBean.getLocation());

            itemList.add(itemOutEach);
        }
    }

    /**
     * 应收账款/主营业务收入
     * 
     * @param user
     * @param outBean
     * @param financeBean
     * @param itemList
     * @throws MYException
     */
    private void createOutRetailItem(User user, OutBean outBean, FinanceBean financeBean,
                                     List<FinanceItemBean> itemList)
        throws MYException
    {
        String name = "销售-零售:" + outBean.getFullId() + '.';

        // 其他应收款（销售金额，含税价）1133-20/主营业务收入
        String pare1 = commonDAO.getSquenceString();

        // 借:库存商品 贷:应付账款-供应商
        FinanceItemBean itemIn1 = new FinanceItemBean();

        itemIn1.setPareId(pare1);

        itemIn1.setName("应收账款:" + name);

        itemIn1.setForward(TaxConstanst.TAX_FORWARD_IN);

        FinanceHelper.copyFinanceItem(financeBean, itemIn1);

        // 库存商品
        String itemInTaxId = TaxItemConstanst.REVEIVE_PRODUCT;

        TaxBean itemInTax = taxDAO.findByUnique(itemInTaxId);

        if (itemInTax == null)
        {
            throw new MYException("数据错误,请确认操作");
        }

        // 科目拷贝
        FinanceHelper.copyTax(itemInTax, itemIn1);

        double money = outBean.getTotal();

        itemIn1.setInmoney(FinanceHelper.doubleToLong(money));

        itemIn1.setOutmoney(0);

        itemIn1.setDescription(itemIn1.getName());

        // 辅助核算 客户，职员，部门
        copyDepartment(outBean, itemIn1);
        itemIn1.setStafferId(outBean.getStafferId());
        itemIn1.setUnitId(outBean.getCustomerId());
        itemIn1.setUnitType(TaxConstanst.UNIT_TYPE_CUSTOMER);

        itemList.add(itemIn1);

        List<BaseBean> baseList = baseDAO.queryEntityBeansByFK(outBean.getFullId());

        long outTotal = 0L;

        // 主营业务收入
        for (BaseBean baseBean : baseList)
        {
            // 贷方
            FinanceItemBean itemOutEach = new FinanceItemBean();

            itemOutEach.setPareId(pare1);

            itemOutEach.setName("主营业务收入:" + name + ".产品:" + baseBean.getProductName());

            itemOutEach.setForward(TaxConstanst.TAX_FORWARD_OUT);

            FinanceHelper.copyFinanceItem(financeBean, itemOutEach);

            // 库存商品
            String itemTaxIdOut1 = TaxItemConstanst.MAIN_RECEIVE;

            TaxBean outTax = taxDAO.findByUnique(itemTaxIdOut1);

            if (outTax == null)
            {
                throw new MYException("数据错误,请确认操作");
            }

            // 科目拷贝
            FinanceHelper.copyTax(outTax, itemOutEach);

            // 销售价
            double outMoney = baseBean.getAmount() * baseBean.getPrice();

            itemOutEach.setInmoney(0);

            itemOutEach.setOutmoney(FinanceHelper.doubleToLong(outMoney));

            outTotal += itemOutEach.getOutmoney();

            itemOutEach.setDescription(itemOutEach.getName());

            // 辅助核算 部门/职员
            copyDepartment(outBean, itemOutEach);
            itemOutEach.setStafferId(outBean.getStafferId());
            itemOutEach.setProductId(baseBean.getProductId());
            itemOutEach.setProductAmountOut(baseBean.getAmount());
            itemOutEach.setDepotId(baseBean.getLocationId());

            itemList.add(itemOutEach);
        }

        if (Math.abs(outTotal - itemIn1.getInmoney()) > 10)
        {
            _logger.error("Inmoney not equal outMonet:" + itemIn1.getInmoney() + "/" + outTotal);
        }

        itemIn1.setInmoney(outTotal);
    }

    /**
     * 其他应收款（销售金额，含税价）1133-20/主营业务收入：含税价
     * 
     * @param user
     * @param outBean
     * @param financeBean
     * @param itemList
     * @throws MYException
     */
    private void createOutSwatchItem1(User user, OutBean outBean, FinanceBean financeBean,
                                      List<FinanceItemBean> itemList)
        throws MYException
    {
        String name = "个人领样:" + outBean.getFullId() + '.';

        // 其他应收款（销售金额，含税价）1133-20/主营业务收入
        String pare1 = commonDAO.getSquenceString();

        List<BaseBean> baseList = outBean.getBaseList();

        // 库存商品（成本价*数量）
        for (BaseBean baseBean : baseList)
        {
            FinanceItemBean itemInEach = new FinanceItemBean();

            itemInEach.setPareId(pare1);

            itemInEach.setName("其他应收款-样品(" + baseBean.getProductName() + "):" + name);

            itemInEach.setForward(TaxConstanst.TAX_FORWARD_IN);

            FinanceHelper.copyFinanceItem(financeBean, itemInEach);

            // 其他应收款-样品
            String itemTaxIdIn = TaxItemConstanst.OTHER_REVEIVE_PRODUCT;

            TaxBean outTax = taxDAO.findByUnique(itemTaxIdIn);

            if (outTax == null)
            {
                throw new MYException("数据错误,请确认操作");
            }

            // 科目拷贝
            FinanceHelper.copyTax(outTax, itemInEach);

            // 含税价
            double outMoney = baseBean.getAmount() * baseBean.getPrice();

            itemInEach.setInmoney(FinanceHelper.doubleToLong(outMoney));

            itemInEach.setOutmoney(0);

            itemInEach.setDescription(itemInEach.getName());

            // 辅助核算 部门/职员/产品/仓库
            copyDepartment(outBean, itemInEach);
            itemInEach.setStafferId(outBean.getStafferId());
            itemInEach.setProductId(baseBean.getProductId());
            itemInEach.setProductAmountIn(baseBean.getAmount());
            itemInEach.setDepotId(outBean.getLocation());

            itemList.add(itemInEach);
        }

        // 库存商品（成本价*数量）
        for (BaseBean baseBean : baseList)
        {
            FinanceItemBean itemOut1 = new FinanceItemBean();

            itemOut1.setPareId(pare1);

            itemOut1.setName("库存商品(" + baseBean.getProductName() + "):" + name);

            itemOut1.setForward(TaxConstanst.TAX_FORWARD_OUT);

            FinanceHelper.copyFinanceItem(financeBean, itemOut1);

            // 库存商品
            String itemTaxIdOut1 = TaxItemConstanst.DEPOR_PRODUCT;

            TaxBean outTax = taxDAO.findByUnique(itemTaxIdOut1);

            if (outTax == null)
            {
                throw new MYException("数据错误,请确认操作");
            }

            // 科目拷贝
            FinanceHelper.copyTax(outTax, itemOut1);

            // 成本价*数量
            double outMoney = baseBean.getAmount() * baseBean.getCostPrice();

            itemOut1.setInmoney(0);

            itemOut1.setOutmoney(FinanceHelper.doubleToLong(outMoney));

            itemOut1.setDescription(itemOut1.getName());

            // 辅助核算 产品/仓库
            itemOut1.setProductId(baseBean.getProductId());
            itemOut1.setProductAmountOut(baseBean.getAmount());
            itemOut1.setDepotId(outBean.getLocation());

            itemList.add(itemOut1);
        }
        
        // 样品毛利准备（(售价 - 成本价)*数量）
        for (BaseBean baseBean : baseList)
        {
            FinanceItemBean itemOut2 = new FinanceItemBean();

            itemOut2.setPareId(pare1);

            itemOut2.setName("库存商品(" + baseBean.getProductName() + "):" + name);

            itemOut2.setForward(TaxConstanst.TAX_FORWARD_OUT);

            FinanceHelper.copyFinanceItem(financeBean, itemOut2);

            // 样品毛利准备
            String itemTaxIdOut1 = TaxItemConstanst.SWATCH_GROSS;

            TaxBean outTax = taxDAO.findByUnique(itemTaxIdOut1);

            if (outTax == null)
            {
                throw new MYException("数据错误,请确认操作");
            }

            // 科目拷贝
            FinanceHelper.copyTax(outTax, itemOut2);

            // 成本价*数量
            double outMoney = baseBean.getAmount() * (baseBean.getPrice() - baseBean.getCostPrice());

            itemOut2.setInmoney(0);

            itemOut2.setOutmoney(FinanceHelper.doubleToLong(outMoney));

            itemOut2.setDescription(itemOut2.getName());

            // 辅助核算 部门/职员/产品/仓库
            copyDepartment(outBean, itemOut2);
            itemOut2.setStafferId(outBean.getStafferId());
            itemOut2.setProductId(baseBean.getProductId());
            itemOut2.setProductAmountIn(baseBean.getAmount());
            itemOut2.setDepotId(outBean.getLocation());

            itemList.add(itemOut2);
        }
    }

    /**
     * createDuiChongItem1
     * 
     * @param user
     * @param outBean
     * @param financeBean
     * @param itemList
     * @throws MYException
     */
    private void createDuiChongItem1(User user, OutBean outBean, FinanceBean financeBean,
                                     List<FinanceItemBean> itemList)
        throws MYException
    {
        String name = "领样转销售对冲单据:" + outBean.getFullId() + '.';

        // 其他应收款（销售金额，含税价）1133-20/主营业务收入
        String pare1 = commonDAO.getSquenceString();

        List<BaseBean> baseList = outBean.getBaseList();

        // 库存商品（成本价*数量）
        for (BaseBean baseBean : baseList)
        {
            FinanceItemBean itemInEach = new FinanceItemBean();

            itemInEach.setPareId(pare1);

            itemInEach.setName("其他应收款-样品(" + baseBean.getProductName() + "):" + name);

            itemInEach.setForward(TaxConstanst.TAX_FORWARD_IN);

            FinanceHelper.copyFinanceItem(financeBean, itemInEach);

            // 其他应收款-样品
            String itemTaxIdIn = TaxItemConstanst.OTHER_REVEIVE_PRODUCT;

            TaxBean outTax = taxDAO.findByUnique(itemTaxIdIn);

            if (outTax == null)
            {
                throw new MYException("数据错误,请确认操作");
            }

            // 科目拷贝
            FinanceHelper.copyTax(outTax, itemInEach);

            // 含税价
            double outMoney = baseBean.getAmount() * baseBean.getPrice();

            itemInEach.setInmoney( -FinanceHelper.doubleToLong(outMoney));

            itemInEach.setOutmoney(0);

            itemInEach.setDescription(itemInEach.getName());

            // 辅助核算 部门/职员/产品/仓库
            copyDepartment(outBean, itemInEach);
            itemInEach.setStafferId(outBean.getStafferId());
            itemInEach.setProductId(baseBean.getProductId());
            itemInEach.setProductAmountIn( -baseBean.getAmount());
            itemInEach.setDepotId(outBean.getLocation());

            itemList.add(itemInEach);
        }

        for (BaseBean baseBean : baseList)
        {
            // 贷方
            FinanceItemBean itemOutEach = new FinanceItemBean();

            itemOutEach.setPareId(pare1);

            itemOutEach.setName("主营业务收入:" + name + ".产品:" + baseBean.getProductName());

            itemOutEach.setForward(TaxConstanst.TAX_FORWARD_OUT);

            FinanceHelper.copyFinanceItem(financeBean, itemOutEach);

            // 库存商品
            String itemTaxIdOutEach = TaxItemConstanst.MAIN_RECEIVE;

            TaxBean outTax = taxDAO.findByUnique(itemTaxIdOutEach);

            if (outTax == null)
            {
                throw new MYException("数据错误,请确认操作");
            }

            // 科目拷贝
            FinanceHelper.copyTax(outTax, itemOutEach);

            // 销售价
            double outMoney = baseBean.getAmount() * baseBean.getPrice();

            itemOutEach.setInmoney(0);

            itemOutEach.setOutmoney( -FinanceHelper.doubleToLong(outMoney));

            itemOutEach.setDescription(itemOutEach.getName());

            // 辅助核算 部门/职员
            copyDepartment(outBean, itemOutEach);
            itemOutEach.setStafferId(outBean.getStafferId());
            itemOutEach.setProductId(baseBean.getProductId());
            itemOutEach.setDepotId(baseBean.getLocationId());
            itemOutEach.setProductAmountOut( -baseBean.getAmount());

            itemList.add(itemOutEach);
        }
    }

    /**
     * 其他应收款-样品（销售金额，含税价）（负数）/主营业务收入：含税价
     * 
     * @param user
     * @param outBean
     * @param financeBean
     * @param itemList
     * @throws MYException
     */
    private void createBuySwatchItem1(User user, OutBean outBean, FinanceBean financeBean,
                                      List<FinanceItemBean> itemList)
        throws MYException
    {
        String name = "入库-个人领样退库:" + outBean.getFullId() + '.';

        // 其他应收款（销售金额，含税价）1133-20/主营业务收入
        String pare1 = commonDAO.getSquenceString();

        List<BaseBean> baseList = outBean.getBaseList();

        // 其他应收款-样品（成本价*数量）
        for (BaseBean baseBean : baseList)
        {
            FinanceItemBean itemInEach = new FinanceItemBean();

            itemInEach.setPareId(pare1);

            itemInEach.setName("其他应收款-样品(" + baseBean.getProductName() + "):" + name);

            itemInEach.setForward(TaxConstanst.TAX_FORWARD_IN);

            FinanceHelper.copyFinanceItem(financeBean, itemInEach);

            // 其他应收款-样品
            String itemTaxIdIn = TaxItemConstanst.OTHER_REVEIVE_PRODUCT;

            TaxBean outTax = taxDAO.findByUnique(itemTaxIdIn);

            if (outTax == null)
            {
                throw new MYException("数据错误,请确认操作");
            }

            // 科目拷贝
            FinanceHelper.copyTax(outTax, itemInEach);

            // 含税价
            double outMoney = -baseBean.getAmount() * baseBean.getPrice();

            itemInEach.setInmoney(FinanceHelper.doubleToLong(outMoney));

            itemInEach.setOutmoney(0);

            itemInEach.setDescription(itemInEach.getName());

            // 辅助核算 部门/职员/产品/仓库
            copyDepartment(outBean, itemInEach);
            itemInEach.setStafferId(outBean.getStafferId());
            itemInEach.setProductId(baseBean.getProductId());
            itemInEach.setProductAmountIn( -baseBean.getAmount());
            itemInEach.setDepotId(outBean.getLocation());

            itemList.add(itemInEach);
        }

        // 主营业务收入
        for (BaseBean baseBean : baseList)
        {
            // 贷方
            FinanceItemBean itemOut1 = new FinanceItemBean();

            itemOut1.setPareId(pare1);

            itemOut1.setName("主营业务收入:" + name + ".产品:" + baseBean.getProductName());

            itemOut1.setForward(TaxConstanst.TAX_FORWARD_OUT);

            FinanceHelper.copyFinanceItem(financeBean, itemOut1);

            // 库存商品
            String itemTaxIdOut1 = TaxItemConstanst.MAIN_RECEIVE;

            TaxBean outTax = taxDAO.findByUnique(itemTaxIdOut1);

            if (outTax == null)
            {
                throw new MYException("数据错误,请确认操作");
            }

            // 科目拷贝
            FinanceHelper.copyTax(outTax, itemOut1);

            // 总销售价
            double outMoney = -baseBean.getAmount() * baseBean.getPrice();

            itemOut1.setInmoney(0);

            itemOut1.setOutmoney(FinanceHelper.doubleToLong(outMoney));

            itemOut1.setDescription(itemOut1.getName());

            // 辅助核算 部门/职员
            copyDepartment(outBean, itemOut1);
            itemOut1.setStafferId(outBean.getStafferId());
            itemOut1.setProductId(baseBean.getProductId());
            itemOut1.setDepotId(baseBean.getLocationId());
            itemOut1.setProductAmountOut( -baseBean.getAmount());

            itemList.add(itemOut1);
        }
    }
    
    private void createBuySwatchItem11(User user, OutBean outBean, FinanceBean financeBean,
            List<FinanceItemBean> itemList)
	throws MYException
	{
        String name = "入库-个人领样退库:" + outBean.getFullId() + '.';

        // 其他应收款（销售金额，含税价）1133-20/主营业务收入
        String pare1 = commonDAO.getSquenceString();

        List<BaseBean> baseList = outBean.getBaseList();

        // 其他应收款-样品（成本价*数量）
        for (BaseBean baseBean : baseList)
        {
            FinanceItemBean itemInEach = new FinanceItemBean();

            itemInEach.setPareId(pare1);

            itemInEach.setName("其他应收款-样品(" + baseBean.getProductName() + "):" + name);

            itemInEach.setForward(TaxConstanst.TAX_FORWARD_IN);

            FinanceHelper.copyFinanceItem(financeBean, itemInEach);

            // 其他应收款-样品
            String itemTaxIdIn = TaxItemConstanst.OTHER_REVEIVE_PRODUCT;

            TaxBean outTax = taxDAO.findByUnique(itemTaxIdIn);

            if (outTax == null)
            {
                throw new MYException("数据错误,请确认操作");
            }

            // 科目拷贝
            FinanceHelper.copyTax(outTax, itemInEach);

            // 含税价
            double outMoney = -baseBean.getAmount() * baseBean.getPrice();

            itemInEach.setInmoney(FinanceHelper.doubleToLong(outMoney));

            itemInEach.setOutmoney(0);

            itemInEach.setDescription(itemInEach.getName());

            // 辅助核算 部门/职员/产品/仓库
            copyDepartment(outBean, itemInEach);
            itemInEach.setStafferId(outBean.getStafferId());
            itemInEach.setProductId(baseBean.getProductId());
            itemInEach.setProductAmountIn( -baseBean.getAmount());
            itemInEach.setDepotId(outBean.getLocation());

            itemList.add(itemInEach);
        }

        // 库存商品（成本价*数量）
        for (BaseBean baseBean : baseList)
        {
            FinanceItemBean itemOut1 = new FinanceItemBean();

            itemOut1.setPareId(pare1);

            itemOut1.setName("库存商品(" + baseBean.getProductName() + "):" + name);

            itemOut1.setForward(TaxConstanst.TAX_FORWARD_OUT);

            FinanceHelper.copyFinanceItem(financeBean, itemOut1);

            // 库存商品
            String itemTaxIdOut1 = TaxItemConstanst.DEPOR_PRODUCT;

            TaxBean outTax = taxDAO.findByUnique(itemTaxIdOut1);

            if (outTax == null)
            {
                throw new MYException("数据错误,请确认操作");
            }

            // 科目拷贝
            FinanceHelper.copyTax(outTax, itemOut1);

            // 成本价*数量
            double outMoney = -baseBean.getAmount() * baseBean.getCostPrice();

            itemOut1.setInmoney(0);

            itemOut1.setOutmoney(FinanceHelper.doubleToLong(outMoney));

            itemOut1.setDescription(itemOut1.getName());

            // 辅助核算 产品/仓库
            itemOut1.setProductId(baseBean.getProductId());
            itemOut1.setProductAmountOut( -baseBean.getAmount());
            itemOut1.setDepotId(outBean.getLocation());

            itemList.add(itemOut1);
        }
        
        // 样品毛利准备（(售价 - 成本价)*数量）
        for (BaseBean baseBean : baseList)
        {
            FinanceItemBean itemOut2 = new FinanceItemBean();

            itemOut2.setPareId(pare1);

            itemOut2.setName("库存商品(" + baseBean.getProductName() + "):" + name);

            itemOut2.setForward(TaxConstanst.TAX_FORWARD_OUT);

            FinanceHelper.copyFinanceItem(financeBean, itemOut2);

            // 样品毛利准备
            String itemTaxIdOut1 = TaxItemConstanst.SWATCH_GROSS;

            TaxBean outTax = taxDAO.findByUnique(itemTaxIdOut1);

            if (outTax == null)
            {
                throw new MYException("数据错误,请确认操作");
            }

            // 科目拷贝
            FinanceHelper.copyTax(outTax, itemOut2);

            // 成本价*数量
            double outMoney = -baseBean.getAmount() * (baseBean.getPrice() - baseBean.getCostPrice());

            itemOut2.setInmoney(0);

            itemOut2.setOutmoney(FinanceHelper.doubleToLong(outMoney));

            itemOut2.setDescription(itemOut2.getName());

            // 辅助核算 部门/职员/产品/仓库
            copyDepartment(outBean, itemOut2);
            itemOut2.setStafferId(outBean.getStafferId());
            itemOut2.setProductId(baseBean.getProductId());
            itemOut2.setProductAmountIn(baseBean.getAmount());
            itemOut2.setDepotId(outBean.getLocation());

            itemList.add(itemOut2);
        }
    }

    /**
     * 应收账款（销售金额，含税价）（负数）（1132）/主营业务收入：含税价
     * 
     * @param user
     * @param outBean
     * @param financeBean
     * @param itemList
     * @throws MYException
     */
    private void createBuyCommonBackItem1(User user, OutBean outBean, FinanceBean financeBean,
                                          List<FinanceItemBean> itemList)
        throws MYException
    {
        String name = "入库-销售退库:" + outBean.getFullId() + '.';

        // 借:库存商品 贷:应付账款-供应商
        FinanceItemBean itemIn = new FinanceItemBean();

        String pare1 = commonDAO.getSquenceString();

        itemIn.setPareId(pare1);

        itemIn.setName("应收账款:" + name);

        itemIn.setForward(TaxConstanst.TAX_FORWARD_IN);

        FinanceHelper.copyFinanceItem(financeBean, itemIn);

        // 库存商品
        String itemInTaxId = TaxItemConstanst.REVEIVE_PRODUCT;

        TaxBean itemInTax = taxDAO.findByUnique(itemInTaxId);

        if (itemInTax == null)
        {
            throw new MYException("数据错误,请确认操作");
        }

        // 科目拷贝
        FinanceHelper.copyTax(itemInTax, itemIn);

        double money = 0.0d;
        
        money = -outBean.getTotal() ;
        
        if ((outBean.getPromStatus() == OutConstant.OUT_PROMSTATUS_BACKBIND
                || outBean.getPromStatus() == OutConstant.OUT_PROMSTATUS_PARTBACKBIND)
                && FinanceHelper.isPromBind(outBean.getRefOutFullId(), outBean.getRefBindOutId()))
        {
            money = -outBean.getTotal() + outBean.getPromValue();
        }

        itemIn.setInmoney(FinanceHelper.doubleToLong(money));

        itemIn.setOutmoney(0);

        itemIn.setDescription(itemIn.getName());

        // 辅助核算 客户，职员，部门
        copyDepartment(outBean, itemIn);
        itemIn.setStafferId(outBean.getStafferId());
        itemIn.setUnitId(outBean.getCustomerId());
        itemIn.setUnitType(TaxConstanst.UNIT_TYPE_CUSTOMER);

        itemList.add(itemIn);

        List<BaseBean> baseList = baseDAO.queryEntityBeansByFK(outBean.getFullId());

        long outTotal = 0L;

        // 主营业务收入
        for (BaseBean baseBean : baseList)
        {
            // 贷方
            FinanceItemBean itemOut1 = new FinanceItemBean();

            itemOut1.setPareId(pare1);

            itemOut1.setName("主营业务收入:" + name + ".产品:" + baseBean.getProductName());

            itemOut1.setForward(TaxConstanst.TAX_FORWARD_OUT);

            FinanceHelper.copyFinanceItem(financeBean, itemOut1);

            // 库存商品
            String itemTaxIdOut1 = TaxItemConstanst.MAIN_RECEIVE;

            TaxBean outTax = taxDAO.findByUnique(itemTaxIdOut1);

            if (outTax == null)
            {
                throw new MYException("数据错误,请确认操作");
            }

            // 科目拷贝
            FinanceHelper.copyTax(outTax, itemOut1);

            // 总销售价
            double outMoney = -baseBean.getAmount() * baseBean.getPrice();

            itemOut1.setInmoney(0);

            itemOut1.setOutmoney(FinanceHelper.doubleToLong(outMoney));

            outTotal += itemOut1.getOutmoney();

            itemOut1.setDescription(itemOut1.getName());

            // 辅助核算 部门/职员
            copyDepartment(outBean, itemOut1);
            itemOut1.setStafferId(outBean.getStafferId());
            itemOut1.setProductId(baseBean.getProductId());
            itemOut1.setDepotId(baseBean.getLocationId());
            itemOut1.setProductAmountOut( -baseBean.getAmount());

            itemList.add(itemOut1);
        }

        // 增加折扣部分凭证数据 - promStatus == 2 or 3 对于参加了促销的销售退库的处理,且要求只对执行促销的销售退库有效,绑定销售单的退库另外处理
        long outTotal1 = 0L;
        
        if ((outBean.getPromStatus() == OutConstant.OUT_PROMSTATUS_BACKBIND
                || outBean.getPromStatus() == OutConstant.OUT_PROMSTATUS_PARTBACKBIND)
                && FinanceHelper.isPromBind(outBean.getRefOutFullId(), outBean.getRefBindOutId()))
        {
            FinanceItemBean itemOut1 = new FinanceItemBean();

            itemOut1.setPareId(pare1);

            itemOut1.setName("主营业务收入_折扣:" + name);

            itemOut1.setForward(TaxConstanst.TAX_FORWARD_OUT);

            FinanceHelper.copyFinanceItem(financeBean, itemOut1);

            // 应收账款(职员/部门)
            String itemOut1TaxId = TaxItemConstanst.MAIN_RECEIVE_REBATE;

            TaxBean itemOut1Tax = taxDAO.findByUnique(itemOut1TaxId);

            if (itemOut1Tax == null)
            {
                throw new MYException("数据错误,请确认操作");
            }

            // 科目拷贝
            FinanceHelper.copyTax(itemOut1Tax, itemOut1);

            double money1 = outBean.getPromValue();
            
            itemOut1.setInmoney(0);

            itemOut1.setOutmoney(FinanceHelper.doubleToLong(money1));

            outTotal1 = itemOut1.getOutmoney();
            
            itemOut1.setDescription(itemOut1.getName());

            // 辅助核算 客户，职员，部门
            copyDepartment(outBean, itemOut1);
            itemOut1.setStafferId(outBean.getStafferId());

            itemList.add(itemOut1);
        }
                
        // TEMPLATE 说明存在差异
        if (Math.abs(outTotal + outTotal1 - itemIn.getInmoney()) > 10)
        {
            _logger.error("Inmoney not equal outMonet:" + itemIn.getInmoney() + "/" + outTotal);
        }

        itemIn.setInmoney(outTotal + outTotal1);
    }

    /**
     * 部门辅助核算
     * 
     * @param outBean
     * @param item
     */
    private void copyDepartment(OutBean outBean, FinanceItemBean item) throws MYException
    {
        
        // 其它入库 真实的业务员（关联原单入库红冲）
        if (outBean.getReserve9().trim().length() > 0)
        {
            StafferBean sb = stafferDAO.find(outBean.getReserve9().trim());

            if ( !StringTools.isNullOrNone(sb.getIndustryId3()))
            {
                item.setDepartmentId(sb.getIndustryId3());

                return;
            }            
            
            if ( !StringTools.isNullOrNone(sb.getIndustryId2()))
            {
                item.setDepartmentId(sb.getIndustryId2());

                return;
            }

            if ( !StringTools.isNullOrNone(sb.getIndustryId()))
            {
                item.setDepartmentId(sb.getIndustryId());

                return;
            }
            
        }else
        {
//            if ( !StringTools.isNullOrNone(outBean.getIndustryId3()))
//            {
//                item.setDepartmentId(outBean.getIndustryId3());
//
//                return;
//            }
//            
//            if ( !StringTools.isNullOrNone(outBean.getIndustryId2()))
//            {
//                item.setDepartmentId(outBean.getIndustryId2());
//
//                return;
//            }
//
//            if ( !StringTools.isNullOrNone(outBean.getIndustryId()))
//            {
//                item.setDepartmentId(outBean.getIndustryId());
//
//                return;
//            }
        	
            StafferBean sb = stafferDAO.find(outBean.getStafferId());
            
            if (sb == null) {
            	StafferVSCustomerBean vsCust = stafferVSCustomerDAO.findByUnique(outBean.getCustomerId());
    	    	
    	    	if (vsCust == null) {
    	    		throw new MYException("客户 " + outBean.getCustomerName() + " 没有挂靠关系");
    	    	} else {
    	    		sb = stafferDAO.find(vsCust.getStafferId());
    	    	}
            }

            if ( !StringTools.isNullOrNone(sb.getIndustryId3()))
            {
                item.setDepartmentId(sb.getIndustryId3());

                return;
            }            
            
            if ( !StringTools.isNullOrNone(sb.getIndustryId2()))
            {
                item.setDepartmentId(sb.getIndustryId2());

                return;
            }

            if ( !StringTools.isNullOrNone(sb.getIndustryId()))
            {
                item.setDepartmentId(sb.getIndustryId());

                return;
            }
        }
        

    }

    /**
     * 主营业务成本(5401)/库存商品（成本价*数量）
     * 
     * @param user
     * @param outBean
     * @param financeBean
     * @param itemList
     * @throws MYException
     */
    private void createOutCommonItem2(User user, OutBean outBean, FinanceBean financeBean,
                                      List<FinanceItemBean> itemList)
        throws MYException
    {
        String name = "销售出库:" + outBean.getFullId() + '.';

        String pare1 = commonDAO.getSquenceString();

        List<BaseBean> baseList = baseDAO.queryEntityBeansByFK(outBean.getFullId());

        for (BaseBean baseBean : baseList)
        {
            // 借:库存商品 贷:应付账款-供应商
            FinanceItemBean itemInEach = new FinanceItemBean();

            itemInEach.setPareId(pare1);

            itemInEach.setName("主营业务成本:" + name + ".产品:" + baseBean.getProductName());

            itemInEach.setForward(TaxConstanst.TAX_FORWARD_IN);

            FinanceHelper.copyFinanceItem(financeBean, itemInEach);

            // 主营业务成本(部门/职员)
            String itemInTaxId = TaxItemConstanst.MAIN_COST;

            TaxBean itemInTax = taxDAO.findByUnique(itemInTaxId);

            if (itemInTax == null)
            {
                throw new MYException("数据错误,请确认操作");
            }

            // 科目拷贝
            FinanceHelper.copyTax(itemInTax, itemInEach);

            double money = baseBean.getAmount() * baseBean.getCostPrice();

            itemInEach.setInmoney(FinanceHelper.doubleToLong(money));

            itemInEach.setOutmoney(0);

            itemInEach.setDescription(itemInEach.getName());

            // 辅助核算 客户，职员，部门
            copyDepartment(outBean, itemInEach);
            itemInEach.setStafferId(outBean.getStafferId());
            itemInEach.setProductId(baseBean.getProductId());
            itemInEach.setDepotId(baseBean.getLocationId());
            itemInEach.setProductAmountIn(baseBean.getAmount());

            itemList.add(itemInEach);
        }

        // 库存商品（成本价*数量）
        for (BaseBean baseBean : baseList)
        {
            FinanceItemBean itemOut1 = new FinanceItemBean();

            itemOut1.setPareId(pare1);

            itemOut1.setName("库存商品(" + baseBean.getProductName() + "):" + name);

            itemOut1.setForward(TaxConstanst.TAX_FORWARD_OUT);

            FinanceHelper.copyFinanceItem(financeBean, itemOut1);

            // 库存商品
            String itemTaxIdOut1 = TaxItemConstanst.DEPOR_PRODUCT;

            TaxBean outTax = taxDAO.findByUnique(itemTaxIdOut1);

            if (outTax == null)
            {
                throw new MYException("数据错误,请确认操作");
            }

            // 科目拷贝
            FinanceHelper.copyTax(outTax, itemOut1);

            double outMoney = baseBean.getAmount() * baseBean.getCostPrice();

            itemOut1.setInmoney(0);

            itemOut1.setOutmoney(FinanceHelper.doubleToLong(outMoney));

            itemOut1.setDescription(itemOut1.getName());

            // 辅助核算 产品/仓库
            itemOut1.setProductId(baseBean.getProductId());
            itemOut1.setProductAmountOut(baseBean.getAmount());
            itemOut1.setDepotId(outBean.getLocation());

            itemList.add(itemOut1);
        }
    }

    /**
     * 主营业务成本抵消
     * 
     * @param user
     * @param outBean
     * @param financeBean
     * @param itemList
     * @throws MYException
     */
    private void createOutCommonItem13(User user, OutBean outBean, FinanceBean financeBean,
                                       List<FinanceItemBean> itemList, StafferBean targerStaffer)
        throws MYException
    {
        String pareId = commonDAO.getSquenceString();

        List<BaseBean> baseList = baseDAO.queryEntityBeansByFK(outBean.getFullId());

        // 老职员抵消
        for (BaseBean baseBean : baseList)
        {
            // 借:库存商品 贷:应付账款-供应商
            FinanceItemBean itemInEach = new FinanceItemBean();

            itemInEach.setPareId(pareId);

            itemInEach.setName("主营业务成本抵消:" + outBean.getFullId() + ".产品:"
                               + baseBean.getProductName());

            itemInEach.setForward(TaxConstanst.TAX_FORWARD_IN);

            FinanceHelper.copyFinanceItem(financeBean, itemInEach);

            // 主营业务成本(部门/职员)
            String itemInTaxId = TaxItemConstanst.MAIN_COST;

            TaxBean itemInTax = taxDAO.findByUnique(itemInTaxId);

            if (itemInTax == null)
            {
                throw new MYException("数据错误,请确认操作");
            }

            // 科目拷贝
            FinanceHelper.copyTax(itemInTax, itemInEach);

            double money = baseBean.getAmount() * baseBean.getCostPrice();

            itemInEach.setInmoney(0);

            itemInEach.setOutmoney(FinanceHelper.doubleToLong(money));

            itemInEach.setDescription(itemInEach.getName());

            // 辅助核算 客户，职员，部门
            copyDepartment(outBean, itemInEach);
            itemInEach.setStafferId(outBean.getStafferId());
            itemInEach.setProductId(baseBean.getProductId());
            itemInEach.setDepotId(baseBean.getLocationId());
            itemInEach.setProductAmountOut(baseBean.getAmount());

            itemList.add(itemInEach);
        }

        // 新职员
        for (BaseBean baseBean : baseList)
        {
            // 借:库存商品 贷:应付账款-供应商
            FinanceItemBean itemOutEach = new FinanceItemBean();

            itemOutEach.setPareId(pareId);

            itemOutEach.setName("抵消后,新主营业务成本:" + outBean.getFullId() + ".产品:"
                                + baseBean.getProductName());

            itemOutEach.setForward(TaxConstanst.TAX_FORWARD_IN);

            FinanceHelper.copyFinanceItem(financeBean, itemOutEach);

            // 主营业务成本(部门/职员)
            String itemInTaxId = TaxItemConstanst.MAIN_COST;

            TaxBean itemInTax = taxDAO.findByUnique(itemInTaxId);

            if (itemInTax == null)
            {
                throw new MYException("数据错误,请确认操作");
            }

            // 科目拷贝
            FinanceHelper.copyTax(itemInTax, itemOutEach);

            double money = baseBean.getAmount() * baseBean.getCostPrice();

            itemOutEach.setInmoney(FinanceHelper.doubleToLong(money));

            itemOutEach.setOutmoney(0);

            itemOutEach.setDescription(itemOutEach.getName());

            // 辅助核算 客户，职员，部门
            itemOutEach.setDepartmentId(targerStaffer.getPrincipalshipId());
            itemOutEach.setStafferId(targerStaffer.getId());
            itemOutEach.setProductId(baseBean.getProductId());
            itemOutEach.setDepotId(baseBean.getLocationId());
            itemOutEach.setProductAmountIn(baseBean.getAmount());

            itemList.add(itemOutEach);
        }

    }
    
    /**
     * 其它入库针对有原单红冲处理，凭证为负数
     * @param user
     * @param outBean
     * @param financeBean
     * @param itemList
     * @throws MYException
     */
    private void createOutCommonItem14(User user, OutBean outBean, FinanceBean financeBean,
            List<FinanceItemBean> itemList)
    throws MYException
    {
        String name = "其它出库:" + outBean.getFullId() + '.';

        // 借:库存商品 贷:应付账款-供应商
        FinanceItemBean itemIn1 = new FinanceItemBean();

        String pare1 = commonDAO.getSquenceString();

        itemIn1.setPareId(pare1);

        itemIn1.setName("应收账款:" + name);

        itemIn1.setForward(TaxConstanst.TAX_FORWARD_IN);

        FinanceHelper.copyFinanceItem(financeBean, itemIn1);

        // 应收账款(客户/职员/部门)
        String itemInTaxId = TaxItemConstanst.REVEIVE_PRODUCT;

        TaxBean itemInTax = taxDAO.findByUnique(itemInTaxId);

        if (itemInTax == null)
        {
            throw new MYException("数据错误,请确认操作");
        }

        // 科目拷贝
        FinanceHelper.copyTax(itemInTax, itemIn1);

        double money = outBean.getTotal();

        itemIn1.setInmoney(-FinanceHelper.doubleToLong(money));

        itemIn1.setOutmoney(0);

        itemIn1.setDescription(itemIn1.getName());

        // 辅助核算 客户，职员，部门
        copyDepartment(outBean, itemIn1);
        // 其它入库时,reserve9是业务员，而staffer id为经手人（入库人）
        if (outBean.getReserve9().trim().length() > 0){
            itemIn1.setStafferId(outBean.getReserve9().trim());
        }else
        {
            itemIn1.setStafferId(outBean.getStafferId());    
        }
        
        itemIn1.setUnitId(outBean.getCustomerId());
        itemIn1.setUnitType(TaxConstanst.UNIT_TYPE_CUSTOMER);

        itemList.add(itemIn1);

        List<BaseBean> baseList = baseDAO.queryEntityBeansByFK(outBean.getFullId());

        long outTotal = 0L;

        // 主营业务收入
        for (BaseBean baseBean : baseList)
        {
            // 贷方
            FinanceItemBean itemOutEach = new FinanceItemBean();

            itemOutEach.setPareId(pare1);

            itemOutEach.setName("主营业务收入:" + name + ".产品:" + baseBean.getProductName());

            itemOutEach.setForward(TaxConstanst.TAX_FORWARD_OUT);

            FinanceHelper.copyFinanceItem(financeBean, itemOutEach);

            // 库存商品
            String itemTaxIdOut1 = TaxItemConstanst.MAIN_RECEIVE;

            TaxBean outTax = taxDAO.findByUnique(itemTaxIdOut1);

            if (outTax == null)
            {
                throw new MYException("数据错误,请确认操作");
            }

            // 科目拷贝
            FinanceHelper.copyTax(outTax, itemOutEach);

            double outMoney = baseBean.getAmount() * baseBean.getPrice();

            itemOutEach.setInmoney(0);

            itemOutEach.setOutmoney(-FinanceHelper.doubleToLong(outMoney));

            outTotal += itemOutEach.getOutmoney();

            itemOutEach.setDescription(itemOutEach.getName());

            // 辅助核算 部门/职员
            copyDepartment(outBean, itemOutEach);
            
            // 其它入库时,reserve9是业务员，而staffer id为经手人（入库人）
            if (outBean.getReserve9().trim().length() > 0){
                itemOutEach.setStafferId(outBean.getReserve9().trim());
            }else
            {
                itemOutEach.setStafferId(outBean.getStafferId());    
            }            
            
            itemOutEach.setProductId(baseBean.getProductId());
            itemOutEach.setDepotId(baseBean.getLocationId());
            itemOutEach.setProductAmountOut(baseBean.getAmount());

            itemList.add(itemOutEach);
        }

        if (Math.abs(outTotal - itemIn1.getInmoney()) > 10)
        {
            _logger.error("Inmoney not equal outMonet:" + itemIn1.getInmoney() + "/" + outTotal);
        }

        itemIn1.setInmoney(outTotal);
    }
    
    /**
     * 
     * @param user
     * @param outBean
     * @param financeBean
     * @param itemList
     * @throws MYException
     */
    private void createOutCommonItem24(User user, OutBean outBean, FinanceBean financeBean,
            List<FinanceItemBean> itemList)
    throws MYException
    {
        String name = "其它出库:" + outBean.getFullId() + '.';

        String pare1 = commonDAO.getSquenceString();

        List<BaseBean> baseList = baseDAO.queryEntityBeansByFK(outBean.getFullId());

        for (BaseBean baseBean : baseList)
        {
            // 借:库存商品 贷:应付账款-供应商
            FinanceItemBean itemInEach = new FinanceItemBean();

            itemInEach.setPareId(pare1);

            itemInEach.setName("主营业务成本:" + name + ".产品:" + baseBean.getProductName());

            itemInEach.setForward(TaxConstanst.TAX_FORWARD_IN);

            FinanceHelper.copyFinanceItem(financeBean, itemInEach);

            // 主营业务成本(部门/职员)
            String itemInTaxId = TaxItemConstanst.MAIN_COST;

            TaxBean itemInTax = taxDAO.findByUnique(itemInTaxId);

            if (itemInTax == null)
            {
                throw new MYException("数据错误,请确认操作");
            }

            // 科目拷贝
            FinanceHelper.copyTax(itemInTax, itemInEach);

            double money = baseBean.getAmount() * baseBean.getCostPrice();

            itemInEach.setInmoney(-FinanceHelper.doubleToLong(money));

            itemInEach.setOutmoney(0);

            itemInEach.setDescription(itemInEach.getName());

            // 辅助核算 客户，职员，部门
            copyDepartment(outBean, itemInEach);
            // 其它入库时,reserve9是业务员，而staffer id为经手人（入库人）
            if (outBean.getReserve9().trim().length() > 0){
                itemInEach.setStafferId(outBean.getReserve9().trim());
            }else
            {
                itemInEach.setStafferId(outBean.getStafferId());    
            } 
            itemInEach.setProductId(baseBean.getProductId());
            itemInEach.setDepotId(baseBean.getLocationId());
            itemInEach.setProductAmountIn(baseBean.getAmount());

            itemList.add(itemInEach);
        }

        // 库存商品（成本价*数量）
        for (BaseBean baseBean : baseList)
        {
            FinanceItemBean itemOut1 = new FinanceItemBean();

            itemOut1.setPareId(pare1);

            itemOut1.setName("库存商品(" + baseBean.getProductName() + "):" + name);

            itemOut1.setForward(TaxConstanst.TAX_FORWARD_OUT);

            FinanceHelper.copyFinanceItem(financeBean, itemOut1);

            // 库存商品
            String itemTaxIdOut1 = TaxItemConstanst.DEPOR_PRODUCT;

            TaxBean outTax = taxDAO.findByUnique(itemTaxIdOut1);

            if (outTax == null)
            {
                throw new MYException("数据错误,请确认操作");
            }

            // 科目拷贝
            FinanceHelper.copyTax(outTax, itemOut1);

            double outMoney = baseBean.getAmount() * baseBean.getCostPrice();

            itemOut1.setInmoney(0);

            itemOut1.setOutmoney(-FinanceHelper.doubleToLong(outMoney));

            itemOut1.setDescription(itemOut1.getName());

            // 辅助核算 产品/仓库
            itemOut1.setProductId(baseBean.getProductId());
            itemOut1.setProductAmountOut(baseBean.getAmount());
            itemOut1.setDepotId(outBean.getLocation());

            itemList.add(itemOut1);
        }
    }
    
    /**
     * 委托代销商品/库存商品
     * 
     * @param user
     * @param outBean
     * @param financeBean
     * @param itemList
     * @throws MYException
     */
    private void createOutConsignItem(User user, OutBean outBean, FinanceBean financeBean,
                                      List<FinanceItemBean> itemList)
        throws MYException
    {
        String name = "销售-委托代销:" + outBean.getFullId() + '.';

        String pare1 = commonDAO.getSquenceString();

        List<BaseBean> baseList = outBean.getBaseList();

        // 委托代销商品（成本价*数量）
        for (BaseBean baseBean : baseList)
        {
            FinanceItemBean itemInEach = new FinanceItemBean();

            itemInEach.setPareId(pare1);

            itemInEach.setName("委托代销商品(" + baseBean.getProductName() + "):" + name);

            itemInEach.setForward(TaxConstanst.TAX_FORWARD_IN);

            FinanceHelper.copyFinanceItem(financeBean, itemInEach);

            // 委托代销商品
            String itemTaxIdIn = TaxItemConstanst.CONSIGN_PRODUCT;

            TaxBean outTax = taxDAO.findByUnique(itemTaxIdIn);

            if (outTax == null)
            {
                throw new MYException("数据错误,请确认操作");
            }

            // 科目拷贝
            FinanceHelper.copyTax(outTax, itemInEach);

            // 委托代销商品（成本*数量）
            double outMoney = baseBean.getAmount() * baseBean.getCostPrice();

            itemInEach.setInmoney(FinanceHelper.doubleToLong(outMoney));

            itemInEach.setOutmoney(0);

            itemInEach.setDescription(itemInEach.getName());

            // 辅助核算 产品/仓库
            itemInEach.setProductId(baseBean.getProductId());
            itemInEach.setProductAmountIn(baseBean.getAmount());
            itemInEach.setDepotId(outBean.getLocation());

            itemList.add(itemInEach);
        }

        // 库存商品（成本价*数量）
        for (BaseBean baseBean : baseList)
        {
            FinanceItemBean itemOut1 = new FinanceItemBean();

            itemOut1.setPareId(pare1);

            itemOut1.setName("库存商品(" + baseBean.getProductName() + "):" + name);

            itemOut1.setForward(TaxConstanst.TAX_FORWARD_OUT);

            FinanceHelper.copyFinanceItem(financeBean, itemOut1);

            // 库存商品
            String itemTaxIdOut1 = TaxItemConstanst.DEPOR_PRODUCT;

            TaxBean outTax = taxDAO.findByUnique(itemTaxIdOut1);

            if (outTax == null)
            {
                throw new MYException("数据错误,请确认操作");
            }

            // 科目拷贝
            FinanceHelper.copyTax(outTax, itemOut1);

            // 成本价*数量
            double outMoney = baseBean.getAmount() * baseBean.getCostPrice();

            itemOut1.setInmoney(0);
            itemOut1.setOutmoney(FinanceHelper.doubleToLong(outMoney));
            itemOut1.setDescription(itemOut1.getName());

            // 辅助核算 产品/仓库
            itemOut1.setProductId(baseBean.getProductId());
            itemOut1.setProductAmountOut(baseBean.getAmount());
            itemOut1.setDepotId(outBean.getLocation());

            itemList.add(itemOut1);
        }
    }
    
    /**
     * createOutBalanceBackItem
     * 
     * @param user
     * @param outBean
     * @param bean
     * @param financeBean
     * @param itemList
     * @throws MYException
     */
    private void createOutBalanceBackItem(User user, OutBean outBean, OutBalanceBean bean,
                                          FinanceBean financeBean, List<FinanceItemBean> itemList)
        throws MYException
    {
        String name = "委托销售-结算退货单:" + outBean.getFullId() + '.';

        String pare1 = commonDAO.getSquenceString();

        List<BaseBean> baseList = outBean.getBaseList();

        List<BaseBalanceBean> baseBalanceList = bean.getBaseBalanceList();

        // 委托代销商品（成本价*数量）
        for (BaseBalanceBean baseBalanceBean : baseBalanceList)
        {
            BaseBean baseBean = getBaseBean(baseBalanceBean, baseList);

            FinanceItemBean itemInEach = new FinanceItemBean();

            itemInEach.setPareId(pare1);

            itemInEach.setName("委托代销商品(" + baseBean.getProductName() + "):" + name);

            itemInEach.setForward(TaxConstanst.TAX_FORWARD_IN);

            FinanceHelper.copyFinanceItem(financeBean, itemInEach);

            // 委托代销商品 ->改为 主营业务成本5401
            String itemTaxIdIn = TaxItemConstanst.MAIN_COST;

            TaxBean outTax = taxDAO.findByUnique(itemTaxIdIn);

            if (outTax == null)
            {
                throw new MYException("数据错误,请确认操作");
            }

            // 科目拷贝
            FinanceHelper.copyTax(outTax, itemInEach);

            // 委托代销商品（成本*数量）
            double outMoney = -baseBalanceBean.getAmount() * baseBean.getCostPrice();

            itemInEach.setInmoney(FinanceHelper.doubleToLong(outMoney));

            itemInEach.setOutmoney(0);

            itemInEach.setDescription(itemInEach.getName());

            // 辅助核算 产品/仓库
//            itemInEach.setProductId(baseBean.getProductId());
//            itemInEach.setProductAmountIn( -baseBalanceBean.getAmount());
//            itemInEach.setDepotId(outBean.getLocation());

            copyDepartment(outBean, itemInEach);
            itemInEach.setStafferId(outBean.getStafferId());
            itemInEach.setProductId(baseBean.getProductId());
            itemInEach.setDepotId(baseBean.getLocationId());
            itemInEach.setProductAmountIn( -baseBalanceBean.getAmount());
            
            itemList.add(itemInEach);
        }

        // 库存商品（成本价*数量）
        for (BaseBalanceBean baseBalanceBean : baseBalanceList)
        {
            BaseBean baseBean = getBaseBean(baseBalanceBean, baseList);

            FinanceItemBean itemOut1 = new FinanceItemBean();

            itemOut1.setPareId(pare1);

            itemOut1.setName("库存商品(" + baseBean.getProductName() + "):" + name);

            itemOut1.setForward(TaxConstanst.TAX_FORWARD_OUT);

            FinanceHelper.copyFinanceItem(financeBean, itemOut1);

            // 库存商品
            String itemTaxIdOut1 = TaxItemConstanst.DEPOR_PRODUCT;

            TaxBean outTax = taxDAO.findByUnique(itemTaxIdOut1);

            if (outTax == null)
            {
                throw new MYException("数据错误,请确认操作");
            }

            // 科目拷贝
            FinanceHelper.copyTax(outTax, itemOut1);

            // 成本价*数量
            double outMoney = -baseBalanceBean.getAmount() * baseBean.getCostPrice();

            itemOut1.setInmoney(0);
            itemOut1.setOutmoney(FinanceHelper.doubleToLong(outMoney));
            itemOut1.setDescription(itemOut1.getName());

            // 辅助核算 产品/仓库
            itemOut1.setProductId(baseBean.getProductId());
            itemOut1.setProductAmountOut( -baseBalanceBean.getAmount());
            itemOut1.setDepotId(outBean.getLocation());

            itemList.add(itemOut1);
        }
    }

    /**
     * 销售-委托代销退货, 应收账款(1132), 主营业务收入(5101),核算到单品 
     * @param user
     * @param outBean
     * @param bean
     * @param financeBean
     * @param itemList
     * @throws MYException
     */
    private void createOutBalanceBackItem0(User user, OutBean outBean, OutBalanceBean bean,
            FinanceBean financeBean, List<FinanceItemBean> itemList)
    throws MYException
    {
        String name = "委托销售-结算退货单:" + outBean.getFullId() + '.';

        // 借:应收账款 贷:主营业务收入
        FinanceItemBean itemIn = new FinanceItemBean();
        
        String pare1 = commonDAO.getSquenceString();

        itemIn.setPareId(pare1);

        itemIn.setName("应收账款:" + name);

        itemIn.setForward(TaxConstanst.TAX_FORWARD_IN);

        FinanceHelper.copyFinanceItem(financeBean, itemIn);

        // 应收账款
        String itemInTaxId = TaxItemConstanst.REVEIVE_PRODUCT;

        TaxBean itemInTax = taxDAO.findByUnique(itemInTaxId);

        if (itemInTax == null)
        {
            throw new MYException("数据错误,请确认操作");
        }

        // 科目拷贝
        FinanceHelper.copyTax(itemInTax, itemIn);

        double money = -bean.getTotal();

        itemIn.setInmoney(FinanceHelper.doubleToLong(money));

        itemIn.setOutmoney(0);

        itemIn.setDescription(itemIn.getName());

        // 辅助核算 客户，职员，部门
        copyDepartment(outBean, itemIn);
        itemIn.setStafferId(outBean.getStafferId());
        itemIn.setUnitId(outBean.getCustomerId());
        itemIn.setUnitType(TaxConstanst.UNIT_TYPE_CUSTOMER);

        itemList.add(itemIn);
        
        List<BaseBean> baseList = outBean.getBaseList();

        List<BaseBalanceBean> baseBalanceList = bean.getBaseBalanceList();

/*        // 委托代销商品（成本价*数量）
        for (BaseBalanceBean baseBalanceBean : baseBalanceList)
        {
            BaseBean baseBean = getBaseBean(baseBalanceBean, baseList);

            FinanceItemBean itemInEach = new FinanceItemBean();

            itemInEach.setPareId(pare1);

            itemInEach.setName("应收账款(" + baseBean.getProductName() + "):" + name);

            itemInEach.setForward(TaxConstanst.TAX_FORWARD_IN);

            FinanceHelper.copyFinanceItem(financeBean, itemInEach);

            // 委托代销商品 ->改为 主营业务成本5401
            String itemTaxIdIn = TaxItemConstanst.MAIN_COST;

            TaxBean outTax = taxDAO.findByUnique(itemTaxIdIn);

            if (outTax == null)
            {
                throw new MYException("数据错误,请确认操作");
            }

            // 科目拷贝
            FinanceHelper.copyTax(outTax, itemInEach);

            // 委托代销商品（成本*数量）
            double outMoney = -baseBalanceBean.getAmount() * baseBean.getCostPrice();

            itemInEach.setInmoney(FinanceHelper.doubleToLong(outMoney));

            itemInEach.setOutmoney(0);

            itemInEach.setDescription(itemInEach.getName());

            // 辅助核算 产品/仓库
            copyDepartment(outBean, itemInEach);
            itemInEach.setStafferId(outBean.getStafferId());
            itemInEach.setProductId(baseBean.getProductId());
            itemInEach.setDepotId(baseBean.getLocationId());
            itemInEach.setProductAmountIn( -baseBalanceBean.getAmount());
            
            itemList.add(itemInEach);
        }*/

        // 主营业务收入（成本价*数量）
        for (BaseBalanceBean baseBalanceBean : baseBalanceList)
        {
            BaseBean baseBean = getBaseBean(baseBalanceBean, baseList);

            FinanceItemBean itemOut1 = new FinanceItemBean();

            itemOut1.setPareId(pare1);

            itemOut1.setName("主营业务收入(" + baseBean.getProductName() + "):" + name);

            itemOut1.setForward(TaxConstanst.TAX_FORWARD_OUT);

            FinanceHelper.copyFinanceItem(financeBean, itemOut1);

            // 主营业务收入
            String itemTaxIdOut1 = TaxItemConstanst.MAIN_RECEIVE;

            TaxBean outTax = taxDAO.findByUnique(itemTaxIdOut1);

            if (outTax == null)
            {
                throw new MYException("数据错误,请确认操作");
            }

            // 科目拷贝
            FinanceHelper.copyTax(outTax, itemOut1);

            // 销售价*数量
            double outMoney = -baseBalanceBean.getAmount() * baseBalanceBean.getSailPrice();

            itemOut1.setInmoney(0);
            itemOut1.setOutmoney(FinanceHelper.doubleToLong(outMoney));
            itemOut1.setDescription(itemOut1.getName());

            // 辅助核算 产品/仓库
//            itemOut1.setProductId(baseBean.getProductId());
//            itemOut1.setProductAmountOut( -baseBalanceBean.getAmount());
//            itemOut1.setDepotId(outBean.getLocation());
            
            copyDepartment(outBean, itemOut1);
            itemOut1.setStafferId(outBean.getStafferId());
            itemOut1.setProductId(baseBean.getProductId());
            itemOut1.setDepotId(baseBean.getLocationId());
            itemOut1.setProductAmountIn( -baseBalanceBean.getAmount());

            itemList.add(itemOut1);
        }
    }    
    
    /**
     * 主营业务成本/库存商品
     * 
     * @param user
     * @param outBean
     * @param financeBean
     * @param itemList
     * @throws MYException
     */
    private void createOutRetailItem2(User user, OutBean outBean, FinanceBean financeBean,
                                      List<FinanceItemBean> itemList)
        throws MYException
    {
        List<BaseBean> baseList = outBean.getBaseList();

        String name = "销售-零售:" + outBean.getFullId() + '.';

        String pare1 = commonDAO.getSquenceString();

        for (BaseBean baseBean : baseList)
        {
            // 借:库存商品 贷:应付账款-供应商
            FinanceItemBean itemInEach = new FinanceItemBean();

            itemInEach.setPareId(pare1);

            itemInEach.setName("主营业务成本:" + name + ".产品:" + baseBean.getProductName());

            itemInEach.setForward(TaxConstanst.TAX_FORWARD_IN);

            FinanceHelper.copyFinanceItem(financeBean, itemInEach);

            // 主营业务成本(部门/职员)
            String itemInTaxId = TaxItemConstanst.MAIN_COST;

            TaxBean itemInTax = taxDAO.findByUnique(itemInTaxId);

            if (itemInTax == null)
            {
                throw new MYException("数据错误,请确认操作");
            }

            // 科目拷贝
            FinanceHelper.copyTax(itemInTax, itemInEach);

            double money = baseBean.getAmount() * baseBean.getCostPrice();

            itemInEach.setInmoney(FinanceHelper.doubleToLong(money));

            itemInEach.setOutmoney(0);

            itemInEach.setDescription(itemInEach.getName());

            // 辅助核算 职员/部门
            copyDepartment(outBean, itemInEach);
            itemInEach.setStafferId(outBean.getStafferId());
            itemInEach.setProductId(baseBean.getProductId());
            itemInEach.setProductAmountIn(baseBean.getAmount());
            itemInEach.setDepotId(baseBean.getLocationId());

            itemList.add(itemInEach);
        }

        // 库存商品（成本价*数量）
        for (BaseBean baseBean : baseList)
        {
            FinanceItemBean itemOut1 = new FinanceItemBean();

            itemOut1.setPareId(pare1);

            itemOut1.setName("库存商品(" + baseBean.getProductName() + "):" + name);

            itemOut1.setForward(TaxConstanst.TAX_FORWARD_OUT);

            FinanceHelper.copyFinanceItem(financeBean, itemOut1);

            // 库存商品
            String itemTaxIdOut1 = TaxItemConstanst.DEPOR_PRODUCT;

            TaxBean outTax = taxDAO.findByUnique(itemTaxIdOut1);

            if (outTax == null)
            {
                throw new MYException("数据错误,请确认操作");
            }

            // 科目拷贝
            FinanceHelper.copyTax(outTax, itemOut1);

            // 成本价*数量
            double outMoney = baseBean.getAmount() * baseBean.getCostPrice();

            itemOut1.setInmoney(0);

            itemOut1.setOutmoney(FinanceHelper.doubleToLong(outMoney));

            itemOut1.setDescription(itemOut1.getName());

            // 辅助核算 产品/仓库
            itemOut1.setProductId(baseBean.getProductId());
            itemOut1.setProductAmountOut(baseBean.getAmount());
            itemOut1.setDepotId(outBean.getLocation());

            itemList.add(itemOut1);
        }
    }

    /**
     * 销售-赠送( 营业费用-业务招待费（5501-05）/库存商品（成本价*数量）)
     * 
     * @param user
     * @param outBean
     * @param financeBean
     * @param itemList
     * @throws MYException
     */
    private void createOutPresentItem(User user, OutBean outBean, FinanceBean financeBean,
                                      List<FinanceItemBean> itemList, int type)
        throws MYException
    {
        String name = "销售-赠送:" + outBean.getFullId() + '.';

        // 借:库存商品 贷:应付账款-供应商
        FinanceItemBean itemIn = new FinanceItemBean();

        String pare1 = commonDAO.getSquenceString();

        itemIn.setPareId(pare1);

        itemIn.setName("主营业务成本:" + name);

        itemIn.setForward(TaxConstanst.TAX_FORWARD_IN);

        FinanceHelper.copyFinanceItem(financeBean, itemIn);

        // 营业费用-买赠费用(单位/部门/职员/纳税实体)
        String itemInTaxId = TaxItemConstanst.RECEIVE_COMMON1;

        TaxBean itemInTax = taxDAO.findByUnique(itemInTaxId);

        if (itemInTax == null)
        {
            throw new MYException("数据错误,请确认操作");
        }

        // 科目拷贝
        FinanceHelper.copyTax(itemInTax, itemIn);

        final List<BaseBean> baseList = baseDAO.queryEntityBeansByFK(outBean.getFullId());

        outBean.setBaseList(baseList);

        double money = type * getOutCost(outBean);

        itemIn.setInmoney(FinanceHelper.doubleToLong(money));

        itemIn.setOutmoney(0);

        itemIn.setDescription(itemIn.getName());

        // 辅助核算 单位/部门/职员/纳税实体
        itemIn.setUnitId(outBean.getCustomerId());
        copyDepartment(outBean, itemIn);
        itemIn.setStafferId(outBean.getStafferId());
        itemIn.setDuty2Id(outBean.getDutyId());

        itemList.add(itemIn);

        // 库存商品（成本价*数量）
        for (BaseBean baseBean : baseList)
        {
            FinanceItemBean itemOut1 = new FinanceItemBean();

            itemOut1.setPareId(pare1);

            itemOut1.setName("库存商品(" + baseBean.getProductName() + "):" + name);

            itemOut1.setForward(TaxConstanst.TAX_FORWARD_OUT);

            FinanceHelper.copyFinanceItem(financeBean, itemOut1);

            // 库存商品
            String itemTaxIdOut1 = TaxItemConstanst.DEPOR_PRODUCT;

            TaxBean outTax = taxDAO.findByUnique(itemTaxIdOut1);

            if (outTax == null)
            {
                throw new MYException("数据错误,请确认操作");
            }

            // 科目拷贝
            FinanceHelper.copyTax(outTax, itemOut1);

            // 成本价*数量
            double outMoney = type * baseBean.getAmount() * baseBean.getCostPrice();

            itemOut1.setInmoney(0);

            itemOut1.setOutmoney(FinanceHelper.doubleToLong(outMoney));

            itemOut1.setDescription(itemOut1.getName());

            // 辅助核算 产品/仓库
            itemOut1.setProductId(baseBean.getProductId());
            itemOut1.setProductAmountOut(baseBean.getAmount());
            itemOut1.setDepotId(outBean.getLocation());

            itemList.add(itemOut1);
        }
    }

    @Deprecated
    private void createOutSwatchItem2(User user, OutBean outBean, FinanceBean financeBean,
                                      List<FinanceItemBean> itemList)
        throws MYException
    {
        List<BaseBean> baseList = outBean.getBaseList();

        String name = "个人领样:" + outBean.getFullId() + '.';

        String pare1 = commonDAO.getSquenceString();

        for (BaseBean baseBean : baseList)
        {
            // 借:库存商品 贷:应付账款-供应商
            FinanceItemBean itemInEach = new FinanceItemBean();

            itemInEach.setPareId(pare1);

            itemInEach.setName("主营业务成本:" + name + ".产品:" + baseBean.getProductName());

            itemInEach.setForward(TaxConstanst.TAX_FORWARD_IN);

            FinanceHelper.copyFinanceItem(financeBean, itemInEach);

            // 主营业务成本(部门/职员)
            String itemInTaxId = TaxItemConstanst.MAIN_COST;

            TaxBean itemInTax = taxDAO.findByUnique(itemInTaxId);

            if (itemInTax == null)
            {
                throw new MYException("数据错误,请确认操作");
            }

            // 科目拷贝
            FinanceHelper.copyTax(itemInTax, itemInEach);

            double money = baseBean.getAmount() * baseBean.getCostPrice();

            itemInEach.setInmoney(FinanceHelper.doubleToLong(money));

            itemInEach.setOutmoney(0);

            itemInEach.setDescription(itemInEach.getName());

            // 辅助核算 职员/部门
            copyDepartment(outBean, itemInEach);
            itemInEach.setStafferId(outBean.getStafferId());
            itemInEach.setProductId(baseBean.getProductId());
            itemInEach.setProductAmountIn(baseBean.getAmount());
            itemInEach.setDepotId(baseBean.getLocationId());

            itemList.add(itemInEach);
        }

        // 库存商品（成本价*数量）
        for (BaseBean baseBean : baseList)
        {
            FinanceItemBean itemOut1 = new FinanceItemBean();

            itemOut1.setPareId(pare1);

            itemOut1.setName("库存商品(" + baseBean.getProductName() + "):" + name);

            itemOut1.setForward(TaxConstanst.TAX_FORWARD_OUT);

            FinanceHelper.copyFinanceItem(financeBean, itemOut1);

            // 库存商品
            String itemTaxIdOut1 = TaxItemConstanst.DEPOR_PRODUCT;

            TaxBean outTax = taxDAO.findByUnique(itemTaxIdOut1);

            if (outTax == null)
            {
                throw new MYException("数据错误,请确认操作");
            }

            // 科目拷贝
            FinanceHelper.copyTax(outTax, itemOut1);

            // 成本价*数量
            double outMoney = baseBean.getAmount() * baseBean.getCostPrice();

            itemOut1.setInmoney(0);

            itemOut1.setOutmoney(FinanceHelper.doubleToLong(outMoney));

            itemOut1.setDescription(itemOut1.getName());

            // 辅助核算 产品/仓库
            itemOut1.setProductId(baseBean.getProductId());
            itemOut1.setProductAmountOut(baseBean.getAmount());
            itemOut1.setDepotId(outBean.getLocation());

            itemList.add(itemOut1);
        }
    }

    /**
     * createDuiChongItem2
     * 
     * @param user
     * @param outBean
     * @param financeBean
     * @param itemList
     * @throws MYException
     */
    private void createDuiChongItem2(User user, OutBean outBean, FinanceBean financeBean,
                                     List<FinanceItemBean> itemList)
        throws MYException
    {
        List<BaseBean> baseList = outBean.getBaseList();

        String name = "领样转销售对冲单据:" + outBean.getFullId() + '.';

        String pare1 = commonDAO.getSquenceString();

        for (BaseBean baseBean : baseList)
        {
            // 借:库存商品 贷:应付账款-供应商
            FinanceItemBean itemInEach = new FinanceItemBean();

            itemInEach.setPareId(pare1);

            itemInEach.setName("主营业务成本:" + name + ".产品:" + baseBean.getProductName());

            itemInEach.setForward(TaxConstanst.TAX_FORWARD_IN);

            FinanceHelper.copyFinanceItem(financeBean, itemInEach);

            // 主营业务成本(部门/职员)
            String itemInTaxId = TaxItemConstanst.MAIN_COST;

            TaxBean itemInTax = taxDAO.findByUnique(itemInTaxId);

            if (itemInTax == null)
            {
                throw new MYException("数据错误,请确认操作");
            }

            // 科目拷贝
            FinanceHelper.copyTax(itemInTax, itemInEach);

            double money = baseBean.getAmount() * baseBean.getCostPrice();

            itemInEach.setInmoney( -FinanceHelper.doubleToLong(money));

            itemInEach.setOutmoney(0);

            itemInEach.setDescription(itemInEach.getName());

            // 辅助核算 职员/部门
            copyDepartment(outBean, itemInEach);
            itemInEach.setStafferId(outBean.getStafferId());
            itemInEach.setProductId(baseBean.getProductId());
            itemInEach.setDepotId(baseBean.getLocationId());
            itemInEach.setProductAmountIn( -baseBean.getAmount());

            itemList.add(itemInEach);
        }

        // 库存商品（成本价*数量）
        for (BaseBean baseBean : baseList)
        {
            FinanceItemBean itemOut1 = new FinanceItemBean();

            itemOut1.setPareId(pare1);

            itemOut1.setName("库存商品(" + baseBean.getProductName() + "):" + name);

            itemOut1.setForward(TaxConstanst.TAX_FORWARD_OUT);

            FinanceHelper.copyFinanceItem(financeBean, itemOut1);

            // 库存商品
            String itemTaxIdOut1 = TaxItemConstanst.DEPOR_PRODUCT;

            TaxBean outTax = taxDAO.findByUnique(itemTaxIdOut1);

            if (outTax == null)
            {
                throw new MYException("数据错误,请确认操作");
            }

            // 科目拷贝
            FinanceHelper.copyTax(outTax, itemOut1);

            // 成本价*数量
            double outMoney = baseBean.getAmount() * baseBean.getCostPrice();

            itemOut1.setInmoney(0);

            itemOut1.setOutmoney( -FinanceHelper.doubleToLong(outMoney));

            itemOut1.setDescription(itemOut1.getName());

            // 辅助核算 产品/仓库
            itemOut1.setProductId(baseBean.getProductId());
            itemOut1.setProductAmountOut( -baseBean.getAmount());
            itemOut1.setDepotId(outBean.getLocation());

            itemList.add(itemOut1);
        }
    }

    /**
     * createDuiChongItem3(借:主营业务成本 贷:库存商品)
     * 
     * @param user
     * @param outBean
     * @param financeBean
     * @param itemList
     * @throws MYException
     */
    private void createDuiChongItem3(User user, OutBean outBean, FinanceBean financeBean,
                                     List<FinanceItemBean> itemList)
        throws MYException
    {
        List<BaseBean> baseList = outBean.getBaseList();

        String name = "B单入库单据对冲单据:" + outBean.getFullId() + '.';

        String pare1 = commonDAO.getSquenceString();

        for (BaseBean baseBean : baseList)
        {
            // 借:主营业务成本 贷:库存商品
            FinanceItemBean itemInEach = new FinanceItemBean();

            itemInEach.setPareId(pare1);

            itemInEach.setName("主营业务成本:" + name + ".产品:" + baseBean.getProductName());

            itemInEach.setForward(TaxConstanst.TAX_FORWARD_IN);

            FinanceHelper.copyFinanceItem(financeBean, itemInEach);

            // 主营业务成本(部门/职员)
            String itemInTaxId = TaxItemConstanst.MAIN_COST;

            TaxBean itemInTax = taxDAO.findByUnique(itemInTaxId);

            if (itemInTax == null)
            {
                throw new MYException("数据错误,请确认操作");
            }

            // 科目拷贝
            FinanceHelper.copyTax(itemInTax, itemInEach);

            double money = baseBean.getAmount() * baseBean.getCostPrice();

            itemInEach.setInmoney( -FinanceHelper.doubleToLong(money));

            itemInEach.setOutmoney(0);

            itemInEach.setDescription(itemInEach.getName());

            // 辅助核算 职员/部门
            copyDepartment(outBean, itemInEach);
            itemInEach.setStafferId(outBean.getStafferId());
            itemInEach.setProductId(baseBean.getProductId());
            itemInEach.setDepotId(baseBean.getLocationId());
            itemInEach.setProductAmountIn( -baseBean.getAmount());

            itemList.add(itemInEach);
        }

        // 库存商品（成本价*数量）
        for (BaseBean baseBean : baseList)
        {
            FinanceItemBean itemOut1 = new FinanceItemBean();

            itemOut1.setPareId(pare1);

            itemOut1.setName("库存商品(" + baseBean.getProductName() + "):" + name);

            itemOut1.setForward(TaxConstanst.TAX_FORWARD_OUT);

            FinanceHelper.copyFinanceItem(financeBean, itemOut1);

            // 库存商品
            String itemTaxIdOut1 = TaxItemConstanst.DEPOR_PRODUCT;

            TaxBean outTax = taxDAO.findByUnique(itemTaxIdOut1);

            if (outTax == null)
            {
                throw new MYException("数据错误,请确认操作");
            }

            // 科目拷贝
            FinanceHelper.copyTax(outTax, itemOut1);

            // 成本价*数量
            double outMoney = baseBean.getAmount() * baseBean.getCostPrice();

            itemOut1.setInmoney(0);

            itemOut1.setOutmoney( -FinanceHelper.doubleToLong(outMoney));

            itemOut1.setDescription(itemOut1.getName());

            // 辅助核算 产品/仓库
            itemOut1.setProductId(baseBean.getProductId());
            itemOut1.setProductAmountOut( -baseBean.getAmount());
            itemOut1.setDepotId(outBean.getLocation());

            itemList.add(itemOut1);
        }
    }

    /**
     * 主营业务成本（负数）/库存商品（成本价*数量）
     * 
     * @param user
     * @param outBean
     * @param financeBean
     * @param itemList
     * @throws MYException
     */
    private void createBuySwatchItem2(User user, OutBean outBean, FinanceBean financeBean,
                                      List<FinanceItemBean> itemList)
        throws MYException
    {
        List<BaseBean> baseList = outBean.getBaseList();

        String name = "个人领样退库:" + outBean.getFullId() + '.';

        String pare1 = commonDAO.getSquenceString();

        for (BaseBean baseBean : baseList)
        {
            FinanceItemBean itemInEach = new FinanceItemBean();

            itemInEach.setPareId(pare1);

            itemInEach.setName("主营业务成本:" + name + ".产品:" + baseBean.getProductName());

            itemInEach.setForward(TaxConstanst.TAX_FORWARD_IN);

            FinanceHelper.copyFinanceItem(financeBean, itemInEach);

            // 主营业务成本(部门/职员)
            String itemInTaxId = TaxItemConstanst.MAIN_COST;

            TaxBean itemInTax = taxDAO.findByUnique(itemInTaxId);

            if (itemInTax == null)
            {
                throw new MYException("数据错误,请确认操作");
            }

            // 科目拷贝
            FinanceHelper.copyTax(itemInTax, itemInEach);

            double money = -baseBean.getAmount() * baseBean.getCostPrice();

            itemInEach.setInmoney(FinanceHelper.doubleToLong(money));

            itemInEach.setOutmoney(0);

            itemInEach.setDescription(itemInEach.getName());

            // 辅助核算 职员/部门
            copyDepartment(outBean, itemInEach);
            itemInEach.setStafferId(outBean.getStafferId());
            itemInEach.setProductId(baseBean.getProductId());
            itemInEach.setDepotId(baseBean.getLocationId());
            itemInEach.setProductAmountIn( -baseBean.getAmount());

            itemList.add(itemInEach);
        }

        // 库存商品（成本价*数量）
        for (BaseBean baseBean : baseList)
        {
            FinanceItemBean itemOut1 = new FinanceItemBean();

            itemOut1.setPareId(pare1);

            itemOut1.setName("库存商品(" + baseBean.getProductName() + "):" + name);

            itemOut1.setForward(TaxConstanst.TAX_FORWARD_OUT);

            FinanceHelper.copyFinanceItem(financeBean, itemOut1);

            // 库存商品
            String itemTaxIdOut1 = TaxItemConstanst.DEPOR_PRODUCT;

            TaxBean outTax = taxDAO.findByUnique(itemTaxIdOut1);

            if (outTax == null)
            {
                throw new MYException("数据错误,请确认操作");
            }

            // 科目拷贝
            FinanceHelper.copyTax(outTax, itemOut1);

            // 成本价*数量
            double outMoney = -baseBean.getAmount() * baseBean.getCostPrice();

            itemOut1.setInmoney(0);

            itemOut1.setOutmoney(FinanceHelper.doubleToLong(outMoney));

            itemOut1.setDescription(itemOut1.getName());

            // 辅助核算 产品/仓库
            itemOut1.setProductId(baseBean.getProductId());
            itemOut1.setProductAmountOut( -baseBean.getAmount());
            itemOut1.setDepotId(outBean.getLocation());

            itemList.add(itemOut1);
        }
    }

    private void createBuyCommonBackItem2(User user, OutBean outBean, FinanceBean financeBean,
                                          List<FinanceItemBean> itemList)
        throws MYException
    {
        List<BaseBean> baseList = outBean.getBaseList();

        String name = "入库-销售退库:" + outBean.getFullId() + '.';

        String pare1 = commonDAO.getSquenceString();

        for (BaseBean baseBean : baseList)
        {
            // 借:库存商品 贷:应付账款-供应商
            FinanceItemBean itemInEach = new FinanceItemBean();

            itemInEach.setPareId(pare1);

            itemInEach.setName("主营业务成本:" + name + ".产品:" + baseBean.getProductName());

            itemInEach.setForward(TaxConstanst.TAX_FORWARD_IN);

            FinanceHelper.copyFinanceItem(financeBean, itemInEach);

            // 主营业务成本(部门/职员/产品/仓区)
            String itemInTaxId = TaxItemConstanst.MAIN_COST;

            TaxBean itemInTax = taxDAO.findByUnique(itemInTaxId);

            if (itemInTax == null)
            {
                throw new MYException("数据错误,请确认操作");
            }

            // 科目拷贝
            FinanceHelper.copyTax(itemInTax, itemInEach);

            double money = -baseBean.getAmount() * baseBean.getCostPrice();

            itemInEach.setInmoney(FinanceHelper.doubleToLong(money));

            itemInEach.setOutmoney(0);

            itemInEach.setDescription(itemInEach.getName());

            // 辅助核算 职员/部门/产品/仓库
            copyDepartment(outBean, itemInEach);
            itemInEach.setStafferId(outBean.getStafferId());
            itemInEach.setProductId(baseBean.getProductId());
            itemInEach.setDepotId(baseBean.getLocationId());
            itemInEach.setProductAmountIn( -baseBean.getAmount());

            itemList.add(itemInEach);
        }

        // 库存商品（成本价*数量）
        for (BaseBean baseBean : baseList)
        {
            FinanceItemBean itemOut1 = new FinanceItemBean();

            itemOut1.setPareId(pare1);

            itemOut1.setName("库存商品(" + baseBean.getProductName() + "):" + name);

            itemOut1.setForward(TaxConstanst.TAX_FORWARD_OUT);

            FinanceHelper.copyFinanceItem(financeBean, itemOut1);

            // 库存商品
            String itemTaxIdOut1 = TaxItemConstanst.DEPOR_PRODUCT;

            TaxBean outTax = taxDAO.findByUnique(itemTaxIdOut1);

            if (outTax == null)
            {
                throw new MYException("数据错误,请确认操作");
            }

            // 科目拷贝
            FinanceHelper.copyTax(outTax, itemOut1);

            // 成本价*数量
            double outMoney = -baseBean.getAmount() * baseBean.getCostPrice();

            itemOut1.setInmoney(0);

            itemOut1.setOutmoney(FinanceHelper.doubleToLong(outMoney));

            itemOut1.setDescription(itemOut1.getName());

            // 辅助核算 产品/仓库
            itemOut1.setProductId(baseBean.getProductId());
            itemOut1.setProductAmountOut( -baseBean.getAmount());
            itemOut1.setDepotId(outBean.getLocation());

            itemList.add(itemOut1);
        }
    }

    /**
     * 营业外支出/库存商品
     * 
     * @param user
     * @param outBean
     * @param financeBean
     * @param itemList
     * @throws MYException
     */
    private void createBuyDrop(User user, OutBean outBean, FinanceBean financeBean,
                               List<FinanceItemBean> itemList)
        throws MYException
    {

        String name = "入库单-报废/系统纠正:" + outBean.getFullId() + '.';

        // 营业外支出/库存商品
        FinanceItemBean itemIn = new FinanceItemBean();

        String pare1 = commonDAO.getSquenceString();

        itemIn.setPareId(pare1);

        itemIn.setName("营业外支出:" + name);

        itemIn.setForward(TaxConstanst.TAX_FORWARD_IN);

        FinanceHelper.copyFinanceItem(financeBean, itemIn);

        // 营业外支出
        String itemInTaxId = TaxItemConstanst.OTHER_PAY;

        TaxBean itemInTax = taxDAO.findByUnique(itemInTaxId);

        if (itemInTax == null)
        {
            throw new MYException("数据错误,请确认操作");
        }

        // 科目拷贝
        FinanceHelper.copyTax(itemInTax, itemIn);

        // 因为入库单是特性决定
        double money = -getOutCost(outBean);

        itemIn.setInmoney(FinanceHelper.doubleToLong(money));

        itemIn.setOutmoney(0);

        itemIn.setDescription(itemIn.getName());

        // 辅助核算NA
        itemList.add(itemIn);

        List<BaseBean> baseList = outBean.getBaseList();

        // 库存商品（成本价*数量）
        for (BaseBean baseBean : baseList)
        {
            FinanceItemBean itemOut1 = new FinanceItemBean();

            itemOut1.setPareId(pare1);

            itemOut1.setName("库存商品(" + baseBean.getProductName() + "):" + name);

            itemOut1.setForward(TaxConstanst.TAX_FORWARD_OUT);

            FinanceHelper.copyFinanceItem(financeBean, itemOut1);

            // 库存商品
            String itemTaxIdOut1 = TaxItemConstanst.DEPOR_PRODUCT;

            TaxBean outTax = taxDAO.findByUnique(itemTaxIdOut1);

            if (outTax == null)
            {
                throw new MYException("数据错误,请确认操作");
            }

            // 科目拷贝
            FinanceHelper.copyTax(outTax, itemOut1);

            double outMoney = -baseBean.getAmount() * baseBean.getCostPrice();

            itemOut1.setInmoney(0);

            itemOut1.setOutmoney(FinanceHelper.doubleToLong(outMoney));

            itemOut1.setDescription(itemOut1.getName());

            // 辅助核算 产品/仓库
            itemOut1.setProductId(baseBean.getProductId());
            itemOut1.setProductAmountOut( -baseBean.getAmount());
            itemOut1.setDepotId(outBean.getLocation());

            itemList.add(itemOut1);
        }
    }

    private void createBuyDrop1(User user, OutBean outBean, FinanceBean financeBean,
            List<FinanceItemBean> itemList)
    throws MYException
    {

        String name = "入库单-报废:" + outBean.getFullId() + '.';
        
        String pare1 = commonDAO.getSquenceString();
        
        String dropType = getDropType(outBean);
        
        String itemInTaxId = getDropTaxId(outBean);
        
        String [] taxId = null;
        
        if (itemInTaxId.equals(TaxItemConstanst.DROP3))
        {
            taxId = new String[2];
            
            taxId[0] = TaxItemConstanst.DROP0;
            taxId[1] = TaxItemConstanst.DROP2;
        }else
        {
            taxId = new String[1];
            
            taxId[0] = itemInTaxId;
        }
        
        long taxInmoney = 0L;
        
        for (int i = 0; i < taxId.length; i++)
        {
            // 产品报废损失/库存商品
            FinanceItemBean itemIn = new FinanceItemBean();

            itemIn.setPareId(pare1);

            itemIn.setName(dropType + name);

            itemIn.setForward(TaxConstanst.TAX_FORWARD_IN);

            FinanceHelper.copyFinanceItem(financeBean, itemIn);

            TaxBean itemInTax = taxDAO.findByUnique(taxId[i]);

            if (itemInTax == null)
            {
                throw new MYException("数据错误,请确认操作");
            }

            // 科目拷贝
            FinanceHelper.copyTax(itemInTax, itemIn);

            // 因为入库单是特性决定
            double money = -getOutCost(outBean);

            if (taxId.length > 1 && i == taxId.length - 1)
            {
                itemIn.setInmoney(FinanceHelper.doubleToLong(money) - taxInmoney);
            }
            else
            {
                itemIn.setInmoney(FinanceHelper.doubleToLong(money / taxId.length));
                
                taxInmoney += itemIn.getInmoney();
            }                        

            itemIn.setOutmoney(0);

            itemIn.setDescription(itemIn.getName());

            StafferBean sbean = stafferDAO.find(outBean.getReserve9().trim());
            // 辅助核算
            if (taxId[i].equals(TaxItemConstanst.DROP0) || taxId[i].equals(TaxItemConstanst.DROP1))
            {
                // 人员所在部门的负责人
                itemIn.setDepartmentId(sbean.getPrincipalshipId());
                
                if (taxId.length == 2)
                {
                    List<StafferBean> stafferList = setDrop1StafferId(itemIn);
                    
                    if (ListTools.isEmptyOrNull(stafferList))
                    {
                        throw new MYException("人员[%s]所在的部门负责人不存在，请确认",sbean.getName());
                    }
                    
                    for (Iterator<StafferBean> iterator = stafferList.iterator(); iterator.hasNext();)
                    {
                        StafferBean stafferBean = iterator.next();
                        
                        if (stafferBean.getPostId().equals(PublicConstant.POST_FEE_DEPENDER))
                        {
                            itemIn.setStafferId(stafferBean.getId());

                            iterator.remove();
                        }
                    }
                    
                    if (stafferList.size() > 1)
                    {
                        throw new MYException("人员[%s]所在的部门负责人存在多个，请确认",sbean.getName());
                    }
                    
                    if (StringTools.isNullOrNone(itemIn.getStafferId()))
                    {
                        itemIn.setStafferId(stafferList.get(0).getId());
                    }
                    
                }else
                {
                    itemIn.setStafferId(outBean.getReserve9().trim());
                }                
            }else
            {
                itemIn.setDepartmentId(sbean.getPrincipalshipId());
                itemIn.setStafferId(outBean.getReserve9().trim());
                itemIn.setUnitId("2237"); //中国永银
                itemIn.setUnitType(TaxConstanst.UNIT_TYPE_CUSTOMER);
            }
            
            itemList.add(itemIn);
        }

        List<BaseBean> baseList = outBean.getBaseList();

        // 库存商品（成本价*数量）
        for (BaseBean baseBean : baseList)
        {
            FinanceItemBean itemOut1 = new FinanceItemBean();

            itemOut1.setPareId(pare1);

            itemOut1.setName("库存商品(" + baseBean.getProductName() + "):" + name);

            itemOut1.setForward(TaxConstanst.TAX_FORWARD_OUT);

            FinanceHelper.copyFinanceItem(financeBean, itemOut1);

            // 库存商品
            String itemTaxIdOut1 = TaxItemConstanst.DEPOR_PRODUCT;

            TaxBean outTax = taxDAO.findByUnique(itemTaxIdOut1);

            if (outTax == null)
            {
                throw new MYException("数据错误,请确认操作");
            }

            // 科目拷贝
            FinanceHelper.copyTax(outTax, itemOut1);

            double outMoney = -baseBean.getAmount() * baseBean.getCostPrice();

            itemOut1.setInmoney(0);

            itemOut1.setOutmoney(FinanceHelper.doubleToLong(outMoney));

            itemOut1.setDescription(itemOut1.getName());

            // 辅助核算 产品/仓库
            itemOut1.setProductId(baseBean.getProductId());
            itemOut1.setProductAmountOut( -baseBean.getAmount());
            itemOut1.setDepotId(outBean.getLocation());

            itemList.add(itemOut1);
        }
    }

    private List<StafferBean> setDrop1StafferId(FinanceItemBean itemIn) 
    {
        ConditionParse condition = new ConditionParse();
        
        condition.addWhereStr();
        
        condition.addCondition(" and StafferBean.postid in ('17','19')");
        
        condition.addCondition("StafferBean.principalshipid", "=", itemIn.getDepartmentId());
        
        List<StafferBean> stafferList = stafferDAO.queryEntityBeansByCondition(condition);
        
        return stafferList;
    }
    
    /**
     * 库存商品/应付账款-供应商（负数）
     * 
     * @param user
     * @param outBean
     * @param financeBean
     * @param itemList
     * @throws MYException
     */
    private void createBuyStockBack(User user, OutBean outBean, FinanceBean financeBean,
                                    List<FinanceItemBean> itemList)
        throws MYException
    {

        String name = "入库单-采购退货:" + outBean.getFullId() + '.';

        String pare1 = commonDAO.getSquenceString();

        List<BaseBean> baseList = outBean.getBaseList();

        // 库存商品（成本价*数量）
        for (BaseBean baseBean : baseList)
        {
            FinanceItemBean itemInEach = new FinanceItemBean();

            itemInEach.setPareId(pare1);

            itemInEach.setName("库存商品(" + baseBean.getProductName() + "):" + name);

            itemInEach.setForward(TaxConstanst.TAX_FORWARD_IN);

            FinanceHelper.copyFinanceItem(financeBean, itemInEach);

            // 库存商品
            String itemTaxIdIn = TaxItemConstanst.DEPOR_PRODUCT;

            TaxBean outTax = taxDAO.findByUnique(itemTaxIdIn);

            if (outTax == null)
            {
                throw new MYException("数据错误,请确认操作");
            }

            // 科目拷贝
            FinanceHelper.copyTax(outTax, itemInEach);

            // 库存商品减少(baseBean.getAmount()是负数)
            double outMoney = baseBean.getAmount() * baseBean.getCostPrice();

            itemInEach.setInmoney(FinanceHelper.doubleToLong(outMoney));

            itemInEach.setOutmoney(0);

            itemInEach.setDescription(itemInEach.getName());

            // 辅助核算 产品/仓库
            itemInEach.setProductId(baseBean.getProductId());
            itemInEach.setProductAmountIn(baseBean.getAmount());
            itemInEach.setDepotId(outBean.getLocation());

            itemList.add(itemInEach);
        }

        FinanceItemBean itemOut1 = new FinanceItemBean();

        itemOut1.setPareId(pare1);

        itemOut1.setName("应付账款:" + name);

        itemOut1.setForward(TaxConstanst.TAX_FORWARD_OUT);

        FinanceHelper.copyFinanceItem(financeBean, itemOut1);

        // 应付账款
        String itemTaxIdOut1 = TaxItemConstanst.PAY_PRODUCT;

        TaxBean outTax = taxDAO.findByUnique(itemTaxIdOut1);

        if (outTax == null)
        {
            throw new MYException("数据错误,请确认操作");
        }

        // 科目拷贝
        FinanceHelper.copyTax(outTax, itemOut1);

        // 应付账款-供应商（负数）
        double outMoney = getOutCost(outBean);

        itemOut1.setInmoney(0);

        itemOut1.setOutmoney(FinanceHelper.doubleToLong(outMoney));

        itemOut1.setDescription(itemOut1.getName());

        // 辅助核算 单位
        itemOut1.setUnitId(outBean.getCustomerId());
        itemOut1.setUnitType(TaxConstanst.UNIT_TYPE_PROVIDE);

        itemList.add(itemOut1);
    }

    /**
     * 库存商品/库存商品-中转
     * 
     * @param user
     * @param outBean
     * @param financeBean
     * @param itemList
     * @throws MYException
     */
    private void createBuyOther(User user, OutBean outBean, FinanceBean financeBean,
                                List<FinanceItemBean> itemList)
        throws MYException
    {
        String name = "入库单-库存中转:" + outBean.getFullId() + '.';

        String pare1 = commonDAO.getSquenceString();

        List<BaseBean> baseList = outBean.getBaseList();

        // 库存商品（成本价*数量）
        for (BaseBean baseBean : baseList)
        {
            FinanceItemBean itemInEach = new FinanceItemBean();

            itemInEach.setPareId(pare1);

            itemInEach.setName("库存商品(" + baseBean.getProductName() + "):" + name);

            itemInEach.setForward(TaxConstanst.TAX_FORWARD_IN);

            FinanceHelper.copyFinanceItem(financeBean, itemInEach);

            // 库存商品
            String itemTaxIdIn = TaxItemConstanst.DEPOR_PRODUCT;

            TaxBean outTax = taxDAO.findByUnique(itemTaxIdIn);

            if (outTax == null)
            {
                throw new MYException("数据错误,请确认操作");
            }

            // 科目拷贝
            FinanceHelper.copyTax(outTax, itemInEach);

            // 库存商品减少(baseBean.getAmount()是负数)
            double outMoney = baseBean.getAmount() * baseBean.getCostPrice();

            itemInEach.setInmoney(FinanceHelper.doubleToLong(outMoney));

            itemInEach.setOutmoney(0);

            itemInEach.setDescription(itemInEach.getName());

            // 辅助核算 产品/仓库
            itemInEach.setProductId(baseBean.getProductId());
            itemInEach.setProductAmountIn(baseBean.getAmount());
            itemInEach.setDepotId(outBean.getLocation());

            itemList.add(itemInEach);
        }

        // 营业外收入
        for (BaseBean baseBean : baseList)
        {
            FinanceItemBean itemOutEach = new FinanceItemBean();

            itemOutEach.setPareId(pare1);

            itemOutEach.setName("库存商品-中转(" + baseBean.getProductName() + "):" + name);

            itemOutEach.setForward(TaxConstanst.TAX_FORWARD_OUT);

            FinanceHelper.copyFinanceItem(financeBean, itemOutEach);

            // 营业外收入
            String itemTaxIdIn = TaxItemConstanst.EXT_RECEIVE;

            TaxBean outTax = taxDAO.findByUnique(itemTaxIdIn);

            if (outTax == null)
            {
                throw new MYException("数据错误,请确认操作");
            }

            // 科目拷贝
            FinanceHelper.copyTax(outTax, itemOutEach);

            // 库存商品
            double outMoney = baseBean.getAmount() * baseBean.getCostPrice();

            itemOutEach.setInmoney(0);
            itemOutEach.setOutmoney(FinanceHelper.doubleToLong(outMoney));

            itemOutEach.setDescription(itemOutEach.getName());

            // 辅助核算 产品/仓库(其实可以没有核算型)
            // itemOutEach.setProductId(baseBean.getProductId());
            // itemOutEach.setProductAmountOut(baseBean.getAmount());
            // itemOutEach.setDepotId(outBean.getLocation());

            itemList.add(itemOutEach);
        }
    }

    /**
     * 库存商品/库存商品
     * 
     * @param user
     * @param outBean
     *            接受的bean(数量是整数)
     * @param financeBean
     * @param itemList
     * @throws MYException
     */
    private void createOutCommonItemMoveIn(User user, OutBean outBean, FinanceBean financeBean,
                                           List<FinanceItemBean> itemList)
        throws MYException
    {

        String name = "入库单-调拨:" + outBean.getFullId() + '.';

        String pare = commonDAO.getSquenceString();

        // 处理调出变动库存
        String moveOutFullId = outBean.getRefOutFullId();

        OutBean moveOut = outDAO.find(moveOutFullId);

        if (moveOut == null)
        {
            throw new MYException("数据错误,请确认操作");
        }

        final List<BaseBean> baseList = baseDAO.queryEntityBeansByFK(outBean.getFullId());

        // 库存商品（成本价*数量）
        for (BaseBean baseBean : baseList)
        {
            FinanceItemBean itemInEach = new FinanceItemBean();

            itemInEach.setPareId(pare);

            itemInEach.setName("库存商品调出(" + baseBean.getProductName() + "):" + name);

            itemInEach.setForward(TaxConstanst.TAX_FORWARD_IN);

            FinanceHelper.copyFinanceItem(financeBean, itemInEach);

            // 库存商品
            String itemTaxIdOut1 = TaxItemConstanst.DEPOR_PRODUCT;

            TaxBean outTax = taxDAO.findByUnique(itemTaxIdOut1);

            if (outTax == null)
            {
                throw new MYException("数据错误,请确认操作");
            }

            // 科目拷贝
            FinanceHelper.copyTax(outTax, itemInEach);

            double outMoney = baseBean.getAmount() * baseBean.getCostPrice();

            itemInEach.setInmoney(FinanceHelper.doubleToLong(outMoney));

            itemInEach.setOutmoney(0);

            itemInEach.setDescription(itemInEach.getName());

            // 辅助核算 产品/仓库
            itemInEach.setProductId(baseBean.getProductId());
            itemInEach.setProductAmountIn(baseBean.getAmount());

            // 调入的仓库
            itemInEach.setDepotId(outBean.getLocation());

            itemList.add(itemInEach);
        }

        // 库存商品（成本价*数量）
        for (BaseBean baseBean : baseList)
        {
            FinanceItemBean itemOutEach = new FinanceItemBean();

            itemOutEach.setPareId(pare);

            itemOutEach.setName("库存商品调入(" + baseBean.getProductName() + "):" + name);

            itemOutEach.setForward(TaxConstanst.TAX_FORWARD_OUT);

            FinanceHelper.copyFinanceItem(financeBean, itemOutEach);

            // 库存商品
            String itemTaxIdOut1 = TaxItemConstanst.DEPOR_PRODUCT;

            TaxBean outTax = taxDAO.findByUnique(itemTaxIdOut1);

            if (outTax == null)
            {
                throw new MYException("数据错误,请确认操作");
            }

            // 科目拷贝
            FinanceHelper.copyTax(outTax, itemOutEach);

            double outMoney = baseBean.getAmount() * baseBean.getCostPrice();

            itemOutEach.setInmoney(0);

            itemOutEach.setOutmoney(FinanceHelper.doubleToLong(outMoney));

            itemOutEach.setDescription(itemOutEach.getName());

            // 辅助核算 产品/仓库
            itemOutEach.setProductId(baseBean.getProductId());
            itemOutEach.setProductAmountOut(baseBean.getAmount());

            // 调出的仓库
            itemOutEach.setDepotId(moveOut.getLocation());

            itemList.add(itemOutEach);
        }
    }

    /**
     * 获取成本
     * 
     * @param outBean
     * @return
     */
    private double getOutCost(OutBean outBean)
    {
        double total = 0.0d;

        List<BaseBean> baseList = outBean.getBaseList();

        for (BaseBean baseBean : baseList)
        {
            total += baseBean.getAmount() * baseBean.getCostPrice();
        }

        return total;
    }

    /**
     * 委托成本
     * 
     * @param bean
     * @param outBean
     * @return
     * @throws MYException
     */
    protected double getOutBalanceCost(OutBalanceBean bean, OutBean outBean)
        throws MYException
    {
        double total = 0.0d;

        List<BaseBalanceBean> baseBalanceList = bean.getBaseBalanceList();

        List<BaseBean> baseList = outBean.getBaseList();

        for (BaseBalanceBean baseBalanceBean : baseBalanceList)
        {
            BaseBean baseBean = getBaseBean(baseBalanceBean, baseList);

            if (baseBean == null)
            {
                throw new MYException("没有找到委托原单据产品,请确认操作");
            }

            total += baseBalanceBean.getAmount() * baseBean.getCostPrice();
        }

        return total;
    }

    private BaseBean getBaseBean(BaseBalanceBean baseBalanceBean, List<BaseBean> baseList)
    {
        for (BaseBean baseBean : baseList)
        {
            if (baseBalanceBean.getBaseId().equals(baseBean.getId()))
            {
                return baseBean;
            }
        }

        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.china.center.oa.sail.listener.OutListener#onDelete(com.center.china.osgi.publics.User,
     *      com.china.center.oa.sail.bean.OutBean)
     */
    public void onDelete(User user, OutBean bean)
        throws MYException
    {
        // 

    }

    /*
     * (non-Javadoc)
     * 
     * @see com.china.center.oa.sail.listener.OutListener#onHadPay(com.center.china.osgi.publics.User,
     *      com.china.center.oa.sail.bean.OutBean)
     */
    public ResultBean onHadPay(User user, OutBean bean)
    {
        // 
        return null;
    }

    /**
     * 委托销售-结算单/委托销售-结算退货单
     */
    public void onOutBalancePass(User user, OutBalanceBean bean)
        throws MYException
    {
        OutBean outBean = outDAO.find(bean.getOutId());

        if (outBean == null)
        {
            throw new MYException("数据错误,请确认操作");
        }

        List<BaseBean> baseList = baseDAO.queryEntityBeansByFK(bean.getOutId());

        outBean.setBaseList(baseList);

        // 结算单
        if (bean.getType() == OutConstant.OUTBALANCE_TYPE_SAIL)
        {
            processOutBalance(user, bean, outBean);
        }
        else if (bean.getType() == OutConstant.OUTBALANCE_TYPE_SAILBACK)
        {
            processOutBalanceBack(user, bean, outBean);
            
            processOutBalanceBackTag(user, bean, outBean);
        }
        else
        {
        	processOutBalanceBack2(user, bean, outBean);
        	
        	processOutBalanceBackTag(user, bean, outBean);
        }

    }

    private void processOutBalance(User user, OutBalanceBean bean, OutBean outBean)
        throws MYException
    {
        // 应收账款（销售金额，含税价）(1132) 主营业务收入：含税价
        // 主营业务成本(5401) 库存商品（成本价*数量）
        FinanceBean financeBean = new FinanceBean();

        String name = "委托销售-结算单:" + bean.getId() + '.';

        financeBean.setName(name);

        financeBean.setType(TaxConstanst.FINANCE_TYPE_MANAGER);

        financeBean.setCreateType(TaxConstanst.FINANCE_CREATETYPE_SAIL_CONSIGN_PAY);

        financeBean.setRefId(bean.getId());

        financeBean.setRefOut(bean.getOutId());

        financeBean.setDutyId(outBean.getDutyId());

        financeBean.setDescription(financeBean.getName());

        financeBean.setFinanceDate(TimeTools.now_short());

        financeBean.setLogTime(TimeTools.now());

        List<FinanceItemBean> itemList = new ArrayList<FinanceItemBean>();

        // 应收账款（销售金额，含税价）(1132)/主营业务收入：含税价
        createOutBalanceItem1(user, outBean, bean, financeBean, itemList);

        // 主营业务成本(5401)/委托代销商品（成本价*数量）
        createOutBalanceItem2(user, outBean, bean, financeBean, itemList);

        financeBean.setItemList(itemList);

        financeManager.addFinanceBeanWithoutTransactional(user, financeBean);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.china.center.oa.sail.listener.OutListener#onPass(com.center.china.osgi.publics.User,
     *      com.china.center.oa.sail.bean.OutBean)
     */
    public void onPass(User user, OutBean bean)
        throws MYException
    {
        // 

    }

    /*
     * (non-Javadoc)
     * 
     * @see com.china.center.oa.sail.listener.OutListener#onReject(com.center.china.osgi.publics.User,
     *      com.china.center.oa.sail.bean.OutBean)
     */
    public void onReject(User user, OutBean outBean)
        throws MYException
    {
        // 这里的预收转应收在BillListenerTaxGlueImpl里面实现,但是坏账的取消是在这里
        onCancleBadDebts(user, outBean);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.china.center.oa.sail.listener.OutListener#outNeedPayMoney(com.center.china.osgi.publics.User,
     *      java.lang.String)
     */
    public double outNeedPayMoney(User user, String fullId)
    {
        return 0;
    }

    public void onConfirmBadDebts(User user, OutBean bean)
        throws MYException
    {
        // 这里需要注意因为坏账是覆盖模式的(就是不管原先是多少坏账,本次是先取消所有坏账,然后生成坏账)
        // 所以先要查询是否存在此单据的坏账确认,然后本次的金额是总坏账金额-已经存在的
        mainFinanceInBadPay(user, bean);
    }

    /**
     * 其它应收-坏账(1133-16)/应收账款
     * 
     * @param user
     * @param outBean
     * @param bad
     * @throws MYException
     */
    private void mainFinanceInBadPay(User user, OutBean outBean)
        throws MYException
    {
        FinanceBean financeBean = new FinanceBean();

        String name = user.getStafferName() + "往来核对中确认坏账:" + outBean.getFullId() + '.';

        financeBean.setName(name);

        financeBean.setType(TaxConstanst.FINANCE_TYPE_MANAGER);

        financeBean.setCreateType(TaxConstanst.FINANCE_CREATETYPE_SAIL_BADMONEY);

        // 这里也是关联的回款单号
        financeBean.setRefId(outBean.getFullId());

        financeBean.setRefOut(outBean.getFullId());

        financeBean.setDutyId(outBean.getDutyId());

        financeBean.setCreaterId(user.getStafferId());

        financeBean.setDescription(financeBean.getName());

        financeBean.setFinanceDate(TimeTools.now_short());

        financeBean.setLogTime(TimeTools.now());

        List<FinanceItemBean> itemList = new ArrayList<FinanceItemBean>();

        // 其它应收-坏账(1133-16)/应收账款
        createAddItem5(user, outBean, financeBean, itemList);

        financeBean.setItemList(itemList);

        financeManager.addFinanceBeanWithoutTransactional(user, financeBean);
    }

    private void createAddItem5(User user, OutBean outBean, FinanceBean financeBean,
                                List<FinanceItemBean> itemList)
        throws MYException
    {
        // 申请人
        StafferBean staffer = stafferDAO.find(outBean.getStafferId());

        if (staffer == null)
        {
            throw new MYException("数据错误,请确认操作");
        }

        doCancleBad(user, outBean, financeBean, itemList);

        String name = user.getStafferName() + "往来核对中确认坏账:" + outBean.getFullId() + '.';

        // 借方
        FinanceItemBean itemIn = new FinanceItemBean();

        String pareId = commonDAO.getSquenceString();

        itemIn.setPareId(pareId);

        itemIn.setName("其它应收-坏账:" + name);

        itemIn.setForward(TaxConstanst.TAX_FORWARD_IN);

        FinanceHelper.copyFinanceItem(financeBean, itemIn);

        TaxBean inTax = taxDAO.findByUnique(TaxItemConstanst.BAD_REVEIVE_PRODUCT);

        if (inTax == null)
        {
            throw new MYException("数据错误,请确认操作");
        }

        // 科目拷贝
        FinanceHelper.copyTax(inTax, itemIn);

        // 当前发生额
        double inMoney = outBean.getBadDebts();

        itemIn.setInmoney(FinanceHelper.doubleToLong(inMoney));

        itemIn.setOutmoney(0);

        itemIn.setDescription(itemIn.getName());

        // 辅助核算 客户/职员/部门
        itemIn.setDepartmentId(staffer.getPrincipalshipId());
        itemIn.setStafferId(outBean.getStafferId());
        itemIn.setUnitId(outBean.getCustomerId());
        itemIn.setUnitType(TaxConstanst.UNIT_TYPE_CUSTOMER);

        itemList.add(itemIn);

        // 贷方
        FinanceItemBean itemOut = new FinanceItemBean();

        itemOut.setPareId(pareId);

        itemOut.setName("应收账款:" + name);

        itemOut.setForward(TaxConstanst.TAX_FORWARD_OUT);

        FinanceHelper.copyFinanceItem(financeBean, itemOut);

        // 应收账款(客户/职员/部门)
        TaxBean outTax = taxDAO.findByUnique(TaxItemConstanst.REVEIVE_PRODUCT);

        if (outTax == null)
        {
            throw new MYException("数据错误,请确认操作");
        }

        // 科目拷贝
        FinanceHelper.copyTax(outTax, itemOut);

        double outMoney = outBean.getBadDebts();

        itemOut.setInmoney(0);

        itemOut.setOutmoney(FinanceHelper.doubleToLong(outMoney));

        itemOut.setDescription(itemOut.getName());

        // 辅助核算 客户/职员/部门
        itemOut.setDepartmentId(staffer.getPrincipalshipId());
        itemOut.setStafferId(outBean.getStafferId());
        itemOut.setUnitId(outBean.getCustomerId());
        itemOut.setUnitType(TaxConstanst.UNIT_TYPE_CUSTOMER);

        itemList.add(itemOut);
    }

    /**
     * 先取消坏账
     * 
     * @param user
     * @param outBean
     * @param financeBean
     * @param itemList
     * @param bad
     * @throws MYException
     */
    private void createAddItem6(User user, OutBean outBean, FinanceBean financeBean,
                                List<FinanceItemBean> itemList, long bad)
        throws MYException
    {
        // 申请人
        StafferBean staffer = stafferDAO.find(outBean.getStafferId());

        if (staffer == null)
        {
            throw new MYException("数据错误,请确认操作");
        }

        String name = "驳回或坏账生成前先取消已经存在的坏账:" + outBean.getFullId() + '.';

        // 借方
        FinanceItemBean itemIn = new FinanceItemBean();

        String pareId = commonDAO.getSquenceString();

        itemIn.setPareId(pareId);

        itemIn.setName("其它应收-坏账:" + name);

        itemIn.setForward(TaxConstanst.TAX_FORWARD_IN);

        FinanceHelper.copyFinanceItem(financeBean, itemIn);

        TaxBean inTax = taxDAO.findByUnique(TaxItemConstanst.BAD_REVEIVE_PRODUCT);

        if (inTax == null)
        {
            throw new MYException("数据错误,请确认操作");
        }

        // 科目拷贝
        FinanceHelper.copyTax(inTax, itemIn);

        itemIn.setInmoney( -bad);

        itemIn.setOutmoney(0);

        itemIn.setDescription(itemIn.getName());

        // 辅助核算 客户/职员/部门
        itemIn.setDepartmentId(staffer.getPrincipalshipId());
        itemIn.setStafferId(outBean.getStafferId());
        itemIn.setUnitId(outBean.getCustomerId());
        itemIn.setUnitType(TaxConstanst.UNIT_TYPE_CUSTOMER);

        itemList.add(itemIn);

        // 贷方
        FinanceItemBean itemOut = new FinanceItemBean();

        itemOut.setPareId(pareId);

        itemOut.setName("应收账款:" + name);

        itemOut.setForward(TaxConstanst.TAX_FORWARD_OUT);

        FinanceHelper.copyFinanceItem(financeBean, itemOut);

        // 应收账款(客户/职员/部门)
        TaxBean outTax = taxDAO.findByUnique(TaxItemConstanst.REVEIVE_PRODUCT);

        if (outTax == null)
        {
            throw new MYException("数据错误,请确认操作");
        }

        // 科目拷贝
        FinanceHelper.copyTax(outTax, itemOut);

        itemOut.setInmoney(0);

        itemOut.setOutmoney( -bad);

        itemOut.setDescription(itemOut.getName());

        // 辅助核算 客户/职员/部门
        itemOut.setDepartmentId(staffer.getPrincipalshipId());
        itemOut.setStafferId(outBean.getStafferId());
        itemOut.setUnitId(outBean.getCustomerId());
        itemOut.setUnitType(TaxConstanst.UNIT_TYPE_CUSTOMER);

        itemList.add(itemOut);
    }

    private String getDropType(OutBean outBean)
    {
        if (outBean.getForceBuyType() == OutConstant.DROP_FLOW
                || outBean.getForceBuyType() == OutConstant.DROP_INDUSTRY
                || outBean.getForceBuyType() == OutConstant.DROP_HQ)
        {
            return "产品报废损失:";
        }
        
        if (outBean.getForceBuyType() == OutConstant.DROP_STAFFER                
                || outBean.getForceBuyType() == OutConstant.DROP_PART)
        {
            return "存货破损扣款:";
        }
        
        if (outBean.getForceBuyType() == OutConstant.DROP_COMMON)         
        {
            return "存货破损扣款/产品报废损失:";
        }
        
        return "";
    }
    
    private String getDropTaxId(OutBean outBean)
    {
        if (outBean.getForceBuyType() == OutConstant.DROP_FLOW
                || outBean.getForceBuyType() == OutConstant.DROP_HQ)
        {
            return TaxItemConstanst.DROP1;
        }
        
        if (outBean.getForceBuyType() == OutConstant.DROP_INDUSTRY)
        {
            return TaxItemConstanst.DROP0;
        }
        
        if (outBean.getForceBuyType() == OutConstant.DROP_STAFFER                
                || outBean.getForceBuyType() == OutConstant.DROP_PART)
        {
            return TaxItemConstanst.DROP2;
        }
        
        if (outBean.getForceBuyType() == OutConstant.DROP_COMMON)         
        {
            return TaxItemConstanst.DROP3;
        }
        
        return "";
    }
    
    /**
     * 空开空退时，已付款销售，新销售直接勾款，产生预收-应收凭证
     * 
     * {@inheritDoc}
     */
	public void onOutRepairePass(User user, OutBean oldOutBean, OutBean newOutBean) throws MYException
	{
		List<FinanceBean> financeBeanList = financeDAO.queryRefFinanceItemByOutId(oldOutBean.getFullId());
		
		double cha = 0d;
		
		// 新单金额比原单金额小
		if (newOutBean.getTotal() - oldOutBean.getTotal() < 0)
			cha = oldOutBean.getTotal() - newOutBean.getTotal();
		
		long newMoney = FinanceHelper.doubleToLong(newOutBean.getTotal());
		
		long checkMoney = 0L;
		
		Set<String> bills = new HashSet<String>();

		for (FinanceBean each : financeBeanList)
		{
			long inmoney = 0L;
			long outmoney = 0L;
			
			if (newMoney == 0.0d)
				break;
			
			if (each.getCreateType() == TaxConstanst.FINANCE_CREATETYPE_BILL_GETPAY
					|| each.getCreateType() == TaxConstanst.FINANCE_CREATETYPE_BILL_MAYTOREAL)
			{
				FinanceBean newFinanceBean = new FinanceBean();
				
				BeanUtil.copyProperties(newFinanceBean, each);
				
				newFinanceBean.setId(commonDAO.getSquenceString20(IDPrefixConstant.ID_FINANCE_PREFIX));
				
				newFinanceBean.setName(newFinanceBean.getId());
				
				newFinanceBean.setDutyId(newOutBean.getDutyId());
				
				newFinanceBean.setRefOut(newOutBean.getFullId());
				
				newFinanceBean.setCreaterId(user.getStafferId());

				inmoney = newFinanceBean.getInmoney();

				outmoney = newFinanceBean.getOutmoney();
				
				if (cha > 0)
				{
					if (newMoney >= newFinanceBean.getInmoney())
					{
						newMoney -= newFinanceBean.getInmoney();
					}
					else
					{
						inmoney = newMoney;
						
						outmoney = newMoney;
						
						newMoney = 0;
					}
				}
				
				newFinanceBean.setInmoney(inmoney);
				
				newFinanceBean.setOutmoney(outmoney);
				
				newFinanceBean.setFinanceDate(TimeTools.now_short());
				
				newFinanceBean.setLogTime(TimeTools.now());
				
				checkMoney += newFinanceBean.getInmoney();
				
				if (!bills.contains(newFinanceBean.getRefBill()))
					bills.add(newFinanceBean.getRefBill());
				
				financeDAO.saveEntityBean(newFinanceBean);
				
				List<FinanceItemBean> itemList = financeItemDAO.queryEntityBeansByFK(each.getId());
				
				String pareId = commonDAO.getSquenceString();
				
				for (FinanceItemBean eachItem : itemList)
				{
					FinanceItemBean newFinanceItemBean = new FinanceItemBean();
					
					BeanUtil.copyProperties(newFinanceItemBean, eachItem);
					
					newFinanceItemBean.setId(commonDAO.getSquenceString20());
					
					newFinanceItemBean.setPid(newFinanceBean.getId());
					
					newFinanceItemBean.setPareId(pareId);
					
					newFinanceItemBean.setName(newFinanceItemBean.getId());
					
					FinanceHelper.copyFinanceItem(newFinanceBean, newFinanceItemBean);
					
					if (newFinanceItemBean.getForward() == TaxConstanst.TAX_FORWARD_IN)
					{
						newFinanceItemBean.setInmoney(newFinanceBean.getInmoney());
					}
					else
					{
						newFinanceItemBean.setOutmoney(newFinanceBean.getInmoney());
					}
					
					financeItemDAO.saveEntityBean(newFinanceItemBean);
				}

			}
		}
		
		// 校验
		if (cha > 0)
		{
			if (FinanceHelper.doubleToLong(newOutBean.getTotal()) - checkMoney != 0)
			{
				throw new MYException("空开空退资金数据溢出0");
			}
		}
		else
		{
			if (FinanceHelper.doubleToLong(oldOutBean.getTotal()) - checkMoney != 0)
			{
				throw new MYException("空开空退资金数据溢出1");
			}
		}
		
		double inBillCheckMoney = 0.0d;
		
		List<InBillBean> inList = new ArrayList<InBillBean>();
		
		for (String each : bills)
		{
			InBillBean inBillBean = inBillDAO.find(each);
			
			if (null != inBillBean)
			{// 脏数据
//				if (inBillBean.getStatus() != FinanceConstant.INBILL_STATUS_NOREF)
//				{
//					throw new MYException("原销售单关联的收款单不是预收状态，请确认");
//				}
				
				inBillCheckMoney += inBillBean.getMoneys();
				
				inList.add(inBillBean);
			}
		}
		
		// 考虑精度问题，如果绝对值大于1做检查
		if (Math.abs(oldOutBean.getTotal() - inBillCheckMoney) >= 1)
		{
			throw new MYException("检查原销售单关联收款单金额溢出");
		}
		
		// 针对新单金额小于原单金额，生成预收
		StringBuffer refBuffer = splitInBill(user, oldOutBean, cha, inList);
		
		String refStr = refBuffer.toString();
		
		// 更新收款单状态及关联信息
		for (InBillBean each : inList)
		{
			if (refStr.indexOf(each.getId()) == -1)
			{
				each.setOutId(newOutBean.getFullId());
				
				each.setStatus(FinanceConstant.INBILL_STATUS_PAYMENTS);
				
				if (cha > 0)
				{
					if (each.getMoneys() >= cha)
					{
						each.setMoneys(each.getMoneys() - cha);
						
						cha = 0;
					}
					else
					{
						each.setMoneys(0);
						
						cha -= each.getMoneys();
					}
				}
				
				inBillDAO.updateEntityBean(each);
			}
		}
		
		if (cha != 0)
		{
			throw new MYException("空开空退转预收数据溢出");
		}
	}

	private StringBuffer splitInBill(User user, OutBean oldOutBean, double cha,
			List<InBillBean> inList) throws MYException
	{
		StringBuffer refBuffer = new StringBuffer();
		
		// cha 大于0，转预收
		if (cha > 0)
		{
			// 从大到小
            Collections.sort(inList, new Comparator<InBillBean>()
            {
                public int compare(InBillBean o1, InBillBean o2)
                {
                    return (int) (o2.getMoneys() - o1.getMoneys());
                }
            });

            double hasOut = cha;

            for (int i = 0; i < inList.size(); i++ )
            {
                InBillBean each = inList.get(i);

                // 最后的处理
                if (each.getMoneys() > hasOut)
                {
                    String newId = billManager.splitInBillBeanWithoutTransactional(user, each
                        .getId(), hasOut);

                    // 预收
                    InBillBean newInBill = inBillDAO.find(newId);

                    if (newInBill == null)
                    {
                        throw new MYException("数据错误,请确认操作");
                    }

                    // 取消关联了
                    newInBill.setOutId("");
                    newInBill.setOutBalanceId("");

                    // 未核对
                    newInBill.setCheckStatus(PublicConstant.CHECK_STATUS_INIT);
                    newInBill.setChecks("");

                    newInBill.setStatus(FinanceConstant.INBILL_STATUS_NOREF);
                    newInBill
                        .setDescription(newInBill.getDescription()
                                        + ";空开空退金额差转预收,销售单:"
                                        + "<a href='../sail/out.do?method=findOut&radioIndex=0&fow=99&outId="
                                        + oldOutBean.getFullId() + "'>" + oldOutBean.getFullId() + "</a>");

                    inBillDAO.updateEntityBean(newInBill);

                    break;
                }
                else
                {
                    // 逐步的转预收
                    hasOut = hasOut - each.getMoneys();
                    each.setOutId("");
                    each.setOutBalanceId("");
                    each.setStatus(FinanceConstant.INBILL_STATUS_NOREF);

                    // 未核对
                    each.setCheckStatus(PublicConstant.CHECK_STATUS_INIT);
                    each.setChecks("");

                    each.setDescription("空开空退金额差转预收:" + oldOutBean.getFullId());

                    inBillDAO.updateEntityBean(each);

                    refBuffer.append(each.getId()).append(',');

                    if (hasOut == 0.0d)
                    {
                        break;
                    }
                }
            }
		}
		
		return refBuffer;
	}
    
	/**
	 * processOutSwatchTag
	 * @param user
	 * @param outBean
	 * @throws MYException
	 */
	private void processOutSwatchTag(User user, OutBean outBean)
    throws MYException
	{
		FinanceTagBean tag = new FinanceTagBean();
		
		tag.setType("OUTSWATCHSHOW");
		tag.setTypeName("领样巡展单");
		tag.setFullId(outBean.getFullId());
		tag.setStatsTime(TimeTools.now());
		
		if (outBean.getMtype() == PublicConstant.MANAGER_TYPE_COMMON)
		{
			tag.setTag("LYP1");
			tag.setMtype(PublicConstant.MANAGER_TYPE_COMMON);
		}else{
			tag.setTag("LYG1");
			tag.setMtype(PublicConstant.MANAGER_TYPE_MANAGER);
		}
		
		financeTagManager.addFinanceTagBeanWithoutTransaction(user, tag);
	}
	
	/**
	 * 
	 * @param user
	 * @param outBean
	 * @throws MYException
	 */
	private void processOutConsignTag(User user, OutBean outBean)
    throws MYException
	{
		FinanceTagBean tag = new FinanceTagBean();
		
		tag.setType("OUTCONSIGN");
		tag.setTypeName("委托代销");
		tag.setFullId(outBean.getFullId());
		tag.setStatsTime(TimeTools.now());
		
		if (outBean.getMtype() == PublicConstant.MANAGER_TYPE_COMMON)
		{
			tag.setTag("WP1");
			tag.setMtype(PublicConstant.MANAGER_TYPE_COMMON);
		}else{
			tag.setTag("WG1");
			tag.setMtype(PublicConstant.MANAGER_TYPE_MANAGER);
		}
		
		financeTagManager.addFinanceTagBeanWithoutTransaction(user, tag);
	}
	
	/**
	 * 
	 * @param user
	 * @param outBean
	 * @throws MYException
	 */
	private void processOutPresentTag(User user, OutBean outBean)
    throws MYException
	{
		FinanceTagBean tag = new FinanceTagBean();
		
		tag.setType("OUTPRESENT");
		tag.setTypeName("赠送");
		tag.setFullId(outBean.getFullId());
		tag.setStatsTime(TimeTools.now());
		
		if (outBean.getMtype() == PublicConstant.MANAGER_TYPE_COMMON)
		{
			tag.setTag("ZP1");
			tag.setMtype(PublicConstant.MANAGER_TYPE_COMMON);
		}else{
			tag.setTag("ZG1");
			tag.setMtype(PublicConstant.MANAGER_TYPE_MANAGER);
		}
		
		financeTagManager.addFinanceTagBeanWithoutTransaction(user, tag);
	}
	
	/**
	 * 
	 * @param user
	 * @param outBean
	 * @throws MYException
	 */
	private void processDuiChongTag(User user, OutBean outBean)
    throws MYException
	{
		FinanceTagBean tag = new FinanceTagBean();
		
		tag.setType("OUTSWATCHSHOWDUICHONG");
		tag.setTypeName("领样巡展单对冲");
		tag.setFullId(outBean.getFullId());
		tag.setStatsTime(TimeTools.now());
		
		if (outBean.getMtype() == PublicConstant.MANAGER_TYPE_COMMON)
		{
			tag.setTag("TLYP1");
			tag.setMtype(PublicConstant.MANAGER_TYPE_COMMON);
		}else{
			tag.setTag("TLYG1");
			tag.setMtype(PublicConstant.MANAGER_TYPE_MANAGER);
		}
		
		financeTagManager.addFinanceTagBeanWithoutTransaction(user, tag);
	}
	
	/**
	 * 
	 * @param user
	 * @param outBean
	 * @throws MYException
	 */
	private void processOutBalanceBackTag(User user, OutBalanceBean outBalanceBean, OutBean outBean)
    throws MYException
	{
		FinanceTagBean tag = new FinanceTagBean();
		
		tag.setType("OUTCONSIGNBACK");
		tag.setTypeName("委托代销退货单");
		tag.setFullId(outBalanceBean.getId());
		tag.setStatsTime(TimeTools.now());
		
		if (outBean.getMtype() == PublicConstant.MANAGER_TYPE_COMMON)
		{
			tag.setTag("TWP1");
			tag.setMtype(PublicConstant.MANAGER_TYPE_COMMON);
		}else{
			tag.setTag("TWG1");
			tag.setMtype(PublicConstant.MANAGER_TYPE_MANAGER);
		}
		
		financeTagManager.addFinanceTagBeanWithoutTransaction(user, tag);
	}
	
	/**
	 * 
	 * @param user
	 * @param outBean
	 * @throws MYException
	 */
	private void processBuySwatchTag(User user, OutBean outBean)
    throws MYException
	{
		FinanceTagBean tag = new FinanceTagBean();
		
		tag.setType("OUTSWATCHSHOWBACK");
		tag.setTypeName("领样巡展单退货");
		tag.setFullId(outBean.getFullId());
		tag.setStatsTime(TimeTools.now());
		
		if (outBean.getMtype() == PublicConstant.MANAGER_TYPE_COMMON)
		{
			tag.setTag("TLYP1");
			tag.setMtype(PublicConstant.MANAGER_TYPE_COMMON);
		}else{
			tag.setTag("TLYG1");
			tag.setMtype(PublicConstant.MANAGER_TYPE_MANAGER);
		}
		
		financeTagManager.addFinanceTagBeanWithoutTransaction(user, tag);
	}
	
	@Override
	public void onTranOut(User user, OutBean out, StafferBean targerStaffer) throws MYException
	{
		if (out.getStatus() == OutConstant.STATUS_PASS 
    			|| out.getStatus() == OutConstant.STATUS_SEC_PASS) {
    		processTranOutCommon(user, out, targerStaffer);
    	}
		
		// 针对已勾款的收款单产 生成的凭证先红冲，再生成新的业务员的凭证. ordinary staffer: outBean.getStafferId; targetStaff
    	List<InBillBean> list = inBillDAO.queryEntityBeansByFK(out.getFullId());
    	
    	for (InBillBean inbill : list) {
    		List<FinanceBean> financeList = financeDAO.queryEntityBeansByCondition("where refOut = ? and refBill = ?", out.getFullId(), inbill.getId());
    		
    		for (FinanceBean finance : financeList) {
    			List<FinanceItemBean> itemList = financeItemDAO.queryEntityBeansByFK(finance.getId());
    			
    			if (itemList.get(0).getStafferId().equals(out.getStafferId())) {
    				// 红冲
    				FinanceBean hcBean = new FinanceBean();
    				
    				BeanUtil.copyProperties(hcBean, finance);
    				
    				hcBean.setId(commonDAO.getSquenceString20("PZ"));
    				hcBean.setName(hcBean.getId());
    				hcBean.setInmoney(-hcBean.getInmoney());
    				hcBean.setOutmoney(-hcBean.getOutmoney());
    				hcBean.setDescription(hcBean.getDescription() + ",客户移交红冲");
    				hcBean.setFinanceDate(TimeTools.now_short());
    				hcBean.setLogTime(TimeTools.now());
    				
    				financeDAO.saveEntityBean(hcBean);
    				
    				String pareId = commonDAO.getSquenceString();
    				
    				for (FinanceItemBean item : itemList) {
    					FinanceItemBean hcItem = new FinanceItemBean();
    					
    					BeanUtil.copyProperties(hcItem, item);
    					
    					hcItem.setId(commonDAO.getSquenceString20());
    					hcItem.setPid(hcBean.getId());
    					hcItem.setPareId(pareId);
    					hcItem.setName(hcItem.getId());
    					if (hcItem.getForward() == 0){
    						hcItem.setInmoney(-hcItem.getInmoney());
    					} else {
    						hcItem.setOutmoney(-hcItem.getOutmoney());
    					}
    					
    					hcItem.setFinanceDate(TimeTools.now_short());
    					hcItem.setLogTime(TimeTools.now());
    					
    					financeItemDAO.saveEntityBean(hcItem);
    				}
    				// 新的
    				FinanceBean newBean = new FinanceBean();
    				
    				BeanUtil.copyProperties(newBean, finance);
    				
    				newBean.setId(commonDAO.getSquenceString20("PZ"));
    				newBean.setName(newBean.getId());
    				newBean.setDescription(newBean.getDescription() + ",客户移交由" + out.getStafferName() + " 到 " + targerStaffer.getName());
    				newBean.setFinanceDate(TimeTools.now_short());
    				newBean.setLogTime(TimeTools.now());
    				
    				financeDAO.saveEntityBean(newBean);
    				
    				String npareId = commonDAO.getSquenceString();
    				
    				for (FinanceItemBean item : itemList) {
    					FinanceItemBean newItem = new FinanceItemBean();
    					
    					BeanUtil.copyProperties(newItem, item);
    					
    					newItem.setId(commonDAO.getSquenceString20());
    					newItem.setPid(newBean.getId());
    					newItem.setPareId(npareId);
    					newItem.setName(newItem.getId());
    					newItem.setStafferId(targerStaffer.getId());
    					newItem.setFinanceDate(TimeTools.now_short());
    					newItem.setLogTime(TimeTools.now());
    					
    					financeItemDAO.saveEntityBean(newItem);
    				}
    			}
    		}
    	}
	}
	
    /*
     * (non-Javadoc)
     * 
     * @see com.center.china.osgi.publics.ParentListener#getListenerType()
     */
    public String getListenerType()
    {
        return "OutListener.TaxGlueImpl";
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
     * @return the departmentDAO
     */
    public DepartmentDAO getDepartmentDAO()
    {
        return departmentDAO;
    }

    /**
     * @param departmentDAO
     *            the departmentDAO to set
     */
    public void setDepartmentDAO(DepartmentDAO departmentDAO)
    {
        this.departmentDAO = departmentDAO;
    }

    /**
     * @return the taxDAO
     */
    public TaxDAO getTaxDAO()
    {
        return taxDAO;
    }

    /**
     * @param taxDAO
     *            the taxDAO to set
     */
    public void setTaxDAO(TaxDAO taxDAO)
    {
        this.taxDAO = taxDAO;
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
     * @return the providerDAO
     */
    public ProviderDAO getProviderDAO()
    {
        return providerDAO;
    }

    /**
     * @param providerDAO
     *            the providerDAO to set
     */
    public void setProviderDAO(ProviderDAO providerDAO)
    {
        this.providerDAO = providerDAO;
    }

    /**
     * @return the financeManager
     */
    public FinanceManager getFinanceManager()
    {
        return financeManager;
    }

    /**
     * @param financeManager
     *            the financeManager to set
     */
    public void setFinanceManager(FinanceManager financeManager)
    {
        this.financeManager = financeManager;
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
     * @return the financeDAO
     */
    public FinanceDAO getFinanceDAO()
    {
        return financeDAO;
    }

    /**
     * @param financeDAO
     *            the financeDAO to set
     */
    public void setFinanceDAO(FinanceDAO financeDAO)
    {
        this.financeDAO = financeDAO;
    }

    /**
     * @return the financeItemDAO
     */
    public FinanceItemDAO getFinanceItemDAO()
    {
        return financeItemDAO;
    }

    /**
     * @param financeItemDAO
     *            the financeItemDAO to set
     */
    public void setFinanceItemDAO(FinanceItemDAO financeItemDAO)
    {
        this.financeItemDAO = financeItemDAO;
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

	public InBillDAO getInBillDAO()
	{
		return inBillDAO;
	}

	public void setInBillDAO(InBillDAO inBillDAO)
	{
		this.inBillDAO = inBillDAO;
	}

	public BillManager getBillManager()
	{
		return billManager;
	}

	public void setBillManager(BillManager billManager)
	{
		this.billManager = billManager;
	}

	/**
	 * @return the financeTagManager
	 */
	public FinanceTagManager getFinanceTagManager()
	{
		return financeTagManager;
	}

	/**
	 * @param financeTagManager the financeTagManager to set
	 */
	public void setFinanceTagManager(FinanceTagManager financeTagManager)
	{
		this.financeTagManager = financeTagManager;
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
}
