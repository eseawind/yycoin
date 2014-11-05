<%@page contentType="text/html; charset=UTF-8"%>
<%@include file="../common/common.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<p:link title="预估毛利查询" link="true" guid="true" cal="true" dialog="true" />
<script src="../js/common.js"></script>
<script src="../js/public.js"></script>
<script src="../js/JCheck.js"></script>
<script src="../js/pop.js"></script>
<script src="../js/plugin/highlight/jquery.highlight.js"></script>
<script type="text/javascript">

var gurl = '../sail/outImport.do?method=';
var ukey = 'EstimateProfit';

var allDef = window.top.topFrame.allDef;
var guidMap;
var thisObj;
function load()
{
     preload();
     
     guidMap = {
         title: '预估毛利明细',
         url: gurl + 'query' + ukey ,
         colModel : [
             {display: '选择', name : 'check', content : '<input type=radio name=checkb value={id} >', width : 40, align: 'center'},
             {display: '商品', name : 'productName', width : '30%'},
             {display: '预估毛利', name : 'profit', width : '6%', toFixed : 2},
             {display: '操作人', name : 'stafferName', width : '8%'},
             {display: '时间', name : 'logTime', width : 'auto'}
             ],
         extAtt: {
    	 	//batchId : {begin : '<a href=' + gurl + 'queryOutImportLog&batchId={batchId}>', end : '</a>'}
         },
         buttons : [
             //{id: 'add', bclass: 'add', onpress : addBean},
             //{id: 'update', bclass: 'update', onpress : updateBean},
             {id: 'del', bclass: 'del', caption: '删除' onpress : delBean},
             {id: 'search', bclass: 'search', onpress : doSearch}
             //{id: 'export', bclass: 'replied',  caption: '导出', onpress : exports}
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

function delBean(opr, grid)
{
    if (getRadio('checkb') && getRadioValue('checkb'))
    {    
        if(window.confirm('确定删除?'))    
        $ajax(gurl + 'delete' + ukey + '&id=' + getRadio('checkb').value, callBackFun);
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
<p:message></p:message>
<table id="mainTable" style="display: none"></table>
<p:query height="20px"/>

<p:query />
</body>