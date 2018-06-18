package net.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.dao.ConnectionDB;
import net.dao.careerDAO;
import net.model.career;

/**
 * Servlet implementation class servletABCCareer
 */
public class servletABCCareer extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public servletABCCareer() {
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
        String id = request.getParameter("idCareer");
        String description = request.getParameter("description");

        ConnectionDB cn = new ConnectionDB();
        career careerObj = new career();
        String msg = "", url = "";
        boolean status = false;
        RequestDispatcher rd;
        careerDAO careerDao = new careerDAO(cn);

        //Crear mensaje
        if (msgresult != null) {
            request.setAttribute("msg", msgresult);
            request.setAttribute("success", success);
        }
        
        switch (action) {
            case "toList":
                List<career> lista = careerDao.getAll();
                request.setAttribute("lista", lista);
                url = "career.jsp";
                break;
            case "insert":
                careerObj.setDescription(description);
                status = careerDao.insert(careerObj);
                if (status) {
                    msg = "Se guardo correctamente la carrera";
                } else {
                    msg = "Ocurrio un error, la carrera no fue guardada";
                }
                url = "servletABCCareer?action=toList&success=" + status + "&msg=" + msg;
                break;
            case "update":
                careerObj.setId(Integer.parseInt(id));
                careerObj.setDescription(description);
                status = careerDao.update(careerObj);
                if (status) {
                    msg = "Se actualizo correctamente la información de la carrera";
                } else {
                    msg = "Ocurrio un error, no se actualizo la información de la carrera";
                }
                url = "servletABCCareer?action=toList&success=" + status + "&msg=" + msg;
                break;
            case "delete":
                 try {
                    status = careerDao.delete(Integer.parseInt(id));
                    
                    if (status) {
                        msg = "Se elimino correctamente la carrera";
                    } else {
                        msg = "Ocurrio un error, la carrera no se elimino";
                    }
                    
                } catch (NumberFormatException ex) {
                    msg = ex.getMessage();
                    status = false;
                }
                url = "servletABCCareer?action=toList&success=" + status + "&msg=" + msg;
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
