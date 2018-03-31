/*
 * ----------------------------------------
 *      Nutrient Usage Tracking System
 * ----------------------------------------
 *          Produced by Dan Grew
 *                 2017
 * ----------------------------------------
 */package uk.dangrew.nuts.graphics.goal;

import uk.dangrew.nuts.goal.CalorieGoal;
import uk.dangrew.nuts.goal.GoalStore;
import uk.dangrew.nuts.graphics.food.GeneralConceptTableController;
import uk.dangrew.nuts.graphics.table.ConceptTable;

/**
 * Provides a custom {@link GoalTable} for {@link Goal}s.
 */
public class GoalTable extends ConceptTable< CalorieGoal > {

   /**
    * Constructs a new {@link GoalTable}.
    * @param database the {@link Database}.
    */
   public GoalTable( GoalStore store ) {
      super( new GoalTableColumns(), new GeneralConceptTableController<>( store ) );
   }//End Constructor
   
}//End Class
