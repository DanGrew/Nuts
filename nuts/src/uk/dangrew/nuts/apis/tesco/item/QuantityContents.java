package uk.dangrew.nuts.apis.tesco.item;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

public class QuantityContents {
   
   private final ObjectProperty< Double > quantity;
   private final ObjectProperty< Double > totalQuantity;
   private final ObjectProperty< String > quantityUom;
   private final ObjectProperty< String > netContents;
   private final ObjectProperty< String > averageMeasure;
   
   public QuantityContents() {
      this.quantity = new SimpleObjectProperty<>();
      this.totalQuantity = new SimpleObjectProperty<>();
      this.quantityUom = new SimpleObjectProperty<>();
      this.netContents = new SimpleObjectProperty<>();
      this.averageMeasure = new SimpleObjectProperty<>();
   }//End Constructor

   public ObjectProperty< Double > quantity() {
      return quantity;
   }//End Method

   public ObjectProperty< Double > totalQuantity() {
      return totalQuantity;
   }//End Method

   public ObjectProperty< String > quantityUom() {
      return quantityUom;
   }//End Method

   public ObjectProperty< String > netContents() {
      return netContents;
   }//End Method

   public ObjectProperty< String > averageMeasure() {
      return averageMeasure;
   }//End Method
   
}//End Class
