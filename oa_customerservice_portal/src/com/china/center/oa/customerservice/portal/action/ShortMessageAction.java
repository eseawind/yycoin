package com.china.center.oa.customerservice.portal.action;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.center.china.osgi.publics.User;
import com.center.china.osgi.publics.file.read.ReadeFileFactory;
import com.center.china.osgi.publics.file.read.ReaderFile;
import com.center.china.osgi.publics.file.writer.WriteFile;
import com.center.china.osgi.publics.file.writer.WriteFileFactory;
import com.china.center.actionhelper.common.ActionTools;
import com.china.center.actionhelper.common.JSONPageSeparateTools;
import com.china.center.actionhelper.common.JSONTools;
import com.china.center.actionhelper.common.KeyConstant;
import com.china.center.common.MYException;
import com.china.center.jdbc.util.ConditionParse;
import com.china.center.jdbc.util.PageSeparate;
import com.china.center.oa.customerservice.bean.ShortMessageBean;
import com.china.center.oa.customerservice.constant.ShortMessageConstant;
import com.china.center.oa.customerservice.dao.ShortMessageDAO;
import com.china.center.oa.customerservice.manager.ShortMessageManager;
import com.china.center.oa.publics.Helper;
import com.china.center.osgi.jsp.ElTools;
import com.china.center.tools.MathTools;
import com.china.center.tools.RequestDataStream;
import com.china.center.tools.StringTools;
import com.china.center.tools.TimeTools;
import com.china.center.tools.WriteFileBuffer;

public class ShortMessageAction extends DispatchAction
{
	private final Log _logger = LogFactory.getLog(getClass());
	
	private ShortMessageDAO shortMessageDAO = null;
	
	private ShortMessageManager shortMessageManager = null;
	
	private final static String QUERYSHORTMESSAGE = "queryShortMessage";
	
	public ShortMessageAction(){
		
	}
	
