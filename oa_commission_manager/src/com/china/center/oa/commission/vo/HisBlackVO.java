package com.china.center.oa.commission.vo;

import com.china.center.jdbc.annotation.Entity;
import com.china.center.jdbc.annotation.Relationship;
import com.china.center.oa.commission.bean.HisBlackBean;

@SuppressWarnings("serial")
@Entity(inherit = true)
public class HisBlackVO extends HisBlackBean 
{

    @Relationship(relationField = "stafferId")
    private String stafferName = "";
    
    @Relationship(relationField = "stafferId", tagField = "birthday")
    private String birthday = "";
    
    @Relationship(relationField = "industryId")
    private String industryName = "";
    
    
    public HisBlackVO(){}

    public String getStafferName() {
        return stafferName;
    }

    public void setStafferName(String stafferName) {
        this.stafferName = stafferName;
    }
    
    public String getIndustryName() {
		return industryName;
	}

	public void setIndustryName(String industryName) {
		this.industryName = industryName;
	}

	public String getBirthday()
	{
		return birthday;
	}

	public void setBirthday(String birthday)
	{
		this.birthday = birthday;
	}

	public String toString()
    {
        final String TAB = ",";

        StringBuilder retValue = new StringBuilder();

        retValue
            .append("BlackVO ( ")
            .append(super.toString())
            .append(TAB)
            .append("stafferName = ")
            .append(this.stafferName)
            .append(TAB)
            .append("industryName = ")
            .append(this.industryName)
            .append(TAB)
            .append(" )");
            ;
        
        return retValue.toString();
    }
}
