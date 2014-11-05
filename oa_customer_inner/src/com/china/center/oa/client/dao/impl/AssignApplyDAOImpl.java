/**
 * File Name: AssignApplyDAOImpl.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2010-10-6<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.client.dao.impl;


import com.china.center.jdbc.inter.IbatisDaoSupport;
import com.china.center.jdbc.inter.impl.BaseDAO;
import com.china.center.oa.client.bean.AssignApplyBean;
import com.china.center.oa.client.dao.AssignApplyDAO;
import com.china.center.oa.client.vo.AssignApplyVO;


/**
 * AssignApplyDAOImpl
 * 
 * @author ZHUZHU
 * @version 2010-10-6
 * @see AssignApplyDAOImpl
 * @since 1.0
 */
public class AssignApplyDAOImpl extends BaseDAO<AssignApplyBean, AssignApplyVO> implements AssignApplyDAO
{
    private IbatisDaoSupport ibatisDaoSupport = null;

    @Deprecated
    public boolean delAssignByCityId(String cityId)
    {
        ibatisDaoSupport.delete("CustomerDAOImpl.delAssignByCityId", cityId);

        return true;
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
