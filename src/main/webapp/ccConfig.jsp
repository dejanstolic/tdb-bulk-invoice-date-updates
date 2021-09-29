<%@ page import="java.util.Properties"%>
<%@ page import="com.brandmaker.cs.skyhigh.tdb.config.Globals" %>
<%@ page import="com.brandmaker.cs.skyhigh.tdb.servlets.LoadConfigData" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>Cross Charges Utility - config page</title>
    <link rel="stylesheet" href="css/bootstrap.min.css">
    <link rel="stylesheet" href="css/noty.css">
    <script src="js/noty.min.js"></script>
    <script src="js/authorize.js"></script>
</head>
<body>

<%
    LoadConfigData configData = new LoadConfigData();
    Properties properties = configData.getData();
%>

<nav class="navbar navbar-dark bg-dark">
    <a class="navbar-brand" href="#">Cross Charges Utility - Config</a>
    <div>
        <label class="navbar-brand">Version: <%= Globals.GetVersion()%></label>
    </div>
</nav>



<form action="ccConfigServlet" method="post">

    <div class="container" style="margin-top: 20px; margin-bottom: 20px">

        <h4>Properties for Mail Server</h4>
        <div class="row">
            <div class="col">
                <div class="form-group">
                    <label for="mail.server">Mail Server</label>
                    <label class="font-italic">(mail.server)</label>
                    <input type="text" class="form-control" name="mail.server" id="mail.server" placeholder="Mail Server" value="<%= properties.getProperty("mail.server") %>">
                </div>
            </div>
            <div class="col">
                <div class="form-group">
                    <label for="mail.username">Mail Username</label>
                    <label class="font-italic">(mail.username)</label>
                    <input type="text" class="form-control" name="mail.username" id="mail.username" placeholder="Mail Username" value="<%= properties.getProperty("mail.username") %>">
                </div>
            </div>
        </div>
        <div class="row">
            <div class="col">
                <div class="form-group">
                    <label for="mail.password">.Mail Password</label>
                    <label class="font-italic">(mail.password)</label>
                    <input type="password" class="form-control" name="mail.password" id="mail.password" placeholder="Mail Password" value="<%= properties.getProperty("mail.password") %>">
                </div>
            </div>
            <div class="col">
                <div class="form-group">
                    <label for="mail.smtp.auth">Mail SMTP Auth</label>
                    <label class="font-italic">(mail.smtp.auth)</label>
                    <input type="text" class="form-control" name="mail.smtp.auth" id="mail.smtp.auth" placeholder="Mail SMTP auth" value="<%= properties.getProperty("mail.smtp.auth") %>">
                </div>
            </div>
        </div>
        <div class="row">
            <div class="col">
                <div class="form-group">
                    <label for="mail.smtp.starttls.enable">Mail Start TLS </label>
                    <label class="font-italic">(mail.smtp.starttls.enable)</label>
                    <input type="text" class="form-control" name="mail.smtp.starttls.enable" id="mail.smtp.starttls.enable" placeholder="Mail Start TLS" value="<%= properties.getProperty("mail.smtp.starttls.enable") %>">
                </div>
            </div>
            <div class="col">
                <div class="form-group">
                    <label for="mail.smtp.port">Mail SMTP Port</label>
                    <label class="font-italic">(mail.smtp.port)</label>
                    <input type="text" class="form-control" name="mail.smtp.port" id="mail.smtp.port" placeholder="Mail SMTP Port" value="<%= properties.getProperty("mail.smtp.port") %>">
                </div>
            </div>
        </div>
        <div class="row">
            <div class="col">
                <div class="form-group">
                    <label for="mail.recipients">Mail Additional Recipients</label>
                    <label class="font-italic">(mail.recipients)</label>
                    <input type="text" class="form-control" name="mail.recipients" id="mail.recipients" placeholder="Mail Additional Recipients" value="<%= properties.getProperty("mail.recipients") %>">
                </div>
            </div>
            <div class="col"></div>
        </div>

        <h4>Properties for Web API</h4>
        <div class="row">
            <div class="col">
                <div class="form-group">
                    <label for="web.api.root">Web API Root</label>
                    <label class="font-italic">(web.api.root)</label>
                    <input type="text" class="form-control" name="web.api.root" id="web.api.root" placeholder="Web API Root" value="<%= properties.getProperty("web.api.root") %>">
                </div>
            </div>
            <div class="col">
                <div class="form-group">
                    <label for="web.api.username">Web API Username</label>
                    <label class="font-italic">(web.api.username)</label>
                    <input type="text" class="form-control" name="web.api.username" id="web.api.username" placeholder="Web API Username" value="<%= properties.getProperty("web.api.username") %>">
                </div>
            </div>
        </div>
        <div class="row">
            <div class="col">
                <div class="form-group">
                    <label for="web.api.password">Web API Password</label>
                    <label class="font-italic">(web.api.password)</label>
                    <input type="text" class="form-control" name="web.api.password" id="web.api.password" placeholder="Web API Password" value="<%= properties.getProperty("web.api.password") %>">
                </div>
            </div>
            <div class="col"></div>
        </div>

        <div class="text-right ">
            <button type="submit" class="btn btn-primary">SAVE CONFIG</button>
        </div>
    </div>

    <%
        if (request.getAttribute("pageSaved") != null) {
            Boolean pageSaved = (Boolean)request.getAttribute("pageSaved");
            if (pageSaved)
            {
    %>
    <script>
        new Noty({
            type: 'success',
            layout: 'topCenter',
            timeout: 5000,
            text: 'Config file changes successfully saved!'
        }).show();
    </script>
    <%
    }
    else
    {
    %>
    <script>
        new Noty({
            type: 'error',
            layout: 'topCenter',
            timeout: 10000,
            text: 'An error occurred while saving config file or updating change log probably because your session is expired! Please login again and after that you will be able to execute this action. '
        }).show();
    </script>
    <%
            }
        }
    %>

</form>
</body>
</html>