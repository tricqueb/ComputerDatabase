<jsp:include page="/WEB-INF/include/header.jsp" />
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="computerCount" value="${cList.size()}" />

<div class=container>

<h1 id="homeTitle" class="page-header">${computerCount} Computers
	found</h1>
	 
<div id="actions"> 
<div class="form-group">
		<form class="form-inline" action="/computer-database/Dashboard" method="GET">
			<input class="form-control" type="search" id="searchbox" name="search" value=""
				placeholder="Search name"> <input type="submit"
				id="searchsubmit" value="Filter by name" class="btn btn-primary">
		</form>
		<a class="btn btn-success" id="add" href="AddComputer">Add Computer</a>
		</div>
</div>

<table class="computers table table-striped">
	<thead>
		<tr>
			<!-- Variable declarations for passing labels as parameters -->
			<!-- Table header for Computer Name -->
			<th>Computer Name</th>
			<th>Introduced Date</th>
			<!-- Table header for Discontinued Date -->
			<th>Discontinued Date</th>
			<!-- Table header for Company -->
			<th>Company</th>
		</tr>
	</thead>
	<tbody>

		<!-- Afficher tous les éléments d'une collection dans le request-->

		<c:forEach var="el" items="${cList}">
			<tr>
				<td>${el.getName()}</td>
				<td>${el.getIntroduced().toString()}</td>
				<td>${el.getDiscontinued()}</td>
				<td>${el.getCompany().getName()}</td>
			</tr>
		</c:forEach>
	</tbody>
</table>
</div>

<jsp:include page="/WEB-INF/include/footer.jsp" />
