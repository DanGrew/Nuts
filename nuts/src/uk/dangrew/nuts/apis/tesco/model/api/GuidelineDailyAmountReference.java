package uk.dangrew.nuts.apis.tesco.model.api;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class GuidelineDailyAmountReference {

   private final ObjectProperty< String > description;
   private final ObservableList< String > headers;
   private final ObservableList< String > footers;
   private final GuidelineDailyAmounts gdaValue;
   
   public GuidelineDailyAmountReference() {
      this.description = new SimpleObjectProperty<>();
      this.headers = FXCollections.observableArrayList();
      this.footers = FXCollections.observableArrayList();
      this.gdaValue = new GuidelineDailyAmounts();
   }//End Constructor

   public ObjectProperty< String > description() {
      return description;
   }//End Method

   public ObservableList< String > headers() {
      return headers;
   }//End Method

   public ObservableList< String > footers() {
      return footers;
   }//End Method

   public GuidelineDailyAmounts gda() {
      return gdaValue;
   }//End Method
   
}//End Class
