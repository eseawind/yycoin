/**
 *	页面翻页机制
 */

//分页设置
var pageRows = 10;
//当前页
var currentPage = 1;
//总页数
var totalPages = 0;
//取值的object
var elementlist = [];
//最初的可以被采用的elenemnt
var init_elementlist = [];
//取值的总行数
var total;
//开始位置
var beginPosition = 0;
//倒数结束位置
var endPosition = 0;
//系统默认选中的element
var autotSelectElement;
//当前TR数组
var currentTrList= [];

var fanye_tagName;

var srcObject;

var callBackFunction;


/**
 * 翻页
 * rowNum 每页记录数
 * obj 翻页实体
 * tagName 翻页的单元对象type 支持 tr table div
 * begin 单元对象的翻页开始位置
 * end 单元对象的翻页结束位置(倒数的绝对位置)
 * autoAdd 是否自动增加翻页的设置
 * fun 翻页后的回调函数,没有自动绑定autoSelects
 */
function initPage(rowNum, obj, tagName, begin, end, autoAdd, fun)
{
	//每页的行数
	if (rowNum > 0)
	{
		pageRows = rowNum;
	}

	if (begin != undefined)
	{
		beginPosition = begin;
	}

	if (end != undefined)
	{
		endPosition = end;
	}

	srcObject = obj;

	currentPage = 1;

	fanye_tagName = tagName;

	//toatl元素
	var arr = obj.getElementsByTagName(tagName);

	//复制list
	var k = 0;
	for (var i = beginPosition; i < arr.length - endPosition; i++)
	{
		elementlist[k] = arr[i];
		init_elementlist[k] = arr[i];
		k++;
	}

	setCallback(fun);

	beginInit(autoAdd);
}

function setCallback(fun)
{
	if (fun)
	callBackFunction = fun;
	else
	callBackFunction = autoSelects;
}

function beginInit(autoAdd)
{
	//总行数
	this.total = elementlist.length;

	//页数
	this.totalPages = Math.ceil(total / pageRows);

	this.currentPage = 1;

	if (autoAdd)
	{
		//是否自动增加最后一行
		initButton(srcObject, total, totalPages);
	}

	//设置当前隐藏的和显示的行
	displayPage();

	//初始化页数选择框
	initSelectElement();
}

/**
 * 初始写入button
 */
function initButton(obj, total, totalPages)
{
	if (fanye_tagName.toUpperCase() != "TR")
	{
		return false;
	}

	var arr = obj.getElementsByTagName(fanye_tagName);

	var tds = arr[0].getElementsByTagName("TD").length;

	//写入最后一个TR
	var html = '<tr align="right" class="content2" id="TR_LAST">';
	html += '<td colspan="' + tds + '"><span id=notesIn></span>&nbsp;&nbsp;<input type="button" id="preButton"';
	html += 'style="cursor: pointer;" value="&nbsp;上一页&nbsp;" class="button_class"';
	html += 'onclick="prePage()">&nbsp;&nbsp; <select';
	html += 'id="pageSelectElement" style="width: 40px;"';
	html += 'onchange=goToSpecialPage($$("pageSelectElement"))>';
	html += '</select> <input type="button" id="nextButton" style="cursor: pointer;"';
	html += 'value="&nbsp;下一页&nbsp;" class="button_class" onclick="nextPage()"></td>';
	html += '</tr>';


	appendTR(obj, html);
}

function initSelectElement()
{
	//获得pageSelectElement
	var oo = document.getElementById("pageSelectElement");
	if (oo != undefined && oo != null)
	{
		for (var i = 1; i <= totalPages; i++)
		{
		    var oOption = document.createElement("OPTION");
	        oOption.text = i;
	        oOption.value= i;
	        oo.add(oOption);
	    }
	}

	displayNote();
}

function displayPage()
{
	hideAll();

	if (total == 0)
	{
		return;
	}

	//IE friefox
	var inline = window.event ? "inline" : "";

	this.autotSelectElement = null;
	//设置当前隐藏的和显示的行
	if (total > pageRows)
	{
		autotSelectElement = elementlist[(currentPage - 1) * pageRows];
		for (var i = 0; i < (currentPage - 1) * pageRows; i++)
	    {
	        elementlist[i].style.display = "none";
	    }

	    var k = 0;
	    var temp = [];
	    if (currentPage * pageRows < total)
	    {
		    for (var i = (currentPage - 1) * pageRows; i < currentPage * pageRows; i++)
		    {
		    	temp[k++] =  elementlist[i];
		        elementlist[i].style.display = inline;
		    }

		    for (var i = currentPage * pageRows; i < total; i++)
		    {
		        elementlist[i].style.display = "none";
		    }
		    currentTrList =  temp;
	    }
	    else
	    {
		    for (var i = (currentPage - 1) * pageRows; i < total; i++)
		    {
		    	temp[k++] =  elementlist[i];
		        elementlist[i].style.display = inline;
		    }
		    currentTrList =  temp;
	    }
    }
    else
    {
    	autotSelectElement = elementlist[0];
    	for (var i = 0; i < total; i++)
	    {
	        currentTrList = elementlist;
	         elementlist[i].style.display = inline;
	    }
    }

    displayButton();

    displayNote();

    if (callBackFunction)
    {
    	callBackFunction.apply(null, [autotSelectElement]);
    }
}

