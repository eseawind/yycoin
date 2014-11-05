/**
 * File Name: SailConfigBean.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2011-12-17<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.sail.bean;


import java.io.Serializable;

import com.china.center.jdbc.annosql.constant.AnoConstant;
import com.china.center.jdbc.annotation.Entity;
import com.china.center.jdbc.annotation.FK;
import com.china.center.jdbc.annotation.Html;
import com.china.center.jdbc.annotation.Id;
import com.china.center.jdbc.annotation.Join;
import com.china.center.jdbc.annotation.Table;
import com.china.center.jdbc.annotation.Unique;
import com.china.center.jdbc.annotation.enums.Element;
import com.china.center.oa.publics.bean.ShowBean;
import com.china.center.oa.sail.constanst.SailConstant;


/**
 * SailConfigBean(废弃)
 * 
 * @author ZHUZHU
 * @version 2011-12-17
 * @see SailConfigBean
 * @since 3.0
 */
@Entity
@Table(name = "T_CENTER_SAILCONFIG")
public class SailConfigBean implements Serializable
{
    @Id
    private String id = "";

    /**
     * 一个pare里面有多个品名,其他的不行
     */
    @FK
    private String pareId = "";

    @Join(tagClass = ShowBean.class)
    @Unique(dependFields = {"sailType", "productType"})
    @FK(index = AnoConstant.FK_FIRST)
    private String showId = "";

    /**
     * 销售类型
     */
    @Html(title = "销售类型", type = Element.SELECT, must = true)
    private int sailType = 0;

    /**
     * 销售品类
     */
    @Html(title = "销售品类", type = Element.SELECT, must = true)
    private int productType = 0;

    /**
     * 一般纳税人
     */
    @Html(title = "一般纳税人", type = Element.SELECT, must = true)
    private int finType0 = SailConstant.SAILCONFIG_FIN_NO;

    /**
     * 小规模纳税人
     */
    @Html(title = "小规模纳税人", type = Element.SELECT, must = true)
    private int finType1 = SailConstant.SAILCONFIG_FIN_NO;

    /**
     * 服务类
     */
    @Html(title = "服务类", type = Element.SELECT, must = true)
    private int finType2 = SailConstant.SAILCONFIG_FIN_NO;

    /**
     * 个体户
     */
    @Html(title = "个体户", type = Element.SELECT, must = true)
    private int finType3 = SailConstant.SAILCONFIG_FIN_NO;

    /**
     * 一般纳税人(17+2)
     */
    @Html(title = "一般纳税人(17+2)", type = Element.SELECT, must = true)
    private int finType4 = SailConstant.SAILCONFIG_FIN_NO;

    /**
     * 一般纳税人(SPE)
     */
    @Html(title = "一般纳税人(SPE)", type = Element.SELECT, must = true)
    private int finType5 = SailConstant.SAILCONFIG_FIN_NO;

    /**
     * 税点
     */
    @Html(title = "税点(‰)", type = Element.NUMBER, must = true)
    private int ratio0 = 0;

    @Html(title = "税点(‰)", type = Element.NUMBER, must = true)
    private int ratio1 = 0;

    @Html(title = "税点(‰)", type = Element.NUMBER, must = true)
    private int ratio2 = 0;

    @Html(title = "税点(‰)", type = Element.NUMBER, must = true)
    private int ratio3 = 0;

    @Html(title = "税点(‰)", type = Element.NUMBER, must = true)
    private int ratio4 = 0;

    @Html(title = "税点(‰)", type = Element.NUMBER, must = true)
    private int ratio5 = 0;

    @Html(title = "描述", maxLength = 100, type = Element.TEXTAREA)
    private String description = "";

    /**
     * default constructor
     */
    public SailConfigBean()
    {
    }

    /**
     * @return the id
     */
    public String getId()
    {
        return id;
    }

    /**
     * @param id
     *            the id to set
     */
    public void setId(String id)
    {
        this.id = id;
    }

    /**
     * @return the pareId
     */
    public String getPareId()
    {
        return pareId;
    }

    /**
     * @param pareId
     *            the pareId to set
     */
    public void setPareId(String pareId)
    {
        this.pareId = pareId;
    }

    /**
     * @return the showId
     */
    public String getShowId()
    {
        return showId;
    }

    /**
     * @param showId
     *            the showId to set
     */
    public void setShowId(String showId)
    {
        this.showId = showId;
    }

    /**
     * @return the sailType
     */
    public int getSailType()
    {
        return sailType;
    }

    /**
     * @param sailType
     *            the sailType to set
     */
    public void setSailType(int sailType)
    {
        this.sailType = sailType;
    }

    /**
     * @return the productType
     */
    public int getProductType()
    {
        return productType;
    }

    /**
     * @param productType
     *            the productType to set
     */
    public void setProductType(int productType)
    {
        this.productType = productType;
    }

