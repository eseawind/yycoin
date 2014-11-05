package com.china.center.oa.sail.dao;

import com.china.center.jdbc.inter.DAO;
import com.china.center.oa.sail.bean.OutImportLogBean;

public interface OutImportLogDAO extends DAO<OutImportLogBean, OutImportLogBean>
{
	OutImportLogBean findByBatchIdAndStatus(String batchId, int status);
	
	boolean updateStatus(String batchId, int status);
}
