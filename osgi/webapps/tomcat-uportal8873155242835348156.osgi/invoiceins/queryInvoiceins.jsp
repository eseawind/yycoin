<%@page contentType="text/html; charset=UTF-8"%>
<%@include file="../common/common.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<style type="text/css">
.flexigrid div.fbutton .draw
{
    background: url(../css/flexigrid/images/get.png) no-repeat center left;
}

.flexigrid div.fbutton .odraw
{
    background: url(../css/flexigrid/images/oget.png) no-repeat center left;
} 
</style>
<p:link title="开票管理" link="true" guid="true" cal="true" dialog="true" />
<script src="../js/common.js"></script>
<script src="../js/public.js"></script>
<script src="../js/pop.js"></script>
<script src="../js/plugin/highlight/jquery.highlight.js"></script>
<script src="../js/jquery.blockUI.js"></script>
<script src="../stockapply_js/scheck.js"></script>
<script type="text/javascript">

var gurl = '../finance/invoiceins.do?method=';
var addUrl = '../invoiceins/addInvoiceins.jsp';
var ukey = 'Invoiceins';

var allDef = getAllDef();
var guidMap;
var thisObj;

var mode = '<p:value key="mode"/>';

function load()
{
     preload();
     
     guidMap = {
         title: '开票列表',
         url: gurl + 'query' + ukey + '&mode=' + mode,
         colModel : [
             {display: '选择', name : 'check', content : '<input type=radio name=checkb value={id} lstatus={status} lotype={otype}>', width : 40, align: 'center'},
             {display: '类型', name : 'otype', width : '8%', cc:'invoiceinsOtype'},
             {display: '开票抬头', name : 'headContent', width : '10%'},
             {display: '纳税实体', name : 'dutyName', width : '10%'},
             {display: '客户', name : 'customerName', width : '18%'},
             {display: '发票类型', name : 'invoiceName', cc: 'bankType', width : '15%'},
             {display: '状态', name : 'status', cc: 'invoiceinsStatus', width : '10%'},
             {display: '类型', name : 'type', cc: 'invoiceinsType', width : '10%'},
             {display: '金额', name : 'moneys', width : '10%', toFixed: 2},
             {display: '开票人', name : 'stafferName', width : '8%'},
             {display: '时间', name : 'logTime', width : 'auto', sortable : true}
             ],
         extAtt: {
             dutyName : {begin : '<a title="点击查看详细" href=' + gurl + 'find' + ukey + '&id={id}&mode=' + mode + '>', end : '</a>'}
         },
         buttons : [
             <c:if test="${mode == 0}">
             {id: 'add', bclass: 'add', caption: '申请开票(2012前)', onpress : addBean, auth: '1401'},
             {id: 'add1', bclass: 'add', caption: '申请开票(2012后)', onpress : addBean1, auth: '1401'},
             {id: 'addRet', bclass: 'add', caption: '申请退票', onpress : backInvoiceins, auth: '1401'},
             {id: 'update', bclass: 'update', onpress : updateBean, auth: '1401'},
             {id: 'del1', bclass: 'del', caption: '删除', onpress : delBean1, auth: '1401'},
             </c:if>
             <c:if test="${mode == 1}">
             {id: 'pass', bclass: 'pass', caption: '处理', onpress : doProcess, auth: '1604'},
             </c:if>
             <c:if test="${mode == 2}">
             {id: 'add2', bclass: 'add', caption: '对分公司开票', onpress : addBean2, auth: '1604'},
             {id: 'del', bclass: 'del',  onpress : delBean, auth: '1605'},
             {id: 'check', bclass: 'pass', caption: '总部核对', onpress : checkBean, auth: '1608'},
             {id: 'export', bclass: 'replied', caption: '导出查询结果', onpress : exports, auth: '1604'},
             {id: 'export', bclass: 'replied', caption: '导出查询结果2', onpress : exports2, auth: '1604'},
             {id: 'confirmInvoice', bclass: 'pass', caption: '确认开票', onpress : confirmInvoice, auth: '1604'},
             {id: 'confirmPay', bclass: 'pass', caption: '确认付款', onpress : confirmPay, auth: '1604'},
             </c:if>
             <c:if test="${mode == 3}">
             {id: 'pass', bclass: 'pass', caption: '处理', onpress : doProcess2, auth: '1610'},
             </c:if>
             <c:if test="${mode == 4}">
             {id: 'autoadd1', bclass: 'add', caption: '个人开票', onpress : autoadd1, auth: '1401'},
             {id: 'autoadd2', bclass: 'add', caption: '全部开票', onpress : autoadd, auth: '1604'},
             </c:if>
             
             {id: 'search', bclass: 'search', onpress : doSearch}
             ],
        <p:conf/>
     };
     
     $("#mainTable").flexigrid(guidMap, thisObj);

     $('#destStafferDIV').dialog({
         modal:true,
         closed:true,
         buttons:{
             '确 定':function(){
    	 		autoadd();
             },
             '取 消':function(){
                 $('#destStafferDIV').dialog({closed:true});
             }
         }
	});

     $ESC('destStafferDIV');
}
 
function $callBack()
{
    loadForm();

    highlights($("#mainTable").get(0), ['驳回'], 'red');
    highlights($("#mainTable").get(0), ['保存'], 'red');
}

function addBean(opr, grid)
{
    $l(gurl + 'preForAdd' + ukey + '&mode=' + mode);
    //$l(addUrl);
}

function addBean1(opr, grid)
{
    $l(gurl + 'preForAdd' + ukey + '1&mode=' + mode);
    //$l(addUrl);
}

function addBean2(opr, grid)
{
    $l(gurl + 'preForAdd' + ukey + '2&mode=' + mode);
    //$l(addUrl);
}

