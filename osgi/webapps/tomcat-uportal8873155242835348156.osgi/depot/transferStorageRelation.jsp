<%@ page contentType="text/html;charset=UTF-8" language="java"
	errorPage="../common/error.jsp"%>
<%@include file="../common/common.jsp"%>
<html>
<head>
<p:link title="产品转移" />
<script language="JavaScript" src="../js/common.js"></script>
<script language="JavaScript" src="../js/JCheck.js"></script>
<script language="JavaScript" src="../js/public.js"></script>
<script language="JavaScript" src="../js/string.js"></script>
<script language="javascript">

function load()
{
	loadForm();
}


function changes()
{
	if ($$('dirStorage') == '${bean.id}')
	{
		alert('目的储位不能是源储位');
		return false;
	}
	
	fromSelect = $O('productName');

	$O('productId').value = '';

	for (i = fromSelect.options.length-1; i >= 0; i--)
	{
		var opp = fromSelect.options[i];
		if (opp.selected == true)
		{
			$O('productId').value += opp.value + '#';
		}
	}

	if ($O('productId').value == '')
	{
		alert('请选择需要转移储位的产品');
		return false;
	}


	submit('确定转移产品到--' + getOptionText($O('dirStorage')));
}

function query()
{
    $l('../depot/storage.do?method=preForFindStorageToTransfer&id=${bean.id}&pname=' + $$('pname'));
}

</script>

</head>
<body class="body_class" onload="load()">
<form name="formEntry" action="../depot/storage.do" method="post">
<input type="hidden" name="id" value="${bean.id}"> <input
	type="hidden" name="productId" value=""> <input type="hidden"
	name="depotpartId" value="${bean.depotpartId}"> <input
	type="hidden" name="method" value='transferStorageRelation'> 
<p:navigation height="22">
	<td width="550" class="navigation"><span style="cursor: pointer;"
		onclick="javascript:history.go(-1)">储位管理</span> &gt;&gt; 产品转移</td>
	<td width="85"></td>
</p:navigation> <br>

<p:body width="98%">

	<p:title>
		<td class="caption"><strong>储位信息：</strong></td>
	</p:title>

	<p:line flag="0" />

	<p:subBody width="75%">

		<p:class value="com.china.center.oa.product.bean.StorageBean" />

		<p:table cells="1">

			<p:pro field="name" innerString="readonly=true" value="${bean.name}" />

			<p:cell title="目的储位">
				<select class="select_class" name="dirStorage" oncheck="notNone;"
					head='目的储位'>
					<option value="">--</option>
					<c:forEach items="${list}" var="item">
						<option value="${item.id}">${item.name}</option>
					</c:forEach>
				</select>
				<font color="#FF0000">*</font>
			</p:cell>
			
			<p:cell title="产品名称">
                <input type="text" name="pname" value="${pname}"/>&nbsp;&nbsp;
                <input type="button" class="button_class" style="cursor: pointer" value="&nbsp;查询&nbsp;" onclick="query()">
            </p:cell>

			<p:cell title="库存产品">
				<select multiple="multiple" size=12 style='width: 720px' name="productName">
				<c:forEach items="${relations}" var="item">
					<option value="${item.id}" amount="${item.amount}">${item.productName}</option>
				</c:forEach>
				</select>
			</p:cell>

		</p:table>
	</p:subBody>

	<p:line flag="1" />

	<p:button leftWidth="87%" rightWidth="13%">
		<div align="right"><input type="button" class="button_class"
			style="cursor: pointer" value="&nbsp;产品转移储位&nbsp;"
			onclick="changes()">&nbsp;&nbsp; <input type="button"
			class="button_class" onclick="javascript:history.go(-1)"
			value="&nbsp;&nbsp;返 回&nbsp;&nbsp;"></div>
	</p:button>

</p:body></form>
</body>
</html>

