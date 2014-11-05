/**
 *
 */
package com.china.center.oa.stock.vo;


import java.util.List;

import com.china.center.jdbc.annotation.Entity;
import com.china.center.jdbc.annotation.Ignore;
import com.china.center.jdbc.annotation.Relationship;
import com.china.center.oa.stock.bean.StockBean;
import com.china.center.oa.stock.constant.StockConstant;


/**
 * @author Administrator
 */
@Entity(inherit = true)
public class StockVO extends StockBean
{
    @Relationship(relationField = "stafferId")
    private String userName = "";

    @Relationship(relationField = "owerId")
    private String owerName = "";

    @Relationship(relationField = "locationId")
    private String locationName = "";

    @Relationship(relationField = "industryId")
    private String industryName = "";

    @Ignore
    private String dutyName = "";

    /**
     * 是否被操作
     */
    @Ignore
    private int display = 0;

    @Ignore
    private int overTime = StockConstant.STOCK_OVERTIME_NO;

    @Ignore
    private List<StockItemVO> itemVO = null;

    /**
     * @return the userName
     */
    public String getUserName()
    {
        return userName;
    }

    /**
     * @param userName
     *            the userName to set
     */
    public void setUserName(String userName)
    {
        this.userName = userName;
    }

    /**
     * @return the locationName
     */
    public String getLocationName()
    {
        return locationName;
    }

    /**
     * @param locationName
     *            the locationName to set
     */
    public void setLocationName(String locationName)
    {
        this.locationName = locationName;
    }

    /**
     * @return the itemVO
     */
    public List<StockItemVO> getItemVO()
    {
        return itemVO;
    }

    /**
     * @param itemVO
     *            the itemVO to set
     */
    public void setItemVO(List<StockItemVO> itemVO)
    {
        this.itemVO = itemVO;
    }

    /**
     * @return the display
     */
    public int getDisplay()
    {
        return display;
    }

    /**
     * @param display
     *            the display to set
     */
    public void setDisplay(int display)
    {
        this.display = display;
    }

    /**
     * @return the overTime
     */
    public int getOverTime()
    {
        return overTime;
    }

    /**
     * @param overTime
     *            the overTime to set
     */
    public void setOverTime(int overTime)
    {
        this.overTime = overTime;
    }

    /**
     * @return the owerName
     */
    public String getOwerName()
    {
        return owerName;
    }

    /**
     * @param owerName
     *            the owerName to set
     */
    public void setOwerName(String owerName)
    {
        this.owerName = owerName;
    }

    /**
     * @return the dutyName
     */
    public String getDutyName()
    {
        return dutyName;
    }

    /**
     * @param dutyName
     *            the dutyName to set
     */
    public void setDutyName(String dutyName)
    {
        this.dutyName = dutyName;
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
            .append("StockVO ( ")
            .append(super.toString())
            .append(TAB)
            .append("userName = ")
            .append(this.userName)
            .append(TAB)
            .append("owerName = ")
            .append(this.owerName)
            .append(TAB)
            .append("locationName = ")
            .append(this.locationName)
            .append(TAB)
            .append("dutyName = ")
            .append(this.dutyName)
            .append(TAB)
            .append("display = ")
            .append(this.display)
            .append(TAB)
            .append("overTime = ")
            .append(this.overTime)
            .append(TAB)
            .append("itemVO = ")
            .append(this.itemVO)
            .append(TAB)
            .append(" )");

        return retValue.toString();
    }

    /**
     * @return the industryName
     */
    public String getIndustryName()
    {
        return industryName;
    }

    /**
     * @param industryName
     *            the industryName to set
     */
    public void setIndustryName(String industryName)
    {
        this.industryName = industryName;
    }
}
