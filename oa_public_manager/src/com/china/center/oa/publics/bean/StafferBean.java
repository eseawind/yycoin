/**
 * File Name: StafferBean.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * Creater: zhuAchen<br>
 * CreateTime: 2008-11-2<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.publics.bean;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.china.center.jdbc.annotation.Entity;
import com.china.center.jdbc.annotation.Html;
import com.china.center.jdbc.annotation.Id;
import com.china.center.jdbc.annotation.Ignore;
import com.china.center.jdbc.annotation.JCheck;
import com.china.center.jdbc.annotation.Join;
import com.china.center.jdbc.annotation.Table;
import com.china.center.jdbc.annotation.Unique;
import com.china.center.jdbc.annotation.enums.Element;
import com.china.center.jdbc.annotation.enums.JoinType;
import com.china.center.oa.publics.constant.PublicConstant;
import com.china.center.oa.publics.constant.StafferConstant;
import com.china.center.oa.publics.vs.StafferVSPriBean;


/**
 * StafferBean
 * 
 * @author ZHUZHU
 * @version 2008-11-2
 * @see StafferBean
 * @since 1.0
 */
@Entity
@Table(name = "T_CENTER_OASTAFFER")
public class StafferBean implements Serializable
{
    @Id
    private String id = "";

    @Unique
    @Html(title = "姓名", must = true, maxLength = 100)
    private String name = "";

    @Html(title = "工号", must = true, maxLength = 20, oncheck = JCheck.ONLY_NUMBER_OR_LETTER)
    private String code = "";

    @Html(title = "工作类型", must = true, type = Element.SELECT)
    private int examType = StafferConstant.EXAMTYPE_OTHER;

    /**
     * status
     */
    @Html(title = "状态", must = true, type = Element.SELECT)
    private int status = StafferConstant.STATUS_COMMON;

    @Html(title = "属性类型", must = true, type = Element.SELECT)
    private int otype = 0;

    /**
     * 信用杠杆倍数
     */
    @Html(title = "信用杠杆倍数")
    private int lever = StafferConstant.LEVER_DEFAULT;

    /**
     * 信用属性
     */
    @Html(title = "信用属性", must = true, type = Element.SELECT)
    private int black = StafferConstant.BLACK_NO;

    @Html(title = "身份证名", must = true)
    private String locationId = "";

    /**
     * 四级组织
     */
    @Join(tagClass = PrincipalshipBean.class, type = JoinType.LEFT, alias = "INDUSTRY")
    private String industryId = "";

    /**
     * 五级组织
     */
    @Join(tagClass = PrincipalshipBean.class, type = JoinType.LEFT, alias = "INDUSTRY2")
    private String industryId2 = "";

    /**
     * 六级组织
     */
    @Join(tagClass = PrincipalshipBean.class, type = JoinType.LEFT, alias = "INDUSTRY3")
    private String industryId3 = "";

    @Html(title = "性别", must = true, type = Element.SELECT)
    private int sex = PublicConstant.SEX_MAN;

    @Html(title = "毕业时间", type = Element.DATE)
    private String graduateDate = "";

    @Html(title = "专业", maxLength = 100)
    private String specialty = "";

    @Html(title = "学历", maxLength = 100)
    private String graduate = "";

    @Html(title = "毕业院校", maxLength = 100)
    private String graduateSchool = "";

    @Html(title = "部门", type = Element.SELECT)
    @Join(tagClass = DepartmentBean.class, type = JoinType.LEFT)
    private String departmentId = "";

    @Html(title = "职员属性", type = Element.SELECT, must = true)
    @Join(tagClass = PostBean.class, type = JoinType.LEFT)
    private String postId = "";

    /**
     * 真正的组织结构位置
     */
    @Html(title = "组织岗位", type = Element.INPUT, name = "principalshipName", must = true)
    @Join(tagClass = PrincipalshipBean.class, type = JoinType.LEFT)
    private String principalshipId = "-1";

