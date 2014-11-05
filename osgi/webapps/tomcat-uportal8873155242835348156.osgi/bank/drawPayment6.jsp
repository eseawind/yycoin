<%@ page contentType="text/html;charset=UTF-8" language="java"
    errorPage="../common/error.jsp"%>
<%@include file="../common/common.jsp"%>
<html>
<head>
<p:link title="预收勾应收" />
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
	
    submit('确定批量勾款?', null, check);
}

function check()
{
    var commonTotalMoney = ${my:formatNum(prePayment.commonTotalMoney)};
    var manageTotalMoney = ${my:formatNum(prePayment.manageTotalMoney)};
    
    var common = 0.0;
    var manage = 0.0;

	var outMoneys = document.getElementsByName("p_outMoney");
	var mtypes = document.getElementsByName("p_mtype");

	if (outMoneys.length == 1)
	{
		alert('请增加行项目 ');
		return false;
	}
	
	if (outMoneys.length > 21)
	{
		//alert('不能超过20行');
		//return false;
	}
    
    for (var i = 0; i < outMoneys.length; i++)
    {
        if (!isNone(outMoneys[i].value))
        {
            if (mtypes[i].value == '1')
            {
            	common += parseFloat(outMoneys[i].value);
            }else{
            	manage += parseFloat(outMoneys[i].value);
            }
        }
    }
    
    if (manageTotalMoney < manage)
    {
    	alert('管理类预收使用溢出,请核对');
    	
        return false;
    }

    if (commonTotalMoney < common)
    {
        if (manageTotalMoney < (manage + common - commonTotalMoney ))
        {
        	alert('管理类预收使用溢出,请核对');
        	
            return false;
        }
    }
    
    return true;
}


var gobj = null;

var gall = 0;

function opens(obj)
{
    if ($O('customerId').value == '')
    {
        alert('请选择客户');
        return false;
    }

	gobj = obj;
	gall = 0;
    
    if ($O('customerId').value == '99')
    {
        window.common.modal('../sail/out.do?method=rptQueryOut&selectMode=0&mode=0&load=1&nopay=1&stafferId=${user.stafferId}&customerId=' + $$('customerId'));
    }
    else
    {
        window.common.modal('../sail/out.do?method=rptQueryOut&selectMode=0&mode=0&load=1&nopay=1&customerId=' + $$('customerId'));    
    }
}

// 批量获取销售单
function opensAll()
{
    if ($O('customerId').value == '')
    {
        alert('请选择客户');
        return false;
    }

    gall = 1;
    
    if ($O('customerId').value == '99')
    {
        window.common.modal('../sail/out.do?method=rptQueryOut&selectMode=1&mode=0&load=1&nopay=1&stafferId=${user.stafferId}&customerId=' + $$('customerId'));
    }
    else
    {
        window.common.modal('../sail/out.do?method=rptQueryOut&selectMode=1&mode=0&load=1&nopay=1&customerId=' + $$('customerId'));    
    }
}

function openBalance(obj)
{
    if ($O('customerId').value == '')
    {
        alert('请选择客户');
        
        return false;
    }

    gobj = obj;
    gall = 0;
    
    window.common.modal('../sail/out.do?method=rptQueryOutBalance&pay=0&type=0&selectMode=0&load=1&nopay=1&customerId=' + $$('customerId'));
}

function openBalanceAll()
{
    if ($O('customerId').value == '')
    {
        alert('请选择客户');
        
        return false;
    }

    gall = 1;
    
    window.common.modal('../sail/out.do?method=rptQueryOutBalance&pay=0&type=0&selectMode=1&load=1&nopay=1&customerId=' + $$('customerId'));
}

function getOutBalance(oos)
{
    return getOut(oos);
}

