package com.china.center.oa.project.dao.impl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Iterator;
import java.util.List;

import com.china.center.jdbc.inter.impl.BaseDAO;
import com.china.center.oa.project.bean.TaskMailPlanBean;
import com.china.center.oa.project.dao.TaskMailPlanDAO;
import com.china.center.tools.TimeTools;

public class TaskMailPlanDAOImpl extends BaseDAO<TaskMailPlanBean, TaskMailPlanBean> implements
		TaskMailPlanDAO
{
	/**
	 * 
	 * {@inheritDoc}
	 */
	public List<TaskMailPlanBean> queryByIdAndTypeAndStatus(String id, int type, int status)
	{
		List<TaskMailPlanBean> list = this.queryEntityBeansByCondition("where id = ? and type = ? and status = ?", id, type, status);
		
		return list;
	}

	public List<TaskMailPlanBean> queryByNeedMail()
	{
		List<TaskMailPlanBean> list = this.queryEntityBeansByCondition("where status = ?", 0);
		
		DateFormat df = new SimpleDateFormat("yyyy-dd-MM hh:mm:ss");
		
		try
		{
			for (Iterator<TaskMailPlanBean> iterator = list.iterator(); iterator.hasNext();)
			{
				TaskMailPlanBean tmpBean = iterator.next();
				
				java.util.Date now = df.parse(TimeTools.now());
				
				java.util.Date planTime = df.parse(tmpBean.getPlanTime());
				
				if (now.getTime() < planTime.getTime())
				{
					iterator.remove();
				}
			}
		}
		catch(Exception e)
		{
			//
		}
		
		return list;
	}
}
