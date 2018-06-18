package net.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import javax.servlet.RequestDispatcher;
import net.dao.*;
import net.model.planEstudio;

/**
 * Servlet implementation class servletABCPlan
 */
public class servletABCPlan extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public servletABCPlan() {
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
	        String id = request.getParameter("idPlan");
	        String description = request.getParameter("description");
	        String startDate = request.getParameter("cStartDate");
	        String endDate = request.getParameter("cEndDate");

	        ConnectionDB cn = new ConnectionDB();
	        planEstudio pe = new planEstudio();
	        SimpleDateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd");
	        String msg = "", url = "";
	        boolean status = false;
	        RequestDispatcher rd;
	        planEstudioDAO peDao = new planEstudioDAO(cn);

	        //Crear mensaje
	        if (msgresult != null) {
	            request.setAttribute("msg", msgresult);
	            request.setAttribute("success", success);
	        }

	        switch (action) {
	            case "toList":
	                List<planEstudio> lista = peDao.getAll();
	                request.setAttribute("lista", lista);
	                url = "planEstudio.jsp";
	                break;
	            case "insert":
	                try {
	                    pe.setDescription(description);
	                    pe.setStartDate(formatDate.parse(startDate));
	                    pe.setEndDate(formatDate.parse(endDate));
	                    
	                    status = peDao.insert(pe);
	                    
	                    if (status) {
	                        msg = "Se guardo correctamente el plan de estudio";
	                    } else {
	                        msg = "Ocurrio un error, el plan de estudio no fue guardado";
	                    }
	                    
	                } catch (ParseException ex) {
	                    msg = ex.getMessage();
	                    status = false;
	                }   url = "servletABCPlan?action=toList&success=" + status + "&msg=" + msg;
	                break;
	            case "update":
	                try {
	                    pe.setId(Integer.parseInt(id));
	                    pe.setDescription(description);
	                    pe.setStartDate(formatDate.parse(startDate));
	                    pe.setEndDate(formatDate.parse(endDate));
	                    
	                    status = peDao.update(pe);
	                    
	                    if (status) {
	                        msg = "Se actualizo correctamente el plan de estudio";
	                    } else {
	                        msg = "Ocurrio un error, el plan de estudio no fue actualizado";
	                    }
	                    
	                } catch (ParseException ex) {
	                    msg = ex.getMessage();
	                    status = false;
	                }   url = "servletABCPlan?action=toList&success=" + status + "&msg=" + msg;
	                break;
	            case "delete":
	                try {
	                    status = peDao.delete(Integer.parseInt(id));
	                    
	                    if (status) {
	                        msg = "Se elimino correctamente el plan";
	                    } else {
	                        msg = "Ocurrio un error, el plan de estudio no se elimino";
	                    }
	                    
	                } catch (Exception ex) {
	                    msg = ex.getMessage();
	                    status = false;
	                }   url = "servletABCPlan?action=toList&success=" + status + "&msg=" + msg;
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
