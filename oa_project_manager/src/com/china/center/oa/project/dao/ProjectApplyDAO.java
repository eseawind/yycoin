package com.china.center.oa.project.dao;

import com.china.center.jdbc.inter.DAO;
import com.china.center.oa.project.bean.ProjectApplyBean;
import com.china.center.oa.project.vo.ProjectApplyVO;

public interface ProjectApplyDAO  extends DAO<ProjectApplyBean, ProjectApplyVO>
{

	int updateStatus(String id, int status);
	
}
