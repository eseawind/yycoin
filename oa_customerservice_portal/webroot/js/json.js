/*
 * Compressed by JSA(www.xidea.org)
 */
if(!this.JSON)JSON=function(){function f($){return $<10?"0"+$:$}Date.prototype.toJSON=function($){return this.getUTCFullYear()+"-"+f(this.getUTCMonth()+1)+"-"+f(this.getUTCDate())+"T"+f(this.getUTCHours())+":"+f(this.getUTCMinutes())+":"+f(this.getUTCSeconds())+"Z"};var cx=/[\u0000\u00ad\u0600-\u0604\u070f\u17b4\u17b5\u200c-\u200f\u2028-\u202f\u2060-\u206f\ufeff\ufff0-\uffff]/g,escapeable=/[\\\"\x00-\x1f\x7f-\x9f\u00ad\u0600-\u0604\u070f\u17b4\u17b5\u200c-\u200f\u2028-\u202f\u2060-\u206f\ufeff\ufff0-\uffff]/g,gap,indent,meta={"\b":"\\b","\t":"\\t","\n":"\\n","\f":"\\f","\r":"\\r","\"":"\\\"","\\":"\\\\"},rep;function quote($){escapeable.lastIndex=0;return escapeable.test($)?"\""+$.replace(escapeable,function($){var _=meta[$];if(typeof _==="string")return _;return"\\u"+("0000"+(+($.charCodeAt(0))).toString(16)).slice(-4)})+"\"":"\""+$+"\""}function str($,C){var _,B,G,D,F=gap,E,A=C[$];if(A&&typeof A==="object"&&typeof A.toJSON==="function")A=A.toJSON($);if(typeof rep==="function")A=rep.call(C,$,A);switch(typeof A){case"string":return quote(A);case"number":return isFinite(A)?String(A):"null";case"boolean":case"null":return String(A);case"object":if(!A)return"null";gap+=indent;E=[];if(typeof A.length==="number"&&!(A.propertyIsEnumerable("length"))){D=A.length;for(_=0;_<D;_+=1)E[_]=str(_,A)||"null";G=E.length===0?"[]":gap?"[\n"+gap+E.join(",\n"+gap)+"\n"+F+"]":"["+E.join(",")+"]";gap=F;return G}if(rep&&typeof rep==="object"){D=rep.length;for(_=0;_<D;_+=1){B=rep[_];if(typeof B==="string"){G=str(B,A,rep);if(G)E.push(quote(B)+(gap?": ":":")+G)}}}else for(B in A)if(Object.hasOwnProperty.call(A,B)){G=str(B,A,rep);if(G)E.push(quote(B)+(gap?": ":":")+G)}G=E.length===0?"{}":gap?"{\n"+gap+E.join(",\n"+gap)+"\n"+F+"}":"{"+E.join(",")+"}";gap=F;return G}}return{stringify:function(_,B,A){var $;gap="";indent="";if(typeof A==="number"){for($=0;$<A;$+=1)indent+=" "}else if(typeof A==="string")indent=A;rep=B;if(B&&typeof B!=="function"&&(typeof B!=="object"||typeof B.length!=="number"))throw new Error("JSON.stringify");return str("",{"":_})},parse:function(text,reviver){var j;function walk(B,$){var A,C,_=B[$];if(_&&typeof _==="object")for(A in _)if(Object.hasOwnProperty.call(_,A)){C=walk(_,A);if(C!==undefined)_[A]=C;else delete _[A]}return reviver.call(B,$,_)}cx.lastIndex=0;if(cx.test(text))text=text.replace(cx,function($){return"\\u"+("0000"+(+($.charCodeAt(0))).toString(16)).slice(-4)});if(/^[\],:{}\s]*$/.test(text.replace(/\\(?:["\\\/bfnrt]|u[0-9a-fA-F]{4})/g,"@").replace(/"[^"\\\n\r]*"|true|false|null|-?\d+(?:\.\d*)?(?:[eE][+\-]?\d+)?/g,"]").replace(/(?:^|:|,)(?:\s*\[)+/g,""))){j=eval("("+text+")");return typeof reviver==="function"?walk({"":j},""):j}throw new SyntaxError("JSON.parse")}}}()