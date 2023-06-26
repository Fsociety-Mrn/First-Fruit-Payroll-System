import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Image;

import javax.swing.SwingConstants;
import javax.swing.ImageIcon;
import javax.swing.JButton;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableCellRenderer;

import java.util.Date;
import java.util.Calendar;
import javax.swing.JTextField;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;


import javax.swing.Timer;


public class EMPLOYEE extends JFrame {
	
	private Database db = new Database();

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					
					EMPLOYEE frame = new EMPLOYEE(0);
					
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
	public EMPLOYEE(int ID) {
		
		Database db = new Database();
		
		JLabel NameLabel = new JLabel(db.getEmployeeName(ID));
		
		System.out.println(ID);
		db.createDatabaseEmployee(String.valueOf(ID));
		
		 // Create a Calendar instance
        Calendar calendar = Calendar.getInstance();
        
        // Get the current date
        Date currentDate = calendar.getTime();
        
        // Format the date
        SimpleDateFormat dateFormat = new SimpleDateFormat("EEEE, MMMM d yyyy");
        String formattedDate = dateFormat.format(currentDate);
        
        // Display the formatted date
        System.out.println("Current Date: " + formattedDate);
        
        
        // Format the time
        SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm a");
        String formattedTime = timeFormat.format(calendar.getTime());
        
        // Display the formatted time
        System.out.println("Current Time: " + formattedTime);
		
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setUndecorated(true);
		
		setBounds(100, 100, 1030, 600);
		
        // Set the preferred size of the frame
        setPreferredSize(new Dimension(1030, 600));
        
        // Pack the components of the frame to calculate the preferred size
        pack();

        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);

		// Create the line border with the specified color and thickness

        
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));

		setContentPane(contentPane);
		contentPane.setLayout(null);
        JPanel TimeInOut = new JPanel();
		
		JLabel EmployeeID = new JLabel(String. valueOf(ID));
		EmployeeID.setFont(new Font("Segoe UI", Font.PLAIN, 15));
		EmployeeID.setBounds(359, 85, 89, 30);
		contentPane.add(EmployeeID);
		
		JLabel lblEmployeeId = new JLabel("Employee ID:");
		lblEmployeeId.setFont(new Font("Segoe UI", Font.PLAIN, 15));
		lblEmployeeId.setBounds(268, 85, 96, 30);
		contentPane.add(lblEmployeeId);
		
