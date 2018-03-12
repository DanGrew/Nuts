package uk.dangrew.nuts.apis.tesco.database;

import uk.dangrew.kode.storage.structure.MappedObservableStoreManagerImpl;
import uk.dangrew.nuts.apis.tesco.model.TescoFoodItem;
import uk.dangrew.nuts.system.ConceptStore;

public class TescoFoodItemStore extends MappedObservableStoreManagerImpl< String, TescoFoodItem > implements ConceptStore< TescoFoodItem > {

   public TescoFoodItemStore() {
      super( f -> f.properties().id() );
   }//End Constructor

   @Override public TescoFoodItem createConcept( String name ) {
      TescoFoodItem food = new TescoFoodItem( name );
      store( food );
      return food;
   }//End Method
   
   @Override public TescoFoodItem createConcept( String id, String name ) {
      TescoFoodItem food = new TescoFoodItem( id, name );
      store( food );
      return food;
   }//End Method
   
   @Override public void store( TescoFoodItem food ) {
      super.store( food );
   }//End Method
   
   @Override public void removeConcept( TescoFoodItem food ) {
      remove( food.properties().id() );
   }//End Method

}//End Class
