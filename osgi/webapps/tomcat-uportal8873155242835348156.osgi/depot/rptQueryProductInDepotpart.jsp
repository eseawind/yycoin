<%@ page contentType="text/html;charset=UTF-8" language="java"
	errorPage="../common/error.jsp"%>
<%@include file="../common/common.jsp"%>
<html>
<head>
<p:link title="产品库存列表" />
<base target="_self">
<script language="JavaScript" src="../js/common.js"></script>
<script language="JavaScript" src="../js/public.js"></script>
<script language="javascript">
function querys()
{
	formEntry.submit();
}

function sures()
{
	add();
    
    closes();
}

function add()
{
    var opener = window.common.opener();
    
    var oo = getCheckBox("beans");
    
    if (oo && oo.length == 0)
    {
        alert('请选择产品库存');
        return;
    }
    
    if (oo)
    opener.getProductRelation(oo);
}

function closes()
{
	opener = null;
	window.close();
}

function load()
{
	loadForm();
}

function press()
{
    window.common.enter(querys);
} 

function closesd()
{
    var opener = window.common.opener();
    
    opener = null;
    window.close();
}

</script>

</head>
<body class="body_class" onload="load()">
<form name="formEntry" action="../depot/storage.do" method="post"><input
	type="hidden" name="method" value="rptQueryProductInDepotpart"><input
	type="hidden" value="1" name="load">
<input type="hidden" value="${depotpartId}" name="depotpartId">
<input type="hidden" value="${stafferId}" name="stafferId">
<input type="hidden" value="${selectMode}" name="selectMode">
<p:navigation
	height="22">
	<td width="550" class="navigation">产品库存管理</td>
	<td width="85"></td>
</p:navigation> <br>

<p:body width="100%">
	<p:subBody width="90%">
		<table width="100%" align="center" cellspacing='1' class="table0"
			id="result">
			<tr class="content1">
				<td width="15%" align="center">产品名称</td>
				<td align="center"><input type="text" name="name" onkeypress="press()"
					value="${name}"></td>
				<td width="15%" align="center">产品编码</td>
				<td align="center"><input type="text" name="code" onkeypress="press()"
					value="${code}"></td>
			</tr>
			
			<tr class="content2">
                <td width="15%" align="center">储位</td>
                <td align="center"><input type="text" name="storageName" onkeypress="press()"
                    value="${storageName}"></td>
                <td width="15%" align="center"></td>
                <td align="center"></td>
            </tr>

			<tr class="content1">
				<td colspan="4" align="right"><input type="button"
					onclick="querys()" class="button_class"
					value="&nbsp;&nbsp;查 询&nbsp;&nbsp;"></td>
		</table>

	</p:subBody>

	<p:title>
		<td class="caption"><strong>库存列表：</strong></td>
	</p:title>

	<p:line flag="0" />

	<p:subBody width="90%">
		<table width="100%" align="center" cellspacing='1' class="table0"
			id="result">
			<tr align=center class="content0">
				<td align="center">选择</td>
				<td align="center"><strong>仓区</strong></td>
				<td align="center"><strong>储位</strong></td>
				<td align="center"><strong>产品</strong></td>
				<td align="center"><strong>可转数量</strong></td>
				<td align="center"><strong>预占数量</strong></td>
				<td align="center"><strong>价格</strong></td>
				<td align="center"><strong>职员</strong></td>
			</tr>

			<c:forEach items="${beanList}" var="item" varStatus="vs">
				<tr class="${vs.index % 2 == 0 ? 'content1' : 'content2'}">
					<td align="center"><input type='${selectMode == 1 ? "radio" : "checkbox"}' name="beans" ppid="${item.productId}"
						pname="${item.productName}" pprice="${my:formatNum(item.price)}" pamount="${item.amount}" 
						value="${item.id}"/></td>
					<td align="center" onclick="hrefAndSelect(this)">${item.depotpartName}</td>
					<td align="center" onclick="hrefAndSelect(this)">${item.storageName}</td>
					<td align="center" onclick="hrefAndSelect(this)">${item.productName}(${item.productCode})</td>
					<td align="center" onclick="hrefAndSelect(this)">${item.amount}</td>
					<td align="center" onclick="hrefAndSelect(this)">${item.mayAmount}</td>
					<td align="center" onclick="hrefAndSelect(this)">${my:formatNum(item.price)}</td>
					<td align="center" onclick="hrefAndSelect(this)">${item.stafferName}</td>
				</tr>
			</c:forEach>
		</table>
			
		<p:formTurning form="formEntry" method="rptQueryProductInDepotpart"></p:formTurning>

	</p:subBody>

	<p:line flag="1" />

	<p:button leftWidth="100%" rightWidth="0%">
		<div align="right">
		<c:if test="${selectMode != 1}">
		<input type="button" class="button_class" id="adds"
            value="&nbsp;&nbsp;选 择&nbsp;&nbsp;" onClick="add()">&nbsp;&nbsp;
        </c:if>
        <input type="button" class="button_class" id="sure1"
            value="&nbsp;&nbsp;确 定&nbsp;&nbsp;" onClick="sures()" id="sures">&nbsp;&nbsp;<input type="button" class="button_class"
            value="&nbsp;&nbsp;关 闭&nbsp;&nbsp;" onClick="closesd()" id="clo">&nbsp;&nbsp;
		</div>
	</p:button>

	<p:message2 />

</p:body></form>
</body>
</html>

