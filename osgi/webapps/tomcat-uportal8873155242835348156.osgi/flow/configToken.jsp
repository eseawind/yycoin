<%@ page contentType="text/html;charset=UTF-8" language="java"
	errorPage="../common/error.jsp"%>
<%@include file="../common/common.jsp"%>
<html>
<head>
<p:link title="配置流程环节" />
<script language="JavaScript" src="../js/common.js"></script>
<script language="JavaScript" src="../js/JCheck.js"></script>
<script language="JavaScript" src="../js/public.js"></script>
<script src="../js/jquery/jquery.js"></script>
<script src="../js/jquery.blockUI.js"></script>
<script language="javascript">
var cindex = -1;

var ie6 = $.browser.msie && /MSIE 6.0/.test(navigator.userAgent) && !(/MSIE 8.0/.test(navigator.userAgent)) && !(/MSIE 7.0/.test(navigator.userAgent));

function addBean(opr)
{
    if (ie6)
    {
	    var tot = ${tokens};
	    
	    for (var i = 0; i < tot; i++)
	    {
	        var odiv = $O('dataDiv_' + i);
	        
	        fixcheckbox(odiv);
	    }
    }
    
	submit('确定配置流程环节,并提交流程定义?', null, lverify);
}


var ccmap = {};

function lverify()
{
	var checkArr = document.getElementsByName('check_init');

	var isSelect = 0;

	var imap = {};

	for (var i = 0; i < checkArr.length; i++)
	{
		var obj = checkArr[i];

		var index = obj.value;

		if (obj.checked)
		{
			isSelect++;
			if ($O('tokenName_' + i).value == '')
			{
				alert('环节名称不能为空');
				return false;
			}
			
			if ($O('tokenSelfType_' + i).value == '0')
			{
				if ($O('tokenType_' + i).value == '' )
	            {
	                alert('处理类型不能为空');
	                return false;
	            }
	
				if ($$('tokenType_' + i)  != "2" && $$('tokenType_' + i)  != "3" 
					&& $$('tokenType_' + i)  != "999")
				{
					if ($$('processerId_' + i) == '')
					{
						alert('请选择环节操作者');
						return false;
					}
				}
			}
			else
			{
			    if ($O('tokenSubFlowId_' + i).value == '' )
			    {
			        alert('请选择子流程');
                    
                    return false;
			    }
			}

			if (imap[$O('tokenName_' + i).value] == $O('tokenName_' + i).value)
			{
				alert('环节名称不能重复');
				return false;
			}

			imap[$O('tokenName_' + i).value] = $O('tokenName_' + i).value;
		}
	}

	if (isSelect < 1)
	{
		alert('至少需要两个环节');
		return false;
	}

	return true;
}
function load()
{
	loadForm();

	init();
	
}

var totalTokens = ${tokens};

function init(index)
{
	
}


var urlMap = 
{
"0" : "../admin/pop.do?method=rptQueryStaffer&load=1&selectMode=1",
"1" : "../group/group.do?method=rptQueryGroup&load=1&selectMode=1",
"2" : "",
"999" : ""
};

function selectProcesser(index)
{
	cindex = index;

	if ($$('tokenType_' +cindex) == "")
	{
		alert("请选择处理类型");
		return;
	}

	if ($$('tokenType_' + cindex) == "2")
	{
	    alert('自主插件不需要选择处理者');   
		return;
	}
	
	if ($$('tokenType_' + cindex) == "3")
	{
	    alert('上级主管不需要选择处理者');   
		return;
	}
	
	if ($$('tokenType_' + cindex) == "999")
    {
        alert('结束插件不需要选择处理者');   
        return;
    }
	
	window.common.modal(urlMap[$$('tokenType_' +cindex)]);
}

function selectSubFlow(index)
{
    cindex = index;
    
    window.common.modal("../admin/pop.do?method=rptQuerySubFlow&load=1&selectMode=1");
}

function getSubFlow(oo)
{
    if (cindex != -1 && oo.length > 0)
    {
        var item = oo[0];
        
        $O("tokenSubFlow_" + cindex).value = item.pname;
        
        $O("tokenSubFlowId_" + cindex).value = item.value;
    }
}

function getGroup(oo)
{
	if (cindex != -1 && oo.length > 0)
	{
	    var item = oo[0];
		$O("processer_" + cindex).value = item.pname;
		$O("processerId_" + cindex).value = item.value;
	}
}

