<%@ page contentType="text/html;charset=UTF-8" language="java"
    errorPage="../common/error.jsp"%>
<%@include file="../common/common.jsp"%>
<html>
<head>
<p:link title="任务相关" />
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
    type="hidden" name="method" value=""><input
    type="hidden" value="1" name="load"><input
    type="hidden" value="${selectMode}" name="selectMode"> <p:navigation
    height="22">
    <td width="550" class="navigation">商品明细</td>
    <td width="85"></td>
</p:navigation> <br>

<p:body width="100%">
    <p:title>
        <td class="caption"><strong>商品明细列表：</strong></td>
    </p:title>

    <p:line flag="0" />

    <p:subBody width="98%">
        <table width="100%" align="center" cellspacing='1' class="table0"
            id="result">
            <tr align=center class="content0">
                <td align="center" width="12%"><strong>销售单</strong></td>
                <td align="center" width="40%"><strong>产品</strong></td>
                <td align="center" width="10%"><strong>销售数量</strong></td>
                <td align="center" width="10%"><strong>退货数量</strong></td>
                <td align="center" width="10%"><strong>付款状态</strong></td>
                <td align="center" width="10%"><strong>收货人</strong></td>
                <td align="center"><strong>收货电话</strong></td>
            </tr>

            <c:forEach items="${items}" var="item" varStatus="vs">
                <tr class="${vs.index % 2 == 0 ? 'content1' : 'content2'}">
                    <td align="center" style="color:#008AC0;text-decoration:underline;cursor: pointer;"><a href="../sail/out.do?method=findOut&fow=99&outId=${item.outId}" target="_blank">${item.outId}</a>
                    </td>
                    <td align="center" onclick="hrefAndSelect(this)">${item.productName}</td>
                    <td align="center" onclick="hrefAndSelect(this)">${item.amount+item.hasBack}</td>
                    <td align="center" onclick="hrefAndSelect(this)">${item.hasBack}</td>
                    <td align="center" onclick="hrefAndSelect(this)">${my:get('outPay', item.pay)}</td>
                    <td align="center" onclick="hrefAndSelect(this)">${item.receiver}</td>
                    <td align="center" onclick="hrefAndSelect(this)">${item.mobile}</td>
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

