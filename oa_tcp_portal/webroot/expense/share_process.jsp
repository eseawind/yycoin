<%@ page contentType="text/html;charset=UTF-8" language="java"
    errorPage="../common/error.jsp"%>
<%@include file="../common/common.jsp"%>
<p:title>
        <td class="caption">
         <strong>费用分担</strong>
        </td>
    </p:title>

    <p:line flag="0" />
    
    <tr>
        <td colspan='2' align='center'>
        <table width="98%" border="0" cellpadding="0" cellspacing="0"
            class="border">
            <tr>
                <td>
                <table width="100%" border="0" cellspacing='1' id="tables_share">
                    <tr align="center" class="content0">
                        <td width="30%" align="center">月度预算</td>
                        <td width="30%" align="center">部门</td>
                        <td width="15%" align="center">权签人</td>
                        <td width="15%" align="center">承担人</td>
                        <td width="10%" align="center">分担比例(%)/金额</td>
                    </tr>
                    <c:forEach items="${bean.shareVOList}" var="item">
                    <tr align="center" class="content1">
                        <td align="center">${item.budgetName}</td>
                        <td align="center">${item.departmentName}</td>
                        <td align="center">${item.approverName}</td>
                        <td align="center">${item.bearName}</td>
                        <td align="center">${item.showRealMonery}</td>
                    </tr>
                    </c:forEach>
                </table>
                </td>
            </tr>
        </table>

        </td>
    </tr>