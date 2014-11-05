<%@ page contentType="text/html;charset=UTF-8" language="java"
	errorPage="../common/error.jsp"%>
<%@include file="../common/common.jsp"%>
<html>
<head>
<p:link title="黑名单例外规则" />
<script language="JavaScript" src="../js/JCheck.js"></script>
<script language="JavaScript" src="../js/common.js"></script>
<script language="JavaScript" src="../js/public.js"></script>
<script language="JavaScript" src="../js/key.js"></script>
<script language="JavaScript" src="../js/math.js"></script>
<script language="JavaScript" src="../black_js/blackRule.js"></script>
<script language="javascript">


</script>

</head>
<body class="body_class" onload="load()">
<form name="formEntry" action="../commission/black.do" method="post">
	<input type="hidden" name="method" value="updateBlackRule">
	<input type="hidden" name="id" value="${bean.id}">
	<input type="hidden" name="industryId" value="${bean.industryId}">
	<input type="hidden" name="productType" value="${bean.productType}">
	<input type="hidden" name="departmentId" value="${bean.departmentId}">
	
<p:navigation height="22">
	<td width="550" class="navigation"><span style="cursor: pointer;"
		onclick="javascript:history.go(-1)">黑名单例外规则管理</span> &gt;&gt; 黑名单例外规则明细</td>
	<td width="85"></td>
</p:navigation> <br>

<p:body width="100%">

	<p:title>
		<td class="caption"><strong>基本信息：</strong></td>
	</p:title>

	<p:line flag="0" />

	<p:subBody width="98%">
		<p:class value="com.china.center.oa.commission.bean.BlackRuleBean" opr="2"/>

		<p:table cells="2">
			
			<p:pro field="name" cell="0" innerString="size=80 readonly=readonly" />

			<p:pro field="type" cell="0" >
				<p:option type="blackType"></p:option>
			</p:pro>
			
			<p:pro field="beginOutTime" />
			
			<p:pro field="endOutTime" />
			
			<p:cell title="品类" end="true">
				${bean.productTypeName}
			</p:cell>
			
			<p:cell title="事业部" end="true">
				${bean.industryName}
			</p:cell>

			<p:cell title="销售单号" end="true">
				${bean.outId}
			</p:cell>
		</p:table>
	</p:subBody>

	<!--<p:line flag="0" />-->
	
	<tr id="subProduct_tr" >
		<td colspan='2' align='center'>
		<table width="98%" border="0" cellpadding="0" cellspacing="0"
			class="border">
			<tr>
				<td>
				<table width="100%" border="0" cellspacing='1' id="tables">
					<tr align="center" class="content0">
						<td width="45%" align="center">产品名称</td>						
					</tr>
					
					<c:forEach items="${itemList}" var="item" varStatus="vs">
                        <tr class='${vs.index % 2 == 0 ? "content1" : "content2"}'>
                            <td  align="left">${item.productName}</td>
                        </tr>
                    </c:forEach>
					
				</table>
				</td>
			</tr>
		</table>

		</td>
	</tr>

	<tr id="staffer_tr" >
		<td colspan='2' align='center'>
		<table width="98%" border="0" cellpadding="0" cellspacing="0"
			class="border">
			<tr>
				<td>
				<table width="100%" border="0" cellspacing='1' id="tables_staffer">
					<tr align="center" class="content0">
						<td width="45%" align="center">职员</td>						
					</tr>
					
					<c:forEach items="${item1List}" var="item" varStatus="vs">
                        <tr class='${vs.index % 2 == 0 ? "content1" : "content2"}'>
                            <td  align="left">${item.stafferName}</td>
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
<br/>
<br/>
<br/>
<br/>
<br/>

</body>
</html>