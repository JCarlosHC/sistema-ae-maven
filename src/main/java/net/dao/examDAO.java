package net.dao;

import com.json.JSONArray;
import com.json.JSONObject;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import net.model.answersPerQuestion;
import net.model.aplicationExam;
import net.model.detailExam;
import net.model.typeExam;
import net.model.exam;
import net.model.examforview;
import net.model.examsPerSecuencia;
import net.model.questionPerExam;

public class examDAO {

    ConnectionDB cn;

    public examDAO(ConnectionDB _cn) {
        this.cn = _cn;
    }

    //Funciones CRUD basic information
    public boolean insertOrUpdate(exam model) {
        String sql = "call SVURS_CRUDBasicInformation(?,?,?,?,?,?,?,?,?,?,?)";
        try {
            PreparedStatement ps = cn.getConnection().prepareStatement(sql);
            ps.setInt(1, model.getId());
            ps.setString(2, model.getDescription());
            ps.setInt(3, model.getQuestions());
            ps.setString(4, model.getCreatDate());
            ps.setString(5, model.getDischargeDate());
            ps.setFloat(6, model.getNote());
            ps.setInt(7, model.getId_user());
            ps.setInt(8, model.getId_status());
            ps.setInt(9, model.getId_typeExa());
            ps.setString(10, model.getTitle());
            ps.setString(11, model.getImage());
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                model.setId(rs.getInt(1));
            }
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    public boolean updateImage(exam model) {
        String sql = "update ae_creaexa set image = ? where id_creaexa = ?";
        try {
            PreparedStatement ps = cn.getConnection().prepareStatement(sql);
            ps.setString(1, model.getImage());
            ps.setInt(2, model.getId());
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    public boolean delete(int id) {
        String sql = "UPDATE ae_creaexa SET ID_ESTATUS = 0, FECHA_BAJA = NOW() WHERE ID_CREAEXA=?";
        try {
            PreparedStatement ps = cn.getConnection().prepareStatement(sql);
            ps.setInt(1, id);
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    public boolean update(exam model) {
        String sql = "UPDATE ae_creaexa SET "
                + "DESCRIPCION=?, CAL_MAX=?, ID_TIPOEXA=? "
                + "WHERE ID_CREAEXA=?";

        try {
            PreparedStatement ps = cn.getConnection().prepareStatement(sql);
            ps.setString(1, model.getDescription());
            ps.setFloat(2, model.getNote());
            ps.setInt(3, model.getId_typeExa());
            ps.setInt(4, model.getId());
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    public List<exam> getAll() {
        ArrayList<exam> lista = new ArrayList<exam>();
        String sql = "SELECT ex.ID_CREAEXA, ex.TITLE, ex.DESCRIPCION, ex.NUM_PREGUNTAS, ex.FECHA_CREACION, ex.FECHA_BAJA, "
                + "ex.CAL_MAX, ex.ID_USUARIO, ex.ID_ESTATUS, ex.ID_TIPOEXA, ex.IMAGE FROM ae_creaexa ex;";
        try {
            PreparedStatement sta = cn.getConnection().prepareStatement(sql);
            ResultSet rs = sta.executeQuery();

            while (rs.next()) {
                exam sc = new exam(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getInt(4), rs.getString(5), rs.getString(6),
                        rs.getFloat(7), rs.getInt(8), rs.getInt(9), rs.getInt(10), rs.getString(11));
                lista.add(sc);
            }

        } catch (SQLException e) {
            lista = null;
        }
        return lista;
    }

    public List<examforview> getMyExams(int iduser) {
        ArrayList<examforview> lista = new ArrayList<examforview>();
        String sql = "SELECT ex.ID_CREAEXA, ex.TITLE, ex.IMAGE, ex.DESCRIPCION, IFNULL(SEC_TO_TIME(averageTime(ex.ID_CREAEXA)),0) as AVERAGETIME,"
                + "IFNULL(averageMark(ex.ID_CREAEXA),0) as AVERAGEMARK, totalStudents(ex.ID_CREAEXA) as TOTALSTUDENTS, ex.CAL_MAX "
                + "FROM ae_creaexa ex INNER JOIN ae_usuarios us ON ex.ID_USUARIO = us.ID_USUARIO WHERE ex.ID_USUARIO=?;";
        try {
            PreparedStatement sta = cn.getConnection().prepareStatement(sql);
            sta.setInt(1, iduser);
            ResultSet rs = sta.executeQuery();

            while (rs.next()) {
                examforview sc = new examforview(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4),
                        rs.getString(5), rs.getFloat(6), rs.getInt(7), rs.getFloat(8));
                lista.add(sc);
            }

        } catch (SQLException e) {
            lista = null;
        }
        return lista;
    }

    public exam getExam(int id) {
        String sql = "SELECT ex.ID_CREAEXA, ex.TITLE, ex.DESCRIPCION, ex.NUM_PREGUNTAS, ex.FECHA_CREACION, ex.FECHA_BAJA, "
                + "ex.CAL_MAX, ex.ID_USUARIO, ex.ID_ESTATUS, ex.ID_TIPOEXA, ex.IMAGE FROM ae_creaexa ex WHERE ex.ID_CREAEXA=?";
        exam model = new exam();
        List<questionPerExam> questions = null;
        try {
            PreparedStatement sta = cn.getConnection().prepareStatement(sql);
            sta.setInt(1, id);
            ResultSet rs = sta.executeQuery();

            while (rs.next()) {
                model = new exam(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getInt(4), rs.getString(5), rs.getString(6),
                        rs.getFloat(7), rs.getInt(8), rs.getInt(9), rs.getInt(10), rs.getString(11));
                questions = this.getQuestions(model.getId());
                model.setQuestionsList(questions);
            }

        } catch (SQLException e) {
            model = null;
        }

        return model;
    }

    //Funtions CRUD Types of the exams
    public boolean insertType(typeExam model) {
        String id = "SELECT MAX(ID_TIPOEXA) + 1 from ae_tipoexa";
        String sql = "INSERT INTO ae_tipoexa(ID_TIPOEXA, DESCRIPCION) VALUES(?,?)";
        int idType = 0;

        try {
            PreparedStatement sta = cn.getConnection().prepareStatement(id);
            ResultSet rs = sta.executeQuery();

            while (rs.next()) {
                idType = rs.getInt(1);
            }
            PreparedStatement ps = cn.getConnection().prepareStatement(sql);
            ps.setInt(1, idType);
            ps.setString(2, model.getDescription());
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    public boolean deleteType(int id) {
        String sql = "DELETE FROM ae_tipoexa WHERE ID_TIPOEXA=?";
        try {
            PreparedStatement ps = cn.getConnection().prepareStatement(sql);
            ps.setInt(1, id);
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    public boolean updateType(typeExam model) {
        String sql = "UPDATE ae_tipoexa SET DESCRIPCION = ? WHERE ID_TIPOEXA=?";
        try {
            PreparedStatement ps = cn.getConnection().prepareStatement(sql);
            ps.setString(1, model.getDescription());
            ps.setInt(2, model.getId());
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    //Funcions CRUD preguntas
    public List<questionPerExam> getQuestions(int id) {
        ArrayList<questionPerExam> lista = new ArrayList<questionPerExam>();
        String sql = "select p.* from ae_cexapreg ce "
                + "inner join ae_creaexa ac on ce.ID_CREAEXA = ac.ID_CREAEXA "
                + "inner join ae_preguntas p on p.ID_PREGUNTA = ce.ID_PREGUNTA "
                + "where ce.ID_CREAEXA =? and p.ID_ESTATUS = 1";
        try {
            PreparedStatement sta = cn.getConnection().prepareStatement(sql);
            sta.setInt(1, id);
            ResultSet rs = sta.executeQuery();

            while (rs.next()) {
                questionPerExam model = new questionPerExam(
                        rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5),
                        rs.getInt(6), rs.getInt(7));
                List<answersPerQuestion> answers = this.getAnswers(model.getId());
                model.setAnswers(answers);
                lista.add(model);
            }

        } catch (SQLException e) {
            lista = null;
        }
        return lista;
    }

    public questionPerExam getQuestion(int id) {
        questionPerExam model = null;

        String sql = "select * from ae_preguntas "
                + "where ID_PREGUNTA =?";
        try {
            PreparedStatement sta = cn.getConnection().prepareStatement(sql);
            sta.setInt(1, id);
            ResultSet rs = sta.executeQuery();

            while (rs.next()) {
                model = new questionPerExam(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4),
                        rs.getString(5), rs.getInt(6), rs.getInt(7));
            }
            List<answersPerQuestion> answers = this.getAnswers(id);
            model.setAnswers(answers);

        } catch (SQLException e) {
            model = null;
        }
        return model;
    }

    public boolean insertOrUpdateQuestion(questionPerExam model, int idexam) {
        String sql = "call SVURS_CRUDQuestion(?,?,?,?,?,?,?,?)";
        try {
            PreparedStatement ps = cn.getConnection().prepareStatement(sql);
            ps.setInt(1, model.getId());
            ps.setString(2, model.getQuestion());
            ps.setString(3, model.getCreatDate());
            ps.setString(4, model.getUnitTemary());
            ps.setString(5, model.getDischargeDate());
            ps.setInt(6, model.getIdUser());
            ps.setInt(7, model.getIdStatus());
            ps.setInt(8, idexam);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                model.setId(rs.getInt(1));
            }
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    public boolean deleteQuestion(int id) {
        String sql = "call SVURS_CRUDQuestion(?,?,?,?,?,?,?,?)";
        questionPerExam model = this.getQuestion(id);
        model.setIdStatus(0);
        try {
            PreparedStatement ps = cn.getConnection().prepareStatement(sql);
            ps.setInt(1, model.getId());
            ps.setString(2, model.getQuestion());
            ps.setString(3, model.getCreatDate());
            ps.setString(4, model.getUnitTemary());
            ps.setString(5, model.getDischargeDate());
            ps.setInt(6, model.getIdUser());
            ps.setInt(7, model.getIdStatus());
            ps.setInt(8, 0);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                model.setId(rs.getInt(1));
            }
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    //Funciones CRUD respuestas
    public List<answersPerQuestion> getAnswers(int id) {
        ArrayList<answersPerQuestion> lista = new ArrayList<answersPerQuestion>();
        String sql = "select*from ae_respuestas where ID_PREGUNTA =? AND ID_ESTATUSRESP IN(0,1)";

        try {
            PreparedStatement sta = cn.getConnection().prepareStatement(sql);
            sta.setInt(1, id);
            ResultSet rs = sta.executeQuery();

            while (rs.next()) {
                answersPerQuestion model = new answersPerQuestion(rs.getInt("ID_RESPUESTA"), rs.getInt("ID_PREGUNTA"),
                        rs.getString("RESPUESTA"), rs.getInt("ID_ESTATUSRESP"), false);
                lista.add(model);
            }

        } catch (SQLException e) {
            lista = null;
        }
        return lista;
    }

    public answersPerQuestion getAnswer(int id) {
        answersPerQuestion model = null;
        String sql = "select * from ae_respuestas "
                + "where ID_RESPUESTA =?";
        try {
            PreparedStatement sta = cn.getConnection().prepareStatement(sql);
            sta.setInt(1, id);
            ResultSet rs = sta.executeQuery();

            while (rs.next()) {
                model = new answersPerQuestion(rs.getInt("ID_RESPUESTA"), rs.getInt("ID_PREGUNTA"),
                        rs.getString("RESPUESTA"), rs.getInt("ID_ESTATUSRESP"), false);
            }

        } catch (SQLException e) {
            model = null;
        }
        return model;
    }

    public boolean insertOrUpdateAnswer(answersPerQuestion model) {
        String sql = "call SVURS_CRUDAnswer(?,?,?,?)";
        try {
            PreparedStatement ps = cn.getConnection().prepareStatement(sql);
            ps.setInt(1, model.getId());
            ps.setString(2, model.getAnswer());
            ps.setInt(3, model.getStatus());
            ps.setInt(4, model.getIdQuestion());

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                model.setId(rs.getInt(1));
            }
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    public boolean changeAnswer(answersPerQuestion model) {
        String sql = "update ae_respuestas set ID_ESTATUSRESP = ? where ID_PREGUNTA = ? and ID_RESPUESTA = ?;";
        try {
            PreparedStatement ps = cn.getConnection().prepareStatement(sql);
            ps.setInt(1, model.getStatus());
            ps.setInt(2, model.getIdQuestion());
            ps.setInt(3, model.getId());
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    public boolean deleteAnswer(int id) {
        String sql = "call SVURS_CRUDAnswer(?,?,?,?)";
        answersPerQuestion model = this.getAnswer(id);

        if (model.getStatus() == 0) {
            model.setStatus(2);
        } else {
            model.setStatus(3);
        }
        try {
            PreparedStatement ps = cn.getConnection().prepareStatement(sql);
            ps.setInt(1, model.getId());
            ps.setString(2, model.getAnswer());
            ps.setInt(3, model.getStatus());
            ps.setInt(4, model.getIdQuestion());

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                model.setId(rs.getInt(1));
            }
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    public List<examsPerSecuencia> getSecuenciasPerExam(int id) {
        ArrayList<examsPerSecuencia> lista = new ArrayList<examsPerSecuencia>();
        String sql = "select ID_SECUENCIA, ID_CREAEXA from ae_examsec where ID_CREAEXA = ?";
        try {
            PreparedStatement sta = cn.getConnection().prepareStatement(sql);
            sta.setInt(1, id);
            ResultSet rs = sta.executeQuery();

            while (rs.next()) {
                examsPerSecuencia sc = new examsPerSecuencia(rs.getString(1), rs.getInt(2));
                lista.add(sc);
            }

        } catch (SQLException e) {
            lista = null;
        }
        return lista;
    }

    public boolean insertSecuenciaPerExam(int idexa, String idsecuencia) {
        String sql = "INSERT INTO ae_examsec(ID_SECUENCIA, ID_CREAEXA) VALUES(?,?)";

        try {
            PreparedStatement ps = cn.getConnection().prepareStatement(sql);
            ps.setString(1, idsecuencia);
            ps.setInt(2, idexa);
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    public List<examforview> getMyExamsEarmarked(String iduser) {
        ArrayList<examforview> lista = new ArrayList<examforview>();
        String sql = "select ex.ID_CREAEXA, ex.TITLE, ex.DESCRIPCION, ex.NUM_PREGUNTAS, ex.FECHA_CREACION, ex.FECHA_BAJA, "
                + "ex.CAL_MAX, ex.ID_USUARIO, ex.ID_ESTATUS, ex.ID_TIPOEXA, ex.IMAGE from ae_examsec es "
                + "inner join ae_secal sc ON es.ID_SECUENCIA = sc.ID_SECUENCIA "
                + "INNER JOIN ae_alumnos al ON sc.ID_ALUMNO = al.ID_ALUMNO "
                + "INNER JOIN ae_creaexa ex ON ex.ID_CREAEXA = es.ID_CREAEXA "
                + "WHERE al.ID_ALUMNO = ? AND NOT EXISTS (select*from ae_aplicacion ap where ap.ID_ALUMNO = al.ID_ALUMNO and ap.ID_CREAEXA=es.ID_CREAEXA)";

        try {
            PreparedStatement sta = cn.getConnection().prepareStatement(sql);
            sta.setString(1, iduser);
            ResultSet rs = sta.executeQuery();

            while (rs.next()) {
                examforview sc = new examforview();
                sc.setId(rs.getInt("ID_CREAEXA"));
                sc.setTitle(rs.getString("TITLE"));
                sc.setDescription(rs.getString("DESCRIPCION"));
                sc.setImage(rs.getString("IMAGE"));
                sc.setcalMax(rs.getFloat("CAL_MAX"));
                lista.add(sc);
            }

        } catch (SQLException e) {
            lista = null;
        }
        return lista;
    }

    private boolean isCorrectly(int idquestion, int idanswer) {
        String sql = "select ID_ESTATUSRESP from ae_respuestas where ID_PREGUNTA = ? and ID_RESPUESTA = ? and ID_ESTATUSRESP = 1;";
        boolean resp = false;
        try {
            PreparedStatement ps = cn.getConnection().prepareStatement(sql);
            ps.setInt(1, idquestion);
            ps.setInt(2, idanswer);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                resp = true;
            }
        } catch (SQLException e) {
            resp = false;
        }
        return resp;
    }

    public aplicationExam insertAnswersUser(JSONArray answers, String idstudent, int idexa) {
        String insertaplicacion = "Insert into ae_aplicacion(id_creaexa, id_alumno, puntaje,fecha_inicio, fecha_final) "
                + "values(?,?,?,?,?)";
        String insertdetail = "Insert into ae_detalleexamen(id_creaexa,id_alumno,id_pregunta,id_respuesta) values(?,?,?,?)";
        aplicationExam model = null;
        ArrayList<detailExam> details = new ArrayList<detailExam>();
        PreparedStatement ps;
        int idpregunta, idrespuesta;
        boolean iscorrecta;
        float note;
        String startDate = null, endDate = null; //Falta agregar horas y fecha de aplicacion

        try {
            exam modelexa = this.getExam(idexa);
            
            for (int i = 0; i < answers.length(); i++) {
                JSONObject obj = answers.getJSONObject(i);
                idpregunta = obj.getInt("idpregunta");
                idrespuesta = obj.getInt("idrespuesta");
                iscorrecta = isCorrectly(idpregunta, idrespuesta);
                detailExam detail = new detailExam(idexa, idstudent, idpregunta, idrespuesta, iscorrecta);
                details.add(detail);
            }
            //Contando respuestas correctas
            int n=0;
            for (int j = 0; j < details.size(); j++){
                if(details.get(j).getIsCorrect()) n++;
            }
            note = (modelexa.getNote() * n) / modelexa.getQuestions();
            model = new aplicationExam(idexa, idstudent, note, startDate, endDate, details);
            model.setNumQuestions(details.size());
            model.setNoteExam(modelexa.getNote());
            model.setNumQuestionsOK(n);
            
            //Insert in database aplicacion
            ps = cn.getConnection().prepareStatement(insertaplicacion);
            ps.setInt(1, idexa);
            ps.setString(2, idstudent);
            ps.setFloat(3, note);
            ps.setDate(4, null);
            ps.setDate(5, null);
            ps.executeUpdate();
            
            //Insert in database details
            for(detailExam d: details){
                ps = cn.getConnection().prepareStatement(insertdetail);
                ps.setInt(1, d.getIdExam());
                ps.setString(2, d.getIdStudent());
                ps.setInt(3, d.getIdQuestion());
                ps.setInt(4, d.getIdAnswer());
                ps.executeUpdate();
            }
                
        } catch (SQLException e) {
            return null;
        }
        return model;
    }
    
    public List<aplicationExam> getMyExamsResolved(String iduser) {
        ArrayList<aplicationExam> lista = new ArrayList<aplicationExam>();
        String sql = "select ex.ID_CREAEXA, ap.ID_ALUMNO, ap.puntaje, ex.CAL_MAX, ex.NUM_PREGUNTAS, 0 as NUM_PREGUNTASOK, ap.FECHA_INICIO, ap.FECHA_FINAL " +
                    "from ae_aplicacion ap INNER JOIN ae_creaexa ex ON ap.ID_CREAEXA = ex.ID_CREAEXA " +
                    "WHERE ap.ID_ALUMNO = ?";
        try {
            PreparedStatement sta = cn.getConnection().prepareStatement(sql);
            sta.setString(1, iduser);
            ResultSet rs = sta.executeQuery();

            while (rs.next()) {
                aplicationExam sc = new aplicationExam();
                sc.setIdExam(rs.getInt("ID_CREAEXA"));
                sc.setNote(rs.getFloat("puntaje"));
                sc.setNoteExam(rs.getFloat("CAL_MAX"));
                sc.setNumQuestions(rs.getInt("NUM_PREGUNTAS"));
                lista.add(sc);
            }

        } catch (SQLException e) {
            lista = null;
        }
        return lista;
    }

}
