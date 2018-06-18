<%@page import="java.util.List"%>
<%@page import="net.model.secuencia"%>
<%@page import="net.dao.secuenciaDAO"%>
<%@page import="net.model.exam"%>
<%@page import="net.model.subjects"%>
<%@page import="net.model.typeExam"%>
<%@page import="net.dao.catalogs"%>
<%@page import="java.util.ArrayList"%>
<%@page import="net.dao.ConnectionDB"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<h3>Basic information</h3>

<form action="servletExam" method="post">
    <%
        exam model = (exam) request.getAttribute("myExam");
    %>
    <input type="hidden" name="id" id="id" value="<%=model.getId()%>">
    <div class="form-group row">
        <label for="title" class="col-sm-3 col-form-label">Title</label>
        <div class="col-sm-9">
            <input type="text" class="form-control" id="title" name="title" value="<%=model.getTitle()%>" required>
        </div>
    </div>
    <div class="form-group row">
        <label for="descriptionExa" class="col-sm-3 col-form-label">Description</label>
        <div class="col-sm-9">
            <input type="text" class="form-control" id="descriptionExa" name="descriptionExa" value="<%=model.getDescription()%>" required>
        </div>
    </div>
    <div class="form-group row">
        <label for="note" class="col-sm-3 col-form-label">Maximum note</label>
        <div class="col-sm-9">
            <input type="text" class="form-control" id="note" name="note" value="<%=model.getNote()%>" required>
        </div>
    </div>
    <div class="form-group row">
        <label for="typeExam" class="col-sm-3 col-form-label">Type exam</label>
        <div class="col-sm-9">
            <select name="typeExam" class="form-control">
                <%
                    ConnectionDB cn = new ConnectionDB();
                    catalogs catalog = new catalogs(cn);
                    ArrayList<typeExam> listTypeExam = catalog.getListTypeExams();

                    for (int i = 0; i < listTypeExam.size(); i++) {
                        typeExam c = listTypeExam.get(i);
                        if (model.getId_typeExa() == c.getId()) {
                %>
                <option value="<%=c.getId()%>" selected="true"><%=c.getDescription()%></option>
                <%
                } else {
                %>
                <option value="<%=c.getId()%>"><%=c.getDescription()%></option>
                <%
                        }
                    }
                %>        
            </select>
        </div>
    </div>
    <div class="form-group row">
        <label for="selectsecuencia" class="col-sm-3 col-form-label">Secuencias</label>
        <div class="col-sm-9">
            <div class="input-group">
                <select name="selectsecuencia" id="selectsecuencia" class="form-control">
                    <%
                        secuenciaDAO secdao = new secuenciaDAO(cn);
                        int id = (int) session.getAttribute("IdtableUser");
                        List<secuencia> listsecuencias = secdao.getMisSecuencias(id);

                        for (int i = 0; i < listsecuencias.size(); i++) {
                            secuencia c = listsecuencias.get(i);
                    %>
                    <option value="<%=c.getId()%>"><%=c.getId()%></option>
                    <%
                        }
                        cn.disconnect();
                    %>        
                </select>
                <span class="input-group-btn">
                    <button id="btnloadSecuencias" class="btn btn-primary" type="button" visible="false" hidden="hidden">
                    </button>
                    <button id="btnAddSecuencia" class="btn btn-primary" type="button" title="Insert">
                        <i class="fa fa-plus"></i>
                    </button>
                </span>
            </div>

        </div>
    </div>
    <div id="secuenciasperexam"></div>
    <div class="row">
        <div class="col-md-10" id="containermsg"></div>
        <div class="col-md-2">
            <input type="submit" class="btn btn-primary" name="btnNew" id="btnNew" value="Save changes">
        </div>
        <input type="hidden" name="action" value="saveInformationBasic">
    </div>
</form>
