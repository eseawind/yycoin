/**
 * File Name: NoteManagerImpl.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2010-7-17<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.note.manager.impl;


import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.china.center.spring.ex.annotation.Exceptional;
import org.springframework.transaction.annotation.Transactional;

import com.china.center.common.MYException;
import com.china.center.jdbc.util.ConditionParse;
import com.china.center.oa.note.bean.ReceiveTaskBean;
import com.china.center.oa.note.bean.ShortMessageTaskBean;
import com.china.center.oa.note.constant.ShortMessageConstant;
import com.china.center.oa.note.dao.ReceiveTaskDAO;
import com.china.center.oa.note.dao.ShortMessageTaskDAO;
import com.china.center.oa.note.dao.ShortMessageTaskHisDAO;
import com.china.center.oa.note.manager.NoteManager;
import com.china.center.oa.publics.dao.CommonDAO;
import com.china.center.tools.TimeTools;


/**
 * NoteManagerImpl
 * 
 * @author ZHUZHU
 * @version 2010-7-17
 * @see NoteManagerImpl
 * @since 1.0
 */
@Exceptional
public class NoteManagerImpl implements NoteManager
{
    private final Log triggerLog = LogFactory.getLog("trigger");

    private ReceiveTaskDAO receiveTaskDAO = null;

    private CommonDAO commonDAO = null;

    private ShortMessageTaskDAO shortMessageTaskDAO = null;

    private ShortMessageTaskHisDAO shortMessageTaskHisDAO = null;

    /**
     * default constructor
     */
    public NoteManagerImpl()
    {
    }

    @Transactional(rollbackFor = {MYException.class})
    public boolean addReceiveTask(ReceiveTaskBean task)
        throws MYException
    {
        task.setId(commonDAO.getSquenceString20());

        task.setLogTime(TimeTools.now());

        return receiveTaskDAO.saveEntityBean(task);
    }

    /**
     * synReceiveToSend
     */
    @Transactional(rollbackFor = {MYException.class})
    public void synReceiveToSend()
    {
        triggerLog.debug("synReceiveToSend begin...");

        ConditionParse con = new ConditionParse();

        con.addWhereStr();

        List<ReceiveTaskBean> receiveList = receiveTaskDAO.queryEntityBeansByLimit(con, 200);

        for (ReceiveTaskBean receiveTaskBean : receiveList)
        {
            receiveTaskDAO.deleteEntityBean(receiveTaskBean.getId());

            String receiveMessage = receiveTaskBean.getMessage();

            String[] splitStr = receiveMessage.split(":");

            ShortMessageTaskBean task = shortMessageTaskDAO.findByReceiverAndHandId(receiveTaskBean.getSender(),
                splitStr[0]);

            if (task == null)
            {
                triggerLog.warn("接收消息解析失败:" + receiveTaskBean);
                continue;
            }

            task.setReceiveMsg(receiveMessage);

            task.setStatus(ShortMessageConstant.STATUS_RECEIVE_SUCCESS);

            shortMessageTaskDAO.updateEntityBean(task);
        }

        triggerLog.debug("synReceiveToSend end...");
    }

    /**
     * changeInitToWaitSend
     */
    @Transactional(rollbackFor = {MYException.class})
    public void changeInitToWaitSend()
    {
        shortMessageTaskDAO.updateInitToWaitSend();
    }

    /**
     * moveTimeoutData
     */
    @Transactional(rollbackFor = {MYException.class})
    public void moveTimeoutData()
    {
        ConditionParse con = new ConditionParse();

        con.addWhereStr();

        con.addCondition("endTime", "<=", TimeTools.now());

        shortMessageTaskDAO.deleteEntityBeansByCondition(con);
    }

    /**
     * @return the receiveTaskDAO
     */
    public ReceiveTaskDAO getReceiveTaskDAO()
    {
        return receiveTaskDAO;
    }

    /**
     * @param receiveTaskDAO
     *            the receiveTaskDAO to set
     */
    public void setReceiveTaskDAO(ReceiveTaskDAO receiveTaskDAO)
    {
        this.receiveTaskDAO = receiveTaskDAO;
    }

    /**
     * @return the shortMessageTaskDAO
     */
    public ShortMessageTaskDAO getShortMessageTaskDAO()
    {
        return shortMessageTaskDAO;
    }

    /**
     * @param shortMessageTaskDAO
     *            the shortMessageTaskDAO to set
     */
    public void setShortMessageTaskDAO(ShortMessageTaskDAO shortMessageTaskDAO)
    {
        this.shortMessageTaskDAO = shortMessageTaskDAO;
    }

    /**
     * @return the shortMessageTaskHisDAO
     */
    public ShortMessageTaskHisDAO getShortMessageTaskHisDAO()
    {
        return shortMessageTaskHisDAO;
    }

    /**
     * @param shortMessageTaskHisDAO
     *            the shortMessageTaskHisDAO to set
     */
    public void setShortMessageTaskHisDAO(ShortMessageTaskHisDAO shortMessageTaskHisDAO)
    {
        this.shortMessageTaskHisDAO = shortMessageTaskHisDAO;
    }
}
