package orm;

/**
 * Created by Uliana on 02.12.2016.
 */
public interface IObservable {
    void notifyObservers();
    void addObserver (IObserver observer);
}
