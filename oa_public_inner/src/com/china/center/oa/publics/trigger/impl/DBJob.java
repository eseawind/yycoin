/**
 * File Name: DBJob.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2010-6-27<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.publics.trigger.impl;


import java.io.File;
import java.io.IOException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.center.china.osgi.config.ConfigLoader;
import com.center.china.osgi.publics.file.writer.WriteFile;
import com.center.china.osgi.publics.file.writer.WriteFileFactory;
import com.china.center.oa.publics.trigger.CommonJob;
import com.china.center.tools.CMDTools;
import com.china.center.tools.FileTools;
import com.china.center.tools.TimeTools;


/**
 * DBJob
 * 
 * @author ZHUZHU
 * @version 2010-6-27
 * @see DBJob
 * @since 1.0
 */
public class DBJob implements CommonJob
{
    private final Log triggerLog = LogFactory.getLog("trigger");

    /**
     * default constructor
     */
    public DBJob()
    {
    }

    public void excuteJob()
    {
        // 先生成脚本
        WriteFile write = WriteFileFactory.getMyTXTWriter();

        String filePath = "";

        char separator = File.separatorChar;

        try
        {
            FileTools.mkdirs(getScript());

            write.openFile(getScript() + '/' + "bak.bat");

            if (getMysql_root().charAt(1) == ':')
            {
                // 到指定的目录下
                write.writeLine(getMysql_root().substring(0, 2));
            }

            if (separator == '\\')
            {
                write.writeLine("cd " + getMysql_root().replaceAll("/", "\\\\"));
            }
            else
            {
                write.writeLine("cd " + getMysql_root());
            }

            String dirPath = getDb_bak() + '/' + mkdir();

            filePath = dirPath + "/coredata.dump";

            String content1 = "mysqldump -u"
                              + getUser()
                              + " -p"
                              + getPassword()
                              + " "
                              + getDb_name()
                              + " --skip-opt --create-option --set-charset --default-character-set=utf8 -e --max_allowed_packet=1047552 --net_buffer_length=16384>"
                              + filePath;

            write.writeLine(content1);

            if (dirPath.charAt(1) == ':')
            {
                // 到指定的目录下
                write.writeLine(dirPath.substring(0, 2));
            }

            if (separator == '\\')
            {
                write.writeLine("cd " + dirPath.replaceAll("/", "\\\\"));
            }
            else
            {
                write.writeLine("cd " + dirPath);
            }

            write.writeLine("jar cvf coredata.zip coredata.dump");

            write.writeLine("del -f coredata.dump");

            write.close();
        }
        catch (IOException e)
        {
            triggerLog.error(e, e);
        }

        CMDTools.cmdAndShow(getScript() + '/' + "bak.bat");

        // 执行备份数据库
        triggerLog.info("执行备份数据库:" + filePath);

        String delPath = getDb_bak() + '/' + TimeTools.getSpecialDateString( -30, "yyyy/MM/dd");

        // 每个月1号的要留着
        if ( !delPath.endsWith("01"))
        {
            try
            {
                FileTools.delete(delPath);

                triggerLog.info("删除备份数据库成功:" + delPath);
            }
            catch (IOException e)
            {
                triggerLog.error(e, e);
            }
        }

    }

    private String mkdir()
    {
        String path = TimeTools.now("yyyy/MM/dd");

        FileTools.mkdirs(getDb_bak() + '/' + path);

        return path;
    }

    public String getJobName()
    {
        return "Public.DBJob";
    }

    /**
     * @return the script
     */
    public String getScript()
    {
        return ConfigLoader.getProperty("script");
    }

    /**
     * @return the db_bak
     */
    public String getDb_bak()
    {
        return ConfigLoader.getProperty("db_bak", "d:/oa_db_bak");
    }

    /**
     * @return the mysql_root
     */
    public String getMysql_root()
    {
        return ConfigLoader
            .getProperty("mysql_root", "D:/Program Files/MySQL/MySQL Server 5.5/bin");
    }

    /**
     * @return the user
     */
    public String getUser()
    {
        return ConfigLoader.getProperty("user", "uportal");
    }

    /**
     * @return the password
     */
    public String getPassword()
    {
        return ConfigLoader.getProperty("password", "Qwerty123789");
    }

    /**
     * @return the db_name
     */
    public String getDb_name()
    {
        return ConfigLoader.getProperty("db_name", "uportal");
    }
}
