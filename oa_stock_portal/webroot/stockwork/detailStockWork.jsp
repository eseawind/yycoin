<%@ page contentType="text/html;charset=UTF-8" language="java"
	errorPage="../common/error.jsp"%>
<%@include file="../common/common.jsp"%>
<html>
<head>
<p:link title="跟单处理" />
<script language="JavaScript" src="../js/JCheck.js"></script>
<script language="JavaScript" src="../js/common.js"></script>
<script language="JavaScript" src="../js/public.js"></script>
<script language="JavaScript" src="../js/key.js"></script>
<script language="javascript">

function load()
{
	if ($$('deliveryDate') == '1')
	{
		$O('cdeliveryDate').checked = true;
	}

	if ($$('technology') == '1')
	{
		$O('ctechnology').checked = true;
	}

	if ($$('pay') == '1')
	{
		$O('cpay').checked = true;
	}

	loadForm();
}

</script>

</head>
<body class="body_class" onload="load()">
<form name="formEntry" action="../stock/work.do" method="post">
<input type="hidden" name="method" value="handleStockWork"> <input
	type="hidden" name="id" value="${bean.id}"> 
	<input type="hidden" name="deliveryDate" value="${bean.deliveryDate}">
	<input type="hidden" name="technology" value="${bean.technology}" >
	<input type="hidden" name="pay" value="${bean.pay}">
<p:navigation
	height="22">
	<td width="550" class="navigation"><span>采购跟单处理</span></td>
	<td width="85"></td>
</p:navigation> <br>

<p:body width="98%">

	<p:title>
		<td class="caption"><strong>跟单任务基本信息：</strong></td>
	</p:title>

	<p:line flag="0" />

	<p:subBody width="100%">

		<p:class value="com.china.center.oa.stock.bean.StockWorkBean" opr="2"/>

		<p:table cells="2">
            <p:cell title="跟单任务" end="true">
               ${bean.id}
            </p:cell>
            
			<p:cell title="状态">
               ${my:get('stockWorkStatus',bean.status)}
            </p:cell>

			<p:cell title="跟单时间">
               ${bean.workDate}
            </p:cell>
            
            <p:cell title="采购目标" end="true">
               ${bean.target}
            </p:cell>
            
            <p:cell title="采购申请人">
               ${bean.stafferName}
            </p:cell>

			<p:cell title="采购单号">
               <a href="../stock/stock.do?method=findStock&id=${bean.stockId}">${bean.stockId}</a>
            </p:cell>
            
            <p:cell title="采购商品">
               ${bean.productName}
            </p:cell>

			<p:cell title="供应商">
               ${bean.provideName}
            </p:cell>
            
			<p:cell title="供应商确认时间">
               ${bean.provideConfirmDate}
            </p:cell>
            
            <p:cell title="确认发货时间">
               ${bean.confirmSendDate}
            </p:cell>
            
            <p:cell title="已跟单次数">
               ${count}
            </p:cell>
            
            <p:cell title="最新确认发货时间">
               ${bean.newSendDate}
            </p:cell>
            
            <p:pro field="connector" cell="0"/>
            
            <p:pro field="way" cell="0">
                <p:option type="stockWorkWay" empty="true"/>
            </p:pro>
            
            <p:pro field="followPlan" cell="0">
                <p:option type="hasRef" empty="true"/>
            </p:pro>
            
            <p:cell title="更改内容" end="true">
			<input type="checkbox" name="cdeliveryDate">交货日期<br>
			<input type="checkbox" name="ctechnology">工艺<br>
			<input type="checkbox" name="cpay">付款方式<br>
			</p:cell>
			
			<p:pro field="sendDate" cell="0"></p:pro>
	
			<p:pro field="description" cell="0" innerString="rows=2 cols=80" />
	
		</p:table>

	</p:subBody>
	
	<p:line flag="1" />

	<p:button leftWidth="100%" rightWidth="0%">
		<div align="right">
            <input
            type="button" name="ba" class="button_class"
            onclick="javascript:history.go(-1)"
            value="&nbsp;&nbsp;返 回&nbsp;&nbsp;">
			</div>
	</p:button>

	<p:message2 />
</p:body></form>
</body>
</html>

