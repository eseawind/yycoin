/**
 * File Name: PriceAskManagerImpl.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2010-9-11<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.stock.manager.impl;


import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.china.center.spring.ex.annotation.Exceptional;
import org.springframework.transaction.annotation.Transactional;

import com.center.china.osgi.publics.User;
import com.china.center.common.MYException;
import com.china.center.oa.publics.constant.AuthConstant;
import com.china.center.oa.publics.constant.StafferConstant;
import com.china.center.oa.publics.dao.CommonDAO;
import com.china.center.oa.publics.helper.AuthHelper;
import com.china.center.oa.stock.bean.PriceAskBean;
import com.china.center.oa.stock.bean.PriceAskProviderBean;
import com.china.center.oa.stock.constant.PriceConstant;
import com.china.center.oa.stock.dao.PriceAskDAO;
import com.china.center.oa.stock.dao.PriceAskProviderDAO;
import com.china.center.oa.stock.manager.PriceAskManager;
import com.china.center.oa.stock.vo.PriceAskBeanVO;
import com.china.center.oa.stock.vo.PriceAskProviderBeanVO;
import com.china.center.tools.BeanUtil;
import com.china.center.tools.JudgeTools;
import com.china.center.tools.SequenceTools;
import com.china.center.tools.StringTools;


/**
 * PriceAskManagerImpl
 * 
 * @author ZHUZHU
 * @version 2010-9-11
 * @see PriceAskManagerImpl
 * @since 1.0
 */
@Exceptional
public class PriceAskManagerImpl implements PriceAskManager
{
    private final Log _triggerLogger = LogFactory.getLog("trigger");

    private PriceAskDAO priceAskDAO = null;

    private CommonDAO commonDAO = null;

    private PriceAskProviderDAO priceAskProviderDAO = null;

    /**
     * default constructor
     */
    public PriceAskManagerImpl()
    {
    }

    /**
     * 增加询价
     * 
     * @param bean
     * @return
     * @throws MYException
     */

    @Transactional(rollbackFor = {MYException.class})
    public boolean addPriceAskBean(User user, PriceAskBean bean)
        throws MYException
    {
        return addPriceAskBeanWithoutTransactional(user, bean);
    }

    public boolean addPriceAskBeanWithoutTransactional(User user, PriceAskBean bean)
        throws MYException
    {
        JudgeTools.judgeParameterIsNull(bean);

        // 如果是外网询价这里需要归并在一起
        handleNetAsk(bean);

        bean.setSrcamount(bean.getAmount());

        priceAskDAO.saveEntityBean(bean);

        return true;
    }

    /**
     * updatePriceAskAmount
     * 
     * @param bean
     * @return
     * @throws MYException
     */

    @Transactional(rollbackFor = {MYException.class})
    public boolean updatePriceAskAmount(User user, final String id, int newAmount)
        throws MYException
    {
        JudgeTools.judgeParameterIsNull(id);

        priceAskDAO.updateAmount(id, newAmount);

        return true;
    }

    @Transactional(rollbackFor = {MYException.class})
    public boolean updatePriceAskAmountStatus(User user, final String id, int newStatus)
        throws MYException
    {
        JudgeTools.judgeParameterIsNull(id);

        priceAskDAO.updateAmountStatus(id, newStatus);

        return true;
    }

