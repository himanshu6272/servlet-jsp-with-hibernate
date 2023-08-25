$(document).ready(function(){

    let flag = false;

    let emailAddress = function () {
        let emailExp = /^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/;
        let email = $("#email-address").val();
        if (email == "" || email == undefined) {
          $("#emailHelp").html("email is required!").addClass("text-danger");
          return false;
        } else if (emailExp.test(email) == false) {
          $("#emailHelp")
            .html("Invalid email, email must contain @ (example@xyz.com)!")
            .addClass("text-danger");
          return false;
        } else {
          $("#emailHelp").empty();
          return true;
        }
      };

    let securityAnswer = function () {
        let answer = $("#security-answer").val();
        if (answer == "" || answer == undefined) {
          $("#securityanswerHelp")
            .html("please enter the answer!")
            .addClass("text-danger");
          return false;
        } else {
          $("#securityanswerHelp").empty();
          return true;
        }
      };

    let securityQuestion = function(){
            let que = $("#security-que").val();
            if(que === "none" || que === undefined){
                $("#securityQuestionHelp").html("please select the security question!").addClass("text-danger");
                return false;
            }else{
                $("#securityQuestionHelp").empty();
                return true;
            }
      };

      $("#submit-btn").click(emailAddress);
      $("#email-address").blur(emailAddress);
      $("#submit-btn").click(securityQuestion);
      $("#submit-btn").click(securityAnswer);

      $("#forgot-form").on('submit', function (event) {
                             event.preventDefault();
                             form = new FormData(this);
                             console.log(form);
                             $.ajax({
                                 url: 'forgotServlet',
                                 type: 'POST',
                                 data: form,
                                 success: function (data, textStatus, jqXHR) {
                                     console.log(data);
                                     if(data.trim() === 'sent'){
                                     $("#errorPopup").removeClass("bg-danger").addClass("bg-success");
                                     let message = "Reset link is sent to the log";
                                     showErrorPopup(message);
                                     setTimeout(function () {
                                          window.location.href = "login.jsp";
                                      }, 3000);

                                     }else{
                                     showErrorPopup(data);
                                     }
                                 },
                                 error: function (jqXHR, textStatus, errorThrown) {
                                     console.log(jqXHR);
                                 },
                                 processData: false,
                                 contentType: false
                             });
                         });

                         // Function to show the error popup
                         function showErrorPopup(message) {
                             $("#errorPopup").html(message).show();
                             setTimeout(function () {
                                 $("#errorPopup").hide();
                             }, 3000);
                          }

})