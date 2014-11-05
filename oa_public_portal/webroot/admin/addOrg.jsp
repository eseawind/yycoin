<%@ page contentType="text/html;charset=UTF-8" language="java"
	errorPage="../common/error.jsp"%>
<%@include file="../common/common.jsp"%>
<html>
<head>
<p:link title="增加组织" />
<script language="JavaScript" src="../js/common.js"></script>
<script language="JavaScript" src="../js/public.js"></script>
<script language="JavaScript" src="../js/JCheck.js"></script>
<script language="JavaScript" src="../js/tree.js"></script>
<script language="JavaScript" src="../js/json.js"></script>
<script language="JavaScript" src="../admin_js/org.js"></script>
<script language="javascript">

var shipList = JSON.parse('${shipList}');

var shipMap = JSON.parse('${mapJSON}');

var levelMap = {};

function addBean()
{
    submit('确定增加组织到组织结构?', null, checkBean);
}

function checkBean()
{
    var checks = document.getElementsByTagName('input');
    
    var count = 0;
    
    var id;
    for (var i = 0; i < checks.length; i++)
    {
        var ele = checks[i];
        if (ele.type == 'checkbox' && ele.checked)
        {
            count ++;
            
            id = ele.value;
        }
    }
    
    if (count == 0)
    {
        alert('请选择上级组织');
        return false;
    }
    
    if (count != 1)
    {
        alert('只能选择一个组织');
        return false;
    }
    
    return true;
}


var tv = new treeview("treeview","../js/tree", 1, false);

</script>

</head>
<body class="body_class" onload="load()">
<form name="addApply" action="../admin/org.do"><input
	type="hidden" name="method" value="addOrg"> <p:navigation
	height="22">
	<td width="550" class="navigation"><span style="cursor: pointer;"
		onclick="javascript:history.go(-1)">组织管理</span> &gt;&gt; 增加组织</td>
	<td width="85"></td>
</p:navigation> <br>

<p:body width="100%">

	<p:title>
		<td class="caption"><strong>组织基本信息：</strong></td>
	</p:title>

	<p:line flag="0" />

	<p:subBody width="98%">
		<p:class value="com.china.center.oa.publics.bean.PrincipalshipBean" />

		<p:table cells="1">
			<p:pro field="name" />

			<p:cell title="上级组织">
			    <span style="cursor: pointer;" onclick="allSelect(true)">全部展开</span> | <span
                    style="cursor: pointer;" onclick="allSelect(false)">全部收起</span>
                <br>
                <br>
				<div id=tree></div>
			</p:cell>

		</p:table>
	</p:subBody>

	<p:line flag="1" />

	<p:button leftWidth="100%" rightWidth="0%">
		<div align="right"><input type="button" class="button_class" id="ok_b"
			style="cursor: pointer" value="&nbsp;&nbsp;确 定&nbsp;&nbsp;"
			onclick="addBean()"></div>
	</p:button>

	<p:message />
</p:body></form>
</body>
</html>

