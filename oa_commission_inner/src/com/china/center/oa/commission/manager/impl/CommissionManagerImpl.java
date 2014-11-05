package com.china.center.oa.commission.manager.impl;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

import com.center.china.osgi.publics.User;
import com.china.center.common.MYException;
import com.china.center.jdbc.annosql.constant.AnoConstant;
import com.china.center.jdbc.util.ConditionParse;
import com.china.center.oa.commission.bean.AllProfitBean;
import com.china.center.oa.commission.bean.BadDropAssessBean;
import com.china.center.oa.commission.bean.BadDropAssessDetailBean;
import com.china.center.oa.commission.bean.CommissionBean;
import com.china.center.oa.commission.bean.CommissionFreezeBean;
import com.china.center.oa.commission.bean.CommissionLogBean;
import com.china.center.oa.commission.bean.CommissionMonthBean;
import com.china.center.oa.commission.bean.CurFeeBean;
import com.china.center.oa.commission.bean.CurFeeDetailBean;
import com.china.center.oa.commission.bean.CurMoneyBean;
import com.china.center.oa.commission.bean.CurMoneyDetailBean;
import com.china.center.oa.commission.bean.CurProfitBean;
import com.china.center.oa.commission.bean.DeductionTurnBean;
import com.china.center.oa.commission.bean.ExternalAssessBean;
import com.china.center.oa.commission.bean.ExternalStafferYearBean;
import com.china.center.oa.commission.bean.FinanceFeeResultBean;
import com.china.center.oa.commission.bean.FinanceFeeStatsBean;
import com.china.center.oa.commission.bean.FinanceFeeStatsDetailBean;
import com.china.center.oa.commission.dao.AllProfitDAO;
import com.china.center.oa.commission.dao.BadDropAssessDAO;
import com.china.center.oa.commission.dao.BadDropAssessDetailDAO;
import com.china.center.oa.commission.dao.CommissionDAO;
import com.china.center.oa.commission.dao.CommissionFreezeDAO;
import com.china.center.oa.commission.dao.CommissionLogDAO;
import com.china.center.oa.commission.dao.CommissionMonthDAO;
import com.china.center.oa.commission.dao.CurFeeDAO;
import com.china.center.oa.commission.dao.CurFeeDetailDAO;
import com.china.center.oa.commission.dao.CurMoneyDAO;
import com.china.center.oa.commission.dao.CurMoneyDetailDAO;
import com.china.center.oa.commission.dao.CurProfitDAO;
import com.china.center.oa.commission.dao.DeductionTurnDAO;
import com.china.center.oa.commission.dao.ExternalAssessDAO;
import com.china.center.oa.commission.dao.ExternalStafferYearDAO;
import com.china.center.oa.commission.dao.FinanceFeeResultDAO;
import com.china.center.oa.commission.dao.FinanceFeeStatsDAO;
import com.china.center.oa.commission.dao.FinanceFeeStatsDetailDAO;
import com.china.center.oa.commission.helper.CommissionHelper;
import com.china.center.oa.commission.manager.CommissionManager;
import com.china.center.oa.publics.bean.StafferBean;
import com.china.center.oa.publics.dao.CommonDAO;
import com.china.center.oa.publics.dao.StafferDAO;
import com.china.center.oa.sail.bean.BaseBean;
import com.china.center.oa.sail.bean.OutBean;
import com.china.center.oa.sail.constanst.OutConstant;
import com.china.center.oa.sail.dao.BaseDAO;
import com.china.center.oa.sail.dao.OutDAO;
import com.china.center.oa.sail.helper.YYTools;
import com.china.center.oa.tax.bean.FinanceBean;
import com.china.center.oa.tax.bean.FinanceItemBean;
import com.china.center.oa.tax.bean.FinanceTurnRollbackBean;
import com.china.center.oa.tax.dao.FinanceDAO;
import com.china.center.oa.tax.dao.FinanceItemDAO;
import com.china.center.oa.tax.dao.FinanceTurnDAO;
import com.china.center.oa.tax.dao.FinanceTurnRollbackDAO;
import com.china.center.oa.tax.helper.FinanceHelper;
import com.china.center.oa.tax.vo.FinanceTurnVO;
import com.china.center.tools.ListTools;
import com.china.center.tools.MathTools;
import com.china.center.tools.TimeTools;

public class CommissionManagerImpl implements CommissionManager 
{
    private final Log operationLog = LogFactory.getLog("opr");
    
    private final Log triggerLog = LogFactory.getLog("trigger");
    
    private StafferDAO stafferDAO = null;
    
    private OutDAO  outDAO = null;
    
    private FinanceFeeStatsDAO financeFeeStatsDAO = null;
    
    private CommissionLogDAO commissionLogDAO = null;
    
    private FinanceFeeResultDAO financeFeeResultDAO = null;
    
    private CurMoneyDAO curMoneyDAO = null;
    
    private BadDropAssessDAO badDropAssessDAO = null;
    
    private FinanceItemDAO financeItemDAO = null;
    
    private CurFeeDAO curFeeDAO = null;
    
    private CurProfitDAO curProfitDAO = null;
    
    private AllProfitDAO allProfitDAO = null;
    
    private FinanceTurnDAO financeTurnDAO = null;
    
    private FinanceTurnRollbackDAO financeTurnRollbackDAO = null;
    
    private FinanceFeeStatsDetailDAO financeFeeStatsDetailDAO = null;
    
    private BaseDAO baseDAO = null;
    
    private CurMoneyDetailDAO curMoneyDetailDAO = null; 
    
    private FinanceDAO financeDAO = null;
    
    private BadDropAssessDetailDAO badDropAssessDetailDAO = null;    
    
    private CommissionMonthDAO commissionMonthDAO = null;
    
    private DeductionTurnDAO deductionTurnDAO = null;
    
    private ExternalAssessDAO externalAssessDAO = null;
    
    private ExternalStafferYearDAO externalStafferYearDAO = null;
    
    private CommissionDAO commissionDAO = null;
    
    private CommonDAO commonDAO = null;
    
    private CurFeeDetailDAO curFeeDetailDAO = null;
    
    private CommissionFreezeDAO commissionFreezeDAO = null;
    
    private PlatformTransactionManager transactionManager = null;
    
