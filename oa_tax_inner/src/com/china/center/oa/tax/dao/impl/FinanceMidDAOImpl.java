package com.china.center.oa.tax.dao.impl;

import java.util.List;

import com.china.center.jdbc.inter.impl.BaseDAO;
import com.china.center.jdbc.util.ConditionParse;
import com.china.center.oa.tax.bean.FinanceMidBean;
import com.china.center.oa.tax.dao.FinanceMidDAO;

/**
 * 
 * 请输入功能描述
 *
 * @author fangliwen 2012-6-7
 */
public class FinanceMidDAOImpl extends BaseDAO<FinanceMidBean, FinanceMidBean> implements FinanceMidDAO {

    @Override
    public List<FinanceMidBean> queryRefFinanceMidBeanByCondition(String outId) {
        
        ConditionParse condtion = new ConditionParse();
        condtion.addWhereStr();
        condtion.addCondition("refOut", "=", outId);

        List<FinanceMidBean> result = this.queryEntityBeansByCondition(condtion);
        
        return result;
    }
    
}