//        JPanel EmployeeList = new JPanel();
//        JPanel AddEmployee = new JPanel();
		
		JPanel employeeAttendanceButton = new JPanel();
		JPanel addEmployeeButton = new JPanel();

		
		JPanel sidebar = new JPanel();
		sidebar.setBackground(new Color(225, 244, 243));
		sidebar.setBorder(new LineBorder(new Color(0, 0, 0), 3));
		sidebar.setBounds(0, 0, 242, 600);
		contentPane.add(sidebar);
		sidebar.setLayout(null);
		
		JPanel logoutButton = new JPanel();
		logoutButton.setBackground(Color.BLACK);
		logoutButton.setBounds(10, 547, 222, 42);
		sidebar.add(logoutButton);
		logoutButton.setLayout(null);
		
		JLabel logout = new JLabel("Logout");
		logout.setBounds(10, 11, 202, 20);
		logoutButton.add(logout);
		logout.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				dispose();
				new Login().setVisible(true);
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				logout.setCursor(new Cursor(Cursor.HAND_CURSOR));
			}
		});
		logout.setForeground(Color.WHITE);
		logout.setHorizontalAlignment(SwingConstants.CENTER);
		logout.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		
		JPanel employeListButton = new JPanel();
		employeListButton.setBounds(10, 172, 222, 53);
		sidebar.add(employeListButton);
		employeListButton.setLayout(null);
		employeListButton.setBorder(new LineBorder(new Color(0, 0, 0)));
		employeListButton.setBackground(new Color(225, 244, 243));
		
		JLabel employelist = new JLabel("Time In / Time Out");
		employelist.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				
				employeListButton.setBorder(new LineBorder(new Color(0, 0, 0)));
				employeeAttendanceButton.setBorder(null);
				addEmployeeButton.setBorder(null);
				

				
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				employelist.setCursor(new Cursor(Cursor.HAND_CURSOR));
			}
			
		});
		employelist.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		employelist.setHorizontalAlignment(SwingConstants.CENTER);
		employelist.setBounds(10, 11, 202, 31);
		employeListButton.add(employelist);
		

		employeeAttendanceButton.setLayout(null);
		employeeAttendanceButton.setBorder(null);
		employeeAttendanceButton.setBackground(new Color(225, 244, 243));
		employeeAttendanceButton.setBounds(10, 300, 222, 53);
		sidebar.add(employeeAttendanceButton);
		
		JLabel employeeattendance = new JLabel("Change Password");
		employeeattendance.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				employeListButton.setBorder(null);
				employeeAttendanceButton.setBorder(new LineBorder(new Color(0, 0, 0)));
				addEmployeeButton.setBorder(null);
			
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				employeeattendance.setCursor(new Cursor(Cursor.HAND_CURSOR));
			}
		});
		employeeattendance.setHorizontalAlignment(SwingConstants.CENTER);
		employeeattendance.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		employeeattendance.setBounds(10, 11, 202, 31);
		employeeAttendanceButton.add(employeeattendance);
		

		addEmployeeButton.setLayout(null);
		addEmployeeButton.setBorder(null);
		addEmployeeButton.setBackground(new Color(225, 244, 243));
		addEmployeeButton.setBounds(10, 236, 222, 53);
		sidebar.add(addEmployeeButton);
		
		JLabel addemployee = new JLabel("Salary History");
		addemployee.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				employeListButton.setBorder(null);
				employeeAttendanceButton.setBorder(null);
				addEmployeeButton.setBorder(new LineBorder(new Color(0, 0, 0)));
				

				
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				addemployee.setCursor(new Cursor(Cursor.HAND_CURSOR));
			}
		});
		addemployee.setHorizontalAlignment(SwingConstants.CENTER);
		addemployee.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		addemployee.setBounds(10, 11, 202, 31);
		addEmployeeButton.add(addemployee);
		
		JLabel TIME = new JLabel(formattedTime);
		TIME.setBounds(10, 38, 222, 80);
		sidebar.add(TIME);
		TIME.setHorizontalAlignment(SwingConstants.CENTER);
		TIME.setFont(new Font("Segoe UI", Font.PLAIN, 40));
		
		JLabel DATE = new JLabel(formattedDate);
		DATE.setBounds(10, 92, 222, 40);
		sidebar.add(DATE);
		DATE.setFont(new Font("Segoe UI", Font.PLAIN, 20));
		DATE.setHorizontalAlignment(SwingConstants.CENTER);
				
				JLabel brandTitle = new JLabel();
				brandTitle.setBounds(715, 11, 222, 108);
				contentPane.add(brandTitle);
				brandTitle.setPreferredSize(new Dimension(230, 130)); // Set a preferred size for the label
				
//				PC ko
				ImageIcon icon = new ImageIcon("D:\\Art Lisboa files\\Java Projects\\POS-java\\Images\\IMG_9704.JPG");
				
