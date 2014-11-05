<%@ page contentType="text/html;charset=UTF-8" language="java"
	errorPage="../common/error.jsp"%>
<%@include file="../common/common.jsp"%>
<html>
<head>
<p:link title="仓区产品移动明细" />
<script language="JavaScript" src="../js/common.js"></script>
<script language="JavaScript" src="../js/cnchina.js"></script>
<script language="JavaScript" src="../js/public.js"></script>
<script language="JavaScript" src="../js/JCheck.js"></script>
<script language="JavaScript" src="../js/key.js"></script>
<script language="javascript">

function load()
{
    loadForm();
    
	setAllReadOnly();
}


</script>

</head>
<body class="body_class" onload="load()">
<form name="formEntry" action="../provider/provider.do" method="post"><input
	type="hidden" name="method" value=""> <input
    type="hidden" name="id" value="${bean.id}"><p:navigation
	height="22">
	<td width="550" class="navigation">仓区产品移动明细</td>
	<td width="85"></td>
</p:navigation> <br>
<p:body width="100%">
	<p:subBody width="90%">
		<table width="100%" align="center" cellspacing='1' class="table0"
			id="result">
			<tr class="content1">
				<td width="15%" align="center">流水编号</td>
				<td align="center">${flowid}</td>
				<td width="15%" align="center">原仓区</td>
				<td align="center"></td>
					<td width="15%" align="center">产品名称</td>
				<td align="center"><input type="text" name="name" onkeypress="press()"
					value="${name}"></td>
			</tr>
			
			<tr class="content2">
                <td width="15%" align="center">储位</td>
                <td align="center"><input type="text" name="storageName" onkeypress="press()"
                    value="${storageName}"></td>
                <td width="15%" align="center">仓区</td>
                <td align="center"><input type="text" name="depotpartName" onkeypress="press()"
                    value="${depotpartName}"></td>
            </tr>

			<tr class="content1">
				<td colspan="4" align="right"><input type="button"
					onclick="querys()" class="button_class"
					value="&nbsp;&nbsp;查 询&nbsp;&nbsp;"></td>
		</table>

	</p:subBody>

	<p:line flag="1" />

	<p:button leftWidth="100%" rightWidth="0%">
		<div align="right"><input type="button" class="button_class"
            style="cursor: pointer" value="&nbsp;&nbsp;返 回&nbsp;&nbsp;"
            onclick="javascript:history.go(-1)"></div>
	</p:button>

	<p:message />
</p:body></form>
</body>
</html>

