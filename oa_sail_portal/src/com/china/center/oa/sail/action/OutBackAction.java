package com.china.center.oa.sail.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
import com.china.center.actionhelper.json.AjaxResult;
import com.china.center.actionhelper.query.HandleResult;
import com.china.center.common.MYException;
import com.china.center.jdbc.util.ConditionParse;
import com.china.center.oa.publics.Helper;
import com.china.center.oa.publics.bean.AttachmentBean;
import com.china.center.oa.publics.dao.AttachmentDAO;
import com.china.center.oa.sail.bean.OutBackBean;
import com.china.center.oa.sail.bean.TransportBean;
import com.china.center.oa.sail.constanst.OutConstant;
import com.china.center.oa.sail.constanst.SailConstant;
import com.china.center.oa.sail.dao.ConsignDAO;
import com.china.center.oa.sail.dao.OutBackDAO;
import com.china.center.oa.sail.manager.OutBackManager;
import com.china.center.oa.sail.vo.OutBackVO;
import com.china.center.tools.BeanUtil;
import com.china.center.tools.CommonTools;
import com.china.center.tools.FileTools;
import com.china.center.tools.RequestDataStream;
import com.china.center.tools.SequenceTools;
import com.china.center.tools.StringTools;
import com.china.center.tools.TimeTools;
import com.china.center.tools.UtilStream;

public class OutBackAction extends DispatchAction
{
	private final Log _logger = LogFactory.getLog(getClass());

	private ConsignDAO consignDAO = null;
	
	private OutBackDAO outBackDAO = null;
	
	private OutBackManager outBackManager = null;
	
	private AttachmentDAO attachmentDAO = null;
	
	private final static String QUERYOUTBACK = "queryOutBack";
	
	public OutBackAction()
	{
	}
	
	/**
	 * queryOutBack
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 */
	public ActionForward queryOutBack(ActionMapping mapping, ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response)
	throws ServletException
	{
		User user = Helper.getUser(request);
		
    	String mode = request.getParameter("mode");
    	
    	if (StringTools.isNullOrNone(mode))
    		mode = "0";
    	
        ConditionParse condtion = new ConditionParse();

        condtion.addWhereStr();

        Map<String, String> initMap = new HashMap<String, String>();
        
        if (mode.equals("1"))
        {
//        	String status = request.getParameter("status");
//        	
//        	if (StringTools.isNullOrNone(status))
//        	{
//        		initMap.put("status", String.valueOf(OutConstant.OUTBACK_STATUS_CLAIM));
//        		
//        		condtion.addIntCondition("OutBackBean.status", "=", OutConstant.OUTBACK_STATUS_CLAIM);
//        	}
        }	
        else if (mode.equals("2"))
        {
        	condtion.addIntCondition("OutBackBean.status", "=", OutConstant.OUTBACK_STATUS_CHECK);
        }
        else if (mode.equals("98"))
        {
        	condtion.addCondition("OutBackBean.claimer", "=", user.getStafferName());
        }
        else if (mode.equals("3"))
        {
        	condtion.addIntCondition("OutBackBean.status", "=", OutConstant.OUTBACK_STATUS_IN);
        }
        	
		ActionTools.processJSONDataQueryCondition(QUERYOUTBACK, request, condtion, initMap);

        String jsonstr = ActionTools.queryVOByJSONAndToString(QUERYOUTBACK, request, condtion, this.outBackDAO, 
        		new HandleResult<OutBackVO>()
				{
					public void handle(OutBackVO vo)
					{
						vo.setFromFullAddress(vo.getFromProvinceName() + vo.getFromCityName() + vo.getFromAddress());
						vo.setToFullAddress(vo.getToProvinceName() + vo.getToCityName() + vo.getToAddress());
						
					}
        			
				}
        );

        return JSONTools.writeResponse(response, jsonstr);
	}
	
	/**
	 * preForAddOutBack
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 */
	public ActionForward preForAddOutBack(ActionMapping mapping, ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response)
	throws ServletException
	{
		prepare(request);
        
		return mapping.findForward("addOutBack");
	}

	private void prepare(HttpServletRequest request)
	{
		List<TransportBean> list = consignDAO.queryTransportByType(SailConstant.TRANSPORT_COMMON);

        request.setAttribute("transportList", list);
	}

