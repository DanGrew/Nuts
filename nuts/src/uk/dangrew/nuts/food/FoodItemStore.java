/*
 * ----------------------------------------
 *      Nutrient Usage Tracking System
 * ----------------------------------------
 *          Produced by Dan Grew
 *                 2017
 * ----------------------------------------
 */
package uk.dangrew.nuts.food;

import uk.dangrew.kode.storage.structure.MappedObservableStoreManagerImpl;
import uk.dangrew.nuts.goal.Goal;

/**
 * {@link FoodItemStore} provides an {@link uk.dangrew.kode.storage.structure.ObjectStoreManager}
 * for {@link FoodItem}s.
 */
public class FoodItemStore extends MappedObservableStoreManagerImpl< String, FoodItem > implements FoodStore< FoodItem > {

   private final Goal goal;
   
   /**
    * Constructs a new {@link FoodItemStore}.
    * @param goal the {@link Goal}.
    */
   public FoodItemStore( Goal goal ) {
      super( f -> f.properties().id() );
      this.goal = goal;
   }//End Constructor

   /**
    * {@inheritDoc}
    */
   @Override public FoodItem createFood( String name ) {
      FoodItem food = new FoodItem( name );
      food.goalAnalytics().goal().set( goal );
      store( food );
      return food;
   }//End Method
   
   /**
    * {@inheritDoc}
    */
   @Override public void store( FoodItem food ) {
      food.goalAnalytics().goal().set( goal );
      super.store( food );
   }//End Method
   
   /**
    * {@inheritDoc}
    */
   @Override public void removeFood( FoodItem food ) {
      food.goalAnalytics().goal().set( null );
      remove( food.properties().id() );
   }//End Method

}//End Class
