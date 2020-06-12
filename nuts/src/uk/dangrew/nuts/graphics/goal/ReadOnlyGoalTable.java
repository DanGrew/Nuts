/*
 * ----------------------------------------
 *      Nutrient Usage Tracking System
 * ----------------------------------------
 *          Produced by Dan Grew
 *                 2017
 * ----------------------------------------
 */package uk.dangrew.nuts.graphics.goal;

import javafx.collections.ObservableList;
import uk.dangrew.kode.javafx.table.base.ConceptTable;
import uk.dangrew.nuts.goal.Goal;
import uk.dangrew.nuts.graphics.food.FoodTableColumns;
import uk.dangrew.kode.javafx.table.controller.UnresponsiveConceptTableController;
import uk.dangrew.kode.javafx.table.column.ReadOnlyTableColumns;
import uk.dangrew.kode.javafx.table.tools.RowSynchronizer;
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
