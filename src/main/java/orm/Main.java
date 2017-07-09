package orm;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Uliana on 19.07.2016.
 */
public class Main {
    public static void main(String[] args) {

        LoginWindow loginWindow=new LoginWindow();
        /*List<User> users=new ArrayList<User>();
        users.add(new User(11, "user1", "Anna", "user1", 1));
        users.add(new User(22, "user2", "Alisa", "user2", 2));
        users.add(new User(33, "user3", "Lexa", "user3", 3));
        users.add(new User(55, "user4", "Tima", "user4",4));
        List<Account> accounts=new ArrayList<Account>();
        accounts.add(new Account(11,100, "User1account","Anna", 2000));
        accounts.add(new Account(22,101, "User3account", "Anna", 3000));
        List<Record> records=new ArrayList<Record>();
        records.add(new Record(111,1, 100, "Anna", "transaction1", 100, false, true,  "shopping" ));
        records.add(new Record(222,2, 100, "Anna", "transaction2", 200, true, false, "cashback"));
               IDaoFactory iDaoFactory=new UserDaoFactory();
        try {
            Connection connection=iDaoFactory.getConnection();
            DaoUtil.createTable(connection);
            DaoUtil.createAccountTable(connection);
            DaoUtil.createRecordTable(connection);
iGenericDao<User> userDao=new UserDaoImpl(connection);

            for(User own:users) {
                userDao.save(own);
            }
            iGenericDao<Account> accountDao=new AccountDaoImpl(connection);
            for (Account acc:accounts) {
                accountDao.save(acc);
            }
            iGenericDao<Record> recordDao=new RecordDaoImpl(connection);
            for(Record rec:records){
                recordDao.save(rec);
            }
            DaoUtil.closeConnection(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
*/

    }
}
