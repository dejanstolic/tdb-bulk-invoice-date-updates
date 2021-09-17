<%@ page import="com.brandmaker.cs.skyhigh.tdb.config.Globals" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>TD Bank Cross charges </title>
    <link rel="stylesheet" href="css/bootstrap.min.css">
    <link rel="stylesheet" href="css/noty.css">
    <script src="js/noty.min.js"></script>
    <script src="js/authorize.js"></script>
</head>
<body>

<form action="ccImportServlet" method="post">
    <button onclick="this.disabled=true;this.value='Processing...'; this.form.submit();" type="submit"
            class="btn btn-primary">cross charges
    </button>
</form>
<%
    if (request.getAttribute("message") != null) {
        out.print(request.getAttribute("message"));
    }
%>
</body>
</html>
