/**
 * File Name: DutyDAO.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2010-7-10<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.publics.dao;


import java.util.List;

import com.china.center.jdbc.inter.DAO;
import com.china.center.oa.publics.bean.DutyBean;
import com.china.center.oa.publics.bean.StafferBean;
import com.china.center.oa.publics.vo.DutyVO;
import com.china.center.tools.ListTools;


/**
 * DutyDAO
 * 
 * @author ZHUZHU
 * @version 2010-7-10
 * @see DutyDAO
 * @since 1.0
 */
public interface DutyDAO extends DAO<DutyBean, DutyVO>
{
	public DutyBean findyDutyByName(String name);
}
