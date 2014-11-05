<%@ page contentType="text/html;charset=UTF-8" language="java"
	errorPage="../common/error.jsp"%>
<%@include file="../common/common.jsp"%>
<html>
<head>
<p:link title="认领回款" />
<script language="JavaScript" src="../js/JCheck.js"></script>
<script language="JavaScript" src="../js/common.js"></script>
<script language="JavaScript" src="../js/public.js"></script>
<script language="javascript">

function addBean()
{
	submit('确定认领回款?', null, check);
}

function check()
{
    //计算是否回款溢出
    var total = ${my:formatNum(bean.money)};
    
    var pu = 0.0;
    
    for (var i = 1; i <= 5; i++)
    {
	    if (!isNone($$('outMoney' + i)))
	    {
	        pu += parseFloat($$('outMoney' + i));
	    }
    }
    
    if (total == pu)
    {
        return true;
    }
    
    if (total > pu)
    {
        return window.confirm('回款没有全部使用,系统自动把剩余的转预收,确认是否先领取回款?');
    }
    
    alert('回款金额使用溢出,请核对');
    
    return false;
}

function selectCus()
{
    window.common.modal('../client/client.do?method=rptQuerySelfClient&stafferId=${user.stafferId}&load=1');
}

function getCustomer(obj)
{
    $O('customerId').value = obj.value;
    $O('cname').value = obj.pname;
}

var g_index = 1;

function opens(index)
{
    if ($O('customerId').value == '')
    {
        alert('请选择客户');
        return false;
    }
    
    g_index = index;
    
    window.common.modal('../sail/out.do?method=rptQueryOut&selectMode=0&mode=0&load=1&stafferId=${user.stafferId}&customerId=' + $$('customerId'));
}

function openBalance(index)
{
    if ($O('customerId').value == '')
    {
        alert('请选择客户');
        
        return false;
    }
    
    g_index = index;
    
    window.common.modal('../sail/out.do?method=rptQueryOutBalance&pay=0&type=0&selectMode=0&load=1&customerId=' + $$('customerId'));
}

function getOutBalance(oos)
{
    return getOut(oos);
}

function getOut(oos)
{
    $O('outId' + g_index).value = oos[0].value;
    $O('outMoney' + g_index).value = oos[0].plast;
}

function clears()
{
    $O('outId' + g_index).value = '客户预收';
    $O('outMoney' + g_index).value = '';
}

function selectPublic()
{
    $O('customerId').value = '99';
    $O('cname').value = '公共客户';
}
</script>

</head>
<body class="body_class">
<form name="formEntry" action="../finance/bank.do" method="post">
<input type="hidden" name="method" value="drawPayment"> 
<input type="hidden" name="customerId" value=""> 
<input type="hidden" name="id" value="${bean.id}"> 
<input type="hidden" name="mtype" value="${bean.mtype}"> 
<input type="hidden" name="dutyId" value="${bean.dutyId}"> 
<p:navigation
	height="22">
	<td width="550" class="navigation"><span style="cursor: pointer;"
		onclick="javascript:history.go(-1)">回款管理</span> &gt;&gt; 认领回款</td>
	<td width="85"></td>
</p:navigation> <br>

<p:body width="98%">

	<p:title>
		<td class="caption"><strong>回款基本信息：(如果金额过多回款可以分为多个预收的收款单,销售委托单只能选择到2012前)</strong></td>
	</p:title>

	<p:line flag="0" />

	<p:subBody width="100%">

		<p:class value="com.china.center.oa.finance.bean.PaymentBean" />

		<p:table cells="1">
		
		    <p:cell title="标识">
               ${bean.id}
            </p:cell>

			<p:cell title="帐户">
               ${bean.bankName}
            </p:cell>
            
            <p:cell title="类型">
               ${my:get('paymentType', bean.type)}
            </p:cell>
            
            <p:cell title="金额">
               ${my:formatNum(bean.money)}
            </p:cell>
            
            <p:cell title="手续费">
               ${my:formatNum(bean.handling)}
            </p:cell>
            
            <p:cell title="回款来源">
               ${bean.fromer}
            </p:cell>
            
            <p:cell title="回款日期">
               ${bean.receiveTime}
            </p:cell>
			
			<p:cell title="绑定客户" end="true">
                <input type="text" size="60" readonly="readonly" name="cname" oncheck="notNone;"> 
                <font color="red">*</font>
                <input type="button" value="&nbsp;选 择&nbsp;" name="qout1" id="qout1"
                    class="button_class" onclick="selectCus()">&nbsp;
                <input type="button" value="&nbsp;公共客户&nbsp;" name="qout2" id="qout2"
                    class="button_class" onclick="selectPublic()">
            </p:cell>
            
            <c:forEach begin="1" end="5" var="item">
	            <p:cell title="关联单据${item}" end="true">
	                <input type="text" size="40" value="客户预收" readonly="readonly" name="outId${item}"> 
	                &nbsp;回款金额:<input type="text" name="outMoney${item}" oncheck="isFloat2">
	                <input type="button" value="&nbsp;销售单&nbsp;" name="qout" id="qout"
	                    class="button_class" onclick="opens(${item})">&nbsp;
	                <input type="button" value="&nbsp;委托清单&nbsp;" name="qout" id="qout"
                    class="button_class" onclick="openBalance(${item})">&nbsp;
	                <input type="button" value="&nbsp;清 空&nbsp;" name="qout" id="qout"
	                    class="button_class" onclick="clears(${item})">&nbsp;&nbsp;
	            </p:cell>
            </c:forEach>
            
             <p:cell title="备注">
               <textarea rows=3 cols=55  oncheck="maxLength(200);" name="description"></textarea>
            </p:cell>

		</p:table>

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

