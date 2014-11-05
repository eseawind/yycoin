<%@ page contentType="text/html;charset=UTF-8" language="java"
    errorPage="../common/error.jsp"%>
<%@include file="../common/common.jsp"%>
<html>
<head>
<p:link title="发送邮件" />
<script language="JavaScript" src="../js/string.js"></script>
<script language="JavaScript" src="../js/JCheck.js"></script>
<script language="JavaScript" src="../js/common.js"></script>
<script language="JavaScript" src="../js/public.js"></script>
<script src="../js/jquery/jquery.js"></script>
<script src="../js/jquery.blockUI.js"></script>
<script language="javascript">
function addApplys()
{
     //自动校验
    if (formCheck(formEntry) && window.confirm('确认发送邮件'))
    {
        $.blockUI({ message: $('#mailDiv'),css:{width: '40%'}}, true);
        
        submitC(formEntry);
    }
}

var gflag = 0;
function selectStaffer(flag)
{
    gflag = flag;
    
    window.common.modal('../admin/pop.do?method=rptQueryStaffer&load=1');
}

function selectGroup(flag)
{
    gflag = flag;
    window.common.modal('../group/group.do?method=rptQueryGroup&load=1');
}

function drops()
{
    if (window.confirm('确定放弃此邮件?'))
    $l('../mail/queryMail.jsp');
}

function getGroup(oo)
{
    getStaffers(oo);
}

function getStaffers(oo)
{
    var currentObj;
    var currentDis;
    if (gflag == 0)
    {
        currentObj = $O('reveiveIds');
        currentDis = $O('senderName');
    }
    else
    {
        currentObj = $O('reveiveIds2');
        currentDis = $O('senderName2');
    }
    
    for (var i = 0; i < oo.length; i++)
    {
        var eachItem = oo[i];
        
        if (containOption(eachItem.value))
        {
            continue;
        }
        
        currentObj.value += eachItem.value + ';'
        currentDis.value += eachItem.pname + ';'
    }
}

function containOption(id)
{
    var currentObj;
    
    if (gflag == 0)
    {
        currentObj = $O('reveiveIds');
    }
    else
    {
        currentObj = $O('reveiveIds2');
    }
    
    var vsoptions = currentObj.value.split(';');
        
    for (var k = 0; k < vsoptions.length; k++)
    {
        if (vsoptions[k] == id)
        {
            return true;
        }
    }
    
    return false;
}

function load()
{
    $v('tr_att_more', false);
}

var gMore = 0;

function showMoreAtt()
{
    var obj = getObj('tr_att_more');
    
    if (gMore % 2 == 0)
    {
        $v('tr_att_more', true);
    }
    else
    {
        $v('tr_att_more', false);
    }
    
    gMore++;
}

function esc_back()
{
    $l('../mail/queryMail.jsp');
}

function del(id)
{
    $O('span_' + id).innerHTML = '';
    
    $O('attacmentIds').value = $O('attacmentIds').value.delSubString(id + ';')
}
</script>

</head>
<body class="body_class" onload="load()">
<form name="formEntry" action="../mail/mail.do?method=sendMail" enctype="multipart/form-data" method="post">
<input
    type="hidden" name="attacmentIds" value="${attacmentIds}">
<input
    type="hidden" name="forwardMail" value="1">

<input
    type="hidden" name="reveiveIds" value="${mainReveiveIds}"> <input
    type="hidden" name="reveiveIds2" value="${secReveiveIds}"><p:navigation
    height="22">
    <td width="550" class="navigation"><span style="cursor: pointer;"
        onclick="esc_back()">邮件管理</span> &gt;&gt; 转发邮件</td>
    <td width="85"></td>
</p:navigation> <br>

