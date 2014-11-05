<%@page contentType="text/html; charset=UTF-8"%>
<%@include file="../common/common.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<p:link title="供应商管理" link="true" guid="true" cal="true" dialog="true" />
<script src="../js/common.js"></script>
<script src="../js/public.js"></script>
<script src="../js/pop.js"></script>
<script src="../js/json.js"></script>
<script type="text/javascript">

var allDef = window.top.topFrame.allDef;
var guidMap;
var thisObj;

var updatFlag = window.top.topFrame.containAuth('0213') ? '1' : '0';

function load()
{
     preload();
     
	 guidMap = {
		 title: '供应商列表',
		 url: '../provider/provider.do?method=queryProvider',
		 colModel : [
		     {display: '选择', name : 'check', content : '<input type=radio name=checkb value={id} lname={name}>', width : 40, sortable : false, align: 'center'},
		     {display: '名称', name : 'name', width : '20%', sortable : false, align: 'left'},
		     {display: '编码', name : 'code', width : '10%', sortable : false, align: 'left'},
		     {display: '类型', name : 'type', width : '10%', sortable : false, align: 'left', cc: 109},
		     {display: '询价用户', name : 'loginName', width : '8%'},
		     {display: '分类', name : 'typeName', width : '20%'},
		     {display: '所属片区', name : 'location', width : '8%', sortable : false, align: 'left', cc: 123},
		     {display: '时间', name : 'logTime', width : 'auto', sortable : true, align: 'left'}
		     ],
		 extAtt: {
		     name : {begin : '<a href=../provider/provider.do?method=findProvider&id={id}>', end : '</a>'}
		 },
		 buttons : [
		     {id: 'add', bclass: 'add', onpress : addBean, auth: '0213'},
		     {id: 'update', bclass: 'update', onpress : updateBean, auth: '0213'},
		     {id: 'update0', bclass: 'edit', caption: '绑定分类', onpress : bingType, auth: '0213'},
		     {id: 'update1', bclass: 'edit', caption: '更新登录用户', onpress : updateUserBean, auth: '0213'},
		     {id: 'update2', bclass: 'edit', caption: '重置密码', onpress : updateUserPassword, auth: '0213'},
		     {id: 'del', bclass: 'delete', onpress : delBean, auth: '0213'},
		     {id: 'search', bclass: 'search', onpress : doSearch}
		     ],
		 <p:conf callBack="loadForm"/>
	 };
	 
	 $("#mainTable").flexigrid(guidMap, thisObj);
 }
 
function doSearch()
{
    $modalQuery('../admin/query.do?method=popCommonQuery2&key=queryProvider');
}


function delBean()
{
    if (getRadio('checkb') && getRadioValue('checkb'))
    {
        if (window.confirm('确定删除--' + getRadio('checkb').lname))
        {
            $ajax('../provider/provider.do?method=delProvider&id=' + getRadioValue('checkb'), callBackFun);
        }
    }
}

function updateBean(opr, grid)
{
    if (getRadio('checkb') && getRadioValue('checkb'))
    {
       $l('../provider/provider.do?method=findProvider&update=1&id=' + getRadioValue('checkb'));
    }
}

function updateUserBean(opr, grid)
{
    if (getRadio('checkb') && getRadioValue('checkb'))
    {
       $l('../provider/provider.do?method=findProviderUser&update=1&id=' + getRadioValue('checkb'));
    }
}

function updateUserPassword()
{
    if (getRadio('checkb') && getRadioValue('checkb'))
    {
        if (window.confirm('确定重置' + getRadio('checkb').lname + '的用户密码?'))
        {
            $ajax('../provider/provider.do?method=updateUserPassword&id=' + getRadioValue('checkb'), callBackFun);
        }
    }
}

function bingType()
{
    if (getRadio('checkb') && getRadioValue('checkb'))
    {
       $l('../provider/provider.do?method=preForBing&id=' + getRadioValue('checkb'));
    }
}

function addBean(opr, grid)
{
   $l('../provider/addProvider.jsp');
}
</script>
</head>
<body onload="load()" class="body_class">
<form>
<p:cache></p:cache>
</form>
<p:message></p:message>
<table id="mainTable" style="display: none"></table>
<p:query/>
</body>