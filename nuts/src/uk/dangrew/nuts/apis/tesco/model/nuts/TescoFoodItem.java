package uk.dangrew.nuts.apis.tesco.model.nuts;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import uk.dangrew.kode.concept.Concept;
import uk.dangrew.kode.concept.Properties;
import uk.dangrew.nuts.food.FoodItem;

public class TescoFoodItem implements Concept {
   
   private final Properties properties;
   private final ObjectProperty< TescoFoodDescription > descriptionProperty;
   private final ObjectProperty< FoodItem > foodItemProperty;
   
   public TescoFoodItem( String name ) {
      this( new Properties( name ) );
   }//End Constructor
   
   public TescoFoodItem( String id, String name ) {
      this( new Properties( id, name ) );
   }//End Constructor
   
   private TescoFoodItem( Properties properties ) {
      this.properties = properties;
      
      this.descriptionProperty = new SimpleObjectProperty<>();
      this.foodItemProperty = new SimpleObjectProperty<>();
   }//End Constructor

   @Override public Properties properties() {
      return properties;
   }//End Method

   @Override public Concept duplicate( String referenceId ) {
      return this;
   }//End Method

   public ObjectProperty< TescoFoodDescription > description() {
      return descriptionProperty;
   }//End Method
   
   public ObjectProperty< FoodItem > foodItem() {
      return foodItemProperty;
   }//End Method

}//End Class
