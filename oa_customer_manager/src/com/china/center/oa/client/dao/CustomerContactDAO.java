/**
 * File Name: CustomerApplyDAO.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2010-10-6<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.client.dao;


import java.util.List;

import com.china.center.jdbc.inter.DAO;
import com.china.center.oa.client.bean.CustomerContactBean;


/**
 * CustomerApplyDAO
 * 
 * @author ZHUZHU
 * @version 2010-10-6
 * @see CustomerContactDAO
 * 
 * @since 1.0
 */
public interface CustomerContactDAO extends DAO<CustomerContactBean, CustomerContactBean>
{
	boolean updateValid(String id, int valid);
	
	List<CustomerContactBean> queryEntityBeansByCidAndValid(String cid, int valid);
	
	/**
	 * 获取有效信息中第一条
	 */
	CustomerContactBean findFirstValidBean(String cid);
	
	boolean uniqueHandphone(String handPhone);
	
}
