<%@ page contentType="text/html;charset=UTF-8" language="java"
    errorPage="../common/error.jsp"%>
<%@include file="../common/common.jsp"%>
<html>
<head>
<p:link title="查询销售单"/>
<link href="../js/plugin/dialog/css/dialog.css" type="text/css" rel="stylesheet"/>
<script src="../js/title_div.js"></script>
<script src="../js/public.js"></script>
<script src="../js/JCheck.js"></script>
<script src="../js/common.js"></script>
<script src="../js/json.js"></script>
<script src="../js/tableSort.js"></script>
<script src="../js/jquery/jquery.js"></script>
<script src="../js/plugin/dialog/jquery.dialog.js"></script>
<script src="../js/plugin/highlight/jquery.highlight.js"></script>
<script src="../js/adapter.js"></script>
<script language="javascript">
//var distMap = JSON.parse('${mapdJSON}');

function detail()
{
	document.location.href = '../sail/out.do?method=findOut&outId=' + getRadioValue("fullId");
}

function getCustomer(oos)
{
    var obj = oos;
    
    $O("customerName").value = obj.pname;
    
    $O("customerId").value = obj.value;
}

function selectCustomer()
{
    window.common.modal("../client/client.do?method=rptQuerySelfClient&stafferId=${user.stafferId}&load=1");
}


function pagePrint()
{
	window.open('../sail/out.do?method=findOut&fow=4&outId=' + getRadioValue("fullId"));
}

function exports()
{
	if (window.confirm("确定导出当前的全部查询的库单?"))
	document.location.href = '../sail/out.do?method=export';
}

function comp()
{
	var now = '${now}';

	var str1 = $O('outTime').value;

	var str2 = $O('outTime1').value;
	
	if (str1 != '' && str2 == '')
    {
        if (!coo(str1, now))
        {
            alert('查询日期跨度不能大于3个月(90天)!');
            return false;
        }

        $O('outTime1').value = now;
    }

    if (str1 == '' && str2 != '')
    {
        if (!coo(now, str2))
        {
            alert('查询日期跨度不能大于3个月(90天)!');
            return false;
        }

        $O('outTime').value = now;
    }

    if (str1 != '' && str2 != '')
    {
        if (!coo(str1, str2))
        {
            alert('查询日期跨度不能大于3个月(90天)!');
            return false;
        }
    }
	
	

	return true;
}

function comp2()
{
	var now = '${now}';

	var str1 = $O('changeTime').value;

	var str2 = $O('changeTime1').value;
	
	if (str1 != '' && str2 == '')
    {
        if (!coo(str1, now))
        {
            alert('查询日期跨度不能大于3个月(90天)!');
            return false;
        }

        $O('changeTime1').value = now;
    }

    if (str1 == '' && str2 != '')
    {
        if (!coo(now, str2))
        {
            alert('查询日期跨度不能大于3个月(90天)!');
            return false;
        }

        $O('changeTime').value = now;
    }

    if (str1 != '' && str2 != '')
    {
        if (!coo(str1, str2))
        {
            alert('查询日期跨度不能大于3个月(90天)!');
            return false;
        }
    }
	
	

	return true;
}

function comp3()
{
	var now = '${now}';

	var str1 = $O('redateB').value;

	var str2 = $O('redateE').value;
	
	if (str1 != '' && str2 == '')
    {
        if (!coo(str1, now))
        {
            alert('查询日期跨度不能大于3个月(90天)!');
            return false;
        }

        $O('redateE').value = now;
    }

    if (str1 == '' && str2 != '')
    {
        if (!coo(now, str2))
        {
            alert('查询日期跨度不能大于3个月(90天)!');
            return false;
        }

        $O('redateB').value = now;
    }

    if (str1 != '' && str2 != '')
    {
        if (!coo(str1, str2))
        {
            alert('查询日期跨度不能大于3个月(90天)!');
            return false;
        }
    }
	
	

	return true;
}

function coo(str1, str2)
{
	var s1 = str1.split('-');
	var s2 = str2.split('-');

	var year1 = parseInt(s1[0], 10);

	var year2 = parseInt(s2[0], 10);

	var month1 = parseInt(s1[1], 10);

	var month2 = parseInt(s2[1], 10);

	var day1 = parseInt(s1[2], 10);

	var day2 = parseInt(s2[2], 10);

	return Math.abs((year2 - year1) * 365 + (month2 - month1) * 30 + (day2 - day1)) <= 90;
}

function query()
{

	var str1 = $O('outTime').value;

	var str2 = $O('outTime1').value;

	var str3 = $O('changeTime').value;

	var str4 = $O('changeTime1').value;

	var str5 = $O('redateB').value;

	var str6 = $O('redateE').value;

	//必须要有开始和结束时间一个
	if (str1 == '' && str2 == '' && str3 == '' && str4 == '' && str5 == '' && str6 == '')
	{
		alert('必须要选择一种时间');
		return false;
	}
	
	if (comp() || comp2() || comp3())
	{
	    getObj('method').value = 'queryOut';
	    
		adminForm.submit();
	}
}

