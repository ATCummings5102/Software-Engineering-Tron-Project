import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Properties;

public class Database
{
    private Connection conn;

    public Database()
    {
        try
        {
            Properties props = new Properties();
            FileInputStream in = new FileInputStream("db.properties");
            props.load(in);
            in.close();

            String url = props.getProperty("url");
            String user = props.getProperty("user");
            String password = props.getProperty("password");

            conn = DriverManager.getConnection(url, user, password);
        }
        catch (IOException | SQLException e)
        {
            System.out.println("Error initializing database connection: " + e.getMessage());
        }
    }

    public ArrayList<Object[]> fetchLeaderboardData()
    {
        ArrayList<Object[]> leaderboardData = new ArrayList<>();
        String query = "SELECT username, total_wins FROM Players ORDER BY total_wins DESC";

        try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(query))
        {
            int rank = 1;

            while (rs.next())
            {
                Object[] playerData = new Object[3];
                playerData[0] = rank;
                playerData[1] = rs.getString("username");
                playerData[2] = rs.getInt("total_wins");

                leaderboardData.add(playerData);
                rank++;
            }
        }
        catch (SQLException e)
        {
            System.out.println("SQLException: " + e.getMessage());
        }

        return leaderboardData;
    }

    public boolean verifyAccount(String username, String password)
    {
        String query = "SELECT AES_DECRYPT(password, 'key') AS decryptedPassword FROM players WHERE username = ?";

        try (PreparedStatement stmt = conn.prepareStatement(query))
        {
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();

            if (rs.next())
            {
                String storedPassword = rs.getString("decryptedPassword");
                return storedPassword != null && storedPassword.equals(password);
            }
        }
        catch (SQLException e)
        {
            System.out.println("Error verifying account: " + e.getMessage());
        }

        return false;
    }

    public boolean createNewAccount(String username, String password)
    {
        String checkQuery = "SELECT username FROM Players WHERE username = ?";

        try (PreparedStatement checkStmt = conn.prepareStatement(checkQuery))
        {
            checkStmt.setString(1, username);
            ResultSet rs = checkStmt.executeQuery();

            if (rs.next())
            {
                System.out.println("Account creation failed: Username already exists.");
                return false;
            }
        }
        catch (SQLException e)
        {
            System.out.println("Error checking account existence: " + e.getMessage());
            return false;
        }

        String insertQuery = "INSERT INTO Players (username, password) VALUES (?, AES_ENCRYPT(?, 'key'))";

        try (PreparedStatement stmt = conn.prepareStatement(insertQuery))
        {
            stmt.setString(1, username);
            stmt.setBytes(2, password.getBytes());

            stmt.executeUpdate();
            System.out.println("Account successfully created.");
            return true;
        }
        catch (SQLException e)
        {
            System.out.println("Error creating account: " + e.getMessage());
        }

        return false;
    }

    public Connection getConnection() {
        return conn;
    }


}
