package com.china.center.oa.sail.bean;

import java.io.Serializable;
import java.util.List;

import com.china.center.jdbc.annotation.Entity;
import com.china.center.jdbc.annotation.Html;
import com.china.center.jdbc.annotation.Id;
import com.china.center.jdbc.annotation.Ignore;
import com.china.center.jdbc.annotation.Join;
import com.china.center.jdbc.annotation.Table;
import com.china.center.jdbc.annotation.enums.Element;
import com.china.center.jdbc.annotation.enums.JoinType;
import com.china.center.oa.publics.bean.PrincipalshipBean;

@Entity
@Table(name = "T_CENTER_OUT_AUDITRULE")
public class AuditRuleBean implements Serializable
{
	@Id
	private String id = "";
	
	/**
	 * 事业部
	 */
	@Html(name = "industryName", title = "事业部", must = true)
	@Join(tagClass = PrincipalshipBean.class, type = JoinType.LEFT)
	private String industryId = "";
	
	/**
	 * 销售类型
	 */
	@Html(title = "销售类型", must = true, type = Element.SELECT)
	private int sailType = 0;	

	/**
	 * 商品行项目
	 */
	@Ignore	
	private List<AuditRuleItemBean> itemList = null;
	
	public String getId()
	{
		return id;
	}

	public void setId(String id)
	{
		this.id = id;
	}

	public String getIndustryId()
	{
		return industryId;
	}

	public void setIndustryId(String industryId)
	{
		this.industryId = industryId;
	}

	public int getSailType()
	{
		return sailType;
	}

	public void setSailType(int sailType)
	{
		this.sailType = sailType;
	}
	
	public List<AuditRuleItemBean> getItemList()
	{
		return itemList;
	}

	public void setItemList(List<AuditRuleItemBean> itemList)
	{
		this.itemList = itemList;
	}

	public String toString()
	{
        final String TAB = ",";

        StringBuilder retValue = new StringBuilder();

        retValue
            .append("AuditRuleBean ( ")
            .append(super.toString())
            .append(TAB)
            .append("id = ")
            .append(this.id)
            .append(TAB)
            .append("industryId = ")
            .append(this.industryId)
            .append(TAB)
            .append("sailType = ")
            .append(this.sailType)            
            .append(TAB)
            .append("itemList = ")
            .append(this.itemList)            
            .append(TAB)
            .append(" )");

        return retValue.toString();
    }
}
