package uk.dangrew.nuts.apis.tesco.item;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class GuidelineDailyAmountReference {

   private final String description;
   private final ObservableList< String > headers;
   private final ObservableList< String > footers;
   private final GuidelineDailyAmounts gdaValue;
   
   public GuidelineDailyAmountReference( String description ) {
      this.description = description;
      this.headers = FXCollections.observableArrayList();
      this.footers = FXCollections.observableArrayList();
      this.gdaValue = new GuidelineDailyAmounts();
   }//End Constructor

   public String getDescription() {
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
