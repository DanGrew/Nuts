/*
 * ----------------------------------------
 *      Nutrient Usage Tracking System
 * ----------------------------------------
 *          Produced by Dan Grew
 *                 2017
 * ----------------------------------------
 */
package uk.dangrew.nuts.day;

import uk.dangrew.kode.storage.structure.MappedObservableStoreManagerImpl;
import uk.dangrew.nuts.system.ConceptStore;

public class DayPlanStore extends MappedObservableStoreManagerImpl< String, DayPlan > implements ConceptStore< DayPlan > {

   public DayPlanStore() {
      super( f -> f.properties().id() );
   }//End Constructor
   
   /**
    * {@inheritDoc}
    */
   @Override public DayPlan createConcept( String name ) {
      DayPlan food = new DayPlan( name );
      store( food );
      return food;
   }//End Method
   
   /**
    * {@inheritDoc}
    */
   @Override public DayPlan createConcept( String id, String name ) {
      DayPlan food = new DayPlan( id, name );
      store( food );
      return food;
   }//End Method
   
   /**
    * {@inheritDoc}
    */
   @Override public void store( DayPlan object ) {
      super.store( object );
   }//End Method
   
   /**
    * {@inheritDoc}
    */
   @Override public void removeConcept( DayPlan food ) {
      remove( food.properties().id() );
   }//End Method

}//End Class
