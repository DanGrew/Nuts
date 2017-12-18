/*
 * ----------------------------------------
 *      Nutrient Usage Tracking System
 * ----------------------------------------
 *          Produced by Dan Grew
 *                 2017
 * ----------------------------------------
 */
package uk.dangrew.nuts.cycle.alternating;

import uk.dangrew.kode.storage.structure.MappedObservableStoreManagerImpl;
import uk.dangrew.nuts.system.ConceptStore;

public class AlternatingCycleStore extends MappedObservableStoreManagerImpl< String, AlternatingCycle > implements ConceptStore< AlternatingCycle > {

   public AlternatingCycleStore() {
      super( f -> f.properties().id() );
   }//End Constructor

   /**
    * {@inheritDoc}
    */
   @Override public AlternatingCycle createConcept( String name ) {
      AlternatingCycle cycle = new AlternatingCycle( name );
      store( cycle );
      return cycle;
   }//End Method
   
   /**
    * {@inheritDoc}
    */
   @Override public AlternatingCycle createConcept( String id, String name ) {
      AlternatingCycle cycle = new AlternatingCycle( id, name );
      store( cycle );
      return cycle;
   }//End Method
   
   /**
    * {@inheritDoc}
    */
   @Override public void store( AlternatingCycle cycle ) {
      super.store( cycle );
   }//End Method
   
   /**
    * {@inheritDoc}
    */
   @Override public void removeConcept( AlternatingCycle cycle ) {
      remove( cycle.properties().id() );
   }//End Method

}//End Class
