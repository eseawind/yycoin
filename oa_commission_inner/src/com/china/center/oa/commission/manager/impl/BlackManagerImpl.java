package com.china.center.oa.commission.manager.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

import com.center.china.osgi.publics.User;
import com.china.center.common.MYException;
import com.china.center.common.taglib.DefinedCommon;
import com.china.center.jdbc.expression.Expression;
import com.china.center.jdbc.util.ConditionParse;
import com.china.center.oa.commission.bean.BlackBean;
import com.china.center.oa.commission.bean.BlackOutBean;
import com.china.center.oa.commission.bean.BlackOutDetailBean;
import com.china.center.oa.commission.bean.BlackRuleBean;
import com.china.center.oa.commission.bean.BlackRuleProductBean;
import com.china.center.oa.commission.bean.BlackRuleStafferBean;
import com.china.center.oa.commission.bean.OutPayChangedBean;
import com.china.center.oa.commission.bean.ReDateRuleBean;
import com.china.center.oa.commission.constant.BlackConstant;
import com.china.center.oa.commission.dao.BlackDAO;
import com.china.center.oa.commission.dao.BlackOutDAO;
import com.china.center.oa.commission.dao.BlackOutDetailDAO;
import com.china.center.oa.commission.dao.BlackRuleDAO;
import com.china.center.oa.commission.dao.BlackRuleProductDAO;
import com.china.center.oa.commission.dao.BlackRuleStafferDAO;
import com.china.center.oa.commission.dao.OutPayChangedDAO;
import com.china.center.oa.commission.dao.ReDateRuleDAO;
import com.china.center.oa.commission.manager.BlackManager;
import com.china.center.oa.commission.wrap.BlackOutWrap;
import com.china.center.oa.product.bean.ProductBean;
import com.china.center.oa.product.dao.ProductDAO;
import com.china.center.oa.publics.bean.StafferBean;
import com.china.center.oa.publics.constant.StafferConstant;
import com.china.center.oa.publics.dao.CommonDAO;
import com.china.center.oa.publics.dao.StafferDAO;
import com.china.center.oa.publics.manager.NotifyManager;
import com.china.center.oa.sail.bean.BaseBalanceBean;
import com.china.center.oa.sail.bean.BaseBean;
import com.china.center.oa.sail.bean.OutBalanceBean;
import com.china.center.oa.sail.bean.OutBean;
import com.china.center.oa.sail.constanst.OutConstant;
import com.china.center.oa.sail.dao.BaseBalanceDAO;
import com.china.center.oa.sail.dao.BaseDAO;
import com.china.center.oa.sail.dao.OutBalanceDAO;
import com.china.center.oa.sail.dao.OutDAO;
import com.china.center.tools.ListTools;
import com.china.center.tools.StringTools;
import com.china.center.tools.TimeTools;

public class BlackManagerImpl implements BlackManager 
{
    private final Log _logger = LogFactory.getLog(getClass());
    
    private final Log operationLog = LogFactory.getLog("opr");
    
    private final Log triggerLog = LogFactory.getLog("trigger");
    
    private CommonDAO commonDAO = null;
    
    private BlackRuleDAO blackRuleDAO = null;
    
    private BlackRuleProductDAO blackRuleProductDAO = null;
    
    private BlackRuleStafferDAO blackRuleStafferDAO = null;
    
    private ReDateRuleDAO reDateRuleDAO = null;
    
    private BlackDAO blackDAO = null;
    
    private BlackOutDAO blackOutDAO = null;
    
    private OutPayChangedDAO outPayChangedDAO = null;
    
    private StafferDAO stafferDAO = null;
    
    private OutDAO outDAO = null;       
    
    private BaseDAO baseDAO = null;
    
    private OutBalanceDAO outBalanceDAO = null;
    
    private BaseBalanceDAO baseBalanceDAO = null;
    
    private ProductDAO productDAO = null;
    
    private BlackOutDetailDAO blackOutDetailDAO = null;
    
    private NotifyManager notifyManager = null;
    
    private PlatformTransactionManager transactionManager = null;
    
    public BlackManagerImpl()
    {        
    }
    
    @Transactional(rollbackFor = MYException.class)
    public boolean addBean(BlackRuleBean bean, User user) throws MYException 
    {
        
        Expression exp = new Expression(bean, this);

        exp.check("#name &unique @blackRuleDAO", "名称已经存在");
        
        List<BlackRuleProductBean> productList = bean.getProductList();
        
        List<BlackRuleStafferBean> stafferList = bean.getStafferList();
        
        String id = commonDAO.getSquenceString20();
        
        bean.setId(id);
        
        blackRuleDAO.saveEntityBean(bean);
        
        for (BlackRuleProductBean each : productList)
        {
            each.setRefId(id);
            
            blackRuleProductDAO.saveEntityBean(each);
        }
        
        for (BlackRuleStafferBean each1 : stafferList)
        {
            each1.setRefId(id);
            
            blackRuleStafferDAO.saveEntityBean(each1);
        }              
        
        return true;
    }

