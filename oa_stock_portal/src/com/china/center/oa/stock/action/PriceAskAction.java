/**
 *
 */
package com.china.center.oa.stock.action;


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
import com.china.center.actionhelper.common.KeyConstant;
import com.china.center.actionhelper.common.OldPageSeparateTools;
import com.china.center.actionhelper.query.QueryTools;
import com.china.center.common.MYException;
import com.china.center.jdbc.util.ConditionParse;
import com.china.center.oa.product.bean.ProductBean;
import com.china.center.oa.product.dao.ProductDAO;
import com.china.center.oa.product.vs.ProductTypeVSCustomer;
import com.china.center.oa.publics.Helper;
import com.china.center.oa.publics.bean.StafferBean;
import com.china.center.oa.publics.bean.UserBean;
import com.china.center.oa.publics.constant.AuthConstant;
import com.china.center.oa.publics.constant.SysConfigConstant;
import com.china.center.oa.publics.dao.ParameterDAO;
import com.china.center.oa.publics.dao.RoleAuthDAO;
import com.china.center.oa.publics.dao.StafferDAO;
import com.china.center.oa.publics.dao.UserDAO;
import com.china.center.oa.publics.helper.AuthHelper;
import com.china.center.oa.publics.vs.RoleAuthBean;
import com.china.center.oa.stock.action.helper.NetLoginHelper;
import com.china.center.oa.stock.action.helper.PriceAskHelper;
import com.china.center.oa.stock.bean.PriceAskBean;
import com.china.center.oa.stock.bean.PriceAskProviderBean;
import com.china.center.oa.stock.bean.StockBean;
import com.china.center.oa.stock.constant.PriceConstant;
import com.china.center.oa.stock.dao.PriceAskDAO;
import com.china.center.oa.stock.dao.PriceAskProviderDAO;
import com.china.center.oa.stock.dao.StockDAO;
import com.china.center.oa.stock.manager.PriceAskManager;
import com.china.center.oa.stock.vo.PriceAskBeanVO;
import com.china.center.oa.stock.vo.PriceAskProviderBeanVO;
import com.china.center.tools.BeanUtil;
import com.china.center.tools.CommonTools;
import com.china.center.tools.MathTools;
import com.china.center.tools.SequenceTools;
import com.china.center.tools.StringTools;
import com.china.center.tools.TimeTools;


/**
 * 询价的action
 * 
 * @author Administrator
 */
/**
 * @author Administrator
 */
public class PriceAskAction extends DispatchAction
{
    private final Log _logger = LogFactory.getLog(getClass());

    private ProductDAO productDAO = null;
    
    private StafferDAO stafferDAO = null;

    private PriceAskDAO priceAskDAO = null;
    
    private RoleAuthDAO   roleAuthDAO   = null;

    private UserDAO userDAO = null;

    private PriceAskManager priceAskManager = null;

    private ParameterDAO parameterDAO = null;

    private PriceAskProviderDAO priceAskProviderDAO = null;
    
    private StockDAO stockDAO = null;

    /**
     *
     */
    public PriceAskAction()
    {
    }

    /**
     * 准备处理询价
     * 
     * @param mapping
     * @param form
     * @param request
     * @param reponse
     * @return
     * @throws ServletException
     */
    public ActionForward preForProcessAskPrice(ActionMapping mapping, ActionForm form,
                                               HttpServletRequest request,
                                               HttpServletResponse reponse)
        throws ServletException
    {
        CommonTools.saveParamers(request);

        String id = request.getParameter("id");

        String net = request.getParameter("net");

        PriceAskBeanVO bean = priceAskDAO.findVO(id);

        User user = Helper.getUser(request);

        request.setAttribute("bean", bean);

        if (bean == null)
        {
            request.setAttribute(KeyConstant.ERROR_MESSAGE, "询价不存在");

            QueryTools.setJustQuery(request);

            return mapping.findForward("error");
        }

        ProductBean product = productDAO.find(bean.getProductId());

        if (product == null)
        {
            request.setAttribute(KeyConstant.ERROR_MESSAGE, "产品不存在");

            QueryTools.setJustQuery(request);

            return mapping.findForward("error");
        }
        
        request.setAttribute("product", product);
        
        String needTime = "";
        // 获取采购上的到货日期
        if (!StringTools.isNullOrNone(bean.getRefStock()))
        {
        	StockBean stock = stockDAO.find(bean.getRefStock());
        	
        	needTime = stock.getNeedTime();
        }
        
        request.setAttribute("needTime", needTime);

        if ("1".equals(net))
        {
            PriceAskProviderBean paskBean = priceAskProviderDAO.findBeanByAskIdAndProviderId(id,
                user.getId(), PriceConstant.PRICE_ASK_TYPE_NET);

            request.setAttribute("paskBean", paskBean);

            return mapping.findForward("processAskPriceForNetAsk");
        }

        return mapping.findForward("processAskPrice");
    }

    /**
     * 处理询价(把界面上询价的结果保存到数据库)
     * 
     * @param mapping
     * @param form
     * @param request
     * @param reponse
     * @return
     * @throws ServletException
     */
    public ActionForward endAskPrice(ActionMapping mapping, ActionForm form,
                                     HttpServletRequest request, HttpServletResponse reponse)
        throws ServletException
    {
        String id = request.getParameter("id");

        PriceAskBean bean = priceAskDAO.find(id);

        if (bean == null)
        {
            request.setAttribute(KeyConstant.ERROR_MESSAGE, "询价不存在");

            QueryTools.setMemoryQuery(request);

            return queryPriceAsk(mapping, form, request, reponse);
        }

        User user = Helper.getUser(request);

        try
        {
            priceAskManager.endPriceAskBean(user, id);

            request.setAttribute(KeyConstant.MESSAGE, "成功结束询价:" + id);
        }
        catch (MYException e)
        {
            _logger.warn(e);

            request.setAttribute(KeyConstant.ERROR_MESSAGE, "结束询价失败:" + e.getMessage());
        }

        QueryTools.setMemoryQuery(request);

        return queryPriceAsk(mapping, form, request, reponse);
    }

