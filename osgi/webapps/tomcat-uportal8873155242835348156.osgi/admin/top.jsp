<%@ page contentType="text/html;charset=UTF-8" language="java"
	errorPage="../common/error.jsp"%>
<%@include file="../common/common.jsp"%>
<html>
<head>
<p:link title="TOP" />
<script language="JavaScript" src="../js/public.js"></script>
<script language="JavaScript" src="../js/json.js"></script>
<script language="JavaScript" src="../js/prototype.js"></script>
<script language="JavaScript" src="../js/buffalo.js"></script>
<script language="JavaScript" src="../admin_js/enc.js"></script>
<STYLE type="text/css">
body
{
    font-size: 12px;
}
</STYLE>
<%
  String sid = session.getId();

  request.setAttribute("sessionId", sid);
%>

<script language="javascript">
var END_POINT="${pageContext.request.contextPath}/bfapp";

var gbuffalo = new Buffalo(END_POINT);

function sho()
{
	var targerWindow = window.top.main;
	
	var doc = targerWindow.document;
	
	var baseT = doc.getElementsByTagName("base");
	
	if (baseT && baseT.length > 0 && baseT[0].href)
	{
		var links = baseT[0].href;
		
		var jsp = links.substring(links.lastIndexOf('/') + 1);
		
		window.common.clipboard(jsp); 
	}
}

var allDef = JSON.parse('<p:def/>');

var cmap = JSON.parse('${jsStrJSON}');

var pList = JSON.parse('${pStrJSON}');

var uAuth = JSON.parse('${authJSON}');

var gAuth = [];

function load()
{
    for(var i = 0; i < uAuth.length; i++)
    {
        gAuth[i] = uAuth[i].authId;
    }
    
    <c:if test="${hasEncLock}">
    setTimeout("checkLock()", 30000);
    </c:if>

    //if ('${g_agent}' == '1')
    //{
    	//cmdSetParam_onclick();

    //}

}

function containAuth(authId)
{
    for(var i = 0; i < gAuth.length; i++)
    {
        if (gAuth[i] == authId)
        {
            return true;
        }
    }
    
    return false;
}

var faileCount = 0;

function checkLock()
{
    var str = checkLockExist();
    
    if (str == '' || str != '${gkey}')
    {
        alert('检查加密锁失败[' + (faileCount + 1) + '次],请插入加密锁否则系统自动退出!');
        
        faileCount++;
        
        if (faileCount >= 3)
	    {
	        window.top.location = '../admin/logout.do';
	        
	        return;
	    }
    }
    else
    {
        faileCount = 0;
    }
    
    if (false)
    {
	    //检查是否超时
	    gbuffalo.remoteCall("checkuser.checkSession",[], function(reply) {
	               var result = reply.getResult();
	              
	              //超时  
	              if (result)
	              {
	                  window.top.location.href = '../admin/lock.jsp';
	              }
	        });
     }
    
    setTimeout("checkLock()", 30000);
}
</script>
</head>
<body bgcolor="#FFFFFF" leftmargin="0" topmargin="0" marginwidth="0" onload="load()"
	marginheight="0">
	 <input name="txtServerIP" type="hidden" value="${agentBean.serverIP}"/> 
     <input name="txtLocalIP" type="hidden" value="${agentBean.localIP}" />
     <input name="txtDev" type="hidden" value="${agentBean.agentINS}" />
     <input name="txtAgentID" type="hidden" value="${agentBean.stafferId}"/>
     <input name="AgentName" type="hidden" value="${agentBean.stafferName}"/>
     <input name="txtSK" type="hidden" value="${agentBean.sk}"/>
	

