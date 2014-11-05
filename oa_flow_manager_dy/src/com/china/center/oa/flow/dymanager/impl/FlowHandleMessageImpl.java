/**
 * File Name: MailHandleMessageImpl.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * Creater: ZHUACHEN<br>
 * CreateTime: 2009-8-8<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.flow.dymanager.impl;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.china.center.spring.ex.annotation.Exceptional;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

import com.center.china.osgi.publics.User;
import com.china.center.common.MYException;
import com.china.center.oa.flow.bean.FlowInstanceBean;
import com.china.center.oa.flow.dao.FlowInstanceDAO;
import com.china.center.oa.flow.manager.FlowInstanceManager;
import com.china.center.oa.note.bean.ShortMessageTaskBean;
import com.china.center.oa.note.constant.ShortMessageConstant;
import com.china.center.oa.note.manager.AbstractHandleMessage;
import com.china.center.oa.note.manager.HandleMessage;
import com.china.center.oa.publics.bean.StafferBean;
import com.china.center.oa.publics.dao.CommonDAO;
import com.china.center.oa.publics.dao.StafferDAO;
import com.china.center.oa.publics.dao.UserDAO;
import com.china.center.tools.ListTools;
import com.china.center.tools.RandomTools;
import com.china.center.tools.StringTools;
import com.china.center.tools.TimeTools;


/**
 * FlowHandleMessageImpl
 * 
 * @author ZHUZHU
 * @version 2009-8-8
 * @see FlowHandleMessageImpl
 * @since 1.0
 */
@Exceptional
public class FlowHandleMessageImpl extends AbstractHandleMessage
{
    private final Log _logger = LogFactory.getLog(getClass());

    private FlowInstanceManager flowInstanceManager = null;

    private FlowInstanceDAO flowInstanceDAO = null;

    private StafferDAO stafferDAO = null;

    private UserDAO userDAO = null;

    private CommonDAO commonDAO = null;

    /**
     * default constructor
     */
    public FlowHandleMessageImpl()
    {
    }

    public int getHandleType()
    {
        return HandleMessage.TYPE_FLOW;
    }

    /**
     * handle mail message
     */
    public void handleMessage(ShortMessageTaskBean task)
    {
        String[] parserMessage = parserMessage(task, 4);

        if ("0".equals(parserMessage[1].trim()))
        {
            try
            {
                handlePass(task);

                moveDataToHis(task);

                sendSMS(task, "OA提示您的短信审批成功:" + task.getHandId(), false);
            }
            catch (MYException e)
            {
                _logger.error(e, e);

                // send SMS and reset message to receive
                sendSMS(task, "OA提示您的短信审批无效(" + task.getHandId() + "):" + e.getErrorContent(), true);
            }
        }

        if ("1".equals(parserMessage[1].trim()))
        {
            try
            {
                handleReject(task);

                moveDataToHis(task);

                sendSMS(task, "OA提示您的短信审批成功:" + task.getHandId(), false);
            }
            catch (MYException e)
            {
                _logger.error(e, e);

                // send SMS and reset message to receive
                sendSMS(task, "OA提示您的短信审批无效(" + task.getHandId() + "):" + e.getErrorContent(), true);
            }
        }
    }

    /**
     * cancleMessage
     */
    public void cancelMessage(ShortMessageTaskBean task)
    {
        FlowInstanceBean instacne = flowInstanceDAO.find(task.getFk());

        if (instacne == null || !instacne.getCurrentTokenId().equals(task.getFktoken()))
        {
            // delete task
            deleteMsg(task);
        }
    }

    private void handlePass(ShortMessageTaskBean task)
        throws MYException
    {
        String[] parserMessage = parserMessage(task, 4);

        String sname = parserMessage[2];

        String nextStafferId = "";

        if ( !StringTools.isNullOrNone(sname))
        {
            String strName = "默认系统";

            String stafferName = getStafferName(task, sname);

            if (stafferName == null)
            {
                throw new MYException("回复格式错误:" + task.getReceiveMsg());
            }

            if ( !strName.equals(stafferName))
            {
                StafferBean sbean = stafferDAO.findByUnique(stafferName);

                if (sbean == null)
                {
                    throw new MYException("下一环审核人[%s]不存在", sname);
                }

                nextStafferId = sbean.getId();
            }
            else
            {
                nextStafferId = "";
            }
        }

        User user = userDAO.findFirstUserByStafferId(task.getStafferId());

        if (user == null)
        {
            throw new MYException("下一环审核人[%s]不存在", sname);
        }

        FlowInstanceBean instance = flowInstanceDAO.find(task.getFk());

        if (instance == null)
        {
            throw new MYException("流程实例不存在");
        }

        if ( !instance.getCurrentTokenId().equals(task.getFktoken()))
        {
            throw new MYException("您已经无法审核此流程");
        }

        List<String> processers = new ArrayList<String>();

        processers.add(nextStafferId);

        flowInstanceManager.passFlowInstance(user, task.getFk(), parserMessage[3], processers);
    }

