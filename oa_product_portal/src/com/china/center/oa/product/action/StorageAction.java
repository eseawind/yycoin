/**
 * File Name: LocationAction.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2010-6-27<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.product.action;


import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
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
import com.china.center.actionhelper.common.JSONTools;
import com.china.center.actionhelper.common.KeyConstant;
import com.china.center.actionhelper.common.OldPageSeparateTools;
import com.china.center.actionhelper.common.PageSeparateTools;
import com.china.center.actionhelper.json.AjaxResult;
import com.china.center.actionhelper.jsonimpl.JSONArray;
import com.china.center.actionhelper.query.HandleHint;
import com.china.center.actionhelper.query.HandleResult;
import com.china.center.common.MYException;
import com.china.center.common.taglib.DefinedCommon;
import com.china.center.jdbc.util.ConditionParse;
import com.china.center.jdbc.util.PageSeparate;
import com.china.center.oa.product.bean.DepotBean;
import com.china.center.oa.product.bean.DepotpartBean;
import com.china.center.oa.product.bean.GSOutBean;
import com.china.center.oa.product.bean.GSOutItemBean;
import com.china.center.oa.product.bean.PriceConfigBean;
import com.china.center.oa.product.bean.ProductBean;
import com.china.center.oa.product.bean.StorageApplyBean;
import com.china.center.oa.product.bean.StorageBean;
import com.china.center.oa.product.constant.DepotConstant;
import com.china.center.oa.product.constant.ProductConstant;
import com.china.center.oa.product.constant.StorageConstant;
import com.china.center.oa.product.dao.DepotDAO;
import com.china.center.oa.product.dao.DepotpartDAO;
import com.china.center.oa.product.dao.GSOutDAO;
import com.china.center.oa.product.dao.GSOutItemDAO;
import com.china.center.oa.product.dao.PriceConfigDAO;
import com.china.center.oa.product.dao.ProductChangeRecordDAO;
import com.china.center.oa.product.dao.ProductCombinationDAO;
import com.china.center.oa.product.dao.ProductDAO;
import com.china.center.oa.product.dao.ProductVSLocationDAO;
import com.china.center.oa.product.dao.StorageApplyDAO;
import com.china.center.oa.product.dao.StorageDAO;
import com.china.center.oa.product.dao.StorageLogDAO;
import com.china.center.oa.product.dao.StorageRelationDAO;
import com.china.center.oa.product.facade.ProductFacade;
import com.china.center.oa.product.manager.PriceConfigManager;
import com.china.center.oa.product.manager.StorageManager;
import com.china.center.oa.product.manager.StorageRelationManager;
import com.china.center.oa.product.vo.DepotVO;
import com.china.center.oa.product.vo.DepotpartVO;
import com.china.center.oa.product.vo.GSOutItemVO;
import com.china.center.oa.product.vo.GSOutVO;
import com.china.center.oa.product.vo.ProductChangeRecordVO;
import com.china.center.oa.product.vo.ProductVO;
import com.china.center.oa.product.vo.StorageLogVO;
import com.china.center.oa.product.vo.StorageRelationVO;
import com.china.center.oa.product.vs.ProductVSLocationBean;
import com.china.center.oa.publics.Helper;
import com.china.center.oa.publics.LocationHelper;
import com.china.center.oa.publics.bean.DutyBean;
import com.china.center.oa.publics.bean.FlowLogBean;
import com.china.center.oa.publics.bean.InvoiceBean;
import com.china.center.oa.publics.bean.StafferBean;
import com.china.center.oa.publics.constant.PublicConstant;
import com.china.center.oa.publics.dao.DutyDAO;
import com.china.center.oa.publics.dao.FlowLogDAO;
import com.china.center.oa.publics.dao.InvoiceDAO;
import com.china.center.oa.publics.dao.StafferDAO;
import com.china.center.oa.publics.helper.OATools;
import com.china.center.oa.publics.vo.FlowLogVO;
import com.china.center.oa.sail.bean.SailConfBean;
import com.china.center.oa.sail.bean.SailConfigBean;
import com.china.center.oa.sail.constanst.SailConstant;
import com.china.center.oa.sail.dao.SailConfigDAO;
import com.china.center.oa.sail.manager.AuditRuleManager;
import com.china.center.oa.sail.manager.OutManager;
import com.china.center.oa.sail.manager.SailConfigManager;
import com.china.center.oa.tax.bean.FinanceBean;
import com.china.center.oa.tax.dao.FinanceDAO;
import com.china.center.osgi.jsp.ElTools;
import com.china.center.tools.BeanUtil;
import com.china.center.tools.CommonTools;
import com.china.center.tools.IntegerWrap;
import com.china.center.tools.JudgeTools;
import com.china.center.tools.ListTools;
import com.china.center.tools.MathTools;
import com.china.center.tools.RequestTools;
import com.china.center.tools.StringTools;
import com.china.center.tools.TimeTools;
import com.china.center.tools.WriteFileBuffer;


/**
 * DepotAction
 * 
 * @author ZHUZHU
 * @version 2010-6-27
 * @see StorageAction
 * @since 1.0
 */
public class StorageAction extends DispatchAction
{
    private final Log _logger = LogFactory.getLog(getClass());

    private ProductFacade productFacade = null;

    private StorageDAO storageDAO = null;

    private ProductDAO productDAO = null;

    private DepotpartDAO depotpartDAO = null;

    private DepotDAO depotDAO = null;

    private StafferDAO stafferDAO = null;
    
    private StorageManager storageManager = null ;
    
    private ProductChangeRecordDAO productChangeRecordDAO = null;

    private InvoiceDAO invoiceDAO = null;

    private DutyDAO dutyDAO = null;

    private SailConfigManager sailConfigManager = null;

    private OutManager outManager = null;

    private SailConfigDAO sailConfigDAO = null;

    private StorageLogDAO storageLogDAO = null;

    private ProductVSLocationDAO productVSLocationDAO = null;

    private StorageRelationManager storageRelationManager = null;

    private StorageRelationDAO storageRelationDAO = null;

    private StorageApplyDAO storageApplyDAO = null;

    private static final int MAXLENGTH = 200;

    private ProductCombinationDAO productCombinationDAO = null;
    
    private PriceConfigManager priceConfigManager = null;
    
    private AuditRuleManager auditRuleManager = null;
    
    private PriceConfigDAO priceConfigDAO = null;
    
    private GSOutDAO gsOutDAO = null;
    
    private GSOutItemDAO gsOutItemDAO = null;
    
    private FlowLogDAO flowLogDAO = null;
    
    private FinanceDAO financeDAO = null;
    
    private static final String QUERYSTORAGE = "queryStorage";

    private static final String QUERYSTORAGERELATION = "queryStorageRelation";

    private static final String QUERYDEPOTSTORAGERELATION = "queryDepotStorageRelation";

    private static final String QUERYSELFSTORAGERELATION = "querySelfStorageRelation";

    private static final String RPTQUERYPRODUCTINDEPOTPART = "rptQueryProductInDepotpart";

    private static final String RPTQUERYPRODUCTINDEPOT = "rptQueryProductInDepot";

    private static final String QUERYSTORAGEAPPLY = "queryStorageApply";

    private static final String QUERYPUBLICSTORAGERELATION = "queryPublicStorageRelation";

    private static final String RPTQUERYSTORAGERELATIONINDEPOT = "rptQueryStorageRelationInDepot";
    
    private static final String QUERYMOVEPRODUCT = "queryMoveProduct";
    
    private static final String QUERYGSOUT = "queryGSOut";

    /**
     * default constructor
     */
    public StorageAction()
    {
    }

    public ActionForward queryStorage(ActionMapping mapping, ActionForm form,
                                      HttpServletRequest request, HttpServletResponse response)
        throws ServletException
    {
        ConditionParse condtion = new ConditionParse();

        condtion.addWhereStr();

        ActionTools.processJSONQueryCondition(QUERYSTORAGE, request, condtion);

        String jsonstr = ActionTools.queryVOByJSONAndToString(QUERYSTORAGE, request, condtion,
            this.storageDAO);

        return JSONTools.writeResponse(response, jsonstr);
    }

    /**
     * queryStorageRelation
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward queryStorageRelation(ActionMapping mapping, ActionForm form,
                                              HttpServletRequest request,
                                              HttpServletResponse response)
        throws ServletException
    {
        ConditionParse condtion = new ConditionParse();

        condtion.addWhereStr();

        ActionTools.processJSONQueryCondition(QUERYSTORAGERELATION, request, condtion);

        notQueryFirstTime(condtion);
        
        final IntegerWrap wrap = new IntegerWrap();

        String jsonstr = ActionTools.queryVOByJSONAndToString(QUERYSTORAGERELATION, request,
            condtion, this.storageRelationDAO, new HandleResult<StorageRelationVO>()
            {
                public void handle(StorageRelationVO vo)
                {
                    if (StringTools.isNullOrNone(vo.getStafferName()))
                    {
                        vo.setStafferName("公共");
                    }

                    int preassign = storageRelationManager.sumPreassignByStorageRelation(vo);

                    vo.setPreassignAmount(preassign);

                    int inway = storageRelationManager.sumInwayByStorageRelation(vo);

                    vo.setInwayAmount(inway);

                    wrap.add(vo.getAmount());
                }
            }, new HandleHint<StorageRelationVO>()
            {
                public String getHint(List<StorageRelationVO> list)
                {
                    return "产品当前页数量:" + wrap.getResult();
                }
            });

        return JSONTools.writeResponse(response, jsonstr);
    }

    /**
     * queryPublicStorageRelation
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward queryPublicStorageRelation(ActionMapping mapping, ActionForm form,
    		final HttpServletRequest request,HttpServletResponse response)
        throws ServletException
    {
        ConditionParse condtion = new ConditionParse();

        condtion.addWhereStr();

        ActionTools.processJSONQueryCondition(QUERYPUBLICSTORAGERELATION, request, condtion);
        
        notQueryFirstTime(condtion);
        
        condtion.addCondition("StorageRelationBean.stafferId", "=", StorageConstant.PUBLIC_STAFFER);

        condtion.addIntCondition("StorageRelationBean.amount", ">", 0);

        String jsonstr = ActionTools.queryVOByJSONAndToString(QUERYPUBLICSTORAGERELATION, request,
            condtion, this.storageRelationDAO, new HandleResult<StorageRelationVO>()
            {
                public void handle(StorageRelationVO vo)
                {
                	ProductVO obj = productDAO.findVO(vo.getProductId());
                	
                	// 根据配置获取结算价
                	List<PriceConfigBean> list = priceConfigDAO.querySailPricebyProductId(obj.getId());
                	
                	if (!ListTools.isEmptyOrNull(list))
                	{
                		PriceConfigBean cb = priceConfigManager.calcSailPrice(list.get(0));
                		
                		obj.setSailPrice(cb.getSailPrice());
                	}
                	
                	SailConfBean sailConf = sailConfigManager.findProductConf(Helper
                            .getStaffer(request), obj);

                	vo.setProductSailPrice(obj.getSailPrice()
                                         * (1 + sailConf.getPratio() / 1000.0d + sailConf
                                             .getIratio() / 1000.0d));
                	
                    if (StringTools.isNullOrNone(vo.getStafferName()))
                    {
                        vo.setStafferName("公共");
                    }

                    int preassign = storageRelationManager.sumPreassignByStorageRelation(vo);

                    vo.setPreassignAmount(preassign);

                    int inway = storageRelationManager.sumInwayByStorageRelation(vo);

                    vo.setInwayAmount(inway);

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
     * 查询仓库下的产品库存
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward queryDepotStorageRelation(ActionMapping mapping, ActionForm form,
                                                   HttpServletRequest request,
                                                   HttpServletResponse response)
        throws ServletException
    {
        ConditionParse condtion = new ConditionParse();

        condtion.addWhereStr();

        condtion.addCondition("StorageRelationBean.locationId", "=", request
            .getParameter("depotId"));

        ActionTools.processJSONQueryCondition(QUERYDEPOTSTORAGERELATION, request, condtion);

        condtion.addCondition("order by StorageRelationBean.amount desc");

        String jsonstr = ActionTools.queryVOByJSONAndToString(QUERYDEPOTSTORAGERELATION, request,
            condtion, this.storageRelationDAO, new HandleResult<StorageRelationVO>()
            {
                public void handle(StorageRelationVO vo)
                {
                    if (StringTools.isNullOrNone(vo.getStafferName()))
                    {
                        vo.setStafferName("公共");
                    }

                    int preassign = storageRelationManager.sumPreassignByStorageRelation(vo);

                    vo.setPreassignAmount(preassign);

                    int inway = storageRelationManager.sumInwayByStorageRelation(vo);

                    vo.setInwayAmount(inway);
                }
            });

        return JSONTools.writeResponse(response, jsonstr);
    }

    /**
     * querySelfStorageRelation
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward querySelfStorageRelation(ActionMapping mapping, ActionForm form,
                                                  HttpServletRequest request,
                                                  HttpServletResponse response)
        throws ServletException
    {
        User user = Helper.getUser(request);

        ConditionParse condtion = new ConditionParse();

        condtion.addWhereStr();

        condtion.addCondition("StorageRelationBean.stafferId", "=", user.getStafferId());

        ActionTools.processJSONQueryCondition(QUERYSELFSTORAGERELATION, request, condtion);

        String jsonstr = ActionTools.queryVOByJSONAndToString(QUERYSELFSTORAGERELATION, request,
            condtion, this.storageRelationDAO);

        return JSONTools.writeResponse(response, jsonstr);
    }

    /**
     * querySelfStorageRelation
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward queryStorageApply(ActionMapping mapping, ActionForm form,
                                           HttpServletRequest request, HttpServletResponse response)
        throws ServletException
    {
        User user = Helper.getUser(request);

        ConditionParse condtion = new ConditionParse();

        condtion.addWhereStr();

        condtion.addCondition("StorageApplyBean.reveiver", "=", user.getStafferId());

        condtion.addIntCondition("StorageApplyBean.status", "=",
            StorageConstant.STORAGEAPPLY_STATUS_SUBMIT);

        ActionTools.processJSONQueryCondition(QUERYSTORAGEAPPLY, request, condtion);

        String jsonstr = ActionTools.queryVOByJSONAndToString(QUERYSTORAGEAPPLY, request, condtion,
            this.storageApplyDAO);

        return JSONTools.writeResponse(response, jsonstr);
    }

    /**
     * queryStorageLog
     * 
     * @param mapping
     * @param form
     * @param request
     * @param reponse
     * @return
     * @throws ServletException
     */
    public ActionForward queryStorageLog(ActionMapping mapping, ActionForm form,
                                         HttpServletRequest request, HttpServletResponse reponse)
        throws ServletException
    {
        ConditionParse condition = new ConditionParse();

        setCondition(request, condition);

        // 获取指定时间内的仓区的异动(仅仅查询最近的1000个)
        List<StorageLogVO> list = storageLogDAO.queryEntityVOsByLimit(condition, 1000);

        for (StorageLogVO storageLogBean : list)
        {
            storageLogBean.setTypeName(ElTools.get("storageType", storageLogBean.getType()));
            if(storageLogBean.getDescription().contains("["))
            {
            	String oldDesc = storageLogBean.getDescription();
            	String des = storageLogBean.getDescription().substring(storageLogBean.getDescription().indexOf("[")+1,storageLogBean.getDescription().indexOf("]"));
            	if(des.length() == 21)
            	{
            	storageLogBean.setDescription(oldDesc.replace(des, "<a href='../sail/out.do?method=findOut&queryType=6&radioIndex=0&fow=99&outId="+des+"'>"+des+ "</a>"));
            	}
            }
        }

        Collections.sort(list, new Comparator<StorageLogVO>()
        {
            public int compare(StorageLogVO o1, StorageLogVO o2)
            {
                return Integer.parseInt(o1.getId()) - Integer.parseInt(o2.getId());
            }
        });

        request.setAttribute("listStorageLog", list);

        String queryType = request.getParameter("queryType");

        // 仓区下
        if ("1".equals(queryType))
        {
            return mapping.findForward("listStorageLog");
        }

        // 仓库下
        return mapping.findForward("listStorageLog1");
    }

