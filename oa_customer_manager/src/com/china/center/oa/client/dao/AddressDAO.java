package com.china.center.oa.client.dao;

import java.util.List;

import com.china.center.jdbc.inter.DAO;
import com.china.center.oa.client.bean.AddressBean;
import com.china.center.oa.client.vo.AddressVO;

public interface AddressDAO extends DAO<AddressBean, AddressVO>
{

	List<AddressBean> queryByCustomerId(String customerId);
}
