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
import com.china.center.oa.client.bean.CustomerBusinessBean;
import com.china.center.oa.client.dao.CustomerBusinessDAO;

/**
 * CustomerApplyDAOImpl
 * 
 * @author ZHUZHU
 * @version 2010-10-6
 * @see CustomerBusinessDAOImpl
 * @since 1.0
 */
public class CustomerBusinessDAOImpl extends
		BaseDAO<CustomerBusinessBean, CustomerBusinessBean> implements CustomerBusinessDAO 
{
	public boolean updateValid(String id, int valid)
	{
		int i = jdbcOperation.updateField("valid", valid, id, this.claz);

        return i != 0;
	}

	public List<CustomerBusinessBean> queryEntityBeansByCidAndValid(String cid, int valid)
	{
		return this.queryEntityVOsByCondition("where customerId = ? and valid = ?", cid, valid);
	}

}
