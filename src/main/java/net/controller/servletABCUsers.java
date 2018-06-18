package net.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.dao.ConnectionDB;
import net.dao.catalogs;
import net.dao.userDAO;
import net.model.typeuser;
import net.model.user;

/**
 * Servlet implementation class servletABCUsers
 */
public class servletABCUsers extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public servletABCUsers() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getParameter("action");
        String id = request.getParameter("iduser");
        String idType = request.getParameter("idType");
        String msgresult = request.getParameter("msg");
        String success = request.getParameter("success");
        
        String url = "", msg;
        RequestDispatcher rd;
        ConnectionDB cn = new ConnectionDB();
        userDAO usrDao = new userDAO(cn);

        //Crear mensaje
        if (msgresult != null) {
            request.setAttribute("msg", msgresult);
            request.setAttribute("success", success);
        }
        switch (action) {
            case "toList":
                List<user> lista = usrDao.getAll();
                request.setAttribute("lista", lista);
                url = "users.jsp";
                break;
            case "delete":
                if (usrDao.delete(Integer.parseInt(id))) {
                    msg = "Se eliminó correctamente el usuario";
                    url = "servletABCUsers?action=toList&success=true&msg=" + msg;
                } else {
                    msg = "Ocurrio un error, el usuario no fue eliminado";
                    url = "servletABCUsers?action=toList&success=false&msg=" + msg;
                }   
                break;
            case "deleteType":
                if (usrDao.deleteType(Integer.parseInt(idType))) {
                    msg = "Se eliminó correctamente el tipo";
                    url = "servletABCUsers?action=typeusers&success=true&msg=" + msg;
                } else {
                    msg = "Ocurrio un error, el tipo no fue eliminado";
                    url = "servletABCUsers?action=typeusers&success=false&msg=" + msg;
                }   
                break;
            case "typeusers":
                catalogs catalog = new catalogs(cn);
                List<typeuser> listaType = catalog.getListTypeUsers();
                request.setAttribute("listaType", listaType);
                url = "typeusers.jsp";
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
        String id = request.getParameter("iduser");
        String firstname = request.getParameter("firstname");
        String pname = request.getParameter("psurname");
        String mname = request.getParameter("msurname");
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");
        String typeuser = request.getParameter("typeuser");
        //Tipos de usuario
        String idType = request.getParameter("idType");
        String description = request.getParameter("description");
        typeuser type = new typeuser();
        
        String url = "", msg = "";
        ConnectionDB cn = new ConnectionDB();
        userDAO usrDao = new userDAO(cn);
        user model = new user();

        switch (action) {
            case "insert":
                model.setFirstname(firstname);
                model.setPsurname(pname);
                model.setMsurname(mname);
                model.setEmail(email);
                model.setPhone(phone);
                model.setPassword(pname.substring(0, 0) + firstname.trim()); //La contraseña por default es la primera letra de su apellido paterno mas el nombre
                model.setId_tipo(Integer.parseInt(typeuser));
                model.setId_status(1);
                if (usrDao.insert(model)) {
                    msg = "Se guardo correctamente el usuario";
                    url = "servletABCUsers?action=toList&success=true&msg=" + msg;
                } else {
                    msg = "Ocurrio un error, el usuario no fue guardado";
                    url = "servletABCUsers?action=toList&success=false&msg=" + msg;
                }
                break;
            case "insertType":
                type.setDescription(description);
                
                if (usrDao.insertType(type)) {
                    msg = "Se guardo correctamente el tipo";
                    url = "servletABCUsers?action=typeusers&success=true&msg=" + msg;
                } else {
                    msg = "Ocurrio un error, el tipo no fue guardado";
                    url = "servletABCUsers?action=typeusers&success=false&msg=" + msg;
                }
                break;
            case "updateType":
                type.setId(Integer.parseInt(idType));
                type.setDescription(description);
                
                if (usrDao.updateType(type)) {
                    msg = "Se modificó correctamente el tipo";
                    url = "servletABCUsers?action=typeusers&success=true&msg=" + msg;
                } else {
                    msg = "Ocurrio un error, el tipo no fue modificado";
                    url = "servletABCUsers?action=typeusers&success=false&msg=" + msg;
                }
                break;
            case "update":
                model.setId(Integer.parseInt(id));
                model.setFirstname(firstname);
                model.setPsurname(pname);
                model.setMsurname(mname);
                model.setEmail(email);
                model.setPhone(phone);
                model.setId_tipo(Integer.parseInt(typeuser));

                if (usrDao.update(model)) {
                    msg = "Se modifico correctamente el usuario";
                    url = "servletABCUsers?action=toList&success=true&msg=" + msg;
                } else {
                    msg = "Ocurrio un error, el usuario no fue modificado";
                    url = "servletABCUsers?action=toList&success=false&msg=" + msg;
                }
                break;

            default:
                break;
        }

        cn.disconnect();
        response.sendRedirect(url);
	}

}
