<%@page contentType="text/html; charset=UTF-8"%>
<%@include file="../common/common.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<p:link title="凭证管理" link="true" guid="true" cal="true" dialog="true" />
<script src="../js/common.js"></script>
<script src="../js/public.js"></script>
<script src="../js/pop.js"></script>
<script src="../js/plugin/highlight/jquery.highlight.js"></script>
<script type="text/javascript">

var gurl = '../finance/finance.do?method=';
var addUrl = '../finance/addFinance.jsp';
var ukey = 'FinanceItem';

var allDef = getAllDef();
var guidMap;
var thisObj;
function load()
{
     preload();
     
     guidMap = {
         title: '凭证项列表',
         url: gurl + 'query' + ukey,
         colModel : [
             {display: '选择', name : 'check', content : '<input type=radio name=checkb value={id}>', width : 40, align: 'center'},
             {display: '凭证', name : 'pid', width : '15%'},
             {display: '索引', name : 'pareId', width : '10%'},
             {display: '科目', name : 'taxName', content: '{taxId}{taxName}', width : '15%'},
             {display: '借贷', name : 'showMoney', content: '{showInmoney}/{showOutmoney}', width : '10%'},
             {display: '辅助', name : 'helpItem', content: '{departmentName}/{stafferName}/{unitName}/{productName}/{depotName}/{duty2Name}', width : '20%'},
             {display: '凭证日期', name : 'financeDate', sortable : true, width : '10%'},
             {display: '创建时间', name : 'logTime', sortable : true, width : 'auto'}
             ],
         extAtt: {
             pid : {begin : '<a title=点击查看明细 href=' + gurl + 'findFinance&id={pid}>', end : '</a>'}
         },
         buttons : [
             {id: 'search', bclass: 'search', onpress : doSearch},
             {id: 'export', bclass: 'replied',  caption: '导出明细', onpress : exports}
             ],
        <p:conf/>
     };
     
     $("#mainTable").flexigrid(guidMap, thisObj);
}
 
function $callBack()
{
    loadForm();
}

function exports()
{
    document.location.href = gurl + 'exportFinanceItem';
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
<p:query height="300px"/>
</body>