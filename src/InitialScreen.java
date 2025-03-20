import java.awt.*;
import javax.swing.*;

public class InitialScreen extends JFrame{
    private JButton logInButton, signUpButton;
    private JLabel welcomeLabel, logInLabel, signUpLabel;
    private JPanel panel;

    public InitialScreen() {

        //create frame
        setTitle("VCRTS - Vehicular Cloud Real Time System");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);

       //main panel
       panel = new JPanel();
       panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
       panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
       //panel.setBackground(new Color(204, 229, 255));
       
       //welcome message
       welcomeLabel = new JLabel("Welcome to the Vehicular Cloud Real Time System!", SwingConstants.CENTER);
       welcomeLabel.setFont(new Font("Times New Roman", Font.BOLD, 16));
       welcomeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

       //login label
       logInLabel = new JLabel("Already have an account? Login Here:");
       logInLabel.setFont(new Font("Calibri Body", Font.PLAIN, 12));
       logInLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

       //login button
       logInButton = new JButton("Login");
       logInButton.setPreferredSize(new Dimension(120, 30));
       logInButton.setAlignmentX(Component.CENTER_ALIGNMENT);

       //signup label
       signUpLabel = new JLabel("New user? Sign Up below:");
       signUpLabel.setFont(new Font("Calibri Body", Font.PLAIN , 12));
       signUpLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

       //signup button
       signUpButton = new JButton("Create Account");
       signUpButton.setPreferredSize(new Dimension(140, 30));
       signUpButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        //action listeners 
        //open login page and close window
        logInButton.addActionListener(e -> 
        {
            new Login();
            dispose(); 
        });
        
        //open sign up page and close window
        signUpButton.addActionListener(e -> {
        	new Signup();
            //dispose();
        });

        panel.add(welcomeLabel);
        panel.add(Box.createRigidArea(new Dimension(0, 20))); 

        panel.add(logInLabel);
        panel.add(Box.createRigidArea(new Dimension(0, 20))); 
        panel.add(logInButton);
        panel.add(Box.createRigidArea(new Dimension(0, 20))); 

        panel.add(signUpLabel);
        panel.add(Box.createRigidArea(new Dimension(0, 10))); 
        panel.add(signUpButton);

        add(panel, BorderLayout.CENTER);
        
    }

    public static void main(String[] args) {
        new InitialScreen();
    }
}
