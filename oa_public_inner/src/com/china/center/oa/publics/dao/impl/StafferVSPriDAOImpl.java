/**
 * File Name: StafferVSPriDAOImpl.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2010-8-7<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.publics.dao.impl;


import java.util.List;

import com.china.center.jdbc.inter.impl.BaseDAO;
import com.china.center.oa.publics.dao.StafferVSPriDAO;
import com.china.center.oa.publics.vo.StafferVSPriVO;
import com.china.center.oa.publics.vs.StafferVSPriBean;


/**
 * StafferVSPriDAOImpl
 * 
 * @author ZHUZHU
 * @version 2010-8-7
 * @see StafferVSPriDAOImpl
 * @since 1.0
 */
public class StafferVSPriDAOImpl extends BaseDAO<StafferVSPriBean, StafferVSPriVO> implements StafferVSPriDAO
{
    public StafferVSPriVO findVOByStafferId(String stafferId)
    {
        List<StafferVSPriVO> voList = this.queryEntityVOsByFK(stafferId);

        if (voList.size() == 0)
        {
            return null;
        }

        return voList.get(0);
    }
}
