<%@ page contentType="text/html;charset=UTF-8" language="java"
	errorPage="../common/error.jsp"%>
<%@include file="../common/common.jsp"%>
<html>
<head>
<p:link title="中信客户分组" />
<script language="JavaScript" src="../js/key.js"></script>
<script language="JavaScript" src="../js/common.js"></script>
<script language="javascript">

function load()
{
    setAllReadOnly();
}

</script>

</head>
<body class="body_class" onload="load()">
<p:navigation
	height="22">
	<td width="550" class="navigation"><span style="cursor: pointer;"
		onclick="javascript:history.go(-1)">群组管理</span> &gt;&gt; 群组详细</td>
	<td width="85"></td>
</p:navigation> <br>

<p:body width="98%">

	<p:title>
		<td class="caption"><strong>群组基本信息：</strong></td>
	</p:title>

	<p:line flag="0" />

	<p:subBody width="100%">
		<p:class value="com.china.center.oa.customer.bean.CiticBranchBean" opr="1"/>

		<p:table cells="1">
			<p:pro field="stafferId" value="${bean.stafferName}">
			</p:pro>
			
			<p:cell title="组员">
                <br>
                <select multiple="multiple" size="20" style="width: 260px" name="vsCustomerIds" disall="true">
                <c:forEach items="${items}" var="item">
                <option value="${item.customerId}">${item.customerName}</option>
                </c:forEach>
                </select>
            </p:cell>
		</p:table>
	</p:subBody>

	<p:line flag="1" />

	<p:button leftWidth="100%" rightWidth="0%">
		<div align="right"><input type="button" class="button_class" id="ok_b"
			style="cursor: pointer" value="&nbsp;&nbsp;返 回&nbsp;&nbsp;"
			onclick="javascript:history.go(-1)"></div>
	</p:button>

	<p:message/>
</p:body>
</body>
</html>

