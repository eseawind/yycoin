<%@page contentType="text/html; charset=UTF-8"%>
<%@include file="../common/common.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<p:link title="人员管理" link="true" guid="true" cal="false" dialog="true"/>
<script src="../js/common.js"></script>
<script src="../js/public.js"></script>
<script src="../js/pop.js"></script>
<script src="../js/json.js"></script>
<script src="../js/plugin/highlight/jquery.highlight.js"></script>
<script type="text/javascript">
var guidMap;
var thisObj;

var allDef = window.top.topFrame.allDef;

function load()
{
     preload();
    
	 guidMap = {
		 title: '人员列表',
		 url: '../admin/staffer.do?method=queryStaffer',
		 colModel : [
		     {display: '选择', name : 'check', content : '<input type=radio name=checkb value={id} lname={name} lstatus={status}>', width : 40, sortable : false, align: 'center'},
		     {display: '工号', name : 'code', width : '8%', sortable : false, align: 'left'},
		     {display: '名称', name : 'name', width : '10%', sortable : false, align: 'left'},
		     {display: '事业部', name : 'industryName', width : '10%', sortable : false, align: 'left'},
		     {display: '大区', name : 'industryName2', cname : 'departmentId', width : '15%', sortable : true, align: 'left'},
		     {display: '部门', name : 'industryName3', width : '12%', sortable : false, align: 'left'},
		     {display: '类型', name : 'examType', width : '5%', sortable : false, align: 'left', cc : 'examType'},
		     {display: '状态', name : 'status', width : '5%', sortable : false, align: 'left', cc : 'stafferStatus'},
		     {display: '加密', name : 'enc', width : '8%'},
		     {display: '额度', name : 'credit', width : '8%',  toFixed: 2},
		     {display: '信用属性', name : 'black', width : 'auto', sortable : false, cc : 'stafferBlack'}
		     ],
		 extAtt: {
		     code : {begin : '<a href=../admin/staffer.do?method=findStaffer&id={id} title=查看明细>', end : '</a>'}
		 },
		 buttons : [
		     {id: 'add', bclass: 'add', onpress : addBean},
		     {id: 'update', bclass: 'update', onpress : updateBean},
		     {id: 'update', bclass: 'update',caption: '加密锁设置', onpress : updateKey},
		     {id: 'update', bclass: 'update',caption: '工作交接', onpress : transferWork},
		     {id: 'del', bclass: 'delete',caption: '废弃', onpress : dropBean},
		     {id: 'del2', bclass: 'delete',caption: '删除', onpress : delBean},
		     {id: 'search', bclass: 'search', onpress : doSearch},
		     {separator: true}
		     ],
		 <p:conf/>
	 };
	 
	 $("#mainTable").flexigrid(guidMap, thisObj);
 }

function $callBack()
{
    loadForm();
    
    highlights($("#mainTable").get(0), ['设置', '正常'], 'blue');
    
    highlights($("#mainTable").get(0), ['未设置', '废弃'], 'red');
}
 
function doSearch()
{
    $modalQuery('../admin/query.do?method=popCommonQuery2&key=queryStaffer');
}

function transferWork()
{
    if (getRadio('checkb') && getRadioValue('checkb') && getRadio('checkb').lstatus == 99)
    {
        $l('../admin/staffer.do?method=preForTransferWork&srcId=' + getRadioValue('checkb'));
    }
    else
    $error();
}

function addBean()
{
    $l('../admin/staffer.do?method=preForAddStaffer');
}

function delBean(opr, grid)
{
    if (getRadio('checkb') && getRadioValue('checkb') && getRadio('checkb').lstatus == 99)
    {
        if (window.confirm('确定删除--' + getRadio('checkb').lname))
        {
            document.mainForm.stafferId.value = getRadioValue('checkb');
            
            document.mainForm.method.value = 'delStaffer';
            
            document.mainForm.submit();
        }
    }
    else
    $error();
}

function dropBean(opr, grid)
{
    if (getRadio('checkb') && getRadioValue('checkb'))
    {
        if (window.confirm('确定废弃--' + getRadio('checkb').lname))
        {
            document.mainForm.stafferId.value = getRadioValue('checkb');
            
            document.mainForm.method.value = 'dropStaffer';
            
            document.mainForm.submit();
        }
    }
    else
    $error();
}

function updateBean(opr, grid)
{
    if (getRadio('checkb') && getRadioValue('checkb'))
    {
       $l('../admin/staffer.do?method=findStaffer&id=' + getRadioValue('checkb') + '&update=1');
    }
    else
    $error();
}

function updateKey(opr, grid)
{
    if (getRadio('checkb') && getRadioValue('checkb'))
    {
       $l('../admin/staffer.do?method=preForSetpwkey&id=' + getRadioValue('checkb'));
    }
    else
    $error();
}

function commonQuery(par)
{
    gobal_guid.p.queryCondition = par;
    
    gobal_guid.grid.populate(true);
}
</script>
</head>
<body onload="load()" class="body_class">
<form name="mainForm" action="../admin/staffer.do" method="post">
<input type="hidden" name="method" value=""/>
<input type="hidden" name="stafferId" value=""/>
<p:cache></p:cache>
</form>
<p:message></p:message>
<table id="mainTable" style="display: none"></table>
<p:query/>
</body>