(function (){

 var app = {
        
        initialize : function(){
            this.setUpListeners();    
        },      //end initialize

        setUpListeners : function(){
           var $form = $("#contactForm");
            $form.on('submit', app.submitForm);
            $form.on('keydown', 'input', app.removeError); 
            
            $("#cancelButtonCreate").click(app.closeForm);
            
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
               
                if(val.length===0 && index < 4){
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
                      //first Name
                       case 0 :   if( app.isValidInitial(val) && val.length<=15 &&  val.length>=3 ){
                                    var icon = document.getElementById("firstNameIcon");
                                    icon.className = "glyphicon glyphicon-ok success-icon";
                                  }else{   
                                    valid = false;
                                    formGroup.removeClass('has-success').addClass('has-error');
                                    var icon = document.getElementById("firstNameIcon");
                                    icon.className = "glyphicon glyphicon-remove error-icon";   
                                    input.tooltip({
                                           trigger: 'manual',
                                           placement : 'right',
                                            title: 'size of firstname must be beatween 3\n and 15,including only characters\n and no spaces'
                                         }).tooltip('show');
                                    }   break;
                       //last name  
                     case 1 :      if( app.isValidInitial(val) && val.length<=15 &&  val.length>=3 ){
                                    var icon = document.getElementById("lastNameIcon");
                                    icon.className = "glyphicon glyphicon-ok success-icon";
                                  }else{   
                                    valid = false;
                                    formGroup.removeClass('has-success').addClass('has-error');
                                    var icon = document.getElementById("lastNameIcon");
                                    icon.className = "glyphicon glyphicon-remove error-icon";
                                    input.tooltip({
                                           trigger: 'manual',
                                           placement : 'right',
                                             title: 'size of lastname must be beatween 3\n and 15,including only characters\n and no spaces'
                                         }).tooltip('show');
                                    }   break;
                        //middle name 
                     case 2 :      if( app.isValidInitial(val) && val.length<=15 &&  val.length>=3 ){
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
                                             title: 'size of middlename must be beatween\n 3 and 15,including only characters\n and no spaces'
                                         }).tooltip('show');
                                    }   break;      
                     //mobile phone 
                     case 3 :     if( app.isOperatorExists(val) && app.isCorrectNumber(val)){ 
                                        var icon = document.getElementById("mobilePhoneIcon");
                                        icon.className = "glyphicon glyphicon-ok success-icon";
                                  }else{
                                    valid = false;
                                    formGroup.removeClass('has-success').addClass('has-error');
                                    var icon = document.getElementById("mobilePhoneIcon");
                                    icon.className = "glyphicon glyphicon-remove error-icon";
                                    input.tooltip({
                                           trigger: 'manual',
                                           placement : 'right',
                                             title: 'this mobile operator does not exists, \nplease input a correct code\n of operator'
                                         }).tooltip('show');
                                    } break;
                     // home phone
                     case 4 :     if( app.isValidHomeNumber(val) && val.length<=15){
                         var icon = document.getElementById("mobilePhoneIcon");
                         formGroup.removeClass('has-error').addClass('has-success');

                     }else if(val.length==0){
                         var icon = document.getElementById("mobilePhoneIcon");
                         formGroup.removeClass('has-error').addClass('has-success');
                     }
                     else{
                         valid = false;
                         formGroup.removeClass('has-success').addClass('has-error');
                         input.tooltip({
                             trigger: 'manual',
                             placement : 'right',
                             title: 'phone number must contains\n only digits'
                         }).tooltip('show');
                     }   
               } //end of switch
               }//end of else
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

     isValidInitial : function(val){
         pattern =  /^[а-яА-Яa-zA-Z]+$/;
    return pattern.test(val);
},    
     
     isOperatorExists : function(val){
         
         var oper_number = +(val.substring(5, 8));
         
         var isOperatorExists = false;
         switch(oper_number){ 
             
             case 50 :  isOperatorExists = true; break;  
             case 63 :  isOperatorExists = true; break;
             case 67 :  isOperatorExists = true; break;
             case 68 :  isOperatorExists = true; break;
             case 73 :  isOperatorExists = true; break;
             case 93 :  isOperatorExists = true; break;                 
             case 95 :  isOperatorExists = true; break;
             case 96 :  isOperatorExists = true; break;
             case 97 :  isOperatorExists = true; break;
             case 98 :  isOperatorExists = true; break;
             case 99 :  isOperatorExists = true; break;  
         };
     return isOperatorExists;    
     },
       
     isCorrectNumber : function(val){
       var isCorrectNumber = true;
         
         if(isNaN( (val[val.length-1])*2) ) isCorrectNumber = false;
         return isCorrectNumber;
     },
     
     closeForm : function(){
         var $form = $("#contactForm");
         var inputs = $form.find('input');
           $.each(inputs, function(index, val){
             var input = $(val),
                   formGroup = input.parents('.form-group');
                input.val("");
                formGroup.removeClass("has-error").removeClass("has-success");
                input.tooltip('destroy');

              
           });
                    var firstNameIcon = document.getElementById("firstNameIcon");
                     firstNameIcon.className = "";
                    var lastNameIcon = document.getElementById("lastNameIcon");
                     lastNameIcon.className = "";
                    var middleNameIcon = document.getElementById("middleNameIcon");
                     middleNameIcon.className = "";
                    var mobilePhoneIcon = document.getElementById("mobilePhoneIcon");
                     mobilePhoneIcon.className = "";

         
          $("#dlDropDown").dropdown("toggle");
     
     },

     isValidHomeNumber : function(val){
         var isValid = false;
          if( val.empty){
              isValid = true;
          }else{
             for(var i=0; i<val.length; i++){
                 var indexItem = val[i];
                 if( isFinite(indexItem) ){
                     isValid=true;
                 }
                 else{
                     isValid = false;
                     return;
                 }
             }
          }
         return isValid;
     } 
    };  // end app

app.initialize();
  
}() );