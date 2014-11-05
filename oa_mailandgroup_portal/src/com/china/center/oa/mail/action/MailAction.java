/**
 * File Name: WorkLogAction.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * Creater: zhuAchen<br>
 * CreateTime: 2009-2-16<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.mail.action;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileUploadBase;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.center.china.osgi.config.ConfigLoader;
import com.center.china.osgi.publics.User;
import com.china.center.actionhelper.common.ActionTools;
import com.china.center.actionhelper.common.JSONTools;
import com.china.center.actionhelper.common.KeyConstant;
import com.china.center.actionhelper.json.AjaxResult;
import com.china.center.common.MYException;
import com.china.center.jdbc.util.ConditionParse;
import com.china.center.oa.group.dao.GroupDAO;
import com.china.center.oa.group.dao.GroupVSStafferDAO;
import com.china.center.oa.mail.bean.AttachmentBean;
import com.china.center.oa.mail.bean.MailBean;
import com.china.center.oa.mail.bean.MailBean2;
import com.china.center.oa.mail.dao.AttachmentDAO;
import com.china.center.oa.mail.dao.MailDAO;
import com.china.center.oa.mail.dao.MailDAO2;
import com.china.center.oa.mail.helper.MailHelper;
import com.china.center.oa.mg.facade.MailGroupFacade;
import com.china.center.oa.publics.Helper;
import com.china.center.oa.publics.bean.StafferBean;
import com.china.center.oa.publics.dao.StafferDAO;
import com.china.center.tools.BeanUtil;
import com.china.center.tools.CommonTools;
import com.china.center.tools.FileTools;
import com.china.center.tools.RequestDataStream;
import com.china.center.tools.SequenceTools;
import com.china.center.tools.StringTools;
import com.china.center.tools.TimeTools;
import com.china.center.tools.UtilStream;


/**
 * WorkLogAction
 * 
 * @author ZHUZHU
 * @version 2009-2-16
 * @see MailAction
 * @since 1.0
 */
public class MailAction extends DispatchAction
{
    private final Log _logger = LogFactory.getLog(getClass());

    private MailGroupFacade mailGroupFacade = null;

    private GroupDAO groupDAO = null;

    private MailDAO mailDAO = null;

    private StafferDAO stafferDAO = null;

    private MailDAO2 mailDAO2 = null;

    private AttachmentDAO attachmentDAO = null;

    private GroupVSStafferDAO groupVSStafferDAO = null;

    private static String QUERYMAIL = "queryMail";

    private static String QUERYMAIL2 = "queryMail2";

    /**
     * default constructor
     */
    public MailAction()
    {
    }

