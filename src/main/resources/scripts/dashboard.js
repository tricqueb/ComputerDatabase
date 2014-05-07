$(document).ready(function() {

	$(".edit").click(function() {
		var values = $($(this).parent()).prevAll();
		if($(values[0]).attr('id'))
			$("#company").val($(values[0]).attr('id'));
		else
			$("#company").val("null");
		$("#discontinuedDate").val($(values[1]).text());
		$("#introducedDate").val($(values[2]).text());
		$("#name").val($(values[3]).text());
		$("#id").val($(values[4]).text());
		$("#computerForm").validate().resetForm();
	});

	//Modal management
	if( $("#editModal").attr("modalShow") == "true") {
		$("#editModal").modal('toggle');
		$("#editModal").attr("modalShow","false");
	}

	
//	$("#name").change(function(){
//	if($(this).val().length > 255) {
//	$("#nameAlert").show();
//	$("#nameAlert").text("Length must be inferior to 255");
//	} else
//	if(($(this).val().length < 1)){
//	$("#nameAlert").show();
//	$("#nameAlert").text("Name can't be empty !");
//	}
//	});

//	$("#introducedDate").change(function(){});

	jQuery.validator.addMethod(
			"greaterThan", function(value,element,params) {
				if(value!="")
					return (value >= params);
				else
					return true;
			}, "Please specify a discontinue date later than introduce date");	

	$("#computerForm").validate({
		errorContainer: "#formAlert",
//		errorLabelContainer: "#formAlertLabel ul",
//		wrapper: "li", //FIXME :Why tons of li are added ?
		errorPlacement: function(error, element) {
			//$("#formAlert").append("<li>"+$(element).parent().prev("label").attr("for")+"</li>");			
			error.appendTo("#formAlert");
			//FIXME : how to prevent error to disappear when correcting only one element
//			$(element).parent().prev("label").appendTo("#formAlert");
//			element.appendTo("#formAlert");
//			},
//			showErrors: function(errorMap, errorList) {
//			$(errorMap).each(function(key,val){
//			$("#formAlert").html( $("#formAlert").html()+
//			key+""+val
//			);});
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
				required:"Please, specify a name",
				maxlength:"Name must be less than 255 characters"
			},
			introducedDate: {
				required:"You should have an introduced date if you have an discontinued date",//FIXME Does not work... shitty plugin ?
			}
		},
		success: function(label,element) {
			//FIXME see error placement
//			$(element).prev().insertBefore("#"+$(label).attr("for")+"Div");
//			$(element).appendTo("#"+$(element).attr("name")+"Div");
		},
	});
});
