<%@ page contentType="text/html;charset=UTF-8" language="java"
	errorPage="../common/error.jsp"%>
<%@include file="../common/common.jsp"%>
<html>
<head>
<p:link title="修改人员" />
<script language="JavaScript" src="../js/common.js"></script>
<script language="JavaScript" src="../js/cnchina.js"></script>
<script language="JavaScript" src="../js/public.js"></script>
<script language="JavaScript" src="../js/JCheck.js"></script>
<script language="JavaScript" src="../js/json.js"></script>
<script language="JavaScript" src="../js/tree_debug.js"></script>
<script language="JavaScript" src="../admin_js/org.js"></script>
<script language="javascript">
function updateBean()
{
	submit('确定修改人员?');
}

var shipList = JSON.parse('${shipList}');

var shipMap = JSON.parse('${mapJSON}');

var myAuth = JSON.parse('${myPris}');

var levelMap = {};

var tv = new treeview("treeview","../js/tree", true, false);

function containAuth(authId)
{
    for(var i = 0; i < myAuth.length; i++)
    {
        if (myAuth[i].principalshipId == authId)
        {
            return true;
        }
    }
    
    return false;
}

function setAuth()
{
    for (var att in gNodeMap)
    {
        var node = gNodeMap[att];
        
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

function inits()
{
	load(true);
	
	setAuth();
}

</script>

</head>
<body class="body_class" onload="inits()">
<form name="addApply" action="../admin/staffer.do"><input
	type="hidden" name="method" value="updateStaffer"><input
	type="hidden" name="principalshipId" value="${bean.principalshipId}"> <input
	type="hidden" name="id" value="${bean.id}"> <p:navigation
	height="22">
	<td width="550" class="navigation"><span style="cursor: pointer;"
		onclick="javascript:history.go(-1)">人员管理</span> &gt;&gt; 修改人员</td>
	<td width="85"></td>
</p:navigation> <br>

<p:body width="98%">

	<p:title>
		<td class="caption"><strong>人员基本信息：</strong></td>
	</p:title>

	<p:line flag="0" />

	<p:subBody width="100%">
		<p:class value="com.china.center.oa.publics.bean.StafferBean" opr="1"/>

		<p:table cells="2">
			<p:pro field="name" value="${bean.name}" innerString="readonly=true" />

			<p:pro field="sex" value="${bean.sex}">
				<option value="0">男</option>
				<option value="1">女</option>
			</p:pro>
			
			<p:pro field="code" value="${bean.code}" innerString="readonly=true"/>
            <p:pro field="examType" value="${bean.examType}">
                <p:option type="examType"></p:option>
            </p:pro>

			<p:pro field="locationId" value="${bean.locationId}" innerString="readonly=true" />
			<p:pro field="postId" innerString="quick=true" value="${bean.postId}">
				<option value="">--</option>
				<c:forEach items="${postList}" var="item">
					<option value="${item.id}">${item.name}</option>
				</c:forEach>
			</p:pro>

			<p:pro field="graduateSchool" value="${bean.graduateSchool}" />
			<p:pro field="graduateDate" value="${bean.graduateDate}" />

			<p:pro field="specialty" value="${bean.specialty}" />
			<p:pro field="graduate" value="${bean.graduate}" />

			<p:pro field="nation" value="${bean.nation}" />
			<p:pro field="city" value="${bean.city}" />

			<p:pro field="visage" value="${bean.visage}" />
			<p:pro field="idCard" value="${bean.idCard}" />


			<p:pro field="birthday" value="${bean.birthday}" />
			<p:pro field="handphone" value="${bean.handphone}" />

			<p:pro field="subphone" value="${bean.subphone}" />
			
			<p:pro field="credit" cell="1"/>
            
            <p:pro field="black" cell="1">
                <p:option type="stafferBlack"></p:option>
            </p:pro>
            
            <p:pro field="status" cell="1">
                <p:option type="stafferStatus"></p:option>
            </p:pro>
            
            <p:pro field="otype" cell="1">
                <p:option type="stafferOtype"></p:option>
            </p:pro>

			<p:pro field="address" cell="0" innerString="size=80"
				value="${bean.address}" />

			<p:pro field="description" cell="0" innerString="rows=4 cols=60"
				value="${bean.description}" />
				
			<p:cells title="所属岗位" celspan="2">
			    <span style="cursor: pointer;" onclick="allSelect(true)">全部展开</span> | <span
                    style="cursor: pointer;" onclick="allSelect(false)">全部收起</span>
                <br>
                <br>
				<div id=tree></div>
			</p:cells>

		</p:table>
	</p:subBody>

	<p:line flag="1" />

	<p:button leftWidth="100%" rightWidth="0%">
		<div align="right"><input type="button" class="button_class"
			style="cursor: pointer" value="&nbsp;&nbsp;确 定&nbsp;&nbsp;"
			onclick="updateBean()"></div>
	</p:button>

	<p:message />
</p:body></form>
</body>
</html>