    /**
     * 统计资金占用费，每月分别月中与月末统计，起始时间:2011-03-01<br>
     * 每月的16日凌晨1:30统计第一次, type=0 <br>
     * 每月的1日凌晨1:30统计截止上月月底的数据，type=1，统计完后，并与第一次比较<br>
     * {@inheritDoc}
     */
    public void statsFinanceFee0()
    {
        triggerLog.info("statsFinanceFee0 开始统计...");
        
        long statsStar = System.currentTimeMillis();
        
        TransactionTemplate tran = new TransactionTemplate(transactionManager);
        
        try
        {
            tran.execute(new TransactionCallback()
            {
                public Object doInTransaction(TransactionStatus arg0)
                {
                    String statsDate = TimeTools.changeTimeToDate(TimeTools.now(-1));
                    
                    String month = TimeTools.now("yyyyMM");
                    
                    processFinanceFee(statsDate, month, 0, 10);
                    
                    saveCommissionLog(month,10);
                    
                    return Boolean.TRUE;
                }
            });
        }
        catch (Exception e)
        {
            triggerLog.error(e, e);
        }
      
        triggerLog.info("statsFinanceFee0 统计结束... ,共耗时："+ (System.currentTimeMillis() - statsStar));
        
        return;
    }

    /**
     * 
     * {@inheritDoc}
     */
    public void statsFinanceFee1()
    {
        triggerLog.info("statsFinanceFee1 开始统计...");
        
        long statsStar = System.currentTimeMillis();
        
        TransactionTemplate tran = new TransactionTemplate(transactionManager);
        
        try
        {
            tran.execute(new TransactionCallback()
            {
                public Object doInTransaction(TransactionStatus arg0)
                {
                    String statsDate = TimeTools.changeTimeToDate(TimeTools.now(-1));
                    
                    String month = TimeTools.changeFormat(TimeTools.getLastestMonthBegin(), TimeTools.SHORT_FORMAT, "yyyyMM");
                    
                    processFinanceFee(statsDate, month, 1, 11);
                    
                    saveCommissionLog(month,11);
                    
                    return Boolean.TRUE;
                }
            });
        }
        catch (Exception e)
        {
            triggerLog.error(e, e);
        }
      
        triggerLog.info("statsFinanceFee1 统计结束... ,共耗时："+ (System.currentTimeMillis() - statsStar));
        
        return;
    } 
    
    private void setFinanceFeeStatsCondition(String statsDate, StafferBean stafferBean, ConditionParse condition) 
    {
        condition.clear();
        
        condition.addWhereStr();
        
        condition.addIntCondition("OutBean.type", "=", OutConstant.OUT_TYPE_OUTBILL);
        
        condition.addIntCondition("OutBean.pay", "=", OutConstant.PAY_NOT);
        
        condition.addCondition("and OutBean.status in (3, 4)");
        
        condition.addCondition("OutBean.stafferId", "=", stafferBean.getId());
        
        condition.addCondition("OutBean.outTime", ">=", "2011-03-01");
        
        condition.addCondition("OutBean.outTime", "<=", statsDate);
        
        condition.addCondition("OutBean.redate", "<", statsDate);
        
    }

    /**
     * CORE 资金占用费统计
     * @param statsDate
     * @param month
     * @param type
     * @
     */
    private void processFinanceFee(String statsDate, String month, int type, int status) 
    {
        boolean check = check(month,status);
        
        if (!check)
        {
            triggerLog.info("statsFinanceFee 已成功统计过，月份："+month+",状态:"+status);
            
            throw new RuntimeException("statsFinanceFee 已成功统计过，月份："+month+",状态:"+status);
        }
        
        List<StafferBean> stafferList = stafferDAO.getNeedCommissionStaffer();
        
        ConditionParse condition = new ConditionParse();
        
        for (StafferBean stafferBean : stafferList)
        {
            String stafferId = stafferBean.getId();
        
            setFinanceFeeStatsCondition(statsDate, stafferBean, condition);
            
            List<OutBean> outList = outDAO.queryEntityBeansByCondition(condition);
            
            double financeFee = 0.0d;
            
            for (OutBean outBean : outList)
            {
                double feePerOut = 0.0d;
                
                double outBackValue = sumOutBackValue(outBean.getFullId());
                
                // 其它入库中关联了销售单的
                double outForceBackValue = outDAO.sumOutForceBackValue(outBean.getFullId());
                
                double financeFeeRatio = CommissionHelper.getFinanceFeeRatio(statsDate, outBean.getRedate());
                
                feePerOut = (outBean.getTotal() - outBackValue - outForceBackValue) * financeFeeRatio;
                
                FinanceFeeStatsDetailBean statsDetailBean = new FinanceFeeStatsDetailBean();
                
                statsDetailBean.setMonth(month);
                statsDetailBean.setStafferId(stafferId);
                statsDetailBean.setOutId(outBean.getFullId());
                statsDetailBean.setOutValue(outBean.getTotal());
                statsDetailBean.setOutBackValue(outBackValue);
                statsDetailBean.setOtherBuyValue(outForceBackValue);
                statsDetailBean.setMoney(feePerOut);
                statsDetailBean.setRatio(financeFeeRatio);
                statsDetailBean.setType(type);
                statsDetailBean.setUsed(type);
                
                financeFeeStatsDetailDAO.saveEntityBean(statsDetailBean);
                
                financeFee += feePerOut;
            }
            
            FinanceFeeStatsBean statsBean = new FinanceFeeStatsBean();
            
            statsBean.setMonth(month);
            statsBean.setMoney(financeFee);
            statsBean.setStafferId(stafferId);
            statsBean.setType(type);
            
            financeFeeStatsDAO.saveEntityBean(statsBean);
            
            //月末统计时，取出月中统计的数据，做比较，取小的值
            if (type == 1)
            {
                double minFee = 0.0d;
                
                int used = 0;
                
                FinanceFeeResultBean resultBean = new FinanceFeeResultBean();
                
                FinanceFeeStatsBean stats0Bean = financeFeeStatsDAO.findByTypeAndMonthAndStaffer(0, month, stafferId);
                
                if(null == stats0Bean)
                {
                    minFee = 0.0d;
                }else
                {
                    int i = MathTools.compare(stats0Bean.getMoney(), financeFee);
                    
                    if (i>=0)
                    {
                        minFee = financeFee;
                        
                        used = 1;
                    }
                    else
                    {
                        minFee = stats0Bean.getMoney();
                        
                        used = 0;
                    }                        
                }
                
                resultBean.setMonth(month);
                resultBean.setStafferId(stafferId);
                resultBean.setMoney(minFee);
                
                financeFeeResultDAO.saveEntityBean(resultBean);
                
                // 更新执行最小值的标志                
                financeFeeStatsDetailDAO.updateUsedByMonthAndStafferIdAndType(month, stafferId, used, 99);
            }
        
        }
        
    }
    
    /**
     * 含销售退库， 领样退库
     * 
     * @param fullId
     * @return
     */
    private double sumOutBackValue(String fullId)
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

