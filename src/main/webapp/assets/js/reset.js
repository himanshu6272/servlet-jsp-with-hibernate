$(document).ready(function(){
    let flag = false;

    let pwd = function () {
        let password = $("#password").val();
        let passwordExp = /^(?=.*[0-9])(?=.*[!@#$%^&*])[a-zA-Z0-9!@#$%^&*]{8,16}$/;
        if (password == "" || password == undefined) {
          $("#passwordHelp").html("password is required!").addClass("text-danger");
          return false;
        } else if (passwordExp.test(password) == false) {
          $("#passwordHelp")
            .html(
              "password should contain atleast 8 characters(one uppercase, one lowercase, one digit and one special character"
            )
            .addClass("text-danger");
          return false;
        } else {
          $("#passwordHelp").empty();
          return true;
        }
      };
      let cnfPassword = function () {
        let password = $("#password").val();
        let cnfPwd = $("#cnf-password").val();
        if (cnfPwd == "" || cnfPwd == undefined) {
          $("#cnfpasswordHelp")
            .html("please re-enter password!")
            .addClass("text-danger");
          return false;
        } else if (cnfPwd != password) {
          $("#cnfpasswordHelp")
            .html("password does not matched!")
            .addClass("text-danger");
          return false;
        } else {
          $("#cnfpasswordHelp").empty();
          return true;
        }
      };

      $("#password").blur(pwd);
      $("#cnf-password").blur(cnfPassword);
      $("#submit-btn").click(pwd);
      $("#submit-btn").click(cnfPassword);

      $("#reset-form").on('submit', function (event) {
                             event.preventDefault();
                             form = new FormData(this);
                             console.log(form);
                             $.ajax({
                                 url: 'resetServlet',
                                 type: 'POST',
                                 data: form,
                                 success: function (data, textStatus, jqXHR) {
                                     console.log(data);
                                     if(data.trim() === 'reset'){
                                     $("#errorPopup").removeClass("bg-danger").addClass("bg-success");
                                     let message = "Password reset successfully";
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