    /**
     * query your mail
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward queryMail(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                   HttpServletResponse response)
        throws ServletException
    {
        final ConditionParse condtion = new ConditionParse();

        condtion.addWhereStr();

        User user = Helper.getUser(request);

        condtion.addCondition("MailBean.reveiveId", "=", user.getStafferId());

        ActionTools.processJSONQueryCondition(QUERYMAIL, request, condtion);

        condtion.addCondition("order by MailBean.logTime desc");

        String jsonstr = ActionTools.queryVOByJSONAndToString(QUERYMAIL, request, condtion, this.mailDAO);

        return JSONTools.writeResponse(response, jsonstr);
    }

    /**
     * query has send mail
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward queryMail2(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                    HttpServletResponse response)
        throws ServletException
    {
        final ConditionParse condtion = new ConditionParse();

        condtion.addWhereStr();

        User user = Helper.getUser(request);

        condtion.addCondition("MailBean2.senderId", "=", user.getStafferId());

        ActionTools.processJSONQueryCondition(QUERYMAIL2, request, condtion);

        condtion.addCondition("order by MailBean2.logTime desc");

        String jsonstr = ActionTools.queryVOByJSONAndToString(QUERYMAIL2, request, condtion, this.mailDAO2);

        return JSONTools.writeResponse(response, jsonstr);
    }

    /**
     * addOrUpdateGroup
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward sendMail(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                  HttpServletResponse response)
        throws ServletException
    {
        RequestDataStream rds = new RequestDataStream(request);

        try
        {
            rds.parser();
        }
        catch (FileUploadBase.SizeLimitExceededException e)
        {
            _logger.error(e, e);

            request.setAttribute(KeyConstant.ERROR_MESSAGE, "发送邮件失败:附件超过10M");

            return mapping.findForward("queryMail");
        }
        catch (Exception e)
        {
            _logger.error(e, e);

            request.setAttribute(KeyConstant.ERROR_MESSAGE, "发送邮件失败");

            return mapping.findForward("queryMail");
        }

        MailBean bean = new MailBean();

        List<AttachmentBean> attachments = new ArrayList<AttachmentBean>();

        bean.setAttachments(attachments);

        BeanUtil.getBean(bean, rds.getParmterMap());

        // parser attachment
        if (rds.haveStream())
        {
            Map<String, InputStream> streamMap = rds.getStreamMap();

            for (Map.Entry<String, InputStream> item : streamMap.entrySet())
            {
                AttachmentBean att = new AttachmentBean();

                FileOutputStream out = null;

                try
                {
                    String savePath = mkdir();

                    String fileAlais = SequenceTools.getSequence();

                    String rabsPath = '/' + savePath + '/' + fileAlais + "."
                                      + FileTools.getFilePostfix(rds.getFileName(item.getKey())).toLowerCase();

                    String filePath = getMailAttchmentPath() + '/' + rabsPath;

                    att.setName(FileTools.getFileName(rds.getFileName(item.getKey())));

                    att.setPath(rabsPath);

                    out = new FileOutputStream(filePath);

                    UtilStream ustream = new UtilStream(item.getValue(), out);

                    ustream.copyAndCloseStream();
                }
                catch (IOException e)
                {
                    _logger.error(e, e);

                    request.setAttribute(KeyConstant.ERROR_MESSAGE, "发送邮件失败");

                    return mapping.findForward("queryMail");
                }

                attachments.add(att);
            }
        }

        rds.close();

        try
        {
            User user = Helper.getUser(request);

            bean.setSenderId(user.getStafferId());

            createItems(rds.getParmterMap(), bean);

            mailGroupFacade.addMail(user.getId(), bean);

            request.setAttribute(KeyConstant.MESSAGE, "成功发送邮件");
        }
        catch (MYException e)
        {
            _logger.warn(e, e);

            request.setAttribute(KeyConstant.ERROR_MESSAGE, "发送邮件失败:" + e.getMessage());
        }

        return mapping.findForward("queryMail");
    }

    private String mkdir()
    {
        String path = TimeTools.now("yyyy/MM/dd/HH");

        FileTools.mkdirs(getMailAttchmentPath() + '/' + path);

        return path;
    }

    /**
     * findNextMail
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward findNextMail(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                      HttpServletResponse response)
        throws ServletException
    {
        String id = request.getParameter("id");

        User user = Helper.getUser(request);

        Serializable dirId = mailDAO.findNextId(id, user.getStafferId());

        if (StringTools.isNullOrNone(dirId))
        {
            return ActionTools.toError("没有下一封邮件", "queryMail", mapping, request);
        }

        CommonTools.removeParamers(request);

        request.setAttribute("dirId", dirId);

        return findMail(mapping, form, request, response);
    }

    /**
     * findPreviewMail
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward findPreviewMail(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                         HttpServletResponse response)
        throws ServletException
    {
        String id = request.getParameter("id");

        User user = Helper.getUser(request);

        Serializable dirId = mailDAO.findPreviewId(id, user.getStafferId());

        if (StringTools.isNullOrNone(dirId))
        {
            return ActionTools.toError("没有上一封邮件", "queryMail", mapping, request);
        }

        CommonTools.removeParamers(request);

        request.setAttribute("dirId", dirId);

        return findMail(mapping, form, request, response);
    }

    /**
     * find邮件
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward findMail(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                  HttpServletResponse response)
        throws ServletException
    {
        CommonTools.saveParamers(request);

        String id = "";

        Object oo = request.getAttribute("dirId");

        if (oo == null)
        {
            id = request.getParameter("id");
        }
        else
        {
            id = oo.toString();
        }

        String operation = request.getParameter("operation");

        MailBean bean = mailDAO.find(id);

        if (bean == null)
        {
            return ActionTools.toError("邮件不存在", "queryMail", mapping, request);
        }

        User user = Helper.getUser(request);

        if ( !bean.getReveiveId().equals(user.getStafferId()))
        {
            request.setAttribute(KeyConstant.ERROR_MESSAGE, "拒绝操作");

            return mapping.findForward("queryMail");
        }

        try
        {
            mailGroupFacade.updateStatusToRead(user.getId(), id);
        }
        catch (MYException e)
        {
            request.setAttribute(KeyConstant.ERROR_MESSAGE, "查看失败:" + e.getMessage());

            return mapping.findForward("queryMail");
        }

        List<AttachmentBean> atts = attachmentDAO.queryEntityBeansByFK(id);

        request.setAttribute("bean", bean);

        request.setAttribute("atts", atts);

        // forward
        if ("1".equals(operation))
        {
            String attacmentIds = "";

            for (AttachmentBean attachmentBean : atts)
            {
                attacmentIds += attachmentBean.getId() + ';';
            }

            request.setAttribute("attacmentIds", attacmentIds);

            return mapping.findForward("transmitMail");
        }

        // reply
        if ("2".equals(operation))
        {
            request.setAttribute("mainReveiveIds", bean.getSenderId() + ';');
            request.setAttribute("mainReveiveNames", bean.getSenderName() + ';');
            request.setAttribute("secReveiveIds", "");
            request.setAttribute("secReveiveNames", "");

            return mapping.findForward("replyMail");
        }

        // reply all
        if ("3".equals(operation))
        {
            StafferBean stafferBean = stafferDAO.find(user.getStafferId());

            if (stafferBean == null)
            {
                return ActionTools.toError("职员不存在", "queryMail", mapping, request);
            }

            // mainReveiveIds include sender and main reveive
            request.setAttribute("mainReveiveIds", MailHelper.trimStaffers(bean.getSenderId() + ';'
                                                                           + bean.getReveiveIds()));

            request.setAttribute("mainReveiveNames", MailHelper.trimStaffers(bean.getSenderName() + ';'
                                                                             + bean.getReveiveNames()));

            request.setAttribute("secReveiveIds", MailHelper.deleteStaffer(bean.getReveiveIds2(), user.getStafferId()));

            request.setAttribute("secReveiveNames", MailHelper.deleteStaffer(bean.getReveiveNames2(), stafferBean
                .getName()));

            return mapping.findForward("replyMail");
        }

        return mapping.findForward("detailMail");
    }

    /**
     * find邮件
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward findMail2(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                   HttpServletResponse response)
        throws ServletException
    {
        CommonTools.saveParamers(request);

        String id = request.getParameter("id");

        String operation = request.getParameter("operation");

        MailBean2 bean = mailDAO2.find(id);

        if (bean == null)
        {
            return ActionTools.toError("邮件不存在", "queryMail2", mapping, request);
        }

        User user = Helper.getUser(request);

        if ( !bean.getSenderId().equals(user.getStafferId()))
        {
            request.setAttribute(KeyConstant.ERROR_MESSAGE, "拒绝操作");

            return mapping.findForward("queryMail2");
        }

        List<AttachmentBean> atts = attachmentDAO.queryEntityBeansByFK(id);

        request.setAttribute("bean", bean);

        request.setAttribute("atts", atts);

        // forward
        if ("1".equals(operation))
        {
            String attacmentIds = "";

            for (AttachmentBean attachmentBean : atts)
            {
                attacmentIds += attachmentBean.getId() + ';';
            }

            request.setAttribute("attacmentIds", attacmentIds);

            return mapping.findForward("transmitMail");
        }

        request.setAttribute("mail2", true);

        return mapping.findForward("detailMail");
    }

    /**
     * if the operation is forward,we must add the old attachment
     * 
     * @param request
     * @param bean
     * @throws MYException
     */
    private void createItems(Map map, MailBean bean)
        throws MYException
    {
        Object oo = map.get("forwardMail");

        if (oo == null)
        {
            return;
        }

        String forwardMail = oo.toString();

        if ("1".equals(forwardMail))
        {
            oo = map.get("attacmentIds");

            if (oo == null)
            {
                return;
            }

            String attacmentIds = oo.toString();

            if (StringTools.isNullOrNone(attacmentIds))
            {
                return;
            }

            String[] ids = attacmentIds.split(";");

            for (String string : ids)
            {
                if ( !StringTools.isNullOrNone(string))
                {
                    AttachmentBean temp = attachmentDAO.find(string);

                    if (temp == null)
                    {
                        throw new MYException("附件不存在");
                    }

                    // add old attachment
                    bean.getAttachments().add(temp);
                }
            }
        }
    }

