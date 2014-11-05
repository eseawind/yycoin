<%@page contentType="text/html; charset=UTF-8"%>
<%@include file="../common/common.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<p:link title="合同管理" link="true" guid="true" cal="true" dialog="true" />
<script src="../js/common.js"></script>
<script src="../js/public.js"></script>
<script src="../js/JCheck.js"></script>
<script src="../js/pop.js"></script>
<script src="../js/plugin/highlight/jquery.highlight.js"></script>
<script type="text/javascript">

var gurl = '../project/project.do?method=';
var addUrl = '../project/addAgreement.jsp';
var ukey = 'Agreement';

var allDef = window.top.topFrame.allDef;
var guidMap;
var thisObj;
function load()
{
     preload();
     guidMap = {
         title: '合同管理列表',
         url: gurl + 'query' + ukey + '&src=0',
         colModel : [
             {display: '选择', name : 'check', content : '<input type=radio name=checkb value={id} lbatchPrice={batchPrice} lsailPrice={sailPrice} lcost={cost}>', width : 40, align: 'center'},
             {display: '单据ID', name : 'id', width : '14%'},
             {display: '名称', name : 'agreementName', width : '10%'},
             {display: '线下编码', name : 'lineCode', width : '10%'},
             {display: '签约日期', name : 'signDate', width : '10%'},
             {display: '合同金额', name : 'agreementMoney',  width : '7%'},
             {display: '合同类型', name : 'agreementType', cc : '205',  width : '10%'},
             {display: '合同状态', name : 'agreementStatus',cc : 'agreementStatus', width : '10%'},
             {display: '合同阶段', name : 'agreementStage',cc : 'agreementStage', width : '10%'},
             {display: '创建人', name : 'appName',  width : '10%'},
             {display: '完成进度', name : 'finishiProcess', width : 'auto'}
             ],
         extAtt: {
    	 id : {begin : '<a href=' + gurl + 'find' + ukey + '&id={id}>', end : '</a>'}
         },
         buttons : [
             {id: 'add', bclass: 'add',caption : '合同创建', onpress : addBean},
             {id: 'update', bclass: 'update', onpress : updateBean},
             {id: 'del', bclass: 'del',  onpress : delBean},
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

<p:query/>
</body>