<%@page import="java.util.List"%>
<%@page import="net.model.subjects"%>
<%@page import="net.dao.subjectDAO"%>
<%@page import="net.dao.ConnectionDB"%>
<%@page import="net.model.secuencia"%>
<%@page import="java.util.ArrayList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
        <meta name="description" content="">
        <meta name="author" content="Juan Carlos Hernández">
        <title>Exam Application System - Mis Secuencias</title>
        <link href="vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet">
        <link href="vendor/font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css">
        <link href="vendor/datatables/dataTables.bootstrap4.css" rel="stylesheet">
        <link href="css/sb-admin.css" rel="stylesheet">
    </head>

    <body class="fixed-nav sticky-footer bg-dark" id="page-top">
        <div id="app">
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
                            <a href="#">Secuencias</a>
                        </li>
                        <li class="breadcrumb-item active">Mis secuencias</li>
                    </ol>

                    <div class="card mb-3">
                        <div class="card-header">
                            <i class="fa fa-table"></i> Listado de secuencias</div>
                        <div class="card-body">
                            <div class="row">
                                <div class="form-group">
                                    <a class="btn btn-success" data-toggle="modal" data-target="#new">
                                        <i class="fa fa-user-plus"></i> New Secuencia
                                    </a>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-md-12">
                                    <%  String msg = (String) request.getAttribute("msg");
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
                            <%
                                ArrayList<secuencia> lista = (ArrayList<secuencia>) request.getAttribute("myList");

                                if (lista == null || lista.isEmpty()) {
                            %>
                            <div class="alert alert-info alert-dismissible">
                                <a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
                                <strong>Warning!</strong>No hay resultados
                            </div>
                            <%
                            } else {
                            %>
                                                     
                            <div class="table-responsive">
                                <table class="table table-bordered" id="dataTable" width="100%" cellspacing="0">
                                    <thead>
                                        <tr>
                                            <th>ID Secuencia</th>
                                            <th>Materia</th>
                                            <th>Estatus</th>
                                            <th>Descripcion</th>
                                            <th>Total Students</th>
                                            <th>Options <span class="fa fa-wrench"></span></th>
                                        </tr>
                                    </thead>
                                    <tfoot>
                                        <tr>
                                            <th>ID Secuencia</th>
                                            <th>Materia</th>
                                            <th>Estatus</th>
                                            <th>Descripcion</th>
                                            <th>Total Students</th>
                                            <th>Options <span class="fa fa-wrench"></span></th>
                                        </tr>
                                    </tfoot>
                                    <tbody>
                                        <%
                                            for (int i = 0; i < lista.size(); i++) {
                                                secuencia e = lista.get(i);
                                        %>
                                        <tr>
                                            <td><%=e.getId()%></td>
                                            <td><%=e.getIdsubject()%></td>
                                            <td><%=e.getIdstatus()%></td>
                                            <td><%=e.getDescripcion()%></td>
                                            <td><%=e.getTotalstudents()%></td>
                                            <td>
                                                <a href="servletSecuencia?action=getSecuencia&id=<%=e.getId()%>" class="btn btn-warning">
                                                    <span class="fa fa-edit"></span> Edit
                                                </a>
                                                <a class="btn btn-danger" href="servletSecuencia?action=delete&id=<%=e.getId()%>">
                                                    <span class="fa fa-trash"></span> Delete
                                                </a>		
                                            </td>
                                        </tr>
                                        <%
                                            }
                                        %> 
                                    </tbody>
                                </table>
                            </div>
                            <%}%>
                        </div>
                    </div>
                    <!-- /.container-fluid-->
                    <!-- /.content-wrapper-->
                </div>

                <!-- Scroll to Top Button-->
                <a class="scroll-to-top rounded" href="#page-top">
                    <i class="fa fa-angle-up"></i>
                </a>
                <!-- new modal -->
                <div class="modal fade" id="new" tabindex="-1" role="dialog" aria-labellebdy="title" aria-hidden="true">
                    <div class="modal-dialog modal-lg" role="document">
                        <div class="modal-content">
                            <form action="servletSecuencia" method="post">
                                <div class="modal-header bg-light">
                                    <h5 class="modal-title" id="title">New Secuencia</h5>
                                    <button class="close" type="button" data-dismiss="modal" aria-label="Close">
                                        <span aria-hidden="true">×</span>
                                    </button>                    
                                </div>
                                <div class="modal-body">
                                    <div class="form-group row">
                                        <label for="id" class="col-sm-3 col-form-label">ID secuencia</label>
                                        <div class="col-sm-9">
                                            <input type="text" class="form-control" id="id" name="id" required>
                                        </div>
                                    </div>
                                    <div class="form-group row">
                                        <label for="subject" class="col-sm-3 col-form-label">Materia</label>
                                        <div class="col-sm-9">
                                            <select name="subject" class="form-control">
                                                <%
                                                    ConnectionDB cn = new ConnectionDB();
                                                    subjectDAO sdao = new subjectDAO(cn);

                                                    List<subjects> listSubjects = sdao.getAll();

                                                    for (int i = 0; i < listSubjects.size(); i++) {
                                                        subjects c = listSubjects.get(i);
                                                %>
                                                <option value="<%=c.getId()%>"><%=c.getDescription()%></option>
                                                <%
                                                    }
                                                %>        
                                            </select>
                                        </div>
                                    </div>
                                    <div class="form-group row">
                                        <label for="description" class="col-sm-3 col-form-label">Description</label>
                                        <div class="col-sm-9">
                                            <input type="text" class="form-control" id="description" name="description" required>
                                        </div>
                                    </div>
                                </div>
                                <div class="modal-footer">
                                    <button class="btn btn-secondary" type="button" data-dismiss="modal">Cancel</button>
                                    <input type="submit" class="btn btn-primary" name="btnNew" id="btnNew" value="Save">
                                    <input type="hidden" name="action" value="insert">
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
            </div>
        </div>
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
            <script src="js/utils.js"></script>
    </body>

</html>
