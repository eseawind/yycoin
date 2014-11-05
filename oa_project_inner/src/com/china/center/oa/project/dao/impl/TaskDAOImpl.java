package com.china.center.oa.project.dao.impl;

import java.util.List;
import java.util.Map;

import com.china.center.common.MYException;
import com.china.center.jdbc.inter.DAO;
import com.china.center.jdbc.inter.impl.BaseDAO;
import com.china.center.jdbc.util.ConditionParse;
import com.china.center.oa.project.bean.TaskBean;
import com.china.center.oa.project.constant.ProjectConstant;
import com.china.center.oa.project.dao.TaskDAO;
import com.china.center.oa.project.vo.TaskVO;

public class TaskDAOImpl extends BaseDAO<TaskBean, TaskVO> implements TaskDAO
{
	public boolean updateStatus(String id, int status)
    {
        return this.jdbcOperation.updateField("taskStatus", status, id, claz) > 0;
    }

    public TaskBean findByName(String name)
    {
        ConditionParse con = new ConditionParse();
        con.addWhereStr();

        con.addCondition("projectName", "=", name);

        List<TaskBean> projectList = queryEntityBeansByCondition(con.toString());

        if (projectList.size() > 0)
        {
            return projectList.get(0);
        }

        return null;
    }
    
 public List<TaskBean> queryTaskByConditions()
    throws MYException
    {
    	ConditionParse con = new ConditionParse();
    	
        con.addWhereStr();

        con.addCondition("taskStatus", "=", ProjectConstant.TASK_START);

        List<TaskBean> projectList = queryEntityBeansByCondition(con.toString());

        if (projectList.size() > 0)
        {
            return projectList;
        }
        return null;
    }
}
