/**
 * File Name: PrincipalshipDAOImpl.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2010-6-21<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.publics.dao.impl;


import java.util.ArrayList;
import java.util.List;

import com.china.center.jdbc.inter.impl.BaseDAO;
import com.china.center.jdbc.util.ConditionParse;
import com.china.center.oa.publics.bean.PrincipalshipBean;
import com.china.center.oa.publics.constant.OrgConstant;
import com.china.center.oa.publics.dao.PrincipalshipDAO;


/**
 * PrincipalshipDAOImpl
 * 
 * @author ZHUZHU
 * @version 2010-6-21
 * @see PrincipalshipDAOImpl
 * @since 1.0
 */
public class PrincipalshipDAOImpl extends BaseDAO<PrincipalshipBean, PrincipalshipBean> implements PrincipalshipDAO
{
    public List<PrincipalshipBean> querySubPrincipalship(String id)
    {
        String sql = "select t1.* from T_CENTER_PRINCIPALSHIP t1, t_center_org t2 where t1.id = t2.subid and t2.PARENTID = ? and t1.status = 0";

        return jdbcOperation.queryForListBySql(sql, claz, id);
    }

    public List<PrincipalshipBean> listSYBSubPrincipalship()
    {
        List<PrincipalshipBean> result = new ArrayList<PrincipalshipBean>();

        List<PrincipalshipBean> sybParent = queryEntityBeansByFK(OrgConstant.ORG_BIG_DEPARTMENT);

        for (PrincipalshipBean principalshipBean : sybParent)
        {
            List<PrincipalshipBean> subList = queryEntityBeansByFK(principalshipBean.getId());

            for (PrincipalshipBean principalshipBean2 : subList)
            {
                principalshipBean2.setName(principalshipBean.getName() + "-->"
                                           + principalshipBean2.getName());
            }

            result.addAll(subList);
        }

        return result;
    }

    public PrincipalshipBean findUniqueByName(String name)
    {
        ConditionParse con = new ConditionParse();
        con.addWhereStr();

        con.addCondition("name", "=", name);

        List<PrincipalshipBean> list = queryEntityBeansByCondition(con.toString());

        if (list.size() > 0)
        {
            return list.get(0);
        }

        return null;
    }
    
    public List<PrincipalshipBean> queryPrincipalshipByCondition1()
    {
        String sql = "select * from T_CENTER_PRINCIPALSHIP where parentid " +
        		"in(select id from T_CENTER_PRINCIPALSHIP where parentid=3) and level!=3 and status=0";

            return jdbcOperation.queryForListBySql(sql, PrincipalshipBean.class);
    }
}
