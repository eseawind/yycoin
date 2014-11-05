/**
 * File Name: SailManagerImpl.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2012-1-2<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.sail.manager.impl;


import org.china.center.spring.iaop.annotation.IntegrationAOP;
import org.springframework.transaction.annotation.Transactional;

import com.center.china.osgi.publics.User;
import com.china.center.common.MYException;
import com.china.center.oa.publics.bean.FlowLogBean;
import com.china.center.oa.publics.constant.AuthConstant;
import com.china.center.oa.publics.dao.FlowLogDAO;
import com.china.center.oa.sail.bean.OutBean;
import com.china.center.oa.sail.constanst.SailConstant;
import com.china.center.oa.sail.dao.OutDAO;
import com.china.center.oa.sail.helper.OutHelper;
import com.china.center.oa.sail.manager.SailManager;
import com.china.center.tools.JudgeTools;
import com.china.center.tools.TimeTools;


/**
 * SailManagerImpl
 * 
 * @author ZHUZHU
 * @version 2012-1-2
 * @see SailManagerImpl
 * @since 1.0
 */
@IntegrationAOP
public class SailManagerImpl implements SailManager
{
    private OutDAO outDAO = null;

    private FlowLogDAO flowLogDAO = null;

    /*
     * (non-Javadoc)
     * 
     * @see com.china.center.oa.sail.manager.SailManager#updateInvoiceStatus(com.center.china.osgi.publics.User,
     *      java.lang.String, int)
     */
    @IntegrationAOP(auth = AuthConstant.SAIL_UPDATE_INVOICE)
    @Transactional(rollbackFor = MYException.class)
    public boolean updateInvoiceStatus(User user, String fullId, double invoiceMoney, int invoiceStatus)
        throws MYException
    {
        JudgeTools.judgeParameterIsNull(user, fullId);

        OutBean out = outDAO.find(fullId);

        if (out == null)
        {
            throw new MYException("销售单不存在，请重新操作");
        }

        if ( !OutHelper.isSailEnd(out))
        {
            throw new MYException("销售单状态不对,不能修改发票状态，请重新操作");
        }

        outDAO.updateInvoiceStatus(fullId, invoiceMoney, invoiceStatus);

        FlowLogBean log = new FlowLogBean();

        log.setActor(user.getStafferName());

        log.setDescription("修改单据已经开票");
        log.setFullId(fullId);
        log.setOprMode(SailConstant.OPR_OUT_PASS);
        log.setLogTime(TimeTools.now());

        log.setPreStatus(out.getStatus());

        log.setAfterStatus(out.getStatus());

        flowLogDAO.saveEntityBean(log);

        return true;
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
}
