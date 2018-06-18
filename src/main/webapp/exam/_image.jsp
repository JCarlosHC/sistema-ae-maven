<%@page import="net.model.exam"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    exam model = (exam) request.getAttribute("myExam");
    %>
<div class="row">
    <div class="col-sm-6">
        <div class="thumbnail">
            <img id="courseImage" src="<%=model.getImage()%>" alt="<%=model.getTitle()%>" />
        </div>
    </div>
    <div class="col-sm-6">
        <form action="servletImages" method="post" enctype="multipart/form-data">
            <input type="hidden" name="id" value="<%=model.getId()%>">
            <div class="form-group">
                <label>Image <span class="required">*</span></label>
                <input type="file" name="file" class="form-control-file">
                <span class="help-block">Only valid images are accepted in jpg, gif, png format and that does not exceed 900 x 473 pixels.</span>
            </div>
            <div class="text-right">
                <input type="submit" class="btn btn-primary" name="btnImage" id="btnImage" value="Save" data-endrequest="refreshImage">
                <input type="hidden" name="action" value="saveImage">
            </div>
        </form>
    </div>
</div>