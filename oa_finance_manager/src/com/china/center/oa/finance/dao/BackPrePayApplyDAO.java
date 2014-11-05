/*
 * Copyright (C), 2002-2014, 苏宁易购电子商务有限公司
 * FileName: BackPrePayApplyDAO.java
 * Author:   smart
 * Date:     2014年10月10日 下午3:18:52
 * Description: //模块目的、功能描述      
 * History: //修改记录
 * <author>      <time>      <version>    <desc>
 * 修改人姓名             修改时间            版本号                  描述
 */
package com.china.center.oa.finance.dao;

import com.china.center.jdbc.inter.DAO;
import com.china.center.oa.finance.bean.BackPrePayApplyBean;
import com.china.center.oa.finance.vo.BackPrePayApplyVO;

/**
 * 〈功能详细描述〉
 *
 * @author smart
 * @since 20140923
 */
public interface BackPrePayApplyDAO extends DAO<BackPrePayApplyBean, BackPrePayApplyVO> {
	
	int updateStatus(String id, int status);
	
	int updateStatusAndNewBillId(String id, int status, String newBillId);
}
