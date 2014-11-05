<%@ page contentType="text/html;charset=UTF-8" language="java"
	errorPage="../common/error.jsp"%>
<%@include file="../common/common.jsp"%>
<html>
<head>
<p:link title="增加发票" />
<script language="JavaScript" src="../js/common.js"></script>
<script language="JavaScript" src="../js/math.js"></script>
<script language="JavaScript" src="../js/public.js"></script>
<script language="JavaScript" src="../js/cnchina.js"></script>
<script language="JavaScript" src="../js/JCheck.js"></script>
<script language="JavaScript" src="../js/compatible.js"></script>
<script language="JavaScript" src="../js/jquery/jquery.js"></script>
<script language="JavaScript" src="../js/json.js"></script>

<script language="javascript">
	<%@include file="../invoiceins_js/invoiceins.jsp"%>

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

    if (dutyVal == '')
    {
        dutyVal = '90201008080000000001';
        $O('dutyId').value = dutyVal;
    }

    var invObj = $O('invoiceId');

    var processerObj = $O('processer');

    removeAllItem(invObj);

    if (invMap[dutyVal] == '3')
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

    if ($$('id') == '')
    	$O('invoiceDate').value = '${now}';

    if (flag != 1)
    	load();
}

function load()
{
    setOption($O('provinceId'), "", "--");
    for (var i = 0; i < pList.length; i++)
    {
        setOption($O('provinceId'), pList[i].id, pList[i].name);
    }    
    
    var radioElements = document.getElementsByName('rshipping');

    var rshipping = $O('shipping').value;

	for (var i=0; i< radioElements.length; i++)
	{
		if (radioElements[i].value == rshipping)
		{
			radioElements[i].checked = "checked";

			radio_click(radioElements[i]);
		}
	}
	
    loadForm();

    changes($O('cityId'));
    
    loadForm();

    changeArea("${pmap['areaId']}");
    
    fillTypeChange();
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
	
	if ($$('mayMoney') == '')
	{
		$O('mayMoney').value = '0';
	}
	
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

function fillTypeChange()
{
	var tobj = document.getElementById('dist_table');
	
	var fillType = $O('fillType').value;

	if (fillType == '0') {
		$v(tobj, true);
		$O('provinceId').oncheck = 'notNone';
	    $O('cityId').oncheck = 'notNone';
	    $O('areaId').oncheck = 'notNone';
	    $O('address').oncheck = 'notNone';
	    $O('receiver').oncheck = 'notNone';
	    $O('mobile').oncheck = 'notNone;isMathNumber';
		
	} else {
		$v(tobj, false);
		$O('provinceId').oncheck = '';
	    $O('cityId').oncheck = '';
	    $O('areaId').oncheck = '';
	    $O('address').oncheck = '';
	    $O('receiver').oncheck = '';
	    $O('mobile').oncheck = '';
	}
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
<input type="hidden" name="customerId" value="${pmap['customerId']}"> 
<input type="hidden" name="type" value="0"> 
<input type="hidden" name="mode" value="0"> 
<input type="hidden" name="id" value="${pmap['id']}">
<input type=hidden name="shipping" value="${pmap['shipping']}" />

<p:navigation
	height="22">
	<td width="550" class="navigation"><span style="cursor: pointer;"
		onclick="javascript:history.go(-1)">发票管理</span> &gt;&gt; 开票申请向导(1)</td>
	<td width="85"></td>
</p:navigation> <br>

<p:body width="98%">

	<p:title>
		<td class="caption"><strong>发票基本信息：(关联多个单据默认依次填充单据的可开票金额)</strong></td>
	</p:title>

	<p:line flag="0" />

	<p:subBody width="100%">

		<p:class value="com.china.center.oa.finance.bean.InvoiceinsBean" />

		<p:table cells="1">

			<p:pro field="invoiceDate" value="${pmap['invoiceDate']}"/>
			
			<p:pro field="headType" value="${pmap['headType']}">
				<p:option type="invoiceinsHeadType" />
			</p:pro>
			
			<p:pro field="headContent" innerString="size=60" value="${pmap['headContent']}"/>
			
			<!--<p:pro field="unit" innerString="size=60" />
			
			--><p:pro field="dutyId" value="${pmap['dutyId']}" innerString="readonly='readonly' onchange=loadShow(1) style='WIDTH: 340px;'">
				<option value="">--</option>
                <p:option type="dutyList" />
            </p:pro>

			<p:pro field="invoiceId" innerString="style='WIDTH: 340px;'">
	
			</p:pro>
			
			<p:pro field="insAmount" value="${pmap['insAmount']}" innerString="oncheck='range(1,10)'"/>

			<p:cell title="开票客户" end="true">
                <input type="text" size="60" readonly="readonly" name="cname" value="${pmap['cname']}" oncheck="notNone;"> 
                <font color="red">*</font>
                <input type="button" value="&nbsp;选 择&nbsp;" name="qout1" id="qout1"
                    class="button_class" onclick="selectCus()">&nbsp;
                 <input type="button" value="&nbsp;公共客户&nbsp;" name="qout2" id="qout2"
                    class="button_class" onclick="selectPublic()">
            </p:cell>
			
			<p:cell title="关联单据" end="true">
			    <input type="text" size="60" readonly="readonly" name="outId" value="${pmap['outId']}"> 
                <input type="button" value="&nbsp;销售单&nbsp;" name="qout" id="qout"
                    class="button_class" onclick="opens()">&nbsp;
                 <input type="button" value="&nbsp;委托清单&nbsp;" name="qout" id="qout"
                    class="button_class" onclick="openBalance()">&nbsp;
                <input type="button" value="&nbsp;清 空&nbsp;" name="qout" id="qout"
                    class="button_class" onclick="clears()">&nbsp;&nbsp;
            </p:cell>
            
            <p:cell title="金额">
                可开票金额：<input type="text" size="20" readonly="readonly" name="mayMoney" value="${pmap['mayMoney']}"> 
            </p:cell>
            
            <p:pro field="processer" innerString="readonly='readonly'" value="${pmap['processer']}"/>
			
			<p:pro field="fillType" value="${pmap['fillType']}" innerString="onchange='fillTypeChange()'">
				<option value="">--</option>
				<option value="0">新配送地址</option>
				<option value="1">同销售单地址</option>
			</p:pro>
			
			<p:pro field="description" cell="0" innerString="rows=3 cols=55" value="${pmap['description']}"/>

		</p:table>
		
		<p:table cells="1" id="dist_table">
			<tr  class="content1">
		  		<td align="center" colspan="2"><strong>==========以下是配送信息==========</strong></td>
		  	</tr>
			
			<p:cell title="发货方式" end="true">
             <input type="radio" name="rshipping" value="0" onClick="radio_click(this)">自提&nbsp;&nbsp;
             <input type="radio" name="rshipping" value="1" onClick="radio_click(this)">公司&nbsp;&nbsp;
             <input type="radio" name="rshipping" value="2" onClick="radio_click(this)">第三方快递&nbsp;&nbsp;
             <input type="radio" name="rshipping" value="3" onClick="radio_click(this)">第三方货运&nbsp;&nbsp;
             <input type="radio" name="rshipping" value="4" onClick="radio_click(this)">第三方快递+货运&nbsp;&nbsp;
          	</p:cell>
		  
		  <p:cell title="运输方式" end="true">
             <select name="transport1" quick=true class="select_class" style="width:20%" values="${pmap['transport1']}">
	         </select>&nbsp;&nbsp;
	         <select name="transport2" quick=true class="select_class" style="width:20%" values="${pmap['transport2']}">
	         </select>
          </p:cell>
		
		<p:cell title="运费支付方式" end="true">
             <select name="expressPay" quick=true class="select_class" style="width:20%" values="${pmap['expressPay']}">
             <p:option type="deliverPay" empty="true"></p:option>
	         </select>&nbsp;&nbsp;
	         <select name="transportPay" quick=true class="select_class" style="width:20%" values="${pmap['transportPay']}">
	         <p:option type="deliverPay" empty="true"></p:option>
	         </select>
          </p:cell>
		
		  <tr  class="content1">
		  	<td>送货地址：</td>
		  	<td>选择地址：
			  	<select name="provinceId" quick=true onchange="changes(this)" values="${pmap['provinceId']}" class="select_class" ></select>&nbsp;&nbsp;
	         	<select name="cityId" quick=true onchange="changeArea()" values="${pmap['cityId']}" class="select_class" ></select>&nbsp;&nbsp;
	         	<select name="areaId" quick=true values="${pmap['areaId']}" class="select_class" ></select>&nbsp;&nbsp;
	         	<input type="button" class="button_class"
						value="&nbsp;选择地址&nbsp;" onClick="selectAddr();" />&nbsp;&nbsp;
			</td>
		  </tr>	
		  
		  <tr  class="content2">
		  	<td></td>
		  	<td>详细地址：
		  		<input type="text" name="address" value="${pmap['address']}" size=100 maxlength="300" style="width: 80%;">
		  	</td>
		  </tr>
		  
		  <p:cell title="收 货 人" end="true">
             <input type="text" name="receiver" value="${pmap['receiver']}" size=20 maxlength="30">
          </p:cell>
          
          <tr  class="content2">
            <td>手&nbsp;&nbsp;&nbsp;&nbsp;机：</td>
            <td>
              <input type="text" name="mobile" value="${pmap['mobile']}"  size=13 maxlength="13"><font color="#FF0000">*</font>
              &nbsp;&nbsp;固定电话：&nbsp;&nbsp;            
              <input type="text" name="telephone" value="${pmap['telephone']}" size=20 maxlength="30">
            </td>
          </tr>

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

