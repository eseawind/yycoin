<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="GBK"%>
<html>
<head>
<title>test_word</title>
</head>
<body bgcolor="#ffffff">
<h1><input type="button" name="button" value="view" onclick="viewWord()"> 
    <input type="button" name="button1" value="edit" onclick="OpenWord()">
    <input type="button" name="button2" value="new" onclick="newWord()">
</h1>

<Script Language="JavaScript"> 
function OpenWord() 
{
    var openDocObj; 
    openDocObj = new ActiveXObject("SharePoint.OpenDocuments.2"); 
    openDocObj.editDocument("http://127.0.0.1:8080/webdav/template/2009/04/23/20/200904232040212.doc?key=123456&value=zhuzhu"); 
}

function newWord() 
{
    var openDocObj; 
    openDocObj = new ActiveXObject("SharePoint.OpenDocuments.1"); 
    openDocObj.CreateNewDocument("http://127.0.0.1:8080/webdav/template/2009/04/23/20/200904232040212.doc", "http://127.0.0.1:8080/webdav/"); 
}

function viewWord() 
{
    var openDocObj; 
    openDocObj = new ActiveXObject("SharePoint.OpenDocuments.2"); 
    openDocObj.ViewDocument("http://127.0.0.1:8080/webdav/1.doc"); 
}
</script>

</body>
</html>
