//KWEENTOLOME
//INF212

import java.awt.Color;

import java.awt.Desktop;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import java.util.LinkedList;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel; 

import java.io.File;
import java.io.IOException;

// PDF
import com.itextpdf.kernel.colors.DeviceRgb;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.properties.TextAlignment;

// SQL
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.SwingConstants;

public class DataOfEmployees {
		
 class Employee {
	 
		public Employee(Object[] tableRow) {
			// to create an object constructor for employee
		}
		
		// Class attributes
		int id, days, rate, absence, salary;
		String name, position;
		
		
		// To assign values
		public Employee(int id, int days, int rate, int absence, int salary, String name, String position) {
			
		this.id = id;
		this.name = name;
		this.position = position;
		this.days = days;
		this.rate = rate;
		this.absence = absence;
		this.salary = salary;
						
		}


		public Employee(String text) {
			// TODO Auto-generated constructor stub
		}
	}

	private JFrame frame2;
	
	private Object [] tableColumnHeaders = {"Id", "Name", "Position", "Days", "Absence", "Rate", "Salary"};
	private JTable table;
	private JTable partTimeEmployeesTable;
	private JTable fullTimeEmployeesTable;
	private JTable focusedTable;
	private String focusedTableName;
	
	DefaultTableModel model;
	DefaultTableModel partTimeEmployeesTableModel;
	DefaultTableModel fullTimeEmployeesTableModel;
	
	private JTextField absence;
	private JTextField name;
	private JTextField days;
	private JTextField rate;
	private JTextField id;
	private JTextField salary;
	private JComboBox position;

	/**
	 * Launch the application.
	 */
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					DataOfEmployees window = new DataOfEmployees();
					window.frame2.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	
	public DataOfEmployees() {
		initialize();
	}
	
