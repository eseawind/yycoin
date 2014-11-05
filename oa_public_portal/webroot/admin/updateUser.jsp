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
<script language="JavaScript" src="../js/prototype.js"></script>
<script language="JavaScript" src="../js/buffalo.js"></script>
<script language="JavaScript" src="../js/key.js"></script>
<script language="javascript">
var END_POINT="${pageContext.request.contextPath}/bfapp";

var buffalo = new Buffalo(END_POINT);

function addBean()
{
	submit('确定修改人员?');
}

function goqueryUser()
{
    $l('../admin/queryUser.jsp');
}

function changes()
{
    loadForm();
    
    var id = $$('locationId');
    
    buffalo.remoteCall("userManager.queryStafferAndRoleByLocationId",[id], function(reply) {
                var result = reply.getResult();
                
                var sList = result['stafferList'];
                
                var roleList = result['roleList'];
                
                removeAllItem($O('stafferId'));
                removeAllItem($O('roleId'));
                
                setOption($O('stafferId'), "", "--");
                setOption($O('roleId'), "", "--");
                
                for (var i = 0; i < sList.length; i++)
                {
                    setOption($O('stafferId'), sList[i].id,  sList[i].name);
                }
                
                for (var i = 0; i < roleList.length; i++)
                {
                    setOption($O('roleId'), roleList[i].id,  roleList[i].name);
                }
                
                loadForm();
        });
}

</script>

</head>
<body class="body_class" onload="changes()">
<form name="addApply" action="../admin/user.do"><input
	type="hidden" name="method" value="updateUser"><input
    type="hidden" name="id" value="${bean.id}"> <p:navigation
	height="22">
	<td width="550" class="navigation"><span style="cursor: pointer;"
		onclick="goqueryUser()">人员管理</span> &gt;&gt; 修改人员</td>
	<td width="85"></td>
</p:navigation> <br>

<p:body width="100%">

	<p:title>
		<td class="caption"><strong>人员基本信息：</strong></td>
	</p:title>

	<p:line flag="0" />

	<p:subBody width="98%">
		<p:class value="com.china.center.oa.publics.bean.UserBean" opr="1"/>

		<p:table cells="1">
			<p:pro field="name" innerString="readonly=true"/>

			<p:pro field="locationId" innerString="readonly=true">
				<c:forEach items="${locationList}" var="item">
					<option value="${item.id}">${item.name}</option>
				</c:forEach>
			</p:pro>

			<p:pro field="stafferId" innerString="quick=true">
				<option value="">--</option>
				<c:forEach items="${stafferList}" var="item">
					<option value="${item.id}">${item.name}</option>
				</c:forEach>
			</p:pro>

			<p:pro field="roleId" innerString="quick=true">
				<option value="">--</option>
				<c:forEach items="${roleList}" var="item">
					<option value="${item.id}">${item.name}</option>
				</c:forEach>
			</p:pro>

		</p:table>
	</p:subBody>

	<p:line flag="1" />

	<p:button leftWidth="100%" rightWidth="0%">
		<div align="right"><input type="button" class="button_class" id="ok_b"
			style="cursor: pointer" value="&nbsp;&nbsp;确 定&nbsp;&nbsp;"
			onclick="addBean()"></div>
	</p:button>
</p:body></form>
</body>
</html>

