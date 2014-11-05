<%@page contentType="text/html; charset=UTF-8"%>
<%@include file="../common/common.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<p:link title="客户管理" link="true" guid="true" cal="true"/>
<link href="../js/plugin/dialog/css/dialog.css" type="text/css" rel="stylesheet"/>
<script src="../js/common.js"></script>
<script src="../js/public.js"></script>
<script src="../js/pop.js"></script>
<script src="../js/json.js"></script>
<script src="../js/plugin/dialog/jquery.dialog.js"></script>
<script type="text/javascript">

var allDef = window.top.topFrame.allDef;
var guidMap;
var thisObj;
function load()
{
	 guidMap = {
		 title: '申请客户列表',
		 url: '../client/client.do?method=queryCheckApplyClient',
		 colModel : [
		     {display: '选择', name : 'check', content : '<input type=radio name=checkb value={id} lname={name} lopr={opr}>', width : 40, sortable : false, align: 'center'},
		     {display: '客户', name : 'name', width : '20%', sortable : false, align: 'left'},
		     {display: '操作', name : 'opr', width : '10%', sortable : false, align: 'left', cc: 'customerOpr'},
		     {display: '申请人', name : 'stafferName', width : '10%', sortable : false, align: 'left'},
		     {display: '类型', name : 'selltype', width : '10%', sortable : false, align: 'left', cc: 101},
		     {display: '级别', name : 'qqtype', width : '10%', sortable : false, align: 'left', cc: 104},
		     {display: '分类', name : 'rtype', width : '10%', sortable : false, align: 'left', cc: 105},
		     {display: '状态', name : 'status', width : '11%', sortable : false, align: 'left', cc: 'customerStatus'},
		     {display: '时间', name : 'logTime', width : 'auto', sortable : true, align: 'left'}
		     ],
		 extAtt: {
		     name : {begin : '<a href=../client/client.do?method=findApplyClient&id={id}&upd=1>', end : '</a>'}
		 },
		 buttons : [
		     {id: 'pass', caption: '通过',bclass: 'pass', auth: '0220', onpress : doPass},
		     {id: 'reject', caption: '驳回',bclass: 'reject', auth: '0220', onpress : doReject}
		     //{id: 'pass1', caption: '批量通过',bclass: 'pass', auth: '0220', onpress : doPass1}
		     ],
		 <p:conf callBack="loadForm" queryMode="0"/>
	 };
	 
	 $("#mainTable").flexigrid(guidMap, thisObj);
 }
 
function doPass()
{
    if (getRadio('checkb') && getRadioValue('checkb'))
    {
        var opr = getRadio('checkb').lopr;

        if (opr == 1)
        {
        	 $l('../client/client.do?method=findClient&update=99&id='+ getRadioValue('checkb'));
        }
        else
        {
            if (window.confirm('确定通过--' + getRadio('checkb').lname))
            {
                $ajax('../client/client.do?method=processApply&operation=0&id=' + getRadioValue('checkb'), callBackFun);
            }
        }
    }
}

function doPass1()
{
     if (window.confirm('确定批量通过?'))
     {
         //$ajax('../client/client.do?method=batchProcessApply', callBackFun);
     }
}

function callBackFun(data)
{
    reloadTip(data.msg, data.ret == 0);
    
    if (data.ret == 0)
    commonQuery();
}

function doReject()
{
    $.messager.prompt('驳回', '请输入驳回原因', '', function(r){
                    if (r)
                    {
                        $ajax2('../client/client.do?method=processApply&operation=1&id=' + getRadioValue('checkb'), {'reson' : r},  callBackFun);
                    }
                   
                }, 2);
}

</script>
</head>
<body onload="load()" class="body_class">
<form>
</form>
<p:cache/>
<p:message></p:message>
<table id="mainTable" style="display: none"></table>
</body>