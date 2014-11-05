/**
 * File Name: StorageLogDAO.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2010-8-25<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.product.dao;


import java.util.List;

import com.china.center.jdbc.inter.DAO;
import com.china.center.jdbc.util.ConditionParse;
import com.china.center.oa.product.bean.StorageLogBean;
import com.china.center.oa.product.vo.StorageLogVO;


/**
 * StorageLogDAO
 * 
 * @author ZHUZHU
 * @version 2010-8-25
 * @see StorageLogDAO
 * @since 1.0
 */
public interface StorageLogDAO extends DAO<StorageLogBean, StorageLogVO>
{
    List<StorageLogBean> queryStorageLogByCondition(ConditionParse condition);

    List<String> queryDistinctProductByDepotIdAndLogTime(String depotId, String logTime);
}
