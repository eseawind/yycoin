/**
 * File Name: CreditCoreDAO.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2010-10-6<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.credit.dao;


import com.china.center.jdbc.inter.DAO;
import com.china.center.oa.credit.bean.CreditCoreBean;


/**
 * CreditCoreDAO
 * 
 * @author ZHUZHU
 * @version 2010-10-6
 * @see CreditCoreDAO
 * @since 1.0
 */
public interface CreditCoreDAO extends DAO<CreditCoreBean, CreditCoreBean>
{
    int synMaxBusinessToOld(int oldYear);

    /**
     * updateCurCreToInit(是在库存通过的时候,人工干预后的客户信用恢复到原来的)
     * 
     * @param pid
     *            信用项ID
     * @param cid
     *            客户的ID
     * @return
     */
    boolean updateCurCreToInit(String pid, String cid);
}
