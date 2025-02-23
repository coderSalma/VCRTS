import java.io.*;
import java.util.*;
/* 
public class AccountAuthentication {
	public static void authenticateAccount(){
		boolean authenticated = false;
		try (FileReader reader = new FileReader("Accounts.csv");
		BufferedReader buffer = new BufferedReader(reader)) {
		String input = buffer.readLine();
		while(input!=null && !authenticated) {
			//goes until the end of the account authentication file or until it finds a match
			String[] line = input.split(",");
			String tempUserName = line[1];
			String tempPassword = line[2];
			boolean tempType = Boolean.parseBoolean(line[3]);
			Account tempAccount = new Account(tempUserName,tempPassword,tempType);
			//creates a temp account to compare the input account to
			//need to change this into the INPUT account
			if (Account.getUsername() == tempAccount.getUsername() & Account.getPassword() == tempAccount.getPassword()) {
				authenticated = true;
				if (tempAccount.getAccountType() == true) {
					//go to car owner
				}
				else if (tempAccount.getAccountType()==false) {
					//go to job owner
				}
			}
		}
	}
		if (authenticated=false) {
			System.out.println("Wrong login info. Try again or create an account");
		}
	}


}
*/
import java.io.*;

public class AccountAuthentication {
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
                if (inputUsername.equals(tempUsername) && inputPassword.equals(tempPassword)) {
                    authenticated = true;
                    if (tempType) {
                        System.out.println("Welcome, Vehicle Owner!");
                        // Redirect to vehicle owner page
                    } else {
                        System.out.println("Welcome, Job Owner!");
                        // Redirect to job owner page
                    }
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
