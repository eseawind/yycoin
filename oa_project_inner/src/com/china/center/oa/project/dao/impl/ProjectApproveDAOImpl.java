package com.china.center.oa.project.dao.impl;

import java.util.List;

import com.china.center.jdbc.inter.impl.BaseDAO;
import com.china.center.oa.project.bean.ProjectApproveBean;
import com.china.center.oa.project.dao.ProjectApproveDAO;
import com.china.center.oa.project.vo.ProjectApproveVO;
import com.china.center.oa.publics.bean.StafferBean;
import com.china.center.tools.ListTools;

public class ProjectApproveDAOImpl extends BaseDAO<ProjectApproveBean, ProjectApproveVO> implements ProjectApproveDAO
{

	public ProjectApproveBean findProjectApproveByApproveId(String approveid)
	{
	        List<ProjectApproveBean> list = this.jdbcOperation.queryForList("where approverId = ?", claz, approveid);

	        if (ListTools.isEmptyOrNull(list) || list.size() != 1)
	        {
	            return null;
	        }

	        return list.get(0);
	}
}
