<jsp:include page="/WEB-INF/jsp/include/header.jsp" />

<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib tagdir="/WEB-INF/tags/42" prefix="KD"%>

<div class=container>

	<h1 id="homeTitle" class="page-header">${total} <spring:message code="label.h1" /></h1>

	<jsp:include page="/WEB-INF/jsp/editComputerModal.jsp" />

	<div id="actions">
		<KD:search value="${search}" method="search" action="Dashboard"
			id="searchbox" column="15" springlabel="label.search"
			inline="col-xs-2" />
		<a class="btn btn-success" id="add" href="Dashboard/computer/add"> <spring:message code="label.add" /> </a>
	</div>

	<jsp:include page="/WEB-INF/jsp/dashboardTable.jsp" />

	<nav class="navbar navbar-default navbar-fixed-bottom" role="navigation">
  		<div class="container"> 
			<KD:pagination action="Dashboard"  orderBy="${page.getOrderById()}" orderDirection="${page.getOrderDirection()}"/>
  		</div>
	</nav>
</div>

<jsp:include page="/WEB-INF/jsp/include/footer.jsp" />
<script type="text/javascript" src="/computer-database/scripts/dashboard.js"></script>
