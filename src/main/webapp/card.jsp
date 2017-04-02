<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="flwList" value="${sessionScope.flwSession}" />
<c:set var="bwList" value="${sessionScope.bwSession}" />
<c:set var="fwList" value="${sessionScope.fwSession}" />
<c:set var="lwList" value="${sessionScope.lwSession}" />
<c:set var="wwList" value="${sessionScope.wwSession}" />
<c:set var="mwList" value="${sessionScope.mwSession}" />
<c:set var="lhwList" value="${sessionScope.lhwSession}" />
<c:set var="hwList" value="${sessionScope.hwSession}" />
<c:set var="wswList" value="${sessionScope.wswSession}" />
<c:set var="wbwList" value="${sessionScope.wbwSession}" />
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" href="css/bootstrap.css">
    <link rel="stylesheet" href="css/extra.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <!-- Initialise JavaScript global arrays based on Java variables -->
    <script>
        var flwList = [
        <c:forEach items="${flwList}" var="flw">
            "${flw.first_name} ${flw.last_name}|${flw.id}",
        </c:forEach>];
        var bwList = [
        <c:forEach items="${bwList}" var="bw">
            "${bw.first_name} ${bw.last_name}|${bw.id}",
        </c:forEach>];
        var fwList = [
        <c:forEach items="${fwList}" var="fw">
            "${fw.first_name} ${fw.last_name}|${fw.id}",
        </c:forEach>];
        var lwList = [
        <c:forEach items="${lwList}" var="lw">
            "${lw.first_name} ${lw.last_name}|${lw.id}",
        </c:forEach>];
        var wwList = [
        <c:forEach items="${wwList}" var="ww">
            "${ww.first_name} ${ww.last_name}|${ww.id}",
        </c:forEach>];
        var mwList = [
        <c:forEach items="${mwList}" var="mw">
            "${mw.first_name} ${mw.last_name}|${mw.id}",
        </c:forEach>];
        var lhwList = [
        <c:forEach items="${lhwList}" var="lhw">
            "${lhw.first_name} ${lhw.last_name}|${lhw.id}",
        </c:forEach>];
        var hwList = [
        <c:forEach items="${hwList}" var="hw">
            "${hw.first_name} ${hw.last_name}|${hw.id}",
        </c:forEach>];
        var wswList = [
        <c:forEach items="${wswList}" var="wsw">
            "${wsw.first_name} ${wsw.last_name}|${wsw.id}",
        </c:forEach>];
        var wbwList = [
        <c:forEach items="${wbwList}" var="wbw">
            "${wbw.first_name} ${wbw.last_name}|${wbw.id}",
        </c:forEach>];
    </script>
    <script src="scripts/cardselect.js"></script>
    <title>Create Card</title>
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
                        <form id="card" action="createcard" method="post" onsubmit="return validateForm()"></form>
                        <br />
                        <table class="table table-striped table-condensed table-hover">
                            <thead>
                                <tr><th colspan="5">Main Card</th>
                                </tr>
                            </thead>
                            <c:forEach var="i" begin="1" end="5">
                                <tr>
                                    <td style="width:10%">
                                        <select id="fight${i}" form="card" name="fight${i}" onchange="changer(${i})">
                                            <option name="FLW" value="flw">FLW</option>
                                            <option name="BW" value="bw">BW</option>
                                            <option name="FW" value="fw">FW</option>
                                            <option name="LW" value="lw">LW</option>
                                            <option name="WW" value="ww">WW</option>
                                            <option name="MW" value="mw">MW</option>
                                            <option name="LHW" value="lhw">LHW</option>
                                            <option name="HW" value="hw">HW</option>
                                            <option name="WSW" value="wsw">WSW</option>
                                            <option name="WBW" value="wbw">WBW</option>
                                        </select>
                                    </td>
                                    <td>
                                        <select id="fighterA${i}" form="card" name="fighterA${i}" class="selectmin">
                                            <c:forEach items="${flwList}" var="f">
                                                <option value="${f.id}">
                                                    <c:if test="${param.selectValue == f.id}">selected</c:if>
                                                    ${f.first_name} ${f.last_name}
                                                </option>
                                            </c:forEach>
                                        </select>
                                    </td>
                                    <td style="vertical-align:middle">vs.</td>
                                    <td>
                                        <select id="fighterB${i}" form="card" name="fighterB${i}" class="selectmin">
                                            <c:forEach items="${flwList}" var="f">
                                                <option value="${f.id}">
                                                    <c:if test="${param.selectValue == f.id}">selected</c:if>
                                                    ${f.first_name} ${f.last_name}
                                                </option>
                                            </c:forEach>
                                        </select>
                                    </td>
                                    <td style="vertical-align:middle">
                                        <input id="check${i}" name="check${i}" type="checkbox" form="card" data-toggle="tooltip" data-placement="top" title="5 rounds"/>
                                    </td>
                                </tr>
                            </c:forEach>
                            <thead>
                                <tr>
                                    <th colspan="5">Preliminary Card</th>
                                </tr>
                            </thead>
                            <c:forEach var="i" begin="6" end="9">
                                <tr>
                                    <td style="width:10%">
                                        <select id="fight${i}" form="card" name="fight${i}" onchange="changer(${i})">
                                            <option name="FLW" value="flw">FLW</option>
                                            <option name="BW" value="bw">BW</option>
                                            <option name="FW" value="fw">FW</option>
                                            <option name="LW" value="lw">LW</option>
                                            <option name="WW" value="ww">WW</option>
                                            <option name="MW" value="mw">MW</option>
                                            <option name="LHW" value="lhw">LHW</option>
                                            <option name="HW" value="hw">HW</option>
                                            <option name="WSW" value="wsw">WSW</option>
                                            <option name="WBW" value="wbw">WBW</option>
                                        </select>
                                    </td>
                                    <td>
                                        <select id="fighterA${i}" form="card" name="fighterA${i}" class="selectmin">
                                            <c:forEach items="${flwList}" var="f">
                                                <option value="${f.id}">
                                                    <c:if test="${param.selectValue == f.id}">selected</c:if>
                                                    ${f.first_name} ${f.last_name}
                                                </option>
                                            </c:forEach>
                                        </select>
                                    </td>
                                    <td style="vertical-align:middle">vs.</td>
                                    <td>
                                        <select id="fighterB${i}" form="card" name="fighterB${i}" class="selectmin">
                                            <c:forEach items="${flwList}" var="f">
                                                <option value="${f.id}">
                                                    <c:if test="${param.selectValue == f.id}">selected</c:if>
                                                    ${f.first_name} ${f.last_name}
                                                </option>
                                            </c:forEach>
                                        </select>
                                    </td>
                                    <td style="vertical-align:middle">
                                        <input id="check${i}" name="check${i}" type="checkbox" form="card" data-toggle="tooltip" data-placement="top" title="5 rounds"/>
                                    </td>
                                </tr>
                            </c:forEach>
                            <thead>
                                <tr><th colspan="5">Early Prelims</th></tr>
                            </thead>
                            <c:forEach var="i" begin="10" end="11">
                                <tr>
                                    <td style="width:10%">
                                        <select id="fight${i}" form="card" name="fight${i}" onchange="changer(${i})">
                                            <option name="FLW" value="flw">FLW</option>
                                            <option name="BW" value="bw">BW</option>
                                            <option name="FW" value="fw">FW</option>
                                            <option name="LW" value="lw">LW</option>
                                            <option name="WW" value="ww">WW</option>
                                            <option name="MW" value="mw">MW</option>
                                            <option name="LHW" value="lhw">LHW</option>
                                            <option name="HW" value="hw">HW</option>
                                            <option name="WSW" value="wsw">WSW</option>
                                            <option name="WBW" value="wbw">WBW</option>
                                        </select>
                                    </td>
                                    <td>
                                        <select id="fighterA${i}" form="card" name="fighterA${i}" class="selectmin">
                                            <c:forEach items="${flwList}" var="f">
                                                <option value="${f.id}">
                                                    <c:if test="${param.selectValue == f.id}">selected</c:if>
                                                    ${f.first_name} ${f.last_name}
                                                </option>
                                            </c:forEach>
                                        </select>
                                    </td>
                                    <td style="vertical-align:middle">vs.</td>
                                    <td>
                                        <select id="fighterB${i}" form="card" name="fighterB${i}" class="selectmin">
                                            <c:forEach items="${flwList}" var="f">
                                                <option value="${f.id}">
                                                    <c:if test="${param.selectValue == f.id}">selected</c:if>
                                                    ${f.first_name} ${f.last_name}
                                                </option>
                                            </c:forEach>
                                        </select>
                                    </td>
                                    <td style="vertical-align:middle">
                                        <input id="check${i}" name="check${i}" type="checkbox" form="card" data-toggle="tooltip" data-placement="top" title="5 rounds"/>
                                    </td>
                                </tr>
                            </c:forEach>
                        </table>
                        <input form="card" type="submit" value="Finalise" /><br /><br />
                        <div id="error" class="alert alert-danger fade in" style="text-align:center"></div>
                    </div>
                    <div class="col-sm-1 col-md-1"></div>
                </div>
            </div><br />
            <div style="text-align:center"><c:import url="/footer.jsp" /></div>
        </div>
        <div class="col-sm-1 col-sm-1"></div>
    </div>
</div>
</body>
</html>
