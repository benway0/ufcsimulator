<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" href="css/bootstrap.css">
<link rel="stylesheet" href="css/extra.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
<title>Error</title>
</head>
<body>
    <div class="container-fluid">
        <div class="row" >
            <div class="col-sm-1 col-md-1"></div>
            <div class="col-sm-10 col-md-10"><c:import url="/nav.jsp" />
                <div class="foo">
                    <div class="row">
                        <div class="col-sm-1 col-md-1"></div>
                        <div class="col-sm-10 col-md-10" style="text-align:center">
                            <br /><h1>Oops! An error occurred.</h1>
                            <br /><br />
                        </div>
                        <div class="col-sm-1 col-md-1"></div>
                    </div>
                </div><br />
                <div style="text-align:center">
                    <c:import url="/footer.jsp" />
                </div>
            </div>
            <div class="col-sm-1 col-md-1"></div>
        </div>
    </div>
</body>
</html>
