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
         title: '指标列表',
         url: '../credit/credit.do?method=queryCreditItemSec',
         colModel : [
             {display: '选择', name : 'check', content : '<input type=radio name=checkb value={id} lper={per} ltype={type} lsub={sub}>', width : 40, align: 'center'},
             {display: '评估项', name : 'name', width : '20%'},
             {display: '比例/分值', name : 'per', width : '8%', toFixed: 2},
             {display: '最高分', name : 'point', width : '8%'},
             {display: '得分类型', name : 'type', width : '8%', cc: 'creditItemType'},
             {display: '类型', name : 'face', width : '8%', cc: 'creditItemFace'},
             {display: '类型2', name : 'sub', width : '8%', cc: 'creditItemSub'},
             {display: '指标类型', name : 'parentType', width : '10%', cc: 'creditType'},
             {display: '父项', name : 'pName', sortable : false, width : 'auto'}
             ],
         extAtt: {
             
         },
         buttons : [
            {id: 'add', bclass: 'add', caption: '增加子指标项', onpress : addBean, auth: 'true'},
            {id: 'update', bclass: 'update', caption: '修改比例/分值', onpress : updateBean, auth: 'true'},
            {id: 'search', bclass: 'search', onpress : doSearch, auth: 'true'}
             ],
         <p:conf />
     };
     
     $("#mainTable").flexigrid(guidMap, thisObj);
}
 
function $callBack()
{
    loadForm();
    
    highlights($("#mainTable").get(0), ['正向指标', '百分制', '静态指标', '直接设值'], 'blue');
    highlights($("#mainTable").get(0), ['负向指标', '实际值', '动态指标'], 'red');
}

function addBean(opr, grid)
{
    if (getRadio('checkb') && getRadioValue('checkb') && getRadio('checkb').lsub == 0)
    {    
        $l('../credit/credit.do?method=preForAddCreditItemThr&pid=' + $$('checkb'));
    }
    else
    $error('不能操作');
}

function updateBean(opr, grid)
{
    if (((getRadio('checkb') && getRadioValue('checkb') && getRadio('checkb').ltype == 0)) 
        || ( getRadio('checkb') && getRadioValue('checkb') && getRadio('checkb').ltype == 1 && getRadio('checkb').lsub == 1))
    {
        var tit = getRadio('checkb').ltype == 1 ? "分值" : "比例";
        
	    $.messager.prompt('修改比例', '请输入' + tit + '(只能是数字)', getRadio('checkb').lper,
	        function(value, isOk)
	        {
	            if (isOk)
	            if (isFloat(value))
	            $ajax('../credit/credit.do?method=updateCreditItemSecPer&id=' + $$('checkb') + '&newPer=' + value, callBackFun);
	            else
	            $error('只能输入数字');           
	        });
    }
    else
    $error('不能操作');
}

function doSearch()
{
    $modalQuery('../admin/query.do?method=popCommonQuery2&key=queryCreditItemSec');
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