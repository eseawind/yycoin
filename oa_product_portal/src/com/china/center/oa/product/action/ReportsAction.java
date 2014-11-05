/**
 * File Name: ReportsAction.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * Creater: zhuAchen<br>
 * CreateTime: 2008-2-18<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.product.action;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.NumberFormat;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.china.center.actionhelper.common.JSONTools;
import com.china.center.actionhelper.json.AjaxResult;
import com.china.center.common.taglib.DefinedCommon;
import com.china.center.jdbc.util.ConditionParse;
import com.china.center.oa.product.bean.DepotBean;
import com.china.center.oa.product.bean.DepotpartBean;
import com.china.center.oa.product.bean.ProductBean;
import com.china.center.oa.product.bean.StorageBean;
import com.china.center.oa.product.bean.StorageLogBean;
import com.china.center.oa.product.dao.DepotDAO;
import com.china.center.oa.product.dao.DepotpartDAO;
import com.china.center.oa.product.dao.ProductDAO;
import com.china.center.oa.product.dao.StorageDAO;
import com.china.center.oa.product.dao.StorageLogDAO;
import com.china.center.oa.product.dao.StorageRelationDAO;
import com.china.center.oa.product.vo.StorageRelationVO;
import com.china.center.oa.product.wrap.StatProductBean;
import com.china.center.oa.publics.bean.PrincipalshipBean;
import com.china.center.oa.publics.dao.PrincipalshipDAO;
import com.china.center.tools.BeanUtil;
import com.china.center.tools.CommonTools;
import com.china.center.tools.ListTools;
import com.china.center.tools.StringTools;
import com.china.center.tools.TimeTools;

/**
 * 报表
 * 
 * @author ZHUZHU
 * @version 2008-2-18
 * @see
 * @since
 */
public class ReportsAction extends DispatchAction {
    private StorageDAO         storageDAO         = null;

    private StorageLogDAO      storageLogDAO      = null;

    private ProductDAO         productDAO         = null;

    private StorageRelationDAO storageRelationDAO = null;

    private DepotpartDAO       depotpartDAO       = null;

    private DepotDAO           depotDAO           = null;

    private PrincipalshipDAO   principalshipDAO   = null;

    /**
     * default constructor
     */
    public ReportsAction() {
    }

    /**
     * 根据仓区ID动态查询储位,aJax方法
     * 
     * @param mapping
     * @param form
     * @param request
     * @param reponse
     * @return
     * @throws ServletException
     */
    public ActionForward queryStorageByDepotpartId(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse reponse) throws ServletException {

        String depotpartId = request.getParameter("depotpartId");

        AjaxResult ajax = new AjaxResult();
        List<StorageBean> storageLst = storageDAO.queryEntityBeansByFK(depotpartId);

        ajax.setSuccess(storageLst);

        return JSONTools.writeResponse(reponse, ajax);
    }

    /**
     * 根据仓库ID动态查询仓区,aJax方法
     * 
     * @param mapping
     * @param form
     * @param request
     * @param reponse
     * @return
     * @throws ServletException
     */
    public ActionForward queryDepotpartByDepotId(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse reponse) throws ServletException {

        String depotId = request.getParameter("depotId");

        AjaxResult ajax = new AjaxResult();
        List<DepotpartBean> depotpartLst = depotpartDAO.queryOkDepotpartInDepot(depotId);

        ajax.setSuccess(depotpartLst);

        return JSONTools.writeResponse(reponse, ajax);
    }

