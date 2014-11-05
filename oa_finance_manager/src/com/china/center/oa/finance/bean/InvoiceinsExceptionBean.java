package com.china.center.oa.finance.bean;

import java.io.Serializable;

import com.china.center.jdbc.annotation.Entity;
import com.china.center.jdbc.annotation.FK;
import com.china.center.jdbc.annotation.Id;
import com.china.center.jdbc.annotation.Table;

@Entity
@Table(name = "t_center_invoiceins_exception")
public class InvoiceinsExceptionBean implements Serializable
{
	@Id
	private String id = "";
	
	@FK
	private String parentId = "";
	
	/**
	 * 开票时异常
	 */
	private String exception = "";

	public String getId()
	{
		return id;
	}

	public void setId(String id)
	{
		this.id = id;
	}

	public String getParentId()
	{
		return parentId;
	}

	public void setParentId(String parentId)
	{
		this.parentId = parentId;
	}

	public String getException()
	{
		return exception;
	}

	public void setException(String exception)
	{
		this.exception = exception;
	}
		
	
}
