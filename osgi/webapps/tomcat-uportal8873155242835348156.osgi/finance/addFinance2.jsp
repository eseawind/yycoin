<%@ page contentType="text/html;charset=UTF-8" language="java"
	errorPage="../common/error.jsp"%>
<%@include file="../common/common.jsp"%>
<html>
<head>
<p:link title="增加凭证" />
<script language="JavaScript" src="../js/common.js"></script>
<script language="JavaScript" src="../js/public.js"></script>
<script language="JavaScript" src="../js/JCheck.js"></script>
<script language="JavaScript" src="../js/json.js"></script>
<script language="JavaScript" src="../js/autosuggest.js"></script>
<script language="JavaScript" src="../tax_js/addTax.js"></script>
<script language="JavaScript" src="../tax_js/addFinance.js"></script>
<script language="javascript">

function addBean()
{
    submit('确定增加凭证?', null, checks);
}

var taxList = JSON.parse('${taxListStr}');

function checks()
{
	var deList = document.getElementsByName('taxId');
	
	var inMoney = 0.0;
	
	var outMoney = 0.0;
	
	for (var i = 0; i < deList.length; i++)
	{
		if (deList[i].value == '')
		{
			continue;
		}
		
		var ins = getTrInnerObj(deList[i], 'inmoney');
		
		var ous = getTrInnerObj(deList[i], 'outmoney');
		
		if (ins.value != 'NA')
		{
			if (parseFloat(ins.value) <= 0.0)
			{
				alert('金额必须大于0');
				
				return false;
			}
			
			inMoney += parseFloat(ins.value);
		}
		
		if (ous.value != 'NA')
		{
			if (parseFloat(ous.value) <= 0.0)
			{
				alert('金额必须大于0');
				
				return false;
			}
			
			outMoney += parseFloat(ous.value);
		}
		
		if (inMoney == outMoney && inMoney != 0.0)
		{
			inMoney = 0.0;
			
			outMoney = 0.0;
		}
	}
	
	if (inMoney != outMoney)
	{
		alert('借贷必相等,借方:' + inMoney + ',贷方:' + outMoney);
				
		return false;
	}
	
    return true;
}

function load()
{
	addTr();
	addTr();
	addTr();
	addTr();
	addTr();
	addTr();
}

</script>

</head>
<body class="body_class" onload="load()">
<form name="formEntry" action="../finance/finance.do" method="post">
<input
	type="hidden" name="method" value="addFinance"> 

<p:navigation height="22">
	<td width="550" class="navigation"><span style="cursor: pointer;"
		onclick="javaScript:window.history.go(-1);">凭证管理</span> &gt;&gt; 增加凭证</td>
	<td width="85"></td>
</p:navigation>

<p:body width="100%">

	<p:title>
		<td class="caption">
		<strong>凭证信息</strong>
		</td>
	</p:title>

	<p:line flag="0" />

	<p:subBody width="98%">
		<p:table cells="1">
			<p:tr align="left">
			凭证归属：
			<select name="dutyId" class="select_class" style="width: 15%;" oncheck="notNone">
		         <option value="">--</option>
		         <p:option type="dutyList"></p:option>
	         </select>
	         凭证类型：
			<select name="type" class="select_class" style="width: 15%;" oncheck="notNone">
		         <option value="">--</option>
		         <p:option type="financeType"/>
	         </select>
         	描述：<input type="text" style="width: 50%"
                    name="description" value="" maxlength="100">
			</p:tr>
		</p:table>
	</p:subBody>
	
	<p:tr></p:tr>
	
	<tr>
        <td colspan='2' align='center'>
        <table width="98%" border="0" cellpadding="0" cellspacing="0"
            class="border">
            <tr>
                <td>
                <table width="100%" border="0" cellspacing='1' id="tables">
                    <tr align="center" class="content0">
                        <td width="15%" align="center">摘要</td>
                        <td width="60%" align="center">科目</td>
                        <td width="8%" align="center">借方金额</td>
                        <td width="8%" align="center">贷方金额</td>
                        <td width="5%" align="left"><input type="button" accesskey="A"
                            value="增加" class="button_class" onclick="addTr()"></td>
                    </tr>
                </table>
                </td>
            </tr>
        </table>
        </td>
    </tr>

	<p:line flag="1" />

	<p:button leftWidth="100%" rightWidth="0%">
		<div align="right">
		  <input type="button" class="button_class" id="sub_b"
            value="&nbsp;&nbsp;提 交&nbsp;&nbsp;" onclick="addBean()">
        </div>
	</p:button>
	
	<p:message2/>
</p:body>
</form>
<table>
    <tr class="content1" id="trCopy" style="display: none;">
         <td width="15%" align="center">
         <input type="text" 
         style="width: 100%" value="" name="idescription">
         </td>
         <td width="60%" align="center">
         <select name="taxId" class="select_class" style="width: 50%;" onchange="taxChange(this)">
         <option value="">--</option>
         <c:forEach var="item" items="${taxList}">
             <option value="${item.id}" 
             >${item.code}${item.name}</option>
         </c:forEach>
         </select>
         <select name="departmentId" class="select_class" style="width: 15%;display: none;">
         <option value="">选择部门</option>
         <c:forEach var="item" items="${departmentBeanList}">
             <option value="${item.id}">${item.name}</option>
         </c:forEach>
         </select>
         <input type="text" style="width: 15%;display: none;cursor: pointer;" onclick="selectStaffer(this)"
                    name="stafferId" value="选择职员" readonly="readonly">
         <input type="hidden" name="stafferId2" value=""> 
         <input type="text" style="width: 15%;display: none;cursor: pointer;" onclick="selectUnit(this)"
                    name="unitId" value="选择单位" readonly="readonly">
         <input type="hidden" name="unitId2" value=""> 
         </td>
         <td width="8%" align="center">
         <input type="text" style="width: 100%;"
                    name="inmoney" value="" oncheck=""></td>
                    
         <td width="8%" align="center">
         <input type="text" style="width: 100%"
                    name="outmoney" value="" oncheck=""></td>
                    
        <td width="5%" align="center"><input type=button
            value="&nbsp;删 除&nbsp;" class=button_class onclick="removeTr(this)"></td>
    </tr>
</table>
</body>
</html>

