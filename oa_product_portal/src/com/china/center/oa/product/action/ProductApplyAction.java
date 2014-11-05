/**
 * File Name: FlowAction.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * Creater: zhuAchen<br>
 * CreateTime: 2009-4-26<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.product.action;

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
import com.china.center.actionhelper.common.ActionTools;
import com.china.center.actionhelper.common.JSONTools;
import com.china.center.actionhelper.common.KeyConstant;
import com.china.center.actionhelper.json.AjaxResult;
import com.china.center.common.MYException;
import com.china.center.common.taglib.DefinedCommon;
import com.china.center.jdbc.util.ConditionParse;
import com.china.center.oa.product.bean.ProductApplyBean;
import com.china.center.oa.product.bean.ProductSubApplyBean;
import com.china.center.oa.product.bean.ProductVSStafferBean;
import com.china.center.oa.product.constant.ProductApplyConstant;
import com.china.center.oa.product.dao.ProductApplyDAO;
import com.china.center.oa.product.dao.ProductSubApplyDAO;
import com.china.center.oa.product.dao.ProductVSStafferDAO;
import com.china.center.oa.product.facade.ProductApplyFacade;
import com.china.center.oa.product.manager.ProductApplyManager;
import com.china.center.oa.product.vo.ProductApplyVO;
import com.china.center.oa.product.vo.ProductSubApplyVO;
import com.china.center.oa.product.vo.ProductVSStafferVO;
import com.china.center.oa.publics.Helper;
import com.china.center.oa.publics.bean.FlowLogBean;
import com.china.center.oa.publics.bean.InvoiceBean;
import com.china.center.oa.publics.bean.PrincipalshipBean;
import com.china.center.oa.publics.dao.FlowLogDAO;
import com.china.center.oa.publics.dao.InvoiceDAO;
import com.china.center.oa.publics.dao.PrincipalshipDAO;
import com.china.center.oa.publics.vo.FlowLogVO;
import com.china.center.tools.BeanUtil;
import com.china.center.tools.MathTools;
import com.china.center.tools.TimeTools;

/**
 * 
 * 新产品申请
 * 
 * @author fangliwen 2012-5-23
 */
public class ProductApplyAction extends DispatchAction {

    private final Log           _logger             = LogFactory.getLog(getClass());

    private ProductApplyFacade  productApplyFacade  = null;

    private ProductApplyManager productApplyManager = null;

    private ProductApplyDAO     productApplyDAO     = null;   

    private ProductVSStafferDAO productVSStafferDAO = null;

    private ProductSubApplyDAO  productSubApplyDAO  = null;

    private FlowLogDAO          flowLogDAO          = null;

    private PrincipalshipDAO    principalshipDAO    = null;
    
    private InvoiceDAO invoiceDAO = null;

    private static String       QUERYPRODUCTAPPLY   = "queryProductApply";

    private static final String SAVE                = "0";

    /**
     * default constructor
     */
    public ProductApplyAction() {
    }

