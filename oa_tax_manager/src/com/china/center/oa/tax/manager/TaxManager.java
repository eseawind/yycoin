/**
 * File Name: TaxManager.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2011-1-30<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.tax.manager;


import com.center.china.osgi.publics.User;
import com.china.center.common.MYException;
import com.china.center.oa.tax.bean.TaxBean;
import com.china.center.oa.tax.vo.TaxVO;


/**
 * TaxManager
 * 
 * @author ZHUZHU
 * @version 2011-1-30
 * @see TaxManager
 * @since 1.0
 */
public interface TaxManager
{
    void init2();

    boolean addTaxBean(User user, TaxBean bean)
        throws MYException;

    boolean addTaxBeanWithoutTransactional(User user, TaxBean bean)
        throws MYException;

    boolean updateTaxBean(User user, TaxBean bean)
        throws MYException;

    boolean deleteTaxBean(User user, String id)
        throws MYException;

    TaxVO findVO(TaxVO vo);
}
