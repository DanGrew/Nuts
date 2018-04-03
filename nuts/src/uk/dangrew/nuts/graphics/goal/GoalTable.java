/*
 * ----------------------------------------
 *      Nutrient Usage Tracking System
 * ----------------------------------------
 *          Produced by Dan Grew
 *                 2017
 * ----------------------------------------
 */package uk.dangrew.nuts.graphics.goal;

import uk.dangrew.nuts.goal.Goal;
import uk.dangrew.nuts.graphics.table.ConceptTable;
import uk.dangrew.nuts.store.Database;

/**
 * Provides a custom {@link GoalTable} for {@link Goal}s.
 */
public class GoalTable extends ConceptTable< Goal > {

   public GoalTable( Database datbase ) {
      super( new GoalTableColumns(), new GoalTableController( datbase.calorieGoals(), datbase.proportionGoals() ) );
   }//End Constructor
   
   GoalTable( GoalTableController controller ) {
      super( new GoalTableColumns(), controller );
   }//End Constructor
   
}//End Class