    private String getStafferName(ShortMessageTaskBean task, String opr)
    {
        String staffList = task.getMenuReceives();

        String[] splits = staffList.split(";");

        Map<String, String> map = new HashMap();

        for (String str : splits)
        {
            if ( !StringTools.isNullOrNone(str))
            {
                String[] fill = ListTools.fill(str.split(":"), 2);

                map.put(fill[0], fill[1]);
            }
        }

        return map.get(opr);
    }

    private void handleReject(ShortMessageTaskBean task)
        throws MYException
    {
        String[] parserMessage = parserMessage(task, 3);

        User user = userDAO.findFirstUserByStafferId(task.getStafferId());

        if (user == null)
        {
            throw new MYException("系统内部错误");
        }

        flowInstanceManager.rejectFlowInstance(user, task.getFk(), parserMessage[2]);
    }

    private void sendSMS(final ShortMessageTaskBean task, String msg, final boolean updateTask)
    {
        if (updateTask)
        {
            task.setReceiveMsg("");

            task.setStatus(ShortMessageConstant.STATUS_SEND_SUCCESS);
        }

        // send short message
        final ShortMessageTaskBean sms = new ShortMessageTaskBean();

        sms.setId(commonDAO.getSquenceString20());

        sms.setFk(task.getFk());

        sms.setHandId(RandomTools.getRandomMumber(6));

        sms.setType(HandleMessage.TYPE_FLOW);

        sms.setStatus(ShortMessageConstant.STATUS_WAIT_SEND);

        sms.setMtype(ShortMessageConstant.MTYPE_ONLY_SEND);

        sms.setFktoken("");

        sms.setMessage(msg);

        sms.setReceiver(task.getReceiver());

        sms.setStafferId(task.getStafferId());

        sms.setLogTime(TimeTools.now());

        // 24 hour
        sms.setEndTime(TimeTools.now(1));

        TransactionTemplate tran = new TransactionTemplate(transactionManager);

        tran.execute(new TransactionCallback()
        {
            public Object doInTransaction(TransactionStatus arg0)
            {
                if (updateTask)
                {
                    shortMessageTaskDAO.updateEntityBean(task);
                }

                // add SMS
                return shortMessageTaskDAO.saveEntityBean(sms);
            }
        });

    }

    /**
     * @return the flowInstanceManager
     */
    public FlowInstanceManager getFlowInstanceManager()
    {
        return flowInstanceManager;
    }

    /**
     * @param flowInstanceManager
     *            the flowInstanceManager to set
     */
    public void setFlowInstanceManager(FlowInstanceManager flowInstanceManager)
    {
        this.flowInstanceManager = flowInstanceManager;
    }

    /**
     * @return the stafferDAO
     */
    public StafferDAO getStafferDAO()
    {
        return stafferDAO;
    }

    /**
     * @param stafferDAO
     *            the stafferDAO to set
     */
    public void setStafferDAO(StafferDAO stafferDAO)
    {
        this.stafferDAO = stafferDAO;
    }

    /**
     * @return the userDAO
     */
    public UserDAO getUserDAO()
    {
        return userDAO;
    }

    /**
     * @param userDAO
     *            the userDAO to set
     */
    public void setUserDAO(UserDAO userDAO)
    {
        this.userDAO = userDAO;
    }

    /**
     * @return the commonDAO
     */
    public CommonDAO getCommonDAO()
    {
        return commonDAO;
    }

    /**
     * @param commonDAO
     *            the commonDAO to set
     */
    public void setCommonDAO(CommonDAO commonDAO)
    {
        this.commonDAO = commonDAO;
    }

    /**
     * @return the flowInstanceDAO
     */
    public FlowInstanceDAO getFlowInstanceDAO()
    {
        return flowInstanceDAO;
    }

    /**
     * @param flowInstanceDAO
     *            the flowInstanceDAO to set
     */
    public void setFlowInstanceDAO(FlowInstanceDAO flowInstanceDAO)
    {
        this.flowInstanceDAO = flowInstanceDAO;
    }
}
