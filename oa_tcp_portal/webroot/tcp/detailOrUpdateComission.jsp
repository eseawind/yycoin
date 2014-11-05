<%@ page contentType="text/html;charset=UTF-8" language="java"
	errorPage="../common/error.jsp"%>
<%@include file="../common/common.jsp"%>
<html>
<head>
<p:link title="提成特批申请" guid="true"/>
<script language="JavaScript" src="../js/common.js"></script>
<script language="JavaScript" src="../js/compatible.js"></script>
<script language="JavaScript" src="../js/math.js"></script>
<script language="JavaScript" src="../js/public.js"></script>
<script language="JavaScript" src="../js/JCheck.js"></script>
<script language="JavaScript" src="../js/json.js"></script>
<script language="JavaScript" src="../tcp_js/travelApply.js"></script>

<script type="text/javascript" src="../tcp_js/jquery-calendar.js"></script>
<!--<script type="text/javascript" src="../tcp_js/jquery-calendar2.js"></script>-->
<link rel="stylesheet" type="text/css" href="../tcp_js/jquery-calendar.css">
<link rel="stylesheet" type="text/css" href="../tcp_js/styles.css">

<script language="javascript">

$(document).ready(function (){


    $("#beginDate").calendar();
    $("#endDate").calendar();

});

function initSelectNext1(status)
{
	if ($$('stype') == '')
	{
		alert('请选择系列');
		
		return false;
	}
	
	if ($$('stype') != '2')
	{
		selectNext('group', 'A220110406000200001',status)
	}
	else
	{
		selectNext('group', 'A220110406000200004',status)
	}
}

function addBean(opr)
{
    $("input[name='oprType']").val(opr);
    submit('确定提交申请?');
}

function load()
{
	showFlowLogTr();
	$v('tr_att_more', false);
	
}
var showTr = false;
function showFlowLogTr()
{
    $v('flowLogTr', showTr);
    
    showTr = !showTr;
}

function purposeChange(obj)
{
	if(obj.value==31)
		{
			$("#vocationType").show();
		}
	else
	{
		$("#vocationType").hide();
	}
}

function selectDeparts()
{
    window.common.modal('../admin/pop.do?method=selectDeparts&load=1&selectMode=0');
}

function getDeparts(oos)
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
            ids = ids + oos[i].value + ',';
            names = names + oos[i].pname + ',' ;
        }        
    }

    $O('departid').value = ids;
    $O('departs').value = names;
    
}

function selectProducts()
{
    window.common.modal('../product/product.do?method=selectProducts&load=1&selectMode=0');
}

function getProducts(oos)
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
            ids = ids + oos[i].value + ',';
            names = names + oos[i].pname + ',' ;
        }        
    }

    $O('applyProduct').value = ids;
    $O('products').value = names;
    
}
</script>
</head>

<body class="body_class" onload="load()">
<form name="formEntry" action="../tcp/apply.do?method=addOrUpdateCommission" method="post">
<input type="hidden" name="oprType" value="0"> 
<input type="hidden" name="addOrUpdate" value="1"> 
<input type="hidden" name="id" value="${bean.id}"> 
<input type="hidden" name="processId" value=""> 
<input type="hidden" name="type" value="0"> 
<input type="hidden" name="stafferId" value="${staffervo.id}"> 
<input type="hidden" name="departmentId" value="${staffervo.principalshipId}"> 
<input type="hidden" name="departid" value="${bean.departid}"> 
<input type="hidden" name="applyProduct" value="${bean.applyProduct}"> 
<input type="hidden" name="stype" value="${staffervo.otype}">
<input type="hidden" name="applyer" value="${bean.applyer }" />

<p:navigation height="22">
	<td width="550" class="navigation">提成特批申请</td>
	<td width="85"></td>
</p:navigation> <br>

