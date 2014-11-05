function addTr()
{
    for (var i = 0; i < 1; i++)
    {
        addTrInner();
    }
}

function addTrInner()
{
    var table = $O("tables");
    
    var tr = $O("trCopy");
    
    trow =  table.insertRow(-1);
    
    for (var i = 0; i < tr.cells.length; i++)
    {
        var tcell = document.createElement("td");
        
        tcell.innerHTML = tr.cells[i].innerHTML;
        
        trow.appendChild(tcell);
    }
    
    //tcell = document.createElement("td");
    
    //tcell.innerHTML = '<input type=button value="删除" class=button_class onclick="removeTr(this)">';
    
    trow.appendChild(tcell);
    
    return trow;
}

function removeTr(obj)
{
    //rows
    var table = $O("tables");
    
    if (table.rows.length <= 2)
    {
    	alert('不能全部删除');
    	
    	return false;
    }
    
    obj.parentNode.parentNode.removeNode(true);
    
    
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