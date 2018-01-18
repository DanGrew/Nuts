package uk.dangrew.nuts.graphics.cycle;

import java.util.HashSet;
import java.util.Set;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import uk.dangrew.kode.observable.FunctionListChangeListenerImpl;
import uk.dangrew.nuts.cycle.Cycle;
import uk.dangrew.nuts.goal.DerivedGoal;
import uk.dangrew.nuts.graphics.table.ConceptTable;
import uk.dangrew.nuts.graphics.table.ConceptTableController;
import uk.dangrew.nuts.graphics.table.ConceptTableRow;

public class UiCycleGoalsTableController implements ConceptTableController< DerivedGoal >{

   private final UiCycleDialogs dialogs;
   private final FunctionListChangeListenerImpl< DerivedGoal > goalsChangeListener;
   private final ObjectProperty< Cycle > focus;
   
   private ConceptTable< DerivedGoal > table;
   
   public UiCycleGoalsTableController() {
      this( new UiCycleDialogs() );
   }//End Constructor
   
   UiCycleGoalsTableController( UiCycleDialogs dialogs ) {
      this.focus = new SimpleObjectProperty<>();
      this.dialogs = dialogs;
      this.goalsChangeListener = new FunctionListChangeListenerImpl<>( 
               this::addRow, this::removeRow 
      );
   }//End Constructor
   
   @Override public void associate( ConceptTable< DerivedGoal > table ) {
      this.table = table;
   }//End Method
   
   public void focusOn( Cycle cycle ) {
      if ( this.focus.get() != null ) {
         this.focus.get().goals().removeListener( goalsChangeListener );
      }
      this.focus.set( cycle );
      
      table.getRows().clear();
      focus.get().goals().forEach( this::addRow );
      focus.get().goals().addListener( goalsChangeListener );
   }//End Method
   
   public ObjectProperty< Cycle > focus(){
      return focus;
   }//End Method
   
   private void addRow( DerivedGoal concept ) {
      table.getItems().add( new ConceptTableRow<>( concept ) );
   }//End Method
   
   private void removeRow( DerivedGoal concept ) {
      Set< ConceptTableRow< DerivedGoal > > rowsToRemove = new HashSet<>();
      for ( ConceptTableRow< DerivedGoal > row : table.getItems() ) {
         if ( row.concept().properties().id().equals( concept.properties().id() ) ) {
            rowsToRemove.add( row );
         }
      }
      
      table.getItems().removeAll( rowsToRemove );
   }//End Method

   @Override public DerivedGoal createConcept() {
      if ( focus.get() == null ) {
         dialogs.showNoFocusDialog();
         return null;
      }
      
      if ( focus.get().baseGoal() == null ) {
         dialogs.showNoBaseGoalDialog();
         return null;
      }
      
      DerivedGoal derived = new DerivedGoal( "Day " + ( focus.get().goals().size() + 1 ) );
      derived.setBaseGoal( focus.get().baseGoal() );
      focus.get().goals().add( derived );
      return derived;
   }//End Method

   @Override public void removeSelectedConcept() {
      if ( focus == null ) {
         return;
      }
      
      ConceptTableRow< DerivedGoal > selected = table.getSelectionModel().getSelectedItem();
      if ( selected == null ) {
         return;
      }
      focus.get().goals().remove( selected.concept() );
   }//End Method
   
   /**
    * {@inheritDoc}
    */
   @Override public void copySelectedConcept() {
      ConceptTableRow< DerivedGoal > selection = table.getSelectionModel().getSelectedItem();
      if ( selection == null ) {
         return;
      }
      
      DerivedGoal copy = selection.concept().duplicate( "-copy" );
      focus.get().goals().add( copy );
   }//End Method

}//End Class
