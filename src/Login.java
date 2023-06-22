import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.SwingConstants;
import java.awt.BorderLayout;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JButton;
//import net.miginfocom.swing.MigLayout;
import javax.swing.JRadioButton;
import java.awt.Button;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.ActionEvent;
import javax.swing.ImageIcon;



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
	        setPreferredSize(new Dimension(700, 500));

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
			
			JLabel brandTitle = new JLabel();
			ImageIcon icon = new ImageIcon("D:\\Art Lisboa files\\Java Projects\\POS-java\\Images\\IMG_9704.JPG");

			brandTitle.setPreferredSize(new Dimension(300, 130)); // Set a preferred size for the label

			Image img = icon.getImage();
			Image imgScale = img.getScaledInstance(brandTitle.getPreferredSize().width, brandTitle.getPreferredSize().height, Image.SCALE_SMOOTH);
			ImageIcon newIcon = new ImageIcon(imgScale);

			brandTitle.setIcon(newIcon);


			// Add the brandTitle to the frame or panel as necessary

			
			brandTitle.setHorizontalAlignment(SwingConstants.CENTER);
			brandTitle.setFont(new Font("Segoe UI", Font.PLAIN, 43));
			brandTitle.setBounds(192, 11, 367, 160);
			contentPane.add(brandTitle);
			
			JLabel lblSwitchTo = new JLabel("switch to");
			lblSwitchTo.setBounds(245, 182, 85, 22);
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
			adminButton.setBounds(336, 182, 68, 23);
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
			employeeButton.setBounds(406, 182, 85, 23);
			contentPane.add(employeeButton);
			

			employee.setLayout(null);
			employee.setBackground(Color.WHITE);
			employee.setBounds(10, 213, 680, 276);
			contentPane.add(employee);
			
			employeeUsername = new JTextField();
			employeeUsername.setHorizontalAlignment(SwingConstants.CENTER);
			employeeUsername.setFont(new Font("Segoe UI", Font.PLAIN, 14));
			employeeUsername.setColumns(10);
			employeeUsername.setBounds(48, 11, 586, 53);
			employee.add(employeeUsername);
			
			employeePasswors = new JPasswordField();
			employeePasswors.setHorizontalAlignment(SwingConstants.CENTER);
			employeePasswors.setFont(new Font("Segoe UI", Font.PLAIN, 14));
			employeePasswors.setBounds(48, 76, 586, 53);
			employee.add(employeePasswors);
			
			Button employeeLogin = new Button("Login as Employee");
			employeeLogin.setBounds(229, 171, 263, 43);
			employee.add(employeeLogin);
			

			admin.setBackground(Color.WHITE);
			admin.setBounds(10, 213, 680, 276);
			contentPane.add(admin);
			admin.setLayout(null);
			
			userField = new JTextField();
			userField.setHorizontalAlignment(SwingConstants.CENTER);
			userField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
			userField.setBounds(48, 11, 586, 53);
			admin.add(userField);
			userField.setColumns(10);
			
			passwordField = new JPasswordField();
			passwordField.setHorizontalAlignment(SwingConstants.CENTER);
			passwordField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
			passwordField.setBounds(48, 76, 586, 53);
			admin.add(passwordField);
			
			Button button = new Button("Login as Admin");
			button.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					
					String username = userField.getText();
					String password = passwordField.getText();
					
					if (new Database().loginAdmin(username, password)) {
						
						new ADMIN().setVisible(true);
						dispose();
					};
				}
				
			});
			button.setBounds(225, 171, 263, 43);
			admin.add(button);
			
			Button close = new Button("close");
			close.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					dispose();
				}
			});
			close.setFont(new Font("Segoe UI", Font.PLAIN, 14));
			close.setBounds(605, 11, 85, 24);
			contentPane.add(close);

	}
}
