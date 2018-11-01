/*
 * ----------------------------------------
 *      Nutrient Usage Tracking System
 * ----------------------------------------
 *          Produced by Dan Grew
 *                 2017
 * ----------------------------------------
 */
package uk.dangrew.nuts.cycle;

import uk.dangrew.kode.concept.ConceptStore;
import uk.dangrew.kode.storage.structure.MappedObservableStoreManagerImpl;

public class CycleStore extends MappedObservableStoreManagerImpl< String, Cycle > implements ConceptStore< Cycle > {

   public CycleStore() {
      super( c -> c.properties().id() );
   }//End Constructor
   
   @Override public Cycle createConcept( String name ) {
      Cycle food = new Cycle( name );
      store( food );
      return food;
   }//End Method
   
   @Override public Cycle createConcept( String id, String name ) {
      Cycle food = new Cycle( id, name );
      store( food );
      return food;
   }//End Method
   
   @Override public void store( Cycle food ) {
      super.store( food );
   }//End Method
   
   @Override public void removeConcept( Cycle food ) {
      remove( food.properties().id() );
   }//End Method

}//End Class
