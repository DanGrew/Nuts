package uk.dangrew.nuts.apis.tesco.item;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

public class CalculatedNutrientValue {

   private final String name;
   private final ObjectProperty< String > valuePer100Property;
   private final ObjectProperty< String > valuePerServingProperty;
   
   public CalculatedNutrientValue( String name ) {
      this.name = name;
      this.valuePer100Property = new SimpleObjectProperty<>();
      this.valuePerServingProperty = new SimpleObjectProperty<>();
   }//End Constructor

   public String name() {
      return name;
   }//End Method

   public ObjectProperty< String > valuePer100() {
      return valuePer100Property;
   }//End Method

   public ObjectProperty< String > valuePerServing() {
      return valuePerServingProperty;
   }//End Method
   
}//End Class
