package orm;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by Uliana on 16.12.2016.
 */
public class AccountService {
    private static AccountService instance=new AccountService();

    public static AccountService getInstance() {
        return instance;
    }

    private AccountService() {

    }

    public void updateList (AccountJList accountJList, int userID) {
        try{
            Connection conn=UserDaoFactory.getVariable().getConnection();
            AccountDaoImpl accountDao=new AccountDaoImpl(conn, userID);
            List<Account> accounts=accountDao.getAll();
            Object arr[]=accounts.toArray();
            accountJList.setListData(arr);
            UserDaoFactory.getVariable().closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void remove (Account account, int userID) {
        try{
            Connection conn=UserDaoFactory.getVariable().getConnection();
            AccountDaoImpl accountDao=new AccountDaoImpl(conn, userID);
            accountDao.remove(account);
            UserDaoFactory.getVariable().closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addOrUpdateAccount(Account account, int userID, int operID) {
        try{
            Connection conn=UserDaoFactory.getVariable().getConnection();
            AccountDaoImpl accountDao=new AccountDaoImpl(conn, userID);
            switch (operID) {

                case 0: {
                    accountDao.save(account);
                    break;
                }
                case 1: {
                    accountDao.update(account);
                    break;
                }
            }
            UserDaoFactory.getVariable().closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
