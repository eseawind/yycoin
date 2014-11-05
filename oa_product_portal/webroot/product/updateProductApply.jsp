<%@ page contentType="text/html;charset=UTF-8" language="java"
	errorPage="../common/error.jsp"%>
<%@include file="../common/common.jsp"%>
<html>
<head>
<p:link title="增加新产品申请" />
<script language="JavaScript" src="../js/JCheck.js"></script>
<script language="JavaScript" src="../js/common.js"></script>
<script language="JavaScript" src="../js/public.js"></script>
<script language="JavaScript" src="../product_js/productApply.js"></script>
<script language="javascript">

function saveBean()
{
	//保存
	$O('save').value = '0';
	submit('确定保存申请新产品?', null, check);
}

function addBean()
{
	//提交
	$O('save').value = '1';
	submit('确定提交申请新产品?', null, check);
}

function check()
{

	 var date1 = $$('commissionBeginDate');

	 var date2 = $$('commissionEndDate');

	 if ((date1 != '' && date2 == '')||(date1 == '' && date2 != ''))
	 {
		alert('提成周期须同时填写');

		return false;
	 }
	 
	 if (compareDate(date1, date2)>0)
	 {
		alert('开始日期不能大于结束日期');

		return false;
	 }
	
	 if ($O('nature').value == '0')
	 {
		 if ($$('refProductId') == '')
		 {
			alert('配件产品要关联成品产品');

			return false;
		 }
	 }

	 // 产品人员属性校验 
	 var stafferRoleArr = document.getElementsByName("stafferRole");
	 var commissionRatioArr = document.getElementsByName('commissionRatio');
	 var stafferIdArr = document.getElementsByName('stafferId');

	 var arr1 = [];
	 
	 if (stafferRoleArr == null || stafferRoleArr.length == 0)
	 {
		alert('必须要有产品人员属性');

		return false;
	 }
	 
	 for (var i = 0; i < stafferRoleArr.length - 1; i++)
	 {

		 //数字型判断
		 if (!isNumbers(commissionRatioArr[i].value))
	        {
	            alert('提成比例为正整数');
	            
	            $f(commissionRatioArr[i]);
	            
	            return false;
	        }

	     //子商品不能重复
		 var each = stafferRoleArr[i];
			
			if (containInList(arr1, each.value))
			{
				alert('人员角色不能重复');
				
				return false;
			}
			
			arr1.push(each.value);
	 }
	 
	 return true;

}

function load()
{
	var nature = ${bean.nature};
	
	if (nature == '1')
	{
		$v('subProduct_tr', true);
	}
}

</script>

</head>
<body class="body_class" onload="load()">
<form name="formEntry" action="../product/productApply.do" method="post">
	<input type="hidden" name="method" value="updateProductApply">
	<input type="hidden" name="productManagerId" value="${bean.productManagerId}"> 
	<input type="hidden" name="oprId" value="${bean.oprId}"> 
	<input type="hidden" name="save" value=""> 
	<input type="hidden" name="industryId" value="${bean.industryId}">
	<input type="hidden" name="id" value="${bean.id}">
	<input type="hidden" name="refProductId" value="">

<p:navigation height="22">
	<td width="550" class="navigation"><span style="cursor: pointer;"
		onclick="javascript:history.go(-1)">新产品申请</span> &gt;&gt; 更新新产品</td>
	<td width="85"></td>
</p:navigation> <br>

