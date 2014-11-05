<%@ page contentType="text/html;charset=UTF-8" language="java"
	errorPage="../common/error.jsp"%>
<%@include file="../common/common.jsp"%>
<html>
<head>
<p:link title="告警" />
<script language="JavaScript" src="../js/JCheck.js"></script>
<script language="JavaScript" src="../js/common.js"></script>
<script language="JavaScript" src="../js/public.js"></script>
<script language="JavaScript" src="../js/key.js"></script>
<script language="javascript">

function load()
{
  
}

</script>

</head>
<body class="body_class" onload="load()">
<form name="formEntry">
<input type="hidden" name="method" value=""> 
<input type="hidden" name="id" value="${bean.id}"> 
<p:navigation
	height="22">
	<td width="550" class="navigation">
	<span>告警信息</span></td>
	<td width="85"></td>
</p:navigation> <br>

<p:body width="98%">

	<p:title>
		<td class="caption"><strong>告警基本信息：</strong></td>
	</p:title>

	<p:line flag="0" />

	<p:subBody width="100%">

		<p:class value="com.china.center.oa.publics.bean.AlarmBean" />

		<p:table cells="1">
		
		    <p:cell title="标识" end="true">
                ${bean.id}
            </p:cell>
            
             <p:cell title="时间">
                ${bean.logTime}
            </p:cell>

			<p:cell title="关联单据">
			    <c:choose>
				    <c:when test="${bean.refType == 0}">
	                    <a href="../sail/out.do?method=findOut&outId=${bean.refId}">${bean.refId}</a>
	                </c:when> 
	                
	                <c:otherwise>
	                    ${bean.refId}
	                </c:otherwise>
                </c:choose>
                
            </p:cell>

            <p:cell title="状态">
                ${my:get('alarmStatus', bean.status)}
            </p:cell>
            
             <p:cell title="告警内容">
                ${bean.alarmContent}
            </p:cell>
            
             <p:cell title="处理">
                ${bean.description}
            </p:cell>

		</p:table>

	</p:subBody>
	
	<p:line flag="1" />

	<p:button leftWidth="100%" rightWidth="0%">
		<div align="right">
			
            <input
	            type="button" name="ba" class="button_class"
	            onclick="javascript:history.go(-1)"
	            value="&nbsp;&nbsp;返 回&nbsp;&nbsp;">
			</div>
	</p:button>

	<p:message2 />
</p:body></form>
</body>
</html>

