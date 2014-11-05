package com.china.center.oa.extsail.dao;

import java.util.List;

import com.china.center.jdbc.inter.DAO;
import com.china.center.oa.extsail.bean.ZJRCOutBean;
import com.china.center.oa.extsail.vo.ZJRCOutVO;

public interface ZJRCOutDAO extends DAO<ZJRCOutBean, ZJRCOutVO>
{
	List<ZJRCOutBean> queryNotCreateOA();
	
	boolean updateStatus(String fullId, int status);
}