function res()
{
	$O('customerName').value = '';
	$O("customerId").value = '';
	$O("changeTime").value = '';
	$O("changeTime1").value = '';
	$O("id").value = '';
	$O("stafferName").value = '';
	setSelectIndex($O('status'), 0);
	setSelectIndex($O('pay'), 0);
	setSelectIndex($O('duty'), 0);
	setSelectIndex($O('invoiceStatus'), 0);
	setSelectIndex($O('vtype'), 0);
}

var jmap = new Object();
<c:forEach items="${listOut1}" var="item">
	jmap['${item.fullId}'] = "${divMap[item.fullId]}";
</c:forEach>

function showDiv(id)
{
	tooltip.showTable(jmap[id]);
}

function dialog_open()
{
    $v('dia_inner', true);
}

function load()
{
    var ll = document.getElementsByName('fullId');
    
    for (var i = 0 ; i < ll.length; i++)
    {
        ll[i].index = $$('radioIndex');
    }
    
	loadForm();
	tooltip.init();
	
	bingTable("mainTable");
	
	highlights($("#mainTable").get(0), ['驳回'], 'red');
	
	$('#dlg').dialog({
                modal:true,
                closed:true,
                buttons:{
                    '确 定':function(){
                        centerSubmit();
                    },
                    '取 消':function(){
                        $('#dlg').dialog({closed:true});
                    }
                }
     });
     
     $ESC('dlg');

 	$('#dlg1').dialog({
        modal:true,
        closed:true,
        buttons:{
            '取 消':function(){
                $('#dlg1').dialog({closed:true});
            }
	        }
	});
	
	$ESC('dlg1');
}

function payOut()
{
    if (getRadio('fullId').statuss == 1 && getRadio('fullId').pay == 1)
    {
        if (window.confirm("确定此销售单已经收到货款?"))
        {
	        getObj('method').value = 'payOut';
	        
	        getObj('outId').value = getRadioValue("fullId");
	        
	        adminForm.submit();
        }
    }
    else
    {
        alert('不能操作');
    }
}

function isEnd()
{
    return getRadio('fullId').statuss == 3 || getRadio('fullId').statuss == 4;
}

function payOut2()
{
    if (isEnd() && getRadio('fullId').paytype != 1)
    {
        if (window.confirm("确定此销售单已经全部回款?"))
        {
	        getObj('method').value = 'payOut2';
	        
	        getObj('outId').value = getRadioValue("fullId");
	        
	        adminForm.submit();
        }
    }
    else
    {
        alert('不能操作');
    }
}

function fourcePayOut()
{
    if (isEnd() && getRadio('fullId').paytype != 1)
    {
        if (window.confirm("确定此销售单已经全部回款(仅仅适用于2011-04-01之前的销售单)?"))
        {
            getObj('method').value = 'fourcePayOut';
            
            getObj('outId').value = getRadioValue("fullId");
            
            adminForm.submit();
        }
    }
    else
    {
        alert('不能操作');
    }
}

function payOut3()
{
    if (isEnd() && getRadio('fullId').paytype != 1 && getRadio('fullId').outtype != 1)
    {
        $.messager.prompt('销售单坏账', '请输入销售单坏账金额', getRadio('fullId').baddebts, function(value, isOk){
                if (isOk)
                {
                    if (isFloat(value))
                    {
                        getObj('method').value = 'payOut3';
            
			            getObj('baddebts').value = value;
			            
			            getObj('outId').value = getRadioValue("fullId");
			            
			            adminForm.submit();
                    }
                    else
                    {
                        alert('只能输入金额');
                        $Dbuttons(false);
                    }
                }
                else
                {
                    $Dbuttons(false);
                }
            });
    }
    else
    {
        alert('不能操作');
    }
}

function payOut4()
{
    if (isEnd() && getRadio('fullId').paytype == 1 && parseFloat(getRadio('fullId').baddebts) > 0.0 && getRadio('fullId').outtype != 1)
    {
        if (window.confirm("确定取消坏账?"))
        {
            getObj('method').value = 'payOut4';
            
            getObj('outId').value = getRadioValue("fullId");
            
            adminForm.submit();
        }
    }
    else
    {
        alert('不能操作');
    }
}

