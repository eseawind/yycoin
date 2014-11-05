package com.china.center.oa.tcp.dao;

import com.china.center.jdbc.inter.DAO;
import com.china.center.oa.tcp.bean.RebateApplyBean;
import com.china.center.oa.tcp.vo.RebateApplyVO;

public interface RebateApplyDAO extends DAO<RebateApplyBean, RebateApplyVO>
{
	int updateStatus(String id, int status);
}
