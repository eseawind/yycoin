package com.china.center.oa.project.vo;

import com.china.center.jdbc.annotation.Entity;
import com.china.center.jdbc.annotation.Relationship;
import com.china.center.oa.project.bean.StafferProjectBean;

@Entity(inherit = true)
public class StafferProjectVO extends StafferProjectBean
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Relationship(relationField = "staffer")
    private String pstafferName = "";

	public StafferProjectVO()
	{
		
	}

	public String getPstafferName() {
		return pstafferName;
	}

	public void setPstafferName(String pstafferName) {
		this.pstafferName = pstafferName;
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

        retValue.append("StafferProjectVO ( ")
        .append(super.toString())
        .append(TAB)
        .append("pstafferName = ")
        .append(this.pstafferName)
        .append(TAB)
        .append(" )");

        return retValue.toString();
    }
}
