package orm;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by Uliana on 01.08.2016.
 */
public class SigningFormSQL {
    public static SigningFormSQL variable;

    public static SigningFormSQL getVariable() {
        return variable;
    }

    public SigningFormSQL() {
    }

    public int authorization(String login, String password) {
        int id = 0;
        Statement stm = null;
        try {
            Connection con=UserDaoFactory.getVariable().getConnection();
            stm = con.createStatement();
            String sql = "SELECT ID FROM CUSTOMER WHERE LOGIN='" + login + "' AND PASSWORD='" + hashFunction(password) + "' " + "';";
            ResultSet res = stm.executeQuery(sql);
            if (res.next()) {
                id = res.getInt("ID");
                // logger.info("User with login " + login + " is authorized");
                System.out.println("User with login" + login + "is authorized");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            //logger.error("Error in method authorisation, detail: " + e.getMessage());
            System.out.println("Error in method authorization, detal:" + e.getMessage());
        } finally {
            try {
                UserDaoFactory.getVariable().closeConnection();
                stm.close();
            } catch (SQLException e) {
                e.printStackTrace();
                //logger.error("Error at finally part of method authorisation, detail: " + e.getMessage());
                System.out.println("Error in finally part pf method, detail:" + e.getMessage());
            }


        }
        return id;
    }

    public int registration( String login, String password) {
        int newId=0;
        Statement stm=null;
        try {
            Connection con=UserDaoFactory.getVariable().getConnection();
            stm=con.createStatement();
            String sql="SELECT FROM CUSTOMER WHERE LOGIN= '" + login +"';";
            ResultSet res=stm.executeQuery(sql);
            if(res.next()) {
                return 0;
            } else {
                sql="INSERT INTO CUSTOMER (LOGIN, PASSWORD) VALUES ('" + login + "', '" + hashFunction(password) + "');";
         int result= stm.executeUpdate(sql);
                if (result == 1) {
                    sql="SELECT ID FROM CUSTOMER WHERE LOGIN= '"+login+"'AND PASSWORD= '"+hashFunction(password)+"';";
                    res=stm.executeQuery(sql);
                    if(res.next()) {
                        newId=res.getInt("ID");
                                //logger.info("New user with login " + login + " is registered");
                        System.out.println("NEw user with login"+ login+"is registered");
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            //logger.error("Error in method registration, detail: " + e.getMessage());
            System.out.println("Error in method registration, detail:" + e.getMessage());
        }
        finally {
            try {
                UserDaoFactory.getVariable().closeConnection();
                stm.close();
            } catch (SQLException e) {
                e.printStackTrace();
                //logger.error("Error in method registration, detail: " + e.getMessage());
                System.out.println("Error in finally part of method registration, detail" + e.getMessage());
            }
        }
        return newId;
    }
    private String hashFunction(String password) {
        MessageDigest msg=null;
        byte[] bytes=new byte[0];
        try {
            msg=MessageDigest.getInstance("SHA-224");
            msg.reset();
            msg.update(password.getBytes());
            bytes=msg.digest();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();}

            BigInteger big=new BigInteger(1, bytes);
            String sha224=big.toString(16);
            while(sha224.length()<32) {
                sha224="0"+sha224;
            }
        return sha224;
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
            System.out.println("Error in method removeUSer, detail:" +e.getMessage());
        } finally {
            try {
                statement.close();
            } catch (SQLException e) {
                e.getLocalizedMessage();
                //logger.error("Error in finally part of method removeUser, detail: " + e.getMessage());
           System.out.println("Error in finally part of the method removeUser, detail:" + e.getMessage());
            }
        }
    }
    }

