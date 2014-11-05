<%@ page contentType="text/html;charset=UTF-8" language="java"
	errorPage="../common/error.jsp"%>
<%@include file="../common/common.jsp"%>
<html>
<head>
<base target="_self">
<p:link title="外网询价查询" />
<script src="../js/public.js"></script>
<script src="../js/tableSort.js"></script>
<script src="../js/common.js"></script>
<script language="javascript">

function sure()
{
	var opener = window.common.opener();

	var oo = getRadio("processers");

	if (oo == null)
	{
		alert('请选择外网询价单');
		return;
	}

    opener.getPriceAskProvider(oo);

    closes();
}

function closes()
{
	opener = null;
	window.close();
}

function query()
{
	adminForm.submit();

}

</script>
<body class="body_class" onload="loadForm()">
<form>
<input type="hidden" value="l" name="load">

<p:navigation
	height="22">
	<td width="550" class="navigation">外网询价列表</td>
	<td width="85"></td>
</p:navigation> <br>

<p:body width="100%">
	<p:title>
		<td class="caption"><strong>产品列表：</strong></td>
	</p:title>

	<p:line flag="0" />
	
	<p:subBody width="90%">
		<table width="100%" border="0" cellpadding="0" cellspacing="0"
			class="border">
			<tr>
				<td>
				<table width="100%" border="0" cellspacing='1'>
					<tr align="center" class="content0">
						<td align="center" width="8%" align="center">选择</td>
						<td align="center" onclick="tableSort(this)" class="td_class">时间</td>
						<td align="center" onclick="tableSort(this)" class="td_class">类型</td>
						<td align="center" onclick="tableSort(this)" class="td_class">询价人</td>
						<td align="center" onclick="tableSort(this)" class="td_class">供应商</td>
						<td align="center" onclick="tableSort(this)" class="td_class">产品名称</td>
						<td align="center" onclick="tableSort(this)" class="td_class">产品编码</td>
						<td align="center" onclick="tableSort(this)" class="td_class">数量</td>
						<td align="center" onclick="tableSort(this)" class="td_class">剩余数量</td>
						<td align="center" onclick="tableSort(this)" class="td_class">价格</td>
						<td align="center" onclick="tableSort(this)" class="td_class">描述</td>
					</tr>

					<c:forEach items="${beanList}" var="item" varStatus="vs">
						<tr class='${vs.index % 2 == 0 ? "content1" : "content2"}'>
							<td align="center"><input type="radio" name="processers" ppid="${item.pid}" 
							     pprovideriid="${item.providerId}"
							     pstafferid="${item.stafferId}"
							     pprovidername="${item.providerName}" 
							     pamount="${item.remainmount}"
								 pn="${item.productName}" pp="${my:formatNum(item.price)}" paskid="${item.askId}" value="${item.productId}" /></td>
							<td align="center" onclick="hrefAndSelect(this)">${item.logTime}</td>
							<td align="center" onclick="hrefAndSelect(this)">${my:get('priceAskSrcType', item.srcType)}</td>
							<td align="center" onclick="hrefAndSelect(this)">${item.stafferName}</td>
							<td align="center" onclick="hrefAndSelect(this)">${item.providerName}</td>
							<td align="center" onclick="hrefAndSelect(this)">${item.productName}</td>
							<td align="center" onclick="hrefAndSelect(this)">${item.productCode}</td>
							<td align="center" onclick="hrefAndSelect(this)">${item.amount}</td>
							<td align="center" onclick="hrefAndSelect(this)">${item.remainmount}</td>
							<td align="center" onclick="hrefAndSelect(this)">${my:formatNum(item.price)}</td>
							<td align="center" onclick="hrefAndSelect(this)">${item.description}</td>
						</tr>
					</c:forEach>
				</table>

				</td>
			</tr>
		</table>
	</p:subBody>
	
	<p:line flag="1" />
	
	<p:button leftWidth="100%" rightWidth="0%">
		<div align="right"><input type="button" class="button_class"
			value="&nbsp;&nbsp;确 定&nbsp;&nbsp;" onClick="sure()">&nbsp;&nbsp;<input
			type="button" class="button_class"
			value="&nbsp;&nbsp;关 闭&nbsp;&nbsp;" onClick="closes()">&nbsp;&nbsp;</div>
	</p:button>
	
</p:body>
</form>
</body>
</html>
