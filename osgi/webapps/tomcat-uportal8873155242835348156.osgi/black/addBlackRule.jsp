<%@ page contentType="text/html;charset=UTF-8" language="java"
	errorPage="../common/error.jsp"%>
<%@include file="../common/common.jsp"%>
<html>
<head>
<p:link title="增加黑名单例外规则" />
<script language="JavaScript" src="../js/JCheck.js"></script>
<script language="JavaScript" src="../js/common.js"></script>
<script language="JavaScript" src="../js/public.js"></script>
<script language="JavaScript" src="../js/math.js"></script>
<script language="JavaScript" src="../black_js/blackRule.js"></script>
<script language="javascript">

function addBean()
{
	
	submit('确定提交规则?', null, check);
}


function selectPrincipalship()
{
    window.common.modal('../admin/pop.do?method=rptQueryPrincipalship&load=1&selectMode=0');
}

function getPrincipalship(oos)
{

    var ids = '';
    var names = '';
    for (var i = 0; i < oos.length; i++)
    {
        if (i == oos.length - 1)
        {
            ids = ids + oos[i].value ;
            names = names + oos[i].pname ;
        }
        else
        {
            ids = ids + oos[i].value + ';';
            names = names + oos[i].pname + ';' ;
        }        
    }

    $O('industryId').value = ids;
    $O('industryIdsName').value = names;
    
}

function load()
{
	loadForm();
}

function check()
{
	if (compareDate($$('beginOutTime'),$$('endOutTime')) > 0)
	{
		alert('开始日期不能大于结束日期');

		return false;		
	}

	 return true;
	
}


</script>

</head>
<body class="body_class" onload="load()">
<form name="formEntry" action="../commission/black.do" method="post">
	<input type="hidden" name="method" value="addBlackRule">
	<input type="hidden" name="industryId" value="">
	<input type="hidden" name="productType" value="">
	<input type="hidden" name="departmentId" value="">
	
<p:navigation height="22">
	<td width="550" class="navigation"><span style="cursor: pointer;"
		onclick="javascript:history.go(-1)">黑名单例外规则管理</span> &gt;&gt; 增加黑名单例外规则</td>
	<td width="85"></td>
</p:navigation> <br>

<p:body width="100%">

	<p:title>
		<td class="caption"><strong>基本信息：</strong></td>
	</p:title>

	<p:line flag="0" />

	<p:subBody width="98%">
		<p:class value="com.china.center.oa.commission.bean.BlackRuleBean" />

		<p:table cells="2">
			
			<p:pro field="name" cell="0" innerString="size=80" />

			<p:pro field="type" cell="0" >
				<p:option type="blackType"></p:option>
			</p:pro>
			
			<p:pro field="beginOutTime" />
			
			<p:pro field="endOutTime" />
			
			<p:cell title="品类" end="true">
				<textarea name='productTypeName'
					head='品类' id='productTypeName' rows=2 cols=80 readonly=true"></textarea><input
					type="button" value="&nbsp;...&nbsp;" name="qout1" id="qout1"
					class="button_class" onclick="selectProductType()">
					<input
					type="button" value="清空" name="qout" id="qout"
					class="button_class" onclick="clears(1)">&nbsp;&nbsp;
			</p:cell>
			
			<p:cell title="事业部" end="true">
				<textarea name='industryIdsName'
					head='事业部' id='industryIdsName' rows=2 cols=80 readonly=true
					></textarea><input
					type="button" value="&nbsp;...&nbsp;" name="qout2" id="qout2"
					class="button_class" onclick="selectPrincipalship()">
					<input
					type="button" value="清空" name="qout4" id="qout4"
					class="button_class" onclick="clears(2)">&nbsp;&nbsp;
			</p:cell>
			
			<p:cell title="销售单号" end="true">
				<textarea name='outId'
					head='销售单号，以分号;间隔' id='outId' rows=2 cols=80
					></textarea>
			</p:cell>
			
		</p:table>
	</p:subBody>

	<!--<p:line flag="0" />-->
	
	<tr id="subProduct_tr" >
		<td colspan='2' align='center'>
		<table width="98%" border="0" cellpadding="0" cellspacing="0"
			class="border">
			<tr>
				<td>
				<table width="100%" border="0" cellspacing='1' id="tables">
					<tr align="center" class="content0">
						<td width="45%" align="center">产品</td>
						<td width="5%" align="left"><input type="button"
							accesskey="A" value="增加(A)" class="button_class"
							onclick="addTr()"></td>
					</tr>
				</table>
				</td>
			</tr>
		</table>

		</td>
	</tr>

	<tr id="staffer_tr" >
		<td colspan='2' align='center'>
		<table width="98%" border="0" cellpadding="0" cellspacing="0"
			class="border">
			<tr>
				<td>
				<table width="100%" border="0" cellspacing='1' id="tables_staffer">
					<tr align="center" class="content0">
						<td width="45%" align="center">职员</td>
						<td width="5%" align="left"><input type="button"
							accesskey="A" value="增加(A)" class="button_class"
							onclick="addStafferTr()"></td>
					</tr>
				</table>
				</td>
			</tr>
		</table>

		</td>
	</tr>

	<p:line flag="1" />
	
	<p:button leftWidth="100%" rightWidth="0%">
		<div align="right"><input type="button"
			class="button_class" id="ok_b" style="cursor: pointer"
			value="&nbsp;&nbsp;提交&nbsp;&nbsp;" onclick="addBean()"></div>
	</p:button>

	<p:message />
	
</p:body>
</form>
<br/>
<br/>
<br/>
<br/>
<br/>
<table>
	<tr class="content1" id="trCopy" style="display: none;">
		<td width="45%" align="center"><input type="text"
			style="width: 100%; cursor: pointer;" readonly="readonly" value=""
			oncheck="notNone" name="targetName" onclick="selectProduct(this)">
		<input type="hidden" name="productId" value="0"></td>		
		<td width="5%" align="center"><input type=button
			value="&nbsp;删 除&nbsp;" class=button_class onclick="removeTr(this)"></td>
	</tr>
	
	<tr class="content1" id="trCopy1" style="display: none;">
		<td width="45%" align="center"><input type="text"
			style="width: 100%; cursor: pointer;" readonly="readonly" value=""
			oncheck="notNone" name="stafferName" onclick="selectStaffer(this)">
		<input type="hidden" name="stafferId" value="0"></td>		
		<td width="5%" align="center"><input type=button
			value="&nbsp;删 除&nbsp;" class=button_class onclick="removeTr(this)"></td>
	</tr>
	
</table>

</body>
</html>