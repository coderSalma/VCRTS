import java.util.ArrayList;
import java.util.Scanner;
public class AccountDemo {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ArrayList<Account> accounts = new ArrayList<>();
        // Creating a vehicle owner account
        System.out.println("Create an account for a vehicle owner:");
        Account vehicleOwner = createAccount(scanner, true);
        accounts.add(vehicleOwner); 
        // Creating a job owner account
        System.out.println("\nCreate an account for a job owner:");
        Account jobOwner = createAccount(scanner, false);
        accounts.add(jobOwner); 
        // Print account details
        System.out.println("\nCreated Accounts:");
        System.out.println("Username: " + vehicleOwner.getUsername() + ", Type: " + getAccountTypeName(vehicleOwner.getAccountType()));
        System.out.println("Username: " + jobOwner.getUsername() + ", Type: " + getAccountTypeName(jobOwner.getAccountType()));

        // Login Simulation
        System.out.println("\nLogin Simulation:");
        System.out.println("Enter Username: "); 
        String inputUsername = scanner.nextLine(); 
        System.out.println("Enter Password: ");
        String inputPassword = scanner.nextLine(); 

        AccountAuthentication.authenticateAccount(accounts, inputUsername,inputPassword);

        scanner.close();
    }

    private static Account createAccount(Scanner scanner, boolean isVehicleOwner) {
        System.out.print("Enter username: ");
        String username = scanner.nextLine();

        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        return new Account(username, password, isVehicleOwner);
    }

    private static String getAccountTypeName(boolean type) {
        return type ? "Vehicle Owner" : "Job Owner";
    }


    }
