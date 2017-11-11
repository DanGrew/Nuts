/*
 * ----------------------------------------
 *      Nutrient Usage Tracking System
 * ----------------------------------------
 *          Produced by Dan Grew
 *                 2017
 * ----------------------------------------
 */
package uk.dangrew.nuts.goal;

import uk.dangrew.kode.storage.structure.MappedObservableStoreManagerImpl;
import uk.dangrew.nuts.food.FoodStore;

/**
 * {@link GoalStore} provides an {@link uk.dangrew.kode.storage.structure.ObjectStoreManager}
 * for {@link Goal}s.
 */
public class GoalStore extends MappedObservableStoreManagerImpl< String, Goal > implements FoodStore< Goal > {

   /**
    * Constructs a new {@link GoalStore}.
    */
   public GoalStore() {
      super( f -> f.properties().id() );
   }//End Constructor

   /**
    * {@inheritDoc}
    */
   @Override public Goal createFood( String name ) {
      Goal food = new Goal( name );
      store( food );
      return food;
   }//End Method
   
   /**
    * {@inheritDoc}
    */
   @Override public Goal createFood( String id, String name ) {
      Goal goal = new Goal( id, name );
      store( goal );
      return goal;
   }//End Method
   
   /**
    * {@inheritDoc}
    */
   @Override public void store( Goal goal ) {
      super.store( goal );
   }//End Method
   
   /**
    * {@inheritDoc}
    */
   @Override public void removeFood( Goal goal ) {
      remove( goal.properties().id() );
   }//End Method

}//End Class
