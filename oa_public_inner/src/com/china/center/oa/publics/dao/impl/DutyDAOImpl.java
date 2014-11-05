/**
 * File Name: DutyDAOImpl.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2010-7-10<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.publics.dao.impl;


import java.util.List;

import com.china.center.jdbc.inter.impl.BaseDAO;
import com.china.center.oa.publics.bean.DutyBean;
import com.china.center.oa.publics.bean.StafferBean;
import com.china.center.oa.publics.dao.DutyDAO;
import com.china.center.oa.publics.vo.DutyVO;
import com.china.center.tools.ListTools;


/**
 * DutyDAOImpl
 * 
 * @author ZHUZHU
 * @version 2010-7-10
 * @see DutyDAOImpl
 * @since 1.0
 */
public class DutyDAOImpl extends BaseDAO<DutyBean, DutyVO> implements DutyDAO
{
	
	public DutyBean findyDutyByName(String name)
    {
        List<DutyBean> list = this.jdbcOperation.queryForList("where name = ?", claz, name);

        if (ListTools.isEmptyOrNull(list) || list.size() != 1)
        {
            return null;
        }

        return list.get(0);
    }
}
