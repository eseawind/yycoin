/**
 * File Name: ComposeProductDAOImpl.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2010-10-2<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.product.dao.impl;


import java.util.List;

import com.china.center.jdbc.inter.impl.BaseDAO;
import com.china.center.oa.product.bean.ComposeProductBean;
import com.china.center.oa.product.dao.ComposeProductDAO;
import com.china.center.oa.product.vo.ComposeProductVO;
import com.china.center.tools.ListTools;


/**
 * ComposeProductDAOImpl
 * 
 * @author ZHUZHU
 * @version 2010-10-2
 * @see ComposeProductDAOImpl
 * @since 1.0
 */
public class ComposeProductDAOImpl extends BaseDAO<ComposeProductBean, ComposeProductVO> implements ComposeProductDAO
{
    public boolean updateStatus(String id, int status)
    {
        return jdbcOperation.updateField("status", status, id, claz) > 0;
    }

    public boolean updateLogTime(String id, String logTime)
    {
        return jdbcOperation.updateField("logTime", logTime, id, claz) > 0;
    }

	public ComposeProductBean queryLatestByProduct(String productId)
	{
		List<ComposeProductBean> list = this.queryEntityBeansByCondition("where productId=? and status = 3 order by logTime DESC", productId);
		
		if (ListTools.isEmptyOrNull(list))
			return null;
		else
			return list.get(0);
	}
	
	public boolean updateHybrid(String id, int hybrid)
    {
        return jdbcOperation.updateField("hybrid", hybrid, id, claz) > 0;
    }
}
