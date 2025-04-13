import java.sql.*;

public class DBConnection
{
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/cus1166VCTRS";
        String username = "cus1166";
        String password = "cus1166"; 

        try 
        {
            Connection conn = DriverManager.getConnection(url, username, password);
            System.out.println("Connected to user cus1166");
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
