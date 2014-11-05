<%@page contentType="text/html; charset=UTF-8"%>
<%@include file="../common/common.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<p:link title="提成管理" link="true" guid="true" cal="true" dialog="true" />
<script src="../js/common.js"></script>
<script src="../js/public.js"></script>
<script src="../js/pop.js"></script>
<script src="../js/plugin/highlight/jquery.highlight.js"></script>
<script src="../js/jquery.blockUI.js"></script>
<script type="text/javascript">

var gurl = '../commission/commission.do?method=';
var addUrl = '../commission/addCommission.jsp';
var ukey = 'Commission';

var allDef = getAllDef();
var guidMap;
var thisObj;
function load()
{
     preload();
     
     guidMap = {
         title: '提成计算列表',
         url: gurl + 'query' + ukey,
         colModel : [
             {display: '选择', name : 'check', content : '<input type=radio name=checkb value={id}>', width : 40, align: 'center'},
             {display: '月份', name : 'month', width : '10%'},
             {display: '开始时间', name : 'beginTime',  width : '15%'},
             {display: '结束时间', name : 'endTime',  width : '15%'},
             {display: '创建人', name : 'stafferName', width : '10%'},
             {display: '创建时间', name : 'logTime', sortable : true, width : 'auto'}
             ],
         extAtt: {
             //pid : {begin : '<a title=点击查看明细 href=' + gurl + 'findFinance&id={pid}>', end : '</a>'}
         },
         buttons : [
             {id: 'add', bclass: 'add', caption: '月度统计', onpress : addBean, auth: '1806'},
             {id: 'del', bclass: 'del', caption: '撤销月度统计', onpress : delBean, auth: '1806'},
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
	if (window.confirm('确定准备提成计算,计算需要一段时间?'))
    {
        $.blockUI({ message: $('#loadingDiv'),css:{width: '40%'}}, true);
        
        $l(gurl + 'add' + ukey);
    }
}

function delBean(opr, grid)
{
    if (getRadio('checkb') && getRadioValue('checkb'))
    {    
        if(window.confirm('确定撤销提成统计?'))    
        $ajax(gurl + 'delete' + ukey + '&id=' + getRadioValue('checkb'), callBackFun);
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
<p:message/>
<table id="mainTable" style="display: none"></table>
<p:query/>
<div id="loadingDiv" style="display:none">
<p>&nbsp;</p>
<p>月结准备中......</p>
<p><img src="../images/oa/process.gif" /></p>
<p>&nbsp;</p>
</div>
</body>