    @Transactional(rollbackFor = MYException.class)
    public boolean updateBean(BlackRuleBean bean, User user) throws MYException 
    {
        
        BlackRuleBean oldBean = blackRuleDAO.find(bean.getId());
        
        if (null == oldBean)
        {
            throw new MYException("数据错误，原单不存在");
        }
        
        List<BlackRuleProductBean> productList = bean.getProductList();
        
        List<BlackRuleStafferBean> stafferList = bean.getStafferList();
        
        String id = bean.getId();
        
        blackRuleDAO.updateEntityBean(bean);
        
        blackRuleProductDAO.deleteEntityBeansByFK(id);
        
        blackRuleStafferDAO.deleteEntityBeansByFK(id);
        
        for (BlackRuleProductBean each : productList)
        {
            each.setRefId(id);
            
            blackRuleProductDAO.saveEntityBean(each);
        }
        
        for (BlackRuleStafferBean each1 : stafferList)
        {
            each1.setRefId(id);
            
            blackRuleStafferDAO.saveEntityBean(each1);
        }              
        
        return true;
    }

    @Transactional(rollbackFor = MYException.class)
    public boolean deleteBean(String id, User user) throws MYException 
    {

        BlackRuleBean bean = blackRuleDAO.find(id);
        
        if (null == bean)
        {
            throw new MYException("数据不存在");
        }
        
        operationLog.info("DEL BlackRuleBean:" + bean);
        
        blackRuleDAO.deleteEntityBean(id);
        
        blackRuleProductDAO.deleteEntityBeansByFK(id);
        
        blackRuleStafferDAO.deleteEntityBeansByFK(id);
        
        return true;
    }

    /**
     * 增加JOB 统计超期的数据，并根据规则计算黑名单人员
     * 每日 凌晨 2:30 统计
     * {@inheritDoc}
     */
    public void statsBlack()
    {
        triggerLog.info("statsBlack 开始统计...");
        
        long statsStar = System.currentTimeMillis();
        
        TransactionTemplate tran = new TransactionTemplate(transactionManager);
        
        try
        {
            tran.execute(new TransactionCallback()
            {
                public Object doInTransaction(TransactionStatus arg0)
                {            
                    String statsDate = TimeTools.now_short();
                    
                    processStatsBlack(statsDate, 0);
                    
                    return Boolean.TRUE;
                }
            });
        }
        catch (Exception e)
        {
            triggerLog.error(e, e);
        }
      
        triggerLog.info("statsBlack 统计结束... ,共耗时："+ (System.currentTimeMillis() - statsStar));
        
        return;
    }

