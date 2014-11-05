<%@page contentType="text/html; charset=UTF-8"%>
<%@include file="../common/common.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<p:link title="退款列表" link="true" guid="true" cal="true" dialog="true" />
<script src="../js/common.js"></script>
<script src="../js/public.js"></script>
<script src="../js/pop.js"></script>
<script src="../js/plugin/highlight/jquery.highlight.js"></script>
<script type="text/javascript">

var gurl = '../finance/backpay.do?method=';
var addUrl = '';
var ukey = 'BackPayApply';

var allDef = getAllDef();
var guidMap;
var thisObj;

var mode = '<p:value key="mode"/>';

function load()
{
     preload();
     
     guidMap = {
         title: '退款列表',
         url: gurl + 'query' + ukey + '&mode=' + mode,
         colModel : [
             {display: '选择', name : 'check', content : '<input type=radio name=checkb value={id} lstatus={status} lstafferId={stafferId}>', width : 40, align: 'center'},
             {display: '申请人', name : 'stafferName', width : '8%'},
             {display: '客户', name : 'customerName', width : '20%'},
             {display: '单据', name : 'outId', content: '{outId}{billId}', width : '15%'},
             {display: '类型', name : 'type', cc: 'backPayApplyType', width : '10%'},
             {display: '状态', name : 'status', cc: 'backPayApplyStatus', width : '10%'},
             {display: '退款金额', name : 'backPay', width : '8%', toFixed: 2},
             {display: '转预收金额', name : 'changePayment', width : '8%', toFixed: 2},
             {display: '时间', name : 'logTime', sortable : true, width : 'auto'}
             ],
         extAtt: {
             stafferName : {begin : '<a href=' + gurl + 'find' + ukey + '&id={id}&mode=' + mode + '>', end : '</a>'}
         },
         buttons : [
             {id: 'pass', caption: '处理',bclass: 'update', auth: '1606', onpress : doProcess},
             {id: 'del', bclass: 'del', auth: '1401', onpress : delBean},
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
    if (getRadio('checkb') && getRadioValue('checkb') && getRadio('checkb').lstatus == 98 && getRadio('checkb').lstafferId == '${user.stafferId}')
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
    if (getRadio('checkb') && getRadioValue('checkb'))
    {   
        $l(gurl + 'find' + ukey + '&update=1&id=' + getRadioValue('checkb') + '&mode=' + mode);
    }
    else
    $error('不能操作');
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
<p:message/>
<table id="mainTable" style="display: none"></table>
<p:query/>
</body>