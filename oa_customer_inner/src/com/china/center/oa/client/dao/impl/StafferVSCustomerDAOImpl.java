/**
 * File Name: StafferVSCustomerDAOImpl.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2010-10-6<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.client.dao.impl;


import com.china.center.jdbc.inter.IbatisDaoSupport;
import com.china.center.jdbc.inter.impl.BaseDAO;
import com.china.center.oa.client.dao.StafferVSCustomerDAO;
import com.china.center.oa.client.vo.StafferVSCustomerVO;
import com.china.center.oa.client.vs.StafferVSCustomerBean;


/**
 * StafferVSCustomerDAOImpl
 * 
 * @author ZHUZHU
 * @version 2010-10-6
 * @see StafferVSCustomerDAOImpl
 * @since 1.0
 */
public class StafferVSCustomerDAOImpl extends BaseDAO<StafferVSCustomerBean, StafferVSCustomerVO> implements StafferVSCustomerDAO
{
    private IbatisDaoSupport ibatisDaoSupport = null;

    /**
     * 修改地市下的拓展客户为新客户,请是否是新的临时变量也是0(就是新的)
     * 
     * @param cityId
     * @return
     */
    public boolean updateNewByCityId(String cityId)
    {
        ibatisDaoSupport.delete("CustomerDAOImpl.updateNewByCityId", cityId);

        return true;
    }

    /**
     * 这里仅仅删除拓展的客户
     * 
     * @param cityId
     * @return
     */
    public boolean delVSByCityId(String cityId)
    {
        ibatisDaoSupport.delete("CustomerDAOImpl.delVSByCityId", cityId);

        return true;
    }

    public int countByStafferId(String stafferId)
    {
        return this.jdbcOperation.queryForInt("where stafferId = ?", claz, stafferId);
    }

    public int countByStafferIdAndCustomerId(String stafferId, String customerId)
    {
        return this.jdbcOperation.queryForInt("where stafferId = ? and customerId = ?", claz,
            stafferId, customerId);
    }

    /**
     * @return the ibatisDaoSupport
     */
    public IbatisDaoSupport getIbatisDaoSupport()
    {
        return ibatisDaoSupport;
    }

    /**
     * @param ibatisDaoSupport
     *            the ibatisDaoSupport to set
     */
    public void setIbatisDaoSupport(IbatisDaoSupport ibatisDaoSupport)
    {
        this.ibatisDaoSupport = ibatisDaoSupport;
    }
}
