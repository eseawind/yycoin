/**
 * File Name: TemplateFileAction.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: zhuAchen<br>
 * CreateTime: 2009-4-21<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.flow.action;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Random;

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
import com.china.center.actionhelper.common.PageSeparateTools;
import com.china.center.actionhelper.json.AjaxResult;
import com.china.center.common.MYException;
import com.china.center.jdbc.util.ConditionParse;
import com.china.center.jdbc.util.PageSeparate;
import com.china.center.oa.flow.bean.TemplateFileBean;
import com.china.center.oa.flow.dao.TemplateFileDAO;
import com.china.center.oa.flow.facade.WorkFlowFacade;
import com.china.center.oa.publics.Helper;
import com.china.center.tools.BeanUtil;
import com.china.center.tools.CommonTools;
import com.china.center.tools.FileTools;
import com.china.center.tools.HTTPTools;
import com.china.center.tools.RequestDataStream;
import com.china.center.tools.SequenceTools;
import com.china.center.tools.StringTools;
import com.china.center.tools.TimeTools;
import com.china.center.tools.UtilStream;


/**
 * TemplateFileAction
 * 
 * @author ZHUZHU
 * @version 2009-4-21
 * @see TemplateFileAction
 * @since 1.0
 */
public class TemplateFileAction extends DispatchAction
{
    private final Log _logger = LogFactory.getLog(getClass());

    private WorkFlowFacade workFlowFacade = null;

    private TemplateFileDAO templateFileDAO = null;

    private static String QUERYTEMPLATEFILE = "queryTemplateFile";

    private static String RPTQUERYTEMPLATEFILE = "rptQueryTemplateFile";

    /**
     * queryTemplateFile
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward queryTemplateFile(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                           HttpServletResponse response)
        throws ServletException
    {
        final ConditionParse condtion = new ConditionParse();

        condtion.addWhereStr();

        ActionTools.processJSONQueryCondition(QUERYTEMPLATEFILE, request, condtion);

        String jsonstr = ActionTools.queryVOByJSONAndToString(QUERYTEMPLATEFILE, request, condtion,
            this.templateFileDAO);

        return JSONTools.writeResponse(response, jsonstr);
    }

    /**
     * findTemplateFile
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward findTemplateFile(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                          HttpServletResponse response)
        throws ServletException
    {
        CommonTools.saveParamers(request);

        String id = request.getParameter("id");

        String update = request.getParameter("update");

        TemplateFileBean bean = templateFileDAO.find(id);

        if (bean == null)
        {
            return ActionTools.toError("流程模板不存在", mapping, request);
        }

        request.setAttribute("bean", bean);

        String url = HTTPTools.getHTTPURL(request) + this.getEditServerName() + "/" + request.getSession().getId()
                     + "/template" + bean.getPath();

        request.setAttribute("url", url);

        if ("1".equals(update))
        {
            return mapping.findForward("updateTemplateFile");
        }

        return mapping.findForward("detailTemplateFile");
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
    public ActionForward addTemplateFile(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                         HttpServletResponse response)
        throws ServletException
    {
        // 模板最多1M
        RequestDataStream rds = new RequestDataStream(request, 1024 * 1024 * 1L);

        try
        {
            rds.parser();
        }
        catch (FileUploadBase.SizeLimitExceededException e)
        {
            _logger.error(e, e);

            request.setAttribute(KeyConstant.ERROR_MESSAGE, "增加模板失败:附件超过1M");

            return mapping.findForward("queryTemplateFile");
        }
        catch (Exception e)
        {
            _logger.error(e, e);

            request.setAttribute(KeyConstant.ERROR_MESSAGE, "增加模板失败");

            return mapping.findForward("queryTemplateFile");
        }

        TemplateFileBean bean = new TemplateFileBean();

        ActionForward afor = parserAttachment(mapping, request, rds, bean);

        if (afor != null)
        {
            return afor;
        }

        rds.close();

        try
        {
            BeanUtil.getBean(bean, rds.getParmterMap());

            User user = Helper.getUser(request);

            workFlowFacade.addTemplateFile(user.getId(), bean);

            request.setAttribute(KeyConstant.MESSAGE, "成功保存模板");
        }
        catch (MYException e)
        {
            _logger.warn(e, e);

            request.setAttribute(KeyConstant.ERROR_MESSAGE, "保存模板失败:" + e.getMessage());
        }

        return mapping.findForward("queryTemplateFile");
    }

    /**
     * updatedTemplateFile
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward updateTemplateFile(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                            HttpServletResponse response)
        throws ServletException
    {
        TemplateFileBean bean = new TemplateFileBean();

        try
        {
            BeanUtil.getBean(bean, request);

            User user = Helper.getUser(request);

            workFlowFacade.updateTemplateFile(user.getId(), bean);

            request.setAttribute(KeyConstant.MESSAGE, "成功保存模板");
        }
        catch (MYException e)
        {
            _logger.warn(e, e);

            request.setAttribute(KeyConstant.ERROR_MESSAGE, "保存模板失败:" + e.getMessage());
        }

        return mapping.findForward("queryTemplateFile");
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
    public ActionForward deleteTemplateFile(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                            HttpServletResponse response)
        throws ServletException
    {
        String id = request.getParameter("id");

        AjaxResult ajax = new AjaxResult();

        try
        {
            TemplateFileBean bean = templateFileDAO.find(id);

            if (bean == null)
            {
                return ActionTools.toError("流程模板不存在", mapping, request);
            }

            User user = Helper.getUser(request);

            if (workFlowFacade.deleteTemplateFile(user.getId(), id))
            {
                // 删除模板文件
                File file = new File(this.getTemplateFilePath() + bean.getPath());

                file.delete();

                _logger.info("成功删除文件:" + this.getTemplateFilePath() + bean.getPath());
            }

            ajax.setSuccess("成功删除流程模板和文件:" + bean.getFileName());
        }
        catch (MYException e)
        {
            _logger.warn(e, e);

            ajax.setError("删除流程模板失败:" + e.getMessage());
        }

        return JSONTools.writeResponse(response, ajax);
    }

    /**
     * 流程模板的查询
     * 
     * @param mapping
     * @param form
     * @param request
     * @param reponse
     * @return
     * @throws ServletException
     */
    public ActionForward rptQueryTemplateFile(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                              HttpServletResponse reponse)
        throws ServletException
    {
        CommonTools.saveParamers(request);

        String urlPfix = HTTPTools.getHTTPURL(request) + this.getEditServerName();

        List<TemplateFileBean> list = null;

        if (PageSeparateTools.isFirstLoad(request))
        {
            ConditionParse condtion = new ConditionParse();

            condtion.addWhereStr();

            setTemplateFileInnerCondition(request, condtion);

            int total = templateFileDAO.countByCondition(condtion.toString());

            PageSeparate page = new PageSeparate(total, 10);

            PageSeparateTools.initPageSeparate(condtion, page, request, RPTQUERYTEMPLATEFILE);

            list = templateFileDAO.queryEntityVOsByCondition(condtion, page);
        }
        else
        {
            PageSeparateTools.processSeparate(request, RPTQUERYTEMPLATEFILE);

            list = templateFileDAO.queryEntityVOsByCondition(PageSeparateTools.getCondition(request,
                RPTQUERYTEMPLATEFILE), PageSeparateTools.getPageSeparate(request, RPTQUERYTEMPLATEFILE));
        }

        request.setAttribute("beanList", list);

        request.setAttribute("urlPfix", urlPfix);

        return mapping.findForward("rptQueryTemplateFile");
    }

