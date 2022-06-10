package application;

import java.sql.*;

public class JdbcController {
    private Connection conn;

    public JdbcController() throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        this.conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/hospital", "root", "Sriganash@27");
    }

    public int insertIntoTable(int mid, String name, String batch_no, int price, int stock) throws SQLException {
        Statement stmt = this.conn.createStatement();
        ResultSet rs=stmt.executeQuery("select * from medicine where M_ID="+mid+";");
        if(rs.next())
        {
        	return 0;
        }
        else 
        {
        	String sql = "insert into medicine values(" + mid + ",'" + name + "','" + batch_no + "'," + price + "," + stock + ", '2027-06-15')";
 	        return stmt.executeUpdate(sql);
        }
    }
    public  int deleteRecord(int id) throws SQLException {
        Statement stmt = this.conn.createStatement();
        String sqlOld =  "select * from medicine where m_id="+id+";";
        String sql = "delete from medicine where m_id="+id+";";
        ResultSet rs = stmt.executeQuery(sqlOld);
        if(!rs.next()){
            return  -1;
        }

        return stmt.executeUpdate(sql);
    }
}
