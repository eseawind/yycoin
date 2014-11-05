/**
 * File Name: TaxHelper.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2011-5-8<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.tax.helper;


import java.util.HashMap;
import java.util.Map;

import com.china.center.oa.tax.bean.FinanceItemBean;
import com.china.center.oa.tax.bean.TaxBean;
import com.china.center.oa.tax.constanst.TaxConstanst;
import com.china.center.tools.BeanUtil;


/**
 * TaxHelper
 * 
 * @author ZHUZHU
 * @version 2011-5-8
 * @see TaxHelper
 * @since 3.0
 */
public abstract class TaxHelper
{
    public static void copyParent(FinanceItemBean financeItem, TaxBean tax)
    {
        financeItem.setTaxId0(tax.getParentId0());
        financeItem.setTaxId1(tax.getParentId1());
        financeItem.setTaxId2(tax.getParentId2());
        financeItem.setTaxId3(tax.getParentId3());
        financeItem.setTaxId4(tax.getParentId4());
        financeItem.setTaxId5(tax.getParentId5());
        financeItem.setTaxId6(tax.getParentId6());
        financeItem.setTaxId7(tax.getParentId7());
        financeItem.setTaxId8(tax.getParentId8());

        String field = "taxId" + tax.getLevel();

        Map temp = new HashMap();

        temp.put(field, tax.getId());

        BeanUtil.copyProperties(financeItem, temp);
    }

    /**
     * 获取余额
     * 
     * @param tax
     * @param sums
     * @return
     */
    public static long getLastMoney(TaxBean tax, long[] sums)
    {
        long ptotal = 0L;

        if (tax.getForward() == TaxConstanst.TAX_FORWARD_IN)
        {
            ptotal = sums[0] - sums[1];
        }
        else
        {
            ptotal = sums[1] - sums[0];
        }

        return ptotal;
    }

    public static long getLastMoney(TaxBean tax, long sumIn, long sumOut)
    {
        return getLastMoney(tax, new long[] {sumIn, sumOut});
    }
}