    /**
     * 处理询价(把界面上询价的结果保存到数据库)
     * 
     * @param mapping
     * @param form
     * @param request
     * @param reponse
     * @return
     * @throws ServletException
     */
    public ActionForward processAskPrice(ActionMapping mapping, ActionForm form,
                                         HttpServletRequest request, HttpServletResponse reponse)
        throws ServletException
    {
        String id = request.getParameter("id");

        String fw = request.getParameter("fw");

        PriceAskBean bean = priceAskDAO.find(id);

        if (bean == null)
        {
            request.setAttribute(KeyConstant.ERROR_MESSAGE, "询价不存在");

            QueryTools.setMemoryQuery(request);

            return queryPriceAsk(mapping, form, request, reponse);
        }

        List<PriceAskProviderBean> item = new ArrayList<PriceAskProviderBean>();

        setPriceAskProviderBeans(bean, item, request);

        User user = Helper.getUser(request);

        try
        {
            bean.setEndTime(TimeTools.now());

            bean.setPuserId(user.getId());

            priceAskManager.processPriceAskBean(user, bean);

            request.setAttribute(KeyConstant.MESSAGE, "成功处理询价申请");
        }
        catch (MYException e)
        {
            _logger.warn(e);

            request.setAttribute(KeyConstant.ERROR_MESSAGE, "处理询价申请失败:" + e.getMessage());
        }

        QueryTools.setMemoryQuery(request);

        if ("queryPriceAsk".equals(fw))
        {
            return queryPriceAsk(mapping, form, request, reponse);
        }

        if ("queryPriceAskForProcess".equals(fw))
        {
            return queryPriceAskForProcess(mapping, form, request, reponse);
        }

        if ("queryPriceAskForNetProviderProcess".equals(fw))
        {
            return queryPriceAskForNetProviderProcess(mapping, form, request, reponse);
        }

        if ("queryPriceAskForNetProcess".equals(fw))
        {
            return queryPriceAskForNetProcess(mapping, form, request, reponse);
        }

        if ("queryPriceAskForNetManager".equals(fw))
        {
            return queryPriceAskForNetManager(mapping, form, request, reponse);
        }

        return queryPriceAsk(mapping, form, request, reponse);
    }

    /**
     * 询价明细
     * 
     * @param mapping
     * @param form
     * @param request
     * @param reponse
     * @return
     * @throws ServletException
     */
    public ActionForward findPriceAsk(ActionMapping mapping, ActionForm form,
                                      HttpServletRequest request, HttpServletResponse reponse)
        throws ServletException
    {
        CommonTools.saveParamers(request);

        String id = request.getParameter("id");

        String self = request.getParameter("self");

        String owner = request.getParameter("owner");

        int srcType = CommonTools.parseInt(request.getParameter("srcType"));

        PriceAskBeanVO bean = null;
        try
        {
            bean = priceAskManager.findPriceAskBeanVO(id);

            if (bean == null)
            {
                request.setAttribute(KeyConstant.ERROR_MESSAGE, "询价不存在");

                QueryTools.setJustQuery(request);

                return queryPriceAsk(mapping, form, request, reponse);
            }

            User user = Helper.getUser(request);

            List<PriceAskProviderBeanVO> items = bean.getItemVO();

            if (NetLoginHelper.isNetLogin(request))
            {
                filterItem(user, items, 3);
            }
            else
            {
                if ( !"1".equals(self))
                {
                    // 只能看到自己
                    filterItem(user, items, 1, srcType);
                }

                if ("1".equals(owner))
                {
                    for (int i = items.size() - 1; i >= 0; i-- )
                    {
                        // items.get(i).setProviderName("");
                    }
                }
            }

            request.setAttribute("bean", bean);
        }
        catch (MYException e)
        {
            _logger.warn(e);

            request.setAttribute(KeyConstant.ERROR_MESSAGE, "查询询价申请失败:" + e.getMessage());
        }

        request.setAttribute("forward", 1);

        return mapping.findForward("detailPriceAsk");
    }

    /**
     * filterItem
     * 
     * @param user
     * @param items
     */
    private void filterItem(User user, List<PriceAskProviderBeanVO> items, int type)
    {
        if (type == 0)
        {
            return;
        }

        // 询价员只能看到自己的
        if (type == 1)
        {
            for (int i = items.size() - 1; i >= 0; i-- )
            {
                if ( !items.get(i).getUserId().equals(user.getId()))
                {
                    items.remove(i);
                }
            }
        }

        // 供应商询价员只能看到供应商下面
        if (type == 3)
        {
            for (int i = items.size() - 1; i >= 0; i-- )
            {
                if ( !items.get(i).getProviderId().equals(user.getId()))
                {
                    items.remove(i);
                }
            }
        }

        // 只能看到外网询价的结果
        if (type == 2)
        {
            for (int i = items.size() - 1; i >= 0; i-- )
            {
                if (items.get(i).getType() == PriceConstant.PRICE_ASK_TYPE_INNER)
                {
                    items.remove(i);
                }
            }
        }
    }

    private void filterItem(User user, List<PriceAskProviderBeanVO> items, int type, int srcType)
    {
        if (type == 0)
        {
            return;
        }

        // 询价员只能看到自己的
        if (type == 1)
        {
            for (int i = items.size() - 1; i >= 0; i-- )
            {
                if ( !items.get(i).getUserId().equals(user.getId())
                    || items.get(i).getSrcType() != srcType)
                {
                    items.remove(i);
                }
            }
        }

        // 供应商询价员只能看到供应商下面
        if (type == 3)
        {
            for (int i = items.size() - 1; i >= 0; i-- )
            {
                if ( !items.get(i).getProviderId().equals(user.getId()))
                {
                    items.remove(i);
                }
            }
        }

        // 只能看到外网询价的结果
        if (type == 2)
        {
            for (int i = items.size() - 1; i >= 0; i-- )
            {
                if (items.get(i).getType() == PriceConstant.PRICE_ASK_TYPE_INNER)
                {
                    items.remove(i);
                }
            }
        }
    }

    /**
     * 收集数据
     * 
     * @param pbean
     * @param item
     * @param request
     */
    private void setPriceAskProviderBeans(PriceAskBean pbean, List<PriceAskProviderBean> item,
                                          HttpServletRequest request)
    {
        String[] providers = request.getParameterValues("check_init");

        String askType = request.getParameter("askType");
        String srcType = request.getParameter("srcType");

        User user = Helper.getUser(request);

        // 外网询价员
        if (AuthHelper.containAuth(user, AuthConstant.PRICE_ASK_NET_INNER_PROCESS))
        {
            askType = "1";
        }

        for (int i = 0; i < providers.length; i++ )
        {
            if ( !StringTools.isNullOrNone(providers[i]))
            {
                PriceAskProviderBean bean = new PriceAskProviderBean();

                bean.setType(CommonTools.parseInt(askType));

                bean.setAskId(pbean.getId());

                bean.setProductId(pbean.getProductId());

                bean.setLogTime(TimeTools.now());

                bean.setProductId(pbean.getProductId());

                bean.setPrice(Float.parseFloat(request.getParameter("price_" + providers[i])));

                bean.setProviderId(request.getParameter("customerId_" + providers[i]));

                bean.setUserId(user.getId());

                bean.setUnitPrice(Float.parseFloat(request.getParameter("unitPrice_" + providers[i])));
                bean.setGoldPrice(MathTools.parseDouble(request.getParameter("goldPrice_" + providers[i])));
                bean.setSilverPrice(MathTools.parseDouble(request.getParameter("silverPrice" + providers[i])));
                bean.setAmount(MathTools.parseDouble(request.getParameter("amount_" + providers[i])));
                bean.setHandleFee(MathTools.parseDouble(request.getParameter("handleFee_" + providers[i])));
                bean.setIsWrapper(MathTools.parseInt(request.getParameter("isWrapper_" + providers[i])));
                bean.setOutTime(request.getParameter("outTime_" + providers[i]));
                bean.setFlow(request.getParameter("flow_" + providers[i]));
                bean.setGap(MathTools.parseInt(request.getParameter("gap_" + providers[i])));

                bean.setProvideConfirmDate(request.getParameter("provideConfirmDate_" + providers[i]));
                bean.setConfirmSendDate(request.getParameter("confirmSendDate_" + providers[i]));
                
//                bean.setHasAmount(CommonTools.parseInt(request.getParameter("hasAmount_"
//                                                                            + providers[i])));
//
//                bean.setSupportAmount(CommonTools.parseInt(request.getParameter("supportAmount_"
//                                                                                + providers[i])));

                bean.setDescription(request.getParameter("description_" + providers[i]));

                // 内网询价而且是满足，自动补足数量
//                if (bean.getHasAmount() == PriceConstant.HASAMOUNT_OK
//                    && bean.getSupportAmount() < pbean.getAmount())
//                {
//                    bean.setSupportAmount(pbean.getAmount());
//                }

                bean.setSrcType(MathTools.parseInt(srcType));

                item.add(bean);
            }
        }

        pbean.setItem(item);
    }

