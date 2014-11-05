package com.china.center.oa.sail.dao;

import com.china.center.jdbc.inter.DAO;
import com.china.center.oa.sail.bean.OutImportBean;
import com.china.center.oa.sail.vo.OutImportVO;

public interface OutImportDAO extends DAO<OutImportBean, OutImportVO>
{
	boolean updatePreUse(String id, int preUse);
}
