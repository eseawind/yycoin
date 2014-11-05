/**
 * File Name: ProviderDAO.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2010-8-21<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.product.dao;


import java.util.List;

import com.china.center.jdbc.inter.DAO;
import com.china.center.jdbc.util.ConditionParse;
import com.china.center.oa.product.bean.ProviderBean;
import com.china.center.oa.product.vo.ProviderVO;


/**
 * ProviderDAO
 * 
 * @author ZHUZHU
 * @version 2010-8-21
 * @see ProviderDAO
 * @since 1.0
 */
public interface ProviderDAO extends DAO<ProviderBean, ProviderVO>
{
    int countProviderInOut(String providerId);

    int countProviderByName(String name);

    List<ProviderBean> queryByLimit(ConditionParse condition, int limit);
    
    List<ProviderBean> findProviderByName(String name);
}
