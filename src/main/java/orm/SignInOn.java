package orm;

import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.util.logging.Logger;

/**
 * Created by Uliana on 02.12.2016.
 */
public class SignInOn {
    private static SignInOn instance = new SignInOn();
    // private static Logger logger=LoggerFactory.getLogger(SignInOn.class);

    public static SignInOn getInstance() {
        return instance;
    }

    private SignInOn() {
    }

    /* Method to authorize user
    param login
    param password
    return String of ligin user, else null
     */

    public int authorisation(String login, String password) {
        int authLoginID = 0;
        Statement statement = null;
        try {
            Connection connection = UserDaoFactory.getVariable().getConnection();
            statement = connection.createStatement();
            String sql = "SELECT ID FROM CUSTOMER WHERE LOGIN ='" + login + "'AND PASSWORD = '" + hashing(password) + "';";
            ResultSet res = statement.executeQuery(sql);
            if (res.next()) {
                authLoginID = res.getInt(1);
                //logger.info("User with login" + login + " is authorized");
System.out.println(" User with login" + login + " is authorized");
            }
        } catch (SQLException e) {
            e.getLocalizedMessage();
            // logger.error("Error in method authorisation, detail: " + e.getMessage());

        } finally {
            try {
                UserDaoFactory.getVariable().closeConnection();
                statement.close();
            } catch (SQLException e) {
                e.getLocalizedMessage();
                //logger.error("Error at finally part of method authorisation, detail: " + e.getMessage());
System.out.println("Error at finaly part of method authorization, detail" + e.getMessage());
            }
        }
        return authLoginID;
    }

    /* Method to register new user
    param login
    param password
    Return String of login new user else null
     */

    public  int registration (String login, String password, String name)throws Exception {
        int newLoginID=0;
        Statement statement=null;
         try {
             Connection connection=UserDaoFactory.getVariable().getConnection();
             statement=connection.createStatement();
             String sql_select="SELECT ID FROM CUSTOMER WHERE LOGIN = '" + login + "';";
             ResultSet resultSet=statement.executeQuery(sql_select);
             if(resultSet.next()) {
                 return 0;
             } else {
                 String sql = "INSERT INTO customer (login, password, name) VALUES (?, ?, ?) ";
                 PreparedStatement st = connection.prepareStatement(sql);

                 st.setString(1, login);
                 st.setString(2, hashing(password));
                 st.setString(3, name);
                 //st.execute();
                 //sql="INSERT INTO CUSTOMER (LOGIN, PASSWORD, NAME) VALUES ('" +login + "'," +"'"+ hashing(password) + "','"+ name+"');";
//                 ResultSet res=st.executeQuery();
                 st.execute();
                // if(res==1) {
                  //   sql="SELECT CUSTOMERID FROM CUSTOMER WHERE LOGIN='" + login + "' AND PASSWORD = '" + hashing(password) + "';";
                     resultSet=statement.executeQuery(sql_select);
//                 if(res.next()) {
//                     int id = res.getInt(1);
                     if(resultSet.next()) {
                         newLoginID=resultSet.getInt(1);
                         //logger.info("New user with login " + login + " is registered");
                     }
//                 }
             }
         } catch (SQLException e) {
            e.getStackTrace();
             // logger.error("Error in method registration, detail: " + e.getMessage());
             throw e;

         }finally {
             try {
                 UserDaoFactory.getVariable().closeConnection();
                 statement.close();
             } catch (SQLException e) {
                 e.getLocalizedMessage();
                // logger.error("Error at finally part of method registration, detail: " + e.getMessage());
             }
         }
        return newLoginID;




    }

    /* Method hashing string SHA-224 algorythm
        retirns String of hashing string

         */
    private String hashing(String text) {
        MessageDigest md=null;
        byte [] hashingBytes=new byte[0];
        try {
            md=MessageDigest.getInstance("SHA-224");
            md.reset();
            md.update(text.getBytes());
            hashingBytes=md.digest();

        } catch (NoSuchAlgorithmException e) {
            e.getLocalizedMessage();
        }
        BigInteger bi =new BigInteger(1, hashingBytes);
        String sha_224=bi.toString(16);
        while (sha_224.length()<32) {
            sha_224="0" +sha_224;
                    }
        return  sha_224;
    }

    public void removeUser(int userID) {
        Statement statement=null;
         try {
             Connection connection=UserDaoFactory.getVariable().getConnection();
             statement=connection.createStatement();
             String sql="DELETE FROM USER WHERE ID=" + userID+ ";";
             if(statement.executeUpdate(sql)>0) {
                 System.out.println ("\"User with userID: \"+ userID+ \" was deleted.\" ");
                // logger.info("User with userID: "+ userID+ " was deleted.");
             }
             UserDaoFactory.getVariable().closeConnection();
         } catch (SQLException e) {
             e.getLocalizedMessage();
             //logger.error("Error in method removeUser, detail: " + e.getMessage());
         } finally {
             try {
                 statement.close();
             } catch (SQLException e) {
                 e.getLocalizedMessage();
                 //logger.error("Error in finally part of method removeUser, detail: " + e.getMessage());
             }
         }
    }
}
