/*
 * ----------------------------------------
 *      Nutrient Usage Tracking System
 * ----------------------------------------
 *          Produced by Dan Grew
 *                 2017
 * ----------------------------------------
 */
package uk.dangrew.nuts.meal;

import uk.dangrew.kode.storage.structure.MappedObservableStoreManagerImpl;
import uk.dangrew.nuts.food.FoodStore;

/**
 * {@link MealStore} provides an {@link uk.dangrew.kode.storage.structure.ObjectStoreManager} for 
 * {@link Meal}s.
 */
public class MealStore extends MappedObservableStoreManagerImpl< String, Meal > implements FoodStore< Meal > {

   /**
    * Constructs a new {@link MealStore}.
    */
   public MealStore() {
      super( m -> m.properties().id() );
   }//End Constructor

   /**
    * {@inheritDoc}
    */
   @Override public Meal createFood( String name ) {
      Meal meal = new Meal( name );
      store( meal );
      return meal;
   }//End Method
   
   /**
    * {@inheritDoc}
    */
   @Override public void removeFood( Meal food ) {
      remove( food.properties().id() );
   }//End Method

}//End Class