function getOut(oos)
{
	if (gall == 0)
	{
		 var tr = getTrObject(gobj);
		    
		 setInputValueInTr(tr, 'p_outId', oos[0].value);
		 setInputValueInTr(tr, 'p_mtype', oos[0].pmtype);
		 setInputValueInTr(tr, 'p_outMoney', oos[0].plast);
	}else{
		for(var i = 0; i < oos.length; i++)
		{
			var trow = addTr();
			
			setInputValueInTr(trow, 'p_outId', oos[i].value);
			setInputValueInTr(trow, 'p_mtype', oos[i].pmtype);
			setInputValueInTr(trow, 'p_outMoney', oos[i].plast);
		}
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

function addTr()
{
    var trow = addTrInner("tables", "trCopy");

    return trow;
}

function removeTr(obj)
{
    obj.parentNode.parentNode.removeNode(true);
}

function load()
{
	for (var i=0; i < 1 ; i++)
	{
		addTr();
	}
}

</script>

</head>
<body class="body_class" onload="load()">
<form name="formEntry" action="../finance/bank.do" method="post">
<input type="hidden" name="method" value="drawPayment6"> 
<input type="hidden" name="customerId" value="${prePayment.customerId}"> 
<input type="hidden" name="commonTotalMoney" value="${prePayment.commonTotalMoney}"> 
<input type="hidden" name="manageTotalMoney" value="${prePayment.manageTotalMoney}"> 
<p:navigation
    height="22">
    <td width="550" class="navigation"><span style="cursor: pointer;"
        onclick="javascript:history.go(-1)">预收管理</span> &gt;&gt; 预收勾应收</td>
    <td width="85"></td>
</p:navigation> <br>

<p:body width="98%">

    <p:title>
        <td class="caption"><strong>预收勾应收</strong></td>
    </p:title>

    <p:line flag="0" />

    <p:subBody width="100%">

        <p:table cells="1">
        
            <p:cell title="客户">
               ${prePayment.customerName}
            </p:cell>

            <p:cell title="预收金额">
               ${my:formatNum(prePayment.totalMoney)}
            </p:cell>
            
            <p:cell title="普通预收金额">
               ${my:formatNum(prePayment.commonTotalMoney)}
            </p:cell>
            
            <p:cell title="管理预收金额">
               ${my:formatNum(prePayment.manageTotalMoney)}
            </p:cell>
            
             <p:cell title="备注">
               <textarea rows=3 cols=55  oncheck="maxLength(200);" name="description"></textarea>
            </p:cell>
            
        </p:table>

    </p:subBody>
    
    <p:line flag="0" />
    
	<tr>
        <td colspan='2' align='center'>
        <table width="100%" border="0" cellpadding="0" cellspacing="0"
            class="border">
            <tr>
                <td>
                <table width="100%" border="0" cellspacing='1' id="tables">
                    <tr align="center" class="content0">
                        <td width="15%" align="left">关联单据</td>
                        <td width="10%" align="left">回款金额</td>
                        <td width="75%" align="center">
                        &nbsp;<input type="button" value="&nbsp;批量销售单&nbsp;" name="qout" id="qout"
	                        class="button_class" onclick="opensAll()">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	                    <input type="button" value="&nbsp;批量委托清单&nbsp;" name="qout" id="qout"
	                    class="button_class" onclick="openBalanceAll()">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                        <input type="button" accesskey="A"
                            value="&nbsp;&nbsp;增 加&nbsp;&nbsp;" class="button_class" onclick="addTr()"></td>
                    </tr>
                </table>
                </td>
            </tr>
        </table>

        </td>
    </tr>

	<p:line flag="1" />

    <p:button leftWidth="100%" rightWidth="0%">
        <div align="right"><input type="button" class="button_class"
            id="ok_b" style="cursor: pointer" value="&nbsp;&nbsp;确 定&nbsp;&nbsp;"
            onclick="addBean()"></div>
    </p:button>

    <p:message2 />
</p:body>
</form>
<table>
    <tr class="content1" id="trCopy" style="display: none;">
 		<td align="left">
 			<input type="text" style="width: 100%" readonly="readonly" name="p_outId" value="" >
 			<input type="hidden" name="p_mtype" value="" >
         </td>         
         <td align="left"><input type="text" style="width: 100%"
                    name="p_outMoney" value="" oncheck="isFloat2"></td>
        <td width="75%" align="center">
	        &nbsp;<input type="button" value="&nbsp;销售单&nbsp;" name="qout" id="qout"
	                        class="button_class" onclick="opens(this)">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	                    <input type="button" value="&nbsp;委托清单&nbsp;" name="qout" id="qout"
	                    class="button_class" onclick="openBalance(this)">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	                    <input type="button" value="&nbsp;清 空&nbsp;" name="qout" id="qout"
	                        class="button_class" onclick="clears(this)">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	        <input type=button
	            value="&nbsp;删 除&nbsp;" class=button_class onclick="removeTr(this)">
        </td>
    </tr>
</table>

</body>
</html>

