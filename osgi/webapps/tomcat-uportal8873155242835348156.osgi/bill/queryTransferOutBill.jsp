<%@page contentType="text/html; charset=UTF-8"%>
<%@include file="../common/common.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<p:link title="转账管理" link="true" guid="true" cal="true" dialog="true" />
<script src="../js/common.js"></script>
<script src="../js/public.js"></script>
<script src="../js/pop.js"></script>
<script src="../js/plugin/highlight/jquery.highlight.js"></script>
<script type="text/javascript">

var gurl = '../finance/bill.do?method=';
var addUrl = '../finance/addOutBill.jsp';
var ukey = 'OutBill';

var allDef = window.top.topFrame.allDef;
var guidMap;
var thisObj;
function load()
{
     preload();
     
     guidMap = {
         title: '转账列表',
         url: gurl + 'queryTransfer' + ukey,
         colModel : [
             {display: '选择', name : 'check', content : '<input type=radio name=checkb value={id} lstatus={status} llock={lock} ltype={type}>', width : 40, align: 'center'},
             {display: '标识', name : 'id', width : '15%'},
             {display: '源帐户', name : 'bankName',  width : '10%'},
             {display: '目的帐户', name : 'destBankName', width : '10%'},
             {display: '类型', name : 'type', cc: 'outbillType', width : '8%'},
             {display: '付款方式', name : 'payType', cc: 'outbillPayType', width : '8%'},
             {display: '金额', name : 'moneys',  toFixed: 2, width : '8%'},
             {display: '申请人', name : 'stafferName', width : '8%'},
             {display: '状态', name : 'status', cc: 'outbillStatus', width : '8%'},
             {display: '时间', name : 'logTime', sortable : true, width : 'auto'}
             ],
         extAtt: {
             id : {begin : '<a href=' + gurl + 'find' + ukey + '&id={id}>', end : '</a>'}
         },
         buttons : [
             {id: 'pass', bclass: 'pass', caption: '财务经理审批',  onpress : passBean, auth: '1611'},
             {id: 'pass', bclass: 'pass', caption: '财务总监审批',  onpress : passBean1, auth: '1612'},
             {id: 'reject', bclass: 'reject', caption: '拒绝', auth: '1611', onpress : rejectBean},
             {id: 'reject', bclass: 'reject', caption: '拒绝', auth: '1612', onpress : rejectBean1},
             {id: 'search', bclass: 'search', onpress : doSearch}
             ],
        <p:conf/>
     };
     
     $("#mainTable").flexigrid(guidMap, thisObj);
}
 
function $callBack()
{
    loadForm();
    
}

function passBean(opr, grid)
{
    if (getRadio('checkb') && getRadioValue('checkb') && getRadio('checkb').lstatus == 1)
    {    
        if(window.confirm('确定接收转账?'))    
        $ajax(gurl + 'passTransfer' + ukey + '&id=' + getRadioValue('checkb') + '&flag=0', callBackFun);
    }
    else
    $error('不能操作');
}

function passBean1(opr, grid)
{
    if (getRadio('checkb') && getRadioValue('checkb') && getRadio('checkb').lstatus == 3)
    {    
        if(window.confirm('确定接收转账?'))    
        $ajax(gurl + 'passTransfer' + ukey + '&id=' + getRadioValue('checkb') + '&flag=1', callBackFun);
    }
    else
    $error('不能操作');
}

function rejectBean(opr, grid)
{
    if (getRadio('checkb') && getRadioValue('checkb') && getRadio('checkb').lstatus == 1)
    {    
        if(window.confirm('确定驳回转账?'))    
        $ajax(gurl + 'rejectTransfer' + ukey + '&id=' + getRadioValue('checkb') + '&flag=0', callBackFun);
    }
    else
    $error('不能操作');
}

function rejectBean1(opr, grid)
{
    if (getRadio('checkb') && getRadioValue('checkb') && getRadio('checkb').lstatus == 3)
    {    
        if(window.confirm('确定驳回转账?'))    
        $ajax(gurl + 'rejectTransfer' + ukey + '&id=' + getRadioValue('checkb') + '&flag=1', callBackFun);
    }
    else
    $error('不能操作');
}

function doSearch()
{
    $modalQuery('../admin/query.do?method=popCommonQuery2&key=queryTransfer' + ukey);
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