    /**
     * 增加询价申请
     * 
     * @param mapping
     * @param form
     * @param request
     * @param reponse
     * @return
     * @throws ServletException
     */
    public ActionForward addPriceAsk(ActionMapping mapping, ActionForm form,
                                     HttpServletRequest request, HttpServletResponse reponse)
        throws ServletException
    {
        PriceAskBean bean = new PriceAskBean();

        User user = Helper.getUser(request);

        try
        {
            BeanUtil.getBean(bean, request);

            bean.setId(SequenceTools.getSequence("ASK", 5));

            setAskType(request, bean);

            bean.setUserId(user.getId());

            bean.setLogTime(TimeTools.now());

            bean.setStatus(PriceConstant.PRICE_COMMON);

            bean.setLocationId(user.getLocationId());

            ProductBean product = productDAO.find(bean.getProductId());

            if (product == null)
            {
                throw new MYException("数据错误,请确认操作");
            }

            bean.setProductType(product.getType());

            setPriceAskProcessTime(bean);

            priceAskManager.addPriceAskBean(user, bean);

            request.setAttribute(KeyConstant.MESSAGE, "成功增加询价申请");
        }
        catch (MYException e)
        {
            _logger.warn(e);

            request.setAttribute(KeyConstant.ERROR_MESSAGE, "增加询价申请失败:" + e.getMessage());
        }

        CommonTools.removeParamers(request);

        QueryTools.setForwardQuery(request);

        return queryPriceAsk(mapping, form, request, reponse);
    }

    /**
     * setAskType
     * 
     * @param request
     * @param bean
     */
    private void setAskType(HttpServletRequest request, PriceAskBean bean)
    {
        String par0 = request.getParameter("type_list_0");

        String par1 = request.getParameter("type_list_1");

        if ("0".equals(par0) && !"1".equals(par1))
        {
            bean.setType(PriceConstant.PRICE_ASK_TYPE_INNER);
        }

        if ( !"0".equals(par0) && "1".equals(par1))
        {
            bean.setType(PriceConstant.PRICE_ASK_TYPE_NET);
        }

        if ("0".equals(par0) && "1".equals(par1))
        {
            bean.setType(PriceConstant.PRICE_ASK_TYPE_BOTH);
        }
    }

    /**
     * updatePriceAskAmount
     * 
     * @param mapping
     * @param form
     * @param request
     * @param reponse
     * @return
     * @throws ServletException
     */
    public ActionForward updatePriceAskAmount(ActionMapping mapping, ActionForm form,
                                              HttpServletRequest request,
                                              HttpServletResponse reponse)
        throws ServletException
    {
        String id = request.getParameter("id");

        String newAmount = request.getParameter("newAmount");

        User user = Helper.getUser(request);

        try
        {
            priceAskManager.updatePriceAskAmount(user, id, CommonTools.parseInt(newAmount));

            request.setAttribute(KeyConstant.MESSAGE, "成功修改询价数量");
        }
        catch (MYException e)
        {
            _logger.warn(e, e);

            request.setAttribute(KeyConstant.ERROR_MESSAGE, "修改询价数量失败:" + e.getMessage());
        }

        QueryTools.setForwardQuery(request);

        return queryPriceAsk(mapping, form, request, reponse);
    }

    /**
     * updatePriceAskAmount
     * 
     * @param mapping
     * @param form
     * @param request
     * @param reponse
     * @return
     * @throws ServletException
     */
    public ActionForward updatePriceAskAmountStatus(ActionMapping mapping, ActionForm form,
                                                    HttpServletRequest request,
                                                    HttpServletResponse reponse)
        throws ServletException
    {
        String id = request.getParameter("id");

        User user = Helper.getUser(request);

        try
        {
            priceAskManager.updatePriceAskAmountStatus(user, id, 1);

            request.setAttribute(KeyConstant.MESSAGE, "成功放行");
        }
        catch (MYException e)
        {
            _logger.warn(e, e);

            request.setAttribute(KeyConstant.ERROR_MESSAGE, "放行失败:" + e.getMessage());
        }

        QueryTools.setForwardQuery(request);

        return queryPriceAsk(mapping, form, request, reponse);
    }

    /**
     * @param bean
     */
    private void setPriceAskProcessTime(PriceAskBean bean)
    {
        // 设置处理时间
        if (bean.getInstancy() == PriceConstant.PRICE_INSTANCY_COMMON)
        {
            bean.setProcessTime(TimeTools.getDateTimeString(2 * 3600 * 1000));
        }

        if (bean.getInstancy() == PriceConstant.PRICE_INSTANCY_INSTANCY)
        {
            bean.setProcessTime(TimeTools.getDateTimeString(3600 * 1000));
        }

        if (bean.getInstancy() == PriceConstant.PRICE_INSTANCY_VERYINSTANCY)
        {
            bean.setProcessTime(TimeTools.getDateTimeString(1800 * 1000));
        }

        if (bean.getInstancy() == PriceConstant.PRICE_INSTANCY_NETWORK_1130)
        {
            bean.setProcessTime(TimeTools.now_short() + " 11:30:00");
        }

        if (bean.getInstancy() == PriceConstant.PRICE_INSTANCY_NETWORK_1530)
        {
            bean.setProcessTime(TimeTools.now_short() + " 15:30:00");
        }

        if (bean.getInstancy() == PriceConstant.PRICE_INSTANCY_NETWORK_16)
        {
            bean.setProcessTime(TimeTools.now_short() + " 16:00:00");
        }

        // 测试使用的
        if (bean.getInstancy() == 6)
        {
            bean.setProcessTime(TimeTools.now_short() + " 23:00:00");
        }

        // 和当前时间比较
        if (StringTools.compare(TimeTools.now(), bean.getProcessTime()) > 0)
        {
            Date dateByFormat = TimeTools.getDateByFormat(bean.getProcessTime(),
                TimeTools.LONG_FORMAT);

            String newTime = TimeTools.getStringByFormat(new Date(
                dateByFormat.getTime() + 24 * 3600 * 1000), TimeTools.LONG_FORMAT);

            bean.setProcessTime(newTime);

            bean.setAskDate(TimeTools.getStringByFormat(new Date(
                dateByFormat.getTime() + 24 * 3600 * 1000), "yyyyMMdd"));
        }
        else
        {
            bean.setAskDate(TimeTools.now("yyyyMMdd"));
        }
    }

