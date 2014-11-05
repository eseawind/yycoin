package com.china.center.oa.customerservice.dao;

import com.china.center.jdbc.inter.DAO;
import com.china.center.oa.customerservice.bean.ShortMessageBean;

public interface ShortMessageDAO extends DAO<ShortMessageBean, ShortMessageBean>
{
	boolean updateResult(String msgId, String mobile, int result, String description);
}
