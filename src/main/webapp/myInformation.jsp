<%@page import="net.model.user"%>
<%@page import="net.model.student"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="es">
    <head>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
        <meta name="description" content="">
        <meta name="author" content="Juan Carlos Hernández">
        <title>Exam Application System - My information</title>
        <link href="vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet">
        <link href="vendor/font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css">
        <link href="vendor/datatables/dataTables.bootstrap4.css" rel="stylesheet">
        <link href="css/sb-admin.css" rel="stylesheet">
    </head>

    <body class="fixed-nav sticky-footer bg-dark" id="page-top">
        <%
            String user = (String) session.getAttribute("userId");
            int userType = (int) session.getAttribute("userType");
            
            if (user != null) {
                String id, firstname, psurname, msurname, email, phone;
                if(userType == 4){
                    student modelstudent = (student) request.getAttribute("userinformation");
                    id = modelstudent.getId();
                    firstname = modelstudent.getFirstname();
                    psurname = modelstudent.getPsurname();
                    msurname = modelstudent.getMsurname();
                    email = modelstudent.getEmail();
                    phone = modelstudent.getPhone();
                }else{
                    user modeluser = (user) request.getAttribute("userinformation");
                    id = Integer.toString(modeluser.getId());
                    firstname = modeluser.getFirstname();
                    psurname = modeluser.getPsurname();
                    msurname = modeluser.getMsurname();
                    email = modeluser.getEmail();
                    phone = modeluser.getPhone();
                }
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
                    <li class="breadcrumb-item active">My information</li>
                </ol>

                <div class="card mb-3">
                    <div class="card-header">
                        <i class="fa fa-table"></i> My information basic</div>
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
                                <label for="firstname">First Name</label>
                                <input class="form-control" value="<%=firstname%>" id="firstname" name="firstname" type="text" placeholder="Firstname" required>
                            </div>
                            <div class="form-group">
                                <label for="psurname">Fathers Surname</label>
                                <input class="form-control" value="<%=psurname%>" id="psurname" name="psurname" type="text" placeholder="Father's surname" required>
                            </div>
                            <div class="form-group">
                                <label for="msurname">Mothers Surname</label>
                                <input class="form-control" value="<%=msurname%>" id="msurname" name="msurname" type="text" placeholder="Mother's surname">
                            </div>
                            <div class="form-group">
                                <label for="email">Email</label>
                                <input class="form-control" value="<%=email%>" id="email" name="email" type="email" placeholder="Email" required>
                            </div>
                            <div class="form-group">
                                <label for="phone">Phone</label>
                                <input class="form-control" value="<%=phone%>" id="phone" name="phone" type="tel" placeholder="phone">
                            </div>
                            
                            <input type="submit" class="btn btn-primary" name="btnupdate" value="Update">
                            <input type="hidden" name="action" value="updateInformation">
                            <input type="hidden" name="id" value="<%=id%>">
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
    </body>

</html>
