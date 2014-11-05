<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@include file="../common/common.jsp"%>
<html>
<head>
<p:link title="呼叫中心座席" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script language="JavaScript" src="../js/common.js"></script>
<script language="JavaScript" src="../js/cnchina.js"></script>
<script language="JavaScript" src="../js/public.js"></script>
<script language="JavaScript" src="../js/JCheck.js"></script>

<script language="JavaScript" type="text/JavaScript">

<%
String sid = session.getId();

request.setAttribute("sessionId", sid);
%>

function Logout()
{
}
</script>

</head>

<body class="body_class" onload="window_onload()">
     <input name="txtServerIP" type="hidden" value="192.168.1.192"/> 
     <input name="txtLocalIP" type="hidden" value="192.168.1.10" />
     <input name="txtDev" type="hidden" value="6001" />
     <input name="txtAgentID" type="hidden" value="999"/>
     <input name="AgentName" type="hidden" value="Demo"/>
     <input name="txtSK" type="hidden" value="1001|1002"/>
     
     <object id="csSoftPhone"
        classid="CLSID:FAAD0925-0C9B-47BE-B67E-09E2BCB95A3C"
        codebase="CrystalSoftPhone21.cab#Version=2,1,0,62"
        style="width: 100%; height: 65pt">
        <param name="ServerIP" value="">
        <param name="LocalIP" value="">
        <param name="INS" value="">
        <param name="SK" value="1001|1002">
        <param name="AgentID" value="1001">
        <param name="AgentName" value="">
      </object><br />
        
    <textarea id="txtTrace" style="width: 100%; height: 294px"></textarea>&nbsp;
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
    
    txtTrace.value = txtTrace.value + msg +"\r";
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
		
    obj.AgentType = 0;
    obj.SK =  txtSK.value ;
    
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
