<%@ page contentType="text/html;charset=UTF-8" language="java"
	errorPage="../common/error.jsp"%>
<%@include file="../common/common.jsp"%>
<html>
<head>
<p:link title="申请退款" />
<script language="JavaScript" src="../js/JCheck.js"></script>
<script language="JavaScript" src="../js/common.js"></script>
<script language="JavaScript" src="../js/public.js"></script>
<script language="javascript">
function addBean()
{
	var ret = checkCurrentUser();

	if (!ret)
	{
		window.parent.location.reload();

		return false
	}
	
	submit('确定申请退款?', null, check);
}

var maxBack = -parseFloat('${my:formatNum(check.value)}');

function check()
{
    var backPay = parseFloat($$('backPay'));
    
    var changePayment = parseFloat($$('changePayment'));
    
    if (backPay + changePayment != maxBack)
    {
        alert('申请退款和转预收必须等于:' + maxBack);
        
        return false;
    }
    
    return true;
}

function checkCurrentUser()
{	
     // check
     var elogin = "${g_elogin}";

 	 //  商务登陆
     //if (elogin == '1')
     //{
          var top = window.top.topFrame.document;
          //var allDef = window.top.topFrame.allDef;
          var srcStafferId = top.getElementById('srcStafferId').value;
         
          var currentStafferId = "${g_stafferBean.id}";

          var currentStafferName = "${g_stafferBean.name}";
         
          if (srcStafferId && srcStafferId != currentStafferId)
          {

               alert('请不要同时打开多个OA连接，且当前登陆者不同，当前登陆者应为：' + currentStafferName);
               
               return false;
          }
     //}

	return true;
}

</script>

</head>
<body class="body_class">
<form name="formEntry" action="../finance/backpay.do" method="post">
<input type="hidden" name="method" value="addBackPayApply">
<input type="hidden" name="outId" value="${bean.fullId}">
<input type="hidden" name="customerId" value="${bean.customerId}">
<p:navigation
	height="22">
	<td width="550" class="navigation">
		<span style="cursor: pointer;"
			onclick="javascript:history.go(-1)">销售管理</span> &gt;&gt; 申请退款</td>
	<td width="85"></td>
</p:navigation> <br>

<p:body width="98%">

	<p:title>
		<td class="caption"><strong>申请退款信息：</strong></td>
	</p:title>

	<p:line flag="0" />

	<p:subBody width="100%">

		<p:table cells="1">
		
		    <p:cell title="标识">
                ${bean.fullId}
            </p:cell>

            <p:cell title="总金额">
				${my:formatNum(bean.total)}
			</p:cell>
			
			<p:cell title="已支付">
                ${my:formatNum(bean.hadPay)}
            </p:cell>
            
            <p:cell title="坏账金额">
                ${my:formatNum(bean.badDebts)}
            </p:cell>
            
            <p:cell title="退货金额">
                ${my:formatNum(backTotal)}
            </p:cell>
            
            <p:cell title="退货返还金额">
                ${my:formatNum(hadOut)}
            </p:cell>
            
            <p:cell title="本次最大退货金额">
                ${my:formatNum(-check.value)}
            </p:cell>
            
            <p:cell title="客户">
                ${bean.customerName}
            </p:cell>
            
            <p:cell title="申请退款金额">
                <input type="text" name="backPay" value="0.0" oncheck="notNone;isFloat">
            </p:cell>
            
            <p:cell title="转预收">
                <input type="text" name="changePayment" value="0.0" oncheck="notNone;isFloat">
            </p:cell>
            
            <p:cell title="备注">
                <textarea name="description" rows=3 cols=55></textarea>
            </p:cell>

		</p:table>
	</p:subBody>

	<p:line flag="1" />

	<p:button leftWidth="100%" rightWidth="0%">
		<div align="right"><input type="button" class="button_class" id="ok_b"
			style="cursor: pointer" value="&nbsp;&nbsp;确 定&nbsp;&nbsp;"
			onclick="addBean()"></div>
	</p:button>

	<p:message2/>
	
</p:body>
</form>
</body>
</html>

