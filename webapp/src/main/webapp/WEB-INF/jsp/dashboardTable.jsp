<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib tagdir="/WEB-INF/tags/42" prefix="KD"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<spring:message code="label.ComputerName" var="ComputerNameLabel"/>
<spring:message code="label.IntroducedDate" var="IntroducedDateLabel"/>
<spring:message code="label.DiscontinuedDate" var="DiscontinuedDateLabel"/>
<spring:message code="label.CompanyName" var="CompanyNameLabel"/>
<spring:message code="label.delete" var="deleteLabel"/>

<table class="computers table table-striped">
		<thead>
			<tr>
				<KD:th-orderBy text="${ComputerNameLabel}" value="2" action="Dashboard" page="${page}"  width="30%" />
				<KD:th-orderBy text="${IntroducedDateLabel}" value="3" action="Dashboard" page="${page}"  />
				<KD:th-orderBy text="${DiscontinuedDateLabel}" value="4" action="Dashboard" page="${page}" />
				<KD:th-orderBy text="${CompanyNameLabel}" value="6" action="Dashboard" page="${page}"  width="20%"/>
				<th></th>
			</tr>
		</thead>
		<tbody>

			<!-- Afficher tous les éléments d'une collection dans le request-->

			<c:forEach var="el" items="${page.getComputerList()}">
				<tr>
					<KD:tdStyle value="${el.getId()}" name="id" hidden="none" action="Dashboard"/>
					<KD:tdStyle value="${el.getName()}" name="name" action="Dashboard"/>
					<KD:tdStyle value="${el.getIntroduced()}" name="introduced" action="Dashboard"/>
					<KD:tdStyle value="${el.getDiscontinued()}" name="discontinued" action="Dashboard"/>
					<KD:tdStyle id="${el.getCompany().getId()}" name="companyName"
							value="${el.getCompany().getName()}" action="Dashboard"/>
					<td>
				
		
						<button class="btn btn-warning edit" data-toggle="modal"
							data-target="#editModal"><spring:message code="label.edit" /></button> 						
					</td>
					<td>
						<form class="form-inline" action="Computer/Delete" method="post">
							<input type="hidden" name="id" value="${el.getId()}"></input> <input
								type="submit" name="delete" value="${deleteLabel}"
								class="btn btn-danger"></input>
						</form>
					</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>