<%@ page contentType="text/html;charset=UTF-8" language="java"
	errorPage="../common/error.jsp"%>
<%@include file="../common/common.jsp"%>
<html>
<head>
<p:link title="配置流程查阅" />
<script language="JavaScript" src="../js/common.js"></script>
<script language="JavaScript" src="../js/JCheck.js"></script>
<script language="JavaScript" src="../js/public.js"></script>
<script language="javascript">

function addBean()
{
    var msg = '确定配置流程查阅?';
    
    if ($$('type') == "")
    {
        msg = '确定卸载流程查阅?';
    }
    else
    {
        if ($$('type') != "3" && $O('processerName').value == '')
        {
            alert('请选择查阅者');
            return false;
        }
    }
    
	submit(msg, null, lverify);
}

function lverify()
{
	return true;
}
function load()
{
	loadForm();
}

var urlMap = 
{
"0" : "../admin/pop.do?method=rptQueryStaffer&load=1&selectMode=1",
"1" : "../group/group.do?method=rptQueryGroup&load=1&selectMode=1",
"2" : "",
"999" : ""
};

function selectProcesser()
{
	if ($$('type') == "3")
	{
		alert("上级主管不需要选择具体的查阅者");
		return;
	}
	
	if ($$('type') == "")
	{
		alert("请选择处理类型");
		return;
	}

	window.common.modal(urlMap[$$('type')]);
}

function getGroup(oo)
{
	getStaffers(oo);
}

function getStaffers(oo)
{
	var item = oo[0];
    $O("processerName").value = item.pname;
    $O("processer").value = item.value;
}

function change(index, obj)
{
	$O('processerName').value = '';
	$O('processer').value = '';
}

</script>

</head>
<body class="body_class" onload="load()">
<form name="formEntry" action="../flow/flow.do" method="post"><input
	type="hidden" name="method" value="configView"> <input
	type="hidden" name="flowId" value="${flowId}"> <p:navigation
	height="22">
	<td width="550" class="navigation"><span style="cursor: pointer;"
		onclick="javascript:history.go(-1)">流程管理</span> &gt;&gt; 配置流程环节</td>
	<td width="85"></td>
</p:navigation> <br>

<p:body width="100%">

	<p:title>
		<td class="caption"><strong>流程查阅：</strong></td>
	</p:title>

	<p:line flag="0" />

	<p:subBody width="98%">
		<p:table cells="1">
			<p:cells celspan="2" title="帮助说明">
			查阅插件类型分两类<br>
			1、职员插件就是流程结束后,由具体的某一职员可以查阅<br>
			2、群组插件就是流程结束后,由群组内的任意一职员可以查阅<br>
			</p:cells>

			<p:cells id="selects" celspan="2" title="流程环节">
				<table id="mselect">
					<tr>
						<td>查阅类型: <select class="select_class" name="type"
							values="${view.type}" onchange="change(0, this)">
							<option value="">--</option>
							<option value=0>职员插件</option>
							<option value=1>群组插件</option>
							<option value=3>上级主管</option>
						</select>&nbsp; 查阅者:<input type="text" name="processerName"
							value="${view.processerName}" size="15" readonly="readonly"
							style="cursor: pointer;" onclick="selectProcesser(0)"
							title="选择查阅者">&nbsp; <input type="button"
							value="&nbsp;...&nbsp;" name="qout" title="选择查阅者"
							class="button_class" onclick="selectProcesser(0)"> <input
							type="hidden" name="processer" value="${view.processer}">&nbsp;&nbsp;
						</td>
					</tr>
				</table>
			</p:cells>

		</p:table>
	</p:subBody>

	<p:line flag="1" />

	<p:button leftWidth="100%" rightWidth="0%">
		<div align="right"><input type="button" class="button_class"
			name="adds" style="cursor: pointer"
			value="&nbsp;&nbsp;保 存&nbsp;&nbsp;" onclick="addBean()">&nbsp;&nbsp;
		<input type="button" class="button_class"
			onclick="javascript:history.go(-1)"
			value="&nbsp;&nbsp;返 回&nbsp;&nbsp;"></div>
	</p:button>

	<p:message />
</p:body></form>
</body>
</html>

