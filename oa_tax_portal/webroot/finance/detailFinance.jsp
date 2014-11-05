<%@ page contentType="text/html;charset=UTF-8" language="java"
	errorPage="../common/error.jsp"%>
<%@include file="../common/common.jsp"%>
<html>
<head>
<p:link title="凭证明细"/>
<script language="JavaScript" src="../js/common.js"></script>
<script language="JavaScript" src="../js/public.js"></script>
<script language="JavaScript" src="../js/key.js"></script>
<script language="javascript">

function updateBean()
{
    document.location.href = '../finance/finance.do?method=findFinance&tempFlag=${tempFlag}&update=1&id=${bean.id}&backType=${backType}&backId=${backId}';
}


</script>

</head>

<body class="body_class">
<form name="formEntry">
<input type="hidden" name="id" value="${bean.id}">
<input type="hidden" name="tempFlag" value="${tempFlag}">
<p:navigation
	height="22">
	<td width="550" class="navigation">凭证明细</td>
	<td width="85"></td>
</p:navigation> <br>

<p:body width="98%">

	<p:title>
		<td class="caption"><strong>凭证信息：</strong></td>
	</p:title>

	<p:line flag="0" />

	<p:subBody width="100%">

		<p:table cells="2">
			<p:cell title="凭证时间">${bean.financeDate}</p:cell>
			<p:cell title="标识">${bean.id}</p:cell>
			<p:cell title="纳税实体">${bean.dutyName}</p:cell>
			<p:cell title="类型">${my:get('financeType', bean.type)}</p:cell>
			<p:cell title="创建类型">${my:get('financeCreateType', bean.createType)}</p:cell>
			<p:cell title="状态">${my:get('financeStatus', bean.status)}</p:cell>
			<p:cell title="锁定">${my:get('financeLock', bean.locks)}</p:cell>
			<p:cell title="总金额">${bean.showInmoney}</p:cell>
			<p:cell title="录入人">${bean.createrName}</p:cell>
			<p:cell title="录入时间">${bean.logTime}</p:cell>
			<p:cell title="更改状态">
			    ${my:get('financeUpdateFlag', bean.updateFlag)}
			</p:cell>
			<p:cell title="月索引">
                ${bean.monthIndex}
            </p:cell>
			<p:cell title="关联" end="true">
			<c:choose>
			    <c:when test="${bean.createType == 1}">
                <a href="../stock/stock.do?method=findStock&id=${bean.refId}">${bean.refId}</a>
                </c:when>
                
                <c:when test="${bean.createType == 2}">
                <a href="../finance/stock.do?method=findStockPayApply&id=${bean.refId}">${bean.refId}</a>
                </c:when>
                
			    <c:when test="${bean.createType == 3 || bean.createType == 4}">
			    <a href="../product/product.do?method=findCompose&id=${bean.refId}">${bean.refId}</a>
			    </c:when>
			    
			    <c:when test="${bean.createType == 5 || bean.createType == 6}">
                <a href="../product/product.do?method=findPriceChange&id=${bean.refId}">${bean.refId}</a>
                </c:when>
                
                <c:when test="${bean.createType == 20 || bean.createType == 21 || bean.createType == 22 
                            || bean.createType == 23
                            || bean.createType == 24
                            || bean.createType == 25
                            || bean.createType == 26
                            || bean.createType == 27
                            || bean.createType == 40
                            || bean.createType == 41
                            || bean.createType == 42
                            || bean.createType == 44
                            || bean.createType == 47
                            || bean.createType == 48
                            }">
                <a href="../sail/out.do?method=findOut&fow=99&outId=${bean.refId}">${bean.refId}</a>
                </c:when>
                
                <c:when test="${bean.createType == 45 || bean.createType == 46}">
                <a href="../sail/out.do?method=findOutBalance&id=${bean.refId}">${bean.refId}</a>
                </c:when>
                
                <c:when test="${bean.createType == 60 || bean.createType == 61 
                            || bean.createType == 63
                            || bean.createType == 67}">
                <a href="../finance/bank.do?method=findPayment&mode=2&id=${bean.refId}">${bean.refId}</a>
                </c:when>
                
                <c:when test="${bean.createType == 65 || bean.createType == 66}">
                <a href="../finance/backpay.do?method=findBackPayApply&id=${bean.refId}">${bean.refId}</a>
                </c:when>
                
                <c:when test="${bean.createType == 70}">
                <a href="../tcp/apply.do?method=findTravelApply&id=${bean.refId}">${bean.refId}</a>
                </c:when>
                
                <c:when test="${bean.createType >= 80 && bean.createType <= 89}">
                <a href="../tcp/expense.do?method=findExpense&id=${bean.refId}">${bean.refId}</a>
                </c:when>
                
				<c:otherwise>
				${bean.refId}
				</c:otherwise>
			</c:choose>
			</p:cell>
			<p:cell title="关联链接" end="true">
                <c:if test="${my:length(bean.refOut) > 0}">
                <a href="../sail/out.do?method=findOut&fow=99&outId=${bean.refOut}" title="点击查看销售入库详细">${bean.refOut}</a>&nbsp;&nbsp;
                </c:if>
                
                <c:if test="${my:length(bean.refBill) > 0}">
                <a href="../finance/bill.do?method=findBill&id=${bean.refBill}" title="点击查看收付款单详细">${bean.refBill}</a>&nbsp;&nbsp;
                </c:if>
                
                <c:if test="${my:length(bean.refStock) > 0}">
                <a href="../stock/stock.do?method=findStock&id=${bean.refStock}" title="点击查看采购单详细">${bean.refStock}</a>&nbsp;&nbsp;
                </c:if>
			</p:cell>
			<p:cell title="描述" end="true">${bean.description}</p:cell>
			<p:cell title="总部核对" end="true">${bean.checks}</p:cell>
			<p:cell title="凭证意见" end="true">${bean.refChecks}</p:cell>
		</p:table>
	</p:subBody>
	
	<p:tr/>
	
	<p:subBody width="100%">
		<p:table cells="2">
			<tr align="center" class="content0">
				 <td width="10%" align="center">索引</td>
				 <td width="25%" align="center">摘要</td>
                 <td width="15%" align="center">科目</td>
                 <td width="25%" align="center">辅助(部门/职员/单位/产品/仓区/纳税实体)</td>
                 <td width="8%" align="center">借方金额</td>
                 <td width="8%" align="center">贷方金额</td>
                 <td width="8%" align="center">产品借/贷</td>
			</tr>
			
			<c:forEach items="${bean.itemVOList}" var="item" varStatus="vs">
			<tr align="center" class="content${(vs.index + 1) % 2}">
				<td align="center">${item.pareId}</td>
				<td align="center">${item.description}</td>
				<td align="center">${item.taxName}(${item.taxId})</td>
				<td align="center">${item.departmentName}/${item.stafferName}/${item.unitName}/${item.productName}/${item.depotName}/${item.duty2Name}</td>
				<td align="center">${item.showInmoney}</td>
				<td align="center">${item.showOutmoney}</td>
				<td align="center">${item.productAmountIn}/${item.productAmountOut}</td>
			</tr>
			</c:forEach>
		</p:table>
	</p:subBody>
	
	<p:line flag="1" />

	<p:button leftWidth="100%" rightWidth="0%">
		<div align="right">
		<c:if test="${my:auth(user, '1802')}">
		<input type="button" class="button_class" id="return_b"
            style="cursor: pointer" value="&nbsp;&nbsp;财务修改&nbsp;&nbsp;"
            onclick="updateBean()">&nbsp;&nbsp;
        </c:if>
		<input type="button" class="button_class" id="return_b"
			style="cursor: pointer" value="&nbsp;&nbsp;返 回&nbsp;&nbsp;"
			onclick="javascript:history.go(-1)">
		</div>
	</p:button>

	<p:message2/>
</p:body></form>
</body>
</html>

