<%@ page info="Web Service Index Page" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="com.china.center.osgi.ws.service.impl.ServerManagerImpl" %>
<html>
	<head>
		<title>Web Service Index Page</title>
	</head>
	<body>
		<CENTER>
		<font face="Comic Sans MS" size=4>
		<font color=blue>
		Web Service Index Page <br/>
		</font>
		<%
			request.setAttribute("wsMap", ServerManagerImpl.wsMap);
		%>
		
		<font face="Comic Sans MS" size=2>
		<table border="1">
			<c:forEach items="${wsMap}" var="item" varStatus="vs">
			<tr>
			<td width="10%" align="center">${vs.index + 1}</td>
			<td>
			${item.key} --> ${item.value})
			</td>
			<td>
			<c:if test="${item.value.modal == 'SOAP'}">
			<a href="./remoting${item.key}?wsdl" target="_blank">WSDL</a>
			</c:if>
			&nbsp;
			</td>
			</tr>
			</c:forEach>
		</table>
		
		</font>
		</font>
		</CENTER>
	</body>
</html>
