<%@page contentType="text/html; charset=UTF-8"%>
<%@include file="../common/common.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<p:link title="收款申请审核" link="true" guid="true" cal="true" dialog="true" />
<script src="../js/common.js"></script>
<script src="../js/public.js"></script>
<script src="../js/pop.js"></script>
<script src="../js/plugin/highlight/jquery.highlight.js"></script>
<script type="text/javascript">

var gurl = '../finance/bank.do?method=';
var addUrl = '../finance/addBank.jsp';
var ukey = 'PaymentApply';

var allDef = window.top.topFrame.allDef;
var guidMap;
var thisObj;
function load()
{
     preload();
     
     guidMap = {
         title: '申请列表',
         url: gurl + 'queryPaymentApplyCheck',
         colModel : [
             {display: '选择', name : 'check', content : '<input type=radio name=checkb value={id} lstatus={status} lstafferId={stafferId}>', width : 40, align: 'center'},
             {display: '申请人', name : 'stafferName', width : '10%'},
             {display: '类型', name : 'type', cc: 'payApplyType', width : '8%'},
             {display: '状态', name : 'status', cc: 'payApplyStatus', width : '8%'},
             {display: '客户', name : 'customerName', width : '15%'},
             {display: '总金额', name : 'moneys', width : '8%', toFixed: 2},
             {display: '回款标识', name : 'paymentId', width : '15%'},
             {display: '时间', name : 'logTime', sortable : true, width : 'auto'}
             ],
         extAtt: {
             stafferName : {begin : '<a href=' + gurl + 'find' + ukey + '&id={id}&update=1>', end : '</a>'}
         },
         buttons : [
             {id: 'pass', caption: '处理',bclass: 'update', auth: '1606', onpress : doProcess},
             {id: 'search', bclass: 'search', onpress : doSearch}
             ],
        <p:conf/>
     };
     
     $("#mainTable").flexigrid(guidMap, thisObj);
}
 
function $callBack()
{
    loadForm();
    
    highlights($("#mainTable").get(0), ['驳回'], 'red');
}

function delBean(opr, grid)
{
    if (getRadio('checkb') && getRadioValue('checkb') && getRadio('checkb').lstatus == 0)
    {    
        if(window.confirm('确定删除?'))    
        $ajax(gurl + 'delete' + ukey + '&id=' + getRadioValue('checkb'), callBackFun);
    }
    else
    $error('不能操作');
}

function updateBean()
{
	if (getRadio('checkb') && getRadioValue('checkb'))
	{	
		$l(gurl + 'find' + ukey + '&update=1&id=' + getRadioValue('checkb'));
	}
	else
	$error('不能操作');
}

function doProcess()
{
    if (getRadio('checkb') && getRadio('checkb').lstatus == 3)
    {   
        $l(gurl + 'find' + ukey + '&update=1&id=' + getRadioValue('checkb'));
    }
    else
    $error('不能操作');
}


function doSearch()
{
    $modalQuery('../admin/query.do?method=popCommonQuery2&key=queryPaymentApplyCheck');
}

</script>
</head>
<body onload="load()" class="body_class">
<form name="mainForm" method="post">
<p:cache></p:cache>
</form>
<p:message></p:message>
<table id="mainTable" style="display: none"></table>
<p:query/>
</body>