    /**
     * deleteMail
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward deleteMail(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                    HttpServletResponse response)
        throws ServletException
    {
        String ids = request.getParameter("ids");

        AjaxResult ajax = new AjaxResult();

        try
        {
            User user = Helper.getUser(request);

            String[] idArr = ids.split(";");

            for (String eachItem : idArr)
            {
                if ( !StringTools.isNullOrNone(eachItem))
                {
                    mailGroupFacade.deleteMail(user.getId(), eachItem);
                }
            }

            ajax.setSuccess("成功删除%d封邮件", idArr.length);
        }
        catch (MYException e)
        {
            _logger.warn(e, e);

            ajax.setError("删除邮件失败:" + e.getMessage());
        }

        return JSONTools.writeResponse(response, ajax);
    }

    /**
     * deleteMail2
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward deleteMail2(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                     HttpServletResponse response)
        throws ServletException
    {
        String ids = request.getParameter("ids");

        AjaxResult ajax = new AjaxResult();

        try
        {
            User user = Helper.getUser(request);

            String[] idArr = ids.split(";");

            for (String eachItem : idArr)
            {
                if ( !StringTools.isNullOrNone(eachItem))
                {
                    mailGroupFacade.deleteMail2(user.getId(), eachItem);
                }
            }

            ajax.setSuccess("成功删除%d封邮件", idArr.length);
        }
        catch (MYException e)
        {
            _logger.warn(e, e);

            ajax.setError("删除邮件失败:" + e.getMessage());
        }

        return JSONTools.writeResponse(response, ajax);
    }

    /**
     * down mail attachment
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     * @throws IOException
     */
    public ActionForward downMailAttachment(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                            HttpServletResponse response)
        throws ServletException, IOException
    {
        String id = request.getParameter("id");

        AttachmentBean attachment = attachmentDAO.find(id);

        if (attachment == null)
        {
            return null;
        }

        String path = FileTools.formatPath2(getMailAttchmentPath()) + attachment.getPath();

        File file = new File(path);

        OutputStream out = response.getOutputStream();

        response.setContentType("application/x-dbf");

        response.setHeader("Content-Disposition", "attachment; filename="
                                                  + StringTools
                                                      .getStringBySet(attachment.getName(), "GBK", "ISO8859-1"));

        UtilStream us = new UtilStream(new FileInputStream(file), out);

        us.copyAndCloseStream();

        return null;
    }

