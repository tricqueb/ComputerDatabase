<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
	
	
<c:set var="hideErrors" value="${empty validationErrorPage.getHideErrors()}" />
<c:if test="${empty hideErrors}">
	<c:set var="hideErrors" value="none" />
</c:if>
	<div class="form-group">
	<label id="formAlertLabel" hidden="true" class="alert alert-danger"></label>
	<div id="formAlert" style="display:${hideErrors}" class="alert alert-danger">
	<c:forEach var="error" items="${validationErrorPage.getErrors()}">
	<li>${error.getMessage()}</li>
	</c:forEach>
	</div>
		<fieldset>
		<input type="hidden" id="id" name="id" >
			<div id="nameDiv" class="clearfix">
				<label for="name" name="name">Computer name:</label>
				<div class="input">
					<input id="name" value="${validationErrorPage.getComputerdto.getName()}" class="form-control" type="text" name="name" />
				</div>
			</div>
			<div id="introducedDateDiv class="clearfix">
				<label for="introducedDate">Introduced date:</label>
				<div class="input">
					<input id="introducedDate" value="${validationErrorPage.getComputerdto.getIntroduced()}" class="form-control" type="date" name="introducedDate" placeholder="YYYY-MM-DD" pattern="[0-9]{4}-(0[1-9]|1[012])-(0[1-9]|1[0-9]|2[0-9]|3[01])"/>
				</div>
			</div>
			<div id="discontinuedDateDiv class="clearfix">
				<label for="discontinuedDate">Discontinued date:</label>
				<div class="input">
					<input id="discontinuedDate" value="${validationErrorPage.getComputerdto.getDiscontinued()}" class="form-control" type="date" name="discontinuedDate" placeholder="YYYY-MM-DD" pattern="[0-9]{4}-(0[1-9]|1[012])-(0[1-9]|1[0-9]|2[0-9]|3[01])"/>
				</div>
			</div>
			<div class="form-group">
				<label for="company">Company Name:</label>
				<div id="companyDiv class="input">
					<select id="company" class="form-control" name="company">
					<option value=null>Inconnu</option>
						<c:forEach var="el" items="${companyList}" >
						<option value=${el.getId()}>${el.getName()}</option>
						</c:forEach>
					</select>
				</div>
			</div>
		</fieldset>
	</div>
