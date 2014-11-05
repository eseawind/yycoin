package com.china.center.oa.customerservice.manager;

import java.util.List;

import com.center.china.osgi.publics.User;
import com.china.center.common.MYException;
import com.china.center.oa.customerservice.bean.ShortMessageBean;

public interface ShortMessageManager
{
	/**
	 * 批量短信发送
	 * 
	 * @param user
	 * @param smbList
	 * @return
	 * 			批次号
	 * @throws MYException
	 */
	String sendShortMessage(User user, List<ShortMessageBean> smbList) throws MYException;
	
	/**
	 * 单条短信发送
	 * @param user
	 * @param bean
	 * @return
	 * 			
	 * @throws MYException
	 */
	String sendShortMessage(User user, ShortMessageBean bean) throws MYException;
	
	/**
	 * 定时任务   轮循检查短信发送状态
	 * 
	 * 短信接口批量返回
	 * @throws MYException
	 */
	void checkShortMessageStatus() throws MYException;
	
	/**
	 * 定时任务，轮循
	 * 
	 * 短信接口批量返回
	 * 
	 * @throws MYException
	 */
	void getShortMessageReply() throws MYException;
}