    /**
     * 查询询价(自己查询)
     * 
     * @param mapping
     * @param form
     * @param request
     * @param reponse
     * @return
     * @throws ServletException
     */
    public ActionForward queryPriceAsk(ActionMapping mapping, ActionForm form,
                                       HttpServletRequest request, HttpServletResponse reponse)
        throws ServletException
    {
        CommonTools.saveParamers(request);
        
        User user = Helper.getUser(request);

        ConditionParse condtion = new ConditionParse();

        List<PriceAskBeanVO> list = new ArrayList<PriceAskBeanVO>();;
        
        List<PriceAskBeanVO> viewList = new ArrayList<PriceAskBeanVO>();
        String roleid = user.getRoleId();
        List<RoleAuthBean> authList = roleAuthDAO.queryEntityBeansByFK(roleid);
        int flag = 0;
        if(authList != null && authList.size() > 0 )
        {
        	for(int i = 0 ; i < authList.size(); i++ )
        	{
        		RoleAuthBean rab = authList.get(i);
        		if(rab.getAuthId().trim().equals("120801"))
        		{
        			flag = flag + 120801;
        			
        		}
        		if(rab.getAuthId().trim().equals("120802"))
        		{
        			flag = flag + 120802;
        		}
        		
        	}
        }
        
        try
        {
            if (OldPageSeparateTools.isFirstLoad(request))
            {
                setConditionForAsk(request, condtion, 0);
            }

            QueryTools.commonQueryVO("queryPriceAsk", request, list, condtion, this.priceAskDAO);

            Map<String, String> map = new HashMap<String, String>();

            int maxAmount = parameterDAO.getInt(SysConfigConstant.ASK_PRODUCT_AMOUNT_MAX);
            for (PriceAskBeanVO priceAskBeanVO : list)
            {
                // 虚拟存储
                if (priceAskBeanVO.getSaveType() == PriceConstant.PRICE_ASK_SAVE_TYPE_ABS)
                {
                    if (priceAskBeanVO.getAmount() > maxAmount)
                    {
                        // 超出了
                        priceAskBeanVO.setOverMax(1);
                    }
                }
                
                UserBean tmpUser = userDAO.find(priceAskBeanVO.getUserId());
                StafferBean sb = stafferDAO.find(tmpUser.getStafferId());
            	String indusId = sb.getIndustryId();
            	if(indusId.equals("5111658") && flag == 120801)//只展示终端事业部
            	{
            		viewList.add(priceAskBeanVO);
            	}
            	if((!indusId.equals("5111658")) && flag == 120802)//只展示非终端事业部
            	{
            		viewList.add(priceAskBeanVO);
            	}

                if (priceAskBeanVO.getStatus() == PriceConstant.PRICE_ASK_STATUS_PROCESSING
                    || priceAskBeanVO.getStatus() == PriceConstant.PRICE_ASK_STATUS_END)
                {
                    List<PriceAskProviderBeanVO> items = priceAskProviderDAO
                        .queryEntityVOsByFK(priceAskBeanVO.getId());

                    filterItem(user, items, 0);

                    if (items.size() > 0)
                    {
                        map.put(priceAskBeanVO.getId(), PriceAskHelper.createTable(items, user, 0));
                    }
                }
            }
            if(flag == 120801 || flag == 120802)
            {
            	request.setAttribute("list", viewList);
            }
            else
            {
            	request.setAttribute("list", list);
            }

            request.setAttribute("map", map);
        }
        catch (Exception e)
        {
            request.setAttribute(KeyConstant.ERROR_MESSAGE, "查询询价失败:" + e.getMessage());

            _logger.error(e, e);

            return mapping.findForward("error");
        }

        return mapping.findForward("queryPriceAsk");
    }

    /**
     * 采购主管可以看到全部的询价(分为销售采购和生产采购)
     * 
     * @param mapping
     * @param form
     * @param request
     * @param reponse
     * @return
     * @throws ServletException
     */
    public ActionForward queryAllPriceAsk(ActionMapping mapping, ActionForm form,
                                          HttpServletRequest request, HttpServletResponse reponse)
        throws ServletException
    {
        CommonTools.saveParamers(request);
        
        User user = Helper.getUser(request);

        ConditionParse condtion = new ConditionParse();

        List<PriceAskBeanVO> list = new ArrayList<PriceAskBeanVO>();;
        
        List<PriceAskBeanVO> viewList = new ArrayList<PriceAskBeanVO>();
        String roleid = user.getRoleId();
        List<RoleAuthBean> authList = roleAuthDAO.queryEntityBeansByFK(roleid);
        int flag = 0;
        if(authList != null && authList.size() > 0 )
        {
        	for(int i = 0 ; i < authList.size(); i++ )
        	{
        		RoleAuthBean rab = authList.get(i);
        		if(rab.getAuthId().trim().equals("120801"))
        		{
        			flag = flag + 120801;
        			
        		}
        		if(rab.getAuthId().trim().equals("120802"))
        		{
        			flag = flag + 120802;
        		}
        		
        	}
        }


        try
        {
            if (OldPageSeparateTools.isFirstLoad(request))
            {
                setConditionForAsk(request, condtion, 9);
            }

            QueryTools.commonQueryVO("queryAllPriceAsk", request, list, condtion, this.priceAskDAO);

            Map<String, String> map = new HashMap<String, String>();

            for (PriceAskBeanVO priceAskBeanVO : list)
            {

                List<PriceAskProviderBeanVO> items = priceAskProviderDAO
                    .queryEntityVOsByFK(priceAskBeanVO.getId());
                
                UserBean tmpUser = userDAO.find(priceAskBeanVO.getUserId());
                StafferBean sb = stafferDAO.find(tmpUser.getStafferId());
            	String indusId = sb.getIndustryId();
            	if(indusId.equals("5111658") && flag == 120801)//只展示终端事业部
            	{
            		viewList.add(priceAskBeanVO);
            	}
            	if((!indusId.equals("5111658")) && flag == 120802)//只展示非终端事业部
            	{
            		viewList.add(priceAskBeanVO);
            	}

                if (items.size() > 0)
                {
                    map.put(priceAskBeanVO.getId(), PriceAskHelper.createTable(items, user, 1));
                }
            }

            if(flag == 120801 || flag == 120802)
            {
            	request.setAttribute("list", viewList);
            }
            else
            {
            	request.setAttribute("list", list);
            }


            request.setAttribute("map", map);
        }
        catch (Exception e)
        {
            request.setAttribute(KeyConstant.ERROR_MESSAGE, "查询询价失败:" + e.getMessage());

            _logger.error(e, e);

            return mapping.findForward("error");
        }

        return mapping.findForward("queryAllPriceAsk");
    }

