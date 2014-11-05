package com.china.center.oa.sail.dao;

import java.util.List;

import com.china.center.jdbc.inter.DAO;
import com.china.center.jdbc.util.ConditionParse;
import com.china.center.oa.sail.bean.AppOutBean;
import com.china.center.oa.sail.vo.AppOutVO;

public interface AppOutDAO extends DAO<AppOutBean, AppOutVO>
{
	List<AppOutBean> queryAppOut(ConditionParse con);
	
	List<AppOutBean> queryNotCreateOAOrder();
}
