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
import uk.dangrew.nuts.food.Food;

/**
 * The {@link Database} provides access to the data for the system such as {@link Food}s.
 */
public class Database {

   private final ObjectStoreManager< String, Food > foods;
   
   /**
    * Constructs a new {@link Database}.
    */
   public Database() {
      this.foods = new MappedObservableStoreManagerImpl<>( Food::id );
   }//End Constructor
   
   /**
    * Access to the {@link Food}s stored.
    * @return the {@link ObjectStoreManager} of {@link Food}s against their {@link Food#id()}.
    */
   public ObjectStoreManager< String, Food > foods() {
      return foods;
   }//End Method

}//End Class
