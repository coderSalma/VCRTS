import java.io.*;

public class AccountAuthentication 
{
	private static Account CurrentAccount;
	
	public static Account getCurrentAccount() 
	{
		return CurrentAccount; 
	}
	
    public static boolean authenticateAccount(String inputUsername, String inputPassword) {
        boolean authenticated = false;
        try (FileReader reader = new FileReader("Accounts.csv");
             BufferedReader buffer = new BufferedReader(reader)) {
            String input = buffer.readLine();
            while (input != null && !authenticated) {
                // Split the line into parts
                String[] line = input.split(",");
                String tempUsername = line[1];
                String tempPassword = line[2];
                boolean tempType = Boolean.parseBoolean(line[3]);

                // Compare input credentials with the temporary account
                // below code was written by manasa srinivasa: 
                if (inputUsername.equals(tempUsername) && inputPassword.equals(tempPassword)) {
                    authenticated = true;
                    
                    CurrentAccount = new Account(tempUsername, tempPassword /*,tempType*/);
                    new DashboardScreen();
                    new ControllerScreen();
                }
                input = buffer.readLine();
            }
        } catch (IOException e) {
            System.err.println("Error reading accounts: " + e.getMessage());
        }

        if (!authenticated) {
            System.out.println("Wrong login info. Try again or create an account.");
        }
        return authenticated;
    }
}
