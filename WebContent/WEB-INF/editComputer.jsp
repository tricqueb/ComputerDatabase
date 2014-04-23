	<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<form id=computerForm action="ComputerCrud" method="POST">  
<div class="modal-body">
	<div class="form-group">
	<label id="formAlertLabel" hidden="true" class="alert alert-danger"></label>
	<div id="formAlert" hidden="true" class="alert alert-danger"></div>
		<fieldset>
		<input type="hidden" id="id" name="id" >
			<div id="nameDiv" class="clearfix">
				<label for="name" name="name">Computer name:</label>
				<div class="input">
					<input id="name" class="form-control" type="text" name="name" />
				</div>
			</div>
			<div id="introducedDateDiv class="clearfix">
				<label for="introducedDate">Introduced date:</label>
				<div class="input">
					<input id="introducedDate" class="form-control" type="date" name="introducedDate" placeholder="YYYY-MM-DD" pattern="[0-9]{4}-(0[1-9]|1[012])-(0[1-9]|1[0-9]|2[0-9]|3[01])"/>
				</div>
			</div>
			<div id="discontinuedDateDiv class="clearfix">
				<label for="discontinuedDate">Discontinued date:</label>
				<div class="input">
					<input id="discontinuedDate" class="form-control" type="date" name="discontinuedDate" placeholder="YYYY-MM-DD" pattern="[0-9]{4}-(0[1-9]|1[012])-(0[1-9]|1[0-9]|2[0-9]|3[01])"/>
				</div>
			</div>
			<div class="form-group">
				<label for="company">Company Name:</label>
				<div id="companyDiv class="input">
					<select id="company" class="form-control" name="company">
					<option value=null>Inconnu</option>
						<c:forEach var="el" items="${cyList}" >
						<option value=${el.getId()}>${el.getName()}</option>
						</c:forEach>
					</select>
				</div>
			</div>
		</fieldset>
	</div>
	<div class="modal-footer">
        <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
        <input type="submit"  name="update" value="update" class="btn btn-success">
      </div>
      
</div>
</form>