	public JTable createTable(DefaultTableModel tableModel, String tableName) {
		DataOfEmployees parentObject = this;
		
		JTable table = new JTable();
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				int i = table.getSelectedRow();
				id.setText(tableModel.getValueAt(i, 0).toString());
				name.setText(tableModel.getValueAt(i, 1).toString());
				position.setSelectedItem(tableModel.getValueAt(i, 2).toString());
				days.setText(tableModel.getValueAt(i, 3).toString());
				absence.setText(tableModel.getValueAt(i, 4).toString());
				rate.setText(tableModel.getValueAt(i, 5).toString());
				salary.setText(tableModel.getValueAt(i, 6).toString());
				
				parentObject.focusedTable = table;
				parentObject.focusedTableName = tableName;
			}
		});
		table.setForeground(new Color(64, 0, 128));
		table.setBackground(new Color(255, 191, 223));
		
		// Set headers
		tableModel.setColumnIdentifiers(tableColumnHeaders);
		
		// Set model
		table.setModel(tableModel);
		
		return table;
	}
	
	//Import data from system

	private Table generatePayrollTableByPosition(DefaultTableModel tableModel, String tableTitle) {
		Table pdfTable = new Table(7).useAllAvailableWidth();
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
        
        // Generate rows base on table model
        for (int j = 0; j < tableModel.getRowCount(); j++) {
        	for (int i = 0; i < tableColumnHeaders.length; i++) {
        		String rowData = tableModel.getValueAt(j, i).toString();
            	pdfTable.addCell(rowData);
            }
        }
        
        return pdfTable;
	}
	
	private void serveFileOnBrowser(File file) throws IOException {
		try {
			Desktop desktop = Desktop.getDesktop();
			desktop.open(file);
		} catch (IOException e) {
			throw new IOException("Open PDF in Browser: Something went wrong.\n Error: " + e.getMessage());
		}
	}
	
	private void generatePayrollPDF() throws IOException {
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
        
        Paragraph headerText = new Paragraph("Kweentelecom Payroll System ").setFontSize(24);
        document.add(headerText);
        
        Paragraph dateText = new Paragraph("Export Date: " + exportDate);
        document.add(dateText);
        
        // Full Time Employees
        Table fullTimeEmployeesTable = generatePayrollTableByPosition(
        		fullTimeEmployeesTableModel,
        		"Full Time Employees"
        );
        document.add(fullTimeEmployeesTable);
        
        // Part Time Employees
        Table partTimeEmployeesTable = generatePayrollTableByPosition(
        		partTimeEmployeesTableModel,
        		"Part Time Employees"
        );
        document.add(partTimeEmployeesTable);
        
        
        //Close document
        document.close();
        
        // Serve PDF on the default browser
        serveFileOnBrowser(file);
	}
	
	// Clear form fields
	private void clearFormFields() {
		id.setText("");
		name.setText("");
		position.setSelectedIndex(0);
		days.setText("");
		absence.setText("");
		rate.setText("");
		salary.setText("");
	}

	/**
	 * Initialize the contents of the frame.
	 */
	
	private void initialize() {
		DataOfEmployees parentObject = this;
		
		frame2 = new JFrame();
		frame2.getContentPane().setBackground(new Color(209, 200, 234));
		frame2.getContentPane().setLayout(null);
		
		// Full Time Employees Table
		JScrollPane scrollPane1 = new JScrollPane();
		scrollPane1.setBounds(26, 258, 452, 205);
		frame2.getContentPane().add(scrollPane1);
		
		fullTimeEmployeesTableModel = new DefaultTableModel();
		fullTimeEmployeesTable = this.createTable(fullTimeEmployeesTableModel, "Full Time Emp Table");
		scrollPane1.setViewportView(fullTimeEmployeesTable);
		
		
		// Part Time Employees Table
		JScrollPane scrollPane2 = new JScrollPane();
		scrollPane2.setBounds(481, 258, 452, 205);
		frame2.getContentPane().add(scrollPane2);
		
		partTimeEmployeesTableModel = new DefaultTableModel();
		partTimeEmployeesTable = this.createTable(partTimeEmployeesTableModel, "Part Time Emp Table");
		scrollPane2.setViewportView(partTimeEmployeesTable);
		
		JLabel lblFulltimeEmployees = new JLabel("Part-time Employees");
		lblFulltimeEmployees.setForeground(new Color(64, 0, 128));
		lblFulltimeEmployees.setFont(new Font("Times New Roman", Font.BOLD, 27));
		lblFulltimeEmployees.setBackground(Color.WHITE);
		lblFulltimeEmployees.setBounds(481, 209, 251, 38);
		frame2.getContentPane().add(lblFulltimeEmployees);
		
		JLabel lblParttimeEmployees = new JLabel("Full-time Employees");
		lblParttimeEmployees.setForeground(new Color(64, 0, 128));
		lblParttimeEmployees.setFont(new Font("Times New Roman", Font.BOLD, 24));
		lblParttimeEmployees.setBackground(Color.WHITE);
		lblParttimeEmployees.setBounds(26, 209, 251, 38);
		frame2.getContentPane().add(lblParttimeEmployees);
		frame2.setBounds(100, 100, 980, 631);
		frame2.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			
		
	
		//ADD BUTTON
		JButton btnAdd = new JButton("Add");
		btnAdd.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent arg0) {
		    	if (
		    			id.getText().equals("") || 
		                name.getText().equals("") || 
		                days.getText().equals("") || 
		                absence.getText().equals("") || 
		                rate.getText().equals("") ||
		                position.getSelectedItem().toString().equals("") ||
		                salary.getText().equals("")) 
		    	{
		            JOptionPane.showMessageDialog(null, "Please Fill Complete Information!");
		        } else {
		            // Make position field value reusable.
		            String positionValue = position.getSelectedItem().toString();
		            // Calculate salary
		            int salaryValue = 0;
		            if (positionValue.equals("Part Time")) {
		                int daysValue = Integer.parseInt(days.getText());
		                int rateValue = Integer.parseInt(rate.getText());
		                int absenceValue = Integer.parseInt(absence.getText());
		                salaryValue = (daysValue * rateValue) - (absenceValue * rateValue);
		            } else if (positionValue.equals("Full Time")) {
		            	int daysValue = Integer.parseInt(days.getText());
		                int rateValue = Integer.parseInt(rate.getText());
		                int absenceValue = Integer.parseInt(absence.getText());
		                salaryValue = (daysValue * rateValue) - (absenceValue * rateValue);
		            }
		            salary.setText("" + String.valueOf(salaryValue));
		            
		            Object tableRow[] = {id.getText(), name.getText(), positionValue, days.getText(), 
		            		absence.getText(), rate.getText(), salary.getText()};
		            
		            DefaultTableModel currentTableModel = model;
		            
		            if (positionValue.equals("Part Time")) {
		                //SQL query for part-time employees
		                String sql = "INSERT INTO `employeetable` " + 
		                "(`employee_id`, `employee_name`, `employee_position`, `employee_days`, `employee_rate`, `employee_absences`, `employee_salary`) " + 
		                        "VALUES "+"('"+id.getText()+"'," + // AUTOINCREMENT ID
		                        "'" + name.getText() + // NAME
		                        "','Part Time','" + // POSITION
		                        Integer.parseInt(days.getText()) + // DAYS
		                        "','" + Integer.parseInt(rate.getText()) + //RATE
		                        "','" + Integer.parseInt(absence.getText()) + //ABSENT
		                        "','" + salaryValue + "');"; //SALARY
		                        
		                // Database connection and insertion for part-time employees
		                String url = "jdbc:mysql://localhost/"; 
		                String dbName = "payrollsystemdb"; 
		                String driver = "com.mysql.jdbc.Driver";
		                String userName = "root";
		                String password = "";
		                
		                try (Connection conn = DriverManager.getConnection(url+dbName, userName, password);
		                    Statement state = conn.createStatement();
		                    ) {
		                    
		                    int rowsAffected = state.executeUpdate(sql);
		                    System.out.println(rowsAffected + " row(s) affected");
		                    
		                    conn.close();
		    
		                } catch (SQLException e) {
		                    System.out.println(e.getMessage());
		                }
		                
		                currentTableModel = partTimeEmployeesTableModel;
		                
		            } else if (positionValue.equals("Full Time")) {
		                // SQL query for full-time employees
		            	String sql = "INSERT INTO `employeetable` " + 
		                "(`employee_id`, `employee_name`, `employee_position`, `employee_days`, `employee_rate`,`employee_absences`,`employee_salary`) " + 
		                	"VALUES "+"('"+id.getText()+"'," + // AUTOINCREMENT ID
		                	"'" + name.getText() + // NAME
		                	"','Full Time','" + // POSITION
		                	Integer.parseInt(days.getText()) + // DAYS
		                	"','" + Integer.parseInt(rate.getText()) + //RATE
		                	"','" + Integer.parseInt(absence.getText()) + //ABSENT
		                	"','" + salaryValue + "');"; //SALARY
		            	
		            	// Database connection and insertion for full-time employees
		            	  String url = "jdbc:mysql://localhost/"; 
			              String dbName = "payrollsystemdb"; 
			              String driver = "com.mysql.jdbc.Driver";
			              String userName = "root";
			              String password = "";
			                
			                try (Connection conn = DriverManager.getConnection(url+dbName, userName, password);
			                    Statement state = conn.createStatement();
			                    ) {
			                    
			                    int rowsAffected = state.executeUpdate(sql);
			                    System.out.println(rowsAffected + " row(s) affected");
			                    
			                    conn.close();
			    
			                } catch (SQLException e) {
			                    System.out.println(e.getMessage());
			                }
			                
			                currentTableModel = fullTimeEmployeesTableModel;
		            }
 	
	            	currentTableModel.addRow(tableRow);
	            	
	            	parentObject.clearFormFields();
		            
	            	Employee e1 = new Employee(tableRow);
		            	
		        }
		    }
		});
		               
		btnAdd.setForeground(new Color(64, 0, 128));
		btnAdd.setFont(new Font("Times New Roman", Font.BOLD, 12));
		btnAdd.setBackground(Color.WHITE);
		btnAdd.setBounds(428, 474, 104, 23);
		frame2.getContentPane().add(btnAdd);


		btnAdd.setForeground(new Color(64, 0, 128));
		btnAdd.setFont(new Font("Times New Roman", Font.BOLD, 12));
		btnAdd.setBackground(Color.WHITE);
		btnAdd.setBounds(428, 474, 104, 23);
		frame2.getContentPane().add(btnAdd);
		

		//DELETE ROW
		JButton btnDelete = new JButton("Delete");
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) { 
	
				JTable focusedTable = parentObject.focusedTable;
				int rowIndex = focusedTable.getSelectedRow();
				
				System.out.println("Focused Table Name: " + parentObject.focusedTableName);
				System.out.println("rowIndex: " + rowIndex);
				
				if(rowIndex >= 0)
				{		
					String positionValue = focusedTable.getModel().getValueAt(rowIndex, 2).toString();
					
					System.out.println(positionValue);
					
					DefaultTableModel currentTableModel = model;
					if (positionValue.equals("Part Time")) {
						
//						****************************************************
						String sql = "DELETE FROM `employeetable` WHERE `employeetable`.`employee_id` = ?;";
				         // Database connection and insertion for part-time employees
		                String url = "jdbc:mysql://localhost/";  
		                String dbName = "payrollsystemdb"; 
		                String driver = "com.mysql.jdbc.Driver";
		                String userName = "root";
		                String password = "";
						
		                try (Connection conn = DriverManager.getConnection(url+dbName, userName, password);
			                    Statement state = conn.createStatement();
			                    ) {

		                	PreparedStatement stmt = conn.prepareStatement(sql);
		                	stmt.setInt(1, Integer.parseInt(id.getText().toString()));
		                	stmt.executeUpdate();

//			                    int rowsAffected = ;
			                    System.out.println(state.executeUpdate(sql));
			                    
			                    conn.close();
			    
			                } catch (SQLException e1) {
			                    System.out.println(e1.getMessage());
			                }
			                	                
//		                ****************************************************
						
						currentTableModel = parentObject.partTimeEmployeesTableModel;
					currentTableModel.removeRow(rowIndex);
					parentObject.clearFormFields();
						
						JOptionPane.showMessageDialog(null, "Deleted Successfully");
						
					} else if (positionValue.equals("Full Time")); {
						
//						****************************************************
						String sql = "DELETE FROM `employeetable` WHERE `employeetable`.`employee_id` = ?;";
				         // Database connection and insertion for part-time employees
		                String url = "jdbc:mysql://localhost/";  
		                String dbName = "payrollsystemdb"; 
		                String driver = "com.mysql.jdbc.Driver";
		                String userName = "root";
		                String password = "";
						
		                try (Connection conn = DriverManager.getConnection(url+dbName, userName, password);
			                    Statement state = conn.createStatement();
			                    ) {

		                	PreparedStatement stmt = conn.prepareStatement(sql);
		                	stmt.setInt(1, Integer.parseInt(id.getText().toString()));
		                	stmt.executeUpdate();

//			                    int rowsAffected = ;
			                    System.out.println(state.executeUpdate(sql));
			                    
			                    conn.close();
			    
			                } catch (SQLException e1) {
			                    System.out.println(e1.getMessage());
			                }
			                	                
//		                ****************************************************
						
						currentTableModel = parentObject.fullTimeEmployeesTableModel;
					currentTableModel.removeRow(rowIndex);
					parentObject.clearFormFields();
					
						JOptionPane.showMessageDialog(null, "Deleted Successfully");
				} 
				}	else {
					JOptionPane.showMessageDialog(null, "Please Select a Row");
				}
			}
		});
		

		btnDelete.setForeground(new Color(64, 0, 128));
		btnDelete.setFont(new Font("Times New Roman", Font.BOLD, 12));
		btnDelete.setBackground(Color.WHITE);
		btnDelete.setBounds(570, 474, 104, 23);
		frame2.getContentPane().add(btnDelete);
		

		
		//UPDATE BUTTON
		JButton btnUpdate = new JButton("Update");
		btnUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				if (
						id.getText().equals("") || 
						name.getText().equals("") || 
						days.getText().equals("") || 
						absence.getText().equals("") || 
						rate.getText().equals("") ||
						position.getSelectedItem().toString().equals("")
				) {
					JOptionPane.showMessageDialog(null, "Please Fill Complete Information!");
					return;
				}
					
				
				JTable focusedTable 				= parentObject.focusedTable;
				int rowIndex 						= focusedTable.getSelectedRow();
				String currentPositionValue 		= focusedTable.getValueAt(rowIndex, 2).toString(); // Value from selected row
				String newPositionValue 			= position.getSelectedItem().toString(); // Value from combo box
				DefaultTableModel currentTableModel = model;
				String oldID = id.getText().toString();
				
				
				// If user decided to change the Employee Type value
				// from what is current selected.
				if (!currentPositionValue.equals(newPositionValue)) {
					
					if (currentPositionValue.equals("Part Time")) {
						currentTableModel = partTimeEmployeesTableModel;
					} else {
						currentTableModel = model;
					}		
					// Since position value from combo box has changed
					// remove from the current focused table and we will
					// move to the other table.
					currentTableModel.removeRow(rowIndex);
					
					if (currentPositionValue.equals("Full Time")) {
						currentTableModel = fullTimeEmployeesTableModel;
					} else {
						currentTableModel = model;
					}
					currentTableModel.removeRow(rowIndex);
					
					// Transfer to another table
					Object tableRow[] = {
						id.getText(),
						name.getText(),
						newPositionValue,
						days.getText(),
						absence.getText(),
						rate.getText(),
						salary.getText()
					};
					
					if (newPositionValue.equals("Part Time")) {
						currentTableModel = partTimeEmployeesTableModel;
					} else {
						currentTableModel = model;
					}
					
					currentTableModel.addRow(tableRow);
					
					if (newPositionValue.equals("Full Time")) {
						currentTableModel = fullTimeEmployeesTableModel;
					} else {
						currentTableModel = model;
					}
					
					currentTableModel.addRow(tableRow);
					
				} else {
					if (currentPositionValue.equals("Part Time")) {
						currentTableModel = parentObject.partTimeEmployeesTableModel;
					} else if (currentPositionValue.equals("Full Time")) {
						currentTableModel = parentObject.fullTimeEmployeesTableModel;
					}
					
					currentTableModel.setValueAt(id.getText(), rowIndex, 0);
					currentTableModel.setValueAt(name.getText(), rowIndex, 1);
					currentTableModel.setValueAt(currentPositionValue, rowIndex, 2);
					currentTableModel.setValueAt(days.getText(), rowIndex, 3);
					currentTableModel.setValueAt(absence.getText(), rowIndex, 4);
					currentTableModel.setValueAt(rate.getText(), rowIndex, 5);
					currentTableModel.setValueAt(salary.getText(), rowIndex, 6);
				}
				
