<%@page contentType="text/html; charset=UTF-8"%>
<%@include file="../common/common.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<p:link title="退货登记" link="true" guid="true" cal="true" dialog="true" />
<script src="../js/common.js"></script>
<script src="../js/public.js"></script>
<script src="../js/JCheck.js"></script>
<script src="../js/pop.js"></script>
<script src="../js/plugin/highlight/jquery.highlight.js"></script>
<script type="text/javascript">

var gurl = '../sail/outback.do?method=';
var addUrl = '../sail/outback.do?method=';
var ukey = 'OutBack';

var mode = '<p:value key="mode"/>';

var allDef = window.top.topFrame.allDef;
var guidMap;
var thisObj;
function load()
{
     preload();
     
     guidMap = {
         title: '退货登记列表',
         url: gurl + 'query' + ukey + '&mode=' + mode,
         colModel : [
             {display: '选择', name : 'check', content : '<input type=radio name=checkb value={id} lstatus={status} >', width : 40, align: 'center'},
             {display: '标识', name : 'id', width : '10%'},
             {display: '快递公司', name : 'expressCompanyName',  width : '10%'},
             {display: '运单号', name : 'transportNo',  width : '10%'},
             {display: '状态', name : 'status',  width : '10%', cc: 'outbackStatus'},
             {display: '发件人地址', name : 'fromFullAddress',  width : '18%'},
             {display: '发件人', name : 'fromer',  width : '5%'},
             {display: '发件人电话', name : 'fromMobile',  width : '6%'},
             {display: '收件人地址', name : 'toFullAddress',  width : '18%'},
             {display: '收件人', name : 'to',  width : '5%'},
             {display: '收件人电话', name : 'toMobile',  width : '6%'},
             {display: '库管收货人', name : 'receiver',  width : '5%'},
             {display: '收货日期', name : 'receiverDate',  width : 'auto'}
             ],
         extAtt: {
    	 	id : {begin : '<a href=' + gurl + 'find' + ukey + '&id={id}>', end : '</a>'}
         },
         buttons : [
             <c:if test="${mode == 0}">
             {id: 'add', bclass: 'add', onpress : addBean},
             {id: 'update', bclass: 'update',onpress : updateBean},
             {id: 'del', bclass: 'del',  onpress : delBean},
			 </c:if>
             <c:if test="${mode == 1}">
             {id: 'update1', bclass: 'update', caption : '认领', onpress : claimBean},
             {id: 'update2', bclass: 'update', caption : '退领', onpress : unclaimBean},
			 </c:if>
             <c:if test="${mode == 2}">
             {id: 'update3', bclass: 'update', caption : '验货', onpress : checkBean},
			 </c:if>
             <c:if test="${mode == 3}">
             {id: 'update4', bclass: 'update', caption : '结束入库', onpress : finishBean},
			 </c:if>
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
}

function delBean(opr, grid)
{
    if (getRadio('checkb') && getRadioValue('checkb'))
    {    
        if (getRadio('checkb').lstatus == 1)
        {
        	if(window.confirm('确定删除?'))    
                $ajax(gurl + 'delete' + ukey + '&id=' + getRadioValue('checkb'), callBackFun);    
        }
        else
        {
        	$error('已认领过,不能删除');
        }
    }
    else
    $error('不能操作');
}

function updateBean()
{
	if (getRadio('checkb') && getRadioValue('checkb'))
	{	
		if (getRadio('checkb').lstatus == 1)
        {
			$l(gurl + 'find' + ukey + '&update=1&id=' + getRadioValue('checkb'));
    	}
        else
        {
        	$error('已认领过,不能修改');
        }
	}
	else
	$error('不能操作');
}

function claimBean()
{
	if (getRadio('checkb') && getRadioValue('checkb'))
	{	
		if (getRadio('checkb').lstatus == 1)
        {
			$l(gurl + 'find' + ukey + '&update=2&id=' + getRadioValue('checkb'));
    	}
        else
        {
        	$error('不是待认领状态,不可认领');
        }
	}
	else
	$error('不能操作');
}

function unclaimBean(opr, grid)
{
    if (getRadio('checkb') && getRadioValue('checkb'))
    {    
        if (getRadio('checkb').lstatus == 2)
        {
        	if(window.confirm('确定退领?'))    
                $ajax(gurl + 'unclaim' + ukey + '&id=' + getRadioValue('checkb'), callBackFun);    
        }
        else
        {
        	$error('只能在待验货时才能退领');
        }
    }
    else
    $error('不能操作');
}

function checkBean()
{
	if (getRadio('checkb') && getRadioValue('checkb'))
	{	
		if (getRadio('checkb').lstatus == 2)
        {
			$l(gurl + 'find' + ukey + '&update=3&id=' + getRadioValue('checkb'));
    	}
        else
        {
        	$error('不是待验货状态,不可认领');
        }
	}
	else
	$error('不能操作');
}

function finishBean(opr, grid)
{
    if (getRadio('checkb') && getRadioValue('checkb'))
    {    
        if (getRadio('checkb').lstatus == 3)
        {
        	if(window.confirm('确定结束入库?'))    
                $ajax(gurl + 'finish' + ukey + '&id=' + getRadioValue('checkb'), callBackFun);    
        }
        else
        {
        	$error('只能在待入库时才能结束入库');
        }
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
<p:query height="40px"/>

<p:query />
</body>