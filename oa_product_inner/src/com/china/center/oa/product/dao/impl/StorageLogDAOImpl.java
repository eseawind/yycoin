/**
 * File Name: StorageLogDAOImpl.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2010-8-25<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.product.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import org.springframework.jdbc.core.RowCallbackHandler;

import com.china.center.jdbc.inter.impl.BaseDAO;
import com.china.center.jdbc.util.ConditionParse;
import com.china.center.oa.product.bean.StorageLogBean;
import com.china.center.oa.product.dao.StorageLogDAO;
import com.china.center.oa.product.vo.StorageLogVO;

/**
 * StorageLogDAOImpl
 * 
 * @author ZHUZHU
 * @version 2010-8-25
 * @see StorageLogDAOImpl
 * @since 1.0
 */
public class StorageLogDAOImpl extends BaseDAO<StorageLogBean, StorageLogVO> implements
        StorageLogDAO {
    /**
     * queryStorageLogByCondition
     * 
     * @param condition
     * @return
     */
    public List<StorageLogBean> queryStorageLogByCondition(ConditionParse condition) {
        condition.addWhereStr();

        return jdbcOperation
                .queryForListBySql(
                        "select t1.*,t3.industryId, t3.industryId2 from T_CENTER_STORAGELOG t1, t_center_product t2, t_center_depot t3, t_center_storage t4 "
                                + condition.toString(), StorageLogBean.class);
    }

    public List<String> queryDistinctProductByDepotIdAndLogTime(String depotId, String logTime) {
        final List<String> result = new LinkedList<String>();

        String sql = "select distinct(productid) as productid from t_center_storagelog where locationId = ? and logTime >= ?";

        this.jdbcOperation.query(sql, new Object[] { depotId, logTime }, new RowCallbackHandler() {
            public void processRow(ResultSet rs) throws SQLException {
                result.add(rs.getString("productid"));
            }
        });

        return result;
    }
}