    /**
     * handleNetAsk
     * 
     * @param bean
     */
    private void handleNetAsk(final PriceAskBean bean)
    {
        // 只有询价的合并,采购的询价不合并
        if (bean.getSrc() == PriceConstant.PRICE_ASK_SRC_ASK
            && (bean.getType() == PriceConstant.PRICE_ASK_TYPE_NET || bean.getType() == PriceConstant.PRICE_ASK_TYPE_BOTH))
        {
            // 查看当天的产品外网询价是否存在
            PriceAskBean absAsk = priceAskDAO.findAbsByProductIdAndProcessTime(bean.getProductId(),
                bean.getProcessTime());

            if (absAsk == null)
            {
                // create a new ABS ask
                absAsk = new PriceAskBean();

                BeanUtil.copyProperties(absAsk, bean);

                absAsk.setId(SequenceTools.getSequence("ASK", 5));

                absAsk.setSaveType(PriceConstant.PRICE_ASK_SAVE_TYPE_ABS);

                absAsk.setUserId(StafferConstant.SUPER_STAFFER);

                absAsk.setSrcamount(absAsk.getAmount());

                priceAskDAO.saveEntityBean(absAsk);
            }
            else
            {
                // 补充数量
                absAsk.setAmount(bean.getAmount() + absAsk.getAmount());

                absAsk.setSrcamount(absAsk.getSrcamount() + bean.getAmount());

                priceAskDAO.updateEntityBean(absAsk);
            }

            bean.setParentAsk(absAsk.getId());
        }
    }

    /**
     * 驳回询价
     * 
     * @param bean
     * @return
     * @throws MYException
     */
    @Transactional(rollbackFor = {MYException.class})
    public boolean rejectPriceAskBean(User user, String id, String reason)
        throws MYException
    {
        JudgeTools.judgeParameterIsNull(id);

        PriceAskBean bean = checkAskReject(id);

        bean.setStatus(PriceConstant.PRICE_ASK_STATUS_EXCEPTION);

        bean.setReason(reason);

        priceAskDAO.updateEntityBean(bean);

        return true;
    }

    /**
     * 结束询价
     * 
     * @param bean
     * @return
     * @throws MYException
     */

    @Transactional(rollbackFor = {MYException.class})
    public boolean endPriceAskBean(final User user, String id)
        throws MYException
    {
        JudgeTools.judgeParameterIsNull(id);

        PriceAskBean bean = checkAskEnd(id);

        bean.setStatus(PriceConstant.PRICE_ASK_STATUS_END);

        priceAskDAO.updateEntityBean(bean);

        return true;
    }

    /**
     * @param id
     * @throws MYException
     */
    private PriceAskBean checkAskReject(String id)
        throws MYException
    {
        PriceAskBean bean = priceAskDAO.find(id);

        if (bean == null)
        {
            throw new MYException("询价不存在");
        }

        if (bean.getStatus() != PriceConstant.PRICE_ASK_STATUS_INIT)
        {
            throw new MYException("不能驳回询价");
        }

        return bean;
    }

    /**
     * @param id
     * @throws MYException
     */
    private PriceAskBean checkAskEnd(String id)
        throws MYException
    {
        PriceAskBean bean = priceAskDAO.find(id);

        if (bean == null)
        {
            throw new MYException("询价不存在");
        }

        if (bean.getStatus() != PriceConstant.PRICE_ASK_STATUS_PROCESSING)
        {
            throw new MYException("不能结束询价,询价单不处于询价中");
        }

        return bean;
    }

