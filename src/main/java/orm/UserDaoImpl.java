package orm;

import com.sun.istack.internal.Nullable;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Uliana on 19.07.2016.
 */
public class UserDaoImpl implements iGenericDao<User> {
    private Connection conn;
    public UserDaoImpl(Connection conn) {
        this.conn=conn;
    }
    public void save(User owner) {
try {
    //Statement stm=conn.createStatement();
//String sql="INSERT INTO CUSTOMER (ID, NAME, ACCOUNTS, LOGIN, PASSWORD)" +
//"VALUES (" + owner.getId()+", '" + owner.getuName()+"', " +owner.getAccountAmount()+", '"+owner.getLogin()+"', "+ " '"+owner.getPassword()+"' "+")";
    String sql = "INSERT INTO customer (id, login, password, name, accounts) VALUES (?, ?, ?, ?, ?) ";
    PreparedStatement stm=conn.prepareStatement(sql);
    stm.setInt(1, owner.getId());
    stm.setString(2, owner.getLogin());
    stm.setString(3, owner.getPassword());
    stm.setString(4, owner.getuName());
    stm.setInt(5, owner.getAccountAmount());
    stm.executeUpdate();
    stm.close();
} catch (SQLException e) {
    e.printStackTrace();
}
    }

    public void remove(User owner) {
        try {
            Statement stm = conn.createStatement();
String sql="DELETE FROM CUSTOMER WHERE ID=" +owner.getId();
stm.executeUpdate(sql);
            stm.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void update(User owner) {
        try {
            Statement stm=conn.createStatement();
            String sql="UPDATE CUSTOMER SET NAME='"+ owner.getuName() +"',ACCOUNTS" + owner.getAccountAmount()+ "WHERE ID=" + owner.getId();
        stm.executeUpdate(sql);
            stm.close();
        }catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public @Nullable User findById(int id) {
        User owner=null;
        try{
Statement stm=conn.createStatement();
            String sql="SELECT * FROM CUSTOMER WHERE ID="+id;
            ResultSet rs=stm.executeQuery(sql);
owner=new User(rs.getInt("ACCOUNT"), rs.getString("LOGIN"), rs.getString("NAME"), rs.getString("PASSWORD"), rs.getInt(id));
        stm.executeUpdate(sql);
        stm.close();
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return owner;
    }

    public List<User> getAll() {
        List<User> users=new ArrayList<User>();
try{
    Statement stm=conn.createStatement();
    String sql="SELECT* FROM CUSTOMER WHERE";
ResultSet rs=stm.executeQuery(sql);
    while(rs.next()) {
User owner=new User(rs.getInt("ACCOUNT"), rs.getString("LOGIN"), rs.getString("NAME"), rs.getString("PASSWORD"), rs.getInt("ID"));
    users.add(owner);
    }stm.executeUpdate(sql);
        stm.close();
} catch (SQLException e) {e.printStackTrace();}
        return users;
    }
}
