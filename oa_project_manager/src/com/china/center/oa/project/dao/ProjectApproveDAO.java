package com.china.center.oa.project.dao;

import com.china.center.jdbc.inter.DAO;
import com.china.center.oa.project.bean.ProjectApproveBean;
import com.china.center.oa.project.vo.ProjectApproveVO;

public interface ProjectApproveDAO extends DAO<ProjectApproveBean, ProjectApproveVO>
{

	ProjectApproveBean findProjectApproveByApproveId(String approveid);
}
