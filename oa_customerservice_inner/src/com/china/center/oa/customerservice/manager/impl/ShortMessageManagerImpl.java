package com.china.center.oa.customerservice.manager.impl;

import java.io.ByteArrayInputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Formatter;
import java.util.List;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.springframework.transaction.annotation.Transactional;

import com.center.china.osgi.publics.User;
import com.china.center.common.MYException;
import com.china.center.common.taglib.DefinedCommon;
import com.china.center.oa.customerservice.bean.ShortMessageBean;
import com.china.center.oa.customerservice.bean.ShortMessageStatusBean;
import com.china.center.oa.customerservice.constant.ShortMessageConstant;
import com.china.center.oa.customerservice.dao.ShortMessageDAO;
import com.china.center.oa.customerservice.dao.ShortMessageStatusDAO;
import com.china.center.oa.customerservice.manager.ShortMessageManager;
import com.china.center.oa.customerservice.wrap.ShortMessageReplyWrap;
import com.china.center.oa.customerservice.wrap.ShortMessageResult;
import com.china.center.oa.customerservice.wrap.ShortMessageSendWrap;
import com.china.center.oa.customerservice.wrap.ShortMessageStatusWrap;
import com.china.center.oa.publics.dao.CommonDAO;
import com.china.center.oa.publics.dao.ParameterDAO;
import com.china.center.tools.BeanUtil;
import com.china.center.tools.JudgeTools;
import com.china.center.tools.ListTools;
import com.china.center.tools.MathTools;
import com.china.center.tools.StringTools;
import com.china.center.tools.TimeTools;

public class ShortMessageManagerImpl implements ShortMessageManager
{
	private final Log _logger = LogFactory.getLog(getClass());
	
	private final Log triggerLog = LogFactory.getLog("trigger");
	
	private final Log SMSLOG = LogFactory.getLog("sms");
	
	private CommonDAO commonDAO = null;
	
	private ParameterDAO parameterDAO = null;
	
	private ShortMessageDAO shortMessageDAO = null;
	
	private ShortMessageStatusDAO shortMessageStatusDAO = null;
	
	private final static int HTTPCLIENT_CONNECTION_TIMEOUT = 3000;
	
	private final static int HTTPCLIENT_RESPONSE_TIMEOUT = 3000;
	
	public ShortMessageManagerImpl(){
		
	}
	
	@Transactional(rollbackFor = MYException.class)
	public String sendShortMessage(User user, List<ShortMessageBean> smbList)
			throws MYException
	{
		_logger.info("sendShortMessage 批量导入短信...");
		
		JudgeTools.judgeParameterIsNull(user, smbList);
		
		String batchId = commonDAO.getSquenceString20();
		
		for (ShortMessageBean bean : smbList)
		{
			ShortMessageResult result = sendShortMessage(bean);
			
			if (result.getRet() != 1)
			{
				bean.setResult(ShortMessageConstant.SMS_RESULT_SUBMIT_FAIL);
				
				bean.setRet(result.getRet());
				
				bean.setMsgId("");
			}else{
				bean.setResult(ShortMessageConstant.SMS_RESULT_SUBMIT_SUCCESS);
				
				ShortMessageSendWrap wrap = (ShortMessageSendWrap)result.getObj();
				
				bean.setMsgId(wrap.getMsgId());
			}
			
			// 下行
			bean.setSmode(1);
			
			bean.setLogTime(TimeTools.now());
			
			bean.setStafferName(user.getStafferName());
			
			// 批量导入
			// persist to db
			bean.setId(commonDAO.getSquenceString());
			
			bean.setBatchId(batchId);
			
			shortMessageDAO.saveEntityBean(bean);
		}
		
		_logger.info("sendShortMessage 批量发送短信..." + smbList.size() + " 条");
		
		return batchId;
	}

	/**
	 * 短信发送，实时返回
	 * 
	 * @param bean
	 * @return  
	 * 			ret 为 xml <br>
	 *			<xml name="sendOnce" result="1"> <br>
	 *			<Item cid="xxx" sid="xxx" msgid="xxx" total="2" fee="0.10" remain="170.04"/> <br>
	 *			</xml> <br>
	 * @throws MYException
	 */
	private ShortMessageResult sendShortMessage(ShortMessageBean bean) throws MYException
	{
		String http_url = parameterDAO.getString("SMS_SEND_URL");
		
		String ret = callRemoteService(http_url, bean, true);
		
		if (StringTools.isNullOrNone(ret))
			throw new MYException("短信发送失败，内部错误");
		
		// 解析xml字符串，获取msgid值
		ShortMessageResult result = parseXML(ret, 1);
		
		return result;
	}

