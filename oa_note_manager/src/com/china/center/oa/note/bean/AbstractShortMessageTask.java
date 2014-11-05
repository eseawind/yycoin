/**
 * File Name: AbstractMSTask.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * Creater: zhuAchen<br>
 * CreateTime: 2009-7-28<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.note.bean;


import java.io.Serializable;

import com.china.center.jdbc.annotation.Id;
import com.china.center.oa.note.constant.ShortMessageConstant;


/**
 * AbstractShortMessageTask
 * 
 * @author zhuzhu
 * @version 2009-7-28
 * @see AbstractShortMessageTask
 * @since 1.0
 */
public abstract class AbstractShortMessageTask implements Serializable
{
    @Id
    private String id = "";

    private String fk = "";

    private String handId = "";

    /**
     * 不同类型的实体的通知
     */
    private int type = 0;

    /**
     * 最大失败次数
     */
    private int fail = 0;

    private int status = ShortMessageConstant.STATUS_INIT;

    /**
     * 0:只发送 1:有回收的短信
     */
    private int mtype = ShortMessageConstant.MTYPE_ONLY_SEND;

    private String fktoken = "";

    private String message = "";

    private String receiver = "";

    private String receiveMsg = "";

    private String stafferId = "";

    private String logTime = "";

    private String sendTime = "";

    private String endTime = "";

    private String sendLog = "";

    /**
     * 0:YYY;1:XXXX
     */
    private String menuReceives = "";

    /**
     * default constructor
     */
    public AbstractShortMessageTask()
    {
    }

    /**
     * @return the id
     */
    public String getId()
    {
        return id;
    }

    /**
     * @param id
     *            the id to set
     */
    public void setId(String id)
    {
        this.id = id;
    }

    /**
     * @return the fk
     */
    public String getFk()
    {
        return fk;
    }

    /**
     * @param fk
     *            the fk to set
     */
    public void setFk(String fk)
    {
        this.fk = fk;
    }

    /**
     * @return the handId
     */
    public String getHandId()
    {
        return handId;
    }

    /**
     * @param handId
     *            the handId to set
     */
    public void setHandId(String handId)
    {
        this.handId = handId;
    }

    /**
     * @return the type
     */
    public int getType()
    {
        return type;
    }

    /**
     * @param type
     *            the type to set
     */
    public void setType(int type)
    {
        this.type = type;
    }

    /**
     * @return the status
     */
    public int getStatus()
    {
        return status;
    }

    /**
     * @param status
     *            the status to set
     */
    public void setStatus(int status)
    {
        this.status = status;
    }

    /**
     * @return the fktoken
     */
    public String getFktoken()
    {
        return fktoken;
    }

    /**
     * @param fktoken
     *            the fktoken to set
     */
    public void setFktoken(String fktoken)
    {
        this.fktoken = fktoken;
    }

    /**
     * @return the message
     */
    public String getMessage()
    {
        return message;
    }

    /**
     * @param message
     *            the message to set
     */
    public void setMessage(String message)
    {
        if (message.length() <= 200)
        {
            this.message = message;
        }
        else
        {
            this.message = message.substring(0, 200);
        }
    }

    /**
     * @return the receiver
     */
    public String getReceiver()
    {
        return receiver;
    }

    /**
     * @param receiver
     *            the receiver to set
     */
    public void setReceiver(String receiver)
    {
        this.receiver = receiver;
    }

    /**
     * @return the receiveMsg
     */
    public String getReceiveMsg()
    {
        return receiveMsg;
    }

    /**
     * @param receiveMsg
     *            the receiveMsg to set
     */
    public void setReceiveMsg(String receiveMsg)
    {
        this.receiveMsg = receiveMsg;
    }

    /**
     * @return the stafferId
     */
    public String getStafferId()
    {
        return stafferId;
    }

    /**
     * @param stafferId
     *            the stafferId to set
     */
    public void setStafferId(String stafferId)
    {
        this.stafferId = stafferId;
    }

    /**
     * @return the logTime
     */
    public String getLogTime()
    {
        return logTime;
    }

    /**
     * @param logTime
     *            the logTime to set
     */
    public void setLogTime(String logTime)
    {
        this.logTime = logTime;
    }

    /**
     * @return the endTime
     */
    public String getEndTime()
    {
        return endTime;
    }

    /**
     * @param endTime
     *            the endTime to set
     */
    public void setEndTime(String endTime)
    {
        this.endTime = endTime;
    }

    /**
     * @return the sendLog
     */
    public String getSendLog()
    {
        return sendLog;
    }

    /**
     * @param sendLog
     *            the sendLog to set
     */
    public void setSendLog(String sendLog)
    {
        this.sendLog = sendLog;
    }

    /**
     * @return the mtype
     */
    public int getMtype()
    {
        return mtype;
    }

    /**
     * @param mtype
     *            the mtype to set
     */
    public void setMtype(int mtype)
    {
        this.mtype = mtype;
    }

    /**
     * @return the menuReceives
     */
    public String getMenuReceives()
    {
        return menuReceives;
    }

    /**
     * @param menuReceives
     *            the menuReceives to set
     */
    public void setMenuReceives(String menuReceives)
    {
        this.menuReceives = menuReceives;
    }

    /**
     * @return the sendTime
     */
    public String getSendTime()
    {
        return sendTime;
    }

    /**
     * @param sendTime
     *            the sendTime to set
     */
    public void setSendTime(String sendTime)
    {
        this.sendTime = sendTime;
    }

    /**
     * @return the fail
     */
    public int getFail()
    {
        return fail;
    }

    /**
     * @param fail
     *            the fail to set
     */
    public void setFail(int fail)
    {
        this.fail = fail;
    }

    /**
     * Constructs a <code>String</code> with all attributes in name = value format.
     * 
     * @return a <code>String</code> representation of this object.
     */
    public String toString()
    {
        final String tab = ",";

        StringBuilder retValue = new StringBuilder();

        retValue
            .append("AbstractShortMessageTask ( ")
            .append(super.toString())
            .append(tab)
            .append("id = ")
            .append(this.id)
            .append(tab)
            .append("fk = ")
            .append(this.fk)
            .append(tab)
            .append("handId = ")
            .append(this.handId)
            .append(tab)
            .append("type = ")
            .append(this.type)
            .append(tab)
            .append("fail = ")
            .append(this.fail)
            .append(tab)
            .append("status = ")
            .append(this.status)
            .append(tab)
            .append("mtype = ")
            .append(this.mtype)
            .append(tab)
            .append("fktoken = ")
            .append(this.fktoken)
            .append(tab)
            .append("message = ")
            .append(this.message)
            .append(tab)
            .append("receiver = ")
            .append(this.receiver)
            .append(tab)
            .append("receiveMsg = ")
            .append(this.receiveMsg)
            .append(tab)
            .append("stafferId = ")
            .append(this.stafferId)
            .append(tab)
            .append("logTime = ")
            .append(this.logTime)
            .append(tab)
            .append("sendTime = ")
            .append(this.sendTime)
            .append(tab)
            .append("endTime = ")
            .append(this.endTime)
            .append(tab)
            .append("sendLog = ")
            .append(this.sendLog)
            .append(tab)
            .append("menuReceives = ")
            .append(this.menuReceives)
            .append(tab)
            .append(" )");

        return retValue.toString();
    }
}