<p:body width="100%">

	<p:title>
		<td class="caption">
		 <strong>提成特批申请</strong>
		</td>
	</p:title>

	<p:line flag="0" />

	<p:subBody width="98%">
	    <p:class value="com.china.center.oa.tcp.bean.CommissionApplyBean" opr="1"/>
	    <c:if test="${update=='1' }">
		<p:table cells="2">
			<p:pro field="applyobj"  innerString="onchange='purposeChange(this)'">
                <p:option type="selApplyobj"></p:option>
            </p:pro>
            <p:pro field="applyer" value="${staffervo.name}"/>
            <p:cell title="申请部门" end="true" >
				<textarea name='departs'
					head='申请部门' id='departs' rows=2 cols=40 readonly=true
					>${bean.departName }</textarea> <input
					type="button" value="&nbsp;...&nbsp;" name="qout" id="qout"
					class="button_class" onclick="selectDeparts()">&nbsp;&nbsp;
			</p:cell>
			<p:cell title="申请产品" end="true">
				<textarea name='products'
					head='申请产品' id='products' rows=2 cols=40 readonly=true
					>${bean.productName }</textarea> <input
					type="button" value="&nbsp;...&nbsp;" name="qout" id="qout"
					class="button_class" onclick="selectProducts()">&nbsp;&nbsp;
			</p:cell>
			
          	<p:pro field="applyType" >
                <p:option type="216"></p:option>
            </p:pro>
            <p:pro field="secondType" >
                <p:option type="selApplyType"></p:option>
            </p:pro>
            <p:pro field="startTime"/>
            <p:pro field="endTime"/>
            <p:pro field="changeType"  >
                <p:option type="selChangeType"></p:option>
            </p:pro>
            <p:pro field="moneys"  />
            <p:pro field="detail"  cell="0" innerString="rows=4 cols=55"/>
        </p:table>
        

    <p:line flag="0" />
    
		<p:button leftWidth="98%" rightWidth="0%">
			<div align="right">
			<input type="button" class="button_class" id="sub_b1"
	            value="&nbsp;&nbsp;取消&nbsp;&nbsp;" onclick="javascript:window.history.go(-1);">
			  &nbsp;&nbsp;
			  <input type="button" class="button_class" id="sub_b2"
	            value="&nbsp;&nbsp;提 交&nbsp;&nbsp;" onclick="addBean(1)">
	        </div>
		</p:button>
        </c:if>
        <c:if test="${update=='2' }">
        <p:class value="com.china.center.oa.tcp.bean.CommissionApplyBean" opr="1"/>
        	<p:table cells="2">
			<p:cell title="处理流程" width="8" end="true">
            ${desc}
            </p:cell>
			<p:pro field="applyobj"  innerString="onchange='purposeChange(this)' disabled='disabled'">
                <p:option type="selApplyobj"></p:option>
            </p:pro>
            <p:pro field="applyer" value="${staffervo.name}"/>
            <p:cell title="申请部门" end="true" >
				<textarea name='departs'
					head='申请部门' id='departs' rows=2 cols=40 readonly=true
					>${bean.departName }</textarea>
			</p:cell>
			<p:cell title="申请产品" end="true">
				<textarea name='products'
					head='申请产品' id='products' rows=2 cols=40 readonly=true
					>${bean.productName }</textarea> 
			</p:cell>
			
         	<p:pro field="applyType" >
                <p:option type="216"></p:option>
            </p:pro>
            <p:pro field="secondType" >
                <p:option type="selApplyType"></p:option>
            </p:pro>
            <p:pro field="startTime" innerString="disabled='disabled'"/>
            <p:pro field="endTime" innerString="disabled='disabled'"/>
            <p:pro field="changeType"  innerString="disabled='disabled'">
                <p:option type="selChangeType"></p:option>
            </p:pro>
            <p:pro field="moneys"  innerString="disabled='disabled'"/>
            <p:pro field="detail"  cell="0" innerString="rows=4 cols=55 disabled='disabled'"/>
             <p:pro field="logTime"  innerString="disabled='disabled'"/>
        </p:table>
        
         <p:line flag="0" />
         
	     <p:title>
        <td class="caption">
         <strong><span style="cursor: pointer;" onclick="showFlowLogTr()">流程日志(点击查看)</span></strong>
        </td>
    </p:title>

    <p:line flag="0" />
    
    <tr id="flowLogTr">
        <td colspan='2' align='center'>
        <table width="98%" border="0" cellpadding="0" cellspacing="0"
            class="border">
            <tr>
                <td>
                <table width="100%" border="0" cellspacing='1' id="tables">
                    <tr align="center" class="content0">
                        <td width="10%" align="center">审批人</td>
                        <td width="10%" align="center">审批动作</td>
                        <td width="10%" align="center">前状态</td>
                        <td width="10%" align="center">后状态</td>
                        <td width="45%" align="center">意见</td>
                        <td width="15%" align="center">时间</td>
                    </tr>

                    <c:forEach items="${logList}" var="item" varStatus="vs">
                        <tr class='${vs.index % 2 == 0 ? "content1" : "content2"}'>
                            <td align="center">${item.actor}</td>

                            <td  align="center">${item.oprModeName}</td>

                            <td  align="center">${item.preStatusName}</td>

                            <td  align="center">${item.afterStatusName}</td>

                            <td  align="center">${item.description}</td>

                            <td  align="center">${item.logTime}</td>

                        </tr>
                    </c:forEach>
                </table>
                </td>
            </tr>
        </table>

        </td>
    </tr>
        
        <p:button leftWidth="98%" rightWidth="0%">
			<div align="right">
			<input type="button" class="button_class" id="sub_b1"
	            value="&nbsp;&nbsp;返回&nbsp;&nbsp;" onclick="javascript:window.history.go(-1);">
	        </div>
		</p:button>
        </c:if> 
       
	</p:subBody>

	
	<p:message2/>
