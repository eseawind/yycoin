/**
 * File Name: FinanceTurnDAOImpl.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2011-7-27<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.tax.dao.impl;


import java.util.List;

import com.china.center.jdbc.inter.impl.BaseDAO;
import com.china.center.oa.tax.bean.FinanceTurnBean;
import com.china.center.oa.tax.dao.FinanceTurnDAO;
import com.china.center.oa.tax.vo.FinanceTurnVO;
import com.china.center.tools.ListTools;


/**
 * FinanceTurnDAOImpl
 * 
 * @author ZHUZHU
 * @version 2011-7-27
 * @see FinanceTurnDAOImpl
 * @since 3.0
 */
public class FinanceTurnDAOImpl extends BaseDAO<FinanceTurnBean, FinanceTurnVO> implements FinanceTurnDAO
{
    public FinanceTurnVO findLastVO()
    {
        List<FinanceTurnVO> list = this.listEntityVOsByOrder("order by monthKey desc");

        if (ListTools.isEmptyOrNull(list))
        {
            return null;
        }

        return list.get(0);
    }
}
