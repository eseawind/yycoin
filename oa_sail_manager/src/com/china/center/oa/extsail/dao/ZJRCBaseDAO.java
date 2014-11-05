package com.china.center.oa.extsail.dao;

import com.china.center.jdbc.inter.DAO;
import com.china.center.oa.extsail.bean.ZJRCBaseBean;

public interface ZJRCBaseDAO extends DAO<ZJRCBaseBean, ZJRCBaseBean>
{
	boolean updatePstatus(String id, int status);
	
	boolean updateOAno(String id, String oano);
}
