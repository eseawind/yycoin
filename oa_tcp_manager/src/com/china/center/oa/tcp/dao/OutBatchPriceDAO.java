package com.china.center.oa.tcp.dao;

import com.china.center.jdbc.inter.DAO;
import com.china.center.oa.tcp.bean.OutBatchPriceBean;
import com.china.center.oa.tcp.vo.OutBatchPriceVO;

public interface OutBatchPriceDAO extends DAO<OutBatchPriceBean, OutBatchPriceVO>
{
	OutBatchPriceBean queryByProductIdAndChangeTime(String productId, String industryId, String changeTime);
}