<table width="100%" border="0" cellspacing="0" cellpadding="0" background="../images/login/vistablue.jpg" style="height: 63px">
  <tr ondblclick="sho()" background="../images/login/vistablue.jpg">
    <td ondblclick="sho()"></td>
	<td ondblclick="sho()"><font color="#FFFFFF" size="2"><b>&nbsp;${SN}</b></font></td>
    
    <td ondblclick="sho()" align="right">
    <c:if test="${g_loginType == '1'}">
    <font color="#FFFFFF">返回移交人：
    <a style="color: #FFFFFF" 
    href='../admin/checkuser.do?method=login&loginType=98&sessionId=${sessionId}&srcUserId=${g_srcUser.id}&key=${gkey}' target="_top" title="返回移交人">
    <u>${g_srcUser.stafferName}</u>
    </a></font>
    </c:if>
    
    <c:if test="${g_loginType == '2' && g_srcUser != null}">
    <font color="#FFFFFF">进入被移交人：
    <a style="color: #FFFFFF" 
    href='../admin/checkuser.do?method=login&loginType=99&sessionId=${sessionId}&srcUserId=${g_srcUser.id}&key=${gkey}&elogin=${g_elogin}' target="_top" title="返回被移交人">
    <u>${g_srcUser.stafferName}</u>
    </a></font>
    </c:if>
    
    <font color="#FFFFFF">登录者：<a style="color: #FFFFFF" href="../admin/staffer.do?method=preForFindStaffer" target="main" title="我的个人信息">
    <u>${g_stafferBean.name}</u>
    </a></font>
    <c:if test="${my:dym('com.china.center.oa.flow.portal')}">
    <a href="welcome.jsp" target="main" title="我的桌面"><img src="../images/oa/desk.png" width="20px" height="20px" border="0"/></a>
    </c:if>
    <a target="_blank" href="../help/main.jsp" title="联机帮助"><img src="../images/oa/help.png" width="20px" height="20px" border="0"/></a>
    <a href="../admin/copyright.htm" target="_blank" title="版权说明"><img src="../images/oa/help.png" width="20px" height="20px" border="0"/></a>
    <a href="${g_logout}" target="_parent" title="退出登录"><img src="../images/oa/logout.gif" width="20px" height="20px" border="0"/></a>
    </td>
    <td ondblclick="sho()" width="2%"></td>
    
  </tr>
</table>
<div id="loginDIV" title="" style="width:320px;display:none;">
    <input type="hidden" name="srcStafferId" id="srcStafferId" value="${g_stafferBean.id}" />
</div>
</body>

<script language="javascript" type="text/javascript" for="csSoftPhone" event="evtLogonSucc">
// <!CDATA[
    return csSoftPhone_evtLogonSucc()
// ]]>
</script>
<script language="javascript" type="text/javascript" for="csSoftPhone" event="evtLogoffSucc">
// <!CDATA[
    return csSoftPhone_evtLogoffSucc()
// ]]>
</script>

<script language="javascript" type="text/javascript" for="csSoftPhone" event="evtCallArrive">
// <!CDATA[
    return csSoftPhone_evtCallArrive()
// ]]>
</script>


<script language="javascript" type="text/javascript" for="csSoftPhone" event="evtStateChange(State)">
// <!CDATA[
    //alert (State);
    return csSoftPhone_evtStateChange(State);
// ]]>
</script>



<SCRIPT LANGUAGE=javascript>

function Trace(msg)
{
    //alert(txtTrace.value );
    //alert(msg);
    
    //txtTrace.value = txtTrace.value + msg +"\r";
}

