<%@ page contentType="text/html;charset=UTF-8" language="java"
	errorPage="../common/error.jsp"%>
<%@include file="../common/common.jsp"%>

<html>
<head>
<p:link title="业务申请单打印" guid="true" dialog="true"/>
<script language="JavaScript" src="../js/common.js"></script>
<script language="JavaScript" src="../js/math.js"></script>
<script language="JavaScript" src="../js/public.js"></script>
<script language="JavaScript" src="../js/cnchina.js"></script>
<script language="JavaScript" src="../js/JCheck.js"></script>
<script language="JavaScript" src="../js/compatible.js"></script>
<script language="JavaScript" src="../js/json.js"></script>
<script language="JavaScript" src="../js/key.js"></script>
<script language="JavaScript" src="../sailImport_js/addZJRCOut.js"></script>
<script language="JavaScript" src="../js/plugin/highlight/jquery.highlight.js"></script>
<script language="javascript">

function load()
{
    //loadForm();

    //$detail($O('viewTable'), ['pr', 'ba', 'pr2']);
    //$detail($O('viewTable'), ['pr','ba']);
    
}

function hides(boo)
{
    
}


function pagePrint()
{
    $O('na').style.display = 'none';
    $O('pr').style.display = 'none';
//    $O('ba').style.display = 'none';
//    $O('desc1').style.display = 'none';
    window.print();

    $O('pr').style.display = 'inline';
//    $O('ba').style.display = 'inline';
    $O('na').style.display = 'block';
//    $O('desc1').style.display = 'block';
}

function pagePrint2()
{
    $O('na').style.display = 'none';
    //$O('pr').style.display = 'none';
    $O('pr2').style.display = 'none';
    $O('ba').style.display = 'none';
    $O('desc1').style.display = 'none';
    $O('cost_td').style.display = 'none';
    hiddenCost('none');
    window.print();

    //$O('pr').style.display = 'inline';
    $O('pr2').style.display = 'inline';
    $O('ba').style.display = 'inline';
    $O('na').style.display = 'block';
    $O('desc1').style.display = 'block';
    $O('cost_td').style.display = 'block';
    hiddenCost('block');
}

function hiddenCost(str)
{
	//var costList = document.getElementsByName('desciprt');
	
	for (var i = 0; i < 30; i++)
	{
		if ($O('sub_td_' + i))
		{
			$O('sub_td_' + i).style.display = str;
		}

		if ($O('subp_td_' + i))
		{
			$O('subp_td_' + i).style.display = str;
		}
	}
}
</script>
<style type="text/css">
    #mainTable td{border:1px solid }
    .no-border{border:0 !important;}
    #mainTable tr{height: 50px}
    .title{height: 80px !important;}
    .applyForm{height: 30px !important;}
</style>
</head>
<body class="body_class" onload="load()">
<form name="outForm" method="post">

<div id="na">
<p:navigation
	height="22">
	<td width="550" class="navigation">业务申请单打印</td>
				<td width="85"></td>
</p:navigation> <br>
</div>

<table width="98%" border="0" cellpadding="0" cellspacing="0" id="viewTable"
	align="center">

	<tr>
		<td colspan='2' align='center'>
            <table width="80%" border="0" cellspacing='0' id="mainTable">
                <tr class="content2 title">
                    <td colspan="3" align="center">业务申请单</td>
                </tr>

                <tr>
                    <td colspan="3">
                        <table>
                            <tr class="applyForm">
                                <td class="no-border" rowspan="2" colspan="2" width="550px">填单时间：&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;年  &nbsp;&nbsp;&nbsp;&nbsp;  月 &nbsp;&nbsp;&nbsp;&nbsp;   日 &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                </td>
                                <td class="no-border"><input type="checkbox">买入</td>
                            </tr>
                            <tr class="applyForm">
                                <td class="no-border"><input type="checkbox">卖出</td>
                            </tr>
                        </table>
                    </td>
                </tr>
                <tr class="content2">
                    <td width="30%" align="center">规格</td>
                    <td align="center">申请数量（大写)</td>
                    <td align="center">申请数量（小写）</td>
                </tr>
                <tr class="content2">
                    <td></td>
                    <td></td>
                    <td></td>
                </tr>
                <tr class="content2">
                    <td> </td>
                    <td></td>
                    <td></td>
                </tr>
                <tr class="content2">
                    <td> </td>
                    <td></td>
                    <td></td>
                </tr>
                <tr class="content2">
                    <td colspan="3">客户签字：</td>
                </tr>
                <tr class="content2">
                    <td colspan="3">经办人：</td>
                </tr>

            </table>

		</td>
	</tr>

	<tr>
		<td height="10" colspan='2'></td>
	</tr>

	<tr>
		<td background="../images/dot_line.gif" colspan='2'></td>
	</tr>


    <tr>
        <td height="10" colspan='2'></td>
    </tr>

    <tr>
        <td background="../images/dot_line.gif" colspan='2'></td>
    </tr>

    <tr>
        <td height="10" colspan='2'></td>
    </tr>

	<tr>
        <td width="100%">
        <div align="right">
            <input type="button" name="pr"
                class="button_class" onclick="pagePrint()"
                value="&nbsp;&nbsp;打 印&nbsp;&nbsp;">&nbsp;&nbsp;
        </div>
        </td>
        <td width="0%"></td>
    </tr>

</table>
</form>
</body>
</html>

