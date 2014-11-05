<%@ page contentType="text/html;charset=UTF-8" language="java"
    errorPage="../common/error.jsp"%>
<%@include file="../common/common.jsp"%>

<c:if test="${bean.status == 23}">
    
        <p:title>
            <td class="caption">
             <strong>财务入账-入账金额必须是:${ruAll}</strong>
            </td>
        </p:title>
    
        <p:line flag="0" />
        
        <tr>
            <td colspan='2' align='center'>
            <table width="98%" border="0" cellpadding="0" cellspacing="0"
                class="border">
                <tr>
                    <td>
                    <table width="100%" border="0" cellspacing='1' id="tables_tax">
                    <tr align="center" class="content0">
                        <td width="30%" align="center">科目</td>
                        <td width="15%" align="center">金额</td>
                        <td width="15%" align="center">承担人</td>
                        <td width="5%" align="left"><input type="button" accesskey="A"
                            value="增加" class="button_class" onclick="addTaxTr()"></td>
                    </tr>
                    
                    <c:forEach items="${wapList}" var="item" varStatus="vs">
                    <tr class="content1">
                         <td width="50%" align="center">
                         <input name="taxName" type="text" readonly="readonly"
                         value="${item.taxId} ${item.taxName}"
                          style="width: 100%;cursor: pointer;" oncheck="notNone" onclick="selectTax(this)">
                         <input type="hidden" name="taxId" value="${item.taxId}"> 
                         </td>
                         <td width="20%" align="center"><input type="text" style="width: 100%"
                                    name="t_money" value="${item.showMoney}" oncheck="notNone;isFloat">
                         </td>
                         
                         <td width="50%" align="center">
	                         <input name="taxStafferName" type="text" readonly="readonly"
	                         value="${item.stafferName}"
	                          style="width: 100%;cursor: pointer;" oncheck="notNone" onclick="selectTaxStaffer(this)">
	                         <input type="hidden" name="taxStafferId" value="${item.stafferId}"> 
                         </td>
                         
                         <td width="5%" align="center"><input type=button name="tax_del_bu"
                            value="&nbsp;删 除&nbsp;" class=button_class onclick="removeTr(this)"></td>
                    </tr>
                    </c:forEach>
                    
                    </table>
                    </td>
                </tr>
            </table>
    
            </td>
        </tr>
        
        <p:line flag="0" />
    </c:if>
    