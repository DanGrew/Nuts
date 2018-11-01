/*
 * ----------------------------------------
 *      Nutrient Usage Tracking System
 * ----------------------------------------
 *          Produced by Dan Grew
 *                 2017
 * ----------------------------------------
 */
package uk.dangrew.nuts.progress.custom;

import uk.dangrew.kode.concept.ConceptStore;
import uk.dangrew.kode.storage.structure.MappedObservableStoreManagerImpl;

public class ProgressSeriesStore extends MappedObservableStoreManagerImpl< String, ProgressSeries > implements ConceptStore< ProgressSeries > {

   public ProgressSeriesStore() {
      super( f -> f.properties().id() );
   }//End Constructor

   @Override public ProgressSeries createConcept( String name ) {
      ProgressSeries food = new ProgressSeries( name );
      store( food );
      return food;
   }//End Method
   
   @Override public ProgressSeries createConcept( String id, String name ) {
      ProgressSeries food = new ProgressSeries( id, name );
      store( food );
      return food;
   }//End Method
   
   @Override public void store( ProgressSeries food ) {
      super.store( food );
   }//End Method
   
   @Override public void removeConcept( ProgressSeries food ) {
      remove( food.properties().id() );
   }//End Method

}//End Class
