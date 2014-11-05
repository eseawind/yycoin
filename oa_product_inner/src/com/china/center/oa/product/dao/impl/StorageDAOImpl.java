/**
 * File Name: StorageDAOImpl.java<br>
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
import com.china.center.oa.product.bean.StorageBean;
import com.china.center.oa.product.constant.DepotConstant;
import com.china.center.oa.product.dao.StorageDAO;
import com.china.center.oa.product.vo.StorageVO;
import com.china.center.tools.ListTools;


/**
 * StorageDAOImpl
 * 
 * @author ZHUZHU
 * @version 2010-8-22
 * @see StorageDAOImpl
 * @since 1.0
 */
public class StorageDAOImpl extends BaseDAO<StorageBean, StorageVO> implements StorageDAO
{
	
    public StorageBean findFristStorage(String depotpartId)
    {
        ConditionParse con = new ConditionParse();

        con.addCondition("StorageBean.depotpartId", "=", depotpartId);

        con.addCondition("order by StorageBean.id");

        List<StorageBean> list = this.queryEntityBeansByCondition(con);

        if (ListTools.isEmptyOrNull(list))
        {
            return null;
        }

        return list.get(0);
    }

}
