/**
 * File Name: DepotListenerSailImpl.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2012-3-1<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.sail.listener.impl;


import com.center.china.osgi.publics.User;
import com.china.center.common.MYException;
import com.china.center.jdbc.util.ConditionParse;
import com.china.center.oa.product.bean.DepotBean;
import com.china.center.oa.product.listener.DepotListener;
import com.china.center.oa.sail.dao.OutDAO;


/**
 * DepotListenerSailImpl
 * 
 * @author ZHUZHU
 * @version 2012-3-1
 * @see DepotListenerSailImpl
 * @since 1.0
 */
public class DepotListenerSailImpl implements DepotListener
{
    private OutDAO outDAO = null;

    /*
     * (non-Javadoc)
     * 
     * @see com.china.center.oa.product.listener.DepotListener#onDeleteDepot(com.center.china.osgi.publics.User,
     *      com.china.center.oa.product.bean.DepotBean)
     */
    public void onDeleteDepot(User user, DepotBean bean)
        throws MYException
    {
        ConditionParse con = new ConditionParse();

        con.addWhereStr();

        con.addCondition("OutBean.location", "=", bean.getId());

        if (outDAO.countByCondition(con.toString()) > 0)
        {
            throw new MYException("仓库已经被使用不能删除");
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.center.china.osgi.publics.ParentListener#getListenerType()
     */
    public String getListenerType()
    {
        return "DepotListener.SailImpl";
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

}
