<%@page contentType="text/html; charset=UTF-8"%>
<%@include file="../common/common.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<p:link title="空开空退管理" link="true" guid="true" cal="true" dialog="true" />
<script src="../js/common.js"></script>
<script src="../js/public.js"></script>
<script src="../js/pop.js"></script>
<script src="../js/plugin/highlight/jquery.highlight.js"></script>
<script type="text/javascript">

var gurl = '../sail/out.do?method=';
var addUrl = '../sail/out.do?method=';
var ukey = 'AllOutRepaireApply';

var allDef = window.top.topFrame.allDef;
var guidMap;
var thisObj;
function load()
{
     preload();
     
     guidMap = {
         title: '销售单空开空退审批',
         url: gurl + 'query' + ukey,
         colModel : [
             {display: '选择', name : 'check', content : '<input type=radio name=checkb value={id} >', width : 40, align: 'center'},
             {display: '标识', name : 'id', width : '12%'},
             {display: '销售单', name : 'outId', width : '12%'},
             {display: '原因', name : 'reason',  width : '15%', cc : 'outRepaireReason'},
             {display: '申请人', name : 'stafferName',  width : '5%'},
             {display: '状态', name : 'status',  width : '5%', cc : 'outRepaireStatus' },
             {display: '新销售单', name : 'newOutId', width : '12%'},
             {display: '销售退单', name : 'retOutId', width : '12%'},
             {display: '时间', name : 'logTime',  width : 'auto'}
             ],
         extAtt: {
	         id : {begin : '<a href=../sail/out.do?method=findOut&fow=922&outId={outId}&repaireId={id}>', end : '</a>'},
	         outId : {begin : '<a href=../sail/out.do?method=findOut&fow=99&outId={outId}>', end : '</a>'},
	         newOutId : {begin : '<a href=../sail/out.do?method=findOut&fow=99&outId={newOutId}>', end : '</a>'},
	         retOutId : {begin : '<a href=../sail/out.do?method=findOut&fow=99&outId={retOutId}>', end : '</a>'}
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
    highlights($("#mainTable").get(0), ['驳回'], 'red');
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