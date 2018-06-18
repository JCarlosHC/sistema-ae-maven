package net.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.json.JSONArray;
import com.json.JSONObject;

import net.dao.ConnectionDB;
import net.dao.examDAO;
import net.model.aplicationExam;
import net.model.exam;

/**
 * Servlet implementation class servletExamUser
 */
public class servletExamUser extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public servletExamUser() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String userId = (String) request.getSession().getAttribute("userId");
        String id = request.getParameter("id");
        String action = request.getParameter("action");
        String url = "";
        RequestDispatcher rd;
        ConnectionDB cn = new ConnectionDB();
        examDAO exDao = new examDAO(cn);

        if (action == null) {
            url = "index.jsp";
        } else if (action.equals("getExam")) {
            exam model;
            if (Integer.parseInt(id) == 0) {
                model = new exam(0, "", "", 0, null, null, 0, 0, 0, 0, null);
            } else {
                model = exDao.getExam(Integer.parseInt(id));
            }
            request.setAttribute("myExam", model);
            url = "examUser.jsp";
        } else if (action.equals("getExamsResolved")) {
            List<aplicationExam> lista = exDao.getMyExamsResolved(userId);
            request.setAttribute("lista", lista);
            url = "examsResolved.jsp";
        }
        cn.disconnect();
        rd = request.getRequestDispatcher(url);
        rd.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("application/json");
        JSONObject objJson;
        PrintWriter out = response.getWriter();

        int user = (int) request.getSession().getAttribute("IdtableUser");
        String userId = (String) request.getSession().getAttribute("userId");
        String action = request.getParameter("action");
        String id = request.getParameter("id");

        ConnectionDB cn = new ConnectionDB();
        examDAO exDao = new examDAO(cn);

        if (action.equals("getExamenesAsignados")) {
            JSONArray myExamsEarmarked = new JSONArray(exDao.getMyExamsEarmarked(userId));
            objJson = new JSONObject();
            objJson.put("exams", myExamsEarmarked);
            out.print(objJson);
            out.flush();
        } else if (action.equals("aplicarExamen")) {
            String respuestasuser = request.getParameter("respuestasuser");
            JSONArray array = new JSONArray(respuestasuser);
            aplicationExam model = exDao.insertAnswersUser(array, userId, Integer.parseInt(id));
            objJson = new JSONObject(model);
            out.print(objJson);
            out.flush();
        }
        cn.disconnect();
	}

}
