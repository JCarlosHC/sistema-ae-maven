package net.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.dao.ConnectionDB;
import net.dao.userDAO;
import net.model.student;
import net.model.user;

/**
 * Servlet implementation class servletInformation
 */
public class servletInformation extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public servletInformation() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");

        String action = request.getParameter("action");
        String url = "";

        int userType = (int) request.getSession().getAttribute("userType");
        int user = (int) request.getSession().getAttribute("IdtableUser");
        String userEmail = (String) request.getSession().getAttribute("userEmail");
        String userId = (String) request.getSession().getAttribute("userId");

        RequestDispatcher rd;
        ConnectionDB cn = new ConnectionDB();
        userDAO usrDao = new userDAO(cn);

        if(action == null){
            url = "index.jsp";
        }else if (action.equals("getUser")) {
            if (userType == 4) {
                student stu = usrDao.getStudent(userId);
                request.setAttribute("userinformation", stu);
                url = "myInformation.jsp";
            } else {
                user usr = usrDao.getUser(user);
                request.setAttribute("userinformation", usr);
                url = "myInformation.jsp";
            }
        }

        rd = request.getRequestDispatcher(url);
        rd.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
        RequestDispatcher rd;

        String action = request.getParameter("action");
        String userEmail = (String) request.getSession().getAttribute("userEmail");
        String userId = (String) request.getSession().getAttribute("userId");
        int userType = (int) request.getSession().getAttribute("userType");
        int user = (int) request.getSession().getAttribute("IdtableUser");
        String passActual = request.getParameter("passActual");
        String confirmnewPassword = request.getParameter("ConfirmnewPassword");
        String newPassword = request.getParameter("newPassword");

        String id = request.getParameter("id");;
        String firstname = request.getParameter("firstname");
        String psurname = request.getParameter("psurname");
        String msurname = request.getParameter("msurname");
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");

        String url = "";
        ConnectionDB cn = new ConnectionDB();
        userDAO usrDao = new userDAO(cn);

        if (action.equals("updatePassword")) {
            if (confirmnewPassword.equals(newPassword)) {
                if (userType == 4) {
                    student stu = usrDao.validateStudent(userId, passActual);
                    if (stu != null) {
                        if (usrDao.updatePasswordStudent(newPassword, userId)) {
                            url = "loginstudent.jsp";
                        } else {
                            url = "changePassword.jsp";
                            request.setAttribute("msg", "Ocurrio un error, no se pudo actualizar la contraseña");
                            request.setAttribute("success", "false");
                        }
                    } else {
                        url = "changePassword.jsp";
                        request.setAttribute("msg", "Ocurrio un error, sus credenciales no son validas");
                        request.setAttribute("success", "false");
                    }
                } else {
                    user usr = usrDao.validate(userEmail, passActual);
                    if (usr != null) {
                        if (usrDao.updatePasswordUser(newPassword, userEmail)) {
                            url = "loginuser.jsp";
                        } else {
                            url = "changePassword.jsp";
                            request.setAttribute("msg", "Ocurrio un error, no se pudo actualizar la contraseña");
                            request.setAttribute("success", "false");
                        }
                    } else {
                        url = "changePassword.jsp";
                        request.setAttribute("msg", "Ocurrio un error, sus credenciales no son validas");
                        request.setAttribute("success", "false");
                    }
                }
            } else {
                url = "changePassword.jsp";
                request.setAttribute("msg", "Ocurrio un error, las contraseñas no coinciden");
                request.setAttribute("success", "false");
            }
        } else if (action.equals("updateInformation")) {
            if (userType == 4) {
                student m = new student();
                m.setId(id);
                m.setFirstname(firstname);
                m.setPsurname(psurname);
                m.setMsurname(msurname);
                m.setEmail(email);
                m.setPhone(phone);
                
                if (usrDao.updateStudent(m)) {
                    url = "myInformation.jsp";
                    request.setAttribute("msg", "Se guardo correctamenta la informacion");
                    request.setAttribute("success", "true");
                } else {
                    url = "myInformation.jsp";
                    request.setAttribute("msg", "No se pudo actualizar la información");
                    request.setAttribute("success", "false");
                }
                student stu = usrDao.getStudent(userId);
                request.setAttribute("userinformation", stu);
            } else {
                user u = new user();
                u.setId(Integer.parseInt(id));
                u.setFirstname(firstname);
                u.setPsurname(psurname);
                u.setMsurname(msurname);
                u.setEmail(email);
                u.setPhone(phone);
                u.setId_tipo(userType);
                
                if (usrDao.update(u)) {
                    url = "myInformation.jsp";
                    request.setAttribute("msg", "Se guardo correctamenta la informacion");
                    request.setAttribute("success", "true");
                } else {
                    url = "myInformation.jsp";
                    request.setAttribute("msg", "No se pudo actualizar la información");
                    request.setAttribute("success", "false");
                }
                user usr = usrDao.getUser(user);
                request.setAttribute("userinformation", usr);
            }
        }

        cn.disconnect();
        rd = request.getRequestDispatcher(url);
        rd.forward(request, response);
	}

}
