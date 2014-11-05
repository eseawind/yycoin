<%@ page contentType="text/html;charset=UTF-8" language="java"
	errorPage="../common/error.jsp"%>
<%@include file="../common/common.jsp"%>
<html>
<head>
<p:link title="合同申请" guid="true"/>
<script language="JavaScript" src="../js/common.js"></script>
<script language="JavaScript" src="../js/compatible.js"></script>
<script language="JavaScript" src="../js/math.js"></script>
<script language="JavaScript" src="../js/public.js"></script>
<script language="JavaScript" src="../js/JCheck.js"></script>
<script language="JavaScript" src="../js/json.js"></script>
<script language="JavaScript" src="../project_js/project.js"></script>
<script language="javascript">

function addBean(opr)
{
    $("input[name='oprType']").val(opr);
    
    submit('确定任务申请?', null, checks);
}

function load()
{
	
	$v('tr_att_more', false);
	
}

function clears()
{
	$O('partakerids').value = '';
	$O('partaker').value = '';
}

</script>
</head>

<body class="body_class" onload="load()">
<form name="formEntry" action="../project/project.do?method=addOrUpdateTask" enctype="multipart/form-data" method="post">
<input type="hidden" name="oprType" value="0"> 
<input type="hidden" name="addOrUpdate" value="0"> 
<input type="hidden" name="processId" value=""> 
<input type="hidden" name="type" value="0"> 
<input type="hidden" name="stafferId" value="${g_stafferBean.id}"> 
<input type="hidden" name="departmentId" value="${g_stafferBean.principalshipId}"> 
<input type="hidden" name="stype" value="${g_stafferBean.otype}">
<input type="hidden" name="dutyStafferID" value="" />
<input type="hidden" name="partakerids" value="" />
<input type="hidden" name="receiverid" value="" />
<input type="hidden" value="${selType}" name="selType"> 
<input type="hidden" value="${taskBeforeids}" name="taskBeforeids"> 
<input type="hidden" value="${taskAfterids}" name=taskAfterids>

<input type="hidden" name="applyer" value="${g_stafferBean.id}">
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
	
	    <p:class value="com.china.center.oa.project.bean.TaskBean" />
		<p:table cells="2">
			<p:pro field="applyer" innerString="readonly=true" value="${g_stafferBean.name}" cell="0">
				<input type="button" value="&nbsp;...&nbsp;" name="qout0" id="qout0"
                    class="button_class" onclick="selectApplyer()">&nbsp;&nbsp;
			</p:pro>
		
            <p:pro field="taskName" />
            <p:pro field="taskType"  innerString="onchange='borrowChange()'" value="9995757">
                <p:option type="223"></p:option>
            </p:pro>
            <p:pro field="dutyStaffer" innerString="onclick=selectDutyStaffer(this)" />
            
            <p:pro field="emergencyType">
            	<p:option type="taskEmergencyType"></p:option>
            </p:pro>
            
            <p:cell title="参与人" end="true">
            <textarea name='partaker'
					head='参与人' id='partaker' onclick='selectpartaker()' rows=2 cols=80 style='cursor: pointer;' readonly=true></textarea>
            <input
						type="button" value="清空" name="qout" id="qout"
						class="button_class" onclick="clears()">&nbsp;&nbsp;
            </p:cell>
	            
            <p:pro field="planFinishTime" cell="0">
            	时：<select name="finishTime" class="select_class" oncheck="notNone;isInt;range(0, 23)" style="width: 50px">
                            <p:option type="[0,23]"/>
                  </select>&nbsp;
            	分：<select name="finishMin" class="select_class" oncheck="notNone;isInt;range(0, 59)" style="width: 50px">
                            <p:option type="[0,59]"/>
                   </select>
            </p:pro>
            
            <p:pro field="transType"  innerString="onchange='borrowChange()'">
                <p:option type="224"></p:option>
            </p:pro>
             <p:pro field="transObj"  innerString="onchange='borrowChange()'">
                <p:option type="226"></p:option>
            </p:pro>
             <p:pro field="transObjCount" />
            <p:pro field="receiver" innerString="onclick=selectReceiver(this)"/>
            
            <p:pro field="refid" cell="0"/>
            
            <p:cell title="附件" width="8" end="true"><input type="file" name="filePath" size="70" >  
            </p:cell>
            <p:pro field="beforeTask" innerString="cols=40 onclick='selectTaskBefore(this)' style='cursor: pointer;'"/>
            <p:pro field="afterTask" innerString="cols=40 onclick='selectTaskAfter(this)' style='cursor: pointer;'"/>
            <p:pro field="description" cell="0" innerString="rows=4 cols=55" />
            
        </p:table>
	</p:subBody>
	
	

    <p:line flag="1" />
    
	<p:button leftWidth="98%" rightWidth="0%">
		<div align="right">
		<input type="button" class="button_class" id="sub_b1"
            value="&nbsp;&nbsp;保存&nbsp;&nbsp;" onclick="addBean(0)">
		  &nbsp;&nbsp;
		  <input type="button" class="button_class" id="sub_b2"
            value="&nbsp;&nbsp;提 交&nbsp;&nbsp;" onclick="addBean(1)">
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