    private void processStatsBlack(String statsDate, int type)
    {
        final String beginDate = "2011-03-01";
        
        // 例外规则
        List<BlackRuleBean> ruleList = getBlackRuleForBlack();
        
        List<String> stafferList = outDAO.queryDistinctStafferId(beginDate, statsDate, type);
        
        ConditionParse condition = new ConditionParse();
        
        for (String each : stafferList)
        {
            String stafferId = each;
        
            setStatsBlackCondition(statsDate, stafferId, condition);
            
            List<OutBean> outList = outDAO.queryEntityBeansByCondition(condition);
            
            double outValue = 0.0d;
            
            double allOutValue = 0.0d;
            
            int maxReDate = 0;
            
            List<BlackOutWrap> outWrapList = new ArrayList<BlackOutWrap>();
            
            List<BlackOutWrap> allOutWrapList = new ArrayList<BlackOutWrap>();
            
            List<BlackOutWrap> reDateOutWrapList = new ArrayList<BlackOutWrap>();            
            
            boolean exceptStaffer = false;

            for (OutBean outBean : outList)
            {
                boolean exceptOut = false;
                
                double perOutValue = 0.0d;
                
                int reDate = 0;
                
                String outId = outBean.getFullId();
                
                List<BaseBean> baseList = baseDAO.queryEntityBeansByFK(outId);
                
                Set<String> productIdSet = new HashSet<String>();
                
                // 刨去例外逻辑，涉及到 事业部，具体单号，销售时间，人员（这些在单据头中），具体商品,品类要涉及到单据行项目
                if (!ListTools.isEmptyOrNull(ruleList))
                {
                    for(BlackRuleBean eachRuleBean : ruleList)
                    {
                        if (TimeTools.cdate(outBean.getOutTime(), eachRuleBean.getBeginOutTime())>=0  
                                && TimeTools.cdate(outBean.getOutTime(), eachRuleBean.getEndOutTime())<=0)
                        {
                            String ruleId = eachRuleBean.getId();
                            
                            String industryId = outBean.getIndustryId();
                            
                            String industryIds = eachRuleBean.getIndustryId();
                            
                            String outIds = eachRuleBean.getOutId();
                            
                            // 事业部
                            if (industryIds.indexOf(industryId) != -1)
                            {
                                exceptStaffer = true;
                            }
                           
                            // 单号
                            if (outIds.indexOf(outId) != -1)
                            {
                                exceptOut = true;
                                
                                break;
                            }
                            
                            // 人员
                            boolean stafferInBlackRule = stafferIdInBlackRule(stafferId, ruleId);
                            
                            if (stafferInBlackRule)
                            {
                                exceptStaffer = true;
                            }
                            
                            // 检查品类、单品， 品类 包含单品
                            List<BlackRuleProductBean> ruleProductList = blackRuleProductDAO.queryEntityBeansByFK(ruleId);
                            
                            if (StringTools.isNullOrNone(eachRuleBean.getProductType()) && ListTools.isEmptyOrNull(ruleProductList))
                            {
                                continue;
                            }
                            else
                            {
                                getExceptProduct(outId, productIdSet, eachRuleBean, ruleProductList, baseList);
                            }
                        }
                        else
                        {
                            continue;
                        }
                    }
                }
                
                // 整笔是例外，直接进入下一个
                if (exceptOut)
                {
                    continue;
                }
                
                // 事业部，人员例外，直接跳出
//                if (exceptStaffer)
//                {
//                    break;
//                }
                    
                // ProductIdSet 中是要刨去的商品
                double excepTotal = 0.0d;
                double total = 0.0d;
                
                if (!productIdSet.isEmpty())
                {
                    for (BaseBean eachBase : baseList)
                    {
                        String productId = eachBase.getProductId();
                        
                        for (String eachItem : productIdSet)
                        {
                            if (eachItem.equals(productId))
                            {
                                excepTotal += eachBase.getValue();
                            }
                        }
                    }
                }
                
                total = outBean.getTotal() - excepTotal;
                
                // 非委托代销
                double outBackValue = sumOutBackValue(outId,productIdSet);
                
                // 委托代销
                double balanceBackValue = 0.0d;
                
                if (outBean.getOutType() == OutConstant.OUTTYPE_OUT_CONSIGN)
                {
                	List<OutBalanceBean> balanceList = outBalanceDAO.queryExcludeSettleBack(outId, 0);
                	
                	for (OutBalanceBean eachBalance : balanceList)
                	{
                		if (eachBalance.getStatus() == 99)
                			balanceBackValue += eachBalance.getTotal();
                	}
                }
                
                
                // 其它入库中关联了销售单的
                double outForceBackValue = sumOutForceBackValue(outId, productIdSet);
                                
                // 本单应收款 = 总金额 - 退货 - 坏账 - 折扣 - 已付款
                perOutValue = total - outBackValue  - balanceBackValue - outForceBackValue - outBean.getBadDebts() - outBean.getPromValue() - outBean.getHadPay() ;
                
                reDate = TimeTools.cdate(statsDate, outBean.getRedate());
                
                // 超期
                if (reDate > 0)
                {
                    outValue += perOutValue;
                                        
                    BlackOutWrap outWrap = new BlackOutWrap();
                    
                    outWrap.setOutId(outId);
                    outWrap.setDays(reDate);
                    outWrap.setMoney(perOutValue);
                    outWrap.setCustomerName(outBean.getCustomerName());
                    
                    outWrapList.add(outWrap);
                    
                    if (reDate >= maxReDate )
                    {
                        if (reDate > maxReDate)
                            reDateOutWrapList.clear();
                        
                        BlackOutWrap reDateOutWrap = new BlackOutWrap();
                        
                        reDateOutWrap.setOutId(outId);
                        reDateOutWrap.setDays(reDate);
                        reDateOutWrap.setMoney(perOutValue);
                        reDateOutWrap.setCustomerName(outBean.getCustomerName());
                        
                        reDateOutWrapList.add(reDateOutWrap);
                        
                        maxReDate = reDate;
                    }
                    
                }
                
                // 所有应收款
                allOutValue += perOutValue;
                                
                BlackOutWrap allOutWrap = new BlackOutWrap();
                
                allOutWrap.setOutId(outId);
                allOutWrap.setDays(reDate);
                allOutWrap.setMoney(perOutValue);
                allOutWrap.setCustomerName(outBean.getCustomerName());
                
                allOutWrapList.add(allOutWrap);
            }
            
            // 处理下一个人
//            if (exceptStaffer)
//            {
//                continue;
//            }
            
            int blackType = processBlackTypeInner(stafferId, outValue, maxReDate, exceptStaffer);
            
            BlackBean blackBean = blackDAO.findByUnique(stafferId);
            StafferBean sb = stafferDAO.find(stafferId);
            
            if (null == blackBean)
            {
                String id = commonDAO.getSquenceString20();
                
                BlackBean bean = new BlackBean();
                
                bean.setId(id);
                bean.setLogDate(statsDate);
                bean.setStafferId(stafferId);
                
                if(null != sb)
                {
                	bean.setIndustryId(sb.getIndustryId());
                }
                bean.setMoney(outValue);
                bean.setDays(maxReDate);
                bean.setAllMoneys(allOutValue);
                bean.setCredit(blackType);
                
                if (blackType != StafferConstant.BLACK_NO)
                {
                    bean.setEntryDate(TimeTools.now_short());
                }
                
                blackDAO.saveEntityBean(bean);
                
                saveBlackOutInner(outWrapList, allOutWrapList, reDateOutWrapList, id);
            }
            else
            {
                String id = blackBean.getId();
                
                blackBean.setLogDate(statsDate);
                blackBean.setMoney(outValue);
                blackBean.setDays(maxReDate);
                blackBean.setAllMoneys(allOutValue);
                
                if (blackBean.getCredit() == StafferConstant.BLACK_NO 
                        && blackType != StafferConstant.BLACK_NO)
                {
                    blackBean.setEntryDate(TimeTools.now_short());
                    blackBean.setRemoveDate("");
                }
                else if (blackBean.getCredit() != StafferConstant.BLACK_NO 
                        && blackType == StafferConstant.BLACK_NO)
                {
                    blackBean.setRemoveDate(TimeTools.now_short());
                }
                
                blackBean.setCredit(blackType);
                
                if(null != sb)
                {
                	blackBean.setIndustryId(sb.getIndustryId());
                }

                // 先删 
                blackDAO.deleteEntityBean(id);
                
                blackDAO.saveEntityBean(blackBean);
                
                blackOutDAO.deleteEntityBeansByFK(id);
                
                saveBlackOutInner(outWrapList, allOutWrapList, reDateOutWrapList, id);
            }
        }
        
        // delete logDate 不是statsDate，人员信息更新为正常
        List<BlackBean> needDelBlackList = blackDAO.queryByLogDate(statsDate);
        
        for (BlackBean eachd : needDelBlackList)
        {
        	blackDAO.deleteEntityBean(eachd.getId());
        	
        	blackOutDAO.deleteEntityBeansByFK(eachd.getId());
        	
        	stafferDAO.updateBlack(eachd.getStafferId(), StafferConstant.BLACK_NO);
        }
    }