	@Transactional(rollbackFor = MYException.class)
	public String sendShortMessage(User user, ShortMessageBean bean)
			throws MYException
	{
		_logger.info("sendShortMessage 单条短信...");
		
		JudgeTools.judgeParameterIsNull(user, bean);
		
		// 不针对导入模式
		if (bean.getStype() == ShortMessageConstant.MODE_IMPORT)
		{
			throw new MYException("数据错误");
		}
		
		List<ShortMessageBean> list = new ArrayList<ShortMessageBean>();
		
		List<ShortMessageBean> newList = new ArrayList<ShortMessageBean>();
		
		if (bean.getStype() == ShortMessageConstant.MODE_MULTI){
			
			String [] mobiles = bean.getMobile().trim().split(","); 

			int count = 0;
			String mobile = "";
			
			for(String eachmobile : mobiles){
				if (!StringTools.isNullOrNone(eachmobile)){
					mobile += eachmobile + ",";
					
					count ++;
					
					if (count >= 100){
						ShortMessageBean newSMB = new ShortMessageBean();
						
						BeanUtil.copyProperties(newSMB, bean);
						newSMB.setMobile(mobile.substring(0, mobile.length() - 1));

						newList.add(newSMB);
						
						count = 0;
						mobile = "";
					}
				}
			}
			
			if (count > 0)
			{
				ShortMessageBean newSMB = new ShortMessageBean();
				
				BeanUtil.copyProperties(newSMB, bean);
				newSMB.setMobile(mobile.substring(0, mobile.length() - 1));

				newList.add(newSMB);
			}
			
		}else
		{
			newList.add(bean);
		}

		String batchId = commonDAO.getSquenceString20();
		
		for (ShortMessageBean eachsmb : newList)
		{
			ShortMessageResult result = sendShortMessage(eachsmb);
			
			// 失败 ->>>>> error.jsp
			if (result.getRet() != 1)
			{
				throw new MYException("短信发送失败，失败原因：" + DefinedCommon.getValue("sendStatus", result.getRet()));
			}
			
			ShortMessageSendWrap wrap = (ShortMessageSendWrap)result.getObj();
			
			String msgId = wrap.getMsgId();
			
			// 如果是批量的要分拆成多行
			// persist to db
			eachsmb.setMsgId(msgId);
			eachsmb.setResult(ShortMessageConstant.SMS_RESULT_SUBMIT_SUCCESS);
			
			// 下行
			eachsmb.setSmode(1);

			eachsmb.setLogTime(TimeTools.now());
			
			eachsmb.setStafferName(user.getStafferName());
			
			eachsmb.setId(commonDAO.getSquenceString());
			
			eachsmb.setBatchId(batchId);
			
			// 批量发送多个手机号
			if (eachsmb.getStype() == ShortMessageConstant.MODE_MULTI)
			{
				String mobiles = eachsmb.getMobile();
				
				String [] mobilearr = mobiles.split(",");
				
				for (String each : mobilearr)
				{
					if (!StringTools.isNullOrNone(each)){
						ShortMessageBean smBean = new ShortMessageBean();
						
						BeanUtil.copyProperties(smBean, eachsmb);
						
						smBean.setMobile(each);
						smBean.setId(commonDAO.getSquenceString());
						
						list.add(smBean);
					}
				}
			}else
			{
				list.add(eachsmb);
			}
		}
		
		shortMessageDAO.saveAllEntityBeans(list);
		
		return batchId;
	}