        return backTotal;
    }
    
    /**
     * 统计到款总额及毛利
     * 统计时间为每月的2日凌晨1:30
     * {@inheritDoc}
     */    
    public void statsHadPayAndProfit()  
    {
        triggerLog.info("statsHadPayAndProfit 开始统计...");        
        
        long statsStar = System.currentTimeMillis();
        
        TransactionTemplate tran = new TransactionTemplate(transactionManager);
        try
        {
            tran.execute(new TransactionCallback()
            {
                public Object doInTransaction(TransactionStatus arg0)
                {
                    String statsBegin = TimeTools.getLastestMonthBegin();
                    
                    String statsEnd = TimeTools.getMonthEnd(statsBegin);
                    
                    String month = TimeTools.changeFormat(statsBegin, TimeTools.SHORT_FORMAT, "yyyyMM");
                    
                    processHadPayAndProfit(statsBegin, statsEnd, month);
                    
                    saveCommissionLog(month,20);
                    
                    return Boolean.TRUE;
                }
            });
        }
        catch(Exception e)
        {
            triggerLog.error(e, e);
        }
        
        triggerLog.info("statsHadPayAndProfit 统计结束...,共耗时："+ (System.currentTimeMillis() - statsStar));
                
        return;
    }


    /**
     * Core
     * @param stafferList
     * @param statsBegin
     * @param statsEnd
     * @param month
     */
    private void processHadPayAndProfit(String statsBegin,
            String statsEnd, String month) 
    {
        boolean check = check(month,20);
        
        if (!check)
        {
            triggerLog.info("statsHadPayAndProfit 已成功统计过，月份："+month);
            
            throw new RuntimeException("statsHadPayAndProfit 已成功统计过，月份："+month);
        }
        
        List<StafferBean> stafferList = stafferDAO.getNeedCommissionStaffer();
        
        ConditionParse con = new ConditionParse();
        
        for (StafferBean stafferBean : stafferList)
        {
            String stafferId = stafferBean.getId();
            
            double curMoney = 0.0d;
            
            double curCost = 0.0d;
            
            double baseValuePerStaffer = 0.0d;
            
            double costValuePerStaffer = 0.0d;
            
            double baseBackValuePerStaffer = 0.0d;
            
            double costBackValuePerStaffer = 0.0d;
            
            double baseOtherBuyValuePerStaffer = 0.0d;
            
            double costOtherBuyValuePerStaffer = 0.0d;
            
            double badDebts = 0.0d;
                            
            // 销售数据统计
            setStatsHadPayCon1(statsBegin, statsEnd, con, stafferBean);
            
            List<OutBean> outList = outDAO.queryEntityBeansByCondition(con);
            
            for (OutBean outBean : outList)
            {
                List<BaseBean> baseList = baseDAO.queryEntityBeansByFK(outBean.getFullId());
                
                double baseValue = 0.0d;
                double costValue = 0.0d;
                
                for (BaseBean baseBean : baseList)
                {
                    baseValue += baseBean.getValue();
                    
                    costValue += baseBean.getInputPrice()*baseBean.getAmount();
                }
                
                CurMoneyDetailBean detailBean = new CurMoneyDetailBean();
                
                detailBean.setMonth(month);
                detailBean.setStafferId(stafferId);
                detailBean.setOutId(outBean.getFullId());
                detailBean.setType(outBean.getType());                
                detailBean.setMoney(baseValue - outBean.getPromValue());
                detailBean.setCost(costValue);
                detailBean.setProfit(baseValue - outBean.getPromValue() - costValue);
                detailBean.setPromValue(outBean.getPromValue());
                detailBean.setBadDebts(outBean.getBadDebts());
                
                curMoneyDetailDAO.saveEntityBean(detailBean);
                
                baseValuePerStaffer += (baseValue - outBean.getPromValue());
                costValuePerStaffer += costValue;
                badDebts += outBean.getBadDebts();
            }

            // 销售退库
            setStatsHadPayCon2(statsBegin, statsEnd, con, stafferBean);
            
            List<OutBean> outList2 = outDAO.queryEntityBeansByCondition(con);
            
            for (OutBean outBean : outList2)
            {
                List<BaseBean> baseList = baseDAO.queryEntityBeansByFK(outBean.getFullId());
                
                double baseValue = 0.0d;
                double costValue = 0.0d;
                
                for (BaseBean baseBean : baseList)
                {
                    baseValue += baseBean.getValue();
                    
                    costValue += baseBean.getInputPrice()*baseBean.getAmount();
                }
                
                CurMoneyDetailBean detailBean = new CurMoneyDetailBean();
                
                detailBean.setMonth(month);
                detailBean.setStafferId(stafferId);
                detailBean.setOutId(outBean.getFullId());
                detailBean.setType(outBean.getType());                
                detailBean.setMoney(baseValue);
                detailBean.setCost(costValue);
                detailBean.setProfit(baseValue - costValue);
                detailBean.setPromValue(0.0d);
                detailBean.setRefOutFullId(outBean.getRefOutFullId());
                
                curMoneyDetailDAO.saveEntityBean(detailBean);
                
                baseBackValuePerStaffer += baseValue;
                costBackValuePerStaffer += costValue;
            }
            
            // 其它入库
            setStatsHadPayCon3(statsBegin, statsEnd, con, stafferBean);
            
            List<OutBean> outList3 = outDAO.queryEntityBeansByCondition(con);
            
            for (OutBean outBean : outList3)
            {
                List<BaseBean> baseList = baseDAO.queryEntityBeansByFK(outBean.getFullId());
                
                double baseValue = 0.0d;
                double costValue = 0.0d;
                
                for (BaseBean baseBean : baseList)
                {
                    baseValue += baseBean.getValue();
                    
                    costValue += baseBean.getCostPrice() * baseBean.getAmount();
                }
                
                CurMoneyDetailBean detailBean = new CurMoneyDetailBean();
                
                detailBean.setMonth(month);
                detailBean.setStafferId(stafferId);
                detailBean.setOutId(outBean.getFullId());
                detailBean.setType(outBean.getType());                
                detailBean.setMoney(baseValue);
                detailBean.setCost(costValue);
                detailBean.setProfit(baseValue - costValue);
                detailBean.setPromValue(outBean.getPromValue());
                detailBean.setRefOutFullId(outBean.getRefOutFullId());
                
                curMoneyDetailDAO.saveEntityBean(detailBean);
                
                baseOtherBuyValuePerStaffer += baseValue;
                costOtherBuyValuePerStaffer += costValue;
            }
            
            curMoney = baseValuePerStaffer - baseBackValuePerStaffer - baseOtherBuyValuePerStaffer;
            curCost = costValuePerStaffer - costBackValuePerStaffer - costOtherBuyValuePerStaffer ;
            
            CurMoneyBean curMoneyBean = new CurMoneyBean();
            
            curMoneyBean.setMonth(month);
            curMoneyBean.setStafferId(stafferId);
            curMoneyBean.setMoney(curMoney);
            curMoneyBean.setProfit(curMoney - curCost);
            curMoneyBean.setBadDebts(badDebts);
            
            curMoneyDAO.saveEntityBean(curMoneyBean);
        }
    }

    private void setStatsHadPayCon1(String statsBegin, String statsEnd, ConditionParse con,
            StafferBean stafferBean) 
    {
        con.clear();
        
        con.addWhereStr();
        
        con.addIntCondition("outBean.type", "=", 0);
        
        con.addIntCondition("outBean.pay", "=", 1);
        
        con.addCondition(" and outBean.status in (3,4)");
        
        con.addCondition(" and outBean.outtype in (0,2)");
        
        con.addCondition("outBean.redate", ">=", statsBegin);
        
        con.addCondition("outBean.redate", "<=", statsEnd);
        
        con.addCondition("outBean.stafferId", "=", stafferBean.getId());
    }

    private void setStatsHadPayCon2(String statsBegin, String statsEnd, ConditionParse con,
            StafferBean stafferBean) 
    {
        
        con.clear();
        
        con.addWhereStr();
        
        con.addIntCondition("outBean.type", "=", 1);
        
        con.addCondition(" and outBean.status in (3,4)");
        
        con.addIntCondition("outBean.outType", "=", 5);
        
        con.addCondition("outBean.outTime", ">=", statsBegin);
        
        con.addCondition("outBean.outTime", "<=", statsEnd);
        
        con.addCondition("outBean.stafferId", "=", stafferBean.getId());

    }
    
    /**
     * 其它入库的条件
     * @param statsBegin
     * @param statsEnd
     * @param con
     * @param stafferBean
     */
    private void setStatsHadPayCon3(String statsBegin, String statsEnd, ConditionParse con,
            StafferBean stafferBean) 
    {
        
        con.clear();
        
        con.addWhereStr();
        
        con.addIntCondition("outBean.type", "=", 1);
        
        con.addCondition(" and outBean.status in (3,4)");
        
        con.addIntCondition("outBean.outType", "=", 99);
        
        con.addCondition("outBean.reserve8", "<>", "1");
        
        con.addCondition("outBean.outTime", ">=", statsBegin);
        
        con.addCondition("outBean.outTime", "<=", statsEnd);
        
        con.addCondition("outBean.reserve9", "=", stafferBean.getId());

    }
    
    /**
     * 统计报废
     * 统计时间为每月的3日凌晨1:30
     * {@inheritDoc}
     */
    public void statsDrop()
    {
        triggerLog.info("statsDrop 开始统计...");
        
        long statsStar = System.currentTimeMillis();
        
        TransactionTemplate tran = new TransactionTemplate(transactionManager);
        try
        {
            tran.execute(new TransactionCallback()
            {
                public Object doInTransaction(TransactionStatus arg0)
                {
                    String statsBegin = TimeTools.getLastestMonthBegin();
                    
                    String statsEnd = TimeTools.getMonthEnd(statsBegin);
                    
                    String month = TimeTools.changeFormat(statsBegin, TimeTools.SHORT_FORMAT, "yyyyMM");
                    
                    processBaddebtAndDrop(statsBegin, statsEnd, month);
                    
                    saveCommissionLog(month,30);
                    
                    return Boolean.TRUE;
                }
            });           
        }
        catch(Exception e)
        {
            triggerLog.error(e, e);
        }
        
        triggerLog.info("statsDrop 统计结束...,共耗时："+ (System.currentTimeMillis() - statsStar));
                
        return;
    }

    /**
     * 
     * @param statsBegin
     * @param statsEnd
     * @param month
     */
    private void processBaddebtAndDrop(String statsBegin, String statsEnd, String month) 
    {
        
        boolean check = check(month,30);
        
        if (!check)
        {
            triggerLog.info("statsDrop 已成功统计过，月份："+month);
            
            throw new RuntimeException("statsDrop 已成功统计过，月份："+month);
        }
        
        List<StafferBean> stafferList = stafferDAO.getNeedCommissionStaffer();
        
        ConditionParse con = new ConditionParse();
        
        for (StafferBean stafferBean : stafferList)
        {   
            double inMoneyPerStaffer = 0.0d;
            
            setDropCondition(statsBegin, statsEnd, con, stafferBean);
            
            List<FinanceItemBean> itemList = financeItemDAO.queryEntityBeansByCondition(con);
            
            for (FinanceItemBean eachBean : itemList)
            {
                long dropLong = eachBean.getInmoney();

                double dropDouble = FinanceHelper.longToDouble(dropLong);
                
                BadDropAssessDetailBean detailBean = new BadDropAssessDetailBean();
                
                detailBean.setMonth(month);
                detailBean.setStafferId(stafferBean.getId());
                detailBean.setType(1);
                detailBean.setValue(dropDouble);
                
                String pid = eachBean.getPid();
                
                FinanceBean bean = financeDAO.find(pid);
                
                if (null != bean)
                {
                    detailBean.setOutId(bean.getRefId());
                }                    
                
                badDropAssessDetailDAO.saveEntityBean(detailBean);
                
                inMoneyPerStaffer += dropDouble;
            }
            
            
            BadDropAssessBean badDropBean = new BadDropAssessBean();
            
            badDropBean.setMonth(month);
            badDropBean.setStafferId(stafferBean.getId());
            badDropBean.setValue(inMoneyPerStaffer);
            badDropBean.setType(1);
            
            badDropAssessDAO.saveEntityBean(badDropBean);
        }
    }

    /**
     * 报废条件,从凭证中取
     * @param statsBegin
     * @param statsEnd
     * @param con
     * @param stafferBean
     */
    private void setDropCondition(String statsBegin, String statsEnd, ConditionParse con,
            StafferBean stafferBean) 
    {
        
        con.clear();
        
        con.addWhereStr();
        
        con.addCondition("FinanceItemBean.financeDate", ">=", statsBegin);
        
        con.addCondition("FinanceItemBean.financeDate", "<=", statsEnd);
               
        con.addCondition(" and FinanceItemBean.taxid in ('5504-30','5505-30','1133-06')");
        
        con.addIntCondition("FinanceItemBean.forward", "=", 0);
        
        con.addCondition("FinanceItemBean.stafferId", "=", stafferBean.getId());
    }
    
    /**
     * 统计费用与净利
     * 统计时间为每天2点开始，前提:
     * 1）上月已做月结
     * 2）上月费用与净利还没有统计
     * 3）上月到款金额已统计
     * {@inheritDoc}
     */    
    public void statsFeeAndNetProfit()
    {        
        triggerLog.info("statsFeeAndNetProfit 开始统计...");
        
        long statsStar = System.currentTimeMillis();
                
        TransactionTemplate tran = new TransactionTemplate(transactionManager);
        try
        {
            tran.execute(new TransactionCallback()
            {
                public Object doInTransaction(TransactionStatus arg0)
                {
                    String statsBegin = TimeTools.getLastestMonthBegin();
                    
                    String statsEnd = TimeTools.getMonthEnd(statsBegin);
                    
                    String month = TimeTools.changeFormat(statsBegin, TimeTools.SHORT_FORMAT, "yyyyMM");
                    
                    processFeeAndNetProfit(statsBegin, statsEnd, month);
                    
                    saveCommissionLog(month,40);
                    
                    return Boolean.TRUE;
                }
            });
        }
        catch(Exception e)
        {
            triggerLog.error(e, e);
        }
        
        triggerLog.info("statsFeeAndNetProfit 统计结束..., 共耗时："+ (System.currentTimeMillis() - statsStar));
                
        return;
    }

    /**
     * Core 计算费用及净利
     * @param statsBegin
     * @param statsEnd
     * @param month
     */
    private void processFeeAndNetProfit(String statsBegin, String statsEnd, String month) 
    {
        
        boolean check = check(month,40);
        
        if (!check)
        {
            triggerLog.info("statsFeeAndNetProfit 已成功统计过，月份："+month);
            
            throw new RuntimeException("statsFeeAndNetProfit 已成功统计过，月份："+month);
        }
        
        FinanceTurnVO trunVO = financeTurnDAO.findLastVO();
        
        if (!trunVO.getMonthKey().equals(month))
        {
            triggerLog.info("statsFeeAndNetProfit 上月还没有做月结,净利暂不统计，月份："+month);
            
            throw new RuntimeException("statsFeeAndNetProfit 上月还没有做月结,净利暂不统计，月份："+month);
        }
        
        boolean check1 = check(month,20);
        
        if (check1)
        {
            triggerLog.info("statsFeeAndNetProfit 上月到款总额没有生成，月份："+month);
            
            throw new RuntimeException("statsFeeAndNetProfit 上月到款总额没有生成，月份："+month);
        }
        
        List<StafferBean> stafferList = stafferDAO.getNeedCommissionStaffer();
        
        ConditionParse con = new ConditionParse();
        
        for (StafferBean stafferBean : stafferList)
        {
            double inMoneyPerStaffer = 0.0d;
            
            setFeeCondition(statsBegin, statsEnd, con, stafferBean);
                  
            List<FinanceItemBean> beanList = financeItemDAO.queryEntityBeansByCondition(con);

            for (FinanceItemBean eachBean : beanList)
            {
                long feeLong = eachBean.getInmoney();

                double feeDouble = FinanceHelper.longToDouble(feeLong);

                CurFeeDetailBean detailBean = new CurFeeDetailBean();
                
                detailBean.setStafferId(stafferBean.getId());
                detailBean.setMonth(month);                    
                detailBean.setMoney(feeDouble);
                
                String pid = eachBean.getPid();
                
                FinanceBean financeBean = financeDAO.find(pid);
                
                if (null != financeBean)
                {
                    detailBean.setRefId(financeBean.getRefId());  
                    detailBean.setDescription(financeBean.getDescription()); 
                }

                curFeeDetailDAO.saveEntityBean(detailBean);
                
                inMoneyPerStaffer += feeDouble;
            }
            
            CurFeeBean curFeeBean = new CurFeeBean();
            
            curFeeBean.setMonth(month);
            curFeeBean.setStafferId(stafferBean.getId());
            curFeeBean.setMoney(inMoneyPerStaffer);
            
            curFeeDAO.saveEntityBean(curFeeBean);
            
            // 结合毛利,计算净利
            CurProfitBean curProfitBean = new CurProfitBean();
            
            curProfitBean.setMonth(month);
            curProfitBean.setStafferId(stafferBean.getId());
            
            CurMoneyBean curMoneyBean = curMoneyDAO.findByMonthAndStaffer(month, stafferBean.getId());
            
            if (null == curMoneyBean)
            {
                curProfitBean.setMoney(0 - inMoneyPerStaffer);
            }else
            {
                curProfitBean.setMoney(curMoneyBean.getProfit() - inMoneyPerStaffer);
            }
            
            curProfitDAO.saveEntityBean(curProfitBean);
            
            // 更新累计
            AllProfitBean allProfitBean = allProfitDAO.findByUnique(stafferBean.getId());
            
            if (null == allProfitBean)
            {
                AllProfitBean bean = new AllProfitBean();
                
                bean.setStafferId(stafferBean.getId());
                bean.setMoneys(curProfitBean.getMoney());
                bean.setLastModified(TimeTools.now());
                
                allProfitDAO.saveEntityBean(bean);
            }
            else
            {
                allProfitBean.setMoneys(allProfitBean.getMoneys() + curProfitBean.getMoney());
                allProfitBean.setLastModified(TimeTools.now());
                
                allProfitDAO.updateEntityBean(allProfitBean);
            }
            
        }
    }

    private void setFeeCondition(String statsBegin, String statsEnd, ConditionParse con,
            StafferBean stafferBean) 
    {
        
        con.clear();
        
        con.addWhereStr();
        
        con.addCondition("FinanceItemBean.financeDate", ">=", statsBegin);
        
        con.addCondition("FinanceItemBean.financeDate", "<=", statsEnd);
        
        con.addCondition("FinanceItemBean.taxid0", "=", "5504");
        
        con.addIntCondition("FinanceItemBean.forward", "=", 0);
        
        con.addCondition("FinanceItemBean.stafferId", "=", stafferBean.getId());
    }
    
    /**
     * 
     * @param month
     * @param status
     */
    private void saveCommissionLog(String month, int status)
    {
        // 记录统计日志
        CommissionLogBean logBean = new CommissionLogBean();
        
        logBean.setMonth(month);
        logBean.setStatus(status);
        logBean.setOpr("系统");
        logBean.setLastModified(TimeTools.now());
        
        commissionLogDAO.saveEntityBean(logBean);
    }
    
    /**
     * 判断月度统计是否成功操作过
     * @param month
     * @param status
     * @return
     */
    private boolean check(String month, int status)
    {
        CommissionLogBean bean = commissionLogDAO.findByUnique(month, status);
        
        if (null == bean)
            return true;
        else
            return false;
    }
    
    @Transactional(rollbackFor=MYException.class)
    public void rollbackFeeAndNetProfit()
    {        
        triggerLog.info("rollbackFeeAndNetProfit begin...");
        
        try
        {
            FinanceTurnRollbackBean turnRollback = financeTurnRollbackDAO.findLastBean();
            
            if (null == turnRollback)
                return;
            
            CommissionLogBean logBean = commissionLogDAO.findByUnique(turnRollback.getMonthKey(), 40);
            
            if (null == logBean)
            {
                financeTurnRollbackDAO.deleteAllEntityBean();
            
                return;
            }
            
            curFeeDAO.deleteEntityBeansByFK(turnRollback.getMonthKey());
            
            curFeeDetailDAO.deleteEntityBeansByFK(turnRollback.getMonthKey());
            
            List<CurProfitBean> curProfitList = curProfitDAO.queryEntityBeansByFK(turnRollback.getMonthKey());
         
            for (CurProfitBean each : curProfitList)
            {
                String stafferId = each.getStafferId();
                
                double money = each.getMoney();
                
                AllProfitBean aprofitBean = allProfitDAO.findByUnique(stafferId);
                
                if (null != aprofitBean)
                {
                    aprofitBean.setMoneys(aprofitBean.getMoneys() - money);
                    
                    allProfitDAO.updateEntityBean(aprofitBean);
                }
            }
            
            curProfitDAO.deleteEntityBeansByFK(turnRollback.getMonthKey());
            
            // del log
            commissionLogDAO.deleteEntityBean(logBean.getId());
            
            financeTurnRollbackDAO.deleteAllEntityBean();
            
        }
        catch(Exception e)
        {
            triggerLog.error(e, e);
        }
        
        triggerLog.info("rollbackFeeAndNetProfit end...");
    }
    
    @Transactional(rollbackFor=MYException.class)
    public boolean addBean(User user) throws MYException 
    {
        //计算前的校验
        CommissionMonthBean monthBean = commissionMonthDAO.findLastBean();
        
        String nextMonth = "";
        String lastMonth = "";
        
        if (null == monthBean)
        {
            nextMonth = TimeTools.changeFormat(TimeTools.getLastestMonthBegin(), TimeTools.SHORT_FORMAT, "yyyyMM");
        }
        else
        {
            nextMonth = TimeTools.getStringByOrgAndDaysAndFormat(monthBean.getMonth(), 32,
            "yyyyMM");
            lastMonth = monthBean.getMonth();
        }
        
        checkMonth(nextMonth, lastMonth);
        
        String id = commonDAO.getSquenceString20();
        
        processCalculateCommission(nextMonth, lastMonth, id);
        
        //记录提成计算概要
        CommissionMonthBean commissionMonthBean = new CommissionMonthBean();
        
        String statsBegin = TimeTools.getLastestMonthBegin();
        
        String statsEnd = TimeTools.getMonthEnd(statsBegin);
        
        commissionMonthBean.setId(id);
        commissionMonthBean.setMonth(nextMonth);
        commissionMonthBean.setStafferId(user.getStafferId());
        commissionMonthBean.setBeginTime(statsBegin);
        commissionMonthBean.setEndTime(statsEnd);
        commissionMonthBean.setLogTime(TimeTools.now());
        
        commissionMonthDAO.saveEntityBean(commissionMonthBean);
        
        return true;
    }


    private void processCalculateCommission(String nextMonth, String lastMonth, String id)
    {
        //根据人，获取基础数据，进行相关计算，并保存结果        
        List<StafferBean> stafferList = stafferDAO.getNeedCommissionStaffer();
        
        for (StafferBean eachStaffer : stafferList)
        {
            double profitValue = 0.0d;
            double finaFeeValue = 0.0d;
            double feeValue = 0.0d;
            double dropValue = 0.0d;
            double badDebts = 0.0d;
            double turnValue = 0.0d;
            
            double kpi = 0.0d;

            double scost = 0.0d;
            double yearKpi = 0.0d;
            double sCommission = 0.0d;
            
            // 冻结
            double freezeMoney = 0.0d;
            double curFreezeMoney = 0.0d;
            
            String stafferId = eachStaffer.getId();

            // 净利
            CurProfitBean curProfitBean = curProfitDAO.findByMonthAndStaffer(nextMonth, stafferId);
            
            if (null != curProfitBean)
            {
                profitValue = curProfitBean.getMoney();
            }
            
            CurFeeBean curFeeBean = curFeeDAO.findByMonthAndStaffer(nextMonth, stafferId);
            
            if (null != curFeeBean)
            {
                feeValue = curFeeBean.getMoney();
            }

            // 资金占用费用
            FinanceFeeResultBean feeResultBean = financeFeeResultDAO.findByMonthAndStaffer(nextMonth, stafferId);
            
            if (null != feeResultBean)
            {
                finaFeeValue = feeResultBean.getMoney();
            }
            
            // 报废
            BadDropAssessBean dropAssessBean = badDropAssessDAO.findByMonthAndStaffer(nextMonth, stafferId, 1);
            
            if (null != dropAssessBean)
            {
                dropValue = dropAssessBean.getValue();
            }

            // 坏账
            CurMoneyBean curMoneyBean = curMoneyDAO.findByMonthAndStaffer(nextMonth, stafferId);
            
            if (null != curMoneyBean)
            {
                badDebts = curMoneyBean.getBadDebts();
            }

            // 上月未扣的
            DeductionTurnBean turnBean = deductionTurnDAO.findByMonthAndStaffer(lastMonth, stafferId);
            
            if (null != turnBean)
            {
                turnValue = turnBean.getMoney();
            }

            // 上月冻结金额
            CommissionFreezeBean freezeBean = commissionFreezeDAO.findByMonthAndStaffer(lastMonth, stafferId);
            
            if (null != freezeBean)
            {
                freezeMoney = freezeBean.getFreezeMoney();
            }
            
            // 外部导入
            ExternalAssessBean externalAssessBean = externalAssessDAO.findByMonthAndStaffer(nextMonth, stafferId);
            
            if (null != externalAssessBean)
            {
                kpi = externalAssessBean.getKpi();
                
//                frozenRatio = externalAssessBean.getRatio();
                
                scost = externalAssessBean.getCost();
                
                yearKpi = externalAssessBean.getYearKpi();
                
                sCommission = externalAssessBean.getShouldCommission();
            }
            
            double realCommission = 0.0d;
            double kpiValue = 0.0d;
            double turnNextMonthDeduction = 0.0d;
            
            double finalCommission = 0.0d;
            
            // KPI 扣款 到款净利*kpi
            kpiValue = profitValue * kpi;
            
            // 显示计算公式“当月应发提成XXX元-上月未扣XXX元 -应收成本考核XXX元-KPI扣款XXX元-报废XXX元-资金占用费XXX元+扣款转下月XXX元+年度KPI返款XXX元
            realCommission = sCommission - turnValue - scost - kpiValue - dropValue - badDebts - finaFeeValue + yearKpi;
            
            if (realCommission <= 0)
            {
                turnNextMonthDeduction = -realCommission;
                realCommission = 0.0d;
            }
            else
            {
                // 计算信用冻结比例(缓发)
                double totalCredit = eachStaffer.getCredit() * eachStaffer.getLever();
                
                // 使用信用 - 自己使用
                double st = outDAO.sumNoPayAndAvouchBusinessByStafferId(stafferId, eachStaffer
                    .getIndustryId(), YYTools. getStatBeginDate(), YYTools.getStatEndDate());

                // 担保
                double mt = outDAO.sumNoPayAndAvouchBusinessByManagerId2(stafferId, YYTools
                    . getStatBeginDate(), YYTools.getStatEndDate());

                double creditUsedRate = 0.0d;
                
                // 信用冻结提成
                double frozenRatio = 0.0d;
                
                double creditUsed = st + mt;
                
                if (totalCredit != 0)
                {
                    creditUsedRate = creditUsed/totalCredit;
                }
                
                if (creditUsed <= totalCredit * 0.3)
                {
                    frozenRatio = 0.0d;
                }
                else if (creditUsed > totalCredit * 0.3 && creditUsed <= totalCredit)
                {
                    frozenRatio = 0.5d;
                }
                else
                {
                    frozenRatio = 1.0d;
                }
                
                curFreezeMoney = (realCommission + freezeMoney) * frozenRatio ;
                
                // 冻结后最终可发的提成
                finalCommission = realCommission + freezeMoney - curFreezeMoney ;
                
                CommissionFreezeBean cfreezeBean = new CommissionFreezeBean();
                
                cfreezeBean.setParentid(id);
                cfreezeBean.setMonth(nextMonth);
                cfreezeBean.setStafferId(stafferId);
                cfreezeBean.setFreezeRate(frozenRatio);
                cfreezeBean.setFreezeMoney(curFreezeMoney);
                cfreezeBean.setCreditMoney(creditUsed);
                cfreezeBean.setCreditAmout(totalCredit);
                cfreezeBean.setCreditRate(creditUsedRate);
                
                commissionFreezeDAO.saveEntityBean(cfreezeBean);
                
            }
            
            CommissionBean bean = new CommissionBean();
            
            bean.setParentId(id);
            bean.setMonth(nextMonth);
            bean.setStafferId(stafferId);
            bean.setShouldCommission(sCommission);
            bean.setRealCommission(realCommission);
            bean.setProfit(profitValue);
            bean.setFee(feeValue);
            bean.setLastDeduction(turnValue);
            bean.setReceiveCost(scost);
            bean.setKpiDeduction(kpiValue);
            bean.setTurnNextMonthDeduction(turnNextMonthDeduction);
            bean.setYearKpiMoney(yearKpi);
            bean.setBaddebt(badDebts);
            bean.setBroken(dropValue);
            bean.setFinanceFee(finaFeeValue);
            bean.setBefFreeze(freezeMoney);
            bean.setCurFreeze(curFreezeMoney);
            bean.setFinalCommission(finalCommission);

            commissionDAO.saveEntityBean(bean);

            if (turnNextMonthDeduction > 0)
            {
                DeductionTurnBean turnNextBean = new DeductionTurnBean();
                
                turnNextBean.setParentId(id);
                turnNextBean.setMonth(nextMonth);
                turnNextBean.setStafferId(stafferId);
                turnNextBean.setMoney(turnNextMonthDeduction);
                turnNextBean.setDescription("转下月扣款 ");
                
                deductionTurnDAO.saveEntityBean(turnNextBean);
            }
        }
    }
    
    private void checkMonth(String month, String lastMonth) throws MYException
    {
        // 1)检查month 是不是上一个月
    	/*
        String month1 = TimeTools.changeFormat(TimeTools.getLastestMonthBegin(), TimeTools.SHORT_FORMAT, "yyyyMM");
        
        if (lastMonth.equals(month1))
        {
            throw new MYException("上月提成已计算,不能重复计算，请确定");
        }
        
        if (!month1.equals(month))
        {            
            throw new MYException("提成计算月不是上一个月，请确定");
        }*/
        
        // 2)检查基础数据是否全部准备到位log
        int [] statuss = new int[]{40,30,20,11,10};
        
        List<CommissionLogBean> logList = commissionLogDAO.queryEntityBeansByFK(month);

        if (logList.size()!= statuss.length)
        {            
            throw new MYException("提成计算前基础数据准备未完成，请核对");
        }
        
        // 从大到小
        Collections.sort(logList, new Comparator<CommissionLogBean>() {
            public int compare(CommissionLogBean o1, CommissionLogBean o2) {
                return o2.getStatus() - o1.getStatus();
            }
        });

        for (int i = 0; i < logList.size(); i++)
        {
            if(logList.get(i).getStatus()!= statuss[i])
            {
                throw new MYException("提成计算前基础数据准备有误，请核对");
            }
        }
        
    }
    
    /**
     * 
     * {@inheritDoc}
     */
    @Transactional(rollbackFor={MYException.class})
    public boolean deleteBean(User user, String id) throws MYException
    {
        CommissionMonthBean bean = commissionMonthDAO.find(id);
        
        if (null == bean)
        {
            throw new MYException("数据错误，请确认");            
        }
        
        // 检查是不是上月的提成数据
        String lastMonth = TimeTools.changeFormat(TimeTools.getLastestMonthBegin(), TimeTools.SHORT_FORMAT, "yyyyMM");
        
        if (!bean.getMonth().equals(lastMonth))
        {
            throw new MYException("只能撤销上月的提成数据");
        }
        
        operationLog.info("delete commission conf:" + bean);
        
        commissionFreezeDAO.deleteEntityBeansByFK(id, AnoConstant.FK_FIRST);
        
        commissionDAO.deleteEntityBeansByFK(id, AnoConstant.FK_FIRST);
        
        deductionTurnDAO.deleteEntityBeansByFK(id, AnoConstant.FK_FIRST);
     
        commissionMonthDAO.deleteEntityBean(id);
        
        return true;
    }
    
    @Transactional(rollbackFor={MYException.class})
    public boolean importExternel(User user, CommissionBean bean ,String type ) 
    throws MYException 
    {
        if (type.equals("0"))
        {
            externalStafferYearDAO.deleteAllEntityBean();

            List<ExternalStafferYearBean> list = bean.getExternalStafferList();
            
            for (ExternalStafferYearBean each : list)
            {
                externalStafferYearDAO.saveEntityBean(each);
            }
            
        }
        else
        {
            List<ExternalAssessBean> externalAssessList = bean.getExternalAssessList();
            
            if (!ListTools.isEmptyOrNull(externalAssessList))
            {
                String month = externalAssessList.get(0).getMonth();
                
                externalAssessDAO.deleteEntityBeansByFK(month);
            }
            
            externalAssessDAO.saveAllEntityBeans(externalAssessList);
        }
        
        return true;
        
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

    public FinanceFeeStatsDAO getFinanceFeeStatsDAO() {
        return financeFeeStatsDAO;
    }

    public void setFinanceFeeStatsDAO(FinanceFeeStatsDAO financeFeeStatsDAO) {
        this.financeFeeStatsDAO = financeFeeStatsDAO;
    }

    public CommissionLogDAO getCommissionLogDAO() {
        return commissionLogDAO;
    }

    public void setCommissionLogDAO(CommissionLogDAO commissionLogDAO) {
        this.commissionLogDAO = commissionLogDAO;
    }

    public FinanceFeeResultDAO getFinanceFeeResultDAO() {
        return financeFeeResultDAO;
    }

    public void setFinanceFeeResultDAO(FinanceFeeResultDAO financeFeeResultDAO) {
        this.financeFeeResultDAO = financeFeeResultDAO;
    }

    public CurMoneyDAO getCurMoneyDAO() {
        return curMoneyDAO;
    }

    public void setCurMoneyDAO(CurMoneyDAO curMoneyDAO) {
        this.curMoneyDAO = curMoneyDAO;
    }

    public BadDropAssessDAO getBadDropAssessDAO() {
        return badDropAssessDAO;
    }

    public void setBadDropAssessDAO(BadDropAssessDAO badDropAssessDAO) {
        this.badDropAssessDAO = badDropAssessDAO;
    }

    public FinanceItemDAO getFinanceItemDAO() {
        return financeItemDAO;
    }

    public void setFinanceItemDAO(FinanceItemDAO financeItemDAO) {
        this.financeItemDAO = financeItemDAO;
    }

    public CurFeeDAO getCurFeeDAO() {
        return curFeeDAO;
    }

    public void setCurFeeDAO(CurFeeDAO curFeeDAO) {
        this.curFeeDAO = curFeeDAO;
    }

    public CurProfitDAO getCurProfitDAO() {
        return curProfitDAO;
    }

    public void setCurProfitDAO(CurProfitDAO curProfitDAO) {
        this.curProfitDAO = curProfitDAO;
    }

    public AllProfitDAO getAllProfitDAO() {
        return allProfitDAO;
    }

    public void setAllProfitDAO(AllProfitDAO allProfitDAO) {
        this.allProfitDAO = allProfitDAO;
    }


    public FinanceTurnDAO getFinanceTurnDAO() {
        return financeTurnDAO;
    }


    public void setFinanceTurnDAO(FinanceTurnDAO financeTurnDAO) {
        this.financeTurnDAO = financeTurnDAO;
    }


    public FinanceTurnRollbackDAO getFinanceTurnRollbackDAO() {
        return financeTurnRollbackDAO;
    }


    public void setFinanceTurnRollbackDAO(FinanceTurnRollbackDAO financeTurnRollbackDAO) {
        this.financeTurnRollbackDAO = financeTurnRollbackDAO;
    }


    public FinanceFeeStatsDetailDAO getFinanceFeeStatsDetailDAO() {
        return financeFeeStatsDetailDAO;
    }


    public void setFinanceFeeStatsDetailDAO(FinanceFeeStatsDetailDAO financeFeeStatsDetailDAO) {
        this.financeFeeStatsDetailDAO = financeFeeStatsDetailDAO;
    }


    public BaseDAO getBaseDAO() {
        return baseDAO;
    }


    public void setBaseDAO(BaseDAO baseDAO) {
        this.baseDAO = baseDAO;
    }


    public CurMoneyDetailDAO getCurMoneyDetailDAO() {
        return curMoneyDetailDAO;
    }


    public void setCurMoneyDetailDAO(CurMoneyDetailDAO curMoneyDetailDAO) {
        this.curMoneyDetailDAO = curMoneyDetailDAO;
    }


    public FinanceDAO getFinanceDAO() {
        return financeDAO;
    }


    public void setFinanceDAO(FinanceDAO financeDAO) {
        this.financeDAO = financeDAO;
    }


    public BadDropAssessDetailDAO getBadDropAssessDetailDAO() {
        return badDropAssessDetailDAO;
    }


    public void setBadDropAssessDetailDAO(BadDropAssessDetailDAO badDropAssessDetailDAO) {
        this.badDropAssessDetailDAO = badDropAssessDetailDAO;
    }


    public CommissionMonthDAO getCommissionMonthDAO() {
        return commissionMonthDAO;
    }


    public void setCommissionMonthDAO(CommissionMonthDAO commissionMonthDAO) {
        this.commissionMonthDAO = commissionMonthDAO;
    }


    public DeductionTurnDAO getDeductionTurnDAO() {
        return deductionTurnDAO;
    }


    public void setDeductionTurnDAO(DeductionTurnDAO deductionTurnDAO) {
        this.deductionTurnDAO = deductionTurnDAO;
    }


    public ExternalAssessDAO getExternalAssessDAO() {
        return externalAssessDAO;
    }


    public void setExternalAssessDAO(ExternalAssessDAO externalAssessDAO) {
        this.externalAssessDAO = externalAssessDAO;
    }


    public CommissionDAO getCommissionDAO() {
        return commissionDAO;
    }


    public void setCommissionDAO(CommissionDAO commissionDAO) {
        this.commissionDAO = commissionDAO;
    }


    public CommonDAO getCommonDAO() {
        return commonDAO;
    }


    public void setCommonDAO(CommonDAO commonDAO) {
        this.commonDAO = commonDAO;
    }

    public CurFeeDetailDAO getCurFeeDetailDAO() {
        return curFeeDetailDAO;
    }

    public void setCurFeeDetailDAO(CurFeeDetailDAO curFeeDetailDAO) {
        this.curFeeDetailDAO = curFeeDetailDAO;
    }

    public PlatformTransactionManager getTransactionManager() {
        return transactionManager;
    }

    public void setTransactionManager(PlatformTransactionManager transactionManager) {
        this.transactionManager = transactionManager;
    }

    public CommissionFreezeDAO getCommissionFreezeDAO() {
        return commissionFreezeDAO;
    }

    public void setCommissionFreezeDAO(CommissionFreezeDAO commissionFreezeDAO) {
        this.commissionFreezeDAO = commissionFreezeDAO;
    }

    public ExternalStafferYearDAO getExternalStafferYearDAO() {
        return externalStafferYearDAO;
    }

    public void setExternalStafferYearDAO(ExternalStafferYearDAO externalStafferYearDAO) {
        this.externalStafferYearDAO = externalStafferYearDAO;
    }

    
  
    
}
