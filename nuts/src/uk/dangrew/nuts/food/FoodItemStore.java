/*
 * ----------------------------------------
 *      Nutrient Usage Tracking System
 * ----------------------------------------
 *          Produced by Dan Grew
 *                 2017
 * ----------------------------------------
 */
package uk.dangrew.nuts.food;

import uk.dangrew.kode.concept.ConceptStore;
import uk.dangrew.kode.storage.structure.MappedObservableStoreManagerImpl;

/**
 * {@link FoodItemStore} provides an {@link uk.dangrew.kode.storage.structure.ObjectStoreManager}
 * for {@link FoodItem}s.
 */
public class FoodItemStore extends MappedObservableStoreManagerImpl< String, FoodItem > implements ConceptStore< FoodItem > {

   /**
    * Constructs a new {@link FoodItemStore}.
    */
   public FoodItemStore() {
      super( f -> f.properties().id() );
   }//End Constructor

   /**
    * {@inheritDoc}
    */
   @Override public FoodItem createConcept( String name ) {
      FoodItem food = new FoodItem( name );
      store( food );
      return food;
   }//End Method
   
   /**
    * {@inheritDoc}
    */
   @Override public FoodItem createConcept( String id, String name ) {
      FoodItem food = new FoodItem( id, name );
      store( food );
      return food;
   }//End Method
   
   /**
    * {@inheritDoc}
    */
   @Override public void store( FoodItem food ) {
      super.store( food );
   }//End Method
   
   /**
    * {@inheritDoc}
    */
   @Override public void removeConcept( FoodItem food ) {
      remove( food.properties().id() );
   }//End Method

}//End Class
