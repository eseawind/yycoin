/**
 * File Name: InvoiceinsAction.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2011-1-2<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.finance.portal.action;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

import org.apache.commons.fileupload.FileUploadBase;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.center.china.osgi.config.ConfigLoader;
import com.center.china.osgi.publics.User;
import com.center.china.osgi.publics.file.read.ReadeFileFactory;
import com.center.china.osgi.publics.file.read.ReaderFile;
import com.china.center.actionhelper.common.ActionTools;
import com.china.center.actionhelper.common.JSONTools;
import com.china.center.actionhelper.common.KeyConstant;
import com.china.center.actionhelper.common.PageSeparateTools;
import com.china.center.actionhelper.json.AjaxResult;
import com.china.center.actionhelper.jsonimpl.JSONArray;
import com.china.center.actionhelper.query.CommonQuery;
import com.china.center.common.MYException;
import com.china.center.jdbc.annosql.constant.AnoConstant;
import com.china.center.jdbc.util.ConditionParse;
import com.china.center.jdbc.util.PageSeparate;
import com.china.center.oa.finance.bean.InsVSInvoiceNumBean;
import com.china.center.oa.finance.bean.InvoiceBindOutBean;
import com.china.center.oa.finance.bean.InvoiceStorageBean;
import com.china.center.oa.finance.bean.InvoiceinsBean;
import com.china.center.oa.finance.bean.InvoiceinsDetailBean;
import com.china.center.oa.finance.bean.InvoiceinsImportBean;
import com.china.center.oa.finance.bean.InvoiceinsItemBean;
import com.china.center.oa.finance.bean.StockPayApplyBean;
import com.china.center.oa.finance.constant.FinanceConstant;
import com.china.center.oa.finance.dao.InsImportLogDAO;
import com.china.center.oa.finance.dao.InsVSInvoiceNumDAO;
import com.china.center.oa.finance.dao.InsVSOutDAO;
import com.china.center.oa.finance.dao.InvoiceStorageDAO;
import com.china.center.oa.finance.dao.InvoiceinsDAO;
import com.china.center.oa.finance.dao.InvoiceinsImportDAO;
import com.china.center.oa.finance.dao.InvoiceinsItemDAO;
import com.china.center.oa.finance.dao.StockPayApplyDAO;
import com.china.center.oa.finance.facade.FinanceFacade;
import com.china.center.oa.finance.manager.InvoiceinsManager;
import com.china.center.oa.finance.vo.InvoiceStorageVO;
import com.china.center.oa.finance.vo.InvoiceinsItemVO;
import com.china.center.oa.finance.vo.InvoiceinsVO;
import com.china.center.oa.finance.vs.InsVSOutBean;
import com.china.center.oa.publics.Helper;
import com.china.center.oa.publics.bean.AttachmentBean;
import com.china.center.oa.publics.bean.CityBean;
import com.china.center.oa.publics.bean.DutyBean;
import com.china.center.oa.publics.bean.FlowLogBean;
import com.china.center.oa.publics.bean.InvoiceBean;
import com.china.center.oa.publics.bean.ProvinceBean;
import com.china.center.oa.publics.bean.ShowBean;
import com.china.center.oa.publics.bean.StafferBean;
import com.china.center.oa.publics.constant.AuthConstant;
import com.china.center.oa.publics.constant.InvoiceConstant;
import com.china.center.oa.publics.dao.AttachmentDAO;
import com.china.center.oa.publics.dao.CityDAO;
import com.china.center.oa.publics.dao.DutyDAO;
import com.china.center.oa.publics.dao.DutyVSInvoiceDAO;
import com.china.center.oa.publics.dao.FlowLogDAO;
import com.china.center.oa.publics.dao.InvoiceDAO;
import com.china.center.oa.publics.dao.ProvinceDAO;
import com.china.center.oa.publics.dao.ShowDAO;
import com.china.center.oa.publics.dao.StafferDAO;
import com.china.center.oa.publics.manager.CommonMailManager;
import com.china.center.oa.publics.manager.StafferManager;
import com.china.center.oa.publics.vs.DutyVSInvoiceBean;
import com.china.center.oa.publics.vs.RoleAuthBean;
import com.china.center.oa.sail.bean.BaseBalanceBean;
import com.china.center.oa.sail.bean.BaseBean;
import com.china.center.oa.sail.bean.DistributionBean;
import com.china.center.oa.sail.bean.ExpressBean;
import com.china.center.oa.sail.bean.OutBalanceBean;
import com.china.center.oa.sail.bean.OutBean;
import com.china.center.oa.sail.bean.UnitViewBean;
import com.china.center.oa.sail.constanst.OutConstant;
import com.china.center.oa.sail.constanst.OutImportConstant;
import com.china.center.oa.sail.dao.BaseBalanceDAO;
import com.china.center.oa.sail.dao.BaseDAO;
import com.china.center.oa.sail.dao.DistributionDAO;
import com.china.center.oa.sail.dao.ExpressDAO;
import com.china.center.oa.sail.dao.OutBalanceDAO;
import com.china.center.oa.sail.dao.OutDAO;
import com.china.center.oa.sail.dao.UnitViewDAO;
import com.china.center.oa.sail.helper.OutHelper;
import com.china.center.oa.sail.vo.DistributionVO;
import com.china.center.oa.tax.bean.FinanceBean;
import com.china.center.oa.tax.dao.FinanceDAO;
import com.china.center.tools.BeanUtil;
import com.china.center.tools.CommonTools;
import com.china.center.tools.FileTools;
import com.china.center.tools.ListTools;
import com.china.center.tools.MathTools;
import com.china.center.tools.RequestDataStream;
import com.china.center.tools.RequestTools;
import com.china.center.tools.SequenceTools;
import com.china.center.tools.StringTools;
import com.china.center.tools.TimeTools;
import com.china.center.tools.UtilStream;


/**
 * InvoiceinsAction
 * 
 * @author ZHUZHU
 * @version 2011-1-2
 * @see InvoiceinsAction
 * @since 3.0
 */
public class InvoiceinsAction extends DispatchAction
{
    private final Log _logger = LogFactory.getLog(getClass());

    private FinanceFacade financeFacade = null;
    
    private StafferDAO stafferDAO = null;
    
    protected CommonMailManager commonMailManager = null;

    private InvoiceinsDAO invoiceinsDAO = null;

    private InvoiceinsItemDAO invoiceinsItemDAO = null;

    private InsVSOutDAO insVSOutDAO = null;

    private DutyDAO dutyDAO = null;

    private ShowDAO showDAO = null;

    private StafferManager stafferManager = null;

    private OutDAO outDAO = null;

    private BaseDAO baseDAO = null;

    private FlowLogDAO flowLogDAO = null;

    private BaseBalanceDAO baseBalanceDAO = null;

    private InvoiceDAO invoiceDAO = null;

    private OutBalanceDAO outBalanceDAO = null;

    private InvoiceinsManager invoiceinsManager = null;

    private DutyVSInvoiceDAO dutyVSInvoiceDAO = null;
    
    private FinanceDAO financeDAO = null;
    
    private InvoiceStorageDAO invoiceStorageDAO = null;
    
    private StockPayApplyDAO stockPayApplyDAO = null;
    
    private InsVSInvoiceNumDAO insVSInvoiceNumDAO = null;
    
    private AttachmentDAO attachmentDAO = null;
    
    private UnitViewDAO unitViewDAO = null;
    
    private InvoiceinsImportDAO invoiceinsImportDAO = null;
    
    private InsImportLogDAO insImportLogDAO = null;
    
    private ExpressDAO expressDAO = null;
    
    private DistributionDAO distributionDAO = null;

    private ProvinceDAO provinceDAO = null;
	
	private CityDAO cityDAO = null;

    private static final String QUERYINVOICEINS = "queryInvoiceins";
    
    private static final String QUERYIMPORTINVOIE = "queryImportInvoice";
    
    private static final String QUERYINVOICEINSIMPORT = "queryInvoiceinsImport";
    
    private static final String QUERYINSIMPORTLOG = "queryInsImportLog";
    

    /**
     * default constructor
     */
    public InvoiceinsAction()
    {
    }

    /**
     * preForAddInvoiceins
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward preForAddInvoiceins(ActionMapping mapping, ActionForm form,
                                             HttpServletRequest request,
                                             HttpServletResponse response)
        throws ServletException
    {
        CommonTools.saveParamers(request);

        prepare(request);

        // 查询开单品名
        List<ShowBean> showList = showDAO.listEntityBeans();

        JSONArray shows = new JSONArray(showList, true);

        request.setAttribute("showJSON", shows.toString());
        
        // 获得财务审批的权限人(1604)
        List<StafferBean> stafferList = stafferManager
            .queryStafferByAuthId(AuthConstant.INVOICEINS_OPR);

        request.setAttribute("stafferList", stafferList);

        return mapping.findForward("addInvoiceins");
    }

    /**
     * 2012以后的开票
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward preForAddInvoiceins1(ActionMapping mapping, ActionForm form,
                                              HttpServletRequest request,
                                              HttpServletResponse response)
        throws ServletException
    {
        CommonTools.saveParamers(request);

        prepare(request);

        // 查询开单品名
        List<ShowBean> showList = showDAO.listEntityBeans();

        JSONArray shows = new JSONArray(showList, true);

        request.setAttribute("showJSON", shows.toString());
        
        // 获得财务审批的权限人(1604)
        List<StafferBean> stafferList = stafferManager
            .queryStafferByAuthId(AuthConstant.INVOICEINS_OPR);

        request.setAttribute("now", TimeTools.now_short());
        
        request.setAttribute("stafferList", stafferList);
        
        List<ExpressBean> expressList = expressDAO
				.listEntityBeansByOrder("order by id");

		request.setAttribute("expressList", expressList);

        return mapping.findForward("navigationAddInvoiceins1");
    }

    /**
     * preForAddInvoiceins2
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward preForAddInvoiceins2(ActionMapping mapping, ActionForm form,
                                              HttpServletRequest request,
                                              HttpServletResponse response)
        throws ServletException
    {
        CommonTools.saveParamers(request);

        prepare(request);

        // 查询开单品名
        List<ShowBean> showList = showDAO.listEntityBeans();

        JSONArray shows = new JSONArray(showList, true);

        request.setAttribute("showJSON", shows.toString());

        return mapping.findForward("addInvoiceins2");
    }

    private void prepare(HttpServletRequest request)
    {
    	List<DutyBean> dutyList = dutyDAO.listEntityBeans();
        
        List<DutyBean> dutyList1 = new ArrayList<DutyBean>();
	       for(int i = 0 ; i < dutyList.size();i++)
	       {
	    	   DutyBean db = dutyList.get(i);
	    	   if(!db.getName().contains("停用"))
	    	   {
	    		   dutyList1.add(db);
	    	   }
	       }
       
       request.setAttribute("dutyList", dutyList1);

        ConditionParse condition = new ConditionParse();

        condition.addWhereStr();

        condition.addIntCondition("InvoiceBean.forward", "=", InvoiceConstant.INVOICE_FORWARD_OUT);

        List<InvoiceBean> invoiceList = invoiceDAO.queryEntityBeansByCondition(condition);

        request.setAttribute("invoiceList", invoiceList);

        List<DutyVSInvoiceBean> vsList = dutyVSInvoiceDAO.listEntityBeans();

        // 过滤
        fiterVS(invoiceList, vsList);

        JSONArray vsJSON = new JSONArray(vsList, true);

        request.setAttribute("vsJSON", vsJSON.toString());

        JSONArray invoices = new JSONArray(invoiceList, true);

        request.setAttribute("invoicesJSON", invoices.toString());
        
        // 查询开单品名
        List<ShowBean> showList = showDAO.listEntityBeans();

        JSONArray shows = new JSONArray(showList, true);

        request.setAttribute("showJSON", shows.toString());
    }

    private void fiterVS(List<InvoiceBean> invoiceList, List<DutyVSInvoiceBean> vsList)
    {
        for (Iterator iterator = vsList.iterator(); iterator.hasNext();)
        {
            DutyVSInvoiceBean dutyVSInvoiceBean = (DutyVSInvoiceBean)iterator.next();

            boolean delete = true;

            for (InvoiceBean invoiceBean : invoiceList)
            {
                if (dutyVSInvoiceBean.getInvoiceId().equals(invoiceBean.getId()))
                {
                    delete = false;
                    break;
                }
            }

            if (delete)
            {
                iterator.remove();
            }
        }
    }

    /**
     * findInvoiceins
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward findInvoiceins(ActionMapping mapping, ActionForm form,
                                        HttpServletRequest request, HttpServletResponse response)
        throws ServletException
    {
        CommonTools.saveParamers(request);

        String id = request.getParameter("id");
        
        String update = request.getParameter("update");

        if (StringTools.isNullOrNone(update))
        	update = "";

        InvoiceinsVO bean = invoiceinsManager.findVO(id);

        if (bean == null)
        {
            request.setAttribute(KeyConstant.ERROR_MESSAGE, "数据异常,请重新操作");

            return mapping.findForward("error");
        }
        
        List<InvoiceinsItemVO> itemList = invoiceinsItemDAO.queryEntityVOsByFK(bean.getId());
        
        setItemTaxRate(itemList);

        prepare(request);
        
        List<DistributionVO> distList = distributionDAO.queryEntityVOsByFK(bean.getId());
        
        if (!ListTools.isEmptyOrNull(distList)) {
        	request.setAttribute("distributionBean", distList.get(0));
        }
        
        // 查询开单品名
        List<ShowBean> showList = showDAO.listEntityBeans();

        JSONArray shows = new JSONArray(showList, true);

        request.setAttribute("showJSON", shows.toString());

        double mayMoney = 0.0d;
        
        // 选择的销售单哦(委托单最终也是销售单)
        if (StringTools.isNullOrNone(bean.getRefIds()))
        {
            return ActionTools.toError("没有开票的销售单", mapping, request);
        }
        
        mayMoney = statsMayMoney(bean);
        
        request.setAttribute("mayMoney", mayMoney);
        
        request.setAttribute("bean", bean);

        if ("2".equals(update))
        {
        	// 获得财务审批的权限人(1604)
            List<StafferBean> stafferList = stafferManager
                .queryStafferByAuthId(AuthConstant.INVOICEINS_OPR);

            request.setAttribute("stafferList", stafferList);
        	
            getBeanDescribe(bean, request, mayMoney);
            
            List<ExpressBean> expressList = expressDAO
    				.listEntityBeansByOrder("order by id");

    		request.setAttribute("expressList", expressList);
            
        	return mapping.findForward("navigationAddInvoiceins1");
        }
        
        if (!"3".equals(update))
        {
            List<FlowLogBean> logList = flowLogDAO.queryEntityBeansByFK(id);

            request.setAttribute("logList", logList);
        }
		
        List<FinanceBean> financeList = financeDAO.queryRefFinanceItemByOutId(id);

        request.setAttribute("financeList", financeList);
        
        request.setAttribute("update", update);
        
		request.setAttribute("itemList", itemList);
        
        return mapping.findForward("detailInvoiceins");
    }

	/**
	 * 功能描述: <br>
	 * 〈功能详细描述〉
	 *
	 * @param itemList
	 * @see [相关类/方法](可选)
	 * @since [产品/模块版本](可选)
	 */
	private void setItemTaxRate(List<InvoiceinsItemVO> itemList) {
		// 获取税率
        for (InvoiceinsItemVO each : itemList) {
        	if (each.getType() == FinanceConstant.INSVSOUT_TYPE_OUT) {
        		BaseBean base = baseDAO.find(each.getBaseId());
        		
        		if (null != base) {
        			each.setTaxrate(base.getTaxrate());
        		}
        	} else {
        		BaseBalanceBean baseBalance = baseBalanceDAO.find(each.getBaseId());
        		
        		if (null != baseBalance) {
        			BaseBean base = baseDAO.find(baseBalance.getBaseId());
        			
        			if (null != base) {
        				each.setTaxrate(base.getTaxrate());
        			}
        		}
        	}
        }
	}

