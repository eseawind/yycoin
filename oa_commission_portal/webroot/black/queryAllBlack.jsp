<%@page contentType="text/html; charset=UTF-8"%>
<%@include file="../common/common.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<p:link title="黑名单管理" link="true" guid="true" cal="true" dialog="true" />
<script src="../js/common.js"></script>
<script src="../js/public.js"></script>
<script src="../js/pop.js"></script>
<script src="../js/plugin/highlight/jquery.highlight.js"></script>
<script src="../js/jquery.blockUI.js"></script>
<script type="text/javascript">

var gurl = '../commission/black.do?method=';
var addUrl = '../black/addBlack.jsp';
var ukey = 'Black';

var allDef = getAllDef();
var guidMap;
var thisObj;
function load()
{
     preload();
     
     guidMap = {
         title: '黑名单列表',
         url: gurl + 'queryAll' + ukey,
         colModel : [
           {display: '选择', name : 'check', content : '<input type=radio name=checkb value={id}>', width : 40, align: 'center'},
           {display: '职员', name : 'stafferName', width : '8%'},
           {display: '职员状态', name : 'birthday', width : '8%'},
           {display: '超期应收金额', name : 'money',  width : '10%', toFixed: 2},
           {display: '超期最大天数', name : 'days',  width : '10%'},
           {display: '全部应收金额', name : 'allMoneys',  width : '10%', toFixed: 2},
           {display: '信用属性', name : 'credit',  width : '10%', cc: 'stafferBlack'},
           {display: '进入日期', name : 'entryDate',  width : '10%'},
           {display: '解除日期', name : 'removeDate',  width : '10%'},
           {display: '事业部名称', name : 'industryName',  width : 'auto'}
             ],
         extAtt: {
	    	 money : {begin : '<a title=点击查看明细  href=' + gurl + 'findBlack&id={id}&type=0&firstLoad=1>', end : '</a>'},
	    	 days : {begin : '<a title=点击查看明细 href=' + gurl + 'findBlack&id={id}&type=1&firstLoad=1>', end : '</a>'},
	    	 allMoneys : {begin : '<a title=点击查看明细 href=' + gurl + 'findBlack&id={id}&type=2&firstLoad=1>', end : '</a>'}
         },
         buttons : [
             {id: 'search', bclass: 'search', onpress : doSearch},
             {id: 'export', bclass: 'replied', caption: '导出所有黑名单', onpress : exports},
             {id: 'export', bclass: 'replied', caption: '导出所有黑名单应收', onpress : exports1},
             {id: 'export', bclass: 'replied', caption: '导出所有应收明细', onpress : exports2}
             ],
        <p:conf/>
     };
     
     $("#mainTable").flexigrid(guidMap, thisObj);
}
 
function $callBack()
{
    loadForm();

    highlights($("#mainTable").get(0), ['废弃'], 'red');
}

function doSearch()
{
    $modalQuery('../admin/query.do?method=popCommonQuery2&key=query' + ukey);
}

function exports()
{
    if (window.confirm('确定导出所有黑名单?'))
    {
    document.location.href = '../commission/black.do?method=exportAllBlackStaffer';
    }
}

function exports1()
{
    if (window.confirm('确定导出所有黑名单?'))
    {
    document.location.href = '../commission/black.do?method=exportAllBlackMoney';
    }
}

function exports2()
{
    if (window.confirm('确定导出所有黑名单明细?'))
    {
    document.location.href = '../commission/black.do?method=exportAllBlackDetail';
    }
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