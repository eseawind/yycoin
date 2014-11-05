/**
 * File Name: FinanceBean.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2011-2-6<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.tax.bean;


import java.io.Serializable;
import java.util.List;

import com.china.center.jdbc.annotation.Entity;
import com.china.center.jdbc.annotation.FK;
import com.china.center.jdbc.annotation.Id;
import com.china.center.jdbc.annotation.Ignore;
import com.china.center.jdbc.annotation.Join;
import com.china.center.jdbc.annotation.Table;
import com.china.center.jdbc.annotation.enums.JoinType;
import com.china.center.oa.publics.bean.DutyBean;
import com.china.center.oa.publics.bean.StafferBean;
import com.china.center.oa.publics.constant.PublicConstant;
import com.china.center.oa.tax.constanst.TaxConstanst;


/**
 * 凭证
 * 
 * @author ZHUZHU
 * @version 2011-2-6
 * @see FinanceBean
 * @since 1.0
 */
@Entity
@Table(name = "T_CENTER_FINANCE")
public class FinanceBean implements Serializable
{
    @Id
    private String id = "";

    private String name = "";

    /**
     * 凭证类型
     */
    private int type = TaxConstanst.FINANCE_TYPE_MANAGER;

    /**
     * 0:未人工核对 1:已核对 2:删除隐藏
     */
    private int status = TaxConstanst.FINANCE_STATUS_NOCHECK;

    /**
     * 锁定(结转后就锁定)
     */
    private int locks = TaxConstanst.FINANCE_LOCK_NO;

    /**
     * 更新标识
     */
    private int updateFlag = TaxConstanst.FINANCE_UPDATEFLAG_NO;

    /**
     * 凭证创建的类型(0:手工创建 其他是系统创建/也有不同分类)
     */
    private int createType = TaxConstanst.FINANCE_CREATETYPE_HAND;

    private int monthIndex = 0;

    /**
     * 主关联单据
     */
    @FK
    private String refId = "";

    private String refOut = "";

    private String refBill = "";

    private String refStock = "";

    /**
     * 管理凭证是不分纳税实体的(都在总部),税务凭证是分纳税实体的
     */
    @Join(tagClass = DutyBean.class)
    private String dutyId = PublicConstant.DEFAULR_DUTY_ID;

    @Join(tagClass = StafferBean.class, type = JoinType.LEFT)
    private String createrId = "";

    /**
     * 准确到微分,就是小数点后四位
     */
    private long inmoney = 0;

    /**
     * 准确到微分,就是小数点后四位
     */
    private long outmoney = 0;

    private String description = "";

    /**
     * 凭证时间
     */
    private String financeDate = "";

    /**
     * 入库时间
     */
    private String logTime = "";

    /**
     * 核对信息
     */
    private String checks = "";

    /**
     * 关联核对
     */
    private String refChecks = "";

    @Ignore
    private List<FinanceItemBean> itemList = null;

    /**
     * default constructor
     */
    public FinanceBean()
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
     * @return the createType
     */
    public int getCreateType()
    {
        return createType;
    }

