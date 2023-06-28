import java.awt.Color;
import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import javax.swing.border.LineBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.Image;

import javax.swing.SwingConstants;
import javax.swing.ImageIcon;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;

import java.sql.SQLException;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;


import com.itextpdf.io.exceptions.IOException;

//PDF
import com.itextpdf.kernel.colors.DeviceRgb;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.properties.TextAlignment;



import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableCellRenderer;

import java.util.Date;
import java.util.Calendar;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.swing.Timer;


public class ADMIN extends JFrame {
	

	
	
	
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					
					ADMIN frame = new ADMIN();
					
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
	
    private JComboBox comboBox_1 = new JComboBox<String>();
	private Database db = new Database();

	private JPanel contentPane;
	private JTable employeeListTable;
	private JTextField NameTextfield;
	private JTextField rate;
	private JTable employeeAttendance;
	private  JPanel EmployeeAttendance = new JPanel();
	
	private Object [] tableColumnHeaders = {"employee ID", "Name"};
	
	//Import data from system
	private Table generatePayrollTableByPosition(DefaultTableModel tableModel, String tableTitle) {
	    Table pdfTable = new Table(2).useAllAvailableWidth();
	    pdfTable.setMarginTop(0);
	    pdfTable.setMarginBottom(20);

	    // Table main header
	    Cell cell = new Cell(1, 7).add(new Paragraph(tableTitle));
	    cell.setTextAlignment(TextAlignment.CENTER);
	    cell.setPadding(5);
	    cell.setBackgroundColor(new DeviceRgb(209, 200, 234));
	    pdfTable.addCell(cell);

	    // Generate first row for table header
	    for (int i = 0; i < tableColumnHeaders.length; i++) {
	        pdfTable.addCell(tableColumnHeaders[i].toString());
	    }

	    // Generate rows based on table model
	    for (int j = 0; j < tableModel.getRowCount(); j++) {
	        for (int i = 0; i < tableColumnHeaders.length; i++) {
	            String rowData = tableModel.getValueAt(j, i).toString();
	            pdfTable.addCell(rowData);
	        }
	    }

	    // Check if the last row has fewer cells
	    int lastRowCellCount = pdfTable.getNumberOfColumns();
	    int expectedCellCount = tableColumnHeaders.length;
	    if (lastRowCellCount < expectedCellCount) {
	        int emptyCellCount = expectedCellCount - lastRowCellCount;
	        for (int i = 0; i < emptyCellCount; i++) {
	            pdfTable.addCell("");
	        }
	    }

	    return pdfTable;
	}


		
	private void serveFileOnBrowser(File file) throws IOException, java.io.IOException {
		try {
			Desktop desktop = Desktop.getDesktop();
			desktop.open(file);
		} catch (IOException e) {
			throw new IOException("Open PDF in Browser: Something went wrong.\n Error: " + e.getMessage());
		}
	}
	
	
	private void generatePayrollPDF(JTable table) throws IOException {
        try {
        DefaultTableModel tblmodel = (DefaultTableModel)table.getModel();
		
		LocalDateTime dateNowTime = LocalDateTime.now();
		System.out.println(dateNowTime.toString());
		DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("MMMM dd, YYYY");
		DateTimeFormatter fileDateFormatter = DateTimeFormatter.ofPattern("MMMM_dd_YYYY_hh_mm_ss_a_n");
		
		String exportDate = dateNowTime.format(dateFormatter).toString();
		String fileExportDate = dateNowTime.format(fileDateFormatter).toString();
		
		String fileName = "Payroll_Export_" + fileExportDate + ".pdf";
		String fileDest = "pdfs/" + fileName;
		
		File file = new File(fileDest);
        file.getParentFile().mkdirs();
        
        // Initialize PDF writer
        PdfWriter writer = new PdfWriter(fileDest);

        //Initialize PDF document
        PdfDocument pdf = new PdfDocument(writer);

        // Initialize document
        Document document = new Document(pdf, new PageSize(595, 842));
        
        
        Paragraph headerText = new Paragraph("First Fruit Telecom Payroll System ").setFontSize(24);
        document.add(headerText);
        
        Paragraph dateText = new Paragraph("Export Date: " + exportDate);
        document.add(dateText);
        
        // Employee List
        Table fullTimeEmployeesTable = generatePayrollTableByPosition(
        		tblmodel,
        		"Employee List"
        );
        document.add(fullTimeEmployeesTable);
        

        
        
        //Close document
        document.close();
        
        // Serve PDF on the default browser

			serveFileOnBrowser(file);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (java.io.IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public ADMIN() {
        JPanel EmployeeList = new JPanel();
        JPanel AddEmployee = new JPanel();
		
		 // Create a Calendar instance
        Calendar calendar = Calendar.getInstance();
        
        // Get the current date
        Date currentDate = calendar.getTime();
        
        // Format the date
        SimpleDateFormat dateFormat = new SimpleDateFormat("EE, MMM d yyyy");
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
		logout.setBounds(0, 0, 222, 42);
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
		
		JLabel employelist = new JLabel("Employee List");
		employelist.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				
				employeListButton.setBorder(new LineBorder(new Color(0, 0, 0)));
				employeeAttendanceButton.setBorder(null);
				addEmployeeButton.setBorder(null);
				
//				employeeListTable
				db.showRows(employeeListTable);
				
			    EmployeeList.setVisible(true);
		        AddEmployee.setVisible(false);
		        EmployeeAttendance.setVisible(false);
				
				
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				employelist.setCursor(new Cursor(Cursor.HAND_CURSOR));
			}
			
		});
		employelist.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		employelist.setHorizontalAlignment(SwingConstants.CENTER);
		employelist.setBounds(0, 0, 222, 53);
		employeListButton.add(employelist);
		

		employeeAttendanceButton.setLayout(null);
		employeeAttendanceButton.setBorder(null);
		employeeAttendanceButton.setBackground(new Color(225, 244, 243));
		employeeAttendanceButton.setBounds(10, 300, 222, 53);
		sidebar.add(employeeAttendanceButton);
		
		JLabel employeeattendance = new JLabel("Employee Attendance");
		employeeattendance.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				employeListButton.setBorder(null);
				employeeAttendanceButton.setBorder(new LineBorder(new Color(0, 0, 0)));
				addEmployeeButton.setBorder(null);
				
			    EmployeeList.setVisible(false);
		        AddEmployee.setVisible(false);
		        EmployeeAttendance.setVisible(true);
		        
		       
//		        show all dates and show the data on tables
		       db.showAttendance_dATE(comboBox_1);
		       
		       
//		       String curren_date = String.valueOf(comboBox_1.getSelectedItem());
//		       System.out.println(curren_date);
//		       db.showRows_Attendace(employeeAttendance,curren_date);
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				employeeattendance.setCursor(new Cursor(Cursor.HAND_CURSOR));
			}
		});
		employeeattendance.setHorizontalAlignment(SwingConstants.CENTER);
		employeeattendance.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		employeeattendance.setBounds(0, 0, 222, 53);
		employeeAttendanceButton.add(employeeattendance);
		

		addEmployeeButton.setLayout(null);
		addEmployeeButton.setBorder(null);
		addEmployeeButton.setBackground(new Color(225, 244, 243));
		addEmployeeButton.setBounds(10, 236, 222, 53);
		sidebar.add(addEmployeeButton);
		
		JLabel addemployee = new JLabel("Add Employee");
		addemployee.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				employeListButton.setBorder(null);
				employeeAttendanceButton.setBorder(null);
				addEmployeeButton.setBorder(new LineBorder(new Color(0, 0, 0)));
				
		        EmployeeList.setVisible(false);
		        AddEmployee.setVisible(true);
				EmployeeAttendance.setVisible(false);
				
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				addemployee.setCursor(new Cursor(Cursor.HAND_CURSOR));
			}
		});
		addemployee.setHorizontalAlignment(SwingConstants.CENTER);
		addemployee.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		addemployee.setBounds(0, 0, 222, 53);
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
				brandTitle.setBounds(533, 11, 222, 97);
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
//						EmployeeAttendance.setVisible(false);
				        
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
        		        
        		        

        
	
        

