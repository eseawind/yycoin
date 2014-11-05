<%@page contentType="text/html; charset=UTF-8"%>
<%@include file="../common/common.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<p:link title="流程实例管理" link="true" guid="true" cal="true" dialog="true" />
<script src="../js/JCheck.js"></script>
<script src="../js/common.js"></script>
<script src="../js/public.js"></script>
<script src="../js/pop.js"></script>
<script src="../js/json.js"></script>
<script src="../js/jquery.blockUI.js"></script>
<script src="../js/plugin/highlight/jquery.highlight.js"></script>
<script type="text/javascript">

var titleMap = {"0" : "我的实例", "1" : "我的任务", "2" : "我的查阅", "3" : "历史处理"};

var sMap = {"0" : "创建人", "1" : "创建人", "2" : "创建人", "3" : "创建/处理人"};

var cMap = {"0" : "{stafferName}", "1" : "{createName}", "2" : "{createName}", "3" : "{createName}/{stafferName}"};

var buttonsMap = 
{
    "0" : 
    [
        {id: 'update', bclass: 'update',  caption : '编辑', onpress : updateBean},
        //{id: 'submits', bclass: 'pass', caption : '提交',  onpress : configBean},
        {id: 'del', bclass: 'del',  onpress : delBean},
        {id: 'search', bclass: 'search',  onpress : doSearch}
    ],
    "1" : 
    [
        {id: 'update', bclass: 'update',  caption : '处理申请', onpress : updateBean2},
        {id: 'search', bclass: 'search',  onpress : doSearch}
    ],
    "2" : 
    [
        {id: 'search', bclass: 'search',  onpress : doSearch}
    ],    
    "3" : 
    [
        {id: 'search', bclass: 'search',  onpress : doSearch}
    ]
};
var allDef = window.top.topFrame.allDef;
var guidMap;
var thisObj;
function load()
{
     preload();
      
	 guidMap = {
		 title: titleMap['${param.operation}'],
		 url: '../flow/instance.do?method=queryFlowInstance&operationMode=${param.operation}',
		 colModel : [
		     {display: '选择', name : 'check', content : '<input type=radio name=checkb value={instanceId} sstatus={status}>', width : 40, align: 'center'},
		     {display: '标识', name : 'instanceId', width : '15%'},
		     {display: '主题', name : 'title', width : '30%'},
		     {display: sMap['${param.operation}'], name : 'stafferName', content: cMap['${param.operation}'], sortable : false, width : '8%'},
		     {display: '当前环节', name : 'tokenName', width : '10%'},
		     {display: '流程定义', name : 'flowName', sortable : true, cname: 'FlowDefineBean.name', width : '15%'},
		     {display: '更新时间', name : 'logTime', sortable : true, width : 'auto'}
		     ],
		 extAtt: {
		     instanceId : {begin : '<a href=../flow/instance.do?method=findFlowInstance&id={instanceId} title=查看流程实例>', end : '</a>'}
		 },
		 buttons : buttonsMap['${param.operation}'],
		 <p:conf/>
	 };
	 
	 $("#mainTable").flexigrid(guidMap, thisObj);
}
 
function $callBack()
{
    loadForm();
    
    $.highlight($("#mainTable").get(0), '结束环节', 'blue');
}
 

function addBean(opr, grid)
{
    $l('../flow/addFlowDefine.jsp');
}

function delBean(opr, grid)
{
    if (getRadio('checkb') && getRadioValue('checkb') && getRadio('checkb').sstatus == 0 && window.confirm('确定删除流程实例?'))
    $ajax('../flow/instance.do?method=deleteFlowInstance&id=' + getRadioValue('checkb'), callBackFun);
}

function dropBean(opr, grid)
{
    if (getRadio('checkb') && getRadioValue('checkb') && getRadio('checkb').sstatus == 1 && window.confirm('确定废弃流程实例?'))
    $ajax('../flow/flow.do?method=dropFlowDefine&id=' + getRadioValue('checkb'), callBackFun);
}

function updateBean(opr, grid)
{
    if (getRadio('checkb') && getRadioValue('checkb') && getRadio('checkb').sstatus == 0)
    {
        $l('../flow/instance.do?method=findFlowInstance&update=1&id=' + getRadioValue('checkb'));
    }
}

function updateBean2(opr, grid)
{
    if (getRadio('checkb') && getRadioValue('checkb') && getRadio('checkb').sstatus != 0)
    {
        $l('../flow/instance.do?method=findFlowInstance&update=1&id=' + getRadioValue('checkb'));
    }
}

function configBean()
{
    if (getRadio('checkb') && getRadioValue('checkb') && getRadio('checkb').sstatus == 0)
    {
        $.blockUI({ message: $('#dataDiv'),css:{width: '40%'}});
    }
}

function doSearch()
{
    $modalQuery('../admin/query.do?method=popCommonQuery2&key=queryFlowInstance${param.operation}');
}

function $process()
{
    if (getRadio('checkb') && getRadioValue('checkb') && eCheck([$O('totalTokens')]))
    {
        var sid = $$('totalTokens');
    
        $l('../flow/flow.do?method=preForConfigToken&id=' + $$('checkb') + '&totalTokens=' + sid);
    }
}

</script>
</head>
<body onload="load()" class="body_class">
<form name="mainForm" action="../flow/template.do" method="post">
<p:cache></p:cache>
</form>

<div id="dataDiv" style="display:none">
<p align='left'><label><font color=""><b>请输入环节数</b></font></label></p>
<p><label>&nbsp;</label></p>
<input type="text" id="totalTokens" value="2" oncheck="isNumber;range(2, 99)" style="width: 85%"/>
<p><label>&nbsp;</label></p>
<p>
<input type='button' value='&nbsp;&nbsp;确 定&nbsp;&nbsp;' id='div_b_ok1' class='button_class' onclick='$process()'/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
<input type='button' id='div_b_cancle' value='&nbsp;&nbsp;关 闭&nbsp;&nbsp;' class='button_class' onclick='$close()'/>
</p>
<p><label>&nbsp;</label></p>
</div>

<p:message></p:message>
<table id="mainTable" style="display: none"></table>
<p:query/>
</body>