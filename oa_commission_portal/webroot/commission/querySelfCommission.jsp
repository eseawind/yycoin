<%@page contentType="text/html; charset=UTF-8"%>
<%@include file="../common/common.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<p:link title="提成管理" link="true" guid="true" cal="true" dialog="true" />
<script src="../js/common.js"></script>
<script src="../js/public.js"></script>
<script src="../js/pop.js"></script>
<script src="../js/plugin/highlight/jquery.highlight.js"></script>
<script src="../js/jquery.blockUI.js"></script>
<script type="text/javascript">

var gurl = '../commission/commission.do?method=';
var addUrl = '../commission/addCommission.jsp';
var ukey = 'Commission';

var allDef = getAllDef();
var guidMap;
var thisObj;
function load()
{
     preload();
     
     guidMap = {
         title: '提成计算列表',
         url: gurl + 'querySelf' + ukey,
         colModel : [
             {display: '选择', name : 'check', content : '<input type=radio name=checkb value={id}>', width : 40, align: 'center'},
             {display: '月份', name : 'month', width : '5%'},
             {display: '职员', name : 'stafferName', width : '5%'},
             {display: '应发提成', name : 'shouldCommission',  width : '8%', toFixed : 2},
             {display: '冻结前提成', name : 'realCommission', width : '8%', toFixed : 2},
             {display: '冻结后提成', name : 'finalCommission', width : '8%', toFixed : 2},
             {display: '到款毛利', name : 'profit',  width : '8%', toFixed : 2},
             {display: '费用', name : 'fee',  width : '8%', toFixed : 2},             
             {display: '坏账', name : 'baddebt',  width : '8%', toFixed : 2},
             {display: '报废', name : 'broken',  width : '8%', toFixed : 2},
             {display: '资金占用费', name : 'financeFee',  width : '8%', toFixed : 2},
             {display: '上月未扣', name : 'lastDeduction',  width : '8%', toFixed : 2},
             {display: '转下月扣款', name : 'turnNextMonthDeduction',  width : '8%', toFixed : 2},
             {display: '应收成本', name : 'receiveCost',  width : '8%', toFixed : 2},
             {display: 'KPI扣款', name : 'kpiDeduction',  width : '8%', toFixed : 2},             
             {display: '年度KPI返款', name : 'yearKpiMoney',  width : 'auto', toFixed : 2}
             ],
         extAtt: {
    	 	realCommission : {begin : '<a title=点击查看明细  href=' + gurl + 'findCommission&id={id}>', end : '</a>'},
    	 	finalCommission : {begin : '<a title=点击查看明细  href=' + gurl + 'findCommission0&id={id}>', end : '</a>'},
    	 	profit : {begin : '<a title=点击查看明细 href=' + gurl + 'findCommission2&id={id}&firstLoad=1>', end : '</a>'},
    	 	fee : {begin : '<a title=点击查看明细 href=' + gurl + 'findCommission3&id={id}&firstLoad=1>', end : '</a>'},    	 	
    	 	receiveCost : {begin : '<a title=点击查看明细 href=' + gurl + 'findCommission4&id={id}&firstLoad=1>', end : '</a>'},
    	 	kpiDeduction : {begin : '<a title=点击查看明细 href=' + gurl + 'findCommission4&id={id}&firstLoad=1>', end : '</a>'},
    	 	yearKpiMoney : {begin : '<a title=点击查看明细 href=' + gurl + 'findCommission4&id={id}&firstLoad=1>', end : '</a>'},
    	 	broken : {begin : '<a title=点击查看明细 href=' + gurl + 'findCommission5&id={id}&firstLoad=1>', end : '</a>'},
    	 	financeFee : {begin : '<a title=点击查看明细 href=' + gurl + 'findCommission6&id={id}&firstLoad=1>', end : '</a>'}
         },
         buttons : [
             {id: 'search', bclass: 'search', onpress : doSearch}
             ],
        <p:conf/>
     };
     
     $("#mainTable").flexigrid(guidMap, thisObj);
}
 
function $callBack()
{
    loadForm();
}

function doSearch()
{
    $modalQuery('../admin/query.do?method=popCommonQuery2&key=querySelf' + ukey);
}

</script>
</head>
<body onload="load()" class="body_class">
<form name="mainForm" method="post">
<p:cache></p:cache>
</form>
<p:message/>
<table id="mainTable" style="display: none"></table>
<p:query/>

</body>