/**
 * File Name: CustomerCreditAction.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2009-11-8<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.credit.action;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.china.center.actionhelper.common.ActionTools;
import com.china.center.actionhelper.common.JSONTools;
import com.china.center.actionhelper.common.KeyConstant;
import com.china.center.actionhelper.json.AjaxResult;
import com.china.center.common.MYException;
import com.china.center.jdbc.util.ConditionParse;
import com.china.center.oa.client.bean.CustomerBean;
import com.china.center.oa.client.dao.CustomerMainDAO;
import com.china.center.oa.client.facade.ClientFacade;
import com.china.center.oa.client.manager.ClientManager;
import com.china.center.oa.credit.bean.CreditItemBean;
import com.china.center.oa.credit.bean.CreditItemSecBean;
import com.china.center.oa.credit.bean.CreditItemThrBean;
import com.china.center.oa.credit.dao.CreditItemDAO;
import com.china.center.oa.credit.dao.CreditItemSecDAO;
import com.china.center.oa.credit.dao.CreditItemThrDAO;
import com.china.center.oa.credit.dao.CreditlogDAO;
import com.china.center.oa.credit.dao.CustomerCreditApplyDAO;
import com.china.center.oa.credit.dao.CustomerCreditDAO;
import com.china.center.oa.credit.manager.CustomerCreditManager;
import com.china.center.oa.credit.vo.CreditItemSecVO;
import com.china.center.oa.credit.vo.CreditItemThrVO;
import com.china.center.oa.credit.vo.CustomerCreditApplyVO;
import com.china.center.oa.credit.vo.CustomerCreditVO;
import com.china.center.oa.credit.vs.AbstractCustomerCredit;
import com.china.center.oa.credit.vs.CustomerCreditApplyBean;
import com.china.center.oa.credit.vs.CustomerCreditBean;
import com.china.center.oa.customer.bean.CustomerApplyBean;
import com.china.center.oa.customer.constant.CreditConstant;
import com.china.center.oa.customer.dao.CustomerApplyDAO;
import com.china.center.oa.publics.Helper;
import com.china.center.oa.publics.bean.StafferBean;
import com.china.center.oa.publics.constant.AuthConstant;
import com.china.center.oa.publics.constant.SysConfigConstant;
import com.china.center.oa.publics.dao.ParameterDAO;
import com.china.center.oa.publics.dao.StafferDAO;
import com.china.center.oa.publics.manager.CommonMailManager;
import com.china.center.oa.publics.manager.UserManager;
import com.china.center.tools.CommonTools;
import com.china.center.tools.ListTools;
import com.china.center.tools.RequestDataStream;
import com.china.center.tools.StringTools;
import com.china.center.tools.TimeTools;


/**
 * CustomerCreditAction
 * 
 * @author ZHUZHU
 * @version 2009-11-8
 * @see CustomerCreditAction
 * @since 1.0
 */
public class CustomerCreditAction extends DispatchAction
{
    private final Log _logger = LogFactory.getLog(getClass());

    private CustomerCreditManager customerCreditManager = null;

    private CustomerCreditDAO customerCreditDAO = null;
    
    private CustomerApplyDAO customerApplyDAO = null;

    private CustomerCreditApplyDAO customerCreditApplyDAO = null;

    private CustomerMainDAO customerMainDAO = null;

    private CreditItemDAO creditItemDAO = null;

    private CreditItemSecDAO creditItemSecDAO = null;

    private ClientManager clientManager = null;
    
    private StafferDAO stafferDAO = null;

    private CreditItemThrDAO creditItemThrDAO = null;

    private ClientFacade clientFacade = null;

    private CreditlogDAO creditlogDAO = null;

    private ParameterDAO parameterDAO = null;
    
    protected CommonMailManager commonMailManager = null;

    private UserManager userManager = null;

    private static final String QUERYCUSTOMERCREDITLOG = "queryCustomerCreditLog";

    private static final String QUERYCUSTOMERCREDIT = "queryCustomerCredit";

    /**
     * default constructor
     */
    public CustomerCreditAction()
    {
    }

