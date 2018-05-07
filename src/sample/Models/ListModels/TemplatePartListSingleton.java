package sample.Models.ListModels;

import javafx.collections.FXCollections;
import javafx.collections.MapChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import sample.Models.DetailModels.ItemModel;
import sample.Models.DetailModels.TemplatePartModel;

import java.util.Collection;

/**
 * Created by Stephen on 3/1/2018.
 */
class TemplatePartListSingleton {
    private static TemplatePartListSingleton uniqueInstance;
    private ObservableList<ItemModel> templatePartList;
    private ObservableMap<Pair, TemplatePartModel> observableMap;

    static TemplatePartListSingleton getInstance(ObservableMap observableMap){
        if(uniqueInstance == null && observableMap != null){
            uniqueInstance = new TemplatePartListSingleton(observableMap);
        }
        return uniqueInstance;
    }

    private TemplatePartListSingleton(ObservableMap<Pair, TemplatePartModel> observableMap){
        this.observableMap = observableMap;
        this.templatePartList = FXCollections.observableArrayList(observableMap.values());
        observableMap.addListener((MapChangeListener.Change<? extends Pair, ? extends TemplatePartModel> c) ->{
            if(c.wasAdded()){
                this.templatePartList.add(c.getValueAdded());
            } else if (c.wasRemoved()){
                this.templatePartList.remove(c.getValueRemoved());
            }
        });
        //this.templatePartList = FXCollections.observableArrayList(observableMapValues);
    }

    ObservableList<ItemModel> getTemplatePartList(){
        return templatePartList;
    }

    ObservableMap<Pair, TemplatePartModel> getTemplatePartMap(){return observableMap;}

}
