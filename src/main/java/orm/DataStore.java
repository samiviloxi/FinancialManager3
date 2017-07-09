package orm;

import java.util.Set;

/**
 * Created by Uliana on 13.07.2016.
 */
public interface DataStore {
    User getUser(String uname);
    Set<String> getUserName(User owner);
    Set<Account> getAccounts(User owner);
    Set<Record> getRecords(Account account);
    void addUser(User user);
    void addAccount(User user, Account account);
    void addRecord(Account account, Record record);
    User removeUser(String name);
    Account removeAccount(User owner, Account account);
    Record removeRecord(Account from, Record record);
}
