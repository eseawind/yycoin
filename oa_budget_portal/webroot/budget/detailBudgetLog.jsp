<%@page contentType="text/html; charset=UTF-8"%>
<%@include file="../common/common.jsp"%>
<html>
<head>
<p:link title="修改预算" />
<script language="JavaScript" src="../js/common.js"></script>
<script language="JavaScript" src="../js/public.js"></script>
<script language="JavaScript" src="../js/JCheck.js"></script>
<script language="JavaScript" src="../js/key.js"></script>
<script language="JavaScript" src="../budget_js/budget.js"></script>
<script language="javascript">
function load()
{
}

</script>

</head>
<body class="body_class" onload="load()">
<form name="formEntry" > 
<p:navigation height="22">
	<td width="550" class="navigation">预算使用详细</td>
	<td width="85"></td>
</p:navigation> <br>

<p:body width="100%">

	<p:title>
		<td class="caption"><strong>预算使用信息：</strong></td>
	</p:title>

	<p:line flag="0" />

	<p:subBody width="98%">
		<p:class value="com.china.center.oa.budget.vo.BudgetLogVO" opr="2" />

		<p:table cells="2">
		    <p:cell title="标识" end="true">
               ${bean.id}
            </p:cell>
            
            <p:cell title="使用人">
               ${bean.stafferName}
            </p:cell>
            
            <p:cell title="部门">
               ${bean.departmentName}
            </p:cell>
            
            <p:cell title="预算">
               ${bean.budgetName}
            </p:cell>
            
            <p:cell title="预算项">
               ${bean.feeItemName}
            </p:cell>
            
            <p:cell title="来源">
               ${my:get('budgetLogFromType', bean.fromType)}
            </p:cell>
            
            <p:cell title="关联单据">
            <c:choose>
               <c:when test="${bean.fromType == 0}">
               <a href="../tcp/apply.do?method=findTravelApply&id=${bean.refId}">${bean.refId}</a>
               </c:when>
               
               <c:when test="${bean.fromType == 1}">
               <a href="../tcp/expense.do?method=findExpense&id=${bean.refId}">${bean.refId}</a>
               </c:when>
               
               <c:otherwise>
                ${bean.refId}
                </c:otherwise>
            </c:choose>
            </p:cell>
            
            <p:cell title="使用类型">
               ${my:get('budgetLogUserType', bean.userType)}
            </p:cell>
            
            
            <p:cell title="金额">
               ${my:formatNum(bean.monery / 100.0)}
            </p:cell>
            
            <p:cell title="状态">
               ${my:get('budgetLogStatus', bean.status)}
            </p:cell>
            
            <p:cell title="时间" >
               ${bean.logTime}
            </p:cell>
            
            <p:cell title="日志内容" end="true">
               ${bean.log}
            </p:cell>
            
             <p:cell title="关联付款单" end="true">
               <c:forEach items="${billList}" var="item">
               <a href="../finance/bill.do?method=findBill&id=${item}">${item}</a>&nbsp;&nbsp;
               </c:forEach>
            </p:cell>
			
		</p:table>
	</p:subBody>

	<p:line flag="1" />

	<p:button leftWidth="100%" rightWidth="0%">
		<div align="right"><input type="button" class="button_class"
			id="ok_b" style="cursor: pointer" value="&nbsp;&nbsp;返 回&nbsp;&nbsp;"
			onclick="javaScript:window.history.go(-1);"></div>
	</p:button>
</p:body>
</form>

</body>
</html>