    @Html(title = "邮箱", maxLength = 20)
    private String nation = "";

    @Html(title = "籍贯", maxLength = 20)
    private String city = "";

    @Html(title = "地址", maxLength = 100)
    private String address = "";

    @Html(title = "政治面貌", maxLength = 20)
    private String visage = "";

    @Html(title = "身份证", maxLength = 50, oncheck = JCheck.ONLY_NUMBER_OR_LETTER)
    private String idCard = "";

    @Html(title = "离职日期", type = Element.DATE)
    private String birthday = "";

    @Html(title = "移动电话", oncheck = JCheck.ONLY_NUMBER, maxLength = 20)
    private String handphone = "";

    @Html(title = "分机号码", oncheck = JCheck.ONLY_NUMBER, maxLength = 20)
    private String subphone = "";

    private String picPath = "";

    private String idiograph = "";

    private String logTime = "";

    /**
     * key
     */
    private String pwkey = "";

    /**
     * 信用额度
     */
    @Html(title = "个人信用额度", must = true, oncheck = JCheck.ONLY_FLOAT, maxLength = 20)
    private double credit = 0.0d;

    @Html(title = "其他", type = Element.TEXTAREA, maxLength = 200)
    private String description = "";

    @Ignore
    private List<StafferVSPriBean> priList = new ArrayList();

    /**
     * default constructor
     */
    public StafferBean()
    {
    }

    public boolean equals(Object obj)
    {
        if (obj == null)
        {
            return false;
        }

        if (this == obj)
        {
            return true;
        }

        if (obj instanceof StafferBean)
        {
            StafferBean oo = (StafferBean)obj;

            return this.id.equals(oo.getId());
        }

        return false;
    }

