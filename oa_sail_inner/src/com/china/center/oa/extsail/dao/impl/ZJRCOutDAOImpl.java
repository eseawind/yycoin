package com.china.center.oa.extsail.dao.impl;

import java.util.List;

import com.china.center.jdbc.inter.impl.BaseDAO;
import com.china.center.oa.extsail.bean.ZJRCOutBean;
import com.china.center.oa.extsail.dao.ZJRCOutDAO;
import com.china.center.oa.extsail.vo.ZJRCOutVO;

public class ZJRCOutDAOImpl extends BaseDAO<ZJRCOutBean, ZJRCOutVO> implements ZJRCOutDAO
{
	public List<ZJRCOutBean> queryNotCreateOA()
	{
		return this.queryEntityBeansByCondition("where status = 1");
	}

	@Override
	public boolean updateStatus(String fullId, int status)
	{
		jdbcOperation.updateField("status", status, fullId, this.claz);
		
		return true;
	}
	
}
