/**
 * File Name: ProductDAO.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2010-8-15<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.project.dao;


import com.china.center.jdbc.inter.DAO;
import com.china.center.oa.project.bean.ProjectBean;
import com.china.center.oa.project.vo.ProjectVO;


/**
 * ProductDAO
 * 
 * @author ZHUZHU
 * @version 2010-8-15
 * @see ProjectDAO
 * @since 1.0
 */
public interface ProjectDAO extends DAO<ProjectBean, ProjectVO>
{
    boolean updateStatus(String id, int status);

    ProjectBean findByName(String name);
}
