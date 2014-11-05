package com.china.center.oa.customerservice.dao.impl;

import java.util.List;

import com.china.center.jdbc.inter.impl.BaseDAO;
import com.china.center.oa.customerservice.bean.FeedBackVisitBean;
import com.china.center.oa.customerservice.dao.FeedBackVisitDAO;
import com.china.center.tools.ListTools;

public class FeedBackVisitDAOImpl extends BaseDAO<FeedBackVisitBean, FeedBackVisitBean> implements
		FeedBackVisitDAO
{

	public FeedBackVisitBean findMaxLogTimeByTaskId(String taskId)
	{
	    List<FeedBackVisitBean> list = this.queryEntityBeansByCondition("where taskId = ? order by logTime desc",
	    		taskId);

	        if (ListTools.isEmptyOrNull(list))
	        {
	            return null;
	        }

	        return list.get(0);
	}

}
