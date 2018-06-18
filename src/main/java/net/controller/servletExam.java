package net.controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.dao.ConnectionDB;
import net.dao.catalogs;
import net.dao.examDAO;
import net.model.exam;
import net.model.examforview;
import net.model.typeExam;

/**
 * Servlet implementation class servletExam
 */
public class servletExam extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public servletExam() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getParameter("action");
        String id = request.getParameter("id");
        String idType = request.getParameter("idType");
        String msgresult = request.getParameter("msg");
        String success = request.getParameter("success");
        int user = (int) request.getSession().getAttribute("IdtableUser");
        
        String url = "", msg;
        RequestDispatcher rd;
        ConnectionDB cn = new ConnectionDB();
        examDAO exDao = new examDAO(cn);

        //Crear mensaje
        if (msgresult != null) {
            request.setAttribute("msg", msgresult);
            request.setAttribute("success", success);
        }
        switch (action) {
            case "toList":
                List<exam> lista = exDao.getAll();
                request.setAttribute("lista", lista);
                url = "exams.jsp";
                break;
            case "myExams":
                List<examforview> myList = exDao.getMyExams(user);
                request.setAttribute("myList", myList);
                url = "myExams.jsp";
                break;
            case "getExam":
                exam model;
                if(Integer.parseInt(id) == 0){
                    model = new exam(0,"","",0,null,null,0,0, 0,0, null);
                }else{
                    model = exDao.getExam(Integer.parseInt(id));
                }
                request.setAttribute("myExam", model);
                url = "exam.jsp";
                break;             
            case "delete":
                if (exDao.delete(Integer.parseInt(id))) {
                    msg = "Se eliminó correctamente el examen";
                    url = "servletExam?action=toList&success=true&msg=" + msg;
                } else {
                    msg = "Ocurrio un error, el examen no fue eliminado";
                    url = "servletExam?action=toList&success=false&msg=" + msg;
                }   
                break;
            case "deleteType":
                if (exDao.deleteType(Integer.parseInt(idType))) {
                    msg = "Se eliminó correctamente el tipo";
                    url = "servletExam?action=typeExams&success=true&msg=" + msg;
                } else {
                    msg = "Ocurrio un error, el tipo no fue eliminado";
                    url = "servletExam?action=typeExams&success=false&msg=" + msg;
                }   
                break;
            case "typeExams":
                catalogs catalog = new catalogs(cn);
                List<typeExam> listaType = catalog.getListTypeExams();
                request.setAttribute("listaType", listaType);
                url = "typeExams.jsp";
                break;
            default:
                break;
        }
        cn.disconnect();
        rd = request.getRequestDispatcher(url);
        rd.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");

        String action = request.getParameter("action");
        String id = request.getParameter("id");
        String title = request.getParameter("title");
        String descriptionE = request.getParameter("descriptionExa");
        String note = request.getParameter("note");
        String typeExam = request.getParameter("typeExam");
        int user = (int) request.getSession().getAttribute("IdtableUser");
        //Tipos de usuario
        String idType = request.getParameter("idType");
        String description = request.getParameter("description");
        typeExam type = new typeExam();
        
        String url = "", msg = "";
        ConnectionDB cn = new ConnectionDB();
        examDAO exDao = new examDAO(cn);
        exam model = new exam();

        switch (action) {
            case "saveInformationBasic": //The insert of basic information
                Date d = new Date(); 
                SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd");
                model.setId(Integer.parseInt(id));
                model.setTitle(title);
                model.setDescription(descriptionE);
                model.setQuestions(0);
                model.setCreatDate(formater.format(d));
                model.setDischargeDate(null);
                model.setNote(Float.parseFloat(note));
                model.setId_user(user);
                model.setId_status(1);
                model.setId_typeExa(Integer.parseInt(typeExam));
                model.setImage("images/no-image.jpg");
                
                if (exDao.insertOrUpdate(model)) {
                    msg = "Se guardo correctamente el examen";
                    url = "servletExam?action=getExam&id=" + model.getId() + "&success=true&msg=" + msg;
                } else {
                    msg = "Ocurrio un error, el examen no fue guardado";
                    url = "servletExam?action=getExam&id=" + model.getId() + "&success=false&msg=" + msg;
                }
                break;
            case "insertType":
                type.setDescription(description);
                
                if (exDao.insertType(type)) {
                    msg = "Se guardo correctamente el tipo";
                    url = "servletExam?action=typeExams&success=true&msg=" + msg;
                } else {
                    msg = "Ocurrio un error, el tipo no fue guardado";
                    url = "servletExam?action=typeExams&success=false&msg=" + msg;
                }
                break;
            case "updateType":
                type.setId(Integer.parseInt(idType));
                type.setDescription(description);
                
                if (exDao.updateType(type)) {
                    msg = "Se modificó correctamente el tipo";
                    url = "servletExam?action=typeExams&success=true&msg=" + msg;
                } else {
                    msg = "Ocurrio un error, el tipo no fue modificado";
                    url = "servletExam?action=typeExams&success=false&msg=" + msg;
                }
                break;
            default:
                break;
        }

        cn.disconnect();
        response.sendRedirect(url);
	}

}
