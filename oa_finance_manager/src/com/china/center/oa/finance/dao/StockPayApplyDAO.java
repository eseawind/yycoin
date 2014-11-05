/**
 * File Name: StockPayApplyDAO.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2011-2-17<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.finance.dao;


import java.util.List;

import com.china.center.jdbc.inter.DAO;
import com.china.center.oa.finance.bean.StockPayApplyBean;
import com.china.center.oa.finance.vo.StockPayApplyVO;
import com.china.center.oa.sail.wrap.ConfirmInsWrap;


/**
 * StockPayApplyDAO
 * 
 * @author ZHUZHU
 * @version 2011-2-17
 * @see StockPayApplyDAO
 * @since 3.0
 */
public interface StockPayApplyDAO extends DAO<StockPayApplyBean, StockPayApplyVO>
{
	boolean isExistsNotFinish(String id, String refId);
	
	List<ConfirmInsWrap> queryCanConfirmApply(String stafferId, String invoceId);
	
	boolean updateHasConfirm(String fullId, int hasConfirm);
    
    boolean updateHasConfirmMoney(String fullId, double confirmMoney);
    
    boolean hasCreateApply(String stockItemId);
}
