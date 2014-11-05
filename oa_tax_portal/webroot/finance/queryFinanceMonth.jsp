<%@page contentType="text/html; charset=UTF-8"%>
<%@include file="../common/common.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<p:link title="月结明细" link="true" guid="true" cal="true" dialog="true" />
<script src="../js/common.js"></script>
<script src="../js/public.js"></script>
<script src="../js/pop.js"></script>
<script src="../js/plugin/highlight/jquery.highlight.js"></script>
<script type="text/javascript">

var gurl = '../finance/finance.do?method=';
var addUrl = '../finance/addFinance.jsp';
var ukey = 'FinanceMonth';

var allDef = getAllDef();
var guidMap;
var thisObj;
function load()
{
     preload();
     
     guidMap = {
         title: '月结明细',
         url: gurl + 'query' + ukey,
         colModel : [
             {display: '选择', name : 'check', content : '<input type=radio name=checkb value={id}>', width : 40, align: 'center'},
             {display: '月份', name : 'monthKey', width : '5%'},
             {display: '科目', name : 'taxName', content: '{taxName}({taxId})', sortable : true, cname: 'taxId', width : '25%'},
             {display: '本月借贷', name : 'curr', content: '{showInmoneyTotal}/{showOutmoneyTotal}',  width : '18%'},
             {display: '本月结余', name : 'showLastTotal',  width : '8%'},
             {display: '累计借贷', name : 'curr', content: '{showInmoneyAllTotal}/{showOutmoneyAllTotal}',  width : '18%'},
             {display: '期末余额', name : 'showLastAllTotal',  width : 'auto'},
             {display: '结转金额', name : 'showMonthTurnTotal',  width : '10%'}
             ],
         extAtt: {
             //pid : {begin : '<a title=点击查看明细 href=' + gurl + 'findFinance&id={pid}>', end : '</a>'}
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
}

function exports()
{
    document.location.href = gurl + 'exportFinanceMonth';
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