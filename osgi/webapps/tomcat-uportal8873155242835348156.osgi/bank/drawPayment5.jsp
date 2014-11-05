<%@ page contentType="text/html;charset=UTF-8" language="java"
    errorPage="../common/error.jsp"%>
<%@include file="../common/common.jsp"%>
<html>
<head>
<p:link title="预收拆分" />
<script language="JavaScript" src="../js/JCheck.js"></script>
<script language="JavaScript" src="../js/common.js"></script>
<script language="JavaScript" src="../js/public.js"></script>
<script language="javascript">

function addBean()
{	
    submit('确定拆分预收?', null, check);
}

function check()
{

	var customerId = $$('customerId');

	var oriCustomerId = $$('oriCustomerId');

	if (customerId == oriCustomerId)
	{
		alert('预收拆分后的接受客户不能是原客户');

		return false;
	}
	
    return true;
}

function selectCus()
{
    window.common.modal('../client/client.do?method=rptQueryAllClient&type=1&first=1&load=1');
}

function getCustomer(obj)
{
    $O('customerId').value = obj.value;
    $O('cname').value = obj.pname;
}

</script>

</head>
<body class="body_class">
<form name="formEntry" action="../finance/bank.do" method="post">
<input type="hidden" name="method" value="drawPayment5"> 
<input type="hidden" name="customerId" value="">
<input type="hidden" name="oriCustomerId" value="${bean.customerId}">
<input type="hidden" name="oriStafferId" value="${bean.ownerId}">
<input type="hidden" name="id" value="${bean.id}">
<input type="hidden" name="paymentId" value="${bean.paymentId}">

<p:navigation
    height="22">
    <td width="550" class="navigation"><span style="cursor: pointer;"
        onclick="javascript:history.go(-1)">预收管理</span> &gt;&gt; 预收拆分</td>
    <td width="85"></td>
</p:navigation> <br>

<p:body width="98%">

    <p:title>
        <td class="caption"><strong>预付拆分</strong></td>
    </p:title>

    <p:line flag="0" />

    <p:subBody width="100%">

        <p:table cells="1">
        
            <p:cell title="预收标识">
               ${bean.id}
            </p:cell>

            <p:cell title="回款标识">
               ${bean.paymentId}
            </p:cell>

            <p:cell title="帐户">
               ${bean.bankName}
            </p:cell>
            
            <p:cell title="类型">
               ${my:get('inbillType', bean.type)}
            </p:cell>
            
            <p:cell title="原客户">
               ${bean.customerName}
            </p:cell>
            
            <p:cell title="原业务员">
               ${bean.ownerName}
            </p:cell>
            
            <p:cell title="金额">
               ${my:formatNum(bean.moneys)}
            </p:cell>
            
            <p:cell title="转入客户" end="true">
                <input type="text" size="60" readonly="readonly" name="cname" oncheck="notNone;"> 
                <font color="red">*</font>
                <input type="button" value="&nbsp;选 择&nbsp;" name="qout1" id="qout1"
                    class="button_class" onclick="selectCus()">&nbsp;              
            </p:cell>            

            <p:cell title="转移金额" end="true">
				<input type="text" name="transMoney" value="${my:formatNum(bean.moneys)}" oncheck="isFloat2;range(0, ${my:formatNum(bean.moneys)})">                
            </p:cell>
            
             <p:cell title="备注">
               <textarea rows=3 cols=55  oncheck="notNone;maxLength(200);" name="description"></textarea>&nbsp;<font color="red">*</font>
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

