<%@ page contentType="text/html;charset=UTF-8" language="java"
	errorPage="../common/error.jsp"%>
<%@include file="../common/common.jsp"%>
<html>
<head>
<p:link title="手机用户审核" link="true" guid="true" cal="true" dialog="true" />
<script language="JavaScript" src="../js/JCheck.js"></script>
<script language="JavaScript" src="../js/common.js"></script>
<script language="JavaScript" src="../js/public.js"></script>
<script language="JavaScript" src="../js/key.js"></script>
<script src="../stockapply_js/scheck.js"></script>
<script language="javascript">

function passBean()
{
	if ($$('cid') == '')
	{
		alert('请选择要绑定的客户');
		return ;
	}
	
    $O('method').value = 'passAppUserApply';
    
    submit('确定通过?', null, null);
}

function rejectBean()
{
    $.messager.prompt('驳回', '请输入驳回原因', '', function(r){
        if (r)
        {
            $Dbuttons(true);
            getObj('method').value = 'rejectAppUserApply';

            var sss = r;

            getObj('reason').value = r;

            if (!(sss == null || sss == ''))
            {
            	formEntry.submit();
            }
            else
            {
                $Dbuttons(false);
            }
        }
       
    });
}

function addBean()
{
	if ($$('cid') == '')
	{
		alert('请选择要绑定的客户');
		return ;
	}
	
    $O('method').value = 'addAppUserCust';
    
    submit('确定提交?', null, null);
}

function selectCus()
{
    window.common.modal('../client/client.do?method=rptQueryAllClient&load=1&first=1');
}

function getCustomer(obj)
{
    $O('cid').value = obj.value;
    $O('cname').value = obj.pname;
}

function selectAppUser()
{
    window.common.modal('../client/client.do?method=rptQueryAllAppUser&load=1&first=1');
}

function getAppUser(obj)
{
    $O('id').value = obj.value;
    $O('uname').value = obj.pname;
}

</script>

</head>
<body class="body_class">
<form name="formEntry" action="../client/client.do" method="post">
<input type="hidden" name="method" value=""> 
<input type="hidden" name="id" value="${bean.id}"> 
<input type="hidden" name="mode" value="${mode}">
<input type="hidden" name="cid" value="">
<input type="hidden" name="reason" value="">
<p:navigation
	height="22">
	<td width="550" class="navigation"><span style="cursor: pointer;"
		onclick="javascript:history.go(-1)">手机用户管理</span> &gt;&gt; 用户明细</td>
	<td width="85"></td>
</p:navigation> <br>

<p:body width="98%">

	<p:title>
		<td class="caption"><strong>用户基本信息：</strong></td>
	</p:title>

	<p:line flag="0" />

	<p:subBody width="100%">
           
		<p:table cells="1">
		
		 <c:if test="${mode != 1}">
		 
		    <p:cell title="选择用户">
			    <input type="text" class="input_class" name="uname" id="uname" style="width: 240px" readonly="readonly" oncheck="notNone;">
			    <input type="button" value="&nbsp;...&nbsp;" name="qout1" id="qout1"
	                   class="button_class" onclick="selectAppUser()">&nbsp;&nbsp; 
			</p:cell>			 
		 
		 </c:if>
		
	    <p:cell title="选择客户">
		    <input type="text" class="input_class" name="cname" id="cname" style="width: 240px" readonly="readonly">
		    <input type="button" value="&nbsp;...&nbsp;" name="qout" id="qout"
                   class="button_class" onclick="selectCus()">&nbsp;&nbsp; 
		</p:cell>		
		
		 <c:if test="${mode == 1}">
		  <p:cell title="用户ID">
               ${bean.id}
            </p:cell>

			<p:cell title="注册用户名">
               ${bean.loginName}
            </p:cell>
			
            <p:cell title="申请人">
               ${bean.applyName}
            </p:cell>
            
            <p:cell title="状态">
               ${my:get('userApplyStatus', bean.status)}
            </p:cell>            

            <p:cell title="手机">
               ${bean.mobile}
            </p:cell>
            
            <p:cell title="邮件">
               ${bean.email}
            </p:cell>
            
            <p:cell title="省">
               ${bean.province}
            </p:cell>
            
            <p:cell title="市">
               ${bean.city}
            </p:cell>
            
            <p:cell title="地址">
               ${bean.fullAddress}
            </p:cell>
            
            <p:cell title="开票时间">
               ${bean.logTime}
            </p:cell>
            
            <p:cell title="开票时间">
               ${bean.description}
            </p:cell>
		  </c:if>

		</p:table>

	</p:subBody>

	<p:line flag="1" />

	<p:button leftWidth="100%" rightWidth="0%">
	<div align="right">
		<c:if test="${mode == 1}">
                <input type="button" class="button_class"
                    id="ok_p" style="cursor: pointer" value="&nbsp;&nbsp;通 过&nbsp;&nbsp;"
                    onclick="passBean()">&nbsp;&nbsp;
                <input type="button" class="button_class"
                id="re_b" style="cursor: pointer" value="&nbsp;&nbsp;驳 回&nbsp;&nbsp;"
                onclick="rejectBean()">&nbsp;&nbsp;
         </c:if>
         
         <c:if test="${mode != 1}">
                <input type="button" class="button_class"
                    id="ok_s" style="cursor: pointer" value="&nbsp;&nbsp;确定&nbsp;&nbsp;"
                    onclick="addBean()">&nbsp;&nbsp;
         </c:if>
         </div>
	</p:button>

	<p:message2 />
</p:body></form>
</body>
</html>

