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

/**
 * 比较时间
 */
function compareDays1(date1, date2)
{
    var s1 = date1.split('-');
    var s2 = date2.split('-');

    var year1 = parseInt(s1[0], 10);

    var year2 = parseInt(s2[0], 10);

    var month1 = parseInt(s1[1], 10);

    var month2 = parseInt(s2[1], 10);

    var day1 = parseInt(s1[2], 10);

    var day2 = parseInt(s2[2], 10);

    return (year2 - year1) * 365 + (month2 - month1) * 30 + (day2 - day1);
}

var gurl = '../customerService/feedback.do?method=';
var addUrl = '';
var ukey = 'FeedBack';

var mode = '<p:value key="mode"/>';

var allDef = getAllDef();
var guidMap;
var thisObj;
function load()
{
     preload();

     guidMap = {
         title: '任务列表',
         url: gurl + 'query' + ukey + '&mode=' + mode,
         colModel : [
		   {display: '<input type=checkbox id=flexi_Check onclick=checkAll(this)>选择', name : 'check', content : '<input type=checkbox name=checkb value={id} ltype={type} lstatus={status} lnow={now} lforecastDate={forecastDate}>', width : 40, sortable : false, align: 'center'},
		   {display: '任务', name : 'id', width : '10%'},
		   {display: '客户', name : 'customerId', content:'{customerName}', width : '20%', sortable : true},
		   //{display: '客户信用', name : 'custCredit', width : '5%', toFixed: 2},
           {display: '职员', name : 'stafferId', content:'{stafferName}', width : '6%', sortable : true},
           //{display: '职员信用', name : 'staffCredit', width : '5%', toFixed: 2},
           {display: '销售单数量', name : 'outCount',  width : '6%'},
           {display: '商品数量', name : 'productCount',  width : '5%'},
           {display: '销售金额', name : 'moneys',  width : '6%', toFixed: 2},
           {display: '已付金额', name : 'hadpay',  width : '6%', toFixed: 2},
           {display: '未付款金额', name : 'noPayMoneys',  width : '6%', toFixed: 2},
           {display: '客户预付', name : 'preMoney',  width : '6%', toFixed: 2},
           {display: '类型', name : 'type',  width : '5%', cc: 'feedbackType'},
           {display: '状态', name : 'status',  width : '5%', cc: 'feedbackStatus'},
           {display: '处理状态', name : 'pstatus', cc: 'feedbackPstatus',  width : '5%'},
           {display: '异常回访日', name : 'forecastDate',  width : '8%'},
           {display: '事业部', name : 'industryIdName',  width : '6%'},
           {display: '统计时间', name : 'logTime',  width : '8%'},
           {display: '负责人', name : 'bearName',  width : 'auto'}
             ],
         extAtt: {
    	 	//id : {begin : '<a href=../customerService/feedback.do?method=findFeedBack&id={id}>', end : '</a>'}
         },
         buttons : [
			 <c:if test="${mode == 0}">
			 {id: 'add', caption: '任务分配', bclass: 'add', onpress : addBean},
			 {id: 'export', bclass: 'replied',  caption: '导出回访明细', onpress : exports},
			 {id: 'export2', bclass: 'replied',  caption: '导出对账明细', onpress : exports2},
			 </c:if>
			 <c:if test="${mode == 1}">
			 {id: 'add1', caption: '任务回应', bclass: 'add', onpress : addBean1},
			 {id: 'add2', caption: '任务处理', bclass: 'update', onpress : addBean2},
			 </c:if>
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

    highlights($("#mainTable").get(0), ['已完成'], 'blue');

    highlights($("#mainTable").get(0), ['保存','提交','传真已发','传真已收'], 'red');
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
			if (clis[i].lstatus != 1)
				continue;
            
            str += clis[i].value + '~';
        }

		if (str.length == 0)
		{
			alert('未选择待分配的任务,不可操作');

			return;
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
            if (clis[i].lstatus != 2)
				continue;
                
            str += clis[i].value + '~';
        }

		if (str.length == 0)
		{
			alert('未选择待接收的任务,不可操作');

			return;
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
		// 异常回访有时间要求
		if (clis[0].ltype == 4 && clis[0].lstatus == 3)
		{
			if (compareDays1(clis[0].lnow, clis[0].lforecastDate) > 0)
			{
				//alert('该异常回访时间未到，日期：' + clis[0].lforecastDate);
				//return;
			}
		}
        
        // 跳转 preForAddFeedBackVisit
        if ((clis[0].ltype == 1 || clis[0].ltype == 4) && clis[0].lstatus == 3)
        	$l(gurl + 'preForAdd' + ukey + 'Visit' + '&taskId=' + clis[0].value );
        else if ((clis[0].ltype == 2 || clis[0].ltype == 5) && clis[0].lstatus == 3)
        	$l(gurl + 'preForAdd' + ukey + 'Check' + '&taskId=' + clis[0].value);
        else
        	$error('不能操作，可能是类型与状态不对')
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

function exports()
{
    document.location.href = gurl + 'exportFeedBackVisit';
}

function exports2()
{
    document.location.href = gurl + 'exportFeedBackCheck';
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