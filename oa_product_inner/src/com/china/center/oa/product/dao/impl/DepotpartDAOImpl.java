/**
 * File Name: DepotpartDAOImpl.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2010-8-22<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.product.dao.impl;


import java.util.List;

import com.china.center.jdbc.inter.impl.BaseDAO;
import com.china.center.jdbc.util.ConditionParse;
import com.china.center.oa.product.bean.DepotpartBean;
import com.china.center.oa.product.constant.DepotConstant;
import com.china.center.oa.product.dao.DepotpartDAO;
import com.china.center.oa.product.vo.DepotpartVO;
import com.china.center.tools.ListTools;


/**
 * DepotpartDAOImpl
 * 
 * @author ZHUZHU
 * @version 2010-8-22
 * @see DepotpartDAOImpl
 * @since 1.0
 */
public class DepotpartDAOImpl extends BaseDAO<DepotpartBean, DepotpartVO> implements DepotpartDAO
{
    public List<DepotpartBean> queryOkDepotpartInDepot(String depotId)
    {
        ConditionParse condition = new ConditionParse();

        condition.addWhereStr();

        condition.addCondition("DepotpartBean.locationId", "=", depotId);

        condition.addIntCondition("DepotpartBean.type", "=", DepotConstant.DEPOTPART_TYPE_OK);

        return this.queryEntityBeansByCondition(condition);
    }

    public DepotpartBean findDefaultOKDepotpart(String depotId)
    {
        ConditionParse con = new ConditionParse();

        con.addCondition("DepotpartBean.locationId", "=", depotId);

        con.addCondition("order by DepotpartBean.id");

        List<DepotpartBean> list = this.queryEntityBeansByCondition(con);

        if (ListTools.isEmptyOrNull(list))
        {
            return null;
        }

        // 返回南京物流中心-物流中心库(销售可发)
        if (DepotConstant.CENTER_DEPOT_ID.equals(depotId))
        {
            for (DepotpartBean depotpartBean : list)
            {
                if ("1".equals(depotpartBean.getId()))
                {
                    return depotpartBean;
                }
            }
        }

        return list.get(0);
    }
}
