/**
 * Auto-suggest/auto-complete control
 * depends on cnchina.js and public.js
 * original code:
 * (C) 2004-2005 zichun
 *
 * fixes and heavy modifications:
 * (C) 2007 Dmitriy Khudorozhkov (kh_dmitry2001@mail.ru)
 *
 * This software is provided "as-is", without any express or implied warranty.
 * In no event will the author be held liable for any damages arising from the
 * use of this software.
 *
 * Permission is granted to anyone to use this software for any purpose,
 * including commercial applications, and to alter it and redistribute it
 * freely, subject to the following restrictions:
 *
 * 1. The origin of this software must not be misrepresented; you must not
 *    claim that you wrote the original software. If you use this software
 *    in a product, an acknowledgment in the product documentation would be
 *    appreciated but is not required.
 *
 * 2. Altered source versions must be plainly marked as such, and must not be
 *    misrepresented as being the original software.
 *
 * 3. This notice may not be removed or altered from any source distribution.
 */

var suggesturl = ''; // This is the link to the server-side script, that gives you the suggestion list.

// pre-load images:
var image = [];

var HINT_MODE = {"CLIENT" : 0, "SERVER" : 1};

image[0] = new Image(), image[1] = new Image(),
image[2] = new Image(), image[3] = new Image();

image[0].src = "../images/arrow-down.gif", image[1].src = "../images/arrow-down-d.gif";
image[2].src = "../images/arrow-up.gif",   image[3].src = "../images/arrow-up-d.gif";

/**
 * id hint的对象
 * ca 提示数据
 * single 是否支持简拼
 * mode if mode == HINT_MODE.SERVER 请调用hint.flush实时刷新hint，实现ajax的效果
 * dataCall 数据回调函数 当控件促发keydown发生，自动传入参数控件对象
 */
function hint(id, ca, mode, single, dataCall, enterCall)
{
	// Public Variables:
	this.actb_timeOut    = -1;                  // Autocomplete Timeout in ms (-1: autocomplete never time out)
	this.actb_lim        = 20;                   // Number of elements autocomplete can show (-1: no limit)
	this.actb_firstText  = false;                // should the auto complete be limited to the beginning of keyword?
	this.single          = single;                // support single spell
	this.actb_mouse      = true;                // Enable Mouse Support
	this.actb_delimiter  = new Array(';', ','); // Delimiter for multiple autocomplete. Set it to empty array for single autocomplete
	this.actb_startcheck = 0;                   // Show widget only after this number of characters is typed in.

	// Styles:
	this.actb_arColor   = '#656291';  // background color for the "arrows"
	this.actb_bgColor   = '#FFFFFF';
	this.actb_textColor = '#000000';
	this.actb_hColor    = '#D6D7E7';
	this.actb_fFamily   = 'Arial, Helvetica, sans-serif';
	this.actb_arrowSize = "12px";
	this.actb_fontFamily = "Arial, Helvetica, sans-serif";
	this.actb_fSize     = '12px';
	this.actb_hStyle    = 'font-family:Arial, Helvetica, sans-serif;';

	// "Private" Variables:
	this.actb_delimwords = [];
	this.actb_cdelimword = 0;
	this.actb_delimchar  = [];
	this.actb_display    = false;
	this.actb_pos    = 0;
	this.actb_total  = 0;
	this.actb_rangeu = 0;
	this.actb_ranged = 0;
	this.actb_bool   = [];
	this.actb_pre    = 0;
	this.actb_toid   = 0;
	this.actb_tomake = false;
	this.actb_single = single;
	this.actb_dataCall = dataCall;
	this.actb_enterCall = enterCall;
	this.actb_mode   = HINT_MODE.CLIENT;

	if (mode == HINT_MODE.SERVER)
	this.actb_mode   = mode;  //"CLIENT" : 0, "SERVER" : 1

	this.actb_mouse_on_list = 1;
	//阻止事件冒泡
	this.actb_caretmove     = false;
	if (typeof id == 'string')
	this.actb_curr     = document.getElementById(id);
	else
	this.actb_curr     = id;

	this.actb_curr.autocomplete = "off";
	this.actb_keywords = [];
	this.actb_singleKeywords = {};

	for(var i = 0, cl = ca.length; i < cl; i++)
	{
		this.actb_keywords[i] = ca[i];
		if (this.actb_single)
		{
			// single spell
			this.actb_singleKeywords[ca[i]] = getSpell(ca[i], true, false);
		}
	}

	return this.construct();
};

