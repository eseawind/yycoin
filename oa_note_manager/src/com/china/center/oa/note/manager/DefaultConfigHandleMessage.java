/**
 * File Name: DefaultConfigHandleMessage.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2010-7-17<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.note.manager;


import java.util.ArrayList;
import java.util.List;


/**
 * DefaultConfigHandleMessage
 * 
 * @author ZHUZHU
 * @version 2010-7-17
 * @see DefaultConfigHandleMessage
 * @since 1.0
 */
public class DefaultConfigHandleMessage
{
    private ProcessShortMessage processShortMessage = null;

    private List<HandleMessage> handleList = new ArrayList();

    /**
     * default constructor
     */
    public DefaultConfigHandleMessage(List<HandleMessage> handleList)
    {
        this.handleList = handleList;
    }

    public void init()
    {
        for (HandleMessage handlePlugin : this.handleList)
        {
            processShortMessage.putHandleMessage(handlePlugin);
        }
    }

    public void destroy()
    {
        for (HandleMessage handlePlugin : this.handleList)
        {
            processShortMessage.removeHandleMessage(handlePlugin.getHandleType());
        }
    }

    /**
     * @return the processShortMessage
     */
    public ProcessShortMessage getProcessShortMessage()
    {
        return processShortMessage;
    }

    /**
     * @param processShortMessage
     *            the processShortMessage to set
     */
    public void setProcessShortMessage(ProcessShortMessage processShortMessage)
    {
        this.processShortMessage = processShortMessage;
    }
}
