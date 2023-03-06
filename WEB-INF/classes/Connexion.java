
package connect;

import java.sql.*;
public class Connexion
{
    public static Connection IConnex(){
    Connection conn = null;
    try {
        //étape 1: charger la classe de driver
        Class.forName("oracle.jdbc.driver.OracleDriver");
        //étape 2: créer l'objet de connexion
        conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl","star","society");       
    } catch (Exception e) { e.printStackTrace(); } 
      return conn;
    }
}




