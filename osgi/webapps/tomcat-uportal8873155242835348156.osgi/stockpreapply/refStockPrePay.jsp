<%@ page contentType="text/html;charset=UTF-8" language="java"
	errorPage="../common/error.jsp"%>
<%@include file="../common/common.jsp"%>
<html>
<head>
<p:link title="采购付款关联" />
<script language="JavaScript" src="../js/JCheck.js"></script>
<script language="JavaScript" src="../js/common.js"></script>
<script language="JavaScript" src="../js/public.js"></script>
<script language="JavaScript" src="../js/math.js"></script>
<script language="javascript">

function addBean()
{
	submit('确定关联采购付款单?', null, check);
}

function check()
{
    //计算是否回款溢出
    var total = ${my:formatNum(lastMoney)};
    
    var pu = 0.0;
    var pu1 = 0.0;
    
    var bills = document.getElementsByName('preApplyId');
    
    var isSelect = false;
    
    for (var i = 0; i < bills.length; i++)
    {
    	pu1 += parseFloat(bills[i].pmoney);
	    if (bills[i].checked)
	    {
	        pu += parseFloat(bills[i].pmoney);
	        
	        isSelect = true;
	    }
    }
    
    if (!isSelect)
    {
        alert('请选择付款单');
        
        return false;
    }
    
    return true;
}

function skipBean()
{
	$l('../finance/stock.do?method=findStockPayApply&update=1&id=' + $$('applyId') + '&ifRef=1');
}

</script>

</head>
<body class="body_class">
<form name="formEntry" action="../finance/stock.do" method="post">
<input type="hidden" name="method" value="refStockPrePay"> 
<input type="hidden" name="applyId" value="${bean.id}">
<input type="hidden" name="providerId" value="${bean.provideId}"> 
<input type="hidden" name="total" value="${bean.realMoneys}"> 
<p:navigation
	height="22">
	<td width="550" class="navigation"><span style="cursor: pointer;"
		onclick="javascript:history.go(-1)">预付款管理</span> &gt;&gt; 采购付款关联</td>
	<td width="85"></td>
</p:navigation> <br>

<p:body width="98%">

	<p:title>
		<td class="caption"><strong>此单需付款金额:${my:formatNum(bean.realMoneys)}</strong></td>
	</p:title>

	<p:line flag="0" />

	<p:subBody width="100%">

		  <table width="100%" border="0" cellpadding="0" cellspacing="0"
            class="border">
            <tr>
                <td>
                <table width="100%" border="0" cellspacing='1' id="tables">
                    <tr align="center" class="content0">
                        <td width="5%" align="center">选择</td>
                        <td width="20%" align="center">预付款单</td>
                        <td width="20%" align="center">供应商</td>
                        <td width="20%" align="center">付款单</td>
                        <td width="10%" align="center">金额</td>
                        <td width="10%" align="center">时间</td>
                    </tr>

                    <c:forEach items="${stockPreList}" var="item" varStatus="vs">
                        <tr class='${vs.index % 2 == 0 ? "content1" : "content2"}'>
                            <td align="center"><input type="checkbox" name="preApplyId" value="${item.id}" 
                            pmoney="${my:formatNum(item.moneys)}"></td>
                            
                            <td align="center">${item.id}</td>

                            <td align="center">${item.providerName}</td>
                            
                            <td align="center">${item.inBillId}</td>

                            <td align="center">${my:formatNum(item.moneys - item.realMoneys)}</td>

                            <td align="center">${item.logTime}</td>

                        </tr>
                    </c:forEach>
                </table>
                </td>
            </tr>
        </table>
        
	</p:subBody>
	
	<p:line flag="1" />

	<p:button leftWidth="100%" rightWidth="0%">
		<div align="right"><input type="button" class="button_class"
			id="ok_b" style="cursor: pointer" value="&nbsp;&nbsp;确 定&nbsp;&nbsp;"
			onclick="addBean()">&nbsp;&nbsp;
			<input type="button" class="button_class"
			id="skip_b" style="cursor: pointer" value="&nbsp;&nbsp;跳 过&nbsp;&nbsp;"
			onclick="skipBean()">
			</div>
	</p:button>

	<p:message2 />
</p:body></form>
</body>
</html>

