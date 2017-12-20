/*
 * ----------------------------------------
 *      Nutrient Usage Tracking System
 * ----------------------------------------
 *          Produced by Dan Grew
 *                 2017
 * ----------------------------------------
 */package uk.dangrew.nuts.graphics.goal;

import javafx.collections.ObservableList;
import uk.dangrew.nuts.goal.Goal;
import uk.dangrew.nuts.graphics.food.UnresponsiveConceptTableController;
import uk.dangrew.nuts.graphics.table.ConceptTable;
import uk.dangrew.nuts.graphics.table.ReadOnlyTableColumns;
import uk.dangrew.nuts.graphics.table.RowSynchronizer;

public class ReadOnlyGoalTable extends ConceptTable< Goal > {

   private RowSynchronizer< Goal > synchronizer;
   
   public ReadOnlyGoalTable() {
      super( new ReadOnlyTableColumns<>( new GoalTableColumns() ), new UnresponsiveConceptTableController<>() );
   }//End Constructor
   
   public void setGoals( ObservableList< Goal > goals ) {
      if ( synchronizer != null ) {
         synchronizer.detach();
      }
      synchronizer = new RowSynchronizer<>( this, goals );
   }//End Method 
   
}//End Class