//				SQL QUERY **************************************
				String sql = "UPDATE "+
							"`employeetable` SET "+
						"`employee_name` = ?, `employee_position` = ?, `employee_days` = ?, `employee_rate` = ?, `employee_absences` = ?, `employee_salary` = ? "+
							"WHERE `employeetable`.`employee_id` = ?;";
				
				
		         // Database connection and insertion for part-time employees
                String url = "jdbc:mysql://localhost/";  
                String dbName = "payrollsystemdb"; 
                String driver = "com.mysql.jdbc.Driver";
                String userName = "root";
                String password = "";
				
                try (Connection conn = DriverManager.getConnection(url+dbName, userName, password);
	                    Statement state = conn.createStatement();
	                    ) {

                	PreparedStatement stmt = conn.prepareStatement(sql);
                	stmt.setString(1, name.getText().toString()); //employee_name
                	stmt.setString(2, newPositionValue); //employee_position
                	stmt.setInt(3, Integer.parseInt(days.getText().toString())); //employee_days
                	stmt.setInt(4, Integer.parseInt(rate.getText().toString())); //employee_rate
                	stmt.setInt(5, Integer.parseInt(absence.getText().toString())); //employee_absences
                	stmt.setInt(6, Integer.parseInt(salary.getText().toString())); //employee_salary
                	
                	stmt.setInt(7, Integer.parseInt(oldID)); //employee_salary
                	stmt.executeUpdate();

//	                    int rowsAffected = ;
	                    System.out.println(state.executeUpdate(sql));
	                    System.out.println(id.getText().toString());
	                    
	                    conn.close();
	    
	                } catch (SQLException e1) {
	                    System.out.println(e1.getMessage());
	                }
	                
                