    /**
     * queryPriceAskForProcess(内网询价处理)(包括内部询价、内外询价,生产询价)
     * 
     * @param mapping
     * @param form
     * @param request
     * @param reponse
     * @return
     * @throws ServletException
     */
    public ActionForward queryPriceAskForProcess(ActionMapping mapping, ActionForm form,
                                                 HttpServletRequest request,
                                                 HttpServletResponse reponse)
        throws ServletException
    {
        CommonTools.saveParamers(request);
        
        User user = Helper.getUser(request);

        ConditionParse condtion = new ConditionParse();

        List<PriceAskBeanVO> list = new ArrayList<PriceAskBeanVO>();;
        
        List<PriceAskBeanVO> viewList = new ArrayList<PriceAskBeanVO>();
        String roleid = user.getRoleId();
        List<RoleAuthBean> authList = roleAuthDAO.queryEntityBeansByFK(roleid);
        int flag = 0;
        if(authList != null && authList.size() > 0 )
        {
        	for(int i = 0 ; i < authList.size(); i++ )
        	{
        		RoleAuthBean rab = authList.get(i);
        		if(rab.getAuthId().trim().equals("120801"))
        		{
        			flag = flag + 120801;
        			
        		}
        		if(rab.getAuthId().trim().equals("120802"))
        		{
        			flag = flag + 120802;
        		}
        		
        	}
        }

        int srcType = CommonTools.parseInt(request.getParameter("srcType"));

        try
        {
            if (OldPageSeparateTools.isFirstLoad(request))
            {
                if (srcType == 4)
                {
                    setConditionForAsk(request, condtion, 5);
                }
                else
                {
                    setConditionForAsk(request, condtion, 1);
                }
            }

            QueryTools.commonQueryVO("queryPriceAskForProcess", request, list, condtion,
                this.priceAskDAO);

            Map<String, String> map = new HashMap<String, String>();

            int maxAmount = parameterDAO.getInt(SysConfigConstant.ASK_PRODUCT_AMOUNT_MAX);

            for (PriceAskBeanVO priceAskBeanVO : list)
            {
                // 虚拟存储
                if (priceAskBeanVO.getSaveType() == PriceConstant.PRICE_ASK_SAVE_TYPE_ABS)
                {
                    if (priceAskBeanVO.getAmount() > maxAmount)
                    {
                        // 超出了
                        priceAskBeanVO.setOverMax(1);
                    }
                }
                
                UserBean tmpUser = userDAO.find(priceAskBeanVO.getUserId());
                StafferBean sb = stafferDAO.find(tmpUser.getStafferId());
            	String indusId = sb.getIndustryId();
            	if(indusId.equals("5111658") && flag == 120801)//只展示终端事业部
            	{
            		viewList.add(priceAskBeanVO);
            	}
            	if((!indusId.equals("5111658")) && flag == 120802)//只展示非终端事业部
            	{
            		viewList.add(priceAskBeanVO);
            	}

                if (priceAskBeanVO.getStatus() == PriceConstant.PRICE_ASK_STATUS_PROCESSING
                    || priceAskBeanVO.getStatus() == PriceConstant.PRICE_ASK_STATUS_END)
                {

                    List<PriceAskProviderBeanVO> items = priceAskProviderDAO
                        .queryEntityVOsByFK(priceAskBeanVO.getId());

                    filterItem(user, items, 1, srcType);

                    if (items.size() > 0)
                    {
                        map.put(priceAskBeanVO.getId(), PriceAskHelper.createTable(items, user, 1));
                    }
                }
            }

            if(flag == 120801 || flag == 120802)
            {
            	request.setAttribute("list", viewList);
            }
            else
            {
            	request.setAttribute("list", list);
            }

            request.setAttribute("map", map);
        }
        catch (Exception e)
        {
            request.setAttribute(KeyConstant.ERROR_MESSAGE, "查询询价失败:" + e.getMessage());

            _logger.error(e, e);

            return mapping.findForward("error");
        }

        return mapping.findForward("queryPriceAskForProcess");
    }

    /**
     * queryPriceAskForNetProviderProcess(供应商询价员)
     * 
     * @param mapping
     * @param form
     * @param request
     * @param reponse
     * @return
     * @throws ServletException
     */
    public ActionForward queryPriceAskForNetProviderProcess(ActionMapping mapping, ActionForm form,
                                                            HttpServletRequest request,
                                                            HttpServletResponse reponse)
        throws ServletException
    {
        CommonTools.saveParamers(request);

        ConditionParse condtion = new ConditionParse();

        List<PriceAskBeanVO> list = new ArrayList<PriceAskBeanVO>();

        try
        {
            if (OldPageSeparateTools.isFirstLoad(request))
            {
                setConditionForAsk(request, condtion, 2);
            }

            QueryTools.commonQueryVO("queryPriceAskForNetProviderProcess", request, list, condtion,
                this.priceAskDAO);

            Map<String, String> map = new HashMap<String, String>();

            int maxAmount = parameterDAO.getInt(SysConfigConstant.ASK_PRODUCT_AMOUNT_MAX);

            for (PriceAskBeanVO priceAskBeanVO : list)
            {
                // 虚拟存储
                if (priceAskBeanVO.getSaveType() == PriceConstant.PRICE_ASK_SAVE_TYPE_ABS)
                {
                    if (priceAskBeanVO.getAmount() > maxAmount)
                    {
                        // 超出了
                        priceAskBeanVO.setOverMax(1);
                    }
                }

                if (priceAskBeanVO.getStatus() == PriceConstant.PRICE_ASK_STATUS_PROCESSING
                    || priceAskBeanVO.getStatus() == PriceConstant.PRICE_ASK_STATUS_END)
                {
                    User user = Helper.getUser(request);

                    List<PriceAskProviderBeanVO> items = priceAskProviderDAO
                        .queryEntityVOsByFK(priceAskBeanVO.getId());

                    filterItem(user, items, 3);

                    if (items.size() > 0)
                    {
                        map.put(priceAskBeanVO.getId(), PriceAskHelper.createTable(items, user, 1));

                        priceAskBeanVO.setHasItem(true);
                    }
                }
            }

            request.setAttribute("list", list);

            request.setAttribute("map", map);
        }
        catch (Exception e)
        {
            request.setAttribute(KeyConstant.ERROR_MESSAGE, "查询询价失败:" + e.getMessage());

            _logger.error(e, e);

            return mapping.findForward("error");
        }

        return mapping.findForward("queryPriceAskForNetProviderProcess");
    }

