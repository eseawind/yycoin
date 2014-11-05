/**
 * File Name: SMSHelper.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * Creater: ZHUACHEN<br>
 * CreateTime: 2009-8-16<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.sms.helper;


import java.io.UnsupportedEncodingException;
import java.util.Formatter;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


/**
 * SMSHelper
 * 
 * @author ZHUZHU
 * @version 2009-8-16
 * @see SMSHelper
 * @since 1.0
 */
public abstract class SMSHelper
{
    private static final Log _logger = LogFactory.getLog(SMSHelper.class);

    private static final Log SMSLOG = LogFactory.getLog("sms");

    public static boolean sendSMSReal(String format, String phone, String content, String messageFlag, String exNumber)
    {
        HttpClient httpclient = new HttpClient();

        try
        {
            content = java.net.URLEncoder.encode(content, "GBK");
        }
        catch (UnsupportedEncodingException e)
        {
            _logger.error(e, e);
        }

        Formatter formatter = new Formatter();

        String url = formatter.format(format.trim(), phone.trim(), content.trim(), messageFlag, exNumber).toString();

        GetMethod getMethod = new GetMethod(url);

        int statusCode = 200;

        try
        {
            statusCode = httpclient.executeMethod(getMethod);
        }
        catch (Throwable e)
        {
            SMSLOG.info("send sms to [" + phone.trim() + "] is:" + 505 + "." + url + "." + e);

            _logger.error(e, e);

            return false;
        }

        SMSLOG.info("send sms to [" + phone.trim() + "] is:" + statusCode + "." + url);

        return statusCode == 200;
    }
}
