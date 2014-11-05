package com.china.center.oa.sail.dao.impl;

import com.china.center.jdbc.inter.impl.BaseDAO;
import com.china.center.oa.sail.bean.OutImportBean;
import com.china.center.oa.sail.dao.OutImportDAO;
import com.china.center.oa.sail.vo.OutImportVO;

public class OutImportDAOImpl extends BaseDAO<OutImportBean, OutImportVO> implements OutImportDAO
{

	public boolean updatePreUse(String id, int preUse)
	{
        jdbcOperation.updateField("preUse", preUse, id, this.claz);

        return true;
    }
	
}
