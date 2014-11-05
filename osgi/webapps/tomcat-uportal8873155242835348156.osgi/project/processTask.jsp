<%@ page contentType="text/html;charset=UTF-8" language="java"
    errorPage="../common/error.jsp"%>
<%@include file="../common/common.jsp"%>
<html>
<head>
<p:link title="任务审批"/>
<link href="../js/plugin/dialog/css/dialog.css" type="text/css" rel="stylesheet"/>
<script src="../js/title_div.js"></script>
<script src="../js/public.js"></script>
<script src="../js/JCheck.js"></script>
<script src="../js/common.js"></script>
<script src="../js/tableSort.js"></script>
<script src="../js/jquery/jquery.js"></script>
<script src="../js/plugin/dialog/jquery.dialog.js"></script>
<script src="../js/plugin/highlight/jquery.highlight.js"></script>
<script src="../js/adapter.js"></script>
<script language="JavaScript" src="../project_js/project.js"></script>
<script language="javascript">

function processBean(opr)
{
    $("input[name='oprType']").val(opr);
    
    var msg = '';
    
    var checkFun = null;
    
    if ("0" == opr)
    {
        msg = '确定接受任务?';
    }
    
    if ("1" == opr)
    {
        msg = '确定驳回任务?';
    }
    
    if ("2" == opr)
    {
        $.messager.prompt('通过', '请输入增加信息', '', function(r){
            if (r)
            {
                $Dbuttons(true);
                var sss = r;

                getObj('reason').value = r;

                if (!(sss == null || sss == ''))
                {
                	formEntry.submit();
                }
                else
                {
                    $Dbuttons(false);
                }
            }
           
        });
        return false;
    }

    if ("3" == opr)
    {
    	$.messager.prompt('通过', '请输入信息', '', function(r){
            if (r)
            {
                $Dbuttons(true);
                var sss = r;
                getObj('reason').value = r;
                if (!(sss == null || sss == ''))
                {
                	formEntry.submit();
                }
                else
                {
                    $Dbuttons(false);
                }
            }
           
        });
        return false;
    }

    if ("4" == opr)
    {
    	$.messager.prompt('通过', '请输入信息', '', function(r){
            if (r)
            {
                $Dbuttons(true);
                var sss = r;
                getObj('reason').value = r;
                if (!(sss == null || sss == ''))
                {
                	getObj('flag').value = r;
                	formEntry.submit();
                }
                else
                {
                    $Dbuttons(false);
                }
            }
           
        });
        return false;
    }
    
    if ("5" == opr)
    {
    	$.messager.prompt('通过', '请输入信息', '', function(r){
            if (r)
            {
                $Dbuttons(true);
                var sss = r;
                getObj('reason').value = r;
                if (!(sss == null || sss == ''))
                {
                	getObj('flag').value = r;
                	formEntry.submit();
                }
                else
                {
                    $Dbuttons(false);
                }
            }
           
        });
        return false;
    }

    if ("7" == opr)
    {
    	$.messager.prompt('通过', '请输入增加信息', '', function(r){
            if (r)
            {
                $Dbuttons(true);
                var sss = r;

                getObj('reason').value = r;

                if (!(sss == null || sss == ''))
                {
                	formEntry.submit();
                }
                else
                {
                    $Dbuttons(false);
                }
            }
           
        });
        return false;
    }

    if ("8" == opr)
    {
    	$.messager.prompt('通过', '请输入信息', '', function(r){
            if (r)
            {
                $Dbuttons(true);
                var sss = r;
                getObj('reason').value = r;
                if (!(sss == null || sss == ''))
                {
                	getObj('flag').value = r;
                	formEntry.submit();
                }
                else
                {
                    $Dbuttons(false);
                }
            }
           
        });
        return false;
    }
    
    if ($O('processer'))
    {
	    if ("0" != opr)
	    {
	        $O('processer').oncheck = '';
	    }
	    else
	    {
	        $O('processer').oncheck = 'notNone';
	    }
    }
    
    submit(msg, null, checkFun);
}


