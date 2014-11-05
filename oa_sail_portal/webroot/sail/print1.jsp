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

	// 更新打印次数
	$ajax('../sail/out.do?method=updateDistPrintCount&id=' + $$('id'), callBackFun);
}

function callBackFun()
{
	//
}
</script>

<style> 
.bordern {
	BORDER-RIGHT: #000000 1px solid;
	height: 100%;
	width: 100%;
	cellspacing: 0px;
	cellpadding: 0px;
	BACKGROUND-COLOR: #FFFFFF;
}
</style> 

</head>
<body>
<input type="hidden" name="id" value="${distributionBean.id}">
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
						出&nbsp;&nbsp;库&nbsp;&nbsp;单</b></font></td>
					</tr>
					<tr>
						<td style="height: 14px" align="right"><b>
						第 ${distributionBean.printCount}&nbsp;次打印</b></td>
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
						<td><table class="border1"><tr><td>制表日期：${year} / ${month} / ${day}</td></tr></table></td>
						<td><table class="border1"><tr><td>申请人：${out.stafferName}</td></tr></table></td>
						<td><table class="border1"><tr><td>总金额：${my:formatNum(out.total)}</td></tr></table></td>
						<td><table class="border1"><tr><td>销售部门：${out.industryName3}</td></tr></table></td>
					</tr>
					<tr class="content2">
						<td><table class="border1"><tr><td>收货人：${distributionBean.receiver}</td></tr></table></td>
						<td  colspan="2"><table class="border1"><tr><td>联系方式：${distributionBean.mobile}</td></tr></table></td>
						<td><table class="border1"><tr><td>销售单号：${out.fullId}</td></tr></table></td>
					</tr>
					
					<tr class="content2">
						<td colspan="4"><table class="border1"><tr><td>收货地址：${distributionBean.address}</td></tr></table></td>
					</tr>
					
					<tr class="content2">
						<td><table class="border1"><tr><td>快递支付方式：${my:get('deliverPay',distributionBean.expressPay)}</td></tr></table></td>
						<td><table class="border1"><tr><td>货运支付方式：${my:get('deliverPay',distributionBean.transportPay)}</td></tr></table></td>
						<td colspan="2"><table class="border1"><tr><td>发货方式：${distributionBean.shippingName}</td></tr></table></td>
					</tr>
					
					<tr class="content2">
						<td colspan="4"><table class="border1"><tr><td>信息备注：${out.description}</td></tr></table></td>
					</tr>
				</table>
				</td>
			</tr>
			
			<tr>
				<td>
				<table width="100%" cellspacing='0' cellpadding="0"  class="border2">
					<tr class="content2">
						<td width="50%"><table class="border1"><tr><td align="center">品名</td></tr></table></td>
						<td width="15%"><table class="border1"><tr><td align="center">发货数量</td></tr></table></td>
						<td width="35%"><table class="border1"><tr><td align="center">打包信息</td></tr></table></td>
					</tr>
					
					<c:forEach items="${baseList}" var="item" varStatus="vs">
					<tr class="content2">
						<td><table class="border1"><tr><td>${item.productName}</td></tr></table></td>
						<td><table class="border1"><tr><td align="center">${item.amount}</td></tr></table></td>
						<td><table class="bordern"><tr><td align="center"></td></tr></table></td>
					</tr>
					</c:forEach>
					
					<tr class="content2">
						<td><table class="border1"><tr><td></td></tr></table></td>
						<td><table class="border1"><tr><td align="center"></td></tr></table></td>
						<td><table class="border1"><tr><td align="center"></td></tr></table></td>
					</tr>
					
				</table>
				</td>
			</tr>
			
			<tr>
				<td>
				<table width="100%" cellspacing='0' cellpadding="0" class="border2">
					<tr class="content2">
						<td><table class="border1"><tr><td>
						备货人员：&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td></tr></table></td>
					</tr>

					<tr class="content2">
						<td><table class="border1"><tr><td>
						数量是否准确：&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;包装是否完好：&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;金银是否错拿：&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;证书是否齐全：&nbsp;&nbsp;&nbsp;<br>
						手袋是否齐全：&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;配件是否齐全：&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						</td></tr></table></td>
					</tr>
					
					<tr class="content2">
						<td><table class="border1"><tr><td>
						检验人员：&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td></tr></table></td>
					</tr>

					<tr class="content2">
						<td><table class="border1"><tr><td>
						数量是否准确：&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;包装是否完好：&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;金银是否错拿：&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;证书是否齐全：&nbsp;&nbsp;&nbsp;<br>
						手袋是否齐全：&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;配件是否齐全：&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						</td></tr></table></td>
					</tr>
					
					<tr class="content2">
						<td><table class="border1"><tr><td>
						包装人员：&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td></tr></table></td>
					</tr>

					<tr class="content2">
						<td><table class="border1"><tr><td>
						货值超过5万的贵重物品（业务要求发实册的除外）是否已另行包装发快递：&nbsp;&nbsp;&nbsp;
						</td></tr></table></td>
					</tr>
					
					<tr class="content2">
						<td><table class="border1"><tr><td>
						贵金属是否已用气泡袋包裹：&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;纸钞、邮票是否已用报纸、硬纸板夹紧或胶带缠紧：&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						</td></tr></table></td>
					</tr>
					
					<tr class="content2">
						<td><table class="border1"><tr><td>
						包装箱内是否已用足够多的缓冲材料进行包垫：&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;包装箱外是否已按要求做相关发货标识：&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						</td></tr></table></td>
					</tr>
					
					<tr class="content2">
						<td><table class="border1"><tr><td>
						包装箱外是否已使用永银或集邮专用封箱胶带及封箱贴：&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;单箱货值是否超过50万：&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						</td></tr></table></td>
					</tr>
					
					<tr class="content2">
						<td><table class="border1"><tr><td>
						包装时间：&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;监控设备：&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;包装件数：&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;包装重量：&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						</td></tr></table></td>
					</tr>
					
					<tr class="content2">
						<td><table class="border1"><tr><td>
						发货人员：&nbsp;&nbsp;&nbsp;
						</td></tr></table></td>
					</tr>
					<tr class="content2">
						<td><table class="border1"><tr><td>
						一：货运&nbsp;&nbsp;&nbsp;
						</td></tr></table></td>
					</tr>
					<tr class="content2">
						<td><table class="border1"><tr><td>
						发货单号：&nbsp;&nbsp;&nbsp;
						</td></tr></table></td>
					</tr>
					<tr class="content2">
						<td><table class="border1"><tr><td>
						二：快递&nbsp;&nbsp;&nbsp;
						</td></tr></table></td>
					</tr>
					<tr class="content2">
						<td><table class="border1"><tr><td>
						发货单号：&nbsp;&nbsp;&nbsp;
						</td></tr></table></td>
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
