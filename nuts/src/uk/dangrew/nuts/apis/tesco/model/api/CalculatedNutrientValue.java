package uk.dangrew.nuts.apis.tesco.model.api;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

public class CalculatedNutrientValue {

   private final ObjectProperty< String > nameProperty;
   private final ObjectProperty< String > valuePer100Property;
   private final ObjectProperty< String > valuePerServingProperty;
   
   public CalculatedNutrientValue() {
      this.nameProperty = new SimpleObjectProperty<>();
      this.valuePer100Property = new SimpleObjectProperty<>();
      this.valuePerServingProperty = new SimpleObjectProperty<>();
   }//End Constructor

   public ObjectProperty< String > name() {
      return nameProperty;
   }//End Method
   
   public ObjectProperty< String > valuePer100() {
      return valuePer100Property;
   }//End Method

   public ObjectProperty< String > valuePerServing() {
      return valuePerServingProperty;
   }//End Method
   
}//End Class
