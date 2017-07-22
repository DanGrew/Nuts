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
import uk.dangrew.nuts.goal.Goal;

/**
 * {@link MealStore} provides an {@link uk.dangrew.kode.storage.structure.ObjectStoreManager} for 
 * {@link Meal}s.
 */
public class MealStore extends MappedObservableStoreManagerImpl< String, Meal > implements FoodStore< Meal > {

   private final Goal goal;
   
   /**
    * Constructs a new {@link MealStore}.
    * @param goal the {@link Goal}.
    */
   public MealStore( Goal goal ) {
      super( m -> m.properties().id() );
      this.goal = goal;
   }//End Constructor

   /**
    * {@inheritDoc}
    */
   @Override public Meal createFood( String name ) {
      Meal food = new Meal( name );
      food.goalAnalytics().goal().set( goal );
      store( food );
      return food;
   }//End Method
   
   /**
    * {@inheritDoc}
    */
   @Override public void store( Meal object ) {
      super.store( object );
      object.goalAnalytics().goal().set( goal );
   }//End Method
   
   /**
    * {@inheritDoc}
    */
   @Override public void removeFood( Meal food ) {
      food.goalAnalytics().goal().set( null );
      remove( food.properties().id() );
   }//End Method

}//End Class
