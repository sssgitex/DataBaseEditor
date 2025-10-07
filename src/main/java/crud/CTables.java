package crud;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import org.hibernate.SessionFactory;
import org.hibernate.engine.jdbc.connections.spi.ConnectionProvider;


public class CTables {
	public ArrayList<String> getTablesNames() throws SQLException{
		
	Connection conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/hw2", "admin", "admin");
	DatabaseMetaData md = conn.getMetaData();
	ResultSet rs = md.getTables(conn.getCatalog(), null, "%", new String [] {"TABLE"});
	ArrayList<String> res = new ArrayList<String>();

	while (rs.next()) {
	    res.add(rs.getString(3));
	}
	
	return res;
	}
	
	public static DataSource getDataSource(EntityManagerFactory entityManagerFactory) {
        ConnectionProvider cp = ((SessionFactory) entityManagerFactory)
            .getSessionFactoryOptions()
            .getServiceRegistry()
            .getService(ConnectionProvider.class);
        return cp.unwrap(DataSource.class);
    }
}