    /**
     * queryPriceAskForNetProcess(外网询价处理)(仅仅是外网询价员的处理不是供应商询价员的处理)
     * 
     * @param mapping
     * @param form
     * @param request
     * @param reponse
     * @return
     * @throws ServletException
     */
    public ActionForward queryPriceAskForNetProcess(ActionMapping mapping, ActionForm form,
                                                    HttpServletRequest request,
                                                    HttpServletResponse reponse)
        throws ServletException
    {
        CommonTools.saveParamers(request);
        
        User user = Helper.getUser(request);

        ConditionParse condtion = new ConditionParse();

        List<PriceAskBeanVO> list = new ArrayList<PriceAskBeanVO>();;
        
        List<PriceAskBeanVO> viewList = new ArrayList<PriceAskBeanVO>();
        String roleid = user.getRoleId();
        List<RoleAuthBean> authList = roleAuthDAO.queryEntityBeansByFK(roleid);
        int flag = 0;
        if(authList != null && authList.size() > 0 )
        {
        	for(int i = 0 ; i < authList.size(); i++ )
        	{
        		RoleAuthBean rab = authList.get(i);
        		if(null != rab )
        		{
	        		if(rab.getAuthId().trim().equals("120801"))
	        		{
	        			flag = flag + 120801;
	        			
	        		}
	        		if(rab.getAuthId().trim().equals("120802"))
	        		{
	        			flag = flag + 120802;
	        		}
        		}
        		
        	}
        }

        int srcType = CommonTools.parseInt(request.getParameter("srcType"));

        try
        {
            if (OldPageSeparateTools.isFirstLoad(request))
            {
                setConditionForAsk(request, condtion, 3);
            }

            QueryTools.commonQueryVO("queryPriceAskForNetProcess", request, list, condtion,
                this.priceAskDAO);

            Map<String, String> map = new HashMap<String, String>();

            int maxAmount = parameterDAO.getInt(SysConfigConstant.ASK_PRODUCT_AMOUNT_MAX);
            if(null != list && list.size() > 0)
            {
	            for (PriceAskBeanVO priceAskBeanVO : list)
	            {
	            	if(priceAskBeanVO == null )
	            	{
	            		continue;
	            	}
	                // 虚拟存储
	                if (priceAskBeanVO.getSaveType() == PriceConstant.PRICE_ASK_SAVE_TYPE_ABS)
	                {
	                    if (priceAskBeanVO.getAmount() > maxAmount)
	                    {
	                        // 超出了
	                        priceAskBeanVO.setOverMax(1);
	                    }
	                }
	                
	                UserBean tmpUser = userDAO.find(priceAskBeanVO.getUserId());
	                if(null != tmpUser)
	                {
		                StafferBean sb = stafferDAO.find(tmpUser.getStafferId());
		                if(null != sb )
		                {
			            	String indusId = sb.getIndustryId();
			            	if(null != indusId && indusId.equals("5111658") && flag == 120801)//只展示终端事业部
			            	{
			            		viewList.add(priceAskBeanVO);
			            	}
			            	if(null != indusId && (!indusId.equals("5111658")) && flag == 120802)//只展示非终端事业部
			            	{
			            		viewList.add(priceAskBeanVO);
			            	}
		                }
	                }
	
	                if (priceAskBeanVO.getStatus() == PriceConstant.PRICE_ASK_STATUS_PROCESSING
	                    || priceAskBeanVO.getStatus() == PriceConstant.PRICE_ASK_STATUS_END)
	                {
	                    List<PriceAskProviderBeanVO> items = priceAskProviderDAO
	                        .queryEntityVOsByFK(priceAskBeanVO.getId());
	
	                    if (srcType == 1)
	                    {
	                        filterItem(user, items, 1, srcType);
	                    }
	                    else
	                    {
	                        filterItem(user, items, 2);
	                    }
	
	                    if (items.size() > 0)
	                    {
	                        priceAskBeanVO.setSaveType(1);
	                        map.put(priceAskBeanVO.getId(), PriceAskHelper.createTable(items, user, 1));
	                    }
	                    else
	                    {
	                        priceAskBeanVO.setSaveType(0);
	                    }
	                }
	            }
            }

            if(flag == 120801 || flag == 120802)
            {
            	request.setAttribute("list", viewList);
            }
            else
            {
            	request.setAttribute("list", list);
            }

            request.setAttribute("map", map);
        }
        catch (Exception e)
        {
            request.setAttribute(KeyConstant.ERROR_MESSAGE, "查询询价失败:" + e.getMessage());

            _logger.error(e, e);

            return mapping.findForward("error");
        }

        return mapping.findForward("queryPriceAskForNetProcess");
    }

    /**
     * queryPriceAskForNetManager(外网询价的变更最大询价数量的处理)
     * 
     * @param mapping
     * @param form
     * @param request
     * @param reponse
     * @return
     * @throws ServletException
     */
    public ActionForward queryPriceAskForNetManager(ActionMapping mapping, ActionForm form,
                                                    HttpServletRequest request,
                                                    HttpServletResponse reponse)
        throws ServletException
    {
        CommonTools.saveParamers(request);

        ConditionParse condtion = new ConditionParse();

        List<PriceAskBeanVO> list = new ArrayList<PriceAskBeanVO>();

        try
        {
            if (OldPageSeparateTools.isFirstLoad(request))
            {
                setConditionForAsk(request, condtion, 4);
            }

            QueryTools.commonQueryVO("queryPriceAskForNetManager", request, list, condtion,
                this.priceAskDAO);

            Map<String, String> map = new HashMap<String, String>();

            int maxAmount = parameterDAO.getInt(SysConfigConstant.ASK_PRODUCT_AMOUNT_MAX);

            for (PriceAskBeanVO priceAskBeanVO : list)
            {
                // 虚拟存储
                if (priceAskBeanVO.getSaveType() == PriceConstant.PRICE_ASK_SAVE_TYPE_ABS)
                {
                    if (priceAskBeanVO.getAmount() > maxAmount)
                    {
                        // 超出了
                        priceAskBeanVO.setOverMax(1);
                    }
                }

                if (priceAskBeanVO.getStatus() == PriceConstant.PRICE_ASK_STATUS_PROCESSING
                    || priceAskBeanVO.getStatus() == PriceConstant.PRICE_ASK_STATUS_END)
                {
                    User user = Helper.getUser(request);

                    List<PriceAskProviderBeanVO> items = priceAskProviderDAO
                        .queryEntityVOsByFK(priceAskBeanVO.getId());

                    filterItem(user, items, 2);

                    if (items.size() > 0)
                    {
                        map.put(priceAskBeanVO.getId(), PriceAskHelper.createTable(items, user, 1));
                    }
                }
            }

            request.setAttribute("list", list);

            request.setAttribute("map", map);
        }
        catch (Exception e)
        {
            request.setAttribute(KeyConstant.ERROR_MESSAGE, "查询询价失败:" + e.getMessage());

            _logger.error(e, e);

            return mapping.findForward("error");
        }

        return mapping.findForward("queryPriceAskForNetManager");
    }

