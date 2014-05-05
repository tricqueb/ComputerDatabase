<jsp:include page="include/header.jsp" />
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set scope="request" var="action" value="ComputerCrud" />
<c:set scope="request" var="method" value="add" />
<div class=container>
	<h1>Add Computer</h1>
	<form id=computerForm action="ComputerCrud" method="POST">
		<jsp:include page="/WEB-INF/editComputer.jsp" />
		<input type="submit" name="create" value="create"
			class="btn btn-success">
	</form>
</div>

<jsp:include page="/WEB-INF/include/footer.jsp" />
<script type="text/javascript" src="scripts/dashboard.js"></script>
