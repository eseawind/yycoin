<%@ page contentType="text/html;charset=UTF-8" language="java"
	errorPage="../common/error.jsp"%>
<%@include file="../common/common.jsp"%>
<html>
<head>
<p:link title="发送邮件" />
<script language="JavaScript" src="../js/JCheck.js"></script>
<script language="JavaScript" src="../js/common.js"></script>
<script language="JavaScript" src="../js/public.js"></script>
<script language="JavaScript" src="../js/key.js"></script>
<script language="javascript">
function reply()
{
	$l('../mail/mail.do?method=findMail&operation=2&id=${bean.id}');
}

function replyAll()
{
    $l('../mail/mail.do?method=findMail&operation=3&id=${bean.id}');
}

function forward()
{
    <c:if test="${mail2}">
    $l('../mail/mail.do?method=findMail2&operation=1&id=${bean.id}');
    </c:if>
    
    <c:if test="${!mail2}">
    $l('../mail/mail.do?method=findMail&operation=1&id=${bean.id}');
    </c:if>
}

function next()
{
    $l('../mail/mail.do?method=findNextMail&id=${bean.id}');
}

function preview()
{
    $l('../mail/mail.do?method=findPreviewMail&id=${bean.id}');
}

function esc_back()
{
    $l('../mail/queryMail.jsp');
}

</script>

</head>
<body class="body_class" onmouseup="listenerMouse(event, esc_back)">
<form name="addApply" action="../mail/mail.do?method=sendMail" method="post">
<input
    type="hidden" name="reveiveIds" value=""> <input
    type="hidden" name="reveiveIds2" value=""><p:navigation
	height="22">
	<td width="550" class="navigation"><span style="cursor: pointer;"
		onclick="esc_back()">邮件管理</span> &gt;&gt; 阅读邮件</td>
	<td width="85"></td>
</p:navigation> <br>

<p:body width="100%">

    <p:button leftWidth="98%" rightWidth="2%">
        <div align="left">
        <c:if test="${!mail2}">
        &nbsp;&nbsp;<input type="button" class="button_class" id="ok_b"
         value="&nbsp;&nbsp;回复发件人(R)&nbsp;&nbsp;" accesskey="R"
            onclick="reply()">
        &nbsp;&nbsp;<input type="button" class="button_class" id="ok_b1"
         value="&nbsp;&nbsp;回复所有(A)&nbsp;&nbsp;" accesskey="A"
            onclick="replyAll()">
        </c:if>
        &nbsp;&nbsp;<input type="button" class="button_class" id="ok_b1"
         value="&nbsp;&nbsp;转发(F)&nbsp;&nbsp;" accesskey="F"
            onclick="forward()">
        <c:if test="${!mail2}">
        &nbsp;&nbsp;<input type="button" class="button_class" id="ok_b2"
         value="&nbsp;&nbsp;上一封(P)&nbsp;&nbsp;" accesskey="P"
            onclick="preview()">
        &nbsp;&nbsp;<input type="button" class="button_class" id="ok_b3"
         value="&nbsp;&nbsp;下一封(N)&nbsp;&nbsp;" accesskey="N"
            onclick="next()">
        </c:if>
         </div>
    </p:button>

    <p:line flag="1" />
    
	<p:subBody width="98%">

		<p:table cells="1">
		
		    <p:cell title="时间" width="8">
            <font color="blue"><b>${bean.logTime}</b></font>
            </p:cell>  
		
		    <p:cell title="发件人" width="8">
            ${bean.senderName}
            </p:cell>  

			<p:cell title="收件人" width="8">
			${bean.reveiveNames}
			</p:cell>
			<p:cell title="抄送人" width="8">
			${bean.reveiveNames2}
			</p:cell>
			<p:cell title="主题" width="8">
			<c:out value="${bean.title}"/>
			</p:cell>
			
			<p:cell title="附件" width="8">
			<c:forEach items="${atts}" var="item" varStatus="vs">
			<img src=../images/oa/attachment.gif><a target="_blank" href="../mail/mail.do?method=downMailAttachment&id=${item.id}">${item.name}</a>&nbsp;&nbsp;&nbsp;&nbsp;
			&nbsp;&nbsp;&nbsp;&nbsp;
			<c:if test="${!vs.last}">
			<br>
			</c:if>
			</c:forEach>
			</p:cell>
			
			<c:if test="${my:length(bean.href) > 0}">
			<p:cell title="链接" width="8">
            <a href="${bean.href}" title="点击进入链接内容">链接内容</a>
            </p:cell>
            </c:if>
			
			<p:cell title="内容" width="8"><textarea name="content" rows="25" readonly="readonly" style="width: 100%"><c:out value="${bean.content}"/></textarea></p:cell>

		</p:table>
	</p:subBody>
	
	<p:line flag="1" />
	
	<p:button leftWidth="98%" rightWidth="2%">
        <div align="left">&nbsp;&nbsp;<input type="button" class="button_class" id="ok_d"
            value="&nbsp;&nbsp;返 回(B)&nbsp;&nbsp;" accesskey="B"
            onclick="esc_back()"></div>
    </p:button>

    

	<p:message/>
</p:body></form>
</body>
</html>

