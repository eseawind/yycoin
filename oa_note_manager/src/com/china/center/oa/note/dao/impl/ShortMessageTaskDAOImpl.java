/**
 * File Name: ShortMessageTaskDAOImpl.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2010-7-17<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.note.dao.impl;


import com.china.center.jdbc.annosql.tools.BeanTools;
import com.china.center.jdbc.inter.impl.BaseDAO;
import com.china.center.oa.note.bean.ShortMessageTaskBean;
import com.china.center.oa.note.constant.ShortMessageConstant;
import com.china.center.oa.note.dao.ShortMessageTaskDAO;
import com.china.center.tools.TimeTools;


/**
 * ShortMessageTaskDAOImpl
 * 
 * @author ZHUZHU
 * @version 2010-7-17
 * @see ShortMessageTaskDAOImpl
 * @since 1.0
 */
public class ShortMessageTaskDAOImpl extends BaseDAO<ShortMessageTaskBean, ShortMessageTaskBean> implements ShortMessageTaskDAO
{
    /**
     * findByReceiverAndHandId
     * 
     * @param receiver
     * @param handId
     * @return ShortMessageTaskBean
     */
    public ShortMessageTaskBean findByReceiverAndHandId(String receiver, String handId)
    {
        return this.findUnique("where receiver = ? and handId = ?", receiver, handId);
    }

    public boolean updateInitToWaitSend()
    {
        String sql = BeanTools.getUpdateHead(claz) + "set status = ? where sendTime <= ? and status = ?";

        this.jdbcOperation.update(sql, ShortMessageConstant.STATUS_WAIT_SEND, TimeTools.now(),
            ShortMessageConstant.STATUS_INIT);

        return true;
    }
}
