<%@page contentType="text/html; charset=UTF-8"%>
<%@include file="../common/common.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<p:link title="产品体管理" link="true" guid="true" cal="true" dialog="true" />
<script src="../js/common.js"></script>
<script src="../js/public.js"></script>
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
             {display: '选择', name : 'check', content : '<input type=radio name=checkb value={id}>', width : 40, align: 'center'},
             {display: '名称', name : 'name', width : '15%'},
             {display: '编码', name : 'code', width : '10%'},
             {display: '数量', name : 'amount', width : '10%', toFixed: 2},
             {display: '状态', name : 'status', cc : 'productStatus', width : '5%'},
             {display: '类型', name : 'type', cc : 'productType', width : '5%'},
             {display: '虚拟', name : 'abstractType', cc : 'productAbstractType', width : '5%'},
             {display: '库存模型', name : 'stockType', cc : 'productStockType', width : '5%'},
             {display: '时间', name : 'logTime', content: '{logTime}',cname: 'logTime',  sortable : true, width : 'auto'}
             ],
         extAtt: {
             name : {begin : '<a href=' + gurl + 'find' + ukey + '&id={id}>', end : '</a>'}
         },
         buttons : [
             {id: 'add', bclass: 'add', onpress : addBean, auth: '1003'},
             {id: 'update', bclass: 'update', onpress : updateBean, auth: '1003'},
             {id: 'del', bclass: 'del',  onpress : delBean, auth: '1003'},
             {id: 'search', bclass: 'search', onpress : doSearch}
             ],
         <p:conf/>
     };
     
     $("#mainTable").flexigrid(guidMap, thisObj);
}
 
function $callBack()
{
    loadForm();
    
    highlights($("#mainTable").get(0), ['不启用库存模型'], 'red');
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

function doSearch()
{
    $modalQuery('../admin/query.do?method=popCommonQuery2&key=query' + ukey);
}

function exports2()
{
    if (getRadio('checkb') && getRadioValue('checkb'))
    {
        $.messager.prompt('异动日期', '截至异动日期(必须2011-05-22以后)', '', function(value, opr){
                    if (opr)
                    {
                        var sss = value;
                        
                        if (!(sss == null || sss == '') && sss >= '2011-05-22')
                        {
                            document.location.href = '../finance/bank.do?method=exportStatBank&bankId=' + getRadioValue('checkb') + '&timekey=' + sss;
                        }
                        else
                        {
                            alert('请选择异动日期,或者异动日期必须2011-05-22以后');
                        }
                    }
                }, 1);
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
<p:query/>
</body>