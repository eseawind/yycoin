<%@ page contentType="text/html;charset=UTF-8" language="java"
	errorPage="../common/error.jsp"%>
<%@include file="../common/common.jsp"%>
<html>
<head>
<p:link title="人员结构" />
<script language="JavaScript" src="../js/common.js"></script>
<script language="JavaScript" src="../js/public.js"></script>
<script language="JavaScript" src="../js/tree.js"></script>
<script language="JavaScript" src="../js/json.js"></script>
<script language="JavaScript" src="../js/pop.js"></script>
<script language="javascript">

var shipMap = JSON.parse('${mapJSON}');

var root = JSON.parse('${root}');

var tv = new treeview("treeview","../js/tree",false, false);

//收起
var expand = 0;

//不显示
var disPri = 1;

var nodeList = [];

function digui(node)
{
    var subList = shipMap[node.id];
    
    if (subList == null || subList.length == 0)
    {
        return;
    }
    
    for (var i = 0; i < subList.length; i++)
    {
        var ele = subList[i];
        
        var ssItem = shipMap[ele.principalshipId];
        
        var subNode = snode(ele.stafferName , ssItem.length > 0, ele.principalshipId);
        
        node.add(subNode);
        
        subNode.sname = '[' + ele.principalshipName +  '] <font color=blue>' + ele.stafferName + '</font>';
        
        nodeList.push(subNode);
        
        if (ssItem.length > 0)
        {
            digui(subNode);
        }
    }
}

function allSelect()
{
    if (expand == 0)
    {
        expand = 1;
        tv.expandAll();
    }
    else
    {
         expand = 0;
        tv.collapseAll();
    }
}

function disp()
{
    for (var i = nodeList.length - 1 ; i >= 0 ; i--)
    {
        var elet = nodeList[i];
        
        var tem = elet.caption;
        
        elet.setCaption(elet.sname);
        
        elet.sname = tem;
    }
}

function load()
{
    var itemNode = snode(root.name + ' ${rootStaffer}', true, root.id);
    
    tv.add(itemNode);
    
    itemNode.sname = root.name + ' <font color=blue>${rootStaffer}</font>';
    
    nodeList.push(itemNode);
    
    digui(itemNode)
    
    tv.create(document.getElementById("tree"));
    
    expand = 0;
    
    disPri = 1;
    
    disp();
}

</script>

</head>
<body class="body_class" onload="load()">
<form name="addApply"><p:navigation height="22">
	<td width="550" class="navigation"><span>人员结构</span></td>
	<td width="85"></td>
</p:navigation> <br>

<p:body width="100%">

	<p:title>
		<td class="caption"><strong>人员结构：</strong></td>
	</p:title>

	<p:line flag="0" />

	<p:subBody width="90%">
		<p:table cells="1">
			<p:cells celspan="0">
				<span style="cursor: pointer;" onclick="allSelect()">展开/收起</span> | 
				  <span style="cursor: pointer;" onclick="disp()">显示/隐藏岗位</span>
				<br>
				<br>
				<div id=tree align="left"></div>
			</p:cells>

		</p:table>
	</p:subBody>

	<p:message />
</p:body></form>
</body>
</html>

