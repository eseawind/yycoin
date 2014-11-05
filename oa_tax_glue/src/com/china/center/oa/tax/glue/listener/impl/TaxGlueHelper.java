/**
 * File Name: TaxGlueHelper.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2011-6-19<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.tax.glue.listener.impl;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.china.center.common.MYException;
import com.china.center.oa.finance.bean.BankBean;
import com.china.center.oa.publics.constant.PublicConstant;
import com.china.center.oa.tax.bean.FinanceBean;
import com.china.center.oa.tax.bean.TaxBean;
import com.china.center.oa.tax.dao.TaxDAO;


/**
 * TaxGlueHelper
 * 
 * @author ZHUZHU
 * @version 2011-6-19
 * @see TaxGlueHelper
 * @since 3.0
 */
public abstract class TaxGlueHelper
{
    private static final Log badLog = LogFactory.getLog("bad");

    /**
     * TODO 统一兼容性处理
     * 
     * @param bankId
     * @param taxDAO
     * @return
     * @throws MYException
     */
    public static boolean bankGoon(String bankId, TaxDAO taxDAO)
        throws MYException
    {
        // 这里暂时不启用
        TaxBean inTax = taxDAO.findByBankId(bankId);

        if (inTax == null)
        {
            badLog.error("缺少银行科目:" + bankId);
            throw new MYException("缺少银行科目:" + bankId);
        }

        TaxBean outTax = taxDAO.findTempByBankId(bankId);

        if (outTax == null)
        {
            badLog.error("缺少银行临时科目:" + bankId);
            throw new MYException("缺少银行临时科目:" + bankId);
        }

        return true;
    }

    /**
     * bankGoon
     * 
     * @param bank
     * @param taxDAO
     * @return
     * @throws MYException
     */
    public static boolean bankGoon(BankBean bank, TaxDAO taxDAO)
        throws MYException
    {
        String bankId = bank.getId();

        TaxBean inTax = taxDAO.findByBankId(bankId);

        if (inTax == null)
        {
            badLog.error("缺少银行科目:" + bankId + ".银行:" + bank.getName());

            throw new MYException("缺少银行科目:" + bankId + ".银行:" + bank.getName());
        }

        TaxBean outTax = taxDAO.findTempByBankId(bankId);

        if (outTax == null)
        {
            badLog.error("缺少银行临时科目:" + bankId + ".银行:" + bank.getName());

            throw new MYException("缺少银行临时科目:" + bankId + ".银行:" + bank.getName());
        }

        return true;
    }

    /**
     * setDutyId
     * 
     * @param financeBean
     * @param mtype
     */
    public static void setDutyId(FinanceBean financeBean, int mtype)
    {
        if (mtype == PublicConstant.MANAGER_TYPE_MANAGER)
        {
            financeBean.setDutyId(PublicConstant.MANAGER_DUTY_ID);
        }
        else
        {
            financeBean.setDutyId(PublicConstant.DEFAULR_DUTY_ID);
        }
    }
}
