package com.china.center.oa.customerservice.dao;

import com.china.center.jdbc.inter.DAO;
import com.china.center.oa.customerservice.bean.FeedBackVSOutBean;

public interface FeedBackVSOutDAO extends DAO<FeedBackVSOutBean, FeedBackVSOutBean>
{
	FeedBackVSOutBean findMaxchangeTimeByTaskId(String taskId);
}
