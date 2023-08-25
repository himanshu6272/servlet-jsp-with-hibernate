$(document).ready(function(){
    $(".navbar-brand").addClass("d-none");
    $("#edit-user-btn").click(function(){
        $("input").removeAttr("disabled");

        $("#email-address").attr("disabled", "disabled");
        $(".remove-address-btn").removeAttr("disabled");
        $("#submit-btn").removeAttr("disabled");
        $("#add-address-btn").removeClass("d-none");
   })

    $("#login-btn").html("Logout").removeAttr("href").attr("href", "logoutServlet");
    $("#register-btn").remove();

    $("#add-address-btn").click(function(){
        $("#address").removeClass("d-none");
    })

})