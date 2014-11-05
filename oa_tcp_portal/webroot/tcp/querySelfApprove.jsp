<%@page contentType="text/html; charset=UTF-8"%>
<%@include file="../common/common.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<p:link title="待我处理" link="true" guid="true" cal="true" dialog="true" />
<script src="../js/common.js"></script>
<script src="../js/public.js"></script>
<script src="../js/pop.js"></script>
<script src="../js/plugin/highlight/jquery.highlight.js"></script>
<script type="text/javascript">

var gurl = '../tcp/apply.do?method=';
var addUrl = '../tcp/addTravelApply.jsp';
var ukey = 'TravelApply';

var allDef = getAllDef();
var guidMap;
var thisObj;

var mode = '<p:value key="mode"/>';

function load()
{
     preload();
     
     guidMap = {
         title: '待我处理列表',
         url: gurl + 'querySelfApprove&mode=' + mode,
         colModel : [
             {display: '选择', name : 'check', content : '<input type=radio name=checkb value={id} lstatus={status} lurl={url} ltype={type}>', width : 40, align: 'center'},
             {display: '标识', name : 'applyId', width : '15%'},
             {display: '目的', name : 'name', width : '12%'},
             {display: '申请人', name : 'applyerName', width : '10%'},
             {display: '系列', name : 'stype', cc: 'tcpStype', width : '5%'},
             {display: '类型', name : 'type', cc: 'tcpType', width : '12%'},
             {display: '部门', name : 'departmentName', width : '10%'},
             {display: '状态', name : 'status', cc: 'tcpStatus', width : '10%'},
             {display: '费用/借款', name : 'showTotal', sortable: true, cname: 'total', content:'{showTotal}/{showCheckTotal}', width : '10%'},
             {display: '时间', name : 'logTime', sortable: true, width : 'auto'}
             ],
         extAtt: {
             applyId : {begin : '<a href={url}>', end : '</a>'}
         },
         buttons : [
			 <c:if test="${mode == 98}">
			 {id: 'update1', bclass: 'update',  caption : '强制结束', onpress : endBean},
			 </c:if>

			 <c:if test="${mode != 98}">
	             {id: 'update', bclass: 'update',  caption : '处理申请', onpress : updateBean},
	             <c:if test="${mode == 0}">
	             	{id: 'oget1', bclass: 'odraw', caption: '退领', onpress : odrawBean},
	             </c:if>
			 </c:if>       

             {id: 'export', bclass: 'replied',  caption: '导出明细', onpress : exports},
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
}

function updateBean()
{
	if (getRadio('checkb') && getRadioValue('checkb'))
	{	
		$l(getRadio('checkb').lurl + '&ttflag=11');
	}
	else
	$error('不能操作');
}

function updateBean2()
{
	//出差申请的稽核修改
	if (getRadio('checkb') && getRadioValue('checkb') 
			&& getRadio('checkb').lstatus == 20
			&& getRadio('checkb').ltype == 0)
	{	
		$l(getRadio('checkb').lurl + '&update=3');
	}
	else
	$error('不能操作');
}

function endBean(opr, grid)
{
    if (getRadio('checkb') && getRadioValue('checkb'))
    {    
        if(window.confirm('确定强制结束该申请?'))    
        $ajax(gurl + 'endApprove&id=' + getRadioValue('checkb'), callBackFun);
    }
    else
    $error('不能操作');
}

function odrawBean(opr, grid)
{
    if (getRadio('checkb') && getRadioValue('checkb'))
    {    
        if(window.confirm('确定退领此报销处理?'))    
        $ajax(gurl + 'odrawApprove&id=' + getRadioValue('checkb'), callBackFun);
    }
    else
    $error('不能操作');
}

function exports()
{
    document.location.href = gurl + 'exportTCP';
}

function doSearch()
{
    $modalQuery('../admin/query.do?method=popCommonQuery2&key=tcp.queryTcpSelfApprove');
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