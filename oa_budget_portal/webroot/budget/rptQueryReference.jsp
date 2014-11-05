<%@ page contentType="text/html;charset=UTF-8" language="java"
    errorPage="../common/error.jsp"%>
<%@include file="../common/common.jsp"%>
<html>
<head>
<p:link title="预算相关" />
<base target="_self">
<script language="JavaScript" src="../js/common.js"></script>
<script language="JavaScript" src="../js/public.js"></script>
<script language="javascript">
function querys()
{
}

function sures()
{

}

function closes()
{
    opener = null;
    window.close();
}

function load()
{
    loadForm();
}


function closesd()
{
    var opener = window.common.opener();
    
    opener = null;
    window.close();
}

</script>

</head>
<body class="body_class" onload="load()">
<form name="formEntry" action="../admin/pop.do" method="post"><input
    type="hidden" name="method" value="rptQueryStaffer"><input
    type="hidden" value="1" name="load"><input
    type="hidden" value="${selectMode}" name="selectMode"> <p:navigation
    height="22">
    <td width="550" class="navigation">预算相关</td>
    <td width="85"></td>
</p:navigation> <br>

<p:body width="100%">
    <p:title>
        <td class="caption"><strong>【${pbean.name}】预算相关列表：</strong></td>
    </p:title>

    <p:line flag="0" />

    <p:subBody width="98%">
        <table width="100%" align="center" cellspacing='1' class="table0"
            id="result">
            <tr align=center class="content0">
                <td align="center"><strong>预算项</strong></td>
                
                <c:if test="${!unit}">
                <td align="center" width="40%"><strong>子预算</strong></td>
                </c:if>
                <td align="center"><strong>预算金额</strong></td>
                <c:if test="${!unit}">
                <td align="center"><strong>未分配预算</strong></td>
                </c:if>
                <c:if test="${unit}">
                <td align="center"><strong>剩余预算</strong></td>
                </c:if>
                <td align="center"><strong>已经使用</strong></td>
                <c:if test="${!unit}">
                <td align="center"><strong>剩余预算</strong></td>
                </c:if>
            </tr>

            <c:forEach items="${items}" var="item" varStatus="vs">
                <tr class="${vs.index % 2 == 0 ? 'content1' : 'content2'}">
                    <td align="center" onclick="hrefAndSelect(this)">${item.feeItemName}</td>
                    <c:if test="${!unit}">
                    <td align="center" onclick="hrefAndSelect(this)">${item.subDescription}</td>
                    </c:if>
                    <td align="center" onclick="hrefAndSelect(this)">${my:formatNum(item.budget)}</td>
                    <td align="center" onclick="hrefAndSelect(this)">${item.snoAssignMonery}</td>
                    <td align="center" onclick="hrefAndSelect(this)">${item.suseMonery}</td>
                    <c:if test="${!unit}">
                        <td align="center" onclick="hrefAndSelect(this)">${item.sremainMonery}</td>
                    </c:if>
                </tr>
            </c:forEach>
        </table>
    </p:subBody>

    <p:line flag="1" />

    <p:button leftWidth="100%" rightWidth="0%">
        <div align="right">
        <input type="button" class="button_class"
            value="&nbsp;&nbsp;关 闭&nbsp;&nbsp;" onClick="closesd()" id="clo">&nbsp;&nbsp;
        </div>
    </p:button>

    <p:message />

</p:body></form>
</body>
</html>

