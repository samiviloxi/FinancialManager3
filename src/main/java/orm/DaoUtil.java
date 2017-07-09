package orm;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by Uliana on 19.07.2016.
 */
public class DaoUtil {
    private static DaoUtil instance=new DaoUtil();
    public static DaoUtil getInstance() {
        return instance;
    }


    public static void createTable(String table_name, Connection conn) {
        switch (table_name) {
            case "CUSTOMER" :
                createCustomerTable(conn);
                break;
            case "RECORD" :
                createRecordTable(conn);
                break;
            case "ACCOUNT" :
                createAccountTable(conn);
                break;
            case "CATEGORY" :
                createCategoryTable( conn);
                break;
            default:
                break;
        }


    }

    public static void createCustomerTable(Connection conn){
        Statement stm=null;
        try{
            stm=conn.createStatement();
            String sql="CREATE TABLE IF NOT EXISTS CUSTOMER" +
                    "(ID INTEGER PRIMARY KEY NOT NULL ON CONFLICT FAIL," +
                    "NAME TEXT           ,"+ //NOT NULL
                    "ACCOUNTS INTEGER        ,"+
                    " LOGIN TEXT UNIQUE ON CONFLICT FAIL        NOT NULL,"+
                    "PASSWORD TEXT       NOT NULL)";

stm.execute(sql);
        } catch (SQLException e) {
            e.getLocalizedMessage();
            e.printStackTrace();


        }

    }
    public static void createAccountTable(Connection conn){
        Statement stm=null;
        try {
            stm=conn.createStatement();
            String sql="CREATE TABLE IF NOT EXISTS ACCOUNT" +
                    "(ACCOUNTID  INTEGER  PRIMARY KEY ON CONFLICT FAIL AUTOINCREMENT," +
                    "ACCOUNTNUMBER TEXT NOT NULL UNIQUE ON CONFLICT FAIL                 ," +
                    "DESCRIPTION TEXT                    ," +
                   "USERID INTEGER REFERENCES USERS (ID) ON DELETE NO ACTION," +
                    "BALANCE DECIMAL                  )";
       stm.execute(sql);
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
    public static void createRecordTable(Connection conn){
        Statement stm=null;
        try{
            stm=conn.createStatement();
            String sql="CREATE TABLE IF NOT EXISTS RECORD" +
                    "(RECORDID INTEGER PRIMARY KEY ON CONFLICT FAIL  AUTOINCREMENT, " +
                    "ACCOUNTID INTEGER   REFERENCES ACCOUNT (ACCOUNTID) ON DELETE NO ACTION,"+
                     "DESCRIPTION TEXT              ,"+
                    "INCOMMING TEXT                 , "+
                    "SUMM NUMBER           NOT NULL,"+
                    "CATEGORY INTEGER REFERENCES CATEGORY (ID) ON DELETE NO ACTION                ," +
                    "DATETIME DATETIME DEFAULT (CURRENT_TIMESTAMP)          )";
        stm.execute(sql);
        } catch (SQLException e) {e.printStackTrace();}
    }

    public static  void  createCategoryTable (Connection conn) {
        Statement stm=null;
        try {
            stm=conn.createStatement();
            String sql="CREATE TABLE IF NOT EXISTS CATEGORY ("
                    + " ID INTEGER NOT NULL PRIMARY KEY ON CONFLICT FAIL ,"
                    + " NAME TEXT NOT NULL UNIQUE ON CONFLICT FAIL);";
            stm.execute(sql);
            sql="INSERT INTO CATEGORY (NAME) VALUES ('еда');";
            stm.executeUpdate(sql);
            sql="INSERT INTO CATEGORY (NAME) VALUES ('развлечения');";
            stm.executeUpdate(sql);
            sql="INSERT INTO CATEGORY (NAME) VALUES ('ЖКХ');";
            stm.executeUpdate(sql);
            sql="INSERT INTO CATEGORY (NAME) VALUES ('обучение');";
            stm.executeUpdate(sql);


        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void closeConnection(Connection conn) {
try {
    conn.close();
} catch (SQLException e) {
    e.printStackTrace();
    // logger.error("Error in method firstConnection, detail: " + e.getMessage());
}
    }
}