function getStaffers(oo)
{
	if (cindex != -1 && oo.length > 0)
	{
	    var item = oo[0];
		$O("processer_" + cindex).value = item.pname;
		$O("processerId_" + cindex).value = item.value;
	}
}

function change(index, obj)
{
	$O('processer_' + index).value = '';
	$O('processerId_' + index).value = '';
}

function changeType(index, obj)
{
    if (obj.value == '0')
    {
        $v('inner1_span_' + index, true);
        
        $v('inner2_span_' + index, false);
    }
    else
    {
        $v('inner1_span_' + index, false);
        
        $v('inner2_span_' + index, true);
    }
}

var newCss = { 
        padding:        0,
        margin:         0,
        width:          '60%', 
        top:            '15%', 
        left:           '20%', 
        textAlign:      'center', 
        color:          '#000', 
        border:         '1px solid #aaa',
        backgroundColor:'#F4F4F4',
        cursor:         ''
    }

function configOther(index)
{
    var odiv = $O('dataDiv_' + index);
    
    if ($O('init_' + index).checked)
    {
        $.blockUI({ message: $('#dataDiv_' + index),css: newCss});
        
        if (ie6)
        {
            fixcheckbox(odiv);
        }
    }
}

function fixcheckbox(odiv)
{
    var inputs = odiv.getElementsByTagName('input');
    
    for (var i = inputs.length - 1; i >= 0; i--)
    {
        if (inputs[i].type == 'checkbox')
        {
            inputs[i].checked = ccmap[inputs[i].name];
        }
    }
}

function savecheckbox(odiv)
{
    var inputs = odiv.getElementsByTagName('input');
    
    for (var i = inputs.length - 1; i >= 0; i--)
    {
        if (inputs[i].type == 'checkbox')
        {
            ccmap[inputs[i].name] = inputs[i].checked;
        }
    }
}

function $close2(index)
{
    if (eCheck([$O('liminal_' + index)]))
    {
        if (ie6)
        {
            var odiv = $O('dataDiv_' + index);
            
            savecheckbox(odiv);
        }
        
        $close();
    }
    else
    {
        $O('liminal_' + index).value = '0.0';
    }
}

</script>

</head>
<body class="body_class" onload="load()">
<form name="formEntry" action="../flow/flow.do" method="post"><input
	type="hidden" name="method" value="configToken">
	<input
	type="hidden" name="id" value="${bean.id}"> <p:navigation
	height="22">
	<td width="550" class="navigation"><span style="cursor: pointer;"
		onclick="javascript:history.go(-1)">流程管理</span> &gt;&gt; 配置流程环节</td>
	<td width="85"></td>
</p:navigation> <br>

