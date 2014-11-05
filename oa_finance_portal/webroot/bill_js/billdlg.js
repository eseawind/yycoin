/**
 * 总部核对
 */
function loadcheckDIVInner()
{
	if ($('#checkDiv').length > 0)
	{
		return;
	}
	
	var html = '<div id="checkDiv" title="预收转费用" style="width:320px;height:250px;">';
	
	html += ' <div style="padding:20px;height:100px;display:none" id="checkDiv_inner" title="" >';

	html += '备注：<br><textarea style="width:95%" id="descrition" name="descrition"></textarea><br><br>';
	
	html += '金额：<br><input style="width:95%" type="text" name="refMoney" id="refMoney" value="" maxlength="40"><br>';
	
	html += '</div>';
	html += '</div>';
	
	$(document.body).append(html);
}

function openCheckDiv()
{
	$('#checkDiv_inner').show();
	$('#checkDiv').dialog({closed:false});
}

function closeCheckDiv()
{
	$('#checkDiv_inner').hide();
    $('#checkDiv').dialog({closed:true});
}

function loadcheckDIV()
{
	loadcheckDIVInner();
	
	$('#checkDiv').dialog({
                modal:true,
                closed:true,
                buttons:{
                    '确 定':function(){
                        checkSubmit($('#descrition').val(), $('#refMoney').val());
                    },
                    '取 消':function(){
                        $('#checkDiv').dialog({closed:true});
                    }
                }
     });
     
     $ESC('checkDiv');
}

if (window.addEventCommon)
window.addEventCommon(window, 'load', loadcheckDIV);

