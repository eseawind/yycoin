/**
 * 声明继承
 */
Object.extend = function(destination, source) {
  for (property in source) {
    destination[property] = source[property];
  }
  return destination;
}

Object.extend(String.prototype, {
  /**
   * 删除字符串中的sub字符
   */	
  delSubString: function(sub) 
  {
      var index = this.indexOf(sub);
      
	  if (index == -1)
	  {
		  return this;
	  }
	
	  var ss = this.substring(0, index) +  this.substring(index + sub.length);	
	
	  return ss.delSubString(sub);
  }
});