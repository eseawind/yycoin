<%@page contentType="text/html; charset=UTF-8"%>
<%@include file="../common/common.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<p:link title="分公司管理" link="true" guid="true" cal="false"/>
<script src="../js/common.js"></script>
<script src="../js/pop.js"></script>
<script src="../js/json.js"></script>
<script type="text/javascript">

var allDef = window.top.topFrame.allDef;

function load()
{
	 $("#mainTable").flexigrid
	 (
	 {
		 title: '分公司(四级组织)列表',
		 url: '../admin/location.do?method=queryLocation',
		 colModel : [
		     {display: '选择', name : 'check', content : '<input type=radio name=checkb value={id} lname={name}>', width : 40, sortable : false, align: 'center'},
		     {display: '名称', name : 'name', width : '20%', sortable : false, align: 'left'},
		     {display: '标识', name : 'code', width : '15%', sortable : false, align: 'left'},
		     {display: '描述', name : 'description', width : 'auto', sortable : false, align: 'left'}
		     ],
		 extAtt: {
		     name : {begin : '<a href=../admin/location.do?method=findLocation&opr=1&locationId={id} title=更新管辖权区域>', end : '</a>'}
		 },
		 buttons : [
		     //{id: 'add', bclass: 'add', onpress : addBean},
		     {id: 'update',  caption: '更新管辖权区域', bclass: 'update', onpress : oprBean},
		     {id: 'del', caption: '删除', bclass: 'delete', onpress : delBean},
		     {separator: true}
		     ],
		 <p:conf callBack="loadForm"/>
	 }
	 );
 }
 
function addBean()
{
    $l('../admin/addLocation.jsp');
}

function delBean(opr, grid)
{
    if (getRadio('checkb') && getRadioValue('checkb'))
    {
        if (window.confirm('删除分公司会导致大量的分公司数据丢失,确定删除--' + getRadio('checkb').lname))
        {
            document.mainForm.locationId.value = getRadioValue('checkb');
            
            document.mainForm.submit();
        }
    }
}

function oprBean()
{
    if (getRadio('checkb') && getRadioValue('checkb'))
    {
        $l('../admin/location.do?method=findLocation&opr=1&locationId=' + getRadioValue('checkb'));
    }
}

</script>
</head>
<body onload="load()" class="body_class">
<form name="mainForm" action="../admin/location.do" method="post" id="mainForm">
<input type="hidden" name="method" value="delLocation"/>
<input type="hidden" name="locationId" value=""/>
<p:cache/>
</form>
<p:message></p:message>
<table id="mainTable" style="display: none"></table>
</body>