<%@ page contentType="text/html;charset=UTF-8" language="java"
	errorPage="../common/error.jsp"%>
<%@include file="../common/common.jsp"%>
<html>
<head>
<p:link title="批量转销售" guid="true"/>
<LINK href="../css/tabs/jquery.tabs-ie.css" type=text/css rel=stylesheet>
<LINK href="../css/tabs/jquery.tabs.css" type=text/css rel=stylesheet>
<script language="JavaScript" src="../js/common.js"></script>
<script language="JavaScript" src="../js/compatible.js"></script>
<script language="JavaScript" src="../js/math.js"></script>
<script language="JavaScript" src="../js/public.js"></script>
<script language="JavaScript" src="../js/JCheck.js"></script>
<script language="JavaScript" src="../js/json.js"></script>
<script src="../js/jquery/jquery.js"></script>
<SCRIPT src="../js/jquery/jquery.tabs.js"></SCRIPT>
<script language="javascript">

function selCustomer(obj)
{
	    window.common.modal('../client/client.do?method=rptQuerySelfClient1&stafferId=${user.stafferId}&load=1');
}

function getCustomer1(obj)
{
    $O('customerId').value = obj.value;
    $O('cname').value = obj.pname;
}

function addBean(opr)
{
	var outType = $$('outType');

	if (outType == 0)
	{
		$O('method').value = 'batchSettleOut';
	}else
	{
		$O('method').value = 'batchSwatchOut';
	}
	
    submit('确定批量操作，请检查所选的类型?', null, checks);
}

function checks()
{
	var outids = document.getElementsByName('p_outid');
	var baseids = document.getElementsByName('p_baseId');
	var lasts = document.getElementsByName('p_last');
	var amounts = document.getElementsByName('p_amount');
	var prices = document.getElementsByName('p_price');

	if (outids.length <= 1)
	{
		alert('请增加产品行项目')

		return false;
	}
	var arr = [];
	
	for(var i=0; i<outids.length - 1; i++)
	{
		if (outids[i].value == '')
		{
			alert('请选择具体产品');
			$f(outids[i]);
			return false;
		}

		if (baseids[i].value == '')
		{
			alert('数据不全，请重新选择产品');
			$f(baseids[i]);
			return false;
		}

		var amount = parseInt(amounts[i].value, 10);
		var last = parseInt(lasts[i].value, 10);

		if (amount <= 0)
		{
			alert('结算数量须大于0');
			$f(amounts[i]);
			return false;
		}

		if (amount > last)
		{
			alert('结算数量须大于0且小于'+last);
			$f(amounts[i]);
			return false;
		}

		if (parseFloat(prices[i].value) <= 0)
		{
			alert('结算价须大于0');
			$f(prices[i]);
			return false;
		}

		var prodid = baseids[i].value;

		if (containInList(arr, prodid))
		{
			alert('同一委托单中的行项目标识不可重复');

			$f(baseids[i]);
			
			return false;
		}
		
		arr.push(prodid);
	}
	
	return true;
}

function addPayTr()
{
    for (var i = 0; i < 1; i++)
    {
        addTrInner("tables_pay", "trCopy_pay");
    }
}

function load()
{
	addPayTr();	
}

function removeTr(obj)
{
    obj.parentNode.parentNode.removeNode(true);
}


var proobj
function selectCanSettle(obj)
{
	var customerId = $$('customerId');

	if (customerId == '')
	{
		alert('请选择客户');

		return;
	} 
	
	proobj=obj;	

	var outType = $$('outType');

	if (outType == 0)
		window.common.modal('../sail/out.do?method=rptCanSettle&stafferId=${g_stafferBean.id}&customerId='+customerId+'&firstLoad=1&selectMode=1');
	else
		window.common.modal('../sail/out.do?method=rptCanSwatch&stafferId=${g_stafferBean.id}&customerId='+customerId+'&firstLoad=1&selectMode=1');
}

function getCanSwatch(oos)
{
	getCanSettle(oos);
}

function getCanSettle(oos)
{
	var obj = oos[0];
    
    var tr = getTrObject(proobj);

    setInputValueInTr(tr, 'p_outid', obj.poutid);
    setInputValueInTr(tr, 'p_baseId', obj.pvalue);
    setInputValueInTr(tr, 'p_last', obj.plast);
    setInputValueInTr(tr, 'p_productName', obj.pproductName);
    setInputValueInTr(tr, 'p_amount', obj.plast);
    setInputValueInTr(tr, 'p_price', obj.pprice);

    if (oos.length > 1)
    {
        for(var i=1; i < oos.length; i++)
        {
			tr = addTrInner("tables_pay", "trCopy_pay");
            
            setInputValueInTr(tr, 'p_outid', oos[i].poutid);
            setInputValueInTr(tr, 'p_baseId', oos[i].pvalue);
            setInputValueInTr(tr, 'p_last', oos[i].plast);
            setInputValueInTr(tr, 'p_productName', oos[i].pproductName);
            setInputValueInTr(tr, 'p_amount', oos[i].plast);
            setInputValueInTr(tr, 'p_price', oos[i].pprice);
        }
    }
}