    /**
     * preForAddCustomerCredit
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward preForConfigStaticCustomerCredit(ActionMapping mapping, ActionForm form,
                                                          HttpServletRequest request,
                                                          HttpServletResponse response)
        throws ServletException
    {
        String cid = request.getParameter("cid");

        String detail = request.getParameter("detailapply");

        CustomerBean bean = customerMainDAO.find(cid);

        if (bean == null)
        {
            request.setAttribute(KeyConstant.ERROR_MESSAGE, "数据不完备");

            return mapping.findForward("querySelfClient");
        }
        request.setAttribute("bean", bean);

        List<CreditItemSecVO> itemList = queryStaticAndSelectItem();

        request.setAttribute("itemList", itemList);

        // query sub static item
        Map<String, List<CreditItemThrVO>> map = new HashMap();

        ConditionParse subCondition = new ConditionParse();

        for (CreditItemSecVO creditItemSecVO : itemList)
        {
            subCondition.clear();

            subCondition.addWhereStr();

            subCondition.addCondition("CreditItemThrBean.pid", "=", creditItemSecVO.getId());

            subCondition.addCondition("order by CreditItemThrBean.indexPos");

            List<CreditItemThrVO> subList = creditItemThrDAO
                .queryEntityVOsByCondition(subCondition);

            if (ListTools.isEmptyOrNull(subList))
            {
                request.setAttribute(KeyConstant.ERROR_MESSAGE, creditItemSecVO.getName()
                                                                + "没有配置子项,不能配置客户静态指标");

                return mapping.findForward("querySelfClient");
            }

            map.put(creditItemSecVO.getId(), subList);
        }

        request.setAttribute("map", map);

        Map<String, String> valueMap = new HashMap();

        // query configure value
        if ( !"1".equals(detail))
        {
            List<CustomerCreditVO> customerCreditList = customerCreditDAO.queryEntityVOsByFK(cid);

            for (AbstractCustomerCredit customerCreditVO : customerCreditList)
            {
                valueMap.put(customerCreditVO.getItemId(), customerCreditVO.getValueId());
            }
        }
        else
        {
            List<CustomerCreditApplyVO> customerCreditList = customerCreditApplyDAO
                .queryEntityVOsByFK(cid);

            for (AbstractCustomerCredit customerCreditVO : customerCreditList)
            {
                valueMap.put(customerCreditVO.getItemId(), customerCreditVO.getValueId());
            }
        }

        request.setAttribute("valueMap", valueMap);

        if ("1".equals(detail))
        {
            return mapping.findForward("detailCustomerCredit");
        }

        return mapping.findForward("configCustomerCredit");
    }

    /**
     * 人为干预等级
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward interposeCredit(ActionMapping mapping, ActionForm form,
                                         HttpServletRequest request, HttpServletResponse response)
        throws ServletException
    {
        String cid = request.getParameter("cid");

        String newcval = request.getParameter("newcval");

        User user = Helper.getUser(request);

        try
        {
            clientFacade.interposeCredit(user.getId(), cid, CommonTools.parseFloat(newcval));

            request.setAttribute(KeyConstant.MESSAGE, "成功人为干预");
        }
        catch (MYException e)
        {
            request.setAttribute(KeyConstant.ERROR_MESSAGE, e.getErrorContent());

            return mapping.findForward("error");
        }

        return mapping.findForward("interposeCredit");
    }

    /**
     * queryCustomerCreditLog
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward queryCustomerCreditLog(ActionMapping mapping, ActionForm form,
                                                HttpServletRequest request,
                                                HttpServletResponse response)
        throws ServletException
    {
        String cid = request.getParameter("cid");

        ConditionParse condtion = new ConditionParse();

        condtion.addWhereStr();

        condtion.addCondition("CreditlogBean.cid", "=", cid);

        ActionTools.processJSONQueryCondition(QUERYCUSTOMERCREDITLOG, request, condtion);

        condtion.addCondition("order by CreditlogBean.id desc");

        String jsonstr = ActionTools.queryVOByJSONAndToString(QUERYCUSTOMERCREDITLOG, request,
            condtion, this.creditlogDAO);

        return JSONTools.writeResponse(response, jsonstr);
    }

    /**
     * queryCustomerCredit
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward queryCustomerCredit(ActionMapping mapping, ActionForm form,
                                             HttpServletRequest request,
                                             HttpServletResponse response)
        throws ServletException
    {
        String cid = request.getParameter("cid");

        ConditionParse condtion = new ConditionParse();

        condtion.addWhereStr();

        condtion.addCondition("CustomerCreditBean.cid", "=", cid);

        ActionTools.processJSONQueryCondition(QUERYCUSTOMERCREDIT, request, condtion);

        condtion.addCondition("order by CustomerCreditBean.logTime desc");

        String jsonstr = ActionTools.queryVOByJSONAndToString(QUERYCUSTOMERCREDIT, request,
            condtion, this.customerCreditDAO);

        return JSONTools.writeResponse(response, jsonstr);
    }

    /**
     * queryStaticAndSelectItem
     * 
     * @return
     */
    private List<CreditItemSecVO> queryStaticAndSelectItem()
    {
        ConditionParse condition = new ConditionParse();

        condition.addWhereStr();

        // query static and has sub item
        condition.addIntCondition("CreditItemBean.type", "=", CreditConstant.CREDIT_TYPE_STATIC);

        condition.addIntCondition("CreditItemSecBean.sub", "=", CreditConstant.CREDIT_ITEM_SUB_YES);

        List<CreditItemSecVO> itemList = creditItemSecDAO.queryEntityVOsByCondition(condition);

        return itemList;
    }

