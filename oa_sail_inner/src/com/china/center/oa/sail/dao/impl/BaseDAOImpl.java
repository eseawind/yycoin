/**
 * File Name: BaseDAOImpl.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2010-11-7<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.sail.dao.impl;


import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.china.center.common.MYException;
import com.china.center.jdbc.annosql.tools.BeanTools;
import com.china.center.jdbc.util.ConditionParse;
import com.china.center.jdbc.util.PageSeparate;
import com.china.center.oa.sail.bean.BaseBean;
import com.china.center.oa.sail.dao.BaseDAO;
import com.china.center.oa.sail.vo.BaseVO;


/**
 * BaseDAOImpl
 * 
 * @author ZHUZHU
 * @version 2010-11-7
 * @see BaseDAOImpl
 * @since 1.0
 */
public class BaseDAOImpl extends com.china.center.jdbc.inter.impl.BaseDAO<BaseBean, BaseVO> implements BaseDAO
{
	protected final Log _logger = LogFactory.getLog(getClass());
	
    public int countBaseByOutTime(String outTime)
    {
        return this.jdbcOperation
            .queryForInt(
                "select count(1) from t_center_base t1, t_center_out t2  where t1.outid = t2.fullid and t2.outtime >= ?",
                outTime);
    }
    
    public List<BaseBean> queryBaseByConditions(String pa1,String pa2,String date)
    throws MYException
    {
    	String sql = "select BaseBean.* from T_CENTER_BASE BaseBean,  t_center_out O  " +
    			"where BaseBean.OUTID=O.fullid and O.status in(3,4) and O.pay=0 and O.outType=0 and O.outTime between date_sub(now(),interval 6 month) and now() and O.customerId="+pa1+" and BaseBean.productId =? order by O.outTime";
        List<BaseBean> list = this.jdbcOperation.queryForListBySql(sql,BaseBean.class, pa2);
        if(list !=null && list.size() > 0 )
        {
        	return list;
        }
        return null;
    }
    
    public List<BaseBean> queryBaseByConditions2(String pa1,String pa2,String date)
    throws MYException
    {
    	String sql = "select BaseBean.* from T_CENTER_BASE BaseBean,  t_center_out O  " +
    			"where BaseBean.OUTID=O.fullid and O.status in(3,4) and O.pay=1 and O.outType=0 and O.outTime between date_sub(now(),interval 6 month) and now() and O.customerId="+pa1+" and BaseBean.productId =? order by O.outTime desc";
        List<BaseBean> list = this.jdbcOperation.queryForListBySql(sql,BaseBean.class, pa2);
        if(list !=null && list.size() > 0 )
        {
        	return list;
        }
        return null;
    }
    
    public List<BaseBean> queryBaseByConditions3(String pa1,String pa2,String date)
    throws MYException
    {
    	String sql = "select BaseBean.* from T_CENTER_BASE BaseBean,  t_center_out O  " +
		"where BaseBean.OUTID=O.fullid and O.status in(3,4) and O.pay=0 and O.outType=0 and O.outTime>'2011-04-01' and O.outTime<='"+date+"' and O.customerId="+pa1+" and BaseBean.productId =? order by O.outTime";
		List<BaseBean> list = this.jdbcOperation.queryForListBySql(sql,BaseBean.class, pa2);
		if(list !=null && list.size() > 0 )
		{
			return list;
		}
		return null;
    }
    
    public List<BaseBean> queryBaseByConditions4(String pa1,String pa2,String date)
    throws MYException
    {
    	String sql = "select BaseBean.* from T_CENTER_BASE BaseBean,  t_center_out O  " +
		"where BaseBean.OUTID=O.fullid and O.status in(3,4) and O.pay=1 and O.outType=0 and O.outTime>'2011-04-01' and O.outTime<='"+date+"' and O.customerId="+pa1+" and BaseBean.productId =? order by O.outTime desc";
		List<BaseBean> list = this.jdbcOperation.queryForListBySql(sql,BaseBean.class, pa2);
		if(list !=null && list.size() > 0 )
		{
			return list;
		}
		return null;
    }
    
    public List<BaseBean> queryBaseByConditions1(String pa1,String pa2,String date)
    throws MYException
    {
    	String sql = "select BaseBean.* from T_CENTER_BASE BaseBean,  t_center_out O  " +
    			"where BaseBean.OUTID=O.fullid and O.outType=5 and O.outTime>'2011-04-01' and O.outTime<'"+date+"'  and O.customerId="+pa1+" and BaseBean.productId =?";
        List<BaseBean> list = this.jdbcOperation.queryForListBySql(sql,BaseBean.class, pa2);
        if(null !=list && list.size() > 0 )
        {
        	return list;
        }
        return null;
    }

    public List<BaseBean> queryBaseByOutTime(String outTime, PageSeparate pageSeparate)
    {
        String sql = "select t1.* from t_center_base t1, t_center_out t2  where t1.outid = t2.fullid and t2.outtime >= ?";

        return this.jdbcOperation.queryObjectsBySqlAndPageSeparate(sql, pageSeparate,
            BaseBean.class, outTime);
    }

    public boolean updateCostPricekey(String id, String costPricekey)
    {
        return this.jdbcOperation.updateField("costPriceKey", costPricekey, id, claz) > 0;
    }

    public boolean updateInvoice(String id, double invoiceMoney)
    {
        String sql = BeanTools.getUpdateHead(claz) + "set invoiceMoney = ? where id = ?";

        jdbcOperation.update(sql, invoiceMoney, id);

        return true;
    }

	public List<BaseBean> queryBaseByOneCondition(ConditionParse con)
	{
		String sql = "select BaseBean.* from T_CENTER_BASE BaseBean, t_center_out OutBean where BaseBean.outid = OutBean.fullid " + con.toString();
		
		return this.jdbcOperation.queryForListBySql(sql, claz);
	}

	public List<BaseBean> queryBaseByDistribution(String distId)
	{
		String sql = "select BaseBean.* from T_CENTER_BASE BaseBean, T_CENTER_DISTBASE distBaseBean where BaseBean.id = distBaseBean.baseid and distBaseBean.refId = ? " ;
		
		return this.jdbcOperation.queryForListBySql(sql, claz, distId);
	}

	/* (non-Javadoc)
	 * @see com.china.center.oa.sail.dao.BaseDAO#updateLocationIdAndDepotpartByOutIdAndProductId(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public boolean updateLocationIdAndDepotpartByOutIdAndProductId(
			String locationId, String depotpartId, String depotpartName,
			String outId, String productId) {

        String sql = "update t_center_base set locationId = ?, depotpartId = ? , depotpartName = ? where outid = ? and productid = ?";

        int i = jdbcOperation.update(sql, locationId, depotpartId, depotpartName, outId, productId);

        return i != 0;
	}
}
