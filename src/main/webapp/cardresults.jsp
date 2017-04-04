<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" 
           uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" href="css/bootstrap.css">
<link rel="stylesheet" href="css/extra.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
<title>Card Results</title>
</head>
<body>
<c:set var="fights" value="${fightList}" />
<div class="container-fluid">
    <div class="row" >
        <div class="col-sm-1 col-md-1"></div>
        <div class="col-sm-10 col-md-10"><c:import url="/nav.jsp" />
            <div class="foo">
                <div class="row">
                    <div class="col-sm-1 col-md-1"></div>
                    <div class="col-sm-10 col-md-10" style="text-align:center">
                        <br />
                        <table class="table table-striped table-condensed">
                            <thead><tr><th colspan="3">Main Card</th></tr></thead>
                            <c:forEach items="${fights}" var="i" begin="0" end="4">
                                <tr>
                                    <td style="text-align:center">
                                        <img src="${i.winner.profile_image}" />
                                    </td>
                                    <td style="text-align:center; vertical-align:middle">
                                        <c:if test="${i.draw}">vs.</c:if>
                                        <c:if test="${!i.draw}">def.</c:if>
                                    </td>
                                    <td style="text-align:center">
                                        <img src="${i.loser.profile_image}"/>
                                    </td>
                                </tr>
                                <tr>
                                    <td style="text-align:center; vertical-align:middle" colspan="3">
                                        ${i.result}
                                        <c:if test="${i.decision}">
                                            ${i.scores}
                                        </c:if>
                                    </td>
                                </tr>
                            </c:forEach></table>
                        <table class="table table-striped table-condensed">
                            <thead><tr><th colspan="3">Preliminary Card</th></tr></thead>
                            <c:forEach items="${fights}" var="i" begin="5" end="8">
                                <tr>
                                    <td style="text-align:center">
                                        <img src="${i.winner.profile_image}" />
                                    </td>
                                    <td style="text-align:center; vertical-align:middle">def.</td>
                                    <td style="text-align:center">
                                        <img src="${i.loser.profile_image}" />
                                    </td>
                                </tr>
                                <tr>
                                    <td style="text-align:center; vertical-align:middle" colspan="3">
                                        ${i.result}
                                        <c:if test="${i.decision}">
                                            ${i.scores}
                                        </c:if>
                                    </td>
                                </tr>
                            </c:forEach></table>
                        <table class="table table-striped table-condensed">
                            <thead><tr><th colspan="3">Early Prelims</th></tr></thead>
                            <c:forEach items="${fights}" var="i" begin="9" end="11">
                                <tr>
                                    <td style="text-align:center">
                                        <img src="${i.winner.profile_image}" />
                                    </td>
                                    <td style="text-align:center; vertical-align:middle">def.</td>
                                    <td style="text-align:center">
                                        <img src="${i.loser.profile_image}" />
                                    </td>
                                </tr>
                                <tr>
                                    <td style="text-align:center; vertical-align:middle" colspan="3">
                                        ${i.result}
                                        <c:if test="${i.decision}">
                                            ${i.scores}
                                        </c:if>
                                    </td>
                                </tr>
                            </c:forEach>
                        </table>
                    </div>
                    <div class="col-sm-1 col-md-1"></div>
                </div>
            </div><br />
            <div style="text-align:center">2017 UFC Simulator</div>
        </div>
        <div class="col-sm-1 col-sm-1"></div>
    </div>
</div>
</body>
</html>
