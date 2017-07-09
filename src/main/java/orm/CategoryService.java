package orm;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by Uliana on 15.12.2016.
 */
public class CategoryService {
    private static CategoryService instance =new CategoryService();
    public static  CategoryService getInstance() {

        return instance;
    }

    private CategoryService() {

    }

    public void updateList (CategoryJList categoryJList) {
        try {
            Connection conn=UserDaoFactory.getVariable().getConnection();
            CategoryDao categoryDao=new CategoryDao(conn);
            List<Category> categories=categoryDao.getAll();
            Object arr[]=categories.toArray();
            categoryJList.setListData(arr);
            UserDaoFactory.getVariable().closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public  void remove(CategoryJList categoryJList, Category category) {
        try {
            Connection conn=UserDaoFactory.getVariable().getConnection();
            CategoryDao categoryDao=new CategoryDao(conn);
            categoryDao.remove(category);
            updateList(categoryJList);
            UserDaoFactory.getVariable().closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }

    public void addOrUpdateCategory(Category category, int operID) {
        try{
            Connection conn=UserDaoFactory.getVariable().getConnection();
            CategoryDao categoryDao=new CategoryDao(conn);
            switch(operID) {
                case 0: {
                    categoryDao.save(category);
                    break;
                }
                case 1: {
                    categoryDao.update(category);
                    break;
                }

            }
            UserDaoFactory.getVariable().closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
