package orm;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Uliana on 20.07.2016.
 */
public class RecordDaoImpl implements iGenericDao<Record>  {
    private Connection conn;
    private int accountID;
    public RecordDaoImpl(Connection conn, int accountID){

        this.conn=conn;
        this.accountID=accountID;


    }
    public RecordDaoImpl(Connection conn) {

        this.conn = conn;
    }

    public void save(Record record) {
        try {

            //String sql="INSERT INTO RECORD(  ACCOUNTID,   DESCRIPTION, INCOMMING, DATETIME, SUMM,  CATEGORY)" +
              //      "VALUES (" + record.getAccountID()+",'" +record.getrDescription()+"',"+(record.isIncomming() ? 1:0)+"," + record.getDateTime()/1000+","+record.getSum().doubleValue()+","+ record.getrCategory().getcId()+ ")";
            String sql = "INSERT INTO record (ACCOUNTID,   DESCRIPTION, INCOMMING, DATETIME, SUMM,  CATEGORY) VALUES (?, ?, ?, ?, ?, ?) ";
            PreparedStatement stm=conn.prepareStatement( sql);
            stm.setInt(1, record.getAccountID());
            stm.setString(2, record.getrDescription());
            stm.setBoolean(3, record.isIncomming());
            stm.setLong(4, record.getDateTime());
            System.out.println("date - "+record.getDateTime());

            stm.setDouble(5, record.getSum().doubleValue());
            stm.setInt(6, record.getrCategory().getcId());
            if(stm.executeUpdate()>0) {
               //logger.info("New transaction with sum: " + fin.getAmount() + " added");
                sql="SELECT RECORDID FROM RECORD WHERE ACCOUNTID+" + record.getAccountID()+ " " +
                        " AND DATETIME = " + record.getDateTime() +  " AND SUMM = "+ record.getSum()+";";
                Statement st = conn.createStatement();
                ResultSet resultSet= st.executeQuery(sql);
                if( resultSet.next()) {
                    record.setrId(resultSet.getInt("RECORDID"));
                    //logger.info("Object Transaction with sum: " + fin.getAmount() + " set id");

                }
                st.close();
            } else {
                //logger.error("Object Transaction with sum: " + fin.getAmount() + "not added");
            }
            stm.close();
        } catch (SQLException e) {e.printStackTrace();
        //logger.error("Error in method save, detail: " + e.getMessage());
        }
    }

    public void remove(Record record) {
        try {
            Statement stm = conn.createStatement();
            String sql="DELETE FROM RECORD WHERE RECORDID=" +record.getrId();
            if(stm.executeUpdate(sql)>0){
                // logger.info("transaction with sum: " + fin.getAmount() + " deleted");
                System.out.println("Transaction deleted");
            } else {
                //logger.error("transaction with sum: " + fin.getAmount() + "not deleted");
                System.out.println("Transaction is not deleted");
            }
            stm.close();
        } catch (SQLException e) {
            // logger.error("Error in method delete, detail: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void update(Record record) {
        try {
            Statement stm=conn.createStatement();
            String sql="UPDATE RECORD SET ACCOUNTID=" + record.getAccountID()+",CATEGORY=" + record.getrCategory()+
                    ",INCOMMING="+record.isIncomming()+",DATETIME="+ record.getDateTime()+",SUMM=" +
                    record.getSum().doubleValue()+"DESCRIPTION= '" + record.getrDescription() + "' "+
                    "WHERE RECORDID=" + record.getrId()+";";
           if( stm.executeUpdate(sql)>0) {
               // logger.info("transaction with sum: " + fin.getAmount() + " updated");
               System.out.println("Transaction updayed");
           } else {
               //logger.error("transaction with sum: " + fin.getAmount() + "not updated");
               System.out.println("Transaction is not updated");
           }
            stm.close();
        }catch (SQLException e) {
            e.printStackTrace();
           // logger.error("Error in method update, detail: " + e.getMessage());
        }
    }


    public Record findById(int id) {
        Record record=null;
        try{
            Statement stm=conn.createStatement();
            String sql="SELECT * FROM RECORD WHERE RECORDID="+id;
            ResultSet rs=stm.executeQuery(sql);
            if(rs.next()) {
                sql = "SELECT NAME FROM CATEGORY WHERE ID=" + rs.getInt("ID");
                ResultSet rsCategory = stm.executeQuery(sql);

              String categoryName= null;
                if (rsCategory.next()) {
                    categoryName = rsCategory.getString(1);
                }
                record = new Record(id, (rs.getBoolean("INCOMMING")), rs.getInt("ACCOUNTID"),
                        rs.getLong("DATETIME"), rs.getBigDecimal("SUMM"), rs.getString("DESCRIPTION"),
                        new Category(categoryName, rs.getInt("CATEGORY")));            }
            stm.close();
        }catch (SQLException e) {
            e.printStackTrace();
            // logger.error("Error in method findById, detail: " + e.getMessage());
        }
        return record;
    }

    @Override
    public List<Record> getAll() {
        return null;
    }


    public List<Record> getAllByAccount(Account account) {
        List<Record> records=new ArrayList<Record>();
        try{
            Statement stm=conn.createStatement();
            String sql="SELECT* FROM RECORD WHERE ACCOUNTID=" + account.getaId()+";";

            ResultSet rs=stm.executeQuery(sql);
            while(rs.next()) {
                int recordId=rs.getInt("RECORDID");
                boolean recordIncomming=rs.getBoolean("INCOMMING");
                long recordDateTime=rs.getLong("DATETIME");
                BigDecimal recordSumm=new BigDecimal(rs.getDouble("SUMM"));
                String recordDescr=rs.getString("DESCRIPTION");
                int recordCategory=rs.getInt("CATEGORY");
                sql="SELECT NAME FROM CATEGORY WHERE ID=" + recordCategory;
                Statement categoryStm=conn.createStatement();
                ResultSet categoryRs=categoryStm.executeQuery(sql);
                String categoryName=null;
                if(categoryRs.next()) {
                    categoryName=categoryRs.getString(1);
                }
                categoryStm.close();
                Record record=new Record(recordId, recordIncomming, account.getaId(),recordDateTime, recordSumm, recordDescr, new Category(categoryName, recordCategory));
                records.add(record);
            }stm.executeUpdate(sql);
            stm.close();
        } catch (SQLException e) {e.printStackTrace();
            // logger.error("Error in method getAllByAccout, detail: " + e.getMessage());
        }
        return records;
    }

}
