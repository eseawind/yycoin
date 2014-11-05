//格式化数字 四舍五入
function formatNum(num, length)
{
     var reg = /[0-9]*(.)?[0-9]*$/;

     if (!reg.test(num))
     {
        reg = /[0-9]*.$/;
        if (!reg.test(num))
        {
            return num;
        }
     }

     num += '';
     
     if (num.indexOf('.') == -1)
     {
        return num + '.' + getLength0(length);
     }

     var hou = num.substring(num.indexOf('.') + 1);

     if (hou.length <= length)
     {
        return num + getLength0(length - hou.length);
     }

     //超过 指定的四舍五入
     var ins = num.substring(0, num.indexOf('.') + 1) + hou.substring(0, length);

     var last = parseInt(hou.charAt(length));
     
     var add = false;

     if (last >= 5)
     {
        add = true;
     }
     else
     {
        add = false;
     }
     
     if (add)
     {
        var goAdd = true;
        
        for (var i = ins.length - 1; i >= 0; i--)
        {
            if (ins.charAt(i) == '.')
            {
                continue;
            }
            
            if (goAdd && parseInt(ins.charAt(i)) == 9)
            {
                goAdd = true;
                
                ins = ins.substring(0, i) + '0' + ins.substring(i + 1);
            }
            else
            {
                ins = ins.substring(0, i) + (parseInt(ins.charAt(i)) + 1) + ins.substring(i + 1);
                
                goAdd = false;
                
                break;
            }
        }
        
        if (goAdd && parseInt(ins.charAt(0)) == 0)
        {
            ins = '1' + ins;
        }
     }

     var sresult = ins + '';
     
     if (sresult.indexOf('.') == -1)
     {
        return sresult;
     }
     
     if (sresult.indexOf('.') != -1)
     {
         sresult = sresult + '0000000000000000';           
     }
     
     return sresult.substring(0, sresult.indexOf('.') + length + 1);
}

function getLength0(length)
{
	var s = '';
	for (var i = 0; i < length; i++)
	{
		s += '0';
	}

	return s;
}

function mul(n1, n2)
{
    var m=0, s1=n1.toString(), s2=n2.toString();
    try
    {
        m += s1.split(".")[1].length;
    }
    catch(e)
    {}

    try
    {
        m += s2.split(".")[1].length
    }
    catch(e)
    {}

    return Number(s1.replace(".","")) * Number(s2.replace(".","")) / Math.pow(10, m);
}

function add(n1, n2)
{
    var m=0, n=0,s1=n1.toString(), s2=n2.toString();
    try
    {
        m = s1.split(".")[1].length
    }
    catch(e)
    {}

    try
    {
        n = s2.split(".")[1].length
    }
    catch(e)
    {}

    var oo = Math.max(m, n);

    //获得string类型的整数值
    s1 = s1.replace(".","") + getLength0(oo - m);
    s2 = s2.replace(".","") + getLength0(oo - n);

    return (Number(s1) + Number(s2)) / Math.pow(10, oo);
}

function math_compare(a, b)
{
    var aa = Math.round(a * 1000);
    var bb = Math.round(b * 1000);

    if (Math.abs(aa - bb) <= 4)
    {
    	return 0;
    }
    
    if (aa > bb)
    {
    	return 1;
    }
    
    return -1;
}

/**
 * 验证字符串是否仅数字
 */
function isNumbers(str)
{
    var reg = /^[0-9]+$/;
    var reg1 = /^-{1}[0-9]+$/;
    return reg.test(str) || reg1.test(str);
}

/**
 * 比较大小
 */
function compareDouble(a, b)
{
    var aa = a * 1000;
    var bb = b * 1000;
    
    if (Math.abs(aa - bb) < 10)
    {
        return 0;
    }
    
    if (aa > bb)
    {
        return 1;
    }

    return -1;
}