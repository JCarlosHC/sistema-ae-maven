package net.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.dao.ConnectionDB;
import net.dao.subjectDAO;
import net.model.subjects;

/**
 * Servlet implementation class servletABCSubjects
 */
public class servletABCSubjects extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public servletABCSubjects() {
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
        String id = request.getParameter("idSubject");
        String description = request.getParameter("description");

        ConnectionDB cn = new ConnectionDB();
        subjects subjectObj = new subjects();
        String msg = "", url = "";
        boolean status = false;
        RequestDispatcher rd;
        subjectDAO subjectDao_Obj = new subjectDAO(cn);

        //Crear mensaje
        if (msgresult != null) {
            request.setAttribute("msg", msgresult);
            request.setAttribute("success", success);
        }

        switch (action) {
            case "toList":
                List<subjects> lista = subjectDao_Obj.getAll();
                request.setAttribute("lista", lista);
                url = "subjects.jsp";
                break;
            case "insert":
                subjectObj.setId(id);
                subjectObj.setDescription(description);
                status = subjectDao_Obj.insert(subjectObj);
                if (status) {
                    msg = "Se guardo correctamente la materia";
                } else {
                    msg = "Ocurrio un error, la materia no fue guardada";
                }
                url = "servletABCSubjects?action=toList&success=" + status + "&msg=" + msg;
                break;
            case "update":
                subjectObj.setId(id);
                subjectObj.setDescription(description);
                status = subjectDao_Obj.update(subjectObj);
                if (status) {
                    msg = "Se actualizo correctamente la información de la materia";
                } else {
                    msg = "Ocurrio un error, no se actualizo la información de la materia";
                }
                url = "servletABCSubjects?action=toList&success=" + status + "&msg=" + msg;
                break;
            case "delete":
                 try {
                    status = subjectDao_Obj.delete(id);
                    
                    if (status) {
                        msg = "Se elimino correctamente la materia";
                    } else {
                        msg = "Ocurrio un error, la materia no se elimino";
                    }
                    
                } catch (NumberFormatException ex) {
                    msg = ex.getMessage();
                    status = false;
                }
                url = "servletABCSubjects?action=toList&success=" + status + "&msg=" + msg;
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
