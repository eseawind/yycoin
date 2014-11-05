<%@page contentType="text/html; charset=UTF-8"%>
<%@include file="../common/common.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<p:link title="开票导入日志数据查询" link="true" guid="true" cal="true" dialog="true" />
<script src="../js/common.js"></script>
<script src="../js/public.js"></script>
<script src="../js/JChe ck.js"></script>
<script src="../js/pop.js"></script>
<script src="../js/plugin/highlight/jquery.highlight.js"></script>
<script src="../js/jquery.blockUI.js"></script>
<script type="text/javascript">

var gurl = '../finance/invoiceins.do?method=';
var ukey = 'InsImportLog';

var allDef = window.top.topFrame.allDef;
var guidMap;
var thisObj;
function load()
{
     preload();
     
     guidMap = {
         title: '开票导入日志数据',
         url: gurl + 'query' + ukey  ,
         colModel : [
             {display: '选择', name : 'check', content : '<input type=radio name=checkb value={id} vstatus={status} vbatchId={batchId} >', width : 40, align: 'center'},
             {display: '批次号', name : 'batchId', width : '15%'},
             {display: '状态', name : 'status', width : '20%', cc:'logStatus'},
             {display: '时间', name : 'logTime', width : 'auto'}
             ],
         extAtt: {
    	 	//batchId : {begin : '<a href=' + gurl + 'queryOutImport&batchId={batchId}>', end : '</a>'}
         },
         buttons : [
             {id: 'add', bclass: 'add', caption: '重试', onpress : addBean},
             //{id: 'update', bclass: 'update',caption: '立即预占库存', onpress : updateBean, auth:'9003'},
             //{id: 'del', bclass: 'del',  onpress : delBean},
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

function addBean()
{
    if (getRadio('checkb') && getRadioValue('checkb'))
    {    
		if (getRadio('checkb').vstatus == 0)
		{
			if (window.confirm('确定重试?这可能需要花点时间，请耐心等待!'))
		    {
		        $.blockUI({ message: $('#loadingDiv'),css:{width: '40%'}}, true);
		        
		        $ajax(gurl + 'processInsImport&batchId=' + getRadio('checkb').vbatchId, callBackFun);

		        $.unblockUI();
		    }
		}
		else
			$error('进行中或成功状态不能操作');
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
<div id="loadingDiv" style="display:none">
<p>&nbsp;</p>
<p>进在处理中......</p>
<p><img src="../images/oa/process.gif" /></p>
<p>&nbsp;</p>
</div>
<p:query height="20px"/>

<p:query />
</body>