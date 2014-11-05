package com.china.center.oa.project.dao;

import com.china.center.jdbc.inter.DAO;
import com.china.center.oa.project.bean.ProjectFlowBean;

public interface ProjectFlowDAO extends DAO<ProjectFlowBean, ProjectFlowBean>
{
	/**
     * findByFlowKeyAndNextStatus
     * 
     * @param flowKey
     * @param nextStatus
     * @return
     */
    ProjectFlowBean findByFlowKeyAndNextStatus(String flowKey, int nextStatus);
}
