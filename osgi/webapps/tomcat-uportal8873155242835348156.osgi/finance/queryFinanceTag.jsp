<%@page contentType="text/html; charset=UTF-8"%>
<%@include file="../common/common.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<p:link title="财务标记数据查询" link="true" guid="true" cal="true" dialog="true" />
<script src="../js/common.js"></script>
<script src="../js/public.js"></script>
<script src="../js/pop.js"></script>
<script src="../js/plugin/highlight/jquery.highlight.js"></script>
<script src="../js/jquery.blockUI.js"></script>
<script type="text/javascript">

/**
 * 比较时间
 */
function compareDays1(date1, date2)
{
    var s1 = date1.split('-');
    var s2 = date2.split('-');

    var year1 = parseInt(s1[0], 10);

    var year2 = parseInt(s2[0], 10);

    var month1 = parseInt(s1[1], 10);

    var month2 = parseInt(s2[1], 10);

    var day1 = parseInt(s1[2], 10);

    var day2 = parseInt(s2[2], 10);

    return (year2 - year1) * 365 + (month2 - month1) * 30 + (day2 - day1);
}

var gurl = '../finance/finance.do?method=';
var addUrl = '';
var ukey = 'FinanceTag';

var allDef = getAllDef();
var guidMap;
var thisObj;
function load()
{
     preload();

     guidMap = {
         title: '财务标记数据列表',
         url: gurl + 'query' + ukey,
         colModel : [
		   {display: '选择', name : 'check', content : '<input type=radio name=checkb value={id}>', width : 40, align: 'center'},
		   {display: '类型', name : 'typeName', width : '20%'},
		   {display: '单号', name : 'fullId', width : '20%'},
           {display: '标记', name : 'tag', width : '6%'},
           {display: '统计时间', name : 'statsTime',  width : 'auto'}
             ],
         extAtt: {
    	 	//id : {begin : '<a href=../customerService/feedback.do?method=findFeedBack&id={id}>', end : '</a>'}
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

function doSearch()
{
    $modalQuery('../admin/query.do?method=popCommonQuery2&key=query' + ukey);
}

function exports()
{
    document.location.href = gurl + 'exportFinanceTag';
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