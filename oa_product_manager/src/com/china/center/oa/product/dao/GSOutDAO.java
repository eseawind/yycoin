package com.china.center.oa.product.dao;

import com.china.center.jdbc.inter.DAO;
import com.china.center.oa.product.bean.GSOutBean;
import com.china.center.oa.product.vo.GSOutVO;

public interface GSOutDAO extends DAO<GSOutBean, GSOutVO>
{
	boolean modifyStatus(String id, int status);
}
