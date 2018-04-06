/*
 * ----------------------------------------
 *      Nutrient Usage Tracking System
 * ----------------------------------------
 *          Produced by Dan Grew
 *                 2017
 * ----------------------------------------
 */
package uk.dangrew.nuts.graphics.goal;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import uk.dangrew.kode.observable.FunctionListChangeListenerImpl;
import uk.dangrew.nuts.goal.CalorieGoal;
import uk.dangrew.nuts.goal.Goal;
import uk.dangrew.nuts.goal.CalorieGoalStore;
import uk.dangrew.nuts.goal.GoalTypes;
import uk.dangrew.nuts.goal.proportion.ProportionGoal;
import uk.dangrew.nuts.goal.proportion.ProportionGoalStore;
import uk.dangrew.nuts.graphics.table.ConceptTable;
import uk.dangrew.nuts.graphics.table.ConceptTableController;
import uk.dangrew.nuts.graphics.table.ConceptTableRow;

public class GoalTableController implements ConceptTableController< Goal > {

   private final UiGoalTypeSelectionDialog dialog;
   private final CalorieGoalStore calorieGoals;
   private final ProportionGoalStore proportionGoals;
   
   private ConceptTable< Goal > table;
   
   public GoalTableController( 
            CalorieGoalStore calorieGoals,
            ProportionGoalStore proportionGoals
   ) {
      this( new UiGoalTypeSelectionDialog(), calorieGoals, proportionGoals );
   }//End Constructor
   
   GoalTableController(
            UiGoalTypeSelectionDialog dialog,
            CalorieGoalStore calorieGoals,
            ProportionGoalStore proportionGoals
   ) {
      this.dialog = dialog;
      this.calorieGoals = calorieGoals;
      this.proportionGoals = proportionGoals;
      
      this.calorieGoals.objectList().addListener( new FunctionListChangeListenerImpl<>( 
               this::addRow, this::removeRow
      ) );
      this.proportionGoals.objectList().addListener( new FunctionListChangeListenerImpl<>( 
               this::addRow, this::removeRow
      ) );
   }//End Constructor

   /**
    * {@inheritDoc}
    */
   @Override public void associate( ConceptTable< Goal > table ) {
      this.table = table;
      
      this.calorieGoals.objectList().forEach( this::addRow );
      this.proportionGoals.objectList().forEach( this::addRow );
   }//End Method
   
   protected ConceptTable< Goal > table() {
      return table;
   }//End Method
   
   private void addRow( Goal concept ) {
      table.getItems().add( new ConceptTableRow<>( concept ) );
   }//End Method
   
   private void removeRow( Goal food ) {
      Set< ConceptTableRow< Goal > > rowsToRemove = new HashSet<>();
      for ( ConceptTableRow< Goal > row : table.getItems() ) {
         if ( row.concept().properties().id().equals( food.properties().id() ) ) {
            rowsToRemove.add( row );
         }
      }
      
      table.getItems().removeAll( rowsToRemove );
   }//End Method

   /**
    * {@inheritDoc}
    */
   @Override public Goal createConcept() {
      Optional< GoalTypes > result = dialog.friendly_showAndWait();
      if ( result == null || !result.isPresent() ) {
         return null;
      }
      
      switch ( result.get() ) {
         case Calorie:
            return calorieGoals.createConcept( "Unnamed" );
         case Proportion:
            return proportionGoals.createConcept( "Unnamed" );
      }
      return null;
   }//End Method

   /**
    * {@inheritDoc}
    */
   @Override public void removeSelectedConcept() {
      ConceptTableRow< Goal > selection = table.getSelectionModel().getSelectedItem();
      if ( selection == null ) {
         return;
      }
      
      switch ( selection.concept().type() ) {
         case Calorie:
            calorieGoals.remove( selection.concept().properties().id() );
            return;
         case Proportion:
            proportionGoals.remove( selection.concept().properties().id() );
            return;
      }
   }//End Method
   
   /**
    * {@inheritDoc}
    */
   @Override public void copySelectedConcept() {
      ConceptTableRow< Goal > selection = table.getSelectionModel().getSelectedItem();
      if ( selection == null ) {
         return;
      }
      
      switch ( selection.concept().type() ) {
         case Calorie:
            CalorieGoal calorieCopy = ( CalorieGoal )selection.concept().duplicate( "-copy" );
            calorieGoals.store( calorieCopy );
            return;
         case Proportion:
            ProportionGoal proportionCopy = ( ProportionGoal )selection.concept().duplicate( "-copy" );
            proportionGoals.store( proportionCopy );
            return;
      }
   }//End Method
   
}//End Class
