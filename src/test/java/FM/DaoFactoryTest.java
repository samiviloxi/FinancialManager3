package FM;
import org.junit.Test;
import orm.User;
import orm.UserDaoFactory;

import java.sql.Connection;
import java.sql.SQLException;

import static  org.junit.Assert.*;

/**
 * Created by Uliana on 30.03.2017.
 */
public class DaoFactoryTest {

    @Test
    public void createConnectionTest() throws Exception {
        Connection connection= UserDaoFactory.getVariable().getConnection();
        assertNotNull(connection);
        UserDaoFactory.getVariable().closeConnection();

    }
}
