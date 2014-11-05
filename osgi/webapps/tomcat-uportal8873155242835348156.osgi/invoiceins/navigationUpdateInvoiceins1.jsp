<%@ page contentType="text/html;charset=UTF-8" language="java"
	errorPage="../common/error.jsp"%>
<%@include file="../common/common.jsp"%>
<html>
<head>
<p:link title="增加发票" />
<script language="JavaScript" src="../js/JCheck.js"></script>
<script language="JavaScript" src="../js/common.js"></script>
<script language="JavaScript" src="../js/public.js"></script>
<script language="JavaScript" src="../js/json.js"></script>

<script language="javascript">

var showJSON = JSON.parse('${showJSON}');
var invoicesJSON = JSON.parse('${invoicesJSON}');
var vsJSON = JSON.parse('${vsJSON}');

var invMap = {};
var invFullMap = {};
var invNameMap = {};
<c:forEach items="${invoiceList}" var="item">
  invFullMap['${item.id}'] = '${item.fullName}';
</c:forEach>

<c:forEach items="${dutyList}" var="item">
  invMap['${item.id}'] = '${item.type}';
  invNameMap['${item.id}'] = '${item.invoicer}';
</c:forEach>

function nextStep()
{
	var ret = checkCurrentUser();

	if (!ret)
	{
		window.parent.location.reload();

		return;
	}
	
	submit(null, null, check);
}

function check()
{
    return true;
}

function loadShow(flag)
{
    var json = showJSON;
    
    var pid = $$('dutyId');
    
    var showArr = document.getElementsByName('showId');
    
    for (var i = 0; i < showArr.length; i++)
    {
        var each = showArr[i];
        
        removeAllItem(each);
        
        setOption(each, "", "--");
        
        for (var j = 0; j < json.length; j++)
        {
            var item = json[j];
            
            if (item.dutyId == pid)
            {
                setOption(each, item.id, item.name);
            }
        }
    }
    
    var vsjson = vsJSON;
    
    var dutyObj = $O('dutyId');

    var dutyVal = dutyObj.value;

    if (dutyVal == '')
    {
        dutyVal="${pmap['dutyId']}";
    }
    
    var invObj = $O('invoiceId');

    var processerObj = $O('processer');
    
    removeAllItem(invObj);
    
    if (invMap[dutyObj.value] == '3')
    {
        setOption(invObj, '', '没有发票');
    }
    
    for (var i = 0; i < vsjson.length; i++)
    {
        var item = vsjson[i];

        if (item.dutyType == invMap[dutyVal])
        {
            setOption(invObj, item.invoiceId, invFullMap[item.invoiceId]);
        }
    }

    if (flag == 1)
    {
        $O('outId').value = '';
        $O('mayMoney').value = '0.0';
    }

    removeAllItem(processerObj);

    <c:forEach items="${stafferList}" var="item">
     if ('${item.id}' == invNameMap[dutyVal])
     {
    	 setOption(processerObj, '${item.id}', '${item.name}' );
     } 
    </c:forEach>

    $O('invoiceDate').value = '${now}';

    dutyChange();
}

function opens()
{
    if ($O('customerId').value == '')
    {
        alert('请选择客户');
        return false;
    }
    
    var inStr = $O('invoiceId').value;
    
    if ($O('customerId').value == '99')
     window.common.modal('../sail/out.do?method=rptQueryOutForInvoice&mode=1&selectMode=1&stafferId=${user.stafferId}&invoiceStatus=0&load=1&dutyId2=' + $$('dutyId') + '&customerId=' + $$('customerId') + '&invoiceId2=' + inStr);
     else
     window.common.modal('../sail/out.do?method=rptQueryOutForInvoice&mode=1&selectMode=1&invoiceStatus=0&load=1&dutyId2=' + $$('dutyId') + '&customerId=' + $$('customerId') + '&invoiceId2=' + inStr);
}

function openBalance()
{
    if ($O('customerId').value == '')
    {
        alert('请选择客户');
        return false;
    }
    
    var inStr = $O('invoiceId').value;
    
    window.common.modal('../sail/out.do?method=rptQueryOutBalance&mode=1&type=0&stafferId=${user.stafferId}&selectMode=1&invoiceStatus=0&load=1&dutyId=' + $$('dutyId') + '&customerId=' + $$('customerId') + '&invoiceId2=' + inStr);
}


function getOut(oos)
{
    var outId = $$('outId');
    
    var oldm = parseFloat($$('mayMoney'));
    
    for (var i = 0 ; i < oos.length; i++)
    {
        var oo = oos[i];
        
        if (outId.indexOf(oo.value) == -1)
        {
            outId += oo.value + ";";
            
            oldm += parseFloat(oo.pinvoicemoney);
        }
    }
    
    $O('outId').value = outId;   
    
    $O('mayMoney').value = formatNum(oldm, 2);
   
}

function getOutBalance(oos)
{
	return getOut(oos);
}

