package com.china.center.oa.customerservice.dao.impl;

import java.util.List;

import com.china.center.jdbc.inter.impl.BaseDAO;
import com.china.center.oa.customerservice.bean.FeedBackCheckBean;
import com.china.center.oa.customerservice.dao.FeedBackCheckDAO;
import com.china.center.tools.ListTools;

public class FeedBackCheckDAOImpl extends BaseDAO<FeedBackCheckBean, FeedBackCheckBean> implements
		FeedBackCheckDAO
{
	public FeedBackCheckBean findMaxLogTimeByTaskId(String taskId)
	{
	    List<FeedBackCheckBean> list = this.queryEntityBeansByCondition("where taskId = ? order by logTime desc",
	    		taskId);

	        if (ListTools.isEmptyOrNull(list))
	        {
	            return null;
	        }

	        return list.get(0);
	}
}
