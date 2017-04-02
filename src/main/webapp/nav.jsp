<script>
    $(function() {
        $(".dropdown").hover(
        function(){ $(this).addClass('open'); },
        function(){ $(this).removeClass('open'); }
        );
    });
</script>
<h3>UFC Simulator</h3>
<nav class="navbar navbar-inverse">
    <div class="container-fluid">
        <ul class="nav navbar-nav">
            <li><a href="rankings.jsp">Home</a></li>
            <li class="dropdown">
                <a class="dropdown-toggle" data-toggle="dropdown" href="#">Rankings
                    <span class="caret"></span></a>
                <ul class="dropdown-menu">
                    <li><a href="rankings.jsp?wc=flw">Flyweight</a></li>
                    <li><a href="rankings.jsp?wc=bw">Bantamweight</a></li>
                    <li><a href="rankings.jsp?wc=fw">Featherweight</a></li>
                    <li><a href="rankings.jsp?wc=lw">Lightweight</a></li>
                    <li><a href="rankings.jsp?wc=ww">Welterweight</a></li>
                    <li><a href="rankings.jsp?wc=mw">Middleweight</a></li>
                    <li><a href="rankings.jsp?wc=lhw">Light Heavyweight</a></li>
                    <li><a href="rankings.jsp?wc=hw">Heavyweight</a></li>
                    <li><a href="rankings.jsp?wc=wsw">Women's Strawweight</a></li>
                    <li><a href="rankings.jsp?wc=wbw">Women's Bantamweight</a></li>
                </ul>
            </li>
            <li><a href="card.jsp">Create Card</a></li>
        </ul>
        <ul class="nav navbar-nav navbar-right">
            <li><a href="reset"><span class="glyphicon glyphicon-refresh"></span> Reset Stats</a></li>
        </ul>
    </div>
</nav>