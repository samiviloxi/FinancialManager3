package FM;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import orm.*;
import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Created by Uliana on 30.03.2017.
 */
public class AccountServiceTest {
    Account account;
    int userID;
    String accountNumber="accountNumber1";
    String accountDescription="accountDescription";


    @Before
    public void setUp() throws Exception {
        userID= SignInOn.getInstance().registration("user1", "pass1", "name1");
        account=new Account(accountNumber, userID, accountDescription, new ArrayList<>());



    }

    @Test
    public void addAccountTest() throws Exception {
        AccountService.getInstance().addOrUpdateAccount(account, userID, 0);
        List<Account> accounts=new ArrayList<>();
        try{
            Connection connection =UserDaoFactory.getVariable().getConnection();
            AccountDaoImpl accountDao=new AccountDaoImpl(connection, userID);
            accounts=accountDao.getAll();
            UserDaoFactory.getVariable().closeConnection();
        } catch (SQLException e){
            e.printStackTrace();
        }

        Account account=accounts.get(0);
        assertEquals(this.account.getaId(), account.getaId());

    }

    @Test
    public void updateAccountTest() throws Exception {
        AccountService.getInstance().addOrUpdateAccount(account, userID, 0);
        account.setaDescription("new description");
        AccountService.getInstance().addOrUpdateAccount(account, userID, 1);
        List<Account> accounts=new ArrayList<>();
        try{
            Connection connection=UserDaoFactory.getVariable().getConnection();
            AccountDaoImpl accountDao=new AccountDaoImpl(connection, userID);
            accounts=accountDao.getAll();
            UserDaoFactory.getVariable().closeConnection();
        }catch (SQLException e) {
            e.printStackTrace();
        }

        Account account=accounts.get(0);
        assertEquals(this.account.getaDescription(), account.getaDescription());

    }

    @Test
    public void removeAccountTest() throws Exception {
        Account removableAccount=new Account(accountNumber+"remove", userID, accountDescription, new ArrayList<>());
        AccountService.getInstance().addOrUpdateAccount(removableAccount, userID, 0);
        AccountService.getInstance().remove(removableAccount, userID);
        List<Account> accounts=new ArrayList<>();
        try {
            Connection connection=UserDaoFactory.getVariable().getConnection();
            AccountDaoImpl accountDao=new AccountDaoImpl(connection, userID);
            accounts=accountDao.getAll();
            UserDaoFactory.getVariable().closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        assertEquals(accounts.size(), 0);
        AccountService.getInstance().addOrUpdateAccount(account, userID, 0);

    }

    @After
    public void tearDown() throws Exception {
        AccountService.getInstance().remove(account, userID);
        SignInOn.getInstance().removeUser(userID);

    }

  /*  public static void main(String[] args) {
        JUnitCore runner = new JUnitCore();
        Result result = runner.run(AccountServiceTest.class);
        System.out.println("run tests: " + result.getRunCount());
        System.out.println("failed tests: " + result.getFailureCount());
        System.out.println("ignored tests: " + result.getIgnoreCount());
        System.out.println("success: " + result.wasSuccessful());
    }*/
}
