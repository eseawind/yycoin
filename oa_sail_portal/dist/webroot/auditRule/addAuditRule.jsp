<%@ page contentType="text/html;charset=UTF-8" language="java"
	errorPage="../common/error.jsp"%>
<%@include file="../common/common.jsp"%>
<html>
<head>
<p:link title="增加销售单审核规则" />
<script language="JavaScript" src="../js/JCheck.js"></script>
<script language="JavaScript" src="../js/common.js"></script>
<script language="JavaScript" src="../js/public.js"></script>
<script language="JavaScript" src="../js/math.js"></script>
<script language="JavaScript" src="../auditRule_js/auditRule.js"></script>
<script language="javascript">

function addBean()
{
	
	submit('确定提交销售审核规则?', null, check);
}


function selectPrincipalship()
{
    window.common.modal('../admin/pop.do?method=rptQueryPrincipalship&load=1&selectMode=1');
}

function getPrincipalship(oos)
{
    var oo = oos[0];

    $O('industryId').value = oo.value;
    $O('industryName').value = oo.pname;   
}


function load()
{
	loadForm();

	addTr();
}

function check()
{
	
     var managerTypes = document.getElementsByName("p_managerType");
     var productTypes = document.getElementsByName("p_productType");
     var materialTypes = document.getElementsByName("p_materialType");
     var currencyTypes = document.getElementsByName("p_currencyType");
     var productIds = document.getElementsByName("productId");        
     var payConditions = document.getElementsByName("p_payCondition");
     var accountPeriods = document.getElementsByName("p_accountPeriod");
     var productPeriods = document.getElementsByName("p_productPeriod");
     var ratioDowns = document.getElementsByName("p_ratioDown");
     var ratioUps = document.getElementsByName("p_ratioUp");        
     var profitPeriods = document.getElementsByName("p_profitPeriod");
     var ltSailPrices = document.getElementsByName("p_ltSailPrice");
     var diffRatios = document.getElementsByName("p_diffRatio");
     var minRatios = document.getElementsByName("p_minRatio");	 

	 var arr = [];

	 if (!managerTypes || managerTypes.length <= 0)
	 {
		 alert('必须填写一行');
		 return false;
	 }
	 
	 for (var i = 0; i < managerTypes.length - 1; i++)
	 {

		 if (managerTypes[i].value == '' && productTypes[i].value == '' && materialTypes[i].value == '' &&	currencyTypes[i].value == ''&& productIds[i].value == '' && payConditions[i].value == '' && accountPeriods[i].value == '' && ltSailPrices[i].value == '')
		 {
			 alert('行项目中不能全为空');

			 $f(managerTypes[i]);

			 return false;
		 }

	 	if (parseInt(ratioDowns[i].value, 10) > parseInt(ratioUps[i].value, 10))
		{
			alert('下限不能大于上限');

			$f(ratioDowns[i]);
			
			return false;
		}

		if (parseInt(ltSailPrices[i].value, 10) == 0)
		{
			if (parseFloat(diffRatios[i].value) <= 0 )
			{
				alert('允许低于售价时，差异比例必填');

				$f(diffRatios[i]);
				
				return false;
			}
		}
		 
	     //子商品不能重复
		 var each = managerTypes[i].value + productTypes[i].value + materialTypes[i].value + currencyTypes[i].value + productIds[i].value + payConditions[i].value + accountPeriods[i].value + ltSailPrices[i].value ;
			
			if (containInList(arr, each))
			{
				alert('存在重复行,请检查');
				
				return false;
			}		
			
			arr.push(each);
	 }

	 return true;
	
}



</script>

</head>
<body class="body_class" onload="load()">
<form name="formEntry" action="../sail/auditRule.do" method="post">
	<input type="hidden" name="method" value="addAuditRule">
	<input type="hidden" name="industryId" value="">
	
<p:navigation height="22">
	<td width="550" class="navigation"><span style="cursor: pointer;"
		onclick="javascript:history.go(-1)">销售审核规则管理</span> &gt;&gt; 增加销售审核规则</td>
	<td width="85"></td>
</p:navigation> <br>

