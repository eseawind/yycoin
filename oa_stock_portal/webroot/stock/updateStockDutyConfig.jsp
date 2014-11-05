<%@ page contentType="text/html;charset=UTF-8" language="java"
    errorPage="../common/error.jsp"%>
<%@include file="../common/common.jsp"%>
<html>
<head>
<p:link title="处理采购" />
<script language="JavaScript" src="../js/common.js"></script>
<script language="JavaScript" src="../js/JCheck.js"></script>
<script language="JavaScript" src="../js/public.js"></script>
<script language="JavaScript" src="../js/cnchina.js"></script>
<script language="JavaScript" src="../js/jquery/jquery.js"></script>
<script language="JavaScript" src="../js/json.js"></script>
<script language="javascript">

var showJSON = JSON.parse('${showJSON}');

function loadShow()
{
    var json = showJSON;
    
    var pid = $$('dutyId');
    
    var showArr = $("select[name^='showId']") ;
    
    for (var i = 0; i < showArr.length; i++)
    {
        var each = showArr[i];
        
        removeAllItem(each);
        
        for (var j = 0; j < json.length; j++)
        {
            var item = json[j];
            
            if (item.dutyId == pid)
            {
                setOption(each, item.id, item.name);
            }
        }
    }
}

function loadShow2(obj, index)
{
    var json = showJSON;
    
    var pid = $$('dutyId_' + index);
    
    var showArr = $("select[name^='showId_" + index + "']") ;
    
    for (var i = 0; i < showArr.length; i++)
    {
        var each = showArr[i];
        
        removeAllItem(each);
        
        for (var j = 0; j < json.length; j++)
        {
            var item = json[j];
            
            if (item.dutyId == pid)
            {
                setOption(each, item.id, item.name);
            }
        }
    }
}


var cindex = -1;

function addBean()
{
    submit('确定配置产品采购属性?', null, lverify);
}

function lverify()
{
    return true;
}

function load()
{
    loadForm();
    
    var showArr = $("select[name^='dutyId_']") ;
    
    for (var i = 0; i < showArr.length; i++)
    {
        loadShow2(showArr[i], i);
    }
}


function selectProduct(index)
{
    cindex = index;
    
    if ($$('type') == 0)
    {
       window.common.modal(RPT_PRODUCT);
    }
    else
    {
       window.common.modal(RPT_PRODUCT);
    }
}

function getProduct(oos)
{
    
}

function getPriceAskProvider(oo)
{
    if (cindex != -1)
    {
        $O("productName_" + cindex).value = oo.pn;
        $O("productId_" + cindex).value = oo.value;
        $O("price_" + cindex).value = oo.pp;
        $O("netaskId_" + cindex).value = oo.ppid;
    }
}

</script>

</head>
<body class="body_class" onload="load()">
<form name="addApply" action="../stock/stock.do" method="post"><input
    type="hidden" name="method" value="updateStockDutyConfig">
    
    <input type="hidden" name="id" value="${bean.id}"> 
    <input type="hidden" name="owerId" value="${bean.owerId}"> 
    <p:navigation
    height="22">
    <td width="550" class="navigation"><span style="cursor: hand"
        onclick="javascript:history.go(-1)">采购管理</span> &gt;&gt; 采购税务配置</td>
    <td width="85"></td>
</p:navigation> <br>

<p:body width="98%">

    <p:title>
        <td class="caption"><strong>税务配置：</strong></td>
    </p:title>

    <p:line flag="0" />

    <p:subBody width="100%">
        <p:class value="com.china.centet.yongyin.bean.StockBean" opr="1"/>

        <p:table cells="1">
        
            <p:pro field="willDate" innerString="oncheck=range(1)"/>

            <tr>
            <td colspan="2" class="content1">
                <table id="mselect" class='table1'>
                    <c:forEach items="${bean.itemVO}" var="item" varStatus="vs">
                    <tr>
                        <td>
                            <input type="checkbox" name="check_init" value="0" onclick="init()" style="display: none;">产品${vs.index + 1}：<input
                            type="text" name="productName_${vs.index}" value="${item.productName}" size="20" readonly="readonly">&nbsp;
                            参考价格:<input
                            type="text" name="price_${vs.index}" value="${item.prePrice}" size="6" oncheck="notNone;isFloat;" readonly="readonly">&nbsp;
                            数量:<input
                            type="text" name="amount_${vs.index}" value="${item.amount}" size="6" oncheck="notNone;isNumber;" readonly="readonly">&nbsp;
                            纳税实体：
                            <select name="dutyId_${vs.index}" style="WIDTH: 200px;" values="${item.dutyId}" quick=true  oncheck="notNone" onchange="loadShow2(this, '${vs.index}')">
					               <option value="">--</option>
					                <c:forEach items="${dutyList}" var="item3">
					                <option value="${item3.id}">${item3.name}</option>
					                </c:forEach>
                               </select>&nbsp;
                               开发票品名:
                               <select name="showId_${vs.index}" style="WIDTH: 150px;" quick=true values="${item.showId}" oncheck="notNone">
                               </select>&nbsp;
                               发票类型：
                               <select name="invoiceType_${vs.index}" style="WIDTH: 350px;" quick=true values="${item.invoiceType}" > 
                                 <option value="">没有发票</option>
                                <c:forEach items="${invoiceList}" var="item2">
                                <option value="${item2.id}">${item2.fullName}</option>
                                </c:forEach>
                               </select>&nbsp;
                               <input type="hidden" name="productId_${vs.index}" value="${item.productId}">
                               <input type="hidden" name="itemId_${vs.index}" value="${item.id}">
                               <input type="hidden" name="netaskId_${vs.index}" value="${item.priceAskProviderId}">

                            </td>
                    </tr>
                    
                    <tr height="20px">
                    <td></td>
                    </tr>
                    </c:forEach>
                 
                </table>
            </td>
            </tr>

        </p:table>
    </p:subBody>

    <p:line flag="1" />

    <p:button leftWidth="100%" rightWidth="0%">
        <div align="right"><input type="button" class="button_class"
            name="adds" style="cursor: pointer"
            value="&nbsp;&nbsp;确 定&nbsp;&nbsp;" onclick="addBean()">&nbsp;&nbsp;
        <input type="button" class="button_class"
            onclick="javascript:history.go(-1)"
            value="&nbsp;&nbsp;返 回&nbsp;&nbsp;"></div>
    </p:button>

    <p:message2/>
    
</p:body></form>
</body>
</html>

