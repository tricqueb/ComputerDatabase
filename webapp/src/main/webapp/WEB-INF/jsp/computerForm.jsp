<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
	
	
<c:set var="hideErrors" value="${validationErrorPage.getHideErrors()}" />
<c:if test="${empty hideErrors}">
	<c:set var="hideErrors" value="none" />
</c:if>
	<div class="form-group">
	<label id="formAlertLabel" hidden="true" class="alert alert-danger"></label>
	<div id="formAlert" style="display:${hideErrors}" class="alert alert-danger">
	<c:forEach var="error" items="${validationErrorPage.getConstraintViolation()}">
	<li>
	<spring:message code="${error.getMessage()}" />
	</li>
	</c:forEach>
	</div>
		<fieldset>
		<input type="hidden" id="id" name="id" >
			<div id="nameDiv" class="clearfix">
				<label for="name"><spring:message code="label.ComputerName" /></label>
				<div class="input">
					<input id="name" value="${validationErrorPage.dto.getName()}" class="form-control" type="text" name="name" />
				</div>
			</div>
			<div id="introducedDateDiv" class="clearfix">
				<label for="introduced"><spring:message code="label.IntroducedDate" /></label>
				<div class="input">
					<input id="introduced" value="${validationErrorPage.dto.getIntroduced()}" class="form-control" type="text" name="introduced" placeholder="<spring:message code='label.dateFormat' />" />
				</div>
			</div>
			<div id="discontinuedDateDiv" class="clearfix">
				<label for="discontinued"><spring:message code="label.DiscontinuedDate" />:</label>
				<div class="input">
					<input id="discontinued" value="${validationErrorPage.dto.getDiscontinued()}" class="form-control" type="text" name="discontinued" placeholder="<spring:message code='label.dateFormat' />" />
				</div>
			</div>
			<div class="form-group">
				<label for="companyId"><spring:message code="label.CompanyName" /></label>
				<div id="companyDiv" class="input">
					<select id="companyId" class="form-control" name="companyId">
					<option value=null>Inconnu</option>
						<c:forEach var="el" items="${companyList}" >
						<option value="${el.getId()}">${el.getName()}</option>
						</c:forEach>
					</select>
				</div>
			</div>
		</fieldset>
	</div>
