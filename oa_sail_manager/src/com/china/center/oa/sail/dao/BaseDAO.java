/**
 * File Name: BaseDAO.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2010-11-7<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.sail.dao;


import java.util.List;

import com.china.center.common.MYException;
import com.china.center.jdbc.inter.DAO;
import com.china.center.jdbc.util.ConditionParse;
import com.china.center.jdbc.util.PageSeparate;
import com.china.center.oa.sail.bean.BaseBean;
import com.china.center.oa.sail.vo.BaseVO;


/**
 * BaseDAO
 * 
 * @author ZHUZHU
 * @version 2010-11-7
 * @see BaseDAO
 * @since 1.0
 */
public interface BaseDAO extends DAO<BaseBean, BaseVO>
{
    int countBaseByOutTime(String outTime);

    List<BaseBean> queryBaseByOutTime(String outTime, PageSeparate pageSeparate);

    boolean updateCostPricekey(String id, String costPricekey);

    boolean updateInvoice(String id, double invoiceMoney);
    
    List<BaseBean> queryBaseByConditions(String pa1,String pa2,String date)
    throws MYException;
    
    List<BaseBean> queryBaseByConditions3(String pa1,String pa2,String date)
    throws MYException;
    
    List<BaseBean> queryBaseByConditions4(String pa1,String pa2,String date)
    throws MYException;
    
    List<BaseBean> queryBaseByConditions1(String pa1,String pa2,String date)
    throws MYException;
    
    public List<BaseBean> queryBaseByConditions2(String pa1,String pa2,String date)
    throws MYException;
    
    List<BaseBean> queryBaseByOneCondition(ConditionParse con);
    
    List<BaseBean> queryBaseByDistribution(String distId);

    boolean updateLocationIdAndDepotpartByOutIdAndProductId(String locationId, String depotpartId, String depotpartName,
    		String outId, String productId);
}
