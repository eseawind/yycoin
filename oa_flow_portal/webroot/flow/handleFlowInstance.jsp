<%@ page contentType="text/html;charset=UTF-8" language="java"
	errorPage="../common/error.jsp"%>
<%@include file="../common/common.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<p:link title="流程实例处理" cal="true" />
<link rel="stylesheet" href="../js/plugin/accordion/accordion.css" />
<script type="text/javascript" src="../js/jquery/jquery.js"></script>
<script src="../js/plugin/accordion/jquery.accordion.js"></script>
<script src="../js/jquery.blockUI.js"></script>
<script src="../js/common.js"></script>
<script src="../js/public.js"></script>
<script src="../js/JCheck.js"></script>

<script type="text/javascript">
jQuery().ready(function(){
	
	jQuery('#flowDiv').accordion({
		autoheight: true
	});
	
	var accordions = jQuery('#flowDiv');
	
	accordions.accordion("activate", ${currentIndex});
	
	<c:forEach items="${define.tokenVOs}" var="item" varStatus="vs">
	<c:if test="${vs.index > currentIndex}">
	$('#sub_div_${vs.index}').block({ message: null }); 
	</c:if>
	<c:if test="${vs.index < currentIndex}">
    setAllReadOnly2($O('sub_div_${vs.index}'));
    </c:if>
	</c:forEach>
});

function editTemplate(path) 
{
    if (window.ActiveXObject)
    {
        var openDocObj = new ActiveXObject("SharePoint.OpenDocuments.2");
    } 
    else
    {
        alert('请使用IE进行操作');
        return;
    }
    
    openDocObj.editDocument("${eurl}" + path); 
    
}

function viewTemplate(path) 
{
    if (window.ActiveXObject)
    {
        var openDocObj = new ActiveXObject("SharePoint.OpenDocuments.2");
    } 
    else
    {
        alert('请使用IE进行操作');
        return;
    }
    
    openDocObj.ViewDocument("${rurl}" + path); 
}

function save()
{
    $O('operation').value = 0;
    
    submit('确定保存流程实例?');
}

function comeSub(tokenid, index)
{
    var realIndex = '${realIndex}';
    
    if (index == realIndex)
    {
        document.location.href = '../flow/instance.do?method=comeIntoSub&update=1&tokenId=' + tokenid + '&id=${bean.id}';
    }
    else
    {
        document.location.href = '../flow/instance.do?method=comeIntoSub&update=0&tokenId=' + tokenid + '&id=${bean.id}';
    }
    
}

function submits()
{
    $O('operation').value = 1;
    
    if ($$('processId') == '')
    {
        alert('数据不完备,请重新刷新操作');
        
        return false;
    }
    
    submit('确定提交流程实例?');
}

var urlMap = 
{
"3" : "../admin/pop.do?method=rptQuerySuperiorStaffer&stafferId=${bean.stafferId}",
"2" : "../admin/pop.do?method=rptQueryStaffer&load=1&selectMode=1",
"1" : "../group/group.do?method=rptQueryStaffer2&selectMode=1",
"0" : "../group/group.do?method=rptQueryStaffer2&selectMode=1",
"999" : ""
};

var gorder = 0;

function selectNext(order, index, pid)
{
    gorder = order;
    window.common.modal(urlMap[index] + '&mode=' + index + '&pid=' + pid);
}

function getStaffers(oo)
{
    if (oo.length > 0)
    {
        var item = oo[0];
        $O("processer_" + gorder).value = item.pname;
        $O("processId").value = item.value;
    }
}

function pass()
{
    if (!checkIn())
    {
        return false;
    }
    
    if ($O('processer_${currentIndex}') && $$('processer_${currentIndex}') == '')
    {
        alert('请选择处理人');
        return false;
    }
    
    if ($$('processId') == '')
    {
        alert('数据不完备,请重新刷新操作');
        
        return false;
    }
    
    if (window.confirm('确定通过?'))
    $l(getURL('pass'));
}

