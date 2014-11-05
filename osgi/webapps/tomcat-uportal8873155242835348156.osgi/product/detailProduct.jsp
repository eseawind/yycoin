<%@ page contentType="text/html;charset=UTF-8" language="java"
	errorPage="../common/error.jsp"%>
<%@include file="../common/common.jsp"%>
<html>
<head>
<p:link title="产品"/>
<script language="JavaScript" src="../js/JCheck.js"></script>
<script language="JavaScript" src="../js/common.js"></script>
<script language="JavaScript" src="../js/public.js"></script>
<script language="JavaScript" src="../product_js/CJL.0.1.min.js"></script>
<script language="JavaScript" src="../product_js/ImageTrans.js"></script>
<script language="JavaScript" src="../js/key.js"></script>
<script language="javascript">
function addBean()
{
}

function load()
{
	loadForm();
	
	<c:if test="${fn:length(bean.picPath) > 0}">
	loadImage('idContainer', "${rootUrl}pic${bean.picPath}");
	</c:if>
}
</script>

</head>

<body class="body_class" onload="load()">
<form name="addApply" action="../product/product.do?method=updateProduct" 
	method="post" enctype="multipart/form-data">
<input type="hidden" name="id" value="${bean.id}">
<input type="hidden" name="mainProvider" value="${bean.mainProvider}">
<input type="hidden" name="assistantProvider1" value="${bean.assistantProvider1}">
<input type="hidden" name="assistantProvider2" value="${bean.assistantProvider2}">
<input type="hidden" name="assistantProvider3" value="${bean.assistantProvider3}">
<p:navigation
	height="22">
	<td width="550" class="navigation">产品明细</td>
	<td width="85"></td>
</p:navigation> <br>