//		set defaul data 
        

        

        EmployeeList.setBackground(Color.WHITE);
        EmployeeList.setBounds(256, 113, 764, 476);
        contentPane.add(EmployeeList);
        EmployeeList.setLayout(null);
        
        JPanel printtmployeeButton = new JPanel();
        printtmployeeButton.setBorder(new LineBorder(new Color(0, 0, 0)));
        
        	printtmployeeButton.setLayout(null);
        	printtmployeeButton.setBackground(Color.BLACK);
        	printtmployeeButton.setBounds(398, 422, 222, 42);
        	EmployeeList.add(printtmployeeButton);
        	
        	JLabel printEmployee = new JLabel("Print");
        	printEmployee.addMouseListener(new MouseAdapter() {
        		@Override
        		public void mouseClicked(MouseEvent e) {
        			generatePayrollPDF(employeeListTable);
        		}
        		@Override
        		public void mouseExited(MouseEvent e) {
        			printEmployee.setCursor(new Cursor(Cursor.HAND_CURSOR));
        		}
        	});
        	printEmployee.setHorizontalAlignment(SwingConstants.CENTER);
        	printEmployee.setForeground(Color.WHITE);
        	printEmployee.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        	printEmployee.setBounds(0, 0, 222, 42);
        	printtmployeeButton.add(printEmployee);
        	
        	JScrollPane employeeListPane = new JScrollPane();
        	employeeListPane.setViewportBorder(null);
        	employeeListPane.setBounds(10, 11, 744, 400);
        	EmployeeList.add(employeeListPane);
        	
        	employeeListTable = new JTable();
        	employeeListPane.setViewportView(employeeListTable);
        	employeeListTable.setBorder(null);
        	employeeListTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        	employeeListTable.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        	employeeListTable.setDefaultRenderer(Object.class, centerRenderer);
        	
        	// Set the row selection background color
        	employeeListTable.setSelectionBackground(Color.WHITE);
        	
						employeeListTable.setModel(new DefaultTableModel(
        	new Object[][] {
        		{"0", "Please click refresh"},
        	},
        	new String[] {
        		"employee ID", "Name"
        	}
						));
						
				        db.showRows(employeeListTable);
				        
				        employeeListTable.setBackground(new Color(225, 244, 243));
				        
				        JPanel refreshEmployeeList = new JPanel();
				        refreshEmployeeList.setLayout(null);
				        refreshEmployeeList.setBackground(Color.BLACK);
				        refreshEmployeeList.setBounds(166, 422, 222, 42);
				        EmployeeList.add(refreshEmployeeList);
				        
				        JLabel refreshListButton = new JLabel("Refresh");
				        refreshListButton.addMouseListener(new MouseAdapter() {
				        	@Override
				        	public void mouseClicked(MouseEvent e) {
				        		db.showRows(employeeListTable);
				        	}
				        	
				        	@Override
				        	public void mouseEntered(MouseEvent e) {
				        		refreshEmployeeList.setCursor(new Cursor(Cursor.HAND_CURSOR));
				        	}
				        });
				        refreshListButton.setHorizontalAlignment(SwingConstants.CENTER);
				        refreshListButton.setForeground(Color.WHITE);
				        refreshListButton.setFont(new Font("Segoe UI", Font.PLAIN, 14));
				        refreshListButton.setBounds(0, 0, 222, 42);
				        refreshEmployeeList.add(refreshListButton);
				        
        JComboBox comboBox = new JComboBox();

        
        		        

        		        AddEmployee.setBackground(Color.WHITE);
        		        AddEmployee.setBounds(256, 113, 764, 476);
        		        contentPane.add(AddEmployee);
        		        AddEmployee.setLayout(null);
        		        
        		        NameTextfield = new JTextField();
        		        NameTextfield.setHorizontalAlignment(SwingConstants.CENTER);
        		        NameTextfield.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        		        NameTextfield.setColumns(10);
        		        NameTextfield.setBounds(10, 113, 744, 53);
        		        AddEmployee.add(NameTextfield);
        		        
        		        JLabel Name_1 = new JLabel("Name");
        		        Name_1.setHorizontalAlignment(SwingConstants.CENTER);
        		        Name_1.setFont(new Font("Segoe UI", Font.PLAIN, 20));
        		        Name_1.setBounds(10, 74, 744, 40);
        		        AddEmployee.add(Name_1);
        		        

        		        
        		        JPanel refreshEmployeeList_1 = new JPanel();
        		        refreshEmployeeList_1.setLayout(null);
        		        refreshEmployeeList_1.setBackground(Color.BLACK);
        		        refreshEmployeeList_1.setBounds(164, 402, 222, 42);
        		        AddEmployee.add(refreshEmployeeList_1);
        		        
        		        JLabel lblCreateEmployee = new JLabel("Create Employee");
        		        lblCreateEmployee.addMouseListener(new MouseAdapter() {
        		        	@Override
        		        	public void mouseClicked(MouseEvent e) {
        		        		String username = NameTextfield.getText().replace(" ", "");
        		        		String Name = NameTextfield.getText();
        		        		String password = "employee123";
        		        		String salary = rate.getText();
        		        		String position = (String) comboBox.getSelectedItem();
        		        	
				   
        		        		try {
        		        			db.addEmployee(Name, username, password, salary, position);
									
		
								   String message = "Your Username is: " + username + "\nYour default Password is: " + password +"\nPlease change password";

								   JOptionPane.showMessageDialog(null, message, "Create Employee Success!", JOptionPane.INFORMATION_MESSAGE);
								   
								   comboBox.setSelectedItem("please select position");
								   NameTextfield.setText("");
								        
								} catch (SQLException e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
									System.out.println(e1.toString());
									JOptionPane.showMessageDialog(null, "Unable to create employee", "Error!", JOptionPane.INFORMATION_MESSAGE);
								}
        		        	}
        		        });
        		        lblCreateEmployee.setHorizontalAlignment(SwingConstants.CENTER);
        		        lblCreateEmployee.setForeground(Color.WHITE);
        		        lblCreateEmployee.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        		        lblCreateEmployee.setBounds(10, 11, 202, 20);
        		        
        		        refreshEmployeeList_1.add(lblCreateEmployee);
        		        
        		        JPanel refreshEmployeeList_1_1 = new JPanel();
        		        refreshEmployeeList_1_1.setBorder(new LineBorder(new Color(0, 0, 0)));
        		        refreshEmployeeList_1_1.setLayout(null);
        		        refreshEmployeeList_1_1.setBackground(Color.WHITE);
        		        refreshEmployeeList_1_1.setBounds(396, 402, 222, 42);
        		        AddEmployee.add(refreshEmployeeList_1_1);
        		        
        		        JLabel lblCancel = new JLabel("Cancel");
        		        lblCancel.setBackground(Color.WHITE);
        		        lblCancel.setHorizontalAlignment(SwingConstants.CENTER);
        		        lblCancel.setForeground(Color.BLACK);
        		        lblCancel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        		        lblCancel.setBounds(10, 11, 202, 20);
        		        refreshEmployeeList_1_1.add(lblCancel);
        		        
        		        JLabel lblPosition = new JLabel("Position");
        		        lblPosition.setHorizontalAlignment(SwingConstants.CENTER);
        		        lblPosition.setFont(new Font("Segoe UI", Font.PLAIN, 20));
        		        lblPosition.setBounds(10, 166, 744, 53);
        		        AddEmployee.add(lblPosition);
        		        
        		        rate = new JTextField();
        		        rate.setEditable(false);
        		        rate.setHorizontalAlignment(SwingConstants.CENTER);
        		        rate.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        		        rate.setColumns(10);
        		        rate.setBounds(10, 320, 744, 53);
        		        AddEmployee.add(rate);
        		        
        		        JLabel lblRate = new JLabel("Rate");
        		        lblRate.setHorizontalAlignment(SwingConstants.CENTER);
        		        lblRate.setFont(new Font("Segoe UI", Font.PLAIN, 20));
        		        lblRate.setBounds(20, 269, 744, 40);
        		        AddEmployee.add(lblRate);
        		        AddEmployee.setVisible(false);
        		        
        		        
