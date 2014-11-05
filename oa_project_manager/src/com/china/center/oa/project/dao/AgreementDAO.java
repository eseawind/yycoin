package com.china.center.oa.project.dao;

import com.china.center.jdbc.inter.DAO;
import com.china.center.oa.project.bean.AgreementBean;
import com.china.center.oa.project.vo.AgreementVO;

public interface AgreementDAO extends DAO<AgreementBean, AgreementVO>
{
	boolean updateStatus(String id, int status);

	AgreementBean findByName(String name);
}
