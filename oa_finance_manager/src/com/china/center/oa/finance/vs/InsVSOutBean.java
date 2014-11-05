/**
 * File Name: InsVSOutBean.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2010-12-26<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.finance.vs;


import java.io.Serializable;

import com.china.center.jdbc.annosql.constant.AnoConstant;
import com.china.center.jdbc.annotation.Entity;
import com.china.center.jdbc.annotation.FK;
import com.china.center.jdbc.annotation.Id;
import com.china.center.jdbc.annotation.Table;
import com.china.center.oa.finance.constant.FinanceConstant;


/**
 * InsVSOutBean
 * 
 * @author ZHUZHU
 * @version 2010-12-26
 * @see InsVSOutBean
 * @since 3.0
 */
@Entity
@Table(name = "T_CENTER_VS_INSOUT")
public class InsVSOutBean implements Serializable
{
    @Id
    private String id = "";

    @FK(index = AnoConstant.FK_FIRST)
    private String insId = "";

    /**
     * 销售单ID和结算单的ID
     */
    @FK(index = AnoConstant.FK_DEFAULT)
    private String outId = "";

    /**
     * 子项ID
     */
    @FK(index = AnoConstant.FK_SECOND)
    private String baseId = "";

    /**
     * 结算单ID(废弃)
     */
    private String outBalanceId = "";

    /**
     * 0:销售单 1:结算单
     */
    private int type = FinanceConstant.INSVSOUT_TYPE_OUT;

    /**
     * 开票的金额
     */
    private double moneys = 0.0d;

    /**
     * default constructor
     */
    public InsVSOutBean()
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
     * @return the insId
     */
    public String getInsId()
    {
        return insId;
    }

    /**
     * @param insId
     *            the insId to set
     */
    public void setInsId(String insId)
    {
        this.insId = insId;
    }

    /**
     * @return the outId
     */
    public String getOutId()
    {
        return outId;
    }

    /**
     * @param outId
     *            the outId to set
     */
    public void setOutId(String outId)
    {
        this.outId = outId;
    }

    /**
     * @return the moneys
     */
    public double getMoneys()
    {
        return moneys;
    }

    /**
     * @param moneys
     *            the moneys to set
     */
    public void setMoneys(double moneys)
    {
        this.moneys = moneys;
    }

    /**
     * @return the outBalanceId
     */
    public String getOutBalanceId()
    {
        return outBalanceId;
    }

    /**
     * @param outBalanceId
     *            the outBalanceId to set
     */
    public void setOutBalanceId(String outBalanceId)
    {
        this.outBalanceId = outBalanceId;
    }

    /**
     * @return the type
     */
    public int getType()
    {
        return type;
    }

    /**
     * @param type
     *            the type to set
     */
    public void setType(int type)
    {
        this.type = type;
    }

    /**
     * @return the baseId
     */
    public String getBaseId()
    {
        return baseId;
    }

    /**
     * @param baseId
     *            the baseId to set
     */
    public void setBaseId(String baseId)
    {
        this.baseId = baseId;
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
            .append("InsVSOutBean ( ")
            .append(super.toString())
            .append(TAB)
            .append("id = ")
            .append(this.id)
            .append(TAB)
            .append("insId = ")
            .append(this.insId)
            .append(TAB)
            .append("outId = ")
            .append(this.outId)
            .append(TAB)
            .append("baseId = ")
            .append(this.baseId)
            .append(TAB)
            .append("outBalanceId = ")
            .append(this.outBalanceId)
            .append(TAB)
            .append("type = ")
            .append(this.type)
            .append(TAB)
            .append("moneys = ")
            .append(this.moneys)
            .append(TAB)
            .append(" )");

        return retValue.toString();
    }

}