    /**
     * configStaticCustomerCredit
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward configStaticCustomerCredit(ActionMapping mapping, ActionForm form,
                                                    HttpServletRequest request,
                                                    HttpServletResponse response)
        throws ServletException
    {
        String cid = request.getParameter("cid");

        User user = Helper.getUser(request);

        try
        {
            boolean hasAuth = clientManager.hasCustomerAuth(user.getStafferId(), cid);

            if ( !hasAuth)
            {
                request.setAttribute(KeyConstant.ERROR_MESSAGE, "没有操作此客户权限");

                return mapping.findForward("error");
            }
        }
        catch (MYException e)
        {
            request.setAttribute(KeyConstant.ERROR_MESSAGE, "没有操作此客户权限");

            return mapping.findForward("error");
        }

        List<CreditItemSecVO> itemList = queryStaticAndSelectItem();

        List<CustomerCreditApplyBean> creditList = new ArrayList();

        final int staticAmount = parameterDAO.getInt(SysConfigConstant.CREDIT_STATIC);

        for (CreditItemSecVO creditItemSecVO : itemList)
        {
            String valueId = request.getParameter(creditItemSecVO.getId());

            if (StringTools.isNullOrNone(valueId))
            {
                continue;
            }

            CustomerCreditApplyBean bean = new CustomerCreditApplyBean();

            bean.setCid(cid);

            CreditItemThrBean thr = creditItemThrDAO.find(valueId);

            if (thr == null)
            {
                request.setAttribute(KeyConstant.ERROR_MESSAGE, "数据错误,请重新操作");

                return mapping.findForward("error");
            }

            CreditItemBean parent = creditItemDAO.find(creditItemSecVO.getPid());

            if (parent == null)
            {
                request.setAttribute(KeyConstant.ERROR_MESSAGE, "数据错误,请重新操作");

                return mapping.findForward("error");
            }

            bean.setPtype(parent.getType());

            bean
                .setVal( (staticAmount * parent.getPer() * creditItemSecVO.getPer() * thr.getPer()) / 1000000.0d);

            bean.setValueId(valueId);

            bean.setItemId(creditItemSecVO.getId());

            bean.setPitemId(creditItemSecVO.getPid());

            bean.setLog(user.getStafferName() + "配置静态属性");

            creditList.add(bean);
        }

        try
        {
            customerCreditManager.applyConfigStaticCustomerCredit(user, cid, creditList);
        }
        catch (MYException e)
        {
            _logger.error(e, e);

            request.setAttribute(KeyConstant.ERROR_MESSAGE, e.getMessage());

            return mapping.findForward("querySelfClient");
        }

        request.setAttribute(KeyConstant.MESSAGE, "成功申请");

        return mapping.findForward("querySelfClient");
    }

    /**
     * doPassApplyConfigStaticCustomerCredit
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward doPassApplyConfigStaticCustomerCredit(ActionMapping mapping,
                                                               ActionForm form,
                                                               HttpServletRequest request,
                                                               HttpServletResponse response)
        throws ServletException
    {
        String cid = request.getParameter("cid");

        AjaxResult ajax = new AjaxResult();

        User user = Helper.getUser(request);

        try
        {
            clientFacade.doPassApplyConfigStaticCustomerCredit(user.getId(), cid);

            ajax.setSuccess("成功操作");
        }
        catch (MYException e)
        {
            _logger.error(e, e);

            ajax.setError("操作失败:" + e.getMessage());
        }

        return JSONTools.writeResponse(response, ajax);
    }

    /**
     * doRejectApplyConfigStaticCustomerCredit
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward doRejectApplyConfigStaticCustomerCredit(ActionMapping mapping,
                                                                 ActionForm form,
                                                                 HttpServletRequest request,
                                                                 HttpServletResponse response)
        throws ServletException
    {
        String cid = request.getParameter("cid");

        AjaxResult ajax = new AjaxResult();

        User user = Helper.getUser(request);
        CustomerApplyBean customerApplyBean = this.customerApplyDAO.find(cid);
        try
        {
            clientFacade.doRejectApplyConfigStaticCustomerCredit(user.getId(), cid);
        	sendRejectMail(cid,user,"无",customerApplyBean,"客户变更申请驳回");
            ajax.setSuccess("成功操作");
        }
        catch (MYException e)
        {
            _logger.error(e, e);

            ajax.setError("操作失败:" + e.getMessage());
        }

        return JSONTools.writeResponse(response, ajax);
    }

    /**
     * 导入
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward uploadCustomerCredit(ActionMapping mapping, ActionForm form,
                                              HttpServletRequest request,
                                              HttpServletResponse response)
        throws ServletException
    {
        User user = Helper.getUser(request);

        if ( !userManager.containAuth(user, AuthConstant.CREDIT_IMPOTR))
        {
            request.setAttribute(KeyConstant.ERROR_MESSAGE, "没有权限");

            return mapping.findForward("uploadCustomerCredit");
        }

        RequestDataStream rds = new RequestDataStream(request);

        try
        {
            rds.parser();
        }
        catch (Exception e1)
        {
            _logger.error(e1, e1);

            request.setAttribute(KeyConstant.ERROR_MESSAGE, "解析失败");

            return mapping.findForward("uploadCustomerCredit");
        }

        int success = 0;

        int fault = 0;

        final int staticAmount = parameterDAO.getInt(SysConfigConstant.CREDIT_STATIC);

        StringBuilder builder = new StringBuilder();

        if (rds.haveStream())
        {
            try
            {
                ReaderFile reader = ReadeFileFactory.getXLSReader();

                reader.readFile(rds.getUniqueInputStream());

                while (reader.hasNext())
                {
                    String[] obj = (String[])reader.next();

                    // 第一行忽略
                    if (reader.getCurrentLineNumber() == 1)
                    {
                        continue;
                    }

                    int currentNumber = reader.getCurrentLineNumber();

                    String ccode = obj[0];

                    if (StringTools.isNullOrNone(ccode))
                    {
                        builder.append("第[" + currentNumber + "]错误:").append("客户编码为空").append(
                            "<br>");

                        fault++ ;

                        continue;
                    }

                    boolean addSucess = false;

                    if (obj.length >= 21)
                    {
                        addSucess = innerAdd(user, builder, obj, ccode, currentNumber, staticAmount);
                    }
                    else
                    {
                        builder
                            .append("第[" + currentNumber + "]错误:")
                            .append("数据长度不足21格,数据不足")
                            .append("<br>");
                    }

                    if (addSucess)
                    {
                        success++ ;
                    }
                    else
                    {
                        fault++ ;
                    }
                }
            }
            catch (Exception e)
            {
                _logger.error(e, e);

                request.setAttribute(KeyConstant.ERROR_MESSAGE, "导入失败");

                return mapping.findForward("uploadCustomerCredit");
            }
        }

        rds.close();

        StringBuilder result = new StringBuilder();

        result.append("导入成功:").append(success).append("条,失败:").append(fault).append("条<br>");

        result.append(builder.toString());

        request.setAttribute(KeyConstant.MESSAGE, result.toString());

        return mapping.findForward("uploadCustomerCredit");
    }

    /**
     * innerAdd
     * 
     * @param user
     * @param builder
     * @param obj
     * @param stafferId
     * @param currentNumber
     * @return
     */
    private boolean innerAdd(User user, StringBuilder builder, String[] obj, String stafferId,
                             int currentNumber, int staticAmount)
    {
        boolean addSucess = false;

        try
        {
            CustomerBean customer = customerMainDAO.findCustomerByCode(obj[0].trim());

            if (customer == null)
            {
                builder
                    .append("<font color=red>第[" + currentNumber + "]行错误:")
                    .append("客户不存在")
                    .append("</font><br>");

                return false;
            }

            // 定向解析
            String[] keys = new String[] {"80000000000000000001", "80000000000000000002",
                "80000000000000000003", "80000000000000000004", "80000000000000000011",
                "80000000000000000012", "80000000000000000013", "80000000000000000014",
                "80000000000000000021", "80000000000000000022", "80000000000000000023",
                "80000000000000000024", "80000000000000000031", "80000000000000000032",
                "80000000000000000033", "80000000000000000034", "80000000000000000035",
                "80000000000000000036", "80000000000000000037", "80000000000000000038"};

            List<CustomerCreditBean> ccList = new ArrayList();

            for (int i = 0; i < keys.length; i++ )
            {
                String str = keys[i];

                String valieName = obj[i + 1];

                if (StringTools.isNullOrNone(valieName))
                {
                    continue;
                }

                CreditItemThrBean creditItemThr = creditItemThrDAO.findByUnique(valieName.trim(),
                    str);

                if (creditItemThr == null)
                {
                    continue;
                }

                CreditItemSecBean creditItemSec = creditItemSecDAO.find(str);

                if (creditItemSec == null)
                {
                    continue;
                }

                CreditItemBean creditItem = creditItemDAO.find(creditItemSec.getPid());

                if (creditItem == null)
                {
                    continue;
                }

                CustomerCreditBean each = new CustomerCreditBean();

                each.setCid(customer.getId());

                each.setLogTime(TimeTools.now());

                each.setItemId(str);

                each.setValueId(creditItemThr.getId());

                each
                    .setVal( (staticAmount * creditItem.getPer() * creditItemSec.getPer() * creditItemThr
                        .getPer()) / 1000000.0d);

                each.setLog(user.getStafferName() + "导入修改:" + each.getVal());

                each.setPitemId(creditItem.getId());

                each.setPtype(CreditConstant.CREDIT_TYPE_STATIC);

                ccList.add(each);
            }

            customerCreditManager.configCustomerCredit(user, customer.getId(), ccList);

            addSucess = true;
        }
        catch (Exception e)
        {
            addSucess = false;

            builder
                .append("<font color=red>第[" + currentNumber + "]行错误:")
                .append(e.getMessage())
                .append("</font><br>");
        }

        return addSucess;
    }

