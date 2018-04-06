package uk.dangrew.nuts.graphics.cycle;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import uk.dangrew.kode.observable.FunctionListChangeListenerImpl;
import uk.dangrew.nuts.cycle.Cycle;
import uk.dangrew.nuts.cycle.CycleStore;
import uk.dangrew.nuts.goal.CalorieGoalStore;
import uk.dangrew.nuts.graphics.table.ConceptTable;
import uk.dangrew.nuts.graphics.table.ConceptTableController;
import uk.dangrew.nuts.graphics.table.ConceptTableRow;

public class UiCycleTableController implements ConceptTableController< Cycle >{

   private final UiCycleCreationDialog creationDialog;
   private final CycleStore cycles;
   
   private ConceptTable< Cycle > table;
   
   public UiCycleTableController(
            CycleStore cycles,
            CalorieGoalStore goals
   ) {
      this( new UiCycleCreationDialog( goals ), cycles );
   }//End Constructor
   
   UiCycleTableController(
            UiCycleCreationDialog creationDialog,
            CycleStore cycles
   ) {
      this.creationDialog = creationDialog;
      this.cycles = cycles;
      
      this.cycles.objectList().addListener( new FunctionListChangeListenerImpl<>( 
               this::addRow, this::removeRow
      ) );
   }//End Constructor

   /**
    * {@inheritDoc}
    */
   @Override public void associate( ConceptTable< Cycle > table ) {
      this.table = table;
      
      this.cycles.objectList().forEach( this::addRow );
   }//End Method
   
   private void addRow( Cycle concept ) {
      table.getItems().add( new ConceptTableRow<>( concept ) );
   }//End Method
   
   private void removeRow( Cycle cycle ) {
      Set< ConceptTableRow< Cycle > > rowsToRemove = new HashSet<>();
      for ( ConceptTableRow< Cycle > row : table.getItems() ) {
         if ( row.concept().properties().id().equals( cycle.properties().id() ) ) {
            rowsToRemove.add( row );
         }
      }
      
      table.getItems().removeAll( rowsToRemove );
   }//End Method

   @Override public Cycle createConcept() {
      Optional< CycleCreationResult > result = creationDialog.friendly_showAndWait();
      if ( result.isPresent() ) {
         return createCycle( result.get() );
      } else {
         return null;
      }
   }//End Method
   
   private Cycle createCycle( CycleCreationResult result ) {
      Cycle cycle = cycles.createConcept( "Unnamed" );
      cycle.setBaseGoal( result.baseGoal() );
      return cycle;
   }//End Method

   @Override public void removeSelectedConcept() {
      ConceptTableRow< Cycle > selection = table.getSelectionModel().getSelectedItem();
      if ( selection == null ) {
         return;
      }
      
      cycles.removeConcept( selection.concept() );
   }//End Method
   
   @Override public void copySelectedConcept() {
      //Do nothing
   }//End Method

}//End Class
