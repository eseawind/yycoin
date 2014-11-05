<%@ page contentType="text/html;charset=UTF-8" language="java"
	errorPage="../common/error.jsp"%>
<%@include file="../common/common.jsp"%>
<html>
<head>
<p:link title="销售关联" />
<script language="JavaScript" src="../js/JCheck.js"></script>
<script language="JavaScript" src="../js/common.js"></script>
<script language="JavaScript" src="../js/public.js"></script>
<script language="JavaScript" src="../js/math.js"></script>
<script language="javascript">

function addBean()
{
	submit('确定销售关联付款单?', null, check);
}

function check()
{
    //计算是否回款溢出
    var total = ${my:formatNum(lastMoney)};
    
    var pu = 0.0;
    var pu1 = 0.0;
    
    var bills = document.getElementsByName('billId');
    
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
    if((pu1-total)>(pu1*0.2))
    {
    	if($O('description').value==null || $O('description').value == "")
    	{
    		alert("备注不能为空");
    		return false;
    	}
    }
    
    if (!isSelect)
    {
        alert('请选择付款单');
        
        return false;
    }
    
    var bad = parseFloat($$('badMoney'));
    
    if (math_compare((pu + bad), total) == -1)
    {
        //return window.confirm('付款单金额不足,确认当前只付款:' + formatNum(pu, 2) + ',应付金额:' + formatNum(total, 2) + ',坏账金额:' + formatNum(bad, 2));
        alert('付款单金额不足,确认当前只付款:' + formatNum(pu, 2) + ',应付金额:' + formatNum(total, 2) + ',坏账金额:' + formatNum(bad, 2));

        return false;
    }
    
    if (math_compare(pu, total) == 1 && bad > 0)
    {
        alert('预收金额充足,不需要坏账');
        
        return false;
    }
    
    if (math_compare((total - pu), bad) == -1 && bad != 0)
    {
        alert('坏账金额过多,当前最多坏账金额:' + formatNum((total - pu), 2));
        
        return false;
    }
    
    return true;
}


</script>

</head>
<body class="body_class">
<form name="formEntry" action="../finance/bank.do" method="post">
<input type="hidden" name="method" value="drawPayment3"> 
<input type="hidden" name="customerId" value="${customerId}"> 
<input type="hidden" name="outId" value="${outId}"> 
<input type="hidden" name="total" value="${lastMoney}"> 
<p:navigation
	height="22">
	<td width="550" class="navigation"><span style="cursor: pointer;"
		onclick="javascript:history.go(-1)">回款管理</span> &gt;&gt; 销售/委托关联</td>
	<td width="85"></td>
</p:navigation> <br>

<p:body width="98%">

	<p:title>
		<td class="caption"><strong>此单需付款金额:${my:formatNum(lastMoney)}</strong></td>
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
                        <td width="20%" align="center">收款单</td>
                        <td width="20%" align="center">帐户</td>
                        <td width="20%" align="center">金额</td>
                        <td width="20%" align="center">时间</td>
                    </tr>

                    <c:forEach items="${billList}" var="item" varStatus="vs">
                        <tr class='${vs.index % 2 == 0 ? "content1" : "content2"}'>
                            <td align="center"><input type="checkbox" name="billId" value="${item.id}" 
                            pmoney="${my:formatNum(item.moneys)}"></td>
                            
                            <td align="center">${item.id}</td>

                            <td  align="center">${item.bankName}</td>

                            <td  align="center">${my:formatNum(item.moneys)}</td>

                            <td  align="center">${item.logTime}</td>

                        </tr>
                    </c:forEach>
                </table>
                </td>
            </tr>
        </table>
        
        <table width="100%" border="0" cellpadding="0" cellspacing="0"
            class="border">
            <tr>
                <td>
                <table width="100%" border="0" cellspacing='1' id="tables">
                    <tr align="center" class="content0">
                        <td width="15%" align="center">坏账金额</td>
                        <td align="left"><input type="text" name="badMoney" oncheck="isFloat" value="0.0"></td>
                    </tr>
                    
                     <tr align="center" class="content0">
                        <td width="15%" align="center">备注</td>
                        <td align="left"><textarea  rows=3 cols=55  oncheck="maxLength(200);" name="description" ></textarea></td>
                    </tr>

                </table>
                </td>
            </tr>
        </table>

	</p:subBody>
	
	<p:line flag="1" />

	<p:button leftWidth="100%" rightWidth="0%">
		<div align="right"><input type="button" class="button_class"
			id="ok_b" style="cursor: pointer" value="&nbsp;&nbsp;确 定&nbsp;&nbsp;"
			onclick="addBean()"></div>
	</p:button>

	<p:message2 />
</p:body></form>
</body>
</html>

