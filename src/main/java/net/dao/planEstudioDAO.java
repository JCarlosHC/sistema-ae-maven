package net.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import net.model.planEstudio;

public class planEstudioDAO {

    ConnectionDB cn;

    public planEstudioDAO(ConnectionDB _cn) {
        this.cn = _cn;
    }

    public boolean insert(planEstudio pe) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String sql = "call SVURS_CRUDPlan(?,?,?,?,?)";    
        try {
            PreparedStatement ps = cn.getConnection().prepareStatement(sql);
            ps.setString(1, "create");
            ps.setInt(2, 0);
            ps.setString(3, pe.getDescription());
            ps.setString(4, format.format(pe.getStartDate()));
            ps.setString(5, format.format(pe.getEndDate()));
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            return false;
        }
    }
    
    public boolean delete(int id){
        String sql = "call SVURS_CRUDPlan(?,?,?,?,?)";    
        try {
            PreparedStatement ps = cn.getConnection().prepareStatement(sql);
            ps.setString(1, "delete");
            ps.setInt(2, id);
            ps.setString(3, "");
            ps.setString(4, "1900-01-01");
            ps.setString(5, "1900-01-01");
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            return false;
        }
    }
    public boolean update(planEstudio pe) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String sql = "call SVURS_CRUDPlan(?,?,?,?,?)";    
        try {
            PreparedStatement ps = cn.getConnection().prepareStatement(sql);
            ps.setString(1, "update");
            ps.setInt(2, pe.getId());
            ps.setString(3, pe.getDescription());
            ps.setString(4, format.format(pe.getStartDate()));
            ps.setString(5, format.format(pe.getEndDate()));
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    public List<planEstudio> getAll() {
        ArrayList<planEstudio> lista = new ArrayList<planEstudio>();
        String sql = "call SVURS_LISTARCATALOGOS(?)";
        
        try {
            PreparedStatement sta = cn.getConnection().prepareStatement(sql);
            sta.setString(1, "plan");
            
            ResultSet rs = sta.executeQuery();

            while (rs.next()) {
                planEstudio pes = new planEstudio(rs.getInt(1), rs.getString(2), rs.getDate(3), rs.getDate(4));
                lista.add(pes);
            }

        } catch (SQLException e) {
            lista = null;
        }
        return lista;
    }

}
