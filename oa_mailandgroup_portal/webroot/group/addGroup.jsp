<%@ page contentType="text/html;charset=UTF-8" language="java"
	errorPage="../common/error.jsp"%>
<%@include file="../common/common.jsp"%>
<html>
<head>
<p:link title="增加群组" />
<script language="JavaScript" src="../js/JCheck.js"></script>
<script language="JavaScript" src="../js/public.js"></script>
<script language="JavaScript" src="../js/common.js"></script>
<script language="javascript">
function adds()
{
    setAllSelect();
	submit('确定操作群组?');
}

function selectStaffer()
{
    window.common.modal('../admin/pop.do?method=rptQueryStaffer&load=1');
}

function getStaffers(oo)
{
    for (var i = 0; i < oo.length; i++)
    {
        var eachItem = oo[i];
        
        if (containOption(eachItem.value))
        {
            continue;
        }
        
        setOption($O('vsStafferIds'), eachItem.value, eachItem.pname);
    }
}

function containOption(id)
{
    var vsoptions = $O('vsStafferIds').options;
        
    for (var k = 0; k < vsoptions.length; k++)
    {
        if (vsoptions[k].value == id)
        {
            return true;
        }
    }
    
    return false;
}

function setAllSelect()
{
    var vsoptions = $O('vsStafferIds').options;
        
    for (var k = 0; k < vsoptions.length; k++)
    {
        vsoptions[k].selected = true;
    }
}

function delAllStaffer()
{
    removeAllItem($O('vsStafferIds'));
}

function delStaffer()
{
    var vsoptions = $O('vsStafferIds').options;
        
    for (var k = vsoptions.length - 1; k >= 0; k--)
    {
        if (vsoptions[k].selected )
        {
            $O('vsStafferIds').remove(k);
        }
    }
}
</script>

</head>
<body class="body_class">
<form name="formEntry" action="../group/group.do" method="post"><input
	type="hidden" name="method" value="addOrUpdateGroup">
	<input
    type="hidden" name="update" value="${update}">
    <input
    type="hidden" name="id" value="${bean.id}">
	<input
    type="hidden" name="type" value="${param.type}${bean.type}"> <p:navigation
	height="22">
	<td width="550" class="navigation"><span style="cursor: pointer;"
		onclick="javascript:history.go(-1)">群组管理</span> &gt;&gt; 操作群组</td>
	<td width="85"></td>
</p:navigation> <br>

<p:body width="98%">

	<p:title>
		<td class="caption"><strong>群组基本信息：</strong></td>
	</p:title>

	<p:line flag="0" />

	<p:subBody width="100%">
		<p:class value="com.china.center.oa.group.bean.GroupBean" opr="1"/>

		<p:table cells="1">
			<p:pro field="name" />
			
			<p:cell title="组员">
			    <br>
                <span style="cursor: pointer;" onclick="selectStaffer()">选择组员</span> | <span
                    style="cursor: pointer;" onclick="delStaffer()">删除组员</span>  | <span
                    style="cursor: pointer;" onclick="delAllStaffer()">全部删除</span>
                <br>
                <br>
                <select multiple="multiple" size="20" style="width: 260px" name="vsStafferIds">
                <c:forEach items="${items}" var="item">
                <option value="${item.stafferId}">${item.stafferName}</option>
                </c:forEach>
                </select>
            </p:cell>
		</p:table>
	</p:subBody>

	<p:line flag="1" />

	<p:button leftWidth="100%" rightWidth="0%">
		<div align="right"><input type="button" class="button_class" id="ok_b"
			style="cursor: pointer" value="&nbsp;&nbsp;确 定&nbsp;&nbsp;"
			onclick="adds()"></div>
	</p:button>

	<p:message/>
</p:body></form>
</body>
</html>

