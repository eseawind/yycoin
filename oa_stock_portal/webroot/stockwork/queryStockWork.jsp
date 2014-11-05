<%@page contentType="text/html; charset=UTF-8"%>
<%@include file="../common/common.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<p:link title="采购跟单" link="true" guid="true" cal="true" dialog="true" />
<script src="../js/common.js"></script>
<script src="../js/public.js"></script>
<script src="../js/pop.js"></script>
<script src="../js/plugin/highlight/jquery.highlight.js"></script>
<script src="../js/jquery.blockUI.js"></script>
<script type="text/javascript">

var gurl = '../stock/work.do?method=';
var addUrl = '';
var ukey = 'StockWork';

var allDef = getAllDef();
var guidMap;
var thisObj;


function load()
{
     preload();
     
     guidMap = {
         title: '开票列表',
         url: gurl + 'query' + ukey,
         colModel : [
             {display: '选择', name : 'check', content : '<input type=radio name=checkb value={id} lstatus={status}>', width : 40, align: 'center'},
             {display: '采购目标', name : 'target', width : '18%'},
             {display: '采购申请人', name : 'stafferName', width : '6%'},
             {display: '采购单号', name : 'stockId', width : '10%'},
             {display: '采购单品', name : 'productName', width : '18%'},
             {display: '供应商', name : 'provideName', width : '15%'},
             {display: '状态', name : 'status', cc: 'stockWorkStatus', width : '10%'},
             {display: '供应商确认时间', name : 'provideConfirmDate', width : '10%'},
             {display: '确认发货时间', name : 'confirmSendDate', width : '10%'},
             {display: '时间', name : 'logTime', width : 'auto', sortable : true}
             ],
         extAtt: {
    	 	stafferName : {begin : '<a title="点击查看详细" href=' + gurl + 'find' + ukey + '&id={id}' + '>', end : '</a>'}
         },
         buttons : [
			 {id: 'export', bclass: 'replied',  caption: '导出', onpress : exports},
             {id: 'search', bclass: 'search', onpress : doSearch}
             ],
        <p:conf/>
     };
     
     $("#mainTable").flexigrid(guidMap, thisObj);
}
 
function $callBack()
{
    loadForm();
    highlights($("#mainTable").get(0), ['待处理'], 'red');
    highlights($("#mainTable").get(0), ['处理结束'], 'blue');
}

function exports()
{
    document.location.href = gurl + 'exportStockWork';
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
<p:query/>
</body>