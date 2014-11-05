<%@ page contentType="text/html;charset=UTF-8" language="java"
	errorPage="../common/error.jsp"%>
<%@include file="../common/common.jsp"%>
<html>
<head>
<p:link title="增加销售单审核规则" />
<script language="JavaScript" src="../js/JCheck.js"></script>
<script language="JavaScript" src="../js/common.js"></script>
<script language="JavaScript" src="../js/public.js"></script>
<script language="JavaScript" src="../js/math.js"></script>
<script language="JavaScript" src="../js/key.js"></script>
<script language="JavaScript" src="../auditRule_js/auditRule.js"></script>
<script language="javascript">


</script>

</head>
<body class="body_class" onload="load()">
<form name="formEntry" action="../sail/auditRule.do" method="post">
	<input type="hidden" name="method" value="updateAuditRule">
	<input type="hidden" name="industryId" value="${bean.industryId}">
	<input type="hidden" name="id" value="${bean.id}">
	
<p:navigation height="22">
	<td width="550" class="navigation"><span style="cursor: pointer;"
		onclick="javascript:history.go(-1)">销售审核规则管理</span> &gt;&gt; 显示销售审核规则</td>
	<td width="85"></td>
</p:navigation> <br>

<p:body width="100%">

	<p:title>
		<td class="caption"><strong>销售审核规则基本信息：</strong></td>
	</p:title>

	<p:line flag="0" />

	<p:subBody width="98%">
		<p:class value="com.china.center.oa.sail.bean.AuditRuleBean" opr="2"/>

		<p:table cells="2">
			
			<p:pro field="industryId" innerString="readonly=true" value="${bean.industryName}">				
			</p:pro>

			<p:pro field="sailType" >
				<p:option type="outType_out"></p:option>
			</p:pro>
			
		</p:table>
	</p:subBody>

	<p:line flag="0" />
	
	<tr id="subProduct_tr" >
		<td colspan='2' align='center'>
		<table width="98%" border="0" cellpadding="0" cellspacing="0"
			class="border">
			<tr>
				<td>
				<table width="100%" border="0" cellspacing='1' id="tables">
					<tr align="center" class="content0">
						<td width="6%" align="center">管理类型</td>
						<td width="6%" align="center">产品类型</td>
						<td width="6%" align="center">材质类型</td>
						<td width="6%" align="center">纸币类型</td>
						<td width="20%" align="center">产品名称</td>
						<td width="10%" align="center">付款条件</td>
						<td width="6%" align="center">销售账期</td>
						<td width="6%" align="center">产品账期</td>
						<td width="8%" align="center">毛利率上/下限</td>						
						<td width="6%" align="center">利润账期</td>
						<td width="6%" align="center">低于售价</td>
						<td width="6%" align="center">差异比例</td>
						<td width="8%" align="center">最小毛利率</td>			
					</tr>
					
 					<c:forEach items="${itemList}" var="item" varStatus="vs">
                        <tr class='${vs.index % 2 == 0 ? "content1" : "content2"}'>
							<td width="6%" align="center">${my:get('pubManagerType',item.managerType)}</td>
							<td width="6%" align="center">${my:get('productType',item.productType)}</td>
							<td width="6%" align="center">${my:get('201',item.materiaType)}</td>
							<td width="6%" align="center">${my:get('209',item.currencyType)}</td>								
							<td width="20%" align="center">${item.productName}</td>																	
							<td width="10%" align="center">${my:get('payCondition',item.payCondition)}</td>         								
							<td width="6%" align="center">${item.accountPeriod}</td>								
							<td width="6%" align="center">${item.productPeriod}</td>
							<td width="8%" align="center">${item.ratioDown}%/${item.ratioUp}%</td>																		
							<td width="6%" align="center">${item.profitPeriod}</td>					        
							<td width="6%" align="center">${my:get('lessThanSailPrice',item.ltSailPrice)}</td>
							<td width="6%" align="center">${my:formatNum(item.diffRatio)}</td>
							<td width="8%" align="center">${my:formatNum(item.minRatio)}</td>														

                        </tr>
                    </c:forEach>
					
				</table>
				</td>
			</tr>
		</table>

		</td>
	</tr>

	<p:line flag="1" />
	
	<p:button leftWidth="100%" rightWidth="0%">
		<div align="right"><input type="button"
			class="button_class" id="ok_b" style="cursor: pointer"
			value="&nbsp;&nbsp;返回&nbsp;&nbsp;" onclick="javaScript:window.history.go(-1);"></div>
	</p:button>

	<p:message />
	
</p:body>
</form>

</body>
</html>