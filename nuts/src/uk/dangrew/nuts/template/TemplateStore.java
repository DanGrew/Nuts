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
import uk.dangrew.nuts.goal.Goal;
import uk.dangrew.nuts.system.ConceptStore;

/**
 * {@link TemplateStore} provides an {@link uk.dangrew.kode.storage.structure.ObjectStoreManager} for 
 * {@link Template}s.
 */
public class TemplateStore extends MappedObservableStoreManagerImpl< String, Template > implements ConceptStore< Template > {

   private Goal defaultGoal;
   
   /**
    * Constructs a new {@link TemplateStore}.
    * @param defaultGoal the default {@link Goal} to associate.
    */
   public TemplateStore() {
      super( m -> m.properties().id() );
   }//End Constructor
   
   /**
    * Setter for the {@link Goal} applied to all new {@link Template}s.
    * @param goal the {@link Goal} to apply.
    */
   public void setDefaultGoal( Goal goal ) {
      this.defaultGoal = goal;
   }//End Method
   
   /**
    * Access to the default {@link Goal} to apply to all {@link Template}s newly created.
    * @return the {@link Goal}.
    */
   public Goal defaultGoal() {
      return defaultGoal;
   }//End Method

   /**
    * {@inheritDoc}
    */
   @Override public Template createConcept( String name ) {
      Template food = new Template( name );
      food.goalAnalytics().goal().set( defaultGoal );
      store( food );
      return food;
   }//End Method
   
   /**
    * {@inheritDoc}
    */
   @Override public Template createConcept( String id, String name ) {
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
      if ( object.goalAnalytics().goal().get() == null ) {
         object.goalAnalytics().goal().set( defaultGoal );
      }
   }//End Method
   
   /**
    * {@inheritDoc}
    */
   @Override public void removeConcept( Template food ) {
      food.goalAnalytics().goal().set( null );
      remove( food.properties().id() );
   }//End Method

}//End Class
