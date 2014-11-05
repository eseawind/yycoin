/**
 * File Name: DepotDAOImpl.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2010-8-22<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.product.dao.impl;


import java.util.List;

import com.china.center.jdbc.inter.impl.BaseDAO;
import com.china.center.oa.product.bean.DepotBean;
import com.china.center.oa.product.dao.DepotDAO;
import com.china.center.oa.product.vo.DepotVO;


/**
 * DepotDAOImpl
 * 
 * @author ZHUZHU
 * @version 2010-8-22
 * @see DepotDAOImpl
 * @since 1.0
 */
public class DepotDAOImpl extends BaseDAO<DepotBean, DepotVO> implements DepotDAO
{

	public List<DepotBean> queryCommonDepotBean()
	{
		return this.queryEntityBeansByCondition("where status = 0");
	}
}
