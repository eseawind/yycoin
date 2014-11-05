/**
 * File Name: ComposeProductVO.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2010-10-2<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.product.vo;


import java.util.List;

import com.china.center.jdbc.annotation.Entity;
import com.china.center.jdbc.annotation.Ignore;
import com.china.center.jdbc.annotation.Relationship;
import com.china.center.oa.product.bean.GSOutBean;


/**
 * GSOutVO
 * 
 * @author ZHUZHU
 * @version 2010-10-2
 * @see GSOutVO
 * @since 1.0
 */
@SuppressWarnings("serial")
@Entity(inherit = true)
public class GSOutVO extends GSOutBean
{
    @Relationship(relationField = "stafferId")
    private String stafferName = "";

    @Ignore
    private String otherId = "";

    @Ignore
    private List<GSOutItemVO> itemVOList = null;

    /**
     * default constructor
     */
    public GSOutVO()
    {
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
	 * @return the itemVOList
	 */
	public List<GSOutItemVO> getItemVOList()
	{
		return itemVOList;
	}

	/**
	 * @param itemVOList the itemVOList to set
	 */
	public void setItemVOList(List<GSOutItemVO> itemVOList)
	{
		this.itemVOList = itemVOList;
	}

	/**
     * @return the otherId
     */
    public String getOtherId()
    {
        return otherId;
    }

    /**
     * @param otherId
     *            the otherId to set
     */
    public void setOtherId(String otherId)
    {
        this.otherId = otherId;
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
            .append("GSOutVO ( ")
            .append(super.toString())
            .append(TAB)
            .append("stafferName = ")
            .append(this.stafferName)
            .append(TAB)
            .append("otherId = ")
            .append(this.otherId)
            .append(TAB)
            .append("itemVOList = ")
            .append(this.itemVOList)
            .append(TAB)
            .append(" )");

        return retValue.toString();
    }

}
