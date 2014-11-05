<%@ page contentType="text/html;charset=UTF-8" language="java"
	errorPage="../common/error.jsp"%>
<%@include file="../common/common.jsp"%>
<html>
<head>
<p:link title="增加新产品申请" />
<script language="JavaScript" src="../js/JCheck.js"></script>
<script language="JavaScript" src="../js/common.js"></script>
<script language="JavaScript" src="../js/public.js"></script>
<script language="JavaScript" src="../js/key.js"></script>
<script language="JavaScript" src="../product_js/productApply.js"></script>
<script language="javascript">

</script>

</head>
<body class="body_class" onload="load()">
<form name="formEntry" action="../product/productApply.do" method="post">
	<input type="hidden" name="method" value="updateProductApply">
	<input type="hidden" name="productManagerId" value="${g_stafferBean.id}"> 
	<input type="hidden" name="oprId" value="${g_stafferBean.id}"> 
	<input type="hidden" name="save" value="${bean.status})"> 
	<input type="hidden" name="industryId" value="${bean.industryId}">
	<input type="hidden" name="id" value="${bean.id}">

<p:navigation height="22">
	<td width="550" class="navigation"><span style="cursor: pointer;"
		onclick="javascript:history.go(-1)">新产品申请</span> &gt;&gt; 新产品明细</td>
	<td width="85"></td>
</p:navigation> <br>

<p:body width="100%">

	<p:title>
		<td class="caption"><strong>新产品基本信息：</strong></td>
	</p:title>

	<p:line flag="0" />

	<p:subBody width="98%">
		<p:class value="com.china.center.oa.product.bean.ProductApplyBean" opr="2"/>

		<p:table cells="2">

			<p:pro field="name" cell="0" innerString="size=60" />
			
			<p:pro field="fullName" cell="0" innerString="size=60" />

			<p:pro field="type">
				<p:option type="productType" empty="true" />
			</p:pro>

			<p:pro field="materiaType">
				<p:option type="201" empty="true" />
			</p:pro>

			<p:pro field="channelType">
				<p:option type="productSailType" empty="true" />
			</p:pro>

			<p:pro field="managerType">
				<p:option type="pubManagerType" empty="true" />
			</p:pro>

			<p:pro field="cultrueType">
				<p:option type="204" empty="true" />
			</p:pro>

			<p:pro field="discountRate">
				<p:option type="205" empty="true" />
			</p:pro>
			
			<p:pro field="priceRange">
				<p:option type="206" empty="true" />
			</p:pro>
			
			<p:pro field="salePeriod">
				<p:option type="207" empty="true" />
			</p:pro>

			<p:pro field="saleTarget">
				<p:option type="208" empty="true" />
			</p:pro>

			<p:pro field="currencyType">
				<p:option type="209" empty="true" />
			</p:pro>

			<p:pro field="secondhandGoods">
				<p:option type="210" empty="true" />
			</p:pro>			

			<p:pro field="style">
				<p:option type="211" empty="true" />
			</p:pro>	

			<p:pro field="commissionBeginDate" />
			
			<p:pro field="commissionEndDate" />

			<p:pro field="country">
				<p:option type="212" empty="true" />
			</p:pro>

			<p:pro field="gold"/>
			<p:pro field="silver"/>
			
			<p:cell title="产品经理" >
				${bean.productManagerName}
			</p:cell>
						
			<p:pro field="nature" cell="0">
				<p:option type="natureType" empty="true" />
			</p:pro>
			
			<p:pro field="dutyType" cell="0">
				<p:option type="114" empty="true"></p:option>
			</p:pro>
			
			<p:pro field="inputInvoice" innerString="style='width: 300px'">
				<option value="">--</option>
			    <c:forEach items="${invoiceList1}" var="item">
			     <option value="${item.id}">${item.fullName}</option>
			    </c:forEach>
			</p:pro>
			
			<p:pro field="sailInvoice" innerString="style='width: 300px'">
				<option value="">--</option>
			    <c:forEach items="${invoiceList2}" var="item">
			     <option value="${item.id}">${item.fullName}</option>
			    </c:forEach>
			</p:pro>
			
			<p:cell title="事业部" end="true">
				${bean.industryIdsName}
			</p:cell>

			<p:pro field="description" cell="0" innerString="rows=2 cols=80" />

		</p:table>
	</p:subBody>

	<p:line flag="0" />

	<p:title>
		<td class="caption"><strong>产品人员属性：</strong></td>
	</p:title>

	<p:line flag="0" />

	<tr id="productVSStaffer_tr">
		<td colspan='2' align='center'>
		<table width="98%" border="0" cellpadding="0" cellspacing="0"
			class="border">
			<tr>
				<td>
				<table width="100%" border="0" cellspacing='1' id="tables_VS">
					<tr align="center" class="content0">
						<td width="20%" align="center">人员角色</td>
						<td width="20%" align="center">提成比例(‰)</td>
						<td width="55%" align="center">人员</td>						
					</tr>
					
					<c:forEach items="${itemList1}" var="item" varStatus="vs">
                        <tr class='${vs.index % 2 == 0 ? "content1" : "content2"}'>	
							<td width="20%" align="center">${my:get('stafferRole', item.stafferRole)}</td>
							<td width="20%" align="center">${item.commissionRatio}</td>
							<td width="50%" align="center">${item.stafferName}</td>
                        </tr>
                    </c:forEach>
					
				</table>
				</td>
			</tr>
		</table>

		</td>
	</tr>
	
	<p:line flag="1" />
	
	<tr>
        <td colspan='2' align='center'>
        <div id="desc1" style="display: block;">
        <table width="100%" border="0" cellpadding="0" cellspacing="0"
            class="border">
            <tr>
                <td>
                <table width="100%" border="0" cellspacing='1' id="tables">
                    <tr align="center" class="content0">
                        <td width="10%" align="center">审批人</td>
                        <td width="10%" align="center">审批动作</td>
                        <td width="10%" align="center">前状态</td>
                        <td width="10%" align="center">后状态</td>
                        <td width="45%" align="center">意见</td>
                        <td width="15%" align="center">时间</td>
                    </tr>

                    <c:forEach items="${logList}" var="item" varStatus="vs">
                        <tr class='${vs.index % 2 == 0 ? "content1" : "content2"}'>
                            <td align="center">${item.actor}</td>

                            <td  align="center">${item.oprModeName}</td>

                            <td  align="center">${item.preStatusName}</td>

                            <td  align="center">${item.afterStatusName}</td>

                            <td  align="center">${item.description}</td>

                            <td  align="center">${item.logTime}</td>

                        </tr>
                    </c:forEach>
                </table>
                </td>
            </tr>
        </table>
        </div>
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

