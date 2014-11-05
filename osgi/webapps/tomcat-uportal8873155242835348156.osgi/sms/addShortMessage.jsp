<%@ page contentType="text/html;charset=UTF-8" language="java"
	errorPage="../common/error.jsp"%>
<%@include file="../common/common.jsp"%>
<html>
<head>
<p:link title="短信发送" link="true" guid="true" cal="true" dialog="true" />
<script language="JavaScript" src="../js/common.js"></script>
<script language="JavaScript" src="../js/JCheck.js"></script>
<script language="JavaScript" src="../js/public.js"></script>
<script language="javascript">
function addBean()
{
	
	submit('确定提交?', null, checkValue);
}

function checkValue()
{   
	if ($$('stype') == '1')
	{
	    var fileName = $O('myFile').value;
        
	    if ("" == fileName)
	    {
	        alert("请输入要导入的文件名");
	        return false;
	    }
	    
	    if (fileName.indexOf('xls') == -1)
	    {
	        alert("只支持XLS文件格式!");
	        return false;
	    }
	}
    
    return true;
}

function radio_click(obj)
{
	if (obj.value == '0')
	{
		$O('stype').value = obj.value;
		
		$v('sendSingleSM', true);
		
		$v('importFile', false);
		
		$v('sendMultiSM', false);
	}

	if (obj.value == '1')
	{
		$O('stype').value = obj.value;
		
		$v('sendSingleSM', false);
		
		$v('importFile', true);
		
		$v('sendMultiSM', false);
	}

	if (obj.value == '2')
	{
		$O('stype').value = obj.value;
		
		$v('sendSingleSM', false);
		
		$v('importFile', false);
		
		$v('sendMultiSM', true);
	}
}

function load()
{
	var radioElements = document.getElementsByName('mode');
	
	for (var i=0; i< radioElements.length; i++)
	{
		if (radioElements[i].value == "0")
		{
			radioElements[i].checked = "checked";

			radio_click(radioElements[i]);
		}
	}
}

function selectCustomer()
{
	window.common.modal('../client/client.do?method=rptQueryMultiClient&load=1&first=1');
}

function getCustomer(oos)
{
	fillMobile(oos);
}

function selectAllCustomer()
{
	window.common.modal('../client/client.do?method=rptQueryMultiClientByStaff&load=1&first=1');
}

function getAllCustomer(oo)
{
	var url = '../client/client.do?method=queryMultiClientByStaff&stafferId='+ oo;

	// ajax
	//alert(url);
	
	$ajax(url, $callBackCust);
}

function $callBackCust(data)
{
	var handphones = data.msg;

	if (data.ret == 0)
		$O('mobile1').value = $O('mobile1').value + ',' + handphones ;
}

function fillMobile(oos)
{
	for (var i = 0; i < oos.length; i++)
    {
		if (oos[i].phandphone == '')
		{
			//alert(oos[i].pname + " 手机号不存在，系统自动忽略");
			continue;
		}

		if (!isNumbers(oos[i].phandphone))
		{
			//alert(oos[i].pname + " 手机号非数字，系统自动忽略");
			continue;
		}

		if (trim(oos[i].phandphone).length != 11)
		{
			//alert(oos[i].pname + " 手机号不是11位，系统自动忽略");
			continue;
		}

		if(oos[i].phandphone.substr(0,1) != '1')
		{
			continue;
		}
		
    	var pv = $O('persons').value;
    	
    	if (pv && pv.indexOf(oos[i].value) != -1)
		{
    		continue;
		}
    	else
    	{
    		$O('persons').value =  $O('persons').value + ',' + oos[i].value ;
    		$O('mobile1').value = $O('mobile1').value + ',' + oos[i].phandphone ;
    	}   
    }
	
	if ($$('persons').substr(0,1) == ',')
	{
		$O('persons').value = $$('persons').substr(1, $$('persons').length - 1)
		$O('mobile1').value = $$('mobile1').substr(1, $$('mobile1').length - 1)
	}

	if ($$('persons').substr($$('persons').length - 1) == ',')
	{
		$O('persons').value = $$('persons').substr(0, $$('persons').length - 1)
		$O('mobile1').value = $$('mobile1').substr(0, $$('mobile1').length - 1)
	}
}

</script>

</head>
<body class="body_class" onload="load()">
<form name="formEntry"
	action="../customerService/shortMessage.do?method=addShortMessage" enctype="multipart/form-data" method="post">
	<input type="hidden" name="stype" value="">
	<input type="hidden" name="persons" value="">
	
	<p:navigation
	height="22">
	<td width="550" class="navigation"><span>短信发送</span></td>
	<td width="85"></td>
