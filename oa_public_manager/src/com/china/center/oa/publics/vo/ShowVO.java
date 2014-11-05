/**
 * File Name: ShowVO.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2010-12-13<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.publics.vo;


import com.china.center.jdbc.annotation.Entity;
import com.china.center.jdbc.annotation.Relationship;
import com.china.center.oa.publics.bean.ShowBean;


/**
 * ShowVO
 * 
 * @author ZHUZHU
 * @version 2010-12-13
 * @see ShowVO
 * @since 3.0
 */
@Entity(inherit = true)
public class ShowVO extends ShowBean
{
    @Relationship(relationField = "dutyId")
    private String dutyName = "";

    /**
     * default constructor
     */
    public ShowVO()
    {
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

}
