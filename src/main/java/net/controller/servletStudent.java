package net.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.dao.ConnectionDB;
import net.dao.studentDAO;
import net.model.student;

/**
 * Servlet implementation class servletStudent
 */
public class servletStudent extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public servletStudent() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		 String action = request.getParameter("action");
	        String id = request.getParameter("id");
	        String msgresult = request.getParameter("msg");
	        String success = request.getParameter("success");
	        
	        String url = "", msg;
	        RequestDispatcher rd;
	        ConnectionDB cn = new ConnectionDB();
	        studentDAO studentDao = new studentDAO(cn);

	        //Crear mensaje
	        if (msgresult != null) {
	            request.setAttribute("msg", msgresult);
	            request.setAttribute("success", success);
	        }
	        switch (action) {
	            case "toList":
	                List<student> lista = studentDao.getAll();
	                request.setAttribute("lista", lista);
	                url = "students.jsp";
	                break;
	            case "delete":
	                if (studentDao.delete(id)) {
	                    msg = "Se eliminó correctamente el alumno";
	                    url = "servletStudent?action=toList&success=true&msg=" + msg;
	                } else {
	                    msg = "Ocurrio un error, el alumno no fue eliminado";
	                    url = "servletStudent?action=toList&success=false&msg=" + msg;
	                }   
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
	        String firstname = request.getParameter("firstname");
	        String pname = request.getParameter("psurname");
	        String mname = request.getParameter("msurname");
	        String email = request.getParameter("email");
	        String phone = request.getParameter("phone");
	        String idplan = request.getParameter("planest");
	        String idschool = request.getParameter("escuela");
	        String idcareer = request.getParameter("carrera");
	        
	        String url = "", msg = "";
	        ConnectionDB cn = new ConnectionDB();
	        studentDAO studentDao = new studentDAO(cn);
	        student model = new student();

	        switch (action) {
	            case "insert":
	                model.setId(id);
	                model.setFirstname(firstname);
	                model.setPsurname(pname);
	                model.setMsurname(mname);
	                model.setEmail(email);
	                model.setPhone(phone);
	                model.setPassword(id); //La contraseña por default es su boleta
	                
	                model.setId_planest(Integer.parseInt(idplan));
	                model.setId_school(Integer.parseInt(idschool));
	                model.setId_career(Integer.parseInt(idcareer));
	                
	                model.setId_status(1);
	                if (studentDao.insert(model)) {
	                    msg = "Se guardo correctamente el alumno";
	                    url = "servletStudent?action=toList&success=true&msg=" + msg;
	                } else {
	                    msg = "Ocurrio un error, el usuario no fue guardado";
	                    url = "servletStudent?action=toList&success=false&msg=" + msg;
	                }
	                break;
	            case "update":
	                model.setId(id);
	                model.setFirstname(firstname);
	                model.setPsurname(pname);
	                model.setMsurname(mname);
	                model.setEmail(email);
	                model.setPhone(phone);
	                
	                model.setId_planest(Integer.parseInt(idplan));
	                model.setId_school(Integer.parseInt(idschool));
	                model.setId_career(Integer.parseInt(idcareer));

	                if (studentDao.update(model)) {
	                    msg = "Se modifico correctamente el alumno";
	                    url = "servletStudent?action=toList&success=true&msg=" + msg;
	                } else {
	                    msg = "Ocurrio un error, el usuario no fue modificado";
	                    url = "servletStudent?action=toList&success=false&msg=" + msg;
	                }
	                break;

	            default:
	                break;
	        }

	        cn.disconnect();
	        response.sendRedirect(url);
	}

}
