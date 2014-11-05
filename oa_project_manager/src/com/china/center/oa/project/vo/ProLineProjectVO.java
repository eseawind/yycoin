package com.china.center.oa.project.vo;

import com.china.center.jdbc.annotation.Entity;
import com.china.center.jdbc.annotation.Join;
import com.china.center.jdbc.annotation.Relationship;
import com.china.center.jdbc.annotation.enums.JoinType;
import com.china.center.oa.project.bean.ProLineProjectBean;
import com.china.center.oa.publics.bean.StafferBean;

@Entity(inherit = true)
public class ProLineProjectVO extends ProLineProjectBean
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7793906156412966905L;
	
	@Relationship(relationField = "projectpro")
    private String projectproName = "";

	public ProLineProjectVO()
	{
		
	}
	
	 /**
     * Constructs a <code>String</code> with all attributes in name = value format.
     * 
     * @return a <code>String</code> representation of this object.
     */
    public String toString()
    {
        final String TAB = ",";

        StringBuilder retValue = new StringBuilder();

        retValue
            .append("ProLineProjectVO ( ")
            .append(super.toString())
            .append(TAB)
            .append("projectproName = ")
            .append(this.projectproName)
            .append(TAB)
            .append(" )");

        return retValue.toString();
    }

	public String getProjectproName() {
		return projectproName;
	}

	public void setProjectproName(String projectproName) {
		this.projectproName = projectproName;
	}
    
    
}
