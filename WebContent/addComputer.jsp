<jsp:include page="include/header.jsp" />
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<section id="main">

	<h1>Add Computer</h1>
	<div class="form-group">
	<form action="Dashboard" method="POST">
		<fieldset>
			<div class="clearfix">
				<label for="name">Computer name:</label>
				<div class="input">
					<input class="form-control" type="text" name="name" />
				</div>
			</div>
			<div class="clearfix">
				<label for="introduced">Introduced date:</label>
				<div class="input">
					<input class="form-control" type="text" name="introducedDate" placeholder="YYYY-MM-DD" pattern="[0-9]{4}-(0[1-9]|1[012])-(0[1-9]|1[0-9]|2[0-9]|3[01])"/>
				</div>
			</div>
			<div class="clearfix">
				<label for="discontinued">Discontinued date:</label>
				<div class="input">
					<input class="form-control" type="date" name="discontinuedDate" placeholder="YYYY-MM-DD" pattern="[0-9]{4}-(0[1-9]|1[012])-(0[1-9]|1[0-9]|2[0-9]|3[01])"/>
				</div>
			</div>
			<div class="clearfix">
				<label for="company">Company Name:</label>
				<div class="input">
					<select class="form-control" name="company">
						<c:forEach var="el" items="${cyList}" >
						<option value=${el.getId()}>${el.getName()}</option>
						</c:forEach>
						
					</select>
				</div>
			</div>
		</fieldset>
		<div class="actions form-group">
			<input type="submit"  value="Add" class="btn btn-success">
			or <a href="Dashboard" class="btn btn-warning">Cancel</a>
		</div>
	</form>
	</div>
</section>

<jsp:include page="include/footer.jsp" />