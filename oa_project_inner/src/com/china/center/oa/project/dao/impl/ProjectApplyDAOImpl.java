package com.china.center.oa.project.dao.impl;

import com.china.center.jdbc.inter.impl.BaseDAO;
import com.china.center.oa.project.bean.ProjectApplyBean;
import com.china.center.oa.project.dao.ProjectApplyDAO;
import com.china.center.oa.project.vo.ProjectApplyVO;

public class ProjectApplyDAOImpl extends BaseDAO<ProjectApplyBean, ProjectApplyVO> implements ProjectApplyDAO
{
	
	 public int updateStatus(String id, int status)
	    {
	        return this.jdbcOperation.updateField("status", status, id, this.claz);
	    }

}
