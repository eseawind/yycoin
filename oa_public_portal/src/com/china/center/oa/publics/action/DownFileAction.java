/**
 * File Name: DownFileAction.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2010-6-27<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.publics.action;


import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.china.center.oa.publics.Helper;
import com.china.center.tools.FileTools;
import com.china.center.tools.StringTools;
import com.china.center.tools.UtilStream;


/**
 * DownFileAction
 * 
 * @author ZHUZHU
 * @version 2010-6-27
 * @see DownFileAction
 * @since 1.0
 */
public class DownFileAction extends DispatchAction
{
    /**
     * default constructor
     */
    public DownFileAction()
    {
    }

    /**
     * 下载模板
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     * @throws IOException
     */
    public ActionForward downTemplateFile(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                          HttpServletResponse response)
        throws ServletException, IOException
    {
        String path = Helper.getRootPath();

        path = FileTools.formatPath(path) + "template/template.xls";

        File file = new File(path);

        OutputStream out = response.getOutputStream();

        response.setContentType("application/x-dbf");

        response.setHeader("Content-Disposition", "attachment; filename=template.xls");

        UtilStream us = new UtilStream(new FileInputStream(file), out);

        us.copyAndCloseStream();

        return null;
    }

    /**
     * 获得下载的文件
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     * @throws IOException
     */
    public ActionForward downProfitTemplateFile(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                                HttpServletResponse response)
        throws ServletException, IOException
    {
        String path = Helper.getRootPath();

        path = FileTools.formatPath(path) + "template/profitTemplate.xls";

        File file = new File(path);

        OutputStream out = response.getOutputStream();

        response.setContentType("application/x-dbf");

        response.setHeader("Content-Disposition", "attachment; filename=template.xls");

        UtilStream us = new UtilStream(new FileInputStream(file), out);

        us.copyAndCloseStream();

        return null;
    }

    /**
     * 获得下载的文件
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     * @throws IOException
     */
    public ActionForward downTemplateFileByName(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                                HttpServletResponse response)
        throws ServletException, IOException
    {
        String fileName = request.getParameter("fileName");

        if (fileName.contains("/") || fileName.contains("\\"))
        {
            return null;
        }

        String path = Helper.getRootPath();

        path = path + "template/" + fileName;

        File file = new File(path);

        OutputStream out = response.getOutputStream();

        response.setContentLength((int)file.length());

        response.setContentType("application/x-dbf");

        response.setHeader("Content-Disposition", "attachment; filename="
                                                  + StringTools.getStringBySet(fileName, "GBK", "ISO8859-1"));

        UtilStream us = new UtilStream(new FileInputStream(file), out);

        us.copyAndCloseStream();

        return null;
    }
}
