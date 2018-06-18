package net.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import net.model.secuencia;
import net.model.studentsPerSecuencia;

public class secuenciaDAO {

    ConnectionDB cn;

    public secuenciaDAO(ConnectionDB cn) {
        this.cn = cn;
    }

    public boolean insert(secuencia model) {
        String sql = "call SVURS_CRUDSecuencia(?,?,?,?,?,?)";
        try {
            PreparedStatement ps = cn.getConnection().prepareStatement(sql);
            ps.setString(1, "create");
            ps.setString(2, model.getId());
            ps.setString(3, model.getIdsubject());
            ps.setInt(4, model.getIdstatus());
            ps.setString(5, model.getDescripcion());
            ps.setInt(6, model.getIduser());
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    public boolean delete(String id, int iduser) {
        String sql = "call SVURS_CRUDSecuencia(?,?,?,?,?,?)";
        try {
            PreparedStatement ps = cn.getConnection().prepareStatement(sql);
            ps.setString(1, "delete");
            ps.setString(2, id);
            ps.setString(3, "");
            ps.setInt(4, 0);
            ps.setString(5, "");
            ps.setInt(6, iduser);
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    public boolean update(secuencia model) {
        String sql = "call SVURS_CRUDSecuencia(?,?,?,?,?,?)";
        try {
            PreparedStatement ps = cn.getConnection().prepareStatement(sql);
            ps.setString(1, "update");
            ps.setString(2, model.getId());
            ps.setString(3, model.getIdsubject());
            ps.setInt(4, model.getIdstatus());
            ps.setString(5, model.getDescripcion());
            ps.setInt(6, model.getIduser());
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    public List<secuencia> getMisSecuencias(int id) {
        ArrayList<secuencia> lista = new ArrayList<secuencia>();
        String sql = "select s.ID_SECUENCIA, s.ID_MATERIA, s.ID_ESTATUS, s.ID_USUARIO, s.DESCRIPCION, "
                + "(select count(*) from ae_secal where ID_SECUENCIA = s.ID_SECUENCIA) as TOTALSTUDENTS "
                + "from ae_secuencias s where ID_USUARIO = ?";

        try {
            PreparedStatement sta = cn.getConnection().prepareStatement(sql);
            sta.setInt(1, id);

            ResultSet rs = sta.executeQuery();

            while (rs.next()) {
                secuencia model = new secuencia(rs.getString(1), rs.getString(2), rs.getInt(3), rs.getInt(4), rs.getString(5));
                model.setTotalstudents(rs.getInt("TOTALSTUDENTS"));
                lista.add(model);
            }

        } catch (SQLException e) {
            lista = null;
        }
        return lista;
    }

    public List<secuencia> getAll() {
        ArrayList<secuencia> lista = new ArrayList<secuencia>();
        String sql = "select ID_SECUENCIA, ID_MATERIA, ID_ESTATUS, ID_USUARIO, DESCRIPCION from ae_secuencias";

        try {
            PreparedStatement sta = cn.getConnection().prepareStatement(sql);
            ResultSet rs = sta.executeQuery();

            while (rs.next()) {
                secuencia model = new secuencia(rs.getString(1), rs.getString(2), rs.getInt(3), rs.getInt(4), rs.getString(5));
                lista.add(model);
            }

        } catch (SQLException e) {
            lista = null;
        }
        return lista;
    }

    public secuencia getSecuencia(String idsecuencia, int iduser) {
        String sql = "select ID_SECUENCIA, ID_MATERIA, ID_ESTATUS, ID_USUARIO, DESCRIPCION "
                + "from ae_secuencias where ID_USUARIO = ? AND ID_SECUENCIA = ?";
        secuencia model = new secuencia();
        try {
            PreparedStatement sta = cn.getConnection().prepareStatement(sql);
            sta.setInt(1, iduser);
            sta.setString(2, idsecuencia);
            ResultSet rs = sta.executeQuery();

            while (rs.next()) {
                model = new secuencia(rs.getString(1), rs.getString(2), rs.getInt(3), rs.getInt(4), rs.getString(5));
            }

        } catch (SQLException e) {
            model = null;
        }

        return model;
    }
    
    public List<studentsPerSecuencia> getStudents(String idsecuencia, int iduser) {
        ArrayList<studentsPerSecuencia> lista = new ArrayList<studentsPerSecuencia>();
        String sql = "select sa.ID_ALUMNO, sa.ID_SECUENCIA, sa.ID_ESTATUS from ae_secal sa " +
                    "INNER JOIN ae_secuencias se ON se.ID_SECUENCIA = sa.ID_SECUENCIA " +
                    "WHERE se.ID_USUARIO = ? and sa.ID_SECUENCIA = ? and sa.ID_ESTATUS = 1;";
        
        try {
            PreparedStatement sta = cn.getConnection().prepareStatement(sql);
            sta.setInt(1, iduser);
            sta.setString(2, idsecuencia);
            ResultSet rs = sta.executeQuery();

            while (rs.next()) {
                studentsPerSecuencia model = new studentsPerSecuencia(rs.getString(1), rs.getString(2), rs.getInt(3));
                lista.add(model);
            }

        } catch (SQLException e) {
            lista = null;
        }

        return lista;
    }
    
    public boolean insertStudent(studentsPerSecuencia model) {
        String sql = "INSERT INTO ae_secal(ID_ALUMNO,ID_SECUENCIA, ID_ESTATUS) VALUES(?,?,?)";
        try {
            PreparedStatement ps = cn.getConnection().prepareStatement(sql);
            ps.setString(1, model.getIdstudent());
            ps.setString(2, model.getIdsecuencia());
            ps.setInt(3, model.getIdstatus());
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            return false;
        }
    }
    
    public boolean deleteStudent(studentsPerSecuencia model){
        String sql = "UPDATE ae_secal set id_estatus = ? where id_alumno = ? and id_secuencia = ?";
        try {
            PreparedStatement ps = cn.getConnection().prepareStatement(sql);
            ps.setInt(1, model.getIdstatus());
            ps.setString(2, model.getIdstudent());
            ps.setString(3, model.getIdsecuencia());
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            return false;
        }        
    }
}
