/**
 * dialog的适配器
 */
function preload()
{
    $('#modalQuery').dialog({
                iconCls: 'icon-search',
                modal:true,
                closed: true,
                buttons:{
                    '查 询':function(){
                        querySure();
                    },
                    '重 置':function(){
                        resetsModal();
                    },
                    '重置查询':function(){
                        resetsModalAndQuery();
                    },
                    '关 闭':function(){
                        closeModal();
                    }
                }
     });
     
     $ESC('modalQuery');
}

function resetsModal()
{
	var div = $O('dialog_inner');
	
    var inputs = div.getElementsByTagName('input');
    
    for(var i = 0; i < inputs.length; i++)
    {
        if (!inputs[i].readonly && inputs[i].type != 'hidden' && inputs[i].type != 'button')
        {
            inputs[i].value = '';
        }
    }
    
    var selects = div.getElementsByTagName('select');
    
    for(var i = 0; i < selects.length; i++)
    {
        if (!selects[i].readonly)
        {
            setSelectIndex(selects[i], 0);
        }
    }
}

function resetsModalAndQuery()
{
    resetsModal();
    
    querySure();
}

function enterKeyPress(fun)
{
    if (window.common.getEvent().keyCode == 13)
    {
        fun.apply(null);
    }
    
    if (window.common.getEvent().keyCode == 27)
    {
        $('#modalQuery').dialog({closed: true});
    }
}

var inti_query_modal = 0;

function $modalQuery(url)
{
	if (true || inti_query_modal == 0)
	{
		$ajax(url, loadHTML);
	}
	else
	{
		openModal();
	}
}

function loadHTML(data)
{
	$('#dialog_inner').html(data.key);
	
	loadDiv($O('dialog_inner'));
	
	inti_query_modal = 1;
	
    $('#modalQuery').dialog({closed: false});
    
	loadF();
}

function loadF()
{
	var div = $O('dialog_inner');
	
    var inputs = div.getElementsByTagName('input');
    
    for(var i = 0; i < inputs.length; i++)
    {
        if (inputs[i].frister && inputs[i].frister == '1')
        {
            $f(inputs[i]);
        }
    }
}

function closeModal()
{
	$('#modalQuery').dialog({closed: true});
}

function openModal()
{
    $('#modalQuery').dialog({closed: false});
}

function querySure()
{
    var par = {};
    
    var div = $O('dialog_inner');
    
    var inputs = div.getElementsByTagName('input');
    
    for(var i = 0; i < inputs.length; i++)
    {
        if (!isNoneInCommon(inputs[i].value) && inputs[i].type != 'button')
        {
            par[inputs[i].name] = inputs[i].value;
        }
    }
    
    var selects = div.getElementsByTagName('select');
    
    for(var i = 0; i < selects.length; i++)
    {
        var str = $$(selects[i].name);
        
        if (!isNoneInCommon(str))
        {
            par[selects[i].name] = str;
        }
    }

    commonQuery(par);
    
    closeModal();
}

var keyArray = [];

var divArray = [];

function $ESC(id)
{
	 if (typeof id == 'string')
	 {
	 	divArray.push(id);
	 }
	 
	 if (typeof id == 'object')
	 {
	 	 divArray.concat(id);
	 }
	 
	 // bing Esc to close modal query
     document.onkeydown = function ()
     {
        //防止body.onmouseup已经绑定
        if (document.body.onkeydown == null)
        {
            var event = window.common.getEvent();
            
            if (event.keyCode == 27)
            {
        		for (var i = 0; i < divArray.length; i++)
        		{
        			$('#' + divArray[i]).dialog({closed:true});
        		}
            	
                return false;
            }
        }
     }
}