	/**
	 * 发送短信
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 */
	public ActionForward addShortMessage(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
	throws ServletException
	{
		RequestDataStream rds = new RequestDataStream(request);

		try
        {
            rds.parser();
        }
        catch (Exception e1)
        {
			request.setAttribute(KeyConstant.ERROR_MESSAGE, "导入失败，文件解析失败");

			return mapping.findForward("error");
        }
		
		User user = Helper.getUser(request);

		int stype = MathTools.parseInt(rds.getParameter("stype"));

		List<ShortMessageBean> smbList = new ArrayList<ShortMessageBean>();
		
		ShortMessageBean bean = new ShortMessageBean();
		
		bean.setSmode(1);
		bean.setStafferName(user.getStafferName());
		bean.setStype(stype);
		bean.setLogTime(TimeTools.now());

		if (stype == ShortMessageConstant.MODE_SINGLE)
		{
			String mobile0 = rds.getParameter("mobile0");
			String smType0 = rds.getParameter("smType0");
			String content0 = rds.getParameter("content0");
			
			bean.setMobile(mobile0);
			bean.setSmType(MathTools.parseInt(smType0));
			bean.setContent(content0 + "");
			
		}else if (stype == ShortMessageConstant.MODE_IMPORT)
		{
			try
			{
				smbList = importSMS(user, rds);
			}
			catch (MYException e)
			{
				request.setAttribute(KeyConstant.ERROR_MESSAGE, "导入失败，" + e.getErrorContent());

				return mapping.findForward("error");
			}
			
		}else if (stype == ShortMessageConstant.MODE_MULTI)
		{
			String mobile1 = rds.getParameter("mobile1");
			String smType1 = rds.getParameter("smType1");
			String content1 = rds.getParameter("content1");
			
			bean.setMobile(mobile1);
			bean.setSmType(MathTools.parseInt(smType1));
			bean.setContent(content1 + "");
		}
		else
		{
			request.setAttribute(KeyConstant.ERROR_MESSAGE, "未知的短信发送方式");

			return mapping.findForward("error");
		}

		String batchId = "";
		
		try
		{
			if (stype == ShortMessageConstant.MODE_IMPORT)
			{
				_logger.info(smbList);
				batchId = shortMessageManager.sendShortMessage(user, smbList);
			}else
			{
				_logger.info(bean);
				batchId = shortMessageManager.sendShortMessage(user, bean);
			}

			request.setAttribute(KeyConstant.MESSAGE, "短信发送成功");
		}
		catch (MYException e)
		{
			_logger.warn(e, e);

			request.setAttribute(KeyConstant.ERROR_MESSAGE,
					"处理失败，" + e.getErrorContent());
		}
		
		request.setAttribute("batchId", batchId);
		
		return mapping.findForward("queryShortMessage");
	}
	
	private List<ShortMessageBean> importSMS(User user, RequestDataStream rds) throws MYException
	{
        boolean importError = false;
        
        String smType = rds.getParameter("smType");
        
        List<ShortMessageBean> importItemList = new ArrayList<ShortMessageBean>(); 
        
        StringBuilder builder = new StringBuilder();       
        
        if ( !rds.haveStream())
        {
        	throw new MYException("文件解析失败1");
        }
        
        ReaderFile reader = ReadeFileFactory.getXLSReader();
        
        try
        {
            reader.readFile(rds.getUniqueInputStream());
            
            while (reader.hasNext())
            {
                String[] obj = fillObj((String[])reader.next());

                // 第一行忽略
                if (reader.getCurrentLineNumber() == 1)
                {
                    continue;
                }

                if (StringTools.isNullOrNone(obj[0]))
                {
                    continue;
                }
                
                int currentNumber = reader.getCurrentLineNumber();

                if (obj.length >= 2 )
                {
                	ShortMessageBean bean = new ShortMessageBean();
                    
                	// 手机号
            		if ( !StringTools.isNullOrNone(obj[0]))
            		{
            			bean.setMobile(obj[0]);
            		}else
            		{
            			importError = true;
            			
                        builder
                        .append("第[" + currentNumber + "]错误:")
                        .append("手机号为空")
                        .append("<br>");
            		}

            		// 短信内容
            		if ( !StringTools.isNullOrNone(obj[1]))
            		{
            			bean.setContent(obj[1] + "");
            		}else
            		{
            			importError = true;
            			
                        builder
                        .append("第[" + currentNumber + "]错误:")
                        .append("短信内容为空")
                        .append("<br>");
            		}

            		bean.setSmode(1);
            		bean.setStafferName(user.getStafferName());
            		bean.setStype(1);
                    bean.setSmType(MathTools.parseInt(smType));
                    bean.setLogTime(TimeTools.now());
                    
                    importItemList.add(bean);
                }
                else
                {
                    builder
                        .append("第[" + currentNumber + "]错误:")
                        .append("数据长度不足2格错误")
                        .append("<br>");
                    
                    importError = true;
                }
            }
            
            
        }catch (Exception e)
        {
            throw new MYException(e.toString());
        }
        finally
        {
            try
            {
                reader.close();
            }
            catch (IOException e)
            {
                _logger.error(e, e);
            }
        }
        
        rds.close();
        
        if (importError){
            throw new MYException("导入出错:"+ builder.toString());
        }
        
        return importItemList;
	}
	
    private String[] fillObj(String[] obj)
    {
        String[] result = new String[2];

        for (int i = 0; i < result.length; i++ )
        {
            if (i < obj.length)
            {
                result[i] = obj[i];
            }
            else
            {
                result[i] = "";
            }
        }

        return result;
    }
	
	/**
	 * 查询
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 */
	public ActionForward queryShortMessage(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
	throws ServletException
	{
		//User user = Helper.getUser(request);
		
		ConditionParse condtion = new ConditionParse();

		condtion.addWhereStr();

		String batchId = request.getParameter("batchId");
		
		if (!StringTools.isNullOrNone(batchId))
		{
			condtion.addIntCondition("ShortMessageBean.batchId", "=", batchId);
		}
		
		ActionTools.processJSONQueryCondition(QUERYSHORTMESSAGE, request, condtion);

		String jsonstr = ActionTools.queryVOByJSONAndToString(QUERYSHORTMESSAGE,
				request, condtion, this.shortMessageDAO);

		return JSONTools.writeResponse(response, jsonstr);
	}
	
	public ActionForward export(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
	throws ServletException
	{
		OutputStream out = null;

		String filenName = "ShortMessage_" + TimeTools.now("MMddHHmmss") + ".csv";

		response.setContentType("application/x-dbf");

		response.setHeader("Content-Disposition", "attachment; filename="
				+ filenName);

		WriteFile write = null;

		ConditionParse condtion = JSONPageSeparateTools.getCondition(request,
				QUERYSHORTMESSAGE);

		int count = shortMessageDAO.countVOByCondition(condtion.toString());

		if (count > 150000)
		{
			return ActionTools.toError("导出数量大于150000,请重新选择时间段导出", mapping,
					request);
		}

		try
		{
			out = response.getOutputStream();

			write = WriteFileFactory.getMyTXTWriter();

			write.openFile(out);

			write.writeLine("类型,手机,内容,操作人,上行/下行,时间");

			PageSeparate page = new PageSeparate();

			page.reset2(count, 2000);

			WriteFileBuffer line = new WriteFileBuffer(write);

			while (page.nextPage())
			{
				List<ShortMessageBean> voFList = shortMessageDAO.queryEntityVOsByCondition(
						condtion, page);

				for (ShortMessageBean each : voFList)
				{
					line.reset();

					line.writeColumn(ElTools.get("232",	each.getSmType()));
					line.writeColumn(each.getMobile());
					line.writeColumn(each.getContent());
					
					line.writeColumn(each.getStafferName());
					line.writeColumn(ElTools.get("sendMode", each.getSmode()));
					line.writeColumn(each.getLogTime());
					
					line.writeLine();
				}
			}
			
			write.close();
		}
		catch (Throwable e)
		{
			_logger.error(e, e);

			return null;
		}
		finally
		{
			if (out != null)
			{
				try
				{
					out.close();
				}
				catch (IOException e1)
				{
				}
			}

			if (write != null)
			{

				try
				{
					write.close();
				}
				catch (IOException e1)
				{
				}
			}
		}

		return null;
	}

	/**
	 * @return the shortMessageDAO
	 */
	public ShortMessageDAO getShortMessageDAO()
	{
		return shortMessageDAO;
	}

	/**
	 * @param shortMessageDAO the shortMessageDAO to set
	 */
	public void setShortMessageDAO(ShortMessageDAO shortMessageDAO)
	{
		this.shortMessageDAO = shortMessageDAO;
	}

	/**
	 * @return the shortMessageManager
	 */
	public ShortMessageManager getShortMessageManager()
	{
		return shortMessageManager;
	}

	/**
	 * @param shortMessageManager the shortMessageManager to set
	 */
	public void setShortMessageManager(ShortMessageManager shortMessageManager)
	{
		this.shortMessageManager = shortMessageManager;
	}
}
