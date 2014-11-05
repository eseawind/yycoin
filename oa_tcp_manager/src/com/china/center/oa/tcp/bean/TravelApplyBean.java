/**
 * File Name: TravelApplyBean.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2011-7-10<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.tcp.bean;


import java.io.Serializable;

import com.china.center.jdbc.annotation.Entity;
import com.china.center.jdbc.annotation.Html;
import com.china.center.jdbc.annotation.Id;
import com.china.center.jdbc.annotation.Table;
import com.china.center.jdbc.annotation.enums.Element;
import com.china.center.oa.tcp.constanst.TcpConstanst;


/**
 * 借款申请
 * 
 * @author ZHUZHU
 * @version 2011-7-10
 * @see TravelApplyBean
 * @since 3.0
 */
@Entity(inherit = true)
@Table(name = "T_CENTER_TRAVELAPPLY")
public class TravelApplyBean extends AbstractTcpBean implements Serializable
{
    @Id
    @Html(title = "标识", must = true, maxLength = 40)
    private String id = "";

    @Html(title = "原申请单号", must = false,maxLength = 40)
    private String oldNumber = "";
    
    
    
    @Html(title = "目的", maxLength = 200, must = true, type = Element.TEXTAREA)
    private String qingJiapurpose = "";
    
    /**
     * 
     */
    @Html(title = "借款", must = true, type = Element.SELECT)
    private int borrow = TcpConstanst.TRAVELAPPLY_BORROW_NO;
    
    /**
     * 
     */
    @Html(title = "目的类型", must = true, type = Element.SELECT)
    private int purposeType=TcpConstanst.PURPOSETYPE ;
    
    /**
     * 是否借款
     */
    @Html(title = "请假类型", type = Element.SELECT)
    private int vocationType=TcpConstanst.VOCATIONTYPE ;

    /**
     * 是否报销冲抵
     */
    private int feedback = TcpConstanst.TCP_APPLY_FEEDBACK_NO;

    /**
     * default constructor
     */
    public TravelApplyBean()
    {
    }

    /**
     * @return the id
     */
    public String getId()
    {
        return id;
    }

    /**
     * @param id
     *            the id to set
     */
    public void setId(String id)
    {
        this.id = id;
    }

    /**
     * @return the borrow
     */
    public int getBorrow()
    {
        return borrow;
    }

    /**
     * @param borrow
     *            the borrow to set
     */
    public void setBorrow(int borrow)
    {
        this.borrow = borrow;
    }

    /**
     * @return the feedback
     */
    public int getFeedback()
    {
        return feedback;
    }

    /**
     * @param feedback
     *            the feedback to set
     */
    public void setFeedback(int feedback)
    {
        this.feedback = feedback;
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
            .append("TravelApplyBean ( ")
            .append(super.toString())
            .append(TAB)
            .append("id = ")
            .append(this.id)
            .append(TAB)
            .append("borrow = ")
            .append(this.borrow)
            .append(TAB)
            .append("feedback = ")
            .append(this.feedback)
            .append(TAB)
            .append("purposeType = ")
            .append(this.purposeType)
            .append(TAB)
            .append("vocationType = ")
            .append(this.vocationType)
            .append(TAB)
            .append("oldNumber = ")
            .append(this.oldNumber)
            .append(TAB)
            .append("qingJiapurpose = ")
            .append(this.qingJiapurpose)
            .append(TAB)
            .append(" )");

        return retValue.toString();
    }

	public int getPurposeType() {
		return purposeType;
	}

	public void setPurposeType(int purposeType) {
		this.purposeType = purposeType;
	}

	public int getVocationType() {
		return vocationType;
	}

	public void setVocationType(int vocationType) {
		this.vocationType = vocationType;
	}

	public String getOldNumber() {
		return oldNumber;
	}

	public void setOldNumber(String oldNumber) {
		this.oldNumber = oldNumber;
	}

	public String getQingJiapurpose() {
		return qingJiapurpose;
	}

	public void setQingJiapurpose(String qingJiapurpose) {
		this.qingJiapurpose = qingJiapurpose;
	}


	
}
