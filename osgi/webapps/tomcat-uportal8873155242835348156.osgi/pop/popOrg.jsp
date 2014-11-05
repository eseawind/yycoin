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

var currentId = null;

var currentName = null;

var currentLevel = null;

var selectEle;

treeview.prototype.onnodeclick = function(me)
{
	selectEle = me;
	
    currentId = me.id;
    
    currentName = me.sname;
    
    currentLevel = me.subLevel;
    
    return false;
}

var tv = new treeview("treeview","../js/tree",false, false);

var opener;

function sure()
{
    opener = window.common.opener();

    if (currentId == null)
    {
        alert('请选择组织');
        return;
    }
    
    if (selectEle.parent.id != '2' && ('${addLocation}' == '1'))
    {
        alert('请选择大区下的三级组织');
        return;
    }

    opener.setOrgFromPop(currentId ,currentName, currentLevel, selectEle.parent.sname);

    closes();
}

function closes()
{
    opener = null;
    window.close();
}

</script>

</head>
<body class="body_class" onload="load()">
<form><p:navigation height="22">
	<td width="550" class="navigation"><span style="cursor: pointer;">组织管理</span></td>
	<td width="85"></td>
</p:navigation> <br>

<p:body width="100%">

	<p:title>
		<td class="caption"><strong>组织(岗位)结构：</strong></td>
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
			value="&nbsp;&nbsp;确 定&nbsp;&nbsp;" onclick="sure()"></div>
	</p:button>

	<p:message />
</p:body></form>
</body>
</html>

