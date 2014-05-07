<!-- Modal -->
	<div class="modal fade " modalShow="${validationErrorPage.getModalShow()}" id="editModal"
		tabindex="-1" role="dialog" aria-labelledby="editModalLabel"
		aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-hidden="true">&times;</button>
					<h4 class="modal-title" id="editModalLabel">Edit a computer</h4>
				</div>
				<div class="modal-body">
					<form id=computerForm action="Computer/Update" method="POST">
						<jsp:include page="/WEB-INF/jsp/editComputer.jsp" />
						<div class="modal-footer">
							<button type="button" class="btn btn-default"
								data-dismiss="modal">Close</button>
							<input type="submit" name="update" value="update"
								class="btn btn-success">
						</div>
					</form>
				</div>
			</div>
		</div>
	</div>