/**
 * File Name: StafferVSPriBean.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2010-8-7<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.publics.dao;


import com.china.center.jdbc.inter.DAO;
import com.china.center.oa.publics.vo.StafferVSPriVO;
import com.china.center.oa.publics.vs.StafferVSPriBean;


/**
 * StafferVSPriBean
 * 
 * @author ZHUZHU
 * @version 2010-8-7
 * @see StafferVSPriDAO
 * @since 1.0
 */
public interface StafferVSPriDAO extends DAO<StafferVSPriBean, StafferVSPriVO>
{
    /**
     * 查询职员的区域属性
     * 
     * @param stafferId
     * @return
     */
    StafferVSPriVO findVOByStafferId(String stafferId);
}
