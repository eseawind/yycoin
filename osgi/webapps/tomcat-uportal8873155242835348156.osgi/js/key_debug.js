//双击右键返回(或者ESC) 记录连续的两次鼠标事件，间隔不超过500ms 
/**
 * How to use:
 * in body tag, add this content【onmouseup="listenerMouse(event, goProduct)"】
 * if you only want to go back, you just do that do nothing
 */
var interval = 500;
var mouseClick1 = 0;
var mouseClick2 = 0;

function listenerMouse(evt, url)
{
	//右键
	if (evt.button == 2)
	{
		if (mouseClick2 == 0)
		{
			mouseClick2 = new Date().getTime();
			return;
		}
		
		if (mouseClick1 == 0)
		{
			mouseClick1 = new Date().getTime();
			
			
			cgoBack(mouseClick1, mouseClick2, url);
			
			return;
		}
		
		//这里12都已经满了，那么开始1进2出
		mouseClick2 = mouseClick1;
		
		mouseClick1 = new Date().getTime();
		
		cgoBack(mouseClick1, mouseClick2, url);
		
		return;
	}
	else
	{
		mouseClick1 = 0;
		mouseClick2 = 0;
	}
	
}

function cgoBack(l1, l2, url)
{
	if (l1 - l2 < interval)
	{
		if (url)
		{
			if (typeof url == 'function')
			{
				url.call(this);
			}
			
			if (typeof url == 'string')
			{
				document.location.href = url;
			}
			else
			{
				// TODO other
			}
		}
		else
		{
			window.history.go(-1);
		}
	}
}
/**
 * 获得event对象
 */
function getEvent()
{
	// IE
	if (window.event)
   	{
   		return window.event;
   	}
    	
    var func = this.getEvent;
    
    while(func != null)
    {
    	//返回调用函数的【arguments.callee】(callee是arguments的一个属性值)
    	//callee:返回正被执行的 Function 对象,这里就是func
    	//caller:返回一个对函数的引用
    	
        var eve = func.arguments.callee.arguments[0];
        
        if (eve != null && eve.constructor && eve.constructor.toString().indexOf('Event') != -1)
        {
        	return eve;
        }
        
        func = func.caller;
    }
    
    return null;
}

//绑定右键
document.onmouseup = function ()
{
	//防止body.onmouseup已经绑定
	if (document.body.onmouseup == null)
	{
		var event = getEvent();
    	listenerMouse(event);
    }
}

//屏蔽右键
document.oncontextmenu = function()
{
	if (document.body.oncontextmenu == null)
	{
		 return false;
	}	
}

//ESC 返回
document.onkeydown = function ()
{
	//防止body.onmouseup已经绑定
	if (document.body.onkeydown == null)
	{
		var event = getEvent();
		
		if (event.keyCode == 27)
		{
			if (window.esc_back)
			{
				esc_back();
				return false;
			}
			
    		window.history.go(-1);
    		
    		return false;
		}
    }
}

