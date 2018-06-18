package net.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.json.JSONArray;
import com.json.JSONObject;

import net.dao.ConnectionDB;
import net.dao.examDAO;
import net.model.answersPerQuestion;
import net.model.questionPerExam;

/**
 * Servlet implementation class servletQuestion
 */
public class servletQuestion extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public servletQuestion() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("application/json");
        JSONObject objJson;
        JSONArray answers;
        PrintWriter out = response.getWriter();
        
        int user = (int) request.getSession().getAttribute("IdtableUser");
        String action = request.getParameter("action");
        String examId = request.getParameter("examId");
        String questionId = request.getParameter("questionId");
        String question = request.getParameter("question");
        String unitTemary = request.getParameter("unitTemary");
        String answerId = request.getParameter("answerId");
        String answer = request.getParameter("answer");
        
        ConnectionDB cn = new ConnectionDB();
        questionPerExam model = new questionPerExam();
        answersPerQuestion modela = new answersPerQuestion();
        
        examDAO exDao = new examDAO(cn);

        switch (action) {
            case "getQuestions":
                JSONArray questions = new JSONArray(exDao.getQuestions(Integer.parseInt(examId)));
                objJson = new JSONObject();
                objJson.put("questions", questions);
                out.print(objJson);
                out.flush();
                break;
            case "getQuestion":
                objJson = new JSONObject(exDao.getQuestion(Integer.parseInt(questionId)));
                out.print(objJson);
                out.flush();
                break;
            case "saveQuestion":
                Date d = new Date();
                objJson = new JSONObject();
                SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd");
                model.setId(Integer.parseInt(questionId));
                model.setQuestion(question);
                model.setCreatDate(formater.format(d));
                model.setUnitTemary(unitTemary);
                model.setDischargeDate(null);
                model.setIdUser(user);
                model.setIdStatus(1);
                if (!exDao.insertOrUpdateQuestion(model, Integer.parseInt(examId))) {
                    objJson.put("id", model.getId());
                    objJson.put("success", false);
                    objJson.put("msg", "Ocurrio un error, la pregunta no fue guardada");
                }   out.print(objJson);
                out.flush();
                break;
            case "deleteQuestion":
                objJson = new JSONObject();
                
                if (!exDao.deleteQuestion(Integer.parseInt(questionId))) {
                    objJson.put("success", false);
                    objJson.put("msg", "Ocurrio un error, la pregunta no fue eliminada");
                }   
                out.print(objJson);
                out.flush();
                break;
            case "getAnswers":
                answers = new JSONArray(exDao.getAnswers(Integer.parseInt(questionId)));
                objJson = new JSONObject();
                objJson.put("answers", answers);
                out.print(objJson);
                out.flush();
                break;
            case "changeAnswer":
                objJson = new JSONObject();
                String status = request.getParameter("status");
                
                modela.setId(Integer.parseInt(answerId));
                modela.setIdQuestion(Integer.parseInt(questionId));
                modela.setAnswer(answer);
                modela.setStatus(Integer.parseInt(status));
                
                if (!exDao.changeAnswer(modela)) {
                    objJson.put("id", modela.getId());
                    objJson.put("success", false);
                    objJson.put("msg", "Ocurrio un error, la respuesta no fue guardada");
                } 
                answers = new JSONArray(exDao.getAnswers(Integer.parseInt(questionId)));
                objJson.put("answers", answers);
                out.print(objJson);
                out.flush();
                break;
            case "saveAnswer":
                objJson = new JSONObject();
                
                modela.setId(Integer.parseInt(answerId));
                modela.setIdQuestion(Integer.parseInt(questionId));
                modela.setAnswer(answer);
                modela.setStatus(0); //Por default como incorrecta y activa
                
                if (!exDao.insertOrUpdateAnswer(modela)) {
                    objJson.put("id", modela.getId());
                    objJson.put("success", false);
                    objJson.put("msg", "Ocurrio un error, la respuesta no fue guardada");
                }   
                answers = new JSONArray(exDao.getAnswers(Integer.parseInt(questionId)));
                objJson.put("answers", answers);
                out.print(objJson);
                out.flush();
                break;     
            case "deleteAnswer":
                objJson = new JSONObject();
                
                if (!exDao.deleteAnswer(Integer.parseInt(answerId))) {
                    objJson.put("success", false);
                    objJson.put("msg", "Ocurrio un error, la respuesta no fue eliminada");
                }   
                answers = new JSONArray(exDao.getAnswers(Integer.parseInt(questionId)));
                objJson.put("answers", answers);
                out.print(objJson);
                out.flush();
                break;
            default:
                break;
        }
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
