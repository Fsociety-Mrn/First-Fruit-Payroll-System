import java.sql.SQLException;

public class App {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("App is Running....");
		Database db = new Database();
		
//		db.showRows();
		
		try {
			
//			Create Database payroll and create admin & employee table and has a default data
			db.createDatabase();
			
			
//			create a admin table and deafualtt admin user and pass
			db.createAdminTable();
			db.insertDeDataIntoAdminTable();
		
//			create employee table with default employee user and pass
			db.createEmployeeTable();
			db.insertDeDataIntoEmployeeTable();
			
//			create attendance table 
			db.createAttendance();
			
//			db.addAttendance(0, "hello", "friend", "hello", "friend", 0, 0);
			

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		new Login().setVisible(true);
//		new EMPLOYEE().setVisible(true);

	}

}
