package net.dao;


import java.sql.*;

public class ConnectionDB {
    
    static String db = "sistema_ae";
    static String login = "root";
    static String password = "root";
    static String url ="jdbc:mysql://localhost/" + db;
    
    Connection cn = null;
    
    public ConnectionDB(){
        try{
            Class.forName("com.mysql.jdbc.Driver");
            
            cn = DriverManager.getConnection(url,login,password);
            
            if(cn != null){
                System.out.println("Connecting database[" + cn + "] OK");
            }
        }catch(SQLException e){
            System.out.println("Connection exception " + e.getMessage());
        }catch(ClassNotFoundException e){
            System.out.println("Driver exception " + e.getMessage());
        }
    }
    
    public Connection getConnection(){
        return cn;
    }
    
    public void disconnect(){
        System.out.println("Closing database[" + cn + "] OK");
        if(cn != null){
            try{
                cn.close();
            } catch (SQLException e) {
                System.out.println(e);
            }
        }
    }
    
}
