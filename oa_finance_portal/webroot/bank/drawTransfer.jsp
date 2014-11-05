<%@ page contentType="text/html;charset=UTF-8" language="java"
    errorPage="../common/error.jsp"%>
<%@include file="../common/common.jsp"%>
<html>
<head>
<p:link title="认领(财务)回款" />
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

		return;
	}
	
	if($O('description').value==null || $O('description').value == "")
	{
		alert("备注不能为空");
		return false;
	}
	
    submit('确定认领回款?', null, check);
}

function check()
{
	 //计算是否回款溢出
    var total = ${my:formatNum(bean.money)};
    
    var pu = 0.0;

    pu = parseFloat($$('outMoney'));
    
    if (total != pu)
    {
    	alert('回款金额使用溢出,请核对');
        
        return false;
    }

    return true;
}

function opens(obj)
{
   window.common.modal('../finance/bill.do?method=rptQueryOutBill&selectMode=1&load=1&bankId=' + $$('bankId') + '&money=' + $$('money'));    
}

function getOutBill(oos)
{
	 $O('billId').value = oos[0].value;
	 $O('outMoney').value = oos[0].pmoney;
}

function clears(obj)
{
	 $O('billId').value = '';
	 $O('outMoney').value = '';
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
<body class="body_class" onload="load()">
<form name="formEntry" action="../finance/bank.do" method="post">
<input type="hidden" name="method" value="drawTransfer"> 
<input type="hidden" name="id" value="${bean.id}"> 
<input type="hidden" name="bankId" value="${bean.bankId}"> 
<input type="hidden" name="money" value="${bean.money}"> 
<p:navigation
    height="22">
    <td width="550" class="navigation"><span style="cursor: pointer;"
        onclick="javascript:history.go(-1)">回款管理</span> &gt;&gt; 认领财务回款</td>
    <td width="85"></td>
</p:navigation> <br>

<p:body width="98%">

    <p:title>
        <td class="caption"><strong>回款基本信息</strong></td>
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
            
            <p:cell title="回款来源">
               ${bean.fromer}
            </p:cell>
            
            <p:cell title="回款日期">
               ${bean.receiveTime}
            </p:cell>
            
             <p:cell title="备注">
               <textarea rows=3 cols=55  oncheck="maxLength(200);" name="description"></textarea>
            </p:cell>
            
            <p:cell title="关联单据" end="true">
                    <input type="text" size="40" value="关联付款单号" readonly="readonly" name="billId"> 
                    &nbsp;回款金额:<input type="text" name="outMoney" readonly="readonly" oncheck="isFloat2">
                    <input type="button" value="&nbsp;转账付款单&nbsp;" name="qout" id="qout"
                        class="button_class" onclick="opens()">&nbsp;
                    <input type="button" value="&nbsp;清 空&nbsp;" name="qout" id="qout"
                        class="button_class" onclick="clears()">&nbsp;&nbsp;
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
</p:body>
</form>

</body>
</html>

