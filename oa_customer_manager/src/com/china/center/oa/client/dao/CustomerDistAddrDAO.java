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
import com.china.center.oa.client.bean.CustomerDistAddrBean;
import com.china.center.oa.client.vo.CustomerDistAddrVO;


/**
 * CustomerDistAddrDAO
 * 
 * @author ZHUZHU
 * @version 2010-10-6
 * @see CustomerDistAddrDAO
 * @since 1.0
 */
public interface CustomerDistAddrDAO extends DAO<CustomerDistAddrBean, CustomerDistAddrVO>
{
	boolean updateValid(String id, int valid);
	
	List<CustomerDistAddrVO> queryEntityBeansByCidAndValid(String cid, int valid);
}
