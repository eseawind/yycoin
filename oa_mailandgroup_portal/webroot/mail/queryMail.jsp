<%@page contentType="text/html; charset=UTF-8"%>
<%@include file="../common/common.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<p:link title="邮件管理" link="true" guid="true" cal="true" dialog="true" />
<script src="../js/common.js"></script>
<script src="../js/public.js"></script>
<script src="../js/pop.js"></script>
<script src="../js/json.js"></script>
<script src="../mg_js/defined.js"></script>
<script type="text/javascript">
var allDef = window.top.topFrame.allDef;
var guidMap;
var thisObj;
function load()
{
    preload();
    
	 guidMap = {
		 title: '我的收件箱',
		 url: '../mail/mail.do?method=queryMail',
		 colModel : [
		     {display: '<input type=checkbox id=flexi_Check onclick=checkAll(this)> 选择', name : 'check', content : '<input type=checkbox name=checkb value={id} lname={name}>', width : 55, align: 'center'},
		     {display: '状态', name : 'status', width : '35', sortable : true, cc: 'mailStatusIco', align: 'center'},
		     {display: '主题', name : 'title', width : '45%'},
		     {display: '发送人', name : 'senderName', sortable : true, width : '7%'},
		     {display: '附件', name : 'attachment', width : '35', sortable : true, cc: 'mailAttachmentDisplay', align: 'center'},
		     {display: '回复', name : 'feeback', width : '35', cc: 'mailFeebackIco', align: 'center'},
		     {display: '时间', name : 'logTime', sortable : true, width : 'auto'}
		     ],
		 extAtt: {
		     title : {begin : '<a href=../mail/mail.do?method=findMail&id={id} title=查看邮件内容>', end : '</a>'}
		 },
		 buttons : [
		     {id: 'add', bclass: 'add',  caption: '新建', onpress : addBean},
		     {id: 'update', bclass: 'replied', caption: '回复', onpress : replyBean},
		     {id: 'update1', bclass: 'replied', caption: '回复所有', onpress : replyAllBean},
		     {id: 'update2', bclass: 'forward', caption: '转发', onpress : forwardBean},
		     {id: 'del', bclass: 'del',  onpress : delBean},
		     {id: 'search', bclass: 'search',  onpress : doSearch}
		     ],
		 <p:conf callBack="loadForm" def="joinMap2(allDef)"/>
	 };
	 
	 $("#mainTable").flexigrid(guidMap, thisObj);
 }

function addBean(opr, grid)
{
    $l('../mail/addMail.jsp');
}

function delBean(opr, grid)
{
    var clis = getCheckBox('checkb');
    
    if (clis.length > 0)
    {
        var str = '';
        
        for (var i = 0; i < clis.length; i++)
        {
            str += clis[i].value + ';';
        }
        
        if (window.confirm('确定删除选中的邮件?'))
        {
            $ajax('../mail/mail.do?method=deleteMail&ids=' + str, callBackFun);
        }
    }
}


function replyBean(opr, grid)
{
    if (getRadio('checkb') && getRadioValue('checkb'))
    {
        $l('../mail/mail.do?method=findMail&operation=2&id=' + getRadioValue('checkb'));
    }
}


function replyAllBean(opr, grid)
{
    if (getRadio('checkb') && getRadioValue('checkb'))
    {
        $l('../mail/mail.do?method=findMail&operation=3&id=' + getRadioValue('checkb'));
    }
}

function forwardBean(opr, grid)
{
    if (getRadio('checkb') && getRadioValue('checkb'))
    {
        $l('../mail/mail.do?method=findMail&operation=1&id=' + getRadioValue('checkb'));
    }
}

function doSearch()
{
    $modalQuery('../admin/query.do?method=popCommonQuery2&key=queryMail');
}

</script>
</head>
<body onload="load()" class="body_class">
<form name="mainForm" action="../mail/mail.do" method="post">
<p:cache></p:cache>
</form>
<p:message></p:message>
<table id="mainTable" style="display: none"></table>
<p:query/>
</body>