<p:body width="100%">

	<p:title>
		<td class="caption"><strong>销售审核规则基本信息：</strong></td>
	</p:title>

	<p:line flag="0" />

	<p:subBody width="98%">
		<p:class value="com.china.center.oa.sail.bean.AuditRuleBean" />

		<p:table cells="2">
			
			<p:pro field="industryId" innerString="readonly=true">
				<input type="button" value="&nbsp;...&nbsp;" name="qout" id="qout"
                    class="button_class" onclick="selectPrincipalship()">&nbsp;&nbsp;
			</p:pro>

			<p:pro field="sailType" >
				<p:option type="outType_out"></p:option>
			</p:pro>			
			
		</p:table>
	</p:subBody>

	<p:line flag="0" />
	
	<tr id="subProduct_tr" >
		<td colspan='2' align='center'>
		<table width="98%" border="0" cellpadding="0" cellspacing="0"
			class="border">
			<tr>
				<td>
				<table width="100%" border="0" cellspacing='1' id="tables">
					<tr align="center" class="content0">
						<td width="6%" align="center">管理类型</td>
						<td width="6%" align="center">产品类型</td>
						<td width="6%" align="center">材质类型</td>
						<td width="6%" align="center">纸币类型</td>
						<td width="15%" align="center">产品名称</td>
						<td width="6%" align="center">付款条件<font color="#FF0000">*</font></td>
						<td width="6%" align="center">销售账期</td>
						<td width="6%" align="center">产品账期</td>
						<td width="7%" align="center">毛利率下限</td>
						<td width="7%" align="center">毛利率上限</td>
						<td width="6%" align="center">利润账期</td>
						<td width="6%" align="center">低于售价</td>
						<td width="6%" align="center">差异比例</td>
						<td width="6%" align="center">最小毛利率</td>
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

	<p:line flag="1" />
	
	<p:button leftWidth="100%" rightWidth="0%">
		<div align="right"><input type="button"
			class="button_class" id="ok_b" style="cursor: pointer"
			value="&nbsp;&nbsp;提交&nbsp;&nbsp;" onclick="addBean()"></div>
	</p:button>

	<p:message />
	
</p:body>
</form>

<table>
	<tr class="content1" id="trCopy" style="display: none;">
		<td width="6%" align="center">         
			<select name="p_managerType" class="select_class" style="width: 100%;">
            	<p:option type="pubManagerType" empty="true"></p:option>
         	</select>
		</td>
		<td width="6%" align="center">
			<select name="p_productType" class="select_class" style="width: 100%;">
            	<p:option type="productType" empty="true"></p:option>
         	</select>
		</td>	
		<td width="6%" align="center">         
			<select name="p_materialType" class="select_class" style="width: 100%;">
            	<p:option type="201" empty="true"></p:option>
         	</select>
		</td>
		<td width="6%" align="center">
			<select name="p_currencyType" class="select_class" style="width: 100%;">
            	<p:option type="209" empty="true"></p:option>
         	</select>
		</td>		
		<td width="15%" align="center"><input type="text"
			style="width: 100%; cursor: pointer;" maxLength="100" readonly="readonly" value=""
			name="targetName" onclick="selectProduct(this)">
		<input type="hidden" name="productId" value=""></td>
		<td width="6%" align="center">         
			<select name="p_payCondition" class="select_class" style="width: 100%;" oncheck="notNone">
            	<p:option type="payCondition" empty="true"></p:option>
         	</select>
		</td>
		<td width="6%" align="center">
         	<input name="p_accountPeriod" value="0" style="width: 100%;" oncheck="range(0, 180)"/>
		</td>	
		<td width="6%" align="center">         
			<input name="p_productPeriod" value="0" style="width: 100%;" oncheck="range(0, 180)"/>
		</td>
		<td width="7%" align="center">
			<input name="p_ratioDown" value="0" style="width: 100%;" oncheck='range(0, 100)' />
		</td>
		<td width="7%" align="center">
			<input name="p_ratioUp" value="0" style="width: 100%;" oncheck='range(0, 100)' />
		</td>
		<td width="6%" align="center">
         	<input name="p_profitPeriod" value="0" style="width: 100%;" oncheck="range(0, 180)"/>
		</td>	
		<td width="6%" align="center">         			
			<select name="p_ltSailPrice" class="select_class" style="width: 100%;">
            	<p:option type="lessThanSailPrice" empty="true"></p:option>
         	</select>
		</td>
		<td width="6%" align="center">
			<input name="p_diffRatio" value="0.00" style="width: 100%;" oncheck='isFloat;' />
		</td>
		<td width="6%" align="center">
			<input name="p_minRatio" value="0.00" style="width: 100%;" oncheck='isFloat;' />
		</td>
				
		<td width="5%" align="center"><input type=button
			value="&nbsp;删 除&nbsp;" class=button_class onclick="removeTr(this)"></td>
	</tr>
</table>

</body>
</html>