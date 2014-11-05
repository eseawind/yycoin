package com.china.center.oa.customerservice.manager;

import com.center.china.osgi.publics.User;
import com.china.center.common.MYException;
import com.china.center.oa.customerservice.bean.FeedBackCheckBean;
import com.china.center.oa.customerservice.bean.FeedBackVisitBean;
import com.china.center.oa.customerservice.vo.FeedBackVO;

public interface CustomerServiceManager
{
	/**
	 * 统计回访数据 每天统计前三天库管通过时间的销售 
	 */
	void statFeedBackVisit();
	
	/**
	 * 统计对账数据 每月3号出
	 */
	void statFeedBackCheck();
	
	boolean allocationTasks(User user, String ids, String destStafferId)
	throws MYException;
	
	boolean acceptTasks(User user, String ids)
	throws MYException;
	
	boolean rejectTasks(User user, String ids)
	throws MYException;
	
	boolean addFeedBackVisit(User user, FeedBackVisitBean visitBean)
	throws MYException;
	
	boolean updateFeedBackVisit(User user, FeedBackVisitBean visitBean)
	throws MYException;
	
	boolean addFeedBackCheck(User user, FeedBackCheckBean checkBean)
	throws MYException;
	
	boolean updateFeedBackCheck(User user, FeedBackCheckBean visitBean)
	throws MYException;
	
	boolean mailAttachment(User user, FeedBackVO bean)
	throws MYException;
	
	String downAttachment(User user, FeedBackVO bean)
	throws MYException;
	
	boolean addSingleCustomerVisit(User user, String customerId)
	throws MYException;
}
