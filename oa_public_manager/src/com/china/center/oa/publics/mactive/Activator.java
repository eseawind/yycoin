package com.china.center.oa.publics.mactive;


import java.util.ArrayList;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.osgi.framework.console.CommandInterpreter;
import org.eclipse.osgi.framework.console.CommandProvider;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

import com.china.center.common.taglib.DefinedCommon;
import com.china.center.common.taglib.MapBean;
import com.china.center.oa.publics.constant.AlarmConstant;
import com.china.center.oa.publics.constant.CommonConstant;
import com.china.center.oa.publics.constant.DutyConstant;
import com.china.center.oa.publics.constant.InvoiceConstant;
import com.china.center.oa.publics.constant.OperationConstant;
import com.china.center.oa.publics.constant.PublicConstant;
import com.china.center.oa.publics.constant.StafferConstant;
import com.china.center.oa.publics.helper.OATools;


/**
 * Activator
 * 
 * @author ZHUZHU
 * @version 2010-6-27
 * @see Activator
 * @since 1.0
 */
public class Activator implements BundleActivator, CommandProvider
{
    protected BundleContext context;

    private List<Class> parserClass = new LinkedList<Class>()
    {
        {
            add(PublicConstant.class);
            add(StafferConstant.class);
            add(DutyConstant.class);
            add(InvoiceConstant.class);
            add(OperationConstant.class);
            add(CommonConstant.class);
            add(AlarmConstant.class);
        }
    };

    public void start(BundleContext context)
        throws Exception
    {
        for (Class each : parserClass)
        {
            DefinedCommon.addDefinedClass(each);
        }

        List<MapBean> userStatus = new ArrayList<MapBean>();

        userStatus.add(new MapBean(PublicConstant.LOGIN_STATUS_COMMON, "正常"));

        userStatus.add(new MapBean(PublicConstant.LOGIN_STATUS_LOCK, "锁定"));

        DefinedCommon.addDefined("userStatus", userStatus);

        this.context = context;

        Hashtable properties = new Hashtable();

        // 需要注册的
        context.registerService(CommandProvider.class.getName(), this, properties);
    }

    public void stop(BundleContext context)
        throws Exception
    {
        for (Class each : parserClass)
        {
            DefinedCommon.reomoveConstant(each);
        }

        DefinedCommon.removeDefined("userStatus");
    }

    public String getHelp()
    {
        return "";
    }

    public void _mode(CommandInterpreter intp)
        throws Exception
    {
        String filtrate = intp.nextArgument();

        List<String> filtrateList = new ArrayList();

        while (filtrate != null)
        {
            filtrateList.add(filtrate.trim());

            filtrate = intp.nextArgument();
        }

        if (filtrateList.isEmpty())
        {
            if (OATools.isChangeToV5())
            {
                intp.println("当前系统已经正式切换到V5.使用mode 0 切换到V3,使用mode 1 切换到V5");
            }
            else
            {
                intp.println("当前系统仍在V3,还未升级到V5.使用mode 0 切换到V3,使用mode 1 切换到V5");
            }

            return;
        }

        String cmd = filtrateList.get(0);

        if ("0".equals(cmd))
        {
            OATools.setChangeToV5(false);

            intp.println("设置成功,当前系统切换到V3");

            return;
        }

        if ("1".equals(cmd))
        {
            OATools.setChangeToV5(true);

            intp.println("设置成功,当前系统切换到V5");

            return;
        }

        intp.println("错误的命令格式.使用mode 0 切换到V3,使用mode 1 切换到V5");
    }
}
