<!-- Modal -->
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<spring:message code="label.update"  var="updateLabel"/>
	<div class="modal fade " modalShow="${validationErrorPage.getModalShow()}" id="editModal"
		tabindex="-1" role="dialog" aria-labelledby="editModalLabel"
		aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-hidden="true">&times;</button>
					<h4 class="modal-title" id="editModalLabel"><spring:message code="label.edit" /></h4>
				</div>
				<div class="modal-body">
					<form id=computerForm action="Computer/Update" method="POST">
						<jsp:include page="/WEB-INF/jsp/computerForm.jsp" />
						<div class="modal-footer">
							<button type="button" class="btn btn-default"
								data-dismiss="modal"><spring:message code="label.close" /></button>
							<input type="submit" name="update" value="${updateLabel}"
								class="btn btn-success">
						</div>
					</form>
				</div>
			</div>
		</div>
	</div>