function updateInvoiceStatus()
{
    if (isEnd() && getRadio('fullId').invoiceStatus != 1)
    {
        $.messager.prompt('修改发票状态', '请输入销售单已开票金额', '0.0', function(value, isOk){
                if (isOk)
                {
                    if (isFloat(value))
                    {
                        getObj('method').value = 'updateInvoiceStatus';
            
			            getObj('invoiceMoney').value = value;
			            
			            getObj('outId').value = getRadioValue("fullId");
			            
			            adminForm.submit();
                    }
                    else
                    {
                        alert('只能输入金额');
                        $Dbuttons(false);
                    }
                }
                else
                {
                    $Dbuttons(false);
                }
            });
    }
    else
    {
        alert('不能操作');
    }
}

function centerCheck()
{
    if (isEnd())
    {
        $.messager.prompt('总部核对', '请核对说明', '', function(r){
                if (r)
                {
                    $Dbuttons(true);
                    getObj('method').value = 'checks';
                    getObj('outId').value = getRadioValue("fullId");
                    getObj('radioIndex').value = $Index('fullId');
        
                    var sss = r;
        
                    getObj('reason').value = r;
        
                    if (!(sss == null || sss == ''))
                    {
                        adminForm.submit();
                    }
                    else
                    {
                        $Dbuttons(false);
                    }
                }
               
            });
    }
    else
    {
        alert('不能操作');
    }
}

var nextStatusMap = {"1" : 99, "2" : 6, "3" : 7, "4" : 3, "6" : 4, "7" : 6};

var oldStatusMap = {"1" : 8, "2" : 1, "3" : 6, "4" : 7, "6" : 3, "7" : 9};

var queryType = "${queryType}";

// 通过销售单
function check()
{
    if (getRadio('fullId').statuss == oldStatusMap[queryType])
    {
        var hi = '';
        
        if (getRadio('fullId').hasmap == "true")
        {
            hi = "注意:当前业务员下有超期的销售单  ";
        }
        if (getRadio('fullId').reserve9 == "1")
        {
            if (!window.confirm('当前单据的存在产品价格低于成本价,确认是否准备通过?'))
            {
                return false;
            }
        }
        $.messager.prompt('通过', '请输入意见', '同意', function(r){
                    if (r)
                    {
                        $Dbuttons(true);
                        getObj('method').value = 'modifyOutStatus';
                        getObj('statuss').value = nextStatusMap[queryType];
                        
                        getObj('oldStatus').value = getRadio('fullId').statuss;
            
                        getObj('outId').value = getRadioValue("fullId");
            
                        getObj('radioIndex').value = $Index('fullId');
            
                        var sss = r;
            
                        getObj('reason').value = r;
            
                        if (!(sss == null || sss == ''))
                        {
                            adminForm.submit();
                        }
                        else
                        {
                            $Dbuttons(false);
                        }
                    }
                   
                });
    }
    else
    {
        alert('不可以操作!');
    }
}


function centerSubmit()
{
    getObj('method').value = 'modifyOutStatus';

    getObj('statuss').value = 2;
    
    getObj('oldStatus').value = getRadio('fullId').statuss;

    getObj('outId').value = getRadioValue("fullId");

    getObj('radioIndex').value = $Index('fullId');

    getObj('reason').value = $$('passReason');
    
    if (getObj('reason').value == '')
    {
        alert('请输入驳回意见');
        return false;
    }
    
    $Dbuttons(true);
    
    $('#dlg').dialog({closed:true});

    adminForm.submit();
}

function rchange()
{
    $('#passReason').val($$('reasonRadio'));
}

function reject()
{
    if (getRadio('fullId').statuss == oldStatusMap[queryType])
    {
        if (getRadio('fullId').statuss != 1)
        {
	        $.messager.prompt('驳回', '请输入驳回原因', '', function(r){
	                if (r)
	                {
	                    $Dbuttons(true);
	                    getObj('method').value = 'modifyOutStatus';
	                    getObj('statuss').value = '2';
	                    getObj('oldStatus').value = getRadio('fullId').statuss;
	                    getObj('outId').value = getRadioValue("fullId");
	        
	                    getObj('radioIndex').value = $Index('fullId');
	        
	                    var sss = r;
	        
	                    getObj('reason').value = r;
	        
	                    if (!(sss == null || sss == ''))
	                    {
	                        adminForm.submit();
	                    }
	                    else
	                    {
	                        $Dbuttons(false);
	                    }
	                }
	               
	            });
          }
          else
          {
              $('#dlg').dialog({closed:false});   
          }
    }
    else
    {
        alert('不可以操作!');
    }
}

function outBack()
{

	var ret = checkCurrentUser();

	if (!ret)
	{
		window.parent.location.reload();

		return;
	}
	
    //个人领样
    if ((getRadio('fullId').statuss == 3 || getRadio('fullId').statuss == 4) 
            && (getRadio('fullId').outtype == 1 
            		|| getRadio('fullId').outtype == 5 
            		|| getRadio('fullId').outtype == 6))
    {
        $l('../sail/out.do?method=findOut&fow=91&outId=' + getRadioValue("fullId"));
    }
    else
    {
        alert('不能操作');
    }
}

