
/**
 * login page - users will enter their username and passwords
 * to be redirected to a vehicle owner page or a job owner page
 * */

 import java.awt.*;
 import java.awt.event.*;
 import javax.swing.*;
 
 public class Login extends JFrame implements ActionListener
 {
     JButton loginButton;
     JLabel userLabel, passwordLabel;
     JTextField username;
     JPasswordField password;
 
     //test code for login button
     /**
     private Account[] accounts = {
         new Account("carowner1", "password", true),
         new Account("jobowner1", "password", false)
     };**/
     public Login()
     {
         //create frame
         setTitle("VCRTS Portal Login");
         setSize(400, 300);
         setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
         setLayout(null);
         setVisible(true);
        
        //username label
        userLabel = new JLabel("Username:");
        userLabel.setBounds(50, 50, 100, 25);
        userLabel.setFont(new Font("Times New Roman", Font.BOLD, 16));
        add(userLabel);
 
        //username box
        username = new JTextField(15);
        username.setBounds(150, 50, 200, 25);
        add(username);
 
        //password Label
        passwordLabel = new JLabel("Password:");
        passwordLabel.setBounds(50, 90, 100, 25);
        passwordLabel.setFont(new Font("Times New Roman", Font.BOLD, 16));
        add(passwordLabel);
 
        //password box
        password = new JPasswordField(15);
        password.setBounds(150, 90, 200, 25);
        add(password);
 
        //login button
        loginButton = new JButton("Login");
        loginButton.setBounds(150, 130, 90, 25);
        loginButton.setPreferredSize(new Dimension(140, 30));
        loginButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        loginButton.addActionListener(this);  
        add(loginButton); 
     }
 
     //initial testing of login button using array of accounts 
     /**
     @Override
     public void actionPerformed(ActionEvent e) {
         String inputName = username.getText();
         String inputPass = new String(password.getPassword());
 
         //check entered username and password
         for (Account account : accounts) {
             if (account.getUsername().equals(inputName) && account.getPassword().equals(inputPass)) {
                 JOptionPane.showMessageDialog(this, "welcome " + inputName + account.getAccountType());
                 return;
             }
         }
 
         
     }*/
   
     //call account auth class and close login window after when login clicked      
     @Override
     public void actionPerformed(ActionEvent e) {
         String inputName = username.getText();
         String inputPass = new String(password.getPassword());

         if (AccountAuthentication.authenticateAccount(inputName, inputPass)) {
             JOptionPane.showMessageDialog(this, "Login successful! Welcome, " + inputName);
             dispose();
             
         } else {
             JOptionPane.showMessageDialog(this, "Invalid username or password", "Login Failed", JOptionPane.ERROR_MESSAGE);
         }
     }
 
 
 }
 