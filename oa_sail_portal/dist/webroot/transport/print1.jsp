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
<table width="100%" border="0" cellpadding="0" cellspacing="0" id="na">
	<tr>
		<td height="6" >
		<table width="100%" border="0" cellpadding="0" cellspacing="0">
			<tr>
				<td align="center">
				<font size="6"><b>
				${out.depotName}
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
						<td><table class="border1"><tr><td>收货类型：${my:get('outType_out', out.outType)}</td></tr></table></td>
						<td><table class="border1"><tr><td>销售单号：${out.fullId}</td></tr></table></td>
						<td><table class="border1"><tr><td>仓别：${out.depotpartName}</td></tr></table></td>
						<td><table class="border1"><tr><td>运输方式：${tss1.name} &gt;&gt; ${tss.name}</td></tr></table></td>
					</tr>
					<tr class="content2">
						<td colspan="3"><table class="border1"><tr><td>客户名称：${out.customerName}</td></tr></table></td>
						<td><table class="border1"><tr><td>联系人：${out.connector}</td></tr></table></td>
					</tr>
					
					<tr class="content2">
						<td colspan="3"><table class="border1"><tr><td>送货地址：${out.customerAddress}</td></tr></table></td>
						<td><table class="border1"><tr><td>联系方式：${out.connector}</td></tr></table></td>
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
					
					<c:forEach items="${baseList}" var="item" varStatus="vs">
					<tr class="content2">
						<td><table class="border1"><tr><td align="center">${vs.index + 1}</td></tr></table></td>
						<td><table class="border1"><tr><td>${item.productName}</td></tr></table></td>
						<td><table class="border1"><tr><td align="center">${item.amount}</td></tr></table></td>
						<td><table class="border1"><tr><td align="center">${item.unit}</td></tr></table></td>
						<td><table class="border1"><tr><td align="center"></td></tr></table></td>
					</tr>
					</c:forEach>
					
					<c:forEach varStatus="vs" begin="1" end="${(4 - my:length(baseList)) > 0 ? (4 - my:length(baseList)) : 0}">
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
