package uk.dangrew.nuts.graphics.food;

import uk.dangrew.kode.concept.ConceptStore;
import uk.dangrew.kode.javafx.table.base.ConceptTableRow;
import uk.dangrew.kode.javafx.table.controller.GeneralConceptTableController;
import uk.dangrew.nuts.food.Food;
import uk.dangrew.nuts.graphics.deletion.FoodDeletionMechanism;
import uk.dangrew.nuts.store.Database;

public class GeneralFoodTableController< TypeT extends Food > extends GeneralConceptTableController< TypeT > {

   private final FoodDeletionMechanism deletionMechanism;
   
   public GeneralFoodTableController( Database database, ConceptStore< TypeT > foods ) {
      this( new FoodDeletionMechanism( database ), foods );
   }//End Constructor
   
   public GeneralFoodTableController( FoodDeletionMechanism deletionMechanism, ConceptStore< TypeT > foods ) {
      super( foods );
      this.deletionMechanism = deletionMechanism;
   }//End Class

   @Override public void removeSelectedConcept() {
      ConceptTableRow< TypeT > selection = table().getSelectionModel().getSelectedItem();
      if ( selection == null ) {
         return;
      }
      
      if ( deletionMechanism.delete( selection.concept() ) ){
         super.removeSelectedConcept();
      }
   }//End Method
}//End Class