<p:body width="100%">

    <p:button leftWidth="98%" rightWidth="2%">
        <div align="left">&nbsp;&nbsp;<input type="button" class="button_class" id="ok_b"
            style="cursor: pointer" value="&nbsp;&nbsp;发送邮件(S)&nbsp;&nbsp;" accesskey="S"
            onclick="addApplys()"></div>
    </p:button>

    <p:line flag="1" />
    <p:subBody width="98%">
        <p:table cells="1" id="tables">

            <p:cell title="收件人" width="8"><input type="text" name="senderName" size="80" readonly="readonly" style="cursor: pointer;" onclick="selectStaffer(0)" oncheck="notNone;" value="${mainReveiveNames}"> <font color="red">*</font>
            <span style="cursor: pointer;" onclick="selectGroup(0)">选择群组</span>
            </p:cell>
            <p:cell title="抄送人" width="8"><input type="text" name="senderName2" size="80" readonly="readonly" style="cursor: pointer;" onclick="selectStaffer(1)" value="${secReveiveNames}"> <font color="red">&nbsp;</font>
            <span style="cursor: pointer;" onclick="selectGroup(1)">选择群组</span>
            </p:cell>
            <p:cell title="主题" width="8"><input type="text" name="title" size="80" maxlength="200" oncheck="notNone;" value="转发:${bean.title}">  <font color="red">*</font></p:cell>
            
            <p:cell title="原附件" width="8">
            <c:forEach items="${atts}" var="item" varStatus="vs">
            <span id="span_${item.id}"><img src=../images/oa/attachment.gif><a target="_blank" href="../mail/mail.do?method=downMailAttachment&id=${item.id}">${item.name}</a>&nbsp;
            <a title="删除附件" href="javascript:del('${item.id}')"> <img
                        src="../images/oa/del.gif" border="0" height="15" width="15"></a></span>
            &nbsp;&nbsp;&nbsp;&nbsp;
            <c:if test="${!vs.last}">
            <br>
            </c:if>
            </c:forEach>
            </p:cell>
            
            <p:cell title="附件" width="8"><input type="file" name="atts" size="70" class="button_class">  
            <font color="blue"><span style="cursor: pointer;" onclick="showMoreAtt()" >【更多附件】 </span><b>建议压缩后上传,最大支持10M</b></font>
            </p:cell>
            
            <p:cell title="附件N" width="8" id="att_more">
            <input type="file" name="atts0" size="70" class="button_class"> <br> 
            <input type="file" name="atts1" size="70" class="button_class"> <br> 
            <input type="file" name="atts2" size="70" class="button_class"> <br> 
            <input type="file" name="atts3" size="70" class="button_class"> <br> 
            <input type="file" name="atts4" size="70" class="button_class"> <br> 
            <input type="file" name="atts5" size="70" class="button_class"> <br> 
            <input type="file" name="atts6" size="70" class="button_class"> <br> 
            <input type="file" name="atts7" size="70" class="button_class"> <br> 
            <input type="file" name="atts8" size="70" class="button_class"> <br> 
            <input type="file" name="atts9" size="70" class="button_class"> <br> 
            </p:cell>
            
            <p:cell title="内容" width="8">
<textarea name="content" cols="100" rows="25" oncheck="maxLength(500)">
${bean.content}
</textarea></p:cell>

        </p:table>
    </p:subBody>
    
    <p:line flag="1" />
    
    <p:button leftWidth="98%" rightWidth="2%">
        <div align="left">&nbsp;&nbsp;<input type="button" class="button_class" id="ok_s"
            value="&nbsp;&nbsp;发送邮件(S)&nbsp;&nbsp;" accesskey="S"
            onclick="addApplys()">&nbsp;&nbsp;<input type="button" class="button_class" id="ok_c"
            value="&nbsp;&nbsp;返回列表(B)&nbsp;&nbsp;" accesskey="B"
            onclick="esc_back()"></div>
    </p:button>

    

    <p:message/>
</p:body></form>
<div id="mailDiv" style="display:none">
<p>&nbsp;</p>
<p align='center'>邮件发送中......</p>
<p><img src="../images/oa/process.gif" /></p>
<p>&nbsp;</p>
</div>
</body>
</html>