//				Laptop
//				ImageIcon icon = new ImageIcon("C:\\Users\\Lisboa Family\\OneDrive\\Desktop\\Java Projects\\POS-java\\Images\\IMG_9704.JPG");

				Image img = icon.getImage();
				Image imgScale = img.getScaledInstance(brandTitle.getPreferredSize().width, brandTitle.getPreferredSize().height, Image.SCALE_SMOOTH);
				

			

				
				ImageIcon newIcon = new ImageIcon(imgScale);
						
						brandTitle.setIcon(newIcon);
						brandTitle.setPreferredSize(new Dimension(300, 130));
						brandTitle.setHorizontalAlignment(SwingConstants.CENTER);
						brandTitle.setFont(new Font("Segoe UI", Font.PLAIN, 43));
						
				        // Set the cell renderer to center align the content
				        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
				        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
		
				        

				        TimeInOut.setBackground(Color.WHITE);
				        TimeInOut.setBounds(256, 113, 764, 476);
				        contentPane.add(TimeInOut);
				        TimeInOut.setLayout(null);
				        
				        JPanel panel = new JPanel();
				        panel.setBorder(new LineBorder(new Color(0, 0, 0)));
				        panel.setBounds(401, 120, 353, 199);
				        TimeInOut.add(panel);
				        panel.setLayout(null);
				        
				        JLabel lblDeducionSummary = new JLabel("Deduction Summary per week");
				        lblDeducionSummary.setHorizontalAlignment(SwingConstants.CENTER);
				        lblDeducionSummary.setBounds(10, 11, 333, 27);
				        lblDeducionSummary.setFont(new Font("Segoe UI", Font.PLAIN, 15));
				        panel.add(lblDeducionSummary);
				        
				        JLabel lblSss = new JLabel("SSS: 2323");
				        lblSss.setFont(new Font("Segoe UI", Font.PLAIN, 17));
				        lblSss.setBounds(72, 49, 225, 40);
				        panel.add(lblSss);
				        
				        JLabel lblSss_1 = new JLabel("PhilHealth: 2323");
				        lblSss_1.setFont(new Font("Segoe UI", Font.PLAIN, 17));
				        lblSss_1.setBounds(72, 71, 225, 40);
				        panel.add(lblSss_1);
				        
				        JLabel lblSss_2 = new JLabel("PagIbig: 2323");
				        lblSss_2.setFont(new Font("Segoe UI", Font.PLAIN, 17));
				        lblSss_2.setBounds(72, 97, 225, 40);
				        panel.add(lblSss_2);
				        
				        JLabel lblSss_2_2 = new JLabel("Total Deduction: 5050");
				        lblSss_2_2.setHorizontalAlignment(SwingConstants.CENTER);
				        lblSss_2_2.setFont(new Font("Segoe UI", Font.PLAIN, 17));
				        lblSss_2_2.setBounds(10, 148, 333, 40);
				        panel.add(lblSss_2_2);
				        
				        JPanel totalGross = new JPanel();
				        totalGross.setLayout(null);
				        totalGross.setBorder(new LineBorder(new Color(0, 0, 0)));
				        totalGross.setBounds(10, 92, 353, 246);
				        TimeInOut.add(totalGross);
				        
				        JLabel TotalGrossPay = new JLabel("Total Gross Pay summary");
				        TotalGrossPay.setHorizontalAlignment(SwingConstants.CENTER);
				        TotalGrossPay.setFont(new Font("Segoe UI", Font.PLAIN, 15));
				        TotalGrossPay.setBounds(10, 11, 333, 27);
				        totalGross.add(TotalGrossPay);
				        
				        JLabel workDaysLabel = new JLabel("Total Regular work days: 12");
				        workDaysLabel.setFont(new Font("Segoe UI", Font.PLAIN, 17));
				        workDaysLabel.setBounds(53, 49, 290, 40);
				        totalGross.add(workDaysLabel);
				        
				        JLabel workHoursLabel = new JLabel("Regular Work hours: 0");
				        workHoursLabel.setFont(new Font("Segoe UI", Font.PLAIN, 17));
				        workHoursLabel.setBounds(53, 71, 290, 40);
				        totalGross.add(workHoursLabel);
				        
				        JLabel absentLabel = new JLabel("LESS: Absent days: 0");
				        absentLabel.setFont(new Font("Segoe UI", Font.PLAIN, 17));
				        absentLabel.setBounds(53, 97, 290, 40);
				        totalGross.add(absentLabel);
				        
				        JLabel totalGrossLabel = new JLabel("Total Gross pay: 123");
				        totalGrossLabel.setHorizontalAlignment(SwingConstants.CENTER);
				        totalGrossLabel.setFont(new Font("Segoe UI", Font.PLAIN, 17));
				        totalGrossLabel.setBounds(10, 199, 333, 40);
				        totalGross.add(totalGrossLabel);
				        
				        JLabel tardinessLabel = new JLabel("LESS: Tardiness Hours: 2");
				        tardinessLabel.setFont(new Font("Segoe UI", Font.PLAIN, 17));
				        tardinessLabel.setBounds(53, 122, 290, 40);
				        totalGross.add(tardinessLabel);
				        
				        JLabel rateLabel = new JLabel("Daily Rate: 230");
				        rateLabel.setFont(new Font("Segoe UI", Font.PLAIN, 17));
				        rateLabel.setBounds(53, 148, 290, 40);
				        totalGross.add(rateLabel);
				        
				        JPanel netIncome = new JPanel();
				        netIncome.setBorder(new LineBorder(new Color(0, 0, 0)));
				        netIncome.setBounds(156, 349, 476, 127);
				        TimeInOut.add(netIncome);
				        netIncome.setLayout(null);
				        
				        JLabel netIncomeLabel = new JLabel("Net Income: 4000");
				        netIncomeLabel.setBounds(10, 87, 456, 27);
				        netIncome.add(netIncomeLabel);
				        netIncomeLabel.setHorizontalAlignment(SwingConstants.CENTER);
				        netIncomeLabel.setFont(new Font("Segoe UI", Font.PLAIN, 20));
				        
				        JLabel deductionLabel = new JLabel("LESS: Total Deductions: 123");
				        deductionLabel.setHorizontalAlignment(SwingConstants.CENTER);
				        deductionLabel.setFont(new Font("Segoe UI", Font.PLAIN, 20));
				        deductionLabel.setBounds(10, 49, 456, 27);
				        netIncome.add(deductionLabel);
				        
				        JLabel grossPayResult = new JLabel("Total Gross pay: 23123");
				        grossPayResult.setHorizontalAlignment(SwingConstants.CENTER);
				        grossPayResult.setFont(new Font("Segoe UI", Font.PLAIN, 20));
				        grossPayResult.setBounds(10, 11, 456, 27);
				        netIncome.add(grossPayResult);
				        
				        JPanel timeIn = new JPanel();
				        JPanel timeOut = new JPanel();
				        
				        timeIn.setLayout(null);
				        timeIn.setBorder(new LineBorder(new Color(0, 0, 0)));
				        timeIn.setBackground(Color.WHITE);
				        timeIn.setBounds(257, 39, 222, 42);
				        TimeInOut.add(timeIn);
				        
				        JLabel timeOutButton = new JLabel("Time Out");
				        JLabel timeInButton = new JLabel("Time In");
				        
				        timeInButton.addMouseListener(new MouseAdapter() {
				        	@Override
				        	public void mouseEntered(MouseEvent e) {
				        		timeInButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
				        	}
				        	@Override
				        	public void mouseClicked(MouseEvent e) {
				        		
				        		
				        		
				        		timeIn.setVisible(false);
				        		timeOut.setVisible(true);
				        		
				        		
				        
				        	    // Get the current date
				                Date currentDateNow = calendar.getTime();
				        	
				      
				        		String name = "";
				        	
				                
				                // Format the date
				                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-dd-MM");
				                String formattedDate = dateFormat.format(currentDateNow);
				                
				          		String date = formattedDate;
				          		
				                // Display the formatted date
//				                System.out.println("Current Date: " + formattedDate);
				                
				                
//				          		Time In
				                SimpleDateFormat TimeInFormat = new SimpleDateFormat("hh:mm a");
				                String timeIn = TimeInFormat.format(calendar.getTime());
				                String timeOut = "N/A";
				                
				                int workHours = 0;
				                
				             // Get the current time
				                LocalTime currentTime = LocalTime.now();

				                // Set the ideal time to 8:00 AM
				                LocalTime idealTime = LocalTime.of(8, 0, 0);

				                // Calculate the time difference in minutes
				                long minutesLate = ChronoUnit.MINUTES.between(idealTime, currentTime);

				                // Calculate the time difference in hours
				                long hoursLate = ChronoUnit.HOURS.between(idealTime, currentTime);
				             
			                    double tardiness = (minutesLate / 60.0);
			                    
			        			try {
			        				if (db.timeInCheck(NameLabel.getText(), formattedDate) == false) {
			        					db.addAttendance(ID, date, NameLabel.getText(), timeIn, timeOut, workHours, tardiness);
					        		}
								} catch (SQLException e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
								}
			                    System.out.println(tardiness);

		
				        		
				        	}
				        });
				        timeInButton.setHorizontalAlignment(SwingConstants.CENTER);
				        timeInButton.setForeground(Color.BLACK);
				        timeInButton.setFont(new Font("Segoe UI", Font.PLAIN, 14));
				        timeInButton.setBackground(Color.WHITE);
				        timeInButton.setBounds(10, 11, 202, 20);
				        timeIn.add(timeInButton);
				        
			
				        
				        timeOut.setLayout(null);
				        timeOut.setBackground(Color.BLACK);
				        timeOut.setBounds(257, 39, 222, 42);
				        TimeInOut.add(timeOut);
				        
				        
				        timeOutButton.addMouseListener(new MouseAdapter() {
				        	@Override
				        	public void mouseEntered(MouseEvent e) {
				        		timeOutButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
				        	}
				        	
				        	@Override
				        	public void mouseClicked(MouseEvent e) {
				        		timeIn.setVisible(true);
				        		timeOut.setVisible(false);
				        		
				        		// Format the date
				                SimpleDateFormat dateFormats = new SimpleDateFormat("yyyy-dd-MM");
				                String formattedDates = dateFormats.format(currentDate);
				        		
//				          		Time In
				                SimpleDateFormat TimeInFormat = new SimpleDateFormat("hh:mm a");
				                String timeOut = TimeInFormat.format(calendar.getTime());
				                
				                Double tard = db.getEmployeeTardiness(ID, formattedDates);
				                
				        		if (db.timeOutCheck(NameLabel.getText(), formattedDates)) {
				        			System.out.println("pwede kapa mag time Out");
				        			
				        			
				        			try {
				        				
										db.updateAttendance(ID, formattedDates,timeOut,8.0 - tard);
				        				
									} catch (SQLException e1) {
										// TODO Auto-generated catch block
										e1.printStackTrace();
									}
//				        			
				        			
				        		};
				        	}
				        });
				        timeOutButton.setHorizontalAlignment(SwingConstants.CENTER);
				        timeOutButton.setForeground(Color.WHITE);
				        timeOutButton.setFont(new Font("Segoe UI", Font.PLAIN, 14));
				        timeOutButton.setBounds(10, 11, 202, 20);
				        timeOut.add(timeOutButton);
				        
				        JLabel lblGoodDay = new JLabel("Good day!");
				        lblGoodDay.setFont(new Font("Segoe UI", Font.PLAIN, 20));
				        lblGoodDay.setBounds(259, 11, 222, 40);
				        contentPane.add(lblGoodDay);
				        
				       
				        NameLabel.setFont(new Font("Segoe UI", Font.PLAIN, 30));
				        NameLabel.setBounds(268, 48, 395, 47);
				        contentPane.add(NameLabel);
				        

				        
				        // Create a timer to update the time every second
				        Timer timer = new Timer(1000, new ActionListener() {
				            @Override
				            public void actionPerformed(ActionEvent e) {
				                // Get the current time
				                Date currentTime = new Date();
				                
				                // Format the time
				                SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm a");
				                String formattedTime = timeFormat.format(currentTime);
				                
				                // Update the label with the new time
				                TIME.setText(formattedTime);
				            }
				        });
				        timer.start();
				        
				        
		                // Format the date
		                SimpleDateFormat dateFormats = new SimpleDateFormat("yyyy-dd-MM");
		                String formattedDates = dateFormats.format(currentDate);
		                
				        if (db.timeInCheck(NameLabel.getText(), formattedDates)) {
			        		timeIn.setVisible(false);
			        		timeOut.setVisible(true);
				        }else {
			        		timeIn.setVisible(true);
			        		timeOut.setVisible(false);
				        }
						
						
						
	}
}
