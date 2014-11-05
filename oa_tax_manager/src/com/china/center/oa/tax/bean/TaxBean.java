/**
 * File Name: TaxTypeBean.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2011-1-30<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.tax.bean;


import java.io.Serializable;

import com.china.center.jdbc.annotation.Entity;
import com.china.center.jdbc.annotation.FK;
import com.china.center.jdbc.annotation.Html;
import com.china.center.jdbc.annotation.Id;
import com.china.center.jdbc.annotation.Join;
import com.china.center.jdbc.annotation.Table;
import com.china.center.jdbc.annotation.Unique;
import com.china.center.jdbc.annotation.enums.Element;
import com.china.center.jdbc.annotation.enums.JoinType;
import com.china.center.oa.finance.bean.BankBean;
import com.china.center.oa.tax.constanst.TaxConstanst;


/**
 * 科目表
 * 
 * @author ZHUZHU
 * @version 2011-1-30
 * @see TaxBean
 * @since 1.0
 */
@Entity
@Table(name = "T_CENTER_TAX")
public class TaxBean implements Serializable
{
    @Id
    private String id = "";

    /**
     * 父节点
     */
    @Join(tagClass = TaxBean.class, type = JoinType.LEFT, alias = "PA")
    @Html(title = "父科目", name = "parentName", maxLength = 40)
    private String parentId = "0";

    /**
     * 递归节点(一级科目的)
     */
    private String parentId0 = "";

    private String parentId1 = "";

    private String parentId2 = "";

    private String parentId3 = "";

    private String parentId4 = "";

    private String parentId5 = "";

    private String parentId6 = "";

    private String parentId7 = "";

    /**
     * 递归节点(9级科目的)
     */
    private String parentId8 = "";

    @Unique
    @Html(title = "编码", must = true, maxLength = 40)
    private String code = "";

    @Html(title = "名称", must = true, maxLength = 40)
    private String name = "";

    /**
     * 关联银行
     */
    @Join(tagClass = BankBean.class, type = JoinType.LEFT)
    @Html(title = "关联银行", type = Element.SELECT)
    private String refId = "";

    @FK
    @Join(tagClass = TaxTypeBean.class)
    @Html(title = "分类", must = true, type = Element.SELECT)
    private String ptype = "";

    @Html(title = "类型", must = true, type = Element.SELECT)
    private int type = TaxConstanst.TAX_TYPE_CASH;

    /**
     * 0:银行科目 1:暂记户银行科目
     */
    @Html(title = "银行关联类型", type = Element.SELECT)
    private int refType = TaxConstanst.TAX_REFTYPE_BANK;

    private int status = 0;

    /**
     * 父子节点
     */
    @Html(title = "节点", must = true, type = Element.SELECT)
    private int bottomFlag = TaxConstanst.TAX_BOTTOMFLAG_ROOT;

    /**
     * 科目级别(一级是0,最大不超过8)
     */
    private int level = TaxConstanst.TAX_LEVEL_DEFAULT;

    @Html(title = "余额方向", must = true, type = Element.SELECT)
    private int forward = TaxConstanst.TAX_FORWARD_IN;

    /**
     * 计算业务员提成
     */
    @Html(title = "计算业务员提成", must = true, type = Element.SELECT)
    private int checkStaffer = TaxConstanst.TAX_CHECKSTAFFER_NO;

    // 6大辅助核算
    private int unit = TaxConstanst.TAX_CHECK_NO;

    private int department = TaxConstanst.TAX_CHECK_NO;

    private int staffer = TaxConstanst.TAX_CHECK_NO;

    private int depot = TaxConstanst.TAX_CHECK_NO;

    private int product = TaxConstanst.TAX_CHECK_NO;

    private int duty = TaxConstanst.TAX_CHECK_NO;

    /**
     * default constructor
     */
    public TaxBean()
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
     * @return the name
     */
    public String getName()
    {
        return name;
    }

