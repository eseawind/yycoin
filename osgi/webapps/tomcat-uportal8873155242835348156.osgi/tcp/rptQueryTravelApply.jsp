<%@ page contentType="text/html;charset=UTF-8" language="java"
	errorPage="../common/error.jsp"%>
<%@include file="../common/common.jsp"%>
<html>
<head>
<p:link title="申请列表" />
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
        alert('请选择申请');
        return;
    }
    
    if (oo)
    opener.getTravelApply(oo);
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
<form name="formEntry" action="../tcp/apply.do" method="post"><input
	type="hidden" name="method" value="${cacheQueryKey}">
<input type="hidden" value="1" name="load">
<input type="hidden" value="${type}" name="type">
<input type="hidden" value="${stype}" name="stype">
<input type="hidden" value="${selectMode}" name="selectMode"> 

<p:navigation
	height="22">
	<td width="550" class="navigation">借款申请管理</td>
	<td width="85"></td>
</p:navigation> <br>

<p:body width="100%">
	<p:subBody width="90%">
		<table width="100%" align="center" cellspacing='1' class="table0"
			id="result">
			<tr class="content1">
				<td width="15%" align="center">目的</td>
				<td align="center"><input type="text" name="name" onkeypress="press()"
					value="${name}"></td>
				<td width="15%" align="center">标识</td>
				<td align="center"><input type="text" name="id" onkeypress="press()"
					value="${id}"></td>
			</tr>

			<tr class="content1">
				<td colspan="4" align="right"><input type="button"
					onclick="querys()" class="button_class"
					value="&nbsp;&nbsp;查 询&nbsp;&nbsp;"></td>
		</table>

	</p:subBody>

	<p:title>
		<td class="caption"><strong>申请列表：</strong></td>
	</p:title>

	<p:line flag="0" />

	<p:subBody width="90%">
		<table width="100%" align="center" cellspacing='1' class="table0"
			id="result">
			<tr align=center class="content0">
				<td align="center">选择</td>
				<td align="center"><strong>标识</strong></td>
				<td align="center"><strong>名称</strong></td>
				<td align="center"><strong>是否借款</strong></td>
				<td align="center"><strong>申请费用</strong></td>
				<td align="center"><strong>借款费用</strong></td>
				<td align="center"><strong>时间</strong></td>
			</tr>

			<c:forEach items="${beanList}" var="item" varStatus="vs">
				<tr class="${vs.index % 2 == 0 ? 'content1' : 'content2'}">
					<td align="center"><input type='${selectMode == 1 ? "radio" : "checkbox"}' name="beans"
					pname="${item.name}"
					pborrow="${item.borrow}"
					pbtotal="${item.showBorrowTotal}"
					${vs.index == 0 ? 'checked' : ''}
					value="${item.id}"/></td>
					<td align="center" onclick="hrefAndSelect(this)">${item.id}</td>
					<td align="center" onclick="hrefAndSelect(this)">${item.name}</td>
					<td align="center" onclick="hrefAndSelect(this)">${my:get('travelApplyBorrow', item.borrow)}</td>
					<td align="center" onclick="hrefAndSelect(this)">${item.showTotal}</td>
					<td align="center" onclick="hrefAndSelect(this)">${item.showBorrowTotal}</td>
					<td align="center" onclick="hrefAndSelect(this)">${item.logTime}</td>
				</tr>
			</c:forEach>
		</table>
			
		<p:formTurning form="formEntry" method="${cacheQueryKey}"></p:formTurning>

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

