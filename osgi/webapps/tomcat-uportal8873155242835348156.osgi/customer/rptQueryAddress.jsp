<%@ page contentType="text/html;charset=UTF-8" language="java"
	errorPage="../common/error.jsp"%>
<%@include file="../common/common.jsp"%>
<html>
<head>
<p:link title="客户收货地址列表" />
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
        alert('请选择地址');
        return;
    }
    
    if (oo)
    	opener.getAddress(oo);
}

function closes()
{
	var opener = window.common.opener();

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
<form name="formEntry" action="../customer/address.do" method="post"><input
	type="hidden" name="method" value="rptQueryAddress"><input
	type="hidden" value="1" name="load">
<input type="hidden" value="${selectMode}" name="selectMode"> 
<input type=hidden value="${customerId}" name="customerId">

<p:navigation
	height="22">
	<td width="550" class="navigation">客户收货地址</td>
	<td width="85"></td>
</p:navigation> <br>

<p:body width="100%"><!--
	<p:subBody width="90%">
		<table width="100%" align="center" cellspacing='1' class="table0"
			id="result">
			<tr class="content1">
				<td width="15%" align="center">客户</td>
				<td align="center"><input type="text" name="name" onkeypress="press()"
					value="${name}"></td>
				<td width="15%" align="center"></td>
				<td align="center"></td>
			</tr>

			<tr class="content1">
				<td colspan="4" align="right"><input type="button"
					onclick="querys()" class="button_class"
					value="&nbsp;&nbsp;查 询&nbsp;&nbsp;"></td>
		</table>

	</p:subBody>

	--><p:title>
		<td class="caption"><strong>客户收货地址列表：</strong></td>
	</p:title>

	<p:line flag="0" />

	<p:subBody width="90%">
		<table width="100%" align="center" cellspacing='1' class="table0"
			id="result">
			<tr align=center class="content0">
				<td align="center" width="5%">选择</td>
				<td align="center"  width="25%">名称</td>
				<td align="center"  width="10%">省</td>
				<td align="center"  width="10%">市</td>
				<td align="center"  width="10%">区</td>
				<td align="center"  width="20%">地址</td>
				<td align="center"  width="15%">接收人</td>
				<td align="center"  width="15%">手机</td>
			</tr>

			<c:forEach items="${beanList}" var="item" varStatus="vs">
				<tr class="${vs.index % 2 == 0 ? 'content1' : 'content2'}">
					<td align="center"><input type='${selectMode == 1 ? "radio" : "checkbox"}' name="beans"					
					${vs.index == 0 ? 'checked' : ''} 
					pprovinceid="${item.provinceId}" pcityid="${item.cityId}" pareaid="${item.areaId}" paddress="${item.address}"  preceiver="${item.receiver}" 
					pmobile="${item.mobile}" ptelephone="${item.telephone}"
					value="${item.id}"/></td>
					<td align="center" onclick="hrefAndSelect(this)">${item.customerName}</td>
					<td align="center" onclick="hrefAndSelect(this)">${item.provinceName}</td>
					<td align="center" onclick="hrefAndSelect(this)">${item.cityName}</td>
					<td align="center" onclick="hrefAndSelect(this)">${item.areaName}</td>
					<td align="center" onclick="hrefAndSelect(this)">${item.address}</td>
					<td align="center" onclick="hrefAndSelect(this)">${item.receiver}</td>
					<td align="center" onclick="hrefAndSelect(this)">${item.mobile}</td>
				</tr>
			</c:forEach>
		</table>
			
		<p:formTurning form="formEntry" method="rptQueryAddress"></p:formTurning>

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

	<p:message />

</p:body></form>
</body>
</html>

