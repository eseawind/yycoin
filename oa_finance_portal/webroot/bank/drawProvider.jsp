<%@ page contentType="text/html;charset=UTF-8" language="java"
    errorPage="../common/error.jsp"%>
<%@include file="../common/common.jsp"%>
<html>
<head>
<p:link title="认领回款（供应商）" />
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
    return true;
}

function selectProvider()
{
	window.common.modal("../provider/provider.do?method=rptQueryProvider&load=1");
}

function getProvider(id, name)
{
	$O("cname").value = name;
	$O("customerId").value = id;
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

function load()
{
}

</script>

</head>
<body class="body_class" onload="load()">
<form name="formEntry" action="../finance/bank.do" method="post">
<input type="hidden" name="method" value="drawProvider"> 
<input type="hidden" name="customerId" value=""> 
<input type="hidden" name="id" value="${bean.id}"> 
<input type="hidden" name="mtype" value="${bean.mtype}"> 
<input type="hidden" name="dutyId" value="${bean.dutyId}"> 
<p:navigation
    height="22">
    <td width="550" class="navigation"><span style="cursor: pointer;"
        onclick="javascript:history.go(-1)">回款管理</span> &gt;&gt; 认领供应商回款</td>
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
            
            <p:cell title="手续费">
               ${my:formatNum(bean.handling)}
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
            
            <p:cell title="绑定供应商" end="true">
                <input type="text" size="60" readonly="readonly" name="cname" oncheck="notNone;"> 
                <font color="red">*</font>
                <input type="button" value="&nbsp;选 择&nbsp;" name="qout1" id="qout1"
                    class="button_class" onclick="selectProvider()">&nbsp;
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

