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
import uk.dangrew.nuts.system.ConceptStore;

/**
 * {@link GoalStore} provides an {@link uk.dangrew.kode.storage.structure.ObjectStoreManager}
 * for {@link Goal}s.
 */
public class GoalStore extends MappedObservableStoreManagerImpl< String, Goal > implements ConceptStore< Goal > {

   /**
    * Constructs a new {@link GoalStore}.
    */
   public GoalStore() {
      super( f -> f.properties().id() );
   }//End Constructor

   /**
    * {@inheritDoc}
    */
   @Override public Goal createConcept( String name ) {
      Goal food = new GoalImpl( name );
      store( food );
      return food;
   }//End Method
   
   /**
    * {@inheritDoc}
    */
   @Override public Goal createConcept( String id, String name ) {
      Goal goal = new GoalImpl( id, name );
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
   @Override public void removeConcept( Goal goal ) {
      remove( goal.properties().id() );
   }//End Method

}//End Class
