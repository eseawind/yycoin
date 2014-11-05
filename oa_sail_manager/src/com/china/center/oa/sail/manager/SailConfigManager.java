/**
 * File Name: SailConfigManager.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2011-12-17<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.sail.manager;


import java.util.List;

import com.center.china.osgi.publics.User;
import com.china.center.common.MYException;
import com.china.center.oa.product.bean.ProductBean;
import com.china.center.oa.publics.bean.StafferBean;
import com.china.center.oa.sail.bean.SailConfBean;
import com.china.center.oa.sail.bean.SailConfigBean;
import com.china.center.oa.sail.vo.SailConfigVO;


/**
 * SailConfigManager
 * 
 * @author ZHUZHU
 * @version 2011-12-17
 * @see SailConfigManager
 * @since 3.0
 */
public interface SailConfigManager
{
    boolean addBean(User user, List<SailConfigBean> bean)
        throws MYException;

    boolean updateBean(User user, String pareId, List<SailConfigBean> bean)
        throws MYException;

    boolean deleteBean(User user, String pareId)
        throws MYException;

    SailConfigVO findVO(String id)
        throws MYException;

    boolean addBean(User user, SailConfBean bean)
        throws MYException;

    boolean updateBean(User user, SailConfBean bean)
        throws MYException;

    boolean deleteConf(User user, String id)
        throws MYException;

    /**
     * 查询配置
     * 
     * @param sb
     * @param productBean
     * @return
     */
    SailConfBean findProductConf(StafferBean sb, ProductBean productBean);
}
