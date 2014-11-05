<%@page contentType="text/html; charset=UTF-8"%>
<%@include file="../common/common.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<p:link title="指标分类管理" link="true" guid="true" cal="true" dialog="true" />
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
         title: '指标分类列表',
         url: '../credit/credit.do?method=queryCreditItem',
         colModel : [
             {display: '选择', name : 'check', content : '<input type=radio name=checkb value={id} lper={per} ltype={type}>', width : 40, align: 'center'},
             {display: '指标', name : 'name', width : '30%'},
             {display: '比例', name : 'per', width : '10%', toFixed: 2},
             {display: '最高分', name : 'point', width : '10%'},
             {display: '类型', name : 'type', width : '15%', cc: 'creditType'},
             {display: '子项比例之和', name : 'perAmount', width : 'auto'}
             ],
         extAtt: {
             
         },
         buttons : [
            {id: 'update', bclass: 'update', caption: '修改比例', onpress : updateBean, auth: 'true'}
             ],
         <p:conf/>
     };
     
     $("#mainTable").flexigrid(guidMap, thisObj);
}
 
function $callBack()
{
    loadForm();
    
    highlights($("#mainTable").get(0), ['静态指标'], 'blue');
    highlights($("#mainTable").get(0), ['动态指标'], 'red');
}

function updateBean(opr, grid)
{
    if (getRadio('checkb') && getRadioValue('checkb') && getRadio('checkb').ltype == 0)
    $.messager.prompt('修改比例', '请输入比例(只能是数字)', getRadio('checkb').lper,
	    function(value, isOk)
	    {
	        if (isOk)
	        if (isFloat(value))
	        $ajax('../credit/credit.do?method=updateCreditItemPer&id=' + $$('checkb') + '&newPer=' + value, callBackFun);
	        else
            $error('只能输入数字');           
	    });
	else
    $error('不能操作');
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