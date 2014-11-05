<%@ page contentType="text/html;charset=UTF-8" language="java"
	errorPage="../common/error.jsp"%>
<%@include file="../common/common.jsp"%>
<html>
<head>
<p:link title="显示采购预付款申请" />
<script language="JavaScript" src="../js/JCheck.js"></script>
<script language="JavaScript" src="../js/common.js"></script>
<script language="JavaScript" src="../js/public.js"></script>
<script language="JavaScript" src="../js/math.js"></script>
<script language="javascript">

function load()
{
	loadForm();
}

</script>

</head>
<body class="body_class" onload="load()">
<form name="formEntry" action="../finance/stock.do" method="post">
	<input type="hidden" name="method" value="updateStockPrePayApply">
	<input type="hidden" name="providerId" value="${bean.providerId}">
	<input type="hidden" name="id" value="${bean.id}">
	
<p:navigation height="22">
	<td width="550" class="navigation"><span style="cursor: pointer;"
		onclick="javascript:history.go(-1)">采购预付款管理</span> &gt;&gt; 采购预付款</td>
	<td width="85"></td>
</p:navigation> <br>

<p:body width="100%">

	<p:title>
		<td class="caption"><strong>采购预付款基本信息：</strong></td>
	</p:title>

	<p:line flag="0" />

	<p:subBody width="98%">
		<p:class value="com.china.center.oa.finance.bean.StockPrePayApplyBean" opr="2"/>

		<p:table cells="1">
			
			<p:pro field="providerId" innerString="readonly=true" value="${bean.providerName}">
			</p:pro>
			
			<p:pro field="invoiceId">
				<c:forEach items="${invoiceList}" var="item">
                    <option value="${item.id}">${item.fullName}</option>
                </c:forEach>
			</p:pro>

			<p:cell title="付款金额">
				${my:formatNum(bean.moneys)}
			</p:cell>
			
			<p:pro field="payDate" />
			
			<p:cell title="申请人">
			${bean.stafferName}
			</p:cell>
			
			<p:pro field="logTime" />
			
			<p:pro field="description" innerString="rows=2 cols=80" />			
			
		</p:table>
	</p:subBody>

	<c:if test="${my:length(outList) > 0}">
	
	<p:line flag="0" />

    <p:subBody width="100%">
        <table width="100%" border="0" cellspacing='1' id="tables1">
            <tr align="center" class="content0">
                <td align="center">付款单</td>
                <td align="center">金额</td>
                <td align="center">银行</td>
            </tr>

            <c:forEach items="${outList}" var="item" varStatus="vs">
                <tr class='${vs.index % 2 == 0 ? "content1" : "content2"}'>
                    <td align="center"><a href="../finance/bill.do?method=findOutBill&id=${item.id}">${item.id}</a></td>

                    <td align="center">${my:formatNum(item.moneys)}</td>

                    <td align="center">${item.bankName}</td>

                </tr>
            </c:forEach>
        </table>
    </p:subBody>
    
	</c:if>
	
	<p:line flag="0" />

	<p:subBody width="100%">
		<table width="100%" border="0" cellspacing='1' id="tables">
			<tr align="center" class="content0">
				<td width="10%" align="center">审批人</td>
				<td width="10%" align="center">审批动作</td>
				<td width="10%" align="center">前状态</td>
				<td width="10%" align="center">后状态</td>
				<td width="45%" align="center">意见</td>
				<td width="15%" align="center">时间</td>
			</tr>

			<c:forEach items="${loglist}" var="item" varStatus="vs">
				<tr class='${vs.index % 2 == 0 ? "content1" : "content2"}'>
					<td align="center">${item.actor}</td>

					<td align="center">${my:get('oprMode', item.oprMode)}</td>

					<td align="center">${my:get('stockPayApplyStatus', item.preStatus)}</td>

					<td align="center">${my:get('stockPayApplyStatus', item.afterStatus)}</td>

					<td align="center">${item.description}</td>

					<td align="center">${item.logTime}</td>

				</tr>
			</c:forEach>
		</table>
	</p:subBody>

	<p:line flag="1" />

	<p:button leftWidth="100%" rightWidth="0%">
		<div align="right">
            <input
            type="button" name="ba" class="button_class"
            onclick="javascript:history.go(-1)"
            value="&nbsp;&nbsp;返 回&nbsp;&nbsp;">
			</div>
	</p:button>

	<p:message2 />
</p:body></form>
</body>
</html>