function typeChange()
{
	var outType = $$('outType');
	if (outType == 1)
	{
		$O('cname').value = "公共客户";
		$O('customerId').value = "99";
		$O('cname').disabled=true;
	}else{
		$O('cname').value = "";
		$O('customerId').value = "";
		$O('cname').disabled=false;
	}

	var tb = document.getElementById("tables_pay");

	 var rnum=tb.rows.length;

	 if (rnum > 1)
	 {
		 for(var i=rnum -1; i>0; i--)
		 {
			 tb.deleteRow(i);
		 }
	 }

	 addPayTr();
}

function cc()
{
    var aobj = document.getElementsByName("p_amount");
    var pobj = document.getElementsByName("p_price");

    var total = 0;
    for (var i = 0; i < aobj.length; i++)
    {
        if (aobj[i].value != '' && pobj[i].value != '')
        {
            total +=  parseFloat(aobj[i].value) * parseFloat(pobj[i].value) ;
        }
    }

    var ss =  document.getElementById("total");
    tsts = formatNum(total, 2);
    ss.innerHTML = '(总计:' + tsts + ')';
}

</script>
</head>

<body class="body_class" onload="load()">
<form name="formEntry" action="../sail/out.do" method="post">
<input type="hidden" name="method" value="batchSettleOut">
<input type="hidden" name="customerId" value="${customerId}">  
<input type="hidden" name="stafferId" value="${g_stafferBean.id}"> 

<p:body width="100%">

	<p:line flag="0" />

	<p:subBody width="98%">
	    
		<p:table cells="2">
		    <p:cell title="类型" end="true">
            	<select name="outType" style="width: 300px" values="${outType}" class="select_class" onchange="typeChange()">
            		<option value="0">委托批量转结算</option>
            		<option value="1">领样批量转销售</option>
            		<option value="2">巡展批量转销售</option>
            	</select>
			</p:cell>  
            <p:cell title="客户" end="true">
            <input type="text" style="width: 300px" readonly="readonly" name="cname" value="${cname}" oncheck="notNone;" onclick='selCustomer(this)' />
            <font color="#FF0000">*</font> 
			</p:cell>    
			
			<tr class="content2">
						<td align="left">备注：</td>
						<td colspan="3" align="left"><textarea rows="3" cols="55" oncheck="notNone;"
							name="description">${description}</textarea>
							<font color="#FF0000">*</font>
							</td>
			</tr>   
			
        </p:table>
	</p:subBody>
	

	<p:title>
        <td class="caption">
         <strong>产品行项目</strong>
        </td>
    </p:title>

    <p:line flag="0" />
    
    <tr id="pay_main_tr">
        <td colspan='2' align='center'>
        <table width="98%" border="0" cellpadding="0" cellspacing="0"
            class="border">
            <tr>
                <td>
                <table width="100%" border="0" cellspacing='1' id="tables_pay">
                    <tr align="center" class="content0">
                    	<td width="15%" align="center">单号</td>
                    	<td width="5%" align="center">标识</td>
                        <td width="20%" align="center">产品</td>
                        <td width="5%" align="center">数量</td>
                        <td width="5%" align="center">结算价<span id="total"></span></td>
                        <td width="5%" align="left"><input type="button" accesskey="B"
                            value="增加" class="button_class" onclick="addPayTr()"></td>
                    </tr>
                    
                </table>
                </td>
            </tr>
        </table>

        </td>
    </tr>
    
    <p:line flag="1" />
    
	<p:button leftWidth="98%" rightWidth="0%">
		<div align="right">
		  <input type="button" class="button_class" id="sub_b2"
            value="&nbsp;&nbsp;提 交&nbsp;&nbsp;" onclick="addBean()">
        </div>
	</p:button>
	<p:message2/>
    
</p:body>
</form>
<table>
    <tr class="content1" id="trCopy_pay" style="display: none;">
         <td align="left">
         <input type=text name = 'p_outid' onclick="selectCanSettle(this)"  readonly="readonly"  style="width: 100%" >
         <input type="hidden" name="p_last" value="">
         </td>
         
         <td align="left"><input type="text" style="width: 100%;font-style:italic;" readonly="readonly"
                    name="p_baseId" value="" >
         </td>
         
         <td align="left"><input type="text" style="width: 100%" readonly="readonly"
                    name="p_productName" value="" >
         </td>
         
         <td align="left"><input type="text" oncheck="isNumber;" style="width: 100%"
                    name="p_amount" value="0" onkeyup="cc(this)">
         </td>
         
         <td align="left"><input type="text" oncheck="isFloat" style="width: 100%"
                    name="p_price" value="0.00" onkeyup="cc(this)">
         </td>
         
        <td width="5%" align="center"><input type=button name="pay_del_bu"
            value="&nbsp;删 除&nbsp;" class=button_class onclick="removeTr(this)"></td>
    </tr>
    
</table>

</body>
</html>

