<%@page contentType="text/html; charset=UTF-8"%>
<%@include file="../common/common.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<p:link title="开票导入数据查询" link="true" guid="true" cal="true" dialog="true" />
<script src="../js/common.js"></script>
<script src="../js/public.js"></script>
<script src="../js/JCheck.js"></script>
<script src="../js/pop.js"></script>
<script src="../js/plugin/highlight/jquery.highlight.js"></script>
<script type="text/javascript">

var gurl = '../finance/invoiceins.do?method=';
var ukey = 'InvoiceinsImport';

var batchId = "<p:value key='sbatchId'/>";

var allDef = window.top.topFrame.allDef;
var guidMap;
var thisObj;
function load()
{
     preload();
     
     guidMap = {
         title: '开票导入数据明细',
         url: gurl + 'query' + ukey + '&sbatchId=' + batchId ,
         colModel : [
             {display: '选择', name : 'check', content : '<input type=radio name=checkb value={id} >', width : 40, align: 'center'},
             {display: '批次号', name : 'batchId', width : '12%'},
             {display: '销售单', name : 'outId', width : '12%'},
             {display: '开票金额', name : 'invoiceMoney', width : '5%', toFixed : 2},
             {display: '发票号', name : 'invoiceNum', width : '8%'},
             {display: '开票抬头', name : 'invoiceHead', width : '15%'},
             {display: '关联开票申请', name : 'refInsId', width : '15%'},
             {display: '时间', name : 'logTime', width : 'auto'}
             ],
         extAtt: {
    	 	//outId : {begin : '<a href=' + gurl + 'queryOutImportLog&batchId={batchId}>', end : '</a>'}
         },
         buttons : [
             //{id: 'add', bclass: 'add', onpress : addBean},
             //{id: 'update', bclass: 'update', onpress : updateBean},
             //{id: 'del', bclass: 'del',  onpress : delBean},
             // {id: 'export', bclass: 'replied',  caption: '导出', onpress : exports}
             {id: 'search', bclass: 'search', onpress : doSearch}
             
             ],         
         <p:conf/>
     };
     
     $("#mainTable").flexigrid(guidMap, thisObj);
}
 
function $callBack()
{
    loadForm();
    
    //highlights($("#mainTable").get(0), ['已预占'], 'red');
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