	@SuppressWarnings("unchecked")
	private void getBeanDescribe(InvoiceinsVO bean, HttpServletRequest request, double mayMoney)
	{
		Map<String, String> map = null;
		// 将bean 转为 map
		try
		{
			map = BeanUtil.describe(bean);
		}
		catch (IllegalAccessException e)
		{
		}
		catch (InvocationTargetException e)
		{
		}
		catch (NoSuchMethodException e)
		{
		}
		
		map.put("cname", bean.getCustomerName());
		map.put("outId", bean.getRefIds());
		map.put("mayMoney", String.valueOf(mayMoney));
		
		request.setAttribute("pmap", map);
	}
    
    private double statsMayMoney(InvoiceinsBean bean)
    {
    	// 开票的单据
    	List<InsVSOutBean> itemList = insVSOutDAO.queryEntityBeansByFK(bean.getId(), AnoConstant.FK_FIRST);
    	
    	double mayMoney = 0.0d;

    	String oldOutId = "";
    	
        for (InsVSOutBean each : itemList)
        {
    		if (oldOutId.equals(each.getOutId()))
        		continue;
        	
        	oldOutId = each.getOutId();
        	
        	if (each.getType() == FinanceConstant.INSVSOUT_TYPE_OUT)
        	{
            	OutBean out = outDAO.find(each.getOutId());
            	
            	double retTotal = outDAO.sumOutBackValueIgnoreStatus(each.getOutId());
            	
            	// 已开的发票 = 已开票 + 未审批结束的金额
            	double notEndInvoice = insVSOutDAO.sumOutHasInvoiceStatusNotEnd(each.getOutId());

            	double hadInvoice = out.getInvoiceMoney() + notEndInvoice;
            	
            	mayMoney += out.getTotal() - retTotal - hadInvoice;
        	}
        	else
        	{
        		if (!StringTools.isNullOrNone(each.getOutBalanceId()))
        		{
        			OutBalanceBean balacenBean = outBalanceDAO.find(each.getOutBalanceId());
                	
                	// 已开的发票 = 已开票 + 未审批结束的金额
                	double notEndInvoice = insVSOutDAO.sumOutBlanceHasInvoiceStatusNotEnd(each.getOutId());
                	
                	// 在途未审批结束的，未来可能要分开，以示说明
                	double hadInvoice = balacenBean.getInvoiceMoney() + notEndInvoice;
                	
                	mayMoney += balacenBean.getTotal() - hadInvoice;
        		}
        	}        	
        	
        }
        
        return mayMoney;
    }

    /**
     * queryInvoiceins
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward queryInvoiceins(ActionMapping mapping, ActionForm form,
                                         HttpServletRequest request, HttpServletResponse response)
        throws ServletException
    {
        ConditionParse condtion = new ConditionParse();

        condtion.addWhereStr();

        ActionTools.processJSONQueryCondition(QUERYINVOICEINS, request, condtion);

        notQueryFirstTime(condtion);
        
        User user = Helper.getUser(request);

        String mode = RequestTools.getValueFromRequest(request, "mode");

        // 自己的
        if ("0".equals(mode))
        {
            condtion.addCondition("InvoiceinsBean.stafferId", "=", user.getStafferId());
        }
        // 出纳审核
        else if ("1".equals(mode))
        {
            condtion.addCondition("InvoiceinsBean.processer", "=", user.getStafferId());

            condtion.addIntCondition("InvoiceinsBean.status", "=",
                FinanceConstant.INVOICEINS_STATUS_SUBMIT);
        }
        else if ("2".equals(mode))
        {
        }
        // 稽核
        else if ("3".equals(mode))
        {
            condtion.addIntCondition("InvoiceinsBean.status", "=",
                FinanceConstant.INVOICEINS_STATUS_CHECK);
        }
        else
        {
            condtion.addFlaseCondition();
        }

        condtion.addCondition("order by InvoiceinsBean.id desc");

//        String jsonstr = ActionTools.queryVOByJSONAndToString(QUERYINVOICEINS, request, condtion,
//            this.invoiceinsDAO);
        
        String jsonstr = ActionTools.querySelfBeanByJSONAndToString(QUERYINVOICEINS, request, condtion,
                new CommonQuery()
                {
                    public int getCount(String key, HttpServletRequest request, ConditionParse condition)
                    {
                        return invoiceinsDAO.countInvoiceinsByConstion(condition);
                    }

                    public String getOrderPfix(String key, HttpServletRequest request)
                    {
                        return "InvoiceinsBean";
                    }

                    @SuppressWarnings("rawtypes")
    				public List queryResult(String key, HttpServletRequest request,
                                            ConditionParse queryCondition)
                    {
                        return invoiceinsDAO.queryInvoiceinsByConstion(PageSeparateTools
                            .getCondition(request, key), PageSeparateTools
                            .getPageSeparate(request, key));
                    }

                    public String getSortname(HttpServletRequest request)
                    {
                        return request.getParameter(ActionTools.SORTNAME);
                    }
                });

        return JSONTools.writeResponse(response, jsonstr);
    }

    /**
     * 第一次打开时，不做查询，须输入条件才允许查询
     * @param condtion
     */
	private void notQueryFirstTime(ConditionParse condtion)
	{
		String conwhere = condtion.toString();
        
        if (conwhere.indexOf("1 = 2") == -1)
        {
        	// 判断1=1 后面是否有And 条件
        	
        	String subwhere = conwhere.substring(conwhere.indexOf("1=1") + 3);

        	if (StringTools.isNullOrNone(subwhere))
        	{
        		condtion.addFlaseCondition();
        	}
        }
	}
    
    /**
     * exportInvoiceins
     * 
     * @param mapping
     * @param form
     * @param request
     * @param reponse
     * @return
     * @throws ServletException
     */
    public ActionForward exportInvoiceins(ActionMapping mapping, ActionForm form,
                                          HttpServletRequest request, HttpServletResponse reponse)
        throws ServletException
    {
        PageSeparate pageSeparate = PageSeparateTools.getPageSeparate(request, QUERYINVOICEINS);

        ConditionParse condition = PageSeparateTools.getCondition(request, QUERYINVOICEINS);

//        if (pageSeparate.getRowCount() > 1500)
//        {
//            request.setAttribute(KeyConstant.ERROR_MESSAGE, "导出的记录数不能超过1500");
//
//            return mapping.findForward("error");
//        }

        if (pageSeparate.getRowCount() == 0)
        {
            request.setAttribute(KeyConstant.ERROR_MESSAGE, "导出的记录数为0");

            return mapping.findForward("error");
        }

        // 查询的单据
        List<InvoiceinsVO> beanList = invoiceinsDAO.queryEntityVOsByCondition(condition);

        OutputStream out = null;

        String filenName = null;

        filenName = "Invoiceins_" + TimeTools.now("MMddHHmmss") + ".xls";

        reponse.setContentType("application/x-dbf");

        reponse.setHeader("Content-Disposition", "attachment; filename=" + filenName);

        WritableWorkbook wwb = null;

        WritableSheet ws = null;

        try
        {
            out = reponse.getOutputStream();

            // create a excel
            wwb = Workbook.createWorkbook(out);

            ws = wwb.createSheet("Invoiceins", 0);

            int i = 0, j = 0;

            InvoiceinsVO element = null;

            WritableFont font = new WritableFont(WritableFont.ARIAL, 10, WritableFont.BOLD, false,
                jxl.format.UnderlineStyle.NO_UNDERLINE, jxl.format.Colour.BLUE);

            WritableFont font2 = new WritableFont(WritableFont.ARIAL, 10, WritableFont.BOLD, false,
                jxl.format.UnderlineStyle.NO_UNDERLINE, jxl.format.Colour.BLACK);

            WritableCellFormat format = new WritableCellFormat(font);
            
            format.setWrap(true); 

            WritableCellFormat format2 = new WritableCellFormat(font2);

            ws.addCell(new Label(j++ , i, "时间", format));
            ws.addCell(new Label(j++ , i, "发票标识", format));
            ws.addCell(new Label(j++ , i, "纳税实体", format));
            ws.addCell(new Label(j++ , i, "发票抬头", format));
            ws.addCell(new Label(j++ , i, "客户", format));
            ws.addCell(new Label(j++ , i, "发票类型", format));
            ws.addCell(new Label(j++ , i, "税率", format));
            ws.addCell(new Label(j++ , i, "金额", format));
            ws.addCell(new Label(j++ , i, "备注", format));
            ws.addCell(new Label(j++ , i, "经办人", format));
            
            ws.addCell(new Label(j++ , i, "销售单号", format));
            ws.addCell(new Label(j++ , i, "开票金额", format));
            ws.addCell(new Label(j++ , i, "对应税额", format));
            ws.addCell(new Label(j++ , i, "业务员", format));
            ws.addCell(new Label(j++ , i, "过单时间", format));
            ws.addCell(new Label(j++ , i, "发票号码", format));
            
            ws.addCell(new Label(j++ , i, "旧货属性", format));
            ws.addCell(new Label(j++ , i, "管理属性", format));
            
            //导出关联的销售单号 
            for (Iterator iter = beanList.iterator(); iter.hasNext();)
            {
                element = (InvoiceinsVO)iter.next();
                
                // 只导结束态的数据
//                if (element.getStatus() != FinanceConstant.INVOICEINS_STATUS_END)
//                	continue;
                
                List<InsVSInvoiceNumBean> insVSNumList = insVSInvoiceNumDAO.queryEntityBeansByFK(element.getId());
                
                List<InsVSOutBean> insVoList = insVSOutDAO.queryEntityBeansByFK(element.getId(),AnoConstant.FK_FIRST);
                
                for (InsVSOutBean eachVS : insVoList)
                {
                    j = 0;
                    i++ ;

                    ws.addCell(new Label(j++ , i, element.getLogTime()));
                    ws.addCell(new Label(j++ , i, element.getId()));
                    ws.addCell(new Label(j++ , i, element.getDutyName()));
                    ws.addCell(new Label(j++ , i, element.getHeadContent()));
                    ws.addCell(new Label(j++ , i, element.getCustomerName()));
                    ws.addCell(new Label(j++ , i, element.getInvoiceName()));
                    ws.addCell(new Label(j++ , i, MathTools.formatNum(element.getVal())));
                    ws.addCell(new Label(j++ , i, MathTools.formatNum(element.getMoneys())));
                    ws.addCell(new Label(j++ , i, element.getDescription()));
                    ws.addCell(new Label(j++ , i, element.getOperatorName()));
                    
                    OutBean outBean = null;
                    BaseBean base = null;
                    
                    if (!StringTools.isNullOrNone(eachVS.getOutId()))
                    {
                    	outBean = outDAO.find(eachVS.getOutId());
                        
                    	if (null == outBean){
                    		
                    		OutBalanceBean obean = outBalanceDAO.find(eachVS.getOutId());
                    		
                    		if (null != obean)
                    		{
                    			outBean = outDAO.find(obean.getOutId());
                    		}
                    	}
                    	
                    	if (null != outBean){
                    		base  = baseDAO.queryEntityBeansByFK(outBean.getFullId()).get(0);
                    	}
                    }
                    
                    if (eachVS.getType() == FinanceConstant.INSVSOUT_TYPE_OUT)
                    {
                    	ws.addCell(new Label(j++ , i, eachVS.getOutId()));
                    }
                    else{
                    	if (StringTools.isNullOrNone(eachVS.getOutBalanceId()))
                    		ws.addCell(new Label(j++ , i, eachVS.getOutId()));	
                    	else
                    		ws.addCell(new Label(j++ , i, eachVS.getOutBalanceId()));
                    }
                    
                    ws.addCell(new Label(j++ , i, MathTools.formatNum(eachVS.getMoneys())));
                    // 税额
                    ws.addCell(new Label(j++ , i, MathTools.formatNum(eachVS.getMoneys()/(1 + element.getVal()/100) * element.getVal()/100)));
                    ws.addCell(new Label(j++ , i, element.getStafferName()));
                    
                    if (null != outBean)
                    	ws.addCell(new Label(j++ , i, outBean.getChangeTime()));
                    else
                    	ws.addCell(new Label(j++ , i, ""));
                    
                    StringBuffer sb = new StringBuffer();
                    if(!ListTools.isEmptyOrNull(insVSNumList))
                    {
                    	for(int k = 0; k < insVSNumList.size() ; k++)
                    	{
                    		sb.append(insVSNumList.get(k).getInvoiceNum());
                    		
                    		if((k+1) < insVSNumList.size())
                    		{
                    			sb.append(",\n\n");
                    		}
                    	}
                    }
                    
                    ws.addCell(new Label(j++ , i, sb.toString()));
                    
                    if (null != outBean)
                    {
                    	ws.addCell(new Label(j++ , i, base.getOldGoods()== 9522152 ? "旧货" : "非旧货"));
                        ws.addCell(new Label(j++ , i, outBean.getMtype() == 1 ? "普通" : "管理"));
                    }else{
                    	ws.addCell(new Label(j++ , i, ""));
                    	ws.addCell(new Label(j++ , i, ""));
                    }
                    
                }

            }
        }
        catch (Exception e)
        {
            e.printStackTrace();

            return null;
        }
        finally
        {
            if (wwb != null)
            {
                try
                {
                    wwb.write();
                    wwb.close();
                }
                catch (Exception e1)
                {
                }
            }
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
        }

        return null;
    }

