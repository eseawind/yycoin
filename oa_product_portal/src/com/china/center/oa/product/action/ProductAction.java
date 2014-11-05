/**
 * File Name: FlowAction.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * Creater: zhuAchen<br>
 * CreateTime: 2009-4-26<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.product.action;


import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

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
import com.center.china.osgi.publics.file.read.ReadeFileFactory;
import com.center.china.osgi.publics.file.read.ReaderFile;
import com.center.china.osgi.publics.file.writer.WriteFile;
import com.center.china.osgi.publics.file.writer.WriteFileFactory;
import com.china.center.actionhelper.common.ActionTools;
import com.china.center.actionhelper.common.JSONPageSeparateTools;
import com.china.center.actionhelper.common.JSONTools;
import com.china.center.actionhelper.common.KeyConstant;
import com.china.center.actionhelper.common.PageSeparateTools;
import com.china.center.actionhelper.json.AjaxResult;
import com.china.center.actionhelper.jsonimpl.JSONArray;
import com.china.center.actionhelper.query.HandleResult;
import com.china.center.common.MYException;
import com.china.center.common.taglib.DefinedCommon;
import com.china.center.jdbc.util.ConditionParse;
import com.china.center.jdbc.util.PageSeparate;
import com.china.center.oa.product.bean.CiticVSOAProductBean;
import com.china.center.oa.product.bean.ComposeFeeBean;
import com.china.center.oa.product.bean.ComposeFeeDefinedBean;
import com.china.center.oa.product.bean.ComposeItemBean;
import com.china.center.oa.product.bean.ComposeProductBean;
import com.china.center.oa.product.bean.DecomposeProductBean;
import com.china.center.oa.product.bean.DepotBean;
import com.china.center.oa.product.bean.DepotpartBean;
import com.china.center.oa.product.bean.GoldSilverPriceBean;
import com.china.center.oa.product.bean.PriceChangeBean;
import com.china.center.oa.product.bean.PriceChangeNewItemBean;
import com.china.center.oa.product.bean.PriceChangeSrcItemBean;
import com.china.center.oa.product.bean.PriceConfigBean;
import com.china.center.oa.product.bean.ProductBean;
import com.china.center.oa.product.bean.ProviderBean;
import com.china.center.oa.product.constant.ComposeConstant;
import com.china.center.oa.product.constant.DepotConstant;
import com.china.center.oa.product.constant.ProductConstant;
import com.china.center.oa.product.constant.StorageConstant;
import com.china.center.oa.product.dao.CiticVSOAProductDAO;
import com.china.center.oa.product.dao.ComposeFeeDefinedDAO;
import com.china.center.oa.product.dao.ComposeItemDAO;
import com.china.center.oa.product.dao.ComposeProductDAO;
import com.china.center.oa.product.dao.DecomposeProductDAO;
import com.china.center.oa.product.dao.DepotDAO;
import com.china.center.oa.product.dao.DepotpartDAO;
import com.china.center.oa.product.dao.GoldSilverPriceDAO;
import com.china.center.oa.product.dao.PriceChangeDAO;
import com.china.center.oa.product.dao.PriceConfigDAO;
import com.china.center.oa.product.dao.ProductBOMDAO;
import com.china.center.oa.product.dao.ProductCombinationDAO;
import com.china.center.oa.product.dao.ProductDAO;
import com.china.center.oa.product.dao.ProductVSLocationDAO;
import com.china.center.oa.product.dao.ProviderDAO;
import com.china.center.oa.product.dao.StorageRelationDAO;
import com.china.center.oa.product.facade.ProductFacade;
import com.china.center.oa.product.manager.ComposeProductManager;
import com.china.center.oa.product.manager.PriceConfigManager;
import com.china.center.oa.product.manager.ProductManager;
import com.china.center.oa.product.vo.ComposeFeeDefinedVO;
import com.china.center.oa.product.vo.ComposeItemVO;
import com.china.center.oa.product.vo.ComposeProductVO;
import com.china.center.oa.product.vo.DecomposeProductVO;
import com.china.center.oa.product.vo.PriceChangeNewItemVO;
import com.china.center.oa.product.vo.PriceChangeSrcItemVO;
import com.china.center.oa.product.vo.PriceChangeVO;
import com.china.center.oa.product.vo.ProductBOMVO;
import com.china.center.oa.product.vo.ProductCombinationVO;
import com.china.center.oa.product.vo.ProductVO;
import com.china.center.oa.product.vo.ProductVSLocationVO;
import com.china.center.oa.product.vo.StorageRelationVO;
import com.china.center.oa.product.vs.ProductCombinationBean;
import com.china.center.oa.product.vs.ProductVSLocationBean;
import com.china.center.oa.product.vs.StorageRelationBean;
import com.china.center.oa.publics.Helper;
import com.china.center.oa.publics.bean.FlowLogBean;
import com.china.center.oa.publics.bean.InvoiceBean;
import com.china.center.oa.publics.bean.PrincipalshipBean;
import com.china.center.oa.publics.bean.StafferBean;
import com.china.center.oa.publics.constant.PublicConstant;
import com.china.center.oa.publics.dao.CommonDAO;
import com.china.center.oa.publics.dao.EnumDAO;
import com.china.center.oa.publics.dao.FlowLogDAO;
import com.china.center.oa.publics.dao.InvoiceDAO;
import com.china.center.oa.publics.dao.LocationDAO;
import com.china.center.oa.publics.helper.OATools;
import com.china.center.oa.publics.manager.OrgManager;
import com.china.center.oa.publics.vo.FlowLogVO;
import com.china.center.oa.sail.bean.SailConfBean;
import com.china.center.oa.sail.manager.SailConfigManager;
import com.china.center.oa.tax.bean.FinanceBean;
import com.china.center.oa.tax.dao.FinanceDAO;
import com.china.center.osgi.jsp.ElTools;
import com.china.center.tools.BeanUtil;
import com.china.center.tools.CommonTools;
import com.china.center.tools.FileTools;
import com.china.center.tools.ListTools;
import com.china.center.tools.MathTools;
import com.china.center.tools.RequestDataStream;
import com.china.center.tools.RequestTools;
import com.china.center.tools.StringTools;
import com.china.center.tools.TimeTools;
import com.china.center.tools.UtilStream;
import com.china.center.tools.WriteFileBuffer;


/**
 * FlowAction
 * 
 * @author ZHUZHU
 * @version 2009-4-26
 * @see ProductAction
 * @since 1.0
 */
public class ProductAction extends DispatchAction
{
    private final Log _logger = LogFactory.getLog(getClass());

    private ProductFacade productFacade = null;

    private ProductManager productManager = null;

    private ProductCombinationDAO productCombinationDAO = null;

    private ProductDAO productDAO = null;

    private ComposeFeeDefinedDAO composeFeeDefinedDAO = null;

    private ComposeProductDAO composeProductDAO = null;
    
    private DecomposeProductDAO decomposeProductDAO = null;

    private CommonDAO commonDAO = null;

    private LocationDAO locationDAO = null;

    private ProviderDAO providerDAO = null;

    private DepotpartDAO depotpartDAO = null;

    private DepotDAO depotDAO = null;

    private PriceChangeDAO priceChangeDAO = null;

    private StorageRelationDAO storageRelationDAO = null;

    private ComposeProductManager composeProductManager = null;

    private EnumDAO enumDAO = null;

    private OrgManager orgManager = null;

    private FinanceDAO financeDAO = null;

    private SailConfigManager sailConfigManager = null;

    private ProductVSLocationDAO productVSLocationDAO = null;
    
    private ComposeItemDAO composeItemDAO = null;
    
    private GoldSilverPriceDAO goldSilverPriceDAO = null;

    private PriceConfigDAO priceConfigDAO = null;
    
    private PriceConfigManager priceConfigManager = null;
    
    private ProductBOMDAO productBOMDAO = null;
    
    private FlowLogDAO flowLogDAO = null;
    
    private InvoiceDAO invoiceDAO = null;
    
    private CiticVSOAProductDAO citicVSOAProductDAO = null;
    
    private static String QUERYPRODUCT = "queryProduct";

    private static String QUERYAPPLYPRODUCT = "queryApplyProduct";

    private static String QUERYCHECKPRODUCT = "queryCheckProduct";

    private static String QUERYCOMPOSE = "queryCompose";

    private static String RPTQUERYPRODUCT = "rptQueryProduct";

    private static String RPTQUERYABSPRODUCT = "rptQueryAbsProduct";

    private static String QUERYPRICECHANGE = "queryPriceChange";

    private static String QUERYCOMPOSEFEEDEFINED = "queryComposeFeeDefined";
    
    private static String RPTQUERYPRODUCTBOM = "rptQueryProductBom";
    
    private static String QUERYDECOMPOSE = "queryDecompose";
    
    private static String QUERYCITICPRODUCT = "queryCiticProduct";

