package com.china.center.oa.client.bean;

import com.china.center.jdbc.annotation.Entity;
import com.china.center.jdbc.annotation.Html;
import com.china.center.jdbc.annotation.JCheck;
import com.china.center.jdbc.annotation.enums.Element;
import com.china.center.oa.customer.constant.CustomerConstant;
import com.china.center.oa.publics.constant.PublicConstant;

@SuppressWarnings("serial")
@Entity(inherit = true)
public abstract class AbstractCustomerIndividualBean extends AbstractCustomerBean
{
	@Html(title = "性别", type = Element.SELECT, must = true)
	private int sex = PublicConstant.SEX_MAN;

	/**
	 * 1.个人  2.企业 3.部门
	 */
	@Html(title = "客户性质", must = true, type = Element.SELECT)
	private int type = CustomerConstant.NATURE_INDIVIDUAL;
	
	/**
	 * 年龄段
	 */
	@Html(title = "年龄段", type = Element.SELECT)
	private int personal = 0;

	@Html(title = "年龄", type = Element.NUMBER)
	private int age = 0;

	@Html(title = "生日", maxLength = 20, type = Element.DATE)
	private String birthday = "";

	@Html(title = "手机",tip = "只能填写数字", oncheck = JCheck.ONLY_NUMBER, maxLength = 11, must = true)
	private String handphone = "";

	@Html(title = "固话",tip = "只能填写数字", maxLength = 13)
	private String tel = "";
	
	@Html(title = "电子邮箱", maxLength = 100)
	private String email = "";

	@Html(title = "QQ号", tip = "只能填写数字", maxLength = 100)
	private String qq = "";

	@Html(title = "微博", maxLength = 100)
	private String weibo = "";

	@Html(title = "微信", maxLength = 100)
	private String weixin = "";
	
	/**
	 * 
	 */
	@Html(title = "职务", type = Element.SELECT)
	private int duty = 0;

	@Html(title = "汇报对象", maxLength = 100)
	private String reportTo = "";

	@Html(title = "爱好", maxLength = 100)
	private String interest = "";

	/**
	 * A\B\C\D\E
	 */
	@Html(title = "关系程度", type = Element.SELECT)
	private int relationship = -1;

	@Html(title = "备注", type = Element.TEXTAREA, maxLength = 200)
	private String description = "";

	@Html(title = "预留1")
	private String reserve1 = "";

	@Html(title = "预留2")
	private String reserve2 = "";

	@Html(title = "预留3")
	private String reserve3 = "";

	@Html(title = "预留4")
	private String reserve4 = "";

	@Html(title = "预留5")
	private String reserve5 = "";

	@Html(title = "预留6")
	private String reserve6 = "";

	@Html(title = "预留7")
	private String reserve7 = "";

	@Html(title = "预留8")
	private String reserve8 = "";

	@Html(title = "预留9")
	private String reserve9 = "";

	@Html(title = "预留10")
	private String reserve10 = "";

	public AbstractCustomerIndividualBean()
	{
		
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

	/**
	 * @return the duty
	 */
	public int getDuty()
	{
		return duty;
	}

	/**
	 * @param duty the duty to set
	 */
	public void setDuty(int duty)
	{
		this.duty = duty;
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

	/**
	 * @return the type
	 */
	public int getType()
	{
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(int type)
	{
		this.type = type;
	}
}
