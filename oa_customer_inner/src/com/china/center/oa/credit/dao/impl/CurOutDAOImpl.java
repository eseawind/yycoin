/**
 * File Name: CurOutDAOImpl.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2010-10-6<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.credit.dao.impl;


import java.util.List;

import com.china.center.jdbc.inter.impl.BaseDAO;
import com.china.center.oa.credit.bean.CurOutBean;
import com.china.center.oa.credit.dao.CurOutDAO;


/**
 * CurOutDAOImpl
 * 
 * @author ZHUZHU
 * @version 2010-10-6
 * @see CurOutDAOImpl
 * @since 1.0
 */
public class CurOutDAOImpl extends BaseDAO<CurOutBean, CurOutBean> implements CurOutDAO
{
    /**
     * findNearestByCid(只分析当前6个月的单据)
     * 
     * @param cid
     * @return
     */
    public CurOutBean findNearestByCid(String cid)
    {
        List<CurOutBean> list = this.queryEntityBeansByCondition("where cid = ? order by id desc", cid);

        if (list.size() == 0)
        {
            return null;
        }

        return list.get(0);
    }

}
