/**
 * File Name: FinanceVO.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2011-2-6<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.tax.vo;


import com.china.center.jdbc.annotation.Entity;
import com.china.center.jdbc.annotation.Ignore;
import com.china.center.jdbc.annotation.Relationship;
import com.china.center.oa.tax.bean.FinanceItemBean;
import com.china.center.oa.tax.helper.FinanceHelper;


/**
 * FinanceVO
 * 
 * @author ZHUZHU
 * @version 2011-2-6
 * @see FinanceItemVO
 * @since 1.0
 */
@Entity(inherit = true)
public class FinanceItemVO extends FinanceItemBean
{
    @Relationship(relationField = "taxId")
    private String taxName = "";

    @Relationship(relationField = "taxId0")
    private String taxName0 = "";

    @Relationship(relationField = "taxId1")
    private String taxName1 = "";

    @Relationship(relationField = "taxId2")
    private String taxName2 = "";

    @Relationship(relationField = "taxId3")
    private String taxName3 = "";

    @Ignore
    private String unitName = "";

    @Ignore
    private String departmentName = "";

    @Ignore
    private String stafferName = "";

    @Ignore
    private String depotName = "";

    @Ignore
    private String productName = "";

    @Ignore
    private String productCode = "";

    @Ignore
    private String duty2Name = "";

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

    /**
     * 余额
     */
    @Ignore
    private String showLastmoney = "";

    /**
     * 显示金额(中文)
     */
    @Ignore
    private String showChineseInmoney = "";

    /**
     * 显示金额(中文)
     */
    @Ignore
    private String showChineseOutmoney = "";

    /**
     * 余额(中文)
     */
    @Ignore
    private String showChineseLastmoney = "";

    @Ignore
    private String forwardName = "";

    @Ignore
    private long lastmoney = 0;

    /**
     * default constructor
     */
    public FinanceItemVO()
    {
    }

    /**
     * @return the unitName
     */
    public String getUnitName()
    {
        return unitName;
    }

    /**
     * @param unitName
     *            the unitName to set
     */
    public void setUnitName(String unitName)
    {
        this.unitName = unitName;
    }

    /**
     * @return the departmentName
     */
    public String getDepartmentName()
    {
        return departmentName;
    }

    /**
     * @param departmentName
     *            the departmentName to set
     */
    public void setDepartmentName(String departmentName)
    {
        this.departmentName = departmentName;
    }

    /**
     * @return the stafferName
     */
    public String getStafferName()
    {
        return stafferName;
    }

    /**
     * @param stafferName
     *            the stafferName to set
     */
    public void setStafferName(String stafferName)
    {
        this.stafferName = stafferName;
    }

    /**
     * @return the taxName
     */
    public String getTaxName()
    {
        return taxName;
    }

