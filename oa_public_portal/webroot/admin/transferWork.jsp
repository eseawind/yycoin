<%@ page contentType="text/html;charset=UTF-8" language="java"
	errorPage="../common/error.jsp"%>
<%@include file="../common/common.jsp"%>
<html>
<head>
<p:link title="工作移交" />
<script language="JavaScript" src="../js/common.js"></script>
<script language="JavaScript" src="../js/JCheck.js"></script>
<script language="JavaScript" src="../js/public.js"></script>
<script language="javascript">
function addBean()
{
	submit('确定变化工作移交?');
}

function selectCus()
{
    window.common.modal('../admin/pop.do?method=rptQueryStaffer&load=1&selectMode=1');
}

function getStaffers(oos)
{
	var obj = oos[0];
    $O('destId').value = obj.value;
    $O('destName').value = obj.pname;
}

</script>

</head>
<body class="body_class">
<form name="formEntry" action="../admin/staffer.do"><input
	type="hidden" name="method" value="transferWork">
<input type="hidden" name="destId" value=""> 
<input type="hidden" name="srcId" value="${src.id}"> 
<p:navigation
	height="22">
	<td width="550" class="navigation"><span style="cursor: pointer;"
		onclick="javascript:history.go(-1)">职员管理</span> &gt;&gt; 工作移交</td>
	<td width="85"></td>
</p:navigation> <br>

<p:body width="100%">

	<p:title>
		<td class="caption"><strong>工作移交-${src.name}：</strong></td>
	</p:title>

	<p:line flag="0" />

	<p:subBody width="98%">
		<p:table cells="1">
		
		    <p:cell title="原始移交给职员">
                ${srcTransfer.destName}
            </p:cell>

			<p:cell title="新移交给的职员">
			    <input type="text" class="input_class" name="destName" id="destName" oncheck="notNone" style="width: 240px">
			    <input type="button" value="&nbsp;...&nbsp;" name="qout" id="qout"
                    class="button_class" onclick="selectCus()"><font color="red">*</font>&nbsp;&nbsp; 
			</p:cell>

		</p:table>
	</p:subBody>

	<p:line flag="1" />

	<p:button leftWidth="100%" rightWidth="0%">
		<div align="right"><input type="button" class="button_class" id="ok_b"
		 value="&nbsp;&nbsp;确 定&nbsp;&nbsp;"
			onclick="addBean()"></div>
	</p:button>
	
	<p:message2></p:message2>
</p:body></form>
</body>
</html>

