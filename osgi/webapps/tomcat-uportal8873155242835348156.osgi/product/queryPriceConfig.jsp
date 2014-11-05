<%@page contentType="text/html; charset=UTF-8"%>
<%@include file="../common/common.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<p:link title="产品结算价配置" link="true" guid="true" cal="true" dialog="true" />
<script src="../js/common.js"></script>
<script src="../js/public.js"></script>
<script src="../js/JCheck.js"></script>
<script src="../js/pop.js"></script>
<script src="../js/plugin/highlight/jquery.highlight.js"></script>
<script type="text/javascript">

var gurl = '../product/priceConfig.do?method=';
var addUrl = '../product/priceConfig.do?method=';
var ukey = 'PriceConfig';

var allDef = window.top.topFrame.allDef;
var guidMap;
var thisObj;
function load()
{
     preload();
     
     guidMap = {
         title: '产品价格配置列表',
         url: gurl + 'query' + ukey  ,
         colModel : [
             {display: '选择', name : 'check', content : '<input type=radio name=checkb value={id} ltype={type}>', width : 40, align: 'center'},
             {display: '产品', name : 'productName', width : '15%'},
             {display: '类型', name : 'type', width : '8%', cc : 'priceConfigType'},
             {display: '事业部', name : 'industryName',  width : '15%'},
             {display: '金价', name : 'gold',width : '8%' ,toFixed : 2},
             {display: '银价', name : 'silver',width : '8%' ,toFixed : 2},
             {display: '邮票总价', name : 'price',width : '8%' ,toFixed : 2},
             {display: '辅料费用', name : 'gsPriceUp', width : '8%', toFixed : 2},
             {display: '结算价', name : 'sailPrice',width : '8%' ,toFixed : 2},
             {display: '建议销售价', name : 'minPrice', width : 'auto', toFixed : 2}
             ],
         extAtt: {
    	 	productName : {begin : '<a href=' + gurl + 'find' + ukey + '&id={id}>', end : '</a>'}
         },
         buttons : [
             {id: 'add', bclass: 'add', caption : '结算价配置',onpress : addBean},
             {id: 'add2', bclass: 'add', caption : '销售价配置', onpress : addBean2},
             {id: 'update', bclass: 'update', onpress : updateBean},
             {id: 'del', bclass: 'del',  onpress : delBean},
             {id: 'search', bclass: 'search', onpress : doSearch}
             ],         
         <p:conf/>
     };
     
     $("#mainTable").flexigrid(guidMap, thisObj);
     
}
 
function $callBack()
{
    loadForm();
    
    //highlights($("#mainTable").get(0), ['无效'], 'red');
    //highlights($("#mainTable").get(0), ['有效'], 'blue');
    
}

function addBean(opr, grid)
{
    $l(gurl + 'preForAdd' + ukey + '&type=1');
    //$l(addUrl);
}

function addBean2(opr, grid)
{
    $l(gurl + 'preForAdd' + ukey + '&type=0');
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

</script>
</head>
<body onload="load()" class="body_class">
<form name="mainForm" method="post">
<p:cache></p:cache>
</form>
<p:message></p:message>
<table id="mainTable" style="display: none"></table>
<p:query height="40px"/>

<p:query />
</body>