function outBack2()
{

	var ret = checkCurrentUser();

	if (!ret)
	{
		window.parent.location.reload();

		return;
	}
	
    //销售退货
    if ((getRadio('fullId').statuss == 3 || getRadio('fullId').statuss == 4))
    {
        $l('../sail/out.do?method=findOut&fow=92&outId=' + getRadioValue("fullId"));
    }
    else
    {
        alert('不能操作');
    }
}

function applyBackPay()
{
	var ret = checkCurrentUser();

	if (!ret)
	{
		window.parent.location.reload();

		return;
	}
	
    //申请退款
    if (getRadio('fullId').statuss == 3 || getRadio('fullId').statuss == 4)
    {
        $l('../sail/out.do?method=findOut&fow=93&outId=' + getRadioValue("fullId"));
    }
    else
    {
        alert('不能操作');
    }
}

//空开空退
function outRepaire()
{

	var ret = checkCurrentUser();

	if (!ret)
	{
		window.parent.location.reload();

		return;
	}

	//销售出库
	if (getRadio('fullId').outtype != 0)
	{
		alert('不是销售出库类型,不能操作');

		return;
	}
	
    //销售退货
    if ((getRadio('fullId').statuss == 3 || getRadio('fullId').statuss == 4))
    {
        $l('../sail/out.do?method=findOut&fow=921&outId=' + getRadioValue("fullId"));
    }
    else
    {
        alert('不能操作');
    }
}

function swatchToSail()
{
	var ret = checkCurrentUser();

	if (!ret)
	{
		window.parent.location.reload();

		return;
	}
	
	//个人领样
    if ((getRadio('fullId').statuss == 3 || getRadio('fullId').statuss == 4) 
         && (getRadio('fullId').outtype == 1 
        		 || getRadio('fullId').outtype == 5
        		 || getRadio('fullId').outtype == 6))
    {
    	if (window.confirm("确定领样转销售?"))
        $l('../sail/out.do?method=swatchToSail&outId=' + getRadioValue("fullId"));
    }
    else
    {
        alert('不能操作');
    }
}

//选择自动联想radio
function hrefAndSelect(obj)
{
    //$Set(radio, obj.indexs);
    var tr = getTrObject(obj);

    if (tr != null)
    {
        var rad = tr.getElementsByTagName('input');

        for (i = 0; i < rad.length; i++)
        {
            if (rad[i].type.toLowerCase() == 'checkbox')
            {
                rad[i].checked = !rad[i].checked;
            }
            
            if (rad[i].type.toLowerCase() == 'radio')
            {
                rad[i].checked = true;
            }
        }
    }
    
    getObj('radioIndex').value = $Index('fullId');
}

var showTr = false;