    /**
     * queryProduct
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward queryProductApply(ActionMapping mapping, ActionForm form,
            final HttpServletRequest request, HttpServletResponse response) throws ServletException {

        String forward = request.getParameter("forward");

        final ConditionParse condition = new ConditionParse();

        condition.addWhereStr();

        ActionTools.processJSONQueryCondition(QUERYPRODUCTAPPLY, request, condition);

        // 1 部门审批
        if (forward.equals("1")) {

            condition.addCondition("ProductApplyBean.status", "=", ProductApplyConstant.STATUS_DEPARTMENTAPPLY);
            
        } else if (forward.equals("2")) {

            condition.addCondition("ProductApplyBean.status", "=", ProductApplyConstant.STATUS_PRODUCTAPPLY);
        }

        String jsonstr = ActionTools.queryVOByJSONAndToString(QUERYPRODUCTAPPLY, request,
                condition, this.productApplyDAO);

        return JSONTools.writeResponse(response, jsonstr);
    }

    public ActionForward preForAddProductApply(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) 
    throws ServletException {
    	
    	prepare(request);
    	
    	return mapping.findForward("addProductApply");
    }

	private void prepare(HttpServletRequest request)
	{
		List<InvoiceBean> invoiceList1 = invoiceDAO.listForwardIn();

        request.setAttribute("invoiceList1", invoiceList1);
        
        List<InvoiceBean> invoiceList2 = invoiceDAO.listForwardOut();

        request.setAttribute("invoiceList2", invoiceList2);
	}
    
    /**
     * add ProductApply
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward addProductApply(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) 
    throws ServletException {

        ProductApplyBean productApplyBean = new ProductApplyBean();

        BeanUtil.getBean(productApplyBean, request);

        String save = request.getParameter("save");

        if (save.equals(SAVE)) 
        {
            productApplyBean.setStatus(ProductApplyConstant.STATUS_SAVE);

        } else 
        {
            
            productApplyBean.setStatus(ProductApplyConstant.STATUS_SUBMIT);
        }

        productApplyBean.setLogTime(TimeTools.now());

        setSubProduct(request, productApplyBean);
        
        setProductVSStaffer(request, productApplyBean);

        try {

            User user = Helper.getUser(request);

            productApplyFacade.addProductApply(user.getId(), productApplyBean);

            request.setAttribute(KeyConstant.MESSAGE, "操作成功");

        } catch (MYException e) {

            _logger.error(e, e);

            request.setAttribute(KeyConstant.ERROR_MESSAGE, "操作失败：" + e.getMessage());
        }

        return mapping.findForward("queryProductApply");
    }

    /**
     * 产品申请时合成产品之子产品
     * @param request
     * @param productApplyBean
     */
    private void setSubProduct(HttpServletRequest request, ProductApplyBean productApplyBean) 
    {
        List<ProductSubApplyBean> productSubApplyList = new ArrayList<ProductSubApplyBean>();

        productApplyBean.setProductSubApplyList(productSubApplyList);

        String[] subProductIds = request.getParameterValues("subProductId");

        String[] subProductAmounts = request.getParameterValues("subProductAmount");

        if (null != subProductIds)
        {
            for (int i = 0; i < subProductIds.length; i++) 
            {
                ProductSubApplyBean productSubApplyBean = new ProductSubApplyBean();

                productSubApplyBean.setSubProductId(subProductIds[i]);
                productSubApplyBean.setSubProductAmount(MathTools.parseInt(subProductAmounts[i]));

                productSubApplyList.add(productSubApplyBean);
            }
        }
        
    }

    /**
     * setProductVSStaffer
     * 
     * @param request
     * @param productApplyBean
     */
    private void setProductVSStaffer(final HttpServletRequest request, ProductApplyBean productApplyBean) 
    {
        List<ProductVSStafferBean> vsList = new ArrayList<ProductVSStafferBean>();

        productApplyBean.setVsList(vsList);

        String[] stafferRoles = request.getParameterValues("stafferRole");

        String[] commissionRatios = request.getParameterValues("commissionRatio");
        
        String[] stafferIds = request.getParameterValues("stafferId");

        if (null != stafferRoles)
        {
            for (int i = 0; i < stafferRoles.length; i++) 
            {
                ProductVSStafferBean vsBean = new ProductVSStafferBean();

                vsBean.setStafferRole(MathTools.parseInt(stafferRoles[i]));
                vsBean.setCommissionRatio(MathTools.parseInt(commissionRatios[i]));
                vsBean.setStafferId(stafferIds[i]);

                vsList.add(vsBean);
            }
        }
        
    }    
    
