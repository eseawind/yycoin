<%@page contentType="text/html; charset=UTF-8"%>
<%@include file="../common/common.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<p:link title="配置项管理" link="true" guid="true" cal="true" dialog="true" />
<script src="../js/common.js"></script>
<script src="../js/public.js"></script>
<script src="../js/pop.js"></script>
<script src="../js/plugin/highlight/jquery.highlight.js"></script>
<script type="text/javascript">

var gurl = '../admin/enum.do?method=';
var addUrl = '../admin/addEnum.jsp';
var ukey = 'Enum';

var allDef = window.top.topFrame.allDef;
var guidMap;
var thisObj;
function load()
{
     preload();
     
     guidMap = {
         title: '配置项列表',
         url: gurl + 'query' + ukey,
         colModel : [
             {display: '选择', name : 'check', content : '<input type=radio name=checkb value={id} lvalue={value}>', width : 40, align: 'center'},
             {display: '名称', name : 'value', width : '30%'},
             {display: '类型', name : 'typeName', width : '30%'},
             {display: '状态', name : 'status', cc : 'enumStatus', width : 'auto'}
             ],
         extAtt: {
             //name : {begin : '<a href=' + gurl + 'find' + ukey + '&id={id}>', end : '</a>'}
         },
         buttons : [
             {id: 'add', bclass: 'add', onpress : addBean, auth: '0109'},
             {id: 'update', bclass: 'update', onpress : preUpdateBean, auth: '0109'},
             {id: 'del', bclass: 'del',  onpress : delBean, auth: '0109'},
             {id: 'search', bclass: 'search', onpress : doSearch}
             ],
         <p:conf/>
     };
     
     $("#mainTable").flexigrid(guidMap, thisObj);
     
     $('#dlg').dialog({
                //iconCls: 'icon-save',
                modal:true,
                closed:true,
                buttons:{
                    '确 定':function(){
                        updateBean();
                    },
                    '取 消':function(){
                        $('#dlg').dialog({closed:true});
                    }
                }
     });
     
     $ESC('dlg');
}
 
function $callBack()
{
    loadForm();
    
    highlights($("#mainTable").get(0), ['人工添加'], 'red');
    
    highlights($("#mainTable").get(0), ['系统初始'], 'blue');
    
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

function preUpdateBean()
{
	if (getRadio('checkb') && getRadioValue('checkb'))
	{	
		$O('configItemName').value = getRadio('checkb').lvalue;
		
		$('#dlg').dialog({closed:false});
	}
	else
	$error('不能操作');
}

function updateBean(opr, grid)
{
	var vv = $O('configItemName').value;
	
	if (vv == '')
	{
		alert('不能为空');
		return false;
	}
	
    if (getRadio('checkb') && getRadioValue('checkb'))
    {    
        $ajax2(gurl + 'update' + ukey + '&id=' + getRadioValue('checkb'), {value: vv}, callSucess);
    }
    else
    $error('不能操作');
}

function callSucess(data)
{
    $('#dlg').dialog({closed:true});
    
    callBackFun(data);
}

function doSearch()
{
    $modalQuery('../admin/query.do?method=popCommonQuery2&key=query' + ukey);
}

</script>
</head>
<body onload="load()" class="body_class">
<form name="mainForm" method="post">
<p:cache></p:cache>
</form>
<p:message></p:message>
<table id="mainTable" style="display: none"></table>
<div id="dlg" title="修改配置项" style="width:320px;">
    <div style="padding:20px;height:200px;" id="dia_inner" title="">
    配置项：<input type="text" id="configItemName" name="configItemName" />
   </div>
</div>
<p:query/>
</body>