$(document).ready(function() {
    $("#login-btn").html("Logout").removeAttr("href").attr("href", "logoutServlet");
    $("#register-btn").addClass("d-none");
    $("#update-profile-btn").removeClass("d-none");
    $(".navbar-brand").addClass("d-none");

$(".remove-user-btn").click(function(){
    $(this).closest("tr").remove();
})


$("#myInput").on("keyup", function(){
     var input, filter, table, tr, td, i, txtValue;
          input = document.getElementById("myInput");
          filter = input.value.toUpperCase();
          table = document.getElementById("myTable");
          tr = table.getElementsByTagName("tr");

          for (i = 0; i < tr.length; i++) {
            td = tr[i].getElementsByTagName("td")[1];
            if (td) {
              txtValue = td.textContent || td.innerText;
              if (txtValue.toUpperCase().indexOf(filter) > -1) {
                tr[i].style.display = "";
              } else {
                tr[i].style.display = "none";
              }
            }
          }

})

})