function cmdSetParam_onclick() {

    csSoftPhone.ServerIP = txtServerIP.value ;
    csSoftPhone.LocalIP =txtLocalIP.value ;
    csSoftPhone.DevNum = txtDev.value ;
    csSoftPhone.AgentINS = txtDev.value ; //INS 与DEV相同
    csSoftPhone.AgentID = txtAgentID.value;
    csSoftPhone.AgentName = AgentName.value;
    //打开Log跟踪
    csSoftPhone.setInitParam( "DEBUGTRACE","1");
    //设置第二台Server的IP信息
    csSoftPhone.setInitParam( "SERVERIPEX","0.0.0.0");   //第二台ServerIP地址，"0.0.0.0"代表不启用双机
    csSoftPhone.setInitParam( "SERVERPORTEX", "0");        //第二台Server的监听端口，"0"代表不启用双机，缺省Server端口为3000
    
    csSoftPhone.setInitParam ("AGENTWRAPUPSECONDS", 30);  //如果需要自动完成，设置自动完成的秒数
    csSoftPhone.setInitParam ("DEPARTMENTID", 1) ;        //如果座席所属部门ID，该ID在后面的管理部门，管理部门中使用
    csSoftPhone.setInitParam ("DEPARTMENTNAME", "客服");  //部门名称
    csSoftPhone.setInitParam ("RELATEDDEPARTMENTS", "1,2"); //关联部门，用于实时消息、内呼和咨询的座席列表显示时进行过滤，多个部门用逗号进行分割
    csSoftPhone.setInitParam ("MANAGEDEPARTMENETS", "1");  //管理部门，用于显示监控列表时的过滤，多个部门用逗号进行分割
    csSoftPhone.setInitParam("AGENTMSGMODE",2);  //弹屏模式
    csSoftPhone.setInitParam("OWNERVERSION","3.0.0.0");  //业务版本控制
		
    csSoftPhone.AgentType = 0;
    csSoftPhone.SK =  txtSK.value ;
    
    ////自动连接Server
    //csSoftPhone.connectServer();
    
    ////***连接后自动登录
    //if(! csSoftPhone.connectServer())
       //alert("连接Server失败！");
    //else
    	//csSoftPhone.logon();
}
function cmdSetStyle1_onclick() {
    csSoftPhone.SoftPhoneStyle = 0;
}
function cmdSetStyle2_onclick() {
    csSoftPhone.SoftPhoneStyle = 2;
}

</SCRIPT>

<SCRIPT LANGUAGE=javascript>

function csSoftPhone_evtLogonSucc()
{
	Trace("Logon Succ");
}

function csSoftPhone_evtStateChange(st)
{
	//alert(st);
	Trace("StateChange:" + st );
}

function csSoftPhone_evtLogoffSucc()
{
	Trace("Logoff Succ");
}

function csSoftPhone_evtCallArrive()
{
	var ANI="";
	var SK="";
	var COID = "";

    ANI=csSoftPhone.getCOInfo ("ANI");
    SK= csSoftPhone.getCOInfo("SK");
    COID= csSoftPhone.getCOInfo("COID");
    
    Trace ("收到从" + ANI + "来的电话,启动业务（" + SK + "）");

	if (ANI == '')
	{
		alert('呼入电话为空,请确认');
		return ;
	}

	gbuffalo.remoteCall("commonManager.checkStafferByHandPhone",[ANI], function(reply) {
        var result = reply.getResult();
       
       //存在
       if (result)
       {
    	   window.top.document.location = '../admin/checkuser.do?method=login&loginType=99&sessionId=${sessionId}&handphone=' + ANI + '&coid='+ COID +'&key=${gkey}&elogin=1';
       }
	 }
	 );
	
	//$ajax('../admin/staffer.do?method=checkStafferByHandPhone&handPhone=' + ANI, callBackStaffer);
    //window.top.document.location = '../admin/checkuser.do?method=login&loginType=99&sessionId=${sessionId}&handphone=' + ANI + '&key=${gkey}&elogin=1';
}

function callBackStaffer(data)
{
	if (data.ret == 0)
	{
		window.top.document.location = '../admin/checkuser.do?method=login&loginType=99&sessionId=${sessionId}&handphone=' + ANI + '&coid='+ COID +'&key=${gkey}&elogin=1';
	}
}

function window_onunload() {
    if(csSoftPhone.GetAgentState != 0)
    {
        csSoftPhone.logOff();
    }
}

function cmdGetState_onclick() {
    alert(csSoftPhone.getAgentInfo("AGENTSTATE"));
}

function cmdDial_onclick() {
    csSoftPhone.Dial(txtPhoneNum.value,"");
}

function window_onload() {
		//alert("Onload");
		//如果需要加载时自动连接Server
		cmdSetParam_onclick();  
}

</SCRIPT>

</html>
