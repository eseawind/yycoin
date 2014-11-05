package com.china.center.oa.project.bean;

import java.io.Serializable;

import com.china.center.jdbc.annotation.Entity;
import com.china.center.jdbc.annotation.FK;
import com.china.center.jdbc.annotation.Html;
import com.china.center.jdbc.annotation.Id;
import com.china.center.jdbc.annotation.Join;
import com.china.center.jdbc.annotation.Table;
import com.china.center.jdbc.annotation.enums.JoinType;
import com.china.center.oa.product.bean.ProductBean;
import com.china.center.oa.publics.bean.StafferBean;


@Entity
@Table(name = "t_center_productLineProject")
public class ProLineProjectBean implements Serializable
{
	 /**
	 * 
	 */
	private static final long serialVersionUID = 8081187733527880938L;
	
	public ProLineProjectBean()
	{
		
	}

	@Id
    private String id = "";
	
	@FK
    protected String refId = "";
	
	/**
     * 合同产品
     */
    @Join(tagClass = ProductBean.class, type = JoinType.LEFT)
    @Html(title = "合同产品",  must = true)
    private String projectpro = "";
    
    /**
     * 产品数量
     */
    @Html(title = "产品数量", must = true)
    private String procount = "";

    /**
     * 产品单价
     */
    @Html(title = "产品单价", maxLength = 200,  must = true)
    private String prounitprice = "";
    
    

	public String getProjectpro() {
		return projectpro;
	}

	public void setProjectpro(String projectpro) {
		this.projectpro = projectpro;
	}


	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getRefId() {
		return refId;
	}

	public void setRefId(String refId) {
		this.refId = refId;
	}

	public String getProcount() {
		return procount;
	}

	public void setProcount(String procount) {
		this.procount = procount;
	}

	public String getProunitprice() {
		return prounitprice;
	}

	public void setProunitprice(String prounitprice) {
		this.prounitprice = prounitprice;
	}

	@Override
	public String toString() {
		return "ProLineProjectBean [id=" + id + ", refId=" + refId
				+ ", projectpro=" + projectpro + ", procount=" + procount
				+ ", prounitprice=" + prounitprice + "]";
	}


	
}
