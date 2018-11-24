import dao.DBHelper;

public class Test {
	public static void main(String[] args) {
		
		System.out.println(DBHelper.unique("select * from user"));
	}
}
