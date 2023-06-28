import java.awt.Color;
import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;
import javax.swing.JLabel;
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
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.colors.DeviceRgb;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfPage;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.TextAlignment;

import javax.swing.table.DefaultTableCellRenderer;

import java.util.Date;
import java.util.Calendar;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
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
	
	private JComboBox<String> comboBox = new JComboBox<String>();
	private JPanel salaryHistory = new JPanel();
	private JPanel TimeInOut = new JPanel();
	private JLabel tardinessResult_1 = new JLabel("0");
	private JLabel regularResult_1 = new JLabel("0");
	private JLabel grosspayResult_1 = new JLabel("0.0");
	private JLabel netIncomeLabel_1 = new JLabel("Net Income: ");
	private JLabel netIncomeResult = new JLabel("0");
	private JLabel netIncomeResult_1 = new JLabel("0");
	private JLabel grossPayResultLabel_1 = new JLabel("Total Gross pay: ");
	private JLabel workDaysLabel_1 = new JLabel("Total Regular work days: 0.0");
	private JTextField oldPassword;
	private JTextField newPassword;
	private JPanel changePassword = new JPanel();
    
	private JPanel timeIn_2 = new JPanel();
	private JPanel timeOut_2 = new JPanel();
    
	

		
	private void serveFileOnBrowser(File file) throws IOException, java.io.IOException {
		try {
			Desktop desktop = Desktop.getDesktop();
			desktop.open(file);
		} catch (IOException e) {
			throw new IOException("Open PDF in Browser: Something went wrong.\n Error: " + e.getMessage());
		}
	}
	
	
	private void generatePayrollPDF(
			String ID, 
			String Name,
			
			String workHourss,
			String workDayss,
			String tard, 
			String rate,
			String grossPayss,
			
			
			String netIncomes,
			String totalGrossPay
			) throws IOException {
	    try {
	        // Get the current date and time
	        LocalDateTime dateNowTime = LocalDateTime.now();
	        System.out.println(dateNowTime.toString());

	        // Format the date for display
	        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("MMMM dd, yyyy");
	        DateTimeFormatter fileDateFormatter = DateTimeFormatter.ofPattern("MMMM_dd_yyyy_hh_mm_ss_a_n");
	        String exportDate = dateNowTime.format(dateFormatter);
	        String fileExportDate = dateNowTime.format(fileDateFormatter);

	        // Generate the file name and destination
	        String fileName = "Payroll_Export_" + fileExportDate + ".pdf";
	        String fileDest = "pdfs/" + fileName;

	        // Create the file and its parent directories
	        File file = new File(fileDest);
	        file.getParentFile().mkdirs();

	        // Initialize PDF writer
	        PdfWriter writer = new PdfWriter(fileDest);

	        // Initialize PDF document
	        PdfDocument pdf = new PdfDocument(writer);

	        // Initialize document
	        Document document = new Document(pdf, new PageSize(595, 842));

	        // Add the header
	        Paragraph headerText = new Paragraph("First Fruit Telecom Payroll System ").setFontSize(24).setBold();
	        document.add(headerText);

	        // Add the export date
	        Paragraph dateText = new Paragraph("Export Date: " + exportDate);
	        document.add(dateText);

	        // Add the employee name
	        String employeeName = Name; // Replace with actual employee name
	        Paragraph employeeNameParagraph = new Paragraph("Employee Name: " + employeeName);
	        document.add(employeeNameParagraph);

	        // Add the employee ID
	        Paragraph employeeIDParagraph = new Paragraph("Employee ID: " + ID);
	        document.add(employeeIDParagraph);

	        // Create a PdfCanvas object from the first page
	        PdfPage pdfPage = pdf.getFirstPage();
	        PdfCanvas canvas = new PdfCanvas(pdfPage);

	        // Set the line properties
	        canvas.setLineWidth(1f); // Line width in points
	        canvas.setStrokeColor(ColorConstants.BLACK); // Line color
	        
	        // Draw the line
	        canvas.moveTo(30, 680); // Starting point
	        canvas.lineTo(580, 680); // Ending point
	        canvas.stroke();
	        
	        // Total Gross Summary
	        Paragraph TotalGrossSummary = new Paragraph("Total Gross pay summary ").setFontSize(20).setBold();
	        document.add(TotalGrossSummary);
	       
	        Paragraph workDays = new Paragraph("Total Regular workdays: " + workDayss);
	        document.add(workDays);
	        
	        Paragraph regWorkHours = new Paragraph("Regular workhours: " + workHourss);
	        document.add(regWorkHours);
	        
	        Paragraph tardiness = new Paragraph("LESS:Tardiness Hours:  " + tard);
	        document.add(tardiness);
	        
	        Paragraph dailyRate = new Paragraph("Daily Rate:  " + rate);
	        document.add(dailyRate);
	        
	        Paragraph grossPay = new Paragraph("Total Gross Pay:  " + grossPayss);
	        document.add(grossPay);
	        
	        // Draw the line
	        canvas.moveTo(30, 515); // Starting point
	        canvas.lineTo(580, 515); // Ending point
	        canvas.stroke();
	        
	        // Total Gross Summary
	        Paragraph deducrionSummary = new Paragraph("Deduction Summary per week ").setFontSize(20).setBold();
	        document.add(deducrionSummary);
	        
	        Paragraph SSS = new Paragraph("SSS: 99");
	        document.add(SSS);
	        
	        Paragraph Philhealth = new Paragraph("Philhealth: 40");
	        document.add(Philhealth);
	        
	        Paragraph PagIBIG = new Paragraph("PagIBIG:  20");
	        document.add(PagIBIG);
	        
	        // Draw the line
	        canvas.moveTo(30, 400); // Starting point
	        canvas.lineTo(580, 400); // Ending point
	        canvas.stroke();
	        
	        // net income result
	        Paragraph TotalGrossPay = new Paragraph("Total Gross pay: " + totalGrossPay).setFontSize(12).setBold();
	        document.add(TotalGrossPay);
	        
	        Paragraph tottalDedud = new Paragraph("LESS:Total deductions: 159").setFontSize(12).setBold();
	        document.add(tottalDedud);
	        
	        Paragraph netIncome = new Paragraph("Net Income: " + netIncomes).setFontSize(12).setBold();
	        document.add(netIncome);
	        

	        // Close the document
	        document.close();

	        // Serve PDF on the default browser
	        serveFileOnBrowser(file);
	    } catch (IOException e) {
	        e.printStackTrace();
	    } catch (java.io.IOException e) {
	        e.printStackTrace();
	    }
	}
	
	public EMPLOYEE(int ID) {
		
		Database db = new Database();
		
		db.showEmployeeID();
		
		JLabel NameLabel = new JLabel(db.getEmployeeName(ID));
		
		 // Create a Calendar instance
        Calendar calendar = Calendar.getInstance();
        
        // Get the current date
        Date currentDate = calendar.getTime();
		
		

        SimpleDateFormat dateFormats = new SimpleDateFormat("yyyy-dd-MM");
        String formattedDatesss = dateFormats.format(currentDate);
        
//		ewmployee rate
        int rate = db.showSalary(String.valueOf(ID));
		
     
//		System.out.println(db.timeInCheck(String.valueOf(ID),formattedDatesss));
//		if (db.timeInCheck(String.valueOf(ID),formattedDatesss) == false) {
//			timeIn_2.setVisible(false);
//			timeOut_2.setVisible(true);
//		}else {
//			timeIn_2.setVisible(true);
//			timeOut_2.setVisible(false);
//		}
		
		
	
        
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
										        
										        JLabel lblSss = new JLabel("SSS: 99");
										        lblSss.setFont(new Font("Segoe UI", Font.PLAIN, 17));
										        lblSss.setBounds(72, 49, 225, 40);
										        panel.add(lblSss);
										        
										        JLabel lblSss_1 = new JLabel("PhilHealth: 40");
										        lblSss_1.setFont(new Font("Segoe UI", Font.PLAIN, 17));
										        lblSss_1.setBounds(72, 71, 225, 40);
										        panel.add(lblSss_1);
										        
										        JLabel lblSss_2 = new JLabel("PagIbig: 20");
										        lblSss_2.setFont(new Font("Segoe UI", Font.PLAIN, 17));
										        lblSss_2.setBounds(72, 97, 225, 40);
										        panel.add(lblSss_2);
										        
										        JLabel lblSss_2_2 = new JLabel("Total Deduction: 159");
										        lblSss_2_2.setHorizontalAlignment(SwingConstants.CENTER);
										        lblSss_2_2.setFont(new Font("Segoe UI", Font.PLAIN, 17));
										        lblSss_2_2.setBounds(10, 148, 333, 40);
										        panel.add(lblSss_2_2);
										        
										        JPanel totalGross = new JPanel();
										        totalGross.setLayout(null);
										        totalGross.setBorder(new LineBorder(new Color(0, 0, 0)));
										        totalGross.setBounds(10, 120, 353, 214);
										        TimeInOut.add(totalGross);
										        
										        JLabel TotalGrossPay = new JLabel("Total Gross Pay summary");
										        TotalGrossPay.setHorizontalAlignment(SwingConstants.CENTER);
										        TotalGrossPay.setFont(new Font("Segoe UI", Font.PLAIN, 15));
										        TotalGrossPay.setBounds(10, 11, 333, 27);
										        totalGross.add(TotalGrossPay);
										        
										        JLabel workDaysLabel = new JLabel("Total Regular work days: 0");
										        workDaysLabel.setFont(new Font("Segoe UI", Font.PLAIN, 17));
										        workDaysLabel.setBounds(53, 49, 290, 40);
										        totalGross.add(workDaysLabel);
										        
										        JLabel workHoursLabel = new JLabel("Regular Work hours:");
										        workHoursLabel.setFont(new Font("Segoe UI", Font.PLAIN, 17));
										        workHoursLabel.setBounds(53, 71, 162, 40);
										        totalGross.add(workHoursLabel);
										        
										        JLabel totalGrossLabel = new JLabel("Total Gross pay: ");
										        totalGrossLabel.setHorizontalAlignment(SwingConstants.CENTER);
										        totalGrossLabel.setFont(new Font("Segoe UI", Font.PLAIN, 17));
										        totalGrossLabel.setBounds(10, 171, 333, 40);
										        totalGross.add(totalGrossLabel);
										        
										        JLabel tardinessLabel = new JLabel("LESS: Tardiness Hours: ");
										        tardinessLabel.setFont(new Font("Segoe UI", Font.PLAIN, 17));
										        tardinessLabel.setBounds(53, 95, 174, 40);
										        totalGross.add(tardinessLabel);
										        
										        JLabel rateLabel = new JLabel("Daily Rate: " + db.showSalary(String.valueOf(ID)));
										        rateLabel.setFont(new Font("Segoe UI", Font.PLAIN, 17));
										        rateLabel.setBounds(53, 120, 290, 40);
										        totalGross.add(rateLabel);
										        
										        JLabel tardinessResult = new JLabel("0");
										        tardinessResult.setFont(new Font("Segoe UI", Font.PLAIN, 17));
										        tardinessResult.setBounds(225, 95, 118, 40);
										        totalGross.add(tardinessResult);
										        
										        JLabel regularResult = new JLabel("0");
										        regularResult.setFont(new Font("Segoe UI", Font.PLAIN, 17));
										        regularResult.setBounds(210, 71, 133, 40);
										        totalGross.add(regularResult);
										        
										        JLabel grosspayResult = new JLabel("0");
										        grosspayResult.setFont(new Font("Segoe UI", Font.PLAIN, 17));
										        grosspayResult.setBounds(236, 171, 107, 40);
										        totalGross.add(grosspayResult);
										        
										        JPanel netIncome = new JPanel();
										        netIncome.setBorder(new LineBorder(new Color(0, 0, 0)));
										        netIncome.setBounds(156, 349, 476, 127);
										        TimeInOut.add(netIncome);
										        netIncome.setLayout(null);
										        
										        JLabel netIncomeLabel = new JLabel("Net Income: ");
										        netIncomeLabel.setBounds(10, 87, 393, 27);
										        netIncome.add(netIncomeLabel);
										        netIncomeLabel.setHorizontalAlignment(SwingConstants.CENTER);
										        netIncomeLabel.setFont(new Font("Segoe UI", Font.PLAIN, 20));
										        
										        JLabel deductionLabel = new JLabel("LESS: Total Deductions: 159");
										        deductionLabel.setHorizontalAlignment(SwingConstants.CENTER);
										        deductionLabel.setFont(new Font("Segoe UI", Font.PLAIN, 20));
										        deductionLabel.setBounds(10, 49, 456, 27);
										        netIncome.add(deductionLabel);
										        
										        JLabel grossPayResultLabel = new JLabel("Total Gross pay: " + grosspayResult.getText());
										        grossPayResultLabel.setHorizontalAlignment(SwingConstants.CENTER);
										        grossPayResultLabel.setFont(new Font("Segoe UI", Font.PLAIN, 20));
										        grossPayResultLabel.setBounds(10, 11, 456, 27);
										        netIncome.add(grossPayResultLabel);
										        

										        netIncomeResult.setBounds(259, 87, 207, 27);
										        netIncome.add(netIncomeResult);
										        netIncomeResult.setFont(new Font("Segoe UI", Font.PLAIN, 20));
										        
										        		        timeIn_2.setLayout(null);
										        		        timeIn_2.setBorder(new LineBorder(new Color(0, 0, 0)));
										        		        timeIn_2.setBackground(Color.WHITE);
										        		        timeIn_2.setBounds(257, 39, 222, 42);
										        		        TimeInOut.add(timeIn_2);
										        		        
										        		        JLabel timeOutButton = new JLabel("Time Out");
										        		        JLabel timeInButton = new JLabel("Time In");
										        		        
										        		        timeInButton.addMouseListener(new MouseAdapter() {
										        		        	@Override
										        		        	public void mouseEntered(MouseEvent e) {
										        		        		timeInButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
										        		        	}
										        		        	
										        		        	@Override
										        		        	public void mouseClicked(MouseEvent e) {
										        		        		
										        		        		
										        		        		
										        		        		timeIn_2.setVisible(false);
										        		        		timeOut_2.setVisible(true);
										        		        		
										        		        		
										        		        
										        		        	    // Get the current date
										        		                Date currentDateNow = calendar.getTime();
										        		        	
										        		                
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

				      
			                    double tardiness = (minutesLate / 60.0);
			                    
			        			try {
			        				
			        				if (db.timeInCheck(NameLabel.getText(), formattedDate) == false) {
			        					db.addAttendance(ID, date, NameLabel.getText(), timeIn, timeOut, workHours, tardiness);
					        		}
			        				
//			        				db.addAttendance(ID, date, NameLabel.getText(), timeIn, timeOut, workHours, tardiness);
			        				
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
										        		        timeInButton.setBounds(0, 0, 222, 42);
										        		        timeIn_2.add(timeInButton);
										        		        
			
										        		        
										        		        timeOut_2.setLayout(null);
										        		        timeOut_2.setBackground(Color.BLACK);
										        		        timeOut_2.setBounds(257, 39, 222, 42);
										        		        TimeInOut.add(timeOut_2);
										        		        
										        		        
										        		        timeOutButton.addMouseListener(new MouseAdapter() {
										        		        	@Override
										        		        	public void mouseEntered(MouseEvent e) {
										        		        		timeOutButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
										        		        	}
										        		        	
										        		        	@Override
										        		        	public void mouseClicked(MouseEvent e) {
										        		        		
										        		        		timeIn_2.setVisible(true);
										        		        		timeOut_2.setVisible(false);
										        		        		
//				          		Time Out
										        		                SimpleDateFormat timeOutFormat = new SimpleDateFormat("hh:mm a");
										        		                
										        		                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh:mm a");
										        		                
										        		                String timeOut = timeOutFormat.format(calendar.getTime());
										        		        		
										        		                LocalTime enterTime = LocalTime.of(8, 0, 0);
										        		                LocalTime timeOuts = LocalTime.parse(timeOut,formatter);
										        		                

										        		                // Calculate the duration between clock in and clock out times
										        		                Duration workedDuration = Duration.between(enterTime, timeOuts);
										        		                
										        		                // Adjust worked hours if time-out is after 7 PM
										        		                if (timeOuts.isAfter(LocalTime.of(17, 0, 0))) {
										        		                    workedDuration = Duration.ofHours(8);
										        		                }
										        		                
										        		                
//				                work hours
										        		                double workHours = (workedDuration.toMinutes()/60.0);
										        		                double currentWorkHours = Double.parseDouble(regularResult.getText());
										        		                
										        		                
//				                get the current work hours
										        		                

										        		        		// Format the date
										        		                SimpleDateFormat dateFormats = new SimpleDateFormat("yyyy-dd-MM");
										        		                String formattedDates = dateFormats.format(currentDate);
										        		        		
//			
//				                rate;
										        		                
										        		                Double tard = db.getEmployeeTardiness(ID, formattedDates);
		
										        		                
										        		        		if (db.timeOutCheck(NameLabel.getText(), formattedDates)) {
										        		        			
										        		        			System.out.println("pwede kapa mag time Out");
										        		        			
										        		        			
										        		        			try {
										        		        				
										        		        				workHours = workHours - tard;
										        		        				
//				        				total of yesterday workhours + new work hours
										        		        				currentWorkHours += workHours;
										        		        				
//				        				convert workhours into days
										        		        				double daysHours = currentWorkHours / 8;
										        		        				
//				        				compute the gross pay
										        		        				Double grossPays = daysHours * rate;
										        		        				
//				        				compute net income
										        		        				
										        		        				Double netIncomes = grossPays - 159;
										        		        				
//				        				get yesterday and add new tardiness
										        		        				Double currentTardiness =  Double.parseDouble(tardinessResult.getText());
										        		        				currentTardiness += tard;
										        		        				
										        		        
										        		        				
//				        				
										db.updateAttendance(ID, formattedDates,timeOut,workHours);
										db.updateEmployeePayrol_History(String.valueOf(ID), currentWorkHours, currentTardiness, 159, grossPays, netIncomes);
										
//								        get the workhours and tardiness result yesterday
										        		        db.showEmployeePayrol_History(String.valueOf(ID), regularResult, tardinessResult, grosspayResult, netIncomeResult);
										        		        				
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
										        		        timeOutButton.setBounds(0, 0, 222, 42);
										        		        timeOut_2.add(timeOutButton);
										        		        
	
						
						
						computation(ID,rate,regularResult,tardinessResult,grosspayResult,netIncomeResult,workDaysLabel,grossPayResultLabel);
		
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
		
		JLabel employelist = new JLabel("Time In / Time Out");
		employelist.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				
				employeListButton.setBorder(new LineBorder(new Color(0, 0, 0)));
				employeeAttendanceButton.setBorder(null);
				addEmployeeButton.setBorder(null);
				
				TimeInOut.setVisible(true);
				salaryHistory.setVisible(false);
				changePassword.setVisible(false);

				
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
		
		JLabel employeeattendance = new JLabel("Change Password");
		employeeattendance.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				employeListButton.setBorder(null);
				employeeAttendanceButton.setBorder(new LineBorder(new Color(0, 0, 0)));
				addEmployeeButton.setBorder(null);
				
				TimeInOut.setVisible(false);
				salaryHistory.setVisible(false);
				changePassword.setVisible(true);
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
		
		JLabel addemployee = new JLabel("Salary History");
		addemployee.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				employeListButton.setBorder(null);
				employeeAttendanceButton.setBorder(null);
				addEmployeeButton.setBorder(new LineBorder(new Color(0, 0, 0)));
				
				TimeInOut.setVisible(false);
				salaryHistory.setVisible(true);
				changePassword.setVisible(false);
				
				db.showPayroll_historyID(comboBox, String.valueOf(ID));
				
				System.out.println("The selected ittem :" + comboBox.getSelectedItem());
				
				db.showPayroll_history(
						String.valueOf(ID), 
						String.valueOf(comboBox.getSelectedItem()), 
						workDaysLabel_1,
						regularResult_1, 
						tardinessResult_1, 
						grosspayResult_1, 
						netIncomeResult_1);

				grossPayResultLabel_1.setText("Total Gross pay: " + grosspayResult_1.getText());
				
				
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
						salaryHistory.setVisible(false);
						
			
						salaryHistory.setLayout(null);
						salaryHistory.setBackground(Color.WHITE);
						salaryHistory.setBounds(256, 113, 764, 476);
						contentPane.add(salaryHistory);
						
						JPanel deductionSummary = new JPanel();
						deductionSummary.setLayout(null);
						deductionSummary.setBorder(new LineBorder(new Color(0, 0, 0)));
						deductionSummary.setBounds(401, 120, 353, 199);
						salaryHistory.add(deductionSummary);
						
						JLabel lblDeducionSummary_1 = new JLabel("Deduction Summary per week");
						lblDeducionSummary_1.setHorizontalAlignment(SwingConstants.CENTER);
						lblDeducionSummary_1.setFont(new Font("Segoe UI", Font.PLAIN, 15));
						lblDeducionSummary_1.setBounds(10, 11, 333, 27);
						deductionSummary.add(lblDeducionSummary_1);
						
						JLabel lblSss_3 = new JLabel("SSS: 99");
						lblSss_3.setFont(new Font("Segoe UI", Font.PLAIN, 17));
						lblSss_3.setBounds(72, 49, 225, 40);
						deductionSummary.add(lblSss_3);
						
						JLabel lblSss_1_1 = new JLabel("PhilHealth: 40");
						lblSss_1_1.setFont(new Font("Segoe UI", Font.PLAIN, 17));
						lblSss_1_1.setBounds(72, 71, 225, 40);
						deductionSummary.add(lblSss_1_1);
						
						JLabel lblSss_2_1 = new JLabel("PagIbig: 20");
						lblSss_2_1.setFont(new Font("Segoe UI", Font.PLAIN, 17));
						lblSss_2_1.setBounds(72, 97, 225, 40);
						deductionSummary.add(lblSss_2_1);
						
						JLabel lblSss_2_2_1 = new JLabel("Total Deduction: 159");
						lblSss_2_2_1.setHorizontalAlignment(SwingConstants.CENTER);
						lblSss_2_2_1.setFont(new Font("Segoe UI", Font.PLAIN, 17));
						lblSss_2_2_1.setBounds(10, 148, 333, 40);
						deductionSummary.add(lblSss_2_2_1);
						
						JPanel totalGross_1 = new JPanel();
						totalGross_1.setLayout(null);
						totalGross_1.setBorder(new LineBorder(new Color(0, 0, 0)));
						totalGross_1.setBounds(10, 120, 353, 214);
						salaryHistory.add(totalGross_1);
						
						JLabel TotalGrossPay_1 = new JLabel("Total Gross Pay summary");
						TotalGrossPay_1.setHorizontalAlignment(SwingConstants.CENTER);
						TotalGrossPay_1.setFont(new Font("Segoe UI", Font.PLAIN, 15));
						TotalGrossPay_1.setBounds(10, 11, 333, 27);
						totalGross_1.add(TotalGrossPay_1);
						

						workDaysLabel_1.setFont(new Font("Segoe UI", Font.PLAIN, 17));
						workDaysLabel_1.setBounds(53, 49, 290, 40);
						totalGross_1.add(workDaysLabel_1);
						
						JLabel workHoursLabel_1 = new JLabel("Regular Work hours:");
						workHoursLabel_1.setFont(new Font("Segoe UI", Font.PLAIN, 17));
						workHoursLabel_1.setBounds(53, 71, 162, 40);
						totalGross_1.add(workHoursLabel_1);
						
						JLabel totalGrossLabel_1 = new JLabel("Total Gross pay: ");
						totalGrossLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
						totalGrossLabel_1.setFont(new Font("Segoe UI", Font.PLAIN, 17));
						totalGrossLabel_1.setBounds(10, 171, 333, 40);
						totalGross_1.add(totalGrossLabel_1);
						
						JLabel tardinessLabel_1 = new JLabel("LESS: Tardiness Hours: ");
						tardinessLabel_1.setFont(new Font("Segoe UI", Font.PLAIN, 17));
						tardinessLabel_1.setBounds(53, 95, 174, 40);
						totalGross_1.add(tardinessLabel_1);
						
						JLabel rateLabel_1 = new JLabel("Daily Rate: " + db.showSalary(String.valueOf(ID)));
						rateLabel_1.setFont(new Font("Segoe UI", Font.PLAIN, 17));
						rateLabel_1.setBounds(53, 120, 290, 40);
						totalGross_1.add(rateLabel_1);
						
						
						tardinessResult_1.setFont(new Font("Segoe UI", Font.PLAIN, 17));
						tardinessResult_1.setBounds(225, 95, 118, 40);
						totalGross_1.add(tardinessResult_1);
						
					
						regularResult_1.setFont(new Font("Segoe UI", Font.PLAIN, 17));
						regularResult_1.setBounds(210, 71, 133, 40);
						totalGross_1.add(regularResult_1);
						
						
						grosspayResult_1.setFont(new Font("Segoe UI", Font.PLAIN, 17));
						grosspayResult_1.setBounds(236, 171, 107, 40);
						totalGross_1.add(grosspayResult_1);
						
						JPanel netIncome_1 = new JPanel();
						netIncome_1.setLayout(null);
						netIncome_1.setBorder(new LineBorder(new Color(0, 0, 0)));
						netIncome_1.setBounds(156, 349, 476, 127);
						salaryHistory.add(netIncome_1);
						
						
						netIncomeLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
						netIncomeLabel_1.setFont(new Font("Segoe UI", Font.PLAIN, 20));
						netIncomeLabel_1.setBounds(10, 87, 393, 27);
						netIncome_1.add(netIncomeLabel_1);
						
						JLabel deductionLabel_1 = new JLabel("LESS: Total Deductions: 159");
						deductionLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
						deductionLabel_1.setFont(new Font("Segoe UI", Font.PLAIN, 20));
						deductionLabel_1.setBounds(10, 49, 456, 27);
						netIncome_1.add(deductionLabel_1);
						

						grossPayResultLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
						grossPayResultLabel_1.setFont(new Font("Segoe UI", Font.PLAIN, 20));
						grossPayResultLabel_1.setBounds(10, 11, 456, 27);
						netIncome_1.add(grossPayResultLabel_1);
						

						netIncomeResult_1.setFont(new Font("Segoe UI", Font.PLAIN, 20));
						netIncomeResult_1.setBounds(259, 87, 207, 27);
						netIncome_1.add(netIncomeResult_1);
						comboBox.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent e) {
								
								grossPayResultLabel_1.setText("Total Gross pay: " + grosspayResult_1.getText());
								
//								show all the resultt
								db.showPayroll_history(
										String.valueOf(ID), 
										String.valueOf(comboBox.getSelectedItem()), 
										
										workDaysLabel_1,
										regularResult_1, 
										tardinessResult_1, 
										
										grosspayResult_1, 
										
										netIncomeResult_1
										
										);
								
								
								
								
							}
						});
						
						
						comboBox.setFont(new Font("Segoe UI", Font.PLAIN, 16));
						comboBox.setForeground(Color.BLACK);
						comboBox.setBackground(Color.WHITE);
						comboBox.setBounds(202, 42, 161, 36);
						salaryHistory.add(comboBox);
						
						JPanel printSalary = new JPanel();
						printSalary.setLayout(null);
						printSalary.setBorder(new LineBorder(new Color(0, 0, 0)));
						printSalary.setBackground(Color.BLACK);
						printSalary.setBounds(401, 42, 222, 42);
						salaryHistory.add(printSalary);
						
						JLabel printout_salaryButton = new JLabel("Print");
						printout_salaryButton.addMouseListener(new MouseAdapter() {
							@Override
							public void mouseExited(MouseEvent e) {
								printout_salaryButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
							}
							
							@Override
							public void mouseClicked(MouseEvent e) {
								
								generatePayrollPDF(
										String.valueOf(ID),
										NameLabel.getText(),
										
							            regularResult_1.getText(),
										workDaysLabel_1.getText(),
							            tardinessResult_1.getText(),
										rateLabel_1.getText(),
							            grosspayResult_1.getText(),
							            
							            netIncomeResult_1.getText(),
										grossPayResultLabel_1.getText()
							            
																	
																	
									);

							}
						});
						printout_salaryButton.setHorizontalAlignment(SwingConstants.CENTER);
						printout_salaryButton.setForeground(Color.WHITE);
						printout_salaryButton.setFont(new Font("Segoe UI", Font.PLAIN, 14));
						printout_salaryButton.setBackground(Color.WHITE);
						printout_salaryButton.setBounds(0, 0, 222, 42);
						printSalary.add(printout_salaryButton);
						
						db.showPayroll_historyID(comboBox, String.valueOf(ID));
						
						
//		db.timeOutCheck(NameLabel, );
//		

		changePassword.setVisible(false);
		
	
		changePassword.setBackground(Color.WHITE);
		changePassword.setBounds(256, 113, 764, 476);
		contentPane.add(changePassword);
		changePassword.setLayout(null);
		
		oldPassword = new JTextField();
		oldPassword.setHorizontalAlignment(SwingConstants.CENTER);
		oldPassword.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		oldPassword.setColumns(10);
		oldPassword.setBounds(104, 133, 586, 53);
		changePassword.add(oldPassword);
		
		JLabel oldPass_Label = new JLabel("Old Password");
		oldPass_Label.setFont(new Font("Segoe UI", Font.PLAIN, 20));
		oldPass_Label.setBounds(104, 80, 222, 40);
		changePassword.add(oldPass_Label);
		
		newPassword = new JTextField();
		newPassword.setHorizontalAlignment(SwingConstants.CENTER);
		newPassword.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		newPassword.setColumns(10);
		newPassword.setBounds(104, 250, 586, 53);
		changePassword.add(newPassword);
		
		JLabel newPass_Label = new JLabel("New Password");
		newPass_Label.setFont(new Font("Segoe UI", Font.PLAIN, 20));
		newPass_Label.setBounds(104, 197, 222, 40);
		changePassword.add(newPass_Label);
		
		JPanel changePass_Button = new JPanel();
		changePass_Button.setLayout(null);
		changePass_Button.setBorder(new LineBorder(new Color(0, 0, 0)));
		changePass_Button.setBackground(Color.BLACK);
		changePass_Button.setBounds(300, 366, 222, 42);
		changePassword.add(changePass_Button);
		
		JLabel changePassButton = new JLabel("Change Password");
		changePassButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				try {
					db.changePassword(String.valueOf(ID), oldPassword.getText(), newPassword.getText());
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			@Override
			public void mouseExited(MouseEvent e) {
				changePassButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
			}
		});
		changePassButton.setBounds(0, 0, 222, 42);
		changePass_Button.add(changePassButton);
		changePassButton.setHorizontalAlignment(SwingConstants.CENTER);
		changePassButton.setForeground(Color.WHITE);
		changePassButton.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		changePassButton.setBackground(Color.WHITE);

				        
	}
	
	public void computation(
			int ID,
			int rate,
			JLabel regularResult, 
			JLabel tardinessResult,
			JLabel grosspayResult, 
			JLabel netIncomeResult,
			JLabel workDaysLabel,
			JLabel grossPayResultLabel
			) {
		
//        compute ng sahod nila
        
        db.showEmployeePayrol_History(String.valueOf(ID), regularResult, tardinessResult, grosspayResult, netIncomeResult);
        

//        compute the total netincome 
        Double workHours = Double.parseDouble(regularResult.getText());
        Double tardiness = Double.parseDouble(tardinessResult.getText());
        
        Double workdays = (workHours - tardiness) / 8;
        workDaysLabel.setText("Total Regular work days: " + String.valueOf(workdays));
        
        
        double grossPay = workdays * rate;

        grosspayResult.setText(String.valueOf(grossPay));
        
        grossPayResultLabel.setText("Total Gross pay: " + grossPay);
       
        
        if (grossPay != 0) {
        	
        	netIncomeResult.setText(String.valueOf(grossPay - 159));
        }
		
       
		try {
			

//			updated the result
			db.updateEmployeePayrol_History(String.valueOf(ID), workHours, tardiness, 159, grossPay, Double.parseDouble(netIncomeResult.getText()));
			
//	        get the workhours and tardiness result yesterday
	        db.showEmployeePayrol_History(String.valueOf(ID), regularResult, tardinessResult, grosspayResult, netIncomeResult);
	        
	        
		} catch (NumberFormatException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
	}
}