	/**
	 * addOutBack
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 */
	public ActionForward addOutBack(ActionMapping mapping, ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response)
	throws ServletException
	{
        OutBackBean bean = new OutBackBean();
        
        User user = Helper.getUser(request);
        
        BeanUtil.getBean(bean, request);
        
        try
        {
            bean.setStafferId(user.getStafferId());
            bean.setStafferName(user.getStafferName());
            
            bean.setLogTime(TimeTools.now());

            bean.setStatus(OutConstant.OUTBACK_STATUS_CLAIM);

            outBackManager.addOutBack(user, bean);

            request.setAttribute(KeyConstant.MESSAGE, "成功保存");
        }
        catch (MYException e)
        {
            _logger.warn(e, e);

            request.setAttribute(KeyConstant.ERROR_MESSAGE, "保存失败:" + e.getErrorContent());
        }
		
		return mapping.findForward("queryOutBack");
	}
	
	/**
	 * updateOutBack
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 */
	public ActionForward updateOutBack(ActionMapping mapping, ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response)
	throws ServletException
	{
		String id = request.getParameter("id");
		
		OutBackBean oldBean = outBackDAO.find(id);
		
		if (null == oldBean)
		{
			return ActionTools.toError("数据异常,请重新操作", "queryOutBack", mapping, request);
		}
		
		if (oldBean.getStatus() != OutConstant.OUTBACK_STATUS_CLAIM)
		{
			return ActionTools.toError("不是待认领状态,请确认", "queryOutBack", mapping, request);
		}
		
        OutBackBean bean = new OutBackBean();
        
        User user = Helper.getUser(request);
        
        BeanUtil.getBean(bean, request);
        
        try
        {
            bean.setStafferId(user.getStafferId());
            bean.setStafferName(user.getStafferName());
            
            bean.setLogTime(TimeTools.now());

            bean.setStatus(OutConstant.OUTBACK_STATUS_CLAIM);

            outBackManager.updateOutBack(user, bean);

            request.setAttribute(KeyConstant.MESSAGE, "成功修改");
        }
        catch (MYException e)
        {
            _logger.warn(e, e);

            request.setAttribute(KeyConstant.ERROR_MESSAGE, "修改失败:" + e.getErrorContent());
        }
		
		return mapping.findForward("queryOutBack");
	}
	
	/**
	 * deleteOutBack
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 */
	public ActionForward deleteOutBack(ActionMapping mapping, ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response)
	throws ServletException
	{
		AjaxResult ajax = new AjaxResult();
		
		String id = request.getParameter("id");
		
        try
        {
        	User user = Helper.getUser(request);
        	
            outBackManager.deleteOutBack(user, id);

            ajax.setSuccess("删除成功");
        }
        catch (MYException e)
        {
            _logger.warn(e, e);

            ajax.setError("保存失败:" + e.getErrorContent());
        }
		
		return JSONTools.writeResponse(response, ajax);
	}
	
