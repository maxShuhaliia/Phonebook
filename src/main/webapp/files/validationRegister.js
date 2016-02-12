(function (){

 var app = {
        
        initialize : function(){
            this.setUpListeners();    
        },      //end initialize

        setUpListeners : function(){
           var $form = $('form');
            $form.on('submit', app.submitForm);
            $form.on('keydown', 'input', app.removeError);    
        },   //end setUpListeners

        submitForm : function( e ){     
            var form = $(this);
            if(app.validateForm(form)===false) e.preventDefault();
        }, // end submitForm
  
     validateForm : function(form){
          var inputs = form.find('input');
            var valid = true;

           $.each(inputs, function(index, val){
             var input = $(val),
                   val = input.val(),
                   formGroup = input.parents('.form-group'),
                   label = formGroup.find('label').text().toLowerCase(),
                   textError = 'this field is required';
               
               if(val.length===0){
                   valid = false;
                   formGroup.addClass('has-error').removeClass('has-success');
                   input.tooltip({
                       trigger: 'manual',
                       placement : 'right',
                       title: textError
                   }).tooltip('show');
               }else{
                    formGroup.addClass('has-success').removeClass('has-error');

                 switch(index){    
                      //login
                       case 0 :   if( app.isValidLogin(val) && val.length<=15 &&  val.length>=3 ){
                    	   			//	alert(app.isUserFind(val));
	                    	   			if( !app.isUserFind(val) ){
				                           var icon = document.getElementById("LoginIcon");
					                       icon.className = "glyphicon glyphicon-ok success-icon";
	                    	   			}	
	                    	   			else{

	                    	   			   valid = false;
	                                       formGroup.removeClass('has-success').addClass('has-error');
	                                       var icon = document.getElementById("LoginIcon");
	                                       icon.className = "glyphicon glyphicon-remove error-icon";
	                                       input.tooltip({
	                                              trigger: 'manual',
	                                              placement : 'right',
	                                                title: 'user with this login already exists'
	                                            }).tooltip('show');
	                    	   				
	                    	   			}
                                    }else{  
                                    valid = false;
                                    formGroup.removeClass('has-success').addClass('has-error');
                                    var icon = document.getElementById("LoginIcon");
                                    icon.className = "glyphicon glyphicon-remove error-icon";
                                    input.tooltip({
                                           trigger: 'manual',
                                           placement : 'right',
                                             title: 'size of login must be beatween 3 and 15,\n including only English characters , and it\n does not start  with the digit'
                                         }).tooltip('show');
                                    }   break;  //end else
                       //name
                       case 1 :    if( app.isValidInitial(val) && val.length<=15 &&  val.length>=3 ){
                                    var icon = document.getElementById("nameIcon");
                                    icon.className = "glyphicon glyphicon-ok success-icon";
                                  }else{
                                    valid = false;
                                    formGroup.removeClass('has-success').addClass('has-error');
                                    var icon = document.getElementById("nameIcon");
                                    icon.className = "glyphicon glyphicon-remove error-icon"; 
                                    input.tooltip({
                                               trigger: 'manual',
                                               placement : 'right',
                                               title: 'size of name must be beatween 3 and 15\n, including only characters'
                                           }).tooltip( 'show' );   
                                  }  break;                         
                      //surname
                       case 2 :   if( app.isValidInitial(val) && val.length<=15 &&  val.length>=3 ){
                                    var icon = document.getElementById("surnameIcon");
                                    icon.className = "glyphicon glyphicon-ok success-icon";
                                  }else{
                                    valid = false;
                                    formGroup.removeClass('has-success').addClass('has-error');
                                    var icon = document.getElementById("surnameIcon");
                                    icon.className = "glyphicon glyphicon-remove error-icon"; 
                                    input.tooltip({
                                               trigger: 'manual',
                                               placement : 'right',
                                               title: 'size of surname must be beatween 3 and 15,\n including only characters'
                                           }).tooltip( 'show' );
                                  }   break;  
                      //middle name
                       case 3 :    if( app.isValidInitial(val) && val.length<=15 &&  val.length>=3 ){
                                    var icon = document.getElementById("middleNameIcon");
                                    icon.className = "glyphicon glyphicon-ok success-icon";
                                  }else{  
                                    valid = false;  
                                    formGroup.removeClass('has-success').addClass('has-error');
                                    var icon = document.getElementById("middleNameIcon");
                                    icon.className = "glyphicon glyphicon-remove error-icon"; 
                                    input.tooltip({
                                               trigger: 'manual',
                                               placement : 'right',
                                               title: ' middle name must be beatween 3 and 15,\n including only characters '
                                           }).tooltip( 'show' );  
                                  }  break; 
                       //password
                       case 4:  if( app.isValidPassword(val) && val.length<=15 &&  val.length>=5 ) {
                                    var icon = document.getElementById( "passwordIcon" );
                                    icon.className = "glyphicon glyphicon-ok success-icon";
                                }else{
                                    valid = false;
                                    formGroup.removeClass( 'has-success' ).addClass( 'has-error' );
                                    var icon = document.getElementById( "passwordIcon" );
                                    icon.className = "glyphicon glyphicon-remove error-icon"; 
                                    input.tooltip({
                                               trigger: 'manual',
                                               placement : 'right',
                                               title: 'password must be beatween 5 and 15,\n including minimum one digit \nand one upper case char'
                                           }).tooltip( 'show' );
                               }        
               } //end of switch
               }        //end of else   
           });   //end of each
              return valid; 
     },          // end of validateForm
         
     removeError : function(){
             $(this).tooltip('destroy').parent('.form-group').removeClass('has-error');        
     },   //end of removeError
     
     containtsDigit : function(val){
         var i = 0;
            var flag = false;;
            while(i<val.length){
                if(!isNaN(val[i]*2)){
                     flag = true;
                    break;
                } 
                i++
            }
            return flag;
     }, //end of containtsDigit
     
     containtsUpperCaseChar: function(val){         
          var flag = false;    
         for(var i = 0; i<val.length; i++){
            var char = val[i];
             if( !isFinite(char) && char==char.toUpperCase()){
                 flag = true;
                 break;
             }   
         }         
         return flag;
     },  // end of containtsUpperCaseChar
     
     isValidLogin : function(str){
         pattern = /^[a-zA-Z0-9]+$/;
         var en_digit = pattern.test(str);
         var startWithChar = false;
         var startChar = str[0];
         if( en_digit && !isFinite(startChar) ) {
             startWithChar = true;
         }   
         return en_digit && startWithChar;
     },   //end of isValidation
     
     isValidPassword : function(val){
         return  (app.containtsDigit(val) && app.containtsUpperCaseChar(val));
     },
     
     isValidInitial : function(val){
         pattern =  /^[а-яА-Яa-zA-Z]+$/;
    return pattern.test(val);
    },
    
    isUserFind : function(login){
    	var isUserExists = true;
    	
    	   $.ajax({
               type: 'GET',
               url: '/registration/isUserExist/'+login,
               async: false,
               success: function(data){
            	   isUserExists = data;
               },
               error: function(data){
                   //    alert(data);
               }
           });

    return isUserExists;    
    },
         
    };  // end app

app.initialize();
  
}() );