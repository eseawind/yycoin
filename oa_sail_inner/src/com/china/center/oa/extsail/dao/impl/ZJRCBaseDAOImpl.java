package com.china.center.oa.extsail.dao.impl;

import com.china.center.jdbc.inter.impl.BaseDAO;
import com.china.center.oa.extsail.bean.ZJRCBaseBean;
import com.china.center.oa.extsail.dao.ZJRCBaseDAO;

public class ZJRCBaseDAOImpl extends BaseDAO<ZJRCBaseBean, ZJRCBaseBean> implements ZJRCBaseDAO
{
	public boolean updatePstatus(String id, int status)
	{
		this.jdbcOperation.updateField("pstatus", status, id, this.claz);
		
		return true;
	}

	@Override
	public boolean updateOAno(String id, String oano)
	{
		this.jdbcOperation.updateField("oano", oano, id, this.claz);
		
		return true;
	}
	
}
