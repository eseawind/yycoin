<%@ page contentType="text/html;charset=UTF-8" language="java"
	errorPage="../common/error.jsp"%>
<%@include file="../common/common.jsp"%>
<html>
<head>
<p:link title="增加促销规则" />
<script language="JavaScript" src="../js/JCheck.js"></script>
<script language="JavaScript" src="../js/common.js"></script>
<script language="JavaScript" src="../js/public.js"></script>
<script language="JavaScript" src="../js/math.js"></script>
<script language="JavaScript" src="../promotion_js/promotion.js"></script>
<script language="javascript">



</script>

</head>
<body class="body_class" onload="load()">
<form name="formEntry" action="../sail/promotion.do" method="post">
	<input type="hidden" name="method" value="addPromotion">
	<input type="hidden" name="industryId" value="">
	
<p:navigation height="22">
	<td width="550" class="navigation"><span style="cursor: pointer;"
		onclick="javascript:history.go(-1)">促销规则管理</span> &gt;&gt; 促销规则明细</td>
	<td width="85"></td>
</p:navigation> <br>

<p:body width="100%">

	<p:title>
		<td class="caption"><strong>促销规则基本信息：</strong></td>
	</p:title>

	<p:line flag="0" />

	<p:subBody width="98%">
		<p:class value="com.china.center.oa.sail.bean.PromotionBean" opr="2"/>

		<p:table cells="2">
			
			<p:pro field="name" />
			
			<p:pro field="id" />

			<p:pro field="minAmount" />
			
			<p:pro field="maxAmount" />
			
			<p:pro field="minMoney" cell="0"/>
			
			
			
			<p:pro field="rebateType" >
				<p:option type="rebateType"></p:option>
			</p:pro>

			<p:pro field="giftBag">
				<p:option type="giftBag"></p:option>
			</p:pro>
			
			<p:pro field="rebateMoney" cell="0" />
			
			<c:if test="${bean.rebateType==2 }">
			<p:pro field="custCredit"  />
			
			<p:pro field="busiCredit"   />
			</c:if>
			<p:pro field="isReturn" cell="0">
				<p:option type="isReturn"></p:option>
			</p:pro>
			<c:if test="${bean.rebateType!=2 }">
			<p:pro field="rebateRate" />
			</c:if>
			
			<p:pro field="maxRebateMoney" />
			
			<p:pro field="isAddUp" cell="0">
				<p:option type="isAddUp"></p:option>
			</p:pro>			
			
			<p:pro field="payType" >
			    <option value=''>--</option>
			    <option value='2'>客户信用和业务员信用额度担保</option>
                <option value='3'>信用担保</option>
                <option value='1'>款到发货(黑名单客户/零售)</option>
			</p:pro>


			
			<p:pro field="cType" innerString="quick=true">
				<p:option type="102"></p:option>
			</p:pro>
			

			
			<p:pro field="beginDate" />
			
			<p:pro field="endDate" />

			<p:pro field="refTime">
				<p:option type="refTime" empty="false"></p:option>
			</p:pro>
			
			<p:pro field="payTime" >
				<p:option type="payTime" empty="false"></p:option>
			</p:pro>			
			
			<p:cell title="事业部" end="true">
				${bean.industryName}
			</p:cell>
			
			<p:pro field="description" cell="0" innerString="rows=2 cols=80" />			
			
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
						<td width="25%" align="center">销售类型</td>
						<td width="25%" align="center">产品类型</td>
						<td width="45%" align="center">产品名称</td>
					</tr>
					
 					<c:forEach items="${itemList}" var="item" varStatus="vs">
                        <tr class='${vs.index % 2 == 0 ? "content1" : "content2"}'>
                            <td align="center">${my:get('productSailType', item.sailType)}</td>

                            <td  align="center">${my:get('productType', item.productType)}</td>

                            <td  align="center">${item.productName}</td>

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