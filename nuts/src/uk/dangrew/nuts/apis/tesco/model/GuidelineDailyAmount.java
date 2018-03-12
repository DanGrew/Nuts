package uk.dangrew.nuts.apis.tesco.model;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

public class GuidelineDailyAmount {
   
   private final ObjectProperty< String > amountProperty;
   private final ObjectProperty< String > percentProperty;
   private final ObjectProperty< String > ratingProperty;
   
   public GuidelineDailyAmount() {
      this.amountProperty = new SimpleObjectProperty<>();
      this.percentProperty = new SimpleObjectProperty<>();
      this.ratingProperty = new SimpleObjectProperty<>();
   }//End Constructor

   public ObjectProperty< String > amount() {
      return amountProperty;
   }//End Method

   public ObjectProperty< String > percent() {
      return percentProperty;
   }//End Method

   public ObjectProperty< String > rating() {
      return ratingProperty;
   }//End Method
   
}//End Class