function checkIn()
{
    if ($$('reason_${currentIndex}') == '')
    {
        alert('请输入审批意见');
        return false;
    }
    
    return true;
}

function getURL(opr)
{
    var url = '../flow/instance.do?method=handleFlowInstance&id=${instanceId}&reason=' 
        + $encode($$('reason_${currentIndex}')) + '&nextStafferId=' + $$('processId') + '&cmd=' + opr;
        
    return url;
}

function reject()
{
    if (!checkIn())
    {
        return false;
    }
    
    if (window.confirm('确定驳回?'))
    $l(getURL('reject'));
}

function rejectAll()
{
    if (!checkIn())
    {
        return false;
    }
    
    if (window.confirm('确定驳回到初始?'))
    $l(getURL('rejectAll'));
}

function rejectParent()
{
    if (!checkIn())
    {
        return false;
    }
    
    if (window.confirm('确定驳回到父流程的上一环节?'))
    $l(getURL('rejectParent'));
}

function exends()
{
    if (!checkIn())
    {
        return false;
    }
    
    if (window.confirm('确定异常结束'))
    $l(getURL('exception'));
}

function downLoadAtt()
{
    window.location.href = '../flow/instance.do?method=downFlowInstanceAttachment&id=${bean.id}';
}

</script>
</head>
<body class="body_class">
<form name="formEntry" action="../flow/instance.do?method=addFlowInstance" 
enctype="multipart/form-data"  method="post"><input type="hidden"
    name="id" value="${instanceId}"/> <input type="hidden"
    name="processId" value="${lastLogList[0].nextStafferId}"/> <input type="hidden"
    name="flowId" value="${flowId}"/> <input type="hidden"
    name="operation" value=""/> 
    
    
<table width="99%">
<tr><td align="left">
<c:if test="${hasOwen}"> 
<font color=blue><b>编辑模式</b></font>&nbsp;&nbsp;
当前处理人:${currentHandles}&nbsp;&nbsp;
</c:if>
</td></tr></table>    
    
    
<div class="basic" id="flowDiv">
<c:forEach items="${define.tokenVOs}" var="item" varStatus="vs">

<c:if test="${vs.first && !subFlow}">
<a><font color='${vs.index == realIndex ? "red" : ""}'>[${lastLogList[vs.index].stafferName}]${item.name}:</font></a>
<div id="sub_div_${vs.index}"><p:class
	value="com.china.center.oa.flow.bean.FlowInstanceBean" opr="1"/>
<table width='100% border=' 0' cellpadding='0' cellspacing='0'
	class='table1'>
	<tr>
		<td>
		<p:table cells="1">
			<p:cells celspan="1" title="流程定义">
		     ${define.name}
		    </p:cells>

			<p:pro field="title" innerString="size=60" />
			
			<p:pro field="liminal" innerString="size=60" />

			<p:cell title="附件">
                <span onclick="downLoadAtt()" style="cursor: pointer;" title="点击下载附件">${bean.fileName}</span>
            </p:cell>

			<p:pro field="description" innerString="cols=80 rows=3" />
			
			<p:cells celspan="1" title="模板编辑">
            <c:forEach items="${templates}" var="itemEach">
            <c:if test="${itemEach.readonly == 1}">
            ${itemEach.name}：&nbsp;&nbsp;<input class="button_class"
                    type="button" name="view_b" onclick=viewTemplate('${itemEach.path}') value="在线预览"/>&nbsp;&nbsp;
            </c:if>
            <c:if test="${itemEach.readonly == 0}">
            ${itemEach.name}：&nbsp;&nbsp;<input class="button_class"
                    type="button" name="view_b" onclick=editTemplate('${itemEach.path}') value="在线编辑"/>&nbsp;&nbsp;
            </c:if>
            &nbsp;&nbsp;
            </c:forEach>
            </p:cells>
            
            
            <p:cells celspan="1" title="处理人">
            <input type="text" name="processer_0" readonly="readonly" value="${lastLogList[vs.index].nextStafferName}" style="cursor: pointer;"/>&nbsp;
            <font color=red>*</font>
            <input type="button" value="&nbsp;...&nbsp;" name="qout" id="qout"
                    class="button_class" onclick="selectNext(0, ${item.pluginType}, '${item.handleVOs[0].processer}')">&nbsp;&nbsp;
            </p:cells>
            
            <c:if test="${vs.index >= currentIndex}">
            <p:cells celspan="0" align="right">
            <input class="button_class"
                    type="button" name="save_b" onclick=save() value="&nbsp;&nbsp;保 存&nbsp;&nbsp;"/>&nbsp;&nbsp;
            <input class="button_class"
                    type="button" name="save_s" onclick=submits() value="&nbsp;&nbsp;提 交&nbsp;&nbsp;"/>
            </p:cells>
            </c:if>
            
		</p:table>
		</td>
	</tr>
