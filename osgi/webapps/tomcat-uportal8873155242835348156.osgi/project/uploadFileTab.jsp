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
    submit('确定提交申请?');
}


</script>
</head>

<body class="body_class" >
<form name="formEntry" action="../project/project.do?method=uploadFileTab" enctype="multipart/form-data" method="post">
<input type="hidden" name="oprType" value="0"> 
<input type="hidden" name="addOrUpdate" value="0"> 
<input type="hidden" name="processId" value=""> 
<p:navigation height="22">
	<td width="550" class="navigation">上传附件</td>
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
	
	    <p:class value="com.china.center.oa.project.bean.ProjectBean" />
		<p:table cells="2">
            <p:cell title="附件" width="8" end="true"><input type="file" name="filePath" size="70" >  
            </p:cell>
        </p:table>
	</p:subBody>
	

    <p:line flag="0" />
    
	<p:button leftWidth="98%" rightWidth="0%">
		<div align="right">
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