    /**
     * 
     * @param outId
     * @param productIdSet
     * @param eachRuleBean
     * @param ruleProductList
     */
    private void getExceptProduct(String outId, Set<String> productIdSet,
            BlackRuleBean eachRuleBean, List<BlackRuleProductBean> ruleProductList, List<BaseBean> baseList)
    {
        
        for (BaseBean eachBase : baseList)
        {
            String productId = eachBase.getProductId();
            
            if (!ListTools.isEmptyOrNull(ruleProductList))
            {
                for (BlackRuleProductBean eachRuleProduct : ruleProductList)
                {
                    if (productId.equals(eachRuleProduct.getProductId()))
                    {
                        if (!productIdSet.contains(productId))
                        {
                            productIdSet.add(productId);
                        }                                                
                    }
                }
            }
            
            if (!StringTools.isNullOrNone(eachRuleBean.getProductType()))
            {
                ProductBean productBean = productDAO.find(productId);
                
                if (null == productBean)
                {
                    continue;
                }
                else
                {
                    String pType = String.valueOf(productBean.getType());
                    
                    if (eachRuleBean.getProductType().indexOf(pType) != -1)
                    {
                        if (!productIdSet.contains(productId))
                        {
                            productIdSet.add(productId);
                        } 
                    }
                }
            }
        }
    }

    /**
     * 
     * @param stafferId
     * @param ruleId
     * @return
     */
    private boolean stafferIdInBlackRule(String stafferId, String ruleId) 
    {
        List<BlackRuleStafferBean> ruleStafferList = blackRuleStafferDAO.queryEntityBeansByFK(ruleId);
        
        for (BlackRuleStafferBean eachRuleStaffer : ruleStafferList)
        {
            if (stafferId.equals(eachRuleStaffer.getStafferId()))
            {
                return true;
            }
        }
        return false;
    }

    /**
     * 
     */
    private List<BlackRuleBean> getBlackRuleForBlack() 
    {
        ConditionParse con = new ConditionParse();
        
        con.addWhereStr();
        
        con.addIntCondition("BlackRuleBean.type", "=", BlackConstant.TYPE_BLACK);
        
        return blackRuleDAO.queryEntityBeansByCondition(con);
    }

    /**
     * 对外开放，根据超期金额，最大天数，职员获取黑名单类型
     * @param stafferId
     * @param outValue
     * @param maxReDate
     * @return
     */
    private int processBlackTypeInner(String stafferId, double outValue, int maxReDate, boolean exceptStaffer)
    {
        int blackType = StafferConstant.BLACK_NO;
        
        //根据黑名单规则,计算是黑名单种类
        if (outValue >0 && maxReDate > 0)
        {
            List<ReDateRuleBean> reDateRuleList = reDateRuleDAO.listEntityBeansByOrder("order by id desc");
            
            for (ReDateRuleBean eachRule : reDateRuleList)
            {
                int leftDays = eachRule.getLeftDays();
                int rightDays = eachRule.getRightDays();
                double money = eachRule.getMoney();
                
                int logicOperation = eachRule.getLogicOperation();
                
                // ||
                if (logicOperation == 0)
                {
                    if ((maxReDate >= leftDays && maxReDate <= rightDays) || outValue > money)
                    {
                        blackType = eachRule.getBlackType();
                        
                        break;
                    }
                }
                else
                {
                    if ((maxReDate >= leftDays && maxReDate <= rightDays) && outValue > money)
                    {
                        blackType = eachRule.getBlackType();
                        
                        break;
                    }
                }

            }
        }
        
        // 更新职员信用属性
        if (!exceptStaffer)
        	stafferDAO.updateBlack(stafferId, blackType);
        
        return blackType;
    }