    /**
     * exportInvoiceins2
     * 
     * @param mapping
     * @param form
     * @param request
     * @param reponse
     * @return
     * @throws ServletException
     */
    public ActionForward exportInvoiceins2(ActionMapping mapping, ActionForm form,
                                          HttpServletRequest request, HttpServletResponse reponse)
        throws ServletException
    {
        PageSeparate pageSeparate = PageSeparateTools.getPageSeparate(request, QUERYINVOICEINS);

        ConditionParse condition = PageSeparateTools.getCondition(request, QUERYINVOICEINS);

//        if (pageSeparate.getRowCount() > 1500)
//        {
//            request.setAttribute(KeyConstant.ERROR_MESSAGE, "导出的记录数不能超过1500");
//
//            return mapping.findForward("error");
//        }

        if (pageSeparate.getRowCount() == 0)
        {
            request.setAttribute(KeyConstant.ERROR_MESSAGE, "导出的记录数为0");

            return mapping.findForward("error");
        }

        // 查询的单据
        List<InvoiceinsVO> beanList = invoiceinsDAO.queryEntityVOsByCondition(condition);

        OutputStream out = null;

        String filenName = null;

        filenName = "Invoiceins2_" + TimeTools.now("MMddHHmmss") + ".xls";

        reponse.setContentType("application/x-dbf");

        reponse.setHeader("Content-Disposition", "attachment; filename=" + filenName);

        WritableWorkbook wwb = null;

        WritableSheet ws = null;

        try
        {
            out = reponse.getOutputStream();

            // create a excel
            wwb = Workbook.createWorkbook(out);

            ws = wwb.createSheet("Invoiceins", 0);

            int i = 0, j = 0;

            InvoiceinsVO element = null;

            WritableFont font = new WritableFont(WritableFont.ARIAL, 10, WritableFont.BOLD, false,
                jxl.format.UnderlineStyle.NO_UNDERLINE, jxl.format.Colour.BLUE);

            WritableFont font2 = new WritableFont(WritableFont.ARIAL, 10, WritableFont.BOLD, false,
                jxl.format.UnderlineStyle.NO_UNDERLINE, jxl.format.Colour.BLACK);

            WritableCellFormat format = new WritableCellFormat(font);
            
            format.setWrap(true); 

            WritableCellFormat format2 = new WritableCellFormat(font2);

            ws.addCell(new Label(j++ , i, "时间", format));
            ws.addCell(new Label(j++ , i, "发票标识", format));
            ws.addCell(new Label(j++ , i, "纳税实体", format));
            ws.addCell(new Label(j++ , i, "发票抬头", format));
            ws.addCell(new Label(j++ , i, "客户", format));
            ws.addCell(new Label(j++ , i, "发票类型", format));
            ws.addCell(new Label(j++ , i, "税率", format));
            ws.addCell(new Label(j++ , i, "金额", format));
            ws.addCell(new Label(j++ , i, "经办人", format));
            
            ws.addCell(new Label(j++ , i, "发票号码", format));
            ws.addCell(new Label(j++ , i, "发票金额", format));
            ws.addCell(new Label(j++ , i, "发票税额", format));
            
            //导出关联的销售单号 
            for (Iterator iter = beanList.iterator(); iter.hasNext();)
            {
                element = (InvoiceinsVO)iter.next();
                
                // 只导结束态的数据
//                if (element.getStatus() != FinanceConstant.INVOICEINS_STATUS_END)
//                	continue;
                
                List<InsVSInvoiceNumBean> insVSNumList = insVSInvoiceNumDAO.queryEntityBeansByFK(element.getId());
                
                for (InsVSInvoiceNumBean eachVS : insVSNumList)
                {
                    j = 0;
                    i++ ;

                    ws.addCell(new Label(j++ , i, element.getLogTime()));
                    ws.addCell(new Label(j++ , i, element.getId()));
                    ws.addCell(new Label(j++ , i, element.getDutyName()));
                    ws.addCell(new Label(j++ , i, element.getHeadContent()));
                    ws.addCell(new Label(j++ , i, element.getCustomerName()));
                    ws.addCell(new Label(j++ , i, element.getInvoiceName()));
                    ws.addCell(new Label(j++ , i, MathTools.formatNum(element.getVal())));
                    ws.addCell(new Label(j++ , i, MathTools.formatNum(element.getMoneys())));
                    ws.addCell(new Label(j++ , i, element.getOperatorName()));
                    
                    ws.addCell(new Label(j++ , i, eachVS.getInvoiceNum()));
                    ws.addCell(new Label(j++ , i, MathTools.formatNum(eachVS.getMoneys())));
                    // 税额
                    ws.addCell(new Label(j++ , i, MathTools.formatNum(eachVS.getMoneys()/(1 + element.getVal()/100) * element.getVal()/100)));
                    
                }

            }
        }
        catch (Exception e)
        {
            e.printStackTrace();

            return null;
        }
        finally
        {
            if (wwb != null)
            {
                try
                {
                    wwb.write();
                    wwb.close();
                }
                catch (Exception e1)
                {
                }
            }
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
        }

        return null;
    }
    
    /**
     * 向导页面从1到2
     * 
     * @param mapping
     * @param form
     * @param request
     * @param reponse
     * @return
     * @throws ServletException
     */
    public ActionForward navigationAddInvoiceins(ActionMapping mapping, ActionForm form,
                                                 HttpServletRequest request,
                                                 HttpServletResponse reponse)
        throws ServletException
    {
        request.getSession().removeAttribute("bean");
        request.getSession().removeAttribute("pmap");

        InvoiceinsBean bean = new InvoiceinsBean();

        BeanUtil.getBean(bean, request);

        request.setAttribute("bean", bean);

        request.getSession().setAttribute("bean", bean);

        Map<String, String> pmap = CommonTools.saveParamersToMap(request);

        request.setAttribute("pmap", pmap);
        
        request.getSession().setAttribute("pmap", pmap);

        String outId = request.getParameter("outId");

        // 选择的销售单哦(委托单最终也是销售单)
        if (StringTools.isNullOrNone(outId))
        {
            return ActionTools.toError("没有开票的销售单", mapping, request);
        }

        String[] split = outId.split(";");
        
        Map<String, InvoiceinsDetailBean> imap = new HashMap<String, InvoiceinsDetailBean>();
        
        List<InsVSOutBean> vsList = new ArrayList<InsVSOutBean>();

        // 处理每个销售单,进行合并开单品名和价格
        for (int i = 0; i < split.length; i++ )
        {
            OutBean outBean = outDAO.find(split[i]);

            OutBalanceBean outBalanceBean = null;

            if (outBean == null)
            {
                outBalanceBean = outBalanceDAO.find(split[i]);

                if (outBalanceBean == null)
                {
                    return ActionTools.toError(split[i] + "的销售单不存在", mapping, request);
                }

                // 原始的单据
                outBean = outDAO.find(outBalanceBean.getOutId());

                if (outBean == null)
                {
                    return ActionTools.toError(split[i] + "的销售单不存在", mapping, request);
                }
                
				prepareVSList(vsList, outBalanceBean);
                
                List<BaseBalanceBean> bbList = baseBalanceDAO.queryEntityBeansByFK(outBalanceBean.getId());
                
                for (BaseBalanceBean eachb : bbList)
                {
                	if ((eachb.getAmount() * eachb.getSailPrice()) <= eachb.getInvoiceMoney())
                		continue;
                	
                	BaseBean base = baseDAO.find(eachb.getBaseId());
                	
                	if (!imap.containsKey(base.getProductId()))
                	{
                		InvoiceinsDetailBean idBean = new InvoiceinsDetailBean();
                		
                		idBean.setProductId(base.getProductId());
                		idBean.setProductName(base.getProductName());
                		idBean.setAmount(eachb.getAmount());
                		idBean.setMoneys(eachb.getAmount() * eachb.getSailPrice() - eachb.getInvoiceMoney());
                		
                		imap.put(base.getProductId(), idBean);
                	}else{
                		InvoiceinsDetailBean idBean = imap.get(base.getProductId());
                		
                		idBean.setAmount(idBean.getAmount() + eachb.getAmount());
                		idBean.setMoneys(idBean.getMoneys() + eachb.getAmount() * eachb.getSailPrice() - eachb.getInvoiceMoney());
                	}
                }
            }else{
            	prepareVSList(vsList, outBean);
            	
            	List<BaseBean> baseList = baseDAO.queryEntityBeansByFK(outBean.getFullId());
            	
            	for(BaseBean base : baseList)
            	{
            		if (base.getValue() <= base.getInvoiceMoney())
            			continue;
            		
            		if (!imap.containsKey(base.getProductId()))
                	{
                		InvoiceinsDetailBean idBean = new InvoiceinsDetailBean();
                		
                		idBean.setProductId(base.getProductId());
                		idBean.setProductName(base.getProductName());
                		idBean.setAmount(base.getAmount());
                		idBean.setMoneys(base.getValue() - base.getInvoiceMoney());
                		
                		imap.put(base.getProductId(), idBean);
                	}else{
                		InvoiceinsDetailBean idBean = imap.get(base.getProductId());
                		
                		idBean.setAmount(idBean.getAmount() + base.getAmount());
                		idBean.setMoneys(idBean.getMoneys() + base.getValue() - base.getInvoiceMoney());
                	}
            	}
            }
        }
        
        List<InvoiceinsDetailBean> detailList = new ArrayList<InvoiceinsDetailBean>();
        
        for (InvoiceinsDetailBean each : imap.values())
        {
        	detailList.add(each);
        }
        
        request.setAttribute("itemList", detailList);
        
        request.setAttribute("vsList", vsList);

        prepare(request);

        // 获得财务审批的权限人(1604)
        List<StafferBean> stafferList = stafferManager
            .queryStafferByAuthId(AuthConstant.INVOICEINS_OPR);

        request.setAttribute("stafferList", stafferList);

        return mapping.findForward("navigationAddInvoiceins2");
    }

	private void prepareVSList(List<InsVSOutBean> vsList,
			OutBalanceBean outBalanceBean)
	{
		double refMoneys = outBalanceDAO.sumByOutBalanceId(outBalanceBean.getId());
		
		// 已开的发票 = 已开票 + 未审批结束的金额
		double notEndInvoice = insVSOutDAO.sumOutHasInvoiceStatusNotEnd(outBalanceBean.getId());
		
		InsVSOutBean vsBean = new InsVSOutBean();
		 
		vsBean.setOutId(outBalanceBean.getId());
		vsBean.setMoneys(outBalanceBean.getTotal() - refMoneys - outBalanceBean.getInvoiceMoney() - notEndInvoice);
		vsBean.setType(1);
		
		vsList.add(vsBean);
	}
	
	private void prepareVSList(List<InsVSOutBean> vsList,
			OutBean outBean)
	{
		double retTotal = outDAO.sumOutBackValueIgnoreStatus(outBean.getFullId());
    	
    	// 已开的发票 = 已开票 + 未审批结束的金额
    	double notEndInvoice = insVSOutDAO.sumOutHasInvoiceStatusNotEnd(outBean.getFullId());
    	
		InsVSOutBean vsBean = new InsVSOutBean();
		 
		vsBean.setOutId(outBean.getFullId());
		vsBean.setMoneys(outBean.getTotal() - retTotal - outBean.getInvoiceMoney() - notEndInvoice);
		vsBean.setType(0);
		
		vsList.add(vsBean);
	}

	/**
     * 新的开票页面1到页面2
     * @param mapping
     * @param form
     * @param request
     * @param reponse
     * @return
     * @throws ServletException
     */
    public ActionForward navigationAddInvoiceins1(ActionMapping mapping, ActionForm form,
            HttpServletRequest request,
            HttpServletResponse reponse)
	throws ServletException
	{
        request.getSession().removeAttribute("bean");
        request.getSession().removeAttribute("pmap");

        InvoiceinsBean bean = new InvoiceinsBean();

        BeanUtil.getBean(bean, request);

        request.setAttribute("bean", bean);

        request.getSession().setAttribute("bean", bean);

        Map<String, String> pmap = CommonTools.saveParamersToMap(request);

        request.setAttribute("pmap", pmap);
        
        request.getSession().setAttribute("pmap", pmap);

        String outId = request.getParameter("outId");
    	
        String id = request.getParameter("id");
        
        if (StringTools.isNullOrNone(id))
        {
        	id = "";
        }
        // 附件
        List<AttachmentBean> attachmentList = attachmentDAO.queryEntityVOsByFK(id);

        String attacmentIds = "";

        for (AttachmentBean attachmentBean : attachmentList)
        {
            attacmentIds = attacmentIds + attachmentBean.getId() + ";";
        }

        request.setAttribute("attacmentIds", attacmentIds);
        
        return processMayInvoiceins(mapping, request, bean, outId);
	}
	
