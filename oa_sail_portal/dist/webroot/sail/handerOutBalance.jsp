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
	
    submit('确定提交委托代销结算退货单?');
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
	type=hidden name="method" value="outBalanceBack" />
<input type=hidden name="outId" value="${bean.outId}" /> 
<input type=hidden name="id" value="${bean.id}" />
<input type=hidden name="type" value="${type}" />
<p:navigation
    height="22">
    <td width="550" class="navigation"><span style="cursor: pointer;"
        onclick="javascript:history.go(-1)">销售管理</span> &gt;&gt; 委托代销结算退货</td>
    <td width="85"></td>
</p:navigation> <br>

<table width="95%" border="0" cellpadding="0" cellspacing="0" id="viewTable"
	align="center">
	
	<p:title>
        <td class="caption"><strong>结算清单基本信息：</strong>  &nbsp;&nbsp;结算单号:${bean.id };&nbsp;&nbsp; 委托代销单号:${bean.outId }</td>
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
						<td width="10%" align="center">已退数量</td>
						<td width="10%" align="center">可退数量</td>
					</tr>
					
                    <c:forEach items="${bean.baseBalanceList}" var="fristBase" varStatus="vs">
                    <tr class="content1">
                        <td align="center">${fristBase.productName}
                        <input type="hidden" value="${fristBase.baseId}" name="baseId">
                        <input type="hidden" value="${fristBase.sailPrice}" name="price">
                        </td>

                        <td align="center">${fristBase.amount}</td>

                        <td align="center">${my:formatNum(fristBase.sailPrice)}</td>

                        <td align="center">${fristBase.inway}</td>
                        
                        <td align="center"><input type="text" value="0" style="width: 100%" name="amount" oncheck="notNone;isNumber;range(0, ${fristBase.amount - fristBase.inway})"></td>
                        
                    </tr>
                    </c:forEach>
				</table>
				</td>
			</tr>
			
			<tr class="content2">
                <td colspan="2" align="right">
                </td>
            </tr>
			
			<tr class="content1">
                <td colspan="1" align="right">
                 退货仓库：
                </td>
                <td>
                <select name="dirDepot" class="select_class" oncheck="notNone;" values="${outBean.location}">
                    <option value="">--</option>
                    <c:forEach items='${depotList}' var="item">
                        <option value="${item.id}">${item.name}</option>
                    </c:forEach>
                </select><font color="#FF0000">*</font>
                </td>
            </tr>
			
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

