<%@page import="net.model.exam"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    exam model = (exam) request.getAttribute("myExam");
    %>
<div style="min-height:300px;">
    <teacherquestion :id="<%=model.getId()%>"></teacherquestion>
</div>