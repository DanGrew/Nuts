/*
 * ----------------------------------------
 *      Nutrient Usage Tracking System
 * ----------------------------------------
 *          Produced by Dan Grew
 *                 2017
 * ----------------------------------------
 */
package uk.dangrew.nuts.template;

import uk.dangrew.kode.storage.structure.MappedObservableStoreManagerImpl;
import uk.dangrew.nuts.food.FoodStore;
import uk.dangrew.nuts.goal.Goal;

/**
 * {@link TemplateStore} provides an {@link uk.dangrew.kode.storage.structure.ObjectStoreManager} for 
 * {@link Template}s.
 */
public class TemplateStore extends MappedObservableStoreManagerImpl< String, Template > implements FoodStore< Template > {

   private final Goal defaultGoal;
   
   /**
    * Constructs a new {@link TemplateStore}.
    * @param defaultGoal the default {@link Goal} to associate.
    */
   public TemplateStore( Goal defaultGoal ) {
      super( m -> m.properties().id() );
      this.defaultGoal = defaultGoal;
   }//End Constructor

   /**
    * {@inheritDoc}
    */
   @Override public Template createFood( String name ) {
      Template food = new Template( name );
      food.goalAnalytics().goal().set( defaultGoal );
      store( food );
      return food;
   }//End Method
   
   /**
    * {@inheritDoc}
    */
   @Override public Template createFood( String id, String name ) {
      Template food = new Template( id, name );
      food.goalAnalytics().goal().set( defaultGoal );
      store( food );
      return food;
   }//End Method
   
   /**
    * {@inheritDoc}
    */
   @Override public void store( Template object ) {
      super.store( object );
      object.goalAnalytics().goal().set( defaultGoal );
   }//End Method
   
   /**
    * {@inheritDoc}
    */
   @Override public void removeFood( Template food ) {
      food.goalAnalytics().goal().set( null );
      remove( food.properties().id() );
   }//End Method

}//End Class