function hideAll()
{
	for (var i = 0; i < init_elementlist.length; i++)
    {
    	init_elementlist[i].style.display = "none";
    }
}

function goToSpecialPage(page)
{
	if (typeof page == 'string')
	{
		page = parseInt(page);
	}

	if (total == 0)
	{
		return -1;
	}

	if (page > totalPages || page < 1)
	{
		return -1;
	}

	currentPage = page;
	//获得pageSelectElement
	var oo = document.getElementById("pageSelectElement");
	{
		if (oo != undefined && oo != null)
		{
			oo.selectedIndex = currentPage - 1;
		}
	}

	displayPage();

	return 0;
}

function displayNote()
{
	var oo = document.getElementById("notesIn");
	if (oo != undefined && oo != null)
	{
    	oo.innerHTML = '总记录:' + total + '  页数:' + currentPage + '/' + totalPages;
    }
}

function displayButton()
{
	var o1 = document.getElementById("preButton");
	var o2 = document.getElementById("nextButton");

	if (o1 == undefined || o1 == null || o2 == undefined || o2 == null)
	{
		return;
	}

	if (totalPages < 2)
	{
		disableButton(o1, true);
		disableButton(o2, true);
		return;
	}

	if (currentPage == 1)
	{
		disableButton(o1, true);
		disableButton(o2, false);
		return;
	}

	if (currentPage == totalPages)
	{
		disableButton(o1, false);
		disableButton(o2, true);
		return;
	}

	disableButton(o1, false);
	disableButton(o2, false);
}

function disableButton(obj, disable){

  if (window.event)
  {
  	  obj.disabled = disable;
  	  return;
  }

  if(disable){
    obj.setAttribute('color_bak', obj.style.color);
    obj.style.color="gray";
  }
  else{
    obj.style.color=obj.getAttribute("color_bak");
  }

  obj.disabled = disable;
}

function prePage()
{
	return goToSpecialPage(currentPage - 1);
}

function nextPage()
{
	return goToSpecialPage(currentPage + 1);
}

var tempDiv_FAN = document.createElement('DIV');

var tempDiv_FAN_PARENT = document.createElement('DIV');

//afterBegin beforeEnd afterEnd befor after
function appendTR(tab, htm)
{
	if (tempDiv_FAN_PARENT.children.length == 0)
	tempDiv_FAN_PARENT.appendChild(tempDiv_FAN);

	if (htm.toUpperCase().indexOf('<TABLE>') == -1)
	{
		htm = '<table>' + htm + '</table>';
	}

	tempDiv_FAN.innerHTML = htm;

	//获得trs
	var trs = tempDiv_FAN.getElementsByTagName('TR');


	//插入到表格的最前面
	var element;
	var top = tab.getElementsByTagName('TR')[0];
	for (var i = 0; i < trs.length;)
	{
		element = trs[i];

		tab.getElementsByTagName('TR')[tab.getElementsByTagName('TR').length - 1].insertAdjacentElement('afterEnd', element);
	}

	if (tempDiv_FAN.removeNode)
	tempDiv_FAN.removeNode(true);
}

/**
 * 页面JS查询
 * s 查询的条件
 * index 索引值
 * qfun 自定义获得过滤的boolean结果
 */
function queryInner(s, index, qfun)
{
	if (trim(s) == '')
	{
		this.elementlist = init_elementlist;
		beginInit(false);
		return;
	}

	var list = init_elementlist;

	this.elementlist = [];

	var j = 0;
    for (var i = 0; i < list.length; i++)
    {
    	var ss;
    	if (qfun)
    	{
    		if (qfun.apply(null, [list[i]]))
    		{
    			elementlist[j++] = list[i];
    		}
    	}
    	else
    	{
    		ss = getText(list[i], index);

    		if (ss == null)
    		{
    			ss = "";
    		}

    		if (ss.toUpperCase().indexOf(s.toUpperCase()) != -1)
	    	{
	    	     elementlist[j++] = list[i];
	    	}
    	}
    }

    beginInit(false);
}

function getText(obj, index)
{
	if (index == 'undefined')
	{
		return trim(obj.innerText);
	}

	return trim(obj.cells[index].innerText);
}

//选择自动联想radio
function autoSelects(obj)
{
	if (obj != null)
	{
		var rad = obj.getElementsByTagName('input');

		if (rad)
		for (i = 0; i < rad.length; i++)
		{
			if (rad[i].type.toLowerCase() == 'radio')
			{
				rad[i].checked = true;
			}
		}
	}
}

function trim(num)
{
    while(true)
    {
        var cI = num.charAt(0) ;
        if(cI != " ")
        {
            break ;
        }
        else
        {
            num = num.substring(1 ,num.length) ;
        }
    }
    while(true)
    {
        var cJ = num.charAt(num.length - 1) ;
        if(cJ != " ")
        {
            break ;
        }
        else
        {
            num = num.substring(0 ,num.length - 1) ;
        }
    }
    return num ;
}