hint.is_ie = ( /msie/i.test(navigator.userAgent) &&
!/opera/i.test(navigator.userAgent) );

hint.prototype = {

	callLater: function(func, obj)
	{ return function() { func.call(obj) }; },

	callLater1: function(func, obj, obj1)
	{ return function() { func.call(obj, obj1) }; },

	construct: function()
	{
		this.actb_curr.actb = this;

		// pre-create event functions
		this.funcClick = this.actb_mouseclick;
		this.funcCheck = this.actb_checkkey;
		this.funcMousewheel = this.callLater1(this.actb_mousewheel, this, this);

		this.funcHighlight = this.actb_table_highlight;

		this.funcClear = this.callLater(this.actb_clear,    this);
		this.funcPress = this.callLater(this.actb_keypress, this);

		this.funcUp   = this.callLater(this.actb_goup,   this);
		this.funcDown = this.callLater(this.actb_godown, this);

		this.funcFocus   = this.callLater(this.actb_table_focus,   this);
		this.funcUnfocus = this.callLater(this.actb_table_unfocus, this);

		addEvent(this.actb_curr, "focus", this.callLater(this.actb_setup, this));

		return this;
	},

	//重新加载提示列表
	reload: function(dataArray)
	{
		this.actb_keywords = [];

		this.actb_singleKeywords = {};

		for(var i = 0, cl = dataArray.length; i < cl; i++)
		{
			this.actb_keywords[i] = dataArray[i];
			if (this.actb_single)
			{
				// single spell
				this.actb_singleKeywords[dataArray[i]] = getSpell(dataArray[i], true, false);
			}
		}
	},

	//重新加载提示列表
	flush: function(dataArray)
	{
		this.reload(dataArray);

		this.actb_displayInner();
	},

	actb_setup: function()
	{
		addEvent(document,       "keydown",  this.funcCheck);
		addEvent(this.actb_curr, "blur",     this.funcClear);
		addEvent(document,       "keypress", this.funcPress);

		// 增加鼠标的滚轴事件
		if (hint.is_ie)
		addEvent(document,       "mousewheel", this.funcMousewheel);
		else
		addEvent(document,       "DOMMouseScroll", this.funcMousewheel);
	},

	actb_clear: function()
	{
		removeEvent(document,       "keydown",  this.funcCheck);
		removeEvent(this.actb_curr, "blur",     this.funcClear);
		removeEvent(document,       "keypress", this.funcPress);

		if (hint.is_ie)
		removeEvent(document,       "mousewheel", this.funcMousewheel);
		else
		removeEvent(document,       "DOMMouseScroll", this.funcMousewheel);

		this.actb_removedisp();
	},

	actb_parse: function(n)
	{
	    var t, plen;
		if(this.actb_delimiter.length > 0)
		{
			   t = this.actb_delimwords[this.actb_cdelimword].trim().addslashes();
			plen = this.actb_delimwords[this.actb_cdelimword].trim().length;
		}
		else
		{
			   t = this.actb_curr.value.addslashes();
			plen = this.actb_curr.value.length;
		}

		var tobuild = '';

		var re = this.actb_firstText ? new RegExp("^" + t, "i") : new RegExp(t, "i");
		var p = n.search(re);

		tobuild = n.substr(0, p);

		tobuild += "<font style='" + (this.actb_hStyle) + "'>";

		tobuild += n.substring(p, plen + p);

		tobuild += "</font>";

		tobuild += n.substring(plen + p, n.length);

		return tobuild;
	},

	// 隐藏select
	actb_hiddenSelect: function(hidden)
	{
		var el = document.getElementById('tat_table');

		// 只有IE6以下的版本才有这个bug
		if (!el || !hint.is_ie)
		{
			return;
		}

		function getVisib(obj)
	    {
	    	is_khtml = /Konqueror|Safari|KHTML/i.test(navigator.userAgent);
	        var value = obj.style.visibility;
	        if (!value)
	        {
	            if (document.defaultView && typeof (document.defaultView.getComputedStyle) == "function")
	            {
	                // Gecko, W3C
	                if (!is_khtml)
	                value = document.defaultView.
	                getComputedStyle(obj, "").getPropertyValue("visibility");
	                else
	                value = '';
	            }
	            else if (obj.currentStyle)
	            {
	                // IE
	                value = obj.currentStyle.visibility;
	            }
	            else
	            value = '';
	        }
	        return value;
	    }
	    ;

		p = window.common.getAbsolutePos(el);

		var EX1 = p.x;
	    var EX2 = p.w + EX1;
	    var EY1 = p.y;
	    var EY2 = p.h + EY1;

	    var ar = document.getElementsByTagName("select");

	    for (var i = ar.length; i > 0;)
        {
            cc = ar[--i];

            p = window.common.getAbsolutePos(cc);
            var CX1 = p.x;
            var CX2 = p.w + CX1;
            var CY1 = p.y;
            var CY2 = p.h + CY1;

            if (!hidden || (CX1 > EX2) || (CX2 < EX1) || (CY1 > EY2) || (CY2 < EY1))
            {
                if (!cc.__msh_save_visibility)
                {
                    cc.__msh_save_visibility = getVisib(cc);
                }
                cc.style.visibility = cc.__msh_save_visibility;
            }
            else
            {
                if (!cc.__msh_save_visibility)
                {
                    cc.__msh_save_visibility = getVisib(cc);
                }

                cc.style.visibility = "hidden";
            }
        }
	},

	actb_generate: function()
	{
		if(document.getElementById('tat_table'))
		{
		  this.actb_display = false;
		  document.body.removeChild(document.getElementById('tat_table'));
		}

		if(this.actb_total == 0)
		{
			this.actb_display = false;
			return;
		}

		a = document.createElement('table');
		a.cellSpacing ='1px';
		a.cellPadding ='2px';
		a.style.position = 'absolute';
		a.style.border = '#000000 solid 1px';
		a.style.top = eval(curTop(this.actb_curr) + this.actb_curr.offsetHeight) + "px";
		a.style.left = curLeft(this.actb_curr) + 1 + "px";
		a.style.width = this.actb_curr.offsetWidth + "px";
		a.style.backgroundColor = this.actb_bgColor;
		a.id = 'tat_table';
		document.body.appendChild(a);

		var first = true, j = 1;

		if(this.actb_mouse)
		{
			a.onmouseout = this.funcUnfocus;
			a.onmouseover = this.funcFocus;
		}

		var counter = 0;
		for(var i = 0; i < this.actb_keywords.length; i++)
		{
			if((this.actb_keywords.length > this.actb_lim) && (this.actb_total > this.actb_lim) && !i)
			{
				var r = a.insertRow(-1);
				r.style.backgroundColor = this.actb_arColor;

				var c = r.insertCell(-1);
				c.style.color = this.actb_textColor;
				c.style.fontFamily = this.actb_fontFamily;
				c.style.fontSize = this.actb_arrowSize;

				c.style.cursor = 'default';
				c.align = 'center';

				replaceHTML(c, image[3]);
				addEvent(c, "click", this.funcUp);
			}

			if(this.actb_bool[i] && (counter < this.actb_lim))
			{
				counter++;
				var r = a.insertRow(-1);
				if(first && !this.actb_tomake)
				{
					r.style.backgroundColor = this.actb_hColor;
					first = false;
					this.actb_pos = counter;
				}
				else if(this.actb_pre == i)
				{
					r.style.backgroundColor = this.actb_hColor;
					first = false;
					this.actb_pos = counter;
				}
				else
					r.style.backgroundColor = this.actb_bgColor;

				r.id = 'tat_tr' + (j);

				var c = r.insertCell(-1);
				c.style.color = this.actb_textColor;
				c.style.fontFamily = this.actb_fFamily;
				c.style.height  = '12px';
				c.style.fontSize = this.actb_fSize;
				c.innerHTML = this.actb_parse(this.actb_keywords[i]);
				c.id = 'tat_td' + (j);
				c.actb = this;
				c.setAttribute('pos', j);

				if(this.actb_mouse)
				{
					c.style.cursor = 'pointer';
					addEvent(c, "click", this.funcClick);
					c.onmouseover = this.funcHighlight;
				}

				j++;
			}
		}

		if(this.actb_total > this.actb_lim)
		{
			var r = a.insertRow(-1);
			r.style.backgroundColor = this.actb_arColor;

			var c = r.insertCell(-1);
			c.style.color = this.actb_textColor;
			c.style.fontFamily = this.actb_fontFamily;
			c.style.fontSize = this.actb_arrowSize;
			c.style.cursor = "pointer";
			c.align = 'center';
			replaceHTML(c, image[0]);

			addEvent(c, "click", this.funcDown);
		}

		this.actb_rangeu = 1;
		this.actb_ranged = j - 1;
		this.actb_display = true;

		if(this.actb_pos <= 0) this.actb_pos = 1;

		this.actb_hiddenSelect(true);
	},

	actb_remake: function()
	{
		var a = document.getElementById('tat_table');

		if(this.actb_mouse && a)
		{
			a.onmouseout  = this.funcUnfocus;
			a.onmouseover = this.funcFocus;
		}

		var i, k = 0;
		var first = true;
		var j = 1;

		if(this.actb_total > this.actb_lim)
		{
		    var b = (this.actb_rangeu > 1);

			var r = a.rows[k++];
			r.style.backgroundColor = this.actb_arColor;

			var c = r.firstChild;
			c.style.color = this.actb_textColor;
			c.style.fontFamily = this.actb_fontFamily;
			c.style.fontSize = this.actb_arrowSize;
			c.style.cursor = 'default';
			c.align = 'center';

			replaceHTML(c, b ? image[2] : image[3]);

			if(b)
			{
				addEvent(c, "click", this.funcUp);
				c.style.cursor = 'pointer';
			}
			else
			{
				c.style.cursor = 'default';
			}
		}

		for(var i = 0; i < this.actb_keywords.length; i++)
		{
			if(this.actb_bool[i])
			{
				if(j >= this.actb_rangeu && j <= this.actb_ranged)
				{
					var r = a.rows[k++];
					r.style.backgroundColor = this.actb_bgColor;
					r.id = 'tat_tr'+(j);

					var c = r.firstChild;
					c.style.color = this.actb_textColor;
					c.style.fontFamily = this.actb_fFamily;
					c.style.fontSize = this.actb_fSize;
					c.innerHTML = this.actb_parse(this.actb_keywords[i]);
					c.id = 'tat_td' + j;
					c.setAttribute('pos', j);
					j++;
				}
				else j++;
			}

			if(j > this.actb_ranged) break;
		}

		if(this.actb_keywords.length > this.actb_lim)
		{
			var b = ((j - 1) < this.actb_total);

			var r = a.rows[k];

			if (r)
			r.style.backgroundColor = this.actb_arColor;
			else
			return;

			var c = r.firstChild;
			c.style.color = this.actb_textColor;
			c.style.fontFamily = this.actb_fontFamily;
			c.style.fontSize = this.actb_arrowSize;
			c.style.cursor = 'default';
			c.align='center';

			replaceHTML(c, b ? image[0] : image[1]);

			if(b)
			{
				addEvent(c, "click", this.funcDown);
				c.style.cursor = 'pointer';
			}
			else
			{
				c.style.cursor = 'default';
			}
		}
	},

	actb_goup: function(end)
	{
		this.actb_curr.focus();

		if(!this.actb_display) return;

		var t = document.getElementById('tat_tr' + this.actb_pos);
		if(t && t.style) t.style.backgroundColor = this.actb_bgColor;

		if(end || this.actb_pos == 1)
		{
		    this.actb_pos = this.actb_total;

		    this.actb_rangeu = this.actb_total - this.actb_lim + 1;

		    this.actb_ranged = this.actb_total;

		    this.actb_remake();
		}
		else
		{
		    this.actb_pos--;
		    if(this.actb_pos < this.actb_rangeu)
		    {
			this.actb_rangeu--;
			this.actb_ranged--;
			this.actb_remake();
		    }
		}



		t = document.getElementById('tat_tr' + this.actb_pos);
		if(t && t.style) t.style.backgroundColor = this.actb_hColor;

		if(this.actb_toid)
		{
			clearTimeout(this.actb_toid);
			this.actb_toid = 0;
		}

		if(this.actb_timeOut > 0)
		  this.actb_toid = setTimeout(function() { this.actb_mouse_on_list = 1; this.actb_removedisp(); }, this.actb_timeOut);

		this.actb_curr.focus();
	},

	actb_displayInner: function()
	{
	    this.actb_display = true;

	    var obj = this.actb_curr.actb;

	    obj.actb_toid = setTimeout(function() { obj.actb_tocomplete.call(obj, 0) }, 500);

	    this.actb_generate();
	},

	actb_godown: function(home, gun)
	{
		this.actb_curr.focus();

		if (gun)
		{
			if(!this.actb_display)return;
		}

		if(!this.actb_display)
		{
		    this.actb_displayInner();
		    return;
		}

		// 把先前的背景去掉
		var t = document.getElementById('tat_tr' + this.actb_pos);
		if(t && t.style) t.style.backgroundColor = this.actb_bgColor;

		if(home || this.actb_pos == this.actb_total)
		{
		    this.actb_pos = 1;

		    this.actb_rangeu = 1;

		    this.actb_ranged = this.actb_lim;

		    this.actb_remake();
		}
		else
		{
		    this.actb_pos++;

		    if(this.actb_pos > this.actb_ranged)
		    {
			this.actb_rangeu++;
			this.actb_ranged++;
			this.actb_remake();
		    }
		}


		// 换上新的背景
		t = document.getElementById('tat_tr' + this.actb_pos);
		if(t && t.style) t.style.backgroundColor = this.actb_hColor;

		if(this.actb_toid)
		{
			clearTimeout(this.actb_toid);
			this.actb_toid = 0;
		}

		if(this.actb_timeOut > 0)
		  this.actb_toid = setTimeout(function() { this.actb_mouse_on_list = 1; this.actb_removedisp(); }, this.actb_timeOut);

		this.actb_curr.focus();
	},

	actb_mouseclick: function(event)
	{
		var elem = getTargetElement(event);
		if(!elem.id) elem = elem.parentNode;

		var obj = elem.actb;
		if(!obj.actb_display) return;

		obj.actb_mouse_on_list = 0;
		obj.actb_pos = elem.getAttribute('pos');
		obj.actb_penter();
	},

	actb_table_focus: function()
	{ this.actb_mouse_on_list = 1; },

	actb_table_unfocus: function()
	{
		this.actb_mouse_on_list = 0;

		if(this.actb_toid)
		{
			clearTimeout(this.actb_toid);
			this.actb_toid = 0;
		}

		if(this.actb_timeOut > 0)
			this.actb_toid = setTimeout(function() { obj.actb_mouse_on_list = 0; this.actb_removedisp(); }, this.actb_timeOut);
	},

	actb_table_highlight: function(event)
	{
		var elem = getTargetElement(event);

		var obj = elem.actb;
		if(!obj) return;

		obj.actb_mouse_on_list = 1;

		var row = document.getElementById('tat_tr' + obj.actb_pos);
		if(row && row.style) row.style.backgroundColor = obj.actb_bgColor;

		obj.actb_pos = elem.getAttribute('pos');

		row = document.getElementById('tat_tr' + obj.actb_pos);
		if(row && row.style)
			row.style.backgroundColor = obj.actb_hColor;

		if(obj.actb_toid)
		{
			clearTimeout(obj.actb_toid);
			obj.actb_toid = 0;
		}

		if(obj.actb_timeOut > 0)
			obj.actb_toid = setTimeout(function(){ obj.actb_mouse_on_list = 0; obj.actb_removedisp(); }, obj.actb_timeOut);
	},

	actb_insertword: function(a)
	{
		if(this.actb_delimiter.length > 0)
		{
			var str = '';

			for(i = 0; i < this.actb_delimwords.length; i++)
			{
				if(this.actb_cdelimword == i)
				{
					prespace = postspace = '';
					gotbreak = false;
					for(j = 0; j < this.actb_delimwords[i].length; ++j)
					{
						if(this.actb_delimwords[i].charAt(j) != ' ')
						{
							gotbreak = true;
							break;
						}

						prespace += ' ';
					}

					for(j = this.actb_delimwords[i].length - 1; j >= 0; --j)
					{
						if(this.actb_delimwords[i].charAt(j) != ' ') break;
						postspace += ' ';
					}

					str += prespace;
					str += a;
					if(gotbreak) str += postspace;
				}
				else
					str += this.actb_delimwords[i];

				if(i != this.actb_delimwords.length - 1)
					str += this.actb_delimchar[i];
			}

			this.actb_curr.value = str;
			setCaret(this.actb_curr, this.actb_curr.value.length);
		}
		else
			this.actb_curr.value = a;

		this.actb_mouse_on_list = 0;
		this.actb_removedisp();
	},

	actb_penter: function()
	{
		if(!this.actb_display) return;

		this.actb_display = false;
		var word = '';
		var c = 0;

		for(var i = 0; i <= this.actb_keywords.length; i++)
		{
			if(this.actb_bool[i]) c++;
			if(c == this.actb_pos)
			{
				word = this.actb_keywords[i];
				break;
			}
		}

		this.actb_insertword(word);
		
		if (this.actb_enterCall)
		this.actb_enterCall.call(this, this.actb_curr);
	},
	
	//直接插入
	actb_penter2: function()
	{
		var word = '';
		var c = 0;

		for(var i = 0; i <= this.actb_keywords.length; i++)
		{
			if(this.actb_bool[i]) c++;
			if(c == this.actb_pos)
			{
				word = this.actb_keywords[i];
				break;
			}
		}
		
		this.actb_curr.value = word;
	},

	actb_removedisp: function()
	{
		if(this.actb_mouse_on_list == 0)
		{
			this.actb_display = 0;

			this.actb_hiddenSelect(false);

			if(document.getElementById('tat_table'))
			  document.body.removeChild(document.getElementById('tat_table'));

			if(this.actb_toid)
			{
			  clearTimeout(this.actb_toid);
			  this.actb_toid = 0;
			}
		}
	},

	actb_keypress: function(e)
	{
		if(this.actb_caretmove) stopEvent(e);
		return !this.actb_caretmove;
	},

	getEvent: function ()
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
	    	//alert(func.arguments.callee.caller.arguments[0].constructor.toString());

	        var eve = func.arguments.callee.arguments[0];

	        if (eve != null && eve.constructor && eve.constructor.toString().indexOf('Event') != -1)
	        {
	        	return eve;
	        }

	        func = func.caller;
	    }

	    return null;
	},

	//增加滚轴事件的监听
	actb_mousewheel: function(hint)
	{
		var obj = hint;

		eve = getEvent();

		obj.actb_caretmove = 0;

		if(obj.actb_toid)
		{
			clearTimeout(obj.actb_toid);
			obj.actb_toid = 0;
		}

		if (hint.is_ie)
		{
			if (eve.wheelDelta >= 120)
			{
				obj.actb_goup();
				obj.actb_caretmove = 1;
				return false;
			}
			else if (eve.wheelDelta <= -120)
			{
				obj.actb_godown(false, true);
				obj.actb_caretmove = 1;
				return false;
			}
		}
		else
		{
			if ( eve.wheelDelta <= 0 || eve.detail > 0)
			{
				obj.actb_godown(false, true);
				obj.actb_caretmove = 1;
				return false;
			}
			else
			{
				obj.actb_goup();
				obj.actb_caretmove = 1;
				return false;
			}
		}
	},

	actb_checkkey: function(event)
	{
		event = event || window.event;

		var code = event.keyCode;
		var el = getTargetElement(event);
		var obj = el.actb;
		obj.actb_caretmove = 0;

		var term = "";

		if(obj.actb_toid)
		{
			clearTimeout(obj.actb_toid);
			obj.actb_toid = 0;
		}

		// 36 Home 35 End
		switch(code)
		{
			// up
			case 38:
				obj.actb_goup();
				obj.actb_caretmove = 1;
				obj.actb_penter2();
				return false;
				break;
			// down
			case 40:
				obj.actb_godown();
				obj.actb_caretmove = 1;
				obj.actb_penter2();
				return false;
				break;

			// Home
			case 36:
				if(!obj.actb_display)
				{
					return true;
					break;
				}
				obj.actb_godown(true);
				obj.actb_caretmove = 1;
				obj.actb_penter2();
				return false;
				break;
			// End
			case 35:
				if(!obj.actb_display)
				{
					return true;
					break;
				}

				obj.actb_goup(true);
				obj.actb_caretmove = 1;
				obj.actb_penter2();
				return false;
				break;

			// esc
			case 27:
				term = obj.actb_curr.value;

				obj.actb_mouse_on_list = 0;
				obj.actb_removedisp();
				break;

			// enter
			case 13:
				if(obj.actb_display)
				{
					obj.actb_caretmove = 1;
					obj.actb_penter();
					return false;
				}
				break;

			// TAB
			case 9:
				if(obj.actb_display || obj.actb_toid)
				{
					obj.actb_caretmove = 1;
					obj.actb_penter();

					setTimeout(function() { obj.actb_curr.focus(); }, 25);
					return false;
				}
				break;

			default:

				// 等待keydown时间结束
			    if (obj.actb_mode == HINT_MODE.SERVER)
			    {
			    	if (obj.actb_dataCall)
			    	obj.actb_toid = setTimeout(function() { obj.actb_dataCall.call(obj, el) }, 500);
			    }
			    else
				obj.actb_toid = setTimeout(function() { obj.actb_tocomplete.call(obj, code) }, 500);
				break;
		}

		if(term.length) setTimeout(function() { obj.actb_curr.value = term; }, 25);
		return true;
	},

	actb_tocomplete: function(kc)
	{
		if(this.actb_toid)
		{
			clearTimeout(this.actb_toid);
			this.actb_toid = 0;
		}
		else return;

		if(kc == 38 || kc == 40 || kc == 13) return;

		if(this.actb_display)
		{
			var word = 0;
			var c = 0;

			for(var i = 0; i <= this.actb_keywords.length; i++)
			{
				if(this.actb_bool[i]) c++;

				if(c == this.actb_pos)
				{
					word = i;
					break;
				}
			}

			this.actb_pre = word;
		}
		else this.actb_pre = -1;

		if(this.actb_curr.value == '')
		{
			this.actb_mouse_on_list = 0;
			this.actb_removedisp();
		}

		var ot, t;

		if(this.actb_delimiter.length > 0)
		{
			var caret_pos_end = this.actb_curr.value.length;

			var delim_split = '';
			for(var i = 0; i < this.actb_delimiter.length; i++)
				delim_split += this.actb_delimiter[i];

		    delim_split = delim_split.addslashes();
			var delim_split_rx = new RegExp("([" + delim_split + "])");
			c = 0;
			this.actb_delimwords = [];
			this.actb_delimwords[0] = '';

			for(var i = 0, j = this.actb_curr.value.length; i < this.actb_curr.value.length; i++, j--)
			{
				if(this.actb_curr.value.substr(i, j).search(delim_split_rx) == 0)
				{
					ma = this.actb_curr.value.substr(i,j).match(delim_split_rx);
					this.actb_delimchar[c] = ma[1];
					c++;
					this.actb_delimwords[c] = '';
				}
				else
					this.actb_delimwords[c] += this.actb_curr.value.charAt(i);
			}

			var l = 0;
			this.actb_cdelimword = -1;
			for(i = 0; i < this.actb_delimwords.length; i++)
			{
				if((caret_pos_end >= l) && (caret_pos_end <= l + this.actb_delimwords[i].length))
					this.actb_cdelimword = i;

				l += this.actb_delimwords[i].length + 1;
			}

			ot = this.actb_delimwords[this.actb_cdelimword].trim();
			 t = this.actb_delimwords[this.actb_cdelimword].addslashes().trim();
		}
		else
		{
			ot = this.actb_curr.value;
			 t = this.actb_curr.value.addslashes();
		}

		// 如果没有值取全部
		if(ot.length < 0)
		{
			this.actb_mouse_on_list = 0;
			this.actb_removedisp();
		}
		else if((ot.length == 1) || (ot.length == 0) ||
		       ((ot.length > 1) && !this.actb_keywords.length) ||
		       ((ot.length > 1) && (this.actb_keywords[0].substr(0, 1) != ot.substr(0, 1))))
		{
			var ot_ = (ot.length > 1) ? ot.substr(0, 1) : ot;
			var res = this.actb_curr.value;

			if(suggesturl.length)
			{
				// create xmlhttprequest object:
				var http = null;
				if(typeof XMLHttpRequest != 'undefined')
				{
					try
					{
						http = new XMLHttpRequest();
					}
					catch (e) { http = null; }
				}
				else
				{
					try
					{
						http = new ActiveXObject("Msxml2.XMLHTTP") ;
					}
					catch (e)
					{
						try
						{
							http = new ActiveXObject("Microsoft.XMLHTTP") ;
						}
						catch (e) {	http = null; }
					}
				}

				if(http)
				{
					// For local debugging in Mozilla/Firefox only!
					/*try
					{
						netscape.security.PrivilegeManager.enablePrivilege("UniversalBrowserRead");
					} catch (e) { }*/

					if(http.overrideMimeType)
						http.overrideMimeType('text/xml');

					http.open("GET", suggesturl + "?str=" + ot_, true);

					var obj = this;
					http.onreadystatechange = function(n)
					{
						if(http.readyState == 4)
						{
							if((http.status == 200) || (http.status == 0))
							{
								var xmlDocument = null, tmpinfo = null;

								try
								{
									xmlDocument = http.responseXML;
									tmpinfo = xmlDocument.getElementsByTagName('listdata').item(0).firstChild.data;
								}
								catch(e)
								{
									try
									{
										xmlDocument = (new DOMParser()).parseFromString(http.responseText, "text/xml");
										tmpinfo = xmlDocument.getElementsByTagName('listdata').item(0).firstChild.data;
									}
									catch(ee) {}
								}

								obj.actb_keywords = tmpinfo.split('|');
								obj.done.call(obj, ot_, t);
							}
						}
					}

					http.send(null);
				}

				// xmlhttp object creation failed
				return;
			}
			else this.done(ot, t);
		}
		else this.done(ot, t);
	},

	done: function(ot, t)
	{
		if(ot.length < this.actb_startcheck) return;

		var re = new RegExp("^" + t, "i");

		var re1 = new RegExp("\S*" + t, "i");

		var allS = false;
		if (ot == '' || ot == null)
		{
		    allS = true;
		}

		// 如果是服务端的则全部显示不过滤
		if (this.actb_mode == HINT_MODE.SERVER)
		{
			allS = true;
		}

		this.actb_total = 0;
		this.actb_tomake = false;

		var al = this.actb_keywords.length;

		for(var i = 0; i < al; i++)
		{
			this.actb_bool[i] = false;
			if(allS || re.test(this.actb_keywords[i]) || re1.test(this.actb_singleKeywords[this.actb_keywords[i]]))
			{
				this.actb_total++;
				this.actb_bool[i] = true;

				if(this.actb_pre == i) this.actb_tomake = true;
			}
		}

		if(!this.actb_firstText)
		{
			var tmp = [];

			for(i = 0; i < al; i++)
			{
				if(this.actb_bool[i])
					tmp[tmp.length] = this.actb_keywords[i];
			}

			re = new RegExp(t, "i");

			for(i = 0; i < al; i++)
			{
				if(re.test(this.actb_keywords[i]) && !this.actb_bool[i])
				{
					this.actb_total++;
					this.actb_bool[i] = true;

					if(this.actb_pre == i) this.actb_tomake = true;

					tmp[tmp.length] = this.actb_keywords[i];
				}
			}

			for(i = 0; i < al; i++)
			{
				if(!this.actb_bool[i])
					tmp[tmp.length] = this.actb_keywords[i];
			}

			for(i = 0; i < al; i++)
				this.actb_keywords[i] = tmp[i];

			for(i = 0; i < al; i++)
				this.actb_bool[i] = (i < this.actb_total) ? true : false;
		}

		if(this.actb_timeOut > 0)
		  this.actb_toid = setTimeout(function(){ this.actb_mouse_on_list = 0; this.actb_removedisp(); }, this.actb_timeOut);

		this.actb_generate();
	}
}

