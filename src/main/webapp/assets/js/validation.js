$(document).ready(function () {
  let flag = false;

  let firstName = function () {
    let fname = $("#fname").val();
    let nameExp = /^[a-zA-Z]{2,8}$/;
    if (fname == "" || fname == undefined) {
      $("#fnameHelp").html("first name is required!").addClass("text-danger");
      return false;
    } else if (nameExp.test(fname) == false) {
      $("#fnameHelp")
        .html(
          "name should have atleat two character and does not contain any digit"
        )
        .addClass("text-danger");
      return false;
    } else {
      $("#fnameHelp").empty();
      return true;
    }
  };

  let lastName = function () {
    let lname = $("#lname").val();
    let nameExp = /^[a-zA-Z]{2,8}$/;
    if (lname == "" || lname == undefined) {
      $("#lnameHelp").html("last name is required!").addClass("text-danger");
      return false;
    } else if (nameExp.test(lname) == false) {
      $("#lnameHelp")
        .html(
          "name should have atleat two character and does not contain any digit"
        )
        .addClass("text-danger");
      return false;
    } else {
      $("#lnameHelp").empty();
      return true;
    }
  };

  let mobileNumber = function () {
    let mobNum = $("#mobile-number").val();
    let mobExp = /^[0-9]{10,11}$/;
    if (mobNum == "" || mobNum == undefined) {
      $("#mobileHelp")
        .html("mobile number is required!")
        .addClass("text-danger");
      return false;
    } else if (mobExp.test(mobNum) == false) {
      $("#mobileHelp")
        .html("enter valid mobile number!")
        .addClass("text-danger");
      return false;
    } else {
      $("#mobileHelp").empty();
      return true;
    }
  };

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

  let dateOfBirth = function () {
    let dob = $("#dob").val();
    let currentDate = new Date().toISOString().split("T")[0];
    if (dob == "" || dob == undefined) {
      $("#dateHelp").html("enter date of birth!").addClass("text-danger");
      return false;
    } else if (dob > currentDate) {
      $("#dateHelp").html("please enter valid date!").addClass("text-danger");
      return false;
    } else {
      $("#dateHelp").empty();
      return true;
    }
  };

  let securityQue = function () {
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

  let address = function(){
    let length = $("#addresses").children("div").length;
    if(length == 0){
        $("#addressHelp")
                .html("please add atleast one address!")
                .addClass("text-danger");
              return false;
        }
        else {
            $("#addressHelp").empty();
            return true;
        }

  }

  let profilePhoto = function(){
    let photo = $("#profile-photo").val();
    if(photo === "" || photo === undefined){
        $("#profilephotoHelp")
                .html("please upload the profile photo!")
                .addClass("text-danger");
              return false;
        }
        else {
            $("#profilephotoHelp").empty();
            return true;
        }

  }
  let gender = function () {
    let selectedGender = $(".gender:checked").val();

    if (selectedGender === undefined) {
      $("#genderHelp").html("please select the gender").addClass("text-danger");
      return false;
    } else {
      $("#genderHelp").empty();
      return true;
    }
  };

  let role = function () {
    let selectedRole = $(".role:checked").val();
    if (selectedRole === undefined) {
      $("#roleHelp").html("please select the role").addClass("text-danger");
      return false;
    } else {
      $("#roleHelp").empty();
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

  $(".gender").click(function () {
      $("#genderHelp").empty();
  });

 $(".role").click(function () {
      $("#roleHelp").empty();
  });

  $("#dob").datepicker({
    dateFormat: "yy-mm-dd",
    maxDate: 0,
    changeMonth: true,
    changeYear: true,
    yearRange: "-100:+0"
  });

  $("#calendar-icon").click(function () {
    $("#dob").datepicker("show");
  });
  $("#dob, #calendar-icon").click(function () {
    $("#dateHelp").empty();
  });

    $("#registration-form").on('submit', function (event) {
                       event.preventDefault();
                       form = new FormData(this);
                       console.log(form);
                       $.ajax({
                           url: 'registerServlet',
                           type: 'POST',
                           data: form,
                           success: function (data, textStatus, jqXHR) {
                                console.log(data);
                               if(data.trim() === 'done'){
                               $("#errorPopup").removeClass("bg-danger").addClass("bg-success");
                               let message = "Registered Successfully";
                               showErrorPopup(message);
                               setTimeout(function () {
                                    window.location.href = "login.jsp";
                                }, 3000);

                               }else if(data.trim() === 'updated'){
                               $("#errorPopup").removeClass("bg-danger").addClass("bg-success");
                               let message = "User Updated Successfully";
                               showErrorPopup(message);
                               setTimeout(function () {
                                    window.location.href = "view.jsp";
                                }, 3000);

                               }else if(data.trim() === 'exist'){
                               let message = "User already exist with this email use different email !!!!";
                               showErrorPopup(message);
                               }
                               else{
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

    $("#profile-photo").on('change', function(){
        let inputFile = $(this);
        let files = inputFile[0].files;
        let fileName = files[0].name;
        let extension = fileName.substr(fileName.lastIndexOf("."));
        let allowedExtensionsRegx = /(\.jpg|\.jpeg|\.png|\.gif)$/i;
        let isAllowed = allowedExtensionsRegx.test(extension);
        if(isAllowed){
        $("#profilephotoHelp").empty();
        return true;
        }else{
          $("#profilephotoHelp").html("please upload valid file").addClass("text-danger");
          return false;
        }
    })

  $("#fname").blur(firstName);
  $("#lname").blur(lastName);
  $("#mobile-number").blur(mobileNumber);
  $("#email-address").blur(emailAddress);
  $("#dob").blur(dateOfBirth);
  $("#security-que").blur(securityQue);
  $("#password").blur(pwd);
  $("#cnf-password").blur(cnfPassword);
  $("#submit-btn").click(firstName);
  $("#submit-btn").click(lastName);
  $("#submit-btn").click(mobileNumber);
  $("#submit-btn").click(emailAddress);
  $("#submit-btn").click(dateOfBirth);
  $("#submit-btn").click(address);
  $("#submit-btn").click(securityQue);
  $("#submit-btn").click(pwd);
  $("#submit-btn").click(gender);
  $("#submit-btn").click(role);
  $("#submit-btn").click(cnfPassword);
  $("#submit-btn").click(profilePhoto);
  $("#submit-btn").click(securityQuestion);

$("#add-address-btn").click(function(){
      $(this).addClass("d-none");
      $("#address").removeClass("d-none");
      $("#addressHelp").empty();

    })

    $(".update-user-btn").click(function(){
        $("#dashboard-btn").removeClass("d-none");
    })

    $("#cancel-address-btn").click(function(){
      $("#address").addClass("d-none");
      $("#add-address-btn").removeClass("d-none");
    })

        var id = 1;
        $("#save-address-btn").click(function(){
            let street = $("#inputStreet").val();
            let city = $("#inputCity").val();
            let state = $("#inputState").val();
            let zip = $("#inputZip").val();
            let country = $("#inputCountry").val();
            let addElement = '<div class="address border border-black p-2 m-3"><div class="d-flex mb-2"><input hidden name="addressId"><div class="d-inline mr-2"><label>Street</label><br><input class="street" name="street" value="'+street+
            '"><br></div><div class="d-inline mr-2"><label>City</label><br><input class="city" name="city" value="'+city+
            '"><br></div><div class="d-inline mr-2"><label>State</label><br><input class="state" name="state" value="'+state+
            '"><br></div><div class="d-inline mr-2"><label>Zip</label><br><input class="zip" name="zip" value="'+zip+
            '"><br></div><div class="d-inline mr-2"><label>Country</label><br><input class="country" name="country" value="'+country+
            '"><br></div></div><button type="button" class="btn btn-primary d-block remove-address-btn mr-1">Remove</button><small class="form-text addressesHelp"></small></div>';
            $("#addresses").append(addElement);
            $("#inputStreet").val("");
            $("#inputCity").val("");
            $("#inputState").val("");
            $("#inputZip").val("");
            $("#inputCountry").val("");

            id = id + 1;
        })

$(document).on("click", ".remove-address-btn", function() {
    $(this).closest(".address").remove();
});


});


