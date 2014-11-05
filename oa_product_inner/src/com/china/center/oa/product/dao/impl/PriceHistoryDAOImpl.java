/**
 * File Name: PriceHistoryDAOImpl.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2010-10-5<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.product.dao.impl;


import java.util.List;

import com.china.center.jdbc.inter.impl.BaseDAO;
import com.china.center.jdbc.util.ConditionParse;
import com.china.center.oa.product.bean.PriceHistoryBean;
import com.china.center.oa.product.dao.PriceHistoryDAO;
import com.china.center.oa.product.vo.PriceHistoryVO;
import com.china.center.tools.ListTools;


/**
 * PriceHistoryDAOImpl
 * 
 * @author ZHUZHU
 * @version 2010-10-5
 * @see PriceHistoryDAOImpl
 * @since 1.0
 */
public class PriceHistoryDAOImpl extends BaseDAO<PriceHistoryBean, PriceHistoryVO> implements PriceHistoryDAO
{
    public PriceHistoryBean findLastByProductId(String productId)
    {
        ConditionParse condtition = new ConditionParse();

        condtition.addCondition("PriceHistoryBean.productId", "=", productId);

        condtition.addCondition("order by PriceHistoryBean.id desc");

        List<PriceHistoryBean> list = this.queryEntityBeansByLimit(condtition, 1);

        if (ListTools.isEmptyOrNull(list))
        {
            return null;
        }

        return list.get(0);
    }
}