    /**
     * @param createType
     *            the createType to set
     */
    public void setCreateType(int createType)
    {
        this.createType = createType;
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
     * @return the createrId
     */
    public String getCreaterId()
    {
        return createrId;
    }

    /**
     * @param createrId
     *            the createrId to set
     */
    public void setCreaterId(String createrId)
    {
        this.createrId = createrId;
    }

    /**
     * @return the inmoney
     */
    public long getInmoney()
    {
        return inmoney;
    }

    /**
     * @param inmoney
     *            the inmoney to set
     */
    public void setInmoney(long inmoney)
    {
        this.inmoney = inmoney;
    }

    /**
     * @return the outmoney
     */
    public long getOutmoney()
    {
        return outmoney;
    }

    /**
     * @param outmoney
     *            the outmoney to set
     */
    public void setOutmoney(long outmoney)
    {
        this.outmoney = outmoney;
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
     * @return the logTime
     */
    public String getLogTime()
    {
        return logTime;
    }

    /**
     * @param logTime
     *            the logTime to set
     */
    public void setLogTime(String logTime)
    {
        this.logTime = logTime;
    }

    /**
     * @return the itemList
     */
    public List<FinanceItemBean> getItemList()
    {
        return itemList;
    }

    /**
     * @param itemList
     *            the itemList to set
     */
    public void setItemList(List<FinanceItemBean> itemList)
    {
        this.itemList = itemList;
    }

    /**
     * @return the dutyId
     */
    public String getDutyId()
    {
        return dutyId;
    }

    /**
     * @param dutyId
     *            the dutyId to set
     */
    public void setDutyId(String dutyId)
    {
        this.dutyId = dutyId;
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
     * @return the financeDate
     */
    public String getFinanceDate()
    {
        return financeDate;
    }

    /**
     * @param financeDate
     *            the financeDate to set
     */
    public void setFinanceDate(String financeDate)
    {
        this.financeDate = financeDate;
    }

    /**
     * @return the checks
     */
    public String getChecks()
    {
        return checks;
    }

    /**
     * @param checks
     *            the checks to set
     */
    public void setChecks(String checks)
    {
        this.checks = checks;
    }

    /**
     * @return the updateFlag
     */
    public int getUpdateFlag()
    {
        return updateFlag;
    }

    /**
     * @param updateFlag
     *            the updateFlag to set
     */
    public void setUpdateFlag(int updateFlag)
    {
        this.updateFlag = updateFlag;
    }

    /**
     * @return the refOut
     */
    public String getRefOut()
    {
        return refOut;
    }

    /**
     * @param refOut
     *            the refOut to set
     */
    public void setRefOut(String refOut)
    {
        this.refOut = refOut;
    }

    /**
     * @return the refBill
     */
    public String getRefBill()
    {
        return refBill;
    }

    /**
     * @param refBill
     *            the refBill to set
     */
    public void setRefBill(String refBill)
    {
        this.refBill = refBill;
    }

    /**
     * @return the refStock
     */
    public String getRefStock()
    {
        return refStock;
    }

    /**
     * @param refStock
     *            the refStock to set
     */
    public void setRefStock(String refStock)
    {
        this.refStock = refStock;
    }

    /**
     * @return the locks
     */
    public int getLocks()
    {
        return locks;
    }

    /**
     * @param locks
     *            the locks to set
     */
    public void setLocks(int locks)
    {
        this.locks = locks;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + ( (id == null) ? 0 : id.hashCode());
        return result;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj)
    {
        if (this == obj) return true;
        if (obj == null) return false;
        if ( ! (obj instanceof FinanceBean)) return false;
        final FinanceBean other = (FinanceBean)obj;
        if (id == null)
        {
            if (other.id != null) return false;
        }
        else if ( !id.equals(other.id)) return false;
        return true;
    }

    /**
     * @return the monthIndex
     */
    public int getMonthIndex()
    {
        return monthIndex;
    }

    /**
     * @param monthIndex
     *            the monthIndex to set
     */
    public void setMonthIndex(int monthIndex)
    {
        this.monthIndex = monthIndex;
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
            .append("FinanceBean ( ")
            .append(super.toString())
            .append(TAB)
            .append("id = ")
            .append(this.id)
            .append(TAB)
            .append("name = ")
            .append(this.name)
            .append(TAB)
            .append("type = ")
            .append(this.type)
            .append(TAB)
            .append("status = ")
            .append(this.status)
            .append(TAB)
            .append("locks = ")
            .append(this.locks)
            .append(TAB)
            .append("updateFlag = ")
            .append(this.updateFlag)
            .append(TAB)
            .append("createType = ")
            .append(this.createType)
            .append(TAB)
            .append("monthIndex = ")
            .append(this.monthIndex)
            .append(TAB)
            .append("refId = ")
            .append(this.refId)
            .append(TAB)
            .append("refOut = ")
            .append(this.refOut)
            .append(TAB)
            .append("refBill = ")
            .append(this.refBill)
            .append(TAB)
            .append("refStock = ")
            .append(this.refStock)
            .append(TAB)
            .append("dutyId = ")
            .append(this.dutyId)
            .append(TAB)
            .append("createrId = ")
            .append(this.createrId)
            .append(TAB)
            .append("inmoney = ")
            .append(this.inmoney)
            .append(TAB)
            .append("outmoney = ")
            .append(this.outmoney)
            .append(TAB)
            .append("description = ")
            .append(this.description)
            .append(TAB)
            .append("financeDate = ")
            .append(this.financeDate)
            .append(TAB)
            .append("logTime = ")
            .append(this.logTime)
            .append(TAB)
            .append("checks = ")
            .append(this.checks)
            .append(TAB)
            .append("itemList = ")
            .append(this.itemList)
            .append(TAB)
            .append(" )");

        return retValue.toString();
    }

    /**
     * @return the refChecks
     */
    public String getRefChecks()
    {
        return refChecks;
    }

    /**
     * @param refChecks
     *            the refChecks to set
     */
    public void setRefChecks(String refChecks)
    {
        this.refChecks = refChecks;
    }

}
