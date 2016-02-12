
(function(){

    var app = {

        initialize: function(){
            this.setUpListeners();
        },
        setUpListeners: function(){
            $( "tbody > tr" ).click(app.paintSelectedRow);
            $( ".buttonNewContact" ).click(app.createNewContact);
            $( "#cancelButtonCreate" ).click(app.createButtonRefuse); //close dialog form
            $( ".buttonContactEdit" ).click(app.editContact);
            $( ".buttonDeleteContact" ).click(app.showDeleteContact);
            $( "#admitButtonDelete" ).click(app.removeContactButton);
            $( "#cancelButtonDelete" ).click(app.removeContactButtonRefuse);

        },

        paintSelectedRow: function(){
            if (this.style.background == "" || this.style.background == "white") {
                $('.pointer').css("background", 'white');
                //gray
                $(this).css('background', 'rgb(169, 169, 169)');
            }
            else {
                $(this).css('background', 'white');
            }
        },
 //create btn
        createNewContact: function(){
            if( app.isSelectedBtn() ){
                $('#contactForm').get(0).setAttribute('action', "/addContact");

                $('input[name="firstName"]').val("");
                $('input[name="lastName"]').val("");
                $('input[name="middleName"]').val("");
                $('input[name="mobilePhone"]').val("");
                $('input[name="homePhone"]').val("");
                $('input[name="address"]').val("");
                $('input[name="email"]').val("");

                $('#headerForm').html("new Contact");
                $('#admitButton').html("Create");
                $('#divContactForm').css("display", "block");
                $('#wrap').css("display", "block");
            }
        },
        isSelectedBtn: function(){
            return  $('#wrap').css("display")!=="block";
        },
//create btn refuse(close dialog form)
        createButtonRefuse: function(){
            app.hideForm();
        },
        hideForm: function(){
            $('#divContactForm').css("display", "none");
            $('#wrap').css("display", "none");
        },
 //edit contact btn
        editContact: function(){
            //<![CDATA[
            if( app.isSelectedBtn && app.isSelectedRow() ) {
            	
                $('tbody > tr').each(function(index, element) {
                    if( $(element).css("background-color")	=== "rgb(169, 169, 169)" ){
                        var id = $(element).attr('id');
                        $("#contactId").val(id);
                        var path = "/editContact/"+id;
                        $('#contactForm').get(0).setAttribute('action', path);
                        var firstName = $(element).children().eq(0).attr("class");
                        var lastName = $(element).children().eq(1).attr("class");
                        var middleName = $(element).children().eq(2).attr("class");
                        var mobilePhone = $(element).children().eq(3).attr("class");
                        var homePhone = $(element).children().eq(4).attr("class");
                        var address = $(element).children().eq(5).attr("class");
                        var email = $(element).children().eq(6).attr("class");

                        $('input[name="firstName"]').val(firstName);
                        $('input[name="lastName"]').val(lastName);
                        $('input[name="middleName"]').val(middleName);
                        $('input[name="mobilePhone"]').val(mobilePhone);
                        $('input[name="homePhone"]').val(homePhone);
                        $('input[name="address"]').val(address);
                        $('input[name="email"]').val(email);
                    }
                });
                $('#headerForm').html("edit Contact");
                $('#admitButton').html("Create");
                $('#divContactForm').css("display", "block");
                $('#wrap').css("display", "block");
            }
            //]]>
        },
        isSelectedRow: function(){
     
            var isSelected = false;
            $('tbody > tr').each(function(index, element) {
                //if color is gray
                if( $(element).css("background-color") === "rgb(169, 169, 169)" ){
                	isSelected = true;
                 }
            });
            
            return isSelected;   
        },
//delete contact btn
        showDeleteContact: function(){
            //<![CDATA[
            if( app.isSelectedBtn() && app.isSelectedRow() ) {
                $('#deleteContactForm').css("display", "block");
                $('#wrap').css("display", "block");
            }
            //]]>
        },
        removeContactButton: function(){
            $('tbody > tr').each(function(index, element) {
                //if color is gray
                if($(element).css("background-color")=== "rgb(169, 169, 169)"){
                    var id = $(element).attr('id');
                    $("#"+id).remove();
                    //remove it from db
                    deleteContactFromDataBase(id);
                };

                $('#deleteContactForm').css("display", "none");
                $('#wrap').css("display", "none");
            });
        },
        removeContactButtonRefuse: function(){
        	 $('#deleteContactForm').css("display", "none");
             $('#wrap').css("display", "none");
        },
    };

    $(document).ready(function () {
    	$("#mobilePhone").mask("+38 (099) 999-99-99");
        app.initialize();
    });

function deleteContactFromDataBase(id){
    $.ajax({
        type: 'POST',
        url: '/removeContact/'+id,
        success: function(){
            //  window.location.reload();
            //  alert("Success");
        },
        error: function(){
            //    alert("error");
        }
    });
};


})();