    /**
     * @return the mailGroupFacade
     */
    public MailGroupFacade getMailGroupFacade()
    {
        return mailGroupFacade;
    }

    /**
     * @param mailGroupFacade
     *            the mailGroupFacade to set
     */
    public void setMailGroupFacade(MailGroupFacade mailGroupFacade)
    {
        this.mailGroupFacade = mailGroupFacade;
    }

    /**
     * @return the groupDAO
     */
    public GroupDAO getGroupDAO()
    {
        return groupDAO;
    }

    /**
     * @param groupDAO
     *            the groupDAO to set
     */
    public void setGroupDAO(GroupDAO groupDAO)
    {
        this.groupDAO = groupDAO;
    }

    /**
     * @return the groupVSStafferDAO
     */
    public GroupVSStafferDAO getGroupVSStafferDAO()
    {
        return groupVSStafferDAO;
    }

    /**
     * @param groupVSStafferDAO
     *            the groupVSStafferDAO to set
     */
    public void setGroupVSStafferDAO(GroupVSStafferDAO groupVSStafferDAO)
    {
        this.groupVSStafferDAO = groupVSStafferDAO;
    }

    /**
     * @return the mailDAO
     */
    public MailDAO getMailDAO()
    {
        return mailDAO;
    }

    /**
     * @param mailDAO
     *            the mailDAO to set
     */
    public void setMailDAO(MailDAO mailDAO)
    {
        this.mailDAO = mailDAO;
    }

    /**
     * @return the attachmentDAO
     */
    public AttachmentDAO getAttachmentDAO()
    {
        return attachmentDAO;
    }

    /**
     * @param attachmentDAO
     *            the attachmentDAO to set
     */
    public void setAttachmentDAO(AttachmentDAO attachmentDAO)
    {
        this.attachmentDAO = attachmentDAO;
    }

    /**
     * @return the mailDAO2
     */
    public MailDAO2 getMailDAO2()
    {
        return mailDAO2;
    }

    /**
     * @param mailDAO2
     *            the mailDAO2 to set
     */
    public void setMailDAO2(MailDAO2 mailDAO2)
    {
        this.mailDAO2 = mailDAO2;
    }

    /**
     * @return the mailAttchmentPath
     */
    public String getMailAttchmentPath()
    {
        return ConfigLoader.getProperty("mailAttchmentPath");
    }

    /**
     * @return the stafferDAO
     */
    public StafferDAO getStafferDAO()
    {
        return stafferDAO;
    }

    /**
     * @param stafferDAO
     *            the stafferDAO to set
     */
    public void setStafferDAO(StafferDAO stafferDAO)
    {
        this.stafferDAO = stafferDAO;
    }
}
