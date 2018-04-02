package uk.dangrew.nuts.goal.proportion;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

public class ProportionConfiguration {

   private final ObjectProperty< ProportionType > carbohydrateProportionType;
   private final ObjectProperty< Double > carbohydrateValue;
   
   private final ObjectProperty< ProportionType > fatsProportionType;
   private final ObjectProperty< Double > fatsValue;
   
   private final ObjectProperty< ProportionType > proteinProportionType;
   private final ObjectProperty< Double > proteinValue;
   
   public ProportionConfiguration() {
      this.carbohydrateProportionType = new SimpleObjectProperty<>();
      this.carbohydrateValue = new SimpleObjectProperty<>( 0.0 );
      
      this.fatsProportionType = new SimpleObjectProperty<>();
      this.fatsValue = new SimpleObjectProperty<>( 0.0 );
      
      this.proteinProportionType = new SimpleObjectProperty<>();
      this.proteinValue = new SimpleObjectProperty<>( 0.0 );
   }//End Constructor

   public ObjectProperty< ProportionType > carbohydrateProportionType() {
      return carbohydrateProportionType;
   }//End Method

   public ObjectProperty< Double > carbohydrateTargetValue() {
      return carbohydrateValue;
   }//End Method
   
   public ObjectProperty< ProportionType > fatsProportionType() {
      return fatsProportionType;
   }//End Method

   public ObjectProperty< Double > fatsTargetValue() {
      return fatsValue;
   }//End Method
   
   public ObjectProperty< ProportionType > proteinProportionType() {
      return proteinProportionType;
   }//End Method

   public ObjectProperty< Double > proteinTargetValue() {
      return proteinValue;
   }//End Method
   
}//End Class
