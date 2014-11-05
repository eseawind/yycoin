package com.china.center.oa.sail.manager;

import java.util.List;

import com.china.center.common.MYException;
import com.china.center.jdbc.util.ConditionParse;
import com.china.center.oa.sail.bean.AppOutBean;
import com.china.center.oa.sail.wrap.OrderListResult;
import com.china.center.oa.sail.wrap.QueryActivityOutput;
import com.china.center.oa.sail.wrap.QueryPriceOutput;
import com.china.center.oa.sail.wrap.SingleOrderResult;
import com.china.center.oa.sail.wrap.Wrap;

public interface AppOutManager
{
	List<QueryPriceOutput> queryPrice(String userId, List<Wrap> products) throws MYException;
	
	QueryActivityOutput queryActivity(String activityId) throws MYException;
	
	SingleOrderResult createOrder(AppOutBean bean) throws MYException;
	
	List<OrderListResult> queryOrderList(ConditionParse con) throws MYException;
	
	AppOutBean queryOrderDetail(String orderNo) throws MYException;
	
	// task 
	void createOAOrder();
}
