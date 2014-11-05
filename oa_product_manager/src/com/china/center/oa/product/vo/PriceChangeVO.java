/**
 * File Name: PriceChangeVO.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2010-10-4<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.product.vo;


import java.util.List;

import com.china.center.jdbc.annotation.Entity;
import com.china.center.jdbc.annotation.Ignore;
import com.china.center.jdbc.annotation.Relationship;
import com.china.center.oa.product.bean.PriceChangeBean;


/**
 * PriceChangeVO
 * 
 * @author ZHUZHU
 * @version 2010-10-4
 * @see PriceChangeVO
 * @since 1.0
 */
@Entity(inherit = true)
public class PriceChangeVO extends PriceChangeBean
{
    @Relationship(relationField = "stafferId")
    private String stafferName = "";

    @Ignore
    private String otherId = "";

    @Ignore
    private List<PriceChangeSrcItemVO> srcVOList = null;

    @Ignore
    private List<PriceChangeNewItemVO> newVOList = null;

    /**
     * default constructor
     */
    public PriceChangeVO()
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
     * @return the srcVOList
     */
    public List<PriceChangeSrcItemVO> getSrcVOList()
    {
        return srcVOList;
    }

    /**
     * @param srcVOList
     *            the srcVOList to set
     */
    public void setSrcVOList(List<PriceChangeSrcItemVO> srcVOList)
    {
        this.srcVOList = srcVOList;
    }

    /**
     * @return the newVOList
     */
    public List<PriceChangeNewItemVO> getNewVOList()
    {
        return newVOList;
    }

    /**
     * @param newVOList
     *            the newVOList to set
     */
    public void setNewVOList(List<PriceChangeNewItemVO> newVOList)
    {
        this.newVOList = newVOList;
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
            .append("PriceChangeVO ( ")
            .append(super.toString())
            .append(TAB)
            .append("stafferName = ")
            .append(this.stafferName)
            .append(TAB)
            .append("otherId = ")
            .append(this.otherId)
            .append(TAB)
            .append("srcVOList = ")
            .append(this.srcVOList)
            .append(TAB)
            .append("newVOList = ")
            .append(this.newVOList)
            .append(TAB)
            .append(" )");

        return retValue.toString();
    }

}
