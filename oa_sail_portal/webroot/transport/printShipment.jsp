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

	var pickupId = $O('pickupId').value;
	var index_pos = $O('index_pos').value;

	var packageId = $O('packageId').value;
	var subindex_pos = $O('subindex_pos').value;

	var compose = $O('compose').value;
	
	// 更新打印次数
	//$ajax('../sail/ship.do?method=updateStatus&pickupId=' + pickupId, callBackPrintFun);

	// 链到客户出库单打印界面
	$l("../sail/ship.do?method=findOutForReceipt&pickupId="+pickupId+"&index_pos="+index_pos +"&packageId=" + packageId + "&subindex_pos=" + subindex_pos + "&compose=" + compose);
}

function callBackPrintFun()
{
	//
}

</script>
</head>
<body>
<input type="hidden" name="pickupId" value="${pickupId}">
<input type="hidden" name="index_pos" value="${index_pos}">
<input type="hidden" name="packageId" value="${packageId}">
<input type="hidden" name="subindex_pos" value="${subindex_pos}">
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
						发货单</b></font></td>
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
			<table width="100%" cellspacing='0' cellpadding="0" >
			<tr><td>
			制表日期：${year} / ${month} / ${day} </td> 
			<td align="right">页次：&nbsp;&nbsp;&nbsp;/&nbsp;&nbsp;&nbsp;&nbsp;</td>
			</tr>
			</table>
			</td>
			</tr>
			
			<tr>
				<td>
				<table width="100%" cellspacing='0' cellpadding="0"  class="border">
					<tr class="content2">
						<td colspan="2"><table class="border1"><tr><td>编号：${bean.pickupId}--${bean.index_pos} / ${bean.id}</td></tr></table></td>
						<td colspan="2"><table class="border1"><tr><td>发货公司：${bean.transportName1}&nbsp;${bean.transportName2}</td></tr></table></td>
					</tr>
					<tr class="content2">
						<td colspan="2"><table class="border1"><tr><td>收货人：${bean.receiver}</td></tr></table></td>
						<td colspan="2"><table class="border1"><tr><td>收货人电话：${bean.mobile}</td></tr></table></td>
					</tr>
					
					<tr class="content2">
						<td colspan="4"><table class="border1"><tr><td>送货地址：${bean.address}</td></tr></table></td>
					</tr>
				</table>
				</td>
			</tr>
			
			<tr>
				<td>
				<table width="100%" cellspacing='0' cellpadding="0"  class="border2">
					<tr class="content2">
						<td width="8%"><table class="border1"><tr><td align="center">序号</td></tr></table></td>
						<td width="30%"><table class="border1"><tr><td align="center">品名</td></tr></table></td>
						<td width="15%"><table class="border1"><tr><td align="center">发货数量</td></tr></table></td>
						<td width="8%"><table class="border1"><tr><td align="center">单位</td></tr></table></td>
						<td width="20%"><table class="border1"><tr><td align="center">备注</td></tr></table></td>
					</tr>
					
					<c:forEach items="${bean.itemList}" var="item" varStatus="vs">
					<tr class="content2">
						<td><table class="border1"><tr><td align="center">${vs.index + 1}</td></tr></table></td>
						<td><table class="border1"><tr><td>${item.productName}</td></tr></table></td>
						<td><table class="border1"><tr><td align="center">${item.amount}</td></tr></table></td>
						<td><table class="border1"><tr><td align="center">套</td></tr></table></td>
						<td><table class="border1"><tr><td align="center"></td></tr></table></td>
					</tr>
					</c:forEach>
					
					<c:forEach varStatus="vs" begin="1" end="${(2 - my:length(bean.itemList)) > 0 ? (4 - my:length(bean.itemList)) : 0}">
					<tr class="content2">
						<td><table class="border1"><tr><td align="center"></td></tr></table></td>
						<td><table class="border1"><tr><td></td></tr></table></td>
						<td><table class="border1"><tr><td align="center"></td></tr></table></td>
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
						<td>
						备货人：&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
						<td>检验人：&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
						<td>客户：&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
					</tr>

					
					<tr>
						<td height="15"></td>
					</tr>

					<tr>
						<td colspan="3" align="center"><b>第一联
						仓库联（白联）&nbsp;&nbsp;&nbsp;第二联 承运商联（红联）
						&nbsp;&nbsp;&nbsp;第三联 客户联（黄联）<b></td>
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
