package com.china.center.oa.project.dao;

import java.util.List;

import com.china.center.jdbc.inter.DAO;
import com.china.center.oa.project.bean.TaskMailPlanBean;

public interface TaskMailPlanDAO extends DAO<TaskMailPlanBean, TaskMailPlanBean>
{
	List<TaskMailPlanBean> queryByIdAndTypeAndStatus(String id, int type, int status);
	
	List<TaskMailPlanBean> queryByNeedMail();
}
