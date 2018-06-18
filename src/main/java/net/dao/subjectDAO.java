package net.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import net.model.subjects;

public class subjectDAO {

    ConnectionDB cn;

    public subjectDAO(ConnectionDB _cn) {
        this.cn = _cn;
    }

    public boolean insert(subjects sc) {
        String sql = "call SVURS_CRUDSubject(?,?,?)";    
        try {
            PreparedStatement ps = cn.getConnection().prepareStatement(sql);
            ps.setString(1, "create");
            ps.setString(2, sc.getId());
            ps.setString(3, sc.getDescription());
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            return false;
        }
    }
    
    public boolean delete(String id){
        String sql = "call SVURS_CRUDSubject(?,?,?)";    
        try {
            PreparedStatement ps = cn.getConnection().prepareStatement(sql);
            ps.setString(1, "delete");
            ps.setString(2, id);
            ps.setString(3, "");
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            return false;
        }
    }
    public boolean update(subjects sc) {
        String sql = "call SVURS_CRUDSubject(?,?,?)";    
        try {
            PreparedStatement ps = cn.getConnection().prepareStatement(sql);
            ps.setString(1, "update");
            ps.setString(2, sc.getId());
            ps.setString(3, sc.getDescription());
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    public List<subjects> getAll() {
        ArrayList<subjects> lista = new ArrayList<subjects>();
        String sql = "call SVURS_LISTARCATALOGOS(?)";
        
        try {
            PreparedStatement sta = cn.getConnection().prepareStatement(sql);
            sta.setString(1, "subject");
            
            ResultSet rs = sta.executeQuery();

            while (rs.next()) {
                subjects sc = new subjects(rs.getString(1), rs.getString(2));
                lista.add(sc);
            }

        } catch (SQLException e) {
            lista = null;
        }
        return lista;
    }

}
