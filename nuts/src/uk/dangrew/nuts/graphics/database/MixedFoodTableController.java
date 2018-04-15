package uk.dangrew.nuts.graphics.database;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import uk.dangrew.kode.observable.FunctionListChangeListenerImpl;
import uk.dangrew.nuts.food.Food;
import uk.dangrew.nuts.graphics.common.UiEnumTypeSelectionDialog;
import uk.dangrew.nuts.graphics.deletion.FoodDeletionMechanism;
import uk.dangrew.nuts.graphics.selection.model.FoodModel;
import uk.dangrew.nuts.graphics.table.ConceptTable;
import uk.dangrew.nuts.graphics.table.ConceptTableController;
import uk.dangrew.nuts.graphics.table.ConceptTableRow;
import uk.dangrew.nuts.store.Database;

public class MixedFoodTableController implements ConceptTableController< Food > {

   private final UiEnumTypeSelectionDialog< FoodTypes > dialog;
   private final Database database;
   private final FoodModel model;
   private final FoodDeletionMechanism deletionMechanism;
   
   private ConceptTable< Food > table;
   
   public MixedFoodTableController( Database database, FoodModel model ) {
      this( 
               new FoodDeletionMechanism( database ),
               new UiEnumTypeSelectionDialog<>( FoodTypes.class, FoodTypes.FoodItems ),
               database,
               model
      );
   }//End Constructor
   
   public MixedFoodTableController( 
            FoodDeletionMechanism deletionMechanism, 
            UiEnumTypeSelectionDialog< FoodTypes > dialog,
            Database database,
            FoodModel model
   ) {
      this.deletionMechanism = deletionMechanism;
      this.dialog = dialog;
      this.database = database;
      this.model = model;
   }//End Constructor
   
   /**
    * {@inheritDoc}
    */
   @Override public void associate( ConceptTable< Food > table ) {
      this.table = table;
      this.model.concepts().forEach( this::addRow );
      this.model.concepts().addListener( new FunctionListChangeListenerImpl<>( 
               this::addRow, this::removeRow
      ) );
   }//End Method
   
   protected ConceptTable< Food > table() {
      return table;
   }//End Method
   
   private void addRow( Food concept ) {
      table.getItems().add( new ConceptTableRow<>( concept ) );
   }//End Method
   
   private void removeRow( Food food ) {
      Set< ConceptTableRow< Food > > rowsToRemove = new HashSet<>();
      for ( ConceptTableRow< Food > row : table.getItems() ) {
         if ( row.concept().properties().id().equals( food.properties().id() ) ) {
            rowsToRemove.add( row );
         }
      }
      
      table.getItems().removeAll( rowsToRemove );
   }//End Method

   /**
    * {@inheritDoc}
    */
   @Override public Food createConcept() {
      Optional< FoodTypes > result = dialog.friendly_showAndWait();
      if ( result == null || !result.isPresent() ) {
         return null;
      }
      
      return result.get().redirect( database ).createConcept( "Unnamed" );
   }//End Method

   /**
    * {@inheritDoc}
    */
   @Override public void removeSelectedConcept() {
      ConceptTableRow< Food > selection = table.getSelectionModel().getSelectedItem();
      if ( selection == null ) {
         return;
      }
      
      FoodTypes type = FoodTypes.typeOf( selection.concept() );
      if ( type == null ) {
         return;
      }
      type.redirect( database ).removeConcept( selection.concept() );
   }//End Method
   
   /**
    * {@inheritDoc}
    */
   @Override public void copySelectedConcept() {
      ConceptTableRow< Food > selection = table.getSelectionModel().getSelectedItem();
      if ( selection == null ) {
         return;
      }
      
      FoodTypes type = FoodTypes.typeOf( selection.concept() );
      if ( type == null ) {
         return;
      }
      Food copy = selection.concept().duplicate( "-copy" );
      type.redirect( database ).store( copy );
   }//End Method

}//End Class