    /**
     * setTemplateFileInnerCondition
     * 
     * @param request
     * @param condtion
     */
    private void setTemplateFileInnerCondition(HttpServletRequest request, ConditionParse condtion)
    {
        String name = request.getParameter("name");

        if ( !StringTools.isNullOrNone(name))
        {
            condtion.addCondition("name", "like", name);
        }
    }

    /**
     * parserAttachment
     * 
     * @param mapping
     * @param request
     * @param rds
     * @param bean
     */
    private ActionForward parserAttachment(ActionMapping mapping, HttpServletRequest request, RequestDataStream rds,
                                           TemplateFileBean bean)
    {
        // parser attachment
        if (rds.haveStream())
        {
            FileOutputStream out = null;

            UtilStream ustream = null;

            try
            {
                String savePath = mkdir();

                String fileAlais = SequenceTools.getSequence();

                String rabsPath = '/'
                                  + savePath
                                  + '/'
                                  + fileAlais
                                  + "."
                                  + FileTools
                                      .getFilePostfix(FileTools.getFileName(rds.getUniqueFileName()))
                                      .toLowerCase();

                String filePath = this.getTemplateFilePath() + '/' + rabsPath;

                bean.setFileName(FileTools.getFileName(rds.getUniqueFileName()));

                bean.setPath(rabsPath);

                out = new FileOutputStream(filePath);

                ustream = new UtilStream(rds.getUniqueInputStream(), out);

                ustream.copyStream();
            }
            catch (IOException e)
            {
                _logger.error(e, e);

                request.setAttribute(KeyConstant.ERROR_MESSAGE, "保存模板失败");

                return mapping.findForward("queryMail");
            }
            finally
            {
                if (ustream != null)
                {
                    try
                    {
                        ustream.close();
                    }
                    catch (IOException e)
                    {
                        _logger.error(e, e);
                    }
                }
            }
        }
        else
        {
            request.setAttribute(KeyConstant.ERROR_MESSAGE, "增加模板失败:没有附件");

            return mapping.findForward("queryTemplateFile");
        }

        return null;
    }

    private String mkdir()
    {
        String path = TimeTools.now("yyyy/MM/dd/HH") + "/"
                      + SequenceTools.getSequence(String.valueOf(new Random().nextInt(1000)));

        FileTools.mkdirs(this.getTemplateFilePath() + '/' + path);

        return path;
    }

    /**
     * default constructor
     */
    public TemplateFileAction()
    {
    }

    /**
     * @return the workFlowFacade
     */
    public WorkFlowFacade getWorkFlowFacade()
    {
        return workFlowFacade;
    }

    /**
     * @param workFlowFacade
     *            the workFlowFacade to set
     */
    public void setWorkFlowFacade(WorkFlowFacade workFlowFacade)
    {
        this.workFlowFacade = workFlowFacade;
    }

    /**
     * @return the templateFileDAO
     */
    public TemplateFileDAO getTemplateFileDAO()
    {
        return templateFileDAO;
    }

    /**
     * @param templateFileDAO
     *            the templateFileDAO to set
     */
    public void setTemplateFileDAO(TemplateFileDAO templateFileDAO)
    {
        this.templateFileDAO = templateFileDAO;
    }

    /**
     * @return the templateFilePath
     */
    public String getTemplateFilePath()
    {
        return ConfigLoader.getProperty("templateFilePath");
    }

    /**
     * @return the editServerName
     */
    public String getEditServerName()
    {
        return ConfigLoader.getProperty("editServerName");
    }
}
