<%@page contentType="text/html; charset=UTF-8"%>
<%@include file="../common/common.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<p:link title="手机订单" link="true" guid="true" cal="true" dialog="true" />
<script src="../js/common.js"></script>
<script src="../js/public.js"></script>
<script src="../js/JCheck.js"></script>
<script src="../js/pop.js"></script>
<script src="../js/plugin/highlight/jquery.highlight.js"></script>
<script type="text/javascript">

var gurl = '../sail/out.do?method=';
var addUrl = '../sail/out.do?method=';
var ukey = 'AppOut';

var allDef = window.top.topFrame.allDef;
var guidMap;
var thisObj;
function load()
{
     preload();
     
     guidMap = {
         title: '手机订单列表',
         url: gurl + 'query' + ukey  ,
         colModel : [
             {display: '选择', name : 'check', content : '<input type=radio name=checkb value={id} lstatus={status} >', width : 40, align: 'center'},
             {display: '用户名', name : 'loginName', width : '10%'},
             {display: 'APP订单号', name : 'orderNo',  width : '13%'},
             {display: 'OA 订单号', name : 'refOutId',  width : '40%'},
             {display: '备注', name : 'description',  width : 'auto'}
             ],
         extAtt: {
    	 //customerName : {begin : '<a href=' + gurl + 'find' + ukey + '&id={id}>', end : '</a>'}
         },
         buttons : [
             //{id: 'add', bclass: 'update',caption:'处理', onpress : addBean},
             //{id: 'update', bclass: 'update',caption:'处理', onpress : updateBean},
             //{id: 'del', bclass: 'del',  onpress : delBean},
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
<p:message></p:message>
<table id="mainTable" style="display: none"></table>
<p:query height="40px"/>

<p:query />
</body>