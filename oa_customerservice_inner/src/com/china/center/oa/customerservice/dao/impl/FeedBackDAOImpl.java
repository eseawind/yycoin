package com.china.center.oa.customerservice.dao.impl;

import java.util.List;

import com.china.center.jdbc.inter.impl.BaseDAO;
import com.china.center.oa.customerservice.bean.FeedBackBean;
import com.china.center.oa.customerservice.dao.FeedBackDAO;
import com.china.center.oa.customerservice.vo.FeedBackVO;

public class FeedBackDAOImpl extends BaseDAO<FeedBackBean, FeedBackVO> implements FeedBackDAO
{
	public List<FeedBackBean> queryByTypeAndPstatus(int type, int pstatus)
	{
		return this.queryEntityBeansByCondition("where type = ? and pstatus = ?", type, pstatus);
	}

	public List<FeedBackBean> queryByTypeAndCustomerIdAndPstatus(int type,
			String customerId, int pstatus)
	{
		return this.queryEntityBeansByCondition("where type = ? and customerid = ? and pstatus = ?", type, customerId, pstatus);
	}
}