<p:body width="100%">

	<p:title>
		<td class="caption"><strong>流程环节定义信息：</strong></td>
	</p:title>

	<p:line flag="0" />

	<p:subBody width="98%">
		<p:table cells="1">
			<p:cells celspan="2" title="帮助说明">
			插件类型分三类<br>
			1、职员插件就是流程进入下一环节后,由具体的某一职员处理<br>
			2、群组插件就是流程进入下一环节后,由群组内的任意一职员处理<br>
			3、自主插件就是流程进入下一环节前,由流程当前处理人决定下一环节处理人<br>
			4、结束插件就是流程进入下一环节是结束环节,此时已经没有职员处理此流程,一般用在最后一个环节
			<br>
			其他：<br>
			自动结束阈值(金额)意思是当申请的实例的金额在阈值内审批通过后自动结束，如果实例的金额大于阈值则进入下一个环节(子流程内无效)
			</p:cells>

			<p:cells id="selects" celspan="2" title="流程环节">
				<table id="mselect" class='table0' cellpadding='0' cellspacing='1'>
					<c:forEach begin="0" end="${tokens - 1}" var="item" varStatus="vs">
					<tr>
						<td><input type="checkbox" name="check_init" value="${item}" onclick="init(${item})" id="init_${item}" 
						checked="checked" style="display: none;">
						环节${item + 1}：
							名称:<input
							type="text" name="tokenName_${item}" value="" size="15">&nbsp;
							环节类型:
							<select class="select_class" name="tokenSelfType_${item}" onchange="changeType(${item}, this)"
							${(vs.first || subFlow) ? 'readonly=true' : ''} >
                                 <p:option type="tokenType"></p:option>
							</select>
							
							<span id="inner1_span_${item}">
							提交类型:
							<select class="select_class" name="tokenType_${item}" onchange="change(${item}, this)" 
							values="${vs.last && !subFlow ? '999' : ''}" 
							${vs.last && !subFlow ? 'readonly=true' : ''}>
								<option value="">--</option>
                                <p:option type="flowPluginType"></p:option>
							</select>&nbsp;
							下环处理者:<input
							type="text" name="processer_${item}" value="" size="15" readonly="readonly"
							style="cursor: pointer;" onclick="selectProcesser(${item})" title="选择处理者">&nbsp;
							<input type="button" value="&nbsp;...&nbsp;" name="qout" title="选择处理者"
							class="button_class" onclick="selectProcesser(${item})">
							<input type="hidden" name="processerId_${item}" value="">&nbsp;&nbsp;
							<span id="span_${item}" style="cursor: pointer;"  onclick="configOther('${item}')">配置高级属性</span>
							</span>
							
							<span id="inner2_span_${item}" style="display: none;">
                                子流程:<input
                            type="text" name="tokenSubFlow_${item}" value="" size="15" readonly="readonly"
                            style="cursor: pointer;" onclick="selectSubFlow(${item})" title="选择子流程">&nbsp;
                            <input type="button" value="&nbsp;...&nbsp;" name="qout" title="选择子流程"
                            class="button_class" onclick="selectSubFlow(${item})">
                            <input type="hidden" name="tokenSubFlowId_${item}" value="">
                            </span>
							</td>
					</tr>
					<tr><td>&nbsp;</td></tr>
					</c:forEach>
					<tr>
					<td><font color="blue">结束环:系统自动生成(如果是子流程结束环节自动进入主流程的下一环节)</font></td>
					</tr>
					
				</table>
			</p:cells>

		</p:table>
	</p:subBody>

	<p:line flag="1" />

	<p:button leftWidth="100%" rightWidth="0%">
		<div align="right">
		<input type="button" class="button_class"
			name="adds" style="cursor: pointer"
			value="&nbsp;&nbsp;提 交&nbsp;&nbsp;" onclick="addBean(1)">&nbsp;&nbsp;
		<input type="button" class="button_class"
			onclick="javascript:history.go(-1)"
			value="&nbsp;&nbsp;返 回&nbsp;&nbsp;"></div>
	</p:button>

	<p:message/>
</p:body>
<c:forEach begin="0" end="${tokens - 1}" var="item" varStatus="vs">
<div id="dataDiv_${item}" style="display:none">
<p align='left'><label><font color=""><b>配置第${item + 1}环节属性</b></font></label></p>
<p><label>&nbsp;</label></p>
<c:if test="${bean.mode == 1}">
<p align='left'>
&nbsp;&nbsp;<font color="blue">模板编辑配置</font>
<br>
<c:forEach items="${templates}" var="itemTemp">
&nbsp;&nbsp;${itemTemp.name}
&nbsp;&nbsp;<input type="checkbox" name="t_v_${item}_${itemTemp.id}" value="1">查看
&nbsp;&nbsp;<input type="checkbox" name="t_e_${item}_${itemTemp.id}" value="1" ${item == 0 ? "checked=checked" : ""}>编辑
<br>
</c:forEach>
</p>
<br>
</c:if>

<p align='left'>
&nbsp;&nbsp;<font color="blue">流程高级操作</font>
<br>
<c:if test="${!subFlow}">
&nbsp;&nbsp;<input type="checkbox" name="operation_${item}_reject" value="1">驳回到上一环节
</c:if>
<c:if test="${subFlow}">
&nbsp;&nbsp;<input type="checkbox" name="operation_${item}_rejectParent" value="1">驳回到主流程的上一环节
</c:if>
&nbsp;&nbsp;<input type="checkbox" name="operation_${item}_rejectAll" value="1">驳回到初始
<c:if test="${!subFlow}">
&nbsp;&nbsp;<input type="checkbox" name="operation_${item}_exends" value="1">异常结束
</c:if>
<br>
&nbsp;&nbsp;自动结束阈值(金额)：<input type="text" name="liminal_${item}" value="0.0" oncheck="isFloat3" head="自动结束阈值" ${item == 0 ? "readonly='readonly'" : ""} ${vs.last ? "readonly='readonly'" : ""}>(如果不使用阈值,默认0.0即可)
</p>
<p><label>&nbsp;</label></p>
<p>
<input type='button' value='&nbsp;&nbsp;确 定&nbsp;&nbsp;' id='div_b_ok1' class='button_class' onclick='$close2(${item})'/>
</p>
<p><label>&nbsp;</label></p>
</div>
</c:forEach>
</form>
</body>
</html>

