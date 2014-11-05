package com.china.center.oa.product.vo;

import com.china.center.jdbc.annotation.Entity;
import com.china.center.jdbc.annotation.Relationship;
import com.china.center.oa.product.bean.ProductChangeRecordBean;

@Entity(inherit = true)
public class ProductChangeRecordVO extends ProductChangeRecordBean 
{
	
	@Relationship(relationField = "oldStore")
    private String oldName = "";

    @Relationship(relationField = "newStore")
    private String newName = "";
    
    @Relationship(relationField = "productId")
    private String productName = "";
    
    @Relationship(relationField = "moveStaffer")
    private String moveStafferName = "";

	public ProductChangeRecordVO()
    {
    }

	public String getOldName() {
		return oldName;
	}

	public void setOldName(String oldName) {
		this.oldName = oldName;
	}

	public String getNewName() {
		return newName;
	}

	public void setNewName(String newName) {
		this.newName = newName;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}
	
	
	
	public String getMoveStafferName() {
		return moveStafferName;
	}

	public void setMoveStafferName(String moveStafferName) {
		this.moveStafferName = moveStafferName;
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

        retValue.append
        ("ProductChangeRecordVO ( ")
        .append(super.toString())
        .append(TAB)
        .append("oldName = ")
        .append(this.oldName)
        .append(TAB)
        .append("newName = ")
        .append(this.newName)
        .append(TAB)
        .append("productName = ")
        .append(this.productName)
        .append(TAB)
         .append("moveStafferName = ")
        .append(this.moveStafferName)
        .append(TAB)
        .append(
            " )");

        return retValue.toString();
    }
}
