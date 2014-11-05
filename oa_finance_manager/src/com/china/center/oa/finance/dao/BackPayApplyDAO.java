/**
 * File Name: BackPayApplyDAO.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2011-3-3<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.finance.dao;


import com.china.center.jdbc.inter.DAO;
import com.china.center.oa.finance.bean.BackPayApplyBean;
import com.china.center.oa.finance.vo.BackPayApplyVO;


/**
 * BackPayApplyDAO
 * 
 * @author ZHUZHU
 * @version 2011-3-3
 * @see BackPayApplyDAO
 * @since 3.0
 */
public interface BackPayApplyDAO extends DAO<BackPayApplyBean, BackPayApplyVO>
{
    /**
     * updateRefIds 更新ref
     * 
     * @param id
     * @param refIds
     * @return
     */
    boolean updateRefIds(String id, String refIds);
    
    double sumMoneysStatusNotEnd(String billId);
}
