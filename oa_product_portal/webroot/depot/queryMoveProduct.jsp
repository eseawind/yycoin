<%@page contentType="text/html; charset=UTF-8"%>
<%@include file="../common/common.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<p:link title="仓区产品转移查看" link="true" guid="true" cal="true" dialog="true" />
<script src="../js/common.js"></script>
<script src="../js/public.js"></script>
<script src="../js/pop.js"></script>
<script src="../js/plugin/highlight/jquery.highlight.js"></script>
<script type="text/javascript">

var gurl = '../depot/storage.do?method=';
var addUrl = '../depot/addStorage.jsp';
var ukey = 'MoveProduct';

var allDef = window.top.topFrame.allDef;
var guidMap;
var thisObj;
function load()
{
     preload();
     
     guidMap = {
         title: '产品转移列表',
         url: gurl + 'query' + ukey,
         colModel : [
             {display: '流水号', name : 'id', width : '10%'},
             {display: '批次号', name : 'flowid', width : '10%'},
             {display: '源仓区', name : 'oldName', width : '15%'},
             {display: '新仓区', name : 'newName', width : '15%'},
             {display: '移动产品', name : 'productName', width : '15%'},
             {display: '产品数量', name : 'amount', width : '15%'},
             {display: '转移时间', name : 'moveTime', width : '15%'},
             {display: '移交人', name : 'moveStafferName', width : '15%'}
             ],
         extAtt: {
    	// flowid : {begin : '<a href=' + gurl + 'find' + ukey + '&flowid={flowid}>', end : '</a>'}
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

function addBean(opr, grid)
{
    $l(gurl + 'preForAdd' + ukey);
    //$l(addUrl);
}

function delBean(opr, grid)
{
    if (getRadio('checkb') && getRadioValue('checkb'))
    {    
        if(window.confirm('确定删除?'))    
        $ajax(gurl + 'delete' + ukey + '&id=' + getRadioValue('checkb'), callBackFun);
    }
    else
    $error('不能操作');
}

function updateBean()
{
	if (getRadio('checkb') && getRadioValue('checkb'))
	{	
		$l(gurl + 'find' + ukey + '&update=1&id=' + getRadioValue('checkb'));
	}
	else
	$error('不能操作');
}

function preForFindStorageToTransfer()
{
	if (getRadio('checkb') && getRadioValue('checkb'))
	{	
		$l('../depot/storage.do?method=preForFindStorageToTransfer&id=' + getRadioValue('checkb'));
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
<p:query/>
</body>