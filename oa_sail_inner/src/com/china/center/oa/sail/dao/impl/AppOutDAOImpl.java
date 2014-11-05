package com.china.center.oa.sail.dao.impl;

import java.util.List;

import com.china.center.jdbc.inter.impl.BaseDAO;
import com.china.center.jdbc.util.ConditionParse;
import com.china.center.oa.sail.bean.AppOutBean;
import com.china.center.oa.sail.dao.AppOutDAO;
import com.china.center.oa.sail.vo.AppOutVO;

public class AppOutDAOImpl extends BaseDAO<AppOutBean, AppOutVO> implements AppOutDAO
{
	public List<AppOutBean> queryAppOut(ConditionParse con)
	{
		String sql = "select distinct appout.* from t_center_appout appout, t_center_appbase appbase where appout.orderNo = appbase.orderNo ";
		
		sql += con.toString() + " order by appout.outtime desc";
		
		return this.jdbcOperation.queryForListBySql(sql, claz);
	}

	public List<AppOutBean> queryNotCreateOAOrder()
	{
		return this.queryEntityBeansByCondition("where ostatus = 0");
	}
	
}
