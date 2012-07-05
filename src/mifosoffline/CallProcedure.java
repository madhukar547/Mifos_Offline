/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mifosoffline;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author NagaSudheer
 */
public class CallProcedure {

   static  String status = "SUCCESSFULLY CALLED THE PROCEDURE";
    public static String execProc()  {

        
        Connection conn = DataRetrieve_Remote.getDBConnection();
        if (conn != null) {
            try {
                String sql ="call Monthly_PDemand()";
                CallableStatement cs = conn.prepareCall(sql);
                ResultSet resultSet =cs.executeQuery();
                if (resultSet==null) {
                    status = "Procedure Calling is Failed";
                }
            } catch (SQLException e) {
                System.err.println(e);               
            }
            finally{
                try {
                    conn.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        }
       return status;
    }
}