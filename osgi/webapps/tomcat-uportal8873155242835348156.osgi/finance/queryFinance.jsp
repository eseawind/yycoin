<%@page contentType="text/html; charset=UTF-8"%>
<%@include file="../common/common.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<p:link title="凭证管理" link="true" guid="true" cal="true" dialog="true" />
<script src="../js/common.js"></script>
<script src="../js/public.js"></script>
<script src="../js/pop.js"></script>
<script src="../js/plugin/highlight/jquery.highlight.js"></script>
<script type="text/javascript">

var gurl = '../finance/finance.do?method=';
var addUrl = '../finance/addFinance.jsp';
var ukey = 'Finance';

var allDef = getAllDef();
var guidMap;
var thisObj;
function load()
{
     preload();
     
     guidMap = {
         title: '凭证列表',
         url: gurl + 'query' + ukey,
         colModel : [
             {display: '<input type=checkbox id=flexi_Check onclick=checkAll(this)>选择', name : 'check', content : '<input type=checkbox name=checkb value={id} lstatus={status} llocks={locks} lfdate={financeDate}>', width : 40, align: 'center'},
             {display: '标识', name : 'id', width : '15%'},
             {display: '月索引', name : 'monthIndex', sortable : true, width : '7%'},
             {display: '类型', name : 'type', cc: 'financeType', width : '5%'},
             {display: '分类', name : 'createType', cc: 'financeCreateType', width : '10%'},
             {display: '状态', name : 'status', cc: 'financeStatus', width : '8%'},
             {display: '纳税实体', name : 'dutyName',  width : '10%'},
             {display: '金额', name : 'showInmoney', width : '8%'},
             {display: '创建人', name : 'createrName', width : '8%'},
             {display: '凭证日期', name : 'financeDate', sortable : true, width : '10%'},
             {display: '创建时间', name : 'logTime', sortable : true, width : 'auto'}
             ],
         extAtt: {
             id : {begin : '<a title=点击查看明细 href=' + gurl + 'find' + ukey + '&id={id}>', end : '</a>'}
         },
         buttons : [
             {id: 'add', bclass: 'add', onpress : addBean, auth: '1802'},
             {id: 'add1', bclass: 'add', caption: '凭证拷贝', onpress : copyBean, auth: '1802'},
             {id: 'update', bclass: 'update', onpress : updateBean, auth: '1802'},
             {id: 'pass', bclass: 'pass', caption: '总部核对', onpress : checkBean, auth: '1803'},
             {id: 'del', bclass: 'del',  onpress : delBean, auth: '1804'},
             {id: 'search', bclass: 'search', onpress : doSearch},
             {id: 'export', bclass: 'replied',  caption: '导出明细', onpress : exports}
             ],
        <p:conf queryMode="0"/>
     };
     
     $("#mainTable").flexigrid(guidMap, thisObj);
}
 
function $callBack()
{
    loadForm();
    
    highlights($("#mainTable").get(0), ['未核对'], 'red');
}

function exports()
{
    document.location.href = gurl + 'exportFinance';
}

function addBean(opr, grid)
{
    $l(gurl + 'preForAdd' + ukey);
    //$l(addUrl);
}

function delBean(opr, grid)
{
    var clis = getCheckBox('checkb');
    
    if (clis.length != 1)
    {
        $error('只能选择一个凭证');
        return false;
    }
    
    if (getRadio('checkb') && getRadioValue('checkb') && getRadio('checkb').llocks == 0)
    {    
        if(window.confirm('确定删除?'))    
        $ajax(gurl + 'delete' + ukey + '&id=' + getRadioValue('checkb'), callBackFun);
    }
    else
    $error('不能操作,可能被锁定');
}

function copyBean(opr, grid)
{
    var clis = getCheckBox('checkb');
    
    if (clis.length != 1)
    {
        $error('只能选择一个凭证');
        return false;
    }
    
    if (getRadio('checkb') && getRadioValue('checkb'))
    {
        $.messager.prompt('凭证日期', '请选择凭证日期', getRadio('checkb').lfdate, function(value, opr){
                    if (opr)
                    {
                        var sss = value;
                        
                        if (!(sss == null || sss == ''))
                        {
                            $l(gurl + 'copy' + ukey + '&id=' + getRadioValue('checkb') + '&financeDate=' + sss);
                        }
                        else
                        {
                            alert('请选择凭证日期');
                        }
                    }
                }, 1);
    }
    else
    $error('不能操作');
}

function checkBean()
{
    var clis = getCheckBox('checkb');
    
    if (clis.length == 0)
    {
        $error('请选择凭证');
        return false;
    }
    
    var str = '';
    for (var i = 0; i < clis.length; i++)
    {
        str += clis[i].value + '~';
    }
    
    $.messager.prompt('总部核对', '请核对说明', '', function(msg){
                if (msg)
                {
                    $ajax2(gurl + 'checks&id=' + str + '&type=99', {'reason' : msg},  
                        callBackFun);
                }
               
            }, 2);
}

function updateBean()
{
    var clis = getCheckBox('checkb');
    
    if (clis.length != 1)
    {
        $error('只能选择一个凭证');
        return false;
    }
    
	if (getRadio('checkb') && getRadioValue('checkb') && getRadio('checkb').lstatus == 0)
	{	
		$l(gurl + 'find' + ukey + '&update=1&id=' + getRadioValue('checkb'));
	}
	else
	$error('不能操作');
}

function doSearch()
{
    $modalQuery('../admin/query.do?method=popCommonQuery2&key=query' + ukey);
}

</script>
</head>
<body onload="load()" class="body_class">
<form name="mainForm" method="post">
<p:cache></p:cache>
</form>
<p:message/>
<table id="mainTable" style="display: none"></table>
<p:query/>
</body>