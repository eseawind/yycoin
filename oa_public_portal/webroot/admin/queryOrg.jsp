<%@ page contentType="text/html;charset=UTF-8" language="java"
	errorPage="../common/error.jsp"%>
<%@include file="../common/common.jsp"%>
<html>
<head>
<p:link title="组织管理" />
<script language="JavaScript" src="../js/common.js"></script>
<script language="JavaScript" src="../js/public.js"></script>
<script language="JavaScript" src="../js/tree.js"></script>
<script language="JavaScript" src="../js/json.js"></script>
<script language="JavaScript" src="../js/pop.js"></script>
<script language="JavaScript" src="../admin_js/org.js"></script>
<script language="javascript">

var shipList = JSON.parse('${shipList}');

var shipMap = JSON.parse('${mapJSON}');

function addBean()
{
	$l('../admin/org.do?method=preForAddOrg');
}

var currentId = null;

treeview.prototype.onnodeclick = function(me)
{
    currentId = me.id;
    
    return false;
}

function checkBean()
{
    if (currentId == null)
    {
        alert('请选择组织');
        return '';
    }
    
    if (currentId == '0')
    {
        alert('董事会不能操作');
        return '';
    }
    
    return currentId;
}

function updateBean()
{
    var id = checkBean();
    
    if (id != '')
    {
        $l('../admin/org.do?method=findOrg&update=1&id=' + id);
    }
}

function delBean()
{
    var id = checkBean();
    
    if (id != '')
    {
        if (window.confirm('确定删除组织?'))
        {
            $l('../admin/org.do?method=delOrg&id=' + id);
        }
    }
}

var tv = new treeview("treeview","../js/tree",false, false);

</script>

</head>
<body class="body_class" onload="load(true)">
<form name="addApply"><p:navigation height="22">
	<td width="550" class="navigation"><span style="cursor: pointer;">组织管理</span></td>
	<td width="85"></td>
</p:navigation> <br>

<p:body width="100%">

	<p:title>
		<td class="caption"><strong>组织(组织)结构：</strong></td>
	</p:title>

	<p:line flag="0" />

	<p:subBody width="80%">
		<p:table cells="1">
			<p:cells celspan="0">
				<span style="cursor: pointer;" onclick="allSelect(true)">全部展开</span> | <span
					style="cursor: pointer;" onclick="allSelect(false)">全部收起</span>
				<br>
				<br>
				<div id=tree align="left"></div>
			</p:cells>

		</p:table>
	</p:subBody>

	<p:line flag="1" />

	<p:button leftWidth="100%" rightWidth="0%">
		<div align="right"><input type="button" class="button_class"
			id="ok_b" style="cursor: pointer"
			value="&nbsp;&nbsp;增加组织&nbsp;&nbsp;" onclick="addBean()">&nbsp;&nbsp;<input
			type="button" class="button_class" id="update_b"
			style="cursor: pointer" value="&nbsp;&nbsp;修改组织&nbsp;&nbsp;"
			onclick="updateBean()">&nbsp;&nbsp;<input type="button"
			class="button_class" id="del_b" style="cursor: pointer"
			value="&nbsp;&nbsp;删除组织&nbsp;&nbsp;" onclick="delBean()"></div>
	</p:button>

	<p:message />
</p:body></form>
</body>
</html>

