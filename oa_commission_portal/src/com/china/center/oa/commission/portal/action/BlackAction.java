package com.china.center.oa.commission.portal.action;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
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
import com.center.china.osgi.publics.file.writer.WriteFile;
import com.center.china.osgi.publics.file.writer.WriteFileFactory;
import com.china.center.actionhelper.common.ActionTools;
import com.china.center.actionhelper.common.JSONPageSeparateTools;
import com.china.center.actionhelper.common.JSONTools;
import com.china.center.actionhelper.common.KeyConstant;
import com.china.center.actionhelper.common.OldPageSeparateTools;
import com.china.center.actionhelper.json.AjaxResult;
import com.china.center.actionhelper.query.HandleResult;
import com.china.center.common.MYException;
import com.china.center.common.taglib.DefinedCommon;
import com.china.center.jdbc.util.ConditionParse;
import com.china.center.jdbc.util.PageSeparate;
import com.china.center.oa.commission.bean.BlackBean;
import com.china.center.oa.commission.bean.BlackOutBean;
import com.china.center.oa.commission.bean.BlackRuleBean;
import com.china.center.oa.commission.bean.BlackRuleProductBean;
import com.china.center.oa.commission.bean.BlackRuleStafferBean;
import com.china.center.oa.commission.bean.HisBlackBean;
import com.china.center.oa.commission.bean.HisBlackOutBean;
import com.china.center.oa.commission.dao.BlackDAO;
import com.china.center.oa.commission.dao.BlackOutDAO;
import com.china.center.oa.commission.dao.BlackOutDetailDAO;
import com.china.center.oa.commission.dao.BlackRuleDAO;
import com.china.center.oa.commission.dao.BlackRuleProductDAO;
import com.china.center.oa.commission.dao.BlackRuleStafferDAO;
import com.china.center.oa.commission.dao.HisBlackDAO;
import com.china.center.oa.commission.dao.HisBlackOutDAO;
import com.china.center.oa.commission.dao.HisBlackOutDetailDAO;
import com.china.center.oa.commission.helper.BlackHelper;
import com.china.center.oa.commission.manager.BlackManager;
import com.china.center.oa.commission.vo.BlackOutDetailVO;
import com.china.center.oa.commission.vo.BlackRuleProductVO;
import com.china.center.oa.commission.vo.BlackRuleStafferVO;
import com.china.center.oa.commission.vo.BlackRuleVO;
import com.china.center.oa.commission.vo.BlackVO;
import com.china.center.oa.commission.vo.HisBlackOutDetailVO;
import com.china.center.oa.commission.vo.HisBlackVO;
import com.china.center.oa.product.dao.ProductDAO;
import com.china.center.oa.publics.Helper;
import com.china.center.oa.publics.bean.PrincipalshipBean;
import com.china.center.oa.publics.constant.PublicConstant;
import com.china.center.oa.publics.dao.PrincipalshipDAO;
import com.china.center.oa.publics.dao.StafferDAO;
import com.china.center.oa.sail.bean.BaseBalanceBean;
import com.china.center.oa.sail.bean.BaseBean;
import com.china.center.oa.sail.bean.OutBalanceBean;
import com.china.center.oa.sail.constanst.OutConstant;
import com.china.center.oa.sail.dao.BaseBalanceDAO;
import com.china.center.oa.sail.dao.BaseDAO;
import com.china.center.oa.sail.dao.OutBalanceDAO;
import com.china.center.oa.sail.dao.OutDAO;
import com.china.center.oa.sail.vo.OutVO;
import com.china.center.tools.BeanUtil;
import com.china.center.tools.CommonTools;
import com.china.center.tools.MathTools;
import com.china.center.tools.StringTools;
import com.china.center.tools.TimeTools;
import com.china.center.tools.WriteFileBuffer;

public class BlackAction extends DispatchAction 
{
    private final Log _logger = LogFactory.getLog(getClass());
    
    private BlackManager blackManager = null;
    
    private BlackRuleDAO blackRuleDAO = null;
    
    private BlackRuleProductDAO blackRuleProductDAO = null;
    
    private BlackRuleStafferDAO blackRuleStafferDAO = null;
    
    private PrincipalshipDAO principalshipDAO = null;    
    
    private StafferDAO stafferDAO = null;
    
    private BlackDAO blackDAO = null;
    
    private BlackOutDAO blackOutDAO = null;
    
    private ProductDAO productDAO = null;
    
    private OutDAO outDAO = null;
    
    private BaseDAO baseDAO = null;
    
    private OutBalanceDAO outBalanceDAO = null;

    private BaseBalanceDAO baseBalanceDAO = null;
    
    private BlackOutDetailDAO blackOutDetailDAO = null;
    
    private HisBlackDAO hisBlackDAO = null;
    
    private HisBlackOutDAO hisBlackOutDAO = null;
    
    private HisBlackOutDetailDAO hisBlackOutDetailDAO = null;
    
    private static final String QUERYBLACKRULE = "queryBlackRule";
    
    private static final String QUERYBLACK = "queryBlack";
    
    private static final String QUERYHISBLACK = "queryHisBlack";
    
    private static final String QUERYBLACKDETAIL = "queryBlackDetail";
    
