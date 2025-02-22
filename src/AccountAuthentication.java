import java.io.*;
import java.util.*;

public class AccountAuthentication {
	public static void authenticateAccount(){
		boolean authenticated = false;
		FileReader reader = new FileReader("Accounts.csv");
		BufferedReader buffer = new BufferedReader(reader);
		String input = buffer.readLine();
		while(input!=null & authenticated!=true) {
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
		if (authenticated=false) {
			System.out.println("Wrong login info. Try again or create an account");
		}
	}


}
