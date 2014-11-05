/**
 * File Name: TravelApplyVO.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2011-7-10<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.tcp.vo;


import java.util.List;

import com.china.center.jdbc.annotation.Entity;
import com.china.center.jdbc.annotation.Html;
import com.china.center.jdbc.annotation.Ignore;
import com.china.center.jdbc.annotation.Relationship;
import com.china.center.jdbc.annotation.enums.Element;
import com.china.center.oa.tcp.bean.ExpenseApplyBean;


/**
 * TravelApplyVO
 * 
 * @author ZHUZHU
 * @version 2011-7-10
 * @see ExpenseApplyVO
 * @since 3.0
 */
@Entity(inherit = true)
public class ExpenseApplyVO extends ExpenseApplyBean
{
    @Relationship(relationField = "stafferId")
    private String stafferName = "";

    @Relationship(relationField = "borrowStafferId")
    private String borrowStafferName = "";

    @Relationship(relationField = "departmentId")
    private String departmentName = "";

    @Relationship(relationField = "dutyId")
    private String dutyName = "";

    /**
     * 当前处理人
     */
    @Ignore
    private String processer = "";

    /**
     * 流程描述
     */
    @Ignore
    private String flowDescription = "";

    @Ignore
    private List<TravelApplyItemVO> itemVOList = null;

    /**
     * 分担列表
     */
    @Ignore
    private List<TcpShareVO> shareVOList = null;

    /**
     * 准确到分
     */
    @Ignore
    @Html(title = "总费用", must = true, type = Element.DOUBLE)
    private String showTotal = "";

    /**
     * 付款金额
     */
    @Ignore
    @Html(title = "公司支付金额", must = true, type = Element.DOUBLE)
    private String showBorrowTotal = "";

    @Ignore
    @Html(title = "飞机票", must = true, type = Element.DOUBLE)
    private String showAirplaneCharges = "";

    @Ignore
    @Html(title = "火车票", must = true, type = Element.DOUBLE)
    private String showTrainCharges = "";

    @Ignore
    @Html(title = "汽车票", must = true, type = Element.DOUBLE)
    private String showBusCharges = "";

    @Ignore
    @Html(title = "住宿费", must = true, type = Element.DOUBLE)
    private String showHotelCharges = "";

    @Ignore
    @Html(title = "业务招待费", must = true, type = Element.DOUBLE)
    private String showEntertainCharges = "";

    @Ignore
    @Html(title = "补贴", must = true, type = Element.DOUBLE)
    private String showAllowanceCharges = "";

    @Ignore
    @Html(title = "其他费用一", must = true, type = Element.DOUBLE)
    private String showOther1Charges = "";

    @Ignore
    @Html(title = "其他费用二", must = true, type = Element.DOUBLE)
    private String showOther2Charges = "";

    /**
     * default constructor
     */
    public ExpenseApplyVO()
    {
    }

    /**
     * @return the stafferName
     */
    public String getStafferName()
    {
        return stafferName;
    }

    /**
     * @param stafferName
     *            the stafferName to set
     */
    public void setStafferName(String stafferName)
    {
        this.stafferName = stafferName;
    }

    /**
     * @return the departmentName
     */
    public String getDepartmentName()
    {
        return departmentName;
    }

    /**
     * @param departmentName
     *            the departmentName to set
     */
    public void setDepartmentName(String departmentName)
    {
        this.departmentName = departmentName;
    }

    /**
     * @return the showTotal
     */
    public String getShowTotal()
    {
        return showTotal;
    }

    /**
     * @param showTotal
     *            the showTotal to set
     */
    public void setShowTotal(String showTotal)
    {
        this.showTotal = showTotal;
    }

    /**
     * @return the showBorrowTotal
     */
    public String getShowBorrowTotal()
    {
        return showBorrowTotal;
    }

    /**
     * @param showBorrowTotal
     *            the showBorrowTotal to set
     */
    public void setShowBorrowTotal(String showBorrowTotal)
    {
        this.showBorrowTotal = showBorrowTotal;
    }

    /**
     * @return the itemVOList
     */
    public List<TravelApplyItemVO> getItemVOList()
    {
        return itemVOList;
    }

    /**
     * @param itemVOList
     *            the itemVOList to set
     */
    public void setItemVOList(List<TravelApplyItemVO> itemVOList)
    {
        this.itemVOList = itemVOList;
    }

    /**
     * @return the shareVOList
     */
    public List<TcpShareVO> getShareVOList()
    {
        return shareVOList;
    }

    /**
     * @param shareVOList
     *            the shareVOList to set
     */
    public void setShareVOList(List<TcpShareVO> shareVOList)
    {
        this.shareVOList = shareVOList;
    }

    /**
     * @return the showAirplaneCharges
     */
    public String getShowAirplaneCharges()
    {
        return showAirplaneCharges;
    }

    /**
     * @param showAirplaneCharges
     *            the showAirplaneCharges to set
     */
    public void setShowAirplaneCharges(String showAirplaneCharges)
    {
        this.showAirplaneCharges = showAirplaneCharges;
    }

    /**
     * @return the showTrainCharges
     */
    public String getShowTrainCharges()
    {
        return showTrainCharges;
    }

