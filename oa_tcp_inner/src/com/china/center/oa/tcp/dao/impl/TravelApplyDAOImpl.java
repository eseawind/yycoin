/**
 * File Name: TravelApplyDAOImpl.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2011-7-10<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.tcp.dao.impl;


import com.china.center.jdbc.annosql.tools.BeanTools;
import com.china.center.jdbc.inter.impl.BaseDAO;
import com.china.center.oa.tcp.bean.TravelApplyBean;
import com.china.center.oa.tcp.dao.TravelApplyDAO;
import com.china.center.oa.tcp.vo.TravelApplyVO;


/**
 * TravelApplyDAOImpl
 * 
 * @author ZHUZHU
 * @version 2011-7-10
 * @see TravelApplyDAOImpl
 * @since 3.0
 */
public class TravelApplyDAOImpl extends BaseDAO<TravelApplyBean, TravelApplyVO> implements TravelApplyDAO
{
    public int updateStatus(String id, int status)
    {
        return this.jdbcOperation.updateField("status", status, id, this.claz);
    }

    public int updateFeedback(String id, String refId, int feedback)
    {
        String sql = BeanTools.getUpdateHead(claz) + "set feedback = ?, refId = ? where id = ?";

        return this.jdbcOperation.update(sql, feedback, refId, id);
    }

    public int updateBorrowTotal(String id, long borrowTotal)
    {
        return this.jdbcOperation.updateField("borrowTotal", borrowTotal, id, this.claz);
    }

    public int updateTotal(String id, long total)
    {
        return this.jdbcOperation.updateField("total", total, id, this.claz);
    }

    public int updateBorrowStafferId(String id, String borrowStafferId)
    {
        return this.jdbcOperation.updateField("borrowStafferId", borrowStafferId, id, this.claz);
    }

    public int updateDutyId(String id, String dutyId)
    {
        return this.jdbcOperation.updateField("dutyId", dutyId, id, this.claz);
    }
    
    @Override
    public int updateCompliance(String id, String compliance) {
       
        return this.jdbcOperation.updateField("compliance", compliance, id, this.claz);
    }
}
