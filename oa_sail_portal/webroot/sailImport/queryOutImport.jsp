<%@page contentType="text/html; charset=UTF-8"%>
<%@include file="../common/common.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<p:link title="销售导入数据查询" link="true" guid="true" cal="true" dialog="true" />
<script src="../js/common.js"></script>
<script src="../js/public.js"></script>
<script src="../js/JCheck.js"></script>
<script src="../js/pop.js"></script>
<script src="../js/plugin/highlight/jquery.highlight.js"></script>
<script type="text/javascript">

var gurl = '../sail/outImport.do?method=';
var ukey = 'OutImport';

var batchId = "<p:value key='sbatchId'/>";

var allDef = window.top.topFrame.allDef;
var guidMap;
var thisObj;
function load()
{
     preload();
     
     guidMap = {
         title: '销售导入数据明细',
         url: gurl + 'query' + ukey + '&sbatchId=' + batchId ,
         colModel : [
             {display: '选择', name : 'check', content : '<input type=radio name=checkb value={id} >', width : 40, align: 'center'},
             {display: '序号', name : 'id', width : '5%', sortable: true},
             {display: '批次号', name : 'batchId', width : '12%'},
             {display: '中信订单号', name : 'citicNo', width : '12%'},
             {display: 'OA单号', name : 'OANo', width : '14%'},
             {display: '状态', name : 'oaStatus', width : '8%', cc: 'outStatus'},
             {display: '预占', name : 'preUse', width : '8%', cc: 'preUse'},
             {display: '付款状态', name : 'oaPay', width : '6%', cc:'outPay'},
             {display: '网点名称', name : 'comunicatonBranchName', width : '8%'},
             {display: '商品编码', name : 'productCode', width : '8%'},
             {display: '商品名称', name : 'productName', width : '8%'},
             {display: '数量', name : 'amount', width : '5%'},
             {display: '单价', name : 'price', width : '5%', toFixed : 2},
             {display: '金额', name : 'value', width : '8%', toFixed : 2},
             {display: '中收金额', name : 'midValue', width : '8%', toFixed : 2},
             {display: '计划交付日期', name : 'arriveDate', width : '8%'},
             {display: '开票抬头', name : 'invoiceHead', width : '8%'},
             {display: '开票品名', name : 'invoiceName', width : '8%'},
             {display: '开票金额', name : 'invoiceMoney', width : '8%', toFixed : 2},
             {display: '中信订单日期', name : 'citicOrderDate', width : '8%'},
             {display: '时间', name : 'logTime', width : 'auto'}
             ],
         extAtt: {
    	 	//batchId : {begin : '<a href=' + gurl + 'queryOutImportLog&batchId={batchId}>', end : '</a>'}
         },
         buttons : [
             //{id: 'add', bclass: 'add', onpress : addBean},
             //{id: 'update', bclass: 'update', onpress : updateBean},
             //{id: 'del', bclass: 'del',  onpress : delBean},
             {id: 'search', bclass: 'search', onpress : doSearch},
             {id: 'export', bclass: 'replied',  caption: '导出', onpress : exports}
             ],         
         <p:conf/>
     };
     
     $("#mainTable").flexigrid(guidMap, thisObj);
     
}
 
function $callBack()
{
    loadForm();
    
    highlights($("#mainTable").get(0), ['已预占'], 'red');
    //highlights($("#mainTable").get(0), ['有效'], 'blue');
    
}

function exports()
{
    document.location.href = gurl + 'exportOutImport';
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
<p:query height="20px"/>

<p:query />
</body>