<%@ page contentType="text/html;charset=UTF-8" language="java"
	errorPage="../common/error.jsp"%>
<%@include file="../common/common.jsp"%>
<html>
<head>
<p:link title="库存转移" />
<script language="JavaScript" src="../js/JCheck.js"></script>
<script language="JavaScript" src="../js/common.js"></script>
<script language="JavaScript" src="../js/public.js"></script>
<script language="javascript">
function addBean()
{
	submit('确定申请转移库存?');
}

function selectStaffer()
{
    window.common.modal('../admin/pop.do?method=rptQueryStaffer&load=1&selectMode=1');
}

function getStaffers(oos)
{
	var oo = oos[0];
	
    $O('reveiverName').value = oo.pname;
    $O('reveiver').value = oo.value;
}

</script>

</head>
<body class="body_class">
<form name="addApply" action="../depot/storage.do" method="post">
<input type="hidden" name="method" value="addStorageApply"/>
<input type="hidden" name="storageRelationId" value="${bean.id}"/>
<input type="hidden" name="reveiver" value=""/>
<p:navigation
	height="22">
	<td width="550" class="navigation"><span style="cursor: pointer;"
		onclick="javascript:history.go(-1)">仓区管理</span> &gt;&gt; 库存转移</td>
	<td width="85"></td>
</p:navigation> <br>

<p:body width="98%">

	<p:title>
		<td class="caption"><strong>库存基本信息：</strong></td>
	</p:title>

	<p:line flag="0" />

	<p:subBody width="100%">
		<p:class value="com.china.center.oa.product.bean.StorageApplyBean" />

		<p:table cells="1">

			<p:cell title="产品">${bean.productName}</p:cell>
			<p:cell title="储位数量">${bean.amount}</p:cell>
			<p:cell title="价格">${my:formatNum(bean.price)}</p:cell>
			<p:cell title="仓库">${bean.locationName}</p:cell>
			<p:cell title="仓区">${bean.depotpartName}</p:cell>
			<p:cell title="所属">${bean.stafferName}</p:cell>
			
			<p:cell title="接收人">
			<input type="text" name="reveiverName" readonly="readonly" style="width: 240px" oncheck="notNone;" head="接收人"/>
			 <input type="button" value="&nbsp;...&nbsp;" name="qout" id="qout"
                    class="button_class" onclick="selectStaffer()">&nbsp;&nbsp; 
			</p:cell>
			
			<p:pro field="amount"/>

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

