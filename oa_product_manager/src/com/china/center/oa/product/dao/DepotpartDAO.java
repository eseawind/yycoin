/**
 * File Name: DepotpartDAO.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2010-8-22<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.product.dao;


import java.util.List;

import com.china.center.jdbc.inter.DAO;
import com.china.center.oa.product.bean.DepotpartBean;
import com.china.center.oa.product.vo.DepotpartVO;


/**
 * DepotpartDAO
 * 
 * @author ZHUZHU
 * @version 2010-8-22
 * @see DepotpartDAO
 * @since 1.0
 */
public interface DepotpartDAO extends DAO<DepotpartBean, DepotpartVO>
{
    /**
     * queryOkDepotpartInDepot
     * 
     * @param depotId
     * @return
     */
    List<DepotpartBean> queryOkDepotpartInDepot(String depotId);

    /**
     * 获取第一个良品仓
     * 
     * @param depotId
     * @return
     */
    DepotpartBean findDefaultOKDepotpart(String depotId);
}