    /**
     * @return the customerCreditManager
     */
    public CustomerCreditManager getCustomerCreditManager()
    {
        return customerCreditManager;
    }

    /**
     * @param customerCreditManager
     *            the customerCreditManager to set
     */
    public void setCustomerCreditManager(CustomerCreditManager customerCreditManager)
    {
        this.customerCreditManager = customerCreditManager;
    }

    /**
     * @return the customerCreditDAO
     */
    public CustomerCreditDAO getCustomerCreditDAO()
    {
        return customerCreditDAO;
    }

    /**
     * @param customerCreditDAO
     *            the customerCreditDAO to set
     */
    public void setCustomerCreditDAO(CustomerCreditDAO customerCreditDAO)
    {
        this.customerCreditDAO = customerCreditDAO;
    }

    /**
	 * @return the customerMainDAO
	 */
	public CustomerMainDAO getCustomerMainDAO()
	{
		return customerMainDAO;
	}

	/**
	 * @param customerMainDAO the customerMainDAO to set
	 */
	public void setCustomerMainDAO(CustomerMainDAO customerMainDAO)
	{
		this.customerMainDAO = customerMainDAO;
	}

	/**
     * @return the creditItemDAO
     */
    public CreditItemDAO getCreditItemDAO()
    {
        return creditItemDAO;
    }

