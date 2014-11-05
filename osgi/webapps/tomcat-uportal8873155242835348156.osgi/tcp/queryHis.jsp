<%@page contentType="text/html; charset=UTF-8"%>
<%@include file="../common/common.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<p:link title="历史处理" link="true" guid="true" cal="true" dialog="true" />
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
function load()
{
     preload();
     
     guidMap = {
         title: '历史处理列表',
         url: gurl + 'queryTcpHis',
         colModel : [
             {display: '选择', name : 'check', content : '<input type=radio name=checkb value={id} lstatus={status} lurl={url}>', width : 40, align: 'center'},
             {display: '标识', name : 'refId', width : '15%'},
             {display: '目的', name : 'name', width : '15%'},
             {display: '申请人', name : 'applyName', width : '10%'},
             {display: '类型', name : 'type', cc: 'tcpType', sortable: true, width : '20%'},
             {display: '费用', name : 'moneyStr1', width : '8%'},
             {display: '借款', name : 'moneyStr2', width : '8%'},
             {display: '时间', name : 'logTime', sortable: true, width : 'auto'}
             ],
         extAtt: {
             refId : {begin : '<a href={url}>', end : '</a>'}
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
    highlights($("#mainTable").get(0), ['结束'], 'blue');
}

function drawApprove()
{
    var clis = getCheckBox('checkb');
    
    if (clis.length > 0)
    {
        var str = '';
        
        for (var i = 0; i < clis.length; i++)
        {
            str += clis[i].value + ';';
        }
        
        if (window.confirm('确定认领选中的工单?'))
        {
            $ajax('../tcp/apply.do?method=drawApprove&ids=' + str, callBackFun);
        }
    }
	else
	$error('不能操作');
}

function doSearch()
{
    $modalQuery('../admin/query.do?method=popCommonQuery2&key=tcp.queryTcpHis');
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