<%@page contentType="text/html; charset=UTF-8"%>
<%@include file="../common/common.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<p:link title="新产品申请" link="true" guid="true" cal="true" dialog="true" />
<script src="../js/common.js"></script>
<script src="../js/public.js"></script>
<script src="../js/JCheck.js"></script>
<script src="../js/pop.js"></script>
<script src="../js/plugin/highlight/jquery.highlight.js"></script>
<script type="text/javascript">

var gurl = '../product/productApply.do?method=';
var addUrl = '../product/addProductApply.jsp';
var ukey = 'ProductApply';

var allDef = window.top.topFrame.allDef;
var guidMap;
var thisObj;
function load()
{
     preload();
     
     guidMap = {
         title: '新产品申请列表',
         url: gurl + 'query' + ukey + '&forward=${param.forward}' ,
         colModel : [
             {display: '选择', name : 'check', content : '<input type=radio name=checkb value={id} lstatus={status}>', width : 40, align: 'center'},
             {display: '名称', name : 'name', width : '15%'},
             {display: '完整名称', name : 'fullName', width : '15%'},
             {display: '产品类型', name : 'type', width : '10%',cc:'productType'},
             {display: '材质类型', name : 'materiaType', width : '10%',cc:201},
             {display: '管理类型', name : 'managerType',  width : '8%', cc: 'pubManagerType'},
             {display: '销售周期', name : 'salePeriod',  width : '8%', cc: 207},
             {display: '产品性质', name : 'nature',  width : '8%', cc: 'natureType'},
             {display: '申请状态', name : 'status',  width : '8%', cc: 'productApplyStatus'},
             {display: '申请人', name : 'oprName',  width : '5%'},
             {display: '申请时间', name : 'logTime',width : 'auto' }
             ],
         extAtt: {
             name : {begin : '<a href=' + gurl + 'find' + ukey + '&id={id}>', end : '</a>'}
         },
         buttons : [
			<c:if test="${param.forward != 1 && param.forward != 2}" >                
             {id: 'add', bclass: 'add', onpress : addBean},
             {id: 'update', bclass: 'update', onpress : updateBean},
             {id: 'del', bclass: 'del',  onpress : delBean},
            </c:if>
             
             <c:if test="${param.forward == 1}" >
             {id: 'pass', bclass: 'pass', caption: '部门审批', onpress : passBean},
             {id: 'reject', bclass: 'reject', caption: '驳回', onpress : rejectBean},
             </c:if>

             <c:if test="${param.forward == 2}" >
             {id: 'pass1', bclass: 'pass', caption: '产品管理中心审批', onpress : pass1Bean},
             {id: 'reject', bclass: 'reject', caption: '驳回', onpress : rejectBean},
             </c:if>
             {id: 'search', bclass: 'search', onpress : doSearch}
             ],
         procmsg: '正在获取数据，请稍后...',
         <p:conf/>
     };
     
     $("#mainTable").flexigrid(guidMap, thisObj);
     
}
 
function $callBack()
{
    loadForm();
    
    highlights($("#mainTable").get(0), ['驳回'], 'red');
    highlights($("#mainTable").get(0), ['结束'], 'blue');
    
}

function addBean(opr, grid)
{
    $l(gurl + 'preForAdd' + ukey);
    //$l(addUrl);
}

function delBean(opr, grid)
{
    if (getRadio('checkb') && getRadioValue('checkb'))
    {    
        if(window.confirm('确定删除?'))    
        $ajax(gurl + 'delete' + ukey + '&id=' + getRadioValue('checkb'), callBackFun);
    }
    else
    $error('不能操作');
}

function updateBean()
{
	if (getRadio('checkb') && getRadioValue('checkb'))
	{	
		$l(gurl + 'find' + ukey + '&update=1&id=' + getRadioValue('checkb'));
	}
	else
	$error('不能操作');
}

function doSearch()
{
    $modalQuery('../admin/query.do?method=popCommonQuery2&key=query' + ukey);
}

//部门审批
function passBean(opr, grid)
{
    if (getRadio('checkb') && getRadioValue('checkb') && getRadio('checkb').lstatus == 3)
    {    
        if(window.confirm('确定通过此新品申请?'))    
        $ajax(gurl + 'pass' + ukey + '&id=' + getRadioValue('checkb'), callBackFun);
    }
    else
    $error('不能操作');
}

function pass1Bean(opr, grid){

    if (getRadio('checkb') && getRadioValue('checkb') && getRadio('checkb').lstatus == 4)
    {    
        if(window.confirm('确定通过此新品申请?'))    
        $ajax(gurl + 'pass1' + ukey + '&id=' + getRadioValue('checkb'), callBackFun);
    }
    else
    $error('不能操作');
}

function rejectBean(){

    if (getRadio('checkb') && getRadioValue('checkb') && (getRadio('checkb').lstatus == 3 || getRadio('checkb').lstatus == 4))
    {    
        if(window.confirm('确定驳回此新品申请?'))    
        $ajax(gurl + 'reject' + ukey + '&id=' + getRadioValue('checkb'), callBackFun);
    }
    else
    $error('不能操作');
}

</script>
</head>
<body onload="load()" class="body_class">
<form name="mainForm" method="post">
<p:cache></p:cache>
</form>
<p:message></p:message>
<table id="mainTable" style="display: none"></table>

<p:query />
</body>