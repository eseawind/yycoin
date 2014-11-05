package com.china.center.oa.commission.bean;

import com.china.center.jdbc.annotation.Entity;
import com.china.center.jdbc.annotation.Table;

@SuppressWarnings("serial")
@Entity(inherit = true)
@Table(name = "T_CENTER_BLACK_OUT_DETAIL_H")
public class HisBlackOutDetailBean extends AbstractBlackOutDetailBean
{
	private String backupDate = "";
	
	/**
	 * 
	 */
	public HisBlackOutDetailBean()
	{
	}

	/**
	 * @return the backupDate
	 */
	public String getBackupDate()
	{
		return backupDate;
	}

	/**
	 * @param backupDate the backupDate to set
	 */
	public void setBackupDate(String backupDate)
	{
		this.backupDate = backupDate;
	}
}
