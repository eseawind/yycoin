/*
 * Copyright (C), 2002-2014, 苏宁易购电子商务有限公司
 * FileName: BackPrePayApplyDAOImpl.java
 * Author:   smart
 * Date:     2014年10月10日 下午3:19:32
 * Description: //模块目的、功能描述      
 * History: //修改记录
 * <author>      <time>      <version>    <desc>
 * 修改人姓名             修改时间            版本号                  描述
 */
package com.china.center.oa.finance.dao.impl;

import com.china.center.jdbc.inter.impl.BaseDAO;
import com.china.center.oa.finance.bean.BackPrePayApplyBean;
import com.china.center.oa.finance.dao.BackPrePayApplyDAO;
import com.china.center.oa.finance.vo.BackPrePayApplyVO;

/**
 * 〈功能详细描述〉
 *
 * @author smart
 * @since 20140923
 */
public class BackPrePayApplyDAOImpl extends BaseDAO<BackPrePayApplyBean, BackPrePayApplyVO> implements
		BackPrePayApplyDAO {
	
	public int updateStatus(String id, int status)
    {
        return this.jdbcOperation.updateField("status", status, id, this.claz);
    }

	/* (non-Javadoc)
	 * @see com.china.center.oa.finance.dao.BackPrePayApplyDAO#updateStatusAndNewBillId(java.lang.String, int, java.lang.String)
	 */
	@Override
	public int updateStatusAndNewBillId(String id, int status, String newBillId) {
		String sql = "update T_CENTER_BACKPREPAY_APPLY set status = ?, newBillId = ? where id = ?";

        int i = jdbcOperation.update(sql, status, newBillId, id);

        return i;
	}
}
