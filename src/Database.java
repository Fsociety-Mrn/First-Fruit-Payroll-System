import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import javax.swing.JComboBox;  
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class Database {
	
    private Connection conn;
    private  Statement state;
    
    // Database connection details
    private String url = "jdbc:mysql://localhost/";
    private String dbName = "payroll";
    private String driver = "com.mysql.jdbc.Driver";
    private String username = "root";
    private String password = "";
    

    public void createDatabase() {

        try {
            // Register the MySQL JDBC driver
            Class.forName(driver);

            // Create a connection to the database server
            Connection connection = DriverManager.getConnection(url, username, password);

            // Create a statement object
            Statement statement = connection.createStatement();

            // create database 
            String createDatabaseQuery = "CREATE DATABASE " + dbName;
            statement.executeUpdate(createDatabaseQuery);
            
            System.out.println("Database created successfully.");
            
            statement.close();
            connection.close();
            
        } catch (Exception e) {
        	
//           System.out.println(e.toString());
           System.out.println("Database exist");
        } 
    }

    
	
	// connection query
    private Statement getConnected(){
        
        try{
            Class.forName(driver);
            conn = DriverManager.getConnection(url+dbName,username,password);
            state = conn.createStatement();
            System.out.println("Connected to database");
            
            return state;
            
        }catch(Exception e){
            System.out.println(e);
            System.out.println("Could not connect database");
            return null;
        }
        
    }
    
//    create Login table
    public void createLoginTable() throws SQLException {
        try {

            // Create the login table
            String createTableQuery = "CREATE TABLE `login` (`ID` INT NOT NULL AUTO_INCREMENT, `Name` VARCHAR(5000) NOT NULL, `Username` VARCHAR(5000) NOT NULL, `Password` VARCHAR(5000) NOT NULL, PRIMARY KEY (`ID`)) ENGINE = InnoDB";
            getConnected().executeUpdate(createTableQuery);
            System.out.println("Login table created successfully.");

            // Close the statement and connection
            state.close();
            conn.close();
            
        } catch (Exception e) {
            // Close the statement and connection
            state.close();
            conn.close();
            
            System.out.println("Table exist");
        } 
    }
    
//  create admin table
    public void createAdminTable() throws SQLException {
      try {

          // Create the login table
          String createTableQuery = "CREATE TABLE `admin` (`ID` INT NOT NULL AUTO_INCREMENT, `Name` VARCHAR(5000) NOT NULL, `Username` VARCHAR(5000) NOT NULL, `Password` VARCHAR(5000) NOT NULL, PRIMARY KEY (`ID`)) ENGINE = InnoDB";
          getConnected().executeUpdate(createTableQuery);
          System.out.println("admin table created successfully.");

          // Close the statement and connection
          state.close();
          conn.close();
          
      	} catch (Exception e) {
          // Close the statement and connection
          state.close();
          conn.close();
          
          System.out.println("admin table exist");
      	} 
    }
    
//  create admin table
    public void createEmployeeTable() throws SQLException {
      try {

          // Create the login table
          String createTableQuery = "CREATE TABLE `employee` (`ID` INT NOT NULL AUTO_INCREMENT, `Name` VARCHAR(5000) NOT NULL, `Username` VARCHAR(5000) NOT NULL, `Password` VARCHAR(5000) NOT NULL, PRIMARY KEY (`ID`)) ENGINE = InnoDB";
          getConnected().executeUpdate(createTableQuery);
          System.out.println("employee table created successfully.");

          // Close the statement and connection
          state.close();
          conn.close();
          
      	} catch (Exception e) {
          // Close the statement and connection
          state.close();
          conn.close();
          
          System.out.println("employee table exist");
      	} 
    }
    
//  insert admin
    public void insertDeDataIntoAdminTable() throws SQLException {
        try {


            // Insert data into the login table
            String insertDataQuery = "INSERT INTO `admin` (`ID`, `Name`, `Username`, `Password`) VALUES ('1', 'ADMIN', 'admin', 'admin123')";
            getConnected().executeUpdate(insertDataQuery);
            System.out.println("Data inserted into the login table successfully.");

            // Close the statement and connection
            state.close();
            conn.close();
            
        } catch (Exception e) {
            // Close the statement and connection
            state.close();
            conn.close();
            
            System.out.println("Default Data exist");
        } 
    }
    
//  insert employee
    public void insertDeDataIntoEmployeeTable() throws SQLException {
        try {


            // Insert data into the login table
            String insertDataQuery = "INSERT INTO `employee` (`ID`, `Name`, `Username`, `Password`) VALUES ('1', 'EMPLOYEE', 'employee', 'employee123')";
            getConnected().executeUpdate(insertDataQuery);
            System.out.println("Data inserted into the login table successfully.");

            // Close the statement and connection
            state.close();
            conn.close();
            
        } catch (Exception e) {
            // Close the statement and connection
            state.close();
            conn.close();
            
            System.out.println("Default Data exist");
        } 
    }  
    
    
//    Login Admin
    public boolean loginAdmin(String username,String password){
        try{
            String query = "SELECT `ID` FROM " + 
                    "`admin`" + " WHERE username='"+ username + "' AND password='" + password + "'";
            ResultSet result =  getConnected().executeQuery(query);
            int ID = 0;
            while(result.next()){
                ID = result.getInt("ID");
            }
            if(ID != 0){
                System.out.println("Login Success");
                conn.close();
                return true;
            }else{
                System.out.println("Login failed");
                conn.close();
                return false;
            }
            
            
        }catch(Exception e){
            System.out.println(e);
            return false;
        } 
    }
    
    
    // SHOW ALL ROWS IN Employee Table
    public void showRows(JTable table){
            try{
            	

            DefaultTableModel tblmodel = (DefaultTableModel)table.getModel();
            tblmodel.setRowCount(0);
            
            String query = "SELECT * FROM `employee`";
            ResultSet result = getConnected().executeQuery(query);
            
            while(result.next()){
                String data[] = {result.getString("Name")};
                tblmodel.addRow(data);
                
                
            	
            	System.out.println(data);
            }            
            
            conn.close();      
        }catch(Exception e){
            System.out.println(e);
 
        } 
    }
    
    
    
}
