<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!-- Set the appropriate ranking based on the param -->
<c:choose>
    <c:when test="${param.wc eq 'flw'}">
        <c:set var="wc" value="${sessionScope.flwSession}" />
        <c:set var="wcText" value="Flyweight" />
    </c:when>
    <c:when test="${param.wc eq 'bw'}">
        <c:set var="wc" value="${sessionScope.bwSession}" />
        <c:set var="wcText" value="Bantamweight" />
    </c:when>
    <c:when test="${param.wc eq 'fw'}">
        <c:set var="wc" value="${sessionScope.fwSession}" />
        <c:set var="wcText" value="Featherweight" />
    </c:when>
    <c:when test="${param.wc eq 'lw'}">
        <c:set var="wc" value="${sessionScope.lwSession}" />
        <c:set var="wcText" value="Lightweight" />
    </c:when>
    <c:when test="${param.wc eq 'ww'}">
        <c:set var="wc" value="${sessionScope.wwSession}" />
        <c:set var="wcText" value="Welterweight" />
    </c:when>
    <c:when test="${param.wc eq 'mw'}">
        <c:set var="wc" value="${sessionScope.mwSession}" />
        <c:set var="wcText" value="Middleweight" />
    </c:when>
    <c:when test="${param.wc eq 'lhw'}">
        <c:set var="wc" value="${sessionScope.lhwSession}" />
        <c:set var="wcText" value="Light Heavyweight" />
    </c:when>
    <c:when test="${param.wc eq 'hw'}">
        <c:set var="wc" value="${sessionScope.hwSession}" />
        <c:set var="wcText" value="Heavyweight" />
    </c:when>
    <c:when test="${param.wc eq 'wsw'}">
        <c:set var="wc" value="${sessionScope.wswSession}" />
        <c:set var="wcText" value="Women's Strawweight" />
    </c:when>
    <c:when test="${param.wc eq 'wbw'}">
        <c:set var="wc" value="${sessionScope.wbwSession}" />
        <c:set var="wcText" value="Women's Bantamweight" />
    </c:when>
    <c:otherwise>
        <c:redirect url="/rankings.jsp?wc=flw" />
    </c:otherwise>
</c:choose>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" href="css/bootstrap.css">
    <link rel="stylesheet" href="css/extra.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <script>
        $(document).ready(function() {
            $("#fighterB").prop("selectedIndex", 1);
            document.title = "${wcText} Rankings";
        })
    </script>
    <title>Rankings</title>
</head>
<body>
<div class="container-fluid">
<div class="row">
    <div class="col-sm-1"></div>
    <div class="col-sm-10"><c:import url="/nav.jsp" />
        <div class="foo">
            <div class="row">
                <div class="col-sm-6">
                    <table class="table table-striped table-condensed table-hover">
                        <thead>
                            <tr><th colspan="4">${wcText} Rankings</th></tr>
                        </thead>
                        <tbody>
                        <c:forEach items='${wc}' var='f' varStatus='theRank'>
                            <c:choose>
                                <c:when test="${f.isTitle_holder()}">
                                    <tr style="background-color:#ffe680">
                                        <td style="text-align:center"><b>C</b></td>
                                        <td>${f.first_name} ${f.last_name}</td>
                                        <td style="text-align:center">
                                            ${f.wins}-${f.losses}<c:if test="${f.draws > 0}">-${f.draws}</c:if>
                                        </td>
                                        <td>
                                            <a href="profile?wc=${param.wc}&id=${f.id}" target="_blank"><span class="glyphicon glyphicon-info-sign"></span></a>
                                        </td>
                                    </tr>
                                </c:when>
                                <c:when test="${theRank.index < 16}">
                                    <tr>
                                        <td style="text-align:center"><b>${theRank.index}</b></td>
                                        <td>${f.first_name} ${f.last_name}</td>
                                        <td style="text-align:center">
                                            ${f.wins}-${f.losses}<c:if test="${f.draws > 0}">-${f.draws}</c:if>
                                            </td>
                                            <td>
                                                <a href="profile?wc=${param.wc}&id=${f.id}" target="_blank"><span class="glyphicon glyphicon-info-sign"></span></a>
                                        </td>
                                    </tr>
                                </c:when>
                                <c:otherwise>
                                    <tr>
                                        <td style="text-align:center"><b>NR</b></td>
                                        <td>${f.first_name} ${f.last_name}</td>
                                        <td style="text-align:center">
                                            ${f.wins}-${f.losses}<c:if test="${f.draws > 0}">-${f.draws}</c:if>
                                            </td>
                                            <td>
                                                <a href="profile?wc=${param.wc}&id=${f.id}" target="_blank"><span class="glyphicon glyphicon-info-sign"></span></a>
                                        </td>
                                    </tr>
                                </c:otherwise>
                            </c:choose>
                        </c:forEach>
                        </tbody>
                    </table>
                </div>
                <div class="col-sm-6" style="text-align:center">
                    <br />
                    <form id="tform" action="fight" method="post"></form>
                    <table><tr>
                            <td style="text-align:center">
                                <img src="${wc.get(0).profile_image}" height="80%" width="80%" id ="imgid"/>
                            </td>
                            <td></td>
                            <td style="text-align:center">
                                <img src="${wc.get(1).profile_image}" height="80%" width="80%" id="imgid2"/>
                            </td>
                        </tr>
                        <tr>
                            <td style="text-align:center">
                        <select id="fighterA" form="tform" name="fighterA" style="min-width:90%" onchange="document.getElementById('imgid').src = this.value.split('|')[0]">
                            <c:forEach items='${wc}' var="f" varStatus='theRank'>
                                <option value="${f.profile_image}|${f.id}">
                                    <c:if test="${param.selectValue == f.id}">selected</c:if>
                                    ${f.first_name} ${f.last_name}
                                </option>
                            </c:forEach>
                            </select></td>
                            <td style="text-align:center"> vs. </td>
                            <td style="text-align:center">
                                <select id="fighterB" form="tform" name="fighterB" style="min-width:90%" onchange="document.getElementById('imgid2').src = this.value.split('|')[0]">
                                    <c:forEach items="${wc}" var="f" varStatus="theRank">
                                        <option value="${f.profile_image}|${f.id}">
                                            <c:if test="${param.selectValue == f.id}">selected</c:if>
                                            ${f.first_name} ${f.last_name}
                                        </option>
                                    </c:forEach>
                                </select>
                            </td>
                        </tr>
                    </table>
                    <input type="radio" form="tform" name="rounds" value="three"> 3 rounds&nbsp;&nbsp;&nbsp;
                    <input type="radio" form="tform" name="rounds" value="five" checked> 5 rounds
                    <input type="hidden" form="tform" name="wc" id="wc" value="${param.wc}" />
                    <br />
                    <input type="submit" form="tform" value="Sim" />
                    <br /><br />
                    <c:if test="${error}">
                        <div class="alert alert-danger fade in" style="text-align:center">
                            Fighters cannot fight themselves
                        </div>
                    </c:if>
                    <c:if test="${fight != null}">
                        <div class="alert alert-success fade in" style="text-align:center">
                            ${fight.getResult()}
                            <c:if test="${fight.isDecision()}">
                                <br />
                                ${fight.getScores()}
                            </c:if>
                        </div>
                    </c:if>
                </div>
            </div>
        </div><br />
        <div style="text-align:center"><c:import url="/footer.jsp" /></div>
    </div>
    <div class="col-sm-1"></div>
</div>
</div>
</body>
</html>
