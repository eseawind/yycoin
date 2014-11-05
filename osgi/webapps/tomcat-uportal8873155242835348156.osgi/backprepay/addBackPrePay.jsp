<%@ page contentType="text/html;charset=UTF-8" language="java"
	errorPage="../common/error.jsp"%>
<%@include file="../common/common.jsp"%>
<html>
<head>
<p:link title="申请预收退款" guid="true"/>
<script language="JavaScript" src="../js/common.js"></script>
<script language="JavaScript" src="../js/compatible.js"></script>
<script language="JavaScript" src="../js/math.js"></script>
<script language="JavaScript" src="../js/public.js"></script>
<script language="JavaScript" src="../js/JCheck.js"></script>
<script language="JavaScript" src="../js/json.js"></script>
<script language="JavaScript" src="../tcp_js/backPrePay.js"></script>
<script language="javascript">

function addBean(opr)
{
    $("input[name='oprType']").val(opr);
    
    if ("0" == opr)
    {
        $O('processer').oncheck = '';
    }
    else
    {
        $O('processer').oncheck = 'notNone';
    }
    
    submit('确定申请预收退款', null, checks);
}

function load()
{
	addPayTr();
}

function selectCus()
{
    window.common.modal('../client/client.do?method=rptQuerySelfClient&stafferId=${user.stafferId}&load=1');
}

function getCustomer(obj)
{
    $O('customerId').value = obj.value;
    $O('customerName').value = obj.pname;
}

function selectInBill()
{
	if ($$('customerId') == '') {
		alert('请先选择客户');
		return;
	}
	
    window.common.modal('../finance/bill.do?method=rptQueryInBill&selectMode=1&load=1&customerId=' + $$('customerId'));
}

function getInBill(oos)
{
	var item = oos[0];
	
    $O('billId').value = item.value;
    $O('paymentId').value = item.ppaymentid;
    $O('customerAccount').value = item.pcustomeraccount;
    $O('bankId').value = item.pbankid;
    $O('bankName').value = item.pbankName;
    $O('total').value = formatNum(item.pmoney, 2);
}

</script>
</head>

<body class="body_class" onload="load()">
<form name="formEntry" action="../tcp/backprepay.do?method=addOrUpdateBackPrePay" enctype="multipart/form-data" method="post">
<input type="hidden" name="oprType" value="0"> 
<input type="hidden" name="addOrUpdate" value="0"> 
<input type="hidden" name="processId" value=""> 
<input type="hidden" name="customerId" value="">
<input type="hidden" name="type" value="23">
<input type="hidden" name="stafferId" value="${g_stafferBean.id}"> 
<input type="hidden" name="departmentId" value="${g_stafferBean.principalshipId}"> 
<input type="hidden" name="stype" value="${g_stafferBean.otype}">
<input type="hidden" name="bankId" value="">

<p:navigation height="22">
	<td width="550" class="navigation">新增申请预收退款</td>
	<td width="85"></td>
</p:navigation> <br>

<p:body width="100%">

	<p:title>
		<td class="caption">
		 <strong>申请预收退款</strong>
		</td>
	</p:title>

	<p:line flag="0" />

	<p:subBody width="98%">
	
	    <p:class value="com.china.center.oa.finance.bean.BackPrePayApplyBean" />
	    
		<p:table cells="2">
            <p:pro field="stafferId" value="${g_stafferBean.name}"/>
            <p:pro field="departmentId" value="${g_stafferBean.principalshipName}"/>            
            
            <p:pro field="name" cell="0" innerString="size=60"/>
            
            <p:pro field="customerName" innerString="readonly='readonly' size=50" cell="0">
                  <input type="button" value="&nbsp;...&nbsp;" name="qout" id="qout"
                    class="button_class" onclick="selectCus()">&nbsp;
            </p:pro>
            
            <p:pro field="payType" cell="0">
                <p:option type="outbillPayType" empty="true"></p:option>
            </p:pro>
            
            <p:pro field="receiver" cell="0"/>
            
            <p:pro field="receiveBank" innerString="size=40" cell="0"/>
            
            <p:pro field="receiveAccount" innerString="size=40" cell="0"/>
            
            <p:pro field="billId">
            	<input type="button" value="&nbsp;...&nbsp;" name="qout1" id="qout1"
                    class="button_class" onclick="selectInBill()">&nbsp;
            </p:pro>
            
            <p:pro field="paymentId"/>
            
            <p:pro field="total" value="0"/>
            
            <%-- <p:pro field="ownMoney" value="0"/> --%>
            
            <p:pro field="backMoney" value="0"/>
            
            <p:pro field="customerAccount" innerString="readonly='readonly' size=60" cell="0"/>
            
            <p:pro field="bankName" innerString="readonly='readonly' size=60" cell="0"/>
            
            <p:pro field="description" cell="0" innerString="rows=4 cols=55" />
            
            <p:cell title="附件" width="8" end="true"><input type="file" name="atts" size="70" >  
            <font color="blue"><b>建议压缩后上传,最大支持10M</b></font>
            </p:cell>
            
        </p:table>
	</p:subBody>
	
    <p:title>
        <td class="caption">
         <strong>提交/审核</strong>
        </td>
    </p:title>

    <p:line flag="0" />
    
    <tr id="sub_main_tr">
        <td colspan='2' align='center'>
        <table width="98%" border="0" cellpadding="0" cellspacing="0"
            class="border">
            <tr>
                <td>
                <table width="100%" border="0" cellspacing='1' id="tables_pay">
                    <tr align="center" class="content0">
                        <td width="15%" align="center">提交到</td>
                        <td align="left">
                        <input type="text" name="processer" readonly="readonly" oncheck="notNone" head="下环处理人"/>&nbsp;
                        <font color=red>*</font>
                        <input type="button" value="&nbsp;...&nbsp;" name="qout" id="qout"
                            class="button_class" onclick="initSelectNext()">&nbsp;&nbsp;
                        </td>
                    </tr>
                </table>
                </td>
            </tr>
        </table>

        </td>
    </tr>
    
    <p:line flag="1" />
    
	<p:button leftWidth="98%" rightWidth="0%">
		<div align="right">
		<input type="button" class="button_class" id="sub_b1"
            value="&nbsp;&nbsp;保存为草稿&nbsp;&nbsp;" onclick="addBean(0)">
		  &nbsp;&nbsp;
		  <input type="button" class="button_class" id="sub_b2"
            value="&nbsp;&nbsp;提 交&nbsp;&nbsp;" onclick="addBean(1)">
        </div>
	</p:button>
	
	<p:message2/>
</p:body>
</form>
</body>
</html>