</table>
</div>

</c:if>

<c:if test="${!vs.first || subFlow}">

<c:if test="${item.type == 0}">
<a><font color='${vs.index == realIndex ? "red" : ""}'>[${lastLogList[vs.index].stafferName}]${item.name}:</font></a>
<div id="sub_div_${vs.index}">
<table width='100% border=' 0' cellpadding='0' cellspacing='0'
    class='table1'>
    <tr>
        <td>
        <p:table cells="1">
            <p:cells celspan="1" title="流程定义">
             ${define.name}
             </p:cells>
            
            <p:cells celspan="1" title="模板编辑">
            <c:forEach items="${templates}" var="itemEach">
            <c:if test="${itemEach.readonly == 1}">
            ${itemEach.name}：&nbsp;&nbsp;<input class="button_class"
                    type="button" name="view_b" onclick=viewTemplate('${itemEach.path}') value="在线预览"/>&nbsp;&nbsp;
            </c:if>
            <c:if test="${itemEach.readonly == 0}">
            ${itemEach.name}：&nbsp;&nbsp;<input class="button_class"
                    type="button" name="view_b" onclick=editTemplate('${itemEach.path}') value="在线编辑"/>&nbsp;&nbsp;
            </c:if>
            &nbsp;&nbsp;
            </c:forEach>
            </p:cells>
            
             <p:cells celspan="1" title="审批意见">
             <textarea cols=80 rows=3 name="reason_${vs.index}"><c:out value="${lastLogList[vs.index].opinion}"/></textarea>&nbsp;<font color=red>*</font>
             </p:cells>
            
            <c:if test="${(!vs.last && (item.operation.liminal < bean.liminal || item.operation.liminal == 0.0)) || subFlow}">
            <p:cells celspan="1" title="处理人">
            <input type="text"  name="processer_${vs.index}" readonly="readonly" value="${lastLogList[vs.index].nextStafferName}" style="cursor: pointer;"/>&nbsp;
            <font color=red>*</font>
            <input type="button" value="&nbsp;...&nbsp;" name="qout" id="qout"
                    class="button_class" onclick="selectNext(${vs.index}, ${item.pluginType}, '${item.handleVOs[0].processer}')">&nbsp;&nbsp;
            </p:cells>
            </c:if>
            
            <c:if test="${lastLogList[vs.index] != null}">
            <p:cells celspan="1" title="处理说明">
            [${my:get('instanceOper', lastLogList[vs.index].oprMode)}] ${lastLogList[vs.index].logTime}
            </p:cells>
            </c:if>
            
            <c:if test="${vs.index >= currentIndex}">
            <p:cells celspan="0" align="right">
            <input class="button_class"
                    type="button" name="pass_b" onclick=pass() value="&nbsp;&nbsp;通 过&nbsp;&nbsp;"/>&nbsp;&nbsp;
            <c:if test="${item.operation.reject == 1}">
            <input class="button_class"
                    type="button" name="reject_b" onclick=reject() value="&nbsp;&nbsp;驳回到上一环&nbsp;&nbsp;"/>&nbsp;&nbsp;
            </c:if>
            
            <c:if test="${item.operation.rejectAll == 1}">
            <input class="button_class"
                    type="button" name="rejectAll_b" onclick=rejectAll() value="&nbsp;&nbsp;驳回到初始&nbsp;&nbsp;"/>&nbsp;&nbsp;
            </c:if>
            
            <c:if test="${item.operation.rejectParent == 1}">
            <input class="button_class"
                    type="button" name="rejectAll_b" onclick=rejectParent() value="&nbsp;&nbsp;驳回到父流程的上一环节&nbsp;&nbsp;"/>&nbsp;&nbsp;
            </c:if>
            
            <c:if test="${item.operation.exends == 1}">
            <input class="button_class"
                    type="button" name="exends_b" onclick=exends() value="&nbsp;&nbsp;异常结束&nbsp;&nbsp;"/>&nbsp;&nbsp;
            </c:if>
            </p:cells>
            </c:if>
            
            
        </p:table>
        </td>
    </tr>
