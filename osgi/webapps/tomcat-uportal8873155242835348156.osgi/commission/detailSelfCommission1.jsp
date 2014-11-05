<%@ page contentType="text/html;charset=UTF-8" language="java"
    errorPage="../common/error.jsp"%>
<%@include file="../common/common.jsp"%>
<html>
<head>
<p:link title="提成明细" cal="true" guid="true"/>
<script language="JavaScript" src="../js/common.js"></script>
<script language="JavaScript" src="../js/public.js"></script>
<script language="JavaScript" src="../js/key.js"></script>
<script language="JavaScript" src="../js/JCheck.js"></script>
<script language="JavaScript" src="../js/tableSort.js"></script>
<script language="javascript">

function load()
{
	loadForm();
	
	bingTable("senfe");
}

function query()
{
	submit(null, null, null);
}


</script>

</head>
<body class="body_class" onload="load()">
<form name="formEntry" action="../budget/budget.do" method="post">
<input type="hidden" name="method" value="${cacheQueryKey}"> 
<input type="hidden" value="1" name="firstLoad">

<p:navigation
	height="22">
	<td width="550" class="navigation">提成明细</td>
	<td width="85"></td>
</p:navigation> <br>

<p:body width="100%">

	<p:title>
		<td class="caption">
		<strong>当月实发提成公式：当月应发提成-上月未扣 -应收成本考核-KPI扣款-报废-坏账-资金占用费+扣款转下月+年度KPI返款</strong>
		</td>
	</p:title>

	<p:line flag="0" />

	<p:subBody width="98%">
		
		<p:table cells="2">
		    <p:cell title="当月应发提成">
               ${my:formatNum(bean.shouldCommission)}
            </p:cell>
            
            <p:cell title="当月实发提成">
               <strong>${my:formatNum(bean.realCommission)}</strong>
            </p:cell>
            
            <p:cell title="上月未扣">
               ${my:formatNum(bean.lastDeduction)}
            </p:cell>
            
            <p:cell title="应收成本">
               	${my:formatNum(bean.receiveCost)}
            </p:cell>
            
            <p:cell title="KPI扣款">
            	${my:formatNum(bean.kpiDeduction)}
            </p:cell>
            
            <p:cell title="坏账">
            	${my:formatNum(bean.baddebt)}
            </p:cell>
            
            <p:cell title="报废">
            	${my:formatNum(bean.broken)}
            </p:cell>
            
            <p:cell title="资金占用费">
            	${my:formatNum(bean.financeFee)}
            </p:cell>
            
            <p:cell title="扣款转下月">
            	${my:formatNum(bean.turnNextMonthDeduction)}
            </p:cell>
            
            <p:cell title="年度KPI返款">
            	${my:formatNum(bean.yearKpiMoney)}
            </p:cell>
            
        </p:table>

	</p:subBody>

	<p:line flag="1" />
	
	<p:button leftWidth="100%" rightWidth="0%">
        <div align="right"><input type="button" class="button_class"
            id="ok_b" style="cursor: pointer" value="&nbsp;&nbsp;返 回&nbsp;&nbsp;"
            onclick="javaScript:window.history.go(-1);"></div>
    </p:button>
	
	<p:message2 />
	
</p:body>
</form>
</body>
</html>

