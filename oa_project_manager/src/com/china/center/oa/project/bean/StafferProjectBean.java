package com.china.center.oa.project.bean;

import java.io.Serializable;

import com.china.center.jdbc.annotation.Entity;
import com.china.center.jdbc.annotation.FK;
import com.china.center.jdbc.annotation.Html;
import com.china.center.jdbc.annotation.Id;
import com.china.center.jdbc.annotation.Join;
import com.china.center.jdbc.annotation.Table;
import com.china.center.jdbc.annotation.enums.Element;
import com.china.center.jdbc.annotation.enums.JoinType;
import com.china.center.oa.project.constant.ProjectConstant;
import com.china.center.oa.publics.bean.StafferBean;

/**
 * 人员行项目
 * 
 * @author zhangyang
 * @version 2013-2-18
 * @see TravelApplyItemBean
 * @since 3.0
 */
@Entity
@Table(name = "t_center_stafferLineProject")
public class StafferProjectBean implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	private String id = "";

	@FK
	private String refid = "";

	@Join(tagClass = StafferBean.class, type = JoinType.LEFT)
	private String staffer = "";

	@Html(title = "人员角色", type = Element.SELECT, must = true)
	private String role = "";

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getStaffer() {
		return staffer;
	}

	public void setStaffer(String staffer) {
		this.staffer = staffer;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}
	
	

	public String getRefid() {
		return refid;
	}

	public void setRefid(String refid) {
		this.refid = refid;
	}

	public String toString() {
		return "StafferProjectBean [id=" + id + ", refid=" + refid
				+ ", staffer=" + staffer + ", role=" + role + "]";
	}

}
