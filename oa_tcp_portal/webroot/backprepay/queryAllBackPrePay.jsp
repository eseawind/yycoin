<%@page contentType="text/html; charset=UTF-8"%>
<%@include file="../common/common.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<p:link title="申请预收退款管理" link="true" guid="true" cal="true" dialog="true" />
<script src="../js/common.js"></script>
<script src="../js/public.js"></script>
<script src="../js/pop.js"></script>
<script src="../js/plugin/highlight/jquery.highlight.js"></script>
<script type="text/javascript">

var gurl = '../tcp/backprepay.do?method=';
var addUrl = '../backPrepay/addBackPrePay.jsp';
var ukey = 'BackPrePay';

var allDef = getAllDef();
var guidMap;
var thisObj;
function load()
{
     preload();
     
     guidMap = {
         title: '申请预收退款列表',
         url: gurl + 'queryAllBackPrePay&type=23',
         colModel : [
             {display: '选择', name : 'check', content : '<input type=radio name=checkb value={id} lstatus={status}>', width : 40, align: 'center'},
             {display: '标识', name : 'id', width : '15%'},
             {display: '目的', name : 'name', width : '15%'},
             {display: '申请人', name : 'stafferName', width : '8%'},
             {display: '处理人', name : 'processer', width : '8%'},
             {display: '状态', name : 'status', cc: 'tcpStatus', width : '10%'},
             {display: '客户', name : 'customerName', width : '15%'},
             {display: '收款单', name : 'billId', width : '10%'},
             {display: '退款金额', name : 'showBackMoney', sortable: true, cname: 'backMoney', width : '8%'},
             {display: '时间', name : 'logTime', sortable: true, width : 'auto'}
             ],
         extAtt: {
             id : {begin : '<a href=' + gurl + 'find' + ukey + '&id={id}>', end : '</a>'}
         },
         buttons : [
			 {id: 'export', bclass: 'replied',  caption: '导出明细', onpress : exports},
             {id: 'search', bclass: 'search', onpress : doSearch}
             ],
        <p:conf/>
     };
     
     $("#mainTable").flexigrid(guidMap, thisObj);
}
 
function $callBack()
{
    loadForm();
    highlights($("#mainTable").get(0), ['结束'], 'blue');
    highlights($("#mainTable").get(0), ['驳回'], 'red');
}

function exports()
{
    document.location.href = gurl + 'exportBackPrePayApply';
}

function doSearch()
{
    $modalQuery('../admin/query.do?method=popCommonQuery2&key=tcp.queryAllBackPrePay');
}

</script>
</head>
<body onload="load()" class="body_class">
<form name="mainForm" method="post">
<p:cache></p:cache>
</form>
<p:message></p:message>
<table id="mainTable" style="display: none"></table>
<p:query height="300px"/>
</body>