<%@page contentType="text/html; charset=UTF-8"%>
<%@include file="../common/common.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<p:link title="告警管理" link="true" guid="true" cal="true" dialog="true" />
<script src="../js/common.js"></script>
<script src="../js/public.js"></script>
<script src="../js/pop.js"></script>
<script src="../js/plugin/highlight/jquery.highlight.js"></script>
<script type="text/javascript">

var gurl = '../admin/alarm.do?method=';
var addUrl = '../admin/alarm.do?method=';
var ukey = 'Alarm';

var allDef = window.top.topFrame.allDef;
var guidMap;
var thisObj;
function load()
{
     preload();
     
     guidMap = {
         title: '告警列表',
         url: gurl + 'query' + ukey,
         colModel : [
             {display: '选择', name : 'check', content : '<input type=radio name=checkb value={id} pstatus={status}>', width : 40, align: 'center'},
             {display: '时间', name : 'logTime',  width : '15%'},
             {display: '相关单据', name : 'refId', width : '15%'},
             {display: '状态', name : 'status', cc: 'alarmStatus',  width : '5%'},
             {display: '告警内容', name : 'alarmContent',  width : 'auto'}
             ],
         extAtt: {
             logTime : {begin : '<a href=' + gurl + 'findAlarm&id={id}>', end : '</a>'}
         },
         buttons : [
             {id: 'update', bclass: 'update',  caption: '处理', onpress : updateBean},
             {id: 'search', bclass: 'search', onpress : doSearch}
             ],
        <p:conf/>
     };
     
     $("#mainTable").flexigrid(guidMap, thisObj);
}
 
function $callBack()
{
    loadForm();
    highlights($("#mainTable").get(0), ['结束'], 'red');
}

function updateBean(opr, grid)
{
    if (getRadio('checkb') && getRadioValue('checkb'))
    {    
        $.messager.prompt('告警处理', '请输入处理说明', '',
            function(value, isOk)
            {
                if (isOk)
                {
                    $ajax2('../admin/alarm.do?method=updateAlarm&id=' + $$('checkb'), {'reason': value}, callBackFun);
                }
            }, 2);
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