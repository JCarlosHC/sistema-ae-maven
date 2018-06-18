package net.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.dao.ConnectionDB;
import net.dao.schoolDAO;
import net.model.school;

/**
 * Servlet implementation class servletABCSchool
 */
public class servletABCSchool extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public servletABCSchool() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");

        //Recepcion de parametros
        String action = request.getParameter("action");
        String msgresult = request.getParameter("msg");
        String success = request.getParameter("success");
        String id = request.getParameter("idSchool");
        String description = request.getParameter("description");
        String acronym = request.getParameter("acronym");

        ConnectionDB cn = new ConnectionDB();
        school school = new school();
        String msg = "", url = "";
        boolean status = false;
        RequestDispatcher rd;
        schoolDAO schoolDao = new schoolDAO(cn);

        //Crear mensaje
        if (msgresult != null) {
            request.setAttribute("msg", msgresult);
            request.setAttribute("success", success);
        }

        switch (action) {
            case "toList":
                List<school> lista = schoolDao.getAll();
                request.setAttribute("lista", lista);
                url = "school.jsp";
                break;
            case "insert":
                school.setDescription(description);
                school.setAcronym(acronym);
                status = schoolDao.insert(school);
                if (status) {
                    msg = "Se guardo correctamente la escuela";
                } else {
                    msg = "Ocurrio un error, la escuela no fue guardada";
                }
                url = "servletABCSchool?action=toList&success=" + status + "&msg=" + msg;
                break;
            case "update":
                school.setId(Integer.parseInt(id));
                school.setDescription(description);
                school.setAcronym(acronym);
                status = schoolDao.update(school);
                if (status) {
                    msg = "Se actualizo correctamente la información de la escuela";
                } else {
                    msg = "Ocurrio un error, no se actualizo la información de la escuela";
                }
                url = "servletABCSchool?action=toList&success=" + status + "&msg=" + msg;
                break;
            case "delete":
                 try {
                    status = schoolDao.delete(Integer.parseInt(id));
                    
                    if (status) {
                        msg = "Se elimino correctamente la escuela";
                    } else {
                        msg = "Ocurrio un error, la escuela no se elimino";
                    }
                    
                } catch (NumberFormatException ex) {
                    msg = ex.getMessage();
                    status = false;
                }
                url = "servletABCSchool?action=toList&success=" + status + "&msg=" + msg;
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
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
