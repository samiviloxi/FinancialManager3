package orm;

/**
 * Created by Uliana on 15.12.2016.
 */
public class CategoryList implements IObservable {

    private static CategoryList instance = new CategoryList();

    private CategoryList() {

    }

    public static CategoryList getInstance() {
        return instance;
    }

    private CategoryJList categoryJList;

    @Override
    public void notifyObservers() {
        categoryJList.handleEvent();

    }

    @Override
    public void addObserver(IObserver observer) {
        categoryJList = (CategoryJList) observer;

    }
}