<p:body width="98%">

	<p:title>
		<td class="caption"><strong>产品基本信息：</strong></td>
	</p:title>

	<p:line flag="0" />

	<p:subBody width="100%">
		<p:class value="com.china.center.oa.product.bean.ProductBean" opr="2"/>

		<p:table cells="2">

			<p:pro field="name" cell="0" innerString="size=60 readonly=true"/>
			
			<p:pro field="code" cell="0" innerString="size=60 readonly=true"/>
			
			<p:pro field="abstractType" innerString="readonly=true">
				<p:option type="productAbstractType"/>
			</p:pro>
			
			<p:pro field="type">
				<p:option type="productType"/>
			</p:pro>
			
			<c:if test="${bean.abstractType == 0}">
			
				<p:pro field="ptype">
					<p:option type="productPtype"/>
				</p:pro>
				
				<p:pro field="ctype">
					<p:option type="productCtype"/>
				</p:pro>
				
				<p:pro field="stockType">
					<p:option type="207"/>
				</p:pro>
	
				<p:pro field="typeName">
					<p:option type="116"/>
				</p:pro>
				<p:pro field="specification"/>
				<p:pro field="model"/>
				
				<p:pro field="amountUnit">
					<p:option type="117"/>
				</p:pro>
				<p:pro field="weightUnit">
					<p:option type="118"/>
				</p:pro>
				<p:pro field="cubageUnit">
					<p:option type="119"/>
				</p:pro>
				<p:pro field="version"/>
				<p:pro field="design"/>
				<p:pro field="materielSource"/>
				<p:pro field="storeUnit">
					<p:option type="120"/>
				</p:pro>
				<p:pro field="abc"/>
				<p:pro field="batchModal">
					<p:option type="115"/>
				</p:pro>
				<p:pro field="checkDays">
					<p:option type="201"/>
				</p:pro>
				<p:pro field="maxStoreDays">
					<p:option type="204"/>
				</p:pro>
				<p:pro field="safeStoreDays">
					<p:option type="205"/>
				</p:pro>
				<p:pro field="makeDays">
					<p:option type="206"/>
				</p:pro>
				<p:pro field="flowDays">
					<p:option type="208"/>
				</p:pro>
				<p:pro field="minAmount">
					<p:option type="209"/>
				</p:pro>
				<p:pro field="assembleFlag"/>				
				<p:pro field="consumeInDay">
					<p:option type="210" />
				</p:pro>
				
				<p:pro field="orderAmount">
					<p:option type="211"/>
				</p:pro>
				
				<p:pro field="mainProvider" value="${bean.mainProviderName}" innerString="readonly=true" cell="0">
				</p:pro>
				<p:pro field="assistantProvider1" value="${bean.assistantProviderName1}" innerString="readonly=true">
				</p:pro>
				<p:pro field="assistantProvider2" value="${bean.assistantProviderName2}" innerString="readonly=true">
				</p:pro>
				<p:pro field="assistantProvider3" value="${bean.assistantProviderName3}" innerString="readonly=true"/>
				<p:pro field="assistantProvider4" value="${bean.assistantProviderName4}" innerString="readonly=true"/>
				
				<p:pro field="sailType">
					<p:option type="productSailType"/>
				</p:pro>
				
				<p:pro field="adjustPrice">
					<p:option type="212"/>
				</p:pro>
				
				<p:pro field="financeType">
					<p:option type="113"/>
				</p:pro>
				
				<p:pro field="dutyType">
					<p:option type="114"/>
				</p:pro>
				
				<p:cell title="金(克)">
					${bean.cost}
				</p:cell>
				
				<p:cell title="银(克)">
					${bean.planCost}
				</p:cell>	
			
				<p:pro field="batchPrice"/>
				<p:pro field="sailPrice" value="0.0"/>
				<p:pro field="checkFlag"/>
				
				<p:pro field="checkType">
					<p:option type="productCheckType"/>
				</p:pro>
				<p:pro field="checkStandard" cell="0">
					<p:option type="121"/>
				</p:pro>

				<p:pro field="inputInvoice" innerString="style='width: 300px'">
					<option value="">--</option>
				    <c:forEach items="${invoiceList}" var="item">
				     <option value="${item.id}">${item.fullName}</option>
				    </c:forEach>
				</p:pro>
				
				<p:pro field="sailInvoice" innerString="style='width: 300px'">
					<option value="">--</option>
				    <c:forEach items="${invoiceList}" var="item">
				     <option value="${item.id}">${item.fullName}</option>
				    </c:forEach>
				</p:pro>
				
				<p:pro field="picPath" cell="0" innerString="size=60 class=button_class"/>
				
				<c:if test="${fn:length(bean.picPath) > 0}">
				<p:tr align="left">
					<div id="idContainer"> </div>
				</p:tr>
				</c:if>
				
			</c:if>
			
			<p:pro field="reserve1"/>
			<p:pro field="reserve2"/>
			<p:pro field="reserve3"/>
			
			<p:pro field="reserve4">
                <p:option type="pubManagerType" empty="true"/>
            </p:pro>
            
			<p:pro field="reserve5">
                <p:option type="productStep" empty="false"/>
            </p:pro>
            
			<p:pro field="reserve6"/>
			
			<p:pro field="description" cell="0" innerString="rows=3 cols=55" />
			
			<p:cells celspan="2" title="销售范围">${locationNames}</p:cells>
			
		</p:table>
	</p:subBody>
	
	<p:tr/>
	
	<p:subBody width="100%">
		<p:table cells="2">
			<tr align="center" class="content0">
				<td width="50%" align="center">组合产品</td>
				<td width="25%" align="center">编码</td>
				<td width="25%" align="center">数量</td>
			</tr>
			
			<c:forEach items="${comVOList}" var="item">
			<tr align="center" class="content1">
				<td width="50%" align="center">${item.sproductName}</td>
				<td width="25%" align="center">${item.sproductCode}</td>
				<td width="25%" align="center">${item.amount}</td>
			</tr>
			</c:forEach>
		</p:table>
	</p:subBody>
	
	

	<p:line flag="1" />

	<p:button leftWidth="100%" rightWidth="0%">
		<div align="right">
		<input type="button" class="button_class" id="ok_b"
			style="cursor: pointer" value="&nbsp;&nbsp;返 回&nbsp;&nbsp;"
			onclick="javascript:history.go(-1)"></div>
	</p:button>

	<p:message2/>
</p:body></form>
</body>
</html>