    /**
     * 
     * @param mapping
     * @param request
     * @param bean
     * @param outId
     * @return
     */
	private ActionForward processMayInvoiceins(ActionMapping mapping,
			HttpServletRequest request, InvoiceinsBean bean, String outId)
	{
		// 选择的销售单哦(委托单最终也是销售单)
        if (StringTools.isNullOrNone(outId))
        {
            return ActionTools.toError("没有开票的销售单", mapping, request);
        }
		
        String id = request.getParameter("id");
        
        if (StringTools.isNullOrNone(id))
        {
        	id = "";
        }
        
		String[] split = outId.split(";");

		// 取销售单的配送地址时，不允许一票中有多销售单
		if (split.length > 1 && bean.getFillType() == 1) {
			return ActionTools.toError("取销售单的配送地址时，不允许一票中有多销售单", mapping, request);
		}
		
        List<BaseBean> itemList = new ArrayList<BaseBean>();
        
        List<InsVSOutBean> vsList = new ArrayList<InsVSOutBean>();
        
        String unioutId = "";
        
        // CORE 组装assemble未开票的商品明细
        for (int i = 0 ; i < split.length; i++)
        {
            int type = 0;

            OutBean outBean = outDAO.find(split[i]);

            OutBalanceBean outBalanceBean = null;

            if (outBean == null)
            {
                outBalanceBean = outBalanceDAO.find(split[i]);

                if (outBalanceBean == null)
                {
                    return ActionTools.toError(split[i] + "的销售单不存在", mapping, request);
                }

                // 原始的单据
                outBean = outDAO.find(outBalanceBean.getOutId());

                if (outBean == null)
                {
                    return ActionTools.toError(split[i] + "的销售单不存在", mapping, request);
                }

                type = 1;
            }

            unioutId = outBean.getFullId();
            
            // 0:销售单 1:结算单
            if (type == 0)
            {
            	prepareVSList(vsList, outBean);
            	
            	List<BaseBean> baseList = baseDAO.queryEntityBeansByFK(outBean.getFullId());
            	
            	List<BaseBean> mergeBaseList = OutHelper.trimBaseList4(baseList);
            	
            	// 退货产品明细
            	ConditionParse con = new ConditionParse();

                con.addWhereStr();

                con.addCondition("OutBean.refOutFullId", "=", outBean.getFullId());

                con.addIntCondition("OutBean.type", "=", OutConstant.OUT_TYPE_INBILL);

                con.addIntCondition("OutBean.outType", "=", OutConstant.OUTTYPE_IN_OUTBACK);

                List<OutBean> refOutList = outDAO.queryEntityBeansByCondition(con);
                
                for (OutBean each : refOutList)
                {
                	List<BaseBean> list = baseDAO.queryEntityBeansByFK(each.getFullId());
                	
                	each.setBaseList(list);
                }
                
                // 计算出已经退货的数量                
                for (BaseBean baseBean : mergeBaseList)
                {
                    int hasBack = 0;

                    // 退库
                    for (OutBean ref : refOutList)
                    {
                        List<BaseBean> refOutBaseList = OutHelper.trimBaseList2(ref.getBaseList());

                        for (BaseBean refBase : refOutBaseList)
                        {
                            if (refBase.equals2(baseBean))
                            {
                                hasBack += refBase.getAmount();

                                break;
                            }
                        }
                    }

                    baseBean.setMtype(0);
                    
                    baseBean.setInway(hasBack);
                }
                                
                // 已开票产品明细                
                List<InvoiceinsItemBean> invoiceinsList = invoiceinsItemDAO.queryHasInvoiceStatusNotEnd(outBean.getFullId());
            	
            	if (!ListTools.isEmptyOrNull(invoiceinsList))
            	{
            		for (BaseBean baseBean : mergeBaseList)
                    {
                        int hasBack = 0;

                        // 申请开票除外
                        for (InvoiceinsItemBean each : invoiceinsList)
                        {
                            if (each.getBaseId().equals(baseBean.getId()))
                            {
                                hasBack += each.getAmount();
                            }
                        }

                        baseBean.setMtype(0);
                        
                        baseBean.setInway(baseBean.getInway() + hasBack);
                        
                    }
            	}
            	
            	itemList.addAll(mergeBaseList);
            }
            else
            {
            	prepareVSList(vsList, outBalanceBean);
            	
            	// 结算单
            	List<BaseBalanceBean> baseBalanceList = baseBalanceDAO.queryEntityBeansByFK(outBalanceBean.getId());
            	
            	List<BaseBean> baseList = new ArrayList<BaseBean>();
            	
            	for (BaseBalanceBean each : baseBalanceList)
            	{
            		String baseId = each.getBaseId();
            		
            		BaseBean baseBean = baseDAO.find(baseId);
            		
            		if (null != baseBean)
            		{
            			baseBean.setId(each.getId());
            			baseBean.setOutId(each.getParentId());
            			baseBean.setAmount(each.getAmount());
            			baseBean.setPrice(each.getSailPrice());
            			baseBean.setMtype(1);
            			baseBean.setDescription("");
            			baseBean.setLocationId(each.getBaseId());
            			
            			baseList.add(baseBean);
            		}
            	}
            	
            	// 已开票产品明细                
                List<InvoiceinsItemBean> invoiceinsList = invoiceinsItemDAO.queryHasInvoiceStatusNotEnd(outBalanceBean.getId());
            	
            	if (!ListTools.isEmptyOrNull(invoiceinsList))
            	{
            		for (BaseBean baseBean : baseList)
                    {
                        int hasBack = 0;

                        // 申请开票除外
                        for (InvoiceinsItemBean each : invoiceinsList)
                        {
                            if (each.getBaseId().equals(baseBean.getId())                             		
                            		&& each.getOutId().equals(baseBean.getOutId()))
                            {
                                hasBack += each.getAmount();
                            }
                        }
                        
                        baseBean.setInway(hasBack);
                    }
            	}
            	
            	// 结算单退货明细
            	List<BaseBalanceBean> refList = new ArrayList<BaseBalanceBean>();
            	
            	List<OutBalanceBean> balanceList = outBalanceDAO.queryEntityBeansByFK(outBalanceBean.getId(), AnoConstant.FK_FIRST);
            	
            	for (OutBalanceBean each : balanceList)
            	{
            		List<BaseBalanceBean> bbList = baseBalanceDAO.queryEntityBeansByFK(each.getId());
            		
            		refList.addAll(bbList);
            	}
            	
        		for (BaseBean baseBean : baseList)
                {
                    int hasBack = 0;

                    // 申请开票除外
                    for (BaseBalanceBean each : refList)
                    {
                        if (each.getBaseId().equals(baseBean.getLocationId()))
                        {
                            hasBack += each.getAmount();
                        }
                    }
                    
                    baseBean.setInway(baseBean.getInway() + hasBack);
                }
            	
            	itemList.addAll(baseList);
            }
        }
        
        // 根据销售单检查是否有配送地址
        List<DistributionBean> distList = distributionDAO.queryEntityBeansByFK(unioutId);
        
        if (ListTools.isEmptyOrNull(distList) && bean.getFillType() == 1) {
        	return ActionTools.toError("销售单" + unioutId + "配送信息不存在", mapping, request);
        }
        
        request.setAttribute("itemList", itemList);
        
        request.setAttribute("vsList", vsList);
        
        prepare(request);

        // 获得财务审批的权限人(1604)
        List<StafferBean> stafferList = stafferManager
            .queryStafferByAuthId(AuthConstant.INVOICEINS_OPR);

        request.setAttribute("stafferList", stafferList);

        return mapping.findForward("navigationAddInvoiceins2");
	}
    
    
    /**
     * addInvoiceins
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward addInvoiceins(ActionMapping mapping, ActionForm form,
                                       HttpServletRequest request, HttpServletResponse response)
        throws ServletException
    {
        String mode = request.getParameter("mode");

        try
        {
            InvoiceinsBean bean = createIns(request);

            User user = Helper.getUser(request);

            financeFacade.addInvoiceinsBean(user.getId(), bean);

            request.setAttribute(KeyConstant.MESSAGE, "成功操作");
        }
        catch (MYException e)
        {
            _logger.warn(e, e);

            request.setAttribute(KeyConstant.ERROR_MESSAGE, "操作失败:" + e.getMessage());
        }

        CommonTools.removeParamers(request);

        request.setAttribute("mode", mode);

        RequestTools.menuInitQuery(request);

        return mapping.findForward("queryInvoiceins");
    }

    /**
     * 导航结束
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward addInvoiceinsInNavigation(ActionMapping mapping, ActionForm form,
                                                   HttpServletRequest request,
                                                   HttpServletResponse response)
        throws ServletException
    {
    	InvoiceinsBean bean = (InvoiceinsBean)request.getSession().getAttribute("bean");
        
        User user = Helper.getUser(request);
    	
    	//商务 - begin
        ActionForward error = checkAuthForEcommerce(request, user, mapping);
    	
    	if (null != error)
    	{
    		return error;
    	}
        
        User g_srcUser = (User)request.getSession().getAttribute("g_srcUser");
        
        String elogin = (String)request.getSession().getAttribute("g_elogin");
        
        String g_loginType = (String)request.getSession().getAttribute("g_loginType");
        
        if (!StringTools.isNullOrNone(elogin) && null == g_srcUser)
        {
            request.setAttribute(KeyConstant.ERROR_MESSAGE, "登陆异常,请重新登陆");

            return mapping.findForward("error");
        }
        
        // 当前切换用户登陆的且为商务登陆的，记录经办人
        if (!StringTools.isNullOrNone(elogin) && null != g_srcUser && g_loginType.equals("1"))
        {
        	bean.setOperator(g_srcUser.getStafferId());
        	bean.setOperatorName(g_srcUser.getStafferName());
        }
        else
        {
        	bean.setOperator(user.getStafferId());
        	bean.setOperatorName(user.getStafferName());
        }
        // 商务 - end
    	
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

            return mapping.findForward("error");
        }
        catch (Exception e)
        {
            _logger.error(e, e);

            request.setAttribute(KeyConstant.ERROR_MESSAGE, "增加失败");

            return mapping.findForward("error");
        }

        ActionForward afor = parserAttachment(mapping, request, rds, bean);

        if (afor != null)
        {
            return afor;
        }

        rds.close();
        
        try
        {
            createInsInNavigation1(rds, bean);

            bean.setLocationId(user.getLocationId());

            financeFacade.addInvoiceinsBean(user.getId(), bean);

            request.setAttribute(KeyConstant.MESSAGE, "成功操作");
        }
        catch (MYException e)
        {
            _logger.warn(e, e);

            request.setAttribute(KeyConstant.ERROR_MESSAGE, "操作失败:" + e.getMessage());
        }

        CommonTools.removeParamers(request);
        
        request.getSession().removeAttribute("pmap");

        request.setAttribute("mode", "0");

        RequestTools.menuInitQuery(request);

        request.getSession().removeAttribute("bean");

        return mapping.findForward("queryInvoiceins");
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
                                           RequestDataStream rds, InvoiceinsBean invoiceinsBean)
    {
        List<AttachmentBean> attachmentList = new ArrayList<AttachmentBean>();

        invoiceinsBean.setAttachmentList(attachmentList);

        String attachmentIds = rds.getParameter("attacmentIds");
        
        if (StringTools.isNullOrNone(attachmentIds))
        {
        	attachmentIds = "";
        }

        String[] split = attachmentIds.split(";");

        for (String each : split)
        {
            if (StringTools.isNullOrNone(each))
            {
                continue;
            }

            AttachmentBean att = attachmentDAO.find(each);

            if (att != null)
            {
                attachmentList.add(att);
            }
        }

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

                return mapping.findForward("querySelfTravelApply");
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
     * 
     * @param root
     * @return
     */
    private String mkdir(String root)
    {
        String path = TimeTools.now("yyyy/MM/dd/HH") + "/"
                      + SequenceTools.getSequence(String.valueOf(new Random().nextInt(1000)));

        FileTools.mkdirs(root + '/' + path);

        return path;
    }

