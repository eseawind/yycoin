<%@ page contentType="text/html;charset=UTF-8" language="java"
    errorPage="../common/error.jsp"%>
<%@include file="../common/common.jsp"%>
<html>
<head>
<title>打印发货单</title>
<link href="../css/self.css" type=text/css rel=stylesheet>
<script language="javascript">
function pagePrint()
{
	document.getElementById('ptr').style.display = 'none';
	window.print();
	document.getElementById('ptr').style.display = 'block';
}
</script>
</head>
<body>

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
						永银文化贵金属产品收货回执单</b></font></td>
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
				<table width="100%" cellspacing='0' cellpadding="0">
					<tr class="content2">
						<td colspan="3" width="50%"><table><tr><td>发货时间：${out.changeTime}</td></tr></table></td>
						<td width="50%"><table><tr><td>传真：025-51885907</td></tr></table></td>
					</tr>
					<tr class="content2">
						<td colspan="3"><table><tr><td>收货时间：</td></tr></table></td>
						<td><table><tr><td>商务联系人：刘倩（华东） 凌燕（华中） 周晓辉（华南） 岳元梅（华北、西部）</td></tr></table></td>
					</tr>
					<tr class="content2">
						<td colspan="3"><table><tr><td></td></tr></table></td>
						<td><table><tr><td>电话：18994031103 13925070986  18680580807  18980089190</td></tr></table></td>
					</tr>
					<tr class="content2">
						<td colspan="4"><table><tr><td>今收到永银文化（铺货）以下产品:</td></tr></table></td>
					</tr>
				</table>
				</td>
			</tr>
			
			<tr>
				<td>
				<table width="100%" cellspacing='0' cellpadding="0"  class="border">
					<tr class="content2">
						<td width="30%"><table class="border1"><tr><td align="center">产品分类名称</td></tr></table></td>
						<td width="8%"><table class="border1"><tr><td align="center">结算单价（元）</td></tr></table></td>
						<td width="15%"><table class="border1"><tr><td align="center">订货数量</td></tr></table></td>
						<td width="8%"><table class="border1"><tr><td align="center">金额合计   (元)</td></tr></table></td>
						<td width="8%"><table class="border1"><tr><td align="center">订单日期</td></tr></table></td>
						<td width="20%"><table class="border1"><tr><td align="center">备注</td></tr></table></td>
					</tr>
					
					<c:forEach items="${lastList}" var="item" varStatus="vs">
					<tr class="content2">
						<td><table class="border1"><tr><td>${item.productName}</td></tr></table></td>
						<td><table class="border1"><tr><td align="center"></td></tr></table></td>
						<td><table class="border1"><tr><td align="center">${item.amount}</td></tr></table></td>
						<td><table class="border1"><tr><td align="center"></td></tr></table></td>
						<td><table class="border1"><tr><td align="center">${item.description}</td></tr></table></td>
						<td><table class="border1"><tr><td align="center">${item.outId}</td></tr></table></td>
					</tr>
					</c:forEach>
					
					<c:forEach varStatus="vs" begin="1" end="${(2 - my:length(baseList)) > 0 ? (2 - my:length(baseList)) : 0}">
					<tr class="content2">
						<td><table class="border1"><tr><td align="center"></td></tr></table></td>
						<td><table class="border1"><tr><td></td></tr></table></td>
						<td><table class="border1"><tr><td align="center"></td></tr></table></td>
						<td><table class="border1"><tr><td align="center"></td></tr></table></td>
						<td><table class="border1"><tr><td align="center"></td></tr></table></td>
						<td><table class="border1"><tr><td align="center"></td></tr></table></td>
					</tr>
					</c:forEach>
					<tr class="content2">
						<td colspan="2"><table class="border1"><tr><td align="center">合计(人民币):</td></tr></table></td>
						<td><table class="border1"><tr><td align="center">${out.inway}</td></tr></table></td>
						<td><table class="border1"><tr><td align="center">-</td></tr></table></td>
						<td><table class="border1"><tr><td align="center"></td></tr></table></td>
						<td><table class="border1"><tr><td align="center"></td></tr></table></td>
					</tr>
					<tr class="content2">
						<td colspan="6"><table class="border1"><tr><td align="center">注："此收货回执单仅做为收货确认所用，其价格不作为对帐与结算的依据”</td></tr></table></td>
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
						<td colspan="3" width="50%"><table><tr><td>所属省行：${out.description}</td></tr></table></td>
						<td width="50%"><table><tr><td>支行名称：${out.customerName}</td></tr></table></td>
					</tr>
					<tr class="content2">
						<td colspan="3"><table><tr><td>收货人：</td></tr></table></td>
						<td><table><tr><td>收货人身份证号：</td></tr></table></td>
					</tr>
					<tr class="content2">
						<td colspan="3"><table><tr><td>电话：</td></tr></table></td>
						<td><table><tr><td>传真：</td></tr></table></td>
					</tr>
					<tr class="content2">
						<td colspan="3"><table><tr><td>邮编：</td></tr></table></td>
						<td><table><tr><td>地址：</td></tr></table></td>
					</tr>
					<tr class="content2">
						<td colspan="3"><table><tr><td></td></tr></table></td>
						<td><table><tr><td></td></tr></table></td>
					</tr>
					<tr class="content2">
						<td colspan="3"><table><tr><td>收货人签字：</td></tr></table></td>
						<td><table><tr><td>盖章：</td></tr></table></td>
					</tr>
					<tr class="content2">
						<td colspan="3"><table><tr><td></td></tr></table></td>
						<td><table><tr><td></td></tr></table></td>
					</tr>
					<tr class="content2">
						<td colspan="4"><table><tr>
						<td>					
						</td>
						</tr></table></td>
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
