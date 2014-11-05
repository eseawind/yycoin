<%@page contentType="text/html; charset=UTF-8"%>
<%@include file="../common/common.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<p:link title="任务管理" link="true" guid="true" cal="true" dialog="true" />
<script src="../js/common.js"></script>
<script src="../js/public.js"></script>
<script src="../js/JCheck.js"></script>
<script src="../js/pop.js"></script>
<script src="../js/plugin/highlight/jquery.highlight.js"></script>
<script type="text/javascript">

var gurl = '../project/project.do?method=';
var addUrl = '../project/addTask.jsp';
var ukey = 'AllTask';

var allDef = window.top.topFrame.allDef;
var guidMap;
var thisObj;
function load()
{
     preload();
     guidMap = {
         title: '任务管理列表',
         url: gurl + 'query' + ukey + '&src=0',
         colModel : [
             {display: '选择', name : 'check', content : '<input type=radio name=checkb value={id} lbatchPrice={batchPrice} lsailPrice={sailPrice} lcost={cost}>', width : 40, align: 'center'},
             {display: '单据ID', name : 'id', width : '13%'},
             {display: '任务名称', name : 'taskName', width : '10%'},
             {display: '任务类型', name : 'taskType', cc : '223', width : '7%'},
             {display: '责任人', name : 'dutyName', width : '7%'},
             {display: '计划完成时间', name : 'planFinishTime',  width : '7%'},
             {display: '实际完成时间', name : 'realFinishiTime',  width : '7%'},
             {display: '交付类型', name : 'transType', cc : '224', width : '6%'},
             {display: '交付物', name : 'transObj',cc : '226',  width : '8%'},
             {display: '交付物数量', name : 'transObjCount',  width : '8%'},
             {display: '接收人', name : 'receiverName',  width : '8%'},
             {display: '任务状态', name : 'taskStatus',cc : 'taskStatus',  width : '5%'},
             {display: '任务阶段', name : 'taskStage', cc : 'taskStage', width : 'auto'}
             ],
         extAtt: {
    	 id : {begin : '<a href=' + gurl + 'findTask&id={id}&update=0>', end : '</a>'}
         },
         buttons : [
             //{id: 'add', bclass: 'add',caption : '任务创建', onpress : addBean},
             //{id: 'update', bclass: 'update', onpress : updateBean},
             {id: 'export', bclass: 'replied',  caption: '导出', onpress : exports},
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

function exports()
{
    document.location.href = gurl + 'exportTask';
}

function doSearch()
{
    $modalQuery('../admin/query.do?method=popCommonQuery2&key=project.queryProjectAllTask');
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