function showQueryConditionTr()
{
    $v('queryCondition', showTr);
    
    showTr = !showTr;

    if (showTr){

        $O("queryConditionText").innerHTML = '显示查询条件';
    }
    else{
    	$O("queryConditionText").innerHTML = '隐藏查询条件';
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

function printDist()
{
	var fullid = getRadio('fullId').value;

	$ajax('../sail/out.do?method=queryDistributionByOut&outId=' + fullid, callBackFunPrint);
}

function callBackFunPrint(data)
{
	var htm='';

	var obj = data.msg;

	for(var i = 0; i < obj.length; i++)
	{
		var url = '../sail/out.do?method=printOut&id=' + obj[i].id + '&fullId='+obj[i].outId;
		var ahtm = "<li><a href=" + url + ">" + obj[i].id + "</a></li> <br>";
		htm += ahtm;
	}
	
	$v('dia_inner1', true);
	$O('dia_inner1').innerHTML = htm;
    
    $('#dlg1').dialog({closed:false});
}

function selectPrincipalship()
{
    window.common.modal('../admin/pop.do?method=rptQueryPrincipalship&load=1&selectMode=1');
}

function getPrincipalship(oos)
{
    var oo = oos[0];

    $O('industryId').value = oo.value;
    $O('industryName').value = oo.pname;   
}

function clears()
{
	$O('industryId').value = '';
	$O('industryName').value = '';
}

</script>

</head>
<body class="body_class" onkeypress="tooltip.bingEsc(event)" onload="load()">
<form action="../sail/out.do" name="adminForm"><input type="hidden"
	value="queryOut" name="method"> <input type="hidden" value="1"
	name="firstLoad">
	<input type="hidden" value="${ppmap.customerId}"
	name="customerId">
	<input type="hidden" value="${queryType}"
	name="queryType">
	<input type="hidden" value=""
	name="outId">
<input type="hidden" value="" name="oldStatus">
<input type="hidden" value="" name="statuss">
<input type="hidden" value="${ppmap.radioIndex}" name="radioIndex">
<input type="hidden" value="" name="reason">
<input type="hidden" value="" name="baddebts">
<input type="hidden" value="" name="invoiceMoney">
<input type="hidden" value="${ppmap.industryId}"
	name="industryId">

<c:set var="fg" value='销售'/>

<p:navigation
    height="22">
    <td width="550" class="navigation">库单管理 &gt;&gt; 查询销售单${queryType}</td>
                <td width="85"></td>
</p:navigation> <br>

<table width="98%" border="0" cellpadding="0" cellspacing="0"
	align="center">

	<p:title>
		<td class="caption">	
		<strong><span id="queryConditionText" style="cursor: pointer;" onclick="showQueryConditionTr()">隐藏查询条件</span></strong>
		</td>
	</p:title>

	<tr id="queryCondition">
		<td align='center' colspan='2'>
		<table width="100%" border="0" cellpadding="0" cellspacing="0"
			class="border">
			<tr>
				<td>
				<table width="100%" border="0" cellspacing='1'>
					<tr class="content1">
						<td width="15%" align="center">开始时间</td>
						<td align="center" width="35%"><p:plugin name="outTime" size="20" value="${ppmap.outTime}"/></td>
						<td width="15%" align="center">结束时间</td>
						<td align="center"><p:plugin name="outTime1" size="20" value="${ppmap.outTime1}"/>
						</td>
					</tr>
					
					<tr class="content2">
                        <td width="15%" align="center">发货时间从</td>
                        <td align="center" width="35%"><p:plugin name="changeTime" type="0" size="20" value="${ppmap.changeTime}"/></td>
                        <td width="15%" align="center">到</td>
                        <td align="center"><p:plugin name="changeTime1" type="0" size="20" value="${ppmap.changeTime1}"/>
                        </td>
                    </tr>
                    
                    <tr class="content1">
                        <td width="15%" align="center">回款时间从</td>
                        <td align="center" width="35%"><p:plugin name="redateB" type="0" size="20" value="${ppmap.redateB}"/></td>
                        <td width="15%" align="center">到</td>
                        <td align="center"><p:plugin name="redateE" type="0" size="20" value="${ppmap.redateE}"/>
                        </td>
                    </tr>

					<tr class="content2">
						<td width="15%" align="center">销售单状态</td>
						<td align="center">
						<select name="status" class="select_class" values="${ppmap.status}">
							<option value="">--</option>
							<p:option type="outStatus"/>
							<option value="99">发货态</option>
						</select>
						</td>
						
						<c:if test="${queryType == '8'}">
						<td width="15%" align="center">客户：</td>
                        <td align="center">
                        <input type="text" name="customerName" maxlength="14" value="${ppmap.customerName}"
                            onclick="selectCustomer()" style="cursor: pointer;"
                            readonly="readonly">
                        </td>
						</c:if>
						
						<c:if test="${queryType != '8'}">
                        <td width="15%" align="center">客户：</td>
                        <td align="center"><input type="text" name="customerName" maxlength="14" value="${ppmap.customerName}"></td>
                        </c:if>
						
					</tr>

					<tr class="content1">
						<td width="15%" align="center">销售类型</td>
						<td align="center">
						<select name="outType"
							class="select_class" values=${ppmap.outType}>
							<option value="">--</option>
							<p:option type="outType_out"></p:option>
						</select>

						</td>
						<td width="15%" align="center">销售单号</td>
						<td align="center"><input type="text" name="id" value="${ppmap.id}"></td>
					</tr>

					<tr class="content2">
						<td width="15%" align="center">是否回款</td>
						<td align="center" colspan="1"><select name="pay" values="${ppmap.pay}"
							class="select_class">
							<option value="">--</option>
							<option value="1">是</option>
							<option value="0">否</option>
							<option value="2">超期</option>
						</select></td>
						
						<td width="15%" align="center">仓库</td>
                        <td align="center">
                        <select name="location"
                            class="select_class" values=${ppmap.location}>
                            <option value="">--</option>
                            <c:forEach items="${depotList}" var="item">
                             <option value="${item.id}">${item.name}</option>
                            </c:forEach>
                        </select>
                        </td>
					</tr>
					
					<tr class="content1">
						<td width="15%" align="center">纳税实体</td>
						<td align="center">
						<select name="duty"
							class="select_class" values=${ppmap.duty}>
							<option value="">--</option>
							<c:forEach items='${dutyList}' var="item">
								<option value="${item.id}">${item.name}</option>
							</c:forEach>
						</select>
						</td>
						<td width="15%" align="center">销售人员</td>
						<td align="center"><input type="text" name="stafferName" value="${ppmap.stafferName}"></td>
					</tr>
					
					<tr class="content2">
                        <td width="15%" align="center">开票状态</td>
                        <td align="center" colspan="1"><select name="invoiceStatus" values="${ppmap.invoiceStatus}"
                            class="select_class">
                            <option value="">--</option>
                            <option value="0">可开票</option>
                            <option value="1">全部开票</option>
                        </select></td>
                        
                        <td width="15%" align="center">关注状态</td>
                        <td align="center" colspan="1"><select name="vtype" values="${ppmap.vtype}"
                            class="select_class">
                            <p:option type="outVtype" empty="true"></p:option>
                        </select>
                        </td>
                    </tr>

					<tr class="content2">
                        <td width="15%" align="center">经办人</td>
                       <td align="center"><input type="text" name="operatorName" value="${ppmap.operatorName}"></td>
                        
                        <td width="15%" align="center">事业部</td>
                        <td align="center">
                        <input type="text" name="industryName" value="${ppmap.industryName}" readonly="readonly" onClick="selectPrincipalship()">
                        <input
								type="button" value="清空" name="qout" id="qout"
								class="button_class" onclick="clears()">&nbsp;&nbsp;
                        </td>
                    </tr>

					<tr class="content2">
						<td width="15%" align="center">银行导入</td>
                        <td align="center" colspan="1"><select name="isBank" values="${ppmap.isBank}"
                            class="select_class">
                            <option value="">--</option>
                            <option value="0">是</option>
                            <option value="1">否</option>
                        </select></td>

						<td colspan="2" align="right"><input type="button" id="query_b"
							onclick="query()" class="button_class"
							value="&nbsp;&nbsp;查 询&nbsp;&nbsp;">&nbsp;&nbsp;
							<input type="button" onclick="res()" class="button_class" value="&nbsp;&nbsp;重 置&nbsp;&nbsp;"></td>
					</tr>
				</table>
				</td>
			</tr>
		</table>
		</td>
	</tr>

	<tr>
		<td valign="top" colspan='2'>
		<table width="100%" border="0" cellpadding="0" cellspacing="0">
			<!--DWLayoutTable-->
			<tr>
				<td width="784" height="6"></td>
			</tr>
			<tr>
				<td align="center" valign="top">
				<div align="left">
				<table width="90%" border="0" cellspacing="2">
					<tr>
						<td>
						<table width="100%" border="0" cellpadding="0" cellspacing="10">
							<tr>
								<td width="35">&nbsp;</td>
								<td width="6"><img src="../images/dot_r.gif" width="6"
									height="6"></td>
								<td class="caption"><strong>浏览${fg}单:</strong>
								<c:if test="${queryType == '1'}">
								<font color=blue>[当前您剩余的信用:${credit}]</font>
								</c:if>
								</td>
							</tr>
						</table>
						</td>
					</tr>
				</table>
				</td>
			</tr>
		</table>
		</td>
	</tr>


	<tr>
		<td background="../images/dot_line.gif" colspan='2'></td>
	</tr>

	<tr>
		<td height="10" colspan='2'></td>
	</tr>

	<tr>
		<td align='center' colspan='2'>
		<table width="100%" border="0" cellpadding="0" cellspacing="0"
			class="border">
			<tr>
				<td>
				<table width="100%" border="0" cellspacing='1' id="mainTable">
					<tr align="center" class="content0">
						<td align="center" width="5%" align="center">选择</td>
						<td align="center" onclick="tableSort(this)" class="td_class">单据编号</td>
						<td align="center" onclick="tableSort(this)" class="td_class">客户</td>
						<td align="center" onclick="tableSort(this)" class="td_class">状态</td>
						<td align="center" onclick="tableSort(this)" class="td_class">${fg}类型</td>
						<td align="center" onclick="tableSort(this)" class="td_class">${fg}时间</td>
						<c:if test="${queryType == '5' || queryType == '6'}">
						<td align="center" onclick="tableSort(this)" class="td_class">库管通过</td>
						</c:if>
						<c:if test="${queryType != '5' && queryType != '6'}">
                        <td align="center" onclick="tableSort(this)" class="td_class">结算通过</td>
                        </c:if>
						<td align="center" onclick="tableSort(this)" class="td_class">回款日期</td>
						<td align="center" onclick="tableSort(this)" class="td_class">付款方式</td>
						<td align="center" onclick="tableSort(this)" class="td_class">超期(天)</td>
						<td align="center" onclick="tableSort(this)" class="td_class">金额</td>
						<td align="center" onclick="tableSort(this)" class="td_class">付款</td>
						<td align="center" onclick="tableSort(this)" class="td_class">${fg}人</td>
						<td align="center" onclick="tableSort(this)" class="td_class">仓库</td>
						<td align="center" onclick="tableSort(this)" class="td_class">发货单</td>
					</tr>

					<c:forEach items="${listOut1}" var="item" varStatus="vs">
					    <c:if test="${queryType == '6' && item.status == 3}">
                        <c:set var="pcheck" value="&check=1"></c:set>
                        </c:if>
                        <c:if test="${queryType != '6' || item.status != 3}">
                        <c:set var="pcheck" value=""></c:set>
                        </c:if>
						<tr class='${vs.index % 2 == 0 ? "content1" : "content2"}'
						>
							<td align="center"><input type="radio" name="fullId" 
							   temptype="${item.tempType}"
							   hasmap="${hasMap[item.fullId]}"
							   ${vs.index == 0 ? "checked=checked" : ""}
							   con="${item.consign}"
							   pay="${item.reserve3}"
							   paytype="${item.pay}"
							   outtype="${item.outType}"
							   statuss='${item.status}' 
							   reserve9='${item.reserve9}' 
							   baddebts='${my:formatNum(item.total - item.hadPay)}' 
							   value="${item.fullId}"/></td>
							<td align="center"
							onMouseOver="showDiv('${item.fullId}')" onmousemove="tooltip.move()" onmouseout="tooltip.hide()"><a onclick="hrefAndSelect(this)" href="../sail/out.do?method=findOut&radioIndex=${vs.index}&fow=99&outId=${item.fullId}${pcheck}">
							${item.fullId}</a></td>
							<td align="center" onclick="hrefAndSelect(this)">${item.customerName}</td>
							<td align="center" onclick="hrefAndSelect(this)">${my:get('outStatus', item.status)}</td>
							<td align="center" onclick="hrefAndSelect(this)">${my:get('outType_out', item.outType)}</td>
							<td align="center" onclick="hrefAndSelect(this)">${item.outTime}</td>
							
							<c:if test="${queryType != '5' && queryType != '6'}">
							<td align="center" onclick="hrefAndSelect(this)">${item.managerTime}</td>
							</c:if>
							<c:if test="${queryType == '5' || queryType == '6'}">
	                        <td align="center" onclick="hrefAndSelect(this)">${item.changeTime}</td>
	                        </c:if>
                        
							<c:if test="${item.pay == 0}">
							<td align="center" onclick="hrefAndSelect(this)"><font color=red>${item.redate}</font></td>
							</c:if>
							<c:if test="${item.pay == 1}">
							<td align="center" onclick="hrefAndSelect(this)"><font color=blue>${item.redate}</font></td>
							</c:if>
							<c:if test="${item.reserve3 == 1}">
							<td align="center" onclick="hrefAndSelect(this)">款到发货(黑名单客户/零售)</td>
							</c:if>
							<c:if test="${item.reserve3 == 2}">
							<td align="center" onclick="hrefAndSelect(this)">客户信用和业务员信用额度担保</td>
							</c:if>
							<c:if test="${item.reserve3 == 3}">
							<td align="center" onclick="hrefAndSelect(this)">信用担保</td>
							</c:if>
							<td align="center" onclick="hrefAndSelect(this)">${overDayMap[item.fullId]}</td>
							<td align="center" onclick="hrefAndSelect(this)">${my:formatNum(item.total)}</td>
							<td align="center" onclick="hrefAndSelect(this)">${my:formatNum(item.hadPay)}</td>
							<td align="center" onclick="hrefAndSelect(this)">${item.stafferName}</td>
							<td align="center" onclick="hrefAndSelect(this)">${item.depotName}</td>
							<c:if test="${queryType == '3'}">
							<td align="center" onclick="hrefAndSelect(this)"><a
							href="../sail/transport.do?method=findConsign&fullId=${item.fullId}"
							>${my:get("consignStatus", item.consign)}</a></td>
							</c:if>
							<c:if test="${queryType != '3'}">
                            <td align="center" onclick="hrefAndSelect(this)">
                            <a
                            href="../sail/transport.do?method=findConsign&forward=2&fullId=${item.fullId}"
                            >
                            ${my:get("consignStatus", item.consign)}
                            </a>
                            </td>
                            </c:if>
						</tr>
					</c:forEach>
				</table>

				<p:formTurning form="adminForm" method="queryOut"></p:formTurning>
				</td>
			</tr>
		</table>
		</td>
	</tr>

	<tr>
		<td height="10" colspan='2'></td>
	</tr>


	<tr>
		<td background="../images/dot_line.gif" colspan='2'></td>
	</tr>

	<tr>
		<td height="10" colspan='2'></td>
	</tr>

	<c:if test="${my:length(listOut1) > 0}">
	<tr>
		<td width="100%">
		<div align="right">
		
		<c:if test="${queryType != '5' && queryType != '6' 
		  && queryType != '8' && queryType != '9' && queryType != '10' && queryType != '11'}">
		
			<c:if test="${queryType == '2'}">
				<input type="button" class="button_class" style="display: none;"
		                value="&nbsp;&nbsp;确认回款&nbsp;&nbsp;" onClick="payOut()">&nbsp;&nbsp;
	        </c:if>
	        
	        <input name="bu1"
	                type="button" class="button_class" value="&nbsp;审核通过&nbsp;"
	                onclick="check()" />&nbsp;&nbsp;<input type="button" name="bu2"
	                class="button_class" value="&nbsp;&nbsp;驳 回&nbsp;&nbsp;"
	                onclick="reject()" />&nbsp;&nbsp;
	                <!--
	                <c:if test="${queryType == '4'}">
	                <input type="button" name="bu_pridist"
	                class="button_class" value="&nbsp;&nbsp;打印配送单&nbsp;&nbsp;"
	                onclick="printDist()" />&nbsp;&nbsp;
	                </c:if>-->
		</c:if>	
		
		<c:if test="${queryType == '5'}">
		<input type="button" class="button_class"
                value="&nbsp;&nbsp;确认回款&nbsp;&nbsp;" onClick="payOut2()"/>&nbsp;&nbsp;
        <input type="button" class="button_class"
                value="&nbsp;&nbsp;强制回款&nbsp;&nbsp;" onClick="fourcePayOut()"/>&nbsp;&nbsp;
        
        <input type="button" class="button_class"
                value="&nbsp;&nbsp;确认坏账&nbsp;&nbsp;" onClick="payOut3()"/>&nbsp;&nbsp;
        <input type="button" class="button_class"
                value="&nbsp;&nbsp;坏账取消&nbsp;&nbsp;" onClick="payOut4()"/>&nbsp;&nbsp;
        
        <c:if test="${my:auth(user, '1418')}">
        <input
                type="button" class="button_class"
                value="&nbsp;修改发票状态&nbsp;" onclick="updateInvoiceStatus()" />&nbsp;&nbsp;
        </c:if>
        
		</c:if>
		
		<c:if test="${queryType == '6'}">
        <input type="button" class="button_class"
                value="&nbsp;&nbsp;总部核对&nbsp;&nbsp;" onClick="centerCheck()"/>&nbsp;&nbsp;
        </c:if>
        
        <c:if test="${queryType == '9'}">
        <input type="button" class="button_class"
                value="&nbsp;&nbsp;领样巡展退库&nbsp;&nbsp;" onClick="outBack()"/>&nbsp;&nbsp;
        <input type="button" class="button_class"
                value="&nbsp;&nbsp;领样巡展转销售&nbsp;&nbsp;" onClick="swatchToSail()"/>&nbsp;&nbsp;
        </c:if>
        
        <c:if test="${queryType == '8'}">
        <input type="button" class="button_class"
                value="&nbsp;&nbsp;销售退单&nbsp;&nbsp;" onClick="outBack2()"/>&nbsp;&nbsp;
        <input type="button" class="button_class"
                value="&nbsp;&nbsp;申请退款&nbsp;&nbsp;" onClick="applyBackPay()"/>&nbsp;&nbsp;
                <input type="button" class="button_class"
                value="&nbsp;&nbsp;空开空退&nbsp;&nbsp;" onClick="outRepaire()"/>&nbsp;&nbsp;
        </c:if>
        <c:if test="${my:auth(user, '1417') || queryType == '10'}">
        <input
                type="button" class="button_class"
                value="&nbsp;导出查询结果&nbsp;" onclick="exports()" />&nbsp;&nbsp;
        </c:if>
        
		</div>
		</td>
		<td width="0%"></td>
	</tr>
	
	</c:if>

	<tr height="10">
		<td height="10" colspan='2'></td>
	</tr>

	<p:message2/>
</table>

</form>
<div id="dlg" title="结算中心驳回" style="width:320px;height:300px;">
    <div style="padding:20px;height:200px;display:none" id="dia_inner" title="" >
    意见：<input type="text" name="passReason" id="passReason" value=""><br>
    <input type="radio" name="reasonRadio" value="批价错误" onclick="rchange()">批价错误<br>
    <input type="radio" name="reasonRadio" value="帐期错误" onclick="rchange()">帐期错误<br>
    <input type="radio" name="reasonRadio" value="品名错误" onclick="rchange()">品名错误<br>
    <input type="radio" name="reasonRadio" value="黑名单人员" onclick="rchange()">黑名单人员<br>
    <input type="radio" name="reasonRadio" value="款到发货客户" onclick="rchange()">款到发货客户<br>
    <input type="radio" name="reasonRadio" value="金质产品需要先款后货" onclick="rchange()">金质产品需要先款后货<br>
   </div>
</div>

<div id="dlg1" title="配送单" style="width:320px;height:300px;">
    <div style="padding:20px;height:200px;display:none" id="dia_inner1" title="" >
    
   </div>
</div>
</body>
</html>