</table>
</div>
</c:if>

<c:if test="${item.type == 1}">
<a><font color='${vs.index == realIndex ? "red" : ""}'>[${lastLogList[vs.index].stafferName}]${item.name}:</font></a>
<div id="sub_div_${vs.index}">
<table width='100% border=' 0' cellpadding='0' cellspacing='0'
    class='table1'>
    <tr>
        <td>
        <p:table cells="1">
            <p:cells celspan="1" title="流程定义">
             ${define.name}
             </p:cells>
            
             <p:cells celspan="1" title="子流程">
             <span onclick="comeSub('${item.id}', '${vs.index}')" style="cursor: pointer;">进入子流程</span>
             &nbsp;&nbsp;
             </p:cells>
          
            <c:if test="${vs.index < currentIndex && lastLogList[vs.index] != null}">
            <p:cells celspan="0" align="right">
            <b>签名：${lastLogList[vs.index].stafferName}&nbsp;&nbsp;
                 时间：${lastLogList[vs.index].logTime}&nbsp;&nbsp;
                 操作：${my:get('instanceOper', lastLogList[vs.index].oprMode)}</b>
            </p:cells>
            </c:if>
            
        </p:table>
        </td>
    </tr>
</table>
</div>
</c:if>

</c:if>
</c:forEach>

<c:if test="${define.mode == 1}">
<a>模板查看:</a>
<div>
<p>&nbsp;</p>
<c:forEach items="${readonlyTemplates}" var="itemEach">
<c:if test="${itemEach.readonly == 1}">
${itemEach.name}：&nbsp;&nbsp;<input class="button_class"
        type="button" name="view_bxx" onclick=viewTemplate('${itemEach.path}') value="在线预览">&nbsp;&nbsp;
</c:if>
&nbsp;&nbsp;
</c:forEach>
<p>&nbsp;</p>
</div>
</c:if>

<a>流程日志:</a>
<div>
<table width='100% border=' 0' cellpadding='0' cellspacing='0'
    class='table1'>
    <tr>
        <td>
<table width="100%" border="0" cellspacing='1' id="tables">
  <tr align="center" class="content0">
      <td width="15%" align="center">时间</td>
      <td width="10%" align="center">审批方式</td>
      <td width="10%" align="center">操作人</td>
      <td width="10%" align="center">下环人</td>
      <td width="15%" align="center">操作环节</td>
      <td width="15%" align="center">后环节</td>
      <td width="35%" align="center">批注</td>
  </tr>

  <c:forEach items="${logsVO}" var="item" varStatus="vs">
      <tr class='${vs.index % 2 == 0 ? "content1" : "content2"}'>
          <td align="center">${item.logTime}</td>
          <td align="center">${my:get('instanceOper', item.oprMode)}</td>
          <td align="center">${item.stafferName}</td>
          <td align="center">${item.nextStafferName}</td>
          <td align="center">${item.tokenName}</td>
          <td align="center">${item.nextTokenName}</td>
          <td align="center">${item.opinion}</td>
      </tr>
  </c:forEach>
</table>
        </td>
    </tr>
</table>
</div>
</form>
</body>
</html>