function load()
{	
	 var flag = $O('update').value;
	 loadForm();
	tooltip.init();
	 if(flag==0)
	 {
		 $(":text").each(function(){      this.disabled=true;   });
	 }
	$v('tr_att_more', false);
	
}

</script>
</head>

<body class="body_class" onload="load()">
<form name="formEntry" action="../project/project.do?method=processTaskBean"  method="post">
<input type="hidden" name="oprType" value="0"> 
<input type="hidden" name="addOrUpdate" value="1"> 
<input type="hidden" name="update" value="${update }"> 
<input type="hidden" name="processId" value="${bean.processid}"> 
<input type="hidden" name="type" value="0"> 
<input type="hidden" name="stafferId" value="${g_stafferBean.id}"> 
<input type="hidden" name="departmentId" value="${g_stafferBean.principalshipId}"> 
<input type="hidden" name="stype" value="${g_stafferBean.otype}">
<input type="hidden" name="dutyStafferID" value="${bean.dutyStaffer}" />
<input type="hidden" name="partakerids" value="${partakerids}" />
<input type="hidden" name="receiverid" value="${bean.receiver}" />
<input type="hidden" value="${selType}" name="selType"> 
<input type="hidden" value="${taskBeforeids}" name="taskBeforeids"> 
<input type="hidden" value="${taskAfterids}" name="taskAfterids">
<input type="hidden" value="${bean.taskCode}" name="taskCode">
<input type="hidden" value="${bean.id}" name="id" id="id">
<input type="hidden" value="${bean.applyer}" name="applyer">
<input type="hidden" name="reason" id="reason" />
<input type="hidden" name="flag" id="flag" />
<p:navigation height="22">
	<td width="550" class="navigation">任务申请</td>
	<td width="85"></td>
</p:navigation> <br>