    /**
     * @param creditItemDAO
     *            the creditItemDAO to set
     */
    public void setCreditItemDAO(CreditItemDAO creditItemDAO)
    {
        this.creditItemDAO = creditItemDAO;
    }

    /**
     * @return the creditItemSecDAO
     */
    public CreditItemSecDAO getCreditItemSecDAO()
    {
        return creditItemSecDAO;
    }

    /**
     * @param creditItemSecDAO
     *            the creditItemSecDAO to set
     */
    public void setCreditItemSecDAO(CreditItemSecDAO creditItemSecDAO)
    {
        this.creditItemSecDAO = creditItemSecDAO;
    }

    /**
     * @return the creditItemThrDAO
     */
    public CreditItemThrDAO getCreditItemThrDAO()
    {
        return creditItemThrDAO;
    }

    /**
     * @param creditItemThrDAO
     *            the creditItemThrDAO to set
     */
    public void setCreditItemThrDAO(CreditItemThrDAO creditItemThrDAO)
    {
        this.creditItemThrDAO = creditItemThrDAO;
    }

    /**
     * @return the parameterDAO
     */
    public ParameterDAO getParameterDAO()
    {
        return parameterDAO;
    }

    /**
     * @param parameterDAO
     *            the parameterDAO to set
     */
    public void setParameterDAO(ParameterDAO parameterDAO)
    {
        this.parameterDAO = parameterDAO;
    }

