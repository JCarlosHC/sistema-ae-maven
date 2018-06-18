package net.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import net.model.school;

public class schoolDAO {

    ConnectionDB cn;

    public schoolDAO(ConnectionDB _cn) {
        this.cn = _cn;
    }

    public boolean insert(school sc) {
        String sql = "call SVURS_CRUDSchool(?,?,?,?)";    
        try {
            PreparedStatement ps = cn.getConnection().prepareStatement(sql);
            ps.setString(1, "create");
            ps.setInt(2, 0);
            ps.setString(3, sc.getDescription());
            ps.setString(4, sc.getAcronym());
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            return false;
        }
    }
    
    public boolean delete(int id){
        String sql = "call SVURS_CRUDSchool(?,?,?,?)";    
        try {
            PreparedStatement ps = cn.getConnection().prepareStatement(sql);
            ps.setString(1, "delete");
            ps.setInt(2, id);
            ps.setString(3, "");
            ps.setString(4, "");
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            return false;
        }
    }
    public boolean update(school sc) {
        String sql = "call SVURS_CRUDSchool(?,?,?,?)";    
        try {
            PreparedStatement ps = cn.getConnection().prepareStatement(sql);
            ps.setString(1, "update");
            ps.setInt(2, sc.getId());
            ps.setString(3, sc.getDescription());
            ps.setString(4, sc.getAcronym());
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    public List<school> getAll() {
        ArrayList<school> lista = new ArrayList<school>();
        String sql = "call SVURS_LISTARCATALOGOS(?)";
        
        try {
            PreparedStatement sta = cn.getConnection().prepareStatement(sql);
            sta.setString(1, "school");
            
            ResultSet rs = sta.executeQuery();

            while (rs.next()) {
                school sc = new school(rs.getInt(1), rs.getString(2), rs.getString(3));
                lista.add(sc);
            }

        } catch (SQLException e) {
            lista = null;
        }
        return lista;
    }

}
