package uk.dangrew.nuts.apis.tesco.item;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

public class ProductCharacteristics {
   
   private final ObjectProperty< Boolean > isFoodProperty;
   private final ObjectProperty< Boolean > isDrinkProperty;
   private final ObjectProperty< Double > healthScoreProperty;
   private final ObjectProperty< Boolean > isHazardousProperty;
   private final ObjectProperty< String > storageTypeProperty;
   private final ObjectProperty< Boolean > isNonLiquidAnalgesicProperty;
   private final ObjectProperty< Boolean > containsLoperamideProperty;
   
   public ProductCharacteristics() {
      this.isFoodProperty = new SimpleObjectProperty<>();
      this.isDrinkProperty = new SimpleObjectProperty<>();
      this.healthScoreProperty = new SimpleObjectProperty<>();
      this.isHazardousProperty = new SimpleObjectProperty<>();
      this.storageTypeProperty = new SimpleObjectProperty<>();
      this.isNonLiquidAnalgesicProperty = new SimpleObjectProperty<>();
      this.containsLoperamideProperty = new SimpleObjectProperty<>();
   }//End Constructor

   public ObjectProperty< Boolean > isFood() {
      return isFoodProperty;
   }//End Method

   public ObjectProperty< Boolean > isDrink() {
      return isDrinkProperty;
   }//End Method

   public ObjectProperty< Double > healthScore() {
      return healthScoreProperty;
   }//End Method

   public ObjectProperty< Boolean > isHazardous() {
      return isHazardousProperty;
   }//End Method

   public ObjectProperty< String > storageType() {
      return storageTypeProperty;
   }//End Method

   public ObjectProperty< Boolean > isNonLiquidAnalgesic() {
      return isNonLiquidAnalgesicProperty;
   }//End Method

   public ObjectProperty< Boolean > containsLoperamide() {
      return containsLoperamideProperty;
   }//End Method
   
}//End Class