    /**
     * @return the customerCreditApplyDAO
     */
    public CustomerCreditApplyDAO getCustomerCreditApplyDAO()
    {
        return customerCreditApplyDAO;
    }

    /**
     * @param customerCreditApplyDAO
     *            the customerCreditApplyDAO to set
     */
    public void setCustomerCreditApplyDAO(CustomerCreditApplyDAO customerCreditApplyDAO)
    {
        this.customerCreditApplyDAO = customerCreditApplyDAO;
    }

    /**
     * @return the userManager
     */
    public UserManager getUserManager()
    {
        return userManager;
    }

    /**
     * @param userManager
     *            the userManager to set
     */
    public void setUserManager(UserManager userManager)
    {
        this.userManager = userManager;
    }

    /**
     * @return the creditlogDAO
     */
    public CreditlogDAO getCreditlogDAO()
    {
        return creditlogDAO;
    }

    /**
     * @param creditlogDAO
     *            the creditlogDAO to set
     */
    public void setCreditlogDAO(CreditlogDAO creditlogDAO)
    {
        this.creditlogDAO = creditlogDAO;
    }
    
    /**
     * 驳回邮件发送
     * @param fullId
     * @param user
     * @param reason
     * @param flowInstanceBean
     * @param subject
     */
    private void sendRejectMail(String fullId, User user, String reason, CustomerApplyBean customerApplyBean,String subject) 
    {
        StafferBean rejectorBean = stafferDAO.find(user.getStafferId());        
        
        StafferBean approverBean = stafferDAO.find(customerApplyBean.getUpdaterId());
        
        
        if (null != approverBean)
        {
            StringBuffer sb = new StringBuffer();
            
            sb.append("系统发送>>>")
            .append("\r\n").append("客户:"+ customerApplyBean.getName()).append("信息变更驳回,")
            .append("\r\n").append("审批人:"+ rejectorBean.getName()).append(",")
            .append("\r\n").append("审批结果:驳回").append(",")
            .append("\r\n").append("审批时间：").append(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())).append(",")
            .append("\r\n").append("审批意见:" + reason).append(",")
            .append("\r\n").append("审批人电话:"+ rejectorBean.getHandphone());
            
            String message = sb.toString();
            String to = approverBean.getNation();
            
            _logger.info(message);
            
            commonMailManager.sendMail(to, subject, message);
        }
    }

	public CustomerApplyDAO getCustomerApplyDAO() {
		return customerApplyDAO;
	}

	public void setCustomerApplyDAO(CustomerApplyDAO customerApplyDAO) {
		this.customerApplyDAO = customerApplyDAO;
	}

	public StafferDAO getStafferDAO() {
		return stafferDAO;
	}

	public void setStafferDAO(StafferDAO stafferDAO) {
		this.stafferDAO = stafferDAO;
	}

	public CommonMailManager getCommonMailManager() {
		return commonMailManager;
	}

	public void setCommonMailManager(CommonMailManager commonMailManager) {
		this.commonMailManager = commonMailManager;
	}

	/**
	 * @return the clientManager
	 */
	public ClientManager getClientManager()
	{
		return clientManager;
	}

	/**
	 * @param clientManager the clientManager to set
	 */
	public void setClientManager(ClientManager clientManager)
	{
		this.clientManager = clientManager;
	}

	/**
	 * @return the clientFacade
	 */
	public ClientFacade getClientFacade()
	{
		return clientFacade;
	}

	/**
	 * @param clientFacade the clientFacade to set
	 */
	public void setClientFacade(ClientFacade clientFacade)
	{
		this.clientFacade = clientFacade;
	}
}