    /**
     * checkStorageLog
     * 
     * @param mapping
     * @param form
     * @param request
     * @param reponse
     * @return
     * @throws ServletException
     */
    public ActionForward checkStorageLog(ActionMapping mapping, ActionForm form,
                                         HttpServletRequest request, HttpServletResponse reponse)
        throws ServletException
    {
        List<String> checkStorageLog = storageRelationManager.checkStorageLog();

        request.setAttribute("checkStorageLog", checkStorageLog);

        // 仓库下
        return mapping.findForward("checkStorageLog");
    }

    /**
     * 当天仓库的异动明细
     * 
     * @param mapping
     * @param form
     * @param request
     * @param reponse
     * @return
     * @throws ServletException
     */
    public ActionForward queryProductInOut(ActionMapping mapping, ActionForm form,
                                           HttpServletRequest request, HttpServletResponse reponse)
        throws ServletException
    {
        CommonTools.saveParamers(request);

        String depotId = request.getParameter("depotId");

        String now = request.getParameter("now");
        String name = request.getParameter("name");
        String code = request.getParameter("code");

        ConditionParse condition = new ConditionParse();

        condition.addWhereStr();

        // 默认是当天的
        if (StringTools.isNullOrNone(now))
        {
            now = TimeTools.now_short();
        }

        condition.addCondition("StorageLogBean.logTime", ">=", now + " 00:00:00");

        condition.addCondition("StorageLogBean.logTime", "<=", now + " 23:59:59");

        condition.addCondition("StorageLogBean.locationid", "=", depotId);

        List<StorageLogVO> list = storageLogDAO.queryEntityVOsByCondition(condition);

        Collections.sort(list, new Comparator<StorageLogVO>()
        {
            public int compare(StorageLogVO o1, StorageLogVO o2)
            {
                return o1.getProductId().compareTo(o2.getProductId());
            }
        });

        DepotBean depot = depotDAO.find(depotId);

        request.getSession().setAttribute("queryProductInOut_depot", depot);

        request.getSession().setAttribute("queryProductInOut_now", now);

        // 统计进出数量
        Map<String, StorageLogVO> result = new HashMap<String, StorageLogVO>();

        for (StorageLogVO storageLogVO : list)
        {
            StorageLogVO vo = result.get(storageLogVO.getProductId());

            if (vo == null)
            {
                vo = new StorageLogVO();

                vo.setProductId(storageLogVO.getProductId());

                vo.setProductName(storageLogVO.getProductName());

                vo.setProductCode(storageLogVO.getProductCode());

                result.put(storageLogVO.getProductId(), vo);
            }

            if (storageLogVO.getChangeAmount() >= 0)
            {
                // 入库数量
                vo.setPreAmount(vo.getPreAmount() + storageLogVO.getChangeAmount());
            }
            else
            {
                // 出库数量
                vo.setAfterAmount(vo.getAfterAmount() + storageLogVO.getChangeAmount());
            }
        }

        Collection<StorageLogVO> values = result.values();

        for (StorageLogVO vo : values)
        {
            vo.setAfterAmount( -vo.getAfterAmount());

            // 获得当前的实际库存
            int current = storageRelationDAO.sumProductInLocationId(vo.getProductId(), depotId);

            // 仓库内所有的产品数量
            vo.setAfterAmount1(current);

            int okSum = storageRelationDAO.sumProductInOKLocationId(vo.getProductId(), depotId);

            // 良品仓数量
            vo.setAfterAmount2(okSum);

            // 异动数量
            vo.setChangeAmount(vo.getPreAmount() - vo.getAfterAmount());
        }

        List<StorageLogVO> showList = new ArrayList<StorageLogVO>();

        showList.addAll(values);

        for (Iterator iterator = showList.iterator(); iterator.hasNext();)
        {
            StorageLogVO storageLogVO2 = (StorageLogVO)iterator.next();

            if ( !StringTools.isNullOrNone(name))
            {
                if (storageLogVO2.getProductName().indexOf(name) == -1)
                {
                    iterator.remove();

                    continue;
                }
            }

            if ( !StringTools.isNullOrNone(code))
            {
                if (storageLogVO2.getProductCode().indexOf(code) == -1)
                {
                    iterator.remove();
                    continue;
                }
            }

        }

        request.setAttribute("showList", showList);

        request.getSession().setAttribute("g_queryProductInOut_resultList", result.values());

        // 仓库下
        return mapping.findForward("queryProductInOut");
    }

