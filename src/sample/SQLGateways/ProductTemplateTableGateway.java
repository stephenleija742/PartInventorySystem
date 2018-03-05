package sample.SQLGateways;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.sun.rowset.CachedRowSetImpl;

import javax.sql.rowset.CachedRowSet;
import java.sql.*;

/**
 * Created by Stephen on 1/21/2018.
 */
public class ProductTemplateTableGateway implements CabinetronGateway{

    private static ProductTemplateTableGateway uniqueInstance;
    private ComboPooledDataSource pds;

    private static final String allTemplates = "SELECT * FROM producttemplate";
    private static final String templateInsertStr =
            "INSERT INTO producttemplate (ProductNum, ProductDescription) " +
                    "VALUES (?, ?)";
    private static final String tableEditStr = "UPDATE producttemplate " +
            "SET ProductNum = ?, ProductDescription = ?" +
            " WHERE ProductNum = ?";
    private static final String delProdStr = "DELETE FROM producttemplate WHERE ProductTemplateID = ?";

    public static ProductTemplateTableGateway getInstance(){
        if(uniqueInstance == null){
            uniqueInstance = new ProductTemplateTableGateway();
        }
        return uniqueInstance;
    }

    private ProductTemplateTableGateway(){
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
             ResultSet rs = statement.executeQuery(allTemplates)){
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
    public void updateRecord(String[] templateDetails) throws SQLException {
        try (Connection conn = pds.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(tableEditStr)) {
            //preparedStatement.setString(1, templateDetails[0]);
            preparedStatement.setString(1, templateDetails[1]);
            preparedStatement.setString(2, templateDetails[2]);
            preparedStatement.setString(3, templateDetails[1]);
            preparedStatement.executeUpdate();
        }
    }

    @Override
    public void deleteRecord(int idToDelete) throws SQLException {
        try (Connection conn = pds.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(delProdStr)) {
            preparedStatement.setInt(1, idToDelete);
            preparedStatement.executeUpdate();
        }
    }

    @Override
    public PreparedStatement createPreparedStatement(Connection conn, String[] templateDetails) throws SQLException {
        PreparedStatement preparedStatement = conn.prepareStatement(templateInsertStr,
                Statement.RETURN_GENERATED_KEYS);
        //preparedStatement.setString(1, (templateDetails[0]));
        preparedStatement.setString(1, templateDetails[0]);
        preparedStatement.setString(2, templateDetails[1]);
        preparedStatement.executeUpdate();
        return preparedStatement;
    }
}
