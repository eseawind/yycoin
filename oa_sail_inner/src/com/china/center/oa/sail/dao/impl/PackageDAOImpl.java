package com.china.center.oa.sail.dao.impl;

import java.util.List;

import com.china.center.jdbc.inter.impl.BaseDAO;
import com.china.center.jdbc.util.ConditionParse;
import com.china.center.jdbc.util.PageSeparate;
import com.china.center.oa.sail.bean.PackageBean;
import com.china.center.oa.sail.dao.PackageDAO;
import com.china.center.oa.sail.vo.PackageVO;

public class PackageDAOImpl extends BaseDAO<PackageBean, PackageVO> implements PackageDAO
{

	public List<PackageVO> queryVOsByCondition(ConditionParse con)
	{
		return this.queryEntityVOsByCondition(con);
	}

	public int countByCon(ConditionParse con)
	{
		String sql = "select count(distinct PackageBean.id) from t_center_package PackageBean, T_CENTER_PACKAGE_ITEM PackageItemBean where PackageBean.id = PackageItemBean.packageId ";
		
		return this.jdbcOperation.queryForInt(sql + con.toString());
	}

	public List<PackageVO> queryVOsByCon(ConditionParse con,
			PageSeparate page)
	{
		String sql = "SELECT distinct CustomerBean.NAME AS customerName, e1.NAME AS transportName1, e2.NAME AS transportName2, PrincipalshipBean.NAME AS locationName, PackageBean.ID, PackageBean.CUSTOMERID, PackageBean.SHIPPING, PackageBean.TRANSPORT1, PackageBean.EXPRESSPAY, PackageBean.TRANSPORT2, PackageBean.TRANSPORTPAY, PackageBean.ADDRESS, PackageBean.RECEIVER, PackageBean.MOBILE, PackageBean.AMOUNT, PackageBean.PRODUCTCOUNT, PackageBean.TOTAL, PackageBean.STATUS, PackageBean.STAFFERNAME, PackageBean.INDUSTRYNAME, PackageBean.DEPARTNAME, PackageBean.LOCATIONID, PackageBean.LOGTIME, PackageBean.PICKUPID, PackageBean.INDEX_POS, PackageBean.SHIPTIME ";
		
		sql += " FROM T_CENTER_PACKAGE PackageBean  LEFT OUTER JOIN T_CENTER_EXPRESS e1 ON (PackageBean.transport1 = e1.id )  LEFT OUTER JOIN T_CENTER_EXPRESS e2 ON (PackageBean.transport2 = e2.id )  LEFT OUTER JOIN T_CENTER_CUSTOMER_MAIN CustomerBean ON (PackageBean.customerId = CustomerBean.id )  LEFT OUTER JOIN T_CENTER_PRINCIPALSHIP PrincipalshipBean ON (PackageBean.locationId = PrincipalshipBean.id ), T_CENTER_PACKAGE_ITEM PackageItemBean";
		
		sql += " where PackageBean.id = PackageItemBean.packageId ";
		
		sql += con.toString();
		
		return this.jdbcOperation.queryObjectsBySqlAndPageSeparate(sql, page, this.clazVO);
	}
	
}
