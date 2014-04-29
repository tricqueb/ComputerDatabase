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

<!-- <div class=container> -->
<!-- 	<h1>Add Computer</h1> -->
<!-- 	<div class="form-group"> -->
<!-- 	<form action="ComputerCrud" method="POST"> -->
<!-- 		<fieldset> -->
<!-- 			<div class="clearfix"> -->
<!-- 				<label for="name">Computer name:</label> -->
<!-- 				<div class="input"> -->
<!-- 					<input class="form-control" type="text" name="name" required /> -->
<!-- 				</div> -->
<!-- 			</div> -->
<!-- 			<div class="clearfix"> -->
<!-- 				<label for="introduced">Introduced date:</label> -->
<!-- 				<div class="input"> -->
<!-- 					<input class="form-control" type="date" name="introducedDate" placeholder="YYYY-MM-DD" pattern="[0-9]{4}-(0[1-9]|1[012])-(0[1-9]|1[0-9]|2[0-9]|3[01])"/> -->
<!-- 				</div> -->
<!-- 			</div> -->
<!-- 			<div class="clearfix"> -->
<!-- 				<label for="discontinued">Discontinued date:</label> -->
<!-- 				<div class="input"> -->
<!-- 					<input class="form-control" type="date" name="discontinuedDate" placeholder="YYYY-MM-DD" pattern="[0-9]{4}-(0[1-9]|1[012])-(0[1-9]|1[0-9]|2[0-9]|3[01])"/> -->
<!-- 				</div> -->
<!-- 			</div> -->
<!-- 			<div class="form-group"> -->
<!-- 				<label for="company">Company Name:</label> -->
<!-- 				<div class="input"> -->
<!-- 					<select class="form-control" name="company"> -->
<!-- 					<option value=null>Inconnu</option> -->
<%-- 						<c:forEach var="el" items="${cyList}" > --%>
<%-- 						<option value=${el.getId()}>${el.getName()}</option> --%>
<%-- 						</c:forEach> --%>
<!-- 					</select> -->
<!-- 				</div> -->
<!-- 			</div> -->
<!-- 		</fieldset> -->
<!-- 		<div class="actions form-group"> -->
<!-- 			<input type="submit"  name="create" value="create" class="btn btn-success"> -->
<!-- 			or <a href="Dashboard" class="btn btn-warning">Cancel</a> -->
<!-- 		</div> -->
<!-- 	</form> -->
<!-- 	</div> -->
<!-- </div> -->

<jsp:include page="/WEB-INF/include/footer.jsp" />
<script type="text/javascript" src="scripts/dashboard.js"></script>
