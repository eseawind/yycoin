<%@ page contentType="text/html;charset=UTF-8" language="java"
	errorPage="../common/error.jsp"%>
<%@include file="../common/common.jsp"%>

<html>
<head>
<p:link title="增加委托代销结算清单" />
<script language="JavaScript" src="../js/common.js"></script>
<script language="JavaScript" src="../js/math.js"></script>
<script language="JavaScript" src="../js/public.js"></script>
<script language="JavaScript" src="../js/cnchina.js"></script>
<script language="JavaScript" src="../js/JCheck.js"></script>
<script language="JavaScript" src="../js/key.js"></script>
<script language="JavaScript" src="../js/compatible.js"></script>
<script language="JavaScript" src="../js/json.js"></script>
<script language="JavaScript" src="../sail_js/addOut.js"></script>
<script language="JavaScript" src="../js/jquery/jquery.js"></script>
<script language="JavaScript" src="../js/plugin/highlight/jquery.highlight.js"></script>
<script language="javascript">
function load()
{
    
}

function addBean()
{
	var ret = checkCurrentUser();

	if (!ret)
	{
		window.parent.location.reload();

		return false
	}
	
    submit('确定提交委托代销结算?');
}

function checkCurrentUser()
{	
     // check
     var elogin = "${g_elogin}";

 	 //  商务登陆
     //if (elogin == '1')
     //{
          var top = window.top.topFrame.document;
          //var allDef = window.top.topFrame.allDef;
          var srcStafferId = top.getElementById('srcStafferId').value;
         
          var currentStafferId = "${g_stafferBean.id}";

          var currentStafferName = "${g_stafferBean.name}";
         
          if (srcStafferId && srcStafferId != currentStafferId)
          {

               alert('请不要同时打开多个OA连接，且当前登陆者不同，当前登陆者应为：' + currentStafferName);
               
               return false;
          }
     //}

	return true;
}

</script>
</head>
<body class="body_class" onload="load()">
<form name="formBean" method=post action="../sail/out.do">
<input
	type=hidden name="method" value="addOutBalance" />
<input type=hidden name="outId" value="${outId}" /> 
<input type=hidden name="type" value="${type}" /> 
<p:navigation
    height="22">
    <td width="550" class="navigation"><span style="cursor: pointer;"
        onclick="javascript:history.go(-1)">销售管理</span> &gt;&gt; 委托代销</td>
    <td width="85"></td>
</p:navigation> <br>

<table width="95%" border="0" cellpadding="0" cellspacing="0" id="viewTable"
	align="center">
	
	<p:title>
        <td class="caption"><strong>结算清单基本信息：</strong></td>
    </p:title>

    <p:line flag="0" />
    
	<tr>
		<td colspan='2' align='center'>
		<table width="100%" border="0" cellpadding="0" cellspacing="0" 
			class="border">
			<tr>
				<td colspan="2">
				<table width="100%" border="0" cellspacing='1' id="tables">
					<tr align="center" class="content0">
						<td width="20%" align="center">品名</td>
						<td width="5%" align="center">数量</td>
						<td width="10%" align="center">销售价</td>
						<td width="10%" align="center">成本</td>
						<td width="10%" align="center">已结数量</td>
						<td width="10%" align="center">结算数量</td>
						<td width="10%" align="center">结算单价</td>
					</tr>
					
					<c:if test="${type == '0'}">
					<c:forEach items="${baseList}" var="fristBase" varStatus="vs">
                    <tr class="content1">
                        <td align="center">${fristBase.productName}
                        <input type="hidden" value="${fristBase.id}" name="baseId">
                        </td>

                        <td align="center">${fristBase.amount}</td>

                        <td align="center">${my:formatNum(fristBase.price)}</td>

                        <td align="center">${my:formatNum(fristBase.iprice)}</td>
                        
                        <td align="center">${fristBase.inway}</td>
                        
                        <td align="center"><input type="text"  style="width: 100%" name="amount" oncheck="notNone;isNumber;range(0, ${fristBase.unit})" value="0"></td>
                        
                        <td align="center"><input type="text"  style="width: 100%" name="price" oncheck="notNone;isFloat" value="${my:formatNum(fristBase.price)}" ></td>
                    </tr>
                    </c:forEach>
                    </c:if>
                    
                    <c:if test="${type == '1'}">
                    <c:forEach items="${baseList}" var="fristBase" varStatus="vs">
                    <tr class="content1">
                        <td align="center">${fristBase.productName}
                        <input type="hidden" value="${fristBase.id}" name="baseId">
                        </td>

                        <td align="center">${fristBase.amount}</td>

                        <td align="center">${my:formatNum(fristBase.price)}</td>

                        <td align="center">${my:formatNum(fristBase.iprice)}</td>
                        
                        <td align="center">${fristBase.inway}</td>
                        
                        <td align="center"><input type="text" style="width: 100%" name="amount" oncheck="notNone;isNumber;range(0, ${fristBase.unit})" value="0"></td>
                        
                        <td align="center"><input type="text" value="${my:formatNum(fristBase.price)}" style="width: 100%" readonly="readonly" name="price" oncheck="notNone;isFloat"></td>
                    </tr>
                    </c:forEach>
                    </c:if>
				</table>
				</td>
			</tr>
			
			<c:if test="${type == '1'}">
			<tr class="content1">
                <td colspan="1" align="right">
                 退货仓库：
                </td>
                <td>
                <select name="dirDepot" class="select_class" oncheck="notNone;" values="${out.location}">
                    <option value="">--</option>
                    <c:forEach items='${depotList}' var="item">
                        <option value="${item.id}">${item.name}</option>
                    </c:forEach>
                </select><font color="#FF0000">*</font>
                </td>
            </tr>
			</c:if>
			
			<tr class="content2">
                <td colspan="1" align="right">
                 备注：
                </td>
                <td>
                <textarea rows="3" cols="55" oncheck="notNone;"
                            name="description"></textarea><font color="#FF0000">*</font>
                </td>
            </tr>
		</table>

		</td>
	</tr>

	<p:line flag="1" />

	<tr>
        <td width="100%">
        <div align="right"><input type="button" name="pr"
            class="button_class" onclick="addBean()"
            value="&nbsp;&nbsp;提 交&nbsp;&nbsp;"></div>
        </td>
        <td width="0%"></td>
    </tr>

</table>
</form>
</body>
</html>

