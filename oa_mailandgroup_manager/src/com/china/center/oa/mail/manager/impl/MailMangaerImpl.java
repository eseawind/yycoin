/**
 * File Name: MailMangaerImpl.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2010-7-4<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.mail.manager.impl;


import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.china.center.spring.ex.annotation.Exceptional;
import org.springframework.transaction.annotation.Transactional;

import com.center.china.osgi.publics.User;
import com.china.center.common.MYException;
import com.china.center.oa.gm.constant.MailConstant;
import com.china.center.oa.group.dao.GroupDAO;
import com.china.center.oa.group.dao.GroupVSStafferDAO;
import com.china.center.oa.group.vs.GroupVSStafferBean;
import com.china.center.oa.mail.bean.AttachmentBean;
import com.china.center.oa.mail.bean.MailBean;
import com.china.center.oa.mail.bean.MailBean2;
import com.china.center.oa.mail.dao.AttachmentDAO;
import com.china.center.oa.mail.dao.MailDAO;
import com.china.center.oa.mail.dao.MailDAO2;
import com.china.center.oa.mail.manager.MailMangaer;
import com.china.center.oa.publics.bean.StafferBean;
import com.china.center.oa.publics.constant.StafferConstant;
import com.china.center.oa.publics.dao.CommonDAO;
import com.china.center.oa.publics.dao.StafferDAO;
import com.china.center.tools.BeanUtil;
import com.china.center.tools.JudgeTools;
import com.china.center.tools.ListTools;
import com.china.center.tools.StringTools;
import com.china.center.tools.TimeTools;


/**
 * MailMangaerImpl
 * 
 * @author ZHUZHU
 * @version 2010-7-4
 * @see MailMangaerImpl
 * @since 1.0
 */
@Exceptional
public class MailMangaerImpl implements MailMangaer
{
    private MailDAO mailDAO = null;

    private AttachmentDAO attachmentDAO = null;

    private StafferDAO stafferDAO = null;

    private MailDAO2 mailDAO2 = null;

    private GroupDAO groupDAO = null;

    private GroupVSStafferDAO groupVSStafferDAO = null;

    private CommonDAO commonDAO = null;

    /**
     * default constructor
     */
    public MailMangaerImpl()
    {
    }

    @Transactional(rollbackFor = {MYException.class})
    public boolean addBean(User user, MailBean bean)
        throws MYException
    {
        return addMailWithoutTransactional(user, bean);
    }

    /**
     * @param user
     * @param bean
     * @return
     * @throws MYException
     */
    public boolean addMailWithoutTransactional(User user, MailBean bean)
        throws MYException
    {
        JudgeTools.judgeParameterIsNull(user, bean);

        checkAdd(user, bean);

        // change the mail and get the reveivers
        Set<String> reveivers = processMailAndGetReveives(bean);

        if (reveivers.size() == 0)
        {
            return true;
        }

        for (String eachItem : reveivers)
        {
            innerAddMail(bean, eachItem);
        }

        // process feeback
        if ( !StringTools.isNullOrNone(bean.getRefId()))
        {
            mailDAO.updateFeeback(bean.getRefId(), MailConstant.FEEBACK_YES);
        }

        // if system send mail,do not copy mail2
        if ( !StafferConstant.SUPER_STAFFER.equals(bean.getSenderId()))
        {
            // save mail to mail2
            saveMail2(bean);
        }

        return true;
    }

    /**
     * save mail to mail2
     * 
     * @param bean
     */
    private void saveMail2(MailBean bean)
    {
        MailBean2 mail2 = new MailBean2();

        BeanUtil.copyProperties(mail2, bean);

        mail2.setId(commonDAO.getSquenceString20());

        mailDAO2.saveEntityBean(mail2);

        // add acctachment
        if (mail2.getAttachment() == MailConstant.ATTACHMENT_YES)
        {
            List<AttachmentBean> atts = mail2.getAttachments();

            for (AttachmentBean attachmentBean : atts)
            {
                attachmentBean.setId(commonDAO.getSquenceString20());

                attachmentBean.setStafferId(mail2.getSenderId());

                attachmentBean.setMailId(mail2.getId());

                attachmentBean.setLogTime(mail2.getLogTime());

                attachmentDAO.saveEntityBean(attachmentBean);
            }
        }
    }

