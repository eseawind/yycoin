<%@ page contentType="text/html;charset=UTF-8" language="java"
	errorPage="../common/error.jsp"%>
<%@include file="../common/common.jsp"%>
<html>
<head>
<p:link title="demo" />
<meta charset="utf-8" />
<meta name="apple-mobile-web-app-capable" content="yes" />
<meta name="viewport"
	content="maximum-scale=1.0,width=device-width,initial-scale=1.0,user-scalable=0">
<title>Enter Passcode</title>
<link href="/favicon.ico" rel="shortcut icon" type="image/x-icon" />
<link rel="apple-touch-icon" href="/Content/Images/57.png" />
<link rel="apple-touch-icon" sizes="72x72" href="/Content/Images/72.png" />
<link rel="apple-touch-icon" sizes="114x114" href="/Content/Images/114.png" />
<link href="../webmobi_js/jquery.mobile-1.2.0.min.css" rel="stylesheet"	type="text/css" />
<link href="../webmobi_js/css.css" rel="stylesheet" />
<link href="../webmobi_js/cubeposcss.css" rel="stylesheet" />
<link href="../webmobi_js/jquery.css"	rel="stylesheet" />

<script	src="../webmobi_js/cubeposscript.js"></script>
<script	src="../webmobi_js/jquery.js"></script>

<script type="text/javascript">
        $(document).bind("mobileinit", function () {
            $.mobile.defaultPageTransition = "none";
        });
    </script>
<script	src="../webmobi_js/jquerymobile.js"></script>
<script	src="../webmobi_js/modernizr.js"></script>

</head>
<body class="body_class" onload="loads()">
<div data-role="page" data-theme="d">
<div data-role="header">
<h1>Enter Passcode</h1>
</div>
<div id="content">

<form action="" method="post">
	<ul data-role="listview" data-inset="true">
		<li><input class="PasswordFor" data-val="true"
			data-val-required="The Password field is required." id="Password"
			name="Password" placeholder="Password" type="password" /> <span
			class="field-validation-valid" data-valmsg-for="Password"
			data-valmsg-replace="true"></span></li>
		<li><input type="submit" id="btnLogin" value="Login" /></li>
	</ul>
</form>

</div>
</div>

<script	src="../webmobi_js/jqueryval.js"></script>
</body>
</html>