    /**
     * setCondition
     * 
     * @param request
     * @param condtion
     */
    protected void setCondition(HttpServletRequest request, ConditionParse condtion)
    {
        condtion.addWhereStr();

        User user = Helper.getUser(request);

        // 只能看到通过的
        if ( !AuthHelper.containAuth(user, AuthConstant.PRICE_ASK_PROCESS))
        {
            request.setAttribute("readonly", "true");

            condtion.addIntCondition("PriceBean.status", "=", PriceConstant.PRICE_COMMON);

            request.setAttribute("status", PriceConstant.PRICE_COMMON);
        }

        String productId = request.getParameter("productId");

        if ( !StringTools.isNullOrNone(productId))
        {
            condtion.addCondition("PriceBean.productId", "=", productId);
        }

        String status = request.getParameter("status");

        if ( !StringTools.isNullOrNone(status))
        {
            condtion.addIntCondition("PriceBean.status", "=", status);
        }

        String alogTime = request.getParameter("alogTime");

        if ( !StringTools.isNullOrNone(alogTime))
        {
            condtion.addCondition("PriceBean.logTime", ">=", alogTime + " 00:00:00");
        }
        else
        {
            condtion.addCondition("PriceBean.logTime", ">=", TimeTools.getDateShortString( -7)
                                                             + " 00:00:00");

            request.setAttribute("alogTime", TimeTools.getDateShortString( -7));
        }

        String blogTime = request.getParameter("blogTime");

        if ( !StringTools.isNullOrNone(blogTime))
        {
            condtion.addCondition("PriceBean.logTime", "<=", blogTime + " 23:59:59");
        }
        else
        {
            condtion.addCondition("PriceBean.logTime", "<=", TimeTools.now_short() + " 23:59:59");

            request.setAttribute("blogTime", TimeTools.now_short());
        }

        condtion.addCondition("order by PriceBean.id desc");
    }

    /**
     * setConditionForAsk(询价的处理是整个系统的,不分区域)
     * 
     * @param request
     * @param condtion
     */
    private void setConditionForAsk(HttpServletRequest request, ConditionParse condtion,
                                    int conditionType)
    {
        condtion.addWhereStr();

        User user = Helper.getUser(request);

        // 防止攻击登录
        if (NetLoginHelper.isNetLogin(request) && conditionType != 2)
        {
            condtion.addCondition("and 1 = 0");

            return;
        }

        // 只能看到自己的
        if (conditionType == 0)
        {
            condtion.addCondition("PriceAskBean.userId", "=", user.getId());
        }

        // 处理询价的(内部询价)
        if (conditionType == 1)
        {
            // 只能看到普通存储的
            condtion.addIntCondition("PriceAskBean.saveType", "=",
                PriceConstant.PRICE_ASK_SAVE_TYPE_COMMON);

            condtion.addIntCondition("PriceAskBean.src", "<>", PriceConstant.PRICE_ASK_SRC_MAKE);

            // 内网和内外网
            condtion.addCondition("AND PriceAskBean.type in (0, 2)");
        }

        // 外网询价员(供应商询价员)的逻辑
        if (conditionType == 2)
        {
            List<ProductTypeVSCustomer> typeList = (List<ProductTypeVSCustomer>)request
                .getSession()
                .getAttribute("typeList");

            StringBuilder sb = new StringBuilder();

            sb.append("(");

            for (int i = 0; i < typeList.size(); i++ )
            {
                if (i != typeList.size() - 1)
                {
                    sb.append(typeList.get(i).getProductTypeId()).append(",");
                }
                else
                {
                    sb.append(typeList.get(i).getProductTypeId());
                }
            }

            sb.append(")");

            // 只能看见制定数量的产品询价(或者放行的)
            condtion.addCondition("AND (PriceAskBean.amount <= "
                                  + parameterDAO.getInt(SysConfigConstant.ASK_PRODUCT_AMOUNT_MAX)
                                  + " or PriceAskBean.amountStatus = 1) ");

            // 只能看到虚拟存储的
            condtion.addIntCondition("PriceAskBean.saveType", "=",
                PriceConstant.PRICE_ASK_SAVE_TYPE_ABS);

            condtion.addCondition("AND PriceAskBean.status in (0, 1)");

            condtion.addCondition("AND PriceAskBean.type in (1, 2)");

            condtion.addCondition("AND PriceAskBean.productType in " + sb.toString());
        }

        // 处理询价的(外网询价)
        if (conditionType == 3)
        {
            String src = request.getParameter("src");

            // 询价
            if ("0".equals(src))
            {
                if (AuthHelper.containAuth(user, AuthConstant.PRICE_ASK_NET_INNER_PROCESS))
                {
                    // 只能看到普通存储的
                    condtion.addIntCondition("PriceAskBean.saveType", "=",
                        PriceConstant.PRICE_ASK_SAVE_TYPE_ABS);
                }
                else
                {
                    // 只能看到普通存储的
                    condtion.addIntCondition("PriceAskBean.saveType", "=",
                        PriceConstant.PRICE_ASK_SAVE_TYPE_COMMON);
                }

                condtion.addIntCondition("PriceAskBean.src", "=", PriceConstant.PRICE_ASK_SRC_ASK);

                // 外网和内外网
                condtion.addCondition("AND PriceAskBean.type in (1, 2)");
            }
            else if ("1".equals(src))
            {
                // 采购
                // 只能看到普通存储的
                condtion.addIntCondition("PriceAskBean.saveType", "=",
                    PriceConstant.PRICE_ASK_SAVE_TYPE_COMMON);

                condtion
                    .addIntCondition("PriceAskBean.src", "=", PriceConstant.PRICE_ASK_SRC_STOCK);

                QueryTools.setParMapAttribute(request, "src", "1");
            }
            else
            {
                condtion.addIntCondition("PriceAskBean.saveType", "=",
                    PriceConstant.PRICE_ASK_SAVE_TYPE_COMMON);

                condtion.addIntCondition("PriceAskBean.src", "=", PriceConstant.PRICE_ASK_SRC_MAKE);

                QueryTools.setParMapAttribute(request, "src", "2");
            }
        }

        // 处理询价的(外网询价调整数量的)
        if (conditionType == 4)
        {
            condtion.addCondition(" AND PriceAskBean.type in (1, 2) ");

            // 只能看到虚拟存储的
            condtion.addIntCondition("PriceAskBean.saveType", "=",
                PriceConstant.PRICE_ASK_SAVE_TYPE_ABS);

            // 没有放行的
            condtion.addIntCondition("PriceAskBean.amountStatus", "=", 0);

            // 只能看见额定数量的产品询价
            condtion.addIntCondition("PriceAskBean.amount", ">", parameterDAO
                .getInt(SysConfigConstant.ASK_PRODUCT_AMOUNT_MAX));

            condtion.addCondition("AND PriceAskBean.status in (0, 1)");
        }

        // 处理生产采购
        if (conditionType == 5)
        {
            // 只能看到普通存储的
            condtion.addIntCondition("PriceAskBean.saveType", "=",
                PriceConstant.PRICE_ASK_SAVE_TYPE_COMMON);

            condtion.addIntCondition("PriceAskBean.src", "=", PriceConstant.PRICE_ASK_SRC_MAKE);

            // 内网和内外网
            condtion.addCondition("AND PriceAskBean.type in (0, 2)");

            QueryTools.setParMapAttribute(request, "src", "2");
        }

        // 采购主管看全部的
        if (conditionType == 9)
        {
            condtion.addIntCondition("PriceAskBean.saveType", "=",
                PriceConstant.PRICE_ASK_SAVE_TYPE_COMMON);
        }

        String productId = request.getParameter("productId");

        if ( !StringTools.isNullOrNone(productId))
        {
            condtion.addCondition("PriceAskBean.productId", "=", productId);
        }

        String id = request.getParameter("qid");

        if ( !StringTools.isNullOrNone(id))
        {
            condtion.addCondition("PriceAskBean.id", "like", id);
        }

        String status = request.getParameter("status");

        if ( !StringTools.isNullOrNone(status))
        {
            condtion.addIntCondition("PriceAskBean.status", "=", status);
        }

        String type = request.getParameter("type");

        if ( !StringTools.isNullOrNone(type))
        {
            condtion.addIntCondition("PriceAskBean.type", "=", type);
        }

        String overTime = request.getParameter("overTime");

        if ( !StringTools.isNullOrNone(overTime))
        {
            condtion.addIntCondition("PriceAskBean.overTime", "=", overTime);
        }

        String src = request.getParameter("src");

        if ( !StringTools.isNullOrNone(src))
        {
            condtion.addIntCondition("PriceAskBean.src", "=", src);
        }

        String instancy = request.getParameter("instancy");

        if ( !StringTools.isNullOrNone(instancy))
        {
            condtion.addIntCondition("PriceAskBean.instancy", "=", instancy);
        }

        String alogTime = request.getParameter("alogTime");

        if ( !StringTools.isNullOrNone(alogTime))
        {
            condtion.addCondition("PriceAskBean.logTime", ">=", alogTime + " 00:00:00");
        }
        else
        {
            condtion.addCondition("PriceAskBean.logTime", ">=", TimeTools.getDateShortString( -7)
                                                                + " 00:00:00");

            QueryTools.setParMapAttribute(request, "alogTime", TimeTools.getDateShortString( -7));
        }

        String blogTime = request.getParameter("blogTime");

        if ( !StringTools.isNullOrNone(blogTime))
        {
            condtion.addCondition("PriceAskBean.logTime", "<=", blogTime + " 23:59:59");
        }
        else
        {
            condtion
                .addCondition("PriceAskBean.logTime", "<=", TimeTools.now_short() + " 23:59:59");

            QueryTools.setParMapAttribute(request, "blogTime", TimeTools.now_short());
        }

        condtion.addCondition("order by PriceAskBean.id desc");
    }

