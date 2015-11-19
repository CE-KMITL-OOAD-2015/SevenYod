package mchat;
import org.omg.CORBA.Object;

import java.sql.Connection;
import java.sql.DriverManager;


public class Model {
    public static Connection getConnection(){
        Connection connection=null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection= DriverManager.getConnection("jdbc:mysql://localhost:3306/mchat","root","dbpass");

        } catch (Exception e) {
            e.printStackTrace();
        }
        return connection;
    }
}
