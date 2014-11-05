/**
 * File Name: FatalNotifyImpl.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2010-11-28<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.publics.manager.impl;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.china.center.oa.publics.constant.SysConfigConstant;
import com.china.center.oa.publics.dao.ParameterDAO;
import com.china.center.oa.publics.manager.FatalNotify;
import com.china.center.tools.DecSecurity;


/**
 * FatalNotifyImpl
 * 
 * @author ZHUZHU
 * @version 2010-11-28
 * @see FatalNotifyImpl
 * @since 3.0
 */
public class FatalNotifyImpl implements FatalNotify
{
    private final Log _logger = LogFactory.getLog(getClass());

    private final Log log = LogFactory.getLog("fatal");

    private ParameterDAO parameterDAO = null;

    /**
     * default constructor
     */
    public FatalNotifyImpl()
    {
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.china.center.oa.publics.manager.FatalNotify#notify(java.lang.String)
     */
    public void notify(String message)
    {
        log.fatal(message);

        SendMailThread thread = new SendMailThread(message);

        try
        {
            thread.start();
        }
        catch (Exception e)
        {
            _logger.error(e, e);
        }
    }

    /**
     * @return the parameterDAO
     */
    public ParameterDAO getParameterDAO()
    {
        return parameterDAO;
    }

    /**
     * @param parameterDAO
     *            the parameterDAO to set
     */
    public void setParameterDAO(ParameterDAO parameterDAO)
    {
        this.parameterDAO = parameterDAO;
    }

    class SendMailThread extends Thread
    {
        private String msg = "";

        /**
         * default constructor
         */
        public SendMailThread(String msg)
        {
            setMsg(msg);
        }

        /**
         * @return the msg
         */
        public String getMsg()
        {
            return msg;
        }

        /**
         * @param msg
         *            the msg to set
         */
        public void setMsg(String msg)
        {
            this.msg = msg;
        }

        public void run()
        {
            SendMailImpl mail = new SendMailImpl("smtp.163.com", DecSecurity.decrypt(parameterDAO
                .getString(SysConfigConstant.MAIL_FROM)), "zhuzhu", DecSecurity
                .decrypt(parameterDAO.getString(SysConfigConstant.MAIL_USER)), DecSecurity
                .decrypt(parameterDAO.getString(SysConfigConstant.MAIL_PWD)),
                new String[] {DecSecurity.decrypt(parameterDAO
                    .getString(SysConfigConstant.MAIL_RECEIVE))}, "Fatal message form software",
                msg);

            try
            {
                mail.send();
            }
            catch (Exception e)
            {
            }
        }
    }


}
