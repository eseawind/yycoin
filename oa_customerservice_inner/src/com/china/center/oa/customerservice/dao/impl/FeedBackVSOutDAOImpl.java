package com.china.center.oa.customerservice.dao.impl;

import java.util.List;

import com.china.center.jdbc.inter.impl.BaseDAO;
import com.china.center.oa.customerservice.bean.FeedBackVSOutBean;
import com.china.center.oa.customerservice.dao.FeedBackVSOutDAO;
import com.china.center.tools.ListTools;

public class FeedBackVSOutDAOImpl extends BaseDAO<FeedBackVSOutBean, FeedBackVSOutBean> implements
		FeedBackVSOutDAO
{
	public FeedBackVSOutBean findMaxchangeTimeByTaskId(String taskId)
	{
	    List<FeedBackVSOutBean> list = this.queryEntityBeansByCondition("where taskId = ? order by changeTime desc",
	    		taskId);

	        if (ListTools.isEmptyOrNull(list))
	        {
	            return null;
	        }

	        return list.get(0);
	}
}
