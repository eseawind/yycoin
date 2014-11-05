/**
 * File Name: TravelApplyManager.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2011-7-10<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.tcp.manager;


import com.center.china.osgi.publics.ListenerManager;
import com.center.china.osgi.publics.User;
import com.china.center.common.MYException;
import com.china.center.oa.tcp.bean.ExpenseApplyBean;
import com.china.center.oa.tcp.listener.TcpPayListener;
import com.china.center.oa.tcp.vo.ExpenseApplyVO;
import com.china.center.oa.tcp.wrap.TcpParamWrap;


/**
 * TravelApplyManager
 * 
 * @author ZHUZHU
 * @version 2011-7-10
 * @see ExpenseManager
 * @since 3.0
 */
public interface ExpenseManager extends ListenerManager<TcpPayListener>
{
    /**
     * 增加差旅费申请及借款
     * 
     * @param user
     * @param bean
     * @return
     * @throws MYException
     */
    boolean addExpenseBean(User user, ExpenseApplyBean bean)
        throws MYException;

    /**
     * 修改差旅费申请及借款
     * 
     * @param user
     * @param bean
     * @return
     * @throws MYException
     */
    boolean updateExpenseBean(User user, ExpenseApplyBean bean)
        throws MYException;

    /**
     * 删除
     * 
     * @param user
     * @param id
     * @return
     * @throws MYException
     */
    boolean deleteExpenseBean(User user, String id)
        throws MYException;

    /**
     * findVO
     * 
     * @param id
     * @return
     */
    ExpenseApplyVO findVO(String id);

    /**
     * 提交
     * 
     * @param user
     * @param id
     * @return
     * @throws MYException
     */
    boolean submitExpenseBean(User user, String id, String processId)
        throws MYException;

    /**
     * 通过
     * 
     * @param user
     * @param id
     * @param processId
     * @param reason
     * @return
     * @throws MYException
     */
    boolean passExpenseBean(User user, TcpParamWrap param)
        throws MYException;

    /**
     * rejectExpenseApplyBean
     * 
     * @param user
     * @param id
     * @param processId
     * @param reason
     * @return
     * @throws MYException
     */
    boolean rejectExpenseBean(User user, TcpParamWrap param)
        throws MYException;
}
