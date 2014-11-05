<%@ page contentType="text/html;charset=UTF-8" language="java"
	errorPage="../common/error.jsp"%>
<%@include file="../common/common.jsp"%>
<html>
<head>
<p:link title="修改发票" />
<script language="JavaScript" src="../js/JCheck.js"></script>
<script language="JavaScript" src="../js/common.js"></script>
<script language="JavaScript" src="../js/public.js"></script>
<script language="javascript">
function addBean()
{
	submit('确定修改发票?');
}
</script>

</head>
<body class="body_class">
<form name="formEntry" action="../admin/invoice.do" method="post">
<input type="hidden" name="method" value="updateInvoice">
<input type="hidden" name="id" value="${bean.id}">
<p:navigation
	height="22">
	<td width="550" class="navigation"><span style="cursor: pointer;"
		onclick="javascript:history.go(-1)">发票管理</span> &gt;&gt; 修改发票</td>
	<td width="85"></td>
</p:navigation> <br>

<p:body width="98%">

	<p:title>
		<td class="caption"><strong>发票基本信息：</strong></td>
	</p:title>

	<p:line flag="0" />

	<p:subBody width="100%">
		<p:class value="com.china.center.oa.publics.bean.InvoiceBean" opr="1"/>

		<p:table cells="1">

			<p:pro field="name" innerString="readonly=readonly"/>
			
			<p:pro field="type" innerString="readonly=true">
				<p:option type="invoiceType"/>
			</p:pro>
			
			<p:pro field="forward" innerString="readonly=true">
				<p:option type="invoiceForward"/>
			</p:pro>
			
			<p:pro field="counteract" innerString="readonly=true">
				<p:option type="invoiceCounteract"/>
			</p:pro>

			<p:pro field="val"  />

			<p:pro field="description" cell="0" innerString="rows=3 cols=55" />

		</p:table>
	</p:subBody>

	<p:line flag="1" />

	<p:button leftWidth="100%" rightWidth="0%">
		<div align="right"><input type="button" class="button_class" id="ok_b"
			style="cursor: pointer" value="&nbsp;&nbsp;确 定&nbsp;&nbsp;"
			onclick="addBean()"></div>
	</p:button>

	<p:message/>
</p:body></form>
</body>
</html>