    /**
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward deleteProductApply(ActionMapping mapping, ActionForm form,
            final HttpServletRequest request, HttpServletResponse response) throws ServletException {

        String id = request.getParameter("id");

        AjaxResult ajax = new AjaxResult();

        try {
            User user = Helper.getUser(request);

            productApplyFacade.deleteProductApply(user.getId(), id);

            ajax.setSuccess("成功操作");

        } catch (MYException e) {

            _logger.error(e, e);

            ajax.setError("删除新申请产品失败：" + e.getMessage());
        }

        return JSONTools.writeResponse(response, ajax);
    }

    /**
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward findProductApply(ActionMapping mapping, ActionForm form,
            final HttpServletRequest request, HttpServletResponse response) throws ServletException {

        String id = request.getParameter("id");

        String update = request.getParameter("update");

        if (null == update)
            update = "0";

        ProductApplyVO bean = productApplyDAO.findVO(id);

        if (null == bean) {
            return ActionTools.toError("数据异常，请重新操作", "queryProductApply", mapping, request);
        }

        int status = bean.getStatus();

        if ("1".equals(update)) {

            if (status != ProductApplyConstant.STATUS_SAVE
                    && status != ProductApplyConstant.STATUS_REJECT) {
                return ActionTools.toError("非保存或驳回状态不允许修改", "queryProductApply", mapping, request);
            }
        }

        request.setAttribute("bean", bean);

        prepare(request);
        
        processIndustryId(bean);

        List<ProductSubApplyVO> psab = productSubApplyDAO.queryEntityVOsByFK(id);

        request.setAttribute("itemList", psab);
        
        List<ProductVSStafferVO> vsList = productVSStafferDAO.queryEntityVOsByFK(id);
        
        request.setAttribute("itemList1", vsList);
        
        if (update.equals("1")) {

            return mapping.findForward("updateProductApply");

        } else {

            List<FlowLogBean> logList = flowLogDAO.queryEntityBeansByFK(id);

            List<FlowLogVO> logsVO = new ArrayList<FlowLogVO>();

            for (FlowLogBean flowLogBean : logList)
            {
                logsVO.add(getFlowLogVO(flowLogBean));
            }
            
            request.setAttribute("logList", logsVO);

            return mapping.findForward("detailProductApply");
        }

    }
    
    private FlowLogVO getFlowLogVO(FlowLogBean bean)
    {
        FlowLogVO vo = new FlowLogVO();

        if (bean == null)
        {
            return vo;
        }

        BeanUtil.copyProperties(vo, bean);

        vo.setOprModeName(DefinedCommon.getValue("productOPRMode", vo.getOprMode()));

        vo.setPreStatusName(DefinedCommon.getValue("productApplyStatus", vo.getPreStatus()));

        vo.setAfterStatusName(DefinedCommon.getValue("productApplyStatus", vo.getAfterStatus()));

        return vo;
    }

    private void processIndustryId(ProductApplyVO bean) 
    {
        String industryId = bean.getIndustryId();

        String industryNames = "";

        String[] industryIds = industryId.split(",");

        for (String each : industryIds) {
            PrincipalshipBean prin = principalshipDAO.find(each);

            industryNames += prin.getName() + ",";
        }

        bean.setIndustryIdsName(industryNames);
    }

    /**
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward updateProductApply(ActionMapping mapping, ActionForm form,
            final HttpServletRequest request, HttpServletResponse response) throws ServletException 
    {
        String id = request.getParameter("id");

        String save = request.getParameter("save");
        
        ProductApplyBean bean = productApplyDAO.find(id);

        if (null == bean) 
        {
            request.setAttribute(KeyConstant.ERROR_MESSAGE, "数据错误，未找到id=" + id + " 的单据");

            mapping.findForward("queryProductApply");
        }

        BeanUtil.getBean(bean, request);

        bean.setLogTime(TimeTools.now());

        if (save.equals("1"))
        {
            bean.setStatus(ProductApplyConstant.STATUS_SUBMIT);
        }
        else
        {
            bean.setStatus(ProductApplyConstant.STATUS_SAVE);
        }
        
        setSubProduct(request, bean);

        setProductVSStaffer(request, bean);
        
        try {
            User user = Helper.getUser(request);

            productApplyFacade.updateProductApply(user.getId(), bean);

            request.setAttribute(KeyConstant.MESSAGE, "成功修改申请的产品");

        } catch (MYException e) {

            _logger.error(e, e);

            request.setAttribute(KeyConstant.ERROR_MESSAGE, "修改产品失败：" + e.getMessage());
        }
        
        return mapping.findForward("queryProductApply");
    }

    public ActionForward passProductApply(ActionMapping mapping, ActionForm form,
            final HttpServletRequest request, HttpServletResponse response) throws ServletException {

        String id = request.getParameter("id");

        ProductApplyBean bean = productApplyDAO.find(id);

        if (null == bean) {

            return ActionTools.toError("数据异常，请重新操作", "queryProductApply", mapping, request);
        }

        AjaxResult ajax = new AjaxResult();

        try {
            User user = Helper.getUser(request);

            productApplyFacade.passProductApply(user.getId(), bean);

            ajax.setSuccess("成功操作");

        } catch (MYException e) {

            _logger.error(e, e);

            ajax.setError("审批出错:" + e.getMessage());
        }

        return JSONTools.writeResponse(response, ajax);

    }

    public ActionForward pass1ProductApply(ActionMapping mapping, ActionForm form,
            final HttpServletRequest request, HttpServletResponse response) throws ServletException {

        String id = request.getParameter("id");

        ProductApplyBean bean = productApplyDAO.find(id);

        if (null == bean) {

            return ActionTools.toError("数据异常，请重新操作", "queryProductApply", mapping, request);
        }

        AjaxResult ajax = new AjaxResult();

        try {
            User user = Helper.getUser(request);

            productApplyFacade.pass1ProductApply(user.getId(), bean);

            ajax.setSuccess("成功操作");

        } catch (MYException e) {

            _logger.error(e, e);

            ajax.setError("审批出错:" + e.getMessage());
        }

        return JSONTools.writeResponse(response, ajax);

    }

    public ActionForward rejectProductApply(ActionMapping mapping, ActionForm form,
            final HttpServletRequest request, HttpServletResponse response) throws ServletException {

        String id = request.getParameter("id");

        ProductApplyBean bean = productApplyDAO.find(id);

        if (null == bean) {

            return ActionTools.toError("数据异常，请重新操作", "queryProductApply", mapping, request);
        }

        AjaxResult ajax = new AjaxResult();

        try {
            User user = Helper.getUser(request);

            productApplyFacade.rejectProductApply(user.getId(), bean);

            ajax.setSuccess("成功操作");

        } catch (MYException e) {

            _logger.error(e, e);

            ajax.setError("审批出错:" + e.getMessage());
        }

        return JSONTools.writeResponse(response, ajax);

    }

    public ProductApplyFacade getProductApplyFacade() {
        return productApplyFacade;
    }

    public void setProductApplyFacade(ProductApplyFacade productApplyFacade) {
        this.productApplyFacade = productApplyFacade;
    }

    public ProductApplyManager getProductApplyManager() {
        return productApplyManager;
    }

    public void setProductApplyManager(ProductApplyManager productApplyManager) {
        this.productApplyManager = productApplyManager;
    }

    public ProductApplyDAO getProductApplyDAO() {
        return productApplyDAO;
    }

    public void setProductApplyDAO(ProductApplyDAO productApplyDAO) {
        this.productApplyDAO = productApplyDAO;
    }

    public ProductVSStafferDAO getProductVSStafferDAO() {
        return productVSStafferDAO;
    }

    public void setProductVSStafferDAO(ProductVSStafferDAO productVSStafferDAO) {
        this.productVSStafferDAO = productVSStafferDAO;
    }

    public ProductSubApplyDAO getProductSubApplyDAO() {
        return productSubApplyDAO;
    }

    public void setProductSubApplyDAO(ProductSubApplyDAO productSubApplyDAO) {
        this.productSubApplyDAO = productSubApplyDAO;
    }

    public FlowLogDAO getFlowLogDAO() {
        return flowLogDAO;
    }

    public void setFlowLogDAO(FlowLogDAO flowLogDAO) {
        this.flowLogDAO = flowLogDAO;
    }

    public PrincipalshipDAO getPrincipalshipDAO() {
        return principalshipDAO;
    }

    public void setPrincipalshipDAO(PrincipalshipDAO principalshipDAO) {
        this.principalshipDAO = principalshipDAO;
    }

	/**
	 * @return the invoiceDAO
	 */
	public InvoiceDAO getInvoiceDAO()
	{
		return invoiceDAO;
	}

	/**
	 * @param invoiceDAO the invoiceDAO to set
	 */
	public void setInvoiceDAO(InvoiceDAO invoiceDAO)
	{
		this.invoiceDAO = invoiceDAO;
	}

}
