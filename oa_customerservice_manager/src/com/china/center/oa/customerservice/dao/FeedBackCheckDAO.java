package com.china.center.oa.customerservice.dao;

import com.china.center.jdbc.inter.DAO;
import com.china.center.oa.customerservice.bean.FeedBackCheckBean;

public interface FeedBackCheckDAO extends DAO<FeedBackCheckBean, FeedBackCheckBean>
{
	FeedBackCheckBean findMaxLogTimeByTaskId(String taskId);
}