    /**
     * 提供盘点报表，处理当天的异动的产品数量。<br>
     * 提供报表时需要分仓位提供报表，报表结构：储位、品名、原始数量、异动数量，<br>
     * 当前数量（查询条件时间，仓区，盘点属性）
     * 
     * @param mapping
     * @param form
     * @param request
     * @param reponse
     * @return
     * @throws ServletException
     */
    public ActionForward statReports(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse reponse) throws ServletException {
        CommonTools.saveParamers(request);

        ConditionParse condition = new ConditionParse();

        String flag = request.getParameter("flag");
        //修改后，要根据查询条件导出盘点表
        if (flag.equals("exportAll"))
            return exportAll(mapping,form,request,reponse);
        
        setCondition(request, condition);

        // 获取指定时间内的仓区的异动
        List<StorageLogBean> logs = storageLogDAO.queryStorageLogByCondition(condition);

        List<StatProductBean> statList = statProduct(logs, request);

        request.getSession().setAttribute("statList", statList);

        // 仓库
        List<DepotBean> depotList = depotDAO.listEntityBeans();

        request.setAttribute("depotList", depotList);

        /*
         * 页面采用ajax 联动 // 仓区 List<DepotpartBean> list =
         * depotpartDAO.listEntityBeans();
         * 
         * request.setAttribute("depotpartList", list);
         * 
         * // 仓储 List<StorageBean> storageList = storageDAO.listEntityBeans();
         * 
         * request.setAttribute("storageList", storageList);
         */

        return mapping.findForward("queryReports");
    }

    /**
     * 查询仓区的
     * 
     * @param mapping
     * @param form
     * @param request
     * @param reponse
     * @return
     * @throws ServletException
     */
    public ActionForward queryForStatReports(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse reponse) throws ServletException {
        // 仓库
        List<DepotBean> depotList = depotDAO.listEntityBeans();

        request.setAttribute("depotList", depotList);

        /*
         * 页面采用ajax 联动 // 仓区 List<DepotpartBean> list =
         * depotpartDAO.listEntityBeans();
         * 
         * request.setAttribute("depotpartList", list);
         * 
         * // 仓储 List<StorageBean> storageList = storageDAO.listEntityBeans();
         * 
         * request.setAttribute("storageList", storageList);
         */

        request.getSession().removeAttribute("statList");

        return mapping.findForward("queryReports");
    }

    /**
     * listStorageLog
     * 
     * @param mapping
     * @param form
     * @param request
     * @param reponse
     * @return
     * @throws ServletException
     */
    public ActionForward listStorageLog(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse reponse) throws ServletException {
        Map<String, List<StorageLogBean>> map = (Map<String, List<StorageLogBean>>) request
                .getSession().getAttribute("logsMap");

        String productId = request.getParameter("productId");

        List<StorageLogBean> lits = map.get(productId);

        List<StatProductBean> statList = new ArrayList<StatProductBean>();

        for (StorageLogBean storageLogBean : lits) {
            StatProductBean bean = new StatProductBean();

            BeanUtil.copyProperties(bean, storageLogBean);

            getProductReprot(bean);

            statList.add(bean);
        }

        request.setAttribute("listStorageLog", statList);

        return mapping.findForward("listStorageLog");
    }

    /**
     * listStorageLog2
     * 
     * @param mapping
     * @param form
     * @param request
     * @param reponse
     * @return
     * @throws ServletException
     */
    public ActionForward listStorageLog2(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse reponse) throws ServletException {
        ConditionParse condition = new ConditionParse();

        condition.addCondition("and t1.productId = t2.id");

        String productId = request.getParameter("productId");

        String depotpartId = request.getParameter("depotpartId");

        condition.addCondition("t1.productId", "=", productId);

        condition.addCondition("t1.depotpartId", "=", depotpartId);

        condition.addCondition("order by t1.logTime");

        // 获取指定时间内的仓区的异动
        List<StorageLogBean> lits = storageLogDAO.queryStorageLogByCondition(condition);

        List<StatProductBean> statList = new ArrayList<StatProductBean>();

        for (StorageLogBean storageLogBean : lits) {
            StatProductBean bean = new StatProductBean();

            BeanUtil.copyProperties(bean, storageLogBean);

            getProductReprot(bean);

            statList.add(bean);
        }

        request.setAttribute("listStorageLog", statList);

        return mapping.findForward("listStorageLog");
    }

