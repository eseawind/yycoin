function MyCheck()
{}

function isNone(obj)
{
	if (isNull(obj))
	{
		return true;
	}
	
	if (typeof obj == 'string')
	{
		if (obj == '')
		{
			return true;
		}
	}
	
	return false;
}

function isNull(obj)
{
	if (obj == undefined || obj == null)
	{
		return true;
	}
	
	return false;
}

function trimInCheck(num)
{
    if (isNone(num))
    {
        return '';
    }
    
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

/**
 * 检测一个字符串的真实长度（一个汉字算2个字节）
 */
function getLengthInCheck(str)
{
    var length2 = str.length;
    for(var i = 0; i < str.length; i++)
    {
		if(str.charAt(i)>'~' || str.charAt(i)<' ')
		{
		    length2++;
		}
    }
	
    return length2;
}

function getNowDateInCheck()
{
	var crtDate = new Date();
	var tmpMonth = crtDate.getMonth() + 1;
	var tmpDay = crtDate.getDate();
	if  (tmpMonth < 10)
	{
		tmpMonth = '0' + tmpMonth;
	}
	if (tmpDay < 10)
	{
		tmpDay = '0' + tmpDay;
	}
	
	return crtDate.getFullYear() + '-' + tmpMonth + '-' + tmpDay;
}

function checkDateInCheck(dateStr)
{
    //日期格式正则表达式，格式为"yyyy-MM-dd"
    var re = /^\d{4}-[0-1][0-9]-[0-3][0-9]$/;
    return re.test(dateStr)
}

MyCheck.prototype = ( 
{
	//内部value对象
	innerObj : null,
	
	//是否alert信息
	show: true,
    
    index: 'value',
    
    bingFun: null,
    
    head: 'head',
	
	//getvalue
	getValue:
	function()
	{
		if (this.innerObj.type.toLowerCase() == 'select')
		{
			return this.innerObj.options[this.innerObj.selectedIndex][this.index];
		}
		
		if (this.innerObj.type.toLowerCase() == 'radio' || this.innerObj.type.toLowerCase() == 'checkbox')
		{
			var obj = document.getElementsByName(this.innerObj.name);
			for (var i = 0; i < obj.length; i++)
			{
				if (obj[i].checked)
				{
					return obj[i][this.index];
				}
			}
			
			return "";
		}
		
		if (isNull(this.innerObj[this.index]))
		{
			return null;
		}
		else
		{
			return this.innerObj[this.index];
		}
	},
	
	//getHead
	getHead:
	function()
	{
		if (isNull(this.innerObj[this.head]))
		{
			return '';
		}
		else
		{
			return this.innerObj[this.head];
		}
	},
	
	innerCheck:
	function()
	{
		//如果value没有值
		if (isNull(this.getValue()))
		{
			alert('没有value属性');
			return false;
		}
		
		return true;
	},
	
	//alert默认或者自定义的提示
	showMessage:
	function(defaultMessage, args)
	{
		if (this.show)
		if (isNull(args) || isNull(args[0]) || args[0] == true)
		{
			if (!isNone(this.innerObj.message))
			{
				alert(this.innerObj.message);
			}
			else
			{
				if (!isNone(defaultMessage))
				{
					alert(this.getHead() + ' ' + defaultMessage);
				}
			}
		}		
	},
	
	//是否为空
	notNone : 
	function()
	{
		//去空后
		if (trimInCheck(this.getValue()) == '')
		{
			var defaultMessage = '不能为空';
			
			this.showMessage(defaultMessage, arguments);
			
			return false;
		}
		else
		{
			return true;
		}
	},
	
	//正则校验
	isReg:
	function(reg, defaultMessage)
	{
		var value = trimInCheck(this.getValue());
		
		var arr = [true];
		
		if (!isNull(arguments[2]))
		{
			arr[0] = arguments[2];
		}
		
		if (!reg.test(value))
		{
			this.showMessage(defaultMessage, arr);
			
			return false;
		}
		else
		{
			return true;
		}
	},
	
	//仅仅是数字(空算正确)
	isNumber:
	function()
	{
		var reg = /^[0-9]*$/;
		
		var defaultMessage = '只能是数字';
		
		return this.isReg(reg, defaultMessage);
	},
	
	//仅仅是数字(空算错误)
	isOnlyNumber:
	function()
	{
		var reg = /^[0-9]+$/;
		
		var defaultMessage = '只能是数字';
		
		return this.isReg(reg, defaultMessage);
	},
	
	//仅仅是数字(空算错误,可以有负号)
	isMathNumber:
	function()
	{
		var reg = /^[0-9]+$/;
    	var reg1 = /^-{1}[0-9]+$/;
		
		var defaultMessage = '只能是数字';
		
		if (this.isReg(reg, null, false) || this.isReg(reg, null, false))
		{
			return true;
		}
		else
		{
			this.showMessage(defaultMessage, arguments);
			return false;
		}
	},
	
	maxLength:
	function(parmters)
	{
		var length;
		if (typeof parmters == 'number')
		{
			length = parmters;
		}
		else
		{
			alert('参数不是数值');
			return false;
		}
		
		if (getLengthInCheck(this.getValue()) > length)
		{
			var defaultMessage = '长度不能超过' + length;
			
			this.showMessage(defaultMessage);
			
			return false;
		}
		else
		{
			return true;
		}
	},
	
	isChecked:
	function()
	{
		var flag = false;
		if (this.innerObj.type.toLowerCase() == 'radio' || this.innerObj.type.toLowerCase() == 'checkbox')
		{
			var obj = document.getElementsByName(this.innerObj.name);
			for (var i = 0; i < obj.length; i++)
			{
				if (obj[i].checked)
				{
					flag = true;
				}
			}
		}
		
		if (!flag)
		{
			var defaultMessage = '选择不能为空';
			
			this.showMessage(defaultMessage, arguments);
			
			return false;
		}
		else
		{
			return true;
		}
	},
	
	isCommonChar:
	function()
	{
		var regText = /^[^`~@#\$%\^&\*=\!\+\\\/\|<>\?;\:\.'"\{\}\[\]　, ]*$/;
		if (!isNone(arguments[0]))
		{
			if (typeof arguments[0] == 'string')
			{
				regText = arguments[0];
			}
		}
		
		var defaultMessage = '不能包含下列字符: ,./<>?\';:~!`#$%^&*()-=+\\|{}[]"';
		
		return this.isReg(regText, defaultMessage);
	},
	
	// 验证字符串是否仅包含字母和数字
	isNumberOrLetter:
	function()
	{
		var regText = /^[A-Za-z0-9]*$/;
		
		var defaultMessage = '只能是数字或字母';
		
		return this.isReg(regText, defaultMessage);
	},
	
	// 验证字符串是浮点
	isFloat:
	function()
	{
		var regText = /^[0-9]*(.)?[0-9]+$/;
		
		var defaultMessage = '只能是浮点型';
		
		return this.isReg(regText, defaultMessage);
	},
	
	// 最小长度
	minLength:
	function(min)
	{
		var length;
		if (typeof parmters == 'number')
		{
			length = parmters;
		}
		else
		{
			alert('参数不是数值');
			return false;
		}
		
		if (getLengthInCheck(this.getValue()) < length)
		{
			var defaultMessage = '长度不能小于' + length;
			
			this.showMessage(defaultMessage);
			
			return false;
		}
		else
		{
			return true;
		}
	},
	
	// 不能小于当前时间 
	cnow:
	function(cc, dir, format)
	{
		var cint = '==';
		if (!isNull(cc))
		{
			cint = cc;
		}
		
		
		//暂时不支持格式化比较
		var ff = 'yyyy-MM-dd';
		var current;
		
		//获取时间
		var src = trimInCheck(this.getValue());
		
		if (isNull(dir))
		{
			//当前时间
			current = getNowDateInCheck();
		}
		else
		{
			//指定比较时间
			current = dir;
		}
		
		if (!checkDateInCheck(src))
		{
			alert(src + '不是' + ff + '格式的');
			return false;
		}
		
		if (!checkDateInCheck(current))
		{
			alert(current + '不是' + ff + '格式的');
			return false;
		}
		
		var cresult;
		var selfMessage;
		
		// -2小于等于
		if (cint == '<=')
		{
			cresult = (src <= current);
			selfMessage = "不能大于日期: " + current;
		}
		
		if (cint == '==')
		{
			cresult = (src == current);
			selfMessage = "必须等于日期: " + current;
		}
		
		if (cint == '<')
		{
			cresult = (src < current);
			selfMessage = "不能大于等于日期: " + current;
		}
		
		if (cint == '>=')
		{
			cresult = (src >= current);
			selfMessage = "不能小于日期: " + current;
		}
		
		if (cint == '>')
		{
			cresult = (src > current);
			selfMessage = "不能小于等于日期: " + current;
		}
		
		 
		if (!cresult)
		{
			var defaultMessage = selfMessage;
			
			this.showMessage(defaultMessage);
			
			return false;
		}
		else
		{
			return true;
		}
	},
	
	noEquals:
	function(str)
	{
		if (this.getValue() == str)
		{
			var defaultMessage = '值不能等于:' + str;
			
			this.showMessage(defaultMessage);
			
			return false;
		}
		else
		{
			return true;
		}
	},
	
	equals:
	function(str)
	{
		if (this.getValue() != str)
		{
			var defaultMessage = '值必须等于:' + str;
			
			this.showMessage(defaultMessage);
			
			return false;
		}
		else
		{
			return true;
		}
	},
	
	//范围比较>= <= 空返回true
	range:
	function(begin, end)
	{
		//关闭提示
		if (this.notNone(false))
		{
			var defaultMessage = '';
			
			//前后比较的
			if (!isNull(begin) && !isNull(end))
			{
				defaultMessage = '值必须大于等于:' + begin + '且小于等于' + end;
				
				var num = parseInt(this.getValue(), 10);
				if (num >= begin && num <= end)
				{
					return true;
				}
				else
				{
					this.showMessage(defaultMessage);
					return false;
				}
			}
			
			//前比较的
			if (!isNull(begin) && isNull(end))
			{
				defaultMessage = '值必须大于等于:' + begin;
				
				var num = parseInt(this.getValue(), 10);
				if (num >= begin)
				{
					return true;
				}
				else
				{
					this.showMessage(defaultMessage);
					return false;
				}
			}
			
			//后比较的
			if (isNull(begin) && !isNull(end))
			{
				defaultMessage = '值必须小于等于:' + end;
				
				var num = parseInt(this.getValue(), 10);
				if (num <= end)
				{
					return true;
				}
				else
				{
					this.showMessage(defaultMessage);
					return false;
				}
			}
			
			//默认没有
			defaultMessage = '比较不合法，请确认';
			this.showMessage(defaultMessage);
			return false;
			
		}
		else
		{
			return true;
		}
	}
});

var myCheck = new MyCheck();
