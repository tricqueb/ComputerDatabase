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
					return (Date.parse(value) >= Date.parse(params));
				else
					return true;
			}, labels['error.33']);	
	
	//Regex method
	$.validator.addMethod(
	        "regex",
	        function(value, element, regexp) {
	            var regex = new RegExp(regexp);
	            return this.optional(element) || regex.test(value);
	        },
	        "Format is not correct"
	);
	
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
			introduced:{required:"#discontinued:filled", regex:labels['js.format.date']},
			discontinued:{greaterThan:$("#introduced").val(), regex:labels['js.format.date']},
			company:{}
		},
		messages:{
			name: {
				required:labels['error.10'],
				maxlength:labels['error.11']
			},
			introduced: {
				required:labels['error.20'],
				regex:labels['error.21']
			},
			discontinued: {
				regex:labels['error.31'],
			}
		},
	});
});
