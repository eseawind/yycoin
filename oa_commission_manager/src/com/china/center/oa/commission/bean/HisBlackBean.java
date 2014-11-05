package com.china.center.oa.commission.bean;

import com.china.center.jdbc.annotation.Entity;
import com.china.center.jdbc.annotation.Table;
import com.china.center.jdbc.annotation.Unique;

@SuppressWarnings("serial")
@Entity(inherit = true)
@Table(name = "T_CENTER_BLACK_H")
public class HisBlackBean extends AbstractBlackBean
{
	@Unique(dependFields = "id")
	private String backupDate = "";
	
	public HisBlackBean(){}

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
