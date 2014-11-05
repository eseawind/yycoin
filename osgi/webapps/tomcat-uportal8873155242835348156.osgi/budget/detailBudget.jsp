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
    bingTable("result");
}

</script>

</head>
<body class="body_class" onload="load()">
<form name="formEntry" action="../budget/budget.do"><input
	type="hidden" name="method" value="updateBudget"> <input
	type="hidden" name="opr" value="0"> <input type="hidden"
	name="id" value="${bean.id}"> <p:navigation height="22">
	<td width="550" class="navigation">预算详细</td>
	<td width="85"></td>
</p:navigation> <br>

<p:body width="100%">

	<p:title>
		<td class="caption"><strong>预算基本信息：</strong></td>
	</p:title>

	<p:line flag="0" />

	<p:subBody width="98%">
		<p:class value="com.china.center.oa.budget.vo.BudgetVO" opr="2" />

		<p:table cells="2">
		    <p:cell title="预算标识" end="true">
               ${bean.id}
            </p:cell>
            
			<p:pro field="name" cell="2" innerString="size=60"/>
            
            <p:pro field="type">
            <p:option type="budgetType"></p:option>
            </p:pro>
            <p:pro field="level">
            <p:option type="budgetLevel"></p:option>
            </p:pro>
            
            <p:pro field="year">
                <option value="">--</option>
                <c:forEach begin="2000" end="2100" var="item">
                    <option value="${item}">${item}</option>
                </c:forEach>
            </p:pro>
            
            <p:pro field="status">
                <p:option type="budgetStatus"></p:option>
            </p:pro>
            
             <p:pro field="carryStatus">
                <p:option type="budgetCarry"></p:option>
            </p:pro>
            
            <p:cells celspan="1" title="预算部门">
            ${bean.budgetFullDepartmentName}
            </p:cells>
            
            <p:pro field="beginDate" />
            <p:pro field="endDate" />
            
            <p:pro field="sail" />
            <p:pro field="orgProfit" />
            
            <p:pro field="realProfit" />
            <p:pro field="outSave" />
            
            <p:pro field="outMoney"/>
            <p:pro field="inMoney" />
            
            <p:cell title="预算合计">
               ${my:formatNum(bean.total)}
            </p:cell>
            
            <p:cell title="实际使用">
               ${my:formatNum(bean.realMonery)}
            </p:cell>
            
            <p:cell title="权签人">
               ${bean.signerName}
            </p:cell>
            
            <p:cell title="提交人">
            ${bean.stafferName}
            </p:cell>
            
            <p:pro field="description" cell="2" innerString="rows=4 cols=60"/>
		</p:table>
	</p:subBody>

	<p:tr></p:tr>
	
	<p:subBody width="98%">
        <table width="100%" align="center" cellspacing='1' class="table0"
            id="result">
            <tr align=center class="content0">
                <td align="center" width="10%"><strong>预算项</strong></td>
                
                <c:if test="${!unit}">
                <td align="center"><strong>子预算</strong></td>
                </c:if>
                
                <td align="center"><strong>预算金额</strong></td>
                
                <c:if test="${!unit}">
                <td align="center"><strong>未分配预算</strong></td>
                </c:if>
                <c:if test="${unit}">
                <td align="center"><strong>剩余预算</strong></td>
                </c:if>
                
                <td align="center"><strong>实际使用/预占+实际</strong></td>
                
                <c:if test="${!unit}">
                <td align="center"><strong>剩余预算</strong></td>
                </c:if>
                
                <td align="center"><strong>使用率</strong></td>
                <td align="center"><strong>描述</strong></td>
            </tr>

            <c:forEach items="${bean.itemVOs}" var="item" varStatus="vs">
                <tr class="${vs.index % 2 == 0 ? 'content1' : 'content2'}">
                    <td align="center" onclick="hrefAndSelect(this)" width="10%">${item.feeItemName}</td>
                    
                    <c:if test="${!unit}">
                    <td align="center" onclick="hrefAndSelect(this)">${item.subDescription}</td>
                    </c:if>
                    
                    
                    <c:if test="${bean.status == 99}">
                    <td align="center" onclick="hrefAndSelect(this)">${my:formatNum(item.budget)}</td>
                    </c:if>
                    <c:if test="${bean.status != 99}">
                    <td align="center" onclick="hrefAndSelect(this)">${my:formatNum(item.budget)}</td>
                    </c:if>
                    
                    <c:if test="${!unit}">
                    <td align="center" onclick="hrefAndSelect(this)">${item.snoAssignMonery}</td>
                    </c:if>
                     <c:if test="${unit}">
                    <td align="center" onclick="hrefAndSelect(this)">${item.sremainMonery}</td>
                    </c:if>
                    
                    <td align="center" onclick="hrefAndSelect(this)">
                    <a title="点击查看使用明细" href="../budget/budget.do?method=queryAllBudgetLog&load=1&itemId=${item.id}">${item.suseMonery}/${item.spreAndUseMonery}</a>
                    </td>
                    
                    <c:if test="${!unit}">
                        <td align="center" onclick="hrefAndSelect(this)">${item.sremainMonery}</td>
                    </c:if>
                    
                    <c:if test="${item.budget > 0}">
                    <td align="center" onclick="hrefAndSelect(this)">${my:formatNum((item.suseMonery / item.budget) * 100.0)}%</td>
                    </c:if>
                     <c:if test="${item.budget == 0}">
                    <td align="center" onclick="hrefAndSelect(this)">0.00%</td>
                    </c:if>
                    
                    <td align="center" onclick="hrefAndSelect(this)">${item.description}</td>
                </tr>
            </c:forEach>
        </table>
    </p:subBody>
	
	<p:tr/>
	
	<tr>
        <td colspan='2' align='center'>
        <div id="desc1" style="display: block;">
        <table width="98%" border="0" cellpadding="0" cellspacing="0"
            class="border">
            <tr>
                <td>
                <table width="100%" border="0" cellspacing='1' id="tables2">
                    <tr align="center" class="content0">
                        <td width="10%" align="center">操作人</td>
                        <td width="10%" align="center">动作</td>
                        <td width="45%" align="center">日志</td>
                        <td width="15%" align="center">时间</td>
                    </tr>

                    <c:forEach items="${logs}" var="item" varStatus="vs">
                        <tr class='${vs.index % 2 == 0 ? "content1" : "content2"}'>
                            <td align="center">${item.stafferName}</td>

                            <td  align="center">${item.operation}</td>

                            <td  align="center">${item.log}</td>

                            <td  align="center">${item.logTime}</td>

                        </tr>
                    </c:forEach>
                </table>
                </td>
            </tr>
        </table>
        </div>
        </td>
    </tr>

	<p:line flag="1" />

	<p:button leftWidth="100%" rightWidth="0%">
		<div align="right">
		<input type="button" class="button_class"
			id="ok_b" style="cursor: pointer" value="&nbsp;&nbsp;返 回&nbsp;&nbsp;"
			onclick="javaScript:window.history.go(-1);">
	   </div>
	</p:button>
</p:body></form>
</body>
</html>

