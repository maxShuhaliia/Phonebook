(function(){

    var search = {

        initialize : function(){
            search.setUpListeners();
        },      //end initialize

        setUpListeners : function(){

            $("#firstNameSearchBy").click( {labelText:"Search by first name", column:"firstname"}, search.setSearchByBtn );
            $("#lastNameSearchBy").click( {labelText:"Search by last name", column:"lastname"}, search.setSearchByBtn );
            $("#middleNameSearchBy").click( {labelText:"Search by middle name", column:"middlename"}, search.setSearchByBtn );
            $("#searchInput").keyup( search.takeInputValue );

        },   //end setUpListeners

        setSearchByBtn: function(event){
            event.preventDefault();
            $("#labelSearchBy").text(event.data.labelText);
            search.setSearchBy(event.data.column);
        },// end setSearchByBtn
        
        setSearchBy: function(column){
        	$.ajax({
        		type: "GET",
        		url: "/setSerachBy/"+column,
        		success: function(data){
        		},
        		error: function(){
        			alert("error");
        		}
        	});
        },

        setSelectedBtnFromSession: function(){
            var column = $( "#searchByFromSession" ).attr( "class" );
            if( column=="firstname" ){
                $( "#labelSearchBy" ).text( "Search by first name" );
            }else if( column == "lastname" ){
                $( "#labelSearchBy" ).text( "Search by last name" );
            }else if( column == "middlename" ){
                $( "#labelSearchBy").text( "Search by middle name" );
            }
        }, //end of setSelectedBtnFromSession

        takeInputValue: function(){
            var text = $( "#searchInput" ).val();
            if( text!="" ) search.findContacts( text );
            if( text=="" ) search.getAllContacts();
        },// end of takeInputValue

        findContacts: function( text ){
            $.ajax({
                type: "POST",
                url: "/findAllContacts/"+text,
                success: function(data){
                    $( "#tBodyRows" ).empty();
                    var obj = jQuery.parseJSON(data);
                    $.each(obj, function(key,val){
                        $( '#tBodyRows' ).append('<tr  id="'+val.contactId+'">'+
                            '<td class="'+val.firstName+'"><label>'+val.firstName+'</label></td>'+
                            '<td class="'+val.lastName+'"><label>'+val.lastName+'</label></td>'+
                            '<td class="'+val.middleName+'"><label>'+val.middleName+'</label></td>'+
                            '<td class="'+val.mobilePhone+'"><label>'+val.mobilePhone+'</label></td>'+
                            '<td class="'+val.homePhone+'"><label>'+val.homePhone+'</label></td>'+
                            '<td class="'+val.address+'"><label>'+val.address+'</label></td>'+
                            '<td class="'+val.email+'"><label>'+val.email+'</label></td></tr>');
                        $( '#'+val.contactId ).addClass( "row" );
                        $( '#'+val.contactId ).addClass( "pointer" );
                    });
                    $.getScript( 'files/modalForm.js', function() {});
                },
                error: function(){}
            });
        },//end of findContacts

        getAllContacts: function(){
            $.ajax({
                type: "POST",
                url: "/getAllContacts",
                success: function(data){
                    $("#tBodyRows").empty();
                    var obj = jQuery.parseJSON(data);
                    $.each(obj, function(key,val){
                        $( '#tBodyRows' ).append('<tr  id="'+val.contactId+'">'+
                            '<td class="'+val.firstName+'"><label>'+val.firstName+'</label></td>'+
                            '<td class="'+val.lastName+'"><label>'+val.lastName+'</label></td>'+
                            '<td class="'+val.middleName+'"><label>'+val.middleName+'</label></td>'+
                            '<td class="'+val.mobilePhone+'"><label>'+val.mobilePhone+'</label></td>'+
                            '<td class="'+val.homePhone+'"><label>'+val.homePhone+'</label></td>'+
                            '<td class="'+val.address+'"><label>'+val.address+'</label></td>'+
                            '<td class="'+val.email+'"><label>'+val.email+'</label></td></tr>');
                        $( '#'+val.contactId ).addClass( "row" );
                        $( '#'+val.contactId ).addClass( "pointer" );
                    });
                    $.getScript('files/modalForm.js', function() {});
                    
                },
                error: function(){}
            });
        }// end of getAllContacts
    };

$(document).ready(function(){
    search.initialize();
    search.setSelectedBtnFromSession();
});

})();
