package com.china.center.oa.client.bean;

import com.china.center.jdbc.annotation.Entity;
import com.china.center.jdbc.annotation.Html;
import com.china.center.jdbc.annotation.JCheck;
import com.china.center.jdbc.annotation.enums.Element;
import com.china.center.oa.customer.constant.CustomerConstant;

@SuppressWarnings("serial")
@Entity(inherit = true)
public abstract class AbstractCustomerDepartBean extends AbstractCustomerBean
{
	/**
	 * 1.个人  2.企业 3.部门
	 */
	@Html(title = "客户性质", must = true, type = Element.SELECT)
	private int type = CustomerConstant.NATURE_DEPART;
	
	/**
	 * 人数
	 */
	@Html(title = "部门人数", type = Element.NUMBER, must = true, oncheck = JCheck.ONLY_NUMBER)
	private int people = 0;
	
	/**
	 * 302
	 */
	@Html(title = "部门性质", type = Element.SELECT)
	private int departType = 0;
	
	@Html(title = "备注", maxLength = 300, type = Element.TEXTAREA)
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

	public AbstractCustomerDepartBean()
	{
	}

	public int getPeople()
	{
		return people;
	}

	public void setPeople(int people)
	{
		this.people = people;
	}

	public int getDepartType()
	{
		return departType;
	}

	public void setDepartType(int departType)
	{
		this.departType = departType;
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
