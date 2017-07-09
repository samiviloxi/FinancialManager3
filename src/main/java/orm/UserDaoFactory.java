package orm;

import java.sql.*;
import java.util.HashSet;
import java.util.Iterator;

/**
 * Created by Uliana on 19.07.2016.
 */
public class UserDaoFactory implements IDaoFactory {
    private static UserDaoFactory variable=new UserDaoFactory();
    private String url="jdbc:sqlite:database.db";
    private final String DRIVER="org.sqlite.JDBC";
    private Connection connection = null;
    //private Logger logger = LoggerFactory.getLogger(DBHelper.class);
    public static UserDaoFactory getVariable() {
        return variable;
    }
/* Method returns single instance of class UserDaoFacory*/

    public UserDaoFactory() {
    try {
        Class.forName(DRIVER);
        firstConnection();

    } catch (ClassNotFoundException e) {
        e.printStackTrace();
    }
}
    public Connection getConnection() throws SQLException {
        return connection=DriverManager.getConnection(url);
    }
    public void closeConnection() throws SQLException{
        if(connection != null) {
            connection.close();
        }
    }

    public <E extends User> iGenericDao getUserDao(Connection conn) {
        return new UserDaoImpl(conn);
    }


/**
 * If database not exists the tables which we used,
 * we create it in this method
 */
private void firstConnection () {
    try {
        Connection connection=getConnection();
        Statement statement=connection.createStatement();

        String sql="SELECT name FROM sqlite_master WHERE type='table' AND name in ('CUSTOMER', 'RECORD', 'ACCOUNT', 'CATEGORY');";
        ResultSet resultSet=statement.executeQuery(sql);

        HashSet<String> set = new HashSet<>(4);
        set.add("CUSTOMER");
        set.add("RECORD");
        set.add("ACCOUNT");
        set.add("CATEGORY");
        while(resultSet.next()){
            //if no result creat tables

            set.remove(resultSet.getString(1));

//            if(resultSet.getInt(1)!=4) {
//                //create Customer table
//                DaoUtil.getInstance().createTable(connection);
//                DaoUtil.getInstance().createRecordTable(connection);
//                DaoUtil.getInstance().createAccountTable(connection);
//                DaoUtil.getInstance().createCategoryTable(connection);
//
//            }
        }
        Iterator<String> iterator = set.iterator();
        while(iterator.hasNext()) {
            DaoUtil.createTable(iterator.next(), connection);
        }
        statement.close();
    } catch (SQLException e) {
        e.printStackTrace();
        //logger.error("Error in method firstConnection, detail: " + e.getMessage());
    }

}
}
