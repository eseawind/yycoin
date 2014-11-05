<%@ page contentType="text/html;charset=UTF-8" language="java"
    errorPage="../common/error.jsp"%>
<%@include file="../common/common.jsp"%>
<html>
<head>
<p:link title="打印邮政发货单" link="false" guid="true" cal="true" dialog="true" />
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
						永银文化创意产业发展有限责任公司发货确认单</b></font></td>
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
			<tr>
			<td colspan="2" align="center">发货信息</td> 
			</tr>
			</table>
			</td>
			</tr>
			
			<tr>
				<td>
				<table width="100%" cellspacing='0' cellpadding="0">
					<tr class="content2">
						<td colspan="2" width="60%"><table><tr><td>收货单位：${bean.customerName}</td></tr></table></td>
						<td width="20%"><table><tr><td>收货人：${bean.receiver}</td></tr></table></td>
						<td width="20%"><table><tr><td>运单号：</td></tr></table></td>
					</tr>
					<tr class="content2">
						<td colspan="2" width="60%"><table><tr><td>送货地址：${bean.address}</td></tr></table></td>
						<td width="20%"><table><tr><td>联系电话：${bean.mobile}</td></tr></table></td>
						<td width="20%"><table><tr><td>快递公司：${bean.transportName1}&nbsp;${bean.transportName2}</td></tr></table></td>
					</tr>
				</table>
				</td>
			</tr>
			
			<tr>
				<td>
				<table width="100%" cellspacing='0' cellpadding="0"  class="border">
					<tr class="content2">
						<td width="8%"><table class="border1"><tr><td align="center">发货日期</td></tr></table></td>
						<td width="20%"><table class="border1"><tr><td align="center">品名</td></tr></table></td>
						<td width="5%"><table class="border1"><tr><td align="center">数量</td></tr></table></td>
						<td width="8%"><table class="border1"><tr><td align="center">结算单价</td></tr></table></td>
						<td width="8%"><table class="border1"><tr><td align="center">金额</td></tr></table></td>
						<td width="15%"><table class="border1"><tr><td align="center">对应销售单号</td></tr></table></td>
						<td width="20%"><table class="border1"><tr><td align="center">配件</td></tr></table></td>
						<td width="16%"><table class="border1"><tr><td align="center">备注</td></tr></table></td>
					</tr>
					
					<c:forEach items="${bean.itemList}" var="item" varStatus="vs">
					<tr class="content2">
						<td><table class="border1"><tr><td>${bean.repTime}</td></tr></table></td>
						<td><table class="border1"><tr><td align="center">${item.productName}</td></tr></table></td>
						<td><table class="border1"><tr><td align="center">${item.amount}</td></tr></table></td>
						<td><table class="border1"><tr><td align="center">${item.price}</td></tr></table></td>
						<td><table class="border1"><tr><td align="center">${item.value}</td></tr></table></td>
						<td><table class="border1"><tr><td align="center">${item.outId}</td></tr></table></td>
						<td><table class="border1"><tr><td align="center">${item.showSubProductName}</td></tr></table></td>
						<td><table class="border1"><tr><td align="center">${item.printText}</td></tr></table></td>
					</tr>
					</c:forEach>
					
					<c:forEach varStatus="vs" begin="1" end="${(2 - my:length(bean.itemList)) > 0 ? (2 - my:length(bean.itemList)) : 0}">
					<tr class="content2">
						<td><table class="border1"><tr><td align="center"></td></tr></table></td>
						<td><table class="border1"><tr><td></td></tr></table></td>
						<td><table class="border1"><tr><td align="center"></td></tr></table></td>
						<td><table class="border1"><tr><td align="center"></td></tr></table></td>
						<td><table class="border1"><tr><td align="center"></td></tr></table></td>
						<td><table class="border1"><tr><td align="center"></td></tr></table></td>
						<td><table class="border1"><tr><td align="center"></td></tr></table></td>
						<td><table class="border1"><tr><td align="center"></td></tr></table></td>
					</tr>
					</c:forEach>
					<tr class="content2">
						<td colspan="2"><table class="border1"><tr><td align="center">合计(人民币):</td></tr></table></td>
						<td colspan="3"><table class="border1"><tr><td align="center">${total}</td></tr></table></td>
						<td><table class="border1"><tr><td align="center"></td></tr></table></td>
						<td><table class="border1"><tr><td align="center"></td></tr></table></td>
						<td><table class="border1"><tr><td align="center"></td></tr></table></td>
					</tr>
					<tr class="content2">
						<td colspan="8"><table class="border1"><tr><td align="center">货物签收信息</td></tr></table></td>
					</tr>
					<tr class="content2">
						<td colspan="4" align="left"><table class="border1"><tr><td align="center">以上货物全部收到并确认无误！   </td></tr></table></td>
						<td colspan="4" align="left"><table class="border1"><tr><td align="center">日期：</td></tr></table></td>
					</tr>
					<tr>
						<td height="15" colspan="8"><table class="border1"><tr><td align="center"></td></tr></table></td>
					</tr>
					<tr class="content2">
						<td colspan="8" align="left"><table class="border1"><tr><td align="center">回函地址：南京市秦淮区应天大街388号1865创意园c2栋   邮编：210006    </td></tr></table></td>
					</tr>
					<tr>
						<td height="15" colspan="8"><table class="border1"><tr><td align="center"></td></tr></table></td>
					</tr>					
					<tr class="content2">
						<td colspan="4" align="left"><table class="border1"><tr><td align="center">传真请直接回传至：025-51885907   </td></tr></table></td>
						<td colspan="4" align="left"><table class="border1"><tr><td align="center">联系电话：4006518859</td></tr></table></td>
					</tr>
					<tr>
						<td height="15" colspan="8"><table class="border1"><tr><td align="center"></td></tr></table></td>
					</tr>
				</table>
				</td>
			</tr>
			
			<tr>
				<td height="15"></td>
			</tr>
			
			<tr>
				<td>
				<table width="100%" cellspacing='0' cellpadding="0">
					<tr class="content2">
						<td colspan="4" width="50%"><table><tr><td>补充说明：</td></tr></table></td>
					</tr>
					<tr class="content2">
						<td colspan="4" width="50%"><table><tr><td>1、货物签收人承诺与收货单位及经办人为直接负责人或有授权关系。</td></tr></table></td>
					</tr>
					<tr class="content2">
						<td colspan="4" width="50%"><table><tr><td>2、请签收人收到货物时现场验收，并对货物验收情况在收货后3个工作日内反馈，未反馈的视同产品无异常。</td></tr></table></td>
					</tr>
					<tr class="content2">
						<td colspan="4" width="50%"><table><tr><td>3、请收货人对货物信息进行确认后签字及盖章后快递或传真至我司，传真件有效。</td></tr></table></td>
					</tr>
					<tr class="content2">
						<td colspan="4" width="50%"><table><tr><td>4、如有任何需求，请与本单客服联系。</td></tr></table></td>
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
