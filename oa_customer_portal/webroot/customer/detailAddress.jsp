<%@ page contentType="text/html;charset=UTF-8" language="java"
	errorPage="../common/error.jsp"%>
<%@include file="../common/common.jsp"%>

<html>
<head>
<p:link title="收货信息" />
<script language="JavaScript" src="../js/common.js"></script>
<script language="JavaScript" src="../js/math.js"></script>
<script language="JavaScript" src="../js/public.js"></script>
<script language="JavaScript" src="../js/cnchina.js"></script>
<script language="JavaScript" src="../js/JCheck.js"></script>
<script language="JavaScript" src="../js/compatible.js"></script>
<script language="JavaScript" src="../js/jquery/jquery.js"></script>
<script language="JavaScript" src="../js/key.js"></script>

<script language="javascript">

function load()
{
    loadForm();
}

function pagePrint()
{
	outForm.submit();
}

</script>
</head>
<body class="body_class" onload="load()">
<form name="outForm" method=post action="../customer/address.do">
<input type="hidden" name="method" value="">

<p:navigation
	height="22">
	<td width="550" class="navigation">收货管理 &gt;&gt; 客户收货地址</td>
				<td width="85"></td>
</p:navigation> <br>

<p:body width="100%">

	<p:title>
		<td class="caption">
		 <strong>收货信息</strong>
		</td>
	</p:title>
	
	<p:line flag="0" />
	
	
	<p:subBody width="98%">

		<p:table cells="2">
		
		  <tr  class="content2">
		  	<td>详细地址：</td>
		  	<td colspan="3">${bean.provinceName}&nbsp;&nbsp;${bean.cityName}&nbsp;&nbsp;${bean.areaName}&nbsp;&nbsp;${bean.address}</td>		  	
		  </tr>
		  
		  <p:cell title="收 货 人" end="true">
             ${bean.receiver}
          </p:cell>
          
          <tr  class="content2">
            <td>手&nbsp;&nbsp;&nbsp;&nbsp;机：</td>
            <td colspan=3>
              ${bean.mobile}
            </td>
          </tr>

          <tr  class="content1">
            <td>固定电话：</td>
            <td colspan=3>
              ${bean.telephone}
            </td>
          </tr>

		</p:table>

	</p:subBody>
	

	<p:line flag="1" />

	<p:button leftWidth="100%" rightWidth="0%">
		<div align="right">
		<input type="button"
			class="button_class" id="ok_b" style="cursor: pointer"
			value="&nbsp;&nbsp;返回&nbsp;&nbsp;" onclick="javaScript:window.history.go(-1);"></div>
	</p:button>

	<p:message2/>

</p:body>
</form>
</body>
</html>

