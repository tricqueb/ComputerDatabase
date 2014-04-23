<jsp:include page="/WEB-INF/include/header.jsp" />
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<div class=container>

	<h1 id="homeTitle" class="page-header">${cListSize} Computers
		found</h1>

	<!-- Modal -->
	<div class="modal fade" id="editModal" tabindex="-1" role="dialog"
		aria-labelledby="editModalLabel" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-hidden="true">&times;</button>
					<h4 class="modal-title" id="editModalLabel">Edit a computer</h4>
				</div>
				<jsp:include page="/WEB-INF/editComputer.jsp" />
			</div>
		</div>
	</div>

	<div id="actions">
		<div class="form-group">
			<form class="form-inline" action="/computer-database/Dashboard"
				method="GET">
				<input class="form-control" type="search" id="searchbox"
					name="search" value="" placeholder="Search name"> <input
					type="submit" id="searchsubmit" value="Filter by name"
					class="btn btn-primary">
			</form>
			<a class="btn btn-success" id="add" href="ComputerCrud">Add
				Computer</a>
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
				<th></th>
			</tr>
		</thead>
		<tbody>

			<!-- Afficher tous les éléments d'une collection dans le request-->

			<c:forEach var="el" items="${cList}">
				<tr>
					<td hidden="true">${el.getId()}</td>
					<td>${el.getName()}</td>
					<td>${el.getIntroduced().toString()}</td>
					<td>${el.getDiscontinued()}</td>
					<td id=${el.getCompany().getId()}>
						${el.getCompany().getName()}</td>
					<td>
						<!-- 						<form class="form-inline" action="ComputerCrud" method="post"> -->
						<!-- 							<input type="hidden" name="EditValue" --> <%-- 								value="${el.getCompany().getName()}"></input>  --%>
						<!-- 								<input type="submit" name="Edit" value="Edit" class="btn btn-warning" data-toggle="modal" data-target="#editModal"></input> -->
						<button class="btn btn-warning edit" data-toggle="modal"
							data-target="#editModal">Edit</button> <!-- 						</form> -->
					</td>
					<td>
						<form class="form-inline" action="ComputerCrud" method="post">
							<input type="hidden" name="deleteValue" value="${el.getId()}"></input>
							<input type="submit" name="delete" value="delete"
								class="btn btn-danger"></input>
						</form>
					</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	
	<ul class="pagination">
	<li><a href="#">&laquo;</a></li>
	<c:forEach var="page" begin="1" end="${nbPages}">
		<li><a href=<c:url value="Dashboard">	
						<c:param name="pageNumber" value="${page}" />
					</c:url>>
				${page}
			</a>
		</li>
		</c:forEach>
		<li><a href="#">&raquo;</a></li>
	</ul>
	
</div>

<jsp:include page="/WEB-INF/include/footer.jsp" />
<script type="text/javascript" src="scripts/dashboard.js"></script>
