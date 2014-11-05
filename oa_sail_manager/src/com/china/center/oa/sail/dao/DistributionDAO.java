package com.china.center.oa.sail.dao;

import com.china.center.jdbc.inter.DAO;
import com.china.center.oa.sail.bean.DistributionBean;
import com.china.center.oa.sail.vo.DistributionVO;

public interface DistributionDAO extends DAO<DistributionBean, DistributionVO>
{
	boolean updateBean(String id, DistributionBean bean);
	
	boolean updateOutboundDate(String id, String outboundDate);
}