	/**
	 * 定时获取短信发送状态，且更新发送记录状态. uniqe:  msgid + mobile
	 * {@inheritDoc}
	 */
	@SuppressWarnings("unchecked")
	@Transactional(rollbackFor = MYException.class)
	public void checkShortMessageStatus() throws MYException
	{
		triggerLog.info("checkShortMessageStatus begin ...");
		
		String http_url = parameterDAO.getString("SMS_SEND_STATUS_URL");
		
		String ret = callRemoteService(http_url, null, false);
		
		if (StringTools.isNullOrNone(ret))
			throw new MYException("短信发送失败，内部错误");
		
		// 解析xml字符串，获取msgid值
		ShortMessageResult result = parseXML(ret, 2);
		
		// 失败
		if (result.getRet() != 1)
		{
			throw new MYException("短信状态获取失败，失败原因：" + DefinedCommon.getValue("sendStatus", result.getRet()));
		}
		
		if (null == result.getObj())
		{
			triggerLog.info("checkShortMessageStatus end, no Items  ...");
			
			return;
		}
		
		List<ShortMessageStatusWrap> wrapList = (List<ShortMessageStatusWrap>)result.getObj();

		List<ShortMessageStatusBean> statusList = new ArrayList<ShortMessageStatusBean>();
		
		for(ShortMessageStatusWrap each : wrapList)
		{
			ShortMessageStatusBean statusBean = new ShortMessageStatusBean();
			
			statusBean.setMobile(each.getMobile());
			statusBean.setMsgId(each.getMsgId());
			statusBean.setRetsult(each.getResult());
			statusBean.setRet(each.getRet());
			
			statusList.add(statusBean);
			
			// 根据状态，修改发送短信的状态
			// 成功
			if (each.getResult() == 2){
				shortMessageDAO.updateResult(each.getMsgId(), each.getMobile(), ShortMessageConstant.SMS_RESULT_SEND_SUCCESS, "");
			}else if (each.getResult() == 1) // 失败
			{
				shortMessageDAO.updateResult(each.getMsgId(), each.getMobile(), ShortMessageConstant.SMS_RESULT_SEND_FAIL, each.getRet());
			}else // 未知 
			{
				// do nothing
			}
		}
		
		shortMessageStatusDAO.saveAllEntityBeans(statusList);
		
		triggerLog.info("checkShortMessageStatus end ...");
	}

	/**
	 * 
	 * {@inheritDoc}
	 */
	@SuppressWarnings("unchecked")
	@Transactional(rollbackFor = MYException.class)
	public void getShortMessageReply() throws MYException
	{
		triggerLog.info("getShortMessageReply begin ...");
		
		String http_url = parameterDAO.getString("SMS_SEND_REPLY_URL");
		
		String ret = callRemoteService(http_url, null, false);
		
		if (StringTools.isNullOrNone(ret))
			throw new MYException("短信发送失败，内部错误");
		
		// 解析xml字符串，获取msgid值
		ShortMessageResult result = parseXML(ret, 3);
		
		_logger.info("==========" + ret);
		
		// 失败
		if (result.getRet() != 1)
		{
			throw new MYException("短信回复获取失败，失败原因：" + DefinedCommon.getValue("sendStatus", result.getRet()));
		}
		
		if (null == result.getObj())
		{
			triggerLog.info("getShortMessageReply end, no Items  ...");
			
			return;
		}
		
		List<ShortMessageReplyWrap> wrapList = (List<ShortMessageReplyWrap>)result.getObj();
		
		List<ShortMessageBean> smList = new ArrayList<ShortMessageBean>();
		
		String batchId = commonDAO.getSquenceString20();
		
		for(ShortMessageReplyWrap each : wrapList)
		{
			ShortMessageBean smBean = new ShortMessageBean();
			
			smBean.setId(commonDAO.getSquenceString());
			smBean.setMsgId(smBean.getId());
			smBean.setBatchId(batchId);
			smBean.setMobile(each.getMobile());
			smBean.setContent(each.getContent());
			smBean.setSmode(2);
			smBean.setStype(0);
			smBean.setLogTime(TimeTools.getString(Long.parseLong(each.getTime()), "yyyy-MM-dd hh:mm:ss"));
			smBean.setSmType(0);
			
			smList.add(smBean);
		}
		
		shortMessageDAO.saveAllEntityBeans(smList);
		
		triggerLog.info("getShortMessageReply end ...");
	}

	/**
	 * 远程调用短信发送平台
	 * @param url
	 * @param bean
	 * @param hasParam
	 * @return
	 * @throws MYException
	 */
	private String callRemoteService(String url, ShortMessageBean bean, boolean hasParam) throws MYException
	{
		if (null != bean)
			SMSLOG.info("callRemoteService SMS begin ..., content：" + bean.getContent() + ", 手机:" + bean.getMobile());
		
        HttpClient httpclient = new HttpClient();

        String content = "";
        
        String mobile = "";
        
        if (hasParam)
        {
        	mobile = bean.getMobile();
            try
            {
            	content = java.net.URLEncoder.encode(bean.getContent(), "UTF-8");
            }
            catch (UnsupportedEncodingException e)
            {
                throw new MYException(e);
            }
            
            Formatter formatter = new Formatter();

            url = formatter.format(url.trim(), content.trim(), bean.getMobile().trim()).toString();
        }

        GetMethod getMethod = new GetMethod(url);

        // 设置连接超时
        httpclient.getHttpConnectionManager().getParams().setConnectionTimeout(HTTPCLIENT_CONNECTION_TIMEOUT);
        
        // 设置返回超时
        httpclient.getHttpConnectionManager().getParams().setSoTimeout(HTTPCLIENT_RESPONSE_TIMEOUT);
        
        int statusCode = 200;

        String ret = "";
        
        try
        {
            statusCode = httpclient.executeMethod(getMethod);
            
            String set = getMethod.getResponseCharSet();
            
            //读取内容 
            byte[] responseBody = getMethod.getResponseBody();                       
            
            //处理内容
            ret = new String(responseBody, set);
        }
        catch (Throwable e)
        {
            SMSLOG.info("send sms to [" + mobile.trim() + "] is:" + 505 + "." + url + "." + e);

            throw new MYException(e);
        }

        SMSLOG.info("send sms to [" + mobile.trim() + "] is:" + statusCode + "." + url);
        
		if (null != bean)
			SMSLOG.info("callRemoteService SMS end ..., content：" + bean.getContent() + ", 手机:" + bean.getMobile());

        return ret;
	}
	
