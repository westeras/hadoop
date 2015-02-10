/**
 * Created by adam on 11/10/14.
 */
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MySQLAction {

	public static void main(String[] args) {

		Connection con = null;
		Statement st = null;
		ResultSet rs = null;

		String url = "jdbc:mysql://localhost:3306/test";
		String user = "root";
		System.out.println("Connecting to MySQL Database...");
		System.out.println("c1" + '\t' + "c2" + '\t' + "c3");

		try {
			con = DriverManager.getConnection(url, user, null);
			st = con.createStatement();
			rs = st.executeQuery("SELECT * FROM test.dummy;");

			while (rs.next()) {
				String c1 = rs.getString("c1");
				String c2 = rs.getString("c2");
				String c3 = rs.getString("c3");
				System.out.println(c1 + '\t' + c2 + '\t' + c3);
			}

		} catch (SQLException ex) {
			Logger lgr = Logger.getLogger(MySQLAction.class.getName());
			lgr.log(Level.SEVERE, ex.getMessage(), ex);

		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (st != null) {
					st.close();
				}
				if (con != null) {
					con.close();
				}

			} catch (SQLException ex) {
				Logger lgr = Logger.getLogger(MySQLAction.class.getName());
				lgr.log(Level.WARNING, ex.getMessage(), ex);
			}
		}
	}
}
