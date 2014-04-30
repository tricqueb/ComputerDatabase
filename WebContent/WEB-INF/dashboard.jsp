<jsp:include page="/WEB-INF/include/header.jsp" />
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib tagdir="/WEB-INF/tags/42" prefix="KD"%>

<div class=container>

	<h1 id="homeTitle" class="page-header">${page.getTotal()} Computers found</h1>

	<jsp:include page="/WEB-INF/modalForm.jsp" />

	<div id="actions">
		<KD:search value="${search}" method="search" action="Dashboard"
			id="searchbox" column="15" placeholder="Search computer"
			inline="col-xs-2" />
		<a class="btn btn-success" id="add" href="ComputerCrud">Add
			Computer</a>
	</div>

	<jsp:include page="/WEB-INF/dashboardTable.jsp" />

	<div style="text-align:center; width:100%;">
		<KD:pagination action="Dashboard?search=${search}"
			currentPage="${page.getCurrentPage()}" startPage="${page.getStartPage()}"
			endPage="${page.getEndPage()}" orderby="${page.getOrderBy()}" desc="${page.getDesc() }"/>
	</div>
</div>

<jsp:include page="/WEB-INF/include/footer.jsp" />
<script type="text/javascript" src="scripts/dashboard.js"></script>