    private static final String QUERYHISBLACKDETAIL = "queryHisBlackDetail";
    
    
    /**
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward addBlackRule(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) 
    throws ServletException
    {
        CommonTools.saveParamers(request);
        
        String [] productIds = request.getParameterValues("productId");
        
        String [] stafferIds = request.getParameterValues("stafferId");
        
        BlackRuleBean bean = new BlackRuleBean();
        
        List<BlackRuleProductBean> productList = new ArrayList<BlackRuleProductBean>();
        
        List<BlackRuleStafferBean> stafferList = new ArrayList<BlackRuleStafferBean>();
        
        bean.setProductList(productList);
        
        bean.setStafferList(stafferList);
        
        fillBean(productIds, stafferIds, productList, stafferList);
        
        BeanUtil.getBean(bean, request);
        
        try
        {
            User user = Helper.getUser(request);
            
            blackManager.addBean(bean, user);

            request.setAttribute(KeyConstant.MESSAGE, "成功操作");
        }
        catch (MYException e)
        {
            _logger.warn(e, e);

            request.setAttribute(KeyConstant.ERROR_MESSAGE, "增加失败:" + e.getMessage());
        }

        CommonTools.removeParamers(request);

        return mapping.findForward("queryBlackRule");
    }

    private void fillBean(String[] productIds, String[] stafferIds,
            List<BlackRuleProductBean> productList, List<BlackRuleStafferBean> stafferList) 
    {
        if (null != productIds)
        {
            for (String each : productIds)
            {
                BlackRuleProductBean pBean = new BlackRuleProductBean();
                
                pBean.setProductId(each);
                
                productList.add(pBean);
            }
        }

        if (null != stafferIds)
        {
            for (String each : stafferIds)
            {
                BlackRuleStafferBean sBean = new BlackRuleStafferBean();
                
                sBean.setStafferId(each);
                
                stafferList.add(sBean);
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
    public ActionForward updateBlackRule(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) 
    throws ServletException
    {
        CommonTools.saveParamers(request);
        
        String [] productIds = request.getParameterValues("productId");
        
        String [] stafferIds = request.getParameterValues("stafferId");
        
        BlackRuleBean bean = new BlackRuleBean();
        
        List<BlackRuleProductBean> productList = new ArrayList<BlackRuleProductBean>();
        
        List<BlackRuleStafferBean> stafferList = new ArrayList<BlackRuleStafferBean>();
        
        bean.setProductList(productList);
        
        bean.setStafferList(stafferList);
        
        fillBean(productIds, stafferIds, productList, stafferList);
        
        BeanUtil.getBean(bean, request);
        
        try
        {
            User user = Helper.getUser(request);
            
            blackManager.updateBean(bean, user);

            request.setAttribute(KeyConstant.MESSAGE, "成功操作");
        }
        catch (MYException e)
        {
            _logger.warn(e, e);

            request.setAttribute(KeyConstant.ERROR_MESSAGE, "增加失败:" + e.getMessage());
        }

        CommonTools.removeParamers(request);

        return mapping.findForward("queryBlackRule");
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
    public ActionForward deleteBlackRule(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) 
    throws ServletException
    {
        AjaxResult ajax = new AjaxResult();
        
        String id = request.getParameter("id");        
        
        try
        {
            User user = Helper.getUser(request);
            
            blackManager.deleteBean(id, user);
            
            ajax.setSuccess("删除成功.");
        }
        catch(MYException e)
        {            
            _logger.warn(e, e);

            ajax.setError("删除失败:" + e.getMessage());
            
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
    public ActionForward queryBlackRule(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
    throws ServletException
    {
        ConditionParse condtion = new ConditionParse();

        condtion.addWhereStr();

        ActionTools.processJSONQueryCondition(QUERYBLACKRULE, request, condtion);
        
        String jsonstr = ActionTools.queryVOByJSONAndToString(QUERYBLACKRULE, request, condtion,
            this.blackRuleDAO, new HandleResult<BlackRuleVO>()
            {
                public void handle(BlackRuleVO obj)
                {
                    handlePrincipalshipResult(obj);
                    
                    handleProductTypeResult(obj);
                    
                    handleProductResult(obj);
                    
                    handleStafferResult(obj);
                }


            });

        return JSONTools.writeResponse(response, jsonstr);
    }
    
    private void handlePrincipalshipResult(BlackRuleVO obj) 
    {
        String industryNames = "";
        
        String industryId = obj.getIndustryId();
        
        if (!StringTools.isNullOrNone(industryId))
        {
            String [] industryIds = industryId.split(";");
            
            if (null != industryIds)
            {
                for (String each : industryIds) 
                {
                   PrincipalshipBean prin = principalshipDAO.find(each);
                   
                   industryNames += prin.getName()+";";
                }
            }
        }
        
        obj.setIndustryName(industryNames);
    }
    
    private void handleProductTypeResult(BlackRuleVO obj) 
    {
        String productTypeNames = "";
        
        String productType = obj.getProductType();
        
        if (!StringTools.isNullOrNone(productType))
        {
            String [] productTypes = productType.split(";");
            
            if (null != productTypes)
            {
                for (String each : productTypes) 
                {
                    int productType1 = MathTools.parseInt(each);
                    
                   productTypeNames += BlackHelper.getProductTypeName(productType1)+";";
                }
            }        
        }        
        
        obj.setProductTypeName(productTypeNames);
    }
    
    private void handleProductResult(BlackRuleVO obj) 
    {
        String productNames = "";
        
        String id = obj.getId();
        
        List<BlackRuleProductVO> list = blackRuleProductDAO.queryEntityVOsByFK(id);
        
        for (BlackRuleProductVO each : list) 
        {
           productNames += each.getProductName() + ";";
        }
        
        obj.setProductName(productNames);
    }
    
    private void handleStafferResult(BlackRuleVO obj) 
    {
        String stafferNames = "";
        
        String id = obj.getId();
        
        List<BlackRuleStafferVO> list = blackRuleStafferDAO.queryEntityVOsByFK(id);
        
        for (BlackRuleStafferVO each : list) 
        {
           stafferNames += each.getStafferName()+";";
        }
        
        obj.setStafferName(stafferNames);
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
    public ActionForward findBlackRule(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
    throws ServletException
    {
        
        String id = request.getParameter("id");
        
        String update = request.getParameter("update");
        
        BlackRuleVO vo = blackRuleDAO.findVO(id);
        
        if (null == vo)
        {
            request.setAttribute(KeyConstant.ERROR_MESSAGE, "数据不 存在，请刷新数据再试.");
            
            return mapping.findForward("queryBlackRule");
        }
        
        handlePrincipalshipResult(vo);
        
        handleProductTypeResult(vo);
        
        List<BlackRuleProductVO> itemList = blackRuleProductDAO.queryEntityVOsByFK(id);
        
        List<BlackRuleStafferVO> item1List = blackRuleStafferDAO.queryEntityVOsByFK(id);
        
        request.setAttribute("bean", vo);
        
        request.setAttribute("itemList", itemList);
        
        request.setAttribute("item1List", item1List);
        
        if ("1".equals(update))
            return mapping.findForward("updateBlackRule");
        
        
        return mapping.findForward("detailBlackRule");
        
    }
    /**
     * 查询自己的黑名单
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward querySelfBlack(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
    throws ServletException
    {

        ConditionParse condtion = new ConditionParse();

        condtion.addWhereStr();

        User user  = Helper.getUser(request);
        
        String stafferId = user.getStafferId();
        
        condtion.addCondition("BlackBean.stafferId", "=", stafferId);
        
        ActionTools.processJSONQueryCondition(QUERYBLACK, request, condtion);
        
        String jsonstr = ActionTools.queryVOByJSONAndToString(QUERYBLACK, request, condtion,
            this.blackDAO, new HandleResult<BlackVO>()
        {
            public void handle(BlackVO obj) 
            {
                obj.setMoney(MathTools.round2(obj.getMoney()));
                obj.setAllMoneys(MathTools.round2(obj.getAllMoneys()));                
                
            }

        });

        return JSONTools.writeResponse(response, jsonstr);
    }
    
    /**
     * 全部黑名单
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward queryAllBlack(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
    throws ServletException
    {

        ConditionParse condtion = new ConditionParse();

        condtion.addWhereStr();
        
        ActionTools.processJSONQueryCondition(QUERYBLACK, request, condtion);
        
        String jsonstr = ActionTools.queryVOByJSONAndToString(QUERYBLACK, request, condtion,
            this.blackDAO, new HandleResult<BlackVO>()
            {
                public void handle(BlackVO obj)
                {
                    obj.setMoney(MathTools.round2(obj.getMoney()));
                    obj.setAllMoneys(MathTools.round2(obj.getAllMoneys()));                
                    
                }

            });

        return JSONTools.writeResponse(response, jsonstr);
    }
    
    public ActionForward queryAllHisBlack(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
    throws ServletException
    {
        ConditionParse condtion = new ConditionParse();

        condtion.addWhereStr();
        
        Map<String, String> changeMap = initLogTime(request, condtion, false);

		ActionTools.processJSONDataQueryCondition(QUERYHISBLACK, request,
				condtion, changeMap);

		String jsonstr = ActionTools.queryVOByJSONAndToString(QUERYHISBLACK,
				request, condtion, this.hisBlackDAO);

        return JSONTools.writeResponse(response, jsonstr);
    }
    
    private Map<String, String> initLogTime(HttpServletRequest request,
			ConditionParse condtion, boolean initStatus) {
		Map<String, String> changeMap = new HashMap<String, String>();

		String logDate = request.getParameter("logDate");

		if (StringTools.isNullOrNone(logDate)) {
			
			changeMap.put("logDate", TimeTools.now_short());

			condtion.addCondition("HisBlackBean.logDate", "=",
					TimeTools.now_short());
		}

		return changeMap;
	}
    
    /**
     * 黑名单明细查询
     */
    public ActionForward findBlack(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
    throws ServletException
    {
        String id = request.getParameter("id");
        
        String type = request.getParameter("type");
        
        String industryId = request.getParameter("industryId");
        
        BlackBean bean = this.blackDAO.find(id);
        
        if (null == bean)
        {
            request.setAttribute(KeyConstant.ERROR_MESSAGE, "数据错误");
            
            return mapping.findForward("querySelfBlack");
        }
                
        ConditionParse con = new ConditionParse();
        
        con.addWhereStr();       
        
        con.addCondition("refId", "=", id);
        
        con.addIntCondition("type", "=", MathTools.parseInt(type));
        
        con.addCondition("industryId", "=", industryId);
              
        con.addCondition(" order by outid");
        
        List<BlackOutBean> beanList = null;
        
        try
        {
            if (OldPageSeparateTools.isFirstLoad(request))
            {
                Object attribute = request.getAttribute("holdCondition");

                ConditionParse condtion = null;

                if (attribute == null)
                {
                    condtion = con;
                }
                else
                {
                    condtion = OldPageSeparateTools.getCondition(request, QUERYBLACKDETAIL);
                }
                
                int tatol = blackOutDAO.countVOByCondition(condtion.toString());

                PageSeparate page = new PageSeparate(tatol, PublicConstant.PAGE_SIZE - 5);
                
                OldPageSeparateTools.initPageSeparate(condtion, page, request, QUERYBLACKDETAIL);
                
                beanList = blackOutDAO.queryEntityVOsByCondition(condtion, page);
                                
            }
            else
            {
                OldPageSeparateTools.processSeparate(request, QUERYBLACKDETAIL);

                beanList = blackOutDAO.queryEntityVOsByCondition(OldPageSeparateTools.getCondition(request,
                        QUERYBLACKDETAIL), OldPageSeparateTools.getPageSeparate(request, QUERYBLACKDETAIL));
            }
        }
        catch (Exception e)
        {
            request.setAttribute(KeyConstant.ERROR_MESSAGE, "查询单据失败");

            _logger.error(e, e);

            return mapping.findForward("error");
        }

        double total = 0.0d;
        
        for (BlackOutBean each : beanList)
        {
        	total += each.getMoney();
        }
        
        request.setAttribute("beanList", beanList);
        
        request.setAttribute("total", total);
        
        request.setAttribute("id", id);
        
        request.setAttribute("type", type);
        
        return mapping.findForward("detailBlack");
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
    public ActionForward findHisBlack(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
    throws ServletException
    {
    	String backupdate = getBackupDate(request);
        
        String id = request.getParameter("id");
        
        String type = request.getParameter("type");
        
        String industryId = request.getParameter("industryId");
        
        HisBlackBean bean = hisBlackDAO.findByUnique(backupdate, id);
        
        if (null == bean)
        {
            request.setAttribute(KeyConstant.ERROR_MESSAGE, "数据错误");
            
            return mapping.findForward("querySelfBlack");
        }
                
        ConditionParse con = new ConditionParse();
        
        con.addWhereStr();
        
        con.addCondition("HisBlackOutBean.refId", "=", id);
        
        con.addIntCondition("HisBlackOutBean.type", "=", MathTools.parseInt(type));
        
        con.addCondition("HisBlackOutBean.industryId", "=", industryId);
        
        con.addCondition("HisBlackOutBean.backupdate", "=", backupdate);
              
        con.addCondition(" order by HisBlackOutBean.outid");
        
        List<HisBlackOutBean> beanList = null;
        
        try
        {
            if (OldPageSeparateTools.isFirstLoad(request))
            {
                Object attribute = request.getAttribute("holdCondition");

                ConditionParse condtion = null;

                if (attribute == null)
                {
                    condtion = con;
                }
                else
                {
                    condtion = OldPageSeparateTools.getCondition(request, QUERYHISBLACKDETAIL);
                }
                
                int tatol = hisBlackOutDAO.countVOByCondition(condtion.toString());

                PageSeparate page = new PageSeparate(tatol, PublicConstant.PAGE_SIZE - 5);
                
                OldPageSeparateTools.initPageSeparate(condtion, page, request, QUERYHISBLACKDETAIL);
                
                beanList = hisBlackOutDAO.queryEntityVOsByCondition(condtion, page);
                                
            }
            else
            {
                OldPageSeparateTools.processSeparate(request, QUERYHISBLACKDETAIL);

                beanList = hisBlackOutDAO.queryEntityVOsByCondition(OldPageSeparateTools.getCondition(request,
                		QUERYHISBLACKDETAIL), OldPageSeparateTools.getPageSeparate(request, QUERYHISBLACKDETAIL));
            }
        }
        catch (Exception e)
        {
            request.setAttribute(KeyConstant.ERROR_MESSAGE, "查询单据失败");

            _logger.error(e, e);

            return mapping.findForward("error");
        }

        double total = 0.0d;
        
        for (HisBlackOutBean each : beanList)
        {
        	total += each.getMoney();
        }
        
        request.setAttribute("beanList", beanList);
        
        request.setAttribute("total", total);
        
        request.setAttribute("id", id);
        
        request.setAttribute("type", type);
        
        return mapping.findForward("detailBlack");
    }

	private String getBackupDate(HttpServletRequest request)
	{
		ConditionParse condtion = JSONPageSeparateTools.getCondition(
				request, QUERYHISBLACK);
        
        String whereCause = condtion.toString();
        
        whereCause = whereCause.substring(whereCause.indexOf("logDate"));
        
        whereCause = whereCause.substring(whereCause.indexOf("'") + 1);
        
        String backupdate = whereCause.substring(0, 10) + " 04:30:00";
        
		return backupdate;
	}
    
    /**
     * 查询产品类别（静态数据），因要多选，采用弹出窗口
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward rptQueryProductType(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
    throws ServletException
    {
        
        String load = request.getParameter("load");
        String selectMode = request.getParameter("selectMode");
        
        request.setAttribute("load", load);
        request.setAttribute("selectMode", selectMode);
        
        return mapping.findForward("rptQueryProductType");
    }
    
    
    /**
     * 导出所有黑名单
     * 
     * @param mapping
     * @param form
     * @param request
     * @param reponse
     * @return
     * @throws ServletException
     */
    public ActionForward exportAllBlackStaffer(ActionMapping mapping, ActionForm form,
                                                HttpServletRequest request,
                                                HttpServletResponse reponse)
        throws ServletException
    {
        OutputStream out = null;

        String filenName = "StafferBlack_" + TimeTools.now("yyyyMMddHHmm") + ".csv";

        reponse.setContentType("application/x-dbf");

        reponse.setHeader("Content-Disposition", "attachment; filename=" + filenName);

        WriteFile write = null;

        List<BlackVO> blackListVO = blackDAO.listEntityVOs();

        try
        {
            out = reponse.getOutputStream();

            write = WriteFileFactory.getMyTXTWriter();

            write.openFile(out);

            write.writeLine("职员,超期应收金额,超期最大天数,全部应收金额,信用属性,进入日期,解除日期,事业部名称");

            String now = TimeTools.now("yyyy-MM-dd");

            WriteFileBuffer line = new WriteFileBuffer(write);
            for(int i = 0 ; i < blackListVO.size();i++ )
            {
            	BlackVO bv = blackListVO.get(i);
            	line.writeColumn(bv.getStafferName());
            	line.writeColumn(bv.getMoney());
            	line.writeColumn(bv.getDays());
            	line.writeColumn(bv.getAllMoneys());
                if(bv.getCredit() == 0)
                {
                	line.writeColumn("正常");
                }
                else if(bv.getCredit() == 1)
                {
                	line.writeColumn("黑名单-款到发货");
                }
                else
                {
                	line.writeColumn("黑名单-停止销售");
                }
            	line.writeColumn(bv.getEntryDate());
            	line.writeColumn(bv.getRemoveDate());
            	line.writeColumn(bv.getIndustryName());
            	line.writeLine();
            }

                line.writeColumn(now);

                line.writeLine();
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
     * 历史某一天的黑名单
     * @param mapping
     * @param form
     * @param request
     * @param reponse
     * @return
     * @throws ServletException
     */
    public ActionForward exportAllHisBlackStaffer(ActionMapping mapping, ActionForm form,
            HttpServletRequest request,
            HttpServletResponse reponse)
	throws ServletException
	{
        OutputStream out = null;

        String filenName = "StafferHisBlack_" + TimeTools.now("yyyyMMddHHmm") + ".csv";

        reponse.setContentType("application/x-dbf");

        reponse.setHeader("Content-Disposition", "attachment; filename=" + filenName);

        WriteFile write = null;

        ConditionParse condtion = JSONPageSeparateTools.getCondition(
				request, QUERYHISBLACK);
        
        List<HisBlackVO> blackListVO = hisBlackDAO.queryEntityVOsByCondition(condtion);

        try
        {
            out = reponse.getOutputStream();

            write = WriteFileFactory.getMyTXTWriter();

            write.openFile(out);

            write.writeLine("职员,超期应收金额,超期最大天数,全部应收金额,信用属性,进入日期,解除日期,事业部名称");

            String now = TimeTools.now("yyyy-MM-dd");

            WriteFileBuffer line = new WriteFileBuffer(write);
            for(int i = 0 ; i < blackListVO.size();i++ )
            {
            	HisBlackVO bv = blackListVO.get(i);
            	line.writeColumn(bv.getStafferName());
            	line.writeColumn(bv.getMoney());
            	line.writeColumn(bv.getDays());
            	line.writeColumn(bv.getAllMoneys());
                if(bv.getCredit() == 0)
                {
                	line.writeColumn("正常");
                }
                else if(bv.getCredit() == 1)
                {
                	line.writeColumn("黑名单-款到发货");
                }
                else
                {
                	line.writeColumn("黑名单-停止销售");
                }
            	line.writeColumn(bv.getEntryDate());
            	line.writeColumn(bv.getRemoveDate());
            	line.writeColumn(bv.getIndustryName());
            	line.writeLine();
            }

                line.writeColumn(now);

                line.writeLine();
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
     * 导出黑名单全部预收
     * exportAllBlackMoney
     * 
     * @param mapping
     * @param form
     * @param request
     * @param reponse
     * @return
     * @throws ServletException
     */
    public ActionForward exportAllBlackMoney(ActionMapping mapping, ActionForm form,
            HttpServletRequest request,
            HttpServletResponse reponse)
	throws ServletException
	{
        OutputStream out = null;

        String filenName = "BlackMoney_" + TimeTools.now("yyyyMMddHHmm") + ".csv";

        reponse.setContentType("application/x-dbf");

        reponse.setHeader("Content-Disposition", "attachment; filename=" + filenName);

        WriteFile write = null;

        ConditionParse con = new ConditionParse();
        
        con.addWhereStr();
        
        con.addIntCondition("BlackOutBean.type", "=", 2);
        
        List<BlackOutBean> blackOutList = blackOutDAO.queryEntityBeansByCondition(con);

        try
        {
            out = reponse.getOutputStream();

            write = WriteFileFactory.getMyTXTWriter();

            write.openFile(out);

            write.writeLine("单号,结算单号,类型,状态,客户,业务员,超期天数,全部应收金额(成本),全部应收金额(成交价),事业部名称");

            WriteFileBuffer line = new WriteFileBuffer(write);
            
            for(BlackOutBean each : blackOutList)
            {
            	String outId = each.getOutId();
            	
            	OutVO outVO = outDAO.findVO(outId);
            	
            	if (null == outVO)
            		continue;
            	
            	line.writeColumn(each.getOutId());
            	line.writeColumn("N/A");
            	line.writeColumn(DefinedCommon.getValue("outType_out", outVO.getOutType()));
            	line.writeColumn("其它");
            	line.writeColumn(outVO.getCustomerName());
            	line.writeColumn(outVO.getStafferName());
            	line.writeColumn(each.getDays());
            	
            	// 如果是委托代销，处理相关的结算单
            	if (outVO.getOutType() == OutConstant.OUTTYPE_OUT_CONSIGN)
            	{
            		List<BaseBean> baseList = baseDAO.queryEntityBeansByFK(outVO.getFullId());
            		
            		double costTotal = 0.0d;
            		
            		for (BaseBean eachbb : baseList)
            		{
            			costTotal += eachbb.getAmount() * eachbb.getCostPrice();
            		}
            		
            		// 取未结算部分
            		List<BaseBalanceBean> baseBalanceList = new ArrayList<BaseBalanceBean>();
            		
            		// 1.结算单
            		List<OutBalanceBean> settleList = outBalanceDAO.queryEntityBeansByCondition("where outid = ? and type = ? and status = 1", each.getOutId(), 0);
            		
            		for (OutBalanceBean eachBase : settleList)
            		{
            			baseBalanceList.addAll(baseBalanceDAO.queryEntityBeansByFK(eachBase.getId()));
            		}
            		
            		// 2.退货结算单
            		List<OutBalanceBean> retsettleList = outBalanceDAO.queryEntityBeansByCondition("where outid = ? and type = ? and status = 99", each.getOutId(), 1);
            		
            		for (OutBalanceBean eachBase : retsettleList)
            		{
            			baseBalanceList.addAll(baseBalanceDAO.queryEntityBeansByFK(eachBase.getId()));
            		}
            		
            		double settleTotal = 0.0d;
            		double settleTotal1 = 0.0d;
            		
            		for (BaseBalanceBean eachBBase : baseBalanceList)
        			{
        				BaseBean base = baseDAO.find(eachBBase.getBaseId());
        				
        				settleTotal += base.getCostPrice() * eachBBase.getAmount();
        				settleTotal1 += base.getPrice() * eachBBase.getAmount();
        			}
            		
            		line.writeColumn(costTotal - settleTotal);
            		line.writeColumn(outVO.getTotal() - settleTotal1);
                	line.writeColumn(outVO.getIndustryName());
                	line.writeLine();
            		
            		List<OutBalanceBean> balanceList = outBalanceDAO.queryByOutIdAndType(each.getOutId(), OutConstant.OUTBALANCE_TYPE_SAIL);
            		
            		for (OutBalanceBean eachbalance : balanceList)
            		{
            			if (eachbalance.getStatus() != 1)
            				continue;
            			
            			double retTotal = outBalanceDAO.sumByOutBalanceId(eachbalance.getId());
            			
            			if (eachbalance.getTotal() - eachbalance.getPayMoney() - retTotal == 0)
            				continue;
            			
            			line.writeColumn(each.getOutId());
            			line.writeColumn(eachbalance.getId());
                    	line.writeColumn("结算单");
                    	line.writeColumn("N/A");
                    	line.writeColumn(outVO.getCustomerName());
                    	line.writeColumn(outVO.getStafferName());
                    	line.writeColumn("N/A");
                    	line.writeColumn("N/A");
                    	line.writeColumn(eachbalance.getTotal() - eachbalance.getPayMoney() - retTotal);
                    	line.writeColumn(outVO.getIndustryName());
                    	line.writeLine();
            		}
            	}else
            	{
            		line.writeColumn("N/A");
            		line.writeColumn(each.getMoney());
                	line.writeColumn(outVO.getIndustryName());
                	line.writeLine();
            	}
            }
            
            // 增加已付款，未发货部分
            ConditionParse cond = new ConditionParse();
            
            cond.addWhereStr();
            
            cond.addCondition("OutBean.type", "=", 0);
            cond.addCondition("OutBean.outtype", "=", 0);
            cond.addCondition("OutBean.pay", "=", 1);
            cond.addCondition(" and OutBean.status not in (3,4)");
            
            List<OutVO> list = outDAO.queryEntityVOsByCondition(cond);
            
            for (OutVO each : list)
            {
            	line.writeColumn(each.getFullId());
    			line.writeColumn("");
            	line.writeColumn("销售出库");
            	line.writeColumn("已付款未发货");
            	line.writeColumn(each.getCustomerName());
            	line.writeColumn(each.getStafferName());
            	line.writeColumn("N/A");
            	line.writeColumn("N/A");
            	line.writeColumn(each.getTotal());
            	line.writeColumn(each.getIndustryName());
            	line.writeLine();
            }

            line.writeLine();
            
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
     * 
     * @param mapping
     * @param form
     * @param request
     * @param reponse
     * @return
     * @throws ServletException
     */
    public ActionForward exportAllHisBlackMoney(ActionMapping mapping, ActionForm form,
            HttpServletRequest request,
            HttpServletResponse reponse)
	throws ServletException
	{
        OutputStream out = null;

        String filenName = "BlackHisMoney_" + TimeTools.now("yyyyMMddHHmm") + ".csv";

        reponse.setContentType("application/x-dbf");

        reponse.setHeader("Content-Disposition", "attachment; filename=" + filenName);

        WriteFile write = null;

        ConditionParse condtion = JSONPageSeparateTools.getCondition(
				request, QUERYHISBLACK);
        
        String whereCause = condtion.toString();
        
        whereCause = whereCause.substring(whereCause.indexOf("logDate"));
        
        whereCause = whereCause.substring(whereCause.indexOf("'") + 1);
        
        String logDate = whereCause.substring(0, 10);
        
        ConditionParse con = new ConditionParse();
        
        con.addWhereStr();
        
        con.addIntCondition("HisBlackOutBean.type", "=", 2);
        
        con.addCondition("HISBlackOutBean.backupdate", ">=", logDate + " 00:01:01");
        
        con.addCondition("HISBlackOutBean.backupdate", "<=", logDate + " 23:59:59");
        
        List<HisBlackOutBean> blackOutList = hisBlackOutDAO.queryEntityBeansByCondition(con);

        try
        {
            out = reponse.getOutputStream();

            write = WriteFileFactory.getMyTXTWriter();

            write.openFile(out);

            write.writeLine("单号,结算单号,类型,状态,客户,业务员,超期天数,全部应收金额(成本),全部应收金额(成交价),事业部名称");

            WriteFileBuffer line = new WriteFileBuffer(write);
            
            for(HisBlackOutBean each : blackOutList)
            {
            	String outId = each.getOutId();
            	
            	OutVO outVO = outDAO.findVO(outId);
            	
            	if (null == outVO)
            		continue;
            	
            	line.writeColumn(each.getOutId());
            	line.writeColumn("N/A");
            	line.writeColumn(DefinedCommon.getValue("outType_out", outVO.getOutType()));
            	line.writeColumn("其它");
            	line.writeColumn(outVO.getCustomerName());
            	line.writeColumn(outVO.getStafferName());
            	line.writeColumn(each.getDays());
            	
            	// 如果是委托代销，处理相关的结算单
            	if (outVO.getOutType() == OutConstant.OUTTYPE_OUT_CONSIGN)
            	{
            		List<BaseBean> baseList = baseDAO.queryEntityBeansByFK(outVO.getFullId());
            		
            		double costTotal = 0.0d;
            		
            		for (BaseBean eachbb : baseList)
            		{
            			costTotal += eachbb.getAmount() * eachbb.getCostPrice();
            		}
            		
            		// 取未结算部分
            		List<BaseBalanceBean> baseBalanceList = new ArrayList<BaseBalanceBean>();
            		
            		// 1.结算单
            		List<OutBalanceBean> settleList = outBalanceDAO.queryEntityBeansByCondition("where outid = ? and type = ? and status = 1", each.getOutId(), 0);
            		
            		for (OutBalanceBean eachBase : settleList)
            		{
            			baseBalanceList.addAll(baseBalanceDAO.queryEntityBeansByFK(eachBase.getId()));
            		}
            		
            		// 2.退货结算单
            		List<OutBalanceBean> retsettleList = outBalanceDAO.queryEntityBeansByCondition("where outid = ? and type = ? and status = 99", each.getOutId(), 1);
            		
            		for (OutBalanceBean eachBase : retsettleList)
            		{
            			baseBalanceList.addAll(baseBalanceDAO.queryEntityBeansByFK(eachBase.getId()));
            		}
            		
            		double settleTotal = 0.0d;
            		double settleTotal1 = 0.0d;
            		
            		for (BaseBalanceBean eachBBase : baseBalanceList)
        			{
        				BaseBean base = baseDAO.find(eachBBase.getBaseId());
        				
        				settleTotal += base.getCostPrice() * eachBBase.getAmount();
        				settleTotal1 += base.getPrice() * eachBBase.getAmount();
        			}
            		
            		line.writeColumn(costTotal - settleTotal);
            		line.writeColumn(outVO.getTotal() - settleTotal1);
                	line.writeColumn(outVO.getIndustryName());
                	line.writeLine();
            		
            		List<OutBalanceBean> balanceList = outBalanceDAO.queryByOutIdAndType(each.getOutId(), OutConstant.OUTBALANCE_TYPE_SAIL);
            		
            		for (OutBalanceBean eachbalance : balanceList)
            		{
            			if (eachbalance.getStatus() != 1)
            				continue;
            			
            			double retTotal = outBalanceDAO.sumByOutBalanceId(eachbalance.getId());
            			
            			if (eachbalance.getTotal() - eachbalance.getPayMoney() - retTotal == 0)
            				continue;
            			
            			line.writeColumn(each.getOutId());
            			line.writeColumn(eachbalance.getId());
                    	line.writeColumn("结算单");
                    	line.writeColumn("N/A");
                    	line.writeColumn(outVO.getCustomerName());
                    	line.writeColumn(outVO.getStafferName());
                    	line.writeColumn("N/A");
                    	line.writeColumn("N/A");
                    	line.writeColumn(eachbalance.getTotal() - eachbalance.getPayMoney() - retTotal);
                    	line.writeColumn(outVO.getIndustryName());
                    	line.writeLine();
            		}
            	}else
            	{
            		line.writeColumn("N/A");
            		line.writeColumn(each.getMoney());
                	line.writeColumn(outVO.getIndustryName());
                	line.writeLine();
            	}
            }
            
            // 增加已付款，未发货部分
            ConditionParse cond = new ConditionParse();
            
            cond.addWhereStr();
            
            cond.addCondition("OutBean.type", "=", 0);
            cond.addCondition("OutBean.outtype", "=", 0);
            cond.addCondition("OutBean.pay", "=", 1);
            cond.addCondition(" and OutBean.status not in (3,4)");
            
            List<OutVO> list = outDAO.queryEntityVOsByCondition(cond);
            
            for (OutVO each : list)
            {
            	line.writeColumn(each.getFullId());
    			line.writeColumn("");
            	line.writeColumn("销售出库");
            	line.writeColumn("已付款未发货");
            	line.writeColumn(each.getCustomerName());
            	line.writeColumn(each.getStafferName());
            	line.writeColumn("N/A");
            	line.writeColumn("N/A");
            	line.writeColumn(each.getTotal());
            	line.writeColumn(each.getIndustryName());
            	line.writeLine();
            }

            line.writeLine();
            
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
     * exportAllBlackDetail
     * 明细
     * @param mapping
     * @param form
     * @param request
     * @param reponse
     * @return
     * @throws ServletException
     */
    public ActionForward exportAllBlackDetail(ActionMapping mapping, ActionForm form,
            HttpServletRequest request,
            HttpServletResponse reponse)
	throws ServletException
	{
        OutputStream out = null;

        String filenName = "BlackDetail_" + TimeTools.now("yyyyMMddHHmm") + ".csv";

        reponse.setContentType("application/x-dbf");

        reponse.setHeader("Content-Disposition", "attachment; filename=" + filenName);

        WriteFile write = null;

        ConditionParse con = new ConditionParse();
        
        con.addWhereStr();
        
        con.addIntCondition("BlackOutBean.type", "=", 2);
        
        List<BlackOutBean> blackOutList = blackOutDAO.queryEntityBeansByCondition(con);

        try
        {
            out = reponse.getOutputStream();

            write = WriteFileFactory.getMyTXTWriter();

            write.openFile(out);

            write.writeLine("单号,结算单号,类型,客户,业务员,事业部名称,产品,数量,单价,成本");

            WriteFileBuffer line = new WriteFileBuffer(write);
            
            for(BlackOutBean each : blackOutList)
            {
            	String outId = each.getOutId();
            	
            	OutVO outVO = outDAO.findVO(outId);
            	
            	if (null == outVO)
            		continue;
            	
            	List<BlackOutDetailVO> dlist = blackOutDetailDAO.queryEntityVOsByFK(outId);
            	
            	for (BlackOutDetailVO eachd : dlist)
            	{
            		line.writeColumn(eachd.getOutId());
                	line.writeColumn(eachd.getOutBalanceId());
                	line.writeColumn(DefinedCommon.getValue("outType_out", outVO.getOutType()));
                	line.writeColumn(outVO.getCustomerName());
                	line.writeColumn(outVO.getStafferName());
                	line.writeColumn(outVO.getIndustryName());
                	
                	line.writeColumn(eachd.getProductName());
                	line.writeColumn(eachd.getAmount());
                	line.writeColumn(eachd.getPrice());
                	line.writeColumn(eachd.getCostPrice());
                	
                	line.writeLine();
            	}
            }

            line.writeLine();
            
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
    
    public ActionForward exportAllHisBlackDetail(ActionMapping mapping, ActionForm form,
            HttpServletRequest request,
            HttpServletResponse reponse)
	throws ServletException
	{
        OutputStream out = null;

        String filenName = "BlackDetail_" + TimeTools.now("yyyyMMddHHmm") + ".csv";

        reponse.setContentType("application/x-dbf");

        reponse.setHeader("Content-Disposition", "attachment; filename=" + filenName);

        WriteFile write = null;

        ConditionParse condtion = JSONPageSeparateTools.getCondition(
				request, QUERYHISBLACK);
        
        String whereCause = condtion.toString();
        
        whereCause = whereCause.substring(whereCause.indexOf("logDate"));
        
        whereCause = whereCause.substring(whereCause.indexOf("'") + 1);
        
        String logDate = whereCause.substring(0, 10);
        
        ConditionParse con = new ConditionParse();
        
        con.addWhereStr();
        
        con.addIntCondition("HisBlackOutBean.type", "=", 2);
        
        con.addCondition("HISBlackOutBean.backupdate", ">=", logDate + " 00:01:01");
        
        con.addCondition("HISBlackOutBean.backupdate", "<=", logDate + " 23:59:59");
        
        List<HisBlackOutBean> blackOutList = hisBlackOutDAO.queryEntityBeansByCondition(con);

        try
        {
            out = reponse.getOutputStream();

            write = WriteFileFactory.getMyTXTWriter();

            write.openFile(out);

            write.writeLine("单号,结算单号,类型,客户,业务员,事业部名称,产品,数量,单价,成本");

            WriteFileBuffer line = new WriteFileBuffer(write);
            
            for(HisBlackOutBean each : blackOutList)
            {
            	String outId = each.getOutId();
            	
            	OutVO outVO = outDAO.findVO(outId);
            	
            	if (null == outVO)
            		continue;
            	
            	List<HisBlackOutDetailVO> dlist = hisBlackOutDetailDAO
            				.queryEntityVOsByCondition("where outid = ? and backupdate >= ? and backupdate <= ?", outId, logDate + " 00:01:01", logDate + " 23:59:59");
            	
            	for (HisBlackOutDetailVO eachd : dlist)
            	{
            		line.writeColumn(eachd.getOutId());
                	line.writeColumn(eachd.getOutBalanceId());
                	line.writeColumn(DefinedCommon.getValue("outType_out", outVO.getOutType()));
                	line.writeColumn(outVO.getCustomerName());
                	line.writeColumn(outVO.getStafferName());
                	line.writeColumn(outVO.getIndustryName());
                	
                	line.writeColumn(eachd.getProductName());
                	line.writeColumn(eachd.getAmount());
                	line.writeColumn(eachd.getPrice());
                	line.writeColumn(eachd.getCostPrice());
                	
                	line.writeLine();
            	}
            }

            line.writeLine();
            
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
    
    public BlackRuleDAO getBlackRuleDAO() {
        return blackRuleDAO;
    }

    public void setBlackRuleDAO(BlackRuleDAO blackRuleDAO) {
        this.blackRuleDAO = blackRuleDAO;
    }

    public BlackRuleProductDAO getBlackRuleProductDAO() {
        return blackRuleProductDAO;
    }

    public void setBlackRuleProductDAO(BlackRuleProductDAO blackRuleProductDAO) {
        this.blackRuleProductDAO = blackRuleProductDAO;
    }

    public BlackRuleStafferDAO getBlackRuleStafferDAO() {
        return blackRuleStafferDAO;
    }

    public void setBlackRuleStafferDAO(BlackRuleStafferDAO blackRuleStafferDAO) {
        this.blackRuleStafferDAO = blackRuleStafferDAO;
    }

    public PrincipalshipDAO getPrincipalshipDAO() {
        return principalshipDAO;
    }

    public void setPrincipalshipDAO(PrincipalshipDAO principalshipDAO) {
        this.principalshipDAO = principalshipDAO;
    }

    public StafferDAO getStafferDAO() {
        return stafferDAO;
    }

    public void setStafferDAO(StafferDAO stafferDAO) {
        this.stafferDAO = stafferDAO;
    }

    public BlackManager getBlackManager() {
        return blackManager;
    }

    public void setBlackManager(BlackManager blackManager) {
        this.blackManager = blackManager;
    }

    public BlackDAO getBlackDAO() {
        return blackDAO;
    }

    public void setBlackDAO(BlackDAO blackDAO) {
        this.blackDAO = blackDAO;
    }

    public BlackOutDAO getBlackOutDAO() {
        return blackOutDAO;
    }

    public void setBlackOutDAO(BlackOutDAO blackOutDAO) {
        this.blackOutDAO = blackOutDAO;
    }

    public ProductDAO getProductDAO() {
        return productDAO;
    }

    public void setProductDAO(ProductDAO productDAO) {
        this.productDAO = productDAO;
    }

	/**
	 * @return the outDAO
	 */
	public OutDAO getOutDAO()
	{
		return outDAO;
	}

	/**
	 * @param outDAO the outDAO to set
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
	 * @param outBalanceDAO the outBalanceDAO to set
	 */
	public void setOutBalanceDAO(OutBalanceDAO outBalanceDAO)
	{
		this.outBalanceDAO = outBalanceDAO;
	}

	/**
	 * @return the baseDAO
	 */
	public BaseDAO getBaseDAO()
	{
		return baseDAO;
	}

	/**
	 * @param baseDAO the baseDAO to set
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
	 * @param baseBalanceDAO the baseBalanceDAO to set
	 */
	public void setBaseBalanceDAO(BaseBalanceDAO baseBalanceDAO)
	{
		this.baseBalanceDAO = baseBalanceDAO;
	}

	/**
	 * @return the blackOutDetailDAO
	 */
	public BlackOutDetailDAO getBlackOutDetailDAO()
	{
		return blackOutDetailDAO;
	}

	/**
	 * @param blackOutDetailDAO the blackOutDetailDAO to set
	 */
	public void setBlackOutDetailDAO(BlackOutDetailDAO blackOutDetailDAO)
	{
		this.blackOutDetailDAO = blackOutDetailDAO;
	}

	/**
	 * @return the hisBlackDAO
	 */
	public HisBlackDAO getHisBlackDAO()
	{
		return hisBlackDAO;
	}

	/**
	 * @param hisBlackDAO the hisBlackDAO to set
	 */
	public void setHisBlackDAO(HisBlackDAO hisBlackDAO)
	{
		this.hisBlackDAO = hisBlackDAO;
	}

	/**
	 * @return the hisBlackOutDAO
	 */
	public HisBlackOutDAO getHisBlackOutDAO()
	{
		return hisBlackOutDAO;
	}

	/**
	 * @param hisBlackOutDAO the hisBlackOutDAO to set
	 */
	public void setHisBlackOutDAO(HisBlackOutDAO hisBlackOutDAO)
	{
		this.hisBlackOutDAO = hisBlackOutDAO;
	}

	/**
	 * @return the hisBlackOutDetailDAO
	 */
	public HisBlackOutDetailDAO getHisBlackOutDetailDAO()
	{
		return hisBlackOutDetailDAO;
	}

	/**
	 * @param hisBlackOutDetailDAO the hisBlackOutDetailDAO to set
	 */
	public void setHisBlackOutDetailDAO(HisBlackOutDetailDAO hisBlackOutDetailDAO)
	{
		this.hisBlackOutDetailDAO = hisBlackOutDetailDAO;
	}
}
