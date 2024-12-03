import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import java.util.Properties;
import java.io.FileOutputStream;
import java.io.IOException;

public class DatabaseTest {
    private static Database database;

    @BeforeAll
    static void setup() {
        // Create a temporary db.properties file for the test
        try (FileOutputStream out = new FileOutputStream("db.properties")) {
            Properties props = new Properties();
            props.setProperty("url", "jdbc:mysql://localhost:3306/student_space"); // Adjust to your test database
            props.setProperty("user", "student"); // Adjust to your test user
            props.setProperty("password", "hello"); // Adjust to your test password
            props.store(out, null);
        } catch (IOException e) {
            fail("Failed to create temporary db.properties file: " + e.getMessage());
        }

        // Initialize the Database instance
        database = new Database();
        assertNotNull(database, "Database instance should be initialized.");
    }

    @BeforeEach
    void clearDatabase() {
        // Clean up the test database before each test
        try {
            database.getConnection().createStatement().executeUpdate("DELETE FROM Players");
        } catch (Exception e) {
            fail("Failed to clear test database: " + e.getMessage());
        }
    }

    @Test
    public void testCreateNewAccount() {
        String username = "testUser";
        String password = "testPassword";

        // Test creating a new account
        boolean result = database.createNewAccount(username, password);
        assertTrue(result, "Account creation should succeed.");

        // Verify that the account was added
        boolean loginResult = database.verifyAccount(username, password);
        assertTrue(loginResult, "Account verification should succeed for created account.");
    }

    @Test
    public void testDuplicateAccountCreation() {
        String username = "duplicateUser";
        String password = "duplicatePassword";

        // Create the account for the first time
        boolean firstResult = database.createNewAccount(username, password);
        assertTrue(firstResult, "First account creation should succeed.");

        // Attempt to create the account again
        boolean secondResult = database.createNewAccount(username, password);
        assertFalse(secondResult, "Second account creation with the same username should fail.");
    }

    @Test
    public void testInvalidLogin() {
        String username = "nonExistentUser";
        String password = "wrongPassword";

        // Verify that logging in with non-existent credentials fails
        boolean loginResult = database.verifyAccount(username, password);
        assertFalse(loginResult, "Login attempt with non-existent credentials should fail.");
    }
}
