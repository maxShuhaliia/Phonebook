(function(){
    var app = {
        initialize : function(){
            app.setUpListeners();
        },
        setUpListeners: function(){
            $(".arrow").click(app.changeIcon);
        },
        changeIcon: function(){
            if( $(this).hasClass("checkedArrow") ){
                if( $(this).hasClass("glyphicon-sort-by-attributes") ){
                    $(this).removeClass("glyphicon-sort-by-attributes");
                    $(this).addClass("glyphicon-sort-by-attributes-alt");
                    app.setSortBy( "decrease", $(this).attr('id') ); 
                }else{
                    app.setSortBy( "increase", $(this).attr('id') );
                    $(this).addClass("glyphicon-sort-by-attributes");
                    $(this).removeClass("glyphicon-sort-by-attributes-alt");
                }
            }else{
                $(".arrow").removeClass("checkedArrow");
                $(this).addClass("checkedArrow");
                    if( $(this).hasClass("glyphicon-sort-by-attributes") ){
                        app.setSortBy( "increase", $(this).attr('id') )
                    }
                    else{
                        app.setSortBy( "decrease", $(this).attr('id') )
                    }
            }
        },
        setSortBy: function(arrow, columnId){
            var column = "";
            
            if(columnId==="fnameArrow"){
            	column = "firstname";
            }
            else if(columnId==="lnameArrow"){
            	column = "lastname";
            }
            else if(columnId==="mnameArrow"){
            	column = "middlename";
            }
            else if(columnId==="mobileArrow"){
            	column = "mobilephone";
            }
           $.ajax({
        	   type: "POST",
        	   url: "/setSortBy",
        	   data: { arrow: arrow,
        		   		column: column},
        	   success : function(data){   
        		   $("#tBodyRows").empty();
        		   var obj = jQuery.parseJSON(data);
       			   $.each(obj, function(key,val){
       					$('#tBodyRows').append('<tr  id="'+val.contactId+'">'+
       							'<td><label>'+val.firstName+'</label></td>'+
       							'<td><label>'+val.lastName+'</label></td>'+
       							'<td><label>'+val.middleName+'</label></td>'+
       							'<td><label>'+val.mobilePhone+'</label></td>'+
       							'<td><label>'+val.homePhone+'</label></td>'+
       							'<td><label>'+val.address+'</label></td>'+
       							'<td><label>'+val.email+'</label></td></tr>');
       					$('#'+val.contactId).addClass("row");	      
       					$('#'+val.contactId).addClass("pointer");        
       			    });
       			    $.getScript('files/modalForm.js', function() {
       			    //	console.log("script loaded ");
       		        });   
        		//   alert("success");
          	   },
        	   error: function(data){
        	//	   alert("error");
        	   }
           });    
        }
    };

    $(document).ready(function(){
        app.initialize();
        
        var typeArrow = $("#typeArrow").attr("class");
        var activeColumn = $("#activeColumn").attr("class");
        var isIncrease = (typeArrow=="increase");

        if(typeArrow != undefined){
//fist name
            if( activeColumn === "firstname" ){
                if(isIncrease){
                    $("#fnameArrow").addClass("checkedArrow");
                }else{
                    $("#fnameArrow").removeClass("glyphicon-sort-by-attributes");
                    $("#fnameArrow").addClass("glyphicon-sort-by-attributes-alt");
                    $("#fnameArrow").addClass("checkedArrow");
                }
//last name		   
            }else if( activeColumn === "lastname" ){
                if(isIncrease){
                    $("#lnameArrow").addClass("checkedArrow");
                }else{
                    $("#lnameArrow").removeClass("glyphicon-sort-by-attributes");
                    $("#lnameArrow").addClass("glyphicon-sort-by-attributes-alt");
                    $("#lnameArrow").addClass("checkedArrow");
                }
//middle name			   
            }else if( activeColumn === "middlename" ){
                if(isIncrease){
                    $("#mnameArrow").addClass("checkedArrow");
                }else{
                    $("#mnameArrow").removeClass("glyphicon-sort-by-attributes");
                    $("#mnameArrow").addClass("glyphicon-sort-by-attributes-alt");
                    $("#mnameArrow").addClass("checkedArrow");
                }
//mobile phone			   
            }else if( activeColumn === "mobilephone" ){
                if(isIncrease){
                    $("#mobileArrow").addClass("checkedArrow");
                }else{
                    $("#mobileArrow").removeClass("glyphicon-sort-by-attributes");
                    $("#mobileArrow").addClass("glyphicon-sort-by-attributes-alt");
                    $("#mobileArrow").addClass("checkedArrow");
                }
            }
        }//end if        
        
    });
})();


