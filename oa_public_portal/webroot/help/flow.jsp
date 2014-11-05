<%@ page contentType="text/html;charset=GBK" language="java"
    errorPage="../common/error.jsp"%>
<%@include file="../common/common.jsp"%>
<html>
<head>
<p:link title="FLOW" />
<link rel="stylesheet" href="../js/plugin/accordion/accordion.css" />
<script type="text/javascript" src="../js/jquery/jquery.js"></script>
<script src="../js/plugin/accordion/jquery.accordion.js"></script>
<script src="../js/common.js"></script>
<script src="../js/public.js"></script>

<script language="javascript">
jQuery().ready(function(){
    
    jQuery('#flowDiv').accordion({
        autoheight: false
    });
    
    var accordions = jQuery('#flowDiv');
    
    accordions.accordion("activate", 0);
});

</script>

</head>
<body class="body_class">
<div class="basic" id="flowDiv">
<a><font><b>1、如何加载OFFICE在线编辑控件</b></font></a>
<div>
<pre>
<img src="../images/help/flow/dll.png">
<font color="red">注意：仅限于IE</font>
</pre>
</div>

<a><font ><b>2、如何创建流程定义</b></font></a>
<div>
<pre>
<font color="blue">1)首先到超级管理员里面配置流程所需权限</font>
<img src="../images/help/flow/operation.png">

<font color="blue">2)增加流程模板[流程模板-->增加](当前仅支持WORD和EXCEL)</font>

<font color="blue">3)增加流程定义[流程定义-->增加]</font>
<img src="../images/help/flow/addflow.png">

<font color="blue">4)配置流程环节[流程定义-->配置环节]</font>
<img src="../images/help/flow/configtoken.png">
<img src="../images/help/flow/special.png">
<font color="red">注意：选择模板模式必须要配置高级属性,否则模板无法编辑</font>

<font color="blue">5)配置流程查阅[流程定义-->配置查阅](如果没有查阅,可以不配置)</font>
<img src="../images/help/flow/configview.png">

<font color="blue">6)流程定义结束</font>
<img src="../images/help/flow/flowlist.png">
</pre>
</div>

<a><font ><b>3、如何创建流程实例</b></font></a>
<div>
<pre>
<font color="blue">1)选择一个流程定义，点击"创建流程实例"[创建流程实例-->创建流程实例]</font>
<img src="../images/help/flow/createinstance.png">

<font color="blue">2)填写基本信息,如果存在模板还可以在线编辑</font>
<img src="../images/help/flow/edittemplate.png">

<font color="blue">3)选择下一环处理人,提交实例,流程就可以按照定义进行下去</font>

</pre>
</div>

</div>
</body>
</html>