    /**
     * 当有销售单付款状态发生变化，如回款，退货， 个人领样转销售等（销售库管审批后），除回款实时，其它定时统计
     * {@inheritDoc}
     */
    public void processOutPayChangedWithoutTransaction(String stafferId, BlackBean blackBean,
            double backValue, String refId, final Set<String> idSet) 
    {
        int maxReDate;
        
        int oldMaxReDate = blackBean.getDays();
        
        for  (String eachId : idSet)
        {
            blackOutDAO.deleteEntityBean(eachId);
        }
        
        // 检查超期最长时间的销售单是否全部回款,如果全部回款,则要从超期中二次取出最大超期天数的单据,并更新超期最长的单据数据
        List<BlackOutBean> blackOutList = blackOutDAO.queryByRefIdAndType(refId, 1);
        
        if (ListTools.isEmptyOrNull(blackOutList))
        {
            //二次取出最大超期天数
            maxReDate = blackOutDAO.queryMaxDaysByRefIdAndType(refId, 0);
        }
        else
        {
            maxReDate = blackBean.getDays();
        }
        
        _logger.info("maxReDate :" + maxReDate);
        
        if (maxReDate > 0 && maxReDate != oldMaxReDate)
        {
            List<BlackOutBean> blackOut0List = blackOutDAO.queryByRefIdAndType(refId, 0);
            
            for (BlackOutBean each : blackOut0List)
            {
                if (each.getDays() == maxReDate)
                {
                    BlackOutBean outBean = new BlackOutBean();
                    
                    outBean.setRefId(refId);
                    outBean.setType(1);
                    outBean.setOutId(each.getOutId());
                    outBean.setDays(each.getDays());
                    
                    blackOutDAO.saveEntityBean(outBean);
                }
            }
        }
        
        // 回款后仍超期应收金额（不计当天发生的） 
        double reDateMoney = blackBean.getMoney() - backValue;
        
        if (maxReDate == 0)
        {
            reDateMoney = 0.0d;
            
            backValue = blackBean.getMoney();
        }
            
        
        int blackType = processBlackTypeInner(stafferId, reDateMoney, maxReDate, false);
        
        // 更新黑名单列表
        blackBean.setMoney(reDateMoney);
        blackBean.setDays(maxReDate);
        blackBean.setAllMoneys(blackBean.getAllMoneys() - backValue);
        _logger.info("blackBean.setDays :" + blackBean.getDays());
        int oldBlackType = blackBean.getCredit();
        
        if (oldBlackType == StafferConstant.BLACK_NO 
                && blackType != StafferConstant.BLACK_NO)
        {
            blackBean.setEntryDate(TimeTools.now_short());
            blackBean.setRemoveDate("");
        }
        else if (oldBlackType != StafferConstant.BLACK_NO 
                && blackType == StafferConstant.BLACK_NO)
        {
            blackBean.setRemoveDate(TimeTools.now_short());
        }        
        
        blackBean.setCredit(blackType);
        
        _logger.info("processOutPayChangedWithoutTransaction blackBean update :" + blackBean);
        
        blackDAO.updateEntityBean(blackBean);
        
        // 通知
        if (oldBlackType != blackType)
        {
            String msg = "你的信用属性发生变化，原信用属性为：【"
                + DefinedCommon.getValue("stafferBlack", oldBlackType)
                + "】，新的信用属性为：【"
                + DefinedCommon.getValue("stafferBlack", blackType)
                + "】";
            
            notifyManager.notifyMessage(stafferId, msg);
            
            _logger.info(stafferId+"--"+msg);
        }
    }
    
    private void saveBlackOutInner(List<BlackOutWrap> outWrapList, List<BlackOutWrap> allOutWrapList, 
            List<BlackOutWrap> reDateOutWrapList, String id) 
    {
        for (BlackOutWrap each : outWrapList)
        {
            BlackOutBean outBean = new BlackOutBean();
            
            outBean.setRefId(id);
            outBean.setType(0);
            outBean.setOutId(each.getOutId());
            outBean.setDays(each.getDays());
            outBean.setMoney(each.getMoney());
            outBean.setCustomerName(each.getCustomerName());
            
            blackOutDAO.saveEntityBean(outBean);
        }
        
        for (BlackOutWrap each : reDateOutWrapList)
        {
            BlackOutBean outBean = new BlackOutBean();
            
            outBean.setRefId(id);
            outBean.setType(1);
            outBean.setOutId(each.getOutId());
            outBean.setDays(each.getDays());
            outBean.setMoney(each.getMoney());
            outBean.setCustomerName(each.getCustomerName());
            
            blackOutDAO.saveEntityBean(outBean);
        }
        
        for (BlackOutWrap each : allOutWrapList)
        {
            BlackOutBean outBean = new BlackOutBean();
            
            outBean.setRefId(id);
            outBean.setType(2);
            outBean.setOutId(each.getOutId());
            outBean.setDays(each.getDays());
            outBean.setMoney(each.getMoney());
            outBean.setCustomerName(each.getCustomerName());
            
            blackOutDAO.saveEntityBean(outBean);
        }
    }
    
    private void setStatsBlackCondition(String statsDate, String stafferId, ConditionParse condition) 
    {
        condition.clear();
        
        condition.addWhereStr();
        
        condition.addIntCondition("OutBean.type", "=", OutConstant.OUT_TYPE_OUTBILL);
        
        condition.addIntCondition("OutBean.pay", "=", OutConstant.PAY_NOT);
        
        condition.addCondition("and OutBean.status in (3, 4)");
        
        condition.addCondition("OutBean.stafferId", "=", stafferId);
        
        condition.addCondition("OutBean.outTime", ">=", "2011-03-01");
        
        condition.addCondition("OutBean.outTime", "<=", statsDate);
        
//        condition.addCondition("OutBean.redate", "<", statsDate);
        
    }
    
