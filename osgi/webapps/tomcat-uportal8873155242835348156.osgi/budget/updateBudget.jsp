<%@page contentType="text/html; charset=UTF-8"%>
<%@include file="../common/common.jsp"%>
<html>
<head>
<p:link title="修改预算" guid="true"/>
<script language="JavaScript" src="../js/common.js"></script>
<script language="JavaScript" src="../js/cnchina.js"></script>
<script language="JavaScript" src="../js/public.js"></script>
<script language="JavaScript" src="../js/JCheck.js"></script>
<script language="JavaScript" src="../js/compatible.js"></script>
<script language="JavaScript" src="../budget_js/budget.js"></script>
<script language="javascript">

function addBean(opr)
{
    $O('opr').value = opr;
    
    var title = $O('opr').value == 0 ? "保存" : "提交";
    
	submit('确定'+title+'预算?', null, check);
}

function clears()
{
    document.getElementById('f_item_budget').value = '';
    document.getElementById('f_item_description').value = '';
}

//选择职位
function selectPrin()
{
    window.common.modal('../admin/org.do?method=popOrg');
}

function load()
{
}

function setOrgFromPop(id, name, level)
{
    $O('budgetDepartment').value = id;
    
    $O('budgetDepartmentName').value = name;
}

function selectStaffer()
{
    window.common.modal('../admin/pop.do?method=rptQueryStaffer&load=1&selectMode=1');
}

function getStaffers(oos)
{
    var oo = oos[0];
    
    $O('signerName').value = oo.pname;
    $O('signer').value = oo.value;
}

</script>

</head>
<body class="body_class">
<form name="formEntry" action="../budget/budget.do" method="post"><input
	type="hidden" name="method" value="updateBudget"> <input
	type="hidden" name="opr" value="0"> <input type="hidden"
	name="id" value="${bean.id}"> <input type="hidden"
    name="parentId" value="${bean.parentId}">
<input type="hidden" name="budgetDepartment" value="${bean.budgetDepartment}">
<input type="hidden" name="signer" value="${bean.signer}">
<p:navigation height="22">
	<td width="550" class="navigation"><span style="cursor: pointer;"
		onclick="javaScript:window.history.go(-1);">预算管理</span> &gt;&gt; 修改预算</td>
	<td width="85"></td>
</p:navigation> <br>

