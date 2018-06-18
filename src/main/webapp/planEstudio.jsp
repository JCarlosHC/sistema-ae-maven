<%@page import="java.util.Date"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.ArrayList"%>
<%@page import="net.model.planEstudio"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
        <meta name="description" content="">
        <meta name="author" content="Juan Carlos Hernández">
        <title>Exam Application System</title>
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
                        <a href="#">Plan de estudio</a>
                    </li>
                    <li class="breadcrumb-item active">Lista</li>
                </ol>

                <div class="card mb-3">
                    <div class="card-header">
                        <i class="fa fa-table"></i> Listado plan de estudios</div>
                    <div class="card-body">
                        <div class="row">
                            <div class="col-md-4">
                                <div class="form-group">
                                    <a class="btn btn-success" data-toggle="modal" data-target="#newPlan">
                                        <i class="fa fa-user-plus"></i> New Plan
                                    </a>    
                                </div>
                            </div>
                            <div class="col-md-8">
                                <%                                    String msg = (String) request.getAttribute("msg");
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
                        <div class="table-responsive">
                            <table class="table table-bordered" id="dataTable" width="100%" cellspacing="0">
                                <thead>
                                    <tr>
                                        <th>ID</th>
                                        <th>Description</th>
                                        <th>Start Date</th>
                                        <th>End Date</th>
                                        <th>Options <span class="fa fa-wrench"></span></th>
                                    </tr>
                                </thead>
                                <tfoot>
                                    <tr>
                                        <th>ID</th>
                                        <th>Description</th>
                                        <th>Start Date</th>
                                        <th>End Date</th>
                                        <th>Options <span class="fa fa-wrench"></span></th>
                                    </tr>
                                </tfoot>
                                <tbody>
                                    <%
                                        ArrayList<planEstudio> lista = (ArrayList<planEstudio>) request.getAttribute("lista");

                                        for (int i = 0; i < lista.size(); i++) {
                                            planEstudio pe = lista.get(i);
                                            String star, end;
                                            SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");

                                            if (pe.getStartDate() == null) {
                                                star = "-";
                                            } else {
                                                star = format.format(pe.getStartDate());
                                            }
                                            if (pe.getEndDate() == null) {
                                                end = "-";
                                            } else {
                                                end = format.format(pe.getEndDate());
                                            }
                                    %>
                                    <tr>
                                        <td><%=pe.getId()%></td>
                                        <td><%=pe.getDescription()%></td>
                                        <td><%=star%></td>
                                        <td><%=end%></td>
                                        <td>
                                            <a data-toggle="modal" data-target="#editPlan" data-idplan="<%=pe.getId()%>" 
                                               data-description="<%=pe.getDescription()%>" data-startDate="<%=pe.getStartDate()%>" 
                                               data-endDate="<%=pe.getEndDate()%>" class="btn btn-warning">
                                                <span class="fa fa-edit"></span>
                                                Edit
                                            </a>			
                                            <a class="btn btn-danger" href="servletABCPlan?action=delete&idPlan=<%=pe.getId()%>">
                                                <span class="fa fa-trash"></span>
                                                Delete
                                            </a>		
                                        </td>
                                    </tr>
                                    <%
                                        }
                                    %> 
                                </tbody>
                            </table>
                        </div>
                    </div>
                    <div class="card-footer small text-muted">Updated yesterday at 11:59 PM</div>
                </div>
                <!-- /.container-fluid-->
                <!-- /.content-wrapper-->
            </div>

            <!-- Scroll to Top Button-->
            <a class="scroll-to-top rounded" href="#page-top">
                <i class="fa fa-angle-up"></i>
            </a>

            <!-- new modal -->
            <div class="modal fade" id="newPlan" tabindex="-1" role="dialog" aria-labellebdy="title" aria-hidden="true">
                <div class="modal-dialog" role="document">
                    <div class="modal-content">
                        <form action="servletABCPlan" method="post">
                            <div class="modal-header bg-light">
                                <h5 class="modal-title" id="title">New Plan</h5>
                                <button class="close" type="button" data-dismiss="modal" aria-label="Close">
                                    <span aria-hidden="true">×</span>
                                </button>                    
                            </div>
                            <div class="modal-body">
                                <div class="form-group row">
                                    <label for="description" class="col-sm-3 col-form-label">Description</label>
                                    <div class="col-sm-9">
                                        <input type="text" class="form-control" id="description" name="description" required>
                                    </div>
                                </div>

                                <div class="form-group row">
                                    <label for="cStartDate" class="col-sm-3 col-form-label">Start Date</label>
                                    <div class="col-sm-5">
                                        <input type="date" class="form-control" id="cStartDate" name="cStartDate" required>
                                    </div>
                                </div>

                                <div class="form-group row">
                                    <label for="cEndDate" class="col-sm-3 col-form-label">End Date</label>
                                    <div class="col-sm-5">
                                        <input type="date" class="form-control" id="cEndDate" name="cEndDate" required>
                                    </div>
                                </div>
                            </div>
                            <div class="modal-footer">
                                <button class="btn btn-secondary" type="button" data-dismiss="modal">Cancel</button>
                                <input type="submit" class="btn btn-primary" name="btnNewPlan" id="btnNewPlan" value="Save">
                                <input type="hidden" name="action" value="insert">
                            </div>
                        </form>
                    </div>
                </div>
            </div> 

            <!-- Edit modal -->
            <div class="modal fade" id="editPlan" tabindex="-1" role="dialog" aria-labellebdy="title" aria-hidden="true">
                <div class="modal-dialog" role="document">
                    <div class="modal-content">
                        <form action="servletABCPlan" method="post">
                            <div class="modal-header bg-light">
                                <h5 class="modal-title" id="title">Edit Plan</h5>
                                <button class="close" type="button" data-dismiss="modal" aria-label="Close">
                                    <span aria-hidden="true">×</span>
                                </button>                    
                            </div>
                            <div class="modal-body">
                                <div class="form-group row">
                                    <label for="description" class="col-sm-3 col-form-label">Description</label>
                                    <div class="col-sm-9">
                                        <input type="text" class="form-control" id="description" name="description" required>
                                    </div>
                                </div>
                                <div class="form-group row">
                                    <label for="cStartDate" class="col-sm-3 col-form-label">Start Date</label>
                                    <div class="col-sm-5">
                                        <input type="date" class="form-control" id="cStartDate" name="cStartDate" required>
                                    </div>
                                </div>
                                <div class="form-group row">
                                    <label for="cEndDate" class="col-sm-3 col-form-label">End Date</label>
                                    <div class="col-sm-5">
                                        <input type="date" class="form-control" id="cEndDate" name="cEndDate" required>
                                    </div>
                                </div>
                                <input type="hidden" name="idPlan" id="idPlan" value="">
                            </div>
                            <div class="modal-footer">
                                <button class="btn btn-secondary" type="button" data-dismiss="modal">Cancel</button>
                                <input type="submit" class="btn btn-primary" name="btnAuthor" value="Save changes">
                                <input type="hidden" name="action" value="update">
                            </div>
                        </form>
                    </div>
                </div>
            </div> 

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
            }else{
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
            <% } %>
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
            <!-- Custom scripts for this page-->
            <script src="js/sb-admin-datatables.min.js"></script>
            <script src="js/sb-admin-charts.min.js"></script>

            <script src="js/utils.js" type="text/javascript"></script>
    </body>

</html>