    /**
     * 每30分钟一次，早上9-17
     * {@inheritDoc}
     */
    public void statsBlackWhenOutPayChanged()
    {
        triggerLog.info("statsBlackWhenOutPayChanged 开始统计...");
        
        long statsStar = System.currentTimeMillis();
        
        TransactionTemplate tran = new TransactionTemplate(transactionManager);
        
        try
        {
            tran.execute(new TransactionCallback()
            {
                public Object doInTransaction(TransactionStatus arg0)
                {
                    String statsDate = TimeTools.now_short();
                    
                    processStatsBlack(statsDate, 1);
                    
                    return Boolean.TRUE;
                }
            });
        }
        catch (Exception e)
        {
            triggerLog.error(e, e);
        }
      
        triggerLog.info("statsBlackWhenOutPayChanged 统计结束... ,共耗时："+ (System.currentTimeMillis() - statsStar));
        
        return;
    }
    
    @SuppressWarnings("unused")
    @Deprecated
    private void processStatsBlackWhenOutPayChanged()
    {
        
        // 获取实时已付款的数据，如果数据量大，要限制每次获取的条数
        List<OutPayChangedBean> list = outPayChangedDAO.listEntityBeans();
        
        if (ListTools.isEmptyOrNull(list))
            return ;
        
        Set<String> idSet = new HashSet<String>();
        
        for (OutPayChangedBean each : list)
        {
            idSet.clear();
            
            double backValue = 0.0d;
            
            String outId = each.getOutId();
            
            OutBean outBean = outDAO.find(outId);
            
            if (null == outBean)
            {
                outPayChangedDAO.deleteEntityBean(each.getId());
                
                continue;
            }
            
            String stafferId = outBean.getStafferId();
            
            BlackBean blackBean = blackDAO.findByUnique(stafferId);
            
            if (null == blackBean)
            {
                outPayChangedDAO.deleteEntityBean(each.getId());
                
                continue;
            }
            
            String refId = blackBean.getId();
            
            BlackOutBean boBean = blackOutDAO.findByRefIdAndTypeAndOutId(refId, 0, outId);
            
            if (null == boBean)
            {
                outPayChangedDAO.deleteEntityBean(each.getId());
                
                continue;
            }

            idSet.add(boBean.getId());
            
            BlackOutBean bo1Bean = blackOutDAO.findByRefIdAndTypeAndOutId(refId, 1, outId);
            
            if (null != bo1Bean)
            {                
                idSet.add(bo1Bean.getId());
            }
            
            _logger.info(stafferId+" "+backValue+" "+refId + "==" + idSet);
            
            processOutPayChangedWithoutTransaction(stafferId, blackBean, backValue, refId, idSet);
            
            // 用完就删掉
            outPayChangedDAO.deleteEntityBean(each.getId());
        }
        
    }
    
    /**
     * 指定商品除外的退库, 含 销售退库, 领样退库
     * 
     * @param fullId
     * @param productIdSet
     * @return
     */
    private double sumOutBackValue(String fullId, Set<String> productIdSet)
    {
        ConditionParse con = new ConditionParse();

        con.addWhereStr();

        con.addCondition("OutBean.refOutFullId", "=", fullId);

        con.addIntCondition("OutBean.type", "=", OutConstant.OUT_TYPE_INBILL);

        con.addCondition("and OutBean.status in (3, 4)");
        
        // 销售退库 & 领样退库
        con.addCondition("and OutBean.outType in (4, 5)");
        
        List<OutBean> refList = outDAO.queryEntityBeansByCondition(con);

        double backTotal = 0.0d;

        for (OutBean outBean : refList)
        {
            backTotal += outBean.getTotal();
        }

        double excepTotal = getExceptProductValue(productIdSet, refList);
        
        return backTotal - excepTotal;
    }
    
    /**
     * 
     * @param fullId
     * @param productIdSet
     * @return
     */
    private double sumOutForceBackValue(String fullId, Set<String> productIdSet) 
    {
        ConditionParse con = new ConditionParse();

        con.addWhereStr();

        con.addCondition("OutBean.refOutFullId", "=", fullId);

        con.addIntCondition("OutBean.type", "=", OutConstant.OUT_TYPE_INBILL);

        con.addCondition("and OutBean.status in (3, 4)");

        con.addIntCondition("OutBean.outType", "=", OutConstant.OUTTYPE_IN_OTHER);
        
//        con.addCondition("OutBean.reserve8", "<>", "1");

        List<OutBean> refList = outDAO.queryEntityBeansByCondition(con);

        double backTotal = 0.0d;

        for (OutBean outBean : refList)
        {
            backTotal += outBean.getTotal();
        }

        double excepTotal = getExceptProductValue(productIdSet, refList);
        
        return backTotal - excepTotal;
    }

    /**
     * 
     * @param productIdSet
     * @param refList
     * @return
     */
    private double getExceptProductValue(Set<String> productIdSet, List<OutBean> refList) 
    {
        // 排除之外的
        double excepTotal = 0.0d;
        
        List<BaseBean> allList = new ArrayList<BaseBean>();        
        
        if (!productIdSet.isEmpty())
        {
            for (OutBean outBean : refList)
            {
                List<BaseBean> list = baseDAO.queryEntityBeansByFK(outBean.getFullId());
                
                allList.addAll(list);
            }
            
            for (BaseBean each : allList)
            {
                String productId = each.getProductId();
                
                for (String eachId :  productIdSet)
                {
                    if (productId.equals(eachId))
                    {
                        excepTotal += each.getValue();
                    }
                }
            }
        }
        
        return excepTotal;
    }
    
