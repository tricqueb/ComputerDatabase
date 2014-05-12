<jsp:include page="/WEB-INF/jsp/include/header.jsp" />
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib tagdir="/WEB-INF/tags/42" prefix="KD"%>

<div class=container>

	<h1 id="homeTitle" class="page-header">${total} Computers found</h1>

	<jsp:include page="/WEB-INF/jsp/modalForm.jsp" />

	<div id="actions">
		<KD:search value="${search}" method="search" action="Dashboard"
			id="searchbox" column="15" placeholder="Search computer"
			inline="col-xs-2" />
		<a class="btn btn-success" id="add" href="Dashboard/computer/add">Add
			Computer</a>
	</div>

	<jsp:include page="/WEB-INF/jsp/dashboardTable.jsp" />

	<nav class="navbar navbar-default navbar-fixed-bottom" role="navigation">
  		<div class="container"> 
			<KD:pagination action="Dashboard"  orderBy="${page.getOrderById()}" orderDirection="${page.getOrderDirection()}"/>
  		</div>
	</nav>

</div>

<jsp:include page="/WEB-INF/jsp/include/footer.jsp" />
<script type="text/javascript" src="scripts/dashboard.js"></script>