</p:body>
</form>
<br/>
<br/>
<br/>
<br/>
<br/>
<br/>
<br/>
<br/>
<br/>
<br/>
<br/>
<table>
    <tr class="content1" id="trCopy" style="display: none;">
         <td align="left">
         <input type=text name = 'i_beginDate'  id = 'i_beginDate'  oncheck = ""  value = ''  readonly=readonly class='input_class' size = '20' ><img src='../images/calendar.gif' style='cursor: pointer' title='请选择时间' align='top' onclick='return calDateInner(this, "i_beginDate");' height='20px' width='20px'/>
         </td>
         <td align="left">
         <input type=text name = 'i_endDate'  id = 'i_endDate'  oncheck = ""  value = ''  readonly=readonly class='input_class' size = '20' ><img src='../images/calendar.gif' style='cursor: pointer' title='请选择时间' align='top' onclick='return calDateInner(this, "i_endDate");' height='20px' width='20px'/>
         </td>
         <td align="left">
         <select name="i_feeItem" class="select_class" style="width: 100%;" oncheck="notNone">
	         <option value="">--</option>
	         <c:forEach var="item" items="${feeItemList}">
	             <option value="${item.id}">${item.name}</option>
	         </c:forEach>
         </select>
         </td>
         
         <td align="left"><input type="text" style="width: 100%"
                    name="i_moneys" value="" oncheck="notNone;isFloat3"></td>
         <td align="left">
         <textarea name="i_description" rows="3" style="width: 100%"></textarea>
         </td>
        <td width="5%" align="center"><input type=button
            value="&nbsp;删 除&nbsp;" class=button_class onclick="removeTr(this)"></td>
    </tr>
    
    <tr class="content1" id="trCopy_pay" style="display: none;">
         <td align="left">
         <select name="p_receiveType" class="select_class" style="width: 100%;" oncheck="notNone">
            <p:option type="travelApplyReceiveType" empty="true"></p:option>
         </select>
         </td>
         
         <td align="left"><input type="text" style="width: 100%"
                    name="p_bank" value="" >
         </td>
         
         <td align="left">
         <input type="text" style="width: 100%"
                    name="p_userName" value="" >
         </td>
         
         <td align="left">
         <input type="text" style="width: 100%"
                    name="p_bankNo" value="" >
         </td>
         
         <td align="left">
         <input type="text" style="width: 100%"
                    name="p_moneys" value="" oncheck="notNone;isFloat3">
         </td>
         
         <td align="left">
         <textarea name="p_description" rows="3" style="width: 100%"></textarea>
         </td>
        <td width="5%" align="center"><input type=button name="pay_del_bu"
            value="&nbsp;删 除&nbsp;" class=button_class onclick="removeTr(this)"></td>
    </tr>
    
    <%@include file="share_tr.jsp"%>
    
</table>
</body>
</html>

