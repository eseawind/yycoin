<%@ page contentType="text/html;charset=UTF-8" language="java"
	errorPage="../common/error.jsp"%>
<%@include file="../common/common.jsp"%>
<html>
<head>
<p:link title="销售单列表" />
<base target="_self">
<script language="JavaScript" src="../js/common.js"></script>
<script language="JavaScript" src="../js/public.js"></script>
<script language="javascript">
function querys()
{
	formEntry.submit();
}

var flag = true;
function sures()
{
	add();
    
    closes();
}

function add()
{
    var opener = window.common.opener();
    
    var oo = getCheckBoxs("beans");
    
    if (oo && oo.length == 0)
    {
        alert('请选择销售单');
        flag = false;
        return;
    }
    
    if (oo)
    	opener.getOrder(oo);
}

function closes()
{
	var opener = window.common.opener();

	if (!flag)
		opener.clearProm();
	
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

    opener.clearProm();
    
    opener = null;
    window.close();
}

function getCheckBoxs(name)
{
    var arr = new Array();
    var k =0;
    var obj = document.getElementsByName(name);
    for (var i = 0; i < obj.length; i++)
    {
        if (obj[i].checked)
        {
            arr[k++] = obj[i];
        }
    }

    return arr;
}

</script>

</head>
<body class="body_class" onload="load()">
<form name="formEntry" action="../sail/out.do" method="post"><input
	type="hidden" name="method" value="rptQueryHisOut"><input
	type="hidden" value="1" name="load">
<input type="hidden" value="${selectMode}" name="selectMode"> 
<input type="hidden" value="${status}" name="status"> 

<p:navigation
	height="22">
	<td width="550" class="navigation">销售管理</td>
	<td width="85"></td>
</p:navigation> <br>

<p:body width="100%">
<!-- 
	<p:subBody width="90%">
		<table width="100%" align="center" cellspacing='1' class="table0"
			id="result">
			<tr class="content1">
				<td width="15%" align="center">事业部名称</td>
				<td align="center"><input type="text" name="name" onkeypress="press()"
					value="${name}"></td>
				<td width="15%" align="center">事业部编码</td>
				<td align="center"><input type="text" name="code" onkeypress="press()"
					value="${id}"></td>
			</tr>

			<tr class="content1">
				<td colspan="4" align="right"><input type="button"
					onclick="querys()" class="button_class"
					value="&nbsp;&nbsp;查 询&nbsp;&nbsp;"></td>
		</table>

	</p:subBody>
 -->
	<p:title>
		<td class="caption"><strong>销售单列表：</strong></td>
	</p:title>

	<p:line flag="0" />

	<p:subBody width="90%">
		<table width="100%" align="center" cellspacing='1' class="table0"
			id="result">
			<tr align=center class="content0">
				<td align="center">选择</td>
				<td align="center">单据编号</td>
				<td align="center">客户</td>
				<td align="center">状态</td>
				<td align="center">金额</td>
				<td align="center">销售日期</td>
			</tr>

			<!-- ${vs.index == 0 ? 'checked' : ''} -->
			<c:forEach items="${BeanList}" var="item" varStatus="vs">
				<tr class="${vs.index % 2 == 0 ? 'content1' : 'content2'}">
					<td align="center"><input type='${selectMode == 1 ? "radio" : "checkbox"}' name="beans"					
					value="${item.fullId}"/></td>		
					<td align="left" onclick="hrefAndSelect(this)">${item.fullId}</td>
					<td align="center" onclick="hrefAndSelect(this)">${item.customerName}</td>
					<td align="center" onclick="hrefAndSelect(this)">${my:get('outStatus', item.status)}</td>
					<td align="center" onclick="hrefAndSelect(this)">${my:formatNum(item.total)}</td>
					<td align="center" onclick="hrefAndSelect(this)">${item.outTime}</td>

				</tr>
			</c:forEach>
		</table>
			
		<!--<p:formTurning form="formEntry" method="rptQueryPrincipalship"></p:formTurning> -->

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

