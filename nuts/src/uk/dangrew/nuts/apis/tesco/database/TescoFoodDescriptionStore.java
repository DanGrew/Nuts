package uk.dangrew.nuts.apis.tesco.database;

import uk.dangrew.kode.concept.ConceptStore;
import uk.dangrew.kode.storage.structure.MappedObservableStoreManagerImpl;
import uk.dangrew.nuts.apis.tesco.model.nuts.TescoFoodDescription;

public class TescoFoodDescriptionStore extends MappedObservableStoreManagerImpl< String, TescoFoodDescription > implements ConceptStore< TescoFoodDescription > {

   public TescoFoodDescriptionStore() {
      super( f -> f.properties().id() );
   }//End Constructor

   @Override public TescoFoodDescription createConcept( String name ) {
      TescoFoodDescription food = new TescoFoodDescription( name );
      store( food );
      return food;
   }//End Method
   
   @Override public TescoFoodDescription createConcept( String id, String name ) {
      TescoFoodDescription food = new TescoFoodDescription( id, name );
      store( food );
      return food;
   }//End Method
   
   @Override public void store( TescoFoodDescription food ) {
      super.store( food );
   }//End Method
   
   @Override public void removeConcept( TescoFoodDescription food ) {
      remove( food.properties().id() );
   }//End Method

}//End Class
