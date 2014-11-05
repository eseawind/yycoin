package com.china.center.oa.sail.dao.impl;

import com.china.center.jdbc.inter.impl.BaseDAO;
import com.china.center.oa.sail.bean.OutImportLogBean;
import com.china.center.oa.sail.constanst.OutImportConstant;
import com.china.center.oa.sail.dao.OutImportLogDAO;

public class OutImportLogDAOImpl extends BaseDAO<OutImportLogBean, OutImportLogBean> implements
		OutImportLogDAO
{

	public OutImportLogBean findByBatchIdAndStatus(String batchId, int status)
	{
		return this.findUnique("where batchId=? and status = ?", batchId, status);
	}

	public boolean updateStatus(String batchId, int status)
	{
		String sql = "update t_center_outimport_log set status=? where batchId=?";
		
		jdbcOperation.update(sql, OutImportConstant.LOGSTATUS_SUCCESSPREUSE, batchId);
		
		return true;
	}
	
}
