import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Toolkit;

import javax.swing.SwingConstants;
import java.awt.BorderLayout;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import net.miginfocom.swing.MigLayout;
import javax.swing.JRadioButton;
import java.awt.Button;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;



public class Login extends JFrame {
	private JTextField userField;
	private JPasswordField passwordField;
	private JTextField employeeUsername;
	private JPasswordField employeePasswors;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Login frame = new Login();
					
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	

	/**
	 * Create the frame.
	 */
	public Login() {
		   setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	       setUndecorated(true);
	       
	  
			JPanel employee = new JPanel();
			JPanel admin = new JPanel();
			
			
			JRadioButton employeeButton = new JRadioButton("Employee");
			employeeButton.setSelected(true);
			JRadioButton adminButton = new JRadioButton("Admin");
			
	        // Set the preferred size of the frame
	        setPreferredSize(new Dimension(600, 450));

	        // Pack the components of the frame to calculate the preferred size
	        pack();
	        
	        
	        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
	        setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);
	        getContentPane().setLayout(null);
	        
	        JLabel BrandTitle = new JLabel("First Fruit");
	        BrandTitle.setFont(new Font("Segoe UI", Font.PLAIN, 30));
	        BrandTitle.setHorizontalAlignment(SwingConstants.CENTER);
	        BrandTitle.setBounds(0, 0, 800, 125);
	        getContentPane().add(BrandTitle);
	        
	    	// Set the border color
			Color borderColor = new Color(160, 196, 157);

			// Create the line border with the specified color and thickness
			LineBorder lineBorder = new LineBorder(borderColor, 3);

			// Create a JPanel to hold the content
			JPanel contentPane = new JPanel();
			contentPane.setBorder(new LineBorder(Color.BLACK, 3, true));
			setContentPane(contentPane);
			contentPane.setLayout(null);
			
			JLabel brandTitle = new JLabel("First Fruit");
			brandTitle.setHorizontalAlignment(SwingConstants.CENTER);
			brandTitle.setFont(new Font("Segoe UI", Font.PLAIN, 43));
			brandTitle.setBounds(10, 26, 580, 75);
			contentPane.add(brandTitle);
			
			JLabel lblSwitchTo = new JLabel("switch to");
			lblSwitchTo.setBounds(193, 126, 85, 22);
			contentPane.add(lblSwitchTo);
			lblSwitchTo.setHorizontalAlignment(SwingConstants.CENTER);
			lblSwitchTo.setFont(new Font("Segoe UI", Font.PLAIN, 16));
			

			adminButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					admin.setVisible(true);
					employeeButton.setSelected(false);
					
					employee.setVisible(false);
				}
			});
			adminButton.setFont(new Font("Segoe UI", Font.PLAIN, 11));
			adminButton.setHorizontalAlignment(SwingConstants.CENTER);
			adminButton.setBounds(284, 127, 68, 23);
			contentPane.add(adminButton);
			

			employeeButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					admin.setVisible(false);
					adminButton.setSelected(false);
					employee.setVisible(true);
				}
			});
			employeeButton.setHorizontalAlignment(SwingConstants.CENTER);
			employeeButton.setFont(new Font("Segoe UI", Font.PLAIN, 11));
			employeeButton.setBounds(354, 127, 85, 23);
			contentPane.add(employeeButton);
			

			employee.setLayout(null);
			employee.setBackground(Color.WHITE);
			employee.setBounds(10, 159, 580, 280);
			contentPane.add(employee);
			
			employeeUsername = new JTextField();
			employeeUsername.setHorizontalAlignment(SwingConstants.CENTER);
			employeeUsername.setFont(new Font("Segoe UI", Font.PLAIN, 14));
			employeeUsername.setColumns(10);
			employeeUsername.setBounds(10, 58, 560, 53);
			employee.add(employeeUsername);
			
			employeePasswors = new JPasswordField();
			employeePasswors.setHorizontalAlignment(SwingConstants.CENTER);
			employeePasswors.setFont(new Font("Segoe UI", Font.PLAIN, 14));
			employeePasswors.setBounds(10, 122, 560, 53);
			employee.add(employeePasswors);
			
			Button button_1 = new Button("Login as Employee");
			button_1.setBounds(192, 209, 184, 43);
			employee.add(button_1);
			

			admin.setBackground(Color.WHITE);
			admin.setBounds(10, 159, 580, 280);
			contentPane.add(admin);
			admin.setLayout(null);
			
			userField = new JTextField();
			userField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
			userField.setBounds(10, 40, 560, 53);
			admin.add(userField);
			userField.setColumns(10);
			
			passwordField = new JPasswordField();
			passwordField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
			passwordField.setBounds(10, 104, 560, 53);
			admin.add(passwordField);
			
			Button button = new Button("Login as Admin");
			button.setBounds(192, 209, 184, 43);
			admin.add(button);

	}
}