// Supplementary functions

// Add an event to the obj given
// event_name refers to the event trigger, without the "on", like click or mouseover
// func_name refers to the function callback when event is triggered
function addEvent(obj, event_name, func_ref)
{
	if(obj.addEventListener && !window.opera)
	{
		obj.addEventListener(event_name, func_ref, true);
	}
	else obj["on" + event_name] = func_ref;
}

// Removes an event from the object
function removeEvent(obj, event_name, func_ref)
{
	if(obj.removeEventListener && !window.opera)
	{
		obj.removeEventListener(event_name, func_ref, true);
	}
	else obj["on" + event_name] = null;
}

// Stop an event from bubbling up the event DOM
function stopEvent(event)
{
	event = event || window.event || getEvent();

	if(event.stopPropagation) event.stopPropagation();
	if(event.preventDefault) event.preventDefault();

	if(typeof event.cancelBubble != "undefined")
	{
		event.cancelBubble = true;
		event.returnValue = false;
	}

	return false;
}

// Get the obj that triggers off the event
function getTargetElement(event)
{
	event = event || window.event;
	return event.srcElement || event.target;
}

// Sets the caret position to l in the object
function setCaret(obj, l)
{
	obj.focus();

	if(obj.setSelectionRange)
	{
		obj.setSelectionRange(l, l);
	}
	else if(obj.createTextRange)
	{
		m = obj.createTextRange();
		m.moveStart('character', l);
		m.collapse();
		m.select();
	}
}

// String functions
String.prototype.addslashes = function() { return this.replace(/(["\\\.\|\[\]\^\*\+\?\$\(\)])/g, '\\$1'); }

String.prototype.trim = function () { return this.replace(/^\s*(\S*(\s+\S+)*)\s*$/, "$1"); };

// Offset position from top of the screen
function curTop(obj)
{
	var toreturn = 0;
	while(obj)
	{
		toreturn += obj.offsetTop;
		obj = obj.offsetParent;
	}

	return toreturn;
}

// Offset position from left of the screen
function curLeft(obj)
{
	var toreturn = 0;
	while(obj)
	{
		toreturn += obj.offsetLeft;
		obj = obj.offsetParent;
	}

	return toreturn;
}

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
    	//alert(func.arguments.callee.caller.arguments[0].constructor.toString());

        var eve = func.arguments.callee.arguments[0];

        if (eve != null && eve.constructor && eve.constructor.toString().indexOf('Event') != -1)
        {
        	return eve;
        }

        func = func.caller;
    }

    return null;
}

// Image installation
function replaceHTML(obj, oImg)
{
	var el = obj.childNodes[0];
	while(el)
	{
		obj.removeChild(el);
		el = obj.childNodes[0];
	}

	obj.appendChild(oImg);
}