function clears()
{
    $O('outId').value = '';
    $O('mayMoney').value = '0.0';
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

function selectPublic()
{
    $O('customerId').value = '99';
    $O('cname').value = '公共客户';
}

function cc(obj, index)
{
    var am = $$('amount_' + index);
    
    var pr = $$('price_' + index);
    
    if (am == '' || pr == '')
    {
        $O('e_total_' + index).value = '0.0';
    }
    else
    {
        $O('e_total_' + index).value = parseFloat(pr) * parseInt(am, 10);
    }
}

//格式化数字 四舍五入
function formatNum(num, length)
{
     var reg = /[0-9]*(.)?[0-9]*$/;

     if (!reg.test(num))
     {
        reg = /[0-9]*.$/;
        if (!reg.test(num))
        {
            return num;
        }
     }

     num += '';
     
     if (num.indexOf('.') == -1)
     {
        return num + '.' + getLength0(length);
     }

     var hou = num.substring(num.indexOf('.') + 1);

     if (hou.length <= length)
     {
        return num + getLength0(length - hou.length);
     }

     //超过 指定的四舍五入
     var ins = num.substring(0, num.indexOf('.') + 1) + hou.substring(0, length);

     var last = parseInt(hou.charAt(length));
     
     var add = false;

     if (last >= 5)
     {
        add = true;
     }
     else
     {
        add = false;
     }
     
     if (add)
     {
        var goAdd = true;
        
        for (var i = ins.length - 1; i >= 0; i--)
        {
            if (ins.charAt(i) == '.')
            {
                continue;
            }
            
            if (goAdd && parseInt(ins.charAt(i)) == 9)
            {
                goAdd = true;
                
                ins = ins.substring(0, i) + '0' + ins.substring(i + 1);
            }
            else
            {
                ins = ins.substring(0, i) + (parseInt(ins.charAt(i)) + 1) + ins.substring(i + 1);
                
                goAdd = false;
                
                break;
            }
        }
        
        if (goAdd && parseInt(ins.charAt(0)) == 0)
        {
            ins = '1' + ins;
        }
     }

     var sresult = ins + '';
     
     if (sresult.indexOf('.') == -1)
     {
        return sresult;
     }
     
     if (sresult.indexOf('.') != -1)
     {
         sresult = sresult + '0000000000000000';           
     }
     
     return sresult.substring(0, sresult.indexOf('.') + length + 1);
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
<body class="body_class" onload="loadShow(0)">
<form name="formEntry" action="../finance/invoiceins.do" method="post">
<input type="hidden" name="method" value="navigationAddInvoiceins1"> 
<input type="hidden" name="customerId" value="${bean.customerId}"> 
<input type="hidden" name="type" value="0"> 
<input type="hidden" name="mode" value="0"> 
<input type="hidden" name="id" value="${bean.id}">

<p:navigation
	height="22">
	<td width="550" class="navigation"><span style="cursor: pointer;"
		onclick="javascript:history.go(-1)">发票管理</span> &gt;&gt; 修改开票申请向导(1)</td>
	<td width="85"></td>
</p:navigation> <br>

<p:body width="98%">

	<p:title>
		<td class="caption"><strong>发票基本信息：(关联多个单据默认依次填充单据的可开票金额)</strong></td>
	</p:title>

	<p:line flag="0" />

	<p:subBody width="100%">

		<p:class value="com.china.center.oa.finance.bean.InvoiceinsBean" opr="1" />

		<p:table cells="1">

			<p:pro field="invoiceDate"/>
			
			<p:pro field="headType">
				<p:option type="invoiceinsHeadType" />
			</p:pro>
			
			<p:pro field="headContent"/>
			
			<!--<p:pro field="unit" innerString="size=60" />
			
			--><p:pro field="dutyId" innerString="readonly='readonly' onchange=loadShow(1) style='WIDTH: 340px;'">
                <p:option type="dutyList" />
            </p:pro>

			<p:pro field="invoiceId" innerString="style='WIDTH: 340px;'">
	
			</p:pro>
			
			<p:pro field="insAmount" innerString="oncheck='range(1)'"/>

			<p:cell title="开票客户" end="true">
                <input type="text" size="60" readonly="readonly" name="cname" value="${bean.customerName}" oncheck="notNone;"> 
                <font color="red">*</font>
                <input type="button" value="&nbsp;选 择&nbsp;" name="qout1" id="qout1"
                    class="button_class" onclick="selectCus()">&nbsp;
                 <input type="button" value="&nbsp;公共客户&nbsp;" name="qout2" id="qout2"
                    class="button_class" onclick="selectPublic()">
            </p:cell>
			
			<p:cell title="关联单据" end="true">
			    <input type="text" size="60" readonly="readonly" name="outId" value="${bean.refIds}"> 
                <input type="button" value="&nbsp;销售单&nbsp;" name="qout" id="qout"
                    class="button_class" onclick="opens()">&nbsp;
                 <input type="button" value="&nbsp;委托清单&nbsp;" name="qout" id="qout"
                    class="button_class" onclick="openBalance()">&nbsp;
                <input type="button" value="&nbsp;清 空&nbsp;" name="qout" id="qout"
                    class="button_class" onclick="clears()">&nbsp;&nbsp;
            </p:cell>
            
            <p:cell title="金额">
                可开票金额：<input type="text" size="20" readonly="readonly" name="mayMoney" value="${my:formatNum(mayMoney)}"> 
            </p:cell>
            
            <p:pro field="processer" innerString="readonly='readonly'">
            </p:pro>

			<p:pro field="description" cell="0" innerString="rows=3 cols=55" />

		</p:table>

	</p:subBody>

	<p:line flag="1" />

	<p:button leftWidth="100%" rightWidth="0%">
		<div align="right"><input type="button" class="button_class"
			id="ok_b" style="cursor: pointer" value="&nbsp;&nbsp;下一步&nbsp;&nbsp;"
			onclick="nextStep()"></div>
	</p:button>

	<p:message />
</p:body></form>
</body>
</html>