    public ActionForward export(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse reponse) throws ServletException {

        OutputStream out = null;

        List<StatProductBean> statList = (List<StatProductBean>) request.getSession().getAttribute(
                "statList");

        if (ListTools.isEmptyOrNull(statList))
        {
        	return null;
        }
        
        String filenName = TimeTools.now("MMddHHmmss") + ".xls";

        reponse.setContentType("application/x-dbf");

        reponse.setHeader("Content-Disposition", "attachment; filename=" + filenName);

        WritableWorkbook wwb = null;
        WritableSheet ws = null;

        try {
            out = reponse.getOutputStream();

            wwb = Workbook.createWorkbook(out);
            ws = wwb.createSheet("sheel1", 0);
            int i = 0, j = 0;

            WritableFont font = new WritableFont(WritableFont.ARIAL, 10, WritableFont.BOLD, false,
                    jxl.format.UnderlineStyle.NO_UNDERLINE, jxl.format.Colour.BLUE);

            WritableCellFormat format = new WritableCellFormat(font);

            ws.addCell(new Label(j++, i, "地点", format));
            ws.addCell(new Label(j++, i, "事业部", format));
            ws.addCell(new Label(j++, i, "仓库", format)); // add
            ws.addCell(new Label(j++, i, "仓区", format));
            ws.addCell(new Label(j++, i, "储位", format)); // add
            // ws.addCell(new Label(j++, i, "属性", format));
            ws.addCell(new Label(j++, i, "产品", format));
            ws.addCell(new Label(j++, i, "原始数量", format));
            ws.addCell(new Label(j++, i, "异动数量", format));
            ws.addCell(new Label(j++, i, "当前数量", format));
            // ws.addCell(new Label(j++, i, "具体描述", format));

            // NumberFormat nf = new NumberFormat("###,##0.00");
            NumberFormat nf = new NumberFormat("###,##0");

            jxl.write.WritableCellFormat wcfN = new jxl.write.WritableCellFormat(nf);

            for (StatProductBean statProductBean : statList) {
                j = 0;
                i++;

                // DepotpartBean depotpartBean =
                // depotpartDAO.find(statProductBean.getDepotpartId());
                //
                // String typeName = "";
                //
                // if (depotpartBean != null) {
                // typeName = DefinedCommon.getValue("depotpartType",
                // depotpartBean.getType());
                // }

                ws.addCell(new Label(j++, i, statProductBean.getIndustryName2()));
                ws.addCell(new Label(j++, i, statProductBean.getIndustryName()));
                ws.addCell(new Label(j++, i, statProductBean.getLocationName()));
                ws.addCell(new Label(j++, i, statProductBean.getDepotpartName()));
                ws.addCell(new Label(j++, i, statProductBean.getStorageName()));
                // ws.addCell(new Label(j++, i, typeName));
                ws.addCell(new Label(j++, i, statProductBean.getProductName()));
                ws.addCell(new jxl.write.Number(j++, i, statProductBean.getPreAmount(), wcfN));
                ws.addCell(new jxl.write.Number(j++, i, statProductBean.getChangeAmount(), wcfN));
                ws.addCell(new jxl.write.Number(j++, i, statProductBean.getCurrentAmount(), wcfN));

                /*
                 * ConditionParse condition = new ConditionParse();
                 * 
                 * condition.addWhereStr();
                 * 
                 * condition.addCondition("StorageRelationBean.depotpartId",
                 * "=", statProductBean.getDepotpartId());
                 * condition.addCondition("StorageRelationBean.productId", "=",
                 * statProductBean.getProductId());
                 * 
                 * // 查询产品在仓区的分布 List<StorageRelationVO> voList =
                 * storageRelationDAO .queryEntityVOsByCondition(condition);
                 * 
                 * StringBuffer sb = new StringBuffer();
                 * 
                 * for (StorageRelationVO each : voList) { sb.append("储位[" +
                 * each.getStorageName() + "]:" + each.getAmount()).append(";");
                 * }
                 * 
                 * ws.addCell(new Label(j++, i, sb.toString()));
                 */
            }
        } catch (Exception e) 
        {
            e.printStackTrace();

            return null;
            
        } finally 
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

    public ActionForward exportAll(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse reponse) throws ServletException {

        OutputStream out = null;

        ConditionParse condition = new ConditionParse();

        setCondition2(request, condition);
        
        List<StorageRelationVO> statList = storageRelationDAO.queryStorageRelationByCondition(
                condition, false);

        String filenName = TimeTools.now("MMddHHmmss") + "_ALL.xls";

        reponse.setContentType("application/x-dbf");

        reponse.setHeader("Content-Disposition", "attachment; filename=" + filenName);

        WritableWorkbook wwb = null;

        WritableSheet ws = null;

        try {
            out = reponse.getOutputStream();

            wwb = Workbook.createWorkbook(out);
            ws = wwb.createSheet(TimeTools.now("MMddHHmmss") + "全部盘点", 0);
            int i = 0, j = 0;

            WritableFont font = new WritableFont(WritableFont.ARIAL, 10, WritableFont.BOLD, false,
                    jxl.format.UnderlineStyle.NO_UNDERLINE, jxl.format.Colour.BLUE);

            NumberFormat nf = new NumberFormat("###,##0");

            jxl.write.WritableCellFormat wcfN = new jxl.write.WritableCellFormat(nf);
            
            WritableCellFormat format = new WritableCellFormat(font);

            ws.addCell(new Label(j++, i, "类型", format));
            ws.addCell(new Label(j++, i, "分类", format));
            ws.addCell(new Label(j++, i, "地点", format));
            ws.addCell(new Label(j++, i, "事业部", format));
            ws.addCell(new Label(j++, i, "仓库", format));
            ws.addCell(new Label(j++, i, "仓区", format));
            ws.addCell(new Label(j++, i, "储位", format));
            ws.addCell(new Label(j++, i, "产品", format));

            ws.addCell(new Label(j++, i, "产品编码", format));
            ws.addCell(new Label(j++, i, "数量", format));

            for (StorageRelationVO statProductBean : statList) {
                j = 0;
                i++;

                String productTypeName = "";
                String managerTypeName = "";

                // 获取产品类型与管理类型 add by fang 2012.5.9
                productTypeName = DefinedCommon.getValue("productType",
                        statProductBean.getProductType());
                managerTypeName = DefinedCommon.getValue("pubManagerType",
                        statProductBean.getProductMtype());

                ws.addCell(new Label(j++, i, productTypeName));
                ws.addCell(new Label(j++, i, managerTypeName));
                ws.addCell(new Label(j++, i, statProductBean.getIndustryName2()));
                ws.addCell(new Label(j++, i, statProductBean.getIndustryName()));
                ws.addCell(new Label(j++, i, statProductBean.getLocationName()));
                ws.addCell(new Label(j++, i, statProductBean.getDepotpartName()));
                ws.addCell(new Label(j++, i, statProductBean.getStorageName()));
                ws.addCell(new Label(j++, i, statProductBean.getProductName()));

                ws.addCell(new Label(j++, i, statProductBean.getProductCode()));
                ws.addCell(new jxl.write.Number(j++, i, statProductBean.getAmount(), wcfN));
            }

            wwb.write();

        } catch (Exception e) {
            return null;
        } finally {
            if (wwb != null) {
                try {
                    wwb.close();
                } catch (Exception e1) {
                }
            }
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e1) {
                }
            }
        }