    /**
     * @param name
     *            the name to set
     */
    public void setName(String name)
    {
        this.name = name;
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
     * @return the code
     */
    public String getCode()
    {
        return code;
    }

    /**
     * @param code
     *            the code to set
     */
    public void setCode(String code)
    {
        this.code = code;
    }

    /**
     * @return the ptype
     */
    public String getPtype()
    {
        return ptype;
    }

    /**
     * @param ptype
     *            the ptype to set
     */
    public void setPtype(String ptype)
    {
        this.ptype = ptype;
    }

    /**
     * @return the status
     */
    public int getStatus()
    {
        return status;
    }

    /**
     * @param status
     *            the status to set
     */
    public void setStatus(int status)
    {
        this.status = status;
    }

    /**
     * @return the forward
     */
    public int getForward()
    {
        return forward;
    }

    /**
     * @param forward
     *            the forward to set
     */
    public void setForward(int forward)
    {
        this.forward = forward;
    }

    /**
     * @return the unit
     */
    public int getUnit()
    {
        return unit;
    }

    /**
     * @param unit
     *            the unit to set
     */
    public void setUnit(int unit)
    {
        this.unit = unit;
    }

    /**
     * @return the department
     */
    public int getDepartment()
    {
        return department;
    }

    /**
     * @param department
     *            the department to set
     */
    public void setDepartment(int department)
    {
        this.department = department;
    }

    /**
     * @return the staffer
     */
    public int getStaffer()
    {
        return staffer;
    }

    /**
     * @param staffer
     *            the staffer to set
     */
    public void setStaffer(int staffer)
    {
        this.staffer = staffer;
    }

    /**
     * @return the refId
     */
    public String getRefId()
    {
        return refId;
    }

    /**
     * @param refId
     *            the refId to set
     */
    public void setRefId(String refId)
    {
        this.refId = refId;
    }

    /**
     * @return the level
     */
    public int getLevel()
    {
        return level;
    }

    /**
     * @param level
     *            the level to set
     */
    public void setLevel(int level)
    {
        this.level = level;
    }

    /**
     * @return the parentId
     */
    public String getParentId()
    {
        return parentId;
    }

    /**
     * @param parentId
     *            the parentId to set
     */
    public void setParentId(String parentId)
    {
        this.parentId = parentId;
    }

    /**
     * @return the parentId0
     */
    public String getParentId0()
    {
        return parentId0;
    }

    /**
     * @param parentId0
     *            the parentId0 to set
     */
    public void setParentId0(String parentId0)
    {
        this.parentId0 = parentId0;
    }

    /**
     * @return the parentId1
     */
    public String getParentId1()
    {
        return parentId1;
    }

    /**
     * @param parentId1
     *            the parentId1 to set
     */
    public void setParentId1(String parentId1)
    {
        this.parentId1 = parentId1;
    }

    /**
     * @return the parentId2
     */
    public String getParentId2()
    {
        return parentId2;
    }

    /**
     * @param parentId2
     *            the parentId2 to set
     */
    public void setParentId2(String parentId2)
    {
        this.parentId2 = parentId2;
    }

    /**
     * @return the parentId3
     */
    public String getParentId3()
    {
        return parentId3;
    }

    /**
     * @param parentId3
     *            the parentId3 to set
     */
    public void setParentId3(String parentId3)
    {
        this.parentId3 = parentId3;
    }

    /**
     * @return the parentId4
     */
    public String getParentId4()
    {
        return parentId4;
    }

    /**
     * @param parentId4
     *            the parentId4 to set
     */
    public void setParentId4(String parentId4)
    {
        this.parentId4 = parentId4;
    }

    /**
     * @return the parentId5
     */
    public String getParentId5()
    {
        return parentId5;
    }

    /**
     * @param parentId5
     *            the parentId5 to set
     */
    public void setParentId5(String parentId5)
    {
        this.parentId5 = parentId5;
    }

    /**
     * @return the parentId6
     */
    public String getParentId6()
    {
        return parentId6;
    }

    /**
     * @param parentId6
     *            the parentId6 to set
     */
    public void setParentId6(String parentId6)
    {
        this.parentId6 = parentId6;
    }

    /**
     * @return the parentId7
     */
    public String getParentId7()
    {
        return parentId7;
    }

    /**
     * @param parentId7
     *            the parentId7 to set
     */
    public void setParentId7(String parentId7)
    {
        this.parentId7 = parentId7;
    }

    /**
     * @return the parentId8
     */
    public String getParentId8()
    {
        return parentId8;
    }

    /**
     * @param parentId8
     *            the parentId8 to set
     */
    public void setParentId8(String parentId8)
    {
        this.parentId8 = parentId8;
    }

    /**
     * @return the bottomFlag
     */
    public int getBottomFlag()
    {
        return bottomFlag;
    }

    /**
     * @param bottomFlag
     *            the bottomFlag to set
     */
    public void setBottomFlag(int bottomFlag)
    {
        this.bottomFlag = bottomFlag;
    }

    /**
     * @return the depot
     */
    public int getDepot()
    {
        return depot;
    }

    /**
     * @param depot
     *            the depot to set
     */
    public void setDepot(int depot)
    {
        this.depot = depot;
    }

    /**
     * @return the product
     */
    public int getProduct()
    {
        return product;
    }

    /**
     * @param product
     *            the product to set
     */
    public void setProduct(int product)
    {
        this.product = product;
    }

    /**
     * @return the duty
     */
    public int getDuty()
    {
        return duty;
    }

    /**
     * @param duty
     *            the duty to set
     */
    public void setDuty(int duty)
    {
        this.duty = duty;
    }

    /**
     * @return the refType
     */
    public int getRefType()
    {
        return refType;
    }

    /**
     * @param refType
     *            the refType to set
     */
    public void setRefType(int refType)
    {
        this.refType = refType;
    }

    /**
     * @return the checkStaffer
     */
    public int getCheckStaffer()
    {
        return checkStaffer;
    }

    /**
     * @param checkStaffer
     *            the checkStaffer to set
     */
    public void setCheckStaffer(int checkStaffer)
    {
        this.checkStaffer = checkStaffer;
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
            .append("TaxBean ( ")
            .append(super.toString())
            .append(TAB)
            .append("id = ")
            .append(this.id)
            .append(TAB)
            .append("parentId = ")
            .append(this.parentId)
            .append(TAB)
            .append("parentId0 = ")
            .append(this.parentId0)
            .append(TAB)
            .append("parentId1 = ")
            .append(this.parentId1)
            .append(TAB)
            .append("parentId2 = ")
            .append(this.parentId2)
            .append(TAB)
            .append("parentId3 = ")
            .append(this.parentId3)
            .append(TAB)
            .append("parentId4 = ")
            .append(this.parentId4)
            .append(TAB)
            .append("parentId5 = ")
            .append(this.parentId5)
            .append(TAB)
            .append("parentId6 = ")
            .append(this.parentId6)
            .append(TAB)
            .append("parentId7 = ")
            .append(this.parentId7)
            .append(TAB)
            .append("parentId8 = ")
            .append(this.parentId8)
            .append(TAB)
            .append("code = ")
            .append(this.code)
            .append(TAB)
            .append("name = ")
            .append(this.name)
            .append(TAB)
            .append("refId = ")
            .append(this.refId)
            .append(TAB)
            .append("ptype = ")
            .append(this.ptype)
            .append(TAB)
            .append("type = ")
            .append(this.type)
            .append(TAB)
            .append("refType = ")
            .append(this.refType)
            .append(TAB)
            .append("status = ")
            .append(this.status)
            .append(TAB)
            .append("bottomFlag = ")
            .append(this.bottomFlag)
            .append(TAB)
            .append("level = ")
            .append(this.level)
            .append(TAB)
            .append("forward = ")
            .append(this.forward)
            .append(TAB)
            .append("checkStaffer = ")
            .append(this.checkStaffer)
            .append(TAB)
            .append("unit = ")
            .append(this.unit)
            .append(TAB)
            .append("department = ")
            .append(this.department)
            .append(TAB)
            .append("staffer = ")
            .append(this.staffer)
            .append(TAB)
            .append("depot = ")
            .append(this.depot)
            .append(TAB)
            .append("product = ")
            .append(this.product)
            .append(TAB)
            .append("duty = ")
            .append(this.duty)
            .append(TAB)
            .append(" )");

        return retValue.toString();
    }

}
