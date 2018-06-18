package net.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import net.model.career;

public class careerDAO {

    ConnectionDB cn;

    public careerDAO(ConnectionDB _cn) {
        this.cn = _cn;
    }

    public boolean insert(career sc) {
        String sql = "call SVURS_CRUDCareer(?,?,?)";    
        try {
            PreparedStatement ps = cn.getConnection().prepareStatement(sql);
            ps.setString(1, "create");
            ps.setInt(2, 0);
            ps.setString(3, sc.getDescription());
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            return false;
        }
    }
    
    public boolean delete(int id){
        String sql = "call SVURS_CRUDCareer(?,?,?)";    
        try {
            PreparedStatement ps = cn.getConnection().prepareStatement(sql);
            ps.setString(1, "delete");
            ps.setInt(2, id);
            ps.setString(3, "");
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            return false;
        }
    }
    public boolean update(career sc) {
        String sql = "call SVURS_CRUDCareer(?,?,?)";    
        try {
            PreparedStatement ps = cn.getConnection().prepareStatement(sql);
            ps.setString(1, "update");
            ps.setInt(2, sc.getId());
            ps.setString(3, sc.getDescription());
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    public List<career> getAll() {
        ArrayList<career> lista = new ArrayList<career>();
        String sql = "call SVURS_LISTARCATALOGOS(?)";
        
        try {
            PreparedStatement sta = cn.getConnection().prepareStatement(sql);
            sta.setString(1, "career");
            
            ResultSet rs = sta.executeQuery();

            while (rs.next()) {
                career sc = new career(rs.getInt(1), rs.getString(2));
                lista.add(sc);
            }

        } catch (SQLException e) {
            lista = null;
        }
        return lista;
    }

}
