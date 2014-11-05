/**
 * 总部核对
 */
function loadcheckDIVInner()
{
	if ($('#checkDiv').length > 0)
	{
		return;
	}
	
	var html = '<div id="checkDiv" title="总部核对" style="width:320px;height:250px;">';
	
	html += ' <div style="padding:20px;height:100px;display:none" id="checkDiv_inner" title="" >';

	html += '意见：<br><textarea style="width:95%" id="checks" name="checks"></textarea><br><br>';
	
	html += '关联单据：<br><input style="width:95%" type="text" name="checkrefId" id="checkrefId" value="" maxlength="40"><br>';
	
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
                        checkSubmit($('#checks').val(), $('#checkrefId').val());
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

