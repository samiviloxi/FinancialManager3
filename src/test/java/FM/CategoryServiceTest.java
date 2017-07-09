package FM;

import org.junit.Test;
import orm.*;

import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.Assert.*;

/**
 * Created by Uliana on 30.03.2017.
 */
public class CategoryServiceTest {
    @Test
    public void addNewCategoryTest() throws Exception {
        Category category=new Category("Test Category");
        CategoryService.getInstance().addOrUpdateCategory(category, 0);
        assertNotEquals(-1, category.getcId());
        CategoryService.getInstance().remove(new CategoryJList(), category);

    }

    @Test
    public void updateCategoryTest() throws Exception {
        Category category=new Category("Test Category");
        CategoryService.getInstance().addOrUpdateCategory(category, 0);
        category.setName("New Category");
        CategoryService.getInstance().addOrUpdateCategory(category, 1);
        Category modifyCategory=null;
        try {
            Connection connection= UserDaoFactory.getVariable().getConnection();
            CategoryDao categoryDao=new CategoryDao(connection);
            modifyCategory=categoryDao.findById(category.getcId());
            UserDaoFactory.getVariable().closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
assertEquals(category.getName(), modifyCategory.getName());
        CategoryService.getInstance().remove(new CategoryJList(), category);
    }
}
