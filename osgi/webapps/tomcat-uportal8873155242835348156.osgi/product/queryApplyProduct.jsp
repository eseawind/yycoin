<%@page contentType="text/html; charset=UTF-8"%>
<%@include file="../common/common.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<p:link title="产品管理" link="true" guid="true" cal="true" dialog="true" />
<script src="../js/common.js"></script>
<script src="../js/public.js"></script>
<script src="../js/pop.js"></script>
<script src="../js/plugin/highlight/jquery.highlight.js"></script>
<script type="text/javascript">

var gurl = '../product/product.do?method=';
var addUrl = '../product/addAbstractProduct.jsp';
var ukey = 'Product';

var allDef = window.top.topFrame.allDef;
var guidMap;
var thisObj;
function load()
{
     preload();
     
     guidMap = {
         title: '产品列表',
         url: gurl + 'queryApply' + ukey,
         colModel : [
             {display: '选择', name : 'check', content : '<input type=radio name=checkb value={id}>', width : 40, align: 'center'},
             {display: '名称', name : 'name', width : '15%'},
             {display: '编码', name : 'code', width : '10%'},
             {display: '状态', name : 'status', cc : 'productStatus', width : '8%'},
             {display: '类型', name : 'type', cc : 'productType', width : '8%'},
             {display: '虚拟', name : 'abstractType', cc : 'productAbstractType', width : '8%'},
             {display: '库存模型', name : 'stockType', cc : 'productStockType', width : '10%'},
             {display: '时间', name : 'logTime', sortable : true, width : 'auto'}
             ],
         extAtt: {
             name : {begin : '<a href=' + gurl + 'find' + ukey + '&id={id}>', end : '</a>'}
         },
         buttons : [
             {id: 'add', bclass: 'add', caption: '申请虚拟产品', onpress : addBean, auth: 'true'},
             {id: 'del', bclass: 'del',  onpress : delBean, auth: 'true'},
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
                        updateLocation();
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
    
    highlights($("#mainTable").get(0), ['不启用库存模型', '虚拟产品'], 'red');
    
}

function addBean(opr, grid)
{
    //$l(gurl + 'preForAdd' + ukey);
    $l(addUrl);
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

function configBean()
{
	if (getRadio('checkb') && getRadioValue('checkb'))
    {
       $ajax('../product/product.do?method=preForConfigProductVSLocation&id=' + getRadioValue('checkb'), callBackFunLocation);
    }
    else
    {
        $error();
    }
}

function callBackFunLocation(data)
{
    $O('dia_inner').innerHTML = '';
    
    var logs = data.msg;
    
    var htm = '';
    
    for(var i = 0; i < logs.length; i++)
    {
        var item = logs[i];
        
        var ck = '';
        
        if (item.code == '1')
        {
        	ck = 'checked=true'
        }
        
        var llog = '<input type=checkbox ' + ck + ' name=locationCheck value=' + item.id + '> ' + item.name + '<br>';
        
        htm += llog;
    }
    $O('dia_inner').innerHTML = htm;
    
    $('#dlg').dialog({closed:false});
}

function callSucess(data)
{
    $('#dlg').dialog({closed:true});
    
    callBackFun(data);
}

function doSearch()
{
    $modalQuery('../admin/query.do?method=popCommonQuery2&key=queryApply' + ukey);
}

</script>
</head>
<body onload="load()" class="body_class">
<form name="mainForm" method="post">
<p:cache></p:cache>
</form>
<p:message></p:message>
<table id="mainTable" style="display: none"></table>
<div id="dlg" title="选择销售范围" style="width:320px;">
    <div style="padding:20px;height:200px;" id="dia_inner" title="">
   </div>
</div>
<p:query/>
</body>