package com.china.center.oa.sail.dao;

import com.china.center.jdbc.inter.DAO;
import com.china.center.oa.sail.bean.ReplenishmentBean;

public interface ReplenishmentDAO extends DAO<ReplenishmentBean, ReplenishmentBean>
{
	ReplenishmentBean findByProductIdAndDepotpartIdAndOwner(String productId, String depotpartId, String owner);
}
