/**
 * File Name: StorageRelationHelper.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2010-8-31<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.product.helper;


import com.china.center.oa.product.vs.StorageRelationBean;
import com.china.center.oa.product.wrap.ProductChangeWrap;
import com.china.center.tools.MathTools;


/**
 * StorageRelationHelper
 * 
 * @author ZHUZHU
 * @version 2010-8-31
 * @see StorageRelationHelper
 * @since 1.0
 */
public abstract class StorageRelationHelper
{
    /**
     * getPriceKey
     * 
     * @param value
     * @return
     */
    public static String getPriceKey(double value)
    {
        String formatNum = MathTools.formatNum(value);

        long last = Math.round( (Double.parseDouble(formatNum) * 100));

        return String.valueOf(last);
    }

    public static ProductChangeWrap createProductChangeWrap(StorageRelationBean bean)
    {
        ProductChangeWrap wrap = new ProductChangeWrap();

        wrap.setDepotpartId(bean.getDepotpartId());
        wrap.setProductId(bean.getProductId());
        wrap.setStorageId(bean.getStorageId());

        wrap.setPrice(bean.getPrice());

        return wrap;

    }
}
