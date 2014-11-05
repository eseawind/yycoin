/**
 * File Name: StorageRelationDAOImpl.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2010-8-22<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.product.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.china.center.jdbc.annosql.tools.BeanTools;
import com.china.center.jdbc.inter.IbatisDaoSupport;
import com.china.center.jdbc.inter.impl.BaseDAO;
import com.china.center.jdbc.util.ConditionParse;
import com.china.center.oa.product.constant.DepotConstant;
import com.china.center.oa.product.dao.StorageRelationDAO;
import com.china.center.oa.product.vo.StorageRelationVO;
import com.china.center.oa.product.vs.StorageRelationBean;

/**
 * StorageRelationDAOImpl
 * 
 * @author ZHUZHU
 * @version 2010-8-22
 * @see StorageRelationDAOImpl
 * @since 1.0
 */
public class StorageRelationDAOImpl extends BaseDAO<StorageRelationBean, StorageRelationVO>
        implements StorageRelationDAO {
    private IbatisDaoSupport ibatisDaoSupport = null;

    public int sumAllProductInStorage(String storageId) {
        String sql = BeanTools.getSumHead(claz, "amount") + "where storageId = ?";

        return this.jdbcOperation.queryForInt(sql, storageId);
    }

    public int sumAllProductByProductId(String productId) {
        String sql = BeanTools.getSumHead(claz, "amount") + "where productId = ?";

        return this.jdbcOperation.queryForInt(sql, productId);
    }

    public StorageRelationBean findByDepotpartIdAndProductIdAndPriceKeyAndStafferId(
            String depotpartId, String productId, String priceKey, String stafferId) {
        return findUnique(
                "where depotpartId = ? and productId = ? and priceKey = ? and stafferId = ?",
                depotpartId, productId, priceKey, stafferId);
    }

    public int sumProductInDepotpartId(String productId, String depotpartId) {
        String sql = BeanTools.getSumHead(claz, "amount")
                + "where productId = ? and depotpartId = ?";

        return this.jdbcOperation.queryForInt(sql, productId, depotpartId);
    }

    public int sumProductInStorage(String productId, String storageId) {
        String sql = BeanTools.getSumHead(claz, "amount") + "where productId = ? and storageId = ?";

        return this.jdbcOperation.queryForInt(sql, productId, storageId);
    }

    public int sumProductInLocationId(String productId, String locationId) {
        String sql = BeanTools.getSumHead(claz, "amount")
                + "where productId = ? and locationId = ?";

        return this.jdbcOperation.queryForInt(sql, productId, locationId);
    }

    public int sumProductInDepotpartIdAndPriceKey(String productId, String depotpartId,
            String priceKey) {
        String sql = BeanTools.getSumHead(claz, "amount")
                + "where productId = ? and depotpartId = ? and priceKey = ?";

        return this.jdbcOperation.queryForInt(sql, productId, depotpartId, priceKey);
    }

    public int sumProductInLocationIdAndPriceKey(String productId, String locationId,
            String priceKey) {
        String sql = BeanTools.getSumHead(claz, "amount")
                + "where productId = ? and locationId = ? and priceKey = ?";

        return this.jdbcOperation.queryForInt(sql, productId, locationId, priceKey);
    }

    /**
     * queryStorageRelationByCondition
     * 
     * @param condition
     * @param isLimit
     * @return
     */
    public List<StorageRelationVO> queryStorageRelationByCondition(ConditionParse condition,
            boolean isLimit) {
        condition.removeWhereStr();

        String sql = "select t1.*, t0.name as productName, t0.code as productCode,t0.type as productType, t0.reserve4 as productMtype,"
                + " t3.name as depotpartName, "
                + "t2.name as storageName, t5.name as industryName, t6.name as industryName2,t4.name as locationName "
                + "from t_center_product t0, t_center_storageralation t1, t_center_storage t2, t_center_depotpart t3, " 
                + "t_center_depot t4, t_center_principalship t5, t_center_principalship t6 "
                + "where t0.id = t1.productId and t1.storageId = t2.id and t1.depotpartId = t3.id and " 
                + "t1.locationId = t4.id and t4.industryId = t5.id and t4.industryId2 = t6.id "
                + condition.toString() + " order by t1.amount desc,  t1.productId desc";

        if (isLimit) {
            return jdbcOperation.queryObjectsBySql(sql).setMaxResults(200)
                    .list(StorageRelationVO.class);
        } else {
            return jdbcOperation.queryForListBySql(sql, StorageRelationVO.class);
        }
    }

    public int updateStorageRelationAmount(String id, int change) {
        // 这里可以防止脏数据的读取
        String sql = BeanTools.getUpdateHead(claz) + "set amount = amount + ? where id = ?";

        jdbcOperation.update(sql, change, id);

        return change;
    }

    public int sumProductInOKLocationId(String productId, String locationId) {
        String sql = "select sum(t1.amount) from t_center_storageralation t1, "
                + "t_center_depotpart t2 "
                + "where t1.productId = ? and t1.locationId = ? and t1.depotpartId = t2.id and t2.type = ? ";

        return this.jdbcOperation.queryForInt(sql, productId, locationId,
                DepotConstant.DEPOTPART_TYPE_OK);
    }

    /**
     * @return the ibatisDaoSupport
     */
    public IbatisDaoSupport getIbatisDaoSupport() {
        return ibatisDaoSupport;
    }

    /**
     * @param ibatisDaoSupport the ibatisDaoSupport to set
     */
    public void setIbatisDaoSupport(IbatisDaoSupport ibatisDaoSupport) {
        this.ibatisDaoSupport = ibatisDaoSupport;
    }

    public List<StorageRelationVO> queryStorageRelationWithoutPrice(String depotId) {
        Map<String, String> paramterMap = new HashMap();

        paramterMap.put("depotId", depotId);

        List<StorageRelationVO> result = getIbatisDaoSupport().queryForList(
                "StorageRelationDAO.queryStorageRelationWithoutPrice", paramterMap);

        return result;
    }

    /**
     * 根据仓区、产品、产品所有者查询库存数大于0的数据
     * {@inheritDoc}
     */
	public List<StorageRelationBean> queryByDepotpartIdAndProductIdAndStafferId(
			String depotpartId, String productId, String stafferId)
	{
		ConditionParse con = new ConditionParse();
		
		con.addWhereStr();
		
		con.addCondition("StorageRelationBean.depotpartId", "=", depotpartId);
		
		con.addCondition("StorageRelationBean.productId", "=", productId);
		
		con.addCondition("StorageRelationBean.stafferId", "=", stafferId);
		
		con.addIntCondition("StorageRelationBean.amount", ">", 0);
		
		return this.queryEntityBeansByCondition(con);
	}
	
	public int sumByDepotpartIdAndProductIdAndStafferId(
			String depotpartId, String productId, String stafferId)
	{
        String sql = BeanTools.getSumHead(claz, "amount")
        + "where productId = ? and depotpartId = ? and stafferId = ?";

        return this.jdbcOperation.queryForInt(sql, productId, depotpartId, stafferId);
	}
	
    public int updateStorageRelationInputRate(String id, double inputRate) {
        // 这里可以防止脏数据的读取
        String sql = BeanTools.getUpdateHead(claz) + "set inputRate = ? where id = ?";

        int i = jdbcOperation.update(sql, inputRate, id);

        return i;
    }
}
