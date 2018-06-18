<%@page import="java.util.ArrayList"%>
<%@page import="net.model.school"%>
<%@page import="net.dao.schoolDAO"%>
<%@page import="net.model.planEstudio"%>
<%@page import="net.dao.planEstudioDAO"%>
<%@page import="java.util.List"%>
<%@page import="net.model.career"%>
<%@page import="net.dao.careerDAO"%>
<%@page import="net.model.student"%>
<%@page import="net.dao.ConnectionDB"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
        <meta name="description" content="">
        <meta name="author" content="Juan Carlos Hernández">
        <title>Exam Application System - Students</title>
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
                        <a href="#">Students</a>
                    </li>
                    <li class="breadcrumb-item active">Lista</li>
                </ol>

                <div class="card mb-3">
                    <div class="card-header">
                        <i class="fa fa-table"></i> Listado de estudiantes</div>
                    <div class="card-body">
                        <div class="row">
                            <div class="form-group">
                                <a class="btn btn-success" data-toggle="modal" data-target="#new">
                                    <i class="fa fa-user-plus"></i> New Student
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
                        <div class="table-responsive">
                            <table class="table table-bordered" id="dataTable" width="100%" cellspacing="0">
                                <thead>
                                    <tr>
                                        <th>ID</th>
                                        <th>Full name</th>
                                        <th>Email</th>
                                        <th>Phone</th>
                                        <th>Options <span class="fa fa-wrench"></span></th>
                                    </tr>
                                </thead>
                                <tfoot>
                                    <tr>
                                        <th>ID</th>
                                        <th>Full name</th>
                                        <th>Email</th>
                                        <th>Phone</th>
                                        <th>Options <span class="fa fa-wrench"></span></th>
                                    </tr>
                                </tfoot>
                                <tbody>
                                    <%
                                        ArrayList<student> lista = (ArrayList<student>) request.getAttribute("lista");

                                        for (int i = 0; i < lista.size(); i++) {
                                            student s = lista.get(i);
                                    %>
                                    <tr>
                                        <td><%=s.getId()%></td>
                                        <td><%=s.getFirstname() + " " + s.getPsurname() + " " + s.getMsurname()%></td>
                                        <td><%=s.getEmail()%></td>
                                        <td><%=s.getPhone()%></td>
                                        <td>
                                            <a data-toggle="modal" data-target="#editStudent" class="btn btn-warning"
                                               data-id="<%=s.getId()%>" data-firstname="<%=s.getFirstname()%>" 
                                               data-pname="<%=s.getPsurname()%>" data-mname="<%=s.getMsurname()%>" 
                                               data-email="<%=s.getEmail()%>" data-phone="<%=s.getPhone()%>"
                                               data-idplan="<%=s.getId_planest()%>" data-idschool="<%=s.getId_school()%>"
                                               data-idcareer="<%=s.getId_career()%>" >
                                                <span class="fa fa-edit"></span>
                                                Edit
                                            </a>			
                                            <a class="btn btn-danger" href="servletStudent?action=delete&id=<%=s.getId()%>">
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
                        <form action="servletStudent" method="post">
                            <div class="modal-header bg-light">
                                <h5 class="modal-title" id="title">New Student</h5>
                                <button class="close" type="button" data-dismiss="modal" aria-label="Close">
                                    <span aria-hidden="true">×</span>
                                </button>                    
                            </div>
                            <div class="modal-body">
                                <div class="form-group">
                                    <div class="form-row">
                                        <div class="col-md-4">
                                            <label for="firstname">First name</label>
                                            <input type="text" class="form-control" id="firstname" name="firstname" required>
                                        </div>
                                        <div class="col-md-4">
                                            <label for="psurname">Father surname</label>
                                            <input type="text" class="form-control" id="psurname" name="psurname" required>
                                        </div>
                                        <div class="col-md-4">
                                            <label for="msurname">Mother surname</label>
                                            <input type="text" class="form-control" id="msurname" name="msurname">
                                        </div>
                                    </div>
                                </div>

                                <div class="form-group">
                                    <div class="form-row">
                                        <div class="col-md-4">
                                            <label for="id">Boleta</label>
                                            <input type="text" class="form-control" id="id" name="id" required>
                                        </div>
                                        <div class="col-md-4">
                                            <label for="email">Email</label>
                                            <input type="email" class="form-control" id="email" name="email">
                                        </div>
                                        <div class="col-md-4">
                                            <label for="phone">Phone</label>
                                            <input type="tel" class="form-control" id="phone" name="phone">
                                        </div>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <div class="form-row">
                                        <div class="col-md-4">
                                            <label for="planest">Plan de estudios</label>
                                            <select name="planest" class="form-control">
                                                <%
                                                    ConnectionDB cn = new ConnectionDB();
                                                    careerDAO cdao = new careerDAO(cn);
                                                    planEstudioDAO pdao = new planEstudioDAO(cn);
                                                    schoolDAO sdao = new schoolDAO(cn);
                                                    List<planEstudio> listPlan = pdao.getAll();
                                                    List<school> listSchool = sdao.getAll();
                                                    List<career> listCareer = cdao.getAll();

                                                    for (int i = 0; i < listPlan.size(); i++) {
                                                        planEstudio c = listPlan.get(i);
                                                %>
                                                <option value="<%=c.getId()%>"><%=c.getDescription()%></option>
                                                <%
                                                    }
                                                %>        
                                            </select>
                                        </div>
                                        <div class="col-md-4">
                                            <label for="escuela">Escuela</label>
                                            <select name="escuela" class="form-control">
                                                <%
                                                    for (int i = 0; i < listSchool.size(); i++) {
                                                        school c = listSchool.get(i);
                                                %>
                                                <option value="<%=c.getId()%>"><%=c.getDescription()%></option>
                                                <%
                                                    }
                                                %>        
                                            </select>
                                        </div>
                                        <div class="col-md-4">
                                            <label for="carrera">Carrera</label>
                                            <select name="carrera" class="form-control">
                                                <%
                                                    for (int i = 0; i < listCareer.size(); i++) {
                                                        career c = listCareer.get(i);
                                                %>
                                                <option value="<%=c.getId()%>"><%=c.getDescription()%></option>
                                                <%
                                                    }
                                                %>        
                                            </select>
                                        </div>
                                    </div>
                                </div>          
                            </div>
                            <div class="modal-footer">
                                <button class="btn btn-secondary" type="button" data-dismiss="modal">Cancel</button>
                                <input type="submit" class="btn btn-primary" name="btnNewPlan" id="btnNew" value="Save">
                                <input type="hidden" name="action" value="insert">
                            </div>
                        </form>
                    </div>
                </div>
            </div> 

            <!-- Edit modal -->
            <div class="modal fade" id="editStudent" tabindex="-1" role="dialog" aria-labellebdy="title" aria-hidden="true">
                <div class="modal-dialog modal-lg" role="document">
                    <div class="modal-content">
                        <form action="servletStudent" method="post">
                            <div class="modal-header bg-light">
                                <h5 class="modal-title" id="title">Edit Student</h5>
                                <button class="close" type="button" data-dismiss="modal" aria-label="Close">
                                    <span aria-hidden="true">×</span>
                                </button>                    
                            </div>
                            <div class="modal-body">
                                <div class="form-group">
                                    <div class="form-row">
                                        <div class="col-md-4">
                                            <label for="firstname">First name</label>
                                            <input type="text" class="form-control" id="firstname" name="firstname" required>
                                        </div>
                                        <div class="col-md-4">
                                            <label for="psurname">Father surname</label>
                                            <input type="text" class="form-control" id="psurname" name="psurname" required>
                                        </div>
                                        <div class="col-md-4">
                                            <label for="msurname">Mother surname</label>
                                            <input type="text" class="form-control" id="msurname" name="msurname">
                                        </div>
                                    </div>
                                </div>

                                <div class="form-group">
                                    <div class="form-row">
                                        <div class="col-md-4">
                                            <label for="id">Boleta</label>
                                            <input type="text" class="form-control" id="id" name="id" readonly>
                                        </div>
                                        <div class="col-md-4">
                                            <label for="email">Email</label>
                                            <input type="email" class="form-control" id="email" name="email">
                                        </div>
                                        <div class="col-md-4">
                                            <label for="phone">Phone</label>
                                            <input type="tel" class="form-control" id="phone" name="phone">
                                        </div>
                                        
                                    </div>
                                </div>
                                <div class="form-group">
                                    <div class="form-row">
                                        <div class="col-md-4">
                                            <label for="planest">Plan de estudios</label>
                                            <select name="planest" class="form-control">
                                                <%
                                                    for (int i = 0; i < listPlan.size(); i++) {
                                                        planEstudio c = listPlan.get(i);
                                                %>
                                                <option value="<%=c.getId()%>"><%=c.getDescription()%></option>
                                                <%
                                                    }
                                                %>        
                                            </select>
                                        </div>
                                        <div class="col-md-4">
                                            <label for="escuela">Escuela</label>
                                            <select name="escuela" class="form-control">
                                                <%
                                                    for (int i = 0; i < listSchool.size(); i++) {
                                                        school c = listSchool.get(i);
                                                %>
                                                <option value="<%=c.getId()%>"><%=c.getDescription()%></option>
                                                <%
                                                    }
                                                %>        
                                            </select>
                                        </div>
                                        <div class="col-md-4">
                                            <label for="carrera">Carrera</label>
                                            <select name="carrera" class="form-control">
                                                <%
                                                    for (int i = 0; i < listCareer.size(); i++) {
                                                        career c = listCareer.get(i);
                                                %>
                                                <option value="<%=c.getId()%>"><%=c.getDescription()%></option>
                                                <%
                                                    }
                                                %>        
                                            </select>
                                        </div>
                                    </div>
                                </div>  
                            </div>
                            <div class="modal-footer">
                                <button class="btn btn-secondary" type="button" data-dismiss="modal">Cancel</button>
                                <input type="submit" class="btn btn-primary" name="btnsave" id="btnsave" value="Save changes">
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
            <!-- Custom scripts for this page-->
            <script src="js/sb-admin-datatables.min.js"></script>
            <script src="js/sb-admin-charts.min.js"></script>
            <script src="js/utils.js"></script>
    </body>

</html>
