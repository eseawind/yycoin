/**
 * File Name: TaxDAO.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2011-1-30<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.tax.dao;


import java.util.List;

import com.china.center.jdbc.inter.DAO;
import com.china.center.oa.tax.bean.TaxBean;
import com.china.center.oa.tax.vo.TaxVO;


/**
 * TaxDAO
 * 
 * @author ZHUZHU
 * @version 2011-1-30
 * @see TaxDAO
 * @since 1.0
 */
public interface TaxDAO extends DAO<TaxBean, TaxVO>
{
    /**
     * 获取银行关联的科目
     * 
     * @param bankId
     * @return
     */
    TaxBean findByBankId(String bankId);

    /**
     * 获取银行暂记户关联的科目
     * 
     * @param bankId
     * @return
     */
    TaxBean findTempByBankId(String bankId);

    /**
     * listLastStafferTax
     * 
     * @return
     */
    List<TaxVO> listLastStafferTax();

    /**
     * listLastUnitTax
     * 
     * @return
     */
    List<TaxVO> listLastUnitTax();
}