    /**
     * 须在 statsBlack 统计结束后，执行此操作
     * @see statsBlack
     * {@inheritDoc}
     */
    public void statsBlackOutDetail()
    {
        triggerLog.info("statsBlackOutDetail 开始统计...");
        
        long statsStar = System.currentTimeMillis();
        
        TransactionTemplate tran = new TransactionTemplate(transactionManager);
        
        try
        {
            tran.execute(new TransactionCallback()
            {
                public Object doInTransaction(TransactionStatus arg0)
                {            
                    processStatsBlackOutDetail();
                    
                    return Boolean.TRUE;
                }
            });
        }
        catch (Exception e)
        {
            triggerLog.error(e, e);
        }
      
        triggerLog.info("statsBlackOutDetail 统计结束... ,共耗时："+ (System.currentTimeMillis() - statsStar));
        
        return;
    }
    
    /**
     * 区别销售与委托代销模式
     */
    private void processStatsBlackOutDetail()
    {
    	blackOutDetailDAO.deleteAllEntityBean();
    	
    	List<BlackOutBean> outList = blackOutDAO.queryEntityBeansByCondition("where type = 2");
    	
    	for (BlackOutBean each : outList)
    	{
    		String outId = each.getOutId();
    		
    		OutBean out = outDAO.find(outId);
    		
    		if (out.getPay() == OutConstant.PAY_YES) {
    			continue;
    		}
    		
    		List<BaseBean> baseList = baseDAO.queryEntityBeansByFK(outId);
    		
    		if (out.getOutType() == OutConstant.OUTTYPE_OUT_CONSIGN)
    		{
    			// 1.未结算部分
    			List<OutBalanceBean> obList1 = outBalanceDAO.queryEntityBeansByCondition("where outid = ? and type = 0 and status = 1", outId);
    			
    			List<OutBalanceBean> obList2 = outBalanceDAO.queryEntityBeansByCondition("where outid = ? and type = 1 and status = 99", outId);
    			
    			obList2.addAll(obList1);
    			
    			for (BaseBean base : baseList)
    			{
    				for (OutBalanceBean balance : obList2)
    				{
    					List<BaseBalanceBean> bList = baseBalanceDAO.queryEntityBeansByFK(balance.getId());
    					
    					for (BaseBalanceBean bb : bList)
    					{
    						if (base.getId().equals(bb.getBaseId()))
    						{
    							base.setAmount(base.getAmount() - bb.getAmount());
    						}
    					}
    				}
    				
    				// 将结果写入明细表
    	        	saveOutDetail(base);
    			}
    			
    			// 2.结算未回款部分
    			for (OutBalanceBean balance : obList1)
    			{
    				if (balance.getPayMoney() > 0)
    					continue;
    				
    				List<BaseBalanceBean> bbList = baseBalanceDAO.queryEntityBeansByFK(balance.getId());
    				
    				for (BaseBalanceBean eachbb : bbList)
    				{
    					List<OutBalanceBean> refBalanceList = outBalanceDAO.queryEntityBeansByCondition("where refOutBalanceId = ? and status = 99", balance.getId());
        				
        				for (OutBalanceBean refb : refBalanceList)
        				{
        					List<BaseBalanceBean> refbbList = baseBalanceDAO.queryEntityBeansByFK(refb.getId());
        					
        					for (BaseBalanceBean refbb : refbbList)
        					{
        						if (eachbb.getBaseId().equals(refbb.getBaseId()))
        						{
        							eachbb.setAmount(eachbb.getAmount() - refbb.getAmount());
        						}
        					}
        				}
        				
        				if (eachbb.getAmount() > 0)
        				{
        					BaseBean base = baseDAO.find(eachbb.getBaseId());
            				
            				BlackOutDetailBean bod = new BlackOutDetailBean();
            				
            				bod.setProductId(base.getProductId());
            				bod.setPrice(eachbb.getSailPrice());
            				bod.setAmount(eachbb.getAmount());
            				bod.setOutId(base.getOutId());
            				bod.setOutBalanceId(eachbb.getParentId());
            				bod.setCostPrice(base.getCostPrice());
            				
            				blackOutDetailDAO.saveEntityBean(bod);
        				}
    				}
    			}
        		
    		}else{
    			// 减去退货部分
    			List<BaseBean> refBaseList = new ArrayList<BaseBean>();
    			
    			ConditionParse con = new ConditionParse();

    	        con.addWhereStr();

    	        con.addCondition("OutBean.refOutFullId", "=", outId);

    	        con.addIntCondition("OutBean.type", "=", OutConstant.OUT_TYPE_INBILL);

    	        con.addCondition("and OutBean.status in (3, 4)");
    	        
    	        // 销售退库 & 领样退库
    	        con.addCondition("and OutBean.outType in (4, 5)");
    	        
    	        List<OutBean> refList = outDAO.queryEntityBeansByCondition(con);
    	        
    	        // 领样对冲单
    	        con.clear();

    	        con.addWhereStr();

    	        con.addCondition("OutBean.refOutFullId", "=", outId);

    	        con.addIntCondition("OutBean.type", "=", OutConstant.OUT_TYPE_INBILL);

    	        con.addCondition("and OutBean.status in (3, 4)");

    	        con.addIntCondition("OutBean.outType", "=", OutConstant.OUTTYPE_IN_OTHER);
    	        
    	        refList.addAll(outDAO.queryEntityBeansByCondition(con));
    	        
    	        for (OutBean eachOut : refList)
    	        {
    	        	refBaseList.addAll(baseDAO.queryEntityBeansByFK(eachOut.getFullId()));
    	        }
    	        
    	        for (BaseBean base : baseList)
    	        {
    	        	for (BaseBean refBase : refBaseList)
    	        	{
    	        		if (base.equals(refBase))
    	        		{
    	        			base.setAmount(base.getAmount() - refBase.getAmount());
    	        		}
    	        	}
    	        	
    	        	saveOutDetail(base);
    	        }
    		}
    	}
    }

