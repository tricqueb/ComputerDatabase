<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib tagdir="/WEB-INF/tags/42" prefix="KD"%>

<table class="computers table table-striped">
		<thead>
			<tr>
				<KD:th-orderBy text="Computer Name" value="2" action="Dashboard" page="${page}" search="${search}" width="30%" />
				<KD:th-orderBy text="Introduced Date" value="3" action="Dashboard" page="${page}" search="${search}" />
				<KD:th-orderBy text="Discontinued" value="4" action="Dashboard" page="${page}" search="${search}"/>
				<KD:th-orderBy text="Company" value="6" action="Dashboard" page="${page}" search="${search}" width="20%"/>
				<th></th>
			</tr>
		</thead>
		<tbody>

			<!-- Afficher tous les éléments d'une collection dans le request-->

			<c:forEach var="el" items="${page.getCList()}">
				<tr>
					<KD:tdStyle value="${el.getId()}" name="id" hidden="none" action="Dashboard"/>
					<KD:tdStyle value="${el.getName()}" name="name" action="Dashboard"/>
					<KD:tdStyle value="${el.getIntroduced().toString()}" name="introduced" action="Dashboard"/>
					<KD:tdStyle value="${el.getDiscontinued()}" name="discontinued" action="Dashboard"/>
					<KD:tdStyle id="${el.getCompany().getId()}" name="companyName"
							value="${el.getCompany().getName()}" action="Dashboard"/>
					<td>
				
		
						<button class="btn btn-warning edit" data-toggle="modal"
							data-target="#editModal">Edit</button> 						
					</td>
					<td>
						<form class="form-inline" action="ComputerCrud" method="post">
							<input type="hidden" name="id" value="${el.getId()}"></input> <input
								type="submit" name="delete" value="delete"
								class="btn btn-danger"></input>
						</form>
					</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>