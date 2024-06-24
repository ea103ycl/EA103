<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>Insert title here</title>
</head>

<body id="page-top">

<!-- 	<!-- Page Wrapper -->
<!-- 	<div id="wrapper"> -->
<!-- 		<div id="content-wrapper" class="d-flex flex-column"> -->

			<footer class="sticky-footer bg-white">
				<div class="container my-auto">
					<div class="copyright text-center my-auto">
						<span>Copyright &copy; ArtsBlock 2020</span>
					</div>
				</div>
			</footer>
			
<!-- 		</div> -->
<!-- 	</div> -->
	
	     <script>	     
// 	  $("#sidebarToggle, #sidebarToggleTop").on('click', function(e) {
// 		    $("body").toggleClass("sidebar-toggled");
// 		    $(".sidebar").toggleClass("toggled");
// 		    if ($(".sidebar").hasClass("toggled")) {
// 		      $('.sidebar .collapse').collapse('hide');
// 		    };
// 		  });
		  
	  $("#sidebarToggleTop").on('click', function(e) {
		    $("body").toggleClass("sidebar-toggled");
		    $(".sidebar").toggleClass("toggled");
		    if ($(".sidebar").hasClass("toggled")) {
		      $('.sidebar .collapse').collapse('hide');
		    };
		  });
	  
	  $("#sidebarToggle").on('click', function(e) {
		    $("body").toggleClass("sidebar-toggled");
// 		    $(".sidebar").toggleClass("toggled");
// 		    if ($(".sidebar").hasClass("toggled")) {
// 		      $('.sidebar .collapse').collapse('hide');
// 		    };
		  });
	</script>

</body>
</html>