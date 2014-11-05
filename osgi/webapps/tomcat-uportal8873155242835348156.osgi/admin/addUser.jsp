<%@ page contentType="text/html;charset=UTF-8" language="java"
	errorPage="../common/error.jsp"%>
<%@include file="../common/common.jsp"%>
<html>
<head>
<p:link title="增加人员" />
<script language="JavaScript" src="../js/common.js"></script>
<script language="JavaScript" src="../js/cnchina.js"></script>
<script language="JavaScript" src="../js/public.js"></script>
<script language="JavaScript" src="../js/JCheck.js"></script>
<script language="JavaScript" src="../js/tree.js"></script>
<script language="JavaScript" src="../js/prototype.js"></script>
<script language="JavaScript" src="../js/buffalo.js"></script>
<script language="JavaScript" src="../js/json.js"></script>
<script language="javascript">
var END_POINT="${pageContext.request.contextPath}/bfapp";

var buffalo = new Buffalo(END_POINT);

function addBean()
{
	submit('确定增加人员?');
}

function goqueryUser()
{
    $l('../admin/queryUser.jsp');
}

function changes()
{
    var id = $$('locationId');
    
    buffalo.remoteCall("userManager.queryStafferAndRoleByLocationId",[id], function(reply) {
                var result = reply.getResult();
                
                var sList = result['stafferList'];
                
                removeAllItem($O('stafferId'));
                
                setOption($O('stafferId'), "", "--");
                
                for (var i = 0; i < sList.length; i++)
                {
                    setOption($O('stafferId'), sList[i].id,  sList[i].name);
                }
                
        });
}
var authList = JSON.parse('${authListJSON}');

var tv = new treeview("treeview","../js/tree",true,false);

var nmap = {};

var gAuth = [];

function load()
{
    
    for (var i = 0; i < authList.length; i++)
    {
        var ele = authList[i];
        
        if (ele.bottomFlag == 0 && ele.level == 0)
        {
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

function copyAuth()
{
	window.common.modal('../admin/pop.do?method=rptQueryUser&load=1&selectMode=1');
}

var myAuth;

function getUsers(oos)
{
	myAuth = JSON.parse(oos[0].pauth);
	
	//展开后才能全部赋值
	tv.expandAll();
	
	gAuth = [];
	
	for(var i = 0; i < myAuth.length; i++)
    {
        gAuth[i] = myAuth[i].authId;
    }
	
	setAuth();
	
	tv.collapseAll();
}

function setAuth()
{
    for (var att in nmap)
    {
        var node = nmap[att];
        
        if (containAuth(node.id))
        {
            node.checkNode.checked = true;
        }
        else
        {
            node.checkNode.checked = false;
        }
    }
}

function containAuth(authId)
{
    for(var i = 0; i < gAuth.length; i++)
    {
        if (gAuth[i] == authId)
        {
            return true;
        }
    }
    
    return false;
}

function selectDutyStaffer()
{
	window.common.modal('../group/group.do?method=selectDutyStaffer&selectMode=1&load=1&flag=1');
}



function setDutyStafferVal(oos)
{
	$O('dutyStaffer').value=oos.pname;
	$O('stafferId').value=oos.pvalue;
    
}

</script>

</head>
<body class="body_class" onload="load();">
<form name="addApply" action="../admin/user.do"><input
	type="hidden" name="method" value="addUser"> 
	<input type="hidden" name="stafferId" value="" /> 
	<p:navigation
	height="22">
	<td width="550" class="navigation"><span style="cursor: pointer;"
		onclick="goqueryUser()">人员管理</span> &gt;&gt; 增加人员</td>
	<td width="85"></td>
</p:navigation> <br>

<p:body width="100%">

	<p:title>
		<td class="caption"><strong>人员基本信息：</strong></td>
	</p:title>

	<p:line flag="0" />

	<p:subBody width="98%">
		<p:class value="com.china.center.oa.publics.bean.UserBean" />

		<p:table cells="1">
			<p:pro field="name" />
			 <p:cell title="职员" width="8" end="true"><input type="text" onclick='selectDutyStaffer(this);' name=dutyStaffer size="50" >  
            </p:cell>
			
			<p:cell title="权限">
                <br>
                <span style="cursor: pointer;" onclick="copyAuth()">拷贝他人权限</span> | 
                <span style="cursor: pointer;" onclick="allSelect(true)">全部展开</span> | 
                <span
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

	<tr>
        <td colspan='2' align="center"><FONT color="blue">${MESSAGE_INFO}</FONT><FONT
            color="red">${errorInfo}</FONT></td>
    </tr>
</p:body></form>
</body>
</html>

