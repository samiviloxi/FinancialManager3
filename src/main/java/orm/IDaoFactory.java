package orm;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Created by Uliana on 19.07.2016.
 */
public interface IDaoFactory {

    Connection getConnection() throws SQLException;
<E extends User> iGenericDao getUserDao(Connection conn);
}
