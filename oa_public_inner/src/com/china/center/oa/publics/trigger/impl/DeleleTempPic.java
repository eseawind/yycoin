/**
 * File Name: DeleleTempPic.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2010-6-27<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.publics.trigger.impl;


import java.io.IOException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.china.center.oa.publics.constant.PublicConstant;
import com.china.center.oa.publics.trigger.CommonJob;
import com.china.center.tools.FileTools;
import com.china.center.tools.StringTools;
import com.china.center.tools.TimeTools;


/**
 * DeleleTempPic
 * 
 * @author ZHUZHU
 * @version 2010-6-27
 * @see DeleleTempPic
 * @since 1.0
 */
public class DeleleTempPic implements CommonJob
{
    private final Log triggerLog = LogFactory.getLog("trigger");

    /**
     * 删除临时的图片，主要是JFREECHAR生成的
     */
    public void excuteJob()
    {
        String root = FileTools.formatPath2(System.getProperty(PublicConstant.OSGI_TEMP_PATH));

        if (StringTools.isNullOrNone(root))
        {
            return;
        }

        root = root + "/jfreechar/" + TimeTools.getSpecialDateString(0, "yyyy/MM");

        try
        {
            FileTools.delete(root);

            triggerLog.info("删除临时数据成功:" + FileTools.formatPath(root));
        }
        catch (IOException e)
        {
            triggerLog.error(e, e);
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.china.center.oa.publics.trigger.CommonJob#getJobName()
     */
    public String getJobName()
    {
        return "Public.DeleleTempPic";
    }

}
