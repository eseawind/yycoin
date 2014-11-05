<%@page contentType="text/html; charset=UTF-8"%>
<%@include file="../common/common.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<p:link title="发票导入查询" link="true" guid="true" cal="true" dialog="true" />
<script src="../js/common.js"></script>
<script src="../js/public.js"></script>
<script src="../js/pop.js"></script>
<script src="../js/plugin/highlight/jquery.highlight.js"></script>
<script src="../js/jquery.blockUI.js"></script>
<script type="text/javascript">

var gurl = '../finance/invoiceins.do?method=';
var addUrl = '';
var ukey = 'ImportInvoice';

var allDef = getAllDef();
var guidMap;
var thisObj;
function load()
{
     preload();
     
     guidMap = {
         title: '导入发票列表',
         url: gurl + 'query' + ukey,
         colModel : [
             {display: '选择', name : 'check', content : '<input type=radio name=checkb value={id}>', width : 40, align: 'center'},
             {display: '职员', name : 'stafferName', width : '8%'},
             {display: '发票抬头', name : 'invoiceHead',  width : '10%'},
             {display: '发票开具单位', name : 'invoiceCompany',  width : '20%'},
             {display: '发票金额', name : 'moneys',  width : '5%', toFixed: 2},
             {display: '已确认金额', name : 'hasConfirmMoneys',  width : '5%', toFixed: 2},
             {display: '供应商', name : 'providerName',  width : '10%'},
             {display: '发票类型', name : 'invoiceName',  width : '10%'},
             {display: '发票号码', name : 'invoiceNumber',  width : '10%'},
             {display: '发票日期', name : 'invoiceDate',  width : '10%'},
             {display: '导入人', name : 'oprName',  width : '10%'},
             {display: '导入时间', name : 'logTime',  width : 'auto'}
             ],
         extAtt: {
	    	
         },
         buttons : [
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