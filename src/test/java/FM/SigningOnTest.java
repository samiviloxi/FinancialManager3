package FM;

import org.junit.Before;
import orm.SignInOn;
import org.junit.Test;
import static  org.junit.Assert.*;

/**
 * Created by Uliana on 30.03.2017.
 */
public class SigningOnTest {
    @Before
    public void setUp() throws Exception {
        SignInOn.getInstance().registration("user1", "pas1", "name1");

    }

    @Test
    public void authorizationTest() throws Exception {
        int newLoginId=SignInOn.getInstance().authorisation("user1", "pass1");
        assertNotEquals(0, newLoginId);

    }

    @Test
    public void doubleRegistrationTest() throws Exception {
        int authLoginId=SignInOn.getInstance().registration("user1", "pass1","name1");
        assertEquals(0, authLoginId);

    }
}
