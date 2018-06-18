package net.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import net.model.student;

public class studentDAO {
    ConnectionDB cn;

    public studentDAO(ConnectionDB cn) {
        this.cn = cn;
    }
    
    public List<student> getAll() {
        ArrayList<student> lista = new ArrayList<student>();
        String sql = "SELECT a.ID_ALUMNO, a.NOMBRE, a.APE_PATERNO, a.APE_MATERNO, a.CORREO, a.TELEFONO, p.ID_PLANEST, " +
                "e.ID_ESCUELA, c.ID_CARRERA, s.ID_ESTATUS FROM ae_alumnos a " +
                "INNER JOIN ae_planest p ON p.ID_PLANEST = a.ID_PLANEST " +
                "INNER JOIN ae_escuelas e ON e.ID_ESCUELA = a.ID_ESCUELA " +
                "INNER JOIN ae_carreras c ON c.ID_CARRERA = a.ID_CARRERA " +
                "INNER JOIN ae_estatus s ON s.ID_ESTATUS = a.ID_ESTATUS WHERE s.DESCRIPCION = 'Activo';";
        try {
            PreparedStatement sta = cn.getConnection().prepareStatement(sql);
            ResultSet rs = sta.executeQuery();

            while (rs.next()) {
                student model = new student(rs.getString(1), rs.getString(2), rs.getString(3),rs.getString(4),rs.getString(5),
                        rs.getString(6),rs.getInt(7),rs.getInt(8), rs.getInt(9), rs.getInt(10));
                lista.add(model);
            }

        } catch (SQLException e) {
            lista = null;
        }
        return lista;
    }
    public boolean insert(student model) {
        String sql = "call SVURS_RegisterStudent(?,?,?,?,?,?,?,?,?,?,?)";    
        try {
            PreparedStatement ps = cn.getConnection().prepareStatement(sql);
            ps.setString(1, model.getId());
            ps.setString(2, model.getFirstname());
            ps.setString(3, model.getPsurname());
            ps.setString(4, model.getMsurname());
            ps.setString(5, model.getEmail());
            ps.setString(6, model.getPhone());
            ps.setString(7, model.getPassword());
            ps.setInt(8, model.getId_planest());
            ps.setInt(9, model.getId_school());
            ps.setInt(10, model.getId_career());
            ps.setInt(11, model.getId_status());
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            return false;
        }
    }
    public boolean update(student model) {
        String sql = "UPDATE ae_alumnos SET NOMBRE=?, APE_PATERNO=?, APE_MATERNO=?, CORREO=?, TELEFONO=?, " +
                    "ID_PLANEST=?, ID_ESCUELA=?, ID_CARRERA=? WHERE ID_ALUMNO=?";    
        try {
            PreparedStatement ps = cn.getConnection().prepareStatement(sql);
            ps.setString(1, model.getFirstname());
            ps.setString(2, model.getPsurname());
            ps.setString(3, model.getMsurname());
            ps.setString(4, model.getEmail());
            ps.setString(5, model.getPhone());
            ps.setInt(6, model.getId_planest());
            ps.setInt(7, model.getId_school());
            ps.setInt(8, model.getId_career());
            ps.setString(9, model.getId());
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            return false;
        }
    }
    public boolean delete(String id){
        String sql = "UPDATE ae_alumnos SET ID_ESTATUS = 0 WHERE ID_ALUMNO=?";     
        try {
            PreparedStatement ps = cn.getConnection().prepareStatement(sql);
            ps.setString(1, id);
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            return false;
        }
    }
}
