<%@page contentType="text/html; charset=UTF-8"%>
<%@include file="../common/common.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<p:link title="回访对账任务管理" link="true" guid="true" cal="true" dialog="true" />
<script src="../js/common.js"></script>
<script src="../js/public.js"></script>
<script src="../js/pop.js"></script>
<script src="../js/plugin/highlight/jquery.highlight.js"></script>
<script src="../js/jquery.blockUI.js"></script>
<script type="text/javascript">

var gurl = '../customerService/feedback.do?method=';
var addUrl = '../feedback/addBlack.jsp';
var ukey = 'FeedBack';

var allDef = getAllDef();
var guidMap;
var thisObj;
function load()
{
     preload();
     
     guidMap = {
         title: '任务列表',
         url: gurl + 'query' + ukey,
         colModel : [
		   {display: '<input type=checkbox id=flexi_Check onclick=checkAll(this)>选择', name : 'check', content : '<input type=checkbox name=checkb value={id} ltype={type} lstatus={status}>', width : 40, sortable : false, align: 'center'},
		   {display: '任务', name : 'id', width : '8%'},
		   {display: '客户', name : 'customerName', width : '20%'},
		   {display: '客户信用', name : 'custCredit', width : '5%', toFixed: 2},
           {display: '职员', name : 'stafferName', width : '8%'},
           {display: '客户信用', name : 'staffCredit', width : '5%', toFixed: 2},
           {display: '销售单数量', name : 'outCount',  width : '8%'},
           {display: '商品数量', name : 'productCount',  width : '5%'},
           {display: '销售金额', name : 'moneys',  width : '6%', toFixed: 2},
           {display: '未付款金额', name : 'noPayMoneys',  width : '6%'},
           {display: '客户预付', name : 'preMoney',  width : '6%', toFixed: 2},
           {display: '类型', name : 'type',  width : '5%', cc: 'feedbackType'},
           {display: '状态', name : 'status',  width : '5%', cc: 'feedbackStatus'},
           {display: '负责人', name : 'bear',  width : 'auto'}
             ],
         extAtt: {
         },
         buttons : [
			 {id: 'add', caption: '任务分配', bclass: 'add', onpress : addBean},
			 {id: 'add1', caption: '任务回应', bclass: 'add', onpress : addBean1},
			 {id: 'add2', caption: '任务处理', bclass: 'update', onpress : addBean2},
             {id: 'search', bclass: 'search', onpress : doSearch}
             ],
        <p:conf/>
     };
     
     $("#mainTable").flexigrid(guidMap, thisObj);

     $('#destStafferDIV').dialog({
         modal:true,
         closed:true,
         buttons:{
             '确 定':function(){
    	 		processFeedBack();
             },
             '取 消':function(){
                 $('#destStafferDIV').dialog({closed:true});
             }
         }
	});

     $ESC('destStafferDIV');
}
 
function $callBack()
{
    loadForm();

    //highlights($("#mainTable").get(0), ['废弃'], 'red');
}

function addBean()
{
	$('#destStafferDIV').dialog({closed:false});
}

function processFeedBack(opr, grid)
{
	$('#destStafferDIV').dialog({closed:true});
	
    var clis = getCheckBox('checkb');
    
    if (clis.length > 0)
    {
        var str = '';
        for (var i = 0; i < clis.length; i++)
        {
            str += clis[i].value + '~';
        }
        
        if (window.confirm('确定分配选中的任务?'))
        {
            $ajax(gurl + 'allocation' + ukey + '&destStafferId=' + $O('destStafferId').value + '&taskIds=' + str, callBackFun);
        }
    }
    else
    	$error('不能操作');
}

function addBean1()
{
	var clis = getCheckBox('checkb');
    
    if (clis.length > 0)
    {
        var str = '';
        for (var i = 0; i < clis.length; i++)
        {
            str += clis[i].value + '~';
        }
        
        if (window.confirm('确定接收选中的任务?'))
        {
            $ajax(gurl + 'accept' + ukey + '&taskIds=' + str, callBackFun);
        }
        else
        {
        	$ajax(gurl + 'reject' + ukey + '&taskIds=' + str, callBackFun);
        }
    }
    else
    	$error('不能操作');
}

function addBean2()
{
	var clis = getCheckBox('checkb');

	if (clis.length > 1)
	{
		alert('任务处理只能选择一个任务，请确认');
		return;
	}
    
    if (clis.length > 0)
    {
        var str = '';
        for (var i = 0; i < clis.length; i++)
        {
            str += clis[i].value + '~';
        }
        
        // 跳转 preForAddFeedBackVisit
        $l(gurl + 'preForAdd' + ukey + 'Visit' + '&mode=' + mode);
    }
    else
    	$error('不能操作');
}

function $selectStaffer()
{   
    window.common.modal('../admin/pop.do?method=rptQueryStaffer&load=1&selectMode=1');
}

function getStaffers(oos)
{
	 var oo = oos[0];

	 $O('destStafferName').value = oo.pname;
     $O('destStafferId').value = oo.value;
     
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
<p:message/>
<table id="mainTable" style="display: none"></table>
<div id="destStafferDIV" title="请选择业务员" style="width:320px;">
    <div style="padding:20px;" id="dia_inner" title="">
    <input type="text" name="destStafferName" readonly="readonly"/>
	<input type="hidden" name="destStafferId"/>&nbsp;&nbsp;
	<input type="button" value="&nbsp;...&nbsp;" name="qout" id="qout"
                            class="button_class" onclick="$selectStaffer(1)"/>&nbsp;&nbsp;
   </div>
</div>
<p:query/>

</body>