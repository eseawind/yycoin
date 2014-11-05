/**
 * File Name: DepotBean.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2010-8-22<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.product.dao;


import java.util.List;

import com.china.center.jdbc.inter.DAO;
import com.china.center.oa.product.bean.DepotBean;
import com.china.center.oa.product.vo.DepotVO;


/**
 * DepotBean
 * 
 * @author ZHUZHU
 * @version 2010-8-22
 * @see DepotDAO
 * @since 1.0
 */
public interface DepotDAO extends DAO<DepotBean, DepotVO>
{
	List<DepotBean> queryCommonDepotBean();
}
