package sample.SQLGateways;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.sun.rowset.CachedRowSetImpl;

import javax.sql.rowset.CachedRowSet;
import java.sql.*;

/**
 * Created by Stephen on 2/8/2018.
 */
public class TemplatePartGateway implements CabinetronGateway {

    private static TemplatePartGateway uniqueInstance;
    private ComboPooledDataSource pds;

    private static final String allTemplateParts = "SELECT * FROM templatepart";
    private static final String templateInsertStr =
            "INSERT INTO templatepart (Part, Template, Quantity) " +
            "SELECT p.partnum, pt.productnum, ? FROM producttemplate pt " +
            "CROSS JOIN part p WHERE p.partnum = ? AND pt.productnum = ?";
    // SELECT FROM other table where partnum = ? and producttemplate = ?
    private static final String tableEditStr = "UPDATE templatepart " +
            "SET Part = ?, Template = ?, Quantity = ?" +
            " WHERE TemplatePartID = ?";
    private static final String delTemplateStr = "DELETE FROM producttemplate WHERE ProductTemplateID = ?";

    public static TemplatePartGateway getInstance(){
        if(uniqueInstance == null){
            uniqueInstance = new TemplatePartGateway();
        }
        return uniqueInstance;
    }

    private TemplatePartGateway(){
        pds = new ComboPooledDataSource();
        pds.setJdbcUrl("jdbc:mysql://localhost:8081/mydatabase");
        pds.setUser("root");
        pds.setPassword("root");
    }


    @Override
    public CachedRowSet findAllRecords() throws SQLException {
        CachedRowSet rowset = new CachedRowSetImpl();
        try (Connection conn = pds.getConnection();
             Statement statement = conn.createStatement();
             ResultSet rs = statement.executeQuery(allTemplateParts)){
            rowset.populate(rs);
        }
        return rowset;
    }

    @Override
    public int insertRecord(String[] recordDetail) throws SQLException {
        int dbID = 0;
        //int insertSuccess = 0;
        try (Connection conn = pds.getConnection();
             PreparedStatement preparedStatement = createPreparedStatement(conn, recordDetail);
             ResultSet rs = preparedStatement.getGeneratedKeys()){

            //insertSuccess = preparedStatement.executeUpdate();
            //rs = preparedStatement.getGeneratedKeys();
            if(rs.next()) { // to get resultset
                dbID = rs.getInt(1);
            }
        } /*catch (SQLException se){
            se.printStackTrace();
            se.getMessage();
        }*/
        return dbID;
    }

    @Override
    public void updateRecord(String[] recordDetail, int indexToUpdate) throws SQLException {

    }

    @Override
    public void updateRecord(String[] recordDetail) throws SQLException {
        try (Connection conn = pds.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(tableEditStr)) {
            //preparedStatement.setString(1, templateDetails[0]);
            preparedStatement.setString(1, recordDetail[1]);
            preparedStatement.setString(2, recordDetail[2]);
            preparedStatement.setInt(3, Integer.parseInt(recordDetail[3]));
            preparedStatement.setInt(4, Integer.parseInt(recordDetail[0]));
            preparedStatement.executeUpdate();
        }
    }

    @Override
    public void deleteRecord(int idToDelete) throws SQLException {
        try (Connection conn = pds.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(delTemplateStr)){
            preparedStatement.setInt(1, idToDelete);
            preparedStatement.executeUpdate();
        }
    }

    @Override
    public PreparedStatement createPreparedStatement(Connection conn, String[] recordDetails) throws SQLException {
        PreparedStatement preparedStatement = conn.prepareStatement(templateInsertStr,
                Statement.RETURN_GENERATED_KEYS);
        preparedStatement.setInt(1, Integer.parseInt(recordDetails[2]));
        preparedStatement.setString(2, recordDetails[0]);
        preparedStatement.setString(3, recordDetails[1]);
        /*preparedStatement.setString(1, (recordDetails[0]));  //partnum
        preparedStatement.setString(2, recordDetails[1]); //productnum
        preparedStatement.setInt(3, Integer.parseInt(recordDetails[2])); //quantity
        preparedStatement.setInt(4, Integer.parseInt(recordDetails[2])); //quantity
        preparedStatement.setString(5, recordDetails[0]); //partnum
        preparedStatement.setString(6, recordDetails[1]); //productnum*/
        preparedStatement.executeUpdate();
        return preparedStatement;
    }
}
