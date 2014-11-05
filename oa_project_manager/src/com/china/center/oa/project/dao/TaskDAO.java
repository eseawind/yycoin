package com.china.center.oa.project.dao;

import java.util.List;

import com.china.center.common.MYException;
import com.china.center.jdbc.inter.DAO;
import com.china.center.oa.project.bean.TaskBean;
import com.china.center.oa.project.vo.TaskVO;

public interface TaskDAO  extends DAO<TaskBean, TaskVO>
{
	boolean updateStatus(String id, int status);

	TaskBean findByName(String name);
	
	public List<TaskBean> queryTaskByConditions()
    throws MYException;
}
