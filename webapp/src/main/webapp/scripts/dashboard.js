$(document).ready(function() {

	  
	//Filling with correponding line data when user click on edit button
	$(".edit").click(function() {
		var values = $($(this).parent()).prevAll();
		if($(values[0]).attr('id'))
			$("#companyId").val($(values[0]).attr('id'));
		else
			$("#companyId").val("null");
		$("#discontinued").val($(values[1]).text());
		$("#introduced").val($(values[2]).text());
		$("#name").val($(values[3]).text());
		$("#id").val($(values[4]).text());
		$("#computerForm").validate().resetForm();
	});
	

	//Modal management
	if( $("#editModal").attr("modalShow") == "true") {
		$("#editModal").modal('toggle');
		$("#editModal").attr("modalShow","false");
	}

	//Custom Date validator
	jQuery.validator.addMethod(
			"greaterThan", function(value,element,params) {
				if(value!="")
					return (value >= params);
				else
					return true;
			}, strings['error.33']);	

	$("#computerForm").validate({
		errorContainer: "#formAlert",
		errorPlacement: function(error, element) {
			
			error.appendTo("#formAlert");

		},
		highlight: function(element, errorClass) {
			$(element).fadeOut(function() {
				$(element).fadeIn();
			});
		},

		rules: {
			name: {required:true, maxlength:255},
			introducedDate:{required:"#discontinuedDate:filled"	},
			discontinuedDate:{greaterThan:$("#introducedDate").val()},
			company:{}
		},
		messages:{
			name: {
				required:strings['error.10'],
				maxlength:strings['error.11']
			},
			introducedDate: {
				required:strings['error.20'],
			}
		},
	});
});
