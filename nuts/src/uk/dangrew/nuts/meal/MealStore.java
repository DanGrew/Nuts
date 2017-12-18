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
import uk.dangrew.nuts.system.ConceptStore;

/**
 * {@link MealStore} provides an {@link uk.dangrew.kode.storage.structure.ObjectStoreManager} for 
 * {@link Meal}s.
 */
public class MealStore extends MappedObservableStoreManagerImpl< String, Meal > implements ConceptStore< Meal > {

   /**
    * Constructs a new {@link MealStore}.
    */
   public MealStore() {
      super( m -> m.properties().id() );
   }//End Constructor

   /**
    * {@inheritDoc}
    */
   @Override public Meal createConcept( String name ) {
      Meal food = new Meal( name );
      store( food );
      return food;
   }//End Method
   
   /**
    * {@inheritDoc}
    */
   @Override public Meal createConcept( String id, String name ) {
      Meal food = new Meal( id, name );
      store( food );
      return food;
   }//End Method
   
   /**
    * {@inheritDoc}
    */
   @Override public void store( Meal object ) {
      super.store( object );
   }//End Method
   
   /**
    * {@inheritDoc}
    */
   @Override public void removeConcept( Meal food ) {
      remove( food.properties().id() );
   }//End Method

}//End Class
