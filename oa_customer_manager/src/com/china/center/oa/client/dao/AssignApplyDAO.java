/**
 * File Name: AssignApplyDAO.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2010-10-6<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.client.dao;


import com.china.center.jdbc.inter.DAO;
import com.china.center.oa.client.bean.AssignApplyBean;
import com.china.center.oa.client.vo.AssignApplyVO;


/**
 * AssignApplyDAO
 * 
 * @author ZHUZHU
 * @version 2010-10-6
 * @see AssignApplyDAO
 * @since 1.0
 */
public interface AssignApplyDAO extends DAO<AssignApplyBean, AssignApplyVO>
{
    boolean delAssignByCityId(String cityId);
}