	private void saveOutDetail(BaseBean base)
	{
		if (base.getAmount() <= 0)
			return;
		
		BlackOutDetailBean bod = new BlackOutDetailBean();
		
		bod.setProductId(base.getProductId());
		bod.setPrice(base.getPrice());
		bod.setAmount(base.getAmount());
		bod.setOutId(base.getOutId());
		bod.setCostPrice(base.getCostPrice());
		
		blackOutDetailDAO.saveEntityBean(bod);
	}
    
    public CommonDAO getCommonDAO() {
        return commonDAO;
    }

    public void setCommonDAO(CommonDAO commonDAO) {
        this.commonDAO = commonDAO;
    }

    public BlackRuleDAO getBlackRuleDAO() {
        return blackRuleDAO;
    }

    public void setBlackRuleDAO(BlackRuleDAO blackRuleDAO) {
        this.blackRuleDAO = blackRuleDAO;
    }

    public BlackRuleProductDAO getBlackRuleProductDAO() {
        return blackRuleProductDAO;
    }

    public void setBlackRuleProductDAO(BlackRuleProductDAO blackRuleProductDAO) {
        this.blackRuleProductDAO = blackRuleProductDAO;
    }

    public BlackRuleStafferDAO getBlackRuleStafferDAO() {
        return blackRuleStafferDAO;
    }

    public void setBlackRuleStafferDAO(BlackRuleStafferDAO blackRuleStafferDAO) {
        this.blackRuleStafferDAO = blackRuleStafferDAO;
    }

    public PlatformTransactionManager getTransactionManager() {
        return transactionManager;
    }

    public void setTransactionManager(PlatformTransactionManager transactionManager) {
        this.transactionManager = transactionManager;
    }

    public StafferDAO getStafferDAO() {
        return stafferDAO;
    }

    public void setStafferDAO(StafferDAO stafferDAO) {
        this.stafferDAO = stafferDAO;
    }

    public OutDAO getOutDAO() {
        return outDAO;
    }

    public void setOutDAO(OutDAO outDAO) {
        this.outDAO = outDAO;
    }

    public ReDateRuleDAO getReDateRuleDAO() {
        return reDateRuleDAO;
    }

    public void setReDateRuleDAO(ReDateRuleDAO reDateRuleDAO) {
        this.reDateRuleDAO = reDateRuleDAO;
    }

    public BlackDAO getBlackDAO() {
        return blackDAO;
    }

    public void setBlackDAO(BlackDAO blackDAO) {
        this.blackDAO = blackDAO;
    }

    public BlackOutDAO getBlackOutDAO() {
        return blackOutDAO;
    }

    public void setBlackOutDAO(BlackOutDAO blackOutDAO) {
        this.blackOutDAO = blackOutDAO;
    }

    public NotifyManager getNotifyManager() {
        return notifyManager;
    }

    public void setNotifyManager(NotifyManager notifyManager) {
        this.notifyManager = notifyManager;
    }

    public OutPayChangedDAO getOutPayChangedDAO() {
        return outPayChangedDAO;
    }

    public void setOutPayChangedDAO(OutPayChangedDAO outPayChangedDAO) {
        this.outPayChangedDAO = outPayChangedDAO;
    }

    public BaseDAO getBaseDAO() {
        return baseDAO;
    }

    public void setBaseDAO(BaseDAO baseDAO) {
        this.baseDAO = baseDAO;
    }

    public ProductDAO getProductDAO() {
        return productDAO;
    }

    public void setProductDAO(ProductDAO productDAO) {
        this.productDAO = productDAO;
    }

	public OutBalanceDAO getOutBalanceDAO()
	{
		return outBalanceDAO;
	}

	public void setOutBalanceDAO(OutBalanceDAO outBalanceDAO)
	{
		this.outBalanceDAO = outBalanceDAO;
	}

	/**
	 * @return the blackOutDetailDAO
	 */
	public BlackOutDetailDAO getBlackOutDetailDAO()
	{
		return blackOutDetailDAO;
	}

	/**
	 * @param blackOutDetailDAO the blackOutDetailDAO to set
	 */
	public void setBlackOutDetailDAO(BlackOutDetailDAO blackOutDetailDAO)
	{
		this.blackOutDetailDAO = blackOutDetailDAO;
	}

	/**
	 * @return the baseBalanceDAO
	 */
	public BaseBalanceDAO getBaseBalanceDAO()
	{
		return baseBalanceDAO;
	}

	/**
	 * @param baseBalanceDAO the baseBalanceDAO to set
	 */
	public void setBaseBalanceDAO(BaseBalanceDAO baseBalanceDAO)
	{
		this.baseBalanceDAO = baseBalanceDAO;
	}
}
