/*
 * ----------------------------------------
 *      Nutrient Usage Tracking System
 * ----------------------------------------
 *          Produced by Dan Grew
 *                 2017
 * ----------------------------------------
 */
package uk.dangrew.nuts.stock;

import uk.dangrew.kode.storage.structure.MappedObservableStoreManagerImpl;
import uk.dangrew.nuts.system.ConceptStore;

public class StockStore extends MappedObservableStoreManagerImpl< String, Stock > implements ConceptStore< Stock > {

   public StockStore() {
      super( m -> m.properties().id() );
   }//End Constructor
   
   @Override public Stock createConcept( String name ) {
      Stock food = new Stock( name );
      store( food );
      return food;
   }//End Method
   
   @Override public Stock createConcept( String id, String name ) {
      Stock food = new Stock( id, name );
      store( food );
      return food;
   }//End Method
   
   @Override public void store( Stock object ) {
      super.store( object );
   }//End Method
   
   @Override public void removeConcept( Stock food ) {
      remove( food.properties().id() );
   }//End Method

}//End Class
