var digitArray = new Array('0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F');

function toHex( n ) {

        var result = ''
        var start = true;

        for ( var i=32; i>0; ) {
                i -= 4;
                var digit = ( n >> i ) & 0xf;

                if (!start || digit != 0) {
                        start = false;
                        result += digitArray[digit];
                }
        }

        return ( result == '' ? '0' : result );
}

function checkLockExist()
{
	try
    {
		var tax = new ActiveXObject("Syunew6A.s_simnew6");
		
	    DevicePath = tax.FindPort(0);
	    
	    if( tax.LastError!= 0 )
	    {
	        return '';
	    }
	    else
	    {
	    	var ret = toHex(tax.GetID_1(DevicePath))+toHex(tax.GetID_2(DevicePath));
	    	
	    	tax = null;
	    	
	    	return ret;
	    }
    }
    catch(e)
    {
    	return '';
    }
}


function checkEnc(encStr) 
{
     try
     {
     	$O('jiamiRand').value = '';
        $O('key').value = '';
        
        var DevicePath,mylen,ret;
        var s_simnew31 = new ActiveXObject("Syunew6A.s_simnew6");
        DevicePath = s_simnew31.FindPort(0);
        if( s_simnew31.LastError!= 0 )
        {
            window.alert ( "未发现加密锁，请插入加密锁");
            
            return false;
        }
        else
        {
            //s_simnew31.SetCal_2("12345678901234567890123456xxxxx", DevicePath);
            if (encStr != '')
            {
                $O('jiamiRand').value = s_simnew31.EncString(encStr, DevicePath);
            }
            
            $O('encSuperRand').value = s_simnew31.EncString($O('superRand').value, DevicePath);
            
            //获取锁的ID
            $O('key').value = toHex(s_simnew31.GetID_1(DevicePath))+toHex(s_simnew31.GetID_2(DevicePath));
        }
      }
     catch(err)  
      {  
          txt="错误,原因是" + err.description + "\n\n"  
          txt+="请检查是否安装驱动程序"
          alert(txt)  
          
          $O('jiamiRand').value = '';
          $O('key').value = '';
          
          return false;
      }  
      
      return true;
}