    /**
     * default constructor
     */
    public ProductAction()
    {
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
    public ActionForward queryProduct(ActionMapping mapping, ActionForm form,
                                      final HttpServletRequest request, HttpServletResponse response)
        throws ServletException
    {
        final ConditionParse condtion = new ConditionParse();

        condtion.addWhereStr();

        // 产品管理进来的
        final String src = request.getParameter("src");

        ActionTools.processJSONQueryCondition(QUERYPRODUCT, request, condtion);
        
        notQueryFirstTime(condtion);
        
        // 过滤非稳态的产品
        condtion.addIntCondition("ProductBean.status", "<>", ProductConstant.STATUS_APPLY);

        // 默认虚拟产品在前面
        condtion.addCondition("order by ProductBean.abstractType desc, ProductBean.logTime desc");

        String jsonstr = ActionTools.queryVOByJSONAndToString(QUERYPRODUCT, request, condtion,
            this.productDAO, new HandleResult<ProductVO>()
            {
                public void handle(ProductVO obj)
                {
                	// 根据配置获取结算价
                	List<PriceConfigBean> list = priceConfigDAO.querySailPricebyProductId(obj.getId());
                	
                	if (!ListTools.isEmptyOrNull(list))
                	{
                		PriceConfigBean cb = priceConfigManager.calcSailPrice(list.get(0));
                		
                		obj.setSailPrice(cb.getSailPrice());
                	}
                	
                    if ( !"0".equals(src))
                    {
                        SailConfBean sailConf = sailConfigManager.findProductConf(Helper
                            .getStaffer(request), obj);

                        obj.setSailPrice(obj.getSailPrice()
                                         * (1 + sailConf.getPratio() / 1000.0d + sailConf
                                             .getIratio() / 1000.0d));
                    }
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
    
    public ActionForward selectProducts(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws ServletException {
        
    	CommonTools.saveParamers(request);

        List<ProductVO> list = null;

        if (PageSeparateTools.isFirstLoad(request))
        {
            ConditionParse condtion = new ConditionParse();

            condtion.addWhereStr();

            setProductInnerCondition(request, condtion);

            int total = productDAO.countByCondition(condtion.toString());

            PageSeparate page = new PageSeparate(total, PublicConstant.PAGE_COMMON_SIZE);

            PageSeparateTools.initPageSeparate(condtion, page, request, RPTQUERYPRODUCT);

            list = productDAO.queryEntityVOsByCondition(condtion, page);
        }
        else
        {
            PageSeparateTools.processSeparate(request, RPTQUERYPRODUCT);

            list = productDAO.queryEntityVOsByCondition(PageSeparateTools.getCondition(request,
                RPTQUERYPRODUCT), PageSeparateTools.getPageSeparate(request, RPTQUERYPRODUCT));
        }

        request.setAttribute("random", new Random().nextInt());

        request.setAttribute("productList", list);

        return mapping.findForward("rptQueryProducts");
    }
    
    
    /**
     * queryPriceChange
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward queryPriceChange(ActionMapping mapping, ActionForm form,
                                          HttpServletRequest request, HttpServletResponse response)
        throws ServletException
    {
        final ConditionParse condtion = new ConditionParse();

        condtion.addWhereStr();

        ActionTools.processJSONQueryCondition(QUERYPRICECHANGE, request, condtion);

        condtion.addCondition("order by PriceChangeBean.id desc");

        String jsonstr = ActionTools.queryVOByJSONAndToString(QUERYPRICECHANGE, request, condtion,
            this.priceChangeDAO);

        return JSONTools.writeResponse(response, jsonstr);
    }

    /**
     * lockStorageRelation
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward lockStorageRelation(ActionMapping mapping, ActionForm form,
                                             HttpServletRequest request,
                                             HttpServletResponse response)
        throws ServletException
    {
        AjaxResult ajax = new AjaxResult();

        try
        {
            User user = Helper.getUser(request);

            productFacade.lockStorageRelation(user.getId());

            ajax.setSuccess("成功锁定库存");
        }
        catch (MYException e)
        {
            _logger.warn(e, e);

            ajax.setError("锁定库存失败:" + e.getMessage());
        }

        return JSONTools.writeResponse(response, ajax);
    }

    /**
     * lockStorageRelation
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward findStorageRelationStatus(ActionMapping mapping, ActionForm form,
                                                   HttpServletRequest request,
                                                   HttpServletResponse response)
        throws ServletException
    {
        AjaxResult ajax = new AjaxResult();

        if (productFacade.isStorageRelationLock())
        {
            ajax.setSuccess("<font color=blue><b>锁定</b></font>");
        }
        else
        {
            ajax.setSuccess("<font color=red><b>未锁定</b></font>");
        }

        return JSONTools.writeResponse(response, ajax);
    }

    /**
     * unlockStorageRelation
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward unlockStorageRelation(ActionMapping mapping, ActionForm form,
                                               HttpServletRequest request,
                                               HttpServletResponse response)
        throws ServletException
    {
        AjaxResult ajax = new AjaxResult();

        try
        {
            User user = Helper.getUser(request);

            productFacade.unlockStorageRelation(user.getId());

            ajax.setSuccess("成功解锁库存");
        }
        catch (MYException e)
        {
            _logger.warn(e, e);

            ajax.setError("解锁库存失败:" + e.getMessage());
        }

        return JSONTools.writeResponse(response, ajax);
    }

    /**
     * queryCompose
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward queryCompose(ActionMapping mapping, ActionForm form,
                                      HttpServletRequest request, HttpServletResponse response)
        throws ServletException
    {
        String foward = request.getParameter("foward");

        final ConditionParse condtion = new ConditionParse();

        condtion.addWhereStr();
        
        condtion.addCondition(" and ComposeProductBean.parentId = ''");

        if ("1".equals(foward))
        {
            condtion.addIntCondition("ComposeProductBean.status", "=",
                ComposeConstant.STATUS_SUBMIT);
        }

        if ("2".equals(foward))
        {
            condtion.addIntCondition("ComposeProductBean.status", "=",
                ComposeConstant.STATUS_MANAGER_PASS);
        }

        if ("3".equals(foward))
        {
            condtion.addIntCondition("ComposeProductBean.status", "=",
                ComposeConstant.STATUS_CRO_PASS);
        }

        ActionTools.processJSONQueryCondition(QUERYCOMPOSE, request, condtion);

        condtion.addCondition("order by ComposeProductBean.logTime desc");

        String jsonstr = ActionTools.queryVOByJSONAndToString(QUERYCOMPOSE, request, condtion,
            this.composeProductDAO);

        return JSONTools.writeResponse(response, jsonstr);
    }

    /**
     * exportCompose
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward exportCompose(ActionMapping mapping, ActionForm form,
                                       HttpServletRequest request, HttpServletResponse response)
        throws ServletException
    {
        OutputStream out = null;

        String filenName = "Compose_" + TimeTools.now("MMddHHmmss") + ".csv";

        response.setContentType("application/x-dbf");

        response.setHeader("Content-Disposition", "attachment; filename=" + filenName);

        WriteFile write = null;

        ConditionParse condtion = JSONPageSeparateTools.getCondition(request, QUERYCOMPOSE);

        int count = this.composeProductDAO.countVOByCondition(condtion.toString());

        try
        {
            out = response.getOutputStream();

            write = WriteFileFactory.getMyTXTWriter();

            write.openFile(out);

            write.writeLine("日期,标识,关联凭证,产品名称,产品编码,数量,单价,合成人,类型,核对,管理类型,源仓区,源产品编码,源产品名称,源产品数量,源产品价格");

            PageSeparate page = new PageSeparate();

            page.reset2(count, 2000);

            WriteFileBuffer line = new WriteFileBuffer(write);

            while (page.nextPage())
            {
                List<ComposeProductVO> voFList = composeProductDAO.queryEntityVOsByCondition(
                    condtion, page);

                for (ComposeProductVO vo : voFList)
                {
                    
                    List<ComposeItemVO> itemVoList =  composeItemDAO.queryEntityVOsByFK(vo.getId());
                    
                    for (ComposeItemVO itemVO : itemVoList)
                    {
                        line.reset();
                        
                        line.writeColumn("[" + vo.getLogTime() + "]");
                        line.writeColumn(vo.getId());

                        List<FinanceBean> financeBeanList = financeDAO.queryRefFinanceItemByRefId(vo
                            .getId());

                        if (financeBeanList.size() > 0)
                        {
                            line.writeColumn(financeBeanList.get(0).getId());
                        }
                        else
                        {
                            line.writeColumn("");
                        }

                        line.writeColumn(vo.getProductName());
                        line.writeColumn(vo.getProductCode());
                        line.writeColumn(vo.getAmount());
                        line.writeColumn(vo.getPrice());
                        line.writeColumn(vo.getStafferName());
                        line.writeColumn(ElTools.get("composeType", vo.getType()));
                        line.writeColumn(ElTools.get("pubCheckStatus", vo.getCheckStatus()));
                        line.writeColumn(ElTools.get("pubManagerType", vo.getMtype()));
                        
                        line.writeColumn(itemVO.getDepotpartName());
                        line.writeColumn(itemVO.getProductCode());
                        line.writeColumn(itemVO.getProductName());
                        line.writeColumn(itemVO.getAmount());
                        line.writeColumn(itemVO.getPrice());
                        
                        line.writeLine();
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
     * queryApplyProduct(查询申请的产品)
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward queryApplyProduct(ActionMapping mapping, ActionForm form,
                                           HttpServletRequest request, HttpServletResponse response)
        throws ServletException
    {
        User user = Helper.getUser(request);

        final ConditionParse condtion = new ConditionParse();

        condtion.addWhereStr();

        condtion.addIntCondition("ProductBean.status", "=", ProductConstant.STATUS_APPLY);

        condtion.addCondition("ProductBean.createrId", "=", user.getStafferId());

        ActionTools.processJSONQueryCondition(QUERYAPPLYPRODUCT, request, condtion);

        String jsonstr = ActionTools.queryVOByJSONAndToString(QUERYAPPLYPRODUCT, request, condtion,
            this.productDAO);

        return JSONTools.writeResponse(response, jsonstr);
    }

    /**
     * 查询产品申请
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward queryCheckProduct(ActionMapping mapping, ActionForm form,
                                           HttpServletRequest request, HttpServletResponse response)
        throws ServletException
    {
        final ConditionParse condtion = new ConditionParse();

        condtion.addWhereStr();

        condtion.addIntCondition("ProductBean.status", "=", ProductConstant.STATUS_APPLY);

        ActionTools.processJSONQueryCondition(QUERYCHECKPRODUCT, request, condtion);

        String jsonstr = ActionTools.queryVOByJSONAndToString(QUERYCHECKPRODUCT, request, condtion,
            this.productDAO);

        return JSONTools.writeResponse(response, jsonstr);
    }

    /**
     * 产品的选择
     * 
     * @param mapping
     * @param form
     * @param request
     * @param reponse
     * @return
     * @throws ServletException
     */
    public ActionForward rptQueryProduct(ActionMapping mapping, ActionForm form,
                                         HttpServletRequest request, HttpServletResponse reponse)
        throws ServletException
    {
        CommonTools.saveParamers(request);

        List<ProductVO> list = null;
        
        String flag = request.getParameter("flag");
        
        request.setAttribute("flag", flag);

        if (PageSeparateTools.isFirstLoad(request))
        {
            ConditionParse condtion = new ConditionParse();

            condtion.addWhereStr();

            setProductInnerCondition(request, condtion);

            int total = productDAO.countByCondition(condtion.toString());

            PageSeparate page = new PageSeparate(total, PublicConstant.PAGE_COMMON_SIZE);

            PageSeparateTools.initPageSeparate(condtion, page, request, RPTQUERYPRODUCT);

            list = productDAO.queryEntityVOsByCondition(condtion, page);
        }
        else
        {
            PageSeparateTools.processSeparate(request, RPTQUERYPRODUCT);

            list = productDAO.queryEntityVOsByCondition(PageSeparateTools.getCondition(request,
                RPTQUERYPRODUCT), PageSeparateTools.getPageSeparate(request, RPTQUERYPRODUCT));
        }

        request.setAttribute("beanList", list);

        request.setAttribute("random", new Random().nextInt());

        String rootUrl = RequestTools.getRootUrl(request);

        request.setAttribute("rootUrl", rootUrl);

        return mapping.findForward("rptQueryProduct");
    }
    
    /**
     * 产品的选择
     * 
     * @param mapping
     * @param form
     * @param request
     * @param reponse
     * @return
     * @throws ServletException
     */
    public ActionForward rptQueryProductBom(ActionMapping mapping, ActionForm form,
                                         HttpServletRequest request, HttpServletResponse reponse)
        throws ServletException
    {
        CommonTools.saveParamers(request);

        List<ProductBOMVO> list = null;
        
        if (PageSeparateTools.isFirstLoad(request))
        {
            ConditionParse condtion = new ConditionParse();

            condtion.addWhereStr();

            setProductBomCondition(request, condtion);

            int total = productBOMDAO.countVOByCondition(condtion.toString());

            PageSeparate page = new PageSeparate(total, PublicConstant.PAGE_SIZE);

            PageSeparateTools.initPageSeparate(condtion, page, request, RPTQUERYPRODUCTBOM);

            list = productBOMDAO.queryEntityVOsByCondition(condtion, page);
        }
        else
        {
            PageSeparateTools.processSeparate(request, RPTQUERYPRODUCTBOM);

            list = productBOMDAO.queryEntityVOsByCondition(PageSeparateTools.getCondition(request,
            		RPTQUERYPRODUCTBOM), PageSeparateTools.getPageSeparate(request, RPTQUERYPRODUCTBOM));
        }

        Set<String> set = new HashSet<String>();
        
        List<ProductBOMVO> lastList = new ArrayList<ProductBOMVO>();
        
        // 组装结果集
        for (ProductBOMVO each : list)
        {
        	if (!set.contains(each.getProductId()))
        	{
        		List<ProductBOMVO> voList = productBOMDAO.queryEntityVOsByFK(each.getProductId());
        		
        		each.setVoList(voList);
        		
        		// voList 转换为JSON
        		JSONArray shows = new JSONArray(voList, true);

        		each.setBomJson(shows.toString());
        		
        		lastList.add(each);
        		
        		set.add(each.getProductId());
        	}
        }

        request.setAttribute("beanList", lastList);
        
        request.setAttribute("random", new Random().nextInt());

        String rootUrl = RequestTools.getRootUrl(request);

        request.setAttribute("rootUrl", rootUrl);

        return mapping.findForward("rptQueryProductBom");
    }
    
    private void setProductBomCondition(HttpServletRequest request, ConditionParse condtion)
    {
        String name = request.getParameter("name");

        String code = request.getParameter("code");

        String mtype = request.getParameter("mtype");
        
        String stock = request.getParameter("stock");

        if ( !StringTools.isNullOrNone(name))
        {
            condtion.addCondition("ProductBean1.name", "like", name);
        }

        if ( !StringTools.isNullOrNone(code))
        {
            condtion.addCondition("ProductBean1.code", "like", code);
        }

        // 不可全为空
        if (StringTools.isNullOrNone(name) && StringTools.isNullOrNone(code))
        {
        	condtion.addFlaseCondition();
        }
        
        if (OATools.getManagerFlag() && !StringTools.isNullOrNone(mtype))
        {
            // 采购
            if (!StringTools.isNullOrNone(stock))
            {
            	int mtype1 = MathTools.parseInt(mtype);
            	
            	// 普通 17% 非旧货
            	if (mtype1 == 1 || mtype1 == 3)
            	{
            		condtion.addCondition("ProductBean1.reserve4", "=", "1");
            		
            		condtion.addIntCondition("ProductBean1.consumeInDay", "<>", ProductConstant.PRODUCT_OLDGOOD);
            		
            	} // 普通 旧货
            	else if (mtype1 == 2)
            	{
            		condtion.addCondition("ProductBean1.reserve4", "=", "1");
            		
            		condtion.addIntCondition("ProductBean1.consumeInDay", "=", ProductConstant.PRODUCT_OLDGOOD);
            	} 
            	// 管理
            	else
            	{
            		condtion.addCondition("ProductBean1.reserve4", "=", mtype);
            	}
            }
            else
            {
            	condtion.addCondition("ProductBean1.reserve4", "=", mtype);
            }
        }
    }
    
    /**
     * 项目产品的选择
     * 
     * @param mapping
     * @param form
     * @param request
     * @param reponse
     * @return
     * @throws ServletException
     */
    public ActionForward selectProjectpro(ActionMapping mapping, ActionForm form,
                                         HttpServletRequest request, HttpServletResponse reponse)
        throws ServletException
    {
        CommonTools.saveParamers(request);

        List<ProductVO> list = null;
        
        String name = request.getParameter("name");

        String code = request.getParameter("code");
        
        String firstLoad = request.getParameter("firstLoad");
        
        ConditionParse condtion = new ConditionParse();
        
        if ( !StringTools.isNullOrNone(name))
        {
            condtion.addCondition("ProductBean.name", "like", name);
        }

        if ( !StringTools.isNullOrNone(code))
        {
            condtion.addCondition("ProductBean.code", "like", code);
        }
        
            condtion.addIntCondition("ProductBean.status", "=", ProductConstant.STATUS_COMMON);

        if (!"1".equals(firstLoad))
        {

            condtion.addWhereStr();

            setProductInnerCondition(request, condtion);

            int total = productDAO.countByCondition(condtion.toString());

            PageSeparate page = new PageSeparate(total, PublicConstant.PAGE_COMMON_SIZE);

            PageSeparateTools.initPageSeparate(condtion, page, request, RPTQUERYPRODUCT);

            list = productDAO.queryEntityVOsByCondition(condtion, page);
        }
        else
        {
            PageSeparateTools.processSeparate(request, RPTQUERYPRODUCT);

            list = null;
        }

        request.setAttribute("beanList", list);

        request.setAttribute("random", new Random().nextInt());

        String rootUrl = RequestTools.getRootUrl(request);

        request.setAttribute("rootUrl", rootUrl);

        return mapping.findForward("selectProjectpro");
    }
    
    /**
     * 合同产品的选择
     * 
     * @param mapping
     * @param form
     * @param request
     * @param reponse
     * @return
     * @throws ServletException
     */
    public ActionForward selectAgreementpro(ActionMapping mapping, ActionForm form,
                                         HttpServletRequest request, HttpServletResponse reponse)
        throws ServletException
    {
        CommonTools.saveParamers(request);

        List<ProductVO> list = null;
        
        String name = request.getParameter("name");

        String code = request.getParameter("code");
        
        String firstLoad = request.getParameter("firstLoad");
        
        ConditionParse condtion = new ConditionParse();
        
        if ( !StringTools.isNullOrNone(name))
        {
            condtion.addCondition("ProductBean.name", "like", name);
        }

        if ( !StringTools.isNullOrNone(code))
        {
            condtion.addCondition("ProductBean.code", "like", code);
        }
        
            condtion.addIntCondition("ProductBean.status", "=", ProductConstant.STATUS_COMMON);

        if (!"1".equals(firstLoad))
        {

            condtion.addWhereStr();

            setProductInnerCondition(request, condtion);

            int total = productDAO.countByCondition(condtion.toString());

            PageSeparate page = new PageSeparate(total, PublicConstant.PAGE_COMMON_SIZE);

            PageSeparateTools.initPageSeparate(condtion, page, request, RPTQUERYPRODUCT);

            list = productDAO.queryEntityVOsByCondition(condtion, page);
        }
        else
        {
            PageSeparateTools.processSeparate(request, RPTQUERYPRODUCT);

            list = null;
        }

        request.setAttribute("beanList", list);

        request.setAttribute("random", new Random().nextInt());

        String rootUrl = RequestTools.getRootUrl(request);

        request.setAttribute("rootUrl", rootUrl);

        return mapping.findForward("selectAgreementpro");
    }

    /**
     * 虚拟产品的选择
     * 
     * @param mapping
     * @param form
     * @param request
     * @param reponse
     * @return
     * @throws ServletException
     */
    public ActionForward rptQueryAbsProduct(ActionMapping mapping, ActionForm form,
                                            HttpServletRequest request, HttpServletResponse reponse)
        throws ServletException
    {
        CommonTools.saveParamers(request);

        List<ProductVO> list = null;

        if (PageSeparateTools.isFirstLoad(request))
        {
            ConditionParse condtion = new ConditionParse();

            condtion.addWhereStr();

            setAbsProductInnerCondition(request, condtion);

            int total = productDAO.countByCondition(condtion.toString());

            PageSeparate page = new PageSeparate(total, PublicConstant.PAGE_COMMON_SIZE);

            PageSeparateTools.initPageSeparate(condtion, page, request, RPTQUERYABSPRODUCT);

            list = productDAO.queryEntityVOsByCondition(condtion, page);
        }
        else
        {
            PageSeparateTools.processSeparate(request, RPTQUERYPRODUCT);

            list = productDAO
                .queryEntityVOsByCondition(PageSeparateTools.getCondition(request,
                    RPTQUERYABSPRODUCT), PageSeparateTools.getPageSeparate(request,
                    RPTQUERYABSPRODUCT));
        }

        Map<String, List<ProductBean>> map = new HashMap();

        for (ProductVO productVO : list)
        {
            // 获取组合方式
            List<ProductCombinationVO> comVOList = productCombinationDAO
                .queryEntityVOsByFK(productVO.getId());

            List<ProductBean> eachList = new ArrayList<ProductBean>();

            String lastName = "&nbsp;<br>";

            for (ProductCombinationVO productCombinationVO : comVOList)
            {
                ProductBean product = productDAO.find(productCombinationVO.getSproductId());

                eachList.add(product);

                product.setDescription("");
                
                lastName += product.getName() + "(" + product.getCode() + ")<br>&nbsp;";
            }

            productVO.setReserve1(lastName);

            map.put(productVO.getId(), eachList);
        }

        request.setAttribute("beanList", list);

        // var uAuth = JSON.parse('${authJSON}');
        request.setAttribute("mapStr", JSONTools.getMapListJSON(map));        

        request.setAttribute("random", new Random().nextInt());

        String rootUrl = RequestTools.getRootUrl(request);

        request.setAttribute("rootUrl", rootUrl);

        return mapping.findForward("rptQueryAbsProduct");
    }

    /**
     * @param request
     * @param condtion
     */
    private void setProductInnerCondition(HttpServletRequest request, ConditionParse condtion)
    {
        String name = request.getParameter("name");

        String code = request.getParameter("code");

        String abstractType = request.getParameter("abstractType");

        String status = request.getParameter("status");

        String ctype = request.getParameter("ctype");

        String mtype = request.getParameter("mtype");
        
        String stock = request.getParameter("stock");

        if ( !StringTools.isNullOrNone(name))
        {
            condtion.addCondition("ProductBean.name", "like", name);
        }

        if ( !StringTools.isNullOrNone(code))
        {
            condtion.addCondition("ProductBean.code", "like", code);
        }

        if ( !StringTools.isNullOrNone(abstractType))
        {
            condtion.addIntCondition("ProductBean.abstractType", "=", abstractType);
        }

//        if ( !StringTools.isNullOrNone(status))
//        {
            condtion.addIntCondition("ProductBean.status", "=", ProductConstant.STATUS_COMMON);
//        }

        if ( !StringTools.isNullOrNone(ctype))
        {
            condtion.addIntCondition("ProductBean.ctype", "=", ProductConstant.CTYPE_YES);
        }

        if (OATools.getManagerFlag() && !StringTools.isNullOrNone(mtype))
        {
            // 采购
            if (!StringTools.isNullOrNone(stock))
            {
            	int mtype1 = MathTools.parseInt(mtype);
            	
            	// 普通 17% 非旧货
            	if (mtype1 == 1 || mtype1 == 3)
            	{
            		condtion.addCondition("ProductBean.reserve4", "=", "1");
            		
            		//condtion.addIntCondition("ProductBean.consumeInDay", "<>", ProductConstant.PRODUCT_OLDGOOD);
            		
            	} // 普通 旧货
            	else if (mtype1 == 2)
            	{
            		condtion.addCondition("ProductBean.reserve4", "=", "1");
            		
            		//condtion.addIntCondition("ProductBean.consumeInDay", "=", ProductConstant.PRODUCT_OLDGOOD);
            	} 
            	// 管理
            	else
            	{
            		condtion.addCondition("ProductBean.reserve4", "=", mtype);
            	}
            }
            else
            {
            	condtion.addCondition("ProductBean.reserve4", "=", mtype);
            }
        }
    }

    /**
     * @param request
     * @param condtion
     */
    private void setAbsProductInnerCondition(HttpServletRequest request, ConditionParse condtion)
    {
        String name = request.getParameter("name");

        String code = request.getParameter("code");

        if ( !StringTools.isNullOrNone(name))
        {
            condtion.addCondition("ProductBean.name", "like", name);
        }

        if ( !StringTools.isNullOrNone(code))
        {
            condtion.addCondition("ProductBean.code", "like", code);
        }

        condtion
            .addIntCondition("ProductBean.abstractType", "=", ProductConstant.ABSTRACT_TYPE_YES);

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
    public ActionForward rptQueryComposeProduct(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse reponse)
	throws ServletException
	{
        CommonTools.saveParamers(request);

        String productId = request.getParameter("productId");
        
        List<ProductVO> list = new ArrayList<ProductVO>();
        
        ComposeProductBean composeProduct = composeProductDAO.queryLatestByProduct(productId);
        
        if (null != composeProduct)
        {
        	List<ComposeItemVO> citemList = composeItemDAO.queryEntityVOsByFK(composeProduct.getId());
            
            for (ComposeItemVO each : citemList)
            {
            	ProductVO vo = productDAO.findVO(each.getProductId());
            	
            	list.add(vo);
            }
        }
        
        request.setAttribute("beanList", list);

        return mapping.findForward("rptQueryComposeProduct");
    }
    
    /**
     * 管理员增加产品(非虚拟产品)
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward addProduct(ActionMapping mapping, ActionForm form,
                                    HttpServletRequest request, HttpServletResponse response)
        throws ServletException
    {
        ProductBean bean = new ProductBean();

        // 模板最多10M
        RequestDataStream rds = new RequestDataStream(request, 1024 * 1024 * 10L);

        try
        {
            rds.parser();
        }
        catch (FileUploadBase.SizeLimitExceededException e)
        {
            _logger.error(e, e);

            request.setAttribute(KeyConstant.ERROR_MESSAGE, "增加失败:附件超过10M");

            return mapping.findForward("queryProduct");
        }
        catch (Exception e)
        {
            _logger.error(e, e);

            request.setAttribute(KeyConstant.ERROR_MESSAGE, "增加失败");

            return mapping.findForward("queryProduct");
        }

        BeanUtil.getBean(bean, rds.getParmterMap());

        bean.setId(commonDAO.getSquenceString());

        ActionForward afor = parserAttachment(mapping, request, rds, bean);

        if (afor != null)
        {
            return afor;
        }

        rds.close();

        try
        {
            User user = Helper.getUser(request);

            bean.setCreaterId(user.getStafferId());

            bean.setLogTime(TimeTools.now());

            bean.setStatus(ProductConstant.STATUS_APPLY);

            productFacade.addProductBean(user.getId(), bean);

            request.setAttribute(KeyConstant.MESSAGE, "成功保存产品");
        }
        catch (MYException e)
        {
            _logger.warn(e, e);

            request.setAttribute(KeyConstant.ERROR_MESSAGE, "保存产品失败:" + e.getMessage());
        }

        return mapping.findForward("queryProduct");
    }

    /**
     * 管理员增加产品(虚拟产品)
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward addAbstractProduct(ActionMapping mapping, ActionForm form,
                                            HttpServletRequest request, HttpServletResponse response)
        throws ServletException
    {
        ProductBean bean = new ProductBean();

        BeanUtil.getBean(bean, request);

        bean.setId(commonDAO.getSquenceString());

        setCombination(request, bean);

        try
        {
            User user = Helper.getUser(request);

            bean.setCreaterId(user.getStafferId());

            bean.setLogTime(TimeTools.now());

            bean.setStatus(ProductConstant.STATUS_APPLY);

            bean.setAbstractType(ProductConstant.ABSTRACT_TYPE_YES);

            productFacade.addProductBean(user.getId(), bean);

            request.setAttribute(KeyConstant.MESSAGE, "成功保存产品");
        }
        catch (MYException e)
        {
            _logger.warn(e, e);

            request.setAttribute(KeyConstant.ERROR_MESSAGE, "保存产品失败:" + e.getMessage());
        }

        return mapping.findForward("queryApplyProduct");
    }

    /**
     * 合成产品
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward composeProduct(ActionMapping mapping, ActionForm form,
                                        HttpServletRequest request, HttpServletResponse response)
        throws ServletException
    {
        ComposeProductBean bean = new ComposeProductBean();

        BeanUtil.getBean(bean, request);

        try
        {
            setCompose(request, bean);

            User user = Helper.getUser(request);

            bean.setStafferId(user.getStafferId());

            bean.setType(ComposeConstant.COMPOSE_TYPE_COMPOSE);

            productFacade.addComposeProduct(user.getId(), bean);

            request.setAttribute(KeyConstant.MESSAGE, "成功合成产品,合成后均价:"
                                                      + MathTools.formatNum(bean.getPrice()));
        }
        catch (MYException e)
        {
            _logger.warn(e, e);

            request.setAttribute(KeyConstant.ERROR_MESSAGE, "合成产品失败:" + e.getMessage());
        }

        return preForCompose(mapping, form, request, response);
    }

    /**
     * queryDecompose
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward queryDecompose(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
	throws ServletException
	{
        String foward = request.getParameter("foward");

        final ConditionParse condtion = new ConditionParse();

        condtion.addWhereStr();

        if ("1".equals(foward))
        {
        	StafferBean staffer = Helper.getStaffer(request);
        	
            condtion.addIntCondition("DecomposeProductBean.status", "=",
                ComposeConstant.STATUS_INDUSTRY_PASS);
            
            condtion.addCondition("StafferBean.industryId", "=", staffer.getIndustryId());
        }

        ActionTools.processJSONQueryCondition(QUERYDECOMPOSE, request, condtion);

        condtion.addCondition("order by DecomposeProductBean.logTime desc");

        String jsonstr = ActionTools.queryVOByJSONAndToString(QUERYDECOMPOSE, request, condtion,
            this.decomposeProductDAO);

        return JSONTools.writeResponse(response, jsonstr);
    }
    
    /**
     * findDecompose
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward findDecompose(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
	throws ServletException
	{
    	CommonTools.saveParamers(request);
    	
    	String id = request.getParameter("id");
    	
    	 DecomposeProductVO vo = decomposeProductDAO.findVO(id);

         if (vo == null)
         {
        	 return ActionTools.toError("数据异常,请重新操作", "queryDecompose", mapping, request);
         }

         vo.setItemVOList(composeItemDAO.queryEntityVOsByFK(id));
    	
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
    	
    	request.setAttribute("bean", vo);
    	
    	return mapping.findForward("detailDecompose");
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

        vo.setPreStatusName(DefinedCommon.getValue("composeStatus", vo.getPreStatus()));

        vo.setAfterStatusName(DefinedCommon.getValue("composeStatus", vo.getAfterStatus()));

        return vo;
    }
    
    /**
     * 产品拆分
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward deComposeProduct(ActionMapping mapping, ActionForm form,
                                        HttpServletRequest request, HttpServletResponse response)
        throws ServletException
    {
        DecomposeProductBean bean = new DecomposeProductBean();

        BeanUtil.getBean(bean, request);

        try
        {
        	setDecompose(request, bean);

            User user = Helper.getUser(request);

            bean.setStafferId(user.getStafferId());

            productFacade.addDecomposeProduct(user.getId(), bean);

            request.setAttribute(KeyConstant.MESSAGE, "成功拆分产品");
        }
        catch (MYException e)
        {
            _logger.warn(e, e);

            request.setAttribute(KeyConstant.ERROR_MESSAGE, "拆分产品失败:" + e.getMessage());
        }

        return preForDecompose(mapping, form, request, response);
    }
    
    /**
     * queryComposeFeeDefined
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward queryComposeFeeDefined(ActionMapping mapping, ActionForm form,
                                                HttpServletRequest request,
                                                HttpServletResponse response)
        throws ServletException
    {
        final ConditionParse condtion = new ConditionParse();

        condtion.addWhereStr();

        ActionTools.processJSONQueryCondition(QUERYCOMPOSEFEEDEFINED, request, condtion);

        String jsonstr = ActionTools.queryVOByJSONAndToString(QUERYCOMPOSEFEEDEFINED, request,
            condtion, this.composeFeeDefinedDAO, new HandleResult<ComposeFeeDefinedVO>()
            {
                public void handle(ComposeFeeDefinedVO obj)
                {
                    try
                    {
                        ComposeFeeDefinedVO vo = composeProductManager.findComposeFeeDefinedVO(obj
                            .getId());

                        obj.setTaxName(vo.getTaxName());
                    }
                    catch (MYException e)
                    {
                        _logger.warn(e, e);
                    }
                }
            });

        return JSONTools.writeResponse(response, jsonstr);
    }

    /**
     * addComposeFeeDefinedBean
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward addComposeFeeDefined(ActionMapping mapping, ActionForm form,
                                              HttpServletRequest request,
                                              HttpServletResponse response)
        throws ServletException
    {
        ComposeFeeDefinedBean bean = new ComposeFeeDefinedBean();

        BeanUtil.getBean(bean, request);

        try
        {
            User user = Helper.getUser(request);

            productFacade.addComposeFeeDefinedBean(user.getId(), bean);

            request.setAttribute(KeyConstant.MESSAGE, "操作成功");
        }
        catch (MYException e)
        {
            _logger.warn(e, e);

            request.setAttribute(KeyConstant.ERROR_MESSAGE, "操作失败:" + e.getMessage());
        }

        return mapping.findForward(QUERYCOMPOSEFEEDEFINED);
    }

    /**
     * findComposeFeeDefined
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward findComposeFeeDefined(ActionMapping mapping, ActionForm form,
                                               HttpServletRequest request,
                                               HttpServletResponse response)
        throws ServletException
    {
        CommonTools.saveParamers(request);

        String id = request.getParameter("id");

        ComposeFeeDefinedVO bean = null;

        try
        {
            bean = composeProductManager.findComposeFeeDefinedVO(id);
        }
        catch (MYException e)
        {
            return ActionTools.toError(e.getErrorContent(), QUERYCOMPOSEFEEDEFINED, mapping,
                request);
        }

        if (bean == null)
        {
            return ActionTools.toError("数据异常,请重新操作", QUERYCOMPOSEFEEDEFINED, mapping, request);
        }

        request.setAttribute("bean", bean);

        return mapping.findForward("updateComposeFeeDefined");
    }

    /**
     * updateComposeFeeDefinedBean
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward updateComposeFeeDefined(ActionMapping mapping, ActionForm form,
                                                 HttpServletRequest request,
                                                 HttpServletResponse response)
        throws ServletException
    {
        ComposeFeeDefinedBean bean = new ComposeFeeDefinedBean();

        BeanUtil.getBean(bean, request);

        try
        {
            User user = Helper.getUser(request);

            productFacade.updateComposeFeeDefinedBean(user.getId(), bean);

            request.setAttribute(KeyConstant.MESSAGE, "操作成功");
        }
        catch (MYException e)
        {
            _logger.warn(e, e);

            request.setAttribute(KeyConstant.ERROR_MESSAGE, "操作失败:" + e.getMessage());
        }

        return mapping.findForward(QUERYCOMPOSEFEEDEFINED);
    }

    /**
     * deleteComposeFeeDefinedBean
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward deleteComposeFeeDefined(ActionMapping mapping, ActionForm form,
                                                 HttpServletRequest request,
                                                 HttpServletResponse response)
        throws ServletException
    {
        String id = request.getParameter("id");

        AjaxResult ajax = new AjaxResult();

        try
        {
            User user = Helper.getUser(request);

            productFacade.deleteComposeFeeDefinedBean(user.getId(), id);

            ajax.setSuccess("成功操作");
        }
        catch (MYException e)
        {
            _logger.warn(e, e);

            ajax.setError("删除失败:" + e.getMessage());
        }

        return JSONTools.writeResponse(response, ajax);
    }

    /**
     * 产品调价
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward priceChange(ActionMapping mapping, ActionForm form,
                                     HttpServletRequest request, HttpServletResponse response)
        throws ServletException
    {
        PriceChangeBean bean = new PriceChangeBean();

        try
        {
            User user = Helper.getUser(request);

            setForPriceChange(request, bean);

            bean.setDescription(request.getParameter("description"));

            bean.setStafferId(user.getStafferId());

            bean.setLogTime(TimeTools.now());

            productFacade.addPriceChange(user.getId(), bean);

            request.setAttribute(KeyConstant.MESSAGE, "成功产品调价");
        }
        catch (MYException e)
        {
            _logger.warn(e, e);

            request.setAttribute(KeyConstant.ERROR_MESSAGE, "产品调价失败:" + e.getMessage());
        }

        return mapping.findForward("queryPriceChange");
    }

    /**
     * setCompose
     * 
     * @param request
     * @param bean
     */
    private void setCompose(HttpServletRequest request, ComposeProductBean bean)
        throws MYException
    {
        String dirDepotpart = request.getParameter("dirDepotpart");
        String dirProductId = request.getParameter("dirProductId");
        String dirAmount = request.getParameter("dirAmount");
        String srcDepot = request.getParameter("srcDepot");

        bean.setDepotpartId(dirDepotpart);
        bean.setDeportId(srcDepot);
        bean.setProductId(dirProductId);
        bean.setAmount(CommonTools.parseInt(dirAmount));
        bean.setLogTime(TimeTools.now());
        bean.setType(StorageConstant.OPR_STORAGE_COMPOSE);

        // 获取费用
        String[] feeItemIds = request.getParameterValues("feeItemId");
        String[] feeItems = request.getParameterValues("feeItem");
        String[] idescriptions = request.getParameterValues("idescription");

        List<ComposeFeeBean> feeList = new ArrayList<ComposeFeeBean>();

        double total = 0.0d;

        for (int i = 0; i < feeItems.length; i++ )
        {
            if ( !MathTools.equal(0.0, CommonTools.parseFloat(feeItems[i])))
            {
                ComposeFeeBean each = new ComposeFeeBean();
                each.setFeeItemId(feeItemIds[i]);
                each.setPrice(CommonTools.parseFloat(feeItems[i]));
                each.setLogTime(bean.getLogTime());
                each.setDescription(idescriptions[i]);
                feeList.add(each);

                total += each.getPrice();
            }
        }

        bean.setFeeList(feeList);

        String[] srcDepotparts = request.getParameterValues("srcDepotpart");
        String[] srcProductIds = request.getParameterValues("srcProductId");
        String[] srcAmounts = request.getParameterValues("useAmount");
        String[] srcPrices = request.getParameterValues("srcPrice");
        String[] srcRelations = request.getParameterValues("srcRelation");
        String[] srcInputRates = request.getParameterValues("srcInputRate");
        
        List<ComposeItemBean> itemList = new ArrayList<ComposeItemBean>();

        for (int i = 0; i < srcRelations.length; i++ )
        {
            if (StringTools.isNullOrNone(srcDepotparts[i]))
            {
                continue;
            }

            if (bean.getProductId().equals(srcProductIds[i]))
            {
                throw new MYException("产品不能自己合成自己");
            }

            ComposeItemBean each = new ComposeItemBean();
            each.setAmount(CommonTools.parseInt(srcAmounts[i]));
            each.setDeportId(srcDepot);
            each.setDepotpartId(srcDepotparts[i]);
            each.setLogTime(bean.getLogTime());
            each.setPrice(CommonTools.parseFloat(srcPrices[i]));
            each.setProductId(srcProductIds[i]);
            each.setRelationId(srcRelations[i]);
            each.setInputRate(CommonTools.parseFloat(srcInputRates[i]));

            itemList.add(each);

            total += each.getPrice() * each.getAmount();
        }

        bean.setItemList(itemList);

        // 计算新产品的成本价
        double price = total / bean.getAmount();

        bean.setPrice(price);
    }

    /**
     * setDecompose
     * 
     * @param request
     * @param bean
     */
    private void setDecompose(HttpServletRequest request, DecomposeProductBean bean)
        throws MYException
    {
        String depotpart = request.getParameter("depotpart");
        String productId = request.getParameter("productId");
        String amount = request.getParameter("amount");
        String depot = request.getParameter("depot");
        String price = request.getParameter("price");

        bean.setDepotpartId(depotpart);
        bean.setDeportId(depot);
        bean.setProductId(productId);
        bean.setAmount(CommonTools.parseInt(amount));
        bean.setPrice(CommonTools.parseFloat(price));
        bean.setLogTime(TimeTools.now());
        bean.setType(StorageConstant.OPR_STORAGE_DECOMPOSE);

        double total = 0.0d;

        String[] srcDepots = request.getParameterValues("srcDepot");
        String[] srcDepotparts = request.getParameterValues("srcDepotpart");
        String[] srcProductIds = request.getParameterValues("srcProductId");
        String[] srcAmounts = request.getParameterValues("srcAmount");
        String[] srcPrices = request.getParameterValues("srcPrice");
        String[] stypes = request.getParameterValues("stype");
        
        List<ComposeItemBean> itemList = new ArrayList<ComposeItemBean>();

        for (int i = 0; i < srcProductIds.length; i++ )
        {
            if (StringTools.isNullOrNone(srcProductIds[i]))
            {
                continue;
            }

            if (bean.getProductId().equals(srcProductIds[i]))
            {
                throw new MYException("产品不能自己拆分自己");
            }

            ComposeItemBean each = new ComposeItemBean();
            
            each.setAmount(CommonTools.parseInt(srcAmounts[i]));
            each.setDeportId(srcDepots[i]);
            each.setDepotpartId(srcDepotparts[i]);
            each.setLogTime(bean.getLogTime());
            each.setPrice(CommonTools.parseFloat(srcPrices[i]));
            each.setProductId(srcProductIds[i]);
            each.setStype(CommonTools.parseInt(stypes[i]));

            itemList.add(each);

            total += each.getPrice() * each.getAmount();
        }

        bean.setItemList(itemList);

        // 拆分的金额要与成品一样
        double cha = Math.abs(total - bean.getAmount() * bean.getPrice());
        
        if (cha > 1)
        {
        	throw new MYException("拆分后配件金额[%.2f]须与成品[%.2f]一样", total,bean.getAmount() * bean.getPrice() );
        }
    }
    
    /**
     * 拆分导出
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward exportDecompose(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws ServletException
	{
		OutputStream out = null;

		String filenName = "Decompose_" + TimeTools.now("MMddHHmmss") + ".csv";

		response.setContentType("application/x-dbf");

		response.setHeader("Content-Disposition", "attachment; filename="
				+ filenName);

		WriteFile write = null;

		ConditionParse condtion = JSONPageSeparateTools.getCondition(request,
				QUERYDECOMPOSE);

		int count = decomposeProductDAO.countVOByCondition(condtion.toString());

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

			write.writeLine("标识,拆分产品编码,拆分产品,数量,最终价格,拆分人,状态,仓库,目的仓区,时间,源产品仓库,源产品仓区,源产品,源产品数量,源产品价格");

			PageSeparate page = new PageSeparate();

			page.reset2(count, 2000);

			WriteFileBuffer line = new WriteFileBuffer(write);

			while (page.nextPage())
			{
				List<DecomposeProductVO> voFList = decomposeProductDAO.queryEntityVOsByCondition(
						condtion, page);

				for (DecomposeProductVO each : voFList)
				{
					List<ComposeItemVO> itemList = composeItemDAO.queryEntityVOsByFK(each.getId());
					
					for (ComposeItemVO item : itemList)
					{
						line.reset();

						line.writeColumn(each.getId());
						line.writeColumn(each.getProductCode());
						line.writeColumn(each.getProductName());
						line.writeColumn(each.getAmount());
						line.writeColumn(each.getPrice());
						line.writeColumn(each.getStafferName());
						line.writeColumn(DefinedCommon.getValue("composeStatus", each.getStatus()));
						line.writeColumn(each.getDeportName());
						line.writeColumn(each.getDepotpartName());
						line.writeColumn(each.getLogTime());
						
						line.writeColumn(item.getDeportName());
						line.writeColumn(item.getDepotpartName());
						line.writeColumn(item.getProductName());
						line.writeColumn(item.getAmount());
						line.writeColumn(item.getPrice());
						
						line.writeLine();
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
     * setForPriceChange
     * 
     * @param request
     * @param bean
     */
    private void setForPriceChange(HttpServletRequest request, PriceChangeBean bean)
        throws MYException
    {
        // 获取费用
        String[] relations = request.getParameterValues("relation_id");
        String[] oprices = request.getParameterValues("old_price");

        String[] changeAmounts = request.getParameterValues("changeAmount");
        String[] changePrices = request.getParameterValues("changePrice");

        List<PriceChangeSrcItemBean> srcList = new ArrayList<PriceChangeSrcItemBean>();
        bean.setSrcList(srcList);

        List<PriceChangeNewItemBean> newList = new ArrayList<PriceChangeNewItemBean>();
        bean.setNewList(newList);

        if (relations == null)
        {
            throw new MYException("没有调价的产品,请重新操作");
        }

        for (int i = 0; i < relations.length; i++ )
        {
            // 没有修改价格
            if (CommonTools.parseFloat(oprices[i]) == CommonTools.parseFloat(changePrices[i]))
            {
                continue;
            }

            String id = relations[i];

            StorageRelationBean relation = storageRelationDAO.find(id);

            if (relation == null)
            {
                throw new MYException("库存不存在,请重新操作");
            }

            if (CommonTools.parseInt(changeAmounts[i]) > relation.getAmount())
            {
                throw new MYException("调价数量不能大于总数量,请重新操作");
            }

            String refId = commonDAO.getSquenceString();

            // 修改价格
            PriceChangeSrcItemBean src = new PriceChangeSrcItemBean();
            src.setAmount(relation.getAmount());
            src.setDeportId(relation.getLocationId());
            src.setDepotpartId(relation.getDepotpartId());
            src.setPrice(relation.getPrice());
            src.setProductId(relation.getProductId());
            src.setRefId(refId);

            // 使用RelationId更新
            src.setRelationId(relation.getId());
            src.setStafferId(relation.getStafferId());
            src.setStorageId(relation.getStorageId());
            srcList.add(src);

            // 调价后的库存
            PriceChangeNewItemBean item = new PriceChangeNewItemBean();
            item.setAmount(CommonTools.parseInt(changeAmounts[i]));
            item.setDeportId(relation.getLocationId());
            item.setDepotpartId(relation.getDepotpartId());
            // 改变价格
            item.setPrice(CommonTools.parseFloat(changePrices[i]));
            item.setProductId(relation.getProductId());
            item.setRefId(refId);
            item.setStafferId(relation.getStafferId());
            item.setStorageId(relation.getStorageId());

            newList.add(item);

            // 保持原价的
            if (CommonTools.parseInt(changeAmounts[i]) != relation.getAmount())
            {
                PriceChangeNewItemBean item_old = new PriceChangeNewItemBean();
                // 剩余的数量
                item_old.setAmount(relation.getAmount() - CommonTools.parseInt(changeAmounts[i]));
                item_old.setDeportId(relation.getLocationId());
                item_old.setDepotpartId(relation.getDepotpartId());
                // 原价
                item_old.setPrice(relation.getPrice());
                item_old.setProductId(relation.getProductId());
                item_old.setRefId(refId);
                item_old.setStafferId(relation.getStafferId());
                item_old.setStorageId(relation.getStorageId());

                newList.add(item_old);
            }
        }

        if (ListTools.isEmptyOrNull(bean.getSrcList()))
        {
            throw new MYException("没有调价操作,请重新操作");
        }
    }

    private void setCombination(HttpServletRequest request, ProductBean bean)
    {
        List<ProductCombinationBean> vsList = new ArrayList();

        User user = Helper.getUser(request);

        bean.setVsList(vsList);

        // 获取组合方式 ProductCombinationBean
        String[] srcProductIds = request.getParameterValues("srcProductId");

        // String[] srcAmounts = request.getParameterValues("srcAmount");

        for (int i = 0; i < srcProductIds.length; i++ )
        {
            ProductCombinationBean com = new ProductCombinationBean();

            com.setAmount(1);
            com.setVproductId(bean.getId());
            com.setSproductId(srcProductIds[i]);
            com.setCreaterId(user.getStafferId());

            vsList.add(com);
        }
    }

    /**
     * query depotpart for compose
     * 
     * @param mapping
     * @param form
     * @param request
     * @param reponse
     * @return
     * @throws ServletException
     */
    public ActionForward preForCompose(ActionMapping mapping, ActionForm form,
                                       HttpServletRequest request, HttpServletResponse response)
        throws ServletException
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

        List<ComposeFeeDefinedBean> feeList = composeFeeDefinedDAO.listEntityBeans();

        request.setAttribute("feeList", feeList);

        return mapping.findForward("composeProduct");
    }
    
    /**
     * query depotpart for decompose
     * 
     * @param mapping
     * @param form
     * @param request
     * @param reponse
     * @return
     * @throws ServletException
     */
    public ActionForward preForDecompose(ActionMapping mapping, ActionForm form,
                                       HttpServletRequest request, HttpServletResponse response)
        throws ServletException
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

        return mapping.findForward("decomposeProduct50");
    }

    /**
     * preForPriceChange
     * 
     * @param mapping
     * @param form
     * @param request
     * @param reponse
     * @return
     * @throws ServletException
     */
    public ActionForward preForAddPriceChange(ActionMapping mapping, ActionForm form,
                                              HttpServletRequest request,
                                              HttpServletResponse response)
        throws ServletException
    {
        User user = Helper.getUser(request);

        if ( !productFacade.isStorageRelationLock())
        {
            return ActionTools.toError("库存未锁定,请先锁定库存再调价", "queryPriceChange", mapping, request);
        }

        String products = request.getParameter("products");

        String[] split = products.split("\r\n");

        if (split.length > 100)
        {
            return ActionTools.toError("不能超过100个产品", "queryPriceChange", mapping, request);
        }

        Set<String> set = new HashSet<String>();

        for (String string : split)
        {
            if ( !StringTools.isNullOrNone(string))
            {
                set.add(string.trim());
            }
        }

        StringBuffer error = new StringBuffer();

        List<StorageRelationVO> relationList = new LinkedList<StorageRelationVO>();

        int mtype = -1;

        for (String each : set)
        {
            if (StringTools.isNullOrNone(each))
            {
                continue;
            }

            ProductBean product = productDAO.findByUnique(each.trim());

            // 忽略
            if (product == null)
            {
                error.append("产品[" + each.trim() + "]不存在").append("<br>");
                continue;
            }

            // 可以调价
            if (product.getAdjustPrice() != ProductConstant.ADJUSTPRICE_YES)
            {
                error.append("产品[" + product.getCode() + "]不支持调价").append("<br>");
                continue;
            }

            if (OATools.getManagerFlag())
            {
                if (mtype == -1)
                {
                    mtype = CommonTools.parseInt(product.getReserve4());
                }
                else
                {
                    if (mtype != CommonTools.parseInt(product.getReserve4()))
                    {
                        error.append("产品[" + product.getCode() + "]管理属性不匹配").append("<br>");
                        continue;
                    }
                }
            }

            ConditionParse condition = new ConditionParse();

            // 数量大于0的库存
            condition.addIntCondition("StorageRelationBean.amount", ">", 0);

            // 良品仓的
            condition.addIntCondition("DepotpartBean.type", "=", DepotConstant.DEPOTPART_TYPE_OK);

            // 公共的库存
            condition.addCondition("StorageRelationBean.stafferId", "=", "0");

            condition.addCondition("StorageRelationBean.productId", "=", product.getId());

            List<StorageRelationVO> eachList = storageRelationDAO
                .queryEntityVOsByCondition(condition);

            for (Iterator iterator = eachList.iterator(); iterator.hasNext();)
            {
                StorageRelationVO vo = (StorageRelationVO)iterator.next();

                vo.setStafferName("公共");

                // 过滤预占的
                int inWay = Math.abs(productFacade.onPriceChange2(user.getId(), vo));

                if (vo.getAmount() > inWay)
                {
                    vo.setAmount(vo.getAmount() - inWay);

                    vo.setErrorAmount(inWay);
                }
                else
                {
                    iterator.remove();
                }
            }

            relationList.addAll(eachList);
        }

        Collections.sort(relationList, new Comparator<StorageRelationVO>()
        {
            public int compare(StorageRelationVO o1, StorageRelationVO o2)
            {
                int a = o1.getProductId().compareTo(o2.getProductId());

                if (a != 0)
                {
                    return a;
                }

                return o1.getLocationId().compareTo(o2.getLocationId());
            }
        });

        request.setAttribute("relationList", relationList);

        request.setAttribute(KeyConstant.ERROR_MESSAGE, error.toString());

        return mapping.findForward("priceChange");
    }

    /**
     * 管理员修改产品(非虚拟产品)
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward updateProduct(ActionMapping mapping, ActionForm form,
                                       HttpServletRequest request, HttpServletResponse response)
        throws ServletException
    {
        // 模板最多10M
        RequestDataStream rds = new RequestDataStream(request, 1024 * 1024 * 10L);

        try
        {
            rds.parser();
        }
        catch (FileUploadBase.SizeLimitExceededException e)
        {
            _logger.error(e, e);

            request.setAttribute(KeyConstant.ERROR_MESSAGE, "增加失败:附件超过10M");

            return mapping.findForward("queryProduct");
        }
        catch (Exception e)
        {
            _logger.error(e, e);

            request.setAttribute(KeyConstant.ERROR_MESSAGE, "增加失败");

            return mapping.findForward("queryProduct");
        }

        String id = rds.getParmterMap().get("id");

        ProductBean bean = productDAO.find(id);

        if (bean == null)
        {
            request.setAttribute(KeyConstant.ERROR_MESSAGE, "数据错误,请确认操作");

            return mapping.findForward("queryProduct");
        }

        BeanUtil.getBean(bean, rds.getParmterMap());

        ActionForward afor = parserAttachment(mapping, request, rds, bean);

        if (afor != null)
        {
            return afor;
        }

        rds.close();

        try
        {
            User user = Helper.getUser(request);

            productFacade.updateProductBean(user.getId(), bean);

            request.setAttribute(KeyConstant.MESSAGE, "成功修改产品");
        }
        catch (MYException e)
        {
            _logger.warn(e, e);

            request.setAttribute(KeyConstant.ERROR_MESSAGE, "修改产品失败:" + e.getMessage());
        }

        return mapping.findForward("queryProduct");
    }

    private ActionForward parserAttachment(ActionMapping mapping, HttpServletRequest request,
                                           RequestDataStream rds, ProductBean bean)
    {
        if ( !rds.haveStream())
        {
            return null;
        }

        FileOutputStream out = null;

        UtilStream ustream = null;

        try
        {
            String rabsPath = '/'
                              + bean.getId()
                              + "."
                              + FileTools.getFilePostfix(
                                  FileTools.getFileName(rds.getUniqueFileName())).toLowerCase();

            String filePath = this.getPicPath() + '/' + rabsPath;

            bean.setPicPath(rabsPath);

            out = new FileOutputStream(filePath);

            ustream = new UtilStream(rds.getUniqueInputStream(), out);

            ustream.copyStream();
        }
        catch (IOException e)
        {
            _logger.error(e, e);

            request.setAttribute(KeyConstant.ERROR_MESSAGE, "保存失败");

            return mapping.findForward("queryProduct");
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

        return null;
    }

    /**
     * findProduct
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward findProduct(ActionMapping mapping, ActionForm form,
                                     HttpServletRequest request, HttpServletResponse response)
        throws ServletException
    {
        String id = request.getParameter("id");

        ProductVO bean = productDAO.findVO(id);

        if (bean == null)
        {
            return ActionTools.toError("数据异常,请重新操作", "queryProduct", mapping, request);
        }

        request.setAttribute("bean", bean);

        String rootUrl = RequestTools.getRootUrl(request);

        request.setAttribute("rootUrl", rootUrl);

        setProviderName(bean);

        String update = request.getParameter("update");

        if ("1".equals(update))
        {
            if (bean.getAbstractType() == ProductConstant.ABSTRACT_TYPE_YES)
            {
                return ActionTools.toError("虚拟产品不能修改", "queryProduct", mapping, request);
            }

            return mapping.findForward("updateProduct");
        }

        // 销售范围
        List<ProductVSLocationVO> voList = productVSLocationDAO.queryEntityVOsByFK(id);

        StringBuilder builder = new StringBuilder();
        for (ProductVSLocationVO productVSLocationVO : voList)
        {
            builder.append(productVSLocationVO.getLocationName()).append(" ");
        }

        // 虚拟产品的处理
        if (bean.getAbstractType() == ProductConstant.ABSTRACT_TYPE_YES)
        {
            // 获取组合方式
            List<ProductCombinationVO> comVOList = productCombinationDAO.queryEntityVOsByFK(id);

            request.setAttribute("comVOList", comVOList);
        }

        request.setAttribute("locationNames", builder);
        
        List<InvoiceBean> invoiceList = invoiceDAO.listEntityBeans();

        request.setAttribute("invoiceList", invoiceList);

        return mapping.findForward("detailProduct");
    }

    /**
     * findCompose
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward findCompose(ActionMapping mapping, ActionForm form,
                                     HttpServletRequest request, HttpServletResponse response)
        throws ServletException
    {
        CommonTools.saveParamers(request);

        String id = request.getParameter("id");

        ComposeProductVO bean = productFacade.findComposeById(id);

        if (bean == null)
        {
            return ActionTools.toError("数据异常,请重新操作", "queryCompose", mapping, request);
        }

        List<FinanceBean> financeBeanList = financeDAO.queryRefFinanceItemByRefId(id);

//        if (financeBeanList.size() > 0)
//        {
//            bean.setOtherId(financeBeanList.get(0).getId());
//        }
        
        request.setAttribute("financeBeanList", financeBeanList);

        request.setAttribute("bean", bean);

        return mapping.findForward("detailCompose");
    }

    /**
     * findPriceChange
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward findPriceChange(ActionMapping mapping, ActionForm form,
                                         HttpServletRequest request, HttpServletResponse response)
        throws ServletException
    {
        CommonTools.saveParamers(request);

        String id = request.getParameter("id");

        PriceChangeVO bean = productFacade.findPriceChangeById(id);

        if (bean == null)
        {
            return ActionTools.toError("数据异常,请重新操作", "queryPriceChange", mapping, request);
        }

        List<PriceChangeSrcItemVO> srcVOList = bean.getSrcVOList();
        List<PriceChangeNewItemVO> newVOList = bean.getNewVOList();

        Map<String, List<PriceChangeNewItemVO>> map = new HashMap<String, List<PriceChangeNewItemVO>>();

        for (PriceChangeSrcItemVO each : srcVOList)
        {
            if (StringTools.isNullOrNone(each.getStafferName()))
            {
                each.setStafferName("公共");
            }

            map.put(each.getRefId(), new ArrayList<PriceChangeNewItemVO>());

            for (PriceChangeNewItemVO newEach : newVOList)
            {
                if (StringTools.isNullOrNone(newEach.getStafferName()))
                {
                    newEach.setStafferName("公共");
                }

                if (newEach.getRefId().equals(each.getRefId()))
                {
                    map.get(each.getRefId()).add(newEach);
                }
            }
        }

        List<FinanceBean> financeBeanList = financeDAO.queryRefFinanceItemByRefId(id);

        if (financeBeanList.size() > 0)
        {
            bean.setOtherId(financeBeanList.get(0).getId());
        }

        request.setAttribute("map", map);
        request.setAttribute("bean", bean);

        return mapping.findForward("detailPriceChange");
    }

    private void setProviderName(ProductVO bean)
    {
        // 查询4个供应商
        if ( !StringTools.isNullOrNone(bean.getMainProvider()))
        {
            ProviderBean pro = providerDAO.find(bean.getMainProvider());

            if (pro != null)
            {
                bean.setMainProviderName(pro.getName());
            }
        }

        if ( !StringTools.isNullOrNone(bean.getAssistantProvider1()))
        {
            ProviderBean pro = providerDAO.find(bean.getAssistantProvider1());

            if (pro != null)
            {
                bean.setAssistantProviderName1(pro.getName());
            }
        }

        if ( !StringTools.isNullOrNone(bean.getAssistantProvider2()))
        {
            ProviderBean pro = providerDAO.find(bean.getAssistantProvider2());

            if (pro != null)
            {
                bean.setAssistantProviderName2(pro.getName());
            }
        }

        if ( !StringTools.isNullOrNone(bean.getAssistantProvider3()))
        {
            ProviderBean pro = providerDAO.find(bean.getAssistantProvider3());

            if (pro != null)
            {
                bean.setAssistantProviderName3(pro.getName());
            }
        }

        if ( !StringTools.isNullOrNone(bean.getAssistantProvider4()))
        {
            ProviderBean pro = providerDAO.find(bean.getAssistantProvider4());

            if (pro != null)
            {
                bean.setAssistantProviderName4(pro.getName());
            }
        }
    }

    /**
     * configProductVSLocation
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward configProductVSLocation(ActionMapping mapping, ActionForm form,
                                                 HttpServletRequest request,
                                                 HttpServletResponse response)
        throws ServletException
    {
        String id = request.getParameter("id");

        String newLocationIds = request.getParameter("newLocationIds");

        String[] split = newLocationIds.split("~");

        List<ProductVSLocationBean> vsList = new ArrayList();

        for (String each : split)
        {
            if ( !StringTools.isNullOrNone(each))
            {
                ProductVSLocationBean vs = new ProductVSLocationBean();

                vs.setLocationId(each);
                vs.setProductId(id);

                vsList.add(vs);
            }
        }

        AjaxResult ajax = new AjaxResult();

        try
        {
            User user = Helper.getUser(request);

            productFacade.configProductVSLocation(user.getId(), id, vsList);

            ajax.setSuccess("成功配置产品的销售范围");
        }
        catch (MYException e)
        {
            _logger.warn(e, e);

            ajax.setError("配置产品的销售范围失败:" + e.getMessage());
        }

        return JSONTools.writeResponse(response, ajax);
    }

    /**
     * configProductVSLocation
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward configPrice(ActionMapping mapping, ActionForm form,
                                     HttpServletRequest request, HttpServletResponse response)
        throws ServletException
    {
        String id = request.getParameter("id");

        String batchPrice = request.getParameter("batchPrice");

        String sailPrice = request.getParameter("sailPrice");

        ProductBean bean = productDAO.find(id);

        AjaxResult ajax = new AjaxResult();

        if (bean == null)
        {
            ajax.setError("数据不存在,请重新操作");

            return JSONTools.writeResponse(response, ajax);
        }

        double newBatchPrice = CommonTools.parseFloat(batchPrice);
        double newSailPrice = CommonTools.parseFloat(sailPrice);

        bean.setBatchPrice(newBatchPrice);
        bean.setSailPrice(newSailPrice);

        try
        {
            User user = Helper.getUser(request);

            productFacade.updateProductBean(user.getId(), bean);

            ajax.setSuccess("成功配置产品");
        }
        catch (MYException e)
        {
            _logger.warn(e, e);

            ajax.setError("配置产品的失败:" + e.getMessage());
        }

        return JSONTools.writeResponse(response, ajax);
    }

    /**
     * 准备配置产品和分公司的关系
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward preForConfigProductVSLocation(ActionMapping mapping, ActionForm form,
                                                       HttpServletRequest request,
                                                       HttpServletResponse response)
        throws ServletException
    {
        AjaxResult ajax = new AjaxResult();

        String id = request.getParameter("id");

        List<ProductVSLocationBean> beanList = productVSLocationDAO.queryEntityBeansByFK(id);

        request.setAttribute("beanList", beanList);

        List<PrincipalshipBean> locationList = orgManager.listAllIndustry();

        for (PrincipalshipBean locationBean : locationList)
        {
            locationBean.setLevel(0);

            for (ProductVSLocationBean vs : beanList)
            {
                if (vs.getLocationId().equals(locationBean.getId()))
                {
                    locationBean.setLevel(1);
                }
            }
        }

        ajax.setSuccess(locationList);

        return JSONTools.writeResponse(response, ajax);

    }

    /**
     * deleteProduct
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward rollbackPriceChange(ActionMapping mapping, ActionForm form,
                                             HttpServletRequest request,
                                             HttpServletResponse response)
        throws ServletException
    {
        String id = request.getParameter("id");

        AjaxResult ajax = new AjaxResult();

        if ( !productFacade.isStorageRelationLock())
        {
            ajax.setError("库存未锁定,请先锁定库存再回滚调价");

            return JSONTools.writeResponse(response, ajax);
        }

        try
        {
            User user = Helper.getUser(request);

            productFacade.rollbackPriceChange(user.getId(), id);

            ajax.setSuccess("成功回滚调价");
        }
        catch (MYException e)
        {
            _logger.warn(e, e);

            ajax.setError("回滚调价失败:" + e.getMessage());
        }

        return JSONTools.writeResponse(response, ajax);
    }

    /**
     * deleteProduct
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward deleteProduct(ActionMapping mapping, ActionForm form,
                                       HttpServletRequest request, HttpServletResponse response)
        throws ServletException
    {
        String id = request.getParameter("id");

        AjaxResult ajax = new AjaxResult();

        try
        {
            User user = Helper.getUser(request);

            productFacade.deleteProductBean(user.getId(), id);

            ajax.setSuccess("成功操作");
        }
        catch (MYException e)
        {
            _logger.warn(e, e);

            ajax.setError("删除产品失败:" + e.getMessage());
        }

        return JSONTools.writeResponse(response, ajax);
    }

    /**
     * passComposeBean
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward passCompose(ActionMapping mapping, ActionForm form,
                                     HttpServletRequest request, HttpServletResponse response)
        throws ServletException
    {
        String id = request.getParameter("id");

        AjaxResult ajax = new AjaxResult();

        try
        {
            User user = Helper.getUser(request);

            productFacade.passComposeProduct(user.getId(), id);

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
     * lastPassComposeBean
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward lastPassCompose(ActionMapping mapping, ActionForm form,
                                         HttpServletRequest request, HttpServletResponse response)
        throws ServletException
    {
        String id = request.getParameter("id");

        AjaxResult ajax = new AjaxResult();

        try
        {
            User user = Helper.getUser(request);

            productFacade.lastPassComposeProduct(user.getId(), id);

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
     * deleteComposeBean
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward deleteCompose(ActionMapping mapping, ActionForm form,
                                       HttpServletRequest request, HttpServletResponse response)
        throws ServletException
    {
        String id = request.getParameter("id");

        AjaxResult ajax = new AjaxResult();

        try
        {
            User user = Helper.getUser(request);

            productFacade.rejectComposeProduct(user.getId(), id);

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
     * rollbackComposeProduct
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward rollbackComposeProduct(ActionMapping mapping, ActionForm form,
                                                HttpServletRequest request,
                                                HttpServletResponse response)
        throws ServletException
    {
        String id = request.getParameter("id");

        AjaxResult ajax = new AjaxResult();

        try
        {
            User user = Helper.getUser(request);

            productFacade.rollbackComposeProduct(user.getId(), id);

            ajax.setSuccess("成功操作");
        }
        catch (MYException e)
        {
            _logger.warn(e, e);

            ajax.setError("操作失败:" + e.getMessage());
        }

        return JSONTools.writeResponse(response, ajax);
    }

    /***
     * 产品拆分(事业部经理审批)
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward passDecompose(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
	throws ServletException
	{
        String id = request.getParameter("id");
        
        String reason = request.getParameter("reason");

        try
        {
            User user = Helper.getUser(request);

            productFacade.passDecomposeProduct(user.getId(), id, reason);

            request.setAttribute(KeyConstant.MESSAGE, "成功操作");
        }
        catch (MYException e)
        {
            _logger.warn(e, e);

            request.setAttribute(KeyConstant.MESSAGE, "操作失败:" + e.getMessage());
        }
        
        request.setAttribute("forward", "1");

        return mapping.findForward("queryDecompose");
    }
    
    /**
     * rejectDecompose
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward rejectDecompose(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
	throws ServletException
	{
        String id = request.getParameter("id");
        
        String reason = request.getParameter("reason");

        try
        {
            User user = Helper.getUser(request);

            productFacade.rejectDecomposeProduct(user.getId(), id, reason);

            request.setAttribute(KeyConstant.MESSAGE, "成功操作");
        }
        catch (MYException e)
        {
            _logger.warn(e, e);

            request.setAttribute(KeyConstant.MESSAGE, "操作失败:" + e.getMessage());
        }
        
        request.setAttribute("forward", "1");

        return mapping.findForward("queryDecompose");
    }
    
    /**
     * deleteProduct
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward passApplyProduct(ActionMapping mapping, ActionForm form,
                                          HttpServletRequest request, HttpServletResponse response)
        throws ServletException
    {
        String id = request.getParameter("id");

        AjaxResult ajax = new AjaxResult();

        try
        {
            User user = Helper.getUser(request);

            productFacade.changeProductStatus(user.getId(), id, ProductConstant.STATUS_APPLY,
                ProductConstant.STATUS_COMMON);

            ajax.setSuccess("成功通过此申请");
        }
        catch (MYException e)
        {
            _logger.warn(e, e);

            ajax.setError("操作失败:" + e.getMessage());
        }

        return JSONTools.writeResponse(response, ajax);
    }

    /**
     * 准备配置金银价
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward preForConfigGoldSilverPrice(ActionMapping mapping, ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response)
	throws ServletException
	{
    	AjaxResult ajax = new AjaxResult();

    	GoldSilverPriceBean bean = null;

    	List<GoldSilverPriceBean> list = goldSilverPriceDAO.listEntityBeans();

    	if (!ListTools.isEmptyOrNull(list))
    	{
    		bean = list.get(0);
    	}
    	else
    	{
    		bean = new GoldSilverPriceBean();
    		
    		bean.setGold(0.0d);
    		
    		bean.setSilver(0.0d);
    	}

    	ajax.setSuccess(bean);

    	return JSONTools.writeResponse(response, ajax);

	}
    
    /***
     * 更新金银价
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward configGoldSilverPrice(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
	throws ServletException
	{
    	String goldPrice = request.getParameter("goldPrice");

    	String silverPrice = request.getParameter("silverPrice");

    	GoldSilverPriceBean bean = new GoldSilverPriceBean();

    	AjaxResult ajax = new AjaxResult();

    	double newGoldPrice = CommonTools.parseFloat(goldPrice);
    	double newSilverPrice = CommonTools.parseFloat(silverPrice);

    	bean.setGold(newGoldPrice);
    	bean.setSilver(newSilverPrice);

    	try
    	{
	    	User user = Helper.getUser(request);
	
	    	productManager.configGoldSilverPrice(user, bean);
	
	    	ajax.setSuccess("成功配置金银价");
    	}
    	catch (MYException e)
    	{
	    	_logger.warn(e, e);
	
	    	ajax.setError("配置金银价的失败:" + e.getMessage());
    	}

    	return JSONTools.writeResponse(response, ajax);
	}
    
    /**
     * 导入中信产品
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward importCiticProduct(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
	throws ServletException
	{
		User user = Helper.getUser(request);
		
        RequestDataStream rds = new RequestDataStream(request);
        
        boolean importError = false;
        
        List<CiticVSOAProductBean> importItemList = new ArrayList<CiticVSOAProductBean>(); 
        
        StringBuilder builder = new StringBuilder();       
        
        try
        {
            rds.parser();
        }
        catch (Exception e1)
        {
            _logger.error(e1, e1);

            request.setAttribute(KeyConstant.ERROR_MESSAGE, "解析失败");

            return mapping.findForward("importCiticProduct");
        }

        if ( !rds.haveStream())
        {
            request.setAttribute(KeyConstant.ERROR_MESSAGE, "解析失败");

            return mapping.findForward("importCiticProduct");
        }
        
        // 获取上次最后一次导入的时间
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
                	CiticVSOAProductBean bean = new CiticVSOAProductBean();
                    
                	// OA产品
            		if ( !StringTools.isNullOrNone(obj[0]))
            		{
            			bean.setProductCode(obj[0]);
            		}else{
            			importError = true;
            			
                        builder
                        .append("第[" + currentNumber + "]错误:")
                        .append("OA产品代码为空")
                        .append("<br>");
            		}
            		
                	// 
            		if ( !StringTools.isNullOrNone(obj[1]))
            		{
            			bean.setProductName(obj[1]);
            		}else{
            			importError = true;
            			
                        builder
                        .append("第[" + currentNumber + "]错误:")
                        .append("OA产品品名为空")
                        .append("<br>");
            		}
            		
                	// 产品
            		if ( !StringTools.isNullOrNone(obj[2]))
            		{
            			bean.setCiticProductCode(obj[2]);
            		}else
            		{
            			importError = true;
            			
                        builder
                        .append("第[" + currentNumber + "]错误:")
                        .append("中信产品code为空")
                        .append("<br>");
            		}

            		// 价格
            		if ( !StringTools.isNullOrNone(obj[3]))
            		{
            			bean.setCiticProductName(obj[3]);
            		}else
            		{
            			importError = true;
            			
                        builder
                        .append("第[" + currentNumber + "]错误:")
                        .append("中信品名为空")
                        .append("<br>");
            		}

            		// 姓氏
            		if ( !StringTools.isNullOrNone(obj[4]))
            		{
            			bean.setFirstName(obj[4]);
            		}else
            		{
            			bean.setFirstName("N/A");
            		}
                    
                    importItemList.add(bean);
                }
                else
                {
                    builder
                        .append("第[" + currentNumber + "]错误:")
                        .append("数据长度不足5格错误")
                        .append("<br>");
                    
                    importError = true;
                }
            }
            
            
        }catch (Exception e)
        {
            _logger.error(e, e);
            
            request.setAttribute(KeyConstant.ERROR_MESSAGE, e.toString());

            return mapping.findForward("importCiticProduct");
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

            return mapping.findForward("importCiticProduct");
        }
        
        try
        {
        	productManager.importCiticProduct(user, importItemList);
        }
        catch(MYException e)
        {
            request.setAttribute(KeyConstant.ERROR_MESSAGE, "导入出错:"+ e.getErrorContent());

            return mapping.findForward("importCiticProduct");
        }
        
        request.setAttribute(KeyConstant.MESSAGE, "导入成功");
        
        return mapping.findForward("importCiticProduct");
	
	}
    
    private String[] fillObj(String[] obj)
    {
        String[] result = new String[5];

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
     * queryCiticProduct
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward queryCiticProduct(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
	throws ServletException
	{
        ConditionParse condtion = new ConditionParse();

        condtion.addWhereStr();

        ActionTools.processJSONQueryCondition(QUERYCITICPRODUCT, request, condtion);

        String jsonstr = ActionTools.queryVOByJSONAndToString(QUERYCITICPRODUCT, request, condtion,
            this.citicVSOAProductDAO);

        return JSONTools.writeResponse(response, jsonstr);
	}
    
    /**
     * deleteCiticProduct
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward deleteCiticProduct(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
	throws ServletException
	{
		AjaxResult ajax = new AjaxResult();
		
		try
		{
			String id = request.getParameter("id");
			
			User user = Helper.getUser(request);
			
			productManager.deleteCiticProductBean(user, id);
			
			ajax.setSuccess("成功删除");
		}
		catch (MYException e)
		{
			_logger.warn(e, e);
			
			ajax.setError("删除失败:" + e.getMessage());
		}
		
		return JSONTools.writeResponse(response, ajax);
	}
    
    public String getPicPath()
    {
        return ConfigLoader.getProperty("picPath");
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
     * @return the productManager
     */
    public ProductManager getProductManager()
    {
        return productManager;
    }

    /**
     * @param productManager
     *            the productManager to set
     */
    public void setProductManager(ProductManager productManager)
    {
        this.productManager = productManager;
    }

    /**
     * @return the productCombinationDAO
     */
    public ProductCombinationDAO getProductCombinationDAO()
    {
        return productCombinationDAO;
    }

    /**
     * @param productCombinationDAO
     *            the productCombinationDAO to set
     */
    public void setProductCombinationDAO(ProductCombinationDAO productCombinationDAO)
    {
        this.productCombinationDAO = productCombinationDAO;
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
     * @return the commonDAO
     */
    public CommonDAO getCommonDAO()
    {
        return commonDAO;
    }

    /**
     * @param commonDAO
     *            the commonDAO to set
     */
    public void setCommonDAO(CommonDAO commonDAO)
    {
        this.commonDAO = commonDAO;
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
     * @return the locationDAO
     */
    public LocationDAO getLocationDAO()
    {
        return locationDAO;
    }

    /**
     * @param locationDAO
     *            the locationDAO to set
     */
    public void setLocationDAO(LocationDAO locationDAO)
    {
        this.locationDAO = locationDAO;
    }

    /**
     * @return the providerDAO
     */
    public ProviderDAO getProviderDAO()
    {
        return providerDAO;
    }

    /**
     * @param providerDAO
     *            the providerDAO to set
     */
    public void setProviderDAO(ProviderDAO providerDAO)
    {
        this.providerDAO = providerDAO;
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
     * @return the enumDAO
     */
    public EnumDAO getEnumDAO()
    {
        return enumDAO;
    }

    /**
     * @param enumDAO
     *            the enumDAO to set
     */
    public void setEnumDAO(EnumDAO enumDAO)
    {
        this.enumDAO = enumDAO;
    }

    /**
     * @return the composeProductDAO
     */
    public ComposeProductDAO getComposeProductDAO()
    {
        return composeProductDAO;
    }

    /**
     * @param composeProductDAO
     *            the composeProductDAO to set
     */
    public void setComposeProductDAO(ComposeProductDAO composeProductDAO)
    {
        this.composeProductDAO = composeProductDAO;
    }

    /**
     * @return the priceChangeDAO
     */
    public PriceChangeDAO getPriceChangeDAO()
    {
        return priceChangeDAO;
    }

    /**
     * @param priceChangeDAO
     *            the priceChangeDAO to set
     */
    public void setPriceChangeDAO(PriceChangeDAO priceChangeDAO)
    {
        this.priceChangeDAO = priceChangeDAO;
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
     * @return the orgManager
     */
    public OrgManager getOrgManager()
    {
        return orgManager;
    }

    /**
     * @param orgManager
     *            the orgManager to set
     */
    public void setOrgManager(OrgManager orgManager)
    {
        this.orgManager = orgManager;
    }

    /**
     * @return the composeFeeDefinedDAO
     */
    public ComposeFeeDefinedDAO getComposeFeeDefinedDAO()
    {
        return composeFeeDefinedDAO;
    }

    /**
     * @param composeFeeDefinedDAO
     *            the composeFeeDefinedDAO to set
     */
    public void setComposeFeeDefinedDAO(ComposeFeeDefinedDAO composeFeeDefinedDAO)
    {
        this.composeFeeDefinedDAO = composeFeeDefinedDAO;
    }

    /**
     * @return the composeProductManager
     */
    public ComposeProductManager getComposeProductManager()
    {
        return composeProductManager;
    }

    /**
     * @param composeProductManager
     *            the composeProductManager to set
     */
    public void setComposeProductManager(ComposeProductManager composeProductManager)
    {
        this.composeProductManager = composeProductManager;
    }

    /**
     * @return the financeDAO
     */
    public FinanceDAO getFinanceDAO()
    {
        return financeDAO;
    }

    /**
     * @param financeDAO
     *            the financeDAO to set
     */
    public void setFinanceDAO(FinanceDAO financeDAO)
    {
        this.financeDAO = financeDAO;
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

    public ComposeItemDAO getComposeItemDAO() {
        return composeItemDAO;
    }

    public void setComposeItemDAO(ComposeItemDAO composeItemDAO) {
        this.composeItemDAO = composeItemDAO;
    }

	public GoldSilverPriceDAO getGoldSilverPriceDAO()
	{
		return goldSilverPriceDAO;
	}

	public void setGoldSilverPriceDAO(GoldSilverPriceDAO goldSilverPriceDAO)
	{
		this.goldSilverPriceDAO = goldSilverPriceDAO;
	}

	public PriceConfigDAO getPriceConfigDAO()
	{
		return priceConfigDAO;
	}

	public void setPriceConfigDAO(PriceConfigDAO priceConfigDAO)
	{
		this.priceConfigDAO = priceConfigDAO;
	}

	public PriceConfigManager getPriceConfigManager()
	{
		return priceConfigManager;
	}

	public void setPriceConfigManager(PriceConfigManager priceConfigManager)
	{
		this.priceConfigManager = priceConfigManager;
	}

	/**
	 * @return the productBOMDAO
	 */
	public ProductBOMDAO getProductBOMDAO()
	{
		return productBOMDAO;
	}

	/**
	 * @param productBOMDAO the productBOMDAO to set
	 */
	public void setProductBOMDAO(ProductBOMDAO productBOMDAO)
	{
		this.productBOMDAO = productBOMDAO;
	}

	/**
	 * @return the decomposeProductDAO
	 */
	public DecomposeProductDAO getDecomposeProductDAO()
	{
		return decomposeProductDAO;
	}

	/**
	 * @param decomposeProductDAO the decomposeProductDAO to set
	 */
	public void setDecomposeProductDAO(DecomposeProductDAO decomposeProductDAO)
	{
		this.decomposeProductDAO = decomposeProductDAO;
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
	 * @return the citicVSOAProductDAO
	 */
	public CiticVSOAProductDAO getCiticVSOAProductDAO()
	{
		return citicVSOAProductDAO;
	}

	/**
	 * @param citicVSOAProductDAO the citicVSOAProductDAO to set
	 */
	public void setCiticVSOAProductDAO(CiticVSOAProductDAO citicVSOAProductDAO)
	{
		this.citicVSOAProductDAO = citicVSOAProductDAO;
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
