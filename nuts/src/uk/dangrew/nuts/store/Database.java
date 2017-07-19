/*
 * ----------------------------------------
 *      Nutrient Usage Tracking System
 * ----------------------------------------
 *          Produced by Dan Grew
 *                 2017
 * ----------------------------------------
 */
package uk.dangrew.nuts.store;

import uk.dangrew.kode.storage.structure.MappedObservableStoreManagerImpl;
import uk.dangrew.kode.storage.structure.ObjectStoreManager;
import uk.dangrew.nuts.food.FoodItem;

/**
 * The {@link Database} provides access to the data for the system such as {@link Food}s.
 */
public class Database {

   private final ObjectStoreManager< String, FoodItem > foodItems;
   
   /**
    * Constructs a new {@link Database}.
    */
   public Database() {
      this.foodItems = new MappedObservableStoreManagerImpl<>( f -> f.properties().id() );
   }//End Constructor
   
   /**
    * Access to the {@link Food}s stored.
    * @return the {@link ObjectStoreManager} of {@link Food}s against their {@link Food#id()}.
    */
   public ObjectStoreManager< String, FoodItem > foodItems() {
      return foodItems;
   }//End Method

}//End Class
