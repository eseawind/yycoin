/**
 * File Name: StafferVO.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * Creater: zhuAchen<br>
 * CreateTime: 2008-11-4<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.publics.vo;


import com.china.center.jdbc.annotation.Entity;
import com.china.center.jdbc.annotation.Ignore;
import com.china.center.jdbc.annotation.Relationship;
import com.china.center.oa.publics.bean.StafferBean;


/**
 * StafferVO
 * 
 * @author ZHUZHU
 * @version 2008-11-4
 * @see StafferVO
 * @since 1.0
 */
@Entity(inherit = true)
public class StafferVO extends StafferBean
{
    @Relationship(relationField = "departmentId", tagField = "name")
    private String departmentName = "";

    @Relationship(relationField = "postId", tagField = "name")
    private String postName = "";

    @Relationship(relationField = "principalshipId", tagField = "name")
    private String principalshipName = "";

    @Relationship(relationField = "industryId", tagField = "name")
    private String industryName = "";

    @Relationship(relationField = "industryId2")
    private String industryName2 = "";

    @Relationship(relationField = "industryId3")
    private String industryName3 = "";

    /**
     * 部门全名
     */
    @Ignore
    private String departmentFullName = "";

    @Ignore
    private String enc = "";

    /**
     * default constructor
     */
    public StafferVO()
    {
    }


    /**
     * @return the departmentName
     */
    public String getDepartmentName()
    {
        return departmentName;
    }

    /**
     * @param departmentName
     *            the departmentName to set
     */
    public void setDepartmentName(String departmentName)
    {
        this.departmentName = departmentName;
    }

    /**
     * @return the postName
     */
    public String getPostName()
    {
        return postName;
    }

    /**
     * @param postName
     *            the postName to set
     */
    public void setPostName(String postName)
    {
        this.postName = postName;
    }

    /**
     * @return the principalshipName
     */
    public String getPrincipalshipName()
    {
        return principalshipName;
    }

    /**
     * @param principalshipName
     *            the principalshipName to set
     */
    public void setPrincipalshipName(String principalshipName)
    {
        this.principalshipName = principalshipName;
    }

    /**
     * @return the enc
     */
    public String getEnc()
    {
        return enc;
    }

    /**
     * @param enc
     *            the enc to set
     */
    public void setEnc(String enc)
    {
        this.enc = enc;
    }

    /**
     * @return the departmentFullName
     */
    public String getDepartmentFullName()
    {
        return departmentFullName;
    }

    /**
     * @param departmentFullName
     *            the departmentFullName to set
     */
    public void setDepartmentFullName(String departmentFullName)
    {
        this.departmentFullName = departmentFullName;
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

    /**
     * @return the industryName2
     */
    public String getIndustryName2()
    {
        return industryName2;
    }

    /**
     * @param industryName2
     *            the industryName2 to set
     */
    public void setIndustryName2(String industryName2)
    {
        this.industryName2 = industryName2;
    }

    /**
     * @return the industryName3
     */
    public String getIndustryName3()
    {
        return industryName3;
    }

    /**
     * @param industryName3
     *            the industryName3 to set
     */
    public void setIndustryName3(String industryName3)
    {
        this.industryName3 = industryName3;
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
            .append("StafferVO ( ")
            .append(super.toString())
            .append(TAB)
            .append("departmentName = ")
            .append(this.departmentName)
            .append(TAB)
            .append("postName = ")
            .append(this.postName)
            .append(TAB)
            .append("principalshipName = ")
            .append(this.principalshipName)
            .append(TAB)
            .append("industryName = ")
            .append(this.industryName)
            .append(TAB)
            .append("industryName2 = ")
            .append(this.industryName2)
            .append(TAB)
            .append("industryName3 = ")
            .append(this.industryName3)
            .append(TAB)
            .append("departmentFullName = ")
            .append(this.departmentFullName)
            .append(TAB)
            .append("enc = ")
            .append(this.enc)
            .append(TAB)
            .append(" )");

        return retValue.toString();
    }
}
