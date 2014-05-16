<jsp:include page="/WEB-INF/jsp/include/header.jsp" />
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<c:set scope="request" var="action" value="/computer-database/Computer" />
<c:set scope="request" var="method" value="Create" />
<spring:message code="label.add" var="addLabel" />

<div class=container>
	<h1><spring:message code="label.add" /></h1>
	<form id=computerForm action="<c:out value="${action}/${method}" />" method="POST">
		<jsp:include page="/WEB-INF/jsp/computerForm.jsp" />
		<input type="submit" name="create" value="${addLabel}"
			class="btn btn-success">
	</form>
</div>

<jsp:include page="/WEB-INF/jsp/include/footer.jsp" />
<script type="text/javascript" src="/computer-database/scripts/dashboard.js"></script>
