<%@page import="net.model.answersPerQuestion"%>
<%@page import="net.model.questionPerExam"%>
<%@page import="java.util.List"%>
<%@page import="net.model.exam"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="es">
    <head>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
        <meta name="description" content="">
        <meta name="author" content="Juan Carlos Hernández">
        <title>Exam Application System - Examen</title>
        <link href="vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet">
        <link href="vendor/font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css">
        <link href="vendor/datatables/dataTables.bootstrap4.css" rel="stylesheet">
        <link href="css/sb-admin.css" rel="stylesheet">
        <link href="css/style.css" rel="stylesheet">
    </head>

    <body class="fixed-nav sticky-footer bg-dark" id="page-top">
        <%
            String user = (String) session.getAttribute("userId");

            if (user != null) {
                exam model = (exam) request.getAttribute("myExam");
        %>
        <!-- Navigation-->
        <jsp:include page="/MasterPagePanel/Header.jsp"/>
        <!-- Content page -->
        <div class="content-wrapper">
            <div class="container-fluid">
                <!-- Breadcrumbs-->
                <ol class="breadcrumb">
                    <li class="breadcrumb-item">
                        <a href="#">Exam</a>
                    </li>
                    <li class="breadcrumb-item active"><%=model.getTitle()%></li>
                </ol>
                <div class="card mb-3">
                    <div class="card-header">
                        <i class="fa fa-table"></i> <%=model.getTitle()%></div>
                    <div class="card-body">
                        <input type="hidden" name="idExam" id="idExam" value="<%=model.getId()%>">
                        <form id="frmExam" name="frmExam" method="post">
                            <p>Descripción: <%=model.getDescription()%></p>
                            <%
                                List<questionPerExam> questions = model.getQuestionsList();
                                for (int i = 0; i < questions.size(); i++) {
                                    questionPerExam q = questions.get(i);
                                    List<answersPerQuestion> answers = q.getAnswer();
                            %>
                            <div class="form-group">
                                <label class="font-weight-bold"><%=i + 1%>. <%=q.getQuestion()%></label>
                                <%
                                    for (int j = 0; j < answers.size(); j++) {
                                        answersPerQuestion a = answers.get(j);

                                %>
                                <div class="form-check">
                                    <input class="form-check-input" type="radio" name="<%=q.getId()%>" id="<%=q.getId()%>" value="<%=a.getId()%>" required>
                                    <label class="form-check-label" for="<%=q.getId()%>">
                                        <%=a.getAnswer()%>
                                    </label>
                                </div>
                                <%
                                    }
                                %>
                            </div>
                            <%
                                }
                            %>
                            <input type="submit" class="btn btn-primary" name="btnEnviarExamen" id="btnEnviarExamen" value="Enviar examen">
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
            <div class="modal fade" id="resultExam" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" data-backdrop="static" data-keyboard="false">
                <div class="modal-dialog" role="document">
                    <div class="modal-content">
                        <div class="modal-header">
                            <h3>Resultados del examen</h3>
                        </div>
                        <div class="modal-body">
                            <div class="form-group row">
                                <label class="col-sm-6 col-form-label"><strong>Alumno</strong></label>
                                <div class="col-sm-6" id="alumno" name="alumno"></div>
                            </div>
                            <div class="form-group row">
                                <label class="col-sm-6 col-form-label"><strong>Num. Preguntas total</strong></label>
                                <div class="col-sm-6" id="ptotal" name="ptotal"></div>
                            </div>
                            <div class="form-group row">
                                <label class="col-sm-6 col-form-label"><strong>Num. Preguntas Correctas</strong></label>
                                <div class="col-sm-6" id="pcorrectas" name="pcorrectas"></div>
                            </div>
                            <div class="form-group row">
                                <label class="col-sm-6 col-form-label"><strong>Calificacion final</strong></label>
                                <div class="col-sm-6" id="calfinal" name="calfinal"></div>
                            </div>
                        </div>
                        <div class="modal-footer">
                            <a class="btn btn-primary" href="index.jsp">OK</a>
                        </div>
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

            <jsp:include page="/MasterPagePanel/Footer.jsp"/>
            <%            } else {
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
        </div>
        <% }%>
        <!-- Bootstrap core JavaScript-->
        <script src="vendor/jquery/jquery.min.js"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js" integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q" crossorigin="anonymous"></script>
        <script src="vendor/bootstrap/js/bootstrap.bundle.min.js"></script>
        <script src="vendor/bootstrap/js/bootstrap.min.js"></script>
        <script src="js/jquery.validate.min.js" type="text/javascript"></script>
        <!-- Core plugin JavaScript-->
        <script src="vendor/jquery-easing/jquery.easing.min.js"></script>
        <!-- Page level plugin JavaScript-->
        <script src="vendor/chart.js/Chart.min.js"></script>
        <script src="vendor/datatables/jquery.dataTables.js"></script>
        <script src="vendor/datatables/dataTables.bootstrap4.js"></script>
        <!-- Custom scripts for all pages-->
        <script src="js/sb-admin.min.js"></script>
        <script type="text/javascript">
            $(document).ready(function () {
                $("#btnEnviarExamen").click(function (e) {
                    if ($("#frmExam").valid()) {
                        e.preventDefault();
                        var idexam = $("#idExam").val();
                        var respuestas = $("#frmExam").serializeArray();
                        var questions = [];
                        $.each(respuestas, function (index, element) {
                            var data = {};
                            data.idpregunta = element.name;
                            data.idrespuesta = element.value;
                            questions.push(data);
                        });
                        $.ajax({
                            type: 'Post',
                            url: 'servletExamUser',
                            data: {id: idexam, respuestasuser: JSON.stringify(questions), action: "aplicarExamen"},
                            success: function (result) {
                                $('#alumno').append(result.idStudent);
                                $('#ptotal').append(result.numQuestions);
                                $('#pcorrectas').append(result.numQuestionsOK);
                                $('#calfinal').append(result.note + '/' + result.noteExam);
                                $('#resultExam').modal('toggle');
                                
                            },
                            error: function (jqxhr, status, exception) {
                                alert('Exception:', exception);
                            }
                        });

                    } else {
                        alert("Responda todas las preguntas");
                    }
                });

            });
        </script>
    </body>

</html>
