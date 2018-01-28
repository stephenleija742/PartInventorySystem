package sample.SQLGateways;

import javax.sql.rowset.CachedRowSet;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Created by Stephen on 9/17/2017.
 */
public interface CabinetronGateway {

    CachedRowSet findAllRecords() throws SQLException;
    int insertRecord(String[] recordDetail) throws SQLException;
    void updateRecord(String[] recordDetail, int indexToUpdate) throws SQLException;
    void updateRecord(String[] recordDetail) throws SQLException;
    void deleteRecord(int idToDelete) throws SQLException;
    PreparedStatement createPreparedStatement(Connection conn, String[] recordDetails) throws SQLException;
}
