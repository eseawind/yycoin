<%@ page contentType="text/html;charset=UTF-8" language="java"
	errorPage="../common/error.jsp"%>
<%@include file="../common/common.jsp"%>
<html>
<head>
<p:link title="修改组织" />
<script language="JavaScript" src="../js/common.js"></script>
<script language="JavaScript" src="../js/public.js"></script>
<script language="JavaScript" src="../js/JCheck.js"></script>
<script language="JavaScript" src="../js/tree.js"></script>
<script language="JavaScript" src="../js/json.js"></script>
<script language="JavaScript" src="../admin_js/org.js"></script>
<script language="javascript">

var shipList = JSON.parse('${shipList}');

var shipMap = JSON.parse('${mapJSON}');

function addBean()
{
    submit('确定修改组织到组织结构?', null, checkBean);
}

function checkBean()
{
    if ($$('modifyParent') == '0')
    {
        return true;
    }
    
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
            
            if (id == $$('id'))
            {
                alert('自己不能作为自己的上级');
                return false;
            }
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


var tv = new treeview("treeview","../js/tree",true, false);


function load1()
{
    load();
    
    $v('dis', false);
}

function change()
{
    if ($$('modifyParent') == '0')
    {
        $O('modifyParent').value = '1';
        $v('dis', true);
    }
    else
    {
        $O('modifyParent').value = '0';
        $v('dis', false);
    }
}
</script>

</head>
<body class="body_class" onload="load1()">
<form name="addApply" action="../admin/org.do"><input
	type="hidden" name="method" value="updateOrg"> <input
	type="hidden" name="id" value="${bean.id}"><input
	type="hidden" name="parentId" value="${bean.parentId}"> <input
	type="hidden" name="modifyParent" value="0"><p:navigation
	height="22">
	<td width="550" class="navigation"><span style="cursor: pointer;"
		onclick="javascript:history.go(-1)">组织管理</span> &gt;&gt; 修改组织</td>
	<td width="85"></td>
</p:navigation> <br>

<p:body width="100%">

	<p:title>
		<td class="caption"><strong>组织基本信息：</strong></td>
	</p:title>

	<p:line flag="0" />

	<p:subBody width="98%">
		<p:class value="com.china.center.oa.publics.bean.PrincipalshipBean"
			opr="1" />

		<p:table cells="1">
			<p:pro field="name" />

			<p:pro field="level" innerString="readonly=true">
				<option value="">--</option>
				<option value="1">1级</option>
				<option value="2">2级</option>
				<option value="3">3级</option>
				<option value="4">4级</option>
				<option value="5">5级</option>
				<option value="6">6级</option>
				<option value="7">7级</option>
				<option value="8">8级</option>
				<option value="9">9级</option>
				<option value="10">10级</option>
                <option value="11">11级</option>
			</p:pro>
			
			<p:cell title="上级组织">
                ${parentName} 
            </p:cell>
            
            <p:pro field = "status" >
            	<p:option type="orgStatus" />
            </p:pro>
            


		</p:table>
	</p:subBody>

	<p:line flag="1" />

	<p:button leftWidth="100%" rightWidth="0%">
		<div align="right"><input type="button" class="button_class"
			id="ok_b" style="cursor: pointer" value="&nbsp;&nbsp;确 定&nbsp;&nbsp;"
			onclick="addBean()"></div>
	</p:button>

	<p:message />
</p:body></form>
</body>
</html>

