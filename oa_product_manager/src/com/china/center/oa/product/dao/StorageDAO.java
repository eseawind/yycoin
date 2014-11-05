/**
 * File Name: StorageDAO.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2010-8-22<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.product.dao;


import java.util.List;

import com.china.center.jdbc.inter.DAO;
import com.china.center.oa.product.bean.StorageBean;
import com.china.center.oa.product.vo.StorageVO;


/**
 * StorageDAO
 * 
 * @author ZHUZHU
 * @version 2010-8-22
 * @see StorageDAO
 * @since 1.0
 */
public interface StorageDAO extends DAO<StorageBean, StorageVO>
{

    StorageBean findFristStorage(String depotpartId);

}
