package orm;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by Uliana on 02.12.2016.
 */
public class TotalAmount {
    private  static TotalAmount instance =new TotalAmount();
    public static  TotalAmount getInstance() {return  instance;}

    public BigDecimal getTotalAmount (int userID) {
        BigDecimal result=new BigDecimal(0);
        try {
            Connection connection=UserDaoFactory.getVariable().getConnection();
            AccountDaoImpl accountDao=new AccountDaoImpl(connection, userID);
            List<Account> accounts=accountDao.getAll();
            for(Account account: accounts) {
                result=result.add(account.getaBalance());
            }
            UserDaoFactory.getVariable().closeConnection();

        } catch (SQLException e) {
            e.getLocalizedMessage();
        }
        return result.setScale(2, BigDecimal.ROUND_HALF_EVEN);
    }
    public BigDecimal getAccountAmount (Account account) {
        BigDecimal result =new BigDecimal(0);
        try {
            Connection connection=UserDaoFactory.getVariable().getConnection();
          //  AccountDaoImpl accountDao=new AccountDaoImpl(connection, accountID);
            RecordDaoImpl recordDao=new RecordDaoImpl(connection, account.getaId());
            List<Record> records =recordDao.getAllByAccount(account);



            for(Record rec : records){
                if (rec.isIncomming())
                result=result.add(rec.getSum());

            else {


                    result=result.subtract(rec.getSum());
                }
            }
            UserDaoFactory.getVariable().closeConnection();
        } catch (SQLException e) {
            e.getStackTrace();
        }
        return  result.setScale(2, BigDecimal.ROUND_HALF_EVEN);
    }
}
