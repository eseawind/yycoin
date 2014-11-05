/**
 * File Name: FinanceItemDAO.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2011-2-6<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.tax.dao;


import java.util.List;

import com.china.center.jdbc.inter.DAO;
import com.china.center.jdbc.util.ConditionParse;
import com.china.center.jdbc.util.PageSeparate;
import com.china.center.oa.tax.bean.FinanceItemBean;
import com.china.center.oa.tax.vo.FinanceItemVO;
import com.china.center.oa.tax.vo.StafferUnitVO;


/**
 * FinanceItemDAO
 * 
 * @author ZHUZHU
 * @version 2011-2-6
 * @see FinanceItemDAO
 * @since 1.0
 */
public interface FinanceItemDAO extends DAO<FinanceItemBean, FinanceItemVO>
{
    /**
     * 借方总额
     * 
     * @param condition
     * @return
     */
    long sumInByCondition(ConditionParse condition);

    /**
     * 贷方总额
     * 
     * @param condition
     * @return
     */
    long sumOutByCondition(ConditionParse condition);

    long[] sumMoneryByCondition(ConditionParse condition);

    /**
     * 通过条件和分页获取
     * 
     * @param condition
     * @param newPage
     * @return
     */
    long[] sumVOMoneryByCondition(ConditionParse condition, PageSeparate newPage);

    /**
     * 查询指定时间内发生凭证的单位
     * 
     * @param stafferId
     * @param beginDate
     * @param endDate
     * @return
     */
    List<String> queryDistinctUnitByStafferId(String stafferId, String beginDate, String endDate);

    /**
     * 查询指定时间内发生凭证的单位
     * 
     * @param stafferId
     * @param beginDate
     * @param endDate
     * @return
     */
    List<String> queryDistinctUnit(String beginDate, String endDate, String taxColumn, String taxId);

    /**
     * 查询指定时间内发生凭证的职员
     * 
     * @param stafferId
     * @param beginDate
     * @param endDate
     * @return
     */
    List<String> queryDistinctStafferId(String beginDate, String endDate, String taxColumn,
                                        String taxId);
    
    List<StafferUnitVO> queryDistinctStafferIdAndUnitId(String beginDate, String endDate, String taxColumn,
            String taxId);
}
