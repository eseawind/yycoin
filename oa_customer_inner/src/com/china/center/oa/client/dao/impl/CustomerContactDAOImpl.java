/**
 * File Name: CustomerApplyDAOImpl.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2010-10-6<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.client.dao.impl;

import java.util.List;

import com.china.center.jdbc.inter.impl.BaseDAO;
import com.china.center.oa.client.bean.CustomerContactBean;
import com.china.center.oa.client.dao.CustomerContactDAO;
import com.china.center.tools.ListTools;

/**
 * CustomerApplyDAOImpl
 * 
 * @author ZHUZHU
 * @version 2010-10-6
 * @see CustomerContactDAOImpl
 * @since 1.0
 */
public class CustomerContactDAOImpl extends	BaseDAO<CustomerContactBean, CustomerContactBean> implements CustomerContactDAO 
{
	public boolean updateValid(String id, int valid)
	{
		int i = jdbcOperation.updateField("valid", valid, id, this.claz);

        return i != 0;
	}

	public List<CustomerContactBean> queryEntityBeansByCidAndValid(String cid, int valid)
	{
		return this.queryEntityBeansByCondition("where customerId = ? and valid = ?", cid, valid);
	}

	public CustomerContactBean findFirstValidBean(String cid)
	{
		List<CustomerContactBean> list = this.queryEntityBeansByCondition("where customerId = ? and valid = 0 order by id", cid);
		
		if (!ListTools.isEmptyOrNull(list))
			return list.get(0);
		
		return null;
	}

	@Override
	public boolean uniqueHandphone(String handPhone) {
		int i = this.countByCondition("where handphone = ?", handPhone);
		
		return i == 0;
	}
}
