<%@ page contentType="text/html;charset=UTF-8" language="java"
	errorPage="../common/error.jsp"%>
<%@include file="../common/common.jsp"%>
<html>
<head>
<p:link title="增加角色" />
<script language="JavaScript" src="../js/common.js"></script>
<script language="JavaScript" src="../js/cnchina.js"></script>
<script language="JavaScript" src="../js/public.js"></script>
<script language="JavaScript" src="../js/JCheck.js"></script>
<script language="JavaScript" src="../js/tree.js"></script>
<script language="JavaScript" src="../js/json.js"></script>
<script language="javascript">

var authList = JSON.parse('${authListJSON}');

function addBean()
{
	submit('确定增加角色?');
}

var tv = new treeview("treeview","../js/tree",true,false);

var nmap = {};

function load()
{
    
    for (var i = 0; i < authList.length; i++)
    {
        var ele = authList[i];
        
        if (ele.bottomFlag == 0 && ele.level == 0)
        {
            //function snode(caption, isRoot, id, url, target)
            var itemNode = snode(ele.name, true, ele.id);
            
            
            tv.add(itemNode);
            
            nmap[ele.id] = itemNode;
        }
        else
        {
            var parentNode = nmap[ele.parentId];
            
            if (parentNode == null)
            {
                continue;
            }
            
            var itemNode;
            if (ele.bottomFlag == 0)
            {
                itemNode = snode(ele.name, true, ele.id);
                
                nmap[ele.id] = itemNode;
                
                parentNode.add(itemNode);
            }
            else
            {
                itemNode = snode(ele.name, false, ele.id);
                
                parentNode.add(itemNode);
                
                nmap[ele.id] = itemNode;
            }
        }
    }
    
    tv.create(document.getElementById("tree"));
}

treeview.prototype.onnodecheck = function(sender)
{
    if (sender.checked)
    {
        diguiCheck(sender.parent.id);
    }
    
}

function diguiCheck(sid)
{
    var parentNode = nmap[sid];
    
    if (!parentNode)
    {
        return;
    }
    
    parentNode.checkNode.checked = true;
    
    if (!parentNode.parent || parentNode.parent.id == sid)
    {
        return;
    }
    
    return diguiCheck(parentNode.parent.id);
}

function allSelect(check)
{
    if (check)
    {
        tv.expandAll();
    }
    else
    {
        tv.collapseAll();
    }
}
</script>

</head>
<body class="body_class" onload="load()">
<form name="addApply" action="../admin/role.do"><input
	type="hidden" name="method" value="addOrUpdateRole"> <p:navigation
	height="22">
	<td width="550" class="navigation"><span style="cursor: pointer;"
		onclick="javascript:history.go(-1)">角色管理</span> &gt;&gt; 增加角色</td>
	<td width="85"></td>
</p:navigation> <br>

<p:body width="100%">

	<p:title>
		<td class="caption"><strong>角色基本信息：</strong></td>
	</p:title>

	<p:line flag="0" />

	<p:subBody width="98%">
		<p:class value="com.china.center.oa.publics.bean.RoleBean" />

		<p:table cells="1">
			<p:pro field="name" />

			<p:pro field="locationId" innerString="quick=true">
				<option value="">--</option>
				<c:forEach items="${locationList}" var="item">
					<option value="${item.id}">${item.name}</option>
				</c:forEach>
			</p:pro>

			<p:pro field="description" innerString="rows=4 cols=60" />

			<p:cell title="权限">
				<br>
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

