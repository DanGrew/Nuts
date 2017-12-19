/*
 * ----------------------------------------
 *      Nutrient Usage Tracking System
 * ----------------------------------------
 *          Produced by Dan Grew
 *                 2017
 * ----------------------------------------
 */package uk.dangrew.nuts.graphics.goal;

import java.util.List;

import uk.dangrew.nuts.goal.Goal;
import uk.dangrew.nuts.graphics.food.UnresponsiveConceptTableController;
import uk.dangrew.nuts.graphics.table.ConceptTable;
import uk.dangrew.nuts.graphics.table.ConceptTableRow;
import uk.dangrew.nuts.graphics.table.ReadOnlyTableColumns;

public class ReadOnlyGoalTable extends ConceptTable< Goal > {

   public ReadOnlyGoalTable() {
      super( new ReadOnlyTableColumns<>( new GoalTableColumns() ), new UnresponsiveConceptTableController<>() );
   }//End Constructor
   
   public void setGoals( List< Goal > goals ) {
      getRows().clear();
      goals.forEach( g -> getRows().add( new ConceptTableRow< Goal >( g ) ) );
   }//End Method 
   
}//End Class
