package com.china.center.oa.customerservice.dao;

import java.util.List;

import com.china.center.jdbc.inter.DAO;
import com.china.center.oa.customerservice.bean.FeedBackBean;
import com.china.center.oa.customerservice.vo.FeedBackVO;

public interface FeedBackDAO extends DAO<FeedBackBean, FeedBackVO>
{
	List<FeedBackBean> queryByTypeAndPstatus(int type, int pstatus);
	
	List<FeedBackBean> queryByTypeAndCustomerIdAndPstatus(int type, String customerId, int pstatus);
}
