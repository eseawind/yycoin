<%@ page contentType="text/html;charset=UTF-8" language="java"
	errorPage="../common/error.jsp"%>
<%@include file="../common/common.jsp"%>
<html>
<head>
<p:link title="流程定义" />
<script language="JavaScript" src="../js/JCheck.js"></script>
<script language="JavaScript" src="../js/common.js"></script>
<script language="JavaScript" src="../js/public.js"></script>
<script src="../js/jquery/jquery.js"></script>
<script language="javascript">
function addBean()
{
     //自动校验
    if (formCheck(formEntry) && window.confirm('确认保存流程定义'))
    {
        submitC(formEntry);
    }
}

</script>

</head>
<body class="body_class">
<form name="formEntry" action="../flow/flow.do" method="post"><input
	type="hidden" name="method" value="updateFlowDefine"> <input
	type="hidden" name="id" value="${bean.id}"> <p:navigation
	height="22">
	<td width="550" class="navigation"><span style="cursor: pointer;"
		onclick="javascript:history.go(-1)">流程定义管理</span> &gt;&gt; 修改流程定义</td>
	<td width="85"></td>
</p:navigation> <br>

<p:body width="100%">
	<p:class value="com.china.center.oa.flow.bean.FlowDefineBean" opr="1" />

	<p:subBody width="98%">
		<p:table cells="1" id="tables">

			<p:pro field="name" innerString="size=50"/>

			<p:pro field="type">
			    <option value="">--</option>
				<p:option type="112" />
			</p:pro>
			
			<p:pro field="parentType" innerString="readonly=true">
                <option value="">--</option>
                <p:option type="parentType" />
            </p:pro>
			
			<p:pro field="mode" innerString="readonly=true">
                <option value="">--</option>
                <p:option type="flowMode" />
            </p:pro>

			<p:pro field="description" innerString="rows=3 cols=55" />
			
		</p:table>
	</p:subBody>

	<p:line flag="1" />

	<p:button leftWidth="100%" rightWidth="0%">
		<div align="right"><input type="button" class="button_class"
			id="ok_b" style="cursor: pointer" value="&nbsp;&nbsp;保 存&nbsp;&nbsp;"
			onclick="addBean()"></div>
	</p:button>

	<p:message />
</p:body>
<input type="text" style="visibility: hidden;" size="1">
</form>
</body>
</html>

