<%@page contentType="text/html; charset=UTF-8"%>
<%@include file="../common/common.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<p:link title="产品管理" link="true" guid="true" cal="true" dialog="true" />
<script src="../js/common.js"></script>
<script src="../js/public.js"></script>
<script src="../js/JCheck.js"></script>
<script src="../js/pop.js"></script>
<script src="../js/plugin/highlight/jquery.highlight.js"></script>
<script type="text/javascript">

var gurl = '../product/product.do?method=';
var addUrl = '../product/addProduct.jsp';
var ukey = 'Product';

var allDef = window.top.topFrame.allDef;
var guidMap;
var thisObj;
function load()
{
     preload();
     
     guidMap = {
         title: '产品列表',
         url: gurl + 'query' + ukey,
         colModel : [
             {display: '选择', name : 'check', content : '<input type=radio name=checkb value={id} lbatchPrice={batchPrice} lsailPrice={sailPrice} lcost={cost}>', width : 40, align: 'center'},
             {display: '名称', name : 'name', width : '15%'},
             {display: '编码', name : 'code', width : '10%'},
             {display: '状态', name : 'status', cc : 'productStatus', width : '8%'},
             {display: '类型', name : 'type', cc : 'productType', width : '8%'},
             {display: '虚拟', name : 'abstractType', cc : 'productAbstractType', width : '8%'},
             //{display: '库存模型', name : 'stockType', cc : 'productStockType', width : '10%'},
             {display: '结算价', name : 'sailPrice', toFixed: 2, width : '10%'},
             {display: '时间', name : 'logTime', sortable : true, width : 'auto'}
             ],
         extAtt: {
             name : {begin : '<a href=' + gurl + 'find' + ukey + '&id={id}>', end : '</a>'}
         },
         buttons : [
             {id: 'search', bclass: 'search', onpress : doSearch}
             ],
         <p:conf/>
     };
     
     $("#mainTable").flexigrid(guidMap, thisObj);
     
     $('#dlg').dialog({
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
     
     $('#dlg1').dialog({
                modal:true,
                closed:true,
                buttons:{
                    '确 定':function(){
                        updatePrice();
                    },
                    '取 消':function(){
                        $('#dlg1').dialog({closed:true});
                    }
                }
     });
     
     $ESC('dlg');
     $ESC('dlg1');
}
 
function $callBack()
{
    loadForm();
    
    highlights($("#mainTable").get(0), ['虚拟产品'], 'red');
    highlights($("#mainTable").get(0), ['不启用库存模型'], 'blue');
    
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

function configPrice()
{
	if (getRadio('checkb') && getRadioValue('checkb'))
    {
        $O('batchPrice').value = getRadio('checkb').lbatchPrice;
        $O('sailPrice').value = getRadio('checkb').lsailPrice;
        $O('cost').value = getRadio('checkb').lcost;
        $('#dlg1').dialog({closed:false});
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

function updateLocation()
{
	var varry = getCheckBox('locationCheck');
	
	var ids = ''
	
	for (var i = 0; i < varry.length; i++)
	{
		ids = ids + varry[i].value + '~';
	}
    
    if (getRadio('checkb') && getRadioValue('checkb'))
    {
        $ajax('../product/product.do?method=configProductVSLocation&id=' + getRadioValue('checkb') + '&newLocationIds=' + ids, callSucess);
    }
}

function updatePrice()
{
	if(!eCheck([$O('batchPrice'), $O('sailPrice')]))
	{
		return false;
	}
    
    if (getRadio('checkb') && getRadioValue('checkb'))
    {
        $ajax('../product/product.do?method=configPrice&id=' 
        	+ getRadioValue('checkb') + '&batchPrice=' 
        	+ $$('batchPrice') + '&sailPrice=' + $$('sailPrice'), callSucess2);
    }
}

function callSucess(data)
{
    $('#dlg').dialog({closed:true});
    
    callBackFun(data);
}

function callSucess2(data)
{
    $('#dlg1').dialog({closed:true});
    
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
<div id="dlg" title="选择销售范围" style="width:320px;">
    <div style="padding:20px;height:200px;" id="dia_inner" title="">
   </div>
</div>

<div id="dlg1" title="调整批发价和零售价" style="width:320px;">
    <div style="padding:20px;height:200px;" id="dia_inner1" title="">
    成本价：<input type="text" name="cost" readonly="readonly" disabled="disabled"/><br>
    批发价：<input type="text" name="batchPrice" oncheck="notNone;isFloat"/><br>
    零售价：<input type="text" name="sailPrice" oncheck="notNone;isFloat"/>
   </div>
</div>
<p:query/>
</body>