    /**
     * @param showTrainCharges
     *            the showTrainCharges to set
     */
    public void setShowTrainCharges(String showTrainCharges)
    {
        this.showTrainCharges = showTrainCharges;
    }

    /**
     * @return the showBusCharges
     */
    public String getShowBusCharges()
    {
        return showBusCharges;
    }

    /**
     * @param showBusCharges
     *            the showBusCharges to set
     */
    public void setShowBusCharges(String showBusCharges)
    {
        this.showBusCharges = showBusCharges;
    }

    /**
     * @return the showHotelCharges
     */
    public String getShowHotelCharges()
    {
        return showHotelCharges;
    }

    /**
     * @param showHotelCharges
     *            the showHotelCharges to set
     */
    public void setShowHotelCharges(String showHotelCharges)
    {
        this.showHotelCharges = showHotelCharges;
    }

    /**
     * @return the showEntertainCharges
     */
    public String getShowEntertainCharges()
    {
        return showEntertainCharges;
    }

    /**
     * @param showEntertainCharges
     *            the showEntertainCharges to set
     */
    public void setShowEntertainCharges(String showEntertainCharges)
    {
        this.showEntertainCharges = showEntertainCharges;
    }

    /**
     * @return the showAllowanceCharges
     */
    public String getShowAllowanceCharges()
    {
        return showAllowanceCharges;
    }

    /**
     * @param showAllowanceCharges
     *            the showAllowanceCharges to set
     */
    public void setShowAllowanceCharges(String showAllowanceCharges)
    {
        this.showAllowanceCharges = showAllowanceCharges;
    }

    /**
     * @return the showOther1Charges
     */
    public String getShowOther1Charges()
    {
        return showOther1Charges;
    }

    /**
     * @param showOther1Charges
     *            the showOther1Charges to set
     */
    public void setShowOther1Charges(String showOther1Charges)
    {
        this.showOther1Charges = showOther1Charges;
    }

    /**
     * @return the showOther2Charges
     */
    public String getShowOther2Charges()
    {
        return showOther2Charges;
    }

    /**
     * @param showOther2Charges
     *            the showOther2Charges to set
     */
    public void setShowOther2Charges(String showOther2Charges)
    {
        this.showOther2Charges = showOther2Charges;
    }

    /**
     * @return the processer
     */
    public String getProcesser()
    {
        return processer;
    }

    /**
     * @param processer
     *            the processer to set
     */
    public void setProcesser(String processer)
    {
        this.processer = processer;
    }

    /**
     * @return the borrowStafferName
     */
    public String getBorrowStafferName()
    {
        return borrowStafferName;
    }

    /**
     * @param borrowStafferName
     *            the borrowStafferName to set
     */
    public void setBorrowStafferName(String borrowStafferName)
    {
        this.borrowStafferName = borrowStafferName;
    }

    /**
     * @return the flowDescription
     */
    public String getFlowDescription()
    {
        return flowDescription;
    }

    /**
     * @param flowDescription
     *            the flowDescription to set
     */
    public void setFlowDescription(String flowDescription)
    {
        this.flowDescription = flowDescription;
    }

    /**
     * @return the dutyName
     */
    public String getDutyName()
    {
        return dutyName;
    }

    /**
     * @param dutyName
     *            the dutyName to set
     */
    public void setDutyName(String dutyName)
    {
        this.dutyName = dutyName;
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
            .append("ExpenseApplyVO ( ")
            .append(super.toString())
            .append(TAB)
            .append("stafferName = ")
            .append(this.stafferName)
            .append(TAB)
            .append("borrowStafferName = ")
            .append(this.borrowStafferName)
            .append(TAB)
            .append("departmentName = ")
            .append(this.departmentName)
            .append(TAB)
            .append("dutyName = ")
            .append(this.dutyName)
            .append(TAB)
            .append("processer = ")
            .append(this.processer)
            .append(TAB)
            .append("flowDescription = ")
            .append(this.flowDescription)
            .append(TAB)
            .append("itemVOList = ")
            .append(this.itemVOList)
            .append(TAB)
            .append("shareVOList = ")
            .append(this.shareVOList)
            .append(TAB)
            .append("showTotal = ")
            .append(this.showTotal)
            .append(TAB)
            .append("showBorrowTotal = ")
            .append(this.showBorrowTotal)
            .append(TAB)
            .append("showAirplaneCharges = ")
            .append(this.showAirplaneCharges)
            .append(TAB)
            .append("showTrainCharges = ")
            .append(this.showTrainCharges)
            .append(TAB)
            .append("showBusCharges = ")
            .append(this.showBusCharges)
            .append(TAB)
            .append("showHotelCharges = ")
            .append(this.showHotelCharges)
            .append(TAB)
            .append("showEntertainCharges = ")
            .append(this.showEntertainCharges)
            .append(TAB)
            .append("showAllowanceCharges = ")
            .append(this.showAllowanceCharges)
            .append(TAB)
            .append("showOther1Charges = ")
            .append(this.showOther1Charges)
            .append(TAB)
            .append("showOther2Charges = ")
            .append(this.showOther2Charges)
            .append(TAB)
            .append(" )");

        return retValue.toString();
    }
}