<p:body width="100%">

	<p:title>
		<td class="caption">
		 <strong>任务申请</strong>
		</td>
	</p:title>

	<p:line flag="0" />

	<p:subBody width="98%">
	
	    <p:class value="com.china.center.oa.project.bean.TaskBean" opr="1"/>
		<p:table cells="2">
			<p:pro field="applyer" innerString="readonly=true" value="${bean.applyerName}" cell="0">
			</p:pro>
            <p:pro field="taskName" />
            <p:pro field="taskType"  innerString="disabled onchange='borrowChange()'">
                <p:option type="223"></p:option>
            </p:pro>
            <p:pro field="dutyStaffer" value="${bean.dutyName}" innerString="readonly onclick=selectDutyStaffer(this)" />
            <p:pro field="emergencyType">
            	<p:option type="taskEmergencyType"></p:option>
            </p:pro>
            
            <p:pro field="partaker" cell="0" innerString="onclick=selectpartaker(this) disabled rows=2 cols=45"/>
            <p:pro field="planFinishTime" innerString="disabled" >
            ${bean.finishFullTime}
            </p:pro>
            <p:pro field="realFinishiTime" innerString="disabled"/>
            <p:pro field="transType"  innerString="disabled onchange='borrowChange()'">
                <p:option type="224"></p:option>
            </p:pro>
             <p:pro field="transObj"  innerString="disabled onchange='borrowChange()'">
                <p:option type="226"></p:option>
            </p:pro>
             <p:pro field="transObjCount" innerString="readonly"/>
            <p:pro field="receiver" value="${bean.receiverName}" innerString="readonly onclick=selectReceiver(this)"/>
            <p:pro field="refid" cell="0"/>
            <p:cell title="附件" width="8" end="true"><input type="file" name="filePath" size="70" readonly>  
            </p:cell>
            <p:pro field="beforeTask" innerString="cols=40 onclick='selectTaskBefore(this)' disabled style='cursor: pointer;'"/>
            <p:pro field="afterTask" innerString="cols=40 onclick='selectTaskAfter(this)' disabled style='cursor: pointer;'"/>
            <p:pro field="description" cell="0" innerString="disabled rows=4 cols=111" />
            <p:pro field="addinfo" cell="0" innerString="disabled rows=4 cols=111" />
            
        </p:table>
	</p:subBody>
	
	   <p:line flag="1" />
    
    <tr id="flowLog">
        <td colspan='2' align='center'>
        <table width="98%" border="0" cellpadding="0" cellspacing="0"
            class="border">
            <tr>
                <td>
                <table width="100%" border="0" cellspacing='1' id="tables">
                    <tr align="center" class="content0">
                        <td width="10%" align="center">审批人</td>
                        <td width="10%" align="center">审批动作</td>
                        <td width="10%" align="center">前状态</td>
                        <td width="10%" align="center">后状态</td>
                        <td width="45%" align="center">意见</td>
                        <td width="15%" align="center">时间</td>
                    </tr>

                    <c:forEach items="${logList}" var="item" varStatus="vs">
                        <tr class='${vs.index % 2 == 0 ? "content1" : "content2"}'>
                            <td align="center">${item.actor}</td>

                            <td  align="center">${item.oprModeName}</td>

                            <td  align="center">${item.preStatusName}</td>

                            <td  align="center">${item.afterStatusName}</td>

                            <td  align="center">${item.description}</td>

                            <td  align="center">${item.logTime}</td>

                        </tr>
                    </c:forEach>
                </table>
                </td>
            </tr>
        </table>

        </td>
    </tr>
    
    <p:line flag="1" />
    
	<p:button leftWidth="98%" rightWidth="0%">
        <div align="right">
        <%-- <c:if test="${pb.flag!=1 && user.stafferId eq pb.approverId}">
          <input type="button" class="button_class" id="sub_b2"
            value="&nbsp;&nbsp;接受任务&nbsp;&nbsp;" onclick="processBean(0)">
            <c:if test="${user.stafferId ne bean.receiver }">
          		<input type="button" class="button_class" id="sub_b2"
            		value="&nbsp;&nbsp;驳回任务&nbsp;&nbsp;" onclick="processBean(1)">
            </c:if>
            </c:if> --%>
             <input type="button" class="button_class" id="sub_b2"
            value="&nbsp;&nbsp;增加任务信息&nbsp;&nbsp;" onclick="processBean(2)">
            <c:if test="${user.stafferId eq bean.dutyStaffer }">
          <input type="button" class="button_class" id="sub_b2"
            value="&nbsp;&nbsp;申请停止&nbsp;&nbsp;" onclick="processBean(3)">
             <input type="button" class="button_class" id="sub_b2"
            value="&nbsp;&nbsp;申请完成&nbsp;&nbsp;" onclick="processBean(6)">
            </c:if>
            <c:if test="${user.stafferId eq bean.receiver }">
            <c:if test="${pb.mode eq 3 }">
             <input type="button" class="button_class" id="sub_b2"
            value="&nbsp;&nbsp;同意申请停止&nbsp;&nbsp;" onclick="processBean(4)">
            <input type="button" class="button_class" id="sub_b2"
            value="&nbsp;&nbsp;不同意申请停止&nbsp;&nbsp;" onclick="processBean(5)">
            </c:if>
            <c:if test="${pb.mode eq 6 }">
            <input type="button" class="button_class" id="sub_b2"
            value="&nbsp;&nbsp;同意申请完成&nbsp;&nbsp;" onclick="processBean(7)">
            <input type="button" class="button_class" id="sub_b2"
            value="&nbsp;&nbsp;不同意申请完成&nbsp;&nbsp;" onclick="processBean(8)">
            </c:if>
            </c:if>
        </div>
    </p:button>
	
	<p:message2/>
</p:body>
</form>
<br/>
<br/>
<br/>
</body>
</html>

