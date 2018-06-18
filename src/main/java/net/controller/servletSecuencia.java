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
import net.dao.secuenciaDAO;
import net.model.secuencia;
import net.model.studentsPerSecuencia;

/**
 * Servlet implementation class servletSecuencia
 */
public class servletSecuencia extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public servletSecuencia() {
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
        int user = (int) request.getSession().getAttribute("IdtableUser");
        
        String url = "", msg;
        RequestDispatcher rd;
        ConnectionDB cn = new ConnectionDB();
        secuenciaDAO secDao = new secuenciaDAO(cn);

        //Crear mensaje
        if (msgresult != null) {
            request.setAttribute("msg", msgresult);
            request.setAttribute("success", success);
        }
        switch (action) {
            case "toList":
                List<secuencia> lista = secDao.getAll();
                request.setAttribute("lista", lista);
                url = "secuencias.jsp";
                break;
            case "mySecuencias":
                List<secuencia> myList = secDao.getMisSecuencias(user);
                request.setAttribute("myList", myList);
                url = "mySecuencias.jsp";
                break;
            case "getSecuencia":
                secuencia model;
                if(id.equals(null) || id.isEmpty()){
                    model = new secuencia();
                }else{
                    model = secDao.getSecuencia(id, user);
                }
                request.setAttribute("mySecuencia", model);
                request.setAttribute("students", secDao.getStudents(id, user));
                url = "secuencia.jsp";
                
                break;             
            case "delete":
                if (secDao.delete(id, user)) {
                    msg = "Se eliminó correctamente la secuencia";
                    url = "servletSecuencia?action=toList&success=true&msg=" + msg;
                } else {
                    msg = "Ocurrio un error, la secuencia no fue eliminada";
                    url = "servletSecuencia?action=toList&success=false&msg=" + msg;
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
		response.setContentType("application/json");
        JSONObject objJson;
        JSONArray students, secuencias;
        PrintWriter out = response.getWriter();

        String action = request.getParameter("action");
        String id = request.getParameter("id");
        String idstudent = request.getParameter("idstudent");
        String idsecuencia = request.getParameter("idsecuencia");
        String subject = request.getParameter("subject");
        String description = request.getParameter("description");
        int user = (int) request.getSession().getAttribute("IdtableUser");
        
        String url = "", msg = "";
        ConnectionDB cn = new ConnectionDB();
        secuenciaDAO secDao = new secuenciaDAO(cn);
        examDAO exDao = new examDAO(cn);
        secuencia model = new secuencia();
        studentsPerSecuencia modela;

        switch (action) {
            case "getStudents":
                students = new JSONArray(secDao.getStudents(id, user));
                objJson = new JSONObject();
                objJson.put("students", students);
                out.print(objJson);
                out.flush();
                break;
            case "insert": 
                model.setId(id);
                model.setIdsubject(subject);
                model.setIdstatus(1);
                model.setDescripcion(description);
                model.setIduser(user);
                
                if (secDao.insert(model)) {
                    msg = "Se guardo correctamente la secuencia";
                    url = "servletSecuencia?action=mySecuencias&success=true&msg=" + msg;
                } else {
                    msg = "Ocurrio un error, la secuencia no fue guardada";
                    url = "servletSecuencia?action=mySecuencias&success=false&msg=" + msg;
                }
                break;
            case "update": 
                model.setId(id);
                model.setIdsubject(subject);
                model.setIdstatus(1);
                model.setDescripcion(description);
                model.setIduser(user);
                
                if (secDao.update(model)) {
                    msg = "Se guardo correctamente la secuencia";
                    url = "servletSecuencia?action=getSecuencia&id=" + model.getId() + "&success=true&msg=" + msg;
                } else {
                    msg = "Ocurrio un error, la secuencia no fue guardada";
                    url = "servletSecuencia?action=getSecuencia&id=" + model.getId() + "&success=false&msg=" + msg;
                }
                break;
            case "saveStudent":
                objJson = new JSONObject();
                modela = new studentsPerSecuencia();
                modela.setId(idstudent);
                modela.setIdsecuencia(id);
                modela.setIdstatus(1);
                
                if (!secDao.insertStudent(modela)) {
                    objJson.put("id", modela.getIdstudent());
                    objJson.put("success", false);
                    objJson.put("msg", "Ocurrio un error, el alumno no fue agregado a la secuencia");
                } 
                students = new JSONArray(secDao.getStudents(id, user));
                objJson.put("studentsResp", students);
                out.print(objJson);
                out.flush();
                break;
            case "deleteStudent":
                objJson = new JSONObject();
                modela = new studentsPerSecuencia();
                modela.setId(idstudent);
                modela.setIdsecuencia(id);
                modela.setIdstatus(0);
                
                if (!secDao.deleteStudent(modela)) {
                    objJson.put("id", modela.getIdstudent());
                    objJson.put("success", false);
                    objJson.put("msg", "Ocurrio un error, el alumno no fue agregado a la secuencia");
                } 
                students = new JSONArray(secDao.getStudents(id, user));
                objJson.put("studentsResp", students);
                out.print(objJson);
                out.flush();
                break;
            case "addSecuencia":
                objJson = new JSONObject();
                
                if(!exDao.insertSecuenciaPerExam(Integer.parseInt(id), idsecuencia)){
                    objJson.put("id", idsecuencia);
                    objJson.put("success", false);
                    objJson.put("msg", "Ocurrio un error, la secuencia no fue asignada al examen");
                }
                secuencias = new JSONArray(exDao.getSecuenciasPerExam(Integer.parseInt(id)));
                objJson.put("secuencias", secuencias);
                out.print(objJson);
                out.flush();                
                break;
            case "getSecuenciasPerExam":
                objJson = new JSONObject();
                secuencias = new JSONArray(exDao.getSecuenciasPerExam(Integer.parseInt(id)));
                objJson.put("secuencias", secuencias);
                out.print(objJson);
                out.flush();  
                break;
            default:
                break;
        }

        cn.disconnect();
        response.sendRedirect(url);
	}

}
