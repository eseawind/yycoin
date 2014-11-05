<%@page contentType="text/html; charset=UTF-8"%>
<%@include file="../common/common.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<p:link title="客户信息审核" link="true" guid="true" cal="true" dialog="true"/>
<script src="../js/common.js"></script>
<script src="../js/public.js"></script>
<script src="../js/pop.js"></script>
<script src="../js/json.js"></script>
<script type="text/javascript">

var allDef = window.top.topFrame.allDef;
var guidMap;
var thisObj;

var updatFlag = window.top.topFrame.containAuth('021602') ? '1' : '0';
function load()
{
	 preload();
	 
	 guidMap = {
		 title: '客户信息审核申请',
		 url: '../customer/check.do?method=queryCustomerCheck',
		 colModel : [
		     {display: '选择', name : 'check', content : '<input type=radio name=checkb value={id} lstatus={status}>', width : 40, sortable : false, align: 'center'},
		     {display: '标识', name : 'id', width : '12%', sortable : false, align: 'left'},
		     {display: '被检职员', name : 'checkerName', width : '7%'},
		     {display: '申请职员', name : 'applyerName', width : '7%'},
		     {display: '审核人', name : 'approverName', width : '7%'},
		     {display: '状态', name : 'status', width : '5%', cc: 'commonStatus'},
		     {display: '初始/正确/错误', name : 'result', content: '{retInit}/{retOK}/{retError}', width : '14%', sortable : true},
		     {display: '时间', name : 'logTime', width : '15%', sortable : true},
		     {display: '时段', name : 'time_', content: '{beginTime}至{endTime}', width : 'auto', cc: 'commonStatus'}
		     ],
		 extAtt: {
		     id : {begin : '<a href=../customer/queryCheckItem.jsp?id={id}&menu=1>', end : '</a>'}
		 },
		 buttons : [
		     {id: 'add', bclass: 'add', onpress : addBean, auth: '021601'},
		     {id: 'update', bclass: 'update', caption: '续请', onpress : goonBean, auth: '021601'},
		     {id: 'del', bclass: 'delete', onpress : delBean, auth: '021601'},
		     {id: 'pass', bclass: 'pass', caption: '通过',  onpress : passBean, auth: '021602'},
		     {id: 'reject', bclass: 'reject', caption: '驳回', onpress : rejectBean, auth: '021602'},
		     {id: 'queryItem', bclass: 'search', caption: '审核信息', onpress : queryItem, auth: '021601'},
		     {id: 'search', bclass: 'search', onpress : doSearch}
		     ],
		 <p:conf callBack="loadForm"/>
	 };
	 
	 $("#mainTable").flexigrid(guidMap, thisObj);
 }
 
function doSearch()
{
    $modalQuery('../admin/query.do?method=popCommonQuery2&key=queryCustomerCheck');
}

function delBean()
{
    if (getRadio('checkb') && getRadioValue('checkb'))
    {
        if (getRadio('checkb').lstatus != 2)
        {
	        if (window.confirm('如果是续请驳回会删除先前已经核对的信息,确定删除--' + getRadioValue('checkb')))
	        {
	            $ajax('../customer/check.do?method=delCheck&id=' + getRadioValue('checkb'), callBackFun);
	        }
        }
        else
        {
            alert('不能此操作');
        }
    }
}

function callBackFun(data)
{
    reloadTip(data.msg, data.ret == 0);

    if (data.ret == 0)
    commonQuery();
}

function passBean(opr, grid)
{
    if (getRadio('checkb') && getRadioValue('checkb'))
    {
       if (getRadio('checkb').lstatus == 1)
       {
           if (window.confirm('确定通过--' + getRadioValue('checkb'))) 
           $ajax('../customer/check.do?method=passCheck&id=' + getRadioValue('checkb'), callBackFun);
       }
    }
}

function rejectBean(opr, grid)
{
    if (getRadio('checkb') && getRadioValue('checkb'))
    {
       if (getRadio('checkb').lstatus == 1)
       {
           if (window.confirm('确定驳回--' + getRadioValue('checkb'))) 
           $ajax('../customer/check.do?method=rejectCheck&id=' + getRadioValue('checkb'), callBackFun);
       }
    }
}

function addBean(opr, grid)
{
   $l('../customer/check.do?method=preForAddCheck');
}

function queryItem(opr, grid)
{
    if (getRadio('checkb') && getRadioValue('checkb'))
    {
       if (getRadio('checkb').lstatus == 2)
       {
           $l('../customer/queryCheckItem.jsp?id=' + getRadioValue('checkb') + '&menu=1' );
       }
       else
       {
           alert('不能此操作,只能是通过的才可以审核');
       }
    }
}

function goonBean(opr, grid)
{
    if (getRadio('checkb') && getRadioValue('checkb'))
    {
       if (getRadio('checkb').lstatus == 99)
       {
           $l('../customer/check.do?method=findCheck&id=' + getRadioValue('checkb') + '&update=1');
       }
       else
       {
           alert('不能此操作,只能是结束的才可以续请');
       }
    }
}
</script>
</head>
<body onload="load()" class="body_class">
<form>
<p:cache></p:cache>
</form>
<p:message></p:message>
<table id="mainTable" style="display: none"></table>
<p:query/>
</body>