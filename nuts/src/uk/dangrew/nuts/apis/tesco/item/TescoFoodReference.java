package uk.dangrew.nuts.apis.tesco.item;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import uk.dangrew.nuts.food.FoodItem;
import uk.dangrew.nuts.system.Concept;
import uk.dangrew.nuts.system.Properties;

public class TescoFoodReference implements Concept {
   
   private final Properties properties;
   private final ObjectProperty< String > productNumberForBaseProduct;
   private final ObjectProperty< String > productNumberForConsumerUnit;
   private final ObjectProperty< String > catalogueNumber;
   private final ObjectProperty< FoodItem > associatedFoodItem;

   public TescoFoodReference( String name ) {
      this( new Properties( name ) );
   }//End Constructor
   
   public TescoFoodReference( String id, String name ) {
      this( new Properties( id, name ) );
   }//End Constructor
   
   protected TescoFoodReference( Properties properties ) {
      this.properties = properties;
      this.productNumberForBaseProduct = new SimpleObjectProperty<>();
      this.productNumberForConsumerUnit = new SimpleObjectProperty<>();
      this.catalogueNumber = new SimpleObjectProperty<>();
      this.associatedFoodItem = new SimpleObjectProperty<>();
   }//End Constructor

   @Override public Properties properties() {
      return properties;
   }//End Method

   @Override public Concept duplicate( String referenceId ) {
      return this;
   }//End Method

   public ObjectProperty< String > productNumberForBaseProduct() {
      return productNumberForBaseProduct;
   }//End Method

   public ObjectProperty< String > productNumberForConsumerUnit() {
      return productNumberForConsumerUnit;
   }//End Method

   public ObjectProperty< String > catalogueNumber() {
      return catalogueNumber;
   }//End Method

   public ObjectProperty< FoodItem > association() {
      return associatedFoodItem;
   }//End Method

}//End Class
