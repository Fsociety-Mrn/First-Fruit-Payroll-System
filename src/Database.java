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

//    create database salary history
    public void createDatabaseEmployee(String salary) {

        try {
            // Register the MySQL JDBC driver
            Class.forName(driver);

            // Create a connection to the database server
            Connection connection = DriverManager.getConnection(url, username, password);

            // Create a statement object
            Statement statement = connection.createStatement();

            // create database 
            String createDatabaseQuery = "CREATE DATABASE `" + String.valueOf(salary) + "`;";
            statement.executeUpdate(createDatabaseQuery);
            
            System.out.println("Database created successfully.");
            
            statement.close();
            connection.close();
            
        } catch (Exception e) {
        	
           System.out.println(e.toString());
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
    
//  create employee table
    public void createEmployeeTable() throws SQLException {
      try {

          // Create the login table
          String createTableQuery = "CREATE TABLE `employee` (`ID` INT NOT NULL AUTO_INCREMENT, `Name` VARCHAR(5000) NOT NULL, `Username` VARCHAR(500) NOT NULL, `Password` VARCHAR(500) NOT NULL, `salary` INT NOT NULL, `position` VARCHAR(500) NOT NULL, PRIMARY KEY (`ID`)) ENGINE = InnoDB";
          getConnected().executeUpdate(createTableQuery);
          System.out.println("employee table created successfully.");

          // Close the statement and connection
          state.close();
          conn.close();
          
      	} catch (Exception e) {
          // Close the statement and connection
          state.close();
          conn.close();
          
          System.out.println(e.toString());
          
//          System.out.println("employee table exist");
      	} 
    }
    
    
//  create employee Attendance table
    public void createAttendance() throws SQLException {
      try {

          // Create the login table
          String createTableQuery = "CREATE TABLE `attendance` ( `ID` INT NOT NULL AUTO_INCREMENT , PRIMARY KEY (`ID`),`employID` INT NOT NULL , `date` VARCHAR(500) NOT NULL , `Name` VARCHAR(500) NOT NULL , `TimeIn` VARCHAR(500) NOT NULL , `TimeOut` VARCHAR(500) NOT NULL , `workHours` DOUBLE NOT NULL , `tardiness` DOUBLE NOT NULL) ENGINE = InnoDB;";
          getConnected().executeUpdate(createTableQuery);
          System.out.println("attendance table created successfully.");

          // Close the statement and connection
          state.close();
          conn.close();
          
      	} catch (Exception e) {
          // Close the statement and connection
          state.close();
          conn.close();
          
          System.out.println(e.toString());
          
//          System.out.println("employee table exist");
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
            String insertDataQuery = "INSERT INTO `employee` (`ID`, `Name`, `Username`, `Password`, `salary`, `position`) VALUES ('1', 'EMPLOYEE', 'employee', 'employee123', '450', 'Officer in Charge (OIC)')";
            getConnected().executeUpdate(insertDataQuery);
            System.out.println("Data inserted into the employee table successfully.");

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
    
    
//  add employee
    public void addEmployee(String Name,String username,String password,String salary,String position) throws SQLException {
        try {


            // Insert data into the login table
            String insertDataQuery = "INSERT INTO `employee` (`Name`, `Username`, `Password`, `salary`, `position`) VALUES ('"+Name+"', '"+username+"', '"+password+"', '"+salary+"', '"+position+"')";
            getConnected().executeUpdate(insertDataQuery);
            System.out.println("Data inserted into the employee table successfully.");

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
    
    
//  add attendance | Time In
    public void addAttendance(int employID, String date, String Name, String TimeIn, String TimeOut, int workHours, double tard) throws SQLException {
        try {


            // Insert data into the login table
            String insertDataQuery = "INSERT INTO `attendance` (`employID`, `date`, `Name`, `TimeIn`, `TimeOut`, `workHours`, `tardiness`) VALUES ('"+employID+"', '"+date+"', '"+Name+"', '"+TimeIn+"', '"+TimeOut+"', '"+workHours+"', '"+tard+"')";
            getConnected().executeUpdate(insertDataQuery);
            System.out.println("Data inserted into the attendance table successfully.");

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
    
//  add attendance | Time Out
    public void updateAttendance(int ID, String Date, String TimeOut, double workHours) throws SQLException {
        try {


            // Insert data into the login table
            String updateDataQuery = "UPDATE `attendance` SET `TimeOut` = '"+TimeOut+"', `workHours` = '"+workHours+"' WHERE `employID` = '"+ID+"' AND `date` = '"+Date+"'";
            int rows = getConnected().executeUpdate(updateDataQuery);
            System.out.println("Attendance table successfully." + rows);

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
    
//  Login employee
    public int loginEmployee(String username,String password){
      try{
          String query = "SELECT `ID` FROM " + 
                  "`employee`" + " WHERE username='"+ username + "' AND password='" + password + "'";
          ResultSet result =  getConnected().executeQuery(query);
          int ID = 0;
          while(result.next()){
              ID = result.getInt("ID");
          }
          if(ID != 0){
              System.out.println("Login Success");
              conn.close();
              return ID;
          }else{
              System.out.println("Login failed");
              conn.close();
  
              return 0;
          }
          
          
      }catch(Exception e){
          System.out.println(e);
          return 0;
      } 
    }
    
//  check TimeIn 
  public boolean timeInCheck(String Name,String Date){
      try{
          String query = "SELECT `ID` FROM " + 
                  "`attendance`" + " WHERE Name='"+ Name + "' AND date='" + Date + "'";
          ResultSet result =  getConnected().executeQuery(query);
          int ID = 0;
          while(result.next()){
              ID = result.getInt("ID");
          }
          if(ID != 0){
              System.out.println("time in");
              conn.close();
              return true;
          }else{
              System.out.println("already time in");
              conn.close();
              return false;
          }
          
          
      }catch(Exception e){
          System.out.println(e);
          return false;
      } 
  }
  
  
//check TimeIn 
public double getEmployeeTardiness(int ID,String Date){
    try{
        String query = "SELECT `tardiness` FROM " + 
                "`attendance`" + " WHERE employID='"+ ID + "' AND date='" + Date + "'";
        ResultSet result =  getConnected().executeQuery(query);
        double tard = 0.0;
        
        while(result.next()){
        	tard = result.getDouble("tardiness");
        }
        
        System.out.println(tard);
        conn.close();
        return tard;
 
    }catch(Exception e){
        System.out.println(e);
    }
    
	return 0; 
}

//check TimeIn 
public boolean timeOutCheck(String Name,String Date){
    try{
        String query = "SELECT `TimeOut` FROM " + 
                "`attendance`" + " WHERE Name='"+ Name + "' AND date='" + Date + "'";
        ResultSet result =  getConnected().executeQuery(query);
        String timeOut = null;
        while(result.next()){
        	timeOut = result.getString("TimeOut");
        }
        
        System.out.println(timeOut);
        
        if(timeOut.equals("N/A")){
            System.out.println("time out");
            conn.close();
            return true;
        }else{
            System.out.println("already time out");
            conn.close();
            return false;
        }
        
        
    }catch(Exception e){
        System.out.println(e);
        return false;
    } 
}
  

    
//  get employee name
    public String getEmployeeName(int ID){
      try{
          String query = "SELECT `Name` FROM " + 
                  "`employee`" + " WHERE ID='"+ ID + "'";
          ResultSet result =  getConnected().executeQuery(query);
          
          String Name = "";
          while(result.next()){
        	  Name = result.getString("Name");
          }
          return Name;
          
      }catch(Exception e){
          System.out.println(e);
          return null;
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
                String data[] = {result.getString("ID"),result.getString("Name")};
                tblmodel.addRow(data);
                
                
            	
            	System.out.println(data);
            }            
            
            conn.close();      
        }catch(Exception e){
            System.out.println(e);
 
        } 
    }
    
    
    
}
