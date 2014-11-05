<%@page contentType="text/html; charset=UTF-8"%>
<%@include file="../common/common.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<p:link title="短信管理" link="true" guid="true" cal="true" dialog="true" />
<script src="../js/common.js"></script>
<script src="../js/public.js"></script>
<script src="../js/pop.js"></script>
<script src="../js/plugin/highlight/jquery.highlight.js"></script>
<script src="../js/jquery.blockUI.js"></script>
<script type="text/javascript">

var gurl = '../customerService/shortMessage.do?method=';
var addUrl = '';
var ukey = 'ShortMessage';

//var mode = '<p:value key="mode"/>';

var allDef = getAllDef();
var guidMap;
var thisObj;
function load()
{
     preload();

     guidMap = {
         title: '短信列表',
         url: gurl + 'query' + ukey,
         colModel : [
		   {display: '<input type=checkbox id=flexi_Check onclick=checkAll(this)>选择', name : 'check', content : '<input type=checkbox name=checkb value={id}>', width : 40, sortable : false, align: 'center'},
		   {display: '类型', name : 'smType', width : '5%', cc: '232'},
		   {display: '手机号', name : 'mobile', width : '10%'},
           {display: '短信内容', name : 'content',  width : '40%'},
           {display: '操作用户', name : 'stafferName', width : '8%'},
           {display: '上/下行', name : 'smode', width : '8%', cc: 'sendMode'},
           {display: '时间', name : 'logTime',  width : 'auto'}
             ],
         extAtt: {
    	 	//id : {begin : '<a href=../customerService/feedback.do?method=findFeedBack&id={id}>', end : '</a>'}
         },
         buttons : [
			 {id: 'export', bclass: 'replied',  caption: '导出', onpress : exports, auth:'0120'},
             {id: 'search', bclass: 'search', onpress : doSearch}
             ],
        <p:conf/>
     };
     
     $("#mainTable").flexigrid(guidMap, thisObj);

     $ESC('destStafferDIV');
}
 
function $callBack()
{
    loadForm();

    //highlights($("#mainTable").get(0), ['已完成'], 'blue');
}

function doSearch()
{
    $modalQuery('../admin/query.do?method=popCommonQuery2&key=query' + ukey);
}

function exports()
{
    document.location.href = gurl + 'export';
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