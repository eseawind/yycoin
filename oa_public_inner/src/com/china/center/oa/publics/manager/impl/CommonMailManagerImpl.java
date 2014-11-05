package com.china.center.oa.publics.manager.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.china.center.oa.publics.constant.SysConfigConstant;
import com.china.center.oa.publics.dao.ParameterDAO;
import com.china.center.oa.publics.manager.CommonMailManager;
import com.china.center.tools.StringTools;

/**
 * 
 * CommonMailManagerImpl
 *
 * @author fangliwen 2012-10-21
 */
public class CommonMailManagerImpl implements CommonMailManager 
{
    private final Log _logger = LogFactory.getLog(getClass());

//    private final Log log = LogFactory.getLog("fatal");

    private ParameterDAO parameterDAO = null;

    /**
     * default constructor
     */
    public CommonMailManagerImpl()
    {
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.china.center.oa.publics.manager.FatalNotify#notify(java.lang.String)
     */
    public void sendMail(String to, String subject, String message)
    {
//        log.fatal(message);

        SendMailThread thread = new SendMailThread(to, subject, message);

        try
        {
            thread.start();
        }
        catch (Exception e)
        {
            _logger.error(e, e);
        }
    }

    public void sendMail(String to, String subject, String message, String file)
    {
    	//        log.fatal(message);

        SendMailThread thread = new SendMailThread(to, subject, message, file);

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
        
        private String to = "";
        
        private String subject = "";
        
        private String file = "";
        
        public String getTo() {
            return to;
        }

        public void setTo(String to) {
            this.to = to;
        }

        public String getSubject() {
            return subject;
        }

        public void setSubject(String subject) {
            this.subject = subject;
        }

        public String getFile()
		{
			return file;
		}

		public void setFile(String file)
		{
			this.file = file;
		}

		/**
         * default constructor
         */
        public SendMailThread(String to, String subject, String msg)
        {
            setTo(to);
            setSubject(subject);
            setMsg(msg);
        }
        
        public SendMailThread(String to, String subject, String msg, String file)
        {
            setTo(to);
            setSubject(subject);
            setMsg(msg);
            setFile(file);
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
            SendMailImpl mail = new SendMailImpl("smtp.163.com", parameterDAO
                .getString(SysConfigConstant.MAIL_FROM), "永银",
                parameterDAO.getString(SysConfigConstant.MAIL_USER),
                parameterDAO.getString(SysConfigConstant.MAIL_PWD),
                new String[] {to}, subject, msg);

            try
            {
            	if (!StringTools.isNullOrNone(getFile()))
            	{
            		mail.addAttachfile(getFile());
            	}
            	
                mail.send();
            }
            catch (Exception e)
            {
            }
        }
    }

}
