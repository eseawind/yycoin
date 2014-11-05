<%@page contentType="text/html; charset=UTF-8"%>
<%@include file="../common/common.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<p:link title="发货管理" link="true" guid="true" cal="true" dialog="true" />
<script src="../js/common.js"></script>
<script src="../js/public.js"></script>
<script src="../js/pop.js"></script>
<script src="../js/json.js"></script>
<script src="../js/plugin/highlight/jquery.highlight.js"></script>

<script type="text/javascript">

var gurl = '../sail/ship.do?method=';
var addUrl = '../sail/ship.do?method=';
var ukey = 'Package';

var allDef = window.top.topFrame.allDef;
var guidMap;
var thisObj;
function load()
{
     preload();
     
     guidMap = {
         title: '出库单列表',
         url: gurl + 'query' + ukey,
         colModel : [
             {display: '<input type=checkbox id=flexi_Check onclick=checkAll(this)>选择', name : 'check', content : '<input type=checkbox name=checkb value={id} lstatus={status}>', width : 40, sortable : false, align: 'center'},             
             {display: '出库单', name : 'id', width : '15%'},
             {display: '商品种类', name : 'productCount', width : '6%'},
             {display: '发货数量', name : 'amount',  width : '6%'},
             {display: '收货人', name : 'receiver',  width : '10%'},
             {display: '收货电话', name : 'mobile',  width : '10%'},
             {display: '发货方式', name : 'shipping', cc : 'outShipment', width : '10%'},
             {display: '发货公司', name : 'transportName1', content : '{transportName1}/{transportName2}',  width : '10%'},
             {display: '支付方式', name : 'pay', width : '10%'},
             {display: '承担人', name : 'stafferName', width : '6%'},
             {display: '仓库地点', name : 'locationName', width : '10%'},
             {display: '状态', name : 'status', cc: 'shipStatus',  width : '5%'},
             {display: '时间', name : 'logTime',  width : 'auto', sortable : true}
             ],
         extAtt: {
             id : {begin : '<a href=' + gurl + 'findPackage&packageId={id}>', end : '</a>'}
         },
         buttons : [
             {id: 'add', bclass: 'add', caption : '拣配', onpress : addBean},
             {id: 'del', bclass: 'del', caption : '撤销', onpress : undoBean},
             {id: 'search', bclass: 'search', onpress : doSearch}
             ],
        <p:conf/>
     };
     
     $("#mainTable").flexigrid(guidMap, thisObj);
}
 
function $callBack()
{
    loadForm();
    //highlights($("#mainTable").get(0), ['驳回'], 'red');
}

function addBean(opr, grid)
{
    var clis = getCheckBox('checkb');
    
    if (clis.length > 0)
    {
        var str = '';
        for (var i = 0; i < clis.length; i++)
        {
            str += clis[i].value + '~';
        }
        
        if (window.confirm('确定拣配选中的发货单?'))
        {
            $ajax('../sail/ship.do?method=addPickup&packageIds=' + str, callBackFun);
        }
    }
}

function undoBean(opr, grid)
{
    var clis = getCheckBox('checkb');
    
    if (clis.length > 0)
    {
        var str = '';
        for (var i = 0; i < clis.length; i++)
        {
            str += clis[i].value + '~';
        }
        
        if (window.confirm('确定撤销?'))
        {
            $ajax(gurl + 'delete' + ukey + '&packageIds=' + str, callBackFun);
        }
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
<p:query/>
</body>