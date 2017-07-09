package orm;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.jar.Pack200;

/**
 * Created by Uliana on 20.07.2016.
 */
public class AccountDaoImpl implements iGenericDao<Account> {
    private Connection conn;
    private int userID;
    public AccountDaoImpl(Connection conn, int userID)
    {
        this.conn=conn;
        this.userID=userID;
    }
    public void save(Account account) {
        try {
          //  Statement stm = conn.createStatement();
            /*String sql = "INSERT INTO ACCOUNT( ACCOUNTNUMBER, DESCRIPTION,  USERID, BALANCE)" +
                    "VALUES (" +"'"+ account.getaNumber()+"'" + ",'" + account.getaDescription() + "'," + account.getuserID() + "," + account.getaBalance() + ")";
            */

            String sql="INSERT INTO account (ACCOUNTNUMBER, DESCRIPTION,  USERID, BALANCE) VALUES (?, ?, ?, ?)";
            PreparedStatement stm=conn.prepareStatement(sql);
            stm.setString(1, account.getaNumber());
            stm.setString(2, account.getaDescription());
            stm.setInt(3, account.getUserID());
            stm.setString(4, account.getaBalance().toString());
            if (stm.executeUpdate()>0) {
                //logger.infor("New account with number:"+account.getaNumber()+ "added");
                sql = "SELECT ACCOUNTID FROM ACCOUNT WHERE ACCOUNTNUMBER= '" + account.getaNumber() + "';";
Statement st=conn.createStatement();
                ResultSet resultSet = st.executeQuery(sql);
                if (resultSet.next()) {
                    account.setaId(resultSet.getInt("ACCOUNTID"));
                    //logger.info("Object Account with number:" + account.getaNumber()+ "set id");

                } else {
                    //logger.error("New account with number :" + account.getaNumber()+ "not added");
                    System.out.println("Account not added");
                }
                stm.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            //logger.error("Error in method save, detail: " + e.getMessage());}

        }
    }

    public void remove(Account account) {
        try {


            Statement stm = conn.createStatement();
            String sql="DELETE FROM ACCOUNT WHERE ACCOUNTID=" +account.getaId()+";";
            if(stm.executeUpdate(sql)>0) {
                //logger.info("Account with number:" + account.getaNumber()+"deleted");
                System.out.println("Account deleted");

            } else{
                //logger.error("Account with number:" + account.getaNumber()+ "is not deleted");
                System.out.println("Account not deleted");

            }

            stm.close();
        } catch (SQLException e) {
            e.printStackTrace();
            //loggr.error("Error in method remove, getail" + e.getMessage());
        }

    }

    public void update(Account account) {
       TotalAmount totalAmount=new TotalAmount();
        String sql;
        try {

            //String sql="UPDATE ACCOUNT SET ACCOUNTNUMBER=" + account.getaNumber()+",DESCRIPTION= '" + account.getaDescription()+ "'"+",BALANCE=" + totalAmount.getAccountAmount(account)+ " WHERE ACCOUNTID= " + account.getaId()+";";
           sql="UPDATE account SET ACCOUNTNUMBER=?, DESCRIPTION=?, BALANCE=? WHERE ACCOUNTID=?";
            PreparedStatement stm=conn.prepareStatement(sql);
          //  PreparedStatement preparedStatement=conn.prepareStatement(sql);
           stm.setString(1, account.getaNumber());
            stm.setString(2, account.getaDescription());
           // BigDecimal amount=totalAmount.getAccountAmount(account);
            stm.setString(3, totalAmount.getAccountAmount(account).toString());
          stm.setInt(4, account.getaId());
           if( stm.executeUpdate(sql)>0) {
               //logger.info("Account with number: " + fin.getNumber() + " updated");
               System.out.println("Account updted");
           } else {
               // logger.error("Account with number: " + fin.getNumber() + " not updated");
               System.out.println("Account is not updated");
           }

        }catch (SQLException e) {
           // logger.error("Error in method update, detail: " + e.getMessage());
            e.printStackTrace();
        }

    }

    public Account findById(int id) {
        Account account=null;
        try{
            Statement stm=conn.createStatement();
            String sql="SELECT * FROM ACCOUNT WHERE ACCOUNTID="+id;
            ResultSet rs=stm.executeQuery(sql);
            if(rs.next()) {
                sql="SELECT * FROM RECORD WHERE ACCOUNTID=" + id+";";
                ResultSet resultSet=stm.executeQuery(sql);
                List<Record> records=new ArrayList<>();
                while(resultSet.next()) {
                    sql="SELECT NAME FROM CATEGORY WHERE ID=" +resultSet.getInt("ID");
                    ResultSet result=stm.executeQuery(sql);
                    String categoryName=null;
                    if(result.next()) {
                        categoryName=result.getString(2);

                    }
                    records.add(new Record(resultSet.getInt("RECORDID"), resultSet.getBoolean("INCOMMING"),
                            id, resultSet.getInt("DATETIME")*1000, new BigDecimal(resultSet.getDouble("AMMOUNT")),
                           resultSet.getString("DESCRIPTION"), new Category(categoryName, resultSet.getInt("CATEGORYID"))) );
                }
                //logger.info("returned object Account with ID = " + id);
                return new Account(id, rs.getString("ACCOUNTNUMBER"), rs.getInt("USERID"),rs.getString("DESCRIPTION"), records);

            }
                       stm.close();
        }catch (SQLException e) {
            e.printStackTrace();
            // logger.error("Error in method findById, detail: " + e.getMessage());
        }
        return null;
    }

    public List<Account> getAll() {
        List<Account> accounts=new ArrayList<Account>();
        try{
            Statement stm=conn.createStatement();
            String sql="SELECT* FROM ACCOUNT WHERE USERID=" + userID+ ";";
            ResultSet rs=stm.executeQuery(sql);
            while(rs.next()) {
                int accountID=rs.getInt("ACCOUNTID");
                String accountNumber=rs.getString("ACCOUNTNUMBER");
                String accountDesc=rs.getString("DESCRIPTION");
                Statement recordStm=conn.createStatement();
                sql="SELECT * FROM RECORD WHERE ACCOUNTID=" + accountID+ ";";
                ResultSet resultRD=recordStm.executeQuery(sql);
                List<Record> records=new ArrayList<>();
                while(resultRD.next()) {
                    int recordID=resultRD.getInt("RECORDID");
                    boolean recordIncomming=(resultRD.getBoolean("INCOMMING"));
                    long recordDateTime=(long)resultRD.getInt("DATETIME")*1000;
                    BigDecimal recordSum=new BigDecimal(resultRD.getDouble("SUMM"));
                    String recordDescr=resultRD.getString("DESCRIPTION");
                    int recordCategoryID=resultRD.getInt("CATEGORY");
                    sql="SELECT NAME FROM CATEGORY WHERE ID=" + recordCategoryID;
                    Statement categoryStm=conn.createStatement();
                    ResultSet resultCat=categoryStm.executeQuery(sql);
                    String categoryName=null;
                    if(resultCat.next()) {
                        categoryName=resultCat.getString(1);

                    }
                    categoryStm.close();
                    records.add(new Record(recordID, recordIncomming, accountID, recordDateTime, recordSum, recordDescr, new Category(categoryName, recordCategoryID)));
                }
                recordStm.close();

                Account account=new Account(rs.getInt("ACCOUNTID"), rs.getString("ACCOUNTNUMBER"),userID, rs.getString("DESCRIPTION"), records);
                accounts.add(account);
            }stm.executeUpdate(sql);
            stm.close();
        } catch (SQLException e) {e.printStackTrace();
        // logger.error("Error in method getAll, detail: " + e.getMessage());
            }
        return accounts;
    }

}
