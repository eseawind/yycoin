package com.china.center.oa.customerservice.dao;

import com.china.center.jdbc.inter.DAO;
import com.china.center.oa.customerservice.bean.FeedBackVisitBean;

public interface FeedBackVisitDAO extends DAO<FeedBackVisitBean, FeedBackVisitBean>
{
	FeedBackVisitBean findMaxLogTimeByTaskId(String taskId);
}