    /**
     * @return the finType0
     */
    public int getFinType0()
    {
        return finType0;
    }

    /**
     * @param finType0
     *            the finType0 to set
     */
    public void setFinType0(int finType0)
    {
        this.finType0 = finType0;
    }

    /**
     * @return the finType1
     */
    public int getFinType1()
    {
        return finType1;
    }

    /**
     * @param finType1
     *            the finType1 to set
     */
    public void setFinType1(int finType1)
    {
        this.finType1 = finType1;
    }

    /**
     * @return the finType2
     */
    public int getFinType2()
    {
        return finType2;
    }

    /**
     * @param finType2
     *            the finType2 to set
     */
    public void setFinType2(int finType2)
    {
        this.finType2 = finType2;
    }

    /**
     * @return the finType3
     */
    public int getFinType3()
    {
        return finType3;
    }

    /**
     * @param finType3
     *            the finType3 to set
     */
    public void setFinType3(int finType3)
    {
        this.finType3 = finType3;
    }

    /**
     * @return the finType4
     */
    public int getFinType4()
    {
        return finType4;
    }

    /**
     * @param finType4
     *            the finType4 to set
     */
    public void setFinType4(int finType4)
    {
        this.finType4 = finType4;
    }

    /**
     * @return the description
     */
    public String getDescription()
    {
        return description;
    }

    /**
     * @param description
     *            the description to set
     */
    public void setDescription(String description)
    {
        this.description = description;
    }

    /**
     * @return the finType5
     */
    public int getFinType5()
    {
        return finType5;
    }

    /**
     * @param finType5
     *            the finType5 to set
     */
    public void setFinType5(int finType5)
    {
        this.finType5 = finType5;
    }

    /**
     * @return the ratio0
     */
    public int getRatio0()
    {
        return ratio0;
    }

    /**
     * @param ratio0
     *            the ratio0 to set
     */
    public void setRatio0(int ratio0)
    {
        this.ratio0 = ratio0;
    }

    /**
     * @return the ratio1
     */
    public int getRatio1()
    {
        return ratio1;
    }

    /**
     * @param ratio1
     *            the ratio1 to set
     */
    public void setRatio1(int ratio1)
    {
        this.ratio1 = ratio1;
    }

    /**
     * @return the ratio2
     */
    public int getRatio2()
    {
        return ratio2;
    }

    /**
     * @param ratio2
     *            the ratio2 to set
     */
    public void setRatio2(int ratio2)
    {
        this.ratio2 = ratio2;
    }

    /**
     * @return the ratio3
     */
    public int getRatio3()
    {
        return ratio3;
    }

    /**
     * @param ratio3
     *            the ratio3 to set
     */
    public void setRatio3(int ratio3)
    {
        this.ratio3 = ratio3;
    }

    /**
     * @return the ratio4
     */
    public int getRatio4()
    {
        return ratio4;
    }

    /**
     * @param ratio4
     *            the ratio4 to set
     */
    public void setRatio4(int ratio4)
    {
        this.ratio4 = ratio4;
    }

    /**
     * @return the ratio5
     */
    public int getRatio5()
    {
        return ratio5;
    }

    /**
     * @param ratio5
     *            the ratio5 to set
     */
    public void setRatio5(int ratio5)
    {
        this.ratio5 = ratio5;
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
            .append("SailConfigBean ( ")
            .append(super.toString())
            .append(TAB)
            .append("id = ")
            .append(this.id)
            .append(TAB)
            .append("pareId = ")
            .append(this.pareId)
            .append(TAB)
            .append("showId = ")
            .append(this.showId)
            .append(TAB)
            .append("sailType = ")
            .append(this.sailType)
            .append(TAB)
            .append("productType = ")
            .append(this.productType)
            .append(TAB)
            .append("finType0 = ")
            .append(this.finType0)
            .append(TAB)
            .append("finType1 = ")
            .append(this.finType1)
            .append(TAB)
            .append("finType2 = ")
            .append(this.finType2)
            .append(TAB)
            .append("finType3 = ")
            .append(this.finType3)
            .append(TAB)
            .append("finType4 = ")
            .append(this.finType4)
            .append(TAB)
            .append("finType5 = ")
            .append(this.finType5)
            .append(TAB)
            .append("ratio0 = ")
            .append(this.ratio0)
            .append(TAB)
            .append("ratio1 = ")
            .append(this.ratio1)
            .append(TAB)
            .append("ratio2 = ")
            .append(this.ratio2)
            .append(TAB)
            .append("ratio3 = ")
            .append(this.ratio3)
            .append(TAB)
            .append("ratio4 = ")
            .append(this.ratio4)
            .append(TAB)
            .append("ratio5 = ")
            .append(this.ratio5)
            .append(TAB)
            .append("description = ")
            .append(this.description)
            .append(TAB)
            .append(" )");

        return retValue.toString();
    }

}
