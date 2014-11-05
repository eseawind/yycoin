/**
 * File Name: TravelApplyItemVO.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2011-7-10<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.tcp.vo;


import com.china.center.jdbc.annotation.Entity;
import com.china.center.jdbc.annotation.Ignore;
import com.china.center.jdbc.annotation.Relationship;
import com.china.center.oa.tcp.bean.TravelApplyItemBean;


/**
 * TravelApplyItemVO
 * 
 * @author ZHUZHU
 * @version 2011-7-10
 * @see TravelApplyItemVO
 * @since 3.0
 */
@Entity(inherit = true)
public class TravelApplyItemVO extends TravelApplyItemBean
{
    @Relationship(relationField = "feeItemId")
    private String feeItemName = "";

    @Ignore
    private String showMoneys = "";

    @Relationship(relationField = "feeStafferId")
    private String feeStafferName = "";

    @Relationship(relationField = "budgetId")
    private String budgetName = "";

    /**
     * default constructor
     */
    public TravelApplyItemVO()
    {
    }

    /**
     * @return the feeItemName
     */
    public String getFeeItemName()
    {
        return feeItemName;
    }

    /**
     * @param feeItemName
     *            the feeItemName to set
     */
    public void setFeeItemName(String feeItemName)
    {
        this.feeItemName = feeItemName;
    }

    /**
     * @return the showMoneys
     */
    public String getShowMoneys()
    {
        return showMoneys;
    }

    /**
     * @param showMoneys
     *            the showMoneys to set
     */
    public void setShowMoneys(String showMoneys)
    {
        this.showMoneys = showMoneys;
    }

    /**
     * @return the feeStafferName
     */
    public String getFeeStafferName()
    {
        return feeStafferName;
    }

    /**
     * @param feeStafferName
     *            the feeStafferName to set
     */
    public void setFeeStafferName(String feeStafferName)
    {
        this.feeStafferName = feeStafferName;
    }

    /**
     * @return the budgetName
     */
    public String getBudgetName()
    {
        return budgetName;
    }

    /**
     * @param budgetName
     *            the budgetName to set
     */
    public void setBudgetName(String budgetName)
    {
        this.budgetName = budgetName;
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

        retValue
            .append("TravelApplyItemVO ( ")
            .append(super.toString())
            .append(TAB)
            .append("feeItemName = ")
            .append(this.feeItemName)
            .append(TAB)
            .append("showMoneys = ")
            .append(this.showMoneys)
            .append(TAB)
            .append("feeStafferName = ")
            .append(this.feeStafferName)
            .append(TAB)
            .append("budgetName = ")
            .append(this.budgetName)
            .append(TAB)
            .append(" )");

        return retValue.toString();
    }

}