    /**
     * @param taxName
     *            the taxName to set
     */
    public void setTaxName(String taxName)
    {
        this.taxName = taxName;
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
     * @return the depotName
     */
    public String getDepotName()
    {
        return depotName;
    }

    /**
     * @param depotName
     *            the depotName to set
     */
    public void setDepotName(String depotName)
    {
        this.depotName = depotName;
    }

    /**
     * @return the productName
     */
    public String getProductName()
    {
        return productName;
    }

    /**
     * @param productName
     *            the productName to set
     */
    public void setProductName(String productName)
    {
        this.productName = productName;
    }

    /**
     * @return the duty2Name
     */
    public String getDuty2Name()
    {
        return duty2Name;
    }

    /**
     * @param duty2Name
     *            the duty2Name to set
     */
    public void setDuty2Name(String duty2Name)
    {
        this.duty2Name = duty2Name;
    }

    /**
     * @return the taxName0
     */
    public String getTaxName0()
    {
        return taxName0;
    }

    /**
     * @param taxName0
     *            the taxName0 to set
     */
    public void setTaxName0(String taxName0)
    {
        this.taxName0 = taxName0;
    }

    /**
     * @return the taxName1
     */
    public String getTaxName1()
    {
        return taxName1;
    }

    /**
     * @param taxName1
     *            the taxName1 to set
     */
    public void setTaxName1(String taxName1)
    {
        this.taxName1 = taxName1;
    }

    /**
     * @return the taxName2
     */
    public String getTaxName2()
    {
        return taxName2;
    }

    /**
     * @param taxName2
     *            the taxName2 to set
     */
    public void setTaxName2(String taxName2)
    {
        this.taxName2 = taxName2;
    }

    /**
     * @return the taxName3
     */
    public String getTaxName3()
    {
        return taxName3;
    }

    /**
     * @param taxName3
     *            the taxName3 to set
     */
    public void setTaxName3(String taxName3)
    {
        this.taxName3 = taxName3;
    }

    /**
     * @return the productCode
     */
    public String getProductCode()
    {
        return productCode;
    }

    /**
     * @param productCode
     *            the productCode to set
     */
    public void setProductCode(String productCode)
    {
        this.productCode = productCode;
    }

    /**
     * @return the showLastmoney
     */
    public String getShowLastmoney()
    {
        return showLastmoney;
    }

    /**
     * @param showLastmoney
     *            the showLastmoney to set
     */
    public void setShowLastmoney(String showLastmoney)
    {
        this.showLastmoney = showLastmoney;
    }

    /**
     * @return the forwardName
     */
    public String getForwardName()
    {
        return forwardName;
    }

    /**
     * @param forwardName
     *            the forwardName to set
     */
    public void setForwardName(String forwardName)
    {
        this.forwardName = forwardName;
    }

    /**
     * @return the lastmoney
     */
    public long getLastmoney()
    {
        return lastmoney;
    }

    /**
     * @param lastmoney
     *            the lastmoney to set
     */
    public void setLastmoney(long lastmoney)
    {
        this.lastmoney = lastmoney;
    }

    /**
     * @return the showChineseInmoney
     */
    public String getShowChineseInmoney()
    {
        this.showChineseInmoney = FinanceHelper.longToChineseString(super.getInmoney());

        return showChineseInmoney;
    }

    /**
     * @param showChineseInmoney
     *            the showChineseInmoney to set
     */
    public void setShowChineseInmoney(String showChineseInmoney)
    {
        this.showChineseInmoney = showChineseInmoney;
    }

    /**
     * @return the showChineseOutmoney
     */
    public String getShowChineseOutmoney()
    {
        this.showChineseOutmoney = FinanceHelper.longToChineseString(super.getOutmoney());

        return showChineseOutmoney;
    }

    /**
     * @param showChineseOutmoney
     *            the showChineseOutmoney to set
     */
    public void setShowChineseOutmoney(String showChineseOutmoney)
    {
        this.showChineseOutmoney = showChineseOutmoney;
    }

    /**
     * @return the showChineseLastmoney
     */
    public String getShowChineseLastmoney()
    {
        this.showChineseLastmoney = FinanceHelper.longToChineseString(getLastmoney());

        return showChineseLastmoney;
    }

    /**
     * @param showChineseLastmoney
     *            the showChineseLastmoney to set
     */
    public void setShowChineseLastmoney(String showChineseLastmoney)
    {
        this.showChineseLastmoney = showChineseLastmoney;
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
            .append("FinanceItemVO ( ")
            .append(super.toString())
            .append(TAB)
            .append("taxName = ")
            .append(this.taxName)
            .append(TAB)
            .append("taxName0 = ")
            .append(this.taxName0)
            .append(TAB)
            .append("taxName1 = ")
            .append(this.taxName1)
            .append(TAB)
            .append("taxName2 = ")
            .append(this.taxName2)
            .append(TAB)
            .append("taxName3 = ")
            .append(this.taxName3)
            .append(TAB)
            .append("unitName = ")
            .append(this.unitName)
            .append(TAB)
            .append("departmentName = ")
            .append(this.departmentName)
            .append(TAB)
            .append("stafferName = ")
            .append(this.stafferName)
            .append(TAB)
            .append("depotName = ")
            .append(this.depotName)
            .append(TAB)
            .append("productName = ")
            .append(this.productName)
            .append(TAB)
            .append("productCode = ")
            .append(this.productCode)
            .append(TAB)
            .append("duty2Name = ")
            .append(this.duty2Name)
            .append(TAB)
            .append("showInmoney = ")
            .append(this.showInmoney)
            .append(TAB)
            .append("showOutmoney = ")
            .append(this.showOutmoney)
            .append(TAB)
            .append("showLastmoney = ")
            .append(this.showLastmoney)
            .append(TAB)
            .append("showChineseInmoney = ")
            .append(this.showChineseInmoney)
            .append(TAB)
            .append("showChineseOutmoney = ")
            .append(this.showChineseOutmoney)
            .append(TAB)
            .append("showChineseLastmoney = ")
            .append(this.showChineseLastmoney)
            .append(TAB)
            .append("forwardName = ")
            .append(this.forwardName)
            .append(TAB)
            .append("lastmoney = ")
            .append(this.lastmoney)
            .append(TAB)
            .append(" )");

        return retValue.toString();
    }

}
