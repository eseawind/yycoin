package com.china.center.oa.project.bean;

import java.io.Serializable;

import com.china.center.jdbc.annotation.Entity;
import com.china.center.jdbc.annotation.Html;
import com.china.center.jdbc.annotation.Table;

@Entity
@Table(name = "t_center_partLineProject")
public class PartProjectBean implements Serializable
{
	
	 /**
	 * 
	 */
	private static final long serialVersionUID = 3853798754541105296L;
	
	public PartProjectBean()
	{
		
	}

	/**
     * 配件明细
     */
    @Html(title = "配件明细")
    private String partsDetail = "";

    /**
     * 配件数量
     */
    @Html(title = "配件数量")
    private String partsCount = "";

	public String getPartsDetail() {
		return partsDetail;
	}

	public void setPartsDetail(String partsDetail) {
		this.partsDetail = partsDetail;
	}

	public String getPartsCount() {
		return partsCount;
	}

	public void setPartsCount(String partsCount) {
		this.partsCount = partsCount;
	}

	@Override
	public String toString() {
		return "PartProject [partsDetail=" + partsDetail + ", partsCount="
				+ partsCount + "]";
	}
    
    
}
