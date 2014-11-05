<%@page contentType="text/html; charset=UTF-8"%>
<%@include file="../common/common.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<p:link title="指标管理" link="true" guid="true" cal="true" dialog="true" />
<script src="../js/common.js"></script>
<script src="../js/public.js"></script>
<script src="../js/pop.js"></script>
<script src="../js/plugin/highlight/jquery.highlight.js"></script>
<script type="text/javascript">

var allDef = window.top.topFrame.allDef;
var guidMap;
var thisObj;
function load()
{
     preload();
     guidMap = {
         title: '指标项列表',
         url: '../credit/credit.do?method=queryCreditItemThr',
         colModel : [
             {display: '选择', name : 'check', content : '<input type=radio name=checkb value={id} lpid={pid}>', width : 40, align: 'center'},
             {display: '评估级别', name : 'name', width : '30%'},
             {display: '得分', name : 'per', width : '10%', sortable : true, toFixed: 2},
             {display: '类型', name : 'face', width : '10%', cc: 'creditItemFace'},
             {display: '档次', name : 'indexPos', width : '10%'},
             {display: '父项', name : 'pName', sortable : true, cname: 'pid', width : 'auto'}
             ],
         extAtt: {
             name : {begin : '<a href=../credit/credit.do?method=findCreditItemThr&id={id}>', end : '</a>'}
         },
         buttons : [
            {id: 'add', bclass: 'add', onpress : addBean, auth: 'true'},
            {id: 'update', bclass: 'update', onpress : updateBean, auth: 'true'},
            {id: 'del', bclass: 'del', onpress : delBean, auth: 'true'},
            {id: 'search', bclass: 'search', onpress : doSearch, auth: 'true'}
             ],
         <p:conf />
     };
     
     $("#mainTable").flexigrid(guidMap, thisObj);
}
 
function $callBack()
{
    loadForm();
    highlights($("#mainTable").get(0), ['正向指标', '百分制', '静态指标'], 'blue');
    highlights($("#mainTable").get(0), ['负向指标', '实际值', '动态指标'], 'red');
}

function addBean(opr, grid)
{
    if (getRadio('checkb') && getRadioValue('checkb'))
    {    
        $l('../credit/credit.do?method=preForAddCreditItemThr&pid=' + getRadio('checkb').lpid);
    }
    else
    $error('不能操作');
}


function updateBean(opr, grid)
{
    if (getRadio('checkb') && getRadioValue('checkb'))
    {    
        $l('../credit/credit.do?method=findCreditItemThr&id=' + $$('checkb'));
    }
    else
    $error('不能操作');
}

function delBean(opr, grid)
{
    if (getRadio('checkb') && getRadioValue('checkb'))
    {
        if(window.confirm('确定删除?'))    
        $ajax('../credit/credit.do?method=deleteCreditItemThr&id=' + $$('checkb'), callBackFun);
    }
    else
    $error('不能操作');
}

function doSearch()
{
    $modalQuery('../admin/query.do?method=popCommonQuery2&key=queryCreditItemThr');
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