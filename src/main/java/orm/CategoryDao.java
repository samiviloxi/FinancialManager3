package orm;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Uliana on 14.12.2016.
 */
public class CategoryDao implements iGenericDao<Category>{
//private static Logger logger=LogerFactory.getLogger(CategoryDao.class);
    private Connection conn;
    public CategoryDao(Connection conn) {
        this.conn=conn;
    }

    @Override
    public void save(Category category) {
        try (Statement stm=conn.createStatement()) {
            String sql="INSERT INTO CATEGORY (NAME) VALUES ('" + category.getName() +"');";
            if(stm.executeUpdate(sql)>0) {
                sql="SELECT ID FROM CATEGORY WHERE NAME= '" + category.getName() +"';";
                ResultSet resultSet=stm.executeQuery(sql);
                if(resultSet.next()) {
                    category.setcId(resultSet.getInt(1));
                }
                //logger.info("Category: " + fin.getName() + " added with id: " + fin.getId());

            }
        } catch (SQLException e) {
            e.getLocalizedMessage();
            //  logger.error("Error in method save, detail: " + e.getMessage());
        }

    }

    @Override
    public void remove(Category category) {
        try(Statement stm=conn.createStatement()) {
            String sql="DELETE FROM CATEGORY WHERE ID=" + category.getcId()+";";
            if(stm.executeUpdate(sql)>0) {
                //  logger.info("Category: " + fin.getName() + " deleted");
                System.out.println("Category deleted");
            } else {
                //logger.error("Category: " + fin.getName() + " not deleted");

            }
        } catch (SQLException e) {
            e.getLocalizedMessage();
            //  logger.error("Error in method delete, detail: " + e.getMessage());
        }

    }

    @Override
    public void update(Category category) {
        try(Statement stm=conn.createStatement()) {
            String sql="UPDATE CATEGORY SET NAME = '" + category.getName()+ "' WHERE ID= " + category.getcId() +";";
            if(stm.executeUpdate(sql) > 0 ) {
                //  logger.info("Category: " + fin.getName() + " is updated");
                System.out.println("Category updated");
            } else {
                // logger.error("Category: " + fin.getName() + " is not updated");
            }
        } catch (SQLException e) {
            e.getLocalizedMessage();
            //  logger.error("Error in method update, detail: " + e.getMessage());
        }

    }

    @Override
    public Category findById(int id) {
        Category category=null;
        try(Statement stm=conn.createStatement()) {
            String sql="SELECT ID, NAME FROM CATEGORY WHERE ID="+id + ";";
            ResultSet resultSet=stm.executeQuery(sql);
            if(resultSet.next()) {
                category=new Category(resultSet.getString("NAME"), id);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // logger.error("Error in method update, detail: " + e.getMessage());
        }
        return category;
    }

    @Override
    public List<Category> getAll() {
        List<Category> categories=new ArrayList<>();
        try(Statement stm=conn.createStatement()) {
            String sql="SELECT * FROM CATEGORY";
            ResultSet resultSet=stm.executeQuery(sql);
            while(resultSet.next()) {
                categories.add(new Category(resultSet.getString("NAME"), resultSet.getInt("ID")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // logger.info("Error in method delete, getAll: " + e.getMessage());
        }
        return categories;
    }
}