function backInvoiceins()
{
	if (getRadio('checkb') && getRadioValue('checkb'))
	{	
		var status = getRadio('checkb').lstatus;

		var otype = getRadio('checkb').lotype;

		if (status == 99 && otype == 0)
		{
			$l(gurl + 'find' + ukey + '&update=3&id=' + getRadioValue('checkb'));
		}
		else
			$error('不能操作');
	}
	else
	$error('不能操作');
}

function updateBean()
{
	if (getRadio('checkb') && getRadioValue('checkb'))
	{	
		var status = getRadio('checkb').lstatus;

		if (status == 3 || status == 98)			
		{
			$l(gurl + 'find' + ukey + '&update=2&id=' + getRadioValue('checkb'));
		}
		else
			$error('不能操作');
	}
	else
	$error('不能操作');
}

function delBean(opr, grid)
{
    if (getRadio('checkb') && getRadioValue('checkb'))
    {    
    	var status = getRadio('checkb').lstatus;
        
    	if (status != 99)
    	{
    		if(window.confirm('确定删除?'))    
    	        $ajax(gurl + 'delete' + ukey + '&id=' + getRadioValue('checkb'), callBackFun);
    	}
    	else
    		$error('结束状态不能删除');
    }
    else
    $error('不能操作');
}

function delBean1(opr, grid)
{
    if (getRadio('checkb') && getRadioValue('checkb'))
    {    
		var status = getRadio('checkb').lstatus;

		if (status == 3 || status == 98)			
		{
			if(window.confirm('确定删除?'))    
		        $ajax(gurl + 'delete' + ukey + '&id=' + getRadioValue('checkb'), callBackFun);
		}
		else
        $error('只能删除保存或驳回状态');
    }
    else
    $error('不能操作');
}

function doProcess()
{
    if (getRadio('checkb') && getRadioValue('checkb'))
    {   
        $l(gurl + 'find' + ukey + '&update=1&id=' + getRadioValue('checkb') + '&mode=' + mode);
    }
    else
    $error('不能操作');
}

function doProcess2()
{
    if (getRadio('checkb') && getRadioValue('checkb'))
    {   
        $l(gurl + 'find' + ukey + '&update=1&id=' + getRadioValue('checkb') + '&mode=' + mode);
    }
    else
    $error('不能操作');
}

function exports()
{
    document.location.href = '../finance/invoiceins.do?method=exportInvoiceins';
}

function exports2()
{
    document.location.href = '../finance/invoiceins.do?method=exportInvoiceins2';
}

function checkSubmit(checks, checkrefId)
{
    if (checks == '' || checkrefId == '')
    {
        alert('意见和关联单据不能为空');
        
        return false;
    }
    
    closeCheckDiv();
    
    $ajax2(gurl + 'checkInvoiceins&id=' + getRadioValue('checkb'), {'checks' : checks, 'checkrefId' : checkrefId}, 
                        callBackFun);
}

function checkBean()
{
    if (getRadio('checkb') && getRadioValue('checkb'))
    {   
       openCheckDiv();
    }
    else
    $error('不能操作');
}

// ajax
function confirmInvoice()
{
	if (getRadio('checkb') && getRadioValue('checkb'))
	{	
		var status = getRadio('checkb').lstatus;

		var otype = getRadio('checkb').lotype;

		if (status == 4 && otype == 0)
		{
			$ajax('../finance/invoiceins.do?method=confirmInvoice&id=' + getRadioValue('checkb'), callBackFun);
		}
		else
			$error('不能操作');
	}
	else
	$error('不能操作');
}

function confirmPay()
{
	if (getRadio('checkb') && getRadioValue('checkb'))
	{	
		var status = getRadio('checkb').lstatus;

		var otype = getRadio('checkb').lotype;

		if (status == 4 && otype == 0)
		{
			$ajax('../finance/invoiceins.do?method=confirmPay&id=' + getRadioValue('checkb'), callBackFun);
		}
		else
			$error('不能操作');
	}
	else
	$error('不能操作');
}

function autoadd1(opr, grid)
{
	$('#destStafferDIV').dialog({closed:false});
}

function autoadd(opr, grid)
{
	$.blockUI({ message: $('#loadingDiv'),css:{width: '40%'}}, true);

	$('#destStafferDIV').dialog({closed:true});

	$ajax(gurl + 'batchAdd' + ukey + '&destStafferId=' + $O('destStafferId').value, callBackFun);

	$.unblockUI();
}

function $selectStaffer()
{   
    window.common.modal('../admin/pop.do?method=rptQueryStaffer&load=1&selectMode=1');
}

function getStaffers(oos)
{
	 var oo = oos[0];

	 $O('destStafferName').value = oo.pname;
     $O('destStafferId').value = oo.value;
     
}

function doSearch()
{
    $modalQuery('../admin/query.do?method=popCommonQuery2&key=query' + ukey);
}

</script>
</head>
<body onload="load()" class="body_class">
<form name="mainForm" method="post">
<p:cache></p:cache>
</form>
<p:message></p:message>
<table id="mainTable" style="display: none"></table>
<div id="destStafferDIV" title="请选择业务员" style="width:320px;">
    <div style="padding:20px;" id="dia_inner" title="">
    <input type="text" name="destStafferName" readonly="readonly"/>
	<input type="hidden" name="destStafferId"/>&nbsp;&nbsp;
	<input type="button" value="&nbsp;...&nbsp;" name="qout" id="qout"
                            class="button_class" onclick="$selectStaffer(1)"/>&nbsp;&nbsp;
   </div>
</div>
<div id="loadingDiv" style="display:none">
<p>&nbsp;</p>
<p>进在处理中......</p>
<p><img src="../images/oa/process.gif" /></p>
<p>&nbsp;</p>
</div>
<p:query height="300px"/>
</body>