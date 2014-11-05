/**
 * File Name: ProductDAOImpl.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2010-8-15<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.project.dao.impl;


import java.util.List;

import com.china.center.jdbc.inter.impl.BaseDAO;
import com.china.center.jdbc.util.ConditionParse;
import com.china.center.oa.project.bean.ProjectBean;
import com.china.center.oa.project.dao.ProjectDAO;
import com.china.center.oa.project.vo.ProjectVO;



public class ProjectDAOImpl extends BaseDAO<ProjectBean, ProjectVO> implements ProjectDAO
{
    public boolean updateStatus(String id, int status)
    {
        return this.jdbcOperation.updateField("projectStatus", status, id, claz) > 0;
    }

    public ProjectBean findByName(String name)
    {
        ConditionParse con = new ConditionParse();
        con.addWhereStr();

        con.addCondition("projectName", "=", name);

        List<ProjectBean> projectList = queryEntityBeansByCondition(con.toString());

        if (projectList.size() > 0)
        {
            return projectList.get(0);
        }

        return null;
    }
}