//                ****************************************************
				
				// Clear form fields
				parentObject.clearFormFields();
				
				// 
				parentObject.focusedTable.clearSelection();
			}
		});
		
		btnUpdate.setForeground(new Color(64, 0, 128));
		btnUpdate.setFont(new Font("Times New Roman", Font.BOLD, 12));
		btnUpdate.setBackground(Color.WHITE);
		btnUpdate.setBounds(280, 519, 104, 23);
		frame2.getContentPane().add(btnUpdate);

		
		
		//CLEAR ALL INPUTS
		JButton btnClear = new JButton("Clear");
		btnClear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				parentObject.clearFormFields();
			}
		});
		
		btnClear.setForeground(new Color(64, 0, 128));
		btnClear.setFont(new Font("Times New Roman", Font.BOLD, 12));
		btnClear.setBackground(Color.WHITE);
		btnClear.setBounds(428, 519, 104, 23);
		frame2.getContentPane().add(btnClear);
		
		JLabel lblPosition = new JLabel("Position:");
		lblPosition.setForeground(new Color(40, 0, 81));
		lblPosition.setFont(new Font("Times New Roman", Font.BOLD, 13));
		lblPosition.setBackground(new Color(221, 160, 221));
		lblPosition.setBounds(26, 163, 77, 14);
		frame2.getContentPane().add(lblPosition);
		
		JLabel lblAbsence = new JLabel("No. of Absence:\r\n");
		lblAbsence.setForeground(new Color(40, 0, 81));
		lblAbsence.setFont(new Font("Times New Roman", Font.BOLD, 13));
		lblAbsence.setBackground(new Color(221, 160, 221));
		lblAbsence.setBounds(664, 113, 104, 14);
		frame2.getContentPane().add(lblAbsence);
		
		absence = new JTextField();
		absence.setForeground(new Color(31, 0, 62));
		absence.setColumns(10);
		absence.setBackground(Color.WHITE);
		absence.setBounds(790, 110, 143, 20);
		frame2.getContentPane().add(absence);
		
		JLabel lblName = new JLabel("Name:");
		lblName.setForeground(new Color(40, 0, 81));
		lblName.setFont(new Font("Times New Roman", Font.BOLD, 13));
		lblName.setBackground(new Color(221, 160, 221));
		lblName.setBounds(26, 138, 77, 14);
		frame2.getContentPane().add(lblName);
		
		name = new JTextField();
		name.setForeground(new Color(31, 0, 62));
		name.setColumns(10);
		name.setBackground(Color.WHITE);
		name.setBounds(99, 135, 143, 20);
		frame2.getContentPane().add(name);
		
		JLabel lblNoOfDays = new JLabel("No. of Days:");
		lblNoOfDays.setForeground(new Color(40, 0, 81));
		lblNoOfDays.setFont(new Font("Times New Roman", Font.BOLD, 13));
		lblNoOfDays.setBackground(new Color(221, 160, 221));
		lblNoOfDays.setBounds(324, 113, 77, 14);
		frame2.getContentPane().add(lblNoOfDays);
		
		days = new JTextField();
		days.setForeground(new Color(31, 0, 62));
		days.setColumns(10);
		days.setBackground(Color.WHITE);
		days.setBounds(428, 110, 142, 20);
		frame2.getContentPane().add(days);
		
		JLabel lblRate = new JLabel("No. of Rate:\r\n");
		lblRate.setForeground(new Color(40, 0, 81));
		lblRate.setFont(new Font("Times New Roman", Font.BOLD, 13));
		lblRate.setBackground(new Color(221, 160, 221));
		lblRate.setBounds(324, 138, 104, 14);
		frame2.getContentPane().add(lblRate);
		
		rate = new JTextField();
		rate.setForeground(new Color(31, 0, 62));
		rate.setColumns(10);
		rate.setBackground(Color.WHITE);
		rate.setBounds(428, 135, 143, 20);
		frame2.getContentPane().add(rate);
		
		JLabel lblDataOfEmployees = new JLabel("KWEENTELECOM PAYROLL SYSTEM");
		lblDataOfEmployees.setHorizontalAlignment(SwingConstants.CENTER);
		lblDataOfEmployees.setForeground(new Color(64, 0, 128));
		lblDataOfEmployees.setFont(new Font("Times New Roman", Font.BOLD, 27));
		lblDataOfEmployees.setBackground(Color.WHITE);
		lblDataOfEmployees.setBounds(196, 21, 547, 70);
		frame2.getContentPane().add(lblDataOfEmployees);
		
		JButton btnLogOut = new JButton("Log out"); //LOG OUT to EXIT
		btnLogOut.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				frame2.dispose();
				LogIn.main(null);
			}
		});
		
		btnLogOut.setForeground(new Color(64, 0, 128));
		btnLogOut.setFont(new Font("Times New Roman", Font.BOLD, 12));
		btnLogOut.setBackground(Color.WHITE);
		btnLogOut.setBounds(844, 558, 89, 23);
		frame2.getContentPane().add(btnLogOut);
		
			
		
		
		//EXPORT to PDF
		JButton btnPrint = new JButton("Export PDF"); 
		btnPrint.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					parentObject.generatePayrollPDF();
					// JOptionPane.showMessageDialog(null, "Successfully exported PDF!");
				} catch(IOException e) {
					JOptionPane.showMessageDialog(null, "Export: Something went wrong.\n Error: " + e.getMessage());
				}
			}
		});
		
	
		btnPrint.setForeground(new Color(64, 0, 128));
		btnPrint.setFont(new Font("Times New Roman", Font.BOLD, 12));
		btnPrint.setBackground(Color.WHITE);
		btnPrint.setBounds(570, 519, 104, 23);
		frame2.getContentPane().add(btnPrint);
		
		JLabel lbl_ID = new JLabel("ID:");
		lbl_ID.setForeground(new Color(40, 0, 81));
		lbl_ID.setFont(new Font("Times New Roman", Font.BOLD, 13));
		lbl_ID.setBackground(new Color(221, 160, 221));
		lbl_ID.setBounds(26, 113, 77, 14);
		frame2.getContentPane().add(lbl_ID);
		
		id = new JTextField();
		id.setForeground(new Color(31, 0, 62));
		id.setColumns(10);
		id.setBackground(Color.WHITE);
		id.setBounds(99, 110, 143, 20);
		frame2.getContentPane().add(id);
		
		salary = new JTextField();
		salary.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

			}
		});
		
		salary.setForeground(new Color(31, 0, 62));
		salary.setColumns(10);
		salary.setBackground(Color.WHITE);
		salary.setBounds(790, 135, 143, 20);
		frame2.getContentPane().add(salary);
		
		JLabel Salary = new JLabel("Gross Salary:");
		Salary.setForeground(new Color(40, 0, 81));
		Salary.setFont(new Font("Times New Roman", Font.BOLD, 13));
		Salary.setBackground(new Color(221, 160, 221));
		Salary.setBounds(664, 138, 104, 14);
		frame2.getContentPane().add(Salary);
		
		JButton Compute = new JButton("Compute");
		Compute.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//Computing the Salary
				DecimalFormat f = new DecimalFormat("##.00");
				
				if(e.getSource().equals(Compute)) {
					int no_of_days = Integer.parseInt(days.getText());
					double rate_days = Double.parseDouble(rate.getText());
				
					double TotalSalary = no_of_days * rate_days;
					salary.setText(String.valueOf("PHP" + f.format(TotalSalary)));
					salary.setFocusable(false);	
					
					int TotalAbsences = 6 - no_of_days;
					absence.setText(String.valueOf(TotalAbsences));
				}
			}
		});
		Compute.setForeground(new Color(64, 0, 128));
		Compute.setFont(new Font("Times New Roman", Font.BOLD, 12));
		Compute.setBackground(Color.WHITE);
		Compute.setBounds(280, 474, 104, 23);
		frame2.getContentPane().add(Compute);
		
		position = new JComboBox(new String[] {"", "Full Time" , "Part Time"});
		position.setBounds(99, 159, 143, 22);
		frame2.getContentPane().add(position);
	}
}