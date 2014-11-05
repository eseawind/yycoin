/**
 * File Name: CustomerBean.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * Creater: zhuAchen<br>
 * CreateTime: 2008-11-9<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.client.bean;

import java.io.Serializable;

import com.china.center.jdbc.annotation.Html;
import com.china.center.jdbc.annotation.Unique;
import com.china.center.jdbc.annotation.enums.Element;
import com.china.center.oa.publics.constant.PublicConstant;

@SuppressWarnings("serial")
public abstract class AbstractCustomerContactBean implements Serializable 
{
	@Html(title = "联系人", must = true, maxLength = 100)
	protected String name = "";
	
	@Html(title = "性别", type = Element.SELECT, must = true)
	protected int sex = PublicConstant.SEX_MAN;

	/**
	 * 年龄段
	 */
	@Html(title = "年龄段", type = Element.SELECT)
	protected int personal = 0;

	@Html(title = "年龄", type = Element.NUMBER)
	protected int age = 0;

	@Html(title = "生日", maxLength = 20, type = Element.DATE)
	protected String birthday = "";

	@Unique
	@Html(title = "手机", maxLength = 11)
	protected String handphone = "";

	@Html(title = "固话", maxLength = 13)
	protected String tel = "";
	
	@Html(title = "电子邮箱", maxLength = 100)
	protected String email = "";

	@Html(title = "QQ号", maxLength = 100)
	protected String qq = "";

	@Html(title = "微博", maxLength = 100)
	protected String weibo = "";

	@Html(title = "微信", maxLength = 100)
	protected String weixin = "";
	
	@Html(title = "职务", type = Element.SELECT)
	protected int duty = 0;

	@Html(title = "汇报对象", maxLength = 100)
	protected String reportTo = "";

	@Html(title = "爱好", maxLength = 100)
	protected String interest = "";

	/**
	 * A\B\C\D\E
	 */
	@Html(title = "关系程度", type = Element.SELECT)
	protected int relationship = -1;

	@Html(title = "联系次数", type = Element.NUMBER)
	protected int contactTimes = 0;

	@Html(title = "最近联系时间")
	protected String lastContactTime = "";
	
	@Html(title = "角色", type = Element.SELECT)
	protected int role = 0;

	/**
	 * 0 有效；1 无效（逻辑删除）
	 */
	protected int valid = 0;
	
	@Html(title = "备注", maxLength = 500, type = Element.TEXTAREA)
	protected String description = "";

	@Html(title = "预留1")
	protected String reserve1 = "";

	@Html(title = "预留2")
	protected String reserve2 = "";

	@Html(title = "预留3")
	protected String reserve3 = "";

	@Html(title = "预留4")
	protected String reserve4 = "";

	@Html(title = "预留5")
	protected String reserve5 = "";

	@Html(title = "预留6")
	protected String reserve6 = "";

	@Html(title = "预留7")
	protected String reserve7 = "";

	@Html(title = "预留8")
	protected String reserve8 = "";

	@Html(title = "预留9")
	protected String reserve9 = "";

	@Html(title = "预留10")
	protected String reserve10 = "";
	
	public AbstractCustomerContactBean() {
	}

	public int getSex()
	{
		return sex;
	}

	public void setSex(int sex)
	{
		this.sex = sex;
	}

	public int getPersonal()
	{
		return personal;
	}

	public void setPersonal(int personal)
	{
		this.personal = personal;
	}

	public int getAge()
	{
		return age;
	}

	public void setAge(int age)
	{
		this.age = age;
	}

	public String getBirthday()
	{
		return birthday;
	}

	public void setBirthday(String birthday)
	{
		this.birthday = birthday;
	}

	public String getHandphone()
	{
		return handphone;
	}

	public void setHandphone(String handphone)
	{
		this.handphone = handphone;
	}

	public String getTel()
	{
		return tel;
	}

	public void setTel(String tel)
	{
		this.tel = tel;
	}

	public String getEmail()
	{
		return email;
	}

	public void setEmail(String email)
	{
		this.email = email;
	}

	public String getQq()
	{
		return qq;
	}

	public void setQq(String qq)
	{
		this.qq = qq;
	}

	public String getWeibo()
	{
		return weibo;
	}

	public void setWeibo(String weibo)
	{
		this.weibo = weibo;
	}

	public String getWeixin()
	{
		return weixin;
	}

	public void setWeixin(String weixin)
	{
		this.weixin = weixin;
	}

	public String getReportTo()
	{
		return reportTo;
	}

	public void setReportTo(String reportTo)
	{
		this.reportTo = reportTo;
	}

	public String getInterest()
	{
		return interest;
	}

	public void setInterest(String interest)
	{
		this.interest = interest;
	}

	public int getRelationship() {
		return relationship;
	}

	public void setRelationship(int relationship) {
		this.relationship = relationship;
	}

	public int getContactTimes()
	{
		return contactTimes;
	}

	public void setContactTimes(int contactTimes)
	{
		this.contactTimes = contactTimes;
	}

	public String getLastContactTime()
	{
		return lastContactTime;
	}

	public void setLastContactTime(String lastContactTime)
	{
		this.lastContactTime = lastContactTime;
	}

	public String getDescription()
	{
		return description;
	}

	public void setDescription(String description)
	{
		this.description = description;
	}

	public String getReserve1()
	{
		return reserve1;
	}

	public void setReserve1(String reserve1)
	{
		this.reserve1 = reserve1;
	}

	public String getReserve2()
	{
		return reserve2;
	}

	public void setReserve2(String reserve2)
	{
		this.reserve2 = reserve2;
	}

	public String getReserve3()
	{
		return reserve3;
	}

	public void setReserve3(String reserve3)
	{
		this.reserve3 = reserve3;
	}

	public String getReserve4()
	{
		return reserve4;
	}

	public void setReserve4(String reserve4)
	{
		this.reserve4 = reserve4;
	}

	public String getReserve5()
	{
		return reserve5;
	}

	public void setReserve5(String reserve5)
	{
		this.reserve5 = reserve5;
	}

	public String getReserve6()
	{
		return reserve6;
	}

	public void setReserve6(String reserve6)
	{
		this.reserve6 = reserve6;
	}

	public String getReserve7()
	{
		return reserve7;
	}

	public void setReserve7(String reserve7)
	{
		this.reserve7 = reserve7;
	}

	public String getReserve8()
	{
		return reserve8;
	}

	public void setReserve8(String reserve8)
	{
		this.reserve8 = reserve8;
	}

	public String getReserve9()
	{
		return reserve9;
	}

	public void setReserve9(String reserve9)
	{
		this.reserve9 = reserve9;
	}

	public String getReserve10()
	{
		return reserve10;
	}

	public void setReserve10(String reserve10)
	{
		this.reserve10 = reserve10;
	}

	public int getValid()
	{
		return valid;
	}

	public void setValid(int valid)
	{
		this.valid = valid;
	}

	/**
	 * @return the name
	 */
	public String getName()
	{
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name)
	{
		this.name = name;
	}

	/**
	 * @return the role
	 */
	public int getRole()
	{
		return role;
	}

	/**
	 * @param role the role to set
	 */
	public void setRole(int role)
	{
		this.role = role;
	}

	/**
	 * @param duty the duty to set
	 */
	public void setDuty(int duty)
	{
		this.duty = duty;
	}

	/**
	 * @return the duty
	 */
	public int getDuty()
	{
		return duty;
	}

	public String toString() {
		return "";
	}

}