/**
 * File Name: TaxDAOImpl.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2011-1-30<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.tax.dao.impl;


import java.util.List;

import com.china.center.jdbc.inter.impl.BaseDAO;
import com.china.center.jdbc.util.ConditionParse;
import com.china.center.oa.tax.bean.TaxBean;
import com.china.center.oa.tax.constanst.TaxConstanst;
import com.china.center.oa.tax.dao.TaxDAO;
import com.china.center.oa.tax.vo.TaxVO;
import com.china.center.tools.ListTools;


/**
 * TaxDAOImpl
 * 
 * @author ZHUZHU
 * @version 2011-1-30
 * @see TaxDAOImpl
 * @since 1.0
 */
public class TaxDAOImpl extends BaseDAO<TaxBean, TaxVO> implements TaxDAO
{
    public TaxBean findByBankId(String bankId)
    {
        ConditionParse conditionParse = new ConditionParse();

        conditionParse.addWhereStr();

        conditionParse.addCondition("refId", "=", bankId);

        conditionParse.addIntCondition("refType", "=", TaxConstanst.TAX_REFTYPE_BANK);

        List<TaxBean> list = this.queryEntityBeansByCondition(conditionParse);

        if (ListTools.isEmptyOrNull(list))
        {
            return null;
        }

        return list.get(0);
    }

    public TaxBean findTempByBankId(String bankId)
    {
        ConditionParse conditionParse = new ConditionParse();

        conditionParse.addWhereStr();

        conditionParse.addCondition("refId", "=", bankId);

        conditionParse.addIntCondition("refType", "=", TaxConstanst.TAX_REFTYPE_BANKTEMP);

        List<TaxBean> list = this.queryEntityBeansByCondition(conditionParse);

        if (ListTools.isEmptyOrNull(list))
        {
            return null;
        }

        return list.get(0);
    }

    public List<TaxVO> listLastStafferTax()
    {
        ConditionParse conditionParse = new ConditionParse();

        conditionParse.addWhereStr();

        conditionParse.addIntCondition("TaxBean.bottomFlag", "=", TaxConstanst.TAX_BOTTOMFLAG_ITEM);

        conditionParse.addIntCondition("TaxBean.staffer", "=", TaxConstanst.TAX_CHECK_YES);

        conditionParse.addCondition("order by TaxBean.id");

        return queryEntityVOsByCondition(conditionParse);
    }

    public List<TaxVO> listLastUnitTax()
    {
        ConditionParse conditionParse = new ConditionParse();

        conditionParse.addWhereStr();

        conditionParse.addIntCondition("TaxBean.bottomFlag", "=", TaxConstanst.TAX_BOTTOMFLAG_ITEM);

        conditionParse.addIntCondition("TaxBean.unit", "=", TaxConstanst.TAX_CHECK_YES);

        conditionParse.addCondition("order by TaxBean.id");

        return queryEntityVOsByCondition(conditionParse);
    }
}
