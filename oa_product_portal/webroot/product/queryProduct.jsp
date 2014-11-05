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

//格式化数字 四舍五入
function formatNum(num, length)
{
     var reg = /[0-9]*(.)?[0-9]*$/;

     if (!reg.test(num))
     {
        reg = /[0-9]*.$/;
        if (!reg.test(num))
        {
            return num;
        }
     }

     num += '';
     
     if (num.indexOf('.') == -1)
     {
        return num + '.' + getLength0(length);
     }

     var hou = num.substring(num.indexOf('.') + 1);

     if (hou.length <= length)
     {
        return num + getLength0(length - hou.length);
     }

     //超过 指定的四舍五入
     var ins = num.substring(0, num.indexOf('.') + 1) + hou.substring(0, length);

     var last = parseInt(hou.charAt(length));
     
     var add = false;

     if (last >= 5)
     {
        add = true;
     }
     else
     {
        add = false;
     }
     
     if (add)
     {
        var goAdd = true;
        
        for (var i = ins.length - 1; i >= 0; i--)
        {
            if (ins.charAt(i) == '.')
            {
                continue;
            }
            
            if (goAdd && parseInt(ins.charAt(i)) == 9)
            {
                goAdd = true;
                
                ins = ins.substring(0, i) + '0' + ins.substring(i + 1);
            }
            else
            {
                ins = ins.substring(0, i) + (parseInt(ins.charAt(i)) + 1) + ins.substring(i + 1);
                
                goAdd = false;
                
                break;
            }
        }
        
        if (goAdd && parseInt(ins.charAt(0)) == 0)
        {
            ins = '1' + ins;
        }
     }

     var sresult = ins + '';
     
     if (sresult.indexOf('.') == -1)
     {
        return sresult;
     }
     
     if (sresult.indexOf('.') != -1)
     {
         sresult = sresult + '0000000000000000';           
     }
     
     return sresult.substring(0, sresult.indexOf('.') + length + 1);
}

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
         url: gurl + 'query' + ukey + '&src=0',
         colModel : [
             {display: '选择', name : 'check', content : '<input type=radio name=checkb value={id} lbatchPrice={batchPrice} lsailPrice={sailPrice} lcost={cost}>', width : 40, align: 'center'},
             {display: '名称', name : 'name', width : '25%'},
             {display: '编码', name : 'code', width : '10%'},
             {display: '状态', name : 'status', cc : 'productStatus', width : '8%'},
             {display: '类型', name : 'type', cc : 'productType', width : '8%'},
             {display: '虚拟', name : 'abstractType', cc : 'productAbstractType', width : '8%'},
             {display: '合成', name : 'ctype', cc : 'productCtype', width : '8%'},
             {display: '管理', name : 'reserve4', cc : 'pubManagerType', width : '8%'},
             {display: '阶段', name : 'reserve5', cc : 'productStep', width : '8%'},
             //{display: '库存模型', name : 'stockType', cc : 'productStockType', width : '10%'},
             {display: '结算价', name : 'sailPrice', toFixed: 2, width : 'auto'}
             ],
         extAtt: {
             name : {begin : '<a href=' + gurl + 'find' + ukey + '&id={id}>', end : '</a>'}
         },
         buttons : [
             {id: 'add', bclass: 'add', onpress : addBean, auth: 'A1003'},
             {id: 'update', bclass: 'update', onpress : updateBean, auth: '1003'},
             {id: 'update1', bclass: 'update', caption : '配置销售范围', onpress : configBean, auth: '1003'},
             //{id: 'update2', bclass: 'update', caption : '批发/结算价', onpress : configPrice, auth: '1003'},
             {id: 'update3', bclass: 'update', caption : '金/银价', onpress : configGoldSilverPrice, auth: '1003'},
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

     $('#dlg2').dialog({
         modal:true,
         closed:true,
         buttons:{
             '确 定':function(){
                 updateGoldSilverPrice();
             },
             '取 消':function(){
                 $('#dlg2').dialog({closed:true});
             }
         }
});
     
     $ESC('dlg');
     $ESC('dlg1');
     $ESC('dlg2');
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
    
    var htm = '<input type=checkbox name=checkAll onclick=call(this)>全选<br>';
    
    for(var i = 0; i < logs.length; i++)
    {
        var item = logs[i];
        
        var ck = '';
        
        if (item.level == '1')
        {
        	ck = 'checked=true'
        }
        
        var llog = '<input type=checkbox ' + ck + ' name=locationCheck value=' + item.id + '> ' + item.parentId + '-->' + item.name + '<br>';
        
        htm += llog;
    }
    
    $O('dia_inner').innerHTML = htm;
    
    $('#dlg').dialog({closed:false});
}

function call(obj)
{
	var list = $N('locationCheck');
	
	for(var i = 0; i < list.length; i++)
	{
	    list[i].checked = obj.checked;
	}
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

function configGoldSilverPrice()
{
	$ajax('../product/product.do?method=preForConfigGoldSilverPrice', callBackGoldSilver);
}

function callBackGoldSilver(data)
{
    var item = data.msg;

    $O('goldPrice').value = formatNum(item.gold, 2);

    $O('silverPrice').value = formatNum(item.silver, 2);
    
    $('#dlg2').dialog({closed:false});
}

function updateGoldSilverPrice()
{
	$ajax('../product/product.do?method=configGoldSilverPrice&goldPrice=' + $$('goldPrice') + '&silverPrice='+ $$('silverPrice'), callSucess3);
}

function callSucess3(data)
{
    $('#dlg2').dialog({closed:true});
    
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
<div id="dlg" title="选择销售范围" style="width:320px;height:400px">
    <div style="padding:20px;height:200px;" id="dia_inner" title="">
   </div>
</div>

<div id="dlg1" title="调整批发价和结算价" style="width:320px;">
    <div style="padding:20px;height:200px;" id="dia_inner1" title="">
    成本价：<input type="text" name="cost" readonly="readonly" disabled="disabled"/><br>
    批发价：<input type="text" name="batchPrice" oncheck="notNone;isFloat"/><br>
    结算价：<input type="text" name="sailPrice" oncheck="notNone;isFloat"/>
   </div>
</div>

<div id="dlg2" title="金银价" style="width:320px;">
    <div style="padding:20px;height:200px;" id="dia_inner2" title="">
    金价：<input type="text" name="goldPrice" oncheck="notNone;isFloat"/><br>
    银价：<input type="text" name="silverPrice" oncheck="notNone;isFloat"/>
   </div>
</div>

<p:query/>
</body>