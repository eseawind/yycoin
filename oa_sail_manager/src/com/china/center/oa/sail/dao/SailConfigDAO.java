/**
 * File Name: SailConfigDAO.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2011-12-17<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.sail.dao;


import java.util.List;

import com.china.center.jdbc.inter.DAO;
import com.china.center.jdbc.util.ConditionParse;
import com.china.center.jdbc.util.PageSeparate;
import com.china.center.oa.sail.bean.SailConfigBean;
import com.china.center.oa.sail.vo.SailConfigVO;


/**
 * SailConfigDAO
 * 
 * @author ZHUZHU
 * @version 2011-12-17
 * @see SailConfigDAO
 * @since 3.0
 */
public interface SailConfigDAO extends DAO<SailConfigBean, SailConfigVO>
{
    /**
     * querySailConfigByConstion
     * 
     * @param condition
     * @param page
     * @return
     */
    List<SailConfigVO> querySailConfigByConstion(ConditionParse condition, PageSeparate page);

    /**
     * countSailConfigByConstion
     * 
     * @param condition
     * @return
     */
    int countSailConfigByConstion(ConditionParse condition);
}
