package com.china.center.oa.project.dao.impl;

import com.china.center.jdbc.inter.impl.BaseDAO;
import com.china.center.oa.project.bean.ProjectFlowBean;
import com.china.center.oa.project.dao.ProjectFlowDAO;

public class ProjectFlowDAOImpl extends BaseDAO<ProjectFlowBean, ProjectFlowBean> implements ProjectFlowDAO
{

	 public ProjectFlowBean findByFlowKeyAndNextStatus(String flowKey, int nextStatus)
	    {
	        return this.findUnique("where flowKey = ? and nextStatus = ?", flowKey, nextStatus);
	    }
}
