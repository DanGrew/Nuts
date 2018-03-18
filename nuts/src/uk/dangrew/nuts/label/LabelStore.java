/*
 * ----------------------------------------
 *      Nutrient Usage Tracking System
 * ----------------------------------------
 *          Produced by Dan Grew
 *                 2017
 * ----------------------------------------
 */
package uk.dangrew.nuts.label;

import uk.dangrew.kode.storage.structure.MappedObservableStoreManagerImpl;
import uk.dangrew.nuts.system.ConceptStore;

public class LabelStore extends MappedObservableStoreManagerImpl< String, Label > implements ConceptStore< Label > {

   public LabelStore() {
      super( f -> f.properties().id() );
   }//End Constructor

   @Override public Label createConcept( String name ) {
      Label label = new Label( name );
      store( label );
      return label;
   }//End Method
   
   @Override public Label createConcept( String id, String name ) {
      Label label = new Label( id, name );
      store( label );
      return label;
   }//End Method
   
   @Override public void store( Label label ) {
      super.store( label );
   }//End Method
   
   @Override public void removeConcept( Label label ) {
      remove( label.properties().id() );
   }//End Method

}//End Class
