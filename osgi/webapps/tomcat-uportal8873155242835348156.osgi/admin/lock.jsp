<%@ page contentType="text/html;charset=UTF-8" language="java"
	errorPage="../common/error.jsp"%>
<%@include file="../common/common.jsp"%>
<html>
<head>

<p:link title="屏幕锁" />
<script src="../js/common.js"></script>
<script src="../js/public.js"></script>
<script language="javascript">
function press()
{
    myevent = window.common.getEvent();
    
	if (myevent.keyCode == 13)
	{
		if (checkValue())
		{
			formBean.submit();
		}
	}
}

function mod()
{
	if (checkValue())
	{
		formBean.submit();
	}
}

function checkValue()
{
	if ($O('password').value == '')
	{
		alert('请输入密码');
		return false;
	}
	
    return true;
}

function returnMain()
{
	window.top.location.href = '../admin/logout.do';
}

</script>

</head>
<body class="body_class">
<form name="formBean" action="../admin/checkuser.do?method=unlock">
<input type="hidden" name="method" value="unlock">
<p:navigation
    height="22">
    <td width="550" class="navigation">屏幕锁</td>
    <td width="85"></td>
</p:navigation>
<br>

<table width="85%" border="0" cellpadding="0" cellspacing="0"
	align="center">
	<tr>
		<td valign="top" colspan='2'>
		<table width="100%" border="0" cellpadding="0" cellspacing="0">
			<!--DWLayoutTable-->
			<tr>
				<td width="784" height="6"></td>
			</tr>
			<tr>
				<td align="center" valign="top">
				<div align="left">
				<table width="90%" border="0" cellspacing="2">
					<tr>
						<td>
						<table width="100%" border="0" cellpadding="0" cellspacing="10">
							<tr>
								<td width="15">&nbsp;</td>
								<td width="6"><img src="../images/dot_r.gif" width="6"
									height="6"></td>
								<td class="caption"><strong>输入密码:</strong></td>
							</tr>
						</table>
						</td>
					</tr>


				</table>
				</td>
			</tr>
		</table>
		</td>
	</tr>


	<tr>
		<td background="../images/dot_line.gif" colspan='2'></td>
	</tr>

	<tr>
		<td height="10" colspan='2'></td>
	</tr>

	<tr>
		<td colspan='2' align='center'>
		<table width="65%" border="0" cellpadding="0" cellspacing="0"
			class="border">
			<tr>
				<td>
				<table width="100%" border="0" cellspacing='1'>
					<tr class="content2">
						<td width="30%">密码:</td>
						<td><input type="password" name="password"
							onkeypress="press()"><font color="#FF0000">*</font></td>
					</tr>
				</table>
				</td>
			</tr>
		</table>
		</td>
	</tr>


	<tr>
		<td height="10" colspan='2'></td>
	</tr>


	<tr>
		<td background="../images/dot_line.gif" colspan='2'></td>
	</tr>

	<tr>
		<td height="10" colspan='2'></td>
	</tr>

	<tr>
		<td width="82%">
		<div align="right"><input name="add" type="button"
			class="button_class" value="&nbsp;&nbsp;确 定&nbsp;&nbsp;"
			onclick="mod()">&nbsp;&nbsp;
		<input name="cancel" type="button" class="button_class"
			value="&nbsp;&nbsp;重新登录&nbsp;&nbsp;" onclick="returnMain()"></div>
		</td>
		<td width="18%"></td>
	</tr>

	<tr>
		<td height="10" colspan='2'></td>
	</tr>

	<p:message2></p:message2>

</table>


</form>
</body>
</html>

