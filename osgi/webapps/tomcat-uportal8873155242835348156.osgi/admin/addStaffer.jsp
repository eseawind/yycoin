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
<script language="JavaScript" src="../js/json.js"></script>
<script language="JavaScript" src="../js/tree.js"></script>
<script language="JavaScript" src="../admin_js/org.js"></script>
<script language="javascript">
function addBean()
{
	submit('确定增加人员?');
}

var shipList = JSON.parse('${shipList}');

var shipMap = JSON.parse('${mapJSON}');

var levelMap = {};

var tv = new treeview("treeview","../js/tree", 1, false);

</script>

</head>
<body class="body_class" onload="load()">
<form name="addApply" action="../admin/staffer.do"><input
	type="hidden" name="method" value="addStaffer"> 
<input
    type="hidden" name="principalshipId" value=""> 
<p:navigation
	height="22">
	<td width="550" class="navigation"><span style="cursor: pointer;"
		onclick="javascript:history.go(-1)">人员管理</span> &gt;&gt; 增加人员</td>
	<td width="85"></td>
</p:navigation> <br>

<p:body width="98%">

	<p:title>
		<td class="caption"><strong>人员基本信息：</strong></td>
	</p:title>

	<p:line flag="0" />

	<p:subBody width="100%">
		<p:class value="com.china.center.oa.publics.bean.StafferBean" />

		<p:table cells="2">
			<p:pro field="name" />
			<p:pro field="sex">
				<option value="0">男</option>
				<option value="1">女</option>
			</p:pro>
			
			<p:pro field="code" />
            <p:pro field="examType">
                <p:option type="examType"></p:option>
            </p:pro>

			<p:pro field="locationId" />

			<p:pro field="postId" innerString="quick=true">
				<option value="">--</option>
				<c:forEach items="${postList}" var="item">
					<option value="${item.id}">${item.name}</option>
				</c:forEach>
			</p:pro>

			<p:pro field="graduateSchool" />
			<p:pro field="graduateDate" />

			<p:pro field="specialty" />
			<p:pro field="graduate" />
			
			<p:pro field="nation" />
            <p:pro field="city" />
            
            <p:pro field="visage" />
            <p:pro field="idCard" />
            
            
            <p:pro field="birthday" />
            <p:pro field="handphone" />
            
            <p:pro field="subphone"/>
            
            <p:pro field="credit" cell="1"/>
            
            <p:pro field="black" cell="1">
                <p:option type="stafferBlack"></p:option>
            </p:pro>
            
            <p:pro field="otype" cell="2">
                <p:option type="stafferOtype"></p:option>
            </p:pro>
            
            <p:pro field="address" cell="0" innerString="size=80"/>

			<p:pro field="description" cell="0" innerString="rows=4 cols=60" />
			
			
			<p:cells celspan="2" title="所属岗位" >
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
		<div align="right"><input type="button" class="button_class" id="ok_b"
			style="cursor: pointer" value="&nbsp;&nbsp;确 定&nbsp;&nbsp;"
			onclick="addBean()"></div>
	</p:button>

	<p:message />
</p:body></form>
</body>
</html>

