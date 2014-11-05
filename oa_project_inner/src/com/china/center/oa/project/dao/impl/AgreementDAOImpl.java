package com.china.center.oa.project.dao.impl;

import java.util.List;

import com.china.center.jdbc.inter.impl.BaseDAO;
import com.china.center.jdbc.util.ConditionParse;
import com.china.center.oa.project.bean.AgreementBean;
import com.china.center.oa.project.dao.AgreementDAO;
import com.china.center.oa.project.vo.AgreementVO;

public class AgreementDAOImpl extends BaseDAO<AgreementBean, AgreementVO> implements AgreementDAO
{
	public boolean updateStatus(String id, int status)
    {
        return this.jdbcOperation.updateField("agreementStatus", status, id, claz) > 0;
    }

    public AgreementBean findByName(String name)
    {
        ConditionParse con = new ConditionParse();
        con.addWhereStr();

        con.addCondition("projectName", "=", name);

        List<AgreementBean> projectList = queryEntityBeansByCondition(con.toString());

        if (projectList.size() > 0)
        {
            return projectList.get(0);
        }

        return null;
    }
}
