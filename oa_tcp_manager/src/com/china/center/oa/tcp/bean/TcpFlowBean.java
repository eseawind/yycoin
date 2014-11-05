/**
 * File Name: TcpFlowBean.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2011-7-17<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.tcp.bean;


import java.io.Serializable;

import com.china.center.jdbc.annotation.Entity;
import com.china.center.jdbc.annotation.FK;
import com.china.center.jdbc.annotation.Id;
import com.china.center.jdbc.annotation.Table;
import com.china.center.jdbc.annotation.Unique;


/**
 * 流程环节表
 * 
 * @author ZHUZHU
 * @version 2011-7-17
 * @see TcpFlowBean
 * @since 3.0
 */
@Entity
@Table(name = "T_CENTER_TCPFLOW")
public class TcpFlowBean implements Serializable
{
    @Id
    private String id = "";

    @FK
    @Unique(dependFields = "currentStatus")
    private String flowKey = "";

    /**
     * 当前支持group,plugin,end
     */
    private String nextPlugin = "";

    private String tokenName = "";

    private int currentStatus = 0;

    private int nextStatus = 0;

    private int reject = 0;

    /**
     * 0:返回到初始 1:到上一级
     */
    private int rejectToPre = 0;

    private int begining = 0;

    /**
     * 0:非会签 1:会签
     */
    private int singeAll = 0;

    private int ending = 0;

    /**
     * default constructor
     */
    public TcpFlowBean()
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
     * @return the flowKey
     */
    public String getFlowKey()
    {
        return flowKey;
    }

    /**
     * @param flowKey
     *            the flowKey to set
     */
    public void setFlowKey(String flowKey)
    {
        this.flowKey = flowKey;
    }

    /**
     * @return the tokenName
     */
    public String getTokenName()
    {
        return tokenName;
    }

    /**
     * @param tokenName
     *            the tokenName to set
     */
    public void setTokenName(String tokenName)
    {
        this.tokenName = tokenName;
    }

    /**
     * @return the currentStatus
     */
    public int getCurrentStatus()
    {
        return currentStatus;
    }

    /**
     * @param currentStatus
     *            the currentStatus to set
     */
    public void setCurrentStatus(int currentStatus)
    {
        this.currentStatus = currentStatus;
    }

    /**
     * @return the nextStatus
     */
    public int getNextStatus()
    {
        return nextStatus;
    }

    /**
     * @param nextStatus
     *            the nextStatus to set
     */
    public void setNextStatus(int nextStatus)
    {
        this.nextStatus = nextStatus;
    }

    /**
     * @return the reject
     */
    public int getReject()
    {
        return reject;
    }

    /**
     * @param reject
     *            the reject to set
     */
    public void setReject(int reject)
    {
        this.reject = reject;
    }

    /**
     * @return the begining
     */
    public int getBegining()
    {
        return begining;
    }

    /**
     * @param begining
     *            the begining to set
     */
    public void setBegining(int begining)
    {
        this.begining = begining;
    }

    /**
     * @return the ending
     */
    public int getEnding()
    {
        return ending;
    }

    /**
     * @param ending
     *            the ending to set
     */
    public void setEnding(int ending)
    {
        this.ending = ending;
    }

    /**
     * @return the nextPlugin
     */
    public String getNextPlugin()
    {
        return nextPlugin;
    }

    /**
     * @param nextPlugin
     *            the nextPlugin to set
     */
    public void setNextPlugin(String nextPlugin)
    {
        this.nextPlugin = nextPlugin;
    }

    /**
     * @return the singeAll
     */
    public int getSingeAll()
    {
        return singeAll;
    }

    /**
     * @param singeAll
     *            the singeAll to set
     */
    public void setSingeAll(int singeAll)
    {
        this.singeAll = singeAll;
    }

    /**
     * @return the rejectToPre
     */
    public int getRejectToPre()
    {
        return rejectToPre;
    }

    /**
     * @param rejectToPre
     *            the rejectToPre to set
     */
    public void setRejectToPre(int rejectToPre)
    {
        this.rejectToPre = rejectToPre;
    }

    /**
     * Constructs a <code>String</code> with all attributes in name = value format.
     * 
     * @return a <code>String</code> representation of this object.
     */
    public String toString()
    {
        final String TAB = ",";

        StringBuilder retValue = new StringBuilder();

        retValue
            .append("TcpFlowBean ( ")
            .append(super.toString())
            .append(TAB)
            .append("id = ")
            .append(this.id)
            .append(TAB)
            .append("flowKey = ")
            .append(this.flowKey)
            .append(TAB)
            .append("nextPlugin = ")
            .append(this.nextPlugin)
            .append(TAB)
            .append("tokenName = ")
            .append(this.tokenName)
            .append(TAB)
            .append("currentStatus = ")
            .append(this.currentStatus)
            .append(TAB)
            .append("nextStatus = ")
            .append(this.nextStatus)
            .append(TAB)
            .append("reject = ")
            .append(this.reject)
            .append(TAB)
            .append("rejectToPre = ")
            .append(this.rejectToPre)
            .append(TAB)
            .append("begining = ")
            .append(this.begining)
            .append(TAB)
            .append("singeAll = ")
            .append(this.singeAll)
            .append(TAB)
            .append("ending = ")
            .append(this.ending)
            .append(TAB)
            .append(" )");

        return retValue.toString();
    }

}
