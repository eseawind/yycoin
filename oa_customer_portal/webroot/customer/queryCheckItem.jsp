<%@page contentType="text/html; charset=UTF-8"%>
<%@include file="../common/common.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<p:link title="客户信息审核" link="true" guid="true" cal="true" dialog="true"/>
<script src="../js/common.js"></script>
<script src="../js/public.js"></script>
<script src="../js/pop.js"></script>
<script src="../js/json.js"></script>
<script src="../js/JCheck.js"></script>
<script src="../js/jquery.blockUI.js"></script>
<script src="../js/plugin/highlight/jquery.highlight.js"></script>
<script type="text/javascript">

var allDef = window.top.topFrame.allDef;
var guidMap;
var thisObj;

var updatFlag = window.top.topFrame.containAuth('021602') ? '1' : '0';
function load()
{
	 preload();
	 
	 guidMap = {
		 title: '客户信息审核',
		 url: '../customer/check.do?method=queryCheckItem&look=${param.look}&id=${param.id}',
		 colModel : [
		     {display: '选择', name : 'check', content : '<input type=radio name=checkb value={id} lstatus={status} lcustomerId = {customerId} lret={ret} ldec="{description}">',
		               width : 40, sortable : false, align: 'center'},
		     {display: '客户', name : 'customerName', width : '15%'},
		     {display: '编码', name : 'customerCode', width : '8%'},
		     {display: '标识', name : 'parentId', width : '8%'},
		     {display: '所属职员', name : 'stafferName', width : '8%'},
		     {display: '核对结果', name : 'ret', width : '5%', cc: 'commonResult'},
		     {display: '核对信息', name : 'description', content: '<p title={description}>{description}</p>', width : '25%'},
		     {display: '状态', name : 'status', width : '5%', cc: 'commonStatus'},
		     {display: '时间', name : 'logTime', width : 'auto', sortable : true}
		     ],
		 extAtt: {
		     customerName : {begin : '<a href=../client/client.do?method=findClient&id={customerId}&update=3&linkId={id}>', end : '</a>'}
		 },
		 buttons : [
		     {id: 'pass', bclass: 'pass', caption: '信息符合', onpress : passBean, auth: '021601'},
		     {id: 'reject', bclass: 'reject', caption: '信息有误', onpress : rejectBean, auth: '021601'},
		     {id: 'search', bclass: 'search', onpress : doSearch}
		     ],
		<p:conf/>
	 };
	 
	 $("#mainTable").flexigrid(guidMap, thisObj);
 }
 
function doSearch()
{
    $modalQuery('../admin/query.do?method=popCommonQuery2&key=queryCheckItem');
}

function $callBack()
{
    loadForm();
    
    highlights($("#mainTable").get(0), ['异常'], 'red');
    
}

function callBackFun(data)
{
    reloadTip(data.msg, data.ret == 0);

    if (data.ret == 0)
    commonQuery();
}

function passBean(opr, grid)
{
    if (getRadio('checkb') && getRadioValue('checkb'))
    {
       if (getRadio('checkb').lstatus == 0)
       {
           if (window.confirm('客户信息正确, 确定通过?')) 
           $ajax('../customer/check.do?method=passItem&id=' + getRadioValue('checkb'), callBackFun);
       }
       else
       {
           $error('不能操作');
       }
    }
}

function rejectBean(opr, grid)
{
    if (getRadio('checkb') && getRadioValue('checkb'))
    {
        if (getRadio('checkb').lstatus == 0)
        {
            $O('reason').value = '';
            $.blockUI({ message: $('#dataDiv'),css: defaultCSS});
        }
        else
        {
            $error('不能操作');
        }
    }
}

function queryReason(opr, grid)
{
    if (getRadio('checkb') && getRadioValue('checkb'))
    {
        if (getRadio('checkb').lret == 2)
        { 
            $O('logD').innerHTML = getRadio('checkb').ldec;
            $.blockUI({ message: $('#logDiv'),css: centerCSS});
        }
    }
}


function $close()
{
    $.unblockUI();
}

function $process()
{
    if (eCheck([$O('reason')]))
    {
        if (window.confirm('确定客户信息有误?'))
        {
            $ajax('../customer/check.do?method=rejectItem&id=' + getRadioValue('checkb') + '&reason=' + $$('reason'), callBackFun);
            
            $.unblockUI();
        }
    }
}
</script>
</head>
<body onload="load()" class="body_class">
<form>
<p:cache></p:cache>
</form>
<div id="dataDiv" style="display:none">
<p align='left'><label><font color=""><b>请输入备注</b></font></label></p>
<p><label>&nbsp;</label></p>
备注：<textarea name="reason" value="" rows="4" oncheck="notNone;maxLength(100)" style="width: 80%"></textarea>
<p><label>&nbsp;</label></p>
<p>
<input type='button' value='&nbsp;&nbsp;确 定&nbsp;&nbsp;' id='div_b_ok1' class='button_class' onclick='$process()'/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
<input type='button' id='div_b_cancle' value='&nbsp;&nbsp;关 闭&nbsp;&nbsp;' class='button_class' onclick='$close()'/>
</p>
<p><label>&nbsp;</label></p>
</div>

<div id="logDiv" style="display:none">
<p align='left'><label><font color=""><b>核对信息:</b></font></label></p>
<p><label>&nbsp;</label></p>
<div id="logD" align='left'>
</div>
<p><label>&nbsp;</label></p>
<p>
<input type='button' id='div_b_cancle' value='&nbsp;&nbsp;关 闭&nbsp;&nbsp;' class='button_class' onclick='$close()'/>
</p>
<p><label>&nbsp;</label></p>
</div>
<p:message></p:message>
<table id="mainTable" style="display: none"></table>
<p:query/>
</body>