<p:body width="100%">

	<p:title>
		<td class="caption"><strong>新产品基本信息：</strong></td>
	</p:title>

	<p:line flag="0" />

	<p:subBody width="98%">
		<p:class value="com.china.center.oa.product.bean.ProductApplyBean" opr="1"/>

		<p:table cells="2">

			<p:pro field="name" cell="0" innerString="size=60" />

			<p:pro field="type">
				<p:option type="productType" empty="true" />
			</p:pro>

			<p:pro field="materiaType">
				<p:option type="201" empty="true" />
			</p:pro>

			<p:pro field="channelType">
				<p:option type="productSailType" empty="true" />
			</p:pro>

			<p:pro field="managerType">
				<p:option type="pubManagerType" empty="true" />
			</p:pro>

			<p:pro field="cultrueType">
				<p:option type="204" empty="true" />
			</p:pro>

			<p:pro field="discountRate">
				<p:option type="205" empty="true" />
			</p:pro>
			
			<p:pro field="priceRange">
				<p:option type="206" empty="true" />
			</p:pro>
			
			<p:pro field="salePeriod">
				<p:option type="207" empty="true" />
			</p:pro>

			<p:pro field="saleTarget">
				<p:option type="208" empty="true" />
			</p:pro>

			<p:pro field="currencyType">
				<p:option type="209" empty="true" />
			</p:pro>

			<p:pro field="secondhandGoods">
				<p:option type="210" empty="true" />
			</p:pro>			

			<p:pro field="style">
				<p:option type="211" empty="true" />
			</p:pro>	

			<p:pro field="commissionBeginDate" />
			
			<p:pro field="commissionEndDate" />

			<p:pro field="gold"/>
			<p:pro field="silver"/>

			<p:pro field="country">
				<p:option type="212" empty="true" />
			</p:pro>
			
			<p:pro field="productManagerId" innerString="readonly=true" value="${bean.productManagerName}">
				<input type="button" value="&nbsp;...&nbsp;" name="qout" id="qout"
					class="button_class" onclick="selectStaffer()">&nbsp;&nbsp;
			</p:pro>
						
			<p:pro field="nature" innerString="onchange='natureChange()'" cell="0">
				<p:option type="natureType" empty="true" />
			</p:pro>
			
			<p:pro field="inputInvoice" innerString="style='width: 300px'">
				<option value="">--</option>
			    <c:forEach items="${invoiceList1}" var="item">
			     <option value="${item.id}">${item.fullName}</option>
			    </c:forEach>
			</p:pro>
			
			<p:pro field="sailInvoice" innerString="style='width: 300px'">
				<option value="">--</option>
			    <c:forEach items="${invoiceList2}" var="item">
			     <option value="${item.id}">${item.fullName}</option>
			    </c:forEach>
			</p:pro>
			
			<p:pro field="refProductId" innerString="readonly=true size=60" cell="0">
				<input type="button" value="&nbsp;...&nbsp;" name="qout_ref" id="qout_ref"
					class="button_class" onclick="selectProduct()">&nbsp;&nbsp;
			</p:pro>
						
			<p:cell title="事业部" end="true">
				<textarea name='industryIdsName'
					head='事业部' id='industryIdsName' rows=2 cols=80 readonly=true
					oncheck="notNone;"><c:out value="${bean.industryIdsName}" /></textarea> <font color='#FF0000'>*</font> <input
					type="button" value="&nbsp;...&nbsp;" name="qout" id="qout"
					class="button_class" onclick="selectPrincipalship()">&nbsp;&nbsp;
			</p:cell>

			<p:pro field="description" cell="0" innerString="rows=2 cols=80" />

		</p:table>
	</p:subBody>

	<p:title>
		<td class="caption"><strong>产品人员属性：</strong></td>
	</p:title>

	<tr id="productVSStaffer_tr">
		<td colspan='2' align='center'>
		<table width="98%" border="0" cellpadding="0" cellspacing="0"
			class="border">
			<tr>
				<td>
				<table width="100%" border="0" cellspacing='1' id="tables_VS">
					<tr align="center" class="content0">
						<td width="20%" align="center">人员角色</td>
						<td width="20%" align="center">提成比例(‰)</td>
						<td width="50%" align="center">人员</td>
						<td width="5%" align="left"><input type="button"
							accesskey="A" value="增加(A)" class="button_class"
							onclick="addVSTr()"></td>
					</tr>
					
					<c:forEach items="${itemList1}" var="item" varStatus="vs">
                        <tr class='${vs.index % 2 == 0 ? "content1" : "content2"}'>	
							<td width="20%" align="center">
								<select name="stafferRole" oncheck="notNone" class="select_class" values="${item.stafferRole}" style="width: 100%;">
									<p:option type="stafferRole" empty="true"></p:option>
								</select>							
							</td>
							<td width="20%" align="center">
								<input type="text"
									style="width: 100%; cursor: pointer;" value="${item.commissionRatio}"
									name="commissionRatio">							
							</td>
							<td width="50%" align="center"><input type="text"
								style="width: 100%; cursor: pointer;" readonly="readonly" value="${item.stafferName}"
								oncheck="notNone" name="stafferName" onclick="selectStaffer1(this)">
							<input type="hidden" name="stafferId" value="${item.stafferId}"></td>
							<td width="5%" align="center"><input type=button
								value="&nbsp;删 除&nbsp;" class=button_class onclick="removeTr(this)"></td>

                           </tr>
                    </c:forEach>
					
				</table>
				</td>
			</tr>
		</table>

		</td>
	</tr>

	<p:line flag="1" />

	<p:button leftWidth="100%" rightWidth="0%">
		<div align="right"><input type="button" class="button_class"
			id="ok_s" style="cursor: pointer" value="&nbsp;&nbsp;保存&nbsp;&nbsp;"
			onclick="saveBean()">&nbsp;&nbsp;<input type="button"
			class="button_class" id="ok_b" style="cursor: pointer"
			value="&nbsp;&nbsp;提交&nbsp;&nbsp;" onclick="addBean()"></div>
	</p:button>

	<p:message />
</p:body>
</form>

<table>
	
	<tr class="content1" id="trCopy1" style="display: none;">
		<td width="20%" align="center">			
			<select name="stafferRole" oncheck="notNone" class="select_class" style="width: 100%;">
				<p:option type="stafferRole" empty="true"></p:option>
			</select>
		</td>
		<td width="20%" align="center"><input type="text"
			style="width: 100%" name="commissionRatio"></td>
		<td width="50%" align="center"><input type="text"
			style="width: 100%" oncheck="notNone" name="stafferName" readonly="readonly" onclick="selectStaffer1(this)">
			<input type="hidden" name="stafferId" value=""></td>
		<td width="5%" align="center"><input type=button
			value="&nbsp;删 除&nbsp;" class=button_class onclick="removeTr(this)"></td>
	</tr>
</table>

</body>
</html>