</p:navigation> <br>
<p:body width="100%">

	<p:title>
		<td class="caption"><strong>发送短信：</strong></td>
	</p:title>

	<p:line flag="0" />

	<p:subBody width="98%">

		<p:table cells="1">
		
		    <p:cell title="模式">
               <input type="radio" name="mode" value="0" onClick="radio_click(this)"/>单条短信发送&nbsp;&nbsp;
			   <input type="radio" name="mode" value="1" onClick="radio_click(this)"/>批量导入&nbsp;&nbsp;
			   <input type="radio" name="mode" value="2" onClick="radio_click(this)"/>按客户批量发送&nbsp;&nbsp;
            </p:cell>
            
            <p:line flag="0" />
            
            <tr id="sendSingleSM">
		        <td colspan='2' align='center'>
			        <table width="98%" border="0" cellpadding="0" cellspacing="0"
			            class="border">
			            <tr>
			                <td>
			                <table width="100%" border="0" cellspacing='1' id="tables">
			                
			                	<p:cell title="手机号码">
			                		 <input type="text" style="width: 98%" name="mobile0" maxLength="11" value="" ><font color='#FF0000'>*</font>
								</p:cell>
				
								<p:cell title="短信类型">
							         <select name="smType0" class="select_class" style="width: 30%;" >
							            <p:option type="232" empty="true"></p:option>
							         </select><font color='#FF0000'>*</font>
								</p:cell>
			                
			                	<p:cell title="短信内容">
			                		<textarea name="content0" rows="3" style="width: 98%" ></textarea><font color='#FF0000'>*</font>
								</p:cell>
			                
			                </table>
			                </td>
			            </tr>
			        </table>
		
		        </td>
		    </tr>
            
			<tr id="importFile">
		        <td colspan='2' align='center'>
			        <table width="98%" border="0" cellpadding="0" cellspacing="0"
			            class="border">
			            <tr>
			                <td>
			                <table width="100%" border="0" cellspacing='1' id="tables">
			                
			                	<p:cell title="导入模板">
									<a target="_blank"
										href="../admin/down.do?method=downTemplateFileByName&fileName=SMSTemplate.xls">下载销售数据导入模板</a>
								</p:cell>
				
								<p:cell title="短信类型">
							         <select name="smType" class="select_class" style="width: 30%;" >
							            <p:option type="232" empty="true"></p:option>
							         </select><font color='#FF0000'>*</font>
								</p:cell>
				
								<p:cell title="选择文件">
									<input type="file" name="myFile" style="width: 70%;" class="button_class" />
								</p:cell>
			                
			                </table>
			                </td>
			            </tr>
			        </table>
		
		        </td>
		    </tr>
		    
            <tr id="sendMultiSM">
		        <td colspan='2' align='center'>
			        <table width="98%" border="0" cellpadding="0" cellspacing="0"
			            class="border">
			            <tr>
			                <td>
			                <table width="100%" border="0" cellspacing='1' id="tables">
			                	
								<p:cell title="手机号码">
			                		 <textarea name='mobile1'
										head='手机号' id='mobile1' rows=6 cols=80 readonly=true
										></textarea> <font color='#FF0000'>*</font><input
										type="button" value="客户" name="qout" id="qout"
										class="button_class" onclick="selectCustomer()">&nbsp;&nbsp;
										<input
										type="button" value="职员" name="qout1" id="qout1"
										class="button_class" onclick="selectAllCustomer()">&nbsp;&nbsp;
								</p:cell>
								
								<p:cell title="短信类型">
							         <select name="smType1" class="select_class" style="width: 30%;" >
							            <p:option type="232" empty="true"></p:option>
							         </select><font color='#FF0000'>*</font>
								</p:cell>
			                
			                	<p:cell title="短信内容">
			                		<textarea name="content1" rows="3" style="width: 98%" ></textarea><font color='#FF0000'>*</font>
								</p:cell>
			                
			                </table>
			                </td>
			            </tr>
			        </table>
		
		        </td>
		    </tr>
			
		</p:table>
	</p:subBody>

	<p:line flag="1" />

	<p:button leftWidth="100%" rightWidth="0%">
		<div align="right">
			<input type="button" class="button_class"
			style="cursor: pointer" value="&nbsp;&nbsp;确 定&nbsp;&nbsp;"
			onclick="addBean()">
		</div>
	</p:button>

	<p:message2 />
</p:body></form>
</body>
</html>

