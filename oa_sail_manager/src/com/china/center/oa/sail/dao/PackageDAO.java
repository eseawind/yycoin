package com.china.center.oa.sail.dao;

import java.util.List;

import com.china.center.jdbc.inter.DAO;
import com.china.center.jdbc.util.ConditionParse;
import com.china.center.jdbc.util.PageSeparate;
import com.china.center.oa.sail.bean.PackageBean;
import com.china.center.oa.sail.vo.PackageVO;

public interface PackageDAO extends DAO<PackageBean, PackageVO>
{
	List<PackageVO> queryVOsByCondition(ConditionParse con);
	
	int countByCon(ConditionParse con);
	
	List<PackageVO> queryVOsByCon(ConditionParse con, PageSeparate page);
	
	
}
