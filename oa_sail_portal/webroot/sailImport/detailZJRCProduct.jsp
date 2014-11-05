<%@ page contentType="text/html;charset=UTF-8" language="java"
	errorPage="../common/error.jsp"%>
<%@include file="../common/common.jsp"%>
<html>
<head>
<p:link title="增加产品配置" />
<script language="JavaScript" src="../js/JCheck.js"></script>
<script language="JavaScript" src="../js/common.js"></script>
<script language="JavaScript" src="../js/public.js"></script>
<script language="JavaScript" src="../js/math.js"></script>
<script language="JavaScript" src="../js/key.js"></script>
<script language="javascript">
function addBean()
{
	submit('确定增加产品配置?', null, null);
}

function selectProduct()
{
    window.common.modal('../product/product.do?method=rptQueryProduct&load=1&selectMode=1');
}

function getProduct(oos)
{
    var obj = oos[0];
    
    $O('productName').value = obj.pname;   
    $O('productId').value = obj.value;   
}

function clears()
{
    $O('productId').value = '';
    $O('productName').value = '';
}

function goback()
{
	document.location.href = '../sailImport/queryZJRCProduct.jsp';
}

</script>

</head>
<body class="body_class">
<form name="formEntry" action="../sail/extout.do" method="post">
<input type="hidden" name="method" value="updateZJRCProduct">
<input type="hidden" name="id" value="${bean.id}">
<input type="hidden" name="productId" value="${bean.productId}">

<p:navigation
	height="22">
	<td width="550" class="navigation"><span style="cursor: pointer;"
		onclick="javascript:history.go(-1)">产品配置管理</span> &gt;&gt; 增加产品配置</td>
	<td width="85"></td>
	
</p:navigation> <br>

<p:body width="98%">

	<p:title>
		<td class="caption"><strong>产品配置基本信息：</strong></td>
	</p:title>

	<p:line flag="0" />

	<p:subBody width="100%">
		<p:class value="com.china.center.oa.extsail.bean.ZJRCProductBean" opr="2"/>

		<p:table cells="1">
		    
			<p:pro field="productId" innerString="size=60" value="${bean.productName}">
			</p:pro>
			
			<p:pro field="zjrProductName" innerString="size=60"/>
			
			<p:pro field="price" innerString="size=60 oncheck='isFloat'"/>
			
			<p:pro field="costPrice" innerString="size=60 oncheck='isFloat'"/>
			
			<p:pro field="midRevenue" innerString="size=60 oncheck='isFloat'"/>
			
			<p:pro field="stafferName" />
			
			<p:pro field="logTime" />
			
		</p:table>
	</p:subBody>

	<p:line flag="1" />

	<p:button leftWidth="100%" rightWidth="0%">
		<div align="right"><input type="button" name="ba" class="button_class"
            onclick="goback()"
            value="&nbsp;&nbsp;返 回&nbsp;&nbsp;"></div>
	</p:button>

	<p:message2/>
</p:body></form>
</body>
</html>

