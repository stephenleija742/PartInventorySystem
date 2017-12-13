package sample;

import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import sample.CabinetronGateway;
import sample.ListModel;

import java.util.Map;

/**
 * Created by Stephen on 11/30/2017.
 */
public interface ItemListFactory {
    ListModel createList(Map modelMapType, ObservableMap observableMap, ObservableList modelList,
                         CabinetronGateway databaseGateway);
}