    public int hashCode()
    {
        return this.id.hashCode();
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
     * @return the locationId
     */
    public String getLocationId()
    {
        return locationId;
    }

    /**
     * @param locationId
     *            the locationId to set
     */
    public void setLocationId(String locationId)
    {
        this.locationId = locationId;
    }

    /**
     * @return the sex
     */
    public int getSex()
    {
        return sex;
    }

    /**
     * @param sex
     *            the sex to set
     */
    public void setSex(int sex)
    {
        this.sex = sex;
    }

    /**
     * @return the graduateDate
     */
    public String getGraduateDate()
    {
        return graduateDate;
    }

    /**
     * @param graduateDate
     *            the graduateDate to set
     */
    public void setGraduateDate(String graduateDate)
    {
        this.graduateDate = graduateDate;
    }

    /**
     * @return the specialty
     */
    public String getSpecialty()
    {
        return specialty;
    }

    /**
     * @param specialty
     *            the specialty to set
     */
    public void setSpecialty(String specialty)
    {
        this.specialty = specialty;
    }

    /**
     * @return the graduate
     */
    public String getGraduate()
    {
        return graduate;
    }

    /**
     * @param graduate
     *            the graduate to set
     */
    public void setGraduate(String graduate)
    {
        this.graduate = graduate;
    }

    /**
     * @return the graduateSchool
     */
    public String getGraduateSchool()
    {
        return graduateSchool;
    }

    /**
     * @param graduateSchool
     *            the graduateSchool to set
     */
    public void setGraduateSchool(String graduateSchool)
    {
        this.graduateSchool = graduateSchool;
    }

    /**
     * @return the departmentId
     */
    public String getDepartmentId()
    {
        return departmentId;
    }

    /**
     * @param departmentId
     *            the departmentId to set
     */
    public void setDepartmentId(String departmentId)
    {
        this.departmentId = departmentId;
    }

    /**
     * @return the postId
     */
    public String getPostId()
    {
        return postId;
    }

    /**
     * @param postId
     *            the postId to set
     */
    public void setPostId(String postId)
    {
        this.postId = postId;
    }

    /**
     * @return the principalshipId
     */
    public String getPrincipalshipId()
    {
        return principalshipId;
    }

    /**
     * @param principalshipId
     *            the principalshipId to set
     */
    public void setPrincipalshipId(String principalshipId)
    {
        this.principalshipId = principalshipId;
    }

    /**
     * @return the nation
     */
    public String getNation()
    {
        return nation;
    }

    /**
     * @param nation
     *            the nation to set
     */
    public void setNation(String nation)
    {
        this.nation = nation;
    }

    /**
     * @return the city
     */
    public String getCity()
    {
        return city;
    }

    /**
     * @param city
     *            the city to set
     */
    public void setCity(String city)
    {
        this.city = city;
    }

    /**
     * @return the address
     */
    public String getAddress()
    {
        return address;
    }

    /**
     * @param address
     *            the address to set
     */
    public void setAddress(String address)
    {
        this.address = address;
    }

    /**
     * @return the visage
     */
    public String getVisage()
    {
        return visage;
    }

    /**
     * @param visage
     *            the visage to set
     */
    public void setVisage(String visage)
    {
        this.visage = visage;
    }

    /**
     * @return the idCard
     */
    public String getIdCard()
    {
        return idCard;
    }

    /**
     * @param idCard
     *            the idCard to set
     */
    public void setIdCard(String idCard)
    {
        this.idCard = idCard;
    }

    /**
     * @return the birthday
     */
    public String getBirthday()
    {
        return birthday;
    }

    /**
     * @param birthday
     *            the birthday to set
     */
    public void setBirthday(String birthday)
    {
        this.birthday = birthday;
    }

    /**
     * @return the handphone
     */
    public String getHandphone()
    {
        return handphone;
    }

    /**
     * @param handphone
     *            the handphone to set
     */
    public void setHandphone(String handphone)
    {
        this.handphone = handphone;
    }

    /**
     * @return the subphone
     */
    public String getSubphone()
    {
        return subphone;
    }

    /**
     * @param subphone
     *            the subphone to set
     */
    public void setSubphone(String subphone)
    {
        this.subphone = subphone;
    }

    /**
     * @return the picPath
     */
    public String getPicPath()
    {
        return picPath;
    }

    /**
     * @param picPath
     *            the picPath to set
     */
    public void setPicPath(String picPath)
    {
        this.picPath = picPath;
    }

    /**
     * @return the idiograph
     */
    public String getIdiograph()
    {
        return idiograph;
    }

    /**
     * @param idiograph
     *            the idiograph to set
     */
    public void setIdiograph(String idiograph)
    {
        this.idiograph = idiograph;
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
     * @return the examType
     */
    public int getExamType()
    {
        return examType;
    }

    /**
     * @param examType
     *            the examType to set
     */
    public void setExamType(int examType)
    {
        this.examType = examType;
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
     * @return the pwkey
     */
    public String getPwkey()
    {
        return pwkey;
    }

    /**
     * @param pwkey
     *            the pwkey to set
     */
    public void setPwkey(String pwkey)
    {
        this.pwkey = pwkey;
    }

    /**
     * @return the credit
     */
    public double getCredit()
    {
        return credit;
    }

    /**
     * @param credit
     *            the credit to set
     */
    public void setCredit(double credit)
    {
        this.credit = credit;
    }

    /**
     * @return the priList
     */
    public List<StafferVSPriBean> getPriList()
    {
        return priList;
    }

    /**
     * @param priList
     *            the priList to set
     */
    public void setPriList(List<StafferVSPriBean> priList)
    {
        this.priList = priList;
    }

    /**
     * @return the lever
     */
    public int getLever()
    {
        return lever;
    }

    /**
     * @param lever
     *            the lever to set
     */
    public void setLever(int lever)
    {
        this.lever = lever;
    }

    /**
     * @return the black
     */
    public int getBlack()
    {
        return black;
    }

    /**
     * @param black
     *            the black to set
     */
    public void setBlack(int black)
    {
        this.black = black;
    }

    /**
     * @return the industryId
     */
    public String getIndustryId()
    {
        return industryId;
    }

    /**
     * @param industryId
     *            the industryId to set
     */
    public void setIndustryId(String industryId)
    {
        this.industryId = industryId;
    }

    /**
     * @return the industryId2
     */
    public String getIndustryId2()
    {
        return industryId2;
    }

    /**
     * @param industryId2
     *            the industryId2 to set
     */
    public void setIndustryId2(String industryId2)
    {
        this.industryId2 = industryId2;
    }

    /**
     * @return the industryId3
     */
    public String getIndustryId3()
    {
        return industryId3;
    }

    /**
     * @param industryId3
     *            the industryId3 to set
     */
    public void setIndustryId3(String industryId3)
    {
        this.industryId3 = industryId3;
    }

    /**
     * @return the otype
     */
    public int getOtype()
    {
        return otype;
    }

    /**
     * @param otype
     *            the otype to set
     */
    public void setOtype(int otype)
    {
        this.otype = otype;
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
            .append("StafferBean ( ")
            .append(super.toString())
            .append(TAB)
            .append("id = ")
            .append(this.id)
            .append(TAB)
            .append("name = ")
            .append(this.name)
            .append(TAB)
            .append("code = ")
            .append(this.code)
            .append(TAB)
            .append("examType = ")
            .append(this.examType)
            .append(TAB)
            .append("status = ")
            .append(this.status)
            .append(TAB)
            .append("otype = ")
            .append(this.otype)
            .append(TAB)
            .append("lever = ")
            .append(this.lever)
            .append(TAB)
            .append("black = ")
            .append(this.black)
            .append(TAB)
            .append("locationId = ")
            .append(this.locationId)
            .append(TAB)
            .append("industryId = ")
            .append(this.industryId)
            .append(TAB)
            .append("industryId2 = ")
            .append(this.industryId2)
            .append(TAB)
            .append("industryId3 = ")
            .append(this.industryId3)
            .append(TAB)
            .append("sex = ")
            .append(this.sex)
            .append(TAB)
            .append("graduateDate = ")
            .append(this.graduateDate)
            .append(TAB)
            .append("specialty = ")
            .append(this.specialty)
            .append(TAB)
            .append("graduate = ")
            .append(this.graduate)
            .append(TAB)
            .append("graduateSchool = ")
            .append(this.graduateSchool)
            .append(TAB)
            .append("departmentId = ")
            .append(this.departmentId)
            .append(TAB)
            .append("postId = ")
            .append(this.postId)
            .append(TAB)
            .append("principalshipId = ")
            .append(this.principalshipId)
            .append(TAB)
            .append("nation = ")
            .append(this.nation)
            .append(TAB)
            .append("city = ")
            .append(this.city)
            .append(TAB)
            .append("address = ")
            .append(this.address)
            .append(TAB)
            .append("visage = ")
            .append(this.visage)
            .append(TAB)
            .append("idCard = ")
            .append(this.idCard)
            .append(TAB)
            .append("birthday = ")
            .append(this.birthday)
            .append(TAB)
            .append("handphone = ")
            .append(this.handphone)
            .append(TAB)
            .append("subphone = ")
            .append(this.subphone)
            .append(TAB)
            .append("picPath = ")
            .append(this.picPath)
            .append(TAB)
            .append("idiograph = ")
            .append(this.idiograph)
            .append(TAB)
            .append("logTime = ")
            .append(this.logTime)
            .append(TAB)
            .append("pwkey = ")
            .append(this.pwkey)
            .append(TAB)
            .append("credit = ")
            .append(this.credit)
            .append(TAB)
            .append("description = ")
            .append(this.description)
            .append(TAB)
            .append("priList = ")
            .append(this.priList)
            .append(TAB)
            .append(" )");

        return retValue.toString();
    }

}
