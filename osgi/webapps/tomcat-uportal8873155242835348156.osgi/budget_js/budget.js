function addTr()
{
    var table = $O("tables");
    
    var tr = $O("trCopy0");
    
    trow =  table.insertRow(-1);
    
    if (length % 2 == 1)
    {
        trow.className = 'content2';
    }
    else
    {
        trow.className = 'content1';
    }
    
    for (var i = 0; i < tr.cells.length; i++)
    {
        var tcell = document.createElement("td");
        
        tcell.innerHTML = tr.cells[i].innerHTML;
        
        trow.appendChild(tcell);
    }
    
    //tcell = document.createElement("td");
    
    //tcell.innerHTML = '<input type=button value="删除" class=button_class onclick="removeTr(this)">';
    
    //trow.appendChild(tcell);
    
    var inputs = trow.getElementsByTagName('input');
    
    for(var i = 0; i < inputs.length; i++)
    {
    	if (inputs[i].type != 'button')
    	{
    	   inputs[i].value = '';
    	}
    }
    
    var tas= trow.getElementsByTagName('textarea');
    
    for(var i = 0; i < tas.length; i++)
    {
        tas[i].value = '';
    }
    
    var sels= trow.getElementsByTagName('select');
    
    for(var i = 0; i < sels.length; i++)
    {
    	sels[i].hasspell = false;
        quickSelect(sels[i]);
        setSelectIndex(sels[i], 0);
    }
    
    return trow;
}

function removeTr(obj)
{
    obj.parentNode.parentNode.removeNode(true);
    //rows
    var table = $O("tables");
    
    for (var i = 2; i < table.rows.length; i++)
    {
        if (i % 2 == 1)
        {
            table.rows[i].className = 'content1';
        }
        else
        {
            table.rows[i].className = 'content2';
        }
    }
}

function check()
{
    var names = document.getElementsByName('item_name');
    for (var i = 0; i < names.length; i++)
    {
        for (var k = i + 1; k < names.length; k++)
        {
            if (names[i].value == names[k].value)
            {
                alert('预算项不能相同:' + getOptionText(names[i]));
                return false;
            }
        }
    }
    
    return true;
}

function viewother()
{
	var pid = $$('parentId');
	
	if (pid == '0')
	{
		alert('没有相关的预算');
		return false;
	}
	
	window.common.modal('../budget/budget.do?method=queryReference&pid=' + pid);
}
