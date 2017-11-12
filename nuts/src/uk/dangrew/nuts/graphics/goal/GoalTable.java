/*
 * ----------------------------------------
 *      Nutrient Usage Tracking System
 * ----------------------------------------
 *          Produced by Dan Grew
 *                 2017
 * ----------------------------------------
 */package uk.dangrew.nuts.graphics.goal;

import uk.dangrew.nuts.goal.Goal;
import uk.dangrew.nuts.goal.GoalStore;
import uk.dangrew.nuts.graphics.food.GeneralFoodTableController;
import uk.dangrew.nuts.graphics.table.FoodTable;

/**
 * Provides a custom {@link GoalTable} for {@link Goal}s.
 */
public class GoalTable extends FoodTable< Goal > {

   /**
    * Constructs a new {@link GoalTable}.
    * @param database the {@link Database}.
    */
   public GoalTable( GoalStore store ) {
      super( new GoalTableColumns(), new GeneralFoodTableController<>( store ) );
   }//End Constructor
   
}//End Class