    /**
     * innerAddMail
     * 
     * @param bean
     * @param eachItem
     */
    private void innerAddMail(MailBean bean, String eachItem)
    {
        bean.setId(commonDAO.getSquenceString20());

        bean.setReveiveId(eachItem);

        bean.setStatus(MailConstant.STATUS_INIT);

        bean.setFeeback(MailConstant.FEEBACK_NO);

        bean.setLogTime(TimeTools.now());

        mailDAO.saveEntityBean(bean);

        // add acctachment
        if (bean.getAttachment() == MailConstant.ATTACHMENT_YES)
        {
            List<AttachmentBean> atts = bean.getAttachments();

            for (AttachmentBean attachmentBean : atts)
            {
                attachmentBean.setId(commonDAO.getSquenceString20());

                attachmentBean.setStafferId(bean.getReveiveId());

                attachmentBean.setMailId(bean.getId());

                attachmentBean.setLogTime(bean.getLogTime());

                attachmentDAO.saveEntityBean(attachmentBean);
            }
        }
    }

    /**
     * deleteBean
     */
    @Transactional(rollbackFor = {MYException.class})
    public boolean deleteBean(User user, String id)
        throws MYException
    {
        JudgeTools.judgeParameterIsNull(user, id);

        checkDel(user, id);

        mailDAO.deleteEntityBean(id);

        // delete attachment
        attachmentDAO.deleteEntityBeansByFK(id);

        return true;
    }

    /**
     * deleteBean2
     * 
     * @param user
     * @param id
     * @return
     * @throws MYException
     */
    @Transactional(rollbackFor = {MYException.class})
    public boolean deleteBean2(User user, String id)
        throws MYException
    {
        JudgeTools.judgeParameterIsNull(user, id);

        checkDel2(user, id);

        mailDAO2.deleteEntityBean(id);

        // delete attachment
        attachmentDAO.deleteEntityBeansByFK(id);

        return true;
    }

    /**
     * check weather delete
     * 
     * @param user
     * @param id
     * @throws MYException
     */
    private void checkDel(User user, String id)
        throws MYException
    {
        MailBean old = mailDAO.find(id);

        if (old == null)
        {
            throw new MYException("邮件不存在");
        }

        if ( !user.getStafferId().equals(old.getReveiveId()))
        {
            throw new MYException("只能删除自己的邮件");
        }
    }

    /**
     * checkDel2
     * 
     * @param user
     * @param id
     * @throws MYException
     */
    private void checkDel2(User user, String id)
        throws MYException
    {
        MailBean2 old = mailDAO2.find(id);

        if (old == null)
        {
            throw new MYException("邮件不存在");
        }

        if ( !user.getStafferId().equals(old.getSenderId()))
        {
            throw new MYException("只能删除自己的邮件");
        }
    }

    @Transactional(rollbackFor = {MYException.class})
    public boolean updateBean(User user, MailBean bean)
        throws MYException
    {
        return true;
    }

    /**
     * updateStatus
     * 
     * @param user
     * @param id
     * @return
     * @throws MYException
     */
    @Transactional(rollbackFor = {MYException.class})
    public boolean updateStatusToRead(User user, String id)
        throws MYException
    {
        JudgeTools.judgeParameterIsNull(user, id);

        checkUpdateStatus(user, id);

        mailDAO.updateStatus(id, MailConstant.STATUS_READ);

        return true;
    }

    /**
     * checkupdateStatus
     * 
     * @param user
     * @param id
     * @throws MYException
     */
    private void checkUpdateStatus(User user, String id)
        throws MYException
    {
        MailBean old = mailDAO.find(id);

        if (old == null)
        {
            throw new MYException("邮件不存在");
        }

        if ( !user.getStafferId().equals(old.getReveiveId()))
        {
            throw new MYException("只能更新自己的邮件");
        }
    }