    /**
     * 修改询价
     * 
     * @param bean
     * @return
     * @throws MYException
     */
    @Transactional(rollbackFor = {MYException.class})
    public boolean processPriceAskBean(final User user, final PriceAskBean bean)
        throws MYException
    {
        JudgeTools.judgeParameterIsNull(bean);

        List<PriceAskProviderBean> oldList = bean.getItem();

        List<PriceAskProviderBean> itemList = new ArrayList<PriceAskProviderBean>();

        for (PriceAskProviderBean each : oldList)
        {
            itemList.add(new PriceAskProviderBean(each));
        }

        Set<PriceAskProviderBean> item = new HashSet();

        for (PriceAskProviderBean each : itemList)
        {
            item.add(each);
        }

        // DROP 只要STOCK询价，此单自动结束
        if (AuthHelper.containAuth(user, AuthConstant.PRICE_ASK_MANAGER) && false)
        {
            bean.setStatus(PriceConstant.PRICE_ASK_STATUS_END);
        }
        else
        {
            bean.setStatus(PriceConstant.PRICE_ASK_STATUS_PROCESSING);

            if ( !StringTools.isNullOrNone(bean.getParentAsk()))
            {
                item.addAll(priceAskProviderDAO.queryEntityBeansByFK(bean.getId()));
            }
        }

        double min = (double)Integer.MAX_VALUE;

        for (PriceAskProviderBean priceAskProviderBean : item)
        {
            //if (priceAskProviderBean.getHasAmount() == PriceConstant.HASAMOUNT_OK)
            //{
                min = Math.min(priceAskProviderBean.getPrice(), min);
            //}
        }

        bean.setPrice(min == Integer.MAX_VALUE ? 0.0d : min);

        priceAskDAO.updateEntityBean(bean);

        List<PriceAskProviderBean> items = bean.getItem();

        for (PriceAskProviderBean priceAskProviderBean : items)
        {
            priceAskProviderBean.setAskId(bean.getId());

            // 删除类型下的供应商
            priceAskProviderDAO.deleteByProviderId(bean.getId(), priceAskProviderBean
                .getProviderId(), priceAskProviderBean.getSrcType());
        }

        // 全部保存
        priceAskProviderDAO.saveAllEntityBeans(items);

        // 如果是ABS的询价就反映到各个子询价单据上
        if (bean.getSaveType() == PriceConstant.PRICE_ASK_SAVE_TYPE_ABS)
        {
            List<PriceAskBean> subList = priceAskDAO.queryByParentId(bean.getId());

            for (PriceAskBean subAskBean : subList)
            {
                List<PriceAskProviderBean> innerList = new ArrayList();

                for (PriceAskProviderBean each : item)
                {
                    innerList.add(each);
                }

                subAskBean.setItem(innerList);

                processPriceAskBean(user, subAskBean);
            }
        }

        return true;
    }

    /**
     * findPriceAskBeanVO
     * 
     * @param bean
     * @return
     * @throws MYException
     */
    public PriceAskBeanVO findPriceAskBeanVO(final String id)
        throws MYException
    {
        JudgeTools.judgeParameterIsNull(id);

        PriceAskBeanVO vo = priceAskDAO.findVO(id);

        if (vo == null)
        {
            throw new MYException("数据错误,请确认操作");
        }

        List<PriceAskProviderBeanVO> items = priceAskProviderDAO.queryEntityVOsByFK(id);

        vo.setItemVO(items);

        return vo;
    }

    /**
     * check overtime 定时器触发的job
     * 
     * @param bean
     * @return
     * @throws MYException
     */
    @Transactional(rollbackFor = {MYException.class})
    public boolean checkOverTime_Job()
    {
        _triggerLogger.info("begin checkOverTime_Job...该JOB停用 2013.7.11");

        //priceAskDAO.checkAndUpdateOverTime();

        _triggerLogger.info("end checkOverTime_Job");

        return true;
    }

    /**
     * 删除询价
     * 
     * @param bean
     * @return
     * @throws MYException
     */
    @Transactional(rollbackFor = {MYException.class})
    public boolean delPriceAskBean(User user, final String id)
        throws MYException
    {
        JudgeTools.judgeParameterIsNull(id);

        PriceAskBean bean = priceAskDAO.find(id);

        if (bean == null)
        {
            throw new MYException("数据错误,请重新操作");
        }

        if ( ! (bean.getStatus() == PriceConstant.PRICE_ASK_STATUS_INIT || bean.getStatus() == PriceConstant.PRICE_ASK_STATUS_EXCEPTION))
        {
            throw new MYException("已经询价,不能删除");
        }

        // 如果存在父询价，也不能删除
        if ( !StringTools.isNullOrNone(bean.getParentAsk()))
        {
            throw new MYException("询价中,不能删除");
        }

        priceAskDAO.deleteEntityBean(id);

        priceAskProviderDAO.deleteEntityBeansByFK(id);

        return true;
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
}
