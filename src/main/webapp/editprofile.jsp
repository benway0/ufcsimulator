<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" 
           uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Fighter Profile</title>
<link rel="stylesheet" href="css/bootstrap.css">
<link rel="stylesheet" href="css/extra.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
<script>
    $(document).ready(function() {
        document.title = "${fighter.first_name} ${fighter.last_name} Profile";
    });
</script>
</head>
<body>
<c:set var="pts" value="${fighter.points}" />
<div class="container-fluid">
    <div class="row" >
        <div class="col-sm-1"></div>
        <div class="col-sm-10"><c:import url="/nav.jsp" />
            <div class="foo">
                <div class="row">
                    <div class="col-sm-1"></div>
                    <div class="col-sm-5"><br />
                        <img src="${fighter.profile_image}" height="82%" width="82%"/>
                    </div>
                    <div class="col-sm-5"><br />
                        <c:if test="${error != null}">
                            <div class="alert alert-danger fade in" style="text-align:center">
                                ${error}
                            </div>
                        </c:if>
                        <form id="editprofile" action="editprofile?wc=${param.wc}&id=${param.id}" method="post"></form>
                        <table class="table table-striped table-condensed table-hover">
                            <tr>
                                <td><strong>Name</strong></td>
                                <td>${fighter.first_name} ${fighter.last_name}</td>
                            </tr>
                            <tr>
                                <td><strong>Record</strong></td>
                                <td>
                                    ${fighter.wins}-${fighter.losses}<c:if test="${fighter.draws > 0}">-${fighter.draws}</c:if>
                                </td>
                            </tr>
                            <tr>
                                <td><strong>Rank</strong></td>
                                <td>
                                    <c:if test="${fighter.rank != 'C'}">#${fighter.rank}</c:if>
                                    <c:if test="${fighter.rank == 'C'}">Champion</c:if>
                                </td>
                            <tr>
                                <td><strong>Rank Points</strong></td>
                                <td>
                                    <input type="text" form="editprofile" name="rankpoints" value="${fn:substringBefore(pts, '.')}" />
                                </td>
                            </tr>
                            <tr>
                                <td><strong>KO</strong></td>
                                <td>
                                    <input type="text" form="editprofile" name="ko" value="${fighter.koPercentage}" />
                                </td>
                            </tr>
                            <tr>
                                <td><strong>Submission</strong></td>
                                <td>
                                    <input type="text" form="editprofile" name="sub" value="${fighter.subPercentage}" />
                                </td>
                            </tr>
                            <tr>
                                <td>
                                    <input type="submit" form="editprofile" value="Submit" />
                                </td>
                                <td></td>
                            </tr>
                        </table>

                    </div>
                    <div class="col-sm-1"></div>
                </div>
                <div class="row">
                    <div class="col-sm-1"></div>
                    <div class="col-sm-10" style="text-align:center">
                        <table class="table table-striped table-condensed table-hover">
                            <thead>
                                <tr>
                                    <th>Previous Results</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:set var="resultsList" value="${fighter.previousResults}" />
                                <c:set var="resultsLen" value="${fn:length(resultsList)}" />
                                <c:if test="${resultsLen == 0}">
                                    <tr>
                                        <td><i>None to show</i></td>
                                    </tr>
                                </c:if>
                                <c:forEach items="${resultsList}" varStatus="status">
                                    <tr>
                                        <td>${resultsList.get(resultsLen - status.count)}</td>
                                    </tr>
                                </c:forEach>
                            </tbody>
                        </table>
                    </div>
                    <div class="col-sm-1"></div>
                </div>
            </div><br />
            <div style="text-align:center">2017 UFC Simulator</div>
        </div>
        <div class="col-sm-1"></div>
    </div>
</div>
</body>
</html>