<p:body width="100%">

	<p:title>
		<td class="caption"><strong>预算基本信息：</strong></td>
	</p:title>

	<p:line flag="0" />

	<p:subBody width="98%">
		<p:class value="com.china.center.oa.budget.vo.BudgetVO" opr="1" />

		<p:table cells="2">
		    <p:cells title="父级预算" celspan="2">${pbean.name}</p:cells>
		    
			<p:pro field="name" cell="2" innerString="size=60"/>
            
            <p:pro field="type" innerString="readonly=true">
            <p:option type="budgetType"></p:option>
            </p:pro>
            <p:pro field="level" innerString="readonly=true">
            <p:option type="budgetLevel"></p:option>
            </p:pro>
            
            
            <p:pro field="budgetDepartment" value="${bean.budgetDepartmentName}">
                  <input type="button" value="&nbsp;...&nbsp;" name="qout" id="qout"
                    class="button_class" onclick="selectPrin()">&nbsp;
            </p:pro>
            
            <p:pro field="signer" value="${bean.signerName}">
                  <input type="button" value="&nbsp;...&nbsp;" name="qout" id="qout"
                    class="button_class" onclick="selectStaffer()">&nbsp;
            </p:pro>
            
            <p:pro field="year" cell="2" innerString="readonly=true">
                <option value="">--</option>
                <c:forEach begin="2010" end="2100" var="item">
                    <option value="${item}">${item}</option>
                </c:forEach>
            </p:pro>
            
            <c:if test="${bean.level != 2}">
	            <p:pro field="beginDate" />
	            <p:pro field="endDate" />
            </c:if>
            
            <c:if test="${bean.level == 2}">
               <p:cell title="选择月" end="true">
	            <select name="month" values="${month}" class="select_class" oncheck="notNone" readonly=true>
	                    <option value="01">01</option>
	                    <option value="02">02</option>
	                    <option value="03">03</option>
	                    <option value="04">04</option>
	                    <option value="05">05</option>
	                    <option value="06">06</option>
	                    <option value="07">07</option>
	                    <option value="08">08</option>
	                    <option value="09">09</option>
	                    <option value="10">10</option>
	                    <option value="11">11</option>
	                    <option value="12">12</option>
	            </select>
	                <font color="#FF0000">*</font>
            </p:cell>
            </c:if>
            
            <c:if test="${bean.level != 2 && bean.type != 2}">
	            <p:pro field="sail" />
	            <p:pro field="orgProfit" />
	            
	            <p:pro field="realProfit" />
	            <p:pro field="outSave" />
	            
	            <p:pro field="outMoney" />
	            <p:pro field="inMoney" cell="1"/>
            </c:if>
            
            <p:pro field="description" cell="2" innerString="rows=4 cols=60"/>
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
						<td width="20%" align="center">预算项</td>
						<td width="20%" align="center">预算金额</td>
						<td width="50%" align="center">描述</td>
						<td width="10%" align="left"><input type="button" value="增加" accesskey="A"
							class="button_class" onclick="addTr()"></td>
					</tr>

					<c:forEach items="${bean.itemVOs}" var="item" varStatus="vs">
						<tr class="content2" id="trCopy${vs.index}">
							<td width="20%">
							<select name="item_name" id="f_item_name" style="width: 100%;" values="${item.feeItemId}">
                            <c:forEach items="${feeItems}" var="itemSub">
                            <option value="${itemSub.id}">${itemSub.name}</option>
                            </c:forEach>
                            </select>
							</td>

							<td width="20%" align="left"><input type="text"
								id="f_item_budget" style="width: 100%;" oncheck="isFloat3"
								head="预算金额" value="${item.budgetStr}" maxlength="12"
								name="item_budget"></td>

							<td width="50%" align="left"><textarea
								name="item_description" rows="2" style="width: 100%;"
								id="f_item_description" oncheck="maxLength(200)">${item.description}</textarea></td>

							<td width="10%" align="left">
							<input type=button value="删除" class=button_class onclick="removeTr(this)">
							</td>
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
            id="view_1" style="cursor: pointer" value="&nbsp;&nbsp;查看相关预算&nbsp;&nbsp;"
            onclick="viewother()">&nbsp;&nbsp;<input type="button" class="button_class"
			id="ok_b" style="cursor: pointer" value="&nbsp;&nbsp;保 存&nbsp;&nbsp;"
			onclick="addBean(0)">&nbsp;&nbsp;<input type="button"
			class="button_class" id="sub_b" style="cursor: pointer"
			value="&nbsp;&nbsp;提 交&nbsp;&nbsp;" onclick="addBean(1)"></div>
	</p:button>
</p:body></form>
<table style="display: none;">
<tr class="content2" id="trCopy0">
    <td width="20%">
        <select name="item_name" id="f_item_name" quick=true style="width: 100%;" oncheck="notNone" head="预算项">
        <option value="">--</option>
        <c:forEach items="${feeItems}" var="itemSub">
        <option value="${itemSub.id}">${itemSub.name}</option>
        </c:forEach>
        </select>
    </td>

    <td width="20%" align="left"><input type="text" id="f_item_budget" style="width: 100%;" oncheck="isFloat3" head="预算金额"
        maxlength="12" 
        name="item_budget"></td>
        
    <td width="50%" align="left"><textarea name="item_description" rows="2"  style="width: 100%;" id="f_item_description" oncheck="maxLength(200)"></textarea></td>

    <td width="10%" align="left"><input type=button value="删除" class=button_class onclick="removeTr(this)"></td>
</tr>
</table>
</body>
</html>