	/**
	 * findOutBack
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 */
	public ActionForward findOutBack(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
	throws ServletException
	{
        String id = request.getParameter("id");

        OutBackVO bean = outBackDAO.findVO(id);

        if (bean == null)
        {
            return ActionTools.toError("数据异常,请重新操作", "queryOutBack", mapping, request);
        }

        request.setAttribute("bean", bean);

        String update = request.getParameter("update");

        prepare(request);
        
        List<AttachmentBean> attachmentList = attachmentDAO.queryEntityBeansByFK(id);
        
        bean.setAttachmentList(attachmentList);
        
        if ("1".equals(update))
        {
            return mapping.findForward("updateOutBack");
        }
        else if ("2".equals(update))
        {
        	return mapping.findForward("claimOutBack");
        }
        else if ("3".equals(update))
        	return mapping.findForward("checkOutBack");

        return mapping.findForward("detailOutBack");
	}
	
	/**
	 * unclaimOutBack
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 */
	public ActionForward unclaimOutBack(ActionMapping mapping, ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response)
	throws ServletException
	{
		AjaxResult ajax = new AjaxResult();
		
		String id = request.getParameter("id");
		
        try
        {
        	User user = Helper.getUser(request);
        	
            outBackManager.unclaimOutBack(user, id);

            ajax.setSuccess("退领成功");
        }
        catch (MYException e)
        {
            _logger.warn(e, e);

            ajax.setError("退领失败:" + e.getErrorContent());
        }
		
		return JSONTools.writeResponse(response, ajax);
	}
	
	/**
	 * claimOutBack
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 */
	public ActionForward claimOutBack(ActionMapping mapping, ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response)
	throws ServletException
	{
		CommonTools.saveParamers(request);
		
		// 模板最多3M
        RequestDataStream rds = new RequestDataStream(request, 1024 * 1024 * 3L);

        try
        {
            rds.parser();
        }
        catch (FileUploadBase.SizeLimitExceededException e)
        {
            _logger.error(e, e);

            request.setAttribute(KeyConstant.ERROR_MESSAGE, "增加失败:附件超过3M");

            return mapping.findForward("queryOutBack");
        }
        catch (Exception e)
        {
            _logger.error(e, e);

            request.setAttribute(KeyConstant.ERROR_MESSAGE, "增加附件失败");

            return mapping.findForward("queryOutBack");
        }

		String id = rds.getParameter("id");

		OutBackBean bean = outBackDAO.find(id);
		
		if (null == bean)
		{
			return ActionTools.toError("数据异常,请重新操作", "queryOutBack", mapping, request);
		}
		
		if (bean.getStatus() != OutConstant.OUTBACK_STATUS_CLAIM)
		{
			return ActionTools.toError("不是待认领状态,请确认", "queryOutBack", mapping, request);
		}
        
        ActionForward afor = parserAttachment(mapping, request, rds, bean);

        if (afor != null)
        {
            return afor;
        }

        rds.close();
		
        try
        {
        	User user = Helper.getUser(request);
        	
            bean.setClaimer(user.getStafferName());
            
            bean.setClaimTime(TimeTools.now());

            bean.setStatus(OutConstant.OUTBACK_STATUS_CHECK);

            outBackManager.claimOutBack(user, bean);

            request.setAttribute(KeyConstant.MESSAGE, "成功认领");
        }
        catch (MYException e)
        {
            _logger.warn(e, e);

            request.setAttribute(KeyConstant.ERROR_MESSAGE, "认领失败:" + e.getErrorContent());
        }
		
        request.setAttribute("mode", 1);
        
		return mapping.findForward("queryOutBack");
	}
	
	/**
	 * checkOutBack
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 */
	public ActionForward checkOutBack(ActionMapping mapping, ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response)
	throws ServletException
	{
		CommonTools.saveParamers(request);
		
		String id = request.getParameter("id");
		
		String reason = request.getParameter("reason");
		
		OutBackBean oldBean = outBackDAO.find(id);
		
		if (null == oldBean)
		{
			return ActionTools.toError("数据异常,请重新操作", "queryOutBack", mapping, request);
		}
		
		if (oldBean.getStatus() != OutConstant.OUTBACK_STATUS_CHECK)
		{
			return ActionTools.toError("不是待验货状态,请确认", "queryOutBack", mapping, request);
		}
		
        User user = Helper.getUser(request);
        
        try
        {
            outBackManager.checkOutBack(user, id, reason);

            request.setAttribute(KeyConstant.MESSAGE, "成功验货");
        }
        catch (MYException e)
        {
            _logger.warn(e, e);

            request.setAttribute(KeyConstant.ERROR_MESSAGE, "验货失败:" + e.getErrorContent());
        }
		
		return mapping.findForward("queryOutBack");
	}
	
	/**
     * parserAttachment
     * 
     * @param mapping
     * @param request
     * @param rds
     * @param bean
     * @return
     */
    private ActionForward parserAttachment(ActionMapping mapping, HttpServletRequest request,
                                           RequestDataStream rds, OutBackBean outBackBean)
    {
        List<AttachmentBean> attachmentList = new ArrayList<AttachmentBean>();

        outBackBean.setAttachmentList(attachmentList);

        // parser attachment
        if ( !rds.haveStream())
        {
            return null;
        }

        Map<String, InputStream> streamMap = rds.getStreamMap();

        for (Map.Entry<String, InputStream> entry : streamMap.entrySet())
        {
            AttachmentBean bean = new AttachmentBean();

            FileOutputStream out = null;

            UtilStream ustream = null;

            try
            {
                String savePath = mkdir(this.getAttachmentPath());

                String fileAlais = SequenceTools.getSequence();

                String fileName = FileTools.getFileName(rds.getFileName(entry.getKey()));

                String rabsPath = '/' + savePath + '/' + fileAlais + "."
                                  + FileTools.getFilePostfix(fileName).toLowerCase();

                String filePath = this.getAttachmentPath() + '/' + rabsPath;

                bean.setName(fileName);

                bean.setPath(rabsPath);

                bean.setLogTime(TimeTools.now());

                out = new FileOutputStream(filePath);

                ustream = new UtilStream(entry.getValue(), out);

                ustream.copyStream();

                attachmentList.add(bean);
            }
            catch (IOException e)
            {
                _logger.error(e, e);

                request.setAttribute(KeyConstant.ERROR_MESSAGE, "保存失败");

                return mapping.findForward("queryOutBack");
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

        return null;
    }
    
    /**
     * @return the flowAtt
     */
    public String getAttachmentPath()
    {
        return ConfigLoader.getProperty("outBackAttachmentPath");
    }
    
    private String mkdir(String root)
    {
        String path = TimeTools.now("yyyy/MM/dd/HH") + "/"
                      + SequenceTools.getSequence(String.valueOf(new Random().nextInt(1000)));

        FileTools.mkdirs(root + '/' + path);

        return path;
    }
	
    /**
     * downAttachmentFile
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     * @throws IOException
     */
    public ActionForward downAttachmentFile(ActionMapping mapping, ActionForm form,
                                            HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException
    {
        String path = getAttachmentPath();
        String id = request.getParameter("id");
        AttachmentBean bean = attachmentDAO.find(id);

        if (bean == null)
        {
            return ActionTools.toError(mapping, request);
        }

        path += bean.getPath();

        File file = new File(path);
        OutputStream out = response.getOutputStream();

        response.setContentType("application/x-dbf");

        response.setHeader("Content-Disposition", "attachment; filename="
                                                  + StringTools.getStringBySet(bean.getName(),
                                                      "GBK", "ISO8859-1"));

        UtilStream us = new UtilStream(new FileInputStream(file), out);

        us.copyAndCloseStream();

        return null;
    }
    
    /**
     * finishOutBack
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward finishOutBack(ActionMapping mapping, ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response)
	throws ServletException
	{
		AjaxResult ajax = new AjaxResult();
		
		String id = request.getParameter("id");
		
        try
        {
        	User user = Helper.getUser(request);
        	
            outBackManager.finishOutBack(user, id);

            ajax.setSuccess("处理成功");
        }
        catch (MYException e)
        {
            _logger.warn(e, e);

            ajax.setError("处理失败:" + e.getErrorContent());
        }
		
		return JSONTools.writeResponse(response, ajax);
	}
    
	/**
	 * @return the consignDAO
	 */
	public ConsignDAO getConsignDAO()
	{
		return consignDAO;
	}

	/**
	 * @param consignDAO the consignDAO to set
	 */
	public void setConsignDAO(ConsignDAO consignDAO)
	{
		this.consignDAO = consignDAO;
	}

	/**
	 * @return the outBackDAO
	 */
	public OutBackDAO getOutBackDAO()
	{
		return outBackDAO;
	}

	/**
	 * @param outBackDAO the outBackDAO to set
	 */
	public void setOutBackDAO(OutBackDAO outBackDAO)
	{
		this.outBackDAO = outBackDAO;
	}

	/**
	 * @return the outBackManager
	 */
	public OutBackManager getOutBackManager()
	{
		return outBackManager;
	}

	/**
	 * @param outBackManager the outBackManager to set
	 */
	public void setOutBackManager(OutBackManager outBackManager)
	{
		this.outBackManager = outBackManager;
	}

	/**
	 * @return the attachmentDAO
	 */
	public AttachmentDAO getAttachmentDAO()
	{
		return attachmentDAO;
	}

	/**
	 * @param attachmentDAO the attachmentDAO to set
	 */
	public void setAttachmentDAO(AttachmentDAO attachmentDAO)
	{
		this.attachmentDAO = attachmentDAO;
	}
}
