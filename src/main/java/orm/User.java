package orm;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 * Created by Uliana on 11.07.2016.
 */
public class User <E> {
    private int id;
    private String uName;
    private int accountAmount;
    private String login;
    private String password;
    List<E> accountList = new LinkedList<E>();

    public User(int accountAmount, String login, String uName, String password, int id) {
        this.accountAmount = accountAmount;
        this.login = login;
        this.uName = uName;
        this.password = password;

        this.id=id;
    }

    public User() {

    }

    public String getuName() {
        return uName;
    }

    public int getAccountAmount() {
        return accountAmount;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public List<E> getAccountList() {
        return accountList;
    }

    public int getId() {
        return id;
    }

    public void setuName(String uName) {
        this.uName = uName;
    }

    public void setAccountAmount(int accountAmount) {
        this.accountAmount = accountAmount;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setAccountList(List<E> accountList) {
        this.accountList = accountList;
    }

    public void setId(int id) {
        this.id = id;
    }
}
