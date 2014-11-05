<%@ page contentType="text/html;charset=UTF-8" language="java"
	errorPage="../common/error.jsp"%>
<%@include file="../common/common.jsp"%>
<html>
<head>
<p:link title="科目列表" />
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
        alert('请选择科目');
        return;
    }
    
    if (oo)
    opener.getTax(oo);
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

function pop(obj)
{
	window.open(obj.href);
}

</script>

</head>
<body class="body_class" onload="load()">
<form name="formEntry" action="../tax/tax.do" method="post"><input
	type="hidden" name="method" value="${rptMethod}"><input
	type="hidden" value="1" name="load">
<input type="hidden" value="${selectMode}" name="selectMode"> 
<input type="hidden" value="${pid}" name="pid"> 
<p:navigation
	height="22">
	<td width="550" class="navigation">科目管理</td>
	<td width="85"></td>
</p:navigation> <br>

<p:body width="100%">
	<p:subBody width="90%">
		<table width="100%" align="center" cellspacing='1' class="table0"
			id="result">
			<tr class="content1">
				<td width="15%" align="center">科目名称</td>
				<td align="center"><input type="text" name="name" onkeypress="press()"
					value="${name}"></td>
				<td width="15%" align="center">科目编码</td>
				<td align="center"><input type="text" name="code" onkeypress="press()"
					value="${code}"></td>
			</tr>

			<tr class="content1">
			    <td width="15%" align="center">节点</td>
                <td align="center">
                <select name="bottomFlag" class="select_class" values="${bottomFlag}">
                            <option value="">--</option>
                            <p:option type="taxBottomFlag"/>
                        </select>
                </td>
				<td colspan="2" align="right"><input type="button"
					onclick="querys()" class="button_class"
					value="&nbsp;&nbsp;查 询&nbsp;&nbsp;"></td>
		</table>

	</p:subBody>

	<p:title>
		<td class="caption"><strong>科目列表：</strong></td>
	</p:title>

	<p:line flag="0" />

	<p:subBody width="90%">
		<table width="100%" align="center" cellspacing='1' class="table0"
			id="result">
			<tr align=center class="content0">
				<td align="center">选择</td>
				<td align="center" class="td_class"><strong>名称</strong></td>
				<td align="center" class="td_class"><strong>编码</strong></td>
				<td align="center" class="td_class"><strong>辅助</strong></td>
				<td align="center" class="td_class"><strong>节点</strong></td>
				<td align="center" class="td_class"><strong>级别</strong></td>
			</tr>

			<c:forEach items="${beanList}" var="item" varStatus="vs">
				<tr class="${vs.index % 2 == 0 ? 'content1' : 'content2'}">
					<td align="center"><input type='${selectMode == 1 ? "radio" : "checkbox"}' name="beans"
						pname="${item.name}" 
						pbottomflag="${item.bottomFlag}" 
						pcode="${item.code}" value="${item.id}"/></td>
					<td align="center" onclick="hrefAndSelect(this)">${item.name}</td>
					<td align="center" onclick="hrefAndSelect(this)">${item.code}</td>
					<td align="center" onclick="hrefAndSelect(this)">${item.other}</td>
					<td align="center" onclick="hrefAndSelect(this)">${my:get('taxBottomFlag', item.bottomFlag)}</td>
					<td align="center" onclick="hrefAndSelect(this)">第${item.level + 1}级</td>
				</tr>
			</c:forEach>
		</table>
			
		<p:formTurning form="formEntry" method="${rptMethod}"></p:formTurning>

	</p:subBody>

	<p:line flag="1" />

	<p:button leftWidth="100%" rightWidth="0%">
		<div align="right">
        <input type="button" class="button_class" id="sure1"
            value="&nbsp;&nbsp;确 定&nbsp;&nbsp;" onClick="sures()" id="sures">&nbsp;&nbsp;<input type="button" class="button_class"
            value="&nbsp;&nbsp;关 闭&nbsp;&nbsp;" onClick="closesd()" id="clo">&nbsp;&nbsp;
		</div>
	</p:button>

	<p:message />

</p:body></form>
</body>
</html>

