package com.china.center.oa.sail.dao.impl;

import com.china.center.jdbc.inter.impl.BaseDAO;
import com.china.center.oa.sail.bean.ReplenishmentBean;
import com.china.center.oa.sail.dao.ReplenishmentDAO;

public class ReplenishmentDAOImpl extends BaseDAO<ReplenishmentBean, ReplenishmentBean> implements
		ReplenishmentDAO
{

	public ReplenishmentBean findByProductIdAndDepotpartIdAndOwner(
			String productId, String depotpartId, String owner)
	{
		return this.findUnique("where productId = ? and depotpartId = ? and owner = ?", productId, depotpartId, owner);
	}
	
}
