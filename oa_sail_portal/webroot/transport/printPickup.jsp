<%@ page contentType="text/html;charset=UTF-8" language="java"
    errorPage="../common/error.jsp"%>
<%@include file="../common/common.jsp"%>
<html>
<head>
<p:link title="打印发货单" link="false" guid="true" cal="true" dialog="true" />
<script language="JavaScript" src="../js/common.js"></script>
<script language="JavaScript" src="../js/JCheck.js"></script>
<script language="JavaScript" src="../js/public.js"></script>
<link href="../css/self.css" type=text/css rel=stylesheet>
<script language="javascript">
function pagePrint()
{
	document.getElementById('ptr').style.display = 'none';
	window.print();
	document.getElementById('ptr').style.display = 'block';

	if ($$('printMode') == '0')
	{
		var pickupId = $O('pickupId').value;
		var index_pos = $O('index_pos').value;
		var compose = $O('compose').value;
		
		// 链到客户出库单打印界面
		$l("../sail/ship.do?method=findNextPackage&printMode=0&printSmode=0&pickupId="+pickupId+"&index_pos="+index_pos + "&compose=" + compose);
	}
}

</script>
</head>
<body>
<input type="hidden" name="pickupId" value="${bean.pickupId}">
<input type="hidden" name="index_pos" value="${index_pos}">
<input type="hidden" name="printMode" value="${printMode}">
<input type="hidden" name="printSmode" value="${printSmode}">
<input type="hidden" name="compose" value="${compose}">

<table width="100%" border="0" cellpadding="0" cellspacing="0" id="na">
	<tr>
		<td height="6" >
		<table width="100%" border="0" cellpadding="0" cellspacing="0">
			<tr>
				<td align="center">
				<font size="6"><b>
				</b>
				</font></td>
			</tr>
		</table>
		</td>
	</tr>
</table>

<table width="90%" border="0" cellpadding="0" cellspacing="0"
	align="center">
	<tr>
		<td colspan='2'>
		<table width="100%" border="0" cellpadding="0" cellspacing="0">
			<tr>

			</tr>
			<tr>
				<td align="center">
				<table width="100%" border="0" cellspacing="2">
					<tr>
						<td style="height: 27px" align="center"><font size=5><b>
						批&nbsp;&nbsp;&nbsp;次&nbsp;&nbsp;&nbsp;出&nbsp;&nbsp;&nbsp;库&nbsp;&nbsp;&nbsp;单</b></font></td>
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
		<td colspan='2' align='center'>
		<table width="100%" border="0" cellpadding="0" cellspacing="0">
			
			<tr>
				<td>
				<table width="100%" cellspacing='0' cellpadding="0"  class="border">
					<tr class="content2">
						<td colspan="2"><table class="border1"><tr><td>批次编号：${bean.pickupId}</td></tr></table></td>
						<td colspan="2"><table class="border1"><tr><td>制单时间：${year} / ${month} / ${day}</td></tr></table></td>
					</tr>
					<tr class="content2">
						<td colspan="4"><table class="border1"><tr><td>对应客户出库单号：1 - ${pickupCount}</td></tr></table></td>
					</tr>
					
				</table>
				</td>
			</tr>
			
			<tr>
				<td>
				<table width="100%" cellspacing='0' cellpadding="0"  class="border2">
					<tr class="content2">
						<td width="5%"><table class="border1"><tr><td align="center">序号</td></tr></table></td>
						<td width="30%"><table class="border1"><tr><td align="center">品名</td></tr></table></td>
						<td width="52%"><table class="border1"><tr><td align="center">配件名</td></tr></table></td>
						<td width="8%"><table class="border1"><tr><td align="center">发货数量</td></tr></table></td>
					</tr>
					
					<c:forEach items="${bean.itemList}" var="item" varStatus="vs">
					<tr class="content2">
						<td><table class="border1"><tr><td align="center">${vs.index + 1}</td></tr></table></td>
						<td><table class="border1"><tr><td>${item.productName}</td></tr></table></td>
						<td><table class="border1"><tr><td>${item.showSubProductName}</td></tr></table></td>
						<td><table class="border1"><tr><td align="center">${item.amount}</td></tr></table></td>
					</tr>
					</c:forEach>
					
					<c:forEach varStatus="vs" begin="1" end="${(4 - my:length(baseList)) > 0 ? (4 - my:length(baseList)) : 0}">
					<tr class="content2">
						<td><table class="border1"><tr><td align="center"></td></tr></table></td>
						<td><table class="border1"><tr><td></td></tr></table></td>
						<td><table class="border1"><tr><td align="center"></td></tr></table></td>
						<td><table class="border1"><tr><td align="center"></td></tr></table></td>
					</tr>
					</c:forEach>
				</table>
				</td>
			</tr>
			
			<tr>
				<td height="15"></td>
			</tr>


			<tr>
				<td>
				<table width="100%" cellspacing='0' cellpadding="0">
					<tr>
						<td colspan="2" align="left">
						备货人：&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
						<td colspan="2" align="left">发货人：&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
					</tr>

					<tr>
						<td colspan="4" align="left">
						检验人员：&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
					</tr>
				</table>
				</td>
			</tr>
		</table>

		</td>
	</tr>
	
	<tr id="ptr">
		<td width="92%">
		<div align="right"><input type="button" name="pr"
			class="button_class" onclick="pagePrint()"
			value="&nbsp;&nbsp;打 印&nbsp;&nbsp;"></div>
		</td>
	</tr>
</table>
</body>
</html>
