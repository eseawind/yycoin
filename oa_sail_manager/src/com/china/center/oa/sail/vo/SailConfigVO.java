/**
 * File Name: SailConfigVO.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2011-12-17<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.sail.vo;


import com.china.center.jdbc.annotation.Entity;
import com.china.center.jdbc.annotation.Ignore;
import com.china.center.jdbc.annotation.Relationship;
import com.china.center.oa.sail.bean.SailConfigBean;


/**
 * SailConfigVO
 * 
 * @author ZHUZHU
 * @version 2011-12-17
 * @see SailConfigVO
 * @since 3.0
 */
@Entity(inherit = true)
public class SailConfigVO extends SailConfigBean
{
    @Relationship(relationField = "showId")
    private String showName = "";

    @Ignore
    private String showAllName = "";

    @Ignore
    private String finTypeName0 = "";

    @Ignore
    private String finTypeName1 = "";

    @Ignore
    private String finTypeName2 = "";

    @Ignore
    private String finTypeName3 = "";

    @Ignore
    private String finTypeName4 = "";

    @Ignore
    private String finTypeName5 = "";

    /**
     * default constructor
     */
    public SailConfigVO()
    {
    }

    /**
     * @return the showName
     */
    public String getShowName()
    {
        return showName;
    }

    /**
     * @param showName
     *            the showName to set
     */
    public void setShowName(String showName)
    {
        this.showName = showName;
    }

    /**
     * @return the showAllName
     */
    public String getShowAllName()
    {
        return showAllName;
    }

    /**
     * @param showAllName
     *            the showAllName to set
     */
    public void setShowAllName(String showAllName)
    {
        this.showAllName = showAllName;
    }

    /**
     * @return the finTypeName0
     */
    public String getFinTypeName0()
    {
        return finTypeName0;
    }

    /**
     * @param finTypeName0
     *            the finTypeName0 to set
     */
    public void setFinTypeName0(String finTypeName0)
    {
        this.finTypeName0 = finTypeName0;
    }

    /**
     * @return the finTypeName1
     */
    public String getFinTypeName1()
    {
        return finTypeName1;
    }

    /**
     * @param finTypeName1
     *            the finTypeName1 to set
     */
    public void setFinTypeName1(String finTypeName1)
    {
        this.finTypeName1 = finTypeName1;
    }

    /**
     * @return the finTypeName2
     */
    public String getFinTypeName2()
    {
        return finTypeName2;
    }

    /**
     * @param finTypeName2
     *            the finTypeName2 to set
     */
    public void setFinTypeName2(String finTypeName2)
    {
        this.finTypeName2 = finTypeName2;
    }

    /**
     * @return the finTypeName3
     */
    public String getFinTypeName3()
    {
        return finTypeName3;
    }

    /**
     * @param finTypeName3
     *            the finTypeName3 to set
     */
    public void setFinTypeName3(String finTypeName3)
    {
        this.finTypeName3 = finTypeName3;
    }

    /**
     * @return the finTypeName4
     */
    public String getFinTypeName4()
    {
        return finTypeName4;
    }

    /**
     * @param finTypeName4
     *            the finTypeName4 to set
     */
    public void setFinTypeName4(String finTypeName4)
    {
        this.finTypeName4 = finTypeName4;
    }

    /**
     * @return the finTypeName5
     */
    public String getFinTypeName5()
    {
        return finTypeName5;
    }

    /**
     * @param finTypeName5
     *            the finTypeName5 to set
     */
    public void setFinTypeName5(String finTypeName5)
    {
        this.finTypeName5 = finTypeName5;
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
            .append("SailConfigVO ( ")
            .append(super.toString())
            .append(TAB)
            .append("showName = ")
            .append(this.showName)
            .append(TAB)
            .append("showAllName = ")
            .append(this.showAllName)
            .append(TAB)
            .append("finTypeName0 = ")
            .append(this.finTypeName0)
            .append(TAB)
            .append("finTypeName1 = ")
            .append(this.finTypeName1)
            .append(TAB)
            .append("finTypeName2 = ")
            .append(this.finTypeName2)
            .append(TAB)
            .append("finTypeName3 = ")
            .append(this.finTypeName3)
            .append(TAB)
            .append("finTypeName4 = ")
            .append(this.finTypeName4)
            .append(TAB)
            .append("finTypeName5 = ")
            .append(this.finTypeName5)
            .append(TAB)
            .append(" )");

        return retValue.toString();
    }
}