//				        JComboBox comboBox = new JComboBox();
        		        comboBox.addActionListener(new ActionListener() {
        		        	public void actionPerformed(ActionEvent e) {
        		        		System.out.println(comboBox.getSelectedItem());
        		        		
//				        		Officer in Charge (OIC)
//				        		Sales staff
//				        		Technician
        		        		
        		        		switch(String.valueOf(comboBox.getSelectedItem())) {
				       				case "Supervisor":
				       					rate.setText("600");       					
        		        			break;
        		        			
        		        			case "Officer in Charge (OIC)":
        		        				rate.setText("450");
        		        			break;
        		        				
				     				case "Sales staff":
				     					rate.setText("430");
					        		break;
					        			
					        		case "Technician":
					        			rate.setText("440");
					        		break;
        		        		}
        		        	}
        		        	
        		        });
        		        comboBox.setModel(new DefaultComboBoxModel(new String[] {"please select position", "Supervisor", "Officer in Charge (OIC)", "Sales staff", "Technician"}));
        		        
        		        comboBox.setBackground(Color.WHITE);
        		        comboBox.setFont(new Font("Segoe UI", Font.PLAIN, 20));
        		        comboBox.setBounds(177, 218, 425, 40);
        		        AddEmployee.add(comboBox);
        		        
        		        employeeAttendance = new JTable();
 
        		        
	
        		        EmployeeAttendance.setBounds(256, 113, 764, 476);
        		        contentPane.add(EmployeeAttendance);
        		        EmployeeAttendance.setLayout(null);
        		        
        		        JScrollPane scrollPane_1 = new JScrollPane();
        		        scrollPane_1.setBounds(10, 58, 744, 407);
        		        EmployeeAttendance.add(scrollPane_1);
        		        

        		        employeeAttendance.setModel(new DefaultTableModel(
        		        	new Object[][] {
        		        	},
        		        	new String[] {
        		        		"employee ID", "Name", "Time In", "Time Out", "work hours", "tardiness"
        		        	}
        		        ));
        		        scrollPane_1.setViewportView(employeeAttendance);
        		        
        		   
        		        comboBox_1.addActionListener(new ActionListener() {
        		        	public void actionPerformed(ActionEvent e) {
//		        show all dates
//		       db.showAttendance_dATE(comboBox_1);
        		               
        		               
        		               String curren_date = String.valueOf(comboBox_1.getSelectedItem());
        		               System.out.println(curren_date);
//		       
        		               db.showRows_Attendace(employeeAttendance, curren_date);
        		        	}
        		        });
        		        comboBox_1.setBackground(Color.WHITE);
        		        comboBox_1.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        		        comboBox_1.setBounds(210, 11, 277, 36);
        		        EmployeeAttendance.add(comboBox_1);
        		        EmployeeAttendance.setVisible(false);
				        
				     
					    EmployeeList.setVisible(true);
				        AddEmployee.setVisible(false);
						
						
						
	}
}
