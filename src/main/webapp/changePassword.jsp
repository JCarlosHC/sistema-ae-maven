<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
        <meta name="description" content="">
        <meta name="author" content="Juan Carlos Hernández">
        <title>Exam Application System - Change password</title>
        <link href="vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet">
        <link href="vendor/font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css">
        <link href="vendor/datatables/dataTables.bootstrap4.css" rel="stylesheet">
        <link href="css/sb-admin.css" rel="stylesheet">
    </head>

    <body class="fixed-nav sticky-footer bg-dark" id="page-top">
        <%
            String user = (String) session.getAttribute("userId");

            if (user != null) {

        %>
        <!-- Navigation-->
        <jsp:include page="MasterPagePanel/Header.jsp"/>
        <!-- Content page -->
        <div class="content-wrapper">
            <div class="container-fluid">
                <!-- Breadcrumbs-->
                <ol class="breadcrumb">
                    <li class="breadcrumb-item">
                        <a href="#">My profil</a>
                    </li>
                    <li class="breadcrumb-item active">Password</li>
                </ol>

                <div class="card mb-3">
                    <div class="card-header">
                        <i class="fa fa-table"></i> Change Password</div>
                    <div class="card-body">
                        <div class="form-group">
                            <div class="row">
                                <div class="col-md-8">
                                    <%                                        String msg = (String) request.getAttribute("msg");
                                        String success = (String) request.getAttribute("success");
                                        String classAlert, title, style;

                                        if (success == null) {
                                            classAlert = "alert alert-danger alert-dismissible";
                                            title = "Warning!";
                                            style = "display:none";
                                        } else if (success.equals("true")) {
                                            classAlert = "alert alert-success alert-dismissible";
                                            title = "Success!";
                                            style = "display:block";
                                        } else {
                                            classAlert = "alert alert-danger alert-dismissible";
                                            title = "Warning!";
                                            style = "display:block";

                                        }
                                    %>
                                    <div class="<%=classAlert%>" style="<%=style%>">
                                        <a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
                                        <strong><%=title%> </strong>${msg}
                                    </div>
                                </div>
                            </div>
                        </div>
                        <form action="servletInformation" method="post">
                            <div class="form-group">
                                <label for="passActual">Contraseña actual</label>
                                <input class="form-control" id="passActual" name="passActual" type="password" placeholder="Password" required>
                            </div>
                            <div class="form-group">
                                <label for="newPassword">Nueva contraseña</label>
                                <input class="form-control" id="newPassword" name="newPassword" type="password" placeholder="New Password" required>
                            </div>
                            <div class="form-group">
                                <label for="ConfirmnewPassword">Confirmar nueva contraseña</label>
                                <input class="form-control" id="ConfirmnewPassword" name="ConfirmnewPassword" type="password" placeholder="Confirm Password" required>
                            </div>
                            <div class="row">
                                <div class="col-sm-12">
                                    <span id="pwmatch" class="fa fa-times-circle"></span> Passwords Match
                                </div>
                            </div>
                            <div class="form-group" id="msgpassword"></div>
                            <input type="submit" class="btn btn-primary" name="btnupdate" value="Update">
                            <input type="hidden" name="action" value="updatePassword">
                        </form>          

                    </div>
                </div>
                <!-- /.container-fluid-->
                <!-- /.content-wrapper-->
            </div>

            <!-- Scroll to Top Button-->
            <a class="scroll-to-top rounded" href="#page-top">
                <i class="fa fa-angle-up"></i>
            </a>

            <!-- Logout Modal-->
            <div class="modal fade" id="exampleModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
                <div class="modal-dialog" role="document">
                    <div class="modal-content">
                        <div class="modal-header">
                            <h5 class="modal-title" id="exampleModalLabel">Ready to Leave?</h5>
                            <button class="close" type="button" data-dismiss="modal" aria-label="Close">
                                <span aria-hidden="true">×</span>
                            </button>
                        </div>
                        <div class="modal-body">Select "Logout" below if you are ready to end your current session.</div>
                        <div class="modal-footer">
                            <button class="btn btn-secondary" type="button" data-dismiss="modal">Cancel</button>
                            <a class="btn btn-primary" href="login.jsp">Logout</a>
                        </div>
                    </div>
                </div>
            </div>

            <jsp:include page="MasterPagePanel/Footer.jsp"/>
            <%
            } else {
            %>
            <div class="container">
                <div class="card card-login bg-light mx-auto mt-5">
                    <div class="card-header">Login failed</div>
                    <div class="card-body">
                        <div class="text-center">
                            <p class="card-text">Unable to login. Please try again.</p>
                            <a class="d-block small mt-3" href="register.jsp">Register an Account</a>
                            <a class="d-block small" href="login.jsp">Login page</a>
                        </div>
                    </div>
                </div>
            </div>
            <% }%>
            <!-- Bootstrap core JavaScript-->
            <script src="vendor/jquery/jquery.min.js"></script>
            <script src="vendor/bootstrap/js/bootstrap.bundle.min.js"></script>
            <!-- Core plugin JavaScript-->
            <script src="vendor/jquery-easing/jquery.easing.min.js"></script>
            <!-- Page level plugin JavaScript-->
            <script src="vendor/chart.js/Chart.min.js"></script>
            <script src="vendor/datatables/jquery.dataTables.js"></script>
            <script src="vendor/datatables/dataTables.bootstrap4.js"></script>
            <!-- Custom scripts for all pages-->
            <script src="js/sb-admin.min.js"></script>            
            <script>
                $(document).ready(function () {
                    $("#ConfirmnewPassword").keyup(function () {
                        if ($("#ConfirmnewPassword").val() == $("#newPassword").val()) {
                            $("#pwmatch").removeClass("fa fa-times-circle");
                            $("#pwmatch").addClass("fa fa-check-circle");
                        } else {
                            $("#pwmatch").removeClass("fa fa-check-circle");
                            $("#pwmatch").addClass("fa fa-times-circle");
                        }
                    });
                });

            </script>
    </body>

</html>
