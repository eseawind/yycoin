package com.china.center.oa.client.dao.impl;

import java.util.List;

import com.china.center.jdbc.inter.impl.BaseDAO;
import com.china.center.jdbc.util.ConditionParse;
import com.china.center.oa.client.bean.AddressBean;
import com.china.center.oa.client.dao.AddressDAO;
import com.china.center.oa.client.vo.AddressVO;

public class AddressDAOImpl extends BaseDAO<AddressBean, AddressVO> implements AddressDAO
{

	public List<AddressBean> queryByCustomerId(String customerId)
	{
		ConditionParse condtion = new ConditionParse();

        condtion.addWhereStr();
        
        condtion.addCondition("AddressBean.CUSTOMERID", "=", customerId);
        
        return this.queryEntityBeansByCondition(condtion);
	}
	
}
