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

    public static void insertJob(int jobId, int duration, String jobName, String jobInfo, String deadline) {
        String url = "jdbc:mysql://localhost:3306/cus1166VCTRS";
        String username = "cus1166";
        String password = "cus1166";

        try {
            Connection conn = DriverManager.getConnection(url, username, password);
            
            String sql = "INSERT INTO job (jobId, jobDuration, jobName, jobInfo, jobDeadline) VALUES (" 
                    + jobId + ", "
                    + duration + ", "
                    + "'" + jobName + "', "
                    + "'" + jobInfo + "', "
                    + "'" + deadline + "')";
            
            Statement statement = conn.createStatement();
            int row = statement.executeUpdate(sql);
            if (row > 0)
                System.out.println("Job data inserted!");

            conn.close();
        } catch (SQLException e) {
            System.out.println("Error inserting job: " + e.getMessage());
        }
    }

    public static void insertVehicle(int ownerId, String model, String arrivalTime, String departureTime) {
        String url = "jdbc:mysql://localhost:3306/cus1166VCTRS";
        String username = "cus1166";
        String password = "cus1166";

        try {
            Connection conn = DriverManager.getConnection(url, username, password);
            String sql = "INSERT INTO Vehicles (OwnerID, Model, ArrivalTime, DepartureTime) VALUES (" 
                       + ownerId + ", '" + model + "', '" + arrivalTime + "', '" + departureTime + "')";
            Statement statement = conn.createStatement();
            int row = statement.executeUpdate(sql);
            if (row > 0)
                System.out.println("Vehicle data inserted!");

            conn.close();
        } catch (SQLException e) {
            System.out.println("Error inserting vehicle: " + e.getMessage());
        }
    }
}
