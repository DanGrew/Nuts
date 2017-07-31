/*
 * ----------------------------------------
 *      Nutrient Usage Tracking System
 * ----------------------------------------
 *          Produced by Dan Grew
 *                 2017
 * ----------------------------------------
 */
package uk.dangrew.nuts.food;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

/**
 * {@link StockProperties} provides information about the stocking of supplies of {@link Food}s.
 */
public class StockProperties {

   private final ObjectProperty< Double > soldInWeight;
   private final ObjectProperty< StorageType > storageType;
   private final ObjectProperty< Double > loggedWeight;
   
   /**
    * Constructs a new {@link StockProperties}.
    */
   public StockProperties(){
      this.soldInWeight = new SimpleObjectProperty<>( 0.0 );
      this.storageType = new SimpleObjectProperty<>( StorageType.Unknown );
      this.loggedWeight = new SimpleObjectProperty<>( 0.0 );
   }//End Constructor
   
   /**
    * Access to the weight of the food as it's sold.
    * @return the {@link ObjectProperty}.
    */
   public ObjectProperty< Double > soldInWeight() {
      return soldInWeight;
   }//End Method

   /**
    * Access to the {@link StorageType} of the food, for managing the expiration.
    * @return the {@link ObjectProperty}.
    */
   public ObjectProperty< StorageType > storageType() {
      return storageType;
   }//End Method

   /**
    * Access to the weight of the food as it's logged in the system, that might relate to 
    * labels rather than portions.
    * @return the {@link ObjectProperty}.
    */
   public ObjectProperty< Double > loggedWeight() {
      return loggedWeight;
   }//End Method
   
   /**
    * Method to set the weights for the food.
    * @param loggedWeight the logged weight.
    * @param soldInWeight the sold in weight.
    */
   public void setWeighting( double loggedWeight, double soldInWeight ) {
      loggedWeight().set( loggedWeight );
      soldInWeight().set( soldInWeight );
   }//End Method

}//End Class
