import java.sql.SQLException;

public class App {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("App is Running....");
		Database db = new Database();
		
//		db.showRows();
		
		try {
			
//			Create Database and create admin & employee table and has a default data
			db.createDatabase();
			
			db.createAdminTable();
			db.insertDeDataIntoAdminTable();
		
			db.createEmployeeTable();
			db.insertDeDataIntoEmployeeTable();
//			db.createDatabaseEmployee("5");
			
//			System.out.print(db.loginEmployee("employee", "employee123"));
			

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		new Login().setVisible(true);
//		new EMPLOYEE().setVisible(true);

	}

}
