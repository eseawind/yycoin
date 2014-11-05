/**
 * File Name: FinanceTempVO.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2012-1-16<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.tax.vo;


import java.util.List;

import com.china.center.jdbc.annotation.Entity;
import com.china.center.jdbc.annotation.Ignore;
import com.china.center.jdbc.annotation.Relationship;
import com.china.center.oa.tax.bean.FinanceTempBean;
import com.china.center.oa.tax.helper.FinanceHelper;


/**
 * FinanceTempVO
 * 
 * @author ZHUZHU
 * @version 2012-1-16
 * @see FinanceTempVO
 * @since 3.0
 */
@Entity(inherit = true)
public class FinanceTempVO extends FinanceTempBean
{

    @Relationship(relationField = "dutyId")
    private String dutyName = "";

    @Relationship(relationField = "createrId")
    private String createrName = "";

    /**
     * 显示金额
     */
    @Ignore
    private String showInmoney = "";

    /**
     * 显示金额
     */
    @Ignore
    private String showOutmoney = "";

    @Ignore
    private List<FinanceItemVO> itemVOList = null;

    /**
     * default constructor
     */
    public FinanceTempVO()
    {
    }

    /**
     * @return the itemVOList
     */
    public List<FinanceItemVO> getItemVOList()
    {
        return itemVOList;
    }

    /**
     * @param itemVOList
     *            the itemVOList to set
     */
    public void setItemVOList(List<FinanceItemVO> itemVOList)
    {
        this.itemVOList = itemVOList;
    }

    /**
     * @return the dutyName
     */
    public String getDutyName()
    {
        return dutyName;
    }

    /**
     * @param dutyName
     *            the dutyName to set
     */
    public void setDutyName(String dutyName)
    {
        this.dutyName = dutyName;
    }

    /**
     * @return the createrName
     */
    public String getCreaterName()
    {
        return createrName;
    }

    /**
     * @param createrName
     *            the createrName to set
     */
    public void setCreaterName(String createrName)
    {
        this.createrName = createrName;
    }

    /**
     * @return the showInmoney
     */
    public String getShowInmoney()
    {
        this.showInmoney = FinanceHelper.longToString(super.getInmoney());

        return showInmoney;
    }

    /**
     * @param showInmoney
     *            the showInmoney to set
     */
    public void setShowInmoney(String showInmoney)
    {
        this.showInmoney = showInmoney;
    }

    /**
     * @return the showOutmoney
     */
    public String getShowOutmoney()
    {
        this.showOutmoney = FinanceHelper.longToString(super.getOutmoney());

        return showOutmoney;
    }

    /**
     * @param showOutmoney
     *            the showOutmoney to set
     */
    public void setShowOutmoney(String showOutmoney)
    {
        this.showOutmoney = showOutmoney;
    }

    /**
     * Constructs a <code>String</code> with all attributes in name = value format.
     * 
     * @return a <code>String</code> representation of this object.
     */
    public String toString()
    {
        final String TAB = ",";

        StringBuilder retValue = new StringBuilder();

        retValue.append("FinanceTempVO ( ").append(super.toString()).append(TAB).append(
            "dutyName = ").append(this.dutyName).append(TAB).append("createrName = ").append(
            this.createrName).append(TAB).append("showInmoney = ").append(this.showInmoney).append(
            TAB).append("showOutmoney = ").append(this.showOutmoney).append(TAB).append(
            "itemVOList = ").append(this.itemVOList).append(TAB).append(" )");

        return retValue.toString();
    }

}
