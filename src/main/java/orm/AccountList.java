package orm;

/**
 * Created by Uliana on 02.12.2016.
 */
public class AccountList implements IObservable {
    private static AccountList instance =new AccountList();
    private AccountJList accountJList;
    private AccountList() {}

    public static AccountList getInstance () {return  instance;}

    public void notifyObservers() {
        accountJList.handleEvent();
    }

    public void addObserver(IObserver observer) {
accountJList=(AccountJList) observer;
    }
}
