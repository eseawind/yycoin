<%@ page contentType="text/html;charset=UTF-8" language="java"
	errorPage="../common/error.jsp"%>
<%@include file="../common/common.jsp"%>

<html>
<head>
<p:link title="维护客户收货地址" />
<script language="JavaScript" src="../js/common.js"></script>
<script language="JavaScript" src="../js/math.js"></script>
<script language="JavaScript" src="../js/public.js"></script>
<script language="JavaScript" src="../js/cnchina.js"></script>
<script language="JavaScript" src="../js/JCheck.js"></script>
<script language="JavaScript" src="../js/compatible.js"></script>
<script language="JavaScript" src="../js/jquery/jquery.js"></script>
<script language="JavaScript" src="../js/json.js"></script>
<script language="JavaScript" src="../js/prototype.js"></script>
<script language="JavaScript" src="../js/buffalo.js"></script>
<script language="javascript">

var END_POINT="${pageContext.request.contextPath}/bfapp";

var buffalo = new Buffalo(END_POINT);

var cmap = window.top.topFrame.cmap;
var pList = window.top.topFrame.pList;

function load()
{
    setOption($O('provinceId'), "", "--");
    for (var i = 0; i < pList.length; i++)
    {
        setOption($O('provinceId'), pList[i].id, pList[i].name);
    }    
    
    var radioElements = document.getElementsByName('shipping');

    var shipping = "${distributionBean.shipping}";

	for (var i=0; i< radioElements.length; i++)
	{
		if (radioElements[i].value == shipping)
		{
			radioElements[i].checked = "checked";

			radio_click(radioElements[i]);
		}
	}
	
	loadForm();

    changes($O('cityId'));
    
    loadForm();

    changeArea();
}

function changes(obj)
{
    removeAllItem($O('cityId'));
    setOption($O('cityId'), "", "--");
    
    if ($$('provinceId') == "")
    {
        return;
    }
    
    var cityList = cmap[$$('provinceId')];
    for (var i = 0; i < cityList.length; i++)
    {
        setOption($O('cityId'), cityList[i].id, cityList[i].name);
    }
    
    removeAllItem($O('areaId'));
    
    setOption($O('areaId'), "", "--");
}

function changeArea()
{
    var id = $$('cityId');
    
    if (id == "")
    {
        return;
    }
    
    buffalo.remoteCall("commonManager.queryAreaByParentId",[id], function(reply) {
                var result = reply.getResult();
                
                var sList = result;
                
                removeAllItem($O('areaId'));
                
                setOption($O('areaId'), "", "--");
                
                for (var i = 0; i < sList.length; i++)
                {
                    setOption($O('areaId'), sList[i].id,  sList[i].name);
                }

                var areaId = '${bean.areaId}';

                if (areaId != '')
                	setSelect($O('areaId'), areaId);
                //loadForm();
        });
}

function selectCustomer()
{
	 window.common.modal('../client/client.do?method=rptQuerySelfClient&stafferId=${user.stafferId}&load=1');
}

function getCustomer(obj)
{
    $O('customerId').value = obj.value;
    $O('customerName').value = obj.pname;
}

function addBean()
{
	submit('确定保存收货信息?', null, check);
}

function check()
{
	 //校验手机与固定电话
    var mobile = $$('mobile');
    var telephone = $$('telephone');
    
    if (trim(mobile).length == 0 || trim(mobile).length != 11)
    {
    	alert('手机的长度须是11位数字.');
    	
    	return false;
    }
    
    if (trim(mobile).indexOf('1') != 0)
    {
    	alert('手机须是1开头');
    	
    	return false;
    }
    
    return true;
}

</script>
</head>
<body class="body_class" onload="load()">
<form name="outForm" action="../customer/address.do?method=updateAddress" method="post">
<input type=hidden name="customerId" value="${bean.customerId}" />
<input type=hidden name="id" value="${bean.id}" />

<p:navigation
	height="22">
	<td width="550" class="navigation">客户管理 &gt;&gt; 修改客户收货信息</td>
				<td width="85"></td>
</p:navigation> <br>

<p:body width="100%">

	<p:title>
		<td class="caption">
		 <strong>收货信息</strong>
		</td>
	</p:title>
	
	<p:line flag="0" />
	
	
	<p:subBody width="98%">

		<p:table cells="2">
		
		<p:cell title="客户" end="true">
				<input type="text" name="customerName" size=80  maxlength="100" value="${bean.customerName}"
							onclick="selectCustomer()" style="cursor: pointer;"
							readonly="readonly" oncheck="notNone;"><font color="#FF0000">*</font>
		</p:cell>
		
		  <tr  class="content1">
		  	<td>送货地址：</td>
		  	<td>选择地址：</td>
		  	<td colspan="2">
			  	<select name="provinceId" quick=true onchange="changes(this)" values="${bean.provinceId}" class="select_class" oncheck="notNone;"></select>&nbsp;&nbsp;
	         	<select name="cityId" quick=true onchange="changeArea()" values="${bean.cityId}" class="select_class" oncheck="notNone;"></select>&nbsp;&nbsp;
	         	<select name="areaId" quick=true values="${bean.areaId}" class="select_class" oncheck="notNone;"></select>
		  	</td>
		  </tr>	
		  
		  <tr  class="content2">
		  	<td></td>
		  	<td>详细地址：</td>
		  	<td colspan="2">
		  		<input type="text" name="address" value="${bean.address}" oncheck="notNone;" size=100 maxlength="300" style="width: 100%;">
		  	</td>
		  </tr>
		  
		  <p:cell title="收 货 人" end="true">
             <input type="text" name="receiver" value="${bean.receiver}" oncheck="notNone;" size=20 maxlength="30">
          </p:cell>
          
          <tr  class="content2">
            <td>手&nbsp;&nbsp;&nbsp;&nbsp;机：</td>
            <td colspan="3">
              <input type="text" name="mobile" value="${bean.mobile}" oncheck="notNone;isMathNumber" size=11 maxlength="11"><font color="#FF0000">*</font>
              &nbsp;&nbsp;固定电话：&nbsp;&nbsp;            
              <input type="text" name="telephone" value="${bean.telephone}" size=20 maxlength="30">
            </td>
          </tr>

		</p:table>

	</p:subBody>
	
	<p:line flag="1" />

	<tr>
		<td width="100%">
		<div align="right">
			<input type="button" class="button_class"
			value="&nbsp;&nbsp;保 存&nbsp;&nbsp;" onClick="addBean()" />&nbsp;&nbsp;
			</div>
		</td>
		<td width="0%"></td>
	</tr>

</p:body>
</form>
</body>
</html>

