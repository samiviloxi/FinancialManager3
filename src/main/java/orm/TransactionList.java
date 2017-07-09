package orm;

/**
 * Created by Uliana on 06.12.2016.
 */
public class TransactionList implements IObservable {
    private TransactionJList transactionJList;
    private static TransactionList instance=new TransactionList();
    public static TransactionList getInstance() { return instance;}
    private TransactionList() {

    }
    public void notifyObservers() {
        transactionJList.handleEvent();

    }

    public void addObserver(IObserver observer) {
        transactionJList=(TransactionJList) observer;

    }
}