    /**
     * @return the flowAtt 
     */
    public String getAttachmentPath()
    {
        return ConfigLoader.getProperty("invoiceinsAttachmentPath");
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
    
    private ActionForward checkAuthForEcommerce(HttpServletRequest request, User user, ActionMapping mapping)
    {
        // 针对非商务模式下，业务员开单要有权限限制
        String elogin = (String)request.getSession().getAttribute("g_elogin");
        
        String g_loginType = (String)request.getSession().getAttribute("g_loginType");
        
        // elogin 为空，则表示非商务模式, elogin 1 表示是商务切换登陆
        if (StringTools.isNullOrNone(elogin) || (elogin.equals("1") && g_loginType.equals("2")))
        {
        	// 检查是否有权限
        	if (!containAuth(user, AuthConstant.DIRECT_SALE))
        	{
                request.setAttribute(KeyConstant.ERROR_MESSAGE, "非商务模式下,没有权限操作");

                return mapping.findForward("error");
        	}
        }
		return null;
    }
    
    private boolean containAuth(User user, String authId)
    {
        if (StringTools.isNullOrNone(authId))
        {
            return true;
        }

        if (authId.equals(AuthConstant.PUNLIC_AUTH))
        {
            return true;
        }

        List<RoleAuthBean> authList = user.getAuth();

        for (RoleAuthBean roleAuthBean : authList)
        {
            if (roleAuthBean.getAuthId().equals(authId))
            {
                return true;
            }
        }

        return false;
    }
    
    /**
     * createIns
     * 
     * @param request
     * @return
     */
    private InvoiceinsBean createIns(HttpServletRequest request)
        throws MYException
    {
        InvoiceinsBean bean = new InvoiceinsBean();

        BeanUtil.getBean(bean, request);

        String[] showIds = request.getParameterValues("showId");
        String[] amounts = request.getParameterValues("amount");
        String[] prices = request.getParameterValues("price");
        String[] specials = request.getParameterValues("special");
        String[] units = request.getParameterValues("sunit");

        List<InvoiceinsItemBean> itemList = new ArrayList<InvoiceinsItemBean>();

        for (int i = 0; i < showIds.length; i++ )
        {
            if (StringTools.isNullOrNone(showIds[i]))
            {
                break;
            }

            InvoiceinsItemBean item = new InvoiceinsItemBean();

            item.setShowId(showIds[i]);
            item.setShowName(showDAO.find(showIds[i]).getName());
            item.setAmount(MathTools.parseInt(amounts[i]));
            item.setPrice(MathTools.parseDouble(prices[i]));
            item.setSpecial(specials[i].trim());
            item.setUnit(units[i].trim());

            itemList.add(item);
        }

        if (ListTools.isEmptyOrNull(itemList))
        {
            throw new MYException("没有开票项");
        }

        double total = 0.0d;

        for (InvoiceinsItemBean invoiceinsItemBean : itemList)
        {
            total += invoiceinsItemBean.getAmount() * invoiceinsItemBean.getPrice();
        }

        bean.setMoneys(total);

        bean.setItemList(itemList);

        User user = Helper.getUser(request);

        bean.setLocationId(user.getLocationId());

        bean.setStafferId(user.getStafferId());

        String outId = request.getParameter("outId");

        if ( !StringTools.isNullOrNone(outId))
        {
            double canUse = bean.getMoneys();

            List<InsVSOutBean> vsList = new ArrayList<InsVSOutBean>();

            String[] split = outId.split(";");

            for (int i = 0; i < split.length; i++ )
            {
                if ( !StringTools.isNullOrNone(split[i]))
                {
                    if (canUse == 0.0)
                    {
                        break;
                    }

                    InsVSOutBean vs = new InsVSOutBean();

                    vs.setOutId(split[i]);

                    vs.setType(FinanceConstant.INSVSOUT_TYPE_OUT);

                    OutBean out = outDAO.find(vs.getOutId());

                    OutBalanceBean balance = null;

                    if (out == null)
                    {
                        balance = outBalanceDAO.find(vs.getOutId());

                        if (balance == null)
                        {
                            throw new MYException("数据错误,请确认操作");
                        }
                        else
                        {
                            vs.setType(FinanceConstant.INSVSOUT_TYPE_BALANCE);
                        }
                    }

                    if (vs.getType() == FinanceConstant.INSVSOUT_TYPE_OUT)
                    {
                        // 剩余需要开票的金额
                        double imoney = out.getTotal() - out.getInvoiceMoney();

                        if (canUse >= imoney)
                        {
                            vs.setMoneys(imoney);

                            canUse = canUse - imoney;
                        }
                        else
                        {
                            vs.setMoneys(canUse);

                            canUse = 0.0d;
                        }
                    }
                    else
                    {
                        // 剩余需要开票的金额
                        double imoney = balance.getTotal() - balance.getInvoiceMoney();

                        if (canUse >= imoney)
                        {
                            vs.setMoneys(imoney);

                            canUse = canUse - imoney;
                        }
                        else
                        {
                            vs.setMoneys(canUse);

                            canUse = 0.0d;
                        }
                    }

                    vsList.add(vs);
                }
            }

            if ( !MathTools.equal(canUse, 0))
            {
                if (canUse > 0)
                {
                    throw new MYException("开票金额过多,请确认操作");
                }
            }

            bean.setVsList(vsList);

            StringBuffer buffer = new StringBuffer();

            for (InsVSOutBean insVSOutBean : vsList)
            {
                buffer.append(insVSOutBean.getOutId()).append(';');
            }

            bean.setRefIds(buffer.toString());
        }

        return bean;
    }

    private InvoiceinsItemBean findInvoiceinsItem(List<InvoiceinsItemBean> list, String showId)
    {
        for (InvoiceinsItemBean invoiceinsItemBean : list)
        {
            if (invoiceinsItemBean.getShowId().equals(showId))
            {
                return invoiceinsItemBean;
            }
        }

        return null;
    }

    /**
     * 导航最后一步
     * 
     * @param request
     * @param bean
     * @return
     * @throws MYException
     */
    private InvoiceinsBean createInsInNavigation(HttpServletRequest request, InvoiceinsBean bean)
        throws MYException
    {
        String[] showNames = request.getParameterValues("showName");
        //String[] specials = request.getParameterValues("special");
        String[] units = request.getParameterValues("sunit");
        String[] totals = request.getParameterValues("total");
        String[] amounts = request.getParameterValues("amount");

        List<InvoiceinsItemBean> itemList = new ArrayList<InvoiceinsItemBean>();

        for (int i = 0; i < showNames.length; i++ )
        {
            // 只有大于0的
            if (MathTools.parseDouble(totals[i]) > 0)
            {
                InvoiceinsItemBean item = new InvoiceinsItemBean();

                item.setShowId("");
                item.setShowName(showNames[i]);
                item.setAmount(MathTools.parseInt(amounts[i]));
                item.setPrice(MathTools.parseDouble(totals[i]));
                item.setMoneys(MathTools.parseDouble(totals[i]));
                //item.setSpecial(specials[i].trim());
                item.setUnit(units[i].trim());

                itemList.add(item);
            }
        }

        if (ListTools.isEmptyOrNull(itemList))
        {
            throw new MYException("没有开票项");
        }

        double total = 0.0d;

        for (InvoiceinsItemBean invoiceinsItemBean : itemList)
        {
            total += invoiceinsItemBean.getMoneys();
        }

        bean.setMoneys(total);

        User user = Helper.getUser(request);

        bean.setLocationId(user.getLocationId());

        bean.setStafferId(user.getStafferId());

        List<InsVSOutBean> vsList = new ArrayList<InsVSOutBean>();

        String vsOutIds[] = request.getParameterValues("vsOutId");
        String vsTypes [] = request.getParameterValues("vsType");
        String vsMoneys [] = request.getParameterValues("vsMoneys");

        for (int i = 0; i < vsOutIds.length; i++)
        {
        	InsVSOutBean vsBean = new InsVSOutBean();
        	
        	vsBean.setOutId(vsOutIds[i]);
        	vsBean.setType(MathTools.parseInt(vsTypes[i]));
        	vsBean.setMoneys(MathTools.parseDouble(vsMoneys[i]));
        	
        	vsList.add(vsBean);
        }
        
        bean.setVsList(vsList);

        bean.setItemList(itemList);

        StringBuffer buffer = new StringBuffer();

        for (InsVSOutBean insVSOutBean : vsList)
        {
            if ( !buffer.toString().contains(insVSOutBean.getOutId()))
            {
                buffer.append(insVSOutBean.getOutId()).append(';');
            }
        }

        bean.setRefIds(buffer.toString());

        return bean;
    }

    /**
     * 申请开票新导航 最后一步
     * @param request
     * @param bean
     * @return
     * @throws MYException
     */
    private InvoiceinsBean createInsInNavigation1(RequestDataStream rds, InvoiceinsBean bean)
    throws MYException
    {
    	StringBuffer sb = new StringBuffer();
    	
    	List<String> showNames = rds.getParameters("showName");
    	List<String> productIds = rds.getParameters("productId");
    	List<String> baseIds = rds.getParameters("baseId");
    	List<String> outIds = rds.getParameters("outFullId");
    	List<String> prices = rds.getParameters("price");        
    	List<String> backUnms = rds.getParameters("backUnm");
    	List<String> types = rds.getParameters("mtype");
    	List<String> sunits = rds.getParameters("sunit");
    	List<String> refBaseIds = rds.getParameters("refBaseIds");
    	List<String> costPrices = rds.getParameters("costPrice");
    	
        List<InvoiceinsItemBean> itemList = new ArrayList<InvoiceinsItemBean>();
        
        for (int i = 0; i < showNames.size(); i++ )
        {
            // 只有大于0的
            if (MathTools.parseInt(backUnms.get(i)) > 0)
            {
            	int amount = MathTools.parseInt(backUnms.get(i));
            	double price = MathTools.parseDouble(prices.get(i));
            	
            	String refBaseId = refBaseIds.get(i);
            	
            	String refBaseIdArr [] = null;
            	
            	if (refBaseId.indexOf(";") != -1)
            	{
            		refBaseIdArr = refBaseId.split(";");
            	}
            	
            	List<BaseBean> tmpList = new ArrayList<BaseBean>();
            	
            	// 检查合并情况
            	if (!StringTools.isNullOrNone(refBaseIdArr) && refBaseIdArr.length > 1)
            	{
            		for (String each : refBaseIdArr)
            		{
            			BaseBean baseBean = baseDAO.find(each);
            			
            			int hasInvoice = (int)(baseBean.getInvoiceMoney() / baseBean.getPrice());
            			
            			int mayInvoice = baseBean.getAmount() - hasInvoice;
            			
            			if (mayInvoice < 0)
            			{
            				continue;
            			}
            			
            			// 要拆分
            			if (amount >= mayInvoice)
            			{
            				BaseBean tmpBean = new BaseBean();
            				
            				tmpBean.setId(each);
            				tmpBean.setAmount(mayInvoice);
            				
            				tmpList.add(tmpBean);
            				
            				amount -= mayInvoice;
            			}
            			else
            			{
            				BaseBean tmpBean = new BaseBean();
            				
            				tmpBean.setId(each);
            				tmpBean.setAmount(amount);
            				
            				tmpList.add(tmpBean);
            				
            				amount = 0;
            			}
            			
            			if (amount == 0)
            				break;
            		}
            		
            		// 允许超出销售数量开票
            		if (amount > 0)
            		{
            			BaseBean tmpBean = tmpList.get(0);
            			
            			tmpBean.setAmount(tmpBean.getAmount() + amount);
            		}
            	}
            	
            	if (ListTools.isEmptyOrNull(tmpList))
            	{
            		InvoiceinsItemBean item = new InvoiceinsItemBean();

                    item.setShowId(showNames.get(i));
                    item.setShowName(showDAO.find(showNames.get(i)).getName());
                    
                    item.setUnit(sunits.get(i));

                    item.setAmount(amount);
                    item.setPrice(price);
                    
                    double moneys = item.getAmount() * item.getPrice();
                    
                    item.setMoneys(moneys);
                    
                    item.setBaseId(baseIds.get(i));
                    
                    item.setProductId(productIds.get(i));
                    
                    item.setOutId(outIds.get(i));                               
                    
                    item.setType(MathTools.parseInt(types.get(i)));
                    
                    item.setCostPrice(MathTools.parseDouble(costPrices.get(i)));
                    
                    if (!sb.toString().contains(item.getOutId()))
                	{
                		sb.append(item.getOutId()).append(";");
                	}
                    
                    itemList.add(item);  
            	}
            	else
            	{
            		for (BaseBean each : tmpList)
            		{
                		InvoiceinsItemBean item = new InvoiceinsItemBean();

                        item.setShowId(showNames.get(i));
                        item.setShowName(showDAO.find(showNames.get(i)).getName());
                        
                        item.setUnit(sunits.get(i));

                        item.setAmount(each.getAmount());
                        item.setPrice(price);
                        
                        double moneys = item.getAmount() * item.getPrice();
                        
                        item.setMoneys(moneys);
                        
                        item.setBaseId(each.getId());
                        
                        item.setProductId(productIds.get(i));
                        
                        item.setOutId(outIds.get(i));                               
                        
                        item.setType(MathTools.parseInt(types.get(i)));
                        
                        item.setCostPrice(MathTools.parseDouble(costPrices.get(i)));
                        
                        if (!sb.toString().contains(item.getOutId()))
                    	{
                    		sb.append(item.getOutId()).append(";");
                    	}
                        
                        itemList.add(item);  
                	
            		}
            	}
        		      	        	               
            }
        }

        if (ListTools.isEmptyOrNull(itemList))
        {
            throw new MYException("没有开票项");
        }
        
        double total = 0.0d;

        for (InvoiceinsItemBean invoiceinsItemBean : itemList)
        {
            total += invoiceinsItemBean.getMoneys();
        }

        bean.setMoneys(total);
        
        bean.setRefIds(sb.toString());
        
        bean.setItemList(itemList);
        
        List<InsVSOutBean> vsList = new ArrayList<InsVSOutBean>();

        List<String> vsOutIds = rds.getParameters("vsOutId");
        List<String> vsTypes = rds.getParameters("vsType");
        List<String> vsMoneys = rds.getParameters("vsMoneys");

        for (int i = 0; i < vsOutIds.size(); i++)
        {
        	InsVSOutBean vsBean = new InsVSOutBean();
        	
        	vsBean.setOutId(vsOutIds.get(i));
        	vsBean.setType(MathTools.parseInt(vsTypes.get(i)));
        	vsBean.setMoneys(MathTools.parseDouble(vsMoneys.get(i)));
        	vsBean.setBaseId("0"); // 标记
        	
        	vsList.add(vsBean);
        }
        
        bean.setVsList(vsList);

        StringBuffer buffer = new StringBuffer();

        for (InsVSOutBean insVSOutBean : vsList)
        {
            if ( !buffer.toString().contains(insVSOutBean.getOutId()))
            {
                buffer.append(insVSOutBean.getOutId()).append(';');
            }
        }

        bean.setRefIds(buffer.toString());
        
        return bean;
    }
    
    /**
     * deleteInvoiceins
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward deleteInvoiceins(ActionMapping mapping, ActionForm form,
                                          HttpServletRequest request, HttpServletResponse response)
        throws ServletException
    {
        AjaxResult ajax = new AjaxResult();

        try
        {
            String id = request.getParameter("id");

            User user = Helper.getUser(request);

            financeFacade.deleteInvoiceinsBean(user.getId(), id);

            ajax.setSuccess("成功删除");
        }
        catch (MYException e)
        {
            _logger.warn(e, e);

            ajax.setError("删除失败:" + e.getMessage());
        }

        return JSONTools.writeResponse(response, ajax);
    }

    /**
     * 核对
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward checkInvoiceins(ActionMapping mapping, ActionForm form,
                                         HttpServletRequest request, HttpServletResponse response)
        throws ServletException
    {
        AjaxResult ajax = new AjaxResult();

        try
        {
            String id = request.getParameter("id");

            String checks = request.getParameter("checks");

            String checkrefId = request.getParameter("checkrefId");

            User user = Helper.getUser(request);

            financeFacade.checkInvoiceinsBean2(user.getId(), id, checks, checkrefId);

            ajax.setSuccess("成功核对");
        }
        catch (MYException e)
        {
            _logger.warn(e, e);

            ajax.setError("核对失败:" + e.getMessage());
        }

        return JSONTools.writeResponse(response, ajax);
    }

    /**
     * passInvoiceins
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward passInvoiceins(ActionMapping mapping, ActionForm form,
                                        HttpServletRequest request, HttpServletResponse response)
        throws ServletException
    {
        String id = request.getParameter("id");

        String mode = request.getParameter("mode");

        String reason = request.getParameter("reason");

        try
        {
            User user = Helper.getUser(request);

            InvoiceinsBean invoiceinsBean = invoiceinsDAO.find(id);

            if (invoiceinsBean.getStatus() == FinanceConstant.INVOICEINS_STATUS_SUBMIT)
            {
            	// 收集 维护发票号、金额
            	String invoiceNums [] = request.getParameterValues("invoiceNum");
            	
            	String moneys [] = request.getParameterValues("moneys");
            	
            	List<InsVSInvoiceNumBean> numList = new ArrayList<InsVSInvoiceNumBean>();
            	
            	invoiceinsBean.setNumList(numList);
            	
            	if (invoiceNums != null)
            	{
            		for (int i = 0; i < invoiceNums.length; i++)
            		{
            			InsVSInvoiceNumBean numBean = new InsVSInvoiceNumBean();
            			
            			numBean.setInvoiceNum(invoiceNums[i]);
            			
            			numBean.setMoneys(MathTools.parseDouble(moneys[i]));
            			
            			numList.add(numBean);
            		}
            	}
            	
                financeFacade.passInvoiceinsBean(user.getId(), invoiceinsBean, reason);
            }

            if (invoiceinsBean.getStatus() == FinanceConstant.INVOICEINS_STATUS_CHECK)
            {
                financeFacade.checkInvoiceinsBean(user.getId(), id, reason);
            }

            request.setAttribute(KeyConstant.MESSAGE, "成功操作");
        }
        catch (MYException e)
        {
            _logger.warn(e, e);

            request.setAttribute(KeyConstant.ERROR_MESSAGE, "操作失败:" + e.getMessage());
        }

        CommonTools.removeParamers(request);

        request.setAttribute("mode", mode);

        return mapping.findForward("queryInvoiceins");
    }

    /**
     * rejectInvoiceins
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward rejectInvoiceins(ActionMapping mapping, ActionForm form,
                                          HttpServletRequest request, HttpServletResponse response)
        throws ServletException
    {
        String id = request.getParameter("id");

        String mode = request.getParameter("mode");

        String reason = request.getParameter("reason");
        
        InvoiceinsVO Invoiceinsvo = invoiceinsManager.findVO(id);

        try
        {
            User user = Helper.getUser(request);

            financeFacade.rejectInvoiceinsBean(user.getId(), id, reason);

            sendOutRejectMail(id, user, reason, Invoiceinsvo,"开票申请");
            
            request.setAttribute(KeyConstant.MESSAGE, "成功操作");
        }
        catch (MYException e)
        {
            _logger.warn(e, e);

            request.setAttribute(KeyConstant.ERROR_MESSAGE, "操作失败:" + e.getMessage());
        }

        CommonTools.removeParamers(request);

        request.setAttribute("mode", mode);

        return mapping.findForward("queryInvoiceins");
    }

    /**
     * 确认开票
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward confirmInvoice(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
	throws ServletException
	{
    	AjaxResult ajax = new AjaxResult();
    	
	    String id = request.getParameter("id");
	    
	    try
        {
            User user = Helper.getUser(request);

            financeFacade.confirmInvoice(user.getId(), id);
            
            ajax.setSuccess("操作成功");
        }
        catch (MYException e)
        {
            _logger.warn(e, e);
            
            ajax.setError("操作失败:" + e.getMessage());
        }
        
        return JSONTools.writeResponse(response, ajax);
	}
    
    /**
     * 确认付款
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward confirmPay(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
	throws ServletException
	{
    	AjaxResult ajax = new AjaxResult();
    	
	    String id = request.getParameter("id");		   
	    
	    try
        {
            User user = Helper.getUser(request);

            financeFacade.confirmPay(user.getId(), id);
            
            ajax.setSuccess("操作成功");
        }
        catch (MYException e)
        {
            _logger.warn(e, e);
            
            ajax.setError("操作失败:" + e.getMessage());
        }
        
        return JSONTools.writeResponse(response, ajax);
	}    
    
    /**
     * 退票
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward backInvoiceins(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
	throws ServletException
	{
    	String id = request.getParameter("id");
    	
    	try
    	{
    		User user = Helper.getUser(request);

            financeFacade.backInvoiceins(user.getId(), id);
    	}
    	catch(MYException e)
    	{
    		_logger.warn(e, e);
            
            request.setAttribute(KeyConstant.ERROR_MESSAGE, "操作失败:" + e.getMessage());
    	}
    	
    	return mapping.findForward("queryInvoiceins");
	}
    
    /**
     * 导入式 批量开具发票
     * importInvoice
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward importInvoiceins(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
	throws ServletException
	{
    	User user = Helper.getUser(request);
    	
        RequestDataStream rds = new RequestDataStream(request);
        
        boolean importError = false;
        
        List<InvoiceinsImportBean> importItemList = new ArrayList<InvoiceinsImportBean>(); 
        
        StringBuilder builder = new StringBuilder();
        
        try
        {
            rds.parser();
        }
        catch (Exception e1)
        {
            _logger.error(e1, e1);

            request.setAttribute(KeyConstant.ERROR_MESSAGE, "解析失败");

            return mapping.findForward("importInvoiceins");
        }

        if ( !rds.haveStream())
        {
            request.setAttribute(KeyConstant.ERROR_MESSAGE, "解析失败");

            return mapping.findForward("importInvoiceins");
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
                	InvoiceinsImportBean bean = new InvoiceinsImportBean();
                    
            		// 销售单
            		if ( !StringTools.isNullOrNone(obj[0]))
            		{
            			String value = obj[0].trim();
            			
                		bean.setOutId(value);
            		}
            		else
            		{
            			builder
                        .append("第[" + currentNumber + "]错误:")
                        .append("销售单不能为空")
                        .append("<br>");
                    
            			importError = true;
            		}
            		
            		// 开票金额
            		if ( !StringTools.isNullOrNone(obj[1]))
            		{
            			double value = MathTools.parseDouble(obj[1].trim());
            			
            			if (value <= 0) {
            				builder
                            .append("第[" + currentNumber + "]错误:")
                            .append("开票金额不能小于0")
                            .append("<br>");
                        
                			importError = true;
            			} else {
            				bean.setInvoiceMoney(value);
            			}
            		}
            		else{
            			builder
                        .append("第[" + currentNumber + "]错误:")
                        .append("开票金额不能为空")
                        .append("<br>");
                    
            			importError = true;
            		}
            		
            		// 发票号
            		if ( !StringTools.isNullOrNone(obj[2]))
            		{
            			bean.setInvoiceNum(obj[2].trim());
            		}
            		else{
            			builder
                        .append("第[" + currentNumber + "]错误:")
                        .append("发票号不能为空")
                        .append("<br>");

            			importError = true;
            		}
            		
            		// 发票类型
            		if ( !StringTools.isNullOrNone(obj[3]))
            		{
            			String name = obj[3].trim();
            			
            			// 特殊类型
            			if (name.equals("混合")){
            				bean.setInvoiceId("9999999999");
            			} else {
            				InvoiceBean invoice = invoiceDAO.findByUnique(name);
                			
                			if (null == invoice)
                			{
                				builder
                                .append("第[" + currentNumber + "]错误:")
                                .append("发票类型不存在")
                                .append("<br>");
                            
                    			importError = true;
                			}
                			else{
                				
                				if (invoice.getForward() == InvoiceConstant.INVOICE_FORWARD_IN)
                				{
                					builder
                                    .append("第[" + currentNumber + "]错误:")
                                    .append("发票类型不是销货发票")
                                    .append("<br>");
                                
                        			importError = true;
                				}else{
                					List<DutyVSInvoiceBean> list = dutyVSInvoiceDAO.queryEntityBeansByCondition("where dutytype = 4 and invoiceid=?", invoice.getId());
                					
                					if (ListTools.isEmptyOrNull(list)) {
                						builder
                                        .append("第[" + currentNumber + "]错误:")
                                        .append("发票类型不是永银文化纳税实体下的发票")
                                        .append("<br>");
                                    
                            			importError = true;
                					} else {
                						bean.setInvoiceId(invoice.getId());
                					}
                				}
                			}
            			}
            		}else
            		{
            			builder
                        .append("第[" + currentNumber + "]错误:")
                        .append("发票类型不能为空")
                        .append("<br>");
                    
            			importError = true;
            		}
            		
            		// 开票抬头
            		if ( !StringTools.isNullOrNone(obj[4]))
            		{
            			String name = obj[4].trim();
            			
            			bean.setInvoiceHead(name);
            		}else
            		{
            			builder
                        .append("第[" + currentNumber + "]错误:")
                        .append("开票抬头不能为空")
                        .append("<br>");
                    
            			importError = true;
            		}
            		
            		// 先判断地址类型  - 必填 [1:同销售单一致;2:新配送地址]
            		if ( !StringTools.isNullOrNone(obj[16])) {
            			if (!obj[16].trim().equals("同销售单一致") && !obj[16].trim().equals("新配送地址")) {
            				builder
                            .append("第[" + currentNumber + "]错误:")
                            .append("地址类型只能是 [同销售单一致]或[新配送地址]")
                            .append("<br>");
                        
                			importError = true;
            			} else {
            				if (obj[16].trim().equals("新配送地址")) {
                				bean.setAddrType(2);
                			}
                			
                			if (obj[16].trim().equals("同销售单一致")) {
                				bean.setAddrType(1);
                			}
            			}
            			
            		} else {
            			builder
                        .append("第[" + currentNumber + "]错误:")
                        .append("地址类型不能为空")
                        .append("<br>");
                    
            			importError = true;
            		}
            		
            		// 只有当是新配送地址时 才 需要校验 
            		if (bean.getAddrType() == 2) {
            			// 发货方式
                		if ( !StringTools.isNullOrNone(obj[10]))
                		{
                			boolean has = false;
                			
                			String shipping = obj[10].trim();
                			
                			for (int i = 0 ; i < OutImportConstant.shipping.length; i++)
                			{
                				if (OutImportConstant.shipping[i].equals(shipping))
                				{
                					has = true;
                					
                					bean.setShipping(OutImportConstant.ishipping[i]);
                					
                					break;
                				}
                			}
                			
                			if (!has)
                			{
                				builder
                	            .append("第[" + currentNumber + "]错误:")
                	            .append("发货方式不对,须为[自提,公司第三方快递,第三方货运,第三方快递+货运,空发]中之一")
                	            .append("<br>");
                				
                				importError = true;
                			} else {
                				if (bean.getShipping() == OutConstant.OUT_SHIPPING_NOTSHIPPING) {
                					builder
                    	            .append("第[" + currentNumber + "]错误:")
                    	            .append("发货方式不能为空发")
                    	            .append("<br>");
                    				
                    				importError = true;
                				}
                			}
                		}else
                		{
                			builder
                            .append("第[" + currentNumber + "]错误:")
                            .append("发货方式不能为空,须为[自提,公司第三方快递,第三方货运,第三方快递+货运,空发]中之一")
                            .append("<br>");
                			
                			importError = true;
                		}
                		
                		if (bean.getShipping() == 2 || bean.getShipping() == 4)
                		{
                			// 如果发货方式是快递或快递+货运 ,则快递须为必填
                			if ( !StringTools.isNullOrNone(obj[11]))
                			{
                				String transport1 = obj[11].trim();
                				
                				ExpressBean express = expressDAO.findByUnique(transport1);
                				
                				if (null == express)
                				{
                					builder
                		            .append("第[" + currentNumber + "]错误:")
                		            .append("快递方式"+ transport1 +"不存在")
                		            .append("<br>");
                					
                					importError = true;
                				}else{
                					bean.setTransport1(MathTools.parseInt(express.getId()));
                				}
                			}else
                			{
                				builder
                	            .append("第[" + currentNumber + "]错误:")
                	            .append("快递方式不能为空")
                	            .append("<br>");
                				
                				importError = true;
                			}
                			
                			// 快递支付方式也不能为空
                			if ( !StringTools.isNullOrNone(obj[12]))
                			{
                				String expressPay = obj[12].trim();
                				
                				boolean isexists = false;
                				
                				for (int i = 0; i < OutImportConstant.expressPay.length; i++)
                				{
                					if (expressPay.equals(OutImportConstant.expressPay[i]))
                					{
                						isexists = true;
                						
                						bean.setExpressPay(OutImportConstant.iexpressPay[i]);
                						
                						break;
                					}
                				}
                				
                				if (!isexists)
                				{
                					builder
                		            .append("第[" + currentNumber + "]错误:")
                		            .append("快递支付方式不存在,只能为[业务员支付,公司支付,客户支付]之一")
                		            .append("<br>");
                					
                					importError = true;
                				}
                			}else
                			{
                				builder
                	            .append("第[" + currentNumber + "]错误:")
                	            .append("快递支付方式不能为空,只能为[业务员支付,公司支付,客户支付]之一")
                	            .append("<br>");
                				
                				importError = true;
                			}
                		}
                		
                		if (bean.getShipping() == 3 || bean.getShipping() == 4)
                		{
                			// 如果发货方式是快递或快递+货运 ,则快递须为必填
                			if ( !StringTools.isNullOrNone(obj[13]))
                			{
                				String transport1 = obj[13].trim();
                				
                				ExpressBean express = expressDAO.findByUnique(transport1);
                				
                				if (null == express)
                				{
                					builder
                		            .append("第[" + currentNumber + "]错误:")
                		            .append("货运方式"+ transport1 +"不存在")
                		            .append("<br>");
                					
                					importError = true;
                				}else{
                					bean.setTransport2(MathTools.parseInt(express.getId()));
                				}
                			}else
                			{
                				builder
                	            .append("第[" + currentNumber + "]错误:")
                	            .append("货运方式不能为空")
                	            .append("<br>");
                				
                				importError = true;
                			}
                			
                			// 快递支付方式也不能为空
                			if ( !StringTools.isNullOrNone(obj[14]))
                			{
                				String expressPay = obj[14].trim();
                				
                				boolean isexists = false;
                				
                				for (int i = 0; i < OutImportConstant.expressPay.length; i++)
                				{
                					if (expressPay.equals(OutImportConstant.expressPay[i]))
                					{
                						isexists = true;
                						
                						bean.setTransportPay(OutImportConstant.iexpressPay[i]);
                						
                						break;
                					}
                				}
                				
                				if (!isexists)
                				{
                					builder
                		            .append("第[" + currentNumber + "]错误:")
                		            .append("货运支付方式不存在,只能为[业务员支付,公司支付,客户支付]之一")
                		            .append("<br>");
                					
                					importError = true;
                				}
                			}else
                			{
                				builder
                	            .append("第[" + currentNumber + "]错误:")
                	            .append("货运支付方式不能为空,只能为[业务员支付,公司支付,客户支付]之一")
                	            .append("<br>");
                				
                				importError = true;
                			}
                		}
                		
                		// 省	
                		if ( !StringTools.isNullOrNone(obj[5]))
                		{
                			String name = obj[5].trim();
                			
                			ProvinceBean province = provinceDAO.findByUnique(name);
                			
                			if (null != province)
                				bean.setProvinceId(province.getId());
                			else {
                				if (bean.getShipping() == 2 || bean.getShipping() == 3 || bean.getShipping() == 4) {
                					builder
                		            .append("第[" + currentNumber + "]错误:")
                		            .append("快递、货运时省不存在")
                		            .append("<br>");
                					
                					importError = true;
                				}
                			}
                		} else {
                			if (bean.getShipping() == 2 || bean.getShipping() == 3 || bean.getShipping() == 4) {
                				builder
                	            .append("第[" + currentNumber + "]错误:")
                	            .append("快递、货运时省不能为空")
                	            .append("<br>");
                				
                				importError = true;
                			}
                		}

                		// 市	
                		if ( !StringTools.isNullOrNone(obj[6]))
                		{
                			String name = obj[6].trim();
                			
                			CityBean city = cityDAO.findByUnique(name);
                			
                			if (null != city)
                				bean.setCityId(city.getId());
                			else {
                				if (bean.getShipping() == 2 || bean.getShipping() == 3 || bean.getShipping() == 4) {
                					builder
                		            .append("第[" + currentNumber + "]错误:")
                		            .append("快递、货运时市不存在")
                		            .append("<br>");
                					
                					importError = true;
                				}
                			}
                		} else {
                			if (bean.getShipping() == 2 || bean.getShipping() == 3 || bean.getShipping() == 4) {
                				builder
                	            .append("第[" + currentNumber + "]错误:")
                	            .append("快递、货运时市不能为空")
                	            .append("<br>");
                				
                				importError = true;
                			}
                		}

                		// 详细地址	
                		if ( !StringTools.isNullOrNone(obj[7]))
                		{
                			bean.setAddress(obj[7].trim());
                			
                			if (bean.getShipping() == 2 || bean.getShipping() == 3 || bean.getShipping() == 4) {
                				if (bean.getAddress().length() < 5) {
                					builder
                		            .append("第[" + currentNumber + "]错误:")
                		            .append("快递、货运时详细地址不能少于5个字")
                		            .append("<br>");
                					
                					importError = true;
                				}
                			}
                			
                		} else {
                			if (bean.getShipping() == 2 || bean.getShipping() == 3 || bean.getShipping() == 4) {
                				builder
                	            .append("第[" + currentNumber + "]错误:")
                	            .append("快递、货运时详细地址不能为空")
                	            .append("<br>");
                				
                				importError = true;
                			}
                		}
                		
                		// 收货人	
                		if ( !StringTools.isNullOrNone(obj[8]))
                		{
                			bean.setReceiver(obj[8].trim());
                			
                			if (bean.getShipping() == 2 || bean.getShipping() == 3 || bean.getShipping() == 4) {
                				if (bean.getReceiver().length() < 2) {
                					builder
                		            .append("第[" + currentNumber + "]错误:")
                		            .append("快递、货运时收货人不能少于2个字")
                		            .append("<br>");
                					
                					importError = true;
                				}
                			}
                		} else {
                			if (bean.getShipping() == 2 || bean.getShipping() == 3 || bean.getShipping() == 4) {
                				builder
                	            .append("第[" + currentNumber + "]错误:")
                	            .append("快递、货运时收货人不能为空")
                	            .append("<br>");
                				
                				importError = true;
                			}
                		}

                		// 收货人手机
                		if ( !StringTools.isNullOrNone(obj[9]))
                		{
                			bean.setMobile(obj[9].trim());
                			
                			if (bean.getShipping() == 2 || bean.getShipping() == 3 || bean.getShipping() == 4) {
                				if (bean.getMobile().length() < 11) {
                					builder
                		            .append("第[" + currentNumber + "]错误:")
                		            .append("快递、货运时收货人手机不能少于11位")
                		            .append("<br>");
                					
                					importError = true;
                				}
                			}
                		} else {
                			if (bean.getShipping() == 2 || bean.getShipping() == 3 || bean.getShipping() == 4) {
                				builder
                	            .append("第[" + currentNumber + "]错误:")
                	            .append("快递、货运时收货人手机不能为空")
                	            .append("<br>");
                				
                				importError = true;
                			}
                		}
            		}
            		
            		bean.setDescription(obj[15].trim());
            		bean.setInvoiceDate(TimeTools.now_short());
            		bean.setStafferName(user.getStafferName());
            		bean.setLogTime(TimeTools.now());
            		
                    importItemList.add(bean);
                    
                }
                else
                {
                    builder
                        .append("第[" + currentNumber + "]错误:")
                        .append("数据长度不足16格错误")
                        .append("<br>");
                    
                    importError = true;
                }
            }
        }catch (Exception e)
        {
            _logger.error(e, e);
            
            request.setAttribute(KeyConstant.ERROR_MESSAGE, e.toString());

            return mapping.findForward("importInvoiceins");
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
            
            request.setAttribute(KeyConstant.ERROR_MESSAGE, "导入出错:"+ builder.toString());

            return mapping.findForward("importInvoiceins");
        }
        
        String batchId = "";
        
        try
        {
        	invoiceinsManager.checkImportIns(importItemList, builder);
        	
        	if (!StringTools.isNullOrNone(builder.toString())) {
        		request.setAttribute(KeyConstant.ERROR_MESSAGE, "导入出错:"+ builder.toString());

                return mapping.findForward("importInvoiceins");
        	}
        	
        	batchId = invoiceinsManager.importInvoiceins(user, importItemList);
        	
        	request.setAttribute(KeyConstant.MESSAGE, "导入成功");
        }
        catch(MYException e)
        {
            request.setAttribute(KeyConstant.ERROR_MESSAGE, "导入出错:"+ e.getErrorContent());

            return mapping.findForward("importInvoiceins");
        }
        
        // 异步处理 - 只针对初始或失败的行项目
        List<InvoiceinsImportBean> list = invoiceinsImportDAO.queryEntityBeansByFK(batchId);
        
        if (!ListTools.isEmptyOrNull(list))
        {
        	invoiceinsManager.processAsyn(list);
        }
        
        return mapping.findForward("queryInvoiceinsImport");
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
    public ActionForward importInvoiceinsForCheck(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
	throws ServletException
	{
    	User user = Helper.getUser(request);
    	
        RequestDataStream rds = new RequestDataStream(request);
        
        boolean importError = false;
        
        List<InvoiceinsImportBean> importItemList = new ArrayList<InvoiceinsImportBean>(); 
        
        StringBuilder builder = new StringBuilder();
        
        try
        {
            rds.parser();
        }
        catch (Exception e1)
        {
            _logger.error(e1, e1);

            request.setAttribute(KeyConstant.ERROR_MESSAGE, "解析失败");

            return mapping.findForward("importInvoiceinsForCheck");
        }

        if ( !rds.haveStream())
        {
            request.setAttribute(KeyConstant.ERROR_MESSAGE, "解析失败");

            return mapping.findForward("importInvoiceinsForCheck");
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
                	InvoiceinsImportBean bean = new InvoiceinsImportBean();
                    
            		// 销售单
            		if ( !StringTools.isNullOrNone(obj[0]))
            		{
            			String value = obj[0].trim();
            			
                		bean.setOutId(value);
            		}
            		else
            		{
            			builder
                        .append("第[" + currentNumber + "]错误:")
                        .append("销售单不能为空")
                        .append("<br>");
                    
            			importError = true;
            		}
            		
            		// 开票金额
            		if ( !StringTools.isNullOrNone(obj[1]))
            		{
            			double value = MathTools.parseDouble(obj[1].trim());
            			
            			if (value <= 0) {
            				builder
                            .append("第[" + currentNumber + "]错误:")
                            .append("开票金额不能小于0")
                            .append("<br>");
                        
                			importError = true;
            			} else {
            				bean.setInvoiceMoney(value);
            			}
            		}
            		else{
            			builder
                        .append("第[" + currentNumber + "]错误:")
                        .append("开票金额不能为空")
                        .append("<br>");
                    
            			importError = true;
            		}
            		
            		// 发票类型
            		if ( !StringTools.isNullOrNone(obj[2]))
            		{
            			String name = obj[2].trim();
            			
            			InvoiceBean invoice = invoiceDAO.findByUnique(name);
            			
            			if (null == invoice)
            			{
            				builder
                            .append("第[" + currentNumber + "]错误:")
                            .append("发票类型不存在")
                            .append("<br>");
                        
                			importError = true;
            			}
            			else{
            				
            				if (invoice.getForward() == InvoiceConstant.INVOICE_FORWARD_IN)
            				{
            					builder
                                .append("第[" + currentNumber + "]错误:")
                                .append("发票类型不是销货发票")
                                .append("<br>");
                            
                    			importError = true;
            				}else{
            					List<DutyVSInvoiceBean> list = dutyVSInvoiceDAO.queryEntityBeansByCondition("where dutytype = 4 and invoiceid=?", invoice.getId());
            					
            					if (ListTools.isEmptyOrNull(list)) {
            						builder
                                    .append("第[" + currentNumber + "]错误:")
                                    .append("发票类型不是永银文化纳税实体下的发票")
                                    .append("<br>");
                                
                        			importError = true;
            					} else {
            						bean.setInvoiceId(invoice.getId());
            					}
            				}
            			}
            			
            		}else
            		{
            			builder
                        .append("第[" + currentNumber + "]错误:")
                        .append("发票类型不能为空")
                        .append("<br>");
                    
            			importError = true;
            		}
            		
                    importItemList.add(bean);
                    
                }
                else
                {
                    builder
                        .append("第[" + currentNumber + "]错误:")
                        .append("数据长度不足7格错误")
                        .append("<br>");
                    
                    importError = true;
                }
            }
        }catch (Exception e)
        {
            _logger.error(e, e);
            
            request.setAttribute(KeyConstant.ERROR_MESSAGE, e.toString());

            return mapping.findForward("importInvoiceinsForCheck");
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
            
            request.setAttribute(KeyConstant.ERROR_MESSAGE, "导入出错:"+ builder.toString());

            return mapping.findForward("importInvoiceinsForCheck");
        }
        
    	invoiceinsManager.checkImportIns(importItemList, builder);
    	
    	if (!StringTools.isNullOrNone(builder.toString())) {
    		request.setAttribute(KeyConstant.ERROR_MESSAGE, "导入出错:"+ builder.toString());

            return mapping.findForward("importInvoiceinsForCheck");
    	}
        
    	request.setAttribute(KeyConstant.MESSAGE, "校验成功，符合规范");
    	
        return mapping.findForward("importInvoiceinsForCheck");
	}
    
    private String[] fillObj(String[] obj)
    {
        String[] result = new String[40];

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
     * 导入式 批量修改发票号
     * importInvoice
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward batchUpdateInsNum(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
	throws ServletException
	{
    	User user = Helper.getUser(request);
    	
        RequestDataStream rds = new RequestDataStream(request);
        
        boolean importError = false;
        
        List<InvoiceinsImportBean> importItemList = new ArrayList<InvoiceinsImportBean>(); 
        
        StringBuilder builder = new StringBuilder();
        
        try
        {
            rds.parser();
        }
        catch (Exception e1)
        {
            _logger.error(e1, e1);

            request.setAttribute(KeyConstant.ERROR_MESSAGE, "解析失败");

            return mapping.findForward("batchUpdateInsNum");
        }

        if ( !rds.haveStream())
        {
            request.setAttribute(KeyConstant.ERROR_MESSAGE, "解析失败");

            return mapping.findForward("batchUpdateInsNum");
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
                	InvoiceinsImportBean bean = new InvoiceinsImportBean();
                    
            		// 发票ID
            		if ( !StringTools.isNullOrNone(obj[0]))
            		{
            			String value = obj[0].trim();
            			
                		bean.setId(value);
            		}
            		else
            		{
            			builder
                        .append("第[" + currentNumber + "]错误:")
                        .append("发票标识不能为空")
                        .append("<br>");
                    
            			importError = true;
            		}
            		
            		// 发票号
            		if ( !StringTools.isNullOrNone(obj[1]))
            		{
            			bean.setInvoiceNum(obj[1].trim());
            		}
            		else{
            			builder
                        .append("第[" + currentNumber + "]错误:")
                        .append("发票号不能为空")
                        .append("<br>");

            			importError = true;
            		}
            		
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
            _logger.error(e, e);
            
            request.setAttribute(KeyConstant.ERROR_MESSAGE, e.toString());

            return mapping.findForward("batchUpdateInsNum");
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
            
            request.setAttribute(KeyConstant.ERROR_MESSAGE, "导入出错:"+ builder.toString());

            return mapping.findForward("batchUpdateInsNum");
        }
        
        try
        {
        	invoiceinsManager.batchUpdateInsNum(user, importItemList);
        	
        	request.setAttribute(KeyConstant.MESSAGE, "导入处理成功");
        }
        catch(MYException e)
        {
            request.setAttribute(KeyConstant.ERROR_MESSAGE, "导入出错:"+ e.getErrorContent());
        }
        
        return mapping.findForward("batchUpdateInsNum");
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
    public ActionForward queryInvoiceinsImport(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
	throws ServletException
	{
        ConditionParse condtion = new ConditionParse();

        condtion.addWhereStr();

        String sbatchId = request.getParameter("sbatchId");
        
        if (!StringTools.isNullOrNone(sbatchId))
        {
        	condtion.addCondition("InvoiceinsImportBean.batchId", "=", sbatchId);
        }
        
        Map<String, String> initMap = initLogTime1(request, condtion);
        
        ActionTools.processJSONDataQueryCondition(QUERYINVOICEINSIMPORT, request,
				condtion, initMap);
        
        condtion.addCondition(" order by InvoiceinsImportBean.logTime desc");
        
        String jsonstr = ActionTools.queryVOByJSONAndToString(QUERYINVOICEINSIMPORT, request, condtion,
            this.invoiceinsImportDAO);

        return JSONTools.writeResponse(response, jsonstr);
    }
    
    /**
     * 查询批量导入执行日志
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward queryInsImportLog(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
	throws ServletException
	{
        ConditionParse condtion = new ConditionParse();

        condtion.addWhereStr();

        ActionTools.processJSONQueryCondition(QUERYINSIMPORTLOG, request, condtion);

        String batchId = request.getParameter("batchId");
        
        if (!StringTools.isNullOrNone(batchId))
        {
        	condtion.addCondition("InsImportLogBean.batchId", "=", batchId);
        }
        
        condtion.addCondition(" order by InsImportLogBean.logTime desc");
        
        String jsonstr = ActionTools.queryVOByJSONAndToString(QUERYINSIMPORTLOG, request, condtion,
            this.insImportLogDAO);

        return JSONTools.writeResponse(response, jsonstr);
    }
    
    /**
     * processInsImport
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward processInsImport(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
	throws ServletException
	{
    	String batchId = request.getParameter("batchId");
    	
    	AjaxResult ajax = new AjaxResult();
    	
    	try
		{
    		List<InvoiceinsImportBean> list = invoiceinsImportDAO.queryEntityBeansByFK(batchId);
    		
			invoiceinsManager.process(list);
			
			ajax.setSuccess("处理成功");
		}
		catch (MYException e)
		{
			_logger.warn(e, e);
			
			ajax.setError("失败:" + e.getErrorContent());
		}
    	
		return JSONTools.writeResponse(response, ajax);
	}
    
    private Map<String, String> initLogTime1(HttpServletRequest request,
			ConditionParse condtion) {
		Map<String, String> changeMap = new HashMap<String, String>();

		String alogTime = request.getParameter("beginDate");

		String blogTime = request.getParameter("beginDate1");

		if (StringTools.isNullOrNone(alogTime)
				&& StringTools.isNullOrNone(blogTime)) {
			changeMap.put("beginDate", TimeTools.now_short(-30));

			changeMap.put("beginDate1", TimeTools.now_short(1));

			condtion.addCondition("InvoiceinsImportBean.logTime", ">=",
					TimeTools.now_short(-30) + " 00:00:00");

			condtion.addCondition("InvoiceinsImportBean.logTime", "<=",
					TimeTools.now_short(1) + " 23:59:59");
		}

		return changeMap;
	}
    
    /**
     * queryImportInvoice
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward queryImportInvoice(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
	throws ServletException
	{
        ConditionParse condtion = new ConditionParse();

        condtion.addWhereStr();

        ActionTools.processJSONQueryCondition(QUERYIMPORTINVOIE, request, condtion);

        //User user = Helper.getUser(request);

        condtion.addCondition("order by InvoiceStorageBean.id desc");

        String jsonstr = ActionTools.queryVOByJSONAndToString(QUERYIMPORTINVOIE, request, condtion,
            this.invoiceStorageDAO);

        return JSONTools.writeResponse(response, jsonstr);
    
	}
    
    /**
     * querySelfInvoice
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward querySelfInvoice(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
	throws ServletException
	{
    	User user = Helper.getUser(request);
    	
    	List<InvoiceStorageVO> voList = invoiceStorageDAO.querySelfSumInvoice(user.getStafferId());
    	
    	request.setAttribute("resultList", voList);
    	
    	return mapping.findForward("querySelfInvoice");
	}
    
    /**
     * refConfirmInvoice
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward refConfirmInvoice(ActionMapping mapping, ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response)
	throws ServletException
	{
    	User user = Helper.getUser(request);
    	
    	double total = 0.0d;

    	String invoiceId = request.getParameter("invoiceId");
    	
    	String providerId = request.getParameter("providerId");
    	
    	// checkBox selected = true
		String[] fullIds = request.getParameterValues("fullId");
		
		double canUseMoney = MathTools.parseDouble(request.getParameter("total"));
		
		List<InvoiceBindOutBean> vsList = new ArrayList<InvoiceBindOutBean>();

		for (int i = 0; i < fullIds.length; i++) {
			
			InvoiceBindOutBean vs = new InvoiceBindOutBean();
			
			vs.setInvoiceStorageId(invoiceId);
			
			vs.setProviderId(providerId);

			String fullId [] = fullIds[i].split("~");
			
			vs.setFullId(fullId[0]);
			
			vs.setOuttype(MathTools.parseInt(fullId[1]));

			vs.setConfirmMoney(MathTools.parseDouble(fullId[2]));
			
			vs.setLogTime(TimeTools.now());
			
			vsList.add(vs);
			
			total += vs.getConfirmMoney();
			
			if (total >= canUseMoney) {
				break;
			}
		}

		try{
			financeFacade.refConfirmInvoice(user.getId(), vsList);
			
			request.setAttribute(KeyConstant.MESSAGE, "成功操作");
        }
        catch (MYException e)
        {
            _logger.warn(e, e);

            request.setAttribute(KeyConstant.ERROR_MESSAGE, "操作失败:" + e.getMessage());
        }

        CommonTools.removeParamers(request);

        return querySelfInvoice(mapping, form, request, response);
	}
    
    /**
     * queryInvoiceForConfirmStockPay
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward queryInvoiceForConfirmStockPay(ActionMapping mapping, ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response)
	throws ServletException
	{
    	User user = Helper.getUser(request);
    	
    	String stockPayApplyId = request.getParameter("id");

    	StockPayApplyBean spaBean = stockPayApplyDAO.find(stockPayApplyId);
    	
    	if (null == spaBean)
    	{
    		return ActionTools.toError("数据错误", "error", mapping, request);
    	}
    	
    	String invoiceId = spaBean.getInvoiceId();
    	
    	double needConfirm = spaBean.getRealMoneys() - spaBean.getHasConfirmInsMoney();
    	
    	// 检索符合的发票
    	ConditionParse con = new ConditionParse();
    	
    	con.addWhereStr();
    	
    	con.addCondition("InvoiceStorageBean.invoiceId", "=", invoiceId);
    	con.addCondition("InvoiceStorageBean.stafferId", "=", user.getStafferId());
    	con.addCondition(" and InvoiceStorageBean.moneys > InvoiceStorageBean.hasConfirmMoneys");
    	
    	List<InvoiceStorageBean> list = invoiceStorageDAO.queryEntityBeansByCondition(con);
    	
    	request.setAttribute("needConfirm", needConfirm);
    	
    	request.setAttribute("stockPayApplyId", stockPayApplyId);
    	
    	request.setAttribute("list", list);
    	
    	return mapping.findForward("refInvoice");
	}
    
    public ActionForward importInvoice(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
	throws ServletException
	{
        RequestDataStream rds = new RequestDataStream(request);
        
        boolean importError = false;
        
        List<InvoiceStorageBean> importItemList = new ArrayList<InvoiceStorageBean>(); 
        
        StringBuilder builder = new StringBuilder();
        
        try
        {
            rds.parser();
        }
        catch (Exception e1)
        {
            _logger.error(e1, e1);

            request.setAttribute(KeyConstant.ERROR_MESSAGE, "解析失败");

            return mapping.findForward("importInvoice");
        }

        if ( !rds.haveStream())
        {
            request.setAttribute(KeyConstant.ERROR_MESSAGE, "解析失败");

            return mapping.findForward("importInvoice");
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
                	InvoiceStorageBean bean = new InvoiceStorageBean();
                    
            		// 业务员
            		if ( !StringTools.isNullOrNone(obj[0]))
            		{
            			String value = obj[0].trim();
            			
            			StafferBean sb = stafferDAO.findyStafferByName(value);
            			
            			if (null == sb)
            			{
            				builder
                            .append("第[" + currentNumber + "]错误:")
                            .append("业务员不存在")
                            .append("<br>");
                        
                			importError = true;
            			}else{
            				bean.setStafferId(sb.getId());
                			bean.setStafferName(sb.getName());
            			}
            		}
            		else
            		{
            			builder
                        .append("第[" + currentNumber + "]错误:")
                        .append("业务员不能为空")
                        .append("<br>");
                    
            			importError = true;
        			
            		}
            		
            		// 发票抬头
            		if ( !StringTools.isNullOrNone(obj[1]))
            		{
            			bean.setInvoiceHead(obj[1].trim());
            		}
            		else{
            			builder
                        .append("第[" + currentNumber + "]错误:")
                        .append("发票抬头不能为空")
                        .append("<br>");
                    
            			importError = true;
            		}
            		
            		// 发票开具单位
            		if ( !StringTools.isNullOrNone(obj[2]))
            		{
            			bean.setInvoiceCompany(obj[2].trim());
            		}
            		else{
            			builder
                        .append("第[" + currentNumber + "]错误:")
                        .append("发票开具单位不能为空")
                        .append("<br>");
                    
            			importError = true;
            		}
            		
            		// 发票金额
            		if ( !StringTools.isNullOrNone(obj[3]))
            		{
            			double moneys = MathTools.parseDouble(obj[3].trim());
            			
            			if (moneys < 0)
            			{
            				builder
                            .append("第[" + currentNumber + "]错误:")
                            .append("发票金额不对")
                            .append("<br>");
                        
                			importError = true;
            			}else{
            				bean.setMoneys(moneys);	
            			}
            		}
            		else
            		{
            			builder
                        .append("第[" + currentNumber + "]错误:")
                        .append("发票金额不能为空")
                        .append("<br>");
                    
            			importError = true;
            		}
            		
            		// 不含税金额
            		if ( !StringTools.isNullOrNone(obj[4]))
            		{
            			double moneys = MathTools.parseDouble(obj[4].trim());
            			
            			if (moneys < 0)
            			{
            				builder
                            .append("第[" + currentNumber + "]错误:")
                            .append("不含税金额不对")
                            .append("<br>");
                        
                			importError = true;
            			}else{
            				bean.setNoTaxMoney(moneys);	
            			}
            		}
            		else
            		{
            			builder
                        .append("第[" + currentNumber + "]错误:")
                        .append("不含税金额不能为空")
                        .append("<br>");
                    
            			importError = true;
            		}
            		
            		// 税额
            		if ( !StringTools.isNullOrNone(obj[5]))
            		{
            			double moneys = MathTools.parseDouble(obj[5].trim());
            			
            			if (moneys < 0)
            			{
            				builder
                            .append("第[" + currentNumber + "]错误:")
                            .append("税额不对")
                            .append("<br>");
                        
                			importError = true;
            			}else{
            				bean.setTaxMoney(moneys);	
            			}
            		}
            		else
            		{
            			builder
                        .append("第[" + currentNumber + "]错误:")
                        .append("税额不能为空")
                        .append("<br>");
                    
            			importError = true;
            		}
            		
            		// 发票类型
            		if ( !StringTools.isNullOrNone(obj[6]))
            		{
            			String name = obj[6].trim();
            			
            			InvoiceBean invoice = invoiceDAO.findByUnique(name);
            			
            			if (null == invoice)
            			{
            				builder
                            .append("第[" + currentNumber + "]错误:")
                            .append("发票类型不存在")
                            .append("<br>");
                        
                			importError = true;
            			}
            			else{
            				
            				if (invoice.getForward() == InvoiceConstant.INVOICE_FORWARD_OUT)
            				{
            					builder
                                .append("第[" + currentNumber + "]错误:")
                                .append("发票类型不是进货发票")
                                .append("<br>");
                            
                    			importError = true;
            				}else{
            					bean.setInvoiceId(invoice.getId());		
            				}
            			}
            			
            		}else
            		{
            			builder
                        .append("第[" + currentNumber + "]错误:")
                        .append("发票类型不能为空")
                        .append("<br>");
                    
            			importError = true;
            		}
            		
            		// 发票号码
            		if ( !StringTools.isNullOrNone(obj[7]))
            		{
            			bean.setInvoiceNumber(obj[7].trim());
            		}else
            		{
            			builder
                        .append("第[" + currentNumber + "]错误:")
                        .append("发票号码不能为空")
                        .append("<br>");
                    
            			importError = true;
            		}
            		
            		// 开票日期
            		if ( !StringTools.isNullOrNone(obj[8]))
            		{
            			String eL = "[0-9]{4}-[0-9]{2}-[0-9]{2}";
            			Pattern p = Pattern.compile(eL);
            			Matcher m = p.matcher(obj[8].trim());
            			boolean dateFlag = m.matches();
            			if (!dateFlag) {
            				builder
                            .append("第[" + currentNumber + "]错误:")
                            .append("开票日期格式错误，如 2000-01-01")
                            .append("<br>");
                        
                			importError = true;
            			}
            			
            			bean.setInvoiceDate(obj[8].trim());
            		}else
            		{
            			builder
                        .append("第[" + currentNumber + "]错误:")
                        .append("开票日期不能为空")
                        .append("<br>");
                    
            			importError = true;
            		}
            		
            		// 供应商
            		if ( !StringTools.isNullOrNone(obj[9]))
            		{
            			String name = obj[9].trim();
            			
            			List<UnitViewBean> unitList = unitViewDAO.queryEntityBeansByCondition("where name=? and type = 1", name);
            			
            			if (ListTools.isEmptyOrNull(unitList))
            			{
            				builder
                            .append("第[" + currentNumber + "]错误:")
                            .append("供应商不存在")
                            .append("<br>");
                        
                			importError = true;
            			}else{
            				if (unitList.get(0).getType() != 1)
            				{
            					builder
                                .append("第[" + currentNumber + "]错误:")
                                .append("不是供应商")
                                .append("<br>");
                            
                    			importError = true;
            				}else{
            					bean.setProviderId(unitList.get(0).getId());
                				bean.setProviderName(unitList.get(0).getName());            					
            				}
            			}
            			
            		}else
            		{
            			builder
                        .append("第[" + currentNumber + "]错误:")
                        .append("供应商不能为空")
                        .append("<br>");
                    
            			importError = true;
            		}
            		
                    importItemList.add(bean);
                    
                }
                else
                {
                    builder
                        .append("第[" + currentNumber + "]错误:")
                        .append("数据长度不足7格错误")
                        .append("<br>");
                    
                    importError = true;
                }
            }
        }catch (Exception e)
        {
            _logger.error(e, e);
            
            request.setAttribute(KeyConstant.ERROR_MESSAGE, e.toString());

            return mapping.findForward("importInvoice");
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
            
            request.setAttribute(KeyConstant.ERROR_MESSAGE, "导入出错:"+ builder.toString());

            return mapping.findForward("importInvoice");
        }
        
        try
        {
        	User user = Helper.getUser(request);
        	
        	invoiceinsManager.importInvoice(user, importItemList);
        	
        	request.setAttribute(KeyConstant.MESSAGE, "导入成功");
        }
        catch(MYException e)
        {
            request.setAttribute(KeyConstant.ERROR_MESSAGE, "导入出错:"+ e.getErrorContent());

            return mapping.findForward("importInvoice");
        }
        
        return mapping.findForward("importInvoice");
	}
    
    /**
     * @return the financeFacade
     */
    public FinanceFacade getFinanceFacade()
    {
        return financeFacade;
    }

    /**
     * @param financeFacade
     *            the financeFacade to set
     */
    public void setFinanceFacade(FinanceFacade financeFacade)
    {
        this.financeFacade = financeFacade;
    }

    /**
     * @return the invoiceinsDAO
     */
    public InvoiceinsDAO getInvoiceinsDAO()
    {
        return invoiceinsDAO;
    }

    /**
     * @param invoiceinsDAO
     *            the invoiceinsDAO to set
     */
    public void setInvoiceinsDAO(InvoiceinsDAO invoiceinsDAO)
    {
        this.invoiceinsDAO = invoiceinsDAO;
    }

    /**
     * @return the invoiceinsItemDAO
     */
    public InvoiceinsItemDAO getInvoiceinsItemDAO()
    {
        return invoiceinsItemDAO;
    }

    /**
     * @param invoiceinsItemDAO
     *            the invoiceinsItemDAO to set
     */
    public void setInvoiceinsItemDAO(InvoiceinsItemDAO invoiceinsItemDAO)
    {
        this.invoiceinsItemDAO = invoiceinsItemDAO;
    }

    /**
     * @return the insVSOutDAO
     */
    public InsVSOutDAO getInsVSOutDAO()
    {
        return insVSOutDAO;
    }

    /**
     * @param insVSOutDAO
     *            the insVSOutDAO to set
     */
    public void setInsVSOutDAO(InsVSOutDAO insVSOutDAO)
    {
        this.insVSOutDAO = insVSOutDAO;
    }

    /**
     * @return the invoiceinsManager
     */
    public InvoiceinsManager getInvoiceinsManager()
    {
        return invoiceinsManager;
    }

    /**
     * @param invoiceinsManager
     *            the invoiceinsManager to set
     */
    public void setInvoiceinsManager(InvoiceinsManager invoiceinsManager)
    {
        this.invoiceinsManager = invoiceinsManager;
    }

    /**
     * @return the dutyDAO
     */
    public DutyDAO getDutyDAO()
    {
        return dutyDAO;
    }

    /**
     * @param dutyDAO
     *            the dutyDAO to set
     */
    public void setDutyDAO(DutyDAO dutyDAO)
    {
        this.dutyDAO = dutyDAO;
    }

    /**
     * @return the invoiceDAO
     */
    public InvoiceDAO getInvoiceDAO()
    {
        return invoiceDAO;
    }

    /**
     * @param invoiceDAO
     *            the invoiceDAO to set
     */
    public void setInvoiceDAO(InvoiceDAO invoiceDAO)
    {
        this.invoiceDAO = invoiceDAO;
    }

    /**
     * @return the showDAO
     */
    public ShowDAO getShowDAO()
    {
        return showDAO;
    }

    /**
     * @param showDAO
     *            the showDAO to set
     */
    public void setShowDAO(ShowDAO showDAO)
    {
        this.showDAO = showDAO;
    }

    /**
     * @return the outDAO
     */
    public OutDAO getOutDAO()
    {
        return outDAO;
    }

    /**
     * @param outDAO
     *            the outDAO to set
     */
    public void setOutDAO(OutDAO outDAO)
    {
        this.outDAO = outDAO;
    }

    /**
     * @return the outBalanceDAO
     */
    public OutBalanceDAO getOutBalanceDAO()
    {
        return outBalanceDAO;
    }

    /**
     * @param outBalanceDAO
     *            the outBalanceDAO to set
     */
    public void setOutBalanceDAO(OutBalanceDAO outBalanceDAO)
    {
        this.outBalanceDAO = outBalanceDAO;
    }

    /**
     * @return the dutyVSInvoiceDAO
     */
    public DutyVSInvoiceDAO getDutyVSInvoiceDAO()
    {
        return dutyVSInvoiceDAO;
    }

    /**
     * @param dutyVSInvoiceDAO
     *            the dutyVSInvoiceDAO to set
     */
    public void setDutyVSInvoiceDAO(DutyVSInvoiceDAO dutyVSInvoiceDAO)
    {
        this.dutyVSInvoiceDAO = dutyVSInvoiceDAO;
    }

    /**
     * @return the stafferManager
     */
    public StafferManager getStafferManager()
    {
        return stafferManager;
    }

    /**
     * @param stafferManager
     *            the stafferManager to set
     */
    public void setStafferManager(StafferManager stafferManager)
    {
        this.stafferManager = stafferManager;
    }

    /**
     * @return the baseDAO
     */
    public BaseDAO getBaseDAO()
    {
        return baseDAO;
    }

    /**
     * @param baseDAO
     *            the baseDAO to set
     */
    public void setBaseDAO(BaseDAO baseDAO)
    {
        this.baseDAO = baseDAO;
    }

    /**
     * @return the baseBalanceDAO
     */
    public BaseBalanceDAO getBaseBalanceDAO()
    {
        return baseBalanceDAO;
    }

    /**
     * @param baseBalanceDAO
     *            the baseBalanceDAO to set
     */
    public void setBaseBalanceDAO(BaseBalanceDAO baseBalanceDAO)
    {
        this.baseBalanceDAO = baseBalanceDAO;
    }

    /**
     * @return the flowLogDAO
     */
    public FlowLogDAO getFlowLogDAO()
    {
        return flowLogDAO;
    }

    /**
     * @param flowLogDAO
     *            the flowLogDAO to set
     */
    public void setFlowLogDAO(FlowLogDAO flowLogDAO)
    {
        this.flowLogDAO = flowLogDAO;
    }
    
    /**
     * 邮件驳回发送
     * @param fullId
     * @param user
     * @param reason
     * @param out
     * @param subject
     */
    private void sendOutRejectMail(String fullId, User user, String reason, InvoiceinsVO vo,String subject) 
    {
        StafferBean rejectorBean = stafferDAO.find(user.getStafferId());        
        
        StafferBean approverBean = stafferDAO.find(vo.getStafferId());
        
        if (null != approverBean)
        {
            StringBuffer sb = new StringBuffer();
            
            sb.append("系统发送>>>")
            .append("\r\n").append("单号:"+ fullId).append(",")
            .append("\r\n").append("客户姓名:"+ vo.getCustomerName()).append(",")
            .append("\r\n").append("总金额:"+ vo.getMoneys()).append(",")
            .append("\r\n").append("审批人:"+ rejectorBean.getName()).append(",")
            .append("\r\n").append("审批结果:驳回").append(",")
            .append("\r\n").append("审批意见:" + reason).append(",")
            .append("\r\n").append("审批人电话:"+ rejectorBean.getHandphone());
            
            String message = sb.toString();
            
            String to = approverBean.getNation();
            
            _logger.info(message);
            
            commonMailManager.sendMail(to, subject, message);
        }
    }

	public CommonMailManager getCommonMailManager() {
		return commonMailManager;
	}

	public void setCommonMailManager(CommonMailManager commonMailManager) {
		this.commonMailManager = commonMailManager;
	}

	public StafferDAO getStafferDAO() {
		return stafferDAO;
	}

	public void setStafferDAO(StafferDAO stafferDAO) {
		this.stafferDAO = stafferDAO;
	}

	/**
	 * @return the financeDAO
	 */
	public FinanceDAO getFinanceDAO()
	{
		return financeDAO;
	}

	/**
	 * @param financeDAO the financeDAO to set
	 */
	public void setFinanceDAO(FinanceDAO financeDAO)
	{
		this.financeDAO = financeDAO;
	}

	/**
	 * @return the invoiceStorageDAO
	 */
	public InvoiceStorageDAO getInvoiceStorageDAO()
	{
		return invoiceStorageDAO;
	}

	/**
	 * @param invoiceStorageDAO the invoiceStorageDAO to set
	 */
	public void setInvoiceStorageDAO(InvoiceStorageDAO invoiceStorageDAO)
	{
		this.invoiceStorageDAO = invoiceStorageDAO;
	}

	/**
	 * @return the stockPayApplyDAO
	 */
	public StockPayApplyDAO getStockPayApplyDAO()
	{
		return stockPayApplyDAO;
	}

	/**
	 * @param stockPayApplyDAO the stockPayApplyDAO to set
	 */
	public void setStockPayApplyDAO(StockPayApplyDAO stockPayApplyDAO)
	{
		this.stockPayApplyDAO = stockPayApplyDAO;
	}

	/**
	 * @return the insVSInvoiceNumDAO
	 */
	public InsVSInvoiceNumDAO getInsVSInvoiceNumDAO()
	{
		return insVSInvoiceNumDAO;
	}

	/**
	 * @param insVSInvoiceNumDAO the insVSInvoiceNumDAO to set
	 */
	public void setInsVSInvoiceNumDAO(InsVSInvoiceNumDAO insVSInvoiceNumDAO)
	{
		this.insVSInvoiceNumDAO = insVSInvoiceNumDAO;
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

	/**
	 * @return the unitViewDAO
	 */
	public UnitViewDAO getUnitViewDAO()
	{
		return unitViewDAO;
	}

	/**
	 * @param unitViewDAO the unitViewDAO to set
	 */
	public void setUnitViewDAO(UnitViewDAO unitViewDAO)
	{
		this.unitViewDAO = unitViewDAO;
	}

	/**
	 * @return the invoiceinsImportDAO
	 */
	public InvoiceinsImportDAO getInvoiceinsImportDAO()
	{
		return invoiceinsImportDAO;
	}

	/**
	 * @param invoiceinsImportDAO the invoiceinsImportDAO to set
	 */
	public void setInvoiceinsImportDAO(InvoiceinsImportDAO invoiceinsImportDAO)
	{
		this.invoiceinsImportDAO = invoiceinsImportDAO;
	}

	/**
	 * @return the insImportLogDAO
	 */
	public InsImportLogDAO getInsImportLogDAO()
	{
		return insImportLogDAO;
	}

	/**
	 * @param insImportLogDAO the insImportLogDAO to set
	 */
	public void setInsImportLogDAO(InsImportLogDAO insImportLogDAO)
	{
		this.insImportLogDAO = insImportLogDAO;
	}

	/**
	 * @return the expressDAO
	 */
	public ExpressDAO getExpressDAO() {
		return expressDAO;
	}

	/**
	 * @param expressDAO the expressDAO to set
	 */
	public void setExpressDAO(ExpressDAO expressDAO) {
		this.expressDAO = expressDAO;
	}

	public DistributionDAO getDistributionDAO() {
		return distributionDAO;
	}

	public void setDistributionDAO(DistributionDAO distributionDAO) {
		this.distributionDAO = distributionDAO;
	}

	public ProvinceDAO getProvinceDAO() {
		return provinceDAO;
	}

	public void setProvinceDAO(ProvinceDAO provinceDAO) {
		this.provinceDAO = provinceDAO;
	}

	public CityDAO getCityDAO() {
		return cityDAO;
	}

	public void setCityDAO(CityDAO cityDAO) {
		this.cityDAO = cityDAO;
	}
}
