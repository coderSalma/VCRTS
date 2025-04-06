import java.awt.*;
import javax.swing.*;

public class DashboardScreen extends JFrame {
	private JPanel backButtonPanel;
	private JButton backButton;
	
    public DashboardScreen() {
        setTitle("Client");
        setSize(450, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
        
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        JLabel welcomeLabel = new JLabel("Client Dashboard", SwingConstants.CENTER);
        welcomeLabel.setFont(new Font("Times New Roman", Font.BOLD, 20));
        welcomeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(welcomeLabel);
        panel.add(Box.createRigidArea(new Dimension(0, 50)));

        //vehicle owner button
        JButton vehicleFunction = new JButton("Vehicle Owner Functions");
        vehicleFunction.setMaximumSize(new Dimension(180, 25));
        vehicleFunction.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        vehicleFunction.addActionListener(e -> VehicleOwner.getMainScreen());
        
        //job owner button
        JButton jobFunction = new JButton("Job Owner Functions");
        jobFunction.setMaximumSize(new Dimension(180, 25));
        jobFunction.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        jobFunction.addActionListener(e -> new JobOwner(AccountAuthentication.getCurrentAccount().getUsername()));

        
        panel.add(vehicleFunction);
        panel.add(Box.createRigidArea(new Dimension(0, 5)));
        panel.add(jobFunction);

        //back button to login
        backButtonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        backButton = new JButton("Back");
        backButton.setPreferredSize(new Dimension(90, 25));
        backButton.setBounds(50, 200, 90, 25);
        backButton.addActionListener(e -> {
            this.dispose();
            new InitialScreen();
        });

        backButtonPanel.add(backButton);
        
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        backButton.setBounds(50, 200, 90, 25);
        panel.add(backButton);
        
        add(panel);
        setVisible(true);
    }
}