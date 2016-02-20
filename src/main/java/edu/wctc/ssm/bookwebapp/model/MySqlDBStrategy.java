package edu.wctc.ssm.bookwebapp.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Allows generic access of our SQL database.
 * @author Scott
 */
public class MySqlDBStrategy implements DBStrategy {

    private Connection conn;

    @Override
    public void openConnection(String driverClass, String url,
            String userName, String password) throws ClassNotFoundException, SQLException {

        Class.forName(driverClass);
        conn = DriverManager.getConnection(url, userName, password);
    }

    @Override
    public void closeConnection() throws SQLException {
        conn.close();
    }

    /**
     * Make sure you open and close connection when using method. Future
     * optimization may include change the return type of an Array.
     *
     * @param tableName
     * @param maxRecords - limit records found to first maxRecords or if
     * maxRecords is zero (0) then no limit.
     * @throws java.sql.SQLException
     * @return records
     */
    @Override
    public List<Map<String, Object>> findAllRecords(String tableName, int maxRecords) throws SQLException {
        String sql;
        if (maxRecords <= 1) {
            sql = "select * from " + tableName;
        } else {
            sql = "select * from " + tableName + " limit " + maxRecords;
        }
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(sql);
        ResultSetMetaData rsmd = rs.getMetaData();
        int columnCount = rsmd.getColumnCount();
        List<Map<String, Object>> records = new ArrayList<>();

        while (rs.next()) {
            Map<String, Object> record = new HashMap<>();
            for (int colNo = 1; colNo <= columnCount; colNo++) {
                Object colData = rs.getObject(colNo);
                String colName = rsmd.getColumnName(colNo);
                record.put(colName, colData);
            }
            records.add(record);
        }

        return records;
    }

    /**
     * Deletes a record by its Id
     *
     * @param tableName
     * @param pkColName
     * @param value
     * @return psmt.executeUpdate();
     * @throws java.sql.SQLException
     */
    @Override
    public int deleteById(String tableName, String pkColName, Object value) throws SQLException {
        String sql = "delete from " + tableName + " where " + pkColName + " = ?";
        PreparedStatement psmt = conn.prepareStatement(sql);
        psmt.setObject(1, value);
        return psmt.executeUpdate();
    }

    /**
     *
     * @param tableName
     * @param colNames
     * @param colValues
     * @param pkColName
     * @param value
     * @return
     * @throws SQLException
     */
    @Override
    public int updateRecordById(String tableName, List<String> colNames, List<Object> colValues, String pkColName, Object value) throws SQLException {
        PreparedStatement pstmt = null;
        int recsUpdated = 0;

        // do this in an excpetion handler so that we can depend on the
        // finally clause to close the connection
        try {
            pstmt = buildUpdateStatement(conn, tableName, colNames, pkColName);

            final Iterator i = colValues.iterator();
            int index = 1;
            Object obj = null;

            // set params for column values
            while (i.hasNext()) {
                obj = i.next();
                pstmt.setObject(index++, obj);
            }
            // and finally set param for wehere value
            pstmt.setObject(index, value);

            recsUpdated = pstmt.executeUpdate();

        } catch (SQLException sqle) {
            throw sqle;
        } catch (Exception e) {
            throw e;
        } finally {
            try {
                pstmt.close();
                conn.close();
            } catch (SQLException e) {
                throw e;
            } // end try
        } // end finally

        return recsUpdated;
    }

    /**
     *
     * @param value
     * @param value2
     * @return
     * @throws SQLException
     */
//    @Override
//    public int createNewRecord(String value, String value2) throws SQLException {
//        //author = tablename, List 
////                              sql2 = "INSERT INTO actor (first_name,last_name)"
////                                + " ('Billy','Carter')";
//    String sql = "INSERT INTO author (author_name,date_added)" + " ('Dan Smith','2003-10-10')";
//    
//    PreparedStatement psmt = conn.prepareStatement(sql);
//    return psmt.executeUpdate();
//    }
    

    
    

    /*
	 * Builds a java.sql.PreparedStatement for an sql update using only one where clause test
	 * @param conn - a JDBC <code>Connection</code> object
	 * @param tableName - a <code>String</code> representing the table name
	 * @param colDescriptors - a <code>List</code> containing the column descriptors for
	 * the fields that can be updated.
	 * @param whereField - a <code>String</code> representing the field name for the
	 * search criteria.
	 * @return java.sql.PreparedStatement
	 * @throws SQLException
     */
    private PreparedStatement buildUpdateStatement(Connection conn_loc, String tableName, List colNames, String pkColName) throws SQLException {
        StringBuffer sql = new StringBuffer("UPDATE ");
        (sql.append(tableName)).append(" SET ");
        final Iterator i = colNames.iterator();
        while (i.hasNext()) {
            (sql.append((String) i.next())).append(" = ?, ");
        }
        sql = new StringBuffer((sql.toString()).substring(0, (sql.toString()).lastIndexOf(", ")));
        ((sql.append(" WHERE ")).append(pkColName)).append(" = ?");
        final String finalSQL = sql.toString();
        return conn_loc.prepareStatement(finalSQL);
    }
    //(String tableName, String pkColName, Object value)
//    @Override
//     public int createOneRecord(String tablename, List<String> colNames, List<Object> values) throws SQLException{
//    String sql = "INSERT INTO "+tablename+" ("+ colNames.get(0) +" ,"+ colNames.get(1) +")"
//                           + " VALUES( ? , ? )";
//    PreparedStatement psmt = conn.prepareStatement(sql);
//    psmt.setObject(1, values.get(0));
//    psmt.setObject(2, values.get(1));
//    return psmt.executeUpdate();
//     }
    
    @Override
     public int createOneRecord(String tablename, List<String> colNames, List<Object> values) throws SQLException{
    String sql = "INSERT INTO "+tablename+" ("+ colNames.get(0) +" ,"+ colNames.get(1) +")"
                           + " VALUES( ? , ? )";
    PreparedStatement psmt = conn.prepareStatement(sql);
    psmt.setObject(1, values.get(0));
    psmt.setObject(2, values.get(1));
    return psmt.executeUpdate();
     }
     
     //    @Override
//    public int deleteById(String tableName, String pkColName, Object value) throws SQLException {
//        String sql = "delete from " + tableName + " where " + pkColName + " = ?";
//        PreparedStatement psmt = conn.prepareStatement(sql);
//        psmt.setObject(1, value);
//        return psmt.executeUpdate();
//    }

    //Testing
    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        DBStrategy db = new MySqlDBStrategy();
//        db.openConnection("com.mysql.jdbc.Driver", "jdbc:mysql://localhost:3306/books", "root", "admin");
//        List<String> colNames = Arrays.asList("author_name", "date_added");
//        List<Object> colValues = Arrays.asList("Lucifer", "2000-11-11");
//        int result = db.updateRecordById("author", colNames, colValues, "author_id", 1);
//        db.closeConnection();
//
        db.openConnection("com.mysql.jdbc.Driver", "jdbc:mysql://localhost:3306/book", "root", "root");
        Object name = "'Bob Johnson'";
        Object date = new Date();
        String table = "author";
        String col1 = "author_name";
        String col2 = "date_added";
        List colNames = new ArrayList();
        colNames.add(col1);
        colNames.add(col2);
        List values = new ArrayList();
        values.add(name);
        values.add(date);
        //public int createOneRecord(String tablename, List<String> colNames, List<String> values) throws SQLException;
        db.createOneRecord(table, colNames, values);
        List rawData = db.findAllRecords("author", 0);
        System.out.println(rawData);
        
        db.closeConnection();
    }
}