    /**
     * 导出
     * 
     * @param mapping
     * @param form
     * @param request
     * @param reponse
     * @return
     * @throws ServletException
     */
    public ActionForward exportProductInOut(ActionMapping mapping, ActionForm form,
                                            HttpServletRequest request, HttpServletResponse reponse)
        throws ServletException
    {
        Collection<StorageLogVO> values = (Collection<StorageLogVO>)request
            .getSession()
            .getAttribute("g_queryProductInOut_resultList");

        String now = request.getSession().getAttribute("queryProductInOut_now").toString();

        DepotBean depot = (DepotBean)request.getSession().getAttribute("queryProductInOut_depot");

        OutputStream out = null;

        String filenName = "ProductChange_" + TimeTools.now("MMddHHmmss") + ".csv";

        reponse.setContentType("application/x-dbf");

        reponse.setHeader("Content-Disposition", "attachment; filename=" + filenName);

        WriteFile write = null;

        try
        {
            out = reponse.getOutputStream();

            write = WriteFileFactory.getMyTXTWriter();

            write.openFile(out);

            write.writeLine("日期,仓库,产品名称,产品编码,入库数量,出库数量,异动数量,当前库存,良品库存");

            for (StorageLogVO each : values)
            {
                String code = each.getProductCode();

                if (code.length() > 10)
                {
                    code = "A" + code;
                }

                write.writeLine(now + ',' + depot.getName() + ','
                                + each.getProductName().replaceAll(",", " ").replaceAll("\r\n", "")
                                + ',' + code + ',' + each.getPreAmount() + ','
                                + each.getAfterAmount() + ',' + each.getChangeAmount() + ','
                                + each.getAfterAmount1() + ',' + each.getAfterAmount2());
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
     * setCondition
     * 
     * @param request
     * @param condition
     */
    private void setCondition(HttpServletRequest request, ConditionParse condition)
    {
        String productId = request.getParameter("productId");

        String depotpartId = request.getParameter("depotpartId");

        String locationId = request.getParameter("locationId");

        String priceKey = request.getParameter("priceKey");

        condition.addCondition("StorageLogBean.productId", "=", productId);

        if ( !StringTools.isNullOrNone(depotpartId))
        {
            condition.addCondition("StorageLogBean.depotpartId", "=", depotpartId);
        }

        if ( !StringTools.isNullOrNone(locationId))
        {
            condition.addCondition("StorageLogBean.locationId", "=", locationId);
        }

        if ( !StringTools.isNullOrNone(priceKey))
        {
            condition.addCondition("StorageLogBean.priceKey", "=", priceKey);

            request.setAttribute("priceKey", 1);
        }
        else
        {
            request.setAttribute("priceKey", 0);
        }

        condition.addCondition("order by StorageLogBean.id desc");
    }

    /**
     * addStorage
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward addStorage(ActionMapping mapping, ActionForm form,
                                    HttpServletRequest request, HttpServletResponse response)
        throws ServletException
    {
        StorageBean bean = new StorageBean();

        try
        {
            BeanUtil.getBean(bean, request);

            User user = Helper.getUser(request);

            productFacade.addStorageBean(user.getId(), bean);

            request.setAttribute(KeyConstant.MESSAGE, "成功增加储位:" + bean.getName());
        }
        catch (MYException e)
        {
            _logger.warn(e, e);

            request.setAttribute(KeyConstant.ERROR_MESSAGE, "增加失败:" + e.getMessage());
        }

        CommonTools.removeParamers(request);

        return mapping.findForward("queryStorage");
    }

    /**
     * updateDepot
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward updateStorage(ActionMapping mapping, ActionForm form,
                                       HttpServletRequest request, HttpServletResponse response)
        throws ServletException
    {
        StorageBean bean = new StorageBean();

        try
        {
            BeanUtil.getBean(bean, request);

            User user = Helper.getUser(request);

            productFacade.updateStorageBean(user.getId(), bean);

            request.setAttribute(KeyConstant.MESSAGE, "成功操作储位:" + bean.getName());
        }
        catch (MYException e)
        {
            _logger.warn(e, e);

            request.setAttribute(KeyConstant.ERROR_MESSAGE, "操作失败:" + e.getMessage());
        }

        CommonTools.removeParamers(request);

        return mapping.findForward("queryStorage");
    }

    /**
     * preForAddStorage
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward preForMoveDepotpart(ActionMapping mapping, ActionForm form,
                                             HttpServletRequest request,
                                             HttpServletResponse response)
        throws ServletException
    {
        CommonTools.saveParamers(request);

        String depotId = RequestTools.getValueFromRequest(request, "id");

        List<DepotpartBean> depotpartList = depotpartDAO.queryEntityBeansByFK(depotId);

        request.setAttribute("depotpartList", depotpartList);

        return mapping.findForward("moveDepotpart");
    }

    /**
     * preForAddStorage
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward preForAddStorage(ActionMapping mapping, ActionForm form,
                                          HttpServletRequest request, HttpServletResponse response)
        throws ServletException
    {
        List<DepotBean> depotList = depotDAO.queryCommonDepotBean();

        request.setAttribute("depotList", depotList);

        return mapping.findForward("addStorage");
    }

    /**
     * preForApplyStorage
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward preForAddStorageApply(ActionMapping mapping, ActionForm form,
                                               HttpServletRequest request,
                                               HttpServletResponse response)
        throws ServletException
    {
        String id = request.getParameter("id");

        StorageRelationVO bean = storageRelationDAO.findVO(id);

        if (StringTools.isNullOrNone(bean.getStafferName()))
        {
            bean.setStafferName("公共");
        }

        request.setAttribute("bean", bean);

        return mapping.findForward("addStorageApply");
    }

    /**
     * addStorageApply
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward addStorageApply(ActionMapping mapping, ActionForm form,
                                         HttpServletRequest request, HttpServletResponse response)
        throws ServletException
    {
        StorageApplyBean bean = new StorageApplyBean();

        try
        {
            BeanUtil.getBean(bean, request);

            User user = Helper.getUser(request);

            StorageRelationVO relation = storageRelationDAO.findVO(bean.getStorageRelationId());

            if (relation == null)
            {
                return ActionTools.toError("数据错误,请重新操作", mapping, request);
            }

            bean.setProductName(relation.getProductName());
            bean.setApplyer(user.getStafferId());
            bean.setLogTime(TimeTools.now());

            if (bean.getAmount() > relation.getAmount() || bean.getAmount() == 0)
            {
                request.setAttribute(KeyConstant.ERROR_MESSAGE, "转移的数量非法");

                return mapping.findForward("querySelfStorageRelation");
            }

            productFacade.addStorageApply(user.getId(), bean);

            request.setAttribute(KeyConstant.MESSAGE, "成功操作");
        }
        catch (MYException e)
        {
            _logger.warn(e, e);

            request.setAttribute(KeyConstant.ERROR_MESSAGE, "操作失败:" + e.getMessage());
        }

        CommonTools.removeParamers(request);

        return mapping.findForward("querySelfStorageRelation");
    }

    /**
     * deleteStorage
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward deleteStorage(ActionMapping mapping, ActionForm form,
                                       HttpServletRequest request, HttpServletResponse response)
        throws ServletException
    {
        AjaxResult ajax = new AjaxResult();

        try
        {
            String id = request.getParameter("id");

            User user = Helper.getUser(request);

            productFacade.deleteStorageBean(user.getId(), id);

            ajax.setSuccess("成功删除储位");
        }
        catch (MYException e)
        {
            _logger.warn(e, e);

            ajax.setError("删除失败:" + e.getMessage());
        }

        return JSONTools.writeResponse(response, ajax);
    }

    /**
     * passStorageApply
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward passStorageApply(ActionMapping mapping, ActionForm form,
                                          HttpServletRequest request, HttpServletResponse response)
        throws ServletException
    {
        AjaxResult ajax = new AjaxResult();

        try
        {
            String id = request.getParameter("id");

            User user = Helper.getUser(request);

            productFacade.passStorageApply(user.getId(), id);

            ajax.setSuccess("成功操作");
        }
        catch (MYException e)
        {
            _logger.warn(e, e);

            ajax.setError("操作失败:" + e.getMessage());
        }

        return JSONTools.writeResponse(response, ajax);
    }

    /**
     * passStorageApply
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward rejectStorageApply(ActionMapping mapping, ActionForm form,
                                            HttpServletRequest request, HttpServletResponse response)
        throws ServletException
    {
        AjaxResult ajax = new AjaxResult();

        try
        {
            String id = request.getParameter("id");

            User user = Helper.getUser(request);

            productFacade.rejectStorageApply(user.getId(), id);

            ajax.setSuccess("成功操作");
        }
        catch (MYException e)
        {
            _logger.warn(e, e);

            ajax.setError("操作失败:" + e.getMessage());
        }

        return JSONTools.writeResponse(response, ajax);
    }

    /**
     * deleteStorageRelation
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward deleteStorageRelation(ActionMapping mapping, ActionForm form,
                                               HttpServletRequest request,
                                               HttpServletResponse response)
        throws ServletException
    {
        AjaxResult ajax = new AjaxResult();

        try
        {
            String id = request.getParameter("id");

            User user = Helper.getUser(request);

            productFacade.deleteStorageRelation(user.getId(), id);

            ajax.setSuccess("成功删除库存");
        }
        catch (MYException e)
        {
            _logger.warn(e, e);

            ajax.setError("删除失败:" + e.getMessage());
        }

        return JSONTools.writeResponse(response, ajax);
    }

    /**
     * initPriceKey
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward initPriceKey(ActionMapping mapping, ActionForm form,
                                      HttpServletRequest request, HttpServletResponse response)
        throws ServletException
    {
        AjaxResult ajax = new AjaxResult();

        int[] initPriceKey = storageRelationManager.initPriceKey();

        int[] initPriceKey2 = outManager.initPriceKey();

        ajax.setSuccess("成功初始化KEY:" + initPriceKey[0] + '/' + initPriceKey2[0] + ",失败:"
                        + initPriceKey[1] + '/' + initPriceKey2[1]);

        return JSONTools.writeResponse(response, ajax);
    }

    /**
     * findDepot
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward findStorage(ActionMapping mapping, ActionForm form,
                                     HttpServletRequest request, HttpServletResponse response)
        throws ServletException
    {
        String id = request.getParameter("id");

        StorageBean bean = storageDAO.findVO(id);

        if (bean == null)
        {
            request.setAttribute(KeyConstant.ERROR_MESSAGE, "数据异常,请重新操作");

            return mapping.findForward("queryDepot");
        }

        List<DepotBean> depotList = depotDAO.queryCommonDepotBean();

        request.setAttribute("depotList", depotList);

        request.setAttribute("bean", bean);

        String update = request.getParameter("update");

        if ("1".equals(update))
        {
            return mapping.findForward("updateStorage");
        }

        return mapping.findForward("detailStorage");
    }

    /**
     * 产品转移查询
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward preForFindStorageToTransfer(ActionMapping mapping, ActionForm form,
                                                     HttpServletRequest request,
                                                     HttpServletResponse response)
        throws ServletException
    {
        CommonTools.saveParamers(request);

        String id = request.getParameter("id");

        String pname = request.getParameter("pname");

        StorageBean bean = storageDAO.findVO(id);

        if (bean == null)
        {
            request.setAttribute(KeyConstant.ERROR_MESSAGE, "数据异常,请重新操作");

            return mapping.findForward("queryDepot");
        }

        List<StorageBean> list = storageDAO.queryEntityBeansByFK(bean.getDepotpartId());

        request.setAttribute("list", list);

        request.setAttribute("bean", bean);

        List<StorageRelationVO> relations = storageRelationDAO.queryEntityVOsByFK(id);

        for (Iterator iterator = relations.iterator(); iterator.hasNext();)
        {
            StorageRelationVO storageRelationBean = (StorageRelationVO)iterator.next();

            String sname = storageRelationBean.getStafferName();

            if (StringTools.isNullOrNone(sname))
            {
                sname = "公共";
            }

            ProductBean product = productDAO.find(storageRelationBean.getProductId());

            if (product != null)
            {
                storageRelationBean.setProductName(product.getName()
                                                   + "["
                                                   + product.getCode()
                                                   + "]数量【"
                                                   + storageRelationBean.getAmount()
                                                   + "】 价格【"
                                                   + ElTools.formatNum(storageRelationBean
                                                       .getPrice()) + "】(" + sname + ")");
            }

            if ( !StringTools.isNullOrNone(pname))
            {
                if (product.getName().indexOf(pname) == -1)
                {
                    iterator.remove();
                }
            }

        }

        request.setAttribute("relations", relations);

        return mapping.findForward("transferStorageRelation");
    }

    /**
     * 默认储位的产品的转移
     * 
     * @param mapping
     * @param form
     * @param request
     * @param reponse
     * @return
     * @throws ServletException
     */
    public ActionForward transferStorageRelation(ActionMapping mapping, ActionForm form,
                                                 HttpServletRequest request,
                                                 HttpServletResponse reponse)
        throws ServletException
    {
        String sourceStorage = request.getParameter("id");

        String dirStorage = request.getParameter("dirStorage");

        String productId = request.getParameter("productId");

        String[] relations = productId.split("#");

        User user = Helper.getUser(request);

        try
        {
            productFacade.transferStorageRelation(user.getId(), sourceStorage, dirStorage,
                relations);
        }
        catch (MYException e)
        {
            request.setAttribute(KeyConstant.ERROR_MESSAGE, e.getErrorContent());

            return mapping.findForward("queryStorage");
        }

        request.setAttribute(KeyConstant.MESSAGE, "产品储位间转移成功");

        return mapping.findForward("queryStorage");
    }

    /**
     * 仓区里面的产品转移
     * 
     * @param mapping
     * @param form
     * @param request
     * @param reponse
     * @return
     * @throws ServletException
     */
    public ActionForward moveDepotpart(ActionMapping mapping, ActionForm form,
                                       HttpServletRequest request, HttpServletResponse response)
        throws ServletException
    {
        String destDepotpartId = request.getParameter("dest");

        String sourceRelationId = request.getParameter("sourceRelationId");

        String amount = request.getParameter("amounts");
        String apply = request.getParameter("apply");

        User user = Helper.getUser(request);

        try
        {
            String id = productFacade.transferStorageRelationInDepotpart(user.getId(),
                sourceRelationId, destDepotpartId, amount, apply);

            request.setAttribute(KeyConstant.MESSAGE, "产品仓区间转移成功,流水号:" + id);
        }
        catch (MYException e)
        {
            request.setAttribute(KeyConstant.ERROR_MESSAGE, e.getErrorContent());

            return mapping.findForward("error");
        }

        return preForMoveDepotpart(mapping, form, request, response);
    }
    
    /**
     * 
     * 
     * @param mapping
     * @param form
     * @param request
     * @param reponse
     * @return
     * @throws ServletException
     */
    public ActionForward queryMoveProduct(ActionMapping mapping, ActionForm form,
                                                HttpServletRequest request,
                                                HttpServletResponse response)
        throws ServletException
    {
    	
    	 ConditionParse condtion = new ConditionParse();

         condtion.addWhereStr();

         User user = Helper.getUser(request);

         if ( !LocationHelper.isVirtualLocation(user.getLocationId()))
         {
             // condtion.addCondition("StafferBean.locationId", "=", user.getLocationId());
         }

         // condtion.addIntCondition("StafferBean.status", "=", StafferConstant.STATUS_COMMON);

         ActionTools.processJSONQueryCondition(QUERYMOVEPRODUCT, request, condtion);

         String jsonstr = ActionTools.queryVOByJSONAndToString(QUERYMOVEPRODUCT, request, condtion,
             this.productChangeRecordDAO, new HandleResult<ProductChangeRecordVO>()
             {
                 public void handle(ProductChangeRecordVO vo)
                 {
                 }
             });

          return JSONTools.writeResponse(response, jsonstr);
      }

    
    /**
     * findMoveProduct
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward findMoveProduct(ActionMapping mapping, ActionForm form,
                                     HttpServletRequest request, HttpServletResponse response)
        throws ServletException
    {
        String flowid = request.getParameter("flowid");
        
        ConditionParse condtion = setRptQueryProductCondition(request);

        condtion.addCondition("ProductChangeRecordBean.flowid", "=", flowid);

        List<ProductChangeRecordVO> list = productChangeRecordDAO
            .queryEntityVOsByCondition(condtion);
        request.setAttribute("list", list);
        return mapping.findForward("detailMoveProduct");
    }
    
    
    
    /**
     * rptQueryProductInDepotpart(仓区内)
     * 
     * @param mapping
     * @param form
     * @param request
     * @param reponse
     * @return
     * @throws ServletException
     */
    public ActionForward rptQueryProductInDepotpart(ActionMapping mapping, ActionForm form,
                                                    HttpServletRequest request,
                                                    HttpServletResponse reponse)
        throws ServletException
    {
        CommonTools.saveParamers(request);

        List<StorageRelationVO> list = null;
        String flag = request.getParameter("flag");
        if(null != flag && flag.equals("1"))
        {
        	request.setAttribute("beanList", list);

            return mapping.findForward("rptQueryProductInDepotpart");
        }

        if (PageSeparateTools.isFirstLoad(request))
        {
            ConditionParse condtion = setRptQueryProductCondition(request);

            int total = storageRelationDAO.countVOByCondition(condtion.toString());

            PageSeparate page = new PageSeparate(total, PublicConstant.PAGE_COMMON_SIZE);

            PageSeparateTools.initPageSeparate(condtion, page, request, RPTQUERYPRODUCTINDEPOTPART);

            list = storageRelationDAO.queryEntityVOsByCondition(condtion, page);
        }
        else
        {
            PageSeparateTools.processSeparate(request, RPTQUERYPRODUCTINDEPOTPART);

            list = storageRelationDAO.queryEntityVOsByCondition(PageSeparateTools.getCondition(
                request, RPTQUERYPRODUCTINDEPOTPART), PageSeparateTools.getPageSeparate(request,
                RPTQUERYPRODUCTINDEPOTPART));
        }

        //for (StorageRelationVO vo : list)
        for (Iterator<StorageRelationVO> iterator = list.iterator(); iterator.hasNext();)
        {
        	StorageRelationVO vo = iterator.next();
        	
            if (StringTools.isNullOrNone(vo.getStafferName()))
            {
                vo.setStafferName("公共");
            }

            int sum = storageRelationManager.sumPreassignByStorageRelation(vo);

            vo.setMayAmount(sum);

            if (vo.getAmount() - sum > 0)
            {
                vo.setAmount(vo.getAmount() - sum);
            }
            else
            {
                // vo.setAmount(0);
            	iterator.remove();
            }
        }

        request.setAttribute("beanList", list);

        return mapping.findForward("rptQueryProductInDepotpart");
    }

    /**
     * 仓库内查询(合成产品,只能是良品仓区)
     * 
     * @param mapping
     * @param form
     * @param request
     * @param reponse
     * @return
     * @throws ServletException
     */
    public ActionForward rptQueryProductInDepot(ActionMapping mapping, ActionForm form,
                                                HttpServletRequest request,
                                                HttpServletResponse reponse)
        throws ServletException
    {
        CommonTools.saveParamers(request);

        int mtype = -1;
        
        int oldproduct = -1;
        
        String smtype = request.getParameter("mtype");
        
        String soldproduct = request.getParameter("oldproduct");
        
        if (!StringTools.isNullOrNone(smtype))
        {
        	mtype = MathTools.parseInt(smtype);
        }
        
        if (!StringTools.isNullOrNone(soldproduct))
        {
        	oldproduct = MathTools.parseInt(soldproduct);
        }
        
        List<StorageRelationVO> list = null;

        if (PageSeparateTools.isFirstLoad(request))
        {
            ConditionParse condtion = setRptQueryProductCondition3(request);

            int total = storageRelationDAO.countVOByCondition(condtion.toString());

            PageSeparate page = new PageSeparate(total, PublicConstant.PAGE_COMMON_SIZE);

            PageSeparateTools.initPageSeparate(condtion, page, request, RPTQUERYPRODUCTINDEPOT);

            list = storageRelationDAO.queryEntityVOsByCondition(condtion, page);
        }
        else
        {
            PageSeparateTools.processSeparate(request, RPTQUERYPRODUCTINDEPOT);

            list = storageRelationDAO.queryEntityVOsByCondition(PageSeparateTools.getCondition(
                request, RPTQUERYPRODUCTINDEPOT), PageSeparateTools.getPageSeparate(request,
                RPTQUERYPRODUCTINDEPOT));
        }

        for (StorageRelationVO vo : list)
        {
            if (StringTools.isNullOrNone(vo.getStafferName()))
            {
                vo.setStafferName("公共");
            }
            
            // 普通  非旧货
/*            if (mtype == 1 && (oldproduct > 0 && oldproduct != ProductConstant.PRODUCT_OLDGOOD))
            {
            	double price = vo.getPrice();
                
                ProductBean pbean = productDAO.find(vo.getProductId());
                
                if (null != pbean)
                {
                	// 普通  旧货 A*17%/117%
                	if ((pbean.getReserve4() == "0") 
                			|| (pbean.getReserve4() == "1" && pbean.getConsumeInDay() == ProductConstant.PRODUCT_OLDGOOD))
                	{
                		double newPrice = (price * 0.17)/1.17;
                		
                		vo.setPrice(newPrice);
                	}
                }
            }*/
        }

        request.setAttribute("beanList", list);

        return mapping.findForward("rptQueryProductInDepot");
    }

    /**
     * CORE 在仓库里面查询可销售的库存(这里可能根据销售区域进行过滤产品)不实现翻页
     * 
     * @param mapping
     * @param form
     * @param request
     * @param reponse
     * @return
     * @throws ServletException
     */
    public ActionForward rptQueryStorageRelationInDepot(ActionMapping mapping, ActionForm form,
                                                        HttpServletRequest request,
                                                        HttpServletResponse reponse)
        throws ServletException
    {
        CommonTools.saveParamers(request);
        
        // MANAGER 核心的产品库存查询过滤,管理类型处理
        String mtype = request.getParameter("mtype");
        
        String depotId = request.getParameter("depotId");

        String costMode = request.getParameter("costMode");
        
        String flag = request.getParameter("flag");
        
        request.setAttribute("flag", flag);
        
        List<StorageRelationVO> list = new ArrayList<StorageRelationVO>();

        String code = request.getParameter("code");
        
        String init = request.getParameter("init");
        
        String queryType = request.getParameter("queryType");
        
        // 查询仓库下的良品仓
        List<DepotpartBean> depotparList = depotpartDAO.queryOkDepotpartInDepot(depotId);

        request.setAttribute("depotparList", depotparList);
        
        // 当在销售单界面点击查找可卖商品时，默认是不查找的
        if (StringTools.isNullOrNone(code) && !StringTools.isNullOrNone(init))
        {
        	return mapping.findForward("rptQueryStorageRelationInDepot");
        }
        ConditionParse condtion = null;
        
        condtion = setRptQueryProductCondition2(request);

        // CORE 控制出售区域(某些产品只能在一定的区域下销售) 这个参数只有为空才有意义
        String sailLocation = request.getParameter("sailLocation");

        // int total = storageRelationDAO.countVOByCondition(condtion.toString());

        PageSeparate page = new PageSeparate(MAXLENGTH, MAXLENGTH);

        PageSeparateTools.initPageSeparate(condtion, page, request, RPTQUERYSTORAGERELATIONINDEPOT);

        List<StorageRelationVO> queryList = storageRelationDAO.queryEntityVOsByCondition(condtion,
            page);
        // 没有过滤直接查询前MAXLENGTH个(先屏蔽)
        if (StringTools.isNullOrNone(sailLocation) || true)
        {
            list.addAll(queryList);
        }
        else
        {
            createList(list, condtion, sailLocation, page, queryList, request);
        }
        for (Iterator iterator = list.iterator(); iterator.hasNext();)
        {
            StorageRelationVO vo = (StorageRelationVO)iterator.next();

            if (StringTools.isNullOrNone(vo.getStafferName()))
            {
                vo.setStafferName("公共");
            }

            int preassign = storageRelationManager.sumPreassignByStorageRelation(vo);

            // 可发数量
            vo.setMayAmount(vo.getAmount() - preassign);

            // 不显示低于0的数量
            if (vo.getMayAmount() <= 0)
            {
                iterator.remove();

                continue;
            }
            
            // 销售模式
            if ( !"1".equals(queryType))
            {
                // 通用产品先去掉
                if ((vo.getProductCode().equals(ProductConstant.OUT_COMMON_PRODUCTCODE)
                		|| vo.getProductCode().equals(ProductConstant.OUT_COMMON_MPRODUCTCODE))
                		&& vo.getDepotpartId().equals(ProductConstant.OUT_COMMON_DEPOTPART))
                {
                	iterator.remove();
                	
                	continue;
                }	
            }


            // 预支数量
            vo.setPreassignAmount(preassign);

            ProductBean product = productDAO.find(vo.getProductId());

            if (product != null)
            {
                // 设置批发价
                vo.setBatchPrice(product.getBatchPrice());

                vo.setCostPrice(product.getCost());
            }

            if ("1".equals(costMode))
            {
                // 原始显示
                vo.setAddPrice(vo.getPrice());
                
                vo.setInputPrice(vo.getPrice());
            }
            else
            {
                if (StringTools.isNullOrNone(vo.getStafferId()) || "0".equals(vo.getStafferId()))
                {
                    SailConfBean sailConf = sailConfigManager.findProductConf(Helper
                        .getStaffer(request), product);
                    
                    // 最新的成本(一致的) - 获取基础结算价，若没有找到则取产品中的sailPrice
                    double sailPrice = 0.0d;
                    
                    PriceConfigBean priceConfigBean = 
                    	priceConfigManager.getPriceConfigBean(product.getId(), Helper.getStaffer(request).getIndustryId(), Helper.getStaffer(request).getId());
                    
                    if (null == priceConfigBean)
                    {
                    	sailPrice = product.getSailPrice();
                    }
                    else
                    {
                    	sailPrice = priceConfigBean.getPrice();
                    	
                    	vo.setInputPrice(priceConfigBean.getMinPrice());
                    }

                    // 最新的成本(一致的)
                    vo
                        .setAddPrice(sailPrice
                                     * (1 + sailConf.getPratio() / 1000.0d + sailConf.getIratio() / 1000.0d));
                    
                    if (null == priceConfigBean || vo.getInputPrice() == 0)
                    {
                    	vo.setInputPrice(vo.getAddPrice());
                    }
                    
                }
                else
                {
                    // 私有库存直接是成本
                    vo.setAddPrice(vo.getPrice());
                    
                    vo.setInputPrice(vo.getPrice());
                }
            }
        }
        // 查询仓库下的良品仓
//        List<DepotpartBean> depotparList = depotpartDAO.queryOkDepotpartInDepot(depotId);
        // 如果是通用产品仓,单独查询出来
        if ( !"1".equals(queryType))
        {
        	if (depotId.equals(ProductConstant.OUT_COMMON_DEPOT))
            {
                condtion.clear();
                
                condtion.addWhereStr();
                
                condtion.addCondition("StorageRelationBean.locationId", "=", ProductConstant.OUT_COMMON_DEPOT);
                
                condtion.addCondition("StorageRelationBean.depotpartId", "=", ProductConstant.OUT_COMMON_DEPOTPART);
                
                condtion.addCondition("StorageRelationBean.priceKey", "=", "0");
                
                condtion.addIntCondition("DepotpartBean.type", "=", 0);
                
                if (!StringTools.isNullOrNone(mtype))
                {
                	if (OATools.getManagerType(mtype) == PublicConstant.MANAGER_TYPE_COMMON)
                	{
                		condtion.addCondition("ProductBean.code", "=", ProductConstant.OUT_COMMON_PRODUCTCODE);
                	}
                	else
                	{
                		condtion.addCondition("ProductBean.code", "=", ProductConstant.OUT_COMMON_MPRODUCTCODE);
                	}
                }
                else
                {
                	condtion.addCondition(" and ProductBean.code in ('191175852','191165735')");
                }

                List<StorageRelationVO> queryList1 = storageRelationDAO.queryEntityVOsByCondition(condtion);
                
                if (!ListTools.isEmptyOrNull(queryList1))
                {
                	for (StorageRelationVO each : queryList1)
                	{
                		each.setAmount(9999);
                		
                		list.add(each);
                	}
                }
            }
        }

        request.setAttribute("beanList", list);

        request.setAttribute("depotparList", depotparList);
        if (OATools.isChangeToV5())
        {
            return mapping.findForward("rptQueryStorageRelationInDepot");
        }
        else
        {
            return mapping.findForward("rptQueryStorageRelationInDepot_bak");
        }
    }
    
    
    /**
     * 调拨申请功能
     * 
     * @param mapping
     * @param form
     * @param request
     * @param reponse
     * @return
     * @throws ServletException
     */
    public ActionForward rptQueryStorageRelationInDepotByDiaobo(ActionMapping mapping, ActionForm form,
                                                        HttpServletRequest request,
                                                        HttpServletResponse reponse)
        throws ServletException
    {
        CommonTools.saveParamers(request);

        String depotId = request.getParameter("depotId");

        String costMode = request.getParameter("costMode");
        
        String flag = request.getParameter("flag");
        
        request.setAttribute("flag", flag);

        List<StorageRelationVO> list = new ArrayList<StorageRelationVO>();

        String code = request.getParameter("code");
        
        String init = request.getParameter("init");
        
        // 查询仓库下的良品仓
        List<DepotpartBean> depotparList = depotpartDAO.queryOkDepotpartInDepot(depotId);

        request.setAttribute("depotparList", depotparList);
        
        ConditionParse condtion = null;
        
        condtion = setRptQueryProductCondition2(request);

        // CORE 控制出售区域(某些产品只能在一定的区域下销售) 这个参数只有为空才有意义
        String sailLocation = request.getParameter("sailLocation");

        // int total = storageRelationDAO.countVOByCondition(condtion.toString());

        PageSeparate page = new PageSeparate(MAXLENGTH, MAXLENGTH);

        PageSeparateTools.initPageSeparate(condtion, page, request, RPTQUERYSTORAGERELATIONINDEPOT);

        List<StorageRelationVO> queryList = storageRelationDAO.queryEntityVOsByCondition(condtion,
            page);

        // 没有过滤直接查询前MAXLENGTH个(先屏蔽)
        if (StringTools.isNullOrNone(sailLocation) || true)
        {
            list.addAll(queryList);
        }
        else
        {
            createList(list, condtion, sailLocation, page, queryList, request);
        }

        for (Iterator iterator = list.iterator(); iterator.hasNext();)
        {
            StorageRelationVO vo = (StorageRelationVO)iterator.next();

            if (StringTools.isNullOrNone(vo.getStafferName()))
            {
                vo.setStafferName("公共");
            }

            int preassign = storageRelationManager.sumPreassignByStorageRelation(vo);

            // 可发数量
            vo.setMayAmount(vo.getAmount() - preassign);

            // 不显示低于0的数量
            if (vo.getMayAmount() <= 0)
            {
                iterator.remove();

                continue;
            }

            // 预支数量
            vo.setPreassignAmount(preassign);

            ProductBean product = productDAO.find(vo.getProductId());

            if (product != null)
            {
                // 设置批发价
                vo.setBatchPrice(product.getBatchPrice());

                vo.setCostPrice(product.getCost());
            }

            if ("1".equals(costMode))
            {
                // 原始显示
                vo.setAddPrice(vo.getPrice());
                
                vo.setInputPrice(vo.getPrice());
            }
            else
            {
                if (StringTools.isNullOrNone(vo.getStafferId()) || "0".equals(vo.getStafferId()))
                {
                    SailConfBean sailConf = sailConfigManager.findProductConf(Helper
                        .getStaffer(request), product);
                    
                    // 最新的成本(一致的) - 获取基础结算价，若没有找到则取产品中的sailPrice
                    double sailPrice = 0.0d;
                    
                    PriceConfigBean priceConfigBean = 
                    	priceConfigManager.getPriceConfigBean(product.getId(), Helper.getStaffer(request).getIndustryId(), Helper.getStaffer(request).getId());
                    
                    if (null == priceConfigBean)
                    {
                    	sailPrice = product.getSailPrice();
                    }
                    else
                    {
                    	sailPrice = priceConfigBean.getPrice();
                    	
                    	vo.setInputPrice(priceConfigBean.getMinPrice());
                    }

                    // 最新的成本(一致的)
                    vo
                        .setAddPrice(sailPrice
                                     * (1 + sailConf.getPratio() / 1000.0d + sailConf.getIratio() / 1000.0d));
                    if (null == priceConfigBean)
                    {
                    	vo.setInputPrice(vo.getAddPrice());
                    }
                }
                else
                {
                    // 私有库存直接是成本
                    vo.setAddPrice(vo.getPrice());
                    
                    vo.setInputPrice(vo.getPrice());
                }
            }
        }

        // 查询仓库下的良品仓
//        List<DepotpartBean> depotparList = depotpartDAO.queryOkDepotpartInDepot(depotId);

        request.setAttribute("beanList", list);

        request.setAttribute("depotparList", depotparList);

        return mapping.findForward("rptQueryStorageRelationInDepotByDiaobo");
    }
    
    /**
     * exportStorageRelation(含价格的)
     * 
     * @param mapping
     * @param form
     * @param request
     * @param reponse
     * @return
     * @throws ServletException
     */
    public ActionForward exportStorageRelation(ActionMapping mapping, ActionForm form,
                                               HttpServletRequest request,
                                               HttpServletResponse reponse)
        throws ServletException
    {
        OutputStream out = null;

        String filenName = "Product_" + TimeTools.now("MMddHHmmss") + ".csv";

        reponse.setContentType("application/x-dbf");

        reponse.setHeader("Content-Disposition", "attachment; filename=" + filenName);

        WriteFile write = null;

        try
        {
            out = reponse.getOutputStream();

            ConditionParse condtion = new ConditionParse();

            List<DepotVO> lList = depotDAO.listEntityVOs();

            write = WriteFileFactory.getMyTXTWriter();

            write.openFile(out);

            write.writeLine("日期,事业部,仓库,仓区,仓区属性,储位,产品名称,产品编码,产品数量,产品价格,归属,销售类型");

            String now = TimeTools.now("yyyy-MM-dd");

            for (DepotVO locationBean : lList)
            {
                condtion.clear();

                condtion.addCondition("StorageRelationBean.locationId", "=", locationBean.getId());

                condtion.addIntCondition("StorageRelationBean.amount", ">", 0);

                List<StorageRelationVO> list = storageRelationDAO
                    .queryEntityVOsByCondition(condtion);

                for (StorageRelationVO each : list)
                {
                    if (each.getAmount() > 0)
                    {
                        String typeName = DefinedCommon.getValue("depotpartType", each
                            .getDepotpartType());

                        String code = each.getProductCode();

                        if (code.length() > 10)
                        {
                            code = "A" + code;
                        }

                        String sname = each.getStafferName();

                        if (StringTools.isNullOrNone(sname))
                        {
                            sname = "公共";
                        }
                        
                        String proSailtype = "";
                        if(each.getProductSailType() == 0)
                        {
                        	proSailtype = "自有";
                        }
                        else if(each.getProductSailType() == 1)
                        {
                        	proSailtype = "经销";
                        }
                        else if(each.getProductSailType() == 2)
                        {
                        	proSailtype = "定制";
                        }
                        else
                        {
                        	proSailtype = "其他";
                        }

                        write.writeLine(now
                                        + ','
                                        + StringTools.getLineString(locationBean.getIndustryName())
                                        + ','
                                        + locationBean.getName()
                                        + ','
                                        + each.getDepotpartName()
                                        + ','
                                        + typeName
                                        + ','
                                        + each.getStorageName()
                                        + ','
                                        + each.getProductName().replaceAll(",", " ").replaceAll(
                                            "\r\n", "") + ',' + code + ','
                                        + String.valueOf(each.getAmount()) + ','
                                        + MathTools.formatNum(each.getPrice()) + ',' + sname
                                        +','+proSailtype
                        );
                    }
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
     * exportStorageRelation2(不含价格的)
     * 
     * @param mapping
     * @param form
     * @param request
     * @param reponse
     * @return
     * @throws ServletException
     */
    public ActionForward exportStorageRelation2(ActionMapping mapping, ActionForm form,
                                                HttpServletRequest request,
                                                HttpServletResponse reponse)
        throws ServletException
    {
        OutputStream out = null;

        // String depotartId = request.getParameter("depotartId");

        String filenName = "Product_" + TimeTools.now("MMddHHmmss") + ".csv";

        reponse.setContentType("application/x-dbf");

        reponse.setHeader("Content-Disposition", "attachment; filename=" + filenName);

        WriteFile write = null;

        try
        {
            out = reponse.getOutputStream();

            List<DepotVO> lList = depotDAO.listEntityVOs();

            write = WriteFileFactory.getMyTXTWriter();

            write.openFile(out);

            write.writeLine("日期,事业部,仓库,仓区,仓区属性,产品名称,产品编码,产品数量,归属,销售类型");

            String now = TimeTools.now("yyyy-MM-dd");

            for (DepotVO locationBean : lList)
            {
                List<StorageRelationVO> list = storageRelationDAO
                    .queryStorageRelationWithoutPrice(locationBean.getId());

                for (StorageRelationVO each : list)
                {
                    if (each.getAmount() > 0)
                    {
                        String typeName = DefinedCommon.getValue("depotpartType", each
                            .getDepotpartType());

                        String code = each.getProductCode();

                        if (code.length() > 10)
                        {
                            code = "A" + code;
                        }

                        String sname = "公共";

                        if ( !StringTools.isNullOrNone(each.getStafferId())
                            && !"0".equals(each.getStafferId()))
                        {

                            StafferBean sb = stafferDAO.find(each.getStafferId());

                            if (sb != null)
                            {
                                sname = sb.getName();
                            }
                        }
                        
                        String proSailtype = "";
                        
                        if(each.getProductSailType() == 0)
                        {
                        	proSailtype = "自有";
                        }
                        else if(each.getProductSailType() == 1)
                        {
                        	proSailtype = "经销";
                        }
                        else if(each.getProductSailType() == 2)
                        {
                        	proSailtype = "定制";
                        }
                        else
                        {
                        	proSailtype = "其他";
                        }


                        write.writeLine(now
                                        + ','
                                        + StringTools.getLineString(locationBean.getIndustryName())
                                        + ','
                                        + locationBean.getName()
                                        + ','
                                        + each.getDepotpartName()
                                        + ','
                                        + typeName
                                        + ','
                                        + each.getProductName().replaceAll(",", " ").replaceAll(
                                            "\r\n", "") + ',' + code + ','
                                        + String.valueOf(each.getTotal()) + ',' + sname + ',' + proSailtype);
                    }
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
     * 导出仓区
     * 
     * @param mapping
     * @param form
     * @param request
     * @param reponse
     * @return
     * @throws ServletException
     */
    public ActionForward exportStorageRelation3(ActionMapping mapping, ActionForm form,
                                                HttpServletRequest request,
                                                HttpServletResponse reponse)
        throws ServletException
    {
        OutputStream out = null;

        String depotartId = request.getParameter("depotartId");

        DepotpartVO depotpartBean = depotpartDAO.findVO(depotartId);

        String filenName = "Product_" + TimeTools.now("MMddHHmmss") + ".csv";

        reponse.setContentType("application/x-dbf");

        reponse.setHeader("Content-Disposition", "attachment; filename=" + filenName);

        WriteFile write = null;

        try
        {
            out = reponse.getOutputStream();

            write = WriteFileFactory.getMyTXTWriter();

            write.openFile(out);

            write.writeLine("日期,仓库,仓区,仓区属性,产品名称,产品编码,产品数量,归属");

            String now = TimeTools.now("yyyy-MM-dd");

            List<StorageRelationVO> list = storageRelationDAO
                .queryStorageRelationWithoutPrice(depotpartBean.getLocationId());

            for (StorageRelationVO each : list)
            {
                if (each.getAmount() > 0 && each.getDepotpartId().equals(depotartId))
                {
                    String typeName = DefinedCommon.getValue("depotpartType", each
                        .getDepotpartType());

                    String code = each.getProductCode();

                    if (code.length() > 10)
                    {
                        code = "A" + code;
                    }

                    String sname = "公共";

                    if ( !StringTools.isNullOrNone(each.getStafferId())
                        && !"0".equals(each.getStafferId()))
                    {

                        StafferBean sb = stafferDAO.find(each.getStafferId());

                        if (sb != null)
                        {
                            sname = sb.getName();
                        }
                    }

                    write.writeLine(now
                                    + ','
                                    + depotpartBean.getLocationName()
                                    + ','
                                    + each.getDepotpartName()
                                    + ','
                                    + typeName
                                    + ','
                                    + each.getProductName().replaceAll(",", " ").replaceAll("\r\n",
                                        "") + ',' + code + ',' + String.valueOf(each.getTotal())
                                    + ',' + sname);
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
     * createList
     * 
     * @param list
     * @param condtion
     * @param sailLocation
     * @param page
     * @param queryList
     */
    private void createList(List<StorageRelationVO> list, ConditionParse condtion,
                            String sailLocation, PageSeparate page,
                            List<StorageRelationVO> queryList, HttpServletRequest request)
    {
        StafferBean user = Helper.getStaffer(request);

        // 如果没有行业直接退出
        if (StringTools.isNullOrNone(user.getIndustryId()))
        {
            return;
        }

        for (StorageRelationVO each : queryList)
        {
            if (hasInSailLocation(each.getProductId(), sailLocation, user))
            {
                list.add(each);

                if (list.size() >= MAXLENGTH)
                {
                    break;
                }
            }
        }

        int index = 0;

        while (page.hasNextPage())
        {
            page.nextPage();

            index++ ;

            if (index > 30)
            {
                break;
            }

            queryList = storageRelationDAO.queryEntityVOsByCondition(condtion, page);

            for (StorageRelationVO each : queryList)
            {
                if (hasInSailLocation(each.getProductId(), sailLocation, user))
                {
                    list.add(each);

                    if (list.size() >= MAXLENGTH)
                    {
                        return;
                    }
                }
            }
        }
    }

    /**
     * hasInSailLocation
     * 
     * @param productId
     * @param sailLocation
     * @param user
     * @return
     */
    private boolean hasInSailLocation(String productId, String sailLocation, StafferBean user)
    {
        List<ProductVSLocationBean> list = productVSLocationDAO.queryEntityBeansByFK(productId);

        // 如果没有设置就是全部可以销售
        if (list.size() == 0)
        {
            return true;
        }

        for (ProductVSLocationBean productVSLocationBean : list)
        {
            if (productVSLocationBean.getLocationId().equals(user.getIndustryId()))
            {
                return true;
            }
        }

        return false;
    }

    private ConditionParse setRptQueryProductCondition(HttpServletRequest request)
    {
        String name = request.getParameter("name");

        String code = request.getParameter("code");

        String stafferId = request.getParameter("stafferId");

        String storageName = request.getParameter("storageName");

        String depotpartId = request.getParameter("depotpartId");

        ConditionParse condtion = new ConditionParse();

        condtion.addWhereStr();

        if ( !StringTools.isNullOrNone(name))
        {
            condtion.addCondition("ProductBean.name", "like", name);
        }

        if ( !StringTools.isNullOrNone(code))
        {
            condtion.addCondition("ProductBean.code", "like", code);
        }

        // storageName
        if ( !StringTools.isNullOrNone(storageName))
        {
            condtion.addCondition("StorageBean.name", "like", storageName);
        }

        if ( !StringTools.isNullOrNone(depotpartId))
        {
            condtion.addCondition("StorageRelationBean.depotpartId", "=", depotpartId);
        }

        if ( !StringTools.isNullOrNone(stafferId))
        {
            condtion.addCondition("StorageRelationBean.stafferId", "=", stafferId);
        }

        return condtion;
    }

    /**
     * setRptQueryProductCondition3
     * 
     * @param request
     * @return
     */
    private ConditionParse setRptQueryProductCondition3(HttpServletRequest request)
    {
        String name = request.getParameter("name");

        String code = request.getParameter("code");

        String stafferId = request.getParameter("stafferId");

        String storageName = request.getParameter("storageName");

        String depotpartName = request.getParameter("depotpartName");

        String depotpartId = request.getParameter("depotpartId");

        String locationId = request.getParameter("locationId");

        ConditionParse condtion = new ConditionParse();

        condtion.addWhereStr();

        if ( !StringTools.isNullOrNone(name))
        {
            condtion.addCondition("ProductBean.name", "like", name);
        }

        if ( !StringTools.isNullOrNone(code))
        {
            condtion.addCondition("ProductBean.code", "like", code);
        }

        // storageName
        if ( !StringTools.isNullOrNone(storageName))
        {
            condtion.addCondition("StorageBean.name", "like", storageName);
        }

        if ( !StringTools.isNullOrNone(depotpartName))
        {
            condtion.addCondition("DepotpartBean.name", "like", depotpartName);
        }

        if ( !StringTools.isNullOrNone(depotpartId))
        {
            condtion.addCondition("StorageRelationBean.depotpartId", "=", depotpartId);
        }

        if ( !StringTools.isNullOrNone(stafferId))
        {
            condtion.addCondition("StorageRelationBean.stafferId", "=", stafferId);
        }

        // 只能是OK仓区
        condtion.addIntCondition("DepotpartBean.type", "=", DepotConstant.DEPOTPART_TYPE_OK);

        condtion.addCondition("StorageRelationBean.locationId", "=", locationId);
        
        condtion.addCondition("StorageRelationBean.amount", ">", 0);

        return condtion;
    }

    /**
     * 查询仓库下的可用库存
     * 
     * @param request
     * @return
     */
    private ConditionParse setRptQueryProductCondition2(HttpServletRequest request)
    {
        String name = request.getParameter("name");

        String code = request.getParameter("code");

        User user = Helper.getUser(request);

        // 公共的就是0,私有的就是自己的ID
        String stafferId = request.getParameter("stafferId");

        String depotpartId = request.getParameter("depotpartId");

        String depotId = request.getParameter("depotId");

        // 0/null:销售单 1:入库单
        String queryType = request.getParameter("queryType");

        ConditionParse condtion = new ConditionParse();

        condtion.addWhereStr();

        condtion.addCondition("StorageRelationBean.locationId", "=", depotId);

        // 只能是OK仓区
        condtion.addIntCondition("DepotpartBean.type", "=", DepotConstant.DEPOTPART_TYPE_OK);

        condtion.addIntCondition("StorageRelationBean.amount", ">", 0);

        if ( !StringTools.isNullOrNone(name))
        {
            condtion.addCondition("ProductBean.name", "like", name);
        }

        if ( !StringTools.isNullOrNone(code))
        {
            condtion.addCondition("ProductBean.code", "like", code);
        }
        
        // 只显示正常态产品
        condtion.addIntCondition("ProductBean.status", "=", ProductConstant.STATUS_COMMON);

        if ( !StringTools.isNullOrNone(depotpartId))
        {
            condtion.addCondition("StorageRelationBean.depotpartId", "=", depotpartId);
        }

        if ( !"1".equals(queryType))
        {
            if (StringTools.isNullOrNone(stafferId))
            {
                condtion
                    .addCondition("and (StorageRelationBean.stafferId = '0' or StorageRelationBean.stafferId = '"
                                  + user.getStafferId() + "')");
            }
            else
            {
                condtion.addCondition("StorageRelationBean.stafferId", "=", stafferId);
            }
        }

        // MANAGER 核心的产品库存查询过滤,管理类型处理
        String mtype = request.getParameter("mtype");

        // 如果是普通的只能买普通的
        if (OATools.getManagerFlag() && OATools.isCommon(mtype))
        {
            condtion.addCondition("ProductBean.reserve4", "=", mtype);
        }

        // 过滤销售类型和产品类型
        String sailType = request.getParameter("sailType");

        String productType = request.getParameter("productType");

        // 纳税实体
        String dutyId = request.getParameter("dutyId");

        String invoiceId = request.getParameter("invoiceId");

        // 这里是存在销售类型的时候就可以使用
        if ( !StringTools.isNullOrNone(sailType) && !StringTools.isNullOrNone(productType))
        {
            condtion.addCondition("ProductBean.sailType", "=", sailType);
            condtion.addCondition("ProductBean.type", "=", productType);
        }
        else
        {
            if ( !StringTools.isNullOrNone(dutyId) && !StringTools.isNullOrNone(invoiceId))
            {
                InvoiceBean invoiceBean = invoiceDAO.find(invoiceId);

                DutyBean duty = dutyDAO.find(dutyId);

                int ratio = (int) (invoiceBean.getVal() * 10);

                ConditionParse sailCondtion = new ConditionParse();

                sailCondtion.addWhereStr();

                sailCondtion.addIntCondition("finType" + duty.getType(), "=",
                    SailConstant.SAILCONFIG_FIN_YES);

                sailCondtion.addIntCondition("ratio" + duty.getType(), "=", ratio);

                sailCondtion.addCondition("group by sailType, productType");

                // 销售类型和销售品类的组合
                List<SailConfigBean> configList = sailConfigDAO
                    .queryEntityBeansByCondition(sailCondtion);

                condtion.addCondition("and (");

                for (Iterator iterator = configList.iterator(); iterator.hasNext();)
                {
                    SailConfigBean sailConfigBean = (SailConfigBean)iterator.next();

                    condtion.addCondition("(ProductBean.type = " + sailConfigBean.getProductType()
                                          + " and ProductBean.sailType = "
                                          + sailConfigBean.getSailType() + ")");

                    if (iterator.hasNext())
                    {
                        condtion.addCondition("or");
                    }
                }

                condtion.addCondition(")");
            }
        }

        return condtion;
    }

    /**
     * preForaddGSOut
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward preForAddGSOut(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
	throws ServletException
	{
    	preForGSOutInner(request);
    	
    	request.setAttribute("current", TimeTools.now("yyyy-MM-dd"));
    	
    	return mapping.findForward("addGSOut");
	}

	private void preForGSOutInner(HttpServletRequest request)
	{
		List<DepotBean> list = depotDAO.queryCommonDepotBean();

        List<DepotpartBean> depotpartList = new ArrayList<DepotpartBean>();

        for (DepotBean depotBean : list)
        {
            // 只查询OK仓区的
            List<DepotpartBean> depotList = depotpartDAO.queryOkDepotpartInDepot(depotBean.getId());

            for (DepotpartBean depotpartBean : depotList)
            {
                depotpartBean.setName(depotBean.getName() + " --> " + depotpartBean.getName());
            }

            depotpartList.addAll(depotList);
        }

        request.setAttribute("depotList", list);

        request.setAttribute("depotpartList", depotpartList);

        JSONArray object = new JSONArray(depotpartList, false);

        request.setAttribute("depotpartListStr", object.toString());
	}
    
    /**
     * addGSOut
     * 金银料出入库
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward addGSOut(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
	throws ServletException
	{
        GSOutBean bean = new GSOutBean();
        
        String save = request.getParameter("save");

        try
        {
        	User user = Helper.getUser(request);
        	
        	BeanUtil.getBean(bean, request);
            
        	setGSOut(request, bean, StorageConstant.STORAGEAPPLY_GS_OUT);
        	
            bean.setStafferId(user.getStafferId());
            bean.setOutTime(TimeTools.now_short());
            bean.setLogTime(TimeTools.now());
            
            if ("0".equals(save))
            	bean.setStatus(StorageConstant.GSOUT_STATUS_SAVE);
            else
            	bean.setStatus(StorageConstant.GSOUT_STATUS_SUBMIT);

            productFacade.addGSOut(user.getId(), bean);

            request.setAttribute(KeyConstant.MESSAGE, "成功操作");
        }
        catch (MYException e)
        {
            _logger.warn(e, e);

            request.setAttribute(KeyConstant.ERROR_MESSAGE, "操作失败:" + e.getMessage());
        }

        CommonTools.removeParamers(request);

		RequestTools.actionInitQuery(request);

		request.setAttribute("queryType", 1);
		
        return queryGSOut(mapping, form, request, response);
    }
    
    private void setGSOut(HttpServletRequest request, GSOutBean bean, int type)
    throws MYException
	{
	    int gtotal = 0;
	    int stotal = 0;
	
	    String[] srcDepots = request.getParameterValues("srcDepot");
	    String[] srcDepotparts = request.getParameterValues("srcDepotpart");
	    String[] srcProductIds = request.getParameterValues("srcProductId");
	    String[] srcAmounts = request.getParameterValues("srcAmount");
	    String[] goldWeights = request.getParameterValues("goldWeight");
	    String[] silverWeights = request.getParameterValues("silverWeight");
	    String[] srcPrices = request.getParameterValues("srcPrice");
	    String[] goldPrices = request.getParameterValues("goldPrice");
	    String[] silverPrices = request.getParameterValues("silverPrice");
	    
	    
	    List<GSOutItemBean> itemList = new ArrayList<GSOutItemBean>();
	
	    for (int i = 0; i < srcProductIds.length; i++ )
	    {
	        if (StringTools.isNullOrNone(srcProductIds[i]))
	        {
	            continue;
	        }
	
	        GSOutItemBean each = new GSOutItemBean();
	        
	        each.setAmount(CommonTools.parseInt(srcAmounts[i]));
	        each.setDeportId(srcDepots[i]);
	        each.setDepotpartId(srcDepotparts[i]);
	        each.setLogTime(bean.getLogTime());
	        each.setProductId(srcProductIds[i]);
	        each.setGoldWeight(CommonTools.parseInt(goldWeights[i]));
	        each.setSilverWeight(CommonTools.parseInt(silverWeights[i]));
	        each.setGoldPrice(CommonTools.parseFloat(goldPrices[i]));
	        each.setSilverPrice(CommonTools.parseFloat(silverPrices[i]));
	        
	        if (type == StorageConstant.STORAGEAPPLY_GS_OUT)
		    {
	        	each.setPrice(CommonTools.parseFloat(srcPrices[i]));
		        
	        	double cha = Math.abs(each.getPrice() * each.getAmount() - 
	        			(each.getGoldPrice() * each.getGoldWeight() + each.getSilverPrice() * each.getSilverWeight()));
	        	
	        	_logger.info("====cha===" + cha);
	        	
		        if (Math.round(cha) > 1)
	            {
	            	throw new MYException("产品的成本[%.2f]应与金银料成本之和[%.2f]一样",each.getPrice() * each.getAmount(),
	            			each.getGoldPrice() * each.getGoldWeight() + each.getSilverPrice() * each.getSilverWeight());
	            }
		    }else{
		    	each.setPrice(each.getGoldPrice() * each.getGoldWeight()  + each.getSilverPrice() * each.getSilverWeight());
		    }
	        
	        itemList.add(each);
	
	        gtotal += each.getGoldWeight();
	        stotal += each.getSilverWeight();
	    }
	
	    bean.setItemList(itemList);
	
	    bean.setGtotal(gtotal);
	    bean.setStotal(stotal);
	 }
    
    /**
     * updateGSOut
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward updateGSOut(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
	throws ServletException
	{
        GSOutBean bean = new GSOutBean();
        
        String save = request.getParameter("save");

        try
        {
        	User user = Helper.getUser(request);
        	
        	BeanUtil.getBean(bean, request);
            
        	setGSOut(request, bean, StorageConstant.STORAGEAPPLY_GS_OUT);
        	
            bean.setStafferId(user.getStafferId());
            bean.setOutTime(TimeTools.now_short());
            bean.setLogTime(TimeTools.now());
            
            if ("0".equals(save))
            	bean.setStatus(StorageConstant.GSOUT_STATUS_SAVE);
            else
            	bean.setStatus(StorageConstant.GSOUT_STATUS_SUBMIT);

            productFacade.updateGSOut(user.getId(), bean);

            request.setAttribute(KeyConstant.MESSAGE, "成功操作");
        }
        catch (MYException e)
        {
            _logger.warn(e, e);

            request.setAttribute(KeyConstant.ERROR_MESSAGE, "操作失败:" + e.getMessage());
        }

        CommonTools.removeParamers(request);

		RequestTools.actionInitQuery(request);

		request.setAttribute("queryType", 1);
		
        return queryGSOut(mapping, form, request, response);
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
    public ActionForward delGSOut(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
	throws ServletException
	{
    	String id = request.getParameter("id");
    	
    	try{
    		
    		User user = Helper.getUser(request);
    		
    		productFacade.deleteGSOut(user.getId(), id);
    		
    		request.setAttribute(KeyConstant.MESSAGE, "成功操作");
    		
    	}catch(MYException e)
    	{
            _logger.warn(e, e);

            request.setAttribute(KeyConstant.ERROR_MESSAGE, "操作失败:" + e.getMessage());
    	}
    	
    	request.setAttribute("queryType", 1);
    	
    	return queryGSOut(mapping, form, request, response);
	}
    
    /**
     * findGSOut
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward findGSOut(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
	throws ServletException
	{
    	CommonTools.saveParamers(request);
    	
    	String id = RequestTools.getValueFromRequest(request, "id");

        String flow = RequestTools.getValueFromRequest(request, "flow");

        GSOutVO vo = gsOutDAO.findVO(id);

		if (vo == null)
		{
			return ActionTools.toError("数据异常,请重新操作", "queryGSOut", mapping, request);
		}

         vo.setItemVOList(gsOutItemDAO.queryEntityVOsByFK(id));
         
         request.setAttribute("bean", vo);
    	
         if ("1".equals(flow))
         {
        	 preForGSOutInner(request);
             
             return mapping.findForward("updateGSOut");
         }
         
         if ("2".equals(flow))
         {
        	 preForGSOutInner(request);
             
        	 List<GSOutItemVO> voList = vo.getItemVOList();
        	 
        	 if (!ListTools.isEmptyOrNull(voList))
        	 {
        		 request.setAttribute("firstItem", voList.get(0));
        	 }
        	 
             return mapping.findForward("handleGSIn");
         }
         
         List<FinanceBean> financeBeanList = financeDAO.queryRefFinanceItemByRefId(id);
     	
     	if (financeBeanList.size() > 0)
     	{
     		vo.setOtherId(financeBeanList.get(0).getId());
     	}
         
         // log
    	 List<FlowLogBean> logList = flowLogDAO.queryEntityBeansByFK(id);

         List<FlowLogVO> logsVO = new ArrayList<FlowLogVO>();

         for (FlowLogBean flowLogBean : logList)
         {
             logsVO.add(getFlowLogVO(flowLogBean));
         }
         
         request.setAttribute("logList", logsVO);
         
         return mapping.findForward("detailGSOut");
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

        vo.setPreStatusName(DefinedCommon.getValue("gsOutStatus", vo.getPreStatus()));

        vo.setAfterStatusName(DefinedCommon.getValue("gsOutStatus", vo.getAfterStatus()));

        return vo;
    }
    
    /**
     * queryGSOut
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward queryGSOut(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
	throws ServletException
	{
		User user = (User) request.getSession().getAttribute("user");

		request.getSession().setAttribute("exportKey", QUERYGSOUT);
		
		List<GSOutVO> list = null;

		CommonTools.saveParamers(request);

		try
		{
			if (OldPageSeparateTools.isFirstLoad(request))
			{
				ConditionParse condtion = getQueryCondition(request,
						user);

				int tatol = gsOutDAO.countVOByCondition(condtion
						.toString());

				PageSeparate page = new PageSeparate(tatol,
						PublicConstant.PAGE_SIZE - 5);

				OldPageSeparateTools.initPageSeparate(condtion, page, request,
						QUERYGSOUT);

				list = gsOutDAO.queryEntityVOsByCondition(condtion, page);
			}
			else
			{
				OldPageSeparateTools.processSeparate(request,
						QUERYGSOUT);

				list = gsOutDAO.queryEntityVOsByCondition(
						OldPageSeparateTools.getCondition(request,
								QUERYGSOUT), OldPageSeparateTools
								.getPageSeparate(request, QUERYGSOUT));
			}
		}
		catch (Exception e)
		{
			request.setAttribute(KeyConstant.ERROR_MESSAGE, "查询单据失败");

			_logger.error(e, e);

			return mapping.findForward("error");
		}

		request.setAttribute("resultList", list);

		return mapping.findForward("queryGSOut");
	}
    
    /**
     * 
     * @param request
     * @param user
     * @return
     */
    private ConditionParse getQueryCondition(HttpServletRequest request, User user)
	{
		ConditionParse condtion = new ConditionParse();

		condtion.addWhereStr();

		String outTime = request.getParameter("outTime");

		String outTime1 = request.getParameter("outTime1");

		if (!StringTools.isNullOrNone(outTime))
		{
			condtion.addCondition("GSOutBean.logTime", ">=", outTime);
		}
		else
		{
			condtion.addCondition("GSOutBean.logTime", ">=",
					TimeTools.now_short(-30));

			request.setAttribute("outTime", TimeTools.now_short(-30));
		}

		if (!StringTools.isNullOrNone(outTime1))
		{
			condtion.addCondition("GSOutBean.logTime", "<=", outTime1);
		}
		else
		{
			condtion.addCondition("GSOutBean.logTime", "<=",
					TimeTools.now_short(1));

			request.setAttribute("outTime1", TimeTools.now_short(1));
		}

		String fullId = request.getParameter("gsOutId");

		if (!StringTools.isNullOrNone(fullId))
		{
			condtion.addCondition("GSOutBean.id", "like", fullId.trim());
		}

		String status = request.getParameter("status");

		if (!StringTools.isNullOrNone(status))
		{
			condtion.addIntCondition("GSOutBean.status", "=", status);
		}

		String type = request.getParameter("type");

		if (!StringTools.isNullOrNone(type))
		{
			condtion.addIntCondition("GSOutBean.type", "=", type);
		}
		
		String stafferName = request.getParameter("stafferName");

		if (!StringTools.isNullOrNone(stafferName))
		{
			condtion.addCondition("StafferBean.name", "like", stafferName);
		}
		
		// 权限校验
		String queryType = RequestTools.getValueFromRequest(request,
				"queryType");

		if ("1".equals(queryType))
		{
			// 只能查询自己的
			condtion.addCondition("GSOutBean.STAFFERID", "=",
					user.getStafferId());
		}
		// 查询审批的
		else if ("2".equals(queryType))
		{
			if (OldPageSeparateTools.isMenuLoad(request))
			{
				condtion.addIntCondition("GSOutBean.status", "=",
						StorageConstant.GSOUT_STATUS_SUBMIT);

				request.setAttribute("status",
						StorageConstant.GSOUT_STATUS_SUBMIT);
			}
		}
		// 查询
		else if ("3".equals(queryType))
		{
			if (OldPageSeparateTools.isMenuLoad(request))
			{
				condtion.addIntCondition("GSOutBean.status", "=",
						StorageConstant.GSOUT_STATUS_FLOW_PASS);

				request.setAttribute("status",
						StorageConstant.GSOUT_STATUS_FLOW_PASS);
			}
		}
		else
		{
			//condtion.addFlaseCondition();
		}

		condtion.addCondition("order by GSOutBean.logTime desc");

		return condtion;
	}
    
    /**
     * exportGS
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward exportGS(ActionMapping mapping, ActionForm form,
    		HttpServletRequest request, HttpServletResponse reponse)
    throws ServletException
    {
		OutputStream out = null;

		String exportKey = (String) request.getSession().getAttribute(
				"exportKey");

		List<GSOutVO> outList = null;

		String filenName = null;

		User user = (User) request.getSession().getAttribute("user");

		if (OldPageSeparateTools.getPageSeparate(request, exportKey)
				.getRowCount() > 25000)
		{
			request.setAttribute(KeyConstant.ERROR_MESSAGE, "导出的记录数不能超过25000");

			return mapping.findForward("error");
		}

		outList = gsOutDAO.queryEntityVOsByCondition(OldPageSeparateTools
				.getCondition(request, exportKey));

		filenName = "ExportGS_" + TimeTools.now("MMddHHmmss") + ".csv";

		if (outList.size() == 0)
		{
			return null;
		}

		reponse.setContentType("application/x-dbf");

		reponse.setHeader("Content-Disposition", "attachment; filename="
				+ filenName);

		WriteFile write = null;

		try
		{
			out = reponse.getOutputStream();

			GSOutVO element = null;

			element = (GSOutVO) outList.get(0);

			write = WriteFileFactory.getMyTXTWriter();

			write.openFile(out);

			WriteFileBuffer line = new WriteFileBuffer(write);

			line.writeColumn("单据日期");

			line.writeColumn("申请人");
			line.writeColumn("状态");

			line.writeColumn("类型");
			line.writeColumn("关联出库单");
			line.writeColumn("备注");

			line.writeColumn("仓库");
			line.writeColumn("仓区");
			line.writeColumn("产品");
			line.writeColumn("数量");
			line.writeColumn("成本");
			line.writeColumn("金料（克）");
			line.writeColumn("金料成本");

			line.writeColumn("银料（克）");
			line.writeColumn("银料成本");

			line.writeLine();
			// 写outbean
			for (Iterator iter = outList.iterator(); iter.hasNext();)
			{
				element = (GSOutVO) iter.next();

				List<GSOutItemVO> baseList = gsOutItemDAO.queryEntityVOsByFK(element.getId());

				for (Iterator iterator = baseList.iterator(); iterator
						.hasNext();)
				{
					line.reset();

					line.writeColumn(element.getOutTime());

					line.writeColumn(element.getStafferName());
					line.writeColumn(DefinedCommon.getValue("gsOutStatus", element.getStatus()));
					line.writeColumn(DefinedCommon.getValue("storageGSType", element.getType()));
					
					line.writeColumn(element.getRefId());
					line.writeColumn(element.getDescription());

					// 下面是base里面的数据
					GSOutItemVO base = (GSOutItemVO) iterator.next();

					line.writeColumn(base.getDeportName());
					line.writeColumn(base.getDepotpartName());
					line.writeColumn(base.getProductName());
					line.writeColumn(String.valueOf(base.getAmount()));
					line.writeColumn(String.valueOf(base.getPrice()));
					line.writeColumn(String.valueOf(base.getGoldWeight()));
					line.writeColumn(String.valueOf(base.getGoldPrice()));
					line.writeColumn(String.valueOf(base.getSilverWeight()));
					line.writeColumn(String.valueOf(base.getSilverPrice()));
					
					line.writeLine();
				}

			}
		}
		catch (Exception e)
		{
			_logger.error(e, e);
			return null;
		}
		finally
		{
			if (write != null)
			{
				try
				{
					write.close();
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
     * handleGSOut
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward handleGSOut(ActionMapping mapping, ActionForm form,
    		HttpServletRequest request, HttpServletResponse response)
    throws ServletException
    {
        GSOutBean bean = new GSOutBean();
        
        String id = request.getParameter("id");
        int gtotal = MathTools.parseInt(request.getParameter("gtotal"));
        int stotal = MathTools.parseInt(request.getParameter("stotal"));

        try
        {
        	User user = Helper.getUser(request);
        	
        	BeanUtil.getBean(bean, request);
        	
        	bean.setId("");
        	bean.setRefId(id);
        	bean.setOutTime(TimeTools.now_short());
        	bean.setLogTime(TimeTools.now());
        	bean.setStafferId(user.getStafferId());
        	bean.setType(StorageConstant.STORAGEAPPLY_GS_IN);
            
        	setGSOut(request, bean, StorageConstant.STORAGEAPPLY_GS_IN);
        	
        	// check
        	checkHandle(bean, gtotal, stotal);
        	
            bean.setStatus(StorageConstant.GSOUT_STATUS_SUBMIT);

            productFacade.addGSOut(user.getId(), bean);

            request.setAttribute(KeyConstant.MESSAGE, "成功操作");
        }
        catch (MYException e)
        {
            _logger.warn(e, e);

            request.setAttribute(KeyConstant.ERROR_MESSAGE, "操作失败:" + e.getMessage());
        }

        CommonTools.removeParamers(request);

        request.setAttribute("queryType", 1);
        
        return queryGSOut(mapping, form, request, response);
    }
    
    /**
     * 
     */
    private void checkHandle(GSOutBean bean, int gtotal, int stotal) throws MYException
    {
    	JudgeTools.judgeParameterIsNull(bean, bean.getRefId());
    	
    	GSOutBean oldGSOut = gsOutDAO.find(bean.getRefId());
    	
    	if (null == oldGSOut)
    	{
    		throw new MYException("出库单[%s]不存在，请确认", bean.getRefId());
    	}
    	
    	// 一个出库单只能被关联一次检查; 入库的金银克数分别要等于出库金银克数
    	GSOutBean ubean = gsOutDAO.findByUnique(bean.getRefId());
    	
    	if (null != ubean)
    	{
    		throw new MYException("出库单[%s]已被入库单[%s]关联了，请确认", bean.getRefId(), ubean.getId());
    	}
    	
    	GSOutItemBean item = bean.getItemList().get(0);
    	
    	// 金料克数检查
    	if (gtotal != item.getAmount() * item.getGoldWeight())
    	{
    		throw new MYException("金料克数与出库不一致，确确认");
    	}else{
    		bean.setGtotal(gtotal);
    	}
    	
    	// 银料克数检查
    	if (stotal != item.getAmount() * item.getSilverWeight())
    	{
    		throw new MYException("银料克数与出库不一致，确确认");
    	}else{
    		bean.setStotal(stotal);
    	}
    }
    
    /**
     * modifyStatus
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward modifyStatus(ActionMapping mapping, ActionForm form,
    		HttpServletRequest request, HttpServletResponse response)
    throws ServletException
    {
    	User user = Helper.getUser(request);
    	
    	String id = request.getParameter("id");
    	int statuss = MathTools.parseInt(request.getParameter("statuss"));
    	String reason = request.getParameter("reason");
    	String queryType = request.getParameter("queryType");
    	
    	if (statuss == 0)
    	{
    		return ActionTools.toError("数据异常,请重新操作", "queryGSOut", mapping, request);
    	}
    	
    	if (StringTools.isNullOrNone(id))
    	{
    		return ActionTools.toError("数据异常,请重新操作", "queryGSOut", mapping, request);
    	}
    	
    	GSOutBean bean = gsOutDAO.find(id);
    	
    	if (null == bean)
    	{
    		return ActionTools.toError("数据异常,请重新操作", "queryGSOut", mapping, request);
    	}
    	
    	try{
    		if (statuss == StorageConstant.GSOUT_STATUS_REJECT)
        	{
    			productFacade.rejectGSOut(user.getId(), id, reason);
        	}else{
        		productFacade.passGSOut(user.getId(), id, statuss);
        	}
    		
    		request.setAttribute(KeyConstant.MESSAGE, "成功操作");
    		
    	}catch(MYException e)
    	{
            _logger.warn(e, e);

            request.setAttribute(KeyConstant.ERROR_MESSAGE, "操作失败:" + e.getMessage());
        }
    	
    	request.setAttribute("queryType", queryType);
    	
    	return queryGSOut(mapping, form, request, response);
    }
    
    /**
     * @return the productFacade
     */
    public ProductFacade getProductFacade()
    {
        return productFacade;
    }

    /**
     * @param productFacade
     *            the productFacade to set
     */
    public void setProductFacade(ProductFacade productFacade)
    {
        this.productFacade = productFacade;
    }

    /**
     * @return the storageDAO
     */
    public StorageDAO getStorageDAO()
    {
        return storageDAO;
    }

    /**
     * @param storageDAO
     *            the storageDAO to set
     */
    public void setStorageDAO(StorageDAO storageDAO)
    {
        this.storageDAO = storageDAO;
    }

    /**
     * @return the depotDAO
     */
    public DepotDAO getDepotDAO()
    {
        return depotDAO;
    }

    /**
     * @param depotDAO
     *            the depotDAO to set
     */
    public void setDepotDAO(DepotDAO depotDAO)
    {
        this.depotDAO = depotDAO;
    }

    /**
     * @return the storageRelationDAO
     */
    public StorageRelationDAO getStorageRelationDAO()
    {
        return storageRelationDAO;
    }

    /**
     * @param storageRelationDAO
     *            the storageRelationDAO to set
     */
    public void setStorageRelationDAO(StorageRelationDAO storageRelationDAO)
    {
        this.storageRelationDAO = storageRelationDAO;
    }

    /**
     * @return the productDAO
     */
    public ProductDAO getProductDAO()
    {
        return productDAO;
    }

    /**
     * @param productDAO
     *            the productDAO to set
     */
    public void setProductDAO(ProductDAO productDAO)
    {
        this.productDAO = productDAO;
    }

    /**
     * @return the storageLogDAO
     */
    public StorageLogDAO getStorageLogDAO()
    {
        return storageLogDAO;
    }

    /**
     * @param storageLogDAO
     *            the storageLogDAO to set
     */
    public void setStorageLogDAO(StorageLogDAO storageLogDAO)
    {
        this.storageLogDAO = storageLogDAO;
    }

    /**
     * @return the depotpartDAO
     */
    public DepotpartDAO getDepotpartDAO()
    {
        return depotpartDAO;
    }

    /**
     * @param depotpartDAO
     *            the depotpartDAO to set
     */
    public void setDepotpartDAO(DepotpartDAO depotpartDAO)
    {
        this.depotpartDAO = depotpartDAO;
    }

    /**
     * @return the storageApplyDAO
     */
    public StorageApplyDAO getStorageApplyDAO()
    {
        return storageApplyDAO;
    }

    /**
     * @param storageApplyDAO
     *            the storageApplyDAO to set
     */
    public void setStorageApplyDAO(StorageApplyDAO storageApplyDAO)
    {
        this.storageApplyDAO = storageApplyDAO;
    }

    /**
     * @return the storageRelationManager
     */
    public StorageRelationManager getStorageRelationManager()
    {
        return storageRelationManager;
    }

    /**
     * @param storageRelationManager
     *            the storageRelationManager to set
     */
    public void setStorageRelationManager(StorageRelationManager storageRelationManager)
    {
        this.storageRelationManager = storageRelationManager;
    }

    /**
     * @return the productVSLocationDAO
     */
    public ProductVSLocationDAO getProductVSLocationDAO()
    {
        return productVSLocationDAO;
    }

    /**
     * @param productVSLocationDAO
     *            the productVSLocationDAO to set
     */
    public void setProductVSLocationDAO(ProductVSLocationDAO productVSLocationDAO)
    {
        this.productVSLocationDAO = productVSLocationDAO;
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
     * @return the outManager
     */
    public OutManager getOutManager()
    {
        return outManager;
    }

    /**
     * @param outManager
     *            the outManager to set
     */
    public void setOutManager(OutManager outManager)
    {
        this.outManager = outManager;
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
     * @return the sailConfigDAO
     */
    public SailConfigDAO getSailConfigDAO()
    {
        return sailConfigDAO;
    }

    /**
     * @param sailConfigDAO
     *            the sailConfigDAO to set
     */
    public void setSailConfigDAO(SailConfigDAO sailConfigDAO)
    {
        this.sailConfigDAO = sailConfigDAO;
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
     * @return the sailConfigManager
     */
    public SailConfigManager getSailConfigManager()
    {
        return sailConfigManager;
    }

    /**
     * @param sailConfigManager
     *            the sailConfigManager to set
     */
    public void setSailConfigManager(SailConfigManager sailConfigManager)
    {
        this.sailConfigManager = sailConfigManager;
    }
	public ProductChangeRecordDAO getProductChangeRecordDAO() {
		return productChangeRecordDAO;
	}

	public void setProductChangeRecordDAO(
			ProductChangeRecordDAO productChangeRecordDAO) {
		this.productChangeRecordDAO = productChangeRecordDAO;
	}

	public StorageManager getStorageManager() {
		return storageManager;
	}

	public void setStorageManager(StorageManager storageManager) {
		this.storageManager = storageManager;
	}
    
	public ProductCombinationDAO getProductCombinationDAO()
	{
		return productCombinationDAO;
	}

	public void setProductCombinationDAO(ProductCombinationDAO productCombinationDAO)
	{
		this.productCombinationDAO = productCombinationDAO;
	}

	public PriceConfigManager getPriceConfigManager()
	{
		return priceConfigManager;
	}

	public void setPriceConfigManager(PriceConfigManager priceConfigManager)
	{
		this.priceConfigManager = priceConfigManager;
	}

	public AuditRuleManager getAuditRuleManager()
	{
		return auditRuleManager;
	}

	public void setAuditRuleManager(AuditRuleManager auditRuleManager)
	{
		this.auditRuleManager = auditRuleManager;
	}

	public PriceConfigDAO getPriceConfigDAO()
	{
		return priceConfigDAO;
	}

	public void setPriceConfigDAO(PriceConfigDAO priceConfigDAO)
	{
		this.priceConfigDAO = priceConfigDAO;
	}

	/**
	 * @return the gsOutDAO
	 */
	public GSOutDAO getGsOutDAO()
	{
		return gsOutDAO;
	}

	/**
	 * @param gsOutDAO the gsOutDAO to set
	 */
	public void setGsOutDAO(GSOutDAO gsOutDAO)
	{
		this.gsOutDAO = gsOutDAO;
	}

	/**
	 * @return the gsOutItemDAO
	 */
	public GSOutItemDAO getGsOutItemDAO()
	{
		return gsOutItemDAO;
	}

	/**
	 * @param gsOutItemDAO the gsOutItemDAO to set
	 */
	public void setGsOutItemDAO(GSOutItemDAO gsOutItemDAO)
	{
		this.gsOutItemDAO = gsOutItemDAO;
	}

	/**
	 * @return the flowLogDAO
	 */
	public FlowLogDAO getFlowLogDAO()
	{
		return flowLogDAO;
	}

	/**
	 * @param flowLogDAO the flowLogDAO to set
	 */
	public void setFlowLogDAO(FlowLogDAO flowLogDAO)
	{
		this.flowLogDAO = flowLogDAO;
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
}
