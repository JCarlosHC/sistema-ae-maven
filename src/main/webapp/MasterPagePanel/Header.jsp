<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!-- Navigation-->
<nav class="navbar navbar-expand-lg navbar-dark bg-dark fixed-top" id="mainNav">
    <%
        String user = (String) session.getAttribute("userId");
        int userType = (int) session.getAttribute("userType");

        if (user != null) {

    %>
    <!--<a class="navbar-brand" href="index.html">Start Bootstrap</a>-->
    <div class="navbar-brand">
        <a href="index.jsp">
            <img src="images/ipn-logo2.png" alt="Logo IPN" style="width:12%; height:12%">
        </a>
        <span style="font-size:0.7em">Instituto Politécnico Nacional</span>
    </div>
    <button class="navbar-toggler navbar-toggler-right" type="button" data-toggle="collapse" data-target="#navbarResponsive" aria-controls="navbarResponsive" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
    </button>
    <div class="collapse navbar-collapse" id="navbarResponsive">
        <ul class="navbar-nav navbar-sidenav" id="exampleAccordion">
            <li class="nav-item" data-toggle="tooltip" data-placement="right" title="Inicio">
                <a class="nav-link" href="index.jsp">
                    <i class="fa fa-home"></i>
                    <span class="nav-link-text">Inicio</span>
                </a>
            </li>

            <li class="nav-item" data-toggle="tooltip" data-placement="right" title="Mi perfil">
                <a class="nav-link nav-link-collapse collapsed" data-toggle="collapse" href="#collapsePerfil" data-parent="#exampleAccordion">
                    <i class="fa fa-user"></i>
                    <span class="nav-link-text">Mi perfil</span>
                </a>
                <ul class="sidenav-second-level collapse" id="collapsePerfil">
                    <li>
                        <a href="servletInformation?action=getUser">Mi informacion</a>
                    </li>
                    <li>
                        <a href="changePassword.jsp">Cambiar contraseña</a>
                    </li>
                </ul>
            </li>
            <%                if (userType == 4) {
            %>
            <li class="nav-item" data-toggle="tooltip" data-placement="right" title="Mi perfil">
                <a class="nav-link nav-link-collapse collapsed" data-toggle="collapse" href="#collapseExams" data-parent="#exampleAccordion">
                    <i class="fa fa-folder-open"></i>
                    <span class="nav-link-text">Examenes</span>
                </a>
                <ul class="sidenav-second-level collapse" id="collapseExams">
                    <li>
                        <a href="examsforme.jsp">Examenes asignados</a>
                    </li>
                    <li>
                        <a href="servletExamUser?action=getExamsResolved">Examenes resueltos</a>
                    </li>
                </ul>
            </li>
            <%
                }
                if (userType == 1) {
            %>
            <li class="nav-item" data-toggle="tooltip" data-placement="right" title="Planes de estudio">
                <a class="nav-link nav-link-collapse collapsed" data-toggle="collapse" href="#collapseComponents" data-parent="#exampleAccordion">
                    <i class="fa fa-university"></i>
                    <span class="nav-link-text">Planes de estudio</span>
                </a>
                <ul class="sidenav-second-level collapse" id="collapseComponents">
                    <li>
                        <a href="servletABCPlan?action=toList">Plan de estudio</a>
                    </li>
                    <li>
                        <a href="servletABCSchool?action=toList">Escuelas</a>
                    </li>
                    <li>
                        <a href="servletABCCareer?action=toList">Carreras</a>
                    </li>
                    <li>
                        <a href="servletABCSubjects?action=toList">Materias</a>
                    </li>
                </ul>
            </li>
            <li class="nav-item" data-toggle="tooltip" data-placement="right" title="Usuarios">
                <a class="nav-link nav-link-collapse collapsed" data-toggle="collapse" href="#collapseExamplePages" data-parent="#exampleAccordion">
                    <i class="fa fa-users"></i>
                    <span class="nav-link-text">Usuarios</span>
                </a>
                <ul class="sidenav-second-level collapse" id="collapseExamplePages">
                    <li>
                        <a href="servletABCUsers?action=typeusers">Tipo de usuarios</a>
                    </li>
                    <li>
                        <a href="servletABCUsers?action=toList">Listar usuarios</a>
                    </li>
                </ul>
            </li>
            <% }
                if (userType == 2 || userType == 3) {
            %>
            <li class="nav-item" data-toggle="tooltip" data-placement="right" title="Examenes">
                <a class="nav-link nav-link-collapse collapsed" data-toggle="collapse" href="#collapseExamPages" data-parent="#exampleAccordion">
                    <i class="fa fa-folder-open"></i>
                    <span class="nav-link-text">Examenes</span>
                </a>
                <ul class="sidenav-second-level collapse" id="collapseExamPages">
                    <li>
                        <a href="servletExam?action=typeExams">Tipo de examenes</a>
                    </li>
                    <li>
                        <a href="servletExam?action=toList">Listar examenes</a>
                    </li>
                    <li>
                        <a href="servletExam?action=myExams">Mis examenes</a>
                    </li>
                </ul>
            </li>
            <li class="nav-item" data-toggle="tooltip" data-placement="right" title="Alumnos">
                <a class="nav-link nav-link-collapse collapsed" data-toggle="collapse" href="#collapseMulti" data-parent="#exampleAccordion">
                    <i class="fa fa-graduation-cap"></i>
                    <span class="nav-link-text">Alumnos</span>
                </a>
                <ul class="sidenav-second-level collapse" id="collapseMulti">
                    <li>
                        <a href="servletStudent?action=toList">Lista alumnos</a>
                    </li>
                    <li>
                        <a href="servletSecuencia?action=toList">Listar secuencias</a>
                    </li>
                    <li>
                        <a href="servletSecuencia?action=mySecuencias">Mis secuencias</a>
                    </li>
                </ul>
            </li>
            <%
                }
                if (userType == 2) {
            %>
            <li class="nav-item" data-toggle="tooltip" data-placement="right" title="Secretarias">
                <a class="nav-link nav-link-collapse collapsed" data-toggle="collapse" href="#collapseSecretary" data-parent="#exampleAccordion">
                    <i class="fa fa-graduation-cap"></i>
                    <span class="nav-link-text">Secretarias</span>
                </a>
                <ul class="sidenav-second-level collapse" id="collapseSecretary">
                    <li>
                        <a href="servletSecretary?action=toList">Mis secretarias</a>
                    </li>
                </ul>
            </li>
            <% } %>
        </ul>
        <ul class="navbar-nav sidenav-toggler">
            <li class="nav-item">
                <a class="nav-link text-center" id="sidenavToggler">
                    <i class="fa fa-fw fa-angle-left"></i>
                </a>
            </li>
        </ul>
        <ul class="navbar-nav ml-auto">
            <li class="nav-item">
                <a class="nav-link" href="#">
                    <i class="fa fa-user"></i>${sessionScope.userId} /</a>
            </li>
            <li class="nav-item">
                <a class="nav-link" data-toggle="modal" data-target="#exampleModal">
                    <i class="fa fa-fw fa-sign-out"></i>Logout</a>
            </li>
        </ul>
    </div>
    <% }%>
</nav>