        return null;
    }

    /**
     * 统计
     * 
     * @param logs
     */
    private List<StatProductBean> statProduct(List<StorageLogBean> logs, HttpServletRequest request) {
        Map<String, List<StorageLogBean>> map = new HashMap<String, List<StorageLogBean>>();

        // 把分类统计的产品放到map里面[仓区+product:Logs]
        for (StorageLogBean storageLogBean : logs) {
            String key = storageLogBean.getProductId() + ";" + storageLogBean.getDepotpartId();

            if (!map.containsKey(key)) {
                map.put(key, new ArrayList<StorageLogBean>());
            }

            map.get(key).add(storageLogBean);
        }

        // 放入session方便查询
        request.getSession().setAttribute("logsMap", map);

        // 统计
        return statInner(map);
    }

    /**
     * 统计开始
     * 
     * @param map
     * @return
     */
    private List<StatProductBean> statInner(Map<String, List<StorageLogBean>> map) {
        List<StatProductBean> statList = new ArrayList<StatProductBean>();

        List<StorageLogBean> temp = null;

        // 统计每个产品
        for (Map.Entry<String, List<StorageLogBean>> entry : map.entrySet()) {
            StatProductBean bean = new StatProductBean();

            temp = entry.getValue();

            boolean frist = true;
            int init = 0, change = 0;
            for (StorageLogBean log : temp) {
                if (frist) {
                    init = log.getPreAmount();

                    BeanUtil.copyProperties(bean, log);

                    frist = false;
                }

                change += log.getChangeAmount();
            }

            bean.setPreAmount(init);

            bean.setChangeAmount(change);

            // 获得当前的数量
            int sum = storageRelationDAO.sumProductInDepotpartId(bean.getProductId(),
                    bean.getDepotpartId());

            bean.setCurrentAmount(sum);

            getProductReprot(bean);

            statList.add(bean);
        }

        return statList;
    }

    /**
     * @param bean
     */
    private void getProductReprot(StatProductBean bean) {
        // 获得产品
        ProductBean pp = productDAO.find(bean.getProductId());

        if (pp != null) {
            bean.setProductName(pp.getName());
            bean.setProductCode(pp.getCode());
        }

        StorageBean sb = storageDAO.find(bean.getStorageId());
        if (sb != null) {
            bean.setStorageName(sb.getName());
        }

        DepotpartBean db = depotpartDAO.find(bean.getDepotpartId());

        if (db != null) {
            bean.setDepotpartName(db.getName());
            // 设置仓库
            bean.setLocationId(db.getLocationId());

            DepotBean depot = depotDAO.find(db.getLocationId());

            if (depot != null) {
                bean.setLocationName(depot.getName());
            }
        }

        if (null != bean.getIndustryId2() && !bean.getIndustryId2().trim().equals("")) {
            PrincipalshipBean psb = principalshipDAO.find(bean.getIndustryId2());
            if (null != psb) {
                bean.setIndustryName2(psb.getName());
            }
        }

        if (null != bean.getIndustryId() && !bean.getIndustryId().trim().equals("")) {
            PrincipalshipBean psb1 = principalshipDAO.find(bean.getIndustryId());
            if (null != psb1) {
                bean.setIndustryName(psb1.getName());
            }

        }

    }

    private void setCondition(HttpServletRequest request, ConditionParse condition) {
        String beginDate = request.getParameter("beginDate");

        condition.addCondition("and t1.productId = t2.id");

        condition.addCondition("and t1.locationId = t3.id");

        condition.addCondition("and t3.id = t4.locationId");

        if (!StringTools.isNullOrNone(beginDate)) {
            condition.addCondition("t1.logTime", ">=", beginDate + " 00:00:00");
        }

        String endDate = request.getParameter("endDate");

        if (!StringTools.isNullOrNone(endDate)) {
            condition.addCondition("t1.logTime", "<=", endDate + " 23:59:59");
        }

        String productName = request.getParameter("productName");

        if (!StringTools.isNullOrNone(productName)) {
            condition.addCondition("t2.name", "like", productName);
        }

        String productCode = request.getParameter("productCode");

        if (!StringTools.isNullOrNone(productCode)) {
            condition.addCondition("t2.code", "like", productCode);
        }

        // add by fang 2012.5.9
        String productType = request.getParameter("productType");

        if (!StringTools.isNullOrNone(productType)) {
            condition.addCondition("t2.reserve4", "=", productType);
        }

        String managerType = request.getParameter("managerType");

        if (!StringTools.isNullOrNone(managerType)) {
            condition.addCondition("t2.type", "=", managerType);
        }

        // add by fang 2012.5.4
        String industryId2 = request.getParameter("industryId2");
        if (!StringTools.isNullOrNone(industryId2)) {
            condition.addCondition("t3.industryId2", "=", industryId2);
        }

        String industryId = request.getParameter("industryId");
        if (!StringTools.isNullOrNone(industryId)) {
            condition.addCondition("t3.industryId", "=", industryId);
        }

        String depotId = request.getParameter("depotId");

        if (!StringTools.isNullOrNone(depotId)) {
            condition.addCondition("t4.locationId", "=", depotId);
        }

        String depotpartId = request.getParameter("depotpartId");

        if (!StringTools.isNullOrNone(depotpartId)) {
            condition.addCondition("t4.depotpartId", "=", depotpartId);
        }

        String storageId = request.getParameter("storageId");

        if (!StringTools.isNullOrNone(storageId)) {
            condition.addCondition("t4.id", "=", storageId);
        }

        condition.addCondition("order by t1.id");
    }

    private void setCondition2(HttpServletRequest request, ConditionParse condition)
    {

        String productName = request.getParameter("productName");

        if (!StringTools.isNullOrNone(productName)) {
            condition.addCondition("t0.name", "like", productName);
        }

        String productCode = request.getParameter("productCode");

        if (!StringTools.isNullOrNone(productCode)) {
            condition.addCondition("t0.code", "like", productCode);
        }

        // add by fang 2012.5.9
        String productType = request.getParameter("productType");

        if (!StringTools.isNullOrNone(productType)) {
            condition.addCondition("t0.reserve4", "=", productType);
        }

        String managerType = request.getParameter("managerType");

        if (!StringTools.isNullOrNone(managerType)) {
            condition.addCondition("t0.type", "=", managerType);
        }

        // add by fang 2012.5.4
        String industryId2 = request.getParameter("industryId2");
        if (!StringTools.isNullOrNone(industryId2)) {
            condition.addCondition("t4.industryId2", "=", industryId2);
        }

        String industryId = request.getParameter("industryId");
        if (!StringTools.isNullOrNone(industryId)) {
            condition.addCondition("t4.industryId", "=", industryId);
        }

        String depotId = request.getParameter("depotId");

        if (!StringTools.isNullOrNone(depotId)) {
            condition.addCondition("t4.id", "=", depotId);
        }

        String depotpartId = request.getParameter("depotpartId");

        if (!StringTools.isNullOrNone(depotpartId)) {
            condition.addCondition("t3.id", "=", depotpartId);
        }

        String storageId = request.getParameter("storageId");

        if (!StringTools.isNullOrNone(storageId)) {
            condition.addCondition("t2.id", "=", storageId);
        }
    
    }
    
    /**
     * @return the storageDAO
     */
    public StorageDAO getStorageDAO() {
        return storageDAO;
    }

    /**
     * @param storageDAO the storageDAO to set
     */
    public void setStorageDAO(StorageDAO storageDAO) {
        this.storageDAO = storageDAO;
    }

    /**
     * @return the productDAO
     */
    public ProductDAO getProductDAO() {
        return productDAO;
    }

    /**
     * @param productDAO the productDAO to set
     */
    public void setProductDAO(ProductDAO productDAO) {
        this.productDAO = productDAO;
    }

    /**
     * @return the depotpartDAO
     */
    public DepotpartDAO getDepotpartDAO() {
        return depotpartDAO;
    }

    /**
     * @param depotpartDAO the depotpartDAO to set
     */
    public void setDepotpartDAO(DepotpartDAO depotpartDAO) {
        this.depotpartDAO = depotpartDAO;
    }

    /**
     * @return the storageLogDAO
     */
    public StorageLogDAO getStorageLogDAO() {
        return storageLogDAO;
    }

    /**
     * @param storageLogDAO the storageLogDAO to set
     */
    public void setStorageLogDAO(StorageLogDAO storageLogDAO) {
        this.storageLogDAO = storageLogDAO;
    }

    /**
     * @return the storageRelationDAO
     */
    public StorageRelationDAO getStorageRelationDAO() {
        return storageRelationDAO;
    }

    /**
     * @param storageRelationDAO the storageRelationDAO to set
     */
    public void setStorageRelationDAO(StorageRelationDAO storageRelationDAO) {
        this.storageRelationDAO = storageRelationDAO;
    }

    /**
     * @return the depotDAO
     */
    public DepotDAO getDepotDAO() {
        return depotDAO;
    }

    /**
     * @param depotDAO the depotDAO to set
     */
    public void setDepotDAO(DepotDAO depotDAO) {
        this.depotDAO = depotDAO;
    }

    /**
     * @return the principalshipDAO
     */
    public PrincipalshipDAO getPrincipalshipDAO() {
        return principalshipDAO;
    }

    /**
     * @param principalshipDAO the principalshipDAO to set
     */
    public void setPrincipalshipDAO(PrincipalshipDAO principalshipDAO) {
        this.principalshipDAO = principalshipDAO;
    }

}
