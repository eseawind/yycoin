<%@ page contentType="text/html;charset=UTF-8" language="java"
	errorPage="../common/error.jsp"%>
<%@include file="../common/common.jsp"%>
<html>
<head>
<p:link title="增加产品价格配置" />
<script language="JavaScript" src="../js/JCheck.js"></script>
<script language="JavaScript" src="../js/common.js"></script>
<script language="JavaScript" src="../js/public.js"></script>
<script language="JavaScript" src="../js/math.js"></script>
<script language="JavaScript" src="../js/key.js"></script>
<script language="JavaScript" src="../product_js/product.js"></script>
<script language="javascript">

function load()
{
	if ($$('ftype') == '1')
	{
		$O('cftype').checked = true;
	}
	
	loadForm();	
}


</script>

</head>
<body class="body_class" onload="load()">
<form name="formEntry" action="../product/priceConfig.do" method="post">
	<input type="hidden" name="method" value="addPriceConfig" />
	<input type="hidden" name="industryId" value="" />
	<input type="hidden" name="stafferId" value="" />
	<input type="hidden" name="productId" value="" />
	<input type="hidden" name="nature" value="" />
	<input type="hidden" name="ftype" value="${bean.ftype}" />
	
<p:navigation height="22">
	<td width="550" class="navigation"><span style="cursor: pointer;"
		onclick="javascript:history.go(-1)">产品价格配置管理</span> &gt;&gt; 显示产品价格配置</td>
	<td width="85"></td>
</p:navigation> <br>

<p:body width="100%">

	<p:title>
		<td class="caption"><strong>产品价格配置信息：</strong></td>
	</p:title>

	<p:line flag="0" />

	<p:subBody width="98%">
		<p:class value="com.china.center.oa.product.bean.PriceConfigBean" opr="2"/>

		<p:table cells="2">
			
			<p:pro field="productId" cell="0" innerString="readonly=true size=80" value="${bean.productName}">
			</p:pro>

			<c:if test="${bean.type=='1'}">
			
				<p:cell title="不受金银价波动影响" end="true">
					<input type="checkbox" name='cftype' id ='cftype' readonly=readonly/>不受金银价波动影响
				</p:cell>
			
				<p:cell title="金价" end="true">
					${my:formatNum(bean.gold)}
				</p:cell>
				
				<p:cell title="银价" end="true">
					${my:formatNum(bean.silver)}
				</p:cell>
				
				<p:cell title="邮票总价" end="true">
					${my:formatNum(bean.price)}
				</p:cell>
				
				<p:cell title="辅料费用" end="true">
					${my:formatNum(bean.gsPriceUp)}
				</p:cell>
				
				<p:cell title="结算价" end="true">
					${my:formatNum(bean.sailPrice)}
				</p:cell>
			</c:if>
			
			<c:if test="${bean.type=='0'}">
				<p:cell title="建议销售价" end="true">
					${my:formatNum(bean.minPrice)}
				</p:cell>
				
				<p:cell title="事业部" end="true">
					${bean.industryName}
				</p:cell>		
			</c:if>	
			
		</p:table>
	</p:subBody>

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