    /**
     * updateFeebackToYes
     * 
     * @param user
     * @param id
     * @return
     * @throws MYException
     */
    @Transactional(rollbackFor = {MYException.class})
    public boolean updateFeebackToYes(User user, String id)
        throws MYException
    {
        JudgeTools.judgeParameterIsNull(user, id);

        checkFeebackToYes(user, id);

        mailDAO.updateFeeback(id, MailConstant.FEEBACK_YES);

        return true;
    }

    /**
     * checkFeebackToYes
     * 
     * @param user
     * @param id
     * @throws MYException
     */
    private void checkFeebackToYes(User user, String id)
        throws MYException
    {
        MailBean old = mailDAO.find(id);

        if (old == null)
        {
            throw new MYException("邮件不存在");
        }

        if ( !user.getStafferId().equals(old.getReveiveId()))
        {
            throw new MYException("只能更新自己的邮件");
        }
    }

    /**
     * checkAdd
     * 
     * @param user
     * @param bean
     */
    private void checkAdd(User user, MailBean bean)
        throws MYException
    {
        StafferBean sb = stafferDAO.find(bean.getSenderId());

        if (sb == null)
        {
            throw new MYException("发件人不存在");
        }

        bean.setSenderName(sb.getName());
    }

    /**
     * 解析收件人,同时获取收件人的名称
     * 
     * @param bean
     * @return
     */
    private Set<String> processMailAndGetReveives(MailBean bean)
    {
        Set<String> reveives = new HashSet<String>();

        StringBuilder ids = new StringBuilder();

        bean.setReveiveNames(parserReveive(reveives, bean.getReveiveIds(), ids));

        bean.setReveiveIds(ids.toString());

        bean.setReveiveNames2(parserReveive(reveives, bean.getReveiveIds2(), ids));

        bean.setReveiveIds2(ids.toString());

        bean.setReveiveNames3(parserReveive(reveives, bean.getReveiveIds3(), ids));

        bean.setReveiveIds3(ids.toString());

        if (ListTools.isEmptyOrNull(bean.getAttachments()))
        {
            bean.setAttachment(MailConstant.ATTACHMENT_NO);
        }
        else
        {
            bean.setAttachment(MailConstant.ATTACHMENT_YES);
        }

        return reveives;
    }

    /**
     * parserReveive
     * 
     * @param reveives
     * @param temp
     * @param ids
     */
    private String parserReveive(Set<String> reveives, String temp, StringBuilder ids)
    {
        ids.delete(0, ids.length());

        if (StringTools.isNullOrNone(temp))
        {
            return "";
        }

        StringBuilder builder = new StringBuilder();

        String[] ss = temp.split(";");

        for (String string : ss)
        {
            if (StringTools.isNullOrNone(string))
            {
                continue;
            }

            StafferBean sb = stafferDAO.find(string);

            if (sb != null)
            {
                if (sb.getStatus() == StafferConstant.STATUS_COMMON && reveives.add(string))
                {
                    builder.append(sb.getName()).append(';');

                    ids.append(sb.getId()).append(';');
                }
            }
            else
            {
                // gropu
                List<GroupVSStafferBean> sds = groupVSStafferDAO.queryEntityBeansByFK(string);

                for (GroupVSStafferBean groupVSStafferBean : sds)
                {
                    StafferBean sbTemp = stafferDAO.find(groupVSStafferBean.getStafferId());

                    if (sbTemp != null && sbTemp.getStatus() == StafferConstant.STATUS_COMMON)
                    {
                        if (reveives.add(groupVSStafferBean.getStafferId()))
                        {
                            builder.append(sbTemp.getName()).append(';');

                            ids.append(sbTemp.getId()).append(';');
                        }
                    }
                }
            }
        }

        if (builder.length() > MailConstant.MAX_LENGTH)
        {
            builder.delete(MailConstant.MAX_LENGTH, builder.length());
        }

        if (ids.length() > MailConstant.MAX_LENGTH)
        {
            ids.delete(MailConstant.MAX_LENGTH, ids.length());
        }

        return builder.toString();

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
     * @return the commonDAO
     */
    public CommonDAO getCommonDAO()
    {
        return commonDAO;
    }

    /**
     * @param commonDAO
     *            the commonDAO to set
     */
    public void setCommonDAO(CommonDAO commonDAO)
    {
        this.commonDAO = commonDAO;
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
}