	@SuppressWarnings("unchecked")
	private ShortMessageResult parseXML(String xml, int type) throws MYException
	{
		ShortMessageResult smresult = new ShortMessageResult();
		
		smresult.setRet(1);
		
		try
	    {
	      SAXReader reader = new SAXReader();

	      Document document = null;
	      
	      document = reader.read(new ByteArrayInputStream(xml.getBytes("GBK")));

	      Element root = document.getRootElement();

	      Element element = root;

	      String result = element.attributeValue("result");

	      if (MathTools.parseInt(result) != 1)
	      {
	    	  smresult.setRet(MathTools.parseInt(result));
	    	  
	    	  return smresult;
	      }
	      
	      List<Element> elements = element.elements("Item");

	      if (type == 1)
	      {
	    	  Element e = elements.get(0);
		      
		      String msgId = e.attributeValue("msgid");
		      
		      ShortMessageSendWrap wrap = new ShortMessageSendWrap();
		      
		      wrap.setMsgId(msgId);
		      
		      smresult.setObj(wrap);
	      }else if (type == 2)
	      {
	    	  if (!ListTools.isEmptyOrNull(elements))
	    	  {
	    		  List<ShortMessageStatusWrap> swList = new ArrayList<ShortMessageStatusWrap>();
	    		  
	    		  smresult.setObj(swList);
	    		  
	    		  for (Element each : elements)
	    		  {
	    			  ShortMessageStatusWrap sw = new ShortMessageStatusWrap();
	    			  
	    			  sw.setMsgId(each.attributeValue("msgid"));
	    			  sw.setMobile(each.attributeValue("mobile"));
	    			  sw.setResult(MathTools.parseInt(each.attributeValue("result")));
	    			  sw.setRet(each.attributeValue("return"));
	    			  
	    			  swList.add(sw);
	    		  }
	    	  }
	      }else if (type == 3)
	      {
	    	  if (!ListTools.isEmptyOrNull(elements))
	    	  {
	    		  List<ShortMessageReplyWrap> rwList = new ArrayList<ShortMessageReplyWrap>();
	    		  
	    		  smresult.setObj(rwList);
	    		  
	    		  for (Element each : elements)
	    		  {
	    			  ShortMessageReplyWrap rw = new ShortMessageReplyWrap(); 
	    			  
	    			  rw.setMobile(each.attributeValue("mobile"));
	    			  rw.setContent(each.attributeValue("content"));
	    			  rw.setTime(each.attributeValue("time"));
	    			  
	    			  rwList.add(rw);
	    		  }
	    	  }
	      }else{
	    	  throw new MYException("不存在类型");
	      }
	      
	      return smresult;
	    }
		catch(Exception e)
		{
			throw new MYException(e);
		}
	}

	/**
	 * @return the commonDAO
	 */
	public CommonDAO getCommonDAO()
	{
		return commonDAO;
	}

	/**
	 * @param commonDAO the commonDAO to set
	 */
	public void setCommonDAO(CommonDAO commonDAO)
	{
		this.commonDAO = commonDAO;
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
	 * @return the shortMessageStatusDAO
	 */
	public ShortMessageStatusDAO getShortMessageStatusDAO()
	{
		return shortMessageStatusDAO;
	}

	/**
	 * @param shortMessageStatusDAO the shortMessageStatusDAO to set
	 */
	public void setShortMessageStatusDAO(ShortMessageStatusDAO shortMessageStatusDAO)
	{
		this.shortMessageStatusDAO = shortMessageStatusDAO;
	}

	/**
	 * @return the parameterDAO
	 */
	public ParameterDAO getParameterDAO()
	{
		return parameterDAO;
	}

	/**
	 * @param parameterDAO the parameterDAO to set
	 */
	public void setParameterDAO(ParameterDAO parameterDAO)
	{
		this.parameterDAO = parameterDAO;
	}
}
