<%@ page contentType="text/html;charset=UTF-8" language="java"
	errorPage="../common/error.jsp"%>
<%@include file="../common/common.jsp"%>
<html>
<head>
<p:link title="查询条件设置" cal="true"/>
<script language="JavaScript" src="../js/common.js"></script>
<script language="JavaScript" src="../js/public.js"></script>
<script language="JavaScript" src="../js/cnchina.js"></script>
<script language="javascript">
function querySure()
{
    var opener = window.common.opener();
    
    var par = {};
    
    var inputs = document.getElementsByTagName('input');
    
    for(var i = 0; i < inputs.length; i++)
    {
        if (!isNoneInCommon(inputs[i].value) && inputs[i].type != 'button')
        {
            par[inputs[i].name] = inputs[i].value;
        }
    }
    
    var selects = document.getElementsByTagName('select');
    
    for(var i = 0; i < selects.length; i++)
    {
        var str = $$(selects[i].name);
        
        if (!isNoneInCommon(str))
        {
            par[selects[i].name] = str;
        }
    }

    opener.commonQuery(par);
    
    closes();
}

function resets()
{
    var inputs = document.getElementsByTagName('input');
    
    for(var i = 0; i < inputs.length; i++)
    {
        if (!inputs[i].readonly && inputs[i].type != 'hidden' && inputs[i].type != 'button')
        {
            inputs[i].value = '';
        }
    }
    
    var selects = document.getElementsByTagName('select');
    
    for(var i = 0; i < selects.length; i++)
    {
        if (!selects[i].readonly)
        {
            setSelectIndex(selects[i], 0);
        }
    }
}

function resets1()
{
    resets();
    
    querySure();
}


function closes()
{
    var opener = window.common.opener();
    
    opener.document.body.focus();
    
    opener = null;
    
    window.close();
}

function enterKeyPress(fun)
{
    if (window.common.getEvent().keyCode == 13)
    {
        fun.apply(null);
    }
    
    if (window.common.getEvent().keyCode == 27)
    {
        closes();
    }
}

function loads()
{
    loadForm();
    
    var inputs = document.getElementsByTagName('input');
    
    for(var i = 0; i < inputs.length; i++)
    {
        if (inputs[i].frister && inputs[i].frister == '1')
        {
            $f(inputs[i]);
        }
    }
}

document.onkeydown = function ()
{
    if (document.body.onkeydown == null)
    {
        var event = window.common.getEvent();
        if (event.keyCode == 27)
        {
            closes();
            return false;
        }
    }
}
</script>

</head>
<body class="body_class" onload="loads()">
<input type="hidden" name="load" value="1">
<c:forEach items="${query.conditions}" var="item">
<input type="hidden" name="hidden_query_${item.name}" value="${item.assistant}">
</c:forEach>
<p:navigation height="22">
	<td width="550" class="navigation"><span>查询条件设置</span></td>
	<td width="85"></td>
</p:navigation> <br>

<p:body width="98%">
	<p:subBody width="100%">
		<p:table cells="1">
		    <c:set var="pkey" value="${queryName}_pmap"></c:set>
		    <c:set var="ppmap" value="${sessionScope[pkey]}"></c:set>
			<c:forEach items="${query.conditions}" var="item" varStatus="vs">
				<c:if test="${item.type == 'text'}">
					<p:cell title="${item.caption}">
						<input type="text" id="${item.name}" name="${item.name}" size=50 onkeypress="enterKeyPress(querySure)" frister="${vs.first ? '1' : '0'}"
							${item.inner} value="${ppmap[item.name]}">
					</p:cell>
				</c:if>

				<c:if test="${item.type == 'select'}">
					<p:cell title="${item.caption}">
						<select id="${item.name}" name="${item.name}" quick=true
							${item.inner} class="select_class" values="${ppmap[item.name]}">
							<option value="">--</option>
							<c:forEach items="${selectMap[item.option]}" var="subItem">
								<option value="${subItem.id}">${subItem.name}</option>
							</c:forEach>
						</select>
						<input type="hidden" name="hidden_query_${item.name}" value="${item.assistant}">
					</p:cell>
				</c:if>
				
				<c:if test="${item.type == 'date'}">
                    <p:cell title="${item.caption}">
                        <p:plugin innerString="${item.inner}" type="0" name="${item.name}" value="${ppmap[item.name]}"></p:plugin>
                    </p:cell>
                </c:if>
                
                <c:if test="${item.type == 'datetime'}">
                    <p:cell title="${item.caption}">
                        <p:plugin innerString="${item.inner}" type="1" name="${item.name}" value="${ppmap[item.name]}"></p:plugin>
                    </p:cell>
                </c:if>
                
			</c:forEach>
    
		</p:table>
	</p:subBody>

	<p:line flag="1" />

	<p:button leftWidth="100%" rightWidth="0%">
		<div align="right"><input type="button" class="button_class"
			id="ok_b" style="cursor: pointer" value="&nbsp;&nbsp;查 询&nbsp;&nbsp;"
			onclick="querySure()">&nbsp;&nbsp;<input type="button"
            class="button_class" style="cursor: pointer"
            value="&nbsp;&nbsp;重 置&nbsp;&nbsp;" id="re_b" onclick="resets()">&nbsp;&nbsp;<input type="button"
			class="button_class" style="cursor: pointer"
			value="&nbsp;&nbsp;重置查询&nbsp;&nbsp;" id="re_b2" onclick="resets1()">&nbsp;&nbsp;<input
			type="button" class="button_class" style="cursor: pointer"
			value="&nbsp;&nbsp;关 闭&nbsp;&nbsp;" id="close_b" onclick="closes()"></div>
	</p:button>
</p:body>
</body>
</html>

