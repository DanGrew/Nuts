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
import uk.dangrew.nuts.graphics.food.FoodTableColumns;
import uk.dangrew.nuts.graphics.food.UnresponsiveConceptTableController;
import uk.dangrew.nuts.graphics.table.ConceptTable;
import uk.dangrew.nuts.graphics.table.ReadOnlyTableColumns;
import uk.dangrew.nuts.graphics.table.RowSynchronizer;
import uk.dangrew.nuts.graphics.table.TableComponents;

public class ReadOnlyGoalTable extends ConceptTable< Goal > {

   private RowSynchronizer< Goal > synchronizer;
   
   public ReadOnlyGoalTable( TableComponents< Goal > components ) {
      super( new ReadOnlyTableColumns<>( new FoodTableColumns<>( components ) ), new UnresponsiveConceptTableController<>() );
   }//End Constructor
   
   public void setGoals( ObservableList< Goal > calorieGoals ) {
      if ( synchronizer != null ) {
         synchronizer.detach();
      }
      synchronizer = new RowSynchronizer<>( this, calorieGoals );
   }//End Method 
   
}//End Class
