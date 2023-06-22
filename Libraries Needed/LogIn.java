//KWEENTOLOME
//INF212


import java.awt.EventQueue;


import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.awt.event.ActionEvent;
import java.awt.Font;
import java.awt.Color;
import javax.swing.JSeparator;
import javax.swing.JSlider;
import javax.swing.JList;

public class LogIn {

	private JFrame frame1;
	private JTextField tFUsername;
	private JPasswordField pFPassword;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					
					LogIn window = new LogIn();
					window.frame1.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public LogIn() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame1 = new JFrame();
		frame1.setForeground(new Color(255, 182, 193));
		frame1.setBackground(new Color(255, 182, 193));
		frame1.getContentPane().setBackground(new Color(209, 200, 234));
		frame1.setBounds(100, 100, 450, 300);
		frame1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame1.getContentPane().setLayout(null);
		
		JLabel lblUsername = new JLabel("Username\r\n");
		lblUsername.setBackground(Color.WHITE);
		lblUsername.setFont(new Font("Times New Roman", Font.BOLD, 13));
		lblUsername.setForeground(new Color(36, 0, 72));
		lblUsername.setBounds(53, 113, 104, 14);
		frame1.getContentPane().add(lblUsername);
		
		tFUsername = new JTextField();
		tFUsername.setForeground(new Color(31, 0, 62));
		tFUsername.setBounds(167, 110, 160, 20);
		frame1.getContentPane().add(tFUsername);
		tFUsername.setColumns(10);
		
		JLabel lblPassword = new JLabel("Password");
		lblPassword.setFont(new Font("Times New Roman", Font.BOLD, 13));
		lblPassword.setForeground(new Color(36, 0, 72));
		lblPassword.setBounds(53, 144, 104, 14);
		frame1.getContentPane().add(lblPassword);
		
		pFPassword = new JPasswordField();
		pFPassword.setForeground(new Color(31, 0, 62));
		pFPassword.setBounds(167, 141, 160, 20);
		frame1.getContentPane().add(pFPassword);
		
		JButton btnLogin = new JButton("Login\r\n");
		btnLogin.setFont(new Font("Times New Roman", Font.BOLD, 12));
		btnLogin.setForeground(new Color(75, 0, 130));
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
		
				String password = pFPassword.getText();
				String username = tFUsername.getText();
				
				if (password.equals("kweentelecom") && username.equals("slaenalyn")) {
					frame1.dispose();
					
					DataOfEmployees info = new DataOfEmployees();
					DataOfEmployees.main(null);
				}
				
				else
				{
					JOptionPane.showMessageDialog(null, "Invalid Login Details", "Login Error", JOptionPane.ERROR_MESSAGE);
					pFPassword.setText(null);
				}
			}
		});
		
		btnLogin.setBounds(264, 191, 89, 23);
		frame1.getContentPane().add(btnLogin);
		
		JLabel lblNewLabel_1 = new JLabel("Admin Login");
		lblNewLabel_1.setForeground(new Color(64, 0, 128));
		lblNewLabel_1.setFont(new Font("Times New Roman", Font.BOLD, 27));
		lblNewLabel_1.setBackground(Color.WHITE);
		lblNewLabel_1.setBounds(137, 31, 216, 37);
		frame1.getContentPane().add(lblNewLabel_1);
		
		JSeparator separator = new JSeparator();
		separator.setForeground(new Color(64, 0, 128));
		separator.setBounds(51, 79, 339, 2);
		frame1.getContentPane().add(separator);
	}

	public void setTitle(String string) {
		// TODO Auto-generated method stub
		
	}

	public void setVisible(boolean b) {
		frame1.setVisible(true);
		setVisible(false);

	}

	public static void dispose() {
		dispose();
	}
}