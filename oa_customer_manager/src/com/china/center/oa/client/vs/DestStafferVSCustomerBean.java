package com.china.center.oa.client.vs;

import java.io.Serializable;

import com.china.center.jdbc.annosql.constant.AnoConstant;
import com.china.center.jdbc.annotation.Entity;
import com.china.center.jdbc.annotation.FK;
import com.china.center.jdbc.annotation.Id;
import com.china.center.jdbc.annotation.Join;
import com.china.center.jdbc.annotation.Table;
import com.china.center.jdbc.annotation.Unique;
import com.china.center.jdbc.annotation.enums.JoinType;
import com.china.center.oa.client.bean.CustomerBean;
import com.china.center.oa.publics.bean.StafferBean;

@Entity
@Table(name = "t_center_vs_deststacus")
public class DestStafferVSCustomerBean implements Serializable
{

    @Id(autoIncrement = true)
    private String id = "";

    @FK
    @Join(tagClass = StafferBean.class, type = JoinType.LEFT, alias = "s1")
    private String stafferId = "";

    @FK(index = AnoConstant.FK_SECOND)
    @Join(tagClass = StafferBean.class, type = JoinType.LEFT, alias = "s2")
    private String destStafferId = "";
    
    @Unique
    @FK(index = AnoConstant.FK_FIRST)
    @Join(tagClass = CustomerBean.class)
    private String customerId = "";

    /**
     * default constructor
     */
    public DestStafferVSCustomerBean()
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
     * @return the stafferId
     */
    public String getStafferId()
    {
        return stafferId;
    }

    /**
     * @param stafferId
     *            the stafferId to set
     */
    public void setStafferId(String stafferId)
    {
        this.stafferId = stafferId;
    }

    /**
     * @return the customerId
     */
    public String getCustomerId()
    {
        return customerId;
    }

    /**
     * @param customerId
     *            the customerId to set
     */
    public void setCustomerId(String customerId)
    {
        this.customerId = customerId;
    }

	public String getDestStafferId()
	{
		return destStafferId;
	}

	public void setDestStafferId(String destStafferId)
	{
		this.destStafferId = destStafferId;
	}

}
