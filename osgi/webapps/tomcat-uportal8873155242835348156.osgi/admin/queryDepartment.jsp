<%@page contentType="text/html; charset=UTF-8"%>
<%@include file="../common/common.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<p:link title="部门管理" link="true" guid="true" cal="false"/>
<script src="../js/common.js"></script>
<script src="../js/public.js"></script>
<script src="../js/pop.js"></script>
<script src="../js/json.js"></script>
<script type="text/javascript">

var allDef = window.top.topFrame.allDef;
var guidMap;
var thisObj;

function load()
{
	 guidMap = {
		 title: '部门列表',
		 url: '../admin/common.do?method=queryDepartment',
		 colModel : [
		     {display: '选择', name : 'check', content : '<input type=radio name=checkb value={id} lname={name}>', width : 40, sortable : false, align: 'center'},
		     {display: '名称', name : 'name', width : 'auto', sortable : false, align: 'left'}
		     ],
		 extAtt: {
		     name : {begin : '<a href=../admin/common.do?method=findDepartment&id={id}&update=2>', end : '</a>'}
		 },
		 buttons : [
		     {id: 'add', bclass: 'add', onpress : addBean, auth: '0107'},
		     {id: 'update', bclass: 'update', onpress : updateBean, auth: '0107'},
		     {id: 'del', bclass: 'delete', onpress : delBean, auth: '0107'}
		     ],
		 <p:conf callBack="loadForm"/>
	 };
	 
	 $("#mainTable").flexigrid(guidMap, thisObj);
 }

function addBean(opr, grid)
{
    $l('../admin/addOrUpdateDepartment.jsp');
}

function delBean()
{
    if (getRadio('checkb') && getRadioValue('checkb'))
    {
        if (window.confirm('确定删除--' + getRadio('checkb').lname))
        {
            $ajax('../admin/common.do?method=delDepartment&id=' + getRadioValue('checkb'), callBackFun);
        }
    }
}

function callBackFun(data)
{
    reloadTip(data.msg, data.ret == 0);
    
    if (data.ret == 0)
    commonQuery();
}

function updateBean(opr, grid)
{
    if (getRadio('checkb') && getRadioValue('checkb'))
    {
       $l('../admin/common.do?method=findDepartment&id=' + getRadioValue('checkb'));
    }
}

function commonQuery(par)
{
    gobal_guid.p.queryCondition = par;
    
    gobal_guid.grid.populate(true);
}
</script>
</head>
<body onload="load()" class="body_class">
<form>
<p:cache/>
</form>
<p:message></p:message>
<table id="mainTable" style="display: none"></table>
</body>