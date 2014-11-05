<%@page contentType="text/html; charset=UTF-8"%>
<%@include file="../common/common.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<p:link title="费用申请管理" link="true" guid="true" cal="true" dialog="true" />
<script src="../js/common.js"></script>
<script src="../js/public.js"></script>
<script src="../js/pop.js"></script>
<script src="../js/plugin/highlight/jquery.highlight.js"></script>
<script type="text/javascript">

var gurl = '../tcp/apply.do?method=';
var addUrl = '../tcp/queryAllCommissionApprove.jsp';
var ukey = 'Commission';

var allDef = getAllDef();
var guidMap;
var thisObj;
function load()
{
     preload();
     
     guidMap = {
         title: '提成特批申请',
         url: gurl + 'queryAllCommission',
         colModel : [
             {display: '选择', name : 'check', content : '<input type=radio name=checkb value={id} lstatus={status}>', width : 40, align: 'center'},
             {display: '标识', name : 'id', width : '15%'},
             {display: '详情', name : 'detail', width : '20%'},
             {display: '申请人', name : 'stafferName', width : '8%'},
             {display: '处理人', name : 'processName', width : '8%'},
             {display: '状态', name : 'status', cc: 'commissionStatus', width : '10%'},
             {display: '时间', name : 'logTime', sortable: true, width : 'auto'}
             ],
         extAtt: {
             id : {begin : '<a href=' + gurl + 'find' + ukey + '&id={id}&update=2>', end : '</a>'}
         },
         buttons : [
             {id: 'add', bclass: 'add', onpress : addBean, auth: '0000'},
             {id: 'update', bclass: 'update', onpress : updateBean, auth: '0000'},
             {id: 'del', bclass: 'del',  onpress : delBean, auth: '0000'},
             {id: 'search', bclass: 'search', onpress : doSearch}
             ],
        <p:conf/>
     };
     
     $("#mainTable").flexigrid(guidMap, thisObj);
}
 
function $callBack()
{
    loadForm();
    highlights($("#mainTable").get(0), ['结束'], 'blue');
    highlights($("#mainTable").get(0), ['驳回', '未关联报销'], 'red');
}

function addBean(opr, grid)
{
    $l(gurl + 'preForAdd' + ukey + '&type=3');
    //$l(addUrl);
}

function delBean(opr, grid)
{
    if (getRadio('checkb')&& (getRadio('checkb').lstatus == 0 || getRadio('checkb').lstatus == 1))
    {    
        if(window.confirm('确定删除?'))    
        $ajax(gurl + 'deleteCommission&id=' + getRadioValue('checkb'), callBackFun);
    }
    else
    $error('不能操作');
}

function updateBean()
{
	if (getRadio('checkb') && getRadioValue('checkb')&& (getRadio('checkb').lstatus == 0 || getRadio('checkb').lstatus == 1))
	{	
		$l(gurl + 'find' + ukey + '&update=1&id=' + getRadioValue('checkb'));
	}
	else
	$error('不能操作');
}

function doSearch()
{
    $modalQuery('../admin/query.do?method=popCommonQuery2&key=tcp.queryVocationAndWorkTravelApply');
}

</script>
</head>
<body onload="load()" class="body_class">
<form name="mainForm" method="post">
<p:cache></p:cache>
</form>
<p:message></p:message>
<table id="mainTable" style="display: none"></table>
<p:query height="300px"/>
</body>