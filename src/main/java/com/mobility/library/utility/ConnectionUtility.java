package com.mobility.library.utility;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionUtility {

    public static Connection getConnection()  throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException{
    	return getConnectionByProperties();
    }
    
    public static Connection getConnectionByProperties() throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException{
    	
        String url =""; //"jdbc:jtds:sqlserver://localhost:1433/GoLibrary;Instance=;";
        String userId = "sa";
        String password = "mssql@123";
       // System.out.println("Hello baby: "+databaseProperties.getDatabaseurl());
        Class.forName("net.sourceforge.jtds.jdbc.Driver").newInstance();
        return DriverManager.getConnection(url,userId,password);
    }
   

}
