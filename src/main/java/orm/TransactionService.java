package orm;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by Uliana on 07.12.2016.
 */
public class TransactionService {
    private static TransactionService ourInstance=new TransactionService();

    public static TransactionService getInstance () {
        return ourInstance;
    }
    private TransactionService() {

    }

    public void updateList(TransactionJList transactionJList, AccountJList accountJList){
        if(accountJList.isSelectionEmpty()) {
            accountJList.setSelectedIndex(0);
        }

        Account account=(Account) accountJList.getSelectedValue();

        if(account!=null) {
            try {
                Connection connection=UserDaoFactory.getVariable().getConnection();
                RecordDaoImpl recordDao=new RecordDaoImpl(connection);
                List<Record> transactions=recordDao.getAllByAccount(account);
                Object arr[]=transactions.toArray();
                transactionJList.setListData(arr);
                UserDaoFactory.getVariable().closeConnection();
            } catch (SQLException e) {
               e.printStackTrace();
            }
        }
    }

    public  void remove (Record record) {
        try{
            Connection conn=UserDaoFactory.getVariable().getConnection();
            RecordDaoImpl recordDao=new RecordDaoImpl(conn);
            recordDao.remove(record);
            TransactionList.getInstance().notifyObservers();
            AccountList.getInstance().notifyObservers();
            UserDaoFactory.getVariable().closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addOrUpdateTransaction(Record record, int operID) {
        try {
            Connection conn=UserDaoFactory.getVariable().getConnection();
            RecordDaoImpl recordDao=new RecordDaoImpl(conn);
            switch (operID) {

                case 0: {
                    recordDao.save(record);
                    break;
                }

                case  1: {
                    recordDao.update(record);
                    break;
                }

            }
            UserDaoFactory.getVariable().closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
