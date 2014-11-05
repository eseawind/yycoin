<%@ page contentType="text/html;charset=UTF-8" language="java"
	errorPage="../common/error.jsp"%>
<%@include file="../common/common.jsp"%>
<html>
<head>
<p:link title="增加紧急询价" />
<script language="JavaScript" src="../js/common.js"></script>
<script language="JavaScript" src="../js/public.js"></script>
<script language="JavaScript" src="../js/JCheck.js"></script>
<script language="javascript">
function addBean()
{
    if ($$('type_list_0') != '0' && $$('type_list_1') != '1')
    {
        alert('请选择类型');
        return;
    }
    
	submit('确定增加紧急询价?');
}

function load()
{
	init();
}

function selectProduct()
{
	//单选
	window.common.modal(RPT_PRODUCT);
}

function getProduct(oo)
{
	var obj = oo[0];
	
	$O('productId').value = obj.value;
	$O('productName').value = obj.pname;
}

function init()
{
    var ss = $O('instancy');
    
    if ($$('type_list_1') != '1')
    {
        removeAllItem(ss);
        setOption(ss, '0', '一般(2小时)');
        setOption(ss, '1', '紧急(1小时)');
        setOption(ss, '2', '非常紧急(30分钟)');
    }
    
    if ($$('type_list_1') == '1')
    {
        removeAllItem(ss);
        setOption(ss, '3', '询价(中午11:30结束)');
        setOption(ss, '4', '询价(下午15:30结束)');
    }
}
</script>

</head>
<body class="body_class" onload="load()">
<form name="addApply" action="../stock/ask.do"><input
	type="hidden" name="method" value="addPriceAsk"> <input
	type="hidden" name="productId" value=""><p:navigation
	height="22">
	<td width="550" class="navigation"><span style="cursor: hand"
		onclick="javascript:history.go(-1)">询价管理</span> &gt;&gt; 增加紧急询价</td>
	<td width="85"></td>
</p:navigation> <br>

<p:body width="100%">

	<p:title>
		<td class="caption"><strong>紧急询价信息：</strong></td>
	</p:title>

	<p:line flag="0" />

	<p:subBody width="98%">
		<p:class value="com.china.center.oa.stock.bean.PriceAskBean" />

		<p:table cells="1">
			<p:pro field="productId" innerString="size=60">&nbsp;&nbsp;<input type="button"
					value="&nbsp;...&nbsp;" name="qout" class="button_class"
					onclick="selectProduct()">
			</p:pro>

			<p:pro field="amount" />
            
            <p:cell title="询价方式">
            <input type="checkbox" name="type_list_1" value="1" onclick=init()>外网询价
            <input type="checkbox" name="type_list_0" value="0" onclick=init()>内部询价
            </p:cell>

			<p:pro field="instancy" innerString="style='width: 240px'">
				<option value="0">一般(2小时)</option>
				<option value="1">紧急(1小时)</option>
				<option value="2">非常紧急(30分钟)</option>
				<option value="3">询价(中午11:30结束)</option>
				<option value="4">询价(下午15:30结束)</option>
			</p:pro>
			
			<p:pro field="description" innerString="rows=3 cols=55"/>
			
		</p:table>
	</p:subBody>

	<p:line flag="1" />

	<p:button leftWidth="100%" rightWidth="0%">
		<div align="right"><input type="button" class="button_class"
			name="adds" style="cursor: pointer"
			value="&nbsp;&nbsp;确 定&nbsp;&nbsp;" onclick="addBean()">&nbsp;&nbsp;
		<input type="button" class="button_class"
			onclick="javascript:history.go(-1)"
			value="&nbsp;&nbsp;返 回&nbsp;&nbsp;"></div>
	</p:button>

	<p:message/>
</p:body></form>
</body>
</html>

