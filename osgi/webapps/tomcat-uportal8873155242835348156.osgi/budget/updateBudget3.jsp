<%@page contentType="text/html; charset=UTF-8"%>
<%@include file="../common/common.jsp"%>
<html>
<head>
<p:link title="追加预算" />
<script language="JavaScript" src="../js/common.js"></script>
<script language="JavaScript" src="../js/cnchina.js"></script>
<script language="JavaScript" src="../js/public.js"></script>
<script language="JavaScript" src="../js/JCheck.js"></script>
<script language="JavaScript" src="../js/compatible.js"></script>
<script language="JavaScript" src="../budget_js/budget.js"></script>
<script language="javascript">

function addBean(opr)
{
    submit('确定追加预算?', null, check);
}

function clears()
{
    document.getElementById('f_item_budget').value = '';
    document.getElementById('f_item_description').value = '';
}

function load()
{
    setAllReadOnly($O('infoTable'));
    
    $r($O('applyReason'), false)
}

</script>

</head>
<body class="body_class" onload="load()">
<form name="formEntry" action="../budget/budget.do" method="post"><input
    type="hidden" name="method" value="addBudgetApply"><input type="hidden"
    name="budgetId" value="${bean.id}"> <input type="hidden"
    name="parentId" value="${bean.parentId}"><input type="hidden"
    name="oprType" value="${oprType}"><p:navigation height="22">
    <td width="550" class="navigation"><span style="cursor: pointer;"
        onclick="javaScript:window.history.go(-1);">预算管理</span> &gt;&gt; 追加预算</td>
    <td width="85"></td>
</p:navigation> <br>

<p:body width="100%">

    <p:title>
        <td class="caption"><strong>预算基本信息：</strong></td>
    </p:title>

    <p:line flag="0" />

    <p:subBody width="98%">
        <p:class value="com.china.center.oa.budget.vo.BudgetVO" opr="2" />

        <p:table cells="2" id="infoTable">
            <p:pro field="name" cell="2" innerString="size=60"/>
            
             <p:cell title="父预算" end="true">
               ${bean.parentName}
            </p:cell>
            
            <p:pro field="type">
            <p:option type="budgetType"></p:option>
            </p:pro>
            <p:pro field="level">
            <p:option type="budgetLevel"></p:option>
            </p:pro>
            
            <p:pro field="year">
                <option value="">--</option>
                <c:forEach begin="2000" end="2100" var="item">
                    <option value="${item}">${item}</option>
                </c:forEach>
            </p:pro>
            
            <p:pro field="status">
                <p:option type="budgetStatus"></p:option>
            </p:pro>
            
            <p:pro field="beginDate" />
            <p:pro field="endDate" />
            
            <p:pro field="sail" />
            <p:pro field="orgProfit" />
            
            <p:pro field="realProfit" />
            <p:pro field="outSave" />
            
            <p:pro field="outMoney" />
            <p:pro field="inMoney" />
            
            <p:cell title="预算合计">
               ${my:formatNum(bean.total)}
            </p:cell>
            
            <p:cell title="权签人">
               ${bean.signerName}
            </p:cell>
            
            <p:pro field="description" cell="2" innerString="rows=4 cols=60"/>
            
            <p:cells celspan="2" title="追加说明">
            <textarea rows=4 cols=60 name="applyReason" oncheck="notNone" head="追加说明"></textarea><font color="red">*</font>
            </p:cells>
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
                                head="预算金额" value="${my:formatNum(item.budget)}" maxlength="12"
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
            onclick="viewother()">&nbsp;&nbsp;<input type="button"
            class="button_class" id="sub_b" style="cursor: pointer"
            value="&nbsp;&nbsp;追加提交&nbsp;&nbsp;" onclick="addBean(1)"></div>
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