    /**
     * delPriceAsk
     * 
     * @param mapping
     * @param form
     * @param request
     * @param reponse
     * @return
     * @throws ServletException
     */
    public ActionForward delPriceAsk(ActionMapping mapping, ActionForm form,
                                     HttpServletRequest request, HttpServletResponse reponse)
        throws ServletException
    {
        String id = request.getParameter("id");

        User user = Helper.getUser(request);

        try
        {
            priceAskManager.delPriceAskBean(user, id);

            request.setAttribute(KeyConstant.MESSAGE, "成功删除:" + id);
        }
        catch (MYException e)
        {
            _logger.warn(e);

            request.setAttribute(KeyConstant.ERROR_MESSAGE, "删除失败:" + e.getMessage());
        }

        QueryTools.setMemoryQuery(request);

        return queryPriceAsk(mapping, form, request, reponse);
    }

    /**
     * rejectPriceAsk
     * 
     * @param mapping
     * @param form
     * @param request
     * @param reponse
     * @return
     * @throws ServletException
     */
    public ActionForward rejectPriceAsk(ActionMapping mapping, ActionForm form,
                                        HttpServletRequest request, HttpServletResponse reponse)
        throws ServletException
    {
        String id = request.getParameter("id");

        String reason = request.getParameter("reason");

        String fw = request.getParameter("fw");

        User user = Helper.getUser(request);

        try
        {
            priceAskManager.rejectPriceAskBean(user, id, reason);

            request.setAttribute(KeyConstant.MESSAGE, "成功驳回询价:" + id);
        }
        catch (MYException e)
        {
            _logger.warn(e);

            request.setAttribute(KeyConstant.ERROR_MESSAGE, "驳回失败:" + e.getMessage());
        }

        QueryTools.setMemoryQuery(request);

        if ("queryPriceAsk".equals(fw))
        {
            return queryPriceAsk(mapping, form, request, reponse);
        }

        if ("queryPriceAskForProcess".equals(fw))
        {
            return queryPriceAskForProcess(mapping, form, request, reponse);
        }

        if ("queryPriceAskForNetProviderProcess".equals(fw))
        {
            return queryPriceAskForNetProviderProcess(mapping, form, request, reponse);
        }

        if ("queryPriceAskForNetProcess".equals(fw))
        {
            return queryPriceAskForNetProcess(mapping, form, request, reponse);
        }

        if ("queryPriceAskForNetManager".equals(fw))
        {
            return queryPriceAskForNetManager(mapping, form, request, reponse);
        }

        return queryPriceAskForProcess(mapping, form, request, reponse);
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
     * @return the priceAskDAO
     */
    public PriceAskDAO getPriceAskDAO()
    {
        return priceAskDAO;
    }

    /**
     * @param priceAskDAO
     *            the priceAskDAO to set
     */
    public void setPriceAskDAO(PriceAskDAO priceAskDAO)
    {
        this.priceAskDAO = priceAskDAO;
    }

    /**
     * @return the userDAO
     */
    public UserDAO getUserDAO()
    {
        return userDAO;
    }

    /**
     * @param userDAO
     *            the userDAO to set
     */
    public void setUserDAO(UserDAO userDAO)
    {
        this.userDAO = userDAO;
    }

    /**
     * @return the priceAskManager
     */
    public PriceAskManager getPriceAskManager()
    {
        return priceAskManager;
    }

    /**
     * @param priceAskManager
     *            the priceAskManager to set
     */
    public void setPriceAskManager(PriceAskManager priceAskManager)
    {
        this.priceAskManager = priceAskManager;
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
     * @return the priceAskProviderDAO
     */
    public PriceAskProviderDAO getPriceAskProviderDAO()
    {
        return priceAskProviderDAO;
    }

    /**
     * @param priceAskProviderDAO
     *            the priceAskProviderDAO to set
     */
    public void setPriceAskProviderDAO(PriceAskProviderDAO priceAskProviderDAO)
    {
        this.priceAskProviderDAO = priceAskProviderDAO;
    }

	public RoleAuthDAO getRoleAuthDAO() {
		return roleAuthDAO;
	}

	public void setRoleAuthDAO(RoleAuthDAO roleAuthDAO) {
		this.roleAuthDAO = roleAuthDAO;
	}

	public StafferDAO getStafferDAO() {
		return stafferDAO;
	}

	public void setStafferDAO(StafferDAO stafferDAO) {
		this.stafferDAO = stafferDAO;
	}

	/**
	 * @return the stockDAO
	 */
	public StockDAO getStockDAO()
	{
		return stockDAO;
	}

	/**
	 * @param stockDAO the stockDAO to set
	 */
	public void setStockDAO(StockDAO stockDAO)
	{
		this.stockDAO = stockDAO;
	}
}
