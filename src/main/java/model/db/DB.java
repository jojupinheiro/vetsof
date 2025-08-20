
package model.db;
import application.Principal;
import java.sql.*;

/*Classe que conecta no banco de dados
 *
 * @author juliano
 */
public class DB {
    
    public static Connection getConnection(){
        
        try {
            String stringConnection = "jdbc:mysql://" + Principal.ip + ":3306/vetsof";
            System.out.println(stringConnection);
            Connection con = DriverManager.getConnection(stringConnection,Principal.usuarioBanco,Principal.senhaBanco);
            return con;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public static void closeStatement(Statement st){
        try {
            if (st != null){
                st.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static void closeResultSet(ResultSet rs){
        try {
            if (rs != null){
                rs.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
}
