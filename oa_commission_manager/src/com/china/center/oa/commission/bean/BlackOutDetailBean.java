package com.china.center.oa.commission.bean;

import com.china.center.jdbc.annotation.Entity;
import com.china.center.jdbc.annotation.Table;

@SuppressWarnings("serial")
@Entity(inherit = true)
@Table(name = "T_CENTER_BLACK_OUT_DETAIL")
public class BlackOutDetailBean extends AbstractBlackOutDetailBean
{
	/**
	 * 
	 */
	public BlackOutDetailBean()
	{
	}

}
